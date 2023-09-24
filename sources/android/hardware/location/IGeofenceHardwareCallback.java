package android.hardware.location;

import android.location.Location;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IGeofenceHardwareCallback extends IInterface {
    void onGeofenceAdd(int i, int i2) throws RemoteException;

    void onGeofencePause(int i, int i2) throws RemoteException;

    void onGeofenceRemove(int i, int i2) throws RemoteException;

    void onGeofenceResume(int i, int i2) throws RemoteException;

    void onGeofenceTransition(int i, int i2, Location location, long j, int i3) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IGeofenceHardwareCallback {
        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceTransition(int geofenceId, int transition, Location location, long timestamp, int monitoringType) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceAdd(int geofenceId, int status) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceRemove(int geofenceId, int status) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofencePause(int geofenceId, int status) throws RemoteException {
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceResume(int geofenceId, int status) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IGeofenceHardwareCallback {
        private static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardwareCallback";
        static final int TRANSACTION_onGeofenceAdd = 2;
        static final int TRANSACTION_onGeofencePause = 4;
        static final int TRANSACTION_onGeofenceRemove = 3;
        static final int TRANSACTION_onGeofenceResume = 5;
        static final int TRANSACTION_onGeofenceTransition = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGeofenceHardwareCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IGeofenceHardwareCallback)) {
                return (IGeofenceHardwareCallback) iin;
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
                    return "onGeofenceTransition";
                case 2:
                    return "onGeofenceAdd";
                case 3:
                    return "onGeofenceRemove";
                case 4:
                    return "onGeofencePause";
                case 5:
                    return "onGeofenceResume";
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
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    Location _arg2 = data.readInt() != 0 ? Location.CREATOR.createFromParcel(data) : null;
                    long _arg3 = data.readLong();
                    int _arg4 = data.readInt();
                    onGeofenceTransition(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    onGeofenceAdd(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    onGeofenceRemove(_arg03, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    int _arg14 = data.readInt();
                    onGeofencePause(_arg04, _arg14);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    onGeofenceResume(_arg05, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IGeofenceHardwareCallback {
            public static IGeofenceHardwareCallback sDefaultImpl;
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

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceTransition(int geofenceId, int transition, Location location, long timestamp, int monitoringType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(geofenceId);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(transition);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeLong(timestamp);
                        try {
                            _data.writeInt(monitoringType);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        boolean _status = this.mRemote.transact(1, _data, null, 1);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onGeofenceTransition(geofenceId, transition, location, timestamp, monitoringType);
                            _data.recycle();
                            return;
                        }
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceAdd(int geofenceId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofenceAdd(geofenceId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceRemove(int geofenceId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofenceRemove(geofenceId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofencePause(int geofenceId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofencePause(geofenceId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IGeofenceHardwareCallback
            public void onGeofenceResume(int geofenceId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofenceResume(geofenceId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IGeofenceHardwareCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IGeofenceHardwareCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
