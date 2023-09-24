package com.wits.pms.bean;

import android.content.Context;
import android.content.Intent;
import android.p007os.Bundle;
import android.p007os.UserHandle;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes2.dex */
public class ZlinkMessage {
    public static final String DEVICE_ZLINK_MODE_SPEC = "com.zjinnova.zlink.mode.spec";
    public static final String DEVICE_ZLINK_MODE_SUPPORT = "com.zjinnova.zlink.mode.support";
    public static final String DISABLE_ANDROID_AUTO = "persist.sys.zlink.auto.disa";
    public static final String DISABLE_CARPLAY = "persist.sys.zlink.cp.disa";
    public static final String DISABLE_FOREGROUND_AUDIOFOCUS = "rw.zlink.foreground.donotreqaf";
    public static final String DISABLE_ZLINK_BACKGROUNG_CONNECT = "persist.sys.zlink.bgconn.disa";
    public static final String ZLINK_AIRPLAY_CONNECT = "vendor.wits.airplay.connected";
    public static final String ZLINK_AIRPLAY_WIRED_CONNECT = "vendor.wits.airplay.wired.connected";
    public static final String ZLINK_ANDROID_AUTO_CONNECT = "vendor.wits.androidAuto.connected";
    public static final String ZLINK_ANDROID_MIRROR_CONNECT = "vendor.wits.androidMirror.connected";
    public static final String ZLINK_APPLE_MAPS_VOL = "persist.sys.zlink.au.alt.v";
    public static final String ZLINK_AUDIO_VOL = "persist.sys.zlink.au.main.v";
    public static final String ZLINK_BACKCAR_START_ACTION = "com.zjinnova.zlink.action.BACKCAR_START";
    public static final String ZLINK_BACKCAR_STOP_ACTION = "com.zjinnova.zlink.action.BACKCAR_STOP";
    public static final String ZLINK_BLUETOOTH_ENTER_CARPLAY = "persist.sys.zlink.wcp.bgc.disa";
    public static final String ZLINK_CALL = "vendor.wits.zlink.call";
    public static final String ZLINK_CALL_ING = "2";
    public static final String ZLINK_CALL_OFF = "0";
    public static final String ZLINK_CALL_ON = "1";
    public static final String ZLINK_CAN_START = "persist.sys.zlink.canstart";
    public static final String ZLINK_CARPLAY_WRIED_CONNECT = "vendor.wits.carplayWried.connected";
    public static final String ZLINK_CONNECT = "vendor.wits.zlink.connected";
    public static final String ZLINK_ENTER_CARPLAY = "persist.sys.zlink.autod.disa";
    public static final String ZLINK_MIC_VOL = "persist.sys.zlink..mic.vol";
    public static final String ZLINK_NORMAL_ACTION = "com.zjinnova.zlink";
    public static final String ZLINK_PATH_KEYCFG = "persist.sys.zlink.path.keycfg";
    public static final String ZLINK_POWER_OFF_ACTION = "com.zjinnova.zlink.action.POWER_OFF";
    public static final String ZLINK_POWER_ON_ACTION = "com.zjinnova.zlink.action.POWER_ON";
    public String action;
    public Bundle bundle;
    public String command;
    public String status;

    public ZlinkMessage(String action) {
        this.action = action;
    }

    public ZlinkMessage(String action, String command, Bundle bundle) {
        bundle = bundle == null ? new Bundle() : bundle;
        this.action = action;
        this.command = command;
        bundle.putString("command", command);
        this.bundle = bundle;
    }

    public ZlinkMessage(Intent intent) {
        this.status = intent.getStringExtra("status");
        this.command = intent.getStringExtra("command");
        this.bundle = intent.getExtras();
    }

    public void sendBroadCast(Context context) {
        Intent txzIntent = new Intent();
        txzIntent.setAction(this.action);
        if (this.bundle != null) {
            txzIntent.putExtras(this.bundle);
        }
        txzIntent.addFlags(16777216);
        txzIntent.setPackage(ZLINK_NORMAL_ACTION);
        Log.m66v("ZlinkMessage", "action: " + txzIntent.getAction() + " command = " + txzIntent.getStringExtra("command"));
        context.sendBroadcastAsUser(txzIntent, UserHandle.getUserHandleForUid(context.getApplicationInfo().uid));
    }

    public String toString() {
        return "status:" + this.status + " - command:" + this.command + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.bundle.toString();
    }
}
