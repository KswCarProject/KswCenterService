package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadSystemException;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.WindowManagerGlobal;
import com.android.internal.R;
import com.ibm.icu.text.ArabicShaping;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import libcore.io.IoUtils;

public class WallpaperManager {
    public static final String ACTION_CHANGE_LIVE_WALLPAPER = "android.service.wallpaper.CHANGE_LIVE_WALLPAPER";
    public static final String ACTION_CROP_AND_SET_WALLPAPER = "android.service.wallpaper.CROP_AND_SET_WALLPAPER";
    public static final String ACTION_LIVE_WALLPAPER_CHOOSER = "android.service.wallpaper.LIVE_WALLPAPER_CHOOSER";
    public static final String COMMAND_DROP = "android.home.drop";
    public static final String COMMAND_SECONDARY_TAP = "android.wallpaper.secondaryTap";
    public static final String COMMAND_TAP = "android.wallpaper.tap";
    private static boolean DEBUG = false;
    public static final String EXTRA_LIVE_WALLPAPER_COMPONENT = "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT";
    public static final String EXTRA_NEW_WALLPAPER_ID = "android.service.wallpaper.extra.ID";
    public static final int FLAG_LOCK = 2;
    public static final int FLAG_SYSTEM = 1;
    private static final String PROP_LOCK_WALLPAPER = "ro.config.lock_wallpaper";
    private static final String PROP_WALLPAPER = "ro.config.wallpaper";
    private static final String PROP_WALLPAPER_COMPONENT = "ro.config.wallpaper_component";
    /* access modifiers changed from: private */
    public static String TAG = "WallpaperManager";
    public static final String WALLPAPER_PREVIEW_META_DATA = "android.wallpaper.preview";
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public static Globals sGlobals;
    private static final Object sSync = new Object[0];
    private final Context mContext;
    private float mWallpaperXStep = -1.0f;
    private float mWallpaperYStep = -1.0f;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetWallpaperFlags {
    }

    static class FastBitmapDrawable extends Drawable {
        private final Bitmap mBitmap;
        private int mDrawLeft;
        private int mDrawTop;
        private final int mHeight;
        private final Paint mPaint;
        private final int mWidth;

        private FastBitmapDrawable(Bitmap bitmap) {
            this.mBitmap = bitmap;
            this.mWidth = bitmap.getWidth();
            this.mHeight = bitmap.getHeight();
            setBounds(0, 0, this.mWidth, this.mHeight);
            this.mPaint = new Paint();
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }

        public void draw(Canvas canvas) {
            canvas.drawBitmap(this.mBitmap, (float) this.mDrawLeft, (float) this.mDrawTop, this.mPaint);
        }

        public int getOpacity() {
            return -1;
        }

        public void setBounds(int left, int top, int right, int bottom) {
            this.mDrawLeft = (((right - left) - this.mWidth) / 2) + left;
            this.mDrawTop = (((bottom - top) - this.mHeight) / 2) + top;
        }

        public void setAlpha(int alpha) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        public void setColorFilter(ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        public void setDither(boolean dither) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        public void setFilterBitmap(boolean filter) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        public int getIntrinsicWidth() {
            return this.mWidth;
        }

        public int getIntrinsicHeight() {
            return this.mHeight;
        }

        public int getMinimumWidth() {
            return this.mWidth;
        }

        public int getMinimumHeight() {
            return this.mHeight;
        }
    }

    private static class Globals extends IWallpaperManagerCallback.Stub {
        private Bitmap mCachedWallpaper;
        private int mCachedWallpaperUserId;
        private boolean mColorCallbackRegistered;
        private final ArrayList<Pair<OnColorsChangedListener, Handler>> mColorListeners = new ArrayList<>();
        private Bitmap mDefaultWallpaper;
        private Handler mMainLooperHandler;
        /* access modifiers changed from: private */
        public final IWallpaperManager mService;

        Globals(IWallpaperManager service, Looper looper) {
            this.mService = service;
            this.mMainLooperHandler = new Handler(looper);
            forgetLoadedWallpaper();
        }

        public void onWallpaperChanged() {
            forgetLoadedWallpaper();
        }

