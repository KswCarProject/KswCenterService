package com.wits.pms.mcu;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class SerialPort {
    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    private static native FileDescriptor open(String str, int i, int i2);

    public native void close();

    public SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {
        Log.m72d("SerialPortService", "==" + device.canRead() + "==" + device.canWrite());
        this.mFd = open(device.getAbsolutePath(), baudrate, flags);
        if (this.mFd == null) {
            Log.m70e(TAG, "native open returns null");
            throw new IOException();
        }
        this.mFileInputStream = new FileInputStream(this.mFd);
        this.mFileOutputStream = new FileOutputStream(this.mFd);
    }

    public InputStream getInputStream() {
        return this.mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return this.mFileOutputStream;
    }

    static {
        System.loadLibrary("serial_port");
    }
}
