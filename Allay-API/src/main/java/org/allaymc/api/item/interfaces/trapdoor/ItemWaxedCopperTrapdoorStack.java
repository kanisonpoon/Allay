package org.allaymc.api.item.interfaces.trapdoor;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemWaxedCopperTrapdoorStack extends ItemStack {
  ItemType<ItemWaxedCopperTrapdoorStack> WAXED_COPPER_TRAPDOOR_TYPE = ItemTypeBuilder
          .builder(ItemWaxedCopperTrapdoorStack.class)
          .vanillaItem(VanillaItemId.WAXED_COPPER_TRAPDOOR)
          .build();
}
