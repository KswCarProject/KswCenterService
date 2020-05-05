package com.wits.pms.utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CopyFile {
    public static boolean copyTo(File target, File dir) {
        try {
            if (!target.exists()) {
                return true;
            }
            InputStream inStream = new FileInputStream(target.getPath());
            FileOutputStream fs = new FileOutputStream(dir);
            FileDescriptor fd = fs.getFD();
            byte[] buffer = new byte[1024];
            while (true) {
                int read = inStream.read(buffer);
                int byteread = read;
                if (read != -1) {
                    fs.write(buffer, 0, byteread);
                } else {
                    inStream.close();
                    fs.flush();
                    fd.sync();
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}
