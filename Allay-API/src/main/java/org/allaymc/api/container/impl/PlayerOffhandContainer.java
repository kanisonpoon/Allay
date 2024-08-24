package org.allaymc.api.container.impl;

import org.allaymc.api.container.BaseContainer;
import org.allaymc.api.container.FixedContainerId;
import org.allaymc.api.container.FullContainerType;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.item.ItemStack;
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket;

import java.util.function.Supplier;

/**
 * Allay Project 2023/7/22
 *
 * @author daoge_cmd
 */
public class PlayerOffhandContainer extends BaseContainer {

    public static final int OFFHAND_SLOT = 0;

    protected Supplier<EntityPlayer> playerSupplier;

    public PlayerOffhandContainer(Supplier<EntityPlayer> playerSupplier) {
        super(FullContainerType.OFFHAND);
        this.playerSupplier = playerSupplier;
        addOnSlotChangeListener(0, this::onOffhandChange);
    }

    protected void onOffhandChange(ItemStack newItemStack) {
        playerSupplier.get().sendPacketToViewers(buildEquipmentPacket());
    }

    public void sendEquipmentPacketTo(EntityPlayer player) {
        player.sendPacket(buildEquipmentPacket());
    }

    public ItemStack getOffhand() {
        return getItemStack(OFFHAND_SLOT);
    }

    public void setOffhand(ItemStack itemStack) {
        setItemStack(OFFHAND_SLOT, itemStack);
    }

    protected MobEquipmentPacket buildEquipmentPacket() {
        var pk = new MobEquipmentPacket();
        pk.setRuntimeEntityId(playerSupplier.get().getRuntimeId());
        pk.setContainerId(FixedContainerId.OFFHAND);
        // Network slot index for offhand is 1
        // See FullContainerType.OFFHAND
        // And hotbar slot is unused
        pk.setInventorySlot(1);
        pk.setItem(getOffhand().toNetworkItemData());
        return pk;
    }
}
