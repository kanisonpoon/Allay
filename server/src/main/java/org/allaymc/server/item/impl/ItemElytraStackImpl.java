package org.allaymc.server.item.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.item.component.ItemArmorBaseComponent;
import org.allaymc.api.item.initinfo.ItemStackInitInfo;
import org.allaymc.api.item.interfaces.ItemElytraStack;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class ItemElytraStackImpl extends ItemStackImpl implements ItemElytraStack {
    @Delegate
    protected ItemArmorBaseComponent armorBaseComponent;

    public ItemElytraStackImpl(ItemStackInitInfo initInfo,
                               List<ComponentProvider<? extends Component>> componentProviders) {
        super(initInfo, componentProviders);
        ;
    }
}