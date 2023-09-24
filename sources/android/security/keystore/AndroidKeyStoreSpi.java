package android.security.keystore;

import android.security.Credentials;
import android.security.GateKeeper;
import android.security.KeyStore;
import android.security.KeyStoreParameter;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyProtection;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSpi extends KeyStoreSpi {
    public static final String NAME = "AndroidKeyStore";
    private KeyStore mKeyStore;
    private int mUid = -1;

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String alias, char[] password) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        String userKeyAlias = Credentials.USER_PRIVATE_KEY + alias;
        if (!this.mKeyStore.contains(userKeyAlias, this.mUid)) {
            userKeyAlias = Credentials.USER_SECRET_KEY + alias;
            if (!this.mKeyStore.contains(userKeyAlias, this.mUid)) {
                return null;
            }
        }
        try {
            AndroidKeyStoreKey key = AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore, userKeyAlias, this.mUid);
            return key;
        } catch (KeyPermanentlyInvalidatedException e) {
            throw new UnrecoverableKeyException(e.getMessage());
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String alias) {
        Certificate[] caList;
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        X509Certificate leaf = (X509Certificate) engineGetCertificate(alias);
        if (leaf == null) {
            return null;
        }
        KeyStore keyStore = this.mKeyStore;
        int i = 1;
        byte[] caBytes = keyStore.get(Credentials.CA_CERTIFICATE + alias, this.mUid, true);
        if (caBytes != null) {
            Collection<X509Certificate> caChain = toCertificates(caBytes);
            caList = new Certificate[caChain.size() + 1];
            for (X509Certificate x509Certificate : caChain) {
                caList[i] = x509Certificate;
                i++;
            }
        } else {
            caList = new Certificate[1];
        }
        Certificate[] caList2 = caList;
        caList2[0] = leaf;
        return caList2;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String alias) {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        KeyStore keyStore = this.mKeyStore;
        byte[] encodedCert = keyStore.get(Credentials.USER_CERTIFICATE + alias, this.mUid);
        if (encodedCert != null) {
            return getCertificateForPrivateKeyEntry(alias, encodedCert);
        }
        KeyStore keyStore2 = this.mKeyStore;
        byte[] encodedCert2 = keyStore2.get(Credentials.CA_CERTIFICATE + alias, this.mUid);
        if (encodedCert2 != null) {
            return getCertificateForTrustedCertificateEntry(encodedCert2);
        }
        return null;
    }

    private Certificate getCertificateForTrustedCertificateEntry(byte[] encodedCert) {
        return toCertificate(encodedCert);
    }

    private Certificate getCertificateForPrivateKeyEntry(String alias, byte[] encodedCert) {
        X509Certificate cert = toCertificate(encodedCert);
        if (cert == null) {
            return null;
        }
        String privateKeyAlias = Credentials.USER_PRIVATE_KEY + alias;
        if (this.mKeyStore.contains(privateKeyAlias, this.mUid)) {
            return wrapIntoKeyStoreCertificate(privateKeyAlias, this.mUid, cert);
        }
        return cert;
    }

    private static KeyStoreX509Certificate wrapIntoKeyStoreCertificate(String privateKeyAlias, int uid, X509Certificate certificate) {
        if (certificate != null) {
            return new KeyStoreX509Certificate(privateKeyAlias, uid, certificate);
        }
        return null;
    }

    private static X509Certificate toCertificate(byte[] bytes) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(bytes));
        } catch (CertificateException e) {
            Log.m63w("AndroidKeyStore", "Couldn't parse certificate in keystore", e);
            return null;
        }
    }

    private static Collection<X509Certificate> toCertificates(byte[] bytes) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            return certFactory.generateCertificates(new ByteArrayInputStream(bytes));
        } catch (CertificateException e) {
            Log.m63w("AndroidKeyStore", "Couldn't parse certificates in keystore", e);
            return new ArrayList();
        }
    }

    private Date getModificationDate(String alias) {
        long epochMillis = this.mKeyStore.getmtime(alias, this.mUid);
        if (epochMillis == -1) {
            return null;
        }
        return new Date(epochMillis);
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String alias) {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        Date d = getModificationDate(Credentials.USER_PRIVATE_KEY + alias);
        if (d != null) {
            return d;
        }
        Date d2 = getModificationDate(Credentials.USER_SECRET_KEY + alias);
        if (d2 != null) {
            return d2;
        }
        Date d3 = getModificationDate(Credentials.USER_CERTIFICATE + alias);
        if (d3 != null) {
            return d3;
        }
        return getModificationDate(Credentials.CA_CERTIFICATE + alias);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) throws KeyStoreException {
        if (password != null && password.length > 0) {
            throw new KeyStoreException("entries cannot be protected with passwords");
        }
        if (key instanceof PrivateKey) {
            setPrivateKeyEntry(alias, (PrivateKey) key, chain, null);
        } else if (key instanceof SecretKey) {
            setSecretKeyEntry(alias, (SecretKey) key, null);
        } else {
            throw new KeyStoreException("Only PrivateKey and SecretKey are supported");
        }
    }

    private static KeyProtection getLegacyKeyProtectionParameter(PrivateKey key) throws KeyStoreException {
        KeyProtection.Builder specBuilder;
        String keyAlgorithm = key.getAlgorithm();
        if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(keyAlgorithm)) {
            specBuilder = new KeyProtection.Builder(12);
            specBuilder.setDigests(KeyProperties.DIGEST_NONE, KeyProperties.DIGEST_SHA1, KeyProperties.DIGEST_SHA224, KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
        } else if (KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(keyAlgorithm)) {
            specBuilder = new KeyProtection.Builder(15);
            specBuilder.setDigests(KeyProperties.DIGEST_NONE, KeyProperties.DIGEST_MD5, KeyProperties.DIGEST_SHA1, KeyProperties.DIGEST_SHA224, KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
            specBuilder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE, KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
            specBuilder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PSS);
            specBuilder.setRandomizedEncryptionRequired(false);
        } else {
            throw new KeyStoreException("Unsupported key algorithm: " + keyAlgorithm);
        }
        specBuilder.setUserAuthenticationRequired(false);
        return specBuilder.build();
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x032c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setPrivateKeyEntry(String alias, PrivateKey key, Certificate[] chain, KeyStore.ProtectionParameter param) throws KeyStoreException {
        KeyProtection spec;
        String pkeyAlias;
        byte[] chainBytes;
        int purposes;
        int[] keymasterEncryptionPaddings;
        boolean shouldReplacePrivateKey;
        byte[] pkcs8EncodedPrivateKeyBytes;
        KeymasterArguments importArgs;
        KeyCharacteristics resultingKeyCharacteristics;
        byte[] chainBytes2;
        byte[] chainBytes3;
        byte[] userCertBytes;
        int i;
        if (param == null) {
            spec = getLegacyKeyProtectionParameter(key);
        } else if (param instanceof KeyStoreParameter) {
            spec = getLegacyKeyProtectionParameter(key);
            KeyStoreParameter legacySpec = (KeyStoreParameter) param;
            if (legacySpec.isEncryptionRequired()) {
                flags = 1;
            }
        } else if (!(param instanceof KeyProtection)) {
            throw new KeyStoreException("Unsupported protection parameter class:" + param.getClass().getName() + ". Supported: " + KeyProtection.class.getName() + ", " + KeyStoreParameter.class.getName());
        } else {
            spec = (KeyProtection) param;
            flags = spec.isCriticalToDeviceEncryption() ? 0 | 8 : 0;
            if (spec.isStrongBoxBacked()) {
                flags |= 16;
            }
        }
        int flags = flags;
        if (chain == null || chain.length == 0) {
            throw new KeyStoreException("Must supply at least one Certificate with PrivateKey");
        }
        X509Certificate[] x509chain = new X509Certificate[chain.length];
        for (int i2 = 0; i2 < chain.length; i2++) {
            if (!"X.509".equals(chain[i2].getType())) {
                throw new KeyStoreException("Certificates must be in X.509 format: invalid cert #" + i2);
            } else if (!(chain[i2] instanceof X509Certificate)) {
                throw new KeyStoreException("Certificates must be in X.509 format: invalid cert #" + i2);
            } else {
                x509chain[i2] = (X509Certificate) chain[i2];
            }
        }
        try {
            byte[] userCertBytes2 = x509chain[0].getEncoded();
            if (chain.length > 1) {
                byte[][] certsBytes = new byte[x509chain.length - 1];
                int totalCertLength = 0;
                for (int totalCertLength2 = 0; totalCertLength2 < certsBytes.length; totalCertLength2++) {
                    try {
                        certsBytes[totalCertLength2] = x509chain[totalCertLength2 + 1].getEncoded();
                        totalCertLength += certsBytes[totalCertLength2].length;
                    } catch (CertificateEncodingException e) {
                        throw new KeyStoreException("Failed to encode certificate #" + totalCertLength2, e);
                    }
                }
                chainBytes = new byte[totalCertLength];
                int outputOffset = 0;
                for (int outputOffset2 = 0; outputOffset2 < certsBytes.length; outputOffset2++) {
                    int certLength = certsBytes[outputOffset2].length;
                    System.arraycopy(certsBytes[outputOffset2], 0, chainBytes, outputOffset, certLength);
                    outputOffset += certLength;
                    certsBytes[outputOffset2] = null;
                }
                pkeyAlias = null;
            } else {
                pkeyAlias = null;
                chainBytes = null;
            }
            byte[] chainBytes4 = chainBytes;
            if (key instanceof AndroidKeyStorePrivateKey) {
                pkeyAlias = ((AndroidKeyStoreKey) key).getAlias();
            }
            if (pkeyAlias == null || !pkeyAlias.startsWith(Credentials.USER_PRIVATE_KEY)) {
                String keyFormat = key.getFormat();
                if (keyFormat == null || !"PKCS#8".equals(keyFormat)) {
                    throw new KeyStoreException("Unsupported private key export format: " + keyFormat + ". Only private keys which export their key material in PKCS#8 format are supported.");
                }
                byte[] pkcs8EncodedPrivateKeyBytes2 = key.getEncoded();
                if (pkcs8EncodedPrivateKeyBytes2 == null) {
                    throw new KeyStoreException("Private key did not export any key material");
                }
                KeymasterArguments importArgs2 = new KeymasterArguments();
                try {
                    importArgs2.addEnum(KeymasterDefs.KM_TAG_ALGORITHM, KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(key.getAlgorithm()));
                    purposes = spec.getPurposes();
                    importArgs2.addEnums(KeymasterDefs.KM_TAG_PURPOSE, KeyProperties.Purpose.allToKeymaster(purposes));
                    if (spec.isDigestsSpecified()) {
                        try {
                            importArgs2.addEnums(KeymasterDefs.KM_TAG_DIGEST, KeyProperties.Digest.allToKeymaster(spec.getDigests()));
                        } catch (IllegalArgumentException | IllegalStateException e2) {
                            e = e2;
                            throw new KeyStoreException(e);
                        }
                    }
                    importArgs2.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, KeyProperties.BlockMode.allToKeymaster(spec.getBlockModes()));
                    keymasterEncryptionPaddings = KeyProperties.EncryptionPadding.allToKeymaster(spec.getEncryptionPaddings());
                } catch (IllegalArgumentException | IllegalStateException e3) {
                    e = e3;
                }
                try {
                    if ((purposes & 1) != 0) {
                        try {
                            if (spec.isRandomizedEncryptionRequired()) {
                                for (int keymasterPadding : keymasterEncryptionPaddings) {
                                    if (!KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(keymasterPadding)) {
                                        StringBuilder sb = new StringBuilder();
                                        try {
                                            sb.append("Randomized encryption (IND-CPA) required but is violated by encryption padding mode: ");
                                            sb.append(KeyProperties.EncryptionPadding.fromKeymaster(keymasterPadding));
                                            sb.append(". See KeyProtection documentation.");
                                            throw new KeyStoreException(sb.toString());
                                        } catch (IllegalArgumentException | IllegalStateException e4) {
                                            e = e4;
                                            throw new KeyStoreException(e);
                                        }
                                    }
                                }
                                importArgs2.addEnums(KeymasterDefs.KM_TAG_PADDING, keymasterEncryptionPaddings);
                                importArgs2.addEnums(KeymasterDefs.KM_TAG_PADDING, KeyProperties.SignaturePadding.allToKeymaster(spec.getSignaturePaddings()));
                                KeymasterUtils.addUserAuthArgs(importArgs2, spec);
                                importArgs2.addDateIfNotNull(KeymasterDefs.KM_TAG_ACTIVE_DATETIME, spec.getKeyValidityStart());
                                importArgs2.addDateIfNotNull(KeymasterDefs.KM_TAG_ORIGINATION_EXPIRE_DATETIME, spec.getKeyValidityForOriginationEnd());
                                importArgs2.addDateIfNotNull(KeymasterDefs.KM_TAG_USAGE_EXPIRE_DATETIME, spec.getKeyValidityForConsumptionEnd());
                                shouldReplacePrivateKey = true;
                                pkcs8EncodedPrivateKeyBytes = pkcs8EncodedPrivateKeyBytes2;
                                importArgs = importArgs2;
                            }
                        } catch (IllegalArgumentException | IllegalStateException e5) {
                            e = e5;
                            throw new KeyStoreException(e);
                        }
                    }
                    importArgs2.addEnums(KeymasterDefs.KM_TAG_PADDING, keymasterEncryptionPaddings);
                    importArgs2.addEnums(KeymasterDefs.KM_TAG_PADDING, KeyProperties.SignaturePadding.allToKeymaster(spec.getSignaturePaddings()));
                    KeymasterUtils.addUserAuthArgs(importArgs2, spec);
                    importArgs2.addDateIfNotNull(KeymasterDefs.KM_TAG_ACTIVE_DATETIME, spec.getKeyValidityStart());
                    importArgs2.addDateIfNotNull(KeymasterDefs.KM_TAG_ORIGINATION_EXPIRE_DATETIME, spec.getKeyValidityForOriginationEnd());
                    importArgs2.addDateIfNotNull(KeymasterDefs.KM_TAG_USAGE_EXPIRE_DATETIME, spec.getKeyValidityForConsumptionEnd());
                    shouldReplacePrivateKey = true;
                    pkcs8EncodedPrivateKeyBytes = pkcs8EncodedPrivateKeyBytes2;
                    importArgs = importArgs2;
                } catch (IllegalArgumentException | IllegalStateException e6) {
                    e = e6;
                    throw new KeyStoreException(e);
                }
            } else {
                String keySubalias = pkeyAlias.substring(Credentials.USER_PRIVATE_KEY.length());
                if (!alias.equals(keySubalias)) {
                    throw new KeyStoreException("Can only replace keys with same alias: " + alias + " != " + keySubalias);
                }
                pkcs8EncodedPrivateKeyBytes = null;
                shouldReplacePrivateKey = false;
                importArgs = null;
            }
            if (shouldReplacePrivateKey) {
                try {
                    Credentials.deleteAllTypesForAlias(this.mKeyStore, alias, this.mUid);
                    resultingKeyCharacteristics = new KeyCharacteristics();
                    chainBytes2 = chainBytes4;
                    chainBytes3 = pkcs8EncodedPrivateKeyBytes;
                    userCertBytes = userCertBytes2;
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    int errorCode = this.mKeyStore.importKey(Credentials.USER_PRIVATE_KEY + alias, importArgs, 1, chainBytes3, this.mUid, flags, resultingKeyCharacteristics);
                    i = 1;
                    if (errorCode != 1) {
                        throw new KeyStoreException("Failed to store private key", android.security.KeyStore.getKeyStoreException(errorCode));
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (0 == 0) {
                    }
                    throw th;
                }
            } else {
                chainBytes2 = chainBytes4;
                userCertBytes = userCertBytes2;
                i = 1;
                try {
                    Credentials.deleteCertificateTypesForAlias(this.mKeyStore, alias, this.mUid);
                    Credentials.deleteLegacyKeyForAlias(this.mKeyStore, alias, this.mUid);
                } catch (Throwable th3) {
                    th = th3;
                    if (0 == 0) {
                        if (shouldReplacePrivateKey) {
                            Credentials.deleteAllTypesForAlias(this.mKeyStore, alias, this.mUid);
                        } else {
                            Credentials.deleteCertificateTypesForAlias(this.mKeyStore, alias, this.mUid);
                            Credentials.deleteLegacyKeyForAlias(this.mKeyStore, alias, this.mUid);
                        }
                    }
                    throw th;
                }
            }
            try {
                int errorCode2 = this.mKeyStore.insert(Credentials.USER_CERTIFICATE + alias, userCertBytes, this.mUid, flags);
                try {
                    if (errorCode2 != i) {
                        throw new KeyStoreException("Failed to store certificate #0", android.security.KeyStore.getKeyStoreException(errorCode2));
                    }
                    int errorCode3 = this.mKeyStore.insert(Credentials.CA_CERTIFICATE + alias, chainBytes2, this.mUid, flags);
                    if (errorCode3 != i) {
                        throw new KeyStoreException("Failed to store certificate chain", android.security.KeyStore.getKeyStoreException(errorCode3));
                    }
                    if (1 == 0) {
                        if (shouldReplacePrivateKey) {
                            Credentials.deleteAllTypesForAlias(this.mKeyStore, alias, this.mUid);
                            return;
                        }
                        Credentials.deleteCertificateTypesForAlias(this.mKeyStore, alias, this.mUid);
                        Credentials.deleteLegacyKeyForAlias(this.mKeyStore, alias, this.mUid);
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (0 == 0) {
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (CertificateEncodingException e7) {
            throw new KeyStoreException("Failed to encode certificate #0", e7);
        }
    }

    private void setSecretKeyEntry(String entryAlias, SecretKey key, KeyStore.ProtectionParameter param) throws KeyStoreException {
        int[] keymasterDigests;
        if (param != null && !(param instanceof KeyProtection)) {
            throw new KeyStoreException("Unsupported protection parameter class: " + param.getClass().getName() + ". Supported: " + KeyProtection.class.getName());
        }
        KeyProtection params = (KeyProtection) param;
        if (key instanceof AndroidKeyStoreSecretKey) {
            String keyAliasInKeystore = ((AndroidKeyStoreSecretKey) key).getAlias();
            if (keyAliasInKeystore == null) {
                throw new KeyStoreException("KeyStore-backed secret key does not have an alias");
            }
            String keyAliasPrefix = Credentials.USER_PRIVATE_KEY;
            if (!keyAliasInKeystore.startsWith(Credentials.USER_PRIVATE_KEY)) {
                keyAliasPrefix = Credentials.USER_SECRET_KEY;
                if (!keyAliasInKeystore.startsWith(Credentials.USER_SECRET_KEY)) {
                    throw new KeyStoreException("KeyStore-backed secret key has invalid alias: " + keyAliasInKeystore);
                }
            }
            String keyEntryAlias = keyAliasInKeystore.substring(keyAliasPrefix.length());
            if (!entryAlias.equals(keyEntryAlias)) {
                throw new KeyStoreException("Can only replace KeyStore-backed keys with same alias: " + entryAlias + " != " + keyEntryAlias);
            } else if (params != null) {
                throw new KeyStoreException("Modifying KeyStore-backed key using protection parameters not supported");
            }
        } else if (params == null) {
            throw new KeyStoreException("Protection parameters must be specified when importing a symmetric key");
        } else {
            String keyExportFormat = key.getFormat();
            if (keyExportFormat == null) {
                throw new KeyStoreException("Only secret keys that export their key material are supported");
            }
            if (!"RAW".equals(keyExportFormat)) {
                throw new KeyStoreException("Unsupported secret key material export format: " + keyExportFormat);
            }
            byte[] keyMaterial = key.getEncoded();
            if (keyMaterial == null) {
                throw new KeyStoreException("Key did not export its key material despite supporting RAW format export");
            }
            KeymasterArguments args = new KeymasterArguments();
            try {
                int keymasterAlgorithm = KeyProperties.KeyAlgorithm.toKeymasterSecretKeyAlgorithm(key.getAlgorithm());
                args.addEnum(KeymasterDefs.KM_TAG_ALGORITHM, keymasterAlgorithm);
                try {
                    if (keymasterAlgorithm == 128) {
                        int keymasterImpliedDigest = KeyProperties.KeyAlgorithm.toKeymasterDigest(key.getAlgorithm());
                        if (keymasterImpliedDigest == -1) {
                            throw new ProviderException("HMAC key algorithm digest unknown for key algorithm " + key.getAlgorithm());
                        }
                        keymasterDigests = new int[]{keymasterImpliedDigest};
                        if (params.isDigestsSpecified()) {
                            int[] keymasterDigestsFromParams = KeyProperties.Digest.allToKeymaster(params.getDigests());
                            if (keymasterDigestsFromParams.length != 1 || keymasterDigestsFromParams[0] != keymasterImpliedDigest) {
                                throw new KeyStoreException("Unsupported digests specification: " + Arrays.asList(params.getDigests()) + ". Only " + KeyProperties.Digest.fromKeymaster(keymasterImpliedDigest) + " supported for HMAC key algorithm " + key.getAlgorithm());
                            }
                        }
                    } else if (params.isDigestsSpecified()) {
                        keymasterDigests = KeyProperties.Digest.allToKeymaster(params.getDigests());
                    } else {
                        keymasterDigests = EmptyArray.INT;
                    }
                    int[] keymasterDigests2 = keymasterDigests;
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, keymasterDigests2);
                    int purposes = params.getPurposes();
                    int[] keymasterBlockModes = KeyProperties.BlockMode.allToKeymaster(params.getBlockModes());
                    if ((purposes & 1) != 0 && params.isRandomizedEncryptionRequired()) {
                        for (int keymasterBlockMode : keymasterBlockModes) {
                            if (!KeymasterUtils.isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(keymasterBlockMode)) {
                                throw new KeyStoreException("Randomized encryption (IND-CPA) required but may be violated by block mode: " + KeyProperties.BlockMode.fromKeymaster(keymasterBlockMode) + ". See KeyProtection documentation.");
                            }
                        }
                    }
                    args.addEnums(KeymasterDefs.KM_TAG_PURPOSE, KeyProperties.Purpose.allToKeymaster(purposes));
                    args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, keymasterBlockModes);
                    if (params.getSignaturePaddings().length <= 0) {
                        int[] keymasterPaddings = KeyProperties.EncryptionPadding.allToKeymaster(params.getEncryptionPaddings());
                        args.addEnums(KeymasterDefs.KM_TAG_PADDING, keymasterPaddings);
                        KeymasterUtils.addUserAuthArgs(args, params);
                        KeymasterUtils.addMinMacLengthAuthorizationIfNecessary(args, keymasterAlgorithm, keymasterBlockModes, keymasterDigests2);
                        args.addDateIfNotNull(KeymasterDefs.KM_TAG_ACTIVE_DATETIME, params.getKeyValidityStart());
                        args.addDateIfNotNull(KeymasterDefs.KM_TAG_ORIGINATION_EXPIRE_DATETIME, params.getKeyValidityForOriginationEnd());
                        args.addDateIfNotNull(KeymasterDefs.KM_TAG_USAGE_EXPIRE_DATETIME, params.getKeyValidityForConsumptionEnd());
                        if ((purposes & 1) != 0 && !params.isRandomizedEncryptionRequired()) {
                            args.addBoolean(KeymasterDefs.KM_TAG_CALLER_NONCE);
                        }
                        int flags = 0;
                        if (params.isCriticalToDeviceEncryption()) {
                            flags = 0 | 8;
                        }
                        if (params.isStrongBoxBacked()) {
                            flags |= 16;
                        }
                        Credentials.deleteAllTypesForAlias(this.mKeyStore, entryAlias, this.mUid);
                        int errorCode = this.mKeyStore.importKey(Credentials.USER_PRIVATE_KEY + entryAlias, args, 3, keyMaterial, this.mUid, flags, new KeyCharacteristics());
                        if (errorCode != 1) {
                            throw new KeyStoreException("Failed to import secret key. Keystore error code: " + errorCode);
                        }
                        return;
                    }
                    try {
                        throw new KeyStoreException("Signature paddings not supported for symmetric keys");
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        e = e;
                        throw new KeyStoreException(e);
                    }
                } catch (IllegalArgumentException | IllegalStateException e2) {
                    e = e2;
                }
            } catch (IllegalArgumentException | IllegalStateException e3) {
                e = e3;
            }
        }
    }

    private void setWrappedKeyEntry(String alias, WrappedKeyEntry entry, KeyStore.ProtectionParameter param) throws KeyStoreException {
        if (param != null) {
            throw new KeyStoreException("Protection parameters are specified inside wrapped keys");
        }
        byte[] maskingKey = new byte[32];
        KeymasterArguments args = new KeymasterArguments();
        String[] parts = entry.getTransformation().split("/");
        String algorithm = parts[0];
        if (KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(algorithm)) {
            args.addEnum(KeymasterDefs.KM_TAG_ALGORITHM, 1);
        } else if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(algorithm)) {
            args.addEnum(KeymasterDefs.KM_TAG_ALGORITHM, 1);
        }
        if (parts.length > 1) {
            String mode = parts[1];
            if (KeyProperties.BLOCK_MODE_ECB.equalsIgnoreCase(mode)) {
                args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, 1);
            } else if (KeyProperties.BLOCK_MODE_CBC.equalsIgnoreCase(mode)) {
                args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, 2);
            } else if (KeyProperties.BLOCK_MODE_CTR.equalsIgnoreCase(mode)) {
                args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, 3);
            } else if (KeyProperties.BLOCK_MODE_GCM.equalsIgnoreCase(mode)) {
                args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, 32);
            }
        }
        if (parts.length > 2) {
            String padding = parts[2];
            if (!KeyProperties.ENCRYPTION_PADDING_NONE.equalsIgnoreCase(padding)) {
                if (KeyProperties.ENCRYPTION_PADDING_PKCS7.equalsIgnoreCase(padding)) {
                    args.addEnums(KeymasterDefs.KM_TAG_PADDING, 64);
                } else if (KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1.equalsIgnoreCase(padding)) {
                    args.addEnums(KeymasterDefs.KM_TAG_PADDING, 4);
                } else if (KeyProperties.ENCRYPTION_PADDING_RSA_OAEP.equalsIgnoreCase(padding)) {
                    args.addEnums(KeymasterDefs.KM_TAG_PADDING, 2);
                }
            }
        }
        KeyGenParameterSpec spec = (KeyGenParameterSpec) entry.getAlgorithmParameterSpec();
        if (spec.isDigestsSpecified()) {
            String digest = spec.getDigests()[0];
            if (!KeyProperties.DIGEST_NONE.equalsIgnoreCase(digest)) {
                if (KeyProperties.DIGEST_MD5.equalsIgnoreCase(digest)) {
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, 1);
                } else if (KeyProperties.DIGEST_SHA1.equalsIgnoreCase(digest)) {
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, 2);
                } else if (KeyProperties.DIGEST_SHA224.equalsIgnoreCase(digest)) {
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, 3);
                } else if (KeyProperties.DIGEST_SHA256.equalsIgnoreCase(digest)) {
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, 4);
                } else if (KeyProperties.DIGEST_SHA384.equalsIgnoreCase(digest)) {
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, 5);
                } else if (KeyProperties.DIGEST_SHA512.equalsIgnoreCase(digest)) {
                    args.addEnums(KeymasterDefs.KM_TAG_DIGEST, 6);
                }
            }
        }
        int errorCode = this.mKeyStore.importWrappedKey(Credentials.USER_PRIVATE_KEY + alias, entry.getWrappedKeyBytes(), Credentials.USER_PRIVATE_KEY + entry.getWrappingKeyAlias(), maskingKey, args, GateKeeper.getSecureUserId(), 0L, this.mUid, new KeyCharacteristics());
        if (errorCode == -100) {
            throw new SecureKeyImportUnavailableException("Could not import wrapped key");
        }
        if (errorCode != 1) {
            throw new KeyStoreException("Failed to import wrapped key. Keystore error code: " + errorCode);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String alias, byte[] userKey, Certificate[] chain) throws KeyStoreException {
        throw new KeyStoreException("Operation not supported because key encoding is unknown");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String alias, Certificate cert) throws KeyStoreException {
        if (isKeyEntry(alias)) {
            throw new KeyStoreException("Entry exists and is not a trusted certificate");
        }
        if (cert == null) {
            throw new NullPointerException("cert == null");
        }
        try {
            byte[] encoded = cert.getEncoded();
            android.security.KeyStore keyStore = this.mKeyStore;
            if (!keyStore.put(Credentials.CA_CERTIFICATE + alias, encoded, this.mUid, 0)) {
                throw new KeyStoreException("Couldn't insert certificate; is KeyStore initialized?");
            }
        } catch (CertificateEncodingException e) {
            throw new KeyStoreException(e);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String alias) throws KeyStoreException {
        if (!Credentials.deleteAllTypesForAlias(this.mKeyStore, alias, this.mUid)) {
            throw new KeyStoreException("Failed to delete entry: " + alias);
        }
    }

    private Set<String> getUniqueAliases() {
        String[] rawAliases = this.mKeyStore.list("", this.mUid);
        if (rawAliases == null) {
            return new HashSet();
        }
        Set<String> aliases = new HashSet<>(rawAliases.length);
        for (String alias : rawAliases) {
            int idx = alias.indexOf(95);
            if (idx == -1 || alias.length() <= idx) {
                Log.m70e("AndroidKeyStore", "invalid alias: " + alias);
            } else {
                aliases.add(new String(alias.substring(idx + 1)));
            }
        }
        return aliases;
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration<String> engineAliases() {
        return Collections.enumeration(getUniqueAliases());
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String alias) {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        android.security.KeyStore keyStore = this.mKeyStore;
        if (!keyStore.contains(Credentials.USER_PRIVATE_KEY + alias, this.mUid)) {
            android.security.KeyStore keyStore2 = this.mKeyStore;
            if (!keyStore2.contains(Credentials.USER_SECRET_KEY + alias, this.mUid)) {
                android.security.KeyStore keyStore3 = this.mKeyStore;
                if (!keyStore3.contains(Credentials.USER_CERTIFICATE + alias, this.mUid)) {
                    android.security.KeyStore keyStore4 = this.mKeyStore;
                    if (!keyStore4.contains(Credentials.CA_CERTIFICATE + alias, this.mUid)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return getUniqueAliases().size();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String alias) {
        return isKeyEntry(alias);
    }

    private boolean isKeyEntry(String alias) {
        android.security.KeyStore keyStore = this.mKeyStore;
        if (!keyStore.contains(Credentials.USER_PRIVATE_KEY + alias, this.mUid)) {
            android.security.KeyStore keyStore2 = this.mKeyStore;
            if (!keyStore2.contains(Credentials.USER_SECRET_KEY + alias, this.mUid)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCertificateEntry(String alias) {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        android.security.KeyStore keyStore = this.mKeyStore;
        return keyStore.contains(Credentials.CA_CERTIFICATE + alias, this.mUid);
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String alias) {
        return !isKeyEntry(alias) && isCertificateEntry(alias);
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate cert) {
        if (cert == null || !"X.509".equalsIgnoreCase(cert.getType())) {
            return null;
        }
        try {
            byte[] targetCertBytes = cert.getEncoded();
            if (targetCertBytes == null) {
                return null;
            }
            Set<String> nonCaEntries = new HashSet<>();
            String[] certAliases = this.mKeyStore.list(Credentials.USER_CERTIFICATE, this.mUid);
            if (certAliases != null) {
                for (String alias : certAliases) {
                    byte[] certBytes = this.mKeyStore.get(Credentials.USER_CERTIFICATE + alias, this.mUid);
                    if (certBytes != null) {
                        nonCaEntries.add(alias);
                        if (Arrays.equals(certBytes, targetCertBytes)) {
                            return alias;
                        }
                    }
                }
            }
            String[] caAliases = this.mKeyStore.list(Credentials.CA_CERTIFICATE, this.mUid);
            if (certAliases != null) {
                for (String alias2 : caAliases) {
                    if (!nonCaEntries.contains(alias2)) {
                        byte[] certBytes2 = this.mKeyStore.get(Credentials.CA_CERTIFICATE + alias2, this.mUid);
                        if (certBytes2 != null && Arrays.equals(certBytes2, targetCertBytes)) {
                            return alias2;
                        }
                    }
                }
            }
            return null;
        } catch (CertificateEncodingException e) {
            return null;
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {
        throw new UnsupportedOperationException("Can not serialize AndroidKeyStore to OutputStream");
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {
        if (stream != null) {
            throw new IllegalArgumentException("InputStream not supported");
        }
        if (password != null) {
            throw new IllegalArgumentException("password not supported");
        }
        this.mKeyStore = android.security.KeyStore.getInstance();
        this.mUid = -1;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(KeyStore.LoadStoreParameter param) throws IOException, NoSuchAlgorithmException, CertificateException {
        int uid = -1;
        if (param != null) {
            if (param instanceof AndroidKeyStoreLoadStoreParameter) {
                uid = ((AndroidKeyStoreLoadStoreParameter) param).getUid();
            } else {
                throw new IllegalArgumentException("Unsupported param type: " + param.getClass());
            }
        }
        this.mKeyStore = android.security.KeyStore.getInstance();
        this.mUid = uid;
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetEntry(String alias, KeyStore.Entry entry, KeyStore.ProtectionParameter param) throws KeyStoreException {
        if (entry == null) {
            throw new KeyStoreException("entry == null");
        }
        Credentials.deleteAllTypesForAlias(this.mKeyStore, alias, this.mUid);
        if (entry instanceof KeyStore.TrustedCertificateEntry) {
            KeyStore.TrustedCertificateEntry trE = (KeyStore.TrustedCertificateEntry) entry;
            engineSetCertificateEntry(alias, trE.getTrustedCertificate());
        } else if (entry instanceof KeyStore.PrivateKeyEntry) {
            KeyStore.PrivateKeyEntry prE = (KeyStore.PrivateKeyEntry) entry;
            setPrivateKeyEntry(alias, prE.getPrivateKey(), prE.getCertificateChain(), param);
        } else if (entry instanceof KeyStore.SecretKeyEntry) {
            KeyStore.SecretKeyEntry secE = (KeyStore.SecretKeyEntry) entry;
            setSecretKeyEntry(alias, secE.getSecretKey(), param);
        } else if (entry instanceof WrappedKeyEntry) {
            WrappedKeyEntry wke = (WrappedKeyEntry) entry;
            setWrappedKeyEntry(alias, wke, param);
        } else {
            throw new KeyStoreException("Entry must be a PrivateKeyEntry, SecretKeyEntry or TrustedCertificateEntry; was " + entry);
        }
    }

    /* loaded from: classes3.dex */
    static class KeyStoreX509Certificate extends DelegatingX509Certificate {
        private final String mPrivateKeyAlias;
        private final int mPrivateKeyUid;

        KeyStoreX509Certificate(String privateKeyAlias, int privateKeyUid, X509Certificate delegate) {
            super(delegate);
            this.mPrivateKeyAlias = privateKeyAlias;
            this.mPrivateKeyUid = privateKeyUid;
        }

        @Override // android.security.keystore.DelegatingX509Certificate, java.security.cert.Certificate
        public PublicKey getPublicKey() {
            PublicKey original = super.getPublicKey();
            return AndroidKeyStoreProvider.getAndroidKeyStorePublicKey(this.mPrivateKeyAlias, this.mPrivateKeyUid, original.getAlgorithm(), original.getEncoded());
        }
    }
}
