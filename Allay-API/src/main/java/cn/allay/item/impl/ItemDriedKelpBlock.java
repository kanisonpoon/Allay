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
public interface ItemDriedKelpBlock extends ItemStack {
    ItemType<ItemDriedKelpBlock> TYPE = ItemTypeBuilder
            .builder(ItemDriedKelpBlock.class)
            .vanillaItem(VanillaItemId.DRIED_KELP_BLOCK, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
