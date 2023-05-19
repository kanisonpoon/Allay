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
public interface ItemStoneBlockSlab2 extends ItemStack {
    ItemType<ItemStoneBlockSlab2> TYPE = ItemTypeBuilder
            .builder(ItemStoneBlockSlab2.class)
            .vanillaItem(VanillaItemId.STONE_BLOCK_SLAB2, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
