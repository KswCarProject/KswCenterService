package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.CapInfo;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;

public interface IOptionsService extends IInterface {
    @UnsupportedAppUsage
    StatusCode addListener(int i, IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getContactCap(int i, String str, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getMyInfo(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode getVersion(int i) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode removeListener(int i, UceLong uceLong) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode responseIncomingOptions(int i, int i2, int i3, String str, OptionsCapInfo optionsCapInfo, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    StatusCode setMyInfo(int i, CapInfo capInfo, int i2) throws RemoteException;

    public static class Default implements IOptionsService {
        public StatusCode getVersion(int optionsServiceHandle) throws RemoteException {
            return null;
        }

        public StatusCode addListener(int optionsServiceHandle, IOptionsListener optionsListener, UceLong optionsServiceListenerHdl) throws RemoteException {
            return null;
        }

        public StatusCode removeListener(int optionsServiceHandle, UceLong optionsServiceListenerHdl) throws RemoteException {
            return null;
        }

        public StatusCode setMyInfo(int optionsServiceHandle, CapInfo capInfo, int reqUserData) throws RemoteException {
            return null;
        }

        public StatusCode getMyInfo(int optionsServiceHandle, int reqUserdata) throws RemoteException {
            return null;
        }

        public StatusCode getContactCap(int optionsServiceHandle, String remoteURI, int reqUserData) throws RemoteException {
            return null;
        }

        public StatusCode getContactListCap(int optionsServiceHandle, String[] remoteURIList, int reqUserData) throws RemoteException {
            return null;
        }

        public StatusCode responseIncomingOptions(int optionsServiceHandle, int tId, int sipResponseCode, String reasonPhrase, OptionsCapInfo capInfo, boolean bContactInBL) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOptionsService {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getMyInfo = 5;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_responseIncomingOptions = 8;
        static final int TRANSACTION_setMyInfo = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOptionsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IOptionsService)) {
                return new Proxy(obj);
            }
            return (IOptionsService) iin;
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
                    return "setMyInfo";
                case 5:
                    return "getMyInfo";
                case 6:
                    return "getContactCap";
                case 7:
                    return "getContactListCap";
                case 8:
                    return "responseIncomingOptions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.android.ims.internal.uce.common.UceLong} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.android.ims.internal.uce.common.CapInfo} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v18 */
        /* JADX WARNING: type inference failed for: r0v26 */
        /* JADX WARNING: type inference failed for: r0v27 */
        /* JADX WARNING: type inference failed for: r0v28 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "com.android.ims.internal.uce.options.IOptionsService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x017c
                r0 = 0
                r13 = 0
                switch(r8) {
                    case 1: goto L_0x0161;
                    case 2: goto L_0x0122;
                    case 3: goto L_0x00f7;
                    case 4: goto L_0x00c8;
                    case 5: goto L_0x00a9;
                    case 6: goto L_0x0086;
                    case 7: goto L_0x0063;
                    case 8: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                int r14 = r20.readInt()
                int r15 = r20.readInt()
                int r16 = r20.readInt()
                java.lang.String r17 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x003d
                android.os.Parcelable$Creator<com.android.ims.internal.uce.options.OptionsCapInfo> r0 = com.android.ims.internal.uce.options.OptionsCapInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                com.android.ims.internal.uce.options.OptionsCapInfo r0 = (com.android.ims.internal.uce.options.OptionsCapInfo) r0
            L_0x003b:
                r5 = r0
                goto L_0x003e
            L_0x003d:
                goto L_0x003b
            L_0x003e:
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0046
                r6 = r12
                goto L_0x0047
            L_0x0046:
                r6 = r13
            L_0x0047:
                r0 = r18
                r1 = r14
                r2 = r15
                r3 = r16
                r4 = r17
                com.android.ims.internal.uce.common.StatusCode r0 = r0.responseIncomingOptions(r1, r2, r3, r4, r5, r6)
                r21.writeNoException()
                if (r0 == 0) goto L_0x005f
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x0062
            L_0x005f:
                r10.writeInt(r13)
            L_0x0062:
                return r12
            L_0x0063:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                java.lang.String[] r1 = r20.createStringArray()
                int r2 = r20.readInt()
                com.android.ims.internal.uce.common.StatusCode r3 = r7.getContactListCap(r0, r1, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0082
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0085
            L_0x0082:
                r10.writeInt(r13)
            L_0x0085:
                return r12
            L_0x0086:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                com.android.ims.internal.uce.common.StatusCode r3 = r7.getContactCap(r0, r1, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x00a5
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x00a8
            L_0x00a5:
                r10.writeInt(r13)
            L_0x00a8:
                return r12
            L_0x00a9:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                com.android.ims.internal.uce.common.StatusCode r2 = r7.getMyInfo(r0, r1)
                r21.writeNoException()
                if (r2 == 0) goto L_0x00c4
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x00c7
            L_0x00c4:
                r10.writeInt(r13)
            L_0x00c7:
                return r12
            L_0x00c8:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00de
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.CapInfo> r0 = com.android.ims.internal.uce.common.CapInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                com.android.ims.internal.uce.common.CapInfo r0 = (com.android.ims.internal.uce.common.CapInfo) r0
                goto L_0x00df
            L_0x00de:
            L_0x00df:
                int r2 = r20.readInt()
                com.android.ims.internal.uce.common.StatusCode r3 = r7.setMyInfo(r1, r0, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x00f3
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x00f6
            L_0x00f3:
                r10.writeInt(r13)
            L_0x00f6:
                return r12
            L_0x00f7:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x010d
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r0 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                com.android.ims.internal.uce.common.UceLong r0 = (com.android.ims.internal.uce.common.UceLong) r0
                goto L_0x010e
            L_0x010d:
            L_0x010e:
                com.android.ims.internal.uce.common.StatusCode r2 = r7.removeListener(r1, r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x011e
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x0121
            L_0x011e:
                r10.writeInt(r13)
            L_0x0121:
                return r12
            L_0x0122:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                android.os.IBinder r2 = r20.readStrongBinder()
                com.android.ims.internal.uce.options.IOptionsListener r2 = com.android.ims.internal.uce.options.IOptionsListener.Stub.asInterface(r2)
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x0140
                android.os.Parcelable$Creator<com.android.ims.internal.uce.common.UceLong> r0 = com.android.ims.internal.uce.common.UceLong.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                com.android.ims.internal.uce.common.UceLong r0 = (com.android.ims.internal.uce.common.UceLong) r0
                goto L_0x0141
            L_0x0140:
            L_0x0141:
                com.android.ims.internal.uce.common.StatusCode r3 = r7.addListener(r1, r2, r0)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0151
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0154
            L_0x0151:
                r10.writeInt(r13)
            L_0x0154:
                if (r0 == 0) goto L_0x015d
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x0160
            L_0x015d:
                r10.writeInt(r13)
            L_0x0160:
                return r12
            L_0x0161:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                com.android.ims.internal.uce.common.StatusCode r1 = r7.getVersion(r0)
                r21.writeNoException()
                if (r1 == 0) goto L_0x0178
                r10.writeInt(r12)
                r1.writeToParcel(r10, r12)
                goto L_0x017b
            L_0x0178:
                r10.writeInt(r13)
            L_0x017b:
                return r12
            L_0x017c:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.uce.options.IOptionsService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IOptionsService {
            public static IOptionsService sDefaultImpl;
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

            public StatusCode getVersion(int optionsServiceHandle) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVersion(optionsServiceHandle);
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

            public StatusCode addListener(int optionsServiceHandle, IOptionsListener optionsListener, UceLong optionsServiceListenerHdl) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    StatusCode _result = null;
                    _data.writeStrongBinder(optionsListener != null ? optionsListener.asBinder() : null);
                    if (optionsServiceListenerHdl != null) {
                        _data.writeInt(1);
                        optionsServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addListener(optionsServiceHandle, optionsListener, optionsServiceListenerHdl);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StatusCode.CREATOR.createFromParcel(_reply);
                    }
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

            public StatusCode removeListener(int optionsServiceHandle, UceLong optionsServiceListenerHdl) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    if (optionsServiceListenerHdl != null) {
                        _data.writeInt(1);
                        optionsServiceListenerHdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeListener(optionsServiceHandle, optionsServiceListenerHdl);
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

            public StatusCode setMyInfo(int optionsServiceHandle, CapInfo capInfo, int reqUserData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    if (capInfo != null) {
                        _data.writeInt(1);
                        capInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(reqUserData);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMyInfo(optionsServiceHandle, capInfo, reqUserData);
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

            public StatusCode getMyInfo(int optionsServiceHandle, int reqUserdata) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    _data.writeInt(reqUserdata);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMyInfo(optionsServiceHandle, reqUserdata);
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

            public StatusCode getContactCap(int optionsServiceHandle, String remoteURI, int reqUserData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    _data.writeString(remoteURI);
                    _data.writeInt(reqUserData);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContactCap(optionsServiceHandle, remoteURI, reqUserData);
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

            public StatusCode getContactListCap(int optionsServiceHandle, String[] remoteURIList, int reqUserData) throws RemoteException {
                StatusCode _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(optionsServiceHandle);
                    _data.writeStringArray(remoteURIList);
                    _data.writeInt(reqUserData);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContactListCap(optionsServiceHandle, remoteURIList, reqUserData);
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

            public StatusCode responseIncomingOptions(int optionsServiceHandle, int tId, int sipResponseCode, String reasonPhrase, OptionsCapInfo capInfo, boolean bContactInBL) throws RemoteException {
                StatusCode _result;
                OptionsCapInfo optionsCapInfo = capInfo;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(optionsServiceHandle);
                        try {
                            _data.writeInt(tId);
                        } catch (Throwable th) {
                            th = th;
                            int i = sipResponseCode;
                            String str = reasonPhrase;
                            boolean z = bContactInBL;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(sipResponseCode);
                        } catch (Throwable th2) {
                            th = th2;
                            String str2 = reasonPhrase;
                            boolean z2 = bContactInBL;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i2 = tId;
                        int i3 = sipResponseCode;
                        String str22 = reasonPhrase;
                        boolean z22 = bContactInBL;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(reasonPhrase);
                        if (optionsCapInfo != null) {
                            _data.writeInt(1);
                            optionsCapInfo.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(bContactInBL ? 1 : 0);
                            if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
                            }
                            StatusCode responseIncomingOptions = Stub.getDefaultImpl().responseIncomingOptions(optionsServiceHandle, tId, sipResponseCode, reasonPhrase, capInfo, bContactInBL);
                            _reply.recycle();
                            _data.recycle();
                            return responseIncomingOptions;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        boolean z222 = bContactInBL;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i4 = optionsServiceHandle;
                    int i22 = tId;
                    int i32 = sipResponseCode;
                    String str222 = reasonPhrase;
                    boolean z2222 = bContactInBL;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }
        }

        public static boolean setDefaultImpl(IOptionsService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IOptionsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
