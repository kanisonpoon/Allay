package cn.allay.api.item.interfaces;

import cn.allay.api.component.interfaces.ComponentProvider;
import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.component.base.ItemBaseComponentImpl;
import cn.allay.api.item.init.ItemStackInitInfo;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemGraniteStairsStack extends ItemStack {
    ItemType<ItemGraniteStairsStack> GRANITE_STAIRS_TYPE = ItemTypeBuilder
            .builder(ItemGraniteStairsStack.class)
            .vanillaItem(VanillaItemId.GRANITE_STAIRS)
            .addComponent(ComponentProvider.of(initInfo -> new ItemBaseComponentImpl<>((ItemStackInitInfo<?>) initInfo), ItemBaseComponentImpl.class))
            .build();
}