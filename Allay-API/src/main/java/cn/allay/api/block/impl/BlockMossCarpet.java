package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.block.type.BlockTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockMossCarpet extends Block {
    BlockType<BlockMossCarpet> TYPE = BlockTypeBuilder
            .builder(BlockMossCarpet.class)
            .vanillaBlock(VanillaBlockId.MOSS_CARPET, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}