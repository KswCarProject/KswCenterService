package android.net;

import android.net.INetworkScoreCache;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface INetworkScoreService extends IInterface {
    boolean clearScores() throws RemoteException;

    void disableScoring() throws RemoteException;

    NetworkScorerAppData getActiveScorer() throws RemoteException;

    String getActiveScorerPackage() throws RemoteException;

    List<NetworkScorerAppData> getAllValidScorers() throws RemoteException;

    boolean isCallerActiveScorer(int i) throws RemoteException;

    void registerNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache, int i2) throws RemoteException;

    boolean requestScores(NetworkKey[] networkKeyArr) throws RemoteException;

    boolean setActiveScorer(String str) throws RemoteException;

    void unregisterNetworkScoreCache(int i, INetworkScoreCache iNetworkScoreCache) throws RemoteException;

    boolean updateScores(ScoredNetwork[] scoredNetworkArr) throws RemoteException;

    public static class Default implements INetworkScoreService {
        public boolean updateScores(ScoredNetwork[] networks) throws RemoteException {
            return false;
        }

        public boolean clearScores() throws RemoteException {
            return false;
        }

        public boolean setActiveScorer(String packageName) throws RemoteException {
            return false;
        }

        public void disableScoring() throws RemoteException {
        }

        public void registerNetworkScoreCache(int networkType, INetworkScoreCache scoreCache, int filterType) throws RemoteException {
        }

        public void unregisterNetworkScoreCache(int networkType, INetworkScoreCache scoreCache) throws RemoteException {
        }

        public boolean requestScores(NetworkKey[] networks) throws RemoteException {
            return false;
        }

        public boolean isCallerActiveScorer(int callingUid) throws RemoteException {
            return false;
        }

        public String getActiveScorerPackage() throws RemoteException {
            return null;
        }

        public NetworkScorerAppData getActiveScorer() throws RemoteException {
            return null;
        }

        public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkScoreService {
        private static final String DESCRIPTOR = "android.net.INetworkScoreService";
        static final int TRANSACTION_clearScores = 2;
        static final int TRANSACTION_disableScoring = 4;
        static final int TRANSACTION_getActiveScorer = 10;
        static final int TRANSACTION_getActiveScorerPackage = 9;
        static final int TRANSACTION_getAllValidScorers = 11;
        static final int TRANSACTION_isCallerActiveScorer = 8;
        static final int TRANSACTION_registerNetworkScoreCache = 5;
        static final int TRANSACTION_requestScores = 7;
        static final int TRANSACTION_setActiveScorer = 3;
        static final int TRANSACTION_unregisterNetworkScoreCache = 6;
        static final int TRANSACTION_updateScores = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkScoreService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkScoreService)) {
                return new Proxy(obj);
            }
            return (INetworkScoreService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "updateScores";
                case 2:
                    return "clearScores";
                case 3:
                    return "setActiveScorer";
                case 4:
                    return "disableScoring";
                case 5:
                    return "registerNetworkScoreCache";
                case 6:
                    return "unregisterNetworkScoreCache";
                case 7:
                    return "requestScores";
                case 8:
                    return "isCallerActiveScorer";
                case 9:
                    return "getActiveScorerPackage";
                case 10:
                    return "getActiveScorer";
                case 11:
                    return "getAllValidScorers";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result = updateScores((ScoredNetwork[]) data.createTypedArray(ScoredNetwork.CREATOR));
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result2 = clearScores();
                        reply.writeNoException();
                        reply.writeInt(_result2);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result3 = setActiveScorer(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result3);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        disableScoring();
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        registerNetworkScoreCache(data.readInt(), INetworkScoreCache.Stub.asInterface(data.readStrongBinder()), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        unregisterNetworkScoreCache(data.readInt(), INetworkScoreCache.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result4 = requestScores((NetworkKey[]) data.createTypedArray(NetworkKey.CREATOR));
                        reply.writeNoException();
                        reply.writeInt(_result4);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result5 = isCallerActiveScorer(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(_result5);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        String _result6 = getActiveScorerPackage();
                        reply.writeNoException();
                        reply.writeString(_result6);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        NetworkScorerAppData _result7 = getActiveScorer();
                        reply.writeNoException();
                        if (_result7 != null) {
                            reply.writeInt(1);
                            _result7.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        List<NetworkScorerAppData> _result8 = getAllValidScorers();
                        reply.writeNoException();
                        reply.writeTypedList(_result8);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements INetworkScoreService {
            public static INetworkScoreService sDefaultImpl;
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

            public boolean updateScores(ScoredNetwork[] networks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    _data.writeTypedArray(networks, 0);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateScores(networks);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean clearScores() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearScores();
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

            public boolean setActiveScorer(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setActiveScorer(packageName);
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

            public void disableScoring() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableScoring();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerNetworkScoreCache(int networkType, INetworkScoreCache scoreCache, int filterType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeStrongBinder(scoreCache != null ? scoreCache.asBinder() : null);
                    _data.writeInt(filterType);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerNetworkScoreCache(networkType, scoreCache, filterType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterNetworkScoreCache(int networkType, INetworkScoreCache scoreCache) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeStrongBinder(scoreCache != null ? scoreCache.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterNetworkScoreCache(networkType, scoreCache);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean requestScores(NetworkKey[] networks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    _data.writeTypedArray(networks, 0);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestScores(networks);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isCallerActiveScorer(int callingUid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callingUid);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCallerActiveScorer(callingUid);
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

            public String getActiveScorerPackage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveScorerPackage();
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

            public NetworkScorerAppData getActiveScorer() throws RemoteException {
                NetworkScorerAppData _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveScorer();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkScorerAppData.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkScorerAppData _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<NetworkScorerAppData> getAllValidScorers() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllValidScorers();
                    }
                    _reply.readException();
                    List<NetworkScorerAppData> _result = _reply.createTypedArrayList(NetworkScorerAppData.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkScoreService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkScoreService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
