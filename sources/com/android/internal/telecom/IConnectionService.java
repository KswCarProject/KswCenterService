package com.android.internal.telecom;

import android.net.Uri;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.ConnectionRequest;
import android.telecom.Logging.Session;
import android.telecom.PhoneAccountHandle;
import android.telephony.ims.ImsCallProfile;
import com.android.internal.telecom.IConnectionServiceAdapter;

/* loaded from: classes4.dex */
public interface IConnectionService extends IInterface {
    void abort(String str, Session.Info info) throws RemoteException;

    void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException;

    void addParticipantWithConference(String str, String str2) throws RemoteException;

    void answer(String str, Session.Info info) throws RemoteException;

    void answerVideo(String str, int i, Session.Info info) throws RemoteException;

    void conference(String str, String str2, Session.Info info) throws RemoteException;

    void connectionServiceFocusGained(Session.Info info) throws RemoteException;

    void connectionServiceFocusLost(Session.Info info) throws RemoteException;

    void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException;

    void createConnectionComplete(String str, Session.Info info) throws RemoteException;

    void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException;

    void deflect(String str, Uri uri, Session.Info info) throws RemoteException;

    void disconnect(String str, Session.Info info) throws RemoteException;

    void handoverComplete(String str, Session.Info info) throws RemoteException;

    void handoverFailed(String str, ConnectionRequest connectionRequest, int i, Session.Info info) throws RemoteException;

    void hold(String str, Session.Info info) throws RemoteException;

    void mergeConference(String str, Session.Info info) throws RemoteException;

    void onCallAudioStateChanged(String str, CallAudioState callAudioState, Session.Info info) throws RemoteException;

    void onExtrasChanged(String str, Bundle bundle, Session.Info info) throws RemoteException;

    void onPostDialContinue(String str, boolean z, Session.Info info) throws RemoteException;

    void playDtmfTone(String str, char c, Session.Info info) throws RemoteException;

    void pullExternalCall(String str, Session.Info info) throws RemoteException;

    void reject(String str, Session.Info info) throws RemoteException;

    void rejectWithMessage(String str, String str2, Session.Info info) throws RemoteException;

    void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException;

