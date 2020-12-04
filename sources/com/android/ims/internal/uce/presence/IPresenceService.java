package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;

public interface IPresenceService extends IInterface {
    @UnsupportedAppUsage
    StatusCode addListener(int i, IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getContactCap(int i, String str, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getVersion(int i) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode publishMyCap(int i, PresCapInfo presCapInfo, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode reenableService(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode removeListener(int i, UceLong uceLong) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode setNewFeatureTag(int i, String str, PresServiceInfo presServiceInfo, int i2) throws RemoteException;

    public static class Default implements IPresenceService {
        public StatusCode getVersion(int presenceServiceHdl) throws RemoteException {
            return null;
        }

        public StatusCode addListener(int presenceServiceHdl, IPresenceListener presenceServiceListener, UceLong presenceServiceListenerHdl) throws RemoteException {
            return null;
        }

        public StatusCode removeListener(int presenceServiceHdl, UceLong presenceServiceListenerHdl) throws RemoteException {
            return null;
        }

        public StatusCode reenableService(int presenceServiceHdl, int userData) throws RemoteException {
            return null;
        }

        public StatusCode publishMyCap(int presenceServiceHdl, PresCapInfo myCapInfo, int userData) throws RemoteException {
            return null;
        }

        public StatusCode getContactCap(int presenceServiceHdl, String remoteUri, int userData) throws RemoteException {
            return null;
        }

        public StatusCode getContactListCap(int presenceServiceHdl, String[] remoteUriList, int userData) throws RemoteException {
            return null;
        }

        public StatusCode setNewFeatureTag(int presenceServiceHdl, String featureTag, PresServiceInfo serviceInfo, int userData) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPresenceService {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_publishMyCap = 5;
        static final int TRANSACTION_reenableService = 4;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setNewFeatureTag = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPresenceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPresenceService)) {
                return new Proxy(obj);
            }
            return (IPresenceService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getVersion";
                case 2:
                    return "addListener";
                case 3:
                    return "removeListener";
                case 4:
                    return "reenableService";
                case 5:
                    return "publishMyCap";
                case 6:
                    return "getContactCap";
                case 7:
                    return "getContactListCap";
                case 8:
                    return "setNewFeatureTag";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: com.android.ims.internal.uce.presence.PresCapInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: com.android.ims.internal.uce.presence.PresServiceInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: type inference failed for: r1v24 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: type inference failed for: r1v26 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r9, android.os.Parcel r10, android.os.Parcel r11, int r12) throws android.os.RemoteException {
            /*
                r8 = this;
                java.lang.String r0 = "com.android.ims.internal.uce.presence.IPresenceService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r9 == r1) goto L_0x015e
                r1 = 0
                r3 = 0
                switch(r9) {
                    case 1: goto L_0x0143;
                    case 2: goto L_0x0104;
                    case 3: goto L_0x00d9;
                    case 4: goto L_0x00ba;
                    case 5: goto L_0x008b;
                    case 6: goto L_0x0068;
                    case 7: goto L_0x0045;
                    case 8: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r9, r10, r11, r12)
                return r1
            L_0x0012:
                r10.enforceInterface(r0)
                int r4 = r10.readInt()
                java.lang.String r5 = r10.readString()
                int r6 = r10.readInt()
                if (r6 == 0) goto L_0x002c
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresServiceInfo> r1 = com.android.ims.internal.uce.presence.PresServiceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                com.android.ims.internal.uce.presence.PresServiceInfo r1 = (com.android.ims.internal.uce.presence.PresServiceInfo) r1
                goto L_0x002d
            L_0x002c:
            L_0x002d:
                int r6 = r10.readInt()
                com.android.ims.internal.uce.common.StatusCode r7 = r8.setNewFeatureTag(r4, r5, r1, r6)
                r11.writeNoException()
                if (r7 == 0) goto L_0x0041
                r11.writeInt(r2)
                r7.writeToParcel(r11, r2)
                goto L_0x0044
            L_0x0041:
                r11.writeInt(r3)
            L_0x0044:
                return r2
            L_0x0045:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                java.lang.String[] r4 = r10.createStringArray()
                int r5 = r10.readInt()
                com.android.ims.internal.uce.common.StatusCode r6 = r8.getContactListCap(r1, r4, r5)
                r11.writeNoException()
                if (r6 == 0) goto L_0x0064
                r11.writeInt(r2)
                r6.writeToParcel(r11, r2)
                goto L_0x0067
            L_0x0064:
                r11.writeInt(r3)
            L_0x0067:
                return r2
            L_0x0068:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                java.lang.String r4 = r10.readString()
                int r5 = r10.readInt()
                com.android.ims.internal.uce.common.StatusCode r6 = r8.getContactCap(r1, r4, r5)
                r11.writeNoException()
                if (r6 == 0) goto L_0x0087
                r11.writeInt(r2)
                r6.writeToParcel(r11, r2)
                goto L_0x008a
            L_0x0087:
                r11.writeInt(r3)
            L_0x008a:
                return r2
            L_0x008b:
                r10.enforceInterface(r0)
                int r4 = r10.readInt()
                int r5 = r10.readInt()
                if (r5 == 0) goto L_0x00a1
                android.os.Parcelable$Creator<com.android.ims.internal.uce.presence.PresCapInfo> r1 = com.android.ims.internal.uce.presence.PresCapInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                com.android.ims.internal.uce.presence.PresCapInfo r1 = (com.android.ims.internal.uce.presence.PresCapInfo) r1
                goto L_0x00a2
            L_0x00a1:
            L_0x00a2:
                int r5 = r10.readInt()
                com.android.ims.internal.uce.common.StatusCode r6 = r8.publishMyCap(r4, r1, r5)
                r11.writeNoException()
                if (r6 == 0) goto L_0x00b6
                r11.writeInt(r2)
                r6.writeToParcel(r11, r2)
                goto L_0x00b9
            L_0x00b6:
                r11.writeInt(r3)
            L_0x00b9:
                return r2
            L_0x00ba:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r4 = r10.readInt()
                com.android.ims.internal.uce.common.StatusCode r5 = r8.reenableService(r1, r4)
                r11.writeNoException()
                if (r5 == 0) goto L_0x00d5
                r11.writeInt(r2)
                r5.writeToParcel(r11, r2)
                goto L_0x00d8
            L_0x00d5:
                r11.writeInt(r3)
            L_0x00d8:
                return r2
            L_0x00d9:
                r10.enforceInterface(r0)
                int r4 = r10.readInt()
                int r5 = r10.readInt()
                if (r5 == 0) goto L_0x00ef
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r1 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                com.android.ims.internal.uce.common.UceLong r1 = (com.android.ims.internal.uce.common.UceLong) r1
                goto L_0x00f0
            L_0x00ef:
            L_0x00f0:
                com.android.ims.internal.uce.common.StatusCode r5 = r8.removeListener(r4, r1)
                r11.writeNoException()
                if (r5 == 0) goto L_0x0100
                r11.writeInt(r2)
                r5.writeToParcel(r11, r2)
                goto L_0x0103
            L_0x0100:
                r11.writeInt(r3)
            L_0x0103:
                return r2
            L_0x0104:
                r10.enforceInterface(r0)
                int r4 = r10.readInt()
                android.os.IBinder r5 = r10.readStrongBinder()
                com.android.ims.internal.uce.presence.IPresenceListener r5 = com.android.ims.internal.uce.presence.IPresenceListener.Stub.asInterface(r5)
                int r6 = r10.readInt()
                if (r6 == 0) goto L_0x0122
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r1 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                com.android.ims.internal.uce.common.UceLong r1 = (com.android.ims.internal.uce.common.UceLong) r1
                goto L_0x0123
            L_0x0122:
            L_0x0123:
                com.android.ims.internal.uce.common.StatusCode r6 = r8.addListener(r4, r5, r1)
                r11.writeNoException()
                if (r6 == 0) goto L_0x0133
                r11.writeInt(r2)
                r6.writeToParcel(r11, r2)
                goto L_0x0136
            L_0x0133:
                r11.writeInt(r3)
            L_0x0136:
                if (r1 == 0) goto L_0x013f
                r11.writeInt(r2)
                r1.writeToParcel(r11, r2)
                goto L_0x0142
            L_0x013f:
                r11.writeInt(r3)
            L_0x0142:
                return r2
            L_0x0143:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                com.android.ims.internal.uce.common.StatusCode r4 = r8.getVersion(r1)
                r11.writeNoException()
                if (r4 == 0) goto L_0x015a
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x015d
            L_0x015a:
                r11.writeInt(r3)
            L_0x015d:
                return r2
            L_0x015e:
                r11.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.uce.presence.IPresenceService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPresenceService {
            public static IPresenceService sDefaultImpl;
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

            public StatusCode getVersion(int presenceServiceHdl) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVersion(presenceServiceHdl);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode addListener(int presenceServiceHdl, IPresenceListener presenceServiceListener, UceLong presenceServiceListenerHdl) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    StatusCode _result = null;
                    _data.writeStrongBinder(presenceServiceListener != null ? presenceServiceListener.asBinder() : null);
                    if (presenceServiceListenerHdl != null) {
                        _data.writeInt(1);
                        presenceServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addListener(presenceServiceHdl, presenceServiceListener, presenceServiceListenerHdl);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    }
                    if (_reply.readInt() != 0) {
                        presenceServiceListenerHdl.readFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode removeListener(int presenceServiceHdl, UceLong presenceServiceListenerHdl) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    if (presenceServiceListenerHdl != null) {
                        _data.writeInt(1);
                        presenceServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeListener(presenceServiceHdl, presenceServiceListenerHdl);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode reenableService(int presenceServiceHdl, int userData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    _data.writeInt(userData);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reenableService(presenceServiceHdl, userData);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode publishMyCap(int presenceServiceHdl, PresCapInfo myCapInfo, int userData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    if (myCapInfo != null) {
                        _data.writeInt(1);
                        myCapInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userData);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().publishMyCap(presenceServiceHdl, myCapInfo, userData);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode getContactCap(int presenceServiceHdl, String remoteUri, int userData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    _data.writeString(remoteUri);
                    _data.writeInt(userData);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContactCap(presenceServiceHdl, remoteUri, userData);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode getContactListCap(int presenceServiceHdl, String[] remoteUriList, int userData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    _data.writeStringArray(remoteUriList);
                    _data.writeInt(userData);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContactListCap(presenceServiceHdl, remoteUriList, userData);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StatusCode setNewFeatureTag(int presenceServiceHdl, String featureTag, PresServiceInfo serviceInfo, int userData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    _data.writeString(featureTag);
                    if (serviceInfo != null) {
                        _data.writeInt(1);
                        serviceInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userData);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setNewFeatureTag(presenceServiceHdl, featureTag, serviceInfo, userData);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StatusCode _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPresenceService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPresenceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
