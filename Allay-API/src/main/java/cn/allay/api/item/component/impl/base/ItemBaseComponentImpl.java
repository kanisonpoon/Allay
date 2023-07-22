package cn.allay.api.item.component.impl.base;

import cn.allay.api.block.type.BlockState;
import cn.allay.api.component.annotation.Impl;
import cn.allay.api.identifier.Identifier;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.component.ItemComponentImpl;
import cn.allay.api.item.type.ItemStackInitInfo;
import cn.allay.api.item.type.ItemType;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.jetbrains.annotations.Nullable;

/**
 * Allay Project 2023/5/19
 *
 * @author daoge_cmd
 */
public class ItemBaseComponentImpl implements ItemBaseComponent, ItemComponentImpl {

    public static final Identifier IDENTIFIER = new Identifier("minecraft:item_base_component");

    protected ItemType<? extends ItemStack> itemType;
    protected int count;
    protected int damage;
    @Nullable
    protected NbtMap nbt;
    @Nullable
    protected BlockState blockState;

    public ItemBaseComponentImpl(ItemType<? extends ItemStack> itemType, ItemStackInitInfo initInfo) {
        this.itemType = itemType;
        this.count = initInfo.count();
        this.damage = initInfo.damage();
        this.nbt = initInfo.nbt();
        this.blockState = initInfo.blockState();
    }

    @Override
    @Impl
    public ItemType<? extends ItemStack> getItemType() {
        return itemType;
    }

    @Override
    @Impl
    public int getCount() {
        return count;
    }

    @Override
    @Impl
    public void setCount(int count) {
        if (count < 0) throw new IllegalArgumentException("count cannot be negative");
        this.count = count;
    }

    @Override
    @Impl
    public int getDamage() {
        return damage;
    }

    @Override
    @Impl
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Nullable
    @Override
    @Impl
    public NbtMap getNbt() {
        return nbt;
    }

    @Override
    @Impl
    public void setNbt(NbtMap nbt) {
        this.nbt = nbt;
    }

    @Override
    @Impl
    @Nullable
    public BlockState getBlockState() {
        return blockState;
    }

    @Override
    @Impl
    public void setBlockState(@Nullable BlockState blockState) {
        this.blockState = blockState;
    }

    @Override
    @Impl
    public ItemData toNetworkItemData() {
        return ItemData
                .builder()
                .definition(new SimpleItemDefinition(itemType.getIdentifier().toString(), itemType.getRuntimeId(), false))
                .blockDefinition(blockState != null ? blockState.toNetworkBlockDefinition() : () -> 0)
                .count(count)
                .damage(damage)
                .tag(nbt)
                .build();
    }

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }
}
