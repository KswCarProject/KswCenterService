package android.filterfw.geometry;

/* loaded from: classes.dex */
public class Rectangle extends Quad {
    public Rectangle() {
    }

    public Rectangle(float x, float y, float width, float height) {
        super(new Point(x, y), new Point(x + width, y), new Point(x, y + height), new Point(x + width, y + height));
    }

    public Rectangle(Point origin, Point size) {
        super(origin, origin.plus(size.f32x, 0.0f), origin.plus(0.0f, size.f33y), origin.plus(size.f32x, size.f33y));
    }

    public static Rectangle fromRotatedRect(Point center, Point size, float rotation) {
        Point p0 = new Point(center.f32x - (size.f32x / 2.0f), center.f33y - (size.f33y / 2.0f));
        Point p1 = new Point(center.f32x + (size.f32x / 2.0f), center.f33y - (size.f33y / 2.0f));
        Point p2 = new Point(center.f32x - (size.f32x / 2.0f), center.f33y + (size.f33y / 2.0f));
        Point p3 = new Point(center.f32x + (size.f32x / 2.0f), center.f33y + (size.f33y / 2.0f));
        return new Rectangle(p0.rotatedAround(center, rotation), p1.rotatedAround(center, rotation), p2.rotatedAround(center, rotation), p3.rotatedAround(center, rotation));
    }

    private Rectangle(Point p0, Point p1, Point p2, Point p3) {
        super(p0, p1, p2, p3);
    }

    public static Rectangle fromCenterVerticalAxis(Point center, Point vAxis, Point size) {
        Point dy = vAxis.scaledTo(size.f33y / 2.0f);
        Point dx = vAxis.rotated90(1).scaledTo(size.f32x / 2.0f);
        return new Rectangle(center.minus(dx).minus(dy), center.plus(dx).minus(dy), center.minus(dx).plus(dy), center.plus(dx).plus(dy));
    }

    public float getWidth() {
        return this.f35p1.minus(this.f34p0).length();
    }

    public float getHeight() {
        return this.f36p2.minus(this.f34p0).length();
    }

    public Point center() {
        return this.f34p0.plus(this.f35p1).plus(this.f36p2).plus(this.f37p3).times(0.25f);
    }

    @Override // android.filterfw.geometry.Quad
    public Rectangle scaled(float s) {
        return new Rectangle(this.f34p0.times(s), this.f35p1.times(s), this.f36p2.times(s), this.f37p3.times(s));
    }

    @Override // android.filterfw.geometry.Quad
    public Rectangle scaled(float x, float y) {
        return new Rectangle(this.f34p0.mult(x, y), this.f35p1.mult(x, y), this.f36p2.mult(x, y), this.f37p3.mult(x, y));
    }
}
