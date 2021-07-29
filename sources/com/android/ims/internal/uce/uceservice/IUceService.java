package com.android.ims.internal.uce.uceservice;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.IOptionsService;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.IPresenceService;

public interface IUceService extends IInterface {
    @UnsupportedAppUsage
    int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException;

    int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String str) throws RemoteException;

    @UnsupportedAppUsage
    int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException;

    int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String str) throws RemoteException;

    @UnsupportedAppUsage
    void destroyOptionsService(int i) throws RemoteException;

    @UnsupportedAppUsage
    void destroyPresenceService(int i) throws RemoteException;

    @UnsupportedAppUsage
    IOptionsService getOptionsService() throws RemoteException;

    IOptionsService getOptionsServiceForSubscription(String str) throws RemoteException;

    @UnsupportedAppUsage
    IPresenceService getPresenceService() throws RemoteException;

    IPresenceService getPresenceServiceForSubscription(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean getServiceStatus() throws RemoteException;

    @UnsupportedAppUsage
    boolean isServiceStarted() throws RemoteException;

    @UnsupportedAppUsage
    boolean startService(IUceListener iUceListener) throws RemoteException;

    @UnsupportedAppUsage
    boolean stopService() throws RemoteException;

    public static class Default implements IUceService {
        public boolean startService(IUceListener uceListener) throws RemoteException {
            return false;
        }

        public boolean stopService() throws RemoteException {
            return false;
        }

        public boolean isServiceStarted() throws RemoteException {
            return false;
        }

        public int createOptionsService(IOptionsListener optionsListener, UceLong optionsServiceListenerHdl) throws RemoteException {
            return 0;
        }

        public int createOptionsServiceForSubscription(IOptionsListener optionsListener, UceLong optionsServiceListenerHdl, String iccId) throws RemoteException {
            return 0;
        }

        public void destroyOptionsService(int optionsServiceHandle) throws RemoteException {
        }

        public int createPresenceService(IPresenceListener presenceServiceListener, UceLong presenceServiceListenerHdl) throws RemoteException {
            return 0;
        }

        public int createPresenceServiceForSubscription(IPresenceListener presenceServiceListener, UceLong presenceServiceListenerHdl, String iccId) throws RemoteException {
            return 0;
        }

        public void destroyPresenceService(int presenceServiceHdl) throws RemoteException {
        }

        public boolean getServiceStatus() throws RemoteException {
            return false;
        }

        public IPresenceService getPresenceService() throws RemoteException {
            return null;
        }

        public IPresenceService getPresenceServiceForSubscription(String iccId) throws RemoteException {
            return null;
        }

        public IOptionsService getOptionsService() throws RemoteException {
            return null;
        }

        public IOptionsService getOptionsServiceForSubscription(String iccId) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUceService {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.uceservice.IUceService";
        static final int TRANSACTION_createOptionsService = 4;
        static final int TRANSACTION_createOptionsServiceForSubscription = 5;
        static final int TRANSACTION_createPresenceService = 7;
        static final int TRANSACTION_createPresenceServiceForSubscription = 8;
        static final int TRANSACTION_destroyOptionsService = 6;
        static final int TRANSACTION_destroyPresenceService = 9;
        static final int TRANSACTION_getOptionsService = 13;
        static final int TRANSACTION_getOptionsServiceForSubscription = 14;
        static final int TRANSACTION_getPresenceService = 11;
        static final int TRANSACTION_getPresenceServiceForSubscription = 12;
        static final int TRANSACTION_getServiceStatus = 10;
        static final int TRANSACTION_isServiceStarted = 3;
        static final int TRANSACTION_startService = 1;
        static final int TRANSACTION_stopService = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUceService)) {
                return new Proxy(obj);
            }
            return (IUceService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startService";
                case 2:
                    return "stopService";
                case 3:
                    return "isServiceStarted";
                case 4:
                    return "createOptionsService";
                case 5:
                    return "createOptionsServiceForSubscription";
                case 6:
                    return "destroyOptionsService";
                case 7:
                    return "createPresenceService";
                case 8:
                    return "createPresenceServiceForSubscription";
                case 9:
                    return "destroyPresenceService";
                case 10:
                    return "getServiceStatus";
                case 11:
                    return "getPresenceService";
                case 12:
                    return "getPresenceServiceForSubscription";
                case 13:
                    return "getOptionsService";
                case 14:
                    return "getOptionsServiceForSubscription";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v18, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v20, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v22, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v24, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v26 */
        /* JADX WARNING: type inference failed for: r3v27 */
        /* JADX WARNING: type inference failed for: r3v28 */
        /* JADX WARNING: type inference failed for: r3v29 */
        /* JADX WARNING: type inference failed for: r3v30 */
        /* JADX WARNING: type inference failed for: r3v31 */
        /* JADX WARNING: type inference failed for: r3v32 */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.ims.internal.uce.uceservice.IUceService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x019a
                r1 = 0
                r3 = 0
                switch(r8) {
                    case 1: goto L_0x0184;
                    case 2: goto L_0x0176;
                    case 3: goto L_0x0168;
                    case 4: goto L_0x0136;
                    case 5: goto L_0x0100;
                    case 6: goto L_0x00f2;
                    case 7: goto L_0x00c0;
                    case 8: goto L_0x008a;
                    case 9: goto L_0x007c;
                    case 10: goto L_0x006e;
                    case 11: goto L_0x0059;
                    case 12: goto L_0x0040;
                    case 13: goto L_0x002b;
                    case 14: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0012:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                com.android.ims.internal.uce.options.IOptionsService r4 = r7.getOptionsServiceForSubscription(r1)
                r10.writeNoException()
                if (r4 == 0) goto L_0x0027
                android.os.IBinder r3 = r4.asBinder()
            L_0x0027:
                r10.writeStrongBinder(r3)
                return r2
            L_0x002b:
                r9.enforceInterface(r0)
                com.android.ims.internal.uce.options.IOptionsService r1 = r7.getOptionsService()
                r10.writeNoException()
                if (r1 == 0) goto L_0x003c
                android.os.IBinder r3 = r1.asBinder()
            L_0x003c:
                r10.writeStrongBinder(r3)
                return r2
            L_0x0040:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                com.android.ims.internal.uce.presence.IPresenceService r4 = r7.getPresenceServiceForSubscription(r1)
                r10.writeNoException()
                if (r4 == 0) goto L_0x0055
                android.os.IBinder r3 = r4.asBinder()
            L_0x0055:
                r10.writeStrongBinder(r3)
                return r2
            L_0x0059:
                r9.enforceInterface(r0)
                com.android.ims.internal.uce.presence.IPresenceService r1 = r7.getPresenceService()
                r10.writeNoException()
                if (r1 == 0) goto L_0x006a
                android.os.IBinder r3 = r1.asBinder()
            L_0x006a:
                r10.writeStrongBinder(r3)
                return r2
            L_0x006e:
                r9.enforceInterface(r0)
                boolean r1 = r7.getServiceStatus()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x007c:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.destroyPresenceService(r1)
                r10.writeNoException()
                return r2
            L_0x008a:
                r9.enforceInterface(r0)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.uce.presence.IPresenceListener r4 = com.android.ims.internal.uce.presence.IPresenceListener.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x00a4
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r3 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                com.android.ims.internal.uce.common.UceLong r3 = (com.android.ims.internal.uce.common.UceLong) r3
                goto L_0x00a5
            L_0x00a4:
            L_0x00a5:
                java.lang.String r5 = r9.readString()
                int r6 = r7.createPresenceServiceForSubscription(r4, r3, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                if (r3 == 0) goto L_0x00bc
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x00bf
            L_0x00bc:
                r10.writeInt(r1)
            L_0x00bf:
                return r2
            L_0x00c0:
                r9.enforceInterface(r0)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.uce.presence.IPresenceListener r4 = com.android.ims.internal.uce.presence.IPresenceListener.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x00da
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r3 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                com.android.ims.internal.uce.common.UceLong r3 = (com.android.ims.internal.uce.common.UceLong) r3
                goto L_0x00db
            L_0x00da:
            L_0x00db:
                int r5 = r7.createPresenceService(r4, r3)
                r10.writeNoException()
                r10.writeInt(r5)
                if (r3 == 0) goto L_0x00ee
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x00f1
            L_0x00ee:
                r10.writeInt(r1)
            L_0x00f1:
                return r2
            L_0x00f2:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.destroyOptionsService(r1)
                r10.writeNoException()
                return r2
            L_0x0100:
                r9.enforceInterface(r0)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.uce.options.IOptionsListener r4 = com.android.ims.internal.uce.options.IOptionsListener.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x011a
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r3 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                com.android.ims.internal.uce.common.UceLong r3 = (com.android.ims.internal.uce.common.UceLong) r3
                goto L_0x011b
            L_0x011a:
            L_0x011b:
                java.lang.String r5 = r9.readString()
                int r6 = r7.createOptionsServiceForSubscription(r4, r3, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                if (r3 == 0) goto L_0x0132
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x0135
            L_0x0132:
                r10.writeInt(r1)
            L_0x0135:
                return r2
            L_0x0136:
                r9.enforceInterface(r0)
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.uce.options.IOptionsListener r4 = com.android.ims.internal.uce.options.IOptionsListener.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0150
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r3 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                com.android.ims.internal.uce.common.UceLong r3 = (com.android.ims.internal.uce.common.UceLong) r3
                goto L_0x0151
            L_0x0150:
            L_0x0151:
                int r5 = r7.createOptionsService(r4, r3)
                r10.writeNoException()
                r10.writeInt(r5)
                if (r3 == 0) goto L_0x0164
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x0167
            L_0x0164:
                r10.writeInt(r1)
            L_0x0167:
                return r2
            L_0x0168:
                r9.enforceInterface(r0)
                boolean r1 = r7.isServiceStarted()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0176:
                r9.enforceInterface(r0)
                boolean r1 = r7.stopService()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0184:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.uce.uceservice.IUceListener r1 = com.android.ims.internal.uce.uceservice.IUceListener.Stub.asInterface(r1)
                boolean r3 = r7.startService(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x019a:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.uce.uceservice.IUceService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IUceService {
            public static IUceService sDefaultImpl;
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

            public boolean startService(IUceListener uceListener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(uceListener != null ? uceListener.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startService(uceListener);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean stopService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopService();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isServiceStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isServiceStarted();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createOptionsService(IOptionsListener optionsListener, UceLong optionsServiceListenerHdl) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(optionsListener != null ? optionsListener.asBinder() : null);
                    if (optionsServiceListenerHdl != null) {
                        _data.writeInt(1);
                        optionsServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createOptionsService(optionsListener, optionsServiceListenerHdl);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (_reply.readInt() != 0) {
                        optionsServiceListenerHdl.readFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createOptionsServiceForSubscription(IOptionsListener optionsListener, UceLong optionsServiceListenerHdl, String iccId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(optionsListener != null ? optionsListener.asBinder() : null);
                    if (optionsServiceListenerHdl != null) {
                        _data.writeInt(1);
                        optionsServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(iccId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createOptionsServiceForSubscription(optionsListener, optionsServiceListenerHdl, iccId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (_reply.readInt() != 0) {
                        optionsServiceListenerHdl.readFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyOptionsService(int optionsServiceHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().destroyOptionsService(optionsServiceHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createPresenceService(IPresenceListener presenceServiceListener, UceLong presenceServiceListenerHdl) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(presenceServiceListener != null ? presenceServiceListener.asBinder() : null);
                    if (presenceServiceListenerHdl != null) {
                        _data.writeInt(1);
                        presenceServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createPresenceService(presenceServiceListener, presenceServiceListenerHdl);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
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

            public int createPresenceServiceForSubscription(IPresenceListener presenceServiceListener, UceLong presenceServiceListenerHdl, String iccId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(presenceServiceListener != null ? presenceServiceListener.asBinder() : null);
                    if (presenceServiceListenerHdl != null) {
                        _data.writeInt(1);
                        presenceServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(iccId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createPresenceServiceForSubscription(presenceServiceListener, presenceServiceListenerHdl, iccId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
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

            public void destroyPresenceService(int presenceServiceHdl) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presenceServiceHdl);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().destroyPresenceService(presenceServiceHdl);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getServiceStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServiceStatus();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPresenceService getPresenceService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPresenceService();
                    }
                    _reply.readException();
                    IPresenceService _result = IPresenceService.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IPresenceService getPresenceServiceForSubscription(String iccId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iccId);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPresenceServiceForSubscription(iccId);
                    }
                    _reply.readException();
                    IPresenceService _result = IPresenceService.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IOptionsService getOptionsService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOptionsService();
                    }
                    _reply.readException();
                    IOptionsService _result = IOptionsService.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IOptionsService getOptionsServiceForSubscription(String iccId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iccId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOptionsServiceForSubscription(iccId);
                    }
                    _reply.readException();
                    IOptionsService _result = IOptionsService.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUceService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IUceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
