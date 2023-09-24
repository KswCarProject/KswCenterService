package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes5.dex */
public class RefStaticObject<T> {
    private static final String TAG = "RefStaticObject";
    private Field field;

    public RefStaticObject(Class<?> cls, Field field) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field.getName());
        this.field.setAccessible(true);
    }

    public Class<?> type() {
        return this.field.getType();
    }

    public T get() {
        try {
            T object = (T) this.field.get(null);
            return object;
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void set(T object) {
        try {
            this.field.set(null, object);
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
        }
    }
}
