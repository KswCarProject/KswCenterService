package com.wits.pms.bean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;

public class AutoKitMessage {
    public static final String AUTOBOX_CALL = "vendor.wits.autobox.call";
    public static final String AUTOBOX_CALL_ING = "2";
    public static final String AUTOBOX_CALL_OFF = "0";
    public static final String AUTOBOX_CALL_ON = "1";
    public static final String AUTOBOX_CONNECT = "vendor.wits.autobox.connected";
    public static final String AUTO_BOX_CONTROL_CMD_DATA = "cn.manstep.phonemirrorBox.AUTO_BOX_CONTROL_CMD_DATA";
    public static final String AUTO_BOX_CONTROL_CMD_DATA_STRING = "cn.manstep.phonemirrorBox.AUTO_BOX_CONTROL_CMD_DATA_STRING";
    public static final String AUTO_BOX_CONTROL_CMD_EVT = "cn.manstep.phonemirrorBox.AUTO_BOX_CONTROL_CMD_EVT";
    public static final String AUTO_BOX_MODE_CHANGE_DATA = "cn.manstep.phonemirrorBox.AUTO_BOX_MODE_CHANGE_DATA";
    public static final String AUTO_BOX_RECEIVE_ACTION = "cn.manstep.phonemirrorBox.AUTO_BOX_MODE_CHANGE_EVT";
    public String action;
    public Bundle bundle;
    public Context context;
    public int receiveKey;

    public AutoKitMessage(Context context2, String action2, Bundle bundle2) {
        this.action = action2;
        this.bundle = bundle2;
        this.context = context2;
    }

    public AutoKitMessage(Intent intent) {
        this.action = intent.getAction();
        this.bundle = intent.getExtras();
        if (this.bundle != null) {
            this.receiveKey = this.bundle.getInt(AUTO_BOX_MODE_CHANGE_DATA);
        }
    }

    public static void obtainMsgSendOut(Context context2, int dataInt, String dataString) {
        Bundle bundle2 = new Bundle();
        bundle2.putInt(AUTO_BOX_CONTROL_CMD_DATA, dataInt);
        bundle2.putString(AUTO_BOX_CONTROL_CMD_DATA_STRING, dataString);
        new AutoKitMessage(context2, AUTO_BOX_CONTROL_CMD_EVT, bundle2).sendBroadCast();
    }

    public static void obtainMsgSendOut(Context context2, int dataInt) {
        Bundle bundle2 = new Bundle();
        bundle2.putInt(AUTO_BOX_CONTROL_CMD_DATA, dataInt);
        new AutoKitMessage(context2, AUTO_BOX_CONTROL_CMD_EVT, bundle2).sendBroadCast();
    }

    public void sendBroadCast() {
        Intent intent = new Intent();
        intent.putExtras(this.bundle);
        intent.setAction(this.action);
        Log.v("AutoKitMessage", "action: " + intent.getStringExtra("action"));
        this.context.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(this.context.getApplicationInfo().uid));
    }

    public String toString() {
        return "receiveKey:" + this.receiveKey + " - action:" + this.action + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.bundle.toString();
    }
}
