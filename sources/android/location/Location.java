package android.location;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.SettingsStringUtil;
import android.util.Printer;
import android.util.TimeUtils;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Location implements Parcelable {
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            Location l = new Location(in.readString());
            long unused = l.mTime = in.readLong();
            long unused2 = l.mElapsedRealtimeNanos = in.readLong();
            double unused3 = l.mElapsedRealtimeUncertaintyNanos = in.readDouble();
            int unused4 = l.mFieldsMask = in.readInt();
            double unused5 = l.mLatitude = in.readDouble();
            double unused6 = l.mLongitude = in.readDouble();
            double unused7 = l.mAltitude = in.readDouble();
            float unused8 = l.mSpeed = in.readFloat();
            float unused9 = l.mBearing = in.readFloat();
            float unused10 = l.mHorizontalAccuracyMeters = in.readFloat();
            float unused11 = l.mVerticalAccuracyMeters = in.readFloat();
            float unused12 = l.mSpeedAccuracyMetersPerSecond = in.readFloat();
            float unused13 = l.mBearingAccuracyDegrees = in.readFloat();
            Bundle unused14 = l.mExtras = Bundle.setDefusable(in.readBundle(), true);
            return l;
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
    public static final String EXTRA_COARSE_LOCATION = "coarseLocation";
    public static final String EXTRA_NO_GPS_LOCATION = "noGPSLocation";
    public static final int FORMAT_DEGREES = 0;
    public static final int FORMAT_MINUTES = 1;
    public static final int FORMAT_SECONDS = 2;
    private static final int HAS_ALTITUDE_MASK = 1;
    private static final int HAS_BEARING_ACCURACY_MASK = 128;
    private static final int HAS_BEARING_MASK = 4;
    private static final int HAS_ELAPSED_REALTIME_UNCERTAINTY_MASK = 256;
    private static final int HAS_HORIZONTAL_ACCURACY_MASK = 8;
    private static final int HAS_MOCK_PROVIDER_MASK = 16;
    private static final int HAS_SPEED_ACCURACY_MASK = 64;
    private static final int HAS_SPEED_MASK = 2;
    private static final int HAS_VERTICAL_ACCURACY_MASK = 32;
    private static ThreadLocal<BearingDistanceCache> sBearingDistanceCache = new ThreadLocal<BearingDistanceCache>() {
        /* access modifiers changed from: protected */
        public BearingDistanceCache initialValue() {
            return new BearingDistanceCache();
        }
    };
    /* access modifiers changed from: private */
    public double mAltitude = 0.0d;
    /* access modifiers changed from: private */
    public float mBearing = 0.0f;
    /* access modifiers changed from: private */
    public float mBearingAccuracyDegrees = 0.0f;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public long mElapsedRealtimeNanos = 0;
    /* access modifiers changed from: private */
    public double mElapsedRealtimeUncertaintyNanos = 0.0d;
    /* access modifiers changed from: private */
    public Bundle mExtras = null;
    /* access modifiers changed from: private */
    public int mFieldsMask = 0;
    /* access modifiers changed from: private */
    public float mHorizontalAccuracyMeters = 0.0f;
    /* access modifiers changed from: private */
    public double mLatitude = 0.0d;
    /* access modifiers changed from: private */
    public double mLongitude = 0.0d;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private String mProvider;
    /* access modifiers changed from: private */
    public float mSpeed = 0.0f;
    /* access modifiers changed from: private */
    public float mSpeedAccuracyMetersPerSecond = 0.0f;
    /* access modifiers changed from: private */
    public long mTime = 0;
    /* access modifiers changed from: private */
    public float mVerticalAccuracyMeters = 0.0f;

    public Location(String provider) {
        this.mProvider = provider;
    }

    public Location(Location l) {
        set(l);
    }

    public void set(Location l) {
        this.mProvider = l.mProvider;
        this.mTime = l.mTime;
        this.mElapsedRealtimeNanos = l.mElapsedRealtimeNanos;
        this.mElapsedRealtimeUncertaintyNanos = l.mElapsedRealtimeUncertaintyNanos;
        this.mFieldsMask = l.mFieldsMask;
        this.mLatitude = l.mLatitude;
        this.mLongitude = l.mLongitude;
        this.mAltitude = l.mAltitude;
        this.mSpeed = l.mSpeed;
        this.mBearing = l.mBearing;
        this.mHorizontalAccuracyMeters = l.mHorizontalAccuracyMeters;
        this.mVerticalAccuracyMeters = l.mVerticalAccuracyMeters;
        this.mSpeedAccuracyMetersPerSecond = l.mSpeedAccuracyMetersPerSecond;
        this.mBearingAccuracyDegrees = l.mBearingAccuracyDegrees;
        this.mExtras = l.mExtras == null ? null : new Bundle(l.mExtras);
    }

    public void reset() {
        this.mProvider = null;
        this.mTime = 0;
        this.mElapsedRealtimeNanos = 0;
        this.mElapsedRealtimeUncertaintyNanos = 0.0d;
        this.mFieldsMask = 0;
        this.mLatitude = 0.0d;
        this.mLongitude = 0.0d;
        this.mAltitude = 0.0d;
        this.mSpeed = 0.0f;
        this.mBearing = 0.0f;
        this.mHorizontalAccuracyMeters = 0.0f;
        this.mVerticalAccuracyMeters = 0.0f;
        this.mSpeedAccuracyMetersPerSecond = 0.0f;
        this.mBearingAccuracyDegrees = 0.0f;
        this.mExtras = null;
    }

    public static String convert(double coordinate, int outputType) {
        if (coordinate < -180.0d || coordinate > 180.0d || Double.isNaN(coordinate)) {
            throw new IllegalArgumentException("coordinate=" + coordinate);
        } else if (outputType == 0 || outputType == 1 || outputType == 2) {
            StringBuilder sb = new StringBuilder();
            if (coordinate < 0.0d) {
                sb.append('-');
                coordinate = -coordinate;
            }
            DecimalFormat df = new DecimalFormat("###.#####");
            if (outputType == 1 || outputType == 2) {
                int degrees = (int) Math.floor(coordinate);
                sb.append(degrees);
                sb.append(':');
                coordinate = (coordinate - ((double) degrees)) * 60.0d;
                if (outputType == 2) {
                    int minutes = (int) Math.floor(coordinate);
                    sb.append(minutes);
                    sb.append(':');
                    coordinate = (coordinate - ((double) minutes)) * 60.0d;
                }
            }
            sb.append(df.format(coordinate));
            return sb.toString();
        } else {
            throw new IllegalArgumentException("outputType=" + outputType);
        }
    }

    public static double convert(String coordinate) {
        double min;
        String coordinate2 = coordinate;
        if (coordinate2 != null) {
            boolean negative = false;
            if (coordinate2.charAt(0) == '-') {
                coordinate2 = coordinate2.substring(1);
                negative = true;
            }
            boolean negative2 = negative;
            String coordinate3 = coordinate2;
            StringTokenizer st = new StringTokenizer(coordinate3, SettingsStringUtil.DELIMITER);
            int tokens = st.countTokens();
            if (tokens >= 1) {
                try {
                    String degrees = st.nextToken();
                    if (tokens == 1) {
                        try {
                            double val = Double.parseDouble(degrees);
                            return negative2 ? -val : val;
                        } catch (NumberFormatException e) {
                            StringTokenizer stringTokenizer = st;
                            throw new IllegalArgumentException("coordinate=" + coordinate3);
                        }
                    } else {
                        String minutes = st.nextToken();
                        int deg = Integer.parseInt(degrees);
                        double sec = 0.0d;
                        boolean secPresent = false;
                        if (st.hasMoreTokens()) {
                            min = (double) Integer.parseInt(minutes);
                            sec = Double.parseDouble(st.nextToken());
                            secPresent = true;
                        } else {
                            min = Double.parseDouble(minutes);
                        }
                        boolean isNegative180 = negative2 && deg == 180 && min == 0.0d && sec == 0.0d;
                        StringTokenizer stringTokenizer2 = st;
                        if (((double) deg) < 0.0d || (deg > 179 && !isNegative180)) {
                            throw new IllegalArgumentException("coordinate=" + coordinate3);
                        } else if (min < 0.0d || min >= 60.0d || (secPresent && min > 59.0d)) {
                            throw new IllegalArgumentException("coordinate=" + coordinate3);
                        } else if (sec < 0.0d || sec >= 60.0d) {
                            try {
                                throw new IllegalArgumentException("coordinate=" + coordinate3);
                            } catch (NumberFormatException e2) {
                                throw new IllegalArgumentException("coordinate=" + coordinate3);
                            }
                        } else {
                            double val2 = (((((double) deg) * 3600.0d) + (60.0d * min)) + sec) / 3600.0d;
                            return negative2 ? -val2 : val2;
                        }
                    }
                } catch (NumberFormatException e3) {
                    StringTokenizer stringTokenizer3 = st;
                    throw new IllegalArgumentException("coordinate=" + coordinate3);
                }
            } else {
                throw new IllegalArgumentException("coordinate=" + coordinate3);
            }
        } else {
            throw new NullPointerException("coordinate");
        }
    }

    private static void computeDistanceAndBearing(double lat1, double lon1, double lat2, double lon2, BearingDistanceCache results) {
        double lon22;
        double lat22;
        double lon12;
        BearingDistanceCache bearingDistanceCache = results;
        double lat12 = lat1 * 0.017453292519943295d;
        double cosSigma = lat2 * 0.017453292519943295d;
        double sinSigma = lon1 * 0.017453292519943295d;
        double lon23 = 0.017453292519943295d * lon2;
        double f = (6378137.0d - 6356752.3142d) / 6378137.0d;
        double aSqMinusBSqOverBSq = ((6378137.0d * 6378137.0d) - (6356752.3142d * 6356752.3142d)) / (6356752.3142d * 6356752.3142d);
        double L = lon23 - sinSigma;
        double A = 0.0d;
        double U1 = Math.atan((1.0d - f) * Math.tan(lat12));
        double U2 = Math.atan((1.0d - f) * Math.tan(cosSigma));
        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;
        double sigma = 0.0d;
        double deltaSigma = 0.0d;
        double sinSigma2 = 0.0d;
        double cosLambda = 0.0d;
        double sinLambda = 0.0d;
        double lambda = L;
        int iter = 0;
        while (true) {
            lon22 = lon23;
            int iter2 = iter;
            if (iter2 >= 20) {
                lat22 = cosSigma;
                lon12 = sinSigma;
                double lon13 = sinSigma2;
                break;
            }
            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = (cosU1 * sinU2) - ((sinU1 * cosU2) * cosLambda);
            lon12 = sinSigma;
            double lon14 = Math.sqrt((t1 * t1) + (t2 * t2));
            lat22 = cosSigma;
            double lat23 = sinU1sinU2 + (cosU1cosU2 * cosLambda);
            sigma = Math.atan2(lon14, lat23);
            double d = 0.0d;
            double sinAlpha = lon14 == 0.0d ? 0.0d : (cosU1cosU2 * sinLambda) / lon14;
            double cosSqAlpha = 1.0d - (sinAlpha * sinAlpha);
            if (cosSqAlpha != 0.0d) {
                d = lat23 - ((sinU1sinU2 * 2.0d) / cosSqAlpha);
            }
            double cos2SM = d;
            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq;
            A = ((uSquared / 16384.0d) * (((((320.0d - (175.0d * uSquared)) * uSquared) - 0.005859375d) * uSquared) + 4096.0d)) + 1.0d;
            double B = (uSquared / 1024.0d) * (((((74.0d - (47.0d * uSquared)) * uSquared) - 0.03125d) * uSquared) + 256.0d);
            double C = (f / 16.0d) * cosSqAlpha * (((4.0d - (3.0d * cosSqAlpha)) * f) + 4.0d);
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * lon14 * (cos2SM + ((B / 4.0d) * ((((cos2SMSq * 2.0d) - 4.0d) * lat23) - ((((B / 6.0d) * cos2SM) * (((lon14 * 4.0d) * lon14) - 1.5d)) * ((4.0d * cos2SMSq) - 1.5d)))));
            lambda = L + ((1.0d - C) * f * sinAlpha * (sigma + (C * lon14 * (cos2SM + (C * lat23 * (((2.0d * cos2SM) * cos2SM) - 4.0d))))));
            if (Math.abs((lambda - lambdaOrig) / lambda) < 1.0E-12d) {
                double uSquared2 = lat23;
                break;
            }
            iter = iter2 + 1;
            double d2 = lat23;
            sinSigma2 = lon14;
            lon23 = lon22;
            sinSigma = lon12;
            cosSigma = lat22;
        }
        float distance = (float) (6356752.3142d * A * (sigma - deltaSigma));
        float unused = bearingDistanceCache.mDistance = distance;
        float f2 = distance;
        float initialBearing = (float) (((double) ((float) Math.atan2(cosU2 * sinLambda, (cosU1 * sinU2) - ((sinU1 * cosU2) * cosLambda)))) * 57.29577951308232d);
        float unused2 = bearingDistanceCache.mInitialBearing = initialBearing;
        float finalBearing = (float) (((double) ((float) Math.atan2(cosU1 * sinLambda, ((-sinU1) * cosU2) + (cosU1 * sinU2 * cosLambda)))) * 57.29577951308232d);
        float unused3 = bearingDistanceCache.mFinalBearing = finalBearing;
        double unused4 = bearingDistanceCache.mLat1 = lat12;
        double unused5 = bearingDistanceCache.mLat2 = lat22;
        float f3 = initialBearing;
        float f4 = finalBearing;
        double lon15 = lon12;
        double unused6 = bearingDistanceCache.mLon1 = lon15;
        double d3 = lon15;
        double unused7 = bearingDistanceCache.mLon2 = lon22;
    }

    public static void distanceBetween(double startLatitude, double startLongitude, double endLatitude, double endLongitude, float[] results) {
        float[] fArr = results;
        if (fArr == null || fArr.length < 1) {
            throw new IllegalArgumentException("results is null or has length < 1");
        }
        BearingDistanceCache cache = sBearingDistanceCache.get();
        computeDistanceAndBearing(startLatitude, startLongitude, endLatitude, endLongitude, cache);
        fArr[0] = cache.mDistance;
        if (fArr.length > 1) {
            fArr[1] = cache.mInitialBearing;
            if (fArr.length > 2) {
                fArr[2] = cache.mFinalBearing;
            }
        }
    }

    public float distanceTo(Location dest) {
        BearingDistanceCache cache = sBearingDistanceCache.get();
        if (!(this.mLatitude == cache.mLat1 && this.mLongitude == cache.mLon1 && dest.mLatitude == cache.mLat2 && dest.mLongitude == cache.mLon2)) {
            computeDistanceAndBearing(this.mLatitude, this.mLongitude, dest.mLatitude, dest.mLongitude, cache);
        }
        return cache.mDistance;
    }

    public float bearingTo(Location dest) {
        BearingDistanceCache cache = sBearingDistanceCache.get();
        if (!(this.mLatitude == cache.mLat1 && this.mLongitude == cache.mLon1 && dest.mLatitude == cache.mLat2 && dest.mLongitude == cache.mLon2)) {
            computeDistanceAndBearing(this.mLatitude, this.mLongitude, dest.mLatitude, dest.mLongitude, cache);
        }
        return cache.mInitialBearing;
    }

    public String getProvider() {
        return this.mProvider;
    }

    public void setProvider(String provider) {
        this.mProvider = provider;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    private String getGpsLocalTime(long gpsTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(gpsTime);
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar.getTime());
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    public void setElapsedRealtimeNanos(long time) {
        this.mElapsedRealtimeNanos = time;
    }

    public double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNanos;
    }

    public void setElapsedRealtimeUncertaintyNanos(double time) {
        this.mElapsedRealtimeUncertaintyNanos = time;
        this.mFieldsMask |= 256;
    }

    public boolean hasElapsedRealtimeUncertaintyNanos() {
        return (this.mFieldsMask & 256) != 0;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public boolean hasAltitude() {
        return (this.mFieldsMask & 1) != 0;
    }

    public double getAltitude() {
        return this.mAltitude;
    }

    public void setAltitude(double altitude) {
        this.mAltitude = altitude;
        this.mFieldsMask |= 1;
    }

    @Deprecated
    public void removeAltitude() {
        this.mAltitude = 0.0d;
        this.mFieldsMask &= -2;
    }

    public boolean hasSpeed() {
        return (this.mFieldsMask & 2) != 0;
    }

    public float getSpeed() {
        return this.mSpeed;
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
        this.mFieldsMask |= 2;
    }

    @Deprecated
    public void removeSpeed() {
        this.mSpeed = 0.0f;
        this.mFieldsMask &= -3;
    }

    public boolean hasBearing() {
        return (this.mFieldsMask & 4) != 0;
    }

    public float getBearing() {
        return this.mBearing;
    }

    public void setBearing(float bearing) {
        while (bearing < 0.0f) {
            bearing += 360.0f;
        }
        while (bearing >= 360.0f) {
            bearing -= 360.0f;
        }
        this.mBearing = bearing;
        this.mFieldsMask |= 4;
    }

    @Deprecated
    public void removeBearing() {
        this.mBearing = 0.0f;
        this.mFieldsMask &= -5;
    }

    public boolean hasAccuracy() {
        return (this.mFieldsMask & 8) != 0;
    }

    public float getAccuracy() {
        return this.mHorizontalAccuracyMeters;
    }

    public void setAccuracy(float horizontalAccuracy) {
        this.mHorizontalAccuracyMeters = horizontalAccuracy;
        this.mFieldsMask |= 8;
    }

    @Deprecated
    public void removeAccuracy() {
        this.mHorizontalAccuracyMeters = 0.0f;
        this.mFieldsMask &= -9;
    }

    public boolean hasVerticalAccuracy() {
        return (this.mFieldsMask & 32) != 0;
    }

    public float getVerticalAccuracyMeters() {
        return this.mVerticalAccuracyMeters;
    }

    public void setVerticalAccuracyMeters(float verticalAccuracyMeters) {
        this.mVerticalAccuracyMeters = verticalAccuracyMeters;
        this.mFieldsMask |= 32;
    }

    @Deprecated
    public void removeVerticalAccuracy() {
        this.mVerticalAccuracyMeters = 0.0f;
        this.mFieldsMask &= -33;
    }

    public boolean hasSpeedAccuracy() {
        return (this.mFieldsMask & 64) != 0;
    }

    public float getSpeedAccuracyMetersPerSecond() {
        return this.mSpeedAccuracyMetersPerSecond;
    }

    public void setSpeedAccuracyMetersPerSecond(float speedAccuracyMeterPerSecond) {
        this.mSpeedAccuracyMetersPerSecond = speedAccuracyMeterPerSecond;
        this.mFieldsMask |= 64;
    }

    @Deprecated
    public void removeSpeedAccuracy() {
        this.mSpeedAccuracyMetersPerSecond = 0.0f;
        this.mFieldsMask &= -65;
    }

    public boolean hasBearingAccuracy() {
        return (this.mFieldsMask & 128) != 0;
    }

    public float getBearingAccuracyDegrees() {
        return this.mBearingAccuracyDegrees;
    }

    public void setBearingAccuracyDegrees(float bearingAccuracyDegrees) {
        this.mBearingAccuracyDegrees = bearingAccuracyDegrees;
        this.mFieldsMask |= 128;
    }

    @Deprecated
    public void removeBearingAccuracy() {
        this.mBearingAccuracyDegrees = 0.0f;
        this.mFieldsMask &= -129;
    }

    @SystemApi
    public boolean isComplete() {
        if (this.mProvider == null || !hasAccuracy() || this.mTime == 0 || this.mElapsedRealtimeNanos == 0) {
            return false;
        }
        return true;
    }

    @SystemApi
    public void makeComplete() {
        if (this.mProvider == null) {
            this.mProvider = "?";
        }
        if (!hasAccuracy()) {
            this.mFieldsMask |= 8;
            this.mHorizontalAccuracyMeters = 100.0f;
        }
        if (this.mTime == 0) {
            this.mTime = System.currentTimeMillis();
        }
        if (this.mElapsedRealtimeNanos == 0) {
            this.mElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        }
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public void setExtras(Bundle extras) {
        this.mExtras = extras == null ? null : new Bundle(extras);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Location[");
        s.append(this.mProvider);
        s.append(String.format(" %.6f,%.6f", new Object[]{Double.valueOf(this.mLatitude), Double.valueOf(this.mLongitude)}));
        if (hasAccuracy()) {
            s.append(String.format(" hAcc=%.0f", new Object[]{Float.valueOf(this.mHorizontalAccuracyMeters)}));
        } else {
            s.append(" hAcc=???");
        }
        if (this.mTime == 0) {
            s.append(" t=?!?");
        }
        if (this.mElapsedRealtimeNanos == 0) {
            s.append(" et=?!?");
        } else {
            s.append(" et=");
            TimeUtils.formatDuration(this.mElapsedRealtimeNanos / TimeUtils.NANOS_PER_MS, s);
        }
        if (hasElapsedRealtimeUncertaintyNanos()) {
            s.append(" etAcc=");
            TimeUtils.formatDuration((long) (this.mElapsedRealtimeUncertaintyNanos / 1000000.0d), s);
        }
        if (hasAltitude()) {
            s.append(" alt=");
            s.append(this.mAltitude);
        }
        if (hasSpeed()) {
            s.append(" vel=");
            s.append(this.mSpeed);
        }
        if (hasBearing()) {
            s.append(" bear=");
            s.append(this.mBearing);
        }
        if (hasVerticalAccuracy()) {
            s.append(String.format(" vAcc=%.0f", new Object[]{Float.valueOf(this.mVerticalAccuracyMeters)}));
        } else {
            s.append(" vAcc=???");
        }
        if (hasSpeedAccuracy()) {
            s.append(String.format(" sAcc=%.0f", new Object[]{Float.valueOf(this.mSpeedAccuracyMetersPerSecond)}));
        } else {
            s.append(" sAcc=???");
        }
        if (hasBearingAccuracy()) {
            s.append(String.format(" bAcc=%.0f", new Object[]{Float.valueOf(this.mBearingAccuracyDegrees)}));
        } else {
            s.append(" bAcc=???");
        }
        if (isFromMockProvider()) {
            s.append(" mock");
        }
        if (this.mExtras != null) {
            s.append(" {");
            s.append(this.mExtras);
            s.append('}');
        }
        s.append(']');
        return s.toString();
    }

    public void dump(Printer pw, String prefix) {
        pw.println(prefix + toString());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.mProvider);
        parcel.writeLong(this.mTime);
        parcel.writeLong(this.mElapsedRealtimeNanos);
        parcel.writeDouble(this.mElapsedRealtimeUncertaintyNanos);
        parcel.writeInt(this.mFieldsMask);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeDouble(this.mAltitude);
        parcel.writeFloat(this.mSpeed);
        parcel.writeFloat(this.mBearing);
        parcel.writeFloat(this.mHorizontalAccuracyMeters);
        parcel.writeFloat(this.mVerticalAccuracyMeters);
        parcel.writeFloat(this.mSpeedAccuracyMetersPerSecond);
        parcel.writeFloat(this.mBearingAccuracyDegrees);
        parcel.writeBundle(this.mExtras);
    }

    public Location getExtraLocation(String key) {
        if (this.mExtras == null) {
            return null;
        }
        Parcelable value = this.mExtras.getParcelable(key);
        if (value instanceof Location) {
            return (Location) value;
        }
        return null;
    }

    @UnsupportedAppUsage
    public void setExtraLocation(String key, Location value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelable(key, value);
    }

    public boolean isFromMockProvider() {
        return (this.mFieldsMask & 16) != 0;
    }

    @SystemApi
    public void setIsFromMockProvider(boolean isFromMockProvider) {
        if (isFromMockProvider) {
            this.mFieldsMask |= 16;
        } else {
            this.mFieldsMask &= -17;
        }
    }

    private static class BearingDistanceCache {
        /* access modifiers changed from: private */
        public float mDistance;
        /* access modifiers changed from: private */
        public float mFinalBearing;
        /* access modifiers changed from: private */
        public float mInitialBearing;
        /* access modifiers changed from: private */
        public double mLat1;
        /* access modifiers changed from: private */
        public double mLat2;
        /* access modifiers changed from: private */
        public double mLon1;
        /* access modifiers changed from: private */
        public double mLon2;

        private BearingDistanceCache() {
            this.mLat1 = 0.0d;
            this.mLon1 = 0.0d;
            this.mLat2 = 0.0d;
            this.mLon2 = 0.0d;
            this.mDistance = 0.0f;
            this.mInitialBearing = 0.0f;
            this.mFinalBearing = 0.0f;
        }
    }
}
