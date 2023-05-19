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
public interface ItemAcaciaFenceGate extends ItemStack {
    ItemType<ItemAcaciaFenceGate> TYPE = ItemTypeBuilder
            .builder(ItemAcaciaFenceGate.class)
            .vanillaItem(VanillaItemId.ACACIA_FENCE_GATE, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
