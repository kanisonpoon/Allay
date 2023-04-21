package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockElement13 extends Block {
    BlockType<BlockElement13> TYPE = BlockTypeBuilder
            .builder(BlockElement13.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_13)
            .build();
}
