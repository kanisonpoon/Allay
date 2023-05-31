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
public interface BlockColoredTorchBp extends Block {
    BlockType<BlockColoredTorchBp> TYPE = BlockTypeBuilder
            .builder(BlockColoredTorchBp.class)
            .vanillaBlock(VanillaBlockId.COLORED_TORCH_BP, true)
            .withProperties(VanillaBlockPropertyTypes.COLOR_BIT,
                    VanillaBlockPropertyTypes.TORCH_FACING_DIRECTION)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}