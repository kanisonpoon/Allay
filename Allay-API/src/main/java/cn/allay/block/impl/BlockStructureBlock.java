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
public interface BlockStructureBlock extends Block {
    BlockType<BlockStructureBlock> TYPE = BlockTypeBuilder
            .builder(BlockStructureBlock.class)
            .vanillaBlock(VanillaBlockId.STRUCTURE_BLOCK)
            .property(VanillaBlockPropertyTypes.STRUCTURE_BLOCK_TYPE)
            .build();
}
