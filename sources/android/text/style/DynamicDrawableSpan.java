package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public abstract class DynamicDrawableSpan extends ReplacementSpan {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 2;
    @UnsupportedAppUsage
    private WeakReference<Drawable> mDrawableRef;
    protected final int mVerticalAlignment;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignmentType {
    }

    public abstract Drawable getDrawable();

    public DynamicDrawableSpan() {
        this.mVerticalAlignment = 0;
    }

    protected DynamicDrawableSpan(int verticalAlignment) {
        this.mVerticalAlignment = verticalAlignment;
    }

    public int getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Rect rect = getCachedDrawable().getBounds();
        if (fm != null) {
            fm.ascent = -rect.bottom;
            fm.descent = 0;
            fm.top = fm.ascent;
            fm.bottom = 0;
        }
        return rect.right;
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();
        int transY = bottom - b.getBounds().bottom;
        if (this.mVerticalAlignment == 1) {
            transY -= paint.getFontMetricsInt().descent;
        } else if (this.mVerticalAlignment == 2) {
            transY = ((bottom - top) / 2) - (b.getBounds().height() / 2);
        }
        canvas.translate(x, (float) transY);
        b.draw(canvas);
        canvas.restore();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.graphics.drawable.Drawable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable getCachedDrawable() {
        /*
            r3 = this;
            java.lang.ref.WeakReference<android.graphics.drawable.Drawable> r0 = r3.mDrawableRef
            r1 = 0
            if (r0 == 0) goto L_0x000c
            java.lang.Object r2 = r0.get()
            r1 = r2
            android.graphics.drawable.Drawable r1 = (android.graphics.drawable.Drawable) r1
        L_0x000c:
            if (r1 != 0) goto L_0x0019
            android.graphics.drawable.Drawable r1 = r3.getDrawable()
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference
            r2.<init>(r1)
            r3.mDrawableRef = r2
        L_0x0019:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.style.DynamicDrawableSpan.getCachedDrawable():android.graphics.drawable.Drawable");
    }
}
