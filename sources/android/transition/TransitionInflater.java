package android.transition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import com.android.internal.R;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final ArrayMap<String, Constructor> sConstructors = new ArrayMap<>();
    private Context mContext;

    private TransitionInflater(Context context) {
        this.mContext = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    public Transition inflateTransition(int resource) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            Transition createTransitionFromXml = createTransitionFromXml(parser, Xml.asAttributeSet(parser), (Transition) null);
            parser.close();
            return createTransitionFromXml;
        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException(e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (IOException e2) {
            InflateException ex2 = new InflateException(parser.getPositionDescription() + ": " + e2.getMessage());
            ex2.initCause(e2);
            throw ex2;
        } catch (Throwable th) {
            parser.close();
            throw th;
        }
    }

    public TransitionManager inflateTransitionManager(int resource, ViewGroup sceneRoot) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            TransitionManager createTransitionManagerFromXml = createTransitionManagerFromXml(parser, Xml.asAttributeSet(parser), sceneRoot);
            parser.close();
            return createTransitionManagerFromXml;
        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException(e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (IOException e2) {
            InflateException ex2 = new InflateException(parser.getPositionDescription() + ": " + e2.getMessage());
            ex2.initCause(e2);
            throw ex2;
        } catch (Throwable th) {
            parser.close();
            throw th;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v42, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.transition.Transition} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.transition.Transition createTransitionFromXml(org.xmlpull.v1.XmlPullParser r9, android.util.AttributeSet r10, android.transition.Transition r11) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r8 = this;
            r0 = 0
            int r1 = r9.getDepth()
            boolean r2 = r11 instanceof android.transition.TransitionSet
            if (r2 == 0) goto L_0x000d
            r2 = r11
            android.transition.TransitionSet r2 = (android.transition.TransitionSet) r2
            goto L_0x000e
        L_0x000d:
            r2 = 0
        L_0x000e:
            int r3 = r9.next()
            r4 = r3
            r5 = 3
            if (r3 != r5) goto L_0x001c
            int r3 = r9.getDepth()
            if (r3 <= r1) goto L_0x018a
        L_0x001c:
            r3 = 1
            if (r4 == r3) goto L_0x018a
            r3 = 2
            if (r4 == r3) goto L_0x0023
            goto L_0x000e
        L_0x0023:
            java.lang.String r3 = r9.getName()
            java.lang.String r5 = "fade"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0039
            android.transition.Fade r5 = new android.transition.Fade
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x0039:
            java.lang.String r5 = "changeBounds"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x004b
            android.transition.ChangeBounds r5 = new android.transition.ChangeBounds
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x004b:
            java.lang.String r5 = "slide"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x005e
            android.transition.Slide r5 = new android.transition.Slide
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x005e:
            java.lang.String r5 = "explode"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0070
            android.transition.Explode r5 = new android.transition.Explode
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x0070:
            java.lang.String r5 = "changeImageTransform"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0082
            android.transition.ChangeImageTransform r5 = new android.transition.ChangeImageTransform
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x0082:
            java.lang.String r5 = "changeTransform"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0094
            android.transition.ChangeTransform r5 = new android.transition.ChangeTransform
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x0094:
            java.lang.String r5 = "changeClipBounds"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x00a6
            android.transition.ChangeClipBounds r5 = new android.transition.ChangeClipBounds
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x00a6:
            java.lang.String r5 = "autoTransition"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x00b8
            android.transition.AutoTransition r5 = new android.transition.AutoTransition
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x00b8:
            java.lang.String r5 = "recolor"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x00cb
            android.transition.Recolor r5 = new android.transition.Recolor
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x00cb:
            java.lang.String r5 = "changeScroll"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x00dd
            android.transition.ChangeScroll r5 = new android.transition.ChangeScroll
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x00dd:
            java.lang.String r5 = "transitionSet"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x00ef
            android.transition.TransitionSet r5 = new android.transition.TransitionSet
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r0 = r5
            goto L_0x0150
        L_0x00ef:
            java.lang.String r5 = "transition"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0105
            java.lang.Class<android.transition.Transition> r5 = android.transition.Transition.class
            java.lang.String r6 = "transition"
            java.lang.Object r5 = r8.createCustom(r10, r5, r6)
            r0 = r5
            android.transition.Transition r0 = (android.transition.Transition) r0
            goto L_0x0150
        L_0x0105:
            java.lang.String r5 = "targets"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0112
            r8.getTargetIds(r9, r10, r11)
            goto L_0x0150
        L_0x0112:
            java.lang.String r5 = "arcMotion"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x0125
            android.transition.ArcMotion r5 = new android.transition.ArcMotion
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r11.setPathMotion(r5)
            goto L_0x0150
        L_0x0125:
            java.lang.String r5 = "pathMotion"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x013d
            java.lang.Class<android.transition.PathMotion> r5 = android.transition.PathMotion.class
            java.lang.String r6 = "pathMotion"
            java.lang.Object r5 = r8.createCustom(r10, r5, r6)
            android.transition.PathMotion r5 = (android.transition.PathMotion) r5
            r11.setPathMotion(r5)
            goto L_0x0150
        L_0x013d:
            java.lang.String r5 = "patternPathMotion"
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x016f
            android.transition.PatternPathMotion r5 = new android.transition.PatternPathMotion
            android.content.Context r6 = r8.mContext
            r5.<init>(r6, r10)
            r11.setPathMotion(r5)
        L_0x0150:
            if (r0 == 0) goto L_0x016d
            boolean r5 = r9.isEmptyElementTag()
            if (r5 != 0) goto L_0x015b
            r8.createTransitionFromXml(r9, r10, r0)
        L_0x015b:
            if (r2 == 0) goto L_0x0162
            r2.addTransition(r0)
            r0 = 0
            goto L_0x016d
        L_0x0162:
            if (r11 != 0) goto L_0x0165
            goto L_0x016d
        L_0x0165:
            android.view.InflateException r5 = new android.view.InflateException
            java.lang.String r6 = "Could not add transition to another transition."
            r5.<init>((java.lang.String) r6)
            throw r5
        L_0x016d:
            goto L_0x000e
        L_0x016f:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unknown scene name: "
            r6.append(r7)
            java.lang.String r7 = r9.getName()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x018a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.TransitionInflater.createTransitionFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.transition.Transition):android.transition.Transition");
    }

    private Object createCustom(AttributeSet attrs, Class expectedType, String tag) {
        Object newInstance;
        Class c;
        String className = attrs.getAttributeValue((String) null, "class");
        if (className != null) {
            try {
                synchronized (sConstructors) {
                    Constructor constructor = sConstructors.get(className);
                    if (constructor == null && (c = this.mContext.getClassLoader().loadClass(className).asSubclass(expectedType)) != null) {
                        constructor = c.getConstructor(sConstructorSignature);
                        constructor.setAccessible(true);
                        sConstructors.put(className, constructor);
                    }
                    newInstance = constructor.newInstance(new Object[]{this.mContext, attrs});
                }
                return newInstance;
            } catch (InstantiationException e) {
                throw new InflateException("Could not instantiate " + expectedType + " class " + className, e);
            } catch (ClassNotFoundException e2) {
                throw new InflateException("Could not instantiate " + expectedType + " class " + className, e2);
            } catch (InvocationTargetException e3) {
                throw new InflateException("Could not instantiate " + expectedType + " class " + className, e3);
            } catch (NoSuchMethodException e4) {
                throw new InflateException("Could not instantiate " + expectedType + " class " + className, e4);
            } catch (IllegalAccessException e5) {
                throw new InflateException("Could not instantiate " + expectedType + " class " + className, e5);
            }
        } else {
            throw new InflateException(tag + " tag must have a 'class' attribute");
        }
    }

    private void getTargetIds(XmlPullParser parser, AttributeSet attrs, Transition transition) throws XmlPullParserException, IOException {
        int depth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if ((next == 3 && parser.getDepth() <= depth) || type == 1) {
                return;
            }
            if (type == 2) {
                if (parser.getName().equals("target")) {
                    TypedArray a = this.mContext.obtainStyledAttributes(attrs, R.styleable.TransitionTarget);
                    int id = a.getResourceId(1, 0);
                    if (id != 0) {
                        transition.addTarget(id);
                    } else {
                        int resourceId = a.getResourceId(2, 0);
                        int id2 = resourceId;
                        if (resourceId != 0) {
                            transition.excludeTarget(id2, true);
                        } else {
                            String string = a.getString(4);
                            String transitionName = string;
                            if (string != null) {
                                transition.addTarget(transitionName);
                            } else {
                                String string2 = a.getString(5);
                                String transitionName2 = string2;
                                if (string2 != null) {
                                    transition.excludeTarget(transitionName2, true);
                                } else {
                                    String className = a.getString(3);
                                    if (className != null) {
                                        try {
                                            transition.excludeTarget(Class.forName(className), true);
                                        } catch (ClassNotFoundException e) {
                                            a.recycle();
                                            throw new RuntimeException("Could not create " + className, e);
                                        }
                                    } else {
                                        String string3 = a.getString(0);
                                        String className2 = string3;
                                        if (string3 != null) {
                                            transition.addTarget(Class.forName(className2));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    a.recycle();
                } else {
                    throw new RuntimeException("Unknown scene name: " + parser.getName());
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0058, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.transition.TransitionManager createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser r8, android.util.AttributeSet r9, android.view.ViewGroup r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r7 = this;
            int r0 = r8.getDepth()
            r1 = 0
        L_0x0005:
            int r2 = r8.next()
            r3 = r2
            r4 = 3
            if (r2 != r4) goto L_0x0013
            int r2 = r8.getDepth()
            if (r2 <= r0) goto L_0x0058
        L_0x0013:
            r2 = 1
            if (r3 == r2) goto L_0x0058
            r2 = 2
            if (r3 == r2) goto L_0x001a
            goto L_0x0005
        L_0x001a:
            java.lang.String r2 = r8.getName()
            java.lang.String r4 = "transitionManager"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x002e
            android.transition.TransitionManager r4 = new android.transition.TransitionManager
            r4.<init>()
            r1 = r4
            goto L_0x003c
        L_0x002e:
            java.lang.String r4 = "transition"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x003d
            if (r1 == 0) goto L_0x003d
            r7.loadTransition(r9, r10, r1)
        L_0x003c:
            goto L_0x0005
        L_0x003d:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Unknown scene name: "
            r5.append(r6)
            java.lang.String r6 = r8.getName()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x0058:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.TransitionInflater.createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):android.transition.TransitionManager");
    }

    private void loadTransition(AttributeSet attrs, ViewGroup sceneRoot, TransitionManager transitionManager) throws Resources.NotFoundException {
        Transition transition;
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, R.styleable.TransitionManager);
        int transitionId = a.getResourceId(2, -1);
        int fromId = a.getResourceId(0, -1);
        Scene toScene = null;
        Scene fromScene = fromId < 0 ? null : Scene.getSceneForLayout(sceneRoot, fromId, this.mContext);
        int toId = a.getResourceId(1, -1);
        if (toId >= 0) {
            toScene = Scene.getSceneForLayout(sceneRoot, toId, this.mContext);
        }
        if (transitionId >= 0 && (transition = inflateTransition(transitionId)) != null) {
            if (toScene == null) {
                throw new RuntimeException("No toScene for transition ID " + transitionId);
            } else if (fromScene == null) {
                transitionManager.setTransition(toScene, transition);
            } else {
                transitionManager.setTransition(fromScene, toScene, transition);
            }
        }
        a.recycle();
    }
}
