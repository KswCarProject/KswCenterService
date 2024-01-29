package com.wits.pms.mcu.custom;

import android.bluetooth.BluetoothHidDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.wifi.WifiScanner;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimedRemoteCaller;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.midi.MidiConstants;
import com.android.internal.telephony.IccCardConstants;
import com.google.gson.Gson;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.wits.pms.ClockActivity;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.CallBackServiceImpl;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.mcu.custom.utils.AccLight;
import com.wits.pms.mcu.custom.utils.CanLogUtils;
import com.wits.pms.mcu.custom.utils.ForceMcuUpdate;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.TimeSetting;
import com.wits.pms.utils.TouchControl;
import com.wits.pms.utils.Utils;
import java.nio.charset.StandardCharsets;
import org.mozilla.universalchardet.UniversalDetector;
import org.mozilla.universalchardet.prober.HebrewProber;

/* loaded from: classes2.dex */
public class KswMcuListener extends KswMcuReceiver {
    public static final String TAG = "KswMcuListener";
    private int bootTime = 0;
    private final Context mContext = PowerManagerAppService.serviceContext;
    private final Handler mHandler = new Handler(this.mContext.getMainLooper());
    private final KswMcuSender mMcuSender = CenterControlImpl.getMcuSender();
    private TouchControl mTouchControl;
    private String memoryPackage;
    private boolean openBluetooth;

