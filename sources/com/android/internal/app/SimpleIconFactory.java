package com.android.internal.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Pools;
import com.android.internal.R;
import org.xmlpull.v1.XmlPullParser;

@Deprecated
public class SimpleIconFactory {
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final float CIRCLE_AREA_BY_RECT = 0.7853982f;
    private static final int DEFAULT_WRAPPER_BACKGROUND = -1;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_DISTANCE = 0.020833334f;
    private static final float LINEAR_SCALE_SLOPE = 0.040449437f;
    private static final float MAX_CIRCLE_AREA_FACTOR = 0.6597222f;
    private static final float MAX_SQUARE_AREA_FACTOR = 0.6510417f;
    private static final int MIN_VISIBLE_ALPHA = 40;
    private static final float SCALE_NOT_INITIALIZED = 0.0f;
    private static final Pools.SynchronizedPool<SimpleIconFactory> sPool = new Pools.SynchronizedPool<>(Runtime.getRuntime().availableProcessors());
    private final Rect mAdaptiveIconBounds;
    private float mAdaptiveIconScale;
    private int mBadgeBitmapSize;
    private final Bitmap mBitmap;
    private Paint mBlurPaint = new Paint(3);
    private final Rect mBounds;
    private Canvas mCanvas;
    private Context mContext;
    private BlurMaskFilter mDefaultBlurMaskFilter;
    private Paint mDrawPaint = new Paint(3);
    private int mFillResIconDpi;
    private int mIconBitmapSize;
    private final float[] mLeftBorder;
    private final int mMaxSize;
    private final Rect mOldBounds = new Rect();
    private final byte[] mPixels;
    private PackageManager mPm;
    private final float[] mRightBorder;
    private final Canvas mScaleCheckCanvas;
    private int mWrapperBackgroundColor;
    private Drawable mWrapperIcon;

    @Deprecated
    public static SimpleIconFactory obtain(Context ctx) {
        SimpleIconFactory instance = sPool.acquire();
        if (instance != null) {
            return instance;
        }
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        int iconDpi = am == null ? 0 : am.getLauncherLargeIconDensity();
        Resources r = ctx.getResources();
        SimpleIconFactory instance2 = new SimpleIconFactory(ctx, iconDpi, r.getDimensionPixelSize(R.dimen.resolver_icon_size), r.getDimensionPixelSize(R.dimen.resolver_badge_size));
        instance2.setWrapperBackgroundColor(-1);
        return instance2;
    }

    @Deprecated
    public void recycle() {
        setWrapperBackgroundColor(-1);
        sPool.release(this);
    }

