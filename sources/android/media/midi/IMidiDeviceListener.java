package android.media.midi;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IMidiDeviceListener extends IInterface {
    void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IMidiDeviceListener {
        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceAdded(MidiDeviceInfo device) throws RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceRemoved(MidiDeviceInfo device) throws RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceStatusChanged(MidiDeviceStatus status) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMidiDeviceListener {
        private static final String DESCRIPTOR = "android.media.midi.IMidiDeviceListener";
        static final int TRANSACTION_onDeviceAdded = 1;
        static final int TRANSACTION_onDeviceRemoved = 2;
        static final int TRANSACTION_onDeviceStatusChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMidiDeviceListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMidiDeviceListener)) {
                return (IMidiDeviceListener) iin;
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
                    return "onDeviceAdded";
                case 2:
                    return "onDeviceRemoved";
                case 3:
                    return "onDeviceStatusChanged";
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
                    MidiDeviceInfo _arg0 = data.readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel(data) : null;
                    onDeviceAdded(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    MidiDeviceInfo _arg02 = data.readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel(data) : null;
                    onDeviceRemoved(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    MidiDeviceStatus _arg03 = data.readInt() != 0 ? MidiDeviceStatus.CREATOR.createFromParcel(data) : null;
                    onDeviceStatusChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IMidiDeviceListener {
            public static IMidiDeviceListener sDefaultImpl;
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

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceAdded(MidiDeviceInfo device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceAdded(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceRemoved(MidiDeviceInfo device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceRemoved(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceStatusChanged(MidiDeviceStatus status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (status != null) {
                        _data.writeInt(1);
                        status.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceStatusChanged(status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMidiDeviceListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMidiDeviceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
