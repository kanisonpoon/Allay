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
public interface BlockMangroveStandingSign extends Block {
    BlockType<BlockMangroveStandingSign> TYPE = BlockTypeBuilder
            .builder(BlockMangroveStandingSign.class)
            .vanillaBlock(VanillaBlockId.MANGROVE_STANDING_SIGN)
            .property(VanillaBlockPropertyTypes.GROUND_SIGN_DIRECTION)
            .build();
}
