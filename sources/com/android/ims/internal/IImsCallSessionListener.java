package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
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

    /* loaded from: classes4.dex */
    public static class Default implements IImsCallSessionListener {
        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionProgressing(IImsCallSession session, ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStarted(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStartFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTerminated(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHeld(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumed(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeStarted(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeComplete(IImsCallSession session) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdated(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateReceived(IImsCallSession session, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtended(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(IImsCallSession session, IImsCallSession newSession, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(IImsCallSession session, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(IImsCallSession session, ImsConferenceState state) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUssdMessageReceived(IImsCallSession session, int mode, String ussdMessage) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandoverFailed(IImsCallSession session, int srcAccessTech, int targetAccessTech, ImsReasonInfo reasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMayHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTtyModeReceived(IImsCallSession session, int mode) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(IImsCallSession session, boolean isMultiParty) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSuppServiceReceived(IImsCallSession session, ImsSuppServiceNotification suppSrvNotification) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(IImsCallSession session, ImsCallProfile callProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int status) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttMessageReceived(String rttMessage) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
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
                    IImsCallSession _arg0 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsStreamMediaProfile _arg1 = data.readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel(data) : null;
                    callSessionProgressing(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg02 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg12 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionStarted(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg03 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg13 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionStartFailed(_arg03, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg04 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg14 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionTerminated(_arg04, _arg14);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg05 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg15 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionHeld(_arg05, _arg15);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg06 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg16 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionHoldFailed(_arg06, _arg16);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg07 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg17 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionHoldReceived(_arg07, _arg17);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg08 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg18 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionResumed(_arg08, _arg18);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg09 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg19 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionResumeFailed(_arg09, _arg19);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg010 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg110 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionResumeReceived(_arg010, _arg110);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg011 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    IImsCallSession _arg111 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg2 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionMergeStarted(_arg011, _arg111, _arg2);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg012 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    callSessionMergeComplete(_arg012);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg013 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg112 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionMergeFailed(_arg013, _arg112);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg014 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg113 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionUpdated(_arg014, _arg113);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg015 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg114 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionUpdateFailed(_arg015, _arg114);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg016 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg115 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionUpdateReceived(_arg016, _arg115);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg017 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    IImsCallSession _arg116 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg22 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceExtended(_arg017, _arg116, _arg22);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg018 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg117 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceExtendFailed(_arg018, _arg117);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg019 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    IImsCallSession _arg118 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg23 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceExtendReceived(_arg019, _arg118, _arg23);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg020 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    callSessionInviteParticipantsRequestDelivered(_arg020);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg021 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg119 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionInviteParticipantsRequestFailed(_arg021, _arg119);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg022 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    callSessionRemoveParticipantsRequestDelivered(_arg022);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg023 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsReasonInfo _arg120 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionRemoveParticipantsRequestFailed(_arg023, _arg120);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg024 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsConferenceState _arg121 = data.readInt() != 0 ? ImsConferenceState.CREATOR.createFromParcel(data) : null;
                    callSessionConferenceStateUpdated(_arg024, _arg121);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg025 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    int _arg122 = data.readInt();
                    String _arg24 = data.readString();
                    callSessionUssdMessageReceived(_arg025, _arg122, _arg24);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg026 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    int _arg123 = data.readInt();
                    int _arg25 = data.readInt();
                    ImsReasonInfo _arg3 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionHandover(_arg026, _arg123, _arg25, _arg3);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg027 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    int _arg124 = data.readInt();
                    int _arg26 = data.readInt();
                    ImsReasonInfo _arg32 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    callSessionHandoverFailed(_arg027, _arg124, _arg26, _arg32);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg028 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    int _arg125 = data.readInt();
                    int _arg27 = data.readInt();
                    callSessionMayHandover(_arg028, _arg125, _arg27);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg029 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    int _arg126 = data.readInt();
                    callSessionTtyModeReceived(_arg029, _arg126);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg030 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    boolean _arg127 = data.readInt() != 0;
                    callSessionMultipartyStateChanged(_arg030, _arg127);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg031 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsSuppServiceNotification _arg128 = data.readInt() != 0 ? ImsSuppServiceNotification.CREATOR.createFromParcel(data) : null;
                    callSessionSuppServiceReceived(_arg031, _arg128);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg032 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    ImsCallProfile _arg129 = data.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(data) : null;
                    callSessionRttModifyRequestReceived(_arg032, _arg129);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg033 = data.readInt();
                    callSessionRttModifyResponseReceived(_arg033);
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg034 = data.readString();
                    callSessionRttMessageReceived(_arg034);
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    ImsStreamMediaProfile _arg035 = data.readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel(data) : null;
                    callSessionRttAudioIndicatorChanged(_arg035);
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    CallQuality _arg036 = data.readInt() != 0 ? CallQuality.CREATOR.createFromParcel(data) : null;
                    callQualityChanged(_arg036);
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

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionProgressing(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionStarted(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionStartFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionTerminated(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHeld(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHoldFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHoldReceived(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumed(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumeFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumeReceived(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMergeStarted(session, newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMergeFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdated(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdateFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdateReceived(session, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtended(session, newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtendFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtendReceived(session, newSession, profile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestDelivered(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession session) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestDelivered(session);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestFailed(session, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceStateUpdated(session, state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUssdMessageReceived(IImsCallSession session, int mode, String ussdMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(mode);
                    _data.writeString(ussdMessage);
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUssdMessageReceived(session, mode, ussdMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHandover(session, srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHandoverFailed(session, srcAccessTech, targetAccessTech, reasonInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMayHandover(IImsCallSession session, int srcAccessTech, int targetAccessTech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(srcAccessTech);
                    _data.writeInt(targetAccessTech);
                    boolean _status = this.mRemote.transact(28, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMayHandover(session, srcAccessTech, targetAccessTech);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTtyModeReceived(IImsCallSession session, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(29, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionTtyModeReceived(session, mode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMultipartyStateChanged(IImsCallSession session, boolean isMultiParty) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeInt(isMultiParty ? 1 : 0);
                    boolean _status = this.mRemote.transact(30, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMultipartyStateChanged(session, isMultiParty);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(31, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionSuppServiceReceived(session, suppSrvNotification);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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
                    boolean _status = this.mRemote.transact(32, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttModifyRequestReceived(session, callProfile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
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

            @Override // com.android.ims.internal.IImsCallSessionListener
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

            @Override // com.android.ims.internal.IImsCallSessionListener
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

            @Override // com.android.ims.internal.IImsCallSessionListener
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
