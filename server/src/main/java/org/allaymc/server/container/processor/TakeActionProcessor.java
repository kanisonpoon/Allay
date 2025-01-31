package org.allaymc.server.container.processor;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.TakeAction;

/**
 * @author daoge_cmd
 */
public class TakeActionProcessor extends TransferItemActionProcessor<TakeAction> {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.TAKE;
    }
}
