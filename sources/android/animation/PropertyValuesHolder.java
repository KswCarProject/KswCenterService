package android.animation;

import android.animation.Keyframes;
import android.animation.PathKeyframes;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.util.PathParser;
import android.util.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class PropertyValuesHolder implements Cloneable {
    private static Class[] DOUBLE_VARIANTS = {Double.TYPE, Double.class, Float.TYPE, Integer.TYPE, Float.class, Integer.class};
    private static Class[] FLOAT_VARIANTS = {Float.TYPE, Float.class, Double.TYPE, Integer.TYPE, Double.class, Integer.class};
    private static Class[] INTEGER_VARIANTS = {Integer.TYPE, Integer.class, Float.TYPE, Double.TYPE, Float.class, Double.class};
    private static final TypeEvaluator sFloatEvaluator = new FloatEvaluator();
    private static final HashMap<Class, HashMap<String, Method>> sGetterPropertyMap = new HashMap<>();
    private static final TypeEvaluator sIntEvaluator = new IntEvaluator();
    private static final HashMap<Class, HashMap<String, Method>> sSetterPropertyMap = new HashMap<>();
    private Object mAnimatedValue;
    private TypeConverter mConverter;
    private TypeEvaluator mEvaluator;
    private Method mGetter;
    Keyframes mKeyframes;
    protected Property mProperty;
    String mPropertyName;
    Method mSetter;
    final Object[] mTmpValueArray;
    Class mValueType;

    /* access modifiers changed from: private */
    public static native void nCallFloatMethod(Object obj, long j, float f);

    /* access modifiers changed from: private */
    public static native void nCallFourFloatMethod(Object obj, long j, float f, float f2, float f3, float f4);

    /* access modifiers changed from: private */
    public static native void nCallFourIntMethod(Object obj, long j, int i, int i2, int i3, int i4);

    /* access modifiers changed from: private */
    public static native void nCallIntMethod(Object obj, long j, int i);

    /* access modifiers changed from: private */
    public static native void nCallMultipleFloatMethod(Object obj, long j, float[] fArr);

    /* access modifiers changed from: private */
    public static native void nCallMultipleIntMethod(Object obj, long j, int[] iArr);

    /* access modifiers changed from: private */
    public static native void nCallTwoFloatMethod(Object obj, long j, float f, float f2);

    /* access modifiers changed from: private */
    public static native void nCallTwoIntMethod(Object obj, long j, int i, int i2);

    /* access modifiers changed from: private */
    public static native long nGetFloatMethod(Class cls, String str);

    /* access modifiers changed from: private */
    public static native long nGetIntMethod(Class cls, String str);

    /* access modifiers changed from: private */
    public static native long nGetMultipleFloatMethod(Class cls, String str, int i);

    /* access modifiers changed from: private */
    public static native long nGetMultipleIntMethod(Class cls, String str, int i);

    private PropertyValuesHolder(String propertyName) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mPropertyName = propertyName;
    }

    private PropertyValuesHolder(Property property) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }

    public static PropertyValuesHolder ofInt(String propertyName, int... values) {
        return new IntPropertyValuesHolder(propertyName, values);
    }

    public static PropertyValuesHolder ofInt(Property<?, Integer> property, int... values) {
        return new IntPropertyValuesHolder((Property) property, values);
    }

    public static PropertyValuesHolder ofMultiInt(String propertyName, int[][] values) {
        if (values.length >= 2) {
            int numParameters = 0;
            int i = 0;
            while (i < values.length) {
                if (values[i] != null) {
                    int length = values[i].length;
                    if (i == 0) {
                        numParameters = length;
                    } else if (length != numParameters) {
                        throw new IllegalArgumentException("Values must all have the same length");
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("values must not be null");
                }
            }
            return new MultiIntValuesHolder(propertyName, (TypeConverter) null, (TypeEvaluator) new IntArrayEvaluator(new int[numParameters]), (Object[]) values);
        }
        throw new IllegalArgumentException("At least 2 values must be supplied");
    }

    public static PropertyValuesHolder ofMultiInt(String propertyName, Path path) {
        return new MultiIntValuesHolder(propertyName, (TypeConverter) new PointFToIntArray(), (TypeEvaluator) null, KeyframeSet.ofPath(path));
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofMultiInt(String propertyName, TypeConverter<V, int[]> converter, TypeEvaluator<V> evaluator, V... values) {
        return new MultiIntValuesHolder(propertyName, (TypeConverter) converter, (TypeEvaluator) evaluator, (Object[]) values);
    }

    public static <T> PropertyValuesHolder ofMultiInt(String propertyName, TypeConverter<T, int[]> converter, TypeEvaluator<T> evaluator, Keyframe... values) {
        return new MultiIntValuesHolder(propertyName, (TypeConverter) converter, (TypeEvaluator) evaluator, (Keyframes) KeyframeSet.ofKeyframe(values));
    }

    public static PropertyValuesHolder ofFloat(String propertyName, float... values) {
        return new FloatPropertyValuesHolder(propertyName, values);
    }

    public static PropertyValuesHolder ofFloat(Property<?, Float> property, float... values) {
        return new FloatPropertyValuesHolder((Property) property, values);
    }

    public static PropertyValuesHolder ofMultiFloat(String propertyName, float[][] values) {
        if (values.length >= 2) {
            int numParameters = 0;
            int i = 0;
            while (i < values.length) {
                if (values[i] != null) {
                    int length = values[i].length;
                    if (i == 0) {
                        numParameters = length;
                    } else if (length != numParameters) {
                        throw new IllegalArgumentException("Values must all have the same length");
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("values must not be null");
                }
            }
            return new MultiFloatValuesHolder(propertyName, (TypeConverter) null, (TypeEvaluator) new FloatArrayEvaluator(new float[numParameters]), (Object[]) values);
        }
        throw new IllegalArgumentException("At least 2 values must be supplied");
    }

    public static PropertyValuesHolder ofMultiFloat(String propertyName, Path path) {
        return new MultiFloatValuesHolder(propertyName, (TypeConverter) new PointFToFloatArray(), (TypeEvaluator) null, KeyframeSet.ofPath(path));
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofMultiFloat(String propertyName, TypeConverter<V, float[]> converter, TypeEvaluator<V> evaluator, V... values) {
        return new MultiFloatValuesHolder(propertyName, (TypeConverter) converter, (TypeEvaluator) evaluator, (Object[]) values);
    }

    public static <T> PropertyValuesHolder ofMultiFloat(String propertyName, TypeConverter<T, float[]> converter, TypeEvaluator<T> evaluator, Keyframe... values) {
        return new MultiFloatValuesHolder(propertyName, (TypeConverter) converter, (TypeEvaluator) evaluator, (Keyframes) KeyframeSet.ofKeyframe(values));
    }

    public static PropertyValuesHolder ofObject(String propertyName, TypeEvaluator evaluator, Object... values) {
        PropertyValuesHolder pvh = new PropertyValuesHolder(propertyName);
        pvh.setObjectValues(values);
        pvh.setEvaluator(evaluator);
        return pvh;
    }

    public static PropertyValuesHolder ofObject(String propertyName, TypeConverter<PointF, ?> converter, Path path) {
        PropertyValuesHolder pvh = new PropertyValuesHolder(propertyName);
        pvh.mKeyframes = KeyframeSet.ofPath(path);
        pvh.mValueType = PointF.class;
        pvh.setConverter(converter);
        return pvh;
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofObject(Property property, TypeEvaluator<V> evaluator, V... values) {
        PropertyValuesHolder pvh = new PropertyValuesHolder(property);
        pvh.setObjectValues(values);
        pvh.setEvaluator(evaluator);
        return pvh;
    }

    @SafeVarargs
    public static <T, V> PropertyValuesHolder ofObject(Property<?, V> property, TypeConverter<T, V> converter, TypeEvaluator<T> evaluator, T... values) {
        PropertyValuesHolder pvh = new PropertyValuesHolder((Property) property);
        pvh.setConverter(converter);
        pvh.setObjectValues(values);
        pvh.setEvaluator(evaluator);
        return pvh;
    }

    public static <V> PropertyValuesHolder ofObject(Property<?, V> property, TypeConverter<PointF, V> converter, Path path) {
        PropertyValuesHolder pvh = new PropertyValuesHolder((Property) property);
        pvh.mKeyframes = KeyframeSet.ofPath(path);
        pvh.mValueType = PointF.class;
        pvh.setConverter(converter);
        return pvh;
    }

    public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values) {
        return ofKeyframes(propertyName, (Keyframes) KeyframeSet.ofKeyframe(values));
    }

    public static PropertyValuesHolder ofKeyframe(Property property, Keyframe... values) {
        return ofKeyframes(property, (Keyframes) KeyframeSet.ofKeyframe(values));
    }

    static PropertyValuesHolder ofKeyframes(String propertyName, Keyframes keyframes) {
        if (keyframes instanceof Keyframes.IntKeyframes) {
            return new IntPropertyValuesHolder(propertyName, (Keyframes.IntKeyframes) keyframes);
        }
        if (keyframes instanceof Keyframes.FloatKeyframes) {
            return new FloatPropertyValuesHolder(propertyName, (Keyframes.FloatKeyframes) keyframes);
        }
        PropertyValuesHolder pvh = new PropertyValuesHolder(propertyName);
        pvh.mKeyframes = keyframes;
        pvh.mValueType = keyframes.getType();
        return pvh;
    }

    static PropertyValuesHolder ofKeyframes(Property property, Keyframes keyframes) {
        if (keyframes instanceof Keyframes.IntKeyframes) {
            return new IntPropertyValuesHolder(property, (Keyframes.IntKeyframes) keyframes);
        }
        if (keyframes instanceof Keyframes.FloatKeyframes) {
            return new FloatPropertyValuesHolder(property, (Keyframes.FloatKeyframes) keyframes);
        }
        PropertyValuesHolder pvh = new PropertyValuesHolder(property);
        pvh.mKeyframes = keyframes;
        pvh.mValueType = keyframes.getType();
        return pvh;
    }

    public void setIntValues(int... values) {
        this.mValueType = Integer.TYPE;
        this.mKeyframes = KeyframeSet.ofInt(values);
    }

    public void setFloatValues(float... values) {
        this.mValueType = Float.TYPE;
        this.mKeyframes = KeyframeSet.ofFloat(values);
    }

    public void setKeyframes(Keyframe... values) {
        int numKeyframes = values.length;
        Keyframe[] keyframes = new Keyframe[Math.max(numKeyframes, 2)];
        this.mValueType = values[0].getType();
        for (int i = 0; i < numKeyframes; i++) {
            keyframes[i] = values[i];
        }
        this.mKeyframes = new KeyframeSet(keyframes);
    }

    public void setObjectValues(Object... values) {
        this.mValueType = values[0].getClass();
        this.mKeyframes = KeyframeSet.ofObject(values);
        if (this.mEvaluator != null) {
            this.mKeyframes.setEvaluator(this.mEvaluator);
        }
    }

    public void setConverter(TypeConverter converter) {
        this.mConverter = converter;
    }

    private Method getPropertyFunction(Class targetClass, String prefix, Class valueType) {
        Class[] typeVariants;
        Method returnVal = null;
        String methodName = getMethodName(prefix, this.mPropertyName);
        if (valueType == null) {
            try {
                returnVal = targetClass.getMethod(methodName, (Class[]) null);
            } catch (NoSuchMethodException e) {
            }
        } else {
            Class[] args = new Class[1];
            if (valueType.equals(Float.class)) {
                typeVariants = FLOAT_VARIANTS;
            } else if (valueType.equals(Integer.class)) {
                typeVariants = INTEGER_VARIANTS;
            } else {
                typeVariants = valueType.equals(Double.class) ? DOUBLE_VARIANTS : new Class[]{valueType};
            }
            int length = typeVariants.length;
            int i = 0;
            while (i < length) {
                Class typeVariant = typeVariants[i];
                args[0] = typeVariant;
                try {
                    Method returnVal2 = targetClass.getMethod(methodName, args);
                    if (this.mConverter == null) {
                        this.mValueType = typeVariant;
                    }
                    return returnVal2;
                } catch (NoSuchMethodException e2) {
                    i++;
                }
            }
            returnVal = null;
        }
        if (returnVal == null) {
            Log.w("PropertyValuesHolder", "Method " + getMethodName(prefix, this.mPropertyName) + "() with type " + valueType + " not found on target class " + targetClass);
        }
        return returnVal;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000b, code lost:
        r2 = r1.containsKey(r4.mPropertyName);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.reflect.Method setupSetterOrGetter(java.lang.Class r5, java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.reflect.Method>> r6, java.lang.String r7, java.lang.Class r8) {
        /*
            r4 = this;
            r0 = 0
            monitor-enter(r6)
            java.lang.Object r1 = r6.get(r5)     // Catch:{ all -> 0x0036 }
            java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ all -> 0x0036 }
            r2 = 0
            if (r1 == 0) goto L_0x001d
            java.lang.String r3 = r4.mPropertyName     // Catch:{ all -> 0x0036 }
            boolean r3 = r1.containsKey(r3)     // Catch:{ all -> 0x0036 }
            r2 = r3
            if (r2 == 0) goto L_0x001d
            java.lang.String r3 = r4.mPropertyName     // Catch:{ all -> 0x0036 }
            java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x0036 }
            java.lang.reflect.Method r3 = (java.lang.reflect.Method) r3     // Catch:{ all -> 0x0036 }
            r0 = r3
        L_0x001d:
            if (r2 != 0) goto L_0x0034
            java.lang.reflect.Method r3 = r4.getPropertyFunction(r5, r7, r8)     // Catch:{ all -> 0x0036 }
            r0 = r3
            if (r1 != 0) goto L_0x002f
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ all -> 0x0036 }
            r3.<init>()     // Catch:{ all -> 0x0036 }
            r1 = r3
            r6.put(r5, r1)     // Catch:{ all -> 0x0036 }
        L_0x002f:
            java.lang.String r3 = r4.mPropertyName     // Catch:{ all -> 0x0036 }
            r1.put(r3, r0)     // Catch:{ all -> 0x0036 }
        L_0x0034:
            monitor-exit(r6)     // Catch:{ all -> 0x0036 }
            return r0
        L_0x0036:
            r1 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0036 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.animation.PropertyValuesHolder.setupSetterOrGetter(java.lang.Class, java.util.HashMap, java.lang.String, java.lang.Class):java.lang.reflect.Method");
    }

    /* access modifiers changed from: package-private */
    public void setupSetter(Class targetClass) {
        this.mSetter = setupSetterOrGetter(targetClass, sSetterPropertyMap, "set", this.mConverter == null ? this.mValueType : this.mConverter.getTargetType());
    }

    private void setupGetter(Class targetClass) {
        this.mGetter = setupSetterOrGetter(targetClass, sGetterPropertyMap, "get", (Class) null);
    }

    /* access modifiers changed from: package-private */
    public void setupSetterAndGetter(Object target) {
        if (this.mProperty != null) {
            try {
                List<Keyframe> keyframes = this.mKeyframes.getKeyframes();
                int keyframeCount = keyframes == null ? 0 : keyframes.size();
                Object testValue = null;
                for (int i = 0; i < keyframeCount; i++) {
                    Keyframe kf = keyframes.get(i);
                    if (!kf.hasValue() || kf.valueWasSetOnStart()) {
                        if (testValue == null) {
                            testValue = convertBack(this.mProperty.get(target));
                        }
                        kf.setValue(testValue);
                        kf.setValueWasSetOnStart(true);
                    }
                }
                return;
            } catch (ClassCastException e) {
                Log.w("PropertyValuesHolder", "No such property (" + this.mProperty.getName() + ") on target object " + target + ". Trying reflection instead");
                this.mProperty = null;
            }
        }
        if (this.mProperty == null) {
            Class targetClass = target.getClass();
            if (this.mSetter == null) {
                setupSetter(targetClass);
            }
            List<Keyframe> keyframes2 = this.mKeyframes.getKeyframes();
            int keyframeCount2 = keyframes2 == null ? 0 : keyframes2.size();
            for (int i2 = 0; i2 < keyframeCount2; i2++) {
                Keyframe kf2 = keyframes2.get(i2);
                if (!kf2.hasValue() || kf2.valueWasSetOnStart()) {
                    if (this.mGetter == null) {
                        setupGetter(targetClass);
                        if (this.mGetter == null) {
                            return;
                        }
                    }
                    try {
                        kf2.setValue(convertBack(this.mGetter.invoke(target, new Object[0])));
                        kf2.setValueWasSetOnStart(true);
                    } catch (InvocationTargetException e2) {
                        Log.e("PropertyValuesHolder", e2.toString());
                    } catch (IllegalAccessException e3) {
                        Log.e("PropertyValuesHolder", e3.toString());
                    }
                }
            }
        }
    }

    private Object convertBack(Object value) {
        if (this.mConverter == null) {
            return value;
        }
        if (this.mConverter instanceof BidirectionalTypeConverter) {
            return ((BidirectionalTypeConverter) this.mConverter).convertBack(value);
        }
        throw new IllegalArgumentException("Converter " + this.mConverter.getClass().getName() + " must be a BidirectionalTypeConverter");
    }

    private void setupValue(Object target, Keyframe kf) {
        if (this.mProperty != null) {
            kf.setValue(convertBack(this.mProperty.get(target)));
            return;
        }
        try {
            if (this.mGetter == null) {
                setupGetter(target.getClass());
                if (this.mGetter == null) {
                    return;
                }
            }
            kf.setValue(convertBack(this.mGetter.invoke(target, new Object[0])));
        } catch (InvocationTargetException e) {
            Log.e("PropertyValuesHolder", e.toString());
        } catch (IllegalAccessException e2) {
            Log.e("PropertyValuesHolder", e2.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void setupStartValue(Object target) {
        List<Keyframe> keyframes = this.mKeyframes.getKeyframes();
        if (!keyframes.isEmpty()) {
            setupValue(target, keyframes.get(0));
        }
    }

    /* access modifiers changed from: package-private */
    public void setupEndValue(Object target) {
        List<Keyframe> keyframes = this.mKeyframes.getKeyframes();
        if (!keyframes.isEmpty()) {
            setupValue(target, keyframes.get(keyframes.size() - 1));
        }
    }

    public PropertyValuesHolder clone() {
        try {
            PropertyValuesHolder newPVH = (PropertyValuesHolder) super.clone();
            newPVH.mPropertyName = this.mPropertyName;
            newPVH.mProperty = this.mProperty;
            newPVH.mKeyframes = this.mKeyframes.clone();
            newPVH.mEvaluator = this.mEvaluator;
            return newPVH;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setAnimatedValue(Object target) {
        if (this.mProperty != null) {
            this.mProperty.set(target, getAnimatedValue());
        }
        if (this.mSetter != null) {
            try {
                this.mTmpValueArray[0] = getAnimatedValue();
                this.mSetter.invoke(target, this.mTmpValueArray);
            } catch (InvocationTargetException e) {
                Log.e("PropertyValuesHolder", e.toString());
            } catch (IllegalAccessException e2) {
                Log.e("PropertyValuesHolder", e2.toString());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void init() {
        TypeEvaluator typeEvaluator;
        if (this.mEvaluator == null) {
            if (this.mValueType == Integer.class) {
                typeEvaluator = sIntEvaluator;
            } else if (this.mValueType == Float.class) {
                typeEvaluator = sFloatEvaluator;
            } else {
                typeEvaluator = null;
            }
            this.mEvaluator = typeEvaluator;
        }
        if (this.mEvaluator != null) {
            this.mKeyframes.setEvaluator(this.mEvaluator);
        }
    }

    public void setEvaluator(TypeEvaluator evaluator) {
        this.mEvaluator = evaluator;
        this.mKeyframes.setEvaluator(evaluator);
    }

    /* access modifiers changed from: package-private */
    public void calculateValue(float fraction) {
        Object value = this.mKeyframes.getValue(fraction);
        this.mAnimatedValue = this.mConverter == null ? value : this.mConverter.convert(value);
    }

    public void setPropertyName(String propertyName) {
        this.mPropertyName = propertyName;
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    public String getPropertyName() {
        return this.mPropertyName;
    }

    /* access modifiers changed from: package-private */
    public Object getAnimatedValue() {
        return this.mAnimatedValue;
    }

    public void getPropertyValues(PropertyValues values) {
        init();
        values.propertyName = this.mPropertyName;
        values.type = this.mValueType;
        values.startValue = this.mKeyframes.getValue(0.0f);
        if (values.startValue instanceof PathParser.PathData) {
            values.startValue = new PathParser.PathData((PathParser.PathData) values.startValue);
        }
        values.endValue = this.mKeyframes.getValue(1.0f);
        if (values.endValue instanceof PathParser.PathData) {
            values.endValue = new PathParser.PathData((PathParser.PathData) values.endValue);
        }
        if ((this.mKeyframes instanceof PathKeyframes.FloatKeyframesBase) || (this.mKeyframes instanceof PathKeyframes.IntKeyframesBase) || (this.mKeyframes.getKeyframes() != null && this.mKeyframes.getKeyframes().size() > 2)) {
            values.dataSource = new PropertyValues.DataSource() {
                public Object getValueAtFraction(float fraction) {
                    return PropertyValuesHolder.this.mKeyframes.getValue(fraction);
                }
            };
        } else {
            values.dataSource = null;
        }
    }

    public Class getValueType() {
        return this.mValueType;
    }

    public String toString() {
        return this.mPropertyName + ": " + this.mKeyframes.toString();
    }

    static String getMethodName(String prefix, String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            return prefix;
        }
        char firstLetter = Character.toUpperCase(propertyName.charAt(0));
        String theRest = propertyName.substring(1);
        return prefix + firstLetter + theRest;
    }

    static class IntPropertyValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        int mIntAnimatedValue;
        Keyframes.IntKeyframes mIntKeyframes;
        private IntProperty mIntProperty;
        long mJniSetter;

        public IntPropertyValuesHolder(String propertyName, Keyframes.IntKeyframes keyframes) {
            super(propertyName);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = keyframes;
            this.mIntKeyframes = keyframes;
        }

        public IntPropertyValuesHolder(Property property, Keyframes.IntKeyframes keyframes) {
            super(property);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = keyframes;
            this.mIntKeyframes = keyframes;
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) this.mProperty;
            }
        }

        public IntPropertyValuesHolder(String propertyName, int... values) {
            super(propertyName);
            setIntValues(values);
        }

        public IntPropertyValuesHolder(Property property, int... values) {
            super(property);
            setIntValues(values);
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) this.mProperty;
            }
        }

        public void setProperty(Property property) {
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) property;
            } else {
                PropertyValuesHolder.super.setProperty(property);
            }
        }

        public void setIntValues(int... values) {
            PropertyValuesHolder.super.setIntValues(values);
            this.mIntKeyframes = (Keyframes.IntKeyframes) this.mKeyframes;
        }

        /* access modifiers changed from: package-private */
        public void calculateValue(float fraction) {
            this.mIntAnimatedValue = this.mIntKeyframes.getIntValue(fraction);
        }

        /* access modifiers changed from: package-private */
        public Object getAnimatedValue() {
            return Integer.valueOf(this.mIntAnimatedValue);
        }

        public IntPropertyValuesHolder clone() {
            IntPropertyValuesHolder newPVH = (IntPropertyValuesHolder) PropertyValuesHolder.super.clone();
            newPVH.mIntKeyframes = (Keyframes.IntKeyframes) newPVH.mKeyframes;
            return newPVH;
        }

        /* access modifiers changed from: package-private */
        public void setAnimatedValue(Object target) {
            if (this.mIntProperty != null) {
                this.mIntProperty.setValue(target, this.mIntAnimatedValue);
            } else if (this.mProperty != null) {
                this.mProperty.set(target, Integer.valueOf(this.mIntAnimatedValue));
            } else if (this.mJniSetter != 0) {
                PropertyValuesHolder.nCallIntMethod(target, this.mJniSetter, this.mIntAnimatedValue);
            } else if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = Integer.valueOf(this.mIntAnimatedValue);
                    this.mSetter.invoke(target, this.mTmpValueArray);
                } catch (InvocationTargetException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (IllegalAccessException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
            r2 = r1.containsKey(r7.mPropertyName);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setupSetter(java.lang.Class r8) {
            /*
                r7 = this;
                android.util.Property r0 = r7.mProperty
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r0 = sJNISetterPropertyMap
                monitor-enter(r0)
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r1 = sJNISetterPropertyMap     // Catch:{ all -> 0x0064 }
                java.lang.Object r1 = r1.get(r8)     // Catch:{ all -> 0x0064 }
                java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ all -> 0x0064 }
                r2 = 0
                if (r1 == 0) goto L_0x002c
                java.lang.String r3 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                boolean r3 = r1.containsKey(r3)     // Catch:{ all -> 0x0064 }
                r2 = r3
                if (r2 == 0) goto L_0x002c
                java.lang.String r3 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x0064 }
                java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ all -> 0x0064 }
                if (r3 == 0) goto L_0x002c
                long r4 = r3.longValue()     // Catch:{ all -> 0x0064 }
                r7.mJniSetter = r4     // Catch:{ all -> 0x0064 }
            L_0x002c:
                if (r2 != 0) goto L_0x0057
                java.lang.String r3 = "set"
                java.lang.String r4 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                java.lang.String r3 = getMethodName(r3, r4)     // Catch:{ all -> 0x0064 }
                long r4 = android.animation.PropertyValuesHolder.nGetIntMethod(r8, r3)     // Catch:{ NoSuchMethodError -> 0x003e }
                r7.mJniSetter = r4     // Catch:{ NoSuchMethodError -> 0x003e }
                goto L_0x003f
            L_0x003e:
                r4 = move-exception
            L_0x003f:
                if (r1 != 0) goto L_0x004c
                java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x0064 }
                r4.<init>()     // Catch:{ all -> 0x0064 }
                r1 = r4
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r4 = sJNISetterPropertyMap     // Catch:{ all -> 0x0064 }
                r4.put(r8, r1)     // Catch:{ all -> 0x0064 }
            L_0x004c:
                java.lang.String r4 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                long r5 = r7.mJniSetter     // Catch:{ all -> 0x0064 }
                java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0064 }
                r1.put(r4, r5)     // Catch:{ all -> 0x0064 }
            L_0x0057:
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                long r0 = r7.mJniSetter
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 != 0) goto L_0x0063
                android.animation.PropertyValuesHolder.super.setupSetter(r8)
            L_0x0063:
                return
            L_0x0064:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.animation.PropertyValuesHolder.IntPropertyValuesHolder.setupSetter(java.lang.Class):void");
        }
    }

    static class FloatPropertyValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        float mFloatAnimatedValue;
        Keyframes.FloatKeyframes mFloatKeyframes;
        private FloatProperty mFloatProperty;
        long mJniSetter;

        public FloatPropertyValuesHolder(String propertyName, Keyframes.FloatKeyframes keyframes) {
            super(propertyName);
            this.mValueType = Float.TYPE;
            this.mKeyframes = keyframes;
            this.mFloatKeyframes = keyframes;
        }

        public FloatPropertyValuesHolder(Property property, Keyframes.FloatKeyframes keyframes) {
            super(property);
            this.mValueType = Float.TYPE;
            this.mKeyframes = keyframes;
            this.mFloatKeyframes = keyframes;
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(String propertyName, float... values) {
            super(propertyName);
            setFloatValues(values);
        }

        public FloatPropertyValuesHolder(Property property, float... values) {
            super(property);
            setFloatValues(values);
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) this.mProperty;
            }
        }

        public void setProperty(Property property) {
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) property;
            } else {
                PropertyValuesHolder.super.setProperty(property);
            }
        }

        public void setFloatValues(float... values) {
            PropertyValuesHolder.super.setFloatValues(values);
            this.mFloatKeyframes = (Keyframes.FloatKeyframes) this.mKeyframes;
        }

        /* access modifiers changed from: package-private */
        public void calculateValue(float fraction) {
            this.mFloatAnimatedValue = this.mFloatKeyframes.getFloatValue(fraction);
        }

        /* access modifiers changed from: package-private */
        public Object getAnimatedValue() {
            return Float.valueOf(this.mFloatAnimatedValue);
        }

        public FloatPropertyValuesHolder clone() {
            FloatPropertyValuesHolder newPVH = (FloatPropertyValuesHolder) PropertyValuesHolder.super.clone();
            newPVH.mFloatKeyframes = (Keyframes.FloatKeyframes) newPVH.mKeyframes;
            return newPVH;
        }

        /* access modifiers changed from: package-private */
        public void setAnimatedValue(Object target) {
            if (this.mFloatProperty != null) {
                this.mFloatProperty.setValue(target, this.mFloatAnimatedValue);
            } else if (this.mProperty != null) {
                this.mProperty.set(target, Float.valueOf(this.mFloatAnimatedValue));
            } else if (this.mJniSetter != 0) {
                PropertyValuesHolder.nCallFloatMethod(target, this.mJniSetter, this.mFloatAnimatedValue);
            } else if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = Float.valueOf(this.mFloatAnimatedValue);
                    this.mSetter.invoke(target, this.mTmpValueArray);
                } catch (InvocationTargetException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (IllegalAccessException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
            r2 = r1.containsKey(r7.mPropertyName);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setupSetter(java.lang.Class r8) {
            /*
                r7 = this;
                android.util.Property r0 = r7.mProperty
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r0 = sJNISetterPropertyMap
                monitor-enter(r0)
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r1 = sJNISetterPropertyMap     // Catch:{ all -> 0x0064 }
                java.lang.Object r1 = r1.get(r8)     // Catch:{ all -> 0x0064 }
                java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ all -> 0x0064 }
                r2 = 0
                if (r1 == 0) goto L_0x002c
                java.lang.String r3 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                boolean r3 = r1.containsKey(r3)     // Catch:{ all -> 0x0064 }
                r2 = r3
                if (r2 == 0) goto L_0x002c
                java.lang.String r3 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x0064 }
                java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ all -> 0x0064 }
                if (r3 == 0) goto L_0x002c
                long r4 = r3.longValue()     // Catch:{ all -> 0x0064 }
                r7.mJniSetter = r4     // Catch:{ all -> 0x0064 }
            L_0x002c:
                if (r2 != 0) goto L_0x0057
                java.lang.String r3 = "set"
                java.lang.String r4 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                java.lang.String r3 = getMethodName(r3, r4)     // Catch:{ all -> 0x0064 }
                long r4 = android.animation.PropertyValuesHolder.nGetFloatMethod(r8, r3)     // Catch:{ NoSuchMethodError -> 0x003e }
                r7.mJniSetter = r4     // Catch:{ NoSuchMethodError -> 0x003e }
                goto L_0x003f
            L_0x003e:
                r4 = move-exception
            L_0x003f:
                if (r1 != 0) goto L_0x004c
                java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x0064 }
                r4.<init>()     // Catch:{ all -> 0x0064 }
                r1 = r4
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r4 = sJNISetterPropertyMap     // Catch:{ all -> 0x0064 }
                r4.put(r8, r1)     // Catch:{ all -> 0x0064 }
            L_0x004c:
                java.lang.String r4 = r7.mPropertyName     // Catch:{ all -> 0x0064 }
                long r5 = r7.mJniSetter     // Catch:{ all -> 0x0064 }
                java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0064 }
                r1.put(r4, r5)     // Catch:{ all -> 0x0064 }
            L_0x0057:
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                long r0 = r7.mJniSetter
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 != 0) goto L_0x0063
                android.animation.PropertyValuesHolder.super.setupSetter(r8)
            L_0x0063:
                return
            L_0x0064:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.animation.PropertyValuesHolder.FloatPropertyValuesHolder.setupSetter(java.lang.Class):void");
        }
    }

    static class MultiFloatValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        private long mJniSetter;

        public MultiFloatValuesHolder(String propertyName, TypeConverter converter, TypeEvaluator evaluator, Object... values) {
            super(propertyName);
            setConverter(converter);
            setObjectValues(values);
            setEvaluator(evaluator);
        }

        public MultiFloatValuesHolder(String propertyName, TypeConverter converter, TypeEvaluator evaluator, Keyframes keyframes) {
            super(propertyName);
            setConverter(converter);
            this.mKeyframes = keyframes;
            setEvaluator(evaluator);
        }

        /* access modifiers changed from: package-private */
        public void setAnimatedValue(Object target) {
            float[] values = (float[]) getAnimatedValue();
            int numParameters = values.length;
            if (this.mJniSetter == 0) {
                return;
            }
            if (numParameters != 4) {
                switch (numParameters) {
                    case 1:
                        PropertyValuesHolder.nCallFloatMethod(target, this.mJniSetter, values[0]);
                        return;
                    case 2:
                        PropertyValuesHolder.nCallTwoFloatMethod(target, this.mJniSetter, values[0], values[1]);
                        return;
                    default:
                        PropertyValuesHolder.nCallMultipleFloatMethod(target, this.mJniSetter, values);
                        return;
                }
            } else {
                PropertyValuesHolder.nCallFourFloatMethod(target, this.mJniSetter, values[0], values[1], values[2], values[3]);
            }
        }

        /* access modifiers changed from: package-private */
        public void setupSetterAndGetter(Object target) {
            setupSetter(target.getClass());
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0017, code lost:
            r2 = r1.containsKey(r9.mPropertyName);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setupSetter(java.lang.Class r10) {
            /*
                r9 = this;
                long r0 = r9.mJniSetter
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r0 = sJNISetterPropertyMap
                monitor-enter(r0)
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r1 = sJNISetterPropertyMap     // Catch:{ all -> 0x0072 }
                java.lang.Object r1 = r1.get(r10)     // Catch:{ all -> 0x0072 }
                java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ all -> 0x0072 }
                r2 = 0
                if (r1 == 0) goto L_0x0030
                java.lang.String r3 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                boolean r3 = r1.containsKey(r3)     // Catch:{ all -> 0x0072 }
                r2 = r3
                if (r2 == 0) goto L_0x0030
                java.lang.String r3 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x0072 }
                java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ all -> 0x0072 }
                if (r3 == 0) goto L_0x0030
                long r4 = r3.longValue()     // Catch:{ all -> 0x0072 }
                r9.mJniSetter = r4     // Catch:{ all -> 0x0072 }
            L_0x0030:
                if (r2 != 0) goto L_0x0070
                java.lang.String r3 = "set"
                java.lang.String r4 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                java.lang.String r3 = getMethodName(r3, r4)     // Catch:{ all -> 0x0072 }
                r4 = 0
                r9.calculateValue(r4)     // Catch:{ all -> 0x0072 }
                java.lang.Object r4 = r9.getAnimatedValue()     // Catch:{ all -> 0x0072 }
                float[] r4 = (float[]) r4     // Catch:{ all -> 0x0072 }
                int r5 = r4.length     // Catch:{ all -> 0x0072 }
                long r6 = android.animation.PropertyValuesHolder.nGetMultipleFloatMethod(r10, r3, r5)     // Catch:{ NoSuchMethodError -> 0x004d }
                r9.mJniSetter = r6     // Catch:{ NoSuchMethodError -> 0x004d }
                goto L_0x0058
            L_0x004d:
                r6 = move-exception
                java.lang.String r7 = r9.mPropertyName     // Catch:{ NoSuchMethodError -> 0x0057 }
                long r7 = android.animation.PropertyValuesHolder.nGetMultipleFloatMethod(r10, r7, r5)     // Catch:{ NoSuchMethodError -> 0x0057 }
                r9.mJniSetter = r7     // Catch:{ NoSuchMethodError -> 0x0057 }
                goto L_0x0058
            L_0x0057:
                r7 = move-exception
            L_0x0058:
                if (r1 != 0) goto L_0x0065
                java.util.HashMap r6 = new java.util.HashMap     // Catch:{ all -> 0x0072 }
                r6.<init>()     // Catch:{ all -> 0x0072 }
                r1 = r6
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r6 = sJNISetterPropertyMap     // Catch:{ all -> 0x0072 }
                r6.put(r10, r1)     // Catch:{ all -> 0x0072 }
            L_0x0065:
                java.lang.String r6 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                long r7 = r9.mJniSetter     // Catch:{ all -> 0x0072 }
                java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0072 }
                r1.put(r6, r7)     // Catch:{ all -> 0x0072 }
            L_0x0070:
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                return
            L_0x0072:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.animation.PropertyValuesHolder.MultiFloatValuesHolder.setupSetter(java.lang.Class):void");
        }
    }

    static class MultiIntValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        private long mJniSetter;

        public MultiIntValuesHolder(String propertyName, TypeConverter converter, TypeEvaluator evaluator, Object... values) {
            super(propertyName);
            setConverter(converter);
            setObjectValues(values);
            setEvaluator(evaluator);
        }

        public MultiIntValuesHolder(String propertyName, TypeConverter converter, TypeEvaluator evaluator, Keyframes keyframes) {
            super(propertyName);
            setConverter(converter);
            this.mKeyframes = keyframes;
            setEvaluator(evaluator);
        }

        /* access modifiers changed from: package-private */
        public void setAnimatedValue(Object target) {
            int[] values = (int[]) getAnimatedValue();
            int numParameters = values.length;
            if (this.mJniSetter == 0) {
                return;
            }
            if (numParameters != 4) {
                switch (numParameters) {
                    case 1:
                        PropertyValuesHolder.nCallIntMethod(target, this.mJniSetter, values[0]);
                        return;
                    case 2:
                        PropertyValuesHolder.nCallTwoIntMethod(target, this.mJniSetter, values[0], values[1]);
                        return;
                    default:
                        PropertyValuesHolder.nCallMultipleIntMethod(target, this.mJniSetter, values);
                        return;
                }
            } else {
                PropertyValuesHolder.nCallFourIntMethod(target, this.mJniSetter, values[0], values[1], values[2], values[3]);
            }
        }

        /* access modifiers changed from: package-private */
        public void setupSetterAndGetter(Object target) {
            setupSetter(target.getClass());
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0017, code lost:
            r2 = r1.containsKey(r9.mPropertyName);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setupSetter(java.lang.Class r10) {
            /*
                r9 = this;
                long r0 = r9.mJniSetter
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r0 = sJNISetterPropertyMap
                monitor-enter(r0)
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r1 = sJNISetterPropertyMap     // Catch:{ all -> 0x0072 }
                java.lang.Object r1 = r1.get(r10)     // Catch:{ all -> 0x0072 }
                java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ all -> 0x0072 }
                r2 = 0
                if (r1 == 0) goto L_0x0030
                java.lang.String r3 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                boolean r3 = r1.containsKey(r3)     // Catch:{ all -> 0x0072 }
                r2 = r3
                if (r2 == 0) goto L_0x0030
                java.lang.String r3 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                java.lang.Object r3 = r1.get(r3)     // Catch:{ all -> 0x0072 }
                java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ all -> 0x0072 }
                if (r3 == 0) goto L_0x0030
                long r4 = r3.longValue()     // Catch:{ all -> 0x0072 }
                r9.mJniSetter = r4     // Catch:{ all -> 0x0072 }
            L_0x0030:
                if (r2 != 0) goto L_0x0070
                java.lang.String r3 = "set"
                java.lang.String r4 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                java.lang.String r3 = getMethodName(r3, r4)     // Catch:{ all -> 0x0072 }
                r4 = 0
                r9.calculateValue(r4)     // Catch:{ all -> 0x0072 }
                java.lang.Object r4 = r9.getAnimatedValue()     // Catch:{ all -> 0x0072 }
                int[] r4 = (int[]) r4     // Catch:{ all -> 0x0072 }
                int r5 = r4.length     // Catch:{ all -> 0x0072 }
                long r6 = android.animation.PropertyValuesHolder.nGetMultipleIntMethod(r10, r3, r5)     // Catch:{ NoSuchMethodError -> 0x004d }
                r9.mJniSetter = r6     // Catch:{ NoSuchMethodError -> 0x004d }
                goto L_0x0058
            L_0x004d:
                r6 = move-exception
                java.lang.String r7 = r9.mPropertyName     // Catch:{ NoSuchMethodError -> 0x0057 }
                long r7 = android.animation.PropertyValuesHolder.nGetMultipleIntMethod(r10, r7, r5)     // Catch:{ NoSuchMethodError -> 0x0057 }
                r9.mJniSetter = r7     // Catch:{ NoSuchMethodError -> 0x0057 }
                goto L_0x0058
            L_0x0057:
                r7 = move-exception
            L_0x0058:
                if (r1 != 0) goto L_0x0065
                java.util.HashMap r6 = new java.util.HashMap     // Catch:{ all -> 0x0072 }
                r6.<init>()     // Catch:{ all -> 0x0072 }
                r1 = r6
                java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> r6 = sJNISetterPropertyMap     // Catch:{ all -> 0x0072 }
                r6.put(r10, r1)     // Catch:{ all -> 0x0072 }
            L_0x0065:
                java.lang.String r6 = r9.mPropertyName     // Catch:{ all -> 0x0072 }
                long r7 = r9.mJniSetter     // Catch:{ all -> 0x0072 }
                java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0072 }
                r1.put(r6, r7)     // Catch:{ all -> 0x0072 }
            L_0x0070:
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                return
            L_0x0072:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.animation.PropertyValuesHolder.MultiIntValuesHolder.setupSetter(java.lang.Class):void");
        }
    }

    private static class PointFToFloatArray extends TypeConverter<PointF, float[]> {
        private float[] mCoordinates = new float[2];

        public PointFToFloatArray() {
            super(PointF.class, float[].class);
        }

        public float[] convert(PointF value) {
            this.mCoordinates[0] = value.x;
            this.mCoordinates[1] = value.y;
            return this.mCoordinates;
        }
    }

    private static class PointFToIntArray extends TypeConverter<PointF, int[]> {
        private int[] mCoordinates = new int[2];

        public PointFToIntArray() {
            super(PointF.class, int[].class);
        }

        public int[] convert(PointF value) {
            this.mCoordinates[0] = Math.round(value.x);
            this.mCoordinates[1] = Math.round(value.y);
            return this.mCoordinates;
        }
    }

    public static class PropertyValues {
        public DataSource dataSource = null;
        public Object endValue;
        public String propertyName;
        public Object startValue;
        public Class type;

        public interface DataSource {
            Object getValueAtFraction(float f);
        }

        public String toString() {
            return "property name: " + this.propertyName + ", type: " + this.type + ", startValue: " + this.startValue.toString() + ", endValue: " + this.endValue.toString();
        }
    }
}
