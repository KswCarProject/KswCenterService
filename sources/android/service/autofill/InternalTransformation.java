package android.service.autofill;

import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.ibm.icu.text.PluralRules;
import java.util.ArrayList;

public abstract class InternalTransformation implements Transformation, Parcelable {
    private static final String TAG = "InternalTransformation";

    /* access modifiers changed from: package-private */
    public abstract void apply(ValueFinder valueFinder, RemoteViews remoteViews, int i) throws Exception;

    public static boolean batchApply(ValueFinder finder, RemoteViews template, ArrayList<Pair<Integer, InternalTransformation>> transformations) {
        int size = transformations.size();
        if (Helper.sDebug) {
            Log.d(TAG, "getPresentation(): applying " + size + " transformations");
        }
        int i = 0;
        while (i < size) {
            Pair<Integer, InternalTransformation> pair = transformations.get(i);
            int id = ((Integer) pair.first).intValue();
            InternalTransformation transformation = (InternalTransformation) pair.second;
            if (Helper.sDebug) {
                Log.d(TAG, "#" + i + PluralRules.KEYWORD_RULE_SEPARATOR + transformation);
            }
            try {
                transformation.apply(finder, template, id);
                i++;
            } catch (Exception e) {
                Log.e(TAG, "Could not apply transformation " + transformation + PluralRules.KEYWORD_RULE_SEPARATOR + e.getClass());
                return false;
            }
        }
        return true;
    }
}
