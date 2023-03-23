package com.wits.pms.mirror;

import android.os.Debug;
import com.wits.reflect.MethodName;
import com.wits.reflect.RefClass;
import com.wits.reflect.RefMethod;

public class MemoryInfoMirror {
    public static final String TAG = MemoryInfoMirror.class.getSimpleName();
    public static final Class<?> TYPE = RefClass.load((Class<?>) MemoryInfoMirror.class, "android.os.Debug$MemoryInfo");
    @MethodName(name = "getTotalUss", params = {})
    public static RefMethod<Integer> getTotalUss;
    public Debug.MemoryInfo mMemoryInfo;

    public MemoryInfoMirror(Debug.MemoryInfo base) {
        this.mMemoryInfo = base;
    }

    public int getTotalUss() {
        return getTotalUss.call(this.mMemoryInfo, new Object[0]).intValue();
    }
}
