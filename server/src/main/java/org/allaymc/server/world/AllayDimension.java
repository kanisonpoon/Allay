package org.allaymc.server.world;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.block.dto.BlockStateWithPos;
import org.allaymc.api.block.dto.PlayerInteractInfo;
import org.allaymc.api.block.type.BlockState;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.eventbus.event.block.BlockBreakEvent;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.math.position.Position3i;
import org.allaymc.api.world.Dimension;
import org.allaymc.api.world.DimensionInfo;
import org.allaymc.api.world.generator.WorldGenerator;
import org.allaymc.server.world.service.AllayBlockUpdateService;
import org.allaymc.server.world.service.AllayChunkService;
import org.allaymc.server.world.service.AllayEntityPhysicsService;
import org.allaymc.server.world.service.AllayEntityService;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.allaymc.api.block.type.BlockTypes.AIR;

/**
 * @author Cool_Loong
 */
@Slf4j
@Getter
public class AllayDimension implements Dimension {
    protected final WorldGenerator worldGenerator;
    protected final AllayChunkService chunkService;
    protected final AllayEntityPhysicsService entityPhysicsService;
    protected final AllayBlockUpdateService blockUpdateService;
    protected final AllayEntityService entityService;
    protected final DimensionInfo dimensionInfo;
    protected final AllayWorld world;

    protected final Set<EntityPlayer> players = Collections.newSetFromMap(new ConcurrentHashMap<>());
    protected final Set<EntityPlayer> unmodifiablePlayersView = Collections.unmodifiableSet(this.players);

    public AllayDimension(AllayWorld world, WorldGenerator worldGenerator, DimensionInfo dimensionInfo) {
        this.world = world;
        this.dimensionInfo = dimensionInfo;
        this.worldGenerator = worldGenerator;
        this.worldGenerator.setDimension(this);
        this.chunkService = new AllayChunkService(this, world.getWorldStorage());
        this.entityPhysicsService = new AllayEntityPhysicsService(this);
        this.entityService = new AllayEntityService(entityPhysicsService);
        this.blockUpdateService = new AllayBlockUpdateService(this);
    }

    public void tick(long currentTick) {
        chunkService.tick(currentTick);
        entityService.tick();
        entityPhysicsService.tick();
        blockUpdateService.tick(currentTick);
    }

    public void shutdown() {
        chunkService.unloadAllChunks().join();
    }

    @Override
    public void addPlayer(EntityPlayer player, Runnable runnable) {
        players.add(player);
        chunkService.addChunkLoader(player);
        entityService.addEntity(player, runnable);
    }

    @Override
    public void removePlayer(EntityPlayer player, Runnable runnable) {
        if (player.isSpawned()) {
            // When the player respawns to another dimension after death, the player entity has already been unloaded
            // Therefore, when unloading the player entity, we need to check if the player entity has been spawned
            entityService.removeEntity(player, runnable);
            chunkService.removeChunkLoader(player);
            players.remove(player);
        } else {
            chunkService.removeChunkLoader(player);
            players.remove(player);
            // Run the callback directly
            runnable.run();
        }
    }

    @Override
    @UnmodifiableView
    public Set<EntityPlayer> getPlayers() {
        return unmodifiablePlayersView;
    }

    @Override
    public void setBlockState(int x, int y, int z, BlockState blockState, int layer, boolean send, boolean update, boolean callBlockBehavior, PlayerInteractInfo placementInfo) {
        var chunk = getChunkService().getChunkByDimensionPos(x, z);
        if (chunk == null) chunk = getChunkService().getOrLoadChunkSync(x >> 4, z >> 4);

        var xIndex = x & 15;
        var zIndex = z & 15;
        var oldBlockState = chunk.getBlockState(xIndex, y, zIndex, layer);

        var blockPos = new Position3i(x, y, z, this);
        var oldBlockStateWithPos = new BlockStateWithPos(oldBlockState, blockPos, layer);
        if (callBlockBehavior) {
            blockState.getBehavior().onPlace(oldBlockStateWithPos, blockState, placementInfo);
            oldBlockState.getBehavior().onReplace(oldBlockStateWithPos, blockState, placementInfo);
        }
        chunk.setBlockState(xIndex, y, zIndex, blockState, layer);

        if (update) updateAround(x, y, z);
        if (send) chunk.sendChunkPacket(Dimension.createUpdateBlockPacket(blockState, x, y, z, layer));

        if (callBlockBehavior) {
            chunk.getBlockState(xIndex, y, zIndex, layer == 0 ? 1 : 0).getBehavior().afterNeighborLayerReplace(oldBlockStateWithPos, blockState, placementInfo);
        }
    }

    @Override
    public boolean breakBlock(int x, int y, int z, ItemStack usedItem, EntityPlayer player) {
        var block = getBlockState(x, y, z);
        if (block.getBlockType() == AIR) {
            return false;
        }

        var event = new BlockBreakEvent(
                new BlockStateWithPos(block, new Position3i(x, y, z, this), 0),
                usedItem, player
        );
        event.call();
        if (event.isCancelled()) {
            return false;
        }

        var pk = new LevelEventPacket();
        pk.setType(LevelEvent.PARTICLE_DESTROY_BLOCK);
        pk.setPosition(Vector3f.from(x + 0.5f, y + 0.5f, z + 0.5f));
        pk.setData(block.blockStateHash());
        getChunkService().getChunkByDimensionPos(x, z).addChunkPacket(pk);

        block.getBehavior().onBreak(
                new BlockStateWithPos(block, new Position3i(x, y, z, this), 0),
                usedItem, player
        );

        setBlockState(x, y, z, AIR.getDefaultState());

        if (player != null) player.exhaust(0.005f);

        return true;
    }

    @Override
    public String toString() {
        return "world=" + this.world.getWorldData().getName() + " dimId=" + this.dimensionInfo.dimensionId();
    }
}
