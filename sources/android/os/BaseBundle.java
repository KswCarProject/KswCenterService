package android.os;

import android.annotation.UnsupportedAppUsage;
import android.util.ArrayMap;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IndentingPrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class BaseBundle {
    private static final int BUNDLE_MAGIC = 1279544898;
    private static final int BUNDLE_MAGIC_NATIVE = 1279544900;
    static final boolean DEBUG = false;
    static final int FLAG_DEFUSABLE = 1;
    private static final boolean LOG_DEFUSABLE = false;
    private static final String TAG = "Bundle";
    private static volatile boolean sShouldDefuse = false;
    private ClassLoader mClassLoader;
    @VisibleForTesting
    public int mFlags;
    @UnsupportedAppUsage
    ArrayMap<String, Object> mMap;
    private boolean mParcelledByNative;
    @UnsupportedAppUsage
    Parcel mParcelledData;

    public static void setShouldDefuse(boolean shouldDefuse) {
        sShouldDefuse = shouldDefuse;
    }

    static final class NoImagePreloadHolder {
        public static final Parcel EMPTY_PARCEL = Parcel.obtain();

        NoImagePreloadHolder() {
        }
    }

    BaseBundle(ClassLoader loader, int capacity) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mMap = capacity > 0 ? new ArrayMap<>(capacity) : new ArrayMap<>();
        this.mClassLoader = loader == null ? getClass().getClassLoader() : loader;
    }

    BaseBundle() {
        this((ClassLoader) null, 0);
    }

    BaseBundle(Parcel parcelledData) {
        this.mMap = null;
        this.mParcelledData = null;
        readFromParcelInner(parcelledData);
    }

    BaseBundle(Parcel parcelledData, int length) {
        this.mMap = null;
        this.mParcelledData = null;
        readFromParcelInner(parcelledData, length);
    }

    BaseBundle(ClassLoader loader) {
        this(loader, 0);
    }

    BaseBundle(int capacity) {
        this((ClassLoader) null, capacity);
    }

    BaseBundle(BaseBundle b) {
        this.mMap = null;
        this.mParcelledData = null;
        copyInternal(b, false);
    }

    BaseBundle(boolean doInit) {
        this.mMap = null;
        this.mParcelledData = null;
    }

    public String getPairValue() {
        unparcel();
        int size = this.mMap.size();
        if (size > 1) {
            Log.w(TAG, "getPairValue() used on Bundle with multiple pairs.");
        }
        if (size == 0) {
            return null;
        }
        Object o = this.mMap.valueAt(0);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            typeWarning("getPairValue()", o, "String", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setClassLoader(ClassLoader loader) {
        this.mClassLoader = loader;
    }

    /* access modifiers changed from: package-private */
    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void unparcel() {
        synchronized (this) {
            Parcel source = this.mParcelledData;
            if (source != null) {
                initializeFromParcelLocked(source, true, this.mParcelledByNative);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004a, code lost:
        if (r9 != false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004c, code lost:
        recycleParcel(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004f, code lost:
        r7.mParcelledData = null;
        r7.mParcelledByNative = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0065, code lost:
        if (r9 != false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0068, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initializeFromParcelLocked(android.os.Parcel r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            boolean r0 = isEmptyParcel(r8)
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x001f
            android.util.ArrayMap<java.lang.String, java.lang.Object> r0 = r7.mMap
            if (r0 != 0) goto L_0x0015
            android.util.ArrayMap r0 = new android.util.ArrayMap
            r3 = 1
            r0.<init>((int) r3)
            r7.mMap = r0
            goto L_0x001a
        L_0x0015:
            android.util.ArrayMap<java.lang.String, java.lang.Object> r0 = r7.mMap
            r0.erase()
        L_0x001a:
            r7.mParcelledData = r2
            r7.mParcelledByNative = r1
            return
        L_0x001f:
            int r0 = r8.readInt()
            if (r0 >= 0) goto L_0x0026
            return
        L_0x0026:
            android.util.ArrayMap<java.lang.String, java.lang.Object> r3 = r7.mMap
            if (r3 != 0) goto L_0x0031
            android.util.ArrayMap r4 = new android.util.ArrayMap
            r4.<init>((int) r0)
            r3 = r4
            goto L_0x0037
        L_0x0031:
            r3.erase()
            r3.ensureCapacity(r0)
        L_0x0037:
            if (r10 == 0) goto L_0x0043
            java.lang.ClassLoader r4 = r7.mClassLoader     // Catch:{ BadParcelableException -> 0x0041 }
            r8.readArrayMapSafelyInternal(r3, r0, r4)     // Catch:{ BadParcelableException -> 0x0041 }
            goto L_0x0048
        L_0x003f:
            r4 = move-exception
            goto L_0x006a
        L_0x0041:
            r4 = move-exception
            goto L_0x0054
        L_0x0043:
            java.lang.ClassLoader r4 = r7.mClassLoader     // Catch:{ BadParcelableException -> 0x0041 }
            r8.readArrayMapInternal(r3, r0, r4)     // Catch:{ BadParcelableException -> 0x0041 }
        L_0x0048:
            r7.mMap = r3
            if (r9 == 0) goto L_0x004f
        L_0x004c:
            recycleParcel(r8)
        L_0x004f:
            r7.mParcelledData = r2
            r7.mParcelledByNative = r1
            goto L_0x0068
        L_0x0054:
            boolean r5 = sShouldDefuse     // Catch:{ all -> 0x003f }
            if (r5 == 0) goto L_0x0069
            java.lang.String r5 = "Bundle"
            java.lang.String r6 = "Failed to parse Bundle, but defusing quietly"
            android.util.Log.w(r5, r6, r4)     // Catch:{ all -> 0x003f }
            r3.erase()     // Catch:{ all -> 0x003f }
            r7.mMap = r3
            if (r9 == 0) goto L_0x004f
            goto L_0x004c
        L_0x0068:
            return
        L_0x0069:
            throw r4     // Catch:{ all -> 0x003f }
        L_0x006a:
            r7.mMap = r3
            if (r9 == 0) goto L_0x0071
            recycleParcel(r8)
        L_0x0071:
            r7.mParcelledData = r2
            r7.mParcelledByNative = r1
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BaseBundle.initializeFromParcelLocked(android.os.Parcel, boolean, boolean):void");
    }

    @UnsupportedAppUsage
    public boolean isParcelled() {
        return this.mParcelledData != null;
    }

    public boolean isEmptyParcel() {
        return isEmptyParcel(this.mParcelledData);
    }

    private static boolean isEmptyParcel(Parcel p) {
        return p == NoImagePreloadHolder.EMPTY_PARCEL;
    }

    private static void recycleParcel(Parcel p) {
        if (p != null && !isEmptyParcel(p)) {
            p.recycle();
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayMap<String, Object> getMap() {
        unparcel();
        return this.mMap;
    }

    public int size() {
        unparcel();
        return this.mMap.size();
    }

    public boolean isEmpty() {
        unparcel();
        return this.mMap.isEmpty();
    }

    public boolean maybeIsEmpty() {
        if (isParcelled()) {
            return isEmptyParcel();
        }
        return isEmpty();
    }

    public static boolean kindofEquals(BaseBundle a, BaseBundle b) {
        return a == b || (a != null && a.kindofEquals(b));
    }

    public boolean kindofEquals(BaseBundle other) {
        if (other == null || isParcelled() != other.isParcelled()) {
            return false;
        }
        if (!isParcelled()) {
            return this.mMap.equals(other.mMap);
        }
        if (this.mParcelledData.compareData(other.mParcelledData) == 0) {
            return true;
        }
        return false;
    }

    public void clear() {
        unparcel();
        this.mMap.clear();
    }

    /* access modifiers changed from: package-private */
    public void copyInternal(BaseBundle from, boolean deep) {
        synchronized (from) {
            if (from.mParcelledData == null) {
                this.mParcelledData = null;
                this.mParcelledByNative = false;
            } else if (from.isEmptyParcel()) {
                this.mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
                this.mParcelledByNative = false;
            } else {
                this.mParcelledData = Parcel.obtain();
                this.mParcelledData.appendFrom(from.mParcelledData, 0, from.mParcelledData.dataSize());
                this.mParcelledData.setDataPosition(0);
                this.mParcelledByNative = from.mParcelledByNative;
            }
            if (from.mMap == null) {
                this.mMap = null;
            } else if (!deep) {
                this.mMap = new ArrayMap<>(from.mMap);
            } else {
                ArrayMap<String, Object> fromMap = from.mMap;
                int N = fromMap.size();
                this.mMap = new ArrayMap<>(N);
                for (int i = 0; i < N; i++) {
                    this.mMap.append(fromMap.keyAt(i), deepCopyValue(fromMap.valueAt(i)));
                }
            }
            this.mClassLoader = from.mClassLoader;
        }
    }

    /* access modifiers changed from: package-private */
    public Object deepCopyValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Bundle) {
            return ((Bundle) value).deepCopy();
        }
        if (value instanceof PersistableBundle) {
            return ((PersistableBundle) value).deepCopy();
        }
        if (value instanceof ArrayList) {
            return deepcopyArrayList((ArrayList) value);
        }
        if (value.getClass().isArray()) {
            if (value instanceof int[]) {
                return ((int[]) value).clone();
            }
            if (value instanceof long[]) {
                return ((long[]) value).clone();
            }
            if (value instanceof float[]) {
                return ((float[]) value).clone();
            }
            if (value instanceof double[]) {
                return ((double[]) value).clone();
            }
            if (value instanceof Object[]) {
                return ((Object[]) value).clone();
            }
            if (value instanceof byte[]) {
                return ((byte[]) value).clone();
            }
            if (value instanceof short[]) {
                return ((short[]) value).clone();
            }
            if (value instanceof char[]) {
                return ((char[]) value).clone();
            }
        }
        return value;
    }

    /* access modifiers changed from: package-private */
    public ArrayList deepcopyArrayList(ArrayList from) {
        int N = from.size();
        ArrayList out = new ArrayList(N);
        for (int i = 0; i < N; i++) {
            out.add(deepCopyValue(from.get(i)));
        }
        return out;
    }

    public boolean containsKey(String key) {
        unparcel();
        return this.mMap.containsKey(key);
    }

    public Object get(String key) {
        unparcel();
        return this.mMap.get(key);
    }

    public void remove(String key) {
        unparcel();
        this.mMap.remove(key);
    }

    public void putAll(PersistableBundle bundle) {
        unparcel();
        bundle.unparcel();
        this.mMap.putAll(bundle.mMap);
    }

    /* access modifiers changed from: package-private */
    public void putAll(ArrayMap map) {
        unparcel();
        this.mMap.putAll(map);
    }

    public Set<String> keySet() {
        unparcel();
        return this.mMap.keySet();
    }

    public void putBoolean(String key, boolean value) {
        unparcel();
        this.mMap.put(key, Boolean.valueOf(value));
    }

    /* access modifiers changed from: package-private */
    public void putByte(String key, byte value) {
        unparcel();
        this.mMap.put(key, Byte.valueOf(value));
    }

    /* access modifiers changed from: package-private */
    public void putChar(String key, char value) {
        unparcel();
        this.mMap.put(key, Character.valueOf(value));
    }

    /* access modifiers changed from: package-private */
    public void putShort(String key, short value) {
        unparcel();
        this.mMap.put(key, Short.valueOf(value));
    }

    public void putInt(String key, int value) {
        unparcel();
        this.mMap.put(key, Integer.valueOf(value));
    }

    public void putLong(String key, long value) {
        unparcel();
        this.mMap.put(key, Long.valueOf(value));
    }

    /* access modifiers changed from: package-private */
    public void putFloat(String key, float value) {
        unparcel();
        this.mMap.put(key, Float.valueOf(value));
    }

    public void putDouble(String key, double value) {
        unparcel();
        this.mMap.put(key, Double.valueOf(value));
    }

    public void putString(String key, String value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putCharSequence(String key, CharSequence value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putIntegerArrayList(String key, ArrayList<Integer> value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putStringArrayList(String key, ArrayList<String> value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putSerializable(String key, Serializable value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putBooleanArray(String key, boolean[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putByteArray(String key, byte[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putShortArray(String key, short[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putCharArray(String key, char[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putIntArray(String key, int[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putLongArray(String key, long[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putFloatArray(String key, float[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putDoubleArray(String key, double[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putStringArray(String key, String[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    /* access modifiers changed from: package-private */
    public void putCharSequenceArray(String key, CharSequence[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public boolean getBoolean(String key) {
        unparcel();
        return getBoolean(key, false);
    }

    /* access modifiers changed from: package-private */
    public void typeWarning(String key, Object value, String className, Object defaultValue, ClassCastException e) {
        Log.w(TAG, "Key " + key + " expected " + className + " but value was a " + value.getClass().getName() + ".  The default value " + defaultValue + " was returned.");
        Log.w(TAG, "Attempt to cast generated internal exception:", e);
    }

    /* access modifiers changed from: package-private */
    public void typeWarning(String key, Object value, String className, ClassCastException e) {
        typeWarning(key, value, className, "<null>", e);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Boolean) o).booleanValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Boolean", Boolean.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    /* access modifiers changed from: package-private */
    public byte getByte(String key) {
        unparcel();
        return getByte(key, (byte) 0).byteValue();
    }

    /* access modifiers changed from: package-private */
    public Byte getByte(String key, byte defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return Byte.valueOf(defaultValue);
        }
        try {
            return (Byte) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Byte", Byte.valueOf(defaultValue), e);
            return Byte.valueOf(defaultValue);
        }
    }

    /* access modifiers changed from: package-private */
    public char getChar(String key) {
        unparcel();
        return getChar(key, 0);
    }

    /* access modifiers changed from: package-private */
    public char getChar(String key, char defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Character) o).charValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Character", Character.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    /* access modifiers changed from: package-private */
    public short getShort(String key) {
        unparcel();
        return getShort(key, 0);
    }

    /* access modifiers changed from: package-private */
    public short getShort(String key, short defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Short) o).shortValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Short", Short.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    public int getInt(String key) {
        unparcel();
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Integer) o).intValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Integer", Integer.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    public long getLong(String key) {
        unparcel();
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Long) o).longValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Long", Long.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    /* access modifiers changed from: package-private */
    public float getFloat(String key) {
        unparcel();
        return getFloat(key, 0.0f);
    }

    /* access modifiers changed from: package-private */
    public float getFloat(String key, float defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Float) o).floatValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Float", Float.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    public double getDouble(String key) {
        unparcel();
        return getDouble(key, 0.0d);
    }

    public double getDouble(String key, double defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Double) o).doubleValue();
        } catch (ClassCastException e) {
            ClassCastException e2 = e;
            typeWarning(key, o, "Double", Double.valueOf(defaultValue), e2);
            return defaultValue;
        }
    }

    public String getString(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "String", e);
            return null;
        }
    }

    public String getString(String key, String defaultValue) {
        String s = getString(key);
        return s == null ? defaultValue : s;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getCharSequence(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        try {
            return (CharSequence) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "CharSequence", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getCharSequence(String key, CharSequence defaultValue) {
        CharSequence cs = getCharSequence(key);
        return cs == null ? defaultValue : cs;
    }

    /* access modifiers changed from: package-private */
    public Serializable getSerializable(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (Serializable) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Serializable", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<Integer> getIntegerArrayList(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "ArrayList<Integer>", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> getStringArrayList(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "ArrayList<String>", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<CharSequence> getCharSequenceArrayList(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "ArrayList<CharSequence>", e);
            return null;
        }
    }

    public boolean[] getBooleanArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (boolean[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "byte[]", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getByteArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (byte[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "byte[]", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public short[] getShortArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (short[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "short[]", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public char[] getCharArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (char[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "char[]", e);
            return null;
        }
    }

    public int[] getIntArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (int[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "int[]", e);
            return null;
        }
    }

    public long[] getLongArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (long[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "long[]", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public float[] getFloatArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (float[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "float[]", e);
            return null;
        }
    }

    public double[] getDoubleArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (double[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "double[]", e);
            return null;
        }
    }

    public String[] getStringArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (String[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "String[]", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence[] getCharSequenceArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (CharSequence[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "CharSequence[]", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0036, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        if (r0 == null) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0040, code lost:
        if (r0.size() > 0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        r2 = r6.dataPosition();
        r6.writeInt(-1);
        r6.writeInt(BUNDLE_MAGIC);
        r1 = r6.dataPosition();
        r6.writeArrayMapInternal(r0);
        r3 = r6.dataPosition();
        r6.setDataPosition(r2);
        r6.writeInt(r3 - r1);
        r6.setDataPosition(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0064, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0065, code lost:
        r6.writeInt(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0068, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeToParcelInner(android.os.Parcel r6, int r7) {
        /*
            r5 = this;
            boolean r0 = r6.hasReadWriteHelper()
            if (r0 == 0) goto L_0x0009
            r5.unparcel()
        L_0x0009:
            monitor-enter(r5)
            android.os.Parcel r0 = r5.mParcelledData     // Catch:{ all -> 0x0069 }
            r1 = 1279544898(0x4c444e42, float:5.146036E7)
            r2 = 0
            if (r0 == 0) goto L_0x0037
            android.os.Parcel r0 = r5.mParcelledData     // Catch:{ all -> 0x0069 }
            android.os.Parcel r3 = android.os.BaseBundle.NoImagePreloadHolder.EMPTY_PARCEL     // Catch:{ all -> 0x0069 }
            if (r0 != r3) goto L_0x001c
            r6.writeInt(r2)     // Catch:{ all -> 0x0069 }
            goto L_0x0035
        L_0x001c:
            android.os.Parcel r0 = r5.mParcelledData     // Catch:{ all -> 0x0069 }
            int r0 = r0.dataSize()     // Catch:{ all -> 0x0069 }
            r6.writeInt(r0)     // Catch:{ all -> 0x0069 }
            boolean r3 = r5.mParcelledByNative     // Catch:{ all -> 0x0069 }
            if (r3 == 0) goto L_0x002d
            r1 = 1279544900(0x4c444e44, float:5.1460368E7)
        L_0x002d:
            r6.writeInt(r1)     // Catch:{ all -> 0x0069 }
            android.os.Parcel r1 = r5.mParcelledData     // Catch:{ all -> 0x0069 }
            r6.appendFrom(r1, r2, r0)     // Catch:{ all -> 0x0069 }
        L_0x0035:
            monitor-exit(r5)     // Catch:{ all -> 0x0069 }
            return
        L_0x0037:
            android.util.ArrayMap<java.lang.String, java.lang.Object> r0 = r5.mMap     // Catch:{ all -> 0x0069 }
            monitor-exit(r5)     // Catch:{ all -> 0x0069 }
            if (r0 == 0) goto L_0x0065
            int r3 = r0.size()
            if (r3 > 0) goto L_0x0043
            goto L_0x0065
        L_0x0043:
            int r2 = r6.dataPosition()
            r3 = -1
            r6.writeInt(r3)
            r6.writeInt(r1)
            int r1 = r6.dataPosition()
            r6.writeArrayMapInternal(r0)
            int r3 = r6.dataPosition()
            r6.setDataPosition(r2)
            int r4 = r3 - r1
            r6.writeInt(r4)
            r6.setDataPosition(r3)
            return
        L_0x0065:
            r6.writeInt(r2)
            return
        L_0x0069:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0069 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BaseBundle.writeToParcelInner(android.os.Parcel, int):void");
    }

    /* access modifiers changed from: package-private */
    public void readFromParcelInner(Parcel parcel) {
        readFromParcelInner(parcel, parcel.readInt());
    }

    private void readFromParcelInner(Parcel parcel, int length) {
        if (length < 0) {
            throw new RuntimeException("Bad length in parcel: " + length);
        } else if (length == 0) {
            this.mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            this.mParcelledByNative = false;
        } else if (length % 4 == 0) {
            int magic = parcel.readInt();
            boolean isNativeBundle = true;
            boolean isJavaBundle = magic == BUNDLE_MAGIC;
            if (magic != BUNDLE_MAGIC_NATIVE) {
                isNativeBundle = false;
            }
            if (!isJavaBundle && !isNativeBundle) {
                throw new IllegalStateException("Bad magic number for Bundle: 0x" + Integer.toHexString(magic));
            } else if (parcel.hasReadWriteHelper()) {
                synchronized (this) {
                    initializeFromParcelLocked(parcel, false, isNativeBundle);
                }
            } else {
                int offset = parcel.dataPosition();
                parcel.setDataPosition(MathUtils.addOrThrow(offset, length));
                Parcel p = Parcel.obtain();
                p.setDataPosition(0);
                p.appendFrom(parcel, offset, length);
                p.adoptClassCookies(parcel);
                p.setDataPosition(0);
                this.mParcelledData = p;
                this.mParcelledByNative = isNativeBundle;
            }
        } else {
            throw new IllegalStateException("Bundle length is not aligned by 4: " + length);
        }
    }

    public static void dumpStats(IndentingPrintWriter pw, String key, Object value) {
        Parcel tmp = Parcel.obtain();
        tmp.writeValue(value);
        int size = tmp.dataPosition();
        tmp.recycle();
        if (size > 1024) {
            pw.println(key + " [size=" + size + "]");
            if (value instanceof BaseBundle) {
                dumpStats(pw, (BaseBundle) value);
            } else if (value instanceof SparseArray) {
                dumpStats(pw, (SparseArray) value);
            }
        }
    }

    public static void dumpStats(IndentingPrintWriter pw, SparseArray array) {
        pw.increaseIndent();
        if (array == null) {
            pw.println("[null]");
            return;
        }
        for (int i = 0; i < array.size(); i++) {
            dumpStats(pw, "0x" + Integer.toHexString(array.keyAt(i)), array.valueAt(i));
        }
        pw.decreaseIndent();
    }

    public static void dumpStats(IndentingPrintWriter pw, BaseBundle bundle) {
        pw.increaseIndent();
        if (bundle == null) {
            pw.println("[null]");
            return;
        }
        ArrayMap<String, Object> map = bundle.getMap();
        for (int i = 0; i < map.size(); i++) {
            dumpStats(pw, map.keyAt(i), map.valueAt(i));
        }
        pw.decreaseIndent();
    }
}