    /* loaded from: classes2.dex */
    public static final class MediaType {
        public static final int SRC_ALL_APP = 13;
        public static final int SRC_AUX = 6;
        public static final int SRC_BT = 3;
        public static final int SRC_BT_MUSIC = 4;
        public static final int SRC_CAR = 0;
        public static final int SRC_CAR_FM = 14;
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
        this.mTouchControl = new TouchControl(this.mContext);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("can_bus_switch"), true, new ContentObserver(this.mHandler) { // from class: com.wits.pms.mcu.custom.KswMcuListener.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                CanLogUtils.canLogSwitch(Settings.System.getInt(KswMcuListener.this.mContext.getContentResolver(), "can_bus_switch", 0) == 1);
            }
        });
        Settings.System.putInt(this.mContext.getContentResolver(), "can_bus_switch", 0);
    }

    @Override // com.wits.pms.mcu.custom.KswMcuReceiver
    public void onMcuMessage(byte[] pack) {
        KswMessage message = KswMessage.parse(pack);
        onMcuMessage(message);
    }

    public void onMcuMessage(KswMessage message) {
        McuStatus.MediaData.BaseMediaInfo currentMediaInfo;
        Log.m68i(TAG, message + "");
        if (message == null) {
            return;
        }
        KswMcuLogic.handleMsg(message);
        UpdateHelper.getInstance().handleMessage(message);
        if (CanLogUtils.isCating()) {
            CanLogUtils.canLog(message);
        }
        final SystemStatus systemStatus = SystemStatusControl.getStatus();
        McuStatus mcuStatus = SystemStatusControl.getDefault().getMcuStatus();
        final byte[] data = message.getData();
        int cmdType = message.getCmdType();
        if (cmdType == 21) {
            Log.m68i(TAG, "memory mode: " + ((int) data[1]));
            try {
                int memoryMode = Settings.System.getInt(this.mContext.getContentResolver(), "memory_mode_for_freedom");
                if (data[1] == 13) {
                    Settings.System.putInt(this.mContext.getContentResolver(), "memory_mode_for_freedom", 0);
                } else if (memoryMode == 0) {
                    Settings.System.putInt(this.mContext.getContentResolver(), "memory_mode_for_freedom", 1);
                }
            } catch (Settings.SettingNotFoundException e) {
                Settings.System.putInt(this.mContext.getContentResolver(), "memory_mode_for_freedom", 1);
            }
            if (data[0] == 1) {
                if (data[1] != 0 && data[1] != 6 && data[1] != 9 && data[1] != 5 && data[1] != 11 && this.bootTime == 0) {
                    r10 = true;
                }
                final boolean isDelay = r10;
                this.bootTime = 1;
                this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$U11nBajvlH9FvPJVYF0TydID9Vs
                    @Override // java.lang.Runnable
                    public final void run() {
                        KswMcuListener.lambda$onMcuMessage$0(KswMcuListener.this, systemStatus, data, isDelay);
                    }
                }, isDelay ? TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS : 0L);
            }
        } else if (cmdType != 23) {
            int action = 2;
            if (cmdType != 161) {
                if (cmdType == 224) {
                    if (data[0] == 4) {
                        if (ForceMcuUpdate.mcuFile != null && ForceMcuUpdate.mcuFile.exists()) {
                            ForceMcuUpdate.fix(this.mContext);
                        }
                        ForceMcuUpdate.NEEDFIX = true;
                    }
                } else {
                    switch (cmdType) {
                        case 16:
                            boolean isAccOpen = data[0] == 1;
                            if (!isAccOpen) {
                                this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        KswMcuListener.this.shutdown();
                                    }
                                }, 1000L);
                                break;
                            } else {
                                systemStatus.setAcc(1);
                                AccLight.show(500L);
                                break;
                            }
                        case 17:
                            if (data[0] == 4 && data[1] == 1) {
                                CenterControlImpl.getImpl().closeScreen(false);
                            }
                            if (data[0] <= 3) {
                                systemStatus.setCcd(data[1]);
                                break;
                            } else if (data[0] == 5) {
                                systemStatus.setAcc(data[1]);
                                break;
                            }
                            break;
                        case 18:
                            byte[] mcub = new byte[40];
                            System.arraycopy(data, 0, mcub, 0, mcub.length);
                            String mcuVer = new String(mcub);
                            mcuStatus.mcuVerison = mcuVer;
                            break;
                        default:
                            switch (cmdType) {
                                case 27:
                                    int x = ((data[0] & 255) << 8) + (data[1] & 255);
                                    int y = ((data[2] & 255) << 8) + (data[3] & 255);
                                    int buttonState = data[4];
                                    boolean buttonDown = buttonState == 1;
                                    if (buttonDown) {
                                        if (!this.mTouchControl.isDown()) {
                                            action = 0;
                                        }
                                    } else if (this.mTouchControl.isDown()) {
                                        action = 1;
                                    }
                                    this.mTouchControl.opPointerEvent(x, 255 - y, action);
                                    break;
                                case 29:
                                    mcuStatus.benzData.highChassisSwitch = data[0] == 1;
                                    mcuStatus.benzData.airMaticStatus = data[1];
                                    mcuStatus.benzData.auxiliaryRadar = data[2] == 1;
                                    mcuStatus.benzData.light1 = data[3];
                                    mcuStatus.benzData.light2 = data[4];
                                    mcuStatus.benzData.airBagSystem = data[5] == 1;
                                    break;
                                case 30:
                                    Intent mcuIntent = new Intent("com.wits.pms.MCU_CHECK_CAR");
                                    mcuIntent.addFlags(16777248);
                                    mcuIntent.putExtra("checkCanStatus", new byte[]{data[0], data[1], data[2], data[3], data[4], data[5]});
                                    this.mContext.sendBroadcastAsUser(mcuIntent, UserHandle.getUserHandleForUid(this.mContext.getApplicationInfo().uid));
                                    return;
                                case 31:
                                    try {
                                        McuStatus.MediaData mediaData = mcuStatus.mediaData;
                                        mediaData.times++;
                                        mediaData.type = data[0];
                                        if (data[1] == 255) {
                                            data[1] = -1;
                                        }
                                        if (data[2] == 255) {
                                            data[2] = -1;
                                        }
                                        if (data[5] == 255) {
                                            data[5] = -1;
                                        }
                                        if (data[6] == 255) {
                                            data[6] = -1;
                                        }
                                        byte b = data[0];
                                        if (b == 1) {
                                            McuStatus.MediaData.C3670Fm fm = mcuStatus.mediaData.f2578fm;
                                            byte b2 = data[1];
                                            switch (b2) {
                                                case 0:
                                                case 1:
                                                case 2:
                                                case 3:
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append("FM");
                                                    sb.append(data[1] == 0 ? "" : Byte.valueOf(data[1]));
                                                    fm.name = sb.toString();
                                                    fm.freq = ((((data[2] & 255) << 8) + (data[3] & 255)) / 100.0f) + "Mhz";
                                                    fm.preFreq = data[4];
                                                    break;
                                                default:
                                                    switch (b2) {
                                                        case 16:
                                                        case 17:
                                                        case 18:
                                                        case 19:
                                                            StringBuilder sb2 = new StringBuilder();
                                                            sb2.append("AM");
                                                            sb2.append(data[1] == 16 ? "" : Integer.valueOf(data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK));
                                                            fm.name = sb2.toString();
                                                            fm.freq = (((data[2] & 255) << 8) + (data[3] & 255)) + "Khz";
                                                            fm.preFreq = data[4];
                                                            break;
                                                    }
                                            }
                                            if (data[1] == -1) {
                                                fm.name = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
                                            }
                                            if (data[2] == -1) {
                                                fm.freq = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
                                            }
                                            mediaData.f2578fm = fm;
                                        } else if (b == 21) {
                                            mcuStatus.bluetoothStatus.times++;
                                            mcuStatus.bluetoothStatus.min = data[5];
                                            mcuStatus.bluetoothStatus.sec = data[6];
                                            PowerManagerApp.setStatusString("mcuBluetoothStatus", new Gson().toJson(mcuStatus.bluetoothStatus));
                                        } else if (b != 64) {
                                            switch (b) {
                                                case 16:
                                                    McuStatus.MediaData.Disc disc = mcuStatus.mediaData.disc;
                                                    disc.number = data[1];
                                                    disc.track = data[2];
                                                    disc.min = data[5];
                                                    disc.sec = data[6];
                                                    mcuStatus.mediaStringInfo.min = disc.min;
                                                    mcuStatus.mediaStringInfo.sec = disc.sec;
                                                    mediaData.disc = disc;
                                                    break;
                                                case 17:
                                                    McuStatus.MediaData.Usb usb = mcuStatus.mediaData.usb;
                                                    usb.folderNumber = data[1] + (data[2] << 8);
                                                    usb.fileNumber = data[3] + (data[4] << 8);
                                                    usb.min = data[5];
                                                    usb.sec = data[6];
                                                    mcuStatus.mediaStringInfo.min = usb.min;
                                                    mcuStatus.mediaStringInfo.sec = usb.sec;
                                                    mediaData.usb = usb;
                                                    break;
                                                case 18:
                                                    McuStatus.MediaData.MP3 mp3 = mcuStatus.mediaData.mp3;
                                                    mp3.folderNumber = data[1] + (data[2] << 8);
                                                    mp3.fileNumber = data[3] + (data[4] << 8);
                                                    mp3.min = data[5];
                                                    mp3.sec = data[6];
                                                    mcuStatus.mediaStringInfo.min = mp3.min;
                                                    mcuStatus.mediaStringInfo.sec = mp3.sec;
                                                    mediaData.mp3 = mp3;
                                                    break;
                                            }
                                        } else {
                                            McuStatus.MediaData.MODE mode = mcuStatus.mediaData.mode;
                                            mode.ASL = data[1] == 1;
                                            mode.f2579ST = data[2] == 1;
                                            mode.RAND = data[3] == 1;
                                            mode.RPT = data[4] == 1;
                                            mode.PAUSE = data[5] == 1;
                                            mode.SCAN = data[6] == 1;
                                            mediaData.mode = mode;
                                        }
                                        PowerManagerApp.setStatusString("mcuMediaJson", new Gson().toJson(mcuStatus.mediaData));
                                        return;
                                    } catch (Exception e2) {
                                        return;
                                    }
                                case 32:
                                    if (mcuStatus.mediaStringInfo == null) {
                                        return;
                                    }
                                    mcuStatus.mediaStringInfo.times++;
                                    String info = autoGetString(data);
                                    if (info == null || (currentMediaInfo = mcuStatus.mediaData.getCurrentMediaInfo()) == null) {
                                        return;
                                    }
                                    switch (data[0]) {
                                        case 1:
                                            if (currentMediaInfo.name != null && !currentMediaInfo.name.equals(info)) {
                                                currentMediaInfo.reset();
                                            }
                                            currentMediaInfo.name = info;
                                            break;
                                        case 2:
                                            currentMediaInfo.artist = info;
                                            break;
                                        case 3:
                                            currentMediaInfo.album = info;
                                            break;
                                        case 4:
                                            currentMediaInfo.folderName = info;
                                            break;
                                    }
                                    PowerManagerApp.setStatusString("mcuMediaJson", new Gson().toJson(mcuStatus.mediaData));
                                    return;
                                case 33:
                                    mcuStatus.mediaData.times++;
                                    mcuStatus.mediaPlayStatus.times++;
                                    mcuStatus.mediaData.type = data[0];
                                    PowerManagerApp.setStatusString("mcuMediaJson", new Gson().toJson(mcuStatus.mediaData));
                                    String status = "";
                                    switch (data[1]) {
                                        case 0:
                                            status = "PLAY";
                                            break;
                                        case 1:
                                            status = "PAUSE";
                                            break;
                                        case 6:
                                            if (mcuStatus.mediaData.type == 16) {
                                                status = "READING DISC";
                                                break;
                                            } else if (mcuStatus.mediaData.type == 17) {
                                                status = "READING FILE";
                                                break;
                                            }
                                            break;
                                        case 7:
                                            status = "ERROR";
                                            break;
                                        case 8:
                                            status = "NO MUSIC";
                                            break;
                                        case 14:
                                            status = "AUDIO OFF";
                                            break;
                                        case 15:
                                            status = IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
                                            break;
                                    }
                                    mcuStatus.mediaPlayStatus.status = status;
                                    mcuStatus.mediaPlayStatus.RPT = (data[2] & WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) > 1;
                                    mcuStatus.mediaPlayStatus.f2580ST = (data[2] & 8) > 1;
                                    mcuStatus.mediaPlayStatus.SCAN = (data[2] & 2) > 1;
                                    mcuStatus.mediaPlayStatus.RAND = (data[2] & 1) >= 1;
                                    PowerManagerApp.setStatusString("mcuMediaPlayStatus", new Gson().toJson(mcuStatus.mediaPlayStatus));
                                    return;
                                case 34:
                                    mcuStatus.discStatus.times++;
                                    String pStatus = "";
                                    switch (data[0]) {
                                        case 1:
                                            pStatus = "LOAD";
                                            break;
                                        case 2:
                                            pStatus = "EJECT";
                                            break;
                                        case 3:
                                            pStatus = "DISC IN";
                                            break;
                                        case 4:
                                            pStatus = "DISC FULL";
                                            break;
                                        case 5:
                                            pStatus = "WAIT";
                                            break;
                                    }
                                    mcuStatus.discStatus.discInsert = new boolean[6];
                                    for (int i = 0; i < mcuStatus.discStatus.discInsert.length; i++) {
                                        mcuStatus.discStatus.discInsert[i] = ((data[1] & 255) & (1 << i)) >= 1;
                                    }
                                    mcuStatus.discStatus.status = pStatus;
                                    mcuStatus.discStatus.range = data[2];
                                    PowerManagerApp.setStatusString("mcuDiscStatus", new Gson().toJson(mcuStatus.discStatus));
                                    return;
                                case 35:
                                    try {
                                        int level = data[0] & Bidi.LEVEL_DEFAULT_RTL;
                                        boolean mute = (data[0] & 128) > 0;
                                        PowerManagerApp.setStatusInt("mcu_volume_level", level);
                                        PowerManagerApp.setBooleanStatus("mcu_volume_mute", mute);
                                        break;
                                    } catch (Exception e3) {
                                        break;
                                    }
                                case 36:
                                    mcuStatus.mediaData.times++;
                                    mcuStatus.bluetoothStatus.times++;
                                    mcuStatus.bluetoothStatus.isCalling = (data[0] & 128) > 1;
                                    mcuStatus.bluetoothStatus.callSignal = (data[0] & MidiConstants.STATUS_PITCH_BEND) >> 5;
                                    mcuStatus.bluetoothStatus.playingMusic = (data[0] & 8) > 1;
                                    mcuStatus.bluetoothStatus.batteryStatus = data[0] & 7;
                                    PowerManagerApp.setStatusString("mcuBluetoothStatus", new Gson().toJson(mcuStatus.bluetoothStatus));
                                    PowerManagerApp.setStatusString("mcuMediaJson", new Gson().toJson(mcuStatus.mediaData));
                                    break;
                                case 37:
                                    mcuStatus.mediaData.times++;
                                    byte[] btStringBytes = new byte[data.length - 1];
                                    System.arraycopy(data, 1, btStringBytes, 0, btStringBytes.length);
                                    String btInfo = new String(btStringBytes, StandardCharsets.US_ASCII);
                                    byte b3 = data[0];
                                    if (b3 == 1) {
                                        mcuStatus.bluetoothStatus.name = btInfo;
                                    } else if (b3 == 4) {
                                        mcuStatus.bluetoothStatus.settingsInfo = btInfo;
                                    }
                                    PowerManagerApp.setStatusString("mcuBluetoothStatus", new Gson().toJson(mcuStatus.bluetoothStatus));
                                    PowerManagerApp.setStatusString("mcuMediaJson", new Gson().toJson(mcuStatus.mediaData));
                                    break;
                                case 38:
                                    mcuStatus.eqData.times++;
                                    switch (data[0]) {
                                        case 1:
                                            mcuStatus.eqData.BAS = data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK;
                                            mcuStatus.eqData.changeVol = "BAS";
                                            break;
                                        case 2:
                                            mcuStatus.eqData.MID = data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK;
                                            mcuStatus.eqData.changeVol = "MID";
                                            break;
                                        case 3:
                                            mcuStatus.eqData.TRE = data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK;
                                            mcuStatus.eqData.changeVol = "TRE";
                                            break;
                                        case 4:
                                            mcuStatus.eqData.FAD = data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK;
                                            mcuStatus.eqData.changeVol = "FAD";
                                            break;
                                        case 5:
                                            mcuStatus.eqData.BAL = data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK;
                                            mcuStatus.eqData.changeVol = "BAL";
                                            break;
                                        case 6:
                                            mcuStatus.mediaPlayStatus.times++;
                                            mcuStatus.mediaData.mode.ASL = data[1] == 1;
                                            mcuStatus.mediaPlayStatus.ALS = data[1] == 1;
                                            McuStatus.EqData eqData = mcuStatus.eqData;
                                            StringBuilder sb3 = new StringBuilder();
                                            sb3.append("ASL ");
                                            sb3.append(data[1] == 1 ? "ON" : "OFF");
                                            eqData.changeVol = sb3.toString();
                                            PowerManagerApp.setStatusString("mcuMediaJson", new Gson().toJson(mcuStatus.mediaData));
                                            PowerManagerApp.setStatusString("mcuMediaPlayStatus", new Gson().toJson(mcuStatus.mediaPlayStatus));
                                            break;
                                    }
                                    mcuStatus.eqData.volume = data[1] - WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK;
                                    PowerManagerApp.setStatusString("mcuEqData", new Gson().toJson(mcuStatus.eqData));
                                    KswMcuSender.getSender().sendMessageDelay(123, new byte[]{0}, 3500L);
                                    break;
                                case 39:
                                    if ((data[0] & 1) >= 1) {
                                    }
                                    if ((data[0] & 2) > 1) {
                                    }
                                    if ((data[0] & 8) > 1) {
                                    }
                                    if ((data[0] & WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) > 1) {
                                    }
                                    if ((data[0] & HebrewProber.SPACE) > 1) {
                                    }
                                    boolean isCarDoorClose = Utils.getIndexValue2DataNew(data[0], 0, 1) == 0;
                                    boolean isSeatbeltOn = Utils.getIndexValue2DataNew(data[0], 1, 1) == 0;
                                    boolean isEnoughOil = Utils.getIndexValue2DataNew(data[0], 2, 1) == 0;
                                    boolean isNormalSpeed = Utils.getIndexValue2DataNew(data[0], 3, 1) == 0;
                                    boolean isTempNormal = Utils.getIndexValue2DataNew(data[0], 4, 1) == 0;
                                    Log.m72d(TAG, "onMcuMessage: CMD_TXZ_DATA isCarDoorClose =" + isCarDoorClose + "  isSeatbeltOn =" + isSeatbeltOn + "  isNoEnoughOil =" + isEnoughOil + " isNormalSpeed =" + isNormalSpeed + " isTempNormal =" + isTempNormal);
                                    boolean isNormalState = isCarDoorClose && isSeatbeltOn && isEnoughOil && isNormalSpeed && isTempNormal;
                                    CenterControlImpl.getImpl().sendCarInfo2Txz(isNormalState, isCarDoorClose, isSeatbeltOn, isNormalSpeed, isTempNormal, isEnoughOil, mcuStatus.carData);
                                    break;
                            }
                    }
                }
            } else {
                switch (data[0]) {
                    case 16:
                        systemStatus.ill = data[1] & 255 & 1;
                        systemStatus.setEpb(data[1] & 255 & 8);
                        mcuStatus.carData.handbrake = ((data[1] & 255) & 8) != 0;
                        mcuStatus.carData.safetyBelt = ((data[2] & 255) & 1) != 0;
                        mcuStatus.carData.carGear = data[2] & 255 & 6;
                        mcuStatus.carData.signalLeft = data[2] & 255 & 8;
                        mcuStatus.carData.signalRight = data[2] & 255 & 16;
                        mcuStatus.carData.signalDouble = data[2] & 255 & 32;
                        CallBackServiceImpl.getCallBackServiceImpl().handleLRReverse();
                        SystemStatusControl.getDefault().getPms().updateMcuJsonStatus("mcuJson", new Gson().toJson(mcuStatus));
                        if (mcuStatus.carData.carGear == 0) {
                            CenterControlImpl.getImpl().TxzCheckGearAck(0);
                            return;
                        } else {
                            CenterControlImpl.getImpl().TxzCheckGearAck(1);
                            return;
                        }
                    case 18:
                        mcuStatus.carData.carDoor = data[1] & 255;
                        SystemStatusControl.getDefault().getPms().updateMcuJsonStatus("mcuJson", new Gson().toJson(mcuStatus));
                        break;
                    case 19:
                        mcuStatus.carData.carWheelAngle = ((data[1] & 255) * 256) + (data[2] & 255);
                        SystemStatusControl.getDefault().getPms().updateMcuJsonStatus("mcuJson", new Gson().toJson(mcuStatus));
                        Log.m68i(TAG, "Task#18097 -- onMcuMessage: carWheelAngle = " + mcuStatus.carData.carWheelAngle);
                        break;
                    case 20:
                        mcuStatus.carData.frontRadarDataL = data[1] & 255;
                        mcuStatus.carData.frontRadarDataLM = data[2] & 255;
                        mcuStatus.carData.frontRadarDataRM = data[3] & 255;
                        mcuStatus.carData.frontRadarDataR = data[4] & 255;
                        SystemStatusControl.getDefault().getPms().updateMcuJsonStatus("mcuJson", new Gson().toJson(mcuStatus));
                        Log.m68i(TAG, "Task#18097 -- onMcuMessage: frontRadarData = " + mcuStatus.carData.frontRadarDataL + "/" + mcuStatus.carData.frontRadarDataLM + "/" + mcuStatus.carData.frontRadarDataRM + "/" + mcuStatus.carData.frontRadarDataR);
                        break;
                    case 21:
                        mcuStatus.carData.backRadarDataL = data[1] & 255;
                        mcuStatus.carData.backRadarDataLM = data[2] & 255;
                        mcuStatus.carData.backRadarDataRM = data[3] & 255;
                        mcuStatus.carData.backRadarDataR = data[4] & 255;
                        SystemStatusControl.getDefault().getPms().updateMcuJsonStatus("mcuJson", new Gson().toJson(mcuStatus));
                        Log.m68i(TAG, "Task#18097 -- onMcuMessage: backRadarData = " + mcuStatus.carData.backRadarDataL + "/" + mcuStatus.carData.backRadarDataLM + "/" + mcuStatus.carData.backRadarDataRM + "/" + mcuStatus.carData.backRadarDataR);
                        break;
                    case 23:
                        if (data[2] != 1) {
                            return;
                        }
                        byte b4 = data[1];
                        if (b4 != 13) {
                            if (b4 != 20) {
                                switch (b4) {
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
                                        int mPageIndex = Settings.System.getInt(this.mContext.getContentResolver(), "mPageIndex", 0);
                                        if (mPageIndex == 1 || !SystemStatusControl.getStatus().topApp.contains("autonavi")) {
                                            return;
                                        }
                                        Intent intent1 = new Intent();
                                        intent1.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
                                        intent1.putExtra("KEY_TYPE", 10027);
                                        intent1.putExtra("EXTRA_TYPE", 1);
                                        intent1.putExtra("EXTRA_OPERA", 0);
                                        this.mContext.sendBroadcast(intent1);
                                        break;
                                        break;
                                    case 7:
                                        AutoKitCallBackImpl.getImpl(this.mContext).drapLeft();
                                        int index = Settings.System.getInt(this.mContext.getContentResolver(), "mPageIndex", 0);
                                        if (index == 1 || !SystemStatusControl.getStatus().topApp.contains("autonavi")) {
                                            return;
                                        }
                                        Intent intent2 = new Intent();
                                        intent2.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
                                        intent2.putExtra("KEY_TYPE", 10027);
                                        intent2.putExtra("EXTRA_TYPE", 1);
                                        intent2.putExtra("EXTRA_OPERA", 1);
                                        this.mContext.sendBroadcast(intent2);
                                        break;
                                        break;
                                    default:
                                        switch (b4) {
                                            case 30:
                                                if (SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1")) {
                                                    if (!SystemStatusControl.getStatus().topApp.contains(ZlinkMessage.ZLINK_NORMAL_ACTION)) {
                                                        CenterControlImpl.getImpl().zlinkHandleCall();
                                                        break;
                                                    }
                                                } else if (SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT).equals("1")) {
                                                    CenterControlImpl.getImpl().zlinkHandleAutoCall();
                                                    break;
                                                } else if (SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1")) {
                                                    AutoKitCallBackImpl.getImpl(this.mContext).acceptPhone();
                                                    break;
                                                } else if ("true".equals(SystemProperties.get("persist.sys.hicar_connect"))) {
                                                    WitsCommand.sendCommand(7, 111);
                                                    break;
                                                } else {
                                                    CenterControlImpl.getImpl().acceptPhone();
                                                    break;
                                                }
                                                break;
                                            case 31:
                                                if (SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1")) {
                                                    if (!SystemStatusControl.getStatus().topApp.contains(ZlinkMessage.ZLINK_NORMAL_ACTION)) {
                                                        CenterControlImpl.getImpl().zlinkHandleCall();
                                                        break;
                                                    }
                                                } else if (SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT).equals("1")) {
                                                    CenterControlImpl.getImpl().zlinkHandleAutoCall();
                                                    break;
                                                } else if (SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1")) {
                                                    AutoKitCallBackImpl.getImpl(this.mContext).rejectPhone();
                                                    break;
                                                } else if ("true".equals(SystemProperties.get("persist.sys.hicar_connect"))) {
                                                    WitsCommand.sendCommand(7, 112);
                                                    break;
                                                } else {
                                                    CenterControlImpl.getImpl().handUpPhone();
                                                    break;
                                                }
                                                break;
                                        }
                                }
                            }
                        } else {
                            CenterControlImpl.getImpl().openSettings();
                            break;
                        }
                        break;
                    case 24:
                        byte[] timeArrays = new byte[7];
                        System.arraycopy(data, 1, timeArrays, 0, timeArrays.length);
                        if (timeArrays[6] != 0) {
                        }
                        TimeSetting.setTime(this.mContext, timeArrays[0] & 255, timeArrays[1], timeArrays[2], timeArrays[3], timeArrays[4], timeArrays[5]);
                        break;
                    case 25:
                        mcuStatus.carData.mileage = ((data[1] & 255) << 8) + (data[2] & 255);
                        mcuStatus.carData.oilWear = (((data[3] & 255) << 8) + (data[4] & 255)) / 10.0f;
                        mcuStatus.carData.averSpeed = (((data[5] & 255) << 8) + (data[6] & 255)) / 10.0f;
                        mcuStatus.carData.speed = ((data[7] & 255) << 8) + (data[8] & 255);
                        mcuStatus.carData.engineTurnS = ((data[9] & 255) << 8) + (data[10] & 255);
                        mcuStatus.carData.oilSum = ((data[11] & 255) << 8) + (data[12] & 255);
                        if ((data[13] & 128) > 0) {
                            int temp = (~(((data[13] & 255) * 256) + (data[14] & 255))) + 1;
                            mcuStatus.carData.airTemperature = (float) ((65535 & temp) * (-0.1d));
                        } else {
                            int temp2 = ((data[13] & 255) * 256) + (data[14] & 255);
                            mcuStatus.carData.airTemperature = (float) ((65535 & temp2) * 0.1d);
                        }
                        mcuStatus.carData.distanceUnitType = data[15] & 8;
                        mcuStatus.carData.oilUnitType = data[15] & 2;
                        mcuStatus.carData.oilUnitType += data[15] & 255 & 1;
                        SystemStatusControl.getDefault().getPms().updateMcuJsonStatus("mcuJson", new Gson().toJson(mcuStatus));
                        return;
                    case 26:
                        if (data[1] == 2) {
                            CenterControlImpl.getImpl().stopMedia();
                            try {
                                if (Settings.System.getInt(this.mContext.getContentResolver(), "CarDisplay") == 0 && Settings.System.getInt(this.mContext.getContentResolver(), "OEM_FM", 0) == 0) {
                                    Intent intent = new Intent(this.mContext, ClockActivity.class);
                                    intent.setFlags(268435456);
                                    this.mContext.startActivity(intent);
                                }
                            } catch (Settings.SettingNotFoundException e4) {
                                e4.printStackTrace();
                            }
                        }
                        mcuStatus.systemMode = data[1];
                        if (mcuStatus.systemMode == 1) {
                            try {
                                int callStatus = PowerManagerApp.getStatusInt("callStatus");
                                if (callStatus != 7) {
                                    r11 = false;
                                }
                                BtPhoneStatus.isCalling(callStatus);
                            } catch (RemoteException e5) {
                            }
                            if (SystemProperties.get("persist.sys.hicar_connect").equals("true")) {
                                CenterControlImpl.getImpl().stopHicarMusic(false);
                            }
                            CenterControlImpl.getImpl().handleZlinkBackCar(false);
                            break;
                        } else if (mcuStatus.systemMode == 2) {
                            CenterControlImpl.getImpl().handleZlinkBackCar(true);
                            break;
                        }
                        break;
                    case 28:
                        mcuStatus.acData.isOpen = (data[1] & 128) != 0;
                        mcuStatus.acData.AC_Switch = (data[1] & BluetoothHidDevice.SUBCLASS1_KEYBOARD) != 0;
                        mcuStatus.acData.loop = (data[1] & HebrewProber.SPACE) != 0 ? 1 : 0;
                        mcuStatus.acData.loop = (data[1] & WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) != 0 ? 2 : mcuStatus.acData.loop;
                        mcuStatus.acData.frontMistSwitch = (data[1] & 8) != 0;
                        mcuStatus.acData.backMistSwitch = (data[1] & 2) != 0;
                        mcuStatus.acData.sync = (data[1] & 1) != 0;
                        mcuStatus.acData.mode = data[2];
                        mcuStatus.acData.setLeftTmp((int) data[3]);
                        mcuStatus.acData.setRightTmp((int) data[4]);
                        mcuStatus.acData.eco = (data[5] & HebrewProber.SPACE) != 0;
                        mcuStatus.acData.autoSwitch = (data[5] & WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) != 0;
                        mcuStatus.acData.speed = (15 & data[5]) * 0.5f;
                        break;
                }
            }
        } else if (data.length >= 9) {
            KswSettings.getSettings().setUpProtocolForMcuListen(data[8]);
        }
        SystemStatusControl.getDefault().handle();
    }

    public static /* synthetic */ void lambda$onMcuMessage$0(KswMcuListener kswMcuListener, SystemStatus systemStatus, byte[] data, boolean isDelay) {
        int i;
        int i2;
        systemStatus.lastMode = data[1];
        switch (data[1]) {
            case 0:
            case 14:
                try {
                    if (PowerManagerApp.getSettingsInt("OEM_FM") != 1) {
                        return;
                    }
                } catch (RemoteException e) {
                }
                CenterControlImpl.getImpl().openCarFm();
                return;
            case 1:
                if (Build.DEVICE.contains("8937")) {
                    try {
                        Log.m72d(TAG, "memory music 8917 delay 02");
                        for (i = 0; i < 20; i = i + 1) {
                            Thread.sleep(500L);
                            i = SystemStatusControl.getStatus().topApp.equals("com.wits.ksw") ? 0 : i + 1;
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    if (PowerManagerApp.getStatusBoolean("show_360_on_boot")) {
                        PowerManagerApp.setBooleanStatus("show_360_on_boot", false);
                        return;
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
                String musicPkg = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_MUSIC_PKG");
                Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_MUSIC_CLS");
                Log.m72d(TAG, "memory music musicPkg = " + musicPkg);
                if (TextUtils.isEmpty(musicPkg)) {
                    CenterControlImpl.getImpl().openMusic(true);
                    return;
                } else if (musicPkg.equals("cls.local.music")) {
                    CenterControlImpl.getImpl().openMusic(true);
                    return;
                } else {
                    CenterControlImpl.getImpl().openApp(musicPkg);
                    return;
                }
            case 2:
                if (Build.DEVICE.contains("8937")) {
                    try {
                        Log.m72d(TAG, "memory music 8917 delay  02");
                        for (i2 = 0; i2 < 20; i2 = i2 + 1) {
                            Thread.sleep(500L);
                            i2 = SystemStatusControl.getStatus().topApp.equals("com.wits.ksw") ? 0 : i2 + 1;
                        }
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                }
                try {
                    if (PowerManagerApp.getStatusBoolean("show_360_on_boot")) {
                        PowerManagerApp.setBooleanStatus("show_360_on_boot", false);
                        return;
                    }
                } catch (RemoteException e5) {
                    e5.printStackTrace();
                }
                String videoPkg = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_VIDEO_PKG");
                Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_VIDEO_CLS");
                Log.m72d(TAG, "memory video videoPkg = " + videoPkg);
                if (TextUtils.isEmpty(videoPkg)) {
                    CenterControlImpl.getImpl().openVideoFirst(true);
                    return;
                } else if (videoPkg.equals("cls.local.video")) {
                    CenterControlImpl.getImpl().openVideo(true);
                    return;
                } else {
                    CenterControlImpl.getImpl().openApp(videoPkg);
                    return;
                }
            case 3:
                if (!isDelay) {
                    CenterControlImpl.getImpl().openBluetooth(true);
                    return;
                } else if (SystemStatusControl.getStatus().topApp != null && !SystemStatusControl.getStatus().topApp.equals("com.wits.ksw.bt")) {
                    Log.m72d(TAG, "memory bt");
                    try {
                        if (PowerManagerApp.getStatusBoolean("show_360_on_boot")) {
                            PowerManagerApp.setBooleanStatus("show_360_on_boot", false);
                            return;
                        }
                    } catch (RemoteException e6) {
                    }
                    CenterControlImpl.getImpl().openBluetooth(true);
                    return;
                } else {
                    return;
                }
            case 4:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
            default:
                return;
            case 5:
                try {
                    if (Settings.System.getInt(kswMcuListener.mContext.getContentResolver(), "DVR_Type") == 1) {
                        kswMcuListener.mMcuSender.sendMessage(103, new byte[]{5});
                        return;
                    }
                    return;
                } catch (Settings.SettingNotFoundException e7) {
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
        }
    }

    @Nullable
    private String autoGetString(byte[] data) {
        try {
            byte[] checkStringBytes = new byte[(data.length - 1) * 20];
            byte[] stringBytes = new byte[data.length - 1];
            for (int i = 0; i < 20; i++) {
                System.arraycopy(data, 1, checkStringBytes, (data.length - 1) * i, stringBytes.length);
            }
            System.arraycopy(data, 1, stringBytes, 0, stringBytes.length);
            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(checkStringBytes);
            CharsetMatch charsetMatch = charsetDetector.detect();
            String charSet = charsetMatch.getName();
            UniversalDetector detector = new UniversalDetector(null);
            detector.handleData(checkStringBytes, 0, checkStringBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            if (encoding != null && encoding.contains("GB")) {
                String info = new String(stringBytes, encoding);
                return info;
            }
            if (!charSet.contains("windows") && !charSet.equals("UTF-16LE") && charsetMatch.getConfidence() >= 10) {
                if (charsetMatch.getName().equals("Big5") && charsetMatch.getConfidence() >= 10) {
                    String checkString = new String(stringBytes, charSet);
                    String uniString = new String(stringBytes, "Unicode");
                    if (uniString.length() < checkString.length()) {
                        charSet = "Unicode";
                    }
                }
                String info2 = new String(stringBytes, charSet);
                return info2;
            }
            charSet = "Unicode";
            String info22 = new String(stringBytes, charSet);
            return info22;
        } catch (Exception e) {
            Log.m69e(TAG, "ksw media string info parse error", e);
            return null;
        }
    }

    private void openApp() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$s2exNzCXGn0ZgfluW0Zd2KAvfEw
            @Override // java.lang.Runnable
            public final void run() {
                KswMcuListener.lambda$openApp$1(KswMcuListener.this);
            }
        });
    }

    public static /* synthetic */ void lambda$openApp$1(KswMcuListener kswMcuListener) {
        Intent lastIntent = kswMcuListener.mContext.getPackageManager().getLaunchIntentForPackage(kswMcuListener.memoryPackage);
        if (lastIntent == null) {
            lastIntent = new Intent();
            String str = kswMcuListener.memoryPackage;
            char c = '\uffff';
            if (str.hashCode() == 1411998635 && str.equals("com.wits.video")) {
                c = 0;
            }
            if (c == 0) {
                lastIntent.setComponent(new ComponentName(kswMcuListener.memoryPackage, "com.wits.video.MainActivity"));
            }
        }
        if (lastIntent != null) {
            try {
                kswMcuListener.mContext.startActivity(lastIntent);
            } catch (Exception e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdown() {
        SystemProperties.set("sys.powerctl", "shutdown");
    }
}
