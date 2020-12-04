package com.android.internal.telecom;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.ParcelableCall;

public interface IInCallService extends IInterface {
    void addCall(ParcelableCall parcelableCall) throws RemoteException;

    void bringToForeground(boolean z) throws RemoteException;

    void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException;

    void onCanAddCallChanged(boolean z) throws RemoteException;

    void onConnectionEvent(String str, String str2, Bundle bundle) throws RemoteException;

    void onHandoverComplete(String str) throws RemoteException;

    void onHandoverFailed(String str, int i) throws RemoteException;

    void onRttInitiationFailure(String str, int i) throws RemoteException;

    void onRttUpgradeRequest(String str, int i) throws RemoteException;

    void setInCallAdapter(IInCallAdapter iInCallAdapter) throws RemoteException;

    void setPostDial(String str, String str2) throws RemoteException;

    void setPostDialWait(String str, String str2) throws RemoteException;

    void silenceRinger() throws RemoteException;

    void updateCall(ParcelableCall parcelableCall) throws RemoteException;

    public static class Default implements IInCallService {
        public void setInCallAdapter(IInCallAdapter inCallAdapter) throws RemoteException {
        }

        public void addCall(ParcelableCall call) throws RemoteException {
        }

        public void updateCall(ParcelableCall call) throws RemoteException {
        }

        public void setPostDial(String callId, String remaining) throws RemoteException {
        }

        public void setPostDialWait(String callId, String remaining) throws RemoteException {
        }

        public void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException {
        }

        public void bringToForeground(boolean showDialpad) throws RemoteException {
        }

        public void onCanAddCallChanged(boolean canAddCall) throws RemoteException {
        }

        public void silenceRinger() throws RemoteException {
        }

        public void onConnectionEvent(String callId, String event, Bundle extras) throws RemoteException {
        }

        public void onRttUpgradeRequest(String callId, int id) throws RemoteException {
        }

        public void onRttInitiationFailure(String callId, int reason) throws RemoteException {
        }

        public void onHandoverFailed(String callId, int error) throws RemoteException {
        }

