package android.permission;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.permission.IPermissionController;
import android.permission.PermissionControllerManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import libcore.io.IoUtils;

@SystemApi
public final class PermissionControllerManager {
    public static final int COUNT_ONLY_WHEN_GRANTED = 1;
    public static final int COUNT_WHEN_SYSTEM = 2;
    public static final String KEY_RESULT = "android.permission.PermissionControllerManager.key.result";
    public static final int REASON_INSTALLER_POLICY_VIOLATION = 2;
    public static final int REASON_MALWARE = 1;
    /* access modifiers changed from: private */
    public static final String TAG = PermissionControllerManager.class.getSimpleName();
    private static final Object sLock = new Object();
    @GuardedBy({"sLock"})
    private static ArrayMap<Pair<Integer, Thread>, RemoteService> sRemoteServices = new ArrayMap<>(1);
    private final Context mContext;
    private final RemoteService mRemoteService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CountPermissionAppsFlag {
    }

    public interface OnCountPermissionAppsResultCallback {
        void onCountPermissionApps(int i);
    }

    public interface OnGetAppPermissionResultCallback {
        void onGetAppPermissions(List<RuntimePermissionPresentationInfo> list);
    }

    public interface OnGetRuntimePermissionBackupCallback {
        void onGetRuntimePermissionsBackup(byte[] bArr);
    }

    public interface OnPermissionUsageResultCallback {
        void onPermissionUsageResult(List<RuntimePermissionUsageInfo> list);
    }

    public static abstract class OnRevokeRuntimePermissionsCallback {
        public abstract void onRevokeRuntimePermissions(Map<String, List<String>> map);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public PermissionControllerManager(Context context, Handler handler) {
        synchronized (sLock) {
            Pair<Integer, Thread> key = new Pair<>(Integer.valueOf(context.getUserId()), handler.getLooper().getThread());
            RemoteService remoteService = sRemoteServices.get(key);
            if (remoteService == null) {
                Intent intent = new Intent(PermissionControllerService.SERVICE_INTERFACE);
                intent.setPackage(context.getPackageManager().getPermissionControllerPackageName());
                remoteService = new RemoteService(ActivityThread.currentApplication(), context.getPackageManager().resolveService(intent, 0).getComponentInfo().getComponentName(), handler, context.getUser());
                sRemoteServices.put(key, remoteService);
            }
            this.mRemoteService = remoteService;
        }
        this.mContext = context;
    }

    public void revokeRuntimePermissions(Map<String, List<String>> request, boolean doDryRun, int reason, Executor executor, OnRevokeRuntimePermissionsCallback callback) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(request);
        for (Map.Entry<String, List<String>> appRequest : request.entrySet()) {
            Preconditions.checkNotNull(appRequest.getKey());
            Preconditions.checkCollectionElementsNotNull(appRequest.getValue(), "permissions");
        }
        if (this.mContext.checkSelfPermission(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS) == 0) {
            this.mRemoteService.scheduleRequest(new PendingRevokeRuntimePermissionRequest(this.mRemoteService, request, doDryRun, reason, this.mContext.getPackageName(), executor, callback));
            return;
        }
        throw new SecurityException("android.permission.REVOKE_RUNTIME_PERMISSIONS required");
    }

    public void setRuntimePermissionGrantStateByDeviceAdmin(String callerPackageName, String packageName, String permission, int grantState, Executor executor, Consumer<Boolean> callback) {
        int i = grantState;
        Preconditions.checkStringNotEmpty(callerPackageName);
        Preconditions.checkStringNotEmpty(packageName);
        Preconditions.checkStringNotEmpty(permission);
        boolean z = true;
        if (!(i == 1 || i == 2 || i == 0)) {
            z = false;
        }
        Preconditions.checkArgument(z);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(callback);
        this.mRemoteService.scheduleRequest(new PendingSetRuntimePermissionGrantStateByDeviceAdmin(this.mRemoteService, callerPackageName, packageName, permission, grantState, executor, callback));
    }

