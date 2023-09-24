package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes5.dex */
public class RefBoolean {
    private static final String TAG = "RefBoolean";
    private Field field;

    public RefBoolean(Class cls, Field field) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field.getName());
        this.field.setAccessible(true);
    }

    public Boolean get(Object object) {
        try {
            return Boolean.valueOf(this.field.getBoolean(object));
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public void set(Object object, Boolean booleanValue) {
        try {
            this.field.setBoolean(object, booleanValue.booleanValue());
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
        }
    }
}
