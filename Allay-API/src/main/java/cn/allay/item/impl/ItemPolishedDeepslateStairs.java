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
public interface ItemPolishedDeepslateStairs extends ItemStack {
    ItemType<ItemPolishedDeepslateStairs> TYPE = ItemTypeBuilder
            .builder(ItemPolishedDeepslateStairs.class)
            .vanillaItem(VanillaItemId.POLISHED_DEEPSLATE_STAIRS, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
