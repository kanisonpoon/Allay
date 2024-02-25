package org.allaymc.server.block.initializer.element;

import org.allaymc.api.block.interfaces.element.BlockElement12Behavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockElement12BehaviorInitializer {
  static void init() {
    BlockTypes.ELEMENT_12_TYPE = BlockTypeBuilder
            .builder(BlockElement12Behavior.class)
            .vanillaBlock(VanillaBlockId.ELEMENT_12)
            .build();
  }
}