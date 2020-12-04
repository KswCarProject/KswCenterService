package android.hardware.camera2.legacy;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.ParameterUtils;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.utils.ParamsUtils;
import android.location.Location;
import android.util.Log;
import android.util.Size;
import java.util.ArrayList;
import java.util.List;

public class LegacyResultMapper {
    private static final boolean DEBUG = false;
    private static final String TAG = "LegacyResultMapper";
    private LegacyRequest mCachedRequest = null;
    private CameraMetadataNative mCachedResult = null;

    public CameraMetadataNative cachedConvertResultMetadata(LegacyRequest legacyRequest, long timestamp) {
        CameraMetadataNative result;
        if (this.mCachedRequest == null || !legacyRequest.parameters.same(this.mCachedRequest.parameters) || !legacyRequest.captureRequest.equals((Object) this.mCachedRequest.captureRequest)) {
            result = convertResultMetadata(legacyRequest);
            this.mCachedRequest = legacyRequest;
            this.mCachedResult = new CameraMetadataNative(result);
        } else {
            result = new CameraMetadataNative(this.mCachedResult);
        }
        result.set(CaptureResult.SENSOR_TIMESTAMP, Long.valueOf(timestamp));
        return result;
    }

    private static CameraMetadataNative convertResultMetadata(LegacyRequest legacyRequest) {
        CameraCharacteristics characteristics = legacyRequest.characteristics;
        CaptureRequest request = legacyRequest.captureRequest;
        Size previewSize = legacyRequest.previewSize;
        Camera.Parameters params = legacyRequest.parameters;
        CameraMetadataNative result = new CameraMetadataNative();
        Rect activeArraySize = (Rect) characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        ParameterUtils.ZoomData zoomData = ParameterUtils.convertScalerCropRegion(activeArraySize, (Rect) request.get(CaptureRequest.SCALER_CROP_REGION), previewSize, params);
        result.set(CaptureResult.COLOR_CORRECTION_ABERRATION_MODE, (Integer) request.get(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE));
        mapAe(result, characteristics, request, activeArraySize, zoomData, params);
        mapAf(result, activeArraySize, zoomData, params);
        mapAwb(result, params);
        int stabMode = 1;
        result.set(CaptureResult.CONTROL_CAPTURE_INTENT, Integer.valueOf(LegacyRequestMapper.filterSupportedCaptureIntent(((Integer) ParamsUtils.getOrDefault(request, CaptureRequest.CONTROL_CAPTURE_INTENT, 1)).intValue())));
        if (((Integer) ParamsUtils.getOrDefault(request, CaptureRequest.CONTROL_MODE, 1)).intValue() == 2) {
            result.set(CaptureResult.CONTROL_MODE, 2);
        } else {
            result.set(CaptureResult.CONTROL_MODE, 1);
        }
        String legacySceneMode = params.getSceneMode();
        int mode = LegacyMetadataMapper.convertSceneModeFromLegacy(legacySceneMode);
        if (mode != -1) {
            result.set(CaptureResult.CONTROL_SCENE_MODE, Integer.valueOf(mode));
        } else {
            Log.w(TAG, "Unknown scene mode " + legacySceneMode + " returned by camera HAL, setting to disabled.");
            result.set(CaptureResult.CONTROL_SCENE_MODE, 0);
        }
        String legacyEffectMode = params.getColorEffect();
        int mode2 = LegacyMetadataMapper.convertEffectModeFromLegacy(legacyEffectMode);
        if (mode2 != -1) {
            result.set(CaptureResult.CONTROL_EFFECT_MODE, Integer.valueOf(mode2));
        } else {
            Log.w(TAG, "Unknown effect mode " + legacyEffectMode + " returned by camera HAL, setting to off.");
            result.set(CaptureResult.CONTROL_EFFECT_MODE, 0);
        }
        if (!params.isVideoStabilizationSupported() || !params.getVideoStabilization()) {
            stabMode = 0;
        }
        result.set(CaptureResult.CONTROL_VIDEO_STABILIZATION_MODE, Integer.valueOf(stabMode));
        if (Camera.Parameters.FOCUS_MODE_INFINITY.equals(params.getFocusMode())) {
            result.set(CaptureResult.LENS_FOCUS_DISTANCE, Float.valueOf(0.0f));
        }
        result.set(CaptureResult.LENS_FOCAL_LENGTH, Float.valueOf(params.getFocalLength()));
        result.set(CaptureResult.REQUEST_PIPELINE_DEPTH, (Byte) characteristics.get(CameraCharacteristics.REQUEST_PIPELINE_MAX_DEPTH));
        mapScaler(result, zoomData, params);
        result.set(CaptureResult.SENSOR_TEST_PATTERN_MODE, 0);
        result.set(CaptureResult.JPEG_GPS_LOCATION, (Location) request.get(CaptureRequest.JPEG_GPS_LOCATION));
        result.set(CaptureResult.JPEG_ORIENTATION, (Integer) request.get(CaptureRequest.JPEG_ORIENTATION));
        result.set(CaptureResult.JPEG_QUALITY, Byte.valueOf((byte) params.getJpegQuality()));
        result.set(CaptureResult.JPEG_THUMBNAIL_QUALITY, Byte.valueOf((byte) params.getJpegThumbnailQuality()));
        Camera.Size s = params.getJpegThumbnailSize();
        if (s != null) {
            result.set(CaptureResult.JPEG_THUMBNAIL_SIZE, ParameterUtils.convertSize(s));
        } else {
            Log.w(TAG, "Null thumbnail size received from parameters.");
        }
        result.set(CaptureResult.NOISE_REDUCTION_MODE, (Integer) request.get(CaptureRequest.NOISE_REDUCTION_MODE));
        return result;
    }

