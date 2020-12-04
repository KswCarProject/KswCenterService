package com.android.ims.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;

public interface IImsService extends IInterface {
    void addRegistrationListener(int i, int i2, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void close(int i) throws RemoteException;

    ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException;

    IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) throws RemoteException;

    IImsConfig getConfigInterface(int i) throws RemoteException;

    IImsEcbm getEcbmInterface(int i) throws RemoteException;

    IImsMultiEndpoint getMultiEndpointInterface(int i) throws RemoteException;

    IImsCallSession getPendingCallSession(int i, String str) throws RemoteException;

    IImsUt getUtInterface(int i) throws RemoteException;

    boolean isConnected(int i, int i2, int i3) throws RemoteException;

    boolean isOpened(int i) throws RemoteException;

    int open(int i, int i2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void setRegistrationListener(int i, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void setUiTTYMode(int i, int i2, Message message) throws RemoteException;

    void turnOffIms(int i) throws RemoteException;

    void turnOnIms(int i) throws RemoteException;

    public static class Default implements IImsService {
        public int open(int phoneId, int serviceClass, PendingIntent incomingCallIntent, IImsRegistrationListener listener) throws RemoteException {
            return 0;
        }

        public void close(int serviceId) throws RemoteException {
        }

        public boolean isConnected(int serviceId, int serviceType, int callType) throws RemoteException {
            return false;
        }

        public boolean isOpened(int serviceId) throws RemoteException {
            return false;
        }

        public void setRegistrationListener(int serviceId, IImsRegistrationListener listener) throws RemoteException {
        }

        public void addRegistrationListener(int phoneId, int serviceClass, IImsRegistrationListener listener) throws RemoteException {
        }

        public ImsCallProfile createCallProfile(int serviceId, int serviceType, int callType) throws RemoteException {
            return null;
        }

        public IImsCallSession createCallSession(int serviceId, ImsCallProfile profile, IImsCallSessionListener listener) throws RemoteException {
            return null;
        }

        public IImsCallSession getPendingCallSession(int serviceId, String callId) throws RemoteException {
            return null;
        }

        public IImsUt getUtInterface(int serviceId) throws RemoteException {
            return null;
        }

        public IImsConfig getConfigInterface(int phoneId) throws RemoteException {
            return null;
        }

        public void turnOnIms(int phoneId) throws RemoteException {
        }

        public void turnOffIms(int phoneId) throws RemoteException {
        }

        public IImsEcbm getEcbmInterface(int serviceId) throws RemoteException {
            return null;
        }

        public void setUiTTYMode(int serviceId, int uiTtyMode, Message onComplete) throws RemoteException {
        }

        public IImsMultiEndpoint getMultiEndpointInterface(int serviceId) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsService {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsService";
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_createCallProfile = 7;
        static final int TRANSACTION_createCallSession = 8;
        static final int TRANSACTION_getConfigInterface = 11;
        static final int TRANSACTION_getEcbmInterface = 14;
        static final int TRANSACTION_getMultiEndpointInterface = 16;
        static final int TRANSACTION_getPendingCallSession = 9;
        static final int TRANSACTION_getUtInterface = 10;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_setRegistrationListener = 5;
        static final int TRANSACTION_setUiTTYMode = 15;
        static final int TRANSACTION_turnOffIms = 13;
        static final int TRANSACTION_turnOnIms = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsService)) {
                return new Proxy(obj);
            }
            return (IImsService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "open";
                case 2:
                    return "close";
                case 3:
                    return "isConnected";
                case 4:
                    return "isOpened";
                case 5:
                    return "setRegistrationListener";
                case 6:
                    return "addRegistrationListener";
                case 7:
                    return "createCallProfile";
                case 8:
                    return "createCallSession";
                case 9:
                    return "getPendingCallSession";
                case 10:
                    return "getUtInterface";
                case 11:
                    return "getConfigInterface";
                case 12:
                    return "turnOnIms";
                case 13:
                    return "turnOffIms";
                case 14:
                    return "getEcbmInterface";
                case 15:
                    return "setUiTTYMode";
                case 16:
                    return "getMultiEndpointInterface";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: android.os.Message} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v12, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v14, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v16, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v18, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v22, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v28, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: type inference failed for: r1v34 */
        /* JADX WARNING: type inference failed for: r1v35 */
        /* JADX WARNING: type inference failed for: r1v36 */
        /* JADX WARNING: type inference failed for: r1v37 */
        /* JADX WARNING: type inference failed for: r1v38 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.ims.internal.IImsService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x01bd
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x018f;
                    case 2: goto L_0x0181;
                    case 3: goto L_0x0167;
                    case 4: goto L_0x0155;
                    case 5: goto L_0x013f;
                    case 6: goto L_0x0125;
                    case 7: goto L_0x0101;
                    case 8: goto L_0x00d0;
                    case 9: goto L_0x00b3;
                    case 10: goto L_0x009a;
                    case 11: goto L_0x0081;
                    case 12: goto L_0x0073;
                    case 13: goto L_0x0065;
                    case 14: goto L_0x004c;
                    case 15: goto L_0x002a;
                    case 16: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                com.android.ims.internal.IImsMultiEndpoint r4 = r7.getMultiEndpointInterface(r3)
                r10.writeNoException()
                if (r4 == 0) goto L_0x0026
                android.os.IBinder r1 = r4.asBinder()
            L_0x0026:
                r10.writeStrongBinder(r1)
                return r2
            L_0x002a:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0044
                android.os.Parcelable$Creator<android.os.Message> r1 = android.os.Message.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Message r1 = (android.os.Message) r1
                goto L_0x0045
            L_0x0044:
            L_0x0045:
                r7.setUiTTYMode(r3, r4, r1)
                r10.writeNoException()
                return r2
            L_0x004c:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                com.android.ims.internal.IImsEcbm r4 = r7.getEcbmInterface(r3)
                r10.writeNoException()
                if (r4 == 0) goto L_0x0061
                android.os.IBinder r1 = r4.asBinder()
            L_0x0061:
                r10.writeStrongBinder(r1)
                return r2
            L_0x0065:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.turnOffIms(r1)
                r10.writeNoException()
                return r2
            L_0x0073:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.turnOnIms(r1)
                r10.writeNoException()
                return r2
            L_0x0081:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                com.android.ims.internal.IImsConfig r4 = r7.getConfigInterface(r3)
                r10.writeNoException()
                if (r4 == 0) goto L_0x0096
                android.os.IBinder r1 = r4.asBinder()
            L_0x0096:
                r10.writeStrongBinder(r1)
                return r2
            L_0x009a:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                com.android.ims.internal.IImsUt r4 = r7.getUtInterface(r3)
                r10.writeNoException()
                if (r4 == 0) goto L_0x00af
                android.os.IBinder r1 = r4.asBinder()
            L_0x00af:
                r10.writeStrongBinder(r1)
                return r2
            L_0x00b3:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                java.lang.String r4 = r9.readString()
                com.android.ims.internal.IImsCallSession r5 = r7.getPendingCallSession(r3, r4)
                r10.writeNoException()
                if (r5 == 0) goto L_0x00cc
                android.os.IBinder r1 = r5.asBinder()
            L_0x00cc:
                r10.writeStrongBinder(r1)
                return r2
            L_0x00d0:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00e6
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r4 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r4 = (android.telephony.ims.ImsCallProfile) r4
                goto L_0x00e7
            L_0x00e6:
                r4 = r1
            L_0x00e7:
                android.os.IBinder r5 = r9.readStrongBinder()
                com.android.ims.internal.IImsCallSessionListener r5 = com.android.ims.internal.IImsCallSessionListener.Stub.asInterface(r5)
                com.android.ims.internal.IImsCallSession r6 = r7.createCallSession(r3, r4, r5)
                r10.writeNoException()
                if (r6 == 0) goto L_0x00fd
                android.os.IBinder r1 = r6.asBinder()
            L_0x00fd:
                r10.writeStrongBinder(r1)
                return r2
            L_0x0101:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                android.telephony.ims.ImsCallProfile r5 = r7.createCallProfile(r1, r3, r4)
                r10.writeNoException()
                if (r5 == 0) goto L_0x0120
                r10.writeInt(r2)
                r5.writeToParcel(r10, r2)
                goto L_0x0124
            L_0x0120:
                r6 = 0
                r10.writeInt(r6)
            L_0x0124:
                return r2
            L_0x0125:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                android.os.IBinder r4 = r9.readStrongBinder()
                com.android.ims.internal.IImsRegistrationListener r4 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(r4)
                r7.addRegistrationListener(r1, r3, r4)
                r10.writeNoException()
                return r2
            L_0x013f:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsRegistrationListener r3 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(r3)
                r7.setRegistrationListener(r1, r3)
                r10.writeNoException()
                return r2
            L_0x0155:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                boolean r3 = r7.isOpened(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0167:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                boolean r5 = r7.isConnected(r1, r3, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x0181:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.close(r1)
                r10.writeNoException()
                return r2
            L_0x018f:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x01a9
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x01aa
            L_0x01a9:
            L_0x01aa:
                android.os.IBinder r5 = r9.readStrongBinder()
                com.android.ims.internal.IImsRegistrationListener r5 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(r5)
                int r6 = r7.open(r3, r4, r1, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                return r2
            L_0x01bd:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.IImsService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsService {
            public static IImsService sDefaultImpl;
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

            public int open(int phoneId, int serviceClass, PendingIntent incomingCallIntent, IImsRegistrationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(serviceClass);
                    if (incomingCallIntent != null) {
                        _data.writeInt(1);
                        incomingCallIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().open(phoneId, serviceClass, incomingCallIntent, listener);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void close(int serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().close(serviceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConnected(int serviceId, int serviceType, int callType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    _data.writeInt(serviceType);
                    _data.writeInt(callType);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConnected(serviceId, serviceType, callType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOpened(int serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOpened(serviceId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRegistrationListener(int serviceId, IImsRegistrationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRegistrationListener(serviceId, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addRegistrationListener(int phoneId, int serviceClass, IImsRegistrationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(serviceClass);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addRegistrationListener(phoneId, serviceClass, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ImsCallProfile createCallProfile(int serviceId, int serviceType, int callType) throws RemoteException {
                ImsCallProfile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    _data.writeInt(serviceType);
                    _data.writeInt(callType);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createCallProfile(serviceId, serviceType, callType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ImsCallProfile.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ImsCallProfile _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsCallSession createCallSession(int serviceId, ImsCallProfile profile, IImsCallSessionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createCallSession(serviceId, profile, listener);
                    }
                    _reply.readException();
                    IImsCallSession _result = IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsCallSession getPendingCallSession(int serviceId, String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    _data.writeString(callId);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPendingCallSession(serviceId, callId);
                    }
                    _reply.readException();
                    IImsCallSession _result = IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsUt getUtInterface(int serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUtInterface(serviceId);
                    }
                    _reply.readException();
                    IImsUt _result = IImsUt.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsConfig getConfigInterface(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfigInterface(phoneId);
                    }
                    _reply.readException();
                    IImsConfig _result = IImsConfig.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void turnOnIms(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().turnOnIms(phoneId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void turnOffIms(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().turnOffIms(phoneId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsEcbm getEcbmInterface(int serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEcbmInterface(serviceId);
                    }
                    _reply.readException();
                    IImsEcbm _result = IImsEcbm.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUiTTYMode(int serviceId, int uiTtyMode, Message onComplete) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    _data.writeInt(uiTtyMode);
                    if (onComplete != null) {
                        _data.writeInt(1);
                        onComplete.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUiTTYMode(serviceId, uiTtyMode, onComplete);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsMultiEndpoint getMultiEndpointInterface(int serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceId);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMultiEndpointInterface(serviceId);
                    }
                    _reply.readException();
                    IImsMultiEndpoint _result = IImsMultiEndpoint.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
