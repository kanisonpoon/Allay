package org.allaymc.server.block.initializer.coral;

import org.allaymc.api.block.interfaces.coral.BlockDeadHornCoralBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockDeadHornCoralBehaviorInitializer {
  static void init() {
    BlockTypes.DEAD_HORN_CORAL_TYPE = BlockTypeBuilder
            .builder(BlockDeadHornCoralBehavior.class)
            .vanillaBlock(VanillaBlockId.DEAD_HORN_CORAL)
            .build();
  }
}