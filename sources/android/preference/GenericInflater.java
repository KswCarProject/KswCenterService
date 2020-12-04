package android.preference;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.preference.GenericInflater.Parent;
import android.util.AttributeSet;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
abstract class GenericInflater<T, P extends Parent> {
    private static final Class[] mConstructorSignature = {Context.class, AttributeSet.class};
    private static final HashMap sConstructorMap = new HashMap();
    private final boolean DEBUG = false;
    private final Object[] mConstructorArgs = new Object[2];
    protected final Context mContext;
    private String mDefaultPackage;
    private Factory<T> mFactory;
    private boolean mFactorySet;

    public interface Factory<T> {
        T onCreateItem(String str, Context context, AttributeSet attributeSet);
    }

    public interface Parent<T> {
        void addItemFromInflater(T t);
    }

    public abstract GenericInflater cloneInContext(Context context);

    private static class FactoryMerger<T> implements Factory<T> {
        private final Factory<T> mF1;
        private final Factory<T> mF2;

        FactoryMerger(Factory<T> f1, Factory<T> f2) {
            this.mF1 = f1;
            this.mF2 = f2;
        }

        public T onCreateItem(String name, Context context, AttributeSet attrs) {
            T v = this.mF1.onCreateItem(name, context, attrs);
            if (v != null) {
                return v;
            }
            return this.mF2.onCreateItem(name, context, attrs);
        }
    }

    protected GenericInflater(Context context) {
        this.mContext = context;
    }

    protected GenericInflater(GenericInflater<T, P> original, Context newContext) {
        this.mContext = newContext;
        this.mFactory = original.mFactory;
    }

    public void setDefaultPackage(String defaultPackage) {
        this.mDefaultPackage = defaultPackage;
    }

    public String getDefaultPackage() {
        return this.mDefaultPackage;
    }

    public Context getContext() {
        return this.mContext;
    }

    public final Factory<T> getFactory() {
        return this.mFactory;
    }

