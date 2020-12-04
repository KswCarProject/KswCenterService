package android.service.wallpaper;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;

public interface IWallpaperEngine extends IInterface {
    @UnsupportedAppUsage
    void destroy() throws RemoteException;

    @UnsupportedAppUsage
    void dispatchPointer(MotionEvent motionEvent) throws RemoteException;

    @UnsupportedAppUsage
    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) throws RemoteException;

    void requestWallpaperColors() throws RemoteException;

    void setDesiredSize(int i, int i2) throws RemoteException;

    void setDisplayPadding(Rect rect) throws RemoteException;

    void setInAmbientMode(boolean z, long j) throws RemoteException;

    @UnsupportedAppUsage
    void setVisibility(boolean z) throws RemoteException;

    public static class Default implements IWallpaperEngine {
        public void setDesiredSize(int width, int height) throws RemoteException {
        }

        public void setDisplayPadding(Rect padding) throws RemoteException {
        }

        public void setVisibility(boolean visible) throws RemoteException {
        }

        public void setInAmbientMode(boolean inAmbientDisplay, long animationDuration) throws RemoteException {
        }

        public void dispatchPointer(MotionEvent event) throws RemoteException {
        }

        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras) throws RemoteException {
        }

        public void requestWallpaperColors() throws RemoteException {
        }

        public void destroy() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWallpaperEngine {
        private static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperEngine";
        static final int TRANSACTION_destroy = 8;
        static final int TRANSACTION_dispatchPointer = 5;
        static final int TRANSACTION_dispatchWallpaperCommand = 6;
        static final int TRANSACTION_requestWallpaperColors = 7;
        static final int TRANSACTION_setDesiredSize = 1;
        static final int TRANSACTION_setDisplayPadding = 2;
        static final int TRANSACTION_setInAmbientMode = 4;
        static final int TRANSACTION_setVisibility = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperEngine asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWallpaperEngine)) {
                return new Proxy(obj);
            }
            return (IWallpaperEngine) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setDesiredSize";
                case 2:
                    return "setDisplayPadding";
                case 3:
                    return "setVisibility";
                case 4:
                    return "setInAmbientMode";
                case 5:
                    return "dispatchPointer";
                case 6:
                    return "dispatchWallpaperCommand";
                case 7:
                    return "requestWallpaperColors";
                case 8:
                    return "destroy";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.view.MotionEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.graphics.Rect} */
        /* JADX WARNING: type inference failed for: r1v10, types: [android.view.MotionEvent] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.service.wallpaper.IWallpaperEngine"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x00b5
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x00a6;
                    case 2: goto L_0x008d;
                    case 3: goto L_0x007e;
                    case 4: goto L_0x006b;
                    case 5: goto L_0x0052;
                    case 6: goto L_0x0025;
                    case 7: goto L_0x001e;
                    case 8: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0017:
                r8.enforceInterface(r9)
                r15.destroy()
                return r10
            L_0x001e:
                r8.enforceInterface(r9)
                r15.requestWallpaperColors()
                return r10
            L_0x0025:
                r8.enforceInterface(r9)
                java.lang.String r11 = r17.readString()
                int r12 = r17.readInt()
                int r13 = r17.readInt()
                int r14 = r17.readInt()
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0048
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r5 = r0
                goto L_0x0049
            L_0x0048:
                r5 = r1
            L_0x0049:
                r0 = r15
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r0.dispatchWallpaperCommand(r1, r2, r3, r4, r5)
                return r10
            L_0x0052:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0065
                android.os.Parcelable$Creator<android.view.MotionEvent> r0 = android.view.MotionEvent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.view.MotionEvent r1 = (android.view.MotionEvent) r1
                goto L_0x0066
            L_0x0065:
            L_0x0066:
                r0 = r1
                r15.dispatchPointer(r0)
                return r10
            L_0x006b:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0076
                r0 = r10
            L_0x0076:
                long r1 = r17.readLong()
                r15.setInAmbientMode(r0, r1)
                return r10
            L_0x007e:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0089
                r0 = r10
            L_0x0089:
                r15.setVisibility(r0)
                return r10
            L_0x008d:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x00a0
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x00a1
            L_0x00a0:
            L_0x00a1:
                r0 = r1
                r15.setDisplayPadding(r0)
                return r10
            L_0x00a6:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                int r1 = r17.readInt()
                r15.setDesiredSize(r0, r1)
                return r10
            L_0x00b5:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.wallpaper.IWallpaperEngine.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWallpaperEngine {
            public static IWallpaperEngine sDefaultImpl;
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

            public void setDesiredSize(int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDesiredSize(width, height);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setDisplayPadding(Rect padding) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (padding != null) {
                        _data.writeInt(1);
                        padding.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setDisplayPadding(padding);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setVisibility(visible);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setInAmbientMode(boolean inAmbientDisplay, long animationDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(inAmbientDisplay);
                    _data.writeLong(animationDuration);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setInAmbientMode(inAmbientDisplay, animationDuration);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchPointer(MotionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchPointer(event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeInt(z);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchWallpaperCommand(action, x, y, z, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestWallpaperColors() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestWallpaperColors();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void destroy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().destroy();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWallpaperEngine impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWallpaperEngine getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
