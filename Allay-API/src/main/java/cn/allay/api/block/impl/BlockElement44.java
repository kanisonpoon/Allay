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
public interface BlockElement44 extends Block {
    BlockType<BlockElement44> TYPE = BlockTypeBuilder
            .builder(BlockElement44.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_44, true)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}