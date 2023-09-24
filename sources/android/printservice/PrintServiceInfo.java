package android.printservice;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.C3132R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes3.dex */
public final class PrintServiceInfo implements Parcelable {
    private static final String TAG_PRINT_SERVICE = "print-service";
    private final String mAddPrintersActivityName;
    private final String mAdvancedPrintOptionsActivityName;
    private final String mId;
    private boolean mIsEnabled;
    private final ResolveInfo mResolveInfo;
    private final String mSettingsActivityName;
    private static final String LOG_TAG = PrintServiceInfo.class.getSimpleName();
    public static final Parcelable.Creator<PrintServiceInfo> CREATOR = new Parcelable.Creator<PrintServiceInfo>() { // from class: android.printservice.PrintServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PrintServiceInfo createFromParcel(Parcel parcel) {
            return new PrintServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PrintServiceInfo[] newArray(int size) {
            return new PrintServiceInfo[size];
        }
    };

    public PrintServiceInfo(Parcel parcel) {
        this.mId = parcel.readString();
        this.mIsEnabled = parcel.readByte() != 0;
        this.mResolveInfo = (ResolveInfo) parcel.readParcelable(null);
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

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00b1, code lost:
        if (r3 == null) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PrintServiceInfo create(Context context, ResolveInfo resolveInfo) {
        String settingsActivityName = null;
        String addPrintersActivityName = null;
        String advancedPrintOptionsActivityName = null;
        PackageManager packageManager = context.getPackageManager();
        XmlResourceParser parser = resolveInfo.serviceInfo.loadXmlMetaData(packageManager, PrintService.SERVICE_META_DATA);
        if (parser != null) {
            for (int type = 0; type != 1 && type != 2; type = parser.next()) {
                try {
                    try {
                        try {
                            try {
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.m70e(LOG_TAG, "Unable to load resources for: " + resolveInfo.serviceInfo.packageName);
                                if (parser != null) {
                                }
                            }
                        } catch (XmlPullParserException xppe) {
                            Log.m64w(LOG_TAG, "Error reading meta-data:" + xppe);
                            if (parser != null) {
                            }
                        }
                    } catch (IOException ioe) {
                        Log.m64w(LOG_TAG, "Error reading meta-data:" + ioe);
                    }
                } finally {
                    if (parser != null) {
                        parser.close();
                    }
                }
            }
            String nodeName = parser.getName();
            if (TAG_PRINT_SERVICE.equals(nodeName)) {
                Resources resources = packageManager.getResourcesForApplication(resolveInfo.serviceInfo.applicationInfo);
                AttributeSet allAttributes = Xml.asAttributeSet(parser);
                TypedArray attributes = resources.obtainAttributes(allAttributes, C3132R.styleable.PrintService);
                settingsActivityName = attributes.getString(0);
                addPrintersActivityName = attributes.getString(1);
                advancedPrintOptionsActivityName = attributes.getString(3);
                attributes.recycle();
            } else {
                Log.m70e(LOG_TAG, "Ignoring meta-data that does not start with print-service tag");
            }
        }
        return new PrintServiceInfo(resolveInfo, settingsActivityName, addPrintersActivityName, advancedPrintOptionsActivityName);
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

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flagz) {
        parcel.writeString(this.mId);
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : (byte) 0);
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
        return "PrintServiceInfo{id=" + this.mId + "isEnabled=" + this.mIsEnabled + ", resolveInfo=" + this.mResolveInfo + ", settingsActivityName=" + this.mSettingsActivityName + ", addPrintersActivityName=" + this.mAddPrintersActivityName + ", advancedPrintOptionsActivityName=" + this.mAdvancedPrintOptionsActivityName + "}";
    }
}
