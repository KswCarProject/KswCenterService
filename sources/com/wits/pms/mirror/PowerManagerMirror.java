package com.wits.pms.mirror;

import android.p007os.PowerManager;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

/* loaded from: classes2.dex */
public class PowerManagerMirror {
    public static final String TAG = PowerManagerMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load(PowerManagerMirror.class, "android.os.PowerManager");
    @MethodName(name = "shutdown", params = {boolean.class, String.class, boolean.class})
    public static RefMethod<Void> shutdown;
    public PowerManager mPowerManager;

    public PowerManagerMirror(PowerManager base) {
        this.mPowerManager = base;
    }

    public void shutdown(boolean confirm, String reason, boolean wait) {
        shutdown.call(this.mPowerManager, Boolean.valueOf(confirm), reason, Boolean.valueOf(wait));
    }
}
