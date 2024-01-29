package com.wits.pms.mcu.custom;

import android.app.Instrumentation;
import android.content.Context;
import android.p007os.Handler;
import android.p007os.Message;
import android.provider.Settings;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.KeyUtils;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class CarCanMsgHandle {
    private static final boolean DBG = false;
    public static final int MSG_HANDLER_KEY = 1;
    private static final String TAG = "CarCanMsgHandle";
    private Context context;
    public final HashMap<Integer, Integer> keyEventMap = new HashMap<Integer, Integer>() { // from class: com.wits.pms.mcu.custom.CarCanMsgHandle.1
        {
            put(1, 691);
            put(2, 692);
            put(3, 21);
            put(4, 22);
            put(5, 66);
            put(7, 19);
            put(6, 20);
            put(8, 3);
            put(9, null);
            put(10, null);
            put(11, null);
            put(12, 4);
            put(13, null);
            put(14, 303);
            put(16, 301);
            put(17, 5);
            put(18, null);
            put(20, 302);
            put(21, null);
            put(22, null);
            put(23, 88);
            put(24, 87);
            put(25, 85);
            put(26, 91);
            put(32, null);
            put(33, null);
        }
    };
    private Instrumentation mInstrumentation = new Instrumentation();
    private Mhandler mhandler = new Mhandler(this);

    /* loaded from: classes2.dex */
    public static final class CanMsg {
        public static final int CMD_BRIGHTNESS = 17;
        public static final int CMD_IDrivce = 23;
        public static final int CMD_VIEW_STATUS = 26;

        /* loaded from: classes2.dex */
        public static final class IDrive {
            public static final int BACK = 12;

            /* renamed from: CD */
            public static final int f2570CD = 9;
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

            /* renamed from: UP */
            public static final int f2571UP = 1;
            public static final int VOICE = 20;
            public static final int VOL_ADD = 21;
            public static final int VOL_SUB = 22;
        }
    }

    public CarCanMsgHandle(Context mContext) {
        this.context = mContext;
    }

    public void handleCanMsg(byte[] datas) {
        int opType;
        int currentVol;
        int subCmd = datas[0];
        boolean fIDriveSndBroadcastMsg = false;
        if (subCmd != 17) {
            if (subCmd == 23) {
                if (SystemStatusControl.getStatus().topApp.contains(ZlinkMessage.ZLINK_NORMAL_ACTION)) {
                    if (datas[2] == 0) {
                        fIDriveSndBroadcastMsg = true;
                        byte b = datas[1];
                        if (b == 12) {
                            CenterControlImpl.getImpl().iDriveZlinkMsg(4);
                        } else if (b != 14) {
                            if (b != 17) {
                                switch (b) {
                                    case 1:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(19);
                                        break;
                                    case 2:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(20);
                                        break;
                                    case 3:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(21);
                                        break;
                                    case 4:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(22);
                                        break;
                                    case 5:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(CenterControlImpl.getImpl().getHicarStatus() ? 23 : 66);
                                        break;
                                    case 6:
                                    case 7:
                                        break;
                                    default:
                                        switch (b) {
                                            case 23:
                                                CenterControlImpl.getImpl().iDriveZlinkMsg(88);
                                                break;
                                            case 24:
                                                CenterControlImpl.getImpl().iDriveZlinkMsg(87);
                                                break;
                                            case 25:
                                                CenterControlImpl.getImpl().iDriveZlinkMsg(85);
                                                break;
                                            default:
                                                switch (b) {
                                                    case 30:
                                                        CenterControlImpl.getImpl().iDriveZlinkMsg(5);
                                                        break;
                                                    case 31:
                                                        CenterControlImpl.getImpl().iDriveZlinkMsg(6);
                                                        break;
                                                    default:
                                                        fIDriveSndBroadcastMsg = false;
                                                        break;
                                                }
                                        }
                                }
                            } else {
                                switch (CenterControlImpl.getImpl().getCpCallStatus()) {
                                    case 1:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(5);
                                        break;
                                    case 2:
                                        CenterControlImpl.getImpl().iDriveZlinkMsg(6);
                                        break;
                                }
                            }
                        } else {
                            CenterControlImpl.getImpl().iDriveZlinkMsg(1504);
                        }
                    } else if (datas[2] == 1) {
                        fIDriveSndBroadcastMsg = true;
                        byte b2 = datas[1];
                        if (b2 != 12 && b2 != 14 && b2 != 17) {
                            switch (b2) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                    break;
                                case 6:
                                    CenterControlImpl.getImpl().iDriveZlinkMsg(CenterControlImpl.getImpl().getHicarStatus() ? 22 : 1502);
                                    break;
                                case 7:
                                    CenterControlImpl.getImpl().iDriveZlinkMsg(CenterControlImpl.getImpl().getHicarStatus() ? 21 : 1501);
                                    break;
                                default:
                                    switch (b2) {
                                        case 23:
                                        case 24:
                                        case 25:
                                            break;
                                        default:
                                            switch (b2) {
                                                case 30:
                                                case 31:
                                                    break;
                                                default:
                                                    fIDriveSndBroadcastMsg = false;
                                                    break;
                                            }
                                    }
                            }
                        }
                    }
                }
                if (!fIDriveSndBroadcastMsg && datas[2] == 1 && (opType = datas[1]) != 11) {
                    if (opType == 14) {
                        CenterControlImpl.getImpl().openNavi();
                        return;
                    }
                    if (opType == 21 || opType == 22) {
                        try {
                            if (CenterControlImpl.getImpl().isBTCallingorTalking()) {
                                currentVol = KswSettings.getSettings().getSettingsInt("Android_phone_vol");
                            } else {
                                currentVol = KswSettings.getSettings().getSettingsInt("Android_media_vol");
                            }
                            if (KswSettings.getSettings().getSettingsInt("AMP_Type", 0) == 1 && currentVol >= 0 && currentVol <= 40) {
                                if (currentVol < 40 && opType == 21 && currentVol >= 0) {
                                    currentVol++;
                                }
                                if (currentVol > 0 && opType == 22 && currentVol <= 40) {
                                    currentVol--;
                                }
                                if (CenterControlImpl.getImpl().isBTCallingorTalking()) {
                                    KswSettings.getSettings().setInt("Android_phone_vol", currentVol);
                                    return;
                                } else {
                                    KswSettings.getSettings().setInt("Android_media_vol", currentVol);
                                    return;
                                }
                            }
                            return;
                        } catch (Exception e) {
                        }
                    }
                    int mPageIndex = Settings.System.getInt(this.context.getContentResolver(), "mPageIndex", 0);
                    if (opType <= 7 && opType >= 1) {
                        if (!SystemStatusControl.getStatus().topApp.contains(AutoKitCallBackImpl.AutoKitPkgName)) {
                            if (mPageIndex != 1 && SystemStatusControl.getStatus().topApp.contains("com.autonavi.amapauto")) {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    if (opType == 32) {
                        WitsCommand.sendCommand(7, 101, "");
                    } else if (opType == 33) {
                        WitsCommand.sendCommand(7, 102, "");
                    } else if (opType == 17 && "true".equals(SystemProperties.get("persist.sys.hicar_connect"))) {
                        WitsCommand.sendCommand(7, 113, "");
                    } else {
                        if (opType == 17) {
                            try {
                                if (KswSettings.getSettings().getSettingsInt("BT_Type") == 1) {
                                    CenterControlImpl.getImpl().openCarBt();
                                    return;
                                }
                            } catch (Settings.SettingNotFoundException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (this.mhandler != null) {
                            boolean hasMsg = this.mhandler.hasMessages(1);
                            if (hasMsg) {
                                return;
                            }
                            Message message = this.mhandler.obtainMessage();
                            message.what = 1;
                            message.arg1 = opType;
                            this.mhandler.sendMessageDelayed(message, 150L);
                        }
                    }
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class Mhandler extends Handler {
        private final WeakReference<CarCanMsgHandle> weakReference;

        public Mhandler(CarCanMsgHandle carCanMsgHandle) {
            this.weakReference = new WeakReference<>(carCanMsgHandle);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            CarCanMsgHandle handle = this.weakReference.get();
            if (handle != null) {
                handle.handlerKeyMsg(msg);
            }
        }
    }

    public void handlerKeyMsg(Message msg) {
        if (msg.what == 1) {
            int opType = msg.arg1;
            if (this.keyEventMap.get(Integer.valueOf(opType)) != null) {
                KeyUtils.pressKey(this.keyEventMap.get(Integer.valueOf(opType)).intValue());
            }
        }
    }
}
