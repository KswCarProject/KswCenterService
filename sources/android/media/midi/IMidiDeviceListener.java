package android.media.midi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMidiDeviceListener extends IInterface {
    void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException;

    public static class Default implements IMidiDeviceListener {
        public void onDeviceAdded(MidiDeviceInfo device) throws RemoteException {
        }

        public void onDeviceRemoved(MidiDeviceInfo device) throws RemoteException {
        }

        public void onDeviceStatusChanged(MidiDeviceStatus status) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IMidiDeviceListener)) {
                return new Proxy(obj);
            }
            return (IMidiDeviceListener) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.media.midi.MidiDeviceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.media.midi.MidiDeviceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.media.midi.MidiDeviceStatus} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: type inference failed for: r1v17 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.media.midi.IMidiDeviceListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x0056
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x003f;
                    case 2: goto L_0x0028;
                    case 3: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.media.midi.MidiDeviceStatus> r1 = android.media.midi.MidiDeviceStatus.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.media.midi.MidiDeviceStatus r1 = (android.media.midi.MidiDeviceStatus) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r4.onDeviceStatusChanged(r1)
                return r2
            L_0x0028:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.media.midi.MidiDeviceInfo> r1 = android.media.midi.MidiDeviceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.media.midi.MidiDeviceInfo r1 = (android.media.midi.MidiDeviceInfo) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                r4.onDeviceRemoved(r1)
                return r2
            L_0x003f:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0051
                android.os.Parcelable$Creator<android.media.midi.MidiDeviceInfo> r1 = android.media.midi.MidiDeviceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.media.midi.MidiDeviceInfo r1 = (android.media.midi.MidiDeviceInfo) r1
                goto L_0x0052
            L_0x0051:
            L_0x0052:
                r4.onDeviceAdded(r1)
                return r2
            L_0x0056:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.midi.IMidiDeviceListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMidiDeviceListener {
            public static IMidiDeviceListener sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDeviceAdded(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDeviceRemoved(device);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDeviceStatusChanged(status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMidiDeviceListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMidiDeviceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
