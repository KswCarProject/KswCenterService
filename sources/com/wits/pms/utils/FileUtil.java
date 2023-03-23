package com.wits.pms.utils;

import android.os.Build;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
    public static final String OTAFileName_R = "Ksw-R-M600_OS_v";
    public static final String OTAFileName_S = "Ksw-S-M600_OS_v";
    public static final String OTAFileName_T = "Ksw-T-M600_OS_v";
    public static final String TAG = "FileUtil";

    public static boolean checkOtaFile(String fileName) {
        if (Build.VERSION.RELEASE.equals("11")) {
            if ((fileName.contains(OTAFileName_R) || fileName.contains(OTAFileName_S) || fileName.contains(OTAFileName_T)) && fileName.contains(".zip")) {
                return true;
            }
            return false;
        } else if (Build.VERSION.RELEASE.equals("12")) {
            if ((fileName.contains(OTAFileName_S) || fileName.contains(OTAFileName_T)) && fileName.contains(".zip")) {
                return true;
            }
            return false;
        } else if (!Build.VERSION.RELEASE.equals("13") || !fileName.contains(OTAFileName_T) || !fileName.contains(".zip")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkFile(String path) {
        if (new File(path).exists()) {
            return true;
        }
        Log.e(TAG, path + " not exist");
        return false;
    }

    public static boolean upPackZip(File src, String entry, File dest) {
        try {
            ZipFile zip = new ZipFile(src, Charset.forName("GBK"));
            ZipEntry zipEntry = zip.getEntry(entry);
            if (zipEntry == null) {
                return false;
            }
            InputStream in = zip.getInputStream(zipEntry);
            if (dest.exists()) {
                dest.delete();
            }
            OutputStream out = new FileOutputStream(dest);
            byte[] buf1 = new byte[1024];
            while (true) {
                int read = in.read(buf1);
                int len = read;
                if (read > 0) {
                    out.write(buf1, 0, len);
                } else {
                    in.close();
                    out.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String[] getProperties(String path) {
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(new File(path)));
            BufferedReader br = new BufferedReader(is);
            List<String> lines = new ArrayList<>();
            while (true) {
                String readLine = br.readLine();
                String line = readLine;
                if (readLine != null) {
                    Log.d(TAG, "getProperties line: " + line);
                    lines.add(line);
                } else {
                    br.close();
                    is.close();
                    return (String[]) lines.toArray(new String[lines.size()]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String[] getPayloadProperties(String path) {
        return getProperties(path);
    }

    public static String[] getMetaDataProperties(String path) {
        String[] properties = getProperties(path);
        if (properties == null) {
            Log.e(TAG, "getMetaDataProperties is null");
            return null;
        }
        String[] temp = new String[properties.length];
        for (String value : properties[0].split(SmsManager.REGEX_PREFIX_DELIMITER)) {
            if (value.contains("payload.bin")) {
                String[] third = value.split(SettingsStringUtil.DELIMITER);
                if (temp.length > 2 && third.length >= 3) {
                    temp[0] = "offset=" + third[1];
                    temp[1] = "size=" + third[2];
                }
            }
        }
        return temp;
    }
}
