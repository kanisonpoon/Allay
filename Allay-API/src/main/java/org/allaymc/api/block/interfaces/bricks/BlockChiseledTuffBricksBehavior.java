package org.allaymc.api.block.interfaces.bricks;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockChiseledTuffBricksBehavior extends BlockBehavior {
  BlockType<BlockChiseledTuffBricksBehavior> CHISELED_TUFF_BRICKS_TYPE = BlockTypeBuilder
          .builder(BlockChiseledTuffBricksBehavior.class)
          .vanillaBlock(VanillaBlockId.CHISELED_TUFF_BRICKS)
          .build();
}
