package cn.allay.api.item.interfaces;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemGoldOreStack extends ItemStack {
    ItemType<ItemGoldOreStack> GOLD_ORE_TYPE = ItemTypeBuilder
            .builder(ItemGoldOreStack.class)
            .vanillaItem(VanillaItemId.GOLD_ORE)
            .build();
}