package android.os;

import android.content.pm.ApplicationInfo;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Process;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Slog;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteConfig;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ZygoteProcess {
    private static final String[] INVALID_USAP_FLAGS = {"--query-abi-list", "--get-pid", "--preload-default", "--preload-package", "--preload-app", "--start-child-zygote", "--set-api-blacklist-exemptions", "--hidden-api-log-sampling-rate", "--hidden-api-statslog-sampling-rate", "--invoke-with"};
    private static final String LOG_TAG = "ZygoteProcess";
    private static final String USAP_POOL_ENABLED_DEFAULT = "false";
    private static final int ZYGOTE_CONNECT_RETRY_DELAY_MS = 50;
    private static final int ZYGOTE_CONNECT_TIMEOUT_MS = 20000;
    static final int ZYGOTE_RETRY_MILLIS = 500;
    private List<String> mApiBlacklistExemptions;
    private int mHiddenApiAccessLogSampleRate;
    private int mHiddenApiAccessStatslogSampleRate;
    private boolean mIsFirstPropCheck;
    private long mLastPropCheckTimestamp;
    private final Object mLock;
    private boolean mUsapPoolEnabled;
    private final LocalSocketAddress mUsapPoolSecondarySocketAddress;
    private final LocalSocketAddress mUsapPoolSocketAddress;
    private final LocalSocketAddress mZygoteSecondarySocketAddress;
    private final LocalSocketAddress mZygoteSocketAddress;
    private ZygoteState primaryZygoteState;
    private ZygoteState secondaryZygoteState;

    public ZygoteProcess() {
        this.mLock = new Object();
        this.mApiBlacklistExemptions = Collections.emptyList();
        this.mUsapPoolEnabled = false;
        this.mIsFirstPropCheck = true;
        this.mLastPropCheckTimestamp = 0;
        this.mZygoteSocketAddress = new LocalSocketAddress(Zygote.PRIMARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mZygoteSecondarySocketAddress = new LocalSocketAddress(Zygote.SECONDARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSocketAddress = new LocalSocketAddress(Zygote.USAP_POOL_PRIMARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSecondarySocketAddress = new LocalSocketAddress(Zygote.USAP_POOL_SECONDARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
    }

    public ZygoteProcess(LocalSocketAddress primarySocketAddress, LocalSocketAddress secondarySocketAddress) {
        this.mLock = new Object();
        this.mApiBlacklistExemptions = Collections.emptyList();
        this.mUsapPoolEnabled = false;
        this.mIsFirstPropCheck = true;
        this.mLastPropCheckTimestamp = 0;
        this.mZygoteSocketAddress = primarySocketAddress;
        this.mZygoteSecondarySocketAddress = secondarySocketAddress;
        this.mUsapPoolSocketAddress = null;
        this.mUsapPoolSecondarySocketAddress = null;
    }

    public LocalSocketAddress getPrimarySocketAddress() {
        return this.mZygoteSocketAddress;
    }

    private static class ZygoteState implements AutoCloseable {
        private final List<String> mAbiList;
        private boolean mClosed;
        final LocalSocketAddress mUsapSocketAddress;
        final DataInputStream mZygoteInputStream;
        final BufferedWriter mZygoteOutputWriter;
        private final LocalSocket mZygoteSessionSocket;
        final LocalSocketAddress mZygoteSocketAddress;

        private ZygoteState(LocalSocketAddress zygoteSocketAddress, LocalSocketAddress usapSocketAddress, LocalSocket zygoteSessionSocket, DataInputStream zygoteInputStream, BufferedWriter zygoteOutputWriter, List<String> abiList) {
            this.mZygoteSocketAddress = zygoteSocketAddress;
            this.mUsapSocketAddress = usapSocketAddress;
            this.mZygoteSessionSocket = zygoteSessionSocket;
            this.mZygoteInputStream = zygoteInputStream;
            this.mZygoteOutputWriter = zygoteOutputWriter;
            this.mAbiList = abiList;
        }

        static ZygoteState connect(LocalSocketAddress zygoteSocketAddress, LocalSocketAddress usapSocketAddress) throws IOException {
            LocalSocket zygoteSessionSocket = new LocalSocket();
            if (zygoteSocketAddress != null) {
                try {
                    zygoteSessionSocket.connect(zygoteSocketAddress);
                    DataInputStream zygoteInputStream = new DataInputStream(zygoteSessionSocket.getInputStream());
                    BufferedWriter zygoteOutputWriter = new BufferedWriter(new OutputStreamWriter(zygoteSessionSocket.getOutputStream()), 256);
                    return new ZygoteState(zygoteSocketAddress, usapSocketAddress, zygoteSessionSocket, zygoteInputStream, zygoteOutputWriter, ZygoteProcess.getAbiList(zygoteOutputWriter, zygoteInputStream));
                } catch (IOException ex) {
                    try {
                        zygoteSessionSocket.close();
                    } catch (IOException e) {
                    }
                    throw ex;
                }
            } else {
                throw new IllegalArgumentException("zygoteSocketAddress can't be null");
            }
        }

        /* access modifiers changed from: package-private */
        public LocalSocket getUsapSessionSocket() throws IOException {
            LocalSocket usapSessionSocket = new LocalSocket();
            usapSessionSocket.connect(this.mUsapSocketAddress);
            return usapSessionSocket;
        }

        /* access modifiers changed from: package-private */
        public boolean matches(String abi) {
            return this.mAbiList.contains(abi);
        }

        public void close() {
            try {
                this.mZygoteSessionSocket.close();
            } catch (IOException ex) {
                Log.e(ZygoteProcess.LOG_TAG, "I/O exception on routine close", ex);
            }
            this.mClosed = true;
        }

        /* access modifiers changed from: package-private */
        public boolean isClosed() {
            return this.mClosed;
        }
    }

    public final Process.ProcessStartResult start(String processClass, String niceName, int uid, int gid, int[] gids, int runtimeFlags, int mountExternal, int targetSdkVersion, String seInfo, String abi, String instructionSet, String appDataDir, String invokeWith, String packageName, boolean useUsapPool, String[] zygoteArgs) {
        if (fetchUsapPoolEnabledPropWithMinInterval()) {
            informZygotesOfUsapPoolStatus();
        }
        try {
            return startViaZygote(processClass, niceName, uid, gid, gids, runtimeFlags, mountExternal, targetSdkVersion, seInfo, abi, instructionSet, appDataDir, invokeWith, false, packageName, useUsapPool, zygoteArgs);
        } catch (ZygoteStartFailedEx e) {
            Log.e(LOG_TAG, "Starting VM process through Zygote failed");
            throw new RuntimeException("Starting VM process through Zygote failed", e);
        }
    }

    /* access modifiers changed from: private */
    @GuardedBy({"mLock"})
    public static List<String> getAbiList(BufferedWriter writer, DataInputStream inputStream) throws IOException {
        writer.write("1");
        writer.newLine();
        writer.write("--query-abi-list");
        writer.newLine();
        writer.flush();
        byte[] bytes = new byte[inputStream.readInt()];
        inputStream.readFully(bytes);
        return Arrays.asList(new String(bytes, StandardCharsets.US_ASCII).split(SmsManager.REGEX_PREFIX_DELIMITER));
    }

    @GuardedBy({"mLock"})
    private Process.ProcessStartResult zygoteSendArgsAndGetResult(ZygoteState zygoteState, boolean useUsapPool, ArrayList<String> args) throws ZygoteStartFailedEx {
        Iterator<String> it = args.iterator();
        while (it.hasNext()) {
            String arg = it.next();
            if (arg.indexOf(10) >= 0) {
                throw new ZygoteStartFailedEx("Embedded newlines not allowed");
            } else if (arg.indexOf(13) >= 0) {
                throw new ZygoteStartFailedEx("Embedded carriage returns not allowed");
            }
        }
        String msgStr = args.size() + "\n" + String.join("\n", args) + "\n";
        if (useUsapPool && this.mUsapPoolEnabled && canAttemptUsap(args)) {
            try {
                return attemptUsapSendArgsAndGetResult(zygoteState, msgStr);
            } catch (IOException ex) {
                Log.e(LOG_TAG, "IO Exception while communicating with USAP pool - " + ex.getMessage());
            }
        }
        return attemptZygoteSendArgsAndGetResult(zygoteState, msgStr);
    }

    private Process.ProcessStartResult attemptZygoteSendArgsAndGetResult(ZygoteState zygoteState, String msgStr) throws ZygoteStartFailedEx {
        try {
            BufferedWriter zygoteWriter = zygoteState.mZygoteOutputWriter;
            DataInputStream zygoteInputStream = zygoteState.mZygoteInputStream;
            zygoteWriter.write(msgStr);
            zygoteWriter.flush();
            Process.ProcessStartResult result = new Process.ProcessStartResult();
            result.pid = zygoteInputStream.readInt();
            result.usingWrapper = zygoteInputStream.readBoolean();
            if (result.pid >= 0) {
                return result;
            }
            throw new ZygoteStartFailedEx("fork() failed");
        } catch (IOException ex) {
            zygoteState.close();
            Log.e(LOG_TAG, "IO Exception while communicating with Zygote - " + ex.toString());
            throw new ZygoteStartFailedEx((Throwable) ex);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0045, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        if (r0 != null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
        if (r1 != null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0056, code lost:
        r0.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.os.Process.ProcessStartResult attemptUsapSendArgsAndGetResult(android.os.ZygoteProcess.ZygoteState r8, java.lang.String r9) throws android.os.ZygoteStartFailedEx, java.io.IOException {
        /*
            r7 = this;
            android.net.LocalSocket r0 = r8.getUsapSessionSocket()
            r1 = 0
            java.io.BufferedWriter r2 = new java.io.BufferedWriter     // Catch:{ Throwable -> 0x0047 }
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ Throwable -> 0x0047 }
            java.io.OutputStream r4 = r0.getOutputStream()     // Catch:{ Throwable -> 0x0047 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0047 }
            r4 = 256(0x100, float:3.59E-43)
            r2.<init>(r3, r4)     // Catch:{ Throwable -> 0x0047 }
            java.io.DataInputStream r3 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x0047 }
            java.io.InputStream r4 = r0.getInputStream()     // Catch:{ Throwable -> 0x0047 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0047 }
            r2.write(r9)     // Catch:{ Throwable -> 0x0047 }
            r2.flush()     // Catch:{ Throwable -> 0x0047 }
            android.os.Process$ProcessStartResult r4 = new android.os.Process$ProcessStartResult     // Catch:{ Throwable -> 0x0047 }
            r4.<init>()     // Catch:{ Throwable -> 0x0047 }
            int r5 = r3.readInt()     // Catch:{ Throwable -> 0x0047 }
            r4.pid = r5     // Catch:{ Throwable -> 0x0047 }
            r5 = 0
            r4.usingWrapper = r5     // Catch:{ Throwable -> 0x0047 }
            int r5 = r4.pid     // Catch:{ Throwable -> 0x0047 }
            if (r5 < 0) goto L_0x003d
            if (r0 == 0) goto L_0x003c
            r0.close()
        L_0x003c:
            return r4
        L_0x003d:
            android.os.ZygoteStartFailedEx r5 = new android.os.ZygoteStartFailedEx     // Catch:{ Throwable -> 0x0047 }
            java.lang.String r6 = "USAP specialization failed"
            r5.<init>((java.lang.String) r6)     // Catch:{ Throwable -> 0x0047 }
            throw r5     // Catch:{ Throwable -> 0x0047 }
        L_0x0045:
            r2 = move-exception
            goto L_0x0049
        L_0x0047:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0045 }
        L_0x0049:
            if (r0 == 0) goto L_0x0059
            if (r1 == 0) goto L_0x0056
            r0.close()     // Catch:{ Throwable -> 0x0051 }
            goto L_0x0059
        L_0x0051:
            r3 = move-exception
            r1.addSuppressed(r3)
            goto L_0x0059
        L_0x0056:
            r0.close()
        L_0x0059:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.ZygoteProcess.attemptUsapSendArgsAndGetResult(android.os.ZygoteProcess$ZygoteState, java.lang.String):android.os.Process$ProcessStartResult");
    }

    private static boolean canAttemptUsap(ArrayList<String> args) {
        Iterator<String> it = args.iterator();
        while (it.hasNext()) {
            String flag = it.next();
            for (String badFlag : INVALID_USAP_FLAGS) {
                if (flag.startsWith(badFlag)) {
                    return false;
                }
            }
            if (flag.startsWith("--nice-name=")) {
                String property_value = SystemProperties.get("wrap." + flag.substring(12));
                if (!(property_value == null || property_value.length() == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Process.ProcessStartResult startViaZygote(String processClass, String niceName, int uid, int gid, int[] gids, int runtimeFlags, int mountExternal, int targetSdkVersion, String seInfo, String abi, String instructionSet, String appDataDir, String invokeWith, boolean startChildZygote, String packageName, boolean useUsapPool, String[] extraArgs) throws ZygoteStartFailedEx {
        int sz;
        String str = niceName;
        int[] iArr = gids;
        int i = mountExternal;
        String str2 = seInfo;
        String str3 = instructionSet;
        String str4 = appDataDir;
        String str5 = invokeWith;
        String str6 = packageName;
        String[] strArr = extraArgs;
        ArrayList arrayList = new ArrayList();
        arrayList.add("--runtime-args");
        arrayList.add("--setuid=" + uid);
        arrayList.add("--setgid=" + gid);
        arrayList.add("--runtime-flags=" + runtimeFlags);
        if (i == 1) {
            arrayList.add("--mount-external-default");
        } else if (i == 2) {
            arrayList.add("--mount-external-read");
        } else if (i == 3) {
            arrayList.add("--mount-external-write");
        } else if (i == 6) {
            arrayList.add("--mount-external-full");
        } else if (i == 5) {
            arrayList.add("--mount-external-installer");
        } else if (i == 4) {
            arrayList.add("--mount-external-legacy");
        }
        arrayList.add("--target-sdk-version=" + targetSdkVersion);
        if (iArr != null && iArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("--setgroups=");
            int sz2 = iArr.length;
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= sz2) {
                    break;
                }
                if (i3 != 0) {
                    sz = sz2;
                    sb.append(',');
                } else {
                    sz = sz2;
                }
                sb.append(iArr[i3]);
                i2 = i3 + 1;
                sz2 = sz;
                int i4 = uid;
            }
            arrayList.add(sb.toString());
        }
        if (str != null) {
            arrayList.add("--nice-name=" + str);
        }
        if (str2 != null) {
            arrayList.add("--seinfo=" + str2);
        }
        if (str3 != null) {
            arrayList.add("--instruction-set=" + str3);
        }
        if (str4 != null) {
            arrayList.add("--app-data-dir=" + str4);
        }
        if (str5 != null) {
            arrayList.add("--invoke-with");
            arrayList.add(str5);
        }
        if (startChildZygote) {
            arrayList.add("--start-child-zygote");
        }
        if (str6 != null) {
            arrayList.add("--package-name=" + str6);
        }
        arrayList.add(processClass);
        if (strArr != null) {
            Collections.addAll(arrayList, strArr);
        }
        synchronized (this.mLock) {
            try {
                Process.ProcessStartResult zygoteSendArgsAndGetResult = zygoteSendArgsAndGetResult(openZygoteSocketIfNeeded(abi), useUsapPool, arrayList);
                return zygoteSendArgsAndGetResult;
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        }
    }

    private boolean fetchUsapPoolEnabledProp() {
        boolean origVal = this.mUsapPoolEnabled;
        if (!Zygote.getConfigurationProperty(ZygoteConfig.USAP_POOL_ENABLED, USAP_POOL_ENABLED_DEFAULT).isEmpty()) {
            this.mUsapPoolEnabled = Zygote.getConfigurationPropertyBoolean(ZygoteConfig.USAP_POOL_ENABLED, Boolean.valueOf(Boolean.parseBoolean(USAP_POOL_ENABLED_DEFAULT)));
        }
        boolean valueChanged = origVal != this.mUsapPoolEnabled;
        if (valueChanged) {
            Log.i(LOG_TAG, "usapPoolEnabled = " + this.mUsapPoolEnabled);
        }
        return valueChanged;
    }

    private boolean fetchUsapPoolEnabledPropWithMinInterval() {
        long currentTimestamp = SystemClock.elapsedRealtime();
        if (SystemProperties.get("dalvik.vm.boot-image", "").endsWith("apex.art") && currentTimestamp <= 15000) {
            return false;
        }
        if (!this.mIsFirstPropCheck && currentTimestamp - this.mLastPropCheckTimestamp < 60000) {
            return false;
        }
        this.mIsFirstPropCheck = false;
        this.mLastPropCheckTimestamp = currentTimestamp;
        return fetchUsapPoolEnabledProp();
    }

    public void close() {
        if (this.primaryZygoteState != null) {
            this.primaryZygoteState.close();
        }
        if (this.secondaryZygoteState != null) {
            this.secondaryZygoteState.close();
        }
    }

    public void establishZygoteConnectionForAbi(String abi) {
        try {
            synchronized (this.mLock) {
                openZygoteSocketIfNeeded(abi);
            }
        } catch (ZygoteStartFailedEx ex) {
            throw new RuntimeException("Unable to connect to zygote for abi: " + abi, ex);
        }
    }

    public int getZygotePid(String abi) {
        int parseInt;
        try {
            synchronized (this.mLock) {
                ZygoteState state = openZygoteSocketIfNeeded(abi);
                state.mZygoteOutputWriter.write("1");
                state.mZygoteOutputWriter.newLine();
                state.mZygoteOutputWriter.write("--get-pid");
                state.mZygoteOutputWriter.newLine();
                state.mZygoteOutputWriter.flush();
                byte[] bytes = new byte[state.mZygoteInputStream.readInt()];
                state.mZygoteInputStream.readFully(bytes);
                parseInt = Integer.parseInt(new String(bytes, StandardCharsets.US_ASCII));
            }
            return parseInt;
        } catch (Exception ex) {
            throw new RuntimeException("Failure retrieving pid", ex);
        }
    }

    public boolean setApiBlacklistExemptions(List<String> exemptions) {
        boolean ok;
        synchronized (this.mLock) {
            this.mApiBlacklistExemptions = exemptions;
            ok = maybeSetApiBlacklistExemptions(this.primaryZygoteState, true);
            if (ok) {
                ok = maybeSetApiBlacklistExemptions(this.secondaryZygoteState, true);
            }
        }
        return ok;
    }

    public void setHiddenApiAccessLogSampleRate(int rate) {
        synchronized (this.mLock) {
            this.mHiddenApiAccessLogSampleRate = rate;
            maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
        }
    }

    public void setHiddenApiAccessStatslogSampleRate(int rate) {
        synchronized (this.mLock) {
            this.mHiddenApiAccessStatslogSampleRate = rate;
            maybeSetHiddenApiAccessStatslogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessStatslogSampleRate(this.secondaryZygoteState);
        }
    }

    @GuardedBy({"mLock"})
    private boolean maybeSetApiBlacklistExemptions(ZygoteState state, boolean sendIfEmpty) {
        if (state == null || state.isClosed()) {
            Slog.e(LOG_TAG, "Can't set API blacklist exemptions: no zygote connection");
            return false;
        } else if (!sendIfEmpty && this.mApiBlacklistExemptions.isEmpty()) {
            return true;
        } else {
            try {
                state.mZygoteOutputWriter.write(Integer.toString(this.mApiBlacklistExemptions.size() + 1));
                state.mZygoteOutputWriter.newLine();
                state.mZygoteOutputWriter.write("--set-api-blacklist-exemptions");
                state.mZygoteOutputWriter.newLine();
                for (int i = 0; i < this.mApiBlacklistExemptions.size(); i++) {
                    state.mZygoteOutputWriter.write(this.mApiBlacklistExemptions.get(i));
                    state.mZygoteOutputWriter.newLine();
                }
                state.mZygoteOutputWriter.flush();
                int status = state.mZygoteInputStream.readInt();
                if (status != 0) {
                    Slog.e(LOG_TAG, "Failed to set API blacklist exemptions; status " + status);
                }
                return true;
            } catch (IOException ioe) {
                Slog.e(LOG_TAG, "Failed to set API blacklist exemptions", ioe);
                this.mApiBlacklistExemptions = Collections.emptyList();
                return false;
            }
        }
    }

    private void maybeSetHiddenApiAccessLogSampleRate(ZygoteState state) {
        if (state != null && !state.isClosed() && this.mHiddenApiAccessLogSampleRate != -1) {
            try {
                state.mZygoteOutputWriter.write(Integer.toString(1));
                state.mZygoteOutputWriter.newLine();
                BufferedWriter bufferedWriter = state.mZygoteOutputWriter;
                bufferedWriter.write("--hidden-api-log-sampling-rate=" + this.mHiddenApiAccessLogSampleRate);
                state.mZygoteOutputWriter.newLine();
                state.mZygoteOutputWriter.flush();
                int status = state.mZygoteInputStream.readInt();
                if (status != 0) {
                    Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate; status " + status);
                }
            } catch (IOException ioe) {
                Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate", ioe);
            }
        }
    }

    private void maybeSetHiddenApiAccessStatslogSampleRate(ZygoteState state) {
        if (state != null && !state.isClosed() && this.mHiddenApiAccessStatslogSampleRate != -1) {
            try {
                state.mZygoteOutputWriter.write(Integer.toString(1));
                state.mZygoteOutputWriter.newLine();
                BufferedWriter bufferedWriter = state.mZygoteOutputWriter;
                bufferedWriter.write("--hidden-api-statslog-sampling-rate=" + this.mHiddenApiAccessStatslogSampleRate);
                state.mZygoteOutputWriter.newLine();
                state.mZygoteOutputWriter.flush();
                int status = state.mZygoteInputStream.readInt();
                if (status != 0) {
                    Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate; status " + status);
                }
            } catch (IOException ioe) {
                Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate", ioe);
            }
        }
    }

    @GuardedBy({"mLock"})
    private void attemptConnectionToPrimaryZygote() throws IOException {
        if (this.primaryZygoteState == null || this.primaryZygoteState.isClosed()) {
            this.primaryZygoteState = ZygoteState.connect(this.mZygoteSocketAddress, this.mUsapPoolSocketAddress);
            maybeSetApiBlacklistExemptions(this.primaryZygoteState, false);
            maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessStatslogSampleRate(this.primaryZygoteState);
        }
    }

    @GuardedBy({"mLock"})
    private void attemptConnectionToSecondaryZygote() throws IOException {
        if (this.secondaryZygoteState == null || this.secondaryZygoteState.isClosed()) {
            this.secondaryZygoteState = ZygoteState.connect(this.mZygoteSecondarySocketAddress, this.mUsapPoolSecondarySocketAddress);
            maybeSetApiBlacklistExemptions(this.secondaryZygoteState, false);
            maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
            maybeSetHiddenApiAccessStatslogSampleRate(this.secondaryZygoteState);
        }
    }

    @GuardedBy({"mLock"})
    private ZygoteState openZygoteSocketIfNeeded(String abi) throws ZygoteStartFailedEx {
        try {
            attemptConnectionToPrimaryZygote();
            if (this.primaryZygoteState.matches(abi)) {
                return this.primaryZygoteState;
            }
            if (this.mZygoteSecondarySocketAddress != null) {
                attemptConnectionToSecondaryZygote();
                if (this.secondaryZygoteState.matches(abi)) {
                    return this.secondaryZygoteState;
                }
            }
            throw new ZygoteStartFailedEx("Unsupported zygote ABI: " + abi);
        } catch (IOException ioe) {
            throw new ZygoteStartFailedEx("Error connecting to zygote", ioe);
        }
    }

    public boolean preloadApp(ApplicationInfo appInfo, String abi) throws ZygoteStartFailedEx, IOException {
        boolean z;
        synchronized (this.mLock) {
            ZygoteState state = openZygoteSocketIfNeeded(abi);
            state.mZygoteOutputWriter.write("2");
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write("--preload-app");
            state.mZygoteOutputWriter.newLine();
            Parcel parcel = Parcel.obtain();
            z = false;
            appInfo.writeToParcel(parcel, 0);
            String encodedParcelData = Base64.getEncoder().encodeToString(parcel.marshall());
            parcel.recycle();
            state.mZygoteOutputWriter.write(encodedParcelData);
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.flush();
            if (state.mZygoteInputStream.readInt() == 0) {
                z = true;
            }
        }
        return z;
    }

    public boolean preloadPackageForAbi(String packagePath, String libsPath, String libFileName, String cacheKey, String abi) throws ZygoteStartFailedEx, IOException {
        boolean z;
        synchronized (this.mLock) {
            ZygoteState state = openZygoteSocketIfNeeded(abi);
            state.mZygoteOutputWriter.write("5");
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write("--preload-package");
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write(packagePath);
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write(libsPath);
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write(libFileName);
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write(cacheKey);
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.flush();
            z = state.mZygoteInputStream.readInt() == 0;
        }
        return z;
    }

    public boolean preloadDefault(String abi) throws ZygoteStartFailedEx, IOException {
        boolean z;
        synchronized (this.mLock) {
            ZygoteState state = openZygoteSocketIfNeeded(abi);
            state.mZygoteOutputWriter.write("1");
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.write("--preload-default");
            state.mZygoteOutputWriter.newLine();
            state.mZygoteOutputWriter.flush();
            z = state.mZygoteInputStream.readInt() == 0;
        }
        return z;
    }

    public static void waitForConnectionToZygote(String zygoteSocketName) {
        waitForConnectionToZygote(new LocalSocketAddress(zygoteSocketName, LocalSocketAddress.Namespace.RESERVED));
    }

    public static void waitForConnectionToZygote(LocalSocketAddress zygoteSocketAddress) {
        int n = 400;
        while (n >= 0) {
            try {
                ZygoteState.connect(zygoteSocketAddress, (LocalSocketAddress) null).close();
                return;
            } catch (IOException ioe) {
                Log.w(LOG_TAG, "Got error connecting to zygote, retrying. msg= " + ioe.getMessage());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                n--;
            }
        }
        Slog.wtf(LOG_TAG, "Failed to connect to Zygote through socket " + zygoteSocketAddress.getName());
    }

    private void informZygotesOfUsapPoolStatus() {
        String command = "1\n--usap-pool-enabled=" + this.mUsapPoolEnabled + "\n";
        synchronized (this.mLock) {
            try {
                attemptConnectionToPrimaryZygote();
                this.primaryZygoteState.mZygoteOutputWriter.write(command);
                this.primaryZygoteState.mZygoteOutputWriter.flush();
                try {
                    if (this.mZygoteSecondarySocketAddress != null) {
                        try {
                            attemptConnectionToSecondaryZygote();
                            this.secondaryZygoteState.mZygoteOutputWriter.write(command);
                            this.secondaryZygoteState.mZygoteOutputWriter.flush();
                            this.secondaryZygoteState.mZygoteInputStream.readInt();
                        } catch (IOException ioe) {
                            throw new IllegalStateException("USAP pool state change cause an irrecoverable error", ioe);
                        } catch (IOException e) {
                        }
                    }
                    this.primaryZygoteState.mZygoteInputStream.readInt();
                } catch (IOException ioe2) {
                    throw new IllegalStateException("USAP pool state change cause an irrecoverable error", ioe2);
                } catch (Throwable ioe3) {
                    throw ioe3;
                }
            } catch (IOException ioe4) {
                this.mUsapPoolEnabled = !this.mUsapPoolEnabled;
                Log.w(LOG_TAG, "Failed to inform zygotes of USAP pool status: " + ioe4.getMessage());
            }
        }
    }

    public ChildZygoteProcess startChildZygote(String processClass, String niceName, int uid, int gid, int[] gids, int runtimeFlags, String seInfo, String abi, String acceptedAbiList, String instructionSet, int uidRangeStart, int uidRangeEnd) {
        LocalSocketAddress serverAddress = new LocalSocketAddress(processClass + "/" + UUID.randomUUID().toString());
        try {
            return new ChildZygoteProcess(serverAddress, startViaZygote(processClass, niceName, uid, gid, gids, runtimeFlags, 0, 0, seInfo, abi, instructionSet, (String) null, (String) null, true, (String) null, false, new String[]{Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG + serverAddress.getName(), Zygote.CHILD_ZYGOTE_ABI_LIST_ARG + acceptedAbiList, Zygote.CHILD_ZYGOTE_UID_RANGE_START + uidRangeStart, Zygote.CHILD_ZYGOTE_UID_RANGE_END + uidRangeEnd}).pid);
        } catch (ZygoteStartFailedEx e) {
            throw new RuntimeException("Starting child-zygote through Zygote failed", e);
        }
    }
}
