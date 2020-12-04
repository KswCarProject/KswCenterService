package com.android.internal.os;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.SystemClock;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import dalvik.system.ZygoteHooks;
import java.io.FileDescriptor;
import java.io.IOException;

class ZygoteServer {
    public static final String TAG = "ZygoteServer";
    private static final String USAP_POOL_SIZE_MAX_DEFAULT = "10";
    private static final int USAP_POOL_SIZE_MAX_LIMIT = 100;
    private static final String USAP_POOL_SIZE_MIN_DEFAULT = "1";
    private static final int USAP_POOL_SIZE_MIN_LIMIT = 1;
    private boolean mCloseSocketFd;
    private boolean mIsFirstPropertyCheck;
    private boolean mIsForkChild;
    private long mLastPropCheckTimestamp;
    private boolean mUsapPoolEnabled;
    private FileDescriptor mUsapPoolEventFD;
    private int mUsapPoolRefillThreshold;
    private int mUsapPoolSizeMax;
    private int mUsapPoolSizeMin;
    private LocalServerSocket mUsapPoolSocket;
    private final boolean mUsapPoolSupported;
    private LocalServerSocket mZygoteSocket;

    ZygoteServer() {
        this.mUsapPoolEnabled = false;
        this.mUsapPoolSizeMax = 0;
        this.mUsapPoolSizeMin = 0;
        this.mUsapPoolRefillThreshold = 0;
        this.mIsFirstPropertyCheck = true;
        this.mLastPropCheckTimestamp = 0;
        this.mUsapPoolEventFD = null;
        this.mZygoteSocket = null;
        this.mUsapPoolSocket = null;
        this.mUsapPoolSupported = false;
    }

    ZygoteServer(boolean isPrimaryZygote) {
        this.mUsapPoolEnabled = false;
        this.mUsapPoolSizeMax = 0;
        this.mUsapPoolSizeMin = 0;
        this.mUsapPoolRefillThreshold = 0;
        this.mIsFirstPropertyCheck = true;
        this.mLastPropCheckTimestamp = 0;
        this.mUsapPoolEventFD = Zygote.getUsapPoolEventFD();
        if (isPrimaryZygote) {
            this.mZygoteSocket = Zygote.createManagedSocketFromInitSocket(Zygote.PRIMARY_SOCKET_NAME);
            this.mUsapPoolSocket = Zygote.createManagedSocketFromInitSocket(Zygote.USAP_POOL_PRIMARY_SOCKET_NAME);
        } else {
            this.mZygoteSocket = Zygote.createManagedSocketFromInitSocket(Zygote.SECONDARY_SOCKET_NAME);
            this.mUsapPoolSocket = Zygote.createManagedSocketFromInitSocket(Zygote.USAP_POOL_SECONDARY_SOCKET_NAME);
        }
        fetchUsapPoolPolicyProps();
        this.mUsapPoolSupported = true;
    }

    /* access modifiers changed from: package-private */
    public void setForkChild() {
        this.mIsForkChild = true;
    }

    public boolean isUsapPoolEnabled() {
        return this.mUsapPoolEnabled;
    }

    /* access modifiers changed from: package-private */
    public void registerServerSocketAtAbstractName(String socketName) {
        if (this.mZygoteSocket == null) {
            try {
                this.mZygoteSocket = new LocalServerSocket(socketName);
                this.mCloseSocketFd = false;
            } catch (IOException ex) {
                throw new RuntimeException("Error binding to abstract socket '" + socketName + "'", ex);
            }
        }
    }

    private ZygoteConnection acceptCommandPeer(String abiList) {
        try {
            return createNewConnection(this.mZygoteSocket.accept(), abiList);
        } catch (IOException ex) {
            throw new RuntimeException("IOException during accept()", ex);
        }
    }

    /* access modifiers changed from: protected */
    public ZygoteConnection createNewConnection(LocalSocket socket, String abiList) throws IOException {
        return new ZygoteConnection(socket, abiList);
    }

