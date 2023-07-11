/*
 * This file is part of Math, licensed under the MIT License (MIT).
 *
 * Copyright (c) Flow Powered <https://github.com/flow>
 * Copyright (c) SpongePowered <https://github.com/SpongePowered>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cn.allay.api.math.vector;

import cn.allay.api.math.GenericMath;

import java.util.Arrays;

public class VecNf implements Vec, Comparable<VecNf>, Cloneable {
    public static VecNf ZERO_2 = new ImmutableZeroVecN(0, 0);
    public static VecNf ZERO_3 = new ImmutableZeroVecN(0, 0, 0);
    public static VecNf ZERO_4 = new ImmutableZeroVecN(0, 0, 0, 0);
    private final float[] vec;

    public VecNf(final int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        vec = new float[size];
    }

    public VecNf(final Vec2f v) {
        this(v.x(), v.y());
    }

    public VecNf(final Vec3f v) {
        this(v.x(), v.y(), v.z());
    }

    public VecNf(final Vec4f v) {
        this(v.x(), v.y(), v.z(), v.w());
    }

    public VecNf(final VecNf v) {
        this(v.vec);
    }

    public VecNf(final float... v) {
        this.vec = v.clone();
    }

    @Override
    public int axisCount() {
        return this.vec.length;
    }

    public float get(final int comp) {
        return this.vec[comp];
    }

    public int floored(final int comp) {
        return GenericMath.floor(this.get(comp));
    }

    public void set(final int comp, final double val) {
        this.set(comp, (float) val);
    }

    public void set(final int comp, final float val) {
        this.vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(this.vec, 0);
    }

    public VecNf resize(final int size) {
        final VecNf d = new VecNf(size);
        System.arraycopy(vec, 0, d.vec, 0, Math.min(size, this.axisCount()));
        return d;
    }

    public VecNf add(final VecNf v) {
        return this.add(v.vec);
    }

    public VecNf add(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = this.vec[comp] + v[comp];
        }
        return d;
    }

    public VecNf sub(final VecNf v) {
        return this.sub(v.vec);
    }

    public VecNf sub(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = this.vec[comp] - v[comp];
        }
        return d;
    }

    public VecNf mul(final double a) {
        return this.mul((float) a);
    }

    public VecNf mul(final float a) {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = this.vec[comp] * a;
        }
        return d;
    }

    public VecNf mul(final VecNf v) {
        return this.mul(v.vec);
    }

    public VecNf mul(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = this.vec[comp] * v[comp];
        }
        return d;
    }

    public VecNf div(final double a) {
        return this.div((float) a);
    }

    public VecNf div(float a) {
        final int size = axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / a;
        }
        return d;
    }

    public VecNf div(final VecNf v) {
        return this.div(v.vec);
    }

    public VecNf div(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / v[comp];
        }
        return d;
    }

    public float dot(final VecNf v) {
        return this.dot(v.vec);
    }

    public float dot(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        float d = 0;
        for (int comp = 0; comp < size; comp++) {
            d += this.vec[comp] * v[comp];
        }
        return d;
    }

    public VecNf project(final VecNf v) {
        return this.project(v.vec);
    }

    public VecNf project(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        float lengthSquared = 0;
        for (int comp = 0; comp < size; comp++) {
            lengthSquared += v[comp] * v[comp];
        }
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vec");
        }
        final float a = this.dot(v) / lengthSquared;
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = a * v[comp];
        }
        return d;
    }

    public VecNf pow(final double pow) {
        return this.pow((float) pow);
    }

    public VecNf pow(final float power) {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (float) Math.pow(this.vec[comp], power);
        }
        return d;
    }

    public VecNf ceil() {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (float) Math.ceil(this.vec[comp]);
        }
        return d;
    }

    public VecNf floor() {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floor(this.vec[comp]);
        }
        return d;
    }

    public VecNf round() {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.round(this.vec[comp]);
        }
        return d;
    }

    public VecNf abs() {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.abs(this.vec[comp]);
        }
        return d;
    }

    public VecNf negate() {
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = -this.vec[comp];
        }
        return d;
    }

    public VecNf min(final VecNf v) {
        return this.min(v.vec);
    }

    public VecNf min(final float... v) {
        final int size = axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.min(this.vec[comp], v[comp]);
        }
        return d;
    }

    public VecNf max(final VecNf v) {
        return this.max(v.vec);
    }

    public VecNf max(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.max(this.vec[comp], v[comp]);
        }
        return d;
    }

    public float distanceSquared(final VecNf v) {
        return this.distanceSquared(v.vec);
    }

    public float distanceSquared(final float... v) {
        final int size = this.axisCount();
        if (size != v.length) {
            throw new IllegalArgumentException("Vec sizes must be the same");
        }
        float d = 0;
        for (int comp = 0; comp < size; comp++) {
            final float delta = this.vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public float distance(final VecNf v) {
        return this.distance(v.vec);
    }

    public float distance(final float... v) {
        return (float) Math.sqrt(this.distanceSquared(v));
    }

    public float lengthSquared() {
        final int size = this.axisCount();
        float l = 0;
        for (int comp = 0; comp < size; comp++) {
            l += this.vec[comp] * this.vec[comp];
        }
        return l;
    }

    public float length() {
        return (float) Math.sqrt(this.lengthSquared());
    }

    public VecNf normalize() {
        final float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vec");
        }
        final int size = this.axisCount();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (float) (this.vec[comp] / length);
        }
        return d;
    }

    @Override
    public int minAxis() {
        int axis = 0;
        float value = this.vec[axis];
        final int size = this.axisCount();
        for (int comp = 1; comp < size; comp++) {
            if (this.vec[comp] < value) {
                value = this.vec[comp];
                axis = comp;
            }
        }
        return axis;
    }

    @Override
    public int maxAxis() {
        int axis = 0;
        float value = this.vec[axis];
        final int size = this.axisCount();
        for (int comp = 1; comp < size; comp++) {
            if (this.vec[comp] > value) {
                value = this.vec[comp];
                axis = comp;
            }
        }
        return axis;
    }

    public Vec2f toVec2() {
        return new ImplVec2f(this);
    }

    public Vec3f toVec3() {
        return new ImplVec3f(this);
    }

    public Vec4f toVec4() {
        return new ImplVec4f(this);
    }

    public float[] toArray() {
        return vec.clone();
    }

    public VecNi toInt() {
        final int size = this.axisCount();
        final int[] intVec = new int[size];
        for (int comp = 0; comp < size; comp++) {
            intVec[comp] = GenericMath.floor(this.vec[comp]);
        }
        return new VecNi(intVec);
    }

    public VecNl toLong() {
        final int size = this.axisCount();
        final long[] longVec = new long[size];
        for (int comp = 0; comp < size; comp++) {
            longVec[comp] = GenericMath.floorl(this.vec[comp]);
        }
        return new VecNl(longVec);
    }

    public VecNf toFloat() {
        final int size = this.axisCount();
        final float[] floatVec = new float[size];
        for (int comp = 0; comp < size; comp++) {
            floatVec[comp] = (float) this.vec[comp];
        }
        return new VecNf(floatVec);
    }

    public VecNd toDouble() {
        final int size = this.axisCount();
        final double[] doubleVec = new double[size];
        for (int comp = 0; comp < size; comp++) {
            doubleVec[comp] = (double) this.vec[comp];
        }
        return new VecNd(doubleVec);
    }

    @Override
    public int compareTo(final VecNf v) {
        return (int) Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VecNf)) {
            return false;
        }
        return Arrays.equals(this.vec, ((VecNf) other).vec);
    }

    @Override
    public int hashCode() {
        return 67 * 5 + Arrays.hashCode(this.vec);
    }

    @Override
    public VecNf clone() {
        return new VecNf(this);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVecN extends VecNf {

        private static final long serialVersionUID = 1L;

        public ImmutableZeroVecN(final float... v) {
            super(v);
        }

        @Override
        public void set(final int comp, final float val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }
}
