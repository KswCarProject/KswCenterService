package android.content;

import android.content.IIntentReceiver;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IIntentSender extends IInterface {
    void send(int i, Intent intent, String str, IBinder iBinder, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IIntentSender {
        @Override // android.content.IIntentSender
        public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IIntentSender {
        private static final String DESCRIPTOR = "android.content.IIntentSender";
        static final int TRANSACTION_send = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIntentSender asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IIntentSender)) {
                return (IIntentSender) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "send";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            Intent _arg1 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
            String _arg2 = data.readString();
            IBinder _arg3 = data.readStrongBinder();
            IIntentReceiver _arg4 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
            String _arg5 = data.readString();
            Bundle _arg6 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
            send(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            return true;
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IIntentSender {
            public static IIntentSender sDefaultImpl;
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

            @Override // android.content.IIntentSender
            public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(code);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(resolvedType);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongBinder(whitelistToken);
                    _data.writeStrongBinder(finishedReceiver != null ? finishedReceiver.asBinder() : null);
                    _data.writeString(requiredPermission);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().send(code, intent, resolvedType, whitelistToken, finishedReceiver, requiredPermission, options);
                        _data.recycle();
                        return;
                    }
                    _data.recycle();
                } catch (Throwable th4) {
                    th = th4;
                    _data.recycle();
                    throw th;
                }
            }
        }

        public static boolean setDefaultImpl(IIntentSender impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IIntentSender getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
