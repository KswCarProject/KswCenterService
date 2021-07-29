package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.servertransaction.ClientTransaction;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractor;
import java.util.List;
import java.util.Map;

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

    public static class Default implements IApplicationThread {
        public void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean sync, int sendingUser, int processState) throws RemoteException {
        }

        public void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) throws RemoteException {
        }

        public void scheduleStopService(IBinder token) throws RemoteException {
        }

        public void bindApplication(String packageName, ApplicationInfo info, List<ProviderInfo> list, ComponentName testName, ProfilerInfo profilerInfo, Bundle testArguments, IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean restrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }

        public void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) throws RemoteException {
        }

        public void scheduleExit() throws RemoteException {
        }

        public void scheduleServiceArgs(IBinder token, ParceledListSlice args) throws RemoteException {
        }

        public void updateTimeZone() throws RemoteException {
        }

        public void processInBackground() throws RemoteException {
        }

        public void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState) throws RemoteException {
        }

        public void scheduleUnbindService(IBinder token, Intent intent) throws RemoteException {
        }

        public void dumpService(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
        }

        public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser, int processState) throws RemoteException {
        }

        public void scheduleLowMemory() throws RemoteException {
        }

        public void scheduleSleeping(IBinder token, boolean sleeping) throws RemoteException {
        }

        public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
        }

        public void setSchedulingGroup(int group) throws RemoteException {
        }

        public void scheduleCreateBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int backupMode, int userId) throws RemoteException {
        }

        public void scheduleDestroyBackupAgent(ApplicationInfo app, CompatibilityInfo compatInfo, int userId) throws RemoteException {
        }

        public void scheduleOnNewActivityOptions(IBinder token, Bundle options) throws RemoteException {
        }

        public void scheduleSuicide() throws RemoteException {
        }

        public void dispatchPackageBroadcast(int cmd, String[] packages) throws RemoteException {
        }

        public void scheduleCrash(String msg) throws RemoteException {
        }

        public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
        }

        public void dumpActivity(ParcelFileDescriptor fd, IBinder servicetoken, String prefix, String[] args) throws RemoteException {
        }

        public void clearDnsCache() throws RemoteException {
        }

        public void updateHttpProxy() throws RemoteException {
        }

        public void setCoreSettings(Bundle coreSettings) throws RemoteException {
        }

        public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) throws RemoteException {
        }

        public void scheduleTrimMemory(int level) throws RemoteException {
        }

        public void dumpMemInfo(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean checkin, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
        }

        public void dumpMemInfoProto(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
        }

        public void dumpGfxInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        public void dumpProvider(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
        }

        public void dumpDbInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        public void unstableProviderDied(IBinder provider) throws RemoteException {
        }

        public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) throws RemoteException {
        }

        public void scheduleTranslucentConversionComplete(IBinder token, boolean timeout) throws RemoteException {
        }

        public void setProcessState(int state) throws RemoteException {
        }

        public void scheduleInstallProvider(ProviderInfo provider) throws RemoteException {
        }

        public void updateTimePrefs(int timeFormatPreference) throws RemoteException {
        }

        public void scheduleEnterAnimationComplete(IBinder token) throws RemoteException {
        }

        public void notifyCleartextNetwork(byte[] firstPacket) throws RemoteException {
        }

        public void startBinderTracking() throws RemoteException {
        }

        public void stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
        }

        public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
        }

        public void handleTrustStorageUpdate() throws RemoteException {
        }

        public void attachAgent(String path) throws RemoteException {
        }

        public void scheduleApplicationInfoChanged(ApplicationInfo ai) throws RemoteException {
        }

        public void setNetworkBlockSeq(long procStateSeq) throws RemoteException {
        }

        public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
        }

        public void requestDirectActions(IBinder activityToken, IVoiceInteractor intractor, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
        }

        public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IApplicationThread)) {
                return new Proxy(obj);
            }
            return (IApplicationThread) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v42, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v15, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v24, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v66, resolved type: android.app.ProfilerInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v27, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v30, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v33, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v78, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v36, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v39, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v78, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v42, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v50, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v53, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v56, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v60, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v103, resolved type: android.content.pm.ProviderInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v63, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v66, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v116, resolved type: android.content.pm.ApplicationInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v69, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v121, resolved type: android.app.servertransaction.ClientTransaction} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v72, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: android.os.RemoteCallback} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v75, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v76, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v77, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v78, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v79, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v80, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v81, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v82, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v83, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v84, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v85, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v86, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v87, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v88, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v89, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v90, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v91, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v92, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v93, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v94, resolved type: android.content.res.CompatibilityInfo} */
        /* JADX WARNING: type inference failed for: r7v11, types: [android.content.pm.ParceledListSlice] */
        /* JADX WARNING: type inference failed for: r2v14, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r7v14, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v47, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r7v17, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v40, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v20, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v26, types: [android.app.ProfilerInfo] */
        /* JADX WARNING: type inference failed for: r7v35, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v74, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v38, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v41, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v86, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v52, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v90, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v55, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v94, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v58, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v62, types: [android.content.pm.ProviderInfo] */
        /* JADX WARNING: type inference failed for: r0v110, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v65, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r7v68, types: [android.content.pm.ApplicationInfo] */
        /* JADX WARNING: type inference failed for: r7v71, types: [android.app.servertransaction.ClientTransaction] */
        /* JADX WARNING: type inference failed for: r7v74, types: [android.os.RemoteCallback] */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r32, android.os.Parcel r33, android.os.Parcel r34, int r35) throws android.os.RemoteException {
            /*
                r31 = this;
                r15 = r31
                r14 = r32
                r13 = r33
                java.lang.String r12 = "android.app.IApplicationThread"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r21 = 1
                if (r14 == r0) goto L_0x0727
                r0 = 0
                r7 = 0
                switch(r14) {
                    case 1: goto L_0x06bb;
                    case 2: goto L_0x0687;
                    case 3: goto L_0x0674;
                    case 4: goto L_0x055f;
                    case 5: goto L_0x0550;
                    case 6: goto L_0x0549;
                    case 7: goto L_0x052c;
                    case 8: goto L_0x0525;
                    case 9: goto L_0x051e;
                    case 10: goto L_0x04f4;
                    case 11: goto L_0x04d7;
                    case 12: goto L_0x04b6;
                    case 13: goto L_0x0457;
                    case 14: goto L_0x0450;
                    case 15: goto L_0x043c;
                    case 16: goto L_0x0416;
                    case 17: goto L_0x040b;
                    case 18: goto L_0x03da;
                    case 19: goto L_0x03ad;
                    case 20: goto L_0x0390;
                    case 21: goto L_0x0389;
                    case 22: goto L_0x037a;
                    case 23: goto L_0x036f;
                    case 24: goto L_0x0321;
                    case 25: goto L_0x02fc;
                    case 26: goto L_0x02f5;
                    case 27: goto L_0x02ee;
                    case 28: goto L_0x02d5;
                    case 29: goto L_0x02b8;
                    case 30: goto L_0x02ad;
                    case 31: goto L_0x024d;
                    case 32: goto L_0x01f7;
                    case 33: goto L_0x01da;
                    case 34: goto L_0x01b9;
                    case 35: goto L_0x019c;
                    case 36: goto L_0x0191;
                    case 37: goto L_0x016f;
                    case 38: goto L_0x015b;
                    case 39: goto L_0x0150;
                    case 40: goto L_0x0137;
                    case 41: goto L_0x012c;
                    case 42: goto L_0x0121;
                    case 43: goto L_0x0116;
                    case 44: goto L_0x010f;
                    case 45: goto L_0x00f6;
                    case 46: goto L_0x00e3;
                    case 47: goto L_0x00dc;
                    case 48: goto L_0x00d1;
                    case 49: goto L_0x00b8;
                    case 50: goto L_0x00ad;
                    case 51: goto L_0x0094;
                    case 52: goto L_0x005f;
                    case 53: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r32, r33, r34, r35)
                return r0
            L_0x0019:
                r13.enforceInterface(r12)
                android.os.IBinder r6 = r33.readStrongBinder()
                java.lang.String r8 = r33.readString()
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0034
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r3 = r0
                goto L_0x0035
            L_0x0034:
                r3 = r7
            L_0x0035:
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0045
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r4 = r0
                goto L_0x0046
            L_0x0045:
                r4 = r7
            L_0x0046:
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0056
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r5 = r0
                goto L_0x0057
            L_0x0056:
                r5 = r7
            L_0x0057:
                r0 = r31
                r1 = r6
                r2 = r8
                r0.performDirectAction(r1, r2, r3, r4, r5)
                return r21
            L_0x005f:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                android.os.IBinder r1 = r33.readStrongBinder()
                com.android.internal.app.IVoiceInteractor r1 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(r1)
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x007d
                android.os.Parcelable$Creator<android.os.RemoteCallback> r2 = android.os.RemoteCallback.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.os.RemoteCallback r2 = (android.os.RemoteCallback) r2
                goto L_0x007e
            L_0x007d:
                r2 = r7
            L_0x007e:
                int r3 = r33.readInt()
                if (r3 == 0) goto L_0x008e
                android.os.Parcelable$Creator<android.os.RemoteCallback> r3 = android.os.RemoteCallback.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                r7 = r3
                android.os.RemoteCallback r7 = (android.os.RemoteCallback) r7
                goto L_0x008f
            L_0x008e:
            L_0x008f:
                r3 = r7
                r15.requestDirectActions(r0, r1, r2, r3)
                return r21
            L_0x0094:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x00a7
                android.os.Parcelable$Creator<android.app.servertransaction.ClientTransaction> r0 = android.app.servertransaction.ClientTransaction.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.app.servertransaction.ClientTransaction r7 = (android.app.servertransaction.ClientTransaction) r7
                goto L_0x00a8
            L_0x00a7:
            L_0x00a8:
                r0 = r7
                r15.scheduleTransaction(r0)
                return r21
            L_0x00ad:
                r13.enforceInterface(r12)
                long r0 = r33.readLong()
                r15.setNetworkBlockSeq(r0)
                return r21
            L_0x00b8:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x00cb
                android.os.Parcelable$Creator<android.content.pm.ApplicationInfo> r0 = android.content.pm.ApplicationInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.content.pm.ApplicationInfo r7 = (android.content.pm.ApplicationInfo) r7
                goto L_0x00cc
            L_0x00cb:
            L_0x00cc:
                r0 = r7
                r15.scheduleApplicationInfoChanged(r0)
                return r21
            L_0x00d1:
                r13.enforceInterface(r12)
                java.lang.String r0 = r33.readString()
                r15.attachAgent(r0)
                return r21
            L_0x00dc:
                r13.enforceInterface(r12)
                r31.handleTrustStorageUpdate()
                return r21
            L_0x00e3:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                android.os.IBinder r1 = r33.readStrongBinder()
                com.android.internal.app.IVoiceInteractor r1 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(r1)
                r15.scheduleLocalVoiceInteractionStarted(r0, r1)
                return r21
            L_0x00f6:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0109
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.ParcelFileDescriptor r7 = (android.os.ParcelFileDescriptor) r7
                goto L_0x010a
            L_0x0109:
            L_0x010a:
                r0 = r7
                r15.stopBinderTrackingAndDump(r0)
                return r21
            L_0x010f:
                r13.enforceInterface(r12)
                r31.startBinderTracking()
                return r21
            L_0x0116:
                r13.enforceInterface(r12)
                byte[] r0 = r33.createByteArray()
                r15.notifyCleartextNetwork(r0)
                return r21
            L_0x0121:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                r15.scheduleEnterAnimationComplete(r0)
                return r21
            L_0x012c:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                r15.updateTimePrefs(r0)
                return r21
            L_0x0137:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x014a
                android.os.Parcelable$Creator<android.content.pm.ProviderInfo> r0 = android.content.pm.ProviderInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.content.pm.ProviderInfo r7 = (android.content.pm.ProviderInfo) r7
                goto L_0x014b
            L_0x014a:
            L_0x014b:
                r0 = r7
                r15.scheduleInstallProvider(r0)
                return r21
            L_0x0150:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                r15.setProcessState(r0)
                return r21
            L_0x015b:
                r13.enforceInterface(r12)
                android.os.IBinder r1 = r33.readStrongBinder()
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x016b
                r0 = r21
            L_0x016b:
                r15.scheduleTranslucentConversionComplete(r1, r0)
                return r21
            L_0x016f:
                r13.enforceInterface(r12)
                android.os.IBinder r6 = r33.readStrongBinder()
                android.os.IBinder r7 = r33.readStrongBinder()
                int r8 = r33.readInt()
                int r9 = r33.readInt()
                int r10 = r33.readInt()
                r0 = r31
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                r0.requestAssistContextExtras(r1, r2, r3, r4, r5)
                return r21
            L_0x0191:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                r15.unstableProviderDied(r0)
                return r21
            L_0x019c:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x01af
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.ParcelFileDescriptor r7 = (android.os.ParcelFileDescriptor) r7
                goto L_0x01b0
            L_0x01af:
            L_0x01b0:
                r0 = r7
                java.lang.String[] r1 = r33.createStringArray()
                r15.dumpDbInfo(r0, r1)
                return r21
            L_0x01b9:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x01cc
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.ParcelFileDescriptor r7 = (android.os.ParcelFileDescriptor) r7
                goto L_0x01cd
            L_0x01cc:
            L_0x01cd:
                r0 = r7
                android.os.IBinder r1 = r33.readStrongBinder()
                java.lang.String[] r2 = r33.createStringArray()
                r15.dumpProvider(r0, r1, r2)
                return r21
            L_0x01da:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x01ed
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.ParcelFileDescriptor r7 = (android.os.ParcelFileDescriptor) r7
                goto L_0x01ee
            L_0x01ed:
            L_0x01ee:
                r0 = r7
                java.lang.String[] r1 = r33.createStringArray()
                r15.dumpGfxInfo(r0, r1)
                return r21
            L_0x01f7:
                r13.enforceInterface(r12)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0209
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.ParcelFileDescriptor r1 = (android.os.ParcelFileDescriptor) r1
                goto L_0x020a
            L_0x0209:
                r1 = r7
            L_0x020a:
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x0219
                android.os.Parcelable$Creator<android.os.Debug$MemoryInfo> r2 = android.os.Debug.MemoryInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.os.Debug$MemoryInfo r2 = (android.os.Debug.MemoryInfo) r2
                goto L_0x021a
            L_0x0219:
                r2 = r7
            L_0x021a:
                int r3 = r33.readInt()
                if (r3 == 0) goto L_0x0223
                r3 = r21
                goto L_0x0224
            L_0x0223:
                r3 = r0
            L_0x0224:
                int r4 = r33.readInt()
                if (r4 == 0) goto L_0x022d
                r4 = r21
                goto L_0x022e
            L_0x022d:
                r4 = r0
            L_0x022e:
                int r5 = r33.readInt()
                if (r5 == 0) goto L_0x0237
                r5 = r21
                goto L_0x0238
            L_0x0237:
                r5 = r0
            L_0x0238:
                int r6 = r33.readInt()
                if (r6 == 0) goto L_0x0241
                r6 = r21
                goto L_0x0242
            L_0x0241:
                r6 = r0
            L_0x0242:
                java.lang.String[] r8 = r33.createStringArray()
                r0 = r31
                r7 = r8
                r0.dumpMemInfoProto(r1, r2, r3, r4, r5, r6, r7)
                return r21
            L_0x024d:
                r13.enforceInterface(r12)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x025f
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.ParcelFileDescriptor r1 = (android.os.ParcelFileDescriptor) r1
                goto L_0x0260
            L_0x025f:
                r1 = r7
            L_0x0260:
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x026f
                android.os.Parcelable$Creator<android.os.Debug$MemoryInfo> r2 = android.os.Debug.MemoryInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.os.Debug$MemoryInfo r2 = (android.os.Debug.MemoryInfo) r2
                goto L_0x0270
            L_0x026f:
                r2 = r7
            L_0x0270:
                int r3 = r33.readInt()
                if (r3 == 0) goto L_0x0279
                r3 = r21
                goto L_0x027a
            L_0x0279:
                r3 = r0
            L_0x027a:
                int r4 = r33.readInt()
                if (r4 == 0) goto L_0x0283
                r4 = r21
                goto L_0x0284
            L_0x0283:
                r4 = r0
            L_0x0284:
                int r5 = r33.readInt()
                if (r5 == 0) goto L_0x028d
                r5 = r21
                goto L_0x028e
            L_0x028d:
                r5 = r0
            L_0x028e:
                int r6 = r33.readInt()
                if (r6 == 0) goto L_0x0297
                r6 = r21
                goto L_0x0298
            L_0x0297:
                r6 = r0
            L_0x0298:
                int r7 = r33.readInt()
                if (r7 == 0) goto L_0x02a1
                r7 = r21
                goto L_0x02a2
            L_0x02a1:
                r7 = r0
            L_0x02a2:
                java.lang.String[] r9 = r33.createStringArray()
                r0 = r31
                r8 = r9
                r0.dumpMemInfo(r1, r2, r3, r4, r5, r6, r7, r8)
                return r21
            L_0x02ad:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                r15.scheduleTrimMemory(r0)
                return r21
            L_0x02b8:
                r13.enforceInterface(r12)
                java.lang.String r0 = r33.readString()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x02cf
                android.os.Parcelable$Creator<android.content.res.CompatibilityInfo> r1 = android.content.res.CompatibilityInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.content.res.CompatibilityInfo r7 = (android.content.res.CompatibilityInfo) r7
                goto L_0x02d0
            L_0x02cf:
            L_0x02d0:
                r1 = r7
                r15.updatePackageCompatibilityInfo(r0, r1)
                return r21
            L_0x02d5:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x02e8
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.Bundle r7 = (android.os.Bundle) r7
                goto L_0x02e9
            L_0x02e8:
            L_0x02e9:
                r0 = r7
                r15.setCoreSettings(r0)
                return r21
            L_0x02ee:
                r13.enforceInterface(r12)
                r31.updateHttpProxy()
                return r21
            L_0x02f5:
                r13.enforceInterface(r12)
                r31.clearDnsCache()
                return r21
            L_0x02fc:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x030f
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.ParcelFileDescriptor r7 = (android.os.ParcelFileDescriptor) r7
                goto L_0x0310
            L_0x030f:
            L_0x0310:
                r0 = r7
                android.os.IBinder r1 = r33.readStrongBinder()
                java.lang.String r2 = r33.readString()
                java.lang.String[] r3 = r33.createStringArray()
                r15.dumpActivity(r0, r1, r2, r3)
                return r21
            L_0x0321:
                r13.enforceInterface(r12)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x032d
                r1 = r21
                goto L_0x032e
            L_0x032d:
                r1 = r0
            L_0x032e:
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x0337
                r2 = r21
                goto L_0x0338
            L_0x0337:
                r2 = r0
            L_0x0338:
                int r3 = r33.readInt()
                if (r3 == 0) goto L_0x0341
                r3 = r21
                goto L_0x0342
            L_0x0341:
                r3 = r0
            L_0x0342:
                java.lang.String r8 = r33.readString()
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0356
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                r5 = r0
                goto L_0x0357
            L_0x0356:
                r5 = r7
            L_0x0357:
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0367
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r6 = r0
                goto L_0x0368
            L_0x0367:
                r6 = r7
            L_0x0368:
                r0 = r31
                r4 = r8
                r0.dumpHeap(r1, r2, r3, r4, r5, r6)
                return r21
            L_0x036f:
                r13.enforceInterface(r12)
                java.lang.String r0 = r33.readString()
                r15.scheduleCrash(r0)
                return r21
            L_0x037a:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                java.lang.String[] r1 = r33.createStringArray()
                r15.dispatchPackageBroadcast(r0, r1)
                return r21
            L_0x0389:
                r13.enforceInterface(r12)
                r31.scheduleSuicide()
                return r21
            L_0x0390:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x03a7
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.os.Bundle r7 = (android.os.Bundle) r7
                goto L_0x03a8
            L_0x03a7:
            L_0x03a8:
                r1 = r7
                r15.scheduleOnNewActivityOptions(r0, r1)
                return r21
            L_0x03ad:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x03bf
                android.os.Parcelable$Creator<android.content.pm.ApplicationInfo> r0 = android.content.pm.ApplicationInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.pm.ApplicationInfo r0 = (android.content.pm.ApplicationInfo) r0
                goto L_0x03c0
            L_0x03bf:
                r0 = r7
            L_0x03c0:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x03d0
                android.os.Parcelable$Creator<android.content.res.CompatibilityInfo> r1 = android.content.res.CompatibilityInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.content.res.CompatibilityInfo r7 = (android.content.res.CompatibilityInfo) r7
                goto L_0x03d1
            L_0x03d0:
            L_0x03d1:
                r1 = r7
                int r2 = r33.readInt()
                r15.scheduleDestroyBackupAgent(r0, r1, r2)
                return r21
            L_0x03da:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x03ec
                android.os.Parcelable$Creator<android.content.pm.ApplicationInfo> r0 = android.content.pm.ApplicationInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.pm.ApplicationInfo r0 = (android.content.pm.ApplicationInfo) r0
                goto L_0x03ed
            L_0x03ec:
                r0 = r7
            L_0x03ed:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x03fd
                android.os.Parcelable$Creator<android.content.res.CompatibilityInfo> r1 = android.content.res.CompatibilityInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.content.res.CompatibilityInfo r7 = (android.content.res.CompatibilityInfo) r7
                goto L_0x03fe
            L_0x03fd:
            L_0x03fe:
                r1 = r7
                int r2 = r33.readInt()
                int r3 = r33.readInt()
                r15.scheduleCreateBackupAgent(r0, r1, r2, r3)
                return r21
            L_0x040b:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                r15.setSchedulingGroup(r0)
                return r21
            L_0x0416:
                r13.enforceInterface(r12)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0422
                r0 = r21
            L_0x0422:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0432
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.app.ProfilerInfo r7 = (android.app.ProfilerInfo) r7
                goto L_0x0433
            L_0x0432:
            L_0x0433:
                r1 = r7
                int r2 = r33.readInt()
                r15.profilerControl(r0, r1, r2)
                return r21
            L_0x043c:
                r13.enforceInterface(r12)
                android.os.IBinder r1 = r33.readStrongBinder()
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x044c
                r0 = r21
            L_0x044c:
                r15.scheduleSleeping(r1, r0)
                return r21
            L_0x0450:
                r13.enforceInterface(r12)
                r31.scheduleLowMemory()
                return r21
            L_0x0457:
                r13.enforceInterface(r12)
                android.os.IBinder r1 = r33.readStrongBinder()
                android.content.IIntentReceiver r10 = android.content.IIntentReceiver.Stub.asInterface(r1)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0472
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.content.Intent r1 = (android.content.Intent) r1
                r2 = r1
                goto L_0x0473
            L_0x0472:
                r2 = r7
            L_0x0473:
                int r11 = r33.readInt()
                java.lang.String r16 = r33.readString()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x048b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x048c
            L_0x048b:
                r5 = r7
            L_0x048c:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0495
                r6 = r21
                goto L_0x0496
            L_0x0495:
                r6 = r0
            L_0x0496:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x049f
                r7 = r21
                goto L_0x04a0
            L_0x049f:
                r7 = r0
            L_0x04a0:
                int r17 = r33.readInt()
                int r18 = r33.readInt()
                r0 = r31
                r1 = r10
                r3 = r11
                r4 = r16
                r8 = r17
                r9 = r18
                r0.scheduleRegisteredReceiver(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                return r21
            L_0x04b6:
                r13.enforceInterface(r12)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x04c9
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                r7 = r0
                android.os.ParcelFileDescriptor r7 = (android.os.ParcelFileDescriptor) r7
                goto L_0x04ca
            L_0x04c9:
            L_0x04ca:
                r0 = r7
                android.os.IBinder r1 = r33.readStrongBinder()
                java.lang.String[] r2 = r33.createStringArray()
                r15.dumpService(r0, r1, r2)
                return r21
            L_0x04d7:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x04ee
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.content.Intent r7 = (android.content.Intent) r7
                goto L_0x04ef
            L_0x04ee:
            L_0x04ef:
                r1 = r7
                r15.scheduleUnbindService(r0, r1)
                return r21
            L_0x04f4:
                r13.enforceInterface(r12)
                android.os.IBinder r1 = r33.readStrongBinder()
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x050b
                android.os.Parcelable$Creator<android.content.Intent> r2 = android.content.Intent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                r7 = r2
                android.content.Intent r7 = (android.content.Intent) r7
                goto L_0x050c
            L_0x050b:
            L_0x050c:
                r2 = r7
                int r3 = r33.readInt()
                if (r3 == 0) goto L_0x0516
                r0 = r21
            L_0x0516:
                int r3 = r33.readInt()
                r15.scheduleBindService(r1, r2, r0, r3)
                return r21
            L_0x051e:
                r13.enforceInterface(r12)
                r31.processInBackground()
                return r21
            L_0x0525:
                r13.enforceInterface(r12)
                r31.updateTimeZone()
                return r21
            L_0x052c:
                r13.enforceInterface(r12)
                android.os.IBinder r0 = r33.readStrongBinder()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0543
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                r7 = r1
                android.content.pm.ParceledListSlice r7 = (android.content.pm.ParceledListSlice) r7
                goto L_0x0544
            L_0x0543:
            L_0x0544:
                r1 = r7
                r15.scheduleServiceArgs(r0, r1)
                return r21
            L_0x0549:
                r13.enforceInterface(r12)
                r31.scheduleExit()
                return r21
            L_0x0550:
                r13.enforceInterface(r12)
                java.lang.String r0 = r33.readString()
                java.lang.String[] r1 = r33.createStringArray()
                r15.runIsolatedEntryPoint(r0, r1)
                return r21
            L_0x055f:
                r13.enforceInterface(r12)
                java.lang.String r22 = r33.readString()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x0576
                android.os.Parcelable$Creator<android.content.pm.ApplicationInfo> r1 = android.content.pm.ApplicationInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.content.pm.ApplicationInfo r1 = (android.content.pm.ApplicationInfo) r1
                r2 = r1
                goto L_0x0577
            L_0x0576:
                r2 = r7
            L_0x0577:
                android.os.Parcelable$Creator<android.content.pm.ProviderInfo> r1 = android.content.pm.ProviderInfo.CREATOR
                java.util.ArrayList r23 = r13.createTypedArrayList(r1)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x058d
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                r4 = r1
                goto L_0x058e
            L_0x058d:
                r4 = r7
            L_0x058e:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x059e
                android.os.Parcelable$Creator<android.app.ProfilerInfo> r1 = android.app.ProfilerInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.app.ProfilerInfo r1 = (android.app.ProfilerInfo) r1
                r5 = r1
                goto L_0x059f
            L_0x059e:
                r5 = r7
            L_0x059f:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x05af
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r6 = r1
                goto L_0x05b0
            L_0x05af:
                r6 = r7
            L_0x05b0:
                android.os.IBinder r1 = r33.readStrongBinder()
                android.app.IInstrumentationWatcher r24 = android.app.IInstrumentationWatcher.Stub.asInterface(r1)
                android.os.IBinder r1 = r33.readStrongBinder()
                android.app.IUiAutomationConnection r25 = android.app.IUiAutomationConnection.Stub.asInterface(r1)
                int r26 = r33.readInt()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x05cd
                r10 = r21
                goto L_0x05ce
            L_0x05cd:
                r10 = r0
            L_0x05ce:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x05d7
                r11 = r21
                goto L_0x05d8
            L_0x05d7:
                r11 = r0
            L_0x05d8:
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x05e1
                r1 = r21
                goto L_0x05e2
            L_0x05e1:
                r1 = r0
            L_0x05e2:
                r9 = r12
                r12 = r1
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x05ed
                r0 = r21
            L_0x05ed:
                r8 = r13
                r13 = r0
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x05fe
                android.os.Parcelable$Creator<android.content.res.Configuration> r0 = android.content.res.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.res.Configuration r0 = (android.content.res.Configuration) r0
                goto L_0x05ff
            L_0x05fe:
                r0 = r7
            L_0x05ff:
                r14 = r0
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x060f
                android.os.Parcelable$Creator<android.content.res.CompatibilityInfo> r0 = android.content.res.CompatibilityInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.res.CompatibilityInfo r0 = (android.content.res.CompatibilityInfo) r0
                goto L_0x0610
            L_0x060f:
                r0 = r7
            L_0x0610:
                r3 = r15
                r15 = r0
                java.lang.Class r0 = r31.getClass()
                java.lang.ClassLoader r1 = r0.getClassLoader()
                java.util.HashMap r27 = r8.readHashMap(r1)
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x062f
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r17 = r0
                goto L_0x0631
            L_0x062f:
                r17 = r7
            L_0x0631:
                java.lang.String r28 = r33.readString()
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0646
                android.os.Parcelable$Creator<android.content.AutofillOptions> r0 = android.content.AutofillOptions.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.AutofillOptions r0 = (android.content.AutofillOptions) r0
                r19 = r0
                goto L_0x0648
            L_0x0646:
                r19 = r7
            L_0x0648:
                int r0 = r33.readInt()
                if (r0 == 0) goto L_0x0659
                android.os.Parcelable$Creator<android.content.ContentCaptureOptions> r0 = android.content.ContentCaptureOptions.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.ContentCaptureOptions r0 = (android.content.ContentCaptureOptions) r0
                r20 = r0
                goto L_0x065b
            L_0x0659:
                r20 = r7
            L_0x065b:
                r0 = r31
                r29 = r1
                r1 = r22
                r7 = r3
                r3 = r23
                r7 = r24
                r8 = r25
                r30 = r9
                r9 = r26
                r16 = r27
                r18 = r28
                r0.bindApplication(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
                return r21
            L_0x0674:
                r30 = r12
                r11 = r30
                r10 = r33
                r10.enforceInterface(r11)
                android.os.IBinder r0 = r33.readStrongBinder()
                r12 = r31
                r12.scheduleStopService(r0)
                return r21
            L_0x0687:
                r11 = r12
                r10 = r13
                r12 = r15
                r10.enforceInterface(r11)
                android.os.IBinder r0 = r33.readStrongBinder()
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x06a0
                android.os.Parcelable$Creator<android.content.pm.ServiceInfo> r1 = android.content.pm.ServiceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.pm.ServiceInfo r1 = (android.content.pm.ServiceInfo) r1
                goto L_0x06a1
            L_0x06a0:
                r1 = r7
            L_0x06a1:
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x06b1
                android.os.Parcelable$Creator<android.content.res.CompatibilityInfo> r2 = android.content.res.CompatibilityInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                r7 = r2
                android.content.res.CompatibilityInfo r7 = (android.content.res.CompatibilityInfo) r7
                goto L_0x06b2
            L_0x06b1:
            L_0x06b2:
                r2 = r7
                int r3 = r33.readInt()
                r12.scheduleCreateService(r0, r1, r2, r3)
                return r21
            L_0x06bb:
                r11 = r12
                r10 = r13
                r12 = r15
                r10.enforceInterface(r11)
                int r1 = r33.readInt()
                if (r1 == 0) goto L_0x06d0
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x06d1
            L_0x06d0:
                r1 = r7
            L_0x06d1:
                int r2 = r33.readInt()
                if (r2 == 0) goto L_0x06e0
                android.os.Parcelable$Creator<android.content.pm.ActivityInfo> r2 = android.content.pm.ActivityInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.pm.ActivityInfo r2 = (android.content.pm.ActivityInfo) r2
                goto L_0x06e1
            L_0x06e0:
                r2 = r7
            L_0x06e1:
                int r3 = r33.readInt()
                if (r3 == 0) goto L_0x06f0
                android.os.Parcelable$Creator<android.content.res.CompatibilityInfo> r3 = android.content.res.CompatibilityInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.content.res.CompatibilityInfo r3 = (android.content.res.CompatibilityInfo) r3
                goto L_0x06f1
            L_0x06f0:
                r3 = r7
            L_0x06f1:
                int r13 = r33.readInt()
                java.lang.String r14 = r33.readString()
                int r4 = r33.readInt()
                if (r4 == 0) goto L_0x0709
                android.os.Parcelable$Creator<android.os.Bundle> r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r10)
                android.os.Bundle r4 = (android.os.Bundle) r4
                r6 = r4
                goto L_0x070a
            L_0x0709:
                r6 = r7
            L_0x070a:
                int r4 = r33.readInt()
                if (r4 == 0) goto L_0x0713
                r7 = r21
                goto L_0x0714
            L_0x0713:
                r7 = r0
            L_0x0714:
                int r15 = r33.readInt()
                int r16 = r33.readInt()
                r0 = r31
                r4 = r13
                r5 = r14
                r8 = r15
                r9 = r16
                r0.scheduleReceiver(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                return r21
            L_0x0727:
                r11 = r12
                r10 = r13
                r12 = r15
                r0 = r34
                r0.writeString(r11)
                return r21
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IApplicationThread.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IApplicationThread {
            public static IApplicationThread sDefaultImpl;
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

            public void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean sync, int sendingUser, int processState) throws RemoteException {
                Intent intent2 = intent;
                ActivityInfo activityInfo = info;
                CompatibilityInfo compatibilityInfo = compatInfo;
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent2 != null) {
                        _data.writeInt(1);
                        intent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (activityInfo != null) {
                        _data.writeInt(1);
                        activityInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (compatibilityInfo != null) {
                        _data.writeInt(1);
                        compatibilityInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(resultCode);
                    _data.writeString(data);
                    if (bundle != null) {
                        _data.writeInt(1);
                        bundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sync ? 1 : 0);
                    _data.writeInt(sendingUser);
                    _data.writeInt(processState);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleReceiver(intent, info, compatInfo, resultCode, data, extras, sync, sendingUser, processState);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleCreateService(token, info, compatInfo, processState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleStopService(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleStopService(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void bindApplication(String packageName, ApplicationInfo info, List<ProviderInfo> providers, ComponentName testName, ProfilerInfo profilerInfo, Bundle testArguments, IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean restrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                Parcel _data;
                int i;
                int i2;
                ApplicationInfo applicationInfo = info;
                ComponentName componentName = testName;
                ProfilerInfo profilerInfo2 = profilerInfo;
                Bundle bundle = testArguments;
                Configuration configuration = config;
                CompatibilityInfo compatibilityInfo = compatInfo;
                Bundle bundle2 = coreSettings;
                AutofillOptions autofillOptions2 = autofillOptions;
                ContentCaptureOptions contentCaptureOptions2 = contentCaptureOptions;
                Parcel _data2 = Parcel.obtain();
                _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                _data2.writeString(packageName);
                if (applicationInfo != null) {
                    try {
                        _data2.writeInt(1);
                        applicationInfo.writeToParcel(_data2, 0);
                    } catch (Throwable th) {
                        th = th;
                        _data = _data2;
                    }
                } else {
                    _data2.writeInt(0);
                }
                _data2.writeTypedList(providers);
                if (componentName != null) {
                    _data2.writeInt(1);
                    componentName.writeToParcel(_data2, 0);
                } else {
                    _data2.writeInt(0);
                }
                if (profilerInfo2 != null) {
                    _data2.writeInt(1);
                    profilerInfo2.writeToParcel(_data2, 0);
                } else {
                    _data2.writeInt(0);
                }
                if (bundle != null) {
                    _data2.writeInt(1);
                    bundle.writeToParcel(_data2, 0);
                } else {
                    _data2.writeInt(0);
                }
                try {
                    _data2.writeStrongBinder(testWatcher != null ? testWatcher.asBinder() : null);
                    _data2.writeStrongBinder(uiAutomationConnection != null ? uiAutomationConnection.asBinder() : null);
                    _data2.writeInt(debugMode);
                    _data2.writeInt(enableBinderTracking ? 1 : 0);
                    _data2.writeInt(trackAllocation ? 1 : 0);
                    _data2.writeInt(restrictedBackupMode ? 1 : 0);
                    _data2.writeInt(persistent ? 1 : 0);
                    if (configuration != null) {
                        _data2.writeInt(1);
                        i = 0;
                        configuration.writeToParcel(_data2, 0);
                    } else {
                        i = 0;
                        _data2.writeInt(0);
                    }
                    if (compatibilityInfo != null) {
                        _data2.writeInt(1);
                        compatibilityInfo.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(i);
                    }
                    _data2.writeMap(services);
                    if (bundle2 != null) {
                        _data2.writeInt(1);
                        bundle2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeString(buildSerial);
                    if (autofillOptions2 != null) {
                        _data2.writeInt(1);
                        i2 = 0;
                        autofillOptions2.writeToParcel(_data2, 0);
                    } else {
                        i2 = 0;
                        _data2.writeInt(0);
                    }
                    if (contentCaptureOptions2 != null) {
                        _data2.writeInt(1);
                        contentCaptureOptions2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(i2);
                    }
                    if (this.mRemote.transact(4, _data2, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
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
                    _data.recycle();
                    throw th;
                }
            }

            public void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(entryPoint);
                    _data.writeStringArray(entryPointArgs);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().runIsolatedEntryPoint(entryPoint, entryPointArgs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleExit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleExit();
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleServiceArgs(token, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateTimeZone() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateTimeZone();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void processInBackground() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().processInBackground();
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    _data.writeInt(rebind);
                    _data.writeInt(processState);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleBindService(token, intent, rebind, processState);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleUnbindService(token, intent);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dumpService(fd, servicetoken, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser, int processState) throws RemoteException {
                Intent intent2 = intent;
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (intent2 != null) {
                        _data.writeInt(1);
                        intent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(resultCode);
                        _data.writeString(data);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(ordered ? 1 : 0);
                        _data.writeInt(sticky ? 1 : 0);
                        _data.writeInt(sendingUser);
                        _data.writeInt(processState);
                        if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().scheduleRegisteredReceiver(receiver, intent, resultCode, data, extras, ordered, sticky, sendingUser, processState);
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    int i = resultCode;
                    _data.recycle();
                    throw th;
                }
            }

            public void scheduleLowMemory() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleLowMemory();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleSleeping(IBinder token, boolean sleeping) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(sleeping);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleSleeping(token, sleeping);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(start);
                    if (profilerInfo != null) {
                        _data.writeInt(1);
                        profilerInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(profileType);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().profilerControl(start, profilerInfo, profileType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSchedulingGroup(int group) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(group);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSchedulingGroup(group);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleCreateBackupAgent(app, compatInfo, backupMode, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleDestroyBackupAgent(app, compatInfo, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleOnNewActivityOptions(token, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleSuicide() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleSuicide();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchPackageBroadcast(int cmd, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeStringArray(packages);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchPackageBroadcast(cmd, packages);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleCrash(String msg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(msg);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleCrash(msg);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = fd;
                RemoteCallback remoteCallback = finishCallback;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(managed ? 1 : 0);
                        try {
                            _data.writeInt(mallocInfo ? 1 : 0);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = runGc;
                            String str = path;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(runGc ? 1 : 0);
                            try {
                                _data.writeString(path);
                                if (parcelFileDescriptor != null) {
                                    _data.writeInt(1);
                                    parcelFileDescriptor.writeToParcel(_data, 0);
                                } else {
                                    _data.writeInt(0);
                                }
                                if (remoteCallback != null) {
                                    _data.writeInt(1);
                                    remoteCallback.writeToParcel(_data, 0);
                                } else {
                                    _data.writeInt(0);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                            try {
                                if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().dumpHeap(managed, mallocInfo, runGc, path, fd, finishCallback);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            String str2 = path;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        boolean z2 = mallocInfo;
                        boolean z3 = runGc;
                        String str22 = path;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    boolean z4 = managed;
                    boolean z22 = mallocInfo;
                    boolean z32 = runGc;
                    String str222 = path;
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dumpActivity(fd, servicetoken, prefix, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void clearDnsCache() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(26, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clearDnsCache();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateHttpProxy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateHttpProxy();
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(28, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCoreSettings(coreSettings);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(29, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updatePackageCompatibilityInfo(pkg, info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleTrimMemory(int level) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    if (this.mRemote.transact(30, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleTrimMemory(level);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dumpMemInfo(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean checkin, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = fd;
                Debug.MemoryInfo memoryInfo = mem;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (memoryInfo != null) {
                        _data.writeInt(1);
                        memoryInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(checkin ? 1 : 0);
                        try {
                            _data.writeInt(dumpInfo ? 1 : 0);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = dumpDalvik;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(dumpDalvik ? 1 : 0);
                            _data.writeInt(dumpSummaryOnly ? 1 : 0);
                            _data.writeInt(dumpUnreachable ? 1 : 0);
                            _data.writeStringArray(args);
                            if (this.mRemote.transact(31, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().dumpMemInfo(fd, mem, checkin, dumpInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable, args);
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        boolean z2 = dumpInfo;
                        boolean z3 = dumpDalvik;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    boolean z4 = checkin;
                    boolean z22 = dumpInfo;
                    boolean z32 = dumpDalvik;
                    _data.recycle();
                    throw th;
                }
            }

            public void dumpMemInfoProto(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = fd;
                Debug.MemoryInfo memoryInfo = mem;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (memoryInfo != null) {
                        _data.writeInt(1);
                        memoryInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(dumpInfo ? 1 : 0);
                        try {
                            _data.writeInt(dumpDalvik ? 1 : 0);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = dumpSummaryOnly;
                            boolean z2 = dumpUnreachable;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(dumpSummaryOnly ? 1 : 0);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z22 = dumpUnreachable;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        boolean z3 = dumpDalvik;
                        boolean z4 = dumpSummaryOnly;
                        boolean z222 = dumpUnreachable;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(dumpUnreachable ? 1 : 0);
                        _data.writeStringArray(args);
                        if (this.mRemote.transact(32, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().dumpMemInfoProto(fd, mem, dumpInfo, dumpDalvik, dumpSummaryOnly, dumpUnreachable, args);
                        _data.recycle();
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    boolean z5 = dumpInfo;
                    boolean z32 = dumpDalvik;
                    boolean z42 = dumpSummaryOnly;
                    boolean z2222 = dumpUnreachable;
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (this.mRemote.transact(33, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dumpGfxInfo(fd, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(34, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dumpProvider(fd, servicetoken, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(35, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dumpDbInfo(fd, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unstableProviderDied(IBinder provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider);
                    if (this.mRemote.transact(36, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unstableProviderDied(provider);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(requestToken);
                    _data.writeInt(requestType);
                    _data.writeInt(sessionId);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(37, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestAssistContextExtras(activityToken, requestToken, requestType, sessionId, flags);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleTranslucentConversionComplete(IBinder token, boolean timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(timeout);
                    if (this.mRemote.transact(38, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleTranslucentConversionComplete(token, timeout);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setProcessState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(39, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setProcessState(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(40, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleInstallProvider(provider);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateTimePrefs(int timeFormatPreference) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeFormatPreference);
                    if (this.mRemote.transact(41, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateTimePrefs(timeFormatPreference);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleEnterAnimationComplete(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(42, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleEnterAnimationComplete(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyCleartextNetwork(byte[] firstPacket) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(firstPacket);
                    if (this.mRemote.transact(43, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyCleartextNetwork(firstPacket);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startBinderTracking() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(44, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startBinderTracking();
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(45, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopBinderTrackingAndDump(fd);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(voiceInteractor != null ? voiceInteractor.asBinder() : null);
                    if (this.mRemote.transact(46, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleLocalVoiceInteractionStarted(token, voiceInteractor);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void handleTrustStorageUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(47, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().handleTrustStorageUpdate();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void attachAgent(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    if (this.mRemote.transact(48, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().attachAgent(path);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(49, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleApplicationInfoChanged(ai);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setNetworkBlockSeq(long procStateSeq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(procStateSeq);
                    if (this.mRemote.transact(50, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setNetworkBlockSeq(procStateSeq);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(51, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().scheduleTransaction(transaction);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(52, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestDirectActions(activityToken, intractor, cancellationCallback, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(53, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().performDirectAction(activityToken, actionId, arguments, cancellationCallback, resultCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IApplicationThread impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IApplicationThread getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
