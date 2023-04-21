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
public interface BlockPolishedBasalt extends Block {
    BlockType<BlockPolishedBasalt> TYPE = BlockTypeBuilder
            .builder(BlockPolishedBasalt.class)
            .vanillaBlock(VanillaBlockId.POLISHED_BASALT)
            .property(VanillaBlockPropertyTypes.PILLAR_AXIS)
            .build();
}
