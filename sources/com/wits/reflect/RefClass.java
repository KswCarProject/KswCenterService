package com.wits.reflect;

import android.util.Log;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public final class RefClass {
    private static final HashMap<Class<?>, Constructor<?>> REF_TYPES = new HashMap<>();
    private static final String TAG = "RefClass";

    static {
        try {
            Log.d(TAG, "RefClass init");
            REF_TYPES.put(RefInt.class, RefInt.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefLong.class, RefLong.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefFloat.class, RefFloat.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefDouble.class, RefDouble.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefBoolean.class, RefBoolean.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefObject.class, RefObject.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefMethod.class, RefMethod.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_TYPES.put(RefConstructor.class, RefConstructor.class.getConstructor(new Class[]{Class.class, Field.class}));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static Class<?> load(Class<?> mappingClass, String className) {
        Log.d(TAG, "RefClass load " + className);
        try {
            return load(mappingClass, Class.forName(className, false, mappingClass.getClassLoader()));
        } catch (Exception e) {
            Log.e(TAG, mappingClass.getName() + ".load", e);
            return null;
        }
    }

    public static Class<?> load(Class<?> mappingClass, String className, String targetJar) {
        Log.d(TAG, "RefClass load " + className + " from " + targetJar);
        try {
            return load(mappingClass, Class.forName(className, false, new PathClassLoader(targetJar, mappingClass.getClassLoader())));
        } catch (Exception e) {
            Log.e(TAG, mappingClass.getName() + ".load", e);
            return null;
        }
    }

    public static Class<?> load(Class<?> mappingClass, Class<?> realClass) {
        Constructor constructor;
        for (Field field : mappingClass.getDeclaredFields()) {
            try {
                if (Modifier.isStatic(field.getModifiers()) && (constructor = REF_TYPES.get(field.getType())) != null) {
                    field.setAccessible(true);
                    field.set((Object) null, constructor.newInstance(new Object[]{realClass, field}));
                }
            } catch (Exception e) {
                Log.e(TAG, mappingClass.getName() + ".load", e);
            }
        }
        return realClass;
    }
}
