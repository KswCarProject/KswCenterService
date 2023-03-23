package com.wits.reflect;

import android.util.Log;

public class TypeUtils {
    public static Class<?> getType(String dataType, String TAG) {
        try {
            return Class.forName(dataType);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }
}
