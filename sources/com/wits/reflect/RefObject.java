package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

public class RefObject<T> {
    private static final String TAG = "RefObject";
    private Field field;

    public RefObject(Class cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public T get(Object object) {
        try {
            return this.field.get(object);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void set(Object object, T objectValue) {
        try {
            this.field.set(object, objectValue);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
