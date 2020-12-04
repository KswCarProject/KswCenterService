package com.android.internal.statusbar;

import android.annotation.UnsupportedAppUsage;
import android.app.Notification;
import android.content.ComponentName;
import android.hardware.biometrics.IBiometricServiceReceiverInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IStatusBarService extends IInterface {
    void addTile(ComponentName componentName) throws RemoteException;

    void clearNotificationEffects() throws RemoteException;

    void clickTile(ComponentName componentName) throws RemoteException;

    @UnsupportedAppUsage
    void collapsePanels() throws RemoteException;

    @UnsupportedAppUsage
    void disable(int i, IBinder iBinder, String str) throws RemoteException;

    void disable2(int i, IBinder iBinder, String str) throws RemoteException;

    void disable2ForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException;

    void disableForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void expandNotificationsPanel() throws RemoteException;

    void expandSettingsPanel(String str) throws RemoteException;

    int[] getDisableFlags(IBinder iBinder, int i) throws RemoteException;

    @UnsupportedAppUsage
    void handleSystemKey(int i) throws RemoteException;

    void hideBiometricDialog() throws RemoteException;

    void onBiometricAuthenticated(boolean z, String str) throws RemoteException;

    void onBiometricError(String str) throws RemoteException;

    void onBiometricHelp(String str) throws RemoteException;

    void onClearAllNotifications(int i) throws RemoteException;

    void onGlobalActionsHidden() throws RemoteException;

    void onGlobalActionsShown() throws RemoteException;

    void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) throws RemoteException;

    void onNotificationBubbleChanged(String str, boolean z) throws RemoteException;

    void onNotificationClear(String str, String str2, int i, int i2, String str3, int i3, int i4, NotificationVisibility notificationVisibility) throws RemoteException;

    void onNotificationClick(String str, NotificationVisibility notificationVisibility) throws RemoteException;

    void onNotificationDirectReplied(String str) throws RemoteException;

    void onNotificationError(String str, String str2, int i, int i2, int i3, String str3, int i4) throws RemoteException;

    void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) throws RemoteException;

    void onNotificationSettingsViewed(String str) throws RemoteException;

    void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) throws RemoteException;

    void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) throws RemoteException;

    void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) throws RemoteException;

    void onPanelHidden() throws RemoteException;

    void onPanelRevealed(boolean z, int i) throws RemoteException;

    void reboot(boolean z) throws RemoteException;

    RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) throws RemoteException;

    void remTile(ComponentName componentName) throws RemoteException;

    @UnsupportedAppUsage
    void removeIcon(String str) throws RemoteException;

    void setIcon(String str, String str2, int i, int i2, String str3) throws RemoteException;

    @UnsupportedAppUsage
    void setIconVisibility(String str, boolean z) throws RemoteException;

    void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) throws RemoteException;

    void setSystemUiVisibility(int i, int i2, int i3, String str) throws RemoteException;

    void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, int i, boolean z, int i2) throws RemoteException;

    void showPinningEnterExitToast(boolean z) throws RemoteException;

    void showPinningEscapeToast() throws RemoteException;

    void shutdown() throws RemoteException;

    void togglePanel() throws RemoteException;

    public static class Default implements IStatusBarService {
        public void expandNotificationsPanel() throws RemoteException {
        }

        public void collapsePanels() throws RemoteException {
        }

        public void togglePanel() throws RemoteException {
        }

        public void disable(int what, IBinder token, String pkg) throws RemoteException {
        }

        public void disableForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
        }

        public void disable2(int what, IBinder token, String pkg) throws RemoteException {
        }

        public void disable2ForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
        }

        public int[] getDisableFlags(IBinder token, int userId) throws RemoteException {
            return null;
        }

        public void setIcon(String slot, String iconPackage, int iconId, int iconLevel, String contentDescription) throws RemoteException {
        }

        public void setIconVisibility(String slot, boolean visible) throws RemoteException {
        }

        public void removeIcon(String slot) throws RemoteException {
        }

        public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
        }

        public void expandSettingsPanel(String subPanel) throws RemoteException {
        }

        public RegisterStatusBarResult registerStatusBar(IStatusBar callbacks) throws RemoteException {
            return null;
        }

        public void onPanelRevealed(boolean clearNotificationEffects, int numItems) throws RemoteException {
        }

        public void onPanelHidden() throws RemoteException {
        }

        public void clearNotificationEffects() throws RemoteException {
        }

        public void onNotificationClick(String key, NotificationVisibility nv) throws RemoteException {
        }

        public void onNotificationActionClick(String key, int actionIndex, Notification.Action action, NotificationVisibility nv, boolean generatedByAssistant) throws RemoteException {
        }

        public void onNotificationError(String pkg, String tag, int id, int uid, int initialPid, String message, int userId) throws RemoteException {
        }

        public void onClearAllNotifications(int userId) throws RemoteException {
        }

        public void onNotificationClear(String pkg, String tag, int id, int userId, String key, int dismissalSurface, int dismissalSentiment, NotificationVisibility nv) throws RemoteException {
        }

        public void onNotificationVisibilityChanged(NotificationVisibility[] newlyVisibleKeys, NotificationVisibility[] noLongerVisibleKeys) throws RemoteException {
        }

        public void onNotificationExpansionChanged(String key, boolean userAction, boolean expanded, int notificationLocation) throws RemoteException {
        }

        public void onNotificationDirectReplied(String key) throws RemoteException {
        }

        public void onNotificationSmartSuggestionsAdded(String key, int smartReplyCount, int smartActionCount, boolean generatedByAsssistant, boolean editBeforeSending) throws RemoteException {
        }

        public void onNotificationSmartReplySent(String key, int replyIndex, CharSequence reply, int notificationLocation, boolean modifiedBeforeSending) throws RemoteException {
        }

        public void onNotificationSettingsViewed(String key) throws RemoteException {
        }

        public void setSystemUiVisibility(int displayId, int vis, int mask, String cause) throws RemoteException {
        }

        public void onNotificationBubbleChanged(String key, boolean isBubble) throws RemoteException {
        }

        public void onGlobalActionsShown() throws RemoteException {
        }

        public void onGlobalActionsHidden() throws RemoteException {
        }

        public void shutdown() throws RemoteException {
        }

        public void reboot(boolean safeMode) throws RemoteException {
        }

        public void addTile(ComponentName tile) throws RemoteException {
        }

        public void remTile(ComponentName tile) throws RemoteException {
        }

        public void clickTile(ComponentName tile) throws RemoteException {
        }

        public void handleSystemKey(int key) throws RemoteException {
        }

        public void showPinningEnterExitToast(boolean entering) throws RemoteException {
        }

        public void showPinningEscapeToast() throws RemoteException {
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

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStatusBarService {
        private static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBarService";
        static final int TRANSACTION_addTile = 35;
        static final int TRANSACTION_clearNotificationEffects = 17;
        static final int TRANSACTION_clickTile = 37;
        static final int TRANSACTION_collapsePanels = 2;
        static final int TRANSACTION_disable = 4;
        static final int TRANSACTION_disable2 = 6;
        static final int TRANSACTION_disable2ForUser = 7;
        static final int TRANSACTION_disableForUser = 5;
        static final int TRANSACTION_expandNotificationsPanel = 1;
        static final int TRANSACTION_expandSettingsPanel = 13;
        static final int TRANSACTION_getDisableFlags = 8;
        static final int TRANSACTION_handleSystemKey = 38;
        static final int TRANSACTION_hideBiometricDialog = 45;
        static final int TRANSACTION_onBiometricAuthenticated = 42;
        static final int TRANSACTION_onBiometricError = 44;
        static final int TRANSACTION_onBiometricHelp = 43;
        static final int TRANSACTION_onClearAllNotifications = 21;
        static final int TRANSACTION_onGlobalActionsHidden = 32;
        static final int TRANSACTION_onGlobalActionsShown = 31;
        static final int TRANSACTION_onNotificationActionClick = 19;
        static final int TRANSACTION_onNotificationBubbleChanged = 30;
        static final int TRANSACTION_onNotificationClear = 22;
        static final int TRANSACTION_onNotificationClick = 18;
        static final int TRANSACTION_onNotificationDirectReplied = 25;
        static final int TRANSACTION_onNotificationError = 20;
        static final int TRANSACTION_onNotificationExpansionChanged = 24;
        static final int TRANSACTION_onNotificationSettingsViewed = 28;
        static final int TRANSACTION_onNotificationSmartReplySent = 27;
        static final int TRANSACTION_onNotificationSmartSuggestionsAdded = 26;
        static final int TRANSACTION_onNotificationVisibilityChanged = 23;
        static final int TRANSACTION_onPanelHidden = 16;
        static final int TRANSACTION_onPanelRevealed = 15;
        static final int TRANSACTION_reboot = 34;
        static final int TRANSACTION_registerStatusBar = 14;
        static final int TRANSACTION_remTile = 36;
        static final int TRANSACTION_removeIcon = 11;
        static final int TRANSACTION_setIcon = 9;
        static final int TRANSACTION_setIconVisibility = 10;
        static final int TRANSACTION_setImeWindowStatus = 12;
        static final int TRANSACTION_setSystemUiVisibility = 29;
        static final int TRANSACTION_showBiometricDialog = 41;
        static final int TRANSACTION_showPinningEnterExitToast = 39;
        static final int TRANSACTION_showPinningEscapeToast = 40;
        static final int TRANSACTION_shutdown = 33;
        static final int TRANSACTION_togglePanel = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBarService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IStatusBarService)) {
                return new Proxy(obj);
            }
            return (IStatusBarService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "expandNotificationsPanel";
                case 2:
                    return "collapsePanels";
                case 3:
                    return "togglePanel";
                case 4:
                    return "disable";
                case 5:
                    return "disableForUser";
                case 6:
                    return "disable2";
                case 7:
                    return "disable2ForUser";
                case 8:
                    return "getDisableFlags";
                case 9:
                    return "setIcon";
                case 10:
                    return "setIconVisibility";
                case 11:
                    return "removeIcon";
                case 12:
                    return "setImeWindowStatus";
                case 13:
                    return "expandSettingsPanel";
                case 14:
                    return "registerStatusBar";
                case 15:
                    return "onPanelRevealed";
                case 16:
                    return "onPanelHidden";
                case 17:
                    return "clearNotificationEffects";
                case 18:
                    return "onNotificationClick";
                case 19:
                    return "onNotificationActionClick";
                case 20:
                    return "onNotificationError";
                case 21:
                    return "onClearAllNotifications";
                case 22:
                    return "onNotificationClear";
                case 23:
                    return "onNotificationVisibilityChanged";
                case 24:
                    return "onNotificationExpansionChanged";
                case 25:
                    return "onNotificationDirectReplied";
                case 26:
                    return "onNotificationSmartSuggestionsAdded";
                case 27:
                    return "onNotificationSmartReplySent";
                case 28:
                    return "onNotificationSettingsViewed";
                case 29:
                    return "setSystemUiVisibility";
                case 30:
                    return "onNotificationBubbleChanged";
                case 31:
                    return "onGlobalActionsShown";
                case 32:
                    return "onGlobalActionsHidden";
                case 33:
                    return "shutdown";
                case 34:
                    return "reboot";
                case 35:
                    return "addTile";
                case 36:
                    return "remTile";
                case 37:
                    return "clickTile";
                case 38:
                    return "handleSystemKey";
                case 39:
                    return "showPinningEnterExitToast";
                case 40:
                    return "showPinningEscapeToast";
                case 41:
                    return "showBiometricDialog";
                case 42:
                    return "onBiometricAuthenticated";
                case 43:
                    return "onBiometricHelp";
                case 44:
                    return "onBiometricError";
                case 45:
                    return "hideBiometricDialog";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: com.android.internal.statusbar.NotificationVisibility} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v53, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v57, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v61, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v21 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: type inference failed for: r0v42 */
        /* JADX WARNING: type inference failed for: r0v68 */
        /* JADX WARNING: type inference failed for: r0v80 */
        /* JADX WARNING: type inference failed for: r0v81 */
        /* JADX WARNING: type inference failed for: r0v82 */
        /* JADX WARNING: type inference failed for: r0v83 */
        /* JADX WARNING: type inference failed for: r0v84 */
        /* JADX WARNING: type inference failed for: r0v85 */
        /* JADX WARNING: type inference failed for: r0v86 */
        /* JADX WARNING: type inference failed for: r0v87 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r23, android.os.Parcel r24, android.os.Parcel r25, int r26) throws android.os.RemoteException {
            /*
                r22 = this;
                r9 = r22
                r10 = r23
                r11 = r24
                r12 = r25
                java.lang.String r13 = "com.android.internal.statusbar.IStatusBarService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r14 = 1
                if (r10 == r0) goto L_0x046d
                r0 = 0
                r2 = 0
                switch(r10) {
                    case 1: goto L_0x0463;
                    case 2: goto L_0x0459;
                    case 3: goto L_0x044f;
                    case 4: goto L_0x0439;
                    case 5: goto L_0x041f;
                    case 6: goto L_0x0409;
                    case 7: goto L_0x03ef;
                    case 8: goto L_0x03d9;
                    case 9: goto L_0x03b3;
                    case 10: goto L_0x039c;
                    case 11: goto L_0x038e;
                    case 12: goto L_0x0365;
                    case 13: goto L_0x0357;
                    case 14: goto L_0x0338;
                    case 15: goto L_0x0321;
                    case 16: goto L_0x0317;
                    case 17: goto L_0x030d;
                    case 18: goto L_0x02ef;
                    case 19: goto L_0x02ae;
                    case 20: goto L_0x027a;
                    case 21: goto L_0x026c;
                    case 22: goto L_0x0226;
                    case 23: goto L_0x020c;
                    case 24: goto L_0x01e9;
                    case 25: goto L_0x01db;
                    case 26: goto L_0x01ae;
                    case 27: goto L_0x0179;
                    case 28: goto L_0x016b;
                    case 29: goto L_0x0151;
                    case 30: goto L_0x013a;
                    case 31: goto L_0x0130;
                    case 32: goto L_0x0126;
                    case 33: goto L_0x011c;
                    case 34: goto L_0x0109;
                    case 35: goto L_0x00ef;
                    case 36: goto L_0x00d5;
                    case 37: goto L_0x00bb;
                    case 38: goto L_0x00ad;
                    case 39: goto L_0x009a;
                    case 40: goto L_0x0090;
                    case 41: goto L_0x0057;
                    case 42: goto L_0x0040;
                    case 43: goto L_0x0032;
                    case 44: goto L_0x0024;
                    case 45: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r23, r24, r25, r26)
                return r0
            L_0x001a:
                r11.enforceInterface(r13)
                r22.hideBiometricDialog()
                r25.writeNoException()
                return r14
            L_0x0024:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                r9.onBiometricError(r0)
                r25.writeNoException()
                return r14
            L_0x0032:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                r9.onBiometricHelp(r0)
                r25.writeNoException()
                return r14
            L_0x0040:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x004b
                r2 = r14
            L_0x004b:
                r0 = r2
                java.lang.String r1 = r24.readString()
                r9.onBiometricAuthenticated(r0, r1)
                r25.writeNoException()
                return r14
            L_0x0057:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x006a
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0068:
                r1 = r0
                goto L_0x006b
            L_0x006a:
                goto L_0x0068
            L_0x006b:
                android.os.IBinder r0 = r24.readStrongBinder()
                android.hardware.biometrics.IBiometricServiceReceiverInternal r6 = android.hardware.biometrics.IBiometricServiceReceiverInternal.Stub.asInterface(r0)
                int r7 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x007f
                r4 = r14
                goto L_0x0080
            L_0x007f:
                r4 = r2
            L_0x0080:
                int r8 = r24.readInt()
                r0 = r22
                r2 = r6
                r3 = r7
                r5 = r8
                r0.showBiometricDialog(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x0090:
                r11.enforceInterface(r13)
                r22.showPinningEscapeToast()
                r25.writeNoException()
                return r14
            L_0x009a:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x00a5
                r2 = r14
            L_0x00a5:
                r0 = r2
                r9.showPinningEnterExitToast(r0)
                r25.writeNoException()
                return r14
            L_0x00ad:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                r9.handleSystemKey(r0)
                r25.writeNoException()
                return r14
            L_0x00bb:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x00cd
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x00ce
            L_0x00cd:
            L_0x00ce:
                r9.clickTile(r0)
                r25.writeNoException()
                return r14
            L_0x00d5:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x00e7
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x00e8
            L_0x00e7:
            L_0x00e8:
                r9.remTile(r0)
                r25.writeNoException()
                return r14
            L_0x00ef:
                r11.enforceInterface(r13)
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0101
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0102
            L_0x0101:
            L_0x0102:
                r9.addTile(r0)
                r25.writeNoException()
                return r14
            L_0x0109:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0114
                r2 = r14
            L_0x0114:
                r0 = r2
                r9.reboot(r0)
                r25.writeNoException()
                return r14
            L_0x011c:
                r11.enforceInterface(r13)
                r22.shutdown()
                r25.writeNoException()
                return r14
            L_0x0126:
                r11.enforceInterface(r13)
                r22.onGlobalActionsHidden()
                r25.writeNoException()
                return r14
            L_0x0130:
                r11.enforceInterface(r13)
                r22.onGlobalActionsShown()
                r25.writeNoException()
                return r14
            L_0x013a:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0149
                r2 = r14
            L_0x0149:
                r1 = r2
                r9.onNotificationBubbleChanged(r0, r1)
                r25.writeNoException()
                return r14
            L_0x0151:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                java.lang.String r3 = r24.readString()
                r9.setSystemUiVisibility(r0, r1, r2, r3)
                r25.writeNoException()
                return r14
            L_0x016b:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                r9.onNotificationSettingsViewed(r0)
                r25.writeNoException()
                return r14
            L_0x0179:
                r11.enforceInterface(r13)
                java.lang.String r6 = r24.readString()
                int r7 = r24.readInt()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0194
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            L_0x0192:
                r3 = r0
                goto L_0x0195
            L_0x0194:
                goto L_0x0192
            L_0x0195:
                int r8 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x01a1
                r5 = r14
                goto L_0x01a2
            L_0x01a1:
                r5 = r2
            L_0x01a2:
                r0 = r22
                r1 = r6
                r2 = r7
                r4 = r8
                r0.onNotificationSmartReplySent(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x01ae:
                r11.enforceInterface(r13)
                java.lang.String r6 = r24.readString()
                int r7 = r24.readInt()
                int r8 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x01c5
                r4 = r14
                goto L_0x01c6
            L_0x01c5:
                r4 = r2
            L_0x01c6:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x01ce
                r5 = r14
                goto L_0x01cf
            L_0x01ce:
                r5 = r2
            L_0x01cf:
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r0.onNotificationSmartSuggestionsAdded(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x01db:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                r9.onNotificationDirectReplied(r0)
                r25.writeNoException()
                return r14
            L_0x01e9:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x01f8
                r1 = r14
                goto L_0x01f9
            L_0x01f8:
                r1 = r2
            L_0x01f9:
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x0201
                r2 = r14
            L_0x0201:
                int r3 = r24.readInt()
                r9.onNotificationExpansionChanged(r0, r1, r2, r3)
                r25.writeNoException()
                return r14
            L_0x020c:
                r11.enforceInterface(r13)
                android.os.Parcelable$Creator<com.android.internal.statusbar.NotificationVisibility> r0 = com.android.internal.statusbar.NotificationVisibility.CREATOR
                java.lang.Object[] r0 = r11.createTypedArray(r0)
                com.android.internal.statusbar.NotificationVisibility[] r0 = (com.android.internal.statusbar.NotificationVisibility[]) r0
                android.os.Parcelable$Creator<com.android.internal.statusbar.NotificationVisibility> r1 = com.android.internal.statusbar.NotificationVisibility.CREATOR
                java.lang.Object[] r1 = r11.createTypedArray(r1)
                com.android.internal.statusbar.NotificationVisibility[] r1 = (com.android.internal.statusbar.NotificationVisibility[]) r1
                r9.onNotificationVisibilityChanged(r0, r1)
                r25.writeNoException()
                return r14
            L_0x0226:
                r11.enforceInterface(r13)
                java.lang.String r15 = r24.readString()
                java.lang.String r16 = r24.readString()
                int r17 = r24.readInt()
                int r18 = r24.readInt()
                java.lang.String r19 = r24.readString()
                int r20 = r24.readInt()
                int r21 = r24.readInt()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0255
                android.os.Parcelable$Creator<com.android.internal.statusbar.NotificationVisibility> r0 = com.android.internal.statusbar.NotificationVisibility.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                com.android.internal.statusbar.NotificationVisibility r0 = (com.android.internal.statusbar.NotificationVisibility) r0
            L_0x0253:
                r8 = r0
                goto L_0x0256
            L_0x0255:
                goto L_0x0253
            L_0x0256:
                r0 = r22
                r1 = r15
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r0.onNotificationClear(r1, r2, r3, r4, r5, r6, r7, r8)
                r25.writeNoException()
                return r14
            L_0x026c:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                r9.onClearAllNotifications(r0)
                r25.writeNoException()
                return r14
            L_0x027a:
                r11.enforceInterface(r13)
                java.lang.String r8 = r24.readString()
                java.lang.String r15 = r24.readString()
                int r16 = r24.readInt()
                int r17 = r24.readInt()
                int r18 = r24.readInt()
                java.lang.String r19 = r24.readString()
                int r20 = r24.readInt()
                r0 = r22
                r1 = r8
                r2 = r15
                r3 = r16
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r0.onNotificationError(r1, r2, r3, r4, r5, r6, r7)
                r25.writeNoException()
                return r14
            L_0x02ae:
                r11.enforceInterface(r13)
                java.lang.String r6 = r24.readString()
                int r7 = r24.readInt()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x02c9
                android.os.Parcelable$Creator<android.app.Notification$Action> r1 = android.app.Notification.Action.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.app.Notification$Action r1 = (android.app.Notification.Action) r1
                r3 = r1
                goto L_0x02ca
            L_0x02c9:
                r3 = r0
            L_0x02ca:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x02da
                android.os.Parcelable$Creator<com.android.internal.statusbar.NotificationVisibility> r0 = com.android.internal.statusbar.NotificationVisibility.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                com.android.internal.statusbar.NotificationVisibility r0 = (com.android.internal.statusbar.NotificationVisibility) r0
            L_0x02d8:
                r4 = r0
                goto L_0x02db
            L_0x02da:
                goto L_0x02d8
            L_0x02db:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x02e3
                r5 = r14
                goto L_0x02e4
            L_0x02e3:
                r5 = r2
            L_0x02e4:
                r0 = r22
                r1 = r6
                r2 = r7
                r0.onNotificationActionClick(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x02ef:
                r11.enforceInterface(r13)
                java.lang.String r1 = r24.readString()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0305
                android.os.Parcelable$Creator<com.android.internal.statusbar.NotificationVisibility> r0 = com.android.internal.statusbar.NotificationVisibility.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                com.android.internal.statusbar.NotificationVisibility r0 = (com.android.internal.statusbar.NotificationVisibility) r0
                goto L_0x0306
            L_0x0305:
            L_0x0306:
                r9.onNotificationClick(r1, r0)
                r25.writeNoException()
                return r14
            L_0x030d:
                r11.enforceInterface(r13)
                r22.clearNotificationEffects()
                r25.writeNoException()
                return r14
            L_0x0317:
                r11.enforceInterface(r13)
                r22.onPanelHidden()
                r25.writeNoException()
                return r14
            L_0x0321:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x032c
                r2 = r14
            L_0x032c:
                r0 = r2
                int r1 = r24.readInt()
                r9.onPanelRevealed(r0, r1)
                r25.writeNoException()
                return r14
            L_0x0338:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.statusbar.IStatusBar r0 = com.android.internal.statusbar.IStatusBar.Stub.asInterface(r0)
                com.android.internal.statusbar.RegisterStatusBarResult r1 = r9.registerStatusBar(r0)
                r25.writeNoException()
                if (r1 == 0) goto L_0x0353
                r12.writeInt(r14)
                r1.writeToParcel(r12, r14)
                goto L_0x0356
            L_0x0353:
                r12.writeInt(r2)
            L_0x0356:
                return r14
            L_0x0357:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                r9.expandSettingsPanel(r0)
                r25.writeNoException()
                return r14
            L_0x0365:
                r11.enforceInterface(r13)
                int r6 = r24.readInt()
                android.os.IBinder r7 = r24.readStrongBinder()
                int r8 = r24.readInt()
                int r15 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0380
                r5 = r14
                goto L_0x0381
            L_0x0380:
                r5 = r2
            L_0x0381:
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r15
                r0.setImeWindowStatus(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x038e:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                r9.removeIcon(r0)
                r25.writeNoException()
                return r14
            L_0x039c:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x03ab
                r2 = r14
            L_0x03ab:
                r1 = r2
                r9.setIconVisibility(r0, r1)
                r25.writeNoException()
                return r14
            L_0x03b3:
                r11.enforceInterface(r13)
                java.lang.String r6 = r24.readString()
                java.lang.String r7 = r24.readString()
                int r8 = r24.readInt()
                int r15 = r24.readInt()
                java.lang.String r16 = r24.readString()
                r0 = r22
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r15
                r5 = r16
                r0.setIcon(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x03d9:
                r11.enforceInterface(r13)
                android.os.IBinder r0 = r24.readStrongBinder()
                int r1 = r24.readInt()
                int[] r2 = r9.getDisableFlags(r0, r1)
                r25.writeNoException()
                r12.writeIntArray(r2)
                return r14
            L_0x03ef:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                java.lang.String r2 = r24.readString()
                int r3 = r24.readInt()
                r9.disable2ForUser(r0, r1, r2, r3)
                r25.writeNoException()
                return r14
            L_0x0409:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                java.lang.String r2 = r24.readString()
                r9.disable2(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x041f:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                java.lang.String r2 = r24.readString()
                int r3 = r24.readInt()
                r9.disableForUser(r0, r1, r2, r3)
                r25.writeNoException()
                return r14
            L_0x0439:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                java.lang.String r2 = r24.readString()
                r9.disable(r0, r1, r2)
                r25.writeNoException()
                return r14
            L_0x044f:
                r11.enforceInterface(r13)
                r22.togglePanel()
                r25.writeNoException()
                return r14
            L_0x0459:
                r11.enforceInterface(r13)
                r22.collapsePanels()
                r25.writeNoException()
                return r14
            L_0x0463:
                r11.enforceInterface(r13)
                r22.expandNotificationsPanel()
                r25.writeNoException()
                return r14
            L_0x046d:
                r12.writeString(r13)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.statusbar.IStatusBarService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IStatusBarService {
            public static IStatusBarService sDefaultImpl;
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

            public void expandNotificationsPanel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().expandNotificationsPanel();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void collapsePanels() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().collapsePanels();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void togglePanel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().togglePanel();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disable(int what, IBinder token, String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disable(what, token, pkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableForUser(what, token, pkg, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disable2(int what, IBinder token, String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disable2(what, token, pkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disable2ForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disable2ForUser(what, token, pkg, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getDisableFlags(IBinder token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisableFlags(token, userId);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setIcon(String slot, String iconPackage, int iconId, int iconLevel, String contentDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    _data.writeString(iconPackage);
                    _data.writeInt(iconId);
                    _data.writeInt(iconLevel);
                    _data.writeString(contentDescription);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setIcon(slot, iconPackage, iconId, iconLevel, contentDescription);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setIconVisibility(String slot, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setIconVisibility(slot, visible);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeIcon(String slot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeIcon(slot);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(token);
                    _data.writeInt(vis);
                    _data.writeInt(backDisposition);
                    _data.writeInt(showImeSwitcher);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setImeWindowStatus(displayId, token, vis, backDisposition, showImeSwitcher);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void expandSettingsPanel(String subPanel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subPanel);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().expandSettingsPanel(subPanel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RegisterStatusBarResult registerStatusBar(IStatusBar callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    RegisterStatusBarResult _result = null;
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerStatusBar(callbacks);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RegisterStatusBarResult.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPanelRevealed(boolean clearNotificationEffects, int numItems) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clearNotificationEffects);
                    _data.writeInt(numItems);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onPanelRevealed(clearNotificationEffects, numItems);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPanelHidden() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onPanelHidden();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearNotificationEffects() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearNotificationEffects();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationClick(String key, NotificationVisibility nv) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    if (nv != null) {
                        _data.writeInt(1);
                        nv.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationClick(key, nv);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationActionClick(String key, int actionIndex, Notification.Action action, NotificationVisibility nv, boolean generatedByAssistant) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(actionIndex);
                    if (action != null) {
                        _data.writeInt(1);
                        action.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (nv != null) {
                        _data.writeInt(1);
                        nv.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(generatedByAssistant);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationActionClick(key, actionIndex, action, nv, generatedByAssistant);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationError(String pkg, String tag, int id, int uid, int initialPid, String message, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(pkg);
                        try {
                            _data.writeString(tag);
                        } catch (Throwable th) {
                            th = th;
                            int i = id;
                            int i2 = uid;
                            int i3 = initialPid;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(id);
                            try {
                                _data.writeInt(uid);
                            } catch (Throwable th2) {
                                th = th2;
                                int i32 = initialPid;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeInt(initialPid);
                                _data.writeString(message);
                                _data.writeInt(userId);
                                if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onNotificationError(pkg, tag, id, uid, initialPid, message, userId);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = uid;
                            int i322 = initialPid;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str = tag;
                        int i4 = id;
                        int i222 = uid;
                        int i3222 = initialPid;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = pkg;
                    String str3 = tag;
                    int i42 = id;
                    int i2222 = uid;
                    int i32222 = initialPid;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void onClearAllNotifications(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onClearAllNotifications(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationClear(String pkg, String tag, int id, int userId, String key, int dismissalSurface, int dismissalSentiment, NotificationVisibility nv) throws RemoteException {
                NotificationVisibility notificationVisibility = nv;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(pkg);
                        try {
                            _data.writeString(tag);
                        } catch (Throwable th) {
                            th = th;
                            int i = id;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(id);
                            _data.writeInt(userId);
                            _data.writeString(key);
                            _data.writeInt(dismissalSurface);
                            _data.writeInt(dismissalSentiment);
                            if (notificationVisibility != null) {
                                _data.writeInt(1);
                                notificationVisibility.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onNotificationClear(pkg, tag, id, userId, key, dismissalSurface, dismissalSentiment, nv);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str = tag;
                        int i2 = id;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str2 = pkg;
                    String str3 = tag;
                    int i22 = id;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void onNotificationVisibilityChanged(NotificationVisibility[] newlyVisibleKeys, NotificationVisibility[] noLongerVisibleKeys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(newlyVisibleKeys, 0);
                    _data.writeTypedArray(noLongerVisibleKeys, 0);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationVisibilityChanged(newlyVisibleKeys, noLongerVisibleKeys);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationExpansionChanged(String key, boolean userAction, boolean expanded, int notificationLocation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(userAction);
                    _data.writeInt(expanded);
                    _data.writeInt(notificationLocation);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationExpansionChanged(key, userAction, expanded, notificationLocation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationDirectReplied(String key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationDirectReplied(key);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationSmartSuggestionsAdded(String key, int smartReplyCount, int smartActionCount, boolean generatedByAsssistant, boolean editBeforeSending) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(smartReplyCount);
                    _data.writeInt(smartActionCount);
                    _data.writeInt(generatedByAsssistant);
                    _data.writeInt(editBeforeSending);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationSmartSuggestionsAdded(key, smartReplyCount, smartActionCount, generatedByAsssistant, editBeforeSending);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationSmartReplySent(String key, int replyIndex, CharSequence reply, int notificationLocation, boolean modifiedBeforeSending) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(replyIndex);
                    if (reply != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(reply, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(notificationLocation);
                    _data.writeInt(modifiedBeforeSending);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationSmartReplySent(key, replyIndex, reply, notificationLocation, modifiedBeforeSending);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationSettingsViewed(String key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationSettingsViewed(key);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSystemUiVisibility(int displayId, int vis, int mask, String cause) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(vis);
                    _data.writeInt(mask);
                    _data.writeString(cause);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSystemUiVisibility(displayId, vis, mask, cause);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onNotificationBubbleChanged(String key, boolean isBubble) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(isBubble);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onNotificationBubbleChanged(key, isBubble);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onGlobalActionsShown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onGlobalActionsShown();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onGlobalActionsHidden() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onGlobalActionsHidden();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().shutdown();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reboot(boolean safeMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(safeMode);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reboot(safeMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addTile(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void remTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().remTile(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clickTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tile != null) {
                        _data.writeInt(1);
                        tile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clickTile(tile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void handleSystemKey(int key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleSystemKey(key);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showPinningEnterExitToast(boolean entering) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(entering);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showPinningEnterExitToast(entering);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showPinningEscapeToast() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showPinningEscapeToast();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal receiver, int type, boolean requireConfirmation, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
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
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showBiometricDialog(bundle, receiver, type, requireConfirmation, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onBiometricAuthenticated(boolean authenticated, String failureReason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(authenticated);
                    _data.writeString(failureReason);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBiometricAuthenticated(authenticated, failureReason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onBiometricHelp(String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBiometricHelp(message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onBiometricError(String error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(error);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onBiometricError(error);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void hideBiometricDialog() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().hideBiometricDialog();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IStatusBarService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IStatusBarService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
