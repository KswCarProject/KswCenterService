package android.support.p011v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.p007os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;

/* renamed from: android.support.v4.graphics.drawable.IconCompat */
/* loaded from: classes3.dex */
public class IconCompat {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
    private static final int TYPE_ADAPTIVE_BITMAP = 5;
    private static final int TYPE_BITMAP = 1;
    private static final int TYPE_DATA = 3;
    private static final int TYPE_RESOURCE = 2;
    private static final int TYPE_URI = 4;
    private int mInt1;
    private int mInt2;
    private Object mObj1;
    private final int mType;

    public static IconCompat createWithResource(Context context, @DrawableRes int resId) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        IconCompat rep = new IconCompat(2);
        rep.mInt1 = resId;
        rep.mObj1 = context;
        return rep;
    }

    public static IconCompat createWithBitmap(Bitmap bits) {
        if (bits == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat rep = new IconCompat(1);
        rep.mObj1 = bits;
        return rep;
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bits) {
        if (bits == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat rep = new IconCompat(5);
        rep.mObj1 = bits;
        return rep;
    }

    public static IconCompat createWithData(byte[] data, int offset, int length) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        IconCompat rep = new IconCompat(3);
        rep.mObj1 = data;
        rep.mInt1 = offset;
        rep.mInt2 = length;
        return rep;
    }

    public static IconCompat createWithContentUri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat rep = new IconCompat(4);
        rep.mObj1 = uri;
        return rep;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        return createWithContentUri(uri.toString());
    }

    private IconCompat(int mType) {
        this.mType = mType;
    }

    @TargetApi(26)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Icon toIcon() {
        switch (this.mType) {
            case 1:
                return Icon.createWithBitmap((Bitmap) this.mObj1);
            case 2:
                return Icon.createWithResource((Context) this.mObj1, this.mInt1);
            case 3:
                return Icon.createWithData((byte[]) this.mObj1, this.mInt1, this.mInt2);
            case 4:
                return Icon.createWithContentUri((String) this.mObj1);
            case 5:
                if (Build.VERSION.SDK_INT >= 26) {
                    return Icon.createWithAdaptiveBitmap((Bitmap) this.mObj1);
                }
                return Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1));
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void addToShortcutIntent(Intent outIntent) {
        int i = this.mType;
        if (i != 5) {
            switch (i) {
                case 1:
                    outIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, (Bitmap) this.mObj1);
                    return;
                case 2:
                    outIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext((Context) this.mObj1, this.mInt1));
                    return;
                default:
                    throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
            }
        }
        outIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1));
    }

    @VisibleForTesting
    static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap adaptiveIconBitmap) {
        int size = (int) (Math.min(adaptiveIconBitmap.getWidth(), adaptiveIconBitmap.getHeight()) * DEFAULT_VIEW_PORT_SCALE);
        Bitmap icon = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        Paint paint = new Paint(3);
        float center = size * 0.5f;
        float radius = ICON_DIAMETER_FACTOR * center;
        float blur = size * BLUR_FACTOR;
        paint.setColor(0);
        paint.setShadowLayer(blur, 0.0f, size * KEY_SHADOW_OFFSET_FACTOR, 1023410176);
        canvas.drawCircle(center, center, radius, paint);
        paint.setShadowLayer(blur, 0.0f, 0.0f, 503316480);
        canvas.drawCircle(center, center, radius, paint);
        paint.clearShadowLayer();
        paint.setColor(-16777216);
        BitmapShader shader = new BitmapShader(adaptiveIconBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix shift = new Matrix();
        shift.setTranslate((-(adaptiveIconBitmap.getWidth() - size)) / 2, (-(adaptiveIconBitmap.getHeight() - size)) / 2);
        shader.setLocalMatrix(shift);
        paint.setShader(shader);
        canvas.drawCircle(center, center, radius, paint);
        canvas.setBitmap(null);
        return icon;
    }
}
