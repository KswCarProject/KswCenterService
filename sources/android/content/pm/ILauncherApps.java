package android.content.pm;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.IntentSender;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.List;

public interface ILauncherApps extends IInterface {
    void addOnAppsChangedListener(String str, IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException;

    ParceledListSlice getAllSessions(String str) throws RemoteException;

    LauncherApps.AppUsageLimit getAppUsageLimit(String str, String str2, UserHandle userHandle) throws RemoteException;

    ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException;

    ParceledListSlice getLauncherActivities(String str, String str2, UserHandle userHandle) throws RemoteException;

    ParceledListSlice getShortcutConfigActivities(String str, String str2, UserHandle userHandle) throws RemoteException;

    IntentSender getShortcutConfigActivityIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, int i) throws RemoteException;

    int getShortcutIconResId(String str, String str2, String str3, int i) throws RemoteException;

    ParceledListSlice getShortcuts(String str, long j, String str2, List list, ComponentName componentName, int i, UserHandle userHandle) throws RemoteException;

    Bundle getSuspendedPackageLauncherExtras(String str, UserHandle userHandle) throws RemoteException;

    boolean hasShortcutHostPermission(String str) throws RemoteException;

    boolean isActivityEnabled(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean isPackageEnabled(String str, String str2, UserHandle userHandle) throws RemoteException;

    void pinShortcuts(String str, String str2, List<String> list, UserHandle userHandle) throws RemoteException;

    void registerPackageInstallerCallback(String str, IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException;

    void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException;

    ActivityInfo resolveActivity(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean shouldHideFromSuggestions(String str, UserHandle userHandle) throws RemoteException;

    void showAppDetailsAsUser(IApplicationThread iApplicationThread, String str, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException;

    void startActivityAsUser(IApplicationThread iApplicationThread, String str, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException;

    void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String str, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException;

    boolean startShortcut(String str, String str2, String str3, Rect rect, Bundle bundle, int i) throws RemoteException;

    public static class Default implements ILauncherApps {
        public void addOnAppsChangedListener(String callingPackage, IOnAppsChangedListener listener) throws RemoteException {
        }

        public void removeOnAppsChangedListener(IOnAppsChangedListener listener) throws RemoteException {
        }

        public ParceledListSlice getLauncherActivities(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        public ActivityInfo resolveActivity(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
            return null;
        }

        public void startSessionDetailsActivityAsUser(IApplicationThread caller, String callingPackage, PackageInstaller.SessionInfo sessionInfo, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
        }

        public void startActivityAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
        }

        public void showAppDetailsAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
        }

        public boolean isPackageEnabled(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return false;
        }

        public Bundle getSuspendedPackageLauncherExtras(String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        public boolean isActivityEnabled(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
            return false;
        }

        public ApplicationInfo getApplicationInfo(String callingPackage, String packageName, int flags, UserHandle user) throws RemoteException {
            return null;
        }

        public LauncherApps.AppUsageLimit getAppUsageLimit(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        public ParceledListSlice getShortcuts(String callingPackage, long changedSince, String packageName, List shortcutIds, ComponentName componentName, int flags, UserHandle user) throws RemoteException {
            return null;
        }

        public void pinShortcuts(String callingPackage, String packageName, List<String> list, UserHandle user) throws RemoteException {
        }

        public boolean startShortcut(String callingPackage, String packageName, String id, Rect sourceBounds, Bundle startActivityOptions, int userId) throws RemoteException {
            return false;
        }

        public int getShortcutIconResId(String callingPackage, String packageName, String id, int userId) throws RemoteException {
            return 0;
        }

        public ParcelFileDescriptor getShortcutIconFd(String callingPackage, String packageName, String id, int userId) throws RemoteException {
            return null;
        }

        public boolean hasShortcutHostPermission(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean shouldHideFromSuggestions(String packageName, UserHandle user) throws RemoteException {
            return false;
        }

        public ParceledListSlice getShortcutConfigActivities(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        public IntentSender getShortcutConfigActivityIntent(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
            return null;
        }

        public void registerPackageInstallerCallback(String callingPackage, IPackageInstallerCallback callback) throws RemoteException {
        }

        public ParceledListSlice getAllSessions(String callingPackage) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILauncherApps {
        private static final String DESCRIPTOR = "android.content.pm.ILauncherApps";
        static final int TRANSACTION_addOnAppsChangedListener = 1;
        static final int TRANSACTION_getAllSessions = 23;
        static final int TRANSACTION_getAppUsageLimit = 12;
        static final int TRANSACTION_getApplicationInfo = 11;
        static final int TRANSACTION_getLauncherActivities = 3;
        static final int TRANSACTION_getShortcutConfigActivities = 20;
        static final int TRANSACTION_getShortcutConfigActivityIntent = 21;
        static final int TRANSACTION_getShortcutIconFd = 17;
        static final int TRANSACTION_getShortcutIconResId = 16;
        static final int TRANSACTION_getShortcuts = 13;
        static final int TRANSACTION_getSuspendedPackageLauncherExtras = 9;
        static final int TRANSACTION_hasShortcutHostPermission = 18;
        static final int TRANSACTION_isActivityEnabled = 10;
        static final int TRANSACTION_isPackageEnabled = 8;
        static final int TRANSACTION_pinShortcuts = 14;
        static final int TRANSACTION_registerPackageInstallerCallback = 22;
        static final int TRANSACTION_removeOnAppsChangedListener = 2;
        static final int TRANSACTION_resolveActivity = 4;
        static final int TRANSACTION_shouldHideFromSuggestions = 19;
        static final int TRANSACTION_showAppDetailsAsUser = 7;
        static final int TRANSACTION_startActivityAsUser = 6;
        static final int TRANSACTION_startSessionDetailsActivityAsUser = 5;
        static final int TRANSACTION_startShortcut = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILauncherApps asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILauncherApps)) {
                return new Proxy(obj);
            }
            return (ILauncherApps) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addOnAppsChangedListener";
                case 2:
                    return "removeOnAppsChangedListener";
                case 3:
                    return "getLauncherActivities";
                case 4:
                    return "resolveActivity";
                case 5:
                    return "startSessionDetailsActivityAsUser";
                case 6:
                    return "startActivityAsUser";
                case 7:
                    return "showAppDetailsAsUser";
                case 8:
                    return "isPackageEnabled";
                case 9:
                    return "getSuspendedPackageLauncherExtras";
                case 10:
                    return "isActivityEnabled";
                case 11:
                    return "getApplicationInfo";
                case 12:
                    return "getAppUsageLimit";
                case 13:
                    return "getShortcuts";
                case 14:
                    return "pinShortcuts";
                case 15:
                    return "startShortcut";
                case 16:
                    return "getShortcutIconResId";
                case 17:
                    return "getShortcutIconFd";
                case 18:
                    return "hasShortcutHostPermission";
                case 19:
                    return "shouldHideFromSuggestions";
                case 20:
                    return "getShortcutConfigActivities";
                case 21:
                    return "getShortcutConfigActivityIntent";
                case 22:
                    return "registerPackageInstallerCallback";
                case 23:
                    return "getAllSessions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v54, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v58, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v67, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v71, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v75, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v83, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v85, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v86, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v87, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v88, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v89, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v90, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v91, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v92, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v93, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v94, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v95, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v96, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v97, resolved type: android.os.UserHandle} */
        /* JADX WARNING: type inference failed for: r0v63, types: [android.os.Bundle] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r24, android.os.Parcel r25, android.os.Parcel r26, int r27) throws android.os.RemoteException {
            /*
                r23 = this;
                r9 = r23
                r10 = r24
                r11 = r25
                r12 = r26
                java.lang.String r13 = "android.content.pm.ILauncherApps"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r14 = 1
                if (r10 == r0) goto L_0x048f
                r15 = 0
                r0 = 0
                switch(r10) {
                    case 1: goto L_0x0479;
                    case 2: goto L_0x0467;
                    case 3: goto L_0x0438;
                    case 4: goto L_0x03fd;
                    case 5: goto L_0x039f;
                    case 6: goto L_0x0341;
                    case 7: goto L_0x02e3;
                    case 8: goto L_0x02bd;
                    case 9: goto L_0x0292;
                    case 10: goto L_0x0260;
                    case 11: goto L_0x022d;
                    case 12: goto L_0x01fe;
                    case 13: goto L_0x019b;
                    case 14: goto L_0x0175;
                    case 15: goto L_0x012e;
                    case 16: goto L_0x0110;
                    case 17: goto L_0x00e9;
                    case 18: goto L_0x00d7;
                    case 19: goto L_0x00b5;
                    case 20: goto L_0x0086;
                    case 21: goto L_0x004b;
                    case 22: goto L_0x0035;
                    case 23: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r24, r25, r26, r27)
                return r0
            L_0x001a:
                r11.enforceInterface(r13)
                java.lang.String r0 = r25.readString()
                android.content.pm.ParceledListSlice r1 = r9.getAllSessions(r0)
                r26.writeNoException()
                if (r1 == 0) goto L_0x0031
                r12.writeInt(r14)
                r1.writeToParcel(r12, r14)
                goto L_0x0034
            L_0x0031:
                r12.writeInt(r15)
            L_0x0034:
                return r14
            L_0x0035:
                r11.enforceInterface(r13)
                java.lang.String r0 = r25.readString()
                android.os.IBinder r1 = r25.readStrongBinder()
                android.content.pm.IPackageInstallerCallback r1 = android.content.pm.IPackageInstallerCallback.Stub.asInterface(r1)
                r9.registerPackageInstallerCallback(r0, r1)
                r26.writeNoException()
                return r14
            L_0x004b:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x0061
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r11)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0062
            L_0x0061:
                r2 = r0
            L_0x0062:
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x0071
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0072
            L_0x0071:
            L_0x0072:
                android.content.IntentSender r3 = r9.getShortcutConfigActivityIntent(r1, r2, r0)
                r26.writeNoException()
                if (r3 == 0) goto L_0x0082
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x0085
            L_0x0082:
                r12.writeInt(r15)
            L_0x0085:
                return r14
            L_0x0086:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x00a0
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x00a1
            L_0x00a0:
            L_0x00a1:
                android.content.pm.ParceledListSlice r3 = r9.getShortcutConfigActivities(r1, r2, r0)
                r26.writeNoException()
                if (r3 == 0) goto L_0x00b1
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x00b4
            L_0x00b1:
                r12.writeInt(r15)
            L_0x00b4:
                return r14
            L_0x00b5:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x00cb
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x00cc
            L_0x00cb:
            L_0x00cc:
                boolean r2 = r9.shouldHideFromSuggestions(r1, r0)
                r26.writeNoException()
                r12.writeInt(r2)
                return r14
            L_0x00d7:
                r11.enforceInterface(r13)
                java.lang.String r0 = r25.readString()
                boolean r1 = r9.hasShortcutHostPermission(r0)
                r26.writeNoException()
                r12.writeInt(r1)
                return r14
            L_0x00e9:
                r11.enforceInterface(r13)
                java.lang.String r0 = r25.readString()
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                android.os.ParcelFileDescriptor r4 = r9.getShortcutIconFd(r0, r1, r2, r3)
                r26.writeNoException()
                if (r4 == 0) goto L_0x010c
                r12.writeInt(r14)
                r4.writeToParcel(r12, r14)
                goto L_0x010f
            L_0x010c:
                r12.writeInt(r15)
            L_0x010f:
                return r14
            L_0x0110:
                r11.enforceInterface(r13)
                java.lang.String r0 = r25.readString()
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                int r4 = r9.getShortcutIconResId(r0, r1, r2, r3)
                r26.writeNoException()
                r12.writeInt(r4)
                return r14
            L_0x012e:
                r11.enforceInterface(r13)
                java.lang.String r7 = r25.readString()
                java.lang.String r8 = r25.readString()
                java.lang.String r15 = r25.readString()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x014d
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r4 = r1
                goto L_0x014e
            L_0x014d:
                r4 = r0
            L_0x014e:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x015e
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x015c:
                r5 = r0
                goto L_0x015f
            L_0x015e:
                goto L_0x015c
            L_0x015f:
                int r16 = r25.readInt()
                r0 = r23
                r1 = r7
                r2 = r8
                r3 = r15
                r6 = r16
                boolean r0 = r0.startShortcut(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                r12.writeInt(r0)
                return r14
            L_0x0175:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                java.util.ArrayList r3 = r25.createStringArrayList()
                int r4 = r25.readInt()
                if (r4 == 0) goto L_0x0193
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0194
            L_0x0193:
            L_0x0194:
                r9.pinShortcuts(r1, r2, r3, r0)
                r26.writeNoException()
                return r14
            L_0x019b:
                r11.enforceInterface(r13)
                java.lang.String r16 = r25.readString()
                long r17 = r25.readLong()
                java.lang.String r19 = r25.readString()
                java.lang.Class r1 = r23.getClass()
                java.lang.ClassLoader r7 = r1.getClassLoader()
                java.util.ArrayList r20 = r11.readArrayList(r7)
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x01c6
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                r6 = r1
                goto L_0x01c7
            L_0x01c6:
                r6 = r0
            L_0x01c7:
                int r21 = r25.readInt()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x01db
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
            L_0x01d9:
                r8 = r0
                goto L_0x01dc
            L_0x01db:
                goto L_0x01d9
            L_0x01dc:
                r0 = r23
                r1 = r16
                r2 = r17
                r4 = r19
                r5 = r20
                r22 = r7
                r7 = r21
                android.content.pm.ParceledListSlice r0 = r0.getShortcuts(r1, r2, r4, r5, r6, r7, r8)
                r26.writeNoException()
                if (r0 == 0) goto L_0x01fa
                r12.writeInt(r14)
                r0.writeToParcel(r12, r14)
                goto L_0x01fd
            L_0x01fa:
                r12.writeInt(r15)
            L_0x01fd:
                return r14
            L_0x01fe:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x0218
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0219
            L_0x0218:
            L_0x0219:
                android.content.pm.LauncherApps$AppUsageLimit r3 = r9.getAppUsageLimit(r1, r2, r0)
                r26.writeNoException()
                if (r3 == 0) goto L_0x0229
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x022c
            L_0x0229:
                r12.writeInt(r15)
            L_0x022c:
                return r14
            L_0x022d:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                int r4 = r25.readInt()
                if (r4 == 0) goto L_0x024b
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x024c
            L_0x024b:
            L_0x024c:
                android.content.pm.ApplicationInfo r4 = r9.getApplicationInfo(r1, r2, r3, r0)
                r26.writeNoException()
                if (r4 == 0) goto L_0x025c
                r12.writeInt(r14)
                r4.writeToParcel(r12, r14)
                goto L_0x025f
            L_0x025c:
                r12.writeInt(r15)
            L_0x025f:
                return r14
            L_0x0260:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x0276
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r11)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0277
            L_0x0276:
                r2 = r0
            L_0x0277:
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x0286
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0287
            L_0x0286:
            L_0x0287:
                boolean r3 = r9.isActivityEnabled(r1, r2, r0)
                r26.writeNoException()
                r12.writeInt(r3)
                return r14
            L_0x0292:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x02a8
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x02a9
            L_0x02a8:
            L_0x02a9:
                android.os.Bundle r2 = r9.getSuspendedPackageLauncherExtras(r1, r0)
                r26.writeNoException()
                if (r2 == 0) goto L_0x02b9
                r12.writeInt(r14)
                r2.writeToParcel(r12, r14)
                goto L_0x02bc
            L_0x02b9:
                r12.writeInt(r15)
            L_0x02bc:
                return r14
            L_0x02bd:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x02d7
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x02d8
            L_0x02d7:
            L_0x02d8:
                boolean r3 = r9.isPackageEnabled(r1, r2, r0)
                r26.writeNoException()
                r12.writeInt(r3)
                return r14
            L_0x02e3:
                r11.enforceInterface(r13)
                android.os.IBinder r1 = r25.readStrongBinder()
                android.app.IApplicationThread r7 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r8 = r25.readString()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0302
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                r3 = r1
                goto L_0x0303
            L_0x0302:
                r3 = r0
            L_0x0303:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0313
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r4 = r1
                goto L_0x0314
            L_0x0313:
                r4 = r0
            L_0x0314:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0324
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x0325
            L_0x0324:
                r5 = r0
            L_0x0325:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0335
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
            L_0x0333:
                r6 = r0
                goto L_0x0336
            L_0x0335:
                goto L_0x0333
            L_0x0336:
                r0 = r23
                r1 = r7
                r2 = r8
                r0.showAppDetailsAsUser(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x0341:
                r11.enforceInterface(r13)
                android.os.IBinder r1 = r25.readStrongBinder()
                android.app.IApplicationThread r7 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r8 = r25.readString()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0360
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                r3 = r1
                goto L_0x0361
            L_0x0360:
                r3 = r0
            L_0x0361:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0371
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r4 = r1
                goto L_0x0372
            L_0x0371:
                r4 = r0
            L_0x0372:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0382
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x0383
            L_0x0382:
                r5 = r0
            L_0x0383:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0393
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
            L_0x0391:
                r6 = r0
                goto L_0x0394
            L_0x0393:
                goto L_0x0391
            L_0x0394:
                r0 = r23
                r1 = r7
                r2 = r8
                r0.startActivityAsUser(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x039f:
                r11.enforceInterface(r13)
                android.os.IBinder r1 = r25.readStrongBinder()
                android.app.IApplicationThread r7 = android.app.IApplicationThread.Stub.asInterface(r1)
                java.lang.String r8 = r25.readString()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x03be
                android.os.Parcelable$Creator<android.content.pm.PackageInstaller$SessionInfo> r1 = android.content.pm.PackageInstaller.SessionInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.content.pm.PackageInstaller$SessionInfo r1 = (android.content.pm.PackageInstaller.SessionInfo) r1
                r3 = r1
                goto L_0x03bf
            L_0x03be:
                r3 = r0
            L_0x03bf:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x03cf
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r4 = r1
                goto L_0x03d0
            L_0x03cf:
                r4 = r0
            L_0x03d0:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x03e0
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x03e1
            L_0x03e0:
                r5 = r0
            L_0x03e1:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x03f1
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
            L_0x03ef:
                r6 = r0
                goto L_0x03f2
            L_0x03f1:
                goto L_0x03ef
            L_0x03f2:
                r0 = r23
                r1 = r7
                r2 = r8
                r0.startSessionDetailsActivityAsUser(r1, r2, r3, r4, r5, r6)
                r26.writeNoException()
                return r14
            L_0x03fd:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                if (r2 == 0) goto L_0x0413
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r11)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0414
            L_0x0413:
                r2 = r0
            L_0x0414:
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x0423
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0424
            L_0x0423:
            L_0x0424:
                android.content.pm.ActivityInfo r3 = r9.resolveActivity(r1, r2, r0)
                r26.writeNoException()
                if (r3 == 0) goto L_0x0434
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x0437
            L_0x0434:
                r12.writeInt(r15)
            L_0x0437:
                return r14
            L_0x0438:
                r11.enforceInterface(r13)
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                int r3 = r25.readInt()
                if (r3 == 0) goto L_0x0452
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0453
            L_0x0452:
            L_0x0453:
                android.content.pm.ParceledListSlice r3 = r9.getLauncherActivities(r1, r2, r0)
                r26.writeNoException()
                if (r3 == 0) goto L_0x0463
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x0466
            L_0x0463:
                r12.writeInt(r15)
            L_0x0466:
                return r14
            L_0x0467:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r25.readStrongBinder()
                android.content.pm.IOnAppsChangedListener r0 = android.content.pm.IOnAppsChangedListener.Stub.asInterface(r0)
                r9.removeOnAppsChangedListener(r0)
                r26.writeNoException()
                return r14
            L_0x0479:
                r11.enforceInterface(r13)
                java.lang.String r0 = r25.readString()
                android.os.IBinder r1 = r25.readStrongBinder()
                android.content.pm.IOnAppsChangedListener r1 = android.content.pm.IOnAppsChangedListener.Stub.asInterface(r1)
                r9.addOnAppsChangedListener(r0, r1)
                r26.writeNoException()
                return r14
            L_0x048f:
                r12.writeString(r13)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.ILauncherApps.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILauncherApps {
            public static ILauncherApps sDefaultImpl;
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

            public void addOnAppsChangedListener(String callingPackage, IOnAppsChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnAppsChangedListener(callingPackage, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnAppsChangedListener(IOnAppsChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeOnAppsChangedListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getLauncherActivities(String callingPackage, String packageName, UserHandle user) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLauncherActivities(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ActivityInfo resolveActivity(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
                ActivityInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resolveActivity(callingPackage, component, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ActivityInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startSessionDetailsActivityAsUser(IApplicationThread caller, String callingPackage, PackageInstaller.SessionInfo sessionInfo, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
                PackageInstaller.SessionInfo sessionInfo2 = sessionInfo;
                Rect rect = sourceBounds;
                Bundle bundle = opts;
                UserHandle userHandle = user;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeString(callingPackage);
                        if (sessionInfo2 != null) {
                            _data.writeInt(1);
                            sessionInfo2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (userHandle != null) {
                            _data.writeInt(1);
                            userHandle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().startSessionDetailsActivityAsUser(caller, callingPackage, sessionInfo, sourceBounds, opts, user);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    String str = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void startActivityAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
                ComponentName componentName = component;
                Rect rect = sourceBounds;
                Bundle bundle = opts;
                UserHandle userHandle = user;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeString(callingPackage);
                        if (componentName != null) {
                            _data.writeInt(1);
                            componentName.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (userHandle != null) {
                            _data.writeInt(1);
                            userHandle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().startActivityAsUser(caller, callingPackage, component, sourceBounds, opts, user);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    String str = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void showAppDetailsAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
                ComponentName componentName = component;
                Rect rect = sourceBounds;
                Bundle bundle = opts;
                UserHandle userHandle = user;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    try {
                        _data.writeString(callingPackage);
                        if (componentName != null) {
                            _data.writeInt(1);
                            componentName.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (userHandle != null) {
                            _data.writeInt(1);
                            userHandle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().showAppDetailsAsUser(caller, callingPackage, component, sourceBounds, opts, user);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    String str = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean isPackageEnabled(String callingPackage, String packageName, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageEnabled(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getSuspendedPackageLauncherExtras(String packageName, UserHandle user) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSuspendedPackageLauncherExtras(packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isActivityEnabled(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _result = true;
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActivityEnabled(callingPackage, component, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ApplicationInfo getApplicationInfo(String callingPackage, String packageName, int flags, UserHandle user) throws RemoteException {
                ApplicationInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationInfo(callingPackage, packageName, flags, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ApplicationInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ApplicationInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LauncherApps.AppUsageLimit getAppUsageLimit(String callingPackage, String packageName, UserHandle user) throws RemoteException {
                LauncherApps.AppUsageLimit _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppUsageLimit(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LauncherApps.AppUsageLimit.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LauncherApps.AppUsageLimit _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getShortcuts(String callingPackage, long changedSince, String packageName, List shortcutIds, ComponentName componentName, int flags, UserHandle user) throws RemoteException {
                ParceledListSlice _result;
                ComponentName componentName2 = componentName;
                UserHandle userHandle = user;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPackage);
                        _data.writeLong(changedSince);
                        try {
                            _data.writeString(packageName);
                            _data.writeList(shortcutIds);
                            if (componentName2 != null) {
                                _data.writeInt(1);
                                componentName2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(flags);
                            if (userHandle != null) {
                                _data.writeInt(1);
                                userHandle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                                } else {
                                    _result = null;
                                }
                                ParceledListSlice _result2 = _result;
                                _reply.recycle();
                                _data.recycle();
                                return _result2;
                            }
                            ParceledListSlice shortcuts = Stub.getDefaultImpl().getShortcuts(callingPackage, changedSince, packageName, shortcutIds, componentName, flags, user);
                            _reply.recycle();
                            _data.recycle();
                            return shortcuts;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = packageName;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    String str2 = callingPackage;
                    String str3 = packageName;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void pinShortcuts(String callingPackage, String packageName, List<String> shortcutIds, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    _data.writeStringList(shortcutIds);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pinShortcuts(callingPackage, packageName, shortcutIds, user);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startShortcut(String callingPackage, String packageName, String id, Rect sourceBounds, Bundle startActivityOptions, int userId) throws RemoteException {
                boolean _result;
                Rect rect = sourceBounds;
                Bundle bundle = startActivityOptions;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th) {
                        th = th;
                        String str = packageName;
                        String str2 = id;
                        int i = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeString(id);
                            _result = true;
                            if (rect != null) {
                                _data.writeInt(1);
                                rect.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            int i2 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean startShortcut = Stub.getDefaultImpl().startShortcut(callingPackage, packageName, id, sourceBounds, startActivityOptions, userId);
                            _reply.recycle();
                            _data.recycle();
                            return startShortcut;
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str22 = id;
                        int i22 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str3 = callingPackage;
                    String str4 = packageName;
                    String str222 = id;
                    int i222 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int getShortcutIconResId(String callingPackage, String packageName, String id, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    _data.writeString(id);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutIconResId(callingPackage, packageName, id, userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getShortcutIconFd(String callingPackage, String packageName, String id, int userId) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    _data.writeString(id);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutIconFd(callingPackage, packageName, id, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasShortcutHostPermission(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasShortcutHostPermission(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldHideFromSuggestions(String packageName, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldHideFromSuggestions(packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getShortcutConfigActivities(String callingPackage, String packageName, UserHandle user) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutConfigActivities(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IntentSender getShortcutConfigActivityIntent(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
                IntentSender _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutConfigActivityIntent(callingPackage, component, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IntentSender.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IntentSender _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerPackageInstallerCallback(String callingPackage, IPackageInstallerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerPackageInstallerCallback(callingPackage, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAllSessions(String callingPackage) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllSessions(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILauncherApps impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILauncherApps getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
