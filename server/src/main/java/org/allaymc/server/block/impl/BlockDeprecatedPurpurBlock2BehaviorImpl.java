package org.allaymc.server.block.impl;

import org.allaymc.api.block.interfaces.BlockDeprecatedPurpurBlock2Behavior;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.server.component.interfaces.ComponentProvider;

import java.util.List;

public class BlockDeprecatedPurpurBlock2BehaviorImpl extends BlockBehaviorImpl implements BlockDeprecatedPurpurBlock2Behavior {
    public BlockDeprecatedPurpurBlock2BehaviorImpl(
            List<ComponentProvider<? extends Component>> componentProviders) {
        super(componentProviders);
    }
}