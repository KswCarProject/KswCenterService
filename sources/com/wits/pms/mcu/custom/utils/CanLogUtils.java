package com.wits.pms.mcu.custom.utils;

import android.annotation.SuppressLint;
import android.net.wifi.WifiEnterpriseConfig;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.KswMessage;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes2.dex */
public class CanLogUtils {
    @SuppressLint({"SdCardPath"})
    private static final String CAN_LOG_PATH = "/sdcard/CANBus_Log";
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
        int type = Settings.System.getInt(PowerManagerAppService.serviceContext.getContentResolver(), "canBusType", 0);
        KswMcuSender.getSender().sendMessage(112, new byte[]{MidiConstants.STATUS_CHANNEL_MASK, 1, (byte) type});
        String path = "/sdcard/CANBus_Log_CAN" + type + ".txt";
        try {
            fileWriter = new FileWriter(path);
        } catch (IOException e) {
        }
    }

    private static void saveCanLog() {
        KswMcuSender.getSender().sendMessage(112, new byte[]{MidiConstants.STATUS_CHANNEL_MASK, 0});
        try {
            fileWriter.flush();
            fileWriter.close();
            fileWriter = null;
        } catch (IOException e) {
        }
    }

    public static void canLog(KswMessage message) {
        if (message.getCmdType() == 161 && message.getData()[0] == 29) {
            byte[] realData = new byte[message.getData().length - 1];
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
            dataString.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        }
        line.append(head.toString());
        line.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        line.append(data[4] == 0 ? "\u6570\u636e\u5e27" : "\u8fdc\u7a0b\u5e27");
        line.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        line.append(data[5] == 0 ? "\u6807\u51c6\u5e27" : "\u6269\u5c55\u5e27");
        line.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        line.append(dataString.toString());
        fileWriter.write(line.toString());
        fileWriter.write("\n");
        Log.m66v("CanLog", line.toString());
    }
}
