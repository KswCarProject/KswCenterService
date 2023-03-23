package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

public class RefStaticObject<T> {
    private static final String TAG = "RefStaticObject";
    private Field field;

    public RefStaticObject(Class<?> cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public Class<?> type() {
        return this.field.getType();
    }

    public T get() {
        try {
            return this.field.get((Object) null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void set(T object) {
        try {
            this.field.set((Object) null, object);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
