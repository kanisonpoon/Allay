package cn.allay.api.block.interfaces;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockBrownCarpetBehavior extends BlockBehavior {
    BlockType<BlockBrownCarpetBehavior> BROWN_CARPET_TYPE = BlockTypeBuilder
            .builder(BlockBrownCarpetBehavior.class)
            .vanillaBlock(VanillaBlockId.BROWN_CARPET)
            .build();
}