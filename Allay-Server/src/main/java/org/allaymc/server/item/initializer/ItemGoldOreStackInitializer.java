package org.allaymc.server.item.initializer;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.ItemGoldOreStack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemGoldOreStackInitializer {
  static void init() {
    ItemTypes.GOLD_ORE_TYPE = ItemTypeBuilder
            .builder(ItemGoldOreStack.class)
            .vanillaItem(VanillaItemId.GOLD_ORE)
            .build();
  }
}