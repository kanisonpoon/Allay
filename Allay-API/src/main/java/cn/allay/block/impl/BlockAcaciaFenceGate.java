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
public interface BlockAcaciaFenceGate extends Block {
    BlockType<BlockAcaciaFenceGate> TYPE = BlockTypeBuilder
            .builder(BlockAcaciaFenceGate.class)
            .vanillaBlock(VanillaBlockId.ACACIA_FENCE_GATE)
            .property(VanillaBlockPropertyTypes.DIRECTION,
                    VanillaBlockPropertyTypes.IN_WALL_BIT,
                    VanillaBlockPropertyTypes.OPEN_BIT)
            .build();
}
