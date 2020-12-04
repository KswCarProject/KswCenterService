package android.security.keystore;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Locale;
import libcore.util.EmptyArray;

public abstract class KeyProperties {
    public static final String BLOCK_MODE_CBC = "CBC";
    public static final String BLOCK_MODE_CTR = "CTR";
    public static final String BLOCK_MODE_ECB = "ECB";
    public static final String BLOCK_MODE_GCM = "GCM";
    public static final String DIGEST_MD5 = "MD5";
    public static final String DIGEST_NONE = "NONE";
    public static final String DIGEST_SHA1 = "SHA-1";
    public static final String DIGEST_SHA224 = "SHA-224";
    public static final String DIGEST_SHA256 = "SHA-256";
    public static final String DIGEST_SHA384 = "SHA-384";
    public static final String DIGEST_SHA512 = "SHA-512";
    public static final String ENCRYPTION_PADDING_NONE = "NoPadding";
    public static final String ENCRYPTION_PADDING_PKCS7 = "PKCS7Padding";
    public static final String ENCRYPTION_PADDING_RSA_OAEP = "OAEPPadding";
    public static final String ENCRYPTION_PADDING_RSA_PKCS1 = "PKCS1Padding";
    @Deprecated
    public static final String KEY_ALGORITHM_3DES = "DESede";
    public static final String KEY_ALGORITHM_AES = "AES";
    public static final String KEY_ALGORITHM_EC = "EC";
    public static final String KEY_ALGORITHM_HMAC_SHA1 = "HmacSHA1";
    public static final String KEY_ALGORITHM_HMAC_SHA224 = "HmacSHA224";
    public static final String KEY_ALGORITHM_HMAC_SHA256 = "HmacSHA256";
    public static final String KEY_ALGORITHM_HMAC_SHA384 = "HmacSHA384";
    public static final String KEY_ALGORITHM_HMAC_SHA512 = "HmacSHA512";
    public static final String KEY_ALGORITHM_RSA = "RSA";
    public static final int ORIGIN_GENERATED = 1;
    public static final int ORIGIN_IMPORTED = 2;
    public static final int ORIGIN_SECURELY_IMPORTED = 8;
    public static final int ORIGIN_UNKNOWN = 4;
    public static final int PURPOSE_DECRYPT = 2;
    public static final int PURPOSE_ENCRYPT = 1;
    public static final int PURPOSE_SIGN = 4;
    public static final int PURPOSE_VERIFY = 8;
    public static final int PURPOSE_WRAP_KEY = 32;
    public static final String SIGNATURE_PADDING_RSA_PKCS1 = "PKCS1";
    public static final String SIGNATURE_PADDING_RSA_PSS = "PSS";

    @Retention(RetentionPolicy.SOURCE)
    public @interface BlockModeEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DigestEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncryptionPaddingEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyAlgorithmEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OriginEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PurposeEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignaturePaddingEnum {
    }

    private KeyProperties() {
    }

    public static abstract class Purpose {
        private Purpose() {
        }

        public static int toKeymaster(int purpose) {
            if (purpose == 4) {
                return 2;
            }
            if (purpose == 8) {
                return 3;
            }
            if (purpose == 32) {
                return 5;
            }
            switch (purpose) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                default:
                    throw new IllegalArgumentException("Unknown purpose: " + purpose);
            }
        }

