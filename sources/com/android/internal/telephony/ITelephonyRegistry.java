package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.CellInfo;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.ims.ImsReasonInfo;
import java.util.List;

public interface ITelephonyRegistry extends IInterface {
    void addOnOpportunisticSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException;

    void addOnSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException;

    @UnsupportedAppUsage
    void listen(String str, IPhoneStateListener iPhoneStateListener, int i, boolean z) throws RemoteException;

    void listenForSubscriber(int i, String str, IPhoneStateListener iPhoneStateListener, int i2, boolean z) throws RemoteException;

    void notifyActiveDataSubIdChanged(int i) throws RemoteException;

    void notifyCallForwardingChanged(boolean z) throws RemoteException;

    void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws RemoteException;

    void notifyCallQualityChanged(CallQuality callQuality, int i, int i2, int i3) throws RemoteException;

    @UnsupportedAppUsage
    void notifyCallState(int i, String str) throws RemoteException;

    void notifyCallStateForPhoneId(int i, int i2, int i3, String str) throws RemoteException;

    void notifyCarrierNetworkChange(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void notifyCellInfo(List<CellInfo> list) throws RemoteException;

    void notifyCellInfoForSubscriber(int i, List<CellInfo> list) throws RemoteException;

    void notifyCellLocation(Bundle bundle) throws RemoteException;

    void notifyCellLocationForSubscriber(int i, Bundle bundle) throws RemoteException;

    void notifyDataActivity(int i) throws RemoteException;

    void notifyDataActivityForSubscriber(int i, int i2) throws RemoteException;

    void notifyDataConnection(int i, boolean z, String str, String str2, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int i2, boolean z2) throws RemoteException;

    @UnsupportedAppUsage
    void notifyDataConnectionFailed(String str) throws RemoteException;

    void notifyDataConnectionFailedForSubscriber(int i, int i2, String str) throws RemoteException;

    void notifyDataConnectionForSubscriber(int i, int i2, int i3, boolean z, String str, String str2, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int i4, boolean z2) throws RemoteException;

    void notifyDisconnectCause(int i, int i2, int i3, int i4) throws RemoteException;

    void notifyEmergencyNumberList(int i, int i2) throws RemoteException;

    void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws RemoteException;

    void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws RemoteException;

    void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException;

    void notifyOtaspChanged(int i, int i2) throws RemoteException;

    void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException;

    void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> list) throws RemoteException;

    void notifyPhysicalChannelConfigurationForSubscriber(int i, List<PhysicalChannelConfig> list) throws RemoteException;

    void notifyPreciseCallState(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void notifyPreciseDataConnectionFailed(int i, int i2, String str, String str2, int i3) throws RemoteException;

    void notifyRadioPowerStateChanged(int i, int i2, int i3) throws RemoteException;

    void notifyServiceStateForPhoneId(int i, int i2, ServiceState serviceState) throws RemoteException;

    void notifySignalStrengthForPhoneId(int i, int i2, SignalStrength signalStrength) throws RemoteException;

    void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws RemoteException;

    void notifySrvccStateChanged(int i, int i2) throws RemoteException;

    void notifySubscriptionInfoChanged() throws RemoteException;

    void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws RemoteException;

    void removeOnSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException;

    public static class Default implements ITelephonyRegistry {
        public void addOnSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback) throws RemoteException {
        }

        public void addOnOpportunisticSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback) throws RemoteException {
        }

