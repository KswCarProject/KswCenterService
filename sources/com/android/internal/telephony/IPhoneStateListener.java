package com.android.internal.telephony;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.CallAttributes;
import android.telephony.CellInfo;
import android.telephony.DataConnectionRealTimeInfo;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.PreciseCallState;
import android.telephony.PreciseDataConnectionState;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.ims.ImsReasonInfo;
import java.util.List;
import java.util.Map;

public interface IPhoneStateListener extends IInterface {
    void onActiveDataSubIdChanged(int i) throws RemoteException;

    void onCallAttributesChanged(CallAttributes callAttributes) throws RemoteException;

    void onCallDisconnectCauseChanged(int i, int i2) throws RemoteException;

    void onCallForwardingIndicatorChanged(boolean z) throws RemoteException;

    void onCallStateChanged(int i, String str) throws RemoteException;

    void onCarrierNetworkChange(boolean z) throws RemoteException;

    void onCellInfoChanged(List<CellInfo> list) throws RemoteException;

    void onCellLocationChanged(Bundle bundle) throws RemoteException;

    void onDataActivationStateChanged(int i) throws RemoteException;

    void onDataActivity(int i) throws RemoteException;

    void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws RemoteException;

    void onDataConnectionStateChanged(int i, int i2) throws RemoteException;

    void onEmergencyNumberListChanged(Map map) throws RemoteException;

    void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void onMessageWaitingIndicatorChanged(boolean z) throws RemoteException;

    void onOemHookRawEvent(byte[] bArr) throws RemoteException;

    void onOtaspChanged(int i) throws RemoteException;

    void onPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException;

