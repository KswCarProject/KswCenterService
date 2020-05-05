package com.wits.pms.mcu.custom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.wits.pms.BuildConfig;
import com.wits.pms.ClockActivity;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.mcu.custom.utils.AccLight;
import com.wits.pms.mcu.custom.utils.CanLogUtils;
import com.wits.pms.mcu.custom.utils.ForceMcuUpdate;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.utils.SystemProperties;
import com.wits.pms.utils.TimeSetting;

public class KswMcuListener extends KswMcuReceiver {
    public static final String TAG = "KswMcuListener";
    /* access modifiers changed from: private */
    public final Context mContext = PowerManagerAppService.serviceContext;
    private final Handler mHandler = new Handler(this.mContext.getMainLooper());
    private final KswMcuSender mMcuSender = CenterControlImpl.getMcuSender();
    private String memoryPackage;
    private boolean openBluetooth;

    public static final class MediaType {
        public static final int SRC_ALL_APP = 13;
        public static final int SRC_AUX = 6;
        public static final int SRC_BT = 3;
        public static final int SRC_BT_MUSIC = 4;
        public static final int SRC_CAR = 0;
        public static final int SRC_DTV = 9;
        public static final int SRC_DVD = 8;
        public static final int SRC_DVD_YUV = 12;
        public static final int SRC_DVR = 5;
        public static final int SRC_F_CAM = 11;
        public static final int SRC_MUSIC = 1;
        public static final int SRC_PHONELINK = 7;
        public static final int SRC_RADIO = 10;
        public static final int SRC_VIDEO = 2;
    }

