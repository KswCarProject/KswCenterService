package android.app.admin;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.AttributeSet;
import android.util.Printer;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.C3132R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class DeviceAdminInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceAdminInfo> CREATOR;
    static final String TAG = "DeviceAdminInfo";
    public static final int USES_ENCRYPTED_STORAGE = 7;
    public static final int USES_POLICY_DEVICE_OWNER = -2;
    public static final int USES_POLICY_DISABLE_CAMERA = 8;
    public static final int USES_POLICY_DISABLE_KEYGUARD_FEATURES = 9;
    public static final int USES_POLICY_EXPIRE_PASSWORD = 6;
    public static final int USES_POLICY_FORCE_LOCK = 3;
    public static final int USES_POLICY_LIMIT_PASSWORD = 0;
    public static final int USES_POLICY_PROFILE_OWNER = -1;
    public static final int USES_POLICY_RESET_PASSWORD = 2;
    public static final int USES_POLICY_SETS_GLOBAL_PROXY = 5;
    public static final int USES_POLICY_WATCH_LOGIN = 1;
    public static final int USES_POLICY_WIPE_DATA = 4;
    final ActivityInfo mActivityInfo;
    boolean mSupportsTransferOwnership;
    int mUsesPolicies;
    boolean mVisible;
    static ArrayList<PolicyInfo> sPoliciesDisplayOrder = new ArrayList<>();
    static HashMap<String, Integer> sKnownPolicies = new HashMap<>();
    static SparseArray<PolicyInfo> sRevKnownPolicies = new SparseArray<>();

    /* loaded from: classes.dex */
    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public final String tag;

        public PolicyInfo(int ident, String tag, int label, int description) {
            this(ident, tag, label, description, label, description);
        }

        public PolicyInfo(int ident, String tag, int label, int description, int labelForSecondaryUsers, int descriptionForSecondaryUsers) {
            this.ident = ident;
            this.tag = tag;
            this.label = label;
            this.description = description;
            this.labelForSecondaryUsers = labelForSecondaryUsers;
            this.descriptionForSecondaryUsers = descriptionForSecondaryUsers;
        }
    }

    static {
        sPoliciesDisplayOrder.add(new PolicyInfo(4, "wipe-data", C3132R.string.policylab_wipeData, C3132R.string.policydesc_wipeData, C3132R.string.policylab_wipeData_secondaryUser, C3132R.string.policydesc_wipeData_secondaryUser));
        sPoliciesDisplayOrder.add(new PolicyInfo(2, "reset-password", C3132R.string.policylab_resetPassword, C3132R.string.policydesc_resetPassword));
        int i = 0;
        sPoliciesDisplayOrder.add(new PolicyInfo(0, "limit-password", C3132R.string.policylab_limitPassword, C3132R.string.policydesc_limitPassword));
        sPoliciesDisplayOrder.add(new PolicyInfo(1, "watch-login", C3132R.string.policylab_watchLogin, C3132R.string.policydesc_watchLogin, C3132R.string.policylab_watchLogin, C3132R.string.policydesc_watchLogin_secondaryUser));
        sPoliciesDisplayOrder.add(new PolicyInfo(3, "force-lock", C3132R.string.policylab_forceLock, C3132R.string.policydesc_forceLock));
        sPoliciesDisplayOrder.add(new PolicyInfo(5, "set-global-proxy", C3132R.string.policylab_setGlobalProxy, C3132R.string.policydesc_setGlobalProxy));
        sPoliciesDisplayOrder.add(new PolicyInfo(6, "expire-password", C3132R.string.policylab_expirePassword, C3132R.string.policydesc_expirePassword));
        sPoliciesDisplayOrder.add(new PolicyInfo(7, "encrypted-storage", C3132R.string.policylab_encryptedStorage, C3132R.string.policydesc_encryptedStorage));
        sPoliciesDisplayOrder.add(new PolicyInfo(8, "disable-camera", C3132R.string.policylab_disableCamera, C3132R.string.policydesc_disableCamera));
        sPoliciesDisplayOrder.add(new PolicyInfo(9, "disable-keyguard-features", C3132R.string.policylab_disableKeyguardFeatures, C3132R.string.policydesc_disableKeyguardFeatures));
        while (true) {
            int i2 = i;
            if (i2 < sPoliciesDisplayOrder.size()) {
                PolicyInfo pi = sPoliciesDisplayOrder.get(i2);
                sRevKnownPolicies.put(pi.ident, pi);
                sKnownPolicies.put(pi.tag, Integer.valueOf(pi.ident));
                i = i2 + 1;
            } else {
                CREATOR = new Parcelable.Creator<DeviceAdminInfo>() { // from class: android.app.admin.DeviceAdminInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.p007os.Parcelable.Creator
                    public DeviceAdminInfo createFromParcel(Parcel source) {
                        return new DeviceAdminInfo(source);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.p007os.Parcelable.Creator
                    public DeviceAdminInfo[] newArray(int size) {
                        return new DeviceAdminInfo[size];
                    }
                };
                return;
            }
        }
    }

    public DeviceAdminInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        this(context, resolveInfo.activityInfo);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0093, code lost:
        if (r15 != r12) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0096, code lost:
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0099, code lost:
        r15 = r0.getName();
        r12 = android.app.admin.DeviceAdminInfo.sKnownPolicies.get(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a5, code lost:
        if (r12 == null) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a7, code lost:
        r18.mUsesPolicies |= r8 << r12.intValue();
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b6, code lost:
        r8 = new java.lang.StringBuilder();
        r17 = r0;
        r8.append("Unknown tag under uses-policies of ");
        r8.append(getComponent());
        r8.append(com.ibm.icu.text.PluralRules.KEYWORD_RULE_SEPARATOR);
        r8.append(r15);
        android.util.Log.m64w(android.app.admin.DeviceAdminInfo.TAG, r8.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0114, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DeviceAdminInfo(Context context, ActivityInfo activityInfo) throws XmlPullParserException, IOException {
        int i;
        Resources res;
        int i2;
        this.mActivityInfo = activityInfo;
        PackageManager pm = context.getPackageManager();
        XmlResourceParser parser = null;
        try {
            try {
                parser = this.mActivityInfo.loadXmlMetaData(pm, DeviceAdminReceiver.DEVICE_ADMIN_META_DATA);
                if (parser == null) {
                    throw new XmlPullParserException("No android.app.device_admin meta-data");
                }
                Resources res2 = pm.getResourcesForApplication(this.mActivityInfo.applicationInfo);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                while (true) {
                    int type = parser.next();
                    i = 1;
                    if (type == 1 || type == 2) {
                        break;
                    }
                }
                String nodeName = parser.getName();
                if (!"device-admin".equals(nodeName)) {
                    throw new XmlPullParserException("Meta-data does not start with device-admin tag");
                }
                TypedArray sa = res2.obtainAttributes(attrs, C3132R.styleable.DeviceAdmin);
                this.mVisible = sa.getBoolean(0, true);
                sa.recycle();
                int outerDepth = parser.getDepth();
                while (true) {
                    int type2 = parser.next();
                    if (type2 == i) {
                        break;
                    }
                    int i3 = 3;
                    if (type2 == 3 && parser.getDepth() <= outerDepth) {
                        break;
                    }
                    if (type2 != 3) {
                        int i4 = 4;
                        if (type2 == 4) {
                            res = res2;
                            i2 = i;
                        } else {
                            String tagName = parser.getName();
                            if (tagName.equals("uses-policies")) {
                                int innerDepth = parser.getDepth();
                                while (true) {
                                    int type3 = parser.next();
                                    if (type3 != i) {
                                        if (type3 == i3 && parser.getDepth() <= innerDepth) {
                                            res = res2;
                                            break;
                                        }
                                        Resources res3 = res2;
                                        res2 = res3;
                                        i = 1;
                                        i3 = 3;
                                        i4 = 4;
                                    } else {
                                        res = res2;
                                        break;
                                    }
                                }
                            } else {
                                res = res2;
                                if (tagName.equals("support-transfer-ownership")) {
                                    if (parser.next() != 3) {
                                        throw new XmlPullParserException("support-transfer-ownership tag must be empty.");
                                    }
                                    i2 = 1;
                                    this.mSupportsTransferOwnership = true;
                                }
                            }
                            i2 = 1;
                        }
                    } else {
                        res = res2;
                        i2 = i;
                    }
                    i = i2;
                    res2 = res;
                }
            } catch (PackageManager.NameNotFoundException e) {
                throw new XmlPullParserException("Unable to create context for: " + this.mActivityInfo.packageName);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    DeviceAdminInfo(Parcel source) {
        this.mActivityInfo = ActivityInfo.CREATOR.createFromParcel(source);
        this.mUsesPolicies = source.readInt();
        this.mSupportsTransferOwnership = source.readBoolean();
    }

    public String getPackageName() {
        return this.mActivityInfo.packageName;
    }

    public String getReceiverName() {
        return this.mActivityInfo.name;
    }

    public ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
    }

    public CharSequence loadLabel(PackageManager pm) {
        return this.mActivityInfo.loadLabel(pm);
    }

    public CharSequence loadDescription(PackageManager pm) throws Resources.NotFoundException {
        if (this.mActivityInfo.descriptionRes != 0) {
            return pm.getText(this.mActivityInfo.packageName, this.mActivityInfo.descriptionRes, this.mActivityInfo.applicationInfo);
        }
        throw new Resources.NotFoundException();
    }

    public Drawable loadIcon(PackageManager pm) {
        return this.mActivityInfo.loadIcon(pm);
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean usesPolicy(int policyIdent) {
        return (this.mUsesPolicies & (1 << policyIdent)) != 0;
    }

    public String getTagForPolicy(int policyIdent) {
        return sRevKnownPolicies.get(policyIdent).tag;
    }

    public boolean supportsTransferOwnership() {
        return this.mSupportsTransferOwnership;
    }

    @UnsupportedAppUsage
    public ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> res = new ArrayList<>();
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo pi = sPoliciesDisplayOrder.get(i);
            if (usesPolicy(pi.ident)) {
                res.add(pi);
            }
        }
        return res;
    }

    public void writePoliciesToXml(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        out.attribute(null, "flags", Integer.toString(this.mUsesPolicies));
    }

    public void readPoliciesFromXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        this.mUsesPolicies = Integer.parseInt(parser.getAttributeValue(null, "flags"));
    }

    public void dump(Printer pw, String prefix) {
        pw.println(prefix + "Receiver:");
        ActivityInfo activityInfo = this.mActivityInfo;
        activityInfo.dump(pw, prefix + "  ");
    }

    public String toString() {
        return "DeviceAdminInfo{" + this.mActivityInfo.name + "}";
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mActivityInfo.writeToParcel(dest, flags);
        dest.writeInt(this.mUsesPolicies);
        dest.writeBoolean(this.mSupportsTransferOwnership);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
