package android.media.p006tv;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.p006tv.ITvInputServiceCallback;
import android.media.p006tv.ITvInputSessionCallback;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.InputChannel;

/* renamed from: android.media.tv.ITvInputService */
/* loaded from: classes3.dex */
public interface ITvInputService extends IInterface {
    void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str) throws RemoteException;

    void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str) throws RemoteException;

    void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException;

    void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException;

    void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException;

    void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException;

    /* renamed from: android.media.tv.ITvInputService$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ITvInputService {
        @Override // android.media.p006tv.ITvInputService
        public void registerCallback(ITvInputServiceCallback callback) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void unregisterCallback(ITvInputServiceCallback callback) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void createSession(InputChannel channel, ITvInputSessionCallback callback, String inputId) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void createRecordingSession(ITvInputSessionCallback callback, String inputId) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void notifyHardwareAdded(TvInputHardwareInfo hardwareInfo) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void notifyHardwareRemoved(TvInputHardwareInfo hardwareInfo) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void notifyHdmiDeviceAdded(HdmiDeviceInfo deviceInfo) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputService
        public void notifyHdmiDeviceRemoved(HdmiDeviceInfo deviceInfo) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.media.tv.ITvInputService$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ITvInputService {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputService";
        static final int TRANSACTION_createRecordingSession = 4;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_notifyHardwareAdded = 5;
        static final int TRANSACTION_notifyHardwareRemoved = 6;
        static final int TRANSACTION_notifyHdmiDeviceAdded = 7;
        static final int TRANSACTION_notifyHdmiDeviceRemoved = 8;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInputService)) {
                return (ITvInputService) iin;
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
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "createSession";
                case 4:
                    return "createRecordingSession";
                case 5:
                    return "notifyHardwareAdded";
                case 6:
                    return "notifyHardwareRemoved";
                case 7:
                    return "notifyHdmiDeviceAdded";
                case 8:
                    return "notifyHdmiDeviceRemoved";
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
                    ITvInputServiceCallback _arg0 = ITvInputServiceCallback.Stub.asInterface(data.readStrongBinder());
                    registerCallback(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ITvInputServiceCallback _arg02 = ITvInputServiceCallback.Stub.asInterface(data.readStrongBinder());
                    unregisterCallback(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    InputChannel _arg03 = data.readInt() != 0 ? InputChannel.CREATOR.createFromParcel(data) : null;
                    ITvInputSessionCallback _arg1 = ITvInputSessionCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg2 = data.readString();
                    createSession(_arg03, _arg1, _arg2);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    ITvInputSessionCallback _arg04 = ITvInputSessionCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg12 = data.readString();
                    createRecordingSession(_arg04, _arg12);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    TvInputHardwareInfo _arg05 = data.readInt() != 0 ? TvInputHardwareInfo.CREATOR.createFromParcel(data) : null;
                    notifyHardwareAdded(_arg05);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    TvInputHardwareInfo _arg06 = data.readInt() != 0 ? TvInputHardwareInfo.CREATOR.createFromParcel(data) : null;
                    notifyHardwareRemoved(_arg06);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    HdmiDeviceInfo _arg07 = data.readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel(data) : null;
                    notifyHdmiDeviceAdded(_arg07);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    HdmiDeviceInfo _arg08 = data.readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel(data) : null;
                    notifyHdmiDeviceRemoved(_arg08);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.media.tv.ITvInputService$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ITvInputService {
            public static ITvInputService sDefaultImpl;
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

            @Override // android.media.p006tv.ITvInputService
            public void registerCallback(ITvInputServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void unregisterCallback(ITvInputServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void createSession(InputChannel channel, ITvInputSessionCallback callback, String inputId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(inputId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createSession(channel, callback, inputId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void createRecordingSession(ITvInputSessionCallback callback, String inputId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(inputId);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createRecordingSession(callback, inputId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void notifyHardwareAdded(TvInputHardwareInfo hardwareInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hardwareInfo != null) {
                        _data.writeInt(1);
                        hardwareInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHardwareAdded(hardwareInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void notifyHardwareRemoved(TvInputHardwareInfo hardwareInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hardwareInfo != null) {
                        _data.writeInt(1);
                        hardwareInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHardwareRemoved(hardwareInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo deviceInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (deviceInfo != null) {
                        _data.writeInt(1);
                        deviceInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHdmiDeviceAdded(deviceInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputService
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo deviceInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (deviceInfo != null) {
                        _data.writeInt(1);
                        deviceInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHdmiDeviceRemoved(deviceInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ITvInputService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}