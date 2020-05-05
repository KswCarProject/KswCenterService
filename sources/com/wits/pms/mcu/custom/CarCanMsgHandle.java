package com.wits.pms.mcu.custom;

import android.app.Instrumentation;
import android.content.Context;
import android.provider.Settings;
import com.wits.pms.consts.KeycodeCustom;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.utils.KeyUtils;
import java.util.HashMap;

public class CarCanMsgHandle {
    public final HashMap<Integer, Integer> keyEventMap = new HashMap<Integer, Integer>() {
        {
            put(1, 19);
            put(2, Integer.valueOf(KeycodeCustom.KEYCODE_DOWN));
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
            put(14, Integer.valueOf(KeycodeCustom.KEYCODE_NAVI));
            put(16, Integer.valueOf(KeycodeCustom.KEYCODE_MODE));
            put(17, 5);
            put(18, (Object) null);
            put(20, Integer.valueOf(KeycodeCustom.KEYCODE_VOICE_ASSIST));
            put(21, (Object) null);
            put(22, (Object) null);
            put(23, 88);
            put(24, 87);
            put(25, 85);
            put(26, 91);
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
            } else if (opType > 7 || opType < 1 || (!SystemStatusControl.getStatus().topApp.contains(AutoKitCallBackImpl.AutoKitPkgName) && !SystemStatusControl.getStatus().topApp.contains("com.autonavi.amapauto"))) {
                if (opType == 17) {
                    try {
                        if (KswSettings.getSettings().getSettingsInt("BT_Type") == 1) {
                            CenterControlImpl.getImpl().openCarBt();
                            return;
                        }
                    } catch (Settings.SettingNotFoundException e) {
                    }
                }
                if (this.keyEventMap.get(Integer.valueOf(opType)) != null) {
                    KeyUtils.pressKey(this.keyEventMap.get(Integer.valueOf(opType)).intValue());
                }
            }
        }
    }
}
