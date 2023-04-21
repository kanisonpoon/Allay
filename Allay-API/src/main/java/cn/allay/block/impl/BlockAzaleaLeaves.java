package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.property.vanilla.VanillaBlockPropertyTypes;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockAzaleaLeaves extends Block {
    BlockType<BlockAzaleaLeaves> TYPE = BlockTypeBuilder
            .builder(BlockAzaleaLeaves.class)
            .vanillaBlock(VanillaBlockId.AZALEA_LEAVES)
            .property(VanillaBlockPropertyTypes.PERSISTENT_BIT,
                    VanillaBlockPropertyTypes.UPDATE_BIT)
            .build();
}
