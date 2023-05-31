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
public interface BlockIronBars extends Block {
    BlockType<BlockIronBars> TYPE = BlockTypeBuilder
            .builder(BlockIronBars.class)
            .vanillaBlock(VanillaBlockId.IRON_BARS, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}