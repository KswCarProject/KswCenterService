package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Objects;

public class RectShape extends Shape {
    private RectF mRect = new RectF();

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(this.mRect, paint);
    }

    public void getOutline(Outline outline) {
        RectF rect = rect();
        outline.setRect((int) Math.ceil((double) rect.left), (int) Math.ceil((double) rect.top), (int) Math.floor((double) rect.right), (int) Math.floor((double) rect.bottom));
    }

    /* access modifiers changed from: protected */
    public void onResize(float width, float height) {
        this.mRect.set(0.0f, 0.0f, width, height);
    }

    /* access modifiers changed from: protected */
    public final RectF rect() {
        return this.mRect;
    }

    public RectShape clone() throws CloneNotSupportedException {
        RectShape shape = (RectShape) super.clone();
        shape.mRect = new RectF(this.mRect);
        return shape;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() || !super.equals(o)) {
            return false;
        }
        return Objects.equals(this.mRect, ((RectShape) o).mRect);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.mRect});
    }
}
