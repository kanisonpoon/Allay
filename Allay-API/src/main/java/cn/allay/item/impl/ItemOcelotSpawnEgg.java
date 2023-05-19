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
public interface ItemOcelotSpawnEgg extends ItemStack {
    ItemType<ItemOcelotSpawnEgg> TYPE = ItemTypeBuilder
            .builder(ItemOcelotSpawnEgg.class)
            .vanillaItem(VanillaItemId.OCELOT_SPAWN_EGG, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
