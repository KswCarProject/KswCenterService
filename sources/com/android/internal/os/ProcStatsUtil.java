package com.android.internal.os;

import android.net.wifi.WifiEnterpriseConfig;
import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
public final class ProcStatsUtil {
    private static final boolean DEBUG = false;
    private static final int READ_SIZE = 1024;
    private static final String TAG = "ProcStatsUtil";

    private ProcStatsUtil() {
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
    public static String readNullSeparatedFile(String path) {
        String contents = readSingleLineProcFile(path);
        if (contents == null) {
            return null;
        }
        int endIndex = contents.indexOf("\u0000\u0000");
        if (endIndex != -1) {
            contents = contents.substring(0, endIndex);
        }
        return contents.replace("\u0000", WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
    public static String readSingleLineProcFile(String path) {
        return readTerminatedProcFile(path, (byte) 10);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0064, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0065, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0069, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x006a, code lost:
        r11 = r4;
        r4 = r3;
        r3 = r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readTerminatedProcFile(java.lang.String r12, byte r13) {
        /*
            android.os.StrictMode$ThreadPolicy r0 = android.os.StrictMode.allowThreadDiskReads()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0081, all -> 0x007c }
            r2.<init>(r12)     // Catch:{ IOException -> 0x0081, all -> 0x007c }
            r3 = 0
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r4]     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
        L_0x000f:
            int r6 = r2.read(r5)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            if (r6 > 0) goto L_0x0016
            goto L_0x004d
        L_0x0016:
            r7 = -1
            r8 = 0
            r9 = r8
        L_0x0019:
            if (r9 >= r6) goto L_0x0024
            byte r10 = r5[r9]     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            if (r10 != r13) goto L_0x0021
            r7 = r9
            goto L_0x0024
        L_0x0021:
            int r9 = r9 + 1
            goto L_0x0019
        L_0x0024:
            r9 = -1
            if (r7 == r9) goto L_0x0029
            r9 = 1
            goto L_0x002a
        L_0x0029:
            r9 = r8
        L_0x002a:
            if (r9 == 0) goto L_0x003a
            if (r3 != 0) goto L_0x003a
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r4.<init>(r5, r8, r7)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r2.close()     // Catch:{ IOException -> 0x0081, all -> 0x007c }
            android.os.StrictMode.setThreadPolicy(r0)
            return r4
        L_0x003a:
            if (r3 != 0) goto L_0x0042
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r10.<init>(r4)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r3 = r10
        L_0x0042:
            if (r9 == 0) goto L_0x0046
            r10 = r7
            goto L_0x0047
        L_0x0046:
            r10 = r6
        L_0x0047:
            r3.write(r5, r8, r10)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            if (r9 == 0) goto L_0x0063
        L_0x004d:
            if (r3 != 0) goto L_0x0058
            java.lang.String r4 = ""
            r2.close()     // Catch:{ IOException -> 0x0081, all -> 0x007c }
            android.os.StrictMode.setThreadPolicy(r0)
            return r4
        L_0x0058:
            java.lang.String r4 = r3.toString()     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r2.close()     // Catch:{ IOException -> 0x0081, all -> 0x007c }
            android.os.StrictMode.setThreadPolicy(r0)
            return r4
        L_0x0063:
            goto L_0x000f
        L_0x0064:
            r3 = move-exception
            r4 = r1
            goto L_0x006d
        L_0x0067:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0069 }
        L_0x0069:
            r4 = move-exception
            r11 = r4
            r4 = r3
            r3 = r11
        L_0x006d:
            if (r4 == 0) goto L_0x0078
            r2.close()     // Catch:{ Throwable -> 0x0073 }
            goto L_0x007b
        L_0x0073:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch:{ IOException -> 0x0081, all -> 0x007c }
            goto L_0x007b
        L_0x0078:
            r2.close()     // Catch:{ IOException -> 0x0081, all -> 0x007c }
        L_0x007b:
            throw r3     // Catch:{ IOException -> 0x0081, all -> 0x007c }
        L_0x007c:
            r1 = move-exception
            android.os.StrictMode.setThreadPolicy(r0)
            throw r1
        L_0x0081:
            r2 = move-exception
            android.os.StrictMode.setThreadPolicy(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ProcStatsUtil.readTerminatedProcFile(java.lang.String, byte):java.lang.String");
    }
}
