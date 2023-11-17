package org.allaymc.api.item.interfaces.button;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemSpruceButtonStack extends ItemStack {
  ItemType<ItemSpruceButtonStack> SPRUCE_BUTTON_TYPE = ItemTypeBuilder
          .builder(ItemSpruceButtonStack.class)
          .vanillaItem(VanillaItemId.SPRUCE_BUTTON)
          .build();
}