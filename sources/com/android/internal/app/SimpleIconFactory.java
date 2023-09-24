package com.android.internal.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.p002pm.PackageManager;
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
import android.p007os.UserHandle;
import android.util.AttributeSet;
import android.util.Pools;
import com.android.internal.C3132R;
import java.nio.ByteBuffer;
import org.xmlpull.v1.XmlPullParser;

@Deprecated
/* loaded from: classes4.dex */
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
    private final Rect mBounds;
    private Context mContext;
    private BlurMaskFilter mDefaultBlurMaskFilter;
    private int mFillResIconDpi;
    private int mIconBitmapSize;
    private final float[] mLeftBorder;
    private final int mMaxSize;
    private final byte[] mPixels;
    private PackageManager mPm;
    private final float[] mRightBorder;
    private final Canvas mScaleCheckCanvas;
    private int mWrapperBackgroundColor;
    private Drawable mWrapperIcon;
    private final Rect mOldBounds = new Rect();
    private Paint mBlurPaint = new Paint(3);
    private Paint mDrawPaint = new Paint(3);
    private Canvas mCanvas = new Canvas();

    @Deprecated
    public static SimpleIconFactory obtain(Context ctx) {
        SimpleIconFactory instance = sPool.acquire();
        if (instance == null) {
            ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            int iconDpi = am == null ? 0 : am.getLauncherLargeIconDensity();
            Resources r = ctx.getResources();
            int iconSize = r.getDimensionPixelSize(C3132R.dimen.resolver_icon_size);
            int badgeSize = r.getDimensionPixelSize(C3132R.dimen.resolver_badge_size);
            SimpleIconFactory instance2 = new SimpleIconFactory(ctx, iconDpi, iconSize, badgeSize);
            instance2.setWrapperBackgroundColor(-1);
            return instance2;
        }
        return instance;
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
        this.mCanvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        this.mMaxSize = iconBitmapSize * 2;
        this.mBitmap = Bitmap.createBitmap(this.mMaxSize, this.mMaxSize, Bitmap.Config.ALPHA_8);
        this.mScaleCheckCanvas = new Canvas(this.mBitmap);
        this.mPixels = new byte[this.mMaxSize * this.mMaxSize];
        this.mLeftBorder = new float[this.mMaxSize];
        this.mRightBorder = new float[this.mMaxSize];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new Rect();
        this.mAdaptiveIconScale = 0.0f;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(iconBitmapSize * BLUR_FACTOR, BlurMaskFilter.Blur.NORMAL);
    }

    @Deprecated
    void setWrapperBackgroundColor(int color) {
        this.mWrapperBackgroundColor = Color.alpha(color) < 255 ? -1 : color;
    }

    @Deprecated
    Bitmap createUserBadgedIconBitmap(Drawable icon, UserHandle user) {
        Bitmap result;
        float[] scale = new float[1];
        if (icon == null) {
            icon = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        Drawable icon2 = normalizeAndWrapToAdaptiveIcon(icon, null, scale);
        Bitmap bitmap = createIconBitmap(icon2, scale[0]);
        if (icon2 instanceof AdaptiveIconDrawable) {
            this.mCanvas.setBitmap(bitmap);
            recreateIcon(Bitmap.createBitmap(bitmap), this.mCanvas);
            this.mCanvas.setBitmap(null);
        }
        if (user != null) {
            BitmapDrawable drawable = new FixedSizeBitmapDrawable(bitmap);
            Drawable badged = this.mPm.getUserBadgedIcon(drawable, user);
            if (badged instanceof BitmapDrawable) {
                result = ((BitmapDrawable) badged).getBitmap();
            } else {
                result = createIconBitmap(badged, 1.0f);
            }
            return result;
        }
        return bitmap;
    }

    @Deprecated
    Bitmap createAppBadgedIconBitmap(Drawable icon, Bitmap renderedAppIcon) {
        if (icon == null) {
            icon = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        int w = icon.getIntrinsicWidth();
        int h = icon.getIntrinsicHeight();
        float scale = 1.0f;
        if (h > w && w > 0) {
            scale = h / w;
        } else if (w > h && h > 0) {
            scale = w / h;
        }
        Bitmap bitmap = createIconBitmap(icon, scale);
        Drawable icon2 = new BitmapDrawable(this.mContext.getResources(), maskBitmapToCircle(bitmap));
        Bitmap bitmap2 = createIconBitmap(icon2, getScale(icon2, null));
        this.mCanvas.setBitmap(bitmap2);
        recreateIcon(Bitmap.createBitmap(bitmap2), this.mCanvas);
        if (renderedAppIcon != null) {
            this.mCanvas.drawBitmap(Bitmap.createScaledBitmap(renderedAppIcon, this.mBadgeBitmapSize, this.mBadgeBitmapSize, false), this.mIconBitmapSize - this.mBadgeBitmapSize, this.mIconBitmapSize - this.mBadgeBitmapSize, (Paint) null);
        }
        this.mCanvas.setBitmap(null);
        return bitmap2;
    }

    private Bitmap maskBitmapToCircle(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f, (bitmap.getWidth() / 2.0f) - 1.0f, paint);
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
            int offset = Math.max((int) Math.ceil(size * BLUR_FACTOR), Math.round((size * (1.0f - scale)) / 2.0f));
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
                float ratio = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    height = (int) (width / ratio);
                } else if (intrinsicHeight > intrinsicWidth) {
                    width = (int) (height * ratio);
                }
            }
            int left = (size - width) / 2;
            int top = (size - height) / 2;
            icon.setBounds(left, top, left + width, top + height);
            this.mCanvas.save();
            this.mCanvas.scale(scale, scale, size / 2, size / 2);
            icon.draw(this.mCanvas);
            this.mCanvas.restore();
        }
        icon.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap(null);
        return bitmap;
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable icon, RectF outIconBounds, float[] outScale) {
        if (this.mWrapperIcon == null) {
            this.mWrapperIcon = this.mContext.getDrawable(C3132R.C3133drawable.iconfactory_adaptive_icon_drawable_wrapper).mutate();
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0045, code lost:
        if (r3 <= r25.mMaxSize) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0048, code lost:
        r6 = r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0112 A[EDGE_INSN: B:100:0x0112->B:67:0x0112 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00de A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f9 A[Catch: all -> 0x01b3, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:13:0x001d, B:17:0x002a, B:19:0x002e, B:36:0x0059, B:41:0x0095, B:47:0x00a7, B:48:0x00af, B:53:0x00c4, B:54:0x00cf, B:59:0x00e6, B:62:0x00f9, B:66:0x010f, B:65:0x0104, B:67:0x0112, B:71:0x0133, B:73:0x0145, B:75:0x017d, B:77:0x0186, B:79:0x0193, B:81:0x0199, B:83:0x01a0, B:70:0x0127, B:21:0x0032, B:23:0x0043, B:30:0x004f, B:34:0x0056, B:27:0x004a), top: B:93:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0127 A[Catch: all -> 0x01b3, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:13:0x001d, B:17:0x002a, B:19:0x002e, B:36:0x0059, B:41:0x0095, B:47:0x00a7, B:48:0x00af, B:53:0x00c4, B:54:0x00cf, B:59:0x00e6, B:62:0x00f9, B:66:0x010f, B:65:0x0104, B:67:0x0112, B:71:0x0133, B:73:0x0145, B:75:0x017d, B:77:0x0186, B:79:0x0193, B:81:0x0199, B:83:0x01a0, B:70:0x0127, B:21:0x0032, B:23:0x0043, B:30:0x004f, B:34:0x0056, B:27:0x004a), top: B:93:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0145 A[Catch: all -> 0x01b3, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:13:0x001d, B:17:0x002a, B:19:0x002e, B:36:0x0059, B:41:0x0095, B:47:0x00a7, B:48:0x00af, B:53:0x00c4, B:54:0x00cf, B:59:0x00e6, B:62:0x00f9, B:66:0x010f, B:65:0x0104, B:67:0x0112, B:71:0x0133, B:73:0x0145, B:75:0x017d, B:77:0x0186, B:79:0x0193, B:81:0x0199, B:83:0x01a0, B:70:0x0127, B:21:0x0032, B:23:0x0043, B:30:0x004f, B:34:0x0056, B:27:0x004a), top: B:93:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0186 A[Catch: all -> 0x01b3, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:13:0x001d, B:17:0x002a, B:19:0x002e, B:36:0x0059, B:41:0x0095, B:47:0x00a7, B:48:0x00af, B:53:0x00c4, B:54:0x00cf, B:59:0x00e6, B:62:0x00f9, B:66:0x010f, B:65:0x0104, B:67:0x0112, B:71:0x0133, B:73:0x0145, B:75:0x017d, B:77:0x0186, B:79:0x0193, B:81:0x0199, B:83:0x01a0, B:70:0x0127, B:21:0x0032, B:23:0x0043, B:30:0x004f, B:34:0x0056, B:27:0x004a), top: B:93:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01ab A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized float getScale(Drawable d, RectF outBounds) {
        int i;
        int rightX;
        int leftX;
        int bottomY;
        int topY;
        int topY2;
        int y;
        int y2;
        float hullByRect;
        float scaleRequired;
        float scale;
        if ((d instanceof AdaptiveIconDrawable) && this.mAdaptiveIconScale != 0.0f) {
            if (outBounds != null) {
                outBounds.set(this.mAdaptiveIconBounds);
            }
            return this.mAdaptiveIconScale;
        }
        int width = d.getIntrinsicWidth();
        int height = d.getIntrinsicHeight();
        if (width > 0 && height > 0) {
            if (width > this.mMaxSize || height > this.mMaxSize) {
                int max = Math.max(width, height);
                width = (this.mMaxSize * width) / max;
                height = (this.mMaxSize * height) / max;
            }
            this.mBitmap.eraseColor(0);
            d.setBounds(0, 0, width, height);
            d.draw(this.mScaleCheckCanvas);
            ByteBuffer buffer = ByteBuffer.wrap(this.mPixels);
            buffer.rewind();
            this.mBitmap.copyPixelsToBuffer(buffer);
            int leftX2 = this.mMaxSize + 1;
            rightX = -1;
            int rowSizeDiff = this.mMaxSize - width;
            int index = 0;
            leftX = leftX2;
            bottomY = -1;
            topY = -1;
            topY2 = 0;
            while (topY2 < height) {
                int lastX = -1;
                int firstX = -1;
                int lastX2 = index;
                int index2 = 0;
                while (index2 < width) {
                    ByteBuffer buffer2 = buffer;
                    if ((this.mPixels[lastX2] & 255) > 40) {
                        if (firstX == -1) {
                            firstX = index2;
                        }
                        lastX = index2;
                    }
                    lastX2++;
                    index2++;
                    buffer = buffer2;
                }
                ByteBuffer buffer3 = buffer;
                index = lastX2 + rowSizeDiff;
                this.mLeftBorder[topY2] = firstX;
                this.mRightBorder[topY2] = lastX;
                if (firstX != -1) {
                    int bottomY2 = topY2;
                    if (topY == -1) {
                        topY = topY2;
                    }
                    leftX = Math.min(leftX, firstX);
                    rightX = Math.max(rightX, lastX);
                    bottomY = bottomY2;
                }
                topY2++;
                buffer = buffer3;
            }
            if (topY == -1 && rightX != -1) {
                convertToConvexArray(this.mLeftBorder, 1, topY, bottomY);
                convertToConvexArray(this.mRightBorder, -1, topY, bottomY);
                float area = 0.0f;
                y = 0;
                while (true) {
                    y2 = y;
                    if (y2 < height) {
                        break;
                    }
                    if (this.mLeftBorder[y2] > -1.0f) {
                        area += (this.mRightBorder[y2] - this.mLeftBorder[y2]) + 1.0f;
                    }
                    y = y2 + 1;
                }
                int y3 = bottomY + 1;
                float rectArea = (y3 - topY) * ((rightX + 1) - leftX);
                hullByRect = area / rectArea;
                if (hullByRect >= CIRCLE_AREA_BY_RECT) {
                    scaleRequired = MAX_CIRCLE_AREA_FACTOR;
                } else {
                    scaleRequired = ((1.0f - hullByRect) * LINEAR_SCALE_SLOPE) + MAX_SQUARE_AREA_FACTOR;
                }
                this.mBounds.left = leftX;
                this.mBounds.right = rightX;
                this.mBounds.top = topY;
                this.mBounds.bottom = bottomY;
                if (outBounds == null) {
                    float rectArea2 = width;
                    outBounds.set(this.mBounds.left / rectArea2, this.mBounds.top / height, 1.0f - (this.mBounds.right / width), 1.0f - (this.mBounds.bottom / height));
                }
                float areaScale = area / (width * height);
                scale = areaScale <= scaleRequired ? (float) Math.sqrt(scaleRequired / areaScale) : 1.0f;
                if ((d instanceof AdaptiveIconDrawable) && this.mAdaptiveIconScale == 0.0f) {
                    this.mAdaptiveIconScale = scale;
                    this.mAdaptiveIconBounds.set(this.mBounds);
                }
                return scale;
            }
            return 1.0f;
        }
        int i2 = this.mMaxSize;
        width = i2;
        if (height > 0 && height <= this.mMaxSize) {
            i = height;
            height = i;
            this.mBitmap.eraseColor(0);
            d.setBounds(0, 0, width, height);
            d.draw(this.mScaleCheckCanvas);
            ByteBuffer buffer4 = ByteBuffer.wrap(this.mPixels);
            buffer4.rewind();
            this.mBitmap.copyPixelsToBuffer(buffer4);
            int leftX22 = this.mMaxSize + 1;
            rightX = -1;
            int rowSizeDiff2 = this.mMaxSize - width;
            int index3 = 0;
            leftX = leftX22;
            bottomY = -1;
            topY = -1;
            topY2 = 0;
            while (topY2 < height) {
            }
            if (topY == -1) {
                convertToConvexArray(this.mLeftBorder, 1, topY, bottomY);
                convertToConvexArray(this.mRightBorder, -1, topY, bottomY);
                float area2 = 0.0f;
                y = 0;
                while (true) {
                    y2 = y;
                    if (y2 < height) {
                    }
                    y = y2 + 1;
                }
                int y32 = bottomY + 1;
                float rectArea3 = (y32 - topY) * ((rightX + 1) - leftX);
                hullByRect = area2 / rectArea3;
                if (hullByRect >= CIRCLE_AREA_BY_RECT) {
                }
                this.mBounds.left = leftX;
                this.mBounds.right = rightX;
                this.mBounds.top = topY;
                this.mBounds.bottom = bottomY;
                if (outBounds == null) {
                }
                float areaScale2 = area2 / (width * height);
                scale = areaScale2 <= scaleRequired ? (float) Math.sqrt(scaleRequired / areaScale2) : 1.0f;
                if (d instanceof AdaptiveIconDrawable) {
                    this.mAdaptiveIconScale = scale;
                    this.mAdaptiveIconBounds.set(this.mBounds);
                }
                return scale;
            }
            return 1.0f;
        }
        i = this.mMaxSize;
        height = i;
        this.mBitmap.eraseColor(0);
        d.setBounds(0, 0, width, height);
        d.draw(this.mScaleCheckCanvas);
        ByteBuffer buffer42 = ByteBuffer.wrap(this.mPixels);
        buffer42.rewind();
        this.mBitmap.copyPixelsToBuffer(buffer42);
        int leftX222 = this.mMaxSize + 1;
        rightX = -1;
        int rowSizeDiff22 = this.mMaxSize - width;
        int index32 = 0;
        leftX = leftX222;
        bottomY = -1;
        topY = -1;
        topY2 = 0;
        while (topY2 < height) {
        }
        if (topY == -1) {
        }
        return 1.0f;
    }

    private static void convertToConvexArray(float[] xCoordinates, int direction, int topY, int bottomY) {
        int start;
        int total = xCoordinates.length;
        float[] angles = new float[total - 1];
        int last = -1;
        float lastAngle = Float.MAX_VALUE;
        for (int i = topY + 1; i <= bottomY; i++) {
            if (xCoordinates[i] > -1.0f) {
                if (lastAngle == Float.MAX_VALUE) {
                    start = topY;
                } else {
                    float currentAngle = (xCoordinates[i] - xCoordinates[last]) / (i - last);
                    int start2 = last;
                    if ((currentAngle - lastAngle) * direction < 0.0f) {
                        start = start2;
                        while (start > topY) {
                            start--;
                            float currentAngle2 = (xCoordinates[i] - xCoordinates[start]) / (i - start);
                            if ((currentAngle2 - angles[start]) * direction >= 0.0f) {
                                break;
                            }
                        }
                    } else {
                        start = start2;
                    }
                }
                float lastAngle2 = (xCoordinates[i] - xCoordinates[start]) / (i - start);
                for (int j = start; j < i; j++) {
                    angles[j] = lastAngle2;
                    xCoordinates[j] = xCoordinates[start] + ((j - start) * lastAngle2);
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
        out.drawBitmap(shadow, offset[0], offset[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(keyAlpha);
        out.drawBitmap(shadow, offset[0], offset[1] + (this.mIconBitmapSize * KEY_SHADOW_DISTANCE), this.mDrawPaint);
        this.mDrawPaint.setAlpha(255);
        out.drawBitmap(icon, 0.0f, 0.0f, this.mDrawPaint);
    }

    /* loaded from: classes4.dex */
    public static class FixedScaleDrawable extends DrawableWrapper {
        private static final float LEGACY_ICON_SCALE = 0.46669f;
        private float mScaleX;
        private float mScaleY;

        public FixedScaleDrawable() {
            super(new ColorDrawable());
            this.mScaleX = LEGACY_ICON_SCALE;
            this.mScaleY = LEGACY_ICON_SCALE;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int saveCount = canvas.save();
            canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
            super.draw(canvas);
            canvas.restoreToCount(saveCount);
        }

        @Override // android.graphics.drawable.Drawable
        public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) {
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) {
        }

        public void setScale(float scale) {
            float h = getIntrinsicHeight();
            float w = getIntrinsicWidth();
            this.mScaleX = scale * LEGACY_ICON_SCALE;
            this.mScaleY = LEGACY_ICON_SCALE * scale;
            if (h > w && w > 0.0f) {
                this.mScaleX *= w / h;
            } else if (w > h && h > 0.0f) {
                this.mScaleY *= h / w;
            }
        }
    }

    /* loaded from: classes4.dex */
    private static class FixedSizeBitmapDrawable extends BitmapDrawable {
        FixedSizeBitmapDrawable(Bitmap bitmap) {
            super((Resources) null, bitmap);
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return getBitmap().getWidth();
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return getBitmap().getWidth();
        }
    }
}