        public static int fromKeymaster(int purpose) {
            if (purpose == 5) {
                return 32;
            }
            switch (purpose) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 4;
                case 3:
                    return 8;
                default:
                    throw new IllegalArgumentException("Unknown purpose: " + purpose);
            }
        }

        public static int[] allToKeymaster(int purposes) {
            int[] result = KeyProperties.getSetFlags(purposes);
            for (int i = 0; i < result.length; i++) {
                result[i] = toKeymaster(result[i]);
            }
            return result;
        }

        public static int allFromKeymaster(Collection<Integer> purposes) {
            int result = 0;
            for (Integer intValue : purposes) {
                result |= fromKeymaster(intValue.intValue());
            }
            return result;
        }
    }

    public static abstract class KeyAlgorithm {
        private KeyAlgorithm() {
        }

        public static int toKeymasterAsymmetricKeyAlgorithm(String algorithm) {
            if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(algorithm)) {
                return 3;
            }
            if (KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(algorithm)) {
                return 1;
            }
            throw new IllegalArgumentException("Unsupported key algorithm: " + algorithm);
        }

        public static String fromKeymasterAsymmetricKeyAlgorithm(int keymasterAlgorithm) {
            if (keymasterAlgorithm == 1) {
                return KeyProperties.KEY_ALGORITHM_RSA;
            }
            if (keymasterAlgorithm == 3) {
                return KeyProperties.KEY_ALGORITHM_EC;
            }
            throw new IllegalArgumentException("Unsupported key algorithm: " + keymasterAlgorithm);
        }

        public static int toKeymasterSecretKeyAlgorithm(String algorithm) {
            if (KeyProperties.KEY_ALGORITHM_AES.equalsIgnoreCase(algorithm)) {
                return 32;
            }
            if (KeyProperties.KEY_ALGORITHM_3DES.equalsIgnoreCase(algorithm)) {
                return 33;
            }
            if (algorithm.toUpperCase(Locale.US).startsWith("HMAC")) {
                return 128;
            }
            throw new IllegalArgumentException("Unsupported secret key algorithm: " + algorithm);
        }

        public static String fromKeymasterSecretKeyAlgorithm(int keymasterAlgorithm, int keymasterDigest) {
            if (keymasterAlgorithm != 128) {
                switch (keymasterAlgorithm) {
                    case 32:
                        return KeyProperties.KEY_ALGORITHM_AES;
                    case 33:
                        return KeyProperties.KEY_ALGORITHM_3DES;
                    default:
                        throw new IllegalArgumentException("Unsupported key algorithm: " + keymasterAlgorithm);
                }
            } else {
                switch (keymasterDigest) {
                    case 2:
                        return KeyProperties.KEY_ALGORITHM_HMAC_SHA1;
                    case 3:
                        return KeyProperties.KEY_ALGORITHM_HMAC_SHA224;
                    case 4:
                        return KeyProperties.KEY_ALGORITHM_HMAC_SHA256;
                    case 5:
                        return KeyProperties.KEY_ALGORITHM_HMAC_SHA384;
                    case 6:
                        return KeyProperties.KEY_ALGORITHM_HMAC_SHA512;
                    default:
                        throw new IllegalArgumentException("Unsupported HMAC digest: " + Digest.fromKeymaster(keymasterDigest));
                }
            }
        }

        public static int toKeymasterDigest(String algorithm) {
            String algorithmUpper = algorithm.toUpperCase(Locale.US);
            char c = 65535;
            if (!algorithmUpper.startsWith("HMAC")) {
                return -1;
            }
            String digestUpper = algorithmUpper.substring("HMAC".length());
            switch (digestUpper.hashCode()) {
                case -1850268184:
                    if (digestUpper.equals("SHA224")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1850268089:
                    if (digestUpper.equals("SHA256")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1850267037:
                    if (digestUpper.equals("SHA384")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1850265334:
                    if (digestUpper.equals("SHA512")) {
                        c = 4;
                        break;
                    }
                    break;
                case 2543909:
                    if (digestUpper.equals("SHA1")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return 2;
                case 1:
                    return 3;
                case 2:
                    return 4;
                case 3:
                    return 5;
                case 4:
                    return 6;
                default:
                    throw new IllegalArgumentException("Unsupported HMAC digest: " + digestUpper);
            }
        }
    }

    public static abstract class BlockMode {
        private BlockMode() {
        }

        public static int toKeymaster(String blockMode) {
            if (KeyProperties.BLOCK_MODE_ECB.equalsIgnoreCase(blockMode)) {
                return 1;
            }
            if (KeyProperties.BLOCK_MODE_CBC.equalsIgnoreCase(blockMode)) {
                return 2;
            }
            if (KeyProperties.BLOCK_MODE_CTR.equalsIgnoreCase(blockMode)) {
                return 3;
            }
            if (KeyProperties.BLOCK_MODE_GCM.equalsIgnoreCase(blockMode)) {
                return 32;
            }
            throw new IllegalArgumentException("Unsupported block mode: " + blockMode);
        }

        public static String fromKeymaster(int blockMode) {
            if (blockMode == 32) {
                return KeyProperties.BLOCK_MODE_GCM;
            }
            switch (blockMode) {
                case 1:
                    return KeyProperties.BLOCK_MODE_ECB;
                case 2:
                    return KeyProperties.BLOCK_MODE_CBC;
                case 3:
                    return KeyProperties.BLOCK_MODE_CTR;
                default:
                    throw new IllegalArgumentException("Unsupported block mode: " + blockMode);
            }
        }

        public static String[] allFromKeymaster(Collection<Integer> blockModes) {
            if (blockModes == null || blockModes.isEmpty()) {
                return EmptyArray.STRING;
            }
            String[] result = new String[blockModes.size()];
            int offset = 0;
            for (Integer intValue : blockModes) {
                result[offset] = fromKeymaster(intValue.intValue());
                offset++;
            }
            return result;
        }

        public static int[] allToKeymaster(String[] blockModes) {
            if (blockModes == null || blockModes.length == 0) {
                return EmptyArray.INT;
            }
            int[] result = new int[blockModes.length];
            for (int i = 0; i < blockModes.length; i++) {
                result[i] = toKeymaster(blockModes[i]);
            }
            return result;
        }
    }

    public static abstract class EncryptionPadding {
        private EncryptionPadding() {
        }

        public static int toKeymaster(String padding) {
            if (KeyProperties.ENCRYPTION_PADDING_NONE.equalsIgnoreCase(padding)) {
                return 1;
            }
            if (KeyProperties.ENCRYPTION_PADDING_PKCS7.equalsIgnoreCase(padding)) {
                return 64;
            }
            if (KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1.equalsIgnoreCase(padding)) {
                return 4;
            }
            if (KeyProperties.ENCRYPTION_PADDING_RSA_OAEP.equalsIgnoreCase(padding)) {
                return 2;
            }
            throw new IllegalArgumentException("Unsupported encryption padding scheme: " + padding);
        }

        public static String fromKeymaster(int padding) {
            if (padding == 4) {
                return KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1;
            }
            if (padding == 64) {
                return KeyProperties.ENCRYPTION_PADDING_PKCS7;
            }
            switch (padding) {
                case 1:
                    return KeyProperties.ENCRYPTION_PADDING_NONE;
                case 2:
                    return KeyProperties.ENCRYPTION_PADDING_RSA_OAEP;
                default:
                    throw new IllegalArgumentException("Unsupported encryption padding: " + padding);
            }
        }

        public static int[] allToKeymaster(String[] paddings) {
            if (paddings == null || paddings.length == 0) {
                return EmptyArray.INT;
            }
            int[] result = new int[paddings.length];
            for (int i = 0; i < paddings.length; i++) {
                result[i] = toKeymaster(paddings[i]);
            }
            return result;
        }
    }

    static abstract class SignaturePadding {
        private SignaturePadding() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002d  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0044 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0046 A[RETURN] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static int toKeymaster(java.lang.String r3) {
            /*
                java.util.Locale r0 = java.util.Locale.US
                java.lang.String r0 = r3.toUpperCase(r0)
                int r1 = r0.hashCode()
                r2 = 79536(0x136b0, float:1.11454E-40)
                if (r1 == r2) goto L_0x001f
                r2 = 76183014(0x48a75e6, float:3.2551917E-36)
                if (r1 == r2) goto L_0x0015
                goto L_0x0029
            L_0x0015:
                java.lang.String r1 = "PKCS1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0029
                r0 = 0
                goto L_0x002a
            L_0x001f:
                java.lang.String r1 = "PSS"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0029
                r0 = 1
                goto L_0x002a
            L_0x0029:
                r0 = -1
            L_0x002a:
                switch(r0) {
                    case 0: goto L_0x0046;
                    case 1: goto L_0x0044;
                    default: goto L_0x002d;
                }
            L_0x002d:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Unsupported signature padding scheme: "
                r1.append(r2)
                r1.append(r3)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x0044:
                r0 = 3
                return r0
            L_0x0046:
                r0 = 5
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.KeyProperties.SignaturePadding.toKeymaster(java.lang.String):int");
        }

        static String fromKeymaster(int padding) {
            if (padding == 3) {
                return KeyProperties.SIGNATURE_PADDING_RSA_PSS;
            }
            if (padding == 5) {
                return KeyProperties.SIGNATURE_PADDING_RSA_PKCS1;
            }
            throw new IllegalArgumentException("Unsupported signature padding: " + padding);
        }

        static int[] allToKeymaster(String[] paddings) {
            if (paddings == null || paddings.length == 0) {
                return EmptyArray.INT;
            }
            int[] result = new int[paddings.length];
            for (int i = 0; i < paddings.length; i++) {
                result[i] = toKeymaster(paddings[i]);
            }
            return result;
        }
    }

    public static abstract class Digest {
        private Digest() {
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int toKeymaster(java.lang.String r9) {
            /*
                java.util.Locale r0 = java.util.Locale.US
                java.lang.String r0 = r9.toUpperCase(r0)
                int r1 = r0.hashCode()
                r2 = 1
                r3 = 0
                r4 = 6
                r5 = 5
                r6 = 4
                r7 = 3
                r8 = 2
                switch(r1) {
                    case -1523887821: goto L_0x0051;
                    case -1523887726: goto L_0x0047;
                    case -1523886674: goto L_0x003d;
                    case -1523884971: goto L_0x0033;
                    case 76158: goto L_0x0029;
                    case 2402104: goto L_0x001f;
                    case 78861104: goto L_0x0015;
                    default: goto L_0x0014;
                }
            L_0x0014:
                goto L_0x005b
            L_0x0015:
                java.lang.String r1 = "SHA-1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r3
                goto L_0x005c
            L_0x001f:
                java.lang.String r1 = "NONE"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r5
                goto L_0x005c
            L_0x0029:
                java.lang.String r1 = "MD5"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r4
                goto L_0x005c
            L_0x0033:
                java.lang.String r1 = "SHA-512"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r6
                goto L_0x005c
            L_0x003d:
                java.lang.String r1 = "SHA-384"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r7
                goto L_0x005c
            L_0x0047:
                java.lang.String r1 = "SHA-256"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r8
                goto L_0x005c
            L_0x0051:
                java.lang.String r1 = "SHA-224"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x005b
                r0 = r2
                goto L_0x005c
            L_0x005b:
                r0 = -1
            L_0x005c:
                switch(r0) {
                    case 0: goto L_0x007c;
                    case 1: goto L_0x007b;
                    case 2: goto L_0x007a;
                    case 3: goto L_0x0079;
                    case 4: goto L_0x0078;
                    case 5: goto L_0x0077;
                    case 6: goto L_0x0076;
                    default: goto L_0x005f;
                }
            L_0x005f:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Unsupported digest algorithm: "
                r1.append(r2)
                r1.append(r9)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x0076:
                return r2
            L_0x0077:
                return r3
            L_0x0078:
                return r4
            L_0x0079:
                return r5
            L_0x007a:
                return r6
            L_0x007b:
                return r7
            L_0x007c:
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.KeyProperties.Digest.toKeymaster(java.lang.String):int");
        }

        public static String fromKeymaster(int digest) {
            switch (digest) {
                case 0:
                    return KeyProperties.DIGEST_NONE;
                case 1:
                    return KeyProperties.DIGEST_MD5;
                case 2:
                    return KeyProperties.DIGEST_SHA1;
                case 3:
                    return KeyProperties.DIGEST_SHA224;
                case 4:
                    return KeyProperties.DIGEST_SHA256;
                case 5:
                    return KeyProperties.DIGEST_SHA384;
                case 6:
                    return KeyProperties.DIGEST_SHA512;
                default:
                    throw new IllegalArgumentException("Unsupported digest algorithm: " + digest);
            }
        }

        public static String fromKeymasterToSignatureAlgorithmDigest(int digest) {
            switch (digest) {
                case 0:
                    return KeyProperties.DIGEST_NONE;
                case 1:
                    return KeyProperties.DIGEST_MD5;
                case 2:
                    return "SHA1";
                case 3:
                    return "SHA224";
                case 4:
                    return "SHA256";
                case 5:
                    return "SHA384";
                case 6:
                    return "SHA512";
                default:
                    throw new IllegalArgumentException("Unsupported digest algorithm: " + digest);
            }
        }

        public static String[] allFromKeymaster(Collection<Integer> digests) {
            if (digests.isEmpty()) {
                return EmptyArray.STRING;
            }
            String[] result = new String[digests.size()];
            int offset = 0;
            for (Integer intValue : digests) {
                result[offset] = fromKeymaster(intValue.intValue());
                offset++;
            }
            return result;
        }

        public static int[] allToKeymaster(String[] digests) {
            if (digests == null || digests.length == 0) {
                return EmptyArray.INT;
            }
            int[] result = new int[digests.length];
            int offset = 0;
            for (String digest : digests) {
                result[offset] = toKeymaster(digest);
                offset++;
            }
            return result;
        }
    }

    public static abstract class Origin {
        private Origin() {
        }

        public static int fromKeymaster(int origin) {
            if (origin == 0) {
                return 1;
            }
            switch (origin) {
                case 2:
                    return 2;
                case 3:
                    return 4;
                case 4:
                    return 8;
                default:
                    throw new IllegalArgumentException("Unknown origin: " + origin);
            }
        }
    }

    /* access modifiers changed from: private */
    public static int[] getSetFlags(int flags) {
        if (flags == 0) {
            return EmptyArray.INT;
        }
        int[] result = new int[getSetBitCount(flags)];
        int resultOffset = 0;
        int flag = 1;
        while (flags != 0) {
            if ((flags & 1) != 0) {
                result[resultOffset] = flag;
                resultOffset++;
            }
            flags >>>= 1;
            flag <<= 1;
        }
        return result;
    }

    private static int getSetBitCount(int value) {
        int result = 0;
        if (value == 0) {
            return 0;
        }
        while (value != 0) {
            if ((value & 1) != 0) {
                result++;
            }
            value >>>= 1;
        }
        return result;
    }
}
