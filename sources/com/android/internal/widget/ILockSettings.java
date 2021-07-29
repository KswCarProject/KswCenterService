package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.trust.IStrongAuthTracker;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore.recovery.WrappedApplicationKey;
import java.util.List;
import java.util.Map;

public interface ILockSettings extends IInterface {
    VerifyCredentialResponse checkCredential(byte[] bArr, int i, int i2, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException;

    boolean checkVoldPassword(int i) throws RemoteException;

    void closeSession(String str) throws RemoteException;

    String generateKey(String str) throws RemoteException;

    String generateKeyWithMetadata(String str, byte[] bArr) throws RemoteException;

    @UnsupportedAppUsage
    boolean getBoolean(String str, boolean z, int i) throws RemoteException;

    byte[] getHashFactor(byte[] bArr, int i) throws RemoteException;

    String getKey(String str) throws RemoteException;

    KeyChainSnapshot getKeyChainSnapshot() throws RemoteException;

    @UnsupportedAppUsage
    long getLong(String str, long j, int i) throws RemoteException;

    String getPassword() throws RemoteException;

    int[] getRecoverySecretTypes() throws RemoteException;

    Map getRecoveryStatus() throws RemoteException;

    boolean getSeparateProfileChallengeEnabled(int i) throws RemoteException;

    @UnsupportedAppUsage
    String getString(String str, String str2, int i) throws RemoteException;

    int getStrongAuthForUser(int i) throws RemoteException;

    boolean hasPendingEscrowToken(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean havePassword(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean havePattern(int i) throws RemoteException;

    String importKey(String str, byte[] bArr) throws RemoteException;

    String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    Map recoverKeyChainSnapshot(String str, byte[] bArr, List<WrappedApplicationKey> list) throws RemoteException;

    void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException;

    void removeKey(String str) throws RemoteException;

    void requireStrongAuth(int i, int i2) throws RemoteException;

    void resetKeyStore(int i) throws RemoteException;

    void sanitizePassword() throws RemoteException;

    @UnsupportedAppUsage
    void setBoolean(String str, boolean z, int i) throws RemoteException;

    void setLockCredential(byte[] bArr, int i, byte[] bArr2, int i2, int i3, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setLong(String str, long j, int i) throws RemoteException;

    void setRecoverySecretTypes(int[] iArr) throws RemoteException;

    void setRecoveryStatus(String str, int i) throws RemoteException;

    void setSeparateProfileChallengeEnabled(int i, boolean z, byte[] bArr) throws RemoteException;

    void setServerParams(byte[] bArr) throws RemoteException;

    void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    @UnsupportedAppUsage
    void setString(String str, String str2, int i) throws RemoteException;

    byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List<KeyChainProtectionParams> list) throws RemoteException;

    void systemReady() throws RemoteException;

    void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException;

    void userPresent(int i) throws RemoteException;

    VerifyCredentialResponse verifyCredential(byte[] bArr, int i, long j, int i2) throws RemoteException;

    VerifyCredentialResponse verifyTiedProfileChallenge(byte[] bArr, int i, long j, int i2) throws RemoteException;

    public static class Default implements ILockSettings {
        public void setBoolean(String key, boolean value, int userId) throws RemoteException {
        }

        public void setLong(String key, long value, int userId) throws RemoteException {
        }

        public void setString(String key, String value, int userId) throws RemoteException {
        }

        public boolean getBoolean(String key, boolean defaultValue, int userId) throws RemoteException {
            return false;
        }

        public long getLong(String key, long defaultValue, int userId) throws RemoteException {
            return 0;
        }

        public String getString(String key, String defaultValue, int userId) throws RemoteException {
            return null;
        }

        public void setLockCredential(byte[] credential, int type, byte[] savedCredential, int requestedQuality, int userId, boolean allowUntrustedChange) throws RemoteException {
        }

        public void resetKeyStore(int userId) throws RemoteException {
        }

        public VerifyCredentialResponse checkCredential(byte[] credential, int type, int userId, ICheckCredentialProgressCallback progressCallback) throws RemoteException {
            return null;
        }

        public VerifyCredentialResponse verifyCredential(byte[] credential, int type, long challenge, int userId) throws RemoteException {
            return null;
        }

        public VerifyCredentialResponse verifyTiedProfileChallenge(byte[] credential, int type, long challenge, int userId) throws RemoteException {
            return null;
        }

        public boolean checkVoldPassword(int userId) throws RemoteException {
            return false;
        }

        public boolean havePattern(int userId) throws RemoteException {
            return false;
        }

        public boolean havePassword(int userId) throws RemoteException {
            return false;
        }

        public byte[] getHashFactor(byte[] currentCredential, int userId) throws RemoteException {
            return null;
        }

        public void setSeparateProfileChallengeEnabled(int userId, boolean enabled, byte[] managedUserPassword) throws RemoteException {
        }

        public boolean getSeparateProfileChallengeEnabled(int userId) throws RemoteException {
            return false;
        }

        public void registerStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
        }

        public void unregisterStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
        }

        public void requireStrongAuth(int strongAuthReason, int userId) throws RemoteException {
        }

        public void systemReady() throws RemoteException {
        }

        public void userPresent(int userId) throws RemoteException {
        }

        public int getStrongAuthForUser(int userId) throws RemoteException {
            return 0;
        }

        public boolean hasPendingEscrowToken(int userId) throws RemoteException {
            return false;
        }

        public void initRecoveryServiceWithSigFile(String rootCertificateAlias, byte[] recoveryServiceCertFile, byte[] recoveryServiceSigFile) throws RemoteException {
        }

        public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
            return null;
        }

        public String generateKey(String alias) throws RemoteException {
            return null;
        }

        public String generateKeyWithMetadata(String alias, byte[] metadata) throws RemoteException {
            return null;
        }

        public String importKey(String alias, byte[] keyBytes) throws RemoteException {
            return null;
        }

        public String importKeyWithMetadata(String alias, byte[] keyBytes, byte[] metadata) throws RemoteException {
            return null;
        }

        public String getKey(String alias) throws RemoteException {
            return null;
        }

        public void removeKey(String alias) throws RemoteException {
        }

        public void setSnapshotCreatedPendingIntent(PendingIntent intent) throws RemoteException {
        }

        public void setServerParams(byte[] serverParams) throws RemoteException {
        }

        public void setRecoveryStatus(String alias, int status) throws RemoteException {
        }

        public Map getRecoveryStatus() throws RemoteException {
            return null;
        }

        public void setRecoverySecretTypes(int[] secretTypes) throws RemoteException {
        }

        public int[] getRecoverySecretTypes() throws RemoteException {
            return null;
        }

        public byte[] startRecoverySessionWithCertPath(String sessionId, String rootCertificateAlias, RecoveryCertPath verifierCertPath, byte[] vaultParams, byte[] vaultChallenge, List<KeyChainProtectionParams> list) throws RemoteException {
            return null;
        }

        public Map recoverKeyChainSnapshot(String sessionId, byte[] recoveryKeyBlob, List<WrappedApplicationKey> list) throws RemoteException {
            return null;
        }

        public void closeSession(String sessionId) throws RemoteException {
        }

        public void sanitizePassword() throws RemoteException {
        }

        public String getPassword() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILockSettings {
        private static final String DESCRIPTOR = "com.android.internal.widget.ILockSettings";
        static final int TRANSACTION_checkCredential = 9;
        static final int TRANSACTION_checkVoldPassword = 12;
        static final int TRANSACTION_closeSession = 41;
        static final int TRANSACTION_generateKey = 27;
        static final int TRANSACTION_generateKeyWithMetadata = 28;
        static final int TRANSACTION_getBoolean = 4;
        static final int TRANSACTION_getHashFactor = 15;
        static final int TRANSACTION_getKey = 31;
        static final int TRANSACTION_getKeyChainSnapshot = 26;
        static final int TRANSACTION_getLong = 5;
        static final int TRANSACTION_getPassword = 43;
        static final int TRANSACTION_getRecoverySecretTypes = 38;
        static final int TRANSACTION_getRecoveryStatus = 36;
        static final int TRANSACTION_getSeparateProfileChallengeEnabled = 17;
        static final int TRANSACTION_getString = 6;
        static final int TRANSACTION_getStrongAuthForUser = 23;
        static final int TRANSACTION_hasPendingEscrowToken = 24;
        static final int TRANSACTION_havePassword = 14;
        static final int TRANSACTION_havePattern = 13;
        static final int TRANSACTION_importKey = 29;
        static final int TRANSACTION_importKeyWithMetadata = 30;
        static final int TRANSACTION_initRecoveryServiceWithSigFile = 25;
        static final int TRANSACTION_recoverKeyChainSnapshot = 40;
        static final int TRANSACTION_registerStrongAuthTracker = 18;
        static final int TRANSACTION_removeKey = 32;
        static final int TRANSACTION_requireStrongAuth = 20;
        static final int TRANSACTION_resetKeyStore = 8;
        static final int TRANSACTION_sanitizePassword = 42;
        static final int TRANSACTION_setBoolean = 1;
        static final int TRANSACTION_setLockCredential = 7;
        static final int TRANSACTION_setLong = 2;
        static final int TRANSACTION_setRecoverySecretTypes = 37;
        static final int TRANSACTION_setRecoveryStatus = 35;
        static final int TRANSACTION_setSeparateProfileChallengeEnabled = 16;
        static final int TRANSACTION_setServerParams = 34;
        static final int TRANSACTION_setSnapshotCreatedPendingIntent = 33;
        static final int TRANSACTION_setString = 3;
        static final int TRANSACTION_startRecoverySessionWithCertPath = 39;
        static final int TRANSACTION_systemReady = 21;
        static final int TRANSACTION_unregisterStrongAuthTracker = 19;
        static final int TRANSACTION_userPresent = 22;
        static final int TRANSACTION_verifyCredential = 10;
        static final int TRANSACTION_verifyTiedProfileChallenge = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILockSettings asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILockSettings)) {
                return new Proxy(obj);
            }
            return (ILockSettings) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setBoolean";
                case 2:
                    return "setLong";
                case 3:
                    return "setString";
                case 4:
                    return "getBoolean";
                case 5:
                    return "getLong";
                case 6:
                    return "getString";
                case 7:
                    return "setLockCredential";
                case 8:
                    return "resetKeyStore";
                case 9:
                    return "checkCredential";
                case 10:
                    return "verifyCredential";
                case 11:
                    return "verifyTiedProfileChallenge";
                case 12:
                    return "checkVoldPassword";
                case 13:
                    return "havePattern";
                case 14:
                    return "havePassword";
                case 15:
                    return "getHashFactor";
                case 16:
                    return "setSeparateProfileChallengeEnabled";
                case 17:
                    return "getSeparateProfileChallengeEnabled";
                case 18:
                    return "registerStrongAuthTracker";
                case 19:
                    return "unregisterStrongAuthTracker";
                case 20:
                    return "requireStrongAuth";
                case 21:
                    return "systemReady";
                case 22:
                    return "userPresent";
                case 23:
                    return "getStrongAuthForUser";
                case 24:
                    return "hasPendingEscrowToken";
                case 25:
                    return "initRecoveryServiceWithSigFile";
                case 26:
                    return "getKeyChainSnapshot";
                case 27:
                    return "generateKey";
                case 28:
                    return "generateKeyWithMetadata";
                case 29:
                    return "importKey";
                case 30:
                    return "importKeyWithMetadata";
                case 31:
                    return "getKey";
                case 32:
                    return "removeKey";
                case 33:
                    return "setSnapshotCreatedPendingIntent";
                case 34:
                    return "setServerParams";
                case 35:
                    return "setRecoveryStatus";
                case 36:
                    return "getRecoveryStatus";
                case 37:
                    return "setRecoverySecretTypes";
                case 38:
                    return "getRecoverySecretTypes";
                case 39:
                    return "startRecoverySessionWithCertPath";
                case 40:
                    return "recoverKeyChainSnapshot";
                case 41:
                    return "closeSession";
                case 42:
                    return "sanitizePassword";
                case 43:
                    return "getPassword";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v47, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.security.keystore.recovery.RecoveryCertPath} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v58, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v59, resolved type: android.app.PendingIntent} */
        /* JADX WARNING: type inference failed for: r0v53, types: [android.security.keystore.recovery.RecoveryCertPath] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "com.android.internal.widget.ILockSettings"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x03ec
                r0 = 0
                r6 = 0
                switch(r8) {
                    case 1: goto L_0x03d1;
                    case 2: goto L_0x03bb;
                    case 3: goto L_0x03a5;
                    case 4: goto L_0x0386;
                    case 5: goto L_0x036c;
                    case 6: goto L_0x0352;
                    case 7: goto L_0x0323;
                    case 8: goto L_0x0315;
                    case 9: goto L_0x02ea;
                    case 10: goto L_0x02bc;
                    case 11: goto L_0x028e;
                    case 12: goto L_0x027c;
                    case 13: goto L_0x026a;
                    case 14: goto L_0x0258;
                    case 15: goto L_0x0242;
                    case 16: goto L_0x0227;
                    case 17: goto L_0x0215;
                    case 18: goto L_0x0203;
                    case 19: goto L_0x01f1;
                    case 20: goto L_0x01df;
                    case 21: goto L_0x01d5;
                    case 22: goto L_0x01c7;
                    case 23: goto L_0x01b5;
                    case 24: goto L_0x01a3;
                    case 25: goto L_0x018d;
                    case 26: goto L_0x0176;
                    case 27: goto L_0x0164;
                    case 28: goto L_0x014e;
                    case 29: goto L_0x0138;
                    case 30: goto L_0x011e;
                    case 31: goto L_0x010c;
                    case 32: goto L_0x00fe;
                    case 33: goto L_0x00e4;
                    case 34: goto L_0x00d6;
                    case 35: goto L_0x00c4;
                    case 36: goto L_0x00b6;
                    case 37: goto L_0x00a8;
                    case 38: goto L_0x009a;
                    case 39: goto L_0x005c;
                    case 40: goto L_0x0040;
                    case 41: goto L_0x0032;
                    case 42: goto L_0x0028;
                    case 43: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r18.getPassword()
                r21.writeNoException()
                r10.writeString(r0)
                return r12
            L_0x0028:
                r9.enforceInterface(r11)
                r18.sanitizePassword()
                r21.writeNoException()
                return r12
            L_0x0032:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.closeSession(r0)
                r21.writeNoException()
                return r12
            L_0x0040:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                byte[] r1 = r20.createByteArray()
                android.os.Parcelable$Creator<android.security.keystore.recovery.WrappedApplicationKey> r2 = android.security.keystore.recovery.WrappedApplicationKey.CREATOR
                java.util.ArrayList r2 = r9.createTypedArrayList(r2)
                java.util.Map r3 = r7.recoverKeyChainSnapshot(r0, r1, r2)
                r21.writeNoException()
                r10.writeMap(r3)
                return r12
            L_0x005c:
                r9.enforceInterface(r11)
                java.lang.String r13 = r20.readString()
                java.lang.String r14 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0077
                android.os.Parcelable$Creator<android.security.keystore.recovery.RecoveryCertPath> r0 = android.security.keystore.recovery.RecoveryCertPath.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.security.keystore.recovery.RecoveryCertPath r0 = (android.security.keystore.recovery.RecoveryCertPath) r0
            L_0x0075:
                r3 = r0
                goto L_0x0078
            L_0x0077:
                goto L_0x0075
            L_0x0078:
                byte[] r15 = r20.createByteArray()
                byte[] r16 = r20.createByteArray()
                android.os.Parcelable$Creator<android.security.keystore.recovery.KeyChainProtectionParams> r0 = android.security.keystore.recovery.KeyChainProtectionParams.CREATOR
                java.util.ArrayList r17 = r9.createTypedArrayList(r0)
                r0 = r18
                r1 = r13
                r2 = r14
                r4 = r15
                r5 = r16
                r6 = r17
                byte[] r0 = r0.startRecoverySessionWithCertPath(r1, r2, r3, r4, r5, r6)
                r21.writeNoException()
                r10.writeByteArray(r0)
                return r12
            L_0x009a:
                r9.enforceInterface(r11)
                int[] r0 = r18.getRecoverySecretTypes()
                r21.writeNoException()
                r10.writeIntArray(r0)
                return r12
            L_0x00a8:
                r9.enforceInterface(r11)
                int[] r0 = r20.createIntArray()
                r7.setRecoverySecretTypes(r0)
                r21.writeNoException()
                return r12
            L_0x00b6:
                r9.enforceInterface(r11)
                java.util.Map r0 = r18.getRecoveryStatus()
                r21.writeNoException()
                r10.writeMap(r0)
                return r12
            L_0x00c4:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                r7.setRecoveryStatus(r0, r1)
                r21.writeNoException()
                return r12
            L_0x00d6:
                r9.enforceInterface(r11)
                byte[] r0 = r20.createByteArray()
                r7.setServerParams(r0)
                r21.writeNoException()
                return r12
            L_0x00e4:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x00f6
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                goto L_0x00f7
            L_0x00f6:
            L_0x00f7:
                r7.setSnapshotCreatedPendingIntent(r0)
                r21.writeNoException()
                return r12
            L_0x00fe:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.removeKey(r0)
                r21.writeNoException()
                return r12
            L_0x010c:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r7.getKey(r0)
                r21.writeNoException()
                r10.writeString(r1)
                return r12
            L_0x011e:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                byte[] r1 = r20.createByteArray()
                byte[] r2 = r20.createByteArray()
                java.lang.String r3 = r7.importKeyWithMetadata(r0, r1, r2)
                r21.writeNoException()
                r10.writeString(r3)
                return r12
            L_0x0138:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                byte[] r1 = r20.createByteArray()
                java.lang.String r2 = r7.importKey(r0, r1)
                r21.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x014e:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                byte[] r1 = r20.createByteArray()
                java.lang.String r2 = r7.generateKeyWithMetadata(r0, r1)
                r21.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x0164:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r7.generateKey(r0)
                r21.writeNoException()
                r10.writeString(r1)
                return r12
            L_0x0176:
                r9.enforceInterface(r11)
                android.security.keystore.recovery.KeyChainSnapshot r0 = r18.getKeyChainSnapshot()
                r21.writeNoException()
                if (r0 == 0) goto L_0x0189
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x018c
            L_0x0189:
                r10.writeInt(r6)
            L_0x018c:
                return r12
            L_0x018d:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                byte[] r1 = r20.createByteArray()
                byte[] r2 = r20.createByteArray()
                r7.initRecoveryServiceWithSigFile(r0, r1, r2)
                r21.writeNoException()
                return r12
            L_0x01a3:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                boolean r1 = r7.hasPendingEscrowToken(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x01b5:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r7.getStrongAuthForUser(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x01c7:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                r7.userPresent(r0)
                r21.writeNoException()
                return r12
            L_0x01d5:
                r9.enforceInterface(r11)
                r18.systemReady()
                r21.writeNoException()
                return r12
            L_0x01df:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                r7.requireStrongAuth(r0, r1)
                r21.writeNoException()
                return r12
            L_0x01f1:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.app.trust.IStrongAuthTracker r0 = android.app.trust.IStrongAuthTracker.Stub.asInterface(r0)
                r7.unregisterStrongAuthTracker(r0)
                r21.writeNoException()
                return r12
            L_0x0203:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.app.trust.IStrongAuthTracker r0 = android.app.trust.IStrongAuthTracker.Stub.asInterface(r0)
                r7.registerStrongAuthTracker(r0)
                r21.writeNoException()
                return r12
            L_0x0215:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                boolean r1 = r7.getSeparateProfileChallengeEnabled(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0227:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0236
                r6 = r12
            L_0x0236:
                r1 = r6
                byte[] r2 = r20.createByteArray()
                r7.setSeparateProfileChallengeEnabled(r0, r1, r2)
                r21.writeNoException()
                return r12
            L_0x0242:
                r9.enforceInterface(r11)
                byte[] r0 = r20.createByteArray()
                int r1 = r20.readInt()
                byte[] r2 = r7.getHashFactor(r0, r1)
                r21.writeNoException()
                r10.writeByteArray(r2)
                return r12
            L_0x0258:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                boolean r1 = r7.havePassword(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x026a:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                boolean r1 = r7.havePattern(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x027c:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                boolean r1 = r7.checkVoldPassword(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x028e:
                r9.enforceInterface(r11)
                byte[] r13 = r20.createByteArray()
                int r14 = r20.readInt()
                long r15 = r20.readLong()
                int r17 = r20.readInt()
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r5 = r17
                com.android.internal.widget.VerifyCredentialResponse r0 = r0.verifyTiedProfileChallenge(r1, r2, r3, r5)
                r21.writeNoException()
                if (r0 == 0) goto L_0x02b8
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x02bb
            L_0x02b8:
                r10.writeInt(r6)
            L_0x02bb:
                return r12
            L_0x02bc:
                r9.enforceInterface(r11)
                byte[] r13 = r20.createByteArray()
                int r14 = r20.readInt()
                long r15 = r20.readLong()
                int r17 = r20.readInt()
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r5 = r17
                com.android.internal.widget.VerifyCredentialResponse r0 = r0.verifyCredential(r1, r2, r3, r5)
                r21.writeNoException()
                if (r0 == 0) goto L_0x02e6
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x02e9
            L_0x02e6:
                r10.writeInt(r6)
            L_0x02e9:
                return r12
            L_0x02ea:
                r9.enforceInterface(r11)
                byte[] r0 = r20.createByteArray()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                com.android.internal.widget.ICheckCredentialProgressCallback r3 = com.android.internal.widget.ICheckCredentialProgressCallback.Stub.asInterface(r3)
                com.android.internal.widget.VerifyCredentialResponse r4 = r7.checkCredential(r0, r1, r2, r3)
                r21.writeNoException()
                if (r4 == 0) goto L_0x0311
                r10.writeInt(r12)
                r4.writeToParcel(r10, r12)
                goto L_0x0314
            L_0x0311:
                r10.writeInt(r6)
            L_0x0314:
                return r12
            L_0x0315:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                r7.resetKeyStore(r0)
                r21.writeNoException()
                return r12
            L_0x0323:
                r9.enforceInterface(r11)
                byte[] r13 = r20.createByteArray()
                int r14 = r20.readInt()
                byte[] r15 = r20.createByteArray()
                int r16 = r20.readInt()
                int r17 = r20.readInt()
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0342
                r6 = r12
            L_0x0342:
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r5 = r17
                r0.setLockCredential(r1, r2, r3, r4, r5, r6)
                r21.writeNoException()
                return r12
            L_0x0352:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                java.lang.String r3 = r7.getString(r0, r1, r2)
                r21.writeNoException()
                r10.writeString(r3)
                return r12
            L_0x036c:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                long r1 = r20.readLong()
                int r3 = r20.readInt()
                long r4 = r7.getLong(r0, r1, r3)
                r21.writeNoException()
                r10.writeLong(r4)
                return r12
            L_0x0386:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0395
                r6 = r12
            L_0x0395:
                r1 = r6
                int r2 = r20.readInt()
                boolean r3 = r7.getBoolean(r0, r1, r2)
                r21.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x03a5:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                java.lang.String r1 = r20.readString()
                int r2 = r20.readInt()
                r7.setString(r0, r1, r2)
                r21.writeNoException()
                return r12
            L_0x03bb:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                long r1 = r20.readLong()
                int r3 = r20.readInt()
                r7.setLong(r0, r1, r3)
                r21.writeNoException()
                return r12
            L_0x03d1:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x03e0
                r6 = r12
            L_0x03e0:
                r1 = r6
                int r2 = r20.readInt()
                r7.setBoolean(r0, r1, r2)
                r21.writeNoException()
                return r12
            L_0x03ec:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ILockSettings.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILockSettings {
            public static ILockSettings sDefaultImpl;
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

            public void setBoolean(String key, boolean value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(value);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBoolean(key, value, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setLong(String key, long value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeLong(value);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLong(key, value, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setString(String key, String value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(value);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setString(key, value, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getBoolean(String key, boolean defaultValue, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(defaultValue);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBoolean(key, defaultValue, userId);
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

            public long getLong(String key, long defaultValue, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeLong(defaultValue);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLong(key, defaultValue, userId);
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

            public String getString(String key, String defaultValue, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(defaultValue);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getString(key, defaultValue, userId);
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

            public void setLockCredential(byte[] credential, int type, byte[] savedCredential, int requestedQuality, int userId, boolean allowUntrustedChange) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeByteArray(credential);
                        try {
                            _data.writeInt(type);
                            try {
                                _data.writeByteArray(savedCredential);
                                try {
                                    _data.writeInt(requestedQuality);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = userId;
                                    boolean z = allowUntrustedChange;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i2 = requestedQuality;
                                int i3 = userId;
                                boolean z2 = allowUntrustedChange;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            byte[] bArr = savedCredential;
                            int i22 = requestedQuality;
                            int i32 = userId;
                            boolean z22 = allowUntrustedChange;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            try {
                                _data.writeInt(allowUntrustedChange ? 1 : 0);
                                if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().setLockCredential(credential, type, savedCredential, requestedQuality, userId, allowUntrustedChange);
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
                            boolean z222 = allowUntrustedChange;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        int i4 = type;
                        byte[] bArr2 = savedCredential;
                        int i222 = requestedQuality;
                        int i322 = userId;
                        boolean z2222 = allowUntrustedChange;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    byte[] bArr3 = credential;
                    int i42 = type;
                    byte[] bArr22 = savedCredential;
                    int i2222 = requestedQuality;
                    int i3222 = userId;
                    boolean z22222 = allowUntrustedChange;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void resetKeyStore(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetKeyStore(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VerifyCredentialResponse checkCredential(byte[] credential, int type, int userId, ICheckCredentialProgressCallback progressCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(credential);
                    _data.writeInt(type);
                    _data.writeInt(userId);
                    VerifyCredentialResponse _result = null;
                    _data.writeStrongBinder(progressCallback != null ? progressCallback.asBinder() : null);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkCredential(credential, type, userId, progressCallback);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VerifyCredentialResponse.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VerifyCredentialResponse verifyCredential(byte[] credential, int type, long challenge, int userId) throws RemoteException {
                VerifyCredentialResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(credential);
                    _data.writeInt(type);
                    _data.writeLong(challenge);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().verifyCredential(credential, type, challenge, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VerifyCredentialResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    VerifyCredentialResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VerifyCredentialResponse verifyTiedProfileChallenge(byte[] credential, int type, long challenge, int userId) throws RemoteException {
                VerifyCredentialResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(credential);
                    _data.writeInt(type);
                    _data.writeLong(challenge);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().verifyTiedProfileChallenge(credential, type, challenge, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VerifyCredentialResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    VerifyCredentialResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean checkVoldPassword(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkVoldPassword(userId);
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

            public boolean havePattern(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().havePattern(userId);
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

            public boolean havePassword(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().havePassword(userId);
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

            public byte[] getHashFactor(byte[] currentCredential, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(currentCredential);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHashFactor(currentCredential, userId);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSeparateProfileChallengeEnabled(int userId, boolean enabled, byte[] managedUserPassword) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(enabled);
                    _data.writeByteArray(managedUserPassword);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSeparateProfileChallengeEnabled(userId, enabled, managedUserPassword);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getSeparateProfileChallengeEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSeparateProfileChallengeEnabled(userId);
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

            public void registerStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tracker != null ? tracker.asBinder() : null);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerStrongAuthTracker(tracker);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(tracker != null ? tracker.asBinder() : null);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterStrongAuthTracker(tracker);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requireStrongAuth(int strongAuthReason, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strongAuthReason);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requireStrongAuth(strongAuthReason, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void systemReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().systemReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void userPresent(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().userPresent(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getStrongAuthForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStrongAuthForUser(userId);
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

            public boolean hasPendingEscrowToken(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasPendingEscrowToken(userId);
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

            public void initRecoveryServiceWithSigFile(String rootCertificateAlias, byte[] recoveryServiceCertFile, byte[] recoveryServiceSigFile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rootCertificateAlias);
                    _data.writeByteArray(recoveryServiceCertFile);
                    _data.writeByteArray(recoveryServiceSigFile);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().initRecoveryServiceWithSigFile(rootCertificateAlias, recoveryServiceCertFile, recoveryServiceSigFile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
                KeyChainSnapshot _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyChainSnapshot();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = KeyChainSnapshot.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    KeyChainSnapshot _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String generateKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().generateKey(alias);
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

            public String generateKeyWithMetadata(String alias, byte[] metadata) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(metadata);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().generateKeyWithMetadata(alias, metadata);
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

            public String importKey(String alias, byte[] keyBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(keyBytes);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().importKey(alias, keyBytes);
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

            public String importKeyWithMetadata(String alias, byte[] keyBytes, byte[] metadata) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(keyBytes);
                    _data.writeByteArray(metadata);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().importKeyWithMetadata(alias, keyBytes, metadata);
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

            public String getKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKey(alias);
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

            public void removeKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeKey(alias);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSnapshotCreatedPendingIntent(PendingIntent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSnapshotCreatedPendingIntent(intent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setServerParams(byte[] serverParams) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(serverParams);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setServerParams(serverParams);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRecoveryStatus(String alias, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(status);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRecoveryStatus(alias, status);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map getRecoveryStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRecoveryStatus();
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRecoverySecretTypes(int[] secretTypes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(secretTypes);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRecoverySecretTypes(secretTypes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getRecoverySecretTypes() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRecoverySecretTypes();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] startRecoverySessionWithCertPath(String sessionId, String rootCertificateAlias, RecoveryCertPath verifierCertPath, byte[] vaultParams, byte[] vaultChallenge, List<KeyChainProtectionParams> secrets) throws RemoteException {
                RecoveryCertPath recoveryCertPath = verifierCertPath;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(sessionId);
                        try {
                            _data.writeString(rootCertificateAlias);
                            if (recoveryCertPath != null) {
                                _data.writeInt(1);
                                recoveryCertPath.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            byte[] bArr = vaultParams;
                            byte[] bArr2 = vaultChallenge;
                            List<KeyChainProtectionParams> list = secrets;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = rootCertificateAlias;
                        byte[] bArr3 = vaultParams;
                        byte[] bArr22 = vaultChallenge;
                        List<KeyChainProtectionParams> list2 = secrets;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeByteArray(vaultParams);
                        try {
                            _data.writeByteArray(vaultChallenge);
                        } catch (Throwable th3) {
                            th = th3;
                            List<KeyChainProtectionParams> list22 = secrets;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeTypedList(secrets);
                            if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                byte[] _result = _reply.createByteArray();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            byte[] startRecoverySessionWithCertPath = Stub.getDefaultImpl().startRecoverySessionWithCertPath(sessionId, rootCertificateAlias, verifierCertPath, vaultParams, vaultChallenge, secrets);
                            _reply.recycle();
                            _data.recycle();
                            return startRecoverySessionWithCertPath;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        byte[] bArr222 = vaultChallenge;
                        List<KeyChainProtectionParams> list222 = secrets;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = sessionId;
                    String str3 = rootCertificateAlias;
                    byte[] bArr32 = vaultParams;
                    byte[] bArr2222 = vaultChallenge;
                    List<KeyChainProtectionParams> list2222 = secrets;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public Map recoverKeyChainSnapshot(String sessionId, byte[] recoveryKeyBlob, List<WrappedApplicationKey> applicationKeys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sessionId);
                    _data.writeByteArray(recoveryKeyBlob);
                    _data.writeTypedList(applicationKeys);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().recoverKeyChainSnapshot(sessionId, recoveryKeyBlob, applicationKeys);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeSession(String sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sessionId);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeSession(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sanitizePassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sanitizePassword();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPassword();
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
        }

        public static boolean setDefaultImpl(ILockSettings impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILockSettings getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
