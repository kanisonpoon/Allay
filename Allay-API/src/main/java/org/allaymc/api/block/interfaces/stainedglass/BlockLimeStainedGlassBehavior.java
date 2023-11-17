package org.allaymc.api.block.interfaces.stainedglass;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockLimeStainedGlassBehavior extends BlockBehavior {
  BlockType<BlockLimeStainedGlassBehavior> LIME_STAINED_GLASS_TYPE = BlockTypeBuilder
          .builder(BlockLimeStainedGlassBehavior.class)
          .vanillaBlock(VanillaBlockId.LIME_STAINED_GLASS)
          .build();
}