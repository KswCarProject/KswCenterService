package android.view.inputmethod;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.p002pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.AttributeSet;
import android.util.Printer;
import android.util.Xml;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.C3132R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class InputMethodInfo implements Parcelable {
    public static final Parcelable.Creator<InputMethodInfo> CREATOR = new Parcelable.Creator<InputMethodInfo>() { // from class: android.view.inputmethod.InputMethodInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public InputMethodInfo createFromParcel(Parcel source) {
            return new InputMethodInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public InputMethodInfo[] newArray(int size) {
            return new InputMethodInfo[size];
        }
    };
    static final String TAG = "InputMethodInfo";
    private final boolean mForceDefault;
    final String mId;
    private final boolean mIsAuxIme;
    final int mIsDefaultResId;
    final boolean mIsVrOnly;
    final ResolveInfo mService;
    final String mSettingsActivityName;
    @UnsupportedAppUsage
    private final InputMethodSubtypeArray mSubtypes;
    private final boolean mSupportsSwitchingToNextInputMethod;

    public static String computeId(ResolveInfo service) {
        ServiceInfo si = service.serviceInfo;
        return new ComponentName(si.packageName, si.name).flattenToShortString();
    }

    public InputMethodInfo(Context context, ResolveInfo service) throws XmlPullParserException, IOException {
        this(context, service, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x014f, code lost:
        throw new org.xmlpull.v1.XmlPullParserException("Meta-data in input-method does not start with subtype tag");
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x016f, code lost:
        if (r10 == null) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0171, code lost:
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x017a, code lost:
        if (r11.size() != 0) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x017c, code lost:
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x017e, code lost:
        r5 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0180, code lost:
        if (r28 == null) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0182, code lost:
        r2 = r28.size();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0187, code lost:
        if (r7 >= r2) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0189, code lost:
        r12 = r28.get(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0193, code lost:
        if (r11.contains(r12) != false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0195, code lost:
        r11.add(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0199, code lost:
        android.util.Slog.m50w(android.view.inputmethod.InputMethodInfo.TAG, "Duplicated subtype definition found: " + r12.getLocale() + ", " + r12.getMode());
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01bf, code lost:
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01c2, code lost:
        r25.mSubtypes = new android.view.inputmethod.InputMethodSubtypeArray(r11);
        r25.mSettingsActivityName = r17;
        r25.mIsDefaultResId = r16;
        r25.mIsAuxIme = r5;
        r25.mSupportsSwitchingToNextInputMethod = r19;
        r25.mIsVrOnly = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01d3, code lost:
        return;
     */
    /* JADX WARN: Not initialized variable reg: 18, insn: 0x0207: MOVE  (r21 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r18 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isAuxIme' boolean)]), block:B:91:0x0207 */
    /* JADX WARN: Not initialized variable reg: 18, insn: 0x020b: MOVE  (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r18 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isAuxIme' boolean)]), block:B:93:0x020b */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0239  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public InputMethodInfo(Context context, ResolveInfo service, List<InputMethodSubtype> additionalSubtypes) throws XmlPullParserException, IOException {
        boolean isAuxIme;
        int i;
        Resources res;
        InputMethodSubtype subtype;
        this.mService = service;
        ServiceInfo si = service.serviceInfo;
        this.mId = computeId(service);
        boolean isAuxIme2 = true;
        this.mForceDefault = false;
        PackageManager pm = context.getPackageManager();
        XmlResourceParser parser = null;
        ArrayList<InputMethodSubtype> subtypes = new ArrayList<>();
        try {
            parser = si.loadXmlMetaData(pm, InputMethod.SERVICE_META_DATA);
            try {
                if (parser == null) {
                    throw new XmlPullParserException("No android.view.im meta-data");
                }
                Resources res2 = pm.getResourcesForApplication(si.applicationInfo);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                while (true) {
                    int type = parser.next();
                    if (type == 1 || type == 2) {
                        break;
                    }
                }
                String nodeName = parser.getName();
                if (!"input-method".equals(nodeName)) {
                    throw new XmlPullParserException("Meta-data does not start with input-method tag");
                }
                TypedArray sa = res2.obtainAttributes(attrs, C3132R.styleable.InputMethod);
                String settingsActivityComponent = sa.getString(1);
                try {
                    boolean isVrOnly = sa.getBoolean(3, false);
                    int isDefaultResId = sa.getResourceId(0, 0);
                    boolean supportsSwitchingToNextInputMethod = sa.getBoolean(2, false);
                    sa.recycle();
                    int depth = parser.getDepth();
                    isAuxIme2 = true;
                    while (true) {
                        TypedArray sa2 = sa;
                        try {
                            int type2 = parser.next();
                            boolean isAuxIme3 = isAuxIme2;
                            if (type2 == 3) {
                                try {
                                    if (parser.getDepth() <= depth) {
                                        i = 0;
                                        break;
                                    }
                                } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e) {
                                    isAuxIme2 = isAuxIme3;
                                    throw new XmlPullParserException("Unable to create context for: " + si.packageName);
                                } catch (Throwable th) {
                                    e = th;
                                    if (parser != null) {
                                    }
                                    throw e;
                                }
                            }
                            if (type2 == 1) {
                                i = 0;
                                break;
                            } else if (type2 == 2) {
                                try {
                                    String nodeName2 = parser.getName();
                                    if (!"subtype".equals(nodeName2)) {
                                        break;
                                    }
                                    TypedArray a = res2.obtainAttributes(attrs, C3132R.styleable.InputMethod_Subtype);
                                    int depth2 = depth;
                                    PackageManager pm2 = pm;
                                    try {
                                        res = res2;
                                        subtype = new InputMethodSubtype.InputMethodSubtypeBuilder().setSubtypeNameResId(a.getResourceId(0, 0)).setSubtypeIconResId(a.getResourceId(1, 0)).setLanguageTag(a.getString(9)).setSubtypeLocale(a.getString(2)).setSubtypeMode(a.getString(3)).setSubtypeExtraValue(a.getString(4)).setIsAuxiliary(a.getBoolean(5, false)).setOverridesImplicitlyEnabledSubtype(a.getBoolean(6, false)).setSubtypeId(a.getInt(7, 0)).setIsAsciiCapable(a.getBoolean(8, false)).build();
                                        isAuxIme2 = !subtype.isAuxiliary() ? false : isAuxIme3;
                                    } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e2) {
                                        isAuxIme2 = isAuxIme3;
                                        throw new XmlPullParserException("Unable to create context for: " + si.packageName);
                                    } catch (Throwable th2) {
                                        e = th2;
                                        if (parser != null) {
                                        }
                                        throw e;
                                    }
                                    try {
                                        try {
                                            subtypes.add(subtype);
                                            sa = sa2;
                                            depth = depth2;
                                            pm = pm2;
                                            res2 = res;
                                        } catch (Throwable th3) {
                                            e = th3;
                                            if (parser != null) {
                                                parser.close();
                                            }
                                            throw e;
                                        }
                                    } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e3) {
                                        throw new XmlPullParserException("Unable to create context for: " + si.packageName);
                                    }
                                } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e4) {
                                    isAuxIme2 = isAuxIme3;
                                } catch (Throwable th4) {
                                    e = th4;
                                }
                            } else {
                                sa = sa2;
                                isAuxIme2 = isAuxIme3;
                            }
                        } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e5) {
                        } catch (Throwable th5) {
                            e = th5;
                        }
                    }
                } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e6) {
                    isAuxIme2 = true;
                } catch (Throwable th6) {
                    e = th6;
                }
            } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e7) {
                isAuxIme2 = isAuxIme;
            } catch (Throwable th7) {
                e = th7;
            }
        } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException e8) {
        } catch (Throwable th8) {
            e = th8;
        }
    }

    InputMethodInfo(Parcel source) {
        this.mId = source.readString();
        this.mSettingsActivityName = source.readString();
        this.mIsDefaultResId = source.readInt();
        this.mIsAuxIme = source.readInt() == 1;
        this.mSupportsSwitchingToNextInputMethod = source.readInt() == 1;
        this.mIsVrOnly = source.readBoolean();
        this.mService = ResolveInfo.CREATOR.createFromParcel(source);
        this.mSubtypes = new InputMethodSubtypeArray(source);
        this.mForceDefault = false;
    }

    public InputMethodInfo(String packageName, String className, CharSequence label, String settingsActivity) {
        this(buildDummyResolveInfo(packageName, className, label), false, settingsActivity, null, 0, false, true, false);
    }

    public InputMethodInfo(ResolveInfo ri, boolean isAuxIme, String settingsActivity, List<InputMethodSubtype> subtypes, int isDefaultResId, boolean forceDefault) {
        this(ri, isAuxIme, settingsActivity, subtypes, isDefaultResId, forceDefault, true, false);
    }

    public InputMethodInfo(ResolveInfo ri, boolean isAuxIme, String settingsActivity, List<InputMethodSubtype> subtypes, int isDefaultResId, boolean forceDefault, boolean supportsSwitchingToNextInputMethod, boolean isVrOnly) {
        ServiceInfo si = ri.serviceInfo;
        this.mService = ri;
        this.mId = new ComponentName(si.packageName, si.name).flattenToShortString();
        this.mSettingsActivityName = settingsActivity;
        this.mIsDefaultResId = isDefaultResId;
        this.mIsAuxIme = isAuxIme;
        this.mSubtypes = new InputMethodSubtypeArray(subtypes);
        this.mForceDefault = forceDefault;
        this.mSupportsSwitchingToNextInputMethod = supportsSwitchingToNextInputMethod;
        this.mIsVrOnly = isVrOnly;
    }

    private static ResolveInfo buildDummyResolveInfo(String packageName, String className, CharSequence label) {
        ResolveInfo ri = new ResolveInfo();
        ServiceInfo si = new ServiceInfo();
        ApplicationInfo ai = new ApplicationInfo();
        ai.packageName = packageName;
        ai.enabled = true;
        si.applicationInfo = ai;
        si.enabled = true;
        si.packageName = packageName;
        si.name = className;
        si.exported = true;
        si.nonLocalizedLabel = label;
        ri.serviceInfo = si;
        return ri;
    }

    public String getId() {
        return this.mId;
    }

    public String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public String getServiceName() {
        return this.mService.serviceInfo.name;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public CharSequence loadLabel(PackageManager pm) {
        return this.mService.loadLabel(pm);
    }

    public Drawable loadIcon(PackageManager pm) {
        return this.mService.loadIcon(pm);
    }

    public String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public boolean isVrOnly() {
        return this.mIsVrOnly;
    }

    public int getSubtypeCount() {
        return this.mSubtypes.getCount();
    }

    public InputMethodSubtype getSubtypeAt(int index) {
        return this.mSubtypes.get(index);
    }

    public int getIsDefaultResourceId() {
        return this.mIsDefaultResId;
    }

    @UnsupportedAppUsage
    public boolean isDefault(Context context) {
        if (this.mForceDefault) {
            return true;
        }
        try {
            if (getIsDefaultResourceId() == 0) {
                return false;
            }
            Resources res = context.createPackageContext(getPackageName(), 0).getResources();
            return res.getBoolean(getIsDefaultResourceId());
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            return false;
        }
    }

    public void dump(Printer pw, String prefix) {
        pw.println(prefix + "mId=" + this.mId + " mSettingsActivityName=" + this.mSettingsActivityName + " mIsVrOnly=" + this.mIsVrOnly + " mSupportsSwitchingToNextInputMethod=" + this.mSupportsSwitchingToNextInputMethod);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append("mIsDefaultResId=0x");
        sb.append(Integer.toHexString(this.mIsDefaultResId));
        pw.println(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(prefix);
        sb2.append("Service:");
        pw.println(sb2.toString());
        ResolveInfo resolveInfo = this.mService;
        resolveInfo.dump(pw, prefix + "  ");
    }

    public String toString() {
        return "InputMethodInfo{" + this.mId + ", settings: " + this.mSettingsActivityName + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof InputMethodInfo)) {
            return false;
        }
        InputMethodInfo obj = (InputMethodInfo) o;
        return this.mId.equals(obj.mId);
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean isSystem() {
        return (this.mService.serviceInfo.applicationInfo.flags & 1) != 0;
    }

    public boolean isAuxiliaryIme() {
        return this.mIsAuxIme;
    }

    public boolean supportsSwitchingToNextInputMethod() {
        return this.mSupportsSwitchingToNextInputMethod;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mSettingsActivityName);
        dest.writeInt(this.mIsDefaultResId);
        dest.writeInt(this.mIsAuxIme ? 1 : 0);
        dest.writeInt(this.mSupportsSwitchingToNextInputMethod ? 1 : 0);
        dest.writeBoolean(this.mIsVrOnly);
        this.mService.writeToParcel(dest, flags);
        this.mSubtypes.writeToParcel(dest);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
