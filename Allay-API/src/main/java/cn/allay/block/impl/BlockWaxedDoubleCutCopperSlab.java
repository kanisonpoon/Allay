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
public interface BlockWaxedDoubleCutCopperSlab extends Block {
    BlockType<BlockWaxedDoubleCutCopperSlab> TYPE = BlockTypeBuilder
            .builder(BlockWaxedDoubleCutCopperSlab.class)
            .vanillaBlock(VanillaBlockId.WAXED_DOUBLE_CUT_COPPER_SLAB)
            .property(VanillaBlockPropertyTypes.TOP_SLOT_BIT)
            .build();
}
