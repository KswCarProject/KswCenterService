package android.app;

import android.app.backup.IBackupCallback;
import android.app.backup.IBackupManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IBackupAgent extends IInterface {
    void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long j, IBackupCallback iBackupCallback, int i) throws RemoteException;

    void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException;

    void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException;

    void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws RemoteException;

    void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws RemoteException;

    void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException;

    void doRestoreFinished(int i, IBackupManager iBackupManager) throws RemoteException;

    void fail(String str) throws RemoteException;

    public static class Default implements IBackupAgent {
        public void doBackup(ParcelFileDescriptor oldState, ParcelFileDescriptor data, ParcelFileDescriptor newState, long quotaBytes, IBackupCallback callbackBinder, int transportFlags) throws RemoteException {
        }

        public void doRestore(ParcelFileDescriptor data, long appVersionCode, ParcelFileDescriptor newState, int token, IBackupManager callbackBinder) throws RemoteException {
        }

        public void doFullBackup(ParcelFileDescriptor data, long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) throws RemoteException {
        }

        public void doMeasureFullBackup(long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) throws RemoteException {
        }

        public void doQuotaExceeded(long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder) throws RemoteException {
        }

        public void doRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime, int token, IBackupManager callbackBinder) throws RemoteException {
        }

        public void doRestoreFinished(int token, IBackupManager callbackBinder) throws RemoteException {
        }

        public void fail(String message) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBackupAgent {
        private static final String DESCRIPTOR = "android.app.IBackupAgent";
        static final int TRANSACTION_doBackup = 1;
        static final int TRANSACTION_doFullBackup = 3;
        static final int TRANSACTION_doMeasureFullBackup = 4;
        static final int TRANSACTION_doQuotaExceeded = 5;
        static final int TRANSACTION_doRestore = 2;
        static final int TRANSACTION_doRestoreFile = 6;
        static final int TRANSACTION_doRestoreFinished = 7;
        static final int TRANSACTION_fail = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupAgent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBackupAgent)) {
                return new Proxy(obj);
            }
            return (IBackupAgent) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "doBackup";
                case 2:
                    return "doRestore";
                case 3:
                    return "doFullBackup";
                case 4:
                    return "doMeasureFullBackup";
                case 5:
                    return "doQuotaExceeded";
                case 6:
                    return "doRestoreFile";
                case 7:
                    return "doRestoreFinished";
                case 8:
                    return "fail";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        public boolean onTransact(int r29, android.os.Parcel r30, android.os.Parcel r31, int r32) throws android.os.RemoteException {
            /*
                r28 = this;
                r13 = r28
                r14 = r29
                r15 = r30
                java.lang.String r12 = "android.app.IBackupAgent"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r16 = 1
                if (r14 == r0) goto L_0x0183
                r0 = 0
                switch(r14) {
                    case 1: goto L_0x0135;
                    case 2: goto L_0x00f7;
                    case 3: goto L_0x00c4;
                    case 4: goto L_0x00a2;
                    case 5: goto L_0x0085;
                    case 6: goto L_0x0036;
                    case 7: goto L_0x0023;
                    case 8: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r0 = super.onTransact(r29, r30, r31, r32)
                return r0
            L_0x0018:
                r15.enforceInterface(r12)
                java.lang.String r0 = r30.readString()
                r13.fail(r0)
                return r16
            L_0x0023:
                r15.enforceInterface(r12)
                int r0 = r30.readInt()
                android.os.IBinder r1 = r30.readStrongBinder()
                android.app.backup.IBackupManager r1 = android.app.backup.IBackupManager.Stub.asInterface(r1)
                r13.doRestoreFinished(r0, r1)
                return r16
            L_0x0036:
                r15.enforceInterface(r12)
                int r1 = r30.readInt()
                if (r1 == 0) goto L_0x0049
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x0047:
                r1 = r0
                goto L_0x004a
            L_0x0049:
                goto L_0x0047
            L_0x004a:
                long r17 = r30.readLong()
                int r19 = r30.readInt()
                java.lang.String r20 = r30.readString()
                java.lang.String r21 = r30.readString()
                long r22 = r30.readLong()
                long r24 = r30.readLong()
                int r26 = r30.readInt()
                android.os.IBinder r0 = r30.readStrongBinder()
                android.app.backup.IBackupManager r27 = android.app.backup.IBackupManager.Stub.asInterface(r0)
                r0 = r28
                r2 = r17
                r4 = r19
                r5 = r20
                r6 = r21
                r7 = r22
                r9 = r24
                r11 = r26
                r13 = r12
                r12 = r27
                r0.doRestoreFile(r1, r2, r4, r5, r6, r7, r9, r11, r12)
                return r16
            L_0x0085:
                r13 = r12
                r15.enforceInterface(r13)
                long r6 = r30.readLong()
                long r8 = r30.readLong()
                android.os.IBinder r0 = r30.readStrongBinder()
                android.app.backup.IBackupCallback r10 = android.app.backup.IBackupCallback.Stub.asInterface(r0)
                r0 = r28
                r1 = r6
                r3 = r8
                r5 = r10
                r0.doQuotaExceeded(r1, r3, r5)
                return r16
            L_0x00a2:
                r13 = r12
                r15.enforceInterface(r13)
                long r6 = r30.readLong()
                int r8 = r30.readInt()
                android.os.IBinder r0 = r30.readStrongBinder()
                android.app.backup.IBackupManager r9 = android.app.backup.IBackupManager.Stub.asInterface(r0)
                int r10 = r30.readInt()
                r0 = r28
                r1 = r6
                r3 = r8
                r4 = r9
                r5 = r10
                r0.doMeasureFullBackup(r1, r3, r4, r5)
                return r16
            L_0x00c4:
                r13 = r12
                r15.enforceInterface(r13)
                int r1 = r30.readInt()
                if (r1 == 0) goto L_0x00d8
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x00d6:
                r1 = r0
                goto L_0x00d9
            L_0x00d8:
                goto L_0x00d6
            L_0x00d9:
                long r7 = r30.readLong()
                int r9 = r30.readInt()
                android.os.IBinder r0 = r30.readStrongBinder()
                android.app.backup.IBackupManager r10 = android.app.backup.IBackupManager.Stub.asInterface(r0)
                int r11 = r30.readInt()
                r0 = r28
                r2 = r7
                r4 = r9
                r5 = r10
                r6 = r11
                r0.doFullBackup(r1, r2, r4, r5, r6)
                return r16
            L_0x00f7:
                r13 = r12
                r15.enforceInterface(r13)
                int r1 = r30.readInt()
                if (r1 == 0) goto L_0x010a
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.os.ParcelFileDescriptor r1 = (android.os.ParcelFileDescriptor) r1
                goto L_0x010b
            L_0x010a:
                r1 = r0
            L_0x010b:
                long r7 = r30.readLong()
                int r2 = r30.readInt()
                if (r2 == 0) goto L_0x011f
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x011d:
                r4 = r0
                goto L_0x0120
            L_0x011f:
                goto L_0x011d
            L_0x0120:
                int r9 = r30.readInt()
                android.os.IBinder r0 = r30.readStrongBinder()
                android.app.backup.IBackupManager r10 = android.app.backup.IBackupManager.Stub.asInterface(r0)
                r0 = r28
                r2 = r7
                r5 = r9
                r6 = r10
                r0.doRestore(r1, r2, r4, r5, r6)
                return r16
            L_0x0135:
                r13 = r12
                r15.enforceInterface(r13)
                int r1 = r30.readInt()
                if (r1 == 0) goto L_0x0148
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.os.ParcelFileDescriptor r1 = (android.os.ParcelFileDescriptor) r1
                goto L_0x0149
            L_0x0148:
                r1 = r0
            L_0x0149:
                int r2 = r30.readInt()
                if (r2 == 0) goto L_0x0158
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r2 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r15)
                android.os.ParcelFileDescriptor r2 = (android.os.ParcelFileDescriptor) r2
                goto L_0x0159
            L_0x0158:
                r2 = r0
            L_0x0159:
                int r3 = r30.readInt()
                if (r3 == 0) goto L_0x0169
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x0167:
                r3 = r0
                goto L_0x016a
            L_0x0169:
                goto L_0x0167
            L_0x016a:
                long r8 = r30.readLong()
                android.os.IBinder r0 = r30.readStrongBinder()
                android.app.backup.IBackupCallback r10 = android.app.backup.IBackupCallback.Stub.asInterface(r0)
                int r11 = r30.readInt()
                r0 = r28
                r4 = r8
                r6 = r10
                r7 = r11
                r0.doBackup(r1, r2, r3, r4, r6, r7)
                return r16
            L_0x0183:
                r13 = r12
                r0 = r31
                r0.writeString(r13)
                return r16
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IBackupAgent.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBackupAgent {
            public static IBackupAgent sDefaultImpl;
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

            public void doBackup(ParcelFileDescriptor oldState, ParcelFileDescriptor data, ParcelFileDescriptor newState, long quotaBytes, IBackupCallback callbackBinder, int transportFlags) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = oldState;
                ParcelFileDescriptor parcelFileDescriptor2 = data;
                ParcelFileDescriptor parcelFileDescriptor3 = newState;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (parcelFileDescriptor2 != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (parcelFileDescriptor3 != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor3.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeLong(quotaBytes);
                        _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                        _data.writeInt(transportFlags);
                        if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().doBackup(oldState, data, newState, quotaBytes, callbackBinder, transportFlags);
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    long j = quotaBytes;
                    _data.recycle();
                    throw th;
                }
            }

            public void doRestore(ParcelFileDescriptor data, long appVersionCode, ParcelFileDescriptor newState, int token, IBackupManager callbackBinder) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = data;
                ParcelFileDescriptor parcelFileDescriptor2 = newState;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeLong(appVersionCode);
                        if (parcelFileDescriptor2 != null) {
                            _data.writeInt(1);
                            parcelFileDescriptor2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(token);
                            _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                        } catch (Throwable th) {
                            th = th;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().doRestore(data, appVersionCode, newState, token, callbackBinder);
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i = token;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    long j = appVersionCode;
                    int i2 = token;
                    _data.recycle();
                    throw th;
                }
            }

            public void doFullBackup(ParcelFileDescriptor data, long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = data;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeLong(quotaBytes);
                    } catch (Throwable th) {
                        th = th;
                        int i = token;
                        int i2 = transportFlags;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(token);
                        _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                        try {
                            _data.writeInt(transportFlags);
                            try {
                                if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().doFullBackup(data, quotaBytes, token, callbackBinder, transportFlags);
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i22 = transportFlags;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    long j = quotaBytes;
                    int i3 = token;
                    int i222 = transportFlags;
                    _data.recycle();
                    throw th;
                }
            }

            public void doMeasureFullBackup(long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(quotaBytes);
                    _data.writeInt(token);
                    _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                    _data.writeInt(transportFlags);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().doMeasureFullBackup(quotaBytes, token, callbackBinder, transportFlags);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void doQuotaExceeded(long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(backupDataBytes);
                    _data.writeLong(quotaBytes);
                    _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().doQuotaExceeded(backupDataBytes, quotaBytes, callbackBinder);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void doRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime, int token, IBackupManager callbackBinder) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = data;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(size);
                    _data.writeInt(type);
                    _data.writeString(domain);
                    _data.writeString(path);
                    _data.writeLong(mode);
                    _data.writeLong(mtime);
                    _data.writeInt(token);
                    _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().doRestoreFile(data, size, type, domain, path, mode, mtime, token, callbackBinder);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void doRestoreFinished(int token, IBackupManager callbackBinder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeStrongBinder(callbackBinder != null ? callbackBinder.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().doRestoreFinished(token, callbackBinder);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void fail(String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().fail(message);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBackupAgent impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBackupAgent getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
