package com.wits.pms.mcu.custom.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.KswMessage;
import java.io.FileWriter;
import java.io.IOException;

public class CanLogUtils {
    @SuppressLint({"SdCardPath"})
    private static final String CAN_LOG_PATH = "/sdcard/CANBus_Log.txt";
    private static FileWriter fileWriter;
    private static boolean pullingCanLog;

    public static void canLogSwitch(boolean open) {
        if (pullingCanLog && !open) {
            saveCanLog();
        } else if (!pullingCanLog && open) {
            startCatCanLog();
        }
        pullingCanLog = open;
    }

    public static boolean isCating() {
        return pullingCanLog;
    }

    private static void startCatCanLog() {
        KswMcuSender.getSender().sendMessage(112, new byte[]{15, 1});
        try {
            fileWriter = new FileWriter(CAN_LOG_PATH);
        } catch (IOException e) {
        }
    }

    private static void saveCanLog() {
        KswMcuSender.getSender().sendMessage(112, new byte[]{15, 0});
        try {
            fileWriter.flush();
            fileWriter.close();
            fileWriter = null;
        } catch (IOException e) {
        }
    }

    public static void canLog(KswMessage message) {
        if (message.getCmdType() == 161 && message.getData()[0] == 29) {
            byte[] realData = new byte[(message.getData().length - 1)];
            System.arraycopy(message.getData(), 1, realData, 0, realData.length);
            try {
                getCanLog(realData);
            } catch (Exception e) {
            }
        }
    }

    public static void getCanLog(byte[] data) throws Exception {
        StringBuilder line = new StringBuilder();
        StringBuilder head = new StringBuilder().append("0x");
        StringBuilder dataString = new StringBuilder();
        for (int i = data[5] == 0 ? 2 : 0; i < 4; i++) {
            String hex = Integer.toHexString(data[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            head.append(hex);
        }
        for (int i2 = 6; i2 < data[6] + 7; i2++) {
            String hex2 = Integer.toHexString(data[i2] & 255);
            if (hex2.length() == 1) {
                hex2 = '0' + hex2;
            }
            dataString.append("0x");
            dataString.append(hex2);
            dataString.append(" ");
        }
        line.append(head.toString());
        line.append(" ");
        line.append(data[4] == 0 ? "数据帧" : "远程帧");
        line.append(" ");
        line.append(data[5] == 0 ? "标准帧" : "扩展帧");
        line.append(" ");
        line.append(dataString.toString());
        fileWriter.write(line.toString());
        fileWriter.write("\n");
        Log.v("CanLog", line.toString());
    }
}
