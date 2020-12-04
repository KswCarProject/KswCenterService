package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.net.wifi.WifiEnterpriseConfig;
import android.nfc.IAppCallback;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class NfcActivityManager extends IAppCallback.Stub implements Application.ActivityLifecycleCallbacks {
    static final Boolean DBG = false;
    static final String TAG = "NFC";
    final List<NfcActivityState> mActivities = new LinkedList();
    @UnsupportedAppUsage
    final NfcAdapter mAdapter;
    final List<NfcApplicationState> mApps = new ArrayList(1);

    class NfcApplicationState {
        final Application app;
        int refCount = 0;

        public NfcApplicationState(Application app2) {
            this.app = app2;
        }

        public void register() {
            this.refCount++;
            if (this.refCount == 1) {
                this.app.registerActivityLifecycleCallbacks(NfcActivityManager.this);
            }
        }

        public void unregister() {
            this.refCount--;
            if (this.refCount == 0) {
                this.app.unregisterActivityLifecycleCallbacks(NfcActivityManager.this);
            } else if (this.refCount < 0) {
                Log.e(NfcActivityManager.TAG, "-ve refcount for " + this.app);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public NfcApplicationState findAppState(Application app) {
        for (NfcApplicationState appState : this.mApps) {
            if (appState.app == app) {
                return appState;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void registerApplication(Application app) {
        NfcApplicationState appState = findAppState(app);
        if (appState == null) {
            appState = new NfcApplicationState(app);
            this.mApps.add(appState);
        }
        appState.register();
    }

    /* access modifiers changed from: package-private */
    public void unregisterApplication(Application app) {
        NfcApplicationState appState = findAppState(app);
        if (appState == null) {
            Log.e(TAG, "app was not registered " + app);
            return;
        }
        appState.unregister();
    }

    class NfcActivityState {
        Activity activity;
        int flags = 0;
        NdefMessage ndefMessage = null;
        NfcAdapter.CreateNdefMessageCallback ndefMessageCallback = null;
        NfcAdapter.OnNdefPushCompleteCallback onNdefPushCompleteCallback = null;
        NfcAdapter.ReaderCallback readerCallback = null;
        Bundle readerModeExtras = null;
        int readerModeFlags = 0;
        boolean resumed = false;
        Binder token;
        NfcAdapter.CreateBeamUrisCallback uriCallback = null;
        Uri[] uris = null;

        public NfcActivityState(Activity activity2) {
            if (!activity2.getWindow().isDestroyed()) {
                this.resumed = activity2.isResumed();
                this.activity = activity2;
                this.token = new Binder();
                NfcActivityManager.this.registerApplication(activity2.getApplication());
                return;
            }
            throw new IllegalStateException("activity is already destroyed");
        }

        public void destroy() {
            NfcActivityManager.this.unregisterApplication(this.activity.getApplication());
            this.resumed = false;
            this.activity = null;
            this.ndefMessage = null;
            this.ndefMessageCallback = null;
            this.onNdefPushCompleteCallback = null;
            this.uriCallback = null;
            this.uris = null;
            this.readerModeFlags = 0;
            this.token = null;
        }

        public String toString() {
            StringBuilder s = new StringBuilder("[").append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            s.append(this.ndefMessage);
            s.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            s.append(this.ndefMessageCallback);
            s.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            s.append(this.uriCallback);
            s.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            if (this.uris != null) {
                for (Uri uri : this.uris) {
                    s.append(this.onNdefPushCompleteCallback);
                    s.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                    s.append(uri);
                    s.append("]");
                }
            }
            return s.toString();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized NfcActivityState findActivityState(Activity activity) {
        for (NfcActivityState state : this.mActivities) {
            if (state.activity == activity) {
                return state;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public synchronized NfcActivityState getActivityState(Activity activity) {
        NfcActivityState state;
        state = findActivityState(activity);
        if (state == null) {
            state = new NfcActivityState(activity);
            this.mActivities.add(state);
        }
        return state;
    }

    /* access modifiers changed from: package-private */
    public synchronized NfcActivityState findResumedActivityState() {
        for (NfcActivityState state : this.mActivities) {
            if (state.resumed) {
                return state;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public synchronized void destroyActivityState(Activity activity) {
        NfcActivityState activityState = findActivityState(activity);
        if (activityState != null) {
            activityState.destroy();
            this.mActivities.remove(activityState);
        }
    }

    public NfcActivityManager(NfcAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void enableReaderMode(Activity activity, NfcAdapter.ReaderCallback callback, int flags, Bundle extras) {
        Binder token;
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.readerCallback = callback;
            state.readerModeFlags = flags;
            state.readerModeExtras = extras;
            token = state.token;
            isResumed = state.resumed;
        }
        if (isResumed) {
            setReaderMode(token, flags, extras);
        }
    }

    public void disableReaderMode(Activity activity) {
        Binder token;
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.readerCallback = null;
            state.readerModeFlags = 0;
            state.readerModeExtras = null;
            token = state.token;
            isResumed = state.resumed;
        }
        if (isResumed) {
            setReaderMode(token, 0, (Bundle) null);
        }
    }

    public void setReaderMode(Binder token, int flags, Bundle extras) {
        if (DBG.booleanValue()) {
            Log.d(TAG, "Setting reader mode");
        }
        try {
            NfcAdapter.sService.setReaderMode(token, this, flags, extras);
        } catch (RemoteException e) {
            this.mAdapter.attemptDeadServiceRecovery(e);
        }
    }

    public void setNdefPushContentUri(Activity activity, Uri[] uris) {
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.uris = uris;
            isResumed = state.resumed;
        }
        if (isResumed) {
            requestNfcServiceCallback();
        } else {
            verifyNfcPermission();
        }
    }

    public void setNdefPushContentUriCallback(Activity activity, NfcAdapter.CreateBeamUrisCallback callback) {
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.uriCallback = callback;
            isResumed = state.resumed;
        }
        if (isResumed) {
            requestNfcServiceCallback();
        } else {
            verifyNfcPermission();
        }
    }

    public void setNdefPushMessage(Activity activity, NdefMessage message, int flags) {
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.ndefMessage = message;
            state.flags = flags;
            isResumed = state.resumed;
        }
        if (isResumed) {
            requestNfcServiceCallback();
        } else {
            verifyNfcPermission();
        }
    }

    public void setNdefPushMessageCallback(Activity activity, NfcAdapter.CreateNdefMessageCallback callback, int flags) {
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.ndefMessageCallback = callback;
            state.flags = flags;
            isResumed = state.resumed;
        }
        if (isResumed) {
            requestNfcServiceCallback();
        } else {
            verifyNfcPermission();
        }
    }

    public void setOnNdefPushCompleteCallback(Activity activity, NfcAdapter.OnNdefPushCompleteCallback callback) {
        boolean isResumed;
        synchronized (this) {
            NfcActivityState state = getActivityState(activity);
            state.onNdefPushCompleteCallback = callback;
            isResumed = state.resumed;
        }
        if (isResumed) {
            requestNfcServiceCallback();
        } else {
            verifyNfcPermission();
        }
    }

    /* access modifiers changed from: package-private */
    public void requestNfcServiceCallback() {
        try {
            NfcAdapter.sService.setAppCallback(this);
        } catch (RemoteException e) {
            this.mAdapter.attemptDeadServiceRecovery(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void verifyNfcPermission() {
        try {
            NfcAdapter.sService.verifyNfcPermission();
        } catch (RemoteException e) {
            this.mAdapter.attemptDeadServiceRecovery(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        r10 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        if (r4 == null) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0032, code lost:
        r6 = r4.createNdefMessage(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0035, code lost:
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0039, code lost:
        if (r5 == null) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r7 = r5.createBeamUris(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0040, code lost:
        if (r7 == null) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0042, code lost:
        r12 = new java.util.ArrayList<>();
        r13 = r7.length;
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0049, code lost:
        if (r14 >= r13) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004b, code lost:
        r15 = r7[r14];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x004d, code lost:
        if (r15 != null) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0051, code lost:
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        android.util.Log.e(TAG, "Uri not allowed to be null.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0059, code lost:
        r16 = r2;
        r0 = r15.getScheme();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005f, code lost:
        if (r0 == null) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0067, code lost:
        if (r0.equalsIgnoreCase(android.content.ContentResolver.SCHEME_FILE) != false) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006f, code lost:
        if (r0.equalsIgnoreCase("content") != false) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0072, code lost:
        r12.add(android.content.ContentProvider.maybeAddUserId(r15, r9.getUserId()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x007e, code lost:
        r17 = r0;
        android.util.Log.e(TAG, "Uri needs to have either scheme file or scheme content");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0088, code lost:
        r14 = r14 + 1;
        r2 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x008d, code lost:
        r16 = r2;
        r7 = (android.net.Uri[]) r12.toArray(new android.net.Uri[r12.size()]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x009d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009e, code lost:
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a1, code lost:
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a3, code lost:
        if (r7 == null) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00a6, code lost:
        if (r7.length <= 0) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00a8, code lost:
        r0 = r7.length;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00aa, code lost:
        if (r2 >= r0) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ac, code lost:
        r9.grantUriPermission("com.android.nfc", r7[r2], 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00b4, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b8, code lost:
        android.os.Binder.restoreCallingIdentity(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00bb, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00bc, code lost:
        android.os.Binder.restoreCallingIdentity(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00c9, code lost:
        return new android.nfc.BeamShareData(r6, r7, r9.getUser(), r8);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.nfc.BeamShareData createBeamShareData(byte r19) {
        /*
            r18 = this;
            r1 = r18
            android.nfc.NfcEvent r0 = new android.nfc.NfcEvent
            android.nfc.NfcAdapter r2 = r1.mAdapter
            r3 = r19
            r0.<init>(r2, r3)
            r2 = r0
            monitor-enter(r18)
            android.nfc.NfcActivityManager$NfcActivityState r0 = r18.findResumedActivityState()     // Catch:{ all -> 0x00ca }
            if (r0 != 0) goto L_0x001b
            r4 = 0
            monitor-exit(r18)     // Catch:{ all -> 0x0016 }
            return r4
        L_0x0016:
            r0 = move-exception
            r16 = r2
            goto L_0x00cd
        L_0x001b:
            android.nfc.NfcAdapter$CreateNdefMessageCallback r4 = r0.ndefMessageCallback     // Catch:{ all -> 0x00ca }
            android.nfc.NfcAdapter$CreateBeamUrisCallback r5 = r0.uriCallback     // Catch:{ all -> 0x00ca }
            android.nfc.NdefMessage r6 = r0.ndefMessage     // Catch:{ all -> 0x00ca }
            android.net.Uri[] r7 = r0.uris     // Catch:{ all -> 0x00ca }
            int r8 = r0.flags     // Catch:{ all -> 0x00ca }
            android.app.Activity r9 = r0.activity     // Catch:{ all -> 0x00ca }
            monitor-exit(r18)     // Catch:{ all -> 0x00ca }
            long r10 = android.os.Binder.clearCallingIdentity()
            if (r4 == 0) goto L_0x0039
            android.nfc.NdefMessage r0 = r4.createNdefMessage(r2)     // Catch:{ all -> 0x0034 }
            r6 = r0
            goto L_0x0039
        L_0x0034:
            r0 = move-exception
            r16 = r2
            goto L_0x00b8
        L_0x0039:
            if (r5 == 0) goto L_0x00a1
            android.net.Uri[] r12 = r5.createBeamUris(r2)     // Catch:{ all -> 0x009d }
            r7 = r12
            if (r7 == 0) goto L_0x00a1
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ all -> 0x009d }
            r12.<init>()     // Catch:{ all -> 0x009d }
            int r13 = r7.length     // Catch:{ all -> 0x009d }
            r14 = 0
        L_0x0049:
            if (r14 >= r13) goto L_0x008d
            r15 = r7[r14]     // Catch:{ all -> 0x009d }
            if (r15 != 0) goto L_0x0059
            java.lang.String r0 = "NFC"
            r16 = r2
            java.lang.String r2 = "Uri not allowed to be null."
            android.util.Log.e(r0, r2)     // Catch:{ all -> 0x00b7 }
            goto L_0x0088
        L_0x0059:
            r16 = r2
            java.lang.String r0 = r15.getScheme()     // Catch:{ all -> 0x00b7 }
            if (r0 == 0) goto L_0x007e
            java.lang.String r2 = "file"
            boolean r2 = r0.equalsIgnoreCase(r2)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0072
            java.lang.String r2 = "content"
            boolean r2 = r0.equalsIgnoreCase(r2)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0072
            goto L_0x007e
        L_0x0072:
            int r2 = r9.getUserId()     // Catch:{ all -> 0x00b7 }
            android.net.Uri r2 = android.content.ContentProvider.maybeAddUserId(r15, r2)     // Catch:{ all -> 0x00b7 }
            r12.add(r2)     // Catch:{ all -> 0x00b7 }
            goto L_0x0088
        L_0x007e:
            java.lang.String r2 = "NFC"
            r17 = r0
            java.lang.String r0 = "Uri needs to have either scheme file or scheme content"
            android.util.Log.e(r2, r0)     // Catch:{ all -> 0x00b7 }
        L_0x0088:
            int r14 = r14 + 1
            r2 = r16
            goto L_0x0049
        L_0x008d:
            r16 = r2
            int r0 = r12.size()     // Catch:{ all -> 0x00b7 }
            android.net.Uri[] r0 = new android.net.Uri[r0]     // Catch:{ all -> 0x00b7 }
            java.lang.Object[] r0 = r12.toArray(r0)     // Catch:{ all -> 0x00b7 }
            android.net.Uri[] r0 = (android.net.Uri[]) r0     // Catch:{ all -> 0x00b7 }
            r7 = r0
            goto L_0x00a3
        L_0x009d:
            r0 = move-exception
            r16 = r2
            goto L_0x00b8
        L_0x00a1:
            r16 = r2
        L_0x00a3:
            if (r7 == 0) goto L_0x00bc
            int r0 = r7.length     // Catch:{ all -> 0x00b7 }
            if (r0 <= 0) goto L_0x00bc
            int r0 = r7.length     // Catch:{ all -> 0x00b7 }
            r2 = 0
        L_0x00aa:
            if (r2 >= r0) goto L_0x00bc
            r12 = r7[r2]     // Catch:{ all -> 0x00b7 }
            java.lang.String r13 = "com.android.nfc"
            r14 = 1
            r9.grantUriPermission(r13, r12, r14)     // Catch:{ all -> 0x00b7 }
            int r2 = r2 + 1
            goto L_0x00aa
        L_0x00b7:
            r0 = move-exception
        L_0x00b8:
            android.os.Binder.restoreCallingIdentity(r10)
            throw r0
        L_0x00bc:
            android.os.Binder.restoreCallingIdentity(r10)
            android.nfc.BeamShareData r0 = new android.nfc.BeamShareData
            android.os.UserHandle r2 = r9.getUser()
            r0.<init>(r6, r7, r2, r8)
            return r0
        L_0x00ca:
            r0 = move-exception
            r16 = r2
        L_0x00cd:
            monitor-exit(r18)     // Catch:{ all -> 0x00cf }
            throw r0
        L_0x00cf:
            r0 = move-exception
            goto L_0x00cd
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcActivityManager.createBeamShareData(byte):android.nfc.BeamShareData");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        r0.onNdefPushComplete(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000d, code lost:
        r1 = new android.nfc.NfcEvent(r3.mAdapter, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        if (r0 == null) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onNdefPushComplete(byte r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            android.nfc.NfcActivityManager$NfcActivityState r0 = r3.findResumedActivityState()     // Catch:{ all -> 0x001a }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r3)     // Catch:{ all -> 0x001a }
            return
        L_0x0009:
            android.nfc.NfcAdapter$OnNdefPushCompleteCallback r1 = r0.onNdefPushCompleteCallback     // Catch:{ all -> 0x001a }
            r0 = r1
            monitor-exit(r3)     // Catch:{ all -> 0x001a }
            android.nfc.NfcEvent r1 = new android.nfc.NfcEvent
            android.nfc.NfcAdapter r2 = r3.mAdapter
            r1.<init>(r2, r4)
            if (r0 == 0) goto L_0x0019
            r0.onNdefPushComplete(r1)
        L_0x0019:
            return
        L_0x001a:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x001a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcActivityManager.onNdefPushComplete(byte):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000d, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000f, code lost:
        r0.onTagDiscovered(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTagDiscovered(android.nfc.Tag r3) throws android.os.RemoteException {
        /*
            r2 = this;
            monitor-enter(r2)
            android.nfc.NfcActivityManager$NfcActivityState r0 = r2.findResumedActivityState()     // Catch:{ all -> 0x0013 }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            return
        L_0x0009:
            android.nfc.NfcAdapter$ReaderCallback r1 = r0.readerCallback     // Catch:{ all -> 0x0013 }
            r0 = r1
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            if (r0 == 0) goto L_0x0012
            r0.onTagDiscovered(r3)
        L_0x0012:
            return
        L_0x0013:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcActivityManager.onTagDiscovered(android.nfc.Tag):void");
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityStarted(Activity activity) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003e, code lost:
        if (r0 == 0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0040, code lost:
        setReaderMode(r3, r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0043, code lost:
        requestNfcServiceCallback();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0046, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResumed(android.app.Activity r7) {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            monitor-enter(r6)
            android.nfc.NfcActivityManager$NfcActivityState r2 = r6.findActivityState(r7)     // Catch:{ all -> 0x0047 }
            java.lang.Boolean r3 = DBG     // Catch:{ all -> 0x0047 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0047 }
            if (r3 == 0) goto L_0x002e
            java.lang.String r3 = "NFC"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0047 }
            r4.<init>()     // Catch:{ all -> 0x0047 }
            java.lang.String r5 = "onResume() for "
            r4.append(r5)     // Catch:{ all -> 0x0047 }
            r4.append(r7)     // Catch:{ all -> 0x0047 }
            java.lang.String r5 = " "
            r4.append(r5)     // Catch:{ all -> 0x0047 }
            r4.append(r2)     // Catch:{ all -> 0x0047 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0047 }
            android.util.Log.d(r3, r4)     // Catch:{ all -> 0x0047 }
        L_0x002e:
            if (r2 != 0) goto L_0x0032
            monitor-exit(r6)     // Catch:{ all -> 0x0047 }
            return
        L_0x0032:
            r3 = 1
            r2.resumed = r3     // Catch:{ all -> 0x0047 }
            android.os.Binder r3 = r2.token     // Catch:{ all -> 0x0047 }
            int r4 = r2.readerModeFlags     // Catch:{ all -> 0x0047 }
            r0 = r4
            android.os.Bundle r4 = r2.readerModeExtras     // Catch:{ all -> 0x0047 }
            r1 = r4
            monitor-exit(r6)     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0043
            r6.setReaderMode(r3, r0, r1)
        L_0x0043:
            r6.requestNfcServiceCallback()
            return
        L_0x0047:
            r2 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0047 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcActivityManager.onActivityResumed(android.app.Activity):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003e, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        setReaderMode(r2, 0, (android.os.Bundle) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityPaused(android.app.Activity r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.nfc.NfcActivityManager$NfcActivityState r0 = r4.findActivityState(r5)     // Catch:{ all -> 0x0045 }
            java.lang.Boolean r1 = DBG     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x0045 }
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "NFC"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0045 }
            r2.<init>()     // Catch:{ all -> 0x0045 }
            java.lang.String r3 = "onPause() for "
            r2.append(r3)     // Catch:{ all -> 0x0045 }
            r2.append(r5)     // Catch:{ all -> 0x0045 }
            java.lang.String r3 = " "
            r2.append(r3)     // Catch:{ all -> 0x0045 }
            r2.append(r0)     // Catch:{ all -> 0x0045 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0045 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0045 }
        L_0x002c:
            if (r0 != 0) goto L_0x0030
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            return
        L_0x0030:
            r1 = 0
            r0.resumed = r1     // Catch:{ all -> 0x0045 }
            android.os.Binder r2 = r0.token     // Catch:{ all -> 0x0045 }
            int r3 = r0.readerModeFlags     // Catch:{ all -> 0x0045 }
            if (r3 == 0) goto L_0x003b
            r3 = 1
            goto L_0x003c
        L_0x003b:
            r3 = r1
        L_0x003c:
            r0 = r3
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x0044
            r3 = 0
            r4.setReaderMode(r2, r1, r3)
        L_0x0044:
            return
        L_0x0045:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcActivityManager.onActivityPaused(android.app.Activity):void");
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
        synchronized (this) {
            NfcActivityState state = findActivityState(activity);
            if (DBG.booleanValue()) {
                Log.d(TAG, "onDestroy() for " + activity + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + state);
            }
            if (state != null) {
                destroyActivityState(activity);
            }
        }
    }
}
