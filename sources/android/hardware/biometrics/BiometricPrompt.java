package android.hardware.biometrics;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.R;
import java.security.Signature;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public class BiometricPrompt implements BiometricAuthenticator, BiometricConstants {
    public static final int DISMISSED_REASON_NEGATIVE = 2;
    public static final int DISMISSED_REASON_POSITIVE = 1;
    public static final int DISMISSED_REASON_USER_CANCEL = 3;
    public static final int HIDE_DIALOG_DELAY = 2000;
    public static final String KEY_ALLOW_DEVICE_CREDENTIAL = "allow_device_credential";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_FROM_CONFIRM_DEVICE_CREDENTIAL = "from_confirm_device_credential";
    public static final String KEY_NEGATIVE_TEXT = "negative_text";
    public static final String KEY_POSITIVE_TEXT = "positive_text";
    public static final String KEY_REQUIRE_CONFIRMATION = "require_confirmation";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_TITLE = "title";
    public static final String KEY_USE_DEFAULT_TITLE = "use_default_title";
    private static final String TAG = "BiometricPrompt";
    /* access modifiers changed from: private */
    public AuthenticationCallback mAuthenticationCallback;
    private final IBiometricServiceReceiver mBiometricServiceReceiver;
    private final Bundle mBundle;
    private final Context mContext;
    /* access modifiers changed from: private */
    public CryptoObject mCryptoObject;
    /* access modifiers changed from: private */
    public Executor mExecutor;
    /* access modifiers changed from: private */
    public final ButtonInfo mNegativeButtonInfo;
    /* access modifiers changed from: private */
    public final ButtonInfo mPositiveButtonInfo;
    private final IBiometricService mService;
    private final IBinder mToken;

    private static class ButtonInfo {
        Executor executor;
        DialogInterface.OnClickListener listener;

        ButtonInfo(Executor ex, DialogInterface.OnClickListener l) {
            this.executor = ex;
            this.listener = l;
        }
    }

    public static class Builder {
        private final Bundle mBundle = new Bundle();
        private Context mContext;
        private ButtonInfo mNegativeButtonInfo;
        private ButtonInfo mPositiveButtonInfo;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(CharSequence title) {
            this.mBundle.putCharSequence("title", title);
            return this;
        }

        public Builder setUseDefaultTitle() {
            this.mBundle.putBoolean(BiometricPrompt.KEY_USE_DEFAULT_TITLE, true);
            return this;
        }

        public Builder setSubtitle(CharSequence subtitle) {
            this.mBundle.putCharSequence(BiometricPrompt.KEY_SUBTITLE, subtitle);
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.mBundle.putCharSequence("description", description);
            return this;
        }

        public Builder setPositiveButton(CharSequence text, Executor executor, DialogInterface.OnClickListener listener) {
            if (TextUtils.isEmpty(text)) {
                throw new IllegalArgumentException("Text must be set and non-empty");
            } else if (executor == null) {
                throw new IllegalArgumentException("Executor must not be null");
            } else if (listener != null) {
                this.mBundle.putCharSequence(BiometricPrompt.KEY_POSITIVE_TEXT, text);
                this.mPositiveButtonInfo = new ButtonInfo(executor, listener);
                return this;
            } else {
                throw new IllegalArgumentException("Listener must not be null");
            }
        }

        public Builder setNegativeButton(CharSequence text, Executor executor, DialogInterface.OnClickListener listener) {
            if (TextUtils.isEmpty(text)) {
                throw new IllegalArgumentException("Text must be set and non-empty");
            } else if (executor == null) {
                throw new IllegalArgumentException("Executor must not be null");
            } else if (listener != null) {
                this.mBundle.putCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT, text);
                this.mNegativeButtonInfo = new ButtonInfo(executor, listener);
                return this;
            } else {
                throw new IllegalArgumentException("Listener must not be null");
            }
        }

        public Builder setConfirmationRequired(boolean requireConfirmation) {
            this.mBundle.putBoolean(BiometricPrompt.KEY_REQUIRE_CONFIRMATION, requireConfirmation);
            return this;
        }

        public Builder setDeviceCredentialAllowed(boolean allowed) {
            this.mBundle.putBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL, allowed);
            return this;
        }

        public Builder setFromConfirmDeviceCredential() {
            this.mBundle.putBoolean(BiometricPrompt.KEY_FROM_CONFIRM_DEVICE_CREDENTIAL, true);
            return this;
        }

        public BiometricPrompt build() {
            CharSequence title = this.mBundle.getCharSequence("title");
            CharSequence negative = this.mBundle.getCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT);
            boolean useDefaultTitle = this.mBundle.getBoolean(BiometricPrompt.KEY_USE_DEFAULT_TITLE);
            boolean enableFallback = this.mBundle.getBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL);
            if (TextUtils.isEmpty(title) && !useDefaultTitle) {
                throw new IllegalArgumentException("Title must be set and non-empty");
            } else if (TextUtils.isEmpty(negative) && !enableFallback) {
                throw new IllegalArgumentException("Negative text must be set and non-empty");
            } else if (TextUtils.isEmpty(negative) || !enableFallback) {
                return new BiometricPrompt(this.mContext, this.mBundle, this.mPositiveButtonInfo, this.mNegativeButtonInfo);
            } else {
                throw new IllegalArgumentException("Can't have both negative button behavior and device credential enabled");
            }
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private OnAuthenticationCancelListener() {
        }

        public void onCancel() {
            BiometricPrompt.this.cancelAuthentication();
        }
    }

    private BiometricPrompt(Context context, Bundle bundle, ButtonInfo positiveButtonInfo, ButtonInfo negativeButtonInfo) {
        this.mToken = new Binder();
        this.mBiometricServiceReceiver = new IBiometricServiceReceiver.Stub() {
            public void onAuthenticationSucceeded() throws RemoteException {
                BiometricPrompt.this.mExecutor.execute(new Runnable() {
                    public final void run() {
                        BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(new BiometricPrompt.AuthenticationResult(BiometricPrompt.this.mCryptoObject));
                    }
                });
            }

            public void onAuthenticationFailed() throws RemoteException {
                BiometricPrompt.this.mExecutor.execute(new Runnable() {
                    public final void run() {
                        BiometricPrompt.this.mAuthenticationCallback.onAuthenticationFailed();
                    }
                });
            }

            public void onError(int error, String message) throws RemoteException {
                BiometricPrompt.this.mExecutor.execute(new Runnable(error, message) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ String f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(this.f$1, this.f$2);
                    }
                });
            }

            public void onAcquired(int acquireInfo, String message) throws RemoteException {
                BiometricPrompt.this.mExecutor.execute(new Runnable(acquireInfo, message) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ String f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        BiometricPrompt.this.mAuthenticationCallback.onAuthenticationHelp(this.f$1, this.f$2);
                    }
                });
            }

            public void onDialogDismissed(int reason) throws RemoteException {
                if (reason == 1) {
                    BiometricPrompt.this.mPositiveButtonInfo.executor.execute(new Runnable() {
                        public final void run() {
                            BiometricPrompt.this.mPositiveButtonInfo.listener.onClick((DialogInterface) null, -1);
                        }
                    });
                } else if (reason == 2) {
                    BiometricPrompt.this.mNegativeButtonInfo.executor.execute(new Runnable() {
                        public final void run() {
                            BiometricPrompt.this.mNegativeButtonInfo.listener.onClick((DialogInterface) null, -2);
                        }
                    });
                }
            }
        };
        this.mContext = context;
        this.mBundle = bundle;
        this.mPositiveButtonInfo = positiveButtonInfo;
        this.mNegativeButtonInfo = negativeButtonInfo;
        this.mService = IBiometricService.Stub.asInterface(ServiceManager.getService(Context.BIOMETRIC_SERVICE));
    }

    public static final class CryptoObject extends CryptoObject {
        public CryptoObject(Signature signature) {
            super(signature);
        }

        public CryptoObject(Cipher cipher) {
            super(cipher);
        }

        public CryptoObject(Mac mac) {
            super(mac);
        }

        public Signature getSignature() {
            return super.getSignature();
        }

        public Cipher getCipher() {
            return super.getCipher();
        }

        public Mac getMac() {
            return super.getMac();
        }
    }

    public static class AuthenticationResult extends BiometricAuthenticator.AuthenticationResult {
        public AuthenticationResult(CryptoObject crypto) {
            super(crypto, (BiometricAuthenticator.Identifier) null, 0);
        }

        public CryptoObject getCryptoObject() {
            return (CryptoObject) super.getCryptoObject();
        }
    }

    public static abstract class AuthenticationCallback extends BiometricAuthenticator.AuthenticationCallback {
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationAcquired(int acquireInfo) {
        }
    }

    public void authenticateUser(CancellationSignal cancel, Executor executor, AuthenticationCallback callback, int userId, IBiometricConfirmDeviceCredentialCallback confirmDeviceCredentialCallback) {
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        } else if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        } else if (callback != null) {
            authenticateInternal((CryptoObject) null, cancel, executor, callback, userId, confirmDeviceCredentialCallback);
        } else {
            throw new IllegalArgumentException("Must supply a callback");
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, Executor executor, AuthenticationCallback callback) {
        if (crypto == null) {
            throw new IllegalArgumentException("Must supply a crypto object");
        } else if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        } else if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        } else if (callback == null) {
            throw new IllegalArgumentException("Must supply a callback");
        } else if (!this.mBundle.getBoolean(KEY_ALLOW_DEVICE_CREDENTIAL)) {
            authenticateInternal(crypto, cancel, executor, callback, this.mContext.getUserId(), (IBiometricConfirmDeviceCredentialCallback) null);
        } else {
            throw new IllegalArgumentException("Device credential not supported with crypto");
        }
    }

    public void authenticate(CancellationSignal cancel, Executor executor, AuthenticationCallback callback) {
        if (cancel == null) {
            throw new IllegalArgumentException("Must supply a cancellation signal");
        } else if (executor == null) {
            throw new IllegalArgumentException("Must supply an executor");
        } else if (callback != null) {
            authenticateInternal((CryptoObject) null, cancel, executor, callback, this.mContext.getUserId(), (IBiometricConfirmDeviceCredentialCallback) null);
        } else {
            throw new IllegalArgumentException("Must supply a callback");
        }
    }

    /* access modifiers changed from: private */
    public void cancelAuthentication() {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to cancel authentication", e);
            }
        }
    }

    private void authenticateInternal(CryptoObject crypto, CancellationSignal cancel, Executor executor, AuthenticationCallback callback, int userId, IBiometricConfirmDeviceCredentialCallback confirmDeviceCredentialCallback) {
        CryptoObject cryptoObject = crypto;
        AuthenticationCallback authenticationCallback = callback;
        try {
            if (cancel.isCanceled()) {
                Log.w(TAG, "Authentication already canceled");
                return;
            }
            try {
                cancel.setOnCancelListener(new OnAuthenticationCancelListener());
                this.mCryptoObject = cryptoObject;
                try {
                    this.mExecutor = executor;
                    this.mAuthenticationCallback = authenticationCallback;
                    long sessionId = cryptoObject != null ? crypto.getOpId() : 0;
                    if (BiometricManager.hasBiometrics(this.mContext)) {
                        this.mService.authenticate(this.mToken, sessionId, userId, this.mBiometricServiceReceiver, this.mContext.getOpPackageName(), this.mBundle, confirmDeviceCredentialCallback);
                    } else {
                        this.mExecutor.execute(new Runnable(authenticationCallback) {
                            private final /* synthetic */ BiometricPrompt.AuthenticationCallback f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                this.f$1.onAuthenticationError(12, BiometricPrompt.this.mContext.getString(R.string.biometric_error_hw_unavailable));
                            }
                        });
                    }
                } catch (RemoteException e) {
                    e = e;
                    Log.e(TAG, "Remote exception while authenticating", e);
                    this.mExecutor.execute(new Runnable(authenticationCallback) {
                        private final /* synthetic */ BiometricPrompt.AuthenticationCallback f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            this.f$1.onAuthenticationError(1, BiometricPrompt.this.mContext.getString(R.string.biometric_error_hw_unavailable));
                        }
                    });
                }
            } catch (RemoteException e2) {
                e = e2;
                Executor executor2 = executor;
                Log.e(TAG, "Remote exception while authenticating", e);
                this.mExecutor.execute(new Runnable(authenticationCallback) {
                    private final /* synthetic */ BiometricPrompt.AuthenticationCallback f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        this.f$1.onAuthenticationError(1, BiometricPrompt.this.mContext.getString(R.string.biometric_error_hw_unavailable));
                    }
                });
            }
        } catch (RemoteException e3) {
            e = e3;
            CancellationSignal cancellationSignal = cancel;
            Executor executor22 = executor;
            Log.e(TAG, "Remote exception while authenticating", e);
            this.mExecutor.execute(new Runnable(authenticationCallback) {
                private final /* synthetic */ BiometricPrompt.AuthenticationCallback f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    this.f$1.onAuthenticationError(1, BiometricPrompt.this.mContext.getString(R.string.biometric_error_hw_unavailable));
                }
            });
        }
    }
}
