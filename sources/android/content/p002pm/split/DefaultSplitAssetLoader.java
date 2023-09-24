package android.content.p002pm.split;

import android.content.p002pm.PackageParser;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.p007os.Build;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import libcore.io.IoUtils;

/* renamed from: android.content.pm.split.DefaultSplitAssetLoader */
/* loaded from: classes.dex */
public class DefaultSplitAssetLoader implements SplitAssetLoader {
    private final String mBaseCodePath;
    private AssetManager mCachedAssetManager;
    private final int mFlags;
    private final String[] mSplitCodePaths;

    public DefaultSplitAssetLoader(PackageParser.PackageLite pkg, int flags) {
        this.mBaseCodePath = pkg.baseCodePath;
        this.mSplitCodePaths = pkg.splitCodePaths;
        this.mFlags = flags;
    }

    private static ApkAssets loadApkAssets(String path, int flags) throws PackageParser.PackageParserException {
        if ((flags & 1) != 0 && !PackageParser.isApkPath(path)) {
            throw new PackageParser.PackageParserException(-100, "Invalid package file: " + path);
        }
        try {
            return ApkAssets.loadFromPath(path);
        } catch (IOException e) {
            throw new PackageParser.PackageParserException(-2, "Failed to load APK at path " + path, e);
        }
    }

    @Override // android.content.p002pm.split.SplitAssetLoader
    public AssetManager getBaseAssetManager() throws PackageParser.PackageParserException {
        if (this.mCachedAssetManager != null) {
            return this.mCachedAssetManager;
        }
        ApkAssets[] apkAssets = new ApkAssets[(this.mSplitCodePaths != null ? this.mSplitCodePaths.length : 0) + 1];
        int splitIdx = 0 + 1;
        apkAssets[0] = loadApkAssets(this.mBaseCodePath, this.mFlags);
        if (!ArrayUtils.isEmpty(this.mSplitCodePaths)) {
            String[] strArr = this.mSplitCodePaths;
            int length = strArr.length;
            int splitIdx2 = splitIdx;
            int splitIdx3 = 0;
            while (splitIdx3 < length) {
                String apkPath = strArr[splitIdx3];
                apkAssets[splitIdx2] = loadApkAssets(apkPath, this.mFlags);
                splitIdx3++;
                splitIdx2++;
            }
        }
        AssetManager assets = new AssetManager();
        assets.setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        assets.setApkAssets(apkAssets, false);
        this.mCachedAssetManager = assets;
        return this.mCachedAssetManager;
    }

    @Override // android.content.p002pm.split.SplitAssetLoader
    public AssetManager getSplitAssetManager(int splitIdx) throws PackageParser.PackageParserException {
        return getBaseAssetManager();
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        IoUtils.closeQuietly(this.mCachedAssetManager);
    }
}
