package org.allaymc.api.block.interfaces.element;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockElement94Behavior extends BlockBehavior {
  BlockType<BlockElement94Behavior> ELEMENT_94_TYPE = BlockTypeBuilder
          .builder(BlockElement94Behavior.class)
          .vanillaBlock(VanillaBlockId.ELEMENT_94)
          .build();
}