package com.android.internal.app;

import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IVoiceInteractionSessionListener extends IInterface {
    void onSetUiHints(Bundle bundle) throws RemoteException;

    void onVoiceSessionHidden() throws RemoteException;

    void onVoiceSessionShown() throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IVoiceInteractionSessionListener {
        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onVoiceSessionShown() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onVoiceSessionHidden() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionSessionListener
        public void onSetUiHints(Bundle args) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IVoiceInteractionSessionListener {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSessionListener";
        static final int TRANSACTION_onSetUiHints = 3;
        static final int TRANSACTION_onVoiceSessionHidden = 2;
        static final int TRANSACTION_onVoiceSessionShown = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSessionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVoiceInteractionSessionListener)) {
                return (IVoiceInteractionSessionListener) iin;
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
                    return "onVoiceSessionShown";
                case 2:
                    return "onVoiceSessionHidden";
                case 3:
                    return "onSetUiHints";
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
            Bundle _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onVoiceSessionShown();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onVoiceSessionHidden();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onSetUiHints(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IVoiceInteractionSessionListener {
            public static IVoiceInteractionSessionListener sDefaultImpl;
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

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onVoiceSessionShown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceSessionShown();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onVoiceSessionHidden() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceSessionHidden();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSessionListener
            public void onSetUiHints(Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetUiHints(args);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVoiceInteractionSessionListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IVoiceInteractionSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
