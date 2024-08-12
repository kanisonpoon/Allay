package org.allaymc.api.entity.component;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.allaymc.api.entity.component.common.EntityBaseComponent;

import java.util.List;

/**
 * Allay Project 2024/8/12
 *
 * @author daoge_cmd
 */
public interface EntityXpOrbBaseComponent extends EntityBaseComponent {
    int getPickupDelay();

    void setPickupDelay(int delay);

    default boolean canBePicked() {
        return getPickupDelay() == 0;
    }

    short getAge();

    void setAge(short age);

    int getExperienceValue();

    void setExperienceValue(int experienceValue);
}