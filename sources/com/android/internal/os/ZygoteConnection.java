package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.metrics.LogMaker;
import android.net.Credentials;
import android.net.LocalSocket;
import android.os.Process;
import android.os.Trace;
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
import libcore.io.IoUtils;

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

    ZygoteConnection(LocalSocket socket, String abiList2) throws IOException {
        this.mSocket = socket;
        this.abiList = abiList2;
        this.mSocketOutStream = new DataOutputStream(socket.getOutputStream());
        this.mSocketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 256);
        this.mSocket.setSoTimeout(1000);
        try {
            this.peer = this.mSocket.getPeerCredentials();
            this.isEof = false;
        } catch (IOException ex) {
            Log.e(TAG, "Cannot read peer credentials", ex);
            throw ex;
        }
    }

    /* access modifiers changed from: package-private */
    public FileDescriptor getFileDescriptor() {
        return this.mSocket.getFileDescriptor();
    }

    /* JADX WARNING: type inference failed for: r12v13, types: [java.lang.Object[]] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Runnable processOneCommand(com.android.internal.os.ZygoteServer r33) {
        /*
            r32 = this;
            r1 = r32
            r2 = r33
            r0 = 0
            r3 = r0
            java.io.BufferedReader r4 = r1.mSocketReader     // Catch:{ IOException -> 0x01fc }
            java.lang.String[] r4 = com.android.internal.os.Zygote.readArgumentList(r4)     // Catch:{ IOException -> 0x01fc }
            android.net.LocalSocket r5 = r1.mSocket     // Catch:{ IOException -> 0x01fc }
            java.io.FileDescriptor[] r5 = r5.getAncillaryFileDescriptors()     // Catch:{ IOException -> 0x01fc }
            r6 = 1
            if (r4 != 0) goto L_0x001a
            r1.isEof = r6
            return r0
        L_0x001a:
            r7 = -1
            r8 = 0
            r9 = 0
            com.android.internal.os.ZygoteArguments r10 = new com.android.internal.os.ZygoteArguments
            r10.<init>(r4)
            r3 = r10
            boolean r10 = r3.mAbiListQuery
            if (r10 == 0) goto L_0x002b
            r32.handleAbiListQuery()
            return r0
        L_0x002b:
            boolean r10 = r3.mPidQuery
            if (r10 == 0) goto L_0x0033
            r32.handlePidQuery()
            return r0
        L_0x0033:
            boolean r10 = r3.mUsapPoolStatusSpecified
            if (r10 == 0) goto L_0x003e
            boolean r0 = r3.mUsapPoolEnabled
            java.lang.Runnable r0 = r1.handleUsapPoolStatusChange(r2, r0)
            return r0
        L_0x003e:
            boolean r10 = r3.mPreloadDefault
            if (r10 == 0) goto L_0x0046
            r32.handlePreload()
            return r0
        L_0x0046:
            java.lang.String r10 = r3.mPreloadPackage
            if (r10 == 0) goto L_0x0056
            java.lang.String r6 = r3.mPreloadPackage
            java.lang.String r10 = r3.mPreloadPackageLibs
            java.lang.String r11 = r3.mPreloadPackageLibFileName
            java.lang.String r12 = r3.mPreloadPackageCacheKey
            r1.handlePreloadPackage(r6, r10, r11, r12)
            return r0
        L_0x0056:
            boolean r10 = r32.canPreloadApp()
            r11 = 0
            if (r10 == 0) goto L_0x008f
            java.lang.String r10 = r3.mPreloadApp
            if (r10 == 0) goto L_0x008f
            java.util.Base64$Decoder r6 = java.util.Base64.getDecoder()
            java.lang.String r10 = r3.mPreloadApp
            byte[] r6 = r6.decode(r10)
            android.os.Parcel r10 = android.os.Parcel.obtain()
            int r12 = r6.length
            r10.unmarshall(r6, r11, r12)
            r10.setDataPosition(r11)
            android.os.Parcelable$Creator<android.content.pm.ApplicationInfo> r11 = android.content.pm.ApplicationInfo.CREATOR
            java.lang.Object r11 = r11.createFromParcel(r10)
            android.content.pm.ApplicationInfo r11 = (android.content.pm.ApplicationInfo) r11
            r10.recycle()
            if (r11 == 0) goto L_0x0087
            r1.handlePreloadApp(r11)
            return r0
        L_0x0087:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "Failed to deserialize --preload-app"
            r0.<init>(r12)
            throw r0
        L_0x008f:
            java.lang.String[] r10 = r3.mApiBlacklistExemptions
            if (r10 == 0) goto L_0x009a
            java.lang.String[] r0 = r3.mApiBlacklistExemptions
            java.lang.Runnable r0 = r1.handleApiBlacklistExemptions(r2, r0)
            return r0
        L_0x009a:
            int r10 = r3.mHiddenApiAccessLogSampleRate
            r12 = -1
            if (r10 != r12) goto L_0x01eb
            int r10 = r3.mHiddenApiAccessStatslogSampleRate
            if (r10 == r12) goto L_0x00ab
            r2 = r1
            r27 = r4
            r4 = r5
            r29 = r7
            goto L_0x01f1
        L_0x00ab:
            long r12 = r3.mPermittedCapabilities
            r14 = 0
            int r10 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r10 != 0) goto L_0x01ba
            long r12 = r3.mEffectiveCapabilities
            int r10 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r10 != 0) goto L_0x01ba
            android.net.Credentials r10 = r1.peer
            com.android.internal.os.Zygote.applyUidSecurityPolicy(r3, r10)
            android.net.Credentials r10 = r1.peer
            com.android.internal.os.Zygote.applyInvokeWithSecurityPolicy(r3, r10)
            com.android.internal.os.Zygote.applyDebuggerSystemProperty(r3)
            com.android.internal.os.Zygote.applyInvokeWithSystemProperty(r3)
            r10 = 0
            java.util.ArrayList<int[]> r12 = r3.mRLimits
            if (r12 == 0) goto L_0x00d9
            java.util.ArrayList<int[]> r12 = r3.mRLimits
            int[][] r13 = com.android.internal.os.Zygote.INT_ARRAY_2D
            java.lang.Object[] r12 = r12.toArray(r13)
            r10 = r12
            int[][] r10 = (int[][]) r10
        L_0x00d9:
            r12 = 0
            java.lang.String r13 = r3.mInvokeWith
            r14 = 2
            if (r13 == 0) goto L_0x0109
            int r13 = android.system.OsConstants.O_CLOEXEC     // Catch:{ ErrnoException -> 0x0100 }
            java.io.FileDescriptor[] r13 = android.system.Os.pipe2(r13)     // Catch:{ ErrnoException -> 0x0100 }
            r15 = r13[r6]     // Catch:{ ErrnoException -> 0x0100 }
            r8 = r15
            r15 = r13[r11]     // Catch:{ ErrnoException -> 0x0100 }
            r9 = r15
            int r15 = android.system.OsConstants.F_SETFD     // Catch:{ ErrnoException -> 0x0100 }
            android.system.Os.fcntlInt(r8, r15, r11)     // Catch:{ ErrnoException -> 0x0100 }
            int[] r15 = new int[r14]     // Catch:{ ErrnoException -> 0x0100 }
            int r16 = r8.getInt$()     // Catch:{ ErrnoException -> 0x0100 }
            r15[r11] = r16     // Catch:{ ErrnoException -> 0x0100 }
            int r16 = r9.getInt$()     // Catch:{ ErrnoException -> 0x0100 }
            r15[r6] = r16     // Catch:{ ErrnoException -> 0x0100 }
            r12 = r15
            goto L_0x0109
        L_0x0100:
            r0 = move-exception
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r11 = "Unable to set up pipe for invoke-with"
            r6.<init>(r11, r0)
            throw r6
        L_0x0109:
            r15 = r9
            r9 = r8
            r8 = r12
            int[] r12 = new int[r14]
            r12 = {-1, -1} // fill-array
            r26 = r12
            android.net.LocalSocket r12 = r1.mSocket
            java.io.FileDescriptor r12 = r12.getFileDescriptor()
            if (r12 == 0) goto L_0x0121
            int r13 = r12.getInt$()
            r26[r11] = r13
        L_0x0121:
            java.io.FileDescriptor r11 = r33.getZygoteSocketFileDescriptor()
            if (r11 == 0) goto L_0x012d
            int r12 = r11.getInt$()
            r26[r6] = r12
        L_0x012d:
            r6 = 0
            int r12 = r3.mUid
            int r13 = r3.mGid
            int[] r14 = r3.mGids
            int r11 = r3.mRuntimeFlags
            int r0 = r3.mMountExternal
            r27 = r4
            java.lang.String r4 = r3.mSeInfo
            r28 = r6
            java.lang.String r6 = r3.mNiceName
            r29 = r7
            boolean r7 = r3.mStartChildZygote
            java.lang.String r2 = r3.mInstructionSet
            java.lang.String r1 = r3.mAppDataDir
            r30 = r5
            int r5 = r3.mTargetSdkVersion
            r31 = r15
            r15 = r11
            r16 = r10
            r17 = r0
            r18 = r4
            r19 = r6
            r20 = r26
            r21 = r8
            r22 = r7
            r23 = r2
            r24 = r1
            r25 = r5
            int r1 = com.android.internal.os.Zygote.forkAndSpecialize(r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            if (r1 != 0) goto L_0x0196
            r33.setForkChild()     // Catch:{ all -> 0x0190 }
            r33.closeServerSocket()     // Catch:{ all -> 0x0190 }
            libcore.io.IoUtils.closeQuietly(r31)     // Catch:{ all -> 0x0190 }
            r15 = 0
            boolean r0 = r3.mStartChildZygote     // Catch:{ all -> 0x0188 }
            r4 = r30
            r2 = r32
            java.lang.Runnable r0 = r2.handleChildProc(r3, r4, r9, r0)     // Catch:{ all -> 0x0184 }
            libcore.io.IoUtils.closeQuietly(r9)
            libcore.io.IoUtils.closeQuietly(r15)
            return r0
        L_0x0184:
            r0 = move-exception
            r31 = r15
            goto L_0x01b3
        L_0x0188:
            r0 = move-exception
            r4 = r30
            r2 = r32
            r31 = r15
            goto L_0x0195
        L_0x0190:
            r0 = move-exception
            r4 = r30
            r2 = r32
        L_0x0195:
            goto L_0x01b3
        L_0x0196:
            r4 = r30
            r2 = r32
            libcore.io.IoUtils.closeQuietly(r9)     // Catch:{ all -> 0x01b0 }
            r9 = 0
            r5 = r31
            r2.handleParentProc(r1, r4, r5)     // Catch:{ all -> 0x01ac }
            libcore.io.IoUtils.closeQuietly(r9)
            libcore.io.IoUtils.closeQuietly(r5)
            r0 = 0
            return r0
        L_0x01ac:
            r0 = move-exception
            r31 = r5
            goto L_0x01b3
        L_0x01b0:
            r0 = move-exception
            r5 = r31
        L_0x01b3:
            libcore.io.IoUtils.closeQuietly(r9)
            libcore.io.IoUtils.closeQuietly(r31)
            throw r0
        L_0x01ba:
            r2 = r1
            r27 = r4
            r4 = r5
            r29 = r7
            com.android.internal.os.ZygoteSecurityException r0 = new com.android.internal.os.ZygoteSecurityException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "Client may not specify capabilities: permitted=0x"
            r1.append(r5)
            long r5 = r3.mPermittedCapabilities
            java.lang.String r5 = java.lang.Long.toHexString(r5)
            r1.append(r5)
            java.lang.String r5 = ", effective=0x"
            r1.append(r5)
            long r5 = r3.mEffectiveCapabilities
            java.lang.String r5 = java.lang.Long.toHexString(r5)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x01eb:
            r2 = r1
            r27 = r4
            r4 = r5
            r29 = r7
        L_0x01f1:
            int r0 = r3.mHiddenApiAccessLogSampleRate
            int r1 = r3.mHiddenApiAccessStatslogSampleRate
            r5 = r33
            java.lang.Runnable r0 = r2.handleHiddenApiAccessLogSampleRate(r5, r0, r1)
            return r0
        L_0x01fc:
            r0 = move-exception
            r5 = r2
            r2 = r1
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r4 = "IOException on command socket"
            r1.<init>(r4, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteConnection.processOneCommand(com.android.internal.os.ZygoteServer):java.lang.Runnable");
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
            byte[] pidStringBytes = String.valueOf(Process.myPid()).getBytes(StandardCharsets.US_ASCII);
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
                Log.i(TAG, "Emptying USAP Pool due to state change.");
                Zygote.emptyUsapPool();
            }
            stateChangeCode.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                Runnable fpResult = zygoteServer.fillUsapPool(new int[]{this.mSocket.getFileDescriptor().getInt$()});
                if (fpResult != null) {
                    zygoteServer.setForkChild();
                    return fpResult;
                }
                Log.i(TAG, "Finished refilling USAP Pool after state change.");
            }
            this.mSocketOutStream.writeInt(0);
            return null;
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private Runnable handleApiBlacklistExemptions(ZygoteServer zygoteServer, String[] exemptions) {
        return stateChangeWithUsapPoolReset(zygoteServer, new Runnable(exemptions) {
            private final /* synthetic */ String[] f$0;

            {
                this.f$0 = r1;
            }

            public final void run() {
                ZygoteInit.setApiBlacklistExemptions(this.f$0);
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

    private static class HiddenApiUsageLogger implements VMRuntime.HiddenApiUsageLogger {
        private static HiddenApiUsageLogger sInstance = new HiddenApiUsageLogger();
        private int mHiddenApiAccessLogSampleRate = 0;
        private int mHiddenApiAccessStatslogSampleRate = 0;
        private final MetricsLogger mMetricsLogger = new MetricsLogger();

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
            StatsLog.write(178, Process.myUid(), signature, accessMethodProto, accessDenied);
        }
    }

    private Runnable handleHiddenApiAccessLogSampleRate(ZygoteServer zygoteServer, int samplingRate, int statsdSamplingRate) {
        return stateChangeWithUsapPoolReset(zygoteServer, new Runnable(samplingRate, statsdSamplingRate) {
            private final /* synthetic */ int f$0;
            private final /* synthetic */ int f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void run() {
                ZygoteConnection.lambda$handleHiddenApiAccessLogSampleRate$1(this.f$0, this.f$1);
            }
        });
    }

    static /* synthetic */ void lambda$handleHiddenApiAccessLogSampleRate$1(int samplingRate, int statsdSamplingRate) {
        ZygoteInit.setHiddenApiAccessLogSampleRate(Math.max(samplingRate, statsdSamplingRate));
        HiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(samplingRate, statsdSamplingRate);
        ZygoteInit.setHiddenApiUsageLogger(HiddenApiUsageLogger.getInstance());
    }

    /* access modifiers changed from: protected */
    public void preload() {
        ZygoteInit.lazyPreload();
    }

    /* access modifiers changed from: protected */
    public boolean isPreloadComplete() {
        return ZygoteInit.isPreloadComplete();
    }

    /* access modifiers changed from: protected */
    public DataOutputStream getSocketOutputStream() {
        return this.mSocketOutStream;
    }

    /* access modifiers changed from: protected */
    public void handlePreloadPackage(String packagePath, String libsPath, String libFileName, String cacheKey) {
        throw new RuntimeException("Zygote does not support package preloading");
    }

    /* access modifiers changed from: protected */
    public boolean canPreloadApp() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void handlePreloadApp(ApplicationInfo aInfo) {
        throw new RuntimeException("Zygote does not support app preloading");
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void closeSocket() {
        try {
            this.mSocket.close();
        } catch (IOException ex) {
            Log.e(TAG, "Exception while closing command socket in parent", ex);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isClosedByPeer() {
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
                Log.e(TAG, "Error reopening stdio", ex);
            }
        }
        if (parsedArgs.mNiceName != null) {
            Process.setArgV0(parsedArgs.mNiceName);
        }
        Trace.traceEnd(64);
        if (parsedArgs.mInvokeWith != null) {
            WrapperInit.execApplication(parsedArgs.mInvokeWith, parsedArgs.mNiceName, parsedArgs.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), pipeFd, parsedArgs.mRemainingArgs);
            throw new IllegalStateException("WrapperInit.execApplication unexpectedly returned");
        } else if (!isZygote) {
            return ZygoteInit.zygoteInit(parsedArgs.mTargetSdkVersion, parsedArgs.mRemainingArgs, (ClassLoader) null);
        } else {
            return ZygoteInit.childZygoteInit(parsedArgs.mTargetSdkVersion, parsedArgs.mRemainingArgs, (ClassLoader) null);
        }
    }

    private void handleParentProc(int pid, FileDescriptor[] descriptors, FileDescriptor pipeFd) {
        int pid2;
        int i = pid;
        FileDescriptor[] fileDescriptorArr = descriptors;
        FileDescriptor fileDescriptor = pipeFd;
        if (i > 0) {
            setChildPgid(pid);
        }
        short s = 0;
        if (fileDescriptorArr != null) {
            for (FileDescriptor fd : fileDescriptorArr) {
                IoUtils.closeQuietly(fd);
            }
        }
        boolean usingWrapper = false;
        if (fileDescriptor != null && i > 0) {
            int innerPid = -1;
            try {
                StructPollfd[] fds = {new StructPollfd()};
                byte[] data = new byte[4];
                int remainingSleepTime = 30000;
                int dataIndex = 0;
                long startTime = System.nanoTime();
                while (dataIndex < data.length && remainingSleepTime > 0) {
                    fds[s].fd = fileDescriptor;
                    fds[s].events = (short) OsConstants.POLLIN;
                    fds[s].revents = s;
                    fds[s].userData = null;
                    int res = Os.poll(fds, remainingSleepTime);
                    remainingSleepTime = 30000 - ((int) ((System.nanoTime() - startTime) / TimeUtils.NANOS_PER_MS));
                    if (res > 0) {
                        if ((fds[0].revents & OsConstants.POLLIN) == 0) {
                            break;
                        }
                        int readBytes = Os.read(fileDescriptor, data, dataIndex, 1);
                        if (readBytes >= 0) {
                            dataIndex += readBytes;
                        } else {
                            throw new RuntimeException("Some error");
                        }
                    } else if (res == 0) {
                        Log.w(TAG, "Timed out waiting for child.");
                    }
                    s = 0;
                }
                if (dataIndex == data.length) {
                    innerPid = new DataInputStream(new ByteArrayInputStream(data)).readInt();
                }
                if (innerPid == -1) {
                    Log.w(TAG, "Error reading pid from wrapped process, child may have died");
                }
            } catch (Exception ex) {
                Log.w(TAG, "Error reading pid from wrapped process, child may have died", ex);
            }
            if (innerPid > 0) {
                int parentPid = innerPid;
                while (parentPid > 0 && parentPid != i) {
                    parentPid = Process.getParentPid(parentPid);
                }
                if (parentPid > 0) {
                    Log.i(TAG, "Wrapped process has pid " + innerPid);
                    pid2 = innerPid;
                    usingWrapper = true;
                    this.mSocketOutStream.writeInt(pid2);
                    this.mSocketOutStream.writeBoolean(usingWrapper);
                }
                Log.w(TAG, "Wrapped process reported a pid that is not a child of the process that we forked: childPid=" + i + " innerPid=" + innerPid);
            }
        }
        pid2 = i;
        try {
            this.mSocketOutStream.writeInt(pid2);
            this.mSocketOutStream.writeBoolean(usingWrapper);
        } catch (IOException ex2) {
            throw new IllegalStateException("Error writing to command socket", ex2);
        }
    }

    private void setChildPgid(int pid) {
        try {
            Os.setpgid(pid, Os.getpgid(this.peer.getPid()));
        } catch (ErrnoException e) {
            Log.i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }
}