    public void getRuntimePermissionBackup(UserHandle user, Executor executor, OnGetRuntimePermissionBackupCallback callback) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(callback);
        this.mRemoteService.scheduleRequest(new PendingGetRuntimePermissionBackup(this.mRemoteService, user, executor, callback));
    }

    public void restoreRuntimePermissionBackup(byte[] backup, UserHandle user) {
        Preconditions.checkNotNull(backup);
        Preconditions.checkNotNull(user);
        this.mRemoteService.scheduleAsyncRequest(new PendingRestoreRuntimePermissionBackup(this.mRemoteService, backup, user));
    }

    public void restoreDelayedRuntimePermissionBackup(String packageName, UserHandle user, Executor executor, Consumer<Boolean> callback) {
        Preconditions.checkNotNull(packageName);
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(callback);
        this.mRemoteService.scheduleRequest(new PendingRestoreDelayedRuntimePermissionBackup(this.mRemoteService, packageName, user, executor, callback));
    }

    public void getAppPermissions(String packageName, OnGetAppPermissionResultCallback callback, Handler handler) {
        Preconditions.checkNotNull(packageName);
        Preconditions.checkNotNull(callback);
        this.mRemoteService.scheduleRequest(new PendingGetAppPermissionRequest(this.mRemoteService, packageName, callback, handler == null ? this.mRemoteService.getHandler() : handler));
    }

    public void revokeRuntimePermission(String packageName, String permissionName) {
        Preconditions.checkNotNull(packageName);
        Preconditions.checkNotNull(permissionName);
        this.mRemoteService.scheduleAsyncRequest(new PendingRevokeAppPermissionRequest(packageName, permissionName));
    }

    public void countPermissionApps(List<String> permissionNames, int flags, OnCountPermissionAppsResultCallback callback, Handler handler) {
        Preconditions.checkCollectionElementsNotNull(permissionNames, "permissionNames");
        Preconditions.checkFlagsArgument(flags, 3);
        Preconditions.checkNotNull(callback);
        this.mRemoteService.scheduleRequest(new PendingCountPermissionAppsRequest(this.mRemoteService, permissionNames, flags, callback, handler == null ? this.mRemoteService.getHandler() : handler));
    }

    public void getPermissionUsages(boolean countSystem, long numMillis, Executor executor, OnPermissionUsageResultCallback callback) {
        Preconditions.checkArgumentNonnegative(numMillis);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(callback);
        this.mRemoteService.scheduleRequest(new PendingGetPermissionUsagesRequest(this.mRemoteService, countSystem, numMillis, executor, callback));
    }

    public void grantOrUpgradeDefaultRuntimePermissions(Executor executor, Consumer<Boolean> callback) {
        this.mRemoteService.scheduleRequest(new PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(this.mRemoteService, executor, callback));
    }

    static final class RemoteService extends AbstractMultiplePendingRequestsRemoteService<RemoteService, IPermissionController> {
        private static final long MESSAGE_TIMEOUT_MILLIS = 30000;
        private static final long UNBIND_TIMEOUT_MILLIS = 10000;

        RemoteService(Context context, ComponentName componentName, Handler handler, UserHandle user) {
            super(context, PermissionControllerService.SERVICE_INTERFACE, componentName, user.getIdentifier(), $$Lambda$PermissionControllerManager$RemoteService$L8NTbqIPWKu7tyiOxbu_00YKss.INSTANCE, handler, 0, false, 1);
        }

        static /* synthetic */ void lambda$new$0(RemoteService service) {
            String access$1000 = PermissionControllerManager.TAG;
            Log.e(access$1000, "RemoteService " + service + " died");
        }

        /* access modifiers changed from: package-private */
        public Handler getHandler() {
            return this.mHandler;
        }

        /* access modifiers changed from: protected */
        public IPermissionController getServiceInterface(IBinder binder) {
            return IPermissionController.Stub.asInterface(binder);
        }

        /* access modifiers changed from: protected */
        public long getTimeoutIdleBindMillis() {
            return 10000;
        }

        /* access modifiers changed from: protected */
        public long getRemoteRequestMillis() {
            return 30000;
        }

        public void scheduleRequest(AbstractRemoteService.BasePendingRequest<RemoteService, IPermissionController> pendingRequest) {
            super.scheduleRequest(pendingRequest);
        }

        public void scheduleAsyncRequest(AbstractRemoteService.AsyncRequest<IPermissionController> request) {
            super.scheduleAsyncRequest(request);
        }
    }

    private static class FileReaderTask<Callback extends Consumer<byte[]>> extends AsyncTask<Void, Void, byte[]> {
        private final Callback mCallback;
        private ParcelFileDescriptor mLocalPipe;
        private ParcelFileDescriptor mRemotePipe;

        FileReaderTask(Callback callback) {
            this.mCallback = callback;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            try {
                ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
                this.mLocalPipe = pipe[0];
                this.mRemotePipe = pipe[1];
            } catch (IOException e) {
                Log.e(PermissionControllerManager.TAG, "Could not create pipe needed to get runtime permission backup", e);
            }
        }

        /* access modifiers changed from: package-private */
        public ParcelFileDescriptor getRemotePipe() {
            return this.mRemotePipe;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0032, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            r2.addSuppressed(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x003b, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x003c, code lost:
            android.util.Log.e(android.permission.PermissionControllerManager.access$1000(), "Error reading runtime permission backup", r1);
            r0.reset();
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x003b A[EDGE_INSN: B:13:?->B:26:0x003b ?: BREAK  , ExcHandler: IOException | NullPointerException (r1v1 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public byte[] doInBackground(java.lang.Void... r7) {
            /*
                r6 = this;
                java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
                r0.<init>()
                android.os.ParcelFileDescriptor$AutoCloseInputStream r1 = new android.os.ParcelFileDescriptor$AutoCloseInputStream     // Catch:{ IOException | NullPointerException -> 0x003b }
                android.os.ParcelFileDescriptor r2 = r6.mLocalPipe     // Catch:{ IOException | NullPointerException -> 0x003b }
                r1.<init>(r2)     // Catch:{ IOException | NullPointerException -> 0x003b }
                r2 = 0
                r3 = 16384(0x4000, float:2.2959E-41)
                byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x002a }
            L_0x0011:
                boolean r4 = r6.isCancelled()     // Catch:{ Throwable -> 0x002a }
                if (r4 != 0) goto L_0x0024
                int r4 = r1.read(r3)     // Catch:{ Throwable -> 0x002a }
                r5 = -1
                if (r4 != r5) goto L_0x001f
                goto L_0x0024
            L_0x001f:
                r5 = 0
                r0.write(r3, r5, r4)     // Catch:{ Throwable -> 0x002a }
                goto L_0x0011
            L_0x0024:
                r1.close()     // Catch:{ IOException | NullPointerException -> 0x003b }
                goto L_0x0048
            L_0x0028:
                r3 = move-exception
                goto L_0x002c
            L_0x002a:
                r2 = move-exception
                throw r2     // Catch:{ all -> 0x0028 }
            L_0x002c:
                if (r2 == 0) goto L_0x0037
                r1.close()     // Catch:{ Throwable -> 0x0032, IOException | NullPointerException -> 0x003b }
                goto L_0x003a
            L_0x0032:
                r4 = move-exception
                r2.addSuppressed(r4)     // Catch:{ IOException | NullPointerException -> 0x003b }
                goto L_0x003a
            L_0x0037:
                r1.close()     // Catch:{ IOException | NullPointerException -> 0x003b }
            L_0x003a:
                throw r3     // Catch:{ IOException | NullPointerException -> 0x003b }
            L_0x003b:
                r1 = move-exception
                java.lang.String r2 = android.permission.PermissionControllerManager.TAG
                java.lang.String r3 = "Error reading runtime permission backup"
                android.util.Log.e(r2, r3, r1)
                r0.reset()
            L_0x0048:
                byte[] r1 = r0.toByteArray()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.permission.PermissionControllerManager.FileReaderTask.doInBackground(java.lang.Void[]):byte[]");
        }

        /* access modifiers changed from: package-private */
        public void interruptRead() {
            IoUtils.closeQuietly(this.mLocalPipe);
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            onPostExecute(new byte[0]);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(byte[] backup) {
            IoUtils.closeQuietly(this.mLocalPipe);
            this.mCallback.accept(backup);
        }
    }

    private static class FileWriterTask extends AsyncTask<byte[], Void, Void> {
        private static final int CHUNK_SIZE = 4096;
        private ParcelFileDescriptor mLocalPipe;
        private ParcelFileDescriptor mRemotePipe;

        private FileWriterTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            try {
                ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
                this.mRemotePipe = pipe[0];
                this.mLocalPipe = pipe[1];
            } catch (IOException e) {
                Log.e(PermissionControllerManager.TAG, "Could not create pipe needed to send runtime permission backup", e);
            }
        }

        /* access modifiers changed from: package-private */
        public ParcelFileDescriptor getRemotePipe() {
            return this.mRemotePipe;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
            r4 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0027, code lost:
            r6 = r4;
            r4 = r0;
            r0 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0030, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            r4.addSuppressed(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0039, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x003a, code lost:
            android.util.Log.e(android.permission.PermissionControllerManager.access$1000(), "Error sending runtime permission backup", r0);
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0039 A[ExcHandler: IOException | NullPointerException (r0v1 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x0004] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(byte[]... r8) {
            /*
                r7 = this;
                r0 = 0
                r1 = r8[r0]
                r2 = 0
                android.os.ParcelFileDescriptor$AutoCloseOutputStream r3 = new android.os.ParcelFileDescriptor$AutoCloseOutputStream     // Catch:{ IOException | NullPointerException -> 0x0039 }
                android.os.ParcelFileDescriptor r4 = r7.mLocalPipe     // Catch:{ IOException | NullPointerException -> 0x0039 }
                r3.<init>(r4)     // Catch:{ IOException | NullPointerException -> 0x0039 }
            L_0x000c:
                int r4 = r1.length     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
                if (r0 >= r4) goto L_0x001d
                int r4 = r1.length     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
                int r4 = r4 - r0
                r5 = 4096(0x1000, float:5.74E-42)
                int r4 = java.lang.Math.min(r5, r4)     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
                r3.write(r1, r0, r4)     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
                int r0 = r0 + 4096
                goto L_0x000c
            L_0x001d:
                r3.close()     // Catch:{ IOException | NullPointerException -> 0x0039 }
                goto L_0x0043
            L_0x0021:
                r0 = move-exception
                r4 = r2
                goto L_0x002a
            L_0x0024:
                r0 = move-exception
                throw r0     // Catch:{ all -> 0x0026 }
            L_0x0026:
                r4 = move-exception
                r6 = r4
                r4 = r0
                r0 = r6
            L_0x002a:
                if (r4 == 0) goto L_0x0035
                r3.close()     // Catch:{ Throwable -> 0x0030, IOException | NullPointerException -> 0x0039 }
                goto L_0x0038
            L_0x0030:
                r5 = move-exception
                r4.addSuppressed(r5)     // Catch:{ IOException | NullPointerException -> 0x0039 }
                goto L_0x0038
            L_0x0035:
                r3.close()     // Catch:{ IOException | NullPointerException -> 0x0039 }
            L_0x0038:
                throw r0     // Catch:{ IOException | NullPointerException -> 0x0039 }
            L_0x0039:
                r0 = move-exception
                java.lang.String r3 = android.permission.PermissionControllerManager.TAG
                java.lang.String r4 = "Error sending runtime permission backup"
                android.util.Log.e(r3, r4, r0)
            L_0x0043:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.permission.PermissionControllerManager.FileWriterTask.doInBackground(byte[][]):java.lang.Void");
        }

        /* access modifiers changed from: package-private */
        public void interruptWrite() {
            IoUtils.closeQuietly(this.mLocalPipe);
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            onPostExecute((Void) null);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void ignored) {
            IoUtils.closeQuietly(this.mLocalPipe);
        }
    }

    private static final class PendingRevokeRuntimePermissionRequest extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnRevokeRuntimePermissionsCallback mCallback;
        private final String mCallingPackage;
        private final boolean mDoDryRun;
        private final Executor mExecutor;
        private final int mReason;
        private final RemoteCallback mRemoteCallback;
        private final Map<String, List<String>> mRequest;

        private PendingRevokeRuntimePermissionRequest(RemoteService service, Map<String, List<String>> request, boolean doDryRun, int reason, String callingPackage, Executor executor, OnRevokeRuntimePermissionsCallback callback) {
            super(service);
            this.mRequest = request;
            this.mDoDryRun = doDryRun;
            this.mReason = reason;
            this.mCallingPackage = callingPackage;
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(executor, callback) {
                private final /* synthetic */ Executor f$1;
                private final /* synthetic */ PermissionControllerManager.OnRevokeRuntimePermissionsCallback f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onResult(Bundle bundle) {
                    this.f$1.execute(new Runnable(bundle, this.f$2) {
                        private final /* synthetic */ Bundle f$1;
                        private final /* synthetic */ PermissionControllerManager.OnRevokeRuntimePermissionsCallback f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            PermissionControllerManager.PendingRevokeRuntimePermissionRequest.lambda$new$0(PermissionControllerManager.PendingRevokeRuntimePermissionRequest.this, this.f$1, this.f$2);
                        }
                    });
                }
            }, (Handler) null);
        }

        public static /* synthetic */ void lambda$new$0(PendingRevokeRuntimePermissionRequest pendingRevokeRuntimePermissionRequest, Bundle result, OnRevokeRuntimePermissionsCallback callback) {
            Map<String, List<String>> revoked;
            long token = Binder.clearCallingIdentity();
            try {
                revoked = new ArrayMap<>();
                Bundle bundleizedRevoked = result.getBundle(PermissionControllerManager.KEY_RESULT);
                for (String packageName : bundleizedRevoked.keySet()) {
                    Preconditions.checkNotNull(packageName);
                    ArrayList<String> permissions = bundleizedRevoked.getStringArrayList(packageName);
                    Preconditions.checkCollectionElementsNotNull(permissions, "permissions");
                    revoked.put(packageName, permissions);
                }
            } catch (Exception e) {
                Log.e(PermissionControllerManager.TAG, "Could not read result when revoking runtime permissions", e);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(token);
                pendingRevokeRuntimePermissionRequest.finish();
                throw th;
            }
            callback.onRevokeRuntimePermissions(revoked);
            Binder.restoreCallingIdentity(token);
            pendingRevokeRuntimePermissionRequest.finish();
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() {
                    public final void run() {
                        PermissionControllerManager.PendingRevokeRuntimePermissionRequest.this.mCallback.onRevokeRuntimePermissions(Collections.emptyMap());
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        public void run() {
            Bundle bundledizedRequest = new Bundle();
            for (Map.Entry<String, List<String>> appRequest : this.mRequest.entrySet()) {
                bundledizedRequest.putStringArrayList(appRequest.getKey(), new ArrayList(appRequest.getValue()));
            }
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).revokeRuntimePermissions(bundledizedRequest, this.mDoDryRun, this.mReason, this.mCallingPackage, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(PermissionControllerManager.TAG, "Error revoking runtime permission", e);
            }
        }
    }

    private static final class PendingGetRuntimePermissionBackup extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> implements Consumer<byte[]> {
        private final FileReaderTask<PendingGetRuntimePermissionBackup> mBackupReader;
        private final OnGetRuntimePermissionBackupCallback mCallback;
        private final Executor mExecutor;
        private final UserHandle mUser;

        private PendingGetRuntimePermissionBackup(RemoteService service, UserHandle user, Executor executor, OnGetRuntimePermissionBackupCallback callback) {
            super(service);
            this.mUser = user;
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mBackupReader = new FileReaderTask<>(this);
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mBackupReader.cancel(true);
            this.mBackupReader.interruptRead();
        }

        public void run() {
            if (this.mBackupReader.getStatus() == AsyncTask.Status.PENDING) {
                this.mBackupReader.execute((Params[]) new Void[0]);
                ParcelFileDescriptor remotePipe = this.mBackupReader.getRemotePipe();
                try {
                    ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).getRuntimePermissionBackup(this.mUser, remotePipe);
                } catch (RemoteException e) {
                    Log.e(PermissionControllerManager.TAG, "Error getting runtime permission backup", e);
                } catch (Throwable th) {
                    IoUtils.closeQuietly(remotePipe);
                    throw th;
                }
                IoUtils.closeQuietly(remotePipe);
            }
        }

        /* JADX INFO: finally extract failed */
        public void accept(byte[] backup) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable(backup) {
                    private final /* synthetic */ byte[] f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        PermissionControllerManager.PendingGetRuntimePermissionBackup.this.mCallback.onGetRuntimePermissionsBackup(this.f$1);
                    }
                });
                Binder.restoreCallingIdentity(token);
                finish();
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(token);
                throw th;
            }
        }
    }

    private static final class PendingSetRuntimePermissionGrantStateByDeviceAdmin extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final Consumer<Boolean> mCallback;
        private final String mCallerPackageName;
        private final Executor mExecutor;
        private final int mGrantState;
        private final String mPackageName;
        private final String mPermission;
        private final RemoteCallback mRemoteCallback;

        private PendingSetRuntimePermissionGrantStateByDeviceAdmin(RemoteService service, String callerPackageName, String packageName, String permission, int grantState, Executor executor, Consumer<Boolean> callback) {
            super(service);
            this.mCallerPackageName = callerPackageName;
            this.mPackageName = packageName;
            this.mPermission = permission;
            this.mGrantState = grantState;
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(executor, callback) {
                private final /* synthetic */ Executor f$1;
                private final /* synthetic */ Consumer f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onResult(Bundle bundle) {
                    this.f$1.execute(new Runnable(this.f$2, bundle) {
                        private final /* synthetic */ Consumer f$1;
                        private final /* synthetic */ Bundle f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin.lambda$new$0(PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin.this, this.f$1, this.f$2);
                        }
                    });
                }
            }, (Handler) null);
        }

        public static /* synthetic */ void lambda$new$0(PendingSetRuntimePermissionGrantStateByDeviceAdmin pendingSetRuntimePermissionGrantStateByDeviceAdmin, Consumer callback, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                callback.accept(Boolean.valueOf(result.getBoolean(PermissionControllerManager.KEY_RESULT, false)));
            } finally {
                Binder.restoreCallingIdentity(token);
                pendingSetRuntimePermissionGrantStateByDeviceAdmin.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() {
                    public final void run() {
                        PermissionControllerManager.PendingSetRuntimePermissionGrantStateByDeviceAdmin.this.mCallback.accept(false);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        public void run() {
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).setRuntimePermissionGrantStateByDeviceAdmin(this.mCallerPackageName, this.mPackageName, this.mPermission, this.mGrantState, this.mRemoteCallback);
            } catch (RemoteException e) {
                String access$1000 = PermissionControllerManager.TAG;
                Log.e(access$1000, "Error setting permissions state for device admin " + this.mPackageName, e);
            }
        }
    }

    private static final class PendingRestoreRuntimePermissionBackup implements AbstractRemoteService.AsyncRequest<IPermissionController> {
        private final byte[] mBackup;
        private final FileWriterTask mBackupSender;
        private final UserHandle mUser;

        private PendingRestoreRuntimePermissionBackup(RemoteService service, byte[] backup, UserHandle user) {
            this.mBackup = backup;
            this.mUser = user;
            this.mBackupSender = new FileWriterTask();
        }

        public void run(IPermissionController service) {
            if (this.mBackupSender.getStatus() == AsyncTask.Status.PENDING) {
                this.mBackupSender.execute((Params[]) new byte[][]{this.mBackup});
                ParcelFileDescriptor remotePipe = this.mBackupSender.getRemotePipe();
                try {
                    service.restoreRuntimePermissionBackup(this.mUser, remotePipe);
                } catch (RemoteException e) {
                    Log.e(PermissionControllerManager.TAG, "Error sending runtime permission backup", e);
                    this.mBackupSender.cancel(false);
                    this.mBackupSender.interruptWrite();
                } catch (Throwable th) {
                    IoUtils.closeQuietly(remotePipe);
                    throw th;
                }
                IoUtils.closeQuietly(remotePipe);
            }
        }
    }

    private static final class PendingRestoreDelayedRuntimePermissionBackup extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final UserHandle mUser;

        private PendingRestoreDelayedRuntimePermissionBackup(RemoteService service, String packageName, UserHandle user, Executor executor, Consumer<Boolean> callback) {
            super(service);
            this.mPackageName = packageName;
            this.mUser = user;
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(executor, callback) {
                private final /* synthetic */ Executor f$1;
                private final /* synthetic */ Consumer f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onResult(Bundle bundle) {
                    this.f$1.execute(new Runnable(this.f$2, bundle) {
                        private final /* synthetic */ Consumer f$1;
                        private final /* synthetic */ Bundle f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup.lambda$new$0(PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup.this, this.f$1, this.f$2);
                        }
                    });
                }
            }, (Handler) null);
        }

        public static /* synthetic */ void lambda$new$0(PendingRestoreDelayedRuntimePermissionBackup pendingRestoreDelayedRuntimePermissionBackup, Consumer callback, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                callback.accept(Boolean.valueOf(result.getBoolean(PermissionControllerManager.KEY_RESULT, false)));
            } finally {
                Binder.restoreCallingIdentity(token);
                pendingRestoreDelayedRuntimePermissionBackup.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() {
                    public final void run() {
                        PermissionControllerManager.PendingRestoreDelayedRuntimePermissionBackup.this.mCallback.accept(true);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        public void run() {
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).restoreDelayedRuntimePermissionBackup(this.mPackageName, this.mUser, this.mRemoteCallback);
            } catch (RemoteException e) {
                String access$1000 = PermissionControllerManager.TAG;
                Log.e(access$1000, "Error restoring delayed permissions for " + this.mPackageName, e);
            }
        }
    }

    private static final class PendingGetAppPermissionRequest extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnGetAppPermissionResultCallback mCallback;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;

        private PendingGetAppPermissionRequest(RemoteService service, String packageName, OnGetAppPermissionResultCallback callback, Handler handler) {
            super(service);
            this.mPackageName = packageName;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(callback) {
                private final /* synthetic */ PermissionControllerManager.OnGetAppPermissionResultCallback f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Bundle bundle) {
                    PermissionControllerManager.PendingGetAppPermissionRequest.lambda$new$0(PermissionControllerManager.PendingGetAppPermissionRequest.this, this.f$1, bundle);
                }
            }, handler);
        }

        public static /* synthetic */ void lambda$new$0(PendingGetAppPermissionRequest pendingGetAppPermissionRequest, OnGetAppPermissionResultCallback callback, Bundle result) {
            List<RuntimePermissionPresentationInfo> permissions = null;
            if (result != null) {
                permissions = result.getParcelableArrayList(PermissionControllerManager.KEY_RESULT);
            }
            if (permissions == null) {
                permissions = Collections.emptyList();
            }
            callback.onGetAppPermissions(permissions);
            pendingGetAppPermissionRequest.finish();
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mCallback.onGetAppPermissions(Collections.emptyList());
        }

        public void run() {
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).getAppPermissions(this.mPackageName, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(PermissionControllerManager.TAG, "Error getting app permission", e);
            }
        }
    }

    private static final class PendingRevokeAppPermissionRequest implements AbstractRemoteService.AsyncRequest<IPermissionController> {
        private final String mPackageName;
        private final String mPermissionName;

        private PendingRevokeAppPermissionRequest(String packageName, String permissionName) {
            this.mPackageName = packageName;
            this.mPermissionName = permissionName;
        }

        public void run(IPermissionController remoteInterface) {
            try {
                remoteInterface.revokeRuntimePermission(this.mPackageName, this.mPermissionName);
            } catch (RemoteException e) {
                Log.e(PermissionControllerManager.TAG, "Error revoking app permission", e);
            }
        }
    }

    private static final class PendingCountPermissionAppsRequest extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnCountPermissionAppsResultCallback mCallback;
        private final int mFlags;
        private final List<String> mPermissionNames;
        private final RemoteCallback mRemoteCallback;

        private PendingCountPermissionAppsRequest(RemoteService service, List<String> permissionNames, int flags, OnCountPermissionAppsResultCallback callback, Handler handler) {
            super(service);
            this.mPermissionNames = permissionNames;
            this.mFlags = flags;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(callback) {
                private final /* synthetic */ PermissionControllerManager.OnCountPermissionAppsResultCallback f$1;

                {
                    this.f$1 = r2;
                }

                public final void onResult(Bundle bundle) {
                    PermissionControllerManager.PendingCountPermissionAppsRequest.lambda$new$0(PermissionControllerManager.PendingCountPermissionAppsRequest.this, this.f$1, bundle);
                }
            }, handler);
        }

        public static /* synthetic */ void lambda$new$0(PendingCountPermissionAppsRequest pendingCountPermissionAppsRequest, OnCountPermissionAppsResultCallback callback, Bundle result) {
            int numApps;
            if (result != null) {
                numApps = result.getInt(PermissionControllerManager.KEY_RESULT);
            } else {
                numApps = 0;
            }
            callback.onCountPermissionApps(numApps);
            pendingCountPermissionAppsRequest.finish();
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mCallback.onCountPermissionApps(0);
        }

        public void run() {
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).countPermissionApps(this.mPermissionNames, this.mFlags, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(PermissionControllerManager.TAG, "Error counting permission apps", e);
            }
        }
    }

    private static final class PendingGetPermissionUsagesRequest extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnPermissionUsageResultCallback mCallback;
        private final boolean mCountSystem;
        private final long mNumMillis;
        private final RemoteCallback mRemoteCallback;

        private PendingGetPermissionUsagesRequest(RemoteService service, boolean countSystem, long numMillis, Executor executor, OnPermissionUsageResultCallback callback) {
            super(service);
            this.mCountSystem = countSystem;
            this.mNumMillis = numMillis;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(executor, callback) {
                private final /* synthetic */ Executor f$1;
                private final /* synthetic */ PermissionControllerManager.OnPermissionUsageResultCallback f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onResult(Bundle bundle) {
                    this.f$1.execute(new Runnable(bundle, this.f$2) {
                        private final /* synthetic */ Bundle f$1;
                        private final /* synthetic */ PermissionControllerManager.OnPermissionUsageResultCallback f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            PermissionControllerManager.PendingGetPermissionUsagesRequest.lambda$new$0(PermissionControllerManager.PendingGetPermissionUsagesRequest.this, this.f$1, this.f$2);
                        }
                    });
                }
            }, (Handler) null);
        }

        public static /* synthetic */ void lambda$new$0(PendingGetPermissionUsagesRequest pendingGetPermissionUsagesRequest, Bundle result, OnPermissionUsageResultCallback callback) {
            List<RuntimePermissionUsageInfo> users;
            long token = Binder.clearCallingIdentity();
            if (result != null) {
                try {
                    users = result.getParcelableArrayList(PermissionControllerManager.KEY_RESULT);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(token);
                    pendingGetPermissionUsagesRequest.finish();
                    throw th;
                }
            } else {
                users = Collections.emptyList();
            }
            callback.onPermissionUsageResult(users);
            Binder.restoreCallingIdentity(token);
            pendingGetPermissionUsagesRequest.finish();
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mCallback.onPermissionUsageResult(Collections.emptyList());
        }

        public void run() {
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).getPermissionUsages(this.mCountSystem, this.mNumMillis, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(PermissionControllerManager.TAG, "Error counting permission users", e);
            }
        }
    }

    private static final class PendingGrantOrUpgradeDefaultRuntimePermissionsRequest extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final Consumer<Boolean> mCallback;
        private final RemoteCallback mRemoteCallback;

        private PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(RemoteService service, Executor executor, Consumer<Boolean> callback) {
            super(service);
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener(executor, callback) {
                private final /* synthetic */ Executor f$1;
                private final /* synthetic */ Consumer f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onResult(Bundle bundle) {
                    this.f$1.execute(new Runnable(this.f$2, bundle) {
                        private final /* synthetic */ Consumer f$1;
                        private final /* synthetic */ Bundle f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            PermissionControllerManager.PendingGrantOrUpgradeDefaultRuntimePermissionsRequest.lambda$new$0(PermissionControllerManager.PendingGrantOrUpgradeDefaultRuntimePermissionsRequest.this, this.f$1, this.f$2);
                        }
                    });
                }
            }, (Handler) null);
        }

        public static /* synthetic */ void lambda$new$0(PendingGrantOrUpgradeDefaultRuntimePermissionsRequest pendingGrantOrUpgradeDefaultRuntimePermissionsRequest, Consumer callback, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                callback.accept(Boolean.valueOf(result != null));
            } finally {
                Binder.restoreCallingIdentity(token);
                pendingGrantOrUpgradeDefaultRuntimePermissionsRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mCallback.accept(false);
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        public void run() {
            try {
                ((IPermissionController) ((RemoteService) getService()).getServiceInterface()).grantOrUpgradeDefaultRuntimePermissions(this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(PermissionControllerManager.TAG, "Error granting or upgrading runtime permissions", e);
            }
        }
    }
}
