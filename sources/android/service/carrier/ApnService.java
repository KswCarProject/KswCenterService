package android.service.carrier;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.p007os.IBinder;
import android.util.Log;
import com.android.internal.telephony.IApnSourceService;
import com.ibm.icu.text.PluralRules;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public abstract class ApnService extends Service {
    private static final String LOG_TAG = "ApnService";
    private final IApnSourceService.Stub mBinder = new IApnSourceService.Stub() { // from class: android.service.carrier.ApnService.1
        @Override // com.android.internal.telephony.IApnSourceService
        public ContentValues[] getApns(int subId) {
            try {
                List<ContentValues> apns = ApnService.this.onRestoreApns(subId);
                return (ContentValues[]) apns.toArray(new ContentValues[apns.size()]);
            } catch (Exception e) {
                Log.m69e(ApnService.LOG_TAG, "Error in getApns for subId=" + subId + PluralRules.KEYWORD_RULE_SEPARATOR + e.getMessage(), e);
                return null;
            }
        }
    };

    public abstract List<ContentValues> onRestoreApns(int i);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
