package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

public class RefDouble {
    private static final String TAG = "RefDouble";
    private Field field;

    public RefDouble(Class cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public Double get(Object object) {
        try {
            return Double.valueOf(this.field.getDouble(object));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return Double.valueOf(0.0d);
        }
    }

    public void set(Object object, Double doubleValue) {
        try {
            this.field.setDouble(object, doubleValue.doubleValue());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
