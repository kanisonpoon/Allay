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
public interface BlockNetheriteBlock extends Block {
    BlockType<BlockNetheriteBlock> TYPE = BlockTypeBuilder
            .builder(BlockNetheriteBlock.class)
            .vanillaBlock(VanillaBlockId.NETHERITE_BLOCK, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}