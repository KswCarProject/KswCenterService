package com.wits.pms.receiver;

import android.content.Context;
import android.os.RemoteException;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.statuscontrol.PowerManagerApp;

public class AutoKitCallBackImpl {
    public static final int AUTO_BOX_CONTROL_CMD_NEXT = 13;
    public static final int AUTO_BOX_CONTROL_CMD_PAUSE = 11;
    public static final int AUTO_BOX_CONTROL_CMD_PLAY = 10;
    public static final int AUTO_BOX_CONTROL_CMD_PLAYORPAUSE = 12;
    public static final int AUTO_BOX_CONTROL_CMD_PREV = 14;
    public static final int AUTO_BOX_MODE_ANDROIDAUTO_WORK_START = 1;
    public static final int AUTO_BOX_MODE_ANDROID_MIRROR_START = 15;
    public static final int AUTO_BOX_MODE_ANDROID_MIRROR_STOP = 16;
    public static final int AUTO_BOX_MODE_BACKCAR = 10;
    public static final int AUTO_BOX_MODE_BACKGROUND = 9;
    public static final int AUTO_BOX_MODE_CARPLAY_WORK_START = 5;
    public static final int AUTO_BOX_MODE_CONNECTING_PHONE = 11;
    public static final int AUTO_BOX_MODE_FOREGROUND = 8;
    public static final int AUTO_BOX_MODE_IPHONE_MIRROR_START = 17;
    public static final int AUTO_BOX_MODE_IPHONE_MIRROR_STOP = 18;
    public static final int AUTO_BOX_MODE_MUSIC_START = 19;
    public static final int AUTO_BOX_MODE_MUSIC_STOP = 20;
    public static final int AUTO_BOX_MODE_NAVI_REPORT_START = 6;
    public static final int AUTO_BOX_MODE_NAVI_REPORT_STOP = 7;
    public static final int AUTO_BOX_MODE_PHONECALL_START = 3;
    public static final int AUTO_BOX_MODE_PHONECALL_STOP = 4;
    public static final int AUTO_BOX_MODE_RELEASE_MIC = 13;
    public static final int AUTO_BOX_MODE_REQUEST_BLUETOOTH_MAC = 14;
    public static final int AUTO_BOX_MODE_REQUEST_BLUETOOTH_PIN_CODE = 21;
    public static final int AUTO_BOX_MODE_REQUEST_MIC = 12;
    public static final int AUTO_BOX_MODE_WORK_STOP = 2;
    public static final String AutoKitPkgName = "cn.manstep.phonemirrorBox";
    static final String TAG = "CenterCallBack";
    private static AutoKitCallBackImpl autoKitCallBack;
    private static Context mContext;

    public static void init(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
            autoKitCallBack = new AutoKitCallBackImpl();
            return;
        }
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AutoKitCallBackImpl getImpl(Context context) {
        if (autoKitCallBack == null) {
            init(context);
        }
        return autoKitCallBack;
    }

    public boolean isUsing() {
        try {
            return PowerManagerApp.getStatusString("topApp").equals(AutoKitPkgName);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void drapUp() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 5);
        }
    }

    public void drapDown() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 6);
        }
    }

    public void drapLeft() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 3);
        }
    }

    public void drapRight() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 4);
        }
    }

    public void enter() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 7);
            AutoKitMessage.obtainMsgSendOut(mContext, 8);
        }
    }

    public void acceptPhone() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 15);
        }
    }

    public void rejectPhone() {
        if (isUsing()) {
            AutoKitMessage.obtainMsgSendOut(mContext, 16);
        }
    }

    public void musicPause() {
        AutoKitMessage.obtainMsgSendOut(mContext, 11);
    }

    public void musicPlay() {
        AutoKitMessage.obtainMsgSendOut(mContext, 10);
    }
}
