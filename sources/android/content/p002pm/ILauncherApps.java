package android.content.p002pm;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.IntentSender;
import android.content.p002pm.IOnAppsChangedListener;
import android.content.p002pm.IPackageInstallerCallback;
import android.content.p002pm.LauncherApps;
import android.content.p002pm.PackageInstaller;
import android.graphics.Rect;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import java.util.List;

/* renamed from: android.content.pm.ILauncherApps */
/* loaded from: classes.dex */
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

    /* renamed from: android.content.pm.ILauncherApps$Default */
    /* loaded from: classes.dex */
    public static class Default implements ILauncherApps {
        @Override // android.content.p002pm.ILauncherApps
        public void addOnAppsChangedListener(String callingPackage, IOnAppsChangedListener listener) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public void removeOnAppsChangedListener(IOnAppsChangedListener listener) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public ParceledListSlice getLauncherActivities(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public ActivityInfo resolveActivity(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public void startSessionDetailsActivityAsUser(IApplicationThread caller, String callingPackage, PackageInstaller.SessionInfo sessionInfo, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public void startActivityAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public void showAppDetailsAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public boolean isPackageEnabled(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.ILauncherApps
        public Bundle getSuspendedPackageLauncherExtras(String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public boolean isActivityEnabled(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.ILauncherApps
        public ApplicationInfo getApplicationInfo(String callingPackage, String packageName, int flags, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public LauncherApps.AppUsageLimit getAppUsageLimit(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public ParceledListSlice getShortcuts(String callingPackage, long changedSince, String packageName, List shortcutIds, ComponentName componentName, int flags, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public void pinShortcuts(String callingPackage, String packageName, List<String> shortcutIds, UserHandle user) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public boolean startShortcut(String callingPackage, String packageName, String id, Rect sourceBounds, Bundle startActivityOptions, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.ILauncherApps
        public int getShortcutIconResId(String callingPackage, String packageName, String id, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.p002pm.ILauncherApps
        public ParcelFileDescriptor getShortcutIconFd(String callingPackage, String packageName, String id, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public boolean hasShortcutHostPermission(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.ILauncherApps
        public boolean shouldHideFromSuggestions(String packageName, UserHandle user) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.ILauncherApps
        public ParceledListSlice getShortcutConfigActivities(String callingPackage, String packageName, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public IntentSender getShortcutConfigActivityIntent(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.ILauncherApps
        public void registerPackageInstallerCallback(String callingPackage, IPackageInstallerCallback callback) throws RemoteException {
        }

        @Override // android.content.p002pm.ILauncherApps
        public ParceledListSlice getAllSessions(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.ILauncherApps$Stub */
    /* loaded from: classes.dex */
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
            if (iin != null && (iin instanceof ILauncherApps)) {
                return (ILauncherApps) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ComponentName _arg1;
            ComponentName _arg12;
            ComponentName _arg13;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    IOnAppsChangedListener _arg14 = IOnAppsChangedListener.Stub.asInterface(data.readStrongBinder());
                    addOnAppsChangedListener(_arg0, _arg14);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IOnAppsChangedListener _arg02 = IOnAppsChangedListener.Stub.asInterface(data.readStrongBinder());
                    removeOnAppsChangedListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    String _arg15 = data.readString();
                    UserHandle _arg2 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    ParceledListSlice _result = getLauncherActivities(_arg03, _arg15, _arg2);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    UserHandle _arg22 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    ActivityInfo _result2 = resolveActivity(_arg04, _arg1, _arg22);
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg05 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg16 = data.readString();
                    PackageInstaller.SessionInfo _arg23 = data.readInt() != 0 ? PackageInstaller.SessionInfo.CREATOR.createFromParcel(data) : null;
                    Rect _arg3 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Bundle _arg4 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg5 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    startSessionDetailsActivityAsUser(_arg05, _arg16, _arg23, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg06 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg17 = data.readString();
                    ComponentName _arg24 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    Rect _arg32 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Bundle _arg42 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg52 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    startActivityAsUser(_arg06, _arg17, _arg24, _arg32, _arg42, _arg52);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    IApplicationThread _arg07 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    String _arg18 = data.readString();
                    ComponentName _arg25 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    Rect _arg33 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Bundle _arg43 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg53 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    showAppDetailsAsUser(_arg07, _arg18, _arg25, _arg33, _arg43, _arg53);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    String _arg19 = data.readString();
                    UserHandle _arg26 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    boolean isPackageEnabled = isPackageEnabled(_arg08, _arg19, _arg26);
                    reply.writeNoException();
                    reply.writeInt(isPackageEnabled ? 1 : 0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    UserHandle _arg110 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    Bundle _result3 = getSuspendedPackageLauncherExtras(_arg09, _arg110);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    if (data.readInt() != 0) {
                        _arg12 = ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    UserHandle _arg27 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    boolean isActivityEnabled = isActivityEnabled(_arg010, _arg12, _arg27);
                    reply.writeNoException();
                    reply.writeInt(isActivityEnabled ? 1 : 0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg011 = data.readString();
                    String _arg111 = data.readString();
                    int _arg28 = data.readInt();
                    UserHandle _arg34 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    ApplicationInfo _result4 = getApplicationInfo(_arg011, _arg111, _arg28, _arg34);
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg012 = data.readString();
                    String _arg112 = data.readString();
                    UserHandle _arg29 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    LauncherApps.AppUsageLimit _result5 = getAppUsageLimit(_arg012, _arg112, _arg29);
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg013 = data.readString();
                    long _arg113 = data.readLong();
                    String _arg210 = data.readString();
                    ClassLoader cl = getClass().getClassLoader();
                    List _arg35 = data.readArrayList(cl);
                    ComponentName _arg44 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    int _arg54 = data.readInt();
                    UserHandle _arg6 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    ParceledListSlice _result6 = getShortcuts(_arg013, _arg113, _arg210, _arg35, _arg44, _arg54, _arg6);
                    reply.writeNoException();
                    if (_result6 != null) {
                        reply.writeInt(1);
                        _result6.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg014 = data.readString();
                    String _arg114 = data.readString();
                    List<String> _arg211 = data.createStringArrayList();
                    UserHandle _arg36 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    pinShortcuts(_arg014, _arg114, _arg211, _arg36);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg015 = data.readString();
                    String _arg115 = data.readString();
                    String _arg212 = data.readString();
                    Rect _arg37 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    Bundle _arg45 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _arg55 = data.readInt();
                    boolean startShortcut = startShortcut(_arg015, _arg115, _arg212, _arg37, _arg45, _arg55);
                    reply.writeNoException();
                    reply.writeInt(startShortcut ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg016 = data.readString();
                    String _arg116 = data.readString();
                    String _arg213 = data.readString();
                    int _arg38 = data.readInt();
                    int _result7 = getShortcutIconResId(_arg016, _arg116, _arg213, _arg38);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg017 = data.readString();
                    String _arg117 = data.readString();
                    String _arg214 = data.readString();
                    int _arg39 = data.readInt();
                    ParcelFileDescriptor _result8 = getShortcutIconFd(_arg017, _arg117, _arg214, _arg39);
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg018 = data.readString();
                    boolean hasShortcutHostPermission = hasShortcutHostPermission(_arg018);
                    reply.writeNoException();
                    reply.writeInt(hasShortcutHostPermission ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg019 = data.readString();
                    UserHandle _arg118 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    boolean shouldHideFromSuggestions = shouldHideFromSuggestions(_arg019, _arg118);
                    reply.writeNoException();
                    reply.writeInt(shouldHideFromSuggestions ? 1 : 0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    String _arg119 = data.readString();
                    UserHandle _arg215 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    ParceledListSlice _result9 = getShortcutConfigActivities(_arg020, _arg119, _arg215);
                    reply.writeNoException();
                    if (_result9 != null) {
                        reply.writeInt(1);
                        _result9.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg021 = data.readString();
                    if (data.readInt() != 0) {
                        _arg13 = ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    UserHandle _arg216 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    IntentSender _result10 = getShortcutConfigActivityIntent(_arg021, _arg13, _arg216);
                    reply.writeNoException();
                    if (_result10 != null) {
                        reply.writeInt(1);
                        _result10.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg022 = data.readString();
                    IPackageInstallerCallback _arg120 = IPackageInstallerCallback.Stub.asInterface(data.readStrongBinder());
                    registerPackageInstallerCallback(_arg022, _arg120);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg023 = data.readString();
                    ParceledListSlice _result11 = getAllSessions(_arg023);
                    reply.writeNoException();
                    if (_result11 != null) {
                        reply.writeInt(1);
                        _result11.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.content.pm.ILauncherApps$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements ILauncherApps {
            public static ILauncherApps sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.p002pm.ILauncherApps
            public void addOnAppsChangedListener(String callingPackage, IOnAppsChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnAppsChangedListener(callingPackage, listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public void removeOnAppsChangedListener(IOnAppsChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnAppsChangedListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLauncherActivities(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resolveActivity(callingPackage, component, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ActivityInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public void startSessionDetailsActivityAsUser(IApplicationThread caller, String callingPackage, PackageInstaller.SessionInfo sessionInfo, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sourceBounds != null) {
                        _data.writeInt(1);
                        sourceBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (opts != null) {
                        _data.writeInt(1);
                        opts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startSessionDetailsActivityAsUser(caller, callingPackage, sessionInfo, sourceBounds, opts, user);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public void startActivityAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sourceBounds != null) {
                        _data.writeInt(1);
                        sourceBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (opts != null) {
                        _data.writeInt(1);
                        opts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startActivityAsUser(caller, callingPackage, component, sourceBounds, opts, user);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public void showAppDetailsAsUser(IApplicationThread caller, String callingPackage, ComponentName component, Rect sourceBounds, Bundle opts, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sourceBounds != null) {
                        _data.writeInt(1);
                        sourceBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (opts != null) {
                        _data.writeInt(1);
                        opts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showAppDetailsAsUser(caller, callingPackage, component, sourceBounds, opts, user);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public boolean isPackageEnabled(String callingPackage, String packageName, UserHandle user) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPackageEnabled(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSuspendedPackageLauncherExtras(packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public boolean isActivityEnabled(String callingPackage, ComponentName component, UserHandle user) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActivityEnabled(callingPackage, component, user);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationInfo(callingPackage, packageName, flags, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ApplicationInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppUsageLimit(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LauncherApps.AppUsageLimit.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public ParceledListSlice getShortcuts(String callingPackage, long changedSince, String packageName, List shortcutIds, ComponentName componentName, int flags, UserHandle user) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                    _data.writeLong(changedSince);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(packageName);
                    _data.writeList(shortcutIds);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        ParceledListSlice shortcuts = Stub.getDefaultImpl().getShortcuts(callingPackage, changedSince, packageName, shortcutIds, componentName, flags, user);
                        _reply.recycle();
                        _data.recycle();
                        return shortcuts;
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
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pinShortcuts(callingPackage, packageName, shortcutIds, user);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public boolean startShortcut(String callingPackage, String packageName, String id, Rect sourceBounds, Bundle startActivityOptions, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(packageName);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(id);
                    if (sourceBounds != null) {
                        _data.writeInt(1);
                        sourceBounds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (startActivityOptions != null) {
                        _data.writeInt(1);
                        startActivityOptions.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        boolean startShortcut = Stub.getDefaultImpl().startShortcut(callingPackage, packageName, id, sourceBounds, startActivityOptions, userId);
                        _reply.recycle();
                        _data.recycle();
                        return startShortcut;
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public int getShortcutIconResId(String callingPackage, String packageName, String id, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    _data.writeString(id);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutIconResId(callingPackage, packageName, id, userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutIconFd(callingPackage, packageName, id, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public boolean hasShortcutHostPermission(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasShortcutHostPermission(callingPackage);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public boolean shouldHideFromSuggestions(String packageName, UserHandle user) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldHideFromSuggestions(packageName, user);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutConfigActivities(callingPackage, packageName, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
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
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutConfigActivityIntent(callingPackage, component, user);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IntentSender.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public void registerPackageInstallerCallback(String callingPackage, IPackageInstallerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerPackageInstallerCallback(callingPackage, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.ILauncherApps
            public ParceledListSlice getAllSessions(String callingPackage) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllSessions(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILauncherApps impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILauncherApps getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
