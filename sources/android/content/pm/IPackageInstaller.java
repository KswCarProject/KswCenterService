package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.IntentSender;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IPackageInstaller extends IInterface {
    void abandonSession(int i) throws RemoteException;

    int createSession(PackageInstaller.SessionParams sessionParams, String str, int i) throws RemoteException;

    ParceledListSlice getAllSessions(int i) throws RemoteException;

    ParceledListSlice getMySessions(String str, int i) throws RemoteException;

    PackageInstaller.SessionInfo getSessionInfo(int i) throws RemoteException;

    ParceledListSlice getStagedSessions() throws RemoteException;

    void installExistingPackage(String str, int i, int i2, IntentSender intentSender, int i3, List<String> list) throws RemoteException;

    IPackageInstallerSession openSession(int i) throws RemoteException;

    void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int i) throws RemoteException;

    void setPermissionsResult(int i, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void uninstall(VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2) throws RemoteException;

    void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException;

    void updateSessionAppIcon(int i, Bitmap bitmap) throws RemoteException;

    void updateSessionAppLabel(int i, String str) throws RemoteException;

    public static class Default implements IPackageInstaller {
        public int createSession(PackageInstaller.SessionParams params, String installerPackageName, int userId) throws RemoteException {
            return 0;
        }

        public void updateSessionAppIcon(int sessionId, Bitmap appIcon) throws RemoteException {
        }

        public void updateSessionAppLabel(int sessionId, String appLabel) throws RemoteException {
        }

        public void abandonSession(int sessionId) throws RemoteException {
        }

        public IPackageInstallerSession openSession(int sessionId) throws RemoteException {
            return null;
        }

        public PackageInstaller.SessionInfo getSessionInfo(int sessionId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getAllSessions(int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getMySessions(String installerPackageName, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getStagedSessions() throws RemoteException {
            return null;
        }

        public void registerCallback(IPackageInstallerCallback callback, int userId) throws RemoteException {
        }

        public void unregisterCallback(IPackageInstallerCallback callback) throws RemoteException {
        }

        public void uninstall(VersionedPackage versionedPackage, String callerPackageName, int flags, IntentSender statusReceiver, int userId) throws RemoteException {
        }

        public void installExistingPackage(String packageName, int installFlags, int installReason, IntentSender statusReceiver, int userId, List<String> list) throws RemoteException {
        }

        public void setPermissionsResult(int sessionId, boolean accepted) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPackageInstaller {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstaller";
        static final int TRANSACTION_abandonSession = 4;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_getAllSessions = 7;
        static final int TRANSACTION_getMySessions = 8;
        static final int TRANSACTION_getSessionInfo = 6;
        static final int TRANSACTION_getStagedSessions = 9;
        static final int TRANSACTION_installExistingPackage = 13;
        static final int TRANSACTION_openSession = 5;
        static final int TRANSACTION_registerCallback = 10;
        static final int TRANSACTION_setPermissionsResult = 14;
        static final int TRANSACTION_uninstall = 12;
        static final int TRANSACTION_unregisterCallback = 11;
        static final int TRANSACTION_updateSessionAppIcon = 2;
        static final int TRANSACTION_updateSessionAppLabel = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageInstaller asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPackageInstaller)) {
                return new Proxy(obj);
            }
            return (IPackageInstaller) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createSession";
                case 2:
                    return "updateSessionAppIcon";
                case 3:
                    return "updateSessionAppLabel";
                case 4:
                    return "abandonSession";
                case 5:
                    return "openSession";
                case 6:
                    return "getSessionInfo";
                case 7:
                    return "getAllSessions";
                case 8:
                    return "getMySessions";
                case 9:
                    return "getStagedSessions";
                case 10:
                    return "registerCallback";
                case 11:
                    return "unregisterCallback";
                case 12:
                    return "uninstall";
                case 13:
                    return "installExistingPackage";
                case 14:
                    return "setPermissionsResult";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.content.pm.PackageInstaller$SessionParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.graphics.Bitmap} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: android.os.IBinder} */
        /* JADX WARNING: type inference failed for: r2v4, types: [android.content.pm.PackageInstaller$SessionParams] */
        /* JADX WARNING: type inference failed for: r2v7, types: [android.graphics.Bitmap] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "android.content.pm.IPackageInstaller"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x01ba
                r0 = 0
                r2 = 0
                switch(r8) {
                    case 1: goto L_0x0192;
                    case 2: goto L_0x0172;
                    case 3: goto L_0x0160;
                    case 4: goto L_0x0152;
                    case 5: goto L_0x0139;
                    case 6: goto L_0x011e;
                    case 7: goto L_0x0103;
                    case 8: goto L_0x00e4;
                    case 9: goto L_0x00cd;
                    case 10: goto L_0x00b7;
                    case 11: goto L_0x00a5;
                    case 12: goto L_0x0068;
                    case 13: goto L_0x0030;
                    case 14: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0029
                r0 = r12
            L_0x0029:
                r7.setPermissionsResult(r1, r0)
                r21.writeNoException()
                return r12
            L_0x0030:
                r9.enforceInterface(r11)
                java.lang.String r13 = r20.readString()
                int r14 = r20.readInt()
                int r15 = r20.readInt()
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x004f
                android.os.Parcelable$Creator<android.content.IntentSender> r0 = android.content.IntentSender.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.IntentSender r0 = (android.content.IntentSender) r0
                r4 = r0
                goto L_0x0050
            L_0x004f:
                r4 = r2
            L_0x0050:
                int r16 = r20.readInt()
                java.util.ArrayList r17 = r20.createStringArrayList()
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r5 = r16
                r6 = r17
                r0.installExistingPackage(r1, r2, r3, r4, r5, r6)
                r21.writeNoException()
                return r12
            L_0x0068:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x007b
                android.os.Parcelable$Creator<android.content.pm.VersionedPackage> r0 = android.content.pm.VersionedPackage.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.pm.VersionedPackage r0 = (android.content.pm.VersionedPackage) r0
                r1 = r0
                goto L_0x007c
            L_0x007b:
                r1 = r2
            L_0x007c:
                java.lang.String r6 = r20.readString()
                int r13 = r20.readInt()
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0094
                android.os.Parcelable$Creator<android.content.IntentSender> r0 = android.content.IntentSender.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.IntentSender r0 = (android.content.IntentSender) r0
                r4 = r0
                goto L_0x0095
            L_0x0094:
                r4 = r2
            L_0x0095:
                int r14 = r20.readInt()
                r0 = r18
                r2 = r6
                r3 = r13
                r5 = r14
                r0.uninstall(r1, r2, r3, r4, r5)
                r21.writeNoException()
                return r12
            L_0x00a5:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.content.pm.IPackageInstallerCallback r0 = android.content.pm.IPackageInstallerCallback.Stub.asInterface(r0)
                r7.unregisterCallback(r0)
                r21.writeNoException()
                return r12
            L_0x00b7:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.content.pm.IPackageInstallerCallback r0 = android.content.pm.IPackageInstallerCallback.Stub.asInterface(r0)
                int r1 = r20.readInt()
                r7.registerCallback(r0, r1)
                r21.writeNoException()
                return r12
            L_0x00cd:
                r9.enforceInterface(r11)
                android.content.pm.ParceledListSlice r1 = r18.getStagedSessions()
                r21.writeNoException()
                if (r1 == 0) goto L_0x00e0
                r10.writeInt(r12)
                r1.writeToParcel(r10, r12)
                goto L_0x00e3
            L_0x00e0:
                r10.writeInt(r0)
            L_0x00e3:
                return r12
            L_0x00e4:
                r9.enforceInterface(r11)
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                android.content.pm.ParceledListSlice r3 = r7.getMySessions(r1, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x00ff
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0102
            L_0x00ff:
                r10.writeInt(r0)
            L_0x0102:
                return r12
            L_0x0103:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                android.content.pm.ParceledListSlice r2 = r7.getAllSessions(r1)
                r21.writeNoException()
                if (r2 == 0) goto L_0x011a
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x011d
            L_0x011a:
                r10.writeInt(r0)
            L_0x011d:
                return r12
            L_0x011e:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                android.content.pm.PackageInstaller$SessionInfo r2 = r7.getSessionInfo(r1)
                r21.writeNoException()
                if (r2 == 0) goto L_0x0135
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x0138
            L_0x0135:
                r10.writeInt(r0)
            L_0x0138:
                return r12
            L_0x0139:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                android.content.pm.IPackageInstallerSession r1 = r7.openSession(r0)
                r21.writeNoException()
                if (r1 == 0) goto L_0x014e
                android.os.IBinder r2 = r1.asBinder()
            L_0x014e:
                r10.writeStrongBinder(r2)
                return r12
            L_0x0152:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                r7.abandonSession(r0)
                r21.writeNoException()
                return r12
            L_0x0160:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                java.lang.String r1 = r20.readString()
                r7.updateSessionAppLabel(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0172:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0189
                android.os.Parcelable$Creator<android.graphics.Bitmap> r1 = android.graphics.Bitmap.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r2 = r1
                android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2
                goto L_0x018a
            L_0x0189:
            L_0x018a:
                r1 = r2
                r7.updateSessionAppIcon(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0192:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x01a5
                android.os.Parcelable$Creator<android.content.pm.PackageInstaller$SessionParams> r0 = android.content.pm.PackageInstaller.SessionParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r2 = r0
                android.content.pm.PackageInstaller$SessionParams r2 = (android.content.pm.PackageInstaller.SessionParams) r2
                goto L_0x01a6
            L_0x01a5:
            L_0x01a6:
                r0 = r2
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                int r3 = r7.createSession(r0, r1, r2)
                r21.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x01ba:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.IPackageInstaller.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPackageInstaller {
            public static IPackageInstaller sDefaultImpl;
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

            public int createSession(PackageInstaller.SessionParams params, String installerPackageName, int userId) throws RemoteException {
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
                    _data.writeString(installerPackageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createSession(params, installerPackageName, userId);
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

            public void updateSessionAppIcon(int sessionId, Bitmap appIcon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (appIcon != null) {
                        _data.writeInt(1);
                        appIcon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateSessionAppIcon(sessionId, appIcon);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateSessionAppLabel(int sessionId, String appLabel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeString(appLabel);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateSessionAppLabel(sessionId, appLabel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abandonSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().abandonSession(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPackageInstallerSession openSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openSession(sessionId);
                    }
                    _reply.readException();
                    IPackageInstallerSession _result = IPackageInstallerSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PackageInstaller.SessionInfo getSessionInfo(int sessionId) throws RemoteException {
                PackageInstaller.SessionInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSessionInfo(sessionId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PackageInstaller.SessionInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PackageInstaller.SessionInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAllSessions(int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllSessions(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getMySessions(String installerPackageName, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(installerPackageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMySessions(installerPackageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getStagedSessions() throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStagedSessions();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerCallback(IPackageInstallerCallback callback, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerCallback(callback, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterCallback(IPackageInstallerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void uninstall(VersionedPackage versionedPackage, String callerPackageName, int flags, IntentSender statusReceiver, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (versionedPackage != null) {
                        _data.writeInt(1);
                        versionedPackage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callerPackageName);
                    _data.writeInt(flags);
                    if (statusReceiver != null) {
                        _data.writeInt(1);
                        statusReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().uninstall(versionedPackage, callerPackageName, flags, statusReceiver, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void installExistingPackage(String packageName, int installFlags, int installReason, IntentSender statusReceiver, int userId, List<String> whiteListedPermissions) throws RemoteException {
                IntentSender intentSender = statusReceiver;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(installFlags);
                        } catch (Throwable th) {
                            th = th;
                            int i = installReason;
                            int i2 = userId;
                            List<String> list = whiteListedPermissions;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(installReason);
                            if (intentSender != null) {
                                _data.writeInt(1);
                                intentSender.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeInt(userId);
                            } catch (Throwable th2) {
                                th = th2;
                                List<String> list2 = whiteListedPermissions;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeStringList(whiteListedPermissions);
                                if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().installExistingPackage(packageName, installFlags, installReason, statusReceiver, userId, whiteListedPermissions);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = userId;
                            List<String> list22 = whiteListedPermissions;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = installFlags;
                        int i4 = installReason;
                        int i222 = userId;
                        List<String> list222 = whiteListedPermissions;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str = packageName;
                    int i32 = installFlags;
                    int i42 = installReason;
                    int i2222 = userId;
                    List<String> list2222 = whiteListedPermissions;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void setPermissionsResult(int sessionId, boolean accepted) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(accepted);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPermissionsResult(sessionId, accepted);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPackageInstaller impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPackageInstaller getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
