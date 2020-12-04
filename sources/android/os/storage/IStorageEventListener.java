package android.os.storage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStorageEventListener extends IInterface {
    void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException;

    void onDiskScanned(DiskInfo diskInfo, int i) throws RemoteException;

    void onStorageStateChanged(String str, String str2, String str3) throws RemoteException;

    void onUsbMassStorageConnectionChanged(boolean z) throws RemoteException;

    void onVolumeForgotten(String str) throws RemoteException;

    void onVolumeRecordChanged(VolumeRecord volumeRecord) throws RemoteException;

    void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) throws RemoteException;

    public static class Default implements IStorageEventListener {
        public void onUsbMassStorageConnectionChanged(boolean connected) throws RemoteException {
        }

        public void onStorageStateChanged(String path, String oldState, String newState) throws RemoteException {
        }

        public void onVolumeStateChanged(VolumeInfo vol, int oldState, int newState) throws RemoteException {
        }

        public void onVolumeRecordChanged(VolumeRecord rec) throws RemoteException {
        }

        public void onVolumeForgotten(String fsUuid) throws RemoteException {
        }

        public void onDiskScanned(DiskInfo disk, int volumeCount) throws RemoteException {
        }

        public void onDiskDestroyed(DiskInfo disk) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStorageEventListener {
        private static final String DESCRIPTOR = "android.os.storage.IStorageEventListener";
        static final int TRANSACTION_onDiskDestroyed = 7;
        static final int TRANSACTION_onDiskScanned = 6;
        static final int TRANSACTION_onStorageStateChanged = 2;
        static final int TRANSACTION_onUsbMassStorageConnectionChanged = 1;
        static final int TRANSACTION_onVolumeForgotten = 5;
        static final int TRANSACTION_onVolumeRecordChanged = 4;
        static final int TRANSACTION_onVolumeStateChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStorageEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IStorageEventListener)) {
                return new Proxy(obj);
            }
            return (IStorageEventListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onUsbMassStorageConnectionChanged";
                case 2:
                    return "onStorageStateChanged";
                case 3:
                    return "onVolumeStateChanged";
                case 4:
                    return "onVolumeRecordChanged";
                case 5:
                    return "onVolumeForgotten";
                case 6:
                    return "onDiskScanned";
                case 7:
                    return "onDiskDestroyed";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.os.storage.VolumeInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: android.os.storage.VolumeRecord} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.os.storage.DiskInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.os.storage.DiskInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: type inference failed for: r1v26 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.os.storage.IStorageEventListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00a7
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x0097;
                    case 2: goto L_0x0084;
                    case 3: goto L_0x0065;
                    case 4: goto L_0x004e;
                    case 5: goto L_0x0043;
                    case 6: goto L_0x0028;
                    case 7: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.os.storage.DiskInfo> r1 = android.os.storage.DiskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.storage.DiskInfo r1 = (android.os.storage.DiskInfo) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r5.onDiskDestroyed(r1)
                return r2
            L_0x0028:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.os.storage.DiskInfo> r1 = android.os.storage.DiskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.storage.DiskInfo r1 = (android.os.storage.DiskInfo) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                int r3 = r7.readInt()
                r5.onDiskScanned(r1, r3)
                return r2
            L_0x0043:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                r5.onVolumeForgotten(r1)
                return r2
            L_0x004e:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0060
                android.os.Parcelable$Creator<android.os.storage.VolumeRecord> r1 = android.os.storage.VolumeRecord.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.storage.VolumeRecord r1 = (android.os.storage.VolumeRecord) r1
                goto L_0x0061
            L_0x0060:
            L_0x0061:
                r5.onVolumeRecordChanged(r1)
                return r2
            L_0x0065:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0077
                android.os.Parcelable$Creator<android.os.storage.VolumeInfo> r1 = android.os.storage.VolumeInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.storage.VolumeInfo r1 = (android.os.storage.VolumeInfo) r1
                goto L_0x0078
            L_0x0077:
            L_0x0078:
                int r3 = r7.readInt()
                int r4 = r7.readInt()
                r5.onVolumeStateChanged(r1, r3, r4)
                return r2
            L_0x0084:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                java.lang.String r3 = r7.readString()
                java.lang.String r4 = r7.readString()
                r5.onStorageStateChanged(r1, r3, r4)
                return r2
            L_0x0097:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x00a2
                r1 = r2
                goto L_0x00a3
            L_0x00a2:
                r1 = 0
            L_0x00a3:
                r5.onUsbMassStorageConnectionChanged(r1)
                return r2
            L_0x00a7:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.storage.IStorageEventListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IStorageEventListener {
            public static IStorageEventListener sDefaultImpl;
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

            public void onUsbMassStorageConnectionChanged(boolean connected) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(connected);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUsbMassStorageConnectionChanged(connected);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStorageStateChanged(String path, String oldState, String newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(oldState);
                    _data.writeString(newState);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStorageStateChanged(path, oldState, newState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVolumeStateChanged(VolumeInfo vol, int oldState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (vol != null) {
                        _data.writeInt(1);
                        vol.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(oldState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVolumeStateChanged(vol, oldState, newState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVolumeRecordChanged(VolumeRecord rec) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rec != null) {
                        _data.writeInt(1);
                        rec.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVolumeRecordChanged(rec);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVolumeForgotten(String fsUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVolumeForgotten(fsUuid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDiskScanned(DiskInfo disk, int volumeCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (disk != null) {
                        _data.writeInt(1);
                        disk.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(volumeCount);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDiskScanned(disk, volumeCount);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDiskDestroyed(DiskInfo disk) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (disk != null) {
                        _data.writeInt(1);
                        disk.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDiskDestroyed(disk);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IStorageEventListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IStorageEventListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
