package com.android.internal.backup;

import android.app.backup.RestoreDescription;
import android.app.backup.RestoreSet;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IBackupTransport extends IInterface {
    int abortFullRestore() throws RemoteException;

    void cancelFullBackup() throws RemoteException;

    int checkFullBackupSize(long j) throws RemoteException;

    int clearBackupData(PackageInfo packageInfo) throws RemoteException;

    Intent configurationIntent() throws RemoteException;

    String currentDestinationString() throws RemoteException;

    Intent dataManagementIntent() throws RemoteException;

    CharSequence dataManagementIntentLabel() throws RemoteException;

    int finishBackup() throws RemoteException;

    void finishRestore() throws RemoteException;

    RestoreSet[] getAvailableRestoreSets() throws RemoteException;

    long getBackupQuota(String str, boolean z) throws RemoteException;

    long getCurrentRestoreSet() throws RemoteException;

    int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int getTransportFlags() throws RemoteException;

    int initializeDevice() throws RemoteException;

    boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean z) throws RemoteException;

    String name() throws RemoteException;

    RestoreDescription nextRestorePackage() throws RemoteException;

    int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    long requestBackupTime() throws RemoteException;

    long requestFullBackupTime() throws RemoteException;

    int sendBackupData(int i) throws RemoteException;

    int startRestore(long j, PackageInfo[] packageInfoArr) throws RemoteException;

    String transportDirName() throws RemoteException;

    public static class Default implements IBackupTransport {
        public String name() throws RemoteException {
            return null;
        }

        public Intent configurationIntent() throws RemoteException {
            return null;
        }

        public String currentDestinationString() throws RemoteException {
            return null;
        }

        public Intent dataManagementIntent() throws RemoteException {
            return null;
        }

        public CharSequence dataManagementIntentLabel() throws RemoteException {
            return null;
        }

        public String transportDirName() throws RemoteException {
            return null;
        }

        public long requestBackupTime() throws RemoteException {
            return 0;
        }

        public int initializeDevice() throws RemoteException {
            return 0;
        }

        public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor inFd, int flags) throws RemoteException {
            return 0;
        }

        public int clearBackupData(PackageInfo packageInfo) throws RemoteException {
            return 0;
        }

        public int finishBackup() throws RemoteException {
            return 0;
        }

        public RestoreSet[] getAvailableRestoreSets() throws RemoteException {
            return null;
        }

        public long getCurrentRestoreSet() throws RemoteException {
            return 0;
        }

        public int startRestore(long token, PackageInfo[] packages) throws RemoteException {
            return 0;
        }

        public RestoreDescription nextRestorePackage() throws RemoteException {
            return null;
        }

        public int getRestoreData(ParcelFileDescriptor outFd) throws RemoteException {
            return 0;
        }

        public void finishRestore() throws RemoteException {
        }

        public long requestFullBackupTime() throws RemoteException {
            return 0;
        }

        public int performFullBackup(PackageInfo targetPackage, ParcelFileDescriptor socket, int flags) throws RemoteException {
            return 0;
        }

        public int checkFullBackupSize(long size) throws RemoteException {
            return 0;
        }

        public int sendBackupData(int numBytes) throws RemoteException {
            return 0;
        }

        public void cancelFullBackup() throws RemoteException {
        }

        public boolean isAppEligibleForBackup(PackageInfo targetPackage, boolean isFullBackup) throws RemoteException {
            return false;
        }

        public long getBackupQuota(String packageName, boolean isFullBackup) throws RemoteException {
            return 0;
        }

        public int getNextFullRestoreDataChunk(ParcelFileDescriptor socket) throws RemoteException {
            return 0;
        }

        public int abortFullRestore() throws RemoteException {
            return 0;
        }

        public int getTransportFlags() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBackupTransport {
        private static final String DESCRIPTOR = "com.android.internal.backup.IBackupTransport";
        static final int TRANSACTION_abortFullRestore = 26;
        static final int TRANSACTION_cancelFullBackup = 22;
        static final int TRANSACTION_checkFullBackupSize = 20;
        static final int TRANSACTION_clearBackupData = 10;
        static final int TRANSACTION_configurationIntent = 2;
        static final int TRANSACTION_currentDestinationString = 3;
        static final int TRANSACTION_dataManagementIntent = 4;
        static final int TRANSACTION_dataManagementIntentLabel = 5;
        static final int TRANSACTION_finishBackup = 11;
        static final int TRANSACTION_finishRestore = 17;
        static final int TRANSACTION_getAvailableRestoreSets = 12;
        static final int TRANSACTION_getBackupQuota = 24;
        static final int TRANSACTION_getCurrentRestoreSet = 13;
        static final int TRANSACTION_getNextFullRestoreDataChunk = 25;
        static final int TRANSACTION_getRestoreData = 16;
        static final int TRANSACTION_getTransportFlags = 27;
        static final int TRANSACTION_initializeDevice = 8;
        static final int TRANSACTION_isAppEligibleForBackup = 23;
        static final int TRANSACTION_name = 1;
        static final int TRANSACTION_nextRestorePackage = 15;
        static final int TRANSACTION_performBackup = 9;
        static final int TRANSACTION_performFullBackup = 19;
        static final int TRANSACTION_requestBackupTime = 7;
        static final int TRANSACTION_requestFullBackupTime = 18;
        static final int TRANSACTION_sendBackupData = 21;
        static final int TRANSACTION_startRestore = 14;
        static final int TRANSACTION_transportDirName = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupTransport asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBackupTransport)) {
                return new Proxy(obj);
            }
            return (IBackupTransport) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "name";
                case 2:
                    return "configurationIntent";
                case 3:
                    return "currentDestinationString";
                case 4:
                    return "dataManagementIntent";
                case 5:
                    return "dataManagementIntentLabel";
                case 6:
                    return "transportDirName";
                case 7:
                    return "requestBackupTime";
                case 8:
                    return "initializeDevice";
                case 9:
                    return "performBackup";
                case 10:
                    return "clearBackupData";
                case 11:
                    return "finishBackup";
                case 12:
                    return "getAvailableRestoreSets";
                case 13:
                    return "getCurrentRestoreSet";
                case 14:
                    return "startRestore";
                case 15:
                    return "nextRestorePackage";
                case 16:
                    return "getRestoreData";
                case 17:
                    return "finishRestore";
                case 18:
                    return "requestFullBackupTime";
                case 19:
                    return "performFullBackup";
                case 20:
                    return "checkFullBackupSize";
                case 21:
                    return "sendBackupData";
                case 22:
                    return "cancelFullBackup";
                case 23:
                    return "isAppEligibleForBackup";
                case 24:
                    return "getBackupQuota";
                case 25:
                    return "getNextFullRestoreDataChunk";
                case 26:
                    return "abortFullRestore";
                case 27:
                    return "getTransportFlags";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v27, resolved type: android.content.pm.PackageInfo} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v5, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r3v9 */
        /* JADX WARNING: type inference failed for: r3v16 */
        /* JADX WARNING: type inference failed for: r3v21, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r3v32 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.internal.backup.IBackupTransport"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x025e
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x0250;
                    case 2: goto L_0x0239;
                    case 3: goto L_0x022b;
                    case 4: goto L_0x0214;
                    case 5: goto L_0x01fd;
                    case 6: goto L_0x01ef;
                    case 7: goto L_0x01e1;
                    case 8: goto L_0x01d3;
                    case 9: goto L_0x01a1;
                    case 10: goto L_0x0181;
                    case 11: goto L_0x0173;
                    case 12: goto L_0x0165;
                    case 13: goto L_0x0157;
                    case 14: goto L_0x013d;
                    case 15: goto L_0x0126;
                    case 16: goto L_0x0106;
                    case 17: goto L_0x00fc;
                    case 18: goto L_0x00ee;
                    case 19: goto L_0x00bc;
                    case 20: goto L_0x00aa;
                    case 21: goto L_0x0098;
                    case 22: goto L_0x008e;
                    case 23: goto L_0x0068;
                    case 24: goto L_0x004e;
                    case 25: goto L_0x002e;
                    case 26: goto L_0x0020;
                    case 27: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r1 = r6.getTransportFlags()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x0020:
                r8.enforceInterface(r0)
                int r1 = r6.abortFullRestore()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x002e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0041
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x0042
            L_0x0041:
            L_0x0042:
                r1 = r3
                int r3 = r6.getNextFullRestoreDataChunk(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x004e:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x005d
                r1 = r2
            L_0x005d:
                long r4 = r6.getBackupQuota(r3, r1)
                r9.writeNoException()
                r9.writeLong(r4)
                return r2
            L_0x0068:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x007a
                android.os.Parcelable$Creator<android.content.pm.PackageInfo> r3 = android.content.pm.PackageInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.content.pm.PackageInfo r3 = (android.content.pm.PackageInfo) r3
                goto L_0x007b
            L_0x007a:
            L_0x007b:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0083
                r1 = r2
            L_0x0083:
                boolean r4 = r6.isAppEligibleForBackup(r3, r1)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x008e:
                r8.enforceInterface(r0)
                r6.cancelFullBackup()
                r9.writeNoException()
                return r2
            L_0x0098:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r6.sendBackupData(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00aa:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                int r1 = r6.checkFullBackupSize(r3)
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x00bc:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00ce
                android.os.Parcelable$Creator<android.content.pm.PackageInfo> r1 = android.content.pm.PackageInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.PackageInfo r1 = (android.content.pm.PackageInfo) r1
                goto L_0x00cf
            L_0x00ce:
                r1 = r3
            L_0x00cf:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00de
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r3 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x00df
            L_0x00de:
            L_0x00df:
                int r4 = r8.readInt()
                int r5 = r6.performFullBackup(r1, r3, r4)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x00ee:
                r8.enforceInterface(r0)
                long r3 = r6.requestFullBackupTime()
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x00fc:
                r8.enforceInterface(r0)
                r6.finishRestore()
                r9.writeNoException()
                return r2
            L_0x0106:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0119
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x011a
            L_0x0119:
            L_0x011a:
                r1 = r3
                int r3 = r6.getRestoreData(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0126:
                r8.enforceInterface(r0)
                android.app.backup.RestoreDescription r3 = r6.nextRestorePackage()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0139
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x013c
            L_0x0139:
                r9.writeInt(r1)
            L_0x013c:
                return r2
            L_0x013d:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                android.os.Parcelable$Creator<android.content.pm.PackageInfo> r1 = android.content.pm.PackageInfo.CREATOR
                java.lang.Object[] r1 = r8.createTypedArray(r1)
                android.content.pm.PackageInfo[] r1 = (android.content.pm.PackageInfo[]) r1
                int r5 = r6.startRestore(r3, r1)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x0157:
                r8.enforceInterface(r0)
                long r3 = r6.getCurrentRestoreSet()
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x0165:
                r8.enforceInterface(r0)
                android.app.backup.RestoreSet[] r1 = r6.getAvailableRestoreSets()
                r9.writeNoException()
                r9.writeTypedArray(r1, r2)
                return r2
            L_0x0173:
                r8.enforceInterface(r0)
                int r1 = r6.finishBackup()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x0181:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0194
                android.os.Parcelable$Creator<android.content.pm.PackageInfo> r1 = android.content.pm.PackageInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.content.pm.PackageInfo r3 = (android.content.pm.PackageInfo) r3
                goto L_0x0195
            L_0x0194:
            L_0x0195:
                r1 = r3
                int r3 = r6.clearBackupData(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x01a1:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01b3
                android.os.Parcelable$Creator<android.content.pm.PackageInfo> r1 = android.content.pm.PackageInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.PackageInfo r1 = (android.content.pm.PackageInfo) r1
                goto L_0x01b4
            L_0x01b3:
                r1 = r3
            L_0x01b4:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01c3
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r3 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x01c4
            L_0x01c3:
            L_0x01c4:
                int r4 = r8.readInt()
                int r5 = r6.performBackup(r1, r3, r4)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x01d3:
                r8.enforceInterface(r0)
                int r1 = r6.initializeDevice()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x01e1:
                r8.enforceInterface(r0)
                long r3 = r6.requestBackupTime()
                r9.writeNoException()
                r9.writeLong(r3)
                return r2
            L_0x01ef:
                r8.enforceInterface(r0)
                java.lang.String r1 = r6.transportDirName()
                r9.writeNoException()
                r9.writeString(r1)
                return r2
            L_0x01fd:
                r8.enforceInterface(r0)
                java.lang.CharSequence r3 = r6.dataManagementIntentLabel()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0210
                r9.writeInt(r2)
                android.text.TextUtils.writeToParcel(r3, r9, r2)
                goto L_0x0213
            L_0x0210:
                r9.writeInt(r1)
            L_0x0213:
                return r2
            L_0x0214:
                r8.enforceInterface(r0)
                android.content.Intent r3 = r6.dataManagementIntent()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0227
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x022a
            L_0x0227:
                r9.writeInt(r1)
            L_0x022a:
                return r2
            L_0x022b:
                r8.enforceInterface(r0)
                java.lang.String r1 = r6.currentDestinationString()
                r9.writeNoException()
                r9.writeString(r1)
                return r2
            L_0x0239:
                r8.enforceInterface(r0)
                android.content.Intent r3 = r6.configurationIntent()
                r9.writeNoException()
                if (r3 == 0) goto L_0x024c
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x024f
            L_0x024c:
                r9.writeInt(r1)
            L_0x024f:
                return r2
            L_0x0250:
                r8.enforceInterface(r0)
                java.lang.String r1 = r6.name()
                r9.writeNoException()
                r9.writeString(r1)
                return r2
            L_0x025e:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.backup.IBackupTransport.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBackupTransport {
            public static IBackupTransport sDefaultImpl;
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

            public String name() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().name();
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

            public Intent configurationIntent() throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().configurationIntent();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String currentDestinationString() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().currentDestinationString();
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

            public Intent dataManagementIntent() throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dataManagementIntent();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence dataManagementIntentLabel() throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dataManagementIntentLabel();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CharSequence _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String transportDirName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().transportDirName();
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

            public long requestBackupTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestBackupTime();
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

            public int initializeDevice() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initializeDevice();
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

            public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor inFd, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (packageInfo != null) {
                        _data.writeInt(1);
                        packageInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (inFd != null) {
                        _data.writeInt(1);
                        inFd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().performBackup(packageInfo, inFd, flags);
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

            public int clearBackupData(PackageInfo packageInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (packageInfo != null) {
                        _data.writeInt(1);
                        packageInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearBackupData(packageInfo);
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

            public int finishBackup() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().finishBackup();
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

            public RestoreSet[] getAvailableRestoreSets() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAvailableRestoreSets();
                    }
                    _reply.readException();
                    RestoreSet[] _result = (RestoreSet[]) _reply.createTypedArray(RestoreSet.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCurrentRestoreSet() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentRestoreSet();
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

            public int startRestore(long token, PackageInfo[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(token);
                    _data.writeTypedArray(packages, 0);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startRestore(token, packages);
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

            public RestoreDescription nextRestorePackage() throws RemoteException {
                RestoreDescription _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().nextRestorePackage();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RestoreDescription.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RestoreDescription _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRestoreData(ParcelFileDescriptor outFd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (outFd != null) {
                        _data.writeInt(1);
                        outFd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRestoreData(outFd);
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

            public void finishRestore() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishRestore();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long requestFullBackupTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestFullBackupTime();
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

            public int performFullBackup(PackageInfo targetPackage, ParcelFileDescriptor socket, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (targetPackage != null) {
                        _data.writeInt(1);
                        targetPackage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (socket != null) {
                        _data.writeInt(1);
                        socket.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().performFullBackup(targetPackage, socket, flags);
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

            public int checkFullBackupSize(long size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(size);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkFullBackupSize(size);
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

            public int sendBackupData(int numBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(numBytes);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendBackupData(numBytes);
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

            public void cancelFullBackup() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelFullBackup();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAppEligibleForBackup(PackageInfo targetPackage, boolean isFullBackup) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (targetPackage != null) {
                        _data.writeInt(1);
                        targetPackage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isFullBackup);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAppEligibleForBackup(targetPackage, isFullBackup);
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

            public long getBackupQuota(String packageName, boolean isFullBackup) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(isFullBackup);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBackupQuota(packageName, isFullBackup);
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

            public int getNextFullRestoreDataChunk(ParcelFileDescriptor socket) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (socket != null) {
                        _data.writeInt(1);
                        socket.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNextFullRestoreDataChunk(socket);
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

            public int abortFullRestore() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().abortFullRestore();
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

            public int getTransportFlags() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTransportFlags();
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

        public static boolean setDefaultImpl(IBackupTransport impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBackupTransport getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
