package cn.allay.api.block.interfaces;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;
import cn.allay.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockGreenCandleCakeBehavior extends BlockBehavior {
    BlockType<BlockGreenCandleCakeBehavior> GREEN_CANDLE_CAKE_TYPE = BlockTypeBuilder
            .builder(BlockGreenCandleCakeBehavior.class)
            .vanillaBlock(VanillaBlockId.GREEN_CANDLE_CAKE)
            .setProperties(VanillaBlockPropertyTypes.LIT)
            .build();
}