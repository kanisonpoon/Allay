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
public interface BlockCoralFanHang2 extends Block {
    BlockType<BlockCoralFanHang2> TYPE = BlockTypeBuilder
            .builder(BlockCoralFanHang2.class)
            .vanillaBlock(VanillaBlockId.CORAL_FAN_HANG2)
            .property(VanillaBlockPropertyTypes.CORAL_DIRECTION,
                    VanillaBlockPropertyTypes.CORAL_HANG_TYPE_BIT,
                    VanillaBlockPropertyTypes.DEAD_BIT)
            .build();
}
