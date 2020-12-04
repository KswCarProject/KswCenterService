package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.util.FunctionalUtils;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class PhoneStateListener {
    private static final boolean DBG = false;
    public static final int LISTEN_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGE = 4194304;
    @SystemApi
    public static final int LISTEN_CALL_ATTRIBUTES_CHANGED = 67108864;
    @SystemApi
    public static final int LISTEN_CALL_DISCONNECT_CAUSES = 33554432;
    public static final int LISTEN_CALL_FORWARDING_INDICATOR = 8;
    public static final int LISTEN_CALL_STATE = 32;
    public static final int LISTEN_CARRIER_NETWORK_CHANGE = 65536;
    public static final int LISTEN_CELL_INFO = 1024;
    public static final int LISTEN_CELL_LOCATION = 16;
    public static final int LISTEN_DATA_ACTIVATION_STATE = 262144;
    public static final int LISTEN_DATA_ACTIVITY = 128;
    @Deprecated
    public static final int LISTEN_DATA_CONNECTION_REAL_TIME_INFO = 8192;
    public static final int LISTEN_DATA_CONNECTION_STATE = 64;
    public static final int LISTEN_EMERGENCY_NUMBER_LIST = 16777216;
    @SystemApi
    public static final int LISTEN_IMS_CALL_DISCONNECT_CAUSES = 134217728;
    public static final int LISTEN_MESSAGE_WAITING_INDICATOR = 4;
    public static final int LISTEN_NONE = 0;
    @Deprecated
    public static final int LISTEN_OEM_HOOK_RAW_EVENT = 32768;
    public static final int LISTEN_OTASP_CHANGED = 512;
    public static final int LISTEN_PHONE_CAPABILITY_CHANGE = 2097152;
    public static final int LISTEN_PHYSICAL_CHANNEL_CONFIGURATION = 1048576;
    @SystemApi
    public static final int LISTEN_PRECISE_CALL_STATE = 2048;
    @SystemApi
    public static final int LISTEN_PRECISE_DATA_CONNECTION_STATE = 4096;
    @SystemApi
    public static final int LISTEN_RADIO_POWER_STATE_CHANGED = 8388608;
    public static final int LISTEN_SERVICE_STATE = 1;
    @Deprecated
    public static final int LISTEN_SIGNAL_STRENGTH = 2;
    public static final int LISTEN_SIGNAL_STRENGTHS = 256;
    @SystemApi
    public static final int LISTEN_SRVCC_STATE_CHANGED = 16384;
    public static final int LISTEN_USER_MOBILE_DATA_STATE = 524288;
    @SystemApi
    public static final int LISTEN_VOICE_ACTIVATION_STATE = 131072;
    private static final String LOG_TAG = "PhoneStateListener";
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @UnsupportedAppUsage
    public final IPhoneStateListener callback;
    @UnsupportedAppUsage
    protected Integer mSubId;

    public PhoneStateListener() {
        this((Integer) null, Looper.myLooper());
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public PhoneStateListener(Looper looper) {
        this((Integer) null, looper);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public PhoneStateListener(Integer subId) {
        this(subId, Looper.myLooper());
        if (subId != null && VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            throw new IllegalArgumentException("PhoneStateListener with subId: " + subId + " is not supported, use default constructor");
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public PhoneStateListener(Integer subId, Looper looper) {
        this(subId, (Executor) new HandlerExecutor(new Handler(looper)));
        if (subId != null && VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            throw new IllegalArgumentException("PhoneStateListener with subId: " + subId + " is not supported, use default constructor");
        }
    }

    public PhoneStateListener(Executor executor) {
        this((Integer) null, executor);
    }

    private PhoneStateListener(Integer subId, Executor e) {
        if (e != null) {
            this.mSubId = subId;
            this.callback = new IPhoneStateListenerStub(this, e);
            return;
        }
        throw new IllegalArgumentException("PhoneStateListener Executor must be non-null");
    }

    public void onServiceStateChanged(ServiceState serviceState) {
    }

    @Deprecated
    public void onSignalStrengthChanged(int asu) {
    }

    public void onMessageWaitingIndicatorChanged(boolean mwi) {
    }

    public void onCallForwardingIndicatorChanged(boolean cfi) {
    }

    public void onCellLocationChanged(CellLocation location) {
    }

    public void onCallStateChanged(int state, String phoneNumber) {
    }

    public void onDataConnectionStateChanged(int state) {
    }

    public void onDataConnectionStateChanged(int state, int networkType) {
    }

    public void onDataActivity(int direction) {
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
    }

    @UnsupportedAppUsage
    public void onOtaspChanged(int otaspMode) {
    }

    public void onCellInfoChanged(List<CellInfo> list) {
    }

    @SystemApi
    public void onPreciseCallStateChanged(PreciseCallState callState) {
    }

    @SystemApi
    public void onCallDisconnectCauseChanged(int disconnectCause, int preciseDisconnectCause) {
    }

    @SystemApi
    public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) {
    }

    @SystemApi
    public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState dataConnectionState) {
    }

    @UnsupportedAppUsage
    public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dcRtInfo) {
    }

    @SystemApi
    public void onSrvccStateChanged(int srvccState) {
    }

    @SystemApi
    public void onVoiceActivationStateChanged(int state) {
    }

    public void onDataActivationStateChanged(int state) {
    }

    public void onUserMobileDataStateChanged(boolean enabled) {
    }

    public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) {
    }

    public void onEmergencyNumberListChanged(Map<Integer, List<EmergencyNumber>> map) {
    }

    @UnsupportedAppUsage
    public void onOemHookRawEvent(byte[] rawData) {
    }

    public void onPhoneCapabilityChanged(PhoneCapability capability) {
    }

    public void onActiveDataSubscriptionIdChanged(int subId) {
    }

    @SystemApi
    public void onCallAttributesChanged(CallAttributes callAttributes) {
    }

    @SystemApi
    public void onRadioPowerStateChanged(int state) {
    }

    public void onCarrierNetworkChange(boolean active) {
    }

    private static class IPhoneStateListenerStub extends IPhoneStateListener.Stub {
        private Executor mExecutor;
        private WeakReference<PhoneStateListener> mPhoneStateListenerWeakRef;

        IPhoneStateListenerStub(PhoneStateListener phoneStateListener, Executor executor) {
            this.mPhoneStateListenerWeakRef = new WeakReference<>(phoneStateListener);
            this.mExecutor = executor;
        }

        public void onServiceStateChanged(ServiceState serviceState) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, serviceState) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ ServiceState f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ ServiceState f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onServiceStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onSignalStrengthChanged(int asu) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, asu) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onSignalStrengthChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onMessageWaitingIndicatorChanged(boolean mwi) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, mwi) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ boolean f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ boolean f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onMessageWaitingIndicatorChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCallForwardingIndicatorChanged(boolean cfi) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, cfi) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ boolean f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ boolean f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCallForwardingIndicatorChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCellLocationChanged(Bundle bundle) {
            CellLocation location = CellLocation.newFromBundle(bundle);
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, location) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ CellLocation f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ CellLocation f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCellLocationChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCallStateChanged(int state, String incomingNumber) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, state, incomingNumber) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;
                    private final /* synthetic */ String f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2, this.f$3) {
                            private final /* synthetic */ int f$1;
                            private final /* synthetic */ String f$2;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCallStateChanged(this.f$1, this.f$2);
                            }
                        });
                    }
                });
            }
        }

        public void onDataConnectionStateChanged(int state, int networkType) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, state, networkType) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;
                    private final /* synthetic */ int f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2, this.f$3) {
                            private final /* synthetic */ int f$1;
                            private final /* synthetic */ int f$2;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                            }

                            public final void run() {
                                PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$12(PhoneStateListener.this, this.f$1, this.f$2);
                            }
                        });
                    }
                });
            }
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$12(PhoneStateListener psl, int state, int networkType) {
            psl.onDataConnectionStateChanged(state, networkType);
            psl.onDataConnectionStateChanged(state);
        }

        public void onDataActivity(int direction) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, direction) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onDataActivity(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, signalStrength) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ SignalStrength f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ SignalStrength f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onSignalStrengthsChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onOtaspChanged(int otaspMode) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, otaspMode) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onOtaspChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, cellInfo) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ List f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ List f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCellInfoChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onPreciseCallStateChanged(PreciseCallState callState) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, callState) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ PreciseCallState f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ PreciseCallState f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onPreciseCallStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCallDisconnectCauseChanged(int disconnectCause, int preciseDisconnectCause) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, disconnectCause, preciseDisconnectCause) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;
                    private final /* synthetic */ int f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2, this.f$3) {
                            private final /* synthetic */ int f$1;
                            private final /* synthetic */ int f$2;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCallDisconnectCauseChanged(this.f$1, this.f$2);
                            }
                        });
                    }
                });
            }
        }

        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState dataConnectionState) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, dataConnectionState) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ PreciseDataConnectionState f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ PreciseDataConnectionState f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onPreciseDataConnectionStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dcRtInfo) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, dcRtInfo) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ DataConnectionRealTimeInfo f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ DataConnectionRealTimeInfo f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onDataConnectionRealTimeInfoChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onSrvccStateChanged(int state) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, state) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onSrvccStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onVoiceActivationStateChanged(int activationState) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, activationState) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onVoiceActivationStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onDataActivationStateChanged(int activationState) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, activationState) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onDataActivationStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onUserMobileDataStateChanged(boolean enabled) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, enabled) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ boolean f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ boolean f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onUserMobileDataStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onOemHookRawEvent(byte[] rawData) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, rawData) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ byte[] f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ byte[] f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onOemHookRawEvent(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCarrierNetworkChange(boolean active) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, active) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ boolean f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ boolean f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCarrierNetworkChange(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> configs) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, configs) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ List f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ List f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onPhysicalChannelConfigurationChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onEmergencyNumberListChanged(Map emergencyNumberList) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, emergencyNumberList) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ Map f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ Map f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onEmergencyNumberListChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onPhoneCapabilityChanged(PhoneCapability capability) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, capability) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ PhoneCapability f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ PhoneCapability f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onPhoneCapabilityChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onRadioPowerStateChanged(int state) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, state) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onRadioPowerStateChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onCallAttributesChanged(CallAttributes callAttributes) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, callAttributes) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ CallAttributes f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ CallAttributes f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onCallAttributesChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onActiveDataSubIdChanged(int subId) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, subId) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ int f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onActiveDataSubscriptionIdChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }

        public void onImsCallDisconnectCauseChanged(ImsReasonInfo disconnectCause) {
            PhoneStateListener psl = (PhoneStateListener) this.mPhoneStateListenerWeakRef.get();
            if (psl != null) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(psl, disconnectCause) {
                    private final /* synthetic */ PhoneStateListener f$1;
                    private final /* synthetic */ ImsReasonInfo f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void runOrThrow() {
                        PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable(this.f$2) {
                            private final /* synthetic */ ImsReasonInfo f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                PhoneStateListener.this.onImsCallDisconnectCauseChanged(this.f$1);
                            }
                        });
                    }
                });
            }
        }
    }

    private void log(String s) {
        Rlog.d(LOG_TAG, s);
    }
}
