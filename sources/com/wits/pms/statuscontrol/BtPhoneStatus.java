package com.wits.pms.statuscontrol;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class BtPhoneStatus {
    public static final int CALL_CALLOUT = 4;
    public static final int CALL_HANDUP = 7;
    public static final int CALL_INCOMING = 5;
    public static final int CALL_TALKING = 6;
    public static final int TYPE_BT_STATUS = 3;
    public static final int VOICE_CAR_SYSTEM_VOICE = 0;
    public static final int VOICE_CUSTOM_PHONE_VOICE = 1;
    public boolean btSwitch;
    public int callStatus;
    public String connectedAddr;
    public String devAddr;
    public boolean isConnected;
    public boolean isPlayingMusic;
    public int voiceStatus;

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    public void setBtSwitch(boolean btSwitch2) {
        this.btSwitch = btSwitch2;
    }

    public boolean isBtSwitch() {
        return this.btSwitch;
    }

    public boolean isPlayingMusic() {
        return this.isPlayingMusic;
    }

    public void setPlayingMusic(boolean playingMusic) {
        this.isPlayingMusic = playingMusic;
    }

    public String getDevAddr() {
        return this.devAddr;
    }

    public void setDevAddr(String devAddr2) {
        this.devAddr = devAddr2;
    }

    public int getCallStatus() {
        return this.callStatus;
    }

    public void setCallStatus(int callStatus2) {
        this.callStatus = callStatus2;
    }

    public int getVoiceStatus() {
        return this.voiceStatus;
    }

    public void setVoiceStatus(int voiceStatus2) {
        this.voiceStatus = voiceStatus2;
    }

    public String getConnectedAddr() {
        return this.connectedAddr;
    }

    public void setConnectedAddr(String connectedAddr2) {
        this.connectedAddr = connectedAddr2;
    }

    public BtPhoneStatus(boolean isConnected2, boolean isPlayingMusic2, String devAddr2, int callStatus2, int voiceStatus2, boolean btSwitch2) {
        this.isConnected = isConnected2;
        this.isPlayingMusic = isPlayingMusic2;
        this.devAddr = devAddr2;
        this.callStatus = callStatus2;
        this.voiceStatus = voiceStatus2;
        this.btSwitch = btSwitch2;
        this.connectedAddr = "";
    }

    public BtPhoneStatus() {
    }

    public static BtPhoneStatus getStatusForJson(String jsonArg) {
        return (BtPhoneStatus) new Gson().fromJson(jsonArg, BtPhoneStatus.class);
    }

    public List<String> compare(BtPhoneStatus btPhoneStatus) {
        List<String> keys = new ArrayList<>();
        if (this.btSwitch != btPhoneStatus.btSwitch) {
            keys.add("btSwitch");
        }
        if (this.isConnected != btPhoneStatus.isConnected) {
            keys.add("isConnected");
        }
        if (this.isPlayingMusic != btPhoneStatus.isPlayingMusic) {
            keys.add("isPlayingMusic");
        }
        if (this.callStatus != btPhoneStatus.callStatus) {
            keys.add("callStatus");
        }
        if (this.devAddr != null && !this.devAddr.equals(btPhoneStatus.devAddr)) {
            keys.add("devAddr");
        }
        if (this.voiceStatus != btPhoneStatus.voiceStatus) {
            keys.add("voiceStatus");
        }
        if (this.connectedAddr != null && !this.connectedAddr.equals(btPhoneStatus.connectedAddr)) {
            keys.add("connectedAddr");
        }
        return keys;
    }

    public static boolean isCalling(int callStatus2) {
        return callStatus2 >= 4 && callStatus2 <= 6;
    }
}
