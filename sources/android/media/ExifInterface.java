package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiScanner;
import android.opengl.GLES30;
import android.security.keymaster.KeymasterDefs;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import com.android.internal.telephony.gsm.SmsCbConstants;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;
import libcore.io.IoUtils;
import libcore.io.Streams;

public class ExifInterface {
    /* access modifiers changed from: private */
    public static final Charset ASCII = Charset.forName("US-ASCII");
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    private static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    private static final short BYTE_ALIGN_II = 18761;
    private static final short BYTE_ALIGN_MM = 19789;
    private static final int DATA_DEFLATE_ZIP = 8;
    private static final int DATA_HUFFMAN_COMPRESSED = 2;
    private static final int DATA_JPEG = 6;
    private static final int DATA_JPEG_COMPRESSED = 7;
    private static final int DATA_LOSSY_JPEG = 34892;
    private static final int DATA_PACK_BITS_COMPRESSED = 32773;
    private static final int DATA_UNCOMPRESSED = 1;
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    /* access modifiers changed from: private */
    public static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final ExifTag[] EXIF_POINTER_TAGS = {new ExifTag((String) TAG_SUB_IFD_POINTER, 330, 4), new ExifTag((String) TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag((String) TAG_GPS_INFO_IFD_POINTER, (int) GLES30.GL_DRAW_BUFFER0, 4), new ExifTag((String) TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag((String) TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 1), new ExifTag((String) TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 1)};
    private static final ExifTag[][] EXIF_TAGS = {IFD_TIFF_TAGS, IFD_EXIF_TAGS, IFD_GPS_TAGS, IFD_INTEROPERABILITY_TAGS, IFD_THUMBNAIL_TAGS, IFD_TIFF_TAGS, ORF_MAKER_NOTE_TAGS, ORF_CAMERA_SETTINGS_TAGS, ORF_IMAGE_PROCESSING_TAGS, PEF_TAGS};
    private static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    private static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, 49};
    private static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, 112};
    private static final byte[] IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(ASCII);
    private static final byte[] IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(ASCII);
    private static final ExifTag[] IFD_EXIF_TAGS = {new ExifTag((String) TAG_EXPOSURE_TIME, 33434, 5), new ExifTag("FNumber", 33437, 5), new ExifTag((String) TAG_EXPOSURE_PROGRAM, 34850, 3), new ExifTag((String) TAG_SPECTRAL_SENSITIVITY, (int) GLES30.GL_MAX_DRAW_BUFFERS, 2), new ExifTag("ISOSpeedRatings", (int) GLES30.GL_DRAW_BUFFER2, 3), new ExifTag((String) TAG_OECF, (int) GLES30.GL_DRAW_BUFFER3, 7), new ExifTag((String) TAG_EXIF_VERSION, 36864, 2), new ExifTag((String) TAG_DATETIME_ORIGINAL, 36867, 2), new ExifTag((String) TAG_DATETIME_DIGITIZED, 36868, 2), new ExifTag((String) TAG_OFFSET_TIME, 36880, 2), new ExifTag((String) TAG_OFFSET_TIME_ORIGINAL, 36881, 2), new ExifTag((String) TAG_OFFSET_TIME_DIGITIZED, 36882, 2), new ExifTag((String) TAG_COMPONENTS_CONFIGURATION, 37121, 7), new ExifTag((String) TAG_COMPRESSED_BITS_PER_PIXEL, 37122, 5), new ExifTag((String) TAG_SHUTTER_SPEED_VALUE, 37377, 10), new ExifTag((String) TAG_APERTURE_VALUE, 37378, 5), new ExifTag((String) TAG_BRIGHTNESS_VALUE, 37379, 10), new ExifTag((String) TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new ExifTag((String) TAG_MAX_APERTURE_VALUE, 37381, 5), new ExifTag((String) TAG_SUBJECT_DISTANCE, 37382, 5), new ExifTag((String) TAG_METERING_MODE, 37383, 3), new ExifTag((String) TAG_LIGHT_SOURCE, 37384, 3), new ExifTag((String) TAG_FLASH, 37385, 3), new ExifTag((String) TAG_FOCAL_LENGTH, 37386, 5), new ExifTag((String) TAG_SUBJECT_AREA, 37396, 3), new ExifTag((String) TAG_MAKER_NOTE, 37500, 7), new ExifTag((String) TAG_USER_COMMENT, 37510, 7), new ExifTag((String) TAG_SUBSEC_TIME, 37520, 2), new ExifTag("SubSecTimeOriginal", 37521, 2), new ExifTag("SubSecTimeDigitized", 37522, 2), new ExifTag((String) TAG_FLASHPIX_VERSION, 40960, 7), new ExifTag((String) TAG_COLOR_SPACE, 40961, 3), new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new ExifTag((String) TAG_RELATED_SOUND_FILE, 40964, 2), new ExifTag((String) TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag((String) TAG_FLASH_ENERGY, 41483, 5), new ExifTag((String) TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7), new ExifTag((String) TAG_FOCAL_PLANE_X_RESOLUTION, 41486, 5), new ExifTag((String) TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, 5), new ExifTag((String) TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new ExifTag((String) TAG_SUBJECT_LOCATION, 41492, 3), new ExifTag((String) TAG_EXPOSURE_INDEX, 41493, 5), new ExifTag((String) TAG_SENSING_METHOD, 41495, 3), new ExifTag((String) TAG_FILE_SOURCE, 41728, 7), new ExifTag((String) TAG_SCENE_TYPE, 41729, 7), new ExifTag((String) TAG_CFA_PATTERN, 41730, 7), new ExifTag((String) TAG_CUSTOM_RENDERED, 41985, 3), new ExifTag((String) TAG_EXPOSURE_MODE, 41986, 3), new ExifTag((String) TAG_WHITE_BALANCE, 41987, 3), new ExifTag((String) TAG_DIGITAL_ZOOM_RATIO, 41988, 5), new ExifTag((String) TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, 3), new ExifTag((String) TAG_SCENE_CAPTURE_TYPE, 41990, 3), new ExifTag((String) TAG_GAIN_CONTROL, 41991, 3), new ExifTag((String) TAG_CONTRAST, 41992, 3), new ExifTag((String) TAG_SATURATION, 41993, 3), new ExifTag((String) TAG_SHARPNESS, 41994, 3), new ExifTag((String) TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7), new ExifTag((String) TAG_SUBJECT_DISTANCE_RANGE, 41996, 3), new ExifTag((String) TAG_IMAGE_UNIQUE_ID, 42016, 2), new ExifTag((String) TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
    private static final int IFD_FORMAT_BYTE = 1;
    /* access modifiers changed from: private */
    public static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    /* access modifiers changed from: private */
    public static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE"};
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS = {new ExifTag((String) TAG_GPS_VERSION_ID, 0, 1), new ExifTag((String) TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag((String) TAG_GPS_LATITUDE, 2, 5), new ExifTag((String) TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag((String) TAG_GPS_LONGITUDE, 4, 5), new ExifTag((String) TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag((String) TAG_GPS_ALTITUDE, 6, 5), new ExifTag((String) TAG_GPS_TIMESTAMP, 7, 5), new ExifTag((String) TAG_GPS_SATELLITES, 8, 2), new ExifTag((String) TAG_GPS_STATUS, 9, 2), new ExifTag((String) TAG_GPS_MEASURE_MODE, 10, 2), new ExifTag((String) TAG_GPS_DOP, 11, 5), new ExifTag((String) TAG_GPS_SPEED_REF, 12, 2), new ExifTag((String) TAG_GPS_SPEED, 13, 5), new ExifTag((String) TAG_GPS_TRACK_REF, 14, 2), new ExifTag((String) TAG_GPS_TRACK, 15, 5), new ExifTag((String) TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag((String) TAG_GPS_IMG_DIRECTION, 17, 5), new ExifTag((String) TAG_GPS_MAP_DATUM, 18, 2), new ExifTag((String) TAG_GPS_DEST_LATITUDE_REF, 19, 2), new ExifTag((String) TAG_GPS_DEST_LATITUDE, 20, 5), new ExifTag((String) TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new ExifTag((String) TAG_GPS_DEST_LONGITUDE, 22, 5), new ExifTag((String) TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag((String) TAG_GPS_DEST_BEARING, 24, 5), new ExifTag((String) TAG_GPS_DEST_DISTANCE_REF, 25, 2), new ExifTag((String) TAG_GPS_DEST_DISTANCE, 26, 5), new ExifTag((String) TAG_GPS_PROCESSING_METHOD, 27, 7), new ExifTag((String) TAG_GPS_AREA_INFORMATION, 28, 7), new ExifTag((String) TAG_GPS_DATESTAMP, 29, 2), new ExifTag((String) TAG_GPS_DIFFERENTIAL, 30, 3)};
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS = {new ExifTag((String) TAG_INTEROPERABILITY_INDEX, 1, 2)};
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS = {new ExifTag((String) TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag((String) TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag((String) TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag((String) TAG_COMPRESSION, 259, 3), new ExifTag((String) TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag((String) TAG_IMAGE_DESCRIPTION, 270, 2), new ExifTag((String) TAG_MAKE, 271, 2), new ExifTag((String) TAG_MODEL, 272, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag((String) TAG_ORIENTATION, 274, 3), new ExifTag((String) TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag((String) TAG_X_RESOLUTION, 282, 5), new ExifTag((String) TAG_Y_RESOLUTION, 283, 5), new ExifTag((String) TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag((String) TAG_RESOLUTION_UNIT, 296, 3), new ExifTag((String) TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag((String) TAG_SOFTWARE, 305, 2), new ExifTag((String) TAG_DATETIME, 306, 2), new ExifTag((String) TAG_ARTIST, 315, 2), new ExifTag((String) TAG_WHITE_POINT, 318, 5), new ExifTag((String) TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag((String) TAG_SUB_IFD_POINTER, 330, 4), new ExifTag((String) TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag((String) TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag((String) TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag((String) TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag((String) TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag((String) TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag((String) TAG_COPYRIGHT, 33432, 2), new ExifTag((String) TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag((String) TAG_GPS_INFO_IFD_POINTER, (int) GLES30.GL_DRAW_BUFFER0, 4), new ExifTag((String) TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
    private static final ExifTag[] IFD_TIFF_TAGS = {new ExifTag((String) TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag((String) TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag((String) TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag((String) TAG_COMPRESSION, 259, 3), new ExifTag((String) TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag((String) TAG_IMAGE_DESCRIPTION, 270, 2), new ExifTag((String) TAG_MAKE, 271, 2), new ExifTag((String) TAG_MODEL, 272, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag((String) TAG_ORIENTATION, 274, 3), new ExifTag((String) TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag((String) TAG_X_RESOLUTION, 282, 5), new ExifTag((String) TAG_Y_RESOLUTION, 283, 5), new ExifTag((String) TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag((String) TAG_RESOLUTION_UNIT, 296, 3), new ExifTag((String) TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag((String) TAG_SOFTWARE, 305, 2), new ExifTag((String) TAG_DATETIME, 306, 2), new ExifTag((String) TAG_ARTIST, 315, 2), new ExifTag((String) TAG_WHITE_POINT, 318, 5), new ExifTag((String) TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag((String) TAG_SUB_IFD_POINTER, 330, 4), new ExifTag((String) TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag((String) TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag((String) TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag((String) TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag((String) TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag((String) TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag((String) TAG_COPYRIGHT, 33432, 2), new ExifTag((String) TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag((String) TAG_GPS_INFO_IFD_POINTER, (int) GLES30.GL_DRAW_BUFFER0, 4), new ExifTag((String) TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new ExifTag((String) TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new ExifTag((String) TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new ExifTag((String) TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new ExifTag((String) TAG_RW2_ISO, 23, 3), new ExifTag((String) TAG_RW2_JPG_FROM_RAW, 46, 7), new ExifTag((String) TAG_XMP, 700, 1)};
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    private static final int IFD_TYPE_PREVIEW = 5;
    private static final int IFD_TYPE_PRIMARY = 0;
    private static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_HEIF = 12;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_LENGTH_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4);
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4);
    private static final byte[] JPEG_SIGNATURE = {-1, MARKER_SOI, -1};
    private static final byte MARKER = -1;
    private static final byte MARKER_APP1 = -31;
    private static final byte MARKER_COM = -2;
    private static final byte MARKER_EOI = -39;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOI = -40;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS = {new ExifTag((String) TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new ExifTag((String) TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, 4)};
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS = {new ExifTag((String) TAG_ORF_ASPECT_FRAME, (int) SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED, 3)};
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS = {new ExifTag((String) TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new ExifTag((String) TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 4), new ExifTag((String) TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 4)};
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    private static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS = {new ExifTag((String) TAG_COLOR_SPACE, 55, 3)};
    private static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    private static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    private static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    private static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int RAF_INFO_SIZE = 160;
    private static final int RAF_JPEG_LENGTH_VALUE_SIZE = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    private static final int REDUCED_RESOLUTION_IMAGE = 1;
    private static final short RW2_SIGNATURE = 85;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    private static final byte START_CODE = 42;
    private static final String TAG = "ExifInterface";
    @Deprecated
    public static final String TAG_APERTURE = "FNumber";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    private static final String TAG_HAS_THUMBNAIL = "HasThumbnail";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    @Deprecated
    public static final String TAG_ISO = "ISOSpeedRatings";
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, 273, 3);
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIG = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIG = "SubSecTimeOriginal";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    private static final String TAG_THUMBNAIL_DATA = "ThumbnailData";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    private static final String TAG_THUMBNAIL_LENGTH = "ThumbnailLength";
    private static final String TAG_THUMBNAIL_OFFSET = "ThumbnailOffset";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_XMP = "Xmp";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    public static final int WHITEBALANCE_AUTO = 0;
    public static final int WHITEBALANCE_MANUAL = 1;
    private static final HashMap<Integer, Integer> sExifPointerTagMap = new HashMap<>();
    private static final HashMap[] sExifTagMapsForReading = new HashMap[EXIF_TAGS.length];
    private static final HashMap[] sExifTagMapsForWriting = new HashMap[EXIF_TAGS.length];
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private static SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    private static SimpleDateFormat sFormatterTz = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss XXX");
    private static final Pattern sGpsTimestampPattern = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    private static final Pattern sNonZeroTimePattern = Pattern.compile(".*[1-9].*");
    private static final HashSet<String> sTagSetForCompatibility = new HashSet<>(Arrays.asList(new String[]{"FNumber", TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP}));
    private AssetManager.AssetInputStream mAssetInputStream;
    @UnsupportedAppUsage
    private final HashMap[] mAttributes = new HashMap[EXIF_TAGS.length];
    private ByteOrder mExifByteOrder = ByteOrder.BIG_ENDIAN;
    private int mExifOffset;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private String mFilename;
    private Set<Integer> mHandledIfdOffsets = new HashSet(EXIF_TAGS.length);
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private boolean mHasThumbnail;
    private boolean mIsInputStream;
    private boolean mIsSupportedFile;
    private int mMimeType;
    private boolean mModified;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private int mRw2JpgFromRawOffset;
    private FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    static {
        sFormatter.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        sFormatterTz.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        for (int ifdType = 0; ifdType < EXIF_TAGS.length; ifdType++) {
            sExifTagMapsForReading[ifdType] = new HashMap();
            sExifTagMapsForWriting[ifdType] = new HashMap();
            for (ExifTag tag : EXIF_TAGS[ifdType]) {
                sExifTagMapsForReading[ifdType].put(Integer.valueOf(tag.number), tag);
                sExifTagMapsForWriting[ifdType].put(tag.name, tag);
            }
        }
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[0].number), 5);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[1].number), 1);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[2].number), 2);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[3].number), 3);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[4].number), 7);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[5].number), 8);
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        private Rational(long numerator2, long denominator2) {
            if (denominator2 == 0) {
                this.numerator = 0;
                this.denominator = 1;
                return;
            }
            this.numerator = numerator2;
            this.denominator = denominator2;
        }

        public String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public double calculate() {
            return ((double) this.numerator) / ((double) this.denominator);
        }
    }

    private static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        private ExifAttribute(int format2, int numberOfComponents2, byte[] bytes2) {
            this(format2, numberOfComponents2, -1, bytes2);
        }

        private ExifAttribute(int format2, int numberOfComponents2, long bytesOffset2, byte[] bytes2) {
            this.format = format2;
            this.numberOfComponents = numberOfComponents2;
            this.bytesOffset = bytesOffset2;
            this.bytes = bytes2;
        }

        public static ExifAttribute createUShort(int[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * values.length)]);
            buffer.order(byteOrder);
            for (int value : values) {
                buffer.putShort((short) value);
            }
            return new ExifAttribute(3, values.length, buffer.array());
        }

        public static ExifAttribute createUShort(int value, ByteOrder byteOrder) {
            return createUShort(new int[]{value}, byteOrder);
        }

        public static ExifAttribute createULong(long[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * values.length)]);
            buffer.order(byteOrder);
            for (long value : values) {
                buffer.putInt((int) value);
            }
            return new ExifAttribute(4, values.length, buffer.array());
        }

        public static ExifAttribute createULong(long value, ByteOrder byteOrder) {
            return createULong(new long[]{value}, byteOrder);
        }

        public static ExifAttribute createSLong(int[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[9] * values.length)]);
            buffer.order(byteOrder);
            for (int value : values) {
                buffer.putInt(value);
            }
            return new ExifAttribute(9, values.length, buffer.array());
        }

        public static ExifAttribute createSLong(int value, ByteOrder byteOrder) {
            return createSLong(new int[]{value}, byteOrder);
        }

        public static ExifAttribute createByte(String value) {
            if (value.length() != 1 || value.charAt(0) < '0' || value.charAt(0) > '1') {
                byte[] ascii = value.getBytes(ExifInterface.ASCII);
                return new ExifAttribute(1, ascii.length, ascii);
            }
            byte[] bytes2 = {(byte) (value.charAt(0) - '0')};
            return new ExifAttribute(1, bytes2.length, bytes2);
        }

        public static ExifAttribute createString(String value) {
            byte[] ascii = (value + 0).getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, ascii.length, ascii);
        }

        public static ExifAttribute createURational(Rational[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * values.length)]);
            buffer.order(byteOrder);
            for (Rational value : values) {
                buffer.putInt((int) value.numerator);
                buffer.putInt((int) value.denominator);
            }
            return new ExifAttribute(5, values.length, buffer.array());
        }

        public static ExifAttribute createURational(Rational value, ByteOrder byteOrder) {
            return createURational(new Rational[]{value}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[10] * values.length)]);
            buffer.order(byteOrder);
            for (Rational value : values) {
                buffer.putInt((int) value.numerator);
                buffer.putInt((int) value.denominator);
            }
            return new ExifAttribute(10, values.length, buffer.array());
        }

        public static ExifAttribute createSRational(Rational value, ByteOrder byteOrder) {
            return createSRational(new Rational[]{value}, byteOrder);
        }

        public static ExifAttribute createDouble(double[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[12] * values.length)]);
            buffer.order(byteOrder);
            for (double value : values) {
                buffer.putDouble(value);
            }
            return new ExifAttribute(12, values.length, buffer.array());
        }

        public static ExifAttribute createDouble(double value, ByteOrder byteOrder) {
            return createDouble(new double[]{value}, byteOrder);
        }

        public String toString() {
            return "(" + ExifInterface.IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + ")";
        }

        /* access modifiers changed from: private */
        public Object getValue(ByteOrder byteOrder) {
            try {
                ByteOrderedDataInputStream inputStream = new ByteOrderedDataInputStream(this.bytes);
                try {
                    inputStream.setByteOrder(byteOrder);
                    int i = 0;
                    switch (this.format) {
                        case 1:
                        case 6:
                            if (this.bytes.length != 1 || this.bytes[0] < 0 || this.bytes[0] > 1) {
                                return new String(this.bytes, ExifInterface.ASCII);
                            }
                            return new String(new char[]{(char) (this.bytes[0] + 48)});
                        case 2:
                        case 7:
                            int index = 0;
                            if (this.numberOfComponents >= ExifInterface.EXIF_ASCII_PREFIX.length) {
                                boolean same = true;
                                while (true) {
                                    if (i < ExifInterface.EXIF_ASCII_PREFIX.length) {
                                        if (this.bytes[i] != ExifInterface.EXIF_ASCII_PREFIX[i]) {
                                            same = false;
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                if (same) {
                                    index = ExifInterface.EXIF_ASCII_PREFIX.length;
                                }
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            while (true) {
                                if (index < this.numberOfComponents) {
                                    byte ch = this.bytes[index];
                                    if (ch != 0) {
                                        if (ch >= 32) {
                                            stringBuilder.append((char) ch);
                                        } else {
                                            stringBuilder.append('?');
                                        }
                                        index++;
                                    }
                                }
                            }
                            return stringBuilder.toString();
                        case 3:
                            int[] values = new int[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values[i] = inputStream.readUnsignedShort();
                                i++;
                            }
                            return values;
                        case 4:
                            long[] values2 = new long[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values2[i] = inputStream.readUnsignedInt();
                                i++;
                            }
                            return values2;
                        case 5:
                            Rational[] values3 = new Rational[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values3[i] = new Rational(inputStream.readUnsignedInt(), inputStream.readUnsignedInt());
                                i++;
                            }
                            return values3;
                        case 8:
                            int[] values4 = new int[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values4[i] = inputStream.readShort();
                                i++;
                            }
                            return values4;
                        case 9:
                            int[] values5 = new int[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values5[i] = inputStream.readInt();
                                i++;
                            }
                            return values5;
                        case 10:
                            Rational[] values6 = new Rational[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                long numerator = (long) inputStream.readInt();
                                long denominator = (long) inputStream.readInt();
                                long j = denominator;
                                values6[i] = new Rational(numerator, denominator);
                                i++;
                            }
                            return values6;
                        case 11:
                            double[] values7 = new double[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values7[i] = (double) inputStream.readFloat();
                                i++;
                            }
                            return values7;
                        case 12:
                            double[] values8 = new double[this.numberOfComponents];
                            while (i < this.numberOfComponents) {
                                values8[i] = inputStream.readDouble();
                                i++;
                            }
                            return values8;
                        default:
                            return null;
                    }
                } catch (IOException e) {
                    e = e;
                    Log.w(ExifInterface.TAG, "IOException occurred during reading a value", e);
                    return null;
                }
            } catch (IOException e2) {
                e = e2;
                ByteOrder byteOrder2 = byteOrder;
                Log.w(ExifInterface.TAG, "IOException occurred during reading a value", e);
                return null;
            }
        }

        public double getDoubleValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                if (value instanceof long[]) {
                    long[] array = (long[]) value;
                    if (array.length == 1) {
                        return (double) array[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof int[]) {
                    int[] array2 = (int[]) value;
                    if (array2.length == 1) {
                        return (double) array2[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof double[]) {
                    double[] array3 = (double[]) value;
                    if (array3.length == 1) {
                        return array3[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof Rational[]) {
                    Rational[] array4 = (Rational[]) value;
                    if (array4.length == 1) {
                        return array4[0].calculate();
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a double value");
                }
            }
        }

        public int getIntValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else {
                if (value instanceof long[]) {
                    long[] array = (long[]) value;
                    if (array.length == 1) {
                        return (int) array[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof int[]) {
                    int[] array2 = (int[]) value;
                    if (array2.length == 1) {
                        return array2[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a integer value");
                }
            }
        }

        public String getStringValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                while (true) {
                    int i2 = i;
                    if (i2 >= array.length) {
                        return stringBuilder.toString();
                    }
                    stringBuilder.append(array[i2]);
                    if (i2 + 1 != array.length) {
                        stringBuilder.append(SmsManager.REGEX_PREFIX_DELIMITER);
                    }
                    i = i2 + 1;
                }
            } else if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                while (true) {
                    int i3 = i;
                    if (i3 >= array2.length) {
                        return stringBuilder.toString();
                    }
                    stringBuilder.append(array2[i3]);
                    if (i3 + 1 != array2.length) {
                        stringBuilder.append(SmsManager.REGEX_PREFIX_DELIMITER);
                    }
                    i = i3 + 1;
                }
            } else if (value instanceof double[]) {
                double[] array3 = (double[]) value;
                while (true) {
                    int i4 = i;
                    if (i4 >= array3.length) {
                        return stringBuilder.toString();
                    }
                    stringBuilder.append(array3[i4]);
                    if (i4 + 1 != array3.length) {
                        stringBuilder.append(SmsManager.REGEX_PREFIX_DELIMITER);
                    }
                    i = i4 + 1;
                }
            } else if (!(value instanceof Rational[])) {
                return null;
            } else {
                Rational[] array4 = (Rational[]) value;
                while (true) {
                    int i5 = i;
                    if (i5 >= array4.length) {
                        return stringBuilder.toString();
                    }
                    stringBuilder.append(array4[i5].numerator);
                    stringBuilder.append('/');
                    stringBuilder.append(array4[i5].denominator);
                    if (i5 + 1 != array4.length) {
                        stringBuilder.append(SmsManager.REGEX_PREFIX_DELIMITER);
                    }
                    i = i5 + 1;
                }
            }
        }

        public int size() {
            return ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }
    }

    private static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        private ExifTag(String name2, int number2, int format) {
            this.name = name2;
            this.number = number2;
            this.primaryFormat = format;
            this.secondaryFormat = -1;
        }

        private ExifTag(String name2, int number2, int primaryFormat2, int secondaryFormat2) {
            this.name = name2;
            this.number = number2;
            this.primaryFormat = primaryFormat2;
            this.secondaryFormat = secondaryFormat2;
        }
    }

    public ExifInterface(File file) throws IOException {
        if (file != null) {
            initForFilename(file.getAbsolutePath());
            return;
        }
        throw new NullPointerException("file cannot be null");
    }

    public ExifInterface(String filename) throws IOException {
        if (filename != null) {
            initForFilename(filename);
            return;
        }
        throw new NullPointerException("filename cannot be null");
    }

    /* JADX WARNING: type inference failed for: r0v6, types: [android.content.res.AssetManager$AssetInputStream, java.io.FileDescriptor, java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v7, types: [java.lang.AutoCloseable] */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExifInterface(java.io.FileDescriptor r4) throws java.io.IOException {
        /*
            r3 = this;
            r3.<init>()
            android.media.ExifInterface$ExifTag[][] r0 = EXIF_TAGS
            int r0 = r0.length
            java.util.HashMap[] r0 = new java.util.HashMap[r0]
            r3.mAttributes = r0
            java.util.HashSet r0 = new java.util.HashSet
            android.media.ExifInterface$ExifTag[][] r1 = EXIF_TAGS
            int r1 = r1.length
            r0.<init>(r1)
            r3.mHandledIfdOffsets = r0
            java.nio.ByteOrder r0 = java.nio.ByteOrder.BIG_ENDIAN
            r3.mExifByteOrder = r0
            if (r4 == 0) goto L_0x004e
            r0 = 0
            r3.mAssetInputStream = r0
            r3.mFilename = r0
            r1 = 0
            boolean r2 = isSeekableFD(r4)
            if (r2 == 0) goto L_0x0035
            r3.mSeekableFileDescriptor = r4
            java.io.FileDescriptor r2 = android.system.Os.dup(r4)     // Catch:{ ErrnoException -> 0x002f }
            r4 = r2
            r1 = 1
            goto L_0x0037
        L_0x002f:
            r0 = move-exception
            java.io.IOException r2 = r0.rethrowAsIOException()
            throw r2
        L_0x0035:
            r3.mSeekableFileDescriptor = r0
        L_0x0037:
            r2 = 0
            r3.mIsInputStream = r2
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0049 }
            r2.<init>(r4, r1)     // Catch:{ all -> 0x0049 }
            r0 = r2
            r3.loadAttributes(r0)     // Catch:{ all -> 0x0049 }
            libcore.io.IoUtils.closeQuietly(r0)
            return
        L_0x0049:
            r2 = move-exception
            libcore.io.IoUtils.closeQuietly(r0)
            throw r2
        L_0x004e:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "fileDescriptor cannot be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.<init>(java.io.FileDescriptor):void");
    }

    public ExifInterface(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            this.mFilename = null;
            if (inputStream instanceof AssetManager.AssetInputStream) {
                this.mAssetInputStream = (AssetManager.AssetInputStream) inputStream;
                this.mSeekableFileDescriptor = null;
            } else if (!(inputStream instanceof FileInputStream) || !isSeekableFD(((FileInputStream) inputStream).getFD())) {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
            } else {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = ((FileInputStream) inputStream).getFD();
            }
            this.mIsInputStream = true;
            loadAttributes(inputStream);
            return;
        }
        throw new NullPointerException("inputStream cannot be null");
    }

    private ExifAttribute getExifAttribute(String tag) {
        if (tag != null) {
            for (int i = 0; i < EXIF_TAGS.length; i++) {
                Object value = this.mAttributes[i].get(tag);
                if (value != null) {
                    return (ExifAttribute) value;
                }
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public String getAttribute(String tag) {
        if (tag != null) {
            ExifAttribute attribute = getExifAttribute(tag);
            if (attribute == null) {
                return null;
            }
            if (!sTagSetForCompatibility.contains(tag)) {
                return attribute.getStringValue(this.mExifByteOrder);
            }
            if (!tag.equals(TAG_GPS_TIMESTAMP)) {
                try {
                    return Double.toString(attribute.getDoubleValue(this.mExifByteOrder));
                } catch (NumberFormatException e) {
                    return null;
                }
            } else if (attribute.format != 5 && attribute.format != 10) {
                return null;
            } else {
                Rational[] array = (Rational[]) attribute.getValue(this.mExifByteOrder);
                if (array.length != 3) {
                    return null;
                }
                return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) (((float) array[0].numerator) / ((float) array[0].denominator))), Integer.valueOf((int) (((float) array[1].numerator) / ((float) array[1].denominator))), Integer.valueOf((int) (((float) array[2].numerator) / ((float) array[2].denominator)))});
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public int getAttributeInt(String tag, int defaultValue) {
        if (tag != null) {
            ExifAttribute exifAttribute = getExifAttribute(tag);
            if (exifAttribute == null) {
                return defaultValue;
            }
            try {
                return exifAttribute.getIntValue(this.mExifByteOrder);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public double getAttributeDouble(String tag, double defaultValue) {
        if (tag != null) {
            ExifAttribute exifAttribute = getExifAttribute(tag);
            if (exifAttribute == null) {
                return defaultValue;
            }
            try {
                return exifAttribute.getDoubleValue(this.mExifByteOrder);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0292, code lost:
        r19 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAttribute(java.lang.String r25, java.lang.String r26) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            r3 = r26
            if (r2 == 0) goto L_0x0367
            r0 = 2
            r4 = 1
            if (r3 == 0) goto L_0x00c0
            java.util.HashSet<java.lang.String> r5 = sTagSetForCompatibility
            boolean r5 = r5.contains(r2)
            if (r5 == 0) goto L_0x00c0
            java.lang.String r5 = "GPSTimeStamp"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x0082
            java.util.regex.Pattern r5 = sGpsTimestampPattern
            java.util.regex.Matcher r5 = r5.matcher(r3)
            boolean r6 = r5.find()
            if (r6 != 0) goto L_0x0047
            java.lang.String r0 = "ExifInterface"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Invalid value for "
            r4.append(r6)
            r4.append(r2)
            java.lang.String r6 = " : "
            r4.append(r6)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r4)
            return
        L_0x0047:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = r5.group(r4)
            int r7 = java.lang.Integer.parseInt(r7)
            r6.append(r7)
            java.lang.String r7 = "/1,"
            r6.append(r7)
            java.lang.String r7 = r5.group(r0)
            int r7 = java.lang.Integer.parseInt(r7)
            r6.append(r7)
            java.lang.String r7 = "/1,"
            r6.append(r7)
            r7 = 3
            java.lang.String r7 = r5.group(r7)
            int r7 = java.lang.Integer.parseInt(r7)
            r6.append(r7)
            java.lang.String r7 = "/1"
            r6.append(r7)
            java.lang.String r3 = r6.toString()
            goto L_0x00c0
        L_0x0082:
            double r5 = java.lang.Double.parseDouble(r26)     // Catch:{ NumberFormatException -> 0x00a0 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x00a0 }
            r7.<init>()     // Catch:{ NumberFormatException -> 0x00a0 }
            r8 = 4666723172467343360(0x40c3880000000000, double:10000.0)
            double r8 = r8 * r5
            long r8 = (long) r8     // Catch:{ NumberFormatException -> 0x00a0 }
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x00a0 }
            java.lang.String r8 = "/10000"
            r7.append(r8)     // Catch:{ NumberFormatException -> 0x00a0 }
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x00a0 }
            r3 = r7
            goto L_0x00c0
        L_0x00a0:
            r0 = move-exception
            java.lang.String r4 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Invalid value for "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r6 = " : "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.w((java.lang.String) r4, (java.lang.String) r5)
            return
        L_0x00c0:
            r5 = 0
            r6 = r5
        L_0x00c2:
            android.media.ExifInterface$ExifTag[][] r7 = EXIF_TAGS
            int r7 = r7.length
            if (r6 >= r7) goto L_0x0366
            r7 = 4
            if (r6 != r7) goto L_0x00d5
            boolean r7 = r1.mHasThumbnail
            if (r7 != 0) goto L_0x00d5
        L_0x00cf:
            r19 = r5
            r22 = r6
            goto L_0x035e
        L_0x00d5:
            java.util.HashMap[] r7 = sExifTagMapsForWriting
            r7 = r7[r6]
            java.lang.Object r7 = r7.get(r2)
            if (r7 == 0) goto L_0x035a
            if (r3 != 0) goto L_0x00e9
            java.util.HashMap[] r8 = r1.mAttributes
            r8 = r8[r6]
            r8.remove(r2)
            goto L_0x00cf
        L_0x00e9:
            r8 = r7
            android.media.ExifInterface$ExifTag r8 = (android.media.ExifInterface.ExifTag) r8
            android.util.Pair r9 = guessDataFormat(r3)
            int r10 = r8.primaryFormat
            F r11 = r9.first
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            if (r10 == r11) goto L_0x01ce
            int r10 = r8.primaryFormat
            S r11 = r9.second
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            if (r10 != r11) goto L_0x010a
            goto L_0x01ce
        L_0x010a:
            int r10 = r8.secondaryFormat
            r11 = -1
            if (r10 == r11) goto L_0x012b
            int r10 = r8.secondaryFormat
            F r12 = r9.first
            java.lang.Integer r12 = (java.lang.Integer) r12
            int r12 = r12.intValue()
            if (r10 == r12) goto L_0x0127
            int r10 = r8.secondaryFormat
            S r12 = r9.second
            java.lang.Integer r12 = (java.lang.Integer) r12
            int r12 = r12.intValue()
            if (r10 != r12) goto L_0x012b
        L_0x0127:
            int r10 = r8.secondaryFormat
            goto L_0x01d0
        L_0x012b:
            int r10 = r8.primaryFormat
            if (r10 == r4) goto L_0x01cb
            int r10 = r8.primaryFormat
            r12 = 7
            if (r10 == r12) goto L_0x01cb
            int r10 = r8.primaryFormat
            if (r10 != r0) goto L_0x013a
            goto L_0x01cb
        L_0x013a:
            boolean r10 = DEBUG
            if (r10 == 0) goto L_0x00cf
            java.lang.String r10 = "ExifInterface"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Given tag ("
            r12.append(r13)
            r12.append(r2)
            java.lang.String r13 = ") value didn't match with one of expected formats: "
            r12.append(r13)
            java.lang.String[] r13 = IFD_FORMAT_NAMES
            int r14 = r8.primaryFormat
            r13 = r13[r14]
            r12.append(r13)
            int r13 = r8.secondaryFormat
            if (r13 != r11) goto L_0x0162
            java.lang.String r13 = ""
            goto L_0x0179
        L_0x0162:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = ", "
            r13.append(r14)
            java.lang.String[] r14 = IFD_FORMAT_NAMES
            int r15 = r8.secondaryFormat
            r14 = r14[r15]
            r13.append(r14)
            java.lang.String r13 = r13.toString()
        L_0x0179:
            r12.append(r13)
            java.lang.String r13 = " (guess: "
            r12.append(r13)
            java.lang.String[] r13 = IFD_FORMAT_NAMES
            F r14 = r9.first
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r14 = r14.intValue()
            r13 = r13[r14]
            r12.append(r13)
            S r13 = r9.second
            java.lang.Integer r13 = (java.lang.Integer) r13
            int r13 = r13.intValue()
            if (r13 != r11) goto L_0x019d
            java.lang.String r11 = ""
            goto L_0x01ba
        L_0x019d:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r13 = ", "
            r11.append(r13)
            java.lang.String[] r13 = IFD_FORMAT_NAMES
            S r14 = r9.second
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r14 = r14.intValue()
            r13 = r13[r14]
            r11.append(r13)
            java.lang.String r11 = r11.toString()
        L_0x01ba:
            r12.append(r11)
            java.lang.String r11 = ")"
            r12.append(r11)
            java.lang.String r11 = r12.toString()
            android.util.Log.d(r10, r11)
            goto L_0x00cf
        L_0x01cb:
            int r10 = r8.primaryFormat
            goto L_0x01d0
        L_0x01ce:
            int r10 = r8.primaryFormat
        L_0x01d0:
            switch(r10) {
                case 1: goto L_0x034a;
                case 2: goto L_0x033a;
                case 3: goto L_0x0310;
                case 4: goto L_0x02e6;
                case 5: goto L_0x0296;
                case 6: goto L_0x01d4;
                case 7: goto L_0x033a;
                case 8: goto L_0x01d4;
                case 9: goto L_0x026a;
                case 10: goto L_0x021b;
                case 11: goto L_0x01d4;
                case 12: goto L_0x01f4;
                default: goto L_0x01d4;
            }
        L_0x01d4:
            r19 = r5
            r22 = r6
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x035e
            java.lang.String r0 = "ExifInterface"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Data format isn't one of expected formats: "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r0, r4)
            goto L_0x035e
        L_0x01f4:
            java.lang.String r11 = ","
            java.lang.String[] r11 = r3.split(r11)
            int r12 = r11.length
            double[] r12 = new double[r12]
            r13 = r5
        L_0x01fe:
            int r14 = r11.length
            if (r13 >= r14) goto L_0x020c
            r14 = r11[r13]
            double r14 = java.lang.Double.parseDouble(r14)
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x01fe
        L_0x020c:
            java.util.HashMap[] r13 = r1.mAttributes
            r13 = r13[r6]
            java.nio.ByteOrder r14 = r1.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r14 = android.media.ExifInterface.ExifAttribute.createDouble((double[]) r12, (java.nio.ByteOrder) r14)
            r13.put(r2, r14)
            goto L_0x00cf
        L_0x021b:
            java.lang.String r11 = ","
            java.lang.String[] r11 = r3.split(r11)
            int r12 = r11.length
            android.media.ExifInterface$Rational[] r12 = new android.media.ExifInterface.Rational[r12]
            r13 = r5
        L_0x0225:
            int r14 = r11.length
            if (r13 >= r14) goto L_0x0258
            r14 = r11[r13]
            java.lang.String r15 = "/"
            java.lang.String[] r14 = r14.split(r15)
            android.media.ExifInterface$Rational r21 = new android.media.ExifInterface$Rational
            r15 = r14[r5]
            r22 = r6
            double r5 = java.lang.Double.parseDouble(r15)
            long r5 = (long) r5
            r15 = r14[r4]
            double r0 = java.lang.Double.parseDouble(r15)
            long r0 = (long) r0
            r20 = 0
            r15 = r21
            r16 = r5
            r18 = r0
            r15.<init>(r16, r18)
            r12[r13] = r21
            int r13 = r13 + 1
            r6 = r22
            r0 = 2
            r1 = r24
            r5 = 0
            goto L_0x0225
        L_0x0258:
            r22 = r6
            r1 = r24
            java.util.HashMap[] r0 = r1.mAttributes
            r0 = r0[r22]
            java.nio.ByteOrder r5 = r1.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r5 = android.media.ExifInterface.ExifAttribute.createSRational((android.media.ExifInterface.Rational[]) r12, (java.nio.ByteOrder) r5)
            r0.put(r2, r5)
            goto L_0x0292
        L_0x026a:
            r22 = r6
            java.lang.String r0 = ","
            java.lang.String[] r0 = r3.split(r0)
            int r5 = r0.length
            int[] r5 = new int[r5]
            r6 = 0
        L_0x0276:
            int r11 = r0.length
            if (r6 >= r11) goto L_0x0284
            r11 = r0[r6]
            int r11 = java.lang.Integer.parseInt(r11)
            r5[r6] = r11
            int r6 = r6 + 1
            goto L_0x0276
        L_0x0284:
            java.util.HashMap[] r6 = r1.mAttributes
            r6 = r6[r22]
            java.nio.ByteOrder r11 = r1.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r11 = android.media.ExifInterface.ExifAttribute.createSLong((int[]) r5, (java.nio.ByteOrder) r11)
            r6.put(r2, r11)
        L_0x0292:
            r19 = 0
            goto L_0x035e
        L_0x0296:
            r22 = r6
            java.lang.String r0 = ","
            java.lang.String[] r0 = r3.split(r0)
            int r5 = r0.length
            android.media.ExifInterface$Rational[] r5 = new android.media.ExifInterface.Rational[r5]
            r6 = 0
        L_0x02a2:
            int r11 = r0.length
            if (r6 >= r11) goto L_0x02d1
            r11 = r0[r6]
            java.lang.String r12 = "/"
            java.lang.String[] r11 = r11.split(r12)
            android.media.ExifInterface$Rational r18 = new android.media.ExifInterface$Rational
            r19 = 0
            r12 = r11[r19]
            double r12 = java.lang.Double.parseDouble(r12)
            long r13 = (long) r12
            r12 = r11[r4]
            r23 = r5
            double r4 = java.lang.Double.parseDouble(r12)
            long r4 = (long) r4
            r17 = 0
            r12 = r18
            r15 = r4
            r12.<init>(r13, r15)
            r23[r6] = r18
            int r6 = r6 + 1
            r5 = r23
            r4 = 1
            goto L_0x02a2
        L_0x02d1:
            r23 = r5
            r19 = 0
            java.util.HashMap[] r4 = r1.mAttributes
            r4 = r4[r22]
            java.nio.ByteOrder r5 = r1.mExifByteOrder
            r6 = r23
            android.media.ExifInterface$ExifAttribute r5 = android.media.ExifInterface.ExifAttribute.createURational((android.media.ExifInterface.Rational[]) r6, (java.nio.ByteOrder) r5)
            r4.put(r2, r5)
            goto L_0x035e
        L_0x02e6:
            r19 = r5
            r22 = r6
            java.lang.String r0 = ","
            java.lang.String[] r0 = r3.split(r0)
            int r4 = r0.length
            long[] r4 = new long[r4]
        L_0x02f4:
            int r6 = r0.length
            if (r5 >= r6) goto L_0x0302
            r6 = r0[r5]
            long r11 = java.lang.Long.parseLong(r6)
            r4[r5] = r11
            int r5 = r5 + 1
            goto L_0x02f4
        L_0x0302:
            java.util.HashMap[] r5 = r1.mAttributes
            r5 = r5[r22]
            java.nio.ByteOrder r6 = r1.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r6 = android.media.ExifInterface.ExifAttribute.createULong((long[]) r4, (java.nio.ByteOrder) r6)
            r5.put(r2, r6)
            goto L_0x035e
        L_0x0310:
            r19 = r5
            r22 = r6
            java.lang.String r0 = ","
            java.lang.String[] r0 = r3.split(r0)
            int r4 = r0.length
            int[] r4 = new int[r4]
        L_0x031e:
            int r6 = r0.length
            if (r5 >= r6) goto L_0x032c
            r6 = r0[r5]
            int r6 = java.lang.Integer.parseInt(r6)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x031e
        L_0x032c:
            java.util.HashMap[] r5 = r1.mAttributes
            r5 = r5[r22]
            java.nio.ByteOrder r6 = r1.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r6 = android.media.ExifInterface.ExifAttribute.createUShort((int[]) r4, (java.nio.ByteOrder) r6)
            r5.put(r2, r6)
            goto L_0x035e
        L_0x033a:
            r19 = r5
            r22 = r6
            java.util.HashMap[] r0 = r1.mAttributes
            r0 = r0[r22]
            android.media.ExifInterface$ExifAttribute r4 = android.media.ExifInterface.ExifAttribute.createString(r3)
            r0.put(r2, r4)
            goto L_0x035e
        L_0x034a:
            r19 = r5
            r22 = r6
            java.util.HashMap[] r0 = r1.mAttributes
            r0 = r0[r22]
            android.media.ExifInterface$ExifAttribute r4 = android.media.ExifInterface.ExifAttribute.createByte(r3)
            r0.put(r2, r4)
            goto L_0x035e
        L_0x035a:
            r19 = r5
            r22 = r6
        L_0x035e:
            int r6 = r22 + 1
            r5 = r19
            r0 = 2
            r4 = 1
            goto L_0x00c2
        L_0x0366:
            return
        L_0x0367:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r4 = "tag shouldn't be null"
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.setAttribute(java.lang.String, java.lang.String):void");
    }

    private boolean updateAttribute(String tag, ExifAttribute value) {
        boolean updated = false;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            if (this.mAttributes[i].containsKey(tag)) {
                this.mAttributes[i].put(tag, value);
                updated = true;
            }
        }
        return updated;
    }

    private void removeAttribute(String tag) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(tag);
        }
    }

    private void loadAttributes(InputStream in) throws IOException {
        if (in != null) {
            int i = 0;
            while (i < EXIF_TAGS.length) {
                try {
                    this.mAttributes[i] = new HashMap();
                    i++;
                } catch (IOException | OutOfMemoryError e) {
                    this.mIsSupportedFile = false;
                    Log.w(TAG, "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e);
                    addDefaultValuesForCompatibility();
                    if (!DEBUG) {
                        return;
                    }
                } catch (Throwable th) {
                    addDefaultValuesForCompatibility();
                    if (DEBUG) {
                        printAttributes();
                    }
                    throw th;
                }
            }
            InputStream in2 = new BufferedInputStream(in, 5000);
            this.mMimeType = getMimeType((BufferedInputStream) in2);
            ByteOrderedDataInputStream inputStream = new ByteOrderedDataInputStream(in2);
            switch (this.mMimeType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 8:
                case 11:
                    getRawAttributes(inputStream);
                    break;
                case 4:
                    getJpegAttributes(inputStream, 0, 0);
                    break;
                case 7:
                    getOrfAttributes(inputStream);
                    break;
                case 9:
                    getRafAttributes(inputStream);
                    break;
                case 10:
                    getRw2Attributes(inputStream);
                    break;
                case 12:
                    getHeifAttributes(inputStream);
                    break;
            }
            setThumbnailData(inputStream);
            this.mIsSupportedFile = true;
            addDefaultValuesForCompatibility();
            if (!DEBUG) {
                return;
            }
            printAttributes();
            return;
        }
        throw new NullPointerException("inputstream shouldn't be null");
    }

    private static boolean isSeekableFD(FileDescriptor fd) throws IOException {
        try {
            Os.lseek(fd, 0, OsConstants.SEEK_CUR);
            return true;
        } catch (ErrnoException e) {
            return false;
        }
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            Log.d(TAG, "The size of tag group[" + i + "]: " + this.mAttributes[i].size());
            for (Map.Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute tagValue = (ExifAttribute) entry.getValue();
                Log.d(TAG, "tagName: " + entry.getKey() + ", tagType: " + tagValue.toString() + ", tagValue: '" + tagValue.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    public void saveAttributes() throws IOException {
        if (!this.mIsSupportedFile || this.mMimeType != 4) {
            throw new IOException("ExifInterface only supports saving attributes on JPEG formats.");
        } else if (this.mIsInputStream || (this.mSeekableFileDescriptor == null && this.mFilename == null)) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        } else {
            this.mModified = true;
            this.mThumbnailBytes = getThumbnail();
            FileInputStream in = null;
            FileOutputStream out = null;
            File tempFile = null;
            try {
                if (this.mFilename != null) {
                    tempFile = new File(this.mFilename + ".tmp");
                    if (!new File(this.mFilename).renameTo(tempFile)) {
                        throw new IOException("Could'nt rename to " + tempFile.getAbsolutePath());
                    }
                } else if (this.mSeekableFileDescriptor != null) {
                    tempFile = File.createTempFile("temp", "jpg");
                    Os.lseek(this.mSeekableFileDescriptor, 0, OsConstants.SEEK_SET);
                    in = new FileInputStream(this.mSeekableFileDescriptor);
                    out = new FileOutputStream(tempFile);
                    Streams.copy(in, out);
                }
                IoUtils.closeQuietly(in);
                IoUtils.closeQuietly(out);
                FileOutputStream out2 = null;
                try {
                    FileInputStream in2 = new FileInputStream(tempFile);
                    if (this.mFilename != null) {
                        out2 = new FileOutputStream(this.mFilename);
                    } else if (this.mSeekableFileDescriptor != null) {
                        Os.lseek(this.mSeekableFileDescriptor, 0, OsConstants.SEEK_SET);
                        out2 = new FileOutputStream(this.mSeekableFileDescriptor);
                    }
                    saveJpegAttributes(in2, out2);
                    IoUtils.closeQuietly(in2);
                    IoUtils.closeQuietly(out2);
                    tempFile.delete();
                    this.mThumbnailBytes = null;
                } catch (ErrnoException e) {
                    throw e.rethrowAsIOException();
                } catch (Throwable th) {
                    IoUtils.closeQuietly((AutoCloseable) null);
                    IoUtils.closeQuietly((AutoCloseable) null);
                    tempFile.delete();
                    throw th;
                }
            } catch (ErrnoException e2) {
                throw e2.rethrowAsIOException();
            } catch (Throwable th2) {
                IoUtils.closeQuietly((AutoCloseable) null);
                IoUtils.closeQuietly((AutoCloseable) null);
                throw th2;
            }
        }
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean hasAttribute(String tag) {
        return getExifAttribute(tag) != null;
    }

    public byte[] getThumbnail() {
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    public byte[] getThumbnailBytes() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes != null) {
            return this.mThumbnailBytes;
        }
        InputStream in = null;
        try {
            if (this.mAssetInputStream != null) {
                in = this.mAssetInputStream;
                if (in.markSupported()) {
                    in.reset();
                } else {
                    Log.d(TAG, "Cannot read thumbnail from inputstream without mark/reset support");
                    IoUtils.closeQuietly(in);
                    return null;
                }
            } else if (this.mFilename != null) {
                in = new FileInputStream(this.mFilename);
            } else if (this.mSeekableFileDescriptor != null) {
                FileDescriptor fileDescriptor = Os.dup(this.mSeekableFileDescriptor);
                Os.lseek(fileDescriptor, 0, OsConstants.SEEK_SET);
                in = new FileInputStream(fileDescriptor, true);
            }
            if (in == null) {
                throw new FileNotFoundException();
            } else if (in.skip((long) this.mThumbnailOffset) == ((long) this.mThumbnailOffset)) {
                byte[] buffer = new byte[this.mThumbnailLength];
                if (in.read(buffer) == this.mThumbnailLength) {
                    this.mThumbnailBytes = buffer;
                    return buffer;
                }
                throw new IOException("Corrupted image");
            } else {
                throw new IOException("Corrupted image");
            }
        } catch (ErrnoException | IOException e) {
            Log.d(TAG, "Encountered exception while getting thumbnail", e);
            return null;
        } finally {
            IoUtils.closeQuietly(in);
        }
    }

    public Bitmap getThumbnailBitmap() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (this.mThumbnailCompression == 1) {
            int[] rgbValues = new int[(this.mThumbnailBytes.length / 3)];
            for (int i = 0; i < rgbValues.length; i++) {
                rgbValues[i] = (this.mThumbnailBytes[i * 3] << WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) + 0 + (this.mThumbnailBytes[(i * 3) + 1] << 8) + this.mThumbnailBytes[(i * 3) + 2];
            }
            ExifAttribute imageLengthAttribute = (ExifAttribute) this.mAttributes[4].get(TAG_IMAGE_LENGTH);
            ExifAttribute imageWidthAttribute = (ExifAttribute) this.mAttributes[4].get(TAG_IMAGE_WIDTH);
            if (!(imageLengthAttribute == null || imageWidthAttribute == null)) {
                return Bitmap.createBitmap(rgbValues, imageWidthAttribute.getIntValue(this.mExifByteOrder), imageLengthAttribute.getIntValue(this.mExifByteOrder), Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        if (!this.mHasThumbnail) {
            return false;
        }
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return true;
        }
        return false;
    }

    public long[] getThumbnailRange() {
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        } else if (!this.mHasThumbnail) {
            return null;
        } else {
            return new long[]{(long) this.mThumbnailOffset, (long) this.mThumbnailLength};
        }
    }

    public long[] getAttributeRange(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        } else if (!this.mModified) {
            ExifAttribute attribute = getExifAttribute(tag);
            if (attribute == null) {
                return null;
            }
            return new long[]{attribute.bytesOffset, (long) attribute.bytes.length};
        } else {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
    }

    public byte[] getAttributeBytes(String tag) {
        if (tag != null) {
            ExifAttribute attribute = getExifAttribute(tag);
            if (attribute != null) {
                return attribute.bytes;
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public boolean getLatLong(float[] output) {
        String latValue = getAttribute(TAG_GPS_LATITUDE);
        String latRef = getAttribute(TAG_GPS_LATITUDE_REF);
        String lngValue = getAttribute(TAG_GPS_LONGITUDE);
        String lngRef = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (!(latValue == null || latRef == null || lngValue == null || lngRef == null)) {
            try {
                output[0] = convertRationalLatLonToFloat(latValue, latRef);
                output[1] = convertRationalLatLonToFloat(lngValue, lngRef);
                return true;
            } catch (IllegalArgumentException e) {
            }
        }
        return false;
    }

    public double getAltitude(double defaultValue) {
        double altitude = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int i = -1;
        int ref = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (altitude < 0.0d || ref < 0) {
            return defaultValue;
        }
        if (ref != 1) {
            i = 1;
        }
        return ((double) i) * altitude;
    }

    @UnsupportedAppUsage
    public long getDateTime() {
        return parseDateTime(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    public long getDateTimeDigitized() {
        return parseDateTime(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute("SubSecTimeDigitized"), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    @UnsupportedAppUsage
    public long getDateTimeOriginal() {
        return parseDateTime(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute("SubSecTimeOriginal"), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    private static long parseDateTime(String dateTimeString, String subSecs, String offsetString) {
        if (dateTimeString == null || !sNonZeroTimePattern.matcher(dateTimeString).matches()) {
            return -1;
        }
        try {
            Date datetime = sFormatter.parse(dateTimeString, new ParsePosition(0));
            if (offsetString != null) {
                datetime = sFormatterTz.parse(dateTimeString + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + offsetString, new ParsePosition(0));
            }
            if (datetime == null) {
                return -1;
            }
            long msecs = datetime.getTime();
            if (subSecs == null) {
                return msecs;
            }
            try {
                long sub = Long.parseLong(subSecs);
                while (sub > 1000) {
                    sub /= 10;
                }
                return msecs + sub;
            } catch (NumberFormatException e) {
                return msecs;
            }
        } catch (IllegalArgumentException e2) {
            return -1;
        }
    }

    @UnsupportedAppUsage
    public long getGpsDateTime() {
        String date = getAttribute(TAG_GPS_DATESTAMP);
        String time = getAttribute(TAG_GPS_TIMESTAMP);
        if (date == null || time == null || (!sNonZeroTimePattern.matcher(date).matches() && !sNonZeroTimePattern.matcher(time).matches())) {
            return -1;
        }
        try {
            Date datetime = sFormatter.parse(date + ' ' + time, new ParsePosition(0));
            if (datetime == null) {
                return -1;
            }
            return datetime.getTime();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static float convertRationalLatLonToFloat(String rationalString, String ref) {
        try {
            String[] parts = rationalString.split(SmsManager.REGEX_PREFIX_DELIMITER);
            String[] pair = parts[0].split("/");
            double degrees = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());
            String[] pair2 = parts[1].split("/");
            double minutes = Double.parseDouble(pair2[0].trim()) / Double.parseDouble(pair2[1].trim());
            String[] pair3 = parts[2].split("/");
            double result = (minutes / 60.0d) + degrees + ((Double.parseDouble(pair3[0].trim()) / Double.parseDouble(pair3[1].trim())) / 3600.0d);
            if (ref.equals("S") || ref.equals("W")) {
                return (float) (-result);
            }
            return (float) result;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void initForFilename(String filename) throws IOException {
        FileInputStream in = null;
        this.mAssetInputStream = null;
        this.mFilename = filename;
        this.mIsInputStream = false;
        try {
            in = new FileInputStream(filename);
            if (isSeekableFD(in.getFD())) {
                this.mSeekableFileDescriptor = in.getFD();
            } else {
                this.mSeekableFileDescriptor = null;
            }
            loadAttributes(in);
        } finally {
            IoUtils.closeQuietly(in);
        }
    }

    private int getMimeType(BufferedInputStream in) throws IOException {
        in.mark(5000);
        byte[] signatureCheckBytes = new byte[5000];
        in.read(signatureCheckBytes);
        in.reset();
        if (isJpegFormat(signatureCheckBytes)) {
            return 4;
        }
        if (isRafFormat(signatureCheckBytes)) {
            return 9;
        }
        if (isHeifFormat(signatureCheckBytes)) {
            return 12;
        }
        if (isOrfFormat(signatureCheckBytes)) {
            return 7;
        }
        if (isRw2Format(signatureCheckBytes)) {
            return 10;
        }
        return 0;
    }

    private static boolean isJpegFormat(byte[] signatureCheckBytes) throws IOException {
        for (int i = 0; i < JPEG_SIGNATURE.length; i++) {
            if (signatureCheckBytes[i] != JPEG_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isRafFormat(byte[] signatureCheckBytes) throws IOException {
        byte[] rafSignatureBytes = RAF_SIGNATURE.getBytes();
        for (int i = 0; i < rafSignatureBytes.length; i++) {
            if (signatureCheckBytes[i] != rafSignatureBytes[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeifFormat(byte[] signatureCheckBytes) throws IOException {
        byte[] bArr = signatureCheckBytes;
        ByteOrderedDataInputStream signatureInputStream = null;
        try {
            signatureInputStream = new ByteOrderedDataInputStream(bArr);
            signatureInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
            long chunkSize = (long) signatureInputStream.readInt();
            byte[] chunkType = new byte[4];
            signatureInputStream.read(chunkType);
            if (!Arrays.equals(chunkType, HEIF_TYPE_FTYP)) {
                signatureInputStream.close();
                return false;
            }
            long chunkDataOffset = 8;
            if (chunkSize == 1) {
                chunkSize = signatureInputStream.readLong();
                if (chunkSize < 16) {
                    signatureInputStream.close();
                    return false;
                }
                chunkDataOffset = 8 + 8;
            }
            if (chunkSize > ((long) bArr.length)) {
                chunkSize = (long) bArr.length;
            }
            long chunkDataSize = chunkSize - chunkDataOffset;
            if (chunkDataSize < 8) {
                signatureInputStream.close();
                return false;
            }
            byte[] brand = new byte[4];
            boolean isMif1 = false;
            boolean isHeic = false;
            for (long i = 0; i < chunkDataSize / 4; i++) {
                if (signatureInputStream.read(brand) != brand.length) {
                    signatureInputStream.close();
                    return false;
                }
                if (i != 1) {
                    if (Arrays.equals(brand, HEIF_BRAND_MIF1)) {
                        isMif1 = true;
                    } else if (Arrays.equals(brand, HEIF_BRAND_HEIC)) {
                        isHeic = true;
                    }
                    if (isMif1 && isHeic) {
                        signatureInputStream.close();
                        return true;
                    }
                }
            }
            signatureInputStream.close();
            return false;
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "Exception parsing HEIF file type box.", e);
            }
            if (signatureInputStream == null) {
                ByteOrderedDataInputStream byteOrderedDataInputStream = signatureInputStream;
            }
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isOrfFormat(byte[] signatureCheckBytes) throws IOException {
        ByteOrderedDataInputStream signatureInputStream = new ByteOrderedDataInputStream(signatureCheckBytes);
        this.mExifByteOrder = readByteOrder(signatureInputStream);
        signatureInputStream.setByteOrder(this.mExifByteOrder);
        short orfSignature = signatureInputStream.readShort();
        if (orfSignature == 20306 || orfSignature == 21330) {
            return true;
        }
        return false;
    }

    private boolean isRw2Format(byte[] signatureCheckBytes) throws IOException {
        ByteOrderedDataInputStream signatureInputStream = new ByteOrderedDataInputStream(signatureCheckBytes);
        this.mExifByteOrder = readByteOrder(signatureInputStream);
        signatureInputStream.setByteOrder(this.mExifByteOrder);
        if (signatureInputStream.readShort() == 85) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00f7 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getJpegAttributes(android.media.ExifInterface.ByteOrderedDataInputStream r22, int r23, int r24) throws java.io.IOException {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r24
            boolean r3 = DEBUG
            if (r3 == 0) goto L_0x0020
            java.lang.String r3 = "ExifInterface"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getJpegAttributes starting with: "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r3, r4)
        L_0x0020:
            java.nio.ByteOrder r3 = java.nio.ByteOrder.BIG_ENDIAN
            r1.setByteOrder(r3)
            r3 = r23
            long r4 = (long) r3
            r1.seek(r4)
            r4 = r23
            byte r5 = r22.readByte()
            r6 = r5
            r7 = -1
            if (r5 != r7) goto L_0x01fa
            r5 = 1
            int r4 = r4 + r5
            byte r8 = r22.readByte()
            r9 = -40
            if (r8 != r9) goto L_0x01dd
            int r4 = r4 + r5
        L_0x0040:
            byte r6 = r22.readByte()
            if (r6 != r7) goto L_0x01c0
            int r4 = r4 + 1
            byte r6 = r22.readByte()
            boolean r8 = DEBUG
            if (r8 == 0) goto L_0x006c
            java.lang.String r8 = "ExifInterface"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Found JPEG segment indicator: "
            r9.append(r10)
            r10 = r6 & 255(0xff, float:3.57E-43)
            java.lang.String r10 = java.lang.Integer.toHexString(r10)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r8, r9)
        L_0x006c:
            int r4 = r4 + r5
            r8 = -39
            if (r6 == r8) goto L_0x01ba
            r8 = -38
            if (r6 != r8) goto L_0x0077
            goto L_0x01ba
        L_0x0077:
            int r8 = r22.readUnsignedShort()
            int r8 = r8 + -2
            int r4 = r4 + 2
            boolean r9 = DEBUG
            if (r9 == 0) goto L_0x00ae
            java.lang.String r9 = "ExifInterface"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "JPEG segment: "
            r10.append(r11)
            r11 = r6 & 255(0xff, float:3.57E-43)
            java.lang.String r11 = java.lang.Integer.toHexString(r11)
            r10.append(r11)
            java.lang.String r11 = " (length: "
            r10.append(r11)
            int r11 = r8 + 2
            r10.append(r11)
            java.lang.String r11 = ")"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.d(r9, r10)
        L_0x00ae:
            if (r8 < 0) goto L_0x01b2
            r9 = -31
            if (r6 == r9) goto L_0x012e
            r9 = -2
            if (r6 == r9) goto L_0x00ff
            switch(r6) {
                case -64: goto L_0x00c5;
                case -63: goto L_0x00c5;
                case -62: goto L_0x00c5;
                case -61: goto L_0x00c5;
                default: goto L_0x00ba;
            }
        L_0x00ba:
            switch(r6) {
                case -59: goto L_0x00c5;
                case -58: goto L_0x00c5;
                case -57: goto L_0x00c5;
                default: goto L_0x00bd;
            }
        L_0x00bd:
            switch(r6) {
                case -55: goto L_0x00c5;
                case -54: goto L_0x00c5;
                case -53: goto L_0x00c5;
                default: goto L_0x00c0;
            }
        L_0x00c0:
            switch(r6) {
                case -51: goto L_0x00c5;
                case -50: goto L_0x00c5;
                case -49: goto L_0x00c5;
                default: goto L_0x00c3;
            }
        L_0x00c3:
            goto L_0x0190
        L_0x00c5:
            int r9 = r1.skipBytes(r5)
            if (r9 != r5) goto L_0x00f7
            java.util.HashMap[] r9 = r0.mAttributes
            r9 = r9[r2]
            java.lang.String r10 = "ImageLength"
            int r11 = r22.readUnsignedShort()
            long r11 = (long) r11
            java.nio.ByteOrder r13 = r0.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r11 = android.media.ExifInterface.ExifAttribute.createULong((long) r11, (java.nio.ByteOrder) r13)
            r9.put(r10, r11)
            java.util.HashMap[] r9 = r0.mAttributes
            r9 = r9[r2]
            java.lang.String r10 = "ImageWidth"
            int r11 = r22.readUnsignedShort()
            long r11 = (long) r11
            java.nio.ByteOrder r13 = r0.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r11 = android.media.ExifInterface.ExifAttribute.createULong((long) r11, (java.nio.ByteOrder) r13)
            r9.put(r10, r11)
            int r8 = r8 + -5
            goto L_0x0190
        L_0x00f7:
            java.io.IOException r5 = new java.io.IOException
            java.lang.String r7 = "Invalid SOFx"
            r5.<init>(r7)
            throw r5
        L_0x00ff:
            byte[] r9 = new byte[r8]
            int r10 = r1.read(r9)
            if (r10 != r8) goto L_0x0126
            r8 = 0
            java.lang.String r10 = "UserComment"
            java.lang.String r10 = r0.getAttribute(r10)
            if (r10 != 0) goto L_0x0190
            java.util.HashMap[] r10 = r0.mAttributes
            r10 = r10[r5]
            java.lang.String r11 = "UserComment"
            java.lang.String r12 = new java.lang.String
            java.nio.charset.Charset r13 = ASCII
            r12.<init>(r9, r13)
            android.media.ExifInterface$ExifAttribute r12 = android.media.ExifInterface.ExifAttribute.createString(r12)
            r10.put(r11, r12)
            goto L_0x0190
        L_0x0126:
            java.io.IOException r5 = new java.io.IOException
            java.lang.String r7 = "Invalid exif"
            r5.<init>(r7)
            throw r5
        L_0x012e:
            r9 = r4
            byte[] r10 = new byte[r8]
            r1.readFully(r10)
            int r4 = r4 + r8
            r8 = 0
            byte[] r11 = IDENTIFIER_EXIF_APP1
            boolean r11 = com.android.internal.util.ArrayUtils.startsWith(r10, r11)
            if (r11 == 0) goto L_0x0152
            byte[] r11 = IDENTIFIER_EXIF_APP1
            int r11 = r11.length
            int r11 = r11 + r9
            long r11 = (long) r11
            byte[] r13 = IDENTIFIER_EXIF_APP1
            int r13 = r13.length
            int r14 = r10.length
            byte[] r13 = java.util.Arrays.copyOfRange(r10, r13, r14)
            r0.readExifSegment(r13, r2)
            int r14 = (int) r11
            r0.mExifOffset = r14
            goto L_0x0190
        L_0x0152:
            byte[] r11 = IDENTIFIER_XMP_APP1
            boolean r11 = com.android.internal.util.ArrayUtils.startsWith(r10, r11)
            if (r11 == 0) goto L_0x0190
            byte[] r11 = IDENTIFIER_XMP_APP1
            int r11 = r11.length
            int r11 = r11 + r9
            long r14 = (long) r11
            byte[] r11 = IDENTIFIER_XMP_APP1
            int r11 = r11.length
            int r12 = r10.length
            byte[] r11 = java.util.Arrays.copyOfRange(r10, r11, r12)
            java.lang.String r12 = "Xmp"
            java.lang.String r12 = r0.getAttribute(r12)
            if (r12 != 0) goto L_0x018f
            java.util.HashMap[] r12 = r0.mAttributes
            r13 = 0
            r13 = r12[r13]
            java.lang.String r12 = "Xmp"
            android.media.ExifInterface$ExifAttribute r5 = new android.media.ExifInterface$ExifAttribute
            r16 = 1
            int r7 = r11.length
            r18 = 0
            r2 = r12
            r12 = r5
            r3 = r13
            r13 = r16
            r19 = r14
            r14 = r7
            r15 = r19
            r17 = r11
            r12.<init>(r13, r14, r15, r17)
            r3.put(r2, r5)
        L_0x018f:
        L_0x0190:
            if (r8 < 0) goto L_0x01aa
            int r2 = r1.skipBytes(r8)
            if (r2 != r8) goto L_0x01a2
            int r4 = r4 + r8
            r2 = r24
            r3 = r23
            r5 = 1
            r7 = -1
            goto L_0x0040
        L_0x01a2:
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "Invalid JPEG segment"
            r2.<init>(r3)
            throw r2
        L_0x01aa:
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "Invalid length"
            r2.<init>(r3)
            throw r2
        L_0x01b2:
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "Invalid length"
            r2.<init>(r3)
            throw r2
        L_0x01ba:
            java.nio.ByteOrder r2 = r0.mExifByteOrder
            r1.setByteOrder(r2)
            return
        L_0x01c0:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Invalid marker:"
            r3.append(r5)
            r5 = r6 & 255(0xff, float:3.57E-43)
            java.lang.String r5 = java.lang.Integer.toHexString(r5)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x01dd:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Invalid marker: "
            r3.append(r5)
            r5 = r6 & 255(0xff, float:3.57E-43)
            java.lang.String r5 = java.lang.Integer.toHexString(r5)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x01fa:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Invalid marker: "
            r3.append(r5)
            r5 = r6 & 255(0xff, float:3.57E-43)
            java.lang.String r5 = java.lang.Integer.toHexString(r5)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.getJpegAttributes(android.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    private void getRawAttributes(ByteOrderedDataInputStream in) throws IOException {
        ExifAttribute makerNoteAttribute;
        parseTiffHeaders(in, in.available());
        readImageFileDirectory(in, 0);
        updateImageSizeValues(in, 0);
        updateImageSizeValues(in, 5);
        updateImageSizeValues(in, 4);
        validateImages(in);
        if (this.mMimeType == 8 && (makerNoteAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE)) != null) {
            ByteOrderedDataInputStream makerNoteDataInputStream = new ByteOrderedDataInputStream(makerNoteAttribute.bytes);
            makerNoteDataInputStream.setByteOrder(this.mExifByteOrder);
            makerNoteDataInputStream.seek(6);
            readImageFileDirectory(makerNoteDataInputStream, 9);
            ExifAttribute colorSpaceAttribute = (ExifAttribute) this.mAttributes[9].get(TAG_COLOR_SPACE);
            if (colorSpaceAttribute != null) {
                this.mAttributes[1].put(TAG_COLOR_SPACE, colorSpaceAttribute);
            }
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream in) throws IOException {
        ExifInterface exifInterface = this;
        ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        byteOrderedDataInputStream.skipBytes(84);
        byte[] jpegOffsetBytes = new byte[4];
        byte[] cfaHeaderOffsetBytes = new byte[4];
        byteOrderedDataInputStream.read(jpegOffsetBytes);
        byteOrderedDataInputStream.skipBytes(4);
        byteOrderedDataInputStream.read(cfaHeaderOffsetBytes);
        int rafJpegOffset = ByteBuffer.wrap(jpegOffsetBytes).getInt();
        int rafCfaHeaderOffset = ByteBuffer.wrap(cfaHeaderOffsetBytes).getInt();
        exifInterface.getJpegAttributes(byteOrderedDataInputStream, rafJpegOffset, 5);
        byteOrderedDataInputStream.seek((long) rafCfaHeaderOffset);
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        int numberOfDirectoryEntry = in.readInt();
        if (DEBUG) {
            Log.d(TAG, "numberOfDirectoryEntry: " + numberOfDirectoryEntry);
        }
        int i = 0;
        while (i < numberOfDirectoryEntry) {
            int tagNumber = in.readUnsignedShort();
            int numberOfBytes = in.readUnsignedShort();
            if (tagNumber == TAG_RAF_IMAGE_SIZE.number) {
                int imageLength = in.readShort();
                int imageWidth = in.readShort();
                ExifAttribute imageLengthAttribute = ExifAttribute.createUShort(imageLength, exifInterface.mExifByteOrder);
                ExifAttribute imageWidthAttribute = ExifAttribute.createUShort(imageWidth, exifInterface.mExifByteOrder);
                exifInterface.mAttributes[0].put(TAG_IMAGE_LENGTH, imageLengthAttribute);
                exifInterface.mAttributes[0].put(TAG_IMAGE_WIDTH, imageWidthAttribute);
                if (DEBUG) {
                    Log.d(TAG, "Updated to length: " + imageLength + ", width: " + imageWidth);
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipBytes(numberOfBytes);
            i++;
            exifInterface = this;
        }
    }

    private void getHeifAttributes(ByteOrderedDataInputStream in) throws IOException {
        final ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource((MediaDataSource) new MediaDataSource() {
                long mPosition;

                public void close() throws IOException {
                }

                public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
                    if (size == 0) {
                        return 0;
                    }
                    if (position < 0) {
                        return -1;
                    }
                    try {
                        if (this.mPosition != position) {
                            if (this.mPosition >= 0 && position >= this.mPosition + ((long) byteOrderedDataInputStream.available())) {
                                return -1;
                            }
                            byteOrderedDataInputStream.seek(position);
                            this.mPosition = position;
                        }
                        if (size > byteOrderedDataInputStream.available()) {
                            size = byteOrderedDataInputStream.available();
                        }
                        int bytesRead = byteOrderedDataInputStream.read(buffer, offset, size);
                        if (bytesRead >= 0) {
                            this.mPosition += (long) bytesRead;
                            return bytesRead;
                        }
                    } catch (IOException e) {
                    }
                    this.mPosition = -1;
                    return -1;
                }

                public long getSize() throws IOException {
                    return -1;
                }
            });
            String exifOffsetStr = retriever.extractMetadata(33);
            String exifLengthStr = retriever.extractMetadata(34);
            String hasImage = retriever.extractMetadata(26);
            String hasVideo = retriever.extractMetadata(17);
            String width = null;
            String height = null;
            String rotation = null;
            if ("yes".equals(hasImage)) {
                width = retriever.extractMetadata(29);
                height = retriever.extractMetadata(30);
                rotation = retriever.extractMetadata(31);
            } else if ("yes".equals(hasVideo)) {
                width = retriever.extractMetadata(18);
                height = retriever.extractMetadata(19);
                rotation = retriever.extractMetadata(24);
            }
            if (width != null) {
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createUShort(Integer.parseInt(width), this.mExifByteOrder));
            }
            if (height != null) {
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createUShort(Integer.parseInt(height), this.mExifByteOrder));
            }
            if (rotation != null) {
                int orientation = 1;
                int parseInt = Integer.parseInt(rotation);
                if (parseInt == 90) {
                    orientation = 6;
                } else if (parseInt == 180) {
                    orientation = 3;
                } else if (parseInt == 270) {
                    orientation = 8;
                }
                this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(orientation, this.mExifByteOrder));
            }
            if (exifOffsetStr == null || exifLengthStr == null) {
            } else {
                int offset = Integer.parseInt(exifOffsetStr);
                int length = Integer.parseInt(exifLengthStr);
                if (length > 6) {
                    byteOrderedDataInputStream.seek((long) offset);
                    byte[] identifier = new byte[6];
                    if (byteOrderedDataInputStream.read(identifier) == 6) {
                        int offset2 = offset + 6;
                        int length2 = length - 6;
                        if (Arrays.equals(identifier, IDENTIFIER_EXIF_APP1)) {
                            byte[] bytes = new byte[length2];
                            if (byteOrderedDataInputStream.read(bytes) == length2) {
                                readExifSegment(bytes, 0);
                                String str = exifOffsetStr;
                            } else {
                                String str2 = exifOffsetStr;
                                throw new IOException("Can't read exif");
                            }
                        } else {
                            throw new IOException("Invalid identifier");
                        }
                    } else {
                        throw new IOException("Can't read identifier");
                    }
                } else {
                    throw new IOException("Invalid exif length");
                }
            }
            if (DEBUG) {
                Log.d(TAG, "Heif meta: " + width + "x" + height + ", rotation " + rotation);
            }
        } finally {
            retriever.release();
        }
    }

    private void getOrfAttributes(ByteOrderedDataInputStream in) throws IOException {
        getRawAttributes(in);
        ExifAttribute makerNoteAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (makerNoteAttribute != null) {
            ByteOrderedDataInputStream makerNoteDataInputStream = new ByteOrderedDataInputStream(makerNoteAttribute.bytes);
            makerNoteDataInputStream.setByteOrder(this.mExifByteOrder);
            byte[] makerNoteHeader1Bytes = new byte[ORF_MAKER_NOTE_HEADER_1.length];
            makerNoteDataInputStream.readFully(makerNoteHeader1Bytes);
            makerNoteDataInputStream.seek(0);
            byte[] makerNoteHeader2Bytes = new byte[ORF_MAKER_NOTE_HEADER_2.length];
            makerNoteDataInputStream.readFully(makerNoteHeader2Bytes);
            if (Arrays.equals(makerNoteHeader1Bytes, ORF_MAKER_NOTE_HEADER_1)) {
                makerNoteDataInputStream.seek(8);
            } else if (Arrays.equals(makerNoteHeader2Bytes, ORF_MAKER_NOTE_HEADER_2)) {
                makerNoteDataInputStream.seek(12);
            }
            readImageFileDirectory(makerNoteDataInputStream, 6);
            ExifAttribute imageLengthAttribute = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute bitsPerSampleAttribute = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (!(imageLengthAttribute == null || bitsPerSampleAttribute == null)) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, imageLengthAttribute);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, bitsPerSampleAttribute);
            }
            ExifAttribute aspectFrameAttribute = (ExifAttribute) this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (aspectFrameAttribute != null) {
                int[] iArr = new int[4];
                int[] aspectFrameValues = (int[]) aspectFrameAttribute.getValue(this.mExifByteOrder);
                if (aspectFrameValues[2] > aspectFrameValues[0] && aspectFrameValues[3] > aspectFrameValues[1]) {
                    int primaryImageWidth = (aspectFrameValues[2] - aspectFrameValues[0]) + 1;
                    int primaryImageLength = (aspectFrameValues[3] - aspectFrameValues[1]) + 1;
                    if (primaryImageWidth < primaryImageLength) {
                        int primaryImageWidth2 = primaryImageWidth + primaryImageLength;
                        primaryImageLength = primaryImageWidth2 - primaryImageLength;
                        primaryImageWidth = primaryImageWidth2 - primaryImageLength;
                    }
                    ExifAttribute primaryImageWidthAttribute = ExifAttribute.createUShort(primaryImageWidth, this.mExifByteOrder);
                    ExifAttribute primaryImageLengthAttribute = ExifAttribute.createUShort(primaryImageLength, this.mExifByteOrder);
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, primaryImageWidthAttribute);
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, primaryImageLengthAttribute);
                }
            }
        }
    }

    private void getRw2Attributes(ByteOrderedDataInputStream in) throws IOException {
        getRawAttributes(in);
        if (((ExifAttribute) this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW)) != null) {
            getJpegAttributes(in, this.mRw2JpgFromRawOffset, 5);
        }
        ExifAttribute rw2IsoAttribute = (ExifAttribute) this.mAttributes[0].get(TAG_RW2_ISO);
        ExifAttribute exifIsoAttribute = (ExifAttribute) this.mAttributes[1].get("ISOSpeedRatings");
        if (rw2IsoAttribute != null && exifIsoAttribute == null) {
            this.mAttributes[1].put("ISOSpeedRatings", rw2IsoAttribute);
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream dataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() == -1) {
            dataOutputStream.writeByte(-1);
            if (dataInputStream.readByte() == -40) {
                dataOutputStream.writeByte(-40);
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(-31);
                writeExifSegment(dataOutputStream, 6);
                byte[] bytes = new byte[4096];
                while (dataInputStream.readByte() == -1) {
                    byte marker = dataInputStream.readByte();
                    if (marker != -31) {
                        switch (marker) {
                            case KeymasterDefs.KM_ERROR_UNSUPPORTED_TAG /*-39*/:
                            case -38:
                                dataOutputStream.writeByte(-1);
                                dataOutputStream.writeByte(marker);
                                Streams.copy(dataInputStream, dataOutputStream);
                                return;
                            default:
                                dataOutputStream.writeByte(-1);
                                dataOutputStream.writeByte(marker);
                                int length = dataInputStream.readUnsignedShort();
                                dataOutputStream.writeUnsignedShort(length);
                                int length2 = length - 2;
                                if (length2 >= 0) {
                                    while (length2 > 0) {
                                        int read = dataInputStream.read(bytes, 0, Math.min(length2, bytes.length));
                                        int read2 = read;
                                        if (read < 0) {
                                            break;
                                        } else {
                                            dataOutputStream.write(bytes, 0, read2);
                                            length2 -= read2;
                                        }
                                    }
                                    break;
                                } else {
                                    throw new IOException("Invalid length");
                                }
                        }
                    } else {
                        int length3 = dataInputStream.readUnsignedShort() - 2;
                        if (length3 >= 0) {
                            byte[] identifier = new byte[6];
                            if (length3 >= 6) {
                                if (dataInputStream.read(identifier) != 6) {
                                    throw new IOException("Invalid exif");
                                } else if (Arrays.equals(identifier, IDENTIFIER_EXIF_APP1)) {
                                    if (dataInputStream.skipBytes(length3 - 6) != length3 - 6) {
                                        throw new IOException("Invalid length");
                                    }
                                }
                            }
                            dataOutputStream.writeByte(-1);
                            dataOutputStream.writeByte(marker);
                            dataOutputStream.writeUnsignedShort(length3 + 2);
                            if (length3 >= 6) {
                                length3 -= 6;
                                dataOutputStream.write(identifier);
                            }
                            while (length3 > 0) {
                                int read3 = dataInputStream.read(bytes, 0, Math.min(length3, bytes.length));
                                int read4 = read3;
                                if (read3 >= 0) {
                                    dataOutputStream.write(bytes, 0, read4);
                                    length3 -= read4;
                                }
                            }
                        } else {
                            throw new IOException("Invalid length");
                        }
                    }
                }
                throw new IOException("Invalid marker");
            }
            throw new IOException("Invalid marker");
        }
        throw new IOException("Invalid marker");
    }

    private void readExifSegment(byte[] exifBytes, int imageType) throws IOException {
        ByteOrderedDataInputStream dataInputStream = new ByteOrderedDataInputStream(exifBytes);
        parseTiffHeaders(dataInputStream, exifBytes.length);
        readImageFileDirectory(dataInputStream, imageType);
    }

    private void addDefaultValuesForCompatibility() {
        String valueOfDateTimeOriginal = getAttribute(TAG_DATETIME_ORIGINAL);
        if (valueOfDateTimeOriginal != null && getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, ExifAttribute.createString(valueOfDateTimeOriginal));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream dataInputStream) throws IOException {
        short byteOrder = dataInputStream.readShort();
        if (byteOrder == 18761) {
            if (DEBUG) {
                Log.d(TAG, "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        } else if (byteOrder == 19789) {
            if (DEBUG) {
                Log.d(TAG, "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        } else {
            throw new IOException("Invalid byte order: " + Integer.toHexString(byteOrder));
        }
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream dataInputStream, int exifBytesLength) throws IOException {
        this.mExifByteOrder = readByteOrder(dataInputStream);
        dataInputStream.setByteOrder(this.mExifByteOrder);
        int startCode = dataInputStream.readUnsignedShort();
        if (this.mMimeType == 7 || this.mMimeType == 10 || startCode == 42) {
            int firstIfdOffset = dataInputStream.readInt();
            if (firstIfdOffset < 8 || firstIfdOffset >= exifBytesLength) {
                throw new IOException("Invalid first Ifd offset: " + firstIfdOffset);
            }
            int firstIfdOffset2 = firstIfdOffset - 8;
            if (firstIfdOffset2 > 0 && dataInputStream.skipBytes(firstIfdOffset2) != firstIfdOffset2) {
                throw new IOException("Couldn't jump to first Ifd: " + firstIfdOffset2);
            }
            return;
        }
        throw new IOException("Invalid start code: " + Integer.toHexString(startCode));
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0146  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0150  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readImageFileDirectory(android.media.ExifInterface.ByteOrderedDataInputStream r30, int r31) throws java.io.IOException {
        /*
            r29 = this;
            r0 = r29
            r1 = r30
            r2 = r31
            java.util.Set<java.lang.Integer> r3 = r0.mHandledIfdOffsets
            int r4 = r30.mPosition
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.add(r4)
            int r3 = r30.mPosition
            r4 = 2
            int r3 = r3 + r4
            int r5 = r30.mLength
            if (r3 <= r5) goto L_0x0020
            return
        L_0x0020:
            short r3 = r30.readShort()
            int r5 = r30.mPosition
            int r6 = r3 * 12
            int r5 = r5 + r6
            int r6 = r30.mLength
            if (r5 > r6) goto L_0x040e
            if (r3 > 0) goto L_0x0037
            r22 = r3
            goto L_0x0410
        L_0x0037:
            boolean r5 = DEBUG
            if (r5 == 0) goto L_0x0052
            java.lang.String r5 = "ExifInterface"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "numberOfDirectoryEntry: "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r5, r6)
        L_0x0052:
            r5 = 0
            r6 = r5
        L_0x0054:
            r9 = 5
            r10 = 1
            r11 = 4
            if (r6 >= r3) goto L_0x0372
            int r12 = r30.readUnsignedShort()
            int r15 = r30.readUnsignedShort()
            int r14 = r30.readInt()
            int r13 = r30.peek()
            int r13 = r13 + r11
            long r7 = (long) r13
            java.util.HashMap[] r13 = sExifTagMapsForReading
            r13 = r13[r2]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r12)
            java.lang.Object r11 = r13.get(r11)
            android.media.ExifInterface$ExifTag r11 = (android.media.ExifInterface.ExifTag) r11
            boolean r13 = DEBUG
            if (r13 == 0) goto L_0x00b0
            java.lang.String r13 = "ExifInterface"
            java.lang.String r4 = "ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d"
            java.lang.Object[] r9 = new java.lang.Object[r9]
            java.lang.Integer r16 = java.lang.Integer.valueOf(r31)
            r9[r5] = r16
            java.lang.Integer r16 = java.lang.Integer.valueOf(r12)
            r9[r10] = r16
            if (r11 == 0) goto L_0x0094
            java.lang.String r10 = r11.name
            goto L_0x0095
        L_0x0094:
            r10 = 0
        L_0x0095:
            r16 = 2
            r9[r16] = r10
            java.lang.Integer r10 = java.lang.Integer.valueOf(r15)
            r16 = 3
            r9[r16] = r10
            java.lang.Integer r10 = java.lang.Integer.valueOf(r14)
            r16 = 4
            r9[r16] = r10
            java.lang.String r4 = java.lang.String.format(r4, r9)
            android.util.Log.d(r13, r4)
        L_0x00b0:
            r9 = 0
            r4 = 0
            if (r11 != 0) goto L_0x00de
            boolean r13 = DEBUG
            if (r13 == 0) goto L_0x00d7
            java.lang.String r13 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r22 = r3
            java.lang.String r3 = "Skip the tag entry since tag number is not defined: "
            r5.append(r3)
            r5.append(r12)
            java.lang.String r3 = r5.toString()
            android.util.Log.d(r13, r3)
            r23 = r4
            r24 = r9
            goto L_0x0142
        L_0x00d7:
            r22 = r3
            r23 = r4
            r24 = r9
            goto L_0x0142
        L_0x00de:
            r22 = r3
            if (r15 <= 0) goto L_0x0124
            int[] r3 = IFD_FORMAT_BYTES_PER_FORMAT
            int r3 = r3.length
            if (r15 < r3) goto L_0x00ec
            r23 = r4
            r24 = r9
            goto L_0x0128
        L_0x00ec:
            r23 = r4
            long r3 = (long) r14
            int[] r5 = IFD_FORMAT_BYTES_PER_FORMAT
            r5 = r5[r15]
            r24 = r9
            long r9 = (long) r5
            long r9 = r9 * r3
            r3 = 0
            int r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0109
            r3 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r3 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0105
            goto L_0x0109
        L_0x0105:
            r4 = 1
            r23 = r4
            goto L_0x0144
        L_0x0109:
            boolean r3 = DEBUG
            if (r3 == 0) goto L_0x0144
            java.lang.String r3 = "ExifInterface"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Skip the tag entry since the number of components is invalid: "
            r4.append(r5)
            r4.append(r14)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r3, r4)
            goto L_0x0144
        L_0x0124:
            r23 = r4
            r24 = r9
        L_0x0128:
            boolean r3 = DEBUG
            if (r3 == 0) goto L_0x0142
            java.lang.String r3 = "ExifInterface"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Skip the tag entry since data format is invalid: "
            r4.append(r5)
            r4.append(r15)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r3, r4)
        L_0x0142:
            r9 = r24
        L_0x0144:
            if (r23 != 0) goto L_0x0150
            r1.seek(r7)
            r27 = r6
        L_0x014c:
            r21 = 2
            goto L_0x0368
        L_0x0150:
            r3 = 4
            int r3 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0215
            int r3 = r30.readInt()
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0175
            java.lang.String r4 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r13 = "seek to data offset: "
            r5.append(r13)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x0175:
            int r4 = r0.mMimeType
            r5 = 7
            if (r4 != r5) goto L_0x01d4
            java.lang.String r4 = r11.name
            java.lang.String r5 = "MakerNote"
            if (r4 != r5) goto L_0x0187
            r0.mOrfMakerNoteOffset = r3
            r27 = r6
            r26 = r14
            goto L_0x01e6
        L_0x0187:
            r4 = 6
            if (r2 != r4) goto L_0x01cf
            java.lang.String r5 = r11.name
            java.lang.String r13 = "ThumbnailImage"
            if (r5 != r13) goto L_0x01cf
            r0.mOrfThumbnailOffset = r3
            r0.mOrfThumbnailLength = r14
            java.nio.ByteOrder r5 = r0.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r4 = android.media.ExifInterface.ExifAttribute.createUShort((int) r4, (java.nio.ByteOrder) r5)
            int r5 = r0.mOrfThumbnailOffset
            r26 = r14
            long r13 = (long) r5
            java.nio.ByteOrder r5 = r0.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r5 = android.media.ExifInterface.ExifAttribute.createULong((long) r13, (java.nio.ByteOrder) r5)
            int r13 = r0.mOrfThumbnailLength
            long r13 = (long) r13
            r27 = r6
            java.nio.ByteOrder r6 = r0.mExifByteOrder
            android.media.ExifInterface$ExifAttribute r6 = android.media.ExifInterface.ExifAttribute.createULong((long) r13, (java.nio.ByteOrder) r6)
            java.util.HashMap[] r13 = r0.mAttributes
            r14 = 4
            r13 = r13[r14]
            java.lang.String r14 = "Compression"
            r13.put(r14, r4)
            java.util.HashMap[] r13 = r0.mAttributes
            r14 = 4
            r13 = r13[r14]
            java.lang.String r14 = "JPEGInterchangeFormat"
            r13.put(r14, r5)
            java.util.HashMap[] r13 = r0.mAttributes
            r14 = 4
            r13 = r13[r14]
            java.lang.String r14 = "JPEGInterchangeFormatLength"
            r13.put(r14, r6)
            goto L_0x01e6
        L_0x01cf:
            r27 = r6
            r26 = r14
            goto L_0x01e6
        L_0x01d4:
            r27 = r6
            r26 = r14
            int r4 = r0.mMimeType
            r5 = 10
            if (r4 != r5) goto L_0x01e6
            java.lang.String r4 = r11.name
            java.lang.String r5 = "JpgFromRaw"
            if (r4 != r5) goto L_0x01e6
            r0.mRw2JpgFromRawOffset = r3
        L_0x01e6:
            long r4 = (long) r3
            long r4 = r4 + r9
            int r6 = r30.mLength
            long r13 = (long) r6
            int r4 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r4 > 0) goto L_0x01f6
            long r4 = (long) r3
            r1.seek(r4)
            goto L_0x0219
        L_0x01f6:
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0210
            java.lang.String r4 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Skip the tag entry since data offset is invalid: "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x0210:
            r1.seek(r7)
            goto L_0x014c
        L_0x0215:
            r27 = r6
            r26 = r14
        L_0x0219:
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r3 = sExifPointerTagMap
            java.lang.Integer r4 = java.lang.Integer.valueOf(r12)
            java.lang.Object r3 = r3.get(r4)
            java.lang.Integer r3 = (java.lang.Integer) r3
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x0248
            java.lang.String r4 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "nextIfdType: "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r6 = " byteCount: "
            r5.append(r6)
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x0248:
            if (r3 == 0) goto L_0x02fa
            r4 = -1
            switch(r15) {
                case 3: goto L_0x0261;
                case 4: goto L_0x025c;
                case 8: goto L_0x0256;
                case 9: goto L_0x0250;
                case 13: goto L_0x0250;
                default: goto L_0x024f;
            }
        L_0x024f:
            goto L_0x0267
        L_0x0250:
            int r6 = r30.readInt()
            long r4 = (long) r6
            goto L_0x0267
        L_0x0256:
            short r6 = r30.readShort()
            long r4 = (long) r6
            goto L_0x0267
        L_0x025c:
            long r4 = r30.readUnsignedInt()
            goto L_0x0267
        L_0x0261:
            int r6 = r30.readUnsignedShort()
            long r4 = (long) r6
        L_0x0267:
            boolean r6 = DEBUG
            if (r6 == 0) goto L_0x028a
            java.lang.String r6 = "ExifInterface"
            java.lang.String r13 = "Offset: %d, tagName: %s"
            r28 = r12
            r14 = 2
            java.lang.Object[] r12 = new java.lang.Object[r14]
            java.lang.Long r16 = java.lang.Long.valueOf(r4)
            r17 = 0
            r12[r17] = r16
            java.lang.String r14 = r11.name
            r16 = 1
            r12[r16] = r14
            java.lang.String r12 = java.lang.String.format(r13, r12)
            android.util.Log.d(r6, r12)
            goto L_0x028c
        L_0x028a:
            r28 = r12
        L_0x028c:
            r12 = 0
            int r6 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r6 <= 0) goto L_0x02db
            int r6 = r30.mLength
            long r12 = (long) r6
            int r6 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r6 >= 0) goto L_0x02db
            java.util.Set<java.lang.Integer> r6 = r0.mHandledIfdOffsets
            int r12 = (int) r4
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            boolean r6 = r6.contains(r12)
            if (r6 != 0) goto L_0x02b3
            r1.seek(r4)
            int r6 = r3.intValue()
            r0.readImageFileDirectory(r1, r6)
            goto L_0x02f5
        L_0x02b3:
            boolean r6 = DEBUG
            if (r6 == 0) goto L_0x02f5
            java.lang.String r6 = "ExifInterface"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Skip jump into the IFD since it has already been read: IfdType "
            r12.append(r13)
            r12.append(r3)
            java.lang.String r13 = " (at "
            r12.append(r13)
            r12.append(r4)
            java.lang.String r13 = ")"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            android.util.Log.d(r6, r12)
            goto L_0x02f5
        L_0x02db:
            boolean r6 = DEBUG
            if (r6 == 0) goto L_0x02f5
            java.lang.String r6 = "ExifInterface"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Skip jump into the IFD since its offset is invalid: "
            r12.append(r13)
            r12.append(r4)
            java.lang.String r12 = r12.toString()
            android.util.Log.d(r6, r12)
        L_0x02f5:
            r1.seek(r7)
            goto L_0x014c
        L_0x02fa:
            r28 = r12
            int r4 = r30.peek()
            int r5 = (int) r9
            byte[] r5 = new byte[r5]
            r1.readFully(r5)
            android.media.ExifInterface$ExifAttribute r6 = new android.media.ExifInterface$ExifAttribute
            long r12 = (long) r4
            r19 = 0
            r16 = r12
            r13 = r6
            r12 = r26
            r21 = 2
            r14 = r15
            r20 = r15
            r15 = r12
            r18 = r5
            r13.<init>(r14, r15, r16, r18)
            java.util.HashMap[] r13 = r0.mAttributes
            r13 = r13[r2]
            java.lang.String r14 = r11.name
            r13.put(r14, r6)
            java.lang.String r13 = r11.name
            java.lang.String r14 = "DNGVersion"
            if (r13 != r14) goto L_0x032d
            r13 = 3
            r0.mMimeType = r13
        L_0x032d:
            java.lang.String r13 = r11.name
            java.lang.String r14 = "Make"
            if (r13 == r14) goto L_0x0339
            java.lang.String r13 = r11.name
            java.lang.String r14 = "Model"
            if (r13 != r14) goto L_0x0347
        L_0x0339:
            java.nio.ByteOrder r13 = r0.mExifByteOrder
            java.lang.String r13 = r6.getStringValue(r13)
            java.lang.String r14 = "PENTAX"
            boolean r13 = r13.contains(r14)
            if (r13 != 0) goto L_0x0358
        L_0x0347:
            java.lang.String r13 = r11.name
            java.lang.String r14 = "Compression"
            if (r13 != r14) goto L_0x035c
            java.nio.ByteOrder r13 = r0.mExifByteOrder
            int r13 = r6.getIntValue(r13)
            r14 = 65535(0xffff, float:9.1834E-41)
            if (r13 != r14) goto L_0x035c
        L_0x0358:
            r13 = 8
            r0.mMimeType = r13
        L_0x035c:
            int r13 = r30.peek()
            long r13 = (long) r13
            int r13 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r13 == 0) goto L_0x0368
            r1.seek(r7)
        L_0x0368:
            int r6 = r27 + 1
            short r6 = (short) r6
            r4 = r21
            r3 = r22
            r5 = 0
            goto L_0x0054
        L_0x0372:
            r22 = r3
            int r3 = r30.peek()
            r4 = 4
            int r3 = r3 + r4
            int r4 = r30.mLength
            if (r3 > r4) goto L_0x040d
            int r3 = r30.readInt()
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x039e
            java.lang.String r4 = "ExifInterface"
            java.lang.String r5 = "nextIfdOffset: %d"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            r8 = 0
            r6[r8] = r7
            java.lang.String r5 = java.lang.String.format(r5, r6)
            android.util.Log.d(r4, r5)
        L_0x039e:
            long r4 = (long) r3
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x03f3
            int r4 = r30.mLength
            if (r3 >= r4) goto L_0x03f3
            java.util.Set<java.lang.Integer> r4 = r0.mHandledIfdOffsets
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            boolean r4 = r4.contains(r5)
            if (r4 != 0) goto L_0x03d8
            long r4 = (long) r3
            r1.seek(r4)
            java.util.HashMap[] r4 = r0.mAttributes
            r5 = 4
            r4 = r4[r5]
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x03ca
            r0.readImageFileDirectory(r1, r5)
            goto L_0x040d
        L_0x03ca:
            java.util.HashMap[] r4 = r0.mAttributes
            r4 = r4[r9]
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x040d
            r0.readImageFileDirectory(r1, r9)
            goto L_0x040d
        L_0x03d8:
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x040d
            java.lang.String r4 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Stop reading file since re-reading an IFD may cause an infinite loop: "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
            goto L_0x040d
        L_0x03f3:
            boolean r4 = DEBUG
            if (r4 == 0) goto L_0x040d
            java.lang.String r4 = "ExifInterface"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Stop reading file since a wrong offset may cause an infinite loop: "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x040d:
            return
        L_0x040e:
            r22 = r3
        L_0x0410:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.readImageFileDirectory(android.media.ExifInterface$ByteOrderedDataInputStream, int):void");
    }

    private void retrieveJpegImageSize(ByteOrderedDataInputStream in, int imageType) throws IOException {
        ExifAttribute jpegInterchangeFormatAttribute;
        ExifAttribute imageLengthAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_IMAGE_LENGTH);
        ExifAttribute imageWidthAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_IMAGE_WIDTH);
        if ((imageLengthAttribute == null || imageWidthAttribute == null) && (jpegInterchangeFormatAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_JPEG_INTERCHANGE_FORMAT)) != null) {
            getJpegAttributes(in, jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder), imageType);
        }
    }

    private void setThumbnailData(ByteOrderedDataInputStream in) throws IOException {
        HashMap thumbnailData = this.mAttributes[4];
        ExifAttribute compressionAttribute = (ExifAttribute) thumbnailData.get(TAG_COMPRESSION);
        if (compressionAttribute != null) {
            this.mThumbnailCompression = compressionAttribute.getIntValue(this.mExifByteOrder);
            int i = this.mThumbnailCompression;
            if (i != 1) {
                switch (i) {
                    case 6:
                        handleThumbnailFromJfif(in, thumbnailData);
                        return;
                    case 7:
                        break;
                    default:
                        return;
                }
            }
            if (isSupportedDataType(thumbnailData)) {
                handleThumbnailFromStrips(in, thumbnailData);
                return;
            }
            return;
        }
        handleThumbnailFromJfif(in, thumbnailData);
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream in, HashMap thumbnailData) throws IOException {
        ExifAttribute jpegInterchangeFormatAttribute = (ExifAttribute) thumbnailData.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute jpegInterchangeFormatLengthAttribute = (ExifAttribute) thumbnailData.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (jpegInterchangeFormatAttribute != null && jpegInterchangeFormatLengthAttribute != null) {
            int thumbnailOffset = jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder);
            int thumbnailLength = Math.min(jpegInterchangeFormatLengthAttribute.getIntValue(this.mExifByteOrder), in.getLength() - thumbnailOffset);
            if (this.mMimeType == 4 || this.mMimeType == 9 || this.mMimeType == 10) {
                thumbnailOffset += this.mExifOffset;
            } else if (this.mMimeType == 7) {
                thumbnailOffset += this.mOrfMakerNoteOffset;
            }
            if (DEBUG) {
                Log.d(TAG, "Setting thumbnail attributes with offset: " + thumbnailOffset + ", length: " + thumbnailLength);
            }
            if (thumbnailOffset > 0 && thumbnailLength > 0) {
                this.mHasThumbnail = true;
                this.mThumbnailOffset = thumbnailOffset;
                this.mThumbnailLength = thumbnailLength;
                this.mThumbnailCompression = 6;
                if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                    byte[] thumbnailBytes = new byte[thumbnailLength];
                    in.seek((long) thumbnailOffset);
                    in.readFully(thumbnailBytes);
                    this.mThumbnailBytes = thumbnailBytes;
                }
            }
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream in, HashMap thumbnailData) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        HashMap hashMap = thumbnailData;
        ExifAttribute stripOffsetsAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_OFFSETS);
        ExifAttribute stripByteCountsAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (stripOffsetsAttribute == null || stripByteCountsAttribute == null) {
            return;
        }
        long[] stripOffsets = convertToLongArray(stripOffsetsAttribute.getValue(this.mExifByteOrder));
        long[] stripByteCounts = convertToLongArray(stripByteCountsAttribute.getValue(this.mExifByteOrder));
        if (stripOffsets == null) {
            Log.w(TAG, "stripOffsets should not be null.");
        } else if (stripByteCounts == null) {
            Log.w(TAG, "stripByteCounts should not be null.");
        } else {
            byte[] totalStripBytes = new byte[((int) Arrays.stream(stripByteCounts).sum())];
            int bytesAdded = 0;
            int bytesRead = 0;
            int i = 0;
            while (i < stripOffsets.length) {
                int stripByteCount = (int) stripByteCounts[i];
                int skipBytes = ((int) stripOffsets[i]) - bytesRead;
                if (skipBytes < 0) {
                    Log.d(TAG, "Invalid strip offset value");
                }
                byteOrderedDataInputStream.seek((long) skipBytes);
                byte[] stripBytes = new byte[stripByteCount];
                byteOrderedDataInputStream.read(stripBytes);
                bytesRead = bytesRead + skipBytes + stripByteCount;
                System.arraycopy(stripBytes, 0, totalStripBytes, bytesAdded, stripBytes.length);
                bytesAdded += stripBytes.length;
                i++;
                stripOffsetsAttribute = stripOffsetsAttribute;
                HashMap hashMap2 = thumbnailData;
            }
            this.mHasThumbnail = true;
            this.mThumbnailBytes = totalStripBytes;
            this.mThumbnailLength = totalStripBytes.length;
        }
    }

    private boolean isSupportedDataType(HashMap thumbnailData) throws IOException {
        ExifAttribute photometricInterpretationAttribute;
        int photometricInterpretationValue;
        ExifAttribute bitsPerSampleAttribute = (ExifAttribute) thumbnailData.get(TAG_BITS_PER_SAMPLE);
        if (bitsPerSampleAttribute != null) {
            int[] bitsPerSampleValue = (int[]) bitsPerSampleAttribute.getValue(this.mExifByteOrder);
            if (Arrays.equals(BITS_PER_SAMPLE_RGB, bitsPerSampleValue)) {
                return true;
            }
            if (this.mMimeType == 3 && (photometricInterpretationAttribute = (ExifAttribute) thumbnailData.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((photometricInterpretationValue = photometricInterpretationAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(bitsPerSampleValue, BITS_PER_SAMPLE_GREYSCALE_2)) || (photometricInterpretationValue == 6 && Arrays.equals(bitsPerSampleValue, BITS_PER_SAMPLE_RGB)))) {
                return true;
            }
        }
        if (!DEBUG) {
            return false;
        }
        Log.d(TAG, "Unsupported data type value");
        return false;
    }

    private boolean isThumbnail(HashMap map) throws IOException {
        ExifAttribute imageLengthAttribute = (ExifAttribute) map.get(TAG_IMAGE_LENGTH);
        ExifAttribute imageWidthAttribute = (ExifAttribute) map.get(TAG_IMAGE_WIDTH);
        if (imageLengthAttribute == null || imageWidthAttribute == null) {
            return false;
        }
        int imageLengthValue = imageLengthAttribute.getIntValue(this.mExifByteOrder);
        int imageWidthValue = imageWidthAttribute.getIntValue(this.mExifByteOrder);
        if (imageLengthValue > 512 || imageWidthValue > 512) {
            return false;
        }
        return true;
    }

    private void validateImages(InputStream in) throws IOException {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute pixelXDimAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute pixelYDimAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (!(pixelXDimAttribute == null || pixelYDimAttribute == null)) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, pixelXDimAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, pixelYDimAttribute);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            this.mAttributes[4] = this.mAttributes[5];
            this.mAttributes[5] = new HashMap();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
    }

    private void updateImageSizeValues(ByteOrderedDataInputStream in, int imageType) throws IOException {
        ExifAttribute defaultCropSizeXAttribute;
        ExifAttribute defaultCropSizeYAttribute;
        ExifAttribute defaultCropSizeAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute topBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute leftBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute bottomBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute rightBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (defaultCropSizeAttribute != null) {
            if (defaultCropSizeAttribute.format == 5) {
                Rational[] defaultCropSizeValue = (Rational[]) defaultCropSizeAttribute.getValue(this.mExifByteOrder);
                defaultCropSizeXAttribute = ExifAttribute.createURational(defaultCropSizeValue[0], this.mExifByteOrder);
                defaultCropSizeYAttribute = ExifAttribute.createURational(defaultCropSizeValue[1], this.mExifByteOrder);
            } else {
                int[] defaultCropSizeValue2 = (int[]) defaultCropSizeAttribute.getValue(this.mExifByteOrder);
                defaultCropSizeXAttribute = ExifAttribute.createUShort(defaultCropSizeValue2[0], this.mExifByteOrder);
                defaultCropSizeYAttribute = ExifAttribute.createUShort(defaultCropSizeValue2[1], this.mExifByteOrder);
            }
            this.mAttributes[imageType].put(TAG_IMAGE_WIDTH, defaultCropSizeXAttribute);
            this.mAttributes[imageType].put(TAG_IMAGE_LENGTH, defaultCropSizeYAttribute);
            ExifAttribute exifAttribute = defaultCropSizeAttribute;
        } else if (topBorderAttribute == null || leftBorderAttribute == null || bottomBorderAttribute == null || rightBorderAttribute == null) {
            retrieveJpegImageSize(in, imageType);
        } else {
            int topBorderValue = topBorderAttribute.getIntValue(this.mExifByteOrder);
            int bottomBorderValue = bottomBorderAttribute.getIntValue(this.mExifByteOrder);
            int rightBorderValue = rightBorderAttribute.getIntValue(this.mExifByteOrder);
            int leftBorderValue = leftBorderAttribute.getIntValue(this.mExifByteOrder);
            if (bottomBorderValue <= topBorderValue || rightBorderValue <= leftBorderValue) {
                return;
            }
            ExifAttribute imageLengthAttribute = ExifAttribute.createUShort(bottomBorderValue - topBorderValue, this.mExifByteOrder);
            ExifAttribute imageWidthAttribute = ExifAttribute.createUShort(rightBorderValue - leftBorderValue, this.mExifByteOrder);
            ExifAttribute exifAttribute2 = defaultCropSizeAttribute;
            this.mAttributes[imageType].put(TAG_IMAGE_LENGTH, imageLengthAttribute);
            this.mAttributes[imageType].put(TAG_IMAGE_WIDTH, imageWidthAttribute);
        }
    }

    private int writeExifSegment(ByteOrderedDataOutputStream dataOutputStream, int exifOffsetFromBeginning) throws IOException {
        int position;
        int position2;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = dataOutputStream;
        int[] ifdOffsets = new int[EXIF_TAGS.length];
        int[] ifdDataSizes = new int[EXIF_TAGS.length];
        for (ExifTag tag : EXIF_POINTER_TAGS) {
            removeAttribute(tag.name);
        }
        removeAttribute(JPEG_INTERCHANGE_FORMAT_TAG.name);
        removeAttribute(JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name);
        for (int ifdType = 0; ifdType < EXIF_TAGS.length; ifdType++) {
            for (Object obj : this.mAttributes[ifdType].entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[ifdType].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        int i = 2;
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        int i2 = 4;
        if (this.mHasThumbnail) {
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong(0, this.mExifByteOrder));
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name, ExifAttribute.createULong((long) this.mThumbnailLength, this.mExifByteOrder));
        }
        for (int i3 = 0; i3 < EXIF_TAGS.length; i3++) {
            int sum = 0;
            for (Map.Entry entry2 : this.mAttributes[i3].entrySet()) {
                int size = ((ExifAttribute) entry2.getValue()).size();
                if (size > 4) {
                    sum += size;
                }
            }
            ifdDataSizes[i3] = ifdDataSizes[i3] + sum;
        }
        int position3 = 8;
        for (int ifdType2 = 0; ifdType2 < EXIF_TAGS.length; ifdType2++) {
            if (!this.mAttributes[ifdType2].isEmpty()) {
                ifdOffsets[ifdType2] = position3;
                position3 += (this.mAttributes[ifdType2].size() * 12) + 2 + 4 + ifdDataSizes[ifdType2];
            }
        }
        if (this.mHasThumbnail != 0) {
            int thumbnailOffset = position3;
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong((long) thumbnailOffset, this.mExifByteOrder));
            this.mThumbnailOffset = exifOffsetFromBeginning + thumbnailOffset;
            position3 += this.mThumbnailLength;
        }
        int totalSize = position3 + 8;
        if (DEBUG) {
            Log.d(TAG, "totalSize length: " + totalSize);
            int i4 = 0;
            while (i4 < EXIF_TAGS.length) {
                Object[] objArr = new Object[i2];
                objArr[0] = Integer.valueOf(i4);
                objArr[1] = Integer.valueOf(ifdOffsets[i4]);
                objArr[2] = Integer.valueOf(this.mAttributes[i4].size());
                objArr[3] = Integer.valueOf(ifdDataSizes[i4]);
                Log.d(TAG, String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d", objArr));
                i4++;
                i2 = 4;
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong((long) ifdOffsets[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong((long) ifdOffsets[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong((long) ifdOffsets[3], this.mExifByteOrder));
        }
        byteOrderedDataOutputStream.writeUnsignedShort(totalSize);
        byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? BYTE_ALIGN_MM : BYTE_ALIGN_II);
        byteOrderedDataOutputStream.setByteOrder(this.mExifByteOrder);
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8);
        int ifdType3 = 0;
        while (ifdType3 < EXIF_TAGS.length) {
            if (!this.mAttributes[ifdType3].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[ifdType3].size());
                int dataOffset = ifdOffsets[ifdType3] + i + (this.mAttributes[ifdType3].size() * 12) + 4;
                for (Map.Entry entry3 : this.mAttributes[ifdType3].entrySet()) {
                    int tagNumber = ((ExifTag) sExifTagMapsForWriting[ifdType3].get(entry3.getKey())).number;
                    ExifAttribute attribute = (ExifAttribute) entry3.getValue();
                    int size2 = attribute.size();
                    byteOrderedDataOutputStream.writeUnsignedShort(tagNumber);
                    byteOrderedDataOutputStream.writeUnsignedShort(attribute.format);
                    byteOrderedDataOutputStream.writeInt(attribute.numberOfComponents);
                    if (size2 > 4) {
                        position2 = position3;
                        byteOrderedDataOutputStream.writeUnsignedInt((long) dataOffset);
                        dataOffset += size2;
                    } else {
                        position2 = position3;
                        byteOrderedDataOutputStream.write(attribute.bytes);
                        if (size2 < 4) {
                            int i5 = size2;
                            for (int i6 = 4; i5 < i6; i6 = 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                i5++;
                            }
                        }
                    }
                    position3 = position2;
                }
                position = position3;
                if (ifdType3 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(0);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt((long) ifdOffsets[4]);
                }
                for (Map.Entry entry4 : this.mAttributes[ifdType3].entrySet()) {
                    ExifAttribute attribute2 = (ExifAttribute) entry4.getValue();
                    if (attribute2.bytes.length > 4) {
                        byteOrderedDataOutputStream.write(attribute2.bytes, 0, attribute2.bytes.length);
                    }
                }
            } else {
                position = position3;
            }
            ifdType3++;
            position3 = position;
            i = 2;
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream.write(getThumbnailBytes());
        }
        byteOrderedDataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        return totalSize;
    }

    private static Pair<Integer, Integer> guessDataFormat(String entryValue) {
        if (entryValue.contains(SmsManager.REGEX_PREFIX_DELIMITER)) {
            String[] entryValues = entryValue.split(SmsManager.REGEX_PREFIX_DELIMITER);
            Pair<Integer, Integer> dataFormat = guessDataFormat(entryValues[0]);
            if (((Integer) dataFormat.first).intValue() == 2) {
                return dataFormat;
            }
            for (int i = 1; i < entryValues.length; i++) {
                Pair<Integer, Integer> guessDataFormat = guessDataFormat(entryValues[i]);
                int first = -1;
                int second = -1;
                if (guessDataFormat.first == dataFormat.first || guessDataFormat.second == dataFormat.first) {
                    first = ((Integer) dataFormat.first).intValue();
                }
                if (((Integer) dataFormat.second).intValue() != -1 && (guessDataFormat.first == dataFormat.second || guessDataFormat.second == dataFormat.second)) {
                    second = ((Integer) dataFormat.second).intValue();
                }
                if (first == -1 && second == -1) {
                    return new Pair<>(2, -1);
                }
                if (first == -1) {
                    dataFormat = new Pair<>(Integer.valueOf(second), -1);
                } else if (second == -1) {
                    dataFormat = new Pair<>(Integer.valueOf(first), -1);
                }
            }
            return dataFormat;
        } else if (entryValue.contains("/")) {
            String[] rationalNumber = entryValue.split("/");
            if (rationalNumber.length == 2) {
                try {
                    long numerator = (long) Double.parseDouble(rationalNumber[0]);
                    long denominator = (long) Double.parseDouble(rationalNumber[1]);
                    if (numerator >= 0) {
                        if (denominator >= 0) {
                            if (numerator <= 2147483647L) {
                                if (denominator <= 2147483647L) {
                                    return new Pair<>(10, 5);
                                }
                            }
                            return new Pair<>(5, -1);
                        }
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException e) {
                }
            }
            return new Pair<>(2, -1);
        } else {
            try {
                Long longValue = Long.valueOf(Long.parseLong(entryValue));
                if (longValue.longValue() >= 0 && longValue.longValue() <= 65535) {
                    return new Pair<>(3, 4);
                }
                if (longValue.longValue() < 0) {
                    return new Pair<>(9, -1);
                }
                return new Pair<>(4, -1);
            } catch (NumberFormatException e2) {
                try {
                    Double.parseDouble(entryValue);
                    return new Pair<>(12, -1);
                } catch (NumberFormatException e3) {
                    return new Pair<>(2, -1);
                }
            }
        }
    }

    private static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
        private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        private ByteOrder mByteOrder;
        private DataInputStream mDataInputStream;
        private InputStream mInputStream;
        /* access modifiers changed from: private */
        public final int mLength;
        /* access modifiers changed from: private */
        public int mPosition;

        public ByteOrderedDataInputStream(InputStream in) throws IOException {
            this.mByteOrder = ByteOrder.BIG_ENDIAN;
            this.mInputStream = in;
            this.mDataInputStream = new DataInputStream(in);
            this.mLength = this.mDataInputStream.available();
            this.mPosition = 0;
            this.mDataInputStream.mark(this.mLength);
        }

        public ByteOrderedDataInputStream(byte[] bytes) throws IOException {
            this((InputStream) new ByteArrayInputStream(bytes));
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void seek(long byteCount) throws IOException {
            if (((long) this.mPosition) > byteCount) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                byteCount -= (long) this.mPosition;
            }
            if (skipBytes((int) byteCount) != ((int) byteCount)) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        public int peek() {
            return this.mPosition;
        }

        public int available() throws IOException {
            return this.mDataInputStream.available();
        }

        public int read() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        public int readUnsignedByte() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        public String readLine() throws IOException {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        public boolean readBoolean() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        public char readChar() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        public String readUTF() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        public void readFully(byte[] buffer, int offset, int length) throws IOException {
            this.mPosition += length;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            } else if (this.mDataInputStream.read(buffer, offset, length) != length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public void readFully(byte[] buffer) throws IOException {
            this.mPosition += buffer.length;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            } else if (this.mDataInputStream.read(buffer, 0, buffer.length) != buffer.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public byte readByte() throws IOException {
            this.mPosition++;
            if (this.mPosition <= this.mLength) {
                int ch = this.mDataInputStream.read();
                if (ch >= 0) {
                    return (byte) ch;
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public short readShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                if ((ch1 | ch2) < 0) {
                    throw new EOFException();
                } else if (this.mByteOrder == LITTLE_ENDIAN) {
                    return (short) ((ch2 << 8) + ch1);
                } else {
                    if (this.mByteOrder == BIG_ENDIAN) {
                        return (short) ((ch1 << 8) + ch2);
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
            } else {
                throw new EOFException();
            }
        }

        public int readInt() throws IOException {
            this.mPosition += 4;
            if (this.mPosition <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                int ch3 = this.mDataInputStream.read();
                int ch4 = this.mDataInputStream.read();
                if ((ch1 | ch2 | ch3 | ch4) < 0) {
                    throw new EOFException();
                } else if (this.mByteOrder == LITTLE_ENDIAN) {
                    return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
                } else {
                    if (this.mByteOrder == BIG_ENDIAN) {
                        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
            } else {
                throw new EOFException();
            }
        }

        public int skipBytes(int byteCount) throws IOException {
            int totalSkip = Math.min(byteCount, this.mLength - this.mPosition);
            int skipped = 0;
            while (skipped < totalSkip) {
                skipped += this.mDataInputStream.skipBytes(totalSkip - skipped);
            }
            this.mPosition += skipped;
            return skipped;
        }

        public int readUnsignedShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                if ((ch1 | ch2) < 0) {
                    throw new EOFException();
                } else if (this.mByteOrder == LITTLE_ENDIAN) {
                    return (ch2 << 8) + ch1;
                } else {
                    if (this.mByteOrder == BIG_ENDIAN) {
                        return (ch1 << 8) + ch2;
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
            } else {
                throw new EOFException();
            }
        }

        public long readUnsignedInt() throws IOException {
            return ((long) readInt()) & 4294967295L;
        }

        public long readLong() throws IOException {
            this.mPosition += 8;
            if (this.mPosition <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                int ch3 = this.mDataInputStream.read();
                int ch4 = this.mDataInputStream.read();
                int ch5 = this.mDataInputStream.read();
                int ch6 = this.mDataInputStream.read();
                int ch7 = this.mDataInputStream.read();
                int ch8 = this.mDataInputStream.read();
                if ((ch1 | ch2 | ch3 | ch4 | ch5 | ch6 | ch7 | ch8) < 0) {
                    throw new EOFException();
                } else if (this.mByteOrder == LITTLE_ENDIAN) {
                    return (((long) ch8) << 56) + (((long) ch7) << 48) + (((long) ch6) << 40) + (((long) ch5) << 32) + (((long) ch4) << 24) + (((long) ch3) << 16) + (((long) ch2) << 8) + ((long) ch1);
                } else {
                    int ch22 = ch2;
                    if (this.mByteOrder == BIG_ENDIAN) {
                        return (((long) ch1) << 56) + (((long) ch22) << 48) + (((long) ch3) << 40) + (((long) ch4) << 32) + (((long) ch5) << 24) + (((long) ch6) << 16) + (((long) ch7) << 8) + ((long) ch8);
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
            } else {
                throw new EOFException();
            }
        }

        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        public int getLength() {
            return this.mLength;
        }
    }

    private static class ByteOrderedDataOutputStream extends FilterOutputStream {
        private ByteOrder mByteOrder;
        private final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream out, ByteOrder byteOrder) {
            super(out);
            this.mOutputStream = out;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void write(byte[] bytes) throws IOException {
            this.mOutputStream.write(bytes);
        }

        public void write(byte[] bytes, int offset, int length) throws IOException {
            this.mOutputStream.write(bytes, offset, length);
        }

        public void writeByte(int val) throws IOException {
            this.mOutputStream.write(val);
        }

        public void writeShort(short val) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((val >>> 0) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 0) & 255);
            }
        }

        public void writeInt(int val) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((val >>> 0) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 16) & 255);
                this.mOutputStream.write((val >>> 24) & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((val >>> 24) & 255);
                this.mOutputStream.write((val >>> 16) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 0) & 255);
            }
        }

        public void writeUnsignedShort(int val) throws IOException {
            writeShort((short) val);
        }

        public void writeUnsignedInt(long val) throws IOException {
            writeInt((int) val);
        }
    }

    private void swapBasedOnImageSize(int firstIfdType, int secondIfdType) throws IOException {
        if (!this.mAttributes[firstIfdType].isEmpty() && !this.mAttributes[secondIfdType].isEmpty()) {
            ExifAttribute firstImageLengthAttribute = (ExifAttribute) this.mAttributes[firstIfdType].get(TAG_IMAGE_LENGTH);
            ExifAttribute firstImageWidthAttribute = (ExifAttribute) this.mAttributes[firstIfdType].get(TAG_IMAGE_WIDTH);
            ExifAttribute secondImageLengthAttribute = (ExifAttribute) this.mAttributes[secondIfdType].get(TAG_IMAGE_LENGTH);
            ExifAttribute secondImageWidthAttribute = (ExifAttribute) this.mAttributes[secondIfdType].get(TAG_IMAGE_WIDTH);
            if (firstImageLengthAttribute == null || firstImageWidthAttribute == null) {
                if (DEBUG) {
                    Log.d(TAG, "First image does not contain valid size information");
                }
            } else if (secondImageLengthAttribute != null && secondImageWidthAttribute != null) {
                int firstImageLengthValue = firstImageLengthAttribute.getIntValue(this.mExifByteOrder);
                int firstImageWidthValue = firstImageWidthAttribute.getIntValue(this.mExifByteOrder);
                int secondImageLengthValue = secondImageLengthAttribute.getIntValue(this.mExifByteOrder);
                int secondImageWidthValue = secondImageWidthAttribute.getIntValue(this.mExifByteOrder);
                if (firstImageLengthValue < secondImageLengthValue && firstImageWidthValue < secondImageWidthValue) {
                    HashMap tempMap = this.mAttributes[firstIfdType];
                    this.mAttributes[firstIfdType] = this.mAttributes[secondIfdType];
                    this.mAttributes[secondIfdType] = tempMap;
                }
            } else if (DEBUG != 0) {
                Log.d(TAG, "Second image does not contain valid size information");
            }
        } else if (DEBUG) {
            Log.d(TAG, "Cannot perform swap since only one image data exists");
        }
    }

    private boolean containsMatch(byte[] mainBytes, byte[] findBytes) {
        int i = 0;
        while (i < mainBytes.length - findBytes.length) {
            int j = 0;
            while (j < findBytes.length && mainBytes[i + j] == findBytes[j]) {
                if (j == findBytes.length - 1) {
                    return true;
                }
                j++;
            }
            i++;
        }
        return false;
    }

    private static long[] convertToLongArray(Object inputObj) {
        if (inputObj instanceof int[]) {
            int[] input = (int[]) inputObj;
            long[] result = new long[input.length];
            for (int i = 0; i < input.length; i++) {
                result[i] = (long) input[i];
            }
            return result;
        } else if (inputObj instanceof long[]) {
            return (long[]) inputObj;
        } else {
            return null;
        }
    }
}
