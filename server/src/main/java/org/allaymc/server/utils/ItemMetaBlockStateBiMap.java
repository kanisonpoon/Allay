package org.allaymc.server.utils;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.block.type.BlockState;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.utils.Identifier;
import org.allaymc.api.utils.Utils;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author daoge_cmd
 */
@Slf4j
public final class ItemMetaBlockStateBiMap {

    private static final Map<ItemType<?>, Map<Integer, BlockState>> ITEM_TYPE_TO_META_MAP = new HashMap<>();
    private static final Map<BlockType<?>, Map<Integer, Integer>> BLOCK_STATE_HASH_TO_META_MAP = new HashMap<>();
    private static boolean INITIALIZED = false;

    @SneakyThrows
    public static void init() {
        try (var reader = NbtUtils.createGZIPReader(Utils.getResource("item_meta_block_state_bimap.nbt"))) {
            var nbt = (NbtMap) reader.readTag();
            nbt.forEach((itemIdentifier, metaToHash) -> {
                var itemType = Registries.ITEMS.get(new Identifier(itemIdentifier));
                Objects.requireNonNull(itemType, "Cannot find item type by identifier: " + itemIdentifier);
                var metaToHashMap = (NbtMap) metaToHash;
                metaToHashMap.forEach((meta, blockStateHash) -> {
                    var metaInt = Integer.parseInt(meta);
                    var blockStateHashInt = (Integer) blockStateHash;
                    var blockState = Registries.BLOCK_STATE_PALETTE.get(blockStateHashInt.intValue());
                    Objects.requireNonNull(blockState, "Cannot find block state by hash: " + blockStateHashInt);
                    ITEM_TYPE_TO_META_MAP.computeIfAbsent(itemType, k -> new Int2ObjectOpenHashMap<>()).put(metaInt, blockState);
                    BLOCK_STATE_HASH_TO_META_MAP.computeIfAbsent(blockState.getBlockType(), k -> new Int2ObjectOpenHashMap<>()).put(blockStateHashInt, metaInt);
                });
            });
            INITIALIZED = true;
        }
    }

    public static Function<Integer, BlockState> getMetaToBlockStateMapper(ItemType<?> itemType) {
        if (!INITIALIZED) {
            throw new IllegalStateException();
        }
        var map = ITEM_TYPE_TO_META_MAP.get(itemType);
        return map != null ? key -> {
            var blockState = map.get(key);
            if (blockState == null) {
                log.warn("Cannot find block state by meta: {}, item type: {}", key, itemType.getIdentifier());
                return itemType.getBlockType().getDefaultState();
            }
            return blockState;
        } : ($ -> itemType.getBlockType().getDefaultState());
    }

    public static Function<Integer, Integer> getBlockStateHashToMetaMapper(BlockType<?> blockType) {
        if (!INITIALIZED) {
            throw new IllegalStateException();
        }
        var map = BLOCK_STATE_HASH_TO_META_MAP.get(blockType);
        return map != null ? key -> {
            var meta = map.get(key);
            if (meta == null) {
                log.warn("Cannot find meta by block state hash: {}, block type: {}", key, blockType.getIdentifier());
                return 0;
            }
            return meta;
        } : ($ -> 0);
    }
}
