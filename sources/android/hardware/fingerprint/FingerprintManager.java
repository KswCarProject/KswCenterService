package android.hardware.fingerprint;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.p007os.Binder;
import android.p007os.CancellationSignal;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.IRemoteCallback;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.PowerManager;
import android.p007os.RemoteException;
import android.util.Slog;
import com.android.internal.C3132R;
import java.security.Signature;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
/* loaded from: classes.dex */
public class FingerprintManager implements BiometricAuthenticator, BiometricFingerprintConstants {
    private static final boolean DEBUG = true;
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ENUMERATED = 106;
    private static final int MSG_ERROR = 104;
    private static final int MSG_REMOVED = 105;
    private static final String TAG = "FingerprintManager";
    private AuthenticationCallback mAuthenticationCallback;
    private Context mContext;
    private CryptoObject mCryptoObject;
    private EnrollmentCallback mEnrollmentCallback;
    private EnumerateCallback mEnumerateCallback;
    private Handler mHandler;
    private RemovalCallback mRemovalCallback;
    private Fingerprint mRemovalFingerprint;
    private IFingerprintService mService;
    private IBinder mToken = new Binder();
    private IFingerprintServiceReceiver mServiceReceiver = new IFingerprintServiceReceiver.Stub() { // from class: android.hardware.fingerprint.FingerprintManager.2
        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(long deviceId, int fingerId, int groupId, int remaining) {
            FingerprintManager.this.mHandler.obtainMessage(100, remaining, 0, new Fingerprint(null, groupId, fingerId, deviceId)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(long deviceId, int acquireInfo, int vendorCode) {
            FingerprintManager.this.mHandler.obtainMessage(101, acquireInfo, vendorCode, Long.valueOf(deviceId)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(long deviceId, Fingerprint fp, int userId) {
            FingerprintManager.this.mHandler.obtainMessage(102, userId, 0, fp).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed(long deviceId) {
            FingerprintManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(long deviceId, int error, int vendorCode) {
            FingerprintManager.this.mHandler.obtainMessage(104, error, vendorCode, Long.valueOf(deviceId)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(long deviceId, int fingerId, int groupId, int remaining) {
            FingerprintManager.this.mHandler.obtainMessage(105, remaining, 0, new Fingerprint(null, groupId, fingerId, deviceId)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnumerated(long deviceId, int fingerId, int groupId, int remaining) {
            FingerprintManager.this.mHandler.obtainMessage(106, fingerId, groupId, Long.valueOf(deviceId)).sendToTarget();
        }
    };

    /* loaded from: classes.dex */
    private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private OnEnrollCancelListener() {
        }

        /* synthetic */ OnEnrollCancelListener(FingerprintManager x0, BinderC08771 x1) {
            this();
        }

        @Override // android.p007os.CancellationSignal.OnCancelListener
        public void onCancel() {
            FingerprintManager.this.cancelEnrollment();
        }
    }

    /* loaded from: classes.dex */
    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private android.hardware.biometrics.CryptoObject mCrypto;

        public OnAuthenticationCancelListener(android.hardware.biometrics.CryptoObject crypto) {
            this.mCrypto = crypto;
        }

        @Override // android.p007os.CancellationSignal.OnCancelListener
        public void onCancel() {
            FingerprintManager.this.cancelAuthentication(this.mCrypto);
        }
    }

    @Deprecated
    /* loaded from: classes.dex */
    public static final class CryptoObject extends android.hardware.biometrics.CryptoObject {
        public CryptoObject(Signature signature) {
            super(signature);
        }

        public CryptoObject(Cipher cipher) {
            super(cipher);
        }

        public CryptoObject(Mac mac) {
            super(mac);
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Signature getSignature() {
            return super.getSignature();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Cipher getCipher() {
            return super.getCipher();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Mac getMac() {
            return super.getMac();
        }
    }

    @Deprecated
    /* loaded from: classes.dex */
    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Fingerprint mFingerprint;
        private int mUserId;

        public AuthenticationResult(CryptoObject crypto, Fingerprint fingerprint, int userId) {
            this.mCryptoObject = crypto;
            this.mFingerprint = fingerprint;
            this.mUserId = userId;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        @UnsupportedAppUsage
        public Fingerprint getFingerprint() {
            return this.mFingerprint;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    @Deprecated
    /* loaded from: classes.dex */
    public static abstract class AuthenticationCallback extends BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int acquireInfo) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int errMsgId, CharSequence errString) {
        }

        public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onEnrollmentProgress(int remaining) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class RemovalCallback {
        public void onRemovalError(Fingerprint fp, int errMsgId, CharSequence errString) {
        }

        public void onRemovalSucceeded(Fingerprint fp, int remaining) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class EnumerateCallback {
        public void onEnumerateError(int errMsgId, CharSequence errString) {
        }

        public void onEnumerate(Fingerprint fingerprint) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class LockoutResetCallback {
        public void onLockoutReset() {
        }
    }

    @Deprecated
    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler) {
        authenticate(crypto, cancel, flags, callback, handler, this.mContext.getUserId());
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(this, handler.getLooper(), (BinderC08771) null);
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this, this.mContext.getMainLooper(), (BinderC08771) null);
        }
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, int flags, AuthenticationCallback callback, Handler handler, int userId) {
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancel != null) {
            if (!cancel.isCanceled()) {
                cancel.setOnCancelListener(new OnAuthenticationCancelListener(crypto));
            } else {
                Slog.m50w(TAG, "authentication already canceled");
                return;
            }
        }
        if (this.mService != null) {
            try {
                useHandler(handler);
                this.mAuthenticationCallback = callback;
                this.mCryptoObject = crypto;
                long sessionId = crypto != null ? crypto.getOpId() : 0L;
                this.mService.authenticate(this.mToken, sessionId, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.m49w(TAG, "Remote exception while authenticating: ", e);
                if (callback != null) {
                    callback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    public void enroll(byte[] token, CancellationSignal cancel, int flags, int userId, EnrollmentCallback callback) {
        if (userId == -2) {
            userId = getCurrentUserId();
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancel != null) {
            if (cancel.isCanceled()) {
                Slog.m50w(TAG, "enrollment already canceled");
                return;
            }
            cancel.setOnCancelListener(new OnEnrollCancelListener(this, null));
        }
        if (this.mService != null) {
            try {
                this.mEnrollmentCallback = callback;
                this.mService.enroll(this.mToken, token, userId, this.mServiceReceiver, flags, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.m49w(TAG, "Remote exception in enroll: ", e);
                if (callback != null) {
                    callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    public long preEnroll() {
        if (this.mService == null) {
            return 0L;
        }
        try {
            long result = this.mService.preEnroll(this.mToken);
            return result;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int postEnroll() {
        if (this.mService == null) {
            return 0;
        }
        try {
            int result = this.mService.postEnroll(this.mToken);
            return result;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.biometrics.BiometricAuthenticator
    public void setActiveUser(int userId) {
        if (this.mService != null) {
            try {
                this.mService.setActiveUser(userId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(Fingerprint fp, int userId, RemovalCallback callback) {
        if (this.mService != null) {
            try {
                this.mRemovalCallback = callback;
                this.mRemovalFingerprint = fp;
                this.mService.remove(this.mToken, fp.getBiometricId(), fp.getGroupId(), userId, this.mServiceReceiver);
            } catch (RemoteException e) {
                Slog.m49w(TAG, "Remote exception in remove: ", e);
                if (callback != null) {
                    callback.onRemovalError(fp, 1, getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    public void enumerate(int userId, EnumerateCallback callback) {
        if (this.mService != null) {
            try {
                this.mEnumerateCallback = callback;
                this.mService.enumerate(this.mToken, userId, this.mServiceReceiver);
            } catch (RemoteException e) {
                Slog.m49w(TAG, "Remote exception in enumerate: ", e);
                if (callback != null) {
                    callback.onEnumerateError(1, getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    public void rename(int fpId, int userId, String newName) {
        if (this.mService != null) {
            try {
                this.mService.rename(fpId, userId, newName);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m50w(TAG, "rename(): Service not connected!");
    }

    @UnsupportedAppUsage
    public List<Fingerprint> getEnrolledFingerprints(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.getEnrolledFingerprints(userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @UnsupportedAppUsage
    public List<Fingerprint> getEnrolledFingerprints() {
        return getEnrolledFingerprints(this.mContext.getUserId());
    }

    @Override // android.hardware.biometrics.BiometricAuthenticator
    public boolean hasEnrolledTemplates() {
        return hasEnrolledFingerprints();
    }

    @Override // android.hardware.biometrics.BiometricAuthenticator
    public boolean hasEnrolledTemplates(int userId) {
        return hasEnrolledFingerprints(userId);
    }

    @Deprecated
    public boolean hasEnrolledFingerprints() {
        if (this.mService != null) {
            try {
                return this.mService.hasEnrolledFingerprints(this.mContext.getUserId(), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean hasEnrolledFingerprints(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.hasEnrolledFingerprints(userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Override // android.hardware.biometrics.BiometricAuthenticator
    @Deprecated
    public boolean isHardwareDetected() {
        if (this.mService != null) {
            try {
                return this.mService.isHardwareDetected(0L, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m50w(TAG, "isFingerprintHardwareDetected(): Service not connected!");
        return false;
    }

    @UnsupportedAppUsage
    public long getAuthenticatorId() {
        if (this.mService != null) {
            try {
                return this.mService.getAuthenticatorId(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m50w(TAG, "getAuthenticatorId(): Service not connected!");
        return 0L;
    }

    public void addLockoutResetCallback(LockoutResetCallback callback) {
        if (this.mService != null) {
            try {
                PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
                this.mService.addLockoutResetCallback(new BinderC08771(powerManager, callback));
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m50w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$1 */
    /* loaded from: classes.dex */
    class BinderC08771 extends IBiometricServiceLockoutResetCallback.Stub {
        final /* synthetic */ LockoutResetCallback val$callback;
        final /* synthetic */ PowerManager val$powerManager;

        BinderC08771(PowerManager powerManager, LockoutResetCallback lockoutResetCallback) {
            this.val$powerManager = powerManager;
            this.val$callback = lockoutResetCallback;
        }

        @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
        public void onLockoutReset(long deviceId, IRemoteCallback serverCallback) throws RemoteException {
            try {
                final PowerManager.WakeLock wakeLock = this.val$powerManager.newWakeLock(1, "lockoutResetCallback");
                wakeLock.acquire();
                Handler handler = FingerprintManager.this.mHandler;
                final LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(new Runnable() { // from class: android.hardware.fingerprint.-$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintManager.BinderC08771.lambda$onLockoutReset$0(FingerprintManager.LockoutResetCallback.this, wakeLock);
                    }
                });
            } finally {
                serverCallback.sendResult(null);
            }
        }

        static /* synthetic */ void lambda$onLockoutReset$0(LockoutResetCallback callback, PowerManager.WakeLock wakeLock) {
            try {
                callback.onLockoutReset();
            } finally {
                wakeLock.release();
            }
        }
    }

    /* loaded from: classes.dex */
    private class MyHandler extends Handler {
        /* synthetic */ MyHandler(FingerprintManager x0, Context x1, BinderC08771 x2) {
            this(x1);
        }

        /* synthetic */ MyHandler(FingerprintManager x0, Looper x1, BinderC08771 x2) {
            this(x1);
        }

        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    FingerprintManager.this.sendEnrollResult((Fingerprint) msg.obj, msg.arg1);
                    return;
                case 101:
                    FingerprintManager.this.sendAcquiredResult(((Long) msg.obj).longValue(), msg.arg1, msg.arg2);
                    return;
                case 102:
                    FingerprintManager.this.sendAuthenticatedSucceeded((Fingerprint) msg.obj, msg.arg1);
                    return;
                case 103:
                    FingerprintManager.this.sendAuthenticatedFailed();
                    return;
                case 104:
                    FingerprintManager.this.sendErrorResult(((Long) msg.obj).longValue(), msg.arg1, msg.arg2);
                    return;
                case 105:
                    FingerprintManager.this.sendRemovedResult((Fingerprint) msg.obj, msg.arg1);
                    return;
                case 106:
                    FingerprintManager.this.sendEnumeratedResult(((Long) msg.obj).longValue(), msg.arg1, msg.arg2);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemovedResult(Fingerprint fingerprint, int remaining) {
        if (this.mRemovalCallback == null) {
            return;
        }
        if (fingerprint == null) {
            Slog.m56e(TAG, "Received MSG_REMOVED, but fingerprint is null");
            return;
        }
        int fingerId = fingerprint.getBiometricId();
        int reqFingerId = this.mRemovalFingerprint.getBiometricId();
        if (reqFingerId != 0 && fingerId != 0 && fingerId != reqFingerId) {
            Slog.m50w(TAG, "Finger id didn't match: " + fingerId + " != " + reqFingerId);
            return;
        }
        int groupId = fingerprint.getGroupId();
        int reqGroupId = this.mRemovalFingerprint.getGroupId();
        if (groupId != reqGroupId) {
            Slog.m50w(TAG, "Group id didn't match: " + groupId + " != " + reqGroupId);
            return;
        }
        this.mRemovalCallback.onRemovalSucceeded(fingerprint, remaining);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnumeratedResult(long deviceId, int fingerId, int groupId) {
        if (this.mEnumerateCallback != null) {
            this.mEnumerateCallback.onEnumerate(new Fingerprint(null, groupId, fingerId, deviceId));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnrollResult(Fingerprint fp, int remaining) {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentProgress(remaining);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(Fingerprint fp, int userId) {
        if (this.mAuthenticationCallback != null) {
            AuthenticationResult result = new AuthenticationResult(this.mCryptoObject, fp, userId);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAcquiredResult(long deviceId, int acquireInfo, int vendorCode) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationAcquired(acquireInfo);
        }
        String msg = getAcquiredString(this.mContext, acquireInfo, vendorCode);
        if (msg == null) {
            return;
        }
        int clientInfo = acquireInfo == 6 ? vendorCode + 1000 : acquireInfo;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentHelp(clientInfo, msg);
        } else if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationHelp(clientInfo, msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(long deviceId, int errMsgId, int vendorCode) {
        int clientErrMsgId = errMsgId == 8 ? vendorCode + 1000 : errMsgId;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentError(clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
        } else if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationError(clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
        } else if (this.mRemovalCallback != null) {
            this.mRemovalCallback.onRemovalError(this.mRemovalFingerprint, clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
        } else if (this.mEnumerateCallback != null) {
            this.mEnumerateCallback.onEnumerateError(clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
        }
    }

    public FingerprintManager(Context context, IFingerprintService service) {
        this.mContext = context;
        this.mService = service;
        if (this.mService == null) {
            Slog.m52v(TAG, "FingerprintManagerService was null");
        }
        this.mHandler = new MyHandler(this, context, (BinderC08771) null);
    }

    private int getCurrentUserId() {
        try {
            return ActivityManager.getService().getCurrentUser().f30id;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment() {
        if (this.mService != null) {
            try {
                this.mService.cancelEnrollment(this.mToken);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(android.hardware.biometrics.CryptoObject cryptoObject) {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getErrorString(Context context, int errMsg, int vendorCode) {
        switch (errMsg) {
            case 1:
                return context.getString(C3132R.string.fingerprint_error_hw_not_available);
            case 2:
                return context.getString(C3132R.string.fingerprint_error_unable_to_process);
            case 3:
                return context.getString(C3132R.string.fingerprint_error_timeout);
            case 4:
                return context.getString(C3132R.string.fingerprint_error_no_space);
            case 5:
                return context.getString(C3132R.string.fingerprint_error_canceled);
            case 7:
                return context.getString(C3132R.string.fingerprint_error_lockout);
            case 8:
                String[] msgArray = context.getResources().getStringArray(C3132R.array.fingerprint_error_vendor);
                if (vendorCode < msgArray.length) {
                    return msgArray[vendorCode];
                }
                break;
            case 9:
                return context.getString(C3132R.string.fingerprint_error_lockout_permanent);
            case 10:
                return context.getString(C3132R.string.fingerprint_error_user_canceled);
            case 11:
                return context.getString(C3132R.string.fingerprint_error_no_fingerprints);
            case 12:
                return context.getString(C3132R.string.fingerprint_error_hw_not_present);
        }
        Slog.m50w(TAG, "Invalid error message: " + errMsg + ", " + vendorCode);
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getAcquiredString(Context context, int acquireInfo, int vendorCode) {
        switch (acquireInfo) {
            case 0:
                return null;
            case 1:
                return context.getString(C3132R.string.fingerprint_acquired_partial);
            case 2:
                return context.getString(C3132R.string.fingerprint_acquired_insufficient);
            case 3:
                return context.getString(C3132R.string.fingerprint_acquired_imager_dirty);
            case 4:
                return context.getString(C3132R.string.fingerprint_acquired_too_slow);
            case 5:
                return context.getString(C3132R.string.fingerprint_acquired_too_fast);
            case 6:
                String[] msgArray = context.getResources().getStringArray(C3132R.array.fingerprint_acquired_vendor);
                if (vendorCode < msgArray.length) {
                    return msgArray[vendorCode];
                }
                break;
        }
        Slog.m50w(TAG, "Invalid acquired message: " + acquireInfo + ", " + vendorCode);
        return null;
    }
}
