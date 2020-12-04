package android.security.keystore;

import android.security.Credentials;
import android.security.KeyPairGeneratorSpec;
import android.security.KeyStore;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.KeyProperties;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.ArrayUtils;
import com.android.org.bouncycastle.x509.X509V3CertificateGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class AndroidKeyStoreKeyPairGeneratorSpi extends KeyPairGeneratorSpi {
    private static final int EC_DEFAULT_KEY_SIZE = 256;
    private static final int RSA_DEFAULT_KEY_SIZE = 2048;
    private static final int RSA_MAX_KEY_SIZE = 8192;
    private static final int RSA_MIN_KEY_SIZE = 512;
    private static final List<String> SUPPORTED_EC_NIST_CURVE_NAMES = new ArrayList();
    private static final Map<String, Integer> SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE = new HashMap();
    private static final List<Integer> SUPPORTED_EC_NIST_CURVE_SIZES = new ArrayList();
    private boolean mEncryptionAtRestRequired;
    private String mEntryAlias;
    private int mEntryUid;
    private String mJcaKeyAlgorithm;
    private int mKeySizeBits;
    private KeyStore mKeyStore;
    private int mKeymasterAlgorithm = -1;
    private int[] mKeymasterBlockModes;
    private int[] mKeymasterDigests;
    private int[] mKeymasterEncryptionPaddings;
    private int[] mKeymasterPurposes;
    private int[] mKeymasterSignaturePaddings;
    private final int mOriginalKeymasterAlgorithm;
    private BigInteger mRSAPublicExponent;
    private SecureRandom mRng;
    private KeyGenParameterSpec mSpec;

    public static class RSA extends AndroidKeyStoreKeyPairGeneratorSpi {
        public RSA() {
            super(1);
        }
    }

    public static class EC extends AndroidKeyStoreKeyPairGeneratorSpi {
        public EC() {
            super(3);
        }
    }

    static {
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("p-224", 224);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp224r1", 224);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("p-256", 256);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp256r1", 256);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("prime256v1", 256);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("p-384", Integer.valueOf(MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION));
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp384r1", Integer.valueOf(MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION));
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("p-521", 521);
        SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.put("secp521r1", 521);
        SUPPORTED_EC_NIST_CURVE_NAMES.addAll(SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.keySet());
        Collections.sort(SUPPORTED_EC_NIST_CURVE_NAMES);
        SUPPORTED_EC_NIST_CURVE_SIZES.addAll(new HashSet(SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.values()));
        Collections.sort(SUPPORTED_EC_NIST_CURVE_SIZES);
    }

    protected AndroidKeyStoreKeyPairGeneratorSpi(int keymasterAlgorithm) {
        this.mOriginalKeymasterAlgorithm = keymasterAlgorithm;
    }

    public void initialize(int keysize, SecureRandom random) {
        throw new IllegalArgumentException(KeyGenParameterSpec.class.getName() + " or " + KeyPairGeneratorSpec.class.getName() + " required to initialize this KeyPairGenerator");
    }

    /* JADX WARNING: Removed duplicated region for block: B:85:0x026b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initialize(java.security.spec.AlgorithmParameterSpec r19, java.security.SecureRandom r20) throws java.security.InvalidAlgorithmParameterException {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r18.resetAll()
            r0 = 0
            r3 = r0
            if (r2 == 0) goto L_0x023b
            r4 = 0
            int r5 = r1.mOriginalKeymasterAlgorithm     // Catch:{ all -> 0x0237 }
            boolean r6 = r2 instanceof android.security.keystore.KeyGenParameterSpec     // Catch:{ all -> 0x0237 }
            r7 = -1
            r8 = 1
            if (r6 == 0) goto L_0x0019
            r6 = r2
            android.security.keystore.KeyGenParameterSpec r6 = (android.security.keystore.KeyGenParameterSpec) r6     // Catch:{ all -> 0x0237 }
            goto L_0x00fa
        L_0x0019:
            boolean r6 = r2 instanceof android.security.KeyPairGeneratorSpec     // Catch:{ all -> 0x0237 }
            if (r6 == 0) goto L_0x01fa
            r6 = r2
            android.security.KeyPairGeneratorSpec r6 = (android.security.KeyPairGeneratorSpec) r6     // Catch:{ all -> 0x0237 }
            java.lang.String r9 = r6.getKeyType()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            if (r9 == 0) goto L_0x0038
            int r10 = android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(r9)     // Catch:{ IllegalArgumentException -> 0x002d }
            r5 = r10
            goto L_0x0038
        L_0x002d:
            r0 = move-exception
            r7 = r0
            r0 = r7
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r8 = "Invalid key type in parameters"
            r7.<init>(r8, r0)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            throw r7     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
        L_0x0038:
            if (r5 == r8) goto L_0x0075
            r10 = 3
            if (r5 != r10) goto L_0x005c
            android.security.keystore.KeyGenParameterSpec$Builder r10 = new android.security.keystore.KeyGenParameterSpec$Builder     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r11 = r6.getKeystoreAlias()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r12 = 12
            r10.<init>(r11, r12)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r11 = "NONE"
            java.lang.String r12 = "SHA-1"
            java.lang.String r13 = "SHA-224"
            java.lang.String r14 = "SHA-256"
            java.lang.String r15 = "SHA-384"
            java.lang.String r16 = "SHA-512"
            java.lang.String[] r11 = new java.lang.String[]{r11, r12, r13, r14, r15, r16}     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setDigests(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            goto L_0x00b1
        L_0x005c:
            java.security.ProviderException r0 = new java.security.ProviderException     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r7.<init>()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r8 = "Unsupported algorithm: "
            r7.append(r8)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            int r8 = r1.mKeymasterAlgorithm     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r7.append(r8)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r7 = r7.toString()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r0.<init>(r7)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            throw r0     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
        L_0x0075:
            android.security.keystore.KeyGenParameterSpec$Builder r10 = new android.security.keystore.KeyGenParameterSpec$Builder     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r11 = r6.getKeystoreAlias()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r12 = 15
            r10.<init>(r11, r12)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r11 = "NONE"
            java.lang.String r12 = "MD5"
            java.lang.String r13 = "SHA-1"
            java.lang.String r14 = "SHA-224"
            java.lang.String r15 = "SHA-256"
            java.lang.String r16 = "SHA-384"
            java.lang.String r17 = "SHA-512"
            java.lang.String[] r11 = new java.lang.String[]{r11, r12, r13, r14, r15, r16, r17}     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setDigests(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r11 = "NoPadding"
            java.lang.String r12 = "PKCS1Padding"
            java.lang.String r13 = "OAEPPadding"
            java.lang.String[] r11 = new java.lang.String[]{r11, r12, r13}     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setEncryptionPaddings(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.lang.String r11 = "PKCS1"
            java.lang.String r12 = "PSS"
            java.lang.String[] r11 = new java.lang.String[]{r11, r12}     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setSignaturePaddings(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setRandomizedEncryptionRequired(r0)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
        L_0x00b1:
            int r11 = r6.getKeySize()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            if (r11 == r7) goto L_0x00bf
            int r11 = r6.getKeySize()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setKeySize(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
        L_0x00bf:
            java.security.spec.AlgorithmParameterSpec r11 = r6.getAlgorithmParameterSpec()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            if (r11 == 0) goto L_0x00cd
            java.security.spec.AlgorithmParameterSpec r11 = r6.getAlgorithmParameterSpec()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setAlgorithmParameterSpec(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
        L_0x00cd:
            javax.security.auth.x500.X500Principal r11 = r6.getSubjectDN()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setCertificateSubject(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.math.BigInteger r11 = r6.getSerialNumber()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setCertificateSerialNumber(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.util.Date r11 = r6.getStartDate()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setCertificateNotBefore(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            java.util.Date r11 = r6.getEndDate()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r10.setCertificateNotAfter(r11)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            boolean r11 = r6.isEncryptionRequired()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r4 = r11
            r10.setUserAuthenticationRequired(r0)     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            android.security.keystore.KeyGenParameterSpec r11 = r10.build()     // Catch:{ IllegalArgumentException | NullPointerException -> 0x01f1 }
            r9 = r11
            r6 = r9
        L_0x00fa:
            java.lang.String r9 = r6.getKeystoreAlias()     // Catch:{ all -> 0x0237 }
            r1.mEntryAlias = r9     // Catch:{ all -> 0x0237 }
            int r9 = r6.getUid()     // Catch:{ all -> 0x0237 }
            r1.mEntryUid = r9     // Catch:{ all -> 0x0237 }
            r1.mSpec = r6     // Catch:{ all -> 0x0237 }
            r1.mKeymasterAlgorithm = r5     // Catch:{ all -> 0x0237 }
            r1.mEncryptionAtRestRequired = r4     // Catch:{ all -> 0x0237 }
            int r9 = r6.getKeySize()     // Catch:{ all -> 0x0237 }
            r1.mKeySizeBits = r9     // Catch:{ all -> 0x0237 }
            r18.initAlgorithmSpecificParameters()     // Catch:{ all -> 0x0237 }
            int r9 = r1.mKeySizeBits     // Catch:{ all -> 0x0237 }
            if (r9 != r7) goto L_0x011f
            int r7 = getDefaultKeySize(r5)     // Catch:{ all -> 0x0237 }
            r1.mKeySizeBits = r7     // Catch:{ all -> 0x0237 }
        L_0x011f:
            int r7 = r1.mKeySizeBits     // Catch:{ all -> 0x0237 }
            android.security.keystore.KeyGenParameterSpec r9 = r1.mSpec     // Catch:{ all -> 0x0237 }
            boolean r9 = r9.isStrongBoxBacked()     // Catch:{ all -> 0x0237 }
            checkValidKeySize(r5, r7, r9)     // Catch:{ all -> 0x0237 }
            java.lang.String r7 = r6.getKeystoreAlias()     // Catch:{ all -> 0x0237 }
            if (r7 == 0) goto L_0x01e7
            java.lang.String r7 = android.security.keystore.KeyProperties.KeyAlgorithm.fromKeymasterAsymmetricKeyAlgorithm(r5)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int r9 = r6.getPurposes()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int[] r9 = android.security.keystore.KeyProperties.Purpose.allToKeymaster(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mKeymasterPurposes = r9     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String[] r9 = r6.getBlockModes()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int[] r9 = android.security.keystore.KeyProperties.BlockMode.allToKeymaster(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mKeymasterBlockModes = r9     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String[] r9 = r6.getEncryptionPaddings()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int[] r9 = android.security.keystore.KeyProperties.EncryptionPadding.allToKeymaster(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mKeymasterEncryptionPaddings = r9     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int r9 = r6.getPurposes()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r8 = r8 & r9
            if (r8 == 0) goto L_0x019f
            boolean r8 = r6.isRandomizedEncryptionRequired()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            if (r8 == 0) goto L_0x019f
            int[] r8 = r1.mKeymasterEncryptionPaddings     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int r9 = r8.length     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
        L_0x0163:
            if (r0 >= r9) goto L_0x019f
            r10 = r8[r0]     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            boolean r11 = android.security.keystore.KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(r10)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            if (r11 == 0) goto L_0x0171
            int r0 = r0 + 1
            goto L_0x0163
        L_0x0171:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r8.<init>()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String r9 = "Randomized encryption (IND-CPA) required but may be violated by padding scheme: "
            r8.append(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String r9 = android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(r10)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r8.append(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String r9 = ". See "
            r8.append(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.Class<android.security.keystore.KeyGenParameterSpec> r9 = android.security.keystore.KeyGenParameterSpec.class
            java.lang.String r9 = r9.getName()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r8.append(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String r9 = " documentation."
            r8.append(r9)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            java.lang.String r8 = r8.toString()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r0.<init>(r8)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            throw r0     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
        L_0x019f:
            java.lang.String[] r0 = r6.getSignaturePaddings()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int[] r0 = android.security.keystore.KeyProperties.SignaturePadding.allToKeymaster(r0)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mKeymasterSignaturePaddings = r0     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            boolean r0 = r6.isDigestsSpecified()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            if (r0 == 0) goto L_0x01bb
            java.lang.String[] r0 = r6.getDigests()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            int[] r0 = android.security.keystore.KeyProperties.Digest.allToKeymaster(r0)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mKeymasterDigests = r0     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            goto L_0x01bf
        L_0x01bb:
            int[] r0 = libcore.util.EmptyArray.INT     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mKeymasterDigests = r0     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
        L_0x01bf:
            android.security.keymaster.KeymasterArguments r0 = new android.security.keymaster.KeymasterArguments     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r0.<init>()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            android.security.keystore.KeyGenParameterSpec r8 = r1.mSpec     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            android.security.keystore.KeymasterUtils.addUserAuthArgs(r0, r8)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01de }
            r1.mJcaKeyAlgorithm = r7     // Catch:{ all -> 0x0237 }
            r8 = r20
            r1.mRng = r8     // Catch:{ all -> 0x0268 }
            android.security.KeyStore r0 = android.security.KeyStore.getInstance()     // Catch:{ all -> 0x0268 }
            r1.mKeyStore = r0     // Catch:{ all -> 0x0268 }
            r0 = 1
            if (r0 != 0) goto L_0x01dd
            r18.resetAll()
        L_0x01dd:
            return
        L_0x01de:
            r0 = move-exception
            r8 = r20
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException     // Catch:{ all -> 0x0268 }
            r7.<init>(r0)     // Catch:{ all -> 0x0268 }
            throw r7     // Catch:{ all -> 0x0268 }
        L_0x01e7:
            r8 = r20
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException     // Catch:{ all -> 0x0268 }
            java.lang.String r7 = "KeyStore entry alias not provided"
            r0.<init>(r7)     // Catch:{ all -> 0x0268 }
            throw r0     // Catch:{ all -> 0x0268 }
        L_0x01f1:
            r0 = move-exception
            r8 = r20
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException     // Catch:{ all -> 0x0268 }
            r7.<init>(r0)     // Catch:{ all -> 0x0268 }
            throw r7     // Catch:{ all -> 0x0268 }
        L_0x01fa:
            r8 = r20
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException     // Catch:{ all -> 0x0268 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0268 }
            r6.<init>()     // Catch:{ all -> 0x0268 }
            java.lang.String r7 = "Unsupported params class: "
            r6.append(r7)     // Catch:{ all -> 0x0268 }
            java.lang.Class r7 = r19.getClass()     // Catch:{ all -> 0x0268 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0268 }
            r6.append(r7)     // Catch:{ all -> 0x0268 }
            java.lang.String r7 = ". Supported: "
            r6.append(r7)     // Catch:{ all -> 0x0268 }
            java.lang.Class<android.security.keystore.KeyGenParameterSpec> r7 = android.security.keystore.KeyGenParameterSpec.class
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0268 }
            r6.append(r7)     // Catch:{ all -> 0x0268 }
            java.lang.String r7 = ", "
            r6.append(r7)     // Catch:{ all -> 0x0268 }
            java.lang.Class<android.security.KeyPairGeneratorSpec> r7 = android.security.KeyPairGeneratorSpec.class
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0268 }
            r6.append(r7)     // Catch:{ all -> 0x0268 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0268 }
            r0.<init>(r6)     // Catch:{ all -> 0x0268 }
            throw r0     // Catch:{ all -> 0x0268 }
        L_0x0237:
            r0 = move-exception
            r8 = r20
            goto L_0x0269
        L_0x023b:
            r8 = r20
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException     // Catch:{ all -> 0x0268 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0268 }
            r4.<init>()     // Catch:{ all -> 0x0268 }
            java.lang.String r5 = "Must supply params of type "
            r4.append(r5)     // Catch:{ all -> 0x0268 }
            java.lang.Class<android.security.keystore.KeyGenParameterSpec> r5 = android.security.keystore.KeyGenParameterSpec.class
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x0268 }
            r4.append(r5)     // Catch:{ all -> 0x0268 }
            java.lang.String r5 = " or "
            r4.append(r5)     // Catch:{ all -> 0x0268 }
            java.lang.Class<android.security.KeyPairGeneratorSpec> r5 = android.security.KeyPairGeneratorSpec.class
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x0268 }
            r4.append(r5)     // Catch:{ all -> 0x0268 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0268 }
            r0.<init>(r4)     // Catch:{ all -> 0x0268 }
            throw r0     // Catch:{ all -> 0x0268 }
        L_0x0268:
            r0 = move-exception
        L_0x0269:
            if (r3 != 0) goto L_0x026e
            r18.resetAll()
        L_0x026e:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.AndroidKeyStoreKeyPairGeneratorSpi.initialize(java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    private void resetAll() {
        this.mEntryAlias = null;
        this.mEntryUid = -1;
        this.mJcaKeyAlgorithm = null;
        this.mKeymasterAlgorithm = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterBlockModes = null;
        this.mKeymasterEncryptionPaddings = null;
        this.mKeymasterSignaturePaddings = null;
        this.mKeymasterDigests = null;
        this.mKeySizeBits = 0;
        this.mSpec = null;
        this.mRSAPublicExponent = null;
        this.mEncryptionAtRestRequired = false;
        this.mRng = null;
        this.mKeyStore = null;
    }

    private void initAlgorithmSpecificParameters() throws InvalidAlgorithmParameterException {
        AlgorithmParameterSpec algSpecificSpec = this.mSpec.getAlgorithmParameterSpec();
        int i = this.mKeymasterAlgorithm;
        if (i == 1) {
            BigInteger publicExponent = null;
            if (algSpecificSpec instanceof RSAKeyGenParameterSpec) {
                RSAKeyGenParameterSpec rsaSpec = (RSAKeyGenParameterSpec) algSpecificSpec;
                if (this.mKeySizeBits == -1) {
                    this.mKeySizeBits = rsaSpec.getKeysize();
                } else if (this.mKeySizeBits != rsaSpec.getKeysize()) {
                    throw new InvalidAlgorithmParameterException("RSA key size must match  between " + this.mSpec + " and " + algSpecificSpec + ": " + this.mKeySizeBits + " vs " + rsaSpec.getKeysize());
                }
                publicExponent = rsaSpec.getPublicExponent();
            } else if (algSpecificSpec != null) {
                throw new InvalidAlgorithmParameterException("RSA may only use RSAKeyGenParameterSpec");
            }
            if (publicExponent == null) {
                publicExponent = RSAKeyGenParameterSpec.F4;
            }
            if (publicExponent.compareTo(BigInteger.ZERO) < 1) {
                throw new InvalidAlgorithmParameterException("RSA public exponent must be positive: " + publicExponent);
            } else if (publicExponent.compareTo(KeymasterArguments.UINT64_MAX_VALUE) <= 0) {
                this.mRSAPublicExponent = publicExponent;
            } else {
                throw new InvalidAlgorithmParameterException("Unsupported RSA public exponent: " + publicExponent + ". Maximum supported value: " + KeymasterArguments.UINT64_MAX_VALUE);
            }
        } else if (i != 3) {
            throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
        } else if (algSpecificSpec instanceof ECGenParameterSpec) {
            String curveName = ((ECGenParameterSpec) algSpecificSpec).getName();
            Integer ecSpecKeySizeBits = SUPPORTED_EC_NIST_CURVE_NAME_TO_SIZE.get(curveName.toLowerCase(Locale.US));
            if (ecSpecKeySizeBits == null) {
                throw new InvalidAlgorithmParameterException("Unsupported EC curve name: " + curveName + ". Supported: " + SUPPORTED_EC_NIST_CURVE_NAMES);
            } else if (this.mKeySizeBits == -1) {
                this.mKeySizeBits = ecSpecKeySizeBits.intValue();
            } else if (this.mKeySizeBits != ecSpecKeySizeBits.intValue()) {
                throw new InvalidAlgorithmParameterException("EC key size must match  between " + this.mSpec + " and " + algSpecificSpec + ": " + this.mKeySizeBits + " vs " + ecSpecKeySizeBits);
            }
        } else if (algSpecificSpec != null) {
            throw new InvalidAlgorithmParameterException("EC may only use ECGenParameterSpec");
        }
    }

    public KeyPair generateKeyPair() {
        if (this.mKeyStore == null || this.mSpec == null) {
            throw new IllegalStateException("Not initialized");
        }
        int flags = this.mEncryptionAtRestRequired;
        if ((flags & 1) == 0 || this.mKeyStore.state() == KeyStore.State.UNLOCKED) {
            if (this.mSpec.isStrongBoxBacked()) {
                flags |= 16;
            }
            byte[] additionalEntropy = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
            Credentials.deleteAllTypesForAlias(this.mKeyStore, this.mEntryAlias, this.mEntryUid);
            String privateKeyAlias = Credentials.USER_PRIVATE_KEY + this.mEntryAlias;
            try {
                generateKeystoreKeyPair(privateKeyAlias, constructKeyGenerationArguments(), additionalEntropy, (int) flags);
                KeyPair keyPair = loadKeystoreKeyPair(privateKeyAlias);
                storeCertificateChain(flags, createCertificateChain(privateKeyAlias, keyPair));
                if (1 == 0) {
                    Credentials.deleteAllTypesForAlias(this.mKeyStore, this.mEntryAlias, this.mEntryUid);
                }
                return keyPair;
            } catch (ProviderException e) {
                if ((this.mSpec.getPurposes() & 32) != 0) {
                    throw new SecureKeyImportUnavailableException((Throwable) e);
                }
                throw e;
            } catch (Throwable th) {
                if (0 == 0) {
                    Credentials.deleteAllTypesForAlias(this.mKeyStore, this.mEntryAlias, this.mEntryUid);
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("Encryption at rest using secure lock screen credential requested for key pair, but the user has not yet entered the credential");
        }
    }

    private Iterable<byte[]> createCertificateChain(String privateKeyAlias, KeyPair keyPair) throws ProviderException {
        byte[] challenge = this.mSpec.getAttestationChallenge();
        if (challenge == null) {
            return Collections.singleton(generateSelfSignedCertificateBytes(keyPair));
        }
        KeymasterArguments args = new KeymasterArguments();
        args.addBytes(KeymasterDefs.KM_TAG_ATTESTATION_CHALLENGE, challenge);
        return getAttestationChain(privateKeyAlias, keyPair, args);
    }

    private void generateKeystoreKeyPair(String privateKeyAlias, KeymasterArguments args, byte[] additionalEntropy, int flags) throws ProviderException {
        String str = privateKeyAlias;
        KeymasterArguments keymasterArguments = args;
        byte[] bArr = additionalEntropy;
        int errorCode = this.mKeyStore.generateKey(str, keymasterArguments, bArr, this.mEntryUid, flags, new KeyCharacteristics());
        if (errorCode == 1) {
            return;
        }
        if (errorCode == -68) {
            throw new StrongBoxUnavailableException("Failed to generate key pair");
        }
        throw new ProviderException("Failed to generate key pair", KeyStore.getKeyStoreException(errorCode));
    }

    private KeyPair loadKeystoreKeyPair(String privateKeyAlias) throws ProviderException {
        try {
            KeyPair result = AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(this.mKeyStore, privateKeyAlias, this.mEntryUid);
            if (this.mJcaKeyAlgorithm.equalsIgnoreCase(result.getPrivate().getAlgorithm())) {
                return result;
            }
            throw new ProviderException("Generated key pair algorithm does not match requested algorithm: " + result.getPrivate().getAlgorithm() + " vs " + this.mJcaKeyAlgorithm);
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e) {
            throw new ProviderException("Failed to load generated key pair from keystore", e);
        }
    }

    private KeymasterArguments constructKeyGenerationArguments() {
        KeymasterArguments args = new KeymasterArguments();
        args.addUnsignedInt(KeymasterDefs.KM_TAG_KEY_SIZE, (long) this.mKeySizeBits);
        args.addEnum(KeymasterDefs.KM_TAG_ALGORITHM, this.mKeymasterAlgorithm);
        args.addEnums(KeymasterDefs.KM_TAG_PURPOSE, this.mKeymasterPurposes);
        args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, this.mKeymasterBlockModes);
        args.addEnums(KeymasterDefs.KM_TAG_PADDING, this.mKeymasterEncryptionPaddings);
        args.addEnums(KeymasterDefs.KM_TAG_PADDING, this.mKeymasterSignaturePaddings);
        args.addEnums(KeymasterDefs.KM_TAG_DIGEST, this.mKeymasterDigests);
        KeymasterUtils.addUserAuthArgs(args, this.mSpec);
        args.addDateIfNotNull(KeymasterDefs.KM_TAG_ACTIVE_DATETIME, this.mSpec.getKeyValidityStart());
        args.addDateIfNotNull(KeymasterDefs.KM_TAG_ORIGINATION_EXPIRE_DATETIME, this.mSpec.getKeyValidityForOriginationEnd());
        args.addDateIfNotNull(KeymasterDefs.KM_TAG_USAGE_EXPIRE_DATETIME, this.mSpec.getKeyValidityForConsumptionEnd());
        addAlgorithmSpecificParameters(args);
        if (this.mSpec.isUniqueIdIncluded()) {
            args.addBoolean(KeymasterDefs.KM_TAG_INCLUDE_UNIQUE_ID);
        }
        return args;
    }

    private void storeCertificateChain(int flags, Iterable<byte[]> iterable) throws ProviderException {
        Iterator<byte[]> iter = iterable.iterator();
        storeCertificate(Credentials.USER_CERTIFICATE, iter.next(), flags, "Failed to store certificate");
        if (iter.hasNext()) {
            ByteArrayOutputStream certificateConcatenationStream = new ByteArrayOutputStream();
            while (iter.hasNext()) {
                byte[] data = iter.next();
                certificateConcatenationStream.write(data, 0, data.length);
            }
            storeCertificate(Credentials.CA_CERTIFICATE, certificateConcatenationStream.toByteArray(), flags, "Failed to store attestation CA certificate");
        }
    }

    private void storeCertificate(String prefix, byte[] certificateBytes, int flags, String failureMessage) throws ProviderException {
        KeyStore keyStore = this.mKeyStore;
        int insertErrorCode = keyStore.insert(prefix + this.mEntryAlias, certificateBytes, this.mEntryUid, flags);
        if (insertErrorCode != 1) {
            throw new ProviderException(failureMessage, KeyStore.getKeyStoreException(insertErrorCode));
        }
    }

    private byte[] generateSelfSignedCertificateBytes(KeyPair keyPair) throws ProviderException {
        try {
            return generateSelfSignedCertificate(keyPair.getPrivate(), keyPair.getPublic()).getEncoded();
        } catch (IOException | CertificateParsingException e) {
            throw new ProviderException("Failed to generate self-signed certificate", e);
        } catch (CertificateEncodingException e2) {
            throw new ProviderException("Failed to obtain encoded form of self-signed certificate", e2);
        }
    }

    private Iterable<byte[]> getAttestationChain(String privateKeyAlias, KeyPair keyPair, KeymasterArguments args) throws ProviderException {
        KeymasterCertificateChain outChain = new KeymasterCertificateChain();
        int errorCode = this.mKeyStore.attestKey(privateKeyAlias, args, outChain);
        if (errorCode == 1) {
            Collection<byte[]> chain = outChain.getCertificates();
            if (chain.size() >= 2) {
                return chain;
            }
            throw new ProviderException("Attestation certificate chain contained " + chain.size() + " entries. At least two are required.");
        }
        throw new ProviderException("Failed to generate attestation certificate chain", KeyStore.getKeyStoreException(errorCode));
    }

    private void addAlgorithmSpecificParameters(KeymasterArguments keymasterArgs) {
        int i = this.mKeymasterAlgorithm;
        if (i == 1) {
            keymasterArgs.addUnsignedLong(KeymasterDefs.KM_TAG_RSA_PUBLIC_EXPONENT, this.mRSAPublicExponent);
        } else if (i != 3) {
            throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
        }
    }

    private X509Certificate generateSelfSignedCertificate(PrivateKey privateKey, PublicKey publicKey) throws CertificateParsingException, IOException {
        String signatureAlgorithm = getCertificateSignatureAlgorithm(this.mKeymasterAlgorithm, this.mKeySizeBits, this.mSpec);
        if (signatureAlgorithm == null) {
            return generateSelfSignedCertificateWithFakeSignature(publicKey);
        }
        try {
            return generateSelfSignedCertificateWithValidSignature(privateKey, publicKey, signatureAlgorithm);
        } catch (Exception e) {
            return generateSelfSignedCertificateWithFakeSignature(publicKey);
        }
    }

    private X509Certificate generateSelfSignedCertificateWithValidSignature(PrivateKey privateKey, PublicKey publicKey, String signatureAlgorithm) throws Exception {
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        certGen.setPublicKey(publicKey);
        certGen.setSerialNumber(this.mSpec.getCertificateSerialNumber());
        certGen.setSubjectDN(this.mSpec.getCertificateSubject());
        certGen.setIssuerDN(this.mSpec.getCertificateSubject());
        certGen.setNotBefore(this.mSpec.getCertificateNotBefore());
        certGen.setNotAfter(this.mSpec.getCertificateNotAfter());
        certGen.setSignatureAlgorithm(signatureAlgorithm);
        return certGen.generate(privateKey);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00dd, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00e1, code lost:
        if (r5 != null) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00e7, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00e8, code lost:
        r5.addSuppressed(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ec, code lost:
        r4.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.security.cert.X509Certificate generateSelfSignedCertificateWithFakeSignature(java.security.PublicKey r10) throws java.io.IOException, java.security.cert.CertificateParsingException {
        /*
            r9 = this;
            com.android.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator r0 = new com.android.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator
            r0.<init>()
            int r1 = r9.mKeymasterAlgorithm
            r2 = 1
            if (r1 == r2) goto L_0x004e
            r2 = 3
            if (r1 != r2) goto L_0x0035
            com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256
            com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = new com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier
            r2.<init>(r1)
            com.android.org.bouncycastle.asn1.ASN1EncodableVector r3 = new com.android.org.bouncycastle.asn1.ASN1EncodableVector
            r3.<init>()
            com.android.org.bouncycastle.asn1.DERInteger r4 = new com.android.org.bouncycastle.asn1.DERInteger
            r5 = 0
            r4.<init>(r5)
            r3.add(r4)
            com.android.org.bouncycastle.asn1.DERInteger r4 = new com.android.org.bouncycastle.asn1.DERInteger
            r4.<init>(r5)
            r3.add(r4)
            com.android.org.bouncycastle.asn1.DERSequence r4 = new com.android.org.bouncycastle.asn1.DERSequence
            r4.<init>()
            byte[] r4 = r4.getEncoded()
            goto L_0x005b
        L_0x0035:
            java.security.ProviderException r1 = new java.security.ProviderException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unsupported key algorithm: "
            r2.append(r3)
            int r3 = r9.mKeymasterAlgorithm
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x004e:
            com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption
            com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier r3 = new com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier
            com.android.org.bouncycastle.asn1.DERNull r4 = com.android.org.bouncycastle.asn1.DERNull.INSTANCE
            r3.<init>(r1, r4)
            byte[] r4 = new byte[r2]
            r2 = r3
        L_0x005b:
            r3 = r4
            com.android.org.bouncycastle.asn1.ASN1InputStream r4 = new com.android.org.bouncycastle.asn1.ASN1InputStream
            byte[] r5 = r10.getEncoded()
            r4.<init>(r5)
            r5 = 0
            com.android.org.bouncycastle.asn1.ASN1Primitive r6 = r4.readObject()     // Catch:{ Throwable -> 0x00df }
            com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r6 = com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(r6)     // Catch:{ Throwable -> 0x00df }
            r0.setSubjectPublicKeyInfo(r6)     // Catch:{ Throwable -> 0x00df }
            r4.close()
            com.android.org.bouncycastle.asn1.ASN1Integer r4 = new com.android.org.bouncycastle.asn1.ASN1Integer
            android.security.keystore.KeyGenParameterSpec r5 = r9.mSpec
            java.math.BigInteger r5 = r5.getCertificateSerialNumber()
            r4.<init>(r5)
            r0.setSerialNumber(r4)
            com.android.org.bouncycastle.jce.X509Principal r4 = new com.android.org.bouncycastle.jce.X509Principal
            android.security.keystore.KeyGenParameterSpec r5 = r9.mSpec
            javax.security.auth.x500.X500Principal r5 = r5.getCertificateSubject()
            byte[] r5 = r5.getEncoded()
            r4.<init>(r5)
            r0.setSubject(r4)
            r0.setIssuer(r4)
            com.android.org.bouncycastle.asn1.x509.Time r5 = new com.android.org.bouncycastle.asn1.x509.Time
            android.security.keystore.KeyGenParameterSpec r6 = r9.mSpec
            java.util.Date r6 = r6.getCertificateNotBefore()
            r5.<init>(r6)
            r0.setStartDate(r5)
            com.android.org.bouncycastle.asn1.x509.Time r5 = new com.android.org.bouncycastle.asn1.x509.Time
            android.security.keystore.KeyGenParameterSpec r6 = r9.mSpec
            java.util.Date r6 = r6.getCertificateNotAfter()
            r5.<init>(r6)
            r0.setEndDate(r5)
            r0.setSignature(r2)
            com.android.org.bouncycastle.asn1.x509.TBSCertificate r5 = r0.generateTBSCertificate()
            com.android.org.bouncycastle.asn1.ASN1EncodableVector r6 = new com.android.org.bouncycastle.asn1.ASN1EncodableVector
            r6.<init>()
            r6.add(r5)
            r6.add(r2)
            com.android.org.bouncycastle.asn1.DERBitString r7 = new com.android.org.bouncycastle.asn1.DERBitString
            r7.<init>(r3)
            r6.add(r7)
            com.android.org.bouncycastle.jce.provider.X509CertificateObject r7 = new com.android.org.bouncycastle.jce.provider.X509CertificateObject
            com.android.org.bouncycastle.asn1.DERSequence r8 = new com.android.org.bouncycastle.asn1.DERSequence
            r8.<init>(r6)
            com.android.org.bouncycastle.asn1.x509.Certificate r8 = com.android.org.bouncycastle.asn1.x509.Certificate.getInstance(r8)
            r7.<init>(r8)
            return r7
        L_0x00dd:
            r6 = move-exception
            goto L_0x00e1
        L_0x00df:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x00dd }
        L_0x00e1:
            if (r5 == 0) goto L_0x00ec
            r4.close()     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x00ef
        L_0x00e7:
            r7 = move-exception
            r5.addSuppressed(r7)
            goto L_0x00ef
        L_0x00ec:
            r4.close()
        L_0x00ef:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.AndroidKeyStoreKeyPairGeneratorSpi.generateSelfSignedCertificateWithFakeSignature(java.security.PublicKey):java.security.cert.X509Certificate");
    }

    private static int getDefaultKeySize(int keymasterAlgorithm) {
        if (keymasterAlgorithm == 1) {
            return 2048;
        }
        if (keymasterAlgorithm == 3) {
            return 256;
        }
        throw new ProviderException("Unsupported algorithm: " + keymasterAlgorithm);
    }

    private static void checkValidKeySize(int keymasterAlgorithm, int keySize, boolean isStrongBoxBacked) throws InvalidAlgorithmParameterException {
        if (keymasterAlgorithm != 1) {
            if (keymasterAlgorithm != 3) {
                throw new ProviderException("Unsupported algorithm: " + keymasterAlgorithm);
            } else if (isStrongBoxBacked && keySize != 256) {
                throw new InvalidAlgorithmParameterException("Unsupported StrongBox EC key size: " + keySize + " bits. Supported: 256");
            } else if (!SUPPORTED_EC_NIST_CURVE_SIZES.contains(Integer.valueOf(keySize))) {
                throw new InvalidAlgorithmParameterException("Unsupported EC key size: " + keySize + " bits. Supported: " + SUPPORTED_EC_NIST_CURVE_SIZES);
            }
        } else if (keySize < 512 || keySize > 8192) {
            throw new InvalidAlgorithmParameterException("RSA key size must be >= 512 and <= 8192");
        }
    }

    private static String getCertificateSignatureAlgorithm(int keymasterAlgorithm, int keySizeBits, KeyGenParameterSpec spec) {
        if ((spec.getPurposes() & 4) == 0 || spec.isUserAuthenticationRequired() || !spec.isDigestsSpecified()) {
            return null;
        }
        if (keymasterAlgorithm != 1) {
            if (keymasterAlgorithm == 3) {
                int bestKeymasterDigest = -1;
                int bestDigestOutputSizeBits = -1;
                Iterator<Integer> it = getAvailableKeymasterSignatureDigests(spec.getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    int keymasterDigest = it.next().intValue();
                    int outputSizeBits = KeymasterUtils.getDigestOutputSizeBits(keymasterDigest);
                    if (outputSizeBits == keySizeBits) {
                        bestKeymasterDigest = keymasterDigest;
                        int bestDigestOutputSizeBits2 = outputSizeBits;
                        break;
                    } else if (bestKeymasterDigest == -1) {
                        bestKeymasterDigest = keymasterDigest;
                        bestDigestOutputSizeBits = outputSizeBits;
                    } else if (bestDigestOutputSizeBits < keySizeBits) {
                        if (outputSizeBits > bestDigestOutputSizeBits) {
                            bestKeymasterDigest = keymasterDigest;
                            bestDigestOutputSizeBits = outputSizeBits;
                        }
                    } else if (outputSizeBits < bestDigestOutputSizeBits && outputSizeBits >= keySizeBits) {
                        bestKeymasterDigest = keymasterDigest;
                        bestDigestOutputSizeBits = outputSizeBits;
                    }
                }
                if (bestKeymasterDigest == -1) {
                    return null;
                }
                return KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(bestKeymasterDigest) + "WithECDSA";
            }
            throw new ProviderException("Unsupported algorithm: " + keymasterAlgorithm);
        } else if (!ArrayUtils.contains(KeyProperties.SignaturePadding.allToKeymaster(spec.getSignaturePaddings()), 5)) {
            return null;
        } else {
            int maxDigestOutputSizeBits = keySizeBits - 240;
            int bestKeymasterDigest2 = -1;
            int bestDigestOutputSizeBits3 = -1;
            for (Integer intValue : getAvailableKeymasterSignatureDigests(spec.getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests())) {
                int keymasterDigest2 = intValue.intValue();
                int outputSizeBits2 = KeymasterUtils.getDigestOutputSizeBits(keymasterDigest2);
                if (outputSizeBits2 <= maxDigestOutputSizeBits) {
                    if (bestKeymasterDigest2 == -1) {
                        bestKeymasterDigest2 = keymasterDigest2;
                        bestDigestOutputSizeBits3 = outputSizeBits2;
                    } else if (outputSizeBits2 > bestDigestOutputSizeBits3) {
                        bestKeymasterDigest2 = keymasterDigest2;
                        bestDigestOutputSizeBits3 = outputSizeBits2;
                    }
                }
            }
            if (bestKeymasterDigest2 == -1) {
                return null;
            }
            return KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(bestKeymasterDigest2) + "WithRSA";
        }
    }

    private static Set<Integer> getAvailableKeymasterSignatureDigests(String[] authorizedKeyDigests, String[] supportedSignatureDigests) {
        Set<Integer> authorizedKeymasterKeyDigests = new HashSet<>();
        for (int keymasterDigest : KeyProperties.Digest.allToKeymaster(authorizedKeyDigests)) {
            authorizedKeymasterKeyDigests.add(Integer.valueOf(keymasterDigest));
        }
        Set<Integer> supportedKeymasterSignatureDigests = new HashSet<>();
        for (int keymasterDigest2 : KeyProperties.Digest.allToKeymaster(supportedSignatureDigests)) {
            supportedKeymasterSignatureDigests.add(Integer.valueOf(keymasterDigest2));
        }
        Set<Integer> result = new HashSet<>(supportedKeymasterSignatureDigests);
        result.retainAll(authorizedKeymasterKeyDigests);
        return result;
    }
}
