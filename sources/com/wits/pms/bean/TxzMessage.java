package com.wits.pms.bean;

import android.content.Context;
import android.content.Intent;
import android.p007os.Bundle;
import android.p007os.UserHandle;
import android.provider.Telephony;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes2.dex */
public class TxzMessage {
    public static final String TXZ_DISMISS = "0";
    public static final String TXZ_SHOW = "1";
    public static final String TXZ_SHOW_STATUS = "vendor.wits.txz.status";
    public String action;
    public Bundle bundle;
    public int keyType;

    public TxzMessage(int keyType, String action, Bundle bundle) {
        bundle = bundle == null ? new Bundle() : bundle;
        this.action = action;
        this.keyType = keyType;
        bundle.putInt(Telephony.CarrierColumns.KEY_TYPE, keyType);
        bundle.putString("action", action);
        this.bundle = bundle;
    }

    public TxzMessage(Intent intent) {
        this.keyType = intent.getIntExtra(Telephony.CarrierColumns.KEY_TYPE, 0);
        this.action = intent.getStringExtra("action");
        this.bundle = intent.getExtras();
    }

    public void sendBroadCast(Context context) {
        Intent txzIntent = new Intent();
        txzIntent.putExtras(this.bundle);
        txzIntent.setAction("com.txznet.adapter.recv");
        Log.m66v("TxzMessage", "keyType: " + txzIntent.getIntExtra(Telephony.CarrierColumns.KEY_TYPE, 0) + " - action: " + txzIntent.getStringExtra("action"));
        context.sendBroadcastAsUser(txzIntent, UserHandle.getUserHandleForUid(context.getApplicationInfo().uid));
    }

    public String toString() {
        return "keyType:" + this.keyType + " - action:" + this.action + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.bundle.toString();
    }
}
