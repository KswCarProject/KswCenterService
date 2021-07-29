package android.content.pm;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IPackageInstallerSession extends IInterface {
    void abandon() throws RemoteException;

    void addChildSessionId(int i) throws RemoteException;

    void addClientProgress(float f) throws RemoteException;

    void close() throws RemoteException;

    void commit(IntentSender intentSender, boolean z) throws RemoteException;

    int[] getChildSessionIds() throws RemoteException;

    String[] getNames() throws RemoteException;

    int getParentSessionId() throws RemoteException;

    boolean isMultiPackage() throws RemoteException;

    boolean isStaged() throws RemoteException;

    ParcelFileDescriptor openRead(String str) throws RemoteException;

    ParcelFileDescriptor openWrite(String str, long j, long j2) throws RemoteException;

    void removeChildSessionId(int i) throws RemoteException;

    void removeSplit(String str) throws RemoteException;

    void setClientProgress(float f) throws RemoteException;

    void transfer(String str) throws RemoteException;

    void write(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static class Default implements IPackageInstallerSession {
        public void setClientProgress(float progress) throws RemoteException {
        }

        public void addClientProgress(float progress) throws RemoteException {
        }

        public String[] getNames() throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor openWrite(String name, long offsetBytes, long lengthBytes) throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor openRead(String name) throws RemoteException {
            return null;
        }

        public void write(String name, long offsetBytes, long lengthBytes, ParcelFileDescriptor fd) throws RemoteException {
        }

        public void removeSplit(String splitName) throws RemoteException {
        }

        public void close() throws RemoteException {
        }

        public void commit(IntentSender statusReceiver, boolean forTransferred) throws RemoteException {
        }

        public void transfer(String packageName) throws RemoteException {
        }

        public void abandon() throws RemoteException {
        }

        public boolean isMultiPackage() throws RemoteException {
            return false;
        }

        public int[] getChildSessionIds() throws RemoteException {
            return null;
        }

        public void addChildSessionId(int sessionId) throws RemoteException {
        }

        public void removeChildSessionId(int sessionId) throws RemoteException {
        }

        public int getParentSessionId() throws RemoteException {
            return 0;
        }

        public boolean isStaged() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPackageInstallerSession {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstallerSession";
        static final int TRANSACTION_abandon = 11;
        static final int TRANSACTION_addChildSessionId = 14;
        static final int TRANSACTION_addClientProgress = 2;
        static final int TRANSACTION_close = 8;
        static final int TRANSACTION_commit = 9;
        static final int TRANSACTION_getChildSessionIds = 13;
        static final int TRANSACTION_getNames = 3;
        static final int TRANSACTION_getParentSessionId = 16;
        static final int TRANSACTION_isMultiPackage = 12;
        static final int TRANSACTION_isStaged = 17;
        static final int TRANSACTION_openRead = 5;
        static final int TRANSACTION_openWrite = 4;
        static final int TRANSACTION_removeChildSessionId = 15;
        static final int TRANSACTION_removeSplit = 7;
        static final int TRANSACTION_setClientProgress = 1;
        static final int TRANSACTION_transfer = 10;
        static final int TRANSACTION_write = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageInstallerSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPackageInstallerSession)) {
                return new Proxy(obj);
            }
            return (IPackageInstallerSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setClientProgress";
                case 2:
                    return "addClientProgress";
                case 3:
                    return "getNames";
                case 4:
                    return "openWrite";
                case 5:
                    return "openRead";
                case 6:
                    return "write";
                case 7:
                    return "removeSplit";
                case 8:
                    return "close";
                case 9:
                    return "commit";
                case 10:
                    return "transfer";
                case 11:
                    return "abandon";
                case 12:
                    return "isMultiPackage";
                case 13:
                    return "getChildSessionIds";
                case 14:
                    return "addChildSessionId";
                case 15:
                    return "removeChildSessionId";
                case 16:
                    return "getParentSessionId";
                case 17:
                    return "isStaged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX WARNING: type inference failed for: r6v1, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "android.content.pm.IPackageInstallerSession"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x015c
                r0 = 0
                r6 = 0
                switch(r8) {
                    case 1: goto L_0x014e;
                    case 2: goto L_0x0140;
                    case 3: goto L_0x0132;
                    case 4: goto L_0x0109;
                    case 5: goto L_0x00ee;
                    case 6: goto L_0x00c1;
                    case 7: goto L_0x00b3;
                    case 8: goto L_0x00a9;
                    case 9: goto L_0x0086;
                    case 10: goto L_0x0078;
                    case 11: goto L_0x006e;
                    case 12: goto L_0x0060;
                    case 13: goto L_0x0052;
                    case 14: goto L_0x0044;
                    case 15: goto L_0x0036;
                    case 16: goto L_0x0028;
                    case 17: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                boolean r0 = r18.isStaged()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0028:
                r9.enforceInterface(r11)
                int r0 = r18.getParentSessionId()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0036:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                r7.removeChildSessionId(r0)
                r21.writeNoException()
                return r12
            L_0x0044:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                r7.addChildSessionId(r0)
                r21.writeNoException()
                return r12
            L_0x0052:
                r9.enforceInterface(r11)
                int[] r0 = r18.getChildSessionIds()
                r21.writeNoException()
                r10.writeIntArray(r0)
                return r12
            L_0x0060:
                r9.enforceInterface(r11)
                boolean r0 = r18.isMultiPackage()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x006e:
                r9.enforceInterface(r11)
                r18.abandon()
                r21.writeNoException()
                return r12
            L_0x0078:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.transfer(r0)
                r21.writeNoException()
                return r12
            L_0x0086:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0098
                android.os.Parcelable$Creator<android.content.IntentSender> r0 = android.content.IntentSender.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.IntentSender r0 = (android.content.IntentSender) r0
                goto L_0x0099
            L_0x0098:
            L_0x0099:
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x00a1
                r6 = r12
            L_0x00a1:
                r1 = r6
                r7.commit(r0, r1)
                r21.writeNoException()
                return r12
            L_0x00a9:
                r9.enforceInterface(r11)
                r18.close()
                r21.writeNoException()
                return r12
            L_0x00b3:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.removeSplit(r0)
                r21.writeNoException()
                return r12
            L_0x00c1:
                r9.enforceInterface(r11)
                java.lang.String r13 = r20.readString()
                long r14 = r20.readLong()
                long r16 = r20.readLong()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x00e0
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x00de:
                r6 = r0
                goto L_0x00e1
            L_0x00e0:
                goto L_0x00de
            L_0x00e1:
                r0 = r18
                r1 = r13
                r2 = r14
                r4 = r16
                r0.write(r1, r2, r4, r6)
                r21.writeNoException()
                return r12
            L_0x00ee:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                android.os.ParcelFileDescriptor r1 = r7.openRead(r0)
                r21.writeNoException()
                if (r1 == 0) goto L_0x0105
                r10.writeInt(r12)
                r1.writeToParcel(r10, r12)
                goto L_0x0108
            L_0x0105:
                r10.writeInt(r6)
            L_0x0108:
                return r12
            L_0x0109:
                r9.enforceInterface(r11)
                java.lang.String r13 = r20.readString()
                long r14 = r20.readLong()
                long r16 = r20.readLong()
                r0 = r18
                r1 = r13
                r2 = r14
                r4 = r16
                android.os.ParcelFileDescriptor r0 = r0.openWrite(r1, r2, r4)
                r21.writeNoException()
                if (r0 == 0) goto L_0x012e
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x0131
            L_0x012e:
                r10.writeInt(r6)
            L_0x0131:
                return r12
            L_0x0132:
                r9.enforceInterface(r11)
                java.lang.String[] r0 = r18.getNames()
                r21.writeNoException()
                r10.writeStringArray(r0)
                return r12
            L_0x0140:
                r9.enforceInterface(r11)
                float r0 = r20.readFloat()
                r7.addClientProgress(r0)
                r21.writeNoException()
                return r12
            L_0x014e:
                r9.enforceInterface(r11)
                float r0 = r20.readFloat()
                r7.setClientProgress(r0)
                r21.writeNoException()
                return r12
            L_0x015c:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.IPackageInstallerSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPackageInstallerSession {
            public static IPackageInstallerSession sDefaultImpl;
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

            public void setClientProgress(float progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(progress);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setClientProgress(progress);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addClientProgress(float progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(progress);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addClientProgress(progress);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getNames() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNames();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openWrite(String name, long offsetBytes, long lengthBytes) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeLong(offsetBytes);
                    _data.writeLong(lengthBytes);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openWrite(name, offsetBytes, lengthBytes);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openRead(String name) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openRead(name);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void write(String name, long offsetBytes, long lengthBytes, ParcelFileDescriptor fd) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = fd;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(name);
                        try {
                            _data.writeLong(offsetBytes);
                        } catch (Throwable th) {
                            th = th;
                            long j = lengthBytes;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeLong(lengthBytes);
                            if (parcelFileDescriptor != null) {
                                _data.writeInt(1);
                                parcelFileDescriptor.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().write(name, offsetBytes, lengthBytes, fd);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j2 = offsetBytes;
                        long j3 = lengthBytes;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = name;
                    long j22 = offsetBytes;
                    long j32 = lengthBytes;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void removeSplit(String splitName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(splitName);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeSplit(splitName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().close();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void commit(IntentSender statusReceiver, boolean forTransferred) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (statusReceiver != null) {
                        _data.writeInt(1);
                        statusReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(forTransferred);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().commit(statusReceiver, forTransferred);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void transfer(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().transfer(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abandon() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().abandon();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMultiPackage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMultiPackage();
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

            public int[] getChildSessionIds() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getChildSessionIds();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addChildSessionId(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addChildSessionId(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeChildSessionId(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeChildSessionId(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getParentSessionId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParentSessionId();
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

            public boolean isStaged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStaged();
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
        }

        public static boolean setDefaultImpl(IPackageInstallerSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPackageInstallerSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
