package com.wits.reflect;

import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class RefMethod<T> {
    private static final String TAG = "RefMethod";
    private Method method;

    public RefMethod(Class<?> cls, Field field) throws NoSuchMethodException {
        this.method = load(cls, field);
    }

    private Method load(Class<?> cls, Field field) {
        Method result = null;
        try {
            if (!field.isAnnotationPresent(MethodName.class)) {
                int i = 0;
                if (!field.isAnnotationPresent(MethodSignature.class)) {
                    Method[] declaredMethods = cls.getDeclaredMethods();
                    int length = declaredMethods.length;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Method method2 = declaredMethods[i];
                        if (method2.getName().equals(field.getName())) {
                            result = method2;
                            break;
                        }
                        i++;
                    }
                } else {
                    boolean arrayset = false;
                    MethodSignature annotation = (MethodSignature) Objects.requireNonNull((MethodSignature) field.getAnnotation(MethodSignature.class));
                    String[] typesNames = annotation.params();
                    Class<?>[] classTypes = new Class[typesNames.length];
                    Class<?>[] arraySetTypes = new Class[typesNames.length];
                    while (i < typesNames.length) {
                        Class<?> type = TypeUtils.getType(typesNames[i], TAG);
                        classTypes[i] = type;
                        if ("java.util.HashSet".equals(typesNames[i])) {
                            arrayset = true;
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
                        i++;
                    }
                    try {
                        result = getMethod(cls, field, classTypes, annotation.name());
                    } catch (Exception e2) {
                        Log.e(TAG, e2.getMessage());
                        if (arrayset) {
                            result = getMethod(cls, field, arraySetTypes, annotation.name());
                        }
                    }
                }
            } else {
                MethodName annotation2 = (MethodName) Objects.requireNonNull((MethodName) field.getAnnotation(MethodName.class));
                result = getMethod(cls, field, annotation2.params(), annotation2.name());
                result.setAccessible(true);
            }
            if (result != null) {
                result.setAccessible(true);
            } else {
                Log.e(TAG, "can not find method");
            }
        } catch (Exception e3) {
            Log.e(TAG, e3.getMessage());
        }
        return result;
    }

    public Method getMethod(Class<?> cls, Field field, Class<?>[] types, String methodName) throws NoSuchMethodException {
        if (!methodName.isEmpty()) {
            return cls.getDeclaredMethod(methodName, types);
        }
        return cls.getDeclaredMethod(field.getName(), types);
    }

    public T call(Object receiver, Object... args) {
        return callWithDefault(receiver, (Object) null, args);
    }

    public T callWithDefault(Object receiver, T defaultValue, Object... args) {
        try {
            return callWithException(receiver, args);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage(), e);
            if (defaultValue != null) {
                return defaultValue;
            }
            return null;
        }
    }

    public T callWithException(Object receiver, Object... args) throws Throwable {
        try {
            return this.method.invoke(receiver, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                throw cause;
            }
            throw e;
        }
    }
}
