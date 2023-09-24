package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes5.dex */
public class RefFloat {
    private static final String TAG = "RefFloat";
    private Field field;

    public RefFloat(Class cls, Field field) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field.getName());
        this.field.setAccessible(true);
    }

    public Float get(Object object) {
        try {
            return Float.valueOf(this.field.getFloat(object));
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
            return Float.valueOf(0.0f);
        }
    }

    public void set(Object object, Float floatValue) {
        try {
            this.field.setFloat(object, floatValue.floatValue());
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
        }
    }
}
