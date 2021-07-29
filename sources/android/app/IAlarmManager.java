package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AlarmManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.WorkSource;

public interface IAlarmManager extends IInterface {
    long currentNetworkTimeMillis() throws RemoteException;

    @UnsupportedAppUsage
    AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws RemoteException;

    long getNextWakeFromIdleTime() throws RemoteException;

    void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException;

    @UnsupportedAppUsage
    void set(String str, int i, long j, long j2, long j3, int i2, PendingIntent pendingIntent, IAlarmListener iAlarmListener, String str2, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClockInfo) throws RemoteException;

    @UnsupportedAppUsage
    boolean setTime(long j) throws RemoteException;

    void setTimeZone(String str) throws RemoteException;

    public static class Default implements IAlarmManager {
        public void set(String callingPackage, int type, long triggerAtTime, long windowLength, long interval, int flags, PendingIntent operation, IAlarmListener listener, String listenerTag, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClock) throws RemoteException {
        }

        public boolean setTime(long millis) throws RemoteException {
            return false;
        }

        public void setTimeZone(String zone) throws RemoteException {
        }

        public void remove(PendingIntent operation, IAlarmListener listener) throws RemoteException {
        }

        public long getNextWakeFromIdleTime() throws RemoteException {
            return 0;
        }

        public AlarmManager.AlarmClockInfo getNextAlarmClock(int userId) throws RemoteException {
            return null;
        }

