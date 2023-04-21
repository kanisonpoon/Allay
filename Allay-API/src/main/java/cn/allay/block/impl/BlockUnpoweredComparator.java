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
public interface BlockUnpoweredComparator extends Block {
    BlockType<BlockUnpoweredComparator> TYPE = BlockTypeBuilder
            .builder(BlockUnpoweredComparator.class)
            .vanillaBlock(VanillaBlockId.UNPOWERED_COMPARATOR)
            .property(VanillaBlockPropertyTypes.DIRECTION,
                    VanillaBlockPropertyTypes.OUTPUT_LIT_BIT,
                    VanillaBlockPropertyTypes.OUTPUT_SUBTRACT_BIT)
            .build();
}
