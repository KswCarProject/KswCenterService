package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes5.dex */
public class RefLong {
    private static final String TAG = "RefLong";
    private Field field;

    public RefLong(Class cls, Field field) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field.getName());
        this.field.setAccessible(true);
    }

    public Long get(Object object) {
        try {
            return Long.valueOf(this.field.getLong(object));
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
            return 0L;
        }
    }

    public void set(Object object, Long longValue) {
        try {
            this.field.setLong(object, longValue.longValue());
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
        }
    }
}
