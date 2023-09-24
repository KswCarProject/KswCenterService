package android.content.p002pm.dex;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.p002pm.dex.ArtManager;
import android.content.p002pm.dex.ISnapshotRuntimeProfileCallback;
import android.p007os.Environment;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.provider.SettingsStringUtil;
import android.util.Slog;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* renamed from: android.content.pm.dex.ArtManager */
/* loaded from: classes.dex */
public class ArtManager {
    public static final int PROFILE_APPS = 0;
    public static final int PROFILE_BOOT_IMAGE = 1;
    public static final int SNAPSHOT_FAILED_CODE_PATH_NOT_FOUND = 1;
    public static final int SNAPSHOT_FAILED_INTERNAL_ERROR = 2;
    public static final int SNAPSHOT_FAILED_PACKAGE_NOT_FOUND = 0;
    private static final String TAG = "ArtManager";
    private final IArtManager mArtManager;
    private final Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.content.pm.dex.ArtManager$ProfileType */
    /* loaded from: classes.dex */
    public @interface ProfileType {
    }

    /* renamed from: android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallback */
    /* loaded from: classes.dex */
    public static abstract class SnapshotRuntimeProfileCallback {
        public abstract void onError(int i);

        public abstract void onSuccess(ParcelFileDescriptor parcelFileDescriptor);
    }

    public ArtManager(Context context, IArtManager manager) {
        this.mContext = context;
        this.mArtManager = manager;
    }

    public void snapshotRuntimeProfile(int profileType, String packageName, String codePath, Executor executor, SnapshotRuntimeProfileCallback callback) {
        Slog.m58d(TAG, "Requesting profile snapshot for " + packageName + SettingsStringUtil.DELIMITER + codePath);
        SnapshotRuntimeProfileCallbackDelegate delegate = new SnapshotRuntimeProfileCallbackDelegate(callback, executor);
        try {
            this.mArtManager.snapshotRuntimeProfile(profileType, packageName, codePath, delegate, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isRuntimeProfilingEnabled(int profileType) {
        try {
            return this.mArtManager.isRuntimeProfilingEnabled(profileType, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate */
    /* loaded from: classes.dex */
    private static class SnapshotRuntimeProfileCallbackDelegate extends ISnapshotRuntimeProfileCallback.Stub {
        private final SnapshotRuntimeProfileCallback mCallback;
        private final Executor mExecutor;

        private SnapshotRuntimeProfileCallbackDelegate(SnapshotRuntimeProfileCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        @Override // android.content.p002pm.dex.ISnapshotRuntimeProfileCallback
        public void onSuccess(final ParcelFileDescriptor profileReadFd) {
            this.mExecutor.execute(new Runnable() { // from class: android.content.pm.dex.-$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$OOdGv4iFxuVpH2kzFMr8KwX3X8s
                @Override // java.lang.Runnable
                public final void run() {
                    ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.mCallback.onSuccess(profileReadFd);
                }
            });
        }

        @Override // android.content.p002pm.dex.ISnapshotRuntimeProfileCallback
        public void onError(final int errCode) {
            this.mExecutor.execute(new Runnable() { // from class: android.content.pm.dex.-$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$m2Wpsf6LxhWt_1tS6tQt3B8QcGo
                @Override // java.lang.Runnable
                public final void run() {
                    ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.mCallback.onError(errCode);
                }
            });
        }
    }

    public static String getProfileName(String splitName) {
        if (splitName == null) {
            return "primary.prof";
        }
        return splitName + ".split.prof";
    }

    public static String getCurrentProfilePath(String packageName, int userId, String splitName) {
        File profileDir = Environment.getDataProfilesDePackageDirectory(userId, packageName);
        return new File(profileDir, getProfileName(splitName)).getAbsolutePath();
    }

    public static File getProfileSnapshotFileForName(String packageName, String profileName) {
        File profileDir = Environment.getDataRefProfilesDePackageDirectory(packageName);
        return new File(profileDir, profileName + ".snapshot");
    }
}