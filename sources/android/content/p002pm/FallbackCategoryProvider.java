package android.content.p002pm;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.p007os.SystemProperties;
import android.telephony.SmsManager;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.C3132R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* renamed from: android.content.pm.FallbackCategoryProvider */
/* loaded from: classes.dex */
public class FallbackCategoryProvider {
    private static final String TAG = "FallbackCategoryProvider";
    private static final ArrayMap<String, Integer> sFallbacks = new ArrayMap<>();

    public static void loadFallbacks() {
        sFallbacks.clear();
        if (SystemProperties.getBoolean("fw.ignore_fb_categories", false)) {
            Log.m72d(TAG, "Ignoring fallback categories");
            return;
        }
        AssetManager assets = new AssetManager();
        assets.addAssetPath("/system/framework/framework-res.apk");
        Resources res = new Resources(assets, null, null);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(res.openRawResource(C3132R.raw.fallback_categories)));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    if (line.charAt(0) != '#') {
                        String[] split = line.split(SmsManager.REGEX_PREFIX_DELIMITER);
                        if (split.length == 2) {
                            sFallbacks.put(split[0], Integer.valueOf(Integer.parseInt(split[1])));
                        }
                    }
                } else {
                    Log.m72d(TAG, "Found " + sFallbacks.size() + " fallback categories");
                    reader.close();
                    return;
                }
            }
        } catch (IOException | NumberFormatException e) {
            Log.m63w(TAG, "Failed to read fallback categories", e);
        }
    }

    public static int getFallbackCategory(String packageName) {
        return sFallbacks.getOrDefault(packageName, -1).intValue();
    }
}
