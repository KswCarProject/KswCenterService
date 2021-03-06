package com.wits.pms.interfaces;

import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.MusicStatus;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.statuscontrol.VideoStatus;

public abstract class LogicSystem {
    protected static CenterControlImpl mController;
    /* access modifiers changed from: protected */
    public BtPhoneStatus mBtPhoneStatus = new BtPhoneStatus();
    protected McuStatus mMcuStatus = new McuStatus();
    protected MusicStatus mMusicStatus = new MusicStatus();
    protected SystemStatus mSystemStatus = new SystemStatus();
    protected VideoStatus mVideoStatus = new VideoStatus();

    public abstract void handle();

    public void updateStatus(McuStatus mcuStatus) {
        this.mMcuStatus = mcuStatus;
    }

    public void updateStatus(SystemStatus systemStatus) {
        this.mSystemStatus = systemStatus;
    }

    public void updateStatus(MusicStatus musicStatus) {
        this.mMusicStatus = musicStatus;
    }

    public void updateStatus(VideoStatus videoStatus) {
        this.mVideoStatus = videoStatus;
    }

    public void updateStatus(BtPhoneStatus btPhoneStatus) {
        this.mBtPhoneStatus = btPhoneStatus;
    }

    public static void setUpController(CenterControlImpl control) {
        mController = control;
    }

    public McuStatus getMcuStatus() {
        return this.mMcuStatus;
    }

    public SystemStatus getSystemStatus() {
        return this.mSystemStatus;
    }

    public MusicStatus getMusicStatus() {
        return this.mMusicStatus;
    }

    public VideoStatus getVideoStatus() {
        return this.mVideoStatus;
    }

    public BtPhoneStatus getBtPhoneStatus() {
        return this.mBtPhoneStatus;
    }
}
