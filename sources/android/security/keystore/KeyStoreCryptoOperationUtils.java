package android.security.keystore;

import android.security.KeyStore;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
abstract class KeyStoreCryptoOperationUtils {
    private static volatile SecureRandom sRng;

    private KeyStoreCryptoOperationUtils() {
    }

    static InvalidKeyException getInvalidKeyExceptionForInit(KeyStore keyStore, AndroidKeyStoreKey key, int beginOpResultCode) {
        if (beginOpResultCode == 1) {
            return null;
        }
        InvalidKeyException e = keyStore.getInvalidKeyException(key.getAlias(), key.getUid(), beginOpResultCode);
        if (beginOpResultCode == 15 && (e instanceof UserNotAuthenticatedException)) {
            return null;
        }
        return e;
    }

    public static GeneralSecurityException getExceptionForCipherInit(KeyStore keyStore, AndroidKeyStoreKey key, int beginOpResultCode) {
        if (beginOpResultCode == 1) {
            return null;
        }
        if (beginOpResultCode != -55) {
            if (beginOpResultCode == -52) {
                return new InvalidAlgorithmParameterException("Invalid IV");
            }
            return getInvalidKeyExceptionForInit(keyStore, key, beginOpResultCode);
        }
        return new InvalidAlgorithmParameterException("Caller-provided IV not permitted");
    }

    static byte[] getRandomBytesToMixIntoKeystoreRng(SecureRandom rng, int sizeBytes) {
        if (sizeBytes <= 0) {
            return EmptyArray.BYTE;
        }
        if (rng == null) {
            rng = getRng();
        }
        byte[] result = new byte[sizeBytes];
        rng.nextBytes(result);
        return result;
    }

    private static SecureRandom getRng() {
        if (sRng == null) {
            sRng = new SecureRandom();
        }
        return sRng;
    }
}
