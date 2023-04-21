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
public interface BlockMangroveDoubleSlab extends Block {
    BlockType<BlockMangroveDoubleSlab> TYPE = BlockTypeBuilder
            .builder(BlockMangroveDoubleSlab.class)
            .vanillaBlock(VanillaBlockId.MANGROVE_DOUBLE_SLAB)
            .property(VanillaBlockPropertyTypes.TOP_SLOT_BIT)
            .build();
}
