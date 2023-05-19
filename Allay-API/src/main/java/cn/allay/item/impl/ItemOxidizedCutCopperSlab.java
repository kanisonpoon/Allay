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
public interface ItemOxidizedCutCopperSlab extends ItemStack {
    ItemType<ItemOxidizedCutCopperSlab> TYPE = ItemTypeBuilder
            .builder(ItemOxidizedCutCopperSlab.class)
            .vanillaItem(VanillaItemId.OXIDIZED_CUT_COPPER_SLAB, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
