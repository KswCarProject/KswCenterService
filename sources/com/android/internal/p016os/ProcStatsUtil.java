package com.android.internal.p016os;

import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.StrictMode;
import com.android.internal.annotations.VisibleForTesting;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
/* renamed from: com.android.internal.os.ProcStatsUtil */
/* loaded from: classes4.dex */
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

    public static String readTerminatedProcFile(String path, byte terminator) {
        boolean foundTerminator;
        StrictMode.ThreadPolicy savedPolicy = StrictMode.allowThreadDiskReads();
        try {
            FileInputStream is = new FileInputStream(path);
            ByteArrayOutputStream byteStream = null;
            try {
                byte[] buffer = new byte[1024];
                do {
                    int len = is.read(buffer);
                    if (len <= 0) {
                        break;
                    }
                    int terminatingIndex = -1;
                    int i = 0;
                    while (true) {
                        if (i >= len) {
                            break;
                        } else if (buffer[i] == terminator) {
                            terminatingIndex = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    foundTerminator = terminatingIndex != -1;
                    if (foundTerminator && byteStream == null) {
                        String str = new String(buffer, 0, terminatingIndex);
                        is.close();
                        return str;
                    }
                    if (byteStream == null) {
                        byteStream = new ByteArrayOutputStream(1024);
                    }
                    byteStream.write(buffer, 0, foundTerminator ? terminatingIndex : len);
                } while (!foundTerminator);
                if (byteStream == null) {
                    is.close();
                    return "";
                }
                String byteArrayOutputStream = byteStream.toString();
                is.close();
                return byteArrayOutputStream;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (th != null) {
                        try {
                            is.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    } else {
                        is.close();
                    }
                    throw th2;
                }
            }
        } catch (IOException e) {
            return null;
        } finally {
            StrictMode.setThreadPolicy(savedPolicy);
        }
    }
}