    /* access modifiers changed from: package-private */
    public void closeServerSocket() {
        try {
            if (this.mZygoteSocket != null) {
                FileDescriptor fd = this.mZygoteSocket.getFileDescriptor();
                this.mZygoteSocket.close();
                if (fd != null && this.mCloseSocketFd) {
                    Os.close(fd);
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "Zygote:  error closing sockets", ex);
        } catch (ErrnoException ex2) {
            Log.e(TAG, "Zygote:  error closing descriptor", ex2);
        }
        this.mZygoteSocket = null;
    }

    /* access modifiers changed from: package-private */
    public FileDescriptor getZygoteSocketFileDescriptor() {
        return this.mZygoteSocket.getFileDescriptor();
    }

    private void fetchUsapPoolPolicyProps() {
        if (this.mUsapPoolSupported) {
            String usapPoolSizeMaxPropString = Zygote.getConfigurationProperty(ZygoteConfig.USAP_POOL_SIZE_MAX, USAP_POOL_SIZE_MAX_DEFAULT);
            if (!usapPoolSizeMaxPropString.isEmpty()) {
                this.mUsapPoolSizeMax = Integer.min(Integer.parseInt(usapPoolSizeMaxPropString), 100);
            }
            String usapPoolSizeMinPropString = Zygote.getConfigurationProperty(ZygoteConfig.USAP_POOL_SIZE_MIN, "1");
            if (!usapPoolSizeMinPropString.isEmpty()) {
                this.mUsapPoolSizeMin = Integer.max(Integer.parseInt(usapPoolSizeMinPropString), 1);
            }
            String usapPoolRefillThresholdPropString = Zygote.getConfigurationProperty(ZygoteConfig.USAP_POOL_REFILL_THRESHOLD, Integer.toString(this.mUsapPoolSizeMax / 2));
            if (!usapPoolRefillThresholdPropString.isEmpty()) {
                this.mUsapPoolRefillThreshold = Integer.min(Integer.parseInt(usapPoolRefillThresholdPropString), this.mUsapPoolSizeMax);
            }
            if (this.mUsapPoolSizeMin >= this.mUsapPoolSizeMax) {
                Log.w(TAG, "The max size of the USAP pool must be greater than the minimum size.  Restoring default values.");
                this.mUsapPoolSizeMax = Integer.parseInt(USAP_POOL_SIZE_MAX_DEFAULT);
                this.mUsapPoolSizeMin = Integer.parseInt("1");
                this.mUsapPoolRefillThreshold = this.mUsapPoolSizeMax / 2;
            }
        }
    }

    private void fetchUsapPoolPolicyPropsWithMinInterval() {
        long currentTimestamp = SystemClock.elapsedRealtime();
        if (this.mIsFirstPropertyCheck || currentTimestamp - this.mLastPropCheckTimestamp >= 60000) {
            this.mIsFirstPropertyCheck = false;
            this.mLastPropCheckTimestamp = currentTimestamp;
            fetchUsapPoolPolicyProps();
        }
    }

    /* access modifiers changed from: package-private */
    public Runnable fillUsapPool(int[] sessionSocketRawFDs) {
        Trace.traceBegin(64, "Zygote:FillUsapPool");
        fetchUsapPoolPolicyPropsWithMinInterval();
        int usapPoolCount = Zygote.getUsapPoolCount();
        int numUsapsToSpawn = this.mUsapPoolSizeMax - usapPoolCount;
        if (usapPoolCount < this.mUsapPoolSizeMin || numUsapsToSpawn >= this.mUsapPoolRefillThreshold) {
            ZygoteHooks.preFork();
            Zygote.resetNicePriority();
            while (true) {
                int usapPoolCount2 = usapPoolCount + 1;
                if (usapPoolCount >= this.mUsapPoolSizeMax) {
                    ZygoteHooks.postForkCommon();
                    Log.i(Zygote.PRIMARY_SOCKET_NAME, "Filled the USAP pool. New USAPs: " + numUsapsToSpawn);
                    int i = usapPoolCount2;
                    break;
                }
                Runnable caller = Zygote.forkUsap(this.mUsapPoolSocket, sessionSocketRawFDs);
                if (caller != null) {
                    return caller;
                }
                usapPoolCount = usapPoolCount2;
            }
        }
        Trace.traceEnd(64);
        return null;
    }

    /* access modifiers changed from: package-private */
    public Runnable setUsapPoolStatus(boolean newStatus, LocalSocket sessionSocket) {
        if (!this.mUsapPoolSupported) {
            Log.w(TAG, "Attempting to enable a USAP pool for a Zygote that doesn't support it.");
            return null;
        } else if (this.mUsapPoolEnabled == newStatus) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("USAP Pool status change: ");
            sb.append(newStatus ? "ENABLED" : "DISABLED");
            Log.i(TAG, sb.toString());
            this.mUsapPoolEnabled = newStatus;
            if (newStatus) {
                return fillUsapPool(new int[]{sessionSocket.getFileDescriptor().getInt$()});
            }
            Zygote.emptyUsapPool();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01af, code lost:
        if (r11 == false) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01b1, code lost:
        r6 = fillUsapPool(r2.subList(1, r2.size()).stream().mapToInt(com.android.internal.os.$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE.INSTANCE).toArray());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01cc, code lost:
        if (r6 == null) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01ce, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Runnable runSelectLoop(java.lang.String r19) {
        /*
            r18 = this;
            r1 = r18
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2 = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = r0
            android.net.LocalServerSocket r0 = r1.mZygoteSocket
            java.io.FileDescriptor r0 = r0.getFileDescriptor()
            r2.add(r0)
            r0 = 0
            r3.add(r0)
        L_0x001b:
            r18.fetchUsapPoolPolicyPropsWithMinInterval()
            r0 = 0
            r4 = 0
            boolean r5 = r1.mUsapPoolEnabled
            r6 = 1
            if (r5 == 0) goto L_0x0034
            int[] r0 = com.android.internal.os.Zygote.getUsapPipeFDs()
            int r5 = r2.size()
            int r5 = r5 + r6
            int r7 = r0.length
            int r5 = r5 + r7
            android.system.StructPollfd[] r4 = new android.system.StructPollfd[r5]
        L_0x0032:
            r5 = r0
            goto L_0x003b
        L_0x0034:
            int r5 = r2.size()
            android.system.StructPollfd[] r4 = new android.system.StructPollfd[r5]
            goto L_0x0032
        L_0x003b:
            r0 = 0
            java.util.Iterator r7 = r2.iterator()
        L_0x0040:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0061
            java.lang.Object r8 = r7.next()
            java.io.FileDescriptor r8 = (java.io.FileDescriptor) r8
            android.system.StructPollfd r9 = new android.system.StructPollfd
            r9.<init>()
            r4[r0] = r9
            r9 = r4[r0]
            r9.fd = r8
            r9 = r4[r0]
            int r10 = android.system.OsConstants.POLLIN
            short r10 = (short) r10
            r9.events = r10
            int r0 = r0 + 1
            goto L_0x0040
        L_0x0061:
            r7 = r0
            boolean r8 = r1.mUsapPoolEnabled
            r9 = 0
            if (r8 == 0) goto L_0x00a3
            android.system.StructPollfd r8 = new android.system.StructPollfd
            r8.<init>()
            r4[r0] = r8
            r8 = r4[r0]
            java.io.FileDescriptor r10 = r1.mUsapPoolEventFD
            r8.fd = r10
            r8 = r4[r0]
            int r10 = android.system.OsConstants.POLLIN
            short r10 = (short) r10
            r8.events = r10
            int r0 = r0 + 1
            int r8 = r5.length
            r10 = r0
            r0 = r9
        L_0x0080:
            if (r0 >= r8) goto L_0x00a4
            r11 = r5[r0]
            java.io.FileDescriptor r12 = new java.io.FileDescriptor
            r12.<init>()
            r12.setInt$(r11)
            android.system.StructPollfd r13 = new android.system.StructPollfd
            r13.<init>()
            r4[r10] = r13
            r13 = r4[r10]
            r13.fd = r12
            r13 = r4[r10]
            int r14 = android.system.OsConstants.POLLIN
            short r14 = (short) r14
            r13.events = r14
            int r10 = r10 + 1
            int r0 = r0 + 1
            goto L_0x0080
        L_0x00a3:
            r10 = r0
        L_0x00a4:
            r8 = -1
            android.system.Os.poll(r4, r8)     // Catch:{ ErrnoException -> 0x01d1 }
            r0 = r9
        L_0x00aa:
            r11 = r0
            int r10 = r10 + r8
            if (r10 < 0) goto L_0x01af
            r0 = r4[r10]
            short r0 = r0.revents
            int r12 = android.system.OsConstants.POLLIN
            r0 = r0 & r12
            if (r0 != 0) goto L_0x00b9
            goto L_0x01aa
        L_0x00b9:
            if (r10 != 0) goto L_0x00cb
            com.android.internal.os.ZygoteConnection r0 = r18.acceptCommandPeer(r19)
            r3.add(r0)
            java.io.FileDescriptor r12 = r0.getFileDescriptor()
            r2.add(r12)
            goto L_0x01aa
        L_0x00cb:
            if (r10 >= r7) goto L_0x012c
            java.lang.Object r0 = r3.get(r10)     // Catch:{ Exception -> 0x0108 }
            com.android.internal.os.ZygoteConnection r0 = (com.android.internal.os.ZygoteConnection) r0     // Catch:{ Exception -> 0x0108 }
            java.lang.Runnable r12 = r0.processOneCommand(r1)     // Catch:{ Exception -> 0x0108 }
            boolean r13 = r1.mIsForkChild     // Catch:{ Exception -> 0x0108 }
            if (r13 == 0) goto L_0x00e9
            if (r12 == 0) goto L_0x00e1
            r1.mIsForkChild = r9
            return r12
        L_0x00e1:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x0108 }
            java.lang.String r14 = "command == null"
            r13.<init>(r14)     // Catch:{ Exception -> 0x0108 }
            throw r13     // Catch:{ Exception -> 0x0108 }
        L_0x00e9:
            if (r12 != 0) goto L_0x00fe
            boolean r13 = r0.isClosedByPeer()     // Catch:{ Exception -> 0x0108 }
            if (r13 == 0) goto L_0x00fa
            r0.closeSocket()     // Catch:{ Exception -> 0x0108 }
            r3.remove(r10)     // Catch:{ Exception -> 0x0108 }
            r2.remove(r10)     // Catch:{ Exception -> 0x0108 }
        L_0x00fa:
            r1.mIsForkChild = r9
            goto L_0x01aa
        L_0x00fe:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x0108 }
            java.lang.String r14 = "command != null"
            r13.<init>(r14)     // Catch:{ Exception -> 0x0108 }
            throw r13     // Catch:{ Exception -> 0x0108 }
        L_0x0106:
            r0 = move-exception
            goto L_0x0129
        L_0x0108:
            r0 = move-exception
            boolean r12 = r1.mIsForkChild     // Catch:{ all -> 0x0106 }
            if (r12 != 0) goto L_0x0121
            java.lang.String r12 = "ZygoteServer"
            java.lang.String r13 = "Exception executing zygote command: "
            android.util.Slog.e(r12, r13, r0)     // Catch:{ all -> 0x0106 }
            java.lang.Object r12 = r3.remove(r10)     // Catch:{ all -> 0x0106 }
            com.android.internal.os.ZygoteConnection r12 = (com.android.internal.os.ZygoteConnection) r12     // Catch:{ all -> 0x0106 }
            r12.closeSocket()     // Catch:{ all -> 0x0106 }
            r2.remove(r10)     // Catch:{ all -> 0x0106 }
            goto L_0x00fa
        L_0x0121:
            java.lang.String r6 = "ZygoteServer"
            java.lang.String r8 = "Caught post-fork exception in child process."
            android.util.Log.e(r6, r8, r0)     // Catch:{ all -> 0x0106 }
            throw r0     // Catch:{ all -> 0x0106 }
        L_0x0129:
            r1.mIsForkChild = r9
            throw r0
        L_0x012c:
            r12 = -1
            r0 = 8
            byte[] r14 = new byte[r0]     // Catch:{ Exception -> 0x0171 }
            r15 = r4[r10]     // Catch:{ Exception -> 0x0171 }
            java.io.FileDescriptor r15 = r15.fd     // Catch:{ Exception -> 0x0171 }
            int r8 = r14.length     // Catch:{ Exception -> 0x0171 }
            int r8 = android.system.Os.read(r15, r14, r9, r8)     // Catch:{ Exception -> 0x0171 }
            if (r8 != r0) goto L_0x015a
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0171 }
            java.io.ByteArrayInputStream r15 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0171 }
            r15.<init>(r14)     // Catch:{ Exception -> 0x0171 }
            r0.<init>(r15)     // Catch:{ Exception -> 0x0171 }
            long r16 = r0.readLong()     // Catch:{ Exception -> 0x0171 }
            r12 = r16
            if (r10 <= r7) goto L_0x0155
            int r0 = (int) r12
            com.android.internal.os.Zygote.removeUsapTableEntry(r0)
        L_0x0155:
            r0 = 1
            r8 = -1
            goto L_0x00aa
        L_0x015a:
            java.lang.String r0 = "ZygoteServer"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0171 }
            r15.<init>()     // Catch:{ Exception -> 0x0171 }
            java.lang.String r9 = "Incomplete read from USAP management FD of size "
            r15.append(r9)     // Catch:{ Exception -> 0x0171 }
            r15.append(r8)     // Catch:{ Exception -> 0x0171 }
            java.lang.String r9 = r15.toString()     // Catch:{ Exception -> 0x0171 }
            android.util.Log.e(r0, r9)     // Catch:{ Exception -> 0x0171 }
            goto L_0x01aa
        L_0x0171:
            r0 = move-exception
            if (r10 != r7) goto L_0x018f
            java.lang.String r8 = "ZygoteServer"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r14 = "Failed to read from USAP pool event FD: "
            r9.append(r14)
            java.lang.String r14 = r0.getMessage()
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r8, r9)
            goto L_0x01a9
        L_0x018f:
            java.lang.String r8 = "ZygoteServer"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r14 = "Failed to read from USAP reporting pipe: "
            r9.append(r14)
            java.lang.String r14 = r0.getMessage()
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r8, r9)
        L_0x01a9:
        L_0x01aa:
            r0 = r11
            r8 = -1
            r9 = 0
            goto L_0x00aa
        L_0x01af:
            if (r11 == 0) goto L_0x01cf
            int r0 = r2.size()
            java.util.List r0 = r2.subList(r6, r0)
            java.util.stream.Stream r0 = r0.stream()
            com.android.internal.os.-$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE r6 = com.android.internal.os.$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE.INSTANCE
            java.util.stream.IntStream r0 = r0.mapToInt(r6)
            int[] r0 = r0.toArray()
            java.lang.Runnable r6 = r1.fillUsapPool(r0)
            if (r6 == 0) goto L_0x01cf
            return r6
        L_0x01cf:
            goto L_0x001b
        L_0x01d1:
            r0 = move-exception
            r6 = r0
            r0 = r6
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.String r8 = "poll failed"
            r6.<init>(r8, r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteServer.runSelectLoop(java.lang.String):java.lang.Runnable");
    }
}
