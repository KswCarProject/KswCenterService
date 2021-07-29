package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Telephony;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.aidl.IImsCallSessionListener;
import com.android.ims.internal.IImsVideoCallProvider;

public interface IImsCallSession extends IInterface {
    void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void close() throws RemoteException;

    void deflect(String str) throws RemoteException;

    void extendToConference(String[] strArr) throws RemoteException;

    String getCallId() throws RemoteException;

    ImsCallProfile getCallProfile() throws RemoteException;

    ImsCallProfile getLocalCallProfile() throws RemoteException;

    String getProperty(String str) throws RemoteException;

    ImsCallProfile getRemoteCallProfile() throws RemoteException;

    int getState() throws RemoteException;

    IImsVideoCallProvider getVideoCallProvider() throws RemoteException;

    void hold(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void inviteParticipants(String[] strArr) throws RemoteException;

    boolean isInCall() throws RemoteException;

    boolean isMultiparty() throws RemoteException;

    void merge() throws RemoteException;

    void reject(int i) throws RemoteException;

    void removeParticipants(String[] strArr) throws RemoteException;

    void resume(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void sendDtmf(char c, Message message) throws RemoteException;

    void sendRttMessage(String str) throws RemoteException;

    void sendRttModifyRequest(ImsCallProfile imsCallProfile) throws RemoteException;

    void sendRttModifyResponse(boolean z) throws RemoteException;

    void sendUssd(String str) throws RemoteException;

    void setListener(IImsCallSessionListener iImsCallSessionListener) throws RemoteException;

    void setMute(boolean z) throws RemoteException;

    void start(String str, ImsCallProfile imsCallProfile) throws RemoteException;

    void startConference(String[] strArr, ImsCallProfile imsCallProfile) throws RemoteException;

    void startDtmf(char c) throws RemoteException;

    void stopDtmf() throws RemoteException;

    void terminate(int i) throws RemoteException;

    void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    public static class Default implements IImsCallSession {
        public void close() throws RemoteException {
        }

        public String getCallId() throws RemoteException {
            return null;
        }

        public ImsCallProfile getCallProfile() throws RemoteException {
            return null;
        }

        public ImsCallProfile getLocalCallProfile() throws RemoteException {
            return null;
        }

        public ImsCallProfile getRemoteCallProfile() throws RemoteException {
            return null;
        }

        public String getProperty(String name) throws RemoteException {
            return null;
        }

        public int getState() throws RemoteException {
            return 0;
        }

        public boolean isInCall() throws RemoteException {
            return false;
        }

        public void setListener(IImsCallSessionListener listener) throws RemoteException {
        }

        public void setMute(boolean muted) throws RemoteException {
        }

        public void start(String callee, ImsCallProfile profile) throws RemoteException {
        }

        public void startConference(String[] participants, ImsCallProfile profile) throws RemoteException {
        }

        public void accept(int callType, ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void deflect(String deflectNumber) throws RemoteException {
        }

        public void reject(int reason) throws RemoteException {
        }

        public void terminate(int reason) throws RemoteException {
        }

        public void hold(ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void resume(ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void merge() throws RemoteException {
        }

        public void update(int callType, ImsStreamMediaProfile profile) throws RemoteException {
        }

        public void extendToConference(String[] participants) throws RemoteException {
        }

        public void inviteParticipants(String[] participants) throws RemoteException {
        }

        public void removeParticipants(String[] participants) throws RemoteException {
        }

        public void sendDtmf(char c, Message result) throws RemoteException {
        }

        public void startDtmf(char c) throws RemoteException {
        }

        public void stopDtmf() throws RemoteException {
        }

        public void sendUssd(String ussdMessage) throws RemoteException {
        }

        public IImsVideoCallProvider getVideoCallProvider() throws RemoteException {
            return null;
        }

        public boolean isMultiparty() throws RemoteException {
            return false;
        }

        public void sendRttModifyRequest(ImsCallProfile toProfile) throws RemoteException {
        }

        public void sendRttModifyResponse(boolean status) throws RemoteException {
        }

        public void sendRttMessage(String rttMessage) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsCallSession {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsCallSession";
        static final int TRANSACTION_accept = 13;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_deflect = 14;
        static final int TRANSACTION_extendToConference = 21;
        static final int TRANSACTION_getCallId = 2;
        static final int TRANSACTION_getCallProfile = 3;
        static final int TRANSACTION_getLocalCallProfile = 4;
        static final int TRANSACTION_getProperty = 6;
        static final int TRANSACTION_getRemoteCallProfile = 5;
        static final int TRANSACTION_getState = 7;
        static final int TRANSACTION_getVideoCallProvider = 28;
        static final int TRANSACTION_hold = 17;
        static final int TRANSACTION_inviteParticipants = 22;
        static final int TRANSACTION_isInCall = 8;
        static final int TRANSACTION_isMultiparty = 29;
        static final int TRANSACTION_merge = 19;
        static final int TRANSACTION_reject = 15;
        static final int TRANSACTION_removeParticipants = 23;
        static final int TRANSACTION_resume = 18;
        static final int TRANSACTION_sendDtmf = 24;
        static final int TRANSACTION_sendRttMessage = 32;
        static final int TRANSACTION_sendRttModifyRequest = 30;
        static final int TRANSACTION_sendRttModifyResponse = 31;
        static final int TRANSACTION_sendUssd = 27;
        static final int TRANSACTION_setListener = 9;
        static final int TRANSACTION_setMute = 10;
        static final int TRANSACTION_start = 11;
        static final int TRANSACTION_startConference = 12;
        static final int TRANSACTION_startDtmf = 25;
        static final int TRANSACTION_stopDtmf = 26;
        static final int TRANSACTION_terminate = 16;
        static final int TRANSACTION_update = 20;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsCallSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsCallSession)) {
                return new Proxy(obj);
            }
            return (IImsCallSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "close";
                case 2:
                    return "getCallId";
                case 3:
                    return "getCallProfile";
                case 4:
                    return "getLocalCallProfile";
                case 5:
                    return "getRemoteCallProfile";
                case 6:
                    return "getProperty";
                case 7:
                    return "getState";
                case 8:
                    return "isInCall";
                case 9:
                    return "setListener";
                case 10:
                    return "setMute";
                case 11:
                    return Telephony.BaseMmsColumns.START;
                case 12:
                    return "startConference";
                case 13:
                    return "accept";
                case 14:
                    return "deflect";
                case 15:
                    return "reject";
                case 16:
                    return "terminate";
                case 17:
                    return "hold";
                case 18:
                    return "resume";
                case 19:
                    return "merge";
                case 20:
                    return "update";
                case 21:
                    return "extendToConference";
                case 22:
                    return "inviteParticipants";
                case 23:
                    return "removeParticipants";
                case 24:
                    return "sendDtmf";
                case 25:
                    return "startDtmf";
                case 26:
                    return "stopDtmf";
                case 27:
                    return "sendUssd";
                case 28:
                    return "getVideoCallProvider";
                case 29:
                    return "isMultiparty";
                case 30:
                    return "sendRttModifyRequest";
                case 31:
                    return "sendRttModifyResponse";
                case 32:
                    return "sendRttMessage";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.telephony.ims.ImsCallProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: android.telephony.ims.ImsStreamMediaProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: android.telephony.ims.ImsStreamMediaProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: android.os.Message} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v18 */
        /* JADX WARNING: type inference failed for: r3v21 */
        /* JADX WARNING: type inference failed for: r3v32, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v43 */
        /* JADX WARNING: type inference failed for: r3v44 */
        /* JADX WARNING: type inference failed for: r3v45 */
        /* JADX WARNING: type inference failed for: r3v46 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "com.android.ims.internal.IImsCallSession"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0274
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x026a;
                    case 2: goto L_0x025c;
                    case 3: goto L_0x0245;
                    case 4: goto L_0x022e;
                    case 5: goto L_0x0217;
                    case 6: goto L_0x0205;
                    case 7: goto L_0x01f7;
                    case 8: goto L_0x01e9;
                    case 9: goto L_0x01d7;
                    case 10: goto L_0x01c5;
                    case 11: goto L_0x01a7;
                    case 12: goto L_0x0189;
                    case 13: goto L_0x016b;
                    case 14: goto L_0x015d;
                    case 15: goto L_0x014f;
                    case 16: goto L_0x0141;
                    case 17: goto L_0x0125;
                    case 18: goto L_0x0109;
                    case 19: goto L_0x00ff;
                    case 20: goto L_0x00e1;
                    case 21: goto L_0x00d3;
                    case 22: goto L_0x00c5;
                    case 23: goto L_0x00b7;
                    case 24: goto L_0x0098;
                    case 25: goto L_0x0089;
                    case 26: goto L_0x007f;
                    case 27: goto L_0x0071;
                    case 28: goto L_0x005c;
                    case 29: goto L_0x004e;
                    case 30: goto L_0x0032;
                    case 31: goto L_0x0020;
                    case 32: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                r5.sendRttMessage(r1)
                r8.writeNoException()
                return r2
            L_0x0020:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x002b
                r1 = r2
            L_0x002b:
                r5.sendRttModifyResponse(r1)
                r8.writeNoException()
                return r2
            L_0x0032:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0045
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r1 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.telephony.ims.ImsCallProfile r3 = (android.telephony.ims.ImsCallProfile) r3
                goto L_0x0046
            L_0x0045:
            L_0x0046:
                r1 = r3
                r5.sendRttModifyRequest(r1)
                r8.writeNoException()
                return r2
            L_0x004e:
                r7.enforceInterface(r0)
                boolean r1 = r5.isMultiparty()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x005c:
                r7.enforceInterface(r0)
                com.android.ims.internal.IImsVideoCallProvider r1 = r5.getVideoCallProvider()
                r8.writeNoException()
                if (r1 == 0) goto L_0x006d
                android.os.IBinder r3 = r1.asBinder()
            L_0x006d:
                r8.writeStrongBinder(r3)
                return r2
            L_0x0071:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                r5.sendUssd(r1)
                r8.writeNoException()
                return r2
            L_0x007f:
                r7.enforceInterface(r0)
                r5.stopDtmf()
                r8.writeNoException()
                return r2
            L_0x0089:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                char r1 = (char) r1
                r5.startDtmf(r1)
                r8.writeNoException()
                return r2
            L_0x0098:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                char r1 = (char) r1
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00af
                android.os.Parcelable$Creator<android.os.Message> r3 = android.os.Message.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.os.Message r3 = (android.os.Message) r3
                goto L_0x00b0
            L_0x00af:
            L_0x00b0:
                r5.sendDtmf(r1, r3)
                r8.writeNoException()
                return r2
            L_0x00b7:
                r7.enforceInterface(r0)
                java.lang.String[] r1 = r7.createStringArray()
                r5.removeParticipants(r1)
                r8.writeNoException()
                return r2
            L_0x00c5:
                r7.enforceInterface(r0)
                java.lang.String[] r1 = r7.createStringArray()
                r5.inviteParticipants(r1)
                r8.writeNoException()
                return r2
            L_0x00d3:
                r7.enforceInterface(r0)
                java.lang.String[] r1 = r7.createStringArray()
                r5.extendToConference(r1)
                r8.writeNoException()
                return r2
            L_0x00e1:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00f7
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r3 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telephony.ims.ImsStreamMediaProfile r3 = (android.telephony.ims.ImsStreamMediaProfile) r3
                goto L_0x00f8
            L_0x00f7:
            L_0x00f8:
                r5.update(r1, r3)
                r8.writeNoException()
                return r2
            L_0x00ff:
                r7.enforceInterface(r0)
                r5.merge()
                r8.writeNoException()
                return r2
            L_0x0109:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x011c
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r1 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.telephony.ims.ImsStreamMediaProfile r3 = (android.telephony.ims.ImsStreamMediaProfile) r3
                goto L_0x011d
            L_0x011c:
            L_0x011d:
                r1 = r3
                r5.resume(r1)
                r8.writeNoException()
                return r2
            L_0x0125:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0138
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r1 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.telephony.ims.ImsStreamMediaProfile r3 = (android.telephony.ims.ImsStreamMediaProfile) r3
                goto L_0x0139
            L_0x0138:
            L_0x0139:
                r1 = r3
                r5.hold(r1)
                r8.writeNoException()
                return r2
            L_0x0141:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.terminate(r1)
                r8.writeNoException()
                return r2
            L_0x014f:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.reject(r1)
                r8.writeNoException()
                return r2
            L_0x015d:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                r5.deflect(r1)
                r8.writeNoException()
                return r2
            L_0x016b:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0181
                android.os.Parcelable$Creator<android.telephony.ims.ImsStreamMediaProfile> r3 = android.telephony.ims.ImsStreamMediaProfile.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telephony.ims.ImsStreamMediaProfile r3 = (android.telephony.ims.ImsStreamMediaProfile) r3
                goto L_0x0182
            L_0x0181:
            L_0x0182:
                r5.accept(r1, r3)
                r8.writeNoException()
                return r2
            L_0x0189:
                r7.enforceInterface(r0)
                java.lang.String[] r1 = r7.createStringArray()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x019f
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r3 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telephony.ims.ImsCallProfile r3 = (android.telephony.ims.ImsCallProfile) r3
                goto L_0x01a0
            L_0x019f:
            L_0x01a0:
                r5.startConference(r1, r3)
                r8.writeNoException()
                return r2
            L_0x01a7:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x01bd
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r3 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telephony.ims.ImsCallProfile r3 = (android.telephony.ims.ImsCallProfile) r3
                goto L_0x01be
            L_0x01bd:
            L_0x01be:
                r5.start(r1, r3)
                r8.writeNoException()
                return r2
            L_0x01c5:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x01d0
                r1 = r2
            L_0x01d0:
                r5.setMute(r1)
                r8.writeNoException()
                return r2
            L_0x01d7:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.telephony.ims.aidl.IImsCallSessionListener r1 = android.telephony.ims.aidl.IImsCallSessionListener.Stub.asInterface(r1)
                r5.setListener(r1)
                r8.writeNoException()
                return r2
            L_0x01e9:
                r7.enforceInterface(r0)
                boolean r1 = r5.isInCall()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x01f7:
                r7.enforceInterface(r0)
                int r1 = r5.getState()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0205:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                java.lang.String r3 = r5.getProperty(r1)
                r8.writeNoException()
                r8.writeString(r3)
                return r2
            L_0x0217:
                r7.enforceInterface(r0)
                android.telephony.ims.ImsCallProfile r3 = r5.getRemoteCallProfile()
                r8.writeNoException()
                if (r3 == 0) goto L_0x022a
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x022d
            L_0x022a:
                r8.writeInt(r1)
            L_0x022d:
                return r2
            L_0x022e:
                r7.enforceInterface(r0)
                android.telephony.ims.ImsCallProfile r3 = r5.getLocalCallProfile()
                r8.writeNoException()
                if (r3 == 0) goto L_0x0241
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x0244
            L_0x0241:
                r8.writeInt(r1)
            L_0x0244:
                return r2
            L_0x0245:
                r7.enforceInterface(r0)
                android.telephony.ims.ImsCallProfile r3 = r5.getCallProfile()
                r8.writeNoException()
                if (r3 == 0) goto L_0x0258
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x025b
            L_0x0258:
                r8.writeInt(r1)
            L_0x025b:
                return r2
            L_0x025c:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getCallId()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x026a:
                r7.enforceInterface(r0)
                r5.close()
                r8.writeNoException()
                return r2
            L_0x0274:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.ims.internal.IImsCallSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsCallSession {
            public static IImsCallSession sDefaultImpl;
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

            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().close();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCallId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallId();
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

            public ImsCallProfile getCallProfile() throws RemoteException {
                ImsCallProfile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallProfile();
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

            public ImsCallProfile getLocalCallProfile() throws RemoteException {
                ImsCallProfile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLocalCallProfile();
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

            public ImsCallProfile getRemoteCallProfile() throws RemoteException {
                ImsCallProfile _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoteCallProfile();
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

            public String getProperty(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProperty(name);
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

            public int getState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getState();
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

            public boolean isInCall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInCall();
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

            public void setListener(IImsCallSessionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMute(boolean muted) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(muted);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMute(muted);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void start(String callee, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callee);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().start(callee, profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startConference(String[] participants, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startConference(participants, profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void accept(int callType, ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callType);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().accept(callType, profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deflect(String deflectNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deflectNumber);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deflect(deflectNumber);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reject(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reject(reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void terminate(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().terminate(reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void hold(ImsStreamMediaProfile profile) throws RemoteException {
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
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().hold(profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resume(ImsStreamMediaProfile profile) throws RemoteException {
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
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resume(profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void merge() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().merge();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void update(int callType, ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callType);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().update(callType, profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void extendToConference(String[] participants) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().extendToConference(participants);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void inviteParticipants(String[] participants) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().inviteParticipants(participants);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeParticipants(String[] participants) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeParticipants(participants);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendDtmf(char c, Message result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(c);
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendDtmf(c, result);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startDtmf(char c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(c);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startDtmf(c);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopDtmf() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopDtmf();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendUssd(String ussdMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ussdMessage);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendUssd(ussdMessage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsVideoCallProvider getVideoCallProvider() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVideoCallProvider();
                    }
                    _reply.readException();
                    IImsVideoCallProvider _result = IImsVideoCallProvider.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMultiparty() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMultiparty();
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

            public void sendRttModifyRequest(ImsCallProfile toProfile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (toProfile != null) {
                        _data.writeInt(1);
                        toProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendRttModifyRequest(toProfile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendRttModifyResponse(boolean status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendRttModifyResponse(status);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendRttMessage(String rttMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rttMessage);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendRttMessage(rttMessage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsCallSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsCallSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
