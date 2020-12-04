package android.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import libcore.io.IoUtils;

public final class UiAutomation {
    private static final int CONNECTION_ID_UNDEFINED = -1;
    private static final long CONNECT_TIMEOUT_MILLIS = 5000;
    private static final boolean DEBUG = false;
    public static final int FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES = 1;
    private static final String LOG_TAG = UiAutomation.class.getSimpleName();
    public static final int ROTATION_FREEZE_0 = 0;
    public static final int ROTATION_FREEZE_180 = 2;
    public static final int ROTATION_FREEZE_270 = 3;
    public static final int ROTATION_FREEZE_90 = 1;
    public static final int ROTATION_FREEZE_CURRENT = -1;
    public static final int ROTATION_UNFREEZE = -2;
    private IAccessibilityServiceClient mClient;
    /* access modifiers changed from: private */
    public int mConnectionId = -1;
    /* access modifiers changed from: private */
    public final ArrayList<AccessibilityEvent> mEventQueue = new ArrayList<>();
    private int mFlags;
    private boolean mIsConnecting;
    private boolean mIsDestroyed;
    /* access modifiers changed from: private */
    public long mLastEventTimeMillis;
    /* access modifiers changed from: private */
    public final Handler mLocalCallbackHandler;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public OnAccessibilityEventListener mOnAccessibilityEventListener;
    private HandlerThread mRemoteCallbackThread;
    private final IUiAutomationConnection mUiAutomationConnection;
    /* access modifiers changed from: private */
    public boolean mWaitingForEventDelivery;

    public interface AccessibilityEventFilter {
        boolean accept(AccessibilityEvent accessibilityEvent);
    }

