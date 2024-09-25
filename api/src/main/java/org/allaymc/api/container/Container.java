package org.allaymc.api.container;

import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.interfaces.ItemAirStack;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents a container.
 *
 * @author daoge_cmd
 */
public interface Container {

    /**
     * Get the container type.
     *
     * @return the container type.
     */
    FullContainerType<?> getContainerType();

    /**
     * Called when the container is opened.
     *
     * @param viewer the viewer.
     */
    @ApiStatus.OverrideOnly
    void onOpen(ContainerViewer viewer);

    /**
     * Called when the container is closed.
     *
     * @param viewer the viewer.
     */
    @ApiStatus.OverrideOnly
    void onClose(ContainerViewer viewer);

    /**
     * Add a listener to be called when the container is opened.
     *
     * @param listener the listener.
     */
    void addOnOpenListener(Consumer<ContainerViewer> listener);

    /**
     * Remove a listener to be called when the container is opened.
     *
     * @param listener the listener.
     */
    void removeOnOpenListener(Consumer<ContainerViewer> listener);

    /**
     * Add a listener to be called when the container is closed.
     *
     * @param listener the listener.
     */
    void addOnCloseListener(Consumer<ContainerViewer> listener);

    /**
     * Remove a listener to be called when the container is closed.
     *
     * @param listener the listener.
     */
    void removeOnCloseListener(Consumer<ContainerViewer> listener);

    /**
     * Add a listener to be called when the slot is changed.
     *
     * @param slot the slot.
     * @param listener the listener.
     */
    void addOnSlotChangeListener(int slot, Consumer<ItemStack> listener);

    /**
     * Remove a listener to be called when the slot is changed.
     *
     * @param slot the slot.
     * @param listener the listener.
     */
    void removeOnSlotChangeListener(int slot, Consumer<ItemStack> listener);

    /**
     * Get the slot type of the slot.
     *
     * @param slot the slot.
     * @return the slot type.
     */
    default ContainerSlotType getSlotType(int slot) {
        return getContainerType().getSlotType(slot);
    }

    /**
     * Get the viewers of the container.
     *
     * @return the viewers.
     */
    Map<Byte, ContainerViewer> getViewers();

    /**
     * Get the item stack of the slot.
     *
     * @param slot the slot.
     * @return the item stack.
     */
    ItemStack getItemStack(int slot);

    /**
     * Check if the slot is empty.
     *
     * @param slot the slot.
     * @return {@code true} if the slot is empty, otherwise {@code false}.
     */
    default boolean isEmpty(int slot) {
        return getItemStack(slot) == ItemAirStack.AIR_STACK;
    }

