package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.R;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class LayoutInflater {
    @UnsupportedAppUsage
    private static final int[] ATTRS_THEME = {16842752};
    private static final String ATTR_LAYOUT = "layout";
    private static final ClassLoader BOOT_CLASS_LOADER = LayoutInflater.class.getClassLoader();
    private static final String COMPILED_VIEW_DEX_FILE_NAME = "/compiled_view.dex";
    private static final boolean DEBUG = false;
    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
    private static final String TAG = LayoutInflater.class.getSimpleName();
    private static final String TAG_1995 = "blink";
    private static final String TAG_INCLUDE = "include";
    private static final String TAG_MERGE = "merge";
    private static final String TAG_REQUEST_FOCUS = "requestFocus";
    private static final String TAG_TAG = "tag";
    private static final String USE_PRECOMPILED_LAYOUT = "view.precompiled_layout_enabled";
    @UnsupportedAppUsage
    static final Class<?>[] mConstructorSignature = {Context.class, AttributeSet.class};
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123769490)
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap = new HashMap<>();
    @UnsupportedAppUsage(maxTargetSdk = 28)
    final Object[] mConstructorArgs = new Object[2];
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected final Context mContext;
    @UnsupportedAppUsage
    private Factory mFactory;
    @UnsupportedAppUsage
    private Factory2 mFactory2;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private boolean mFactorySet;
    private Filter mFilter;
    private HashMap<String, Boolean> mFilterMap;
    private ClassLoader mPrecompiledClassLoader;
    @UnsupportedAppUsage
    private Factory2 mPrivateFactory;
    private TypedValue mTempValue;
    private boolean mUseCompiledView;

    public interface Factory {
        View onCreateView(String str, Context context, AttributeSet attributeSet);
    }

    public interface Factory2 extends Factory {
        View onCreateView(View view, String str, Context context, AttributeSet attributeSet);
    }

    public interface Filter {
        boolean onLoadClass(Class cls);
    }

    public abstract LayoutInflater cloneInContext(Context context);

    private static class FactoryMerger implements Factory2 {
        private final Factory mF1;
        private final Factory2 mF12;
        private final Factory mF2;
        private final Factory2 mF22;

        FactoryMerger(Factory f1, Factory2 f12, Factory f2, Factory2 f22) {
            this.mF1 = f1;
            this.mF2 = f2;
            this.mF12 = f12;
            this.mF22 = f22;
        }

        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = this.mF1.onCreateView(name, context, attrs);
            if (v != null) {
                return v;
            }
            return this.mF2.onCreateView(name, context, attrs);
        }

        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v;
            if (this.mF12 != null) {
                v = this.mF12.onCreateView(parent, name, context, attrs);
            } else {
                v = this.mF1.onCreateView(name, context, attrs);
            }
            if (v != null) {
                return v;
            }
            if (this.mF22 != null) {
                return this.mF22.onCreateView(parent, name, context, attrs);
            }
            return this.mF2.onCreateView(name, context, attrs);
        }
    }

    protected LayoutInflater(Context context) {
        this.mContext = context;
        initPrecompiledViews();
    }

    protected LayoutInflater(LayoutInflater original, Context newContext) {
        this.mContext = newContext;
        this.mFactory = original.mFactory;
        this.mFactory2 = original.mFactory2;
        this.mPrivateFactory = original.mPrivateFactory;
        setFilter(original.mFilter);
        initPrecompiledViews();
    }

    public static LayoutInflater from(Context context) {
        LayoutInflater LayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater != null) {
            return LayoutInflater;
        }
        throw new AssertionError("LayoutInflater not found.");
    }

    public Context getContext() {
        return this.mContext;
    }

    public final Factory getFactory() {
        return this.mFactory;
    }

    public final Factory2 getFactory2() {
        return this.mFactory2;
    }

    public void setFactory(Factory factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
        } else if (factory != null) {
            this.mFactorySet = true;
            if (this.mFactory == null) {
                this.mFactory = factory;
            } else {
                this.mFactory = new FactoryMerger(factory, (Factory2) null, this.mFactory, this.mFactory2);
            }
        } else {
            throw new NullPointerException("Given factory can not be null");
        }
    }

    public void setFactory2(Factory2 factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
        } else if (factory != null) {
            this.mFactorySet = true;
            if (this.mFactory == null) {
                this.mFactory2 = factory;
                this.mFactory = factory;
                return;
            }
            FactoryMerger factoryMerger = new FactoryMerger(factory, factory, this.mFactory, this.mFactory2);
            this.mFactory2 = factoryMerger;
            this.mFactory = factoryMerger;
        } else {
            throw new NullPointerException("Given factory can not be null");
        }
    }

    @UnsupportedAppUsage
    public void setPrivateFactory(Factory2 factory) {
        if (this.mPrivateFactory == null) {
            this.mPrivateFactory = factory;
        } else {
            this.mPrivateFactory = new FactoryMerger(factory, factory, this.mPrivateFactory, this.mPrivateFactory);
        }
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public void setFilter(Filter filter) {
        this.mFilter = filter;
        if (filter != null) {
            this.mFilterMap = new HashMap<>();
        }
    }

    private void initPrecompiledViews() {
        initPrecompiledViews(false);
    }

    private void initPrecompiledViews(boolean enablePrecompiledViews) {
        this.mUseCompiledView = enablePrecompiledViews;
        if (!this.mUseCompiledView) {
            this.mPrecompiledClassLoader = null;
            return;
        }
        ApplicationInfo appInfo = this.mContext.getApplicationInfo();
        if (appInfo.isEmbeddedDexUsed() || appInfo.isPrivilegedApp()) {
            this.mUseCompiledView = false;
            return;
        }
        try {
            this.mPrecompiledClassLoader = this.mContext.getClassLoader();
            String dexFile = this.mContext.getCodeCacheDir() + COMPILED_VIEW_DEX_FILE_NAME;
            if (new File(dexFile).exists()) {
                this.mPrecompiledClassLoader = new PathClassLoader(dexFile, this.mPrecompiledClassLoader);
            } else {
                this.mUseCompiledView = false;
            }
        } catch (Throwable th) {
            this.mUseCompiledView = false;
        }
        if (!this.mUseCompiledView) {
            this.mPrecompiledClassLoader = null;
        }
    }

    public void setPrecompiledLayoutsEnabledForTesting(boolean enablePrecompiledLayouts) {
        initPrecompiledViews(enablePrecompiledLayouts);
    }

    public View inflate(int resource, ViewGroup root) {
        return inflate(resource, root, root != null);
    }

    public View inflate(XmlPullParser parser, ViewGroup root) {
        return inflate(parser, root, root != null);
    }

    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        Resources res = getContext().getResources();
        View view = tryInflatePrecompiled(resource, res, root, attachToRoot);
        if (view != null) {
            return view;
        }
        XmlResourceParser parser = res.getLayout(resource);
        try {
            return inflate((XmlPullParser) parser, root, attachToRoot);
        } finally {
            parser.close();
        }
    }

    private View tryInflatePrecompiled(int resource, Resources res, ViewGroup root, boolean attachToRoot) {
        XmlResourceParser parser;
        if (!this.mUseCompiledView) {
            return null;
        }
        Trace.traceBegin(8, "inflate (precompiled)");
        String pkg = res.getResourcePackageName(resource);
        String layout = res.getResourceEntryName(resource);
        try {
            View view = (View) Class.forName("" + pkg + ".CompiledView", false, this.mPrecompiledClassLoader).getMethod(layout, new Class[]{Context.class, Integer.TYPE}).invoke((Object) null, new Object[]{this.mContext, Integer.valueOf(resource)});
            if (!(view == null || root == null)) {
                parser = res.getLayout(resource);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                advanceToRootNode(parser);
                ViewGroup.LayoutParams params = root.generateLayoutParams(attrs);
                if (attachToRoot) {
                    root.addView(view, params);
                } else {
                    view.setLayoutParams(params);
                }
                parser.close();
            }
            Trace.traceEnd(8);
            return view;
        } catch (Throwable th) {
            Trace.traceEnd(8);
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x000e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x000f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void advanceToRootNode(org.xmlpull.v1.XmlPullParser r5) throws android.view.InflateException, java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            r4 = this;
        L_0x0000:
            int r0 = r5.next()
            r1 = r0
            r2 = 2
            if (r0 == r2) goto L_0x000c
            r0 = 1
            if (r1 == r0) goto L_0x000c
            goto L_0x0000
        L_0x000c:
            if (r1 != r2) goto L_0x000f
            return
        L_0x000f:
            android.view.InflateException r0 = new android.view.InflateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r5.getPositionDescription()
            r2.append(r3)
            java.lang.String r3 = ": No start tag found!"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>((java.lang.String) r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.LayoutInflater.advanceToRootNode(org.xmlpull.v1.XmlPullParser):void");
    }

    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0077, code lost:
        if (r22 == false) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        r16 = r1;
        r10 = r10;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:30:0x007b=Splitter:B:30:0x007b, B:54:0x00df=Splitter:B:54:0x00df} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View inflate(org.xmlpull.v1.XmlPullParser r20, android.view.ViewGroup r21, boolean r22) {
        /*
            r19 = this;
            r7 = r19
            r8 = r21
            java.lang.Object[] r9 = r7.mConstructorArgs
            monitor-enter(r9)
            java.lang.String r0 = "inflate"
            r10 = 8
            android.os.Trace.traceBegin(r10, r0)     // Catch:{ all -> 0x00ed }
            android.content.Context r0 = r7.mContext     // Catch:{ all -> 0x00ed }
            r12 = r0
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r20)     // Catch:{ all -> 0x00ed }
            r13 = r0
            java.lang.Object[] r0 = r7.mConstructorArgs     // Catch:{ all -> 0x00ed }
            r14 = 0
            r0 = r0[r14]     // Catch:{ all -> 0x00ed }
            android.content.Context r0 = (android.content.Context) r0     // Catch:{ all -> 0x00ed }
            r15 = r0
            java.lang.Object[] r0 = r7.mConstructorArgs     // Catch:{ all -> 0x00ed }
            r0[r14] = r12     // Catch:{ all -> 0x00ed }
            r16 = r21
            r17 = 0
            r6 = 1
            r19.advanceToRootNode(r20)     // Catch:{ XmlPullParserException -> 0x00cb, Exception -> 0x00a0, all -> 0x009b }
            java.lang.String r0 = r20.getName()     // Catch:{ XmlPullParserException -> 0x00cb, Exception -> 0x00a0, all -> 0x009b }
            java.lang.String r1 = "merge"
            boolean r1 = r1.equals(r0)     // Catch:{ XmlPullParserException -> 0x00cb, Exception -> 0x00a0, all -> 0x009b }
            if (r1 == 0) goto L_0x0057
            if (r8 == 0) goto L_0x004e
            if (r22 == 0) goto L_0x004e
            r18 = 0
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = r12
            r5 = r13
            r10 = r6
            r6 = r18
            r1.rInflate(r2, r3, r4, r5, r6)     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
            r3 = r20
            goto L_0x007b
        L_0x004e:
            r10 = r6
            android.view.InflateException r1 = new android.view.InflateException     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
            java.lang.String r2 = "<merge /> can be used only with a valid ViewGroup root and attachToRoot=true"
            r1.<init>((java.lang.String) r2)     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
            throw r1     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
        L_0x0057:
            r10 = r6
            android.view.View r1 = r7.createViewFromTag(r8, r0, r12, r13)     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
            r2 = 0
            if (r8 == 0) goto L_0x0069
            android.view.ViewGroup$LayoutParams r3 = r8.generateLayoutParams((android.util.AttributeSet) r13)     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
            r2 = r3
            if (r22 != 0) goto L_0x0069
            r1.setLayoutParams(r2)     // Catch:{ XmlPullParserException -> 0x0097, Exception -> 0x0093, all -> 0x008f }
        L_0x0069:
            r3 = r20
            r7.rInflateChildren(r3, r1, r13, r10)     // Catch:{ XmlPullParserException -> 0x008d, Exception -> 0x008b }
            if (r8 == 0) goto L_0x0075
            if (r22 == 0) goto L_0x0075
            r8.addView((android.view.View) r1, (android.view.ViewGroup.LayoutParams) r2)     // Catch:{ XmlPullParserException -> 0x008d, Exception -> 0x008b }
        L_0x0075:
            if (r8 == 0) goto L_0x0079
            if (r22 != 0) goto L_0x007b
        L_0x0079:
            r16 = r1
        L_0x007b:
            java.lang.Object[] r0 = r7.mConstructorArgs     // Catch:{ all -> 0x00f2 }
            r0[r14] = r15     // Catch:{ all -> 0x00f2 }
            java.lang.Object[] r0 = r7.mConstructorArgs     // Catch:{ all -> 0x00f2 }
            r0[r10] = r17     // Catch:{ all -> 0x00f2 }
            r1 = 8
            android.os.Trace.traceEnd(r1)     // Catch:{ all -> 0x00f2 }
            monitor-exit(r9)     // Catch:{ all -> 0x00f2 }
            return r16
        L_0x008b:
            r0 = move-exception
            goto L_0x00a4
        L_0x008d:
            r0 = move-exception
            goto L_0x00cf
        L_0x008f:
            r0 = move-exception
            r3 = r20
            goto L_0x00df
        L_0x0093:
            r0 = move-exception
            r3 = r20
            goto L_0x00a4
        L_0x0097:
            r0 = move-exception
            r3 = r20
            goto L_0x00cf
        L_0x009b:
            r0 = move-exception
            r3 = r20
            r10 = r6
            goto L_0x00df
        L_0x00a0:
            r0 = move-exception
            r3 = r20
            r10 = r6
        L_0x00a4:
            android.view.InflateException r1 = new android.view.InflateException     // Catch:{ all -> 0x00de }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00de }
            r2.<init>()     // Catch:{ all -> 0x00de }
            java.lang.String r4 = getParserStateDescription(r12, r13)     // Catch:{ all -> 0x00de }
            r2.append(r4)     // Catch:{ all -> 0x00de }
            java.lang.String r4 = ": "
            r2.append(r4)     // Catch:{ all -> 0x00de }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x00de }
            r2.append(r4)     // Catch:{ all -> 0x00de }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00de }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x00de }
            java.lang.StackTraceElement[] r2 = EMPTY_STACK_TRACE     // Catch:{ all -> 0x00de }
            r1.setStackTrace(r2)     // Catch:{ all -> 0x00de }
            throw r1     // Catch:{ all -> 0x00de }
        L_0x00cb:
            r0 = move-exception
            r3 = r20
            r10 = r6
        L_0x00cf:
            android.view.InflateException r1 = new android.view.InflateException     // Catch:{ all -> 0x00de }
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x00de }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x00de }
            java.lang.StackTraceElement[] r2 = EMPTY_STACK_TRACE     // Catch:{ all -> 0x00de }
            r1.setStackTrace(r2)     // Catch:{ all -> 0x00de }
            throw r1     // Catch:{ all -> 0x00de }
        L_0x00de:
            r0 = move-exception
        L_0x00df:
            java.lang.Object[] r1 = r7.mConstructorArgs     // Catch:{ all -> 0x00f2 }
            r1[r14] = r15     // Catch:{ all -> 0x00f2 }
            java.lang.Object[] r1 = r7.mConstructorArgs     // Catch:{ all -> 0x00f2 }
            r1[r10] = r17     // Catch:{ all -> 0x00f2 }
            r1 = 8
            android.os.Trace.traceEnd(r1)     // Catch:{ all -> 0x00f2 }
            throw r0     // Catch:{ all -> 0x00f2 }
        L_0x00ed:
            r0 = move-exception
            r3 = r20
        L_0x00f0:
            monitor-exit(r9)     // Catch:{ all -> 0x00f2 }
            throw r0
        L_0x00f2:
            r0 = move-exception
            goto L_0x00f0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View");
    }

    private static String getParserStateDescription(Context context, AttributeSet attrs) {
        int sourceResId = Resources.getAttributeSetSourceResId(attrs);
        if (sourceResId == 0) {
            return attrs.getPositionDescription();
        }
        return attrs.getPositionDescription() + " in " + context.getResources().getResourceName(sourceResId);
    }

    private final boolean verifyClassLoader(Constructor<? extends View> constructor) {
        ClassLoader constructorLoader = constructor.getDeclaringClass().getClassLoader();
        if (constructorLoader == BOOT_CLASS_LOADER) {
            return true;
        }
        ClassLoader cl = this.mContext.getClassLoader();
        while (constructorLoader != cl) {
            cl = cl.getParent();
            if (cl == null) {
                return false;
            }
        }
        return true;
    }

    public final View createView(String name, String prefix, AttributeSet attrs) throws ClassNotFoundException, InflateException {
        Context context = (Context) this.mConstructorArgs[0];
        if (context == null) {
            context = this.mContext;
        }
        return createView(context, name, prefix, attrs);
    }

    public final View createView(Context viewContext, String name, String prefix, AttributeSet attrs) throws ClassNotFoundException, InflateException {
        String str;
        String str2;
        Object lastContext;
        String str3;
        String str4;
        Objects.requireNonNull(viewContext);
        Objects.requireNonNull(name);
        Constructor<? extends U> constructor = sConstructorMap.get(name);
        if (constructor != null && !verifyClassLoader(constructor)) {
            constructor = null;
            sConstructorMap.remove(name);
        }
        Class<? extends U> cls = null;
        try {
            Trace.traceBegin(8, name);
            if (constructor == null) {
                if (prefix != null) {
                    str4 = prefix + name;
                } else {
                    str4 = name;
                }
                cls = Class.forName(str4, false, this.mContext.getClassLoader()).asSubclass(View.class);
                if (!(this.mFilter == null || cls == null || this.mFilter.onLoadClass(cls))) {
                    failNotAllowed(name, prefix, viewContext, attrs);
                }
                constructor = cls.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            } else if (this.mFilter != null) {
                Boolean allowedState = this.mFilterMap.get(name);
                if (allowedState == null) {
                    if (prefix != null) {
                        str3 = prefix + name;
                    } else {
                        str3 = name;
                    }
                    cls = Class.forName(str3, false, this.mContext.getClassLoader()).asSubclass(View.class);
                    boolean allowed = cls != null && this.mFilter.onLoadClass(cls);
                    this.mFilterMap.put(name, Boolean.valueOf(allowed));
                    if (!allowed) {
                        failNotAllowed(name, prefix, viewContext, attrs);
                    }
                } else if (allowedState.equals(Boolean.FALSE)) {
                    failNotAllowed(name, prefix, viewContext, attrs);
                }
            }
            lastContext = this.mConstructorArgs[0];
            this.mConstructorArgs[0] = viewContext;
            Object[] args = this.mConstructorArgs;
            args[1] = attrs;
            View view = (View) constructor.newInstance(args);
            if (view instanceof ViewStub) {
                ((ViewStub) view).setLayoutInflater(cloneInContext((Context) args[0]));
            }
            this.mConstructorArgs[0] = lastContext;
            Trace.traceEnd(8);
            return view;
        } catch (NoSuchMethodException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(getParserStateDescription(viewContext, attrs));
            sb.append(": Error inflating class ");
            if (prefix != null) {
                str = prefix + name;
            } else {
                str = name;
            }
            sb.append(str);
            InflateException ie = new InflateException(sb.toString(), e);
            ie.setStackTrace(EMPTY_STACK_TRACE);
            throw ie;
        } catch (ClassCastException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getParserStateDescription(viewContext, attrs));
            sb2.append(": Class is not a View ");
            if (prefix != null) {
                str2 = prefix + name;
            } else {
                str2 = name;
            }
            sb2.append(str2);
            InflateException ie2 = new InflateException(sb2.toString(), e2);
            ie2.setStackTrace(EMPTY_STACK_TRACE);
            throw ie2;
        } catch (ClassNotFoundException e3) {
            throw e3;
        } catch (Exception e4) {
            try {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getParserStateDescription(viewContext, attrs));
                sb3.append(": Error inflating class ");
                sb3.append(cls == null ? MediaStore.UNKNOWN_STRING : cls.getName());
                InflateException ie3 = new InflateException(sb3.toString(), e4);
                ie3.setStackTrace(EMPTY_STACK_TRACE);
                throw ie3;
            } catch (Throwable th) {
                Trace.traceEnd(8);
                throw th;
            }
        } catch (Throwable th2) {
            this.mConstructorArgs[0] = lastContext;
            throw th2;
        }
    }

    private void failNotAllowed(String name, String prefix, Context context, AttributeSet attrs) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(getParserStateDescription(context, attrs));
        sb.append(": Class not allowed to be inflated ");
        if (prefix != null) {
            str = prefix + name;
        } else {
            str = name;
        }
        sb.append(str);
        throw new InflateException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        return createView(name, "android.view.", attrs);
    }

    /* access modifiers changed from: protected */
    public View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return onCreateView(name, attrs);
    }

    public View onCreateView(Context viewContext, View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return onCreateView(parent, name, attrs);
    }

    @UnsupportedAppUsage
    private View createViewFromTag(View parent, String name, Context context, AttributeSet attrs) {
        return createViewFromTag(parent, name, context, attrs, false);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public View createViewFromTag(View parent, String name, Context context, AttributeSet attrs, boolean ignoreThemeAttr) {
        Object lastContext;
        View createView;
        if (name.equals("view")) {
            name = attrs.getAttributeValue((String) null, "class");
        }
        if (!ignoreThemeAttr) {
            TypedArray ta = context.obtainStyledAttributes(attrs, ATTRS_THEME);
            int themeResId = ta.getResourceId(0, 0);
            if (themeResId != 0) {
                context = new ContextThemeWrapper(context, themeResId);
            }
            ta.recycle();
        }
        try {
            View view = tryCreateView(parent, name, context, attrs);
            if (view == null) {
                lastContext = this.mConstructorArgs[0];
                this.mConstructorArgs[0] = context;
                if (-1 == name.indexOf(46)) {
                    createView = onCreateView(context, parent, name, attrs);
                } else {
                    createView = createView(context, name, (String) null, attrs);
                }
                view = createView;
                this.mConstructorArgs[0] = lastContext;
            }
            return view;
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException ie = new InflateException(getParserStateDescription(context, attrs) + ": Error inflating class " + name, e2);
            ie.setStackTrace(EMPTY_STACK_TRACE);
            throw ie;
        } catch (Exception e3) {
            InflateException ie2 = new InflateException(getParserStateDescription(context, attrs) + ": Error inflating class " + name, e3);
            ie2.setStackTrace(EMPTY_STACK_TRACE);
            throw ie2;
        } catch (Throwable th) {
            this.mConstructorArgs[0] = lastContext;
            throw th;
        }
    }

    @UnsupportedAppUsage(trackingBug = 122360734)
    public final View tryCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view;
        if (name.equals(TAG_1995)) {
            return new BlinkLayout(context, attrs);
        }
        if (this.mFactory2 != null) {
            view = this.mFactory2.onCreateView(parent, name, context, attrs);
        } else if (this.mFactory != null) {
            view = this.mFactory.onCreateView(name, context, attrs);
        } else {
            view = null;
        }
        if (view != null || this.mPrivateFactory == null) {
            return view;
        }
        return this.mPrivateFactory.onCreateView(parent, name, context, attrs);
    }

    /* access modifiers changed from: package-private */
    public final void rInflateChildren(XmlPullParser parser, View parent, AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {
        rInflate(parser, parent, parent.getContext(), attrs, finishInflate);
    }

    /* access modifiers changed from: package-private */
    public void rInflate(XmlPullParser parser, View parent, Context context, AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {
        int depth = parser.getDepth();
        boolean pendingRequestFocus = false;
        while (true) {
            int next = parser.next();
            int type = next;
            if ((next != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if (TAG_REQUEST_FOCUS.equals(name)) {
                        pendingRequestFocus = true;
                        consumeChildElements(parser);
                    } else if ("tag".equals(name)) {
                        parseViewTag(parser, parent, attrs);
                    } else if (TAG_INCLUDE.equals(name)) {
                        if (parser.getDepth() != 0) {
                            parseInclude(parser, context, parent, attrs);
                        } else {
                            throw new InflateException("<include /> cannot be the root element");
                        }
                    } else if (!TAG_MERGE.equals(name)) {
                        View view = createViewFromTag(parent, name, context, attrs);
                        ViewGroup viewGroup = (ViewGroup) parent;
                        ViewGroup.LayoutParams params = viewGroup.generateLayoutParams(attrs);
                        rInflateChildren(parser, view, attrs, true);
                        viewGroup.addView(view, params);
                    } else {
                        throw new InflateException("<merge /> must be the root element");
                    }
                }
            }
        }
        if (pendingRequestFocus) {
            parent.restoreDefaultFocus();
        }
        if (finishInflate) {
            parent.onFinishInflate();
        }
    }

    private void parseViewTag(XmlPullParser parser, View view, AttributeSet attrs) throws XmlPullParserException, IOException {
        TypedArray ta = view.getContext().obtainStyledAttributes(attrs, R.styleable.ViewTag);
        view.setTag(ta.getResourceId(1, 0), ta.getText(0));
        ta.recycle();
        consumeChildElements(parser);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a5 A[Catch:{ all -> 0x016a }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0148  */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseInclude(org.xmlpull.v1.XmlPullParser r24, android.content.Context r25, android.view.View r26, android.util.AttributeSet r27) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r23 = this;
            r7 = r23
            r0 = r25
            r8 = r26
            r9 = r27
            boolean r1 = r8 instanceof android.view.ViewGroup
            if (r1 == 0) goto L_0x019c
            int[] r1 = ATTRS_THEME
            android.content.res.TypedArray r10 = r0.obtainStyledAttributes((android.util.AttributeSet) r9, (int[]) r1)
            r11 = 0
            int r12 = r10.getResourceId(r11, r11)
            r13 = 1
            if (r12 == 0) goto L_0x001c
            r1 = r13
            goto L_0x001d
        L_0x001c:
            r1 = r11
        L_0x001d:
            r14 = r1
            if (r14 == 0) goto L_0x0026
            android.view.ContextThemeWrapper r1 = new android.view.ContextThemeWrapper
            r1.<init>((android.content.Context) r0, (int) r12)
            r0 = r1
        L_0x0026:
            r15 = r0
            r10.recycle()
            java.lang.String r0 = "layout"
            r6 = 0
            int r0 = r9.getAttributeResourceValue(r6, r0, r11)
            if (r0 != 0) goto L_0x005c
            java.lang.String r1 = "layout"
            java.lang.String r1 = r9.getAttributeValue(r6, r1)
            if (r1 == 0) goto L_0x0054
            int r2 = r1.length()
            if (r2 <= 0) goto L_0x0054
            android.content.res.Resources r2 = r15.getResources()
            java.lang.String r3 = r1.substring(r13)
            java.lang.String r4 = "attr"
            java.lang.String r5 = r15.getPackageName()
            int r0 = r2.getIdentifier(r3, r4, r5)
            goto L_0x005c
        L_0x0054:
            android.view.InflateException r2 = new android.view.InflateException
            java.lang.String r3 = "You must specify a layout in the include tag: <include layout=\"@layout/layoutID\" />"
            r2.<init>((java.lang.String) r3)
            throw r2
        L_0x005c:
            android.util.TypedValue r1 = r7.mTempValue
            if (r1 != 0) goto L_0x0067
            android.util.TypedValue r1 = new android.util.TypedValue
            r1.<init>()
            r7.mTempValue = r1
        L_0x0067:
            if (r0 == 0) goto L_0x0079
            android.content.res.Resources$Theme r1 = r15.getTheme()
            android.util.TypedValue r2 = r7.mTempValue
            boolean r1 = r1.resolveAttribute(r0, r2, r13)
            if (r1 == 0) goto L_0x0079
            android.util.TypedValue r1 = r7.mTempValue
            int r0 = r1.resourceId
        L_0x0079:
            r5 = r0
            if (r5 == 0) goto L_0x0177
            android.content.res.Resources r0 = r15.getResources()
            r1 = r8
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            android.view.View r16 = r7.tryInflatePrecompiled(r5, r0, r1, r13)
            if (r16 != 0) goto L_0x0171
            android.content.res.Resources r0 = r15.getResources()
            android.content.res.XmlResourceParser r0 = r0.getLayout(r5)
            r4 = r0
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r4)     // Catch:{ all -> 0x016a }
        L_0x0096:
            r3 = r0
            int r0 = r4.next()     // Catch:{ all -> 0x016a }
            r2 = r0
            r1 = 2
            if (r0 == r1) goto L_0x00a3
            if (r2 == r13) goto L_0x00a3
            r0 = r3
            goto L_0x0096
        L_0x00a3:
            if (r2 != r1) goto L_0x0148
            java.lang.String r0 = r4.getName()     // Catch:{ all -> 0x016a }
            r1 = r0
            java.lang.String r0 = "merge"
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x016a }
            if (r0 == 0) goto L_0x00cd
            r6 = 0
            r17 = r1
            r1 = r23
            r18 = r2
            r2 = r4
            r11 = r3
            r3 = r26
            r13 = r4
            r4 = r15
            r19 = r5
            r5 = r11
            r1.rInflate(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00c9 }
            r4 = r13
            goto L_0x0140
        L_0x00c9:
            r0 = move-exception
            r4 = r13
            goto L_0x016d
        L_0x00cd:
            r17 = r1
            r18 = r2
            r19 = r5
            r5 = r4
            r4 = r3
            r1 = r23
            r2 = r26
            r3 = r17
            r20 = r4
            r4 = r15
            r21 = r5
            r5 = r20
            r0 = r6
            r6 = r14
            android.view.View r1 = r1.createViewFromTag(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0144 }
            r2 = r8
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2     // Catch:{ all -> 0x0144 }
            int[] r3 = com.android.internal.R.styleable.Include     // Catch:{ all -> 0x0144 }
            android.content.res.TypedArray r3 = r15.obtainStyledAttributes((android.util.AttributeSet) r9, (int[]) r3)     // Catch:{ all -> 0x0144 }
            r4 = -1
            int r5 = r3.getResourceId(r11, r4)     // Catch:{ all -> 0x0144 }
            int r6 = r3.getInt(r13, r4)     // Catch:{ all -> 0x0144 }
            r3.recycle()     // Catch:{ all -> 0x0144 }
            r22 = r0
            android.view.ViewGroup$LayoutParams r0 = r2.generateLayoutParams((android.util.AttributeSet) r9)     // Catch:{ RuntimeException -> 0x010b }
            r22 = r0
            goto L_0x010c
        L_0x0106:
            r0 = move-exception
            r4 = r21
            goto L_0x016d
        L_0x010b:
            r0 = move-exception
        L_0x010c:
            if (r22 != 0) goto L_0x0117
            r11 = r20
            android.view.ViewGroup$LayoutParams r0 = r2.generateLayoutParams((android.util.AttributeSet) r11)     // Catch:{ all -> 0x0106 }
            r22 = r0
            goto L_0x011b
        L_0x0117:
            r11 = r20
            r0 = r22
        L_0x011b:
            r1.setLayoutParams(r0)     // Catch:{ all -> 0x0144 }
            r4 = r21
            r7.rInflateChildren(r4, r1, r11, r13)     // Catch:{ all -> 0x0168 }
            r13 = -1
            if (r5 == r13) goto L_0x0129
            r1.setId(r5)     // Catch:{ all -> 0x0168 }
        L_0x0129:
            switch(r6) {
                case 0: goto L_0x0138;
                case 1: goto L_0x0133;
                case 2: goto L_0x012d;
                default: goto L_0x012c;
            }     // Catch:{ all -> 0x0168 }
        L_0x012c:
            goto L_0x013d
        L_0x012d:
            r13 = 8
            r1.setVisibility(r13)     // Catch:{ all -> 0x0168 }
            goto L_0x013d
        L_0x0133:
            r13 = 4
            r1.setVisibility(r13)     // Catch:{ all -> 0x0168 }
            goto L_0x013d
        L_0x0138:
            r13 = 0
            r1.setVisibility(r13)     // Catch:{ all -> 0x0168 }
        L_0x013d:
            r2.addView(r1)     // Catch:{ all -> 0x0168 }
        L_0x0140:
            r4.close()
            goto L_0x0173
        L_0x0144:
            r0 = move-exception
            r4 = r21
            goto L_0x016d
        L_0x0148:
            r18 = r2
            r11 = r3
            r19 = r5
            android.view.InflateException r0 = new android.view.InflateException     // Catch:{ all -> 0x0168 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0168 }
            r1.<init>()     // Catch:{ all -> 0x0168 }
            java.lang.String r2 = getParserStateDescription(r15, r11)     // Catch:{ all -> 0x0168 }
            r1.append(r2)     // Catch:{ all -> 0x0168 }
            java.lang.String r2 = ": No start tag found!"
            r1.append(r2)     // Catch:{ all -> 0x0168 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0168 }
            r0.<init>((java.lang.String) r1)     // Catch:{ all -> 0x0168 }
            throw r0     // Catch:{ all -> 0x0168 }
        L_0x0168:
            r0 = move-exception
            goto L_0x016d
        L_0x016a:
            r0 = move-exception
            r19 = r5
        L_0x016d:
            r4.close()
            throw r0
        L_0x0171:
            r19 = r5
        L_0x0173:
            consumeChildElements(r24)
            return
        L_0x0177:
            r19 = r5
            r0 = r6
            java.lang.String r1 = "layout"
            java.lang.String r0 = r9.getAttributeValue(r0, r1)
            android.view.InflateException r1 = new android.view.InflateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "You must specify a valid layout reference. The layout ID "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = " is not valid."
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x019c:
            android.view.InflateException r1 = new android.view.InflateException
            java.lang.String r2 = "<include /> can only be used inside of a ViewGroup"
            r1.<init>((java.lang.String) r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.LayoutInflater.parseInclude(org.xmlpull.v1.XmlPullParser, android.content.Context, android.view.View, android.util.AttributeSet):void");
    }

    static final void consumeChildElements(XmlPullParser parser) throws XmlPullParserException, IOException {
        int type;
        int currentDepth = parser.getDepth();
        do {
            int next = parser.next();
            type = next;
            if ((next == 3 && parser.getDepth() <= currentDepth) || type == 1) {
            }
            int next2 = parser.next();
            type = next2;
            return;
        } while (type == 1);
    }

    private static class BlinkLayout extends FrameLayout {
        private static final int BLINK_DELAY = 500;
        private static final int MESSAGE_BLINK = 66;
        /* access modifiers changed from: private */
        public boolean mBlink;
        /* access modifiers changed from: private */
        public boolean mBlinkState;
        private final Handler mHandler = new Handler((Handler.Callback) new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                if (msg.what != 66) {
                    return false;
                }
                if (BlinkLayout.this.mBlink) {
                    boolean unused = BlinkLayout.this.mBlinkState = !BlinkLayout.this.mBlinkState;
                    BlinkLayout.this.makeBlink();
                }
                BlinkLayout.this.invalidate();
                return true;
            }
        });

        public BlinkLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /* access modifiers changed from: private */
        public void makeBlink() {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(66), 500);
        }

        /* access modifiers changed from: protected */
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.mBlink = true;
            this.mBlinkState = true;
            makeBlink();
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.mBlink = false;
            this.mBlinkState = true;
            this.mHandler.removeMessages(66);
        }

        /* access modifiers changed from: protected */
        public void dispatchDraw(Canvas canvas) {
            if (this.mBlinkState) {
                super.dispatchDraw(canvas);
            }
        }
    }
}
