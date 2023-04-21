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
public interface BlockBambooPressurePlate extends Block {
    BlockType<BlockBambooPressurePlate> TYPE = BlockTypeBuilder
            .builder(BlockBambooPressurePlate.class)
            .vanillaBlock(VanillaBlockId.BAMBOO_PRESSURE_PLATE)
            .property(VanillaBlockPropertyTypes.REDSTONE_SIGNAL)
            .build();
}
