package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockBigDripleafBehavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockBigDripleafBehaviorImpl extends BlockBehaviorImpl implements BlockBigDripleafBehavior {
    public BlockBigDripleafBehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}