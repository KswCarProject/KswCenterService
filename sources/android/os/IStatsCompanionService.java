package android.os;

public interface IStatsCompanionService extends IInterface {
    void cancelAlarmForSubscriberTriggering() throws RemoteException;

    void cancelAnomalyAlarm() throws RemoteException;

    void cancelPullingAlarm() throws RemoteException;

    StatsLogEventWrapper[] pullData(int i) throws RemoteException;

    void sendActiveConfigsChangedBroadcast(IBinder iBinder, long[] jArr) throws RemoteException;

    void sendDataBroadcast(IBinder iBinder, long j) throws RemoteException;

    void sendSubscriberBroadcast(IBinder iBinder, long j, long j2, long j3, long j4, String[] strArr, StatsDimensionsValue statsDimensionsValue) throws RemoteException;

    void setAlarmForSubscriberTriggering(long j) throws RemoteException;

    void setAnomalyAlarm(long j) throws RemoteException;

    void setPullingAlarm(long j) throws RemoteException;

    void statsdReady() throws RemoteException;

    void triggerUidSnapshot() throws RemoteException;

    public static class Default implements IStatsCompanionService {
        public void statsdReady() throws RemoteException {
        }

        public void setAnomalyAlarm(long timestampMs) throws RemoteException {
        }

        public void cancelAnomalyAlarm() throws RemoteException {
        }

        public void setPullingAlarm(long nextPullTimeMs) throws RemoteException {
        }

        public void cancelPullingAlarm() throws RemoteException {
        }

        public void setAlarmForSubscriberTriggering(long timestampMs) throws RemoteException {
        }

        public void cancelAlarmForSubscriberTriggering() throws RemoteException {
        }

        public StatsLogEventWrapper[] pullData(int pullCode) throws RemoteException {
            return null;
        }

        public void sendDataBroadcast(IBinder intentSender, long lastReportTimeNs) throws RemoteException {
        }

        public void sendActiveConfigsChangedBroadcast(IBinder intentSender, long[] configIds) throws RemoteException {
        }

        public void sendSubscriberBroadcast(IBinder intentSender, long configUid, long configId, long subscriptionId, long subscriptionRuleId, String[] cookies, StatsDimensionsValue dimensionsValue) throws RemoteException {
        }

