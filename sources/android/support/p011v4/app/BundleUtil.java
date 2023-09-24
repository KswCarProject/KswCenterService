package android.support.p011v4.app;

import android.p007os.Bundle;
import android.p007os.Parcelable;
import java.util.Arrays;

/* renamed from: android.support.v4.app.BundleUtil */
/* loaded from: classes3.dex */
class BundleUtil {
    BundleUtil() {
    }

    public static Bundle[] getBundleArrayFromBundle(Bundle bundle, String key) {
        Parcelable[] array = bundle.getParcelableArray(key);
        if ((array instanceof Bundle[]) || array == null) {
            return (Bundle[]) array;
        }
        Bundle[] typedArray = (Bundle[]) Arrays.copyOf(array, array.length, Bundle[].class);
        bundle.putParcelableArray(key, typedArray);
        return typedArray;
    }
}
