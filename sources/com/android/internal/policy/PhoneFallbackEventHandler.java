package com.android.internal.policy;

import android.annotation.UnsupportedAppUsage;
import android.app.KeyguardManager;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.FallbackEventHandler;
import android.view.KeyEvent;
import android.view.View;

public class PhoneFallbackEventHandler implements FallbackEventHandler {
    private static final boolean DEBUG = false;
    private static String TAG = "PhoneFallbackEventHandler";
    AudioManager mAudioManager;
    @UnsupportedAppUsage
    Context mContext;
    KeyguardManager mKeyguardManager;
    MediaSessionManager mMediaSessionManager;
    SearchManager mSearchManager;
    TelephonyManager mTelephonyManager;
    @UnsupportedAppUsage
    View mView;

    @UnsupportedAppUsage
    public PhoneFallbackEventHandler(Context context) {
        this.mContext = context;
    }

    public void setView(View v) {
        this.mView = v;
    }

    public void preDispatchKeyEvent(KeyEvent event) {
        getAudioManager().preDispatchKeyEvent(event, Integer.MIN_VALUE);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == 0) {
            return onKeyDown(keyCode, event);
        }
        return onKeyUp(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003a, code lost:
        if (getTelephonyManager().getCallState() == 0) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003c, code lost:
        return true;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int r19, android.view.KeyEvent r20) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            android.view.View r0 = r1.mView
            android.view.KeyEvent$DispatcherState r4 = r0.getKeyDispatcherState()
            r0 = 5
            r5 = 268435456(0x10000000, float:2.5243549E-29)
            r6 = 0
            r7 = 1
            if (r2 == r0) goto L_0x0102
            r0 = 27
            if (r2 == r0) goto L_0x00ab
            r0 = 79
            if (r2 == r0) goto L_0x00a7
            r0 = 130(0x82, float:1.82E-43)
            if (r2 == r0) goto L_0x00a7
            r0 = 164(0xa4, float:2.3E-43)
            if (r2 == r0) goto L_0x00a3
            r0 = 222(0xde, float:3.11E-43)
            if (r2 == r0) goto L_0x00a7
            switch(r2) {
                case 24: goto L_0x00a3;
                case 25: goto L_0x00a3;
                default: goto L_0x002a;
            }
        L_0x002a:
            switch(r2) {
                case 84: goto L_0x003d;
                case 85: goto L_0x0032;
                case 86: goto L_0x00a7;
                case 87: goto L_0x00a7;
                case 88: goto L_0x00a7;
                case 89: goto L_0x00a7;
                case 90: goto L_0x00a7;
                case 91: goto L_0x00a7;
                default: goto L_0x002d;
            }
        L_0x002d:
            switch(r2) {
                case 126: goto L_0x0032;
                case 127: goto L_0x0032;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0109
        L_0x0032:
            android.telephony.TelephonyManager r0 = r18.getTelephonyManager()
            int r0 = r0.getCallState()
            if (r0 == 0) goto L_0x00a7
            return r7
        L_0x003d:
            boolean r0 = r1.isNotInstantAppAndKeyguardRestricted(r4)
            if (r0 == 0) goto L_0x0045
            goto L_0x0109
        L_0x0045:
            int r0 = r20.getRepeatCount()
            if (r0 != 0) goto L_0x0050
            r4.startTracking(r3, r1)
            goto L_0x0109
        L_0x0050:
            boolean r0 = r20.isLongPress()
            if (r0 == 0) goto L_0x0109
            boolean r0 = r4.isTracking(r3)
            if (r0 == 0) goto L_0x0109
            android.content.Context r0 = r1.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r8 = r0.getConfiguration()
            int r0 = r8.keyboard
            if (r0 == r7) goto L_0x006f
            int r0 = r8.hardKeyboardHidden
            r9 = 2
            if (r0 != r9) goto L_0x0109
        L_0x006f:
            boolean r0 = r18.isUserSetupComplete()
            if (r0 == 0) goto L_0x009b
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r9 = "android.intent.action.SEARCH_LONG_PRESS"
            r0.<init>((java.lang.String) r9)
            r9 = r0
            r9.setFlags(r5)
            android.view.View r0 = r1.mView     // Catch:{ ActivityNotFoundException -> 0x0098 }
            r0.performHapticFeedback(r6)     // Catch:{ ActivityNotFoundException -> 0x0098 }
            r18.sendCloseSystemWindows()     // Catch:{ ActivityNotFoundException -> 0x0098 }
            android.app.SearchManager r0 = r18.getSearchManager()     // Catch:{ ActivityNotFoundException -> 0x0098 }
            r0.stopSearch()     // Catch:{ ActivityNotFoundException -> 0x0098 }
            android.content.Context r0 = r1.mContext     // Catch:{ ActivityNotFoundException -> 0x0098 }
            r0.startActivity(r9)     // Catch:{ ActivityNotFoundException -> 0x0098 }
            r4.performedLongPress(r3)     // Catch:{ ActivityNotFoundException -> 0x0098 }
            return r7
        L_0x0098:
            r0 = move-exception
            goto L_0x0109
        L_0x009b:
            java.lang.String r0 = TAG
            java.lang.String r5 = "Not dispatching SEARCH long press because user setup is in progress."
            android.util.Log.i(r0, r5)
            goto L_0x0109
        L_0x00a3:
            r1.handleVolumeKeyEvent(r3)
            return r7
        L_0x00a7:
            r1.handleMediaKeyEvent(r3)
            return r7
        L_0x00ab:
            boolean r0 = r1.isNotInstantAppAndKeyguardRestricted(r4)
            if (r0 == 0) goto L_0x00b2
            goto L_0x0109
        L_0x00b2:
            int r0 = r20.getRepeatCount()
            if (r0 != 0) goto L_0x00bc
            r4.startTracking(r3, r1)
            goto L_0x0101
        L_0x00bc:
            boolean r0 = r20.isLongPress()
            if (r0 == 0) goto L_0x0101
            boolean r0 = r4.isTracking(r3)
            if (r0 == 0) goto L_0x0101
            r4.performedLongPress(r3)
            boolean r0 = r18.isUserSetupComplete()
            if (r0 == 0) goto L_0x00fa
            android.view.View r0 = r1.mView
            r0.performHapticFeedback(r6)
            r18.sendCloseSystemWindows()
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r6 = "android.intent.action.CAMERA_BUTTON"
            r8 = 0
            r0.<init>((java.lang.String) r6, (android.net.Uri) r8)
            r0.addFlags(r5)
            java.lang.String r5 = "android.intent.extra.KEY_EVENT"
            r0.putExtra((java.lang.String) r5, (android.os.Parcelable) r3)
            android.content.Context r9 = r1.mContext
            android.os.UserHandle r11 = android.os.UserHandle.CURRENT_OR_SELF
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r10 = r0
            r9.sendOrderedBroadcastAsUser(r10, r11, r12, r13, r14, r15, r16, r17)
            goto L_0x0101
        L_0x00fa:
            java.lang.String r0 = TAG
            java.lang.String r5 = "Not dispatching CAMERA long press because user setup is in progress."
            android.util.Log.i(r0, r5)
        L_0x0101:
            return r7
        L_0x0102:
            boolean r0 = r1.isNotInstantAppAndKeyguardRestricted(r4)
            if (r0 == 0) goto L_0x010a
        L_0x0109:
            return r6
        L_0x010a:
            int r0 = r20.getRepeatCount()
            if (r0 != 0) goto L_0x0114
            r4.startTracking(r3, r1)
            goto L_0x014e
        L_0x0114:
            boolean r0 = r20.isLongPress()
            if (r0 == 0) goto L_0x014e
            boolean r0 = r4.isTracking(r3)
            if (r0 == 0) goto L_0x014e
            r4.performedLongPress(r3)
            boolean r0 = r18.isUserSetupComplete()
            if (r0 == 0) goto L_0x0147
            android.view.View r0 = r1.mView
            r0.performHapticFeedback(r6)
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r6 = "android.intent.action.VOICE_COMMAND"
            r0.<init>((java.lang.String) r6)
            r6 = r0
            r6.setFlags(r5)
            r18.sendCloseSystemWindows()     // Catch:{ ActivityNotFoundException -> 0x0142 }
            android.content.Context r0 = r1.mContext     // Catch:{ ActivityNotFoundException -> 0x0142 }
            r0.startActivity(r6)     // Catch:{ ActivityNotFoundException -> 0x0142 }
            goto L_0x0146
        L_0x0142:
            r0 = move-exception
            r18.startCallActivity()
        L_0x0146:
            goto L_0x014e
        L_0x0147:
            java.lang.String r0 = TAG
            java.lang.String r5 = "Not starting call activity because user setup is in progress."
            android.util.Log.i(r0, r5)
        L_0x014e:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.PhoneFallbackEventHandler.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    private boolean isNotInstantAppAndKeyguardRestricted(KeyEvent.DispatcherState dispatcher) {
        return !this.mContext.getPackageManager().isInstantApp() && (getKeyguardManager().inKeyguardRestrictedInputMode() || dispatcher == null);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        KeyEvent.DispatcherState dispatcher = this.mView.getKeyDispatcherState();
        if (dispatcher != null) {
            dispatcher.handleUpEvent(event);
        }
        if (keyCode != 5) {
            if (keyCode != 27) {
                if (!(keyCode == 79 || keyCode == 130)) {
                    if (keyCode != 164) {
                        if (keyCode != 222) {
                            switch (keyCode) {
                                case 24:
                                case 25:
                                    break;
                                default:
                                    switch (keyCode) {
                                        case 85:
                                        case 86:
                                        case 87:
                                        case 88:
                                        case 89:
                                        case 90:
                                        case 91:
                                            break;
                                        default:
                                            switch (keyCode) {
                                                case 126:
                                                case 127:
                                                    break;
                                                default:
                                                    return false;
                                            }
                                    }
                            }
                        }
                    }
                    if (!event.isCanceled()) {
                        handleVolumeKeyEvent(event);
                    }
                    return true;
                }
                handleMediaKeyEvent(event);
                return true;
            } else if (isNotInstantAppAndKeyguardRestricted(dispatcher)) {
                return false;
            } else {
                if (event.isTracking()) {
                    event.isCanceled();
                }
                return true;
            }
        } else if (isNotInstantAppAndKeyguardRestricted(dispatcher)) {
            return false;
        } else {
            if (event.isTracking() && !event.isCanceled()) {
                if (isUserSetupComplete()) {
                    startCallActivity();
                } else {
                    Log.i(TAG, "Not starting call activity because user setup is in progress.");
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void startCallActivity() {
        sendCloseSystemWindows();
        Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
        intent.setFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, "No activity found for android.intent.action.CALL_BUTTON.");
        }
    }

    /* access modifiers changed from: package-private */
    public SearchManager getSearchManager() {
        if (this.mSearchManager == null) {
            this.mSearchManager = (SearchManager) this.mContext.getSystemService("search");
        }
        return this.mSearchManager;
    }

    /* access modifiers changed from: package-private */
    public TelephonyManager getTelephonyManager() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        }
        return this.mTelephonyManager;
    }

    /* access modifiers changed from: package-private */
    public KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        }
        return this.mKeyguardManager;
    }

    /* access modifiers changed from: package-private */
    public AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        }
        return this.mAudioManager;
    }

    /* access modifiers changed from: package-private */
    public MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager) this.mContext.getSystemService(Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    /* access modifiers changed from: package-private */
    public void sendCloseSystemWindows() {
        PhoneWindow.sendCloseSystemWindows(this.mContext, (String) null);
    }

    private void handleVolumeKeyEvent(KeyEvent keyEvent) {
        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, Integer.MIN_VALUE);
    }

    private void handleMediaKeyEvent(KeyEvent keyEvent) {
        getMediaSessionManager().dispatchMediaKeyEventAsSystemService(keyEvent);
    }

    private boolean isUserSetupComplete() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0) != 0;
    }
}
