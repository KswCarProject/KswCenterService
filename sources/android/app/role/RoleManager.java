package android.app.role;

import android.annotation.SystemApi;
import android.app.role.IOnRoleHoldersChangedListener;
import android.app.role.IRoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class RoleManager {
    public static final String ACTION_REQUEST_ROLE = "android.app.role.action.REQUEST_ROLE";
    private static final String LOG_TAG = RoleManager.class.getSimpleName();
    @SystemApi
    public static final int MANAGE_HOLDERS_FLAG_DONT_KILL_APP = 1;
    public static final String PERMISSION_MANAGE_ROLES_FROM_CONTROLLER = "com.android.permissioncontroller.permission.MANAGE_ROLES_FROM_CONTROLLER";
    public static final String ROLE_ASSISTANT = "android.app.role.ASSISTANT";
    public static final String ROLE_BROWSER = "android.app.role.BROWSER";
    public static final String ROLE_CALL_REDIRECTION = "android.app.role.CALL_REDIRECTION";
    public static final String ROLE_CALL_SCREENING = "android.app.role.CALL_SCREENING";
    public static final String ROLE_DIALER = "android.app.role.DIALER";
    public static final String ROLE_EMERGENCY = "android.app.role.EMERGENCY";
    public static final String ROLE_HOME = "android.app.role.HOME";
    public static final String ROLE_SMS = "android.app.role.SMS";
    private final Context mContext;
    @GuardedBy({"mListenersLock"})
    private final SparseArray<ArrayMap<OnRoleHoldersChangedListener, OnRoleHoldersChangedListenerDelegate>> mListeners = new SparseArray<>();
    private final Object mListenersLock = new Object();
    private final IRoleManager mService;

    public @interface ManageHoldersFlags {
    }

    public RoleManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mService = IRoleManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.ROLE_SERVICE));
    }

    public Intent createRequestRoleIntent(String roleName) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Intent intent = new Intent(ACTION_REQUEST_ROLE);
        intent.setPackage(this.mContext.getPackageManager().getPermissionControllerPackageName());
        intent.putExtra(Intent.EXTRA_ROLE_NAME, roleName);
        return intent;
    }

    public boolean isRoleAvailable(String roleName) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        try {
            return this.mService.isRoleAvailable(roleName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRoleHeld(String roleName) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        try {
            return this.mService.isRoleHeld(roleName, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getRoleHolders(String roleName) {
        return getRoleHoldersAsUser(roleName, Process.myUserHandle());
    }

    @SystemApi
    public List<String> getRoleHoldersAsUser(String roleName, UserHandle user) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Preconditions.checkNotNull(user, "user cannot be null");
        try {
            return this.mService.getRoleHoldersAsUser(roleName, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addRoleHolderAsUser(String roleName, String packageName, @ManageHoldersFlags int flags, UserHandle user, Executor executor, Consumer<Boolean> callback) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(packageName, "packageName cannot be null or empty");
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(callback, "callback cannot be null");
        try {
            this.mService.addRoleHolderAsUser(roleName, packageName, flags, user.getIdentifier(), createRemoteCallback(executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removeRoleHolderAsUser(String roleName, String packageName, @ManageHoldersFlags int flags, UserHandle user, Executor executor, Consumer<Boolean> callback) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(packageName, "packageName cannot be null or empty");
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(callback, "callback cannot be null");
        try {
            this.mService.removeRoleHolderAsUser(roleName, packageName, flags, user.getIdentifier(), createRemoteCallback(executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void clearRoleHoldersAsUser(String roleName, @ManageHoldersFlags int flags, UserHandle user, Executor executor, Consumer<Boolean> callback) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(callback, "callback cannot be null");
        try {
            this.mService.clearRoleHoldersAsUser(roleName, flags, user.getIdentifier(), createRemoteCallback(executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static RemoteCallback createRemoteCallback(Executor executor, Consumer<Boolean> callback) {
        return new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener(executor, callback) {
            private final /* synthetic */ Executor f$0;
            private final /* synthetic */ Consumer f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void onResult(Bundle bundle) {
                this.f$0.execute(new Runnable(this.f$1) {
                    private final /* synthetic */ Consumer f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        RoleManager.lambda$createRemoteCallback$0(Bundle.this, this.f$1);
                    }
                });
            }
        });
    }

    static /* synthetic */ void lambda$createRemoteCallback$0(Bundle result, Consumer callback) {
        boolean successful = result != null;
        long token = Binder.clearCallingIdentity();
        try {
            callback.accept(Boolean.valueOf(successful));
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    @SystemApi
    public void addOnRoleHoldersChangedListenerAsUser(Executor executor, OnRoleHoldersChangedListener listener, UserHandle user) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(listener, "listener cannot be null");
        Preconditions.checkNotNull(user, "user cannot be null");
        int userId = user.getIdentifier();
        synchronized (this.mListenersLock) {
            ArrayMap<OnRoleHoldersChangedListener, OnRoleHoldersChangedListenerDelegate> listeners = this.mListeners.get(userId);
            if (listeners == null) {
                listeners = new ArrayMap<>();
                this.mListeners.put(userId, listeners);
            } else if (listeners.containsKey(listener)) {
                return;
            }
            OnRoleHoldersChangedListenerDelegate listenerDelegate = new OnRoleHoldersChangedListenerDelegate(executor, listener);
            try {
                this.mService.addOnRoleHoldersChangedListenerAsUser(listenerDelegate, userId);
                listeners.put(listener, listenerDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        return;
     */
    @android.annotation.SystemApi
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeOnRoleHoldersChangedListenerAsUser(android.app.role.OnRoleHoldersChangedListener r7, android.os.UserHandle r8) {
        /*
            r6 = this;
            java.lang.String r0 = "listener cannot be null"
            com.android.internal.util.Preconditions.checkNotNull(r7, r0)
            java.lang.String r0 = "user cannot be null"
            com.android.internal.util.Preconditions.checkNotNull(r8, r0)
            int r0 = r8.getIdentifier()
            java.lang.Object r1 = r6.mListenersLock
            monitor-enter(r1)
            android.util.SparseArray<android.util.ArrayMap<android.app.role.OnRoleHoldersChangedListener, android.app.role.RoleManager$OnRoleHoldersChangedListenerDelegate>> r2 = r6.mListeners     // Catch:{ all -> 0x0048 }
            java.lang.Object r2 = r2.get(r0)     // Catch:{ all -> 0x0048 }
            android.util.ArrayMap r2 = (android.util.ArrayMap) r2     // Catch:{ all -> 0x0048 }
            if (r2 != 0) goto L_0x001e
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            return
        L_0x001e:
            java.lang.Object r3 = r2.get(r7)     // Catch:{ all -> 0x0048 }
            android.app.role.RoleManager$OnRoleHoldersChangedListenerDelegate r3 = (android.app.role.RoleManager.OnRoleHoldersChangedListenerDelegate) r3     // Catch:{ all -> 0x0048 }
            if (r3 != 0) goto L_0x0028
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            return
        L_0x0028:
            android.app.role.IRoleManager r4 = r6.mService     // Catch:{ RemoteException -> 0x0042 }
            int r5 = r8.getIdentifier()     // Catch:{ RemoteException -> 0x0042 }
            r4.removeOnRoleHoldersChangedListenerAsUser(r3, r5)     // Catch:{ RemoteException -> 0x0042 }
            r2.remove(r7)     // Catch:{ all -> 0x0048 }
            boolean r4 = r2.isEmpty()     // Catch:{ all -> 0x0048 }
            if (r4 == 0) goto L_0x0040
            android.util.SparseArray<android.util.ArrayMap<android.app.role.OnRoleHoldersChangedListener, android.app.role.RoleManager$OnRoleHoldersChangedListenerDelegate>> r4 = r6.mListeners     // Catch:{ all -> 0x0048 }
            r4.remove(r0)     // Catch:{ all -> 0x0048 }
        L_0x0040:
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            return
        L_0x0042:
            r4 = move-exception
            java.lang.RuntimeException r5 = r4.rethrowFromSystemServer()     // Catch:{ all -> 0x0048 }
            throw r5     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.role.RoleManager.removeOnRoleHoldersChangedListenerAsUser(android.app.role.OnRoleHoldersChangedListener, android.os.UserHandle):void");
    }

    @SystemApi
    public void setRoleNamesFromController(List<String> roleNames) {
        Preconditions.checkNotNull(roleNames, "roleNames cannot be null");
        try {
            this.mService.setRoleNamesFromController(roleNames);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean addRoleHolderFromController(String roleName, String packageName) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(packageName, "packageName cannot be null or empty");
        try {
            return this.mService.addRoleHolderFromController(roleName, packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean removeRoleHolderFromController(String roleName, String packageName) {
        Preconditions.checkStringNotEmpty(roleName, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(packageName, "packageName cannot be null or empty");
        try {
            return this.mService.removeRoleHolderFromController(roleName, packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getHeldRolesFromController(String packageName) {
        Preconditions.checkStringNotEmpty(packageName, "packageName cannot be null or empty");
        try {
            return this.mService.getHeldRolesFromController(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getDefaultSmsPackage(int userId) {
        try {
            return this.mService.getDefaultSmsPackage(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class OnRoleHoldersChangedListenerDelegate extends IOnRoleHoldersChangedListener.Stub {
        private final Executor mExecutor;
        private final OnRoleHoldersChangedListener mListener;

        OnRoleHoldersChangedListenerDelegate(Executor executor, OnRoleHoldersChangedListener listener) {
            this.mExecutor = executor;
            this.mListener = listener;
        }

        public void onRoleHoldersChanged(String roleName, int userId) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(PooledLambda.obtainRunnable($$Lambda$o94o2jK_eiIVw3oY_QJ49zpAA.INSTANCE, this.mListener, roleName, UserHandle.of(userId)));
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }
    }
}