    /**
     * Check if the container is empty.
     *
     * @return {@code true} if the container is empty, otherwise {@code false}.
     */
    default boolean isEmpty() {
        for (ItemStack itemStack : getItemStackArray()) {
            if (itemStack != ItemAirStack.AIR_STACK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the item stacks of the container.
     *
     * @return the item stacks.
     */
    @UnmodifiableView
    List<ItemStack> getItemStacks();

    /**
     * Get the item stack array of the container.
     *
     * @return the item stack array.
     */
    @UnmodifiableView
    ItemStack[] getItemStackArray();

    /**
     * Get the network item data of the container.
     *
     * @return the network item data.
     */
    List<ItemData> toNetworkItemData();

    /**
     * Set the item stack of the slot.
     *
     * @param slot the slot.
     * @param itemStack the item stack.
     */
    void setItemStack(int slot, ItemStack itemStack);

    /**
     * Clear the slot.
     *
     * @param slot the slot.
     */
    default void clearSlot(int slot) {
        setItemStack(slot, ItemAirStack.AIR_STACK);
    }

    /**
     * Clear all slots.
     */
    default void clearAllSlots() {
        for (int slot = 0; slot < getItemStackArray().length; slot++) {
            clearSlot(slot);
        }
    }

    /**
     * Add a viewer.
     *
     * @param viewer the viewer.
     */
    void addViewer(ContainerViewer viewer);

    /**
     * Remove a viewer.
     *
     * @param viewer the viewer.
     */
    void removeViewer(ContainerViewer viewer);

    /**
     * Remove a viewer by the viewer id.
     *
     * @param viewerId the viewer id.
     */
    ContainerViewer removeViewer(byte viewerId);

    /**
     * Remove all viewers.
     */
    default void removeAllViewers() {
        getViewers().values().forEach(this::removeViewer);
    }

    /**
     * Notify the viewers that item in the slot is changed.
     * <p>
     * This method should be called if you modified the item in a slot,
     * and you want to send update packet to the viewers.
     *
     * @param slot the slot.
     */
    void notifySlotChange(int slot);

    /**
     * Notify the viewers that all slots are changed.
     * <p>
     * This method should be called if you modified the items in all slots,
     * and you want to send update packet to the viewers.
     */
    default void notifyAllSlotsChange() {
        for (int slot = 0; slot < getItemStackArray().length; slot++) {
            notifySlotChange(slot);
        }
    }

    /**
     * Save the container to NBT.
     *
     * @return the NBT list.
     */
    List<NbtMap> saveNBT();

    /**
     * Load the container from NBT.
     *
     * @param nbtList the NBT list.
     */
    void loadNBT(List<NbtMap> nbtList);

    /**
     * Get the slot index from the network slot index.
     *
     * @param index the network slot index.
     * @return the slot index.
     */
    default int toNetworkSlotIndex(int index) {
        return getContainerType().networkSlotIndexMapper().inverse().get(index);
    }

    /**
     * Get the network slot index from the slot index.
     *
     * @param index the slot index.
     * @return the network slot index.
     */
    default int fromNetworkSlotIndex(int index) {
        return getContainerType().networkSlotIndexMapper().get(index);
    }

    /**
     * Send the contents of the container to the viewer.
     *
     * @param viewer the viewer.
     */
    default void sendContents(ContainerViewer viewer) {
        viewer.sendContents(this);
    }

    /**
     * Send content of a specific slot to the viewer.
     *
     * @param viewer the viewer.
     * @param slot the slot.
     */
    default void sendContent(ContainerViewer viewer, int slot) {
        viewer.sendContent(this, slot);
    }

    /**
     * Try to add an item to the specified slot range of this container.
     *
     * @param itemStack the item stack.
     * @param minSlotIndex the min slot index.
     * @param maxSlotIndex the max slot index.
     * @return the slot index where the item is added, or {@code -1} if the item is failed to be added.
     */
    default int tryAddItem(ItemStack itemStack, int minSlotIndex, int maxSlotIndex) {
        if (minSlotIndex > maxSlotIndex) {
            throw new IllegalArgumentException("minSlotIndex > maxSlotIndex");
        }
        if (minSlotIndex < 0) {
            throw new IllegalArgumentException("minSlotIndex is less than 0");
        }
        ItemStack[] itemStacks = getItemStackArray();
        if (minSlotIndex > itemStacks.length - 1 || maxSlotIndex > itemStacks.length - 1) {
            throw new IllegalArgumentException("minSlotIndex or maxSlotIndex is out of range");
        }
        var minEmptySlot = -1;
        // Find out the min empty slot
        for (int index = minSlotIndex; index <= maxSlotIndex; index++) {
            if (itemStacks[index] == ItemAirStack.AIR_STACK) {
                minEmptySlot = index;
                break;
            }
        }
        // Firstly, try to merge with other item stack
        for (int index = minSlotIndex; index <= maxSlotIndex; index++) {
            var content = itemStacks[index];
            if (!content.isFull() && content.canMerge(itemStack, true)) {
                if (content.getCount() + itemStack.getCount() <= content.getItemData().maxStackSize()) {
                    content.setCount(content.getCount() + itemStack.getCount());
                    itemStack.setCount(0);
                } else {
                    int count = itemStack.getCount();
                    int completion = content.getItemData().maxStackSize() - content.getCount();
                    itemStack.setCount(count - completion);
                    content.setCount(content.getItemData().maxStackSize());
                }
                notifySlotChange(index);
                if (itemStack.getCount() == 0) {
                    return index;
                }
            }
        }
        // Secondly, put the item on an empty slot (if exists)
        if (minEmptySlot != -1) {
            setItemStack(minEmptySlot, itemStack.copy());
            itemStack.setCount(0);
            return minEmptySlot;
        }
        return -1;
    }

    /**
     * Try to add an item to the container.
     *
     * @param itemStack the item stack.
     * @return the slot index where the item is added, or {@code -1} if the item is failed to be added.
     */
    default int tryAddItem(ItemStack itemStack) {
        var array = getItemStackArray();
        return tryAddItem(itemStack, 0, array.length - 1);
    }

    /**
     * Send a container data to the viewers.
     *
     * @param property the property.
     * @param value the value.
     */
    default void sendContainerData(int property, int value) {
        getViewers().forEach((id, viewer) -> viewer.sendContainerData(id, property, value));
    }
}