package org.allaymc.server.block.impl;

import lombok.experimental.Delegate;
import org.allaymc.api.block.component.BlockEntityHolderComponent;
import org.allaymc.api.block.interfaces.BlockFurnaceBehavior;
import org.allaymc.api.blockentity.interfaces.BlockEntityFurnace;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockFurnaceBehaviorImpl extends BlockBehaviorImpl implements BlockFurnaceBehavior {

    @Delegate
    protected BlockEntityHolderComponent<BlockEntityFurnace> blockEntityHolderComponent;

    public BlockFurnaceBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}
