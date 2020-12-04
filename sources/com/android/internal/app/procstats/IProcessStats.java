package com.android.internal.app.procstats;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IProcessStats extends IInterface {
    long getCommittedStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException;

    int getCurrentMemoryState() throws RemoteException;

    byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException;

    ParcelFileDescriptor getStatsOverTime(long j) throws RemoteException;

    public static class Default implements IProcessStats {
        public byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor getStatsOverTime(long minTime) throws RemoteException {
            return null;
        }

        public int getCurrentMemoryState() throws RemoteException {
            return 0;
        }

        public long getCommittedStats(long highWaterMarkMs, int section, boolean doAggregate, List<ParcelFileDescriptor> list) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IProcessStats {
        private static final String DESCRIPTOR = "com.android.internal.app.procstats.IProcessStats";
        static final int TRANSACTION_getCommittedStats = 4;
        static final int TRANSACTION_getCurrentMemoryState = 3;
        static final int TRANSACTION_getCurrentStats = 1;
        static final int TRANSACTION_getStatsOverTime = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IProcessStats asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IProcessStats)) {
                return new Proxy(obj);
            }
            return (IProcessStats) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getCurrentStats";
                case 2:
                    return "getStatsOverTime";
                case 3:
                    return "getCurrentMemoryState";
                case 4:
                    return "getCommittedStats";
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
                        byte[] _result = getCurrentStats(new ArrayList<>());
                        reply.writeNoException();
                        parcel2.writeByteArray(_result);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        ParcelFileDescriptor _result2 = getStatsOverTime(data.readLong());
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
                        int _result3 = getCurrentMemoryState();
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        long _result4 = getCommittedStats(data.readLong(), data.readInt(), data.readInt() != 0, new ArrayList<>());
                        reply.writeNoException();
                        parcel2.writeLong(_result4);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IProcessStats {
            public static IProcessStats sDefaultImpl;
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

            public byte[] getCurrentStats(List<ParcelFileDescriptor> historic) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentStats(historic);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getStatsOverTime(long minTime) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(minTime);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatsOverTime(minTime);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCurrentMemoryState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentMemoryState();
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

            public long getCommittedStats(long highWaterMarkMs, int section, boolean doAggregate, List<ParcelFileDescriptor> committedStats) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(highWaterMarkMs);
                    _data.writeInt(section);
                    _data.writeInt(doAggregate);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCommittedStats(highWaterMarkMs, section, doAggregate, committedStats);
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

        public static boolean setDefaultImpl(IProcessStats impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IProcessStats getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
