package android.support.p011v4.hardware.fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.p002pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.p007os.CancellationSignal;
import android.p007os.Handler;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@RequiresApi(23)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23 */
/* loaded from: classes3.dex */
public final class FingerprintManagerCompatApi23 {
    private static FingerprintManager getFingerprintManagerOrNull(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        return null;
    }

    @SuppressLint({"MissingPermission"})
    static boolean hasEnrolledFingerprints(Context context) {
        FingerprintManager fp = getFingerprintManagerOrNull(context);
        return fp != null && fp.hasEnrolledFingerprints();
    }

    @SuppressLint({"MissingPermission"})
    static boolean isHardwareDetected(Context context) {
        FingerprintManager fp = getFingerprintManagerOrNull(context);
        return fp != null && fp.isHardwareDetected();
    }

    @SuppressLint({"MissingPermission"})
    static void authenticate(Context context, CryptoObject crypto, int flags, Object cancel, AuthenticationCallback callback, Handler handler) {
        FingerprintManager fp = getFingerprintManagerOrNull(context);
        if (fp != null) {
            fp.authenticate(wrapCryptoObject(crypto), (CancellationSignal) cancel, flags, wrapCallback(callback), handler);
        }
    }

    private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() == null) {
            return null;
        }
        return new FingerprintManager.CryptoObject(cryptoObject.getMac());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() == null) {
            return null;
        }
        return new CryptoObject(cryptoObject.getMac());
    }

    private static FingerprintManager.AuthenticationCallback wrapCallback(final AuthenticationCallback callback) {
        return new FingerprintManager.AuthenticationCallback() { // from class: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.1
            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                AuthenticationCallback.this.onAuthenticationError(errMsgId, errString);
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                AuthenticationCallback.this.onAuthenticationHelp(helpMsgId, helpString);
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                AuthenticationCallback.this.onAuthenticationSucceeded(new AuthenticationResultInternal(FingerprintManagerCompatApi23.unwrapCryptoObject(result.getCryptoObject())));
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
            public void onAuthenticationFailed() {
                AuthenticationCallback.this.onAuthenticationFailed();
            }
        };
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject */
    /* loaded from: classes3.dex */
    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$AuthenticationResultInternal */
    /* loaded from: classes3.dex */
    public static final class AuthenticationResultInternal {
        private CryptoObject mCryptoObject;

        public AuthenticationResultInternal(CryptoObject crypto) {
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$AuthenticationCallback */
    /* loaded from: classes3.dex */
    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResultInternal result) {
        }

        public void onAuthenticationFailed() {
        }
    }
}
