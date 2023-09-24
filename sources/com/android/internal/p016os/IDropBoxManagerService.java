package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.DropBoxManager;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: com.android.internal.os.IDropBoxManagerService */
/* loaded from: classes4.dex */
public interface IDropBoxManagerService extends IInterface {
    void add(DropBoxManager.Entry entry) throws RemoteException;

    @UnsupportedAppUsage
    DropBoxManager.Entry getNextEntry(String str, long j, String str2) throws RemoteException;

    boolean isTagEnabled(String str) throws RemoteException;

    /* renamed from: com.android.internal.os.IDropBoxManagerService$Default */
    /* loaded from: classes4.dex */
    public static class Default implements IDropBoxManagerService {
        @Override // com.android.internal.p016os.IDropBoxManagerService
        public void add(DropBoxManager.Entry entry) throws RemoteException {
        }

        @Override // com.android.internal.p016os.IDropBoxManagerService
        public boolean isTagEnabled(String tag) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.p016os.IDropBoxManagerService
        public DropBoxManager.Entry getNextEntry(String tag, long millis, String packageName) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: com.android.internal.os.IDropBoxManagerService$Stub */
    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IDropBoxManagerService {
        private static final String DESCRIPTOR = "com.android.internal.os.IDropBoxManagerService";
        static final int TRANSACTION_add = 1;
        static final int TRANSACTION_getNextEntry = 3;
        static final int TRANSACTION_isTagEnabled = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDropBoxManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDropBoxManagerService)) {
                return (IDropBoxManagerService) iin;
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
                    return "add";
                case 2:
                    return "isTagEnabled";
                case 3:
                    return "getNextEntry";
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
            DropBoxManager.Entry _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = DropBoxManager.Entry.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    add(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    boolean isTagEnabled = isTagEnabled(_arg02);
                    reply.writeNoException();
                    reply.writeInt(isTagEnabled ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    long _arg1 = data.readLong();
                    String _arg2 = data.readString();
                    DropBoxManager.Entry _result = getNextEntry(_arg03, _arg1, _arg2);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: com.android.internal.os.IDropBoxManagerService$Stub$Proxy */
        /* loaded from: classes4.dex */
        private static class Proxy implements IDropBoxManagerService {
            public static IDropBoxManagerService sDefaultImpl;
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

            @Override // com.android.internal.p016os.IDropBoxManagerService
            public void add(DropBoxManager.Entry entry) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (entry != null) {
                        _data.writeInt(1);
                        entry.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().add(entry);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.p016os.IDropBoxManagerService
            public boolean isTagEnabled(String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTagEnabled(tag);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.p016os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntry(String tag, long millis, String packageName) throws RemoteException {
                DropBoxManager.Entry _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeLong(millis);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNextEntry(tag, millis, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = DropBoxManager.Entry.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDropBoxManagerService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IDropBoxManagerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
