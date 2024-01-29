package com.wits.pms.receiver;

import android.content.Context;
import android.content.Intent;
import android.p007os.Build;
import android.p007os.Bundle;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import com.wits.pms.IContentObserver;
import com.wits.pms.bean.TxzMessage;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.utils.Utils;

/* loaded from: classes2.dex */
public class CenterCallBackImpl {
    static final String TAG = "CenterCallBack";
    private static CenterCallBackImpl centerCall;
    private static Context mContext;
    private static String mPreMediaPkgName = null;

    public static void init(Context context) {
        if (context == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        mContext = context.getApplicationContext();
        centerCall = new CenterCallBackImpl();
    }

    public static CenterCallBackImpl getImpl(Context context) {
        if (centerCall == null) {
            init(context);
        }
        return centerCall;
    }

    public CenterCallBackImpl() {
        PowerManagerApp.registerIContentObserver("isPlayingMusic", new IContentObserver.Stub() { // from class: com.wits.pms.receiver.CenterCallBackImpl.1
            @Override // com.wits.pms.IContentObserver
            public void onChange() throws RemoteException {
                boolean bluetoothPlaying = PowerManagerApp.getStatusBoolean("isPlayingMusic");
                if (bluetoothPlaying) {
                    CenterCallBackImpl.this.musicType(3);
                }
            }
        });
        PowerManagerApp.registerIContentObserver("topApp", new IContentObserver.Stub() { // from class: com.wits.pms.receiver.CenterCallBackImpl.2
            @Override // com.wits.pms.IContentObserver
            public void onChange() throws RemoteException {
                String pkgName = PowerManagerApp.getStatusString("topApp");
                Log.m68i(CenterCallBackImpl.TAG, "onChange: topApp = " + pkgName);
                if ("com.txznet.music".equals(pkgName)) {
                    CenterCallBackImpl.this.musicType(1);
                }
                if ("com.wits.ksw.media".equals(pkgName) || "com.wits.ksw.music".equals(pkgName) || "com.wits.ksw.video".equals(pkgName)) {
                    CenterCallBackImpl.this.musicType(2);
                }
                if (pkgName.contains("cn.kuwo.kwmusiccar") || pkgName.contains("com.ximalaya.ting.android.car")) {
                    CenterCallBackImpl.this.appFocus(CenterCallBackImpl.mPreMediaPkgName, pkgName);
                    if (!TextUtils.equals(pkgName, CenterCallBackImpl.mPreMediaPkgName)) {
                        String unused = CenterCallBackImpl.mPreMediaPkgName = pkgName;
                    }
                } else if (pkgName.equals("com.google.android.apps.maps") || pkgName.equals("com.sygic.aura")) {
                    if ((Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700")) && Integer.parseInt(Build.VERSION.RELEASE) > 10) {
                        try {
                            Utils.updateLocationEnabled(CenterCallBackImpl.mContext, false, UserHandle.myUserId(), 1);
                            Thread.sleep(50L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Utils.updateLocationEnabled(CenterCallBackImpl.mContext, true, UserHandle.myUserId(), 1);
                    }
                    Log.m72d(CenterCallBackImpl.TAG, "onChange: top is google map");
                }
            }
        });
        PowerManagerApp.registerIContentObserver("currentMediaPkg", new IContentObserver.Stub() { // from class: com.wits.pms.receiver.CenterCallBackImpl.3
            @Override // com.wits.pms.IContentObserver
            public void onChange() throws RemoteException {
                String pkgName = PowerManagerApp.getStatusString("currentMediaPkg");
                if (pkgName.contains("cn.kuwo.kwmusiccar") || pkgName.contains("com.ximalaya.ting.android.car")) {
                    Log.m68i(CenterCallBackImpl.TAG, "onChange: currentMediaPkg =" + pkgName);
                    CenterCallBackImpl.this.appFocus(CenterCallBackImpl.mPreMediaPkgName, pkgName);
                    if (!TextUtils.equals(pkgName, CenterCallBackImpl.mPreMediaPkgName)) {
                        String unused = CenterCallBackImpl.mPreMediaPkgName = pkgName;
                    }
                }
            }
        });
        PowerManagerApp.registerIContentObserver("play", new IContentObserver.Stub() { // from class: com.wits.pms.receiver.CenterCallBackImpl.4
            @Override // com.wits.pms.IContentObserver
            public void onChange() throws RemoteException {
                if (PowerManagerApp.getStatusBoolean("play")) {
                    CenterCallBackImpl.this.musicType(2);
                }
            }
        });
    }

    public void sendBroadCast(TxzMessage message) {
        Intent txzIntent = new Intent();
        txzIntent.putExtras(message.bundle);
        txzIntent.setAction("com.txznet.adapter.recv");
        Log.m66v(TAG, "keyType: " + txzIntent.getIntExtra(Telephony.CarrierColumns.KEY_TYPE, 0) + " - action: " + txzIntent.getStringExtra("action"));
        mContext.sendBroadcast(txzIntent);
    }

    public void appStarted(String pkgName) {
        if ("com.txznet.music".equals(pkgName)) {
            musicType(1);
        }
        Bundle bundle = new Bundle();
        bundle.putString("setCllback", pkgName);
        TxzMessage txzMessage = new TxzMessage(2000, "app.status", bundle);
        sendBroadCast(txzMessage);
    }

    public void appFocus(String losePkg, String focusPkg) {
        Bundle bundle = new Bundle();
        bundle.putString("lose", losePkg);
        bundle.putString("focus", focusPkg);
        TxzMessage txzMessage = new TxzMessage(2000, "app.media.focus", bundle);
        sendBroadCast(txzMessage);
    }

    public void wifiOpened() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2010, "wifi.open", bundle);
        sendBroadCast(txzMessage);
    }

    public void wifiClosed() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2010, "wifi.close", bundle);
        sendBroadCast(txzMessage);
    }