        public void removeOnSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback) throws RemoteException {
        }

        public void listen(String pkg, IPhoneStateListener callback, int events, boolean notifyNow) throws RemoteException {
        }

        public void listenForSubscriber(int subId, String pkg, IPhoneStateListener callback, int events, boolean notifyNow) throws RemoteException {
        }

        public void notifyCallState(int state, String incomingNumber) throws RemoteException {
        }

        public void notifyCallStateForPhoneId(int phoneId, int subId, int state, String incomingNumber) throws RemoteException {
        }

        public void notifyServiceStateForPhoneId(int phoneId, int subId, ServiceState state) throws RemoteException {
        }

        public void notifySignalStrengthForPhoneId(int phoneId, int subId, SignalStrength signalStrength) throws RemoteException {
        }

        public void notifyMessageWaitingChangedForPhoneId(int phoneId, int subId, boolean mwi) throws RemoteException {
        }

        public void notifyCallForwardingChanged(boolean cfi) throws RemoteException {
        }

        public void notifyCallForwardingChangedForSubscriber(int subId, boolean cfi) throws RemoteException {
        }

        public void notifyDataActivity(int state) throws RemoteException {
        }

        public void notifyDataActivityForSubscriber(int subId, int state) throws RemoteException {
        }

        public void notifyDataConnection(int state, boolean isDataConnectivityPossible, String apn, String apnType, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int networkType, boolean roaming) throws RemoteException {
        }

        public void notifyDataConnectionForSubscriber(int phoneId, int subId, int state, boolean isDataConnectivityPossible, String apn, String apnType, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int networkType, boolean roaming) throws RemoteException {
        }

        public void notifyDataConnectionFailed(String apnType) throws RemoteException {
        }

        public void notifyDataConnectionFailedForSubscriber(int phoneId, int subId, String apnType) throws RemoteException {
        }

        public void notifyCellLocation(Bundle cellLocation) throws RemoteException {
        }

        public void notifyCellLocationForSubscriber(int subId, Bundle cellLocation) throws RemoteException {
        }

        public void notifyOtaspChanged(int subId, int otaspMode) throws RemoteException {
        }

        public void notifyCellInfo(List<CellInfo> list) throws RemoteException {
        }

        public void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> list) throws RemoteException {
        }

        public void notifyPhysicalChannelConfigurationForSubscriber(int subId, List<PhysicalChannelConfig> list) throws RemoteException {
        }

        public void notifyPreciseCallState(int phoneId, int subId, int ringingCallState, int foregroundCallState, int backgroundCallState) throws RemoteException {
        }

        public void notifyDisconnectCause(int phoneId, int subId, int disconnectCause, int preciseDisconnectCause) throws RemoteException {
        }

        public void notifyPreciseDataConnectionFailed(int phoneId, int subId, String apnType, String apn, int failCause) throws RemoteException {
        }

        public void notifyCellInfoForSubscriber(int subId, List<CellInfo> list) throws RemoteException {
        }

        public void notifySrvccStateChanged(int subId, int lteState) throws RemoteException {
        }

        public void notifySimActivationStateChangedForPhoneId(int phoneId, int subId, int activationState, int activationType) throws RemoteException {
        }

        public void notifyOemHookRawEventForSubscriber(int phoneId, int subId, byte[] rawData) throws RemoteException {
        }

        public void notifySubscriptionInfoChanged() throws RemoteException {
        }

        public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
        }

        public void notifyCarrierNetworkChange(boolean active) throws RemoteException {
        }

        public void notifyUserMobileDataStateChangedForPhoneId(int phoneId, int subId, boolean state) throws RemoteException {
        }

        public void notifyPhoneCapabilityChanged(PhoneCapability capability) throws RemoteException {
        }

        public void notifyActiveDataSubIdChanged(int activeDataSubId) throws RemoteException {
        }

        public void notifyRadioPowerStateChanged(int phoneId, int subId, int state) throws RemoteException {
        }

        public void notifyEmergencyNumberList(int phoneId, int subId) throws RemoteException {
        }

        public void notifyCallQualityChanged(CallQuality callQuality, int phoneId, int subId, int callNetworkType) throws RemoteException {
        }

        public void notifyImsDisconnectCause(int subId, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITelephonyRegistry {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ITelephonyRegistry";
        static final int TRANSACTION_addOnOpportunisticSubscriptionsChangedListener = 2;
        static final int TRANSACTION_addOnSubscriptionsChangedListener = 1;
        static final int TRANSACTION_listen = 4;
        static final int TRANSACTION_listenForSubscriber = 5;
        static final int TRANSACTION_notifyActiveDataSubIdChanged = 37;
        static final int TRANSACTION_notifyCallForwardingChanged = 11;
        static final int TRANSACTION_notifyCallForwardingChangedForSubscriber = 12;
        static final int TRANSACTION_notifyCallQualityChanged = 40;
        static final int TRANSACTION_notifyCallState = 6;
        static final int TRANSACTION_notifyCallStateForPhoneId = 7;
        static final int TRANSACTION_notifyCarrierNetworkChange = 34;
        static final int TRANSACTION_notifyCellInfo = 22;
        static final int TRANSACTION_notifyCellInfoForSubscriber = 28;
        static final int TRANSACTION_notifyCellLocation = 19;
        static final int TRANSACTION_notifyCellLocationForSubscriber = 20;
        static final int TRANSACTION_notifyDataActivity = 13;
        static final int TRANSACTION_notifyDataActivityForSubscriber = 14;
        static final int TRANSACTION_notifyDataConnection = 15;
        static final int TRANSACTION_notifyDataConnectionFailed = 17;
        static final int TRANSACTION_notifyDataConnectionFailedForSubscriber = 18;
        static final int TRANSACTION_notifyDataConnectionForSubscriber = 16;
        static final int TRANSACTION_notifyDisconnectCause = 26;
        static final int TRANSACTION_notifyEmergencyNumberList = 39;
        static final int TRANSACTION_notifyImsDisconnectCause = 41;
        static final int TRANSACTION_notifyMessageWaitingChangedForPhoneId = 10;
        static final int TRANSACTION_notifyOemHookRawEventForSubscriber = 31;
        static final int TRANSACTION_notifyOpportunisticSubscriptionInfoChanged = 33;
        static final int TRANSACTION_notifyOtaspChanged = 21;
        static final int TRANSACTION_notifyPhoneCapabilityChanged = 36;
        static final int TRANSACTION_notifyPhysicalChannelConfiguration = 23;
        static final int TRANSACTION_notifyPhysicalChannelConfigurationForSubscriber = 24;
        static final int TRANSACTION_notifyPreciseCallState = 25;
        static final int TRANSACTION_notifyPreciseDataConnectionFailed = 27;
        static final int TRANSACTION_notifyRadioPowerStateChanged = 38;
        static final int TRANSACTION_notifyServiceStateForPhoneId = 8;
        static final int TRANSACTION_notifySignalStrengthForPhoneId = 9;
        static final int TRANSACTION_notifySimActivationStateChangedForPhoneId = 30;
        static final int TRANSACTION_notifySrvccStateChanged = 29;
        static final int TRANSACTION_notifySubscriptionInfoChanged = 32;
        static final int TRANSACTION_notifyUserMobileDataStateChangedForPhoneId = 35;
        static final int TRANSACTION_removeOnSubscriptionsChangedListener = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelephonyRegistry asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITelephonyRegistry)) {
                return new Proxy(obj);
            }
            return (ITelephonyRegistry) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addOnSubscriptionsChangedListener";
                case 2:
                    return "addOnOpportunisticSubscriptionsChangedListener";
                case 3:
                    return "removeOnSubscriptionsChangedListener";
                case 4:
                    return "listen";
                case 5:
                    return "listenForSubscriber";
                case 6:
                    return "notifyCallState";
                case 7:
                    return "notifyCallStateForPhoneId";
                case 8:
                    return "notifyServiceStateForPhoneId";
                case 9:
                    return "notifySignalStrengthForPhoneId";
                case 10:
                    return "notifyMessageWaitingChangedForPhoneId";
                case 11:
                    return "notifyCallForwardingChanged";
                case 12:
                    return "notifyCallForwardingChangedForSubscriber";
                case 13:
                    return "notifyDataActivity";
                case 14:
                    return "notifyDataActivityForSubscriber";
                case 15:
                    return "notifyDataConnection";
                case 16:
                    return "notifyDataConnectionForSubscriber";
                case 17:
                    return "notifyDataConnectionFailed";
                case 18:
                    return "notifyDataConnectionFailedForSubscriber";
                case 19:
                    return "notifyCellLocation";
                case 20:
                    return "notifyCellLocationForSubscriber";
                case 21:
                    return "notifyOtaspChanged";
                case 22:
                    return "notifyCellInfo";
                case 23:
                    return "notifyPhysicalChannelConfiguration";
                case 24:
                    return "notifyPhysicalChannelConfigurationForSubscriber";
                case 25:
                    return "notifyPreciseCallState";
                case 26:
                    return "notifyDisconnectCause";
                case 27:
                    return "notifyPreciseDataConnectionFailed";
                case 28:
                    return "notifyCellInfoForSubscriber";
                case 29:
                    return "notifySrvccStateChanged";
                case 30:
                    return "notifySimActivationStateChangedForPhoneId";
                case 31:
                    return "notifyOemHookRawEventForSubscriber";
                case 32:
                    return "notifySubscriptionInfoChanged";
                case 33:
                    return "notifyOpportunisticSubscriptionInfoChanged";
                case 34:
                    return "notifyCarrierNetworkChange";
                case 35:
                    return "notifyUserMobileDataStateChangedForPhoneId";
                case 36:
                    return "notifyPhoneCapabilityChanged";
                case 37:
                    return "notifyActiveDataSubIdChanged";
                case 38:
                    return "notifyRadioPowerStateChanged";
                case 39:
                    return "notifyEmergencyNumberList";
                case 40:
                    return "notifyCallQualityChanged";
                case 41:
                    return "notifyImsDisconnectCause";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: android.telephony.SignalStrength} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v64, resolved type: android.telephony.PhoneCapability} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v71, resolved type: android.telephony.CallQuality} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v75, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v26 */
        /* JADX WARNING: type inference failed for: r0v32 */
        /* JADX WARNING: type inference failed for: r0v80 */
        /* JADX WARNING: type inference failed for: r0v81 */
        /* JADX WARNING: type inference failed for: r0v82 */
        /* JADX WARNING: type inference failed for: r0v83 */
        /* JADX WARNING: type inference failed for: r0v84 */
        /* JADX WARNING: type inference failed for: r0v85 */
        /* JADX WARNING: type inference failed for: r0v86 */
        /* JADX WARNING: type inference failed for: r0v87 */
        /* JADX WARNING: type inference failed for: r0v88 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r23, android.os.Parcel r24, android.os.Parcel r25, int r26) throws android.os.RemoteException {
            /*
                r22 = this;
                r11 = r22
                r12 = r23
                r13 = r24
                java.lang.String r14 = "com.android.internal.telephony.ITelephonyRegistry"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r15 = 1
                if (r12 == r0) goto L_0x0467
                r0 = 0
                r1 = 0
                switch(r12) {
                    case 1: goto L_0x0451;
                    case 2: goto L_0x043b;
                    case 3: goto L_0x0425;
                    case 4: goto L_0x0403;
                    case 5: goto L_0x03d6;
                    case 6: goto L_0x03c4;
                    case 7: goto L_0x03aa;
                    case 8: goto L_0x0388;
                    case 9: goto L_0x0366;
                    case 10: goto L_0x034c;
                    case 11: goto L_0x0339;
                    case 12: goto L_0x0323;
                    case 13: goto L_0x0315;
                    case 14: goto L_0x0303;
                    case 15: goto L_0x02ad;
                    case 16: goto L_0x0249;
                    case 17: goto L_0x023b;
                    case 18: goto L_0x0225;
                    case 19: goto L_0x020b;
                    case 20: goto L_0x01ed;
                    case 21: goto L_0x01db;
                    case 22: goto L_0x01cb;
                    case 23: goto L_0x01bb;
                    case 24: goto L_0x01a7;
                    case 25: goto L_0x0182;
                    case 26: goto L_0x0168;
                    case 27: goto L_0x0143;
                    case 28: goto L_0x012f;
                    case 29: goto L_0x011d;
                    case 30: goto L_0x0103;
                    case 31: goto L_0x00ed;
                    case 32: goto L_0x00e3;
                    case 33: goto L_0x00d9;
                    case 34: goto L_0x00c6;
                    case 35: goto L_0x00ac;
                    case 36: goto L_0x0092;
                    case 37: goto L_0x0084;
                    case 38: goto L_0x006e;
                    case 39: goto L_0x005c;
                    case 40: goto L_0x0036;
                    case 41: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r0 = super.onTransact(r23, r24, r25, r26)
                return r0
            L_0x0018:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x002e
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r0 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.ims.ImsReasonInfo r0 = (android.telephony.ims.ImsReasonInfo) r0
                goto L_0x002f
            L_0x002e:
            L_0x002f:
                r11.notifyImsDisconnectCause(r1, r0)
                r25.writeNoException()
                return r15
            L_0x0036:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0048
                android.os.Parcelable$Creator<android.telephony.CallQuality> r0 = android.telephony.CallQuality.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.CallQuality r0 = (android.telephony.CallQuality) r0
                goto L_0x0049
            L_0x0048:
            L_0x0049:
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                r11.notifyCallQualityChanged(r0, r1, r2, r3)
                r25.writeNoException()
                return r15
            L_0x005c:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                r11.notifyEmergencyNumberList(r0, r1)
                r25.writeNoException()
                return r15
            L_0x006e:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                r11.notifyRadioPowerStateChanged(r0, r1, r2)
                r25.writeNoException()
                return r15
            L_0x0084:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                r11.notifyActiveDataSubIdChanged(r0)
                r25.writeNoException()
                return r15
            L_0x0092:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x00a4
                android.os.Parcelable$Creator<android.telephony.PhoneCapability> r0 = android.telephony.PhoneCapability.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.PhoneCapability r0 = (android.telephony.PhoneCapability) r0
                goto L_0x00a5
            L_0x00a4:
            L_0x00a5:
                r11.notifyPhoneCapabilityChanged(r0)
                r25.writeNoException()
                return r15
            L_0x00ac:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x00bf
                r1 = r15
            L_0x00bf:
                r11.notifyUserMobileDataStateChangedForPhoneId(r0, r2, r1)
                r25.writeNoException()
                return r15
            L_0x00c6:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x00d1
                r1 = r15
            L_0x00d1:
                r0 = r1
                r11.notifyCarrierNetworkChange(r0)
                r25.writeNoException()
                return r15
            L_0x00d9:
                r13.enforceInterface(r14)
                r22.notifyOpportunisticSubscriptionInfoChanged()
                r25.writeNoException()
                return r15
            L_0x00e3:
                r13.enforceInterface(r14)
                r22.notifySubscriptionInfoChanged()
                r25.writeNoException()
                return r15
            L_0x00ed:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                byte[] r2 = r24.createByteArray()
                r11.notifyOemHookRawEventForSubscriber(r0, r1, r2)
                r25.writeNoException()
                return r15
            L_0x0103:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                r11.notifySimActivationStateChangedForPhoneId(r0, r1, r2, r3)
                r25.writeNoException()
                return r15
            L_0x011d:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                r11.notifySrvccStateChanged(r0, r1)
                r25.writeNoException()
                return r15
            L_0x012f:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                android.os.Parcelable$Creator<android.telephony.CellInfo> r1 = android.telephony.CellInfo.CREATOR
                java.util.ArrayList r1 = r13.createTypedArrayList(r1)
                r11.notifyCellInfoForSubscriber(r0, r1)
                r25.writeNoException()
                return r15
            L_0x0143:
                r13.enforceInterface(r14)
                int r6 = r24.readInt()
                int r7 = r24.readInt()
                java.lang.String r8 = r24.readString()
                java.lang.String r9 = r24.readString()
                int r10 = r24.readInt()
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                r0.notifyPreciseDataConnectionFailed(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r15
            L_0x0168:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                r11.notifyDisconnectCause(r0, r1, r2, r3)
                r25.writeNoException()
                return r15
            L_0x0182:
                r13.enforceInterface(r14)
                int r6 = r24.readInt()
                int r7 = r24.readInt()
                int r8 = r24.readInt()
                int r9 = r24.readInt()
                int r10 = r24.readInt()
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                r0.notifyPreciseCallState(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r15
            L_0x01a7:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                android.os.Parcelable$Creator<android.telephony.PhysicalChannelConfig> r1 = android.telephony.PhysicalChannelConfig.CREATOR
                java.util.ArrayList r1 = r13.createTypedArrayList(r1)
                r11.notifyPhysicalChannelConfigurationForSubscriber(r0, r1)
                r25.writeNoException()
                return r15
            L_0x01bb:
                r13.enforceInterface(r14)
                android.os.Parcelable$Creator<android.telephony.PhysicalChannelConfig> r0 = android.telephony.PhysicalChannelConfig.CREATOR
                java.util.ArrayList r0 = r13.createTypedArrayList(r0)
                r11.notifyPhysicalChannelConfiguration(r0)
                r25.writeNoException()
                return r15
            L_0x01cb:
                r13.enforceInterface(r14)
                android.os.Parcelable$Creator<android.telephony.CellInfo> r0 = android.telephony.CellInfo.CREATOR
                java.util.ArrayList r0 = r13.createTypedArrayList(r0)
                r11.notifyCellInfo(r0)
                r25.writeNoException()
                return r15
            L_0x01db:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                r11.notifyOtaspChanged(r0, r1)
                r25.writeNoException()
                return r15
            L_0x01ed:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0203
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0204
            L_0x0203:
            L_0x0204:
                r11.notifyCellLocationForSubscriber(r1, r0)
                r25.writeNoException()
                return r15
            L_0x020b:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x021d
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x021e
            L_0x021d:
            L_0x021e:
                r11.notifyCellLocation(r0)
                r25.writeNoException()
                return r15
            L_0x0225:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                java.lang.String r2 = r24.readString()
                r11.notifyDataConnectionFailedForSubscriber(r0, r1, r2)
                r25.writeNoException()
                return r15
            L_0x023b:
                r13.enforceInterface(r14)
                java.lang.String r0 = r24.readString()
                r11.notifyDataConnectionFailed(r0)
                r25.writeNoException()
                return r15
            L_0x0249:
                r13.enforceInterface(r14)
                int r16 = r24.readInt()
                int r17 = r24.readInt()
                int r18 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0260
                r4 = r15
                goto L_0x0261
            L_0x0260:
                r4 = r1
            L_0x0261:
                java.lang.String r19 = r24.readString()
                java.lang.String r20 = r24.readString()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0279
                android.os.Parcelable$Creator<android.net.LinkProperties> r2 = android.net.LinkProperties.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.net.LinkProperties r2 = (android.net.LinkProperties) r2
                r7 = r2
                goto L_0x027a
            L_0x0279:
                r7 = r0
            L_0x027a:
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x028a
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r0 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.NetworkCapabilities r0 = (android.net.NetworkCapabilities) r0
            L_0x0288:
                r8 = r0
                goto L_0x028b
            L_0x028a:
                goto L_0x0288
            L_0x028b:
                int r21 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0297
                r10 = r15
                goto L_0x0298
            L_0x0297:
                r10 = r1
            L_0x0298:
                r0 = r22
                r1 = r16
                r2 = r17
                r3 = r18
                r5 = r19
                r6 = r20
                r9 = r21
                r0.notifyDataConnectionForSubscriber(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r25.writeNoException()
                return r15
            L_0x02ad:
                r13.enforceInterface(r14)
                int r9 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x02bc
                r2 = r15
                goto L_0x02bd
            L_0x02bc:
                r2 = r1
            L_0x02bd:
                java.lang.String r10 = r24.readString()
                java.lang.String r16 = r24.readString()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x02d5
                android.os.Parcelable$Creator<android.net.LinkProperties> r3 = android.net.LinkProperties.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.net.LinkProperties r3 = (android.net.LinkProperties) r3
                r5 = r3
                goto L_0x02d6
            L_0x02d5:
                r5 = r0
            L_0x02d6:
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x02e6
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r0 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.NetworkCapabilities r0 = (android.net.NetworkCapabilities) r0
            L_0x02e4:
                r6 = r0
                goto L_0x02e7
            L_0x02e6:
                goto L_0x02e4
            L_0x02e7:
                int r17 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x02f3
                r8 = r15
                goto L_0x02f4
            L_0x02f3:
                r8 = r1
            L_0x02f4:
                r0 = r22
                r1 = r9
                r3 = r10
                r4 = r16
                r7 = r17
                r0.notifyDataConnection(r1, r2, r3, r4, r5, r6, r7, r8)
                r25.writeNoException()
                return r15
            L_0x0303:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                r11.notifyDataActivityForSubscriber(r0, r1)
                r25.writeNoException()
                return r15
            L_0x0315:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                r11.notifyDataActivity(r0)
                r25.writeNoException()
                return r15
            L_0x0323:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0332
                r1 = r15
            L_0x0332:
                r11.notifyCallForwardingChangedForSubscriber(r0, r1)
                r25.writeNoException()
                return r15
            L_0x0339:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0344
                r1 = r15
            L_0x0344:
                r0 = r1
                r11.notifyCallForwardingChanged(r0)
                r25.writeNoException()
                return r15
            L_0x034c:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x035f
                r1 = r15
            L_0x035f:
                r11.notifyMessageWaitingChangedForPhoneId(r0, r2, r1)
                r25.writeNoException()
                return r15
            L_0x0366:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x0380
                android.os.Parcelable$Creator<android.telephony.SignalStrength> r0 = android.telephony.SignalStrength.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.SignalStrength r0 = (android.telephony.SignalStrength) r0
                goto L_0x0381
            L_0x0380:
            L_0x0381:
                r11.notifySignalStrengthForPhoneId(r1, r2, r0)
                r25.writeNoException()
                return r15
            L_0x0388:
                r13.enforceInterface(r14)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x03a2
                android.os.Parcelable$Creator<android.telephony.ServiceState> r0 = android.telephony.ServiceState.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.ServiceState r0 = (android.telephony.ServiceState) r0
                goto L_0x03a3
            L_0x03a2:
            L_0x03a3:
                r11.notifyServiceStateForPhoneId(r1, r2, r0)
                r25.writeNoException()
                return r15
            L_0x03aa:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                java.lang.String r3 = r24.readString()
                r11.notifyCallStateForPhoneId(r0, r1, r2, r3)
                r25.writeNoException()
                return r15
            L_0x03c4:
                r13.enforceInterface(r14)
                int r0 = r24.readInt()
                java.lang.String r1 = r24.readString()
                r11.notifyCallState(r0, r1)
                r25.writeNoException()
                return r15
            L_0x03d6:
                r13.enforceInterface(r14)
                int r6 = r24.readInt()
                java.lang.String r7 = r24.readString()
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.telephony.IPhoneStateListener r8 = com.android.internal.telephony.IPhoneStateListener.Stub.asInterface(r0)
                int r9 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x03f5
                r5 = r15
                goto L_0x03f6
            L_0x03f5:
                r5 = r1
            L_0x03f6:
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r0.listenForSubscriber(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r15
            L_0x0403:
                r13.enforceInterface(r14)
                java.lang.String r0 = r24.readString()
                android.os.IBinder r2 = r24.readStrongBinder()
                com.android.internal.telephony.IPhoneStateListener r2 = com.android.internal.telephony.IPhoneStateListener.Stub.asInterface(r2)
                int r3 = r24.readInt()
                int r4 = r24.readInt()
                if (r4 == 0) goto L_0x041e
                r1 = r15
            L_0x041e:
                r11.listen(r0, r2, r3, r1)
                r25.writeNoException()
                return r15
            L_0x0425:
                r13.enforceInterface(r14)
                java.lang.String r0 = r24.readString()
                android.os.IBinder r1 = r24.readStrongBinder()
                com.android.internal.telephony.IOnSubscriptionsChangedListener r1 = com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub.asInterface(r1)
                r11.removeOnSubscriptionsChangedListener(r0, r1)
                r25.writeNoException()
                return r15
            L_0x043b:
                r13.enforceInterface(r14)
                java.lang.String r0 = r24.readString()
                android.os.IBinder r1 = r24.readStrongBinder()
                com.android.internal.telephony.IOnSubscriptionsChangedListener r1 = com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub.asInterface(r1)
                r11.addOnOpportunisticSubscriptionsChangedListener(r0, r1)
                r25.writeNoException()
                return r15
            L_0x0451:
                r13.enforceInterface(r14)
                java.lang.String r0 = r24.readString()
                android.os.IBinder r1 = r24.readStrongBinder()
                com.android.internal.telephony.IOnSubscriptionsChangedListener r1 = com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub.asInterface(r1)
                r11.addOnSubscriptionsChangedListener(r0, r1)
                r25.writeNoException()
                return r15
            L_0x0467:
                r0 = r25
                r0.writeString(r14)
                return r15
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.ITelephonyRegistry.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITelephonyRegistry {
            public static ITelephonyRegistry sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void addOnSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnSubscriptionsChangedListener(pkg, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addOnOpportunisticSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnOpportunisticSubscriptionsChangedListener(pkg, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnSubscriptionsChangedListener(String pkg, IOnSubscriptionsChangedListener callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeOnSubscriptionsChangedListener(pkg, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void listen(String pkg, IPhoneStateListener callback, int events, boolean notifyNow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(events);
                    _data.writeInt(notifyNow);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().listen(pkg, callback, events, notifyNow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void listenForSubscriber(int subId, String pkg, IPhoneStateListener callback, int events, boolean notifyNow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(pkg);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(events);
                    _data.writeInt(notifyNow);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().listenForSubscriber(subId, pkg, callback, events, notifyNow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCallState(int state, String incomingNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeString(incomingNumber);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCallState(state, incomingNumber);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCallStateForPhoneId(int phoneId, int subId, int state, String incomingNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(state);
                    _data.writeString(incomingNumber);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCallStateForPhoneId(phoneId, subId, state, incomingNumber);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyServiceStateForPhoneId(int phoneId, int subId, ServiceState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyServiceStateForPhoneId(phoneId, subId, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifySignalStrengthForPhoneId(int phoneId, int subId, SignalStrength signalStrength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    if (signalStrength != null) {
                        _data.writeInt(1);
                        signalStrength.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifySignalStrengthForPhoneId(phoneId, subId, signalStrength);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyMessageWaitingChangedForPhoneId(int phoneId, int subId, boolean mwi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(mwi);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyMessageWaitingChangedForPhoneId(phoneId, subId, mwi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCallForwardingChanged(boolean cfi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cfi);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCallForwardingChanged(cfi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCallForwardingChangedForSubscriber(int subId, boolean cfi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(cfi);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCallForwardingChangedForSubscriber(subId, cfi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyDataActivity(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyDataActivity(state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyDataActivityForSubscriber(int subId, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(state);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyDataActivityForSubscriber(subId, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyDataConnection(int state, boolean isDataConnectivityPossible, String apn, String apnType, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int networkType, boolean roaming) throws RemoteException {
                LinkProperties linkProperties2 = linkProperties;
                NetworkCapabilities networkCapabilities2 = networkCapabilities;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(state);
                        try {
                            _data.writeInt(isDataConnectivityPossible ? 1 : 0);
                            _data.writeString(apn);
                            _data.writeString(apnType);
                            if (linkProperties2 != null) {
                                _data.writeInt(1);
                                linkProperties2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (networkCapabilities2 != null) {
                                _data.writeInt(1);
                                networkCapabilities2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(networkType);
                            _data.writeInt(roaming ? 1 : 0);
                            if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().notifyDataConnection(state, isDataConnectivityPossible, apn, apnType, linkProperties, networkCapabilities, networkType, roaming);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        boolean z = isDataConnectivityPossible;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = state;
                    boolean z2 = isDataConnectivityPossible;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void notifyDataConnectionForSubscriber(int phoneId, int subId, int state, boolean isDataConnectivityPossible, String apn, String apnType, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int networkType, boolean roaming) throws RemoteException {
                LinkProperties linkProperties2 = linkProperties;
                NetworkCapabilities networkCapabilities2 = networkCapabilities;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(state);
                    _data.writeInt(isDataConnectivityPossible ? 1 : 0);
                    _data.writeString(apn);
                    _data.writeString(apnType);
                    if (linkProperties2 != null) {
                        _data.writeInt(1);
                        linkProperties2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (networkCapabilities2 != null) {
                        _data.writeInt(1);
                        networkCapabilities2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(networkType);
                    _data.writeInt(roaming ? 1 : 0);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyDataConnectionForSubscriber(phoneId, subId, state, isDataConnectivityPossible, apn, apnType, linkProperties, networkCapabilities, networkType, roaming);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyDataConnectionFailed(String apnType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(apnType);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyDataConnectionFailed(apnType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyDataConnectionFailedForSubscriber(int phoneId, int subId, String apnType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeString(apnType);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyDataConnectionFailedForSubscriber(phoneId, subId, apnType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCellLocation(Bundle cellLocation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cellLocation != null) {
                        _data.writeInt(1);
                        cellLocation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCellLocation(cellLocation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCellLocationForSubscriber(int subId, Bundle cellLocation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (cellLocation != null) {
                        _data.writeInt(1);
                        cellLocation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCellLocationForSubscriber(subId, cellLocation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyOtaspChanged(int subId, int otaspMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(otaspMode);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyOtaspChanged(subId, otaspMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCellInfo(List<CellInfo> cellInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(cellInfo);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCellInfo(cellInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> configs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(configs);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPhysicalChannelConfiguration(configs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPhysicalChannelConfigurationForSubscriber(int subId, List<PhysicalChannelConfig> configs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeTypedList(configs);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPhysicalChannelConfigurationForSubscriber(subId, configs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPreciseCallState(int phoneId, int subId, int ringingCallState, int foregroundCallState, int backgroundCallState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(ringingCallState);
                    _data.writeInt(foregroundCallState);
                    _data.writeInt(backgroundCallState);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPreciseCallState(phoneId, subId, ringingCallState, foregroundCallState, backgroundCallState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyDisconnectCause(int phoneId, int subId, int disconnectCause, int preciseDisconnectCause) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(disconnectCause);
                    _data.writeInt(preciseDisconnectCause);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyDisconnectCause(phoneId, subId, disconnectCause, preciseDisconnectCause);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPreciseDataConnectionFailed(int phoneId, int subId, String apnType, String apn, int failCause) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeString(apnType);
                    _data.writeString(apn);
                    _data.writeInt(failCause);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPreciseDataConnectionFailed(phoneId, subId, apnType, apn, failCause);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCellInfoForSubscriber(int subId, List<CellInfo> cellInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeTypedList(cellInfo);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCellInfoForSubscriber(subId, cellInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifySrvccStateChanged(int subId, int lteState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(lteState);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifySrvccStateChanged(subId, lteState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifySimActivationStateChangedForPhoneId(int phoneId, int subId, int activationState, int activationType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(activationState);
                    _data.writeInt(activationType);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifySimActivationStateChangedForPhoneId(phoneId, subId, activationState, activationType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyOemHookRawEventForSubscriber(int phoneId, int subId, byte[] rawData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeByteArray(rawData);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyOemHookRawEventForSubscriber(phoneId, subId, rawData);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifySubscriptionInfoChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifySubscriptionInfoChanged();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyOpportunisticSubscriptionInfoChanged();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCarrierNetworkChange(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCarrierNetworkChange(active);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyUserMobileDataStateChangedForPhoneId(int phoneId, int subId, boolean state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(state);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyUserMobileDataStateChangedForPhoneId(phoneId, subId, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyPhoneCapabilityChanged(PhoneCapability capability) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (capability != null) {
                        _data.writeInt(1);
                        capability.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyPhoneCapabilityChanged(capability);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyActiveDataSubIdChanged(int activeDataSubId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(activeDataSubId);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyActiveDataSubIdChanged(activeDataSubId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyRadioPowerStateChanged(int phoneId, int subId, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(state);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyRadioPowerStateChanged(phoneId, subId, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyEmergencyNumberList(int phoneId, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyEmergencyNumberList(phoneId, subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyCallQualityChanged(CallQuality callQuality, int phoneId, int subId, int callNetworkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callQuality != null) {
                        _data.writeInt(1);
                        callQuality.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(phoneId);
                    _data.writeInt(subId);
                    _data.writeInt(callNetworkType);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyCallQualityChanged(callQuality, phoneId, subId, callNetworkType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyImsDisconnectCause(int subId, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (imsReasonInfo != null) {
                        _data.writeInt(1);
                        imsReasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyImsDisconnectCause(subId, imsReasonInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITelephonyRegistry impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITelephonyRegistry getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
