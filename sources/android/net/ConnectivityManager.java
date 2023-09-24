package android.net;

import android.Manifest;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.INetworkPolicyManager;
import android.net.ISocketKeepaliveCallback;
import android.net.ITetheringEventCallback;
import android.net.IpSecManager;
import android.net.NetworkRequest;
import android.net.SocketKeepalive;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.INetworkActivityListener;
import android.p007os.INetworkManagementService;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.Messenger;
import android.p007os.ParcelFileDescriptor;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.ResultReceiver;
import android.p007os.ServiceManager;
import android.p007os.ServiceSpecificException;
import android.provider.Settings;
import android.security.keystore.KeyProperties;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.C3132R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import libcore.net.event.NetworkEventDispatcher;

/* loaded from: classes3.dex */
public class ConnectivityManager {
    @Deprecated
    public static final String ACTION_BACKGROUND_DATA_SETTING_CHANGED = "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED";
    public static final String ACTION_CAPTIVE_PORTAL_SIGN_IN = "android.net.conn.CAPTIVE_PORTAL";
    public static final String ACTION_CAPTIVE_PORTAL_TEST_COMPLETED = "android.net.conn.CAPTIVE_PORTAL_TEST_COMPLETED";
    public static final String ACTION_DATA_ACTIVITY_CHANGE = "android.net.conn.DATA_ACTIVITY_CHANGE";
    public static final String ACTION_PROMPT_LOST_VALIDATION = "android.net.conn.PROMPT_LOST_VALIDATION";
    public static final String ACTION_PROMPT_PARTIAL_CONNECTIVITY = "android.net.conn.PROMPT_PARTIAL_CONNECTIVITY";
    public static final String ACTION_PROMPT_UNVALIDATED = "android.net.conn.PROMPT_UNVALIDATED";
    public static final String ACTION_RESTRICT_BACKGROUND_CHANGED = "android.net.conn.RESTRICT_BACKGROUND_CHANGED";
    @UnsupportedAppUsage
    public static final String ACTION_TETHER_STATE_CHANGED = "android.net.conn.TETHER_STATE_CHANGED";
    private static final int BASE = 524288;
    public static final int CALLBACK_AVAILABLE = 524290;
    public static final int CALLBACK_BLK_CHANGED = 524299;
    public static final int CALLBACK_CAP_CHANGED = 524294;
    public static final int CALLBACK_IP_CHANGED = 524295;
    public static final int CALLBACK_LOSING = 524291;
    public static final int CALLBACK_LOST = 524292;
    public static final int CALLBACK_PRECHECK = 524289;
    public static final int CALLBACK_RESUMED = 524298;
    public static final int CALLBACK_SUSPENDED = 524297;
    public static final int CALLBACK_UNAVAIL = 524293;
    @Deprecated
    public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String CONNECTIVITY_ACTION_SUPL = "android.net.conn.CONNECTIVITY_CHANGE_SUPL";
    @Deprecated
    public static final int DEFAULT_NETWORK_PREFERENCE = 1;
    private static final int EXPIRE_LEGACY_REQUEST = 524296;
    public static final String EXTRA_ACTIVE_LOCAL_ONLY = "localOnlyArray";
    @UnsupportedAppUsage
    public static final String EXTRA_ACTIVE_TETHER = "tetherArray";
    public static final String EXTRA_ADD_TETHER_TYPE = "extraAddTetherType";
    @UnsupportedAppUsage
    public static final String EXTRA_AVAILABLE_TETHER = "availableArray";
    public static final String EXTRA_CAPTIVE_PORTAL = "android.net.extra.CAPTIVE_PORTAL";
    @SystemApi
    public static final String EXTRA_CAPTIVE_PORTAL_PROBE_SPEC = "android.net.extra.CAPTIVE_PORTAL_PROBE_SPEC";
    public static final String EXTRA_CAPTIVE_PORTAL_URL = "android.net.extra.CAPTIVE_PORTAL_URL";
    @SystemApi
    public static final String EXTRA_CAPTIVE_PORTAL_USER_AGENT = "android.net.extra.CAPTIVE_PORTAL_USER_AGENT";
    public static final String EXTRA_DEVICE_TYPE = "deviceType";
    @UnsupportedAppUsage
    public static final String EXTRA_ERRORED_TETHER = "erroredArray";
    @Deprecated
    public static final String EXTRA_EXTRA_INFO = "extraInfo";
    public static final String EXTRA_INET_CONDITION = "inetCondition";
    public static final String EXTRA_IS_ACTIVE = "isActive";
    public static final String EXTRA_IS_CAPTIVE_PORTAL = "captivePortal";
    @Deprecated
    public static final String EXTRA_IS_FAILOVER = "isFailover";
    public static final String EXTRA_NETWORK = "android.net.extra.NETWORK";
    @Deprecated
    public static final String EXTRA_NETWORK_INFO = "networkInfo";
    public static final String EXTRA_NETWORK_REQUEST = "android.net.extra.NETWORK_REQUEST";
    @Deprecated
    public static final String EXTRA_NETWORK_TYPE = "networkType";
    public static final String EXTRA_NO_CONNECTIVITY = "noConnectivity";
    @Deprecated
    public static final String EXTRA_OTHER_NETWORK_INFO = "otherNetwork";
    public static final String EXTRA_PROVISION_CALLBACK = "extraProvisionCallback";
    public static final String EXTRA_REALTIME_NS = "tsNanos";
    public static final String EXTRA_REASON = "reason";
    public static final String EXTRA_REM_TETHER_TYPE = "extraRemTetherType";
    public static final String EXTRA_RUN_PROVISION = "extraRunProvision";
    public static final String EXTRA_SET_ALARM = "extraSetAlarm";
    @UnsupportedAppUsage
    public static final String INET_CONDITION_ACTION = "android.net.conn.INET_CONDITION_ACTION";
    private static final int LISTEN = 1;
    public static final int MAX_NETWORK_TYPE = 18;
    public static final int MAX_RADIO_TYPE = 18;
    private static final int MIN_NETWORK_TYPE = 0;
    public static final int MULTIPATH_PREFERENCE_HANDOVER = 1;
    public static final int MULTIPATH_PREFERENCE_PERFORMANCE = 4;
    public static final int MULTIPATH_PREFERENCE_RELIABILITY = 2;
    public static final int MULTIPATH_PREFERENCE_UNMETERED = 7;
    public static final int NETID_UNSET = 0;
    public static final String PRIVATE_DNS_DEFAULT_MODE_FALLBACK = "opportunistic";
    public static final String PRIVATE_DNS_MODE_OFF = "off";
    public static final String PRIVATE_DNS_MODE_OPPORTUNISTIC = "opportunistic";
    public static final String PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = "hostname";
    private static final int REQUEST = 2;
    public static final int REQUEST_ID_UNSET = 0;
    public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
    public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
    public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;
    @SystemApi
    public static final int TETHERING_BLUETOOTH = 2;
    public static final int TETHERING_INVALID = -1;
    @SystemApi
    public static final int TETHERING_USB = 1;
    @SystemApi
    public static final int TETHERING_WIFI = 0;
    public static final int TETHERING_WIGIG = 3;
    public static final int TETHER_ERROR_DHCPSERVER_ERROR = 12;
    public static final int TETHER_ERROR_DISABLE_NAT_ERROR = 9;
    public static final int TETHER_ERROR_ENABLE_NAT_ERROR = 8;
    @SystemApi
    public static final int TETHER_ERROR_ENTITLEMENT_UNKONWN = 13;
    public static final int TETHER_ERROR_IFACE_CFG_ERROR = 10;
    public static final int TETHER_ERROR_MASTER_ERROR = 5;
    @SystemApi
    public static final int TETHER_ERROR_NO_ERROR = 0;
    @SystemApi
    public static final int TETHER_ERROR_PROVISION_FAILED = 11;
    public static final int TETHER_ERROR_SERVICE_UNAVAIL = 2;
    public static final int TETHER_ERROR_TETHER_IFACE_ERROR = 6;
    public static final int TETHER_ERROR_UNAVAIL_IFACE = 4;
    public static final int TETHER_ERROR_UNKNOWN_IFACE = 1;
    public static final int TETHER_ERROR_UNSUPPORTED = 3;
    public static final int TETHER_ERROR_UNTETHER_IFACE_ERROR = 7;
    @Deprecated
    public static final int TYPE_BLUETOOTH = 7;
    @Deprecated
    public static final int TYPE_DUMMY = 8;
    @Deprecated
    public static final int TYPE_ETHERNET = 9;
    @Deprecated
    public static final int TYPE_MOBILE = 0;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    @Deprecated
    public static final int TYPE_MOBILE_CBS = 12;
    @Deprecated
    public static final int TYPE_MOBILE_DUN = 4;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    @Deprecated
    public static final int TYPE_MOBILE_EMERGENCY = 15;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    @Deprecated
    public static final int TYPE_MOBILE_FOTA = 10;
    @Deprecated
    public static final int TYPE_MOBILE_HIPRI = 5;
    @UnsupportedAppUsage
    @Deprecated
    public static final int TYPE_MOBILE_IA = 14;
    @UnsupportedAppUsage
    @Deprecated
    public static final int TYPE_MOBILE_IMS = 11;
    @Deprecated
    public static final int TYPE_MOBILE_MMS = 2;
    @Deprecated
    public static final int TYPE_MOBILE_SUPL = 3;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    public static final int TYPE_NONE = -1;
    @UnsupportedAppUsage
    @Deprecated
    public static final int TYPE_PROXY = 16;
    @Deprecated
    public static final int TYPE_TEST = 18;
    @Deprecated
    public static final int TYPE_VPN = 17;
    @Deprecated
    public static final int TYPE_WIFI = 1;
    @UnsupportedAppUsage
    @Deprecated
    public static final int TYPE_WIFI_P2P = 13;
    @Deprecated
    public static final int TYPE_WIMAX = 6;
    private static CallbackHandler sCallbackHandler;
    private static final HashMap<NetworkRequest, NetworkCallback> sCallbacks;
    private static ConnectivityManager sInstance;
    private static final SparseIntArray sLegacyTypeToCapability;
    private final Context mContext;
    private INetworkManagementService mNMService;
    private INetworkPolicyManager mNPManager;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    private final IConnectivityManager mService;
    private static final String TAG = "ConnectivityManager";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final NetworkRequest ALREADY_UNREGISTERED = new NetworkRequest.Builder().clearCapabilities().build();
    @UnsupportedAppUsage
    private static final HashMap<NetworkCapabilities, LegacyRequest> sLegacyRequests = new HashMap<>();
    private static final SparseIntArray sLegacyTypeToTransport = new SparseIntArray();
    private final ArrayMap<OnNetworkActiveListener, INetworkActivityListener> mNetworkActivityListeners = new ArrayMap<>();
    @GuardedBy({"mTetheringEventCallbacks"})
    private final ArrayMap<OnTetheringEventCallback, ITetheringEventCallback> mTetheringEventCallbacks = new ArrayMap<>();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface EntitlementResultCode {
    }

