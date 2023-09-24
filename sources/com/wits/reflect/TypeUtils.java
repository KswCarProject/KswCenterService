package com.wits.reflect;

import android.util.Log;

/* loaded from: classes5.dex */
public class TypeUtils {
    public static Class<?> getType(String dataType, String TAG) {
        try {
            Class<?> parameterTypes = Class.forName(dataType);
            return parameterTypes;
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
            return null;
        }
    }
}
