package android.view.inputmethod;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Printer;
import java.io.IOException;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public final class InputMethodInfo implements Parcelable {
    public static final Parcelable.Creator<InputMethodInfo> CREATOR = new Parcelable.Creator<InputMethodInfo>() {
        public InputMethodInfo createFromParcel(Parcel source) {
            return new InputMethodInfo(source);
        }

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
        this(context, service, (List<InputMethodSubtype>) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0239  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public InputMethodInfo(android.content.Context r26, android.content.pm.ResolveInfo r27, java.util.List<android.view.inputmethod.InputMethodSubtype> r28) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r25 = this;
            r1 = r25
            r2 = r27
            r3 = r28
            r25.<init>()
            r1.mService = r2
            android.content.pm.ServiceInfo r4 = r2.serviceInfo
            java.lang.String r0 = computeId(r27)
            r1.mId = r0
            r5 = 1
            r6 = 0
            r0 = 0
            r1.mForceDefault = r0
            android.content.pm.PackageManager r7 = r26.getPackageManager()
            r8 = 0
            r9 = 0
            r10 = 0
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.lang.String r12 = "android.view.im"
            android.content.res.XmlResourceParser r12 = r4.loadXmlMetaData(r7, r12)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            r10 = r12
            if (r10 == 0) goto L_0x01fa
            android.content.pm.ApplicationInfo r12 = r4.applicationInfo     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            android.content.res.Resources r12 = r7.getResourcesForApplication((android.content.pm.ApplicationInfo) r12)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            android.util.AttributeSet r13 = android.util.Xml.asAttributeSet(r10)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
        L_0x0037:
            int r14 = r10.next()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            r15 = r14
            r0 = 1
            if (r14 == r0) goto L_0x0044
            r14 = 2
            if (r15 == r14) goto L_0x0044
            r0 = 0
            goto L_0x0037
        L_0x0044:
            java.lang.String r14 = r10.getName()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            java.lang.String r0 = "input-method"
            boolean r0 = r0.equals(r14)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            if (r0 == 0) goto L_0x01ec
            int[] r0 = com.android.internal.R.styleable.InputMethod     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            android.content.res.TypedArray r0 = r12.obtainAttributes(r13, r0)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            r2 = 1
            java.lang.String r17 = r0.getString(r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0216, all -> 0x020e }
            r8 = r17
            r2 = 3
            r18 = r5
            r5 = 0
            boolean r16 = r0.getBoolean(r2, r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x01e6, all -> 0x01e0 }
            r17 = r16
            int r16 = r0.getResourceId(r5, r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x01e6, all -> 0x01e0 }
            r9 = r16
            r2 = 2
            boolean r19 = r0.getBoolean(r2, r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x01e6, all -> 0x01e0 }
            r6 = r19
            r0.recycle()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x01e6, all -> 0x01e0 }
            int r2 = r10.getDepth()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x01e6, all -> 0x01e0 }
            r5 = r18
        L_0x007d:
            r20 = r0
            int r0 = r10.next()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x01da, all -> 0x01d4 }
            r15 = r0
            r21 = r5
            r5 = 3
            if (r0 != r5) goto L_0x00a1
            int r0 = r10.getDepth()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x009a, all -> 0x0095 }
            if (r0 <= r2) goto L_0x0090
            goto L_0x00a1
        L_0x0090:
            r23 = r7
            r7 = 0
            goto L_0x016f
        L_0x0095:
            r0 = move-exception
            r23 = r7
            goto L_0x0237
        L_0x009a:
            r0 = move-exception
            r23 = r7
        L_0x009d:
            r5 = r21
            goto L_0x021b
        L_0x00a1:
            r0 = 1
            if (r15 == r0) goto L_0x016c
            r0 = 2
            if (r15 != r0) goto L_0x0162
            java.lang.String r0 = r10.getName()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x015b, all -> 0x0156 }
            r14 = r0
            java.lang.String r0 = "subtype"
            boolean r0 = r0.equals(r14)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x015b, all -> 0x0156 }
            if (r0 == 0) goto L_0x0142
            int[] r0 = com.android.internal.R.styleable.InputMethod_Subtype     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x015b, all -> 0x0156 }
            android.content.res.TypedArray r0 = r12.obtainAttributes(r13, r0)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x015b, all -> 0x0156 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r5 = new android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x015b, all -> 0x0156 }
            r5.<init>()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x015b, all -> 0x0156 }
            r22 = r2
            r23 = r7
            r2 = 0
            int r7 = r0.getResourceId(r2, r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r5 = r5.setSubtypeNameResId(r7)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r24 = r12
            r7 = 1
            int r12 = r0.getResourceId(r7, r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r5.setSubtypeIconResId(r12)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 9
            java.lang.String r5 = r0.getString(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setLanguageTag(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 2
            java.lang.String r12 = r0.getString(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setSubtypeLocale(r12)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r12 = 3
            java.lang.String r5 = r0.getString(r12)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setSubtypeMode(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 4
            java.lang.String r5 = r0.getString(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setSubtypeExtraValue(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 5
            r7 = 0
            boolean r5 = r0.getBoolean(r5, r7)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setIsAuxiliary(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 6
            boolean r5 = r0.getBoolean(r5, r7)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setOverridesImplicitlyEnabledSubtype(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 7
            int r5 = r0.getInt(r5, r7)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setSubtypeId(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            r5 = 8
            boolean r5 = r0.getBoolean(r5, r7)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype$InputMethodSubtypeBuilder r2 = r2.setIsAsciiCapable(r5)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            android.view.inputmethod.InputMethodSubtype r2 = r2.build()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            boolean r5 = r2.isAuxiliary()     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            if (r5 != 0) goto L_0x012f
            r5 = 0
            goto L_0x0131
        L_0x012f:
            r5 = r21
        L_0x0131:
            r11.add(r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x013f }
            r0 = r20
            r2 = r22
            r7 = r23
            r12 = r24
            goto L_0x007d
        L_0x013f:
            r0 = move-exception
            goto L_0x021b
        L_0x0142:
            r22 = r2
            r23 = r7
            r24 = r12
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            java.lang.String r2 = "Meta-data in input-method does not start with subtype tag"
            r0.<init>(r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
            throw r0     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x0153, all -> 0x0150 }
        L_0x0150:
            r0 = move-exception
            goto L_0x0237
        L_0x0153:
            r0 = move-exception
            goto L_0x009d
        L_0x0156:
            r0 = move-exception
            r23 = r7
            goto L_0x01e5
        L_0x015b:
            r0 = move-exception
            r23 = r7
            r5 = r21
            goto L_0x01eb
        L_0x0162:
            r22 = r2
            r23 = r7
            r0 = r20
            r5 = r21
            goto L_0x007d
        L_0x016c:
            r23 = r7
            r7 = 0
        L_0x016f:
            if (r10 == 0) goto L_0x0174
            r10.close()
        L_0x0174:
            r0 = r17
            int r2 = r11.size()
            if (r2 != 0) goto L_0x017e
            r5 = 0
            goto L_0x0180
        L_0x017e:
            r5 = r21
        L_0x0180:
            if (r3 == 0) goto L_0x01c2
            int r2 = r28.size()
        L_0x0187:
            if (r7 >= r2) goto L_0x01c2
            java.lang.Object r12 = r3.get(r7)
            android.view.inputmethod.InputMethodSubtype r12 = (android.view.inputmethod.InputMethodSubtype) r12
            boolean r13 = r11.contains(r12)
            if (r13 != 0) goto L_0x0199
            r11.add(r12)
            goto L_0x01bf
        L_0x0199:
            java.lang.String r13 = "InputMethodInfo"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "Duplicated subtype definition found: "
            r14.append(r15)
            java.lang.String r15 = r12.getLocale()
            r14.append(r15)
            java.lang.String r15 = ", "
            r14.append(r15)
            java.lang.String r15 = r12.getMode()
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            android.util.Slog.w((java.lang.String) r13, (java.lang.String) r14)
        L_0x01bf:
            int r7 = r7 + 1
            goto L_0x0187
        L_0x01c2:
            android.view.inputmethod.InputMethodSubtypeArray r2 = new android.view.inputmethod.InputMethodSubtypeArray
            r2.<init>((java.util.List<android.view.inputmethod.InputMethodSubtype>) r11)
            r1.mSubtypes = r2
            r1.mSettingsActivityName = r8
            r1.mIsDefaultResId = r9
            r1.mIsAuxIme = r5
            r1.mSupportsSwitchingToNextInputMethod = r6
            r1.mIsVrOnly = r0
            return
        L_0x01d4:
            r0 = move-exception
            r21 = r5
            r23 = r7
            goto L_0x0237
        L_0x01da:
            r0 = move-exception
            r21 = r5
            r23 = r7
            goto L_0x021b
        L_0x01e0:
            r0 = move-exception
            r23 = r7
            r21 = r18
        L_0x01e5:
            goto L_0x0237
        L_0x01e6:
            r0 = move-exception
            r23 = r7
            r5 = r18
        L_0x01eb:
            goto L_0x021b
        L_0x01ec:
            r18 = r5
            r23 = r7
            r24 = r12
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x020a, all -> 0x0206 }
            java.lang.String r2 = "Meta-data does not start with input-method tag"
            r0.<init>(r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x020a, all -> 0x0206 }
            throw r0     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x020a, all -> 0x0206 }
        L_0x01fa:
            r18 = r5
            r23 = r7
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x020a, all -> 0x0206 }
            java.lang.String r2 = "No android.view.im meta-data"
            r0.<init>(r2)     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x020a, all -> 0x0206 }
            throw r0     // Catch:{ NameNotFoundException | IndexOutOfBoundsException | NumberFormatException -> 0x020a, all -> 0x0206 }
        L_0x0206:
            r0 = move-exception
            r21 = r18
            goto L_0x0237
        L_0x020a:
            r0 = move-exception
            r5 = r18
            goto L_0x021b
        L_0x020e:
            r0 = move-exception
            r18 = r5
            r23 = r7
            r21 = r18
            goto L_0x0237
        L_0x0216:
            r0 = move-exception
            r18 = r5
            r23 = r7
        L_0x021b:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ all -> 0x0234 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0234 }
            r7.<init>()     // Catch:{ all -> 0x0234 }
            java.lang.String r12 = "Unable to create context for: "
            r7.append(r12)     // Catch:{ all -> 0x0234 }
            java.lang.String r12 = r4.packageName     // Catch:{ all -> 0x0234 }
            r7.append(r12)     // Catch:{ all -> 0x0234 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0234 }
            r2.<init>(r7)     // Catch:{ all -> 0x0234 }
            throw r2     // Catch:{ all -> 0x0234 }
        L_0x0234:
            r0 = move-exception
            r21 = r5
        L_0x0237:
            if (r10 == 0) goto L_0x023c
            r10.close()
        L_0x023c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodInfo.<init>(android.content.Context, android.content.pm.ResolveInfo, java.util.List):void");
    }

    InputMethodInfo(Parcel source) {
        this.mId = source.readString();
        this.mSettingsActivityName = source.readString();
        this.mIsDefaultResId = source.readInt();
        boolean z = true;
        this.mIsAuxIme = source.readInt() == 1;
        this.mSupportsSwitchingToNextInputMethod = source.readInt() != 1 ? false : z;
        this.mIsVrOnly = source.readBoolean();
        this.mService = ResolveInfo.CREATOR.createFromParcel(source);
        this.mSubtypes = new InputMethodSubtypeArray(source);
        this.mForceDefault = false;
    }

    public InputMethodInfo(String packageName, String className, CharSequence label, String settingsActivity) {
        this(buildDummyResolveInfo(packageName, className, label), false, settingsActivity, (List<InputMethodSubtype>) null, 0, false, true, false);
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
            return context.createPackageContext(getPackageName(), 0).getResources().getBoolean(getIsDefaultResourceId());
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
        if (o != null && (o instanceof InputMethodInfo)) {
            return this.mId.equals(((InputMethodInfo) o).mId);
        }
        return false;
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

    public int describeContents() {
        return 0;
    }
}
