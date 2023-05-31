package cn.allay.api.block.palette;

import cn.allay.api.block.property.type.BlockPropertyType;
import cn.allay.api.data.VanillaBlockId;

import java.util.List;
import java.util.Map;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/4/8 <br>
 * Allay Project <br>
 */
public record VanillaBlockPaletteDataEntry(
        VanillaBlockId blockId,
        List<BlockPropertyType<?>> propertyTypes,
        Map<List<BlockPropertyType.BlockPropertyValue<?, ?, ?>>, Integer> runtimeIdMap,
        int version,
        int index) {
}