    @Deprecated
    private SimpleIconFactory(Context context, int fillResIconDpi, int iconBitmapSize, int badgeBitmapSize) {
        this.mContext = context.getApplicationContext();
        this.mPm = this.mContext.getPackageManager();
        this.mIconBitmapSize = iconBitmapSize;
        this.mBadgeBitmapSize = badgeBitmapSize;
        this.mFillResIconDpi = fillResIconDpi;
        this.mCanvas = new Canvas();
        this.mCanvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        this.mMaxSize = iconBitmapSize * 2;
        this.mBitmap = Bitmap.createBitmap(this.mMaxSize, this.mMaxSize, Bitmap.Config.ALPHA_8);
        this.mScaleCheckCanvas = new Canvas(this.mBitmap);
        this.mPixels = new byte[(this.mMaxSize * this.mMaxSize)];
        this.mLeftBorder = new float[this.mMaxSize];
        this.mRightBorder = new float[this.mMaxSize];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new Rect();
        this.mAdaptiveIconScale = 0.0f;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(((float) iconBitmapSize) * BLUR_FACTOR, BlurMaskFilter.Blur.NORMAL);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public void setWrapperBackgroundColor(int color) {
        this.mWrapperBackgroundColor = Color.alpha(color) < 255 ? -1 : color;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public Bitmap createUserBadgedIconBitmap(Drawable icon, UserHandle user) {
        Bitmap result;
        float[] scale = new float[1];
        if (icon == null) {
            icon = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        Drawable icon2 = normalizeAndWrapToAdaptiveIcon(icon, (RectF) null, scale);
        Bitmap bitmap = createIconBitmap(icon2, scale[0]);
        if (icon2 instanceof AdaptiveIconDrawable) {
            this.mCanvas.setBitmap(bitmap);
            recreateIcon(Bitmap.createBitmap(bitmap), this.mCanvas);
            this.mCanvas.setBitmap((Bitmap) null);
        }
        if (user == null) {
            return bitmap;
        }
        Drawable badged = this.mPm.getUserBadgedIcon(new FixedSizeBitmapDrawable(bitmap), user);
        if (badged instanceof BitmapDrawable) {
            result = ((BitmapDrawable) badged).getBitmap();
        } else {
            result = createIconBitmap(badged, 1.0f);
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public Bitmap createAppBadgedIconBitmap(Drawable icon, Bitmap renderedAppIcon) {
        if (icon == null) {
            icon = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        int w = icon.getIntrinsicWidth();
        int h = icon.getIntrinsicHeight();
        float scale = 1.0f;
        if (h > w && w > 0) {
            scale = ((float) h) / ((float) w);
        } else if (w > h && h > 0) {
            scale = ((float) w) / ((float) h);
        }
        Drawable icon2 = new BitmapDrawable(this.mContext.getResources(), maskBitmapToCircle(createIconBitmap(icon, scale)));
        Bitmap bitmap = createIconBitmap(icon2, getScale(icon2, (RectF) null));
        this.mCanvas.setBitmap(bitmap);
        recreateIcon(Bitmap.createBitmap(bitmap), this.mCanvas);
        if (renderedAppIcon != null) {
            this.mCanvas.drawBitmap(Bitmap.createScaledBitmap(renderedAppIcon, this.mBadgeBitmapSize, this.mBadgeBitmapSize, false), (float) (this.mIconBitmapSize - this.mBadgeBitmapSize), (float) (this.mIconBitmapSize - this.mBadgeBitmapSize), (Paint) null);
        }
        this.mCanvas.setBitmap((Bitmap) null);
        return bitmap;
    }

    private Bitmap maskBitmapToCircle(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f, (((float) bitmap.getWidth()) / 2.0f) - 1.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private static Drawable getFullResDefaultActivityIcon(int iconDpi) {
        return Resources.getSystem().getDrawableForDensity(17629184, iconDpi);
    }

    private Bitmap createIconBitmap(Drawable icon, float scale) {
        return createIconBitmap(icon, scale, this.mIconBitmapSize);
    }

    private Bitmap createIconBitmap(Drawable icon, float scale, int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmap);
        this.mOldBounds.set(icon.getBounds());
        if (icon instanceof AdaptiveIconDrawable) {
            int offset = Math.max((int) Math.ceil((double) (((float) size) * BLUR_FACTOR)), Math.round((((float) size) * (1.0f - scale)) / 2.0f));
            icon.setBounds(offset, offset, size - offset, size - offset);
            icon.draw(this.mCanvas);
        } else {
            if (icon instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
                Bitmap b = bitmapDrawable.getBitmap();
                if (bitmap != null && b.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                }
            }
            int width = size;
            int height = size;
            int intrinsicWidth = icon.getIntrinsicWidth();
            int intrinsicHeight = icon.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float ratio = ((float) intrinsicWidth) / ((float) intrinsicHeight);
                if (intrinsicWidth > intrinsicHeight) {
                    height = (int) (((float) width) / ratio);
                } else if (intrinsicHeight > intrinsicWidth) {
                    width = (int) (((float) height) * ratio);
                }
            }
            int left = (size - width) / 2;
            int top = (size - height) / 2;
            icon.setBounds(left, top, left + width, top + height);
            this.mCanvas.save();
            this.mCanvas.scale(scale, scale, (float) (size / 2), (float) (size / 2));
            icon.draw(this.mCanvas);
            this.mCanvas.restore();
        }
        icon.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap((Bitmap) null);
        return bitmap;
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable icon, RectF outIconBounds, float[] outScale) {
        if (this.mWrapperIcon == null) {
            this.mWrapperIcon = this.mContext.getDrawable(R.drawable.iconfactory_adaptive_icon_drawable_wrapper).mutate();
        }
        AdaptiveIconDrawable dr = (AdaptiveIconDrawable) this.mWrapperIcon;
        dr.setBounds(0, 0, 1, 1);
        float scale = getScale(icon, outIconBounds);
        if (!(icon instanceof AdaptiveIconDrawable)) {
            FixedScaleDrawable fsd = (FixedScaleDrawable) dr.getForeground();
            fsd.setDrawable(icon);
            fsd.setScale(scale);
            icon = dr;
            scale = getScale(icon, outIconBounds);
            ((ColorDrawable) dr.getBackground()).setColor(this.mWrapperBackgroundColor);
        }
        outScale[0] = scale;
        return icon;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01aa, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01b0, code lost:
        return 1.0f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized float getScale(android.graphics.drawable.Drawable r26, android.graphics.RectF r27) {
        /*
            r25 = this;
            r1 = r25
            r0 = r26
            r2 = r27
            monitor-enter(r25)
            boolean r3 = r0 instanceof android.graphics.drawable.AdaptiveIconDrawable     // Catch:{ all -> 0x01b3 }
            r4 = 0
            if (r3 == 0) goto L_0x001d
            float r3 = r1.mAdaptiveIconScale     // Catch:{ all -> 0x01b3 }
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x001d
            if (r2 == 0) goto L_0x0019
            android.graphics.Rect r3 = r1.mAdaptiveIconBounds     // Catch:{ all -> 0x01b3 }
            r2.set((android.graphics.Rect) r3)     // Catch:{ all -> 0x01b3 }
        L_0x0019:
            float r3 = r1.mAdaptiveIconScale     // Catch:{ all -> 0x01b3 }
            monitor-exit(r25)
            return r3
        L_0x001d:
            int r3 = r26.getIntrinsicWidth()     // Catch:{ all -> 0x01b3 }
            int r5 = r26.getIntrinsicHeight()     // Catch:{ all -> 0x01b3 }
            if (r3 <= 0) goto L_0x0041
            if (r5 > 0) goto L_0x002a
            goto L_0x0041
        L_0x002a:
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            if (r3 > r6) goto L_0x0032
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            if (r5 <= r6) goto L_0x0059
        L_0x0032:
            int r6 = java.lang.Math.max(r3, r5)     // Catch:{ all -> 0x01b3 }
            int r7 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            int r7 = r7 * r3
            int r7 = r7 / r6
            r3 = r7
            int r7 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            int r7 = r7 * r5
            int r7 = r7 / r6
            r5 = r7
            goto L_0x0059
        L_0x0041:
            if (r3 <= 0) goto L_0x004a
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            if (r3 <= r6) goto L_0x0048
            goto L_0x004a
        L_0x0048:
            r6 = r3
            goto L_0x004c
        L_0x004a:
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
        L_0x004c:
            r3 = r6
            if (r5 <= 0) goto L_0x0056
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            if (r5 <= r6) goto L_0x0054
            goto L_0x0056
        L_0x0054:
            r6 = r5
            goto L_0x0058
        L_0x0056:
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
        L_0x0058:
            r5 = r6
        L_0x0059:
            android.graphics.Bitmap r6 = r1.mBitmap     // Catch:{ all -> 0x01b3 }
            r7 = 0
            r6.eraseColor((int) r7)     // Catch:{ all -> 0x01b3 }
            r0.setBounds(r7, r7, r3, r5)     // Catch:{ all -> 0x01b3 }
            android.graphics.Canvas r6 = r1.mScaleCheckCanvas     // Catch:{ all -> 0x01b3 }
            r0.draw(r6)     // Catch:{ all -> 0x01b3 }
            byte[] r6 = r1.mPixels     // Catch:{ all -> 0x01b3 }
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.wrap(r6)     // Catch:{ all -> 0x01b3 }
            r6.rewind()     // Catch:{ all -> 0x01b3 }
            android.graphics.Bitmap r8 = r1.mBitmap     // Catch:{ all -> 0x01b3 }
            r8.copyPixelsToBuffer(r6)     // Catch:{ all -> 0x01b3 }
            r8 = -1
            r9 = -1
            int r10 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            r11 = 1
            int r10 = r10 + r11
            r12 = -1
            r13 = 0
            int r14 = r1.mMaxSize     // Catch:{ all -> 0x01b3 }
            int r14 = r14 - r3
            r15 = r13
            r13 = r10
            r10 = r9
            r9 = r8
            r8 = r7
        L_0x0085:
            r7 = -1
            if (r8 >= r5) goto L_0x00d7
            r17 = r7
            r18 = r7
            r11 = r17
            r4 = r18
            r17 = r15
            r15 = 0
        L_0x0093:
            if (r15 >= r3) goto L_0x00af
            byte[] r7 = r1.mPixels     // Catch:{ all -> 0x01b3 }
            byte r7 = r7[r17]     // Catch:{ all -> 0x01b3 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            r19 = r6
            r6 = 40
            if (r7 <= r6) goto L_0x00a7
            r6 = -1
            if (r4 != r6) goto L_0x00a5
            r4 = r15
        L_0x00a5:
            r6 = r15
            r11 = r6
        L_0x00a7:
            int r17 = r17 + 1
            int r15 = r15 + 1
            r6 = r19
            r7 = -1
            goto L_0x0093
        L_0x00af:
            r19 = r6
            int r15 = r17 + r14
            float[] r6 = r1.mLeftBorder     // Catch:{ all -> 0x01b3 }
            float r7 = (float) r4     // Catch:{ all -> 0x01b3 }
            r6[r8] = r7     // Catch:{ all -> 0x01b3 }
            float[] r6 = r1.mRightBorder     // Catch:{ all -> 0x01b3 }
            float r7 = (float) r11     // Catch:{ all -> 0x01b3 }
            r6[r8] = r7     // Catch:{ all -> 0x01b3 }
            r6 = -1
            if (r4 == r6) goto L_0x00cf
            r7 = r8
            if (r9 != r6) goto L_0x00c4
            r9 = r8
        L_0x00c4:
            int r6 = java.lang.Math.min(r13, r4)     // Catch:{ all -> 0x01b3 }
            int r10 = java.lang.Math.max(r12, r11)     // Catch:{ all -> 0x01b3 }
            r13 = r6
            r12 = r10
            r10 = r7
        L_0x00cf:
            int r8 = r8 + 1
            r6 = r19
            r4 = 0
            r7 = 0
            r11 = 1
            goto L_0x0085
        L_0x00d7:
            r19 = r6
            r4 = 1065353216(0x3f800000, float:1.0)
            r6 = -1
            if (r9 == r6) goto L_0x01ab
            if (r12 != r6) goto L_0x00e6
            r23 = r9
            r24 = r10
            goto L_0x01af
        L_0x00e6:
            float[] r6 = r1.mLeftBorder     // Catch:{ all -> 0x01b3 }
            r7 = 1
            convertToConvexArray(r6, r7, r9, r10)     // Catch:{ all -> 0x01b3 }
            float[] r6 = r1.mRightBorder     // Catch:{ all -> 0x01b3 }
            r7 = -1
            convertToConvexArray(r6, r7, r9, r10)     // Catch:{ all -> 0x01b3 }
            r6 = 0
            r16 = 0
        L_0x00f5:
            r7 = r16
            if (r7 >= r5) goto L_0x0112
            float[] r8 = r1.mLeftBorder     // Catch:{ all -> 0x01b3 }
            r8 = r8[r7]     // Catch:{ all -> 0x01b3 }
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r8 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r8 > 0) goto L_0x0104
            goto L_0x010f
        L_0x0104:
            float[] r8 = r1.mRightBorder     // Catch:{ all -> 0x01b3 }
            r8 = r8[r7]     // Catch:{ all -> 0x01b3 }
            float[] r11 = r1.mLeftBorder     // Catch:{ all -> 0x01b3 }
            r11 = r11[r7]     // Catch:{ all -> 0x01b3 }
            float r8 = r8 - r11
            float r8 = r8 + r4
            float r6 = r6 + r8
        L_0x010f:
            int r16 = r7 + 1
            goto L_0x00f5
        L_0x0112:
            int r7 = r10 + 1
            int r7 = r7 - r9
            int r8 = r12 + 1
            int r8 = r8 - r13
            int r7 = r7 * r8
            float r7 = (float) r7     // Catch:{ all -> 0x01b3 }
            float r8 = r6 / r7
            r11 = 1061752795(0x3f490fdb, float:0.7853982)
            int r11 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r11 >= 0) goto L_0x0127
            r11 = 1059644302(0x3f28e38e, float:0.6597222)
            goto L_0x0133
        L_0x0127:
            r11 = 1059498667(0x3f26aaab, float:0.6510417)
            r16 = 1025879631(0x3d25ae4f, float:0.040449437)
            float r17 = r4 - r8
            float r17 = r17 * r16
            float r11 = r17 + r11
        L_0x0133:
            android.graphics.Rect r4 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            r4.left = r13     // Catch:{ all -> 0x01b3 }
            android.graphics.Rect r4 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            r4.right = r12     // Catch:{ all -> 0x01b3 }
            android.graphics.Rect r4 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            r4.top = r9     // Catch:{ all -> 0x01b3 }
            android.graphics.Rect r4 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            r4.bottom = r10     // Catch:{ all -> 0x01b3 }
            if (r2 == 0) goto L_0x0175
            android.graphics.Rect r4 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            int r4 = r4.left     // Catch:{ all -> 0x01b3 }
            float r4 = (float) r4     // Catch:{ all -> 0x01b3 }
            r21 = r7
            float r7 = (float) r3     // Catch:{ all -> 0x01b3 }
            float r4 = r4 / r7
            android.graphics.Rect r7 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            int r7 = r7.top     // Catch:{ all -> 0x01b3 }
            float r7 = (float) r7     // Catch:{ all -> 0x01b3 }
            r22 = r8
            float r8 = (float) r5     // Catch:{ all -> 0x01b3 }
            float r7 = r7 / r8
            android.graphics.Rect r8 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            int r8 = r8.right     // Catch:{ all -> 0x01b3 }
            float r8 = (float) r8     // Catch:{ all -> 0x01b3 }
            r23 = r9
            float r9 = (float) r3     // Catch:{ all -> 0x01b3 }
            float r8 = r8 / r9
            r9 = 1065353216(0x3f800000, float:1.0)
            float r8 = r9 - r8
            android.graphics.Rect r9 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            int r9 = r9.bottom     // Catch:{ all -> 0x01b3 }
            float r9 = (float) r9     // Catch:{ all -> 0x01b3 }
            r24 = r10
            float r10 = (float) r5     // Catch:{ all -> 0x01b3 }
            float r9 = r9 / r10
            r10 = 1065353216(0x3f800000, float:1.0)
            float r9 = r10 - r9
            r2.set(r4, r7, r8, r9)     // Catch:{ all -> 0x01b3 }
            goto L_0x017d
        L_0x0175:
            r21 = r7
            r22 = r8
            r23 = r9
            r24 = r10
        L_0x017d:
            int r4 = r3 * r5
            float r4 = (float) r4     // Catch:{ all -> 0x01b3 }
            float r4 = r6 / r4
            int r7 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r7 <= 0) goto L_0x0191
            float r7 = r11 / r4
            double r7 = (double) r7     // Catch:{ all -> 0x01b3 }
            double r7 = java.lang.Math.sqrt(r7)     // Catch:{ all -> 0x01b3 }
            float r7 = (float) r7     // Catch:{ all -> 0x01b3 }
            r20 = r7
            goto L_0x0193
        L_0x0191:
            r20 = 1065353216(0x3f800000, float:1.0)
        L_0x0193:
            r7 = r20
            boolean r8 = r0 instanceof android.graphics.drawable.AdaptiveIconDrawable     // Catch:{ all -> 0x01b3 }
            if (r8 == 0) goto L_0x01a9
            float r8 = r1.mAdaptiveIconScale     // Catch:{ all -> 0x01b3 }
            r9 = 0
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x01a9
            r1.mAdaptiveIconScale = r7     // Catch:{ all -> 0x01b3 }
            android.graphics.Rect r8 = r1.mAdaptiveIconBounds     // Catch:{ all -> 0x01b3 }
            android.graphics.Rect r9 = r1.mBounds     // Catch:{ all -> 0x01b3 }
            r8.set(r9)     // Catch:{ all -> 0x01b3 }
        L_0x01a9:
            monitor-exit(r25)
            return r7
        L_0x01ab:
            r23 = r9
            r24 = r10
        L_0x01af:
            monitor-exit(r25)
            r4 = 1065353216(0x3f800000, float:1.0)
            return r4
        L_0x01b3:
            r0 = move-exception
            monitor-exit(r25)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.SimpleIconFactory.getScale(android.graphics.drawable.Drawable, android.graphics.RectF):float");
    }

    private static void convertToConvexArray(float[] xCoordinates, int direction, int topY, int bottomY) {
        int start;
        float[] angles = new float[(xCoordinates.length - 1)];
        int first = topY;
        int last = -1;
        float lastAngle = Float.MAX_VALUE;
        for (int i = topY + 1; i <= bottomY; i++) {
            if (xCoordinates[i] > -1.0f) {
                if (lastAngle == Float.MAX_VALUE) {
                    start = first;
                } else {
                    float currentAngle = (xCoordinates[i] - xCoordinates[last]) / ((float) (i - last));
                    int start2 = last;
                    if ((currentAngle - lastAngle) * ((float) direction) < 0.0f) {
                        int i2 = start2;
                        float f = currentAngle;
                        start = i2;
                        while (start > first) {
                            start--;
                            if ((((xCoordinates[i] - xCoordinates[start]) / ((float) (i - start))) - angles[start]) * ((float) direction) >= 0.0f) {
                                break;
                            }
                        }
                    } else {
                        start = start2;
                    }
                }
                float lastAngle2 = (xCoordinates[i] - xCoordinates[start]) / ((float) (i - start));
                for (int j = start; j < i; j++) {
                    angles[j] = lastAngle2;
                    xCoordinates[j] = xCoordinates[start] + (((float) (j - start)) * lastAngle2);
                }
                last = i;
                lastAngle = lastAngle2;
            }
        }
    }

    private synchronized void recreateIcon(Bitmap icon, Canvas out) {
        recreateIcon(icon, this.mDefaultBlurMaskFilter, 30, 61, out);
    }

    private synchronized void recreateIcon(Bitmap icon, BlurMaskFilter blurMaskFilter, int ambientAlpha, int keyAlpha, Canvas out) {
        int[] offset = new int[2];
        this.mBlurPaint.setMaskFilter(blurMaskFilter);
        Bitmap shadow = icon.extractAlpha(this.mBlurPaint, offset);
        this.mDrawPaint.setAlpha(ambientAlpha);
        out.drawBitmap(shadow, (float) offset[0], (float) offset[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(keyAlpha);
        out.drawBitmap(shadow, (float) offset[0], ((float) offset[1]) + (((float) this.mIconBitmapSize) * KEY_SHADOW_DISTANCE), this.mDrawPaint);
        this.mDrawPaint.setAlpha(255);
        out.drawBitmap(icon, 0.0f, 0.0f, this.mDrawPaint);
    }

    public static class FixedScaleDrawable extends DrawableWrapper {
        private static final float LEGACY_ICON_SCALE = 0.46669f;
        private float mScaleX = LEGACY_ICON_SCALE;
        private float mScaleY = LEGACY_ICON_SCALE;

        public FixedScaleDrawable() {
            super(new ColorDrawable());
        }

        public void draw(Canvas canvas) {
            int saveCount = canvas.save();
            canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
            super.draw(canvas);
            canvas.restoreToCount(saveCount);
        }

        public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) {
        }

        public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) {
        }

        public void setScale(float scale) {
            float h = (float) getIntrinsicHeight();
            float w = (float) getIntrinsicWidth();
            this.mScaleX = scale * LEGACY_ICON_SCALE;
            this.mScaleY = LEGACY_ICON_SCALE * scale;
            if (h > w && w > 0.0f) {
                this.mScaleX *= w / h;
            } else if (w > h && h > 0.0f) {
                this.mScaleY *= h / w;
            }
        }
    }

    private static class FixedSizeBitmapDrawable extends BitmapDrawable {
        FixedSizeBitmapDrawable(Bitmap bitmap) {
            super((Resources) null, bitmap);
        }

        public int getIntrinsicHeight() {
            return getBitmap().getWidth();
        }

        public int getIntrinsicWidth() {
            return getBitmap().getWidth();
        }
    }
}