    public void wifiConnectStatus(int status, String wifiName) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("type", status == 1);
        bundle.putString("name", wifiName);
        TxzMessage txzMessage = new TxzMessage(2010, "wifi.connect", bundle);
        sendBroadCast(txzMessage);
    }

    public void carSystemStatus(String status) {
    }

    public void brightnessChanged(boolean auto, int number) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("auto", auto);
        bundle.putInt("number", number);
        TxzMessage txzMessage = new TxzMessage(2020, "light.status", bundle);
        sendBroadCast(txzMessage);
    }

    public void soundChanged(int value) {
        Bundle bundle = new Bundle();
        bundle.putInt("number", value);
        TxzMessage txzMessage = new TxzMessage(2030, "volume.status", bundle);
        sendBroadCast(txzMessage);
    }

    public void btConnected() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.connect", bundle);
        sendBroadCast(txzMessage);
    }

    public void btDisconnected() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.disconnect", bundle);
        sendBroadCast(txzMessage);
    }

    public void btIdle() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.idle", bundle);
        sendBroadCast(txzMessage);
    }

    public void btIncoming(String name, String number) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("number", number);
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.incoming", bundle);
        sendBroadCast(txzMessage);
    }

    public void btCall(String name, String number) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("number", number);
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.call", bundle);
        sendBroadCast(txzMessage);
    }

    public void btHandUp() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.hangup", bundle);
        sendBroadCast(txzMessage);
    }

    public void btRejected() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.reject", bundle);
        sendBroadCast(txzMessage);
    }

    public void btAccept() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.offhook", bundle);
        sendBroadCast(txzMessage);
    }

    public void btContactSync(String contactJson) {
        Bundle bundle = new Bundle();
        bundle.putString("contact", contactJson);
        TxzMessage txzMessage = new TxzMessage(2040, "bluetooth.contact", bundle);
        sendBroadCast(txzMessage);
    }

    public void musicOpened() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2060, "music.open", bundle);
        sendBroadCast(txzMessage);
    }

    public void musicClosed() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2060, "music.close", bundle);
        sendBroadCast(txzMessage);
    }

    public void musicShowing() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2060, "music.show", bundle);
        sendBroadCast(txzMessage);
    }

    public void musicBackApp() {
        Bundle bundle = new Bundle();
        TxzMessage txzMessage = new TxzMessage(2060, "music.dismiss", bundle);
        sendBroadCast(txzMessage);
    }

    public void musicStatus(int status) {
        String playStatus;
        Bundle bundle = new Bundle();
        switch (status) {
            case 1:
                playStatus = "play";
                break;
            case 2:
                playStatus = "pause";
                break;
            case 3:
                playStatus = "stop";
                break;
            default:
                playStatus = "";
                break;
        }
        bundle.putString("status", playStatus);
        TxzMessage txzMessage = new TxzMessage(2060, "music.status", bundle);
        sendBroadCast(txzMessage);
    }

    public void musicType(int type) {
        String action;
        Bundle bundle = new Bundle();
        switch (type) {
            case 1:
                action = "tongting.music";
                break;
            case 2:
                action = "local.music";
                break;
            case 3:
                action = "blue.music";
                break;
            default:
                action = "";
                break;
        }
        TxzMessage txzMessage = new TxzMessage(2061, action, bundle);
        sendBroadCast(txzMessage);
    }

    public void autoVoiceControl() {
    }

    public void userVoiceOpen(String tts) {
    }

    public void userVoiceClose() {
    }

    public void backTaskVoiceSpeak(String tts) {
    }

    public void uiVoiceSpeak(String tts) {
    }

    public void floatVoiceMode(String mode) {
    }
}
