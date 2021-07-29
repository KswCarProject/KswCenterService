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

public interface IImsMMTelFeature extends IInterface {
    void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException;

    IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile) throws RemoteException;

    void endSession(int i) throws RemoteException;

    IImsConfig getConfigInterface() throws RemoteException;

    IImsEcbm getEcbmInterface() throws RemoteException;

    int getFeatureStatus() throws RemoteException;

    IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

    IImsCallSession getPendingCallSession(int i, String str) throws RemoteException;

    IImsUt getUtInterface() throws RemoteException;

    boolean isConnected(int i, int i2) throws RemoteException;

    boolean isOpened() throws RemoteException;

    void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void setUiTTYMode(int i, Message message) throws RemoteException;

    int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void turnOffIms() throws RemoteException;

    void turnOnIms() throws RemoteException;

    public static class Default implements IImsMMTelFeature {
        public int startSession(PendingIntent incomingCallIntent, IImsRegistrationListener listener) throws RemoteException {
            return 0;
        }

        public void endSession(int sessionId) throws RemoteException {
        }

        public boolean isConnected(int callSessionType, int callType) throws RemoteException {
            return false;
        }

        public boolean isOpened() throws RemoteException {
            return false;
        }

        public int getFeatureStatus() throws RemoteException {
            return 0;
        }

        public void addRegistrationListener(IImsRegistrationListener listener) throws RemoteException {
        }

        public void removeRegistrationListener(IImsRegistrationListener listener) throws RemoteException {
        }

        public ImsCallProfile createCallProfile(int sessionId, int callSessionType, int callType) throws RemoteException {
            return null;
        }

        public IImsCallSession createCallSession(int sessionId, ImsCallProfile profile) throws RemoteException {
            return null;
        }

        public IImsCallSession getPendingCallSession(int sessionId, String callId) throws RemoteException {
            return null;
        }

        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        public IImsConfig getConfigInterface() throws RemoteException {
            return null;
        }

        public void turnOnIms() throws RemoteException {
        }

        public void turnOffIms() throws RemoteException {
        }

        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        public void setUiTTYMode(int uiTtyMode, Message onComplete) throws RemoteException {
        }

        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsMMTelFeature {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsMMTelFeature";
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_createCallProfile = 8;
        static final int TRANSACTION_createCallSession = 9;
        static final int TRANSACTION_endSession = 2;
        static final int TRANSACTION_getConfigInterface = 12;
        static final int TRANSACTION_getEcbmInterface = 15;
        static final int TRANSACTION_getFeatureStatus = 5;
        static final int TRANSACTION_getMultiEndpointInterface = 17;
        static final int TRANSACTION_getPendingCallSession = 10;
        static final int TRANSACTION_getUtInterface = 11;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_removeRegistrationListener = 7;
        static final int TRANSACTION_setUiTTYMode = 16;
        static final int TRANSACTION_startSession = 1;
        static final int TRANSACTION_turnOffIms = 14;
        static final int TRANSACTION_turnOnIms = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMMTelFeature asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsMMTelFeature)) {
                return new Proxy(obj);
            }
            return (IImsMMTelFeature) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startSession";
                case 2:
                    return "endSession";
                case 3:
                    return "isConnected";
                case 4:
                    return "isOpened";
                case 5:
                    return "getFeatureStatus";
                case 6:
                    return "addRegistrationListener";
                case 7:
                    return "removeRegistrationListener";
                case 8:
                    return "createCallProfile";
                case 9:
                    return "createCallSession";
                case 10:
                    return "getPendingCallSession";
                case 11:
                    return "getUtInterface";
                case 12:
                    return "getConfigInterface";
                case 13:
                    return "turnOnIms";
                case 14:
                    return "turnOffIms";
                case 15:
                    return "getEcbmInterface";
                case 16:
                    return "setUiTTYMode";
                case 17:
                    return "getMultiEndpointInterface";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: android.os.Message} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v15, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v17, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v19, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v21, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v23, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v29, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: type inference failed for: r1v34 */
        /* JADX WARNING: type inference failed for: r1v35 */
        /* JADX WARNING: type inference failed for: r1v36 */
        /* JADX WARNING: type inference failed for: r1v37 */
        /* JADX WARNING: type inference failed for: r1v38 */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.ims.internal.IImsMMTelFeature"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x018b
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0165;
                    case 2: goto L_0x0157;
                    case 3: goto L_0x0141;
                    case 4: goto L_0x0133;
                    case 5: goto L_0x0125;
                    case 6: goto L_0x0113;
                    case 7: goto L_0x0101;
                    case 8: goto L_0x00dd;
                    case 9: goto L_0x00b4;
                    case 10: goto L_0x0097;
                    case 11: goto L_0x0082;
                    case 12: goto L_0x006d;
                    case 13: goto L_0x0063;
                    case 14: goto L_0x0059;
                    case 15: goto L_0x0044;
                    case 16: goto L_0x0026;
                    case 17: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                com.android.ims.internal.IImsMultiEndpoint r3 = r7.getMultiEndpointInterface()
                r10.writeNoException()
                if (r3 == 0) goto L_0x0022
                android.os.IBinder r1 = r3.asBinder()
            L_0x0022:
                r10.writeStrongBinder(r1)
                return r2
            L_0x0026:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x003c
                android.os.Parcelable$Creator<android.os.Message> r1 = android.os.Message.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Message r1 = (android.os.Message) r1
                goto L_0x003d
            L_0x003c:
            L_0x003d:
                r7.setUiTTYMode(r3, r1)
                r10.writeNoException()
                return r2
            L_0x0044:
                r9.enforceInterface(r0)
                com.android.ims.internal.IImsEcbm r3 = r7.getEcbmInterface()
                r10.writeNoException()
                if (r3 == 0) goto L_0x0055
                android.os.IBinder r1 = r3.asBinder()
            L_0x0055:
                r10.writeStrongBinder(r1)
                return r2
            L_0x0059:
                r9.enforceInterface(r0)
                r7.turnOffIms()
                r10.writeNoException()
                return r2
            L_0x0063:
                r9.enforceInterface(r0)
                r7.turnOnIms()
                r10.writeNoException()
                return r2
            L_0x006d:
                r9.enforceInterface(r0)
                com.android.ims.internal.IImsConfig r3 = r7.getConfigInterface()
                r10.writeNoException()
                if (r3 == 0) goto L_0x007e
                android.os.IBinder r1 = r3.asBinder()
            L_0x007e:
                r10.writeStrongBinder(r1)
                return r2
            L_0x0082:
                r9.enforceInterface(r0)
                com.android.ims.internal.IImsUt r3 = r7.getUtInterface()
                r10.writeNoException()
                if (r3 == 0) goto L_0x0093
                android.os.IBinder r1 = r3.asBinder()
            L_0x0093:
                r10.writeStrongBinder(r1)
                return r2
            L_0x0097:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                java.lang.String r4 = r9.readString()
                com.android.ims.internal.IImsCallSession r5 = r7.getPendingCallSession(r3, r4)
                r10.writeNoException()
                if (r5 == 0) goto L_0x00b0
                android.os.IBinder r1 = r5.asBinder()
            L_0x00b0:
                r10.writeStrongBinder(r1)
                return r2
            L_0x00b4:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00ca
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r4 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r4 = (android.telephony.ims.ImsCallProfile) r4
                goto L_0x00cb
            L_0x00ca:
                r4 = r1
            L_0x00cb:
                com.android.ims.internal.IImsCallSession r5 = r7.createCallSession(r3, r4)
                r10.writeNoException()
                if (r5 == 0) goto L_0x00d9
                android.os.IBinder r1 = r5.asBinder()
            L_0x00d9:
                r10.writeStrongBinder(r1)
                return r2
            L_0x00dd:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                android.telephony.ims.ImsCallProfile r5 = r7.createCallProfile(r1, r3, r4)
                r10.writeNoException()
                if (r5 == 0) goto L_0x00fc
                r10.writeInt(r2)
                r5.writeToParcel(r10, r2)
                goto L_0x0100
            L_0x00fc:
                r6 = 0
                r10.writeInt(r6)
            L_0x0100:
                return r2
            L_0x0101:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsRegistrationListener r1 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(r1)
                r7.removeRegistrationListener(r1)
                r10.writeNoException()
                return r2
            L_0x0113:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.ims.internal.IImsRegistrationListener r1 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(r1)
                r7.addRegistrationListener(r1)
                r10.writeNoException()
                return r2
            L_0x0125:
                r9.enforceInterface(r0)
                int r1 = r7.getFeatureStatus()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0133:
                r9.enforceInterface(r0)
                boolean r1 = r7.isOpened()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0141:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                boolean r4 = r7.isConnected(r1, r3)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x0157:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.endSession(r1)
                r10.writeNoException()
                return r2
            L_0x0165:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0177
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0178
            L_0x0177:
            L_0x0178:
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.ims.internal.IImsRegistrationListener r3 = com.android.ims.internal.IImsRegistrationListener.Stub.asInterface(r3)
                int r4 = r7.startSession(r1, r3)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x018b:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.IImsMMTelFeature.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsMMTelFeature {
            public static IImsMMTelFeature sDefaultImpl;
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

            public int startSession(PendingIntent incomingCallIntent, IImsRegistrationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (incomingCallIntent != null) {
                        _data.writeInt(1);
                        incomingCallIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startSession(incomingCallIntent, listener);
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

            public void endSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().endSession(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConnected(int callSessionType, int callType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callSessionType);
                    _data.writeInt(callType);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConnected(callSessionType, callType);
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

            public boolean isOpened() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOpened();
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

            public int getFeatureStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFeatureStatus();
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

            public void addRegistrationListener(IImsRegistrationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addRegistrationListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeRegistrationListener(IImsRegistrationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeRegistrationListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ImsCallProfile createCallProfile(int sessionId, int callSessionType, int callType) throws RemoteException {
                ImsCallProfile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(callSessionType);
                    _data.writeInt(callType);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createCallProfile(sessionId, callSessionType, callType);
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

            public IImsCallSession createCallSession(int sessionId, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createCallSession(sessionId, profile);
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

            public IImsCallSession getPendingCallSession(int sessionId, String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeString(callId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPendingCallSession(sessionId, callId);
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

            public IImsUt getUtInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUtInterface();
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

            public IImsConfig getConfigInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfigInterface();
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

            public void turnOnIms() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().turnOnIms();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void turnOffIms() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().turnOffIms();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEcbmInterface();
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

            public void setUiTTYMode(int uiTtyMode, Message onComplete) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uiTtyMode);
                    if (onComplete != null) {
                        _data.writeInt(1);
                        onComplete.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUiTTYMode(uiTtyMode, onComplete);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMultiEndpointInterface();
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

        public static boolean setDefaultImpl(IImsMMTelFeature impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsMMTelFeature getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
