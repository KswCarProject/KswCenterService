package com.wits.pms.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static final String TAG = "FileUtil";

    public static String[] getPayloadProperties(String path) {
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(new File(path)));
            BufferedReader br = new BufferedReader(is);
            List<String> lines = new ArrayList<>();
            while (true) {
                String readLine = br.readLine();
                String line = readLine;
                if (readLine != null) {
                    Log.d(TAG, "getPayloadProperties line: " + line);
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
}
