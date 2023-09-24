package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.HandlerExecutor;
import android.p007os.Looper;
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

/* loaded from: classes.dex */
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
    @UnsupportedAppUsage
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
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
        this(subId, new HandlerExecutor(new Handler(looper)));
        if (subId != null && VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            throw new IllegalArgumentException("PhoneStateListener with subId: " + subId + " is not supported, use default constructor");
        }
    }

    public PhoneStateListener(Executor executor) {
        this((Integer) null, executor);
    }

    private PhoneStateListener(Integer subId, Executor e) {
        if (e == null) {
            throw new IllegalArgumentException("PhoneStateListener Executor must be non-null");
        }
        this.mSubId = subId;
        this.callback = new IPhoneStateListenerStub(this, e);
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

    public void onCellInfoChanged(List<CellInfo> cellInfo) {
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

    public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> configs) {
    }

    public void onEmergencyNumberListChanged(Map<Integer, List<EmergencyNumber>> emergencyNumberList) {
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

    /* loaded from: classes.dex */
    private static class IPhoneStateListenerStub extends IPhoneStateListener.Stub {
        private Executor mExecutor;
        private WeakReference<PhoneStateListener> mPhoneStateListenerWeakRef;

        IPhoneStateListenerStub(PhoneStateListener phoneStateListener, Executor executor) {
            this.mPhoneStateListenerWeakRef = new WeakReference<>(phoneStateListener);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(final ServiceState serviceState) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$uC5syhzl229gIpaK7Jfs__OCJxQ
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nrGqSRBJrc3_EwotCDNwfKeizIo
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onServiceStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(final int asu) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$M39is_Zyt8D7Camw2NS4EGTDn-s
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5J-sdvem6pUpdVwRdm8IbDhvuv8
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onSignalStrengthChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(final boolean mwi) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$okPCYOx4UxYuvUHlM2iS425QGIg
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$TqrkuLPlaG_ucU7VbLS4tnf8hG8
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onMessageWaitingIndicatorChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(final boolean cfi) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$1M3m0i6211i2YjWyTDT7l0bJm3I
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$WYWtWHdkZDxBd9anjoxyZozPWHc
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCallForwardingIndicatorChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(Bundle bundle) {
            final CellLocation location = CellLocation.newFromBundle(bundle);
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Hbn6-eZxY2p3rjOfStodI04A8E8
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2cMrwdqnKBpixpApeIX38rmRLak
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCellLocationChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(final int state, final String incomingNumber) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$oDAZqs8paeefe_3k_uRKV5plQW4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$6czWSGzxct0CXPVO54T0aq05qls
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCallStateChanged(r2, r3);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(final int state, final int networkType) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2VMO21pFQN-JN3kpn6vQN1zPFEU
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$dUc3j82sK9P9Zpaq-91n9bk_Rpc
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$12(PhoneStateListener.this, r2, r3);
                        }
                    });
                }
            });
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$12(PhoneStateListener psl, int state, int networkType) {
            psl.onDataConnectionStateChanged(state, networkType);
            psl.onDataConnectionStateChanged(state);
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(final int direction) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$XyayAGWQZC2dNjwr697SfSGBBOc
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$JalixlMNdjktPsNntP_JT9pymhs
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onDataActivity(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(final SignalStrength signalStrength) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$aysbwPqxcLV_5w6LP0TzZu2D-ew
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$0s34qsuHFsa43jUHrTkD62ni6Ds
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onSignalStrengthsChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOtaspChanged(final int otaspMode) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$i4r8mBqOfCy4bnbF_JG7ujDXEOQ
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$H1CbxYUcdxs1WggP_RRULTY01K8
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onOtaspChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(final List<CellInfo> cellInfo) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$yvQnAlFGg5EWDG2vcA9X-4xnalA
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q2A8FgYlU8_D6PD78tThGut_rTc
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCellInfoChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(final PreciseCallState callState) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bELzxgwsPigyVKYkAXBO2BjcSm8
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$4NHt5Shg_DHV-T1IxfcQLHP5-j0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onPreciseCallStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(final int disconnectCause, final int preciseDisconnectCause) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$icX71zgNszuMfnDaCmahcqWacFM
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$hxq77a5O_MUfoptHg15ipzFvMkI
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCallDisconnectCauseChanged(r2, r3);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(final PreciseDataConnectionState dataConnectionState) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$RC2x2ijetA-pQrLa4QakzMBjh_k
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$HEcWn-J1WRb0wLERu2qoMIZDfjY
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onPreciseDataConnectionStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(final DataConnectionRealTimeInfo dcRtInfo) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OfwFKKtcQHRmtv70FCopw6FDAAU
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$IU278K5QbmReF-mbpcNVAvVlhFI
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onDataConnectionRealTimeInfoChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(final int state) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nR7W5ox6SCgPxtH9IRcENwKeFI4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$ygzOWFRiY4sZQ4WYUPIefqgiGvM
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onSrvccStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(final int activationState) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5rF2IFj8mrb7uZc0HMKiuCodUn0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$y-tK7my_uXPo_oQ7AytfnekGEbU
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onVoiceActivationStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(final int activationState) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$t2gWJ_jA36kAdNXSmlzw85aU-tM
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$W65ui1dCCc-JnQa7gon1I7Bz7Sk
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onDataActivationStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(final boolean enabled) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5uu-05j4ojTh9mEHkN-ynQqQRGM
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5Uf5OZWCyPD0lZtySzbYw18FWhU
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onUserMobileDataStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(final byte[] rawData) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jNtyZYh5ZAuvyDZA_6f30zhW_dI
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jclAV5yU3RtV94suRvvhafvGuhw
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onOemHookRawEvent(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(final boolean active) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$YY3srkIkMm8vTSFJZHoiKzUUrGs
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jlNX9JiqGSNg9W49vDcKucKdeCI
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCarrierNetworkChange(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigurationChanged(final List<PhysicalChannelConfig> configs) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OIAjnTzp_YIf6Y7jPFABi9BXZvs
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nMiL2eSbUjYsM-AZ8joz_n4dLz0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onPhysicalChannelConfigurationChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(final Map emergencyNumberList) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$d9DVwzLraeX80tegF_wEzf_k2FI
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jGj-qFMdpjbsKaUErqJEeOALEGo
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onEmergencyNumberListChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(final PhoneCapability capability) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$-CiOzgf6ys4EwlCYOVUsuz9YQ5c
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$lHL69WZlO89JjNC1LLvFWp2OuKY
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onPhoneCapabilityChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(final int state) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$TYOBpOfoS3xjFssrzOJyHTelndw
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bI97h5HT-IYvguXIcngwUrpGxrw
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onRadioPowerStateChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallAttributesChanged(final CallAttributes callAttributes) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q_Cpm8aB8qYt8lGxD5PXek_-4bA
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5t7yF_frkRH7MdItRlwmP00irsM
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onCallAttributesChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(final int subId) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$ipH9N0fJiGE9EBJHahQeXcCZXzo
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nnG75RvQ1_1KZGJk1ySeCH1JJRg
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onActiveDataSubscriptionIdChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(final ImsReasonInfo disconnectCause) {
            final PhoneStateListener psl = this.mPhoneStateListenerWeakRef.get();
            if (psl == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Bzok3Q_pjLC0O4ulkDfbWru0v6w
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    PhoneStateListener.IPhoneStateListenerStub.this.mExecutor.execute(new Runnable() { // from class: android.telephony.-$$Lambda$PhoneStateListener$IPhoneStateListenerStub$eYTgM6ABgThWqEatVha4ZuIpI0A
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneStateListener.this.onImsCallDisconnectCauseChanged(r2);
                        }
                    });
                }
            });
        }
    }

    private void log(String s) {
        Rlog.m88d(LOG_TAG, s);
    }
}
