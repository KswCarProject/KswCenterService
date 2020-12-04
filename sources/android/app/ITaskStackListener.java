package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITaskStackListener extends IInterface {
    public static final int FORCED_RESIZEABLE_REASON_SECONDARY_DISPLAY = 2;
    public static final int FORCED_RESIZEABLE_REASON_SPLIT_SCREEN = 1;

    void onActivityDismissingDockedStack() throws RemoteException;

    void onActivityForcedResizable(String str, int i, int i2) throws RemoteException;

    void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException;

    void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException;

    void onActivityUnpinned() throws RemoteException;

    void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onPinnedActivityRestartAttempt(boolean z) throws RemoteException;

    void onPinnedStackAnimationEnded() throws RemoteException;

    void onPinnedStackAnimationStarted() throws RemoteException;

    void onSizeCompatModeActivityChanged(int i, IBinder iBinder) throws RemoteException;

    void onTaskCreated(int i, ComponentName componentName) throws RemoteException;

    void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskDisplayChanged(int i, int i2) throws RemoteException;

    void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskProfileLocked(int i, int i2) throws RemoteException;

    void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskRemoved(int i) throws RemoteException;

    void onTaskSnapshotChanged(int i, ActivityManager.TaskSnapshot taskSnapshot) throws RemoteException;

    void onTaskStackChanged() throws RemoteException;

    public static class Default implements ITaskStackListener {
        public void onTaskStackChanged() throws RemoteException {
        }

        public void onActivityPinned(String packageName, int userId, int taskId, int stackId) throws RemoteException {
        }

        public void onActivityUnpinned() throws RemoteException {
        }

        public void onPinnedActivityRestartAttempt(boolean clearedTask) throws RemoteException {
        }

        public void onPinnedStackAnimationStarted() throws RemoteException {
        }

        public void onPinnedStackAnimationEnded() throws RemoteException {
        }

        public void onActivityForcedResizable(String packageName, int taskId, int reason) throws RemoteException {
        }

        public void onActivityDismissingDockedStack() throws RemoteException {
        }

        public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId) throws RemoteException {
        }

        public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId) throws RemoteException {
        }

        public void onTaskCreated(int taskId, ComponentName componentName) throws RemoteException {
        }

        public void onTaskRemoved(int taskId) throws RemoteException {
        }

        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
        }

        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
        }

        public void onActivityRequestedOrientationChanged(int taskId, int requestedOrientation) throws RemoteException {
        }

        public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
        }

        public void onTaskProfileLocked(int taskId, int userId) throws RemoteException {
        }

        public void onTaskSnapshotChanged(int taskId, ActivityManager.TaskSnapshot snapshot) throws RemoteException {
        }

        public void onSizeCompatModeActivityChanged(int displayId, IBinder activityToken) throws RemoteException {
        }

        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
        }

        public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITaskStackListener {
        private static final String DESCRIPTOR = "android.app.ITaskStackListener";
        static final int TRANSACTION_onActivityDismissingDockedStack = 8;
        static final int TRANSACTION_onActivityForcedResizable = 7;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayFailed = 9;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayRerouted = 10;
        static final int TRANSACTION_onActivityPinned = 2;
        static final int TRANSACTION_onActivityRequestedOrientationChanged = 15;
        static final int TRANSACTION_onActivityUnpinned = 3;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 20;
        static final int TRANSACTION_onPinnedActivityRestartAttempt = 4;
        static final int TRANSACTION_onPinnedStackAnimationEnded = 6;
        static final int TRANSACTION_onPinnedStackAnimationStarted = 5;
        static final int TRANSACTION_onSizeCompatModeActivityChanged = 19;
        static final int TRANSACTION_onTaskCreated = 11;
        static final int TRANSACTION_onTaskDescriptionChanged = 14;
        static final int TRANSACTION_onTaskDisplayChanged = 21;
        static final int TRANSACTION_onTaskMovedToFront = 13;
        static final int TRANSACTION_onTaskProfileLocked = 17;
        static final int TRANSACTION_onTaskRemovalStarted = 16;
        static final int TRANSACTION_onTaskRemoved = 12;
        static final int TRANSACTION_onTaskSnapshotChanged = 18;
        static final int TRANSACTION_onTaskStackChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITaskStackListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITaskStackListener)) {
                return new Proxy(obj);
            }
            return (ITaskStackListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onTaskStackChanged";
                case 2:
                    return "onActivityPinned";
                case 3:
                    return "onActivityUnpinned";
                case 4:
                    return "onPinnedActivityRestartAttempt";
                case 5:
                    return "onPinnedStackAnimationStarted";
                case 6:
                    return "onPinnedStackAnimationEnded";
                case 7:
                    return "onActivityForcedResizable";
                case 8:
                    return "onActivityDismissingDockedStack";
                case 9:
                    return "onActivityLaunchOnSecondaryDisplayFailed";
                case 10:
                    return "onActivityLaunchOnSecondaryDisplayRerouted";
                case 11:
                    return "onTaskCreated";
                case 12:
                    return "onTaskRemoved";
                case 13:
                    return "onTaskMovedToFront";
                case 14:
                    return "onTaskDescriptionChanged";
                case 15:
                    return "onActivityRequestedOrientationChanged";
                case 16:
                    return "onTaskRemovalStarted";
                case 17:
                    return "onTaskProfileLocked";
                case 18:
                    return "onTaskSnapshotChanged";
                case 19:
                    return "onSizeCompatModeActivityChanged";
                case 20:
                    return "onBackPressedOnTaskRoot";
                case 21:
                    return "onTaskDisplayChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.app.ActivityManager$RunningTaskInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.app.ActivityManager$RunningTaskInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: android.app.ActivityManager$RunningTaskInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: android.app.ActivityManager$RunningTaskInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.app.ActivityManager$RunningTaskInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.app.ActivityManager$TaskSnapshot} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v40, resolved type: android.app.ActivityManager$RunningTaskInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.app.ITaskStackListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x017d
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0176;
                    case 2: goto L_0x015f;
                    case 3: goto L_0x0158;
                    case 4: goto L_0x0148;
                    case 5: goto L_0x0141;
                    case 6: goto L_0x013a;
                    case 7: goto L_0x0127;
                    case 8: goto L_0x0120;
                    case 9: goto L_0x0105;
                    case 10: goto L_0x00ea;
                    case 11: goto L_0x00cf;
                    case 12: goto L_0x00c4;
                    case 13: goto L_0x00ad;
                    case 14: goto L_0x0096;
                    case 15: goto L_0x0087;
                    case 16: goto L_0x0070;
                    case 17: goto L_0x0061;
                    case 18: goto L_0x0046;
                    case 19: goto L_0x0037;
                    case 20: goto L_0x0020;
                    case 21: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.onTaskDisplayChanged(r1, r3)
                return r2
            L_0x0020:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0032
                android.os.Parcelable$Creator<android.app.ActivityManager$RunningTaskInfo> r1 = android.app.ActivityManager.RunningTaskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
                goto L_0x0033
            L_0x0032:
            L_0x0033:
                r6.onBackPressedOnTaskRoot(r1)
                return r2
            L_0x0037:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                android.os.IBinder r3 = r8.readStrongBinder()
                r6.onSizeCompatModeActivityChanged(r1, r3)
                return r2
            L_0x0046:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x005c
                android.os.Parcelable$Creator<android.app.ActivityManager$TaskSnapshot> r1 = android.app.ActivityManager.TaskSnapshot.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$TaskSnapshot r1 = (android.app.ActivityManager.TaskSnapshot) r1
                goto L_0x005d
            L_0x005c:
            L_0x005d:
                r6.onTaskSnapshotChanged(r3, r1)
                return r2
            L_0x0061:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.onTaskProfileLocked(r1, r3)
                return r2
            L_0x0070:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0082
                android.os.Parcelable$Creator<android.app.ActivityManager$RunningTaskInfo> r1 = android.app.ActivityManager.RunningTaskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
                goto L_0x0083
            L_0x0082:
            L_0x0083:
                r6.onTaskRemovalStarted(r1)
                return r2
            L_0x0087:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.onActivityRequestedOrientationChanged(r1, r3)
                return r2
            L_0x0096:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00a8
                android.os.Parcelable$Creator<android.app.ActivityManager$RunningTaskInfo> r1 = android.app.ActivityManager.RunningTaskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
                goto L_0x00a9
            L_0x00a8:
            L_0x00a9:
                r6.onTaskDescriptionChanged(r1)
                return r2
            L_0x00ad:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<android.app.ActivityManager$RunningTaskInfo> r1 = android.app.ActivityManager.RunningTaskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
                goto L_0x00c0
            L_0x00bf:
            L_0x00c0:
                r6.onTaskMovedToFront(r1)
                return r2
            L_0x00c4:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.onTaskRemoved(r1)
                return r2
            L_0x00cf:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00e5
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x00e6
            L_0x00e5:
            L_0x00e6:
                r6.onTaskCreated(r3, r1)
                return r2
            L_0x00ea:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00fc
                android.os.Parcelable$Creator<android.app.ActivityManager$RunningTaskInfo> r1 = android.app.ActivityManager.RunningTaskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
                goto L_0x00fd
            L_0x00fc:
            L_0x00fd:
                int r3 = r8.readInt()
                r6.onActivityLaunchOnSecondaryDisplayRerouted(r1, r3)
                return r2
            L_0x0105:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0117
                android.os.Parcelable$Creator<android.app.ActivityManager$RunningTaskInfo> r1 = android.app.ActivityManager.RunningTaskInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.ActivityManager$RunningTaskInfo r1 = (android.app.ActivityManager.RunningTaskInfo) r1
                goto L_0x0118
            L_0x0117:
            L_0x0118:
                int r3 = r8.readInt()
                r6.onActivityLaunchOnSecondaryDisplayFailed(r1, r3)
                return r2
            L_0x0120:
                r8.enforceInterface(r0)
                r6.onActivityDismissingDockedStack()
                return r2
            L_0x0127:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                r6.onActivityForcedResizable(r1, r3, r4)
                return r2
            L_0x013a:
                r8.enforceInterface(r0)
                r6.onPinnedStackAnimationEnded()
                return r2
            L_0x0141:
                r8.enforceInterface(r0)
                r6.onPinnedStackAnimationStarted()
                return r2
            L_0x0148:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0153
                r1 = r2
                goto L_0x0154
            L_0x0153:
                r1 = 0
            L_0x0154:
                r6.onPinnedActivityRestartAttempt(r1)
                return r2
            L_0x0158:
                r8.enforceInterface(r0)
                r6.onActivityUnpinned()
                return r2
            L_0x015f:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                r6.onActivityPinned(r1, r3, r4, r5)
                return r2
            L_0x0176:
                r8.enforceInterface(r0)
                r6.onTaskStackChanged()
                return r2
            L_0x017d:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.ITaskStackListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITaskStackListener {
            public static ITaskStackListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void onTaskStackChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskStackChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityPinned(String packageName, int userId, int taskId, int stackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityPinned(packageName, userId, taskId, stackId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityUnpinned() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityUnpinned();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPinnedActivityRestartAttempt(boolean clearedTask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clearedTask);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPinnedActivityRestartAttempt(clearedTask);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPinnedStackAnimationStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPinnedStackAnimationStarted();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPinnedStackAnimationEnded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPinnedStackAnimationEnded();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityForcedResizable(String packageName, int taskId, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(taskId);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityForcedResizable(packageName, taskId, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityDismissingDockedStack() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityDismissingDockedStack();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (taskInfo != null) {
                        _data.writeInt(1);
                        taskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(requestedDisplayId);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityLaunchOnSecondaryDisplayFailed(taskInfo, requestedDisplayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (taskInfo != null) {
                        _data.writeInt(1);
                        taskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(requestedDisplayId);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityLaunchOnSecondaryDisplayRerouted(taskInfo, requestedDisplayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskCreated(int taskId, ComponentName componentName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskCreated(taskId, componentName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskRemoved(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskRemoved(taskId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (taskInfo != null) {
                        _data.writeInt(1);
                        taskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskMovedToFront(taskInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (taskInfo != null) {
                        _data.writeInt(1);
                        taskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskDescriptionChanged(taskInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityRequestedOrientationChanged(int taskId, int requestedOrientation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(requestedOrientation);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityRequestedOrientationChanged(taskId, requestedOrientation);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (taskInfo != null) {
                        _data.writeInt(1);
                        taskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskRemovalStarted(taskInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskProfileLocked(int taskId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskProfileLocked(taskId, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskSnapshotChanged(int taskId, ActivityManager.TaskSnapshot snapshot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (snapshot != null) {
                        _data.writeInt(1);
                        snapshot.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskSnapshotChanged(taskId, snapshot);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSizeCompatModeActivityChanged(int displayId, IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(activityToken);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSizeCompatModeActivityChanged(displayId, activityToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (taskInfo != null) {
                        _data.writeInt(1);
                        taskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBackPressedOnTaskRoot(taskInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(newDisplayId);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTaskDisplayChanged(taskId, newDisplayId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITaskStackListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITaskStackListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
