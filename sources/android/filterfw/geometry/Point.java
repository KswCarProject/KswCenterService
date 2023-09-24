package android.filterfw.geometry;

import android.annotation.UnsupportedAppUsage;

/* loaded from: classes.dex */
public class Point {
    @UnsupportedAppUsage

    /* renamed from: x */
    public float f32x;
    @UnsupportedAppUsage

    /* renamed from: y */
    public float f33y;

    @UnsupportedAppUsage
    public Point() {
    }

    @UnsupportedAppUsage
    public Point(float x, float y) {
        this.f32x = x;
        this.f33y = y;
    }

    public void set(float x, float y) {
        this.f32x = x;
        this.f33y = y;
    }

    public boolean IsInUnitRange() {
        return this.f32x >= 0.0f && this.f32x <= 1.0f && this.f33y >= 0.0f && this.f33y <= 1.0f;
    }

    public Point plus(float x, float y) {
        return new Point(this.f32x + x, this.f33y + y);
    }

    public Point plus(Point point) {
        return plus(point.f32x, point.f33y);
    }

    public Point minus(float x, float y) {
        return new Point(this.f32x - x, this.f33y - y);
    }

    public Point minus(Point point) {
        return minus(point.f32x, point.f33y);
    }

    public Point times(float s) {
        return new Point(this.f32x * s, this.f33y * s);
    }

    public Point mult(float x, float y) {
        return new Point(this.f32x * x, this.f33y * y);
    }

    public float length() {
        return (float) Math.hypot(this.f32x, this.f33y);
    }

    public float distanceTo(Point p) {
        return p.minus(this).length();
    }

    public Point scaledTo(float length) {
        return times(length / length());
    }

    public Point normalize() {
        return scaledTo(1.0f);
    }

    public Point rotated90(int count) {
        float nx = this.f32x;
        float ny = this.f33y;
        for (int i = 0; i < count; i++) {
            float ox = nx;
            nx = ny;
            ny = -ox;
        }
        return new Point(nx, ny);
    }

    public Point rotated(float radians) {
        return new Point((float) ((Math.cos(radians) * this.f32x) - (Math.sin(radians) * this.f33y)), (float) ((Math.sin(radians) * this.f32x) + (Math.cos(radians) * this.f33y)));
    }

    public Point rotatedAround(Point center, float radians) {
        return minus(center).rotated(radians).plus(center);
    }

    public String toString() {
        return "(" + this.f32x + ", " + this.f33y + ")";
    }
}
