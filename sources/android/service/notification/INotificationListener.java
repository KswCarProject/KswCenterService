package android.service.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.List;

public interface INotificationListener extends IInterface {
    void onActionClicked(String str, Notification.Action action, int i) throws RemoteException;

    void onAllowedAdjustmentsChanged() throws RemoteException;

    void onInterruptionFilterChanged(int i) throws RemoteException;

    void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onListenerHintsChanged(int i) throws RemoteException;

    void onNotificationChannelGroupModification(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) throws RemoteException;

    void onNotificationChannelModification(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) throws RemoteException;

    void onNotificationDirectReply(String str) throws RemoteException;

    void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel) throws RemoteException;

    void onNotificationExpansionChanged(String str, boolean z, boolean z2) throws RemoteException;

    void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException;

    void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) throws RemoteException;

    void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String str) throws RemoteException;

    void onNotificationsSeen(List<String> list) throws RemoteException;

    void onStatusBarIconsBehaviorChanged(boolean z) throws RemoteException;

    void onSuggestedReplySent(String str, CharSequence charSequence, int i) throws RemoteException;

    public static class Default implements INotificationListener {
        public void onListenerConnected(NotificationRankingUpdate update) throws RemoteException {
        }

        public void onNotificationPosted(IStatusBarNotificationHolder notificationHolder, NotificationRankingUpdate update) throws RemoteException {
        }

        public void onStatusBarIconsBehaviorChanged(boolean hideSilentStatusIcons) throws RemoteException {
        }

        public void onNotificationRemoved(IStatusBarNotificationHolder notificationHolder, NotificationRankingUpdate update, NotificationStats stats, int reason) throws RemoteException {
        }

        public void onNotificationRankingUpdate(NotificationRankingUpdate update) throws RemoteException {
        }

        public void onListenerHintsChanged(int hints) throws RemoteException {
        }

        public void onInterruptionFilterChanged(int interruptionFilter) throws RemoteException {
        }

        public void onNotificationChannelModification(String pkgName, UserHandle user, NotificationChannel channel, int modificationType) throws RemoteException {
        }

        public void onNotificationChannelGroupModification(String pkgName, UserHandle user, NotificationChannelGroup group, int modificationType) throws RemoteException {
        }

        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder notificationHolder, NotificationChannel channel) throws RemoteException {
        }

        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder notificationHolder, String snoozeCriterionId) throws RemoteException {
        }

        public void onNotificationsSeen(List<String> list) throws RemoteException {
        }

        public void onNotificationExpansionChanged(String key, boolean userAction, boolean expanded) throws RemoteException {
        }

        public void onNotificationDirectReply(String key) throws RemoteException {
        }

        public void onSuggestedReplySent(String key, CharSequence reply, int source) throws RemoteException {
        }

        public void onActionClicked(String key, Notification.Action action, int source) throws RemoteException {
        }

        public void onAllowedAdjustmentsChanged() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INotificationListener {
        private static final String DESCRIPTOR = "android.service.notification.INotificationListener";
        static final int TRANSACTION_onActionClicked = 16;
        static final int TRANSACTION_onAllowedAdjustmentsChanged = 17;
        static final int TRANSACTION_onInterruptionFilterChanged = 7;
        static final int TRANSACTION_onListenerConnected = 1;
        static final int TRANSACTION_onListenerHintsChanged = 6;
        static final int TRANSACTION_onNotificationChannelGroupModification = 9;
        static final int TRANSACTION_onNotificationChannelModification = 8;
        static final int TRANSACTION_onNotificationDirectReply = 14;
        static final int TRANSACTION_onNotificationEnqueuedWithChannel = 10;
        static final int TRANSACTION_onNotificationExpansionChanged = 13;
        static final int TRANSACTION_onNotificationPosted = 2;
        static final int TRANSACTION_onNotificationRankingUpdate = 5;
        static final int TRANSACTION_onNotificationRemoved = 4;
        static final int TRANSACTION_onNotificationSnoozedUntilContext = 11;
        static final int TRANSACTION_onNotificationsSeen = 12;
        static final int TRANSACTION_onStatusBarIconsBehaviorChanged = 3;
        static final int TRANSACTION_onSuggestedReplySent = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INotificationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INotificationListener)) {
                return new Proxy(obj);
            }
            return (INotificationListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onListenerConnected";
                case 2:
                    return "onNotificationPosted";
                case 3:
                    return "onStatusBarIconsBehaviorChanged";
                case 4:
                    return "onNotificationRemoved";
                case 5:
                    return "onNotificationRankingUpdate";
                case 6:
                    return "onListenerHintsChanged";
                case 7:
                    return "onInterruptionFilterChanged";
                case 8:
                    return "onNotificationChannelModification";
                case 9:
                    return "onNotificationChannelGroupModification";
                case 10:
                    return "onNotificationEnqueuedWithChannel";
                case 11:
                    return "onNotificationSnoozedUntilContext";
                case 12:
                    return "onNotificationsSeen";
                case 13:
                    return "onNotificationExpansionChanged";
                case 14:
                    return "onNotificationDirectReply";
                case 15:
                    return "onSuggestedReplySent";
                case 16:
                    return "onActionClicked";
                case 17:
                    return "onAllowedAdjustmentsChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.service.notification.NotificationRankingUpdate} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: android.service.notification.NotificationStats} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: android.app.NotificationChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: android.app.NotificationChannelGroup} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: android.app.NotificationChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v34, resolved type: android.app.Notification$Action} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1 */
        /* JADX WARNING: type inference failed for: r3v13 */
        /* JADX WARNING: type inference failed for: r3v30, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v43 */
        /* JADX WARNING: type inference failed for: r3v44 */
        /* JADX WARNING: type inference failed for: r3v45 */
        /* JADX WARNING: type inference failed for: r3v46 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.service.notification.INotificationListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x01c2
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x01a9;
                    case 2: goto L_0x018a;
                    case 3: goto L_0x017b;
                    case 4: goto L_0x0148;
                    case 5: goto L_0x012f;
                    case 6: goto L_0x0124;
                    case 7: goto L_0x0119;
                    case 8: goto L_0x00ea;
                    case 9: goto L_0x00bb;
                    case 10: goto L_0x009c;
                    case 11: goto L_0x0089;
                    case 12: goto L_0x007e;
                    case 13: goto L_0x0062;
                    case 14: goto L_0x0057;
                    case 15: goto L_0x0038;
                    case 16: goto L_0x0019;
                    case 17: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                r6.onAllowedAdjustmentsChanged()
                return r2
            L_0x0019:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x002f
                android.os.Parcelable$Creator<android.app.Notification$Action> r3 = android.app.Notification.Action.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.Notification$Action r3 = (android.app.Notification.Action) r3
                goto L_0x0030
            L_0x002f:
            L_0x0030:
                int r4 = r8.readInt()
                r6.onActionClicked(r1, r3, r4)
                return r2
            L_0x0038:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x004e
                android.os.Parcelable$Creator<java.lang.CharSequence> r3 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                goto L_0x004f
            L_0x004e:
            L_0x004f:
                int r4 = r8.readInt()
                r6.onSuggestedReplySent(r1, r3, r4)
                return r2
            L_0x0057:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.onNotificationDirectReply(r1)
                return r2
            L_0x0062:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0071
                r4 = r2
                goto L_0x0072
            L_0x0071:
                r4 = r1
            L_0x0072:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x007a
                r1 = r2
            L_0x007a:
                r6.onNotificationExpansionChanged(r3, r4, r1)
                return r2
            L_0x007e:
                r8.enforceInterface(r0)
                java.util.ArrayList r1 = r8.createStringArrayList()
                r6.onNotificationsSeen(r1)
                return r2
            L_0x0089:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.service.notification.IStatusBarNotificationHolder r1 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(r1)
                java.lang.String r3 = r8.readString()
                r6.onNotificationSnoozedUntilContext(r1, r3)
                return r2
            L_0x009c:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.service.notification.IStatusBarNotificationHolder r1 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(r1)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00b6
                android.os.Parcelable$Creator<android.app.NotificationChannel> r3 = android.app.NotificationChannel.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.NotificationChannel r3 = (android.app.NotificationChannel) r3
                goto L_0x00b7
            L_0x00b6:
            L_0x00b7:
                r6.onNotificationEnqueuedWithChannel(r1, r3)
                return r2
            L_0x00bb:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00d1
                android.os.Parcelable$Creator<android.os.UserHandle> r4 = android.os.UserHandle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.os.UserHandle r4 = (android.os.UserHandle) r4
                goto L_0x00d2
            L_0x00d1:
                r4 = r3
            L_0x00d2:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00e1
                android.os.Parcelable$Creator<android.app.NotificationChannelGroup> r3 = android.app.NotificationChannelGroup.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.NotificationChannelGroup r3 = (android.app.NotificationChannelGroup) r3
                goto L_0x00e2
            L_0x00e1:
            L_0x00e2:
                int r5 = r8.readInt()
                r6.onNotificationChannelGroupModification(r1, r4, r3, r5)
                return r2
            L_0x00ea:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0100
                android.os.Parcelable$Creator<android.os.UserHandle> r4 = android.os.UserHandle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.os.UserHandle r4 = (android.os.UserHandle) r4
                goto L_0x0101
            L_0x0100:
                r4 = r3
            L_0x0101:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0110
                android.os.Parcelable$Creator<android.app.NotificationChannel> r3 = android.app.NotificationChannel.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.NotificationChannel r3 = (android.app.NotificationChannel) r3
                goto L_0x0111
            L_0x0110:
            L_0x0111:
                int r5 = r8.readInt()
                r6.onNotificationChannelModification(r1, r4, r3, r5)
                return r2
            L_0x0119:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.onInterruptionFilterChanged(r1)
                return r2
            L_0x0124:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.onListenerHintsChanged(r1)
                return r2
            L_0x012f:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0142
                android.os.Parcelable$Creator<android.service.notification.NotificationRankingUpdate> r1 = android.service.notification.NotificationRankingUpdate.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.service.notification.NotificationRankingUpdate r3 = (android.service.notification.NotificationRankingUpdate) r3
                goto L_0x0143
            L_0x0142:
            L_0x0143:
                r1 = r3
                r6.onNotificationRankingUpdate(r1)
                return r2
            L_0x0148:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.service.notification.IStatusBarNotificationHolder r1 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(r1)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0162
                android.os.Parcelable$Creator<android.service.notification.NotificationRankingUpdate> r4 = android.service.notification.NotificationRankingUpdate.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.service.notification.NotificationRankingUpdate r4 = (android.service.notification.NotificationRankingUpdate) r4
                goto L_0x0163
            L_0x0162:
                r4 = r3
            L_0x0163:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0172
                android.os.Parcelable$Creator<android.service.notification.NotificationStats> r3 = android.service.notification.NotificationStats.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.service.notification.NotificationStats r3 = (android.service.notification.NotificationStats) r3
                goto L_0x0173
            L_0x0172:
            L_0x0173:
                int r5 = r8.readInt()
                r6.onNotificationRemoved(r1, r4, r3, r5)
                return r2
            L_0x017b:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0186
                r1 = r2
            L_0x0186:
                r6.onStatusBarIconsBehaviorChanged(r1)
                return r2
            L_0x018a:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.service.notification.IStatusBarNotificationHolder r1 = android.service.notification.IStatusBarNotificationHolder.Stub.asInterface(r1)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01a4
                android.os.Parcelable$Creator<android.service.notification.NotificationRankingUpdate> r3 = android.service.notification.NotificationRankingUpdate.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.service.notification.NotificationRankingUpdate r3 = (android.service.notification.NotificationRankingUpdate) r3
                goto L_0x01a5
            L_0x01a4:
            L_0x01a5:
                r6.onNotificationPosted(r1, r3)
                return r2
            L_0x01a9:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01bc
                android.os.Parcelable$Creator<android.service.notification.NotificationRankingUpdate> r1 = android.service.notification.NotificationRankingUpdate.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.service.notification.NotificationRankingUpdate r3 = (android.service.notification.NotificationRankingUpdate) r3
                goto L_0x01bd
            L_0x01bc:
            L_0x01bd:
                r1 = r3
                r6.onListenerConnected(r1)
                return r2
            L_0x01c2:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.notification.INotificationListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INotificationListener {
            public static INotificationListener sDefaultImpl;
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

            public void onListenerConnected(NotificationRankingUpdate update) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (update != null) {
                        _data.writeInt(1);
                        update.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onListenerConnected(update);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationPosted(IStatusBarNotificationHolder notificationHolder, NotificationRankingUpdate update) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(notificationHolder != null ? notificationHolder.asBinder() : null);
                    if (update != null) {
                        _data.writeInt(1);
                        update.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationPosted(notificationHolder, update);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStatusBarIconsBehaviorChanged(boolean hideSilentStatusIcons) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hideSilentStatusIcons);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStatusBarIconsBehaviorChanged(hideSilentStatusIcons);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationRemoved(IStatusBarNotificationHolder notificationHolder, NotificationRankingUpdate update, NotificationStats stats, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(notificationHolder != null ? notificationHolder.asBinder() : null);
                    if (update != null) {
                        _data.writeInt(1);
                        update.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (stats != null) {
                        _data.writeInt(1);
                        stats.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(reason);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationRemoved(notificationHolder, update, stats, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationRankingUpdate(NotificationRankingUpdate update) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (update != null) {
                        _data.writeInt(1);
                        update.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationRankingUpdate(update);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onListenerHintsChanged(int hints) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hints);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onListenerHintsChanged(hints);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onInterruptionFilterChanged(int interruptionFilter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(interruptionFilter);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onInterruptionFilterChanged(interruptionFilter);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationChannelModification(String pkgName, UserHandle user, NotificationChannel channel, int modificationType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(modificationType);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationChannelModification(pkgName, user, channel, modificationType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationChannelGroupModification(String pkgName, UserHandle user, NotificationChannelGroup group, int modificationType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (group != null) {
                        _data.writeInt(1);
                        group.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(modificationType);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationChannelGroupModification(pkgName, user, group, modificationType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder notificationHolder, NotificationChannel channel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(notificationHolder != null ? notificationHolder.asBinder() : null);
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationEnqueuedWithChannel(notificationHolder, channel);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder notificationHolder, String snoozeCriterionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(notificationHolder != null ? notificationHolder.asBinder() : null);
                    _data.writeString(snoozeCriterionId);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationSnoozedUntilContext(notificationHolder, snoozeCriterionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationsSeen(List<String> keys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(keys);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationsSeen(keys);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationExpansionChanged(String key, boolean userAction, boolean expanded) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(userAction);
                    _data.writeInt(expanded);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationExpansionChanged(key, userAction, expanded);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationDirectReply(String key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNotificationDirectReply(key);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSuggestedReplySent(String key, CharSequence reply, int source) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    if (reply != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(reply, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(source);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSuggestedReplySent(key, reply, source);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActionClicked(String key, Notification.Action action, int source) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    if (action != null) {
                        _data.writeInt(1);
                        action.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(source);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActionClicked(key, action, source);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAllowedAdjustmentsChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAllowedAdjustmentsChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INotificationListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INotificationListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
