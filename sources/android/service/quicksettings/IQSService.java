package android.service.quicksettings;

import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IQSService extends IInterface {
    Tile getTile(IBinder iBinder) throws RemoteException;

    boolean isLocked() throws RemoteException;

    boolean isSecure() throws RemoteException;

    void onDialogHidden(IBinder iBinder) throws RemoteException;

    void onShowDialog(IBinder iBinder) throws RemoteException;

    void onStartActivity(IBinder iBinder) throws RemoteException;

    void onStartSuccessful(IBinder iBinder) throws RemoteException;

    void startUnlockAndRun(IBinder iBinder) throws RemoteException;

    void updateQsTile(Tile tile, IBinder iBinder) throws RemoteException;

    void updateStatusIcon(IBinder iBinder, Icon icon, String str) throws RemoteException;

    public static class Default implements IQSService {
        public Tile getTile(IBinder tile) throws RemoteException {
            return null;
        }

        public void updateQsTile(Tile tile, IBinder service) throws RemoteException {
        }

        public void updateStatusIcon(IBinder tile, Icon icon, String contentDescription) throws RemoteException {
        }

        public void onShowDialog(IBinder tile) throws RemoteException {
        }

        public void onStartActivity(IBinder tile) throws RemoteException {
        }

        public boolean isLocked() throws RemoteException {
            return false;
        }

        public boolean isSecure() throws RemoteException {
            return false;
        }

        public void startUnlockAndRun(IBinder tile) throws RemoteException {
        }

        public void onDialogHidden(IBinder tile) throws RemoteException {
        }

        public void onStartSuccessful(IBinder tile) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IQSService {
        private static final String DESCRIPTOR = "android.service.quicksettings.IQSService";
        static final int TRANSACTION_getTile = 1;
        static final int TRANSACTION_isLocked = 6;
        static final int TRANSACTION_isSecure = 7;
        static final int TRANSACTION_onDialogHidden = 9;
        static final int TRANSACTION_onShowDialog = 4;
        static final int TRANSACTION_onStartActivity = 5;
        static final int TRANSACTION_onStartSuccessful = 10;
        static final int TRANSACTION_startUnlockAndRun = 8;
        static final int TRANSACTION_updateQsTile = 2;
        static final int TRANSACTION_updateStatusIcon = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IQSService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IQSService)) {
                return new Proxy(obj);
            }
            return (IQSService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getTile";
                case 2:
                    return "updateQsTile";
                case 3:
                    return "updateStatusIcon";
                case 4:
                    return "onShowDialog";
                case 5:
                    return "onStartActivity";
                case 6:
                    return "isLocked";
                case 7:
                    return "isSecure";
                case 8:
                    return "startUnlockAndRun";
                case 9:
                    return "onDialogHidden";
                case 10:
                    return "onStartSuccessful";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.service.quicksettings.Tile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.graphics.drawable.Icon} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v19 */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.service.quicksettings.IQSService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00cf
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x00b3;
                    case 2: goto L_0x0095;
                    case 3: goto L_0x0073;
                    case 4: goto L_0x0065;
                    case 5: goto L_0x0057;
                    case 6: goto L_0x0049;
                    case 7: goto L_0x003b;
                    case 8: goto L_0x002d;
                    case 9: goto L_0x001f;
                    case 10: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.onStartSuccessful(r1)
                r8.writeNoException()
                return r2
            L_0x001f:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.onDialogHidden(r1)
                r8.writeNoException()
                return r2
            L_0x002d:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.startUnlockAndRun(r1)
                r8.writeNoException()
                return r2
            L_0x003b:
                r7.enforceInterface(r0)
                boolean r1 = r5.isSecure()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0049:
                r7.enforceInterface(r0)
                boolean r1 = r5.isLocked()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0057:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.onStartActivity(r1)
                r8.writeNoException()
                return r2
            L_0x0065:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                r5.onShowDialog(r1)
                r8.writeNoException()
                return r2
            L_0x0073:
                r7.enforceInterface(r0)
                android.os.IBinder r3 = r7.readStrongBinder()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0089
                android.os.Parcelable$Creator<android.graphics.drawable.Icon> r1 = android.graphics.drawable.Icon.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.graphics.drawable.Icon r1 = (android.graphics.drawable.Icon) r1
                goto L_0x008a
            L_0x0089:
            L_0x008a:
                java.lang.String r4 = r7.readString()
                r5.updateStatusIcon(r3, r1, r4)
                r8.writeNoException()
                return r2
            L_0x0095:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00a7
                android.os.Parcelable$Creator<android.service.quicksettings.Tile> r1 = android.service.quicksettings.Tile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.service.quicksettings.Tile r1 = (android.service.quicksettings.Tile) r1
                goto L_0x00a8
            L_0x00a7:
            L_0x00a8:
                android.os.IBinder r3 = r7.readStrongBinder()
                r5.updateQsTile(r1, r3)
                r8.writeNoException()
                return r2
            L_0x00b3:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.service.quicksettings.Tile r3 = r5.getTile(r1)
                r8.writeNoException()
                if (r3 == 0) goto L_0x00ca
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x00ce
            L_0x00ca:
                r4 = 0
                r8.writeInt(r4)
            L_0x00ce:
                return r2
            L_0x00cf:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.quicksettings.IQSService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IQSService {
            public static IQSService sDefaultImpl;
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

            public Tile getTile(IBinder tile) throws RemoteException {
                Tile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTile(tile);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Tile.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Tile _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateQsTile(Tile tile, IBinder service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(service);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateQsTile(tile, service);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateStatusIcon(IBinder tile, Icon icon, String contentDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(contentDescription);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateStatusIcon(tile, icon, contentDescription);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onShowDialog(IBinder tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onShowDialog(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onStartActivity(IBinder tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onStartActivity(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isLocked() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLocked();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSecure() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSecure();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startUnlockAndRun(IBinder tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startUnlockAndRun(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onDialogHidden(IBinder tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDialogHidden(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onStartSuccessful(IBinder tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tile);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onStartSuccessful(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IQSService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IQSService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
