package com.wits.pms;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.KeyEvent;

/* loaded from: classes2.dex */
public interface ISystemKeyService extends IInterface {
    boolean keyControl(KeyEvent keyEvent) throws RemoteException;

    void onKeyEvent(KeyEvent keyEvent) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements ISystemKeyService {
        private static final String DESCRIPTOR = "com.wits.pms.ISystemKeyService";
        static final int TRANSACTION_keyControl = 2;
        static final int TRANSACTION_onKeyEvent = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISystemKeyService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemKeyService)) {
                return (ISystemKeyService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            KeyEvent _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(data) : null;
                    onKeyEvent(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(data) : null;
                    boolean keyControl = keyControl(_arg0);
                    reply.writeNoException();
                    reply.writeInt(keyControl ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements ISystemKeyService {
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

            @Override // com.wits.pms.ISystemKeyService
            public void onKeyEvent(KeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.wits.pms.ISystemKeyService
            public boolean keyControl(KeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