    private static void mapAe(CameraMetadataNative m, CameraCharacteristics characteristics, CaptureRequest request, Rect activeArray, ParameterUtils.ZoomData zoomData, Camera.Parameters p) {
        m.set(CaptureResult.CONTROL_AE_ANTIBANDING_MODE, Integer.valueOf(LegacyMetadataMapper.convertAntiBandingModeOrDefault(p.getAntibanding())));
        m.set(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(p.getExposureCompensation()));
        boolean lock = p.isAutoExposureLockSupported() ? p.getAutoExposureLock() : false;
        m.set(CaptureResult.CONTROL_AE_LOCK, Boolean.valueOf(lock));
        Boolean requestLock = (Boolean) request.get(CaptureRequest.CONTROL_AE_LOCK);
        if (!(requestLock == null || requestLock.booleanValue() == lock)) {
            Log.w(TAG, "mapAe - android.control.aeLock was requested to " + requestLock + " but resulted in " + lock);
        }
        mapAeAndFlashMode(m, characteristics, p);
        if (p.getMaxNumMeteringAreas() > 0) {
            m.set(CaptureResult.CONTROL_AE_REGIONS, getMeteringRectangles(activeArray, zoomData, p.getMeteringAreas(), "AE"));
        }
    }

    private static void mapAf(CameraMetadataNative m, Rect activeArray, ParameterUtils.ZoomData zoomData, Camera.Parameters p) {
        m.set(CaptureResult.CONTROL_AF_MODE, Integer.valueOf(convertLegacyAfMode(p.getFocusMode())));
        if (p.getMaxNumFocusAreas() > 0) {
            m.set(CaptureResult.CONTROL_AF_REGIONS, getMeteringRectangles(activeArray, zoomData, p.getFocusAreas(), "AF"));
        }
    }

    private static void mapAwb(CameraMetadataNative m, Camera.Parameters p) {
        m.set(CaptureResult.CONTROL_AWB_LOCK, Boolean.valueOf(p.isAutoWhiteBalanceLockSupported() ? p.getAutoWhiteBalanceLock() : false));
        m.set(CaptureResult.CONTROL_AWB_MODE, Integer.valueOf(convertLegacyAwbMode(p.getWhiteBalance())));
    }

