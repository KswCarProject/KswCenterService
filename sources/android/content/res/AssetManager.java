package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.content.res.XmlBlock;
import android.os.ParcelFileDescriptor;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class AssetManager implements AutoCloseable {
    public static final int ACCESS_BUFFER = 3;
    public static final int ACCESS_RANDOM = 1;
    public static final int ACCESS_STREAMING = 2;
    public static final int ACCESS_UNKNOWN = 0;
    private static final boolean DEBUG_REFS = false;
    private static final boolean FEATURE_FLAG_IDMAP2 = true;
    private static final String FRAMEWORK_APK_PATH = "/system/framework/framework-res.apk";
    private static final String TAG = "AssetManager";
    private static final ApkAssets[] sEmptyApkAssets = new ApkAssets[0];
    private static final Object sSync = new Object();
    @GuardedBy({"sSync"})
    @UnsupportedAppUsage
    static AssetManager sSystem = null;
    @GuardedBy({"sSync"})
    private static ApkAssets[] sSystemApkAssets = new ApkAssets[0];
    @GuardedBy({"sSync"})
    private static ArraySet<ApkAssets> sSystemApkAssetsSet;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public ApkAssets[] mApkAssets;
    @GuardedBy({"this"})
    private int mNumRefs;
    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    @UnsupportedAppUsage
    public long mObject;
    @GuardedBy({"this"})
    private final long[] mOffsets;
    @GuardedBy({"this"})
    private boolean mOpen;
    @GuardedBy({"this"})
    private HashMap<Long, RuntimeException> mRefStacks;
    @GuardedBy({"this"})
    private final TypedValue mValue;

    public static native String getAssetAllocations();

    @UnsupportedAppUsage
    public static native int getGlobalAssetCount();

    @UnsupportedAppUsage
    public static native int getGlobalAssetManagerCount();

    private static native void nativeApplyStyle(long j, long j2, int i, int i2, long j3, int[] iArr, long j4, long j5);

    /* access modifiers changed from: private */
    public static native void nativeAssetDestroy(long j);

    /* access modifiers changed from: private */
    public static native long nativeAssetGetLength(long j);

    /* access modifiers changed from: private */
    public static native long nativeAssetGetRemainingLength(long j);

    /* access modifiers changed from: private */
    public static native int nativeAssetRead(long j, byte[] bArr, int i, int i2);

    /* access modifiers changed from: private */
    public static native int nativeAssetReadChar(long j);

    /* access modifiers changed from: private */
    public static native long nativeAssetSeek(long j, long j2, int i);

    private static native int[] nativeAttributeResolutionStack(long j, long j2, int i, int i2, int i3);

    private static native long nativeCreate();

    private static native String[] nativeCreateIdmapsForStaticOverlaysTargetingAndroid();

    private static native void nativeDestroy(long j);

    private static native SparseArray<String> nativeGetAssignedPackageIdentifiers(long j);

    private static native String nativeGetLastResourceResolution(long j);

    private static native String[] nativeGetLocales(long j, boolean z);

    private static native Map nativeGetOverlayableMap(long j, String str);

    private static native int nativeGetResourceArray(long j, int i, int[] iArr);

    private static native int nativeGetResourceArraySize(long j, int i);

    private static native int nativeGetResourceBagValue(long j, int i, int i2, TypedValue typedValue);

    private static native String nativeGetResourceEntryName(long j, int i);

    private static native int nativeGetResourceIdentifier(long j, String str, String str2, String str3);

    private static native int[] nativeGetResourceIntArray(long j, int i);

    private static native String nativeGetResourceName(long j, int i);

    private static native String nativeGetResourcePackageName(long j, int i);

    private static native String[] nativeGetResourceStringArray(long j, int i);

    private static native int[] nativeGetResourceStringArrayInfo(long j, int i);

    private static native String nativeGetResourceTypeName(long j, int i);

    private static native int nativeGetResourceValue(long j, int i, short s, TypedValue typedValue, boolean z);

    private static native Configuration[] nativeGetSizeConfigurations(long j);

    private static native int[] nativeGetStyleAttributes(long j, int i);

    private static native String[] nativeList(long j, String str) throws IOException;

    private static native long nativeOpenAsset(long j, String str, int i);

    private static native ParcelFileDescriptor nativeOpenAssetFd(long j, String str, long[] jArr) throws IOException;

    private static native long nativeOpenNonAsset(long j, int i, String str, int i2);

    private static native ParcelFileDescriptor nativeOpenNonAssetFd(long j, int i, String str, long[] jArr) throws IOException;

    private static native long nativeOpenXmlAsset(long j, int i, String str);

    private static native boolean nativeResolveAttrs(long j, long j2, int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4);

    private static native boolean nativeRetrieveAttributes(long j, long j2, int[] iArr, int[] iArr2, int[] iArr3);

    /* access modifiers changed from: private */
    public static native void nativeSetApkAssets(long j, ApkAssets[] apkAssetsArr, boolean z);

    private static native void nativeSetConfiguration(long j, int i, int i2, String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17);

    private static native void nativeSetResourceResolutionLoggingEnabled(long j, boolean z);

    private static native void nativeThemeApplyStyle(long j, long j2, int i, boolean z);

    static native void nativeThemeClear(long j);

    private static native void nativeThemeCopy(long j, long j2, long j3, long j4);

    private static native long nativeThemeCreate(long j);

    private static native void nativeThemeDestroy(long j);

    private static native void nativeThemeDump(long j, long j2, int i, String str, String str2);

    private static native int nativeThemeGetAttributeValue(long j, long j2, int i, TypedValue typedValue, boolean z);

    static native int nativeThemeGetChangingConfigurations(long j);

    private static native void nativeVerifySystemIdmaps();

    public static class Builder {
        private ArrayList<ApkAssets> mUserApkAssets = new ArrayList<>();

        public Builder addApkAssets(ApkAssets apkAssets) {
            this.mUserApkAssets.add(apkAssets);
            return this;
        }

        public AssetManager build() {
            ApkAssets[] systemApkAssets = AssetManager.getSystem().getApkAssets();
            ApkAssets[] apkAssets = new ApkAssets[(systemApkAssets.length + this.mUserApkAssets.size())];
            System.arraycopy(systemApkAssets, 0, apkAssets, 0, systemApkAssets.length);
            int userApkAssetCount = this.mUserApkAssets.size();
            for (int i = 0; i < userApkAssetCount; i++) {
                apkAssets[systemApkAssets.length + i] = this.mUserApkAssets.get(i);
            }
            AssetManager assetManager = new AssetManager(false);
            ApkAssets[] unused = assetManager.mApkAssets = apkAssets;
            AssetManager.nativeSetApkAssets(assetManager.mObject, apkAssets, false);
            return assetManager;
        }
    }

    @UnsupportedAppUsage
    public AssetManager() {
        ApkAssets[] assets;
        this.mValue = new TypedValue();
        this.mOffsets = new long[2];
        this.mOpen = true;
        this.mNumRefs = 1;
        synchronized (sSync) {
            createSystemAssetsInZygoteLocked();
            assets = sSystemApkAssets;
        }
        this.mObject = nativeCreate();
        setApkAssets(assets, false);
    }

    private AssetManager(boolean sentinel) {
        this.mValue = new TypedValue();
        this.mOffsets = new long[2];
        this.mOpen = true;
        this.mNumRefs = 1;
        this.mObject = nativeCreate();
    }

    @GuardedBy({"sSync"})
    private static void createSystemAssetsInZygoteLocked() {
        if (sSystem == null) {
            try {
                ArrayList<ApkAssets> apkAssets = new ArrayList<>();
                apkAssets.add(ApkAssets.loadFromPath(FRAMEWORK_APK_PATH, true));
                String[] systemIdmapPaths = nativeCreateIdmapsForStaticOverlaysTargetingAndroid();
                if (systemIdmapPaths != null) {
                    for (String idmapPath : systemIdmapPaths) {
                        apkAssets.add(ApkAssets.loadOverlayFromPath(idmapPath, true));
                    }
                } else {
                    Log.w(TAG, "'idmap2 --scan' failed: no static=\"true\" overlays targeting \"android\" will be loaded");
                }
                sSystemApkAssetsSet = new ArraySet<>(apkAssets);
                sSystemApkAssets = (ApkAssets[]) apkAssets.toArray(new ApkAssets[apkAssets.size()]);
                sSystem = new AssetManager(true);
                sSystem.setApkAssets(sSystemApkAssets, false);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create system AssetManager", e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003d, code lost:
        if (r3 == null) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        $closeResource((java.lang.Throwable) null, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        $closeResource((java.lang.Throwable) null, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        libcore.io.IoUtils.closeQuietly(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0049, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004a, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004b, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004f, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
        r9 = r5;
        r5 = r4;
        r4 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void loadStaticRuntimeOverlays(java.util.ArrayList<android.content.res.ApkAssets> r10) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0066 }
            java.lang.String r1 = "/data/resource-cache/overlays.list"
            r0.<init>(r1)     // Catch:{ FileNotFoundException -> 0x0066 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ all -> 0x0061 }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ all -> 0x0061 }
            r2.<init>(r0)     // Catch:{ all -> 0x0061 }
            r1.<init>(r2)     // Catch:{ all -> 0x0061 }
            r2 = 0
            java.nio.channels.FileChannel r3 = r0.getChannel()     // Catch:{ Throwable -> 0x005b }
            r4 = 0
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r8 = 1
            java.nio.channels.FileLock r3 = r3.lock(r4, r6, r8)     // Catch:{ Throwable -> 0x005b }
        L_0x0025:
            java.lang.String r4 = r1.readLine()     // Catch:{ Throwable -> 0x004d, all -> 0x004a }
            r5 = r4
            if (r4 == 0) goto L_0x003d
            java.lang.String r4 = " "
            java.lang.String[] r4 = r5.split(r4)     // Catch:{ Throwable -> 0x004d, all -> 0x004a }
            r6 = 1
            r4 = r4[r6]     // Catch:{ Throwable -> 0x004d, all -> 0x004a }
            android.content.res.ApkAssets r6 = android.content.res.ApkAssets.loadOverlayFromPath(r4, r6)     // Catch:{ Throwable -> 0x004d, all -> 0x004a }
            r10.add(r6)     // Catch:{ Throwable -> 0x004d, all -> 0x004a }
            goto L_0x0025
        L_0x003d:
            if (r3 == 0) goto L_0x0042
            $closeResource(r2, r3)     // Catch:{ Throwable -> 0x005b }
        L_0x0042:
            $closeResource(r2, r1)     // Catch:{ all -> 0x0061 }
            libcore.io.IoUtils.closeQuietly(r0)
            return
        L_0x004a:
            r4 = move-exception
            r5 = r2
            goto L_0x0053
        L_0x004d:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x004f }
        L_0x004f:
            r5 = move-exception
            r9 = r5
            r5 = r4
            r4 = r9
        L_0x0053:
            if (r3 == 0) goto L_0x0058
            $closeResource(r5, r3)     // Catch:{ Throwable -> 0x005b }
        L_0x0058:
            throw r4     // Catch:{ Throwable -> 0x005b }
        L_0x0059:
            r3 = move-exception
            goto L_0x005d
        L_0x005b:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0059 }
        L_0x005d:
            $closeResource(r2, r1)     // Catch:{ all -> 0x0061 }
            throw r3     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r1 = move-exception
            libcore.io.IoUtils.closeQuietly(r0)
            throw r1
        L_0x0066:
            r0 = move-exception
            java.lang.String r1 = "AssetManager"
            java.lang.String r2 = "no overlays.list file found"
            android.util.Log.i(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.AssetManager.loadStaticRuntimeOverlays(java.util.ArrayList):void");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    @UnsupportedAppUsage
    public static AssetManager getSystem() {
        AssetManager assetManager;
        synchronized (sSync) {
            createSystemAssetsInZygoteLocked();
            assetManager = sSystem;
        }
        return assetManager;
    }

    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                decRefsLocked((long) hashCode());
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v7, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setApkAssets(android.content.res.ApkAssets[] r7, boolean r8) {
        /*
            r6 = this;
            java.lang.String r0 = "apkAssets"
            com.android.internal.util.Preconditions.checkNotNull(r7, r0)
            android.content.res.ApkAssets[] r0 = sSystemApkAssets
            int r0 = r0.length
            int r1 = r7.length
            int r0 = r0 + r1
            android.content.res.ApkAssets[] r0 = new android.content.res.ApkAssets[r0]
            android.content.res.ApkAssets[] r1 = sSystemApkAssets
            android.content.res.ApkAssets[] r2 = sSystemApkAssets
            int r2 = r2.length
            r3 = 0
            java.lang.System.arraycopy(r1, r3, r0, r3, r2)
            android.content.res.ApkAssets[] r1 = sSystemApkAssets
            int r1 = r1.length
            int r2 = r7.length
        L_0x0019:
            if (r3 >= r2) goto L_0x002d
            r4 = r7[r3]
            android.util.ArraySet<android.content.res.ApkAssets> r5 = sSystemApkAssetsSet
            boolean r5 = r5.contains(r4)
            if (r5 != 0) goto L_0x002a
            int r5 = r1 + 1
            r0[r1] = r4
            r1 = r5
        L_0x002a:
            int r3 = r3 + 1
            goto L_0x0019
        L_0x002d:
            int r2 = r0.length
            if (r1 == r2) goto L_0x0037
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r0, r1)
            r0 = r2
            android.content.res.ApkAssets[] r0 = (android.content.res.ApkAssets[]) r0
        L_0x0037:
            monitor-enter(r6)
            r6.ensureOpenLocked()     // Catch:{ all -> 0x004c }
            r6.mApkAssets = r0     // Catch:{ all -> 0x004c }
            long r2 = r6.mObject     // Catch:{ all -> 0x004c }
            android.content.res.ApkAssets[] r4 = r6.mApkAssets     // Catch:{ all -> 0x004c }
            nativeSetApkAssets(r2, r4, r8)     // Catch:{ all -> 0x004c }
            if (r8 == 0) goto L_0x004a
            r2 = -1
            r6.invalidateCachesLocked(r2)     // Catch:{ all -> 0x004c }
        L_0x004a:
            monitor-exit(r6)     // Catch:{ all -> 0x004c }
            return
        L_0x004c:
            r2 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x004c }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.AssetManager.setApkAssets(android.content.res.ApkAssets[], boolean):void");
    }

    private void invalidateCachesLocked(int diff) {
    }

    @UnsupportedAppUsage
    public ApkAssets[] getApkAssets() {
        synchronized (this) {
            if (!this.mOpen) {
                return sEmptyApkAssets;
            }
            ApkAssets[] apkAssetsArr = this.mApkAssets;
            return apkAssetsArr;
        }
    }

    public String[] getApkPaths() {
        synchronized (this) {
            if (!this.mOpen) {
                return new String[0];
            }
            String[] paths = new String[this.mApkAssets.length];
            int count = this.mApkAssets.length;
            for (int i = 0; i < count; i++) {
                paths[i] = this.mApkAssets[i].getAssetPath();
            }
            return paths;
        }
    }

    public int findCookieForPath(String path) {
        Preconditions.checkNotNull(path, "path");
        synchronized (this) {
            ensureValidLocked();
            int count = this.mApkAssets.length;
            for (int i = 0; i < count; i++) {
                if (path.equals(this.mApkAssets[i].getAssetPath())) {
                    int i2 = i + 1;
                    return i2;
                }
            }
            return 0;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public int addAssetPath(String path) {
        return addAssetPathInternal(path, false, false);
    }

    @Deprecated
    @UnsupportedAppUsage
    public int addAssetPathAsSharedLibrary(String path) {
        return addAssetPathInternal(path, false, true);
    }

    @Deprecated
    @UnsupportedAppUsage
    public int addOverlayPath(String path) {
        return addAssetPathInternal(path, true, false);
    }

    private int addAssetPathInternal(String path, boolean overlay, boolean appAsLib) {
        ApkAssets assets;
        Preconditions.checkNotNull(path, "path");
        synchronized (this) {
            ensureOpenLocked();
            int count = this.mApkAssets.length;
            for (int i = 0; i < count; i++) {
                if (this.mApkAssets[i].getAssetPath().equals(path)) {
                    int i2 = i + 1;
                    return i2;
                }
            }
            if (overlay) {
                try {
                    assets = ApkAssets.loadOverlayFromPath("/data/resource-cache/" + path.substring(1).replace('/', '@') + "@idmap", false);
                } catch (IOException e) {
                    return 0;
                }
            } else {
                assets = ApkAssets.loadFromPath(path, false, appAsLib);
            }
            this.mApkAssets = (ApkAssets[]) Arrays.copyOf(this.mApkAssets, count + 1);
            this.mApkAssets[count] = assets;
            nativeSetApkAssets(this.mObject, this.mApkAssets, true);
            invalidateCachesLocked(-1);
            int i3 = count + 1;
            return i3;
        }
    }

    @GuardedBy({"this"})
    private void ensureValidLocked() {
        if (this.mObject == 0) {
            throw new RuntimeException("AssetManager has been destroyed");
        }
    }

    @GuardedBy({"this"})
    private void ensureOpenLocked() {
        if (!this.mOpen) {
            throw new RuntimeException("AssetManager has been closed");
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
        return true;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean getResourceValue(int r8, int r9, android.util.TypedValue r10, boolean r11) {
        /*
            r7 = this;
            java.lang.String r0 = "outValue"
            com.android.internal.util.Preconditions.checkNotNull(r10, r0)
            monitor-enter(r7)
            r7.ensureValidLocked()     // Catch:{ all -> 0x0037 }
            long r1 = r7.mObject     // Catch:{ all -> 0x0037 }
            short r4 = (short) r9     // Catch:{ all -> 0x0037 }
            r3 = r8
            r5 = r10
            r6 = r11
            int r0 = nativeGetResourceValue(r1, r3, r4, r5, r6)     // Catch:{ all -> 0x0037 }
            if (r0 > 0) goto L_0x0019
            r1 = 0
            monitor-exit(r7)     // Catch:{ all -> 0x0037 }
            return r1
        L_0x0019:
            int r1 = r10.changingConfigurations     // Catch:{ all -> 0x0037 }
            int r1 = android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(r1)     // Catch:{ all -> 0x0037 }
            r10.changingConfigurations = r1     // Catch:{ all -> 0x0037 }
            int r1 = r10.type     // Catch:{ all -> 0x0037 }
            r2 = 3
            if (r1 != r2) goto L_0x0034
            android.content.res.ApkAssets[] r1 = r7.mApkAssets     // Catch:{ all -> 0x0037 }
            int r2 = r0 + -1
            r1 = r1[r2]     // Catch:{ all -> 0x0037 }
            int r2 = r10.data     // Catch:{ all -> 0x0037 }
            java.lang.CharSequence r1 = r1.getStringFromPool(r2)     // Catch:{ all -> 0x0037 }
            r10.string = r1     // Catch:{ all -> 0x0037 }
        L_0x0034:
            monitor-exit(r7)     // Catch:{ all -> 0x0037 }
            r1 = 1
            return r1
        L_0x0037:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0037 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.AssetManager.getResourceValue(int, int, android.util.TypedValue, boolean):boolean");
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public CharSequence getResourceText(int resId) {
        synchronized (this) {
            TypedValue outValue = this.mValue;
            if (!getResourceValue(resId, 0, outValue, true)) {
                return null;
            }
            CharSequence coerceToString = outValue.coerceToString();
            return coerceToString;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public CharSequence getResourceBagText(int resId, int bagEntryId) {
        synchronized (this) {
            ensureValidLocked();
            TypedValue outValue = this.mValue;
            int cookie = nativeGetResourceBagValue(this.mObject, resId, bagEntryId, outValue);
            if (cookie <= 0) {
                return null;
            }
            outValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(outValue.changingConfigurations);
            if (outValue.type == 3) {
                CharSequence stringFromPool = this.mApkAssets[cookie - 1].getStringFromPool(outValue.data);
                return stringFromPool;
            }
            CharSequence coerceToString = outValue.coerceToString();
            return coerceToString;
        }
    }

    /* access modifiers changed from: package-private */
    public int getResourceArraySize(int resId) {
        int nativeGetResourceArraySize;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceArraySize = nativeGetResourceArraySize(this.mObject, resId);
        }
        return nativeGetResourceArraySize;
    }

    /* access modifiers changed from: package-private */
    public int getResourceArray(int resId, int[] outData) {
        int nativeGetResourceArray;
        Preconditions.checkNotNull(outData, "outData");
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceArray = nativeGetResourceArray(this.mObject, resId, outData);
        }
        return nativeGetResourceArray;
    }

    /* access modifiers changed from: package-private */
    public String[] getResourceStringArray(int resId) {
        String[] nativeGetResourceStringArray;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceStringArray = nativeGetResourceStringArray(this.mObject, resId);
        }
        return nativeGetResourceStringArray;
    }

    /* access modifiers changed from: package-private */
    public CharSequence[] getResourceTextArray(int resId) {
        synchronized (this) {
            ensureValidLocked();
            int[] rawInfoArray = nativeGetResourceStringArrayInfo(this.mObject, resId);
            if (rawInfoArray == null) {
                return null;
            }
            int rawInfoArrayLen = rawInfoArray.length;
            CharSequence[] retArray = new CharSequence[(rawInfoArrayLen / 2)];
            int i = 0;
            int j = 0;
            while (i < rawInfoArrayLen) {
                int cookie = rawInfoArray[i];
                int index = rawInfoArray[i + 1];
                retArray[j] = (index < 0 || cookie <= 0) ? null : this.mApkAssets[cookie - 1].getStringFromPool(index);
                i += 2;
                j++;
            }
            return retArray;
        }
    }

    /* access modifiers changed from: package-private */
    public int[] getResourceIntArray(int resId) {
        int[] nativeGetResourceIntArray;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceIntArray = nativeGetResourceIntArray(this.mObject, resId);
        }
        return nativeGetResourceIntArray;
    }

    /* access modifiers changed from: package-private */
    public int[] getStyleAttributes(int resId) {
        int[] nativeGetStyleAttributes;
        synchronized (this) {
            ensureValidLocked();
            nativeGetStyleAttributes = nativeGetStyleAttributes(this.mObject, resId);
        }
        return nativeGetStyleAttributes;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean getThemeValue(long r9, int r11, android.util.TypedValue r12, boolean r13) {
        /*
            r8 = this;
            java.lang.String r0 = "outValue"
            com.android.internal.util.Preconditions.checkNotNull(r12, r0)
            monitor-enter(r8)
            r8.ensureValidLocked()     // Catch:{ all -> 0x0037 }
            long r1 = r8.mObject     // Catch:{ all -> 0x0037 }
            r3 = r9
            r5 = r11
            r6 = r12
            r7 = r13
            int r0 = nativeThemeGetAttributeValue(r1, r3, r5, r6, r7)     // Catch:{ all -> 0x0037 }
            if (r0 > 0) goto L_0x0019
            r1 = 0
            monitor-exit(r8)     // Catch:{ all -> 0x0037 }
            return r1
        L_0x0019:
            int r1 = r12.changingConfigurations     // Catch:{ all -> 0x0037 }
            int r1 = android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(r1)     // Catch:{ all -> 0x0037 }
            r12.changingConfigurations = r1     // Catch:{ all -> 0x0037 }
            int r1 = r12.type     // Catch:{ all -> 0x0037 }
            r2 = 3
            if (r1 != r2) goto L_0x0034
            android.content.res.ApkAssets[] r1 = r8.mApkAssets     // Catch:{ all -> 0x0037 }
            int r2 = r0 + -1
            r1 = r1[r2]     // Catch:{ all -> 0x0037 }
            int r2 = r12.data     // Catch:{ all -> 0x0037 }
            java.lang.CharSequence r1 = r1.getStringFromPool(r2)     // Catch:{ all -> 0x0037 }
            r12.string = r1     // Catch:{ all -> 0x0037 }
        L_0x0034:
            monitor-exit(r8)     // Catch:{ all -> 0x0037 }
            r1 = 1
            return r1
        L_0x0037:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0037 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.AssetManager.getThemeValue(long, int, android.util.TypedValue, boolean):boolean");
    }

    /* access modifiers changed from: package-private */
    public void dumpTheme(long theme, int priority, String tag, String prefix) {
        synchronized (this) {
            ensureValidLocked();
            nativeThemeDump(this.mObject, theme, priority, tag, prefix);
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public String getResourceName(int resId) {
        String nativeGetResourceName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceName = nativeGetResourceName(this.mObject, resId);
        }
        return nativeGetResourceName;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public String getResourcePackageName(int resId) {
        String nativeGetResourcePackageName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourcePackageName = nativeGetResourcePackageName(this.mObject, resId);
        }
        return nativeGetResourcePackageName;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public String getResourceTypeName(int resId) {
        String nativeGetResourceTypeName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceTypeName = nativeGetResourceTypeName(this.mObject, resId);
        }
        return nativeGetResourceTypeName;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public String getResourceEntryName(int resId) {
        String nativeGetResourceEntryName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceEntryName = nativeGetResourceEntryName(this.mObject, resId);
        }
        return nativeGetResourceEntryName;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int getResourceIdentifier(String name, String defType, String defPackage) {
        int nativeGetResourceIdentifier;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceIdentifier = nativeGetResourceIdentifier(this.mObject, name, defType, defPackage);
        }
        return nativeGetResourceIdentifier;
    }

    public void setResourceResolutionLoggingEnabled(boolean enabled) {
        synchronized (this) {
            ensureValidLocked();
            nativeSetResourceResolutionLoggingEnabled(this.mObject, enabled);
        }
    }

    public String getLastResourceResolution() {
        String nativeGetLastResourceResolution;
        synchronized (this) {
            ensureValidLocked();
            nativeGetLastResourceResolution = nativeGetLastResourceResolution(this.mObject);
        }
        return nativeGetLastResourceResolution;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getPooledStringForCookie(int cookie, int id) {
        return getApkAssets()[cookie - 1].getStringFromPool(id);
    }

    public InputStream open(String fileName) throws IOException {
        return open(fileName, 2);
    }

    public InputStream open(String fileName, int accessMode) throws IOException {
        AssetInputStream assetInputStream;
        Preconditions.checkNotNull(fileName, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long asset = nativeOpenAsset(this.mObject, fileName, accessMode);
            if (asset != 0) {
                assetInputStream = new AssetInputStream(asset);
                incRefsLocked((long) assetInputStream.hashCode());
            } else {
                throw new FileNotFoundException("Asset file: " + fileName);
            }
        }
        return assetInputStream;
    }

    public AssetFileDescriptor openFd(String fileName) throws IOException {
        AssetFileDescriptor assetFileDescriptor;
        Preconditions.checkNotNull(fileName, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            ParcelFileDescriptor pfd = nativeOpenAssetFd(this.mObject, fileName, this.mOffsets);
            if (pfd != null) {
                assetFileDescriptor = new AssetFileDescriptor(pfd, this.mOffsets[0], this.mOffsets[1]);
            } else {
                throw new FileNotFoundException("Asset file: " + fileName);
            }
        }
        return assetFileDescriptor;
    }

    public String[] list(String path) throws IOException {
        String[] nativeList;
        Preconditions.checkNotNull(path, "path");
        synchronized (this) {
            ensureValidLocked();
            nativeList = nativeList(this.mObject, path);
        }
        return nativeList;
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(String fileName) throws IOException {
        return openNonAsset(0, fileName, 2);
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(String fileName, int accessMode) throws IOException {
        return openNonAsset(0, fileName, accessMode);
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(int cookie, String fileName) throws IOException {
        return openNonAsset(cookie, fileName, 2);
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(int cookie, String fileName, int accessMode) throws IOException {
        AssetInputStream assetInputStream;
        Preconditions.checkNotNull(fileName, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long asset = nativeOpenNonAsset(this.mObject, cookie, fileName, accessMode);
            if (asset != 0) {
                assetInputStream = new AssetInputStream(asset);
                incRefsLocked((long) assetInputStream.hashCode());
            } else {
                throw new FileNotFoundException("Asset absolute file: " + fileName);
            }
        }
        return assetInputStream;
    }

    public AssetFileDescriptor openNonAssetFd(String fileName) throws IOException {
        return openNonAssetFd(0, fileName);
    }

    public AssetFileDescriptor openNonAssetFd(int cookie, String fileName) throws IOException {
        AssetFileDescriptor assetFileDescriptor;
        Preconditions.checkNotNull(fileName, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            ParcelFileDescriptor pfd = nativeOpenNonAssetFd(this.mObject, cookie, fileName, this.mOffsets);
            if (pfd != null) {
                assetFileDescriptor = new AssetFileDescriptor(pfd, this.mOffsets[0], this.mOffsets[1]);
            } else {
                throw new FileNotFoundException("Asset absolute file: " + fileName);
            }
        }
        return assetFileDescriptor;
    }

    public XmlResourceParser openXmlResourceParser(String fileName) throws IOException {
        return openXmlResourceParser(0, fileName);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001e, code lost:
        if (r0 != null) goto L_0x0020;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0020, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0023, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.content.res.XmlResourceParser openXmlResourceParser(int r6, java.lang.String r7) throws java.io.IOException {
        /*
            r5 = this;
            android.content.res.XmlBlock r0 = r5.openXmlBlockAsset(r6, r7)
            r1 = 0
            android.content.res.XmlResourceParser r2 = r0.newParser()     // Catch:{ Throwable -> 0x001c }
            if (r2 == 0) goto L_0x0012
            if (r0 == 0) goto L_0x0011
            $closeResource(r1, r0)
        L_0x0011:
            return r2
        L_0x0012:
            java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch:{ Throwable -> 0x001c }
            java.lang.String r4 = "block.newParser() returned a null parser"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x001c }
            throw r3     // Catch:{ Throwable -> 0x001c }
        L_0x001a:
            r2 = move-exception
            goto L_0x001e
        L_0x001c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001a }
        L_0x001e:
            if (r0 == 0) goto L_0x0023
            $closeResource(r1, r0)
        L_0x0023:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.AssetManager.openXmlResourceParser(int, java.lang.String):android.content.res.XmlResourceParser");
    }

    /* access modifiers changed from: package-private */
    public XmlBlock openXmlBlockAsset(String fileName) throws IOException {
        return openXmlBlockAsset(0, fileName);
    }

    /* access modifiers changed from: package-private */
    public XmlBlock openXmlBlockAsset(int cookie, String fileName) throws IOException {
        XmlBlock block;
        Preconditions.checkNotNull(fileName, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long xmlBlock = nativeOpenXmlAsset(this.mObject, cookie, fileName);
            if (xmlBlock != 0) {
                block = new XmlBlock(this, xmlBlock);
                incRefsLocked((long) block.hashCode());
            } else {
                throw new FileNotFoundException("Asset XML file: " + fileName);
            }
        }
        return block;
    }

    /* access modifiers changed from: package-private */
    public void xmlBlockGone(int id) {
        synchronized (this) {
            decRefsLocked((long) id);
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void applyStyle(long themePtr, int defStyleAttr, int defStyleRes, XmlBlock.Parser parser, int[] inAttrs, long outValuesAddress, long outIndicesAddress) {
        XmlBlock.Parser parser2 = parser;
        Preconditions.checkNotNull(inAttrs, "inAttrs");
        synchronized (this) {
            ensureValidLocked();
            nativeApplyStyle(this.mObject, themePtr, defStyleAttr, defStyleRes, parser2 != null ? parser2.mParseState : 0, inAttrs, outValuesAddress, outIndicesAddress);
        }
    }

    /* access modifiers changed from: package-private */
    public int[] getAttributeResolutionStack(long themePtr, int defStyleAttr, int defStyleRes, int xmlStyle) {
        int[] nativeAttributeResolutionStack;
        synchronized (this) {
            nativeAttributeResolutionStack = nativeAttributeResolutionStack(this.mObject, themePtr, xmlStyle, defStyleAttr, defStyleRes);
        }
        return nativeAttributeResolutionStack;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean resolveAttrs(long themePtr, int defStyleAttr, int defStyleRes, int[] inValues, int[] inAttrs, int[] outValues, int[] outIndices) {
        boolean nativeResolveAttrs;
        Preconditions.checkNotNull(inAttrs, "inAttrs");
        Preconditions.checkNotNull(outValues, "outValues");
        Preconditions.checkNotNull(outIndices, "outIndices");
        synchronized (this) {
            ensureValidLocked();
            nativeResolveAttrs = nativeResolveAttrs(this.mObject, themePtr, defStyleAttr, defStyleRes, inValues, inAttrs, outValues, outIndices);
        }
        return nativeResolveAttrs;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean retrieveAttributes(XmlBlock.Parser parser, int[] inAttrs, int[] outValues, int[] outIndices) {
        boolean nativeRetrieveAttributes;
        Preconditions.checkNotNull(parser, "parser");
        Preconditions.checkNotNull(inAttrs, "inAttrs");
        Preconditions.checkNotNull(outValues, "outValues");
        Preconditions.checkNotNull(outIndices, "outIndices");
        synchronized (this) {
            ensureValidLocked();
            nativeRetrieveAttributes = nativeRetrieveAttributes(this.mObject, parser.mParseState, inAttrs, outValues, outIndices);
        }
        return nativeRetrieveAttributes;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public long createTheme() {
        long themePtr;
        synchronized (this) {
            ensureValidLocked();
            themePtr = nativeThemeCreate(this.mObject);
            incRefsLocked(themePtr);
        }
        return themePtr;
    }

    /* access modifiers changed from: package-private */
    public void releaseTheme(long themePtr) {
        synchronized (this) {
            nativeThemeDestroy(themePtr);
            decRefsLocked(themePtr);
        }
    }

    /* access modifiers changed from: package-private */
    public void applyStyleToTheme(long themePtr, int resId, boolean force) {
        synchronized (this) {
            ensureValidLocked();
            nativeThemeApplyStyle(this.mObject, themePtr, resId, force);
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setThemeTo(long dstThemePtr, AssetManager srcAssetManager, long srcThemePtr) {
        synchronized (this) {
            ensureValidLocked();
            synchronized (srcAssetManager) {
                srcAssetManager.ensureValidLocked();
                nativeThemeCopy(this.mObject, dstThemePtr, srcAssetManager.mObject, srcThemePtr);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mObject != 0) {
            nativeDestroy(this.mObject);
        }
    }

    public final class AssetInputStream extends InputStream {
        private long mAssetNativePtr;
        private long mLength;
        private long mMarkPos;

        @UnsupportedAppUsage
        public final int getAssetInt() {
            throw new UnsupportedOperationException();
        }

        @UnsupportedAppUsage
        public final long getNativeAsset() {
            return this.mAssetNativePtr;
        }

        private AssetInputStream(long assetNativePtr) {
            this.mAssetNativePtr = assetNativePtr;
            this.mLength = AssetManager.nativeAssetGetLength(assetNativePtr);
        }

        public final int read() throws IOException {
            ensureOpen();
            return AssetManager.nativeAssetReadChar(this.mAssetNativePtr);
        }

        public final int read(byte[] b) throws IOException {
            ensureOpen();
            Preconditions.checkNotNull(b, "b");
            return AssetManager.nativeAssetRead(this.mAssetNativePtr, b, 0, b.length);
        }

        public final int read(byte[] b, int off, int len) throws IOException {
            ensureOpen();
            Preconditions.checkNotNull(b, "b");
            return AssetManager.nativeAssetRead(this.mAssetNativePtr, b, off, len);
        }

        public final long skip(long n) throws IOException {
            ensureOpen();
            long pos = AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0, 0);
            if (pos + n > this.mLength) {
                n = this.mLength - pos;
            }
            if (n > 0) {
                long unused = AssetManager.nativeAssetSeek(this.mAssetNativePtr, n, 0);
            }
            return n;
        }

        public final int available() throws IOException {
            ensureOpen();
            long len = AssetManager.nativeAssetGetRemainingLength(this.mAssetNativePtr);
            if (len > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            return (int) len;
        }

        public final boolean markSupported() {
            return true;
        }

        public final void mark(int readlimit) {
            ensureOpen();
            this.mMarkPos = AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0, 0);
        }

        public final void reset() throws IOException {
            ensureOpen();
            long unused = AssetManager.nativeAssetSeek(this.mAssetNativePtr, this.mMarkPos, -1);
        }

        public final void close() throws IOException {
            if (this.mAssetNativePtr != 0) {
                AssetManager.nativeAssetDestroy(this.mAssetNativePtr);
                this.mAssetNativePtr = 0;
                synchronized (AssetManager.this) {
                    AssetManager.this.decRefsLocked((long) hashCode());
                }
            }
        }

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            close();
        }

        private void ensureOpen() {
            if (this.mAssetNativePtr == 0) {
                throw new IllegalStateException("AssetInputStream is closed");
            }
        }
    }

    @UnsupportedAppUsage
    public boolean isUpToDate() {
        synchronized (this) {
            if (!this.mOpen) {
                return false;
            }
            for (ApkAssets apkAssets : this.mApkAssets) {
                if (!apkAssets.isUpToDate()) {
                    return false;
                }
            }
            return true;
        }
    }

    public String[] getLocales() {
        String[] nativeGetLocales;
        synchronized (this) {
            ensureValidLocked();
            nativeGetLocales = nativeGetLocales(this.mObject, false);
        }
        return nativeGetLocales;
    }

    public String[] getNonSystemLocales() {
        String[] nativeGetLocales;
        synchronized (this) {
            ensureValidLocked();
            nativeGetLocales = nativeGetLocales(this.mObject, true);
        }
        return nativeGetLocales;
    }

    /* access modifiers changed from: package-private */
    public Configuration[] getSizeConfigurations() {
        Configuration[] nativeGetSizeConfigurations;
        synchronized (this) {
            ensureValidLocked();
            nativeGetSizeConfigurations = nativeGetSizeConfigurations(this.mObject);
        }
        return nativeGetSizeConfigurations;
    }

    @UnsupportedAppUsage
    public void setConfiguration(int mcc, int mnc, String locale, int orientation, int touchscreen, int density, int keyboard, int keyboardHidden, int navigation, int screenWidth, int screenHeight, int smallestScreenWidthDp, int screenWidthDp, int screenHeightDp, int screenLayout, int uiMode, int colorMode, int majorVersion) {
        synchronized (this) {
            ensureValidLocked();
            nativeSetConfiguration(this.mObject, mcc, mnc, locale, orientation, touchscreen, density, keyboard, keyboardHidden, navigation, screenWidth, screenHeight, smallestScreenWidthDp, screenWidthDp, screenHeightDp, screenLayout, uiMode, colorMode, majorVersion);
        }
    }

    @UnsupportedAppUsage
    public SparseArray<String> getAssignedPackageIdentifiers() {
        SparseArray<String> nativeGetAssignedPackageIdentifiers;
        synchronized (this) {
            ensureValidLocked();
            nativeGetAssignedPackageIdentifiers = nativeGetAssignedPackageIdentifiers(this.mObject);
        }
        return nativeGetAssignedPackageIdentifiers;
    }

    @GuardedBy({"this"})
    public Map<String, String> getOverlayableMap(String packageName) {
        Map<String, String> nativeGetOverlayableMap;
        synchronized (this) {
            ensureValidLocked();
            nativeGetOverlayableMap = nativeGetOverlayableMap(this.mObject, packageName);
        }
        return nativeGetOverlayableMap;
    }

    @GuardedBy({"this"})
    private void incRefsLocked(long id) {
        this.mNumRefs++;
    }

    /* access modifiers changed from: private */
    @GuardedBy({"this"})
    public void decRefsLocked(long id) {
        this.mNumRefs--;
        if (this.mNumRefs == 0 && this.mObject != 0) {
            nativeDestroy(this.mObject);
            this.mObject = 0;
            this.mApkAssets = sEmptyApkAssets;
        }
    }
}
