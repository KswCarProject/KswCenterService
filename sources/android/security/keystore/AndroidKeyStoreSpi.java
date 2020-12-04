package android.security.keystore;

import android.security.Credentials;
import android.security.GateKeeper;
import android.security.KeyStore;
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

public class AndroidKeyStoreSpi extends KeyStoreSpi {
    public static final String NAME = "AndroidKeyStore";
    private KeyStore mKeyStore;
    private int mUid = -1;

    public Key engineGetKey(String alias, char[] password) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        String userKeyAlias = Credentials.USER_PRIVATE_KEY + alias;
        if (!this.mKeyStore.contains(userKeyAlias, this.mUid)) {
            userKeyAlias = Credentials.USER_SECRET_KEY + alias;
            if (!this.mKeyStore.contains(userKeyAlias, this.mUid)) {
                return null;
            }
        }
        try {
            return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore, userKeyAlias, this.mUid);
        } catch (KeyPermanentlyInvalidatedException e) {
            throw new UnrecoverableKeyException(e.getMessage());
        }
    }

    public Certificate[] engineGetCertificateChain(String alias) {
        Certificate[] caList;
        if (alias != null) {
            X509Certificate leaf = (X509Certificate) engineGetCertificate(alias);
            if (leaf == null) {
                return null;
            }
            KeyStore keyStore = this.mKeyStore;
            int i = 1;
            byte[] caBytes = keyStore.get(Credentials.CA_CERTIFICATE + alias, this.mUid, true);
            if (caBytes != null) {
                Collection<X509Certificate> caChain = toCertificates(caBytes);
                caList = new Certificate[(caChain.size() + 1)];
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
        throw new NullPointerException("alias == null");
    }

    public Certificate engineGetCertificate(String alias) {
        if (alias != null) {
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
        throw new NullPointerException("alias == null");
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
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bytes));
        } catch (CertificateException e) {
            Log.w("AndroidKeyStore", "Couldn't parse certificate in keystore", e);
            return null;
        }
    }

    private static Collection<X509Certificate> toCertificates(byte[] bytes) {
        try {
            return CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bytes));
        } catch (CertificateException e) {
            Log.w("AndroidKeyStore", "Couldn't parse certificates in keystore", e);
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

    public Date engineGetCreationDate(String alias) {
        if (alias != null) {
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
        throw new NullPointerException("alias == null");
    }

    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) throws KeyStoreException {
        if (password != null && password.length > 0) {
            throw new KeyStoreException("entries cannot be protected with passwords");
        } else if (key instanceof PrivateKey) {
            setPrivateKeyEntry(alias, (PrivateKey) key, chain, (KeyStore.ProtectionParameter) null);
        } else if (key instanceof SecretKey) {
            setSecretKeyEntry(alias, (SecretKey) key, (KeyStore.ProtectionParameter) null);
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

    /* JADX WARNING: Removed duplicated region for block: B:151:0x032c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setPrivateKeyEntry(java.lang.String r26, java.security.PrivateKey r27, java.security.cert.Certificate[] r28, java.security.KeyStore.ProtectionParameter r29) throws java.security.KeyStoreException {
        /*
            r25 = this;
            r1 = r25
            r2 = r26
            r3 = r27
            r4 = r28
            r5 = r29
            r0 = 0
            if (r5 != 0) goto L_0x0013
            android.security.keystore.KeyProtection r6 = getLegacyKeyProtectionParameter(r27)
        L_0x0011:
            r15 = r0
            goto L_0x003e
        L_0x0013:
            boolean r6 = r5 instanceof android.security.KeyStoreParameter
            if (r6 == 0) goto L_0x0026
            android.security.keystore.KeyProtection r6 = getLegacyKeyProtectionParameter(r27)
            r7 = r5
            android.security.KeyStoreParameter r7 = (android.security.KeyStoreParameter) r7
            boolean r8 = r7.isEncryptionRequired()
            if (r8 == 0) goto L_0x0025
            r0 = 1
        L_0x0025:
            goto L_0x0011
        L_0x0026:
            boolean r6 = r5 instanceof android.security.keystore.KeyProtection
            if (r6 == 0) goto L_0x03a7
            r6 = r5
            android.security.keystore.KeyProtection r6 = (android.security.keystore.KeyProtection) r6
            boolean r7 = r6.isCriticalToDeviceEncryption()
            if (r7 == 0) goto L_0x0035
            r0 = r0 | 8
        L_0x0035:
            boolean r7 = r6.isStrongBoxBacked()
            if (r7 == 0) goto L_0x0011
            r0 = r0 | 16
            goto L_0x0011
        L_0x003e:
            if (r4 == 0) goto L_0x039f
            int r0 = r4.length
            if (r0 == 0) goto L_0x039f
            int r0 = r4.length
            java.security.cert.X509Certificate[] r13 = new java.security.cert.X509Certificate[r0]
            r0 = 0
            r7 = r0
        L_0x0049:
            int r8 = r4.length
            if (r7 >= r8) goto L_0x0097
            java.lang.String r8 = "X.509"
            r9 = r4[r7]
            java.lang.String r9 = r9.getType()
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0080
            r8 = r4[r7]
            boolean r8 = r8 instanceof java.security.cert.X509Certificate
            if (r8 == 0) goto L_0x0069
            r8 = r4[r7]
            java.security.cert.X509Certificate r8 = (java.security.cert.X509Certificate) r8
            r13[r7] = r8
            int r7 = r7 + 1
            goto L_0x0049
        L_0x0069:
            java.security.KeyStoreException r0 = new java.security.KeyStoreException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Certificates must be in X.509 format: invalid cert #"
            r8.append(r9)
            r8.append(r7)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x0080:
            java.security.KeyStoreException r0 = new java.security.KeyStoreException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Certificates must be in X.509 format: invalid cert #"
            r8.append(r9)
            r8.append(r7)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x0097:
            r7 = r13[r0]     // Catch:{ CertificateEncodingException -> 0x0394 }
            byte[] r7 = r7.getEncoded()     // Catch:{ CertificateEncodingException -> 0x0394 }
            r12 = r7
            int r7 = r4.length
            r11 = 1
            if (r7 <= r11) goto L_0x00f3
            int r7 = r13.length
            int r7 = r7 - r11
            byte[][] r7 = new byte[r7][]
            r9 = 0
            r10 = r9
            r9 = r0
        L_0x00ab:
            int r14 = r7.length
            if (r9 >= r14) goto L_0x00d8
            int r14 = r9 + 1
            r14 = r13[r14]     // Catch:{ CertificateEncodingException -> 0x00c0 }
            byte[] r14 = r14.getEncoded()     // Catch:{ CertificateEncodingException -> 0x00c0 }
            r7[r9] = r14     // Catch:{ CertificateEncodingException -> 0x00c0 }
            r14 = r7[r9]     // Catch:{ CertificateEncodingException -> 0x00c0 }
            int r14 = r14.length     // Catch:{ CertificateEncodingException -> 0x00c0 }
            int r10 = r10 + r14
            int r9 = r9 + 1
            goto L_0x00ab
        L_0x00c0:
            r0 = move-exception
            java.security.KeyStoreException r8 = new java.security.KeyStoreException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r14 = "Failed to encode certificate #"
            r11.append(r14)
            r11.append(r9)
            java.lang.String r11 = r11.toString()
            r8.<init>(r11, r0)
            throw r8
        L_0x00d8:
            byte[] r9 = new byte[r10]
            r14 = 0
            r11 = r14
            r14 = r0
        L_0x00dd:
            int r8 = r7.length
            if (r14 >= r8) goto L_0x00f1
            r8 = r7[r14]
            int r8 = r8.length
            r4 = r7[r14]
            java.lang.System.arraycopy(r4, r0, r9, r11, r8)
            int r11 = r11 + r8
            r4 = 0
            r7[r14] = r4
            int r14 = r14 + 1
            r4 = r28
            goto L_0x00dd
        L_0x00f1:
            r4 = 0
            goto L_0x00f5
        L_0x00f3:
            r4 = 0
            r9 = r4
        L_0x00f5:
            r11 = r9
            boolean r7 = r3 instanceof android.security.keystore.AndroidKeyStorePrivateKey
            if (r7 == 0) goto L_0x0103
            r4 = r3
            android.security.keystore.AndroidKeyStoreKey r4 = (android.security.keystore.AndroidKeyStoreKey) r4
            java.lang.String r8 = r4.getAlias()
            r4 = r8
        L_0x0103:
            if (r4 == 0) goto L_0x0148
            java.lang.String r7 = "USRPKEY_"
            boolean r7 = r4.startsWith(r7)
            if (r7 == 0) goto L_0x0148
            java.lang.String r7 = "USRPKEY_"
            int r7 = r7.length()
            java.lang.String r7 = r4.substring(r7)
            boolean r8 = r2.equals(r7)
            if (r8 == 0) goto L_0x0129
            r8 = 0
            r9 = 0
            r7 = 0
            r21 = r4
            r17 = r7
            r3 = r8
            r4 = r9
            goto L_0x0242
        L_0x0129:
            java.security.KeyStoreException r0 = new java.security.KeyStoreException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Can only replace keys with same alias: "
            r8.append(r9)
            r8.append(r2)
            java.lang.String r9 = " != "
            r8.append(r9)
            r8.append(r7)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x0148:
            r8 = 1
            java.lang.String r7 = r27.getFormat()
            if (r7 == 0) goto L_0x036f
            java.lang.String r9 = "PKCS#8"
            boolean r9 = r9.equals(r7)
            if (r9 == 0) goto L_0x036f
            byte[] r9 = r27.getEncoded()
            if (r9 == 0) goto L_0x035e
            android.security.keymaster.KeymasterArguments r10 = new android.security.keymaster.KeymasterArguments
            r10.<init>()
            r14 = 268435458(0x10000002, float:2.5243555E-29)
            java.lang.String r17 = r27.getAlgorithm()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            int r0 = android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(r17)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            r10.addEnum(r14, r0)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            int r0 = r6.getPurposes()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            int[] r14 = android.security.keystore.KeyProperties.Purpose.allToKeymaster(r0)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            r3 = 536870913(0x20000001, float:1.0842023E-19)
            r10.addEnums(r3, r14)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            boolean r3 = r6.isDigestsSpecified()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            if (r3 == 0) goto L_0x01a0
            r3 = 536870917(0x20000005, float:1.0842028E-19)
            java.lang.String[] r14 = r6.getDigests()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0194 }
            int[] r14 = android.security.keystore.KeyProperties.Digest.allToKeymaster(r14)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0194 }
            r10.addEnums(r3, r14)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0194 }
            goto L_0x01a0
        L_0x0194:
            r0 = move-exception
            r21 = r4
        L_0x0197:
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
            goto L_0x0358
        L_0x01a0:
            r3 = 536870916(0x20000004, float:1.0842027E-19)
            java.lang.String[] r14 = r6.getBlockModes()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            int[] r14 = android.security.keystore.KeyProperties.BlockMode.allToKeymaster(r14)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            r10.addEnums(r3, r14)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            java.lang.String[] r3 = r6.getEncryptionPaddings()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            int[] r3 = android.security.keystore.KeyProperties.EncryptionPadding.allToKeymaster(r3)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x034e }
            r14 = r0 & 1
            if (r14 == 0) goto L_0x0206
            boolean r14 = r6.isRandomizedEncryptionRequired()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            if (r14 == 0) goto L_0x0206
            int r14 = r3.length     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            r20 = r0
            r0 = 0
        L_0x01c5:
            if (r0 >= r14) goto L_0x01f7
            r17 = r3[r0]     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            boolean r19 = android.security.keystore.KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(r17)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            if (r19 == 0) goto L_0x01d3
            int r0 = r0 + 1
            goto L_0x01c5
        L_0x01d3:
            java.security.KeyStoreException r0 = new java.security.KeyStoreException     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            r14.<init>()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01fa }
            r21 = r4
            java.lang.String r4 = "Randomized encryption (IND-CPA) required but is violated by encryption padding mode: "
            r14.append(r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
            java.lang.String r4 = android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(r17)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
            r14.append(r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
            java.lang.String r4 = ". See KeyProtection documentation."
            r14.append(r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
            java.lang.String r4 = r14.toString()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
            r0.<init>(r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
            throw r0     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x01f5 }
        L_0x01f5:
            r0 = move-exception
            goto L_0x0197
        L_0x01f7:
            r21 = r4
            goto L_0x020a
        L_0x01fa:
            r0 = move-exception
            r21 = r4
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
            goto L_0x0358
        L_0x0206:
            r20 = r0
            r21 = r4
        L_0x020a:
            r0 = 536870918(0x20000006, float:1.084203E-19)
            r10.addEnums(r0, r3)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            java.lang.String[] r4 = r6.getSignaturePaddings()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            int[] r4 = android.security.keystore.KeyProperties.SignaturePadding.allToKeymaster(r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r10.addEnums(r0, r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            android.security.keystore.KeymasterUtils.addUserAuthArgs(r10, r6)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r0 = 1610613136(0x60000190, float:3.6895247E19)
            java.util.Date r4 = r6.getKeyValidityStart()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r10.addDateIfNotNull(r0, r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r0 = 1610613137(0x60000191, float:3.689525E19)
            java.util.Date r4 = r6.getKeyValidityForOriginationEnd()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r10.addDateIfNotNull(r0, r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r0 = 1610613138(0x60000192, float:3.6895256E19)
            java.util.Date r4 = r6.getKeyValidityForConsumptionEnd()     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r10.addDateIfNotNull(r0, r4)     // Catch:{ IllegalArgumentException | IllegalStateException -> 0x0345 }
            r3 = r8
            r17 = r9
            r4 = r10
        L_0x0242:
            r0 = 0
            r18 = r0
            if (r3 == 0) goto L_0x0299
            android.security.KeyStore r0 = r1.mKeyStore     // Catch:{ all -> 0x028f }
            int r7 = r1.mUid     // Catch:{ all -> 0x028f }
            android.security.Credentials.deleteAllTypesForAlias(r0, r2, r7)     // Catch:{ all -> 0x028f }
            android.security.keymaster.KeyCharacteristics r14 = new android.security.keymaster.KeyCharacteristics     // Catch:{ all -> 0x028f }
            r14.<init>()     // Catch:{ all -> 0x028f }
            android.security.KeyStore r7 = r1.mKeyStore     // Catch:{ all -> 0x028f }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x028f }
            r0.<init>()     // Catch:{ all -> 0x028f }
            java.lang.String r8 = "USRPKEY_"
            r0.append(r8)     // Catch:{ all -> 0x028f }
            r0.append(r2)     // Catch:{ all -> 0x028f }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x028f }
            r10 = 1
            int r0 = r1.mUid     // Catch:{ all -> 0x028f }
            r9 = r4
            r22 = r11
            r11 = r17
            r23 = r12
            r12 = r0
            r16 = r13
            r13 = r15
            int r0 = r7.importKey(r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x0288 }
            r7 = 1
            if (r0 != r7) goto L_0x027c
            goto L_0x02ae
        L_0x027c:
            java.security.KeyStoreException r7 = new java.security.KeyStoreException     // Catch:{ all -> 0x0288 }
            java.lang.String r8 = "Failed to store private key"
            android.security.KeyStoreException r9 = android.security.KeyStore.getKeyStoreException(r0)     // Catch:{ all -> 0x0288 }
            r7.<init>(r8, r9)     // Catch:{ all -> 0x0288 }
            throw r7     // Catch:{ all -> 0x0288 }
        L_0x0288:
            r0 = move-exception
            r12 = r22
            r11 = r23
            goto L_0x032a
        L_0x028f:
            r0 = move-exception
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
            goto L_0x032a
        L_0x0299:
            r22 = r11
            r23 = r12
            r16 = r13
            r7 = 1
            android.security.KeyStore r0 = r1.mKeyStore     // Catch:{ all -> 0x0325 }
            int r8 = r1.mUid     // Catch:{ all -> 0x0325 }
            android.security.Credentials.deleteCertificateTypesForAlias(r0, r2, r8)     // Catch:{ all -> 0x0325 }
            android.security.KeyStore r0 = r1.mKeyStore     // Catch:{ all -> 0x0325 }
            int r8 = r1.mUid     // Catch:{ all -> 0x0325 }
            android.security.Credentials.deleteLegacyKeyForAlias(r0, r2, r8)     // Catch:{ all -> 0x0325 }
        L_0x02ae:
            android.security.KeyStore r0 = r1.mKeyStore     // Catch:{ all -> 0x0325 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0325 }
            r8.<init>()     // Catch:{ all -> 0x0325 }
            java.lang.String r9 = "USRCERT_"
            r8.append(r9)     // Catch:{ all -> 0x0325 }
            r8.append(r2)     // Catch:{ all -> 0x0325 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0325 }
            int r9 = r1.mUid     // Catch:{ all -> 0x0325 }
            r11 = r23
            int r0 = r0.insert(r8, r11, r9, r15)     // Catch:{ all -> 0x0321 }
            if (r0 != r7) goto L_0x0311
            android.security.KeyStore r8 = r1.mKeyStore     // Catch:{ all -> 0x0321 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0321 }
            r9.<init>()     // Catch:{ all -> 0x0321 }
            java.lang.String r10 = "CACERT_"
            r9.append(r10)     // Catch:{ all -> 0x0321 }
            r9.append(r2)     // Catch:{ all -> 0x0321 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0321 }
            int r10 = r1.mUid     // Catch:{ all -> 0x0321 }
            r12 = r22
            int r8 = r8.insert(r9, r12, r10, r15)     // Catch:{ all -> 0x031f }
            r0 = r8
            if (r0 != r7) goto L_0x0305
            r0 = 1
            if (r0 != 0) goto L_0x0304
            if (r3 == 0) goto L_0x02f6
            android.security.KeyStore r7 = r1.mKeyStore
            int r8 = r1.mUid
            android.security.Credentials.deleteAllTypesForAlias(r7, r2, r8)
            goto L_0x0304
        L_0x02f6:
            android.security.KeyStore r7 = r1.mKeyStore
            int r8 = r1.mUid
            android.security.Credentials.deleteCertificateTypesForAlias(r7, r2, r8)
            android.security.KeyStore r7 = r1.mKeyStore
            int r8 = r1.mUid
            android.security.Credentials.deleteLegacyKeyForAlias(r7, r2, r8)
        L_0x0304:
            return
        L_0x0305:
            java.security.KeyStoreException r7 = new java.security.KeyStoreException     // Catch:{ all -> 0x031f }
            java.lang.String r8 = "Failed to store certificate chain"
            android.security.KeyStoreException r9 = android.security.KeyStore.getKeyStoreException(r0)     // Catch:{ all -> 0x031f }
            r7.<init>(r8, r9)     // Catch:{ all -> 0x031f }
            throw r7     // Catch:{ all -> 0x031f }
        L_0x0311:
            r12 = r22
            java.security.KeyStoreException r7 = new java.security.KeyStoreException     // Catch:{ all -> 0x031f }
            java.lang.String r8 = "Failed to store certificate #0"
            android.security.KeyStoreException r9 = android.security.KeyStore.getKeyStoreException(r0)     // Catch:{ all -> 0x031f }
            r7.<init>(r8, r9)     // Catch:{ all -> 0x031f }
            throw r7     // Catch:{ all -> 0x031f }
        L_0x031f:
            r0 = move-exception
            goto L_0x032a
        L_0x0321:
            r0 = move-exception
            r12 = r22
            goto L_0x032a
        L_0x0325:
            r0 = move-exception
            r12 = r22
            r11 = r23
        L_0x032a:
            if (r18 != 0) goto L_0x0344
            if (r3 == 0) goto L_0x0336
            android.security.KeyStore r7 = r1.mKeyStore
            int r8 = r1.mUid
            android.security.Credentials.deleteAllTypesForAlias(r7, r2, r8)
            goto L_0x0344
        L_0x0336:
            android.security.KeyStore r7 = r1.mKeyStore
            int r8 = r1.mUid
            android.security.Credentials.deleteCertificateTypesForAlias(r7, r2, r8)
            android.security.KeyStore r7 = r1.mKeyStore
            int r8 = r1.mUid
            android.security.Credentials.deleteLegacyKeyForAlias(r7, r2, r8)
        L_0x0344:
            throw r0
        L_0x0345:
            r0 = move-exception
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
            goto L_0x0358
        L_0x034e:
            r0 = move-exception
            r21 = r4
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
        L_0x0358:
            java.security.KeyStoreException r3 = new java.security.KeyStoreException
            r3.<init>(r0)
            throw r3
        L_0x035e:
            r21 = r4
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
            java.security.KeyStoreException r0 = new java.security.KeyStoreException
            java.lang.String r3 = "Private key did not export any key material"
            r0.<init>(r3)
            throw r0
        L_0x036f:
            r21 = r4
            r16 = r13
            r24 = r12
            r12 = r11
            r11 = r24
            java.security.KeyStoreException r0 = new java.security.KeyStoreException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unsupported private key export format: "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r4 = ". Only private keys which export their key material in PKCS#8 format are supported."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x0394:
            r0 = move-exception
            r16 = r13
            java.security.KeyStoreException r3 = new java.security.KeyStoreException
            java.lang.String r4 = "Failed to encode certificate #0"
            r3.<init>(r4, r0)
            throw r3
        L_0x039f:
            java.security.KeyStoreException r0 = new java.security.KeyStoreException
            java.lang.String r3 = "Must supply at least one Certificate with PrivateKey"
            r0.<init>(r3)
            throw r0
        L_0x03a7:
            java.security.KeyStoreException r3 = new java.security.KeyStoreException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Unsupported protection parameter class:"
            r4.append(r6)
            java.lang.Class r6 = r29.getClass()
            java.lang.String r6 = r6.getName()
            r4.append(r6)
            java.lang.String r6 = ". Supported: "
            r4.append(r6)
            java.lang.Class<android.security.keystore.KeyProtection> r6 = android.security.keystore.KeyProtection.class
            java.lang.String r6 = r6.getName()
            r4.append(r6)
            java.lang.String r6 = ", "
            r4.append(r6)
            java.lang.Class<android.security.KeyStoreParameter> r6 = android.security.KeyStoreParameter.class
            java.lang.String r6 = r6.getName()
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.AndroidKeyStoreSpi.setPrivateKeyEntry(java.lang.String, java.security.PrivateKey, java.security.cert.Certificate[], java.security.KeyStore$ProtectionParameter):void");
    }

    private void setSecretKeyEntry(String entryAlias, SecretKey key, KeyStore.ProtectionParameter param) throws KeyStoreException {
        int[] keymasterDigests;
        String str = entryAlias;
        SecretKey secretKey = key;
        KeyStore.ProtectionParameter protectionParameter = param;
        if (protectionParameter == null || (protectionParameter instanceof KeyProtection)) {
            KeyProtection params = (KeyProtection) protectionParameter;
            if (secretKey instanceof AndroidKeyStoreSecretKey) {
                String keyAliasInKeystore = ((AndroidKeyStoreSecretKey) secretKey).getAlias();
                if (keyAliasInKeystore != null) {
                    String keyAliasPrefix = Credentials.USER_PRIVATE_KEY;
                    if (!keyAliasInKeystore.startsWith(keyAliasPrefix)) {
                        keyAliasPrefix = Credentials.USER_SECRET_KEY;
                        if (!keyAliasInKeystore.startsWith(keyAliasPrefix)) {
                            throw new KeyStoreException("KeyStore-backed secret key has invalid alias: " + keyAliasInKeystore);
                        }
                    }
                    String keyEntryAlias = keyAliasInKeystore.substring(keyAliasPrefix.length());
                    if (!str.equals(keyEntryAlias)) {
                        throw new KeyStoreException("Can only replace KeyStore-backed keys with same alias: " + str + " != " + keyEntryAlias);
                    } else if (params != null) {
                        throw new KeyStoreException("Modifying KeyStore-backed key using protection parameters not supported");
                    }
                } else {
                    throw new KeyStoreException("KeyStore-backed secret key does not have an alias");
                }
            } else if (params != null) {
                String keyExportFormat = key.getFormat();
                if (keyExportFormat == null) {
                    throw new KeyStoreException("Only secret keys that export their key material are supported");
                } else if ("RAW".equals(keyExportFormat)) {
                    byte[] keyMaterial = key.getEncoded();
                    if (keyMaterial != null) {
                        KeymasterArguments args = new KeymasterArguments();
                        try {
                            int keymasterAlgorithm = KeyProperties.KeyAlgorithm.toKeymasterSecretKeyAlgorithm(key.getAlgorithm());
                            args.addEnum(KeymasterDefs.KM_TAG_ALGORITHM, keymasterAlgorithm);
                            int i = 0;
                            if (keymasterAlgorithm == 128) {
                                try {
                                    int keymasterImpliedDigest = KeyProperties.KeyAlgorithm.toKeymasterDigest(key.getAlgorithm());
                                    if (keymasterImpliedDigest != -1) {
                                        keymasterDigests = new int[]{keymasterImpliedDigest};
                                        if (params.isDigestsSpecified()) {
                                            int[] keymasterDigestsFromParams = KeyProperties.Digest.allToKeymaster(params.getDigests());
                                            if (keymasterDigestsFromParams.length != 1 || keymasterDigestsFromParams[0] != keymasterImpliedDigest) {
                                                throw new KeyStoreException("Unsupported digests specification: " + Arrays.asList(params.getDigests()) + ". Only " + KeyProperties.Digest.fromKeymaster(keymasterImpliedDigest) + " supported for HMAC key algorithm " + key.getAlgorithm());
                                            }
                                        }
                                    } else {
                                        throw new ProviderException("HMAC key algorithm digest unknown for key algorithm " + key.getAlgorithm());
                                    }
                                } catch (IllegalArgumentException | IllegalStateException e) {
                                    e = e;
                                    KeymasterArguments keymasterArguments = args;
                                    throw new KeyStoreException(e);
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
                            if ((purposes & 1) != 0) {
                                if (params.isRandomizedEncryptionRequired()) {
                                    int length = keymasterBlockModes.length;
                                    while (i < length) {
                                        int keymasterBlockMode = keymasterBlockModes[i];
                                        if (KeymasterUtils.isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(keymasterBlockMode)) {
                                            i++;
                                        } else {
                                            throw new KeyStoreException("Randomized encryption (IND-CPA) required but may be violated by block mode: " + KeyProperties.BlockMode.fromKeymaster(keymasterBlockMode) + ". See KeyProtection documentation.");
                                        }
                                    }
                                }
                            }
                            args.addEnums(KeymasterDefs.KM_TAG_PURPOSE, KeyProperties.Purpose.allToKeymaster(purposes));
                            args.addEnums(KeymasterDefs.KM_TAG_BLOCK_MODE, keymasterBlockModes);
                            if (params.getSignaturePaddings().length <= 0) {
                                args.addEnums(KeymasterDefs.KM_TAG_PADDING, KeyProperties.EncryptionPadding.allToKeymaster(params.getEncryptionPaddings()));
                                KeymasterUtils.addUserAuthArgs(args, params);
                                KeymasterUtils.addMinMacLengthAuthorizationIfNecessary(args, keymasterAlgorithm, keymasterBlockModes, keymasterDigests2);
                                args.addDateIfNotNull(KeymasterDefs.KM_TAG_ACTIVE_DATETIME, params.getKeyValidityStart());
                                args.addDateIfNotNull(KeymasterDefs.KM_TAG_ORIGINATION_EXPIRE_DATETIME, params.getKeyValidityForOriginationEnd());
                                args.addDateIfNotNull(KeymasterDefs.KM_TAG_USAGE_EXPIRE_DATETIME, params.getKeyValidityForConsumptionEnd());
                                if ((purposes & 1) != 0) {
                                    if (!params.isRandomizedEncryptionRequired()) {
                                        args.addBoolean(KeymasterDefs.KM_TAG_CALLER_NONCE);
                                    }
                                }
                                int flags = 0;
                                if (params.isCriticalToDeviceEncryption()) {
                                    flags = 0 | 8;
                                }
                                if (params.isStrongBoxBacked()) {
                                    flags |= 16;
                                }
                                Credentials.deleteAllTypesForAlias(this.mKeyStore, str, this.mUid);
                                KeymasterArguments keymasterArguments2 = args;
                                int errorCode = this.mKeyStore.importKey(Credentials.USER_PRIVATE_KEY + str, args, 3, keyMaterial, this.mUid, flags, new KeyCharacteristics());
                                if (errorCode != 1) {
                                    throw new KeyStoreException("Failed to import secret key. Keystore error code: " + errorCode);
                                }
                                return;
                            }
                            try {
                                throw new KeyStoreException("Signature paddings not supported for symmetric keys");
                            } catch (IllegalArgumentException | IllegalStateException e2) {
                                e = e2;
                                throw new KeyStoreException(e);
                            }
                        } catch (IllegalArgumentException | IllegalStateException e3) {
                            e = e3;
                            KeymasterArguments keymasterArguments3 = args;
                            throw new KeyStoreException(e);
                        }
                    } else {
                        throw new KeyStoreException("Key did not export its key material despite supporting RAW format export");
                    }
                } else {
                    throw new KeyStoreException("Unsupported secret key material export format: " + keyExportFormat);
                }
            } else {
                throw new KeyStoreException("Protection parameters must be specified when importing a symmetric key");
            }
        } else {
            throw new KeyStoreException("Unsupported protection parameter class: " + param.getClass().getName() + ". Supported: " + KeyProtection.class.getName());
        }
    }

    private void setWrappedKeyEntry(String alias, WrappedKeyEntry entry, KeyStore.ProtectionParameter param) throws KeyStoreException {
        if (param == null) {
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
            long secureUserId = GateKeeper.getSecureUserId();
            String str = algorithm;
            String[] strArr = parts;
            KeymasterArguments keymasterArguments = args;
            int errorCode = this.mKeyStore.importWrappedKey(Credentials.USER_PRIVATE_KEY + alias, entry.getWrappedKeyBytes(), Credentials.USER_PRIVATE_KEY + entry.getWrappingKeyAlias(), maskingKey, args, secureUserId, 0, this.mUid, new KeyCharacteristics());
            if (errorCode == -100) {
                throw new SecureKeyImportUnavailableException("Could not import wrapped key");
            } else if (errorCode != 1) {
                throw new KeyStoreException("Failed to import wrapped key. Keystore error code: " + errorCode);
            }
        } else {
            throw new KeyStoreException("Protection parameters are specified inside wrapped keys");
        }
    }

    public void engineSetKeyEntry(String alias, byte[] userKey, Certificate[] chain) throws KeyStoreException {
        throw new KeyStoreException("Operation not supported because key encoding is unknown");
    }

    public void engineSetCertificateEntry(String alias, Certificate cert) throws KeyStoreException {
        if (isKeyEntry(alias)) {
            throw new KeyStoreException("Entry exists and is not a trusted certificate");
        } else if (cert != null) {
            try {
                byte[] encoded = cert.getEncoded();
                android.security.KeyStore keyStore = this.mKeyStore;
                if (!keyStore.put(Credentials.CA_CERTIFICATE + alias, encoded, this.mUid, 0)) {
                    throw new KeyStoreException("Couldn't insert certificate; is KeyStore initialized?");
                }
            } catch (CertificateEncodingException e) {
                throw new KeyStoreException(e);
            }
        } else {
            throw new NullPointerException("cert == null");
        }
    }

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
                Log.e("AndroidKeyStore", "invalid alias: " + alias);
            } else {
                aliases.add(new String(alias.substring(idx + 1)));
            }
        }
        return aliases;
    }

    public Enumeration<String> engineAliases() {
        return Collections.enumeration(getUniqueAliases());
    }

    public boolean engineContainsAlias(String alias) {
        if (alias != null) {
            android.security.KeyStore keyStore = this.mKeyStore;
            if (!keyStore.contains(Credentials.USER_PRIVATE_KEY + alias, this.mUid)) {
                android.security.KeyStore keyStore2 = this.mKeyStore;
                if (!keyStore2.contains(Credentials.USER_SECRET_KEY + alias, this.mUid)) {
                    android.security.KeyStore keyStore3 = this.mKeyStore;
                    if (!keyStore3.contains(Credentials.USER_CERTIFICATE + alias, this.mUid)) {
                        android.security.KeyStore keyStore4 = this.mKeyStore;
                        StringBuilder sb = new StringBuilder();
                        sb.append(Credentials.CA_CERTIFICATE);
                        sb.append(alias);
                        return keyStore4.contains(sb.toString(), this.mUid);
                    }
                }
            }
        }
        throw new NullPointerException("alias == null");
    }

    public int engineSize() {
        return getUniqueAliases().size();
    }

    public boolean engineIsKeyEntry(String alias) {
        return isKeyEntry(alias);
    }

    private boolean isKeyEntry(String alias) {
        android.security.KeyStore keyStore = this.mKeyStore;
        if (!keyStore.contains(Credentials.USER_PRIVATE_KEY + alias, this.mUid)) {
            android.security.KeyStore keyStore2 = this.mKeyStore;
            StringBuilder sb = new StringBuilder();
            sb.append(Credentials.USER_SECRET_KEY);
            sb.append(alias);
            return keyStore2.contains(sb.toString(), this.mUid);
        }
    }

    private boolean isCertificateEntry(String alias) {
        if (alias != null) {
            android.security.KeyStore keyStore = this.mKeyStore;
            return keyStore.contains(Credentials.CA_CERTIFICATE + alias, this.mUid);
        }
        throw new NullPointerException("alias == null");
    }

    public boolean engineIsCertificateEntry(String alias) {
        return !isKeyEntry(alias) && isCertificateEntry(alias);
    }

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

    public void engineStore(OutputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {
        throw new UnsupportedOperationException("Can not serialize AndroidKeyStore to OutputStream");
    }

    public void engineLoad(InputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {
        if (stream != null) {
            throw new IllegalArgumentException("InputStream not supported");
        } else if (password == null) {
            this.mKeyStore = android.security.KeyStore.getInstance();
            this.mUid = -1;
        } else {
            throw new IllegalArgumentException("password not supported");
        }
    }

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

    public void engineSetEntry(String alias, KeyStore.Entry entry, KeyStore.ProtectionParameter param) throws KeyStoreException {
        if (entry != null) {
            Credentials.deleteAllTypesForAlias(this.mKeyStore, alias, this.mUid);
            if (entry instanceof KeyStore.TrustedCertificateEntry) {
                engineSetCertificateEntry(alias, ((KeyStore.TrustedCertificateEntry) entry).getTrustedCertificate());
            } else if (entry instanceof KeyStore.PrivateKeyEntry) {
                KeyStore.PrivateKeyEntry prE = (KeyStore.PrivateKeyEntry) entry;
                setPrivateKeyEntry(alias, prE.getPrivateKey(), prE.getCertificateChain(), param);
            } else if (entry instanceof KeyStore.SecretKeyEntry) {
                setSecretKeyEntry(alias, ((KeyStore.SecretKeyEntry) entry).getSecretKey(), param);
            } else if (entry instanceof WrappedKeyEntry) {
                setWrappedKeyEntry(alias, (WrappedKeyEntry) entry, param);
            } else {
                throw new KeyStoreException("Entry must be a PrivateKeyEntry, SecretKeyEntry or TrustedCertificateEntry; was " + entry);
            }
        } else {
            throw new KeyStoreException("entry == null");
        }
    }

    static class KeyStoreX509Certificate extends DelegatingX509Certificate {
        private final String mPrivateKeyAlias;
        private final int mPrivateKeyUid;

        KeyStoreX509Certificate(String privateKeyAlias, int privateKeyUid, X509Certificate delegate) {
            super(delegate);
            this.mPrivateKeyAlias = privateKeyAlias;
            this.mPrivateKeyUid = privateKeyUid;
        }

        public PublicKey getPublicKey() {
            PublicKey original = super.getPublicKey();
            return AndroidKeyStoreProvider.getAndroidKeyStorePublicKey(this.mPrivateKeyAlias, this.mPrivateKeyUid, original.getAlgorithm(), original.getEncoded());
        }
    }
}