    public interface OnAccessibilityEventListener {
        void onAccessibilityEvent(AccessibilityEvent accessibilityEvent);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public UiAutomation(Looper looper, IUiAutomationConnection connection) {
        if (looper == null) {
            throw new IllegalArgumentException("Looper cannot be null!");
        } else if (connection != null) {
            this.mLocalCallbackHandler = new Handler(looper);
            this.mUiAutomationConnection = connection;
        } else {
            throw new IllegalArgumentException("Connection cannot be null!");
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void connect() {
        connect(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r10.mUiAutomationConnection.connect(r10.mClient, r11);
        r10.mFlags = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        r0 = r10.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r1 = android.os.SystemClock.uptimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        if (isConnectedLocked() == false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0047, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        r6 = 5000 - (android.os.SystemClock.uptimeMillis() - r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        if (r6 <= 0) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r10.mLock.wait(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0066, code lost:
        throw new java.lang.RuntimeException("Error while connecting UiAutomation");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0067, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r10.mIsConnecting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006a, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0076, code lost:
        throw new java.lang.RuntimeException("Error while connecting UiAutomation", r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(int r11) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            r10.throwIfConnectedLocked()     // Catch:{ all -> 0x0077 }
            boolean r1 = r10.mIsConnecting     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x000c
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            return
        L_0x000c:
            r1 = 1
            r10.mIsConnecting = r1     // Catch:{ all -> 0x0077 }
            android.os.HandlerThread r1 = new android.os.HandlerThread     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = "UiAutomation"
            r1.<init>(r2)     // Catch:{ all -> 0x0077 }
            r10.mRemoteCallbackThread = r1     // Catch:{ all -> 0x0077 }
            android.os.HandlerThread r1 = r10.mRemoteCallbackThread     // Catch:{ all -> 0x0077 }
            r1.start()     // Catch:{ all -> 0x0077 }
            android.app.UiAutomation$IAccessibilityServiceClientImpl r1 = new android.app.UiAutomation$IAccessibilityServiceClientImpl     // Catch:{ all -> 0x0077 }
            android.os.HandlerThread r2 = r10.mRemoteCallbackThread     // Catch:{ all -> 0x0077 }
            android.os.Looper r2 = r2.getLooper()     // Catch:{ all -> 0x0077 }
            r1.<init>(r2)     // Catch:{ all -> 0x0077 }
            r10.mClient = r1     // Catch:{ all -> 0x0077 }
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            android.app.IUiAutomationConnection r0 = r10.mUiAutomationConnection     // Catch:{ RemoteException -> 0x006e }
            android.accessibilityservice.IAccessibilityServiceClient r1 = r10.mClient     // Catch:{ RemoteException -> 0x006e }
            r0.connect(r1, r11)     // Catch:{ RemoteException -> 0x006e }
            r10.mFlags = r11     // Catch:{ RemoteException -> 0x006e }
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            long r1 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x006b }
        L_0x003c:
            r3 = 0
            boolean r4 = r10.isConnectedLocked()     // Catch:{ all -> 0x0067 }
            if (r4 == 0) goto L_0x0049
            r10.mIsConnecting = r3     // Catch:{ all -> 0x006b }
            monitor-exit(r0)     // Catch:{ all -> 0x006b }
            return
        L_0x0049:
            long r4 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0067 }
            long r4 = r4 - r1
            r6 = 5000(0x1388, double:2.4703E-320)
            long r6 = r6 - r4
            r8 = 0
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x005f
            java.lang.Object r8 = r10.mLock     // Catch:{ InterruptedException -> 0x005d }
            r8.wait(r6)     // Catch:{ InterruptedException -> 0x005d }
            goto L_0x005e
        L_0x005d:
            r3 = move-exception
        L_0x005e:
            goto L_0x003c
        L_0x005f:
            java.lang.RuntimeException r8 = new java.lang.RuntimeException     // Catch:{ all -> 0x0067 }
            java.lang.String r9 = "Error while connecting UiAutomation"
            r8.<init>(r9)     // Catch:{ all -> 0x0067 }
            throw r8     // Catch:{ all -> 0x0067 }
        L_0x0067:
            r4 = move-exception
            r10.mIsConnecting = r3     // Catch:{ all -> 0x006b }
            throw r4     // Catch:{ all -> 0x006b }
        L_0x006b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006b }
            throw r1
        L_0x006e:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Error while connecting UiAutomation"
            r1.<init>(r2, r0)
            throw r1
        L_0x0077:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.UiAutomation.connect(int):void");
    }

    public int getFlags() {
        return this.mFlags;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void disconnect() {
        synchronized (this.mLock) {
            if (!this.mIsConnecting) {
                throwIfNotConnectedLocked();
                this.mConnectionId = -1;
            } else {
                throw new IllegalStateException("Cannot call disconnect() while connecting!");
            }
        }
        try {
            this.mUiAutomationConnection.disconnect();
            this.mRemoteCallbackThread.quit();
            this.mRemoteCallbackThread = null;
        } catch (RemoteException re) {
            throw new RuntimeException("Error while disconnecting UiAutomation", re);
        } catch (Throwable th) {
            this.mRemoteCallbackThread.quit();
            this.mRemoteCallbackThread = null;
            throw th;
        }
    }

    public int getConnectionId() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return i;
    }

    public boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    public void setOnAccessibilityEventListener(OnAccessibilityEventListener listener) {
        synchronized (this.mLock) {
            this.mOnAccessibilityEventListener = listener;
        }
    }

    public void destroy() {
        disconnect();
        this.mIsDestroyed = true;
    }

    public void adoptShellPermissionIdentity() {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), (String[]) null);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error executing adopting shell permission identity!", re);
        }
    }

    public void adoptShellPermissionIdentity(String... permissions) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), permissions);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error executing adopting shell permission identity!", re);
        }
    }

    public void dropShellPermissionIdentity() {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.dropShellPermissionIdentity();
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error executing dropping shell permission identity!", re);
        }
    }

    public final boolean performGlobalAction(int action) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection == null) {
            return false;
        }
        try {
            return connection.performGlobalAction(action);
        } catch (RemoteException re) {
            Log.w(LOG_TAG, "Error while calling performGlobalAction", re);
            return false;
        }
    }

    public AccessibilityNodeInfo findFocus(int focus) {
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, focus);
    }

    public final AccessibilityServiceInfo getServiceInfo() {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection == null) {
            return null;
        }
        try {
            return connection.getServiceInfo();
        } catch (RemoteException re) {
            Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", re);
            return null;
        }
    }

    public final void setServiceInfo(AccessibilityServiceInfo info) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance().clearCache();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                connection.setServiceInfo(info);
            } catch (RemoteException re) {
                Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfo", re);
            }
        }
    }

    public List<AccessibilityWindowInfo> getWindows() {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getWindows(connectionId);
    }

    public AccessibilityNodeInfo getRootInActiveWindow() {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getRootInActiveWindow(connectionId);
    }

    public boolean injectInputEvent(InputEvent event, boolean sync) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.injectInputEvent(event, sync);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while injecting input event!", re);
            return false;
        }
    }

    public void syncInputTransactions() {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.syncInputTransactions();
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while syncing input transactions!", re);
        }
    }

    public boolean setRotation(int rotation) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        switch (rotation) {
            case -2:
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
                try {
                    this.mUiAutomationConnection.setRotation(rotation);
                    return true;
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error while setting rotation!", re);
                    return false;
                }
            default:
                throw new IllegalArgumentException("Invalid rotation.");
        }
    }

    /* JADX INFO: finally extract failed */
    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        	at java.util.ArrayList.get(ArrayList.java:433)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00eb A[LOOP:4: B:78:0x00e9->B:79:0x00eb, LOOP_END] */
    public android.view.accessibility.AccessibilityEvent executeAndWaitForEvent(java.lang.Runnable r20, android.app.UiAutomation.AccessibilityEventFilter r21, long r22) throws java.util.concurrent.TimeoutException {
        /*
            r19 = this;
            r1 = r19
            r2 = r22
            java.lang.Object r4 = r1.mLock
            monitor-enter(r4)
            r19.throwIfNotConnectedLocked()     // Catch:{ all -> 0x010c }
            java.util.ArrayList<android.view.accessibility.AccessibilityEvent> r0 = r1.mEventQueue     // Catch:{ all -> 0x010c }
            r0.clear()     // Catch:{ all -> 0x010c }
            r0 = 1
            r1.mWaitingForEventDelivery = r0     // Catch:{ all -> 0x010c }
            monitor-exit(r4)     // Catch:{ all -> 0x010c }
            long r5 = android.os.SystemClock.uptimeMillis()
            r20.run()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7 = r0
            r4 = 0
            long r8 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00e0 }
        L_0x0025:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00e0 }
            r0.<init>()     // Catch:{ all -> 0x00e0 }
            r10 = r0
            java.lang.Object r11 = r1.mLock     // Catch:{ all -> 0x00e0 }
            monitor-enter(r11)     // Catch:{ all -> 0x00e0 }
            java.util.ArrayList<android.view.accessibility.AccessibilityEvent> r0 = r1.mEventQueue     // Catch:{ all -> 0x00d6 }
            r10.addAll(r0)     // Catch:{ all -> 0x00d6 }
            java.util.ArrayList<android.view.accessibility.AccessibilityEvent> r0 = r1.mEventQueue     // Catch:{ all -> 0x00d6 }
            r0.clear()     // Catch:{ all -> 0x00d6 }
            monitor-exit(r11)     // Catch:{ all -> 0x00d6 }
        L_0x0039:
            boolean r0 = r10.isEmpty()     // Catch:{ all -> 0x00e0 }
            if (r0 != 0) goto L_0x008a
            java.lang.Object r0 = r10.remove(r4)     // Catch:{ all -> 0x0085 }
            android.view.accessibility.AccessibilityEvent r0 = (android.view.accessibility.AccessibilityEvent) r0     // Catch:{ all -> 0x0085 }
            r11 = r0
            long r12 = r11.getEventTime()     // Catch:{ all -> 0x0085 }
            int r0 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x004f
            goto L_0x0039
        L_0x004f:
            r12 = r21
            boolean r0 = r12.accept(r11)     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x007f
            int r13 = r7.size()
            r0 = r4
        L_0x005d:
            if (r0 >= r13) goto L_0x006b
            java.lang.Object r14 = r7.get(r0)
            android.view.accessibility.AccessibilityEvent r14 = (android.view.accessibility.AccessibilityEvent) r14
            r14.recycle()
            int r0 = r0 + 1
            goto L_0x005d
        L_0x006b:
            java.lang.Object r14 = r1.mLock
            monitor-enter(r14)
            r1.mWaitingForEventDelivery = r4     // Catch:{ all -> 0x007c }
            java.util.ArrayList<android.view.accessibility.AccessibilityEvent> r0 = r1.mEventQueue     // Catch:{ all -> 0x007c }
            r0.clear()     // Catch:{ all -> 0x007c }
            java.lang.Object r0 = r1.mLock     // Catch:{ all -> 0x007c }
            r0.notifyAll()     // Catch:{ all -> 0x007c }
            monitor-exit(r14)     // Catch:{ all -> 0x007c }
            return r11
        L_0x007c:
            r0 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x007c }
            throw r0
        L_0x007f:
            r7.add(r11)     // Catch:{ all -> 0x0083 }
            goto L_0x0039
        L_0x0083:
            r0 = move-exception
            goto L_0x0088
        L_0x0085:
            r0 = move-exception
            r12 = r21
        L_0x0088:
            r15 = r5
            goto L_0x00e4
        L_0x008a:
            r12 = r21
            long r13 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00d4 }
            long r13 = r13 - r8
            r15 = r5
            long r4 = r2 - r13
            r17 = 0
            int r0 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x00b5
            java.lang.Object r6 = r1.mLock     // Catch:{ all -> 0x00dc }
            monitor-enter(r6)     // Catch:{ all -> 0x00dc }
            java.util.ArrayList<android.view.accessibility.AccessibilityEvent> r0 = r1.mEventQueue     // Catch:{ all -> 0x00b2 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x00b2 }
            if (r0 == 0) goto L_0x00ac
            java.lang.Object r0 = r1.mLock     // Catch:{ InterruptedException -> 0x00ab }
            r0.wait(r4)     // Catch:{ InterruptedException -> 0x00ab }
            goto L_0x00ac
        L_0x00ab:
            r0 = move-exception
        L_0x00ac:
            monitor-exit(r6)     // Catch:{ all -> 0x00b2 }
            r5 = r15
            r4 = 0
            goto L_0x0025
        L_0x00b2:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x00b2 }
            throw r0     // Catch:{ all -> 0x00dc }
        L_0x00b5:
            java.util.concurrent.TimeoutException r0 = new java.util.concurrent.TimeoutException     // Catch:{ all -> 0x00dc }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00dc }
            r6.<init>()     // Catch:{ all -> 0x00dc }
            java.lang.String r11 = "Expected event not received within: "
            r6.append(r11)     // Catch:{ all -> 0x00dc }
            r6.append(r2)     // Catch:{ all -> 0x00dc }
            java.lang.String r11 = " ms among: "
            r6.append(r11)     // Catch:{ all -> 0x00dc }
            r6.append(r7)     // Catch:{ all -> 0x00dc }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00dc }
            r0.<init>(r6)     // Catch:{ all -> 0x00dc }
            throw r0     // Catch:{ all -> 0x00dc }
        L_0x00d4:
            r0 = move-exception
            goto L_0x00e3
        L_0x00d6:
            r0 = move-exception
            r12 = r21
            r15 = r5
        L_0x00da:
            monitor-exit(r11)     // Catch:{ all -> 0x00de }
            throw r0     // Catch:{ all -> 0x00dc }
        L_0x00dc:
            r0 = move-exception
            goto L_0x00e4
        L_0x00de:
            r0 = move-exception
            goto L_0x00da
        L_0x00e0:
            r0 = move-exception
            r12 = r21
        L_0x00e3:
            r15 = r5
        L_0x00e4:
            int r5 = r7.size()
            r4 = 0
        L_0x00e9:
            if (r4 >= r5) goto L_0x00f7
            java.lang.Object r6 = r7.get(r4)
            android.view.accessibility.AccessibilityEvent r6 = (android.view.accessibility.AccessibilityEvent) r6
            r6.recycle()
            int r4 = r4 + 1
            goto L_0x00e9
        L_0x00f7:
            java.lang.Object r6 = r1.mLock
            monitor-enter(r6)
            r4 = 0
            r1.mWaitingForEventDelivery = r4     // Catch:{ all -> 0x0109 }
            java.util.ArrayList<android.view.accessibility.AccessibilityEvent> r4 = r1.mEventQueue     // Catch:{ all -> 0x0109 }
            r4.clear()     // Catch:{ all -> 0x0109 }
            java.lang.Object r4 = r1.mLock     // Catch:{ all -> 0x0109 }
            r4.notifyAll()     // Catch:{ all -> 0x0109 }
            monitor-exit(r6)     // Catch:{ all -> 0x0109 }
            throw r0
        L_0x0109:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0109 }
            throw r0
        L_0x010c:
            r0 = move-exception
            r12 = r21
        L_0x010f:
            monitor-exit(r4)     // Catch:{ all -> 0x0111 }
            throw r0
        L_0x0111:
            r0 = move-exception
            goto L_0x010f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.UiAutomation.executeAndWaitForEvent(java.lang.Runnable, android.app.UiAutomation$AccessibilityEventFilter, long):android.view.accessibility.AccessibilityEvent");
    }

    public void waitForIdle(long idleTimeoutMillis, long globalTimeoutMillis) throws TimeoutException {
        long j = idleTimeoutMillis;
        long j2 = globalTimeoutMillis;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            long startTimeMillis = SystemClock.uptimeMillis();
            long j3 = 0;
            if (this.mLastEventTimeMillis <= 0) {
                this.mLastEventTimeMillis = startTimeMillis;
            }
            while (true) {
                long currentTimeMillis = SystemClock.uptimeMillis();
                if (j2 - (currentTimeMillis - startTimeMillis) > j3) {
                    long startTimeMillis2 = startTimeMillis;
                    long remainingIdleTimeMillis = j - (currentTimeMillis - this.mLastEventTimeMillis);
                    if (remainingIdleTimeMillis > 0) {
                        try {
                            this.mLock.wait(remainingIdleTimeMillis);
                        } catch (InterruptedException e) {
                        }
                        j3 = 0;
                        startTimeMillis = startTimeMillis2;
                    }
                } else {
                    throw new TimeoutException("No idle state with idle timeout: " + j + " within global timeout: " + j2);
                }
            }
        }
    }

    public Bitmap takeScreenshot() {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        Display display = DisplayManagerGlobal.getInstance().getRealDisplay(0);
        Point displaySize = new Point();
        display.getRealSize(displaySize);
        try {
            Bitmap screenShot = this.mUiAutomationConnection.takeScreenshot(new Rect(0, 0, displaySize.x, displaySize.y), display.getRotation());
            if (screenShot == null) {
                return null;
            }
            screenShot.setHasAlpha(false);
            return screenShot;
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while taking screnshot!", re);
            return null;
        }
    }

    public void setRunAsMonkey(boolean enable) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            ActivityManager.getService().setUserIsMonkey(enable);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while setting run as monkey!", re);
        }
    }

    public boolean clearWindowContentFrameStats(int windowId) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.clearWindowContentFrameStats(windowId);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error clearing window content frame stats!", re);
            return false;
        }
    }

    public WindowContentFrameStats getWindowContentFrameStats(int windowId) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowContentFrameStats(windowId);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error getting window content frame stats!", re);
            return null;
        }
    }

    public void clearWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.clearWindowAnimationFrameStats();
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error clearing window animation frame stats!", re);
        }
    }

    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowAnimationFrameStats();
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error getting window animation frame stats!", re);
            return null;
        }
    }

    public void grantRuntimePermission(String packageName, String permission) {
        grantRuntimePermissionAsUser(packageName, permission, Process.myUserHandle());
    }

    @Deprecated
    public boolean grantRuntimePermission(String packageName, String permission, UserHandle userHandle) {
        grantRuntimePermissionAsUser(packageName, permission, userHandle);
        return true;
    }

    public void grantRuntimePermissionAsUser(String packageName, String permission, UserHandle userHandle) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.grantRuntimePermission(packageName, permission, userHandle.getIdentifier());
        } catch (Exception e) {
            throw new SecurityException("Error granting runtime permission", e);
        }
    }

    public void revokeRuntimePermission(String packageName, String permission) {
        revokeRuntimePermissionAsUser(packageName, permission, Process.myUserHandle());
    }

    @Deprecated
    public boolean revokeRuntimePermission(String packageName, String permission, UserHandle userHandle) {
        revokeRuntimePermissionAsUser(packageName, permission, userHandle);
        return true;
    }

    public void revokeRuntimePermissionAsUser(String packageName, String permission, UserHandle userHandle) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.revokeRuntimePermission(packageName, permission, userHandle.getIdentifier());
        } catch (Exception e) {
            throw new SecurityException("Error granting runtime permission", e);
        }
    }

    public ParcelFileDescriptor executeShellCommand(String command) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        warnIfBetterCommand(command);
        ParcelFileDescriptor source = null;
        ParcelFileDescriptor sink = null;
        try {
            ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
            source = pipe[0];
            sink = pipe[1];
            this.mUiAutomationConnection.executeShellCommand(command, sink, (ParcelFileDescriptor) null);
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Error executing shell command!", ioe);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error executing shell command!", re);
        } catch (Throwable th) {
            IoUtils.closeQuietly(sink);
            throw th;
        }
        IoUtils.closeQuietly(sink);
        return source;
    }

    public ParcelFileDescriptor[] executeShellCommandRw(String command) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        warnIfBetterCommand(command);
        ParcelFileDescriptor source_read = null;
        ParcelFileDescriptor sink_read = null;
        ParcelFileDescriptor source_write = null;
        ParcelFileDescriptor sink_write = null;
        try {
            ParcelFileDescriptor[] pipe_read = ParcelFileDescriptor.createPipe();
            source_read = pipe_read[0];
            sink_read = pipe_read[1];
            ParcelFileDescriptor[] pipe_write = ParcelFileDescriptor.createPipe();
            source_write = pipe_write[0];
            sink_write = pipe_write[1];
            this.mUiAutomationConnection.executeShellCommand(command, sink_read, source_write);
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Error executing shell command!", ioe);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error executing shell command!", re);
        } catch (Throwable th) {
            IoUtils.closeQuietly(sink_read);
            IoUtils.closeQuietly(source_write);
            throw th;
        }
        IoUtils.closeQuietly(sink_read);
        IoUtils.closeQuietly(source_write);
        return new ParcelFileDescriptor[]{source_read, sink_write};
    }

    private boolean isConnectedLocked() {
        return this.mConnectionId != -1;
    }

    private void throwIfConnectedLocked() {
        if (this.mConnectionId != -1) {
            throw new IllegalStateException("UiAutomation not connected!");
        }
    }

    private void throwIfNotConnectedLocked() {
        if (!isConnectedLocked()) {
            throw new IllegalStateException("UiAutomation not connected!");
        }
    }

    private void warnIfBetterCommand(String cmd) {
        if (cmd.startsWith("pm grant ")) {
            Log.w(LOG_TAG, "UiAutomation.grantRuntimePermission() is more robust and should be used instead of 'pm grant'");
        } else if (cmd.startsWith("pm revoke ")) {
            Log.w(LOG_TAG, "UiAutomation.revokeRuntimePermission() is more robust and should be used instead of 'pm revoke'");
        }
    }

    private class IAccessibilityServiceClientImpl extends AccessibilityService.IAccessibilityServiceClientWrapper {
        public IAccessibilityServiceClientImpl(Looper looper) {
            super((Context) null, looper, new AccessibilityService.Callbacks() {
                public void init(int connectionId, IBinder windowToken) {
                    synchronized (UiAutomation.this.mLock) {
                        int unused = UiAutomation.this.mConnectionId = connectionId;
                        UiAutomation.this.mLock.notifyAll();
                    }
                }

                public void onServiceConnected() {
                }

                public void onInterrupt() {
                }

                public boolean onGesture(int gestureId) {
                    return false;
                }

                public void onAccessibilityEvent(AccessibilityEvent event) {
                    OnAccessibilityEventListener listener;
                    synchronized (UiAutomation.this.mLock) {
                        long unused = UiAutomation.this.mLastEventTimeMillis = event.getEventTime();
                        if (UiAutomation.this.mWaitingForEventDelivery) {
                            UiAutomation.this.mEventQueue.add(AccessibilityEvent.obtain(event));
                        }
                        UiAutomation.this.mLock.notifyAll();
                        listener = UiAutomation.this.mOnAccessibilityEventListener;
                    }
                    if (listener != null) {
                        UiAutomation.this.mLocalCallbackHandler.sendMessage(PooledLambda.obtainMessage($$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs.INSTANCE, listener, AccessibilityEvent.obtain(event)));
                    }
                }

                public boolean onKeyEvent(KeyEvent event) {
                    return false;
                }

                public void onMagnificationChanged(int displayId, Region region, float scale, float centerX, float centerY) {
                }

                public void onSoftKeyboardShowModeChanged(int showMode) {
                }

                public void onPerformGestureResult(int sequence, boolean completedSuccessfully) {
                }

                public void onFingerprintCapturingGesturesChanged(boolean active) {
                }

                public void onFingerprintGesture(int gesture) {
                }

                public void onAccessibilityButtonClicked() {
                }

                public void onAccessibilityButtonAvailabilityChanged(boolean available) {
                }
            });
        }
    }
}
