package android.service.wallpaper;

import android.graphics.Rect;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.service.wallpaper.IWallpaperConnection;

/* loaded from: classes3.dex */
public interface IWallpaperService extends IInterface {
    void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4) throws RemoteException;

    void detach() throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IWallpaperService {
        @Override // android.service.wallpaper.IWallpaperService
        public void attach(IWallpaperConnection connection, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void detach() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IWallpaperService {
        private static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperService";
        static final int TRANSACTION_attach = 1;
        static final int TRANSACTION_detach = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWallpaperService)) {
                return (IWallpaperService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "attach";
                case 2:
                    return "detach";
                default:
                    return null;
            }
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    IWallpaperConnection _arg0 = IWallpaperConnection.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg1 = data.readStrongBinder();
                    int _arg2 = data.readInt();
                    boolean _arg3 = data.readInt() != 0;
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    Rect _arg6 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    int _arg7 = data.readInt();
                    attach(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    detach();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IWallpaperService {
            public static IWallpaperService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void attach(IWallpaperConnection connection, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(windowToken);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(windowType);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(isPreview ? 1 : 0);
                    _data.writeInt(reqWidth);
                    _data.writeInt(reqHeight);
                    if (padding != null) {
                        _data.writeInt(1);
                        padding.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(displayId);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attach(connection, windowToken, windowType, isPreview, reqWidth, reqHeight, padding, displayId);
                        _data.recycle();
                        return;
                    }
                    _data.recycle();
                } catch (Throwable th4) {
                    th = th4;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void detach() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().detach();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWallpaperService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IWallpaperService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
