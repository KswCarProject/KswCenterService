package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class RefConstructor<T> {
    private static final String TAG = "RefConstructor";
    private Constructor<?> ctor;

    public RefConstructor(Class<?> cls, Field field) throws NoSuchMethodException {
        String constructName = "";
        if (field.isAnnotationPresent(MethodName.class)) {
            Class<?>[] types = ((MethodName) field.getAnnotation(MethodName.class)).params();
            constructName = ((MethodName) field.getAnnotation(MethodName.class)).name();
            this.ctor = cls.getDeclaredConstructor(types);
        } else {
            if (field.isAnnotationPresent(MethodSignature.class)) {
                String[] typesNames = ((MethodSignature) field.getAnnotation(MethodSignature.class)).params();
                constructName = ((MethodSignature) field.getAnnotation(MethodSignature.class)).name();
                Class<?>[] classTypes = new Class[typesNames.length];
                Class<?>[] arraySetTypes = new Class[typesNames.length];
                for (int i = 0; i < typesNames.length; i++) {
                    Class<?> type = TypeUtils.getType(typesNames[i], TAG);
                    classTypes[i] = type;
                    if ("java.util.HashSet".equals(typesNames[i])) {
                        Class<?> arraySetType = type;
                        try {
                            arraySetType = Class.forName("java.util.ArraySet");
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, e.getMessage());
                        }
                        if (arraySetType != null) {
                            arraySetTypes[i] = arraySetType;
                        }
                    } else {
                        arraySetTypes[i] = type;
                    }
                }
                this.ctor = cls.getDeclaredConstructor(classTypes);
                if (this.ctor == null) {
                    this.ctor = cls.getDeclaredConstructor(arraySetTypes);
                }
            } else {
                this.ctor = cls.getDeclaredConstructor(new Class[0]);
            }
        }
        if (this.ctor == null) {
            Log.e(TAG, "ctor of " + constructName + " is null");
        }
        if (this.ctor != null && !this.ctor.isAccessible()) {
            this.ctor.setAccessible(true);
        }
    }

    public T newInstance() {
        try {
            return this.ctor.newInstance(new Object[0]);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public T newInstance(Object... params) {
        try {
            return this.ctor.newInstance(params);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }
}
