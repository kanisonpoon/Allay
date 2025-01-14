package org.allaymc.api.item.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.allaymc.api.item.tag.ItemTag;

import static org.allaymc.api.item.tag.ItemTags.*;

/**
 * @author daoge_cmd
 */
@Getter
@AllArgsConstructor
public enum ArmorTier {
    LEATHER(LEATHER_TIER),
    IRON(IRON_TIER),
    CHAIN(CHAINMAIL_TIER),
    GOLD(GOLDEN_TIER),
    DIAMOND(DIAMOND_TIER),
    NETHERITE(NETHERITE_TIER);

    public static final ArmorTier[] ORDER_OF_QUALITY = {
            NETHERITE, DIAMOND, GOLD, CHAIN, IRON, LEATHER
    };

    private final ItemTag itemTag;

    public boolean isBetterThan(ArmorTier tier) {
        return this.ordinal() > tier.ordinal();
    }
}