    public KswMcuListener() {
        UpdateHelper.init(this.mMcuSender);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("can_bus_switch"), true, new ContentObserver(this.mHandler) {
            public void onChange(boolean selfChange) {
                boolean z = true;
                if (Settings.System.getInt(KswMcuListener.this.mContext.getContentResolver(), "can_bus_switch", 0) != 1) {
                    z = false;
                }
                CanLogUtils.canLogSwitch(z);
            }
        });
        Settings.System.putInt(this.mContext.getContentResolver(), "can_bus_switch", 0);
    }

    public void onMcuMessage(byte[] pack) {
        onMcuMessage(KswMessage.parse(pack));
    }

    public void onMcuMessage(KswMessage message) {
        KswMessage kswMessage = message;
        Log.i(TAG, kswMessage + BuildConfig.FLAVOR);
        if (kswMessage != null) {
            KswMcuLogic.handleMsg(message);
            UpdateHelper.getInstance().handleMessage(kswMessage);
            if (CanLogUtils.isCating()) {
                CanLogUtils.canLog(message);
            }
            SystemStatus systemStatus = SystemStatusControl.getStatus();
            McuStatus mcuStatus = SystemStatusControl.getDefault().getMcuStatus();
            byte[] data = message.getData();
            int cmdType = message.getCmdType();
            boolean isAccOpen = false;
            if (cmdType == 21) {
                Log.i(TAG, "memory mode: " + data[1]);
                if (data[0] == 1) {
                    if (!(data[1] == 0 || data[1] == 6 || data[1] == 9 || data[1] == 5 || data[1] == 11)) {
                        isAccOpen = true;
                    }
                    this.mHandler.postDelayed(new KswMcuListener$$Lambda$1(this, systemStatus, data), isAccOpen ? 5000 : 0);
                }
            } else if (cmdType != 23) {
                if (cmdType == 161) {
                    byte b = data[0];
                    if (b == 16) {
                        systemStatus.epb = data[1] & 255 & 8;
                        mcuStatus.carData.handbrake = ((data[1] & 255) & 8) != 0;
                        McuStatus.CarData carData = mcuStatus.carData;
                        if ((data[2] & 255 & 1) != 0) {
                            isAccOpen = true;
                        }
                        carData.safetyBelt = isAccOpen;
                    } else if (b == 18) {
                        mcuStatus.carData.carDoor = data[1] & 255;
                    } else if (b != 28) {
                        switch (b) {
                            case 23:
                                if (data[2] == 1) {
                                    byte b2 = data[1];
                                    if (b2 != 13) {
                                        if (b2 == 20) {
                                            this.mContext.sendBroadcast(new Intent("start.txz.ksw"));
                                            break;
                                        } else {
                                            switch (b2) {
                                                case 3:
                                                    AutoKitCallBackImpl.getImpl(this.mContext).drapUp();
                                                    break;
                                                case 4:
                                                    AutoKitCallBackImpl.getImpl(this.mContext).drapDown();
                                                    break;
                                                case 5:
                                                    AutoKitCallBackImpl.getImpl(this.mContext).enter();
                                                    break;
                                                case 6:
                                                    AutoKitCallBackImpl.getImpl(this.mContext).drapRight();
                                                    if (SystemStatusControl.getStatus().topApp.contains("autonavi")) {
                                                        Intent intent1 = new Intent();
                                                        intent1.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
                                                        intent1.putExtra("KEY_TYPE", 10027);
                                                        intent1.putExtra("EXTRA_TYPE", 1);
                                                        intent1.putExtra("EXTRA_OPERA", 0);
                                                        this.mContext.sendBroadcast(intent1);
                                                        break;
                                                    } else {
                                                        return;
                                                    }
                                                case 7:
                                                    AutoKitCallBackImpl.getImpl(this.mContext).drapLeft();
                                                    if (SystemStatusControl.getStatus().topApp.contains("autonavi")) {
                                                        Intent intent2 = new Intent();
                                                        intent2.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
                                                        intent2.putExtra("KEY_TYPE", 10027);
                                                        intent2.putExtra("EXTRA_TYPE", 1);
                                                        intent2.putExtra("EXTRA_OPERA", 1);
                                                        this.mContext.sendBroadcast(intent2);
                                                        break;
                                                    } else {
                                                        return;
                                                    }
                                                default:
                                                    switch (b2) {
                                                        case 30:
                                                            if (!SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1")) {
                                                                if (!SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1")) {
                                                                    CenterControlImpl.getImpl().acceptPhone();
                                                                    break;
                                                                } else {
                                                                    AutoKitCallBackImpl.getImpl(this.mContext).acceptPhone();
                                                                    break;
                                                                }
                                                            } else {
                                                                CenterControlImpl.getImpl().zlinkHandleCall();
                                                                break;
                                                            }
                                                        case 31:
                                                            if (!SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1")) {
                                                                if (!SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1")) {
                                                                    CenterControlImpl.getImpl().handUpPhone();
                                                                    break;
                                                                } else {
                                                                    AutoKitCallBackImpl.getImpl(this.mContext).rejectPhone();
                                                                    break;
                                                                }
                                                            } else {
                                                                CenterControlImpl.getImpl().zlinkHandleCall();
                                                                break;
                                                            }
                                                    }
                                            }
                                        }
                                    } else {
                                        CenterControlImpl.getImpl().openSettings();
                                        break;
                                    }
                                } else {
                                    return;
                                }
                                break;
                            case 24:
                                byte[] timeArrays = new byte[7];
                                System.arraycopy(data, 1, timeArrays, 0, timeArrays.length);
                                switch (timeArrays[6]) {
                                    case 0:
                                        TimeSetting.setTimeTo24(this.mContext);
                                        break;
                                    case 1:
                                        TimeSetting.setTimeTo12(this.mContext);
                                        break;
                                }
                                TimeSetting.setTime(this.mContext, timeArrays[0] & 255, timeArrays[1], timeArrays[2], timeArrays[3], timeArrays[4], timeArrays[5]);
                                break;
                            case 25:
                                mcuStatus.carData.mileage = ((data[1] & 255) << 8) + (data[2] & 255);
                                mcuStatus.carData.oilWear = ((float) (((data[3] & 255) << 8) + (data[4] & 255))) / 10.0f;
                                mcuStatus.carData.averSpeed = ((float) (((data[5] & 255) << 8) + (data[6] & 255))) / 10.0f;
                                mcuStatus.carData.speed = ((data[7] & 255) << 8) + (data[8] & 255);
                                mcuStatus.carData.engineTurnS = ((data[9] & 255) << 8) + (data[10] & 255);
                                mcuStatus.carData.oilSum = ((data[11] & 255) << 8) + (data[12] & 255);
                                if ((data[13] & 128) > 0) {
                                    mcuStatus.carData.airTemperature = (float) (((double) (65535 & ((~(((data[13] & 255) * 256) + (data[14] & 255))) + 1))) * -0.1d);
                                } else {
                                    mcuStatus.carData.airTemperature = (float) (((double) (65535 & (((data[13] & 255) * 256) + (data[14] & 255)))) * 0.1d);
                                }
                                mcuStatus.carData.distanceUnitType = data[15] & 8;
                                mcuStatus.carData.temperatureUnitType = data[15] & 4;
                                mcuStatus.carData.oilUnitType = data[15] & 2;
                                mcuStatus.carData.oilUnitType += data[15] & 255 & 1;
                                break;
                            case 26:
                                if (data[1] == 2) {
                                    CenterControlImpl.getImpl().stopMedia();
                                    try {
                                        if (Settings.System.getInt(this.mContext.getContentResolver(), "CarDisplay") == 0) {
                                            Intent intent = new Intent(this.mContext, ClockActivity.class);
                                            intent.setFlags(268435456);
                                            this.mContext.startActivity(intent);
                                        }
                                    } catch (Settings.SettingNotFoundException e) {
                                    }
                                }
                                mcuStatus.systemMode = data[1];
                                if (mcuStatus.systemMode == 1 && systemStatus.lastMode != 6 && systemStatus.lastMode != 9 && systemStatus.lastMode != 5 && systemStatus.lastMode != 11 && !SystemProperties.get(ZlinkMessage.ZLINK_CALL).equals("1") && !SystemProperties.get(ZlinkMessage.ZLINK_CALL).equals("2") && !SystemProperties.get(AutoKitMessage.AUTOBOX_CALL).equals("1") && !SystemProperties.get(AutoKitMessage.AUTOBOX_CALL).equals("2")) {
                                    CenterControlImpl.getImpl().home();
                                    break;
                                }
                        }
                    } else {
                        mcuStatus.acData.isOpen = (data[1] & 128) != 0;
                        mcuStatus.acData.AC_Switch = (data[1] & 64) != 0;
                        mcuStatus.acData.loop = (data[1] & 32) != 0 ? 1 : 0;
                        mcuStatus.acData.frontMistSwitch = (data[1] & 8) != 0;
                        mcuStatus.acData.backMistSwitch = (data[1] & 2) != 0;
                        mcuStatus.acData.sync = (data[1] & 1) != 0;
                        mcuStatus.acData.mode = data[2];
                        mcuStatus.acData.setLeftTmp(data[3]);
                        mcuStatus.acData.setRightTmp(data[4]);
                        McuStatus.ACData aCData = mcuStatus.acData;
                        if ((data[5] & 16) != 0) {
                            isAccOpen = true;
                        }
                        aCData.autoSwitch = isAccOpen;
                        mcuStatus.acData.speed = ((float) (data[5] & 15)) * 0.5f;
                    }
                } else if (cmdType != 224) {
                    switch (cmdType) {
                        case 16:
                            if (data[0] == 1) {
                                isAccOpen = true;
                            }
                            if (!isAccOpen) {
                                this.mHandler.postDelayed(new KswMcuListener$$Lambda$0(this), 1000);
                                break;
                            } else {
                                systemStatus.setAcc(1);
                                AccLight.show(500);
                                break;
                            }
                        case 17:
                            if (data[0] == 4 && data[1] == 1) {
                                CenterControlImpl.getImpl().closeScreen(false);
                            }
                            if (data[0] > 3) {
                                if (data[0] == 5) {
                                    systemStatus.setAcc(data[1]);
                                    break;
                                }
                            } else {
                                systemStatus.setCcd(data[1]);
                                break;
                            }
                            break;
                        case 18:
                            byte[] mcub = new byte[40];
                            System.arraycopy(data, 0, mcub, 0, mcub.length);
                            mcuStatus.mcuVerison = new String(mcub);
                            break;
                        default:
                            switch (cmdType) {
                                case 29:
                                    mcuStatus.benzData.highChassisSwitch = data[0] == 1;
                                    mcuStatus.benzData.airMaticStatus = data[1];
                                    mcuStatus.benzData.auxiliaryRadar = data[2] == 1;
                                    mcuStatus.benzData.light1 = data[3];
                                    mcuStatus.benzData.light2 = data[4];
                                    McuStatus.BenzData benzData = mcuStatus.benzData;
                                    if (data[5] == 1) {
                                        isAccOpen = true;
                                    }
                                    benzData.airBagSystem = isAccOpen;
                                    break;
                            }
                    }
                } else if (data[0] == 4) {
                    if (ForceMcuUpdate.mcuFile != null && ForceMcuUpdate.mcuFile.exists()) {
                        ForceMcuUpdate.fix(this.mContext);
                    }
                    ForceMcuUpdate.NEEDFIX = true;
                }
            } else if (data.length >= 9) {
                KswSettings.getSettings().setUpProtocolForMcuListen(data[8]);
            }
            SystemStatusControl.getDefault().handle();
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onMcuMessage$0$KswMcuListener(SystemStatus systemStatus, byte[] data) {
        systemStatus.lastMode = data[1];
        int i = 0;
        switch (data[1]) {
            case 1:
                if (Build.DEVICE.contains("8937")) {
                    try {
                        Log.d(TAG, "memory music 8917 delay 02");
                        while (true) {
                            int i2 = i;
                            if (i2 < 20) {
                                Thread.sleep(500);
                                if (!SystemStatusControl.getStatus().topApp.equals("com.wits.ksw")) {
                                    i = i2 + 1;
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CenterControlImpl.getImpl().openMusic(true);
                return;
            case 2:
                if (Build.DEVICE.contains("8937")) {
                    try {
                        Log.d(TAG, "memory music 8917 delay  02");
                        while (true) {
                            int i3 = i;
                            if (i3 < 20) {
                                Thread.sleep(500);
                                if (!SystemStatusControl.getStatus().topApp.equals("com.wits.ksw")) {
                                    i = i3 + 1;
                                }
                            }
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                CenterControlImpl.getImpl().openVideo(true);
                return;
            case 3:
                CenterControlImpl.getImpl().openBluetooth(true);
                return;
            case 5:
                try {
                    if (Settings.System.getInt(this.mContext.getContentResolver(), "DVR_Type") == 1) {
                        this.mMcuSender.sendMessage(103, new byte[]{5});
                        return;
                    }
                    return;
                } catch (Settings.SettingNotFoundException e3) {
                    return;
                }
            case 6:
                CenterControlImpl.getImpl().openAux(true);
                return;
            case 9:
                CenterControlImpl.getImpl().openDtv(true);
                return;
            case 11:
                CenterControlImpl.getImpl().openFrontCamera(true);
                return;
            default:
                return;
        }
    }

    private void openApp() {
        new Handler(Looper.getMainLooper()).post(new KswMcuListener$$Lambda$2(this));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$openApp$1$KswMcuListener() {
        Intent lastIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(this.memoryPackage);
        if (lastIntent == null) {
            lastIntent = new Intent();
            String str = this.memoryPackage;
            char c = 65535;
            if (str.hashCode() == 1411998635 && str.equals("com.wits.video")) {
                c = 0;
            }
            if (c == 0) {
                lastIntent.setComponent(new ComponentName(this.memoryPackage, "com.wits.video.MainActivity"));
            }
        }
        if (lastIntent != null) {
            try {
                this.mContext.startActivity(lastIntent);
            } catch (Exception e) {
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: shutdown */
    public void bridge$lambda$0$KswMcuListener() {
        SystemProperties.set("sys.powerctl", "shutdown");
    }
}
