package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableInflater {
    private static final HashMap<String, Constructor<? extends Drawable>> CONSTRUCTOR_MAP = new HashMap<>();
    @UnsupportedAppUsage
    private final ClassLoader mClassLoader;
    private final Resources mRes;

    public static Drawable loadDrawable(Context context, int id) {
        return loadDrawable(context.getResources(), context.getTheme(), id);
    }

    public static Drawable loadDrawable(Resources resources, Resources.Theme theme, int id) {
        return resources.getDrawable(id, theme);
    }

    public DrawableInflater(Resources res, ClassLoader classLoader) {
        this.mRes = res;
        this.mClassLoader = classLoader;
    }

    public Drawable inflateFromXml(String name, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        return inflateFromXmlForDensity(name, parser, attrs, 0, theme);
    }

    /* access modifiers changed from: package-private */
    public Drawable inflateFromXmlForDensity(String name, XmlPullParser parser, AttributeSet attrs, int density, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (!name.equals("drawable") || (name = attrs.getAttributeValue((String) null, "class")) != null) {
            Drawable drawable = inflateFromTag(name);
            if (drawable == null) {
                drawable = inflateFromClass(name);
            }
            drawable.setSrcDensityOverride(density);
            drawable.inflate(this.mRes, parser, attrs, theme);
            return drawable;
        }
        throw new InflateException("<drawable> tag must specify class attribute");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable inflateFromTag(java.lang.String r2) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -2024464016: goto L_0x00e4;
                case -1724158635: goto L_0x00d9;
                case -1671889043: goto L_0x00ce;
                case -1493546681: goto L_0x00c3;
                case -1388777169: goto L_0x00b8;
                case -930826704: goto L_0x00ad;
                case -925180581: goto L_0x00a1;
                case -820387517: goto L_0x0095;
                case -510364471: goto L_0x008b;
                case -94197862: goto L_0x0081;
                case 3056464: goto L_0x0075;
                case 94842723: goto L_0x006a;
                case 100360477: goto L_0x005e;
                case 109250890: goto L_0x0051;
                case 109399969: goto L_0x0044;
                case 160680263: goto L_0x0039;
                case 1191572447: goto L_0x002d;
                case 1442046129: goto L_0x0021;
                case 2013827269: goto L_0x0015;
                case 2118620333: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x00ee
        L_0x0009:
            java.lang.String r0 = "animated-vector"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 10
            goto L_0x00ef
        L_0x0015:
            java.lang.String r0 = "animated-rotate"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 14
            goto L_0x00ef
        L_0x0021:
            java.lang.String r0 = "animated-image"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 19
            goto L_0x00ef
        L_0x002d:
            java.lang.String r0 = "selector"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 0
            goto L_0x00ef
        L_0x0039:
            java.lang.String r0 = "level-list"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 2
            goto L_0x00ef
        L_0x0044:
            java.lang.String r0 = "shape"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 8
            goto L_0x00ef
        L_0x0051:
            java.lang.String r0 = "scale"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 11
            goto L_0x00ef
        L_0x005e:
            java.lang.String r0 = "inset"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 16
            goto L_0x00ef
        L_0x006a:
            java.lang.String r0 = "color"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 7
            goto L_0x00ef
        L_0x0075:
            java.lang.String r0 = "clip"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 12
            goto L_0x00ef
        L_0x0081:
            java.lang.String r0 = "layer-list"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 3
            goto L_0x00ef
        L_0x008b:
            java.lang.String r0 = "animated-selector"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 1
            goto L_0x00ef
        L_0x0095:
            java.lang.String r0 = "vector"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 9
            goto L_0x00ef
        L_0x00a1:
            java.lang.String r0 = "rotate"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 13
            goto L_0x00ef
        L_0x00ad:
            java.lang.String r0 = "ripple"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 5
            goto L_0x00ef
        L_0x00b8:
            java.lang.String r0 = "bitmap"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 17
            goto L_0x00ef
        L_0x00c3:
            java.lang.String r0 = "animation-list"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 15
            goto L_0x00ef
        L_0x00ce:
            java.lang.String r0 = "nine-patch"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 18
            goto L_0x00ef
        L_0x00d9:
            java.lang.String r0 = "transition"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 4
            goto L_0x00ef
        L_0x00e4:
            java.lang.String r0 = "adaptive-icon"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 6
            goto L_0x00ef
        L_0x00ee:
            r0 = -1
        L_0x00ef:
            switch(r0) {
                case 0: goto L_0x0166;
                case 1: goto L_0x0160;
                case 2: goto L_0x015a;
                case 3: goto L_0x0154;
                case 4: goto L_0x014e;
                case 5: goto L_0x0148;
                case 6: goto L_0x0142;
                case 7: goto L_0x013c;
                case 8: goto L_0x0136;
                case 9: goto L_0x0130;
                case 10: goto L_0x012a;
                case 11: goto L_0x0124;
                case 12: goto L_0x011e;
                case 13: goto L_0x0118;
                case 14: goto L_0x0112;
                case 15: goto L_0x010c;
                case 16: goto L_0x0106;
                case 17: goto L_0x0100;
                case 18: goto L_0x00fa;
                case 19: goto L_0x00f4;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            r0 = 0
            return r0
        L_0x00f4:
            android.graphics.drawable.AnimatedImageDrawable r0 = new android.graphics.drawable.AnimatedImageDrawable
            r0.<init>()
            return r0
        L_0x00fa:
            android.graphics.drawable.NinePatchDrawable r0 = new android.graphics.drawable.NinePatchDrawable
            r0.<init>()
            return r0
        L_0x0100:
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable
            r0.<init>()
            return r0
        L_0x0106:
            android.graphics.drawable.InsetDrawable r0 = new android.graphics.drawable.InsetDrawable
            r0.<init>()
            return r0
        L_0x010c:
            android.graphics.drawable.AnimationDrawable r0 = new android.graphics.drawable.AnimationDrawable
            r0.<init>()
            return r0
        L_0x0112:
            android.graphics.drawable.AnimatedRotateDrawable r0 = new android.graphics.drawable.AnimatedRotateDrawable
            r0.<init>()
            return r0
        L_0x0118:
            android.graphics.drawable.RotateDrawable r0 = new android.graphics.drawable.RotateDrawable
            r0.<init>()
            return r0
        L_0x011e:
            android.graphics.drawable.ClipDrawable r0 = new android.graphics.drawable.ClipDrawable
            r0.<init>()
            return r0
        L_0x0124:
            android.graphics.drawable.ScaleDrawable r0 = new android.graphics.drawable.ScaleDrawable
            r0.<init>()
            return r0
        L_0x012a:
            android.graphics.drawable.AnimatedVectorDrawable r0 = new android.graphics.drawable.AnimatedVectorDrawable
            r0.<init>()
            return r0
        L_0x0130:
            android.graphics.drawable.VectorDrawable r0 = new android.graphics.drawable.VectorDrawable
            r0.<init>()
            return r0
        L_0x0136:
            android.graphics.drawable.GradientDrawable r0 = new android.graphics.drawable.GradientDrawable
            r0.<init>()
            return r0
        L_0x013c:
            android.graphics.drawable.ColorDrawable r0 = new android.graphics.drawable.ColorDrawable
            r0.<init>()
            return r0
        L_0x0142:
            android.graphics.drawable.AdaptiveIconDrawable r0 = new android.graphics.drawable.AdaptiveIconDrawable
            r0.<init>()
            return r0
        L_0x0148:
            android.graphics.drawable.RippleDrawable r0 = new android.graphics.drawable.RippleDrawable
            r0.<init>()
            return r0
        L_0x014e:
            android.graphics.drawable.TransitionDrawable r0 = new android.graphics.drawable.TransitionDrawable
            r0.<init>()
            return r0
        L_0x0154:
            android.graphics.drawable.LayerDrawable r0 = new android.graphics.drawable.LayerDrawable
            r0.<init>()
            return r0
        L_0x015a:
            android.graphics.drawable.LevelListDrawable r0 = new android.graphics.drawable.LevelListDrawable
            r0.<init>()
            return r0
        L_0x0160:
            android.graphics.drawable.AnimatedStateListDrawable r0 = new android.graphics.drawable.AnimatedStateListDrawable
            r0.<init>()
            return r0
        L_0x0166:
            android.graphics.drawable.StateListDrawable r0 = new android.graphics.drawable.StateListDrawable
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.DrawableInflater.inflateFromTag(java.lang.String):android.graphics.drawable.Drawable");
    }

    private Drawable inflateFromClass(String className) {
        Constructor<? extends U> constructor;
        try {
            synchronized (CONSTRUCTOR_MAP) {
                constructor = CONSTRUCTOR_MAP.get(className);
                if (constructor == null) {
                    constructor = this.mClassLoader.loadClass(className).asSubclass(Drawable.class).getConstructor(new Class[0]);
                    CONSTRUCTOR_MAP.put(className, constructor);
                }
            }
            return (Drawable) constructor.newInstance(new Object[0]);
        } catch (NoSuchMethodException e) {
            InflateException ie = new InflateException("Error inflating class " + className);
            ie.initCause(e);
            throw ie;
        } catch (ClassCastException e2) {
            InflateException ie2 = new InflateException("Class is not a Drawable " + className);
            ie2.initCause(e2);
            throw ie2;
        } catch (ClassNotFoundException e3) {
            InflateException ie3 = new InflateException("Class not found " + className);
            ie3.initCause(e3);
            throw ie3;
        } catch (Exception e4) {
            InflateException ie4 = new InflateException("Error inflating class " + className);
            ie4.initCause(e4);
            throw ie4;
        }
    }
}
