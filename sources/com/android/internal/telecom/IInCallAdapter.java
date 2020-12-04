package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;
import android.telephony.ims.ImsCallProfile;
import java.util.List;

public interface IInCallAdapter extends IInterface {
    void answerCall(String str, int i) throws RemoteException;

    void conference(String str, String str2) throws RemoteException;

    void deflectCall(String str, Uri uri) throws RemoteException;

    void disconnectCall(String str) throws RemoteException;

    void handoverTo(String str, PhoneAccountHandle phoneAccountHandle, int i, Bundle bundle) throws RemoteException;

    void holdCall(String str) throws RemoteException;

    void mergeConference(String str) throws RemoteException;

    void mute(boolean z) throws RemoteException;

    void phoneAccountSelected(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    void playDtmfTone(String str, char c) throws RemoteException;

    void postDialContinue(String str, boolean z) throws RemoteException;

    void pullExternalCall(String str) throws RemoteException;

    void putExtras(String str, Bundle bundle) throws RemoteException;

    void rejectCall(String str, boolean z, String str2) throws RemoteException;

    void removeExtras(String str, List<String> list) throws RemoteException;

    void respondToRttRequest(String str, int i, boolean z) throws RemoteException;

    void sendCallEvent(String str, String str2, int i, Bundle bundle) throws RemoteException;

    void sendRttRequest(String str) throws RemoteException;

    void setAudioRoute(int i, String str) throws RemoteException;

    void setRttMode(String str, int i) throws RemoteException;

    void splitFromConference(String str) throws RemoteException;

    void stopDtmfTone(String str) throws RemoteException;

    void stopRtt(String str) throws RemoteException;

    void swapConference(String str) throws RemoteException;

    void turnOffProximitySensor(boolean z) throws RemoteException;

    void turnOnProximitySensor() throws RemoteException;

    void unholdCall(String str) throws RemoteException;

    public static class Default implements IInCallAdapter {
        public void answerCall(String callId, int videoState) throws RemoteException {
        }

        public void deflectCall(String callId, Uri address) throws RemoteException {
        }

        public void rejectCall(String callId, boolean rejectWithMessage, String textMessage) throws RemoteException {
        }

        public void disconnectCall(String callId) throws RemoteException {
        }

        public void holdCall(String callId) throws RemoteException {
        }

        public void unholdCall(String callId) throws RemoteException {
        }

        public void mute(boolean shouldMute) throws RemoteException {
        }

        public void setAudioRoute(int route, String bluetoothAddress) throws RemoteException {
        }

        public void playDtmfTone(String callId, char digit) throws RemoteException {
        }

        public void stopDtmfTone(String callId) throws RemoteException {
        }

        public void postDialContinue(String callId, boolean proceed) throws RemoteException {
        }

        public void phoneAccountSelected(String callId, PhoneAccountHandle accountHandle, boolean setDefault) throws RemoteException {
        }

        public void conference(String callId, String otherCallId) throws RemoteException {
        }

        public void splitFromConference(String callId) throws RemoteException {
        }

        public void mergeConference(String callId) throws RemoteException {
        }

        public void swapConference(String callId) throws RemoteException {
        }

        public void turnOnProximitySensor() throws RemoteException {
        }

        public void turnOffProximitySensor(boolean screenOnImmediately) throws RemoteException {
        }

        public void pullExternalCall(String callId) throws RemoteException {
        }

        public void sendCallEvent(String callId, String event, int targetSdkVer, Bundle extras) throws RemoteException {
        }

        public void putExtras(String callId, Bundle extras) throws RemoteException {
        }

        public void removeExtras(String callId, List<String> list) throws RemoteException {
        }

        public void sendRttRequest(String callId) throws RemoteException {
        }

        public void respondToRttRequest(String callId, int id, boolean accept) throws RemoteException {
        }

        public void stopRtt(String callId) throws RemoteException {
        }

        public void setRttMode(String callId, int mode) throws RemoteException {
        }

        public void handoverTo(String callId, PhoneAccountHandle destAcct, int videoState, Bundle extras) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInCallAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IInCallAdapter";
        static final int TRANSACTION_answerCall = 1;
        static final int TRANSACTION_conference = 13;
        static final int TRANSACTION_deflectCall = 2;
        static final int TRANSACTION_disconnectCall = 4;
        static final int TRANSACTION_handoverTo = 27;
        static final int TRANSACTION_holdCall = 5;
        static final int TRANSACTION_mergeConference = 15;
        static final int TRANSACTION_mute = 7;
        static final int TRANSACTION_phoneAccountSelected = 12;
        static final int TRANSACTION_playDtmfTone = 9;
        static final int TRANSACTION_postDialContinue = 11;
        static final int TRANSACTION_pullExternalCall = 19;
        static final int TRANSACTION_putExtras = 21;
        static final int TRANSACTION_rejectCall = 3;
        static final int TRANSACTION_removeExtras = 22;
        static final int TRANSACTION_respondToRttRequest = 24;
        static final int TRANSACTION_sendCallEvent = 20;
        static final int TRANSACTION_sendRttRequest = 23;
        static final int TRANSACTION_setAudioRoute = 8;
        static final int TRANSACTION_setRttMode = 26;
        static final int TRANSACTION_splitFromConference = 14;
        static final int TRANSACTION_stopDtmfTone = 10;
        static final int TRANSACTION_stopRtt = 25;
        static final int TRANSACTION_swapConference = 16;
        static final int TRANSACTION_turnOffProximitySensor = 18;
        static final int TRANSACTION_turnOnProximitySensor = 17;
        static final int TRANSACTION_unholdCall = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInCallAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInCallAdapter)) {
                return new Proxy(obj);
            }
            return (IInCallAdapter) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "answerCall";
                case 2:
                    return "deflectCall";
                case 3:
                    return "rejectCall";
                case 4:
                    return "disconnectCall";
                case 5:
                    return "holdCall";
                case 6:
                    return "unholdCall";
                case 7:
                    return "mute";
                case 8:
                    return "setAudioRoute";
                case 9:
                    return "playDtmfTone";
                case 10:
                    return "stopDtmfTone";
                case 11:
                    return "postDialContinue";
                case 12:
                    return "phoneAccountSelected";
                case 13:
                    return ImsCallProfile.EXTRA_CONFERENCE;
                case 14:
                    return "splitFromConference";
                case 15:
                    return "mergeConference";
                case 16:
                    return "swapConference";
                case 17:
                    return "turnOnProximitySensor";
                case 18:
                    return "turnOffProximitySensor";
                case 19:
                    return "pullExternalCall";
                case 20:
                    return "sendCallEvent";
                case 21:
                    return "putExtras";
                case 22:
                    return "removeExtras";
                case 23:
                    return "sendRttRequest";
                case 24:
                    return "respondToRttRequest";
                case 25:
                    return "stopRtt";
                case 26:
                    return "setRttMode";
                case 27:
                    return "handoverTo";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v2, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.internal.telecom.IInCallAdapter"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x01ec
                r1 = 0
                r3 = 0
                switch(r8) {
                    case 1: goto L_0x01dd;
                    case 2: goto L_0x01c2;
                    case 3: goto L_0x01ab;
                    case 4: goto L_0x01a0;
                    case 5: goto L_0x0195;
                    case 6: goto L_0x018a;
                    case 7: goto L_0x017b;
                    case 8: goto L_0x016c;
                    case 9: goto L_0x015c;
                    case 10: goto L_0x0151;
                    case 11: goto L_0x013e;
                    case 12: goto L_0x011b;
                    case 13: goto L_0x010c;
                    case 14: goto L_0x0101;
                    case 15: goto L_0x00f6;
                    case 16: goto L_0x00eb;
                    case 17: goto L_0x00e4;
                    case 18: goto L_0x00d5;
                    case 19: goto L_0x00ca;
                    case 20: goto L_0x00a7;
                    case 21: goto L_0x008c;
                    case 22: goto L_0x007d;
                    case 23: goto L_0x0072;
                    case 24: goto L_0x005b;
                    case 25: goto L_0x0050;
                    case 26: goto L_0x0041;
                    case 27: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0012:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0028
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r4 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.telecom.PhoneAccountHandle r4 = (android.telecom.PhoneAccountHandle) r4
                goto L_0x0029
            L_0x0028:
                r4 = r3
            L_0x0029:
                int r5 = r9.readInt()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x003c
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x003d
            L_0x003c:
            L_0x003d:
                r7.handoverTo(r1, r4, r5, r3)
                return r2
            L_0x0041:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                int r3 = r9.readInt()
                r7.setRttMode(r1, r3)
                return r2
            L_0x0050:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.stopRtt(r1)
                return r2
            L_0x005b:
                r9.enforceInterface(r0)
                java.lang.String r3 = r9.readString()
                int r4 = r9.readInt()
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x006e
                r1 = r2
            L_0x006e:
                r7.respondToRttRequest(r3, r4, r1)
                return r2
            L_0x0072:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.sendRttRequest(r1)
                return r2
            L_0x007d:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                java.util.ArrayList r3 = r9.createStringArrayList()
                r7.removeExtras(r1, r3)
                return r2
            L_0x008c:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00a2
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x00a3
            L_0x00a2:
            L_0x00a3:
                r7.putExtras(r1, r3)
                return r2
            L_0x00a7:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                java.lang.String r4 = r9.readString()
                int r5 = r9.readInt()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x00c5
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x00c6
            L_0x00c5:
            L_0x00c6:
                r7.sendCallEvent(r1, r4, r5, r3)
                return r2
            L_0x00ca:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.pullExternalCall(r1)
                return r2
            L_0x00d5:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x00e0
                r1 = r2
            L_0x00e0:
                r7.turnOffProximitySensor(r1)
                return r2
            L_0x00e4:
                r9.enforceInterface(r0)
                r7.turnOnProximitySensor()
                return r2
            L_0x00eb:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.swapConference(r1)
                return r2
            L_0x00f6:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.mergeConference(r1)
                return r2
            L_0x0101:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.splitFromConference(r1)
                return r2
            L_0x010c:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                java.lang.String r3 = r9.readString()
                r7.conference(r1, r3)
                return r2
            L_0x011b:
                r9.enforceInterface(r0)
                java.lang.String r4 = r9.readString()
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0131
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r3 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x0132
            L_0x0131:
            L_0x0132:
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x013a
                r1 = r2
            L_0x013a:
                r7.phoneAccountSelected(r4, r3, r1)
                return r2
            L_0x013e:
                r9.enforceInterface(r0)
                java.lang.String r3 = r9.readString()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x014d
                r1 = r2
            L_0x014d:
                r7.postDialContinue(r3, r1)
                return r2
            L_0x0151:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.stopDtmfTone(r1)
                return r2
            L_0x015c:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                int r3 = r9.readInt()
                char r3 = (char) r3
                r7.playDtmfTone(r1, r3)
                return r2
            L_0x016c:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                java.lang.String r3 = r9.readString()
                r7.setAudioRoute(r1, r3)
                return r2
            L_0x017b:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0186
                r1 = r2
            L_0x0186:
                r7.mute(r1)
                return r2
            L_0x018a:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.unholdCall(r1)
                return r2
            L_0x0195:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.holdCall(r1)
                return r2
            L_0x01a0:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                r7.disconnectCall(r1)
                return r2
            L_0x01ab:
                r9.enforceInterface(r0)
                java.lang.String r3 = r9.readString()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x01ba
                r1 = r2
            L_0x01ba:
                java.lang.String r4 = r9.readString()
                r7.rejectCall(r3, r1, r4)
                return r2
            L_0x01c2:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x01d8
                android.os.Parcelable$Creator<android.net.Uri> r3 = android.net.Uri.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.net.Uri r3 = (android.net.Uri) r3
                goto L_0x01d9
            L_0x01d8:
            L_0x01d9:
                r7.deflectCall(r1, r3)
                return r2
            L_0x01dd:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                int r3 = r9.readInt()
                r7.answerCall(r1, r3)
                return r2
            L_0x01ec:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telecom.IInCallAdapter.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInCallAdapter {
            public static IInCallAdapter sDefaultImpl;
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

            public void answerCall(String callId, int videoState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(videoState);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().answerCall(callId, videoState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void deflectCall(String callId, Uri address) throws RemoteException {
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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().deflectCall(callId, address);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void rejectCall(String callId, boolean rejectWithMessage, String textMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(rejectWithMessage);
                    _data.writeString(textMessage);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().rejectCall(callId, rejectWithMessage, textMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void disconnectCall(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disconnectCall(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void holdCall(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().holdCall(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unholdCall(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unholdCall(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void mute(boolean shouldMute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(shouldMute);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().mute(shouldMute);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAudioRoute(int route, String bluetoothAddress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(route);
                    _data.writeString(bluetoothAddress);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAudioRoute(route, bluetoothAddress);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void playDtmfTone(String callId, char digit) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(digit);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playDtmfTone(callId, digit);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void stopDtmfTone(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopDtmfTone(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void postDialContinue(String callId, boolean proceed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(proceed);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().postDialContinue(callId, proceed);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void phoneAccountSelected(String callId, PhoneAccountHandle accountHandle, boolean setDefault) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(setDefault);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().phoneAccountSelected(callId, accountHandle, setDefault);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void conference(String callId, String otherCallId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(otherCallId);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().conference(callId, otherCallId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void splitFromConference(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().splitFromConference(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void mergeConference(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().mergeConference(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void swapConference(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().swapConference(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void turnOnProximitySensor() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().turnOnProximitySensor();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void turnOffProximitySensor(boolean screenOnImmediately) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(screenOnImmediately);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().turnOffProximitySensor(screenOnImmediately);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void pullExternalCall(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().pullExternalCall(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendCallEvent(String callId, String event, int targetSdkVer, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(event);
                    _data.writeInt(targetSdkVer);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendCallEvent(callId, event, targetSdkVer, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void putExtras(String callId, Bundle extras) throws RemoteException {
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
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().putExtras(callId, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeExtras(String callId, List<String> keys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeStringList(keys);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeExtras(callId, keys);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendRttRequest(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendRttRequest(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void respondToRttRequest(String callId, int id, boolean accept) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(id);
                    _data.writeInt(accept);
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().respondToRttRequest(callId, id, accept);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void stopRtt(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopRtt(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRttMode(String callId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(26, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRttMode(callId, mode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void handoverTo(String callId, PhoneAccountHandle destAcct, int videoState, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (destAcct != null) {
                        _data.writeInt(1);
                        destAcct.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(videoState);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().handoverTo(callId, destAcct, videoState, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInCallAdapter impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInCallAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
