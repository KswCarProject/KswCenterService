package android.telephony.ims.aidl;

import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsReasonInfo;
import com.android.ims.internal.IImsCallSession;

/* loaded from: classes4.dex */
public interface IImsMmTelListener extends IInterface {
    void onIncomingCall(IImsCallSession iImsCallSession, Bundle bundle) throws RemoteException;

    void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void onVoiceMessageCountUpdate(int i) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IImsMmTelListener {
        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onIncomingCall(IImsCallSession c, Bundle extras) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onRejectedCall(ImsCallProfile callProfile, ImsReasonInfo reason) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onVoiceMessageCountUpdate(int count) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IImsMmTelListener {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelListener";
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onRejectedCall = 2;
        static final int TRANSACTION_onVoiceMessageCountUpdate = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMmTelListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IImsMmTelListener)) {
                return (IImsMmTelListener) iin;
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
                    return "onIncomingCall";
                case 2:
                    return "onRejectedCall";
                case 3:
                    return "onVoiceMessageCountUpdate";
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
            ImsCallProfile _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    IImsCallSession _arg02 = IImsCallSession.Stub.asInterface(data.readStrongBinder());
                    Bundle _arg1 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onIncomingCall(_arg02, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = ImsCallProfile.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    ImsReasonInfo _arg12 = data.readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel(data) : null;
                    onRejectedCall(_arg0, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    onVoiceMessageCountUpdate(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IImsMmTelListener {
            public static IImsMmTelListener sDefaultImpl;
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

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onIncomingCall(IImsCallSession c, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onIncomingCall(c, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onRejectedCall(ImsCallProfile callProfile, ImsReasonInfo reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callProfile != null) {
                        _data.writeInt(1);
                        callProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (reason != null) {
                        _data.writeInt(1);
                        reason.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRejectedCall(callProfile, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onVoiceMessageCountUpdate(int count) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(count);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceMessageCountUpdate(count);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsMmTelListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IImsMmTelListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
