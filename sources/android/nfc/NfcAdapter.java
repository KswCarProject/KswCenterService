package android.nfc;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.OnActivityPausedListener;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.INfcAdapter;
import android.nfc.INfcUnlockHandler;
import android.nfc.ITagRemovedCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class NfcAdapter {
    public static final String ACTION_ADAPTER_STATE_CHANGED = "android.nfc.action.ADAPTER_STATE_CHANGED";
    public static final String ACTION_HANDOVER_TRANSFER_DONE = "android.nfc.action.HANDOVER_TRANSFER_DONE";
    public static final String ACTION_HANDOVER_TRANSFER_STARTED = "android.nfc.action.HANDOVER_TRANSFER_STARTED";
    public static final String ACTION_NDEF_DISCOVERED = "android.nfc.action.NDEF_DISCOVERED";
    public static final String ACTION_TAG_DISCOVERED = "android.nfc.action.TAG_DISCOVERED";
    public static final String ACTION_TAG_LEFT_FIELD = "android.nfc.action.TAG_LOST";
    public static final String ACTION_TECH_DISCOVERED = "android.nfc.action.TECH_DISCOVERED";
    public static final String ACTION_TRANSACTION_DETECTED = "android.nfc.action.TRANSACTION_DETECTED";
    public static final String EXTRA_ADAPTER_STATE = "android.nfc.extra.ADAPTER_STATE";
    public static final String EXTRA_AID = "android.nfc.extra.AID";
    public static final String EXTRA_DATA = "android.nfc.extra.DATA";
    public static final String EXTRA_HANDOVER_TRANSFER_STATUS = "android.nfc.extra.HANDOVER_TRANSFER_STATUS";
    public static final String EXTRA_HANDOVER_TRANSFER_URI = "android.nfc.extra.HANDOVER_TRANSFER_URI";
    public static final String EXTRA_ID = "android.nfc.extra.ID";
    public static final String EXTRA_NDEF_MESSAGES = "android.nfc.extra.NDEF_MESSAGES";
    public static final String EXTRA_READER_PRESENCE_CHECK_DELAY = "presence";
    public static final String EXTRA_SECURE_ELEMENT_NAME = "android.nfc.extra.SECURE_ELEMENT_NAME";
    public static final String EXTRA_TAG = "android.nfc.extra.TAG";
    @SystemApi
    public static final int FLAG_NDEF_PUSH_NO_CONFIRM = 1;
    public static final int FLAG_READER_NFC_A = 1;
    public static final int FLAG_READER_NFC_B = 2;
    public static final int FLAG_READER_NFC_BARCODE = 16;
    public static final int FLAG_READER_NFC_F = 4;
    public static final int FLAG_READER_NFC_V = 8;
    public static final int FLAG_READER_NO_PLATFORM_SOUNDS = 256;
    public static final int FLAG_READER_SKIP_NDEF_CHECK = 128;
    public static final int HANDOVER_TRANSFER_STATUS_FAILURE = 1;
    public static final int HANDOVER_TRANSFER_STATUS_SUCCESS = 0;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 3;
    public static final int STATE_TURNING_OFF = 4;
    public static final int STATE_TURNING_ON = 2;
    static final String TAG = "NFC";
    static INfcCardEmulation sCardEmulationService;
    static boolean sHasBeamFeature;
    static boolean sHasNfcFeature;
    static boolean sIsInitialized = false;
    static HashMap<Context, NfcAdapter> sNfcAdapters = new HashMap<>();
    static INfcFCardEmulation sNfcFCardEmulationService;
    static NfcAdapter sNullContextNfcAdapter;
    @UnsupportedAppUsage
    static INfcAdapter sService;
    static INfcTag sTagService;
    final Context mContext;
    OnActivityPausedListener mForegroundDispatchListener = new OnActivityPausedListener() {
        public void onPaused(Activity activity) {
            NfcAdapter.this.disableForegroundDispatchInternal(activity, true);
        }
    };
    final Object mLock;
    final NfcActivityManager mNfcActivityManager;
    final HashMap<NfcUnlockHandler, INfcUnlockHandler> mNfcUnlockHandlers;
    ITagRemovedCallback mTagRemovedListener;

    @Deprecated
    public interface CreateBeamUrisCallback {
        Uri[] createBeamUris(NfcEvent nfcEvent);
    }

    @Deprecated
    public interface CreateNdefMessageCallback {
        NdefMessage createNdefMessage(NfcEvent nfcEvent);
    }

    @SystemApi
    public interface NfcUnlockHandler {
        boolean onUnlockAttempted(Tag tag);
    }

    @Deprecated
    public interface OnNdefPushCompleteCallback {
        void onNdefPushComplete(NfcEvent nfcEvent);
    }

    public interface OnTagRemovedListener {
        void onTagRemoved();
    }

    public interface ReaderCallback {
        void onTagDiscovered(Tag tag);
    }

    private static boolean hasBeamFeature() {
        IPackageManager pm = ActivityThread.getPackageManager();
        if (pm == null) {
            Log.e(TAG, "Cannot get package manager, assuming no Android Beam feature");
            return false;
        }
        try {
            return pm.hasSystemFeature(PackageManager.FEATURE_NFC_BEAM, 0);
        } catch (RemoteException e) {
            Log.e(TAG, "Package manager query failed, assuming no Android Beam feature", e);
            return false;
        }
    }

    private static boolean hasNfcFeature() {
        IPackageManager pm = ActivityThread.getPackageManager();
        if (pm == null) {
            Log.e(TAG, "Cannot get package manager, assuming no NFC feature");
            return false;
        }
        try {
            return pm.hasSystemFeature(PackageManager.FEATURE_NFC, 0);
        } catch (RemoteException e) {
            Log.e(TAG, "Package manager query failed, assuming no NFC feature", e);
            return false;
        }
    }

    private static boolean hasNfcHceFeature() {
        IPackageManager pm = ActivityThread.getPackageManager();
        if (pm == null) {
            Log.e(TAG, "Cannot get package manager, assuming no NFC feature");
            return false;
        }
        try {
            if (pm.hasSystemFeature("android.hardware.nfc.hce", 0) || pm.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF, 0)) {
                return true;
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Package manager query failed, assuming no NFC feature", e);
            return false;
        }
    }

    public List<String> getSupportedOffHostSecureElements() {
        List<String> offHostSE = new ArrayList<>();
        IPackageManager pm = ActivityThread.getPackageManager();
        if (pm == null) {
            Log.e(TAG, "Cannot get package manager, assuming no off-host CE feature");
            return offHostSE;
        }
        try {
            if (pm.hasSystemFeature(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC, 0)) {
                offHostSE.add("SIM");
            }
            if (pm.hasSystemFeature(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE, 0)) {
                offHostSE.add("eSE");
            }
            return offHostSE;
        } catch (RemoteException e) {
            Log.e(TAG, "Package manager query failed, assuming no off-host CE feature", e);
            offHostSE.clear();
            return offHostSE;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00ba, code lost:
        return r1;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized android.nfc.NfcAdapter getNfcAdapter(android.content.Context r5) {
        /*
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sIsInitialized     // Catch:{ all -> 0x00bb }
            if (r1 != 0) goto L_0x0092
            boolean r1 = hasNfcFeature()     // Catch:{ all -> 0x00bb }
            sHasNfcFeature = r1     // Catch:{ all -> 0x00bb }
            boolean r1 = hasBeamFeature()     // Catch:{ all -> 0x00bb }
            sHasBeamFeature = r1     // Catch:{ all -> 0x00bb }
            boolean r1 = hasNfcHceFeature()     // Catch:{ all -> 0x00bb }
            boolean r2 = sHasNfcFeature     // Catch:{ all -> 0x00bb }
            if (r2 != 0) goto L_0x002c
            if (r1 == 0) goto L_0x001e
            goto L_0x002c
        L_0x001e:
            java.lang.String r2 = "NFC"
            java.lang.String r3 = "this device does not have NFC support"
            android.util.Log.v(r2, r3)     // Catch:{ all -> 0x00bb }
            java.lang.UnsupportedOperationException r2 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x00bb }
            r2.<init>()     // Catch:{ all -> 0x00bb }
            throw r2     // Catch:{ all -> 0x00bb }
        L_0x002c:
            android.nfc.INfcAdapter r2 = getServiceInterface()     // Catch:{ all -> 0x00bb }
            sService = r2     // Catch:{ all -> 0x00bb }
            android.nfc.INfcAdapter r2 = sService     // Catch:{ all -> 0x00bb }
            if (r2 == 0) goto L_0x0085
            boolean r2 = sHasNfcFeature     // Catch:{ all -> 0x00bb }
            if (r2 == 0) goto L_0x0051
            android.nfc.INfcAdapter r2 = sService     // Catch:{ RemoteException -> 0x0043 }
            android.nfc.INfcTag r2 = r2.getNfcTagInterface()     // Catch:{ RemoteException -> 0x0043 }
            sTagService = r2     // Catch:{ RemoteException -> 0x0043 }
            goto L_0x0051
        L_0x0043:
            r2 = move-exception
            java.lang.String r3 = "NFC"
            java.lang.String r4 = "could not retrieve NFC Tag service"
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x00bb }
            java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x00bb }
            r3.<init>()     // Catch:{ all -> 0x00bb }
            throw r3     // Catch:{ all -> 0x00bb }
        L_0x0051:
            if (r1 == 0) goto L_0x0081
            android.nfc.INfcAdapter r2 = sService     // Catch:{ RemoteException -> 0x0073 }
            android.nfc.INfcFCardEmulation r2 = r2.getNfcFCardEmulationInterface()     // Catch:{ RemoteException -> 0x0073 }
            sNfcFCardEmulationService = r2     // Catch:{ RemoteException -> 0x0073 }
            android.nfc.INfcAdapter r2 = sService     // Catch:{ RemoteException -> 0x0065 }
            android.nfc.INfcCardEmulation r2 = r2.getNfcCardEmulationInterface()     // Catch:{ RemoteException -> 0x0065 }
            sCardEmulationService = r2     // Catch:{ RemoteException -> 0x0065 }
            goto L_0x0081
        L_0x0065:
            r2 = move-exception
            java.lang.String r3 = "NFC"
            java.lang.String r4 = "could not retrieve card emulation service"
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x00bb }
            java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x00bb }
            r3.<init>()     // Catch:{ all -> 0x00bb }
            throw r3     // Catch:{ all -> 0x00bb }
        L_0x0073:
            r2 = move-exception
            java.lang.String r3 = "NFC"
            java.lang.String r4 = "could not retrieve NFC-F card emulation service"
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x00bb }
            java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x00bb }
            r3.<init>()     // Catch:{ all -> 0x00bb }
            throw r3     // Catch:{ all -> 0x00bb }
        L_0x0081:
            r2 = 1
            sIsInitialized = r2     // Catch:{ all -> 0x00bb }
            goto L_0x0092
        L_0x0085:
            java.lang.String r2 = "NFC"
            java.lang.String r3 = "could not retrieve NFC service"
            android.util.Log.e(r2, r3)     // Catch:{ all -> 0x00bb }
            java.lang.UnsupportedOperationException r2 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x00bb }
            r2.<init>()     // Catch:{ all -> 0x00bb }
            throw r2     // Catch:{ all -> 0x00bb }
        L_0x0092:
            if (r5 != 0) goto L_0x00a4
            android.nfc.NfcAdapter r1 = sNullContextNfcAdapter     // Catch:{ all -> 0x00bb }
            if (r1 != 0) goto L_0x00a0
            android.nfc.NfcAdapter r1 = new android.nfc.NfcAdapter     // Catch:{ all -> 0x00bb }
            r2 = 0
            r1.<init>(r2)     // Catch:{ all -> 0x00bb }
            sNullContextNfcAdapter = r1     // Catch:{ all -> 0x00bb }
        L_0x00a0:
            android.nfc.NfcAdapter r1 = sNullContextNfcAdapter     // Catch:{ all -> 0x00bb }
            monitor-exit(r0)
            return r1
        L_0x00a4:
            java.util.HashMap<android.content.Context, android.nfc.NfcAdapter> r1 = sNfcAdapters     // Catch:{ all -> 0x00bb }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x00bb }
            android.nfc.NfcAdapter r1 = (android.nfc.NfcAdapter) r1     // Catch:{ all -> 0x00bb }
            if (r1 != 0) goto L_0x00b9
            android.nfc.NfcAdapter r2 = new android.nfc.NfcAdapter     // Catch:{ all -> 0x00bb }
            r2.<init>(r5)     // Catch:{ all -> 0x00bb }
            r1 = r2
            java.util.HashMap<android.content.Context, android.nfc.NfcAdapter> r2 = sNfcAdapters     // Catch:{ all -> 0x00bb }
            r2.put(r5, r1)     // Catch:{ all -> 0x00bb }
        L_0x00b9:
            monitor-exit(r0)
            return r1
        L_0x00bb:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.getNfcAdapter(android.content.Context):android.nfc.NfcAdapter");
    }

    private static INfcAdapter getServiceInterface() {
        IBinder b = ServiceManager.getService("nfc");
        if (b == null) {
            return null;
        }
        return INfcAdapter.Stub.asInterface(b);
    }

    public static NfcAdapter getDefaultAdapter(Context context) {
        if (context != null) {
            Context context2 = context.getApplicationContext();
            if (context2 != null) {
                NfcManager manager = (NfcManager) context2.getSystemService("nfc");
                if (manager == null) {
                    return null;
                }
                return manager.getDefaultAdapter();
            }
            throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
        }
        throw new IllegalArgumentException("context cannot be null");
    }

    @Deprecated
    @UnsupportedAppUsage
    public static NfcAdapter getDefaultAdapter() {
        Log.w(TAG, "WARNING: NfcAdapter.getDefaultAdapter() is deprecated, use NfcAdapter.getDefaultAdapter(Context) instead", new Exception());
        return getNfcAdapter((Context) null);
    }

    NfcAdapter(Context context) {
        this.mContext = context;
        this.mNfcActivityManager = new NfcActivityManager(this);
        this.mNfcUnlockHandlers = new HashMap<>();
        this.mTagRemovedListener = null;
        this.mLock = new Object();
    }

    @UnsupportedAppUsage
    public Context getContext() {
        return this.mContext;
    }

    @UnsupportedAppUsage
    public INfcAdapter getService() {
        isEnabled();
        return sService;
    }

    public INfcTag getTagService() {
        isEnabled();
        return sTagService;
    }

    public INfcCardEmulation getCardEmulationService() {
        isEnabled();
        return sCardEmulationService;
    }

    public INfcFCardEmulation getNfcFCardEmulationService() {
        isEnabled();
        return sNfcFCardEmulationService;
    }

    public INfcDta getNfcDtaInterface() {
        if (this.mContext != null) {
            try {
                return sService.getNfcDtaInterface(this.mContext.getPackageName());
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
                return null;
            }
        } else {
            throw new UnsupportedOperationException("You need a context on NfcAdapter to use the  NFC extras APIs");
        }
    }

    @UnsupportedAppUsage
    public void attemptDeadServiceRecovery(Exception e) {
        Log.e(TAG, "NFC service dead - attempting to recover", e);
        INfcAdapter service = getServiceInterface();
        if (service == null) {
            Log.e(TAG, "could not retrieve NFC service during service recovery");
            return;
        }
        sService = service;
        try {
            sTagService = service.getNfcTagInterface();
            try {
                sCardEmulationService = service.getNfcCardEmulationInterface();
            } catch (RemoteException e2) {
                Log.e(TAG, "could not retrieve NFC card emulation service during service recovery");
            }
            try {
                sNfcFCardEmulationService = service.getNfcFCardEmulationInterface();
            } catch (RemoteException e3) {
                Log.e(TAG, "could not retrieve NFC-F card emulation service during service recovery");
            }
        } catch (RemoteException e4) {
            Log.e(TAG, "could not retrieve NFC tag service during service recovery");
        }
    }

    public boolean isEnabled() {
        try {
            return sService.getState() == 3;
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    @UnsupportedAppUsage
    public int getAdapterState() {
        try {
            return sService.getState();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return 1;
        }
    }

    @SystemApi
    public boolean enable() {
        try {
            return sService.enable();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    @SystemApi
    public boolean disable() {
        try {
            return sService.disable(true);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    @SystemApi
    public boolean disable(boolean persist) {
        try {
            return sService.disable(persist);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    public void pausePolling(int timeoutInMs) {
        try {
            sService.pausePolling(timeoutInMs);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public void resumePolling() {
        try {
            sService.resumePolling();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        if (r7 == null) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        if (r6 == null) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0012, code lost:
        r0 = r6.length;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0014, code lost:
        if (r1 >= r0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0016, code lost:
        r2 = r6[r1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0018, code lost:
        if (r2 == null) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        r3 = r2.getScheme();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001e, code lost:
        if (r3 == null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0026, code lost:
        if (r3.equalsIgnoreCase(android.content.ContentResolver.SCHEME_FILE) != false) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002e, code lost:
        if (r3.equalsIgnoreCase("content") == false) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0030, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003a, code lost:
        throw new java.lang.IllegalArgumentException("URI needs to have either scheme file or scheme content");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0042, code lost:
        throw new java.lang.NullPointerException("Uri not allowed to be null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0043, code lost:
        r5.mNfcActivityManager.setNdefPushContentUri(r7, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0050, code lost:
        throw new java.lang.NullPointerException("activity cannot be null");
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setBeamPushUris(android.net.Uri[] r6, android.app.Activity r7) {
        /*
            r5 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0051
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x0057 }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0057 }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x0057 }
            if (r7 == 0) goto L_0x0049
            if (r6 == 0) goto L_0x0043
            int r0 = r6.length
            r1 = 0
        L_0x0014:
            if (r1 >= r0) goto L_0x0043
            r2 = r6[r1]
            if (r2 == 0) goto L_0x003b
            java.lang.String r3 = r2.getScheme()
            if (r3 == 0) goto L_0x0033
            java.lang.String r4 = "file"
            boolean r4 = r3.equalsIgnoreCase(r4)
            if (r4 != 0) goto L_0x0030
            java.lang.String r4 = "content"
            boolean r4 = r3.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0033
        L_0x0030:
            int r1 = r1 + 1
            goto L_0x0014
        L_0x0033:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "URI needs to have either scheme file or scheme content"
            r0.<init>(r1)
            throw r0
        L_0x003b:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "Uri not allowed to be null"
            r0.<init>(r1)
            throw r0
        L_0x0043:
            android.nfc.NfcActivityManager r0 = r5.mNfcActivityManager
            r0.setNdefPushContentUri(r7, r6)
            return
        L_0x0049:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "activity cannot be null"
            r0.<init>(r1)
            throw r0
        L_0x0051:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0057 }
            r1.<init>()     // Catch:{ all -> 0x0057 }
            throw r1     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0057 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.setBeamPushUris(android.net.Uri[], android.app.Activity):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        if (r4 == null) goto L_0x0016;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        r2.mNfcActivityManager.setNdefPushContentUriCallback(r4, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001d, code lost:
        throw new java.lang.NullPointerException("activity cannot be null");
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setBeamPushUrisCallback(android.nfc.NfcAdapter.CreateBeamUrisCallback r3, android.app.Activity r4) {
        /*
            r2 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x0024 }
            if (r1 == 0) goto L_0x001e
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            if (r4 == 0) goto L_0x0016
            android.nfc.NfcActivityManager r0 = r2.mNfcActivityManager
            r0.setNdefPushContentUriCallback(r4, r3)
            return
        L_0x0016:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "activity cannot be null"
            r0.<init>(r1)
            throw r0
        L_0x001e:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0024 }
            r1.<init>()     // Catch:{ all -> 0x0024 }
            throw r1     // Catch:{ all -> 0x0024 }
        L_0x0024:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.setBeamPushUrisCallback(android.nfc.NfcAdapter$CreateBeamUrisCallback, android.app.Activity):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        r0 = getSdkVersion();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        if (r8 == null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r6.mNfcActivityManager.setNdefPushMessage(r8, r7, 0);
        r1 = r9.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        if (r3 >= r1) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001e, code lost:
        r4 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        if (r4 == null) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0022, code lost:
        r6.mNfcActivityManager.setNdefPushMessage(r4, r7, 0);
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
        throw new java.lang.NullPointerException("activities cannot contain null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        throw new java.lang.NullPointerException("activity cannot be null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        if (r0 < 16) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0040, code lost:
        android.util.Log.e(TAG, "Cannot call API with Activity that has already been destroyed", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0048, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setNdefPushMessage(android.nfc.NdefMessage r7, android.app.Activity r8, android.app.Activity... r9) {
        /*
            r6 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x004f }
            if (r1 == 0) goto L_0x0049
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x004f }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            int r0 = r6.getSdkVersion()
            if (r8 == 0) goto L_0x0033
            android.nfc.NfcActivityManager r1 = r6.mNfcActivityManager     // Catch:{ IllegalStateException -> 0x003b }
            r2 = 0
            r1.setNdefPushMessage(r8, r7, r2)     // Catch:{ IllegalStateException -> 0x003b }
            int r1 = r9.length     // Catch:{ IllegalStateException -> 0x003b }
            r3 = r2
        L_0x001c:
            if (r3 >= r1) goto L_0x0032
            r4 = r9[r3]     // Catch:{ IllegalStateException -> 0x003b }
            if (r4 == 0) goto L_0x002a
            android.nfc.NfcActivityManager r5 = r6.mNfcActivityManager     // Catch:{ IllegalStateException -> 0x003b }
            r5.setNdefPushMessage(r4, r7, r2)     // Catch:{ IllegalStateException -> 0x003b }
            int r3 = r3 + 1
            goto L_0x001c
        L_0x002a:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ IllegalStateException -> 0x003b }
            java.lang.String r2 = "activities cannot contain null"
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x003b }
            throw r1     // Catch:{ IllegalStateException -> 0x003b }
        L_0x0032:
            goto L_0x0047
        L_0x0033:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ IllegalStateException -> 0x003b }
            java.lang.String r2 = "activity cannot be null"
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x003b }
            throw r1     // Catch:{ IllegalStateException -> 0x003b }
        L_0x003b:
            r1 = move-exception
            r2 = 16
            if (r0 >= r2) goto L_0x0048
            java.lang.String r2 = "NFC"
            java.lang.String r3 = "Cannot call API with Activity that has already been destroyed"
            android.util.Log.e(r2, r3, r1)
        L_0x0047:
            return
        L_0x0048:
            throw r1
        L_0x0049:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x004f }
            r1.<init>()     // Catch:{ all -> 0x004f }
            throw r1     // Catch:{ all -> 0x004f }
        L_0x004f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.setNdefPushMessage(android.nfc.NdefMessage, android.app.Activity, android.app.Activity[]):void");
    }

    @SystemApi
    public void setNdefPushMessage(NdefMessage message, Activity activity, int flags) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        if (activity != null) {
            this.mNfcActivityManager.setNdefPushMessage(activity, message, flags);
            return;
        }
        throw new NullPointerException("activity cannot be null");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        r0 = getSdkVersion();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        if (r8 == null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r6.mNfcActivityManager.setNdefPushMessageCallback(r8, r7, 0);
        r1 = r9.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        if (r3 >= r1) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001e, code lost:
        r4 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        if (r4 == null) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0022, code lost:
        r6.mNfcActivityManager.setNdefPushMessageCallback(r4, r7, 0);
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
        throw new java.lang.NullPointerException("activities cannot contain null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        throw new java.lang.NullPointerException("activity cannot be null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        if (r0 < 16) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0040, code lost:
        android.util.Log.e(TAG, "Cannot call API with Activity that has already been destroyed", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0048, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setNdefPushMessageCallback(android.nfc.NfcAdapter.CreateNdefMessageCallback r7, android.app.Activity r8, android.app.Activity... r9) {
        /*
            r6 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x004f }
            if (r1 == 0) goto L_0x0049
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x004f }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            int r0 = r6.getSdkVersion()
            if (r8 == 0) goto L_0x0033
            android.nfc.NfcActivityManager r1 = r6.mNfcActivityManager     // Catch:{ IllegalStateException -> 0x003b }
            r2 = 0
            r1.setNdefPushMessageCallback(r8, r7, r2)     // Catch:{ IllegalStateException -> 0x003b }
            int r1 = r9.length     // Catch:{ IllegalStateException -> 0x003b }
            r3 = r2
        L_0x001c:
            if (r3 >= r1) goto L_0x0032
            r4 = r9[r3]     // Catch:{ IllegalStateException -> 0x003b }
            if (r4 == 0) goto L_0x002a
            android.nfc.NfcActivityManager r5 = r6.mNfcActivityManager     // Catch:{ IllegalStateException -> 0x003b }
            r5.setNdefPushMessageCallback(r4, r7, r2)     // Catch:{ IllegalStateException -> 0x003b }
            int r3 = r3 + 1
            goto L_0x001c
        L_0x002a:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ IllegalStateException -> 0x003b }
            java.lang.String r2 = "activities cannot contain null"
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x003b }
            throw r1     // Catch:{ IllegalStateException -> 0x003b }
        L_0x0032:
            goto L_0x0047
        L_0x0033:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ IllegalStateException -> 0x003b }
            java.lang.String r2 = "activity cannot be null"
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x003b }
            throw r1     // Catch:{ IllegalStateException -> 0x003b }
        L_0x003b:
            r1 = move-exception
            r2 = 16
            if (r0 >= r2) goto L_0x0048
            java.lang.String r2 = "NFC"
            java.lang.String r3 = "Cannot call API with Activity that has already been destroyed"
            android.util.Log.e(r2, r3, r1)
        L_0x0047:
            return
        L_0x0048:
            throw r1
        L_0x0049:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x004f }
            r1.<init>()     // Catch:{ all -> 0x004f }
            throw r1     // Catch:{ all -> 0x004f }
        L_0x004f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.setNdefPushMessageCallback(android.nfc.NfcAdapter$CreateNdefMessageCallback, android.app.Activity, android.app.Activity[]):void");
    }

    @UnsupportedAppUsage
    public void setNdefPushMessageCallback(CreateNdefMessageCallback callback, Activity activity, int flags) {
        if (activity != null) {
            this.mNfcActivityManager.setNdefPushMessageCallback(activity, callback, flags);
            return;
        }
        throw new NullPointerException("activity cannot be null");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        r0 = getSdkVersion();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        if (r7 == null) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r5.mNfcActivityManager.setOnNdefPushCompleteCallback(r7, r6);
        r1 = r8.length;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001b, code lost:
        if (r2 >= r1) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001d, code lost:
        r3 = r8[r2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001f, code lost:
        if (r3 == null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0021, code lost:
        r5.mNfcActivityManager.setOnNdefPushCompleteCallback(r3, r6);
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0030, code lost:
        throw new java.lang.NullPointerException("activities cannot contain null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
        throw new java.lang.NullPointerException("activity cannot be null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003d, code lost:
        if (r0 < 16) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003f, code lost:
        android.util.Log.e(TAG, "Cannot call API with Activity that has already been destroyed", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setOnNdefPushCompleteCallback(android.nfc.NfcAdapter.OnNdefPushCompleteCallback r6, android.app.Activity r7, android.app.Activity... r8) {
        /*
            r5 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x004e }
            if (r1 == 0) goto L_0x0048
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x004e }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            int r0 = r5.getSdkVersion()
            if (r7 == 0) goto L_0x0032
            android.nfc.NfcActivityManager r1 = r5.mNfcActivityManager     // Catch:{ IllegalStateException -> 0x003a }
            r1.setOnNdefPushCompleteCallback(r7, r6)     // Catch:{ IllegalStateException -> 0x003a }
            int r1 = r8.length     // Catch:{ IllegalStateException -> 0x003a }
            r2 = 0
        L_0x001b:
            if (r2 >= r1) goto L_0x0031
            r3 = r8[r2]     // Catch:{ IllegalStateException -> 0x003a }
            if (r3 == 0) goto L_0x0029
            android.nfc.NfcActivityManager r4 = r5.mNfcActivityManager     // Catch:{ IllegalStateException -> 0x003a }
            r4.setOnNdefPushCompleteCallback(r3, r6)     // Catch:{ IllegalStateException -> 0x003a }
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0029:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ IllegalStateException -> 0x003a }
            java.lang.String r2 = "activities cannot contain null"
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x003a }
            throw r1     // Catch:{ IllegalStateException -> 0x003a }
        L_0x0031:
            goto L_0x0046
        L_0x0032:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ IllegalStateException -> 0x003a }
            java.lang.String r2 = "activity cannot be null"
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x003a }
            throw r1     // Catch:{ IllegalStateException -> 0x003a }
        L_0x003a:
            r1 = move-exception
            r2 = 16
            if (r0 >= r2) goto L_0x0047
            java.lang.String r2 = "NFC"
            java.lang.String r3 = "Cannot call API with Activity that has already been destroyed"
            android.util.Log.e(r2, r3, r1)
        L_0x0046:
            return
        L_0x0047:
            throw r1
        L_0x0048:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x004e }
            r1.<init>()     // Catch:{ all -> 0x004e }
            throw r1     // Catch:{ all -> 0x004e }
        L_0x004e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.setOnNdefPushCompleteCallback(android.nfc.NfcAdapter$OnNdefPushCompleteCallback, android.app.Activity, android.app.Activity[]):void");
    }

    public void enableForegroundDispatch(Activity activity, PendingIntent intent, IntentFilter[] filters, String[][] techLists) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        if (activity == null || intent == null) {
            throw new NullPointerException();
        } else if (activity.isResumed()) {
            TechListParcel parcel = null;
            if (techLists != null) {
                try {
                    if (techLists.length > 0) {
                        parcel = new TechListParcel(techLists);
                    }
                } catch (RemoteException e) {
                    attemptDeadServiceRecovery(e);
                    return;
                }
            }
            ActivityThread.currentActivityThread().registerOnActivityPausedListener(activity, this.mForegroundDispatchListener);
            sService.setForegroundDispatch(intent, filters, parcel);
        } else {
            throw new IllegalStateException("Foreground dispatch can only be enabled when your activity is resumed");
        }
    }

    public void disableForegroundDispatch(Activity activity) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        ActivityThread.currentActivityThread().unregisterOnActivityPausedListener(activity, this.mForegroundDispatchListener);
        disableForegroundDispatchInternal(activity, false);
    }

    /* access modifiers changed from: package-private */
    public void disableForegroundDispatchInternal(Activity activity, boolean force) {
        try {
            sService.setForegroundDispatch((PendingIntent) null, (IntentFilter[]) null, (TechListParcel) null);
            if (force) {
                return;
            }
            if (!activity.isResumed()) {
                throw new IllegalStateException("You must disable foreground dispatching while your activity is still resumed");
            }
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public void enableReaderMode(Activity activity, ReaderCallback callback, int flags, Bundle extras) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        this.mNfcActivityManager.enableReaderMode(activity, callback, flags, extras);
    }

    public void disableReaderMode(Activity activity) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        this.mNfcActivityManager.disableReaderMode(activity);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (r5 == null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        enforceResumed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        sService.invokeBeam();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0019, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001c, code lost:
        android.util.Log.e(TAG, "invokeBeam: NFC process has died.");
        attemptDeadServiceRecovery(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0026, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002e, code lost:
        throw new java.lang.NullPointerException("activity may not be null.");
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean invokeBeam(android.app.Activity r5) {
        /*
            r4 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x0035 }
            if (r1 == 0) goto L_0x002f
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x0035 }
            r2 = 0
            if (r1 != 0) goto L_0x000e
            monitor-exit(r0)     // Catch:{ all -> 0x0035 }
            return r2
        L_0x000e:
            monitor-exit(r0)     // Catch:{ all -> 0x0035 }
            if (r5 == 0) goto L_0x0027
            r4.enforceResumed(r5)
            android.nfc.INfcAdapter r0 = sService     // Catch:{ RemoteException -> 0x001b }
            r0.invokeBeam()     // Catch:{ RemoteException -> 0x001b }
            r0 = 1
            return r0
        L_0x001b:
            r0 = move-exception
            java.lang.String r1 = "NFC"
            java.lang.String r3 = "invokeBeam: NFC process has died."
            android.util.Log.e(r1, r3)
            r4.attemptDeadServiceRecovery(r0)
            return r2
        L_0x0027:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "activity may not be null."
            r0.<init>(r1)
            throw r0
        L_0x002f:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0035 }
            r1.<init>()     // Catch:{ all -> 0x0035 }
            throw r1     // Catch:{ all -> 0x0035 }
        L_0x0035:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0035 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.invokeBeam(android.app.Activity):boolean");
    }

    public boolean invokeBeam(BeamShareData shareData) {
        try {
            Log.e(TAG, "invokeBeamInternal()");
            sService.invokeBeamInternal(shareData);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "invokeBeam: NFC process has died.");
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        if (r3 == null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        if (r4 == null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0012, code lost:
        enforceResumed(r3);
        r2.mNfcActivityManager.setNdefPushMessage(r3, r4, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
        throw new java.lang.NullPointerException();
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void enableForegroundNdefPush(android.app.Activity r3, android.nfc.NdefMessage r4) {
        /*
            r2 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x0028 }
            if (r1 == 0) goto L_0x0022
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x0028 }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0028 }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x0028 }
            if (r3 == 0) goto L_0x001c
            if (r4 == 0) goto L_0x001c
            r2.enforceResumed(r3)
            android.nfc.NfcActivityManager r0 = r2.mNfcActivityManager
            r1 = 0
            r0.setNdefPushMessage(r3, r4, r1)
            return
        L_0x001c:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            r0.<init>()
            throw r0
        L_0x0022:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0028 }
            r1.<init>()     // Catch:{ all -> 0x0028 }
            throw r1     // Catch:{ all -> 0x0028 }
        L_0x0028:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0028 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.enableForegroundNdefPush(android.app.Activity, android.nfc.NdefMessage):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000e, code lost:
        if (r4 == null) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        enforceResumed(r4);
        r3.mNfcActivityManager.setNdefPushMessage(r4, (android.nfc.NdefMessage) null, 0);
        r3.mNfcActivityManager.setNdefPushMessageCallback(r4, (android.nfc.NfcAdapter.CreateNdefMessageCallback) null, 0);
        r3.mNfcActivityManager.setOnNdefPushCompleteCallback(r4, (android.nfc.NfcAdapter.OnNdefPushCompleteCallback) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        throw new java.lang.NullPointerException();
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disableForegroundNdefPush(android.app.Activity r4) {
        /*
            r3 = this;
            java.lang.Class<android.nfc.NfcAdapter> r0 = android.nfc.NfcAdapter.class
            monitor-enter(r0)
            boolean r1 = sHasNfcFeature     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x002b
            boolean r1 = sHasBeamFeature     // Catch:{ all -> 0x0031 }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            return
        L_0x000d:
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            if (r4 == 0) goto L_0x0025
            r3.enforceResumed(r4)
            android.nfc.NfcActivityManager r0 = r3.mNfcActivityManager
            r1 = 0
            r2 = 0
            r0.setNdefPushMessage(r4, r2, r1)
            android.nfc.NfcActivityManager r0 = r3.mNfcActivityManager
            r0.setNdefPushMessageCallback(r4, r2, r1)
            android.nfc.NfcActivityManager r0 = r3.mNfcActivityManager
            r0.setOnNdefPushCompleteCallback(r4, r2)
            return
        L_0x0025:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            r0.<init>()
            throw r0
        L_0x002b:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0031 }
            r1.<init>()     // Catch:{ all -> 0x0031 }
            throw r1     // Catch:{ all -> 0x0031 }
        L_0x0031:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.NfcAdapter.disableForegroundNdefPush(android.app.Activity):void");
    }

    @SystemApi
    public boolean enableSecureNfc(boolean enable) {
        if (sHasNfcFeature) {
            try {
                return sService.setNfcSecure(enable);
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
                return false;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isSecureNfcSupported() {
        if (sHasNfcFeature) {
            try {
                return sService.deviceSupportsNfcSecure();
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
                return false;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isSecureNfcEnabled() {
        if (sHasNfcFeature) {
            try {
                return sService.isNfcSecureEnabled();
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
                return false;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @SystemApi
    public boolean enableNdefPush() {
        if (sHasNfcFeature) {
            try {
                return sService.enableNdefPush();
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
                return false;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @SystemApi
    public boolean disableNdefPush() {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        try {
            return sService.disableNdefPush();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    @Deprecated
    public boolean isNdefPushEnabled() {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            } else if (!sHasBeamFeature) {
                return false;
            } else {
                try {
                    return sService.isNdefPushEnabled();
                } catch (RemoteException e) {
                    attemptDeadServiceRecovery(e);
                    return false;
                }
            }
        }
    }

    public boolean ignore(Tag tag, int debounceMs, final OnTagRemovedListener tagRemovedListener, final Handler handler) {
        ITagRemovedCallback.Stub iListener = null;
        if (tagRemovedListener != null) {
            iListener = new ITagRemovedCallback.Stub() {
                public void onTagRemoved() throws RemoteException {
                    if (handler != null) {
                        handler.post(new Runnable() {
                            public void run() {
                                tagRemovedListener.onTagRemoved();
                            }
                        });
                    } else {
                        tagRemovedListener.onTagRemoved();
                    }
                    synchronized (NfcAdapter.this.mLock) {
                        NfcAdapter.this.mTagRemovedListener = null;
                    }
                }
            };
        }
        synchronized (this.mLock) {
            this.mTagRemovedListener = iListener;
        }
        try {
            return sService.ignore(tag.getServiceHandle(), debounceMs, iListener);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void dispatch(Tag tag) {
        if (tag != null) {
            try {
                sService.dispatch(tag);
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
            }
        } else {
            throw new NullPointerException("tag cannot be null");
        }
    }

    public void setP2pModes(int initiatorModes, int targetModes) {
        try {
            sService.setP2pModes(initiatorModes, targetModes);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    @SystemApi
    public boolean addNfcUnlockHandler(final NfcUnlockHandler unlockHandler, String[] tagTechnologies) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        if (tagTechnologies.length == 0) {
            return false;
        }
        try {
            synchronized (this.mLock) {
                if (this.mNfcUnlockHandlers.containsKey(unlockHandler)) {
                    sService.removeNfcUnlockHandler(this.mNfcUnlockHandlers.get(unlockHandler));
                    this.mNfcUnlockHandlers.remove(unlockHandler);
                }
                INfcUnlockHandler.Stub iHandler = new INfcUnlockHandler.Stub() {
                    public boolean onUnlockAttempted(Tag tag) throws RemoteException {
                        return unlockHandler.onUnlockAttempted(tag);
                    }
                };
                sService.addNfcUnlockHandler(iHandler, Tag.getTechCodesFromStrings(tagTechnologies));
                this.mNfcUnlockHandlers.put(unlockHandler, iHandler);
            }
            return true;
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "Unable to register LockscreenDispatch", e2);
            return false;
        }
    }

    @SystemApi
    public boolean removeNfcUnlockHandler(NfcUnlockHandler unlockHandler) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                throw new UnsupportedOperationException();
            }
        }
        try {
            synchronized (this.mLock) {
                if (this.mNfcUnlockHandlers.containsKey(unlockHandler)) {
                    sService.removeNfcUnlockHandler(this.mNfcUnlockHandlers.remove(unlockHandler));
                }
            }
            return true;
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    @UnsupportedAppUsage
    public INfcAdapterExtras getNfcAdapterExtrasInterface() {
        if (this.mContext != null) {
            try {
                return sService.getNfcAdapterExtrasInterface(this.mContext.getPackageName());
            } catch (RemoteException e) {
                attemptDeadServiceRecovery(e);
                return null;
            }
        } else {
            throw new UnsupportedOperationException("You need a context on NfcAdapter to use the  NFC extras APIs");
        }
    }

    /* access modifiers changed from: package-private */
    public void enforceResumed(Activity activity) {
        if (!activity.isResumed()) {
            throw new IllegalStateException("API cannot be called while activity is paused");
        }
    }

    /* access modifiers changed from: package-private */
    public int getSdkVersion() {
        if (this.mContext == null) {
            return 9;
        }
        return this.mContext.getApplicationInfo().targetSdkVersion;
    }
}