        public void addOnColorsChangedListener(OnColorsChangedListener callback, Handler handler, int userId, int displayId) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, userId, displayId);
                        this.mColorCallbackRegistered = true;
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mColorListeners.add(new Pair(callback, handler));
            }
        }

        public void removeOnColorsChangedListener(OnColorsChangedListener callback, int userId, int displayId) {
            synchronized (this) {
                this.mColorListeners.removeIf(new Predicate() {
                    public final boolean test(Object obj) {
                        return WallpaperManager.Globals.lambda$removeOnColorsChangedListener$0(WallpaperManager.OnColorsChangedListener.this, (Pair) obj);
                    }
                });
                if (this.mColorListeners.size() == 0 && this.mColorCallbackRegistered) {
                    this.mColorCallbackRegistered = false;
                    try {
                        this.mService.unregisterWallpaperColorsCallback(this, userId, displayId);
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnColorsChangedListener$0(OnColorsChangedListener callback, Pair pair) {
            return pair.first == callback;
        }

        public void onWallpaperColorsChanged(WallpaperColors colors, int which, int userId) {
            synchronized (this) {
                Iterator<Pair<OnColorsChangedListener, Handler>> it = this.mColorListeners.iterator();
                while (it.hasNext()) {
                    Pair<OnColorsChangedListener, Handler> listener = it.next();
                    Handler handler = (Handler) listener.second;
                    if (listener.second == null) {
                        handler = this.mMainLooperHandler;
                    }
                    handler.post(new Runnable(listener, colors, which, userId) {
                        private final /* synthetic */ Pair f$1;
                        private final /* synthetic */ WallpaperColors f$2;
                        private final /* synthetic */ int f$3;
                        private final /* synthetic */ int f$4;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                            this.f$3 = r4;
                            this.f$4 = r5;
                        }

                        public final void run() {
                            WallpaperManager.Globals.lambda$onWallpaperColorsChanged$1(WallpaperManager.Globals.this, this.f$1, this.f$2, this.f$3, this.f$4);
                        }
                    });
                }
            }
        }

        public static /* synthetic */ void lambda$onWallpaperColorsChanged$1(Globals globals, Pair listener, WallpaperColors colors, int which, int userId) {
            boolean stillExists;
            synchronized (WallpaperManager.sGlobals) {
                stillExists = globals.mColorListeners.contains(listener);
            }
            if (stillExists) {
                ((OnColorsChangedListener) listener.first).onColorsChanged(colors, which, userId);
            }
        }

        /* access modifiers changed from: package-private */
        public WallpaperColors getWallpaperColors(int which, int userId, int displayId) {
            if (which == 2 || which == 1) {
                try {
                    return this.mService.getWallpaperColors(which, userId, displayId);
                } catch (RemoteException e) {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("Must request colors for exactly one kind of wallpaper");
            }
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean returnDefault, int which) {
            return peekWallpaperBitmap(context, returnDefault, which, context.getUserId(), false);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0075, code lost:
            if (r7 == false) goto L_0x0089;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0077, code lost:
            r0 = r5.mDefaultWallpaper;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0079, code lost:
            if (r0 != null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x007b, code lost:
            r1 = getDefaultWallpaper(r6, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x007f, code lost:
            monitor-enter(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            r5.mDefaultWallpaper = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0082, code lost:
            monitor-exit(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0089, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
            return r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.graphics.Bitmap peekWallpaperBitmap(android.content.Context r6, boolean r7, int r8, int r9, boolean r10) {
            /*
                r5 = this;
                android.app.IWallpaperManager r0 = r5.mService
                r1 = 0
                if (r0 == 0) goto L_0x0019
                android.app.IWallpaperManager r0 = r5.mService     // Catch:{ RemoteException -> 0x0013 }
                java.lang.String r2 = r6.getOpPackageName()     // Catch:{ RemoteException -> 0x0013 }
                boolean r0 = r0.isWallpaperSupported(r2)     // Catch:{ RemoteException -> 0x0013 }
                if (r0 != 0) goto L_0x0012
                return r1
            L_0x0012:
                goto L_0x0019
            L_0x0013:
                r0 = move-exception
                java.lang.RuntimeException r1 = r0.rethrowFromSystemServer()
                throw r1
            L_0x0019:
                monitor-enter(r5)
                android.graphics.Bitmap r0 = r5.mCachedWallpaper     // Catch:{ all -> 0x008a }
                if (r0 == 0) goto L_0x002e
                int r0 = r5.mCachedWallpaperUserId     // Catch:{ all -> 0x008a }
                if (r0 != r9) goto L_0x002e
                android.graphics.Bitmap r0 = r5.mCachedWallpaper     // Catch:{ all -> 0x008a }
                boolean r0 = r0.isRecycled()     // Catch:{ all -> 0x008a }
                if (r0 != 0) goto L_0x002e
                android.graphics.Bitmap r0 = r5.mCachedWallpaper     // Catch:{ all -> 0x008a }
                monitor-exit(r5)     // Catch:{ all -> 0x008a }
                return r0
            L_0x002e:
                r5.mCachedWallpaper = r1     // Catch:{ all -> 0x008a }
                r0 = 0
                r5.mCachedWallpaperUserId = r0     // Catch:{ all -> 0x008a }
                android.graphics.Bitmap r0 = r5.getCurrentWallpaperLocked(r6, r9, r10)     // Catch:{ OutOfMemoryError -> 0x0052, SecurityException -> 0x003c }
                r5.mCachedWallpaper = r0     // Catch:{ OutOfMemoryError -> 0x0052, SecurityException -> 0x003c }
                r5.mCachedWallpaperUserId = r9     // Catch:{ OutOfMemoryError -> 0x0052, SecurityException -> 0x003c }
            L_0x003b:
                goto L_0x006c
            L_0x003c:
                r0 = move-exception
                android.content.pm.ApplicationInfo r2 = r6.getApplicationInfo()     // Catch:{ all -> 0x008a }
                int r2 = r2.targetSdkVersion     // Catch:{ all -> 0x008a }
                r3 = 27
                if (r2 >= r3) goto L_0x0051
                java.lang.String r2 = android.app.WallpaperManager.TAG     // Catch:{ all -> 0x008a }
                java.lang.String r3 = "No permission to access wallpaper, suppressing exception to avoid crashing legacy app."
                android.util.Log.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x008a }
                goto L_0x006c
            L_0x0051:
                throw r0     // Catch:{ all -> 0x008a }
            L_0x0052:
                r0 = move-exception
                java.lang.String r2 = android.app.WallpaperManager.TAG     // Catch:{ all -> 0x008a }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x008a }
                r3.<init>()     // Catch:{ all -> 0x008a }
                java.lang.String r4 = "Out of memory loading the current wallpaper: "
                r3.append(r4)     // Catch:{ all -> 0x008a }
                r3.append(r0)     // Catch:{ all -> 0x008a }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x008a }
                android.util.Log.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x008a }
                goto L_0x003b
            L_0x006c:
                android.graphics.Bitmap r0 = r5.mCachedWallpaper     // Catch:{ all -> 0x008a }
                if (r0 == 0) goto L_0x0074
                android.graphics.Bitmap r0 = r5.mCachedWallpaper     // Catch:{ all -> 0x008a }
                monitor-exit(r5)     // Catch:{ all -> 0x008a }
                return r0
            L_0x0074:
                monitor-exit(r5)     // Catch:{ all -> 0x008a }
                if (r7 == 0) goto L_0x0089
                android.graphics.Bitmap r0 = r5.mDefaultWallpaper
                if (r0 != 0) goto L_0x0088
                android.graphics.Bitmap r1 = r5.getDefaultWallpaper(r6, r8)
                monitor-enter(r5)
                r5.mDefaultWallpaper = r1     // Catch:{ all -> 0x0085 }
                monitor-exit(r5)     // Catch:{ all -> 0x0085 }
                r0 = r1
                goto L_0x0088
            L_0x0085:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0085 }
                throw r0
            L_0x0088:
                return r0
            L_0x0089:
                return r1
            L_0x008a:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x008a }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.Globals.peekWallpaperBitmap(android.content.Context, boolean, int, int, boolean):android.graphics.Bitmap");
        }

        /* access modifiers changed from: package-private */
        public void forgetLoadedWallpaper() {
            synchronized (this) {
                this.mCachedWallpaper = null;
                this.mCachedWallpaperUserId = 0;
                this.mDefaultWallpaper = null;
            }
        }

        private Bitmap getCurrentWallpaperLocked(Context context, int userId, boolean hardware) {
            ParcelFileDescriptor fd;
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                fd = this.mService.getWallpaper(context.getOpPackageName(), this, 1, new Bundle(), userId);
                if (fd != null) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        if (hardware) {
                            options.inPreferredConfig = Bitmap.Config.HARDWARE;
                        }
                        Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), (Rect) null, options);
                        IoUtils.closeQuietly(fd);
                        return decodeFileDescriptor;
                    } catch (OutOfMemoryError e) {
                        Log.w(WallpaperManager.TAG, "Can't decode file", e);
                        IoUtils.closeQuietly(fd);
                    }
                }
                return null;
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            } catch (Throwable th) {
                IoUtils.closeQuietly(fd);
                throw th;
            }
        }

        private Bitmap getDefaultWallpaper(Context context, int which) {
            InputStream is = WallpaperManager.openDefaultWallpaper(context, which);
            if (is != null) {
                try {
                    return BitmapFactory.decodeStream(is, (Rect) null, new BitmapFactory.Options());
                } catch (OutOfMemoryError e) {
                    Log.w(WallpaperManager.TAG, "Can't decode stream", e);
                } finally {
                    IoUtils.closeQuietly(is);
                }
            }
            return null;
        }
    }

    static void initGlobals(IWallpaperManager service, Looper looper) {
        synchronized (sSync) {
            if (sGlobals == null) {
                sGlobals = new Globals(service, looper);
            }
        }
    }

    WallpaperManager(IWallpaperManager service, Context context, Handler handler) {
        this.mContext = context;
        initGlobals(service, context.getMainLooper());
    }

    public static WallpaperManager getInstance(Context context) {
        return (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
    }

    @UnsupportedAppUsage
    public IWallpaperManager getIWallpaperManager() {
        return sGlobals.mService;
    }

    public Drawable getDrawable() {
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, true, 1);
        if (bm == null) {
            return null;
        }
        Drawable dr = new BitmapDrawable(this.mContext.getResources(), bm);
        dr.setDither(false);
        return dr;
    }

    public Drawable getBuiltInDrawable() {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, 1);
    }

    public Drawable getBuiltInDrawable(int which) {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, which);
    }

    public Drawable getBuiltInDrawable(int outWidth, int outHeight, boolean scaleToFit, float horizontalAlignment, float verticalAlignment) {
        return getBuiltInDrawable(outWidth, outHeight, scaleToFit, horizontalAlignment, verticalAlignment, 1);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v0, resolved type: android.graphics.BitmapRegionDecoder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.graphics.Rect} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v1, resolved type: android.graphics.BitmapRegionDecoder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v2, resolved type: android.graphics.BitmapRegionDecoder} */
    /* JADX WARNING: type inference failed for: r1v3, types: [android.graphics.Rect, android.graphics.BitmapFactory$Options] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.drawable.Drawable getBuiltInDrawable(int r25, int r26, boolean r27, float r28, float r29, int r30) {
        /*
            r24 = this;
            r1 = r24
            r0 = r25
            r2 = r26
            r3 = r30
            android.app.WallpaperManager$Globals r4 = sGlobals
            android.app.IWallpaperManager r4 = r4.mService
            if (r4 == 0) goto L_0x01ff
            r4 = 1
            if (r3 == r4) goto L_0x001f
            r5 = 2
            if (r3 != r5) goto L_0x0017
            goto L_0x001f
        L_0x0017:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Must request exactly one kind of wallpaper"
            r4.<init>(r5)
            throw r4
        L_0x001f:
            android.content.Context r5 = r1.mContext
            android.content.res.Resources r5 = r5.getResources()
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = r28
            float r8 = java.lang.Math.min(r6, r7)
            r9 = 0
            float r7 = java.lang.Math.max(r9, r8)
            r8 = r29
            float r6 = java.lang.Math.min(r6, r8)
            float r6 = java.lang.Math.max(r9, r6)
            android.content.Context r8 = r1.mContext
            java.io.InputStream r8 = openDefaultWallpaper(r8, r3)
            r15 = 0
            if (r8 != 0) goto L_0x0065
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0064
            java.lang.String r4 = TAG
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "default wallpaper stream "
            r9.append(r10)
            r9.append(r3)
            java.lang.String r10 = " is null"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.w((java.lang.String) r4, (java.lang.String) r9)
        L_0x0064:
            return r15
        L_0x0065:
            java.io.BufferedInputStream r10 = new java.io.BufferedInputStream
            r10.<init>(r8)
            if (r0 <= 0) goto L_0x01f2
            if (r2 > 0) goto L_0x0073
            r22 = r6
            r1 = r15
            goto L_0x01f5
        L_0x0073:
            android.graphics.BitmapFactory$Options r11 = new android.graphics.BitmapFactory$Options
            r11.<init>()
            r11.inJustDecodeBounds = r4
            android.graphics.BitmapFactory.decodeStream(r10, r15, r11)
            int r12 = r11.outWidth
            if (r12 == 0) goto L_0x01e7
            int r12 = r11.outHeight
            if (r12 == 0) goto L_0x01e7
            int r14 = r11.outWidth
            int r13 = r11.outHeight
            java.io.BufferedInputStream r11 = new java.io.BufferedInputStream
            android.content.Context r12 = r1.mContext
            java.io.InputStream r12 = openDefaultWallpaper(r12, r3)
            r11.<init>(r12)
            r12 = r11
            int r11 = java.lang.Math.min(r14, r0)
            int r2 = java.lang.Math.min(r13, r2)
            if (r27 == 0) goto L_0x00b8
            r10 = r14
            r16 = r11
            r11 = r13
            r9 = r12
            r12 = r16
            r17 = r13
            r13 = r2
            r18 = r14
            r14 = r7
            r19 = r15
            r15 = r6
            android.graphics.RectF r0 = getMaxCropRect(r10, r11, r12, r13, r14, r15)
            r14 = r0
            r10 = r16
            goto L_0x00d4
        L_0x00b8:
            r16 = r11
            r9 = r12
            r17 = r13
            r18 = r14
            r19 = r15
            r10 = r16
            int r14 = r18 - r10
            float r0 = (float) r14
            float r0 = r0 * r7
            float r11 = (float) r10
            float r11 = r11 + r0
            int r13 = r17 - r2
            float r12 = (float) r13
            float r12 = r12 * r6
            float r13 = (float) r2
            float r13 = r13 + r12
            android.graphics.RectF r14 = new android.graphics.RectF
            r14.<init>(r0, r12, r11, r13)
        L_0x00d4:
            r11 = r14
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r12 = r0
            r11.roundOut(r12)
            int r0 = r12.width()
            if (r0 <= 0) goto L_0x01da
            int r0 = r12.height()
            if (r0 > 0) goto L_0x00f0
            r23 = r2
            r22 = r6
            goto L_0x01de
        L_0x00f0:
            int r0 = r12.width()
            int r0 = r0 / r10
            int r13 = r12.height()
            int r13 = r13 / r2
            int r13 = java.lang.Math.min(r0, r13)
            r14 = r19
            android.graphics.BitmapRegionDecoder r0 = android.graphics.BitmapRegionDecoder.newInstance((java.io.InputStream) r9, (boolean) r4)     // Catch:{ IOException -> 0x0106 }
            r14 = r0
            goto L_0x0110
        L_0x0106:
            r0 = move-exception
            r15 = r0
            r0 = r15
            java.lang.String r15 = TAG
            java.lang.String r4 = "cannot open region decoder for default wallpaper"
            android.util.Log.w((java.lang.String) r15, (java.lang.String) r4)
        L_0x0110:
            r0 = 0
            if (r14 == 0) goto L_0x0124
            android.graphics.BitmapFactory$Options r4 = new android.graphics.BitmapFactory$Options
            r4.<init>()
            r15 = 1
            if (r13 <= r15) goto L_0x011d
            r4.inSampleSize = r13
        L_0x011d:
            android.graphics.Bitmap r0 = r14.decodeRegion(r12, r4)
            r14.recycle()
        L_0x0124:
            if (r0 != 0) goto L_0x015e
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream
            android.content.Context r15 = r1.mContext
            java.io.InputStream r15 = openDefaultWallpaper(r15, r3)
            r4.<init>(r15)
            r9 = 0
            android.graphics.BitmapFactory$Options r15 = new android.graphics.BitmapFactory$Options
            r15.<init>()
            r1 = 1
            if (r13 <= r1) goto L_0x013c
            r15.inSampleSize = r13
        L_0x013c:
            r1 = r19
            android.graphics.Bitmap r9 = android.graphics.BitmapFactory.decodeStream(r4, r1, r15)
            if (r9 == 0) goto L_0x0159
            int r1 = r12.left
            r20 = r0
            int r0 = r12.top
            int r3 = r12.width()
            r21 = r4
            int r4 = r12.height()
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap((android.graphics.Bitmap) r9, (int) r1, (int) r0, (int) r3, (int) r4)
            goto L_0x0162
        L_0x0159:
            r20 = r0
            r21 = r4
            goto L_0x0162
        L_0x015e:
            r20 = r0
            r21 = r9
        L_0x0162:
            if (r0 != 0) goto L_0x016d
            java.lang.String r1 = TAG
            java.lang.String r3 = "cannot decode default wallpaper"
            android.util.Log.w((java.lang.String) r1, (java.lang.String) r3)
            r1 = 0
            return r1
        L_0x016d:
            if (r10 <= 0) goto L_0x01d0
            if (r2 <= 0) goto L_0x01d0
            int r1 = r0.getWidth()
            if (r1 != r10) goto L_0x0183
            int r1 = r0.getHeight()
            if (r1 == r2) goto L_0x017e
            goto L_0x0183
        L_0x017e:
            r23 = r2
            r22 = r6
            goto L_0x01d4
        L_0x0183:
            android.graphics.Matrix r1 = new android.graphics.Matrix
            r1.<init>()
            android.graphics.RectF r3 = new android.graphics.RectF
            int r4 = r0.getWidth()
            float r4 = (float) r4
            int r9 = r0.getHeight()
            float r9 = (float) r9
            r15 = 0
            r3.<init>(r15, r15, r4, r9)
            android.graphics.RectF r4 = new android.graphics.RectF
            float r9 = (float) r10
            r22 = r6
            float r6 = (float) r2
            r4.<init>(r15, r15, r9, r6)
            android.graphics.Matrix$ScaleToFit r6 = android.graphics.Matrix.ScaleToFit.FILL
            r1.setRectToRect(r3, r4, r6)
            float r6 = r4.width()
            int r6 = (int) r6
            float r9 = r4.height()
            int r9 = (int) r9
            android.graphics.Bitmap$Config r15 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r6 = android.graphics.Bitmap.createBitmap(r6, r9, r15)
            if (r6 == 0) goto L_0x01cd
            android.graphics.Canvas r9 = new android.graphics.Canvas
            r9.<init>((android.graphics.Bitmap) r6)
            android.graphics.Paint r15 = new android.graphics.Paint
            r15.<init>()
            r23 = r2
            r2 = 1
            r15.setFilterBitmap(r2)
            r9.drawBitmap(r0, r1, r15)
            r0 = r6
            goto L_0x01d4
        L_0x01cd:
            r23 = r2
            goto L_0x01d4
        L_0x01d0:
            r23 = r2
            r22 = r6
        L_0x01d4:
            android.graphics.drawable.BitmapDrawable r1 = new android.graphics.drawable.BitmapDrawable
            r1.<init>((android.content.res.Resources) r5, (android.graphics.Bitmap) r0)
            return r1
        L_0x01da:
            r23 = r2
            r22 = r6
        L_0x01de:
            java.lang.String r0 = TAG
            java.lang.String r1 = "crop has bad values for full size image"
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r1)
            r1 = 0
            return r1
        L_0x01e7:
            r22 = r6
            r1 = r15
            java.lang.String r3 = TAG
            java.lang.String r4 = "default wallpaper dimensions are 0"
            android.util.Log.e(r3, r4)
            return r1
        L_0x01f2:
            r22 = r6
            r1 = r15
        L_0x01f5:
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeStream(r10, r1, r1)
            android.graphics.drawable.BitmapDrawable r3 = new android.graphics.drawable.BitmapDrawable
            r3.<init>((android.content.res.Resources) r5, (android.graphics.Bitmap) r1)
            return r3
        L_0x01ff:
            r7 = r28
            r8 = r29
            java.lang.String r1 = TAG
            java.lang.String r3 = "WallpaperService not running"
            android.util.Log.w((java.lang.String) r1, (java.lang.String) r3)
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            android.os.DeadSystemException r3 = new android.os.DeadSystemException
            r3.<init>()
            r1.<init>(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.getBuiltInDrawable(int, int, boolean, float, float, int):android.graphics.drawable.Drawable");
    }

    private static RectF getMaxCropRect(int inWidth, int inHeight, int outWidth, int outHeight, float horizontalAlignment, float verticalAlignment) {
        RectF cropRect = new RectF();
        if (((float) inWidth) / ((float) inHeight) > ((float) outWidth) / ((float) outHeight)) {
            cropRect.top = 0.0f;
            cropRect.bottom = (float) inHeight;
            float cropWidth = ((float) outWidth) * (((float) inHeight) / ((float) outHeight));
            cropRect.left = (((float) inWidth) - cropWidth) * horizontalAlignment;
            cropRect.right = cropRect.left + cropWidth;
        } else {
            cropRect.left = 0.0f;
            cropRect.right = (float) inWidth;
            float cropHeight = ((float) outHeight) * (((float) inWidth) / ((float) outWidth));
            cropRect.top = (((float) inHeight) - cropHeight) * verticalAlignment;
            cropRect.bottom = cropRect.top + cropHeight;
        }
        return cropRect;
    }

    public Drawable peekDrawable() {
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, false, 1);
        if (bm == null) {
            return null;
        }
        Drawable dr = new BitmapDrawable(this.mContext.getResources(), bm);
        dr.setDither(false);
        return dr;
    }

    public Drawable getFastDrawable() {
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, true, 1);
        if (bm != null) {
            return new FastBitmapDrawable(bm);
        }
        return null;
    }

    public Drawable peekFastDrawable() {
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, false, 1);
        if (bm != null) {
            return new FastBitmapDrawable(bm);
        }
        return null;
    }

    @UnsupportedAppUsage
    public Bitmap getBitmap() {
        return getBitmap(false);
    }

    @UnsupportedAppUsage
    public Bitmap getBitmap(boolean hardware) {
        return getBitmapAsUser(this.mContext.getUserId(), hardware);
    }

    public Bitmap getBitmapAsUser(int userId, boolean hardware) {
        return sGlobals.peekWallpaperBitmap(this.mContext, true, 1, userId, hardware);
    }

    public ParcelFileDescriptor getWallpaperFile(int which) {
        return getWallpaperFile(which, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(OnColorsChangedListener listener, Handler handler) {
        addOnColorsChangedListener(listener, handler, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public void addOnColorsChangedListener(OnColorsChangedListener listener, Handler handler, int userId) {
        sGlobals.addOnColorsChangedListener(listener, handler, userId, this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener callback) {
        removeOnColorsChangedListener(callback, this.mContext.getUserId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener callback, int userId) {
        sGlobals.removeOnColorsChangedListener(callback, userId, this.mContext.getDisplayId());
    }

    public WallpaperColors getWallpaperColors(int which) {
        return getWallpaperColors(which, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public WallpaperColors getWallpaperColors(int which, int userId) {
        return sGlobals.getWallpaperColors(which, userId, this.mContext.getDisplayId());
    }

    @UnsupportedAppUsage
    public ParcelFileDescriptor getWallpaperFile(int which, int userId) {
        if (which != 1 && which != 2) {
            throw new IllegalArgumentException("Must request exactly one kind of wallpaper");
        } else if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.getWallpaper(this.mContext.getOpPackageName(), (IWallpaperManagerCallback) null, which, new Bundle(), userId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (SecurityException e2) {
                if (this.mContext.getApplicationInfo().targetSdkVersion < 27) {
                    Log.w(TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                    return null;
                }
                throw e2;
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public void forgetLoadedWallpaper() {
        sGlobals.forgetLoadedWallpaper();
    }

    public WallpaperInfo getWallpaperInfo() {
        return getWallpaperInfo(this.mContext.getUserId());
    }

    public WallpaperInfo getWallpaperInfo(int userId) {
        try {
            if (sGlobals.mService != null) {
                return sGlobals.mService.getWallpaperInfo(userId);
            }
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWallpaperId(int which) {
        return getWallpaperIdForUser(which, this.mContext.getUserId());
    }

    public int getWallpaperIdForUser(int which, int userId) {
        try {
            if (sGlobals.mService != null) {
                return sGlobals.mService.getWallpaperIdForUser(which, userId);
            }
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Intent getCropAndSetWallpaperIntent(Uri imageUri) {
        if (imageUri == null) {
            throw new IllegalArgumentException("Image URI must not be null");
        } else if ("content".equals(imageUri.getScheme())) {
            PackageManager packageManager = this.mContext.getPackageManager();
            Intent cropAndSetWallpaperIntent = new Intent(ACTION_CROP_AND_SET_WALLPAPER, imageUri);
            cropAndSetWallpaperIntent.addFlags(1);
            ResolveInfo resolvedHome = packageManager.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 65536);
            if (resolvedHome != null) {
                cropAndSetWallpaperIntent.setPackage(resolvedHome.activityInfo.packageName);
                if (packageManager.queryIntentActivities(cropAndSetWallpaperIntent, 0).size() > 0) {
                    return cropAndSetWallpaperIntent;
                }
            }
            cropAndSetWallpaperIntent.setPackage(this.mContext.getString(R.string.config_wallpaperCropperPackage));
            if (packageManager.queryIntentActivities(cropAndSetWallpaperIntent, 0).size() > 0) {
                return cropAndSetWallpaperIntent;
            }
            throw new IllegalArgumentException("Cannot use passed URI to set wallpaper; check that the type returned by ContentProvider matches image/*");
        } else {
            throw new IllegalArgumentException("Image URI must be of the content scheme type");
        }
    }

    public void setResource(int resid) throws IOException {
        setResource(resid, 3);
    }

    public int setResource(int resid, int which) throws IOException {
        FileOutputStream fos;
        if (sGlobals.mService != null) {
            Bundle result = new Bundle();
            WallpaperSetCompletion completion = new WallpaperSetCompletion();
            try {
                Resources resources = this.mContext.getResources();
                IWallpaperManager access$200 = sGlobals.mService;
                ParcelFileDescriptor fd = access$200.setWallpaper("res:" + resources.getResourceName(resid), this.mContext.getOpPackageName(), (Rect) null, false, result, which, completion, this.mContext.getUserId());
                if (fd != null) {
                    fos = null;
                    fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                    copyStreamToWallpaperFile(resources.openRawResource(resid), fos);
                    fos.close();
                    completion.waitForCompletion();
                    IoUtils.closeQuietly(fos);
                }
                return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (Throwable th) {
                IoUtils.closeQuietly(fos);
                throw th;
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public void setBitmap(Bitmap bitmap) throws IOException {
        setBitmap(bitmap, (Rect) null, true);
    }

    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, 3);
    }

    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, which, this.mContext.getUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, int userId) throws IOException {
        FileOutputStream fos;
        validateRect(visibleCropHint);
        if (sGlobals.mService != null) {
            Bundle result = new Bundle();
            WallpaperSetCompletion completion = new WallpaperSetCompletion();
            try {
                ParcelFileDescriptor fd = sGlobals.mService.setWallpaper((String) null, this.mContext.getOpPackageName(), visibleCropHint, allowBackup, result, which, completion, userId);
                if (fd != null) {
                    fos = null;
                    fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                    fullImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.close();
                    completion.waitForCompletion();
                    IoUtils.closeQuietly(fos);
                }
                return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (Throwable th) {
                IoUtils.closeQuietly(fos);
                throw th;
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    private final void validateRect(Rect rect) {
        if (rect != null && rect.isEmpty()) {
            throw new IllegalArgumentException("visibleCrop rectangle must be valid and non-empty");
        }
    }

    public void setStream(InputStream bitmapData) throws IOException {
        setStream(bitmapData, (Rect) null, true);
    }

    private void copyStreamToWallpaperFile(InputStream data, FileOutputStream fos) throws IOException {
        FileUtils.copy(data, (OutputStream) fos);
    }

    public int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup) throws IOException {
        return setStream(bitmapData, visibleCropHint, allowBackup, 3);
    }

    public int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which) throws IOException {
        FileOutputStream fos;
        validateRect(visibleCropHint);
        if (sGlobals.mService != null) {
            Bundle result = new Bundle();
            WallpaperSetCompletion completion = new WallpaperSetCompletion();
            try {
                ParcelFileDescriptor fd = sGlobals.mService.setWallpaper((String) null, this.mContext.getOpPackageName(), visibleCropHint, allowBackup, result, which, completion, this.mContext.getUserId());
                if (fd != null) {
                    fos = null;
                    fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                    copyStreamToWallpaperFile(bitmapData, fos);
                    fos.close();
                    completion.waitForCompletion();
                    IoUtils.closeQuietly(fos);
                }
                return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (Throwable th) {
                IoUtils.closeQuietly(fos);
                throw th;
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public boolean hasResourceWallpaper(int resid) {
        if (sGlobals.mService != null) {
            try {
                Resources resources = this.mContext.getResources();
                return sGlobals.mService.hasNamedWallpaper("res:" + resources.getResourceName(resid));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public int getDesiredMinimumWidth() {
        if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.getWidthHint(this.mContext.getDisplayId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public int getDesiredMinimumHeight() {
        if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.getHeightHint(this.mContext.getDisplayId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public void suggestDesiredDimensions(int minimumWidth, int minimumHeight) {
        int maximumTextureSize = 0;
        try {
            maximumTextureSize = SystemProperties.getInt("sys.max_texture_size", 0);
        } catch (Exception e) {
        }
        if (maximumTextureSize > 0 && (minimumWidth > maximumTextureSize || minimumHeight > maximumTextureSize)) {
            float aspect = ((float) minimumHeight) / ((float) minimumWidth);
            if (minimumWidth > minimumHeight) {
                minimumWidth = maximumTextureSize;
                minimumHeight = (int) (((double) (((float) minimumWidth) * aspect)) + 0.5d);
            } else {
                minimumHeight = maximumTextureSize;
                minimumWidth = (int) (((double) (((float) minimumHeight) / aspect)) + 0.5d);
            }
        }
        try {
            if (sGlobals.mService != null) {
                sGlobals.mService.setDimensionHints(minimumWidth, minimumHeight, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
            } else {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void setDisplayPadding(Rect padding) {
        try {
            if (sGlobals.mService != null) {
                sGlobals.mService.setDisplayPadding(padding, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
            } else {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDisplayOffset(IBinder windowToken, int x, int y) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperDisplayOffset(windowToken, x, y);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWallpaper() {
        clearWallpaper(2, this.mContext.getUserId());
        clearWallpaper(1, this.mContext.getUserId());
    }

    @SystemApi
    public void clearWallpaper(int which, int userId) {
        if (sGlobals.mService != null) {
            try {
                sGlobals.mService.clearWallpaper(this.mContext.getOpPackageName(), which, userId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    @SystemApi
    public boolean setWallpaperComponent(ComponentName name) {
        return setWallpaperComponent(name, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public boolean setWallpaperComponent(ComponentName name, int userId) {
        if (sGlobals.mService != null) {
            try {
                sGlobals.mService.setWallpaperComponentChecked(name, this.mContext.getOpPackageName(), userId);
                return true;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public void setWallpaperOffsets(IBinder windowToken, float xOffset, float yOffset) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(windowToken, xOffset, yOffset, this.mWallpaperXStep, this.mWallpaperYStep);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsetSteps(float xStep, float yStep) {
        this.mWallpaperXStep = xStep;
        this.mWallpaperYStep = yStep;
    }

    public void sendWallpaperCommand(IBinder windowToken, String action, int x, int y, int z, Bundle extras) {
        try {
            WindowManagerGlobal.getWindowSession().sendWallpaperCommand(windowToken, action, x, y, z, extras, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWallpaperSupported() {
        if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.isWallpaperSupported(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public boolean isSetWallpaperAllowed() {
        if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.isSetWallpaperAllowed(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public void clearWallpaperOffsets(IBinder windowToken) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(windowToken, -1.0f, -1.0f, -1.0f, -1.0f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clear() throws IOException {
        setStream(openDefaultWallpaper(this.mContext, 1), (Rect) null, false);
    }

    public void clear(int which) throws IOException {
        if ((which & 1) != 0) {
            clear();
        }
        if ((which & 2) != 0) {
            clearWallpaper(2, this.mContext.getUserId());
        }
    }

    @UnsupportedAppUsage
    public static InputStream openDefaultWallpaper(Context context, int which) {
        if (which == 2) {
            return null;
        }
        String path = SystemProperties.get(PROP_WALLPAPER);
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    return new FileInputStream(file);
                } catch (IOException e) {
                }
            }
        }
        try {
            return context.getResources().openRawResource(R.drawable.default_wallpaper);
        } catch (Resources.NotFoundException e2) {
            return null;
        }
    }

    public static ComponentName getDefaultWallpaperComponent(Context context) {
        ComponentName cn = null;
        String flat = SystemProperties.get(PROP_WALLPAPER_COMPONENT);
        if (!TextUtils.isEmpty(flat)) {
            cn = ComponentName.unflattenFromString(flat);
        }
        if (cn == null) {
            String flat2 = context.getString(R.string.default_wallpaper_component);
            if (!TextUtils.isEmpty(flat2)) {
                cn = ComponentName.unflattenFromString(flat2);
            }
        }
        if (cn == null) {
            return cn;
        }
        try {
            context.getPackageManager().getPackageInfo(cn.getPackageName(), (int) ArabicShaping.TASHKEEL_REPLACE_BY_TATWEEL);
            return cn;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public boolean setLockWallpaperCallback(IWallpaperManagerCallback callback) {
        if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.setLockWallpaperCallback(callback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    public boolean isWallpaperBackupEligible(int which) {
        if (sGlobals.mService != null) {
            try {
                return sGlobals.mService.isWallpaperBackupEligible(which, this.mContext.getUserId());
            } catch (RemoteException e) {
                String str = TAG;
                Log.e(str, "Exception querying wallpaper backup eligibility: " + e.getMessage());
                return false;
            }
        } else {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
    }

    private class WallpaperSetCompletion extends IWallpaperManagerCallback.Stub {
        final CountDownLatch mLatch = new CountDownLatch(1);

        public WallpaperSetCompletion() {
        }

        public void waitForCompletion() {
            try {
                this.mLatch.await(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
        }

        public void onWallpaperChanged() throws RemoteException {
            this.mLatch.countDown();
        }

        public void onWallpaperColorsChanged(WallpaperColors colors, int which, int userId) throws RemoteException {
            WallpaperManager.sGlobals.onWallpaperColorsChanged(colors, which, userId);
        }
    }

    public interface OnColorsChangedListener {
        void onColorsChanged(WallpaperColors wallpaperColors, int i);

        void onColorsChanged(WallpaperColors colors, int which, int userId) {
            onColorsChanged(colors, which);
        }
    }
}
