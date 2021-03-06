package android.printservice;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class PrintServiceInfo implements Parcelable {
    public static final Parcelable.Creator<PrintServiceInfo> CREATOR = new Parcelable.Creator<PrintServiceInfo>() {
        public PrintServiceInfo createFromParcel(Parcel parcel) {
            return new PrintServiceInfo(parcel);
        }

        public PrintServiceInfo[] newArray(int size) {
            return new PrintServiceInfo[size];
        }
    };
    private static final String LOG_TAG = PrintServiceInfo.class.getSimpleName();
    private static final String TAG_PRINT_SERVICE = "print-service";
    private final String mAddPrintersActivityName;
    private final String mAdvancedPrintOptionsActivityName;
    private final String mId;
    private boolean mIsEnabled;
    private final ResolveInfo mResolveInfo;
    private final String mSettingsActivityName;

    public PrintServiceInfo(Parcel parcel) {
        this.mId = parcel.readString();
        this.mIsEnabled = parcel.readByte() != 0;
        this.mResolveInfo = (ResolveInfo) parcel.readParcelable((ClassLoader) null);
        this.mSettingsActivityName = parcel.readString();
        this.mAddPrintersActivityName = parcel.readString();
        this.mAdvancedPrintOptionsActivityName = parcel.readString();
    }

    public PrintServiceInfo(ResolveInfo resolveInfo, String settingsActivityName, String addPrintersActivityName, String advancedPrintOptionsActivityName) {
        this.mId = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name).flattenToString();
        this.mResolveInfo = resolveInfo;
        this.mSettingsActivityName = settingsActivityName;
        this.mAddPrintersActivityName = addPrintersActivityName;
        this.mAdvancedPrintOptionsActivityName = advancedPrintOptionsActivityName;
    }

    public ComponentName getComponentName() {
        return new ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005a, code lost:
        if (r3 != null) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b1, code lost:
        if (r3 == null) goto L_0x00ba;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.printservice.PrintServiceInfo create(android.content.Context r12, android.content.pm.ResolveInfo r13) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            android.content.pm.PackageManager r4 = r12.getPackageManager()
            android.content.pm.ServiceInfo r5 = r13.serviceInfo
            java.lang.String r6 = "android.printservice"
            android.content.res.XmlResourceParser r3 = r5.loadXmlMetaData(r4, r6)
            if (r3 == 0) goto L_0x00ba
            r5 = 0
            r6 = r5
        L_0x0014:
            r7 = 1
            if (r6 == r7) goto L_0x0020
            r8 = 2
            if (r6 == r8) goto L_0x0020
            int r7 = r3.next()     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            r6 = r7
            goto L_0x0014
        L_0x0020:
            java.lang.String r8 = r3.getName()     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            java.lang.String r9 = "print-service"
            boolean r9 = r9.equals(r8)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            if (r9 != 0) goto L_0x0035
            java.lang.String r5 = LOG_TAG     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            java.lang.String r7 = "Ignoring meta-data that does not start with print-service tag"
            android.util.Log.e(r5, r7)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            goto L_0x005a
        L_0x0035:
            android.content.pm.ServiceInfo r9 = r13.serviceInfo     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            android.content.pm.ApplicationInfo r9 = r9.applicationInfo     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            android.content.res.Resources r9 = r4.getResourcesForApplication((android.content.pm.ApplicationInfo) r9)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            android.util.AttributeSet r10 = android.util.Xml.asAttributeSet(r3)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            int[] r11 = com.android.internal.R.styleable.PrintService     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            android.content.res.TypedArray r11 = r9.obtainAttributes(r10, r11)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            java.lang.String r5 = r11.getString(r5)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            r0 = r5
            java.lang.String r5 = r11.getString(r7)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            r1 = r5
            r5 = 3
            java.lang.String r5 = r11.getString(r5)     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
            r2 = r5
            r11.recycle()     // Catch:{ IOException -> 0x009a, XmlPullParserException -> 0x0080, NameNotFoundException -> 0x0062 }
        L_0x005a:
            if (r3 == 0) goto L_0x00ba
        L_0x005c:
            r3.close()
            goto L_0x00ba
        L_0x0060:
            r5 = move-exception
            goto L_0x00b4
        L_0x0062:
            r5 = move-exception
            java.lang.String r6 = LOG_TAG     // Catch:{ all -> 0x0060 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0060 }
            r7.<init>()     // Catch:{ all -> 0x0060 }
            java.lang.String r8 = "Unable to load resources for: "
            r7.append(r8)     // Catch:{ all -> 0x0060 }
            android.content.pm.ServiceInfo r8 = r13.serviceInfo     // Catch:{ all -> 0x0060 }
            java.lang.String r8 = r8.packageName     // Catch:{ all -> 0x0060 }
            r7.append(r8)     // Catch:{ all -> 0x0060 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0060 }
            android.util.Log.e(r6, r7)     // Catch:{ all -> 0x0060 }
            if (r3 == 0) goto L_0x00ba
            goto L_0x005c
        L_0x0080:
            r5 = move-exception
            java.lang.String r6 = LOG_TAG     // Catch:{ all -> 0x0060 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0060 }
            r7.<init>()     // Catch:{ all -> 0x0060 }
            java.lang.String r8 = "Error reading meta-data:"
            r7.append(r8)     // Catch:{ all -> 0x0060 }
            r7.append(r5)     // Catch:{ all -> 0x0060 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0060 }
            android.util.Log.w((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ all -> 0x0060 }
            if (r3 == 0) goto L_0x00ba
            goto L_0x005c
        L_0x009a:
            r5 = move-exception
            java.lang.String r6 = LOG_TAG     // Catch:{ all -> 0x0060 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0060 }
            r7.<init>()     // Catch:{ all -> 0x0060 }
            java.lang.String r8 = "Error reading meta-data:"
            r7.append(r8)     // Catch:{ all -> 0x0060 }
            r7.append(r5)     // Catch:{ all -> 0x0060 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0060 }
            android.util.Log.w((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ all -> 0x0060 }
            if (r3 == 0) goto L_0x00ba
            goto L_0x005c
        L_0x00b4:
            if (r3 == 0) goto L_0x00b9
            r3.close()
        L_0x00b9:
            throw r5
        L_0x00ba:
            android.printservice.PrintServiceInfo r5 = new android.printservice.PrintServiceInfo
            r5.<init>(r13, r0, r1, r2)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.printservice.PrintServiceInfo.create(android.content.Context, android.content.pm.ResolveInfo):android.printservice.PrintServiceInfo");
    }

    public String getId() {
        return this.mId;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.mIsEnabled = isEnabled;
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public String getAddPrintersActivityName() {
        return this.mAddPrintersActivityName;
    }

    public String getAdvancedOptionsActivityName() {
        return this.mAdvancedPrintOptionsActivityName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flagz) {
        parcel.writeString(this.mId);
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : 0);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString(this.mAddPrintersActivityName);
        parcel.writeString(this.mAdvancedPrintOptionsActivityName);
    }

    public int hashCode() {
        return (this.mId == null ? 0 : this.mId.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrintServiceInfo other = (PrintServiceInfo) obj;
        if (this.mId == null) {
            if (other.mId != null) {
                return false;
            }
        } else if (!this.mId.equals(other.mId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "PrintServiceInfo{" + "id=" + this.mId + "isEnabled=" + this.mIsEnabled + ", resolveInfo=" + this.mResolveInfo + ", settingsActivityName=" + this.mSettingsActivityName + ", addPrintersActivityName=" + this.mAddPrintersActivityName + ", advancedPrintOptionsActivityName=" + this.mAdvancedPrintOptionsActivityName + "}";
    }
}
