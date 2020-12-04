package android.media.audiopolicy;

import android.media.AudioFocusInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAudioPolicyCallback extends IInterface {
    void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException;

    void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int i) throws RemoteException;

    void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean z) throws RemoteException;

    void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int i) throws RemoteException;

    void notifyMixStateUpdate(String str, int i) throws RemoteException;

    void notifyVolumeAdjust(int i) throws RemoteException;

    public static class Default implements IAudioPolicyCallback {
        public void notifyAudioFocusGrant(AudioFocusInfo afi, int requestResult) throws RemoteException {
        }

        public void notifyAudioFocusLoss(AudioFocusInfo afi, boolean wasNotified) throws RemoteException {
        }

        public void notifyAudioFocusRequest(AudioFocusInfo afi, int requestResult) throws RemoteException {
        }

        public void notifyAudioFocusAbandon(AudioFocusInfo afi) throws RemoteException {
        }

        public void notifyMixStateUpdate(String regId, int state) throws RemoteException {
        }

        public void notifyVolumeAdjust(int adjustment) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAudioPolicyCallback {
        private static final String DESCRIPTOR = "android.media.audiopolicy.IAudioPolicyCallback";
        static final int TRANSACTION_notifyAudioFocusAbandon = 4;
        static final int TRANSACTION_notifyAudioFocusGrant = 1;
        static final int TRANSACTION_notifyAudioFocusLoss = 2;
        static final int TRANSACTION_notifyAudioFocusRequest = 3;
        static final int TRANSACTION_notifyMixStateUpdate = 5;
        static final int TRANSACTION_notifyVolumeAdjust = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAudioPolicyCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAudioPolicyCallback)) {
                return new Proxy(obj);
            }
            return (IAudioPolicyCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "notifyAudioFocusGrant";
                case 2:
                    return "notifyAudioFocusLoss";
                case 3:
                    return "notifyAudioFocusRequest";
                case 4:
                    return "notifyAudioFocusAbandon";
                case 5:
                    return "notifyMixStateUpdate";
                case 6:
                    return "notifyVolumeAdjust";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                AudioFocusInfo _arg0 = null;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = AudioFocusInfo.CREATOR.createFromParcel(data);
                        }
                        notifyAudioFocusGrant(_arg0, data.readInt());
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = AudioFocusInfo.CREATOR.createFromParcel(data);
                        }
                        notifyAudioFocusLoss(_arg0, data.readInt() != 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = AudioFocusInfo.CREATOR.createFromParcel(data);
                        }
                        notifyAudioFocusRequest(_arg0, data.readInt());
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = AudioFocusInfo.CREATOR.createFromParcel(data);
                        }
                        notifyAudioFocusAbandon(_arg0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        notifyMixStateUpdate(data.readString(), data.readInt());
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        notifyVolumeAdjust(data.readInt());
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAudioPolicyCallback {
            public static IAudioPolicyCallback sDefaultImpl;
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

            public void notifyAudioFocusGrant(AudioFocusInfo afi, int requestResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (afi != null) {
                        _data.writeInt(1);
                        afi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(requestResult);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyAudioFocusGrant(afi, requestResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyAudioFocusLoss(AudioFocusInfo afi, boolean wasNotified) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (afi != null) {
                        _data.writeInt(1);
                        afi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(wasNotified);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyAudioFocusLoss(afi, wasNotified);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyAudioFocusRequest(AudioFocusInfo afi, int requestResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (afi != null) {
                        _data.writeInt(1);
                        afi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(requestResult);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyAudioFocusRequest(afi, requestResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyAudioFocusAbandon(AudioFocusInfo afi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (afi != null) {
                        _data.writeInt(1);
                        afi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyAudioFocusAbandon(afi);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyMixStateUpdate(String regId, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(regId);
                    _data.writeInt(state);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyMixStateUpdate(regId, state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyVolumeAdjust(int adjustment) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(adjustment);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyVolumeAdjust(adjustment);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAudioPolicyCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAudioPolicyCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
