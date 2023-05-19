package cn.allay.item.impl;

import cn.allay.item.ItemStack;
import cn.allay.item.data.VanillaItemId;
import cn.allay.item.type.ItemType;
import cn.allay.item.type.ItemTypeBuilder;
import cn.allay.item.type.ItemTypeRegistry;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemLeatherHelmet extends ItemStack {
    ItemType<ItemLeatherHelmet> TYPE = ItemTypeBuilder
            .builder(ItemLeatherHelmet.class)
            .vanillaItem(VanillaItemId.LEATHER_HELMET, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
