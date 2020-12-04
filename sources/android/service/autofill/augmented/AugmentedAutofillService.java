package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.SettingsStringUtil;
import android.service.autofill.augmented.IAugmentedAutofillService;
import android.service.autofill.augmented.PresentationParams;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAugmentedAutofillManagerClient;
import android.view.autofill.IAutofillWindowPresenter;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public abstract class AugmentedAutofillService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.autofill.augmented.AugmentedAutofillService";
    /* access modifiers changed from: private */
    public static final String TAG = AugmentedAutofillService.class.getSimpleName();
    static boolean sDebug = (!Build.IS_USER);
    static boolean sVerbose = false;
    private SparseArray<AutofillProxy> mAutofillProxies;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private final IAugmentedAutofillService mInterface = new IAugmentedAutofillService.Stub() {
        public void onConnected(boolean debug, boolean verbose) {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AugmentedAutofillService$1$4dXh5Zwc8KxDD9bV1LFhgo3zrgk.INSTANCE, AugmentedAutofillService.this, Boolean.valueOf(debug), Boolean.valueOf(verbose)));
        }

        public void onDisconnected() {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AugmentedAutofillService$1$D2Ct4Bd0D1M8vONZTBmU9zstEFI.INSTANCE, AugmentedAutofillService.this));
        }

        public void onFillRequest(int sessionId, IBinder client, int taskId, ComponentName componentName, AutofillId focusedId, AutofillValue focusedValue, long requestTime, IFillCallback callback) {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjwQ27Ij0.INSTANCE, AugmentedAutofillService.this, Integer.valueOf(sessionId), client, Integer.valueOf(taskId), componentName, focusedId, focusedValue, Long.valueOf(requestTime), callback));
        }

        public void onDestroyAllFillWindowsRequest() {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AugmentedAutofillService$1$LSvI4QN2NxJLegcZI0BFIvKwp6o.INSTANCE, AugmentedAutofillService.this));
        }
    };
    private ComponentName mServiceComponentName;

    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), (Handler.Callback) null, true);
    }

    public final IBinder onBind(Intent intent) {
        this.mServiceComponentName = intent.getComponent();
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        String str = TAG;
        Log.w(str, "Tried to bind to wrong intent (should be android.service.autofill.augmented.AugmentedAutofillService: " + intent);
        return null;
    }

    public boolean onUnbind(Intent intent) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$AugmentedAutofillService$zZAmNDLQX4rUV_yTGug25y4E6gA.INSTANCE, this));
        return false;
    }

    public void onConnected() {
    }

    public void onFillRequest(FillRequest request, CancellationSignal cancellationSignal, FillController controller, FillCallback callback) {
    }

    public void onDisconnected() {
    }

    /* access modifiers changed from: private */
    public void handleOnConnected(boolean debug, boolean verbose) {
        if (sDebug || debug) {
            String str = TAG;
            Log.d(str, "handleOnConnected(): debug=" + debug + ", verbose=" + verbose);
        }
        sDebug = debug;
        sVerbose = verbose;
        onConnected();
    }

    /* access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
    }

    /* access modifiers changed from: private */
    public void handleOnFillRequest(int sessionId, IBinder client, int taskId, ComponentName componentName, AutofillId focusedId, AutofillValue focusedValue, long requestTime, IFillCallback callback) {
        ICancellationSignal transport;
        AutofillProxy proxy;
        CancellationSignal cancellationSignal;
        IFillCallback iFillCallback;
        int i = sessionId;
        IFillCallback iFillCallback2 = callback;
        if (this.mAutofillProxies == null) {
            this.mAutofillProxies = new SparseArray<>();
        }
        ICancellationSignal transport2 = CancellationSignal.createTransport();
        CancellationSignal cancellationSignal2 = CancellationSignal.fromTransport(transport2);
        AutofillProxy proxy2 = this.mAutofillProxies.get(i);
        if (proxy2 == null) {
            CancellationSignal cancellationSignal3 = cancellationSignal2;
            transport = transport2;
            AutofillProxy proxy3 = new AutofillProxy(sessionId, client, taskId, this.mServiceComponentName, componentName, focusedId, focusedValue, requestTime, callback, cancellationSignal3);
            this.mAutofillProxies.put(i, proxy3);
            AutofillId autofillId = focusedId;
            AutofillValue autofillValue = focusedValue;
            proxy = proxy3;
            cancellationSignal = cancellationSignal3;
            iFillCallback = callback;
        } else {
            CancellationSignal cancellationSignal4 = cancellationSignal2;
            transport = transport2;
            if (sDebug) {
                String str = TAG;
                Log.d(str, "Reusing proxy for session " + i);
            }
            cancellationSignal = cancellationSignal4;
            iFillCallback = callback;
            proxy2.update(focusedId, focusedValue, iFillCallback, cancellationSignal);
            proxy = proxy2;
        }
        try {
            iFillCallback.onCancellable(transport);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        onFillRequest(new FillRequest(proxy), cancellationSignal, new FillController(proxy), new FillCallback(proxy));
    }

    /* access modifiers changed from: private */
    public void handleOnDestroyAllFillWindowsRequest() {
        if (this.mAutofillProxies != null) {
            int size = this.mAutofillProxies.size();
            for (int i = 0; i < size; i++) {
                int sessionId = this.mAutofillProxies.keyAt(i);
                AutofillProxy proxy = this.mAutofillProxies.valueAt(i);
                if (proxy == null) {
                    String str = TAG;
                    Log.w(str, "No proxy for session " + sessionId);
                    return;
                }
                if (proxy.mCallback != null) {
                    try {
                        if (!proxy.mCallback.isCompleted()) {
                            proxy.mCallback.cancel();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "failed to check current pending request status", e);
                    }
                }
                proxy.destroy();
            }
            this.mAutofillProxies.clear();
        }
    }

    /* access modifiers changed from: private */
    public void handleOnUnbind() {
        if (this.mAutofillProxies != null) {
            int size = this.mAutofillProxies.size();
            if (sDebug) {
                String str = TAG;
                Log.d(str, "onUnbind(): destroying " + size + " proxies");
            }
            for (int i = 0; i < size; i++) {
                AutofillProxy proxy = this.mAutofillProxies.valueAt(i);
                try {
                    proxy.destroy();
                } catch (Exception e) {
                    String str2 = TAG;
                    Log.w(str2, "error destroying " + proxy);
                }
            }
            this.mAutofillProxies = null;
        } else if (sDebug) {
            Log.d(TAG, "onUnbind(): no proxy to destroy");
        }
    }

    /* access modifiers changed from: protected */
    public final void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("Service component: ");
        pw.println(ComponentName.flattenToShortString(this.mServiceComponentName));
        if (this.mAutofillProxies != null) {
            int size = this.mAutofillProxies.size();
            pw.print("Number proxies: ");
            pw.println(size);
            for (int i = 0; i < size; i++) {
                int sessionId = this.mAutofillProxies.keyAt(i);
                pw.print(i);
                pw.print(") SessionId=");
                pw.print(sessionId);
                pw.println(SettingsStringUtil.DELIMITER);
                this.mAutofillProxies.valueAt(i).dump("  ", pw);
            }
        }
        dump(pw, args);
    }

    /* access modifiers changed from: protected */
    public void dump(PrintWriter pw, String[] args) {
        pw.print(getClass().getName());
        pw.println(": nothing to dump");
    }

    static final class AutofillProxy {
        static final int REPORT_EVENT_NO_RESPONSE = 1;
        static final int REPORT_EVENT_UI_DESTROYED = 3;
        static final int REPORT_EVENT_UI_SHOWN = 2;
        public final ComponentName componentName;
        /* access modifiers changed from: private */
        @GuardedBy({"mLock"})
        public IFillCallback mCallback;
        private CancellationSignal mCancellationSignal;
        private final IAugmentedAutofillManagerClient mClient;
        @GuardedBy({"mLock"})
        private FillWindow mFillWindow;
        private long mFirstOnSuccessTime;
        private final long mFirstRequestTime;
        @GuardedBy({"mLock"})
        private AutofillId mFocusedId;
        @GuardedBy({"mLock"})
        private AutofillValue mFocusedValue;
        @GuardedBy({"mLock"})
        private AutofillId mLastShownId;
        private final Object mLock;
        private String mServicePackageName;
        private final int mSessionId;
        @GuardedBy({"mLock"})
        private PresentationParams.SystemPopupPresentationParams mSmartSuggestion;
        private long mUiFirstDestroyedTime;
        private long mUiFirstShownTime;
        public final int taskId;

        @Retention(RetentionPolicy.SOURCE)
        @interface ReportEvent {
        }

        private AutofillProxy(int sessionId, IBinder client, int taskId2, ComponentName serviceComponentName, ComponentName componentName2, AutofillId focusedId, AutofillValue focusedValue, long requestTime, IFillCallback callback, CancellationSignal cancellationSignal) {
            this.mLock = new Object();
            this.mSessionId = sessionId;
            this.mClient = IAugmentedAutofillManagerClient.Stub.asInterface(client);
            this.mCallback = callback;
            this.taskId = taskId2;
            this.componentName = componentName2;
            this.mServicePackageName = serviceComponentName.getPackageName();
            this.mFocusedId = focusedId;
            this.mFocusedValue = focusedValue;
            this.mFirstRequestTime = requestTime;
            this.mCancellationSignal = cancellationSignal;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
            return null;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams getSmartSuggestionParams() {
            /*
                r6 = this;
                java.lang.Object r0 = r6.mLock
                monitor-enter(r0)
                android.service.autofill.augmented.PresentationParams$SystemPopupPresentationParams r1 = r6.mSmartSuggestion     // Catch:{ all -> 0x0073 }
                if (r1 == 0) goto L_0x0015
                android.view.autofill.AutofillId r1 = r6.mFocusedId     // Catch:{ all -> 0x0073 }
                android.view.autofill.AutofillId r2 = r6.mLastShownId     // Catch:{ all -> 0x0073 }
                boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0073 }
                if (r1 == 0) goto L_0x0015
                android.service.autofill.augmented.PresentationParams$SystemPopupPresentationParams r1 = r6.mSmartSuggestion     // Catch:{ all -> 0x0073 }
                monitor-exit(r0)     // Catch:{ all -> 0x0073 }
                return r1
            L_0x0015:
                r1 = 0
                android.view.autofill.IAugmentedAutofillManagerClient r2 = r6.mClient     // Catch:{ RemoteException -> 0x0056 }
                android.view.autofill.AutofillId r3 = r6.mFocusedId     // Catch:{ RemoteException -> 0x0056 }
                android.graphics.Rect r2 = r2.getViewCoordinates(r3)     // Catch:{ RemoteException -> 0x0056 }
                if (r2 != 0) goto L_0x0047
                boolean r3 = android.service.autofill.augmented.AugmentedAutofillService.sDebug     // Catch:{ all -> 0x0073 }
                if (r3 == 0) goto L_0x0045
                java.lang.String r3 = android.service.autofill.augmented.AugmentedAutofillService.TAG     // Catch:{ all -> 0x0073 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
                r4.<init>()     // Catch:{ all -> 0x0073 }
                java.lang.String r5 = "getViewCoordinates("
                r4.append(r5)     // Catch:{ all -> 0x0073 }
                android.view.autofill.AutofillId r5 = r6.mFocusedId     // Catch:{ all -> 0x0073 }
                r4.append(r5)     // Catch:{ all -> 0x0073 }
                java.lang.String r5 = ") returned null"
                r4.append(r5)     // Catch:{ all -> 0x0073 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0073 }
                android.util.Log.d(r3, r4)     // Catch:{ all -> 0x0073 }
            L_0x0045:
                monitor-exit(r0)     // Catch:{ all -> 0x0073 }
                return r1
            L_0x0047:
                android.service.autofill.augmented.PresentationParams$SystemPopupPresentationParams r1 = new android.service.autofill.augmented.PresentationParams$SystemPopupPresentationParams     // Catch:{ all -> 0x0073 }
                r1.<init>(r6, r2)     // Catch:{ all -> 0x0073 }
                r6.mSmartSuggestion = r1     // Catch:{ all -> 0x0073 }
                android.view.autofill.AutofillId r1 = r6.mFocusedId     // Catch:{ all -> 0x0073 }
                r6.mLastShownId = r1     // Catch:{ all -> 0x0073 }
                android.service.autofill.augmented.PresentationParams$SystemPopupPresentationParams r1 = r6.mSmartSuggestion     // Catch:{ all -> 0x0073 }
                monitor-exit(r0)     // Catch:{ all -> 0x0073 }
                return r1
            L_0x0056:
                r2 = move-exception
                java.lang.String r3 = android.service.autofill.augmented.AugmentedAutofillService.TAG     // Catch:{ all -> 0x0073 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
                r4.<init>()     // Catch:{ all -> 0x0073 }
                java.lang.String r5 = "Could not get coordinates for "
                r4.append(r5)     // Catch:{ all -> 0x0073 }
                android.view.autofill.AutofillId r5 = r6.mFocusedId     // Catch:{ all -> 0x0073 }
                r4.append(r5)     // Catch:{ all -> 0x0073 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0073 }
                android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x0073 }
                monitor-exit(r0)     // Catch:{ all -> 0x0073 }
                return r1
            L_0x0073:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0073 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy.getSmartSuggestionParams():android.service.autofill.augmented.PresentationParams$SystemPopupPresentationParams");
        }

        public void autofill(List<Pair<AutofillId, AutofillValue>> pairs) throws RemoteException {
            int size = pairs.size();
            List<AutofillId> ids = new ArrayList<>(size);
            List<AutofillValue> values = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Pair<AutofillId, AutofillValue> pair = pairs.get(i);
                ids.add((AutofillId) pair.first);
                values.add((AutofillValue) pair.second);
            }
            this.mClient.autofill(this.mSessionId, ids, values);
        }

        public void setFillWindow(FillWindow fillWindow) {
            synchronized (this.mLock) {
                this.mFillWindow = fillWindow;
            }
        }

        public FillWindow getFillWindow() {
            FillWindow fillWindow;
            synchronized (this.mLock) {
                fillWindow = this.mFillWindow;
            }
            return fillWindow;
        }

        public void requestShowFillUi(int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
            if (!this.mCancellationSignal.isCanceled()) {
                this.mClient.requestShowFillUi(this.mSessionId, this.mFocusedId, width, height, anchorBounds, presenter);
            } else if (AugmentedAutofillService.sVerbose) {
                Log.v(AugmentedAutofillService.TAG, "requestShowFillUi() not showing because request is cancelled");
            }
        }

        public void requestHideFillUi() throws RemoteException {
            this.mClient.requestHideFillUi(this.mSessionId, this.mFocusedId);
        }

        /* access modifiers changed from: private */
        public void update(AutofillId focusedId, AutofillValue focusedValue, IFillCallback callback, CancellationSignal cancellationSignal) {
            synchronized (this.mLock) {
                this.mFocusedId = focusedId;
                this.mFocusedValue = focusedValue;
                if (this.mCallback != null) {
                    try {
                        if (!this.mCallback.isCompleted()) {
                            this.mCallback.cancel();
                        }
                    } catch (RemoteException e) {
                        Log.e(AugmentedAutofillService.TAG, "failed to check current pending request status", e);
                    }
                    Log.d(AugmentedAutofillService.TAG, "mCallback is updated.");
                }
                this.mCallback = callback;
                this.mCancellationSignal = cancellationSignal;
            }
        }

        public AutofillId getFocusedId() {
            AutofillId autofillId;
            synchronized (this.mLock) {
                autofillId = this.mFocusedId;
            }
            return autofillId;
        }

        public AutofillValue getFocusedValue() {
            AutofillValue autofillValue;
            synchronized (this.mLock) {
                autofillValue = this.mFocusedValue;
            }
            return autofillValue;
        }

        public void report(int event) {
            if (AugmentedAutofillService.sVerbose) {
                String access$900 = AugmentedAutofillService.TAG;
                Log.v(access$900, "report(): " + event);
            }
            long duration = -1;
            int type = 0;
            switch (event) {
                case 1:
                    type = 10;
                    if (this.mFirstOnSuccessTime == 0) {
                        this.mFirstOnSuccessTime = SystemClock.elapsedRealtime();
                        long duration2 = this.mFirstOnSuccessTime - this.mFirstRequestTime;
                        if (AugmentedAutofillService.sDebug) {
                            String access$9002 = AugmentedAutofillService.TAG;
                            Log.d(access$9002, "Service responded nothing in " + TimeUtils.formatDuration(duration2));
                        }
                        duration = duration2;
                    }
                    try {
                        this.mCallback.onSuccess();
                        break;
                    } catch (RemoteException e) {
                        String access$9003 = AugmentedAutofillService.TAG;
                        Log.e(access$9003, "Error reporting success: " + e);
                        break;
                    }
                case 2:
                    type = 1;
                    if (this.mUiFirstShownTime == 0) {
                        this.mUiFirstShownTime = SystemClock.elapsedRealtime();
                        duration = this.mUiFirstShownTime - this.mFirstRequestTime;
                        if (AugmentedAutofillService.sDebug) {
                            String access$9004 = AugmentedAutofillService.TAG;
                            Log.d(access$9004, "UI shown in " + TimeUtils.formatDuration(duration));
                            break;
                        }
                    }
                    break;
                case 3:
                    type = 2;
                    if (this.mUiFirstDestroyedTime == 0) {
                        this.mUiFirstDestroyedTime = SystemClock.elapsedRealtime();
                        duration = this.mUiFirstDestroyedTime - this.mFirstRequestTime;
                        if (AugmentedAutofillService.sDebug) {
                            String access$9005 = AugmentedAutofillService.TAG;
                            Log.d(access$9005, "UI destroyed in " + TimeUtils.formatDuration(duration));
                            break;
                        }
                    }
                    break;
                default:
                    String access$9006 = AugmentedAutofillService.TAG;
                    Log.w(access$9006, "invalid event reported: " + event);
                    break;
            }
            Helper.logResponse(type, this.mServicePackageName, this.componentName, this.mSessionId, duration);
        }

        public void dump(String prefix, PrintWriter pw) {
            pw.print(prefix);
            pw.print("sessionId: ");
            pw.println(this.mSessionId);
            pw.print(prefix);
            pw.print("taskId: ");
            pw.println(this.taskId);
            pw.print(prefix);
            pw.print("component: ");
            pw.println(this.componentName.flattenToShortString());
            pw.print(prefix);
            pw.print("focusedId: ");
            pw.println(this.mFocusedId);
            if (this.mFocusedValue != null) {
                pw.print(prefix);
                pw.print("focusedValue: ");
                pw.println(this.mFocusedValue);
            }
            if (this.mLastShownId != null) {
                pw.print(prefix);
                pw.print("lastShownId: ");
                pw.println(this.mLastShownId);
            }
            pw.print(prefix);
            pw.print("client: ");
            pw.println(this.mClient);
            String prefix2 = prefix + "  ";
            if (this.mFillWindow != null) {
                pw.print(prefix);
                pw.println("window:");
                this.mFillWindow.dump(prefix2, pw);
            }
            if (this.mSmartSuggestion != null) {
                pw.print(prefix);
                pw.println("smartSuggestion:");
                this.mSmartSuggestion.dump(prefix2, pw);
            }
            if (this.mFirstOnSuccessTime > 0) {
                pw.print(prefix);
                pw.print("response time: ");
                TimeUtils.formatDuration(this.mFirstOnSuccessTime - this.mFirstRequestTime, pw);
                pw.println();
            }
            if (this.mUiFirstShownTime > 0) {
                pw.print(prefix);
                pw.print("UI rendering time: ");
                TimeUtils.formatDuration(this.mUiFirstShownTime - this.mFirstRequestTime, pw);
                pw.println();
            }
            if (this.mUiFirstDestroyedTime > 0) {
                pw.print(prefix);
                pw.print("UI life time: ");
                TimeUtils.formatDuration(this.mUiFirstDestroyedTime - this.mFirstRequestTime, pw);
                pw.println();
            }
        }

        /* access modifiers changed from: private */
        public void destroy() {
            synchronized (this.mLock) {
                if (this.mFillWindow != null) {
                    if (AugmentedAutofillService.sDebug) {
                        Log.d(AugmentedAutofillService.TAG, "destroying window");
                    }
                    this.mFillWindow.destroy();
                    this.mFillWindow = null;
                }
            }
        }
    }
}