    private static MeteringRectangle[] getMeteringRectangles(Rect activeArray, ParameterUtils.ZoomData zoomData, List<Camera.Area> meteringAreaList, String regionName) {
        List<MeteringRectangle> meteringRectList = new ArrayList<>();
        if (meteringAreaList != null) {
            for (Camera.Area area : meteringAreaList) {
                meteringRectList.add(ParameterUtils.convertCameraAreaToActiveArrayRectangle(activeArray, zoomData, area).toMetering());
            }
        }
        return (MeteringRectangle[]) meteringRectList.toArray(new MeteringRectangle[0]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0062, code lost:
        if (r4.equals("off") != false) goto L_0x0071;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void mapAeAndFlashMode(android.hardware.camera2.impl.CameraMetadataNative r9, android.hardware.camera2.CameraCharacteristics r10, android.hardware.Camera.Parameters r11) {
        /*
            r0 = 0
            android.hardware.camera2.CameraCharacteristics$Key<java.lang.Boolean> r1 = android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE
            java.lang.Object r1 = r10.get(r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            r2 = 0
            if (r1 == 0) goto L_0x0012
            r1 = 0
            goto L_0x0016
        L_0x0012:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
        L_0x0016:
            r3 = 1
            java.lang.String r4 = r11.getFlashMode()
            if (r4 == 0) goto L_0x00a1
            r5 = -1
            int r6 = r4.hashCode()
            r7 = 3551(0xddf, float:4.976E-42)
            r8 = 3
            if (r6 == r7) goto L_0x0065
            r7 = 109935(0x1ad6f, float:1.54052E-40)
            if (r6 == r7) goto L_0x005c
            r2 = 3005871(0x2dddaf, float:4.212122E-39)
            if (r6 == r2) goto L_0x0052
            r2 = 110547964(0x696d3fc, float:5.673521E-35)
            if (r6 == r2) goto L_0x0047
            r2 = 1081542389(0x407706f5, float:3.8597996)
            if (r6 == r2) goto L_0x003c
            goto L_0x0070
        L_0x003c:
            java.lang.String r2 = "red-eye"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = r8
            goto L_0x0071
        L_0x0047:
            java.lang.String r2 = "torch"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 4
            goto L_0x0071
        L_0x0052:
            java.lang.String r2 = "auto"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 1
            goto L_0x0071
        L_0x005c:
            java.lang.String r6 = "off"
            boolean r6 = r4.equals(r6)
            if (r6 == 0) goto L_0x0070
            goto L_0x0071
        L_0x0065:
            java.lang.String r2 = "on"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 2
            goto L_0x0071
        L_0x0070:
            r2 = r5
        L_0x0071:
            switch(r2) {
                case 0: goto L_0x00a0;
                case 1: goto L_0x009e;
                case 2: goto L_0x0097;
                case 3: goto L_0x0095;
                case 4: goto L_0x008f;
                default: goto L_0x0074;
            }
        L_0x0074:
            java.lang.String r2 = "LegacyResultMapper"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "mapAeAndFlashMode - Ignoring unknown flash mode "
            r5.append(r6)
            java.lang.String r6 = r11.getFlashMode()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            android.util.Log.w((java.lang.String) r2, (java.lang.String) r5)
            goto L_0x00a1
        L_0x008f:
            r0 = 2
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
            goto L_0x00a1
        L_0x0095:
            r3 = 4
            goto L_0x00a1
        L_0x0097:
            r0 = 1
            r3 = 3
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
            goto L_0x00a1
        L_0x009e:
            r3 = 2
            goto L_0x00a1
        L_0x00a0:
        L_0x00a1:
            android.hardware.camera2.CaptureResult$Key<java.lang.Integer> r2 = android.hardware.camera2.CaptureResult.FLASH_STATE
            r9.set(r2, r1)
            android.hardware.camera2.CaptureResult$Key<java.lang.Integer> r2 = android.hardware.camera2.CaptureResult.FLASH_MODE
            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)
            r9.set(r2, r5)
            android.hardware.camera2.CaptureResult$Key<java.lang.Integer> r2 = android.hardware.camera2.CaptureResult.CONTROL_AE_MODE
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            r9.set(r2, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.legacy.LegacyResultMapper.mapAeAndFlashMode(android.hardware.camera2.impl.CameraMetadataNative, android.hardware.camera2.CameraCharacteristics, android.hardware.Camera$Parameters):void");
    }

    private static int convertLegacyAfMode(String mode) {
        if (mode == null) {
            Log.w(TAG, "convertLegacyAfMode - no AF mode, default to OFF");
            return 0;
        }
        char c = 65535;
        switch (mode.hashCode()) {
            case -194628547:
                if (mode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                    c = 2;
                    break;
                }
                break;
            case 3005871:
                if (mode.equals("auto")) {
                    c = 0;
                    break;
                }
                break;
            case 3108534:
                if (mode.equals(Camera.Parameters.FOCUS_MODE_EDOF)) {
                    c = 3;
                    break;
                }
                break;
            case 97445748:
                if (mode.equals(Camera.Parameters.FOCUS_MODE_FIXED)) {
                    c = 5;
                    break;
                }
                break;
            case 103652300:
                if (mode.equals(Camera.Parameters.FOCUS_MODE_MACRO)) {
                    c = 4;
                    break;
                }
                break;
            case 173173288:
                if (mode.equals(Camera.Parameters.FOCUS_MODE_INFINITY)) {
                    c = 6;
                    break;
                }
                break;
            case 910005312:
                if (mode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 2;
            case 5:
                return 0;
            case 6:
                return 0;
            default:
                Log.w(TAG, "convertLegacyAfMode - unknown mode " + mode + " , ignoring");
                return 0;
        }
    }

    private static int convertLegacyAwbMode(String mode) {
        if (mode == null) {
            return 1;
        }
        char c = 65535;
        switch (mode.hashCode()) {
            case -939299377:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_INCANDESCENT)) {
                    c = 1;
                    break;
                }
                break;
            case -719316704:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_WARM_FLUORESCENT)) {
                    c = 3;
                    break;
                }
                break;
            case 3005871:
                if (mode.equals("auto")) {
                    c = 0;
                    break;
                }
                break;
            case 109399597:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_SHADE)) {
                    c = 7;
                    break;
                }
                break;
            case 474934723:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_CLOUDY_DAYLIGHT)) {
                    c = 5;
                    break;
                }
                break;
            case 1650323088:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_TWILIGHT)) {
                    c = 6;
                    break;
                }
                break;
            case 1902580840:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_FLUORESCENT)) {
                    c = 2;
                    break;
                }
                break;
            case 1942983418:
                if (mode.equals(Camera.Parameters.WHITE_BALANCE_DAYLIGHT)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            default:
                Log.w(TAG, "convertAwbMode - unrecognized WB mode " + mode);
                return 1;
        }
    }

    private static void mapScaler(CameraMetadataNative m, ParameterUtils.ZoomData zoomData, Camera.Parameters p) {
        m.set(CaptureResult.SCALER_CROP_REGION, zoomData.reportedCrop);
    }
}
