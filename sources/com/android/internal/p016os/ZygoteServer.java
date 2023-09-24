package com.android.internal.p016os;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.p007os.SystemClock;
import android.p007os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import android.util.Slog;
import dalvik.system.ZygoteHooks;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.android.internal.os.ZygoteServer */
/* loaded from: classes4.dex */
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
        this.mLastPropCheckTimestamp = 0L;
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
        this.mLastPropCheckTimestamp = 0L;
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

    void setForkChild() {
        this.mIsForkChild = true;
    }

    public boolean isUsapPoolEnabled() {
        return this.mUsapPoolEnabled;
    }

    void registerServerSocketAtAbstractName(String socketName) {
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

    protected ZygoteConnection createNewConnection(LocalSocket socket, String abiList) throws IOException {
        return new ZygoteConnection(socket, abiList);
    }

    void closeServerSocket() {
        try {
            if (this.mZygoteSocket != null) {
                FileDescriptor fd = this.mZygoteSocket.getFileDescriptor();
                this.mZygoteSocket.close();
                if (fd != null && this.mCloseSocketFd) {
                    Os.close(fd);
                }
            }
        } catch (ErrnoException ex) {
            Log.m69e(TAG, "Zygote:  error closing descriptor", ex);
        } catch (IOException ex2) {
            Log.m69e(TAG, "Zygote:  error closing sockets", ex2);
        }
        this.mZygoteSocket = null;
    }

    FileDescriptor getZygoteSocketFileDescriptor() {
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
                Log.m64w(TAG, "The max size of the USAP pool must be greater than the minimum size.  Restoring default values.");
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

    Runnable fillUsapPool(int[] sessionSocketRawFDs) {
        Trace.traceBegin(64L, "Zygote:FillUsapPool");
        fetchUsapPoolPolicyPropsWithMinInterval();
        int usapPoolCount = Zygote.getUsapPoolCount();
        int numUsapsToSpawn = this.mUsapPoolSizeMax - usapPoolCount;
        if (usapPoolCount < this.mUsapPoolSizeMin || numUsapsToSpawn >= this.mUsapPoolRefillThreshold) {
            ZygoteHooks.preFork();
            Zygote.resetNicePriority();
            while (true) {
                int usapPoolCount2 = usapPoolCount + 1;
                if (usapPoolCount < this.mUsapPoolSizeMax) {
                    Runnable caller = Zygote.forkUsap(this.mUsapPoolSocket, sessionSocketRawFDs);
                    if (caller == null) {
                        usapPoolCount = usapPoolCount2;
                    } else {
                        return caller;
                    }
                } else {
                    ZygoteHooks.postForkCommon();
                    Log.m68i(Zygote.PRIMARY_SOCKET_NAME, "Filled the USAP pool. New USAPs: " + numUsapsToSpawn);
                    break;
                }
            }
        }
        Trace.traceEnd(64L);
        return null;
    }

    Runnable setUsapPoolStatus(boolean newStatus, LocalSocket sessionSocket) {
        if (!this.mUsapPoolSupported) {
            Log.m64w(TAG, "Attempting to enable a USAP pool for a Zygote that doesn't support it.");
            return null;
        } else if (this.mUsapPoolEnabled == newStatus) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("USAP Pool status change: ");
            sb.append(newStatus ? "ENABLED" : "DISABLED");
            Log.m68i(TAG, sb.toString());
            this.mUsapPoolEnabled = newStatus;
            if (newStatus) {
                return fillUsapPool(new int[]{sessionSocket.getFileDescriptor().getInt$()});
            }
            Zygote.emptyUsapPool();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x01af, code lost:
        if (r11 == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01b2, code lost:
        r0 = r0.subList(1, r0.size()).stream().mapToInt(com.android.internal.p016os.$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE.INSTANCE).toArray();
        r6 = fillUsapPool(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01cc, code lost:
        if (r6 == null) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01ce, code lost:
        return r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    Runnable runSelectLoop(String abiList) {
        StructPollfd[] pollFDs;
        int pollIndex;
        ZygoteConnection connection;
        Runnable command;
        byte[] buffer;
        int readBytes;
        ArrayList<FileDescriptor> socketFDs = new ArrayList<>();
        ArrayList<ZygoteConnection> peers = new ArrayList<>();
        socketFDs.add(this.mZygoteSocket.getFileDescriptor());
        peers.add(null);
        while (true) {
            fetchUsapPoolPolicyPropsWithMinInterval();
            int[] usapPipeFDs = null;
            if (this.mUsapPoolEnabled) {
                usapPipeFDs = Zygote.getUsapPipeFDs();
                pollFDs = new StructPollfd[socketFDs.size() + 1 + usapPipeFDs.length];
            } else {
                pollFDs = new StructPollfd[socketFDs.size()];
            }
            int[] usapPipeFDs2 = usapPipeFDs;
            int pollIndex2 = 0;
            Iterator<FileDescriptor> it = socketFDs.iterator();
            while (it.hasNext()) {
                FileDescriptor socketFD = it.next();
                pollFDs[pollIndex2] = new StructPollfd();
                pollFDs[pollIndex2].fd = socketFD;
                pollFDs[pollIndex2].events = (short) OsConstants.POLLIN;
                pollIndex2++;
            }
            int usapPoolEventFDIndex = pollIndex2;
            boolean z = false;
            if (this.mUsapPoolEnabled) {
                pollFDs[pollIndex2] = new StructPollfd();
                pollFDs[pollIndex2].fd = this.mUsapPoolEventFD;
                pollFDs[pollIndex2].events = (short) OsConstants.POLLIN;
                pollIndex = pollIndex2 + 1;
                for (int usapPipeFD : usapPipeFDs2) {
                    FileDescriptor managedFd = new FileDescriptor();
                    managedFd.setInt$(usapPipeFD);
                    pollFDs[pollIndex] = new StructPollfd();
                    pollFDs[pollIndex].fd = managedFd;
                    pollFDs[pollIndex].events = (short) OsConstants.POLLIN;
                    pollIndex++;
                }
            } else {
                pollIndex = pollIndex2;
            }
            int i = -1;
            try {
                Os.poll(pollFDs, -1);
                boolean usapPoolFDRead = false;
                while (true) {
                    boolean usapPoolFDRead2 = usapPoolFDRead;
                    pollIndex += i;
                    if (pollIndex < 0) {
                        break;
                    }
                    if ((pollFDs[pollIndex].revents & OsConstants.POLLIN) != 0) {
                        if (pollIndex == 0) {
                            ZygoteConnection newPeer = acceptCommandPeer(abiList);
                            peers.add(newPeer);
                            socketFDs.add(newPeer.getFileDescriptor());
                        } else if (pollIndex < usapPoolEventFDIndex) {
                            try {
                                try {
                                    connection = peers.get(pollIndex);
                                    command = connection.processOneCommand(this);
                                } catch (Exception e) {
                                    if (this.mIsForkChild) {
                                        Log.m69e(TAG, "Caught post-fork exception in child process.", e);
                                        throw e;
                                    }
                                    Slog.m55e(TAG, "Exception executing zygote command: ", e);
                                    ZygoteConnection conn = peers.remove(pollIndex);
                                    conn.closeSocket();
                                    socketFDs.remove(pollIndex);
                                }
                                if (this.mIsForkChild) {
                                    if (command != null) {
                                        return command;
                                    }
                                    throw new IllegalStateException("command == null");
                                } else if (command != null) {
                                    throw new IllegalStateException("command != null");
                                } else {
                                    if (connection.isClosedByPeer()) {
                                        connection.closeSocket();
                                        peers.remove(pollIndex);
                                        socketFDs.remove(pollIndex);
                                    }
                                }
                            } finally {
                                this.mIsForkChild = z;
                            }
                        } else {
                            try {
                                buffer = new byte[8];
                                readBytes = Os.read(pollFDs[pollIndex].fd, buffer, z ? 1 : 0, buffer.length);
                            } catch (Exception ex) {
                                if (pollIndex == usapPoolEventFDIndex) {
                                    Log.m70e(TAG, "Failed to read from USAP pool event FD: " + ex.getMessage());
                                } else {
                                    Log.m70e(TAG, "Failed to read from USAP reporting pipe: " + ex.getMessage());
                                }
                            }
                            if (readBytes == 8) {
                                DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(buffer));
                                long messagePayload = inputStream.readLong();
                                if (pollIndex > usapPoolEventFDIndex) {
                                    Zygote.removeUsapTableEntry((int) messagePayload);
                                }
                                usapPoolFDRead = true;
                                i = -1;
                            } else {
                                Log.m70e(TAG, "Incomplete read from USAP management FD of size " + readBytes);
                            }
                        }
                    }
                    usapPoolFDRead = usapPoolFDRead2;
                    i = -1;
                    z = false;
                }
            } catch (ErrnoException ex2) {
                throw new RuntimeException("poll failed", ex2);
            }
        }
    }
}
