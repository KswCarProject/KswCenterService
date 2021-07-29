package android.service.autofill;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.autofill.IAutofillFieldClassificationService;
import android.util.Log;
import android.view.autofill.AutofillValue;
import com.android.internal.util.function.pooled.PooledLambda;
import com.ibm.icu.text.PluralRules;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SystemApi
public abstract class AutofillFieldClassificationService extends Service {
    public static final String EXTRA_SCORES = "scores";
    public static final String REQUIRED_ALGORITHM_EDIT_DISTANCE = "EDIT_DISTANCE";
    public static final String REQUIRED_ALGORITHM_EXACT_MATCH = "EXACT_MATCH";
    public static final String SERVICE_INTERFACE = "android.service.autofill.AutofillFieldClassificationService";
    public static final String SERVICE_META_DATA_KEY_AVAILABLE_ALGORITHMS = "android.autofill.field_classification.available_algorithms";
    public static final String SERVICE_META_DATA_KEY_DEFAULT_ALGORITHM = "android.autofill.field_classification.default_algorithm";
    private static final String TAG = "AutofillFieldClassificationService";
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper(), (Handler.Callback) null, true);
    private AutofillFieldClassificationServiceWrapper mWrapper;

    /* access modifiers changed from: private */
    public void calculateScores(RemoteCallback callback, List<AutofillValue> actualValues, String[] userDataValues, String[] categoryIds, String defaultAlgorithm, Bundle defaultArgs, Map algorithms, Map args) {
        Bundle data = new Bundle();
        float[][] scores = onCalculateScores(actualValues, Arrays.asList(userDataValues), Arrays.asList(categoryIds), defaultAlgorithm, defaultArgs, algorithms, args);
        if (scores != null) {
            data.putParcelable(EXTRA_SCORES, new Scores(scores));
        }
        RemoteCallback remoteCallback = callback;
        callback.sendResult(data);
    }

    public void onCreate() {
        super.onCreate();
        this.mWrapper = new AutofillFieldClassificationServiceWrapper();
    }

    public IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    @SystemApi
    @Deprecated
    public float[][] onGetScores(String algorithm, Bundle algorithmOptions, List<AutofillValue> list, List<String> list2) {
        Log.e(TAG, "service implementation (" + getClass() + " does not implement onGetScores()");
        return null;
    }

    @SystemApi
    public float[][] onCalculateScores(List<AutofillValue> list, List<String> list2, List<String> list3, String defaultAlgorithm, Bundle defaultArgs, Map algorithms, Map args) {
        Log.e(TAG, "service implementation (" + getClass() + " does not implement onCalculateScore()");
        return null;
    }

    private final class AutofillFieldClassificationServiceWrapper extends IAutofillFieldClassificationService.Stub {
        private AutofillFieldClassificationServiceWrapper() {
        }

        public void calculateScores(RemoteCallback callback, List<AutofillValue> actualValues, String[] userDataValues, String[] categoryIds, String defaultAlgorithm, Bundle defaultArgs, Map algorithms, Map args) throws RemoteException {
            AutofillFieldClassificationService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AutofillFieldClassificationService$AutofillFieldClassificationServiceWrapper$mUalgFt87R5lup2LhB9vW49Xixs.INSTANCE, AutofillFieldClassificationService.this, callback, actualValues, userDataValues, categoryIds, defaultAlgorithm, defaultArgs, algorithms, args));
        }
    }

    public static final class Scores implements Parcelable {
        public static final Parcelable.Creator<Scores> CREATOR = new Parcelable.Creator<Scores>() {
            public Scores createFromParcel(Parcel parcel) {
                return new Scores(parcel);
            }

            public Scores[] newArray(int size) {
                return new Scores[size];
            }
        };
        public final float[][] scores;

        private Scores(Parcel parcel) {
            int size1 = parcel.readInt();
            int size2 = parcel.readInt();
            this.scores = (float[][]) Array.newInstance(float.class, new int[]{size1, size2});
            for (int i = 0; i < size1; i++) {
                for (int j = 0; j < size2; j++) {
                    this.scores[i][j] = parcel.readFloat();
                }
            }
        }

        private Scores(float[][] scores2) {
            this.scores = scores2;
        }

        public String toString() {
            int size1 = this.scores.length;
            int size2 = size1 > 0 ? this.scores[0].length : 0;
            StringBuilder sb = new StringBuilder("Scores [");
            sb.append(size1);
            sb.append("x");
            sb.append(size2);
            StringBuilder builder = sb.append("] ");
            for (int i = 0; i < size1; i++) {
                builder.append(i);
                builder.append(PluralRules.KEYWORD_RULE_SEPARATOR);
                builder.append(Arrays.toString(this.scores[i]));
                builder.append(' ');
            }
            return builder.toString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int flags) {
            int size1 = this.scores.length;
            int size2 = this.scores[0].length;
            parcel.writeInt(size1);
            parcel.writeInt(size2);
            for (int i = 0; i < size1; i++) {
                for (int j = 0; j < size2; j++) {
                    parcel.writeFloat(this.scores[i][j]);
                }
            }
        }
    }
}
