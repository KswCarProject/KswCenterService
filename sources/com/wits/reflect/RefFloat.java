package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

public class RefFloat {
    private static final String TAG = "RefFloat";
    private Field field;

    public RefFloat(Class cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public Float get(Object object) {
        try {
            return Float.valueOf(this.field.getFloat(object));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return Float.valueOf(0.0f);
        }
    }

    public void set(Object object, Float floatValue) {
        try {
            this.field.setFloat(object, floatValue.floatValue());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
