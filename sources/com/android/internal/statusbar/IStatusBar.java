package com.android.internal.statusbar;

import android.content.ComponentName;
import android.graphics.Rect;
import android.hardware.biometrics.IBiometricServiceReceiverInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStatusBar extends IInterface {
    void addQsTile(ComponentName componentName) throws RemoteException;

    void animateCollapsePanels() throws RemoteException;

    void animateExpandNotificationsPanel() throws RemoteException;

    void animateExpandSettingsPanel(String str) throws RemoteException;

    void appTransitionCancelled(int i) throws RemoteException;

    void appTransitionFinished(int i) throws RemoteException;

    void appTransitionPending(int i) throws RemoteException;

    void appTransitionStarting(int i, long j, long j2) throws RemoteException;

    void cancelPreloadRecentApps() throws RemoteException;

    void clickQsTile(ComponentName componentName) throws RemoteException;

    void disable(int i, int i2, int i3) throws RemoteException;

    void dismissKeyboardShortcutsMenu() throws RemoteException;

    void handleSystemKey(int i) throws RemoteException;

    void hideBiometricDialog() throws RemoteException;

    void hideRecentApps(boolean z, boolean z2) throws RemoteException;

    void onBiometricAuthenticated(boolean z, String str) throws RemoteException;

    void onBiometricError(String str) throws RemoteException;

    void onBiometricHelp(String str) throws RemoteException;

    void onCameraLaunchGestureDetected(int i) throws RemoteException;

    void onDisplayReady(int i) throws RemoteException;

    void onProposedRotationChanged(int i, boolean z) throws RemoteException;

    void onRecentsAnimationStateChanged(boolean z) throws RemoteException;

    void preloadRecentApps() throws RemoteException;

    void remQsTile(ComponentName componentName) throws RemoteException;

    void removeIcon(String str) throws RemoteException;

    void setIcon(String str, StatusBarIcon statusBarIcon) throws RemoteException;

    void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) throws RemoteException;

    void setSystemUiVisibility(int i, int i2, int i3, int i4, int i5, Rect rect, Rect rect2, boolean z) throws RemoteException;

    void setTopAppHidesStatusBar(boolean z) throws RemoteException;

    void setWindowState(int i, int i2, int i3) throws RemoteException;

    void showAssistDisclosure() throws RemoteException;

    void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, int i, boolean z, int i2) throws RemoteException;

    void showGlobalActionsMenu() throws RemoteException;

    void showPictureInPictureMenu() throws RemoteException;

    void showPinningEnterExitToast(boolean z) throws RemoteException;

    void showPinningEscapeToast() throws RemoteException;

    void showRecentApps(boolean z) throws RemoteException;

    void showScreenPinningRequest(int i) throws RemoteException;

    void showShutdownUi(boolean z, String str) throws RemoteException;

    void showWirelessChargingAnimation(int i) throws RemoteException;

    void startAssist(Bundle bundle) throws RemoteException;

    void toggleKeyboardShortcutsMenu(int i) throws RemoteException;

    void togglePanel() throws RemoteException;

    void toggleRecentApps() throws RemoteException;

    void toggleSplitScreen() throws RemoteException;

    void topAppWindowChanged(int i, boolean z) throws RemoteException;

    public static class Default implements IStatusBar {
        public void setIcon(String slot, StatusBarIcon icon) throws RemoteException {
        }

        public void removeIcon(String slot) throws RemoteException {
        }

        public void disable(int displayId, int state1, int state2) throws RemoteException {
        }

        public void animateExpandNotificationsPanel() throws RemoteException {
        }

        public void animateExpandSettingsPanel(String subPanel) throws RemoteException {
        }

        public void animateCollapsePanels() throws RemoteException {
        }

        public void togglePanel() throws RemoteException {
        }

        public void showWirelessChargingAnimation(int batteryLevel) throws RemoteException {
        }

        public void setSystemUiVisibility(int displayId, int vis, int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenBounds, Rect dockedBounds, boolean navbarColorManagedByIme) throws RemoteException {
        }

        public void topAppWindowChanged(int displayId, boolean menuVisible) throws RemoteException {
        }

        public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
        }

        public void setWindowState(int display, int window, int state) throws RemoteException {
        }

        public void showRecentApps(boolean triggeredFromAltTab) throws RemoteException {
        }

        public void hideRecentApps(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) throws RemoteException {
        }

        public void toggleRecentApps() throws RemoteException {
        }

        public void toggleSplitScreen() throws RemoteException {
        }

        public void preloadRecentApps() throws RemoteException {
        }

        public void cancelPreloadRecentApps() throws RemoteException {
        }

        public void showScreenPinningRequest(int taskId) throws RemoteException {
        }

        public void dismissKeyboardShortcutsMenu() throws RemoteException {
        }

        public void toggleKeyboardShortcutsMenu(int deviceId) throws RemoteException {
        }

        public void appTransitionPending(int displayId) throws RemoteException {
        }

        public void appTransitionCancelled(int displayId) throws RemoteException {
        }

        public void appTransitionStarting(int displayId, long statusBarAnimationsStartTime, long statusBarAnimationsDuration) throws RemoteException {
        }

        public void appTransitionFinished(int displayId) throws RemoteException {
        }

        public void showAssistDisclosure() throws RemoteException {
        }

        public void startAssist(Bundle args) throws RemoteException {
        }

        public void onCameraLaunchGestureDetected(int source) throws RemoteException {
        }

        public void showPictureInPictureMenu() throws RemoteException {
        }

        public void showGlobalActionsMenu() throws RemoteException {
        }

        public void onProposedRotationChanged(int rotation, boolean isValid) throws RemoteException {
        }

        public void setTopAppHidesStatusBar(boolean hidesStatusBar) throws RemoteException {
        }

        public void addQsTile(ComponentName tile) throws RemoteException {
        }

        public void remQsTile(ComponentName tile) throws RemoteException {
        }

        public void clickQsTile(ComponentName tile) throws RemoteException {
        }

        public void handleSystemKey(int key) throws RemoteException {
        }

        public void showPinningEnterExitToast(boolean entering) throws RemoteException {
        }

        public void showPinningEscapeToast() throws RemoteException {
        }

        public void showShutdownUi(boolean isReboot, String reason) throws RemoteException {
        }

        public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal receiver, int type, boolean requireConfirmation, int userId) throws RemoteException {
        }

        public void onBiometricAuthenticated(boolean authenticated, String failureReason) throws RemoteException {
        }

        public void onBiometricHelp(String message) throws RemoteException {
        }

        public void onBiometricError(String error) throws RemoteException {
        }

        public void hideBiometricDialog() throws RemoteException {
        }

        public void onDisplayReady(int displayId) throws RemoteException {
        }

        public void onRecentsAnimationStateChanged(boolean running) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStatusBar {
        private static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBar";
        static final int TRANSACTION_addQsTile = 33;
        static final int TRANSACTION_animateCollapsePanels = 6;
        static final int TRANSACTION_animateExpandNotificationsPanel = 4;
        static final int TRANSACTION_animateExpandSettingsPanel = 5;
        static final int TRANSACTION_appTransitionCancelled = 23;
        static final int TRANSACTION_appTransitionFinished = 25;
        static final int TRANSACTION_appTransitionPending = 22;
        static final int TRANSACTION_appTransitionStarting = 24;
        static final int TRANSACTION_cancelPreloadRecentApps = 18;
        static final int TRANSACTION_clickQsTile = 35;
        static final int TRANSACTION_disable = 3;
        static final int TRANSACTION_dismissKeyboardShortcutsMenu = 20;
        static final int TRANSACTION_handleSystemKey = 36;
        static final int TRANSACTION_hideBiometricDialog = 44;
        static final int TRANSACTION_hideRecentApps = 14;
        static final int TRANSACTION_onBiometricAuthenticated = 41;
        static final int TRANSACTION_onBiometricError = 43;
        static final int TRANSACTION_onBiometricHelp = 42;
        static final int TRANSACTION_onCameraLaunchGestureDetected = 28;
        static final int TRANSACTION_onDisplayReady = 45;
        static final int TRANSACTION_onProposedRotationChanged = 31;
        static final int TRANSACTION_onRecentsAnimationStateChanged = 46;
        static final int TRANSACTION_preloadRecentApps = 17;
        static final int TRANSACTION_remQsTile = 34;
        static final int TRANSACTION_removeIcon = 2;
        static final int TRANSACTION_setIcon = 1;
        static final int TRANSACTION_setImeWindowStatus = 11;
        static final int TRANSACTION_setSystemUiVisibility = 9;
        static final int TRANSACTION_setTopAppHidesStatusBar = 32;
        static final int TRANSACTION_setWindowState = 12;
        static final int TRANSACTION_showAssistDisclosure = 26;
        static final int TRANSACTION_showBiometricDialog = 40;
        static final int TRANSACTION_showGlobalActionsMenu = 30;
        static final int TRANSACTION_showPictureInPictureMenu = 29;
        static final int TRANSACTION_showPinningEnterExitToast = 37;
        static final int TRANSACTION_showPinningEscapeToast = 38;
        static final int TRANSACTION_showRecentApps = 13;
        static final int TRANSACTION_showScreenPinningRequest = 19;
        static final int TRANSACTION_showShutdownUi = 39;
        static final int TRANSACTION_showWirelessChargingAnimation = 8;
        static final int TRANSACTION_startAssist = 27;
        static final int TRANSACTION_toggleKeyboardShortcutsMenu = 21;
        static final int TRANSACTION_togglePanel = 7;
        static final int TRANSACTION_toggleRecentApps = 15;
        static final int TRANSACTION_toggleSplitScreen = 16;
        static final int TRANSACTION_topAppWindowChanged = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBar asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IStatusBar)) {
                return new Proxy(obj);
            }
            return (IStatusBar) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setIcon";
                case 2:
                    return "removeIcon";
                case 3:
                    return "disable";
                case 4:
                    return "animateExpandNotificationsPanel";
                case 5:
                    return "animateExpandSettingsPanel";
                case 6:
                    return "animateCollapsePanels";
                case 7:
                    return "togglePanel";
                case 8:
                    return "showWirelessChargingAnimation";
                case 9:
                    return "setSystemUiVisibility";
                case 10:
                    return "topAppWindowChanged";
                case 11:
                    return "setImeWindowStatus";
                case 12:
                    return "setWindowState";
                case 13:
                    return "showRecentApps";
                case 14:
                    return "hideRecentApps";
                case 15:
                    return "toggleRecentApps";
                case 16:
                    return "toggleSplitScreen";
                case 17:
                    return "preloadRecentApps";
                case 18:
                    return "cancelPreloadRecentApps";
                case 19:
                    return "showScreenPinningRequest";
                case 20:
                    return "dismissKeyboardShortcutsMenu";
                case 21:
                    return "toggleKeyboardShortcutsMenu";
                case 22:
                    return "appTransitionPending";
                case 23:
                    return "appTransitionCancelled";
                case 24:
                    return "appTransitionStarting";
                case 25:
                    return "appTransitionFinished";
                case 26:
                    return "showAssistDisclosure";
                case 27:
                    return "startAssist";
                case 28:
                    return "onCameraLaunchGestureDetected";
                case 29:
                    return "showPictureInPictureMenu";
                case 30:
                    return "showGlobalActionsMenu";
                case 31:
                    return "onProposedRotationChanged";
                case 32:
                    return "setTopAppHidesStatusBar";
                case 33:
                    return "addQsTile";
                case 34:
                    return "remQsTile";
                case 35:
                    return "clickQsTile";
                case 36:
                    return "handleSystemKey";
                case 37:
                    return "showPinningEnterExitToast";
                case 38:
                    return "showPinningEscapeToast";
                case 39:
                    return "showShutdownUi";
                case 40:
                    return "showBiometricDialog";
                case 41:
                    return "onBiometricAuthenticated";
                case 42:
                    return "onBiometricHelp";
                case 43:
                    return "onBiometricError";
                case 44:
                    return "hideBiometricDialog";
                case 45:
                    return "onDisplayReady";
                case 46:
                    return "onRecentsAnimationStateChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: com.android.internal.statusbar.StatusBarIcon} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v41, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v11 */
        /* JADX WARNING: type inference failed for: r0v58 */
        /* JADX WARNING: type inference failed for: r0v73 */
        /* JADX WARNING: type inference failed for: r0v74 */
        /* JADX WARNING: type inference failed for: r0v75 */
        /* JADX WARNING: type inference failed for: r0v76 */
        /* JADX WARNING: type inference failed for: r0v77 */
        /* JADX WARNING: type inference failed for: r0v78 */
        /* JADX WARNING: type inference failed for: r0v79 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r9 = r19
                r10 = r20
                r11 = r21
                java.lang.String r12 = "com.android.internal.statusbar.IStatusBar"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r10 == r0) goto L_0x030c
                r0 = 0
                r2 = 0
                switch(r10) {
                    case 1: goto L_0x02f1;
                    case 2: goto L_0x02e6;
                    case 3: goto L_0x02d3;
                    case 4: goto L_0x02cc;
                    case 5: goto L_0x02c1;
                    case 6: goto L_0x02ba;
                    case 7: goto L_0x02b3;
                    case 8: goto L_0x02a8;
                    case 9: goto L_0x0258;
                    case 10: goto L_0x0244;
                    case 11: goto L_0x021e;
                    case 12: goto L_0x020b;
                    case 13: goto L_0x01fb;
                    case 14: goto L_0x01e2;
                    case 15: goto L_0x01db;
                    case 16: goto L_0x01d4;
                    case 17: goto L_0x01cd;
                    case 18: goto L_0x01c6;
                    case 19: goto L_0x01bb;
                    case 20: goto L_0x01b4;
                    case 21: goto L_0x01a9;
                    case 22: goto L_0x019e;
                    case 23: goto L_0x0193;
                    case 24: goto L_0x017b;
                    case 25: goto L_0x0170;
                    case 26: goto L_0x0169;
                    case 27: goto L_0x0152;
                    case 28: goto L_0x0147;
                    case 29: goto L_0x0140;
                    case 30: goto L_0x0139;
                    case 31: goto L_0x0125;
                    case 32: goto L_0x0115;
                    case 33: goto L_0x00fe;
                    case 34: goto L_0x00e7;
                    case 35: goto L_0x00d0;
                    case 36: goto L_0x00c5;
                    case 37: goto L_0x00b5;
                    case 38: goto L_0x00ae;
                    case 39: goto L_0x009a;
                    case 40: goto L_0x0064;
                    case 41: goto L_0x0050;
                    case 42: goto L_0x0045;
                    case 43: goto L_0x003a;
                    case 44: goto L_0x0033;
                    case 45: goto L_0x0028;
                    case 46: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x0018:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0023
                r2 = r13
            L_0x0023:
                r0 = r2
                r9.onRecentsAnimationStateChanged(r0)
                return r13
            L_0x0028:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.onDisplayReady(r0)
                return r13
            L_0x0033:
                r11.enforceInterface(r12)
                r19.hideBiometricDialog()
                return r13
            L_0x003a:
                r11.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                r9.onBiometricError(r0)
                return r13
            L_0x0045:
                r11.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                r9.onBiometricHelp(r0)
                return r13
            L_0x0050:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x005b
                r2 = r13
            L_0x005b:
                r0 = r2
                java.lang.String r1 = r21.readString()
                r9.onBiometricAuthenticated(r0, r1)
                return r13
            L_0x0064:
                r11.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0077
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0075:
                r1 = r0
                goto L_0x0078
            L_0x0077:
                goto L_0x0075
            L_0x0078:
                android.os.IBinder r0 = r21.readStrongBinder()
                android.hardware.biometrics.IBiometricServiceReceiverInternal r6 = android.hardware.biometrics.IBiometricServiceReceiverInternal.Stub.asInterface(r0)
                int r7 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x008c
                r4 = r13
                goto L_0x008d
            L_0x008c:
                r4 = r2
            L_0x008d:
                int r8 = r21.readInt()
                r0 = r19
                r2 = r6
                r3 = r7
                r5 = r8
                r0.showBiometricDialog(r1, r2, r3, r4, r5)
                return r13
            L_0x009a:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x00a5
                r2 = r13
            L_0x00a5:
                r0 = r2
                java.lang.String r1 = r21.readString()
                r9.showShutdownUi(r0, r1)
                return r13
            L_0x00ae:
                r11.enforceInterface(r12)
                r19.showPinningEscapeToast()
                return r13
            L_0x00b5:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x00c0
                r2 = r13
            L_0x00c0:
                r0 = r2
                r9.showPinningEnterExitToast(r0)
                return r13
            L_0x00c5:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.handleSystemKey(r0)
                return r13
            L_0x00d0:
                r11.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00e2
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x00e3
            L_0x00e2:
            L_0x00e3:
                r9.clickQsTile(r0)
                return r13
            L_0x00e7:
                r11.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00f9
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x00fa
            L_0x00f9:
            L_0x00fa:
                r9.remQsTile(r0)
                return r13
            L_0x00fe:
                r11.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0110
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0111
            L_0x0110:
            L_0x0111:
                r9.addQsTile(r0)
                return r13
            L_0x0115:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0120
                r2 = r13
            L_0x0120:
                r0 = r2
                r9.setTopAppHidesStatusBar(r0)
                return r13
            L_0x0125:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0134
                r2 = r13
            L_0x0134:
                r1 = r2
                r9.onProposedRotationChanged(r0, r1)
                return r13
            L_0x0139:
                r11.enforceInterface(r12)
                r19.showGlobalActionsMenu()
                return r13
            L_0x0140:
                r11.enforceInterface(r12)
                r19.showPictureInPictureMenu()
                return r13
            L_0x0147:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.onCameraLaunchGestureDetected(r0)
                return r13
            L_0x0152:
                r11.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0164
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0165
            L_0x0164:
            L_0x0165:
                r9.startAssist(r0)
                return r13
            L_0x0169:
                r11.enforceInterface(r12)
                r19.showAssistDisclosure()
                return r13
            L_0x0170:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.appTransitionFinished(r0)
                return r13
            L_0x017b:
                r11.enforceInterface(r12)
                int r6 = r21.readInt()
                long r7 = r21.readLong()
                long r14 = r21.readLong()
                r0 = r19
                r1 = r6
                r2 = r7
                r4 = r14
                r0.appTransitionStarting(r1, r2, r4)
                return r13
            L_0x0193:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.appTransitionCancelled(r0)
                return r13
            L_0x019e:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.appTransitionPending(r0)
                return r13
            L_0x01a9:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.toggleKeyboardShortcutsMenu(r0)
                return r13
            L_0x01b4:
                r11.enforceInterface(r12)
                r19.dismissKeyboardShortcutsMenu()
                return r13
            L_0x01bb:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.showScreenPinningRequest(r0)
                return r13
            L_0x01c6:
                r11.enforceInterface(r12)
                r19.cancelPreloadRecentApps()
                return r13
            L_0x01cd:
                r11.enforceInterface(r12)
                r19.preloadRecentApps()
                return r13
            L_0x01d4:
                r11.enforceInterface(r12)
                r19.toggleSplitScreen()
                return r13
            L_0x01db:
                r11.enforceInterface(r12)
                r19.toggleRecentApps()
                return r13
            L_0x01e2:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x01ed
                r0 = r13
                goto L_0x01ee
            L_0x01ed:
                r0 = r2
            L_0x01ee:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x01f6
                r2 = r13
            L_0x01f6:
                r1 = r2
                r9.hideRecentApps(r0, r1)
                return r13
            L_0x01fb:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0206
                r2 = r13
            L_0x0206:
                r0 = r2
                r9.showRecentApps(r0)
                return r13
            L_0x020b:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                r9.setWindowState(r0, r1, r2)
                return r13
            L_0x021e:
                r11.enforceInterface(r12)
                int r6 = r21.readInt()
                android.os.IBinder r7 = r21.readStrongBinder()
                int r8 = r21.readInt()
                int r14 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0239
                r5 = r13
                goto L_0x023a
            L_0x0239:
                r5 = r2
            L_0x023a:
                r0 = r19
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r14
                r0.setImeWindowStatus(r1, r2, r3, r4, r5)
                return r13
            L_0x0244:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0253
                r2 = r13
            L_0x0253:
                r1 = r2
                r9.topAppWindowChanged(r0, r1)
                return r13
            L_0x0258:
                r11.enforceInterface(r12)
                int r14 = r21.readInt()
                int r15 = r21.readInt()
                int r16 = r21.readInt()
                int r17 = r21.readInt()
                int r18 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x027f
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r6 = r1
                goto L_0x0280
            L_0x027f:
                r6 = r0
            L_0x0280:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0290
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x028e:
                r7 = r0
                goto L_0x0291
            L_0x0290:
                goto L_0x028e
            L_0x0291:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0299
                r8 = r13
                goto L_0x029a
            L_0x0299:
                r8 = r2
            L_0x029a:
                r0 = r19
                r1 = r14
                r2 = r15
                r3 = r16
                r4 = r17
                r5 = r18
                r0.setSystemUiVisibility(r1, r2, r3, r4, r5, r6, r7, r8)
                return r13
            L_0x02a8:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                r9.showWirelessChargingAnimation(r0)
                return r13
            L_0x02b3:
                r11.enforceInterface(r12)
                r19.togglePanel()
                return r13
            L_0x02ba:
                r11.enforceInterface(r12)
                r19.animateCollapsePanels()
                return r13
            L_0x02c1:
                r11.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                r9.animateExpandSettingsPanel(r0)
                return r13
            L_0x02cc:
                r11.enforceInterface(r12)
                r19.animateExpandNotificationsPanel()
                return r13
            L_0x02d3:
                r11.enforceInterface(r12)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                r9.disable(r0, r1, r2)
                return r13
            L_0x02e6:
                r11.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                r9.removeIcon(r0)
                return r13
            L_0x02f1:
                r11.enforceInterface(r12)
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0307
                android.os.Parcelable$Creator<com.android.internal.statusbar.StatusBarIcon> r0 = com.android.internal.statusbar.StatusBarIcon.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                com.android.internal.statusbar.StatusBarIcon r0 = (com.android.internal.statusbar.StatusBarIcon) r0
                goto L_0x0308
            L_0x0307:
            L_0x0308:
                r9.setIcon(r1, r0)
                return r13
            L_0x030c:
                r0 = r22
                r0.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.statusbar.IStatusBar.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IStatusBar {
            public static IStatusBar sDefaultImpl;
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

            public void setIcon(String slot, StatusBarIcon icon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setIcon(slot, icon);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeIcon(String slot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeIcon(slot);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void disable(int displayId, int state1, int state2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(state1);
                    _data.writeInt(state2);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disable(displayId, state1, state2);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void animateExpandNotificationsPanel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().animateExpandNotificationsPanel();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void animateExpandSettingsPanel(String subPanel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subPanel);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().animateExpandSettingsPanel(subPanel);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void animateCollapsePanels() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().animateCollapsePanels();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void togglePanel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().togglePanel();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showWirelessChargingAnimation(int batteryLevel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(batteryLevel);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showWirelessChargingAnimation(batteryLevel);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSystemUiVisibility(int displayId, int vis, int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenBounds, Rect dockedBounds, boolean navbarColorManagedByIme) throws RemoteException {
                Rect rect = fullscreenBounds;
                Rect rect2 = dockedBounds;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(displayId);
                        try {
                            _data.writeInt(vis);
                        } catch (Throwable th) {
                            th = th;
                            int i = fullscreenStackVis;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(fullscreenStackVis);
                            _data.writeInt(dockedStackVis);
                            _data.writeInt(mask);
                            if (rect != null) {
                                _data.writeInt(1);
                                rect.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (rect2 != null) {
                                _data.writeInt(1);
                                rect2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(navbarColorManagedByIme ? 1 : 0);
                            if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setSystemUiVisibility(displayId, vis, fullscreenStackVis, dockedStackVis, mask, fullscreenBounds, dockedBounds, navbarColorManagedByIme);
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i2 = vis;
                        int i3 = fullscreenStackVis;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i4 = displayId;
                    int i22 = vis;
                    int i32 = fullscreenStackVis;
                    _data.recycle();
                    throw th;
                }
            }

            public void topAppWindowChanged(int displayId, boolean menuVisible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(menuVisible);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().topAppWindowChanged(displayId, menuVisible);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(token);
                    _data.writeInt(vis);
                    _data.writeInt(backDisposition);
                    _data.writeInt(showImeSwitcher);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setImeWindowStatus(displayId, token, vis, backDisposition, showImeSwitcher);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setWindowState(int display, int window, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(display);
                    _data.writeInt(window);
                    _data.writeInt(state);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setWindowState(display, window, state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showRecentApps(boolean triggeredFromAltTab) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(triggeredFromAltTab);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showRecentApps(triggeredFromAltTab);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void hideRecentApps(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(triggeredFromAltTab);
                    _data.writeInt(triggeredFromHomeKey);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().hideRecentApps(triggeredFromAltTab, triggeredFromHomeKey);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void toggleRecentApps() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().toggleRecentApps();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void toggleSplitScreen() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().toggleSplitScreen();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void preloadRecentApps() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().preloadRecentApps();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelPreloadRecentApps() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelPreloadRecentApps();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showScreenPinningRequest(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showScreenPinningRequest(taskId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dismissKeyboardShortcutsMenu() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dismissKeyboardShortcutsMenu();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void toggleKeyboardShortcutsMenu(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().toggleKeyboardShortcutsMenu(deviceId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void appTransitionPending(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().appTransitionPending(displayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void appTransitionCancelled(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().appTransitionCancelled(displayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void appTransitionStarting(int displayId, long statusBarAnimationsStartTime, long statusBarAnimationsDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeLong(statusBarAnimationsStartTime);
                    _data.writeLong(statusBarAnimationsDuration);
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().appTransitionStarting(displayId, statusBarAnimationsStartTime, statusBarAnimationsDuration);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void appTransitionFinished(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(25, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().appTransitionFinished(displayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showAssistDisclosure() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(26, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showAssistDisclosure();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startAssist(Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startAssist(args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCameraLaunchGestureDetected(int source) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(source);
                    if (this.mRemote.transact(28, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCameraLaunchGestureDetected(source);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showPictureInPictureMenu() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(29, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showPictureInPictureMenu();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showGlobalActionsMenu() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(30, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showGlobalActionsMenu();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProposedRotationChanged(int rotation, boolean isValid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    _data.writeInt(isValid);
                    if (this.mRemote.transact(31, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProposedRotationChanged(rotation, isValid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setTopAppHidesStatusBar(boolean hidesStatusBar) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hidesStatusBar);
                    if (this.mRemote.transact(32, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setTopAppHidesStatusBar(hidesStatusBar);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addQsTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(33, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addQsTile(tile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void remQsTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(34, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().remQsTile(tile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void clickQsTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(35, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clickQsTile(tile);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void handleSystemKey(int key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    if (this.mRemote.transact(36, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().handleSystemKey(key);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showPinningEnterExitToast(boolean entering) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(entering);
                    if (this.mRemote.transact(37, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showPinningEnterExitToast(entering);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showPinningEscapeToast() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(38, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showPinningEscapeToast();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showShutdownUi(boolean isReboot, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isReboot);
                    _data.writeString(reason);
                    if (this.mRemote.transact(39, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showShutdownUi(isReboot, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal receiver, int type, boolean requireConfirmation, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        _data.writeInt(1);
                        bundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    _data.writeInt(type);
                    _data.writeInt(requireConfirmation);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(40, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().showBiometricDialog(bundle, receiver, type, requireConfirmation, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBiometricAuthenticated(boolean authenticated, String failureReason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(authenticated);
                    _data.writeString(failureReason);
                    if (this.mRemote.transact(41, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBiometricAuthenticated(authenticated, failureReason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBiometricHelp(String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    if (this.mRemote.transact(42, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBiometricHelp(message);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBiometricError(String error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(error);
                    if (this.mRemote.transact(43, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBiometricError(error);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void hideBiometricDialog() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(44, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().hideBiometricDialog();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDisplayReady(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(45, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDisplayReady(displayId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRecentsAnimationStateChanged(boolean running) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(running);
                    if (this.mRemote.transact(46, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRecentsAnimationStateChanged(running);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IStatusBar impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IStatusBar getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
