package android.view.autofill;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SystemApi;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Rect;
import android.metrics.LogMaker;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.autofill.FillEventHistory;
import android.service.autofill.UserData;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillManager;
import android.view.autofill.IAugmentedAutofillManagerClient;
import android.view.autofill.IAutoFillManagerClient;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.IResultReceiver;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.SyncResultReceiver;
import com.ibm.icu.text.PluralRules;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;
import sun.misc.Cleaner;

public final class AutofillManager {
    public static final int ACTION_START_SESSION = 1;
    public static final int ACTION_VALUE_CHANGED = 4;
    public static final int ACTION_VIEW_ENTERED = 2;
    public static final int ACTION_VIEW_EXITED = 3;
    private static final int AUTHENTICATION_ID_DATASET_ID_MASK = 65535;
    private static final int AUTHENTICATION_ID_DATASET_ID_SHIFT = 16;
    public static final int AUTHENTICATION_ID_DATASET_ID_UNDEFINED = 65535;
    public static final int DEFAULT_LOGGING_LEVEL;
    public static final int DEFAULT_MAX_PARTITIONS_SIZE = 10;
    public static final String DEVICE_CONFIG_AUGMENTED_SERVICE_IDLE_UNBIND_TIMEOUT = "augmented_service_idle_unbind_timeout";
    public static final String DEVICE_CONFIG_AUGMENTED_SERVICE_REQUEST_TIMEOUT = "augmented_service_request_timeout";
    public static final String DEVICE_CONFIG_AUTOFILL_SMART_SUGGESTION_SUPPORTED_MODES = "smart_suggestion_supported_modes";
    public static final String EXTRA_ASSIST_STRUCTURE = "android.view.autofill.extra.ASSIST_STRUCTURE";
    public static final String EXTRA_AUGMENTED_AUTOFILL_CLIENT = "android.view.autofill.extra.AUGMENTED_AUTOFILL_CLIENT";
    public static final String EXTRA_AUTHENTICATION_RESULT = "android.view.autofill.extra.AUTHENTICATION_RESULT";
    public static final String EXTRA_CLIENT_STATE = "android.view.autofill.extra.CLIENT_STATE";
    public static final String EXTRA_RESTORE_SESSION_TOKEN = "android.view.autofill.extra.RESTORE_SESSION_TOKEN";
    public static final int FC_SERVICE_TIMEOUT = 5000;
    public static final int FLAG_ADD_CLIENT_DEBUG = 2;
    public static final int FLAG_ADD_CLIENT_ENABLED = 1;
    public static final int FLAG_ADD_CLIENT_ENABLED_FOR_AUGMENTED_AUTOFILL_ONLY = 8;
    public static final int FLAG_ADD_CLIENT_VERBOSE = 4;
    public static final int FLAG_SMART_SUGGESTION_OFF = 0;
    public static final int FLAG_SMART_SUGGESTION_SYSTEM = 1;
    private static final String LAST_AUTOFILLED_DATA_TAG = "android:lastAutoFilledData";
    public static final int MAX_TEMP_AUGMENTED_SERVICE_DURATION_MS = 120000;
    public static final int NO_LOGGING = 0;
    public static final int NO_SESSION = Integer.MAX_VALUE;
    public static final int PENDING_UI_OPERATION_CANCEL = 1;
    public static final int PENDING_UI_OPERATION_RESTORE = 2;
    public static final int RECEIVER_FLAG_SESSION_FOR_AUGMENTED_AUTOFILL_ONLY = 1;
    public static final int RESULT_CODE_NOT_SERVICE = -1;
    public static final int RESULT_OK = 0;
    private static final String SESSION_ID_TAG = "android:sessionId";
    public static final int SET_STATE_FLAG_DEBUG = 8;
    public static final int SET_STATE_FLAG_ENABLED = 1;
    public static final int SET_STATE_FLAG_FOR_AUTOFILL_ONLY = 32;
    public static final int SET_STATE_FLAG_RESET_CLIENT = 4;
    public static final int SET_STATE_FLAG_RESET_SESSION = 2;
    public static final int SET_STATE_FLAG_VERBOSE = 16;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DISABLED_BY_SERVICE = 4;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_SHOWING_SAVE_UI = 3;
    private static final String STATE_TAG = "android:state";
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_UNKNOWN_COMPAT_MODE = 5;
    public static final int STATE_UNKNOWN_FAILED = 6;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = "AutofillManager";
    @GuardedBy({"mLock"})
    private IAugmentedAutofillManagerClient mAugmentedAutofillServiceClient;
    @GuardedBy({"mLock"})
    private AutofillCallback mCallback;
    @GuardedBy({"mLock"})
    private CompatibilityBridge mCompatibilityBridge;
    /* access modifiers changed from: private */
    public final Context mContext;
    @GuardedBy({"mLock"})
    private boolean mEnabled;
    @GuardedBy({"mLock"})
    private boolean mEnabledForAugmentedAutofillOnly;
    @GuardedBy({"mLock"})
    private Set<AutofillId> mEnteredForAugmentedAutofillIds;
    @GuardedBy({"mLock"})
    private ArraySet<AutofillId> mEnteredIds;
    @GuardedBy({"mLock"})
    private ArraySet<AutofillId> mFillableIds;
    @GuardedBy({"mLock"})
    private boolean mForAugmentedAutofillOnly;
    private AutofillId mIdShownFillUi;
    @GuardedBy({"mLock"})
    private ParcelableMap mLastAutofilledData;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    @GuardedBy({"mLock"})
    private boolean mOnInvisibleCalled;
    private final AutofillOptions mOptions;
    @GuardedBy({"mLock"})
    private boolean mSaveOnFinish;
    @GuardedBy({"mLock"})
    private AutofillId mSaveTriggerId;
    private final IAutoFillManager mService;
    @GuardedBy({"mLock"})
    private IAutoFillManagerClient mServiceClient;
    @GuardedBy({"mLock"})
    private Cleaner mServiceClientCleaner;
    @GuardedBy({"mLock"})
    private int mSessionId = Integer.MAX_VALUE;
    @GuardedBy({"mLock"})
    private int mState;
    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public TrackedViews mTrackedViews;

    public interface AutofillClient {
        void autofillClientAuthenticate(int i, IntentSender intentSender, Intent intent);

        void autofillClientDispatchUnhandledKey(View view, KeyEvent keyEvent);

        View autofillClientFindViewByAccessibilityIdTraversal(int i, int i2);

        View autofillClientFindViewByAutofillIdTraversal(AutofillId autofillId);

        View[] autofillClientFindViewsByAutofillIdTraversal(AutofillId[] autofillIdArr);

        IBinder autofillClientGetActivityToken();

        ComponentName autofillClientGetComponentName();

        AutofillId autofillClientGetNextAutofillId();

        boolean[] autofillClientGetViewVisibility(AutofillId[] autofillIdArr);

        boolean autofillClientIsCompatibilityModeEnabled();

        boolean autofillClientIsFillUiShowing();

        boolean autofillClientIsVisibleForAutofill();

        boolean autofillClientRequestHideFillUi();

        boolean autofillClientRequestShowFillUi(View view, int i, int i2, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter);

        void autofillClientResetableStateAvailable();

        void autofillClientRunOnUiThread(Runnable runnable);

        boolean isDisablingEnterExitEventForAutofill();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SmartSuggestionMode {
    }

    static {
        int i;
        if (Build.IS_DEBUGGABLE) {
            i = 2;
        } else {
            i = 0;
        }
        DEFAULT_LOGGING_LEVEL = i;
    }

    public static int makeAuthenticationId(int requestId, int datasetId) {
        return (requestId << 16) | (65535 & datasetId);
    }

    public static int getRequestIdFromAuthenticationId(int authRequestId) {
        return authRequestId >> 16;
    }

    public static int getDatasetIdFromAuthenticationId(int authRequestId) {
        return 65535 & authRequestId;
    }

    public AutofillManager(Context context, IAutoFillManager service) {
        boolean z = false;
        this.mState = 0;
        this.mContext = (Context) Preconditions.checkNotNull(context, "context cannot be null");
        this.mService = service;
        this.mOptions = context.getAutofillOptions();
        if (this.mOptions != null) {
            Helper.sDebug = (this.mOptions.loggingLevel & 2) != 0;
            Helper.sVerbose = (this.mOptions.loggingLevel & 4) != 0 ? true : z;
        }
    }

