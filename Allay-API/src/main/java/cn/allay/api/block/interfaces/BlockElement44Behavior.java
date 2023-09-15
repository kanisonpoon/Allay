package cn.allay.api.block.interfaces;

import cn.allay.api.block.BlockBehavior;
import cn.allay.api.block.type.BlockType;
import cn.allay.api.block.type.BlockTypeBuilder;
import cn.allay.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement44Behavior extends BlockBehavior {
    BlockType<BlockElement44Behavior> ELEMENT_44_TYPE = BlockTypeBuilder
            .builder(BlockElement44Behavior.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_44)
            .build();
}