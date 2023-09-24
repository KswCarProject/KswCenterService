package android.filterfw.geometry;

import android.annotation.UnsupportedAppUsage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class Quad {
    @UnsupportedAppUsage

    /* renamed from: p0 */
    public Point f34p0;
    @UnsupportedAppUsage

    /* renamed from: p1 */
    public Point f35p1;
    @UnsupportedAppUsage

    /* renamed from: p2 */
    public Point f36p2;
    @UnsupportedAppUsage

    /* renamed from: p3 */
    public Point f37p3;

    @UnsupportedAppUsage
    public Quad() {
    }

    @UnsupportedAppUsage
    public Quad(Point p0, Point p1, Point p2, Point p3) {
        this.f34p0 = p0;
        this.f35p1 = p1;
        this.f36p2 = p2;
        this.f37p3 = p3;
    }

    public boolean IsInUnitRange() {
        return this.f34p0.IsInUnitRange() && this.f35p1.IsInUnitRange() && this.f36p2.IsInUnitRange() && this.f37p3.IsInUnitRange();
    }

    public Quad translated(Point t) {
        return new Quad(this.f34p0.plus(t), this.f35p1.plus(t), this.f36p2.plus(t), this.f37p3.plus(t));
    }

    public Quad translated(float x, float y) {
        return new Quad(this.f34p0.plus(x, y), this.f35p1.plus(x, y), this.f36p2.plus(x, y), this.f37p3.plus(x, y));
    }

    public Quad scaled(float s) {
        return new Quad(this.f34p0.times(s), this.f35p1.times(s), this.f36p2.times(s), this.f37p3.times(s));
    }

    public Quad scaled(float x, float y) {
        return new Quad(this.f34p0.mult(x, y), this.f35p1.mult(x, y), this.f36p2.mult(x, y), this.f37p3.mult(x, y));
    }

    public Rectangle boundingBox() {
        List<Float> xs = Arrays.asList(Float.valueOf(this.f34p0.f32x), Float.valueOf(this.f35p1.f32x), Float.valueOf(this.f36p2.f32x), Float.valueOf(this.f37p3.f32x));
        List<Float> ys = Arrays.asList(Float.valueOf(this.f34p0.f33y), Float.valueOf(this.f35p1.f33y), Float.valueOf(this.f36p2.f33y), Float.valueOf(this.f37p3.f33y));
        float x0 = ((Float) Collections.min(xs)).floatValue();
        float y0 = ((Float) Collections.min(ys)).floatValue();
        float x1 = ((Float) Collections.max(xs)).floatValue();
        float y1 = ((Float) Collections.max(ys)).floatValue();
        return new Rectangle(x0, y0, x1 - x0, y1 - y0);
    }

    public float getBoundingWidth() {
        List<Float> xs = Arrays.asList(Float.valueOf(this.f34p0.f32x), Float.valueOf(this.f35p1.f32x), Float.valueOf(this.f36p2.f32x), Float.valueOf(this.f37p3.f32x));
        return ((Float) Collections.max(xs)).floatValue() - ((Float) Collections.min(xs)).floatValue();
    }

    public float getBoundingHeight() {
        List<Float> ys = Arrays.asList(Float.valueOf(this.f34p0.f33y), Float.valueOf(this.f35p1.f33y), Float.valueOf(this.f36p2.f33y), Float.valueOf(this.f37p3.f33y));
        return ((Float) Collections.max(ys)).floatValue() - ((Float) Collections.min(ys)).floatValue();
    }

    public String toString() {
        return "{" + this.f34p0 + ", " + this.f35p1 + ", " + this.f36p2 + ", " + this.f37p3 + "}";
    }
}
