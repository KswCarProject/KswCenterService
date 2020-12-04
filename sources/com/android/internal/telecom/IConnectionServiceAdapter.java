package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.ConnectionRequest;
import android.telecom.DisconnectCause;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telecom.ParcelableConnection;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.List;

public interface IConnectionServiceAdapter extends IInterface {
    void addConferenceCall(String str, ParcelableConference parcelableConference, Session.Info info) throws RemoteException;

    void addExistingConnection(String str, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException;

    void handleCreateConnectionComplete(String str, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException;

    void onConnectionEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException;

    void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException;

    void onPhoneAccountChanged(String str, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException;

    void onPostDialChar(String str, char c, Session.Info info) throws RemoteException;

    void onPostDialWait(String str, String str2, Session.Info info) throws RemoteException;

    void onRemoteRttRequest(String str, Session.Info info) throws RemoteException;

    void onRttInitiationFailure(String str, int i, Session.Info info) throws RemoteException;

    void onRttInitiationSuccess(String str, Session.Info info) throws RemoteException;

    void onRttSessionRemotelyTerminated(String str, Session.Info info) throws RemoteException;

    void putExtras(String str, Bundle bundle, Session.Info info) throws RemoteException;

    void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String str, Session.Info info) throws RemoteException;

    void removeCall(String str, Session.Info info) throws RemoteException;

    void removeExtras(String str, List<String> list, Session.Info info) throws RemoteException;

    void resetConnectionTime(String str, Session.Info info) throws RemoteException;

    void setActive(String str, Session.Info info) throws RemoteException;

    void setAddress(String str, Uri uri, int i, Session.Info info) throws RemoteException;

    void setAudioRoute(String str, int i, String str2, Session.Info info) throws RemoteException;

    void setCallerDisplayName(String str, String str2, int i, Session.Info info) throws RemoteException;

    void setConferenceMergeFailed(String str, Session.Info info) throws RemoteException;

    void setConferenceState(String str, boolean z, Session.Info info) throws RemoteException;

    void setConferenceableConnections(String str, List<String> list, Session.Info info) throws RemoteException;

    void setConnectionCapabilities(String str, int i, Session.Info info) throws RemoteException;

    void setConnectionProperties(String str, int i, Session.Info info) throws RemoteException;

    void setDialing(String str, Session.Info info) throws RemoteException;

    void setDisconnected(String str, DisconnectCause disconnectCause, Session.Info info) throws RemoteException;

    void setIsConferenced(String str, String str2, Session.Info info) throws RemoteException;

    void setIsVoipAudioMode(String str, boolean z, Session.Info info) throws RemoteException;

    void setOnHold(String str, Session.Info info) throws RemoteException;

    void setPulling(String str, Session.Info info) throws RemoteException;

    void setRingbackRequested(String str, boolean z, Session.Info info) throws RemoteException;

    void setRinging(String str, Session.Info info) throws RemoteException;

    void setStatusHints(String str, StatusHints statusHints, Session.Info info) throws RemoteException;

    void setVideoProvider(String str, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException;

    void setVideoState(String str, int i, Session.Info info) throws RemoteException;

    public static class Default implements IConnectionServiceAdapter {
        public void handleCreateConnectionComplete(String callId, ConnectionRequest request, ParcelableConnection connection, Session.Info sessionInfo) throws RemoteException {
        }

        public void setActive(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void setRinging(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void setDialing(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void setPulling(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void setDisconnected(String callId, DisconnectCause disconnectCause, Session.Info sessionInfo) throws RemoteException {
        }

        public void setOnHold(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void setRingbackRequested(String callId, boolean ringing, Session.Info sessionInfo) throws RemoteException {
        }

        public void setConnectionCapabilities(String callId, int connectionCapabilities, Session.Info sessionInfo) throws RemoteException {
        }

        public void setConnectionProperties(String callId, int connectionProperties, Session.Info sessionInfo) throws RemoteException {
        }

        public void setIsConferenced(String callId, String conferenceCallId, Session.Info sessionInfo) throws RemoteException {
        }

        public void setConferenceMergeFailed(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void addConferenceCall(String callId, ParcelableConference conference, Session.Info sessionInfo) throws RemoteException {
        }

        public void removeCall(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void onPostDialWait(String callId, String remaining, Session.Info sessionInfo) throws RemoteException {
        }

        public void onPostDialChar(String callId, char nextChar, Session.Info sessionInfo) throws RemoteException {
        }

        public void queryRemoteConnectionServices(RemoteServiceCallback callback, String callingPackage, Session.Info sessionInfo) throws RemoteException {
        }

        public void setVideoProvider(String callId, IVideoProvider videoProvider, Session.Info sessionInfo) throws RemoteException {
        }

        public void setVideoState(String callId, int videoState, Session.Info sessionInfo) throws RemoteException {
        }

        public void setIsVoipAudioMode(String callId, boolean isVoip, Session.Info sessionInfo) throws RemoteException {
        }

        public void setStatusHints(String callId, StatusHints statusHints, Session.Info sessionInfo) throws RemoteException {
        }

        public void setAddress(String callId, Uri address, int presentation, Session.Info sessionInfo) throws RemoteException {
        }

        public void setCallerDisplayName(String callId, String callerDisplayName, int presentation, Session.Info sessionInfo) throws RemoteException {
        }

        public void setConferenceableConnections(String callId, List<String> list, Session.Info sessionInfo) throws RemoteException {
        }

        public void addExistingConnection(String callId, ParcelableConnection connection, Session.Info sessionInfo) throws RemoteException {
        }

        public void putExtras(String callId, Bundle extras, Session.Info sessionInfo) throws RemoteException {
        }

        public void removeExtras(String callId, List<String> list, Session.Info sessionInfo) throws RemoteException {
        }

        public void setAudioRoute(String callId, int audioRoute, String bluetoothAddress, Session.Info sessionInfo) throws RemoteException {
        }

        public void onConnectionEvent(String callId, String event, Bundle extras, Session.Info sessionInfo) throws RemoteException {
        }

        public void onRttInitiationSuccess(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void onRttInitiationFailure(String callId, int reason, Session.Info sessionInfo) throws RemoteException {
        }

        public void onRttSessionRemotelyTerminated(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void onRemoteRttRequest(String callId, Session.Info sessionInfo) throws RemoteException {
        }

        public void onPhoneAccountChanged(String callId, PhoneAccountHandle pHandle, Session.Info sessionInfo) throws RemoteException {
        }

        public void onConnectionServiceFocusReleased(Session.Info sessionInfo) throws RemoteException {
        }

        public void resetConnectionTime(String callIdi, Session.Info sessionInfo) throws RemoteException {
        }

        public void setConferenceState(String callId, boolean isConference, Session.Info sessionInfo) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IConnectionServiceAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IConnectionServiceAdapter";
        static final int TRANSACTION_addConferenceCall = 13;
        static final int TRANSACTION_addExistingConnection = 25;
        static final int TRANSACTION_handleCreateConnectionComplete = 1;
        static final int TRANSACTION_onConnectionEvent = 29;
        static final int TRANSACTION_onConnectionServiceFocusReleased = 35;
        static final int TRANSACTION_onPhoneAccountChanged = 34;
        static final int TRANSACTION_onPostDialChar = 16;
        static final int TRANSACTION_onPostDialWait = 15;
        static final int TRANSACTION_onRemoteRttRequest = 33;
        static final int TRANSACTION_onRttInitiationFailure = 31;
        static final int TRANSACTION_onRttInitiationSuccess = 30;
        static final int TRANSACTION_onRttSessionRemotelyTerminated = 32;
        static final int TRANSACTION_putExtras = 26;
        static final int TRANSACTION_queryRemoteConnectionServices = 17;
        static final int TRANSACTION_removeCall = 14;
        static final int TRANSACTION_removeExtras = 27;
        static final int TRANSACTION_resetConnectionTime = 36;
        static final int TRANSACTION_setActive = 2;
        static final int TRANSACTION_setAddress = 22;
        static final int TRANSACTION_setAudioRoute = 28;
        static final int TRANSACTION_setCallerDisplayName = 23;
        static final int TRANSACTION_setConferenceMergeFailed = 12;
        static final int TRANSACTION_setConferenceState = 37;
        static final int TRANSACTION_setConferenceableConnections = 24;
        static final int TRANSACTION_setConnectionCapabilities = 9;
        static final int TRANSACTION_setConnectionProperties = 10;
        static final int TRANSACTION_setDialing = 4;
        static final int TRANSACTION_setDisconnected = 6;
        static final int TRANSACTION_setIsConferenced = 11;
        static final int TRANSACTION_setIsVoipAudioMode = 20;
        static final int TRANSACTION_setOnHold = 7;
        static final int TRANSACTION_setPulling = 5;
        static final int TRANSACTION_setRingbackRequested = 8;
        static final int TRANSACTION_setRinging = 3;
        static final int TRANSACTION_setStatusHints = 21;
        static final int TRANSACTION_setVideoProvider = 18;
        static final int TRANSACTION_setVideoState = 19;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IConnectionServiceAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IConnectionServiceAdapter)) {
                return new Proxy(obj);
            }
            return (IConnectionServiceAdapter) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "handleCreateConnectionComplete";
                case 2:
                    return "setActive";
                case 3:
                    return "setRinging";
                case 4:
                    return "setDialing";
                case 5:
                    return "setPulling";
                case 6:
                    return "setDisconnected";
                case 7:
                    return "setOnHold";
                case 8:
                    return "setRingbackRequested";
                case 9:
                    return "setConnectionCapabilities";
                case 10:
                    return "setConnectionProperties";
                case 11:
                    return "setIsConferenced";
                case 12:
                    return "setConferenceMergeFailed";
                case 13:
                    return "addConferenceCall";
                case 14:
                    return "removeCall";
                case 15:
                    return "onPostDialWait";
                case 16:
                    return "onPostDialChar";
                case 17:
                    return "queryRemoteConnectionServices";
                case 18:
                    return "setVideoProvider";
                case 19:
                    return "setVideoState";
                case 20:
                    return "setIsVoipAudioMode";
                case 21:
                    return "setStatusHints";
                case 22:
                    return "setAddress";
                case 23:
                    return "setCallerDisplayName";
                case 24:
                    return "setConferenceableConnections";
                case 25:
                    return "addExistingConnection";
                case 26:
                    return "putExtras";
                case 27:
                    return "removeExtras";
                case 28:
                    return "setAudioRoute";
                case 29:
                    return "onConnectionEvent";
                case 30:
                    return "onRttInitiationSuccess";
                case 31:
                    return "onRttInitiationFailure";
                case 32:
                    return "onRttSessionRemotelyTerminated";
                case 33:
                    return "onRemoteRttRequest";
                case 34:
                    return "onPhoneAccountChanged";
                case 35:
                    return "onConnectionServiceFocusReleased";
                case 36:
                    return "resetConnectionTime";
                case 37:
                    return "setConferenceState";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ConnectionRequest _arg1;
            ParcelableConnection _arg2;
            DisconnectCause _arg12;
            ParcelableConference _arg13;
            StatusHints _arg14;
            Uri _arg15;
            ParcelableConnection _arg16;
            Bundle _arg17;
            Bundle _arg22;
            PhoneAccountHandle _arg18;
            if (code != 1598968902) {
                boolean _arg19 = false;
                Session.Info _arg23 = null;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = ConnectionRequest.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg2 = ParcelableConnection.CREATOR.createFromParcel(data);
                        } else {
                            _arg2 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        handleCreateConnectionComplete(_arg0, _arg1, _arg2, _arg23);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setActive(_arg02, _arg23);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setRinging(_arg03, _arg23);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setDialing(_arg04, _arg23);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg05 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setPulling(_arg05, _arg23);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg06 = data.readString();
                        if (data.readInt() != 0) {
                            _arg12 = DisconnectCause.CREATOR.createFromParcel(data);
                        } else {
                            _arg12 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setDisconnected(_arg06, _arg12, _arg23);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg07 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setOnHold(_arg07, _arg23);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg08 = data.readString();
                        if (data.readInt() != 0) {
                            _arg19 = true;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setRingbackRequested(_arg08, _arg19, _arg23);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg09 = data.readString();
                        int _arg110 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setConnectionCapabilities(_arg09, _arg110, _arg23);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg010 = data.readString();
                        int _arg111 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setConnectionProperties(_arg010, _arg111, _arg23);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg011 = data.readString();
                        String _arg112 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setIsConferenced(_arg011, _arg112, _arg23);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg012 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setConferenceMergeFailed(_arg012, _arg23);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg013 = data.readString();
                        if (data.readInt() != 0) {
                            _arg13 = ParcelableConference.CREATOR.createFromParcel(data);
                        } else {
                            _arg13 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        addConferenceCall(_arg013, _arg13, _arg23);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg014 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        removeCall(_arg014, _arg23);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg015 = data.readString();
                        String _arg113 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onPostDialWait(_arg015, _arg113, _arg23);
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg016 = data.readString();
                        char _arg114 = (char) data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onPostDialChar(_arg016, _arg114, _arg23);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        RemoteServiceCallback _arg017 = RemoteServiceCallback.Stub.asInterface(data.readStrongBinder());
                        String _arg115 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        queryRemoteConnectionServices(_arg017, _arg115, _arg23);
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg018 = data.readString();
                        IVideoProvider _arg116 = IVideoProvider.Stub.asInterface(data.readStrongBinder());
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setVideoProvider(_arg018, _arg116, _arg23);
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg019 = data.readString();
                        int _arg117 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setVideoState(_arg019, _arg117, _arg23);
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg020 = data.readString();
                        if (data.readInt() != 0) {
                            _arg19 = true;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setIsVoipAudioMode(_arg020, _arg19, _arg23);
                        return true;
                    case 21:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg021 = data.readString();
                        if (data.readInt() != 0) {
                            _arg14 = StatusHints.CREATOR.createFromParcel(data);
                        } else {
                            _arg14 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setStatusHints(_arg021, _arg14, _arg23);
                        return true;
                    case 22:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg022 = data.readString();
                        if (data.readInt() != 0) {
                            _arg15 = Uri.CREATOR.createFromParcel(data);
                        } else {
                            _arg15 = null;
                        }
                        int _arg24 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setAddress(_arg022, _arg15, _arg24, _arg23);
                        return true;
                    case 23:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg023 = data.readString();
                        String _arg118 = data.readString();
                        int _arg25 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setCallerDisplayName(_arg023, _arg118, _arg25, _arg23);
                        return true;
                    case 24:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg024 = data.readString();
                        List<String> _arg119 = data.createStringArrayList();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setConferenceableConnections(_arg024, _arg119, _arg23);
                        return true;
                    case 25:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg025 = data.readString();
                        if (data.readInt() != 0) {
                            _arg16 = ParcelableConnection.CREATOR.createFromParcel(data);
                        } else {
                            _arg16 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        addExistingConnection(_arg025, _arg16, _arg23);
                        return true;
                    case 26:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg026 = data.readString();
                        if (data.readInt() != 0) {
                            _arg17 = Bundle.CREATOR.createFromParcel(data);
                        } else {
                            _arg17 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        putExtras(_arg026, _arg17, _arg23);
                        return true;
                    case 27:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg027 = data.readString();
                        List<String> _arg120 = data.createStringArrayList();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        removeExtras(_arg027, _arg120, _arg23);
                        return true;
                    case 28:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg028 = data.readString();
                        int _arg121 = data.readInt();
                        String _arg26 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setAudioRoute(_arg028, _arg121, _arg26, _arg23);
                        return true;
                    case 29:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg029 = data.readString();
                        String _arg122 = data.readString();
                        if (data.readInt() != 0) {
                            _arg22 = Bundle.CREATOR.createFromParcel(data);
                        } else {
                            _arg22 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onConnectionEvent(_arg029, _arg122, _arg22, _arg23);
                        return true;
                    case 30:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg030 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onRttInitiationSuccess(_arg030, _arg23);
                        return true;
                    case 31:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg031 = data.readString();
                        int _arg123 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onRttInitiationFailure(_arg031, _arg123, _arg23);
                        return true;
                    case 32:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg032 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onRttSessionRemotelyTerminated(_arg032, _arg23);
                        return true;
                    case 33:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg033 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onRemoteRttRequest(_arg033, _arg23);
                        return true;
                    case 34:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg034 = data.readString();
                        if (data.readInt() != 0) {
                            _arg18 = PhoneAccountHandle.CREATOR.createFromParcel(data);
                        } else {
                            _arg18 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onPhoneAccountChanged(_arg034, _arg18, _arg23);
                        return true;
                    case 35:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        onConnectionServiceFocusReleased(_arg23);
                        return true;
                    case 36:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg035 = data.readString();
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        resetConnectionTime(_arg035, _arg23);
                        return true;
                    case 37:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg036 = data.readString();
                        if (data.readInt() != 0) {
                            _arg19 = true;
                        }
                        if (data.readInt() != 0) {
                            _arg23 = Session.Info.CREATOR.createFromParcel(data);
                        }
                        setConferenceState(_arg036, _arg19, _arg23);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IConnectionServiceAdapter {
            public static IConnectionServiceAdapter sDefaultImpl;
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

            public void handleCreateConnectionComplete(String callId, ConnectionRequest request, ParcelableConnection connection, Session.Info sessionInfo) throws RemoteException {
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
                    if (connection != null) {
                        _data.writeInt(1);
                        connection.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().handleCreateConnectionComplete(callId, request, connection, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setActive(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setActive(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRinging(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRinging(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setDialing(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDialing(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPulling(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPulling(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setDisconnected(String callId, DisconnectCause disconnectCause, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (disconnectCause != null) {
                        _data.writeInt(1);
                        disconnectCause.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDisconnected(callId, disconnectCause, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setOnHold(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setOnHold(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRingbackRequested(String callId, boolean ringing, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(ringing);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRingbackRequested(callId, ringing, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setConnectionCapabilities(String callId, int connectionCapabilities, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(connectionCapabilities);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setConnectionCapabilities(callId, connectionCapabilities, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setConnectionProperties(String callId, int connectionProperties, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(connectionProperties);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setConnectionProperties(callId, connectionProperties, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setIsConferenced(String callId, String conferenceCallId, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(conferenceCallId);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setIsConferenced(callId, conferenceCallId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setConferenceMergeFailed(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setConferenceMergeFailed(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addConferenceCall(String callId, ParcelableConference conference, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (conference != null) {
                        _data.writeInt(1);
                        conference.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addConferenceCall(callId, conference, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeCall(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeCall(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPostDialWait(String callId, String remaining, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(remaining);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPostDialWait(callId, remaining, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPostDialChar(String callId, char nextChar, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(nextChar);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPostDialChar(callId, nextChar, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void queryRemoteConnectionServices(RemoteServiceCallback callback, String callingPackage, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callingPackage);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().queryRemoteConnectionServices(callback, callingPackage, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setVideoProvider(String callId, IVideoProvider videoProvider, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeStrongBinder(videoProvider != null ? videoProvider.asBinder() : null);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setVideoProvider(callId, videoProvider, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setVideoState(String callId, int videoState, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setVideoState(callId, videoState, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setIsVoipAudioMode(String callId, boolean isVoip, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(isVoip);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setIsVoipAudioMode(callId, isVoip, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setStatusHints(String callId, StatusHints statusHints, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (statusHints != null) {
                        _data.writeInt(1);
                        statusHints.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setStatusHints(callId, statusHints, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAddress(String callId, Uri address, int presentation, Session.Info sessionInfo) throws RemoteException {
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
                    _data.writeInt(presentation);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAddress(callId, address, presentation, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setCallerDisplayName(String callId, String callerDisplayName, int presentation, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(callerDisplayName);
                    _data.writeInt(presentation);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCallerDisplayName(callId, callerDisplayName, presentation, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setConferenceableConnections(String callId, List<String> conferenceableCallIds, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeStringList(conferenceableCallIds);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setConferenceableConnections(callId, conferenceableCallIds, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addExistingConnection(String callId, ParcelableConnection connection, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (connection != null) {
                        _data.writeInt(1);
                        connection.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addExistingConnection(callId, connection, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void putExtras(String callId, Bundle extras, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(26, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().putExtras(callId, extras, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeExtras(String callId, List<String> keys, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeStringList(keys);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeExtras(callId, keys, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAudioRoute(String callId, int audioRoute, String bluetoothAddress, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(audioRoute);
                    _data.writeString(bluetoothAddress);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(28, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAudioRoute(callId, audioRoute, bluetoothAddress, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onConnectionEvent(String callId, String event, Bundle extras, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(29, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnectionEvent(callId, event, extras, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRttInitiationSuccess(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(30, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRttInitiationSuccess(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRttInitiationFailure(String callId, int reason, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(reason);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRttInitiationFailure(callId, reason, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRttSessionRemotelyTerminated(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(32, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRttSessionRemotelyTerminated(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRemoteRttRequest(String callId, Session.Info sessionInfo) throws RemoteException {
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
                    if (this.mRemote.transact(33, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRemoteRttRequest(callId, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPhoneAccountChanged(String callId, PhoneAccountHandle pHandle, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (pHandle != null) {
                        _data.writeInt(1);
                        pHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(34, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPhoneAccountChanged(callId, pHandle, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onConnectionServiceFocusReleased(Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(35, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnectionServiceFocusReleased(sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void resetConnectionTime(String callIdi, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callIdi);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(36, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().resetConnectionTime(callIdi, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setConferenceState(String callId, boolean isConference, Session.Info sessionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(isConference);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(37, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setConferenceState(callId, isConference, sessionInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IConnectionServiceAdapter impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IConnectionServiceAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