    /* loaded from: classes3.dex */
    public interface Errors {
        public static final int TOO_MANY_REQUESTS = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface MultipathPreference {
    }

    /* loaded from: classes3.dex */
    public interface OnNetworkActiveListener {
        void onNetworkActive();
    }

    @SystemApi
    /* loaded from: classes3.dex */
    public interface OnTetheringEntitlementResultListener {
        void onTetheringEntitlementResult(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface RestrictBackgroundStatus {
    }

    /* loaded from: classes3.dex */
    public static class TooManyRequestsException extends RuntimeException {
    }

    static {
        sLegacyTypeToTransport.put(0, 0);
        sLegacyTypeToTransport.put(12, 0);
        sLegacyTypeToTransport.put(4, 0);
        sLegacyTypeToTransport.put(10, 0);
        sLegacyTypeToTransport.put(5, 0);
        sLegacyTypeToTransport.put(11, 0);
        sLegacyTypeToTransport.put(2, 0);
        sLegacyTypeToTransport.put(3, 0);
        sLegacyTypeToTransport.put(1, 1);
        sLegacyTypeToTransport.put(13, 1);
        sLegacyTypeToTransport.put(7, 2);
        sLegacyTypeToTransport.put(9, 3);
        sLegacyTypeToCapability = new SparseIntArray();
        sLegacyTypeToCapability.put(12, 5);
        sLegacyTypeToCapability.put(4, 2);
        sLegacyTypeToCapability.put(10, 3);
        sLegacyTypeToCapability.put(11, 4);
        sLegacyTypeToCapability.put(2, 0);
        sLegacyTypeToCapability.put(3, 1);
        sLegacyTypeToCapability.put(13, 6);
        sCallbacks = new HashMap<>();
    }

    @Deprecated
    public static boolean isNetworkTypeValid(int networkType) {
        return networkType >= 0 && networkType <= 18;
    }

    @UnsupportedAppUsage
    @Deprecated
    public static String getNetworkTypeName(int type) {
        switch (type) {
            case -1:
                return KeyProperties.DIGEST_NONE;
            case 0:
                return "MOBILE";
            case 1:
                return "WIFI";
            case 2:
                return "MOBILE_MMS";
            case 3:
                return "MOBILE_SUPL";
            case 4:
                return "MOBILE_DUN";
            case 5:
                return "MOBILE_HIPRI";
            case 6:
                return "WIMAX";
            case 7:
                return "BLUETOOTH";
            case 8:
                return "DUMMY";
            case 9:
                return "ETHERNET";
            case 10:
                return "MOBILE_FOTA";
            case 11:
                return "MOBILE_IMS";
            case 12:
                return "MOBILE_CBS";
            case 13:
                return "WIFI_P2P";
            case 14:
                return "MOBILE_IA";
            case 15:
                return "MOBILE_EMERGENCY";
            case 16:
                return "PROXY";
            case 17:
                return "VPN";
            default:
                return Integer.toString(type);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    @Deprecated
    public static boolean isNetworkTypeMobile(int networkType) {
        switch (networkType) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
                return true;
            case 1:
            case 6:
            case 7:
            case 8:
            case 9:
            case 13:
            default:
                return false;
        }
    }

    @Deprecated
    public static boolean isNetworkTypeWifi(int networkType) {
        if (networkType == 1 || networkType == 13) {
            return true;
        }
        return false;
    }

    @Deprecated
    public void setNetworkPreference(int preference) {
    }

    @Deprecated
    public int getNetworkPreference() {
        return -1;
    }

    @Deprecated
    public NetworkInfo getActiveNetworkInfo() {
        try {
            return this.mService.getActiveNetworkInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Network getActiveNetwork() {
        try {
            return this.mService.getActiveNetwork();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Network getActiveNetworkForUid(int uid) {
        return getActiveNetworkForUid(uid, false);
    }

    public Network getActiveNetworkForUid(int uid, boolean ignoreBlocked) {
        try {
            return this.mService.getActiveNetworkForUid(uid, ignoreBlocked);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAlwaysOnVpnPackageSupportedForUser(int userId, String vpnPackage) {
        try {
            return this.mService.isAlwaysOnVpnPackageSupported(userId, vpnPackage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAlwaysOnVpnPackageForUser(int userId, String vpnPackage, boolean lockdownEnabled, List<String> lockdownWhitelist) {
        try {
            return this.mService.setAlwaysOnVpnPackage(userId, vpnPackage, lockdownEnabled, lockdownWhitelist);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAlwaysOnVpnPackageForUser(int userId) {
        try {
            return this.mService.getAlwaysOnVpnPackage(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVpnLockdownEnabled(int userId) {
        try {
            return this.mService.isVpnLockdownEnabled(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getVpnLockdownWhitelist(int userId) {
        try {
            return this.mService.getVpnLockdownWhitelist(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public NetworkInfo getActiveNetworkInfoForUid(int uid) {
        return getActiveNetworkInfoForUid(uid, false);
    }

    public NetworkInfo getActiveNetworkInfoForUid(int uid, boolean ignoreBlocked) {
        try {
            return this.mService.getActiveNetworkInfoForUid(uid, ignoreBlocked);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo getNetworkInfo(int networkType) {
        try {
            return this.mService.getNetworkInfo(networkType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo getNetworkInfo(Network network) {
        return getNetworkInfoForUid(network, Process.myUid(), false);
    }

    public NetworkInfo getNetworkInfoForUid(Network network, int uid, boolean ignoreBlocked) {
        try {
            return this.mService.getNetworkInfoForUid(network, uid, ignoreBlocked);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo[] getAllNetworkInfo() {
        try {
            return this.mService.getAllNetworkInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    @Deprecated
    public Network getNetworkForType(int networkType) {
        try {
            return this.mService.getNetworkForType(networkType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Network[] getAllNetworks() {
        try {
            return this.mService.getAllNetworks();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int userId) {
        try {
            return this.mService.getDefaultNetworkCapabilitiesForUser(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 109783091)
    public LinkProperties getActiveLinkProperties() {
        try {
            return this.mService.getActiveLinkProperties();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    @Deprecated
    public LinkProperties getLinkProperties(int networkType) {
        try {
            return this.mService.getLinkPropertiesForType(networkType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LinkProperties getLinkProperties(Network network) {
        try {
            return this.mService.getLinkProperties(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NetworkCapabilities getNetworkCapabilities(Network network) {
        try {
            return this.mService.getNetworkCapabilities(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getCaptivePortalServerUrl() {
        try {
            return this.mService.getCaptivePortalServerUrl();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int startUsingNetworkFeature(int networkType, String feature) {
        checkLegacyRoutingApiAccess();
        NetworkCapabilities netCap = networkCapabilitiesForFeature(networkType, feature);
        if (netCap == null) {
            Log.m72d(TAG, "Can't satisfy startUsingNetworkFeature for " + networkType + ", " + feature);
            return 3;
        }
        synchronized (sLegacyRequests) {
            LegacyRequest l = sLegacyRequests.get(netCap);
            if (l != null) {
                Log.m72d(TAG, "renewing startUsingNetworkFeature request " + l.networkRequest);
                renewRequestLocked(l);
                if (l.currentNetwork == null) {
                    return 1;
                }
                return 0;
            }
            NetworkRequest request = requestNetworkForFeatureLocked(netCap);
            if (request != null) {
                Log.m72d(TAG, "starting startUsingNetworkFeature for request " + request);
                return 1;
            }
            Log.m72d(TAG, " request Failed");
            return 3;
        }
    }

    @Deprecated
    public int stopUsingNetworkFeature(int networkType, String feature) {
        checkLegacyRoutingApiAccess();
        NetworkCapabilities netCap = networkCapabilitiesForFeature(networkType, feature);
        if (netCap == null) {
            Log.m72d(TAG, "Can't satisfy stopUsingNetworkFeature for " + networkType + ", " + feature);
            return -1;
        } else if (removeRequestForFeature(netCap)) {
            Log.m72d(TAG, "stopUsingNetworkFeature for " + networkType + ", " + feature);
            return 1;
        } else {
            return 1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0049, code lost:
        if (r10.equals("enableDUN") != false) goto L8;
     */
    @UnsupportedAppUsage
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private NetworkCapabilities networkCapabilitiesForFeature(int networkType, String feature) {
        char c = 1;
        if (networkType == 0) {
            switch (feature.hashCode()) {
                case -1451370941:
                    if (feature.equals("enableHIPRI")) {
                        c = 4;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -631682191:
                    if (feature.equals("enableCBS")) {
                        c = 0;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -631680646:
                    break;
                case -631676084:
                    if (feature.equals("enableIMS")) {
                        c = 5;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -631672240:
                    if (feature.equals("enableMMS")) {
                        c = 6;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1892790521:
                    if (feature.equals("enableFOTA")) {
                        c = 3;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1893183457:
                    if (feature.equals("enableSUPL")) {
                        c = 7;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1998933033:
                    if (feature.equals("enableDUNAlways")) {
                        c = 2;
                        break;
                    }
                    c = '\uffff';
                    break;
                default:
                    c = '\uffff';
                    break;
            }
            switch (c) {
                case 0:
                    return networkCapabilitiesForType(12);
                case 1:
                case 2:
                    return networkCapabilitiesForType(4);
                case 3:
                    return networkCapabilitiesForType(10);
                case 4:
                    return networkCapabilitiesForType(5);
                case 5:
                    return networkCapabilitiesForType(11);
                case 6:
                    return networkCapabilitiesForType(2);
                case 7:
                    return networkCapabilitiesForType(3);
                default:
                    return null;
            }
        } else if (networkType != 1 || !"p2p".equals(feature)) {
            return null;
        } else {
            return networkCapabilitiesForType(13);
        }
    }

    private int inferLegacyTypeForNetworkCapabilities(NetworkCapabilities netCap) {
        if (netCap == null || !netCap.hasTransport(0) || !netCap.hasCapability(1)) {
            return -1;
        }
        String type = null;
        int result = -1;
        if (netCap.hasCapability(5)) {
            type = "enableCBS";
            result = 12;
        } else if (netCap.hasCapability(4)) {
            type = "enableIMS";
            result = 11;
        } else if (netCap.hasCapability(3)) {
            type = "enableFOTA";
            result = 10;
        } else if (netCap.hasCapability(2)) {
            type = "enableDUN";
            result = 4;
        } else if (netCap.hasCapability(1)) {
            type = "enableSUPL";
            result = 3;
        } else if (netCap.hasCapability(12)) {
            type = "enableHIPRI";
            result = 5;
        }
        if (type != null) {
            NetworkCapabilities testCap = networkCapabilitiesForFeature(0, type);
            if (testCap.equalsNetCapabilities(netCap) && testCap.equalsTransportTypes(netCap)) {
                return result;
            }
        }
        return -1;
    }

    private int legacyTypeForNetworkCapabilities(NetworkCapabilities netCap) {
        if (netCap == null) {
            return -1;
        }
        if (netCap.hasCapability(5)) {
            return 12;
        }
        if (netCap.hasCapability(4)) {
            return 11;
        }
        if (netCap.hasCapability(3)) {
            return 10;
        }
        if (netCap.hasCapability(2)) {
            return 4;
        }
        if (netCap.hasCapability(1)) {
            return 3;
        }
        if (netCap.hasCapability(0)) {
            return 2;
        }
        if (netCap.hasCapability(12)) {
            return 5;
        }
        if (!netCap.hasCapability(6)) {
            return -1;
        }
        return 13;
    }

    /* loaded from: classes3.dex */
    private static class LegacyRequest {
        Network currentNetwork;
        int delay;
        int expireSequenceNumber;
        NetworkCallback networkCallback;
        NetworkCapabilities networkCapabilities;
        NetworkRequest networkRequest;

        private LegacyRequest() {
            this.delay = -1;
            this.networkCallback = new NetworkCallback() { // from class: android.net.ConnectivityManager.LegacyRequest.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    LegacyRequest.this.currentNetwork = network;
                    Log.m72d(ConnectivityManager.TAG, "startUsingNetworkFeature got Network:" + network);
                    ConnectivityManager.setProcessDefaultNetworkForHostResolution(network);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    if (network.equals(LegacyRequest.this.currentNetwork)) {
                        LegacyRequest.this.clearDnsBinding();
                    }
                    Log.m72d(ConnectivityManager.TAG, "startUsingNetworkFeature lost Network:" + network);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDnsBinding() {
            if (this.currentNetwork != null) {
                this.currentNetwork = null;
                ConnectivityManager.setProcessDefaultNetworkForHostResolution(null);
            }
        }
    }

    private NetworkRequest findRequestForFeature(NetworkCapabilities netCap) {
        synchronized (sLegacyRequests) {
            LegacyRequest l = sLegacyRequests.get(netCap);
            if (l != null) {
                return l.networkRequest;
            }
            return null;
        }
    }

    private void renewRequestLocked(LegacyRequest l) {
        l.expireSequenceNumber++;
        Log.m72d(TAG, "renewing request to seqNum " + l.expireSequenceNumber);
        sendExpireMsgForFeature(l.networkCapabilities, l.expireSequenceNumber, l.delay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void expireRequest(NetworkCapabilities netCap, int sequenceNum) {
        synchronized (sLegacyRequests) {
            LegacyRequest l = sLegacyRequests.get(netCap);
            if (l == null) {
                return;
            }
            int ourSeqNum = l.expireSequenceNumber;
            if (l.expireSequenceNumber == sequenceNum) {
                removeRequestForFeature(netCap);
            }
            Log.m72d(TAG, "expireRequest with " + ourSeqNum + ", " + sequenceNum);
        }
    }

    @UnsupportedAppUsage
    private NetworkRequest requestNetworkForFeatureLocked(NetworkCapabilities netCap) {
        int type = legacyTypeForNetworkCapabilities(netCap);
        try {
            int delay = this.mService.getRestoreDefaultNetworkDelay(type);
            LegacyRequest l = new LegacyRequest();
            l.networkCapabilities = netCap;
            l.delay = delay;
            l.expireSequenceNumber = 0;
            l.networkRequest = sendRequestForNetwork(netCap, l.networkCallback, 0, 2, type, getDefaultHandler());
            if (l.networkRequest == null) {
                return null;
            }
            sLegacyRequests.put(netCap, l);
            sendExpireMsgForFeature(netCap, l.expireSequenceNumber, delay);
            return l.networkRequest;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void sendExpireMsgForFeature(NetworkCapabilities netCap, int seqNum, int delay) {
        if (delay >= 0) {
            Log.m72d(TAG, "sending expire msg with seqNum " + seqNum + " and delay " + delay);
            CallbackHandler handler = getDefaultHandler();
            Message msg = handler.obtainMessage(EXPIRE_LEGACY_REQUEST, seqNum, 0, netCap);
            handler.sendMessageDelayed(msg, (long) delay);
        }
    }

    @UnsupportedAppUsage
    private boolean removeRequestForFeature(NetworkCapabilities netCap) {
        LegacyRequest l;
        synchronized (sLegacyRequests) {
            l = sLegacyRequests.remove(netCap);
        }
        if (l == null) {
            return false;
        }
        unregisterNetworkCallback(l.networkCallback);
        l.clearDnsBinding();
        return true;
    }

    public static NetworkCapabilities networkCapabilitiesForType(int type) {
        NetworkCapabilities nc = new NetworkCapabilities();
        int transport = sLegacyTypeToTransport.get(type, -1);
        boolean z = transport != -1;
        Preconditions.checkArgument(z, "unknown legacy type: " + type);
        nc.addTransportType(transport);
        nc.addCapability(sLegacyTypeToCapability.get(type, 12));
        nc.maybeMarkCapabilitiesRestricted();
        return nc;
    }

    /* loaded from: classes3.dex */
    public static class PacketKeepaliveCallback {
        @UnsupportedAppUsage
        public void onStarted() {
        }

        @UnsupportedAppUsage
        public void onStopped() {
        }

        @UnsupportedAppUsage
        public void onError(int error) {
        }
    }

    /* loaded from: classes3.dex */
    public class PacketKeepalive {
        public static final int BINDER_DIED = -10;
        public static final int ERROR_HARDWARE_ERROR = -31;
        public static final int ERROR_HARDWARE_UNSUPPORTED = -30;
        public static final int ERROR_INVALID_INTERVAL = -24;
        public static final int ERROR_INVALID_IP_ADDRESS = -21;
        public static final int ERROR_INVALID_LENGTH = -23;
        public static final int ERROR_INVALID_NETWORK = -20;
        public static final int ERROR_INVALID_PORT = -22;
        public static final int MIN_INTERVAL = 10;
        public static final int NATT_PORT = 4500;
        public static final int NO_KEEPALIVE = -1;
        public static final int SUCCESS = 0;
        private static final String TAG = "PacketKeepalive";
        private final ISocketKeepaliveCallback mCallback;
        private final ExecutorService mExecutor;
        private final Network mNetwork;
        private volatile Integer mSlot;

        @UnsupportedAppUsage
        public void stop() {
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$--8nwufwzyblnuYRFEYIKx7L4Vg
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConnectivityManager.PacketKeepalive.lambda$stop$0(ConnectivityManager.PacketKeepalive.this);
                    }
                });
            } catch (RejectedExecutionException e) {
            }
        }

        public static /* synthetic */ void lambda$stop$0(PacketKeepalive packetKeepalive) {
            try {
                if (packetKeepalive.mSlot != null) {
                    ConnectivityManager.this.mService.stopKeepalive(packetKeepalive.mNetwork, packetKeepalive.mSlot.intValue());
                }
            } catch (RemoteException e) {
                Log.m69e(TAG, "Error stopping packet keepalive: ", e);
                throw e.rethrowFromSystemServer();
            }
        }

        private PacketKeepalive(Network network, PacketKeepaliveCallback callback) {
            Preconditions.checkNotNull(network, "network cannot be null");
            Preconditions.checkNotNull(callback, "callback cannot be null");
            this.mNetwork = network;
            this.mExecutor = Executors.newSingleThreadExecutor();
            this.mCallback = new BinderC12681(ConnectivityManager.this, callback);
        }

        /* renamed from: android.net.ConnectivityManager$PacketKeepalive$1 */
        /* loaded from: classes3.dex */
        class BinderC12681 extends ISocketKeepaliveCallback.Stub {
            final /* synthetic */ PacketKeepaliveCallback val$callback;
            final /* synthetic */ ConnectivityManager val$this$0;

            BinderC12681(ConnectivityManager connectivityManager, PacketKeepaliveCallback packetKeepaliveCallback) {
                this.val$this$0 = connectivityManager;
                this.val$callback = packetKeepaliveCallback;
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onStarted(final int slot) {
                final PacketKeepaliveCallback packetKeepaliveCallback = this.val$callback;
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$1$iOtsqOYp69ztB6u3PYNu-iI_PGo
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        ConnectivityManager.PacketKeepalive.this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$1$NfMgP6Nh6Ep6LcaiJ10o_zBccII
                            @Override // java.lang.Runnable
                            public final void run() {
                                ConnectivityManager.PacketKeepalive.BinderC12681.lambda$onStarted$0(ConnectivityManager.PacketKeepalive.BinderC12681.this, r2, r3);
                            }
                        });
                    }
                });
            }

            public static /* synthetic */ void lambda$onStarted$0(BinderC12681 binderC12681, int slot, PacketKeepaliveCallback callback) {
                PacketKeepalive.this.mSlot = Integer.valueOf(slot);
                callback.onStarted();
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onStopped() {
                final PacketKeepaliveCallback packetKeepaliveCallback = this.val$callback;
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$1$-H5tzn67t3ydWL8tXpl9UyOmDcc
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        ConnectivityManager.PacketKeepalive.this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$1$WmmtbYWlzqL-V8wWUDKe3CWjvy0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ConnectivityManager.PacketKeepalive.BinderC12681.lambda$onStopped$2(ConnectivityManager.PacketKeepalive.BinderC12681.this, r2);
                            }
                        });
                    }
                });
                PacketKeepalive.this.mExecutor.shutdown();
            }

            public static /* synthetic */ void lambda$onStopped$2(BinderC12681 binderC12681, PacketKeepaliveCallback callback) {
                PacketKeepalive.this.mSlot = null;
                callback.onStopped();
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onError(final int error) {
                final PacketKeepaliveCallback packetKeepaliveCallback = this.val$callback;
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$1$nt5Pgsn85fhX6h9EJ0eAK_PXAjU
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        ConnectivityManager.PacketKeepalive.this.mExecutor.execute(new Runnable() { // from class: android.net.-$$Lambda$ConnectivityManager$PacketKeepalive$1$JWcQQZv8Qrs81cZ-BMAOZZ8MUeU
                            @Override // java.lang.Runnable
                            public final void run() {
                                ConnectivityManager.PacketKeepalive.BinderC12681.lambda$onError$4(ConnectivityManager.PacketKeepalive.BinderC12681.this, r2, r3);
                            }
                        });
                    }
                });
                PacketKeepalive.this.mExecutor.shutdown();
            }

            public static /* synthetic */ void lambda$onError$4(BinderC12681 binderC12681, PacketKeepaliveCallback callback, int error) {
                PacketKeepalive.this.mSlot = null;
                callback.onError(error);
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onDataReceived() {
            }
        }
    }

    @UnsupportedAppUsage
    public PacketKeepalive startNattKeepalive(Network network, int intervalSeconds, PacketKeepaliveCallback callback, InetAddress srcAddr, int srcPort, InetAddress dstAddr) {
        PacketKeepalive k = new PacketKeepalive(network, callback);
        try {
            this.mService.startNattKeepalive(network, intervalSeconds, k.mCallback, srcAddr.getHostAddress(), srcPort, dstAddr.getHostAddress());
            return k;
        } catch (RemoteException e) {
            Log.m69e(TAG, "Error starting packet keepalive: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public SocketKeepalive createSocketKeepalive(Network network, IpSecManager.UdpEncapsulationSocket socket, InetAddress source, InetAddress destination, Executor executor, SocketKeepalive.Callback callback) {
        ParcelFileDescriptor dup;
        try {
            ParcelFileDescriptor dup2 = ParcelFileDescriptor.dup(socket.getFileDescriptor());
            dup = dup2;
        } catch (IOException e) {
            dup = new ParcelFileDescriptor(new FileDescriptor());
        }
        return new NattSocketKeepalive(this.mService, network, dup, socket.getResourceId(), source, destination, executor, callback);
    }

    @SystemApi
    public SocketKeepalive createNattKeepalive(Network network, ParcelFileDescriptor pfd, InetAddress source, InetAddress destination, Executor executor, SocketKeepalive.Callback callback) {
        ParcelFileDescriptor dup;
        try {
            ParcelFileDescriptor dup2 = pfd.dup();
            dup = dup2;
        } catch (IOException e) {
            dup = new ParcelFileDescriptor(new FileDescriptor());
        }
        return new NattSocketKeepalive(this.mService, network, dup, -1, source, destination, executor, callback);
    }

    @SystemApi
    public SocketKeepalive createSocketKeepalive(Network network, Socket socket, Executor executor, SocketKeepalive.Callback callback) {
        ParcelFileDescriptor dup;
        try {
            ParcelFileDescriptor dup2 = ParcelFileDescriptor.fromSocket(socket);
            dup = dup2;
        } catch (UncheckedIOException e) {
            dup = new ParcelFileDescriptor(new FileDescriptor());
        }
        return new TcpSocketKeepalive(this.mService, network, dup, executor, callback);
    }

    @Deprecated
    public boolean requestRouteToHost(int networkType, int hostAddress) {
        return requestRouteToHostAddress(networkType, NetworkUtils.intToInetAddress(hostAddress));
    }

    @UnsupportedAppUsage
    @Deprecated
    public boolean requestRouteToHostAddress(int networkType, InetAddress hostAddress) {
        checkLegacyRoutingApiAccess();
        try {
            return this.mService.requestRouteToHostAddress(networkType, hostAddress.getAddress());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean getBackgroundDataSetting() {
        return true;
    }

    @UnsupportedAppUsage
    @Deprecated
    public void setBackgroundDataSetting(boolean allowBackgroundData) {
    }

    @UnsupportedAppUsage
    @Deprecated
    public NetworkQuotaInfo getActiveNetworkQuotaInfo() {
        try {
            return this.mService.getActiveNetworkQuotaInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    @Deprecated
    public boolean getMobileDataEnabled() {
        IBinder b = ServiceManager.getService("phone");
        if (b != null) {
            try {
                ITelephony it = ITelephony.Stub.asInterface(b);
                int subId = SubscriptionManager.getDefaultDataSubscriptionId();
                Log.m72d(TAG, "getMobileDataEnabled()+ subId=" + subId);
                boolean retVal = it.isUserDataEnabled(subId);
                Log.m72d(TAG, "getMobileDataEnabled()- subId=" + subId + " retVal=" + retVal);
                return retVal;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Log.m72d(TAG, "getMobileDataEnabled()- remote exception retVal=false");
        return false;
    }

    private INetworkManagementService getNetworkManagementService() {
        synchronized (this) {
            if (this.mNMService != null) {
                return this.mNMService;
            }
            IBinder b = ServiceManager.getService(Context.NETWORKMANAGEMENT_SERVICE);
            this.mNMService = INetworkManagementService.Stub.asInterface(b);
            return this.mNMService;
        }
    }

    public void addDefaultNetworkActiveListener(final OnNetworkActiveListener l) {
        INetworkActivityListener rl = new INetworkActivityListener.Stub() { // from class: android.net.ConnectivityManager.1
            @Override // android.p007os.INetworkActivityListener
            public void onNetworkActive() throws RemoteException {
                l.onNetworkActive();
            }
        };
        try {
            getNetworkManagementService().registerNetworkActivityListener(rl);
            this.mNetworkActivityListeners.put(l, rl);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeDefaultNetworkActiveListener(OnNetworkActiveListener l) {
        INetworkActivityListener rl = this.mNetworkActivityListeners.get(l);
        Preconditions.checkArgument(rl != null, "Listener was not registered.");
        try {
            getNetworkManagementService().unregisterNetworkActivityListener(rl);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDefaultNetworkActive() {
        try {
            return getNetworkManagementService().isNetworkActive();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ConnectivityManager(Context context, IConnectivityManager service) {
        this.mContext = (Context) Preconditions.checkNotNull(context, "missing context");
        this.mService = (IConnectivityManager) Preconditions.checkNotNull(service, "missing IConnectivityManager");
        sInstance = this;
    }

    @UnsupportedAppUsage
    public static ConnectivityManager from(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    public NetworkRequest getDefaultRequest() {
        try {
            return this.mService.getDefaultRequest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final void enforceChangePermission(Context context) {
        int uid = Binder.getCallingUid();
        Settings.checkAndNoteChangeNetworkStateOperation(context, uid, Settings.getPackageNameForUid(context, uid), true);
    }

    public static final void enforceTetherChangePermission(Context context, String callingPkg) {
        Preconditions.checkNotNull(context, "Context cannot be null");
        Preconditions.checkNotNull(callingPkg, "callingPkg cannot be null");
        if (context.getResources().getStringArray(C3132R.array.config_mobile_hotspot_provision_app).length == 2) {
            context.enforceCallingOrSelfPermission(Manifest.C0000permission.TETHER_PRIVILEGED, "ConnectivityService");
            return;
        }
        int uid = Binder.getCallingUid();
        Settings.checkAndNoteWriteSettingsOperation(context, uid, callingPkg, true);
    }

    @Deprecated
    static ConnectivityManager getInstanceOrNull() {
        return sInstance;
    }

    @UnsupportedAppUsage
    @Deprecated
    private static ConnectivityManager getInstance() {
        if (getInstanceOrNull() == null) {
            throw new IllegalStateException("No ConnectivityManager yet constructed");
        }
        return getInstanceOrNull();
    }

    @UnsupportedAppUsage
    public String[] getTetherableIfaces() {
        try {
            return this.mService.getTetherableIfaces();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetheredIfaces() {
        try {
            return this.mService.getTetheredIfaces();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetheringErroredIfaces() {
        try {
            return this.mService.getTetheringErroredIfaces();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String[] getTetheredDhcpRanges() {
        try {
            return this.mService.getTetheredDhcpRanges();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int tether(String iface) {
        try {
            String pkgName = this.mContext.getOpPackageName();
            Log.m68i(TAG, "tether caller:" + pkgName);
            return this.mService.tether(iface, pkgName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int untether(String iface) {
        try {
            String pkgName = this.mContext.getOpPackageName();
            Log.m68i(TAG, "untether caller:" + pkgName);
            return this.mService.untether(iface, pkgName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isTetheringSupported() {
        String pkgName = this.mContext.getOpPackageName();
        try {
            return this.mService.isTetheringSupported(pkgName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            return false;
        }
    }

    @SystemApi
    /* loaded from: classes3.dex */
    public static abstract class OnStartTetheringCallback {
        public void onTetheringStarted() {
        }

        public void onTetheringFailed() {
        }
    }

    @SystemApi
    public void startTethering(int type, boolean showProvisioningUi, OnStartTetheringCallback callback) {
        startTethering(type, showProvisioningUi, callback, null);
    }

    @SystemApi
    public void startTethering(int type, boolean showProvisioningUi, final OnStartTetheringCallback callback, Handler handler) {
        Preconditions.checkNotNull(callback, "OnStartTetheringCallback cannot be null.");
        ResultReceiver wrappedCallback = new ResultReceiver(handler) { // from class: android.net.ConnectivityManager.2
            @Override // android.p007os.ResultReceiver
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == 0) {
                    callback.onTetheringStarted();
                } else {
                    callback.onTetheringFailed();
                }
            }
        };
        try {
            String pkgName = this.mContext.getOpPackageName();
            Log.m68i(TAG, "startTethering caller:" + pkgName);
            this.mService.startTethering(type, wrappedCallback, showProvisioningUi, pkgName);
        } catch (RemoteException e) {
            Log.m69e(TAG, "Exception trying to start tethering.", e);
            wrappedCallback.send(2, null);
        }
    }

    @SystemApi
    public void stopTethering(int type) {
        try {
            String pkgName = this.mContext.getOpPackageName();
            Log.m68i(TAG, "stopTethering caller:" + pkgName);
            this.mService.stopTethering(type, pkgName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    /* loaded from: classes3.dex */
    public static abstract class OnTetheringEventCallback {
        public void onUpstreamChanged(Network network) {
        }
    }

    @SystemApi
    public void registerTetheringEventCallback(Executor executor, OnTetheringEventCallback callback) {
        Preconditions.checkNotNull(callback, "OnTetheringEventCallback cannot be null.");
        synchronized (this.mTetheringEventCallbacks) {
            Preconditions.checkArgument(!this.mTetheringEventCallbacks.containsKey(callback), "callback was already registered.");
            ITetheringEventCallback remoteCallback = new BinderC12653(executor, callback);
            try {
                String pkgName = this.mContext.getOpPackageName();
                Log.m68i(TAG, "registerTetheringUpstreamCallback:" + pkgName);
                this.mService.registerTetheringEventCallback(remoteCallback, pkgName);
                this.mTetheringEventCallbacks.put(callback, remoteCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.net.ConnectivityManager$3 */
    /* loaded from: classes3.dex */
    class BinderC12653 extends ITetheringEventCallback.Stub {
        final /* synthetic */ OnTetheringEventCallback val$callback;
        final /* synthetic */ Executor val$executor;

        BinderC12653(Executor executor, OnTetheringEventCallback onTetheringEventCallback) {
            this.val$executor = executor;
            this.val$callback = onTetheringEventCallback;
        }

        @Override // android.net.ITetheringEventCallback
        public void onUpstreamChanged(final Network network) throws RemoteException {
            final Executor executor = this.val$executor;
            final OnTetheringEventCallback onTetheringEventCallback = this.val$callback;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.net.-$$Lambda$ConnectivityManager$3$BfAvTRJTF0an2PdeqkENEBULYBU
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.net.-$$Lambda$ConnectivityManager$3$Hh_etCA-vVs2IV58umWLOd1O4yk
                        @Override // java.lang.Runnable
                        public final void run() {
                            ConnectivityManager.OnTetheringEventCallback.this.onUpstreamChanged(r2);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterTetheringEventCallback(OnTetheringEventCallback callback) {
        synchronized (this.mTetheringEventCallbacks) {
            ITetheringEventCallback remoteCallback = this.mTetheringEventCallbacks.remove(callback);
            Preconditions.checkNotNull(remoteCallback, "callback was not registered.");
            try {
                String pkgName = this.mContext.getOpPackageName();
                Log.m68i(TAG, "unregisterTetheringEventCallback:" + pkgName);
                this.mService.unregisterTetheringEventCallback(remoteCallback, pkgName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableUsbRegexs() {
        try {
            return this.mService.getTetherableUsbRegexs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableWifiRegexs() {
        try {
            return this.mService.getTetherableWifiRegexs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableBluetoothRegexs() {
        try {
            return this.mService.getTetherableBluetoothRegexs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int setUsbTethering(boolean enable) {
        try {
            String pkgName = this.mContext.getOpPackageName();
            Log.m68i(TAG, "setUsbTethering caller:" + pkgName);
            return this.mService.setUsbTethering(enable, pkgName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getLastTetherError(String iface) {
        try {
            return this.mService.getLastTetherError(iface);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.net.ConnectivityManager$4 */
    /* loaded from: classes3.dex */
    class ResultReceiverC12664 extends ResultReceiver {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnTetheringEntitlementResultListener val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ResultReceiverC12664(Handler x0, Executor executor, OnTetheringEntitlementResultListener onTetheringEntitlementResultListener) {
            super(x0);
            this.val$executor = executor;
            this.val$listener = onTetheringEntitlementResultListener;
        }

        @Override // android.p007os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            final Executor executor = this.val$executor;
            final OnTetheringEntitlementResultListener onTetheringEntitlementResultListener = this.val$listener;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.net.-$$Lambda$ConnectivityManager$4$Jk-u9vR1DwqMOUorHyaTIOdhOAs
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.net.-$$Lambda$ConnectivityManager$4$GbcJVaUJX-pIrYQi94EYHYBwTJI
                        @Override // java.lang.Runnable
                        public final void run() {
                            ConnectivityManager.OnTetheringEntitlementResultListener.this.onTetheringEntitlementResult(r2);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void getLatestTetheringEntitlementResult(int type, boolean showEntitlementUi, Executor executor, OnTetheringEntitlementResultListener listener) {
        Preconditions.checkNotNull(listener, "TetheringEntitlementResultListener cannot be null.");
        ResultReceiver wrappedListener = new ResultReceiverC12664(null, executor, listener);
        try {
            String pkgName = this.mContext.getOpPackageName();
            Log.m68i(TAG, "getLatestTetheringEntitlementResult:" + pkgName);
            this.mService.getLatestTetheringEntitlementResult(type, wrappedListener, showEntitlementUi, pkgName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportInetCondition(int networkType, int percentage) {
        printStackTrace();
        try {
            this.mService.reportInetCondition(networkType, percentage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void reportBadNetwork(Network network) {
        printStackTrace();
        try {
            this.mService.reportNetworkConnectivity(network, true);
            this.mService.reportNetworkConnectivity(network, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportNetworkConnectivity(Network network, boolean hasConnectivity) {
        printStackTrace();
        try {
            this.mService.reportNetworkConnectivity(network, hasConnectivity);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGlobalProxy(ProxyInfo p) {
        try {
            this.mService.setGlobalProxy(p);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ProxyInfo getGlobalProxy() {
        try {
            return this.mService.getGlobalProxy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ProxyInfo getProxyForNetwork(Network network) {
        try {
            return this.mService.getProxyForNetwork(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ProxyInfo getDefaultProxy() {
        return getProxyForNetwork(getBoundNetworkForProcess());
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 130143562)
    @Deprecated
    public boolean isNetworkSupported(int networkType) {
        try {
            return this.mService.isNetworkSupported(networkType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActiveNetworkMetered() {
        try {
            return this.mService.isActiveNetworkMetered();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateLockdownVpn() {
        try {
            return this.mService.updateLockdownVpn();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkMobileProvisioning(int suggestedTimeOutMs) {
        try {
            int timeOutMs = this.mService.checkMobileProvisioning(suggestedTimeOutMs);
            return timeOutMs;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getMobileProvisioningUrl() {
        try {
            return this.mService.getMobileProvisioningUrl();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setProvisioningNotificationVisible(boolean visible, int networkType, String action) {
        try {
            this.mService.setProvisioningNotificationVisible(visible, networkType, action);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAirplaneMode(boolean enable) {
        try {
            this.mService.setAirplaneMode(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int registerNetworkFactory(Messenger messenger, String name) {
        try {
            return this.mService.registerNetworkFactory(messenger, name);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void unregisterNetworkFactory(Messenger messenger) {
        try {
            this.mService.unregisterNetworkFactory(messenger);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int registerNetworkAgent(Messenger messenger, NetworkInfo ni, LinkProperties lp, NetworkCapabilities nc, int score, NetworkMisc misc) {
        return registerNetworkAgent(messenger, ni, lp, nc, score, misc, -1);
    }

    public int registerNetworkAgent(Messenger messenger, NetworkInfo ni, LinkProperties lp, NetworkCapabilities nc, int score, NetworkMisc misc, int factorySerialNumber) {
        try {
            return this.mService.registerNetworkAgent(messenger, ni, lp, nc, score, misc, factorySerialNumber);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* loaded from: classes3.dex */
    public static class NetworkCallback {
        private NetworkRequest networkRequest;

        public void onPreCheck(Network network) {
        }

        public void onAvailable(Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, boolean blocked) {
            onAvailable(network);
            if (!networkCapabilities.hasCapability(21)) {
                onNetworkSuspended(network);
            }
            onCapabilitiesChanged(network, networkCapabilities);
            onLinkPropertiesChanged(network, linkProperties);
            onBlockedStatusChanged(network, blocked);
        }

        public void onAvailable(Network network) {
        }

        public void onLosing(Network network, int maxMsToLive) {
        }

        public void onLost(Network network) {
        }

        public void onUnavailable() {
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        }

        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        }

        public void onNetworkSuspended(Network network) {
        }

        public void onNetworkResumed(Network network) {
        }

        public void onBlockedStatusChanged(Network network, boolean blocked) {
        }
    }

    private static RuntimeException convertServiceException(ServiceSpecificException e) {
        if (e.errorCode == 1) {
            return new TooManyRequestsException();
        }
        Log.m64w(TAG, "Unknown service error code " + e.errorCode);
        return new RuntimeException(e);
    }

    public static String getCallbackName(int whichCallback) {
        switch (whichCallback) {
            case CALLBACK_PRECHECK /* 524289 */:
                return "CALLBACK_PRECHECK";
            case 524290:
                return "CALLBACK_AVAILABLE";
            case 524291:
                return "CALLBACK_LOSING";
            case 524292:
                return "CALLBACK_LOST";
            case CALLBACK_UNAVAIL /* 524293 */:
                return "CALLBACK_UNAVAIL";
            case CALLBACK_CAP_CHANGED /* 524294 */:
                return "CALLBACK_CAP_CHANGED";
            case CALLBACK_IP_CHANGED /* 524295 */:
                return "CALLBACK_IP_CHANGED";
            case EXPIRE_LEGACY_REQUEST /* 524296 */:
                return "EXPIRE_LEGACY_REQUEST";
            case CALLBACK_SUSPENDED /* 524297 */:
                return "CALLBACK_SUSPENDED";
            case CALLBACK_RESUMED /* 524298 */:
                return "CALLBACK_RESUMED";
            case CALLBACK_BLK_CHANGED /* 524299 */:
                return "CALLBACK_BLK_CHANGED";
            default:
                return Integer.toString(whichCallback);
        }
    }

    /* loaded from: classes3.dex */
    private class CallbackHandler extends Handler {
        private static final boolean DBG = false;
        private static final String TAG = "ConnectivityManager.CallbackHandler";

        CallbackHandler(Looper looper) {
            super(looper);
        }

        CallbackHandler(ConnectivityManager connectivityManager, Handler handler) {
            this(((Handler) Preconditions.checkNotNull(handler, "Handler cannot be null.")).getLooper());
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message message) {
            if (message.what == ConnectivityManager.EXPIRE_LEGACY_REQUEST) {
                ConnectivityManager.this.expireRequest((NetworkCapabilities) message.obj, message.arg1);
                return;
            }
            NetworkRequest request = (NetworkRequest) getObject(message, NetworkRequest.class);
            Network network = (Network) getObject(message, Network.class);
            synchronized (ConnectivityManager.sCallbacks) {
                NetworkCallback callback = (NetworkCallback) ConnectivityManager.sCallbacks.get(request);
                if (callback == null) {
                    Log.m64w(TAG, "callback not found for " + ConnectivityManager.getCallbackName(message.what) + " message");
                    return;
                }
                if (message.what == 524293) {
                    ConnectivityManager.sCallbacks.remove(request);
                    callback.networkRequest = ConnectivityManager.ALREADY_UNREGISTERED;
                }
                switch (message.what) {
                    case ConnectivityManager.CALLBACK_PRECHECK /* 524289 */:
                        callback.onPreCheck(network);
                        return;
                    case 524290:
                        NetworkCapabilities cap = (NetworkCapabilities) getObject(message, NetworkCapabilities.class);
                        LinkProperties lp = (LinkProperties) getObject(message, LinkProperties.class);
                        callback.onAvailable(network, cap, lp, message.arg1 != 0);
                        return;
                    case 524291:
                        callback.onLosing(network, message.arg1);
                        return;
                    case 524292:
                        callback.onLost(network);
                        return;
                    case ConnectivityManager.CALLBACK_UNAVAIL /* 524293 */:
                        callback.onUnavailable();
                        return;
                    case ConnectivityManager.CALLBACK_CAP_CHANGED /* 524294 */:
                        NetworkCapabilities cap2 = (NetworkCapabilities) getObject(message, NetworkCapabilities.class);
                        callback.onCapabilitiesChanged(network, cap2);
                        return;
                    case ConnectivityManager.CALLBACK_IP_CHANGED /* 524295 */:
                        LinkProperties lp2 = (LinkProperties) getObject(message, LinkProperties.class);
                        callback.onLinkPropertiesChanged(network, lp2);
                        return;
                    case ConnectivityManager.EXPIRE_LEGACY_REQUEST /* 524296 */:
                    default:
                        return;
                    case ConnectivityManager.CALLBACK_SUSPENDED /* 524297 */:
                        callback.onNetworkSuspended(network);
                        return;
                    case ConnectivityManager.CALLBACK_RESUMED /* 524298 */:
                        callback.onNetworkResumed(network);
                        return;
                    case ConnectivityManager.CALLBACK_BLK_CHANGED /* 524299 */:
                        boolean blocked = message.arg1 != 0;
                        callback.onBlockedStatusChanged(network, blocked);
                        return;
                }
            }
        }

        private <T> T getObject(Message msg, Class<T> c) {
            return (T) msg.getData().getParcelable(c.getSimpleName());
        }
    }

    private CallbackHandler getDefaultHandler() {
        CallbackHandler callbackHandler;
        synchronized (sCallbacks) {
            if (sCallbackHandler == null) {
                sCallbackHandler = new CallbackHandler(ConnectivityThread.getInstanceLooper());
            }
            callbackHandler = sCallbackHandler;
        }
        return callbackHandler;
    }

    private NetworkRequest sendRequestForNetwork(NetworkCapabilities need, NetworkCallback callback, int timeoutMs, int action, int legacyType, CallbackHandler handler) {
        NetworkRequest request;
        printStackTrace();
        checkCallbackNotNull(callback);
        Preconditions.checkArgument(action == 2 || need != null, "null NetworkCapabilities");
        try {
            try {
                synchronized (sCallbacks) {
                    try {
                        if (callback.networkRequest != null && callback.networkRequest != ALREADY_UNREGISTERED) {
                            Log.m70e(TAG, "NetworkCallback was already registered");
                        }
                        Messenger messenger = new Messenger(handler);
                        Binder binder = new Binder();
                        if (action == 1) {
                            request = this.mService.listenForNetwork(need, messenger, binder);
                        } else {
                            request = this.mService.requestNetwork(need, messenger, timeoutMs, binder, legacyType);
                        }
                        if (request != null) {
                            sCallbacks.put(request, callback);
                        }
                        callback.networkRequest = request;
                        return request;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            throw th;
                        } catch (RemoteException e) {
                            e = e;
                            throw e.rethrowFromSystemServer();
                        } catch (ServiceSpecificException e2) {
                            e = e2;
                            throw convertServiceException(e);
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (RemoteException e3) {
            e = e3;
        } catch (ServiceSpecificException e4) {
            e = e4;
        }
    }

    public void requestNetwork(NetworkRequest request, NetworkCallback networkCallback, int timeoutMs, int legacyType, Handler handler) {
        CallbackHandler cbHandler = new CallbackHandler(this, handler);
        NetworkCapabilities nc = request.networkCapabilities;
        sendRequestForNetwork(nc, networkCallback, timeoutMs, 2, legacyType, cbHandler);
    }

    public void requestNetwork(NetworkRequest request, NetworkCallback networkCallback) {
        requestNetwork(request, networkCallback, getDefaultHandler());
    }

    public void requestNetwork(NetworkRequest request, NetworkCallback networkCallback, Handler handler) {
        int legacyType = inferLegacyTypeForNetworkCapabilities(request.networkCapabilities);
        CallbackHandler cbHandler = new CallbackHandler(this, handler);
        requestNetwork(request, networkCallback, 0, legacyType, cbHandler);
    }

    public void requestNetwork(NetworkRequest request, NetworkCallback networkCallback, int timeoutMs) {
        checkTimeout(timeoutMs);
        int legacyType = inferLegacyTypeForNetworkCapabilities(request.networkCapabilities);
        requestNetwork(request, networkCallback, timeoutMs, legacyType, getDefaultHandler());
    }

    public void requestNetwork(NetworkRequest request, NetworkCallback networkCallback, Handler handler, int timeoutMs) {
        checkTimeout(timeoutMs);
        int legacyType = inferLegacyTypeForNetworkCapabilities(request.networkCapabilities);
        CallbackHandler cbHandler = new CallbackHandler(this, handler);
        requestNetwork(request, networkCallback, timeoutMs, legacyType, cbHandler);
    }

    public void requestNetwork(NetworkRequest request, PendingIntent operation) {
        printStackTrace();
        checkPendingIntentNotNull(operation);
        try {
            this.mService.pendingRequestForNetwork(request.networkCapabilities, operation);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw convertServiceException(e2);
        }
    }

    public void releaseNetworkRequest(PendingIntent operation) {
        printStackTrace();
        checkPendingIntentNotNull(operation);
        try {
            this.mService.releasePendingNetworkRequest(operation);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static void checkPendingIntentNotNull(PendingIntent intent) {
        Preconditions.checkNotNull(intent, "PendingIntent cannot be null.");
    }

    private static void checkCallbackNotNull(NetworkCallback callback) {
        Preconditions.checkNotNull(callback, "null NetworkCallback");
    }

    private static void checkTimeout(int timeoutMs) {
        Preconditions.checkArgumentPositive(timeoutMs, "timeoutMs must be strictly positive.");
    }

    public void registerNetworkCallback(NetworkRequest request, NetworkCallback networkCallback) {
        registerNetworkCallback(request, networkCallback, getDefaultHandler());
    }

    public void registerNetworkCallback(NetworkRequest request, NetworkCallback networkCallback, Handler handler) {
        CallbackHandler cbHandler = new CallbackHandler(this, handler);
        NetworkCapabilities nc = request.networkCapabilities;
        sendRequestForNetwork(nc, networkCallback, 0, 1, -1, cbHandler);
    }

    public void registerNetworkCallback(NetworkRequest request, PendingIntent operation) {
        printStackTrace();
        checkPendingIntentNotNull(operation);
        try {
            this.mService.pendingListenForNetwork(request.networkCapabilities, operation);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (ServiceSpecificException e2) {
            throw convertServiceException(e2);
        }
    }

    public void registerDefaultNetworkCallback(NetworkCallback networkCallback) {
        registerDefaultNetworkCallback(networkCallback, getDefaultHandler());
    }

    public void registerDefaultNetworkCallback(NetworkCallback networkCallback, Handler handler) {
        CallbackHandler cbHandler = new CallbackHandler(this, handler);
        sendRequestForNetwork(null, networkCallback, 0, 2, -1, cbHandler);
    }

    public boolean requestBandwidthUpdate(Network network) {
        try {
            return this.mService.requestBandwidthUpdate(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterNetworkCallback(NetworkCallback networkCallback) {
        printStackTrace();
        checkCallbackNotNull(networkCallback);
        List<NetworkRequest> reqs = new ArrayList<>();
        synchronized (sCallbacks) {
            Preconditions.checkArgument(networkCallback.networkRequest != null, "NetworkCallback was not registered");
            if (networkCallback.networkRequest == ALREADY_UNREGISTERED) {
                Log.m72d(TAG, "NetworkCallback was already unregistered");
                return;
            }
            for (Map.Entry<NetworkRequest, NetworkCallback> e : sCallbacks.entrySet()) {
                if (e.getValue() == networkCallback) {
                    reqs.add(e.getKey());
                }
            }
            for (NetworkRequest r : reqs) {
                try {
                    this.mService.releaseNetworkRequest(r);
                    sCallbacks.remove(r);
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            }
            networkCallback.networkRequest = ALREADY_UNREGISTERED;
        }
    }

    public void unregisterNetworkCallback(PendingIntent operation) {
        checkPendingIntentNotNull(operation);
        releaseNetworkRequest(operation);
    }

    public void setAcceptUnvalidated(Network network, boolean accept, boolean always) {
        try {
            this.mService.setAcceptUnvalidated(network, accept, always);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAcceptPartialConnectivity(Network network, boolean accept, boolean always) {
        try {
            this.mService.setAcceptPartialConnectivity(network, accept, always);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAvoidUnvalidated(Network network) {
        try {
            this.mService.setAvoidUnvalidated(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startCaptivePortalApp(Network network) {
        try {
            this.mService.startCaptivePortalApp(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startCaptivePortalApp(Network network, Bundle appExtras) {
        try {
            this.mService.startCaptivePortalAppInternal(network, appExtras);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean shouldAvoidBadWifi() {
        try {
            return this.mService.shouldAvoidBadWifi();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMultipathPreference(Network network) {
        try {
            return this.mService.getMultipathPreference(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindProcessToNetwork(Network network) {
        return setProcessDefaultNetwork(network);
    }

    @Deprecated
    public static boolean setProcessDefaultNetwork(Network network) {
        int netId = network == null ? 0 : network.netId;
        boolean isSameNetId = netId == NetworkUtils.getBoundNetworkForProcess();
        if (netId != 0) {
            netId = network.getNetIdForResolv();
        }
        if (!NetworkUtils.bindProcessToNetwork(netId)) {
            return false;
        }
        if (!isSameNetId) {
            try {
                Proxy.setHttpProxySystemProperty(getInstance().getDefaultProxy());
            } catch (SecurityException e) {
                Log.m69e(TAG, "Can't set proxy properties", e);
            }
            InetAddress.clearDnsCache();
            NetworkEventDispatcher.getInstance().onNetworkConfigurationChanged();
        }
        return true;
    }

    public Network getBoundNetworkForProcess() {
        return getProcessDefaultNetwork();
    }

    @Deprecated
    public static Network getProcessDefaultNetwork() {
        int netId = NetworkUtils.getBoundNetworkForProcess();
        if (netId == 0) {
            return null;
        }
        return new Network(netId);
    }

    private void unsupportedStartingFrom(int version) {
        if (Process.myUid() != 1000 && this.mContext.getApplicationInfo().targetSdkVersion >= version) {
            throw new UnsupportedOperationException("This method is not supported in target SDK version " + version + " and above");
        }
    }

    private void checkLegacyRoutingApiAccess() {
        unsupportedStartingFrom(23);
    }

    @UnsupportedAppUsage
    @Deprecated
    public static boolean setProcessDefaultNetworkForHostResolution(Network network) {
        return NetworkUtils.bindProcessToNetworkForHostResolution(network == null ? 0 : network.getNetIdForResolv());
    }

    private INetworkPolicyManager getNetworkPolicyManager() {
        synchronized (this) {
            if (this.mNPManager != null) {
                return this.mNPManager;
            }
            this.mNPManager = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService(Context.NETWORK_POLICY_SERVICE));
            return this.mNPManager;
        }
    }

    public int getRestrictBackgroundStatus() {
        try {
            return getNetworkPolicyManager().getRestrictBackgroundByCaller();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public byte[] getNetworkWatchlistConfigHash() {
        try {
            return this.mService.getNetworkWatchlistConfigHash();
        } catch (RemoteException e) {
            Log.m70e(TAG, "Unable to get watchlist config hash");
            throw e.rethrowFromSystemServer();
        }
    }

    public int getConnectionOwnerUid(int protocol, InetSocketAddress local, InetSocketAddress remote) {
        ConnectionInfo connectionInfo = new ConnectionInfo(protocol, local, remote);
        try {
            return this.mService.getConnectionOwnerUid(connectionInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void printStackTrace() {
        String stackTrace;
        if (DEBUG) {
            StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
            StringBuffer sb = new StringBuffer();
            for (int i = 3; i < callStack.length && (stackTrace = callStack[i].toString()) != null && !stackTrace.contains("android.os"); i++) {
                sb.append(" [");
                sb.append(stackTrace);
                sb.append("]");
            }
            Log.m72d(TAG, "StackLog:" + sb.toString());
        }
    }
}
