package org.allaymc.server.loottable.roll;

import lombok.AllArgsConstructor;
import org.allaymc.server.loottable.Rolls;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author daoge_cmd
 */
@AllArgsConstructor
public class RangedRolls implements Rolls {
    protected int min, max;

    @Override
    public int getRolls() {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
