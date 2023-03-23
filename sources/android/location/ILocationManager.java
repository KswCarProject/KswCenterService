package android.location;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.location.ProviderProperties;
import java.util.List;

public interface ILocationManager extends IInterface {
    boolean addGnssBatchingCallback(IBatchedLocationCallback iBatchedLocationCallback, String str) throws RemoteException;

    boolean addGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener, String str) throws RemoteException;

    boolean addGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener, String str) throws RemoteException;

    void addTestProvider(String str, ProviderProperties providerProperties, String str2) throws RemoteException;

    void flushGnssBatch(String str) throws RemoteException;

    boolean geocoderIsPresent() throws RemoteException;

    @UnsupportedAppUsage
    List<String> getAllProviders() throws RemoteException;

    String[] getBackgroundThrottlingWhitelist() throws RemoteException;

    String getBestProvider(Criteria criteria, boolean z) throws RemoteException;

    String getExtraLocationControllerPackage() throws RemoteException;

    String getFromLocation(double d, double d2, int i, GeocoderParams geocoderParams, List<Address> list) throws RemoteException;

    String getFromLocationName(String str, double d, double d2, double d3, double d4, int i, GeocoderParams geocoderParams, List<Address> list) throws RemoteException;

    int getGnssBatchSize(String str) throws RemoteException;

    long getGnssCapabilities(String str) throws RemoteException;

    String getGnssHardwareModelName() throws RemoteException;

    LocationTime getGnssTimeMillis() throws RemoteException;

    int getGnssYearOfHardware() throws RemoteException;

    String[] getIgnoreSettingsWhitelist() throws RemoteException;

    Location getLastLocation(LocationRequest locationRequest, String str) throws RemoteException;

    ProviderProperties getProviderProperties(String str) throws RemoteException;

    List<String> getProviders(Criteria criteria, boolean z) throws RemoteException;

    List<LocationRequest> getTestProviderCurrentRequests(String str, String str2) throws RemoteException;

    void injectGnssMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections, String str) throws RemoteException;

    boolean injectLocation(Location location) throws RemoteException;

    boolean isExtraLocationControllerPackageEnabled() throws RemoteException;

    boolean isLocationEnabledForUser(int i) throws RemoteException;

    boolean isProviderEnabledForUser(String str, int i) throws RemoteException;

    boolean isProviderPackage(String str) throws RemoteException;

    void locationCallbackFinished(ILocationListener iLocationListener) throws RemoteException;

    boolean registerGnssStatusCallback(IGnssStatusListener iGnssStatusListener, String str) throws RemoteException;

    void removeGeofence(Geofence geofence, PendingIntent pendingIntent, String str) throws RemoteException;

    void removeGnssBatchingCallback() throws RemoteException;

    void removeGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener) throws RemoteException;

    void removeGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener) throws RemoteException;

    void removeTestProvider(String str, String str2) throws RemoteException;

    void removeUpdates(ILocationListener iLocationListener, PendingIntent pendingIntent, String str) throws RemoteException;

    void requestGeofence(LocationRequest locationRequest, Geofence geofence, PendingIntent pendingIntent, String str) throws RemoteException;

    void requestLocationUpdates(LocationRequest locationRequest, ILocationListener iLocationListener, PendingIntent pendingIntent, String str) throws RemoteException;

    boolean sendExtraCommand(String str, String str2, Bundle bundle) throws RemoteException;

    boolean sendNiResponse(int i, int i2) throws RemoteException;

    void setExtraLocationControllerPackage(String str) throws RemoteException;

    void setExtraLocationControllerPackageEnabled(boolean z) throws RemoteException;

    void setTestProviderEnabled(String str, boolean z, String str2) throws RemoteException;

    void setTestProviderLocation(String str, Location location, String str2) throws RemoteException;

    void setTestProviderStatus(String str, int i, Bundle bundle, long j, String str2) throws RemoteException;

    boolean startGnssBatch(long j, boolean z, String str) throws RemoteException;

    boolean stopGnssBatch() throws RemoteException;

    void unregisterGnssStatusCallback(IGnssStatusListener iGnssStatusListener) throws RemoteException;

    public static class Default implements ILocationManager {
        public void requestLocationUpdates(LocationRequest request, ILocationListener listener, PendingIntent intent, String packageName) throws RemoteException {
        }

        public void removeUpdates(ILocationListener listener, PendingIntent intent, String packageName) throws RemoteException {
        }

        public void requestGeofence(LocationRequest request, Geofence geofence, PendingIntent intent, String packageName) throws RemoteException {
        }

        public void removeGeofence(Geofence fence, PendingIntent intent, String packageName) throws RemoteException {
        }

        public Location getLastLocation(LocationRequest request, String packageName) throws RemoteException {
            return null;
        }

        public boolean registerGnssStatusCallback(IGnssStatusListener callback, String packageName) throws RemoteException {
            return false;
        }

        public void unregisterGnssStatusCallback(IGnssStatusListener callback) throws RemoteException {
        }

        public boolean geocoderIsPresent() throws RemoteException {
            return false;
        }

        public String getFromLocation(double latitude, double longitude, int maxResults, GeocoderParams params, List<Address> list) throws RemoteException {
            return null;
        }

        public String getFromLocationName(String locationName, double lowerLeftLatitude, double lowerLeftLongitude, double upperRightLatitude, double upperRightLongitude, int maxResults, GeocoderParams params, List<Address> list) throws RemoteException {
            return null;
        }

        public boolean sendNiResponse(int notifId, int userResponse) throws RemoteException {
            return false;
        }

        public boolean addGnssMeasurementsListener(IGnssMeasurementsListener listener, String packageName) throws RemoteException {
            return false;
        }

        public void injectGnssMeasurementCorrections(GnssMeasurementCorrections corrections, String packageName) throws RemoteException {
        }

        public long getGnssCapabilities(String packageName) throws RemoteException {
            return 0;
        }

        public void removeGnssMeasurementsListener(IGnssMeasurementsListener listener) throws RemoteException {
        }

        public boolean addGnssNavigationMessageListener(IGnssNavigationMessageListener listener, String packageName) throws RemoteException {
            return false;
        }

        public void removeGnssNavigationMessageListener(IGnssNavigationMessageListener listener) throws RemoteException {
        }

        public int getGnssYearOfHardware() throws RemoteException {
            return 0;
        }

        public String getGnssHardwareModelName() throws RemoteException {
            return null;
        }

        public int getGnssBatchSize(String packageName) throws RemoteException {
            return 0;
        }

        public boolean addGnssBatchingCallback(IBatchedLocationCallback callback, String packageName) throws RemoteException {
            return false;
        }

        public void removeGnssBatchingCallback() throws RemoteException {
        }

        public boolean startGnssBatch(long periodNanos, boolean wakeOnFifoFull, String packageName) throws RemoteException {
            return false;
        }

        public void flushGnssBatch(String packageName) throws RemoteException {
        }

        public boolean stopGnssBatch() throws RemoteException {
            return false;
        }

        public boolean injectLocation(Location location) throws RemoteException {
            return false;
        }

        public List<String> getAllProviders() throws RemoteException {
            return null;
        }

        public List<String> getProviders(Criteria criteria, boolean enabledOnly) throws RemoteException {
            return null;
        }

        public String getBestProvider(Criteria criteria, boolean enabledOnly) throws RemoteException {
            return null;
        }

        public ProviderProperties getProviderProperties(String provider) throws RemoteException {
            return null;
        }

        public boolean isProviderPackage(String packageName) throws RemoteException {
            return false;
        }

        public void setExtraLocationControllerPackage(String packageName) throws RemoteException {
        }

        public String getExtraLocationControllerPackage() throws RemoteException {
            return null;
        }

        public void setExtraLocationControllerPackageEnabled(boolean enabled) throws RemoteException {
        }

        public boolean isExtraLocationControllerPackageEnabled() throws RemoteException {
            return false;
        }

        public boolean isProviderEnabledForUser(String provider, int userId) throws RemoteException {
            return false;
        }

        public boolean isLocationEnabledForUser(int userId) throws RemoteException {
            return false;
        }

        public void addTestProvider(String name, ProviderProperties properties, String opPackageName) throws RemoteException {
        }

        public void removeTestProvider(String provider, String opPackageName) throws RemoteException {
        }

        public void setTestProviderLocation(String provider, Location loc, String opPackageName) throws RemoteException {
        }

        public void setTestProviderEnabled(String provider, boolean enabled, String opPackageName) throws RemoteException {
        }

        public List<LocationRequest> getTestProviderCurrentRequests(String provider, String opPackageName) throws RemoteException {
            return null;
        }

        public LocationTime getGnssTimeMillis() throws RemoteException {
            return null;
        }

        public void setTestProviderStatus(String provider, int status, Bundle extras, long updateTime, String opPackageName) throws RemoteException {
        }

        public boolean sendExtraCommand(String provider, String command, Bundle extras) throws RemoteException {
            return false;
        }

        public void locationCallbackFinished(ILocationListener listener) throws RemoteException {
        }

        public String[] getBackgroundThrottlingWhitelist() throws RemoteException {
            return null;
        }

        public String[] getIgnoreSettingsWhitelist() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILocationManager {
        private static final String DESCRIPTOR = "android.location.ILocationManager";
        static final int TRANSACTION_addGnssBatchingCallback = 21;
        static final int TRANSACTION_addGnssMeasurementsListener = 12;
        static final int TRANSACTION_addGnssNavigationMessageListener = 16;
        static final int TRANSACTION_addTestProvider = 38;
        static final int TRANSACTION_flushGnssBatch = 24;
        static final int TRANSACTION_geocoderIsPresent = 8;
        static final int TRANSACTION_getAllProviders = 27;
        static final int TRANSACTION_getBackgroundThrottlingWhitelist = 47;
        static final int TRANSACTION_getBestProvider = 29;
        static final int TRANSACTION_getExtraLocationControllerPackage = 33;
        static final int TRANSACTION_getFromLocation = 9;
        static final int TRANSACTION_getFromLocationName = 10;
        static final int TRANSACTION_getGnssBatchSize = 20;
        static final int TRANSACTION_getGnssCapabilities = 14;
        static final int TRANSACTION_getGnssHardwareModelName = 19;
        static final int TRANSACTION_getGnssTimeMillis = 43;
        static final int TRANSACTION_getGnssYearOfHardware = 18;
        static final int TRANSACTION_getIgnoreSettingsWhitelist = 48;
        static final int TRANSACTION_getLastLocation = 5;
        static final int TRANSACTION_getProviderProperties = 30;
        static final int TRANSACTION_getProviders = 28;
        static final int TRANSACTION_getTestProviderCurrentRequests = 42;
        static final int TRANSACTION_injectGnssMeasurementCorrections = 13;
        static final int TRANSACTION_injectLocation = 26;
        static final int TRANSACTION_isExtraLocationControllerPackageEnabled = 35;
        static final int TRANSACTION_isLocationEnabledForUser = 37;
        static final int TRANSACTION_isProviderEnabledForUser = 36;
        static final int TRANSACTION_isProviderPackage = 31;
        static final int TRANSACTION_locationCallbackFinished = 46;
        static final int TRANSACTION_registerGnssStatusCallback = 6;
        static final int TRANSACTION_removeGeofence = 4;
        static final int TRANSACTION_removeGnssBatchingCallback = 22;
        static final int TRANSACTION_removeGnssMeasurementsListener = 15;
        static final int TRANSACTION_removeGnssNavigationMessageListener = 17;
        static final int TRANSACTION_removeTestProvider = 39;
        static final int TRANSACTION_removeUpdates = 2;
        static final int TRANSACTION_requestGeofence = 3;
        static final int TRANSACTION_requestLocationUpdates = 1;
        static final int TRANSACTION_sendExtraCommand = 45;
        static final int TRANSACTION_sendNiResponse = 11;
        static final int TRANSACTION_setExtraLocationControllerPackage = 32;
        static final int TRANSACTION_setExtraLocationControllerPackageEnabled = 34;
        static final int TRANSACTION_setTestProviderEnabled = 41;
        static final int TRANSACTION_setTestProviderLocation = 40;
        static final int TRANSACTION_setTestProviderStatus = 44;
        static final int TRANSACTION_startGnssBatch = 23;
        static final int TRANSACTION_stopGnssBatch = 25;
        static final int TRANSACTION_unregisterGnssStatusCallback = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILocationManager)) {
                return new Proxy(obj);
            }
            return (ILocationManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "requestLocationUpdates";
                case 2:
                    return "removeUpdates";
                case 3:
                    return "requestGeofence";
                case 4:
                    return "removeGeofence";
                case 5:
                    return "getLastLocation";
                case 6:
                    return "registerGnssStatusCallback";
                case 7:
                    return "unregisterGnssStatusCallback";
                case 8:
                    return "geocoderIsPresent";
                case 9:
                    return "getFromLocation";
                case 10:
                    return "getFromLocationName";
                case 11:
                    return "sendNiResponse";
                case 12:
                    return "addGnssMeasurementsListener";
                case 13:
                    return "injectGnssMeasurementCorrections";
                case 14:
                    return "getGnssCapabilities";
                case 15:
                    return "removeGnssMeasurementsListener";
                case 16:
                    return "addGnssNavigationMessageListener";
                case 17:
                    return "removeGnssNavigationMessageListener";
                case 18:
                    return "getGnssYearOfHardware";
                case 19:
                    return "getGnssHardwareModelName";
                case 20:
                    return "getGnssBatchSize";
                case 21:
                    return "addGnssBatchingCallback";
                case 22:
                    return "removeGnssBatchingCallback";
                case 23:
                    return "startGnssBatch";
                case 24:
                    return "flushGnssBatch";
                case 25:
                    return "stopGnssBatch";
                case 26:
                    return "injectLocation";
                case 27:
                    return "getAllProviders";
                case 28:
                    return "getProviders";
                case 29:
                    return "getBestProvider";
                case 30:
                    return "getProviderProperties";
                case 31:
                    return "isProviderPackage";
                case 32:
                    return "setExtraLocationControllerPackage";
                case 33:
                    return "getExtraLocationControllerPackage";
                case 34:
                    return "setExtraLocationControllerPackageEnabled";
                case 35:
                    return "isExtraLocationControllerPackageEnabled";
                case 36:
                    return "isProviderEnabledForUser";
                case 37:
                    return "isLocationEnabledForUser";
                case 38:
                    return "addTestProvider";
                case 39:
                    return "removeTestProvider";
                case 40:
                    return "setTestProviderLocation";
                case 41:
                    return "setTestProviderEnabled";
                case 42:
                    return "getTestProviderCurrentRequests";
                case 43:
                    return "getGnssTimeMillis";
                case 44:
                    return "setTestProviderStatus";
                case 45:
                    return "sendExtraCommand";
                case 46:
                    return "locationCallbackFinished";
                case 47:
                    return "getBackgroundThrottlingWhitelist";
                case 48:
                    return "getIgnoreSettingsWhitelist";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.location.LocationRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v43, resolved type: android.location.Criteria} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v47, resolved type: android.location.Criteria} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v56, resolved type: com.android.internal.location.ProviderProperties} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v61, resolved type: android.location.Location} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v69, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: type inference failed for: r1v73 */
        /* JADX WARNING: type inference failed for: r1v74 */
        /* JADX WARNING: type inference failed for: r1v75 */
        /* JADX WARNING: type inference failed for: r1v76 */
        /* JADX WARNING: type inference failed for: r1v77 */
        /* JADX WARNING: type inference failed for: r1v78 */
        /* JADX WARNING: type inference failed for: r1v79 */
        /* JADX WARNING: type inference failed for: r1v80 */
        /* JADX WARNING: type inference failed for: r1v81 */
        /* JADX WARNING: type inference failed for: r1v82 */
        /* JADX WARNING: type inference failed for: r1v83 */
        /* JADX WARNING: type inference failed for: r1v84 */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r30, android.os.Parcel r31, android.os.Parcel r32, int r33) throws android.os.RemoteException {
            /*
                r29 = this;
                r13 = r29
                r14 = r30
                r15 = r31
                r12 = r32
                java.lang.String r10 = "android.location.ILocationManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r8 = 1
                if (r14 == r0) goto L_0x0563
                r0 = 0
                r1 = 0
                switch(r14) {
                    case 1: goto L_0x052a;
                    case 2: goto L_0x0501;
                    case 3: goto L_0x04c0;
                    case 4: goto L_0x048f;
                    case 5: goto L_0x0460;
                    case 6: goto L_0x0443;
                    case 7: goto L_0x042e;
                    case 8: goto L_0x041d;
                    case 9: goto L_0x03d9;
                    case 10: goto L_0x0380;
                    case 11: goto L_0x036a;
                    case 12: goto L_0x0350;
                    case 13: goto L_0x0330;
                    case 14: goto L_0x031e;
                    case 15: goto L_0x030c;
                    case 16: goto L_0x02f2;
                    case 17: goto L_0x02e0;
                    case 18: goto L_0x02d2;
                    case 19: goto L_0x02c4;
                    case 20: goto L_0x02b2;
                    case 21: goto L_0x0298;
                    case 22: goto L_0x028e;
                    case 23: goto L_0x0270;
                    case 24: goto L_0x0262;
                    case 25: goto L_0x0254;
                    case 26: goto L_0x0234;
                    case 27: goto L_0x0226;
                    case 28: goto L_0x0200;
                    case 29: goto L_0x01da;
                    case 30: goto L_0x01bf;
                    case 31: goto L_0x01ad;
                    case 32: goto L_0x019f;
                    case 33: goto L_0x0191;
                    case 34: goto L_0x017f;
                    case 35: goto L_0x0171;
                    case 36: goto L_0x015b;
                    case 37: goto L_0x0149;
                    case 38: goto L_0x0127;
                    case 39: goto L_0x0115;
                    case 40: goto L_0x00f3;
                    case 41: goto L_0x00d9;
                    case 42: goto L_0x00c3;
                    case 43: goto L_0x00ac;
                    case 44: goto L_0x007a;
                    case 45: goto L_0x0048;
                    case 46: goto L_0x0036;
                    case 47: goto L_0x0028;
                    case 48: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r30, r31, r32, r33)
                return r0
            L_0x001a:
                r15.enforceInterface(r10)
                java.lang.String[] r0 = r29.getIgnoreSettingsWhitelist()
                r32.writeNoException()
                r12.writeStringArray(r0)
                return r8
            L_0x0028:
                r15.enforceInterface(r10)
                java.lang.String[] r0 = r29.getBackgroundThrottlingWhitelist()
                r32.writeNoException()
                r12.writeStringArray(r0)
                return r8
            L_0x0036:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.ILocationListener r0 = android.location.ILocationListener.Stub.asInterface(r0)
                r13.locationCallbackFinished(r0)
                r32.writeNoException()
                return r8
            L_0x0048:
                r15.enforceInterface(r10)
                java.lang.String r2 = r31.readString()
                java.lang.String r3 = r31.readString()
                int r4 = r31.readInt()
                if (r4 == 0) goto L_0x0062
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0063
            L_0x0062:
            L_0x0063:
                boolean r4 = r13.sendExtraCommand(r2, r3, r1)
                r32.writeNoException()
                r12.writeInt(r4)
                if (r1 == 0) goto L_0x0076
                r12.writeInt(r8)
                r1.writeToParcel(r12, r8)
                goto L_0x0079
            L_0x0076:
                r12.writeInt(r0)
            L_0x0079:
                return r8
            L_0x007a:
                r15.enforceInterface(r10)
                java.lang.String r7 = r31.readString()
                int r9 = r31.readInt()
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x0095
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r3 = r0
                goto L_0x0096
            L_0x0095:
                r3 = r1
            L_0x0096:
                long r16 = r31.readLong()
                java.lang.String r11 = r31.readString()
                r0 = r29
                r1 = r7
                r2 = r9
                r4 = r16
                r6 = r11
                r0.setTestProviderStatus(r1, r2, r3, r4, r6)
                r32.writeNoException()
                return r8
            L_0x00ac:
                r15.enforceInterface(r10)
                android.location.LocationTime r1 = r29.getGnssTimeMillis()
                r32.writeNoException()
                if (r1 == 0) goto L_0x00bf
                r12.writeInt(r8)
                r1.writeToParcel(r12, r8)
                goto L_0x00c2
            L_0x00bf:
                r12.writeInt(r0)
            L_0x00c2:
                return r8
            L_0x00c3:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                java.lang.String r1 = r31.readString()
                java.util.List r2 = r13.getTestProviderCurrentRequests(r0, r1)
                r32.writeNoException()
                r12.writeTypedList(r2)
                return r8
            L_0x00d9:
                r15.enforceInterface(r10)
                java.lang.String r1 = r31.readString()
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x00e8
                r0 = r8
            L_0x00e8:
                java.lang.String r2 = r31.readString()
                r13.setTestProviderEnabled(r1, r0, r2)
                r32.writeNoException()
                return r8
            L_0x00f3:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x0109
                android.os.Parcelable$Creator<android.location.Location> r1 = android.location.Location.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.location.Location r1 = (android.location.Location) r1
                goto L_0x010a
            L_0x0109:
            L_0x010a:
                java.lang.String r2 = r31.readString()
                r13.setTestProviderLocation(r0, r1, r2)
                r32.writeNoException()
                return r8
            L_0x0115:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                java.lang.String r1 = r31.readString()
                r13.removeTestProvider(r0, r1)
                r32.writeNoException()
                return r8
            L_0x0127:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x013d
                android.os.Parcelable$Creator<com.android.internal.location.ProviderProperties> r1 = com.android.internal.location.ProviderProperties.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                com.android.internal.location.ProviderProperties r1 = (com.android.internal.location.ProviderProperties) r1
                goto L_0x013e
            L_0x013d:
            L_0x013e:
                java.lang.String r2 = r31.readString()
                r13.addTestProvider(r0, r1, r2)
                r32.writeNoException()
                return r8
            L_0x0149:
                r15.enforceInterface(r10)
                int r0 = r31.readInt()
                boolean r1 = r13.isLocationEnabledForUser(r0)
                r32.writeNoException()
                r12.writeInt(r1)
                return r8
            L_0x015b:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                int r1 = r31.readInt()
                boolean r2 = r13.isProviderEnabledForUser(r0, r1)
                r32.writeNoException()
                r12.writeInt(r2)
                return r8
            L_0x0171:
                r15.enforceInterface(r10)
                boolean r0 = r29.isExtraLocationControllerPackageEnabled()
                r32.writeNoException()
                r12.writeInt(r0)
                return r8
            L_0x017f:
                r15.enforceInterface(r10)
                int r1 = r31.readInt()
                if (r1 == 0) goto L_0x018a
                r0 = r8
            L_0x018a:
                r13.setExtraLocationControllerPackageEnabled(r0)
                r32.writeNoException()
                return r8
            L_0x0191:
                r15.enforceInterface(r10)
                java.lang.String r0 = r29.getExtraLocationControllerPackage()
                r32.writeNoException()
                r12.writeString(r0)
                return r8
            L_0x019f:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                r13.setExtraLocationControllerPackage(r0)
                r32.writeNoException()
                return r8
            L_0x01ad:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                boolean r1 = r13.isProviderPackage(r0)
                r32.writeNoException()
                r12.writeInt(r1)
                return r8
            L_0x01bf:
                r15.enforceInterface(r10)
                java.lang.String r1 = r31.readString()
                com.android.internal.location.ProviderProperties r2 = r13.getProviderProperties(r1)
                r32.writeNoException()
                if (r2 == 0) goto L_0x01d6
                r12.writeInt(r8)
                r2.writeToParcel(r12, r8)
                goto L_0x01d9
            L_0x01d6:
                r12.writeInt(r0)
            L_0x01d9:
                return r8
            L_0x01da:
                r15.enforceInterface(r10)
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x01ec
                android.os.Parcelable$Creator<android.location.Criteria> r1 = android.location.Criteria.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.location.Criteria r1 = (android.location.Criteria) r1
                goto L_0x01ed
            L_0x01ec:
            L_0x01ed:
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x01f5
                r0 = r8
            L_0x01f5:
                java.lang.String r2 = r13.getBestProvider(r1, r0)
                r32.writeNoException()
                r12.writeString(r2)
                return r8
            L_0x0200:
                r15.enforceInterface(r10)
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x0212
                android.os.Parcelable$Creator<android.location.Criteria> r1 = android.location.Criteria.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.location.Criteria r1 = (android.location.Criteria) r1
                goto L_0x0213
            L_0x0212:
            L_0x0213:
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x021b
                r0 = r8
            L_0x021b:
                java.util.List r2 = r13.getProviders(r1, r0)
                r32.writeNoException()
                r12.writeStringList(r2)
                return r8
            L_0x0226:
                r15.enforceInterface(r10)
                java.util.List r0 = r29.getAllProviders()
                r32.writeNoException()
                r12.writeStringList(r0)
                return r8
            L_0x0234:
                r15.enforceInterface(r10)
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x0247
                android.os.Parcelable$Creator<android.location.Location> r0 = android.location.Location.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                r1 = r0
                android.location.Location r1 = (android.location.Location) r1
                goto L_0x0248
            L_0x0247:
            L_0x0248:
                r0 = r1
                boolean r1 = r13.injectLocation(r0)
                r32.writeNoException()
                r12.writeInt(r1)
                return r8
            L_0x0254:
                r15.enforceInterface(r10)
                boolean r0 = r29.stopGnssBatch()
                r32.writeNoException()
                r12.writeInt(r0)
                return r8
            L_0x0262:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                r13.flushGnssBatch(r0)
                r32.writeNoException()
                return r8
            L_0x0270:
                r15.enforceInterface(r10)
                long r1 = r31.readLong()
                int r3 = r31.readInt()
                if (r3 == 0) goto L_0x027f
                r0 = r8
            L_0x027f:
                java.lang.String r3 = r31.readString()
                boolean r4 = r13.startGnssBatch(r1, r0, r3)
                r32.writeNoException()
                r12.writeInt(r4)
                return r8
            L_0x028e:
                r15.enforceInterface(r10)
                r29.removeGnssBatchingCallback()
                r32.writeNoException()
                return r8
            L_0x0298:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IBatchedLocationCallback r0 = android.location.IBatchedLocationCallback.Stub.asInterface(r0)
                java.lang.String r1 = r31.readString()
                boolean r2 = r13.addGnssBatchingCallback(r0, r1)
                r32.writeNoException()
                r12.writeInt(r2)
                return r8
            L_0x02b2:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                int r1 = r13.getGnssBatchSize(r0)
                r32.writeNoException()
                r12.writeInt(r1)
                return r8
            L_0x02c4:
                r15.enforceInterface(r10)
                java.lang.String r0 = r29.getGnssHardwareModelName()
                r32.writeNoException()
                r12.writeString(r0)
                return r8
            L_0x02d2:
                r15.enforceInterface(r10)
                int r0 = r29.getGnssYearOfHardware()
                r32.writeNoException()
                r12.writeInt(r0)
                return r8
            L_0x02e0:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IGnssNavigationMessageListener r0 = android.location.IGnssNavigationMessageListener.Stub.asInterface(r0)
                r13.removeGnssNavigationMessageListener(r0)
                r32.writeNoException()
                return r8
            L_0x02f2:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IGnssNavigationMessageListener r0 = android.location.IGnssNavigationMessageListener.Stub.asInterface(r0)
                java.lang.String r1 = r31.readString()
                boolean r2 = r13.addGnssNavigationMessageListener(r0, r1)
                r32.writeNoException()
                r12.writeInt(r2)
                return r8
            L_0x030c:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IGnssMeasurementsListener r0 = android.location.IGnssMeasurementsListener.Stub.asInterface(r0)
                r13.removeGnssMeasurementsListener(r0)
                r32.writeNoException()
                return r8
            L_0x031e:
                r15.enforceInterface(r10)
                java.lang.String r0 = r31.readString()
                long r1 = r13.getGnssCapabilities(r0)
                r32.writeNoException()
                r12.writeLong(r1)
                return r8
            L_0x0330:
                r15.enforceInterface(r10)
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x0343
                android.os.Parcelable$Creator<android.location.GnssMeasurementCorrections> r0 = android.location.GnssMeasurementCorrections.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                r1 = r0
                android.location.GnssMeasurementCorrections r1 = (android.location.GnssMeasurementCorrections) r1
                goto L_0x0344
            L_0x0343:
            L_0x0344:
                r0 = r1
                java.lang.String r1 = r31.readString()
                r13.injectGnssMeasurementCorrections(r0, r1)
                r32.writeNoException()
                return r8
            L_0x0350:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IGnssMeasurementsListener r0 = android.location.IGnssMeasurementsListener.Stub.asInterface(r0)
                java.lang.String r1 = r31.readString()
                boolean r2 = r13.addGnssMeasurementsListener(r0, r1)
                r32.writeNoException()
                r12.writeInt(r2)
                return r8
            L_0x036a:
                r15.enforceInterface(r10)
                int r0 = r31.readInt()
                int r1 = r31.readInt()
                boolean r2 = r13.sendNiResponse(r0, r1)
                r32.writeNoException()
                r12.writeInt(r2)
                return r8
            L_0x0380:
                r15.enforceInterface(r10)
                java.lang.String r16 = r31.readString()
                double r17 = r31.readDouble()
                double r19 = r31.readDouble()
                double r21 = r31.readDouble()
                double r23 = r31.readDouble()
                int r25 = r31.readInt()
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x03ab
                android.os.Parcelable$Creator<android.location.GeocoderParams> r0 = android.location.GeocoderParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.location.GeocoderParams r0 = (android.location.GeocoderParams) r0
                r11 = r0
                goto L_0x03ac
            L_0x03ab:
                r11 = r1
            L_0x03ac:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r9 = r0
                r0 = r29
                r1 = r16
                r2 = r17
                r4 = r19
                r6 = r21
                r14 = r8
                r26 = r9
                r8 = r23
                r27 = r10
                r10 = r25
                r14 = r12
                r12 = r26
                java.lang.String r0 = r0.getFromLocationName(r1, r2, r4, r6, r8, r10, r11, r12)
                r32.writeNoException()
                r14.writeString(r0)
                r1 = r26
                r14.writeTypedList(r1)
                r2 = 1
                return r2
            L_0x03d9:
                r27 = r10
                r14 = r12
                r8 = r27
                r15.enforceInterface(r8)
                double r9 = r31.readDouble()
                double r11 = r31.readDouble()
                int r16 = r31.readInt()
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x03fd
                android.os.Parcelable$Creator<android.location.GeocoderParams> r0 = android.location.GeocoderParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.location.GeocoderParams r0 = (android.location.GeocoderParams) r0
                r6 = r0
                goto L_0x03fe
            L_0x03fd:
                r6 = r1
            L_0x03fe:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r7 = r0
                r0 = r29
                r1 = r9
                r3 = r11
                r5 = r16
                r28 = r7
                java.lang.String r0 = r0.getFromLocation(r1, r3, r5, r6, r7)
                r32.writeNoException()
                r14.writeString(r0)
                r1 = r28
                r14.writeTypedList(r1)
                r2 = 1
                return r2
            L_0x041d:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                boolean r0 = r29.geocoderIsPresent()
                r32.writeNoException()
                r14.writeInt(r0)
                r1 = 1
                return r1
            L_0x042e:
                r1 = r8
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IGnssStatusListener r0 = android.location.IGnssStatusListener.Stub.asInterface(r0)
                r13.unregisterGnssStatusCallback(r0)
                r32.writeNoException()
                return r1
            L_0x0443:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.IGnssStatusListener r0 = android.location.IGnssStatusListener.Stub.asInterface(r0)
                java.lang.String r1 = r31.readString()
                boolean r2 = r13.registerGnssStatusCallback(r0, r1)
                r32.writeNoException()
                r14.writeInt(r2)
                r3 = 1
                return r3
            L_0x0460:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x0474
                android.os.Parcelable$Creator<android.location.LocationRequest> r1 = android.location.LocationRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.location.LocationRequest r1 = (android.location.LocationRequest) r1
                goto L_0x0475
            L_0x0474:
            L_0x0475:
                java.lang.String r2 = r31.readString()
                android.location.Location r3 = r13.getLastLocation(r1, r2)
                r32.writeNoException()
                if (r3 == 0) goto L_0x048a
                r4 = 1
                r14.writeInt(r4)
                r3.writeToParcel(r14, r4)
                goto L_0x048e
            L_0x048a:
                r4 = 1
                r14.writeInt(r0)
            L_0x048e:
                return r4
            L_0x048f:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x04a3
                android.os.Parcelable$Creator<android.location.Geofence> r0 = android.location.Geofence.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.location.Geofence r0 = (android.location.Geofence) r0
                goto L_0x04a4
            L_0x04a3:
                r0 = r1
            L_0x04a4:
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x04b3
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x04b4
            L_0x04b3:
            L_0x04b4:
                java.lang.String r2 = r31.readString()
                r13.removeGeofence(r0, r1, r2)
                r32.writeNoException()
                r3 = 1
                return r3
            L_0x04c0:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x04d4
                android.os.Parcelable$Creator<android.location.LocationRequest> r0 = android.location.LocationRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.location.LocationRequest r0 = (android.location.LocationRequest) r0
                goto L_0x04d5
            L_0x04d4:
                r0 = r1
            L_0x04d5:
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x04e4
                android.os.Parcelable$Creator<android.location.Geofence> r2 = android.location.Geofence.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r15)
                android.location.Geofence r2 = (android.location.Geofence) r2
                goto L_0x04e5
            L_0x04e4:
                r2 = r1
            L_0x04e5:
                int r3 = r31.readInt()
                if (r3 == 0) goto L_0x04f4
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x04f5
            L_0x04f4:
            L_0x04f5:
                java.lang.String r3 = r31.readString()
                r13.requestGeofence(r0, r2, r1, r3)
                r32.writeNoException()
                r4 = 1
                return r4
            L_0x0501:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                android.os.IBinder r0 = r31.readStrongBinder()
                android.location.ILocationListener r0 = android.location.ILocationListener.Stub.asInterface(r0)
                int r2 = r31.readInt()
                if (r2 == 0) goto L_0x051d
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x051e
            L_0x051d:
            L_0x051e:
                java.lang.String r2 = r31.readString()
                r13.removeUpdates(r0, r1, r2)
                r32.writeNoException()
                r3 = 1
                return r3
            L_0x052a:
                r8 = r10
                r14 = r12
                r15.enforceInterface(r8)
                int r0 = r31.readInt()
                if (r0 == 0) goto L_0x053e
                android.os.Parcelable$Creator<android.location.LocationRequest> r0 = android.location.LocationRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.location.LocationRequest r0 = (android.location.LocationRequest) r0
                goto L_0x053f
            L_0x053e:
                r0 = r1
            L_0x053f:
                android.os.IBinder r2 = r31.readStrongBinder()
                android.location.ILocationListener r2 = android.location.ILocationListener.Stub.asInterface(r2)
                int r3 = r31.readInt()
                if (r3 == 0) goto L_0x0556
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0557
            L_0x0556:
            L_0x0557:
                java.lang.String r3 = r31.readString()
                r13.requestLocationUpdates(r0, r2, r1, r3)
                r32.writeNoException()
                r4 = 1
                return r4
            L_0x0563:
                r4 = r8
                r8 = r10
                r14 = r12
                r14.writeString(r8)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.location.ILocationManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILocationManager {
            public static ILocationManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void requestLocationUpdates(LocationRequest request, ILocationListener listener, PendingIntent intent, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestLocationUpdates(request, listener, intent, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeUpdates(ILocationListener listener, PendingIntent intent, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeUpdates(listener, intent, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestGeofence(LocationRequest request, Geofence geofence, PendingIntent intent, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (geofence != null) {
                        _data.writeInt(1);
                        geofence.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestGeofence(request, geofence, intent, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeGeofence(Geofence fence, PendingIntent intent, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fence != null) {
                        _data.writeInt(1);
                        fence.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeGeofence(fence, intent, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Location getLastLocation(LocationRequest request, String packageName) throws RemoteException {
                Location _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastLocation(request, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Location.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Location _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean registerGnssStatusCallback(IGnssStatusListener callback, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerGnssStatusCallback(callback, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterGnssStatusCallback(IGnssStatusListener callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterGnssStatusCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean geocoderIsPresent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().geocoderIsPresent();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getFromLocation(double latitude, double longitude, int maxResults, GeocoderParams params, List<Address> addrs) throws RemoteException {
                GeocoderParams geocoderParams = params;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeDouble(latitude);
                        try {
                            _data.writeDouble(longitude);
                            _data.writeInt(maxResults);
                            if (geocoderParams != null) {
                                _data.writeInt(1);
                                geocoderParams.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String _result = _reply.readString();
                                try {
                                    _reply.readTypedList(addrs, Address.CREATOR);
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                } catch (Throwable th) {
                                    th = th;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } else {
                                String fromLocation = Stub.getDefaultImpl().getFromLocation(latitude, longitude, maxResults, params, addrs);
                                _reply.recycle();
                                _data.recycle();
                                return fromLocation;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            List<Address> list = addrs;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        double d = longitude;
                        List<Address> list2 = addrs;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    double d2 = latitude;
                    double d3 = longitude;
                    List<Address> list22 = addrs;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String getFromLocationName(String locationName, double lowerLeftLatitude, double lowerLeftLongitude, double upperRightLatitude, double upperRightLongitude, int maxResults, GeocoderParams params, List<Address> addrs) throws RemoteException {
                Parcel _reply;
                GeocoderParams geocoderParams = params;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(locationName);
                    _data.writeDouble(lowerLeftLatitude);
                    _data.writeDouble(lowerLeftLongitude);
                    _data.writeDouble(upperRightLatitude);
                    _data.writeDouble(upperRightLongitude);
                    _data.writeInt(maxResults);
                    if (geocoderParams != null) {
                        try {
                            _data.writeInt(1);
                            geocoderParams.writeToParcel(_data, 0);
                        } catch (Throwable th) {
                            th = th;
                            List<Address> list = addrs;
                            _reply = _reply2;
                        }
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, _data, _reply2, 0)) {
                        try {
                            if (Stub.getDefaultImpl() != null) {
                                Parcel _reply3 = _reply2;
                                try {
                                    String fromLocationName = Stub.getDefaultImpl().getFromLocationName(locationName, lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude, maxResults, params, addrs);
                                    _reply3.recycle();
                                    _data.recycle();
                                    return fromLocationName;
                                } catch (Throwable th2) {
                                    th = th2;
                                    List<Address> list2 = addrs;
                                    _reply = _reply3;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            List<Address> list3 = addrs;
                            _reply = _reply2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    }
                    Parcel _reply4 = _reply2;
                    try {
                        _reply4.readException();
                        String _result = _reply4.readString();
                        _reply = _reply4;
                        try {
                            _reply.readTypedList(addrs, Address.CREATOR);
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        List<Address> list4 = addrs;
                        _reply = _reply4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    List<Address> list5 = addrs;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean sendNiResponse(int notifId, int userResponse) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(notifId);
                    _data.writeInt(userResponse);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendNiResponse(notifId, userResponse);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addGnssMeasurementsListener(IGnssMeasurementsListener listener, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addGnssMeasurementsListener(listener, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void injectGnssMeasurementCorrections(GnssMeasurementCorrections corrections, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (corrections != null) {
                        _data.writeInt(1);
                        corrections.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().injectGnssMeasurementCorrections(corrections, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getGnssCapabilities(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGnssCapabilities(packageName);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeGnssMeasurementsListener(IGnssMeasurementsListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeGnssMeasurementsListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addGnssNavigationMessageListener(IGnssNavigationMessageListener listener, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addGnssNavigationMessageListener(listener, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeGnssNavigationMessageListener(IGnssNavigationMessageListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeGnssNavigationMessageListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getGnssYearOfHardware() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGnssYearOfHardware();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getGnssHardwareModelName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGnssHardwareModelName();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getGnssBatchSize(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGnssBatchSize(packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addGnssBatchingCallback(IBatchedLocationCallback callback, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addGnssBatchingCallback(callback, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeGnssBatchingCallback() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeGnssBatchingCallback();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startGnssBatch(long periodNanos, boolean wakeOnFifoFull, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(periodNanos);
                    _data.writeInt(wakeOnFifoFull);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startGnssBatch(periodNanos, wakeOnFifoFull, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void flushGnssBatch(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().flushGnssBatch(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean stopGnssBatch() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopGnssBatch();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean injectLocation(Location location) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().injectLocation(location);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getAllProviders() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllProviders();
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getProviders(Criteria criteria, boolean enabledOnly) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (criteria != null) {
                        _data.writeInt(1);
                        criteria.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabledOnly);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProviders(criteria, enabledOnly);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getBestProvider(Criteria criteria, boolean enabledOnly) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (criteria != null) {
                        _data.writeInt(1);
                        criteria.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabledOnly);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBestProvider(criteria, enabledOnly);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ProviderProperties getProviderProperties(String provider) throws RemoteException {
                ProviderProperties _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProviderProperties(provider);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProviderProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ProviderProperties _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isProviderPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isProviderPackage(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setExtraLocationControllerPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setExtraLocationControllerPackage(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getExtraLocationControllerPackage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getExtraLocationControllerPackage();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setExtraLocationControllerPackageEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setExtraLocationControllerPackageEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isExtraLocationControllerPackageEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isExtraLocationControllerPackageEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isProviderEnabledForUser(String provider, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isProviderEnabledForUser(provider, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isLocationEnabledForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLocationEnabledForUser(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addTestProvider(String name, ProviderProperties properties, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (properties != null) {
                        _data.writeInt(1);
                        properties.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addTestProvider(name, properties, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeTestProvider(String provider, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeTestProvider(provider, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestProviderLocation(String provider, Location loc, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    if (loc != null) {
                        _data.writeInt(1);
                        loc.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestProviderLocation(provider, loc, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestProviderEnabled(String provider, boolean enabled, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeInt(enabled);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestProviderEnabled(provider, enabled, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<LocationRequest> getTestProviderCurrentRequests(String provider, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTestProviderCurrentRequests(provider, opPackageName);
                    }
                    _reply.readException();
                    List<LocationRequest> _result = _reply.createTypedArrayList(LocationRequest.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LocationTime getGnssTimeMillis() throws RemoteException {
                LocationTime _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGnssTimeMillis();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LocationTime.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LocationTime _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestProviderStatus(String provider, int status, Bundle extras, long updateTime, String opPackageName) throws RemoteException {
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(provider);
                        try {
                            _data.writeInt(status);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            long j = updateTime;
                            String str = opPackageName;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i = status;
                        long j2 = updateTime;
                        String str2 = opPackageName;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(updateTime);
                        try {
                            _data.writeString(opPackageName);
                            if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setTestProviderStatus(provider, status, extras, updateTime, opPackageName);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str22 = opPackageName;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str3 = provider;
                    int i2 = status;
                    long j22 = updateTime;
                    String str222 = opPackageName;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean sendExtraCommand(String provider, String command, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeString(command);
                    boolean _result = true;
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendExtraCommand(provider, command, extras);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    if (_reply.readInt() != 0) {
                        extras.readFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void locationCallbackFinished(ILocationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().locationCallbackFinished(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getBackgroundThrottlingWhitelist() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBackgroundThrottlingWhitelist();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getIgnoreSettingsWhitelist() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIgnoreSettingsWhitelist();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILocationManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
