package org.allaymc.api.block.interfaces.element;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement55Behavior extends BlockBehavior {
  BlockType<BlockElement55Behavior> ELEMENT_55_TYPE = BlockTypeBuilder
          .builder(BlockElement55Behavior.class)
          .vanillaBlock(VanillaBlockId.ELEMENT_55)
          .build();
}