package android.media.audiopolicy;

import android.media.AudioFocusInfo;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IAudioPolicyCallback extends IInterface {
    void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException;

    void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int i) throws RemoteException;

    void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean z) throws RemoteException;

    void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int i) throws RemoteException;

    void notifyMixStateUpdate(String str, int i) throws RemoteException;

    void notifyVolumeAdjust(int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IAudioPolicyCallback {
        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusGrant(AudioFocusInfo afi, int requestResult) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusLoss(AudioFocusInfo afi, boolean wasNotified) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusRequest(AudioFocusInfo afi, int requestResult) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusAbandon(AudioFocusInfo afi) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyMixStateUpdate(String regId, int state) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyVolumeAdjust(int adjustment) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
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
            if (iin != null && (iin instanceof IAudioPolicyCallback)) {
                return (IAudioPolicyCallback) iin;
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            AudioFocusInfo _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel(data) : null;
                    int _arg1 = data.readInt();
                    notifyAudioFocusGrant(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel(data) : null;
                    boolean _arg12 = data.readInt() != 0;
                    notifyAudioFocusLoss(_arg0, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel(data) : null;
                    int _arg13 = data.readInt();
                    notifyAudioFocusRequest(_arg0, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel(data) : null;
                    notifyAudioFocusAbandon(_arg0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    int _arg14 = data.readInt();
                    notifyMixStateUpdate(_arg02, _arg14);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    notifyVolumeAdjust(data.readInt());
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IAudioPolicyCallback {
            public static IAudioPolicyCallback sDefaultImpl;
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

            @Override // android.media.audiopolicy.IAudioPolicyCallback
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
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusGrant(afi, requestResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
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
                    _data.writeInt(wasNotified ? 1 : 0);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusLoss(afi, wasNotified);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
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
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusRequest(afi, requestResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
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
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusAbandon(afi);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyMixStateUpdate(String regId, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(regId);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyMixStateUpdate(regId, state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyVolumeAdjust(int adjustment) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(adjustment);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyVolumeAdjust(adjustment);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAudioPolicyCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAudioPolicyCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
