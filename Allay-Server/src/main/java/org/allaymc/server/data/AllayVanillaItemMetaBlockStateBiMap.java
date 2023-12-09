package org.allaymc.server.data;

import org.allaymc.api.block.palette.BlockStateHashPalette;
import org.allaymc.api.block.type.BlockState;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.data.VanillaItemMetaBlockStateBiMap;
import org.allaymc.api.identifier.Identifier;
import org.allaymc.api.item.registry.ItemTypeRegistry;
import org.allaymc.api.item.type.ItemType;
import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Allay Project 2023/10/28
 *
 * @author daoge_cmd
 */
@Slf4j
public final class AllayVanillaItemMetaBlockStateBiMap implements VanillaItemMetaBlockStateBiMap {

    private static final Map<ItemType<?>, Map<Integer, BlockState>> ITEM_TYPE_TO_META_MAP = new HashMap<>();
    private static final Map<BlockType<?>, Map<Integer, Integer>> BLOCK_STATE_HASH_TO_META_MAP = new HashMap<>();

    public void init() {
        // Load item_meta_block_state_bimap.nbt
        try (var reader = NbtUtils.createGZIPReader(Objects.requireNonNull(AllayVanillaItemMetaBlockStateBiMap.class.getClassLoader().getResourceAsStream("item_meta_block_state_bimap.nbt")))) {
            var nbt = (NbtMap) reader.readTag();
            nbt.forEach((itemIdentifier, metaToHash) -> {
                ItemType<?> itemType = ItemTypeRegistry.getRegistry().get(new Identifier(itemIdentifier));
                Objects.requireNonNull(itemType, "Cannot find item type by identifier: " + itemIdentifier);
                var metaToHashMap = (NbtMap) metaToHash;
                metaToHashMap.forEach((meta, blockStateHash) -> {
                    var metaInt = Integer.parseInt(meta);
                    var blockStateHashInt = (Integer) blockStateHash;
                    var blockState = BlockStateHashPalette.getRegistry().get(blockStateHashInt);
                    Objects.requireNonNull(blockState, "Cannot find block state by hash: " + blockStateHashInt);
                    ITEM_TYPE_TO_META_MAP.computeIfAbsent(itemType, k -> new Int2ObjectOpenHashMap<>()).put(metaInt, blockState);
                    BLOCK_STATE_HASH_TO_META_MAP.computeIfAbsent(blockState.getBlockType(), k -> new Int2ObjectOpenHashMap<>()).put(blockStateHashInt, metaInt);
                });
            });
        } catch (IOException e) {
            log.error("Cannot load item_meta_block_state_bimap.nbt!", e);
        }
    }

    @Override
    public Function<Integer, BlockState> getMetaToBlockStateMapper(ItemType<?> itemType) {
        var map = ITEM_TYPE_TO_META_MAP.get(itemType);
        if (map != null) {
            return map::get;
        } else {
            return unused -> itemType.getBlockType().getDefaultState();
        }
    }

    @Override
    public Function<Integer, Integer> getBlockStateHashToMetaMapper(BlockType<?> blockType) {
        var map = BLOCK_STATE_HASH_TO_META_MAP.get(blockType);
        if (map != null) {
            return map::get;
        } else {
            return unused -> 0;
        }
    }
}
