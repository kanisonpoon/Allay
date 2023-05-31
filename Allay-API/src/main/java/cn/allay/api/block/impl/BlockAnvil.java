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
public interface BlockAnvil extends Block {
    BlockType<BlockAnvil> TYPE = BlockTypeBuilder
            .builder(BlockAnvil.class)
            .vanillaBlock(VanillaBlockId.ANVIL, true)
            .withProperties(VanillaBlockPropertyTypes.DAMAGE,
                    VanillaBlockPropertyTypes.DIRECTION)
            .addBasicComponents()
            .build().register(BlockTypeRegistry.getRegistry());
}