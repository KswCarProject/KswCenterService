package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.Arrays;
import java.util.Objects;

public class RoundRectShape extends RectShape {
    private float[] mInnerRadii;
    private RectF mInnerRect;
    private RectF mInset;
    private float[] mOuterRadii;
    private Path mPath;

    public RoundRectShape(float[] outerRadii, RectF inset, float[] innerRadii) {
        if (outerRadii != null && outerRadii.length < 8) {
            throw new ArrayIndexOutOfBoundsException("outer radii must have >= 8 values");
        } else if (innerRadii == null || innerRadii.length >= 8) {
            this.mOuterRadii = outerRadii;
            this.mInset = inset;
            this.mInnerRadii = innerRadii;
            if (inset != null) {
                this.mInnerRect = new RectF();
            }
            this.mPath = new Path();
        } else {
            throw new ArrayIndexOutOfBoundsException("inner radii must have >= 8 values");
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(this.mPath, paint);
    }

    public void getOutline(Outline outline) {
        if (this.mInnerRect == null) {
            float radius = 0.0f;
            if (this.mOuterRadii != null) {
                radius = this.mOuterRadii[0];
                for (int i = 1; i < 8; i++) {
                    if (this.mOuterRadii[i] != radius) {
                        outline.setConvexPath(this.mPath);
                        return;
                    }
                }
            }
            RectF rect = rect();
            outline.setRoundRect((int) Math.ceil((double) rect.left), (int) Math.ceil((double) rect.top), (int) Math.floor((double) rect.right), (int) Math.floor((double) rect.bottom), radius);
        }
    }

    /* access modifiers changed from: protected */
    public void onResize(float w, float h) {
        super.onResize(w, h);
        RectF r = rect();
        this.mPath.reset();
        if (this.mOuterRadii != null) {
            this.mPath.addRoundRect(r, this.mOuterRadii, Path.Direction.CW);
        } else {
            this.mPath.addRect(r, Path.Direction.CW);
        }
        if (this.mInnerRect != null) {
            this.mInnerRect.set(r.left + this.mInset.left, r.top + this.mInset.top, r.right - this.mInset.right, r.bottom - this.mInset.bottom);
            if (this.mInnerRect.width() < w && this.mInnerRect.height() < h) {
                if (this.mInnerRadii != null) {
                    this.mPath.addRoundRect(this.mInnerRect, this.mInnerRadii, Path.Direction.CCW);
                } else {
                    this.mPath.addRect(this.mInnerRect, Path.Direction.CCW);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: float[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.drawable.shapes.RoundRectShape clone() throws java.lang.CloneNotSupportedException {
        /*
            r3 = this;
            android.graphics.drawable.shapes.RectShape r0 = super.clone()
            android.graphics.drawable.shapes.RoundRectShape r0 = (android.graphics.drawable.shapes.RoundRectShape) r0
            float[] r1 = r3.mOuterRadii
            r2 = 0
            if (r1 == 0) goto L_0x0014
            float[] r1 = r3.mOuterRadii
            java.lang.Object r1 = r1.clone()
            float[] r1 = (float[]) r1
            goto L_0x0015
        L_0x0014:
            r1 = r2
        L_0x0015:
            r0.mOuterRadii = r1
            float[] r1 = r3.mInnerRadii
            if (r1 == 0) goto L_0x0025
            float[] r1 = r3.mInnerRadii
            java.lang.Object r1 = r1.clone()
            r2 = r1
            float[] r2 = (float[]) r2
        L_0x0025:
            r0.mInnerRadii = r2
            android.graphics.RectF r1 = new android.graphics.RectF
            android.graphics.RectF r2 = r3.mInset
            r1.<init>((android.graphics.RectF) r2)
            r0.mInset = r1
            android.graphics.RectF r1 = new android.graphics.RectF
            android.graphics.RectF r2 = r3.mInnerRect
            r1.<init>((android.graphics.RectF) r2)
            r0.mInnerRect = r1
            android.graphics.Path r1 = new android.graphics.Path
            android.graphics.Path r2 = r3.mPath
            r1.<init>(r2)
            r0.mPath = r1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.shapes.RoundRectShape.clone():android.graphics.drawable.shapes.RoundRectShape");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() || !super.equals(o)) {
            return false;
        }
        RoundRectShape that = (RoundRectShape) o;
        if (!Arrays.equals(this.mOuterRadii, that.mOuterRadii) || !Objects.equals(this.mInset, that.mInset) || !Arrays.equals(this.mInnerRadii, that.mInnerRadii) || !Objects.equals(this.mInnerRect, that.mInnerRect) || !Objects.equals(this.mPath, that.mPath)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.mInset, this.mInnerRect, this.mPath}) * 31) + Arrays.hashCode(this.mOuterRadii)) * 31) + Arrays.hashCode(this.mInnerRadii);
    }
}
