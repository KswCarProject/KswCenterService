package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

public class RefStaticInt {
    private static final String TAG = "RefStaticInt";
    private Field field;

    public RefStaticInt(Class<?> cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public Class<?> type() {
        return this.field.getType();
    }

    public int get() {
        try {
            return this.field.getInt((Object) null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return 0;
        }
    }

    public void set(int value) {
        try {
            this.field.set((Object) null, Integer.valueOf(value));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
