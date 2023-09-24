package com.wits.pms.statuscontrol;

import android.p007os.RemoteException;
import com.google.gson.Gson;
import com.wits.pms.statuscontrol.McuStatus;

/* loaded from: classes2.dex */
public class WitsCommand {
    public static final int BT_TYPE = 3;
    public static final int HICAR_TYPE = 7;
    public static final int MCU_TYPE = 5;
    public static final int MEDIA_TYPE = 2;
    public static final int OTA_TYPE = 9;
    public static final int PIP_TYPE = 20;
    public static final int SYSTEM_TYPE = 1;
    private int command;
    private String jsonArg;
    private boolean needResult;
    private int subCommand;

    /* loaded from: classes2.dex */
    public static final class BtSubCommand {
        public static final int AUTO_CONN = 106;
        public static final int CLOSE_BT = 105;
        public static final int MUSIC_NEXT = 101;
        public static final int MUSIC_PAUSE = 104;
        public static final int MUSIC_PLAY = 103;
        public static final int MUSIC_PLAYPAUSE = 102;
        public static final int MUSIC_PREVIOUS = 100;
        public static final int MUSIC_RELEASE = 113;
        public static final int MUSIC_UNRELEASE = 114;
        public static final int OPEN_BT = 107;
        public static final int PHONE_ACCEPT = 112;
        public static final int PHONE_CALL = 108;
        public static final int PHONE_HANDUP = 109;
        public static final int TXZ_DISABLED = 115;
        public static final int VOICE_TO_PHONE = 110;
        public static final int VOICE_TO_SYSTEM = 111;
    }

    /* loaded from: classes2.dex */
    public static final class HiCarSubCommand {
        public static final int HICAR_ANSWER = 111;
        public static final int HICAR_APP = 101;
        public static final int HICAR_HANDUP = 112;
        public static final int HICAR_TEL = 113;
        public static final int HICAR_VOICE = 102;
    }

    /* loaded from: classes2.dex */
    public static final class MediaSubCommand {
        public static final int CLOSE_MUSIC = 106;
        public static final int CLOSE_PIP = 118;
        public static final int CLOSE_VIDEO = 112;
    }

    /* loaded from: classes2.dex */
    public static final class OtaSubCommand {
        public static final int OTA_DOWNLOAD_COMPLETED = 100;
        public static final int OTA_FILE_ERROR = 102;
        public static final int OTA_READY_TO_UPGRADE = 101;
        public static final int OTA_REBOOT_DEVICE = 108;
        public static final int OTA_START_UPGRADE = 103;
        public static final int OTA_UPDATE_FAIL = 106;
        public static final int OTA_UPDATE_RETRY = 107;
        public static final int OTA_UPDATE_SUCCESS = 105;
        public static final int OTA_UPGRADING = 104;
    }

    /* loaded from: classes2.dex */
    public static final class PIP_Command {
        public static final int WINDOWS_MODE = 100;
    }

    /* loaded from: classes2.dex */
    public static final class SystemCommand {
        public static final int ACCEPT_PHONE = 116;
        public static final int AIRCON_CONTROL = 612;
        public static final int AIR_DATA_REQ = 613;
        public static final int ANDROID_MODE = 602;
        public static final int BACK = 115;
        public static final int BENZ_CONTROL = 801;
        public static final int CALL_BUTTON = 123;
        public static final int CAR_MODE = 601;
        public static final int CHECK_CAN_BOX = 611;
        public static final int CLEAR_MEMORY = 300;
        public static final int DISMISS_INSTALL_APP_DIALOG = 127;
        public static final int DORMANT = 118;
        public static final int FLASH_SPLASH = 124;
        public static final int HANDUP_PHONE = 117;
        public static final int HOME = 114;
        public static final int KSW_MCU_MSG = 699;
        public static final int MCU_REBOOT = 202;
        public static final int MCU_UPDATE = 700;
        public static final int MEDIA_NEXT = 104;
        public static final int MEDIA_PAUSE = 106;
        public static final int MEDIA_PLAY = 105;
        public static final int MEDIA_PLAY_PAUSE = 121;
        public static final int MEDIA_PREVIOUS = 103;
        public static final int MUTE = 100;
        public static final int NEXT_FM = 120;
        public static final int OPEN_AUX = 605;
        public static final int OPEN_BT = 607;
        public static final int OPEN_CVBSDVR = 609;
        public static final int OPEN_DTV = 606;
        public static final int OPEN_FM = 110;
        public static final int OPEN_F_CAM = 610;
        public static final int OPEN_MODE = 604;
        public static final int OPEN_NAVI = 108;
        public static final int OPEN_SETTINGS = 111;
        public static final int OPEN_SPEECH = 109;
        public static final int OUT_MODE = 603;
        public static final int PREV_FM = 119;
        public static final int REBOOT_GOCSDK = 1001;
        public static final int SAVECONFIG_AND_REBOOT = 125;
        public static final int SCREEN_OFF = 113;
        public static final int SCREEN_ON = 112;
        public static final int SHOW_INSTALL_APP_DIALOG = 126;
        public static final int SHUTDOWN = 201;
        public static final int SOURCE_CHANGE = 107;
        public static final int UPDATE_CONFIG = 200;
        public static final int USB_HOST = 122;
        public static final int USING_NAVI = 608;
        public static final int VOLUME_DOWN = 102;
        public static final int VOLUME_UP = 101;
    }

    public int getCommand() {
        return this.command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getSubCommand() {
        return this.subCommand;
    }

    public void setSubCommand(int subCommand) {
        this.subCommand = subCommand;
    }

    public String getJsonArg() {
        return this.jsonArg;
    }

    public void setJsonArg(String jsonArg) {
        this.jsonArg = jsonArg;
    }

    public static WitsCommand getWitsCommandFormJson(String jsonArg) {
        return (WitsCommand) new Gson().fromJson(jsonArg, (Class<Object>) WitsCommand.class);
    }

    public WitsCommand(int command, int subCommand, String jsonArg) {
        this.command = command;
        this.subCommand = subCommand;
        this.jsonArg = jsonArg;
    }

    public WitsCommand(int command, int subCommand, String jsonArg, boolean needResult) {
        this.command = command;
        this.subCommand = subCommand;
        this.jsonArg = jsonArg;
        this.needResult = needResult;
    }

    public WitsCommand(int command, int subCommand) {
        this.command = command;
        this.subCommand = subCommand;
    }

    public static boolean sendCommandGetResult(int command, int subCommand, String arg) {
        try {
            return PowerManagerApp.getManager().sendCommand(new Gson().toJson(new WitsCommand(command, subCommand, arg, true)));
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendCommand(int command, int subCommand, String arg) {
        try {
            PowerManagerApp.getManager().sendCommand(new Gson().toJson(new WitsCommand(command, subCommand, arg)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void sendCommand(int command, int subCommand) {
        try {
            PowerManagerApp.getManager().sendCommand(new Gson().toJson(new WitsCommand(command, subCommand, "")));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void sendMcuCommand(McuStatus.KswMcuMsg mcuMsg) {
        sendCommand(1, 699, new Gson().toJson(mcuMsg));
    }

    public boolean isNeedResult() {
        return this.needResult;
    }

    public void setNeedResult(boolean needResult) {
        this.needResult = needResult;
    }

    public static boolean sendCommandWithBack(int command, int subCommand, String arg) {
        try {
            PowerManagerApp.getManager().sendCommand(new Gson().toJson(new WitsCommand(command, subCommand, arg)));
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
