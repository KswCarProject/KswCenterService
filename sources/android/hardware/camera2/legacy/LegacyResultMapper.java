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

/* loaded from: classes.dex */
public class LegacyResultMapper {
    private static final boolean DEBUG = false;
    private static final String TAG = "LegacyResultMapper";
    private LegacyRequest mCachedRequest = null;
    private CameraMetadataNative mCachedResult = null;

    public CameraMetadataNative cachedConvertResultMetadata(LegacyRequest legacyRequest, long timestamp) {
        CameraMetadataNative result;
        if (this.mCachedRequest != null && legacyRequest.parameters.same(this.mCachedRequest.parameters) && legacyRequest.captureRequest.equals((Object) this.mCachedRequest.captureRequest)) {
            result = new CameraMetadataNative(this.mCachedResult);
        } else {
            result = convertResultMetadata(legacyRequest);
            this.mCachedRequest = legacyRequest;
            this.mCachedResult = new CameraMetadataNative(result);
        }
        result.set((CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP, (CaptureResult.Key<Long>) Long.valueOf(timestamp));
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
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.COLOR_CORRECTION_ABERRATION_MODE, (CaptureResult.Key<Integer>) ((Integer) request.get(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE)));
        mapAe(result, characteristics, request, activeArraySize, zoomData, params);
        mapAf(result, activeArraySize, zoomData, params);
        mapAwb(result, params);
        int i = 1;
        int captureIntent = ((Integer) ParamsUtils.getOrDefault(request, CaptureRequest.CONTROL_CAPTURE_INTENT, 1)).intValue();
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_CAPTURE_INTENT, (CaptureResult.Key<Integer>) Integer.valueOf(LegacyRequestMapper.filterSupportedCaptureIntent(captureIntent)));
        int controlMode = ((Integer) ParamsUtils.getOrDefault(request, CaptureRequest.CONTROL_MODE, 1)).intValue();
        if (controlMode != 2) {
            result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_MODE, (CaptureResult.Key<Integer>) 1);
        } else {
            result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_MODE, (CaptureResult.Key<Integer>) 2);
        }
        String legacySceneMode = params.getSceneMode();
        int mode = LegacyMetadataMapper.convertSceneModeFromLegacy(legacySceneMode);
        if (mode != -1) {
            result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_SCENE_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(mode));
        } else {
            Log.m64w(TAG, "Unknown scene mode " + legacySceneMode + " returned by camera HAL, setting to disabled.");
            result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_SCENE_MODE, (CaptureResult.Key<Integer>) 0);
        }
        String legacyEffectMode = params.getColorEffect();
        int mode2 = LegacyMetadataMapper.convertEffectModeFromLegacy(legacyEffectMode);
        if (mode2 != -1) {
            result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_EFFECT_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(mode2));
        } else {
            Log.m64w(TAG, "Unknown effect mode " + legacyEffectMode + " returned by camera HAL, setting to off.");
            result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_EFFECT_MODE, (CaptureResult.Key<Integer>) 0);
        }
        if (!params.isVideoStabilizationSupported() || !params.getVideoStabilization()) {
            i = 0;
        }
        int stabMode = i;
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_VIDEO_STABILIZATION_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(stabMode));
        if (Camera.Parameters.FOCUS_MODE_INFINITY.equals(params.getFocusMode())) {
            result.set((CaptureResult.Key<CaptureResult.Key<Float>>) CaptureResult.LENS_FOCUS_DISTANCE, (CaptureResult.Key<Float>) Float.valueOf(0.0f));
        }
        result.set((CaptureResult.Key<CaptureResult.Key<Float>>) CaptureResult.LENS_FOCAL_LENGTH, (CaptureResult.Key<Float>) Float.valueOf(params.getFocalLength()));
        result.set((CaptureResult.Key<CaptureResult.Key<Byte>>) CaptureResult.REQUEST_PIPELINE_DEPTH, (CaptureResult.Key<Byte>) ((Byte) characteristics.get(CameraCharacteristics.REQUEST_PIPELINE_MAX_DEPTH)));
        mapScaler(result, zoomData, params);
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.SENSOR_TEST_PATTERN_MODE, (CaptureResult.Key<Integer>) 0);
        result.set((CaptureResult.Key<CaptureResult.Key<Location>>) CaptureResult.JPEG_GPS_LOCATION, (CaptureResult.Key<Location>) ((Location) request.get(CaptureRequest.JPEG_GPS_LOCATION)));
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.JPEG_ORIENTATION, (CaptureResult.Key<Integer>) ((Integer) request.get(CaptureRequest.JPEG_ORIENTATION)));
        result.set((CaptureResult.Key<CaptureResult.Key<Byte>>) CaptureResult.JPEG_QUALITY, (CaptureResult.Key<Byte>) Byte.valueOf((byte) params.getJpegQuality()));
        result.set((CaptureResult.Key<CaptureResult.Key<Byte>>) CaptureResult.JPEG_THUMBNAIL_QUALITY, (CaptureResult.Key<Byte>) Byte.valueOf((byte) params.getJpegThumbnailQuality()));
        Camera.Size s = params.getJpegThumbnailSize();
        if (s != null) {
            result.set((CaptureResult.Key<CaptureResult.Key<Size>>) CaptureResult.JPEG_THUMBNAIL_SIZE, (CaptureResult.Key<Size>) ParameterUtils.convertSize(s));
        } else {
            Log.m64w(TAG, "Null thumbnail size received from parameters.");
        }
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.NOISE_REDUCTION_MODE, (CaptureResult.Key<Integer>) ((Integer) request.get(CaptureRequest.NOISE_REDUCTION_MODE)));
        return result;
    }

    private static void mapAe(CameraMetadataNative m, CameraCharacteristics characteristics, CaptureRequest request, Rect activeArray, ParameterUtils.ZoomData zoomData, Camera.Parameters p) {
        int antiBandingMode = LegacyMetadataMapper.convertAntiBandingModeOrDefault(p.getAntibanding());
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_AE_ANTIBANDING_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(antiBandingMode));
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION, (CaptureResult.Key<Integer>) Integer.valueOf(p.getExposureCompensation()));
        boolean lock = p.isAutoExposureLockSupported() ? p.getAutoExposureLock() : false;
        m.set((CaptureResult.Key<CaptureResult.Key<Boolean>>) CaptureResult.CONTROL_AE_LOCK, (CaptureResult.Key<Boolean>) Boolean.valueOf(lock));
        Boolean requestLock = (Boolean) request.get(CaptureRequest.CONTROL_AE_LOCK);
        if (requestLock != null && requestLock.booleanValue() != lock) {
            Log.m64w(TAG, "mapAe - android.control.aeLock was requested to " + requestLock + " but resulted in " + lock);
        }
        mapAeAndFlashMode(m, characteristics, p);
        if (p.getMaxNumMeteringAreas() > 0) {
            MeteringRectangle[] meteringRectArray = getMeteringRectangles(activeArray, zoomData, p.getMeteringAreas(), "AE");
            m.set((CaptureResult.Key<CaptureResult.Key<MeteringRectangle[]>>) CaptureResult.CONTROL_AE_REGIONS, (CaptureResult.Key<MeteringRectangle[]>) meteringRectArray);
        }
    }

    private static void mapAf(CameraMetadataNative m, Rect activeArray, ParameterUtils.ZoomData zoomData, Camera.Parameters p) {
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_AF_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(convertLegacyAfMode(p.getFocusMode())));
        if (p.getMaxNumFocusAreas() > 0) {
            MeteringRectangle[] meteringRectArray = getMeteringRectangles(activeArray, zoomData, p.getFocusAreas(), "AF");
            m.set((CaptureResult.Key<CaptureResult.Key<MeteringRectangle[]>>) CaptureResult.CONTROL_AF_REGIONS, (CaptureResult.Key<MeteringRectangle[]>) meteringRectArray);
        }
    }

    private static void mapAwb(CameraMetadataNative m, Camera.Parameters p) {
        boolean lock = p.isAutoWhiteBalanceLockSupported() ? p.getAutoWhiteBalanceLock() : false;
        m.set((CaptureResult.Key<CaptureResult.Key<Boolean>>) CaptureResult.CONTROL_AWB_LOCK, (CaptureResult.Key<Boolean>) Boolean.valueOf(lock));
        int awbMode = convertLegacyAwbMode(p.getWhiteBalance());
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_AWB_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(awbMode));
    }

    private static MeteringRectangle[] getMeteringRectangles(Rect activeArray, ParameterUtils.ZoomData zoomData, List<Camera.Area> meteringAreaList, String regionName) {
        List<MeteringRectangle> meteringRectList = new ArrayList<>();
        if (meteringAreaList != null) {
            for (Camera.Area area : meteringAreaList) {
                ParameterUtils.WeightedRectangle rect = ParameterUtils.convertCameraAreaToActiveArrayRectangle(activeArray, zoomData, area);
                meteringRectList.add(rect.toMetering());
            }
        }
        return (MeteringRectangle[]) meteringRectList.toArray(new MeteringRectangle[0]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0062, code lost:
        if (r4.equals("off") != false) goto L19;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void mapAeAndFlashMode(CameraMetadataNative m, CameraCharacteristics characteristics, Camera.Parameters p) {
        int flashMode = 0;
        char c = 0;
        Integer flashState = ((Boolean) characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)).booleanValue() ? null : 0;
        int aeMode = 1;
        String flashModeSetting = p.getFlashMode();
        if (flashModeSetting != null) {
            int hashCode = flashModeSetting.hashCode();
            if (hashCode == 3551) {
                if (flashModeSetting.equals("on")) {
                    c = 2;
                    switch (c) {
                    }
                }
                c = '\uffff';
                switch (c) {
                }
            } else if (hashCode != 109935) {
                if (hashCode == 3005871) {
                    if (flashModeSetting.equals("auto")) {
                        c = 1;
                        switch (c) {
                        }
                    }
                    c = '\uffff';
                    switch (c) {
                    }
                } else if (hashCode != 110547964) {
                    if (hashCode == 1081542389 && flashModeSetting.equals(Camera.Parameters.FLASH_MODE_RED_EYE)) {
                        c = 3;
                        switch (c) {
                            case 0:
                                break;
                            case 1:
                                aeMode = 2;
                                break;
                            case 2:
                                flashMode = 1;
                                aeMode = 3;
                                flashState = 3;
                                break;
                            case 3:
                                aeMode = 4;
                                break;
                            case 4:
                                flashMode = 2;
                                flashState = 3;
                                break;
                            default:
                                Log.m64w(TAG, "mapAeAndFlashMode - Ignoring unknown flash mode " + p.getFlashMode());
                                break;
                        }
                    }
                    c = '\uffff';
                    switch (c) {
                    }
                } else {
                    if (flashModeSetting.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                        c = 4;
                        switch (c) {
                        }
                    }
                    c = '\uffff';
                    switch (c) {
                    }
                }
            }
        }
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.FLASH_STATE, (CaptureResult.Key<Integer>) flashState);
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.FLASH_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(flashMode));
        m.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_AE_MODE, (CaptureResult.Key<Integer>) Integer.valueOf(aeMode));
    }

    private static int convertLegacyAfMode(String mode) {
        if (mode == null) {
            Log.m64w(TAG, "convertLegacyAfMode - no AF mode, default to OFF");
            return 0;
        }
        char c = '\uffff';
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
                Log.m64w(TAG, "convertLegacyAfMode - unknown mode " + mode + " , ignoring");
                return 0;
        }
    }

    private static int convertLegacyAwbMode(String mode) {
        if (mode == null) {
            return 1;
        }
        char c = '\uffff';
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
                Log.m64w(TAG, "convertAwbMode - unrecognized WB mode " + mode);
                return 1;
        }
    }

    private static void mapScaler(CameraMetadataNative m, ParameterUtils.ZoomData zoomData, Camera.Parameters p) {
        m.set((CaptureResult.Key<CaptureResult.Key<Rect>>) CaptureResult.SCALER_CROP_REGION, (CaptureResult.Key<Rect>) zoomData.reportedCrop);
    }
}
