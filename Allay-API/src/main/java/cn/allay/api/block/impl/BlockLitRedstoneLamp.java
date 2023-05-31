package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.block.type.BlockTypeRegistry;
import cn.allay.api.data.VanillaBlockId;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockLitRedstoneLamp extends Block {
    BlockType<BlockLitRedstoneLamp> TYPE = BlockTypeBuilder
            .builder(BlockLitRedstoneLamp.class)
            .vanillaBlock(VanillaBlockId.LIT_REDSTONE_LAMP, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}