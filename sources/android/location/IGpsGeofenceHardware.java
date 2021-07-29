package android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGpsGeofenceHardware extends IInterface {
    boolean addCircularHardwareGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) throws RemoteException;

    boolean isHardwareGeofenceSupported() throws RemoteException;

    boolean pauseHardwareGeofence(int i) throws RemoteException;

    boolean removeHardwareGeofence(int i) throws RemoteException;

    boolean resumeHardwareGeofence(int i, int i2) throws RemoteException;

    public static class Default implements IGpsGeofenceHardware {
        public boolean isHardwareGeofenceSupported() throws RemoteException {
            return false;
        }

        public boolean addCircularHardwareGeofence(int geofenceId, double latitude, double longitude, double radius, int lastTransition, int monitorTransition, int notificationResponsiveness, int unknownTimer) throws RemoteException {
            return false;
        }

        public boolean removeHardwareGeofence(int geofenceId) throws RemoteException {
            return false;
        }

        public boolean pauseHardwareGeofence(int geofenceId) throws RemoteException {
            return false;
        }

        public boolean resumeHardwareGeofence(int geofenceId, int monitorTransition) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGpsGeofenceHardware {
        private static final String DESCRIPTOR = "android.location.IGpsGeofenceHardware";
        static final int TRANSACTION_addCircularHardwareGeofence = 2;
        static final int TRANSACTION_isHardwareGeofenceSupported = 1;
        static final int TRANSACTION_pauseHardwareGeofence = 4;
        static final int TRANSACTION_removeHardwareGeofence = 3;
        static final int TRANSACTION_resumeHardwareGeofence = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGpsGeofenceHardware asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IGpsGeofenceHardware)) {
                return new Proxy(obj);
            }
            return (IGpsGeofenceHardware) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isHardwareGeofenceSupported";
                case 2:
                    return "addCircularHardwareGeofence";
                case 3:
                    return "removeHardwareGeofence";
                case 4:
                    return "pauseHardwareGeofence";
                case 5:
                    return "resumeHardwareGeofence";
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
                r12 = r28
                r13 = r29
                r14 = r30
                r15 = r31
                java.lang.String r11 = "android.location.IGpsGeofenceHardware"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r16 = 1
                if (r13 == r0) goto L_0x00a3
                switch(r13) {
                    case 1: goto L_0x0094;
                    case 2: goto L_0x0053;
                    case 3: goto L_0x0041;
                    case 4: goto L_0x002f;
                    case 5: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r29, r30, r31, r32)
                return r0
            L_0x0019:
                r14.enforceInterface(r11)
                int r0 = r30.readInt()
                int r1 = r30.readInt()
                boolean r2 = r12.resumeHardwareGeofence(r0, r1)
                r31.writeNoException()
                r15.writeInt(r2)
                return r16
            L_0x002f:
                r14.enforceInterface(r11)
                int r0 = r30.readInt()
                boolean r1 = r12.pauseHardwareGeofence(r0)
                r31.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x0041:
                r14.enforceInterface(r11)
                int r0 = r30.readInt()
                boolean r1 = r12.removeHardwareGeofence(r0)
                r31.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x0053:
                r14.enforceInterface(r11)
                int r17 = r30.readInt()
                double r18 = r30.readDouble()
                double r20 = r30.readDouble()
                double r22 = r30.readDouble()
                int r24 = r30.readInt()
                int r25 = r30.readInt()
                int r26 = r30.readInt()
                int r27 = r30.readInt()
                r0 = r28
                r1 = r17
                r2 = r18
                r4 = r20
                r6 = r22
                r8 = r24
                r9 = r25
                r10 = r26
                r12 = r11
                r11 = r27
                boolean r0 = r0.addCircularHardwareGeofence(r1, r2, r4, r6, r8, r9, r10, r11)
                r31.writeNoException()
                r15.writeInt(r0)
                return r16
            L_0x0094:
                r12 = r11
                r14.enforceInterface(r12)
                boolean r0 = r28.isHardwareGeofenceSupported()
                r31.writeNoException()
                r15.writeInt(r0)
                return r16
            L_0x00a3:
                r12 = r11
                r15.writeString(r12)
                return r16
            */
            throw new UnsupportedOperationException("Method not decompiled: android.location.IGpsGeofenceHardware.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IGpsGeofenceHardware {
            public static IGpsGeofenceHardware sDefaultImpl;
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

            public boolean isHardwareGeofenceSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHardwareGeofenceSupported();
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

            public boolean addCircularHardwareGeofence(int geofenceId, double latitude, double longitude, double radius, int lastTransition, int monitorTransition, int notificationResponsiveness, int unknownTimer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(geofenceId);
                        _data.writeDouble(latitude);
                        _data.writeDouble(longitude);
                        _data.writeDouble(radius);
                        _data.writeInt(lastTransition);
                        _data.writeInt(monitorTransition);
                        _data.writeInt(notificationResponsiveness);
                        _data.writeInt(unknownTimer);
                        boolean z = false;
                        if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                z = true;
                            }
                            boolean _result = z;
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        boolean addCircularHardwareGeofence = Stub.getDefaultImpl().addCircularHardwareGeofence(geofenceId, latitude, longitude, radius, lastTransition, monitorTransition, notificationResponsiveness, unknownTimer);
                        _reply.recycle();
                        _data.recycle();
                        return addCircularHardwareGeofence;
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    int i = geofenceId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean removeHardwareGeofence(int geofenceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeHardwareGeofence(geofenceId);
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

            public boolean pauseHardwareGeofence(int geofenceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pauseHardwareGeofence(geofenceId);
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

            public boolean resumeHardwareGeofence(int geofenceId, int monitorTransition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(geofenceId);
                    _data.writeInt(monitorTransition);
                    boolean z = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resumeHardwareGeofence(geofenceId, monitorTransition);
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

        public static boolean setDefaultImpl(IGpsGeofenceHardware impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IGpsGeofenceHardware getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