    void respondToRttUpgradeRequest(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException;

    void sendCallEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException;

    void silence(String str, Session.Info info) throws RemoteException;

    void splitFromConference(String str, Session.Info info) throws RemoteException;

    void startRtt(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException;

    void stopDtmfTone(String str, Session.Info info) throws RemoteException;

    void stopRtt(String str, Session.Info info) throws RemoteException;

    void swapConference(String str, Session.Info info) throws RemoteException;

    void unhold(String str, Session.Info info) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IConnectionService {
        @Override // com.android.internal.telecom.IConnectionService
        public void addConnectionServiceAdapter(IConnectionServiceAdapter adapter, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void removeConnectionServiceAdapter(IConnectionServiceAdapter adapter, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnection(PhoneAccountHandle connectionManagerPhoneAccount, String callId, ConnectionRequest request, boolean isIncoming, boolean isUnknown, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionComplete(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionFailed(PhoneAccountHandle connectionManagerPhoneAccount, String callId, ConnectionRequest request, boolean isIncoming, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void abort(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answerVideo(String callId, int videoState, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answer(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void deflect(String callId, Uri address, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void reject(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithMessage(String callId, String message, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void disconnect(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void silence(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void hold(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void unhold(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallAudioStateChanged(String activeCallId, CallAudioState callAudioState, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void playDtmfTone(String callId, char digit, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopDtmfTone(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void conference(String conferenceCallId, String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void splitFromConference(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void mergeConference(String conferenceCallId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void swapConference(String conferenceCallId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onPostDialContinue(String callId, boolean proceed, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void pullExternalCall(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void sendCallEvent(String callId, String event, Bundle extras, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onExtrasChanged(String callId, Bundle extras, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void startRtt(String callId, ParcelFileDescriptor fromInCall, ParcelFileDescriptor toInCall, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopRtt(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void respondToRttUpgradeRequest(String callId, ParcelFileDescriptor fromInCall, ParcelFileDescriptor toInCall, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void addParticipantWithConference(String callId, String recipients) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusLost(Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusGained(Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverFailed(String callId, ConnectionRequest request, int error, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverComplete(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IConnectionService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IConnectionService";
        static final int TRANSACTION_abort = 6;
        static final int TRANSACTION_addConnectionServiceAdapter = 1;
        static final int TRANSACTION_addParticipantWithConference = 30;
        static final int TRANSACTION_answer = 8;
        static final int TRANSACTION_answerVideo = 7;
        static final int TRANSACTION_conference = 19;
        static final int TRANSACTION_connectionServiceFocusGained = 32;
        static final int TRANSACTION_connectionServiceFocusLost = 31;
        static final int TRANSACTION_createConnection = 3;
        static final int TRANSACTION_createConnectionComplete = 4;
        static final int TRANSACTION_createConnectionFailed = 5;
        static final int TRANSACTION_deflect = 9;
        static final int TRANSACTION_disconnect = 12;
        static final int TRANSACTION_handoverComplete = 34;
        static final int TRANSACTION_handoverFailed = 33;
        static final int TRANSACTION_hold = 14;
        static final int TRANSACTION_mergeConference = 21;
        static final int TRANSACTION_onCallAudioStateChanged = 16;
        static final int TRANSACTION_onExtrasChanged = 26;
        static final int TRANSACTION_onPostDialContinue = 23;
        static final int TRANSACTION_playDtmfTone = 17;
        static final int TRANSACTION_pullExternalCall = 24;
        static final int TRANSACTION_reject = 10;
        static final int TRANSACTION_rejectWithMessage = 11;
        static final int TRANSACTION_removeConnectionServiceAdapter = 2;
        static final int TRANSACTION_respondToRttUpgradeRequest = 29;
        static final int TRANSACTION_sendCallEvent = 25;
        static final int TRANSACTION_silence = 13;
        static final int TRANSACTION_splitFromConference = 20;
        static final int TRANSACTION_startRtt = 27;
        static final int TRANSACTION_stopDtmfTone = 18;
        static final int TRANSACTION_stopRtt = 28;
        static final int TRANSACTION_swapConference = 22;
        static final int TRANSACTION_unhold = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IConnectionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IConnectionService)) {
                return (IConnectionService) iin;
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
                    return "addConnectionServiceAdapter";
                case 2:
                    return "removeConnectionServiceAdapter";
                case 3:
                    return "createConnection";
                case 4:
                    return "createConnectionComplete";
                case 5:
                    return "createConnectionFailed";
                case 6:
                    return "abort";
                case 7:
                    return "answerVideo";
                case 8:
                    return "answer";
                case 9:
                    return "deflect";
                case 10:
                    return "reject";
                case 11:
                    return "rejectWithMessage";
                case 12:
                    return "disconnect";
                case 13:
                    return "silence";
                case 14:
                    return "hold";
                case 15:
                    return "unhold";
                case 16:
                    return "onCallAudioStateChanged";
                case 17:
                    return "playDtmfTone";
                case 18:
                    return "stopDtmfTone";
                case 19:
                    return ImsCallProfile.EXTRA_CONFERENCE;
                case 20:
                    return "splitFromConference";
                case 21:
                    return "mergeConference";
                case 22:
                    return "swapConference";
                case 23:
                    return "onPostDialContinue";
                case 24:
                    return "pullExternalCall";
                case 25:
                    return "sendCallEvent";
                case 26:
                    return "onExtrasChanged";
                case 27:
                    return "startRtt";
                case 28:
                    return "stopRtt";
                case 29:
                    return "respondToRttUpgradeRequest";
                case 30:
                    return "addParticipantWithConference";
                case 31:
                    return "connectionServiceFocusLost";
                case 32:
                    return "connectionServiceFocusGained";
                case 33:
                    return "handoverFailed";
                case 34:
                    return "handoverComplete";
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
            Session.Info _arg1;
            PhoneAccountHandle _arg0;
            ConnectionRequest _arg2;
            PhoneAccountHandle _arg02;
            ConnectionRequest _arg22;
            Uri _arg12;
            CallAudioState _arg13;
            Bundle _arg23;
            Bundle _arg14;
            ParcelFileDescriptor _arg15;
            ParcelFileDescriptor _arg24;
            ParcelFileDescriptor _arg16;
            ParcelFileDescriptor _arg25;
            ConnectionRequest _arg17;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    IConnectionServiceAdapter _arg03 = IConnectionServiceAdapter.Stub.asInterface(data.readStrongBinder());
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    addConnectionServiceAdapter(_arg03, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IConnectionServiceAdapter _arg04 = IConnectionServiceAdapter.Stub.asInterface(data.readStrongBinder());
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    removeConnectionServiceAdapter(_arg04, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = PhoneAccountHandle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    String _arg18 = data.readString();
                    if (data.readInt() != 0) {
                        _arg2 = ConnectionRequest.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    boolean _arg3 = data.readInt() != 0;
                    boolean _arg4 = data.readInt() != 0;
                    Session.Info _arg5 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    createConnection(_arg0, _arg18, _arg2, _arg3, _arg4, _arg5);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    createConnectionComplete(_arg05, _arg1);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = PhoneAccountHandle.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    String _arg19 = data.readString();
                    if (data.readInt() != 0) {
                        _arg22 = ConnectionRequest.CREATOR.createFromParcel(data);
                    } else {
                        _arg22 = null;
                    }
                    boolean _arg32 = data.readInt() != 0;
                    Session.Info _arg42 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    createConnectionFailed(_arg02, _arg19, _arg22, _arg32, _arg42);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg06 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    abort(_arg06, _arg1);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg07 = data.readString();
                    int _arg110 = data.readInt();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    answerVideo(_arg07, _arg110, _arg1);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    answer(_arg08, _arg1);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    if (data.readInt() != 0) {
                        _arg12 = Uri.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    deflect(_arg09, _arg12, _arg1);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    reject(_arg010, _arg1);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg011 = data.readString();
                    String _arg111 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    rejectWithMessage(_arg011, _arg111, _arg1);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg012 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    disconnect(_arg012, _arg1);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg013 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    silence(_arg013, _arg1);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg014 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    hold(_arg014, _arg1);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg015 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    unhold(_arg015, _arg1);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg016 = data.readString();
                    if (data.readInt() != 0) {
                        _arg13 = CallAudioState.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    onCallAudioStateChanged(_arg016, _arg13, _arg1);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg017 = data.readString();
                    char _arg112 = (char) data.readInt();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    playDtmfTone(_arg017, _arg112, _arg1);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg018 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    stopDtmfTone(_arg018, _arg1);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg019 = data.readString();
                    String _arg113 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    conference(_arg019, _arg113, _arg1);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    splitFromConference(_arg020, _arg1);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg021 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    mergeConference(_arg021, _arg1);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg022 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    swapConference(_arg022, _arg1);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg023 = data.readString();
                    boolean _arg114 = data.readInt() != 0;
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    onPostDialContinue(_arg023, _arg114, _arg1);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg024 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    pullExternalCall(_arg024, _arg1);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg025 = data.readString();
                    String _arg115 = data.readString();
                    if (data.readInt() != 0) {
                        _arg23 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg23 = null;
                    }
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    sendCallEvent(_arg025, _arg115, _arg23, _arg1);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg026 = data.readString();
                    if (data.readInt() != 0) {
                        _arg14 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg14 = null;
                    }
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    onExtrasChanged(_arg026, _arg14, _arg1);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg027 = data.readString();
                    if (data.readInt() != 0) {
                        _arg15 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg15 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg24 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg24 = null;
                    }
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    startRtt(_arg027, _arg15, _arg24, _arg1);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg028 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    stopRtt(_arg028, _arg1);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg029 = data.readString();
                    if (data.readInt() != 0) {
                        _arg16 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg16 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg25 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg25 = null;
                    }
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    respondToRttUpgradeRequest(_arg029, _arg16, _arg25, _arg1);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg030 = data.readString();
                    addParticipantWithConference(_arg030, data.readString());
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    Session.Info _arg031 = _arg1;
                    connectionServiceFocusLost(_arg031);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    Session.Info _arg032 = _arg1;
                    connectionServiceFocusGained(_arg032);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg033 = data.readString();
                    if (data.readInt() != 0) {
                        _arg17 = ConnectionRequest.CREATOR.createFromParcel(data);
                    } else {
                        _arg17 = null;
                    }
                    int _arg26 = data.readInt();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    handoverFailed(_arg033, _arg17, _arg26, _arg1);
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg034 = data.readString();
                    _arg1 = data.readInt() != 0 ? Session.Info.CREATOR.createFromParcel(data) : null;
                    handoverComplete(_arg034, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IConnectionService {
            public static IConnectionService sDefaultImpl;
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

            @Override // com.android.internal.telecom.IConnectionService
            public void addConnectionServiceAdapter(IConnectionServiceAdapter adapter, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(adapter != null ? adapter.asBinder() : null);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addConnectionServiceAdapter(adapter, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void removeConnectionServiceAdapter(IConnectionServiceAdapter adapter, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(adapter != null ? adapter.asBinder() : null);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeConnectionServiceAdapter(adapter, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnection(PhoneAccountHandle connectionManagerPhoneAccount, String callId, ConnectionRequest request, boolean isIncoming, boolean isUnknown, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (connectionManagerPhoneAccount != null) {
                        _data.writeInt(1);
                        connectionManagerPhoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(callId);
                        if (request != null) {
                            _data.writeInt(1);
                            request.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(isIncoming ? 1 : 0);
                        try {
                            _data.writeInt(isUnknown ? 1 : 0);
                            if (sessionInfo != null) {
                                _data.writeInt(1);
                                sessionInfo.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        boolean _status = this.mRemote.transact(3, _data, null, 1);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().createConnection(connectionManagerPhoneAccount, callId, request, isIncoming, isUnknown, sessionInfo);
                            _data.recycle();
                            return;
                        }
                        _data.recycle();
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionComplete(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createConnectionComplete(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionFailed(PhoneAccountHandle connectionManagerPhoneAccount, String callId, ConnectionRequest request, boolean isIncoming, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (connectionManagerPhoneAccount != null) {
                        _data.writeInt(1);
                        connectionManagerPhoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isIncoming ? 1 : 0);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createConnectionFailed(connectionManagerPhoneAccount, callId, request, isIncoming, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void abort(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().abort(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answerVideo(String callId, int videoState, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(videoState);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().answerVideo(callId, videoState, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answer(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().answer(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void deflect(String callId, Uri address, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (address != null) {
                        _data.writeInt(1);
                        address.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deflect(callId, address, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void reject(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reject(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithMessage(String callId, String message, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(message);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rejectWithMessage(callId, message, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void disconnect(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void silence(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().silence(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void hold(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hold(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void unhold(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unhold(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallAudioStateChanged(String activeCallId, CallAudioState callAudioState, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(activeCallId);
                    if (callAudioState != null) {
                        _data.writeInt(1);
                        callAudioState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallAudioStateChanged(activeCallId, callAudioState, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void playDtmfTone(String callId, char digit, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(digit);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playDtmfTone(callId, digit, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopDtmfTone(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDtmfTone(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void conference(String conferenceCallId, String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(conferenceCallId);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().conference(conferenceCallId, callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void splitFromConference(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().splitFromConference(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void mergeConference(String conferenceCallId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(conferenceCallId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().mergeConference(conferenceCallId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void swapConference(String conferenceCallId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(conferenceCallId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().swapConference(conferenceCallId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onPostDialContinue(String callId, boolean proceed, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(proceed ? 1 : 0);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPostDialContinue(callId, proceed, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void pullExternalCall(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pullExternalCall(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void sendCallEvent(String callId, String event, Bundle extras, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(event);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendCallEvent(callId, event, extras, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onExtrasChanged(String callId, Bundle extras, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onExtrasChanged(callId, extras, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void startRtt(String callId, ParcelFileDescriptor fromInCall, ParcelFileDescriptor toInCall, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (fromInCall != null) {
                        _data.writeInt(1);
                        fromInCall.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (toInCall != null) {
                        _data.writeInt(1);
                        toInCall.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startRtt(callId, fromInCall, toInCall, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopRtt(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(28, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopRtt(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void respondToRttUpgradeRequest(String callId, ParcelFileDescriptor fromInCall, ParcelFileDescriptor toInCall, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (fromInCall != null) {
                        _data.writeInt(1);
                        fromInCall.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (toInCall != null) {
                        _data.writeInt(1);
                        toInCall.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(29, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().respondToRttUpgradeRequest(callId, fromInCall, toInCall, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void addParticipantWithConference(String callId, String recipients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(recipients);
                    boolean _status = this.mRemote.transact(30, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addParticipantWithConference(callId, recipients);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusLost(Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(31, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectionServiceFocusLost(sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusGained(Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(32, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectionServiceFocusGained(sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverFailed(String callId, ConnectionRequest request, int error, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(error);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(33, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handoverFailed(callId, request, error, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverComplete(String callId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(34, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handoverComplete(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IConnectionService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IConnectionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
