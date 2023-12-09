package org.allaymc.api.item.interfaces;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemDioriteStack extends ItemStack {
  ItemType<ItemDioriteStack> DIORITE_TYPE = ItemTypeBuilder
          .builder(ItemDioriteStack.class)
          .vanillaItem(VanillaItemId.DIORITE)
          .build();
}
