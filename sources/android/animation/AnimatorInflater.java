package android.animation;

import android.content.Context;
import android.content.res.ConfigurationBoundResourceCache;
import android.content.res.ConstantState;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.PathParser;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import android.view.animation.AnimationUtils;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import com.android.ims.ImsConfig;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflater {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int SEQUENTIALLY = 1;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;
    private static final TypedValue sTmpTypedValue = new TypedValue();

    public static Animator loadAnimator(Context context, int id) throws Resources.NotFoundException {
        return loadAnimator(context.getResources(), context.getTheme(), id);
    }

    public static Animator loadAnimator(Resources resources, Resources.Theme theme, int id) throws Resources.NotFoundException {
        return loadAnimator(resources, theme, id, 1.0f);
    }

    public static Animator loadAnimator(Resources resources, Resources.Theme theme, int id, float pathErrorScale) throws Resources.NotFoundException {
        ConfigurationBoundResourceCache<Animator> animatorCache = resources.getAnimatorCache();
        Animator animator = animatorCache.getInstance((long) id, resources, theme);
        if (animator != null) {
            return animator;
        }
        XmlResourceParser parser = null;
        try {
            XmlResourceParser parser2 = resources.getAnimation(id);
            Animator animator2 = createAnimatorFromXml(resources, theme, parser2, pathErrorScale);
            if (animator2 != null) {
                animator2.appendChangingConfigurations(getChangingConfigs(resources, id));
                ConstantState<Animator> constantState = animator2.createConstantState();
                if (constantState != null) {
                    animatorCache.put((long) id, theme, constantState);
                    animator2 = constantState.newInstance(resources, theme);
                }
            }
            if (parser2 != null) {
                parser2.close();
            }
            return animator2;
        } catch (XmlPullParserException ex) {
            Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex2) {
            Resources.NotFoundException rnf2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf2.initCause(ex2);
            throw rnf2;
        } catch (Throwable th) {
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
    }

    public static StateListAnimator loadStateListAnimator(Context context, int id) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        ConfigurationBoundResourceCache<StateListAnimator> cache = resources.getStateListAnimatorCache();
        Resources.Theme theme = context.getTheme();
        StateListAnimator animator = cache.getInstance((long) id, resources, theme);
        if (animator != null) {
            return animator;
        }
        XmlResourceParser parser = null;
        try {
            XmlResourceParser parser2 = resources.getAnimation(id);
            StateListAnimator animator2 = createStateListAnimatorFromXml(context, parser2, Xml.asAttributeSet(parser2));
            if (animator2 != null) {
                animator2.appendChangingConfigurations(getChangingConfigs(resources, id));
                ConstantState<StateListAnimator> constantState = animator2.createConstantState();
                if (constantState != null) {
                    cache.put((long) id, theme, constantState);
                    animator2 = constantState.newInstance(resources, theme);
                }
            }
            if (parser2 != null) {
                parser2.close();
            }
            return animator2;
        } catch (XmlPullParserException ex) {
            Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load state list animator resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex2) {
            Resources.NotFoundException rnf2 = new Resources.NotFoundException("Can't load state list animator resource ID #0x" + Integer.toHexString(id));
            rnf2.initCause(ex2);
            throw rnf2;
        } catch (Throwable th) {
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
    }

    private static StateListAnimator createStateListAnimatorFromXml(Context context, XmlPullParser parser, AttributeSet attributeSet) throws IOException, XmlPullParserException {
        int i;
        StateListAnimator stateListAnimator = new StateListAnimator();
        while (true) {
            switch (parser.next()) {
                case 1:
                case 3:
                    return stateListAnimator;
                case 2:
                    if (ImsConfig.EXTRA_CHANGED_ITEM.equals(parser.getName())) {
                        int attributeCount = parser.getAttributeCount();
                        int[] states = new int[attributeCount];
                        int stateIndex = 0;
                        Animator animator = null;
                        for (int i2 = 0; i2 < attributeCount; i2++) {
                            int attrName = attributeSet.getAttributeNameResource(i2);
                            if (attrName == 16843213) {
                                animator = loadAnimator(context, attributeSet.getAttributeResourceValue(i2, 0));
                            } else {
                                int stateIndex2 = stateIndex + 1;
                                if (attributeSet.getAttributeBooleanValue(i2, false)) {
                                    i = attrName;
                                } else {
                                    i = -attrName;
                                }
                                states[stateIndex] = i;
                                stateIndex = stateIndex2;
                            }
                        }
                        if (animator == null) {
                            animator = createAnimatorFromXml(context.getResources(), context.getTheme(), parser, 1.0f);
                        }
                        if (animator != null) {
                            stateListAnimator.addState(StateSet.trimStateSet(states, stateIndex), animator);
                            break;
                        } else {
                            throw new Resources.NotFoundException("animation state item must have a valid animation");
                        }
                    } else {
                        continue;
                    }
            }
        }
    }

    private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathData> {
        private final PathParser.PathData mPathData;

        private PathDataEvaluator() {
            this.mPathData = new PathParser.PathData();
        }

        public PathParser.PathData evaluate(float fraction, PathParser.PathData startPathData, PathParser.PathData endPathData) {
            if (PathParser.interpolatePathData(this.mPathData, startPathData, endPathData, fraction)) {
                return this.mPathData;
            }
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }

    private static PropertyValuesHolder getPVH(TypedArray styledAttributes, int valueType, int valueFromId, int valueToId, String propertyName) {
        int valueType2;
        PropertyValuesHolder returnValue;
        PropertyValuesHolder returnValue2;
        int valueTo;
        char c;
        int valueFrom;
        int valueTo2;
        char c2;
        PropertyValuesHolder propertyValuesHolder;
        float valueTo3;
        float valueFrom2;
        float valueTo4;
        PathParser.PathData nodesTo;
        int toType;
        PropertyValuesHolder returnValue3;
        TypedArray typedArray = styledAttributes;
        int i = valueFromId;
        int i2 = valueToId;
        String str = propertyName;
        TypedValue tvFrom = typedArray.peekValue(i);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = typedArray.peekValue(i2);
        boolean hasTo = tvTo != null;
        int toType2 = hasTo ? tvTo.type : 0;
        int i3 = valueType;
        if (i3 != 4) {
            valueType2 = i3;
        } else if ((!hasFrom || !isColorType(fromType)) && (!hasTo || !isColorType(toType2))) {
            valueType2 = 0;
        } else {
            valueType2 = 3;
        }
        boolean getFloats = valueType2 == 0;
        if (valueType2 == 2) {
            String fromString = typedArray.getString(i);
            String toString = typedArray.getString(i2);
            PathParser.PathData nodesFrom = fromString == null ? null : new PathParser.PathData(fromString);
            if (toString == null) {
                TypedValue typedValue = tvFrom;
                nodesTo = null;
            } else {
                TypedValue typedValue2 = tvFrom;
                nodesTo = new PathParser.PathData(toString);
            }
            if (nodesFrom == null && nodesTo == null) {
                TypedValue typedValue3 = tvTo;
                toType = toType2;
                returnValue3 = null;
            } else {
                if (nodesFrom != null) {
                    TypedValue typedValue4 = tvTo;
                    TypeEvaluator evaluator = new PathDataEvaluator();
                    if (nodesTo == null) {
                        toType = toType2;
                        returnValue = PropertyValuesHolder.ofObject(str, evaluator, nodesFrom);
                    } else if (PathParser.canMorph(nodesFrom, nodesTo)) {
                        returnValue = PropertyValuesHolder.ofObject(str, evaluator, nodesFrom, nodesTo);
                        toType = toType2;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        int i4 = toType2;
                        sb.append(" Can't morph from ");
                        sb.append(fromString);
                        sb.append(" to ");
                        sb.append(toString);
                        throw new InflateException(sb.toString());
                    }
                } else {
                    toType = toType2;
                    returnValue3 = null;
                    if (nodesTo != null) {
                        returnValue = PropertyValuesHolder.ofObject(str, new PathDataEvaluator(), nodesTo);
                    }
                }
                int i5 = toType;
                int i6 = valueToId;
            }
            returnValue = returnValue3;
            int i52 = toType;
            int i62 = valueToId;
        } else {
            TypedValue typedValue5 = tvTo;
            int toType3 = toType2;
            TypeEvaluator evaluator2 = null;
            if (valueType2 == 3) {
                evaluator2 = ArgbEvaluator.getInstance();
            }
            if (getFloats) {
                if (hasFrom) {
                    if (fromType == 5) {
                        valueFrom2 = typedArray.getDimension(i, 0.0f);
                    } else {
                        valueFrom2 = typedArray.getFloat(i, 0.0f);
                    }
                    if (hasTo) {
                        if (toType3 == 5) {
                            valueTo4 = typedArray.getDimension(valueToId, 0.0f);
                        } else {
                            valueTo4 = typedArray.getFloat(valueToId, 0.0f);
                        }
                        returnValue2 = PropertyValuesHolder.ofFloat(str, valueFrom2, valueTo4);
                    } else {
                        int i7 = valueToId;
                        propertyValuesHolder = PropertyValuesHolder.ofFloat(str, valueFrom2);
                    }
                } else {
                    int i8 = valueToId;
                    if (toType3 == 5) {
                        valueTo3 = typedArray.getDimension(i8, 0.0f);
                    } else {
                        valueTo3 = typedArray.getFloat(i8, 0.0f);
                    }
                    propertyValuesHolder = PropertyValuesHolder.ofFloat(str, valueTo3);
                }
                returnValue2 = propertyValuesHolder;
            } else {
                int toType4 = toType3;
                int i9 = valueToId;
                if (hasFrom) {
                    if (fromType == 5) {
                        valueFrom = (int) typedArray.getDimension(i, 0.0f);
                    } else if (isColorType(fromType)) {
                        valueFrom = typedArray.getColor(i, 0);
                    } else {
                        valueFrom = typedArray.getInt(i, 0);
                    }
                    int valueFrom3 = valueFrom;
                    if (hasTo) {
                        if (toType4 == 5) {
                            valueTo2 = (int) typedArray.getDimension(i9, 0.0f);
                            c2 = 0;
                        } else if (isColorType(toType4)) {
                            c2 = 0;
                            valueTo2 = typedArray.getColor(i9, 0);
                        } else {
                            c2 = 0;
                            valueTo2 = typedArray.getInt(i9, 0);
                        }
                        int[] iArr = new int[2];
                        iArr[c2] = valueFrom3;
                        iArr[1] = valueTo2;
                        returnValue2 = PropertyValuesHolder.ofInt(str, iArr);
                    } else {
                        returnValue2 = PropertyValuesHolder.ofInt(str, valueFrom3);
                    }
                } else if (hasTo) {
                    if (toType4 == 5) {
                        valueTo = (int) typedArray.getDimension(i9, 0.0f);
                        c = 0;
                    } else if (isColorType(toType4)) {
                        c = 0;
                        valueTo = typedArray.getColor(i9, 0);
                    } else {
                        c = 0;
                        valueTo = typedArray.getInt(i9, 0);
                    }
                    int[] iArr2 = new int[1];
                    iArr2[c] = valueTo;
                    returnValue2 = PropertyValuesHolder.ofInt(str, iArr2);
                } else {
                    returnValue2 = null;
                }
            }
            if (!(returnValue == null || evaluator2 == null)) {
                returnValue.setEvaluator(evaluator2);
            }
        }
        return returnValue;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator anim, TypedArray arrayAnimator, TypedArray arrayObjectAnimator, float pixelSize) {
        long duration = (long) arrayAnimator.getInt(1, 300);
        long startDelay = (long) arrayAnimator.getInt(2, 0);
        int valueType = arrayAnimator.getInt(7, 4);
        if (valueType == 4) {
            valueType = inferValueTypeFromValues(arrayAnimator, 5, 6);
        }
        PropertyValuesHolder pvh = getPVH(arrayAnimator, valueType, 5, 6, "");
        if (pvh != null) {
            anim.setValues(pvh);
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        if (arrayAnimator.hasValue(3)) {
            anim.setRepeatCount(arrayAnimator.getInt(3, 0));
        }
        if (arrayAnimator.hasValue(4)) {
            anim.setRepeatMode(arrayAnimator.getInt(4, 1));
        }
        if (arrayObjectAnimator != null) {
            setupObjectAnimator(anim, arrayObjectAnimator, valueType, pixelSize);
        }
    }

    private static TypeEvaluator setupAnimatorForPath(ValueAnimator anim, TypedArray arrayAnimator) {
        String fromString = arrayAnimator.getString(5);
        String toString = arrayAnimator.getString(6);
        PathParser.PathData pathDataFrom = fromString == null ? null : new PathParser.PathData(fromString);
        PathParser.PathData pathDataTo = toString == null ? null : new PathParser.PathData(toString);
        if (pathDataFrom != null) {
            if (pathDataTo != null) {
                anim.setObjectValues(pathDataFrom, pathDataTo);
                if (!PathParser.canMorph(pathDataFrom, pathDataTo)) {
                    throw new InflateException(arrayAnimator.getPositionDescription() + " Can't morph from " + fromString + " to " + toString);
                }
            } else {
                anim.setObjectValues(pathDataFrom);
            }
            return new PathDataEvaluator();
        } else if (pathDataTo == null) {
            return null;
        } else {
            anim.setObjectValues(pathDataTo);
            return new PathDataEvaluator();
        }
    }

    private static void setupObjectAnimator(ValueAnimator anim, TypedArray arrayObjectAnimator, int valueType, float pixelSize) {
        Keyframes yKeyframes;
        Keyframes xKeyframes;
        TypedArray typedArray = arrayObjectAnimator;
        int valueType2 = valueType;
        ObjectAnimator oa = (ObjectAnimator) anim;
        String pathData = typedArray.getString(1);
        if (pathData != null) {
            String propertyXName = typedArray.getString(2);
            String propertyYName = typedArray.getString(3);
            if (valueType2 == 2 || valueType2 == 4) {
                valueType2 = 0;
            }
            if (propertyXName == null && propertyYName == null) {
                throw new InflateException(arrayObjectAnimator.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            PathKeyframes keyframeSet = KeyframeSet.ofPath(PathParser.createPathFromPathData(pathData), 0.5f * pixelSize);
            if (valueType2 == 0) {
                xKeyframes = keyframeSet.createXFloatKeyframes();
                yKeyframes = keyframeSet.createYFloatKeyframes();
            } else {
                xKeyframes = keyframeSet.createXIntKeyframes();
                yKeyframes = keyframeSet.createYIntKeyframes();
            }
            PropertyValuesHolder x = null;
            PropertyValuesHolder y = null;
            if (propertyXName != null) {
                x = PropertyValuesHolder.ofKeyframes(propertyXName, xKeyframes);
            }
            if (propertyYName != null) {
                y = PropertyValuesHolder.ofKeyframes(propertyYName, yKeyframes);
            }
            if (x == null) {
                oa.setValues(y);
            } else if (y == null) {
                oa.setValues(x);
            } else {
                oa.setValues(x, y);
            }
        } else {
            oa.setPropertyName(typedArray.getString(0));
        }
    }

    private static void setupValues(ValueAnimator anim, TypedArray arrayAnimator, boolean getFloats, boolean hasFrom, int fromType, boolean hasTo, int toType) {
        int valueTo;
        int valueFrom;
        int valueTo2;
        float valueTo3;
        float valueFrom2;
        float valueTo4;
        if (getFloats) {
            if (hasFrom) {
                if (fromType == 5) {
                    valueFrom2 = arrayAnimator.getDimension(5, 0.0f);
                } else {
                    valueFrom2 = arrayAnimator.getFloat(5, 0.0f);
                }
                if (hasTo) {
                    if (toType == 5) {
                        valueTo4 = arrayAnimator.getDimension(6, 0.0f);
                    } else {
                        valueTo4 = arrayAnimator.getFloat(6, 0.0f);
                    }
                    anim.setFloatValues(valueFrom2, valueTo4);
                    return;
                }
                anim.setFloatValues(valueFrom2);
                return;
            }
            if (toType == 5) {
                valueTo3 = arrayAnimator.getDimension(6, 0.0f);
            } else {
                valueTo3 = arrayAnimator.getFloat(6, 0.0f);
            }
            anim.setFloatValues(valueTo3);
        } else if (hasFrom) {
            if (fromType == 5) {
                valueFrom = (int) arrayAnimator.getDimension(5, 0.0f);
            } else if (isColorType(fromType)) {
                valueFrom = arrayAnimator.getColor(5, 0);
            } else {
                valueFrom = arrayAnimator.getInt(5, 0);
            }
            if (hasTo) {
                if (toType == 5) {
                    valueTo2 = (int) arrayAnimator.getDimension(6, 0.0f);
                } else if (isColorType(toType)) {
                    valueTo2 = arrayAnimator.getColor(6, 0);
                } else {
                    valueTo2 = arrayAnimator.getInt(6, 0);
                }
                anim.setIntValues(valueFrom, valueTo2);
                return;
            }
            anim.setIntValues(valueFrom);
        } else if (hasTo) {
            if (toType == 5) {
                valueTo = (int) arrayAnimator.getDimension(6, 0.0f);
            } else if (isColorType(toType)) {
                valueTo = arrayAnimator.getColor(6, 0);
            } else {
                valueTo = arrayAnimator.getInt(6, 0);
            }
            anim.setIntValues(valueTo);
        }
    }

    private static Animator createAnimatorFromXml(Resources res, Resources.Theme theme, XmlPullParser parser, float pixelSize) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(res, theme, parser, Xml.asAttributeSet(parser), (AnimatorSet) null, 0, pixelSize);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.animation.Animator createAnimatorFromXml(android.content.res.Resources r20, android.content.res.Resources.Theme r21, org.xmlpull.v1.XmlPullParser r22, android.util.AttributeSet r23, android.animation.AnimatorSet r24, int r25, float r26) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r7 = r20
            r8 = r21
            r9 = r23
            r10 = r24
            r11 = r26
            r0 = 0
            r1 = 0
            int r2 = r22.getDepth()
            r12 = r1
        L_0x0011:
            r13 = r2
            int r1 = r22.next()
            r14 = r1
            r2 = 3
            if (r1 != r2) goto L_0x0025
            int r1 = r22.getDepth()
            if (r1 <= r13) goto L_0x0021
            goto L_0x0025
        L_0x0021:
            r2 = r22
            goto L_0x00ee
        L_0x0025:
            r1 = 1
            if (r14 == r1) goto L_0x0021
            r1 = 2
            if (r14 == r1) goto L_0x002e
        L_0x002c:
            r2 = r13
            goto L_0x0011
        L_0x002e:
            java.lang.String r15 = r22.getName()
            r16 = 0
            java.lang.String r1 = "objectAnimator"
            boolean r1 = r15.equals(r1)
            if (r1 == 0) goto L_0x0045
            android.animation.ObjectAnimator r0 = loadObjectAnimator(r7, r8, r9, r11)
        L_0x0041:
            r2 = r22
            goto L_0x00c0
        L_0x0045:
            java.lang.String r1 = "animator"
            boolean r1 = r15.equals(r1)
            if (r1 == 0) goto L_0x0053
            r1 = 0
            android.animation.ValueAnimator r0 = loadAnimator(r7, r8, r9, r1, r11)
            goto L_0x0041
        L_0x0053:
            java.lang.String r1 = "set"
            boolean r1 = r15.equals(r1)
            if (r1 == 0) goto L_0x009b
            android.animation.AnimatorSet r1 = new android.animation.AnimatorSet
            r1.<init>()
            r6 = r1
            r0 = 0
            if (r8 == 0) goto L_0x006c
            int[] r1 = com.android.internal.R.styleable.AnimatorSet
            android.content.res.TypedArray r1 = r8.obtainStyledAttributes(r9, r1, r0, r0)
            goto L_0x0072
        L_0x006c:
            int[] r1 = com.android.internal.R.styleable.AnimatorSet
            android.content.res.TypedArray r1 = r7.obtainAttributes(r9, r1)
        L_0x0072:
            r5 = r1
            int r1 = r5.getChangingConfigurations()
            r6.appendChangingConfigurations(r1)
            int r17 = r5.getInt(r0, r0)
            r4 = r6
            android.animation.AnimatorSet r4 = (android.animation.AnimatorSet) r4
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r23
            r18 = r5
            r5 = r17
            r19 = r6
            r6 = r26
            createAnimatorFromXml(r0, r1, r2, r3, r4, r5, r6)
            r18.recycle()
            r0 = r19
            goto L_0x00c0
        L_0x009b:
            java.lang.String r1 = "propertyValuesHolder"
            boolean r1 = r15.equals(r1)
            if (r1 == 0) goto L_0x00d1
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r22)
            r2 = r22
            android.animation.PropertyValuesHolder[] r1 = loadValues(r7, r8, r2, r1)
            if (r1 == 0) goto L_0x00bd
            if (r0 == 0) goto L_0x00bd
            boolean r3 = r0 instanceof android.animation.ValueAnimator
            if (r3 == 0) goto L_0x00bd
            r3 = r0
            android.animation.ValueAnimator r3 = (android.animation.ValueAnimator) r3
            r3.setValues(r1)
        L_0x00bd:
            r16 = 1
        L_0x00c0:
            if (r10 == 0) goto L_0x00cf
            if (r16 != 0) goto L_0x00cf
            if (r12 != 0) goto L_0x00cc
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r12 = r1
        L_0x00cc:
            r12.add(r0)
        L_0x00cf:
            goto L_0x002c
        L_0x00d1:
            r2 = r22
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unknown animator name: "
            r3.append(r4)
            java.lang.String r4 = r22.getName()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            throw r1
        L_0x00ee:
            if (r10 == 0) goto L_0x0119
            if (r12 == 0) goto L_0x0119
            int r1 = r12.size()
            android.animation.Animator[] r1 = new android.animation.Animator[r1]
            r3 = 0
            java.util.Iterator r4 = r12.iterator()
        L_0x00fd:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0110
            java.lang.Object r5 = r4.next()
            android.animation.Animator r5 = (android.animation.Animator) r5
            int r6 = r3 + 1
            r1[r3] = r5
            r3 = r6
            goto L_0x00fd
        L_0x0110:
            if (r25 != 0) goto L_0x0116
            r10.playTogether((android.animation.Animator[]) r1)
            goto L_0x0119
        L_0x0116:
            r10.playSequentially((android.animation.Animator[]) r1)
        L_0x0119:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.animation.AnimatorInflater.createAnimatorFromXml(android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.animation.AnimatorSet, int, float):android.animation.Animator");
    }

    private static PropertyValuesHolder[] loadValues(Resources res, Resources.Theme theme, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        int i;
        TypedArray a;
        ArrayList<PropertyValuesHolder> values = null;
        while (true) {
            int eventType = parser.getEventType();
            int type = eventType;
            if (eventType == 3 || type == 1) {
                PropertyValuesHolder[] valuesArray = null;
            } else if (type != 2) {
                parser.next();
            } else {
                if (parser.getName().equals("propertyValuesHolder")) {
                    if (theme != null) {
                        a = theme.obtainStyledAttributes(attrs, R.styleable.PropertyValuesHolder, 0, 0);
                    } else {
                        a = res.obtainAttributes(attrs, R.styleable.PropertyValuesHolder);
                    }
                    String propertyName = a.getString(3);
                    int valueType = a.getInt(2, 4);
                    PropertyValuesHolder pvh = loadPvh(res, theme, parser, propertyName, valueType);
                    if (pvh == null) {
                        pvh = getPVH(a, valueType, 0, 1, propertyName);
                    }
                    if (pvh != null) {
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        values.add(pvh);
                    }
                    a.recycle();
                }
                parser.next();
            }
        }
        PropertyValuesHolder[] valuesArray2 = null;
        if (values != null) {
            int count = values.size();
            valuesArray2 = new PropertyValuesHolder[count];
            for (i = 0; i < count; i++) {
                valuesArray2[i] = values.get(i);
            }
        }
        return valuesArray2;
    }

    private static int inferValueTypeOfKeyframe(Resources res, Resources.Theme theme, AttributeSet attrs) {
        TypedArray a;
        int valueType = 0;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, R.styleable.Keyframe, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, R.styleable.Keyframe);
        }
        TypedValue keyframeValue = a.peekValue(0);
        if ((keyframeValue != null) && isColorType(keyframeValue.type)) {
            valueType = 3;
        }
        a.recycle();
        return valueType;
    }

    private static int inferValueTypeFromValues(TypedArray styledAttributes, int valueFromId, int valueToId) {
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        boolean hasTo = true;
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        if (tvTo == null) {
            hasTo = false;
        }
        int toType = hasTo ? tvTo.type : 0;
        if ((!hasFrom || !isColorType(fromType)) && (!hasTo || !isColorType(toType))) {
            return 0;
        }
        return 3;
    }

    private static void dumpKeyframes(Object[] keyframes, String header) {
        if (keyframes != null && keyframes.length != 0) {
            Log.d(TAG, header);
            int count = keyframes.length;
            for (int i = 0; i < count; i++) {
                Keyframe keyframe = keyframes[i];
                StringBuilder sb = new StringBuilder();
                sb.append("Keyframe ");
                sb.append(i);
                sb.append(": fraction ");
                sb.append(keyframe.getFraction() < 0.0f ? "null" : Float.valueOf(keyframe.getFraction()));
                sb.append(", , value : ");
                sb.append(keyframe.hasValue() ? keyframe.getValue() : "null");
                Log.d(TAG, sb.toString());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.animation.PropertyValuesHolder loadPvh(android.content.res.Resources r19, android.content.res.Resources.Theme r20, org.xmlpull.v1.XmlPullParser r21, java.lang.String r22, int r23) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r0 = r19
            r1 = r20
            r2 = 0
            r3 = 0
            r4 = r23
        L_0x0008:
            int r5 = r21.next()
            r6 = r5
            r7 = 3
            if (r5 == r7) goto L_0x0043
            r5 = 1
            if (r6 == r5) goto L_0x0043
            java.lang.String r5 = r21.getName()
            java.lang.String r7 = "keyframe"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x0042
            r7 = 4
            if (r4 != r7) goto L_0x002a
            android.util.AttributeSet r7 = android.util.Xml.asAttributeSet(r21)
            int r4 = inferValueTypeOfKeyframe(r0, r1, r7)
        L_0x002a:
            android.util.AttributeSet r7 = android.util.Xml.asAttributeSet(r21)
            android.animation.Keyframe r7 = loadKeyframe(r0, r1, r7, r4)
            if (r7 == 0) goto L_0x003f
            if (r3 != 0) goto L_0x003c
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r3 = r8
        L_0x003c:
            r3.add(r7)
        L_0x003f:
            r21.next()
        L_0x0042:
            goto L_0x0008
        L_0x0043:
            if (r3 == 0) goto L_0x0110
            int r5 = r3.size()
            r8 = r5
            if (r5 <= 0) goto L_0x0110
            r5 = 0
            java.lang.Object r9 = r3.get(r5)
            android.animation.Keyframe r9 = (android.animation.Keyframe) r9
            int r10 = r8 + -1
            java.lang.Object r10 = r3.get(r10)
            android.animation.Keyframe r10 = (android.animation.Keyframe) r10
            float r11 = r10.getFraction()
            r12 = 1065353216(0x3f800000, float:1.0)
            int r13 = (r11 > r12 ? 1 : (r11 == r12 ? 0 : -1))
            r14 = 0
            if (r13 >= 0) goto L_0x007b
            int r13 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r13 >= 0) goto L_0x006e
            r10.setFraction(r12)
            goto L_0x007b
        L_0x006e:
            int r13 = r3.size()
            android.animation.Keyframe r15 = createNewKeyframe(r10, r12)
            r3.add(r13, r15)
            int r8 = r8 + 1
        L_0x007b:
            float r13 = r9.getFraction()
            int r15 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r15 == 0) goto L_0x0094
            int r15 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r15 >= 0) goto L_0x008b
            r9.setFraction(r14)
            goto L_0x0094
        L_0x008b:
            android.animation.Keyframe r15 = createNewKeyframe(r9, r14)
            r3.add(r5, r15)
            int r8 = r8 + 1
        L_0x0094:
            android.animation.Keyframe[] r15 = new android.animation.Keyframe[r8]
            r3.toArray(r15)
        L_0x009a:
            if (r5 >= r8) goto L_0x00ff
            r7 = r15[r5]
            float r16 = r7.getFraction()
            int r16 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            if (r16 >= 0) goto L_0x00f1
            if (r5 != 0) goto L_0x00ac
            r7.setFraction(r14)
            goto L_0x00f1
        L_0x00ac:
            int r14 = r8 + -1
            if (r5 != r14) goto L_0x00b6
            r7.setFraction(r12)
            r16 = 0
            goto L_0x00f3
        L_0x00b6:
            r14 = r5
            r16 = r5
            int r17 = r14 + 1
            r12 = r16
        L_0x00bd:
            r18 = r17
            int r0 = r8 + -1
            r1 = r18
            if (r1 >= r0) goto L_0x00da
            r0 = r15[r1]
            float r0 = r0.getFraction()
            r16 = 0
            int r0 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r0 < 0) goto L_0x00d2
            goto L_0x00dc
        L_0x00d2:
            r12 = r1
            int r17 = r1 + 1
            r0 = r19
            r1 = r20
            goto L_0x00bd
        L_0x00da:
            r16 = 0
        L_0x00dc:
            int r0 = r12 + 1
            r0 = r15[r0]
            float r0 = r0.getFraction()
            int r1 = r14 + -1
            r1 = r15[r1]
            float r1 = r1.getFraction()
            float r0 = r0 - r1
            distributeKeyframes(r15, r0, r14, r12)
            goto L_0x00f3
        L_0x00f1:
            r16 = r14
        L_0x00f3:
            int r5 = r5 + 1
            r14 = r16
            r0 = r19
            r1 = r20
            r7 = 3
            r12 = 1065353216(0x3f800000, float:1.0)
            goto L_0x009a
        L_0x00ff:
            r0 = r22
            android.animation.PropertyValuesHolder r2 = android.animation.PropertyValuesHolder.ofKeyframe((java.lang.String) r0, (android.animation.Keyframe[]) r15)
            r1 = 3
            if (r4 != r1) goto L_0x0112
            android.animation.ArgbEvaluator r1 = android.animation.ArgbEvaluator.getInstance()
            r2.setEvaluator(r1)
            goto L_0x0112
        L_0x0110:
            r0 = r22
        L_0x0112:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.animation.AnimatorInflater.loadPvh(android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, java.lang.String, int):android.animation.PropertyValuesHolder");
    }

    private static Keyframe createNewKeyframe(Keyframe sampleKeyframe, float fraction) {
        if (sampleKeyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(fraction);
        }
        if (sampleKeyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt(fraction);
        }
        return Keyframe.ofObject(fraction);
    }

    private static void distributeKeyframes(Keyframe[] keyframes, float gap, int startIndex, int endIndex) {
        float increment = gap / ((float) ((endIndex - startIndex) + 2));
        for (int i = startIndex; i <= endIndex; i++) {
            keyframes[i].setFraction(keyframes[i - 1].getFraction() + increment);
        }
    }

    private static Keyframe loadKeyframe(Resources res, Resources.Theme theme, AttributeSet attrs, int valueType) throws XmlPullParserException, IOException {
        TypedArray a;
        Keyframe keyframe;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, R.styleable.Keyframe, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, R.styleable.Keyframe);
        }
        Keyframe keyframe2 = null;
        float fraction = a.getFloat(3, -1.0f);
        TypedValue keyframeValue = a.peekValue(0);
        boolean hasValue = keyframeValue != null;
        if (valueType == 4) {
            if (!hasValue || !isColorType(keyframeValue.type)) {
                valueType = 0;
            } else {
                valueType = 3;
            }
        }
        if (hasValue) {
            if (valueType != 3) {
                switch (valueType) {
                    case 0:
                        keyframe2 = Keyframe.ofFloat(fraction, a.getFloat(0, 0.0f));
                        break;
                    case 1:
                        break;
                }
            }
            keyframe2 = Keyframe.ofInt(fraction, a.getInt(0, 0));
        } else {
            if (valueType == 0) {
                keyframe = Keyframe.ofFloat(fraction);
            } else {
                keyframe = Keyframe.ofInt(fraction);
            }
            keyframe2 = keyframe;
        }
        int resID = a.getResourceId(1, 0);
        if (resID > 0) {
            keyframe2.setInterpolator(AnimationUtils.loadInterpolator(res, theme, resID));
        }
        a.recycle();
        return keyframe2;
    }

    private static ObjectAnimator loadObjectAnimator(Resources res, Resources.Theme theme, AttributeSet attrs, float pathErrorScale) throws Resources.NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(res, theme, attrs, anim, pathErrorScale);
        return anim;
    }

    private static ValueAnimator loadAnimator(Resources res, Resources.Theme theme, AttributeSet attrs, ValueAnimator anim, float pathErrorScale) throws Resources.NotFoundException {
        TypedArray arrayAnimator;
        TypedArray arrayObjectAnimator = null;
        if (theme != null) {
            arrayAnimator = theme.obtainStyledAttributes(attrs, R.styleable.Animator, 0, 0);
        } else {
            arrayAnimator = res.obtainAttributes(attrs, R.styleable.Animator);
        }
        if (anim != null) {
            if (theme != null) {
                arrayObjectAnimator = theme.obtainStyledAttributes(attrs, R.styleable.PropertyAnimator, 0, 0);
            } else {
                arrayObjectAnimator = res.obtainAttributes(attrs, R.styleable.PropertyAnimator);
            }
            anim.appendChangingConfigurations(arrayObjectAnimator.getChangingConfigurations());
        }
        if (anim == null) {
            anim = new ValueAnimator();
        }
        anim.appendChangingConfigurations(arrayAnimator.getChangingConfigurations());
        parseAnimatorFromTypeArray(anim, arrayAnimator, arrayObjectAnimator, pathErrorScale);
        int resID = arrayAnimator.getResourceId(0, 0);
        if (resID > 0) {
            Interpolator interpolator = AnimationUtils.loadInterpolator(res, theme, resID);
            if (interpolator instanceof BaseInterpolator) {
                anim.appendChangingConfigurations(((BaseInterpolator) interpolator).getChangingConfiguration());
            }
            anim.setInterpolator(interpolator);
        }
        arrayAnimator.recycle();
        if (arrayObjectAnimator != null) {
            arrayObjectAnimator.recycle();
        }
        return anim;
    }

    private static int getChangingConfigs(Resources resources, int id) {
        int i;
        synchronized (sTmpTypedValue) {
            resources.getValue(id, sTmpTypedValue, true);
            i = sTmpTypedValue.changingConfigurations;
        }
        return i;
    }

    private static boolean isColorType(int type) {
        return type >= 28 && type <= 31;
    }
}
