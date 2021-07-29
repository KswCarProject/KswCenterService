package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IGsiService extends IInterface {
    public static final int BOOT_STATUS_DISABLED = 1;
    public static final int BOOT_STATUS_ENABLED = 3;
    public static final int BOOT_STATUS_NOT_INSTALLED = 0;
    public static final int BOOT_STATUS_SINGLE_BOOT = 2;
    public static final int BOOT_STATUS_WILL_WIPE = 4;
    public static final int INSTALL_ERROR_FILE_SYSTEM_CLUTTERED = 3;
    public static final int INSTALL_ERROR_GENERIC = 1;
    public static final int INSTALL_ERROR_NO_SPACE = 2;
    public static final int INSTALL_OK = 0;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_NO_OPERATION = 0;
    public static final int STATUS_WORKING = 1;

    int beginGsiInstall(GsiInstallParams gsiInstallParams) throws RemoteException;

    boolean cancelGsiInstall() throws RemoteException;

    boolean commitGsiChunkFromMemory(byte[] bArr) throws RemoteException;

    boolean commitGsiChunkFromStream(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException;

    boolean disableGsiInstall() throws RemoteException;

    int getGsiBootStatus() throws RemoteException;

    GsiProgress getInstallProgress() throws RemoteException;

    String getInstalledGsiImageDir() throws RemoteException;

    long getUserdataImageSize() throws RemoteException;

    boolean isGsiEnabled() throws RemoteException;

    boolean isGsiInstallInProgress() throws RemoteException;

    boolean isGsiInstalled() throws RemoteException;

    boolean isGsiRunning() throws RemoteException;

    boolean removeGsiInstall() throws RemoteException;

    int setGsiBootable(boolean z) throws RemoteException;

    int startGsiInstall(long j, long j2, boolean z) throws RemoteException;

    int wipeGsiUserdata() throws RemoteException;

    public static class Default implements IGsiService {
        public int startGsiInstall(long gsiSize, long userdataSize, boolean wipeUserdata) throws RemoteException {
            return 0;
        }

        public boolean commitGsiChunkFromStream(ParcelFileDescriptor stream, long bytes) throws RemoteException {
            return false;
        }

        public GsiProgress getInstallProgress() throws RemoteException {
            return null;
        }

        public boolean commitGsiChunkFromMemory(byte[] bytes) throws RemoteException {
            return false;
        }

        public int setGsiBootable(boolean oneShot) throws RemoteException {
            return 0;
        }

        public boolean isGsiEnabled() throws RemoteException {
            return false;
        }

        public boolean cancelGsiInstall() throws RemoteException {
            return false;
        }

        public boolean isGsiInstallInProgress() throws RemoteException {
            return false;
        }

        public boolean removeGsiInstall() throws RemoteException {
            return false;
        }

        public boolean disableGsiInstall() throws RemoteException {
            return false;
        }

        public long getUserdataImageSize() throws RemoteException {
            return 0;
        }

        public boolean isGsiRunning() throws RemoteException {
            return false;
        }

        public boolean isGsiInstalled() throws RemoteException {
            return false;
        }

        public int getGsiBootStatus() throws RemoteException {
            return 0;
        }

        public String getInstalledGsiImageDir() throws RemoteException {
            return null;
        }

        public int beginGsiInstall(GsiInstallParams params) throws RemoteException {
            return 0;
        }

        public int wipeGsiUserdata() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGsiService {
        private static final String DESCRIPTOR = "android.gsi.IGsiService";
        static final int TRANSACTION_beginGsiInstall = 16;
        static final int TRANSACTION_cancelGsiInstall = 7;
        static final int TRANSACTION_commitGsiChunkFromMemory = 4;
        static final int TRANSACTION_commitGsiChunkFromStream = 2;
        static final int TRANSACTION_disableGsiInstall = 10;
        static final int TRANSACTION_getGsiBootStatus = 14;
        static final int TRANSACTION_getInstallProgress = 3;
        static final int TRANSACTION_getInstalledGsiImageDir = 15;
        static final int TRANSACTION_getUserdataImageSize = 11;
        static final int TRANSACTION_isGsiEnabled = 6;
        static final int TRANSACTION_isGsiInstallInProgress = 8;
        static final int TRANSACTION_isGsiInstalled = 13;
        static final int TRANSACTION_isGsiRunning = 12;
        static final int TRANSACTION_removeGsiInstall = 9;
        static final int TRANSACTION_setGsiBootable = 5;
        static final int TRANSACTION_startGsiInstall = 1;
        static final int TRANSACTION_wipeGsiUserdata = 17;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGsiService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IGsiService)) {
                return new Proxy(obj);
            }
            return (IGsiService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startGsiInstall";
                case 2:
                    return "commitGsiChunkFromStream";
                case 3:
                    return "getInstallProgress";
                case 4:
                    return "commitGsiChunkFromMemory";
                case 5:
                    return "setGsiBootable";
                case 6:
                    return "isGsiEnabled";
                case 7:
                    return "cancelGsiInstall";
                case 8:
                    return "isGsiInstallInProgress";
                case 9:
                    return "removeGsiInstall";
                case 10:
                    return "disableGsiInstall";
                case 11:
                    return "getUserdataImageSize";
                case 12:
                    return "isGsiRunning";
                case 13:
                    return "isGsiInstalled";
                case 14:
                    return "getGsiBootStatus";
                case 15:
                    return "getInstalledGsiImageDir";
                case 16:
                    return "beginGsiInstall";
                case 17:
                    return "wipeGsiUserdata";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.gsi.GsiInstallParams} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: type inference failed for: r0v30 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
            /*
                r16 = this;
                r6 = r16
                r7 = r17
                r8 = r18
                r9 = r19
                java.lang.String r10 = "android.gsi.IGsiService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x0157
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0134;
                    case 2: goto L_0x0112;
                    case 3: goto L_0x00fb;
                    case 4: goto L_0x00e9;
                    case 5: goto L_0x00d2;
                    case 6: goto L_0x00c4;
                    case 7: goto L_0x00b6;
                    case 8: goto L_0x00a8;
                    case 9: goto L_0x009a;
                    case 10: goto L_0x008c;
                    case 11: goto L_0x007e;
                    case 12: goto L_0x0070;
                    case 13: goto L_0x0062;
                    case 14: goto L_0x0054;
                    case 15: goto L_0x0046;
                    case 16: goto L_0x0028;
                    case 17: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r17, r18, r19, r20)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                int r0 = r16.wipeGsiUserdata()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0028:
                r8.enforceInterface(r10)
                int r1 = r18.readInt()
                if (r1 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.gsi.GsiInstallParams> r0 = android.gsi.GsiInstallParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.gsi.GsiInstallParams r0 = (android.gsi.GsiInstallParams) r0
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                int r1 = r6.beginGsiInstall(r0)
                r19.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0046:
                r8.enforceInterface(r10)
                java.lang.String r0 = r16.getInstalledGsiImageDir()
                r19.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x0054:
                r8.enforceInterface(r10)
                int r0 = r16.getGsiBootStatus()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0062:
                r8.enforceInterface(r10)
                boolean r0 = r16.isGsiInstalled()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0070:
                r8.enforceInterface(r10)
                boolean r0 = r16.isGsiRunning()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x007e:
                r8.enforceInterface(r10)
                long r0 = r16.getUserdataImageSize()
                r19.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x008c:
                r8.enforceInterface(r10)
                boolean r0 = r16.disableGsiInstall()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x009a:
                r8.enforceInterface(r10)
                boolean r0 = r16.removeGsiInstall()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x00a8:
                r8.enforceInterface(r10)
                boolean r0 = r16.isGsiInstallInProgress()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x00b6:
                r8.enforceInterface(r10)
                boolean r0 = r16.cancelGsiInstall()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x00c4:
                r8.enforceInterface(r10)
                boolean r0 = r16.isGsiEnabled()
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x00d2:
                r8.enforceInterface(r10)
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x00dd
                r1 = r11
            L_0x00dd:
                r0 = r1
                int r1 = r6.setGsiBootable(r0)
                r19.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00e9:
                r8.enforceInterface(r10)
                byte[] r0 = r18.createByteArray()
                boolean r1 = r6.commitGsiChunkFromMemory(r0)
                r19.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00fb:
                r8.enforceInterface(r10)
                android.gsi.GsiProgress r0 = r16.getInstallProgress()
                r19.writeNoException()
                if (r0 == 0) goto L_0x010e
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x0111
            L_0x010e:
                r9.writeInt(r1)
            L_0x0111:
                return r11
            L_0x0112:
                r8.enforceInterface(r10)
                int r1 = r18.readInt()
                if (r1 == 0) goto L_0x0124
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                goto L_0x0125
            L_0x0124:
            L_0x0125:
                long r1 = r18.readLong()
                boolean r3 = r6.commitGsiChunkFromStream(r0, r1)
                r19.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x0134:
                r8.enforceInterface(r10)
                long r12 = r18.readLong()
                long r14 = r18.readLong()
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x0147
                r5 = r11
                goto L_0x0148
            L_0x0147:
                r5 = r1
            L_0x0148:
                r0 = r16
                r1 = r12
                r3 = r14
                int r0 = r0.startGsiInstall(r1, r3, r5)
                r19.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0157:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.gsi.IGsiService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IGsiService {
            public static IGsiService sDefaultImpl;
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

            public int startGsiInstall(long gsiSize, long userdataSize, boolean wipeUserdata) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(gsiSize);
                    _data.writeLong(userdataSize);
                    _data.writeInt(wipeUserdata);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startGsiInstall(gsiSize, userdataSize, wipeUserdata);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean commitGsiChunkFromStream(ParcelFileDescriptor stream, long bytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (stream != null) {
                        _data.writeInt(1);
                        stream.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(bytes);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().commitGsiChunkFromStream(stream, bytes);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public GsiProgress getInstallProgress() throws RemoteException {
                GsiProgress _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstallProgress();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = GsiProgress.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    GsiProgress _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean commitGsiChunkFromMemory(byte[] bytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(bytes);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().commitGsiChunkFromMemory(bytes);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setGsiBootable(boolean oneShot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(oneShot);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setGsiBootable(oneShot);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isGsiEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGsiEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean cancelGsiInstall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelGsiInstall();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isGsiInstallInProgress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGsiInstallInProgress();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeGsiInstall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeGsiInstall();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean disableGsiInstall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableGsiInstall();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getUserdataImageSize() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserdataImageSize();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isGsiRunning() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGsiRunning();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isGsiInstalled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGsiInstalled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getGsiBootStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGsiBootStatus();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getInstalledGsiImageDir() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstalledGsiImageDir();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int beginGsiInstall(GsiInstallParams params) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().beginGsiInstall(params);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int wipeGsiUserdata() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().wipeGsiUserdata();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IGsiService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IGsiService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
