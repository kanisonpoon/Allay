package cn.allay.math.vector;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/3/4 <br>
 * Allay Project <br>
 */
public interface ReadOnlyVector3<T extends Number> {
    static <T extends Number> ReadOnlyVector3<T> of(T x, T y, T z) {
        return new ImplReadOnlyVector3<>(x, y, z);
    }

    T getX();

    T getY();

    T getZ();
}
