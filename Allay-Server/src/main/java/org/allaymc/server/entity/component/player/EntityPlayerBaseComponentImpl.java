package org.allaymc.server.entity.component.player;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.allaymc.api.client.data.Abilities;
import org.allaymc.api.client.data.AdventureSettings;
import org.allaymc.api.client.skin.Skin;
import org.allaymc.api.client.storage.PlayerData;
import org.allaymc.api.command.CommandSender;
import org.allaymc.api.component.annotation.ComponentEventListener;
import org.allaymc.api.component.annotation.Dependency;
import org.allaymc.api.component.interfaces.ComponentInitInfo;
import org.allaymc.api.container.FixedContainerId;
import org.allaymc.api.container.FullContainerType;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.entity.component.common.EntityContainerHolderComponent;
import org.allaymc.api.entity.component.event.PlayerLoggedInEvent;
import org.allaymc.api.entity.component.item.EntityItemBaseComponent;
import org.allaymc.api.entity.component.player.EntityPlayerBaseComponent;
import org.allaymc.api.entity.component.player.EntityPlayerNetworkComponent;
import org.allaymc.api.entity.init.EntityInitInfo;
import org.allaymc.api.entity.interfaces.EntityItem;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.i18n.I18n;
import org.allaymc.api.i18n.TrContainer;
import org.allaymc.api.math.location.Location3f;
import org.allaymc.api.math.location.Location3fc;
import org.allaymc.api.math.location.Location3ic;
import org.allaymc.api.perm.tree.PermTree;
import org.allaymc.api.server.Server;
import org.allaymc.api.utils.MathUtils;
import org.allaymc.api.utils.Utils;
import org.allaymc.api.world.chunk.Chunk;
import org.allaymc.server.entity.component.common.EntityBaseComponentImpl;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtType;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginType;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputMessage;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.joml.primitives.AABBf;
import org.joml.primitives.AABBfc;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Allay Project 2023/9/23
 *
 * @author daoge_cmd
 */
public class EntityPlayerBaseComponentImpl extends EntityBaseComponentImpl<EntityPlayer> implements EntityPlayerBaseComponent {

    @Dependency
    protected EntityContainerHolderComponent containerHolderComponent;
    @Dependency
    protected EntityPlayerNetworkComponent networkComponent;
    @Getter
    protected GameType gameType = GameType.CREATIVE;
    @Getter
    @Setter
    protected Skin skin;
    @Getter
    protected AdventureSettings adventureSettings;
    @Getter
    protected Abilities abilities;
    @Getter
    protected PermTree permTree;
    @Getter
    protected String displayName;
    @Getter
    protected int chunkLoadingRadius = Server.SETTINGS.worldSettings().viewDistance();
    @Getter
    @Setter
    protected int chunkTrySendCountPerTick = Server.SETTINGS.worldSettings().chunkTrySendCountPerTick();
    protected CommandOriginData commandOriginData;
    @Getter
    @Setter
    protected Location3ic spawnPoint;
    protected boolean needDimensionChangeACK;

    public EntityPlayerBaseComponentImpl(EntityInitInfo<EntityPlayer> info) {
        super(info);
    }

    @Override
    public AABBfc getAABB() {
        return new AABBf(-0.3f, 0.0f, -0.3f, 0.3f, 1.8f, 0.3f);
    }

