package android.security.keystore;

import android.security.GateKeeper;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.KeyProperties;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.ArrayUtils;
import java.security.ProviderException;

public abstract class KeymasterUtils {
    private KeymasterUtils() {
    }

    public static int getDigestOutputSizeBits(int keymasterDigest) {
        switch (keymasterDigest) {
            case 0:
                return -1;
            case 1:
                return 128;
            case 2:
                return 160;
            case 3:
                return 224;
            case 4:
                return 256;
            case 5:
                return MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION;
            case 6:
                return 512;
            default:
                throw new IllegalArgumentException("Unknown digest: " + keymasterDigest);
        }
    }

    public static boolean isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(int keymasterBlockMode) {
        if (keymasterBlockMode == 32) {
            return true;
        }
        switch (keymasterBlockMode) {
            case 1:
                return false;
            case 2:
            case 3:
                return true;
            default:
                throw new IllegalArgumentException("Unsupported block mode: " + keymasterBlockMode);
        }
    }

    public static boolean isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(int keymasterPadding) {
        if (keymasterPadding == 4) {
            return true;
        }
        switch (keymasterPadding) {
            case 1:
                return false;
            case 2:
                return true;
            default:
                throw new IllegalArgumentException("Unsupported asymmetric encryption padding scheme: " + keymasterPadding);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: android.hardware.face.FaceManager} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.hardware.fingerprint.FingerprintManager} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void addUserAuthArgs(android.security.keymaster.KeymasterArguments r14, android.security.keystore.UserAuthArgs r15) {
        /*
            boolean r0 = r15.isUserConfirmationRequired()
            if (r0 == 0) goto L_0x000c
            r0 = 1879048700(0x700001fc, float:1.5846592E29)
            r14.addBoolean(r0)
        L_0x000c:
            boolean r0 = r15.isUserPresenceRequired()
            if (r0 == 0) goto L_0x0018
            r0 = 1879048699(0x700001fb, float:1.584659E29)
            r14.addBoolean(r0)
        L_0x0018:
            boolean r0 = r15.isUnlockedDeviceRequired()
            if (r0 == 0) goto L_0x0024
            r0 = 1879048701(0x700001fd, float:1.5846594E29)
            r14.addBoolean(r0)
        L_0x0024:
            boolean r0 = r15.isUserAuthenticationRequired()
            if (r0 != 0) goto L_0x0031
            r0 = 1879048695(0x700001f7, float:1.5846583E29)
            r14.addBoolean(r0)
            return
        L_0x0031:
            int r0 = r15.getUserAuthenticationValidityDurationSeconds()
            r1 = -1
            r2 = 268435960(0x100001f8, float:2.5245066E-29)
            r3 = -1610612234(0xffffffffa00001f6, float:-1.084267E-19)
            r4 = 0
            if (r0 != r1) goto L_0x00fd
            android.content.Context r0 = android.security.KeyStore.getApplicationContext()
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            r1 = 0
            r6 = 0
            java.lang.String r7 = "android.hardware.fingerprint"
            boolean r7 = r0.hasSystemFeature(r7)
            if (r7 == 0) goto L_0x005f
            android.content.Context r7 = android.security.KeyStore.getApplicationContext()
            java.lang.Class<android.hardware.fingerprint.FingerprintManager> r8 = android.hardware.fingerprint.FingerprintManager.class
            java.lang.Object r7 = r7.getSystemService(r8)
            r1 = r7
            android.hardware.fingerprint.FingerprintManager r1 = (android.hardware.fingerprint.FingerprintManager) r1
        L_0x005f:
            java.lang.String r7 = "android.hardware.biometrics.face"
            boolean r7 = r0.hasSystemFeature(r7)
            if (r7 == 0) goto L_0x0074
            android.content.Context r7 = android.security.KeyStore.getApplicationContext()
            java.lang.Class<android.hardware.face.FaceManager> r8 = android.hardware.face.FaceManager.class
            java.lang.Object r7 = r7.getSystemService(r8)
            r6 = r7
            android.hardware.face.FaceManager r6 = (android.hardware.face.FaceManager) r6
        L_0x0074:
            if (r1 == 0) goto L_0x007b
            long r7 = r1.getAuthenticatorId()
            goto L_0x007c
        L_0x007b:
            r7 = r4
        L_0x007c:
            if (r6 == 0) goto L_0x0083
            long r9 = r6.getAuthenticatorId()
            goto L_0x0084
        L_0x0083:
            r9 = r4
        L_0x0084:
            int r11 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r11 != 0) goto L_0x0095
            int r11 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x008d
            goto L_0x0095
        L_0x008d:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "At least one biometric must be enrolled to create keys requiring user authentication for every use"
            r2.<init>(r3)
            throw r2
        L_0x0095:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            long r12 = r15.getBoundToSpecificSecureUserId()
            int r4 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x00ae
            long r4 = r15.getBoundToSpecificSecureUserId()
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r11.add(r4)
            goto L_0x00ce
        L_0x00ae:
            boolean r4 = r15.isInvalidatedByBiometricEnrollment()
            if (r4 == 0) goto L_0x00c3
            java.lang.Long r4 = java.lang.Long.valueOf(r7)
            r11.add(r4)
            java.lang.Long r4 = java.lang.Long.valueOf(r9)
            r11.add(r4)
            goto L_0x00ce
        L_0x00c3:
            long r4 = getRootSid()
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r11.add(r4)
        L_0x00ce:
            r4 = 0
        L_0x00cf:
            int r5 = r11.size()
            if (r4 >= r5) goto L_0x00ea
            java.lang.Object r5 = r11.get(r4)
            java.lang.Long r5 = (java.lang.Long) r5
            long r12 = r5.longValue()
            java.math.BigInteger r5 = android.security.keymaster.KeymasterArguments.toUint64(r12)
            r14.addUnsignedLong(r3, r5)
            int r4 = r4 + 1
            goto L_0x00cf
        L_0x00ea:
            r3 = 2
            r14.addEnum(r2, r3)
            boolean r2 = r15.isUserAuthenticationValidWhileOnBody()
            if (r2 != 0) goto L_0x00f5
            goto L_0x0131
        L_0x00f5:
            java.security.ProviderException r2 = new java.security.ProviderException
            java.lang.String r3 = "Key validity extension while device is on-body is not supported for keys requiring fingerprint authentication"
            r2.<init>(r3)
            throw r2
        L_0x00fd:
            long r0 = r15.getBoundToSpecificSecureUserId()
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x010a
            long r0 = r15.getBoundToSpecificSecureUserId()
            goto L_0x010e
        L_0x010a:
            long r0 = getRootSid()
        L_0x010e:
            java.math.BigInteger r4 = android.security.keymaster.KeymasterArguments.toUint64(r0)
            r14.addUnsignedLong(r3, r4)
            r3 = 3
            r14.addEnum(r2, r3)
            r2 = 805306873(0x300001f9, float:4.656893E-10)
            int r3 = r15.getUserAuthenticationValidityDurationSeconds()
            long r3 = (long) r3
            r14.addUnsignedInt(r2, r3)
            boolean r2 = r15.isUserAuthenticationValidWhileOnBody()
            if (r2 == 0) goto L_0x0131
            r2 = 1879048698(0x700001fa, float:1.5846588E29)
            r14.addBoolean(r2)
        L_0x0131:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.KeymasterUtils.addUserAuthArgs(android.security.keymaster.KeymasterArguments, android.security.keystore.UserAuthArgs):void");
    }

    public static void addMinMacLengthAuthorizationIfNecessary(KeymasterArguments args, int keymasterAlgorithm, int[] keymasterBlockModes, int[] keymasterDigests) {
        if (keymasterAlgorithm != 32) {
            if (keymasterAlgorithm == 128) {
                if (keymasterDigests.length == 1) {
                    int keymasterDigest = keymasterDigests[0];
                    int digestOutputSizeBits = getDigestOutputSizeBits(keymasterDigest);
                    if (digestOutputSizeBits != -1) {
                        args.addUnsignedInt(KeymasterDefs.KM_TAG_MIN_MAC_LENGTH, (long) digestOutputSizeBits);
                        return;
                    }
                    throw new ProviderException("HMAC key authorized for unsupported digest: " + KeyProperties.Digest.fromKeymaster(keymasterDigest));
                }
                throw new ProviderException("Unsupported number of authorized digests for HMAC key: " + keymasterDigests.length + ". Exactly one digest must be authorized");
            }
        } else if (ArrayUtils.contains(keymasterBlockModes, 32)) {
            args.addUnsignedInt(KeymasterDefs.KM_TAG_MIN_MAC_LENGTH, 96);
        }
    }

    private static long getRootSid() {
        long rootSid = GateKeeper.getSecureUserId();
        if (rootSid != 0) {
            return rootSid;
        }
        throw new IllegalStateException("Secure lock screen must be enabled to create keys requiring user authentication");
    }
}
