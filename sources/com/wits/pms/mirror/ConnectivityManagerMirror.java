package com.wits.pms.mirror;

import android.net.ConnectivityManager;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

/* loaded from: classes2.dex */
public class ConnectivityManagerMirror {
    public static final String TAG = ConnectivityManagerMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load(ConnectivityManagerMirror.class, "android.net.ConnectivityManager");
    @MethodName(name = "stopTethering", params = {int.class})
    public static RefMethod<Void> stopTethering;
    public ConnectivityManager mConnectivityManager;

    public ConnectivityManagerMirror(ConnectivityManager base) {
        this.mConnectivityManager = base;
    }

    public void stopTethering(int type) {
        stopTethering.call(this.mConnectivityManager, Integer.valueOf(type));
    }
}