    @ComponentEventListener
    protected void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        var loginData = networkComponent.getLoginData();
        skin = loginData.getSkin();
        setDisplayName(loginData.getDisplayName());
    }

    @Override
    public void onInitFinish(ComponentInitInfo initInfo) {
        super.onInitFinish(initInfo);
        permTree = PermTree.create();
        permTree.setOp(true); // TODO: perm db
        adventureSettings = new AdventureSettings(thisEntity);
        abilities = new Abilities(thisEntity);
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        setAndSendEntityData(EntityDataTypes.NAME, displayName);
    }

    @Override
    public void setGameType(GameType gameType) {
        this.gameType = gameType;

        this.fallDistance = 0;

        this.adventureSettings.applyGameType(gameType);
        this.abilities.applyGameType(gameType);

        setAndSendEntityFlag(EntityFlag.SILENT, gameType == GameType.SPECTATOR);
        setAndSendEntityFlag(EntityFlag.HAS_COLLISION, gameType != GameType.SPECTATOR);
        setHasGravity(gameType != GameType.SPECTATOR);

        var pk = new UpdatePlayerGameTypePacket();
        pk.setGameType(gameType);
        pk.setEntityId(uniqueId);
        networkComponent.sendPacket(pk);
        sendPacketToViewers(pk);
    }

    @Override
    public void tick() {
        super.tick();
        syncData();
        tryPickUpItems();
    }

    protected void syncData() {
        abilities.sync();
        adventureSettings.sync();
    }

    protected void tryPickUpItems() {
        var dimension = location.dimension;
        // pick up items
        var pickUpArea = new AABBf(
                location.x - 1.425f,
                location.y - 1.425f,
                location.z - 1.425f,
                location.x + 1.425f,
                location.y + 1.425f,
                location.z + 1.425f
        );
        var entityItems = dimension.getEntityPhysicsService().computeCollidingEntities(pickUpArea, true)
                .stream()
                .filter(EntityItem.class::isInstance)
                .map(EntityItem.class::cast)
                .filter(EntityItemBaseComponent::canBePicked)
                .toList();
        for (var entityItem : entityItems) {
            var item = entityItem.getItemStack();
            var inventory = Objects.requireNonNull(containerHolderComponent.getContainer(FullContainerType.PLAYER_INVENTORY));
            var slot = inventory.tryAddItem(item);
            if (slot != -1) {
                if (item.getCount() == 0) {
                    TakeItemEntityPacket takeItemEntityPacket = new TakeItemEntityPacket();
                    takeItemEntityPacket.setRuntimeEntityId(uniqueId);
                    takeItemEntityPacket.setItemRuntimeEntityId(entityItem.getUniqueId());
                    Objects.requireNonNull(dimension.getChunkService().getChunkByLevelPos((int) location.x, (int) location.z)).sendChunkPacket(takeItemEntityPacket);
                    entityItem.setItemStack(null);
                    dimension.getEntityService().removeEntity(entityItem);
                }
                // Because of the new inventory system, the client will expect a transaction confirmation, but instead of doing that
                // It's much easier to just resend the inventory.
                thisEntity.sendContentsWithSpecificContainerId(inventory, FixedContainerId.PLAYER_INVENTORY, slot);
            }
        }
    }

    @Override
    protected void teleportInDimension(Location3fc target) {
        super.teleportInDimension(target);
        // For player, we also need to send move packet to client
        // However, there is no need to send motion packet as we are teleporting the player
        sendLocationToSelf();
    }

    @Override
    protected void teleportOverDimension(Location3fc target) {
        var currentDim = location.dimension();
        var targetDim = target.dimension();
        if (currentDim.getWorld() != targetDim.getWorld()) {
            targetDim.getWorld().sendTime(thisEntity);
            networkComponent.sendPacket(targetDim.getWorld().getWorldData().getGameRules().buildPacket());
        }
        this.location.dimension().removePlayer(thisEntity, () -> {
            targetDim.getChunkService().getChunkImmediately((int) target.x() >> 4, (int) target.z() >> 4);
            setLocation(target, false);
            sendLocationToSelf();
            if (currentDim.getDimensionInfo().dimensionId() != targetDim.getDimensionInfo().dimensionId()) {
                var changeDimensionPacket = new ChangeDimensionPacket();
                changeDimensionPacket.setDimension(targetDim.getDimensionInfo().dimensionId());
                changeDimensionPacket.setPosition(MathUtils.JOMLVecToCBVec(target));
                networkComponent.sendPacket(changeDimensionPacket);
                needDimensionChangeACK = true;
            }
            targetDim.addPlayer(thisEntity);
        });
    }

    @Override
    public void spawnTo(EntityPlayer player) {
        if (thisEntity != player) super.spawnTo(player);
    }

    @Override
    public void despawnFrom(EntityPlayer player) {
        if (thisEntity != player) super.despawnFrom(player);
    }

    @Override
    public void broadcastMoveToViewers(Location3fc newLoc, boolean teleporting) {
        var loc = new Location3f(newLoc);
        loc.add(0, getBaseOffset(), 0f);
        super.broadcastMoveToViewers(loc, teleporting);
    }

    @Override
    public BedrockPacket createSpawnPacket() {
        var addPlayerPacket = new AddPlayerPacket();
        addPlayerPacket.setRuntimeEntityId(uniqueId);
        addPlayerPacket.setUniqueEntityId(uniqueId);
        addPlayerPacket.setUuid(networkComponent.getLoginData().getUuid());
        addPlayerPacket.setUsername(networkComponent.getOriginName());
        addPlayerPacket.setPlatformChatId(networkComponent.getLoginData().getDeviceInfo().getDeviceId());
        addPlayerPacket.setPosition(Vector3f.from(location.x(), location.y() + getBaseOffset(), location.z()));
        addPlayerPacket.setMotion(Vector3f.from(motion.x(), motion.y(), motion.z()));
        addPlayerPacket.setRotation(Vector3f.from(location.pitch(), location.yaw(), location.headYaw()));
        addPlayerPacket.setGameType(gameType);
        addPlayerPacket.getMetadata().putAll(this.metadata.getEntityDataMap());
        addPlayerPacket.setDeviceId(networkComponent.getLoginData().getDeviceInfo().getDeviceId());
        addPlayerPacket.setHand(containerHolderComponent.getContainer(FullContainerType.PLAYER_INVENTORY).getItemInHand().toNetworkItemData());
        return addPlayerPacket;
    }

    @Override
    public boolean isSprinting() {
        return metadata.get(EntityFlag.SPRINTING);
    }

    @Override
    public void setSprinting(boolean sprinting) {
        setAndSendEntityFlag(EntityFlag.SPRINTING, sprinting);
    }

    @Override
    public boolean isSneaking() {
        return metadata.get(EntityFlag.SNEAKING);
    }

    @Override
    public void setSneaking(boolean sneaking) {
        setAndSendEntityFlag(EntityFlag.SNEAKING, sneaking);
    }

    @Override
    public boolean isSwimming() {
        return metadata.get(EntityFlag.SWIMMING);
    }

    @Override
    public void setSwimming(boolean swimming) {
        setAndSendEntityFlag(EntityFlag.SWIMMING, swimming);
    }

    @Override
    public boolean isGliding() {
        return metadata.get(EntityFlag.GLIDING);
    }

    @Override
    public void setGliding(boolean gliding) {
        setAndSendEntityFlag(EntityFlag.GLIDING, gliding);
    }

    @Override
    public boolean isCrawling() {
        return metadata.get(EntityFlag.CRAWLING);
    }

    @Override
    public void setCrawling(boolean crawling) {
        setAndSendEntityFlag(EntityFlag.CRAWLING, crawling);
    }

    @Override
    public int getHandSlot() {
        return containerHolderComponent.getContainer(FullContainerType.PLAYER_INVENTORY).getHandSlot();
    }

    @Override
    public void setHandSlot(int handSlot) {
        Preconditions.checkArgument(handSlot >= 0 && handSlot <= 8);
        var inv = containerHolderComponent.getContainer(FullContainerType.PLAYER_INVENTORY);
        inv.setHandSlot(handSlot);
        var itemStack = inv.getItemStack(handSlot);

        var mobEquipmentPacket = new MobEquipmentPacket();
        mobEquipmentPacket.setRuntimeEntityId(uniqueId);
        mobEquipmentPacket.setItem(itemStack.toNetworkItemData());
        mobEquipmentPacket.setInventorySlot(handSlot);
        mobEquipmentPacket.setHotbarSlot(handSlot);

        sendPacketToViewers(mobEquipmentPacket);
    }

    @Override
    public boolean computeMovementServerSide() {
        // TODO: fake client
        return false;
    }

    @Override
    public NbtMap saveNBT() {
        return super.saveNBT().toBuilder()
                .putCompound("Perm", permTree.saveNBT())
                .putList(
                        "Offhand",
                        NbtType.COMPOUND,
                        containerHolderComponent.getContainer(FullContainerType.OFFHAND).saveNBT())
                .putList(
                        "Inventory",
                        NbtType.COMPOUND,
                        containerHolderComponent.getContainer(FullContainerType.PLAYER_INVENTORY).saveNBT())
                .putList(
                        "Armor",
                        NbtType.COMPOUND,
                        containerHolderComponent.getContainer(FullContainerType.ARMOR).saveNBT())
                .build();
    }

    @Override
    public void loadNBT(NbtMap nbt) {
        super.loadNBT(nbt);
        if (nbt.containsKey("Perm")) {
            permTree.loadNBT(nbt.getCompound("Perm"));
        }
        if (nbt.containsKey("Offhand")) {
            containerHolderComponent.getContainer(FullContainerType.OFFHAND).loadNBT(nbt.getList("Offhand", NbtType.COMPOUND));
        }
        if (nbt.containsKey("Inventory")) {
            containerHolderComponent.getContainer(FullContainerType.PLAYER_INVENTORY).loadNBT(nbt.getList("Inventory", NbtType.COMPOUND));
        }
        if (nbt.containsKey("Armor")) {
            containerHolderComponent.getContainer(FullContainerType.ARMOR).loadNBT(nbt.getList("Armor", NbtType.COMPOUND));
        }
    }

    @Override
    public void sendCommandOutputs(CommandSender sender, int status, TrContainer... outputs) {
        var pk = new CommandOutputPacket();
        pk.setType(CommandOutputType.ALL_OUTPUT);
        pk.setCommandOriginData(sender.getCommandOriginData());
        for (var output : outputs) {
            pk.getMessages().add(new CommandOutputMessage(
                    false,
                    I18n.get().tr(thisEntity.getLangCode(), output.str(), output.args()),
                    Utils.EMPTY_STRING_ARRAY));
        }
        pk.setSuccessCount(status);
        pk.setData(""); // Unknown usage
        networkComponent.sendPacket(pk);
    }

    @Override
    public CommandOriginData getCommandOriginData() {
        if (commandOriginData == null) {
            commandOriginData = new CommandOriginData(CommandOriginType.PLAYER, networkComponent.getLoginData().getUuid(), "", 0);
        }
        return commandOriginData;
    }

    @Override
    public void sendTip(String message) {
        sendSimpleMessage(message, TextPacket.Type.TIP);
    }

    @Override
    public void sendPopup(String message) {
        sendSimpleMessage(message, TextPacket.Type.POPUP);
    }

    @Override
    public PlayerData savePlayerData() {
        return PlayerData.builder()
                .playerNBT(saveNBT())
                .currentWorldName(getWorld().getWorldData().getName())
                .currentDimensionId(getDimension().getDimensionInfo().dimensionId())
                .spawnPoint(new org.joml.Vector3i(spawnPoint.x(), spawnPoint.y(), spawnPoint.z()))
                .spawnPointWorldName(spawnPoint.dimension().getWorld().getWorldData().getName())
                .spawnPointDimensionId(spawnPoint.dimension().getDimensionInfo().dimensionId())
                .build();
    }

    @Override
    public void sendLocationToSelf() {
        networkComponent.sendPacket(createMovePacket(location, true));
    }

    @Override
    public void sendText(String text) {
        sendSimpleMessage(text, TextPacket.Type.RAW);
    }

    protected void sendSimpleMessage(String message, TextPacket.Type type) {
        var pk = new TextPacket();
        pk.setType(type);
        pk.setXuid(networkComponent.getLoginData().getXuid());
        pk.setMessage(message);
        networkComponent.sendPacket(pk);
    }

    @Override
    public boolean isLoaderActive() {
        return spawned;
    }

    @Override
    public void setChunkLoadingRadius(int radius) {
        chunkLoadingRadius = Math.min(radius, Server.SETTINGS.worldSettings().viewDistance());
        var chunkRadiusUpdatedPacket = new ChunkRadiusUpdatedPacket();
        chunkRadiusUpdatedPacket.setRadius(chunkLoadingRadius);
        networkComponent.sendPacket(chunkRadiusUpdatedPacket);
    }

    @Override
    public void publishClientChunkUpdate() {
        var chunkPublisherUpdatePacket = new NetworkChunkPublisherUpdatePacket();
        var loc = getLocation();
        chunkPublisherUpdatePacket.setPosition(Vector3i.from(loc.x(), loc.y(), loc.z()));
        chunkPublisherUpdatePacket.setRadius(getChunkLoadingRadius() << 4);
        networkComponent.sendPacket(chunkPublisherUpdatePacket);
    }

    @Override
    public void onChunkInRangeSent(Chunk chunk) {
        if (needDimensionChangeACK) {
            needDimensionChangeACK = false;
            sendDimensionChangeSuccess();
        }
        chunk.spawnEntitiesTo(thisEntity);
        networkComponent.onChunkInRangeSent();
    }

    @Override
    public void sendDimensionChangeSuccess() {
        var ackPk = new PlayerActionPacket();
        ackPk.setAction(PlayerActionType.DIMENSION_CHANGE_SUCCESS);
        ackPk.setRuntimeEntityId(uniqueId);
        ackPk.setBlockPosition(Vector3i.ZERO);
        ackPk.setResultPosition(Vector3i.ZERO);
        networkComponent.sendPacket(ackPk);
    }

    @Override
    public void spawnEntity(Entity entity) {
        entity.spawnTo(this.thisEntity);
    }

    @Override
    public void despawnEntity(Entity entity) {
        entity.despawnFrom(this.thisEntity);
    }

    @Override
    public void onChunkOutOfRange(Set<Long> chunkHashes) {
        chunkHashes
                .stream()
                .map(location.dimension.getChunkService()::getChunk).filter(Objects::nonNull)
                .forEach(chunk -> {
                    chunk.despawnEntitiesFrom(thisEntity);
                    chunk.removeChunkLoader(thisEntity);
                });
    }

    @Override
    public void handleChunkPacket(BedrockPacket packet) {
        networkComponent.sendPacket(packet);
    }

    @Override
    public void handleChunkPacketImmediately(BedrockPacket packet) {
        networkComponent.sendPacketImmediately(packet);
    }

    @Override
    public void sendLevelChunkPacket(LevelChunkPacket lcp) {
        networkComponent.sendPacket(lcp);
    }

    @Override
    public void sendTr(String key, boolean forceTranslatedByClient, String... args) {
        if (forceTranslatedByClient) {
            var pk = new TextPacket();
            pk.setType(TextPacket.Type.TRANSLATION);
            pk.setXuid("");
            pk.setNeedsTranslation(true);
            pk.setMessage(key);
            pk.setParameters(List.of(args));
            networkComponent.sendPacket(pk);
        } else sendText(I18n.get().tr(thisEntity.getLangCode(), key, args));
    }

    @Override
    public String getName() {
        return thisEntity.getOriginName();
    }

    @Override
    public void applyEntityEvent(EntityEventType event, int data) {
        var pk = new EntityEventPacket();
        pk.setRuntimeEntityId(getUniqueId());
        pk.setType(event);
        pk.setData(data);
        sendPacketToViewers(pk);
        // Player should also send the packet to itself
        networkComponent.sendPacket(pk);
    }

    @Override
    public void applyAnimation(AnimatePacket.Action action, float rowingTime) {
        var pk = new AnimatePacket();
        pk.setRuntimeEntityId(getUniqueId());
        pk.setAction(action);
        pk.setRowingTime(rowingTime);
        sendPacketToViewers(pk);
        // Player should also send the packet to itself
        networkComponent.sendPacket(pk);
    }
}