    void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) throws RemoteException;

    void onPreciseCallStateChanged(PreciseCallState preciseCallState) throws RemoteException;

    void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) throws RemoteException;

    void onRadioPowerStateChanged(int i) throws RemoteException;

    void onServiceStateChanged(ServiceState serviceState) throws RemoteException;

    void onSignalStrengthChanged(int i) throws RemoteException;

    void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException;

    void onSrvccStateChanged(int i) throws RemoteException;

    void onUserMobileDataStateChanged(boolean z) throws RemoteException;

    void onVoiceActivationStateChanged(int i) throws RemoteException;

    public static class Default implements IPhoneStateListener {
        public void onServiceStateChanged(ServiceState serviceState) throws RemoteException {
        }

        public void onSignalStrengthChanged(int asu) throws RemoteException {
        }

        public void onMessageWaitingIndicatorChanged(boolean mwi) throws RemoteException {
        }

        public void onCallForwardingIndicatorChanged(boolean cfi) throws RemoteException {
        }

        public void onCellLocationChanged(Bundle location) throws RemoteException {
        }

        public void onCallStateChanged(int state, String incomingNumber) throws RemoteException {
        }

        public void onDataConnectionStateChanged(int state, int networkType) throws RemoteException {
        }

        public void onDataActivity(int direction) throws RemoteException {
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException {
        }

        public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) throws RemoteException {
        }

        public void onOtaspChanged(int otaspMode) throws RemoteException {
        }

        public void onCellInfoChanged(List<CellInfo> list) throws RemoteException {
        }

        public void onPreciseCallStateChanged(PreciseCallState callState) throws RemoteException {
        }

        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState dataConnectionState) throws RemoteException {
        }

        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dcRtInfo) throws RemoteException {
        }

        public void onSrvccStateChanged(int state) throws RemoteException {
        }

        public void onVoiceActivationStateChanged(int activationState) throws RemoteException {
        }

        public void onDataActivationStateChanged(int activationState) throws RemoteException {
        }

        public void onOemHookRawEvent(byte[] rawData) throws RemoteException {
        }

        public void onCarrierNetworkChange(boolean active) throws RemoteException {
        }

        public void onUserMobileDataStateChanged(boolean enabled) throws RemoteException {
        }

        public void onPhoneCapabilityChanged(PhoneCapability capability) throws RemoteException {
        }

        public void onActiveDataSubIdChanged(int subId) throws RemoteException {
        }

        public void onRadioPowerStateChanged(int state) throws RemoteException {
        }

        public void onCallAttributesChanged(CallAttributes callAttributes) throws RemoteException {
        }

        public void onEmergencyNumberListChanged(Map emergencyNumberList) throws RemoteException {
        }

        public void onCallDisconnectCauseChanged(int disconnectCause, int preciseDisconnectCause) throws RemoteException {
        }

        public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPhoneStateListener {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IPhoneStateListener";
        static final int TRANSACTION_onActiveDataSubIdChanged = 23;
        static final int TRANSACTION_onCallAttributesChanged = 25;
        static final int TRANSACTION_onCallDisconnectCauseChanged = 27;
        static final int TRANSACTION_onCallForwardingIndicatorChanged = 4;
        static final int TRANSACTION_onCallStateChanged = 6;
        static final int TRANSACTION_onCarrierNetworkChange = 20;
        static final int TRANSACTION_onCellInfoChanged = 12;
        static final int TRANSACTION_onCellLocationChanged = 5;
        static final int TRANSACTION_onDataActivationStateChanged = 18;
        static final int TRANSACTION_onDataActivity = 8;
        static final int TRANSACTION_onDataConnectionRealTimeInfoChanged = 15;
        static final int TRANSACTION_onDataConnectionStateChanged = 7;
        static final int TRANSACTION_onEmergencyNumberListChanged = 26;
        static final int TRANSACTION_onImsCallDisconnectCauseChanged = 28;
        static final int TRANSACTION_onMessageWaitingIndicatorChanged = 3;
        static final int TRANSACTION_onOemHookRawEvent = 19;
        static final int TRANSACTION_onOtaspChanged = 11;
        static final int TRANSACTION_onPhoneCapabilityChanged = 22;
        static final int TRANSACTION_onPhysicalChannelConfigurationChanged = 10;
        static final int TRANSACTION_onPreciseCallStateChanged = 13;
        static final int TRANSACTION_onPreciseDataConnectionStateChanged = 14;
        static final int TRANSACTION_onRadioPowerStateChanged = 24;
        static final int TRANSACTION_onServiceStateChanged = 1;
        static final int TRANSACTION_onSignalStrengthChanged = 2;
        static final int TRANSACTION_onSignalStrengthsChanged = 9;
        static final int TRANSACTION_onSrvccStateChanged = 16;
        static final int TRANSACTION_onUserMobileDataStateChanged = 21;
        static final int TRANSACTION_onVoiceActivationStateChanged = 17;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPhoneStateListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPhoneStateListener)) {
                return new Proxy(obj);
            }
            return (IPhoneStateListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onServiceStateChanged";
                case 2:
                    return "onSignalStrengthChanged";
                case 3:
                    return "onMessageWaitingIndicatorChanged";
                case 4:
                    return "onCallForwardingIndicatorChanged";
                case 5:
                    return "onCellLocationChanged";
                case 6:
                    return "onCallStateChanged";
                case 7:
                    return "onDataConnectionStateChanged";
                case 8:
                    return "onDataActivity";
                case 9:
                    return "onSignalStrengthsChanged";
                case 10:
                    return "onPhysicalChannelConfigurationChanged";
                case 11:
                    return "onOtaspChanged";
                case 12:
                    return "onCellInfoChanged";
                case 13:
                    return "onPreciseCallStateChanged";
                case 14:
                    return "onPreciseDataConnectionStateChanged";
                case 15:
                    return "onDataConnectionRealTimeInfoChanged";
                case 16:
                    return "onSrvccStateChanged";
                case 17:
                    return "onVoiceActivationStateChanged";
                case 18:
                    return "onDataActivationStateChanged";
                case 19:
                    return "onOemHookRawEvent";
                case 20:
                    return "onCarrierNetworkChange";
                case 21:
                    return "onUserMobileDataStateChanged";
                case 22:
                    return "onPhoneCapabilityChanged";
                case 23:
                    return "onActiveDataSubIdChanged";
                case 24:
                    return "onRadioPowerStateChanged";
                case 25:
                    return "onCallAttributesChanged";
                case 26:
                    return "onEmergencyNumberListChanged";
                case 27:
                    return "onCallDisconnectCauseChanged";
                case 28:
                    return "onImsCallDisconnectCauseChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.telephony.SignalStrength} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: android.telephony.PreciseCallState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v32, resolved type: android.telephony.PreciseDataConnectionState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v36, resolved type: android.telephony.DataConnectionRealTimeInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v48, resolved type: android.telephony.PhoneCapability} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v54, resolved type: android.telephony.CallAttributes} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v33, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v61, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v36, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v37, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v38, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v39, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v40, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v41, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v42, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v43, resolved type: android.telephony.ServiceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v44, resolved type: android.telephony.ServiceState} */
        /* JADX WARNING: type inference failed for: r3v8, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r3v13, types: [android.telephony.SignalStrength] */
        /* JADX WARNING: type inference failed for: r3v16, types: [android.telephony.PreciseCallState] */
        /* JADX WARNING: type inference failed for: r3v19, types: [android.telephony.PreciseDataConnectionState] */
        /* JADX WARNING: type inference failed for: r3v22, types: [android.telephony.DataConnectionRealTimeInfo] */
        /* JADX WARNING: type inference failed for: r3v27, types: [android.telephony.PhoneCapability] */
        /* JADX WARNING: type inference failed for: r3v30, types: [android.telephony.CallAttributes] */
        /* JADX WARNING: type inference failed for: r3v35, types: [android.telephony.ims.ImsReasonInfo] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "com.android.internal.telephony.IPhoneStateListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x01ec
                r1 = 0
                r3 = 0
                switch(r5) {
                    case 1: goto L_0x01d3;
                    case 2: goto L_0x01c8;
                    case 3: goto L_0x01b9;
                    case 4: goto L_0x01aa;
                    case 5: goto L_0x0191;
                    case 6: goto L_0x0182;
                    case 7: goto L_0x0173;
                    case 8: goto L_0x0168;
                    case 9: goto L_0x014f;
                    case 10: goto L_0x0142;
                    case 11: goto L_0x0137;
                    case 12: goto L_0x012a;
                    case 13: goto L_0x0111;
                    case 14: goto L_0x00f8;
                    case 15: goto L_0x00df;
                    case 16: goto L_0x00d4;
                    case 17: goto L_0x00c9;
                    case 18: goto L_0x00be;
                    case 19: goto L_0x00b3;
                    case 20: goto L_0x00a4;
                    case 21: goto L_0x0095;
                    case 22: goto L_0x007c;
                    case 23: goto L_0x0071;
                    case 24: goto L_0x0066;
                    case 25: goto L_0x004d;
                    case 26: goto L_0x003a;
                    case 27: goto L_0x002b;
                    case 28: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0012:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0025
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.ims.ImsReasonInfo r3 = (android.telephony.ims.ImsReasonInfo) r3
                goto L_0x0026
            L_0x0025:
            L_0x0026:
                r1 = r3
                r4.onImsCallDisconnectCauseChanged(r1)
                return r2
            L_0x002b:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                int r3 = r6.readInt()
                r4.onCallDisconnectCauseChanged(r1, r3)
                return r2
            L_0x003a:
                r6.enforceInterface(r0)
                java.lang.Class r1 = r4.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.HashMap r3 = r6.readHashMap(r1)
                r4.onEmergencyNumberListChanged(r3)
                return r2
            L_0x004d:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0060
                android.os.Parcelable$Creator<android.telephony.CallAttributes> r1 = android.telephony.CallAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.CallAttributes r3 = (android.telephony.CallAttributes) r3
                goto L_0x0061
            L_0x0060:
            L_0x0061:
                r1 = r3
                r4.onCallAttributesChanged(r1)
                return r2
            L_0x0066:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onRadioPowerStateChanged(r1)
                return r2
            L_0x0071:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onActiveDataSubIdChanged(r1)
                return r2
            L_0x007c:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x008f
                android.os.Parcelable$Creator<android.telephony.PhoneCapability> r1 = android.telephony.PhoneCapability.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.PhoneCapability r3 = (android.telephony.PhoneCapability) r3
                goto L_0x0090
            L_0x008f:
            L_0x0090:
                r1 = r3
                r4.onPhoneCapabilityChanged(r1)
                return r2
            L_0x0095:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x00a0
                r1 = r2
            L_0x00a0:
                r4.onUserMobileDataStateChanged(r1)
                return r2
            L_0x00a4:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x00af
                r1 = r2
            L_0x00af:
                r4.onCarrierNetworkChange(r1)
                return r2
            L_0x00b3:
                r6.enforceInterface(r0)
                byte[] r1 = r6.createByteArray()
                r4.onOemHookRawEvent(r1)
                return r2
            L_0x00be:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onDataActivationStateChanged(r1)
                return r2
            L_0x00c9:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onVoiceActivationStateChanged(r1)
                return r2
            L_0x00d4:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onSrvccStateChanged(r1)
                return r2
            L_0x00df:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00f2
                android.os.Parcelable$Creator<android.telephony.DataConnectionRealTimeInfo> r1 = android.telephony.DataConnectionRealTimeInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.DataConnectionRealTimeInfo r3 = (android.telephony.DataConnectionRealTimeInfo) r3
                goto L_0x00f3
            L_0x00f2:
            L_0x00f3:
                r1 = r3
                r4.onDataConnectionRealTimeInfoChanged(r1)
                return r2
            L_0x00f8:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x010b
                android.os.Parcelable$Creator<android.telephony.PreciseDataConnectionState> r1 = android.telephony.PreciseDataConnectionState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.PreciseDataConnectionState r3 = (android.telephony.PreciseDataConnectionState) r3
                goto L_0x010c
            L_0x010b:
            L_0x010c:
                r1 = r3
                r4.onPreciseDataConnectionStateChanged(r1)
                return r2
            L_0x0111:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0124
                android.os.Parcelable$Creator<android.telephony.PreciseCallState> r1 = android.telephony.PreciseCallState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.PreciseCallState r3 = (android.telephony.PreciseCallState) r3
                goto L_0x0125
            L_0x0124:
            L_0x0125:
                r1 = r3
                r4.onPreciseCallStateChanged(r1)
                return r2
            L_0x012a:
                r6.enforceInterface(r0)
                android.os.Parcelable$Creator<android.telephony.CellInfo> r1 = android.telephony.CellInfo.CREATOR
                java.util.ArrayList r1 = r6.createTypedArrayList(r1)
                r4.onCellInfoChanged(r1)
                return r2
            L_0x0137:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onOtaspChanged(r1)
                return r2
            L_0x0142:
                r6.enforceInterface(r0)
                android.os.Parcelable$Creator<android.telephony.PhysicalChannelConfig> r1 = android.telephony.PhysicalChannelConfig.CREATOR
                java.util.ArrayList r1 = r6.createTypedArrayList(r1)
                r4.onPhysicalChannelConfigurationChanged(r1)
                return r2
            L_0x014f:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0162
                android.os.Parcelable$Creator<android.telephony.SignalStrength> r1 = android.telephony.SignalStrength.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.SignalStrength r3 = (android.telephony.SignalStrength) r3
                goto L_0x0163
            L_0x0162:
            L_0x0163:
                r1 = r3
                r4.onSignalStrengthsChanged(r1)
                return r2
            L_0x0168:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onDataActivity(r1)
                return r2
            L_0x0173:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                int r3 = r6.readInt()
                r4.onDataConnectionStateChanged(r1, r3)
                return r2
            L_0x0182:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                java.lang.String r3 = r6.readString()
                r4.onCallStateChanged(r1, r3)
                return r2
            L_0x0191:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x01a4
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x01a5
            L_0x01a4:
            L_0x01a5:
                r1 = r3
                r4.onCellLocationChanged(r1)
                return r2
            L_0x01aa:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x01b5
                r1 = r2
            L_0x01b5:
                r4.onCallForwardingIndicatorChanged(r1)
                return r2
            L_0x01b9:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x01c4
                r1 = r2
            L_0x01c4:
                r4.onMessageWaitingIndicatorChanged(r1)
                return r2
            L_0x01c8:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onSignalStrengthChanged(r1)
                return r2
            L_0x01d3:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x01e6
                android.os.Parcelable$Creator<android.telephony.ServiceState> r1 = android.telephony.ServiceState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                r3 = r1
                android.telephony.ServiceState r3 = (android.telephony.ServiceState) r3
                goto L_0x01e7
            L_0x01e6:
            L_0x01e7:
                r1 = r3
                r4.onServiceStateChanged(r1)
                return r2
            L_0x01ec:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.IPhoneStateListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPhoneStateListener {
            public static IPhoneStateListener sDefaultImpl;
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

            public void onServiceStateChanged(ServiceState serviceState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (serviceState != null) {
                        _data.writeInt(1);
                        serviceState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onServiceStateChanged(serviceState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSignalStrengthChanged(int asu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(asu);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSignalStrengthChanged(asu);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onMessageWaitingIndicatorChanged(boolean mwi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mwi);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMessageWaitingIndicatorChanged(mwi);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCallForwardingIndicatorChanged(boolean cfi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cfi);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCallForwardingIndicatorChanged(cfi);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCellLocationChanged(Bundle location) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCellLocationChanged(location);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCallStateChanged(int state, String incomingNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeString(incomingNumber);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCallStateChanged(state, incomingNumber);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDataConnectionStateChanged(int state, int networkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(networkType);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDataConnectionStateChanged(state, networkType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDataActivity(int direction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDataActivity(direction);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (signalStrength != null) {
                        _data.writeInt(1);
                        signalStrength.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSignalStrengthsChanged(signalStrength);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> configs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(configs);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPhysicalChannelConfigurationChanged(configs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onOtaspChanged(int otaspMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(otaspMode);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onOtaspChanged(otaspMode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCellInfoChanged(List<CellInfo> cellInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(cellInfo);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCellInfoChanged(cellInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPreciseCallStateChanged(PreciseCallState callState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callState != null) {
                        _data.writeInt(1);
                        callState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPreciseCallStateChanged(callState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState dataConnectionState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dataConnectionState != null) {
                        _data.writeInt(1);
                        dataConnectionState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPreciseDataConnectionStateChanged(dataConnectionState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dcRtInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dcRtInfo != null) {
                        _data.writeInt(1);
                        dcRtInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDataConnectionRealTimeInfoChanged(dcRtInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSrvccStateChanged(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSrvccStateChanged(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVoiceActivationStateChanged(int activationState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(activationState);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVoiceActivationStateChanged(activationState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDataActivationStateChanged(int activationState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(activationState);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDataActivationStateChanged(activationState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onOemHookRawEvent(byte[] rawData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(rawData);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onOemHookRawEvent(rawData);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCarrierNetworkChange(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCarrierNetworkChange(active);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onUserMobileDataStateChanged(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUserMobileDataStateChanged(enabled);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPhoneCapabilityChanged(PhoneCapability capability) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (capability != null) {
                        _data.writeInt(1);
                        capability.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPhoneCapabilityChanged(capability);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActiveDataSubIdChanged(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActiveDataSubIdChanged(subId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRadioPowerStateChanged(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRadioPowerStateChanged(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCallAttributesChanged(CallAttributes callAttributes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callAttributes != null) {
                        _data.writeInt(1);
                        callAttributes.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCallAttributesChanged(callAttributes);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onEmergencyNumberListChanged(Map emergencyNumberList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(emergencyNumberList);
                    if (this.mRemote.transact(26, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onEmergencyNumberListChanged(emergencyNumberList);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCallDisconnectCauseChanged(int disconnectCause, int preciseDisconnectCause) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(disconnectCause);
                    _data.writeInt(preciseDisconnectCause);
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCallDisconnectCauseChanged(disconnectCause, preciseDisconnectCause);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        _data.writeInt(1);
                        imsReasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(28, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onImsCallDisconnectCauseChanged(imsReasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPhoneStateListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPhoneStateListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
