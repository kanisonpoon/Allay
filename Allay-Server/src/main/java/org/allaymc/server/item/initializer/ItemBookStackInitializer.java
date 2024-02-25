package org.allaymc.server.item.initializer;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.ItemBookStack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemBookStackInitializer {
  static void init() {
    ItemTypes.BOOK_TYPE = ItemTypeBuilder
            .builder(ItemBookStack.class)
            .vanillaItem(VanillaItemId.BOOK)
            .build();
  }
}