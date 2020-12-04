package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;

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

    public static class Default implements IOptionsListener {
        public void getVersionCb(String version) throws RemoteException {
        }

        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
        }

        public void sipResponseReceived(String uri, OptionsSipResponse sipResponse, OptionsCapInfo capInfo) throws RemoteException {
        }

        public void cmdStatus(OptionsCmdStatus cmdStatus) throws RemoteException {
        }

        public void incomingOptions(String uri, OptionsCapInfo capInfo, int tID) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IOptionsListener)) {
                return new Proxy(obj);
            }
            return (IOptionsListener) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.android.ims.internal.uce.common.StatusCode} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.android.ims.internal.uce.common.StatusCode} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: com.android.ims.internal.uce.options.OptionsCapInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: com.android.ims.internal.uce.options.OptionsCmdStatus} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: com.android.ims.internal.uce.options.OptionsCapInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v24 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: type inference failed for: r1v26 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.ims.internal.uce.options.IOptionsListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00bd
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x00af;
                    case 2: goto L_0x0095;
                    case 3: goto L_0x007b;
                    case 4: goto L_0x004d;
                    case 5: goto L_0x0033;
                    case 6: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0027
                android.os.Parcelable$Creator<com.android.ims.internal.uce.options.OptionsCapInfo> r1 = com.android.ims.internal.uce.options.OptionsCapInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                com.android.ims.internal.uce.options.OptionsCapInfo r1 = (com.android.ims.internal.uce.options.OptionsCapInfo) r1
                goto L_0x0028
            L_0x0027:
            L_0x0028:
                int r4 = r8.readInt()
                r6.incomingOptions(r3, r1, r4)
                r9.writeNoException()
                return r2
            L_0x0033:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0045
                android.os.Parcelable$Creator<com.android.ims.internal.uce.options.OptionsCmdStatus> r1 = com.android.ims.internal.uce.options.OptionsCmdStatus.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                com.android.ims.internal.uce.options.OptionsCmdStatus r1 = (com.android.ims.internal.uce.options.OptionsCmdStatus) r1
                goto L_0x0046
            L_0x0045:
            L_0x0046:
                r6.cmdStatus(r1)
                r9.writeNoException()
                return r2
            L_0x004d:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0063
                android.os.Parcelable$Creator<com.android.ims.internal.uce.options.OptionsSipResponse> r4 = com.android.ims.internal.uce.options.OptionsSipResponse.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                com.android.ims.internal.uce.options.OptionsSipResponse r4 = (com.android.ims.internal.uce.options.OptionsSipResponse) r4
                goto L_0x0064
            L_0x0063:
                r4 = r1
            L_0x0064:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0073
                android.os.Parcelable$Creator<com.android.ims.internal.uce.options.OptionsCapInfo> r1 = com.android.ims.internal.uce.options.OptionsCapInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                com.android.ims.internal.uce.options.OptionsCapInfo r1 = (com.android.ims.internal.uce.options.OptionsCapInfo) r1
                goto L_0x0074
            L_0x0073:
            L_0x0074:
                r6.sipResponseReceived(r3, r4, r1)
                r9.writeNoException()
                return r2
            L_0x007b:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x008d
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.StatusCode> r1 = com.android.ims.internal.uce.common.StatusCode.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                com.android.ims.internal.uce.common.StatusCode r1 = (com.android.ims.internal.uce.common.StatusCode) r1
                goto L_0x008e
            L_0x008d:
            L_0x008e:
                r6.serviceUnavailable(r1)
                r9.writeNoException()
                return r2
            L_0x0095:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00a7
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.StatusCode> r1 = com.android.ims.internal.uce.common.StatusCode.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                com.android.ims.internal.uce.common.StatusCode r1 = (com.android.ims.internal.uce.common.StatusCode) r1
                goto L_0x00a8
            L_0x00a7:
            L_0x00a8:
                r6.serviceAvailable(r1)
                r9.writeNoException()
                return r2
            L_0x00af:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.getVersionCb(r1)
                r9.writeNoException()
                return r2
            L_0x00bd:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.uce.options.IOptionsListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IOptionsListener {
            public static IOptionsListener sDefaultImpl;
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

            public void getVersionCb(String version) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(version);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getVersionCb(version);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serviceAvailable(statusCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().serviceUnavailable(statusCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sipResponseReceived(uri, sipResponse, capInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cmdStatus(cmdStatus);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().incomingOptions(uri, capInfo, tID);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOptionsListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IOptionsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
