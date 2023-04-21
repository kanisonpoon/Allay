package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockMovingBlock extends Block {
    BlockType<BlockMovingBlock> TYPE = BlockTypeBuilder
            .builder(BlockMovingBlock.class)
            .vanillaBlock(VanillaBlockId.MOVING_BLOCK)
            .build();
}
