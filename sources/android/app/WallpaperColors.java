package android.app;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.util.ContrastColorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WallpaperColors implements Parcelable {
    private static final float BRIGHT_IMAGE_MEAN_LUMINANCE = 0.75f;
    public static final Parcelable.Creator<WallpaperColors> CREATOR = new Parcelable.Creator<WallpaperColors>() {
        public WallpaperColors createFromParcel(Parcel in) {
            return new WallpaperColors(in);
        }

        public WallpaperColors[] newArray(int size) {
            return new WallpaperColors[size];
        }
    };
    private static final float DARK_PIXEL_CONTRAST = 6.0f;
    private static final float DARK_THEME_MEAN_LUMINANCE = 0.25f;
    private static final boolean DEBUG_DARK_PIXELS = false;
    public static final int HINT_FROM_BITMAP = 4;
    @SystemApi
    public static final int HINT_SUPPORTS_DARK_TEXT = 1;
    @SystemApi
    public static final int HINT_SUPPORTS_DARK_THEME = 2;
    private static final int MAX_BITMAP_SIZE = 112;
    private static final float MAX_DARK_AREA = 0.025f;
    private static final int MAX_WALLPAPER_EXTRACTION_AREA = 12544;
    private static final float MIN_COLOR_OCCURRENCE = 0.05f;
    private int mColorHints;
    private final ArrayList<Color> mMainColors;

    public WallpaperColors(Parcel parcel) {
        this.mMainColors = new ArrayList<>();
        int count = parcel.readInt();
        for (int i = 0; i < count; i++) {
            this.mMainColors.add(Color.valueOf(parcel.readInt()));
        }
        this.mColorHints = parcel.readInt();
    }

    public static WallpaperColors fromDrawable(Drawable drawable) {
        if (drawable != null) {
            Rect initialBounds = drawable.copyBounds();
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            if (width <= 0 || height <= 0) {
                width = 112;
                height = 112;
            }
            Size optimalSize = calculateOptimalSize(width, height);
            Bitmap bitmap = Bitmap.createBitmap(optimalSize.getWidth(), optimalSize.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bmpCanvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            drawable.draw(bmpCanvas);
            WallpaperColors colors = fromBitmap(bitmap);
            bitmap.recycle();
            drawable.setBounds(initialBounds);
            return colors;
        }
        throw new IllegalArgumentException("Drawable cannot be null");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x008f, code lost:
        r9 = r9 + 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0098  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.app.WallpaperColors fromBitmap(android.graphics.Bitmap r12) {
        /*
            if (r12 == 0) goto L_0x00a3
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            int r0 = r0 * r1
            r1 = 0
            r2 = 12544(0x3100, float:1.7578E-41)
            if (r0 <= r2) goto L_0x002a
            r1 = 1
            int r3 = r12.getWidth()
            int r4 = r12.getHeight()
            android.util.Size r3 = calculateOptimalSize(r3, r4)
            int r4 = r3.getWidth()
            int r5 = r3.getHeight()
            r6 = 1
            android.graphics.Bitmap r12 = android.graphics.Bitmap.createScaledBitmap(r12, r4, r5, r6)
        L_0x002a:
            com.android.internal.graphics.palette.Palette$Builder r3 = com.android.internal.graphics.palette.Palette.from((android.graphics.Bitmap) r12)
            com.android.internal.graphics.palette.VariationalKMeansQuantizer r4 = new com.android.internal.graphics.palette.VariationalKMeansQuantizer
            r4.<init>()
            com.android.internal.graphics.palette.Palette$Builder r3 = r3.setQuantizer(r4)
            r4 = 5
            com.android.internal.graphics.palette.Palette$Builder r3 = r3.maximumColorCount(r4)
            com.android.internal.graphics.palette.Palette$Builder r3 = r3.clearFilters()
            com.android.internal.graphics.palette.Palette$Builder r2 = r3.resizeBitmapArea(r2)
            com.android.internal.graphics.palette.Palette r2 = r2.generate()
            java.util.ArrayList r3 = new java.util.ArrayList
            java.util.List r4 = r2.getSwatches()
            r3.<init>(r4)
            int r4 = r12.getWidth()
            int r5 = r12.getHeight()
            int r4 = r4 * r5
            float r4 = (float) r4
            r5 = 1028443341(0x3d4ccccd, float:0.05)
            float r4 = r4 * r5
            android.app.-$$Lambda$WallpaperColors$8R5kfKKLfHjpw_QXmD1mWOKwJxc r5 = new android.app.-$$Lambda$WallpaperColors$8R5kfKKLfHjpw_QXmD1mWOKwJxc
            r5.<init>(r4)
            r3.removeIf(r5)
            android.app.-$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE r5 = android.app.$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE.INSTANCE
            r3.sort(r5)
            int r5 = r3.size()
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x0075:
            if (r9 >= r5) goto L_0x0092
            java.lang.Object r10 = r3.get(r9)
            com.android.internal.graphics.palette.Palette$Swatch r10 = (com.android.internal.graphics.palette.Palette.Swatch) r10
            int r10 = r10.getRgb()
            android.graphics.Color r10 = android.graphics.Color.valueOf((int) r10)
            switch(r9) {
                case 0: goto L_0x008d;
                case 1: goto L_0x008b;
                case 2: goto L_0x0089;
                default: goto L_0x0088;
            }
        L_0x0088:
            goto L_0x0092
        L_0x0089:
            r8 = r10
            goto L_0x008f
        L_0x008b:
            r7 = r10
            goto L_0x008f
        L_0x008d:
            r6 = r10
        L_0x008f:
            int r9 = r9 + 1
            goto L_0x0075
        L_0x0092:
            int r9 = calculateDarkHints(r12)
            if (r1 == 0) goto L_0x009b
            r12.recycle()
        L_0x009b:
            android.app.WallpaperColors r10 = new android.app.WallpaperColors
            r11 = r9 | 4
            r10.<init>(r6, r7, r8, r11)
            return r10
        L_0x00a3:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Bitmap can't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperColors.fromBitmap(android.graphics.Bitmap):android.app.WallpaperColors");
    }

    static /* synthetic */ boolean lambda$fromBitmap$0(float minColorArea, Palette.Swatch s) {
        return ((float) s.getPopulation()) < minColorArea;
    }

    static /* synthetic */ int lambda$fromBitmap$1(Palette.Swatch a, Palette.Swatch b) {
        return b.getPopulation() - a.getPopulation();
    }

    public WallpaperColors(Color primaryColor, Color secondaryColor, Color tertiaryColor) {
        this(primaryColor, secondaryColor, tertiaryColor, 0);
    }

    @SystemApi
    public WallpaperColors(Color primaryColor, Color secondaryColor, Color tertiaryColor, int colorHints) {
        if (primaryColor != null) {
            this.mMainColors = new ArrayList<>(3);
            this.mMainColors.add(primaryColor);
            if (secondaryColor != null) {
                this.mMainColors.add(secondaryColor);
            }
            if (tertiaryColor != null) {
                if (secondaryColor != null) {
                    this.mMainColors.add(tertiaryColor);
                } else {
                    throw new IllegalArgumentException("tertiaryColor can't be specified when secondaryColor is null");
                }
            }
            this.mColorHints = colorHints;
            return;
        }
        throw new IllegalArgumentException("Primary color should never be null.");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        List<Color> mainColors = getMainColors();
        int count = mainColors.size();
        dest.writeInt(count);
        for (int i = 0; i < count; i++) {
            dest.writeInt(mainColors.get(i).toArgb());
        }
        dest.writeInt(this.mColorHints);
    }

    public Color getPrimaryColor() {
        return this.mMainColors.get(0);
    }

    public Color getSecondaryColor() {
        if (this.mMainColors.size() < 2) {
            return null;
        }
        return this.mMainColors.get(1);
    }

    public Color getTertiaryColor() {
        if (this.mMainColors.size() < 3) {
            return null;
        }
        return this.mMainColors.get(2);
    }

    public List<Color> getMainColors() {
        return Collections.unmodifiableList(this.mMainColors);
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WallpaperColors other = (WallpaperColors) o;
        if (!this.mMainColors.equals(other.mMainColors) || this.mColorHints != other.mColorHints) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.mMainColors.hashCode() * 31) + this.mColorHints;
    }

    @SystemApi
    public int getColorHints() {
        return this.mColorHints;
    }

    public void setColorHints(int colorHints) {
        this.mColorHints = colorHints;
    }

    private static int calculateDarkHints(Bitmap source) {
        if (source == null) {
            return 0;
        }
        int[] pixels = new int[(source.getWidth() * source.getHeight())];
        double totalLuminance = 0.0d;
        int maxDarkPixels = (int) (((float) pixels.length) * MAX_DARK_AREA);
        int darkPixels = 0;
        source.getPixels(pixels, 0, source.getWidth(), 0, 0, source.getWidth(), source.getHeight());
        float[] tmpHsl = new float[3];
        for (int i = 0; i < pixels.length; i++) {
            ColorUtils.colorToHSL(pixels[i], tmpHsl);
            float luminance = tmpHsl[2];
            int alpha = Color.alpha(pixels[i]);
            if (!(ContrastColorUtil.calculateContrast(pixels[i], -16777216) > 6.0d) && alpha != 0) {
                darkPixels++;
            }
            totalLuminance += (double) luminance;
        }
        int hints = 0;
        double meanLuminance = totalLuminance / ((double) pixels.length);
        if (meanLuminance > 0.75d && darkPixels < maxDarkPixels) {
            hints = 0 | 1;
        }
        if (meanLuminance < 0.25d) {
            return hints | 2;
        }
        return hints;
    }

    private static Size calculateOptimalSize(int width, int height) {
        int requestedArea = width * height;
        double scale = 1.0d;
        if (requestedArea > MAX_WALLPAPER_EXTRACTION_AREA) {
            scale = Math.sqrt(12544.0d / ((double) requestedArea));
        }
        int newWidth = (int) (((double) width) * scale);
        int newHeight = (int) (((double) height) * scale);
        if (newWidth == 0) {
            newWidth = 1;
        }
        if (newHeight == 0) {
            newHeight = 1;
        }
        return new Size(newWidth, newHeight);
    }

    public String toString() {
        StringBuilder colors = new StringBuilder();
        for (int i = 0; i < this.mMainColors.size(); i++) {
            colors.append(Integer.toHexString(this.mMainColors.get(i).toArgb()));
            colors.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        }
        return "[WallpaperColors: " + colors.toString() + "h: " + this.mColorHints + "]";
    }
}
