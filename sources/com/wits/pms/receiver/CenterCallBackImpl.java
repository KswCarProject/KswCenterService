package com.wits.pms.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import com.wits.pms.IContentObserver;
import com.wits.pms.bean.TxzMessage;
import com.wits.pms.statuscontrol.PowerManagerApp;

public class CenterCallBackImpl {
    static final String TAG = "CenterCallBack";
    private static CenterCallBackImpl centerCall;
    private static Context mContext;
    /* access modifiers changed from: private */
    public static String mPreMediaPkgName = null;

    public static void init(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
            centerCall = new CenterCallBackImpl();
            return;
        }
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CenterCallBackImpl getImpl(Context context) {
        if (centerCall == null) {
            init(context);
        }
        return centerCall;
    }

    public CenterCallBackImpl() {
        PowerManagerApp.registerIContentObserver("isPlayingMusic", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                if (PowerManagerApp.getStatusBoolean("isPlayingMusic")) {
                    CenterCallBackImpl.this.musicType(3);
                }
            }
        });
        PowerManagerApp.registerIContentObserver("topApp", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                String pkgName = PowerManagerApp.getStatusString("topApp");
                Log.i(CenterCallBackImpl.TAG, "onChange: topApp = " + pkgName);
                if ("com.txznet.music".equals(pkgName)) {
                    CenterCallBackImpl.this.musicType(1);
                }
                if (pkgName.contains("cn.kuwo.kwmusiccar") || pkgName.contains("com.ximalaya.ting.android.car")) {
                    CenterCallBackImpl.this.appFocus(CenterCallBackImpl.mPreMediaPkgName, pkgName);
                    if (!TextUtils.equals(pkgName, CenterCallBackImpl.mPreMediaPkgName)) {
                        String unused = CenterCallBackImpl.mPreMediaPkgName = pkgName;
                    }
                }
            }
        });
        PowerManagerApp.registerIContentObserver("currentMediaPkg", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                String pkgName = PowerManagerApp.getStatusString("currentMediaPkg");
                if (pkgName.contains("cn.kuwo.kwmusiccar") || pkgName.contains("com.ximalaya.ting.android.car")) {
                    Log.i(CenterCallBackImpl.TAG, "onChange: currentMediaPkg =" + pkgName);
                    CenterCallBackImpl.this.appFocus(CenterCallBackImpl.mPreMediaPkgName, pkgName);
                    if (!TextUtils.equals(pkgName, CenterCallBackImpl.mPreMediaPkgName)) {
                        String unused = CenterCallBackImpl.mPreMediaPkgName = pkgName;
                    }
                }
            }
        });
        PowerManagerApp.registerIContentObserver("play", new IContentObserver.Stub() {
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
        Log.v(TAG, "keyType: " + txzIntent.getIntExtra(Telephony.CarrierColumns.KEY_TYPE, 0) + " - action: " + txzIntent.getStringExtra("action"));
        mContext.sendBroadcast(txzIntent);
    }

    public void appStarted(String pkgName) {
        if ("com.txznet.music".equals(pkgName)) {
            musicType(1);
        }
        Bundle bundle = new Bundle();
        bundle.putString("setCllback", pkgName);
        sendBroadCast(new TxzMessage(2000, "app.status", bundle));
    }

    public void appFocus(String losePkg, String focusPkg) {
        Bundle bundle = new Bundle();
        bundle.putString("lose", losePkg);
        bundle.putString("focus", focusPkg);
        sendBroadCast(new TxzMessage(2000, "app.media.focus", bundle));
    }

    public void wifiOpened() {
        sendBroadCast(new TxzMessage(2010, "wifi.open", new Bundle()));
    }

    public void wifiClosed() {
        sendBroadCast(new TxzMessage(2010, "wifi.close", new Bundle()));
    }

    public void wifiConnectStatus(int status, String wifiName) {
        Bundle bundle = new Bundle();
        boolean z = true;
        if (status != 1) {
            z = false;
        }
        bundle.putBoolean("type", z);
        bundle.putString("name", wifiName);
        sendBroadCast(new TxzMessage(2010, "wifi.connect", bundle));
    }

    public void carSystemStatus(String status) {
    }

    public void brightnessChanged(boolean auto, int number) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("auto", auto);
        bundle.putInt("number", number);
        sendBroadCast(new TxzMessage(2020, "light.status", bundle));
    }

    public void soundChanged(int value) {
        Bundle bundle = new Bundle();
        bundle.putInt("number", value);
        sendBroadCast(new TxzMessage(2030, "volume.status", bundle));
    }

    public void btConnected() {
        sendBroadCast(new TxzMessage(2040, "bluetooth.connect", new Bundle()));
    }

    public void btDisconnected() {
        sendBroadCast(new TxzMessage(2040, "bluetooth.disconnect", new Bundle()));
    }

    public void btIdle() {
        sendBroadCast(new TxzMessage(2040, "bluetooth.idle", new Bundle()));
    }

    public void btIncoming(String name, String number) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("number", number);
        sendBroadCast(new TxzMessage(2040, "bluetooth.incoming", bundle));
    }

    public void btCall(String name, String number) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("number", number);
        sendBroadCast(new TxzMessage(2040, "bluetooth.call", bundle));
    }

    public void btHandUp() {
        sendBroadCast(new TxzMessage(2040, "bluetooth.hangup", new Bundle()));
    }

    public void btRejected() {
        sendBroadCast(new TxzMessage(2040, "bluetooth.reject", new Bundle()));
    }

    public void btAccept() {
        sendBroadCast(new TxzMessage(2040, "bluetooth.offhook", new Bundle()));
    }

    public void btContactSync(String contactJson) {
        Bundle bundle = new Bundle();
        bundle.putString("contact", contactJson);
        sendBroadCast(new TxzMessage(2040, "bluetooth.contact", bundle));
    }

    public void musicOpened() {
        sendBroadCast(new TxzMessage(2060, "music.open", new Bundle()));
    }

    public void musicClosed() {
        sendBroadCast(new TxzMessage(2060, "music.close", new Bundle()));
    }

    public void musicShowing() {
        sendBroadCast(new TxzMessage(2060, "music.show", new Bundle()));
    }

    public void musicBackApp() {
        sendBroadCast(new TxzMessage(2060, "music.dismiss", new Bundle()));
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
        sendBroadCast(new TxzMessage(2060, "music.status", bundle));
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
        sendBroadCast(new TxzMessage(2061, action, bundle));
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
