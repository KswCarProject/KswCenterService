package android.net;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkStatsSession extends IInterface {
    @UnsupportedAppUsage
    void close() throws RemoteException;

    NetworkStats getDeviceSummaryForNetwork(NetworkTemplate networkTemplate, long j, long j2) throws RemoteException;

    @UnsupportedAppUsage
    NetworkStatsHistory getHistoryForNetwork(NetworkTemplate networkTemplate, int i) throws RemoteException;

    @UnsupportedAppUsage
    NetworkStatsHistory getHistoryForUid(NetworkTemplate networkTemplate, int i, int i2, int i3, int i4) throws RemoteException;

    NetworkStatsHistory getHistoryIntervalForUid(NetworkTemplate networkTemplate, int i, int i2, int i3, int i4, long j, long j2) throws RemoteException;

    int[] getRelevantUids() throws RemoteException;

    @UnsupportedAppUsage
    NetworkStats getSummaryForAllUid(NetworkTemplate networkTemplate, long j, long j2, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    NetworkStats getSummaryForNetwork(NetworkTemplate networkTemplate, long j, long j2) throws RemoteException;

    public static class Default implements INetworkStatsSession {
        public NetworkStats getDeviceSummaryForNetwork(NetworkTemplate template, long start, long end) throws RemoteException {
            return null;
        }

        public NetworkStats getSummaryForNetwork(NetworkTemplate template, long start, long end) throws RemoteException {
            return null;
        }

        public NetworkStatsHistory getHistoryForNetwork(NetworkTemplate template, int fields) throws RemoteException {
            return null;
        }

        public NetworkStats getSummaryForAllUid(NetworkTemplate template, long start, long end, boolean includeTags) throws RemoteException {
            return null;
        }

        public NetworkStatsHistory getHistoryForUid(NetworkTemplate template, int uid, int set, int tag, int fields) throws RemoteException {
            return null;
        }

        public NetworkStatsHistory getHistoryIntervalForUid(NetworkTemplate template, int uid, int set, int tag, int fields, long start, long end) throws RemoteException {
            return null;
        }

        public int[] getRelevantUids() throws RemoteException {
            return null;
        }

        public void close() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkStatsSession {
        private static final String DESCRIPTOR = "android.net.INetworkStatsSession";
        static final int TRANSACTION_close = 8;
        static final int TRANSACTION_getDeviceSummaryForNetwork = 1;
        static final int TRANSACTION_getHistoryForNetwork = 3;
        static final int TRANSACTION_getHistoryForUid = 5;
        static final int TRANSACTION_getHistoryIntervalForUid = 6;
        static final int TRANSACTION_getRelevantUids = 7;
        static final int TRANSACTION_getSummaryForAllUid = 4;
        static final int TRANSACTION_getSummaryForNetwork = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkStatsSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkStatsSession)) {
                return new Proxy(obj);
            }
            return (INetworkStatsSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDeviceSummaryForNetwork";
                case 2:
                    return "getSummaryForNetwork";
                case 3:
                    return "getHistoryForNetwork";
                case 4:
                    return "getSummaryForAllUid";
                case 5:
                    return "getHistoryForUid";
                case 6:
                    return "getHistoryIntervalForUid";
                case 7:
                    return "getRelevantUids";
                case 8:
                    return "close";
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
                NetworkTemplate _arg0 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = NetworkTemplate.CREATOR.createFromParcel(parcel);
                        }
                        NetworkStats _result = getDeviceSummaryForNetwork(_arg0, data.readLong(), data.readLong());
                        reply.writeNoException();
                        if (_result != null) {
                            parcel2.writeInt(1);
                            _result.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = NetworkTemplate.CREATOR.createFromParcel(parcel);
                        }
                        NetworkStats _result2 = getSummaryForNetwork(_arg0, data.readLong(), data.readLong());
                        reply.writeNoException();
                        if (_result2 != null) {
                            parcel2.writeInt(1);
                            _result2.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = NetworkTemplate.CREATOR.createFromParcel(parcel);
                        }
                        NetworkStatsHistory _result3 = getHistoryForNetwork(_arg0, data.readInt());
                        reply.writeNoException();
                        if (_result3 != null) {
                            parcel2.writeInt(1);
                            _result3.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = NetworkTemplate.CREATOR.createFromParcel(parcel);
                        }
                        NetworkStats _result4 = getSummaryForAllUid(_arg0, data.readLong(), data.readLong(), data.readInt() != 0);
                        reply.writeNoException();
                        if (_result4 != null) {
                            parcel2.writeInt(1);
                            _result4.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = NetworkTemplate.CREATOR.createFromParcel(parcel);
                        }
                        NetworkStatsHistory _result5 = getHistoryForUid(_arg0, data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        if (_result5 != null) {
                            parcel2.writeInt(1);
                            _result5.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = NetworkTemplate.CREATOR.createFromParcel(parcel);
                        }
                        NetworkStatsHistory _result6 = getHistoryIntervalForUid(_arg0, data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readLong(), data.readLong());
                        reply.writeNoException();
                        if (_result6 != null) {
                            parcel2.writeInt(1);
                            _result6.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        int[] _result7 = getRelevantUids();
                        reply.writeNoException();
                        parcel2.writeIntArray(_result7);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        close();
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

        private static class Proxy implements INetworkStatsSession {
            public static INetworkStatsSession sDefaultImpl;
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

            public NetworkStats getDeviceSummaryForNetwork(NetworkTemplate template, long start, long end) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (template != null) {
                        _data.writeInt(1);
                        template.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(start);
                    _data.writeLong(end);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceSummaryForNetwork(template, start, end);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getSummaryForNetwork(NetworkTemplate template, long start, long end) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (template != null) {
                        _data.writeInt(1);
                        template.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(start);
                    _data.writeLong(end);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSummaryForNetwork(template, start, end);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStatsHistory getHistoryForNetwork(NetworkTemplate template, int fields) throws RemoteException {
                NetworkStatsHistory _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (template != null) {
                        _data.writeInt(1);
                        template.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(fields);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHistoryForNetwork(template, fields);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStatsHistory.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStatsHistory _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getSummaryForAllUid(NetworkTemplate template, long start, long end, boolean includeTags) throws RemoteException {
                NetworkStats _result;
                NetworkTemplate networkTemplate = template;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkTemplate != null) {
                        _data.writeInt(1);
                        networkTemplate.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeLong(start);
                        try {
                            _data.writeLong(end);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = includeTags;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(includeTags ? 1 : 0);
                            if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = NetworkStats.CREATOR.createFromParcel(_reply);
                                } else {
                                    _result = null;
                                }
                                NetworkStats _result2 = _result;
                                _reply.recycle();
                                _data.recycle();
                                return _result2;
                            }
                            NetworkStats summaryForAllUid = Stub.getDefaultImpl().getSummaryForAllUid(template, start, end, includeTags);
                            _reply.recycle();
                            _data.recycle();
                            return summaryForAllUid;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = end;
                        boolean z2 = includeTags;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    long j2 = start;
                    long j3 = end;
                    boolean z22 = includeTags;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public NetworkStatsHistory getHistoryForUid(NetworkTemplate template, int uid, int set, int tag, int fields) throws RemoteException {
                NetworkStatsHistory _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (template != null) {
                        _data.writeInt(1);
                        template.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(uid);
                    _data.writeInt(set);
                    _data.writeInt(tag);
                    _data.writeInt(fields);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHistoryForUid(template, uid, set, tag, fields);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStatsHistory.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStatsHistory _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStatsHistory getHistoryIntervalForUid(NetworkTemplate template, int uid, int set, int tag, int fields, long start, long end) throws RemoteException {
                NetworkStatsHistory _result;
                NetworkTemplate networkTemplate = template;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkTemplate != null) {
                        _data.writeInt(1);
                        networkTemplate.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(uid);
                        try {
                            _data.writeInt(set);
                            _data.writeInt(tag);
                            _data.writeInt(fields);
                            _data.writeLong(start);
                            _data.writeLong(end);
                            if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = NetworkStatsHistory.CREATOR.createFromParcel(_reply);
                                } else {
                                    _result = null;
                                }
                                NetworkStatsHistory _result2 = _result;
                                _reply.recycle();
                                _data.recycle();
                                return _result2;
                            }
                            NetworkStatsHistory historyIntervalForUid = Stub.getDefaultImpl().getHistoryIntervalForUid(template, uid, set, tag, fields, start, end);
                            _reply.recycle();
                            _data.recycle();
                            return historyIntervalForUid;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i = set;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i2 = uid;
                    int i3 = set;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int[] getRelevantUids() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRelevantUids();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().close();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkStatsSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkStatsSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
