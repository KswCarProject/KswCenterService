package android.filterfw.core;

import android.util.Log;
import java.util.HashSet;
import java.util.Iterator;

public class FilterFactory {
    private static final String TAG = "FilterFactory";
    private static Object mClassLoaderGuard = new Object();
    private static ClassLoader mCurrentClassLoader = Thread.currentThread().getContextClassLoader();
    private static HashSet<String> mLibraries = new HashSet<>();
    private static boolean mLogVerbose = Log.isLoggable(TAG, 2);
    private static FilterFactory mSharedFactory;
    private HashSet<String> mPackages = new HashSet<>();

    public static FilterFactory sharedFactory() {
        if (mSharedFactory == null) {
            mSharedFactory = new FilterFactory();
        }
        return mSharedFactory;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void addFilterLibrary(java.lang.String r3) {
        /*
            boolean r0 = mLogVerbose
            if (r0 == 0) goto L_0x001a
            java.lang.String r0 = "FilterFactory"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Adding filter library "
            r1.append(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x001a:
            java.lang.Object r0 = mClassLoaderGuard
            monitor-enter(r0)
            java.util.HashSet<java.lang.String> r1 = mLibraries     // Catch:{ all -> 0x0042 }
            boolean r1 = r1.contains(r3)     // Catch:{ all -> 0x0042 }
            if (r1 == 0) goto L_0x0032
            boolean r1 = mLogVerbose     // Catch:{ all -> 0x0042 }
            if (r1 == 0) goto L_0x0030
            java.lang.String r1 = "FilterFactory"
            java.lang.String r2 = "Library already added"
            android.util.Log.v(r1, r2)     // Catch:{ all -> 0x0042 }
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            return
        L_0x0032:
            java.util.HashSet<java.lang.String> r1 = mLibraries     // Catch:{ all -> 0x0042 }
            r1.add(r3)     // Catch:{ all -> 0x0042 }
            dalvik.system.PathClassLoader r1 = new dalvik.system.PathClassLoader     // Catch:{ all -> 0x0042 }
            java.lang.ClassLoader r2 = mCurrentClassLoader     // Catch:{ all -> 0x0042 }
            r1.<init>(r3, r2)     // Catch:{ all -> 0x0042 }
            mCurrentClassLoader = r1     // Catch:{ all -> 0x0042 }
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            return
        L_0x0042:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.filterfw.core.FilterFactory.addFilterLibrary(java.lang.String):void");
    }

    public void addPackage(String packageName) {
        if (mLogVerbose) {
            Log.v(TAG, "Adding package " + packageName);
        }
        this.mPackages.add(packageName);
    }

    public Filter createFilterByClassName(String className, String filterName) {
        if (mLogVerbose) {
            Log.v(TAG, "Looking up class " + className);
        }
        Class filterClass = null;
        Iterator<String> it = this.mPackages.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String packageName = it.next();
            try {
                if (mLogVerbose) {
                    Log.v(TAG, "Trying " + packageName + "." + className);
                }
                synchronized (mClassLoaderGuard) {
                    ClassLoader classLoader = mCurrentClassLoader;
                    filterClass = classLoader.loadClass(packageName + "." + className);
                }
                if (filterClass != null) {
                    break;
                }
            } catch (ClassNotFoundException e) {
            }
        }
        if (filterClass != null) {
            return createFilterByClass(filterClass, filterName);
        }
        throw new IllegalArgumentException("Unknown filter class '" + className + "'!");
    }

    public Filter createFilterByClass(Class filterClass, String filterName) {
        try {
            filterClass.asSubclass(Filter.class);
            Filter filter = null;
            try {
                try {
                    filter = (Filter) filterClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{filterName});
                } catch (Throwable th) {
                }
                if (filter != null) {
                    return filter;
                }
                throw new IllegalArgumentException("Could not construct the filter '" + filterName + "'!");
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("The filter class '" + filterClass + "' does not have a constructor of the form <init>(String name)!");
            }
        } catch (ClassCastException e2) {
            throw new IllegalArgumentException("Attempting to allocate class '" + filterClass + "' which is not a subclass of Filter!");
        }
    }
}