        public void triggerUidSnapshot() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStatsCompanionService {
        private static final String DESCRIPTOR = "android.os.IStatsCompanionService";
        static final int TRANSACTION_cancelAlarmForSubscriberTriggering = 7;
        static final int TRANSACTION_cancelAnomalyAlarm = 3;
        static final int TRANSACTION_cancelPullingAlarm = 5;
        static final int TRANSACTION_pullData = 8;
        static final int TRANSACTION_sendActiveConfigsChangedBroadcast = 10;
        static final int TRANSACTION_sendDataBroadcast = 9;
        static final int TRANSACTION_sendSubscriberBroadcast = 11;
        static final int TRANSACTION_setAlarmForSubscriberTriggering = 6;
        static final int TRANSACTION_setAnomalyAlarm = 2;
        static final int TRANSACTION_setPullingAlarm = 4;
        static final int TRANSACTION_statsdReady = 1;
        static final int TRANSACTION_triggerUidSnapshot = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatsCompanionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IStatsCompanionService)) {
                return new Proxy(obj);
            }
            return (IStatsCompanionService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "statsdReady";
                case 2:
                    return "setAnomalyAlarm";
                case 3:
                    return "cancelAnomalyAlarm";
                case 4:
                    return "setPullingAlarm";
                case 5:
                    return "cancelPullingAlarm";
                case 6:
                    return "setAlarmForSubscriberTriggering";
                case 7:
                    return "cancelAlarmForSubscriberTriggering";
                case 8:
                    return "pullData";
                case 9:
                    return "sendDataBroadcast";
                case 10:
                    return "sendActiveConfigsChangedBroadcast";
                case 11:
                    return "sendSubscriberBroadcast";
                case 12:
                    return "triggerUidSnapshot";
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
        public boolean onTransact(int r28, android.os.Parcel r29, android.os.Parcel r30, int r31) throws android.os.RemoteException {
            /*
                r27 = this;
                r12 = r27
                r13 = r28
                r14 = r29
                r15 = r30
                java.lang.String r10 = "android.os.IStatsCompanionService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r8 = 1
                if (r13 == r0) goto L_0x00e5
                switch(r13) {
                    case 1: goto L_0x00dc;
                    case 2: goto L_0x00cf;
                    case 3: goto L_0x00c6;
                    case 4: goto L_0x00b9;
                    case 5: goto L_0x00b0;
                    case 6: goto L_0x00a3;
                    case 7: goto L_0x009a;
                    case 8: goto L_0x0086;
                    case 9: goto L_0x0075;
                    case 10: goto L_0x0061;
                    case 11: goto L_0x001f;
                    case 12: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r0 = super.onTransact(r28, r29, r30, r31)
                return r0
            L_0x0018:
                r14.enforceInterface(r10)
                r27.triggerUidSnapshot()
                return r8
            L_0x001f:
                r14.enforceInterface(r10)
                android.os.IBinder r16 = r29.readStrongBinder()
                long r17 = r29.readLong()
                long r19 = r29.readLong()
                long r21 = r29.readLong()
                long r23 = r29.readLong()
                java.lang.String[] r25 = r29.createStringArray()
                int r0 = r29.readInt()
                if (r0 == 0) goto L_0x004a
                android.os.Parcelable$Creator<android.os.StatsDimensionsValue> r0 = android.os.StatsDimensionsValue.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.StatsDimensionsValue r0 = (android.os.StatsDimensionsValue) r0
            L_0x0048:
                r11 = r0
                goto L_0x004c
            L_0x004a:
                r0 = 0
                goto L_0x0048
            L_0x004c:
                r0 = r27
                r1 = r16
                r2 = r17
                r4 = r19
                r6 = r21
                r13 = r8
                r8 = r23
                r26 = r10
                r10 = r25
                r0.sendSubscriberBroadcast(r1, r2, r4, r6, r8, r10, r11)
                return r13
            L_0x0061:
                r13 = r8
                r26 = r10
                r0 = r26
                r14.enforceInterface(r0)
                android.os.IBinder r1 = r29.readStrongBinder()
                long[] r2 = r29.createLongArray()
                r12.sendActiveConfigsChangedBroadcast(r1, r2)
                return r13
            L_0x0075:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                android.os.IBinder r1 = r29.readStrongBinder()
                long r2 = r29.readLong()
                r12.sendDataBroadcast(r1, r2)
                return r13
            L_0x0086:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                int r1 = r29.readInt()
                android.os.StatsLogEventWrapper[] r2 = r12.pullData(r1)
                r30.writeNoException()
                r15.writeTypedArray(r2, r13)
                return r13
            L_0x009a:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                r27.cancelAlarmForSubscriberTriggering()
                return r13
            L_0x00a3:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                long r1 = r29.readLong()
                r12.setAlarmForSubscriberTriggering(r1)
                return r13
            L_0x00b0:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                r27.cancelPullingAlarm()
                return r13
            L_0x00b9:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                long r1 = r29.readLong()
                r12.setPullingAlarm(r1)
                return r13
            L_0x00c6:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                r27.cancelAnomalyAlarm()
                return r13
            L_0x00cf:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                long r1 = r29.readLong()
                r12.setAnomalyAlarm(r1)
                return r13
            L_0x00dc:
                r13 = r8
                r0 = r10
                r14.enforceInterface(r0)
                r27.statsdReady()
                return r13
            L_0x00e5:
                r13 = r8
                r0 = r10
                r15.writeString(r0)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.IStatsCompanionService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IStatsCompanionService {
            public static IStatsCompanionService sDefaultImpl;
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

            public void statsdReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().statsdReady();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAnomalyAlarm(long timestampMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestampMs);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAnomalyAlarm(timestampMs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAnomalyAlarm() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelAnomalyAlarm();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPullingAlarm(long nextPullTimeMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(nextPullTimeMs);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPullingAlarm(nextPullTimeMs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelPullingAlarm() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelPullingAlarm();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAlarmForSubscriberTriggering(long timestampMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestampMs);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAlarmForSubscriberTriggering(timestampMs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAlarmForSubscriberTriggering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelAlarmForSubscriberTriggering();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public StatsLogEventWrapper[] pullData(int pullCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pullCode);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pullData(pullCode);
                    }
                    _reply.readException();
                    StatsLogEventWrapper[] _result = (StatsLogEventWrapper[]) _reply.createTypedArray(StatsLogEventWrapper.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendDataBroadcast(IBinder intentSender, long lastReportTimeNs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(intentSender);
                    _data.writeLong(lastReportTimeNs);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendDataBroadcast(intentSender, lastReportTimeNs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendActiveConfigsChangedBroadcast(IBinder intentSender, long[] configIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(intentSender);
                    _data.writeLongArray(configIds);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendActiveConfigsChangedBroadcast(intentSender, configIds);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendSubscriberBroadcast(IBinder intentSender, long configUid, long configId, long subscriptionId, long subscriptionRuleId, String[] cookies, StatsDimensionsValue dimensionsValue) throws RemoteException {
                StatsDimensionsValue statsDimensionsValue = dimensionsValue;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(intentSender);
                        _data.writeLong(configUid);
                        _data.writeLong(configId);
                        _data.writeLong(subscriptionId);
                        _data.writeLong(subscriptionRuleId);
                        _data.writeStringArray(cookies);
                        if (statsDimensionsValue != null) {
                            _data.writeInt(1);
                            statsDimensionsValue.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().sendSubscriberBroadcast(intentSender, configUid, configId, subscriptionId, subscriptionRuleId, cookies, dimensionsValue);
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    IBinder iBinder = intentSender;
                    _data.recycle();
                    throw th;
                }
            }

            public void triggerUidSnapshot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().triggerUidSnapshot();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IStatsCompanionService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IStatsCompanionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
