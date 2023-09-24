package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.app.servertransaction.ClientTransaction;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.ParceledListSlice;
import android.content.p002pm.ProviderInfo;
import android.content.p002pm.ServiceInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Debug;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteCallback;
import android.p007os.RemoteException;
import com.android.internal.app.IVoiceInteractor;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface IApplicationThread extends IInterface {
    void attachAgent(String str) throws RemoteException;

    void bindApplication(String str, ApplicationInfo applicationInfo, List<ProviderInfo> list, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z, boolean z2, boolean z3, boolean z4, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str2, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) throws RemoteException;

    void clearDnsCache() throws RemoteException;

    void dispatchPackageBroadcast(int i, String[] strArr) throws RemoteException;

    void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) throws RemoteException;

    void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpHeap(boolean z, boolean z2, boolean z3, String str, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String[] strArr) throws RemoteException;

    void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) throws RemoteException;

    void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException;

    void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException;

    void handleTrustStorageUpdate() throws RemoteException;

    void notifyCleartextNetwork(byte[] bArr) throws RemoteException;

    void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void processInBackground() throws RemoteException;

    void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) throws RemoteException;

    void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) throws RemoteException;

    void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void runIsolatedEntryPoint(String str, String[] strArr) throws RemoteException;

    void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException;

    @UnsupportedAppUsage
    void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i) throws RemoteException;

    void scheduleCrash(String str) throws RemoteException;

    void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException;

    void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException;

    void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException;

    void scheduleExit() throws RemoteException;

    void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException;

    void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException;

    void scheduleLowMemory() throws RemoteException;

    void scheduleOnNewActivityOptions(IBinder iBinder, Bundle bundle) throws RemoteException;

    void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, int i2, int i3) throws RemoteException;

    void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3) throws RemoteException;

    void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException;

    void scheduleSleeping(IBinder iBinder, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void scheduleStopService(IBinder iBinder) throws RemoteException;

    void scheduleSuicide() throws RemoteException;

    void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException;

    void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void scheduleTrimMemory(int i) throws RemoteException;

    @UnsupportedAppUsage
    void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException;

    void setCoreSettings(Bundle bundle) throws RemoteException;

    void setNetworkBlockSeq(long j) throws RemoteException;

    void setProcessState(int i) throws RemoteException;

    void setSchedulingGroup(int i) throws RemoteException;

    void startBinderTracking() throws RemoteException;

    void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void unstableProviderDied(IBinder iBinder) throws RemoteException;

    void updateHttpProxy() throws RemoteException;

    void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) throws RemoteException;

    void updateTimePrefs(int i) throws RemoteException;

    void updateTimeZone() throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IApplicationThread {
        @Override // android.app.IApplicationThread
        public void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean sync, int sendingUser, int processState) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleStopService(IBinder token) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void bindApplication(String packageName, ApplicationInfo info, List<ProviderInfo> providers, ComponentName testName, ProfilerInfo profilerInfo, Bundle testArguments, IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean restrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleExit() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleServiceArgs(IBinder token, ParceledListSlice args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleUnbindService(IBinder token, Intent intent) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpService(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser, int processState) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleSleeping(IBinder token, boolean sleeping) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int group) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int backupMode, int userId) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleDestroyBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int userId) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewActivityOptions(IBinder token, Bundle options) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleSuicide() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int cmd, String[] packages) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(String msg) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(ParcelFileDescriptor fd, IBinder servicetoken, String prefix, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(Bundle coreSettings) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int level) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfo(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean checkin, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfoProto(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(IBinder provider) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(IBinder token, boolean timeout) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int state) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(ProviderInfo provider) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimePrefs(int timeFormatPreference) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(IBinder token) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] firstPacket) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(String path) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(ApplicationInfo ai) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long procStateSeq) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(IBinder activityToken, IVoiceInteractor intractor, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IApplicationThread {
        private static final String DESCRIPTOR = "android.app.IApplicationThread";
        static final int TRANSACTION_attachAgent = 48;
        static final int TRANSACTION_bindApplication = 4;
        static final int TRANSACTION_clearDnsCache = 26;
        static final int TRANSACTION_dispatchPackageBroadcast = 22;
        static final int TRANSACTION_dumpActivity = 25;
        static final int TRANSACTION_dumpDbInfo = 35;
        static final int TRANSACTION_dumpGfxInfo = 33;
        static final int TRANSACTION_dumpHeap = 24;
        static final int TRANSACTION_dumpMemInfo = 31;
        static final int TRANSACTION_dumpMemInfoProto = 32;
        static final int TRANSACTION_dumpProvider = 34;
        static final int TRANSACTION_dumpService = 12;
        static final int TRANSACTION_handleTrustStorageUpdate = 47;
        static final int TRANSACTION_notifyCleartextNetwork = 43;
        static final int TRANSACTION_performDirectAction = 53;
        static final int TRANSACTION_processInBackground = 9;
        static final int TRANSACTION_profilerControl = 16;
        static final int TRANSACTION_requestAssistContextExtras = 37;
        static final int TRANSACTION_requestDirectActions = 52;
        static final int TRANSACTION_runIsolatedEntryPoint = 5;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 49;
        static final int TRANSACTION_scheduleBindService = 10;
        static final int TRANSACTION_scheduleCrash = 23;
        static final int TRANSACTION_scheduleCreateBackupAgent = 18;
        static final int TRANSACTION_scheduleCreateService = 2;
        static final int TRANSACTION_scheduleDestroyBackupAgent = 19;
        static final int TRANSACTION_scheduleEnterAnimationComplete = 42;
        static final int TRANSACTION_scheduleExit = 6;
        static final int TRANSACTION_scheduleInstallProvider = 40;
        static final int TRANSACTION_scheduleLocalVoiceInteractionStarted = 46;
        static final int TRANSACTION_scheduleLowMemory = 14;
        static final int TRANSACTION_scheduleOnNewActivityOptions = 20;
        static final int TRANSACTION_scheduleReceiver = 1;
        static final int TRANSACTION_scheduleRegisteredReceiver = 13;
        static final int TRANSACTION_scheduleServiceArgs = 7;
        static final int TRANSACTION_scheduleSleeping = 15;
        static final int TRANSACTION_scheduleStopService = 3;
        static final int TRANSACTION_scheduleSuicide = 21;
        static final int TRANSACTION_scheduleTransaction = 51;
        static final int TRANSACTION_scheduleTranslucentConversionComplete = 38;
        static final int TRANSACTION_scheduleTrimMemory = 30;
        static final int TRANSACTION_scheduleUnbindService = 11;
        static final int TRANSACTION_setCoreSettings = 28;
        static final int TRANSACTION_setNetworkBlockSeq = 50;
        static final int TRANSACTION_setProcessState = 39;
        static final int TRANSACTION_setSchedulingGroup = 17;
        static final int TRANSACTION_startBinderTracking = 44;
        static final int TRANSACTION_stopBinderTrackingAndDump = 45;
        static final int TRANSACTION_unstableProviderDied = 36;
        static final int TRANSACTION_updateHttpProxy = 27;
        static final int TRANSACTION_updatePackageCompatibilityInfo = 29;
        static final int TRANSACTION_updateTimePrefs = 41;
        static final int TRANSACTION_updateTimeZone = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IApplicationThread asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IApplicationThread)) {
                return (IApplicationThread) iin;
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
                    return "scheduleReceiver";
                case 2:
                    return "scheduleCreateService";
                case 3:
                    return "scheduleStopService";
                case 4:
                    return "bindApplication";
                case 5:
                    return "runIsolatedEntryPoint";
                case 6:
                    return "scheduleExit";
                case 7:
                    return "scheduleServiceArgs";
                case 8:
                    return "updateTimeZone";
                case 9:
                    return "processInBackground";
                case 10:
                    return "scheduleBindService";
                case 11:
                    return "scheduleUnbindService";
                case 12:
                    return "dumpService";
                case 13:
                    return "scheduleRegisteredReceiver";
                case 14:
                    return "scheduleLowMemory";
                case 15:
                    return "scheduleSleeping";
                case 16:
                    return "profilerControl";
                case 17:
                    return "setSchedulingGroup";
                case 18:
                    return "scheduleCreateBackupAgent";
                case 19:
                    return "scheduleDestroyBackupAgent";
                case 20:
                    return "scheduleOnNewActivityOptions";
                case 21:
                    return "scheduleSuicide";
                case 22:
                    return "dispatchPackageBroadcast";
                case 23:
                    return "scheduleCrash";
                case 24:
                    return "dumpHeap";
                case 25:
                    return "dumpActivity";
                case 26:
                    return "clearDnsCache";
                case 27:
                    return "updateHttpProxy";
                case 28:
                    return "setCoreSettings";
                case 29:
                    return "updatePackageCompatibilityInfo";
                case 30:
                    return "scheduleTrimMemory";
                case 31:
                    return "dumpMemInfo";
                case 32:
                    return "dumpMemInfoProto";
                case 33:
                    return "dumpGfxInfo";
                case 34:
                    return "dumpProvider";
                case 35:
                    return "dumpDbInfo";
                case 36:
                    return "unstableProviderDied";
                case 37:
                    return "requestAssistContextExtras";
                case 38:
                    return "scheduleTranslucentConversionComplete";
                case 39:
                    return "setProcessState";
                case 40:
                    return "scheduleInstallProvider";
                case 41:
                    return "updateTimePrefs";
                case 42:
                    return "scheduleEnterAnimationComplete";
                case 43:
                    return "notifyCleartextNetwork";
                case 44:
                    return "startBinderTracking";
                case 45:
                    return "stopBinderTrackingAndDump";
                case 46:
                    return "scheduleLocalVoiceInteractionStarted";
                case 47:
                    return "handleTrustStorageUpdate";
                case 48:
                    return "attachAgent";
                case 49:
                    return "scheduleApplicationInfoChanged";
                case 50:
                    return "setNetworkBlockSeq";
                case 51:
                    return "scheduleTransaction";
                case 52:
                    return "requestDirectActions";
                case 53:
                    return "performDirectAction";
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
            Intent _arg0;
            ActivityInfo _arg1;
            CompatibilityInfo _arg2;
            ServiceInfo _arg12;
            boolean _arg13;
            Configuration _arg132;
            CompatibilityInfo _arg14;
            ApplicationInfo _arg02;
            ApplicationInfo _arg03;
            ParcelFileDescriptor _arg04;
            Debug.MemoryInfo _arg15;
            ParcelFileDescriptor _arg05;
            Debug.MemoryInfo _arg16;
            RemoteCallback _arg22;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg2 = CompatibilityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    int _arg3 = data.readInt();
                    String _arg4 = data.readString();
                    Bundle _arg5 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    boolean _arg6 = data.readInt() != 0;
                    int _arg7 = data.readInt();
                    int _arg8 = data.readInt();
                    scheduleReceiver(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg06 = data.readStrongBinder();
                    if (data.readInt() != 0) {
                        _arg12 = ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    CompatibilityInfo _arg23 = data.readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel(data) : null;
                    int _arg32 = data.readInt();
                    scheduleCreateService(_arg06, _arg12, _arg23, _arg32);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg07 = data.readStrongBinder();
                    scheduleStopService(_arg07);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    ApplicationInfo _arg17 = data.readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel(data) : null;
                    List<ProviderInfo> _arg24 = data.createTypedArrayList(ProviderInfo.CREATOR);
                    ComponentName _arg33 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    ProfilerInfo _arg42 = data.readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel(data) : null;
                    Bundle _arg52 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    IInstrumentationWatcher _arg62 = IInstrumentationWatcher.Stub.asInterface(data.readStrongBinder());
                    IUiAutomationConnection _arg72 = IUiAutomationConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg82 = data.readInt();
                    boolean _arg9 = data.readInt() != 0;
                    boolean _arg10 = data.readInt() != 0;
                    boolean _arg11 = data.readInt() != 0;
                    _arg13 = data.readInt() != 0;
                    boolean _arg122 = _arg13;
                    if (data.readInt() != 0) {
                        _arg132 = Configuration.CREATOR.createFromParcel(data);
                    } else {
                        _arg132 = null;
                    }
                    Configuration _arg133 = _arg132;
                    if (data.readInt() != 0) {
                        _arg14 = CompatibilityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg14 = null;
                    }
                    CompatibilityInfo _arg142 = _arg14;
                    ClassLoader cl = getClass().getClassLoader();
                    Map _arg152 = data.readHashMap(cl);
                    Bundle _arg162 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    String _arg172 = data.readString();
                    AutofillOptions _arg18 = data.readInt() != 0 ? AutofillOptions.CREATOR.createFromParcel(data) : null;
                    ContentCaptureOptions _arg19 = data.readInt() != 0 ? ContentCaptureOptions.CREATOR.createFromParcel(data) : null;
                    bindApplication(_arg08, _arg17, _arg24, _arg33, _arg42, _arg52, _arg62, _arg72, _arg82, _arg9, _arg10, _arg11, _arg122, _arg133, _arg142, _arg152, _arg162, _arg172, _arg18, _arg19);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    runIsolatedEntryPoint(_arg09, data.createStringArray());
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    scheduleExit();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg010 = data.readStrongBinder();
                    scheduleServiceArgs(_arg010, data.readInt() != 0 ? ParceledListSlice.CREATOR.createFromParcel(data) : null);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    updateTimeZone();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    processInBackground();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg011 = data.readStrongBinder();
                    Intent _arg110 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    _arg13 = data.readInt() != 0;
                    int _arg34 = data.readInt();
                    scheduleBindService(_arg011, _arg110, _arg13, _arg34);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg012 = data.readStrongBinder();
                    scheduleUnbindService(_arg012, data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg013 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    IBinder _arg111 = data.readStrongBinder();
                    String[] _arg25 = data.createStringArray();
                    dumpService(_arg013, _arg111, _arg25);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    IIntentReceiver _arg014 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    Intent _arg112 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    int _arg26 = data.readInt();
                    String _arg35 = data.readString();
                    Bundle _arg43 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    boolean _arg53 = data.readInt() != 0;
                    boolean _arg63 = data.readInt() != 0;
                    int _arg73 = data.readInt();
                    int _arg83 = data.readInt();
                    scheduleRegisteredReceiver(_arg014, _arg112, _arg26, _arg35, _arg43, _arg53, _arg63, _arg73, _arg83);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    scheduleLowMemory();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg015 = data.readStrongBinder();
                    _arg13 = data.readInt() != 0;
                    scheduleSleeping(_arg015, _arg13);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    _arg13 = data.readInt() != 0;
                    ProfilerInfo _arg113 = data.readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel(data) : null;
                    int _arg27 = data.readInt();
                    profilerControl(_arg13, _arg113, _arg27);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg016 = data.readInt();
                    setSchedulingGroup(_arg016);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = ApplicationInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    CompatibilityInfo _arg114 = data.readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel(data) : null;
                    int _arg28 = data.readInt();
                    int _arg36 = data.readInt();
                    scheduleCreateBackupAgent(_arg02, _arg114, _arg28, _arg36);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = ApplicationInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    CompatibilityInfo _arg115 = data.readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel(data) : null;
                    int _arg29 = data.readInt();
                    scheduleDestroyBackupAgent(_arg03, _arg115, _arg29);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg017 = data.readStrongBinder();
                    Bundle _arg116 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    scheduleOnNewActivityOptions(_arg017, _arg116);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    scheduleSuicide();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg018 = data.readInt();
                    dispatchPackageBroadcast(_arg018, data.createStringArray());
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg019 = data.readString();
                    scheduleCrash(_arg019);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg020 = data.readInt() != 0;
                    boolean _arg117 = data.readInt() != 0;
                    boolean _arg210 = data.readInt() != 0;
                    String _arg37 = data.readString();
                    ParcelFileDescriptor _arg44 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    RemoteCallback _arg54 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    dumpHeap(_arg020, _arg117, _arg210, _arg37, _arg44, _arg54);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg021 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    IBinder _arg118 = data.readStrongBinder();
                    String _arg211 = data.readString();
                    String[] _arg38 = data.createStringArray();
                    dumpActivity(_arg021, _arg118, _arg211, _arg38);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    clearDnsCache();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    updateHttpProxy();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _arg022 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    setCoreSettings(_arg022);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg023 = data.readString();
                    CompatibilityInfo _arg119 = data.readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel(data) : null;
                    updatePackageCompatibilityInfo(_arg023, _arg119);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg024 = data.readInt();
                    scheduleTrimMemory(_arg024);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg15 = Debug.MemoryInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg15 = null;
                    }
                    boolean _arg212 = data.readInt() != 0;
                    boolean _arg39 = data.readInt() != 0;
                    boolean _arg45 = data.readInt() != 0;
                    boolean _arg55 = data.readInt() != 0;
                    boolean _arg64 = data.readInt() != 0;
                    String[] _arg74 = data.createStringArray();
                    dumpMemInfo(_arg04, _arg15, _arg212, _arg39, _arg45, _arg55, _arg64, _arg74);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg05 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg05 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg16 = Debug.MemoryInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg16 = null;
                    }
                    boolean _arg213 = data.readInt() != 0;
                    boolean _arg310 = data.readInt() != 0;
                    boolean _arg46 = data.readInt() != 0;
                    boolean _arg56 = data.readInt() != 0;
                    String[] _arg65 = data.createStringArray();
                    dumpMemInfoProto(_arg05, _arg16, _arg213, _arg310, _arg46, _arg56, _arg65);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg025 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    dumpGfxInfo(_arg025, data.createStringArray());
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg026 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    IBinder _arg120 = data.readStrongBinder();
                    String[] _arg214 = data.createStringArray();
                    dumpProvider(_arg026, _arg120, _arg214);
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg027 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    dumpDbInfo(_arg027, data.createStringArray());
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg028 = data.readStrongBinder();
                    unstableProviderDied(_arg028);
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg029 = data.readStrongBinder();
                    IBinder _arg121 = data.readStrongBinder();
                    int _arg215 = data.readInt();
                    int _arg311 = data.readInt();
                    int _arg47 = data.readInt();
                    requestAssistContextExtras(_arg029, _arg121, _arg215, _arg311, _arg47);
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg030 = data.readStrongBinder();
                    _arg13 = data.readInt() != 0;
                    scheduleTranslucentConversionComplete(_arg030, _arg13);
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg031 = data.readInt();
                    setProcessState(_arg031);
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    ProviderInfo _arg032 = data.readInt() != 0 ? ProviderInfo.CREATOR.createFromParcel(data) : null;
                    scheduleInstallProvider(_arg032);
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg033 = data.readInt();
                    updateTimePrefs(_arg033);
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg034 = data.readStrongBinder();
                    scheduleEnterAnimationComplete(_arg034);
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg035 = data.createByteArray();
                    notifyCleartextNetwork(_arg035);
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    startBinderTracking();
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelFileDescriptor _arg036 = data.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(data) : null;
                    stopBinderTrackingAndDump(_arg036);
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg037 = data.readStrongBinder();
                    scheduleLocalVoiceInteractionStarted(_arg037, IVoiceInteractor.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    handleTrustStorageUpdate();
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg038 = data.readString();
                    attachAgent(_arg038);
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    ApplicationInfo _arg039 = data.readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel(data) : null;
                    scheduleApplicationInfoChanged(_arg039);
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg040 = data.readLong();
                    setNetworkBlockSeq(_arg040);
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    ClientTransaction _arg041 = data.readInt() != 0 ? ClientTransaction.CREATOR.createFromParcel(data) : null;
                    scheduleTransaction(_arg041);
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg042 = data.readStrongBinder();
                    IVoiceInteractor _arg123 = IVoiceInteractor.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg22 = RemoteCallback.CREATOR.createFromParcel(data);
                    } else {
                        _arg22 = null;
                    }
                    RemoteCallback _arg312 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    requestDirectActions(_arg042, _arg123, _arg22, _arg312);
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg043 = data.readStrongBinder();
                    String _arg124 = data.readString();
                    Bundle _arg216 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    RemoteCallback _arg313 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    RemoteCallback _arg48 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    performDirectAction(_arg043, _arg124, _arg216, _arg313, _arg48);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IApplicationThread {
            public static IApplicationThread sDefaultImpl;
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

            @Override // android.app.IApplicationThread
            public void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean sync, int sendingUser, int processState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (compatInfo != null) {
                        _data.writeInt(1);
                        compatInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(resultCode);
                    _data.writeString(data);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sync ? 1 : 0);
                    _data.writeInt(sendingUser);
                    _data.writeInt(processState);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleReceiver(intent, info, compatInfo, resultCode, data, extras, sync, sendingUser, processState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (compatInfo != null) {
                        _data.writeInt(1);
                        compatInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(processState);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleCreateService(token, info, compatInfo, processState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleStopService(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleStopService(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void bindApplication(String packageName, ApplicationInfo info, List<ProviderInfo> providers, ComponentName testName, ProfilerInfo profilerInfo, Bundle testArguments, IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean restrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                Parcel _data;
                int i;
                int i2;
                Parcel _data2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data2.writeString(packageName);
                    if (info != null) {
                        try {
                            _data2.writeInt(1);
                            info.writeToParcel(_data2, 0);
                        } catch (Throwable th) {
                            th = th;
                            _data = _data2;
                            _data.recycle();
                            throw th;
                        }
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeTypedList(providers);
                    if (testName != null) {
                        _data2.writeInt(1);
                        testName.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (profilerInfo != null) {
                        _data2.writeInt(1);
                        profilerInfo.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (testArguments != null) {
                        _data2.writeInt(1);
                        testArguments.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeStrongBinder(testWatcher != null ? testWatcher.asBinder() : null);
                    _data2.writeStrongBinder(uiAutomationConnection != null ? uiAutomationConnection.asBinder() : null);
                    _data2.writeInt(debugMode);
                    _data2.writeInt(enableBinderTracking ? 1 : 0);
                    _data2.writeInt(trackAllocation ? 1 : 0);
                    _data2.writeInt(restrictedBackupMode ? 1 : 0);
                    _data2.writeInt(persistent ? 1 : 0);
                    if (config != null) {
                        _data2.writeInt(1);
                        i = 0;
                        config.writeToParcel(_data2, 0);
                    } else {
                        i = 0;
                        _data2.writeInt(0);
                    }
                    if (compatInfo != null) {
                        _data2.writeInt(1);
                        compatInfo.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(i);
                    }
                    _data2.writeMap(services);
                    if (coreSettings != null) {
                        _data2.writeInt(1);
                        coreSettings.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeString(buildSerial);
                    if (autofillOptions != null) {
                        _data2.writeInt(1);
                        i2 = 0;
                        autofillOptions.writeToParcel(_data2, 0);
                    } else {
                        i2 = 0;
                        _data2.writeInt(0);
                    }
                    if (contentCaptureOptions != null) {
                        _data2.writeInt(1);
                        contentCaptureOptions.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(i2);
                    }
                    boolean _status = this.mRemote.transact(4, _data2, null, 1);
                    if (_status || Stub.getDefaultImpl() == null) {
                        _data2.recycle();
                        return;
                    }
                    _data = _data2;
                    try {
                        Stub.getDefaultImpl().bindApplication(packageName, info, providers, testName, profilerInfo, testArguments, testWatcher, uiAutomationConnection, debugMode, enableBinderTracking, trackAllocation, restrictedBackupMode, persistent, config, compatInfo, services, coreSettings, buildSerial, autofillOptions, contentCaptureOptions);
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _data = _data2;
                }
            }

            @Override // android.app.IApplicationThread
            public void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(entryPoint);
                    _data.writeStringArray(entryPointArgs);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().runIsolatedEntryPoint(entryPoint, entryPointArgs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleExit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleExit();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleServiceArgs(IBinder token, ParceledListSlice args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleServiceArgs(token, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimeZone() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateTimeZone();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void processInBackground() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().processInBackground();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(rebind ? 1 : 0);
                    _data.writeInt(processState);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleBindService(token, intent, rebind, processState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleUnbindService(IBinder token, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleUnbindService(token, intent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpService(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(servicetoken);
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpService(fd, servicetoken, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser, int processState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(resultCode);
                    _data.writeString(data);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(ordered ? 1 : 0);
                    _data.writeInt(sticky ? 1 : 0);
                    _data.writeInt(sendingUser);
                    _data.writeInt(processState);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleRegisteredReceiver(receiver, intent, resultCode, data, extras, ordered, sticky, sendingUser, processState);
                        _data.recycle();
                        return;
                    }
                    _data.recycle();
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLowMemory() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleLowMemory();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleSleeping(IBinder token, boolean sleeping) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(sleeping ? 1 : 0);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleSleeping(token, sleeping);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(start ? 1 : 0);
                    if (profilerInfo != null) {
                        _data.writeInt(1);
                        profilerInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(profileType);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().profilerControl(start, profilerInfo, profileType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setSchedulingGroup(int group) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(group);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSchedulingGroup(group);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int backupMode, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (app != null) {
                        _data.writeInt(1);
                        app.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (compatInfo != null) {
                        _data.writeInt(1);
                        compatInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(backupMode);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleCreateBackupAgent(app, compatInfo, backupMode, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleDestroyBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (app != null) {
                        _data.writeInt(1);
                        app.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (compatInfo != null) {
                        _data.writeInt(1);
                        compatInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleDestroyBackupAgent(app, compatInfo, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleOnNewActivityOptions(IBinder token, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleOnNewActivityOptions(token, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleSuicide() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleSuicide();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dispatchPackageBroadcast(int cmd, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeStringArray(packages);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPackageBroadcast(cmd, packages);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCrash(String msg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(msg);
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleCrash(msg);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(managed ? 1 : 0);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(mallocInfo ? 1 : 0);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(runGc ? 1 : 0);
                    try {
                        _data.writeString(path);
                        if (fd != null) {
                            _data.writeInt(1);
                            fd.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (finishCallback != null) {
                            _data.writeInt(1);
                            finishCallback.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                    try {
                        boolean _status = this.mRemote.transact(24, _data, null, 1);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dumpHeap(managed, mallocInfo, runGc, path, fd, finishCallback);
                            _data.recycle();
                            return;
                        }
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpActivity(ParcelFileDescriptor fd, IBinder servicetoken, String prefix, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(servicetoken);
                    _data.writeString(prefix);
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpActivity(fd, servicetoken, prefix, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void clearDnsCache() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDnsCache();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateHttpProxy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateHttpProxy();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setCoreSettings(Bundle coreSettings) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (coreSettings != null) {
                        _data.writeInt(1);
                        coreSettings.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(28, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCoreSettings(coreSettings);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(29, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePackageCompatibilityInfo(pkg, info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTrimMemory(int level) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    boolean _status = this.mRemote.transact(30, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleTrimMemory(level);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfo(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean checkin, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (mem != null) {
                        _data.writeInt(1);
                        mem.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(checkin ? 1 : 0);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(dumpInfo ? 1 : 0);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(dumpDalvik ? 1 : 0);
                    _data.writeInt(dumpSummaryOnly ? 1 : 0);
                    _data.writeInt(dumpUnreachable ? 1 : 0);
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(31, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpMemInfo(fd, mem, checkin, dumpInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable, args);
                        _data.recycle();
                        return;
                    }
                    _data.recycle();
                } catch (Throwable th4) {
                    th = th4;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfoProto(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (mem != null) {
                        _data.writeInt(1);
                        mem.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(dumpInfo ? 1 : 0);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(dumpDalvik ? 1 : 0);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(dumpSummaryOnly ? 1 : 0);
                } catch (Throwable th4) {
                    th = th4;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(dumpUnreachable ? 1 : 0);
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(32, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpMemInfoProto(fd, mem, dumpInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable, args);
                        _data.recycle();
                        return;
                    }
                    _data.recycle();
                } catch (Throwable th5) {
                    th = th5;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpGfxInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(33, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpGfxInfo(fd, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpProvider(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(servicetoken);
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(34, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpProvider(fd, servicetoken, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpDbInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(args);
                    boolean _status = this.mRemote.transact(35, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpDbInfo(fd, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void unstableProviderDied(IBinder provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider);
                    boolean _status = this.mRemote.transact(36, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unstableProviderDied(provider);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(requestToken);
                    _data.writeInt(requestType);
                    _data.writeInt(sessionId);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(37, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestAssistContextExtras(activityToken, requestToken, requestType, sessionId, flags);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTranslucentConversionComplete(IBinder token, boolean timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(timeout ? 1 : 0);
                    boolean _status = this.mRemote.transact(38, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleTranslucentConversionComplete(token, timeout);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setProcessState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(39, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProcessState(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleInstallProvider(ProviderInfo provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provider != null) {
                        _data.writeInt(1);
                        provider.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(40, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleInstallProvider(provider);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimePrefs(int timeFormatPreference) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeFormatPreference);
                    boolean _status = this.mRemote.transact(41, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateTimePrefs(timeFormatPreference);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleEnterAnimationComplete(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(42, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleEnterAnimationComplete(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyCleartextNetwork(byte[] firstPacket) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(firstPacket);
                    boolean _status = this.mRemote.transact(43, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCleartextNetwork(firstPacket);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void startBinderTracking() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(44, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startBinderTracking();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(45, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopBinderTrackingAndDump(fd);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(voiceInteractor != null ? voiceInteractor.asBinder() : null);
                    boolean _status = this.mRemote.transact(46, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleLocalVoiceInteractionStarted(token, voiceInteractor);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void handleTrustStorageUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(47, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleTrustStorageUpdate();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachAgent(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    boolean _status = this.mRemote.transact(48, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attachAgent(path);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleApplicationInfoChanged(ApplicationInfo ai) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ai != null) {
                        _data.writeInt(1);
                        ai.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(49, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleApplicationInfoChanged(ai);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setNetworkBlockSeq(long procStateSeq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(procStateSeq);
                    boolean _status = this.mRemote.transact(50, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkBlockSeq(procStateSeq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (transaction != null) {
                        _data.writeInt(1);
                        transaction.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(51, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleTransaction(transaction);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestDirectActions(IBinder activityToken, IVoiceInteractor intractor, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(intractor != null ? intractor.asBinder() : null);
                    if (cancellationCallback != null) {
                        _data.writeInt(1);
                        cancellationCallback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(52, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestDirectActions(activityToken, intractor, cancellationCallback, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeString(actionId);
                    if (arguments != null) {
                        _data.writeInt(1);
                        arguments.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (cancellationCallback != null) {
                        _data.writeInt(1);
                        cancellationCallback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (resultCallback != null) {
                        _data.writeInt(1);
                        resultCallback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(53, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performDirectAction(activityToken, actionId, arguments, cancellationCallback, resultCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IApplicationThread impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IApplicationThread getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
