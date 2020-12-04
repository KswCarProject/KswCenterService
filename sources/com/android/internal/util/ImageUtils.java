package com.android.internal.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Size;

public class ImageUtils {
    private static final int ALPHA_TOLERANCE = 50;
    private static final int COMPACT_BITMAP_SIZE = 64;
    private static final int TOLERANCE = 20;
    private int[] mTempBuffer;
    private Bitmap mTempCompactBitmap;
    private Canvas mTempCompactBitmapCanvas;
    private Paint mTempCompactBitmapPaint;
    private final Matrix mTempMatrix = new Matrix();

    public boolean isGrayscale(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (height > 64 || width > 64) {
            if (this.mTempCompactBitmap == null) {
                this.mTempCompactBitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888);
                this.mTempCompactBitmapCanvas = new Canvas(this.mTempCompactBitmap);
                this.mTempCompactBitmapPaint = new Paint(1);
                this.mTempCompactBitmapPaint.setFilterBitmap(true);
            }
            this.mTempMatrix.reset();
            this.mTempMatrix.setScale(64.0f / ((float) width), 64.0f / ((float) height), 0.0f, 0.0f);
            this.mTempCompactBitmapCanvas.drawColor(0, PorterDuff.Mode.SRC);
            this.mTempCompactBitmapCanvas.drawBitmap(bitmap, this.mTempMatrix, this.mTempCompactBitmapPaint);
            bitmap = this.mTempCompactBitmap;
            height = 64;
            width = 64;
        }
        int size = height * width;
        ensureBufferSize(size);
        bitmap.getPixels(this.mTempBuffer, 0, width, 0, 0, width, height);
        for (int i = 0; i < size; i++) {
            if (!isGrayscale(this.mTempBuffer[i])) {
                return false;
            }
        }
        return true;
    }

    private void ensureBufferSize(int size) {
        if (this.mTempBuffer == null || this.mTempBuffer.length < size) {
            this.mTempBuffer = new int[size];
        }
    }

    public static boolean isGrayscale(int color) {
        if (((color >> 24) & 255) < 50) {
            return true;
        }
        int r = (color >> 16) & 255;
        int g = (color >> 8) & 255;
        int b = color & 255;
        if (Math.abs(r - g) >= 20 || Math.abs(r - b) >= 20 || Math.abs(g - b) >= 20) {
            return false;
        }
        return true;
    }

    public static Bitmap buildScaledBitmap(Drawable drawable, int maxWidth, int maxHeight) {
        if (drawable == null) {
            return null;
        }
        int originalWidth = drawable.getIntrinsicWidth();
        int originalHeight = drawable.getIntrinsicHeight();
        if (originalWidth <= maxWidth && originalHeight <= maxHeight && (drawable instanceof BitmapDrawable)) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (originalHeight <= 0 || originalWidth <= 0) {
            return null;
        }
        float ratio = Math.min(1.0f, Math.min(((float) maxWidth) / ((float) originalWidth), ((float) maxHeight) / ((float) originalHeight)));
        int scaledWidth = (int) (((float) originalWidth) * ratio);
        int scaledHeight = (int) (((float) originalHeight) * ratio);
        Bitmap result = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        drawable.setBounds(0, 0, scaledWidth, scaledHeight);
        drawable.draw(canvas);
        return result;
    }

    public static int calculateSampleSize(Size currentSize, Size requestedSize) {
        int inSampleSize = 1;
        if (currentSize.getHeight() > requestedSize.getHeight() || currentSize.getWidth() > requestedSize.getWidth()) {
            int halfHeight = currentSize.getHeight() / 2;
            int halfWidth = currentSize.getWidth() / 2;
            while (halfHeight / inSampleSize >= requestedSize.getHeight() && halfWidth / inSampleSize >= requestedSize.getWidth()) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002f, code lost:
        if (r0 != null) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0031, code lost:
        if (r1 != null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003c, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002b, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap loadThumbnail(android.content.ContentResolver r5, android.net.Uri r6, android.util.Size r7) throws java.io.IOException {
        /*
            android.content.ContentProviderClient r0 = r5.acquireContentProviderClient((android.net.Uri) r6)
            r1 = 0
            android.os.Bundle r2 = new android.os.Bundle     // Catch:{ Throwable -> 0x002d }
            r2.<init>()     // Catch:{ Throwable -> 0x002d }
            java.lang.String r3 = "android.content.extra.SIZE"
            android.graphics.Point r4 = android.graphics.Point.convert((android.util.Size) r7)     // Catch:{ Throwable -> 0x002d }
            r2.putParcelable(r3, r4)     // Catch:{ Throwable -> 0x002d }
            com.android.internal.util.-$$Lambda$ImageUtils$UJyN8OeHYbkY_xJzm1U3D7W4PNY r3 = new com.android.internal.util.-$$Lambda$ImageUtils$UJyN8OeHYbkY_xJzm1U3D7W4PNY     // Catch:{ Throwable -> 0x002d }
            r3.<init>(r6, r2)     // Catch:{ Throwable -> 0x002d }
            android.graphics.ImageDecoder$Source r3 = android.graphics.ImageDecoder.createSource((java.util.concurrent.Callable<android.content.res.AssetFileDescriptor>) r3)     // Catch:{ Throwable -> 0x002d }
            com.android.internal.util.-$$Lambda$ImageUtils$rnRZcgsdC1BtH9FpHTN2Kf_FXwE r4 = new com.android.internal.util.-$$Lambda$ImageUtils$rnRZcgsdC1BtH9FpHTN2Kf_FXwE     // Catch:{ Throwable -> 0x002d }
            r4.<init>()     // Catch:{ Throwable -> 0x002d }
            android.graphics.Bitmap r3 = android.graphics.ImageDecoder.decodeBitmap(r3, r4)     // Catch:{ Throwable -> 0x002d }
            if (r0 == 0) goto L_0x002a
            r0.close()
        L_0x002a:
            return r3
        L_0x002b:
            r2 = move-exception
            goto L_0x002f
        L_0x002d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002b }
        L_0x002f:
            if (r0 == 0) goto L_0x003f
            if (r1 == 0) goto L_0x003c
            r0.close()     // Catch:{ Throwable -> 0x0037 }
            goto L_0x003f
        L_0x0037:
            r3 = move-exception
            r1.addSuppressed(r3)
            goto L_0x003f
        L_0x003c:
            r0.close()
        L_0x003f:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.ImageUtils.loadThumbnail(android.content.ContentResolver, android.net.Uri, android.util.Size):android.graphics.Bitmap");
    }

    static /* synthetic */ void lambda$loadThumbnail$1(Size size, ImageDecoder decoder, ImageDecoder.ImageInfo info, ImageDecoder.Source source) {
        decoder.setAllocator(1);
        int sample = calculateSampleSize(info.getSize(), size);
        if (sample > 1) {
            decoder.setTargetSampleSize(sample);
        }
    }
}
