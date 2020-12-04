package android.hardware.face;

import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricServiceReceiverInternal;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IFaceService extends IInterface {
    void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback) throws RemoteException;

    void authenticate(IBinder iBinder, long j, int i, IFaceServiceReceiver iFaceServiceReceiver, int i2, String str) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str) throws RemoteException;

    void cancelAuthenticationFromService(IBinder iBinder, String str, int i, int i2, int i3, boolean z) throws RemoteException;

    void cancelEnrollment(IBinder iBinder) throws RemoteException;

    void enroll(IBinder iBinder, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver, String str, int[] iArr) throws RemoteException;

    void enumerate(IBinder iBinder, int i, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException;

    long generateChallenge(IBinder iBinder) throws RemoteException;

    long getAuthenticatorId(String str) throws RemoteException;

    List<Face> getEnrolledFaces(int i, String str) throws RemoteException;

    void getFeature(int i, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException;

    boolean hasEnrolledFaces(int i, String str) throws RemoteException;

    boolean isHardwareDetected(long j, String str) throws RemoteException;

    void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, String str, int i2, int i3, int i4, int i5) throws RemoteException;

    void remove(IBinder iBinder, int i, int i2, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException;

    void rename(int i, String str) throws RemoteException;

    void resetLockout(byte[] bArr) throws RemoteException;

    int revokeChallenge(IBinder iBinder) throws RemoteException;

    void setActiveUser(int i) throws RemoteException;

    void setFeature(int i, boolean z, byte[] bArr, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException;

    void startPreparedClient(int i) throws RemoteException;

    void userActivity() throws RemoteException;

    public static class Default implements IFaceService {
        public void authenticate(IBinder token, long sessionId, int userid, IFaceServiceReceiver receiver, int flags, String opPackageName) throws RemoteException {
        }

        public void prepareForAuthentication(boolean requireConfirmation, IBinder token, long sessionId, int userId, IBiometricServiceReceiverInternal wrapperReceiver, String opPackageName, int cookie, int callingUid, int callingPid, int callingUserId) throws RemoteException {
        }

        public void startPreparedClient(int cookie) throws RemoteException {
        }

        public void cancelAuthentication(IBinder token, String opPackageName) throws RemoteException {
        }

        public void cancelAuthenticationFromService(IBinder token, String opPackageName, int callingUid, int callingPid, int callingUserId, boolean fromClient) throws RemoteException {
        }

        public void enroll(IBinder token, byte[] cryptoToken, IFaceServiceReceiver receiver, String opPackageName, int[] disabledFeatures) throws RemoteException {
        }

        public void cancelEnrollment(IBinder token) throws RemoteException {
        }

        public void remove(IBinder token, int faceId, int userId, IFaceServiceReceiver receiver) throws RemoteException {
        }

        public void rename(int faceId, String name) throws RemoteException {
        }

        public List<Face> getEnrolledFaces(int userId, String opPackageName) throws RemoteException {
            return null;
        }

        public boolean isHardwareDetected(long deviceId, String opPackageName) throws RemoteException {
            return false;
        }

        public long generateChallenge(IBinder token) throws RemoteException {
            return 0;
        }

        public int revokeChallenge(IBinder token) throws RemoteException {
            return 0;
        }

        public boolean hasEnrolledFaces(int userId, String opPackageName) throws RemoteException {
            return false;
        }

        public long getAuthenticatorId(String opPackageName) throws RemoteException {
            return 0;
        }

        public void resetLockout(byte[] token) throws RemoteException {
        }

        public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback callback) throws RemoteException {
        }

        public void setActiveUser(int uid) throws RemoteException {
        }

        public void enumerate(IBinder token, int userId, IFaceServiceReceiver receiver) throws RemoteException {
        }

        public void setFeature(int feature, boolean enabled, byte[] token, IFaceServiceReceiver receiver) throws RemoteException {
        }

        public void getFeature(int feature, IFaceServiceReceiver receiver) throws RemoteException {
        }

        public void userActivity() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFaceService {
        private static final String DESCRIPTOR = "android.hardware.face.IFaceService";
        static final int TRANSACTION_addLockoutResetCallback = 17;
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_cancelAuthentication = 4;
        static final int TRANSACTION_cancelAuthenticationFromService = 5;
        static final int TRANSACTION_cancelEnrollment = 7;
        static final int TRANSACTION_enroll = 6;
        static final int TRANSACTION_enumerate = 19;
        static final int TRANSACTION_generateChallenge = 12;
        static final int TRANSACTION_getAuthenticatorId = 15;
        static final int TRANSACTION_getEnrolledFaces = 10;
        static final int TRANSACTION_getFeature = 21;
        static final int TRANSACTION_hasEnrolledFaces = 14;
        static final int TRANSACTION_isHardwareDetected = 11;
        static final int TRANSACTION_prepareForAuthentication = 2;
        static final int TRANSACTION_remove = 8;
        static final int TRANSACTION_rename = 9;
        static final int TRANSACTION_resetLockout = 16;
        static final int TRANSACTION_revokeChallenge = 13;
        static final int TRANSACTION_setActiveUser = 18;
        static final int TRANSACTION_setFeature = 20;
        static final int TRANSACTION_startPreparedClient = 3;
        static final int TRANSACTION_userActivity = 22;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFaceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFaceService)) {
                return new Proxy(obj);
            }
            return (IFaceService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "authenticate";
                case 2:
                    return "prepareForAuthentication";
                case 3:
                    return "startPreparedClient";
                case 4:
                    return "cancelAuthentication";
                case 5:
                    return "cancelAuthenticationFromService";
                case 6:
                    return "enroll";
                case 7:
                    return "cancelEnrollment";
                case 8:
                    return "remove";
                case 9:
                    return "rename";
                case 10:
                    return "getEnrolledFaces";
                case 11:
                    return "isHardwareDetected";
                case 12:
                    return "generateChallenge";
                case 13:
                    return "revokeChallenge";
                case 14:
                    return "hasEnrolledFaces";
                case 15:
                    return "getAuthenticatorId";
                case 16:
                    return "resetLockout";
                case 17:
                    return "addLockoutResetCallback";
                case 18:
                    return "setActiveUser";
                case 19:
                    return "enumerate";
                case 20:
                    return "setFeature";
                case 21:
                    return "getFeature";
                case 22:
                    return "userActivity";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        public boolean onTransact(int r28, android.os.Parcel r29, android.os.Parcel r30, int r31) throws android.os.RemoteException {
            /*
                r27 = this;
                r12 = r27
                r13 = r28
                r14 = r29
                r15 = r30
                java.lang.String r11 = "android.hardware.face.IFaceService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r16 = 1
                if (r13 == r0) goto L_0x0257
                r0 = 0
                switch(r13) {
                    case 1: goto L_0x0225;
                    case 2: goto L_0x01d4;
                    case 3: goto L_0x01c6;
                    case 4: goto L_0x01b4;
                    case 5: goto L_0x0184;
                    case 6: goto L_0x015b;
                    case 7: goto L_0x014d;
                    case 8: goto L_0x012f;
                    case 9: goto L_0x011d;
                    case 10: goto L_0x0107;
                    case 11: goto L_0x00f1;
                    case 12: goto L_0x00df;
                    case 13: goto L_0x00cd;
                    case 14: goto L_0x00b7;
                    case 15: goto L_0x00a5;
                    case 16: goto L_0x0097;
                    case 17: goto L_0x0085;
                    case 18: goto L_0x0077;
                    case 19: goto L_0x005d;
                    case 20: goto L_0x003a;
                    case 21: goto L_0x0024;
                    case 22: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r28, r29, r30, r31)
                return r0
            L_0x001a:
                r14.enforceInterface(r11)
                r27.userActivity()
                r30.writeNoException()
                return r16
            L_0x0024:
                r14.enforceInterface(r11)
                int r0 = r29.readInt()
                android.os.IBinder r1 = r29.readStrongBinder()
                android.hardware.face.IFaceServiceReceiver r1 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(r1)
                r12.getFeature(r0, r1)
                r30.writeNoException()
                return r16
            L_0x003a:
                r14.enforceInterface(r11)
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                if (r2 == 0) goto L_0x004a
                r0 = r16
            L_0x004a:
                byte[] r2 = r29.createByteArray()
                android.os.IBinder r3 = r29.readStrongBinder()
                android.hardware.face.IFaceServiceReceiver r3 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(r3)
                r12.setFeature(r1, r0, r2, r3)
                r30.writeNoException()
                return r16
            L_0x005d:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                android.os.IBinder r2 = r29.readStrongBinder()
                android.hardware.face.IFaceServiceReceiver r2 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(r2)
                r12.enumerate(r0, r1, r2)
                r30.writeNoException()
                return r16
            L_0x0077:
                r14.enforceInterface(r11)
                int r0 = r29.readInt()
                r12.setActiveUser(r0)
                r30.writeNoException()
                return r16
            L_0x0085:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                android.hardware.biometrics.IBiometricServiceLockoutResetCallback r0 = android.hardware.biometrics.IBiometricServiceLockoutResetCallback.Stub.asInterface(r0)
                r12.addLockoutResetCallback(r0)
                r30.writeNoException()
                return r16
            L_0x0097:
                r14.enforceInterface(r11)
                byte[] r0 = r29.createByteArray()
                r12.resetLockout(r0)
                r30.writeNoException()
                return r16
            L_0x00a5:
                r14.enforceInterface(r11)
                java.lang.String r0 = r29.readString()
                long r1 = r12.getAuthenticatorId(r0)
                r30.writeNoException()
                r15.writeLong(r1)
                return r16
            L_0x00b7:
                r14.enforceInterface(r11)
                int r0 = r29.readInt()
                java.lang.String r1 = r29.readString()
                boolean r2 = r12.hasEnrolledFaces(r0, r1)
                r30.writeNoException()
                r15.writeInt(r2)
                return r16
            L_0x00cd:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r12.revokeChallenge(r0)
                r30.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x00df:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                long r1 = r12.generateChallenge(r0)
                r30.writeNoException()
                r15.writeLong(r1)
                return r16
            L_0x00f1:
                r14.enforceInterface(r11)
                long r0 = r29.readLong()
                java.lang.String r2 = r29.readString()
                boolean r3 = r12.isHardwareDetected(r0, r2)
                r30.writeNoException()
                r15.writeInt(r3)
                return r16
            L_0x0107:
                r14.enforceInterface(r11)
                int r0 = r29.readInt()
                java.lang.String r1 = r29.readString()
                java.util.List r2 = r12.getEnrolledFaces(r0, r1)
                r30.writeNoException()
                r15.writeTypedList(r2)
                return r16
            L_0x011d:
                r14.enforceInterface(r11)
                int r0 = r29.readInt()
                java.lang.String r1 = r29.readString()
                r12.rename(r0, r1)
                r30.writeNoException()
                return r16
            L_0x012f:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                int r1 = r29.readInt()
                int r2 = r29.readInt()
                android.os.IBinder r3 = r29.readStrongBinder()
                android.hardware.face.IFaceServiceReceiver r3 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(r3)
                r12.remove(r0, r1, r2, r3)
                r30.writeNoException()
                return r16
            L_0x014d:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                r12.cancelEnrollment(r0)
                r30.writeNoException()
                return r16
            L_0x015b:
                r14.enforceInterface(r11)
                android.os.IBinder r6 = r29.readStrongBinder()
                byte[] r7 = r29.createByteArray()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.hardware.face.IFaceServiceReceiver r8 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(r0)
                java.lang.String r9 = r29.readString()
                int[] r10 = r29.createIntArray()
                r0 = r27
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                r0.enroll(r1, r2, r3, r4, r5)
                r30.writeNoException()
                return r16
            L_0x0184:
                r14.enforceInterface(r11)
                android.os.IBinder r7 = r29.readStrongBinder()
                java.lang.String r8 = r29.readString()
                int r9 = r29.readInt()
                int r10 = r29.readInt()
                int r17 = r29.readInt()
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x01a4
                r6 = r16
                goto L_0x01a5
            L_0x01a4:
                r6 = r0
            L_0x01a5:
                r0 = r27
                r1 = r7
                r2 = r8
                r3 = r9
                r4 = r10
                r5 = r17
                r0.cancelAuthenticationFromService(r1, r2, r3, r4, r5, r6)
                r30.writeNoException()
                return r16
            L_0x01b4:
                r14.enforceInterface(r11)
                android.os.IBinder r0 = r29.readStrongBinder()
                java.lang.String r1 = r29.readString()
                r12.cancelAuthentication(r0, r1)
                r30.writeNoException()
                return r16
            L_0x01c6:
                r14.enforceInterface(r11)
                int r0 = r29.readInt()
                r12.startPreparedClient(r0)
                r30.writeNoException()
                return r16
            L_0x01d4:
                r14.enforceInterface(r11)
                int r1 = r29.readInt()
                if (r1 == 0) goto L_0x01e0
                r1 = r16
                goto L_0x01e1
            L_0x01e0:
                r1 = r0
            L_0x01e1:
                android.os.IBinder r17 = r29.readStrongBinder()
                long r18 = r29.readLong()
                int r20 = r29.readInt()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.hardware.biometrics.IBiometricServiceReceiverInternal r21 = android.hardware.biometrics.IBiometricServiceReceiverInternal.Stub.asInterface(r0)
                java.lang.String r22 = r29.readString()
                int r23 = r29.readInt()
                int r24 = r29.readInt()
                int r25 = r29.readInt()
                int r26 = r29.readInt()
                r0 = r27
                r2 = r17
                r3 = r18
                r5 = r20
                r6 = r21
                r7 = r22
                r8 = r23
                r9 = r24
                r10 = r25
                r12 = r11
                r11 = r26
                r0.prepareForAuthentication(r1, r2, r3, r5, r6, r7, r8, r9, r10, r11)
                r30.writeNoException()
                return r16
            L_0x0225:
                r12 = r11
                r14.enforceInterface(r12)
                android.os.IBinder r8 = r29.readStrongBinder()
                long r9 = r29.readLong()
                int r11 = r29.readInt()
                android.os.IBinder r0 = r29.readStrongBinder()
                android.hardware.face.IFaceServiceReceiver r17 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(r0)
                int r18 = r29.readInt()
                java.lang.String r19 = r29.readString()
                r0 = r27
                r1 = r8
                r2 = r9
                r4 = r11
                r5 = r17
                r6 = r18
                r7 = r19
                r0.authenticate(r1, r2, r4, r5, r6, r7)
                r30.writeNoException()
                return r16
            L_0x0257:
                r12 = r11
                r15.writeString(r12)
                return r16
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.face.IFaceService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IFaceService {
            public static IFaceService sDefaultImpl;
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

            public void authenticate(IBinder token, long sessionId, int userid, IFaceServiceReceiver receiver, int flags, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(token);
                        try {
                            _data.writeLong(sessionId);
                        } catch (Throwable th) {
                            th = th;
                            int i = userid;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userid);
                            _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                            _data.writeInt(flags);
                            _data.writeString(opPackageName);
                            if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().authenticate(token, sessionId, userid, receiver, flags, opPackageName);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = sessionId;
                        int i2 = userid;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    IBinder iBinder = token;
                    long j2 = sessionId;
                    int i22 = userid;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void prepareForAuthentication(boolean requireConfirmation, IBinder token, long sessionId, int userId, IBiometricServiceReceiverInternal wrapperReceiver, String opPackageName, int cookie, int callingUid, int callingPid, int callingUserId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(requireConfirmation ? 1 : 0);
                        _data.writeStrongBinder(token);
                        _data.writeLong(sessionId);
                        _data.writeInt(userId);
                        _data.writeStrongBinder(wrapperReceiver != null ? wrapperReceiver.asBinder() : null);
                        _data.writeString(opPackageName);
                        _data.writeInt(cookie);
                        _data.writeInt(callingUid);
                        _data.writeInt(callingPid);
                        _data.writeInt(callingUserId);
                        if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().prepareForAuthentication(requireConfirmation, token, sessionId, userId, wrapperReceiver, opPackageName, cookie, callingUid, callingPid, callingUserId);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    boolean z = requireConfirmation;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void startPreparedClient(int cookie) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cookie);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startPreparedClient(cookie);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelAuthentication(IBinder token, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelAuthentication(token, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelAuthenticationFromService(IBinder token, String opPackageName, int callingUid, int callingPid, int callingUserId, boolean fromClient) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(token);
                        try {
                            _data.writeString(opPackageName);
                            try {
                                _data.writeInt(callingUid);
                                try {
                                    _data.writeInt(callingPid);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = callingUserId;
                                    boolean z = fromClient;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i2 = callingPid;
                                int i3 = callingUserId;
                                boolean z2 = fromClient;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i4 = callingUid;
                            int i22 = callingPid;
                            int i32 = callingUserId;
                            boolean z22 = fromClient;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(callingUserId);
                            try {
                                _data.writeInt(fromClient ? 1 : 0);
                                if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().cancelAuthenticationFromService(token, opPackageName, callingUid, callingPid, callingUserId, fromClient);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            boolean z222 = fromClient;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        String str = opPackageName;
                        int i42 = callingUid;
                        int i222 = callingPid;
                        int i322 = callingUserId;
                        boolean z2222 = fromClient;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    IBinder iBinder = token;
                    String str2 = opPackageName;
                    int i422 = callingUid;
                    int i2222 = callingPid;
                    int i3222 = callingUserId;
                    boolean z22222 = fromClient;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void enroll(IBinder token, byte[] cryptoToken, IFaceServiceReceiver receiver, String opPackageName, int[] disabledFeatures) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeByteArray(cryptoToken);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    _data.writeString(opPackageName);
                    _data.writeIntArray(disabledFeatures);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enroll(token, cryptoToken, receiver, opPackageName, disabledFeatures);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelEnrollment(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelEnrollment(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void remove(IBinder token, int faceId, int userId, IFaceServiceReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(faceId);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().remove(token, faceId, userId, receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void rename(int faceId, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(faceId);
                    _data.writeString(name);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().rename(faceId, name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<Face> getEnrolledFaces(int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnrolledFaces(userId, opPackageName);
                    }
                    _reply.readException();
                    List<Face> _result = _reply.createTypedArrayList(Face.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isHardwareDetected(long deviceId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeString(opPackageName);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHardwareDetected(deviceId, opPackageName);
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

            public long generateChallenge(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().generateChallenge(token);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int revokeChallenge(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().revokeChallenge(token);
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

            public boolean hasEnrolledFaces(int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    boolean z = false;
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasEnrolledFaces(userId, opPackageName);
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

            public long getAuthenticatorId(String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAuthenticatorId(opPackageName);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resetLockout(byte[] token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(token);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetLockout(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addLockoutResetCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setActiveUser(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setActiveUser(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enumerate(IBinder token, int userId, IFaceServiceReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enumerate(token, userId, receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFeature(int feature, boolean enabled, byte[] token, IFaceServiceReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(feature);
                    _data.writeInt(enabled);
                    _data.writeByteArray(token);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFeature(feature, enabled, token, receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getFeature(int feature, IFaceServiceReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(feature);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getFeature(feature, receiver);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void userActivity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().userActivity();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IFaceService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IFaceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
