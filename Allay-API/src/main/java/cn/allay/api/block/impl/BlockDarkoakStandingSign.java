package cn.allay.api.block.impl;

import cn.allay.api.block.Block;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.block.type.BlockTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockDarkoakStandingSign extends Block {
    BlockType<BlockDarkoakStandingSign> TYPE = BlockTypeBuilder
            .builder(BlockDarkoakStandingSign.class)
            .vanillaBlock(VanillaBlockId.DARKOAK_STANDING_SIGN, true)
            .withProperties(VanillaBlockPropertyTypes.GROUND_SIGN_DIRECTION)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}