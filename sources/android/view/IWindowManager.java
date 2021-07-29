package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.IAssistDataReceiver;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.IWindowSession;
import com.android.internal.os.IResultReceiver;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IShortcutService;

public interface IWindowManager extends IInterface {
    void addWindowToken(IBinder iBinder, int i, int i2) throws RemoteException;

    void clearForcedDisplayDensityForUser(int i, int i2) throws RemoteException;

    void clearForcedDisplaySize(int i) throws RemoteException;

    boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException;

    void closeSystemDialogs(String str) throws RemoteException;

    @UnsupportedAppUsage
    void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) throws RemoteException;

    @UnsupportedAppUsage
    boolean destroyInputConsumer(String str, int i) throws RemoteException;

    void disableKeyguard(IBinder iBinder, String str, int i) throws RemoteException;

    void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException;

    void dontOverrideDisplayInfo(int i) throws RemoteException;

    void enableScreenIfNeeded() throws RemoteException;

    @UnsupportedAppUsage
    void endProlongedAnimations() throws RemoteException;

    @UnsupportedAppUsage
    void executeAppTransition() throws RemoteException;

    void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException;

    void freezeDisplayRotation(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void freezeRotation(int i) throws RemoteException;

    @UnsupportedAppUsage
    float getAnimationScale(int i) throws RemoteException;

    @UnsupportedAppUsage
    float[] getAnimationScales() throws RemoteException;

    int getBaseDisplayDensity(int i) throws RemoteException;

    @UnsupportedAppUsage
    void getBaseDisplaySize(int i, Point point) throws RemoteException;

    float getCurrentAnimatorScale() throws RemoteException;

    Region getCurrentImeTouchRegion() throws RemoteException;

    int getDefaultDisplayRotation() throws RemoteException;

    @UnsupportedAppUsage
    int getDockedStackSide() throws RemoteException;

    @UnsupportedAppUsage
    int getInitialDisplayDensity(int i) throws RemoteException;

    @UnsupportedAppUsage
    void getInitialDisplaySize(int i, Point point) throws RemoteException;

    int getNavBarPosition(int i) throws RemoteException;

    int getPreferredOptionsPanelGravity(int i) throws RemoteException;

    int getRemoveContentMode(int i) throws RemoteException;

    @UnsupportedAppUsage
    void getStableInsets(int i, Rect rect) throws RemoteException;

    WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) throws RemoteException;

    int getWindowingMode(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean hasNavigationBar(int i) throws RemoteException;

    boolean injectInputAfterTransactionsApplied(InputEvent inputEvent, int i) throws RemoteException;

    boolean isDisplayRotationFrozen(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean isKeyguardLocked() throws RemoteException;

    @UnsupportedAppUsage
    boolean isKeyguardSecure(int i) throws RemoteException;

    boolean isRotationFrozen() throws RemoteException;

    @UnsupportedAppUsage
    boolean isSafeModeEnabled() throws RemoteException;

    boolean isViewServerRunning() throws RemoteException;

    boolean isWindowTraceEnabled() throws RemoteException;

    @UnsupportedAppUsage
    void lockNow(Bundle bundle) throws RemoteException;

    IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) throws RemoteException;

    @UnsupportedAppUsage
    void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) throws RemoteException;

    @UnsupportedAppUsage
    void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) throws RemoteException;

    void prepareAppTransition(int i, boolean z) throws RemoteException;

    void reenableKeyguard(IBinder iBinder, int i) throws RemoteException;

    void refreshScreenCaptureDisabled(int i) throws RemoteException;

    void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException;

    @UnsupportedAppUsage
    void registerDockedStackListener(IDockedStackListener iDockedStackListener) throws RemoteException;

    void registerPinnedStackListener(int i, IPinnedStackListener iPinnedStackListener) throws RemoteException;

    void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException;

    void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException;

    boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException;

    @UnsupportedAppUsage
    void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException;

    void removeWindowToken(IBinder iBinder, int i) throws RemoteException;

    void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException;

    void requestUserActivityNotification() throws RemoteException;

    Bitmap screenshotWallpaper() throws RemoteException;

    @UnsupportedAppUsage
    void setAnimationScale(int i, float f) throws RemoteException;

    @UnsupportedAppUsage
    void setAnimationScales(float[] fArr) throws RemoteException;

    void setDockedStackDividerTouchRegion(Rect rect) throws RemoteException;

    void setEventDispatching(boolean z) throws RemoteException;

    void setForceShowSystemBars(boolean z) throws RemoteException;

    void setForcedDisplayDensityForUser(int i, int i2, int i3) throws RemoteException;

    void setForcedDisplayScalingMode(int i, int i2) throws RemoteException;

    void setForcedDisplaySize(int i, int i2, int i3) throws RemoteException;

    void setForwardedInsets(int i, Insets insets) throws RemoteException;

    void setInTouchMode(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws RemoteException;

    void setOverscan(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void setPipVisibility(boolean z) throws RemoteException;

    void setRecentsVisibility(boolean z) throws RemoteException;

    void setRemoveContentMode(int i, int i2) throws RemoteException;

    void setResizeDimLayer(boolean z, int i, float f) throws RemoteException;

    @UnsupportedAppUsage
    void setShelfHeight(boolean z, int i) throws RemoteException;

    void setShouldShowIme(int i, boolean z) throws RemoteException;

    void setShouldShowSystemDecors(int i, boolean z) throws RemoteException;

    void setShouldShowWithInsecureKeyguard(int i, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setStrictModeVisualIndicatorPreference(String str) throws RemoteException;

    void setSwitchingUser(boolean z) throws RemoteException;

    void setWindowingMode(int i, int i2) throws RemoteException;

    boolean shouldShowIme(int i) throws RemoteException;

    boolean shouldShowSystemDecors(int i) throws RemoteException;

    boolean shouldShowWithInsecureKeyguard(int i) throws RemoteException;

    void showStrictModeViolation(boolean z) throws RemoteException;

    void startFreezingScreen(int i, int i2) throws RemoteException;

    boolean startViewServer(int i) throws RemoteException;

    void startWindowTrace() throws RemoteException;

    void statusBarVisibilityChanged(int i, int i2) throws RemoteException;

    void stopFreezingScreen() throws RemoteException;

    boolean stopViewServer() throws RemoteException;

    void stopWindowTrace() throws RemoteException;

    void syncInputTransactions() throws RemoteException;

    void thawDisplayRotation(int i) throws RemoteException;

    @UnsupportedAppUsage
    void thawRotation() throws RemoteException;

    void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException;

    void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException;

    void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException;

    void updateRotation(boolean z, boolean z2) throws RemoteException;

    int watchRotation(IRotationWatcher iRotationWatcher, int i) throws RemoteException;

    public static class Default implements IWindowManager {
        public boolean startViewServer(int port) throws RemoteException {
            return false;
        }

        public boolean stopViewServer() throws RemoteException {
            return false;
        }

        public boolean isViewServerRunning() throws RemoteException {
            return false;
        }

        public IWindowSession openSession(IWindowSessionCallback callback) throws RemoteException {
            return null;
        }

        public void getInitialDisplaySize(int displayId, Point size) throws RemoteException {
        }

        public void getBaseDisplaySize(int displayId, Point size) throws RemoteException {
        }

        public void setForcedDisplaySize(int displayId, int width, int height) throws RemoteException {
        }

        public void clearForcedDisplaySize(int displayId) throws RemoteException {
        }

        public int getInitialDisplayDensity(int displayId) throws RemoteException {
            return 0;
        }

        public int getBaseDisplayDensity(int displayId) throws RemoteException {
            return 0;
        }

        public void setForcedDisplayDensityForUser(int displayId, int density, int userId) throws RemoteException {
        }

        public void clearForcedDisplayDensityForUser(int displayId, int userId) throws RemoteException {
        }

        public void setForcedDisplayScalingMode(int displayId, int mode) throws RemoteException {
        }

        public void setOverscan(int displayId, int left, int top, int right, int bottom) throws RemoteException {
        }

        public void setEventDispatching(boolean enabled) throws RemoteException {
        }

        public void addWindowToken(IBinder token, int type, int displayId) throws RemoteException {
        }

        public void removeWindowToken(IBinder token, int displayId) throws RemoteException {
        }

        public void prepareAppTransition(int transit, boolean alwaysKeepCurrent) throws RemoteException {
        }

        public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture specsFuture, IRemoteCallback startedCallback, boolean scaleUp, int displayId) throws RemoteException {
        }

        public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int displayId) throws RemoteException {
        }

        public void executeAppTransition() throws RemoteException {
        }

        public void endProlongedAnimations() throws RemoteException {
        }

        public void startFreezingScreen(int exitAnim, int enterAnim) throws RemoteException {
        }

        public void stopFreezingScreen() throws RemoteException {
        }

        public void disableKeyguard(IBinder token, String tag, int userId) throws RemoteException {
        }

        public void reenableKeyguard(IBinder token, int userId) throws RemoteException {
        }

        public void exitKeyguardSecurely(IOnKeyguardExitResult callback) throws RemoteException {
        }

        public boolean isKeyguardLocked() throws RemoteException {
            return false;
        }

        public boolean isKeyguardSecure(int userId) throws RemoteException {
            return false;
        }

        public void dismissKeyguard(IKeyguardDismissCallback callback, CharSequence message) throws RemoteException {
        }

        public void setSwitchingUser(boolean switching) throws RemoteException {
        }

        public void closeSystemDialogs(String reason) throws RemoteException {
        }

        public float getAnimationScale(int which) throws RemoteException {
            return 0.0f;
        }

        public float[] getAnimationScales() throws RemoteException {
            return null;
        }

        public void setAnimationScale(int which, float scale) throws RemoteException {
        }

        public void setAnimationScales(float[] scales) throws RemoteException {
        }

        public float getCurrentAnimatorScale() throws RemoteException {
            return 0.0f;
        }

        public void setInTouchMode(boolean showFocus) throws RemoteException {
        }

        public void showStrictModeViolation(boolean on) throws RemoteException {
        }

        public void setStrictModeVisualIndicatorPreference(String enabled) throws RemoteException {
        }

        public void refreshScreenCaptureDisabled(int userId) throws RemoteException {
        }

        public void updateRotation(boolean alwaysSendConfiguration, boolean forceRelayout) throws RemoteException {
        }

        public int getDefaultDisplayRotation() throws RemoteException {
            return 0;
        }

        public int watchRotation(IRotationWatcher watcher, int displayId) throws RemoteException {
            return 0;
        }

        public void removeRotationWatcher(IRotationWatcher watcher) throws RemoteException {
        }

        public int getPreferredOptionsPanelGravity(int displayId) throws RemoteException {
            return 0;
        }

        public void freezeRotation(int rotation) throws RemoteException {
        }

        public void thawRotation() throws RemoteException {
        }

        public boolean isRotationFrozen() throws RemoteException {
            return false;
        }

        public void freezeDisplayRotation(int displayId, int rotation) throws RemoteException {
        }

        public void thawDisplayRotation(int displayId) throws RemoteException {
        }

        public boolean isDisplayRotationFrozen(int displayId) throws RemoteException {
            return false;
        }

        public Bitmap screenshotWallpaper() throws RemoteException {
            return null;
        }

        public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
            return false;
        }

        public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
        }

        public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener listener, int displayId) throws RemoteException {
        }

        public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener listener, int displayId) throws RemoteException {
        }

        public boolean requestAssistScreenshot(IAssistDataReceiver receiver) throws RemoteException {
            return false;
        }

        public void statusBarVisibilityChanged(int displayId, int visibility) throws RemoteException {
        }

        public void setForceShowSystemBars(boolean show) throws RemoteException {
        }

        public void setRecentsVisibility(boolean visible) throws RemoteException {
        }

        public void setPipVisibility(boolean visible) throws RemoteException {
        }

        public void setShelfHeight(boolean visible, int shelfHeight) throws RemoteException {
        }

        public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean enabled) throws RemoteException {
        }

        public boolean hasNavigationBar(int displayId) throws RemoteException {
            return false;
        }

        public int getNavBarPosition(int displayId) throws RemoteException {
            return 0;
        }

        public void lockNow(Bundle options) throws RemoteException {
        }

        public boolean isSafeModeEnabled() throws RemoteException {
            return false;
        }

        public void enableScreenIfNeeded() throws RemoteException {
        }

        public boolean clearWindowContentFrameStats(IBinder token) throws RemoteException {
            return false;
        }

        public WindowContentFrameStats getWindowContentFrameStats(IBinder token) throws RemoteException {
            return null;
        }

        public int getDockedStackSide() throws RemoteException {
            return 0;
        }

        public void setDockedStackDividerTouchRegion(Rect touchableRegion) throws RemoteException {
        }

        public void registerDockedStackListener(IDockedStackListener listener) throws RemoteException {
        }

        public void registerPinnedStackListener(int displayId, IPinnedStackListener listener) throws RemoteException {
        }

        public void setResizeDimLayer(boolean visible, int targetWindowingMode, float alpha) throws RemoteException {
        }

        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) throws RemoteException {
        }

        public void getStableInsets(int displayId, Rect outInsets) throws RemoteException {
        }

        public void setForwardedInsets(int displayId, Insets insets) throws RemoteException {
        }

        public void registerShortcutKey(long shortcutCode, IShortcutService keySubscriber) throws RemoteException {
        }

        public void createInputConsumer(IBinder token, String name, int displayId, InputChannel inputChannel) throws RemoteException {
        }

        public boolean destroyInputConsumer(String name, int displayId) throws RemoteException {
            return false;
        }

        public Region getCurrentImeTouchRegion() throws RemoteException {
            return null;
        }

        public void registerDisplayFoldListener(IDisplayFoldListener listener) throws RemoteException {
        }

        public void unregisterDisplayFoldListener(IDisplayFoldListener listener) throws RemoteException {
        }

        public void startWindowTrace() throws RemoteException {
        }

        public void stopWindowTrace() throws RemoteException {
        }

        public boolean isWindowTraceEnabled() throws RemoteException {
            return false;
        }

        public void requestUserActivityNotification() throws RemoteException {
        }

        public void dontOverrideDisplayInfo(int displayId) throws RemoteException {
        }

        public int getWindowingMode(int displayId) throws RemoteException {
            return 0;
        }

        public void setWindowingMode(int displayId, int mode) throws RemoteException {
        }

        public int getRemoveContentMode(int displayId) throws RemoteException {
            return 0;
        }

        public void setRemoveContentMode(int displayId, int mode) throws RemoteException {
        }

        public boolean shouldShowWithInsecureKeyguard(int displayId) throws RemoteException {
            return false;
        }

        public void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow) throws RemoteException {
        }

        public boolean shouldShowSystemDecors(int displayId) throws RemoteException {
            return false;
        }

        public void setShouldShowSystemDecors(int displayId, boolean shouldShow) throws RemoteException {
        }

        public boolean shouldShowIme(int displayId) throws RemoteException {
            return false;
        }

        public void setShouldShowIme(int displayId, boolean shouldShow) throws RemoteException {
        }

        public boolean injectInputAfterTransactionsApplied(InputEvent ev, int mode) throws RemoteException {
            return false;
        }

        public void syncInputTransactions() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWindowManager {
        private static final String DESCRIPTOR = "android.view.IWindowManager";
        static final int TRANSACTION_addWindowToken = 16;
        static final int TRANSACTION_clearForcedDisplayDensityForUser = 12;
        static final int TRANSACTION_clearForcedDisplaySize = 8;
        static final int TRANSACTION_clearWindowContentFrameStats = 70;
        static final int TRANSACTION_closeSystemDialogs = 32;
        static final int TRANSACTION_createInputConsumer = 81;
        static final int TRANSACTION_destroyInputConsumer = 82;
        static final int TRANSACTION_disableKeyguard = 25;
        static final int TRANSACTION_dismissKeyguard = 30;
        static final int TRANSACTION_dontOverrideDisplayInfo = 90;
        static final int TRANSACTION_enableScreenIfNeeded = 69;
        static final int TRANSACTION_endProlongedAnimations = 22;
        static final int TRANSACTION_executeAppTransition = 21;
        static final int TRANSACTION_exitKeyguardSecurely = 27;
        static final int TRANSACTION_freezeDisplayRotation = 50;
        static final int TRANSACTION_freezeRotation = 47;
        static final int TRANSACTION_getAnimationScale = 33;
        static final int TRANSACTION_getAnimationScales = 34;
        static final int TRANSACTION_getBaseDisplayDensity = 10;
        static final int TRANSACTION_getBaseDisplaySize = 6;
        static final int TRANSACTION_getCurrentAnimatorScale = 37;
        static final int TRANSACTION_getCurrentImeTouchRegion = 83;
        static final int TRANSACTION_getDefaultDisplayRotation = 43;
        static final int TRANSACTION_getDockedStackSide = 72;
        static final int TRANSACTION_getInitialDisplayDensity = 9;
        static final int TRANSACTION_getInitialDisplaySize = 5;
        static final int TRANSACTION_getNavBarPosition = 66;
        static final int TRANSACTION_getPreferredOptionsPanelGravity = 46;
        static final int TRANSACTION_getRemoveContentMode = 93;
        static final int TRANSACTION_getStableInsets = 78;
        static final int TRANSACTION_getWindowContentFrameStats = 71;
        static final int TRANSACTION_getWindowingMode = 91;
        static final int TRANSACTION_hasNavigationBar = 65;
        static final int TRANSACTION_injectInputAfterTransactionsApplied = 101;
        static final int TRANSACTION_isDisplayRotationFrozen = 52;
        static final int TRANSACTION_isKeyguardLocked = 28;
        static final int TRANSACTION_isKeyguardSecure = 29;
        static final int TRANSACTION_isRotationFrozen = 49;
        static final int TRANSACTION_isSafeModeEnabled = 68;
        static final int TRANSACTION_isViewServerRunning = 3;
        static final int TRANSACTION_isWindowTraceEnabled = 88;
        static final int TRANSACTION_lockNow = 67;
        static final int TRANSACTION_openSession = 4;
        static final int TRANSACTION_overridePendingAppTransitionMultiThumbFuture = 19;
        static final int TRANSACTION_overridePendingAppTransitionRemote = 20;
        static final int TRANSACTION_prepareAppTransition = 18;
        static final int TRANSACTION_reenableKeyguard = 26;
        static final int TRANSACTION_refreshScreenCaptureDisabled = 41;
        static final int TRANSACTION_registerDisplayFoldListener = 84;
        static final int TRANSACTION_registerDockedStackListener = 74;
        static final int TRANSACTION_registerPinnedStackListener = 75;
        static final int TRANSACTION_registerShortcutKey = 80;
        static final int TRANSACTION_registerSystemGestureExclusionListener = 56;
        static final int TRANSACTION_registerWallpaperVisibilityListener = 54;
        static final int TRANSACTION_removeRotationWatcher = 45;
        static final int TRANSACTION_removeWindowToken = 17;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 77;
        static final int TRANSACTION_requestAssistScreenshot = 58;
        static final int TRANSACTION_requestUserActivityNotification = 89;
        static final int TRANSACTION_screenshotWallpaper = 53;
        static final int TRANSACTION_setAnimationScale = 35;
        static final int TRANSACTION_setAnimationScales = 36;
        static final int TRANSACTION_setDockedStackDividerTouchRegion = 73;
        static final int TRANSACTION_setEventDispatching = 15;
        static final int TRANSACTION_setForceShowSystemBars = 60;
        static final int TRANSACTION_setForcedDisplayDensityForUser = 11;
        static final int TRANSACTION_setForcedDisplayScalingMode = 13;
        static final int TRANSACTION_setForcedDisplaySize = 7;
        static final int TRANSACTION_setForwardedInsets = 79;
        static final int TRANSACTION_setInTouchMode = 38;
        static final int TRANSACTION_setNavBarVirtualKeyHapticFeedbackEnabled = 64;
        static final int TRANSACTION_setOverscan = 14;
        static final int TRANSACTION_setPipVisibility = 62;
        static final int TRANSACTION_setRecentsVisibility = 61;
        static final int TRANSACTION_setRemoveContentMode = 94;
        static final int TRANSACTION_setResizeDimLayer = 76;
        static final int TRANSACTION_setShelfHeight = 63;
        static final int TRANSACTION_setShouldShowIme = 100;
        static final int TRANSACTION_setShouldShowSystemDecors = 98;
        static final int TRANSACTION_setShouldShowWithInsecureKeyguard = 96;
        static final int TRANSACTION_setStrictModeVisualIndicatorPreference = 40;
        static final int TRANSACTION_setSwitchingUser = 31;
        static final int TRANSACTION_setWindowingMode = 92;
        static final int TRANSACTION_shouldShowIme = 99;
        static final int TRANSACTION_shouldShowSystemDecors = 97;
        static final int TRANSACTION_shouldShowWithInsecureKeyguard = 95;
        static final int TRANSACTION_showStrictModeViolation = 39;
        static final int TRANSACTION_startFreezingScreen = 23;
        static final int TRANSACTION_startViewServer = 1;
        static final int TRANSACTION_startWindowTrace = 86;
        static final int TRANSACTION_statusBarVisibilityChanged = 59;
        static final int TRANSACTION_stopFreezingScreen = 24;
        static final int TRANSACTION_stopViewServer = 2;
        static final int TRANSACTION_stopWindowTrace = 87;
        static final int TRANSACTION_syncInputTransactions = 102;
        static final int TRANSACTION_thawDisplayRotation = 51;
        static final int TRANSACTION_thawRotation = 48;
        static final int TRANSACTION_unregisterDisplayFoldListener = 85;
        static final int TRANSACTION_unregisterSystemGestureExclusionListener = 57;
        static final int TRANSACTION_unregisterWallpaperVisibilityListener = 55;
        static final int TRANSACTION_updateRotation = 42;
        static final int TRANSACTION_watchRotation = 44;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindowManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWindowManager)) {
                return new Proxy(obj);
            }
            return (IWindowManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startViewServer";
                case 2:
                    return "stopViewServer";
                case 3:
                    return "isViewServerRunning";
                case 4:
                    return "openSession";
                case 5:
                    return "getInitialDisplaySize";
                case 6:
                    return "getBaseDisplaySize";
                case 7:
                    return "setForcedDisplaySize";
                case 8:
                    return "clearForcedDisplaySize";
                case 9:
                    return "getInitialDisplayDensity";
                case 10:
                    return "getBaseDisplayDensity";
                case 11:
                    return "setForcedDisplayDensityForUser";
                case 12:
                    return "clearForcedDisplayDensityForUser";
                case 13:
                    return "setForcedDisplayScalingMode";
                case 14:
                    return "setOverscan";
                case 15:
                    return "setEventDispatching";
                case 16:
                    return "addWindowToken";
                case 17:
                    return "removeWindowToken";
                case 18:
                    return "prepareAppTransition";
                case 19:
                    return "overridePendingAppTransitionMultiThumbFuture";
                case 20:
                    return "overridePendingAppTransitionRemote";
                case 21:
                    return "executeAppTransition";
                case 22:
                    return "endProlongedAnimations";
                case 23:
                    return "startFreezingScreen";
                case 24:
                    return "stopFreezingScreen";
                case 25:
                    return "disableKeyguard";
                case 26:
                    return "reenableKeyguard";
                case 27:
                    return "exitKeyguardSecurely";
                case 28:
                    return "isKeyguardLocked";
                case 29:
                    return "isKeyguardSecure";
                case 30:
                    return "dismissKeyguard";
                case 31:
                    return "setSwitchingUser";
                case 32:
                    return "closeSystemDialogs";
                case 33:
                    return "getAnimationScale";
                case 34:
                    return "getAnimationScales";
                case 35:
                    return "setAnimationScale";
                case 36:
                    return "setAnimationScales";
                case 37:
                    return "getCurrentAnimatorScale";
                case 38:
                    return "setInTouchMode";
                case 39:
                    return "showStrictModeViolation";
                case 40:
                    return "setStrictModeVisualIndicatorPreference";
                case 41:
                    return "refreshScreenCaptureDisabled";
                case 42:
                    return "updateRotation";
                case 43:
                    return "getDefaultDisplayRotation";
                case 44:
                    return "watchRotation";
                case 45:
                    return "removeRotationWatcher";
                case 46:
                    return "getPreferredOptionsPanelGravity";
                case 47:
                    return "freezeRotation";
                case 48:
                    return "thawRotation";
                case 49:
                    return "isRotationFrozen";
                case 50:
                    return "freezeDisplayRotation";
                case 51:
                    return "thawDisplayRotation";
                case 52:
                    return "isDisplayRotationFrozen";
                case 53:
                    return "screenshotWallpaper";
                case 54:
                    return "registerWallpaperVisibilityListener";
                case 55:
                    return "unregisterWallpaperVisibilityListener";
                case 56:
                    return "registerSystemGestureExclusionListener";
                case 57:
                    return "unregisterSystemGestureExclusionListener";
                case 58:
                    return "requestAssistScreenshot";
                case 59:
                    return "statusBarVisibilityChanged";
                case 60:
                    return "setForceShowSystemBars";
                case 61:
                    return "setRecentsVisibility";
                case 62:
                    return "setPipVisibility";
                case 63:
                    return "setShelfHeight";
                case 64:
                    return "setNavBarVirtualKeyHapticFeedbackEnabled";
                case 65:
                    return "hasNavigationBar";
                case 66:
                    return "getNavBarPosition";
                case 67:
                    return "lockNow";
                case 68:
                    return "isSafeModeEnabled";
                case 69:
                    return "enableScreenIfNeeded";
                case 70:
                    return "clearWindowContentFrameStats";
                case 71:
                    return "getWindowContentFrameStats";
                case 72:
                    return "getDockedStackSide";
                case 73:
                    return "setDockedStackDividerTouchRegion";
                case 74:
                    return "registerDockedStackListener";
                case 75:
                    return "registerPinnedStackListener";
                case 76:
                    return "setResizeDimLayer";
                case 77:
                    return "requestAppKeyboardShortcuts";
                case 78:
                    return "getStableInsets";
                case 79:
                    return "setForwardedInsets";
                case 80:
                    return "registerShortcutKey";
                case 81:
                    return "createInputConsumer";
                case 82:
                    return "destroyInputConsumer";
                case 83:
                    return "getCurrentImeTouchRegion";
                case 84:
                    return "registerDisplayFoldListener";
                case 85:
                    return "unregisterDisplayFoldListener";
                case 86:
                    return "startWindowTrace";
                case 87:
                    return "stopWindowTrace";
                case 88:
                    return "isWindowTraceEnabled";
                case 89:
                    return "requestUserActivityNotification";
                case 90:
                    return "dontOverrideDisplayInfo";
                case 91:
                    return "getWindowingMode";
                case 92:
                    return "setWindowingMode";
                case 93:
                    return "getRemoveContentMode";
                case 94:
                    return "setRemoveContentMode";
                case 95:
                    return "shouldShowWithInsecureKeyguard";
                case 96:
                    return "setShouldShowWithInsecureKeyguard";
                case 97:
                    return "shouldShowSystemDecors";
                case 98:
                    return "setShouldShowSystemDecors";
                case 99:
                    return "shouldShowIme";
                case 100:
                    return "setShouldShowIme";
                case 101:
                    return "injectInputAfterTransactionsApplied";
                case 102:
                    return "syncInputTransactions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.view.RemoteAnimationAdapter} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v92, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v100, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v112, resolved type: android.graphics.Insets} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v35, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v136, types: [android.view.InputEvent] */
        /* JADX WARNING: type inference failed for: r0v141 */
        /* JADX WARNING: type inference failed for: r0v142 */
        /* JADX WARNING: type inference failed for: r0v143 */
        /* JADX WARNING: type inference failed for: r0v144 */
        /* JADX WARNING: type inference failed for: r0v145 */
        /* JADX WARNING: type inference failed for: r0v146 */
        /* JADX WARNING: type inference failed for: r0v147 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.view.IWindowManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x07cf
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x07bd;
                    case 2: goto L_0x07af;
                    case 3: goto L_0x07a1;
                    case 4: goto L_0x0784;
                    case 5: goto L_0x076a;
                    case 6: goto L_0x0750;
                    case 7: goto L_0x073a;
                    case 8: goto L_0x072c;
                    case 9: goto L_0x071a;
                    case 10: goto L_0x0708;
                    case 11: goto L_0x06f2;
                    case 12: goto L_0x06e0;
                    case 13: goto L_0x06ce;
                    case 14: goto L_0x06a8;
                    case 15: goto L_0x0695;
                    case 16: goto L_0x067f;
                    case 17: goto L_0x066d;
                    case 18: goto L_0x0657;
                    case 19: goto L_0x0631;
                    case 20: goto L_0x0613;
                    case 21: goto L_0x0609;
                    case 22: goto L_0x05ff;
                    case 23: goto L_0x05ed;
                    case 24: goto L_0x05e3;
                    case 25: goto L_0x05cd;
                    case 26: goto L_0x05bb;
                    case 27: goto L_0x05a9;
                    case 28: goto L_0x059b;
                    case 29: goto L_0x0589;
                    case 30: goto L_0x0567;
                    case 31: goto L_0x0554;
                    case 32: goto L_0x0546;
                    case 33: goto L_0x0534;
                    case 34: goto L_0x0526;
                    case 35: goto L_0x0514;
                    case 36: goto L_0x0506;
                    case 37: goto L_0x04f8;
                    case 38: goto L_0x04e5;
                    case 39: goto L_0x04d2;
                    case 40: goto L_0x04c4;
                    case 41: goto L_0x04b6;
                    case 42: goto L_0x049b;
                    case 43: goto L_0x048d;
                    case 44: goto L_0x0473;
                    case 45: goto L_0x0461;
                    case 46: goto L_0x044f;
                    case 47: goto L_0x0441;
                    case 48: goto L_0x0437;
                    case 49: goto L_0x0429;
                    case 50: goto L_0x0417;
                    case 51: goto L_0x0409;
                    case 52: goto L_0x03f7;
                    case 53: goto L_0x03e0;
                    case 54: goto L_0x03c6;
                    case 55: goto L_0x03b0;
                    case 56: goto L_0x039a;
                    case 57: goto L_0x0384;
                    case 58: goto L_0x036e;
                    case 59: goto L_0x035f;
                    case 60: goto L_0x034f;
                    case 61: goto L_0x033f;
                    case 62: goto L_0x032f;
                    case 63: goto L_0x0318;
                    case 64: goto L_0x0305;
                    case 65: goto L_0x02f3;
                    case 66: goto L_0x02e1;
                    case 67: goto L_0x02c7;
                    case 68: goto L_0x02b9;
                    case 69: goto L_0x02af;
                    case 70: goto L_0x029d;
                    case 71: goto L_0x0282;
                    case 72: goto L_0x0274;
                    case 73: goto L_0x025a;
                    case 74: goto L_0x0248;
                    case 75: goto L_0x0232;
                    case 76: goto L_0x0217;
                    case 77: goto L_0x0201;
                    case 78: goto L_0x01e7;
                    case 79: goto L_0x01c9;
                    case 80: goto L_0x01b3;
                    case 81: goto L_0x0191;
                    case 82: goto L_0x017b;
                    case 83: goto L_0x0164;
                    case 84: goto L_0x0152;
                    case 85: goto L_0x0140;
                    case 86: goto L_0x0136;
                    case 87: goto L_0x012c;
                    case 88: goto L_0x011e;
                    case 89: goto L_0x0114;
                    case 90: goto L_0x0106;
                    case 91: goto L_0x00f4;
                    case 92: goto L_0x00e2;
                    case 93: goto L_0x00d0;
                    case 94: goto L_0x00be;
                    case 95: goto L_0x00ac;
                    case 96: goto L_0x0096;
                    case 97: goto L_0x0084;
                    case 98: goto L_0x006e;
                    case 99: goto L_0x005c;
                    case 100: goto L_0x0046;
                    case 101: goto L_0x0024;
                    case 102: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                r17.syncInputTransactions()
                r20.writeNoException()
                return r11
            L_0x0024:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0036
                android.os.Parcelable$Creator<android.view.InputEvent> r0 = android.view.InputEvent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.InputEvent r0 = (android.view.InputEvent) r0
                goto L_0x0037
            L_0x0036:
            L_0x0037:
                int r1 = r19.readInt()
                boolean r2 = r6.injectInputAfterTransactionsApplied(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0046:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0055
                r1 = r11
            L_0x0055:
                r6.setShouldShowIme(r0, r1)
                r20.writeNoException()
                return r11
            L_0x005c:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.shouldShowIme(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x006e:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x007d
                r1 = r11
            L_0x007d:
                r6.setShouldShowSystemDecors(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0084:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.shouldShowSystemDecors(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0096:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x00a5
                r1 = r11
            L_0x00a5:
                r6.setShouldShowWithInsecureKeyguard(r0, r1)
                r20.writeNoException()
                return r11
            L_0x00ac:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.shouldShowWithInsecureKeyguard(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00be:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.setRemoveContentMode(r0, r1)
                r20.writeNoException()
                return r11
            L_0x00d0:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getRemoveContentMode(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00e2:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.setWindowingMode(r0, r1)
                r20.writeNoException()
                return r11
            L_0x00f4:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getWindowingMode(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0106:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.dontOverrideDisplayInfo(r0)
                r20.writeNoException()
                return r11
            L_0x0114:
                r8.enforceInterface(r10)
                r17.requestUserActivityNotification()
                r20.writeNoException()
                return r11
            L_0x011e:
                r8.enforceInterface(r10)
                boolean r0 = r17.isWindowTraceEnabled()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x012c:
                r8.enforceInterface(r10)
                r17.stopWindowTrace()
                r20.writeNoException()
                return r11
            L_0x0136:
                r8.enforceInterface(r10)
                r17.startWindowTrace()
                r20.writeNoException()
                return r11
            L_0x0140:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IDisplayFoldListener r0 = android.view.IDisplayFoldListener.Stub.asInterface(r0)
                r6.unregisterDisplayFoldListener(r0)
                r20.writeNoException()
                return r11
            L_0x0152:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IDisplayFoldListener r0 = android.view.IDisplayFoldListener.Stub.asInterface(r0)
                r6.registerDisplayFoldListener(r0)
                r20.writeNoException()
                return r11
            L_0x0164:
                r8.enforceInterface(r10)
                android.graphics.Region r0 = r17.getCurrentImeTouchRegion()
                r20.writeNoException()
                if (r0 == 0) goto L_0x0177
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x017a
            L_0x0177:
                r9.writeInt(r1)
            L_0x017a:
                return r11
            L_0x017b:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                boolean r2 = r6.destroyInputConsumer(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0191:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                android.view.InputChannel r3 = new android.view.InputChannel
                r3.<init>()
                r6.createInputConsumer(r0, r1, r2, r3)
                r20.writeNoException()
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                return r11
            L_0x01b3:
                r8.enforceInterface(r10)
                long r0 = r19.readLong()
                android.os.IBinder r2 = r19.readStrongBinder()
                com.android.internal.policy.IShortcutService r2 = com.android.internal.policy.IShortcutService.Stub.asInterface(r2)
                r6.registerShortcutKey(r0, r2)
                r20.writeNoException()
                return r11
            L_0x01c9:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x01df
                android.os.Parcelable$Creator<android.graphics.Insets> r0 = android.graphics.Insets.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.graphics.Insets r0 = (android.graphics.Insets) r0
                goto L_0x01e0
            L_0x01df:
            L_0x01e0:
                r6.setForwardedInsets(r1, r0)
                r20.writeNoException()
                return r11
            L_0x01e7:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.graphics.Rect r1 = new android.graphics.Rect
                r1.<init>()
                r6.getStableInsets(r0, r1)
                r20.writeNoException()
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                return r11
            L_0x0201:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r6.requestAppKeyboardShortcuts(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0217:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0222
                r1 = r11
            L_0x0222:
                r0 = r1
                int r1 = r19.readInt()
                float r2 = r19.readFloat()
                r6.setResizeDimLayer(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0232:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.view.IPinnedStackListener r1 = android.view.IPinnedStackListener.Stub.asInterface(r1)
                r6.registerPinnedStackListener(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0248:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IDockedStackListener r0 = android.view.IDockedStackListener.Stub.asInterface(r0)
                r6.registerDockedStackListener(r0)
                r20.writeNoException()
                return r11
            L_0x025a:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x026c
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x026d
            L_0x026c:
            L_0x026d:
                r6.setDockedStackDividerTouchRegion(r0)
                r20.writeNoException()
                return r11
            L_0x0274:
                r8.enforceInterface(r10)
                int r0 = r17.getDockedStackSide()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0282:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.WindowContentFrameStats r2 = r6.getWindowContentFrameStats(r0)
                r20.writeNoException()
                if (r2 == 0) goto L_0x0299
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x029c
            L_0x0299:
                r9.writeInt(r1)
            L_0x029c:
                return r11
            L_0x029d:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                boolean r1 = r6.clearWindowContentFrameStats(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x02af:
                r8.enforceInterface(r10)
                r17.enableScreenIfNeeded()
                r20.writeNoException()
                return r11
            L_0x02b9:
                r8.enforceInterface(r10)
                boolean r0 = r17.isSafeModeEnabled()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x02c7:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x02d9
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x02da
            L_0x02d9:
            L_0x02da:
                r6.lockNow(r0)
                r20.writeNoException()
                return r11
            L_0x02e1:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getNavBarPosition(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x02f3:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.hasNavigationBar(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0305:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0310
                r1 = r11
            L_0x0310:
                r0 = r1
                r6.setNavBarVirtualKeyHapticFeedbackEnabled(r0)
                r20.writeNoException()
                return r11
            L_0x0318:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0323
                r1 = r11
            L_0x0323:
                r0 = r1
                int r1 = r19.readInt()
                r6.setShelfHeight(r0, r1)
                r20.writeNoException()
                return r11
            L_0x032f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x033a
                r1 = r11
            L_0x033a:
                r0 = r1
                r6.setPipVisibility(r0)
                return r11
            L_0x033f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x034a
                r1 = r11
            L_0x034a:
                r0 = r1
                r6.setRecentsVisibility(r0)
                return r11
            L_0x034f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x035a
                r1 = r11
            L_0x035a:
                r0 = r1
                r6.setForceShowSystemBars(r0)
                return r11
            L_0x035f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.statusBarVisibilityChanged(r0, r1)
                return r11
            L_0x036e:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.app.IAssistDataReceiver r0 = android.app.IAssistDataReceiver.Stub.asInterface(r0)
                boolean r1 = r6.requestAssistScreenshot(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0384:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.ISystemGestureExclusionListener r0 = android.view.ISystemGestureExclusionListener.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r6.unregisterSystemGestureExclusionListener(r0, r1)
                r20.writeNoException()
                return r11
            L_0x039a:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.ISystemGestureExclusionListener r0 = android.view.ISystemGestureExclusionListener.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r6.registerSystemGestureExclusionListener(r0, r1)
                r20.writeNoException()
                return r11
            L_0x03b0:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IWallpaperVisibilityListener r0 = android.view.IWallpaperVisibilityListener.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r6.unregisterWallpaperVisibilityListener(r0, r1)
                r20.writeNoException()
                return r11
            L_0x03c6:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IWallpaperVisibilityListener r0 = android.view.IWallpaperVisibilityListener.Stub.asInterface(r0)
                int r1 = r19.readInt()
                boolean r2 = r6.registerWallpaperVisibilityListener(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x03e0:
                r8.enforceInterface(r10)
                android.graphics.Bitmap r0 = r17.screenshotWallpaper()
                r20.writeNoException()
                if (r0 == 0) goto L_0x03f3
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x03f6
            L_0x03f3:
                r9.writeInt(r1)
            L_0x03f6:
                return r11
            L_0x03f7:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.isDisplayRotationFrozen(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0409:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.thawDisplayRotation(r0)
                r20.writeNoException()
                return r11
            L_0x0417:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.freezeDisplayRotation(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0429:
                r8.enforceInterface(r10)
                boolean r0 = r17.isRotationFrozen()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0437:
                r8.enforceInterface(r10)
                r17.thawRotation()
                r20.writeNoException()
                return r11
            L_0x0441:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.freezeRotation(r0)
                r20.writeNoException()
                return r11
            L_0x044f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getPreferredOptionsPanelGravity(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0461:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IRotationWatcher r0 = android.view.IRotationWatcher.Stub.asInterface(r0)
                r6.removeRotationWatcher(r0)
                r20.writeNoException()
                return r11
            L_0x0473:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IRotationWatcher r0 = android.view.IRotationWatcher.Stub.asInterface(r0)
                int r1 = r19.readInt()
                int r2 = r6.watchRotation(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x048d:
                r8.enforceInterface(r10)
                int r0 = r17.getDefaultDisplayRotation()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x049b:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04a6
                r0 = r11
                goto L_0x04a7
            L_0x04a6:
                r0 = r1
            L_0x04a7:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x04af
                r1 = r11
            L_0x04af:
                r6.updateRotation(r0, r1)
                r20.writeNoException()
                return r11
            L_0x04b6:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.refreshScreenCaptureDisabled(r0)
                r20.writeNoException()
                return r11
            L_0x04c4:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                r6.setStrictModeVisualIndicatorPreference(r0)
                r20.writeNoException()
                return r11
            L_0x04d2:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04dd
                r1 = r11
            L_0x04dd:
                r0 = r1
                r6.showStrictModeViolation(r0)
                r20.writeNoException()
                return r11
            L_0x04e5:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04f0
                r1 = r11
            L_0x04f0:
                r0 = r1
                r6.setInTouchMode(r0)
                r20.writeNoException()
                return r11
            L_0x04f8:
                r8.enforceInterface(r10)
                float r0 = r17.getCurrentAnimatorScale()
                r20.writeNoException()
                r9.writeFloat(r0)
                return r11
            L_0x0506:
                r8.enforceInterface(r10)
                float[] r0 = r19.createFloatArray()
                r6.setAnimationScales(r0)
                r20.writeNoException()
                return r11
            L_0x0514:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                float r1 = r19.readFloat()
                r6.setAnimationScale(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0526:
                r8.enforceInterface(r10)
                float[] r0 = r17.getAnimationScales()
                r20.writeNoException()
                r9.writeFloatArray(r0)
                return r11
            L_0x0534:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                float r1 = r6.getAnimationScale(r0)
                r20.writeNoException()
                r9.writeFloat(r1)
                return r11
            L_0x0546:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                r6.closeSystemDialogs(r0)
                r20.writeNoException()
                return r11
            L_0x0554:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x055f
                r1 = r11
            L_0x055f:
                r0 = r1
                r6.setSwitchingUser(r0)
                r20.writeNoException()
                return r11
            L_0x0567:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                com.android.internal.policy.IKeyguardDismissCallback r1 = com.android.internal.policy.IKeyguardDismissCallback.Stub.asInterface(r1)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0581
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x0582
            L_0x0581:
            L_0x0582:
                r6.dismissKeyguard(r1, r0)
                r20.writeNoException()
                return r11
            L_0x0589:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.isKeyguardSecure(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x059b:
                r8.enforceInterface(r10)
                boolean r0 = r17.isKeyguardLocked()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x05a9:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IOnKeyguardExitResult r0 = android.view.IOnKeyguardExitResult.Stub.asInterface(r0)
                r6.exitKeyguardSecurely(r0)
                r20.writeNoException()
                return r11
            L_0x05bb:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.reenableKeyguard(r0, r1)
                r20.writeNoException()
                return r11
            L_0x05cd:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                r6.disableKeyguard(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x05e3:
                r8.enforceInterface(r10)
                r17.stopFreezingScreen()
                r20.writeNoException()
                return r11
            L_0x05ed:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.startFreezingScreen(r0, r1)
                r20.writeNoException()
                return r11
            L_0x05ff:
                r8.enforceInterface(r10)
                r17.endProlongedAnimations()
                r20.writeNoException()
                return r11
            L_0x0609:
                r8.enforceInterface(r10)
                r17.executeAppTransition()
                r20.writeNoException()
                return r11
            L_0x0613:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0625
                android.os.Parcelable$Creator<android.view.RemoteAnimationAdapter> r0 = android.view.RemoteAnimationAdapter.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.RemoteAnimationAdapter r0 = (android.view.RemoteAnimationAdapter) r0
                goto L_0x0626
            L_0x0625:
            L_0x0626:
                int r1 = r19.readInt()
                r6.overridePendingAppTransitionRemote(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0631:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.view.IAppTransitionAnimationSpecsFuture r0 = android.view.IAppTransitionAnimationSpecsFuture.Stub.asInterface(r0)
                android.os.IBinder r2 = r19.readStrongBinder()
                android.os.IRemoteCallback r2 = android.os.IRemoteCallback.Stub.asInterface(r2)
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x064c
                r1 = r11
            L_0x064c:
                int r3 = r19.readInt()
                r6.overridePendingAppTransitionMultiThumbFuture(r0, r2, r1, r3)
                r20.writeNoException()
                return r11
            L_0x0657:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0666
                r1 = r11
            L_0x0666:
                r6.prepareAppTransition(r0, r1)
                r20.writeNoException()
                return r11
            L_0x066d:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.removeWindowToken(r0, r1)
                r20.writeNoException()
                return r11
            L_0x067f:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                r6.addWindowToken(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0695:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x06a0
                r1 = r11
            L_0x06a0:
                r0 = r1
                r6.setEventDispatching(r0)
                r20.writeNoException()
                return r11
            L_0x06a8:
                r8.enforceInterface(r10)
                int r12 = r19.readInt()
                int r13 = r19.readInt()
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.setOverscan(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x06ce:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.setForcedDisplayScalingMode(r0, r1)
                r20.writeNoException()
                return r11
            L_0x06e0:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                r6.clearForcedDisplayDensityForUser(r0, r1)
                r20.writeNoException()
                return r11
            L_0x06f2:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                r6.setForcedDisplayDensityForUser(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0708:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getBaseDisplayDensity(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x071a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.getInitialDisplayDensity(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x072c:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.clearForcedDisplaySize(r0)
                r20.writeNoException()
                return r11
            L_0x073a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                r6.setForcedDisplaySize(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0750:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.graphics.Point r1 = new android.graphics.Point
                r1.<init>()
                r6.getBaseDisplaySize(r0, r1)
                r20.writeNoException()
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                return r11
            L_0x076a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.graphics.Point r1 = new android.graphics.Point
                r1.<init>()
                r6.getInitialDisplaySize(r0, r1)
                r20.writeNoException()
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                return r11
            L_0x0784:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                android.view.IWindowSessionCallback r1 = android.view.IWindowSessionCallback.Stub.asInterface(r1)
                android.view.IWindowSession r2 = r6.openSession(r1)
                r20.writeNoException()
                if (r2 == 0) goto L_0x079d
                android.os.IBinder r0 = r2.asBinder()
            L_0x079d:
                r9.writeStrongBinder(r0)
                return r11
            L_0x07a1:
                r8.enforceInterface(r10)
                boolean r0 = r17.isViewServerRunning()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x07af:
                r8.enforceInterface(r10)
                boolean r0 = r17.stopViewServer()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x07bd:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.startViewServer(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x07cf:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.IWindowManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWindowManager {
            public static IWindowManager sDefaultImpl;
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

            public boolean startViewServer(int port) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(port);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startViewServer(port);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean stopViewServer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopViewServer();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isViewServerRunning() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isViewServerRunning();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IWindowSession openSession(IWindowSessionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openSession(callback);
                    }
                    _reply.readException();
                    IWindowSession _result = IWindowSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getInitialDisplaySize(int displayId, Point size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            size.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getInitialDisplaySize(displayId, size);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getBaseDisplaySize(int displayId, Point size) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            size.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getBaseDisplaySize(displayId, size);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setForcedDisplaySize(int displayId, int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setForcedDisplaySize(displayId, width, height);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearForcedDisplaySize(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearForcedDisplaySize(displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getInitialDisplayDensity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInitialDisplayDensity(displayId);
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

            public int getBaseDisplayDensity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBaseDisplayDensity(displayId);
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

            public void setForcedDisplayDensityForUser(int displayId, int density, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(density);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setForcedDisplayDensityForUser(displayId, density, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearForcedDisplayDensityForUser(int displayId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearForcedDisplayDensityForUser(displayId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setForcedDisplayScalingMode(int displayId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setForcedDisplayScalingMode(displayId, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOverscan(int displayId, int left, int top, int right, int bottom) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOverscan(displayId, left, top, right, bottom);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setEventDispatching(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setEventDispatching(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addWindowToken(IBinder token, int type, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(type);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addWindowToken(token, type, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeWindowToken(IBinder token, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeWindowToken(token, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareAppTransition(int transit, boolean alwaysKeepCurrent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(transit);
                    _data.writeInt(alwaysKeepCurrent);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepareAppTransition(transit, alwaysKeepCurrent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture specsFuture, IRemoteCallback startedCallback, boolean scaleUp, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(specsFuture != null ? specsFuture.asBinder() : null);
                    if (startedCallback != null) {
                        iBinder = startedCallback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(scaleUp);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().overridePendingAppTransitionMultiThumbFuture(specsFuture, startedCallback, scaleUp, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteAnimationAdapter != null) {
                        _data.writeInt(1);
                        remoteAnimationAdapter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().overridePendingAppTransitionRemote(remoteAnimationAdapter, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void executeAppTransition() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().executeAppTransition();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void endProlongedAnimations() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().endProlongedAnimations();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startFreezingScreen(int exitAnim, int enterAnim) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(exitAnim);
                    _data.writeInt(enterAnim);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startFreezingScreen(exitAnim, enterAnim);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopFreezingScreen() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopFreezingScreen();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableKeyguard(IBinder token, String tag, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(tag);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableKeyguard(token, tag, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reenableKeyguard(IBinder token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reenableKeyguard(token, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void exitKeyguardSecurely(IOnKeyguardExitResult callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().exitKeyguardSecurely(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isKeyguardLocked() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isKeyguardLocked();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isKeyguardSecure(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isKeyguardSecure(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dismissKeyguard(IKeyguardDismissCallback callback, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dismissKeyguard(callback, message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSwitchingUser(boolean switching) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(switching);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSwitchingUser(switching);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeSystemDialogs(reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getAnimationScale(int which) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAnimationScale(which);
                    }
                    _reply.readException();
                    float _result = _reply.readFloat();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float[] getAnimationScales() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAnimationScales();
                    }
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAnimationScale(int which, float scale) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeFloat(scale);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAnimationScale(which, scale);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAnimationScales(float[] scales) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloatArray(scales);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAnimationScales(scales);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getCurrentAnimatorScale() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAnimatorScale();
                    }
                    _reply.readException();
                    float _result = _reply.readFloat();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInTouchMode(boolean showFocus) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showFocus);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInTouchMode(showFocus);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void showStrictModeViolation(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showStrictModeViolation(on);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setStrictModeVisualIndicatorPreference(String enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(enabled);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setStrictModeVisualIndicatorPreference(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void refreshScreenCaptureDisabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().refreshScreenCaptureDisabled(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateRotation(boolean alwaysSendConfiguration, boolean forceRelayout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(alwaysSendConfiguration);
                    _data.writeInt(forceRelayout);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateRotation(alwaysSendConfiguration, forceRelayout);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDefaultDisplayRotation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultDisplayRotation();
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

            public int watchRotation(IRotationWatcher watcher, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().watchRotation(watcher, displayId);
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

            public void removeRotationWatcher(IRotationWatcher watcher) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(watcher != null ? watcher.asBinder() : null);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeRotationWatcher(watcher);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPreferredOptionsPanelGravity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreferredOptionsPanelGravity(displayId);
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

            public void freezeRotation(int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().freezeRotation(rotation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void thawRotation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().thawRotation();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRotationFrozen() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRotationFrozen();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void freezeDisplayRotation(int displayId, int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(rotation);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().freezeDisplayRotation(displayId, rotation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void thawDisplayRotation(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().thawDisplayRotation(displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isDisplayRotationFrozen(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDisplayRotationFrozen(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bitmap screenshotWallpaper() throws RemoteException {
                Bitmap _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().screenshotWallpaper();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bitmap.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bitmap _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerWallpaperVisibilityListener(listener, displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterWallpaperVisibilityListener(listener, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerSystemGestureExclusionListener(listener, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterSystemGestureExclusionListener(listener, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean requestAssistScreenshot(IAssistDataReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestAssistScreenshot(receiver);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void statusBarVisibilityChanged(int displayId, int visibility) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(visibility);
                    if (this.mRemote.transact(59, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().statusBarVisibilityChanged(displayId, visibility);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setForceShowSystemBars(boolean show) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(show);
                    if (this.mRemote.transact(60, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setForceShowSystemBars(show);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRecentsVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(61, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRecentsVisibility(visible);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPipVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(62, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPipVisibility(visible);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setShelfHeight(boolean visible, int shelfHeight) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    _data.writeInt(shelfHeight);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShelfHeight(visible, shelfHeight);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNavBarVirtualKeyHapticFeedbackEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasNavigationBar(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(65, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasNavigationBar(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNavBarPosition(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNavBarPosition(displayId);
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

            public void lockNow(Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().lockNow(options);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSafeModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSafeModeEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableScreenIfNeeded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableScreenIfNeeded();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean clearWindowContentFrameStats(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearWindowContentFrameStats(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public WindowContentFrameStats getWindowContentFrameStats(IBinder token) throws RemoteException {
                WindowContentFrameStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindowContentFrameStats(token);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WindowContentFrameStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WindowContentFrameStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDockedStackSide() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDockedStackSide();
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

            public void setDockedStackDividerTouchRegion(Rect touchableRegion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (touchableRegion != null) {
                        _data.writeInt(1);
                        touchableRegion.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDockedStackDividerTouchRegion(touchableRegion);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerDockedStackListener(IDockedStackListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(74, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerDockedStackListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerPinnedStackListener(int displayId, IPinnedStackListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(75, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerPinnedStackListener(displayId, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setResizeDimLayer(boolean visible, int targetWindowingMode, float alpha) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    _data.writeInt(targetWindowingMode);
                    _data.writeFloat(alpha);
                    if (this.mRemote.transact(76, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setResizeDimLayer(visible, targetWindowingMode, alpha);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    _data.writeInt(deviceId);
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestAppKeyboardShortcuts(receiver, deviceId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getStableInsets(int displayId, Rect outInsets) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            outInsets.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getStableInsets(displayId, outInsets);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setForwardedInsets(int displayId, Insets insets) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (insets != null) {
                        _data.writeInt(1);
                        insets.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setForwardedInsets(displayId, insets);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerShortcutKey(long shortcutCode, IShortcutService keySubscriber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(shortcutCode);
                    _data.writeStrongBinder(keySubscriber != null ? keySubscriber.asBinder() : null);
                    if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerShortcutKey(shortcutCode, keySubscriber);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createInputConsumer(IBinder token, String name, int displayId, InputChannel inputChannel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(name);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            inputChannel.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createInputConsumer(token, name, displayId, inputChannel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean destroyInputConsumer(String name, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroyInputConsumer(name, displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Region getCurrentImeTouchRegion() throws RemoteException {
                Region _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentImeTouchRegion();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Region.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Region _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerDisplayFoldListener(IDisplayFoldListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(84, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerDisplayFoldListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterDisplayFoldListener(IDisplayFoldListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(85, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterDisplayFoldListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startWindowTrace() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startWindowTrace();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopWindowTrace() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopWindowTrace();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWindowTraceEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(88, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWindowTraceEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestUserActivityNotification() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(89, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestUserActivityNotification();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dontOverrideDisplayInfo(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(90, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dontOverrideDisplayInfo(displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getWindowingMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(91, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindowingMode(displayId);
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

            public void setWindowingMode(int displayId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWindowingMode(displayId, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRemoveContentMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(93, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemoveContentMode(displayId);
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

            public void setRemoveContentMode(int displayId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(94, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRemoveContentMode(displayId, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldShowWithInsecureKeyguard(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(95, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldShowWithInsecureKeyguard(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(shouldShow);
                    if (this.mRemote.transact(96, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShouldShowWithInsecureKeyguard(displayId, shouldShow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldShowSystemDecors(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldShowSystemDecors(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setShouldShowSystemDecors(int displayId, boolean shouldShow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(shouldShow);
                    if (this.mRemote.transact(98, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShouldShowSystemDecors(displayId, shouldShow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldShowIme(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(99, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldShowIme(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setShouldShowIme(int displayId, boolean shouldShow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(shouldShow);
                    if (this.mRemote.transact(100, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setShouldShowIme(displayId, shouldShow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean injectInputAfterTransactionsApplied(InputEvent ev, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (ev != null) {
                        _data.writeInt(1);
                        ev.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(mode);
                    if (!this.mRemote.transact(101, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().injectInputAfterTransactionsApplied(ev, mode);
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

            public void syncInputTransactions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(102, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().syncInputTransactions();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWindowManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWindowManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
