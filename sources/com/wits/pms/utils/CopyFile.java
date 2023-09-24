package com.wits.pms.utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class CopyFile {
    public static boolean copyTo(File target, File dir) {
        try {
            if (target.exists()) {
                InputStream inStream = new FileInputStream(target.getPath());
                FileOutputStream fs = new FileOutputStream(dir);
                FileDescriptor fd = fs.getFD();
                byte[] buffer = new byte[1024];
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread != -1) {
                        fs.write(buffer, 0, byteread);
                    } else {
                        inStream.close();
                        fs.flush();
                        fd.sync();
                        fs.close();
                        return true;
                    }
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
