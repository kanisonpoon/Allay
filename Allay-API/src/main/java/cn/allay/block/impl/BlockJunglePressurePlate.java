package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.property.vanilla.VanillaBlockPropertyTypes;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockJunglePressurePlate extends Block {
    BlockType<BlockJunglePressurePlate> TYPE = BlockTypeBuilder
            .builder(BlockJunglePressurePlate.class)
            .vanillaBlock(VanillaBlockId.JUNGLE_PRESSURE_PLATE)
            .property(VanillaBlockPropertyTypes.REDSTONE_SIGNAL)
            .build();
}
