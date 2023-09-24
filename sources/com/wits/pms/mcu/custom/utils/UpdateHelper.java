package com.wits.pms.mcu.custom.utils;

import android.p007os.Handler;
import android.p007os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import com.android.internal.p016os.BatteryStatsHistory;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.KswMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class UpdateHelper {
    private static final String TAG = "McuUpdate";
    private static final int UPDATE_DATA_TYPE = 160;
    private static UpdateHelper helper = null;
    private static final String newTypeFileDataHead = "FFFFFFFFAAF055A50AA";
    private boolean iapUpdateReady;
    private boolean isNewTypeFile;
    private boolean isUpdating;
    private int mNewFileSum;
    private byte[] mcuFile;
    private KswMcuSender mcuSender;
    private boolean stopUpdate;
    private McuUpdateListener updateListener;
    private Handler msgHandler = new Handler(new Handler.Callback() { // from class: com.wits.pms.mcu.custom.utils.UpdateHelper.1
        @Override // android.p007os.Handler.Callback
        public boolean handleMessage(Message msg) {
            int number = msg.what;
            Log.m68i(UpdateHelper.TAG, "-----0xE1-dataNumber" + number);
            byte[] bytes = new byte[130];
            bytes[0] = (byte) (msg.what >> 8);
            bytes[1] = (byte) msg.what;
            if ((number + 1) * 128 <= UpdateHelper.this.mcuFile.length) {
                System.arraycopy(UpdateHelper.this.mcuFile, number * 128, bytes, 2, 128);
            } else {
                System.arraycopy(UpdateHelper.this.mcuFile, number * 128, bytes, 2, UpdateHelper.this.mcuFile.length - (number * 128));
            }
            UpdateHelper.this.send(242, 160, 234, bytes);
            Handler handler = UpdateHelper.this.msgHandler;
            int i = msg.what + 1;
            msg.what = i;
            handler.sendEmptyMessageDelayed(i, 100L);
            return false;
        }
    });
    private File mMcuUpdatePatch = new File("/sdcard/ksw_mcu.bin");
    private boolean forceSend = false;

    /* loaded from: classes2.dex */
    public interface McuUpdateListener {
        void failed(int i);

        void progress(float f);

        void success();
    }

    public static UpdateHelper init(KswMcuSender sender) {
        helper = new UpdateHelper();
        helper.mcuSender = sender;
        return helper;
    }

    public static UpdateHelper getInstance() {
        return helper;
    }

    public void handleMessage(KswMessage message) {
        if (message == null) {
            return;
        }
        handleUpdateMessage(message);
    }

    public void setListener(McuUpdateListener listener) {
        this.updateListener = listener;
    }

    public void sendUpdateMessage(File file) {
        if (!file.exists() || !file.getName().endsWith(BatteryStatsHistory.FILE_SUFFIX)) {
            return;
        }
        this.mMcuUpdatePatch = file;
        if (sendUpdateRequestMsg()) {
        }
    }

    public static String byteToArray(byte[] data) {
        StringBuilder stringBuffer = new StringBuilder();
        for (byte b : data) {
            stringBuffer.append(Integer.toHexString(b & 255).toUpperCase());
        }
        return stringBuffer.toString();
    }

    private boolean sendUpdateRequestMsg() {
        boolean result;
        try {
            Log.m68i(TAG, "sendUpdateRequestMsg");
            FileInputStream fis = new FileInputStream(this.mMcuUpdatePatch);
            long length = this.mMcuUpdatePatch.length();
            int calc_sum = 0;
            byte[] buf = new byte[(int) (length - 24)];
            int len = fis.read(buf, 0, buf.length);
            byte[] b = new byte[24];
            fis.read(b);
            Log.m64w(TAG, "sendUpdateRequestMsg: length " + length);
            Log.m64w(TAG, "sendUpdateRequestMsg: string buf.length " + buf.length);
            String s = byteToArray(buf);
            Log.m64w(TAG, "sendUpdateRequestMsg: string buf :" + s);
            Log.m64w(TAG, "sendUpdateRequestMsg: string buf length : " + s.length());
            Log.m64w(TAG, "sendUpdateRequestMsg: string b " + byteToArray(b));
            byte[] checkCode = new byte[10];
            System.arraycopy(b, 14, checkCode, 0, 10);
            byte[] newTypeData = new byte[14];
            System.arraycopy(b, 0, newTypeData, 0, 14);
            if ((b[0] & 255) != 255 || (b[1] & 255) != 255 || (b[2] & 255) != 255 || (b[3] & 255) != 255 || (b[4] & 255) != 170 || (b[5] & 255) != 240 || (b[6] & 255) != 85 || (b[7] & 255) != 165 || (b[8] & 255) != 0 || (b[9] & 255) != 170) {
                this.isNewTypeFile = false;
                result = true;
            } else {
                this.isNewTypeFile = true;
                Log.m68i(TAG, "check new Type Mcu File");
                int file_sum = ((b[10] & 255) << 24) + ((b[11] & 255) << 16) + ((b[12] & 255) << 8) + (b[13] & 255);
                Log.m68i(TAG, "get file_sum = " + Integer.toHexString(file_sum));
                if (len != -1) {
                    for (int i = 0; i < len; i++) {
                        calc_sum += buf[i] & 255;
                    }
                    Log.m68i(TAG, "calc sum = " + Integer.toHexString(calc_sum));
                } else {
                    Log.m70e(TAG, " -- file error ");
                }
                if (calc_sum != file_sum) {
                    result = false;
                    callUpdateFailed(242);
                } else {
                    result = true;
                }
            }
            if (result) {
                send(242, 160, 232, checkCode);
            }
            fis.close();
            return result;
        } catch (IOException e) {
            Log.m69e(TAG, "sendUpdateRequestMsg error ", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendUpdateFileCheck(boolean onlyCheck) {
        Log.m68i(TAG, "sendUpdateFileCheck");
        try {
            long length = this.mMcuUpdatePatch.length();
            byte[] datas = new byte[8];
            datas[0] = (byte) (length >> 24);
            datas[1] = (byte) (length >> 16);
            datas[2] = (byte) (length >> 8);
            datas[3] = (byte) length;
            FileInputStream fis = new FileInputStream(this.mMcuUpdatePatch);
            byte[] fileBuf = new byte[(int) length];
            int sum = 0;
            int len = fis.read(fileBuf, 0, fileBuf.length);
            if (len != -1) {
                int sum2 = 0;
                for (int sum3 = 0; sum3 < len; sum3++) {
                    sum2 += fileBuf[sum3] & 255;
                }
                Log.m68i(TAG, "full file sum = " + Integer.toHexString(sum2));
                sum = sum2;
            }
            datas[4] = (byte) (sum >> 24);
            datas[5] = (byte) (sum >> 16);
            datas[6] = (byte) (sum >> 8);
            datas[7] = (byte) sum;
            if (onlyCheck) {
                send(242, 160, 233, datas);
            }
            fis.close();
            return true;
        } catch (IOException e) {
            Log.m69e(TAG, "sendUpdateFileCheck error ", e);
            return false;
        }
    }

    private void callUpdateFailed(int error) {
        Log.m70e(TAG, "callUpdateFailed error=" + error);
        this.stopUpdate = true;
        if (this.updateListener != null) {
            this.updateListener.failed(error);
        }
        clear();
    }

    private void clear() {
        this.isUpdating = false;
        this.stopUpdate = true;
        this.isNewTypeFile = false;
        this.mNewFileSum = 0;
        this.mcuFile = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMcu() {
        Log.m72d(TAG, "updateMcu ");
        try {
            FileInputStream fis = new FileInputStream(this.mMcuUpdatePatch);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int count = 0;
            while (true) {
                int len = fis.read(buf);
                if (len == -1) {
                    break;
                }
                if (count == 0) {
                    byte[] bytes = new byte[130];
                    bytes[0] = 0;
                    bytes[1] = 0;
                    System.arraycopy(buf, 0, bytes, 2, buf.length);
                    send(242, 160, 234, bytes);
                }
                baos.write(buf, 0, len);
                count++;
            }
            baos.flush();
            this.mcuFile = baos.toByteArray();
            if (this.forceSend) {
                this.msgHandler.sendEmptyMessage(1);
            }
            baos.close();
            fis.close();
        } catch (IOException e) {
            callUpdateFailed(0);
            Log.m69e(TAG, "updateMcu ", e);
        }
    }

    private void updateSuccess() {
        Log.m72d(TAG, "updateSuccess ");
        this.isUpdating = false;
        this.iapUpdateReady = false;
        this.mMcuUpdatePatch = null;
        if (this.updateListener != null) {
            Log.m72d(TAG, "updateListener  success " + this.updateListener);
            this.updateListener.success();
        }
    }

    /* JADX WARN: Type inference failed for: r0v25, types: [com.wits.pms.mcu.custom.utils.UpdateHelper$3] */
    /* JADX WARN: Type inference failed for: r0v26, types: [com.wits.pms.mcu.custom.utils.UpdateHelper$2] */
    private void handleUpdateMessage(KswMessage message) {
        if (message.getCmdType() == 224) {
            if (message.getData()[0] == 1) {
                this.isUpdating = true;
                new Thread() { // from class: com.wits.pms.mcu.custom.utils.UpdateHelper.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                        }
                        UpdateHelper.this.updateMcu();
                    }
                }.start();
            } else if (message.getData()[0] == 2) {
                updateSuccess();
            } else if (message.getData()[0] == 3) {
                this.iapUpdateReady = true;
                new Thread() { // from class: com.wits.pms.mcu.custom.utils.UpdateHelper.3
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        UpdateHelper.this.sendUpdateFileCheck(true);
                    }
                }.start();
            } else if (message.getData()[0] != 4 && (message.getData()[0] & 255) >= 241) {
                callUpdateFailed(message.getData()[0]);
            }
        } else if (message.getCmdType() == 225) {
            if (this.forceSend) {
                return;
            }
            int high = (message.getData()[0] & 255) << 8;
            int low = message.getData()[1] & 255;
            int number = high + low;
            Log.m68i(TAG, "-----0xE1-dataNumber" + number);
            byte[] bytes = new byte[130];
            bytes[0] = message.getData()[0];
            bytes[0] = message.getData()[1];
            if ((number + 1) * 128 <= this.mcuFile.length) {
                if (this.updateListener != null) {
                    this.updateListener.progress(((number * 128.0f) / this.mcuFile.length) * 100.0f);
                }
                System.arraycopy(this.mcuFile, number * 128, bytes, 2, 128);
            } else {
                if (this.updateListener != null) {
                    this.updateListener.progress(100.0f);
                }
                System.arraycopy(this.mcuFile, number * 128, bytes, 2, this.mcuFile.length - (number * 128));
            }
            send(242, 160, 234, bytes);
        } else if (message.getCmdType() == 227 && message.getData()[0] != 1 && this.updateListener != null) {
            this.updateListener.failed(-1);
        }
    }

    private void printHex(String method, KswMessage msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(method);
        int i = 0;
        sb.append(String.format("-----0x" + "%x  [".toUpperCase(), Integer.valueOf(msg.getCmdType())));
        while (true) {
            int i2 = i;
            if (i2 < msg.getData().length) {
                String hex = Integer.toHexString(msg.getData()[i2] & 255);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append("0x");
                sb.append(hex.toUpperCase());
                sb.append(SmsManager.REGEX_PREFIX_DELIMITER);
                i = i2 + 1;
            } else {
                int i3 = sb.length();
                sb.replace(i3 - 1, sb.length(), "");
                sb.append("]\n");
                Log.m66v(TAG, sb.toString());
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(int frameHead, int dataType, int cmdType, byte[] datas) {
        this.mcuSender.sendUpdateMessage(KswMessage.obtain(cmdType, datas, true));
    }
}
