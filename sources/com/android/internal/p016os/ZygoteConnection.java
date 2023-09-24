package com.android.internal.p016os;

import android.annotation.UnsupportedAppUsage;
import android.content.p002pm.ApplicationInfo;
import android.metrics.LogMaker;
import android.net.Credentials;
import android.net.LocalSocket;
import android.p007os.Parcel;
import android.p007os.Process;
import android.p007os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import android.util.StatsLog;
import android.util.TimeUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import libcore.io.IoUtils;

/* renamed from: com.android.internal.os.ZygoteConnection */
/* loaded from: classes4.dex */
class ZygoteConnection {
    private static final String TAG = "Zygote";
    private final String abiList;
    private boolean isEof;
    @UnsupportedAppUsage
    private final LocalSocket mSocket;
    @UnsupportedAppUsage
    private final DataOutputStream mSocketOutStream;
    private final BufferedReader mSocketReader;
    @UnsupportedAppUsage
    private final Credentials peer;

    ZygoteConnection(LocalSocket socket, String abiList) throws IOException {
        this.mSocket = socket;
        this.abiList = abiList;
        this.mSocketOutStream = new DataOutputStream(socket.getOutputStream());
        this.mSocketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 256);
        this.mSocket.setSoTimeout(1000);
        try {
            this.peer = this.mSocket.getPeerCredentials();
            this.isEof = false;
        } catch (IOException ex) {
            Log.m69e(TAG, "Cannot read peer credentials", ex);
            throw ex;
        }
    }

    FileDescriptor getFileDescriptor() {
        return this.mSocket.getFileDescriptor();
    }

    Runnable processOneCommand(ZygoteServer zygoteServer) {
        ZygoteConnection zygoteConnection;
        try {
            String[] args = Zygote.readArgumentList(this.mSocketReader);
            FileDescriptor[] descriptors = this.mSocket.getAncillaryFileDescriptors();
            if (args == null) {
                this.isEof = true;
                return null;
            }
            FileDescriptor childPipeFd = null;
            FileDescriptor childPipeFd2 = null;
            ZygoteArguments parsedArgs = new ZygoteArguments(args);
            if (parsedArgs.mAbiListQuery) {
                handleAbiListQuery();
                return null;
            } else if (parsedArgs.mPidQuery) {
                handlePidQuery();
                return null;
            } else if (parsedArgs.mUsapPoolStatusSpecified) {
                return handleUsapPoolStatusChange(zygoteServer, parsedArgs.mUsapPoolEnabled);
            } else {
                if (parsedArgs.mPreloadDefault) {
                    handlePreload();
                    return null;
                } else if (parsedArgs.mPreloadPackage != null) {
                    handlePreloadPackage(parsedArgs.mPreloadPackage, parsedArgs.mPreloadPackageLibs, parsedArgs.mPreloadPackageLibFileName, parsedArgs.mPreloadPackageCacheKey);
                    return null;
                } else if (canPreloadApp() && parsedArgs.mPreloadApp != null) {
                    byte[] rawParcelData = Base64.getDecoder().decode(parsedArgs.mPreloadApp);
                    Parcel appInfoParcel = Parcel.obtain();
                    appInfoParcel.unmarshall(rawParcelData, 0, rawParcelData.length);
                    appInfoParcel.setDataPosition(0);
                    ApplicationInfo appInfo = ApplicationInfo.CREATOR.createFromParcel(appInfoParcel);
                    appInfoParcel.recycle();
                    if (appInfo != null) {
                        handlePreloadApp(appInfo);
                        return null;
                    }
                    throw new IllegalArgumentException("Failed to deserialize --preload-app");
                } else if (parsedArgs.mApiBlacklistExemptions != null) {
                    return handleApiBlacklistExemptions(zygoteServer, parsedArgs.mApiBlacklistExemptions);
                } else {
                    if (parsedArgs.mHiddenApiAccessLogSampleRate != -1) {
                        zygoteConnection = this;
                    } else if (parsedArgs.mHiddenApiAccessStatslogSampleRate == -1) {
                        if (parsedArgs.mPermittedCapabilities != 0 || parsedArgs.mEffectiveCapabilities != 0) {
                            throw new ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + Long.toHexString(parsedArgs.mPermittedCapabilities) + ", effective=0x" + Long.toHexString(parsedArgs.mEffectiveCapabilities));
                        }
                        Zygote.applyUidSecurityPolicy(parsedArgs, this.peer);
                        Zygote.applyInvokeWithSecurityPolicy(parsedArgs, this.peer);
                        Zygote.applyDebuggerSystemProperty(parsedArgs);
                        Zygote.applyInvokeWithSystemProperty(parsedArgs);
                        int[][] rlimits = parsedArgs.mRLimits != null ? (int[][]) parsedArgs.mRLimits.toArray(Zygote.INT_ARRAY_2D) : null;
                        int[] fdsToIgnore = null;
                        if (parsedArgs.mInvokeWith != null) {
                            try {
                                FileDescriptor[] pipeFds = Os.pipe2(OsConstants.O_CLOEXEC);
                                childPipeFd = pipeFds[1];
                                childPipeFd2 = pipeFds[0];
                                Os.fcntlInt(childPipeFd, OsConstants.F_SETFD, 0);
                                fdsToIgnore = new int[]{childPipeFd.getInt$(), childPipeFd2.getInt$()};
                            } catch (ErrnoException errnoEx) {
                                throw new IllegalStateException("Unable to set up pipe for invoke-with", errnoEx);
                            }
                        }
                        FileDescriptor serverPipeFd = childPipeFd2;
                        FileDescriptor childPipeFd3 = childPipeFd;
                        int[] fdsToIgnore2 = fdsToIgnore;
                        int[] fdsToClose = {-1, -1};
                        FileDescriptor fd = this.mSocket.getFileDescriptor();
                        if (fd != null) {
                            fdsToClose[0] = fd.getInt$();
                        }
                        FileDescriptor fd2 = zygoteServer.getZygoteSocketFileDescriptor();
                        if (fd2 != null) {
                            fdsToClose[1] = fd2.getInt$();
                        }
                        FileDescriptor serverPipeFd2 = serverPipeFd;
                        int pid = Zygote.forkAndSpecialize(parsedArgs.mUid, parsedArgs.mGid, parsedArgs.mGids, parsedArgs.mRuntimeFlags, rlimits, parsedArgs.mMountExternal, parsedArgs.mSeInfo, parsedArgs.mNiceName, fdsToClose, fdsToIgnore2, parsedArgs.mStartChildZygote, parsedArgs.mInstructionSet, parsedArgs.mAppDataDir, parsedArgs.mTargetSdkVersion);
                        if (pid == 0) {
                            try {
                                zygoteServer.setForkChild();
                                zygoteServer.closeServerSocket();
                                IoUtils.closeQuietly(serverPipeFd2);
                                try {
                                    try {
                                        Runnable handleChildProc = handleChildProc(parsedArgs, descriptors, childPipeFd3, parsedArgs.mStartChildZygote);
                                        IoUtils.closeQuietly(childPipeFd3);
                                        IoUtils.closeQuietly((FileDescriptor) null);
                                        return handleChildProc;
                                    } catch (Throwable th) {
                                        th = th;
                                        serverPipeFd2 = null;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    serverPipeFd2 = null;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } else {
                            try {
                                IoUtils.closeQuietly(childPipeFd3);
                                childPipeFd3 = null;
                                try {
                                    handleParentProc(pid, descriptors, serverPipeFd2);
                                    IoUtils.closeQuietly((FileDescriptor) null);
                                    IoUtils.closeQuietly(serverPipeFd2);
                                    return null;
                                } catch (Throwable th4) {
                                    th = th4;
                                    serverPipeFd2 = serverPipeFd2;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                            }
                        }
                        IoUtils.closeQuietly(childPipeFd3);
                        IoUtils.closeQuietly(serverPipeFd2);
                        throw th;
                    } else {
                        zygoteConnection = this;
                    }
                    return zygoteConnection.handleHiddenApiAccessLogSampleRate(zygoteServer, parsedArgs.mHiddenApiAccessLogSampleRate, parsedArgs.mHiddenApiAccessStatslogSampleRate);
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("IOException on command socket", ex);
        }
    }

    private void handleAbiListQuery() {
        try {
            byte[] abiListBytes = this.abiList.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(abiListBytes.length);
            this.mSocketOutStream.write(abiListBytes);
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private void handlePidQuery() {
        try {
            String pidString = String.valueOf(Process.myPid());
            byte[] pidStringBytes = pidString.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(pidStringBytes.length);
            this.mSocketOutStream.write(pidStringBytes);
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private void handlePreload() {
        try {
            if (isPreloadComplete()) {
                this.mSocketOutStream.writeInt(1);
                return;
            }
            preload();
            this.mSocketOutStream.writeInt(0);
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private Runnable stateChangeWithUsapPoolReset(ZygoteServer zygoteServer, Runnable stateChangeCode) {
        try {
            if (zygoteServer.isUsapPoolEnabled()) {
                Log.m68i(TAG, "Emptying USAP Pool due to state change.");
                Zygote.emptyUsapPool();
            }
            stateChangeCode.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                Runnable fpResult = zygoteServer.fillUsapPool(new int[]{this.mSocket.getFileDescriptor().getInt$()});
                if (fpResult != null) {
                    zygoteServer.setForkChild();
                    return fpResult;
                }
                Log.m68i(TAG, "Finished refilling USAP Pool after state change.");
            }
            this.mSocketOutStream.writeInt(0);
            return null;
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private Runnable handleApiBlacklistExemptions(ZygoteServer zygoteServer, final String[] exemptions) {
        return stateChangeWithUsapPoolReset(zygoteServer, new Runnable() { // from class: com.android.internal.os.-$$Lambda$ZygoteConnection$xjqM7qW7vAjTqh2tR5XRF5Vn5mk
            @Override // java.lang.Runnable
            public final void run() {
                ZygoteInit.setApiBlacklistExemptions(exemptions);
            }
        });
    }

    private Runnable handleUsapPoolStatusChange(ZygoteServer zygoteServer, boolean newStatus) {
        try {
            Runnable fpResult = zygoteServer.setUsapPoolStatus(newStatus, this.mSocket);
            if (fpResult == null) {
                this.mSocketOutStream.writeInt(0);
            } else {
                zygoteServer.setForkChild();
            }
            return fpResult;
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    /* renamed from: com.android.internal.os.ZygoteConnection$HiddenApiUsageLogger */
    /* loaded from: classes4.dex */
    private static class HiddenApiUsageLogger implements VMRuntime.HiddenApiUsageLogger {
        private static HiddenApiUsageLogger sInstance = new HiddenApiUsageLogger();
        private final MetricsLogger mMetricsLogger = new MetricsLogger();
        private int mHiddenApiAccessLogSampleRate = 0;
        private int mHiddenApiAccessStatslogSampleRate = 0;

        private HiddenApiUsageLogger() {
        }

        public static void setHiddenApiAccessLogSampleRates(int sampleRate, int newSampleRate) {
            if (sampleRate != -1) {
                sInstance.mHiddenApiAccessLogSampleRate = sampleRate;
            }
            if (newSampleRate != -1) {
                sInstance.mHiddenApiAccessStatslogSampleRate = newSampleRate;
            }
        }

        public static HiddenApiUsageLogger getInstance() {
            return sInstance;
        }

        public void hiddenApiUsed(int sampledValue, String packageName, String signature, int accessMethod, boolean accessDenied) {
            if (sampledValue < this.mHiddenApiAccessLogSampleRate) {
                logUsage(packageName, signature, accessMethod, accessDenied);
            }
            if (sampledValue < this.mHiddenApiAccessStatslogSampleRate) {
                newLogUsage(signature, accessMethod, accessDenied);
            }
        }

        private void logUsage(String packageName, String signature, int accessMethod, boolean accessDenied) {
            int accessMethodMetric = 0;
            switch (accessMethod) {
                case 0:
                    accessMethodMetric = 0;
                    break;
                case 1:
                    accessMethodMetric = 1;
                    break;
                case 2:
                    accessMethodMetric = 2;
                    break;
                case 3:
                    accessMethodMetric = 3;
                    break;
            }
            LogMaker logMaker = new LogMaker((int) MetricsProto.MetricsEvent.ACTION_HIDDEN_API_ACCESSED).setPackageName(packageName).addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_SIGNATURE, signature).addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_METHOD, Integer.valueOf(accessMethodMetric));
            if (accessDenied) {
                logMaker.addTaggedData(MetricsProto.MetricsEvent.FIELD_HIDDEN_API_ACCESS_DENIED, 1);
            }
            this.mMetricsLogger.write(logMaker);
        }

        private void newLogUsage(String signature, int accessMethod, boolean accessDenied) {
            int accessMethodProto = 0;
            switch (accessMethod) {
                case 0:
                    accessMethodProto = 0;
                    break;
                case 1:
                    accessMethodProto = 1;
                    break;
                case 2:
                    accessMethodProto = 2;
                    break;
                case 3:
                    accessMethodProto = 3;
                    break;
            }
            int uid = Process.myUid();
            StatsLog.write(178, uid, signature, accessMethodProto, accessDenied);
        }
    }

    private Runnable handleHiddenApiAccessLogSampleRate(ZygoteServer zygoteServer, final int samplingRate, final int statsdSamplingRate) {
        return stateChangeWithUsapPoolReset(zygoteServer, new Runnable() { // from class: com.android.internal.os.-$$Lambda$ZygoteConnection$KxVsZ-s4KsanePOHCU5JcuypPik
            @Override // java.lang.Runnable
            public final void run() {
                ZygoteConnection.lambda$handleHiddenApiAccessLogSampleRate$1(samplingRate, statsdSamplingRate);
            }
        });
    }

    static /* synthetic */ void lambda$handleHiddenApiAccessLogSampleRate$1(int samplingRate, int statsdSamplingRate) {
        int maxSamplingRate = Math.max(samplingRate, statsdSamplingRate);
        ZygoteInit.setHiddenApiAccessLogSampleRate(maxSamplingRate);
        HiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(samplingRate, statsdSamplingRate);
        ZygoteInit.setHiddenApiUsageLogger(HiddenApiUsageLogger.getInstance());
    }

    protected void preload() {
        ZygoteInit.lazyPreload();
    }

    protected boolean isPreloadComplete() {
        return ZygoteInit.isPreloadComplete();
    }

    protected DataOutputStream getSocketOutputStream() {
        return this.mSocketOutStream;
    }

    protected void handlePreloadPackage(String packagePath, String libsPath, String libFileName, String cacheKey) {
        throw new RuntimeException("Zygote does not support package preloading");
    }

    protected boolean canPreloadApp() {
        return false;
    }

    protected void handlePreloadApp(ApplicationInfo aInfo) {
        throw new RuntimeException("Zygote does not support app preloading");
    }

    @UnsupportedAppUsage
    void closeSocket() {
        try {
            this.mSocket.close();
        } catch (IOException ex) {
            Log.m69e(TAG, "Exception while closing command socket in parent", ex);
        }
    }

    boolean isClosedByPeer() {
        return this.isEof;
    }

    private Runnable handleChildProc(ZygoteArguments parsedArgs, FileDescriptor[] descriptors, FileDescriptor pipeFd, boolean isZygote) {
        closeSocket();
        if (descriptors != null) {
            try {
                Os.dup2(descriptors[0], OsConstants.STDIN_FILENO);
                Os.dup2(descriptors[1], OsConstants.STDOUT_FILENO);
                Os.dup2(descriptors[2], OsConstants.STDERR_FILENO);
                for (FileDescriptor fd : descriptors) {
                    IoUtils.closeQuietly(fd);
                }
            } catch (ErrnoException ex) {
                Log.m69e(TAG, "Error reopening stdio", ex);
            }
        }
        if (parsedArgs.mNiceName != null) {
            Process.setArgV0(parsedArgs.mNiceName);
        }
        Trace.traceEnd(64L);
        if (parsedArgs.mInvokeWith == null) {
            return !isZygote ? ZygoteInit.zygoteInit(parsedArgs.mTargetSdkVersion, parsedArgs.mRemainingArgs, null) : ZygoteInit.childZygoteInit(parsedArgs.mTargetSdkVersion, parsedArgs.mRemainingArgs, null);
        }
        WrapperInit.execApplication(parsedArgs.mInvokeWith, parsedArgs.mNiceName, parsedArgs.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), pipeFd, parsedArgs.mRemainingArgs);
        throw new IllegalStateException("WrapperInit.execApplication unexpectedly returned");
    }

    private void handleParentProc(int pid, FileDescriptor[] descriptors, FileDescriptor pipeFd) {
        int pid2;
        if (pid > 0) {
            setChildPgid(pid);
        }
        short s = 0;
        if (descriptors != null) {
            for (FileDescriptor fd : descriptors) {
                IoUtils.closeQuietly(fd);
            }
        }
        boolean usingWrapper = false;
        try {
            if (pipeFd != null && pid > 0) {
                int innerPid = -1;
                try {
                    StructPollfd[] fds = {new StructPollfd()};
                    byte[] data = new byte[4];
                    int remainingSleepTime = 30000;
                    int dataIndex = 0;
                    long startTime = System.nanoTime();
                    while (dataIndex < data.length && remainingSleepTime > 0) {
                        fds[s].fd = pipeFd;
                        fds[s].events = (short) OsConstants.POLLIN;
                        fds[s].revents = s;
                        fds[s].userData = null;
                        int res = Os.poll(fds, remainingSleepTime);
                        long endTime = System.nanoTime();
                        int elapsedTimeMs = (int) ((endTime - startTime) / TimeUtils.NANOS_PER_MS);
                        remainingSleepTime = 30000 - elapsedTimeMs;
                        if (res > 0) {
                            if ((fds[0].revents & OsConstants.POLLIN) == 0) {
                                break;
                            }
                            int readBytes = Os.read(pipeFd, data, dataIndex, 1);
                            if (readBytes < 0) {
                                throw new RuntimeException("Some error");
                            }
                            dataIndex += readBytes;
                        } else if (res == 0) {
                            Log.m64w(TAG, "Timed out waiting for child.");
                        }
                        s = 0;
                    }
                    if (dataIndex == data.length) {
                        DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
                        innerPid = is.readInt();
                    }
                    if (innerPid == -1) {
                        Log.m64w(TAG, "Error reading pid from wrapped process, child may have died");
                    }
                } catch (Exception ex) {
                    Log.m63w(TAG, "Error reading pid from wrapped process, child may have died", ex);
                }
                if (innerPid > 0) {
                    int parentPid = innerPid;
                    while (parentPid > 0 && parentPid != pid) {
                        parentPid = Process.getParentPid(parentPid);
                    }
                    if (parentPid > 0) {
                        Log.m68i(TAG, "Wrapped process has pid " + innerPid);
                        pid2 = innerPid;
                        usingWrapper = true;
                        this.mSocketOutStream.writeInt(pid2);
                        this.mSocketOutStream.writeBoolean(usingWrapper);
                        return;
                    }
                    Log.m64w(TAG, "Wrapped process reported a pid that is not a child of the process that we forked: childPid=" + pid + " innerPid=" + innerPid);
                }
            }
            this.mSocketOutStream.writeInt(pid2);
            this.mSocketOutStream.writeBoolean(usingWrapper);
            return;
        } catch (IOException ex2) {
            throw new IllegalStateException("Error writing to command socket", ex2);
        }
        pid2 = pid;
    }

    private void setChildPgid(int pid) {
        try {
            Os.setpgid(pid, Os.getpgid(this.peer.getPid()));
        } catch (ErrnoException e) {
            Log.m68i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }
}
