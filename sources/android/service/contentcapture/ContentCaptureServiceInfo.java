package android.service.contentcapture;

import android.Manifest;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParserException;

public final class ContentCaptureServiceInfo {
    private static final String TAG = ContentCaptureServiceInfo.class.getSimpleName();
    private static final String XML_TAG_SERVICE = "content-capture-service";
    private final ServiceInfo mServiceInfo;
    private final String mSettingsActivity;

    private static ServiceInfo getServiceInfoOrThrow(ComponentName comp, boolean isTemp, int userId) throws PackageManager.NameNotFoundException {
        int flags = 128;
        if (!isTemp) {
            flags = 128 | 1048576;
        }
        ServiceInfo si = null;
        try {
            si = AppGlobals.getPackageManager().getServiceInfo(comp, flags, userId);
        } catch (RemoteException e) {
        }
        if (si != null) {
            return si;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Could not get serviceInfo for ");
        sb.append(isTemp ? " (temp)" : "(default system)");
        sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        sb.append(comp.flattenToShortString());
        throw new PackageManager.NameNotFoundException(sb.toString());
    }

    public ContentCaptureServiceInfo(Context context, ComponentName comp, boolean isTemporaryService, int userId) throws PackageManager.NameNotFoundException {
        this(context, getServiceInfoOrThrow(comp, isTemporaryService, userId));
    }

    /* JADX WARNING: type inference failed for: r1v5, types: [java.lang.String, android.content.res.TypedArray] */
    private ContentCaptureServiceInfo(Context context, ServiceInfo si) {
        if (Manifest.permission.BIND_CONTENT_CAPTURE_SERVICE.equals(si.permission)) {
            this.mServiceInfo = si;
            XmlResourceParser parser = si.loadXmlMetaData(context.getPackageManager(), ContentCaptureService.SERVICE_META_DATA);
            ? r1 = 0;
            if (parser == null) {
                this.mSettingsActivity = r1;
                return;
            }
            String settingsActivity = r1;
            try {
                Resources resources = context.getPackageManager().getResourcesForApplication(si.applicationInfo);
                int type = 0;
                while (type != 1 && type != 2) {
                    type = parser.next();
                }
                if (XML_TAG_SERVICE.equals(parser.getName())) {
                    TypedArray afsAttributes = resources.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.ContentCaptureService);
                    settingsActivity = afsAttributes.getString(0);
                    if (afsAttributes != null) {
                        afsAttributes.recycle();
                    }
                } else {
                    Log.e(TAG, "Meta-data does not start with content-capture-service tag");
                }
            } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
                Log.e(TAG, "Error parsing auto fill service meta-data", e);
            } catch (Throwable th) {
                if (r1 != 0) {
                    r1.recycle();
                }
                throw th;
            }
            this.mSettingsActivity = settingsActivity;
            return;
        }
        Slog.w(TAG, "ContentCaptureService from '" + si.packageName + "' does not require permission " + Manifest.permission.BIND_CONTENT_CAPTURE_SERVICE);
        throw new SecurityException("Service does not require permission android.permission.BIND_CONTENT_CAPTURE_SERVICE");
    }

    public ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public String toString() {
        return getClass().getSimpleName() + "[" + this.mServiceInfo + ", settings:" + this.mSettingsActivity;
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.print(prefix);
        pw.print("Component: ");
        pw.println(getServiceInfo().getComponentName());
        pw.print(prefix);
        pw.print("Settings: ");
        pw.println(this.mSettingsActivity);
    }
}
