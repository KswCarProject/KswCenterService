package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;

public interface IImsCallSessionListener extends IInterface {
    void callQualityChanged(CallQuality callQuality) throws RemoteException;

    void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionHandover(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionHandoverFailed(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMayHandover(IImsCallSession iImsCallSession, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException;

    void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRttMessageReceived(String str) throws RemoteException;

    void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttModifyResponseReceived(int i) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int i) throws RemoteException;

    void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    @UnsupportedAppUsage
    void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int i, String str) throws RemoteException;

    public static class Default implements IImsCallSessionListener {
        public void callSessionProgressing(IImsCallSession session, ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void callSessionStarted(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionStartFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionTerminated(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionHeld(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionHoldFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionHoldReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionResumed(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionResumeFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionResumeReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionMergeStarted(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionMergeComplete(IImsCallSession session) throws RemoteException {
        }

        public void callSessionMergeFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionUpdated(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionUpdateFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionUpdateReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionConferenceExtended(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionConferenceExtendFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionConferenceExtendReceived(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionInviteParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
        }

        public void callSessionInviteParticipantsRequestFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
        }

        public void callSessionRemoveParticipantsRequestFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionConferenceStateUpdated(IImsCallSession session, ImsConferenceState state) throws RemoteException {
        }

        public void callSessionUssdMessageReceived(IImsCallSession session, int mode, String ussdMessage) throws RemoteException {
        }

        public void callSessionHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionHandoverFailed(IImsCallSession session, int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionMayHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech) throws RemoteException {
        }

        public void callSessionTtyModeReceived(IImsCallSession session, int mode) throws RemoteException {
        }

        public void callSessionMultipartyStateChanged(IImsCallSession session, boolean isMultiParty) throws RemoteException {
        }

        public void callSessionSuppServiceReceived(IImsCallSession session, ImsSuppServiceNotification suppSrvNotification) throws RemoteException {
        }

        public void callSessionRttModifyRequestReceived(IImsCallSession session, ImsCallProfile callProfile) throws RemoteException {
        }

        public void callSessionRttModifyResponseReceived(int status) throws RemoteException {
        }

        public void callSessionRttMessageReceived(String rttMessage) throws RemoteException {
        }

        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsCallSessionListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsCallSessionListener";
        static final int TRANSACTION_callQualityChanged = 36;
        static final int TRANSACTION_callSessionConferenceExtendFailed = 18;
        static final int TRANSACTION_callSessionConferenceExtendReceived = 19;
        static final int TRANSACTION_callSessionConferenceExtended = 17;
        static final int TRANSACTION_callSessionConferenceStateUpdated = 24;
        static final int TRANSACTION_callSessionHandover = 26;
        static final int TRANSACTION_callSessionHandoverFailed = 27;
        static final int TRANSACTION_callSessionHeld = 5;
        static final int TRANSACTION_callSessionHoldFailed = 6;
        static final int TRANSACTION_callSessionHoldReceived = 7;
        static final int TRANSACTION_callSessionInviteParticipantsRequestDelivered = 20;
        static final int TRANSACTION_callSessionInviteParticipantsRequestFailed = 21;
        static final int TRANSACTION_callSessionMayHandover = 28;
        static final int TRANSACTION_callSessionMergeComplete = 12;
        static final int TRANSACTION_callSessionMergeFailed = 13;
        static final int TRANSACTION_callSessionMergeStarted = 11;
        static final int TRANSACTION_callSessionMultipartyStateChanged = 30;
        static final int TRANSACTION_callSessionProgressing = 1;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestDelivered = 22;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestFailed = 23;
        static final int TRANSACTION_callSessionResumeFailed = 9;
        static final int TRANSACTION_callSessionResumeReceived = 10;
        static final int TRANSACTION_callSessionResumed = 8;
        static final int TRANSACTION_callSessionRttAudioIndicatorChanged = 35;
        static final int TRANSACTION_callSessionRttMessageReceived = 34;
        static final int TRANSACTION_callSessionRttModifyRequestReceived = 32;
        static final int TRANSACTION_callSessionRttModifyResponseReceived = 33;
        static final int TRANSACTION_callSessionStartFailed = 3;
        static final int TRANSACTION_callSessionStarted = 2;
        static final int TRANSACTION_callSessionSuppServiceReceived = 31;
        static final int TRANSACTION_callSessionTerminated = 4;
        static final int TRANSACTION_callSessionTtyModeReceived = 29;
        static final int TRANSACTION_callSessionUpdateFailed = 15;
        static final int TRANSACTION_callSessionUpdateReceived = 16;
        static final int TRANSACTION_callSessionUpdated = 14;
        static final int TRANSACTION_callSessionUssdMessageReceived = 25;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsCallSessionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsCallSessionListener)) {
                return new Proxy(obj);
            }
            return (IImsCallSessionListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "callSessionProgressing";
                case 2:
                    return "callSessionStarted";
                case 3:
                    return "callSessionStartFailed";
                case 4:
                    return "callSessionTerminated";
                case 5:
                    return "callSessionHeld";
                case 6:
                    return "callSessionHoldFailed";
                case 7:
                    return "callSessionHoldReceived";
                case 8:
                    return "callSessionResumed";
                case 9:
                    return "callSessionResumeFailed";
                case 10:
                    return "callSessionResumeReceived";
                case 11:
                    return "callSessionMergeStarted";
                case 12:
                    return "callSessionMergeComplete";
                case 13:
                    return "callSessionMergeFailed";
                case 14:
                    return "callSessionUpdated";
                case 15:
                    return "callSessionUpdateFailed";
                case 16:
                    return "callSessionUpdateReceived";
                case 17:
                    return "callSessionConferenceExtended";
                case 18:
                    return "callSessionConferenceExtendFailed";
                case 19:
                    return "callSessionConferenceExtendReceived";
                case 20:
                    return "callSessionInviteParticipantsRequestDelivered";
                case 21:
                    return "callSessionInviteParticipantsRequestFailed";
                case 22:
                    return "callSessionRemoveParticipantsRequestDelivered";
                case 23:
                    return "callSessionRemoveParticipantsRequestFailed";
                case 24:
                    return "callSessionConferenceStateUpdated";
                case 25:
                    return "callSessionUssdMessageReceived";
                case 26:
                    return "callSessionHandover";
                case 27:
                    return "callSessionHandoverFailed";
                case 28:
                    return "callSessionMayHandover";
                case 29:
                    return "callSessionTtyModeReceived";
                case 30:
                    return "callSessionMultipartyStateChanged";
                case 31:
                    return "callSessionSuppServiceReceived";
                case 32:
                    return "callSessionRttModifyRequestReceived";
                case 33:
                    return "callSessionRttModifyResponseReceived";
                case 34:
                    return "callSessionRttMessageReceived";
                case 35:
                    return "callSessionRttAudioIndicatorChanged";
                case 36:
                    return "callQualityChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.telephony.ims.ImsStreamMediaProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v38, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v42, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v48, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v52, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v56, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v60, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v64, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v68, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v72, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v78, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v84, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v88, resolved type: android.telephony.ims.ImsConferenceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v94, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v98, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v108, resolved type: android.telephony.ims.ImsSuppServiceNotification} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v112, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v118, resolved type: android.telephony.ims.ImsStreamMediaProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v122, resolved type: android.telephony.CallQuality} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v127 */
        /* JADX WARNING: type inference failed for: r1v128 */
        /* JADX WARNING: type inference failed for: r1v129 */
        /* JADX WARNING: type inference failed for: r1v130 */
        /* JADX WARNING: type inference failed for: r1v131 */
        /* JADX WARNING: type inference failed for: r1v132 */
        /* JADX WARNING: type inference failed for: r1v133 */
        /* JADX WARNING: type inference failed for: r1v134 */
        /* JADX WARNING: type inference failed for: r1v135 */
        /* JADX WARNING: type inference failed for: r1v136 */
        /* JADX WARNING: type inference failed for: r1v137 */
        /* JADX WARNING: type inference failed for: r1v138 */
        /* JADX WARNING: type inference failed for: r1v139 */
        /* JADX WARNING: type inference failed for: r1v140 */
        /* JADX WARNING: type inference failed for: r1v141 */
        /* JADX WARNING: type inference failed for: r1v142 */
        /* JADX WARNING: type inference failed for: r1v143 */
        /* JADX WARNING: type inference failed for: r1v144 */
        /* JADX WARNING: type inference failed for: r1v145 */
        /* JADX WARNING: type inference failed for: r1v146 */
        /* JADX WARNING: type inference failed for: r1v147 */
        /* JADX WARNING: type inference failed for: r1v148 */
        /* JADX WARNING: type inference failed for: r1v149 */
        /* JADX WARNING: type inference failed for: r1v150 */
        /* JADX WARNING: type inference failed for: r1v151 */
        /* JADX WARNING: type inference failed for: r1v152 */
        /* JADX WARNING: type inference failed for: r1v153 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.ims.internal.IImsCallSessionListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x040a
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x03eb;
                    case 2: goto L_0x03cc;
                    case 3: goto L_0x03ad;
                    case 4: goto L_0x038e;
                    case 5: goto L_0x036f;
                    case 6: goto L_0x0350;
                    case 7: goto L_0x0331;
                    case 8: goto L_0x0312;
                    case 9: goto L_0x02f3;
                    case 10: goto L_0x02d4;
                    case 11: goto L_0x02ad;
                    case 12: goto L_0x029e;
                    case 13: goto L_0x027f;
                    case 14: goto L_0x0260;
                    case 15: goto L_0x0241;
                    case 16: goto L_0x0222;
                    case 17: goto L_0x01fb;
                    case 18: goto L_0x01dc;
                    case 19: goto L_0x01b5;
                    case 20: goto L_0x01a6;
                    case 21: goto L_0x0187;
                    case 22: goto L_0x0178;
                    case 23: goto L_0x0159;
                    case 24: goto L_0x013a;
                    case 25: goto L_0x0123;
                    case 26: goto L_0x00fc;
                    case 27: goto L_0x00d5;
                    case 28: goto L_0x00be;
                    case 29: goto L_0x00ab;
                    case 30: goto L_0x0093;
                    case 31: goto L_0x0074;
                    case 32: goto L_0x0055;
                    case 33: goto L_0x004a;
                    case 34: goto L_0x003f;
                    case 35: goto L_0x0028;
                    case 36: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.telephony.CallQuality> r1 = android.telephony.CallQuality.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.CallQuality r1 = (android.telephony.CallQuality) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r7.callQualityChanged(r1)
                return r2
            L_0x0028:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r1 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsStreamMediaProfile r1 = (android.telephony.ims.ImsStreamMediaProfile) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                r7.callSessionRttAudioIndicatorChanged(r1)
                return r2
            L_0x003f:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.callSessionRttMessageReceived(r1)
                return r2
            L_0x004a:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.callSessionRttModifyResponseReceived(r1)
                return r2
            L_0x0055:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x006f
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x0070
            L_0x006f:
            L_0x0070:
                r7.callSessionRttModifyRequestReceived(r3, r1)
                return r2
            L_0x0074:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x008e
                android.os.Parcelable$Creator<android.telephony.ims.ImsSuppServiceNotification> r1 = android.telephony.ims.ImsSuppServiceNotification.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsSuppServiceNotification r1 = (android.telephony.ims.ImsSuppServiceNotification) r1
                goto L_0x008f
            L_0x008e:
            L_0x008f:
                r7.callSessionSuppServiceReceived(r3, r1)
                return r2
            L_0x0093:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x00a6
                r3 = r2
                goto L_0x00a7
            L_0x00a6:
                r3 = 0
            L_0x00a7:
                r7.callSessionMultipartyStateChanged(r1, r3)
                return r2
            L_0x00ab:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                int r3 = r9.readInt()
                r7.callSessionTtyModeReceived(r1, r3)
                return r2
            L_0x00be:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                r7.callSessionMayHandover(r1, r3, r4)
                return r2
            L_0x00d5:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                int r5 = r9.readInt()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x00f7
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x00f8
            L_0x00f7:
            L_0x00f8:
                r7.callSessionHandoverFailed(r3, r4, r5, r1)
                return r2
            L_0x00fc:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                int r5 = r9.readInt()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x011e
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x011f
            L_0x011e:
            L_0x011f:
                r7.callSessionHandover(r3, r4, r5, r1)
                return r2
            L_0x0123:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                int r3 = r9.readInt()
                java.lang.String r4 = r9.readString()
                r7.callSessionUssdMessageReceived(r1, r3, r4)
                return r2
            L_0x013a:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0154
                android.os.Parcelable$Creator<android.telephony.ims.ImsConferenceState> r1 = android.telephony.ims.ImsConferenceState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsConferenceState r1 = (android.telephony.ims.ImsConferenceState) r1
                goto L_0x0155
            L_0x0154:
            L_0x0155:
                r7.callSessionConferenceStateUpdated(r3, r1)
                return r2
            L_0x0159:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0173
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x0174
            L_0x0173:
            L_0x0174:
                r7.callSessionRemoveParticipantsRequestFailed(r3, r1)
                return r2
            L_0x0178:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                r7.callSessionRemoveParticipantsRequestDelivered(r1)
                return r2
            L_0x0187:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x01a1
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x01a2
            L_0x01a1:
            L_0x01a2:
                r7.callSessionInviteParticipantsRequestFailed(r3, r1)
                return r2
            L_0x01a6:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                r7.callSessionInviteParticipantsRequestDelivered(r1)
                return r2
            L_0x01b5:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r4 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x01d7
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x01d8
            L_0x01d7:
            L_0x01d8:
                r7.callSessionConferenceExtendReceived(r3, r4, r1)
                return r2
            L_0x01dc:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x01f6
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x01f7
            L_0x01f6:
            L_0x01f7:
                r7.callSessionConferenceExtendFailed(r3, r1)
                return r2
            L_0x01fb:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r4 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x021d
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x021e
            L_0x021d:
            L_0x021e:
                r7.callSessionConferenceExtended(r3, r4, r1)
                return r2
            L_0x0222:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x023c
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x023d
            L_0x023c:
            L_0x023d:
                r7.callSessionUpdateReceived(r3, r1)
                return r2
            L_0x0241:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x025b
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x025c
            L_0x025b:
            L_0x025c:
                r7.callSessionUpdateFailed(r3, r1)
                return r2
            L_0x0260:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x027a
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x027b
            L_0x027a:
            L_0x027b:
                r7.callSessionUpdated(r3, r1)
                return r2
            L_0x027f:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0299
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x029a
            L_0x0299:
            L_0x029a:
                r7.callSessionMergeFailed(r3, r1)
                return r2
            L_0x029e:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                r7.callSessionMergeComplete(r1)
                return r2
            L_0x02ad:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r4 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x02cf
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x02d0
            L_0x02cf:
            L_0x02d0:
                r7.callSessionMergeStarted(r3, r4, r1)
                return r2
            L_0x02d4:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x02ee
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x02ef
            L_0x02ee:
            L_0x02ef:
                r7.callSessionResumeReceived(r3, r1)
                return r2
            L_0x02f3:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x030d
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x030e
            L_0x030d:
            L_0x030e:
                r7.callSessionResumeFailed(r3, r1)
                return r2
            L_0x0312:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x032c
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x032d
            L_0x032c:
            L_0x032d:
                r7.callSessionResumed(r3, r1)
                return r2
            L_0x0331:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x034b
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x034c
            L_0x034b:
            L_0x034c:
                r7.callSessionHoldReceived(r3, r1)
                return r2
            L_0x0350:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x036a
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x036b
            L_0x036a:
            L_0x036b:
                r7.callSessionHoldFailed(r3, r1)
                return r2
            L_0x036f:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0389
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x038a
            L_0x0389:
            L_0x038a:
                r7.callSessionHeld(r3, r1)
                return r2
            L_0x038e:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x03a8
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x03a9
            L_0x03a8:
            L_0x03a9:
                r7.callSessionTerminated(r3, r1)
                return r2
            L_0x03ad:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x03c7
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x03c8
            L_0x03c7:
            L_0x03c8:
                r7.callSessionStartFailed(r3, r1)
                return r2
            L_0x03cc:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x03e6
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x03e7
            L_0x03e6:
            L_0x03e7:
                r7.callSessionStarted(r3, r1)
                return r2
            L_0x03eb:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0405
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r1 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.telephony.ims.ImsStreamMediaProfile r1 = (android.telephony.ims.ImsStreamMediaProfile) r1
                goto L_0x0406
            L_0x0405:
            L_0x0406:
                r7.callSessionProgressing(r3, r1)
                return r2
            L_0x040a:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.IImsCallSessionListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsCallSessionListener {
            public static IImsCallSessionListener sDefaultImpl;
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

            public void callSessionProgressing(IImsCallSession session, ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionProgressing(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionStarted(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionStarted(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionStartFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionStartFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionTerminated(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionTerminated(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHeld(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHeld(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHoldFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHoldFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHoldReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHoldReceived(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionResumed(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionResumed(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionResumeFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionResumeFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionResumeReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionResumeReceived(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMergeStarted(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeStrongBinder(newSession != null ? newSession.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMergeStarted(session, newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMergeComplete(IImsCallSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMergeComplete(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMergeFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMergeFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUpdated(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUpdated(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUpdateFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUpdateFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUpdateReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUpdateReceived(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceExtended(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeStrongBinder(newSession != null ? newSession.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionConferenceExtended(session, newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceExtendFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionConferenceExtendFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceExtendReceived(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeStrongBinder(newSession != null ? newSession.asBinder() : null);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionConferenceExtendReceived(session, newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionInviteParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestDelivered(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionInviteParticipantsRequestFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestDelivered(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRemoveParticipantsRequestFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceStateUpdated(IImsCallSession session, ImsConferenceState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionConferenceStateUpdated(session, state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUssdMessageReceived(IImsCallSession session, int mode, String ussdMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(mode);
                    _data.writeString(ussdMessage);
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUssdMessageReceived(session, mode, ussdMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(srcAccessTech);
                    _data.writeInt(targetAccessTech);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(26, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHandover(session, srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHandoverFailed(IImsCallSession session, int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(srcAccessTech);
                    _data.writeInt(targetAccessTech);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHandoverFailed(session, srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMayHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(srcAccessTech);
                    _data.writeInt(targetAccessTech);
                    if (this.mRemote.transact(28, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMayHandover(session, srcAccessTech, targetAccessTech);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionTtyModeReceived(IImsCallSession session, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(29, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionTtyModeReceived(session, mode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMultipartyStateChanged(IImsCallSession session, boolean isMultiParty) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(isMultiParty);
                    if (this.mRemote.transact(30, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMultipartyStateChanged(session, isMultiParty);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionSuppServiceReceived(IImsCallSession session, ImsSuppServiceNotification suppSrvNotification) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (suppSrvNotification != null) {
                        _data.writeInt(1);
                        suppSrvNotification.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionSuppServiceReceived(session, suppSrvNotification);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRttModifyRequestReceived(IImsCallSession session, ImsCallProfile callProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (callProfile != null) {
                        _data.writeInt(1);
                        callProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(32, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRttModifyRequestReceived(session, callProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRttModifyResponseReceived(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(33, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRttModifyResponseReceived(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRttMessageReceived(String rttMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rttMessage);
                    if (this.mRemote.transact(34, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRttMessageReceived(rttMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(35, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRttAudioIndicatorChanged(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callQualityChanged(CallQuality callQuality) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callQuality != null) {
                        _data.writeInt(1);
                        callQuality.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(36, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callQualityChanged(callQuality);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsCallSessionListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsCallSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
