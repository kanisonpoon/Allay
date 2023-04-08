package cn.allay.block;

import cn.allay.block.property.type.BlockPropertyType;
import cn.allay.block.type.BlockType;
import cn.allay.identifier.Identifier;
import cn.allay.math.position.PosRO;

import java.util.Set;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/3/19 <br>
 * Allay Project <br>
 */
public interface Block extends PosRO<Integer> {
    BlockType<? extends Block> getBlockType();
}
