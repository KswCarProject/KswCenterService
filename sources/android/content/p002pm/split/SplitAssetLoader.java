package android.content.p002pm.split;

import android.content.p002pm.PackageParser;
import android.content.res.AssetManager;

/* renamed from: android.content.pm.split.SplitAssetLoader */
/* loaded from: classes.dex */
public interface SplitAssetLoader extends AutoCloseable {
    AssetManager getBaseAssetManager() throws PackageParser.PackageParserException;

    AssetManager getSplitAssetManager(int i) throws PackageParser.PackageParserException;
}
