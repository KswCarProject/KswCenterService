package android.content;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIntentReceiver extends IInterface {
    @UnsupportedAppUsage
    void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) throws RemoteException;

    public static class Default implements IIntentReceiver {
        public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IIntentReceiver {
        private static final String DESCRIPTOR = "android.content.IIntentReceiver";
        static final int TRANSACTION_performReceive = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIntentReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IIntentReceiver)) {
                return new Proxy(obj);
            }
            return (IIntentReceiver) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode != 1) {
                return null;
            }
            return "performReceive";
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Intent _arg0;
            int i = code;
            Parcel parcel = data;
            if (i == 1) {
                Parcel parcel2 = reply;
                parcel.enforceInterface(DESCRIPTOR);
                Bundle _arg3 = null;
                if (data.readInt() != 0) {
                    _arg0 = Intent.CREATOR.createFromParcel(parcel);
                } else {
                    _arg0 = null;
                }
                int _arg1 = data.readInt();
                String _arg2 = data.readString();
                if (data.readInt() != 0) {
                    _arg3 = Bundle.CREATOR.createFromParcel(parcel);
                }
                performReceive(_arg0, _arg1, _arg2, _arg3, data.readInt() != 0, data.readInt() != 0, data.readInt());
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IIntentReceiver {
            public static IIntentReceiver sDefaultImpl;
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

            public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser) throws RemoteException {
                Intent intent2 = intent;
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent2 != null) {
                        _data.writeInt(1);
                        intent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(resultCode);
                    } catch (Throwable th) {
                        th = th;
                        String str = data;
                        boolean z = ordered;
                        boolean z2 = sticky;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(data);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(ordered ? 1 : 0);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z22 = sticky;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        boolean z3 = ordered;
                        boolean z222 = sticky;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(sticky ? 1 : 0);
                        _data.writeInt(sendingUser);
                        if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().performReceive(intent, resultCode, data, extras, ordered, sticky, sendingUser);
                        _data.recycle();
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = resultCode;
                    String str2 = data;
                    boolean z32 = ordered;
                    boolean z2222 = sticky;
                    _data.recycle();
                    throw th;
                }
            }
        }

        public static boolean setDefaultImpl(IIntentReceiver impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IIntentReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
