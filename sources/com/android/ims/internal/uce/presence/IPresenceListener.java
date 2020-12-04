package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;

public interface IPresenceListener extends IInterface {
    @UnsupportedAppUsage
    void capInfoReceived(String str, PresTupleInfo[] presTupleInfoArr) throws RemoteException;

    @UnsupportedAppUsage
    void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException;

    @UnsupportedAppUsage
    void getVersionCb(String str) throws RemoteException;

    @UnsupportedAppUsage
    void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] presResInfoArr) throws RemoteException;

    @UnsupportedAppUsage
    void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException;

    @UnsupportedAppUsage
    void serviceAvailable(StatusCode statusCode) throws RemoteException;

    @UnsupportedAppUsage
    void serviceUnAvailable(StatusCode statusCode) throws RemoteException;

    @UnsupportedAppUsage
    void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException;

    @UnsupportedAppUsage
    void unpublishMessageSent() throws RemoteException;

    public static class Default implements IPresenceListener {
        public void getVersionCb(String version) throws RemoteException {
        }

        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
        }

        public void publishTriggering(PresPublishTriggerType publishTrigger) throws RemoteException {
        }

        public void cmdStatus(PresCmdStatus cmdStatus) throws RemoteException {
        }

        public void sipResponseReceived(PresSipResponse sipResponse) throws RemoteException {
        }

        public void capInfoReceived(String presentityURI, PresTupleInfo[] tupleInfo) throws RemoteException {
        }

        public void listCapInfoReceived(PresRlmiInfo rlmiInfo, PresResInfo[] resInfo) throws RemoteException {
        }

        public void unpublishMessageSent() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPresenceListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceListener";
        static final int TRANSACTION_capInfoReceived = 7;
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_listCapInfoReceived = 8;
        static final int TRANSACTION_publishTriggering = 4;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnAvailable = 3;
        static final int TRANSACTION_sipResponseReceived = 6;
        static final int TRANSACTION_unpublishMessageSent = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPresenceListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPresenceListener)) {
                return new Proxy(obj);
            }
            return (IPresenceListener) iin;
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
                    return "serviceUnAvailable";
                case 4:
                    return "publishTriggering";
                case 5:
                    return "cmdStatus";
                case 6:
                    return "sipResponseReceived";
                case 7:
                    return "capInfoReceived";
                case 8:
                    return "listCapInfoReceived";
                case 9:
                    return "unpublishMessageSent";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.android.ims.internal.uce.common.StatusCode} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.android.ims.internal.uce.common.StatusCode} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: com.android.ims.internal.uce.presence.PresPublishTriggerType} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: com.android.ims.internal.uce.presence.PresCmdStatus} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: com.android.ims.internal.uce.presence.PresSipResponse} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: com.android.ims.internal.uce.presence.PresRlmiInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: type inference failed for: r1v34 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "com.android.ims.internal.uce.presence.IPresenceListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x00e3
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x00d5;
                    case 2: goto L_0x00bb;
                    case 3: goto L_0x00a1;
                    case 4: goto L_0x0087;
                    case 5: goto L_0x006d;
                    case 6: goto L_0x0053;
                    case 7: goto L_0x003d;
                    case 8: goto L_0x001b;
                    case 9: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                r4.unpublishMessageSent()
                r7.writeNoException()
                return r2
            L_0x001b:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x002d
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresRlmiInfo> r1 = com.android.ims.internal.uce.presence.PresRlmiInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.ims.internal.uce.presence.PresRlmiInfo r1 = (com.android.ims.internal.uce.presence.PresRlmiInfo) r1
                goto L_0x002e
            L_0x002d:
            L_0x002e:
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresResInfo> r3 = com.android.ims.internal.uce.presence.PresResInfo.CREATOR
                java.lang.Object[] r3 = r6.createTypedArray(r3)
                com.android.ims.internal.uce.presence.PresResInfo[] r3 = (com.android.ims.internal.uce.presence.PresResInfo[]) r3
                r4.listCapInfoReceived(r1, r3)
                r7.writeNoException()
                return r2
            L_0x003d:
                r6.enforceInterface(r0)
                java.lang.String r1 = r6.readString()
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresTupleInfo> r3 = com.android.ims.internal.uce.presence.PresTupleInfo.CREATOR
                java.lang.Object[] r3 = r6.createTypedArray(r3)
                com.android.ims.internal.uce.presence.PresTupleInfo[] r3 = (com.android.ims.internal.uce.presence.PresTupleInfo[]) r3
                r4.capInfoReceived(r1, r3)
                r7.writeNoException()
                return r2
            L_0x0053:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0065
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresSipResponse> r1 = com.android.ims.internal.uce.presence.PresSipResponse.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.ims.internal.uce.presence.PresSipResponse r1 = (com.android.ims.internal.uce.presence.PresSipResponse) r1
                goto L_0x0066
            L_0x0065:
            L_0x0066:
                r4.sipResponseReceived(r1)
                r7.writeNoException()
                return r2
            L_0x006d:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x007f
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresCmdStatus> r1 = com.android.ims.internal.uce.presence.PresCmdStatus.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.ims.internal.uce.presence.PresCmdStatus r1 = (com.android.ims.internal.uce.presence.PresCmdStatus) r1
                goto L_0x0080
            L_0x007f:
            L_0x0080:
                r4.cmdStatus(r1)
                r7.writeNoException()
                return r2
            L_0x0087:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0099
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresPublishTriggerType> r1 = com.android.ims.internal.uce.presence.PresPublishTriggerType.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.ims.internal.uce.presence.PresPublishTriggerType r1 = (com.android.ims.internal.uce.presence.PresPublishTriggerType) r1
                goto L_0x009a
            L_0x0099:
            L_0x009a:
                r4.publishTriggering(r1)
                r7.writeNoException()
                return r2
            L_0x00a1:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x00b3
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.StatusCode> r1 = com.android.ims.internal.uce.common.StatusCode.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.ims.internal.uce.common.StatusCode r1 = (com.android.ims.internal.uce.common.StatusCode) r1
                goto L_0x00b4
            L_0x00b3:
            L_0x00b4:
                r4.serviceUnAvailable(r1)
                r7.writeNoException()
                return r2
            L_0x00bb:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x00cd
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.StatusCode> r1 = com.android.ims.internal.uce.common.StatusCode.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.ims.internal.uce.common.StatusCode r1 = (com.android.ims.internal.uce.common.StatusCode) r1
                goto L_0x00ce
            L_0x00cd:
            L_0x00ce:
                r4.serviceAvailable(r1)
                r7.writeNoException()
                return r2
            L_0x00d5:
                r6.enforceInterface(r0)
                java.lang.String r1 = r6.readString()
                r4.getVersionCb(r1)
                r7.writeNoException()
                return r2
            L_0x00e3:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.uce.presence.IPresenceListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPresenceListener {
            public static IPresenceListener sDefaultImpl;
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

            public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
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
                    Stub.getDefaultImpl().serviceUnAvailable(statusCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void publishTriggering(PresPublishTriggerType publishTrigger) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (publishTrigger != null) {
                        _data.writeInt(1);
                        publishTrigger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().publishTriggering(publishTrigger);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cmdStatus(PresCmdStatus cmdStatus) throws RemoteException {
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

            public void sipResponseReceived(PresSipResponse sipResponse) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sipResponse != null) {
                        _data.writeInt(1);
                        sipResponse.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sipResponseReceived(sipResponse);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void capInfoReceived(String presentityURI, PresTupleInfo[] tupleInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(presentityURI);
                    _data.writeTypedArray(tupleInfo, 0);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().capInfoReceived(presentityURI, tupleInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void listCapInfoReceived(PresRlmiInfo rlmiInfo, PresResInfo[] resInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rlmiInfo != null) {
                        _data.writeInt(1);
                        rlmiInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedArray(resInfo, 0);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().listCapInfoReceived(rlmiInfo, resInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unpublishMessageSent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unpublishMessageSent();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPresenceListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPresenceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