    public void enableCompatibilityMode() {
        synchronized (this.mLock) {
            if (Helper.sDebug) {
                Slog.d(TAG, "creating CompatibilityBridge for " + this.mContext);
            }
            this.mCompatibilityBridge = new CompatibilityBridge();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                this.mLastAutofilledData = (ParcelableMap) savedInstanceState.getParcelable(LAST_AUTOFILLED_DATA_TAG);
                if (isActiveLocked()) {
                    Log.w(TAG, "New session was started before onCreate()");
                    return;
                }
                this.mSessionId = savedInstanceState.getInt(SESSION_ID_TAG, Integer.MAX_VALUE);
                this.mState = savedInstanceState.getInt(STATE_TAG, 0);
                if (this.mSessionId != Integer.MAX_VALUE) {
                    ensureServiceClientAddedIfNeededLocked();
                    AutofillClient client = getClient();
                    if (client != null) {
                        SyncResultReceiver receiver = new SyncResultReceiver(5000);
                        try {
                            this.mService.restoreSession(this.mSessionId, client.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), receiver);
                            boolean sessionWasRestored = true;
                            if (receiver.getIntResult() != 1) {
                                sessionWasRestored = false;
                            }
                            if (!sessionWasRestored) {
                                Log.w(TAG, "Session " + this.mSessionId + " could not be restored");
                                this.mSessionId = Integer.MAX_VALUE;
                                this.mState = 0;
                            } else {
                                if (Helper.sDebug) {
                                    Log.d(TAG, "session " + this.mSessionId + " was restored");
                                }
                                client.autofillClientResetableStateAvailable();
                            }
                        } catch (RemoteException e) {
                            Log.e(TAG, "Could not figure out if there was an autofill session", e);
                        }
                    }
                }
            }
        }
    }

    public void onVisibleForAutofill() {
        Choreographer.getInstance().postCallback(4, new Runnable() {
            public final void run() {
                AutofillManager.lambda$onVisibleForAutofill$0(AutofillManager.this);
            }
        }, (Object) null);
    }

    public static /* synthetic */ void lambda$onVisibleForAutofill$0(AutofillManager autofillManager) {
        synchronized (autofillManager.mLock) {
            if (autofillManager.mEnabled && autofillManager.isActiveLocked() && autofillManager.mTrackedViews != null) {
                autofillManager.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
        }
    }

    public void onInvisibleForAutofill() {
        synchronized (this.mLock) {
            this.mOnInvisibleCalled = true;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                if (this.mSessionId != Integer.MAX_VALUE) {
                    outState.putInt(SESSION_ID_TAG, this.mSessionId);
                }
                if (this.mState != 0) {
                    outState.putInt(STATE_TAG, this.mState);
                }
                if (this.mLastAutofilledData != null) {
                    outState.putParcelable(LAST_AUTOFILLED_DATA_TAG, this.mLastAutofilledData);
                }
            }
        }
    }

    @GuardedBy({"mLock"})
    public boolean isCompatibilityModeEnabledLocked() {
        return this.mCompatibilityBridge != null;
    }

    public boolean isEnabled() {
        if (!hasAutofillFeature()) {
            return false;
        }
        synchronized (this.mLock) {
            if (isDisabledByServiceLocked()) {
                return false;
            }
            ensureServiceClientAddedIfNeededLocked();
            boolean z = this.mEnabled;
            return z;
        }
    }

    public FillEventHistory getFillEventHistory() {
        try {
            SyncResultReceiver receiver = new SyncResultReceiver(5000);
            this.mService.getFillEventHistory(receiver);
            return (FillEventHistory) receiver.getParcelableResult();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public void requestAutofill(View view) {
        notifyViewEntered(view, 1);
    }

    public void requestAutofill(View view, int virtualId, Rect absBounds) {
        notifyViewEntered(view, virtualId, absBounds, 1);
    }

    public void notifyViewEntered(View view) {
        notifyViewEntered(view, 0);
    }

    @GuardedBy({"mLock"})
    private boolean shouldIgnoreViewEnteredLocked(AutofillId id, int flags) {
        if (isDisabledByServiceLocked()) {
            if (Helper.sVerbose) {
                Log.v(TAG, "ignoring notifyViewEntered(flags=" + flags + ", view=" + id + ") on state " + getStateAsStringLocked() + " because disabled by svc");
            }
            return true;
        } else if (!isFinishedLocked() || (flags & 1) != 0 || this.mEnteredIds == null || !this.mEnteredIds.contains(id)) {
            return false;
        } else {
            if (Helper.sVerbose) {
                Log.v(TAG, "ignoring notifyViewEntered(flags=" + flags + ", view=" + id + ") on state " + getStateAsStringLocked() + " because view was already entered: " + this.mEnteredIds);
            }
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean isClientVisibleForAutofillLocked() {
        AutofillClient client = getClient();
        return client != null && client.autofillClientIsVisibleForAutofill();
    }

    private boolean isClientDisablingEnterExitEvent() {
        AutofillClient client = getClient();
        return client != null && client.isDisablingEnterExitEventForAutofill();
    }

    private void notifyViewEntered(View view, int flags) {
        AutofillCallback callback;
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                callback = notifyViewEnteredLocked(view, flags);
            }
            if (callback != null) {
                this.mCallback.onAutofillEvent(view, 3);
            }
        }
    }

    @GuardedBy({"mLock"})
    private AutofillCallback notifyViewEnteredLocked(View view, int flags) {
        AutofillId id = view.getAutofillId();
        if (shouldIgnoreViewEnteredLocked(id, flags)) {
            return null;
        }
        ensureServiceClientAddedIfNeededLocked();
        if (!this.mEnabled && !this.mEnabledForAugmentedAutofillOnly) {
            if (Helper.sVerbose) {
                Log.v(TAG, "ignoring notifyViewEntered(" + id + "): disabled");
            }
            if (this.mCallback != null) {
                return this.mCallback;
            }
            return null;
        } else if (isClientDisablingEnterExitEvent()) {
            return null;
        } else {
            AutofillValue value = view.getAutofillValue();
            if (!isActiveLocked()) {
                startSessionLocked(id, (Rect) null, value, flags);
            } else {
                if (this.mForAugmentedAutofillOnly && (flags & 1) != 0) {
                    if (Helper.sDebug) {
                        Log.d(TAG, "notifyViewEntered(" + id + "): resetting mForAugmentedAutofillOnly on manual request");
                    }
                    this.mForAugmentedAutofillOnly = false;
                }
                updateSessionLocked(id, (Rect) null, value, 2, flags);
            }
            addEnteredIdLocked(id);
            return null;
        }
    }

    public void notifyViewExited(View view) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                notifyViewExitedLocked(view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @GuardedBy({"mLock"})
    public void notifyViewExitedLocked(View view) {
        ensureServiceClientAddedIfNeededLocked();
        if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && isActiveLocked() && !isClientDisablingEnterExitEvent()) {
            updateSessionLocked(view.getAutofillId(), (Rect) null, (AutofillValue) null, 3, 0);
        }
    }

    public void notifyViewVisibilityChanged(View view, boolean isVisible) {
        notifyViewVisibilityChangedInternal(view, 0, isVisible, false);
    }

    public void notifyViewVisibilityChanged(View view, int virtualId, boolean isVisible) {
        notifyViewVisibilityChangedInternal(view, virtualId, isVisible, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a7, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyViewVisibilityChangedInternal(android.view.View r6, int r7, boolean r8, boolean r9) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.mForAugmentedAutofillOnly     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x0015
            boolean r1 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x0013
            java.lang.String r1 = "AutofillManager"
            java.lang.String r2 = "notifyViewVisibilityChanged(): ignoring on augmented only mode"
            android.util.Log.v(r1, r2)     // Catch:{ all -> 0x00a8 }
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            return
        L_0x0015:
            boolean r1 = r5.mEnabled     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x00a6
            boolean r1 = r5.isActiveLocked()     // Catch:{ all -> 0x00a8 }
            if (r1 == 0) goto L_0x00a6
            if (r9 == 0) goto L_0x0026
            android.view.autofill.AutofillId r1 = getAutofillId(r6, r7)     // Catch:{ all -> 0x00a8 }
            goto L_0x002a
        L_0x0026:
            android.view.autofill.AutofillId r1 = r6.getAutofillId()     // Catch:{ all -> 0x00a8 }
        L_0x002a:
            boolean r2 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x004d
            java.lang.String r2 = "AutofillManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            r3.<init>()     // Catch:{ all -> 0x00a8 }
            java.lang.String r4 = "visibility changed for "
            r3.append(r4)     // Catch:{ all -> 0x00a8 }
            r3.append(r1)     // Catch:{ all -> 0x00a8 }
            java.lang.String r4 = ": "
            r3.append(r4)     // Catch:{ all -> 0x00a8 }
            r3.append(r8)     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00a8 }
            android.util.Log.v(r2, r3)     // Catch:{ all -> 0x00a8 }
        L_0x004d:
            if (r8 != 0) goto L_0x007d
            android.util.ArraySet<android.view.autofill.AutofillId> r2 = r5.mFillableIds     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x007d
            android.util.ArraySet<android.view.autofill.AutofillId> r2 = r5.mFillableIds     // Catch:{ all -> 0x00a8 }
            boolean r2 = r2.contains(r1)     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x007d
            boolean r2 = android.view.autofill.Helper.sDebug     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x007a
            java.lang.String r2 = "AutofillManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            r3.<init>()     // Catch:{ all -> 0x00a8 }
            java.lang.String r4 = "Hidding UI when view "
            r3.append(r4)     // Catch:{ all -> 0x00a8 }
            r3.append(r1)     // Catch:{ all -> 0x00a8 }
            java.lang.String r4 = " became invisible"
            r3.append(r4)     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00a8 }
            android.util.Log.d(r2, r3)     // Catch:{ all -> 0x00a8 }
        L_0x007a:
            r5.requestHideFillUi((android.view.autofill.AutofillId) r1, (android.view.View) r6)     // Catch:{ all -> 0x00a8 }
        L_0x007d:
            android.view.autofill.AutofillManager$TrackedViews r2 = r5.mTrackedViews     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x0087
            android.view.autofill.AutofillManager$TrackedViews r2 = r5.mTrackedViews     // Catch:{ all -> 0x00a8 }
            r2.notifyViewVisibilityChangedLocked(r1, r8)     // Catch:{ all -> 0x00a8 }
            goto L_0x00a6
        L_0x0087:
            boolean r2 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x00a6
            java.lang.String r2 = "AutofillManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            r3.<init>()     // Catch:{ all -> 0x00a8 }
            java.lang.String r4 = "Ignoring visibility change on "
            r3.append(r4)     // Catch:{ all -> 0x00a8 }
            r3.append(r1)     // Catch:{ all -> 0x00a8 }
            java.lang.String r4 = ": no tracked views"
            r3.append(r4)     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00a8 }
            android.util.Log.v(r2, r3)     // Catch:{ all -> 0x00a8 }
        L_0x00a6:
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            return
        L_0x00a8:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a8 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.notifyViewVisibilityChangedInternal(android.view.View, int, boolean, boolean):void");
    }

    public void notifyViewEntered(View view, int virtualId, Rect absBounds) {
        notifyViewEntered(view, virtualId, absBounds, 0);
    }

    private void notifyViewEntered(View view, int virtualId, Rect bounds, int flags) {
        AutofillCallback callback;
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                callback = notifyViewEnteredLocked(view, virtualId, bounds, flags);
            }
            if (callback != null) {
                callback.onAutofillEvent(view, virtualId, 3);
            }
        }
    }

    @GuardedBy({"mLock"})
    private AutofillCallback notifyViewEnteredLocked(View view, int virtualId, Rect bounds, int flags) {
        AutofillId id = getAutofillId(view, virtualId);
        if (shouldIgnoreViewEnteredLocked(id, flags)) {
            return null;
        }
        ensureServiceClientAddedIfNeededLocked();
        if (!this.mEnabled && !this.mEnabledForAugmentedAutofillOnly) {
            if (Helper.sVerbose) {
                Log.v(TAG, "ignoring notifyViewEntered(" + id + "): disabled");
            }
            if (this.mCallback != null) {
                return this.mCallback;
            }
            return null;
        } else if (isClientDisablingEnterExitEvent()) {
            return null;
        } else {
            if (!isActiveLocked()) {
                startSessionLocked(id, bounds, (AutofillValue) null, flags);
            } else {
                if (this.mForAugmentedAutofillOnly && (flags & 1) != 0) {
                    if (Helper.sDebug) {
                        Log.d(TAG, "notifyViewEntered(" + id + "): resetting mForAugmentedAutofillOnly on manual request");
                    }
                    this.mForAugmentedAutofillOnly = false;
                }
                updateSessionLocked(id, bounds, (AutofillValue) null, 2, flags);
            }
            addEnteredIdLocked(id);
            return null;
        }
    }

    @GuardedBy({"mLock"})
    private void addEnteredIdLocked(AutofillId id) {
        if (this.mEnteredIds == null) {
            this.mEnteredIds = new ArraySet<>(1);
        }
        id.resetSessionId();
        this.mEnteredIds.add(id);
    }

    public void notifyViewExited(View view, int virtualId) {
        if (Helper.sVerbose) {
            Log.v(TAG, "notifyViewExited(" + view.getAutofillId() + ", " + virtualId);
        }
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                notifyViewExitedLocked(view, virtualId);
            }
        }
    }

    @GuardedBy({"mLock"})
    private void notifyViewExitedLocked(View view, int virtualId) {
        ensureServiceClientAddedIfNeededLocked();
        if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && isActiveLocked() && !isClientDisablingEnterExitEvent()) {
            updateSessionLocked(getAutofillId(view, virtualId), (Rect) null, (AutofillValue) null, 3, 0);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a8, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyValueChanged(android.view.View r12) {
        /*
            r11 = this;
            boolean r0 = r11.hasAutofillFeature()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 0
            r1 = 0
            r2 = 0
            java.lang.Object r3 = r11.mLock
            monitor-enter(r3)
            android.view.autofill.ParcelableMap r4 = r11.mLastAutofilledData     // Catch:{ all -> 0x00a9 }
            r5 = 0
            if (r4 != 0) goto L_0x0016
            r12.setAutofilled(r5)     // Catch:{ all -> 0x00a9 }
            goto L_0x0046
        L_0x0016:
            android.view.autofill.AutofillId r4 = r12.getAutofillId()     // Catch:{ all -> 0x00a9 }
            r0 = r4
            android.view.autofill.ParcelableMap r4 = r11.mLastAutofilledData     // Catch:{ all -> 0x00a9 }
            boolean r4 = r4.containsKey(r0)     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x0043
            android.view.autofill.AutofillValue r4 = r12.getAutofillValue()     // Catch:{ all -> 0x00a9 }
            r2 = r4
            r1 = 1
            android.view.autofill.ParcelableMap r4 = r11.mLastAutofilledData     // Catch:{ all -> 0x00a9 }
            java.lang.Object r4 = r4.get(r0)     // Catch:{ all -> 0x00a9 }
            boolean r4 = java.util.Objects.equals(r4, r2)     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x003a
            r4 = 1
            r12.setAutofilled(r4)     // Catch:{ all -> 0x00a9 }
            goto L_0x0046
        L_0x003a:
            r12.setAutofilled(r5)     // Catch:{ all -> 0x00a9 }
            android.view.autofill.ParcelableMap r4 = r11.mLastAutofilledData     // Catch:{ all -> 0x00a9 }
            r4.remove(r0)     // Catch:{ all -> 0x00a9 }
            goto L_0x0046
        L_0x0043:
            r12.setAutofilled(r5)     // Catch:{ all -> 0x00a9 }
        L_0x0046:
            boolean r4 = r11.mForAugmentedAutofillOnly     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x0058
            boolean r4 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x0056
            java.lang.String r4 = "AutofillManager"
            java.lang.String r5 = "notifyValueChanged(): not notifying system server on augmented-only mode"
            android.util.Log.v(r4, r5)     // Catch:{ all -> 0x00a9 }
        L_0x0056:
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            return
        L_0x0058:
            boolean r4 = r11.mEnabled     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x007c
            boolean r4 = r11.isActiveLocked()     // Catch:{ all -> 0x00a9 }
            if (r4 != 0) goto L_0x0063
            goto L_0x007c
        L_0x0063:
            if (r0 != 0) goto L_0x006a
            android.view.autofill.AutofillId r4 = r12.getAutofillId()     // Catch:{ all -> 0x00a9 }
            r0 = r4
        L_0x006a:
            if (r1 != 0) goto L_0x0071
            android.view.autofill.AutofillValue r4 = r12.getAutofillValue()     // Catch:{ all -> 0x00a9 }
            r2 = r4
        L_0x0071:
            r7 = 0
            r9 = 4
            r10 = 0
            r5 = r11
            r6 = r0
            r8 = r2
            r5.updateSessionLocked(r6, r7, r8, r9, r10)     // Catch:{ all -> 0x00a9 }
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            return
        L_0x007c:
            boolean r4 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x00a7
            java.lang.String r4 = "AutofillManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a9 }
            r5.<init>()     // Catch:{ all -> 0x00a9 }
            java.lang.String r6 = "notifyValueChanged("
            r5.append(r6)     // Catch:{ all -> 0x00a9 }
            android.view.autofill.AutofillId r6 = r12.getAutofillId()     // Catch:{ all -> 0x00a9 }
            r5.append(r6)     // Catch:{ all -> 0x00a9 }
            java.lang.String r6 = "): ignoring on state "
            r5.append(r6)     // Catch:{ all -> 0x00a9 }
            java.lang.String r6 = r11.getStateAsStringLocked()     // Catch:{ all -> 0x00a9 }
            r5.append(r6)     // Catch:{ all -> 0x00a9 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00a9 }
            android.util.Log.v(r4, r5)     // Catch:{ all -> 0x00a9 }
        L_0x00a7:
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            return
        L_0x00a9:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.notifyValueChanged(android.view.View):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0069, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyValueChanged(android.view.View r9, int r10, android.view.autofill.AutofillValue r11) {
        /*
            r8 = this;
            boolean r0 = r8.hasAutofillFeature()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.Object r0 = r8.mLock
            monitor-enter(r0)
            boolean r1 = r8.mForAugmentedAutofillOnly     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x001c
            boolean r1 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x001a
            java.lang.String r1 = "AutofillManager"
            java.lang.String r2 = "notifyValueChanged(): ignoring on augmented only mode"
            android.util.Log.v(r1, r2)     // Catch:{ all -> 0x006a }
        L_0x001a:
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            return
        L_0x001c:
            boolean r1 = r8.mEnabled     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0035
            boolean r1 = r8.isActiveLocked()     // Catch:{ all -> 0x006a }
            if (r1 != 0) goto L_0x0027
            goto L_0x0035
        L_0x0027:
            android.view.autofill.AutofillId r3 = getAutofillId(r9, r10)     // Catch:{ all -> 0x006a }
            r4 = 0
            r6 = 4
            r7 = 0
            r2 = r8
            r5 = r11
            r2.updateSessionLocked(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x006a }
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            return
        L_0x0035:
            boolean r1 = android.view.autofill.Helper.sVerbose     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0068
            java.lang.String r1 = "AutofillManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x006a }
            r2.<init>()     // Catch:{ all -> 0x006a }
            java.lang.String r3 = "notifyValueChanged("
            r2.append(r3)     // Catch:{ all -> 0x006a }
            android.view.autofill.AutofillId r3 = r9.getAutofillId()     // Catch:{ all -> 0x006a }
            r2.append(r3)     // Catch:{ all -> 0x006a }
            java.lang.String r3 = ":"
            r2.append(r3)     // Catch:{ all -> 0x006a }
            r2.append(r10)     // Catch:{ all -> 0x006a }
            java.lang.String r3 = "): ignoring on state "
            r2.append(r3)     // Catch:{ all -> 0x006a }
            java.lang.String r3 = r8.getStateAsStringLocked()     // Catch:{ all -> 0x006a }
            r2.append(r3)     // Catch:{ all -> 0x006a }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x006a }
            android.util.Log.v(r1, r2)     // Catch:{ all -> 0x006a }
        L_0x0068:
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            return
        L_0x006a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.notifyValueChanged(android.view.View, int, android.view.autofill.AutofillValue):void");
    }

    public void notifyViewClicked(View view) {
        notifyViewClicked(view.getAutofillId());
    }

    public void notifyViewClicked(View view, int virtualId) {
        notifyViewClicked(getAutofillId(view, virtualId));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0070, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyViewClicked(android.view.autofill.AutofillId r5) {
        /*
            r4 = this;
            boolean r0 = r4.hasAutofillFeature()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            boolean r0 = android.view.autofill.Helper.sVerbose
            if (r0 == 0) goto L_0x002c
            java.lang.String r0 = "AutofillManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "notifyViewClicked(): id="
            r1.append(r2)
            r1.append(r5)
            java.lang.String r2 = ", trigger="
            r1.append(r2)
            android.view.autofill.AutofillId r2 = r4.mSaveTriggerId
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x002c:
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = r4.mEnabled     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x0071
            boolean r1 = r4.isActiveLocked()     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x003a
            goto L_0x0071
        L_0x003a:
            android.view.autofill.AutofillId r1 = r4.mSaveTriggerId     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x006f
            android.view.autofill.AutofillId r1 = r4.mSaveTriggerId     // Catch:{ all -> 0x0073 }
            boolean r1 = r1.equals(r5)     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x006f
            boolean r1 = android.view.autofill.Helper.sDebug     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x0061
            java.lang.String r1 = "AutofillManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r2.<init>()     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = "triggering commit by click of "
            r2.append(r3)     // Catch:{ all -> 0x0073 }
            r2.append(r5)     // Catch:{ all -> 0x0073 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0073 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0073 }
        L_0x0061:
            r4.commitLocked()     // Catch:{ all -> 0x0073 }
            com.android.internal.logging.MetricsLogger r1 = r4.mMetricsLogger     // Catch:{ all -> 0x0073 }
            r2 = 1229(0x4cd, float:1.722E-42)
            android.metrics.LogMaker r2 = r4.newLog(r2)     // Catch:{ all -> 0x0073 }
            r1.write(r2)     // Catch:{ all -> 0x0073 }
        L_0x006f:
            monitor-exit(r0)     // Catch:{ all -> 0x0073 }
            return
        L_0x0071:
            monitor-exit(r0)     // Catch:{ all -> 0x0073 }
            return
        L_0x0073:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0073 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.notifyViewClicked(android.view.autofill.AutofillId):void");
    }

    public void onActivityFinishing() {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                if (this.mSaveOnFinish) {
                    if (Helper.sDebug) {
                        Log.d(TAG, "onActivityFinishing(): calling commitLocked()");
                    }
                    commitLocked();
                } else {
                    if (Helper.sDebug) {
                        Log.d(TAG, "onActivityFinishing(): calling cancelLocked()");
                    }
                    cancelLocked();
                }
            }
        }
    }

    public void commit() {
        if (hasAutofillFeature()) {
            if (Helper.sVerbose) {
                Log.v(TAG, "commit() called by app");
            }
            synchronized (this.mLock) {
                commitLocked();
            }
        }
    }

    @GuardedBy({"mLock"})
    private void commitLocked() {
        if (this.mEnabled || isActiveLocked()) {
            finishSessionLocked();
        }
    }

    public void cancel() {
        if (Helper.sVerbose) {
            Log.v(TAG, "cancel() called by app");
        }
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                cancelLocked();
            }
        }
    }

    @GuardedBy({"mLock"})
    private void cancelLocked() {
        if (this.mEnabled || isActiveLocked()) {
            cancelSessionLocked();
        }
    }

    public void disableOwnedAutofillServices() {
        disableAutofillServices();
    }

    public void disableAutofillServices() {
        if (hasAutofillFeature()) {
            try {
                this.mService.disableOwnedAutofillServices(this.mContext.getUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasEnabledAutofillServices() {
        if (this.mService == null) {
            return false;
        }
        SyncResultReceiver receiver = new SyncResultReceiver(5000);
        try {
            this.mService.isServiceEnabled(this.mContext.getUserId(), this.mContext.getPackageName(), receiver);
            if (receiver.getIntResult() == 1) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ComponentName getAutofillServiceComponentName() {
        if (this.mService == null) {
            return null;
        }
        SyncResultReceiver receiver = new SyncResultReceiver(5000);
        try {
            this.mService.getAutofillServiceComponentName(receiver);
            return (ComponentName) receiver.getParcelableResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getUserDataId() {
        try {
            SyncResultReceiver receiver = new SyncResultReceiver(5000);
            this.mService.getUserDataId(receiver);
            return receiver.getStringResult();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public UserData getUserData() {
        try {
            SyncResultReceiver receiver = new SyncResultReceiver(5000);
            this.mService.getUserData(receiver);
            return (UserData) receiver.getParcelableResult();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public void setUserData(UserData userData) {
        try {
            this.mService.setUserData(userData);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isFieldClassificationEnabled() {
        SyncResultReceiver receiver = new SyncResultReceiver(5000);
        try {
            this.mService.isFieldClassificationEnabled(receiver);
            if (receiver.getIntResult() == 1) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public String getDefaultFieldClassificationAlgorithm() {
        SyncResultReceiver receiver = new SyncResultReceiver(5000);
        try {
            this.mService.getDefaultFieldClassificationAlgorithm(receiver);
            return receiver.getStringResult();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public List<String> getAvailableFieldClassificationAlgorithms() {
        SyncResultReceiver receiver = new SyncResultReceiver(5000);
        try {
            this.mService.getAvailableFieldClassificationAlgorithms(receiver);
            String[] algorithms = receiver.getStringArrayResult();
            return algorithms != null ? Arrays.asList(algorithms) : Collections.emptyList();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean isAutofillSupported() {
        if (this.mService == null) {
            return false;
        }
        SyncResultReceiver receiver = new SyncResultReceiver(5000);
        try {
            this.mService.isServiceSupported(this.mContext.getUserId(), receiver);
            if (receiver.getIntResult() == 1) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* access modifiers changed from: private */
    public AutofillClient getClient() {
        AutofillClient client = this.mContext.getAutofillClient();
        if (client == null && Helper.sVerbose) {
            Log.v(TAG, "No AutofillClient for " + this.mContext.getPackageName() + " on context " + this.mContext);
        }
        return client;
    }

    public boolean isAutofillUiShowing() {
        AutofillClient client = this.mContext.getAutofillClient();
        return client != null && client.autofillClientIsFillUiShowing();
    }

    public void onAuthenticationResult(int authenticationId, Intent data, View focusView) {
        if (hasAutofillFeature()) {
            if (Helper.sDebug) {
                Log.d(TAG, "onAuthenticationResult(): id= " + authenticationId + ", data=" + data);
            }
            synchronized (this.mLock) {
                if (isActiveLocked()) {
                    if (!this.mOnInvisibleCalled && focusView != null && focusView.canNotifyAutofillEnterExitEvent()) {
                        notifyViewExitedLocked(focusView);
                        notifyViewEnteredLocked(focusView, 0);
                    }
                    if (data != null) {
                        Parcelable result = data.getParcelableExtra(EXTRA_AUTHENTICATION_RESULT);
                        Bundle responseData = new Bundle();
                        responseData.putParcelable(EXTRA_AUTHENTICATION_RESULT, result);
                        Bundle newClientState = data.getBundleExtra(EXTRA_CLIENT_STATE);
                        if (newClientState != null) {
                            responseData.putBundle(EXTRA_CLIENT_STATE, newClientState);
                        }
                        try {
                            this.mService.setAuthenticationResult(responseData, this.mSessionId, authenticationId, this.mContext.getUserId());
                        } catch (RemoteException e) {
                            Log.e(TAG, "Error delivering authentication result", e);
                        }
                    }
                }
            }
        }
    }

    public AutofillId getNextAutofillId() {
        AutofillClient client = getClient();
        if (client == null) {
            return null;
        }
        AutofillId id = client.autofillClientGetNextAutofillId();
        if (id == null && Helper.sDebug) {
            Log.d(TAG, "getNextAutofillId(): client " + client + " returned null");
        }
        return id;
    }

    private static AutofillId getAutofillId(View parent, int virtualId) {
        return new AutofillId(parent.getAutofillViewId(), virtualId);
    }

    @GuardedBy({"mLock"})
    private void startSessionLocked(AutofillId id, Rect bounds, AutofillValue value, int flags) {
        int flags2;
        AutofillId autofillId = id;
        if ((this.mEnteredForAugmentedAutofillIds == null || !this.mEnteredForAugmentedAutofillIds.contains(autofillId)) && !this.mEnabledForAugmentedAutofillOnly) {
            flags2 = flags;
        } else {
            if (Helper.sVerbose) {
                Log.v(TAG, "Starting session for augmented autofill on " + autofillId);
            }
            flags2 = flags | 8;
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "startSessionLocked(): id=" + autofillId + ", bounds=" + bounds + ", value=" + value + ", flags=" + flags2 + ", state=" + getStateAsStringLocked() + ", compatMode=" + isCompatibilityModeEnabledLocked() + ", augmentedOnly=" + this.mForAugmentedAutofillOnly + ", enabledAugmentedOnly=" + this.mEnabledForAugmentedAutofillOnly + ", enteredIds=" + this.mEnteredIds);
        } else {
            Rect rect = bounds;
            AutofillValue autofillValue = value;
        }
        if (this.mForAugmentedAutofillOnly && !this.mEnabledForAugmentedAutofillOnly && (flags2 & 1) != 0) {
            if (Helper.sVerbose) {
                Log.v(TAG, "resetting mForAugmentedAutofillOnly on manual autofill request");
            }
            this.mForAugmentedAutofillOnly = false;
        }
        if (this.mState == 0 || isFinishedLocked() || (flags2 & 1) != 0) {
            try {
                AutofillClient client = getClient();
                if (client != null) {
                    SyncResultReceiver receiver = new SyncResultReceiver(5000);
                    ComponentName componentName = client.autofillClientGetComponentName();
                    SyncResultReceiver receiver2 = receiver;
                    this.mService.startSession(client.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), id, bounds, value, this.mContext.getUserId(), this.mCallback != null, flags2, componentName, isCompatibilityModeEnabledLocked(), receiver2);
                    this.mSessionId = receiver2.getIntResult();
                    if (this.mSessionId != Integer.MAX_VALUE) {
                        this.mState = 1;
                    }
                    if ((receiver2.getOptionalExtraIntResult(0) & 1) != 0) {
                        if (Helper.sDebug) {
                            Log.d(TAG, "startSession(" + componentName + "): for augmented only");
                        }
                        this.mForAugmentedAutofillOnly = true;
                    }
                    client.autofillClientResetableStateAvailable();
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else if (Helper.sVerbose) {
            Log.v(TAG, "not automatically starting session for " + autofillId + " on state " + getStateAsStringLocked() + " and flags " + flags2);
        }
    }

    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public void finishSessionLocked() {
        if (Helper.sVerbose) {
            Log.v(TAG, "finishSessionLocked(): " + getStateAsStringLocked());
        }
        if (isActiveLocked()) {
            try {
                this.mService.finishSession(this.mSessionId, this.mContext.getUserId());
                resetSessionLocked(true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @GuardedBy({"mLock"})
    private void cancelSessionLocked() {
        if (Helper.sVerbose) {
            Log.v(TAG, "cancelSessionLocked(): " + getStateAsStringLocked());
        }
        if (isActiveLocked()) {
            try {
                this.mService.cancelSession(this.mSessionId, this.mContext.getUserId());
                resetSessionLocked(true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @GuardedBy({"mLock"})
    private void resetSessionLocked(boolean resetEnteredIds) {
        this.mSessionId = Integer.MAX_VALUE;
        this.mState = 0;
        this.mTrackedViews = null;
        this.mFillableIds = null;
        this.mSaveTriggerId = null;
        this.mIdShownFillUi = null;
        if (resetEnteredIds) {
            this.mEnteredIds = null;
        }
    }

    @GuardedBy({"mLock"})
    private void updateSessionLocked(AutofillId id, Rect bounds, AutofillValue value, int action, int flags) {
        if (Helper.sVerbose) {
            Log.v(TAG, "updateSessionLocked(): id=" + id + ", bounds=" + bounds + ", value=" + value + ", action=" + action + ", flags=" + flags);
        }
        try {
            this.mService.updateSession(this.mSessionId, id, bounds, value, action, flags, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @GuardedBy({"mLock"})
    private void ensureServiceClientAddedIfNeededLocked() {
        AutofillClient client = getClient();
        if (client != null && this.mServiceClient == null) {
            this.mServiceClient = new AutofillManagerClient();
            try {
                int userId = this.mContext.getUserId();
                SyncResultReceiver receiver = new SyncResultReceiver(5000);
                this.mService.addClient(this.mServiceClient, client.autofillClientGetComponentName(), userId, receiver);
                int flags = receiver.getIntResult();
                boolean z = false;
                this.mEnabled = (flags & 1) != 0;
                Helper.sDebug = (flags & 2) != 0;
                Helper.sVerbose = (flags & 4) != 0;
                if ((flags & 8) != 0) {
                    z = true;
                }
                this.mEnabledForAugmentedAutofillOnly = z;
                if (Helper.sVerbose) {
                    Log.v(TAG, "receiver results: flags=" + flags + " enabled=" + this.mEnabled + ", enabledForAugmentedOnly: " + this.mEnabledForAugmentedAutofillOnly);
                }
                this.mServiceClientCleaner = Cleaner.create(this, new Runnable(this.mServiceClient, userId) {
                    private final /* synthetic */ IAutoFillManagerClient f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        AutofillManager.lambda$ensureServiceClientAddedIfNeededLocked$1(IAutoFillManager.this, this.f$1, this.f$2);
                    }
                });
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    static /* synthetic */ void lambda$ensureServiceClientAddedIfNeededLocked$1(IAutoFillManager service, IAutoFillManagerClient serviceClient, int userId) {
        try {
            service.removeClient(serviceClient, userId);
        } catch (RemoteException e) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002f, code lost:
        throw r2.rethrowFromSystemServer();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0031, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0033, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:6:0x000c, B:17:0x001c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void registerCallback(android.view.autofill.AutofillManager.AutofillCallback r7) {
        /*
            r6 = this;
            boolean r0 = r6.hasAutofillFeature()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            if (r7 != 0) goto L_0x0010
            monitor-exit(r0)     // Catch:{ all -> 0x000e }
            return
        L_0x000e:
            r1 = move-exception
            goto L_0x0032
        L_0x0010:
            android.view.autofill.AutofillManager$AutofillCallback r1 = r6.mCallback     // Catch:{ all -> 0x000e }
            r2 = 1
            if (r1 == 0) goto L_0x0017
            r1 = r2
            goto L_0x0018
        L_0x0017:
            r1 = 0
        L_0x0018:
            r6.mCallback = r7     // Catch:{ all -> 0x000e }
            if (r1 != 0) goto L_0x0030
            android.view.autofill.IAutoFillManager r3 = r6.mService     // Catch:{ RemoteException -> 0x002a }
            int r4 = r6.mSessionId     // Catch:{ RemoteException -> 0x002a }
            android.content.Context r5 = r6.mContext     // Catch:{ RemoteException -> 0x002a }
            int r5 = r5.getUserId()     // Catch:{ RemoteException -> 0x002a }
            r3.setHasCallback(r4, r5, r2)     // Catch:{ RemoteException -> 0x002a }
            goto L_0x0030
        L_0x002a:
            r2 = move-exception
            java.lang.RuntimeException r3 = r2.rethrowFromSystemServer()     // Catch:{ all -> 0x000e }
            throw r3     // Catch:{ all -> 0x000e }
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x000e }
            return
        L_0x0032:
            monitor-exit(r0)     // Catch:{ all -> 0x000e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.registerCallback(android.view.autofill.AutofillManager$AutofillCallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0032, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void unregisterCallback(android.view.autofill.AutofillManager.AutofillCallback r6) {
        /*
            r5 = this;
            boolean r0 = r5.hasAutofillFeature()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            if (r6 == 0) goto L_0x0031
            android.view.autofill.AutofillManager$AutofillCallback r1 = r5.mCallback     // Catch:{ all -> 0x002f }
            if (r1 == 0) goto L_0x0031
            android.view.autofill.AutofillManager$AutofillCallback r1 = r5.mCallback     // Catch:{ all -> 0x002f }
            if (r6 == r1) goto L_0x0015
            goto L_0x0031
        L_0x0015:
            r1 = 0
            r5.mCallback = r1     // Catch:{ all -> 0x002f }
            android.view.autofill.IAutoFillManager r1 = r5.mService     // Catch:{ RemoteException -> 0x0029 }
            int r2 = r5.mSessionId     // Catch:{ RemoteException -> 0x0029 }
            android.content.Context r3 = r5.mContext     // Catch:{ RemoteException -> 0x0029 }
            int r3 = r3.getUserId()     // Catch:{ RemoteException -> 0x0029 }
            r4 = 0
            r1.setHasCallback(r2, r3, r4)     // Catch:{ RemoteException -> 0x0029 }
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return
        L_0x0029:
            r1 = move-exception
            java.lang.RuntimeException r2 = r1.rethrowFromSystemServer()     // Catch:{ all -> 0x002f }
            throw r2     // Catch:{ all -> 0x002f }
        L_0x002f:
            r1 = move-exception
            goto L_0x0033
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return
        L_0x0033:
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.unregisterCallback(android.view.autofill.AutofillManager$AutofillCallback):void");
    }

    @SystemApi
    public void setAugmentedAutofillWhitelist(Set<String> packages, Set<ComponentName> activities) {
        if (hasAutofillFeature()) {
            SyncResultReceiver resultReceiver = new SyncResultReceiver(5000);
            try {
                this.mService.setAugmentedAutofillWhitelist(Helper.toList(packages), Helper.toList(activities), resultReceiver);
                int resultCode = resultReceiver.getIntResult();
                switch (resultCode) {
                    case -1:
                        throw new SecurityException("caller is not user's Augmented Autofill Service");
                    case 0:
                        return;
                    default:
                        Log.wtf(TAG, "setAugmentedAutofillWhitelist(): received invalid result: " + resultCode);
                        return;
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifyViewEnteredForAugmentedAutofill(View view) {
        AutofillId id = view.getAutofillId();
        synchronized (this.mLock) {
            if (this.mEnteredForAugmentedAutofillIds == null) {
                this.mEnteredForAugmentedAutofillIds = new ArraySet(1);
            }
            this.mEnteredForAugmentedAutofillIds.add(id);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        if (r10 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        if (r15.isVirtualInt() == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        r10.onAutofillEvent(r9, r15.getVirtualChildIntId(), 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        r10.onAutofillEvent(r9, 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void requestShowFillUi(int r14, android.view.autofill.AutofillId r15, int r16, int r17, android.graphics.Rect r18, android.view.autofill.IAutofillWindowPresenter r19) {
        /*
            r13 = this;
            r1 = r13
            r2 = r15
            android.view.View r9 = r13.findView(r15)
            if (r9 != 0) goto L_0x0009
            return
        L_0x0009:
            r10 = 0
            java.lang.Object r11 = r1.mLock
            monitor-enter(r11)
            int r0 = r1.mSessionId     // Catch:{ all -> 0x0043 }
            r12 = r14
            if (r0 != r12) goto L_0x002d
            android.view.autofill.AutofillManager$AutofillClient r0 = r13.getClient()     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x002d
            r3 = r0
            r4 = r9
            r5 = r16
            r6 = r17
            r7 = r18
            r8 = r19
            boolean r3 = r3.autofillClientRequestShowFillUi(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0047 }
            if (r3 == 0) goto L_0x002d
            android.view.autofill.AutofillManager$AutofillCallback r3 = r1.mCallback     // Catch:{ all -> 0x0047 }
            r10 = r3
            r1.mIdShownFillUi = r2     // Catch:{ all -> 0x0047 }
        L_0x002d:
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            if (r10 == 0) goto L_0x0042
            boolean r0 = r15.isVirtualInt()
            r3 = 1
            if (r0 == 0) goto L_0x003f
            int r0 = r15.getVirtualChildIntId()
            r10.onAutofillEvent(r9, r0, r3)
            goto L_0x0042
        L_0x003f:
            r10.onAutofillEvent(r9, r3)
        L_0x0042:
            return
        L_0x0043:
            r0 = move-exception
            r12 = r14
        L_0x0045:
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            throw r0
        L_0x0047:
            r0 = move-exception
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.requestShowFillUi(int, android.view.autofill.AutofillId, int, int, android.graphics.Rect, android.view.autofill.IAutofillWindowPresenter):void");
    }

    /* access modifiers changed from: private */
    public void authenticate(int sessionId, int authenticationId, IntentSender intent, Intent fillInIntent) {
        AutofillClient client;
        synchronized (this.mLock) {
            if (sessionId == this.mSessionId && (client = getClient()) != null) {
                this.mOnInvisibleCalled = false;
                client.autofillClientAuthenticate(authenticationId, intent, fillInIntent);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchUnhandledKey(int sessionId, AutofillId id, KeyEvent keyEvent) {
        AutofillClient client;
        View anchor = findView(id);
        if (anchor != null) {
            synchronized (this.mLock) {
                if (this.mSessionId == sessionId && (client = getClient()) != null) {
                    client.autofillClientDispatchUnhandledKey(anchor, keyEvent);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006a, code lost:
        if ((r6 & 8) == 0) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006c, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006e, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006f, code lost:
        android.view.autofill.Helper.sDebug = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0073, code lost:
        if ((r6 & 16) == 0) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0076, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0077, code lost:
        android.view.autofill.Helper.sVerbose = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0079, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setState(int r6) {
        /*
            r5 = this;
            boolean r0 = android.view.autofill.Helper.sVerbose
            if (r0 == 0) goto L_0x0030
            java.lang.String r0 = "AutofillManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "setState("
            r1.append(r2)
            r1.append(r6)
            java.lang.String r2 = ": "
            r1.append(r2)
            java.lang.Class<android.view.autofill.AutofillManager> r2 = android.view.autofill.AutofillManager.class
            java.lang.String r3 = "SET_STATE_FLAG_"
            java.lang.String r2 = android.util.DebugUtils.flagsToString(r2, r3, r6)
            r1.append(r2)
            java.lang.String r2 = ")"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0030:
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            r1 = r6 & 32
            r2 = 1
            if (r1 == 0) goto L_0x003e
            r5.mForAugmentedAutofillOnly = r2     // Catch:{ all -> 0x003c }
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return
        L_0x003c:
            r1 = move-exception
            goto L_0x007a
        L_0x003e:
            r1 = r6 & 1
            r3 = 0
            if (r1 == 0) goto L_0x0045
            r1 = r2
            goto L_0x0046
        L_0x0045:
            r1 = r3
        L_0x0046:
            r5.mEnabled = r1     // Catch:{ all -> 0x003c }
            boolean r1 = r5.mEnabled     // Catch:{ all -> 0x003c }
            if (r1 == 0) goto L_0x0050
            r1 = r6 & 2
            if (r1 == 0) goto L_0x0053
        L_0x0050:
            r5.resetSessionLocked(r2)     // Catch:{ all -> 0x003c }
        L_0x0053:
            r1 = r6 & 4
            if (r1 == 0) goto L_0x0067
            r1 = 0
            r5.mServiceClient = r1     // Catch:{ all -> 0x003c }
            r5.mAugmentedAutofillServiceClient = r1     // Catch:{ all -> 0x003c }
            sun.misc.Cleaner r4 = r5.mServiceClientCleaner     // Catch:{ all -> 0x003c }
            if (r4 == 0) goto L_0x0067
            sun.misc.Cleaner r4 = r5.mServiceClientCleaner     // Catch:{ all -> 0x003c }
            r4.clean()     // Catch:{ all -> 0x003c }
            r5.mServiceClientCleaner = r1     // Catch:{ all -> 0x003c }
        L_0x0067:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            r0 = r6 & 8
            if (r0 == 0) goto L_0x006e
            r0 = r2
            goto L_0x006f
        L_0x006e:
            r0 = r3
        L_0x006f:
            android.view.autofill.Helper.sDebug = r0
            r0 = r6 & 16
            if (r0 == 0) goto L_0x0076
            goto L_0x0077
        L_0x0076:
            r2 = r3
        L_0x0077:
            android.view.autofill.Helper.sVerbose = r2
            return
        L_0x007a:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.AutofillManager.setState(int):void");
    }

    private void setAutofilledIfValuesIs(View view, AutofillValue targetValue) {
        if (Objects.equals(view.getAutofillValue(), targetValue)) {
            synchronized (this.mLock) {
                if (this.mLastAutofilledData == null) {
                    this.mLastAutofilledData = new ParcelableMap(1);
                }
                this.mLastAutofilledData.put(view.getAutofillId(), targetValue);
            }
            view.setAutofilled(true);
        }
    }

    /* access modifiers changed from: private */
    public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) {
        AutofillClient client;
        synchronized (this.mLock) {
            try {
                if (sessionId == this.mSessionId) {
                    AutofillClient client2 = getClient();
                    if (client2 != null) {
                        int itemCount = ids.size();
                        ArrayMap<View, SparseArray<AutofillValue>> virtualValues = null;
                        View[] views = client2.autofillClientFindViewsByAutofillIdTraversal(Helper.toArray(ids));
                        ArrayList<AutofillId> failedIds = null;
                        int numApplied = 0;
                        int i = 0;
                        while (i < itemCount) {
                            try {
                                AutofillId id = ids.get(i);
                                AutofillValue value = values.get(i);
                                View view = views[i];
                                if (view == null) {
                                    StringBuilder sb = new StringBuilder();
                                    client = client2;
                                    sb.append("autofill(): no View with id ");
                                    sb.append(id);
                                    Log.d(TAG, sb.toString());
                                    if (failedIds == null) {
                                        failedIds = new ArrayList<>();
                                    }
                                    failedIds.add(id);
                                } else {
                                    client = client2;
                                    if (id.isVirtualInt()) {
                                        if (virtualValues == null) {
                                            virtualValues = new ArrayMap<>(1);
                                        }
                                        SparseArray<AutofillValue> valuesByParent = virtualValues.get(view);
                                        if (valuesByParent == null) {
                                            valuesByParent = new SparseArray<>(5);
                                            virtualValues.put(view, valuesByParent);
                                        }
                                        valuesByParent.put(id.getVirtualChildIntId(), value);
                                    } else {
                                        if (this.mLastAutofilledData == null) {
                                            this.mLastAutofilledData = new ParcelableMap(itemCount - i);
                                        }
                                        this.mLastAutofilledData.put(id, value);
                                        view.autofill(value);
                                        setAutofilledIfValuesIs(view, value);
                                        numApplied++;
                                    }
                                }
                                i++;
                                client2 = client;
                                int i2 = sessionId;
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                        List<AutofillId> list = ids;
                        List<AutofillValue> list2 = values;
                        AutofillClient autofillClient = client2;
                        if (failedIds != null) {
                            if (Helper.sVerbose) {
                                Log.v(TAG, "autofill(): total failed views: " + failedIds);
                            }
                            try {
                                this.mService.setAutofillFailure(this.mSessionId, failedIds, this.mContext.getUserId());
                            } catch (RemoteException e) {
                                e.rethrowFromSystemServer();
                            }
                        }
                        if (virtualValues != null) {
                            int i3 = 0;
                            while (true) {
                                int i4 = i3;
                                if (i4 >= virtualValues.size()) {
                                    break;
                                }
                                SparseArray<AutofillValue> childrenValues = virtualValues.valueAt(i4);
                                virtualValues.keyAt(i4).autofill(childrenValues);
                                numApplied += childrenValues.size();
                                i3 = i4 + 1;
                            }
                        }
                        this.mMetricsLogger.write(newLog(MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES, Integer.valueOf(itemCount)).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, Integer.valueOf(numApplied)));
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                List<AutofillId> list3 = ids;
                List<AutofillValue> list4 = values;
                throw th;
            }
        }
    }

    private LogMaker newLog(int category) {
        LogMaker log = new LogMaker(category).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_SESSION_ID, Integer.valueOf(this.mSessionId));
        if (isCompatibilityModeEnabledLocked()) {
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_COMPAT_MODE, 1);
        }
        AutofillClient client = getClient();
        if (client == null) {
            log.setPackageName(this.mContext.getPackageName());
        } else {
            log.setComponentName(client.autofillClientGetComponentName());
        }
        return log;
    }

    /* access modifiers changed from: private */
    public void setTrackedViews(int sessionId, AutofillId[] trackedIds, boolean saveOnAllViewsInvisible, boolean saveOnFinish, AutofillId[] fillableIds, AutofillId saveTriggerId) {
        if (saveTriggerId != null) {
            saveTriggerId.resetSessionId();
        }
        synchronized (this.mLock) {
            if (Helper.sVerbose) {
                Log.v(TAG, "setTrackedViews(): sessionId=" + sessionId + ", trackedIds=" + Arrays.toString(trackedIds) + ", saveOnAllViewsInvisible=" + saveOnAllViewsInvisible + ", saveOnFinish=" + saveOnFinish + ", fillableIds=" + Arrays.toString(fillableIds) + ", saveTrigerId=" + saveTriggerId + ", mFillableIds=" + this.mFillableIds + ", mEnabled=" + this.mEnabled + ", mSessionId=" + this.mSessionId);
            }
            if (this.mEnabled && this.mSessionId == sessionId) {
                if (saveOnAllViewsInvisible) {
                    this.mTrackedViews = new TrackedViews(trackedIds);
                } else {
                    this.mTrackedViews = null;
                }
                this.mSaveOnFinish = saveOnFinish;
                if (fillableIds != null) {
                    if (this.mFillableIds == null) {
                        this.mFillableIds = new ArraySet<>(fillableIds.length);
                    }
                    for (AutofillId id : fillableIds) {
                        id.resetSessionId();
                        this.mFillableIds.add(id);
                    }
                }
                if (this.mSaveTriggerId != null && !this.mSaveTriggerId.equals(saveTriggerId)) {
                    setNotifyOnClickLocked(this.mSaveTriggerId, false);
                }
                if (saveTriggerId != null && !saveTriggerId.equals(this.mSaveTriggerId)) {
                    this.mSaveTriggerId = saveTriggerId;
                    setNotifyOnClickLocked(this.mSaveTriggerId, true);
                }
            }
        }
    }

    private void setNotifyOnClickLocked(AutofillId id, boolean notify) {
        View view = findView(id);
        if (view == null) {
            Log.w(TAG, "setNotifyOnClick(): invalid id: " + id);
            return;
        }
        view.setNotifyAutofillManagerOnClick(notify);
    }

    /* access modifiers changed from: private */
    public void setSaveUiState(int sessionId, boolean shown) {
        if (Helper.sDebug) {
            Log.d(TAG, "setSaveUiState(" + sessionId + "): " + shown);
        }
        synchronized (this.mLock) {
            if (this.mSessionId != Integer.MAX_VALUE) {
                Log.w(TAG, "setSaveUiState(" + sessionId + ", " + shown + ") called on existing session " + this.mSessionId + "; cancelling it");
                cancelSessionLocked();
            }
            if (shown) {
                this.mSessionId = sessionId;
                this.mState = 3;
            } else {
                this.mSessionId = Integer.MAX_VALUE;
                this.mState = 0;
            }
        }
    }

    /* access modifiers changed from: private */
    public void setSessionFinished(int newState, List<AutofillId> autofillableIds) {
        if (autofillableIds != null) {
            for (int i = 0; i < autofillableIds.size(); i++) {
                autofillableIds.get(i).resetSessionId();
            }
        }
        synchronized (this.mLock) {
            if (Helper.sVerbose) {
                Log.v(TAG, "setSessionFinished(): from " + getStateAsStringLocked() + " to " + getStateAsString(newState) + "; autofillableIds=" + autofillableIds);
            }
            if (autofillableIds != null) {
                this.mEnteredIds = new ArraySet<>(autofillableIds);
            }
            if (newState != 5) {
                if (newState != 6) {
                    resetSessionLocked(false);
                    this.mState = newState;
                }
            }
            resetSessionLocked(true);
            this.mState = 0;
        }
    }

    /* access modifiers changed from: private */
    public void getAugmentedAutofillClient(IResultReceiver result) {
        synchronized (this.mLock) {
            if (this.mAugmentedAutofillServiceClient == null) {
                this.mAugmentedAutofillServiceClient = new AugmentedAutofillManagerClient();
            }
            Bundle resultData = new Bundle();
            resultData.putBinder(EXTRA_AUGMENTED_AUTOFILL_CLIENT, this.mAugmentedAutofillServiceClient.asBinder());
            try {
                result.send(0, resultData);
            } catch (RemoteException e) {
                Log.w(TAG, "Could not send AugmentedAutofillClient back: " + e);
            }
        }
    }

    public void requestHideFillUi() {
        requestHideFillUi(this.mIdShownFillUi, true);
    }

    /* access modifiers changed from: private */
    public void requestHideFillUi(AutofillId id, boolean force) {
        AutofillClient client;
        View anchor = id == null ? null : findView(id);
        if (Helper.sVerbose) {
            Log.v(TAG, "requestHideFillUi(" + id + "): anchor = " + anchor);
        }
        if (anchor != null) {
            requestHideFillUi(id, anchor);
        } else if (force && (client = getClient()) != null) {
            client.autofillClientRequestHideFillUi();
        }
    }

    private void requestHideFillUi(AutofillId id, View anchor) {
        AutofillCallback callback = null;
        synchronized (this.mLock) {
            AutofillClient client = getClient();
            if (client != null && client.autofillClientRequestHideFillUi()) {
                this.mIdShownFillUi = null;
                callback = this.mCallback;
            }
        }
        if (callback == null) {
            return;
        }
        if (id.isVirtualInt()) {
            callback.onAutofillEvent(anchor, id.getVirtualChildIntId(), 2);
        } else {
            callback.onAutofillEvent(anchor, 2);
        }
    }

    /* access modifiers changed from: private */
    public void notifyNoFillUi(int sessionId, AutofillId id, int sessionFinishedState) {
        if (Helper.sVerbose) {
            Log.v(TAG, "notifyNoFillUi(): sessionId=" + sessionId + ", autofillId=" + id + ", sessionFinishedState=" + sessionFinishedState);
        }
        View anchor = findView(id);
        if (anchor != null) {
            AutofillCallback callback = null;
            synchronized (this.mLock) {
                if (this.mSessionId == sessionId && getClient() != null) {
                    callback = this.mCallback;
                }
            }
            if (callback != null) {
                if (id.isVirtualInt()) {
                    callback.onAutofillEvent(anchor, id.getVirtualChildIntId(), 3);
                } else {
                    callback.onAutofillEvent(anchor, 3);
                }
            }
            if (sessionFinishedState != 0) {
                setSessionFinished(sessionFinishedState, (List<AutofillId>) null);
            }
        }
    }

    private View findView(AutofillId autofillId) {
        AutofillClient client = getClient();
        if (client != null) {
            return client.autofillClientFindViewByAutofillIdTraversal(autofillId);
        }
        return null;
    }

    public boolean hasAutofillFeature() {
        return this.mService != null;
    }

    public void onPendingSaveUi(int operation, IBinder token) {
        if (Helper.sVerbose) {
            Log.v(TAG, "onPendingSaveUi(" + operation + "): " + token);
        }
        synchronized (this.mLock) {
            try {
                this.mService.onPendingSaveUi(operation, token);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void dump(String outerPrefix, PrintWriter pw) {
        pw.print(outerPrefix);
        pw.println("AutofillManager:");
        String pfx = outerPrefix + "  ";
        pw.print(pfx);
        pw.print("sessionId: ");
        pw.println(this.mSessionId);
        pw.print(pfx);
        pw.print("state: ");
        pw.println(getStateAsStringLocked());
        pw.print(pfx);
        pw.print("context: ");
        pw.println(this.mContext);
        AutofillClient client = getClient();
        if (client != null) {
            pw.print(pfx);
            pw.print("client: ");
            pw.print(client);
            pw.print(" (");
            pw.print(client.autofillClientGetActivityToken());
            pw.println(')');
        }
        pw.print(pfx);
        pw.print("enabled: ");
        pw.println(this.mEnabled);
        pw.print(pfx);
        pw.print("enabledAugmentedOnly: ");
        pw.println(this.mForAugmentedAutofillOnly);
        pw.print(pfx);
        pw.print("hasService: ");
        boolean z = false;
        pw.println(this.mService != null);
        pw.print(pfx);
        pw.print("hasCallback: ");
        if (this.mCallback != null) {
            z = true;
        }
        pw.println(z);
        pw.print(pfx);
        pw.print("onInvisibleCalled ");
        pw.println(this.mOnInvisibleCalled);
        pw.print(pfx);
        pw.print("last autofilled data: ");
        pw.println(this.mLastAutofilledData);
        pw.print(pfx);
        pw.print("id of last fill UI shown: ");
        pw.println(this.mIdShownFillUi);
        pw.print(pfx);
        pw.print("tracked views: ");
        if (this.mTrackedViews == null) {
            pw.println("null");
        } else {
            String pfx2 = pfx + "  ";
            pw.println();
            pw.print(pfx2);
            pw.print("visible:");
            pw.println(this.mTrackedViews.mVisibleTrackedIds);
            pw.print(pfx2);
            pw.print("invisible:");
            pw.println(this.mTrackedViews.mInvisibleTrackedIds);
        }
        pw.print(pfx);
        pw.print("fillable ids: ");
        pw.println(this.mFillableIds);
        pw.print(pfx);
        pw.print("entered ids: ");
        pw.println(this.mEnteredIds);
        if (this.mEnteredForAugmentedAutofillIds != null) {
            pw.print(pfx);
            pw.print("entered ids for augmented autofill: ");
            pw.println(this.mEnteredForAugmentedAutofillIds);
        }
        if (this.mForAugmentedAutofillOnly) {
            pw.print(pfx);
            pw.println("For Augmented Autofill Only");
        }
        pw.print(pfx);
        pw.print("save trigger id: ");
        pw.println(this.mSaveTriggerId);
        pw.print(pfx);
        pw.print("save on finish(): ");
        pw.println(this.mSaveOnFinish);
        if (this.mOptions != null) {
            pw.print(pfx);
            pw.print("options: ");
            this.mOptions.dumpShort(pw);
            pw.println();
        }
        pw.print(pfx);
        pw.print("compat mode enabled: ");
        synchronized (this.mLock) {
            if (this.mCompatibilityBridge != null) {
                String pfx22 = pfx + "  ";
                pw.println("true");
                pw.print(pfx22);
                pw.print("windowId: ");
                pw.println(this.mCompatibilityBridge.mFocusedWindowId);
                pw.print(pfx22);
                pw.print("nodeId: ");
                pw.println(this.mCompatibilityBridge.mFocusedNodeId);
                pw.print(pfx22);
                pw.print("virtualId: ");
                pw.println(AccessibilityNodeInfo.getVirtualDescendantId(this.mCompatibilityBridge.mFocusedNodeId));
                pw.print(pfx22);
                pw.print("focusedBounds: ");
                pw.println(this.mCompatibilityBridge.mFocusedBounds);
            } else {
                pw.println("false");
            }
        }
        pw.print(pfx);
        pw.print("debug: ");
        pw.print(Helper.sDebug);
        pw.print(" verbose: ");
        pw.println(Helper.sVerbose);
    }

    @GuardedBy({"mLock"})
    private String getStateAsStringLocked() {
        return getStateAsString(this.mState);
    }

    private static String getStateAsString(int state) {
        switch (state) {
            case 0:
                return IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
            case 1:
                return "ACTIVE";
            case 2:
                return "FINISHED";
            case 3:
                return "SHOWING_SAVE_UI";
            case 4:
                return "DISABLED_BY_SERVICE";
            case 5:
                return "UNKNOWN_COMPAT_MODE";
            case 6:
                return "UNKNOWN_FAILED";
            default:
                return "INVALID:" + state;
        }
    }

    public static String getSmartSuggestionModeToString(int flags) {
        switch (flags) {
            case 0:
                return "OFF";
            case 1:
                return "SYSTEM";
            default:
                return "INVALID:" + flags;
        }
    }

    @GuardedBy({"mLock"})
    private boolean isActiveLocked() {
        return this.mState == 1;
    }

    @GuardedBy({"mLock"})
    private boolean isDisabledByServiceLocked() {
        return this.mState == 4;
    }

    @GuardedBy({"mLock"})
    private boolean isFinishedLocked() {
        return this.mState == 2;
    }

    /* access modifiers changed from: private */
    public void post(Runnable runnable) {
        AutofillClient client = getClient();
        if (client != null) {
            client.autofillClientRunOnUiThread(runnable);
        } else if (Helper.sVerbose) {
            Log.v(TAG, "ignoring post() because client is null");
        }
    }

    private final class CompatibilityBridge implements AccessibilityManager.AccessibilityPolicy {
        @GuardedBy({"mLock"})
        AccessibilityServiceInfo mCompatServiceInfo;
        /* access modifiers changed from: private */
        @GuardedBy({"mLock"})
        public final Rect mFocusedBounds = new Rect();
        /* access modifiers changed from: private */
        @GuardedBy({"mLock"})
        public long mFocusedNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        /* access modifiers changed from: private */
        @GuardedBy({"mLock"})
        public int mFocusedWindowId = -1;
        @GuardedBy({"mLock"})
        private final Rect mTempBounds = new Rect();

        CompatibilityBridge() {
            AccessibilityManager.getInstance(AutofillManager.this.mContext).setAccessibilityPolicy(this);
        }

        private AccessibilityServiceInfo getCompatServiceInfo() {
            synchronized (AutofillManager.this.mLock) {
                if (this.mCompatServiceInfo != null) {
                    AccessibilityServiceInfo accessibilityServiceInfo = this.mCompatServiceInfo;
                    return accessibilityServiceInfo;
                }
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("android", "com.android.server.autofill.AutofillCompatAccessibilityService"));
                try {
                    this.mCompatServiceInfo = new AccessibilityServiceInfo(AutofillManager.this.mContext.getPackageManager().resolveService(intent, 1048704), AutofillManager.this.mContext);
                    AccessibilityServiceInfo accessibilityServiceInfo2 = this.mCompatServiceInfo;
                    return accessibilityServiceInfo2;
                } catch (IOException | XmlPullParserException e) {
                    Log.e(AutofillManager.TAG, "Cannot find compat autofill service:" + intent);
                    throw new IllegalStateException("Cannot find compat autofill service");
                }
            }
        }

        public boolean isEnabled(boolean accessibilityEnabled) {
            return true;
        }

        public int getRelevantEventTypes(int relevantEventTypes) {
            return relevantEventTypes | 8 | 16 | 1 | 2048;
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> installedServices) {
            if (installedServices == null) {
                installedServices = new ArrayList<>();
            }
            installedServices.add(getCompatServiceInfo());
            return installedServices;
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackTypeFlags, List<AccessibilityServiceInfo> enabledService) {
            if (enabledService == null) {
                enabledService = new ArrayList<>();
            }
            enabledService.add(getCompatServiceInfo());
            return enabledService;
        }

        public AccessibilityEvent onAccessibilityEvent(AccessibilityEvent event, boolean accessibilityEnabled, int relevantEventTypes) {
            AutofillClient client;
            int type = event.getEventType();
            if (Helper.sVerbose) {
                Log.v(AutofillManager.TAG, "onAccessibilityEvent(" + AccessibilityEvent.eventTypeToString(type) + "): virtualId=" + AccessibilityNodeInfo.getVirtualDescendantId(event.getSourceNodeId()) + ", client=" + AutofillManager.this.getClient());
            }
            if (type == 1) {
                synchronized (AutofillManager.this.mLock) {
                    notifyViewClicked(event.getWindowId(), event.getSourceNodeId());
                }
            } else if (type == 8) {
                synchronized (AutofillManager.this.mLock) {
                    if (this.mFocusedWindowId == event.getWindowId() && this.mFocusedNodeId == event.getSourceNodeId()) {
                        return event;
                    }
                    if (!(this.mFocusedWindowId == -1 || this.mFocusedNodeId == AccessibilityNodeInfo.UNDEFINED_NODE_ID)) {
                        notifyViewExited(this.mFocusedWindowId, this.mFocusedNodeId);
                        this.mFocusedWindowId = -1;
                        this.mFocusedNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
                        this.mFocusedBounds.set(0, 0, 0, 0);
                    }
                    int windowId = event.getWindowId();
                    long nodeId = event.getSourceNodeId();
                    if (notifyViewEntered(windowId, nodeId, this.mFocusedBounds)) {
                        this.mFocusedWindowId = windowId;
                        this.mFocusedNodeId = nodeId;
                    }
                }
            } else if (type == 16) {
                synchronized (AutofillManager.this.mLock) {
                    if (this.mFocusedWindowId == event.getWindowId() && this.mFocusedNodeId == event.getSourceNodeId()) {
                        notifyValueChanged(event.getWindowId(), event.getSourceNodeId());
                    }
                }
            } else if (type == 2048 && (client = AutofillManager.this.getClient()) != null) {
                synchronized (AutofillManager.this.mLock) {
                    if (client.autofillClientIsFillUiShowing()) {
                        notifyViewEntered(this.mFocusedWindowId, this.mFocusedNodeId, this.mFocusedBounds);
                    }
                    updateTrackedViewsLocked();
                }
            }
            if (accessibilityEnabled) {
                return event;
            }
            return null;
        }

        private boolean notifyViewEntered(int windowId, long nodeId, Rect focusedBounds) {
            View view;
            AccessibilityNodeInfo node;
            int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(nodeId);
            if (!isVirtualNode(virtualId) || (view = findViewByAccessibilityId(windowId, nodeId)) == null || (node = findVirtualNodeByAccessibilityId(view, virtualId)) == null || !node.isEditable()) {
                return false;
            }
            Rect newBounds = this.mTempBounds;
            node.getBoundsInScreen(newBounds);
            if (newBounds.equals(focusedBounds)) {
                return false;
            }
            focusedBounds.set(newBounds);
            AutofillManager.this.notifyViewEntered(view, virtualId, newBounds);
            return true;
        }

        private void notifyViewExited(int windowId, long nodeId) {
            View view;
            int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(nodeId);
            if (isVirtualNode(virtualId) && (view = findViewByAccessibilityId(windowId, nodeId)) != null) {
                AutofillManager.this.notifyViewExited(view, virtualId);
            }
        }

        private void notifyValueChanged(int windowId, long nodeId) {
            View view;
            AccessibilityNodeInfo node;
            int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(nodeId);
            if (isVirtualNode(virtualId) && (view = findViewByAccessibilityId(windowId, nodeId)) != null && (node = findVirtualNodeByAccessibilityId(view, virtualId)) != null) {
                AutofillManager.this.notifyValueChanged(view, virtualId, AutofillValue.forText(node.getText()));
            }
        }

        private void notifyViewClicked(int windowId, long nodeId) {
            View view;
            int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(nodeId);
            if (isVirtualNode(virtualId) && (view = findViewByAccessibilityId(windowId, nodeId)) != null && findVirtualNodeByAccessibilityId(view, virtualId) != null) {
                AutofillManager.this.notifyViewClicked(view, virtualId);
            }
        }

        @GuardedBy({"mLock"})
        private void updateTrackedViewsLocked() {
            if (AutofillManager.this.mTrackedViews != null) {
                AutofillManager.this.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
        }

        private View findViewByAccessibilityId(int windowId, long nodeId) {
            AutofillClient client = AutofillManager.this.getClient();
            if (client == null) {
                return null;
            }
            return client.autofillClientFindViewByAccessibilityIdTraversal(AccessibilityNodeInfo.getAccessibilityViewId(nodeId), windowId);
        }

        private AccessibilityNodeInfo findVirtualNodeByAccessibilityId(View view, int virtualId) {
            AccessibilityNodeProvider provider = view.getAccessibilityNodeProvider();
            if (provider == null) {
                return null;
            }
            return provider.createAccessibilityNodeInfo(virtualId);
        }

        private boolean isVirtualNode(int nodeId) {
            return (nodeId == -1 || nodeId == Integer.MAX_VALUE) ? false : true;
        }
    }

    private class TrackedViews {
        /* access modifiers changed from: private */
        public ArraySet<AutofillId> mInvisibleTrackedIds;
        /* access modifiers changed from: private */
        public ArraySet<AutofillId> mVisibleTrackedIds;

        private <T> boolean isInSet(ArraySet<T> set, T value) {
            return set != null && set.contains(value);
        }

        private <T> ArraySet<T> addToSet(ArraySet<T> set, T valueToAdd) {
            if (set == null) {
                set = new ArraySet<>(1);
            }
            set.add(valueToAdd);
            return set;
        }

        private <T> ArraySet<T> removeFromSet(ArraySet<T> set, T valueToRemove) {
            if (set == null) {
                return null;
            }
            set.remove(valueToRemove);
            if (set.isEmpty()) {
                return null;
            }
            return set;
        }

        TrackedViews(AutofillId[] trackedIds) {
            boolean[] isVisible;
            AutofillClient client = AutofillManager.this.getClient();
            if (!ArrayUtils.isEmpty((T[]) trackedIds) && client != null) {
                if (client.autofillClientIsVisibleForAutofill()) {
                    if (Helper.sVerbose) {
                        Log.v(AutofillManager.TAG, "client is visible, check tracked ids");
                    }
                    isVisible = client.autofillClientGetViewVisibility(trackedIds);
                } else {
                    isVisible = new boolean[trackedIds.length];
                }
                int numIds = trackedIds.length;
                for (int i = 0; i < numIds; i++) {
                    AutofillId id = trackedIds[i];
                    id.resetSessionId();
                    if (isVisible[i]) {
                        this.mVisibleTrackedIds = addToSet(this.mVisibleTrackedIds, id);
                    } else {
                        this.mInvisibleTrackedIds = addToSet(this.mInvisibleTrackedIds, id);
                    }
                }
            }
            if (Helper.sVerbose) {
                Log.v(AutofillManager.TAG, "TrackedViews(trackedIds=" + Arrays.toString(trackedIds) + "):  mVisibleTrackedIds=" + this.mVisibleTrackedIds + " mInvisibleTrackedIds=" + this.mInvisibleTrackedIds);
            }
            if (this.mVisibleTrackedIds == null) {
                AutofillManager.this.finishSessionLocked();
            }
        }

        /* access modifiers changed from: package-private */
        @GuardedBy({"mLock"})
        public void notifyViewVisibilityChangedLocked(AutofillId id, boolean isVisible) {
            if (Helper.sDebug) {
                Log.d(AutofillManager.TAG, "notifyViewVisibilityChangedLocked(): id=" + id + " isVisible=" + isVisible);
            }
            if (AutofillManager.this.isClientVisibleForAutofillLocked()) {
                if (isVisible) {
                    if (isInSet(this.mInvisibleTrackedIds, id)) {
                        this.mInvisibleTrackedIds = removeFromSet(this.mInvisibleTrackedIds, id);
                        this.mVisibleTrackedIds = addToSet(this.mVisibleTrackedIds, id);
                    }
                } else if (isInSet(this.mVisibleTrackedIds, id)) {
                    this.mVisibleTrackedIds = removeFromSet(this.mVisibleTrackedIds, id);
                    this.mInvisibleTrackedIds = addToSet(this.mInvisibleTrackedIds, id);
                }
            }
            if (this.mVisibleTrackedIds == null) {
                if (Helper.sVerbose) {
                    Log.v(AutofillManager.TAG, "No more visible ids. Invisibile = " + this.mInvisibleTrackedIds);
                }
                AutofillManager.this.finishSessionLocked();
            }
        }

        /* access modifiers changed from: package-private */
        @GuardedBy({"mLock"})
        public void onVisibleForAutofillChangedLocked() {
            AutofillClient client = AutofillManager.this.getClient();
            ArraySet<AutofillId> updatedVisibleTrackedIds = null;
            ArraySet<AutofillId> updatedVisibleTrackedIds2 = null;
            if (client != null) {
                if (Helper.sVerbose) {
                    Log.v(AutofillManager.TAG, "onVisibleForAutofillChangedLocked(): inv= " + this.mInvisibleTrackedIds + " vis=" + this.mVisibleTrackedIds);
                }
                if (this.mInvisibleTrackedIds != null) {
                    ArrayList<AutofillId> orderedInvisibleIds = new ArrayList<>(this.mInvisibleTrackedIds);
                    boolean[] isVisible = client.autofillClientGetViewVisibility(Helper.toArray(orderedInvisibleIds));
                    int numInvisibleTrackedIds = orderedInvisibleIds.size();
                    ArraySet<AutofillId> updatedInvisibleTrackedIds = null;
                    ArraySet<AutofillId> updatedVisibleTrackedIds3 = null;
                    for (int i = 0; i < numInvisibleTrackedIds; i++) {
                        AutofillId id = orderedInvisibleIds.get(i);
                        if (isVisible[i]) {
                            updatedVisibleTrackedIds3 = addToSet(updatedVisibleTrackedIds3, id);
                            if (Helper.sDebug) {
                                Log.d(AutofillManager.TAG, "onVisibleForAutofill() " + id + " became visible");
                            }
                        } else {
                            updatedInvisibleTrackedIds = addToSet(updatedInvisibleTrackedIds, id);
                        }
                    }
                    updatedVisibleTrackedIds = updatedVisibleTrackedIds3;
                    updatedVisibleTrackedIds2 = updatedInvisibleTrackedIds;
                }
                if (this.mVisibleTrackedIds != null) {
                    ArrayList<AutofillId> orderedVisibleIds = new ArrayList<>(this.mVisibleTrackedIds);
                    boolean[] isVisible2 = client.autofillClientGetViewVisibility(Helper.toArray(orderedVisibleIds));
                    int numVisibleTrackedIds = orderedVisibleIds.size();
                    for (int i2 = 0; i2 < numVisibleTrackedIds; i2++) {
                        AutofillId id2 = orderedVisibleIds.get(i2);
                        if (isVisible2[i2]) {
                            updatedVisibleTrackedIds = addToSet(updatedVisibleTrackedIds, id2);
                        } else {
                            updatedVisibleTrackedIds2 = addToSet(updatedVisibleTrackedIds2, id2);
                            if (Helper.sDebug) {
                                Log.d(AutofillManager.TAG, "onVisibleForAutofill() " + id2 + " became invisible");
                            }
                        }
                    }
                }
                this.mInvisibleTrackedIds = updatedVisibleTrackedIds2;
                this.mVisibleTrackedIds = updatedVisibleTrackedIds;
            }
            if (this.mVisibleTrackedIds == null) {
                if (Helper.sVerbose) {
                    Log.v(AutofillManager.TAG, "onVisibleForAutofillChangedLocked(): no more visible ids");
                }
                AutofillManager.this.finishSessionLocked();
            }
        }
    }

    public static abstract class AutofillCallback {
        public static final int EVENT_INPUT_HIDDEN = 2;
        public static final int EVENT_INPUT_SHOWN = 1;
        public static final int EVENT_INPUT_UNAVAILABLE = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AutofillEventType {
        }

        public void onAutofillEvent(View view, int event) {
        }

        public void onAutofillEvent(View view, int virtualId, int event) {
        }
    }

    private static final class AutofillManagerClient extends IAutoFillManagerClient.Stub {
        private final WeakReference<AutofillManager> mAfm;

        private AutofillManagerClient(AutofillManager autofillManager) {
            this.mAfm = new WeakReference<>(autofillManager);
        }

        public void setState(int flags) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(flags) {
                    private final /* synthetic */ int f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AutofillManager.this.setState(this.f$1);
                    }
                });
            }
        }

        public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, ids, values) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ List f$2;
                    private final /* synthetic */ List f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void run() {
                        AutofillManager.this.autofill(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        public void authenticate(int sessionId, int authenticationId, IntentSender intent, Intent fillInIntent) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, authenticationId, intent, fillInIntent) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ int f$2;
                    private final /* synthetic */ IntentSender f$3;
                    private final /* synthetic */ Intent f$4;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                    }

                    public final void run() {
                        AutofillManager.this.authenticate(this.f$1, this.f$2, this.f$3, this.f$4);
                    }
                });
            }
        }

        public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, id, width, height, anchorBounds, presenter) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ AutofillId f$2;
                    private final /* synthetic */ int f$3;
                    private final /* synthetic */ int f$4;
                    private final /* synthetic */ Rect f$5;
                    private final /* synthetic */ IAutofillWindowPresenter f$6;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                        this.f$5 = r6;
                        this.f$6 = r7;
                    }

                    public final void run() {
                        AutofillManager.this.requestShowFillUi(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
                    }
                });
            }
        }

        public void requestHideFillUi(int sessionId, AutofillId id) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(id) {
                    private final /* synthetic */ AutofillId f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AutofillManager.this.requestHideFillUi(this.f$1, false);
                    }
                });
            }
        }

        public void notifyNoFillUi(int sessionId, AutofillId id, int sessionFinishedState) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, id, sessionFinishedState) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ AutofillId f$2;
                    private final /* synthetic */ int f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void run() {
                        AutofillManager.this.notifyNoFillUi(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        public void dispatchUnhandledKey(int sessionId, AutofillId id, KeyEvent fullScreen) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, id, fullScreen) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ AutofillId f$2;
                    private final /* synthetic */ KeyEvent f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void run() {
                        AutofillManager.this.dispatchUnhandledKey(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        public void startIntentSender(IntentSender intentSender, Intent intent) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(intentSender, intent) {
                    private final /* synthetic */ IntentSender f$1;
                    private final /* synthetic */ Intent f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        AutofillManager.AutofillManagerClient.lambda$startIntentSender$7(AutofillManager.this, this.f$1, this.f$2);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$startIntentSender$7(AutofillManager afm, IntentSender intentSender, Intent intent) {
            try {
                afm.mContext.startIntentSender(intentSender, intent, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e(AutofillManager.TAG, "startIntentSender() failed for intent:" + intentSender, e);
            }
        }

        public void setTrackedViews(int sessionId, AutofillId[] ids, boolean saveOnAllViewsInvisible, boolean saveOnFinish, AutofillId[] fillableIds, AutofillId saveTriggerId) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, ids, saveOnAllViewsInvisible, saveOnFinish, fillableIds, saveTriggerId) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ AutofillId[] f$2;
                    private final /* synthetic */ boolean f$3;
                    private final /* synthetic */ boolean f$4;
                    private final /* synthetic */ AutofillId[] f$5;
                    private final /* synthetic */ AutofillId f$6;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                        this.f$5 = r6;
                        this.f$6 = r7;
                    }

                    public final void run() {
                        AutofillManager.this.setTrackedViews(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
                    }
                });
            }
        }

        public void setSaveUiState(int sessionId, boolean shown) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, shown) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ boolean f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        AutofillManager.this.setSaveUiState(this.f$1, this.f$2);
                    }
                });
            }
        }

        public void setSessionFinished(int newState, List<AutofillId> autofillableIds) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(newState, autofillableIds) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ List f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        AutofillManager.this.setSessionFinished(this.f$1, this.f$2);
                    }
                });
            }
        }

        public void getAugmentedAutofillClient(IResultReceiver result) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(result) {
                    private final /* synthetic */ IResultReceiver f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AutofillManager.this.getAugmentedAutofillClient(this.f$1);
                    }
                });
            }
        }
    }

    private static final class AugmentedAutofillManagerClient extends IAugmentedAutofillManagerClient.Stub {
        private final WeakReference<AutofillManager> mAfm;

        private AugmentedAutofillManagerClient(AutofillManager autofillManager) {
            this.mAfm = new WeakReference<>(autofillManager);
        }

        public Rect getViewCoordinates(AutofillId id) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm == null) {
                return null;
            }
            AutofillClient client = afm.getClient();
            if (client == null) {
                Log.w(AutofillManager.TAG, "getViewCoordinates(" + id + "): no autofill client");
                return null;
            }
            View view = client.autofillClientFindViewByAutofillIdTraversal(id);
            if (view == null) {
                Log.w(AutofillManager.TAG, "getViewCoordinates(" + id + "): could not find view");
                return null;
            }
            Rect windowVisibleDisplayFrame = new Rect();
            view.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Rect rect = new Rect(location[0], location[1] - windowVisibleDisplayFrame.top, location[0] + view.getWidth(), (location[1] - windowVisibleDisplayFrame.top) + view.getHeight());
            if (Helper.sVerbose) {
                Log.v(AutofillManager.TAG, "Coordinates for " + id + PluralRules.KEYWORD_RULE_SEPARATOR + rect);
            }
            return rect;
        }

        public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, ids, values) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ List f$2;
                    private final /* synthetic */ List f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void run() {
                        AutofillManager.this.autofill(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(sessionId, id, width, height, anchorBounds, presenter) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ AutofillId f$2;
                    private final /* synthetic */ int f$3;
                    private final /* synthetic */ int f$4;
                    private final /* synthetic */ Rect f$5;
                    private final /* synthetic */ IAutofillWindowPresenter f$6;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                        this.f$5 = r6;
                        this.f$6 = r7;
                    }

                    public final void run() {
                        AutofillManager.this.requestShowFillUi(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
                    }
                });
            }
        }

        public void requestHideFillUi(int sessionId, AutofillId id) {
            AutofillManager afm = (AutofillManager) this.mAfm.get();
            if (afm != null) {
                afm.post(new Runnable(id) {
                    private final /* synthetic */ AutofillId f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AutofillManager.this.requestHideFillUi(this.f$1, false);
                    }
                });
            }
        }
    }
}
