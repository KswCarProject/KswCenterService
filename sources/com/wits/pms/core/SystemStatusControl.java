package com.wits.pms.core;

import android.view.KeyEvent;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.SystemStatus;

public class SystemStatusControl {
    public static SystemStatusControl factory;
    private PowerManagerImpl mPmas;
    private McuStatus mcuStatus;
    private SystemStatus systemStatus;

    private SystemStatusControl() {
        getSystemStatus();
    }

    public SystemStatus getSystemStatus() {
        if (this.systemStatus == null) {
            this.systemStatus = new SystemStatus();
        }
        return this.systemStatus;
    }

    public static SystemStatusControl getDefault() {
        if (factory == null) {
            factory = new SystemStatusControl();
        }
        return factory;
    }

    public static SystemStatus getStatus() {
        return getDefault().getSystemStatus();
    }

    public void checkAcc() {
        this.systemStatus.acc = 1;
    }

    public void updateKeyEvent(KeyEvent event) {
        CenterControlImpl.getImpl().handleKeyEvent(event);
    }

    public void dormant(boolean on) {
        this.systemStatus.dormant = on;
    }

    public void handle() {
        CommandFactory.sendOut(this.systemStatus);
        CommandFactory.sendOut(this.mcuStatus);
    }

    public void handleSystemStatus() {
        CommandFactory.sendOut(this.systemStatus);
    }

    public void handleMcuStatus() {
        CommandFactory.sendOut(this.mcuStatus);
    }

    public void boot(PowerManagerImpl powerManagerApp) {
        this.mPmas = powerManagerApp;
        getDefault();
        checkAcc();
    }

    public void setMcuStatus(McuStatus mcuStatus2) {
        this.mcuStatus = mcuStatus2;
    }

    public McuStatus getMcuStatus() {
        if (this.mcuStatus == null) {
            this.mcuStatus = new McuStatus();
        }
        return this.mcuStatus;
    }

    public PowerManagerImpl getPms() {
        return this.mPmas;
    }
}
