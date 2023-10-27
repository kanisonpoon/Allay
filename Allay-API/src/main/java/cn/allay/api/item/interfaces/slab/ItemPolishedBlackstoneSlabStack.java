package cn.allay.api.item.interfaces.slab;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemPolishedBlackstoneSlabStack extends ItemStack {
  ItemType<ItemPolishedBlackstoneSlabStack> POLISHED_BLACKSTONE_SLAB_TYPE = ItemTypeBuilder
          .builder(ItemPolishedBlackstoneSlabStack.class)
          .vanillaItem(VanillaItemId.POLISHED_BLACKSTONE_SLAB)
          .build();
}