        public long currentNetworkTimeMillis() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAlarmManager {
        private static final String DESCRIPTOR = "android.app.IAlarmManager";
        static final int TRANSACTION_currentNetworkTimeMillis = 7;
        static final int TRANSACTION_getNextAlarmClock = 6;
        static final int TRANSACTION_getNextWakeFromIdleTime = 5;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_set = 1;
        static final int TRANSACTION_setTime = 2;
        static final int TRANSACTION_setTimeZone = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAlarmManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAlarmManager)) {
                return new Proxy(obj);
            }
            return (IAlarmManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "set";
                case 2:
                    return "setTime";
                case 3:
                    return "setTimeZone";
                case 4:
                    return "remove";
                case 5:
                    return "getNextWakeFromIdleTime";
                case 6:
                    return "getNextAlarmClock";
                case 7:
                    return "currentNetworkTimeMillis";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: android.app.AlarmManager$AlarmClockInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.app.PendingIntent} */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.app.AlarmManager$AlarmClockInfo] */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r30, android.os.Parcel r31, android.os.Parcel r32, int r33) throws android.os.RemoteException {
            /*
                r29 = this;
                r15 = r29
                r12 = r30
                r11 = r31
                r9 = r32
                java.lang.String r7 = "android.app.IAlarmManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r8 = 1
                if (r12 == r0) goto L_0x010c
                r0 = 0
                switch(r12) {
                    case 1: goto L_0x0093;
                    case 2: goto L_0x0081;
                    case 3: goto L_0x0073;
                    case 4: goto L_0x0051;
                    case 5: goto L_0x0043;
                    case 6: goto L_0x0027;
                    case 7: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r30, r31, r32, r33)
                return r0
            L_0x0019:
                r11.enforceInterface(r7)
                long r0 = r29.currentNetworkTimeMillis()
                r32.writeNoException()
                r9.writeLong(r0)
                return r8
            L_0x0027:
                r11.enforceInterface(r7)
                int r0 = r31.readInt()
                android.app.AlarmManager$AlarmClockInfo r1 = r15.getNextAlarmClock(r0)
                r32.writeNoException()
                if (r1 == 0) goto L_0x003e
                r9.writeInt(r8)
                r1.writeToParcel(r9, r8)
                goto L_0x0042
            L_0x003e:
                r2 = 0
                r9.writeInt(r2)
            L_0x0042:
                return r8
            L_0x0043:
                r11.enforceInterface(r7)
                long r0 = r29.getNextWakeFromIdleTime()
                r32.writeNoException()
                r9.writeLong(r0)
                return r8
            L_0x0051:
                r11.enforceInterface(r7)
                int r1 = r31.readInt()
                if (r1 == 0) goto L_0x0063
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                goto L_0x0064
            L_0x0063:
            L_0x0064:
                android.os.IBinder r1 = r31.readStrongBinder()
                android.app.IAlarmListener r1 = android.app.IAlarmListener.Stub.asInterface(r1)
                r15.remove(r0, r1)
                r32.writeNoException()
                return r8
            L_0x0073:
                r11.enforceInterface(r7)
                java.lang.String r0 = r31.readString()
                r15.setTimeZone(r0)
                r32.writeNoException()
                return r8
            L_0x0081:
                r11.enforceInterface(r7)
                long r0 = r31.readLong()
                boolean r2 = r15.setTime(r0)
                r32.writeNoException()
                r9.writeInt(r2)
                return r8
            L_0x0093:
                r11.enforceInterface(r7)
                java.lang.String r16 = r31.readString()
                int r17 = r31.readInt()
                long r18 = r31.readLong()
                long r20 = r31.readLong()
                long r22 = r31.readLong()
                int r24 = r31.readInt()
                int r1 = r31.readInt()
                if (r1 == 0) goto L_0x00be
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                r10 = r1
                goto L_0x00bf
            L_0x00be:
                r10 = r0
            L_0x00bf:
                android.os.IBinder r1 = r31.readStrongBinder()
                android.app.IAlarmListener r25 = android.app.IAlarmListener.Stub.asInterface(r1)
                java.lang.String r26 = r31.readString()
                int r1 = r31.readInt()
                if (r1 == 0) goto L_0x00db
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.WorkSource r1 = (android.os.WorkSource) r1
                r13 = r1
                goto L_0x00dc
            L_0x00db:
                r13 = r0
            L_0x00dc:
                int r1 = r31.readInt()
                if (r1 == 0) goto L_0x00ec
                android.os.Parcelable$Creator<android.app.AlarmManager$AlarmClockInfo> r0 = android.app.AlarmManager.AlarmClockInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.app.AlarmManager$AlarmClockInfo r0 = (android.app.AlarmManager.AlarmClockInfo) r0
            L_0x00ea:
                r14 = r0
                goto L_0x00ed
            L_0x00ec:
                goto L_0x00ea
            L_0x00ed:
                r0 = r29
                r1 = r16
                r2 = r17
                r3 = r18
                r5 = r20
                r15 = r7
                r27 = r8
                r7 = r22
                r28 = r15
                r15 = r9
                r9 = r24
                r11 = r25
                r12 = r26
                r0.set(r1, r2, r3, r5, r7, r9, r10, r11, r12, r13, r14)
                r32.writeNoException()
                return r27
            L_0x010c:
                r28 = r7
                r27 = r8
                r15 = r9
                r0 = r28
                r15.writeString(r0)
                return r27
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IAlarmManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAlarmManager {
            public static IAlarmManager sDefaultImpl;
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

            public void set(String callingPackage, int type, long triggerAtTime, long windowLength, long interval, int flags, PendingIntent operation, IAlarmListener listener, String listenerTag, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClock) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                int i;
                PendingIntent pendingIntent = operation;
                WorkSource workSource2 = workSource;
                AlarmManager.AlarmClockInfo alarmClockInfo = alarmClock;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data2.writeString(callingPackage);
                    _data2.writeInt(type);
                    _data2.writeLong(triggerAtTime);
                    _data2.writeLong(windowLength);
                    _data2.writeLong(interval);
                    _data2.writeInt(flags);
                    if (pendingIntent != null) {
                        try {
                            _data2.writeInt(1);
                            pendingIntent.writeToParcel(_data2, 0);
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                            _data = _data2;
                        }
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data2.writeString(listenerTag);
                    if (workSource2 != null) {
                        _data2.writeInt(1);
                        i = 0;
                        workSource2.writeToParcel(_data2, 0);
                    } else {
                        i = 0;
                        _data2.writeInt(0);
                    }
                    if (alarmClockInfo != null) {
                        _data2.writeInt(1);
                        alarmClockInfo.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(i);
                    }
                    if (this.mRemote.transact(1, _data2, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _data = _data2;
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply = _reply2;
                    _data = _data2;
                    try {
                        Stub.getDefaultImpl().set(callingPackage, type, triggerAtTime, windowLength, interval, flags, operation, listener, listenerTag, workSource, alarmClock);
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
                    _reply = _reply2;
                    _data = _data2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean setTime(long millis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(millis);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setTime(millis);
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

            public void setTimeZone(String zone) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(zone);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTimeZone(zone);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void remove(PendingIntent operation, IAlarmListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (operation != null) {
                        _data.writeInt(1);
                        operation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().remove(operation, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getNextWakeFromIdleTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNextWakeFromIdleTime();
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

            public AlarmManager.AlarmClockInfo getNextAlarmClock(int userId) throws RemoteException {
                AlarmManager.AlarmClockInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNextAlarmClock(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AlarmManager.AlarmClockInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AlarmManager.AlarmClockInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long currentNetworkTimeMillis() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().currentNetworkTimeMillis();
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
        }

        public static boolean setDefaultImpl(IAlarmManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAlarmManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
