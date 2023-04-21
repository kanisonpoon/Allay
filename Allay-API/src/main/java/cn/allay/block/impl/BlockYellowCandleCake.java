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
public interface BlockYellowCandleCake extends Block {
    BlockType<BlockYellowCandleCake> TYPE = BlockTypeBuilder
            .builder(BlockYellowCandleCake.class)
            .vanillaBlock(VanillaBlockId.YELLOW_CANDLE_CAKE)
            .property(VanillaBlockPropertyTypes.LIT)
            .build();
}
