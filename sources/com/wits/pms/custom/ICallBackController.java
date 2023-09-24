package com.wits.pms.custom;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import com.wits.pms.custom.ICallBack;

/* loaded from: classes2.dex */
public interface ICallBackController extends IInterface {
    ICallBack getICallBack() throws RemoteException;

    void setICallBack(ICallBack iCallBack) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements ICallBackController {
        private static final String DESCRIPTOR = "com.wits.pms.custom.ICallBackController";
        static final int TRANSACTION_getICallBack = 2;
        static final int TRANSACTION_setICallBack = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICallBackController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICallBackController)) {
                return (ICallBackController) iin;
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
                    ICallBack _arg0 = ICallBack.Stub.asInterface(data.readStrongBinder());
                    setICallBack(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ICallBack _result = getICallBack();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements ICallBackController {
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

            @Override // com.wits.pms.custom.ICallBackController
            public void setICallBack(ICallBack iCallBack) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(iCallBack != null ? iCallBack.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.custom.ICallBackController
            public ICallBack getICallBack() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ICallBack _result = ICallBack.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
