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
import com.google.errorprone.annotations.Immutable;

@Immutable
record ImplVec2d(double x, double y) implements Vec2d {
    public ImplVec2d(final Vec3d v) {
        this(v.x(), v.y());
    }

    public ImplVec2d(final Vec4d v) {
        this(v.x(), v.y());
    }

    public ImplVec2d(final VecNd v) {
        this(v.get(0), v.get(1));
    }

    public ImplVec2d(final float x, final float y) {
        this(x, (double) y);
    }

    @Override
    public int floorX() {
        return GenericMath.floor(this.x);
    }

    @Override
    public int floorY() {
        return GenericMath.floor(this.y);
    }

    @Override
    public Vec2d add(final Vec2d v) {
        return this.add(v.x(), v.y());
    }

    @Override
    public Vec2d add(final float x, final float y) {
        return this.add(x, (double) y);
    }

    @Override
    public Vec2d add(final double x, final double y) {
        return new ImplVec2d(this.x + x, this.y + y);
    }

    @Override
    public Vec2d sub(final Vec2d v) {
        return this.sub(v.x(), v.y());
    }

    @Override
    public Vec2d sub(final float x, final float y) {
        return this.sub(x, (double) y);
    }

    @Override
    public Vec2d sub(final double x, final double y) {
        return new ImplVec2d(this.x - x, this.y - y);
    }

    @Override
    public Vec2d mul(final float a) {
        return this.mul((double) a);
    }

    @Override
    public Vec2d mul(final double a) {
        return this.mul(a, a);
    }

    @Override
    public Vec2d mul(final Vec2d v) {
        return this.mul(v.x(), v.y());
    }

    @Override
    public Vec2d mul(final float x, final float y) {
        return this.mul(x, (double) y);
    }

    @Override
    public Vec2d mul(final double x, final double y) {
        return new ImplVec2d(this.x * x, this.y * y);
    }

    @Override
    public Vec2d div(final float a) {
        return this.div((double) a);
    }

    @Override
    public Vec2d div(final double a) {
        return this.div(a, a);
    }

    @Override
    public Vec2d div(final Vec2d v) {
        return this.div(v.x(), v.y());
    }

    @Override
    public Vec2d div(final float x, final float y) {
        return this.div(x, (double) y);
    }

    @Override
    public Vec2d div(final double x, final double y) {
        return new ImplVec2d(this.x / x, this.y / y);
    }

    @Override
    public double dot(final Vec2d v) {
        return this.dot(v.x(), v.y());
    }

    @Override
    public double dot(final float x, final float y) {
        return this.dot(x, (double) y);
    }

    @Override
    public double dot(final double x, final double y) {
        return this.x * x + this.y * y;
    }

    @Override
    public Vec2d project(final Vec2d v) {
        return this.project(v.x(), v.y());
    }

    @Override
    public Vec2d project(final float x, final float y) {
        return this.project(x, (double) y);
    }

    @Override
    public Vec2d project(final double x, final double y) {
        final double lengthSquared = x * x + y * y;
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vec");
        }
        final double a = this.dot(x, y) / lengthSquared;
        return new ImplVec2d(a * x, a * y);
    }

    @Override
    public Vec2d pow(final float pow) {
        return this.pow((double) pow);
    }

    @Override
    public Vec2d pow(final double power) {
        return new ImplVec2d(Math.pow(this.x, power), Math.pow(this.y, power));
    }

    @Override
    public Vec2d ceil() {
        return new ImplVec2d(Math.ceil(this.x), Math.ceil(this.y));
    }

    @Override
    public Vec2d floor() {
        return new ImplVec2d(GenericMath.floor(this.x), GenericMath.floor(this.y));
    }

    @Override
    public Vec2d round() {
        return new ImplVec2d(Math.round(this.x), Math.round(this.y));
    }

    @Override
    public Vec2d abs() {
        return new ImplVec2d(Math.abs(this.x), Math.abs(this.y));
    }

    @Override
    public Vec2d negate() {
        return new ImplVec2d(-this.x, -this.y);
    }

    @Override
    public Vec2d min(final Vec2d v) {
        return this.min(v.x(), v.y());
    }

    @Override
    public Vec2d min(final float x, final float y) {
        return this.min(x, (double) y);
    }

    @Override
    public Vec2d min(final double x, final double y) {
        return new ImplVec2d(Math.min(this.x, x), Math.min(this.y, y));
    }

    @Override
    public Vec2d max(final Vec2d v) {
        return this.max(v.x(), v.y());
    }

    @Override
    public Vec2d max(final float x, final float y) {
        return this.max(x, (double) y);
    }

    @Override
    public Vec2d max(final double x, final double y) {
        return new ImplVec2d(Math.max(this.x, x), Math.max(this.y, y));
    }

    @Override
    public double distanceSquared(final Vec2d v) {
        return this.distanceSquared(v.x(), v.y());
    }

    @Override
    public double distanceSquared(final float x, final float y) {
        return this.distanceSquared(x, (double) y);
    }

    @Override
    public double distanceSquared(final double x, final double y) {
        final double dx = this.x - x;
        final double dy = this.y - y;
        return dx * dx + dy * dy;
    }

    @Override
    public double distance(final Vec2d v) {
        return this.distance(v.x(), v.y());
    }

    @Override
    public double distance(final float x, final float y) {
        return this.distance(x, (double) y);
    }

    @Override
    public double distance(final double x, final double y) {
        return Math.sqrt(this.distanceSquared(x, y));
    }

    @Override
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public Vec2d normalize() {
        final double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vec");
        }
        return new ImplVec2d(this.x / length, this.y / length);
    }

    /**
     * Return the axis with the minimal value.
     *
     * @return {@link int} axis with minimal value
     */
    @Override
    public int minAxis() {
        return this.x < this.y ? 0 : 1;
    }

    /**
     * Return the axis with the maximum value.
     *
     * @return {@link int} axis with maximum value
     */
    @Override
    public int maxAxis() {
        return this.x > this.y ? 0 : 1;
    }

    @Override
    public Vec3d toVec3() {
        return this.toVec3(0);
    }

    @Override
    public Vec3d toVec3(final float z) {
        return this.toVec3((double) z);
    }

    @Override
    public Vec3d toVec3(final double z) {
        return new ImplVec3d(this, z);
    }

    @Override
    public Vec4d toVec4() {
        return this.toVec4(0, 0);
    }

    @Override
    public Vec4d toVec4(final float z, final float w) {
        return this.toVec4(z, (double) w);
    }

    @Override
    public Vec4d toVec4(final double z, final double w) {
        return new ImplVec4d(this, z, w);
    }

    @Override
    public VecNd toVecN() {
        return new VecNd(this);
    }

    @Override
    public double[] toArray() {
        return new double[]{this.x, this.y};
    }

    @Override
    public Vec2i toInt() {
        return new ImplVec2i(this.x, this.y);
    }

    @Override
    public Vec2l toLong() {
        return new ImplVec2l(this.x, this.y);
    }

    @Override
    public Vec2f toFloat() {
        return new ImplVec2f(this.x, this.y);
    }

    @Override
    public Vec2d toDouble() {
        return new ImplVec2d(this.x, this.y);
    }

    @Override
    public int compareTo(final Vec2d v) {
        return (int) Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vec2d that)) {
            return false;
        }
        if (Double.compare(that.x(), this.x) != 0) {
            return false;
        }
        return Double.compare(that.y(), this.y) == 0;
    }
}
