package com.wits.pms.mirror;

import android.net.wifi.WifiManager;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

/* loaded from: classes2.dex */
public class WifiManagerMirror {
    public static final String TAG = WifiManagerMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load(WifiManagerMirror.class, "android.net.wifi.WifiManager");
    @MethodName(name = "getWifiApState", params = {})
    public static RefMethod<Integer> getWifiApState;
    public WifiManager mWifiManager;

    public WifiManagerMirror(WifiManager base) {
        this.mWifiManager = base;
    }

    public int getWifiApState() {
        return getWifiApState.call(this.mWifiManager, new Object[0]).intValue();
    }
}