        public void onHandoverComplete(String callId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInCallService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IInCallService";
        static final int TRANSACTION_addCall = 2;
        static final int TRANSACTION_bringToForeground = 7;
        static final int TRANSACTION_onCallAudioStateChanged = 6;
        static final int TRANSACTION_onCanAddCallChanged = 8;
        static final int TRANSACTION_onConnectionEvent = 10;
        static final int TRANSACTION_onHandoverComplete = 14;
        static final int TRANSACTION_onHandoverFailed = 13;
        static final int TRANSACTION_onRttInitiationFailure = 12;
        static final int TRANSACTION_onRttUpgradeRequest = 11;
        static final int TRANSACTION_setInCallAdapter = 1;
        static final int TRANSACTION_setPostDial = 4;
        static final int TRANSACTION_setPostDialWait = 5;
        static final int TRANSACTION_silenceRinger = 9;
        static final int TRANSACTION_updateCall = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInCallService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IInCallService)) {
                return new Proxy(obj);
            }
            return (IInCallService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setInCallAdapter";
                case 2:
                    return "addCall";
                case 3:
                    return "updateCall";
                case 4:
                    return "setPostDial";
                case 5:
                    return "setPostDialWait";
                case 6:
                    return "onCallAudioStateChanged";
                case 7:
                    return "bringToForeground";
                case 8:
                    return "onCanAddCallChanged";
                case 9:
                    return "silenceRinger";
                case 10:
                    return "onConnectionEvent";
                case 11:
                    return "onRttUpgradeRequest";
                case 12:
                    return "onRttInitiationFailure";
                case 13:
                    return "onHandoverFailed";
                case 14:
                    return "onHandoverComplete";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.telecom.ParcelableCall} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.telecom.ParcelableCall} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.telecom.CallAudioState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r3v3, types: [android.telecom.ParcelableCall] */
        /* JADX WARNING: type inference failed for: r3v6, types: [android.telecom.ParcelableCall] */
        /* JADX WARNING: type inference failed for: r3v11, types: [android.telecom.CallAudioState] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.internal.telecom.IInCallService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0106
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x00f7;
                    case 2: goto L_0x00de;
                    case 3: goto L_0x00c5;
                    case 4: goto L_0x00b6;
                    case 5: goto L_0x00a7;
                    case 6: goto L_0x008e;
                    case 7: goto L_0x007f;
                    case 8: goto L_0x0070;
                    case 9: goto L_0x0069;
                    case 10: goto L_0x004a;
                    case 11: goto L_0x003b;
                    case 12: goto L_0x002c;
                    case 13: goto L_0x001d;
                    case 14: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.onHandoverComplete(r1)
                return r2
            L_0x001d:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.onHandoverFailed(r1, r3)
                return r2
            L_0x002c:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.onRttInitiationFailure(r1, r3)
                return r2
            L_0x003b:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.onRttUpgradeRequest(r1, r3)
                return r2
            L_0x004a:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r4 = r8.readString()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0064
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x0065
            L_0x0064:
            L_0x0065:
                r6.onConnectionEvent(r1, r4, r3)
                return r2
            L_0x0069:
                r8.enforceInterface(r0)
                r6.silenceRinger()
                return r2
            L_0x0070:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x007b
                r1 = r2
            L_0x007b:
                r6.onCanAddCallChanged(r1)
                return r2
            L_0x007f:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x008a
                r1 = r2
            L_0x008a:
                r6.bringToForeground(r1)
                return r2
            L_0x008e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00a1
                android.os.Parcelable$Creator<android.telecom.CallAudioState> r1 = android.telecom.CallAudioState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.CallAudioState r3 = (android.telecom.CallAudioState) r3
                goto L_0x00a2
            L_0x00a1:
            L_0x00a2:
                r1 = r3
                r6.onCallAudioStateChanged(r1)
                return r2
            L_0x00a7:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.setPostDialWait(r1, r3)
                return r2
            L_0x00b6:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.setPostDial(r1, r3)
                return r2
            L_0x00c5:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00d8
                android.os.Parcelable$Creator<android.telecom.ParcelableCall> r1 = android.telecom.ParcelableCall.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.ParcelableCall r3 = (android.telecom.ParcelableCall) r3
                goto L_0x00d9
            L_0x00d8:
            L_0x00d9:
                r1 = r3
                r6.updateCall(r1)
                return r2
            L_0x00de:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00f1
                android.os.Parcelable$Creator<android.telecom.ParcelableCall> r1 = android.telecom.ParcelableCall.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.ParcelableCall r3 = (android.telecom.ParcelableCall) r3
                goto L_0x00f2
            L_0x00f1:
            L_0x00f2:
                r1 = r3
                r6.addCall(r1)
                return r2
            L_0x00f7:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                com.android.internal.telecom.IInCallAdapter r1 = com.android.internal.telecom.IInCallAdapter.Stub.asInterface(r1)
                r6.setInCallAdapter(r1)
                return r2
            L_0x0106:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telecom.IInCallService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IInCallService {
            public static IInCallService sDefaultImpl;
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

            public void setInCallAdapter(IInCallAdapter inCallAdapter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(inCallAdapter != null ? inCallAdapter.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setInCallAdapter(inCallAdapter);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addCall(ParcelableCall call) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (call != null) {
                        _data.writeInt(1);
                        call.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addCall(call);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateCall(ParcelableCall call) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (call != null) {
                        _data.writeInt(1);
                        call.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateCall(call);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPostDial(String callId, String remaining) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(remaining);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPostDial(callId, remaining);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPostDialWait(String callId, String remaining) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(remaining);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPostDialWait(callId, remaining);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callAudioState != null) {
                        _data.writeInt(1);
                        callAudioState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCallAudioStateChanged(callAudioState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void bringToForeground(boolean showDialpad) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showDialpad);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().bringToForeground(showDialpad);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCanAddCallChanged(boolean canAddCall) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(canAddCall);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCanAddCallChanged(canAddCall);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void silenceRinger() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().silenceRinger();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onConnectionEvent(String callId, String event, Bundle extras) throws RemoteException {
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
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnectionEvent(callId, event, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRttUpgradeRequest(String callId, int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(id);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRttUpgradeRequest(callId, id);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRttInitiationFailure(String callId, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRttInitiationFailure(callId, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onHandoverFailed(String callId, int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(error);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onHandoverFailed(callId, error);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onHandoverComplete(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onHandoverComplete(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IInCallService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IInCallService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
