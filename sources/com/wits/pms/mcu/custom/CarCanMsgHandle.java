package com.wits.pms.mcu.custom;

import android.app.Instrumentation;
import android.content.Context;
import android.provider.Settings;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.KeyUtils;
import com.wits.pms.utils.SystemProperties;
import java.util.HashMap;

public class CarCanMsgHandle {
    public final HashMap<Integer, Integer> keyEventMap = new HashMap<Integer, Integer>() {
        {
            put(1, 691);
            put(2, 692);
            put(3, 21);
            put(4, 22);
            put(5, 66);
            put(7, 19);
            put(6, 20);
            put(8, 3);
            put(9, (Object) null);
            put(10, (Object) null);
            put(11, (Object) null);
            put(12, 4);
            put(13, (Object) null);
            put(14, 303);
            put(16, 301);
            put(17, 5);
            put(18, (Object) null);
            put(20, 302);
            put(21, (Object) null);
            put(22, (Object) null);
            put(23, 88);
            put(24, 87);
            put(25, 85);
            put(26, 91);
            put(32, (Object) null);
            put(33, (Object) null);
        }
    };
    private Instrumentation mInstrumentation = new Instrumentation();

    public static final class CanMsg {
        public static final int CMD_BRIGHTNESS = 17;
        public static final int CMD_IDrivce = 23;
        public static final int CMD_VIEW_STATUS = 26;

        public static final class IDrive {
            public static final int BACK = 12;
            public static final int CD = 9;
            public static final int CONFIRM = 5;
            public static final int DOWN = 2;
            public static final int EJECT = 18;
            public static final int HICAR_APP = 32;
            public static final int HICAR_VOICE = 33;
            public static final int LEFT = 3;
            public static final int MAP = 14;
            public static final int MENU = 8;
            public static final int MODE = 16;
            public static final int MUTE = 26;
            public static final int NEXT = 24;
            public static final int OPTION = 13;
            public static final int OP_TEL = 17;
            public static final int PLAYPAUSE = 25;
            public static final int PREV = 23;
            public static final int RADIO = 10;
            public static final int RIGHT = 4;
            public static final int TEL = 11;
            public static final int TURN_LEFT = 7;
            public static final int TURN_RIGHT = 6;
            public static final int UP = 1;
            public static final int VOICE = 20;
            public static final int VOL_ADD = 21;
            public static final int VOL_SUB = 22;
        }
    }

    public CarCanMsgHandle(Context mContext) {
    }

    public void handleCanMsg(byte[] datas) {
        byte opType;
        byte subCmd = datas[0];
        if (subCmd != 17 && subCmd == 23 && datas[2] == 1 && (opType = datas[1]) != 11) {
            if (opType == 14) {
                CenterControlImpl.getImpl().openNavi();
                return;
            }
            if (opType == 21 || opType == 22) {
                try {
                    int currentVol = KswSettings.getSettings().getSettingsInt("Android_media_vol");
                    if (KswSettings.getSettings().getSettingsInt("AMP_Type", 0) == 1 && currentVol >= 0 && currentVol <= 40) {
                        if (currentVol < 40 && opType == 21 && currentVol >= 0) {
                            currentVol++;
                        }
                        if (currentVol > 0 && opType == 22 && currentVol <= 40) {
                            currentVol--;
                        }
                        KswSettings.getSettings().setInt("Android_media_vol", currentVol);
                        return;
                    }
                    return;
                } catch (Exception e) {
                }
            }
            if (opType <= 7 && opType >= 1 && (SystemStatusControl.getStatus().topApp.contains(AutoKitCallBackImpl.AutoKitPkgName) || SystemStatusControl.getStatus().topApp.contains("com.autonavi.amapauto"))) {
                return;
            }
            if (opType == 32) {
                WitsCommand.sendCommand(7, 101, "");
            } else if (opType == 33) {
                WitsCommand.sendCommand(7, 102, "");
            } else if (opType != 17 || !"true".equals(SystemProperties.get("persist.sys.hicar_connect"))) {
                if (opType == 17) {
                    try {
                        if (KswSettings.getSettings().getSettingsInt("BT_Type") == 1) {
                            CenterControlImpl.getImpl().openCarBt();
                            return;
                        }
                    } catch (Settings.SettingNotFoundException e2) {
                    }
                }
                if (this.keyEventMap.get(Integer.valueOf(opType)) != null) {
                    KeyUtils.pressKey(this.keyEventMap.get(Integer.valueOf(opType)).intValue());
                }
            } else {
                WitsCommand.sendCommand(7, 113, "");
            }
        }
    }
}
