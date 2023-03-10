package cn.allay.math.location;

import cn.allay.level.Level;
import cn.allay.math.position.Position;

import java.util.Objects;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/3/11 <br>
 * Allay Project <br>
 */
public interface Location<T extends Number> extends ReadOnlyLocation<T>, Position<T> {

    static <T extends Number> Location<T> of(T x, T y, T z, Level level) {
        return of(x, y, z, 0, 0, level);
    }

    static <T extends Number> Location<T> of(T x, T y, T z, double yaw, double pitch, Level level) {
        return of(x, y, z, yaw, 0d, pitch, level);
    }

    static <T extends Number> Location<T> of(T x, T y, T z, double yaw, double headYaw, double pitch, Level level) {
        Objects.requireNonNull(level);
        return new ImplLocation<>(x, y, z, yaw, headYaw, pitch, level);
    }

    void setPitch(double value);

    void setYaw(double value);

    void setHeadYaw(double value);
}
