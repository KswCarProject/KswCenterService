package android.app.role;

import android.app.ActivityThread;
import android.app.role.IRoleController;
import android.app.role.RoleControllerManager;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class RoleControllerManager {
    /* access modifiers changed from: private */
    public static final String LOG_TAG = RoleControllerManager.class.getSimpleName();
    private static volatile ComponentName sRemoteServiceComponentName;
    @GuardedBy({"sRemoteServicesLock"})
    private static final SparseArray<RemoteService> sRemoteServices = new SparseArray<>();
    private static final Object sRemoteServicesLock = new Object();
    private final RemoteService mRemoteService;

    public static void initializeRemoteServiceComponentName(Context context) {
        sRemoteServiceComponentName = getRemoteServiceComponentName(context);
    }

    public static RoleControllerManager createWithInitializedRemoteServiceComponentName(Handler handler, Context context) {
        return new RoleControllerManager(sRemoteServiceComponentName, handler, context);
    }

    private RoleControllerManager(ComponentName remoteServiceComponentName, Handler handler, Context context) {
        synchronized (sRemoteServicesLock) {
            int userId = context.getUserId();
            RemoteService remoteService = sRemoteServices.get(userId);
            if (remoteService == null) {
                remoteService = new RemoteService(ActivityThread.currentApplication(), remoteServiceComponentName, handler, userId);
                sRemoteServices.put(userId, remoteService);
            }
            this.mRemoteService = remoteService;
        }
    }

    public RoleControllerManager(Context context) {
        this(getRemoteServiceComponentName(context), context.getMainThreadHandler(), context);
    }

    private static ComponentName getRemoteServiceComponentName(Context context) {
        Intent intent = new Intent(RoleControllerService.SERVICE_INTERFACE);
        PackageManager packageManager = context.getPackageManager();
        intent.setPackage(packageManager.getPermissionControllerPackageName());
        return packageManager.resolveService(intent, 0).getComponentInfo().getComponentName();
    }

    public void grantDefaultRoles(Executor executor, Consumer<Boolean> callback) {
        this.mRemoteService.scheduleRequest(new GrantDefaultRolesRequest(this.mRemoteService, executor, callback));
    }

    public void onAddRoleHolder(String roleName, String packageName, @RoleManager.ManageHoldersFlags int flags, RemoteCallback callback) {
        this.mRemoteService.scheduleRequest(new OnAddRoleHolderRequest(this.mRemoteService, roleName, packageName, flags, callback));
    }

    public void onRemoveRoleHolder(String roleName, String packageName, @RoleManager.ManageHoldersFlags int flags, RemoteCallback callback) {
        this.mRemoteService.scheduleRequest(new OnRemoveRoleHolderRequest(this.mRemoteService, roleName, packageName, flags, callback));
    }

    public void onClearRoleHolders(String roleName, @RoleManager.ManageHoldersFlags int flags, RemoteCallback callback) {
        this.mRemoteService.scheduleRequest(new OnClearRoleHoldersRequest(this.mRemoteService, roleName, flags, callback));
    }

    public void isApplicationQualifiedForRole(String roleName, String packageName, Executor executor, Consumer<Boolean> callback) {
        this.mRemoteService.scheduleRequest(new IsApplicationQualifiedForRoleRequest(this.mRemoteService, roleName, packageName, executor, callback));
    }

    public void isRoleVisible(String roleName, Executor executor, Consumer<Boolean> callback) {
        this.mRemoteService.scheduleRequest(new IsRoleVisibleRequest(this.mRemoteService, roleName, executor, callback));
    }

    private static final class RemoteService extends AbstractMultiplePendingRequestsRemoteService<RemoteService, IRoleController> {
        private static final long REQUEST_TIMEOUT_MILLIS = 15000;
        private static final long UNBIND_DELAY_MILLIS = 15000;

        RemoteService(Context context, ComponentName componentName, Handler handler, int userId) {
            super(context, RoleControllerService.SERVICE_INTERFACE, componentName, userId, $$Lambda$RoleControllerManager$RemoteService$45dMO3SdHJhfBB_YKrC44Sznmoo.INSTANCE, handler, 0, false, 1);
        }

        static /* synthetic */ void lambda$new$0(RemoteService service) {
            String access$600 = RoleControllerManager.LOG_TAG;
            Log.e(access$600, "RemoteService " + service + " died");
        }

        public Handler getHandler() {
            return this.mHandler;
        }

        /* access modifiers changed from: protected */
        public IRoleController getServiceInterface(IBinder binder) {
            return IRoleController.Stub.asInterface(binder);
        }

        /* access modifiers changed from: protected */
        public long getTimeoutIdleBindMillis() {
            return 15000;
        }

        /* access modifiers changed from: protected */
        public long getRemoteRequestMillis() {
            return 15000;
        }

        public void scheduleRequest(AbstractRemoteService.BasePendingRequest<RemoteService, IRoleController> pendingRequest) {
            super.scheduleRequest(pendingRequest);
        }

        public void scheduleAsyncRequest(AbstractRemoteService.AsyncRequest<IRoleController> request) {
            super.scheduleAsyncRequest(request);
        }
    }

    private static final class GrantDefaultRolesRequest extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final RemoteCallback mRemoteCallback;

        private GrantDefaultRolesRequest(RemoteService service, Executor executor, Consumer<Boolean> callback) {
            super(service);
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                public final void onResult(Bundle bundle) {
                    RoleControllerManager.GrantDefaultRolesRequest.this.mExecutor.execute(new Runnable(bundle) {
                        private final /* synthetic */ Bundle f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            RoleControllerManager.GrantDefaultRolesRequest.lambda$new$0(RoleControllerManager.GrantDefaultRolesRequest.this, this.f$1);
                        }
                    });
                }
            });
        }

        public static /* synthetic */ void lambda$new$0(GrantDefaultRolesRequest grantDefaultRolesRequest, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                grantDefaultRolesRequest.mCallback.accept(Boolean.valueOf(result != null));
            } finally {
                Binder.restoreCallingIdentity(token);
                grantDefaultRolesRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mExecutor.execute(new Runnable() {
                public final void run() {
                    RoleControllerManager.GrantDefaultRolesRequest.this.mCallback.accept(false);
                }
            });
        }

        public void run() {
            try {
                ((IRoleController) ((RemoteService) getService()).getServiceInterface()).grantDefaultRoles(this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(RoleControllerManager.LOG_TAG, "Error calling grantDefaultRoles()", e);
            }
        }

        /* access modifiers changed from: protected */
        public void onFailed() {
            this.mRemoteCallback.sendResult((Bundle) null);
        }
    }

    private static final class OnAddRoleHolderRequest extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final RemoteCallback mCallback;
        @RoleManager.ManageHoldersFlags
        private final int mFlags;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private OnAddRoleHolderRequest(RemoteService service, String roleName, String packageName, @RoleManager.ManageHoldersFlags int flags, RemoteCallback callback) {
            super(service);
            this.mRoleName = roleName;
            this.mPackageName = packageName;
            this.mFlags = flags;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                public final void onResult(Bundle bundle) {
                    RoleControllerManager.OnAddRoleHolderRequest.lambda$new$0(RoleControllerManager.OnAddRoleHolderRequest.this, bundle);
                }
            });
        }

        public static /* synthetic */ void lambda$new$0(OnAddRoleHolderRequest onAddRoleHolderRequest, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                onAddRoleHolderRequest.mCallback.sendResult(result);
            } finally {
                Binder.restoreCallingIdentity(token);
                onAddRoleHolderRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mCallback.sendResult((Bundle) null);
        }

        public void run() {
            try {
                ((IRoleController) ((RemoteService) getService()).getServiceInterface()).onAddRoleHolder(this.mRoleName, this.mPackageName, this.mFlags, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(RoleControllerManager.LOG_TAG, "Error calling onAddRoleHolder()", e);
            }
        }
    }

    private static final class OnRemoveRoleHolderRequest extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final RemoteCallback mCallback;
        @RoleManager.ManageHoldersFlags
        private final int mFlags;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private OnRemoveRoleHolderRequest(RemoteService service, String roleName, String packageName, @RoleManager.ManageHoldersFlags int flags, RemoteCallback callback) {
            super(service);
            this.mRoleName = roleName;
            this.mPackageName = packageName;
            this.mFlags = flags;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                public final void onResult(Bundle bundle) {
                    RoleControllerManager.OnRemoveRoleHolderRequest.lambda$new$0(RoleControllerManager.OnRemoveRoleHolderRequest.this, bundle);
                }
            });
        }

        public static /* synthetic */ void lambda$new$0(OnRemoveRoleHolderRequest onRemoveRoleHolderRequest, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                onRemoveRoleHolderRequest.mCallback.sendResult(result);
            } finally {
                Binder.restoreCallingIdentity(token);
                onRemoveRoleHolderRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mCallback.sendResult((Bundle) null);
        }

        public void run() {
            try {
                ((IRoleController) ((RemoteService) getService()).getServiceInterface()).onRemoveRoleHolder(this.mRoleName, this.mPackageName, this.mFlags, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(RoleControllerManager.LOG_TAG, "Error calling onRemoveRoleHolder()", e);
            }
        }
    }

    private static final class OnClearRoleHoldersRequest extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final RemoteCallback mCallback;
        @RoleManager.ManageHoldersFlags
        private final int mFlags;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private OnClearRoleHoldersRequest(RemoteService service, String roleName, @RoleManager.ManageHoldersFlags int flags, RemoteCallback callback) {
            super(service);
            this.mRoleName = roleName;
            this.mFlags = flags;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                public final void onResult(Bundle bundle) {
                    RoleControllerManager.OnClearRoleHoldersRequest.lambda$new$0(RoleControllerManager.OnClearRoleHoldersRequest.this, bundle);
                }
            });
        }

        public static /* synthetic */ void lambda$new$0(OnClearRoleHoldersRequest onClearRoleHoldersRequest, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                onClearRoleHoldersRequest.mCallback.sendResult(result);
            } finally {
                Binder.restoreCallingIdentity(token);
                onClearRoleHoldersRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mCallback.sendResult((Bundle) null);
        }

        public void run() {
            try {
                ((IRoleController) ((RemoteService) getService()).getServiceInterface()).onClearRoleHolders(this.mRoleName, this.mFlags, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(RoleControllerManager.LOG_TAG, "Error calling onClearRoleHolders()", e);
            }
        }
    }

    private static final class IsApplicationQualifiedForRoleRequest extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private IsApplicationQualifiedForRoleRequest(RemoteService service, String roleName, String packageName, Executor executor, Consumer<Boolean> callback) {
            super(service);
            this.mRoleName = roleName;
            this.mPackageName = packageName;
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                public final void onResult(Bundle bundle) {
                    RoleControllerManager.IsApplicationQualifiedForRoleRequest.this.mExecutor.execute(new Runnable(bundle) {
                        private final /* synthetic */ Bundle f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            RoleControllerManager.IsApplicationQualifiedForRoleRequest.lambda$new$0(RoleControllerManager.IsApplicationQualifiedForRoleRequest.this, this.f$1);
                        }
                    });
                }
            });
        }

        public static /* synthetic */ void lambda$new$0(IsApplicationQualifiedForRoleRequest isApplicationQualifiedForRoleRequest, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                isApplicationQualifiedForRoleRequest.mCallback.accept(Boolean.valueOf(result != null));
            } finally {
                Binder.restoreCallingIdentity(token);
                isApplicationQualifiedForRoleRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mExecutor.execute(new Runnable() {
                public final void run() {
                    RoleControllerManager.IsApplicationQualifiedForRoleRequest.this.mCallback.accept(false);
                }
            });
        }

        public void run() {
            try {
                ((IRoleController) ((RemoteService) getService()).getServiceInterface()).isApplicationQualifiedForRole(this.mRoleName, this.mPackageName, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(RoleControllerManager.LOG_TAG, "Error calling isApplicationQualifiedForRole()", e);
            }
        }
    }

    private static final class IsRoleVisibleRequest extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private IsRoleVisibleRequest(RemoteService service, String roleName, Executor executor, Consumer<Boolean> callback) {
            super(service);
            this.mRoleName = roleName;
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mRemoteCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                public final void onResult(Bundle bundle) {
                    RoleControllerManager.IsRoleVisibleRequest.this.mExecutor.execute(new Runnable(bundle) {
                        private final /* synthetic */ Bundle f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            RoleControllerManager.IsRoleVisibleRequest.lambda$new$0(RoleControllerManager.IsRoleVisibleRequest.this, this.f$1);
                        }
                    });
                }
            });
        }

        public static /* synthetic */ void lambda$new$0(IsRoleVisibleRequest isRoleVisibleRequest, Bundle result) {
            long token = Binder.clearCallingIdentity();
            try {
                isRoleVisibleRequest.mCallback.accept(Boolean.valueOf(result != null));
            } finally {
                Binder.restoreCallingIdentity(token);
                isRoleVisibleRequest.finish();
            }
        }

        /* access modifiers changed from: protected */
        public void onTimeout(RemoteService remoteService) {
            this.mExecutor.execute(new Runnable() {
                public final void run() {
                    RoleControllerManager.IsRoleVisibleRequest.this.mCallback.accept(false);
                }
            });
        }

        public void run() {
            try {
                ((IRoleController) ((RemoteService) getService()).getServiceInterface()).isRoleVisible(this.mRoleName, this.mRemoteCallback);
            } catch (RemoteException e) {
                Log.e(RoleControllerManager.LOG_TAG, "Error calling isRoleVisible()", e);
            }
        }
    }
}
