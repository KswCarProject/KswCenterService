package android.telephony.euicc;

import android.annotation.SystemApi;
import android.content.Context;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.service.euicc.EuiccProfileInfo;
import android.telephony.euicc.EuiccCardManager;
import android.util.Log;
import com.android.internal.telephony.euicc.IAuthenticateServerCallback;
import com.android.internal.telephony.euicc.ICancelSessionCallback;
import com.android.internal.telephony.euicc.IDeleteProfileCallback;
import com.android.internal.telephony.euicc.IDisableProfileCallback;
import com.android.internal.telephony.euicc.IEuiccCardController;
import com.android.internal.telephony.euicc.IGetAllProfilesCallback;
import com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.IGetEuiccChallengeCallback;
import com.android.internal.telephony.euicc.IGetEuiccInfo1Callback;
import com.android.internal.telephony.euicc.IGetEuiccInfo2Callback;
import com.android.internal.telephony.euicc.IGetProfileCallback;
import com.android.internal.telephony.euicc.IGetRulesAuthTableCallback;
import com.android.internal.telephony.euicc.IGetSmdsAddressCallback;
import com.android.internal.telephony.euicc.IListNotificationsCallback;
import com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback;
import com.android.internal.telephony.euicc.IPrepareDownloadCallback;
import com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback;
import com.android.internal.telephony.euicc.IResetMemoryCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationListCallback;
import com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.ISetNicknameCallback;
import com.android.internal.telephony.euicc.ISwitchToProfileCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class EuiccCardManager {
    public static final int CANCEL_REASON_END_USER_REJECTED = 0;
    public static final int CANCEL_REASON_POSTPONED = 1;
    public static final int CANCEL_REASON_PPR_NOT_ALLOWED = 3;
    public static final int CANCEL_REASON_TIMEOUT = 2;
    public static final int RESET_OPTION_DELETE_FIELD_LOADED_TEST_PROFILES = 2;
    public static final int RESET_OPTION_DELETE_OPERATIONAL_PROFILES = 1;
    public static final int RESET_OPTION_RESET_DEFAULT_SMDP_ADDRESS = 4;
    public static final int RESULT_CALLER_NOT_ALLOWED = -3;
    public static final int RESULT_EUICC_NOT_FOUND = -2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_UNKNOWN_ERROR = -1;
    private static final String TAG = "EuiccCardManager";
    private final Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface CancelReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface ResetOption {
    }

    /* loaded from: classes3.dex */
    public interface ResultCallback<T> {
        void onComplete(int i, T t);
    }

    public EuiccCardManager(Context context) {
        this.mContext = context;
    }

    private IEuiccCardController getIEuiccCardController() {
        return IEuiccCardController.Stub.asInterface(ServiceManager.getService("euicc_card_controller"));
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$1 */
    /* loaded from: classes3.dex */
    class BinderC24481 extends IGetAllProfilesCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24481(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetAllProfilesCallback
        public void onComplete(final int resultCode, final EuiccProfileInfo[] profiles) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$1$WCF3YSMl2TGGvaCq1GRblRP0j8M
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, profiles);
                }
            });
        }
    }

    public void requestAllProfiles(String cardId, Executor executor, ResultCallback<EuiccProfileInfo[]> callback) {
        try {
            getIEuiccCardController().getAllProfiles(this.mContext.getOpPackageName(), cardId, new BinderC24481(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getAllProfiles", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$2 */
    /* loaded from: classes3.dex */
    class BinderC24592 extends IGetProfileCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24592(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetProfileCallback
        public void onComplete(final int resultCode, final EuiccProfileInfo profile) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$2$TyPTPQ9XsUKfhC8yZUgq-jP-Ugs
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, profile);
                }
            });
        }
    }

    public void requestProfile(String cardId, String iccid, Executor executor, ResultCallback<EuiccProfileInfo> callback) {
        try {
            getIEuiccCardController().getProfile(this.mContext.getOpPackageName(), cardId, iccid, new BinderC24592(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$3 */
    /* loaded from: classes3.dex */
    class BinderC24633 extends IDisableProfileCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24633(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IDisableProfileCallback
        public void onComplete(final int resultCode) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$3$rixBHO-K-K3SJ1SVCAj8_82SxFE
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, null);
                }
            });
        }
    }

    public void disableProfile(String cardId, String iccid, boolean refresh, Executor executor, ResultCallback<Void> callback) {
        try {
            getIEuiccCardController().disableProfile(this.mContext.getOpPackageName(), cardId, iccid, refresh, new BinderC24633(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling disableProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$4 */
    /* loaded from: classes3.dex */
    class BinderC24644 extends ISwitchToProfileCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24644(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
        public void onComplete(final int resultCode, final EuiccProfileInfo profile) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$4$yTPBL-lMjfIGHQUa-JxPKPLvVR8
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, profile);
                }
            });
        }
    }

    public void switchToProfile(String cardId, String iccid, boolean refresh, Executor executor, ResultCallback<EuiccProfileInfo> callback) {
        try {
            getIEuiccCardController().switchToProfile(this.mContext.getOpPackageName(), cardId, iccid, refresh, new BinderC24644(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling switchToProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$5 */
    /* loaded from: classes3.dex */
    class BinderC24655 extends ISetNicknameCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24655(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISetNicknameCallback
        public void onComplete(final int resultCode) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$5$Tw9Ac3hC3rh6YoO0o4ip_fVYWq0
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, null);
                }
            });
        }
    }

    public void setNickname(String cardId, String iccid, String nickname, Executor executor, ResultCallback<Void> callback) {
        try {
            getIEuiccCardController().setNickname(this.mContext.getOpPackageName(), cardId, iccid, nickname, new BinderC24655(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling setNickname", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$6 */
    /* loaded from: classes3.dex */
    class BinderC24666 extends IDeleteProfileCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24666(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IDeleteProfileCallback
        public void onComplete(final int resultCode) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$6$K-EQz--QgHyjI0itfruTgIG7hos
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, null);
                }
            });
        }
    }

    public void deleteProfile(String cardId, String iccid, Executor executor, ResultCallback<Void> callback) {
        try {
            getIEuiccCardController().deleteProfile(this.mContext.getOpPackageName(), cardId, iccid, new BinderC24666(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling deleteProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$7 */
    /* loaded from: classes3.dex */
    class BinderC24677 extends IResetMemoryCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24677(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IResetMemoryCallback
        public void onComplete(final int resultCode) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$7$W9T937HBG-sD8BsVWGQ6kDb28dk
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, null);
                }
            });
        }
    }

    public void resetMemory(String cardId, int options, Executor executor, ResultCallback<Void> callback) {
        try {
            getIEuiccCardController().resetMemory(this.mContext.getOpPackageName(), cardId, options, new BinderC24677(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling resetMemory", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$8 */
    /* loaded from: classes3.dex */
    class BinderC24688 extends IGetDefaultSmdpAddressCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24688(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback
        public void onComplete(final int resultCode, final String address) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$8$A_S7upEqW6mofzD1_YkLBP5INOM
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, address);
                }
            });
        }
    }

    public void requestDefaultSmdpAddress(String cardId, Executor executor, ResultCallback<String> callback) {
        try {
            getIEuiccCardController().getDefaultSmdpAddress(this.mContext.getOpPackageName(), cardId, new BinderC24688(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getDefaultSmdpAddress", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$9 */
    /* loaded from: classes3.dex */
    class BinderC24699 extends IGetSmdsAddressCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC24699(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetSmdsAddressCallback
        public void onComplete(final int resultCode, final String address) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$9$cPEHH7JlllMuvBHJOu0A2hY4QyU
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, address);
                }
            });
        }
    }

    public void requestSmdsAddress(String cardId, Executor executor, ResultCallback<String> callback) {
        try {
            getIEuiccCardController().getSmdsAddress(this.mContext.getOpPackageName(), cardId, new BinderC24699(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getSmdsAddress", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDefaultSmdpAddress(String cardId, String defaultSmdpAddress, Executor executor, ResultCallback<Void> callback) {
        try {
            getIEuiccCardController().setDefaultSmdpAddress(this.mContext.getOpPackageName(), cardId, defaultSmdpAddress, new BinderC244910(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling setDefaultSmdpAddress", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$10 */
    /* loaded from: classes3.dex */
    class BinderC244910 extends ISetDefaultSmdpAddressCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC244910(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback
        public void onComplete(final int resultCode) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$10$tNYkM-c2PgDDurtC-iDXbvWa5_8
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, null);
                }
            });
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$11 */
    /* loaded from: classes3.dex */
    class BinderC245011 extends IGetRulesAuthTableCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245011(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
        public void onComplete(final int resultCode, final EuiccRulesAuthTable rat) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$11$IPX2CweBQhOCbcMAQ3yyU-N8fjQ
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, rat);
                }
            });
        }
    }

    public void requestRulesAuthTable(String cardId, Executor executor, ResultCallback<EuiccRulesAuthTable> callback) {
        try {
            getIEuiccCardController().getRulesAuthTable(this.mContext.getOpPackageName(), cardId, new BinderC245011(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getRulesAuthTable", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$12 */
    /* loaded from: classes3.dex */
    class BinderC245112 extends IGetEuiccChallengeCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245112(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccChallengeCallback
        public void onComplete(final int resultCode, final byte[] challenge) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$12$0dAgJQ0nijxpb9xsSsFqNuBkchU
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, challenge);
                }
            });
        }
    }

    public void requestEuiccChallenge(String cardId, Executor executor, ResultCallback<byte[]> callback) {
        try {
            getIEuiccCardController().getEuiccChallenge(this.mContext.getOpPackageName(), cardId, new BinderC245112(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getEuiccChallenge", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$13 */
    /* loaded from: classes3.dex */
    class BinderC245213 extends IGetEuiccInfo1Callback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245213(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccInfo1Callback
        public void onComplete(final int resultCode, final byte[] info) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$13$Kd-aeGG9po3MXhUohSTDAoC8kqI
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, info);
                }
            });
        }
    }

    public void requestEuiccInfo1(String cardId, Executor executor, ResultCallback<byte[]> callback) {
        try {
            getIEuiccCardController().getEuiccInfo1(this.mContext.getOpPackageName(), cardId, new BinderC245213(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getEuiccInfo1", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$14 */
    /* loaded from: classes3.dex */
    class BinderC245314 extends IGetEuiccInfo2Callback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245314(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccInfo2Callback
        public void onComplete(final int resultCode, final byte[] info) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$14$v9-1WsmNGIOXMEjPL4FGhZERO18
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, info);
                }
            });
        }
    }

    public void requestEuiccInfo2(String cardId, Executor executor, ResultCallback<byte[]> callback) {
        try {
            getIEuiccCardController().getEuiccInfo2(this.mContext.getOpPackageName(), cardId, new BinderC245314(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling getEuiccInfo2", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void authenticateServer(String cardId, String matchingId, byte[] serverSigned1, byte[] serverSignature1, byte[] euiccCiPkIdToBeUsed, byte[] serverCertificate, Executor executor, ResultCallback<byte[]> callback) {
        try {
        } catch (RemoteException e) {
            e = e;
        }
        try {
            getIEuiccCardController().authenticateServer(this.mContext.getOpPackageName(), cardId, matchingId, serverSigned1, serverSignature1, euiccCiPkIdToBeUsed, serverCertificate, new BinderC245415(executor, callback));
        } catch (RemoteException e2) {
            e = e2;
            Log.m69e(TAG, "Error calling authenticateServer", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$15 */
    /* loaded from: classes3.dex */
    class BinderC245415 extends IAuthenticateServerCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245415(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
        public void onComplete(final int resultCode, final byte[] response) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$15$_sAstfKdFkraAjC1faT-C0t_PgM
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, response);
                }
            });
        }
    }

    public void prepareDownload(String cardId, byte[] hashCc, byte[] smdpSigned2, byte[] smdpSignature2, byte[] smdpCertificate, Executor executor, ResultCallback<byte[]> callback) {
        try {
            getIEuiccCardController().prepareDownload(this.mContext.getOpPackageName(), cardId, hashCc, smdpSigned2, smdpSignature2, smdpCertificate, new BinderC245516(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling prepareDownload", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$16 */
    /* loaded from: classes3.dex */
    class BinderC245516 extends IPrepareDownloadCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245516(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IPrepareDownloadCallback
        public void onComplete(final int resultCode, final byte[] response) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$16$HzYHHKtPhCzsbbDlkzDxayy5kVM
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, response);
                }
            });
        }
    }

    public void loadBoundProfilePackage(String cardId, byte[] boundProfilePackage, Executor executor, ResultCallback<byte[]> callback) {
        try {
            getIEuiccCardController().loadBoundProfilePackage(this.mContext.getOpPackageName(), cardId, boundProfilePackage, new BinderC245617(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling loadBoundProfilePackage", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$17 */
    /* loaded from: classes3.dex */
    class BinderC245617 extends ILoadBoundProfilePackageCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245617(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback
        public void onComplete(final int resultCode, final byte[] response) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$17$uqb-wFIaxTmLnjUZZIY6J_DjHD0
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, response);
                }
            });
        }
    }

    public void cancelSession(String cardId, byte[] transactionId, int reason, Executor executor, ResultCallback<byte[]> callback) {
        try {
            getIEuiccCardController().cancelSession(this.mContext.getOpPackageName(), cardId, transactionId, reason, new BinderC245718(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling cancelSession", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$18 */
    /* loaded from: classes3.dex */
    class BinderC245718 extends ICancelSessionCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245718(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ICancelSessionCallback
        public void onComplete(final int resultCode, final byte[] response) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$18$2cHWlkpkAsYqhkpHbNv-QMqc0Ng
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, response);
                }
            });
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$19 */
    /* loaded from: classes3.dex */
    class BinderC245819 extends IListNotificationsCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC245819(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
        public void onComplete(final int resultCode, final EuiccNotification[] notifications) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$19$Gjn1FcgJf1Gqq6yBzVQLrvqlyg0
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, notifications);
                }
            });
        }
    }

    public void listNotifications(String cardId, int events, Executor executor, ResultCallback<EuiccNotification[]> callback) {
        try {
            getIEuiccCardController().listNotifications(this.mContext.getOpPackageName(), cardId, events, new BinderC245819(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling listNotifications", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$20 */
    /* loaded from: classes3.dex */
    class BinderC246020 extends IRetrieveNotificationListCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC246020(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IRetrieveNotificationListCallback
        public void onComplete(final int resultCode, final EuiccNotification[] notifications) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$20$BvkqzlF_5oeo0InlIzG65QhyNT0
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, notifications);
                }
            });
        }
    }

    public void retrieveNotificationList(String cardId, int events, Executor executor, ResultCallback<EuiccNotification[]> callback) {
        try {
            getIEuiccCardController().retrieveNotificationList(this.mContext.getOpPackageName(), cardId, events, new BinderC246020(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling retrieveNotificationList", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$21 */
    /* loaded from: classes3.dex */
    class BinderC246121 extends IRetrieveNotificationCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC246121(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IRetrieveNotificationCallback
        public void onComplete(final int resultCode, final EuiccNotification notification) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$21$srrmNYPqPTZF4uUZIcVq86p1JpU
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, notification);
                }
            });
        }
    }

    public void retrieveNotification(String cardId, int seqNumber, Executor executor, ResultCallback<EuiccNotification> callback) {
        try {
            getIEuiccCardController().retrieveNotification(this.mContext.getOpPackageName(), cardId, seqNumber, new BinderC246121(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling retrieveNotification", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeNotificationFromList(String cardId, int seqNumber, Executor executor, ResultCallback<Void> callback) {
        try {
            getIEuiccCardController().removeNotificationFromList(this.mContext.getOpPackageName(), cardId, seqNumber, new BinderC246222(executor, callback));
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error calling removeNotificationFromList", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$22 */
    /* loaded from: classes3.dex */
    class BinderC246222 extends IRemoveNotificationFromListCallback.Stub {
        final /* synthetic */ ResultCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC246222(Executor executor, ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback
        public void onComplete(final int resultCode) {
            Executor executor = this.val$executor;
            final ResultCallback resultCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.euicc.-$$Lambda$EuiccCardManager$22$JpF6e8fcw8874GZTA7KUvqsNhY8
                @Override // java.lang.Runnable
                public final void run() {
                    EuiccCardManager.ResultCallback.this.onComplete(resultCode, null);
                }
            });
        }
    }
}
