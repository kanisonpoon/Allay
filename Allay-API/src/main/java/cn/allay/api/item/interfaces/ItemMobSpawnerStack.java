package cn.allay.api.item.interfaces;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemMobSpawnerStack extends ItemStack {
    ItemType<ItemMobSpawnerStack> MOB_SPAWNER_TYPE = ItemTypeBuilder
            .builder(ItemMobSpawnerStack.class)
            .vanillaItem(VanillaItemId.MOB_SPAWNER)
            .build();
}