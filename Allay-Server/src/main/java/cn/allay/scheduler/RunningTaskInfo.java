package cn.allay.scheduler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/3/12 <br>
 * Allay Project <br>
 *
 * Represents a running task that contains some run presets for this task
 */
@Getter
public final class RunningTaskInfo {
    private final int delay;
    private final int period;
    private final boolean async;

    @Setter
    private long lastRunTick;
    @Setter
    private long nextRunTick;
    @Setter
    private boolean stop;

    @Builder
    public RunningTaskInfo(int delay, int period, boolean async) {
        this.delay = delay;
        this.period = period;
        this.async = async;
    }
}
