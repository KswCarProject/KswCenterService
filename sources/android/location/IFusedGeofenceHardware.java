package android.location;

import android.hardware.location.GeofenceHardwareRequestParcelable;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFusedGeofenceHardware extends IInterface {
    void addGeofences(GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws RemoteException;

    boolean isSupported() throws RemoteException;

    void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void pauseMonitoringGeofence(int i) throws RemoteException;

    void removeGeofences(int[] iArr) throws RemoteException;

    void resumeMonitoringGeofence(int i, int i2) throws RemoteException;

    public static class Default implements IFusedGeofenceHardware {
        public boolean isSupported() throws RemoteException {
            return false;
        }

        public void addGeofences(GeofenceHardwareRequestParcelable[] geofenceRequestsArray) throws RemoteException {
        }

        public void removeGeofences(int[] geofenceIds) throws RemoteException {
        }

        public void pauseMonitoringGeofence(int geofenceId) throws RemoteException {
        }

        public void resumeMonitoringGeofence(int geofenceId, int monitorTransitions) throws RemoteException {
        }

        public void modifyGeofenceOptions(int geofenceId, int lastTransition, int monitorTransitions, int notificationResponsiveness, int unknownTimer, int sourcesToUse) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFusedGeofenceHardware {
        private static final String DESCRIPTOR = "android.location.IFusedGeofenceHardware";
        static final int TRANSACTION_addGeofences = 2;
        static final int TRANSACTION_isSupported = 1;
        static final int TRANSACTION_modifyGeofenceOptions = 6;
        static final int TRANSACTION_pauseMonitoringGeofence = 4;
        static final int TRANSACTION_removeGeofences = 3;
        static final int TRANSACTION_resumeMonitoringGeofence = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFusedGeofenceHardware asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFusedGeofenceHardware)) {
                return new Proxy(obj);
            }
            return (IFusedGeofenceHardware) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isSupported";
                case 2:
                    return "addGeofences";
                case 3:
                    return "removeGeofences";
                case 4:
                    return "pauseMonitoringGeofence";
                case 5:
                    return "resumeMonitoringGeofence";
                case 6:
                    return "modifyGeofenceOptions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = code;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean _result = isSupported();
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        addGeofences((GeofenceHardwareRequestParcelable[]) parcel.createTypedArray(GeofenceHardwareRequestParcelable.CREATOR));
                        reply.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        removeGeofences(data.createIntArray());
                        reply.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        pauseMonitoringGeofence(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        resumeMonitoringGeofence(data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        modifyGeofenceOptions(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IFusedGeofenceHardware {
            public static IFusedGeofenceHardware sDefaultImpl;
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

            public boolean isSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSupported();
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

            public void addGeofences(GeofenceHardwareRequestParcelable[] geofenceRequestsArray) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(geofenceRequestsArray, 0);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addGeofences(geofenceRequestsArray);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeGeofences(int[] geofenceIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(geofenceIds);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeGeofences(geofenceIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pauseMonitoringGeofence(int geofenceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pauseMonitoringGeofence(geofenceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resumeMonitoringGeofence(int geofenceId, int monitorTransitions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    _data.writeInt(monitorTransitions);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resumeMonitoringGeofence(geofenceId, monitorTransitions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void modifyGeofenceOptions(int geofenceId, int lastTransition, int monitorTransitions, int notificationResponsiveness, int unknownTimer, int sourcesToUse) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(geofenceId);
                        try {
                            _data.writeInt(lastTransition);
                            try {
                                _data.writeInt(monitorTransitions);
                                try {
                                    _data.writeInt(notificationResponsiveness);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = unknownTimer;
                                    int i2 = sourcesToUse;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i3 = notificationResponsiveness;
                                int i4 = unknownTimer;
                                int i22 = sourcesToUse;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i5 = monitorTransitions;
                            int i32 = notificationResponsiveness;
                            int i42 = unknownTimer;
                            int i222 = sourcesToUse;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(unknownTimer);
                            try {
                                _data.writeInt(sourcesToUse);
                                if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().modifyGeofenceOptions(geofenceId, lastTransition, monitorTransitions, notificationResponsiveness, unknownTimer, sourcesToUse);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            int i2222 = sourcesToUse;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        int i6 = lastTransition;
                        int i52 = monitorTransitions;
                        int i322 = notificationResponsiveness;
                        int i422 = unknownTimer;
                        int i22222 = sourcesToUse;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i7 = geofenceId;
                    int i62 = lastTransition;
                    int i522 = monitorTransitions;
                    int i3222 = notificationResponsiveness;
                    int i4222 = unknownTimer;
                    int i222222 = sourcesToUse;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }
        }

        public static boolean setDefaultImpl(IFusedGeofenceHardware impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IFusedGeofenceHardware getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
