package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IWallpaperManager extends IInterface {
    void clearWallpaper(String str, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    int getHeightHint(int i) throws RemoteException;

    String getName() throws RemoteException;

    @UnsupportedAppUsage
    ParcelFileDescriptor getWallpaper(String str, IWallpaperManagerCallback iWallpaperManagerCallback, int i, Bundle bundle, int i2) throws RemoteException;

    WallpaperColors getWallpaperColors(int i, int i2, int i3) throws RemoteException;

    int getWallpaperIdForUser(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    WallpaperInfo getWallpaperInfo(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getWidthHint(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean hasNamedWallpaper(String str) throws RemoteException;

    boolean isSetWallpaperAllowed(String str) throws RemoteException;

    boolean isWallpaperBackupEligible(int i, int i2) throws RemoteException;

    boolean isWallpaperSupported(String str) throws RemoteException;

    void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException;

    void setDimensionHints(int i, int i2, String str, int i3) throws RemoteException;

    void setDisplayPadding(Rect rect, String str, int i) throws RemoteException;

    void setInAmbientMode(boolean z, long j) throws RemoteException;

    boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException;

    ParcelFileDescriptor setWallpaper(String str, String str2, Rect rect, boolean z, Bundle bundle, int i, IWallpaperManagerCallback iWallpaperManagerCallback, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void setWallpaperComponent(ComponentName componentName) throws RemoteException;

    void setWallpaperComponentChecked(ComponentName componentName, String str, int i) throws RemoteException;

    void settingsRestored() throws RemoteException;

    void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws RemoteException;

    public static class Default implements IWallpaperManager {
        public ParcelFileDescriptor setWallpaper(String name, String callingPackage, Rect cropHint, boolean allowBackup, Bundle extras, int which, IWallpaperManagerCallback completion, int userId) throws RemoteException {
            return null;
        }

        public void setWallpaperComponentChecked(ComponentName name, String callingPackage, int userId) throws RemoteException {
        }

        public void setWallpaperComponent(ComponentName name) throws RemoteException {
        }

        public ParcelFileDescriptor getWallpaper(String callingPkg, IWallpaperManagerCallback cb, int which, Bundle outParams, int userId) throws RemoteException {
            return null;
        }

        public int getWallpaperIdForUser(int which, int userId) throws RemoteException {
            return 0;
        }

        public WallpaperInfo getWallpaperInfo(int userId) throws RemoteException {
            return null;
        }

        public void clearWallpaper(String callingPackage, int which, int userId) throws RemoteException {
        }

        public boolean hasNamedWallpaper(String name) throws RemoteException {
            return false;
        }

        public void setDimensionHints(int width, int height, String callingPackage, int displayId) throws RemoteException {
        }

        public int getWidthHint(int displayId) throws RemoteException {
            return 0;
        }

        public int getHeightHint(int displayId) throws RemoteException {
            return 0;
        }

        public void setDisplayPadding(Rect padding, String callingPackage, int displayId) throws RemoteException {
        }

        public String getName() throws RemoteException {
            return null;
        }

        public void settingsRestored() throws RemoteException {
        }

        public boolean isWallpaperSupported(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isSetWallpaperAllowed(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isWallpaperBackupEligible(int which, int userId) throws RemoteException {
            return false;
        }

        public boolean setLockWallpaperCallback(IWallpaperManagerCallback cb) throws RemoteException {
            return false;
        }

        public WallpaperColors getWallpaperColors(int which, int userId, int displayId) throws RemoteException {
            return null;
        }

        public void registerWallpaperColorsCallback(IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {
        }

        public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {
        }

        public void setInAmbientMode(boolean inAmbientMode, long animationDuration) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWallpaperManager {
        private static final String DESCRIPTOR = "android.app.IWallpaperManager";
        static final int TRANSACTION_clearWallpaper = 7;
        static final int TRANSACTION_getHeightHint = 11;
        static final int TRANSACTION_getName = 13;
        static final int TRANSACTION_getWallpaper = 4;
        static final int TRANSACTION_getWallpaperColors = 19;
        static final int TRANSACTION_getWallpaperIdForUser = 5;
        static final int TRANSACTION_getWallpaperInfo = 6;
        static final int TRANSACTION_getWidthHint = 10;
        static final int TRANSACTION_hasNamedWallpaper = 8;
        static final int TRANSACTION_isSetWallpaperAllowed = 16;
        static final int TRANSACTION_isWallpaperBackupEligible = 17;
        static final int TRANSACTION_isWallpaperSupported = 15;
        static final int TRANSACTION_registerWallpaperColorsCallback = 20;
        static final int TRANSACTION_setDimensionHints = 9;
        static final int TRANSACTION_setDisplayPadding = 12;
        static final int TRANSACTION_setInAmbientMode = 22;
        static final int TRANSACTION_setLockWallpaperCallback = 18;
        static final int TRANSACTION_setWallpaper = 1;
        static final int TRANSACTION_setWallpaperComponent = 3;
        static final int TRANSACTION_setWallpaperComponentChecked = 2;
        static final int TRANSACTION_settingsRestored = 14;
        static final int TRANSACTION_unregisterWallpaperColorsCallback = 21;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWallpaperManager)) {
                return new Proxy(obj);
            }
            return (IWallpaperManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setWallpaper";
                case 2:
                    return "setWallpaperComponentChecked";
                case 3:
                    return "setWallpaperComponent";
                case 4:
                    return "getWallpaper";
                case 5:
                    return "getWallpaperIdForUser";
                case 6:
                    return "getWallpaperInfo";
                case 7:
                    return "clearWallpaper";
                case 8:
                    return "hasNamedWallpaper";
                case 9:
                    return "setDimensionHints";
                case 10:
                    return "getWidthHint";
                case 11:
                    return "getHeightHint";
                case 12:
                    return "setDisplayPadding";
                case 13:
                    return "getName";
                case 14:
                    return "settingsRestored";
                case 15:
                    return "isWallpaperSupported";
                case 16:
                    return "isSetWallpaperAllowed";
                case 17:
                    return "isWallpaperBackupEligible";
                case 18:
                    return "setLockWallpaperCallback";
                case 19:
                    return "getWallpaperColors";
                case 20:
                    return "registerWallpaperColorsCallback";
                case 21:
                    return "unregisterWallpaperColorsCallback";
                case 22:
                    return "setInAmbientMode";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.graphics.Rect} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v48 */
        /* JADX WARNING: type inference failed for: r0v49 */
        /* JADX WARNING: type inference failed for: r0v50 */
        /* JADX WARNING: type inference failed for: r0v51 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r23, android.os.Parcel r24, android.os.Parcel r25, int r26) throws android.os.RemoteException {
            /*
                r22 = this;
                r9 = r22
                r10 = r23
                r11 = r24
                r12 = r25
                java.lang.String r13 = "android.app.IWallpaperManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r14 = 1
                if (r10 == r0) goto L_0x028d
                r0 = 0
                r15 = 0
                switch(r10) {
                    case 1: goto L_0x0226;
                    case 2: goto L_0x0204;
                    case 3: goto L_0x01ea;
                    case 4: goto L_0x01a6;
                    case 5: goto L_0x0190;
                    case 6: goto L_0x0175;
                    case 7: goto L_0x015f;
                    case 8: goto L_0x014d;
                    case 9: goto L_0x0133;
                    case 10: goto L_0x0121;
                    case 11: goto L_0x010f;
                    case 12: goto L_0x00ed;
                    case 13: goto L_0x00df;
                    case 14: goto L_0x00d5;
                    case 15: goto L_0x00c3;
                    case 16: goto L_0x00b1;
                    case 17: goto L_0x009b;
                    case 18: goto L_0x0085;
                    case 19: goto L_0x0062;
                    case 20: goto L_0x0048;
                    case 21: goto L_0x002e;
                    case 22: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r23, r24, r25, r26)
                return r0
            L_0x001a:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0025
                r15 = r14
            L_0x0025:
                r0 = r15
                long r1 = r24.readLong()
                r9.setInAmbientMode(r0, r1)
                return r14
            L_0x002e:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r24.readStrongBinder()
                android.app.IWallpaperManagerCallback r0 = android.app.IWallpaperManagerCallback.Stub.asInterface(r0)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                r9.unregisterWallpaperColorsCallback(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x0048:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r24.readStrongBinder()
                android.app.IWallpaperManagerCallback r0 = android.app.IWallpaperManagerCallback.Stub.asInterface(r0)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                r9.registerWallpaperColorsCallback(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x0062:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                android.app.WallpaperColors r3 = r9.getWallpaperColors(r0, r1, r2)
                r25.writeNoException()
                if (r3 == 0) goto L_0x0081
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x0084
            L_0x0081:
                r12.writeInt(r15)
            L_0x0084:
                return r14
            L_0x0085:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r24.readStrongBinder()
                android.app.IWallpaperManagerCallback r0 = android.app.IWallpaperManagerCallback.Stub.asInterface(r0)
                boolean r1 = r9.setLockWallpaperCallback(r0)
                r25.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x009b:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                boolean r2 = r9.isWallpaperBackupEligible(r0, r1)
                r25.writeNoException()
                r12.writeInt(r2)
                return r14
            L_0x00b1:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                boolean r1 = r9.isSetWallpaperAllowed(r0)
                r25.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x00c3:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                boolean r1 = r9.isWallpaperSupported(r0)
                r25.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x00d5:
                r11.enforceInterface(r13)
                r22.settingsRestored()
                r25.writeNoException()
                return r14
            L_0x00df:
                r11.enforceInterface(r13)
                java.lang.String r0 = r22.getName()
                r25.writeNoException()
                r12.writeString(r0)
                return r14
            L_0x00ed:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x00ff
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x0100
            L_0x00ff:
            L_0x0100:
                java.lang.String r1 = r24.readString()
                int r2 = r24.readInt()
                r9.setDisplayPadding(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x010f:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r9.getHeightHint(r0)
                r25.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x0121:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r9.getWidthHint(r0)
                r25.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x0133:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                java.lang.String r2 = r24.readString()
                int r3 = r24.readInt()
                r9.setDimensionHints(r0, r1, r2, r3)
                r25.writeNoException()
                return r14
            L_0x014d:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                boolean r1 = r9.hasNamedWallpaper(r0)
                r25.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x015f:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                r9.clearWallpaper(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x0175:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                android.app.WallpaperInfo r1 = r9.getWallpaperInfo(r0)
                r25.writeNoException()
                if (r1 == 0) goto L_0x018c
                r12.writeInt(r14)
                r1.writeToParcel(r12, r14)
                goto L_0x018f
            L_0x018c:
                r12.writeInt(r15)
            L_0x018f:
                return r14
            L_0x0190:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r9.getWallpaperIdForUser(r0, r1)
                r25.writeNoException()
                r12.writeInt(r2)
                return r14
            L_0x01a6:
                r11.enforceInterface(r13)
                java.lang.String r6 = r24.readString()
                android.os.IBinder r0 = r24.readStrongBinder()
                android.app.IWallpaperManagerCallback r7 = android.app.IWallpaperManagerCallback.Stub.asInterface(r0)
                int r8 = r24.readInt()
                android.os.Bundle r0 = new android.os.Bundle
                r0.<init>()
                r5 = r0
                int r16 = r24.readInt()
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r5
                r17 = r5
                r5 = r16
                android.os.ParcelFileDescriptor r0 = r0.getWallpaper(r1, r2, r3, r4, r5)
                r25.writeNoException()
                if (r0 == 0) goto L_0x01dd
                r12.writeInt(r14)
                r0.writeToParcel(r12, r14)
                goto L_0x01e0
            L_0x01dd:
                r12.writeInt(r15)
            L_0x01e0:
                r12.writeInt(r14)
                r1 = r17
                r1.writeToParcel(r12, r14)
                return r14
            L_0x01ea:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x01fc
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x01fd
            L_0x01fc:
            L_0x01fd:
                r9.setWallpaperComponent(r0)
                r25.writeNoException()
                return r14
            L_0x0204:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0216
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0217
            L_0x0216:
            L_0x0217:
                java.lang.String r1 = r24.readString()
                int r2 = r24.readInt()
                r9.setWallpaperComponentChecked(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x0226:
                r11.enforceInterface(r13)
                java.lang.String r16 = r24.readString()
                java.lang.String r17 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0241
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x023f:
                r3 = r0
                goto L_0x0242
            L_0x0241:
                goto L_0x023f
            L_0x0242:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x024a
                r4 = r14
                goto L_0x024b
            L_0x024a:
                r4 = r15
            L_0x024b:
                android.os.Bundle r0 = new android.os.Bundle
                r0.<init>()
                r8 = r0
                int r18 = r24.readInt()
                android.os.IBinder r0 = r24.readStrongBinder()
                android.app.IWallpaperManagerCallback r19 = android.app.IWallpaperManagerCallback.Stub.asInterface(r0)
                int r20 = r24.readInt()
                r0 = r22
                r1 = r16
                r2 = r17
                r5 = r8
                r6 = r18
                r7 = r19
                r21 = r8
                r8 = r20
                android.os.ParcelFileDescriptor r0 = r0.setWallpaper(r1, r2, r3, r4, r5, r6, r7, r8)
                r25.writeNoException()
                if (r0 == 0) goto L_0x0280
                r12.writeInt(r14)
                r0.writeToParcel(r12, r14)
                goto L_0x0283
            L_0x0280:
                r12.writeInt(r15)
            L_0x0283:
                r12.writeInt(r14)
                r1 = r21
                r1.writeToParcel(r12, r14)
                return r14
            L_0x028d:
                r12.writeString(r13)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IWallpaperManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWallpaperManager {
            public static IWallpaperManager sDefaultImpl;
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

            public ParcelFileDescriptor setWallpaper(String name, String callingPackage, Rect cropHint, boolean allowBackup, Bundle extras, int which, IWallpaperManagerCallback completion, int userId) throws RemoteException {
                Rect rect = cropHint;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(name);
                        try {
                            _data.writeString(callingPackage);
                            if (rect != null) {
                                _data.writeInt(1);
                                rect.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            boolean z = allowBackup;
                            Bundle bundle = extras;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = callingPackage;
                        boolean z2 = allowBackup;
                        Bundle bundle2 = extras;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(allowBackup ? 1 : 0);
                        _data.writeInt(which);
                        ParcelFileDescriptor _result = null;
                        _data.writeStrongBinder(completion != null ? completion.asBinder() : null);
                        _data.writeInt(userId);
                        if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                            }
                            ParcelFileDescriptor _result2 = _result;
                            if (_reply.readInt() != 0) {
                                try {
                                    extras.readFromParcel(_reply);
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            } else {
                                Bundle bundle3 = extras;
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result2;
                        }
                        ParcelFileDescriptor wallpaper = Stub.getDefaultImpl().setWallpaper(name, callingPackage, cropHint, allowBackup, extras, which, completion, userId);
                        _reply.recycle();
                        _data.recycle();
                        return wallpaper;
                    } catch (Throwable th4) {
                        th = th4;
                        Bundle bundle22 = extras;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str2 = name;
                    String str3 = callingPackage;
                    boolean z22 = allowBackup;
                    Bundle bundle222 = extras;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void setWallpaperComponentChecked(ComponentName name, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (name != null) {
                        _data.writeInt(1);
                        name.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWallpaperComponentChecked(name, callingPackage, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setWallpaperComponent(ComponentName name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (name != null) {
                        _data.writeInt(1);
                        name.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWallpaperComponent(name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getWallpaper(String callingPkg, IWallpaperManagerCallback cb, int which, Bundle outParams, int userId) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    String str = callingPkg;
                    try {
                        _data.writeString(callingPkg);
                        _result = null;
                        _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    } catch (Throwable th) {
                        th = th;
                        int i = which;
                        Bundle bundle = outParams;
                        int i2 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(which);
                        try {
                            _data.writeInt(userId);
                        } catch (Throwable th2) {
                            th = th2;
                            Bundle bundle2 = outParams;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                                }
                                if (_reply.readInt() != 0) {
                                    try {
                                        outParams.readFromParcel(_reply);
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                } else {
                                    Bundle bundle3 = outParams;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            ParcelFileDescriptor wallpaper = Stub.getDefaultImpl().getWallpaper(callingPkg, cb, which, outParams, userId);
                            _reply.recycle();
                            _data.recycle();
                            return wallpaper;
                        } catch (Throwable th4) {
                            th = th4;
                            Bundle bundle22 = outParams;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        Bundle bundle4 = outParams;
                        int i22 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = callingPkg;
                    int i3 = which;
                    Bundle bundle42 = outParams;
                    int i222 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int getWallpaperIdForUser(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWallpaperIdForUser(which, userId);
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

            public WallpaperInfo getWallpaperInfo(int userId) throws RemoteException {
                WallpaperInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWallpaperInfo(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WallpaperInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WallpaperInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearWallpaper(String callingPackage, int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearWallpaper(callingPackage, which, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasNamedWallpaper(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasNamedWallpaper(name);
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

            public void setDimensionHints(int width, int height, String callingPackage, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeString(callingPackage);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDimensionHints(width, height, callingPackage, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getWidthHint(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWidthHint(displayId);
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

            public int getHeightHint(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHeightHint(displayId);
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

            public void setDisplayPadding(Rect padding, String callingPackage, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (padding != null) {
                        _data.writeInt(1);
                        padding.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDisplayPadding(padding, callingPackage, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getName();
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

            public void settingsRestored() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().settingsRestored();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWallpaperSupported(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWallpaperSupported(callingPackage);
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

            public boolean isSetWallpaperAllowed(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSetWallpaperAllowed(callingPackage);
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

            public boolean isWallpaperBackupEligible(int which, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWallpaperBackupEligible(which, userId);
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

            public boolean setLockWallpaperCallback(IWallpaperManagerCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLockWallpaperCallback(cb);
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

            public WallpaperColors getWallpaperColors(int which, int userId, int displayId) throws RemoteException {
                WallpaperColors _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWallpaperColors(which, userId, displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WallpaperColors.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WallpaperColors _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerWallpaperColorsCallback(IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerWallpaperColorsCallback(cb, userId, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback cb, int userId, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterWallpaperColorsCallback(cb, userId, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInAmbientMode(boolean inAmbientMode, long animationDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(inAmbientMode);
                    _data.writeLong(animationDuration);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setInAmbientMode(inAmbientMode, animationDuration);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWallpaperManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWallpaperManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
