package com.wits.pms.mcu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import com.wits.pms.custom.McuUpdateService;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.utils.ServiceManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class McuService extends Service implements McuSender {
    private static final boolean Debug = false;
    private static final String TAG = "McuService";
    private static OnReceiveData listener;
    public static Context mContext;
    private static ReadThread mReadThread;
    private static String version = Build.DISPLAY;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public InputStream mInputStream;
    protected OutputStream mOutputStream;
    protected SerialPort mSerialPort;

    public interface OnReceiveData {
        void onReceiveMcu(byte[] bArr);

        void reset();
    }

    /* access modifiers changed from: protected */
    public void onDataReceived(byte[] buffer) {
        if (listener != null) {
            listener.onReceiveMcu(buffer);
        }
    }

    public void onCreate() {
        try {
            String serial = version.contains("8937") ? "/dev/ttyHSL1" : "/dev/ttyMSM1";
            Log.d(TAG, "open port   serial = " + serial + "  version = " + version);
            this.mHandler = new Handler(getMainLooper());
            this.mSerialPort = new SerialPort(new File(serial), 115200, 0);
            Log.d(TAG, "open port\t" + this.mSerialPort + "   serial = " + serial);
            this.mOutputStream = this.mSerialPort.getOutputStream();
            this.mInputStream = this.mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServiceManager.addService("mcu_update", new McuUpdateService());
        KswMcuSender.init(this);
    }

    public static void setListener(OnReceiveData listener2) {
        if (listener2 != null) {
            listener = listener2;
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = getApplicationContext();
        return super.onStartCommand(intent, flags, startId);
    }

    private class ReadThread extends Thread {
        public final Object portLock;

        private ReadThread() {
            this.portLock = new Object();
        }

        public void write(final byte[] buf) {
            new Thread() {
                public void run() {
                    if (McuService.this.mOutputStream != null) {
                        try {
                            McuService.this.mOutputStream.write(buf);
                        } catch (IOException e) {
                        }
                    }
                }
            }.start();
        }

        public void run() {
            while (McuService.this.mInputStream != null) {
                try {
                    byte[] buffer = new byte[128];
                    int size = McuService.this.mInputStream.read(buffer);
                    byte[] data = new byte[size];
                    if (size > 0) {
                        System.arraycopy(buffer, 0, data, 0, size);
                        synchronized (this.portLock) {
                            McuService.this.onDataReceived(data);
                        }
                    }
                } catch (IOException e) {
                    return;
                }
            }
        }
    }

    public void send(McuMessage msg) {
        if (mReadThread != null && msg != null) {
            mReadThread.write(msg.outData);
        }
    }

    public void send(byte[] pack) {
    }

    public static void printHex(String method, byte[] data) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("-----[");
        for (byte b : data) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append("0x");
            sb.append(hex.toUpperCase());
            sb.append(SmsManager.REGEX_PREFIX_DELIMITER);
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append("]\n");
        Log.v(TAG, sb.toString());
    }

    public static void printHex(String method, McuMessage msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("-----[");
        for (byte b : msg.getData()) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append("0x");
            sb.append(hex.toUpperCase());
            sb.append(SmsManager.REGEX_PREFIX_DELIMITER);
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append("]\n");
        Log.v(TAG, sb.toString());
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "close port");
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
        if (this.mSerialPort != null) {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }
        this.mSerialPort = null;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
