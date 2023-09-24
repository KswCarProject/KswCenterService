package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes5.dex */
public class RefStaticInt {
    private static final String TAG = "RefStaticInt";
    private Field field;

    public RefStaticInt(Class<?> cls, Field field) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field.getName());
        this.field.setAccessible(true);
    }

    public Class<?> type() {
        return this.field.getType();
    }

    public int get() {
        try {
            int value = this.field.getInt(null);
            return value;
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
            return 0;
        }
    }

    public void set(int value) {
        try {
            this.field.set(null, Integer.valueOf(value));
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
        }
    }
}
