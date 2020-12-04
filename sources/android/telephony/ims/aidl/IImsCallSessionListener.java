package android.telephony.ims.aidl;

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
import com.android.ims.internal.IImsCallSession;

public interface IImsCallSessionListener extends IInterface {
    void callQualityChanged(CallQuality callQuality) throws RemoteException;

    void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) throws RemoteException;

    void callSessionHandover(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHandoverFailed(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHeld(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHoldReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionInitiated(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionInviteParticipantsRequestDelivered() throws RemoteException;

    void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMayHandover(int i, int i2) throws RemoteException;

    void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException;

    void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionMultipartyStateChanged(boolean z) throws RemoteException;

    void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRemoveParticipantsRequestDelivered() throws RemoteException;

    void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionResumeReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionResumed(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRttMessageReceived(String str) throws RemoteException;

    void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttModifyResponseReceived(int i) throws RemoteException;

    void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException;

    void callSessionTerminated(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionTtyModeReceived(int i) throws RemoteException;

    void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionUpdateReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUpdated(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUssdMessageReceived(int i, String str) throws RemoteException;

    public static class Default implements IImsCallSessionListener {
        public void callSessionProgressing(ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void callSessionInitiated(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionInitiatedFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionTerminated(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionHeld(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionHoldFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionHoldReceived(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionResumed(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionResumeFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionResumeReceived(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionMergeStarted(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionMergeComplete(IImsCallSession session) throws RemoteException {
        }

        public void callSessionMergeFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionUpdated(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionUpdateFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionUpdateReceived(ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionConferenceExtended(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionConferenceExtendFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionConferenceExtendReceived(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
        }

        public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
        }

        public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionConferenceStateUpdated(ImsConferenceState state) throws RemoteException {
        }

        public void callSessionUssdMessageReceived(int mode, String ussdMessage) throws RemoteException {
        }

        public void callSessionHandover(int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionHandoverFailed(int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        public void callSessionMayHandover(int srcAccessTech, int targetAccessTech) throws RemoteException {
        }

        public void callSessionTtyModeReceived(int mode) throws RemoteException {
        }

        public void callSessionMultipartyStateChanged(boolean isMultiParty) throws RemoteException {
        }

        public void callSessionSuppServiceReceived(ImsSuppServiceNotification suppSrvNotification) throws RemoteException {
        }

        public void callSessionRttModifyRequestReceived(ImsCallProfile callProfile) throws RemoteException {
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
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsCallSessionListener";
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
        static final int TRANSACTION_callSessionInitiated = 2;
        static final int TRANSACTION_callSessionInitiatedFailed = 3;
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
                    return "callSessionInitiated";
                case 3:
                    return "callSessionInitiatedFailed";
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
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v76, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v80, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v84, resolved type: android.telephony.ims.ImsConferenceState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v89, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v93, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v103, resolved type: android.telephony.ims.ImsSuppServiceNotification} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v107, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v113, resolved type: android.telephony.ims.ImsStreamMediaProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v117, resolved type: android.telephony.CallQuality} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v122 */
        /* JADX WARNING: type inference failed for: r1v123 */
        /* JADX WARNING: type inference failed for: r1v124 */
        /* JADX WARNING: type inference failed for: r1v125 */
        /* JADX WARNING: type inference failed for: r1v126 */
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
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.telephony.ims.aidl.IImsCallSessionListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0312
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x02fb;
                    case 2: goto L_0x02e4;
                    case 3: goto L_0x02cd;
                    case 4: goto L_0x02b6;
                    case 5: goto L_0x029f;
                    case 6: goto L_0x0288;
                    case 7: goto L_0x0271;
                    case 8: goto L_0x025a;
                    case 9: goto L_0x0243;
                    case 10: goto L_0x022c;
                    case 11: goto L_0x020d;
                    case 12: goto L_0x01fe;
                    case 13: goto L_0x01e7;
                    case 14: goto L_0x01d0;
                    case 15: goto L_0x01b9;
                    case 16: goto L_0x01a2;
                    case 17: goto L_0x0183;
                    case 18: goto L_0x016c;
                    case 19: goto L_0x014d;
                    case 20: goto L_0x0146;
                    case 21: goto L_0x012f;
                    case 22: goto L_0x0128;
                    case 23: goto L_0x0111;
                    case 24: goto L_0x00fa;
                    case 25: goto L_0x00eb;
                    case 26: goto L_0x00cc;
                    case 27: goto L_0x00ad;
                    case 28: goto L_0x009e;
                    case 29: goto L_0x0093;
                    case 30: goto L_0x0083;
                    case 31: goto L_0x006c;
                    case 32: goto L_0x0055;
                    case 33: goto L_0x004a;
                    case 34: goto L_0x003f;
                    case 35: goto L_0x0028;
                    case 36: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.telephony.CallQuality> r1 = android.telephony.CallQuality.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.CallQuality r1 = (android.telephony.CallQuality) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r6.callQualityChanged(r1)
                return r2
            L_0x0028:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r1 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsStreamMediaProfile r1 = (android.telephony.ims.ImsStreamMediaProfile) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                r6.callSessionRttAudioIndicatorChanged(r1)
                return r2
            L_0x003f:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.callSessionRttMessageReceived(r1)
                return r2
            L_0x004a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.callSessionRttModifyResponseReceived(r1)
                return r2
            L_0x0055:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0067
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x0068
            L_0x0067:
            L_0x0068:
                r6.callSessionRttModifyRequestReceived(r1)
                return r2
            L_0x006c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x007e
                android.os.Parcelable$Creator<android.telephony.ims.ImsSuppServiceNotification> r1 = android.telephony.ims.ImsSuppServiceNotification.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsSuppServiceNotification r1 = (android.telephony.ims.ImsSuppServiceNotification) r1
                goto L_0x007f
            L_0x007e:
            L_0x007f:
                r6.callSessionSuppServiceReceived(r1)
                return r2
            L_0x0083:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x008e
                r1 = r2
                goto L_0x008f
            L_0x008e:
                r1 = 0
            L_0x008f:
                r6.callSessionMultipartyStateChanged(r1)
                return r2
            L_0x0093:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.callSessionTtyModeReceived(r1)
                return r2
            L_0x009e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.callSessionMayHandover(r1, r3)
                return r2
            L_0x00ad:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00c7
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x00c8
            L_0x00c7:
            L_0x00c8:
                r6.callSessionHandoverFailed(r3, r4, r1)
                return r2
            L_0x00cc:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00e6
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x00e7
            L_0x00e6:
            L_0x00e7:
                r6.callSessionHandover(r3, r4, r1)
                return r2
            L_0x00eb:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                java.lang.String r3 = r8.readString()
                r6.callSessionUssdMessageReceived(r1, r3)
                return r2
            L_0x00fa:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x010c
                android.os.Parcelable$Creator<android.telephony.ims.ImsConferenceState> r1 = android.telephony.ims.ImsConferenceState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsConferenceState r1 = (android.telephony.ims.ImsConferenceState) r1
                goto L_0x010d
            L_0x010c:
            L_0x010d:
                r6.callSessionConferenceStateUpdated(r1)
                return r2
            L_0x0111:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0123
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x0124
            L_0x0123:
            L_0x0124:
                r6.callSessionRemoveParticipantsRequestFailed(r1)
                return r2
            L_0x0128:
                r8.enforceInterface(r0)
                r6.callSessionRemoveParticipantsRequestDelivered()
                return r2
            L_0x012f:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0141
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x0142
            L_0x0141:
            L_0x0142:
                r6.callSessionInviteParticipantsRequestFailed(r1)
                return r2
            L_0x0146:
                r8.enforceInterface(r0)
                r6.callSessionInviteParticipantsRequestDelivered()
                return r2
            L_0x014d:
                r8.enforceInterface(r0)
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0167
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x0168
            L_0x0167:
            L_0x0168:
                r6.callSessionConferenceExtendReceived(r3, r1)
                return r2
            L_0x016c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x017e
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x017f
            L_0x017e:
            L_0x017f:
                r6.callSessionConferenceExtendFailed(r1)
                return r2
            L_0x0183:
                r8.enforceInterface(r0)
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x019d
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x019e
            L_0x019d:
            L_0x019e:
                r6.callSessionConferenceExtended(r3, r1)
                return r2
            L_0x01a2:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x01b4
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x01b5
            L_0x01b4:
            L_0x01b5:
                r6.callSessionUpdateReceived(r1)
                return r2
            L_0x01b9:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x01cb
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x01cc
            L_0x01cb:
            L_0x01cc:
                r6.callSessionUpdateFailed(r1)
                return r2
            L_0x01d0:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x01e2
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x01e3
            L_0x01e2:
            L_0x01e3:
                r6.callSessionUpdated(r1)
                return r2
            L_0x01e7:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x01f9
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x01fa
            L_0x01f9:
            L_0x01fa:
                r6.callSessionMergeFailed(r1)
                return r2
            L_0x01fe:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                com.android.ims.internal.IImsCallSession r1 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r1)
                r6.callSessionMergeComplete(r1)
                return r2
            L_0x020d:
                r8.enforceInterface(r0)
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0227
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x0228
            L_0x0227:
            L_0x0228:
                r6.callSessionMergeStarted(r3, r1)
                return r2
            L_0x022c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x023e
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x023f
            L_0x023e:
            L_0x023f:
                r6.callSessionResumeReceived(r1)
                return r2
            L_0x0243:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0255
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x0256
            L_0x0255:
            L_0x0256:
                r6.callSessionResumeFailed(r1)
                return r2
            L_0x025a:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x026c
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x026d
            L_0x026c:
            L_0x026d:
                r6.callSessionResumed(r1)
                return r2
            L_0x0271:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0283
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x0284
            L_0x0283:
            L_0x0284:
                r6.callSessionHoldReceived(r1)
                return r2
            L_0x0288:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x029a
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x029b
            L_0x029a:
            L_0x029b:
                r6.callSessionHoldFailed(r1)
                return r2
            L_0x029f:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x02b1
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x02b2
            L_0x02b1:
            L_0x02b2:
                r6.callSessionHeld(r1)
                return r2
            L_0x02b6:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x02c8
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x02c9
            L_0x02c8:
            L_0x02c9:
                r6.callSessionTerminated(r1)
                return r2
            L_0x02cd:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x02df
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x02e0
            L_0x02df:
            L_0x02e0:
                r6.callSessionInitiatedFailed(r1)
                return r2
            L_0x02e4:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x02f6
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsCallProfile r1 = (android.telephony.ims.ImsCallProfile) r1
                goto L_0x02f7
            L_0x02f6:
            L_0x02f7:
                r6.callSessionInitiated(r1)
                return r2
            L_0x02fb:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x030d
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r1 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telephony.ims.ImsStreamMediaProfile r1 = (android.telephony.ims.ImsStreamMediaProfile) r1
                goto L_0x030e
            L_0x030d:
            L_0x030e:
                r6.callSessionProgressing(r1)
                return r2
            L_0x0312:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.aidl.IImsCallSessionListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
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

            public void callSessionProgressing(ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionProgressing(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionInitiated(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionInitiated(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionInitiatedFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionInitiatedFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionTerminated(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionTerminated(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHeld(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHeld(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHoldFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHoldFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHoldReceived(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionHoldReceived(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionResumed(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionResumed(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionResumeFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionResumeFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionResumeReceived(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionResumeReceived(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMergeStarted(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().callSessionMergeStarted(newSession, profile);
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

            public void callSessionMergeFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMergeFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUpdated(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUpdated(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUpdateFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUpdateFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUpdateReceived(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUpdateReceived(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceExtended(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().callSessionConferenceExtended(newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceExtendFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionConferenceExtendFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceExtendReceived(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().callSessionConferenceExtendReceived(newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestDelivered();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestDelivered();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (reasonInfo != null) {
                        _data.writeInt(1);
                        reasonInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionConferenceStateUpdated(ImsConferenceState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionConferenceStateUpdated(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionUssdMessageReceived(int mode, String ussdMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(ussdMessage);
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionUssdMessageReceived(mode, ussdMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHandover(int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().callSessionHandover(srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionHandoverFailed(int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().callSessionHandoverFailed(srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMayHandover(int srcAccessTech, int targetAccessTech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(srcAccessTech);
                    _data.writeInt(targetAccessTech);
                    if (this.mRemote.transact(28, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMayHandover(srcAccessTech, targetAccessTech);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionTtyModeReceived(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(29, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionTtyModeReceived(mode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionMultipartyStateChanged(boolean isMultiParty) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isMultiParty);
                    if (this.mRemote.transact(30, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionMultipartyStateChanged(isMultiParty);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionSuppServiceReceived(ImsSuppServiceNotification suppSrvNotification) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (suppSrvNotification != null) {
                        _data.writeInt(1);
                        suppSrvNotification.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionSuppServiceReceived(suppSrvNotification);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void callSessionRttModifyRequestReceived(ImsCallProfile callProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callProfile != null) {
                        _data.writeInt(1);
                        callProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(32, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().callSessionRttModifyRequestReceived(callProfile);
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
