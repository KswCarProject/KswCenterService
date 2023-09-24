package com.wits.reflect;

import android.util.Log;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/* loaded from: classes5.dex */
public final class RefClass {
    private static final HashMap<Class<?>, Constructor<?>> REF_TYPES = new HashMap<>();
    private static final String TAG = "RefClass";

    static {
        try {
            Log.m72d(TAG, "RefClass init");
            REF_TYPES.put(RefInt.class, RefInt.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefLong.class, RefLong.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefFloat.class, RefFloat.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefDouble.class, RefDouble.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefBoolean.class, RefBoolean.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefObject.class, RefObject.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefMethod.class, RefMethod.class.getConstructor(Class.class, Field.class));
            REF_TYPES.put(RefConstructor.class, RefConstructor.class.getConstructor(Class.class, Field.class));
        } catch (Exception e) {
            Log.m69e(TAG, e.getMessage(), e);
        }
    }

    public static Class<?> load(Class<?> mappingClass, String className) {
        Log.m72d(TAG, "RefClass load " + className);
        try {
            ClassLoader classLoader = mappingClass.getClassLoader();
            return load(mappingClass, Class.forName(className, false, classLoader));
        } catch (Exception e) {
            Log.m69e(TAG, mappingClass.getName() + ".load", e);
            return null;
        }
    }

    public static Class<?> load(Class<?> mappingClass, String className, String targetJar) {
        Log.m72d(TAG, "RefClass load " + className + " from " + targetJar);
        try {
            PathClassLoader classLoader = new PathClassLoader(targetJar, mappingClass.getClassLoader());
            return load(mappingClass, Class.forName(className, false, classLoader));
        } catch (Exception e) {
            Log.m69e(TAG, mappingClass.getName() + ".load", e);
            return null;
        }
    }

    public static Class<?> load(Class<?> mappingClass, Class<?> realClass) {
        Constructor constructor;
        Field[] fields = mappingClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (Modifier.isStatic(field.getModifiers()) && (constructor = REF_TYPES.get(field.getType())) != null) {
                    field.setAccessible(true);
                    field.set(null, constructor.newInstance(realClass, field));
                }
            } catch (Exception e) {
                Log.m69e(TAG, mappingClass.getName() + ".load", e);
            }
        }
        return realClass;
    }
}
