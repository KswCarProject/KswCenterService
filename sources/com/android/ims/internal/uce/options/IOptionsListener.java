package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;

/* loaded from: classes4.dex */
public interface IOptionsListener extends IInterface {
    @UnsupportedAppUsage
    void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException;

    @UnsupportedAppUsage
    void getVersionCb(String str) throws RemoteException;

    @UnsupportedAppUsage
    void incomingOptions(String str, OptionsCapInfo optionsCapInfo, int i) throws RemoteException;

    @UnsupportedAppUsage
    void serviceAvailable(StatusCode statusCode) throws RemoteException;

    @UnsupportedAppUsage
    void serviceUnavailable(StatusCode statusCode) throws RemoteException;

    @UnsupportedAppUsage
    void sipResponseReceived(String str, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IOptionsListener {
        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void getVersionCb(String version) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void sipResponseReceived(String uri, OptionsSipResponse sipResponse, OptionsCapInfo capInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void cmdStatus(OptionsCmdStatus cmdStatus) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void incomingOptions(String uri, OptionsCapInfo capInfo, int tID) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IOptionsListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsListener";
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_incomingOptions = 6;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnavailable = 3;
        static final int TRANSACTION_sipResponseReceived = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOptionsListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IOptionsListener)) {
                return (IOptionsListener) iin;
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
                    return "getVersionCb";
                case 2:
                    return "serviceAvailable";
                case 3:
                    return "serviceUnavailable";
                case 4:
                    return "sipResponseReceived";
                case 5:
                    return "cmdStatus";
                case 6:
                    return "incomingOptions";
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
            OptionsSipResponse _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    getVersionCb(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    StatusCode _arg02 = data.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(data) : null;
                    serviceAvailable(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    StatusCode _arg03 = data.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(data) : null;
                    serviceUnavailable(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = OptionsSipResponse.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    OptionsCapInfo _arg2 = data.readInt() != 0 ? OptionsCapInfo.CREATOR.createFromParcel(data) : null;
                    sipResponseReceived(_arg04, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    OptionsCmdStatus _arg05 = data.readInt() != 0 ? OptionsCmdStatus.CREATOR.createFromParcel(data) : null;
                    cmdStatus(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg06 = data.readString();
                    OptionsCapInfo _arg12 = data.readInt() != 0 ? OptionsCapInfo.CREATOR.createFromParcel(data) : null;
                    int _arg22 = data.readInt();
                    incomingOptions(_arg06, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IOptionsListener {
            public static IOptionsListener sDefaultImpl;
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

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void getVersionCb(String version) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(version);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getVersionCb(version);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void serviceAvailable(StatusCode statusCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (statusCode != null) {
                        _data.writeInt(1);
                        statusCode.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serviceAvailable(statusCode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (statusCode != null) {
                        _data.writeInt(1);
                        statusCode.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serviceUnavailable(statusCode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void sipResponseReceived(String uri, OptionsSipResponse sipResponse, OptionsCapInfo capInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    if (sipResponse != null) {
                        _data.writeInt(1);
                        sipResponse.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (capInfo != null) {
                        _data.writeInt(1);
                        capInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sipResponseReceived(uri, sipResponse, capInfo);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void cmdStatus(OptionsCmdStatus cmdStatus) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cmdStatus != null) {
                        _data.writeInt(1);
                        cmdStatus.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cmdStatus(cmdStatus);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void incomingOptions(String uri, OptionsCapInfo capInfo, int tID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    if (capInfo != null) {
                        _data.writeInt(1);
                        capInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(tID);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().incomingOptions(uri, capInfo, tID);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOptionsListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IOptionsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
