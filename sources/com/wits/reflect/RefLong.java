package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

public class RefLong {
    private static final String TAG = "RefLong";
    private Field field;

    public RefLong(Class cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public Long get(Object object) {
        try {
            return Long.valueOf(this.field.getLong(object));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return 0L;
        }
    }

    public void set(Object object, Long longValue) {
        try {
            this.field.setLong(object, longValue.longValue());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
