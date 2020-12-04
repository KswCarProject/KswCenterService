package android.hardware.camera2.impl;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.marshal.impl.MarshalQueryableArray;
import android.hardware.camera2.marshal.impl.MarshalQueryableBlackLevelPattern;
import android.hardware.camera2.marshal.impl.MarshalQueryableBoolean;
import android.hardware.camera2.marshal.impl.MarshalQueryableColorSpaceTransform;
import android.hardware.camera2.marshal.impl.MarshalQueryableEnum;
import android.hardware.camera2.marshal.impl.MarshalQueryableHighSpeedVideoConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableMeteringRectangle;
import android.hardware.camera2.marshal.impl.MarshalQueryableNativeByteToInteger;
import android.hardware.camera2.marshal.impl.MarshalQueryablePair;
import android.hardware.camera2.marshal.impl.MarshalQueryableParcelable;
import android.hardware.camera2.marshal.impl.MarshalQueryablePrimitive;
import android.hardware.camera2.marshal.impl.MarshalQueryableRange;
import android.hardware.camera2.marshal.impl.MarshalQueryableRecommendedStreamConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableRect;
import android.hardware.camera2.marshal.impl.MarshalQueryableReprocessFormatsMap;
import android.hardware.camera2.marshal.impl.MarshalQueryableRggbChannelVector;
import android.hardware.camera2.marshal.impl.MarshalQueryableSize;
import android.hardware.camera2.marshal.impl.MarshalQueryableSizeF;
import android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfigurationDuration;
import android.hardware.camera2.marshal.impl.MarshalQueryableString;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.LensShadingMap;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.hardware.camera2.params.OisSample;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.params.RecommendedStreamConfigurationMap;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.params.TonemapCurve;
import android.hardware.camera2.utils.TypeReference;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CameraMetadataNative implements Parcelable {
    private static final String CELLID_PROCESS = "CELLID";
    public static final Parcelable.Creator<CameraMetadataNative> CREATOR = new Parcelable.Creator<CameraMetadataNative>() {
        public CameraMetadataNative createFromParcel(Parcel in) {
            CameraMetadataNative metadata = new CameraMetadataNative();
            metadata.readFromParcel(in);
            return metadata;
        }

        public CameraMetadataNative[] newArray(int size) {
            return new CameraMetadataNative[size];
        }
    };
    private static final boolean DEBUG = false;
    private static final int FACE_LANDMARK_SIZE = 6;
    private static final String GPS_PROCESS = "GPS";
    public static final int NATIVE_JPEG_FORMAT = 33;
    public static final int NUM_TYPES = 6;
    private static final String TAG = "CameraMetadataJV";
    public static final int TYPE_BYTE = 0;
    public static final int TYPE_DOUBLE = 4;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_INT32 = 1;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_RATIONAL = 5;
    private static final HashMap<Key<?>, GetCommand> sGetCommandMap = new HashMap<>();
    private static final HashMap<Key<?>, SetCommand> sSetCommandMap = new HashMap<>();
    private int mCameraId;
    private Size mDisplaySize;
    @UnsupportedAppUsage
    private long mMetadataPtr;

    private native long nativeAllocate();

    private native long nativeAllocateCopy(CameraMetadataNative cameraMetadataNative) throws NullPointerException;

    private native synchronized void nativeClose();

    private native synchronized void nativeDump() throws IOException;

    private native synchronized ArrayList nativeGetAllVendorKeys(Class cls);

    private native synchronized int nativeGetEntryCount();

    private static native int nativeGetTagFromKey(String str, long j) throws IllegalArgumentException;

    @UnsupportedAppUsage
    private native synchronized int nativeGetTagFromKeyLocal(String str) throws IllegalArgumentException;

    private static native int nativeGetTypeFromTag(int i, long j) throws IllegalArgumentException;

    @UnsupportedAppUsage
    private native synchronized int nativeGetTypeFromTagLocal(int i) throws IllegalArgumentException;

    private native synchronized boolean nativeIsEmpty();

    private native synchronized void nativeReadFromParcel(Parcel parcel);

    @UnsupportedAppUsage
    private native synchronized byte[] nativeReadValues(int i);

    private static native int nativeSetupGlobalVendorTagDescriptor();

    private native synchronized void nativeSwap(CameraMetadataNative cameraMetadataNative) throws NullPointerException;

    private native synchronized void nativeWriteToParcel(Parcel parcel);

    private native synchronized void nativeWriteValues(int i, byte[] bArr);

    public static class Key<T> {
        /* access modifiers changed from: private */
        public final String mFallbackName;
        private boolean mHasTag;
        private final int mHash;
        private final String mName;
        private int mTag;
        private final Class<T> mType;
        private final TypeReference<T> mTypeReference;
        private long mVendorId = Long.MAX_VALUE;

        public Key(String name, Class<T> type, long vendorId) {
            if (name == null) {
                throw new NullPointerException("Key needs a valid name");
            } else if (type != null) {
                this.mName = name;
                this.mFallbackName = null;
                this.mType = type;
                this.mVendorId = vendorId;
                this.mTypeReference = TypeReference.createSpecializedTypeReference(type);
                this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
            } else {
                throw new NullPointerException("Type needs to be non-null");
            }
        }

        public Key(String name, String fallbackName, Class<T> type) {
            if (name == null) {
                throw new NullPointerException("Key needs a valid name");
            } else if (type != null) {
                this.mName = name;
                this.mFallbackName = fallbackName;
                this.mType = type;
                this.mTypeReference = TypeReference.createSpecializedTypeReference(type);
                this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
            } else {
                throw new NullPointerException("Type needs to be non-null");
            }
        }

        public Key(String name, Class<T> type) {
            if (name == null) {
                throw new NullPointerException("Key needs a valid name");
            } else if (type != null) {
                this.mName = name;
                this.mFallbackName = null;
                this.mType = type;
                this.mTypeReference = TypeReference.createSpecializedTypeReference(type);
                this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
            } else {
                throw new NullPointerException("Type needs to be non-null");
            }
        }

        public Key(String name, TypeReference<T> typeReference) {
            if (name == null) {
                throw new NullPointerException("Key needs a valid name");
            } else if (typeReference != null) {
                this.mName = name;
                this.mFallbackName = null;
                this.mType = typeReference.getRawType();
                this.mTypeReference = typeReference;
                this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
            } else {
                throw new NullPointerException("TypeReference needs to be non-null");
            }
        }

        public final String getName() {
            return this.mName;
        }

        public final int hashCode() {
            return this.mHash;
        }

        public final boolean equals(Object o) {
            Key<?> lhs;
            if (this == o) {
                return true;
            }
            if (o == null || hashCode() != o.hashCode()) {
                return false;
            }
            if (o instanceof CaptureResult.Key) {
                lhs = ((CaptureResult.Key) o).getNativeKey();
            } else if (o instanceof CaptureRequest.Key) {
                lhs = ((CaptureRequest.Key) o).getNativeKey();
            } else if (o instanceof CameraCharacteristics.Key) {
                lhs = ((CameraCharacteristics.Key) o).getNativeKey();
            } else if (!(o instanceof Key)) {
                return false;
            } else {
                lhs = (Key) o;
            }
            if (!this.mName.equals(lhs.mName) || !this.mTypeReference.equals(lhs.mTypeReference)) {
                return false;
            }
            return true;
        }

        @UnsupportedAppUsage
        public final int getTag() {
            if (!this.mHasTag) {
                this.mTag = CameraMetadataNative.getTag(this.mName, this.mVendorId);
                this.mHasTag = true;
            }
            return this.mTag;
        }

        public final Class<T> getType() {
            return this.mType;
        }

        public final long getVendorId() {
            return this.mVendorId;
        }

        public final TypeReference<T> getTypeReference() {
            return this.mTypeReference;
        }
    }

    private static String translateLocationProviderToProcess(String provider) {
        if (provider == null) {
            return null;
        }
        char c = 65535;
        int hashCode = provider.hashCode();
        if (hashCode != 102570) {
            if (hashCode == 1843485230 && provider.equals(LocationManager.NETWORK_PROVIDER)) {
                c = 1;
            }
        } else if (provider.equals(LocationManager.GPS_PROVIDER)) {
            c = 0;
        }
        switch (c) {
            case 0:
                return GPS_PROCESS;
            case 1:
                return CELLID_PROCESS;
            default:
                return null;
        }
    }

    private static String translateProcessToLocationProvider(String process) {
        if (process == null) {
            return null;
        }
        char c = 65535;
        int hashCode = process.hashCode();
        if (hashCode != 70794) {
            if (hashCode == 1984215549 && process.equals(CELLID_PROCESS)) {
                c = 1;
            }
        } else if (process.equals(GPS_PROCESS)) {
            c = 0;
        }
        switch (c) {
            case 0:
                return LocationManager.GPS_PROVIDER;
            case 1:
                return LocationManager.NETWORK_PROVIDER;
            default:
                return null;
        }
    }

    public CameraMetadataNative() {
        this.mCameraId = -1;
        this.mDisplaySize = new Size(0, 0);
        this.mMetadataPtr = nativeAllocate();
        if (this.mMetadataPtr == 0) {
            throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
    }

    public CameraMetadataNative(CameraMetadataNative other) {
        this.mCameraId = -1;
        this.mDisplaySize = new Size(0, 0);
        this.mMetadataPtr = nativeAllocateCopy(other);
        if (this.mMetadataPtr == 0) {
            throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
    }

    public static CameraMetadataNative move(CameraMetadataNative other) {
        CameraMetadataNative newObject = new CameraMetadataNative();
        newObject.swap(other);
        return newObject;
    }

    static {
        sGetCommandMap.put(CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getAvailableFormats();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_FACES.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getFaces();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getFaceRectangles();
            }
        });
        sGetCommandMap.put(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getStreamConfigurationMap();
            }
        });
        sGetCommandMap.put(CameraCharacteristics.SCALER_MANDATORY_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMandatoryStreamCombinations();
            }
        });
        sGetCommandMap.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AE.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AF.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(CaptureRequest.TONEMAP_CURVE.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getTonemapCurve();
            }
        });
        sGetCommandMap.put(CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getGpsLocation();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_LENS_SHADING_CORRECTION_MAP.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getLensShadingMap();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_OIS_SAMPLES.getNativeKey(), new GetCommand() {
            public <T> T getValue(CameraMetadataNative metadata, Key<T> key) {
                return metadata.getOisSamples();
            }
        });
        sSetCommandMap.put(CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new SetCommand() {
            public <T> void setValue(CameraMetadataNative metadata, T value) {
                boolean unused = metadata.setAvailableFormats((int[]) value);
            }
        });
        sSetCommandMap.put(CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new SetCommand() {
            public <T> void setValue(CameraMetadataNative metadata, T value) {
                boolean unused = metadata.setFaceRectangles((Rect[]) value);
            }
        });
        sSetCommandMap.put(CaptureResult.STATISTICS_FACES.getNativeKey(), new SetCommand() {
            public <T> void setValue(CameraMetadataNative metadata, T value) {
                boolean unused = metadata.setFaces((Face[]) value);
            }
        });
        sSetCommandMap.put(CaptureRequest.TONEMAP_CURVE.getNativeKey(), new SetCommand() {
            public <T> void setValue(CameraMetadataNative metadata, T value) {
                boolean unused = metadata.setTonemapCurve((TonemapCurve) value);
            }
        });
        sSetCommandMap.put(CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new SetCommand() {
            public <T> void setValue(CameraMetadataNative metadata, T value) {
                boolean unused = metadata.setGpsLocation((Location) value);
            }
        });
        registerAllMarshalers();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        nativeWriteToParcel(dest);
    }

    public <T> T get(CameraCharacteristics.Key<T> key) {
        return get(key.getNativeKey());
    }

    public <T> T get(CaptureResult.Key<T> key) {
        return get(key.getNativeKey());
    }

    public <T> T get(CaptureRequest.Key<T> key) {
        return get(key.getNativeKey());
    }

    public <T> T get(Key<T> key) {
        Preconditions.checkNotNull(key, "key must not be null");
        GetCommand g = sGetCommandMap.get(key);
        if (g != null) {
            return g.getValue(this, key);
        }
        return getBase(key);
    }

    public void readFromParcel(Parcel in) {
        nativeReadFromParcel(in);
    }

    public static void setupGlobalVendorTagDescriptor() throws ServiceSpecificException {
        int err = nativeSetupGlobalVendorTagDescriptor();
        if (err != 0) {
            throw new ServiceSpecificException(err, "Failure to set up global vendor tags");
        }
    }

    public <T> void set(Key<T> key, T value) {
        SetCommand s = sSetCommandMap.get(key);
        if (s != null) {
            s.setValue(this, value);
        } else {
            setBase(key, value);
        }
    }

    public <T> void set(CaptureRequest.Key<T> key, T value) {
        set(key.getNativeKey(), value);
    }

    public <T> void set(CaptureResult.Key<T> key, T value) {
        set(key.getNativeKey(), value);
    }

    public <T> void set(CameraCharacteristics.Key<T> key, T value) {
        set(key.getNativeKey(), value);
    }

    private void close() {
        nativeClose();
        this.mMetadataPtr = 0;
    }

    private <T> T getBase(CameraCharacteristics.Key<T> key) {
        return getBase(key.getNativeKey());
    }

    private <T> T getBase(CaptureResult.Key<T> key) {
        return getBase(key.getNativeKey());
    }

    private <T> T getBase(CaptureRequest.Key<T> key) {
        return getBase(key.getNativeKey());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0016, code lost:
        r0 = nativeGetTagFromKeyLocal(android.hardware.camera2.impl.CameraMetadataNative.Key.access$000(r7));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T getBase(android.hardware.camera2.impl.CameraMetadataNative.Key<T> r7) {
        /*
            r6 = this;
            java.lang.String r0 = r7.getName()
            int r0 = r6.nativeGetTagFromKeyLocal(r0)
            byte[] r1 = r6.readValues(r0)
            if (r1 != 0) goto L_0x0025
            java.lang.String r2 = r7.mFallbackName
            r3 = 0
            if (r2 != 0) goto L_0x0016
            return r3
        L_0x0016:
            java.lang.String r2 = r7.mFallbackName
            int r0 = r6.nativeGetTagFromKeyLocal(r2)
            byte[] r1 = r6.readValues(r0)
            if (r1 != 0) goto L_0x0025
            return r3
        L_0x0025:
            int r2 = r6.nativeGetTypeFromTagLocal(r0)
            android.hardware.camera2.marshal.Marshaler r3 = getMarshalerForKey(r7, r2)
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.wrap(r1)
            java.nio.ByteOrder r5 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteBuffer r4 = r4.order(r5)
            java.lang.Object r5 = r3.unmarshal(r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraMetadataNative.getBase(android.hardware.camera2.impl.CameraMetadataNative$Key):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public int[] getAvailableFormats() {
        int[] availableFormats = (int[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_FORMATS);
        if (availableFormats != null) {
            for (int i = 0; i < availableFormats.length; i++) {
                if (availableFormats[i] == 33) {
                    availableFormats[i] = 256;
                }
            }
        }
        return availableFormats;
    }

    /* access modifiers changed from: private */
    public boolean setFaces(Face[] faces) {
        if (faces == null) {
            return false;
        }
        boolean fullMode = true;
        int numFaces = faces.length;
        for (Face face : faces) {
            if (face == null) {
                numFaces--;
                Log.w(TAG, "setFaces - null face detected, skipping");
            } else if (face.getId() == -1) {
                fullMode = false;
            }
        }
        Rect[] faceRectangles = new Rect[numFaces];
        byte[] faceScores = new byte[numFaces];
        int[] faceIds = null;
        int[] faceLandmarks = null;
        if (fullMode) {
            faceIds = new int[numFaces];
            faceLandmarks = new int[(numFaces * 6)];
        }
        int i = 0;
        for (Face face2 : faces) {
            if (face2 != null) {
                faceRectangles[i] = face2.getBounds();
                faceScores[i] = (byte) face2.getScore();
                if (fullMode) {
                    faceIds[i] = face2.getId();
                    int j = 0 + 1;
                    faceLandmarks[(i * 6) + 0] = face2.getLeftEyePosition().x;
                    int j2 = j + 1;
                    faceLandmarks[(i * 6) + j] = face2.getLeftEyePosition().y;
                    int j3 = j2 + 1;
                    faceLandmarks[(i * 6) + j2] = face2.getRightEyePosition().x;
                    int j4 = j3 + 1;
                    faceLandmarks[(i * 6) + j3] = face2.getRightEyePosition().y;
                    int j5 = j4 + 1;
                    faceLandmarks[(i * 6) + j4] = face2.getMouthPosition().x;
                    int i2 = j5 + 1;
                    faceLandmarks[(i * 6) + j5] = face2.getMouthPosition().y;
                }
                i++;
            }
        }
        set(CaptureResult.STATISTICS_FACE_RECTANGLES, faceRectangles);
        set(CaptureResult.STATISTICS_FACE_IDS, faceIds);
        set(CaptureResult.STATISTICS_FACE_LANDMARKS, faceLandmarks);
        set(CaptureResult.STATISTICS_FACE_SCORES, faceScores);
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: java.lang.Object[]} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.hardware.camera2.params.Face[] getFaces() {
        /*
            r24 = this;
            r0 = r24
            android.hardware.camera2.CaptureResult$Key<java.lang.Integer> r1 = android.hardware.camera2.CaptureResult.STATISTICS_FACE_DETECT_MODE
            java.lang.Object r1 = r0.get(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            android.hardware.camera2.CaptureResult$Key<byte[]> r2 = android.hardware.camera2.CaptureResult.STATISTICS_FACE_SCORES
            java.lang.Object r2 = r0.get(r2)
            byte[] r2 = (byte[]) r2
            android.hardware.camera2.CaptureResult$Key<android.graphics.Rect[]> r3 = android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES
            java.lang.Object r3 = r0.get(r3)
            android.graphics.Rect[] r3 = (android.graphics.Rect[]) r3
            android.hardware.camera2.CaptureResult$Key<int[]> r4 = android.hardware.camera2.CaptureResult.STATISTICS_FACE_IDS
            java.lang.Object r4 = r0.get(r4)
            int[] r4 = (int[]) r4
            android.hardware.camera2.CaptureResult$Key<int[]> r5 = android.hardware.camera2.CaptureResult.STATISTICS_FACE_LANDMARKS
            java.lang.Object r5 = r0.get(r5)
            int[] r5 = (int[]) r5
            r6 = 5
            java.lang.Object[] r7 = new java.lang.Object[r6]
            r8 = 0
            r7[r8] = r1
            r9 = 1
            r7[r9] = r2
            r10 = 2
            r7[r10] = r3
            r11 = 3
            r7[r11] = r4
            r12 = 4
            r7[r12] = r5
            boolean r7 = areValuesAllNull(r7)
            if (r7 == 0) goto L_0x0044
            r6 = 0
            return r6
        L_0x0044:
            if (r1 != 0) goto L_0x0052
            java.lang.String r7 = "CameraMetadataJV"
            java.lang.String r13 = "Face detect mode metadata is null, assuming the mode is SIMPLE"
            android.util.Log.w((java.lang.String) r7, (java.lang.String) r13)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)
            goto L_0x008b
        L_0x0052:
            int r7 = r1.intValue()
            if (r7 <= r10) goto L_0x005d
            java.lang.Integer r1 = java.lang.Integer.valueOf(r10)
            goto L_0x008b
        L_0x005d:
            int r7 = r1.intValue()
            if (r7 != 0) goto L_0x0066
            android.hardware.camera2.params.Face[] r6 = new android.hardware.camera2.params.Face[r8]
            return r6
        L_0x0066:
            int r7 = r1.intValue()
            if (r7 == r9) goto L_0x008b
            int r7 = r1.intValue()
            if (r7 == r10) goto L_0x008b
            java.lang.String r6 = "CameraMetadataJV"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "Unknown face detect mode: "
            r7.append(r9)
            r7.append(r1)
            java.lang.String r7 = r7.toString()
            android.util.Log.w((java.lang.String) r6, (java.lang.String) r7)
            android.hardware.camera2.params.Face[] r6 = new android.hardware.camera2.params.Face[r8]
            return r6
        L_0x008b:
            if (r2 == 0) goto L_0x0191
            if (r3 != 0) goto L_0x0091
            goto L_0x0191
        L_0x0091:
            int r7 = r2.length
            int r13 = r3.length
            if (r7 == r13) goto L_0x00b0
            java.lang.String r7 = "CameraMetadataJV"
            java.lang.String r13 = "Face score size(%d) doesn match face rectangle size(%d)!"
            java.lang.Object[] r14 = new java.lang.Object[r10]
            int r15 = r2.length
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            r14[r8] = r15
            int r15 = r3.length
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            r14[r9] = r15
            java.lang.String r13 = java.lang.String.format(r13, r14)
            android.util.Log.w((java.lang.String) r7, (java.lang.String) r13)
        L_0x00b0:
            int r7 = r2.length
            int r13 = r3.length
            int r7 = java.lang.Math.min(r7, r13)
            int r13 = r1.intValue()
            if (r13 != r10) goto L_0x0104
            if (r4 == 0) goto L_0x00f9
            if (r5 != 0) goto L_0x00c1
            goto L_0x00f9
        L_0x00c1:
            int r13 = r4.length
            if (r13 != r7) goto L_0x00c9
            int r13 = r5.length
            int r14 = r7 * 6
            if (r13 == r14) goto L_0x00ec
        L_0x00c9:
            java.lang.String r13 = "CameraMetadataJV"
            java.lang.String r14 = "Face id size(%d), or face landmark size(%d) don'tmatch face number(%d)!"
            java.lang.Object[] r15 = new java.lang.Object[r11]
            int r6 = r4.length
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r15[r8] = r6
            int r6 = r5.length
            int r6 = r6 * 6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r15[r9] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)
            r15[r10] = r6
            java.lang.String r6 = java.lang.String.format(r14, r15)
            android.util.Log.w((java.lang.String) r13, (java.lang.String) r6)
        L_0x00ec:
            int r6 = r4.length
            int r6 = java.lang.Math.min(r7, r6)
            int r7 = r5.length
            int r7 = r7 / 6
            int r7 = java.lang.Math.min(r6, r7)
            goto L_0x0104
        L_0x00f9:
            java.lang.String r6 = "CameraMetadataJV"
            java.lang.String r13 = "Expect face ids and landmarks to be non-null for FULL mode,fallback to SIMPLE mode"
            android.util.Log.w((java.lang.String) r6, (java.lang.String) r13)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)
        L_0x0104:
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            int r13 = r1.intValue()
            r14 = 100
            if (r13 != r9) goto L_0x012b
        L_0x0112:
            if (r8 >= r7) goto L_0x0187
            byte r10 = r2[r8]
            if (r10 > r14) goto L_0x0128
            byte r10 = r2[r8]
            if (r10 < r9) goto L_0x0128
            android.hardware.camera2.params.Face r10 = new android.hardware.camera2.params.Face
            r11 = r3[r8]
            byte r12 = r2[r8]
            r10.<init>(r11, r12)
            r6.add(r10)
        L_0x0128:
            int r8 = r8 + 1
            goto L_0x0112
        L_0x012b:
        L_0x012c:
            if (r8 >= r7) goto L_0x0187
            byte r13 = r2[r8]
            if (r13 > r14) goto L_0x0181
            byte r13 = r2[r8]
            if (r13 < r9) goto L_0x0181
            r13 = r4[r8]
            if (r13 < 0) goto L_0x0181
            android.graphics.Point r13 = new android.graphics.Point
            int r15 = r8 * 6
            r15 = r5[r15]
            int r17 = r8 * 6
            int r17 = r17 + 1
            r9 = r5[r17]
            r13.<init>(r15, r9)
            r21 = r13
            android.graphics.Point r9 = new android.graphics.Point
            int r13 = r8 * 6
            int r13 = r13 + r10
            r13 = r5[r13]
            int r15 = r8 * 6
            int r15 = r15 + r11
            r15 = r5[r15]
            r9.<init>(r13, r15)
            r22 = r9
            android.graphics.Point r9 = new android.graphics.Point
            int r13 = r8 * 6
            int r13 = r13 + r12
            r13 = r5[r13]
            int r15 = r8 * 6
            r16 = 5
            int r15 = r15 + 5
            r15 = r5[r15]
            r9.<init>(r13, r15)
            r23 = r9
            android.hardware.camera2.params.Face r9 = new android.hardware.camera2.params.Face
            r18 = r3[r8]
            byte r19 = r2[r8]
            r20 = r4[r8]
            r17 = r9
            r17.<init>(r18, r19, r20, r21, r22, r23)
            r6.add(r9)
            goto L_0x0183
        L_0x0181:
            r16 = 5
        L_0x0183:
            int r8 = r8 + 1
            r9 = 1
            goto L_0x012c
        L_0x0187:
            int r8 = r6.size()
            android.hardware.camera2.params.Face[] r8 = new android.hardware.camera2.params.Face[r8]
            r6.toArray(r8)
            return r8
        L_0x0191:
            java.lang.String r6 = "CameraMetadataJV"
            java.lang.String r7 = "Expect face scores and rectangles to be non-null"
            android.util.Log.w((java.lang.String) r6, (java.lang.String) r7)
            android.hardware.camera2.params.Face[] r6 = new android.hardware.camera2.params.Face[r8]
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraMetadataNative.getFaces():android.hardware.camera2.params.Face[]");
    }

    /* access modifiers changed from: private */
    public Rect[] getFaceRectangles() {
        Rect[] faceRectangles = (Rect[]) getBase(CaptureResult.STATISTICS_FACE_RECTANGLES);
        if (faceRectangles == null) {
            return null;
        }
        Rect[] fixedFaceRectangles = new Rect[faceRectangles.length];
        for (int i = 0; i < faceRectangles.length; i++) {
            fixedFaceRectangles[i] = new Rect(faceRectangles[i].left, faceRectangles[i].top, faceRectangles[i].right - faceRectangles[i].left, faceRectangles[i].bottom - faceRectangles[i].top);
        }
        return fixedFaceRectangles;
    }

    /* access modifiers changed from: private */
    public LensShadingMap getLensShadingMap() {
        float[] lsmArray = (float[]) getBase(CaptureResult.STATISTICS_LENS_SHADING_MAP);
        Size s = (Size) get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE);
        if (lsmArray == null) {
            return null;
        }
        if (s != null) {
            return new LensShadingMap(lsmArray, s.getHeight(), s.getWidth());
        }
        Log.w(TAG, "getLensShadingMap - Lens shading map size was null.");
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object[]} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.location.Location getGpsLocation() {
        /*
            r11 = this;
            android.hardware.camera2.CaptureResult$Key<java.lang.String> r0 = android.hardware.camera2.CaptureResult.JPEG_GPS_PROCESSING_METHOD
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            android.hardware.camera2.CaptureResult$Key<double[]> r1 = android.hardware.camera2.CaptureResult.JPEG_GPS_COORDINATES
            java.lang.Object r1 = r11.get(r1)
            double[] r1 = (double[]) r1
            android.hardware.camera2.CaptureResult$Key<java.lang.Long> r2 = android.hardware.camera2.CaptureResult.JPEG_GPS_TIMESTAMP
            java.lang.Object r2 = r11.get(r2)
            java.lang.Long r2 = (java.lang.Long) r2
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r0
            r5 = 1
            r3[r5] = r1
            r6 = 2
            r3[r6] = r2
            boolean r3 = areValuesAllNull(r3)
            if (r3 == 0) goto L_0x002c
            r3 = 0
            return r3
        L_0x002c:
            android.location.Location r3 = new android.location.Location
            java.lang.String r7 = translateProcessToLocationProvider(r0)
            r3.<init>((java.lang.String) r7)
            if (r2 == 0) goto L_0x0042
            long r7 = r2.longValue()
            r9 = 1000(0x3e8, double:4.94E-321)
            long r7 = r7 * r9
            r3.setTime(r7)
            goto L_0x0049
        L_0x0042:
            java.lang.String r7 = "CameraMetadataJV"
            java.lang.String r8 = "getGpsLocation - No timestamp for GPS location."
            android.util.Log.w((java.lang.String) r7, (java.lang.String) r8)
        L_0x0049:
            if (r1 == 0) goto L_0x005b
            r7 = r1[r4]
            r3.setLatitude(r7)
            r4 = r1[r5]
            r3.setLongitude(r4)
            r4 = r1[r6]
            r3.setAltitude(r4)
            goto L_0x0062
        L_0x005b:
            java.lang.String r4 = "CameraMetadataJV"
            java.lang.String r5 = "getGpsLocation - No coordinates for GPS location"
            android.util.Log.w((java.lang.String) r4, (java.lang.String) r5)
        L_0x0062:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraMetadataNative.getGpsLocation():android.location.Location");
    }

    /* access modifiers changed from: private */
    public boolean setGpsLocation(Location l) {
        if (l == null) {
            return false;
        }
        double[] coords = {l.getLatitude(), l.getLongitude(), l.getAltitude()};
        String processMethod = translateLocationProviderToProcess(l.getProvider());
        set(CaptureRequest.JPEG_GPS_TIMESTAMP, Long.valueOf(l.getTime() / 1000));
        set(CaptureRequest.JPEG_GPS_COORDINATES, coords);
        if (processMethod == null) {
            Log.w(TAG, "setGpsLocation - No process method, Location is not from a GPS or NETWORKprovider");
        } else {
            setBase(CaptureRequest.JPEG_GPS_PROCESSING_METHOD, processMethod);
        }
        return true;
    }

    private void parseRecommendedConfigurations(RecommendedStreamConfiguration[] configurations, StreamConfigurationMap fullMap, boolean isDepth, ArrayList<ArrayList<StreamConfiguration>> streamConfigList, ArrayList<ArrayList<StreamConfigurationDuration>> streamDurationList, ArrayList<ArrayList<StreamConfigurationDuration>> streamStallList, boolean[] supportsPrivate) {
        int i;
        int i2;
        char c;
        int width;
        int height;
        int internalFormat;
        StreamConfigurationDuration minDurationConfiguration;
        int publicFormat;
        Size sz;
        StreamConfigurationDuration stallDurationConfiguration;
        StreamConfigurationDuration minDurationConfiguration2;
        RecommendedStreamConfiguration[] recommendedStreamConfigurationArr = configurations;
        StreamConfigurationMap streamConfigurationMap = fullMap;
        ArrayList<ArrayList<StreamConfiguration>> arrayList = streamConfigList;
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList2 = streamDurationList;
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList3 = streamStallList;
        arrayList.ensureCapacity(32);
        arrayList2.ensureCapacity(32);
        arrayList3.ensureCapacity(32);
        for (int i3 = 0; i3 < 32; i3++) {
            arrayList.add(new ArrayList());
            arrayList2.add(new ArrayList());
            arrayList3.add(new ArrayList());
        }
        int publicFormat2 = recommendedStreamConfigurationArr.length;
        int i4 = 0;
        while (i4 < publicFormat2) {
            RecommendedStreamConfiguration c2 = recommendedStreamConfigurationArr[i4];
            int width2 = c2.getWidth();
            int height2 = c2.getHeight();
            int internalFormat2 = c2.getFormat();
            if (isDepth) {
                i = StreamConfigurationMap.depthFormatToPublic(internalFormat2);
            } else {
                i = StreamConfigurationMap.imageFormatToPublic(internalFormat2);
            }
            int publicFormat3 = i;
            Size sz2 = new Size(width2, height2);
            int usecaseBitmap = c2.getUsecaseBitmap();
            if (!c2.isInput()) {
                StreamConfiguration streamConfiguration = new StreamConfiguration(internalFormat2, width2, height2, false);
                long minFrameDuration = streamConfigurationMap.getOutputMinFrameDuration(publicFormat3, sz2);
                if (minFrameDuration > 0) {
                    sz = sz2;
                    i2 = publicFormat2;
                    publicFormat = publicFormat3;
                    internalFormat = internalFormat2;
                    height = height2;
                    width = width2;
                    minDurationConfiguration = new StreamConfigurationDuration(internalFormat2, width2, height2, minFrameDuration);
                } else {
                    i2 = publicFormat2;
                    sz = sz2;
                    publicFormat = publicFormat3;
                    internalFormat = internalFormat2;
                    height = height2;
                    width = width2;
                    minDurationConfiguration = null;
                }
                long stallDuration = streamConfigurationMap.getOutputStallDuration(publicFormat, sz);
                if (stallDuration > 0) {
                    Size size = sz;
                    minDurationConfiguration2 = minDurationConfiguration;
                    stallDurationConfiguration = new StreamConfigurationDuration(internalFormat, width, height, stallDuration);
                } else {
                    minDurationConfiguration2 = minDurationConfiguration;
                    stallDurationConfiguration = null;
                }
                int i5 = 0;
                while (true) {
                    c = ' ';
                    if (i5 >= 32) {
                        break;
                    }
                    if ((usecaseBitmap & (1 << i5)) != 0) {
                        arrayList.get(i5).add(streamConfiguration);
                        if (minFrameDuration > 0) {
                            arrayList2.get(i5).add(minDurationConfiguration2);
                        }
                        if (stallDuration > 0) {
                            arrayList3.get(i5).add(stallDurationConfiguration);
                        }
                        if (supportsPrivate != null && !supportsPrivate[i5] && publicFormat == 34) {
                            supportsPrivate[i5] = true;
                        }
                    }
                    i5++;
                }
            } else {
                i2 = publicFormat2;
                Size size2 = sz2;
                int i6 = publicFormat3;
                int internalFormat3 = internalFormat2;
                int height3 = height2;
                int width3 = width2;
                c = ' ';
                if (usecaseBitmap == 16) {
                    arrayList.get(4).add(new StreamConfiguration(internalFormat3, width3, height3, true));
                } else {
                    int i7 = internalFormat3;
                    int i8 = height3;
                    int i9 = width3;
                    throw new IllegalArgumentException("Recommended input stream configurations should only be advertised in the ZSL use case!");
                }
            }
            i4++;
            char c3 = c;
            publicFormat2 = i2;
            recommendedStreamConfigurationArr = configurations;
        }
    }

    private class StreamConfigurationData {
        StreamConfigurationDuration[] minDurationArray;
        StreamConfigurationDuration[] stallDurationArray;
        StreamConfiguration[] streamConfigurationArray;

        private StreamConfigurationData() {
            this.streamConfigurationArray = null;
            this.minDurationArray = null;
            this.stallDurationArray = null;
        }
    }

    public void initializeStreamConfigurationData(ArrayList<StreamConfiguration> sc, ArrayList<StreamConfigurationDuration> scd, ArrayList<StreamConfigurationDuration> scs, StreamConfigurationData scData) {
        if (scData != null && sc != null) {
            scData.streamConfigurationArray = new StreamConfiguration[sc.size()];
            scData.streamConfigurationArray = (StreamConfiguration[]) sc.toArray(scData.streamConfigurationArray);
            if (scd == null || scd.isEmpty()) {
                scData.minDurationArray = new StreamConfigurationDuration[0];
            } else {
                scData.minDurationArray = new StreamConfigurationDuration[scd.size()];
                scData.minDurationArray = (StreamConfigurationDuration[]) scd.toArray(scData.minDurationArray);
            }
            if (scs == null || scs.isEmpty()) {
                scData.stallDurationArray = new StreamConfigurationDuration[0];
                return;
            }
            scData.stallDurationArray = new StreamConfigurationDuration[scs.size()];
            scData.stallDurationArray = (StreamConfigurationDuration[]) scs.toArray(scData.stallDurationArray);
        }
    }

    public ArrayList<RecommendedStreamConfigurationMap> getRecommendedStreamConfigurations() {
        ArrayList<ArrayList<StreamConfigurationDuration>> streamDurationList;
        ArrayList<ArrayList<StreamConfigurationDuration>> streamStallList;
        ArrayList<ArrayList<StreamConfiguration>> depthStreamConfigList;
        ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamDurationList;
        ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamStallList;
        ArrayList<ArrayList<StreamConfigurationDuration>> streamStallList2;
        ArrayList<ArrayList<StreamConfigurationDuration>> streamDurationList2;
        ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamStallList2;
        ArrayList<ArrayList<StreamConfigurationDuration>> streamDurationList3;
        ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamDurationList2;
        ArrayList<ArrayList<StreamConfiguration>> depthStreamConfigList2;
        AnonymousClass1 r6;
        StreamConfigurationMap map;
        RecommendedStreamConfiguration[] configurations = (RecommendedStreamConfiguration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS);
        RecommendedStreamConfiguration[] depthConfigurations = (RecommendedStreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS);
        AnonymousClass1 r12 = null;
        if (configurations == null && depthConfigurations == null) {
            return null;
        }
        StreamConfigurationMap fullMap = getStreamConfigurationMap();
        ArrayList<RecommendedStreamConfigurationMap> recommendedConfigurations = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        ArrayList<ArrayList<StreamConfigurationDuration>> streamDurationList4 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> streamStallList3 = new ArrayList<>();
        int i = 32;
        boolean[] supportsPrivate = new boolean[32];
        if (configurations != null) {
            streamStallList = streamStallList3;
            streamDurationList = streamDurationList4;
            try {
                parseRecommendedConfigurations(configurations, fullMap, false, arrayList, streamDurationList4, streamStallList3, supportsPrivate);
            } catch (IllegalArgumentException e) {
                IllegalArgumentException illegalArgumentException = e;
                Log.e(TAG, "Failed parsing the recommended stream configurations!");
                return null;
            }
        } else {
            streamStallList = streamStallList3;
            streamDurationList = streamDurationList4;
        }
        ArrayList<ArrayList<StreamConfiguration>> depthStreamConfigList3 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamDurationList3 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamStallList3 = new ArrayList<>();
        if (depthConfigurations != null) {
            depthStreamStallList = depthStreamStallList3;
            depthStreamDurationList = depthStreamDurationList3;
            depthStreamConfigList = depthStreamConfigList3;
            try {
                parseRecommendedConfigurations(depthConfigurations, fullMap, true, depthStreamConfigList3, depthStreamDurationList3, depthStreamStallList, (boolean[]) null);
            } catch (IllegalArgumentException e2) {
                IllegalArgumentException illegalArgumentException2 = e2;
                Log.e(TAG, "Failed parsing the recommended depth stream configurations!");
                return null;
            }
        } else {
            depthStreamStallList = depthStreamStallList3;
            depthStreamDurationList = depthStreamDurationList3;
            depthStreamConfigList = depthStreamConfigList3;
        }
        ReprocessFormatsMap inputOutputFormatsMap = (ReprocessFormatsMap) getBase(CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP);
        HighSpeedVideoConfiguration[] highSpeedVideoConfigurations = (HighSpeedVideoConfiguration[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS);
        boolean listHighResolution = isBurstSupported();
        recommendedConfigurations.ensureCapacity(32);
        int i2 = 0;
        while (i2 < i) {
            StreamConfigurationData scData = new StreamConfigurationData();
            if (configurations != null) {
                streamDurationList2 = streamDurationList;
                streamStallList2 = streamStallList;
                initializeStreamConfigurationData((ArrayList) arrayList.get(i2), streamDurationList2.get(i2), streamStallList2.get(i2), scData);
            } else {
                streamStallList2 = streamStallList;
                streamDurationList2 = streamDurationList;
            }
            StreamConfigurationData depthScData = new StreamConfigurationData();
            if (depthConfigurations != null) {
                ArrayList<ArrayList<StreamConfiguration>> depthStreamConfigList4 = depthStreamConfigList;
                depthStreamConfigList2 = depthStreamConfigList4;
                ArrayList<ArrayList<StreamConfigurationDuration>> depthStreamDurationList4 = depthStreamDurationList;
                depthStreamDurationList2 = depthStreamDurationList4;
                streamDurationList3 = streamDurationList2;
                ArrayList<ArrayList<StreamConfigurationDuration>> streamDurationList5 = depthStreamStallList;
                depthStreamStallList2 = streamDurationList5;
                initializeStreamConfigurationData(depthStreamConfigList4.get(i2), depthStreamDurationList4.get(i2), streamDurationList5.get(i2), depthScData);
            } else {
                streamDurationList3 = streamDurationList2;
                depthStreamStallList2 = depthStreamStallList;
                depthStreamDurationList2 = depthStreamDurationList;
                depthStreamConfigList2 = depthStreamConfigList;
            }
            if ((scData.streamConfigurationArray == null || scData.streamConfigurationArray.length == 0) && (depthScData.streamConfigurationArray == null || depthScData.streamConfigurationArray.length == 0)) {
                r6 = null;
                recommendedConfigurations.add((Object) null);
            } else {
                switch (i2) {
                    case 0:
                    case 2:
                    case 5:
                    case 6:
                        map = new StreamConfigurationMap(scData.streamConfigurationArray, scData.minDurationArray, scData.stallDurationArray, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (HighSpeedVideoConfiguration[]) null, (ReprocessFormatsMap) null, listHighResolution, supportsPrivate[i2]);
                        break;
                    case 1:
                        map = new StreamConfigurationMap(scData.streamConfigurationArray, scData.minDurationArray, scData.stallDurationArray, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, highSpeedVideoConfigurations, (ReprocessFormatsMap) null, listHighResolution, supportsPrivate[i2]);
                        break;
                    case 4:
                        map = new StreamConfigurationMap(scData.streamConfigurationArray, scData.minDurationArray, scData.stallDurationArray, depthScData.streamConfigurationArray, depthScData.minDurationArray, depthScData.stallDurationArray, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (HighSpeedVideoConfiguration[]) null, inputOutputFormatsMap, listHighResolution, supportsPrivate[i2]);
                        break;
                    default:
                        map = new StreamConfigurationMap(scData.streamConfigurationArray, scData.minDurationArray, scData.stallDurationArray, depthScData.streamConfigurationArray, depthScData.minDurationArray, depthScData.stallDurationArray, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (StreamConfiguration[]) null, (StreamConfigurationDuration[]) null, (StreamConfigurationDuration[]) null, (HighSpeedVideoConfiguration[]) null, (ReprocessFormatsMap) null, listHighResolution, supportsPrivate[i2]);
                        break;
                }
                recommendedConfigurations.add(new RecommendedStreamConfigurationMap(map, i2, supportsPrivate[i2]));
                r6 = null;
            }
            i2++;
            streamStallList = streamStallList2;
            depthStreamConfigList = depthStreamConfigList2;
            depthStreamDurationList = depthStreamDurationList2;
            streamDurationList = streamDurationList3;
            depthStreamStallList = depthStreamStallList2;
            i = 32;
            r12 = r6;
        }
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList2 = streamDurationList;
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList3 = depthStreamStallList;
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList4 = depthStreamDurationList;
        ArrayList<ArrayList<StreamConfiguration>> arrayList5 = depthStreamConfigList;
        return recommendedConfigurations;
    }

    private boolean isBurstSupported() {
        for (int capability : (int[]) getBase(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)) {
            if (capability == 6) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatoryStreamCombinations() {
        int[] capabilities = (int[]) getBase(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        ArrayList<Integer> caps = new ArrayList<>();
        caps.ensureCapacity(capabilities.length);
        for (int c : capabilities) {
            caps.add(new Integer(c));
        }
        List<MandatoryStreamCombination> combs = new MandatoryStreamCombination.Builder(this.mCameraId, ((Integer) getBase(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue(), this.mDisplaySize, caps, getStreamConfigurationMap()).getAvailableMandatoryStreamCombinations();
        if (combs == null || combs.isEmpty()) {
            return null;
        }
        return (MandatoryStreamCombination[]) combs.toArray(new MandatoryStreamCombination[combs.size()]);
    }

    /* access modifiers changed from: private */
    public StreamConfigurationMap getStreamConfigurationMap() {
        return new StreamConfigurationMap((StreamConfiguration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS), (StreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS), (StreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS), (StreamConfiguration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS), (HighSpeedVideoConfiguration[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS), (ReprocessFormatsMap) getBase(CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP), isBurstSupported());
    }

    /* access modifiers changed from: private */
    public <T> Integer getMaxRegions(Key<T> key) {
        int[] maxRegions = (int[]) getBase(CameraCharacteristics.CONTROL_MAX_REGIONS);
        if (maxRegions == null) {
            return null;
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AE)) {
            return Integer.valueOf(maxRegions[0]);
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)) {
            return Integer.valueOf(maxRegions[1]);
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)) {
            return Integer.valueOf(maxRegions[2]);
        }
        throw new AssertionError("Invalid key " + key);
    }

    /* access modifiers changed from: private */
    public <T> Integer getMaxNumOutputs(Key<T> key) {
        int[] maxNumOutputs = (int[]) getBase(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_STREAMS);
        if (maxNumOutputs == null) {
            return null;
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW)) {
            return Integer.valueOf(maxNumOutputs[0]);
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC)) {
            return Integer.valueOf(maxNumOutputs[1]);
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING)) {
            return Integer.valueOf(maxNumOutputs[2]);
        }
        throw new AssertionError("Invalid key " + key);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object[]} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> android.hardware.camera2.params.TonemapCurve getTonemapCurve() {
        /*
            r6 = this;
            android.hardware.camera2.CaptureRequest$Key<float[]> r0 = android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_RED
            java.lang.Object r0 = r6.getBase(r0)
            float[] r0 = (float[]) r0
            android.hardware.camera2.CaptureRequest$Key<float[]> r1 = android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_GREEN
            java.lang.Object r1 = r6.getBase(r1)
            float[] r1 = (float[]) r1
            android.hardware.camera2.CaptureRequest$Key<float[]> r2 = android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_BLUE
            java.lang.Object r2 = r6.getBase(r2)
            float[] r2 = (float[]) r2
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r0
            r4 = 1
            r3[r4] = r1
            r4 = 2
            r3[r4] = r2
            boolean r3 = areValuesAllNull(r3)
            r4 = 0
            if (r3 == 0) goto L_0x002c
            return r4
        L_0x002c:
            if (r0 == 0) goto L_0x0039
            if (r1 == 0) goto L_0x0039
            if (r2 != 0) goto L_0x0033
            goto L_0x0039
        L_0x0033:
            android.hardware.camera2.params.TonemapCurve r3 = new android.hardware.camera2.params.TonemapCurve
            r3.<init>(r0, r1, r2)
            return r3
        L_0x0039:
            java.lang.String r3 = "CameraMetadataJV"
            java.lang.String r5 = "getTonemapCurve - missing tone curve components"
            android.util.Log.w((java.lang.String) r3, (java.lang.String) r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraMetadataNative.getTonemapCurve():android.hardware.camera2.params.TonemapCurve");
    }

    /* access modifiers changed from: private */
    public OisSample[] getOisSamples() {
        long[] timestamps = (long[]) getBase(CaptureResult.STATISTICS_OIS_TIMESTAMPS);
        float[] xShifts = (float[]) getBase(CaptureResult.STATISTICS_OIS_X_SHIFTS);
        float[] yShifts = (float[]) getBase(CaptureResult.STATISTICS_OIS_Y_SHIFTS);
        if (timestamps == null) {
            if (xShifts != null) {
                throw new AssertionError("timestamps is null but xShifts is not");
            } else if (yShifts == null) {
                return null;
            } else {
                throw new AssertionError("timestamps is null but yShifts is not");
            }
        } else if (xShifts == null) {
            throw new AssertionError("timestamps is not null but xShifts is");
        } else if (yShifts != null) {
            int i = 0;
            if (xShifts.length != timestamps.length) {
                throw new AssertionError(String.format("timestamps has %d entries but xShifts has %d", new Object[]{Integer.valueOf(timestamps.length), Integer.valueOf(xShifts.length)}));
            } else if (yShifts.length == timestamps.length) {
                OisSample[] samples = new OisSample[timestamps.length];
                while (true) {
                    int i2 = i;
                    if (i2 >= timestamps.length) {
                        return samples;
                    }
                    samples[i2] = new OisSample(timestamps[i2], xShifts[i2], yShifts[i2]);
                    i = i2 + 1;
                }
            } else {
                throw new AssertionError(String.format("timestamps has %d entries but yShifts has %d", new Object[]{Integer.valueOf(timestamps.length), Integer.valueOf(yShifts.length)}));
            }
        } else {
            throw new AssertionError("timestamps is not null but yShifts is");
        }
    }

    private <T> void setBase(CameraCharacteristics.Key<T> key, T value) {
        setBase(key.getNativeKey(), value);
    }

    private <T> void setBase(CaptureResult.Key<T> key, T value) {
        setBase(key.getNativeKey(), value);
    }

    private <T> void setBase(CaptureRequest.Key<T> key, T value) {
        setBase(key.getNativeKey(), value);
    }

    private <T> void setBase(Key<T> key, T value) {
        int tag = nativeGetTagFromKeyLocal(key.getName());
        if (value == null) {
            writeValues(tag, (byte[]) null);
            return;
        }
        Marshaler<T> marshaler = getMarshalerForKey(key, nativeGetTypeFromTagLocal(tag));
        byte[] values = new byte[marshaler.calculateMarshalSize(value)];
        marshaler.marshal(value, ByteBuffer.wrap(values).order(ByteOrder.nativeOrder()));
        writeValues(tag, values);
    }

    /* access modifiers changed from: private */
    public boolean setAvailableFormats(int[] value) {
        int[] availableFormat = value;
        if (value == null) {
            return false;
        }
        int[] newValues = new int[availableFormat.length];
        for (int i = 0; i < availableFormat.length; i++) {
            newValues[i] = availableFormat[i];
            if (availableFormat[i] == 256) {
                newValues[i] = 33;
            }
        }
        setBase(CameraCharacteristics.SCALER_AVAILABLE_FORMATS, newValues);
        return true;
    }

    /* access modifiers changed from: private */
    public boolean setFaceRectangles(Rect[] faceRects) {
        if (faceRects == null) {
            return false;
        }
        Rect[] newFaceRects = new Rect[faceRects.length];
        for (int i = 0; i < newFaceRects.length; i++) {
            newFaceRects[i] = new Rect(faceRects[i].left, faceRects[i].top, faceRects[i].right + faceRects[i].left, faceRects[i].bottom + faceRects[i].top);
        }
        setBase(CaptureResult.STATISTICS_FACE_RECTANGLES, newFaceRects);
        return true;
    }

    /* access modifiers changed from: private */
    public <T> boolean setTonemapCurve(TonemapCurve tc) {
        if (tc == null) {
            return false;
        }
        float[][] curve = new float[3][];
        for (int i = 0; i <= 2; i++) {
            curve[i] = new float[(tc.getPointCount(i) * 2)];
            tc.copyColorCurve(i, curve[i], 0);
        }
        setBase(CaptureRequest.TONEMAP_CURVE_RED, curve[0]);
        setBase(CaptureRequest.TONEMAP_CURVE_GREEN, curve[1]);
        setBase(CaptureRequest.TONEMAP_CURVE_BLUE, curve[2]);
        return true;
    }

    public void setCameraId(int cameraId) {
        this.mCameraId = cameraId;
    }

    public void setDisplaySize(Size displaySize) {
        this.mDisplaySize = displaySize;
    }

    public void swap(CameraMetadataNative other) {
        nativeSwap(other);
        this.mCameraId = other.mCameraId;
        this.mDisplaySize = other.mDisplaySize;
    }

    public int getEntryCount() {
        return nativeGetEntryCount();
    }

    public boolean isEmpty() {
        return nativeIsEmpty();
    }

    public <K> ArrayList<K> getAllVendorKeys(Class<K> keyClass) {
        if (keyClass != null) {
            return nativeGetAllVendorKeys(keyClass);
        }
        throw new NullPointerException();
    }

    public static int getTag(String key) {
        return nativeGetTagFromKey(key, Long.MAX_VALUE);
    }

    public static int getTag(String key, long vendorId) {
        return nativeGetTagFromKey(key, vendorId);
    }

    public static int getNativeType(int tag, long vendorId) {
        return nativeGetTypeFromTag(tag, vendorId);
    }

    public void writeValues(int tag, byte[] src) {
        nativeWriteValues(tag, src);
    }

    public byte[] readValues(int tag) {
        return nativeReadValues(tag);
    }

    public void dumpToLog() {
        try {
            nativeDump();
        } catch (IOException e) {
            Log.wtf(TAG, "Dump logging failed", e);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private static <T> Marshaler<T> getMarshalerForKey(Key<T> key, int nativeType) {
        return MarshalRegistry.getMarshaler(key.getTypeReference(), nativeType);
    }

    private static void registerAllMarshalers() {
        for (MarshalQueryable query : new MarshalQueryable[]{new MarshalQueryablePrimitive(), new MarshalQueryableEnum(), new MarshalQueryableArray(), new MarshalQueryableBoolean(), new MarshalQueryableNativeByteToInteger(), new MarshalQueryableRect(), new MarshalQueryableSize(), new MarshalQueryableSizeF(), new MarshalQueryableString(), new MarshalQueryableReprocessFormatsMap(), new MarshalQueryableRange(), new MarshalQueryablePair(), new MarshalQueryableMeteringRectangle(), new MarshalQueryableColorSpaceTransform(), new MarshalQueryableStreamConfiguration(), new MarshalQueryableStreamConfigurationDuration(), new MarshalQueryableRggbChannelVector(), new MarshalQueryableBlackLevelPattern(), new MarshalQueryableHighSpeedVideoConfiguration(), new MarshalQueryableRecommendedStreamConfiguration(), new MarshalQueryableParcelable()}) {
            MarshalRegistry.registerMarshalQueryable(query);
        }
    }

    private static boolean areValuesAllNull(Object... objs) {
        for (Object o : objs) {
            if (o != null) {
                return false;
            }
        }
        return true;
    }
}
