package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.feature.CapabilityChangeRequest;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;

public interface IImsMmTelFeature extends IInterface {
    void acknowledgeSms(int i, int i2, int i3) throws RemoteException;

    void acknowledgeSmsReport(int i, int i2, int i3) throws RemoteException;

    void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    ImsCallProfile createCallProfile(int i, int i2) throws RemoteException;

    IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException;

    IImsEcbm getEcbmInterface() throws RemoteException;

    int getFeatureState() throws RemoteException;

    IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

    String getSmsFormat() throws RemoteException;

    IImsUt getUtInterface() throws RemoteException;

    void onSmsReady() throws RemoteException;

    void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    int queryCapabilityStatus() throws RemoteException;

    void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) throws RemoteException;

    void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException;

    void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException;

    void setUiTtyMode(int i, Message message) throws RemoteException;

    int shouldProcessCall(String[] strArr) throws RemoteException;

    public static class Default implements IImsMmTelFeature {
        public void setListener(IImsMmTelListener l) throws RemoteException {
        }

        public int getFeatureState() throws RemoteException {
            return 0;
        }

        public ImsCallProfile createCallProfile(int callSessionType, int callType) throws RemoteException {
            return null;
        }

        public IImsCallSession createCallSession(ImsCallProfile profile) throws RemoteException {
            return null;
        }

        public int shouldProcessCall(String[] uris) throws RemoteException {
            return 0;
        }

        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        public void setUiTtyMode(int uiTtyMode, Message onCompleteMessage) throws RemoteException {
        }

        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        public int queryCapabilityStatus() throws RemoteException {
            return 0;
        }

        public void addCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {
        }

        public void removeCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {
        }

        public void changeCapabilitiesConfiguration(CapabilityChangeRequest request, IImsCapabilityCallback c) throws RemoteException {
        }

        public void queryCapabilityConfiguration(int capability, int radioTech, IImsCapabilityCallback c) throws RemoteException {
        }

        public void setSmsListener(IImsSmsListener l) throws RemoteException {
        }

        public void sendSms(int token, int messageRef, String format, String smsc, boolean retry, byte[] pdu) throws RemoteException {
        }

        public void acknowledgeSms(int token, int messageRef, int result) throws RemoteException {
        }

        public void acknowledgeSmsReport(int token, int messageRef, int result) throws RemoteException {
        }

        public String getSmsFormat() throws RemoteException {
            return null;
        }

        public void onSmsReady() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsMmTelFeature {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelFeature";
        static final int TRANSACTION_acknowledgeSms = 17;
        static final int TRANSACTION_acknowledgeSmsReport = 18;
        static final int TRANSACTION_addCapabilityCallback = 11;
        static final int TRANSACTION_changeCapabilitiesConfiguration = 13;
        static final int TRANSACTION_createCallProfile = 3;
        static final int TRANSACTION_createCallSession = 4;
        static final int TRANSACTION_getEcbmInterface = 7;
        static final int TRANSACTION_getFeatureState = 2;
        static final int TRANSACTION_getMultiEndpointInterface = 9;
        static final int TRANSACTION_getSmsFormat = 19;
        static final int TRANSACTION_getUtInterface = 6;
        static final int TRANSACTION_onSmsReady = 20;
        static final int TRANSACTION_queryCapabilityConfiguration = 14;
        static final int TRANSACTION_queryCapabilityStatus = 10;
        static final int TRANSACTION_removeCapabilityCallback = 12;
        static final int TRANSACTION_sendSms = 16;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setSmsListener = 15;
        static final int TRANSACTION_setUiTtyMode = 8;
        static final int TRANSACTION_shouldProcessCall = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMmTelFeature asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsMmTelFeature)) {
                return new Proxy(obj);
            }
            return (IImsMmTelFeature) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setListener";
                case 2:
                    return "getFeatureState";
                case 3:
                    return "createCallProfile";
                case 4:
                    return "createCallSession";
                case 5:
                    return "shouldProcessCall";
                case 6:
                    return "getUtInterface";
                case 7:
                    return "getEcbmInterface";
                case 8:
                    return "setUiTtyMode";
                case 9:
                    return "getMultiEndpointInterface";
                case 10:
                    return "queryCapabilityStatus";
                case 11:
                    return "addCapabilityCallback";
                case 12:
                    return "removeCapabilityCallback";
                case 13:
                    return "changeCapabilitiesConfiguration";
                case 14:
                    return "queryCapabilityConfiguration";
                case 15:
                    return "setSmsListener";
                case 16:
                    return "sendSms";
                case 17:
                    return "acknowledgeSms";
                case 18:
                    return "acknowledgeSmsReport";
                case 19:
                    return "getSmsFormat";
                case 20:
                    return "onSmsReady";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.os.Message} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v2, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v7, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v13, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: type inference failed for: r1v26 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "android.telephony.ims.aidl.IImsMmTelFeature"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x01cb
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x01b9;
                    case 2: goto L_0x01ab;
                    case 3: goto L_0x018c;
                    case 4: goto L_0x0167;
                    case 5: goto L_0x0155;
                    case 6: goto L_0x0140;
                    case 7: goto L_0x012b;
                    case 8: goto L_0x010d;
                    case 9: goto L_0x00f8;
                    case 10: goto L_0x00ea;
                    case 11: goto L_0x00db;
                    case 12: goto L_0x00cc;
                    case 13: goto L_0x00ab;
                    case 14: goto L_0x0094;
                    case 15: goto L_0x0082;
                    case 16: goto L_0x0055;
                    case 17: goto L_0x0042;
                    case 18: goto L_0x002f;
                    case 19: goto L_0x0021;
                    case 20: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                r18.onSmsReady()
                return r12
            L_0x0021:
                r9.enforceInterface(r11)
                java.lang.String r0 = r18.getSmsFormat()
                r21.writeNoException()
                r10.writeString(r0)
                return r12
            L_0x002f:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                r7.acknowledgeSmsReport(r0, r1, r2)
                return r12
            L_0x0042:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                r7.acknowledgeSms(r0, r1, r2)
                return r12
            L_0x0055:
                r9.enforceInterface(r11)
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                java.lang.String r15 = r20.readString()
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0070
                r5 = r12
                goto L_0x0071
            L_0x0070:
                r5 = r0
            L_0x0071:
                byte[] r17 = r20.createByteArray()
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r6 = r17
                r0.sendSms(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x0082:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.telephony.ims.aidl.IImsSmsListener r0 = android.telephony.ims.aidl.IImsSmsListener.Stub.asInterface(r0)
                r7.setSmsListener(r0)
                r21.writeNoException()
                return r12
            L_0x0094:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                android.os.IBinder r2 = r20.readStrongBinder()
                android.telephony.ims.aidl.IImsCapabilityCallback r2 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(r2)
                r7.queryCapabilityConfiguration(r0, r1, r2)
                return r12
            L_0x00ab:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x00be
                android.os.Parcelable$Creator<android.telephony.ims.feature.CapabilityChangeRequest> r0 = android.telephony.ims.feature.CapabilityChangeRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r1 = r0
                android.telephony.ims.feature.CapabilityChangeRequest r1 = (android.telephony.ims.feature.CapabilityChangeRequest) r1
                goto L_0x00bf
            L_0x00be:
            L_0x00bf:
                r0 = r1
                android.os.IBinder r1 = r20.readStrongBinder()
                android.telephony.ims.aidl.IImsCapabilityCallback r1 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(r1)
                r7.changeCapabilitiesConfiguration(r0, r1)
                return r12
            L_0x00cc:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.telephony.ims.aidl.IImsCapabilityCallback r0 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(r0)
                r7.removeCapabilityCallback(r0)
                return r12
            L_0x00db:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.telephony.ims.aidl.IImsCapabilityCallback r0 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(r0)
                r7.addCapabilityCallback(r0)
                return r12
            L_0x00ea:
                r9.enforceInterface(r11)
                int r0 = r18.queryCapabilityStatus()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x00f8:
                r9.enforceInterface(r11)
                com.android.ims.internal.IImsMultiEndpoint r0 = r18.getMultiEndpointInterface()
                r21.writeNoException()
                if (r0 == 0) goto L_0x0109
                android.os.IBinder r1 = r0.asBinder()
            L_0x0109:
                r10.writeStrongBinder(r1)
                return r12
            L_0x010d:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0123
                android.os.Parcelable$Creator<android.os.Message> r1 = android.os.Message.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Message r1 = (android.os.Message) r1
                goto L_0x0124
            L_0x0123:
            L_0x0124:
                r7.setUiTtyMode(r0, r1)
                r21.writeNoException()
                return r12
            L_0x012b:
                r9.enforceInterface(r11)
                com.android.ims.internal.IImsEcbm r0 = r18.getEcbmInterface()
                r21.writeNoException()
                if (r0 == 0) goto L_0x013c
                android.os.IBinder r1 = r0.asBinder()
            L_0x013c:
                r10.writeStrongBinder(r1)
                return r12
            L_0x0140:
                r9.enforceInterface(r11)
                com.android.ims.internal.IImsUt r0 = r18.getUtInterface()
                r21.writeNoException()
                if (r0 == 0) goto L_0x0151
                android.os.IBinder r1 = r0.asBinder()
            L_0x0151:
                r10.writeStrongBinder(r1)
                return r12
            L_0x0155:
                r9.enforceInterface(r11)
                java.lang.String[] r0 = r20.createStringArray()
                int r1 = r7.shouldProcessCall(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0167:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0179
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r0 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.ImsCallProfile r0 = (android.telephony.ims.ImsCallProfile) r0
                goto L_0x017a
            L_0x0179:
                r0 = r1
            L_0x017a:
                com.android.ims.internal.IImsCallSession r2 = r7.createCallSession(r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x0188
                android.os.IBinder r1 = r2.asBinder()
            L_0x0188:
                r10.writeStrongBinder(r1)
                return r12
            L_0x018c:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.telephony.ims.ImsCallProfile r3 = r7.createCallProfile(r1, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x01a7
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x01aa
            L_0x01a7:
                r10.writeInt(r0)
            L_0x01aa:
                return r12
            L_0x01ab:
                r9.enforceInterface(r11)
                int r0 = r18.getFeatureState()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x01b9:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.telephony.ims.aidl.IImsMmTelListener r0 = android.telephony.ims.aidl.IImsMmTelListener.Stub.asInterface(r0)
                r7.setListener(r0)
                r21.writeNoException()
                return r12
            L_0x01cb:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.aidl.IImsMmTelFeature.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsMmTelFeature {
            public static IImsMmTelFeature sDefaultImpl;
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

            public void setListener(IImsMmTelListener l) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(l != null ? l.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setListener(l);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getFeatureState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFeatureState();
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

            public ImsCallProfile createCallProfile(int callSessionType, int callType) throws RemoteException {
                ImsCallProfile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callSessionType);
                    _data.writeInt(callType);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createCallProfile(callSessionType, callType);
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

            public IImsCallSession createCallSession(ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createCallSession(profile);
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

            public int shouldProcessCall(String[] uris) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(uris);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldProcessCall(uris);
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

            public IImsUt getUtInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public void setUiTtyMode(int uiTtyMode, Message onCompleteMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uiTtyMode);
                    if (onCompleteMessage != null) {
                        _data.writeInt(1);
                        onCompleteMessage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUiTtyMode(uiTtyMode, onCompleteMessage);
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
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            public int queryCapabilityStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryCapabilityStatus();
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

            public void addCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addCapabilityCallback(c);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeCapabilityCallback(c);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void changeCapabilitiesConfiguration(CapabilityChangeRequest request, IImsCapabilityCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().changeCapabilitiesConfiguration(request, c);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void queryCapabilityConfiguration(int capability, int radioTech, IImsCapabilityCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capability);
                    _data.writeInt(radioTech);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().queryCapabilityConfiguration(capability, radioTech, c);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSmsListener(IImsSmsListener l) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(l != null ? l.asBinder() : null);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSmsListener(l);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendSms(int token, int messageRef, String format, String smsc, boolean retry, byte[] pdu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(token);
                    } catch (Throwable th) {
                        th = th;
                        int i = messageRef;
                        String str = format;
                        String str2 = smsc;
                        boolean z = retry;
                        byte[] bArr = pdu;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(messageRef);
                        try {
                            _data.writeString(format);
                            try {
                                _data.writeString(smsc);
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z2 = retry;
                                byte[] bArr2 = pdu;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str22 = smsc;
                            boolean z22 = retry;
                            byte[] bArr22 = pdu;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str3 = format;
                        String str222 = smsc;
                        boolean z222 = retry;
                        byte[] bArr222 = pdu;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(retry ? 1 : 0);
                        try {
                            _data.writeByteArray(pdu);
                            try {
                                if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().sendSms(token, messageRef, format, smsc, retry, pdu);
                                _data.recycle();
                            } catch (Throwable th5) {
                                th = th5;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        byte[] bArr2222 = pdu;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    int i2 = token;
                    int i3 = messageRef;
                    String str32 = format;
                    String str2222 = smsc;
                    boolean z2222 = retry;
                    byte[] bArr22222 = pdu;
                    _data.recycle();
                    throw th;
                }
            }

            public void acknowledgeSms(int token, int messageRef, int result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeInt(messageRef);
                    _data.writeInt(result);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().acknowledgeSms(token, messageRef, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void acknowledgeSmsReport(int token, int messageRef, int result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeInt(messageRef);
                    _data.writeInt(result);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().acknowledgeSmsReport(token, messageRef, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public String getSmsFormat() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSmsFormat();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onSmsReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSmsReady();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsMmTelFeature impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsMmTelFeature getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