    public void setFactory(Factory<T> factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this inflater");
        } else if (factory != null) {
            this.mFactorySet = true;
            if (this.mFactory == null) {
                this.mFactory = factory;
            } else {
                this.mFactory = new FactoryMerger(factory, this.mFactory);
            }
        } else {
            throw new NullPointerException("Given factory can not be null");
        }
    }

    public T inflate(int resource, P root) {
        return inflate(resource, root, root != null);
    }

    public T inflate(XmlPullParser parser, P root) {
        return inflate(parser, root, root != null);
    }

    public T inflate(int resource, P root, boolean attachToRoot) {
        XmlResourceParser parser = getContext().getResources().getXml(resource);
        try {
            return inflate((XmlPullParser) parser, root, attachToRoot);
        } finally {
            parser.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001d A[Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0033 A[SYNTHETIC, Splitter:B:16:0x0033] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T inflate(org.xmlpull.v1.XmlPullParser r8, P r9, boolean r10) {
        /*
            r7 = this;
            java.lang.Object[] r0 = r7.mConstructorArgs
            monitor-enter(r0)
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r8)     // Catch:{ all -> 0x0084 }
            java.lang.Object[] r2 = r7.mConstructorArgs     // Catch:{ all -> 0x0084 }
            r3 = 0
            android.content.Context r4 = r7.mContext     // Catch:{ all -> 0x0084 }
            r2[r3] = r4     // Catch:{ all -> 0x0084 }
            r2 = r9
        L_0x000f:
            int r3 = r8.next()     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            r4 = r3
            r5 = 2
            if (r3 == r5) goto L_0x001b
            r3 = 1
            if (r4 == r3) goto L_0x001b
            goto L_0x000f
        L_0x001b:
            if (r4 != r5) goto L_0x0033
            java.lang.String r3 = r8.getName()     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            java.lang.Object r3 = r7.createItemFromTag(r8, r3, r1)     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            r5 = r3
            android.preference.GenericInflater$Parent r5 = (android.preference.GenericInflater.Parent) r5     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            android.preference.GenericInflater$Parent r5 = r7.onMergeRoots(r9, r10, r5)     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            r2 = r5
            r7.rInflate(r8, r2, r1)     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            monitor-exit(r0)     // Catch:{ all -> 0x0084 }
            return r2
        L_0x0033:
            android.view.InflateException r3 = new android.view.InflateException     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            r5.<init>()     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            java.lang.String r6 = r8.getPositionDescription()     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            r5.append(r6)     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            java.lang.String r6 = ": No start tag found!"
            r5.append(r6)     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            java.lang.String r5 = r5.toString()     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            r3.<init>((java.lang.String) r5)     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
            throw r3     // Catch:{ InflateException -> 0x0082, XmlPullParserException -> 0x0074, IOException -> 0x004e }
        L_0x004e:
            r3 = move-exception
            android.view.InflateException r4 = new android.view.InflateException     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r5.<init>()     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = r8.getPositionDescription()     // Catch:{ all -> 0x0084 }
            r5.append(r6)     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = ": "
            r5.append(r6)     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = r3.getMessage()     // Catch:{ all -> 0x0084 }
            r5.append(r6)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0084 }
            r4.<init>((java.lang.String) r5)     // Catch:{ all -> 0x0084 }
            r4.initCause(r3)     // Catch:{ all -> 0x0084 }
            throw r4     // Catch:{ all -> 0x0084 }
        L_0x0074:
            r3 = move-exception
            android.view.InflateException r4 = new android.view.InflateException     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = r3.getMessage()     // Catch:{ all -> 0x0084 }
            r4.<init>((java.lang.String) r5)     // Catch:{ all -> 0x0084 }
            r4.initCause(r3)     // Catch:{ all -> 0x0084 }
            throw r4     // Catch:{ all -> 0x0084 }
        L_0x0082:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0084 }
        L_0x0084:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0084 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.GenericInflater.inflate(org.xmlpull.v1.XmlPullParser, android.preference.GenericInflater$Parent, boolean):java.lang.Object");
    }

    public final T createItem(String name, String prefix, AttributeSet attrs) throws ClassNotFoundException, InflateException {
        String str;
        String str2;
        Constructor constructor = (Constructor) sConstructorMap.get(name);
        if (constructor == null) {
            try {
                ClassLoader classLoader = this.mContext.getClassLoader();
                if (prefix != null) {
                    str2 = prefix + name;
                } else {
                    str2 = name;
                }
                constructor = classLoader.loadClass(str2).getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            } catch (NoSuchMethodException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(attrs.getPositionDescription());
                sb.append(": Error inflating class ");
                if (prefix != null) {
                    str = prefix + name;
                } else {
                    str = name;
                }
                sb.append(str);
                InflateException ie = new InflateException(sb.toString());
                ie.initCause(e);
                throw ie;
            } catch (ClassNotFoundException e2) {
                throw e2;
            } catch (Exception e3) {
                InflateException ie2 = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + constructor.getClass().getName());
                ie2.initCause(e3);
                throw ie2;
            }
        }
        Object[] args = this.mConstructorArgs;
        args[1] = attrs;
        return constructor.newInstance(args);
    }

    /* access modifiers changed from: protected */
    public T onCreateItem(String name, AttributeSet attrs) throws ClassNotFoundException {
        return createItem(name, this.mDefaultPackage, attrs);
    }

    private final T createItemFromTag(XmlPullParser parser, String name, AttributeSet attrs) {
        try {
            T item = this.mFactory == null ? null : this.mFactory.onCreateItem(name, this.mContext, attrs);
            if (item != null) {
                return item;
            }
            if (-1 == name.indexOf(46)) {
                return onCreateItem(name, attrs);
            }
            return createItem(name, (String) null, attrs);
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException ie = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + name);
            ie.initCause(e2);
            throw ie;
        } catch (Exception e3) {
            InflateException ie2 = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + name);
            ie2.initCause(e3);
            throw ie2;
        }
    }

    private void rInflate(XmlPullParser parser, T parent, AttributeSet attrs) throws XmlPullParserException, IOException {
        int depth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if ((next == 3 && parser.getDepth() <= depth) || type == 1) {
                return;
            }
            if (type == 2 && !onCreateCustomFromTag(parser, parent, attrs)) {
                T item = createItemFromTag(parser, parser.getName(), attrs);
                ((Parent) parent).addItemFromInflater(item);
                rInflate(parser, item, attrs);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean onCreateCustomFromTag(XmlPullParser parser, T t, AttributeSet attrs) throws XmlPullParserException {
        return false;
    }

    /* access modifiers changed from: protected */
    public P onMergeRoots(P p, boolean attachToGivenRoot, P xmlRoot) {
        return xmlRoot;
    }
}
