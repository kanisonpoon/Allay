package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.block.type.BlockTypeRegistry;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockAzaleaLeaves extends Block {
    BlockType<BlockAzaleaLeaves> TYPE = BlockTypeBuilder
            .builder(BlockAzaleaLeaves.class)
            .vanillaBlock(VanillaBlockId.AZALEA_LEAVES, true)
            .withProperties(VanillaBlockPropertyTypes.PERSISTENT_BIT,
                    VanillaBlockPropertyTypes.UPDATE_BIT)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}