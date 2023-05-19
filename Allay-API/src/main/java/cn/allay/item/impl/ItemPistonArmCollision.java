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
public interface ItemPistonArmCollision extends ItemStack {
    ItemType<ItemPistonArmCollision> TYPE = ItemTypeBuilder
            .builder(ItemPistonArmCollision.class)
            .vanillaItem(VanillaItemId.PISTON_ARM_COLLISION, true)
            .addBasicComponents()
            .build().register(ItemTypeRegistry.getRegistry());
}
