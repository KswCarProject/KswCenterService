package android.telephony.ims.aidl;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import com.android.ims.internal.IImsCallSession;

/* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    public static class Default implements IImsCallSessionListener {
        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionProgressing(ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiated(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatedFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTerminated(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHeld(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldReceived(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumed(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeReceived(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeStarted(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeComplete(IImsCallSession session) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdated(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateReceived(ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtended(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(ImsConferenceState state) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUssdMessageReceived(int mode, String ussdMessage) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandover(int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandoverFailed(int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMayHandover(int srcAccessTech, int targetAccessTech) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTtyModeReceived(int mode) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(boolean isMultiParty) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSuppServiceReceived(ImsSuppServiceNotification suppSrvNotification) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(ImsCallProfile callProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int status) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttMessageReceived(String rttMessage) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
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
            if (iin != null && (iin instanceof IImsCallSessionListener)) {
                return (IImsCallSessionListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    ImsStreamMediaProfile _arg0 = data.readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel(data) : null;
                    callSessionProgressing(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg02 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionInitiated(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg03 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionInitiatedFailed(_arg03);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg04 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionTerminated(_arg04);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg05 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionHeld(_arg05);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg06 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionHoldFailed(_arg06);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg07 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionHoldReceived(_arg07);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg08 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionResumed(_arg08);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg09 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionResumeFailed(_arg09);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg010 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionResumeReceived(_arg010);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg011 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg1 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionMergeStarted(_arg011, _arg1);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg012 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    callSessionMergeComplete(_arg012);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg013 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionMergeFailed(_arg013);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg014 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionUpdated(_arg014);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg015 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionUpdateFailed(_arg015);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg016 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionUpdateReceived(_arg016);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg017 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg12 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceExtended(_arg017, _arg12);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg018 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceExtendFailed(_arg018);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg019 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg13 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceExtendReceived(_arg019, _arg13);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    callSessionInviteParticipantsRequestDelivered();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg020 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionInviteParticipantsRequestFailed(_arg020);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    callSessionRemoveParticipantsRequestDelivered();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    ImsReasonInfo _arg021 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionRemoveParticipantsRequestFailed(_arg021);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    ImsConferenceState _arg022 = data.readInt() != 0 ? ImsConferenceState.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceStateUpdated(_arg022);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg023 = data.readInt();
                    String _arg14 = data.readString();
                    callSessionUssdMessageReceived(_arg023, _arg14);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg024 = data.readInt();
                    int _arg15 = data.readInt();
                    ImsReasonInfo _arg2 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionHandover(_arg024, _arg15, _arg2);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg025 = data.readInt();
                    int _arg16 = data.readInt();
                    ImsReasonInfo _arg22 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionHandoverFailed(_arg025, _arg16, _arg22);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg026 = data.readInt();
                    int _arg17 = data.readInt();
                    callSessionMayHandover(_arg026, _arg17);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg027 = data.readInt();
                    callSessionTtyModeReceived(_arg027);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg028 = data.readInt() != 0;
                    callSessionMultipartyStateChanged(_arg028);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    ImsSuppServiceNotification _arg029 = data.readInt() != 0 ? ImsSuppServiceNotification.CREATOR.createFromParcel(data) : null;
                    callSessionSuppServiceReceived(_arg029);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    ImsCallProfile _arg030 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionRttModifyRequestReceived(_arg030);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg031 = data.readInt();
                    callSessionRttModifyResponseReceived(_arg031);
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg032 = data.readString();
                    callSessionRttMessageReceived(_arg032);
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    ImsStreamMediaProfile _arg033 = data.readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel(data) : null;
                    callSessionRttAudioIndicatorChanged(_arg033);
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    CallQuality _arg034 = data.readInt() != 0 ? CallQuality.CREATOR.createFromParcel(data) : null;
                    callQualityChanged(_arg034);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IImsCallSessionListener {
            public static IImsCallSessionListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionProgressing(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInitiated(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInitiatedFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionTerminated(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHeld(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHoldFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHoldReceived(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumed(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumeFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumeReceived(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMergeStarted(newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeComplete(IImsCallSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMergeComplete(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMergeFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdated(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdateFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdateReceived(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtended(newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtendFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtendReceived(newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestDelivered();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestDelivered();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestFailed(reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceStateUpdated(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUssdMessageReceived(int mode, String ussdMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(ussdMessage);
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUssdMessageReceived(mode, ussdMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHandover(srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHandoverFailed(srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMayHandover(int srcAccessTech, int targetAccessTech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(srcAccessTech);
                    _data.writeInt(targetAccessTech);
                    boolean _status = this.mRemote.transact(28, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMayHandover(srcAccessTech, targetAccessTech);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTtyModeReceived(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(29, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionTtyModeReceived(mode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMultipartyStateChanged(boolean isMultiParty) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isMultiParty ? 1 : 0);
                    boolean _status = this.mRemote.transact(30, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMultipartyStateChanged(isMultiParty);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(31, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionSuppServiceReceived(suppSrvNotification);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(32, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttModifyRequestReceived(callProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttModifyResponseReceived(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(33, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttModifyResponseReceived(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttMessageReceived(String rttMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rttMessage);
                    boolean _status = this.mRemote.transact(34, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttMessageReceived(rttMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(35, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttAudioIndicatorChanged(profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(36, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callQualityChanged(callQuality);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsCallSessionListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IImsCallSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
