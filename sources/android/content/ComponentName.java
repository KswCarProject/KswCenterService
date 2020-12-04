package android.content;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

public final class ComponentName implements Parcelable, Cloneable, Comparable<ComponentName> {
    public static final Parcelable.Creator<ComponentName> CREATOR = new Parcelable.Creator<ComponentName>() {
        public ComponentName createFromParcel(Parcel in) {
            return new ComponentName(in);
        }

        public ComponentName[] newArray(int size) {
            return new ComponentName[size];
        }
    };
    private final String mClass;
    private final String mPackage;

    @FunctionalInterface
    public interface WithComponentName {
        ComponentName getComponentName();
    }

    public static ComponentName createRelative(String pkg, String cls) {
        String fullName;
        if (!TextUtils.isEmpty(cls)) {
            if (cls.charAt(0) == '.') {
                fullName = pkg + cls;
            } else {
                fullName = cls;
            }
            return new ComponentName(pkg, fullName);
        }
        throw new IllegalArgumentException("class name cannot be empty");
    }

    public static ComponentName createRelative(Context pkg, String cls) {
        return createRelative(pkg.getPackageName(), cls);
    }

    public ComponentName(String pkg, String cls) {
        if (pkg == null) {
            throw new NullPointerException("package name is null");
        } else if (cls != null) {
            this.mPackage = pkg;
            this.mClass = cls;
        } else {
            throw new NullPointerException("class name is null");
        }
    }

    public ComponentName(Context pkg, String cls) {
        if (cls != null) {
            this.mPackage = pkg.getPackageName();
            this.mClass = cls;
            return;
        }
        throw new NullPointerException("class name is null");
    }

    public ComponentName(Context pkg, Class<?> cls) {
        this.mPackage = pkg.getPackageName();
        this.mClass = cls.getName();
    }

    public ComponentName clone() {
        return new ComponentName(this.mPackage, this.mClass);
    }

    public String getPackageName() {
        return this.mPackage;
    }

    public String getClassName() {
        return this.mClass;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r0 = r4.mPackage.length();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getShortClassName() {
        /*
            r4 = this;
            java.lang.String r0 = r4.mClass
            java.lang.String r1 = r4.mPackage
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L_0x0029
            java.lang.String r0 = r4.mPackage
            int r0 = r0.length()
            java.lang.String r1 = r4.mClass
            int r1 = r1.length()
            if (r1 <= r0) goto L_0x0029
            java.lang.String r2 = r4.mClass
            char r2 = r2.charAt(r0)
            r3 = 46
            if (r2 != r3) goto L_0x0029
            java.lang.String r2 = r4.mClass
            java.lang.String r2 = r2.substring(r0, r1)
            return r2
        L_0x0029:
            java.lang.String r0 = r4.mClass
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ComponentName.getShortClassName():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r5.length();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void appendShortClassName(java.lang.StringBuilder r4, java.lang.String r5, java.lang.String r6) {
        /*
            boolean r0 = r6.startsWith(r5)
            if (r0 == 0) goto L_0x001c
            int r0 = r5.length()
            int r1 = r6.length()
            if (r1 <= r0) goto L_0x001c
            char r2 = r6.charAt(r0)
            r3 = 46
            if (r2 != r3) goto L_0x001c
            r4.append(r6, r0, r1)
            return
        L_0x001c:
            r4.append(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ComponentName.appendShortClassName(java.lang.StringBuilder, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r5.length();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void printShortClassName(java.io.PrintWriter r4, java.lang.String r5, java.lang.String r6) {
        /*
            boolean r0 = r6.startsWith(r5)
            if (r0 == 0) goto L_0x001e
            int r0 = r5.length()
            int r1 = r6.length()
            if (r1 <= r0) goto L_0x001e
            char r2 = r6.charAt(r0)
            r3 = 46
            if (r2 != r3) goto L_0x001e
            int r2 = r1 - r0
            r4.write(r6, r0, r2)
            return
        L_0x001e:
            r4.print(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ComponentName.printShortClassName(java.io.PrintWriter, java.lang.String, java.lang.String):void");
    }

    public static String flattenToShortString(ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        return componentName.flattenToShortString();
    }

    public String flattenToString() {
        return this.mPackage + "/" + this.mClass;
    }

    public String flattenToShortString() {
        StringBuilder sb = new StringBuilder(this.mPackage.length() + this.mClass.length());
        appendShortString(sb, this.mPackage, this.mClass);
        return sb.toString();
    }

    public void appendShortString(StringBuilder sb) {
        appendShortString(sb, this.mPackage, this.mClass);
    }

    @UnsupportedAppUsage
    public static void appendShortString(StringBuilder sb, String packageName, String className) {
        sb.append(packageName);
        sb.append('/');
        appendShortClassName(sb, packageName, className);
    }

    @UnsupportedAppUsage
    public static void printShortString(PrintWriter pw, String packageName, String className) {
        pw.print(packageName);
        pw.print('/');
        printShortClassName(pw, packageName, className);
    }

    public static ComponentName unflattenFromString(String str) {
        int sep = str.indexOf(47);
        if (sep < 0 || sep + 1 >= str.length()) {
            return null;
        }
        String pkg = str.substring(0, sep);
        String cls = str.substring(sep + 1);
        if (cls.length() > 0 && cls.charAt(0) == '.') {
            cls = pkg + cls;
        }
        return new ComponentName(pkg, cls);
    }

    public String toShortString() {
        return "{" + this.mPackage + "/" + this.mClass + "}";
    }

    public String toString() {
        return "ComponentInfo{" + this.mPackage + "/" + this.mClass + "}";
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.mPackage);
        proto.write(1138166333442L, this.mClass);
        proto.end(token);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            try {
                ComponentName other = (ComponentName) obj;
                if (!this.mPackage.equals(other.mPackage) || !this.mClass.equals(other.mClass)) {
                    return false;
                }
                return true;
            } catch (ClassCastException e) {
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mPackage.hashCode() + this.mClass.hashCode();
    }

    public int compareTo(ComponentName that) {
        int v = this.mPackage.compareTo(that.mPackage);
        if (v != 0) {
            return v;
        }
        return this.mClass.compareTo(that.mClass);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mPackage);
        out.writeString(this.mClass);
    }

    public static void writeToParcel(ComponentName c, Parcel out) {
        if (c != null) {
            c.writeToParcel(out, 0);
        } else {
            out.writeString((String) null);
        }
    }

    public static ComponentName readFromParcel(Parcel in) {
        String pkg = in.readString();
        if (pkg != null) {
            return new ComponentName(pkg, in);
        }
        return null;
    }

    public ComponentName(Parcel in) {
        this.mPackage = in.readString();
        if (this.mPackage != null) {
            this.mClass = in.readString();
            if (this.mClass == null) {
                throw new NullPointerException("class name is null");
            }
            return;
        }
        throw new NullPointerException("package name is null");
    }

    private ComponentName(String pkg, Parcel in) {
        this.mPackage = pkg;
        this.mClass = in.readString();
    }
}
