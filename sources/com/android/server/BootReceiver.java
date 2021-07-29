package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.provider.Downloads;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Slog;
import android.util.StatsLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.util.FastXmlSerializer;
import com.ibm.icu.text.DateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlSerializer;

public class BootReceiver extends BroadcastReceiver {
    private static final String FSCK_FS_MODIFIED = "FILE SYSTEM WAS MODIFIED";
    private static final String FSCK_PASS_PATTERN = "Pass ([1-9]E?):";
    private static final String FSCK_TREE_OPTIMIZATION_PATTERN = "Inode [0-9]+ extent tree.*could be shorter";
    private static final int FS_STAT_FS_FIXED = 1024;
    private static final String FS_STAT_PATTERN = "fs_stat,[^,]*/([^/,]+),(0x[0-9a-fA-F]+)";
    private static final String LAST_HEADER_FILE = "last-header.txt";
    private static final String[] LAST_KMSG_FILES = {"/sys/fs/pstore/console-ramoops", "/proc/last_kmsg"};
    private static final String LAST_SHUTDOWN_TIME_PATTERN = "powerctl_shutdown_time_ms:([0-9]+):([0-9]+)";
    private static final String LOG_FILES_FILE = "log-files.xml";
    /* access modifiers changed from: private */
    public static final int LOG_SIZE = (SystemProperties.getInt("ro.debuggable", 0) == 1 ? 98304 : 65536);
    private static final String METRIC_SHUTDOWN_TIME_START = "begin_shutdown";
    private static final String METRIC_SYSTEM_SERVER = "shutdown_system_server";
    private static final String[] MOUNT_DURATION_PROPS_POSTFIX = {"early", PhoneConstants.APN_TYPE_DEFAULT, "late"};
    private static final String OLD_UPDATER_CLASS = "com.google.android.systemupdater.SystemUpdateReceiver";
    private static final String OLD_UPDATER_PACKAGE = "com.google.android.systemupdater";
    private static final String SHUTDOWN_METRICS_FILE = "/data/system/shutdown-metrics.txt";
    private static final String SHUTDOWN_TRON_METRICS_PREFIX = "shutdown_";
    private static final String TAG = "BootReceiver";
    private static final String TAG_TOMBSTONE = "SYSTEM_TOMBSTONE";
    /* access modifiers changed from: private */
    public static final File TOMBSTONE_DIR = new File("/data/tombstones");
    private static final int UMOUNT_STATUS_NOT_AVAILABLE = 4;
    private static final File lastHeaderFile = new File(Environment.getDataSystemDirectory(), LAST_HEADER_FILE);
    private static final AtomicFile sFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), LOG_FILES_FILE), "log-files");
    private static FileObserver sTombstoneObserver = null;

    public void onReceive(final Context context, Intent intent) {
        new Thread() {
            public void run() {
                try {
                    BootReceiver.this.logBootEvents(context);
                } catch (Exception e) {
                    Slog.e(BootReceiver.TAG, "Can't log boot events", e);
                }
                boolean onlyCore = false;
                try {
                    onlyCore = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isOnlyCoreApps();
                } catch (RemoteException e2) {
                }
                if (!onlyCore) {
                    try {
                        BootReceiver.this.removeOldUpdatePackages(context);
                    } catch (Exception e3) {
                        Slog.e(BootReceiver.TAG, "Can't remove old update packages", e3);
                    }
                }
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void removeOldUpdatePackages(Context context) {
        Downloads.removeAllDownloadsByPackage(context, OLD_UPDATER_PACKAGE, OLD_UPDATER_CLASS);
    }

    private String getPreviousBootHeaders() {
        try {
            return FileUtils.readTextFile(lastHeaderFile, 0, (String) null);
        } catch (IOException e) {
            return null;
        }
    }

    private String getCurrentBootHeaders() throws IOException {
        StringBuilder sb = new StringBuilder(512);
        sb.append("Build: ");
        sb.append(Build.FINGERPRINT);
        sb.append("\n");
        sb.append("Hardware: ");
        sb.append(Build.BOARD);
        sb.append("\n");
        sb.append("Revision: ");
        sb.append(SystemProperties.get("ro.revision", ""));
        sb.append("\n");
        sb.append("Bootloader: ");
        sb.append(Build.BOOTLOADER);
        sb.append("\n");
        sb.append("Radio: ");
        sb.append(Build.getRadioVersion());
        sb.append("\n");
        sb.append("Kernel: ");
        sb.append(FileUtils.readTextFile(new File("/proc/version"), 1024, "...\n"));
        sb.append("\n");
        return sb.toString();
    }

    private String getBootHeadersToLogAndUpdate() throws IOException {
        String oldHeaders = getPreviousBootHeaders();
        String newHeaders = getCurrentBootHeaders();
        try {
            FileUtils.stringToFile(lastHeaderFile, newHeaders);
        } catch (IOException e) {
            Slog.e(TAG, "Error writing " + lastHeaderFile, e);
        }
        if (oldHeaders == null) {
            return "isPrevious: false\n" + newHeaders;
        }
        return "isPrevious: true\n" + oldHeaders;
    }

    /* access modifiers changed from: private */
    public void logBootEvents(Context ctx) throws IOException {
        DropBoxManager db = (DropBoxManager) ctx.getSystemService(Context.DROPBOX_SERVICE);
        String headers = getBootHeadersToLogAndUpdate();
        String bootReason = SystemProperties.get("ro.boot.bootreason", (String) null);
        String recovery = RecoverySystem.handleAftermath(ctx);
        if (!(recovery == null || db == null)) {
            db.addText("SYSTEM_RECOVERY_LOG", headers + recovery);
        }
        String lastKmsgFooter = "";
        if (bootReason != null) {
            StringBuilder sb = new StringBuilder(512);
            sb.append("\n");
            sb.append("Boot info:\n");
            sb.append("Last boot reason: ");
            sb.append(bootReason);
            sb.append("\n");
            lastKmsgFooter = sb.toString();
        }
        String lastKmsgFooter2 = lastKmsgFooter;
        HashMap<String, Long> timestamps = readTimestamps();
        if (SystemProperties.getLong("ro.runtime.firstboot", 0) == 0) {
            if (!StorageManager.inCryptKeeperBounce()) {
                SystemProperties.set("ro.runtime.firstboot", Long.toString(System.currentTimeMillis()));
            }
            if (db != null) {
                db.addText("SYSTEM_BOOT", headers);
            }
            HashMap<String, Long> hashMap = timestamps;
            String str = headers;
            String str2 = lastKmsgFooter2;
            addFileWithFootersToDropBox(db, hashMap, str, str2, "/proc/last_kmsg", -LOG_SIZE, "SYSTEM_LAST_KMSG");
            addFileWithFootersToDropBox(db, hashMap, str, str2, "/sys/fs/pstore/console-ramoops", -LOG_SIZE, "SYSTEM_LAST_KMSG");
            addFileWithFootersToDropBox(db, hashMap, str, str2, "/sys/fs/pstore/console-ramoops-0", -LOG_SIZE, "SYSTEM_LAST_KMSG");
            addFileToDropBox(db, hashMap, str, "/cache/recovery/log", -LOG_SIZE, "SYSTEM_RECOVERY_LOG");
            addFileToDropBox(db, hashMap, str, "/cache/recovery/last_kmsg", -LOG_SIZE, "SYSTEM_RECOVERY_KMSG");
            addAuditErrorsToDropBox(db, timestamps, headers, -LOG_SIZE, "SYSTEM_AUDIT");
        } else if (db != null) {
            db.addText("SYSTEM_RESTART", headers);
        }
        logFsShutdownTime();
        logFsMountTime();
        addFsckErrorsToDropBoxAndLogFsStat(db, timestamps, headers, -LOG_SIZE, "SYSTEM_FSCK");
        logSystemServerShutdownTimeMetrics();
        File[] tombstoneFiles = TOMBSTONE_DIR.listFiles();
        int i = 0;
        while (true) {
            int i2 = i;
            if (tombstoneFiles == null || i2 >= tombstoneFiles.length) {
                writeTimestamps(timestamps);
                final DropBoxManager dropBoxManager = db;
                final String str3 = headers;
                sTombstoneObserver = new FileObserver(TOMBSTONE_DIR.getPath(), 256) {
                    public void onEvent(int event, String path) {
                        HashMap<String, Long> timestamps = BootReceiver.readTimestamps();
                        try {
                            File file = new File(BootReceiver.TOMBSTONE_DIR, path);
                            if (file.isFile() && file.getName().startsWith("tombstone_")) {
                                BootReceiver.addFileToDropBox(dropBoxManager, timestamps, str3, file.getPath(), BootReceiver.LOG_SIZE, BootReceiver.TAG_TOMBSTONE);
                            }
                        } catch (IOException e) {
                            Slog.e(BootReceiver.TAG, "Can't log tombstone", e);
                        }
                        BootReceiver.this.writeTimestamps(timestamps);
                    }
                };
                sTombstoneObserver.startWatching();
            } else {
                if (tombstoneFiles[i2].isFile()) {
                    addFileToDropBox(db, timestamps, headers, tombstoneFiles[i2].getPath(), LOG_SIZE, TAG_TOMBSTONE);
                }
                i = i2 + 1;
            }
        }
        writeTimestamps(timestamps);
        final DropBoxManager dropBoxManager2 = db;
        final String str32 = headers;
        sTombstoneObserver = new FileObserver(TOMBSTONE_DIR.getPath(), 256) {
            public void onEvent(int event, String path) {
                HashMap<String, Long> timestamps = BootReceiver.readTimestamps();
                try {
                    File file = new File(BootReceiver.TOMBSTONE_DIR, path);
                    if (file.isFile() && file.getName().startsWith("tombstone_")) {
                        BootReceiver.addFileToDropBox(dropBoxManager2, timestamps, str32, file.getPath(), BootReceiver.LOG_SIZE, BootReceiver.TAG_TOMBSTONE);
                    }
                } catch (IOException e) {
                    Slog.e(BootReceiver.TAG, "Can't log tombstone", e);
                }
                BootReceiver.this.writeTimestamps(timestamps);
            }
        };
        sTombstoneObserver.startWatching();
    }

    /* access modifiers changed from: private */
    public static void addFileToDropBox(DropBoxManager db, HashMap<String, Long> timestamps, String headers, String filename, int maxSize, String tag) throws IOException {
        addFileWithFootersToDropBox(db, timestamps, headers, "", filename, maxSize, tag);
    }

    private static void addFileWithFootersToDropBox(DropBoxManager db, HashMap<String, Long> timestamps, String headers, String footers, String filename, int maxSize, String tag) throws IOException {
        if (db != null && db.isTagEnabled(tag)) {
            File file = new File(filename);
            long fileTime = file.lastModified();
            if (fileTime > 0) {
                if (!timestamps.containsKey(filename) || timestamps.get(filename).longValue() != fileTime) {
                    timestamps.put(filename, Long.valueOf(fileTime));
                    String fileContents = FileUtils.readTextFile(file, maxSize, "[[TRUNCATED]]\n");
                    String text = headers + fileContents + footers;
                    if (tag.equals(TAG_TOMBSTONE) && fileContents.contains(">>> system_server <<<")) {
                        addTextToDropBox(db, "system_server_native_crash", text, filename, maxSize);
                    }
                    if (tag.equals(TAG_TOMBSTONE)) {
                        StatsLog.write(186);
                    }
                    addTextToDropBox(db, tag, text, filename, maxSize);
                }
            }
        }
    }

    private static void addTextToDropBox(DropBoxManager db, String tag, String text, String filename, int maxSize) {
        Slog.i(TAG, "Copying " + filename + " to DropBox (" + tag + ")");
        db.addText(tag, text);
        EventLog.writeEvent((int) DropboxLogTags.DROPBOX_FILE_COPY, filename, Integer.valueOf(maxSize), tag);
    }

    private static void addAuditErrorsToDropBox(DropBoxManager db, HashMap<String, Long> timestamps, String headers, int maxSize, String tag) throws IOException {
        if (db != null && db.isTagEnabled(tag)) {
            Slog.i(TAG, "Copying audit failures to DropBox");
            File file = new File("/proc/last_kmsg");
            long fileTime = file.lastModified();
            if (fileTime <= 0) {
                file = new File("/sys/fs/pstore/console-ramoops");
                fileTime = file.lastModified();
                if (fileTime <= 0) {
                    file = new File("/sys/fs/pstore/console-ramoops-0");
                    fileTime = file.lastModified();
                }
            }
            if (fileTime > 0) {
                if (!timestamps.containsKey(tag) || timestamps.get(tag).longValue() != fileTime) {
                    timestamps.put(tag, Long.valueOf(fileTime));
                    String log = FileUtils.readTextFile(file, maxSize, "[[TRUNCATED]]\n");
                    StringBuilder sb = new StringBuilder();
                    for (String line : log.split("\n")) {
                        if (line.contains("audit")) {
                            sb.append(line + "\n");
                        }
                    }
                    Slog.i(TAG, "Copied " + sb.toString().length() + " worth of audits to DropBox");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(headers);
                    sb2.append(sb.toString());
                    db.addText(tag, sb2.toString());
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000b, code lost:
        if (r6.isTagEnabled(r25) == false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void addFsckErrorsToDropBoxAndLogFsStat(android.os.DropBoxManager r21, java.util.HashMap<java.lang.String, java.lang.Long> r22, java.lang.String r23, int r24, java.lang.String r25) throws java.io.IOException {
        /*
            r6 = r21
            r0 = 1
            if (r6 == 0) goto L_0x0010
            r7 = r25
            boolean r1 = r6.isTagEnabled(r7)
            if (r1 != 0) goto L_0x000e
            goto L_0x0012
        L_0x000e:
            r8 = r0
            goto L_0x0014
        L_0x0010:
            r7 = r25
        L_0x0012:
            r0 = 0
            goto L_0x000e
        L_0x0014:
            r0 = 0
            java.lang.String r1 = "BootReceiver"
            java.lang.String r2 = "Checking for fsck errors"
            android.util.Slog.i(r1, r2)
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "/dev/fscklogs/log"
            r1.<init>(r2)
            r9 = r1
            long r10 = r9.lastModified()
            r1 = 0
            int r1 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r1 > 0) goto L_0x002f
            return
        L_0x002f:
            java.lang.String r1 = "[[TRUNCATED]]\n"
            r12 = r24
            java.lang.String r13 = android.os.FileUtils.readTextFile(r9, r12, r1)
            java.lang.String r1 = "fs_stat,[^,]*/([^/,]+),(0x[0-9a-fA-F]+)"
            java.util.regex.Pattern r14 = java.util.regex.Pattern.compile(r1)
            java.lang.String r1 = "\n"
            java.lang.String[] r15 = r13.split(r1)
            r1 = 0
            r2 = 0
            int r3 = r15.length
            r4 = 0
            r16 = r0
            r5 = r1
        L_0x004a:
            if (r4 >= r3) goto L_0x009d
            r0 = r15[r4]
            java.lang.String r1 = "FILE SYSTEM WAS MODIFIED"
            boolean r1 = r0.contains(r1)
            if (r1 == 0) goto L_0x005c
            r1 = 1
            r16 = r1
        L_0x0059:
            r20 = r3
            goto L_0x0096
        L_0x005c:
            java.lang.String r1 = "fs_stat"
            boolean r1 = r0.contains(r1)
            if (r1 == 0) goto L_0x0090
            java.util.regex.Matcher r1 = r14.matcher(r0)
            boolean r17 = r1.find()
            if (r17 == 0) goto L_0x0073
            handleFsckFsStat(r1, r15, r2, r5)
            r2 = r5
            goto L_0x0059
        L_0x0073:
            r18 = r1
            java.lang.String r1 = "BootReceiver"
            r19 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r20 = r3
            java.lang.String r3 = "cannot parse fs_stat:"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)
            goto L_0x0094
        L_0x0090:
            r19 = r2
            r20 = r3
        L_0x0094:
            r2 = r19
        L_0x0096:
            int r5 = r5 + 1
            int r4 = r4 + 1
            r3 = r20
            goto L_0x004a
        L_0x009d:
            r19 = r2
            if (r8 == 0) goto L_0x00b7
            if (r16 == 0) goto L_0x00b7
            java.lang.String r3 = "/dev/fscklogs/log"
            r0 = r21
            r1 = r22
            r17 = r19
            r2 = r23
            r4 = r24
            r18 = r5
            r5 = r25
            addFileToDropBox(r0, r1, r2, r3, r4, r5)
            goto L_0x00bb
        L_0x00b7:
            r18 = r5
            r17 = r19
        L_0x00bb:
            r9.delete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.BootReceiver.addFsckErrorsToDropBoxAndLogFsStat(android.os.DropBoxManager, java.util.HashMap, java.lang.String, int, java.lang.String):void");
    }

    private static void logFsMountTime() {
        for (String propPostfix : MOUNT_DURATION_PROPS_POSTFIX) {
            int duration = SystemProperties.getInt("ro.boottime.init.mount_all." + propPostfix, 0);
            if (duration != 0) {
                MetricsLogger.histogram((Context) null, "boot_mount_all_duration_" + propPostfix, duration);
            }
        }
    }

    private static void logSystemServerShutdownTimeMetrics() {
        File metricsFile = new File(SHUTDOWN_METRICS_FILE);
        String metricsStr = null;
        if (metricsFile.exists()) {
            try {
                metricsStr = FileUtils.readTextFile(metricsFile, 0, (String) null);
            } catch (IOException e) {
                Slog.e(TAG, "Problem reading " + metricsFile, e);
            }
        }
        if (!TextUtils.isEmpty(metricsStr)) {
            String duration = null;
            String start_time = null;
            String reason = null;
            String reboot = null;
            for (String keyValueStr : metricsStr.split(SmsManager.REGEX_PREFIX_DELIMITER)) {
                String[] keyValue = keyValueStr.split(SettingsStringUtil.DELIMITER);
                if (keyValue.length != 2) {
                    Slog.e(TAG, "Wrong format of shutdown metrics - " + metricsStr);
                } else {
                    if (keyValue[0].startsWith(SHUTDOWN_TRON_METRICS_PREFIX)) {
                        logTronShutdownMetric(keyValue[0], keyValue[1]);
                        if (keyValue[0].equals(METRIC_SYSTEM_SERVER)) {
                            duration = keyValue[1];
                        }
                    }
                    if (keyValue[0].equals("reboot")) {
                        reboot = keyValue[1];
                    } else if (keyValue[0].equals("reason")) {
                        reason = keyValue[1];
                    } else if (keyValue[0].equals(METRIC_SHUTDOWN_TIME_START)) {
                        start_time = keyValue[1];
                    }
                }
            }
            logStatsdShutdownAtom(reboot, reason, start_time, duration);
        }
        metricsFile.delete();
    }

    private static void logTronShutdownMetric(String metricName, String valueStr) {
        try {
            int value = Integer.parseInt(valueStr);
            if (value >= 0) {
                MetricsLogger.histogram((Context) null, metricName, value);
            }
        } catch (NumberFormatException e) {
            Slog.e(TAG, "Cannot parse metric " + metricName + " int value - " + valueStr);
        }
    }

    private static void logStatsdShutdownAtom(String rebootStr, String reasonStr, String startStr, String durationStr) {
        String str = rebootStr;
        String str2 = startStr;
        boolean reboot = false;
        String reason = "<EMPTY>";
        long start = 0;
        long duration = 0;
        if (str == null) {
            Slog.e(TAG, "No value received for reboot");
        } else if (str.equals(DateFormat.YEAR)) {
            reboot = true;
        } else if (!str.equals("n")) {
            Slog.e(TAG, "Unexpected value for reboot : " + str);
        }
        boolean reboot2 = reboot;
        if (reasonStr != null) {
            reason = reasonStr;
        } else {
            Slog.e(TAG, "No value received for shutdown reason");
        }
        if (str2 != null) {
            try {
                start = Long.parseLong(startStr);
            } catch (NumberFormatException e) {
                NumberFormatException numberFormatException = e;
                Slog.e(TAG, "Cannot parse shutdown start time: " + str2);
            }
        } else {
            Slog.e(TAG, "No value received for shutdown start time");
        }
        if (durationStr != null) {
            try {
                duration = Long.parseLong(durationStr);
            } catch (NumberFormatException e2) {
                NumberFormatException numberFormatException2 = e2;
                Slog.e(TAG, "Cannot parse shutdown duration: " + str2);
            }
        } else {
            Slog.e(TAG, "No value received for shutdown duration");
        }
        StatsLog.write(56, reboot2, reason, start, duration);
    }

    private static void logFsShutdownTime() {
        File f = null;
        String[] strArr = LAST_KMSG_FILES;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            File file = new File(strArr[i]);
            if (file.exists()) {
                f = file;
                break;
            }
            i++;
        }
        if (f != null) {
            try {
                Matcher matcher = Pattern.compile(LAST_SHUTDOWN_TIME_PATTERN, 8).matcher(FileUtils.readTextFile(f, -16384, (String) null));
                if (matcher.find()) {
                    MetricsLogger.histogram((Context) null, "boot_fs_shutdown_duration", Integer.parseInt(matcher.group(1)));
                    MetricsLogger.histogram((Context) null, "boot_fs_shutdown_umount_stat", Integer.parseInt(matcher.group(2)));
                    Slog.i(TAG, "boot_fs_shutdown," + matcher.group(1) + SmsManager.REGEX_PREFIX_DELIMITER + matcher.group(2));
                    return;
                }
                MetricsLogger.histogram((Context) null, "boot_fs_shutdown_umount_stat", 4);
                Slog.w(TAG, "boot_fs_shutdown, string not found");
            } catch (IOException e) {
                Slog.w(TAG, "cannot read last msg", e);
            }
        }
    }

    @VisibleForTesting
    public static int fixFsckFsStat(String partition, int statOrg, String[] lines, int startLineNumber, int endLineNumber) {
        String line;
        Pattern treeOptPattern;
        Pattern passPattern;
        String str = partition;
        int stat = statOrg;
        if ((stat & 1024) != 0) {
            Pattern passPattern2 = Pattern.compile(FSCK_PASS_PATTERN);
            Pattern treeOptPattern2 = Pattern.compile(FSCK_TREE_OPTIMIZATION_PATTERN);
            boolean foundOtherFix = false;
            String otherFixLine = null;
            boolean foundTimestampAdjustment = false;
            boolean foundQuotaFix = false;
            boolean foundTreeOptimization = false;
            String currentPass = "";
            int i = startLineNumber;
            while (true) {
                if (i >= endLineNumber) {
                    Pattern pattern = treeOptPattern2;
                    break;
                }
                line = lines[i];
                if (line.contains(FSCK_FS_MODIFIED)) {
                    Pattern pattern2 = passPattern2;
                    Pattern pattern3 = treeOptPattern2;
                    break;
                }
                if (line.startsWith("Pass ")) {
                    Matcher matcher = passPattern2.matcher(line);
                    if (matcher.find()) {
                        currentPass = matcher.group(1);
                    }
                    passPattern = passPattern2;
                    treeOptPattern = treeOptPattern2;
                } else if (!line.startsWith("Inode ")) {
                    passPattern = passPattern2;
                    treeOptPattern = treeOptPattern2;
                    if (line.startsWith("[QUOTA WARNING]") && currentPass.equals("5")) {
                        Slog.i(TAG, "fs_stat, partition:" + str + " found quota warning:" + line);
                        foundQuotaFix = true;
                        if (!foundTreeOptimization) {
                            otherFixLine = line;
                            break;
                        }
                    } else if (!line.startsWith("Update quota info") || !currentPass.equals("5")) {
                        if (!line.startsWith("Timestamp(s) on inode") || !line.contains("beyond 2310-04-04 are likely pre-1970") || !currentPass.equals("1")) {
                            String line2 = line.trim();
                            if (!line2.isEmpty() && !currentPass.isEmpty()) {
                                foundOtherFix = true;
                                otherFixLine = line2;
                                break;
                            }
                        } else {
                            Slog.i(TAG, "fs_stat, partition:" + str + " found timestamp adjustment:" + line);
                            if (lines[i + 1].contains("Fix? yes")) {
                                i++;
                            }
                            foundTimestampAdjustment = true;
                        }
                    }
                } else if (!treeOptPattern2.matcher(line).find() || !currentPass.equals("1")) {
                    Pattern pattern4 = treeOptPattern2;
                    foundOtherFix = true;
                    otherFixLine = line;
                } else {
                    foundTreeOptimization = true;
                    passPattern = passPattern2;
                    StringBuilder sb = new StringBuilder();
                    treeOptPattern = treeOptPattern2;
                    sb.append("fs_stat, partition:");
                    sb.append(str);
                    sb.append(" found tree optimization:");
                    sb.append(line);
                    Slog.i(TAG, sb.toString());
                }
                i++;
                passPattern2 = passPattern;
                treeOptPattern2 = treeOptPattern;
            }
            Pattern pattern42 = treeOptPattern2;
            foundOtherFix = true;
            otherFixLine = line;
            if (foundOtherFix) {
                if (otherFixLine == null) {
                    return stat;
                }
                Slog.i(TAG, "fs_stat, partition:" + str + " fix:" + otherFixLine);
                return stat;
            } else if (foundQuotaFix && !foundTreeOptimization) {
                Slog.i(TAG, "fs_stat, got quota fix without tree optimization, partition:" + str);
                return stat;
            } else if ((!foundTreeOptimization || !foundQuotaFix) && !foundTimestampAdjustment) {
                return stat;
            } else {
                Slog.i(TAG, "fs_stat, partition:" + str + " fix ignored");
                return stat & -1025;
            }
        } else {
            int i2 = endLineNumber;
            return stat;
        }
    }

    private static void handleFsckFsStat(Matcher match, String[] lines, int startLineNumber, int endLineNumber) {
        String partition = match.group(1);
        try {
            int stat = fixFsckFsStat(partition, Integer.decode(match.group(2)).intValue(), lines, startLineNumber, endLineNumber);
            MetricsLogger.histogram((Context) null, "boot_fs_stat_" + partition, stat);
            Slog.i(TAG, "fs_stat, partition:" + partition + " stat:0x" + Integer.toHexString(stat));
        } catch (NumberFormatException e) {
            Slog.w(TAG, "cannot parse fs_stat: partition:" + partition + " stat:" + match.group(2));
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0092, code lost:
        if (1 == 0) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0145, code lost:
        if (r2 != false) goto L_0x0149;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b A[Catch:{ all -> 0x00a2, Throwable -> 0x00ae, FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0099 A[SYNTHETIC, Splitter:B:37:0x0099] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.HashMap<java.lang.String, java.lang.Long> readTimestamps() {
        /*
            android.util.AtomicFile r0 = sFile
            monitor-enter(r0)
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x0151 }
            r1.<init>()     // Catch:{ all -> 0x0151 }
            r2 = 0
            android.util.AtomicFile r3 = sFile     // Catch:{ FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }
            java.io.FileInputStream r3 = r3.openRead()     // Catch:{ FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }
            r4 = 0
            org.xmlpull.v1.XmlPullParser r5 = android.util.Xml.newPullParser()     // Catch:{ Throwable -> 0x00a4 }
            java.nio.charset.Charset r6 = java.nio.charset.StandardCharsets.UTF_8     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r6 = r6.name()     // Catch:{ Throwable -> 0x00a4 }
            r5.setInput(r3, r6)     // Catch:{ Throwable -> 0x00a4 }
        L_0x001d:
            int r6 = r5.next()     // Catch:{ Throwable -> 0x00a4 }
            r7 = r6
            r8 = 1
            r9 = 2
            if (r6 == r9) goto L_0x0029
            if (r7 == r8) goto L_0x0029
            goto L_0x001d
        L_0x0029:
            if (r7 != r9) goto L_0x0099
            int r6 = r5.getDepth()     // Catch:{ Throwable -> 0x00a4 }
        L_0x002f:
            int r9 = r5.next()     // Catch:{ Throwable -> 0x00a4 }
            r7 = r9
            if (r9 == r8) goto L_0x008c
            r9 = 3
            if (r7 != r9) goto L_0x003f
            int r10 = r5.getDepth()     // Catch:{ Throwable -> 0x00a4 }
            if (r10 <= r6) goto L_0x008c
        L_0x003f:
            if (r7 == r9) goto L_0x002f
            r9 = 4
            if (r7 != r9) goto L_0x0045
            goto L_0x002f
        L_0x0045:
            java.lang.String r9 = r5.getName()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r10 = "log"
            boolean r10 = r9.equals(r10)     // Catch:{ Throwable -> 0x00a4 }
            if (r10 == 0) goto L_0x006e
            java.lang.String r10 = "filename"
            java.lang.String r10 = r5.getAttributeValue(r4, r10)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r11 = "timestamp"
            java.lang.String r11 = r5.getAttributeValue(r4, r11)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch:{ Throwable -> 0x00a4 }
            long r11 = r11.longValue()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Long r13 = java.lang.Long.valueOf(r11)     // Catch:{ Throwable -> 0x00a4 }
            r1.put(r10, r13)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x008b
        L_0x006e:
            java.lang.String r10 = "BootReceiver"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a4 }
            r11.<init>()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r12 = "Unknown tag: "
            r11.append(r12)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r12 = r5.getName()     // Catch:{ Throwable -> 0x00a4 }
            r11.append(r12)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x00a4 }
            android.util.Slog.w((java.lang.String) r10, (java.lang.String) r11)     // Catch:{ Throwable -> 0x00a4 }
            com.android.internal.util.XmlUtils.skipCurrentTag(r5)     // Catch:{ Throwable -> 0x00a4 }
        L_0x008b:
            goto L_0x002f
        L_0x008c:
            r2 = 1
            if (r3 == 0) goto L_0x0092
            r3.close()     // Catch:{ FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }
        L_0x0092:
            if (r2 != 0) goto L_0x0149
        L_0x0094:
            r1.clear()     // Catch:{ all -> 0x0151 }
            goto L_0x0149
        L_0x0099:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r8 = "no start tag found"
            r6.<init>(r8)     // Catch:{ Throwable -> 0x00a4 }
            throw r6     // Catch:{ Throwable -> 0x00a4 }
        L_0x00a2:
            r5 = move-exception
            goto L_0x00a6
        L_0x00a4:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x00a2 }
        L_0x00a6:
            if (r3 == 0) goto L_0x00b6
            if (r4 == 0) goto L_0x00b3
            r3.close()     // Catch:{ Throwable -> 0x00ae }
            goto L_0x00b6
        L_0x00ae:
            r6 = move-exception
            r4.addSuppressed(r6)     // Catch:{ FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }
            goto L_0x00b6
        L_0x00b3:
            r3.close()     // Catch:{ FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }
        L_0x00b6:
            throw r5     // Catch:{ FileNotFoundException -> 0x0123, IOException -> 0x0108, IllegalStateException -> 0x00ee, NullPointerException -> 0x00d4, XmlPullParserException -> 0x00ba }
        L_0x00b7:
            r3 = move-exception
            goto L_0x014b
        L_0x00ba:
            r3 = move-exception
            java.lang.String r4 = "BootReceiver"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r5.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r6 = "Failed parsing "
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            r5.append(r3)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00b7 }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0149
            goto L_0x0094
        L_0x00d4:
            r3 = move-exception
            java.lang.String r4 = "BootReceiver"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r5.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r6 = "Failed parsing "
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            r5.append(r3)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00b7 }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0149
            goto L_0x0094
        L_0x00ee:
            r3 = move-exception
            java.lang.String r4 = "BootReceiver"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r5.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r6 = "Failed parsing "
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            r5.append(r3)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00b7 }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0149
            goto L_0x0094
        L_0x0108:
            r3 = move-exception
            java.lang.String r4 = "BootReceiver"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r5.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r6 = "Failed parsing "
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            r5.append(r3)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00b7 }
            android.util.Slog.w((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0149
            goto L_0x0094
        L_0x0123:
            r3 = move-exception
            java.lang.String r4 = "BootReceiver"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r5.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.String r6 = "No existing last log timestamp file "
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            android.util.AtomicFile r6 = sFile     // Catch:{ all -> 0x00b7 }
            java.io.File r6 = r6.getBaseFile()     // Catch:{ all -> 0x00b7 }
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            java.lang.String r6 = "; starting empty"
            r5.append(r6)     // Catch:{ all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00b7 }
            android.util.Slog.i(r4, r5)     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x0149
            goto L_0x0094
        L_0x0149:
            monitor-exit(r0)     // Catch:{ all -> 0x0151 }
            return r1
        L_0x014b:
            if (r2 != 0) goto L_0x0150
            r1.clear()     // Catch:{ all -> 0x0151 }
        L_0x0150:
            throw r3     // Catch:{ all -> 0x0151 }
        L_0x0151:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0151 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.BootReceiver.readTimestamps():java.util.HashMap");
    }

    /* access modifiers changed from: private */
    public void writeTimestamps(HashMap<String, Long> timestamps) {
        synchronized (sFile) {
            try {
                FileOutputStream stream = sFile.startWrite();
                try {
                    XmlSerializer out = new FastXmlSerializer();
                    out.setOutput(stream, StandardCharsets.UTF_8.name());
                    out.startDocument((String) null, true);
                    out.startTag((String) null, "log-files");
                    for (String filename : timestamps.keySet()) {
                        out.startTag((String) null, "log");
                        out.attribute((String) null, "filename", filename);
                        out.attribute((String) null, "timestamp", timestamps.get(filename).toString());
                        out.endTag((String) null, "log");
                    }
                    out.endTag((String) null, "log-files");
                    out.endDocument();
                    sFile.finishWrite(stream);
                } catch (IOException e) {
                    Slog.w(TAG, "Failed to write timestamp file, using the backup: " + e);
                    sFile.failWrite(stream);
                } catch (Throwable th) {
                    throw th;
                }
            } catch (IOException e2) {
                Slog.w(TAG, "Failed to write timestamp file: " + e2);
            }
        }
    }
}
