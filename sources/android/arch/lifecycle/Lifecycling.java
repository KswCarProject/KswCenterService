package android.arch.lifecycle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.telecom.Logging.Session;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class Lifecycling {
    private static Map<Class, Constructor<? extends GenericLifecycleObserver>> sCallbackCache = new HashMap();
    private static Constructor<? extends GenericLifecycleObserver> sREFLECTIVE;

    Lifecycling() {
    }

    static {
        try {
            sREFLECTIVE = ReflectiveGenericLifecycleObserver.class.getDeclaredConstructor(new Class[]{Object.class});
        } catch (NoSuchMethodException e) {
        }
    }

    @NonNull
    static GenericLifecycleObserver getCallback(Object object) {
        if (object instanceof GenericLifecycleObserver) {
            return (GenericLifecycleObserver) object;
        }
        try {
            Class<?> klass = object.getClass();
            Constructor<? extends GenericLifecycleObserver> cachedConstructor = sCallbackCache.get(klass);
            if (cachedConstructor != null) {
                return (GenericLifecycleObserver) cachedConstructor.newInstance(new Object[]{object});
            }
            Constructor<? extends GenericLifecycleObserver> cachedConstructor2 = getGeneratedAdapterConstructor(klass);
            if (cachedConstructor2 == null) {
                cachedConstructor2 = sREFLECTIVE;
            } else if (!cachedConstructor2.isAccessible()) {
                cachedConstructor2.setAccessible(true);
            }
            sCallbackCache.put(klass, cachedConstructor2);
            return (GenericLifecycleObserver) cachedConstructor2.newInstance(new Object[]{object});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    @Nullable
    private static Constructor<? extends GenericLifecycleObserver> getGeneratedAdapterConstructor(Class<?> klass) {
        String str;
        String str2;
        Package aPackage = klass.getPackage();
        String fullPackage = aPackage != null ? aPackage.getName() : "";
        String name = klass.getCanonicalName();
        if (name == null) {
            return null;
        }
        if (fullPackage.isEmpty()) {
            str = name;
        } else {
            str = name.substring(fullPackage.length() + 1);
        }
        String adapterName = getAdapterName(str);
        try {
            if (fullPackage.isEmpty()) {
                str2 = adapterName;
            } else {
                str2 = fullPackage + "." + adapterName;
            }
            return Class.forName(str2).getDeclaredConstructor(new Class[]{klass});
        } catch (ClassNotFoundException e) {
            Class<? super Object> superclass = klass.getSuperclass();
            if (superclass != null) {
                return getGeneratedAdapterConstructor(superclass);
            }
            return null;
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    static String getAdapterName(String className) {
        return className.replace(".", Session.SESSION_SEPARATION_CHAR_CHILD) + "_LifecycleAdapter";
    }
}
