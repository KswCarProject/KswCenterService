package com.wits.pms.mcu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import com.wits.pms.custom.McuUpdateService;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mirror.ServiceManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class McuService extends Service implements McuSender {
    private static final boolean Debug = false;
    private static final String TAG = "McuService";
    private static OnReceiveData listener;
    public static Context mContext;
    private static ReadThread mReadThread;
    private static String version = Build.DISPLAY;
    private Handler mHandler;
    private InputStream mInputStream;
    protected OutputStream mOutputStream;
    protected SerialPort mSerialPort;

    /* loaded from: classes2.dex */
    public interface OnReceiveData {
        void onReceiveMcu(byte[] bArr);

        void reset();
    }

    protected void onDataReceived(byte[] buffer) {
        if (listener != null) {
            listener.onReceiveMcu(buffer);
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        String serial;
        try {
            if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && Build.DISPLAY.contains("M600")) {
                serial = "/dev/ttyHS1";
            } else if (version.contains("8937")) {
                serial = "/dev/ttyHSL1";
            } else {
                serial = "/dev/ttyMSM1";
            }
            Log.m72d(TAG, "open port   serial = " + serial + "  version = " + version);
            this.mHandler = new Handler(getMainLooper());
            this.mSerialPort = new SerialPort(new File(serial), 115200, 0);
            Log.m72d(TAG, "open port\t" + this.mSerialPort + "   serial = " + serial);
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

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = getApplicationContext();
        return super.onStartCommand(intent, flags, startId);
    }

    /* loaded from: classes2.dex */
    private class ReadThread extends Thread {
        public final Object portLock;

        private ReadThread() {
            this.portLock = new Object();
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.mcu.McuService$ReadThread$1] */
        public void write(final byte[] buf) {
            new Thread() { // from class: com.wits.pms.mcu.McuService.ReadThread.1
                @Override // java.lang.Thread, java.lang.Runnable
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

        @Override // java.lang.Thread, java.lang.Runnable
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

    @Override // com.wits.pms.mcu.McuSender
    public void send(McuMessage msg) {
        if (mReadThread != null) {
            if (msg == null) {
                Log.m72d(TAG, "msg is null");
                return;
            } else {
                mReadThread.write(msg.outData);
                return;
            }
        }
        Log.m72d(TAG, "mReadThread is null");
    }

    @Override // com.wits.pms.mcu.McuSender
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
        int i = sb.length();
        sb.replace(i - 1, sb.length(), "");
        sb.append("]\n");
        Log.m66v(TAG, sb.toString());
    }

    public static void printHex(String method, McuMessage msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("-----[");
        for (int i = 0; i < msg.getData().length; i++) {
            String hex = Integer.toHexString(msg.getData()[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append("0x");
            sb.append(hex.toUpperCase());
            sb.append(SmsManager.REGEX_PREFIX_DELIMITER);
        }
        int i2 = sb.length();
        sb.replace(i2 - 1, sb.length(), "");
        sb.append("]\n");
        Log.m66v(TAG, sb.toString());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.m72d(TAG, "close port");
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
        if (this.mSerialPort != null) {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }
        this.mSerialPort = null;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
