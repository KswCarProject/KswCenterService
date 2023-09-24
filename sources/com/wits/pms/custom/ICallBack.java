package com.wits.pms.custom;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes2.dex */
public interface ICallBack extends IInterface {
    void onAcc(boolean z) throws RemoteException;

    void onAngle(int i) throws RemoteException;

    void onDoor(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) throws RemoteException;

    void onFRadar(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onLRReverse(int i) throws RemoteException;

    void onRRadar(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onReverse(int i) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements ICallBack {
        private static final String DESCRIPTOR = "com.wits.pms.custom.ICallBack";
        static final int TRANSACTION_onAcc = 7;
        static final int TRANSACTION_onAngle = 5;
        static final int TRANSACTION_onDoor = 6;
        static final int TRANSACTION_onFRadar = 4;
        static final int TRANSACTION_onLRReverse = 2;
        static final int TRANSACTION_onRRadar = 3;
        static final int TRANSACTION_onReverse = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICallBack asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICallBack)) {
                return (ICallBack) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
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
                    onReverse(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onLRReverse(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    onRRadar(_arg03, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg42 = data.readInt();
                    onFRadar(_arg04, _arg12, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    onAngle(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg06 = data.readInt() != 0;
                    boolean _arg13 = data.readInt() != 0;
                    boolean _arg23 = data.readInt() != 0;
                    boolean _arg33 = data.readInt() != 0;
                    boolean _arg43 = data.readInt() != 0;
                    boolean _arg5 = data.readInt() != 0;
                    onDoor(_arg06, _arg13, _arg23, _arg33, _arg43, _arg5);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg07 = data.readInt() != 0;
                    onAcc(_arg07);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements ICallBack {
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

            @Override // com.wits.pms.custom.ICallBack
            public void onReverse(int var1) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBack
            public void onLRReverse(int var1) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBack
            public void onRRadar(int var1, int var2, int var3, int var4, int var5) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1);
                    _data.writeInt(var2);
                    _data.writeInt(var3);
                    _data.writeInt(var4);
                    _data.writeInt(var5);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBack
            public void onFRadar(int var1, int var2, int var3, int var4, int var5) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1);
                    _data.writeInt(var2);
                    _data.writeInt(var3);
                    _data.writeInt(var4);
                    _data.writeInt(var5);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBack
            public void onAngle(int var1) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBack
            public void onDoor(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1 ? 1 : 0);
                    _data.writeInt(var2 ? 1 : 0);
                    _data.writeInt(var3 ? 1 : 0);
                    _data.writeInt(var4 ? 1 : 0);
                    _data.writeInt(var5 ? 1 : 0);
                    _data.writeInt(var6 ? 1 : 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBack
            public void onAcc(boolean var1) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(var1 ? 1 : 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
