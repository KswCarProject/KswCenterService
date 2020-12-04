package com.wits.pms.bean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Telephony;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;

public class TxzMessage {
    public String action;
    public Bundle bundle;
    public int keyType;

    public TxzMessage(int keyType2, String action2, Bundle bundle2) {
        bundle2 = bundle2 == null ? new Bundle() : bundle2;
        this.action = action2;
        this.keyType = keyType2;
        bundle2.putInt(Telephony.CarrierColumns.KEY_TYPE, keyType2);
        bundle2.putString("action", action2);
        this.bundle = bundle2;
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
        Log.v("TxzMessage", "keyType: " + txzIntent.getIntExtra(Telephony.CarrierColumns.KEY_TYPE, 0) + " - action: " + txzIntent.getStringExtra("action"));
        context.sendBroadcastAsUser(txzIntent, UserHandle.getUserHandleForUid(context.getApplicationInfo().uid));
    }

    public String toString() {
        return "keyType:" + this.keyType + " - action:" + this.action + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.bundle.toString();
    }
}
