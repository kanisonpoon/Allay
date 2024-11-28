package org.allaymc.server.block.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.block.component.BlockWoodBaseComponent;
import org.allaymc.api.block.interfaces.BlockWoodBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockWoodBehaviorImpl extends BlockBehaviorImpl implements BlockWoodBehavior {
    public BlockWoodBehaviorImpl(List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }

    @Delegate
    @Override
    protected BlockWoodBaseComponent getBaseComponent() {
        return (BlockWoodBaseComponent) super.getBaseComponent();
    }
}