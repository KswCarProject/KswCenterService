package android.media;

import android.annotation.UnsupportedAppUsage;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.media.MediaCodecInfo;
import android.media.MediaPlayer;
import android.mtp.MtpConstants;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.p007os.SystemProperties;
import android.p007os.UserHandle;
import android.p007os.health.HealthKeys;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.telephony.SmsManager;
import android.text.Spanned;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.view.SurfaceControl;
import android.view.autofill.AutofillManager;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.Protocol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/* loaded from: classes3.dex */
public final class MediaCodecInfo {
    private static final int DEFAULT_MAX_SUPPORTED_INSTANCES = 32;
    private static final int ERROR_NONE_SUPPORTED = 4;
    private static final int ERROR_UNRECOGNIZED = 1;
    private static final int ERROR_UNSUPPORTED = 2;
    private static final int FLAG_IS_ENCODER = 1;
    private static final int FLAG_IS_HARDWARE_ACCELERATED = 8;
    private static final int FLAG_IS_SOFTWARE_ONLY = 4;
    private static final int FLAG_IS_VENDOR = 2;
    private static final int MAX_SUPPORTED_INSTANCES_LIMIT = 256;
    private static final String TAG = "MediaCodecInfo";
    private String mCanonicalName;
    private Map<String, CodecCapabilities> mCaps = new HashMap();
    private int mFlags;
    private String mName;
    private static final Range<Integer> POSITIVE_INTEGERS = Range.create(1, Integer.MAX_VALUE);
    private static final Range<Long> POSITIVE_LONGS = Range.create(1L, Long.MAX_VALUE);
    private static final Range<Rational> POSITIVE_RATIONALS = Range.create(new Rational(1, Integer.MAX_VALUE), new Rational(Integer.MAX_VALUE, 1));
    private static final Range<Integer> SIZE_RANGE = Range.create(1, 32768);
    private static final Range<Integer> FRAME_RATE_RANGE = Range.create(0, 960);
    private static final Range<Integer> BITRATE_RANGE = Range.create(0, 500000000);

    MediaCodecInfo(String name, String canonicalName, int flags, CodecCapabilities[] caps) {
        this.mName = name;
        this.mCanonicalName = canonicalName;
        this.mFlags = flags;
        for (CodecCapabilities c : caps) {
            this.mCaps.put(c.getMimeType(), c);
        }
    }

    public final String getName() {
        return this.mName;
    }

    public final String getCanonicalName() {
        return this.mCanonicalName;
    }

    public final boolean isAlias() {
        return !this.mName.equals(this.mCanonicalName);
    }

    public final boolean isEncoder() {
        return (this.mFlags & 1) != 0;
    }

    public final boolean isVendor() {
        return (this.mFlags & 2) != 0;
    }

    public final boolean isSoftwareOnly() {
        return (this.mFlags & 4) != 0;
    }

    public final boolean isHardwareAccelerated() {
        return (this.mFlags & 8) != 0;
    }

    public final String[] getSupportedTypes() {
        Set<String> typeSet = this.mCaps.keySet();
        String[] types = (String[]) typeSet.toArray(new String[typeSet.size()]);
        Arrays.sort(types);
        return types;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPowerOfTwo(int value, String message) {
        if (((value - 1) & value) != 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /* loaded from: classes3.dex */
    private static class Feature {
        public boolean mDefault;
        public String mName;
        public int mValue;

        public Feature(String name, int value, boolean def) {
            this.mName = name;
            this.mValue = value;
            this.mDefault = def;
        }
    }

    /* loaded from: classes3.dex */
    public static final class CodecCapabilities {
        public static final int COLOR_Format12bitRGB444 = 3;
        public static final int COLOR_Format16bitARGB1555 = 5;
        public static final int COLOR_Format16bitARGB4444 = 4;
        public static final int COLOR_Format16bitBGR565 = 7;
        public static final int COLOR_Format16bitRGB565 = 6;
        public static final int COLOR_Format18BitBGR666 = 41;
        public static final int COLOR_Format18bitARGB1665 = 9;
        public static final int COLOR_Format18bitRGB666 = 8;
        public static final int COLOR_Format19bitARGB1666 = 10;
        public static final int COLOR_Format24BitABGR6666 = 43;
        public static final int COLOR_Format24BitARGB6666 = 42;
        public static final int COLOR_Format24bitARGB1887 = 13;
        public static final int COLOR_Format24bitBGR888 = 12;
        public static final int COLOR_Format24bitRGB888 = 11;
        public static final int COLOR_Format25bitARGB1888 = 14;
        public static final int COLOR_Format32bitABGR8888 = 2130747392;
        public static final int COLOR_Format32bitARGB8888 = 16;
        public static final int COLOR_Format32bitBGRA8888 = 15;
        public static final int COLOR_Format8bitRGB332 = 2;
        public static final int COLOR_FormatCbYCrY = 27;
        public static final int COLOR_FormatCrYCbY = 28;
        public static final int COLOR_FormatL16 = 36;
        public static final int COLOR_FormatL2 = 33;
        public static final int COLOR_FormatL24 = 37;
        public static final int COLOR_FormatL32 = 38;
        public static final int COLOR_FormatL4 = 34;
        public static final int COLOR_FormatL8 = 35;
        public static final int COLOR_FormatMonochrome = 1;
        public static final int COLOR_FormatRGBAFlexible = 2134288520;
        public static final int COLOR_FormatRGBFlexible = 2134292616;
        public static final int COLOR_FormatRawBayer10bit = 31;
        public static final int COLOR_FormatRawBayer8bit = 30;
        public static final int COLOR_FormatRawBayer8bitcompressed = 32;
        public static final int COLOR_FormatSurface = 2130708361;
        public static final int COLOR_FormatYCbYCr = 25;
        public static final int COLOR_FormatYCrYCb = 26;
        public static final int COLOR_FormatYUV411PackedPlanar = 18;
        public static final int COLOR_FormatYUV411Planar = 17;
        public static final int COLOR_FormatYUV420Flexible = 2135033992;
        public static final int COLOR_FormatYUV420PackedPlanar = 20;
        public static final int COLOR_FormatYUV420PackedSemiPlanar = 39;
        public static final int COLOR_FormatYUV420Planar = 19;
        public static final int COLOR_FormatYUV420SemiPlanar = 21;
        public static final int COLOR_FormatYUV422Flexible = 2135042184;
        public static final int COLOR_FormatYUV422PackedPlanar = 23;
        public static final int COLOR_FormatYUV422PackedSemiPlanar = 40;
        public static final int COLOR_FormatYUV422Planar = 22;
        public static final int COLOR_FormatYUV422SemiPlanar = 24;
        public static final int COLOR_FormatYUV444Flexible = 2135181448;
        public static final int COLOR_FormatYUV444Interleaved = 29;
        public static final int COLOR_QCOM_FormatYUV420SemiPlanar = 2141391872;
        public static final int COLOR_TI_FormatYUV420PackedSemiPlanar = 2130706688;
        private static final String TAG = "CodecCapabilities";
        public int[] colorFormats;
        private AudioCapabilities mAudioCaps;
        private MediaFormat mCapabilitiesInfo;
        private MediaFormat mDefaultFormat;
        private EncoderCapabilities mEncoderCaps;
        int mError;
        private int mFlagsRequired;
        private int mFlagsSupported;
        private int mFlagsVerified;
        private int mMaxSupportedInstances;
        private String mMime;
        private VideoCapabilities mVideoCaps;
        public CodecProfileLevel[] profileLevels;
        public static final String FEATURE_AdaptivePlayback = "adaptive-playback";
        public static final String FEATURE_SecurePlayback = "secure-playback";
        public static final String FEATURE_TunneledPlayback = "tunneled-playback";
        public static final String FEATURE_PartialFrame = "partial-frame";
        public static final String FEATURE_FrameParsing = "frame-parsing";
        public static final String FEATURE_MultipleFrames = "multiple-frames";
        public static final String FEATURE_DynamicTimestamp = "dynamic-timestamp";
        private static final Feature[] decoderFeatures = {new Feature(FEATURE_AdaptivePlayback, 1, true), new Feature(FEATURE_SecurePlayback, 2, false), new Feature(FEATURE_TunneledPlayback, 4, false), new Feature(FEATURE_PartialFrame, 8, false), new Feature(FEATURE_FrameParsing, 16, false), new Feature(FEATURE_MultipleFrames, 32, false), new Feature(FEATURE_DynamicTimestamp, 64, false)};
        public static final String FEATURE_IntraRefresh = "intra-refresh";
        private static final Feature[] encoderFeatures = {new Feature(FEATURE_IntraRefresh, 1, false), new Feature(FEATURE_MultipleFrames, 2, false), new Feature(FEATURE_DynamicTimestamp, 4, false)};

        public CodecCapabilities() {
        }

        public final boolean isFeatureSupported(String name) {
            return checkFeature(name, this.mFlagsSupported);
        }

        public final boolean isFeatureRequired(String name) {
            return checkFeature(name, this.mFlagsRequired);
        }

        public String[] validFeatures() {
            Feature[] features = getValidFeatures();
            String[] res = new String[features.length];
            for (int i = 0; i < res.length; i++) {
                res[i] = features[i].mName;
            }
            return res;
        }

        private Feature[] getValidFeatures() {
            if (!isEncoder()) {
                return decoderFeatures;
            }
            return encoderFeatures;
        }

        private boolean checkFeature(String name, int flags) {
            Feature[] validFeatures;
            for (Feature feat : getValidFeatures()) {
                if (feat.mName.equals(name)) {
                    return (feat.mValue & flags) != 0;
                }
            }
            return false;
        }

        public boolean isRegular() {
            Feature[] validFeatures;
            for (Feature feat : getValidFeatures()) {
                if (!feat.mDefault && isFeatureRequired(feat.mName)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isFormatSupported(MediaFormat format) {
            Feature[] validFeatures;
            CodecProfileLevel[] codecProfileLevelArr;
            Map<String, Object> map = format.getMap();
            String mime = (String) map.get(MediaFormat.KEY_MIME);
            if (mime != null && !this.mMime.equalsIgnoreCase(mime)) {
                return false;
            }
            for (Feature feat : getValidFeatures()) {
                Integer yesNo = (Integer) map.get(MediaFormat.KEY_FEATURE_ + feat.mName);
                if (yesNo != null && ((yesNo.intValue() == 1 && !isFeatureSupported(feat.mName)) || (yesNo.intValue() == 0 && isFeatureRequired(feat.mName)))) {
                    return false;
                }
            }
            Integer profile = (Integer) map.get(MediaFormat.KEY_PROFILE);
            Integer level = (Integer) map.get("level");
            if (profile != null) {
                if (!supportsProfileLevel(profile.intValue(), level)) {
                    return false;
                }
                int maxLevel = 0;
                for (CodecProfileLevel pl : this.profileLevels) {
                    if (pl.profile == profile.intValue() && pl.level > maxLevel) {
                        maxLevel = pl.level;
                    }
                }
                CodecCapabilities levelCaps = createFromProfileLevel(this.mMime, profile.intValue(), maxLevel);
                Map<String, Object> mapWithoutProfile = new HashMap<>(map);
                mapWithoutProfile.remove(MediaFormat.KEY_PROFILE);
                MediaFormat formatWithoutProfile = new MediaFormat(mapWithoutProfile);
                if (levelCaps != null && !levelCaps.isFormatSupported(formatWithoutProfile)) {
                    return false;
                }
            }
            if (this.mAudioCaps != null && !this.mAudioCaps.supportsFormat(format)) {
                return false;
            }
            if (this.mVideoCaps == null || this.mVideoCaps.supportsFormat(format)) {
                return this.mEncoderCaps == null || this.mEncoderCaps.supportsFormat(format);
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean supportsBitrate(Range<Integer> bitrateRange, MediaFormat format) {
            Map<String, Object> map = format.getMap();
            Integer maxBitrate = (Integer) map.get(MediaFormat.KEY_MAX_BIT_RATE);
            Integer bitrate = (Integer) map.get(MediaFormat.KEY_BIT_RATE);
            if (bitrate == null) {
                bitrate = maxBitrate;
            } else if (maxBitrate != null) {
                bitrate = Integer.valueOf(Math.max(bitrate.intValue(), maxBitrate.intValue()));
            }
            if (bitrate != null && bitrate.intValue() > 0) {
                return bitrateRange.contains((Range<Integer>) bitrate);
            }
            return true;
        }

        private boolean supportsProfileLevel(int profile, Integer level) {
            CodecProfileLevel[] codecProfileLevelArr;
            for (CodecProfileLevel pl : this.profileLevels) {
                if (pl.profile == profile) {
                    if (level == null || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AAC)) {
                        return true;
                    }
                    if ((!this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_H263) || pl.level == level.intValue() || pl.level != 16 || level.intValue() <= 1) && (!this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_MPEG4) || pl.level == level.intValue() || pl.level != 4 || level.intValue() <= 1)) {
                        if (this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_HEVC)) {
                            boolean supportsHighTier = (pl.level & 44739242) != 0;
                            boolean checkingHighTier = (44739242 & level.intValue()) != 0;
                            if (checkingHighTier && !supportsHighTier) {
                            }
                        }
                        if (pl.level >= level.intValue()) {
                            return createFromProfileLevel(this.mMime, profile, pl.level) == null || createFromProfileLevel(this.mMime, profile, level.intValue()) != null;
                        }
                    }
                }
            }
            return false;
        }

        public MediaFormat getDefaultFormat() {
            return this.mDefaultFormat;
        }

        public String getMimeType() {
            return this.mMime;
        }

        public int getMaxSupportedInstances() {
            return this.mMaxSupportedInstances;
        }

        private boolean isAudio() {
            return this.mAudioCaps != null;
        }

        public AudioCapabilities getAudioCapabilities() {
            return this.mAudioCaps;
        }

        private boolean isEncoder() {
            return this.mEncoderCaps != null;
        }

        public EncoderCapabilities getEncoderCapabilities() {
            return this.mEncoderCaps;
        }

        private boolean isVideo() {
            return this.mVideoCaps != null;
        }

        public VideoCapabilities getVideoCapabilities() {
            return this.mVideoCaps;
        }

        public CodecCapabilities dup() {
            CodecCapabilities caps = new CodecCapabilities();
            caps.profileLevels = (CodecProfileLevel[]) Arrays.copyOf(this.profileLevels, this.profileLevels.length);
            caps.colorFormats = Arrays.copyOf(this.colorFormats, this.colorFormats.length);
            caps.mMime = this.mMime;
            caps.mMaxSupportedInstances = this.mMaxSupportedInstances;
            caps.mFlagsRequired = this.mFlagsRequired;
            caps.mFlagsSupported = this.mFlagsSupported;
            caps.mFlagsVerified = this.mFlagsVerified;
            caps.mAudioCaps = this.mAudioCaps;
            caps.mVideoCaps = this.mVideoCaps;
            caps.mEncoderCaps = this.mEncoderCaps;
            caps.mDefaultFormat = this.mDefaultFormat;
            caps.mCapabilitiesInfo = this.mCapabilitiesInfo;
            return caps;
        }

        public static CodecCapabilities createFromProfileLevel(String mime, int profile, int level) {
            CodecProfileLevel pl = new CodecProfileLevel();
            pl.profile = profile;
            pl.level = level;
            MediaFormat defaultFormat = new MediaFormat();
            defaultFormat.setString(MediaFormat.KEY_MIME, mime);
            CodecCapabilities ret = new CodecCapabilities(new CodecProfileLevel[]{pl}, new int[0], true, defaultFormat, new MediaFormat());
            if (ret.mError != 0) {
                return null;
            }
            return ret;
        }

        CodecCapabilities(CodecProfileLevel[] profLevs, int[] colFmts, boolean encoder, Map<String, Object> defaultFormatMap, Map<String, Object> capabilitiesMap) {
            this(profLevs, colFmts, encoder, new MediaFormat(defaultFormatMap), new MediaFormat(capabilitiesMap));
        }

        CodecCapabilities(CodecProfileLevel[] profLevs, int[] colFmts, boolean encoder, MediaFormat defaultFormat, MediaFormat info) {
            Feature[] validFeatures;
            Map<String, Object> map = info.getMap();
            this.colorFormats = colFmts;
            this.mFlagsVerified = 0;
            this.mDefaultFormat = defaultFormat;
            this.mCapabilitiesInfo = info;
            this.mMime = this.mDefaultFormat.getString(MediaFormat.KEY_MIME);
            CodecProfileLevel[] profLevs2 = profLevs;
            if (profLevs2.length == 0 && this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_VP9)) {
                CodecProfileLevel profLev = new CodecProfileLevel();
                profLev.profile = 1;
                profLev.level = VideoCapabilities.equivalentVP9Level(info);
                profLevs2 = new CodecProfileLevel[]{profLev};
            }
            this.profileLevels = profLevs2;
            if (this.mMime.toLowerCase().startsWith("audio/")) {
                this.mAudioCaps = AudioCapabilities.create(info, this);
                this.mAudioCaps.getDefaultFormat(this.mDefaultFormat);
            } else if (this.mMime.toLowerCase().startsWith("video/") || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_IMAGE_ANDROID_HEIC)) {
                this.mVideoCaps = VideoCapabilities.create(info, this);
            }
            if (encoder) {
                this.mEncoderCaps = EncoderCapabilities.create(info, this);
                this.mEncoderCaps.getDefaultFormat(this.mDefaultFormat);
            }
            Map<String, Object> global = MediaCodecList.getGlobalSettings();
            this.mMaxSupportedInstances = Utils.parseIntSafely(global.get("max-concurrent-instances"), 32);
            int maxInstances = Utils.parseIntSafely(map.get("max-concurrent-instances"), this.mMaxSupportedInstances);
            this.mMaxSupportedInstances = ((Integer) Range.create(1, 256).clamp(Integer.valueOf(maxInstances))).intValue();
            for (Feature feat : getValidFeatures()) {
                String key = MediaFormat.KEY_FEATURE_ + feat.mName;
                Integer yesNo = (Integer) map.get(key);
                if (yesNo != null) {
                    if (yesNo.intValue() > 0) {
                        this.mFlagsRequired = feat.mValue | this.mFlagsRequired;
                    }
                    this.mFlagsSupported |= feat.mValue;
                    this.mDefaultFormat.setInteger(key, 1);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class AudioCapabilities {
        private static final int MAX_INPUT_CHANNEL_COUNT = 30;
        private static final String TAG = "AudioCapabilities";
        private Range<Integer> mBitrateRange;
        private int mMaxInputChannelCount;
        private CodecCapabilities mParent;
        private Range<Integer>[] mSampleRateRanges;
        private int[] mSampleRates;

        public Range<Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public int[] getSupportedSampleRates() {
            if (this.mSampleRates != null) {
                return Arrays.copyOf(this.mSampleRates, this.mSampleRates.length);
            }
            return null;
        }

        public Range<Integer>[] getSupportedSampleRateRanges() {
            return (Range[]) Arrays.copyOf(this.mSampleRateRanges, this.mSampleRateRanges.length);
        }

        public int getMaxInputChannelCount() {
            return this.mMaxInputChannelCount;
        }

        private AudioCapabilities() {
        }

        public static AudioCapabilities create(MediaFormat info, CodecCapabilities parent) {
            AudioCapabilities caps = new AudioCapabilities();
            caps.init(info, parent);
            return caps;
        }

        private void init(MediaFormat info, CodecCapabilities parent) {
            this.mParent = parent;
            initWithPlatformLimits();
            applyLevelLimits();
            parseFromInfo(info);
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = Range.create(0, Integer.MAX_VALUE);
            this.mMaxInputChannelCount = 30;
            int minSampleRate = SystemProperties.getInt("ro.mediacodec.min_sample_rate", 7350);
            int maxSampleRate = SystemProperties.getInt("ro.mediacodec.max_sample_rate", AudioFormat.SAMPLE_RATE_HZ_MAX);
            this.mSampleRateRanges = new Range[]{Range.create(Integer.valueOf(minSampleRate), Integer.valueOf(maxSampleRate))};
            this.mSampleRates = null;
        }

        private boolean supports(Integer sampleRate, Integer inputChannels) {
            if (inputChannels != null && (inputChannels.intValue() < 1 || inputChannels.intValue() > this.mMaxInputChannelCount)) {
                return false;
            }
            if (sampleRate != null) {
                int ix = Utils.binarySearchDistinctRanges(this.mSampleRateRanges, sampleRate);
                if (ix < 0) {
                    return false;
                }
            }
            return true;
        }

        public boolean isSampleRateSupported(int sampleRate) {
            return supports(Integer.valueOf(sampleRate), null);
        }

        private void limitSampleRates(int[] rates) {
            Arrays.sort(rates);
            ArrayList<Range<Integer>> ranges = new ArrayList<>();
            for (int rate : rates) {
                if (supports(Integer.valueOf(rate), null)) {
                    ranges.add(Range.create(Integer.valueOf(rate), Integer.valueOf(rate)));
                }
            }
            this.mSampleRateRanges = (Range[]) ranges.toArray(new Range[ranges.size()]);
            createDiscreteSampleRates();
        }

        private void createDiscreteSampleRates() {
            this.mSampleRates = new int[this.mSampleRateRanges.length];
            for (int i = 0; i < this.mSampleRateRanges.length; i++) {
                this.mSampleRates[i] = this.mSampleRateRanges[i].getLower().intValue();
            }
        }

        private void limitSampleRates(Range<Integer>[] rateRanges) {
            Range<Integer>[] rangeArr;
            Utils.sortDistinctRanges(rateRanges);
            this.mSampleRateRanges = Utils.intersectSortedDistinctRanges(this.mSampleRateRanges, rateRanges);
            for (Range<Integer> range : this.mSampleRateRanges) {
                if (!range.getLower().equals(range.getUpper())) {
                    this.mSampleRates = null;
                    return;
                }
            }
            createDiscreteSampleRates();
        }

        private void applyLevelLimits() {
            int[] sampleRates = null;
            Range<Integer> sampleRateRange = null;
            Range<Integer> bitRates = null;
            int maxChannels = 30;
            String mime = this.mParent.getMimeType();
            if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_MPEG)) {
                sampleRates = new int[]{8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000};
                bitRates = Range.create(8000, 320000);
                maxChannels = 2;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AMR_NB)) {
                sampleRates = new int[]{8000};
                bitRates = Range.create(4750, 12200);
                maxChannels = 1;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AMR_WB)) {
                sampleRates = new int[]{16000};
                bitRates = Range.create(6600, 23850);
                maxChannels = 1;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AAC)) {
                sampleRates = new int[]{7350, 8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000};
                bitRates = Range.create(8000, 510000);
                maxChannels = 48;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_VORBIS)) {
                bitRates = Range.create(32000, 500000);
                sampleRateRange = Range.create(8000, Integer.valueOf((int) AudioFormat.SAMPLE_RATE_HZ_MAX));
                maxChannels = 255;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_OPUS)) {
                bitRates = Range.create(6000, 510000);
                sampleRates = new int[]{8000, 12000, 16000, 24000, 48000};
                maxChannels = 255;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_RAW)) {
                sampleRateRange = Range.create(1, 96000);
                bitRates = Range.create(1, 10000000);
                maxChannels = AudioSystem.OUT_CHANNEL_COUNT_MAX;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_FLAC)) {
                sampleRateRange = Range.create(1, 655350);
                maxChannels = 255;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_MLAW)) {
                sampleRates = new int[]{8000};
                bitRates = Range.create(64000, 64000);
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                sampleRates = new int[]{8000};
                bitRates = Range.create(13000, 13000);
                maxChannels = 1;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AC3)) {
                maxChannels = 6;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_EAC3)) {
                maxChannels = 16;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_EAC3_JOC)) {
                sampleRates = new int[]{48000};
                bitRates = Range.create(32000, 6144000);
                maxChannels = 16;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AC4)) {
                sampleRates = new int[]{44100, 48000, 96000, AudioFormat.SAMPLE_RATE_HZ_MAX};
                bitRates = Range.create(16000, 2688000);
                maxChannels = 24;
            } else {
                Log.m64w(TAG, "Unsupported mime " + mime);
                CodecCapabilities codecCapabilities = this.mParent;
                codecCapabilities.mError = codecCapabilities.mError | 2;
            }
            if (sampleRates != null) {
                limitSampleRates(sampleRates);
            } else if (sampleRateRange != null) {
                limitSampleRates(new Range[]{sampleRateRange});
            }
            applyLimits(maxChannels, bitRates);
        }

        private void applyLimits(int maxInputChannels, Range<Integer> bitRates) {
            this.mMaxInputChannelCount = ((Integer) Range.create(1, Integer.valueOf(this.mMaxInputChannelCount)).clamp(Integer.valueOf(maxInputChannels))).intValue();
            if (bitRates != null) {
                this.mBitrateRange = this.mBitrateRange.intersect(bitRates);
            }
        }

        private void parseFromInfo(MediaFormat info) {
            int maxInputChannels = 30;
            Range<Integer> bitRates = MediaCodecInfo.POSITIVE_INTEGERS;
            if (info.containsKey("sample-rate-ranges")) {
                String[] rateStrings = info.getString("sample-rate-ranges").split(SmsManager.REGEX_PREFIX_DELIMITER);
                Range<Integer>[] rateRanges = new Range[rateStrings.length];
                for (int i = 0; i < rateStrings.length; i++) {
                    rateRanges[i] = Utils.parseIntRange(rateStrings[i], null);
                }
                limitSampleRates(rateRanges);
            }
            if (info.containsKey("max-channel-count")) {
                maxInputChannels = Utils.parseIntSafely(info.getString("max-channel-count"), 30);
            } else if ((this.mParent.mError & 2) != 0) {
                maxInputChannels = 0;
            }
            if (info.containsKey("bitrate-range")) {
                bitRates = bitRates.intersect(Utils.parseIntRange(info.getString("bitrate-range"), bitRates));
            }
            applyLimits(maxInputChannels, bitRates);
        }

        public void getDefaultFormat(MediaFormat format) {
            if (this.mBitrateRange.getLower().equals(this.mBitrateRange.getUpper())) {
                format.setInteger(MediaFormat.KEY_BIT_RATE, this.mBitrateRange.getLower().intValue());
            }
            if (this.mMaxInputChannelCount == 1) {
                format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
            }
            if (this.mSampleRates != null && this.mSampleRates.length == 1) {
                format.setInteger(MediaFormat.KEY_SAMPLE_RATE, this.mSampleRates[0]);
            }
        }

        public boolean supportsFormat(MediaFormat format) {
            Map<String, Object> map = format.getMap();
            Integer sampleRate = (Integer) map.get(MediaFormat.KEY_SAMPLE_RATE);
            Integer channels = (Integer) map.get(MediaFormat.KEY_CHANNEL_COUNT);
            return supports(sampleRate, channels) && CodecCapabilities.supportsBitrate(this.mBitrateRange, format);
        }
    }

    /* loaded from: classes3.dex */
    public static final class VideoCapabilities {
        private static final String TAG = "VideoCapabilities";
        private boolean mAllowMbOverride;
        private Range<Rational> mAspectRatioRange;
        private Range<Integer> mBitrateRange;
        private Range<Rational> mBlockAspectRatioRange;
        private Range<Integer> mBlockCountRange;
        private int mBlockHeight;
        private int mBlockWidth;
        private Range<Long> mBlocksPerSecondRange;
        private Range<Integer> mFrameRateRange;
        private int mHeightAlignment;
        private Range<Integer> mHeightRange;
        private Range<Integer> mHorizontalBlockRange;
        private Map<Size, Range<Long>> mMeasuredFrameRates;
        private CodecCapabilities mParent;
        private List<PerformancePoint> mPerformancePoints;
        private int mSmallerDimensionUpperLimit;
        private Range<Integer> mVerticalBlockRange;
        private int mWidthAlignment;
        private Range<Integer> mWidthRange;

        public Range<Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public Range<Integer> getSupportedWidths() {
            return this.mWidthRange;
        }

        public Range<Integer> getSupportedHeights() {
            return this.mHeightRange;
        }

        public int getWidthAlignment() {
            return this.mWidthAlignment;
        }

        public int getHeightAlignment() {
            return this.mHeightAlignment;
        }

        public int getSmallerDimensionUpperLimit() {
            return this.mSmallerDimensionUpperLimit;
        }

        public Range<Integer> getSupportedFrameRates() {
            return this.mFrameRateRange;
        }

        public Range<Integer> getSupportedWidthsFor(int height) {
            try {
                Range<Integer> range = this.mWidthRange;
                if (!this.mHeightRange.contains((Range<Integer>) Integer.valueOf(height)) || height % this.mHeightAlignment != 0) {
                    throw new IllegalArgumentException("unsupported height");
                }
                int heightInBlocks = Utils.divUp(height, this.mBlockHeight);
                int minWidthInBlocks = Math.max(Utils.divUp(this.mBlockCountRange.getLower().intValue(), heightInBlocks), (int) Math.ceil(this.mBlockAspectRatioRange.getLower().doubleValue() * heightInBlocks));
                int maxWidthInBlocks = Math.min(this.mBlockCountRange.getUpper().intValue() / heightInBlocks, (int) (this.mBlockAspectRatioRange.getUpper().doubleValue() * heightInBlocks));
                Range<Integer> range2 = range.intersect(Integer.valueOf(((minWidthInBlocks - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(this.mBlockWidth * maxWidthInBlocks));
                if (height > this.mSmallerDimensionUpperLimit) {
                    range2 = range2.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                return range2.intersect(Integer.valueOf((int) Math.ceil(this.mAspectRatioRange.getLower().doubleValue() * height)), Integer.valueOf((int) (this.mAspectRatioRange.getUpper().doubleValue() * height)));
            } catch (IllegalArgumentException e) {
                Log.m66v(TAG, "could not get supported widths for " + height);
                throw new IllegalArgumentException("unsupported height");
            }
        }

        public Range<Integer> getSupportedHeightsFor(int width) {
            try {
                Range<Integer> range = this.mHeightRange;
                if (!this.mWidthRange.contains((Range<Integer>) Integer.valueOf(width)) || width % this.mWidthAlignment != 0) {
                    throw new IllegalArgumentException("unsupported width");
                }
                int widthInBlocks = Utils.divUp(width, this.mBlockWidth);
                int minHeightInBlocks = Math.max(Utils.divUp(this.mBlockCountRange.getLower().intValue(), widthInBlocks), (int) Math.ceil(widthInBlocks / this.mBlockAspectRatioRange.getUpper().doubleValue()));
                int maxHeightInBlocks = Math.min(this.mBlockCountRange.getUpper().intValue() / widthInBlocks, (int) (widthInBlocks / this.mBlockAspectRatioRange.getLower().doubleValue()));
                Range<Integer> range2 = range.intersect(Integer.valueOf(((minHeightInBlocks - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(this.mBlockHeight * maxHeightInBlocks));
                if (width > this.mSmallerDimensionUpperLimit) {
                    range2 = range2.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                return range2.intersect(Integer.valueOf((int) Math.ceil(width / this.mAspectRatioRange.getUpper().doubleValue())), Integer.valueOf((int) (width / this.mAspectRatioRange.getLower().doubleValue())));
            } catch (IllegalArgumentException e) {
                Log.m66v(TAG, "could not get supported heights for " + width);
                throw new IllegalArgumentException("unsupported width");
            }
        }

        public Range<Double> getSupportedFrameRatesFor(int width, int height) {
            Range<Integer> range = this.mHeightRange;
            if (!supports(Integer.valueOf(width), Integer.valueOf(height), null)) {
                throw new IllegalArgumentException("unsupported size");
            }
            int blockCount = Utils.divUp(width, this.mBlockWidth) * Utils.divUp(height, this.mBlockHeight);
            return Range.create(Double.valueOf(Math.max(this.mBlocksPerSecondRange.getLower().longValue() / blockCount, this.mFrameRateRange.getLower().intValue())), Double.valueOf(Math.min(this.mBlocksPerSecondRange.getUpper().longValue() / blockCount, this.mFrameRateRange.getUpper().intValue())));
        }

        private int getBlockCount(int width, int height) {
            return Utils.divUp(width, this.mBlockWidth) * Utils.divUp(height, this.mBlockHeight);
        }

        private Size findClosestSize(int width, int height) {
            int targetBlockCount = getBlockCount(width, height);
            Size closestSize = null;
            int minDiff = Integer.MAX_VALUE;
            for (Size size : this.mMeasuredFrameRates.keySet()) {
                int diff = Math.abs(targetBlockCount - getBlockCount(size.getWidth(), size.getHeight()));
                if (diff < minDiff) {
                    minDiff = diff;
                    closestSize = size;
                }
            }
            return closestSize;
        }

        private Range<Double> estimateFrameRatesFor(int width, int height) {
            Size size = findClosestSize(width, height);
            Range<Long> range = this.mMeasuredFrameRates.get(size);
            Double ratio = Double.valueOf(getBlockCount(size.getWidth(), size.getHeight()) / Math.max(getBlockCount(width, height), 1));
            return Range.create(Double.valueOf(range.getLower().longValue() * ratio.doubleValue()), Double.valueOf(range.getUpper().longValue() * ratio.doubleValue()));
        }

        public Range<Double> getAchievableFrameRatesFor(int width, int height) {
            if (!supports(Integer.valueOf(width), Integer.valueOf(height), null)) {
                throw new IllegalArgumentException("unsupported size");
            }
            if (this.mMeasuredFrameRates == null || this.mMeasuredFrameRates.size() <= 0) {
                Log.m64w(TAG, "Codec did not publish any measurement data.");
                return null;
            }
            return estimateFrameRatesFor(width, height);
        }

        /* loaded from: classes3.dex */
        public static final class PerformancePoint {
            private Size mBlockSize;
            private int mHeight;
            private int mMaxFrameRate;
            private long mMaxMacroBlockRate;
            private int mWidth;
            public static final PerformancePoint SD_24 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 24);
            public static final PerformancePoint SD_25 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 576, 25);
            public static final PerformancePoint SD_30 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 30);
            public static final PerformancePoint SD_48 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 48);
            public static final PerformancePoint SD_50 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 576, 50);
            public static final PerformancePoint SD_60 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 60);
            public static final PerformancePoint HD_24 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 24);
            public static final PerformancePoint HD_25 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 25);
            public static final PerformancePoint HD_30 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 30);
            public static final PerformancePoint HD_50 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 50);
            public static final PerformancePoint HD_60 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 60);
            public static final PerformancePoint HD_100 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 100);
            public static final PerformancePoint HD_120 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 120);
            public static final PerformancePoint HD_200 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 200);
            public static final PerformancePoint HD_240 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 240);
            public static final PerformancePoint FHD_24 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 24);
            public static final PerformancePoint FHD_25 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 25);
            public static final PerformancePoint FHD_30 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 30);
            public static final PerformancePoint FHD_50 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 50);
            public static final PerformancePoint FHD_60 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 60);
            public static final PerformancePoint FHD_100 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 100);
            public static final PerformancePoint FHD_120 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 120);
            public static final PerformancePoint FHD_200 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 200);
            public static final PerformancePoint FHD_240 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 240);
            public static final PerformancePoint UHD_24 = new PerformancePoint(3840, 2160, 24);
            public static final PerformancePoint UHD_25 = new PerformancePoint(3840, 2160, 25);
            public static final PerformancePoint UHD_30 = new PerformancePoint(3840, 2160, 30);
            public static final PerformancePoint UHD_50 = new PerformancePoint(3840, 2160, 50);
            public static final PerformancePoint UHD_60 = new PerformancePoint(3840, 2160, 60);
            public static final PerformancePoint UHD_100 = new PerformancePoint(3840, 2160, 100);
            public static final PerformancePoint UHD_120 = new PerformancePoint(3840, 2160, 120);
            public static final PerformancePoint UHD_200 = new PerformancePoint(3840, 2160, 200);
            public static final PerformancePoint UHD_240 = new PerformancePoint(3840, 2160, 240);

            public int getMaxMacroBlocks() {
                return saturateLongToInt(this.mWidth * this.mHeight);
            }

            public int getMaxFrameRate() {
                return this.mMaxFrameRate;
            }

            public long getMaxMacroBlockRate() {
                return this.mMaxMacroBlockRate;
            }

            public String toString() {
                int blockWidth = this.mBlockSize.getWidth() * 16;
                int blockHeight = this.mBlockSize.getHeight() * 16;
                int origRate = (int) Utils.divUp(this.mMaxMacroBlockRate, getMaxMacroBlocks());
                String info = (this.mWidth * 16) + "x" + (this.mHeight * 16) + "@" + origRate;
                if (origRate < this.mMaxFrameRate) {
                    info = info + ", max " + this.mMaxFrameRate + "fps";
                }
                if (blockWidth > 16 || blockHeight > 16) {
                    info = info + ", " + blockWidth + "x" + blockHeight + " blocks";
                }
                return "PerformancePoint(" + info + ")";
            }

            public int hashCode() {
                return this.mMaxFrameRate;
            }

            public PerformancePoint(int width, int height, int frameRate, int maxFrameRate, Size blockSize) {
                MediaCodecInfo.checkPowerOfTwo(blockSize.getWidth(), "block width");
                MediaCodecInfo.checkPowerOfTwo(blockSize.getHeight(), "block height");
                this.mBlockSize = new Size(Utils.divUp(blockSize.getWidth(), 16), Utils.divUp(blockSize.getHeight(), 16));
                this.mWidth = (int) (Utils.divUp(Math.max(1L, width), Math.max(blockSize.getWidth(), 16)) * this.mBlockSize.getWidth());
                this.mHeight = (int) (Utils.divUp(Math.max(1L, height), Math.max(blockSize.getHeight(), 16)) * this.mBlockSize.getHeight());
                this.mMaxFrameRate = Math.max(1, Math.max(frameRate, maxFrameRate));
                this.mMaxMacroBlockRate = Math.max(1, frameRate) * getMaxMacroBlocks();
            }

            public PerformancePoint(PerformancePoint pp, Size newBlockSize) {
                this(pp.mWidth * 16, pp.mHeight * 16, (int) Utils.divUp(pp.mMaxMacroBlockRate, pp.getMaxMacroBlocks()), pp.mMaxFrameRate, new Size(Math.max(newBlockSize.getWidth(), pp.mBlockSize.getWidth() * 16), Math.max(newBlockSize.getHeight(), pp.mBlockSize.getHeight() * 16)));
            }

            public PerformancePoint(int width, int height, int frameRate) {
                this(width, height, frameRate, frameRate, new Size(16, 16));
            }

            private int saturateLongToInt(long value) {
                if (value < -2147483648L) {
                    return Integer.MIN_VALUE;
                }
                if (value > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) value;
            }

            private int align(int value, int alignment) {
                return Utils.divUp(value, alignment) * alignment;
            }

            private void checkPowerOfTwo2(int value, String description) {
                if (value == 0 || ((value - 1) & value) != 0) {
                    throw new IllegalArgumentException(description + " (" + value + ") must be a power of 2");
                }
            }

            public boolean covers(MediaFormat format) {
                PerformancePoint other = new PerformancePoint(format.getInteger("width", 0), format.getInteger("height", 0), Math.round((float) Math.ceil(format.getNumber(MediaFormat.KEY_FRAME_RATE, 0).doubleValue())));
                return covers(other);
            }

            public boolean covers(PerformancePoint other) {
                Size commonSize = getCommonBlockSize(other);
                PerformancePoint aligned = new PerformancePoint(this, commonSize);
                PerformancePoint otherAligned = new PerformancePoint(other, commonSize);
                return aligned.getMaxMacroBlocks() >= otherAligned.getMaxMacroBlocks() && aligned.mMaxFrameRate >= otherAligned.mMaxFrameRate && aligned.mMaxMacroBlockRate >= otherAligned.mMaxMacroBlockRate;
            }

            private Size getCommonBlockSize(PerformancePoint other) {
                return new Size(Math.max(this.mBlockSize.getWidth(), other.mBlockSize.getWidth()) * 16, Math.max(this.mBlockSize.getHeight(), other.mBlockSize.getHeight()) * 16);
            }

            public boolean equals(Object o) {
                if (o instanceof PerformancePoint) {
                    PerformancePoint other = (PerformancePoint) o;
                    Size commonSize = getCommonBlockSize(other);
                    PerformancePoint aligned = new PerformancePoint(this, commonSize);
                    PerformancePoint otherAligned = new PerformancePoint(other, commonSize);
                    return aligned.getMaxMacroBlocks() == otherAligned.getMaxMacroBlocks() && aligned.mMaxFrameRate == otherAligned.mMaxFrameRate && aligned.mMaxMacroBlockRate == otherAligned.mMaxMacroBlockRate;
                }
                return false;
            }
        }

        public List<PerformancePoint> getSupportedPerformancePoints() {
            return this.mPerformancePoints;
        }

        public boolean areSizeAndRateSupported(int width, int height, double frameRate) {
            return supports(Integer.valueOf(width), Integer.valueOf(height), Double.valueOf(frameRate));
        }

        public boolean isSizeSupported(int width, int height) {
            return supports(Integer.valueOf(width), Integer.valueOf(height), null);
        }

        private boolean supports(Integer width, Integer height, Number rate) {
            boolean ok = true;
            boolean z = true;
            if (1 != 0 && width != null) {
                ok = this.mWidthRange.contains((Range<Integer>) width) && width.intValue() % this.mWidthAlignment == 0;
            }
            if (ok && height != null) {
                ok = this.mHeightRange.contains((Range<Integer>) height) && height.intValue() % this.mHeightAlignment == 0;
            }
            if (ok && rate != null) {
                ok = this.mFrameRateRange.contains(Utils.intRangeFor(rate.doubleValue()));
            }
            if (ok && height != null && width != null) {
                boolean ok2 = Math.min(height.intValue(), width.intValue()) <= this.mSmallerDimensionUpperLimit;
                int widthInBlocks = Utils.divUp(width.intValue(), this.mBlockWidth);
                int heightInBlocks = Utils.divUp(height.intValue(), this.mBlockHeight);
                int blockCount = widthInBlocks * heightInBlocks;
                if (!ok2 || !this.mBlockCountRange.contains((Range<Integer>) Integer.valueOf(blockCount)) || !this.mBlockAspectRatioRange.contains((Range<Rational>) new Rational(widthInBlocks, heightInBlocks)) || !this.mAspectRatioRange.contains((Range<Rational>) new Rational(width.intValue(), height.intValue()))) {
                    z = false;
                }
                boolean ok3 = z;
                if (ok3 && rate != null) {
                    double blocksPerSec = blockCount * rate.doubleValue();
                    return this.mBlocksPerSecondRange.contains(Utils.longRangeFor(blocksPerSec));
                }
                return ok3;
            }
            return ok;
        }

        public boolean supportsFormat(MediaFormat format) {
            Map<String, Object> map = format.getMap();
            Integer width = (Integer) map.get("width");
            Integer height = (Integer) map.get("height");
            Number rate = (Number) map.get(MediaFormat.KEY_FRAME_RATE);
            return supports(width, height, rate) && CodecCapabilities.supportsBitrate(this.mBitrateRange, format);
        }

        private VideoCapabilities() {
        }

        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public static VideoCapabilities create(MediaFormat info, CodecCapabilities parent) {
            VideoCapabilities caps = new VideoCapabilities();
            caps.init(info, parent);
            return caps;
        }

        private void init(MediaFormat info, CodecCapabilities parent) {
            this.mParent = parent;
            initWithPlatformLimits();
            applyLevelLimits();
            parseFromInfo(info);
            updateLimits();
        }

        public Size getBlockSize() {
            return new Size(this.mBlockWidth, this.mBlockHeight);
        }

        public Range<Integer> getBlockCountRange() {
            return this.mBlockCountRange;
        }

        public Range<Long> getBlocksPerSecondRange() {
            return this.mBlocksPerSecondRange;
        }

        public Range<Rational> getAspectRatioRange(boolean blocks) {
            return blocks ? this.mBlockAspectRatioRange : this.mAspectRatioRange;
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = MediaCodecInfo.BITRATE_RANGE;
            this.mWidthRange = MediaCodecInfo.SIZE_RANGE;
            this.mHeightRange = MediaCodecInfo.SIZE_RANGE;
            this.mFrameRateRange = MediaCodecInfo.FRAME_RATE_RANGE;
            this.mHorizontalBlockRange = MediaCodecInfo.SIZE_RANGE;
            this.mVerticalBlockRange = MediaCodecInfo.SIZE_RANGE;
            this.mBlockCountRange = MediaCodecInfo.POSITIVE_INTEGERS;
            this.mBlocksPerSecondRange = MediaCodecInfo.POSITIVE_LONGS;
            this.mBlockAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS;
            this.mAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS;
            this.mWidthAlignment = 2;
            this.mHeightAlignment = 2;
            this.mBlockWidth = 2;
            this.mBlockHeight = 2;
            this.mSmallerDimensionUpperLimit = ((Integer) MediaCodecInfo.SIZE_RANGE.getUpper()).intValue();
        }

        private List<PerformancePoint> getPerformancePoints(Map<String, Object> map) {
            Range<Long> range;
            Vector<PerformancePoint> ret = new Vector<>();
            String prefix = "performance-point-";
            Set<String> keys = map.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (key.startsWith("performance-point-")) {
                    String subKey = key.substring("performance-point-".length());
                    if (subKey.equals("none") && ret.size() == 0) {
                        return Collections.unmodifiableList(ret);
                    }
                    String[] temp = key.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (temp.length == 4) {
                        String sizeStr = temp[2];
                        Size size = Utils.parseSize(sizeStr, null);
                        if (size != null && size.getWidth() * size.getHeight() > 0 && (range = Utils.parseLongRange(map.get(key), null)) != null && range.getLower().longValue() >= 0 && range.getUpper().longValue() >= 0) {
                            String prefix2 = prefix;
                            Set<String> keys2 = keys;
                            PerformancePoint given = new PerformancePoint(size.getWidth(), size.getHeight(), range.getLower().intValue(), range.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                            Iterator<String> it2 = it;
                            PerformancePoint rotated = new PerformancePoint(size.getHeight(), size.getWidth(), range.getLower().intValue(), range.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                            ret.add(given);
                            if (!given.covers(rotated)) {
                                ret.add(rotated);
                            }
                            prefix = prefix2;
                            keys = keys2;
                            it = it2;
                        }
                    }
                }
            }
            if (ret.size() == 0) {
                return null;
            }
            ret.sort(new Comparator() { // from class: android.media.-$$Lambda$MediaCodecInfo$VideoCapabilities$DpgwEn-gVFZT9EtP3qcxpiA2G0M
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MediaCodecInfo.VideoCapabilities.lambda$getPerformancePoints$0((MediaCodecInfo.VideoCapabilities.PerformancePoint) obj, (MediaCodecInfo.VideoCapabilities.PerformancePoint) obj2);
                }
            });
            return Collections.unmodifiableList(ret);
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x002f, code lost:
            if (r6.getMaxMacroBlockRate() < r7.getMaxMacroBlockRate()) goto L5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0045, code lost:
            if (r6.getMaxFrameRate() < r7.getMaxFrameRate()) goto L5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:5:0x0014, code lost:
            if (r6.getMaxMacroBlocks() < r7.getMaxMacroBlocks()) goto L5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:6:0x0016, code lost:
            r2 = -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        static /* synthetic */ int lambda$getPerformancePoints$0(PerformancePoint a, PerformancePoint b) {
            int i = 1;
            if (a.getMaxMacroBlocks() == b.getMaxMacroBlocks()) {
                if (a.getMaxMacroBlockRate() == b.getMaxMacroBlockRate()) {
                    if (a.getMaxFrameRate() == b.getMaxFrameRate()) {
                        i = 0;
                    }
                }
            }
            return -i;
        }

        private Map<Size, Range<Long>> getMeasuredFrameRates(Map<String, Object> map) {
            Range<Long> range;
            Map<Size, Range<Long>> ret = new HashMap<>();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                if (key.startsWith("measured-frame-rate-")) {
                    key.substring("measured-frame-rate-".length());
                    String[] temp = key.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (temp.length == 5) {
                        String sizeStr = temp[3];
                        Size size = Utils.parseSize(sizeStr, null);
                        if (size != null && size.getWidth() * size.getHeight() > 0 && (range = Utils.parseLongRange(map.get(key), null)) != null && range.getLower().longValue() >= 0 && range.getUpper().longValue() >= 0) {
                            ret.put(size, range);
                        }
                    }
                }
            }
            return ret;
        }

        private static Pair<Range<Integer>, Range<Integer>> parseWidthHeightRanges(Object o) {
            Pair<Size, Size> range = Utils.parseSizeRange(o);
            if (range != null) {
                try {
                    return Pair.create(Range.create(Integer.valueOf(range.first.getWidth()), Integer.valueOf(range.second.getWidth())), Range.create(Integer.valueOf(range.first.getHeight()), Integer.valueOf(range.second.getHeight())));
                } catch (IllegalArgumentException e) {
                    Log.m64w(TAG, "could not parse size range '" + o + "'");
                    return null;
                }
            }
            return null;
        }

        public static int equivalentVP9Level(MediaFormat info) {
            int D;
            Map<String, Object> map = info.getMap();
            Size blockSize = Utils.parseSize(map.get("block-size"), new Size(8, 8));
            int BS = blockSize.getWidth() * blockSize.getHeight();
            Range<Integer> counts = Utils.parseIntRange(map.get("block-count-range"), null);
            int FS = counts == null ? 0 : counts.getUpper().intValue() * BS;
            Range<Long> blockRates = Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            long SR = blockRates == null ? 0L : BS * blockRates.getUpper().longValue();
            Pair<Range<Integer>, Range<Integer>> dimensionRanges = parseWidthHeightRanges(map.get("size-range"));
            if (dimensionRanges == null) {
                D = 0;
            } else {
                D = Math.max(dimensionRanges.first.getUpper().intValue(), dimensionRanges.second.getUpper().intValue());
            }
            Range<Integer> bitRates = Utils.parseIntRange(map.get("bitrate-range"), null);
            int BR = bitRates != null ? Utils.divUp(bitRates.getUpper().intValue(), 1000) : 0;
            if (SR <= 829440 && FS <= 36864 && BR <= 200 && D <= 512) {
                return 1;
            }
            if (SR <= 2764800 && FS <= 73728 && BR <= 800 && D <= 768) {
                return 2;
            }
            if (SR <= 4608000 && FS <= 122880 && BR <= 1800 && D <= 960) {
                return 4;
            }
            if (SR > 9216000 || FS > 245760 || BR > 3600 || D > 1344) {
                if (SR <= 20736000 && FS <= 552960 && BR <= 7200 && D <= 2048) {
                    return 16;
                }
                if (SR <= 36864000 && FS <= 983040 && BR <= 12000 && D <= 2752) {
                    return 32;
                }
                if (SR <= 83558400 && FS <= 2228224 && BR <= 18000 && D <= 4160) {
                    return 64;
                }
                if (SR <= 160432128 && FS <= 2228224 && BR <= 30000 && D <= 4160) {
                    return 128;
                }
                if (SR <= 311951360 && FS <= 8912896 && BR <= 60000 && D <= 8384) {
                    return 256;
                }
                if (SR > 588251136 || FS > 8912896 || BR > 120000 || D > 8384) {
                    if (SR <= 1176502272 && FS <= 8912896 && BR <= 180000 && D <= 8384) {
                        return 1024;
                    }
                    if (SR <= 1176502272 && FS <= 35651584 && BR <= 180000 && D <= 16832) {
                        return 2048;
                    }
                    if (SR > 2353004544L || FS > 35651584 || BR > 240000 || D > 16832) {
                        return (SR > 4706009088L || FS > 35651584 || BR > 480000 || D <= 16832) ? 8192 : 8192;
                    }
                    return 4096;
                }
                return 512;
            }
            return 8;
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x01cd  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0272  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x027e  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x028a  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0296  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x02b5  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x02d5  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x02f3  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x02ff  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x030b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void parseFromInfo(MediaFormat info) {
            Range<Integer> bitRates;
            Range<Integer> heights;
            Range<Integer> widths;
            Range<Integer> frameRates;
            Range<Rational> ratios;
            Range<Rational> blockRatios;
            Map<String, Object> map = info.getMap();
            Size blockSize = new Size(this.mBlockWidth, this.mBlockHeight);
            Size alignment = new Size(this.mWidthAlignment, this.mHeightAlignment);
            Range<Integer> widths2 = null;
            Range<Integer> heights2 = null;
            Size blockSize2 = Utils.parseSize(map.get("block-size"), blockSize);
            Size alignment2 = Utils.parseSize(map.get("alignment"), alignment);
            Range<Integer> counts = Utils.parseIntRange(map.get("block-count-range"), null);
            Range<Long> blockRates = Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            this.mMeasuredFrameRates = getMeasuredFrameRates(map);
            this.mPerformancePoints = getPerformancePoints(map);
            Pair<Range<Integer>, Range<Integer>> sizeRanges = parseWidthHeightRanges(map.get("size-range"));
            if (sizeRanges != null) {
                Range<Integer> widths3 = sizeRanges.first;
                widths2 = widths3;
                Range<Integer> heights3 = sizeRanges.second;
                heights2 = heights3;
            }
            if (map.containsKey("feature-can-swap-width-height")) {
                if (widths2 != null) {
                    this.mSmallerDimensionUpperLimit = Math.min(widths2.getUpper().intValue(), heights2.getUpper().intValue());
                    Range<Integer> extend = widths2.extend(heights2);
                    heights2 = extend;
                    widths2 = extend;
                } else {
                    Log.m64w(TAG, "feature can-swap-width-height is best used with size-range");
                    this.mSmallerDimensionUpperLimit = Math.min(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getUpper().intValue());
                    Range<Integer> extend2 = this.mWidthRange.extend(this.mHeightRange);
                    this.mHeightRange = extend2;
                    this.mWidthRange = extend2;
                }
            }
            Range<Integer> heights4 = heights2;
            Range<Integer> widths4 = widths2;
            Range<Rational> ratios2 = Utils.parseRationalRange(map.get("block-aspect-ratio-range"), null);
            Range<Rational> blockRatios2 = Utils.parseRationalRange(map.get("pixel-aspect-ratio-range"), null);
            Range<Integer> frameRates2 = Utils.parseIntRange(map.get("frame-rate-range"), null);
            if (frameRates2 != null) {
                try {
                    frameRates2 = frameRates2.intersect(MediaCodecInfo.FRAME_RATE_RANGE);
                } catch (IllegalArgumentException e) {
                    Log.m64w(TAG, "frame rate range (" + frameRates2 + ") is out of limits: " + MediaCodecInfo.FRAME_RATE_RANGE);
                    frameRates2 = null;
                }
            }
            Range<Integer> frameRates3 = frameRates2;
            Range<Integer> bitRates2 = Utils.parseIntRange(map.get("bitrate-range"), null);
            if (bitRates2 != null) {
                try {
                    bitRates = bitRates2.intersect(MediaCodecInfo.BITRATE_RANGE);
                } catch (IllegalArgumentException e2) {
                    Log.m64w(TAG, "bitrate range (" + bitRates2 + ") is out of limits: " + MediaCodecInfo.BITRATE_RANGE);
                    bitRates2 = null;
                }
                MediaCodecInfo.checkPowerOfTwo(blockSize2.getWidth(), "block-size width must be power of two");
                MediaCodecInfo.checkPowerOfTwo(blockSize2.getHeight(), "block-size height must be power of two");
                MediaCodecInfo.checkPowerOfTwo(alignment2.getWidth(), "alignment width must be power of two");
                MediaCodecInfo.checkPowerOfTwo(alignment2.getHeight(), "alignment height must be power of two");
                applyMacroBlockLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, blockSize2.getWidth(), blockSize2.getHeight(), alignment2.getWidth(), alignment2.getHeight());
                if ((this.mParent.mError & 2) != 0) {
                    if (this.mAllowMbOverride) {
                        heights = heights4;
                        widths = widths4;
                        frameRates = frameRates3;
                        ratios = ratios2;
                        blockRatios = blockRatios2;
                    } else {
                        if (widths4 != null) {
                            this.mWidthRange = this.mWidthRange.intersect(widths4);
                        }
                        if (heights4 != null) {
                            this.mHeightRange = this.mHeightRange.intersect(heights4);
                        }
                        if (counts != null) {
                            this.mBlockCountRange = this.mBlockCountRange.intersect(Utils.factorRange(counts, ((this.mBlockWidth * this.mBlockHeight) / blockSize2.getWidth()) / blockSize2.getHeight()));
                        }
                        if (blockRates != null) {
                            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(Utils.factorRange(blockRates, ((this.mBlockWidth * this.mBlockHeight) / blockSize2.getWidth()) / blockSize2.getHeight()));
                        }
                        if (blockRatios2 != null) {
                            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(Utils.scaleRange(blockRatios2, this.mBlockHeight / blockSize2.getHeight(), this.mBlockWidth / blockSize2.getWidth()));
                        }
                        if (ratios2 != null) {
                            this.mAspectRatioRange = this.mAspectRatioRange.intersect(ratios2);
                        }
                        if (frameRates3 != null) {
                            this.mFrameRateRange = this.mFrameRateRange.intersect(frameRates3);
                        }
                        if (bitRates != null) {
                            this.mBitrateRange = this.mBitrateRange.intersect(bitRates);
                        }
                        updateLimits();
                    }
                } else {
                    heights = heights4;
                    widths = widths4;
                    frameRates = frameRates3;
                    ratios = ratios2;
                    blockRatios = blockRatios2;
                }
                if (widths != null) {
                    this.mWidthRange = MediaCodecInfo.SIZE_RANGE.intersect(widths);
                }
                if (heights != null) {
                    this.mHeightRange = MediaCodecInfo.SIZE_RANGE.intersect(heights);
                }
                if (counts != null) {
                    this.mBlockCountRange = MediaCodecInfo.POSITIVE_INTEGERS.intersect(Utils.factorRange(counts, ((this.mBlockWidth * this.mBlockHeight) / blockSize2.getWidth()) / blockSize2.getHeight()));
                }
                if (blockRates != null) {
                    this.mBlocksPerSecondRange = MediaCodecInfo.POSITIVE_LONGS.intersect(Utils.factorRange(blockRates, ((this.mBlockWidth * this.mBlockHeight) / blockSize2.getWidth()) / blockSize2.getHeight()));
                }
                if (blockRatios != null) {
                    this.mBlockAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS.intersect(Utils.scaleRange(blockRatios, this.mBlockHeight / blockSize2.getHeight(), this.mBlockWidth / blockSize2.getWidth()));
                }
                if (ratios != null) {
                    this.mAspectRatioRange = MediaCodecInfo.POSITIVE_RATIONALS.intersect(ratios);
                }
                if (frameRates != null) {
                    this.mFrameRateRange = MediaCodecInfo.FRAME_RATE_RANGE.intersect(frameRates);
                }
                if (bitRates != null) {
                    if ((this.mParent.mError & 2) != 0) {
                        this.mBitrateRange = MediaCodecInfo.BITRATE_RANGE.intersect(bitRates);
                    } else {
                        this.mBitrateRange = this.mBitrateRange.intersect(bitRates);
                    }
                }
                updateLimits();
            }
            bitRates = bitRates2;
            MediaCodecInfo.checkPowerOfTwo(blockSize2.getWidth(), "block-size width must be power of two");
            MediaCodecInfo.checkPowerOfTwo(blockSize2.getHeight(), "block-size height must be power of two");
            MediaCodecInfo.checkPowerOfTwo(alignment2.getWidth(), "alignment width must be power of two");
            MediaCodecInfo.checkPowerOfTwo(alignment2.getHeight(), "alignment height must be power of two");
            applyMacroBlockLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, blockSize2.getWidth(), blockSize2.getHeight(), alignment2.getWidth(), alignment2.getHeight());
            if ((this.mParent.mError & 2) != 0) {
            }
            if (widths != null) {
            }
            if (heights != null) {
            }
            if (counts != null) {
            }
            if (blockRates != null) {
            }
            if (blockRatios != null) {
            }
            if (ratios != null) {
            }
            if (frameRates != null) {
            }
            if (bitRates != null) {
            }
            updateLimits();
        }

        private void applyBlockLimits(int blockWidth, int blockHeight, Range<Integer> counts, Range<Long> rates, Range<Rational> ratios) {
            MediaCodecInfo.checkPowerOfTwo(blockWidth, "blockWidth must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(blockHeight, "blockHeight must be a power of two");
            int newBlockWidth = Math.max(blockWidth, this.mBlockWidth);
            int newBlockHeight = Math.max(blockHeight, this.mBlockHeight);
            int factor = ((newBlockWidth * newBlockHeight) / this.mBlockWidth) / this.mBlockHeight;
            if (factor != 1) {
                this.mBlockCountRange = Utils.factorRange(this.mBlockCountRange, factor);
                this.mBlocksPerSecondRange = Utils.factorRange(this.mBlocksPerSecondRange, factor);
                this.mBlockAspectRatioRange = Utils.scaleRange(this.mBlockAspectRatioRange, newBlockHeight / this.mBlockHeight, newBlockWidth / this.mBlockWidth);
                this.mHorizontalBlockRange = Utils.factorRange(this.mHorizontalBlockRange, newBlockWidth / this.mBlockWidth);
                this.mVerticalBlockRange = Utils.factorRange(this.mVerticalBlockRange, newBlockHeight / this.mBlockHeight);
            }
            int factor2 = ((newBlockWidth * newBlockHeight) / blockWidth) / blockHeight;
            if (factor2 != 1) {
                counts = Utils.factorRange(counts, factor2);
                rates = Utils.factorRange(rates, factor2);
                ratios = Utils.scaleRange(ratios, newBlockHeight / blockHeight, newBlockWidth / blockWidth);
            }
            this.mBlockCountRange = this.mBlockCountRange.intersect(counts);
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(rates);
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(ratios);
            this.mBlockWidth = newBlockWidth;
            this.mBlockHeight = newBlockHeight;
        }

        private void applyAlignment(int widthAlignment, int heightAlignment) {
            MediaCodecInfo.checkPowerOfTwo(widthAlignment, "widthAlignment must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(heightAlignment, "heightAlignment must be a power of two");
            if (widthAlignment > this.mBlockWidth || heightAlignment > this.mBlockHeight) {
                applyBlockLimits(Math.max(widthAlignment, this.mBlockWidth), Math.max(heightAlignment, this.mBlockHeight), MediaCodecInfo.POSITIVE_INTEGERS, MediaCodecInfo.POSITIVE_LONGS, MediaCodecInfo.POSITIVE_RATIONALS);
            }
            this.mWidthAlignment = Math.max(widthAlignment, this.mWidthAlignment);
            this.mHeightAlignment = Math.max(heightAlignment, this.mHeightAlignment);
            this.mWidthRange = Utils.alignRange(this.mWidthRange, this.mWidthAlignment);
            this.mHeightRange = Utils.alignRange(this.mHeightRange, this.mHeightAlignment);
        }

        private void updateLimits() {
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Utils.factorRange(this.mWidthRange, this.mBlockWidth));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Range.create(Integer.valueOf(this.mBlockCountRange.getLower().intValue() / this.mVerticalBlockRange.getUpper().intValue()), Integer.valueOf(this.mBlockCountRange.getUpper().intValue() / this.mVerticalBlockRange.getLower().intValue())));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Utils.factorRange(this.mHeightRange, this.mBlockHeight));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Range.create(Integer.valueOf(this.mBlockCountRange.getLower().intValue() / this.mHorizontalBlockRange.getUpper().intValue()), Integer.valueOf(this.mBlockCountRange.getUpper().intValue() / this.mHorizontalBlockRange.getLower().intValue())));
            this.mBlockCountRange = this.mBlockCountRange.intersect(Range.create(Integer.valueOf(this.mHorizontalBlockRange.getLower().intValue() * this.mVerticalBlockRange.getLower().intValue()), Integer.valueOf(this.mHorizontalBlockRange.getUpper().intValue() * this.mVerticalBlockRange.getUpper().intValue())));
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(new Rational(this.mHorizontalBlockRange.getLower().intValue(), this.mVerticalBlockRange.getUpper().intValue()), new Rational(this.mHorizontalBlockRange.getUpper().intValue(), this.mVerticalBlockRange.getLower().intValue()));
            this.mWidthRange = this.mWidthRange.intersect(Integer.valueOf(((this.mHorizontalBlockRange.getLower().intValue() - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(this.mHorizontalBlockRange.getUpper().intValue() * this.mBlockWidth));
            this.mHeightRange = this.mHeightRange.intersect(Integer.valueOf(((this.mVerticalBlockRange.getLower().intValue() - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(this.mVerticalBlockRange.getUpper().intValue() * this.mBlockHeight));
            this.mAspectRatioRange = this.mAspectRatioRange.intersect(new Rational(this.mWidthRange.getLower().intValue(), this.mHeightRange.getUpper().intValue()), new Rational(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getLower().intValue()));
            this.mSmallerDimensionUpperLimit = Math.min(this.mSmallerDimensionUpperLimit, Math.min(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getUpper().intValue()));
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(Long.valueOf(this.mBlockCountRange.getLower().intValue() * this.mFrameRateRange.getLower().intValue()), Long.valueOf(this.mBlockCountRange.getUpper().intValue() * this.mFrameRateRange.getUpper().intValue()));
            this.mFrameRateRange = this.mFrameRateRange.intersect(Integer.valueOf((int) (this.mBlocksPerSecondRange.getLower().longValue() / this.mBlockCountRange.getUpper().intValue())), Integer.valueOf((int) (this.mBlocksPerSecondRange.getUpper().longValue() / this.mBlockCountRange.getLower().intValue())));
        }

        private void applyMacroBlockLimits(int maxHorizontalBlocks, int maxVerticalBlocks, int maxBlocks, long maxBlocksPerSecond, int blockWidth, int blockHeight, int widthAlignment, int heightAlignment) {
            applyMacroBlockLimits(1, 1, maxHorizontalBlocks, maxVerticalBlocks, maxBlocks, maxBlocksPerSecond, blockWidth, blockHeight, widthAlignment, heightAlignment);
        }

        private void applyMacroBlockLimits(int minHorizontalBlocks, int minVerticalBlocks, int maxHorizontalBlocks, int maxVerticalBlocks, int maxBlocks, long maxBlocksPerSecond, int blockWidth, int blockHeight, int widthAlignment, int heightAlignment) {
            applyAlignment(widthAlignment, heightAlignment);
            applyBlockLimits(blockWidth, blockHeight, Range.create(1, Integer.valueOf(maxBlocks)), Range.create(1L, Long.valueOf(maxBlocksPerSecond)), Range.create(new Rational(1, maxVerticalBlocks), new Rational(maxHorizontalBlocks, 1)));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Integer.valueOf(Utils.divUp(minHorizontalBlocks, this.mBlockWidth / blockWidth)), Integer.valueOf(maxHorizontalBlocks / (this.mBlockWidth / blockWidth)));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Integer.valueOf(Utils.divUp(minVerticalBlocks, this.mBlockHeight / blockHeight)), Integer.valueOf(maxVerticalBlocks / (this.mBlockHeight / blockHeight)));
        }

        /* JADX WARN: Removed duplicated region for block: B:138:0x0617  */
        /* JADX WARN: Removed duplicated region for block: B:141:0x062c  */
        /* JADX WARN: Removed duplicated region for block: B:142:0x063a  */
        /* JADX WARN: Removed duplicated region for block: B:200:0x0837  */
        /* JADX WARN: Removed duplicated region for block: B:201:0x0841  */
        /* JADX WARN: Removed duplicated region for block: B:338:0x01d1 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:341:0x038a A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:54:0x01cd  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0386  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void applyLevelLimits() {
            int i;
            int errors;
            int maxBps;
            int FS;
            int i2;
            CodecProfileLevel[] profileLevels;
            int FS2;
            long SR;
            int FS3;
            int FS4;
            long SR2;
            CodecProfileLevel[] profileLevels2;
            CodecProfileLevel[] profileLevels3;
            int FR;
            int minW;
            int minH;
            int MBPS;
            int FS5;
            int FR2;
            int W;
            int H;
            int MBPS2;
            int FS6;
            int BR;
            int i3;
            CodecProfileLevel[] profileLevels4;
            int BR2;
            int FR3;
            int W2;
            int H2;
            long j;
            int MBPS3;
            int FS7;
            int FR4;
            int W3;
            int H3;
            int MBPS4;
            int FS8;
            int BR3;
            int i4;
            boolean z;
            int BR4;
            int FR5;
            int W4;
            int H4;
            int i5;
            int BR5;
            boolean z2 = false;
            CodecProfileLevel[] profileLevels5 = this.mParent.profileLevels;
            String mime = this.mParent.getMimeType();
            int i6 = 4;
            if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AVC)) {
                int maxDPBBlocks = profileLevels5.length;
                errors = 4;
                int i7 = 0;
                int maxBlocks = 99;
                int maxBps2 = 64000;
                long maxBlocksPerSecond = 1485;
                int maxDPBBlocks2 = 396;
                while (i7 < maxDPBBlocks) {
                    CodecProfileLevel profileLevel = profileLevels5[i7];
                    int MBPS5 = 0;
                    int FS9 = 0;
                    int BR6 = 0;
                    int DPB = 0;
                    boolean supported = true;
                    switch (profileLevel.level) {
                        case 1:
                            MBPS5 = 1485;
                            FS9 = 99;
                            BR6 = 64;
                            DPB = 396;
                            break;
                        case 2:
                            MBPS5 = 1485;
                            FS9 = 99;
                            BR6 = 128;
                            DPB = 396;
                            break;
                        case 4:
                            MBPS5 = PathInterpolatorCompat.MAX_NUM_POINTS;
                            FS9 = 396;
                            BR6 = 192;
                            DPB = 900;
                            break;
                        case 8:
                            MBPS5 = 6000;
                            FS9 = 396;
                            BR6 = MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION;
                            DPB = 2376;
                            break;
                        case 16:
                            MBPS5 = 11880;
                            FS9 = 396;
                            BR6 = 768;
                            DPB = 2376;
                            break;
                        case 32:
                            MBPS5 = 11880;
                            FS9 = 396;
                            BR6 = 2000;
                            DPB = 2376;
                            break;
                        case 64:
                            MBPS5 = 19800;
                            FS9 = 792;
                            BR6 = 4000;
                            DPB = 4752;
                            break;
                        case 128:
                            MBPS5 = 20250;
                            FS9 = 1620;
                            BR6 = 4000;
                            DPB = 8100;
                            break;
                        case 256:
                            MBPS5 = 40500;
                            FS9 = 1620;
                            BR6 = 10000;
                            DPB = 8100;
                            break;
                        case 512:
                            MBPS5 = 108000;
                            FS9 = 3600;
                            BR6 = 14000;
                            DPB = 18000;
                            break;
                        case 1024:
                            MBPS5 = 216000;
                            FS9 = 5120;
                            BR6 = 20000;
                            DPB = MtpConstants.DEVICE_PROPERTY_UNDEFINED;
                            break;
                        case 2048:
                            MBPS5 = 245760;
                            FS9 = 8192;
                            BR6 = 20000;
                            DPB = 32768;
                            break;
                        case 4096:
                            MBPS5 = 245760;
                            FS9 = 8192;
                            BR6 = 50000;
                            DPB = 32768;
                            break;
                        case 8192:
                            MBPS5 = 522240;
                            FS9 = 8704;
                            BR6 = 50000;
                            DPB = GLES20.GL_STENCIL_BACK_FUNC;
                            break;
                        case 16384:
                            MBPS5 = 589824;
                            FS9 = 22080;
                            BR6 = 135000;
                            DPB = 110400;
                            break;
                        case 32768:
                            MBPS5 = SurfaceControl.FX_SURFACE_MASK;
                            FS9 = 36864;
                            BR6 = 240000;
                            DPB = 184320;
                            break;
                        case 65536:
                            MBPS5 = 2073600;
                            FS9 = 36864;
                            BR6 = 240000;
                            DPB = 184320;
                            break;
                        case 131072:
                            MBPS5 = 4177920;
                            FS9 = Protocol.BASE_WIFI_P2P_MANAGER;
                            BR6 = 240000;
                            DPB = 696320;
                            break;
                        case 262144:
                            MBPS5 = 8355840;
                            FS9 = Protocol.BASE_WIFI_P2P_MANAGER;
                            BR6 = 480000;
                            DPB = 696320;
                            break;
                        case 524288:
                            MBPS5 = Spanned.SPAN_PRIORITY;
                            FS9 = Protocol.BASE_WIFI_P2P_MANAGER;
                            BR6 = 800000;
                            DPB = 696320;
                            break;
                        default:
                            Log.m64w(TAG, "Unrecognized level " + profileLevel.level + " for " + mime);
                            errors |= 1;
                            break;
                    }
                    int MBPS6 = MBPS5;
                    int FS10 = FS9;
                    int BR7 = BR6;
                    int DPB2 = DPB;
                    int i8 = profileLevel.profile;
                    if (i8 != i6) {
                        if (i8 != 8) {
                            if (i8 == 16) {
                                i5 = maxDPBBlocks;
                                BR5 = BR7 * PathInterpolatorCompat.MAX_NUM_POINTS;
                            } else if (i8 != 32 && i8 != 64) {
                                if (i8 != 65536) {
                                    if (i8 != 524288) {
                                        switch (i8) {
                                            case 1:
                                            case 2:
                                                break;
                                            default:
                                                StringBuilder sb = new StringBuilder();
                                                i5 = maxDPBBlocks;
                                                sb.append("Unrecognized profile ");
                                                sb.append(profileLevel.profile);
                                                sb.append(" for ");
                                                sb.append(mime);
                                                Log.m64w(TAG, sb.toString());
                                                errors |= 1;
                                                BR5 = BR7 * 1000;
                                                break;
                                        }
                                    }
                                }
                                i5 = maxDPBBlocks;
                                BR5 = BR7 * 1000;
                            }
                            if (supported) {
                                errors &= -5;
                            }
                            maxBlocksPerSecond = Math.max(MBPS6, maxBlocksPerSecond);
                            maxBlocks = Math.max(FS10, maxBlocks);
                            maxBps2 = Math.max(BR5, maxBps2);
                            maxDPBBlocks2 = Math.max(maxDPBBlocks2, DPB2);
                            i7++;
                            maxDPBBlocks = i5;
                            i6 = 4;
                        }
                        i5 = maxDPBBlocks;
                        BR5 = BR7 * MetricsProto.MetricsEvent.FIELD_SELECTION_RANGE_START;
                        if (supported) {
                        }
                        maxBlocksPerSecond = Math.max(MBPS6, maxBlocksPerSecond);
                        maxBlocks = Math.max(FS10, maxBlocks);
                        maxBps2 = Math.max(BR5, maxBps2);
                        maxDPBBlocks2 = Math.max(maxDPBBlocks2, DPB2);
                        i7++;
                        maxDPBBlocks = i5;
                        i6 = 4;
                    }
                    i5 = maxDPBBlocks;
                    Log.m64w(TAG, "Unsupported profile " + profileLevel.profile + " for " + mime);
                    errors |= 2;
                    supported = false;
                    BR5 = BR7 * 1000;
                    if (supported) {
                    }
                    maxBlocksPerSecond = Math.max(MBPS6, maxBlocksPerSecond);
                    maxBlocks = Math.max(FS10, maxBlocks);
                    maxBps2 = Math.max(BR5, maxBps2);
                    maxDPBBlocks2 = Math.max(maxDPBBlocks2, DPB2);
                    i7++;
                    maxDPBBlocks = i5;
                    i6 = 4;
                }
                int maxLengthInBlocks = (int) Math.sqrt(maxBlocks * 8);
                long maxBlocksPerSecond2 = maxBlocksPerSecond;
                applyMacroBlockLimits(maxLengthInBlocks, maxLengthInBlocks, maxBlocks, maxBlocksPerSecond2, 16, 16, 1, 1);
                i = 1;
                j = maxBlocksPerSecond2;
                maxBps = maxBps2;
            } else if (!mime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_MPEG2)) {
                String mime2 = mime;
                if (mime2.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_MPEG4)) {
                    int maxBps3 = 64000;
                    CodecProfileLevel[] profileLevels6 = profileLevels5;
                    int H5 = profileLevels6.length;
                    errors = 4;
                    int maxWidth = 11;
                    int maxHeight = 9;
                    int maxRate = 15;
                    int i9 = 0;
                    long maxBlocksPerSecond3 = 1485;
                    int maxBlocks2 = 99;
                    while (i9 < H5) {
                        CodecProfileLevel profileLevel2 = profileLevels6[i9];
                        boolean strict = false;
                        boolean supported2 = true;
                        switch (profileLevel2.profile) {
                            case 1:
                                MBPS = 0;
                                FS5 = 0;
                                int MBPS7 = profileLevel2.level;
                                if (MBPS7 == 4) {
                                    FR2 = 30;
                                    W = 11;
                                    H = 9;
                                    MBPS2 = 1485;
                                    FS6 = 99;
                                    BR = 64;
                                } else if (MBPS7 == 8) {
                                    FR2 = 30;
                                    W = 22;
                                    H = 18;
                                    MBPS2 = 5940;
                                    FS6 = 396;
                                    BR = 128;
                                } else if (MBPS7 == 16) {
                                    FR2 = 30;
                                    W = 22;
                                    H = 18;
                                    MBPS2 = 11880;
                                    FS6 = 396;
                                    BR = MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION;
                                } else if (MBPS7 == 64) {
                                    FR2 = 30;
                                    W = 40;
                                    H = 30;
                                    MBPS2 = 36000;
                                    FS6 = 1200;
                                    BR = 4000;
                                } else if (MBPS7 == 128) {
                                    FR2 = 30;
                                    W = 45;
                                    H = 36;
                                    MBPS2 = 40500;
                                    FS6 = 1620;
                                    BR = 8000;
                                } else if (MBPS7 != 256) {
                                    switch (MBPS7) {
                                        case 1:
                                            strict = true;
                                            FR2 = 15;
                                            W = 11;
                                            H = 9;
                                            MBPS2 = 1485;
                                            FS6 = 99;
                                            BR = 64;
                                            break;
                                        case 2:
                                            strict = true;
                                            FR2 = 15;
                                            W = 11;
                                            H = 9;
                                            MBPS2 = 1485;
                                            FS6 = 99;
                                            BR = 128;
                                            break;
                                        default:
                                            Log.m64w(TAG, "Unrecognized profile/level " + profileLevel2.profile + "/" + profileLevel2.level + " for " + mime2);
                                            errors |= 1;
                                            i3 = H5;
                                            profileLevels4 = profileLevels6;
                                            BR2 = 0;
                                            FR3 = 0;
                                            W2 = 0;
                                            H2 = 0;
                                            MBPS2 = MBPS;
                                            FS6 = FS5;
                                            if (supported2) {
                                                errors &= -5;
                                            }
                                            String mime3 = mime2;
                                            maxBlocksPerSecond3 = Math.max(MBPS2, maxBlocksPerSecond3);
                                            maxBlocks2 = Math.max(FS6, maxBlocks2);
                                            maxBps3 = Math.max(BR2 * 1000, maxBps3);
                                            if (strict) {
                                                int maxHeight2 = FS6 * 2;
                                                int maxDim = (int) Math.sqrt(maxHeight2);
                                                maxWidth = Math.max(maxDim, maxWidth);
                                                int maxHeight3 = Math.max(maxDim, maxHeight);
                                                int W5 = Math.max(Math.max(FR3, 60), maxRate);
                                                maxRate = W5;
                                                maxHeight = maxHeight3;
                                            } else {
                                                maxWidth = Math.max(W2, maxWidth);
                                                int maxHeight4 = Math.max(H2, maxHeight);
                                                maxRate = Math.max(FR3, maxRate);
                                                maxHeight = maxHeight4;
                                            }
                                            i9++;
                                            H5 = i3;
                                            profileLevels6 = profileLevels4;
                                            mime2 = mime3;
                                    }
                                } else {
                                    FR2 = 30;
                                    W = 80;
                                    H = 45;
                                    MBPS2 = 108000;
                                    FS6 = 3600;
                                    BR = 12000;
                                }
                                i3 = H5;
                                profileLevels4 = profileLevels6;
                                BR2 = BR;
                                FR3 = FR2;
                                W2 = W;
                                H2 = H;
                                if (supported2) {
                                }
                                String mime32 = mime2;
                                maxBlocksPerSecond3 = Math.max(MBPS2, maxBlocksPerSecond3);
                                maxBlocks2 = Math.max(FS6, maxBlocks2);
                                maxBps3 = Math.max(BR2 * 1000, maxBps3);
                                if (strict) {
                                }
                                i9++;
                                H5 = i3;
                                profileLevels6 = profileLevels4;
                                mime2 = mime32;
                            case 2:
                            case 4:
                            case 8:
                            case 16:
                            case 32:
                            case 64:
                            case 128:
                            case 256:
                            case 512:
                            case 1024:
                            case 2048:
                            case 4096:
                            case 8192:
                            case 16384:
                                MBPS = 0;
                                FS5 = 0;
                                Log.m68i(TAG, "Unsupported profile " + profileLevel2.profile + " for " + mime2);
                                errors |= 2;
                                supported2 = false;
                                i3 = H5;
                                profileLevels4 = profileLevels6;
                                BR2 = 0;
                                FR3 = 0;
                                W2 = 0;
                                H2 = 0;
                                MBPS2 = MBPS;
                                FS6 = FS5;
                                if (supported2) {
                                }
                                String mime322 = mime2;
                                maxBlocksPerSecond3 = Math.max(MBPS2, maxBlocksPerSecond3);
                                maxBlocks2 = Math.max(FS6, maxBlocks2);
                                maxBps3 = Math.max(BR2 * 1000, maxBps3);
                                if (strict) {
                                }
                                i9++;
                                H5 = i3;
                                profileLevels6 = profileLevels4;
                                mime2 = mime322;
                                break;
                            case 32768:
                                int BR8 = profileLevel2.level;
                                MBPS = 0;
                                if (BR8 == 1 || BR8 == 4) {
                                    FR2 = 30;
                                    W = 11;
                                    H = 9;
                                    MBPS2 = 2970;
                                    FS6 = 99;
                                    BR = 128;
                                } else if (BR8 == 8) {
                                    FR2 = 30;
                                    W = 22;
                                    H = 18;
                                    MBPS2 = 5940;
                                    FS6 = 396;
                                    BR = MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION;
                                } else if (BR8 == 16) {
                                    FR2 = 30;
                                    W = 22;
                                    H = 18;
                                    MBPS2 = 11880;
                                    FS6 = 396;
                                    BR = 768;
                                } else if (BR8 == 24) {
                                    FR2 = 30;
                                    W = 22;
                                    H = 18;
                                    MBPS2 = 11880;
                                    FS6 = 396;
                                    BR = 1500;
                                } else if (BR8 == 32) {
                                    FR2 = 30;
                                    W = 44;
                                    H = 36;
                                    MBPS2 = 23760;
                                    FS6 = 792;
                                    BR = PathInterpolatorCompat.MAX_NUM_POINTS;
                                } else if (BR8 != 128) {
                                    StringBuilder sb2 = new StringBuilder();
                                    FS5 = 0;
                                    sb2.append("Unrecognized profile/level ");
                                    sb2.append(profileLevel2.profile);
                                    sb2.append("/");
                                    sb2.append(profileLevel2.level);
                                    sb2.append(" for ");
                                    sb2.append(mime2);
                                    Log.m64w(TAG, sb2.toString());
                                    errors |= 1;
                                    i3 = H5;
                                    profileLevels4 = profileLevels6;
                                    BR2 = 0;
                                    FR3 = 0;
                                    W2 = 0;
                                    H2 = 0;
                                    MBPS2 = MBPS;
                                    FS6 = FS5;
                                    if (supported2) {
                                    }
                                    String mime3222 = mime2;
                                    maxBlocksPerSecond3 = Math.max(MBPS2, maxBlocksPerSecond3);
                                    maxBlocks2 = Math.max(FS6, maxBlocks2);
                                    maxBps3 = Math.max(BR2 * 1000, maxBps3);
                                    if (strict) {
                                    }
                                    i9++;
                                    H5 = i3;
                                    profileLevels6 = profileLevels4;
                                    mime2 = mime3222;
                                } else {
                                    FR2 = 30;
                                    W = 45;
                                    H = 36;
                                    MBPS2 = 48600;
                                    FS6 = 1620;
                                    BR = 8000;
                                }
                                i3 = H5;
                                profileLevels4 = profileLevels6;
                                BR2 = BR;
                                FR3 = FR2;
                                W2 = W;
                                H2 = H;
                                if (supported2) {
                                }
                                String mime32222 = mime2;
                                maxBlocksPerSecond3 = Math.max(MBPS2, maxBlocksPerSecond3);
                                maxBlocks2 = Math.max(FS6, maxBlocks2);
                                maxBps3 = Math.max(BR2 * 1000, maxBps3);
                                if (strict) {
                                }
                                i9++;
                                H5 = i3;
                                profileLevels6 = profileLevels4;
                                mime2 = mime32222;
                                break;
                            default:
                                MBPS = 0;
                                FS5 = 0;
                                Log.m64w(TAG, "Unrecognized profile " + profileLevel2.profile + " for " + mime2);
                                errors |= 1;
                                i3 = H5;
                                profileLevels4 = profileLevels6;
                                BR2 = 0;
                                FR3 = 0;
                                W2 = 0;
                                H2 = 0;
                                MBPS2 = MBPS;
                                FS6 = FS5;
                                if (supported2) {
                                }
                                String mime322222 = mime2;
                                maxBlocksPerSecond3 = Math.max(MBPS2, maxBlocksPerSecond3);
                                maxBlocks2 = Math.max(FS6, maxBlocks2);
                                maxBps3 = Math.max(BR2 * 1000, maxBps3);
                                if (strict) {
                                }
                                i9++;
                                H5 = i3;
                                profileLevels6 = profileLevels4;
                                mime2 = mime322222;
                                break;
                        }
                    }
                    int maxBps4 = maxBps3;
                    int maxBps5 = maxBlocks2;
                    applyMacroBlockLimits(maxWidth, maxHeight, maxBps5, maxBlocksPerSecond3, 16, 16, 1, 1);
                    this.mFrameRateRange = this.mFrameRateRange.intersect(12, Integer.valueOf(maxRate));
                    maxBps = maxBps4;
                    i = 1;
                } else {
                    VideoCapabilities videoCapabilities = this;
                    if (mime2.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_H263)) {
                        int maxHeight5 = 9;
                        int maxWidth2 = 11;
                        int minH2 = 9;
                        CodecProfileLevel[] profileLevels7 = profileLevels5;
                        int length = profileLevels7.length;
                        errors = 4;
                        int minAlignment = 16;
                        int minAlignment2 = 11;
                        long maxBlocksPerSecond4 = 1485;
                        int minW2 = 0;
                        int maxBlocks3 = 99;
                        int maxBps6 = 64000;
                        int maxRate2 = 15;
                        while (minW2 < length) {
                            CodecProfileLevel profileLevel3 = profileLevels7[minW2];
                            int MBPS8 = 0;
                            int BR9 = 0;
                            int FR6 = 0;
                            int W6 = 0;
                            int H6 = 0;
                            int minW3 = minAlignment2;
                            int minH3 = minH2;
                            boolean strict2 = false;
                            int i10 = profileLevel3.level;
                            int i11 = length;
                            if (i10 == 4) {
                                profileLevels3 = profileLevels7;
                                strict2 = true;
                                FR6 = 30;
                                W6 = 22;
                                H6 = 18;
                                BR9 = 6;
                                MBPS8 = 22 * 18 * 30;
                            } else if (i10 == 8) {
                                profileLevels3 = profileLevels7;
                                strict2 = true;
                                FR6 = 30;
                                W6 = 22;
                                H6 = 18;
                                BR9 = 32;
                                MBPS8 = 22 * 18 * 30;
                            } else if (i10 == 16) {
                                profileLevels3 = profileLevels7;
                                strict2 = profileLevel3.profile == 1 || profileLevel3.profile == 4;
                                if (!strict2) {
                                    minW3 = 1;
                                    minH3 = 1;
                                    minAlignment = 4;
                                }
                                FR6 = 15;
                                W6 = 11;
                                H6 = 9;
                                BR9 = 2;
                                MBPS8 = 11 * 9 * 15;
                            } else if (i10 == 32) {
                                profileLevels3 = profileLevels7;
                                minW3 = 1;
                                minH3 = 1;
                                minAlignment = 4;
                                FR6 = 60;
                                W6 = 22;
                                H6 = 18;
                                BR9 = 64;
                                MBPS8 = 22 * 18 * 50;
                            } else if (i10 == 64) {
                                profileLevels3 = profileLevels7;
                                minW3 = 1;
                                minH3 = 1;
                                minAlignment = 4;
                                FR6 = 60;
                                W6 = 45;
                                H6 = 18;
                                BR9 = 128;
                                MBPS8 = 45 * 18 * 50;
                            } else if (i10 != 128) {
                                switch (i10) {
                                    case 1:
                                        profileLevels3 = profileLevels7;
                                        strict2 = true;
                                        FR6 = 15;
                                        W6 = 11;
                                        H6 = 9;
                                        BR9 = 1;
                                        MBPS8 = 11 * 9 * 15;
                                        break;
                                    case 2:
                                        profileLevels3 = profileLevels7;
                                        strict2 = true;
                                        FR6 = 30;
                                        W6 = 22;
                                        H6 = 18;
                                        BR9 = 2;
                                        MBPS8 = 22 * 18 * 15;
                                        break;
                                    default:
                                        StringBuilder sb3 = new StringBuilder();
                                        profileLevels3 = profileLevels7;
                                        sb3.append("Unrecognized profile/level ");
                                        sb3.append(profileLevel3.profile);
                                        sb3.append("/");
                                        sb3.append(profileLevel3.level);
                                        sb3.append(" for ");
                                        sb3.append(mime2);
                                        Log.m64w(TAG, sb3.toString());
                                        errors |= 1;
                                        break;
                                }
                            } else {
                                profileLevels3 = profileLevels7;
                                minW3 = 1;
                                minH3 = 1;
                                minAlignment = 4;
                                FR6 = 60;
                                W6 = 45;
                                H6 = 36;
                                BR9 = 256;
                                MBPS8 = 45 * 36 * 50;
                            }
                            int i12 = minW2;
                            int MBPS9 = MBPS8;
                            int FR7 = FR6;
                            int W7 = W6;
                            int H7 = H6;
                            int minHeight = minH2;
                            int minHeight2 = profileLevel3.profile;
                            int minWidth = minAlignment2;
                            if (minHeight2 != 4 && minHeight2 != 8 && minHeight2 != 16 && minHeight2 != 32 && minHeight2 != 64 && minHeight2 != 128 && minHeight2 != 256) {
                                switch (minHeight2) {
                                    case 1:
                                    case 2:
                                        break;
                                    default:
                                        StringBuilder sb4 = new StringBuilder();
                                        FR = FR7;
                                        sb4.append("Unrecognized profile ");
                                        sb4.append(profileLevel3.profile);
                                        sb4.append(" for ");
                                        sb4.append(mime2);
                                        Log.m64w(TAG, sb4.toString());
                                        errors |= 1;
                                        break;
                                }
                                if (strict2) {
                                    videoCapabilities.mAllowMbOverride = true;
                                    minW = minW3;
                                    minH = minH3;
                                } else {
                                    minW = 11;
                                    minH = 9;
                                }
                                errors &= -5;
                                maxBlocksPerSecond4 = Math.max(MBPS9, maxBlocksPerSecond4);
                                maxBlocks3 = Math.max(W7 * H7, maxBlocks3);
                                maxBps6 = Math.max(64000 * BR9, maxBps6);
                                maxWidth2 = Math.max(W7, maxWidth2);
                                maxHeight5 = Math.max(H7, maxHeight5);
                                maxRate2 = Math.max(FR, maxRate2);
                                int minWidth2 = Math.min(minW, minWidth);
                                minH2 = Math.min(minH, minHeight);
                                minW2 = i12 + 1;
                                minAlignment2 = minWidth2;
                                length = i11;
                                profileLevels7 = profileLevels3;
                                videoCapabilities = this;
                            }
                            FR = FR7;
                            if (strict2) {
                            }
                            errors &= -5;
                            maxBlocksPerSecond4 = Math.max(MBPS9, maxBlocksPerSecond4);
                            maxBlocks3 = Math.max(W7 * H7, maxBlocks3);
                            maxBps6 = Math.max(64000 * BR9, maxBps6);
                            maxWidth2 = Math.max(W7, maxWidth2);
                            maxHeight5 = Math.max(H7, maxHeight5);
                            maxRate2 = Math.max(FR, maxRate2);
                            int minWidth22 = Math.min(minW, minWidth);
                            minH2 = Math.min(minH, minHeight);
                            minW2 = i12 + 1;
                            minAlignment2 = minWidth22;
                            length = i11;
                            profileLevels7 = profileLevels3;
                            videoCapabilities = this;
                        }
                        int minHeight3 = minH2;
                        int minWidth3 = minAlignment2;
                        if (!this.mAllowMbOverride) {
                            this.mBlockAspectRatioRange = Range.create(new Rational(11, 9), new Rational(11, 9));
                        }
                        int maxRate3 = maxRate2;
                        int maxRate4 = maxWidth2;
                        applyMacroBlockLimits(minWidth3, minHeight3, maxRate4, maxHeight5, maxBlocks3, maxBlocksPerSecond4, 16, 16, minAlignment, minAlignment);
                        this.mFrameRateRange = Range.create(1, Integer.valueOf(maxRate3));
                        maxBps = maxBps6;
                        i = 1;
                    } else {
                        int i13 = 1;
                        if (mime2.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_VP8)) {
                            int length2 = profileLevels5.length;
                            errors = 4;
                            int i14 = 0;
                            while (i14 < length2) {
                                CodecProfileLevel profileLevel4 = profileLevels5[i14];
                                int i15 = profileLevel4.level;
                                if (i15 != 4 && i15 != 8) {
                                    switch (i15) {
                                        case 1:
                                        case 2:
                                            break;
                                        default:
                                            Log.m64w(TAG, "Unrecognized level " + profileLevel4.level + " for " + mime2);
                                            errors |= 1;
                                            break;
                                    }
                                }
                                int i16 = i13;
                                if (profileLevel4.profile != i16) {
                                    Log.m64w(TAG, "Unrecognized profile " + profileLevel4.profile + " for " + mime2);
                                    errors |= 1;
                                }
                                errors &= -5;
                                i14++;
                                i13 = i16;
                            }
                            i = i13;
                            applyMacroBlockLimits(32767, 32767, Integer.MAX_VALUE, 2147483647L, 16, 16, 1, 1);
                            maxBps = 100000000;
                        } else {
                            CodecProfileLevel[] profileLevels8 = profileLevels5;
                            i = 1;
                            if (mime2.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_VP9)) {
                                int maxBlocks4 = 36864;
                                int length3 = profileLevels8.length;
                                maxBps = 200000;
                                errors = 4;
                                int maxDim2 = 512;
                                long maxBlocksPerSecond5 = 829440;
                                int i17 = 0;
                                while (i17 < length3) {
                                    CodecProfileLevel profileLevel5 = profileLevels8[i17];
                                    int BR10 = 0;
                                    int D = 0;
                                    switch (profileLevel5.level) {
                                        case 1:
                                            SR = 829440;
                                            FS3 = 36864;
                                            BR10 = 200;
                                            D = 512;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 2:
                                            SR = 2764800;
                                            FS3 = 73728;
                                            BR10 = 800;
                                            D = 768;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 4:
                                            SR = 4608000;
                                            FS3 = 122880;
                                            BR10 = 1800;
                                            D = 960;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 8:
                                            SR = 9216000;
                                            FS3 = 245760;
                                            BR10 = 3600;
                                            D = 1344;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 16:
                                            SR = 20736000;
                                            FS3 = 552960;
                                            BR10 = 7200;
                                            D = 2048;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 32:
                                            SR = 36864000;
                                            FS3 = SurfaceControl.FX_SURFACE_MASK;
                                            BR10 = 12000;
                                            D = 2752;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 64:
                                            SR = 83558400;
                                            FS3 = 2228224;
                                            BR10 = 18000;
                                            D = 4160;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 128:
                                            SR = 160432128;
                                            FS3 = 2228224;
                                            BR10 = 30000;
                                            D = 4160;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 256:
                                            SR = 311951360;
                                            FS3 = 8912896;
                                            BR10 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                            D = 8384;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 512:
                                            SR = 588251136;
                                            FS3 = 8912896;
                                            BR10 = AutofillManager.MAX_TEMP_AUGMENTED_SERVICE_DURATION_MS;
                                            D = 8384;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 1024:
                                            SR = 1176502272;
                                            FS3 = 8912896;
                                            BR10 = 180000;
                                            D = 8384;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 2048:
                                            SR = 1176502272;
                                            FS3 = 35651584;
                                            BR10 = 180000;
                                            D = 16832;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 4096:
                                            SR = 2353004544L;
                                            FS3 = 35651584;
                                            BR10 = 240000;
                                            D = 16832;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        case 8192:
                                            SR = 4706009088L;
                                            FS3 = 35651584;
                                            BR10 = 480000;
                                            D = 16832;
                                            FS4 = FS3;
                                            SR2 = SR;
                                            break;
                                        default:
                                            Log.m64w(TAG, "Unrecognized level " + profileLevel5.level + " for " + mime2);
                                            errors |= 1;
                                            SR2 = 0L;
                                            FS4 = 0;
                                            break;
                                    }
                                    int i18 = length3;
                                    int i19 = profileLevel5.profile;
                                    if (i19 != 4 && i19 != 8 && i19 != 4096 && i19 != 8192 && i19 != 16384 && i19 != 32768) {
                                        switch (i19) {
                                            case 1:
                                            case 2:
                                                break;
                                            default:
                                                StringBuilder sb5 = new StringBuilder();
                                                profileLevels2 = profileLevels8;
                                                sb5.append("Unrecognized profile ");
                                                sb5.append(profileLevel5.profile);
                                                sb5.append(" for ");
                                                sb5.append(mime2);
                                                Log.m64w(TAG, sb5.toString());
                                                errors |= 1;
                                                continue;
                                                errors &= -5;
                                                maxBlocksPerSecond5 = Math.max(SR2, maxBlocksPerSecond5);
                                                maxBlocks4 = Math.max(FS4, maxBlocks4);
                                                maxBps = Math.max(BR10 * 1000, maxBps);
                                                maxDim2 = Math.max(D, maxDim2);
                                                i17++;
                                                length3 = i18;
                                                profileLevels8 = profileLevels2;
                                        }
                                    }
                                    profileLevels2 = profileLevels8;
                                    errors &= -5;
                                    maxBlocksPerSecond5 = Math.max(SR2, maxBlocksPerSecond5);
                                    maxBlocks4 = Math.max(FS4, maxBlocks4);
                                    maxBps = Math.max(BR10 * 1000, maxBps);
                                    maxDim2 = Math.max(D, maxDim2);
                                    i17++;
                                    length3 = i18;
                                    profileLevels8 = profileLevels2;
                                }
                                int maxLengthInBlocks2 = Utils.divUp(maxDim2, 8);
                                applyMacroBlockLimits(maxLengthInBlocks2, maxLengthInBlocks2, Utils.divUp(maxBlocks4, 64), Utils.divUp(maxBlocksPerSecond5, 64L), 8, 8, 1, 1);
                            } else if (mime2.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_HEVC)) {
                                long maxBlocksPerSecond6 = 576 * 15;
                                long maxBlocksPerSecond7 = maxBlocksPerSecond6;
                                int maxBlocks5 = 576;
                                maxBps = 128000;
                                errors = 4;
                                for (CodecProfileLevel profileLevel6 : profileLevels8) {
                                    double FR8 = 0.0d;
                                    int FS11 = 0;
                                    int BR11 = 0;
                                    switch (profileLevel6.level) {
                                        case 1:
                                        case 2:
                                            FR8 = 15.0d;
                                            FS11 = 36864;
                                            BR11 = 128;
                                            break;
                                        case 4:
                                        case 8:
                                            FR8 = 30.0d;
                                            FS11 = 122880;
                                            BR11 = 1500;
                                            break;
                                        case 16:
                                        case 32:
                                            FR8 = 30.0d;
                                            FS11 = 245760;
                                            BR11 = PathInterpolatorCompat.MAX_NUM_POINTS;
                                            break;
                                        case 64:
                                        case 128:
                                            FR8 = 30.0d;
                                            FS11 = 552960;
                                            BR11 = 6000;
                                            break;
                                        case 256:
                                        case 512:
                                            FR8 = 33.75d;
                                            FS11 = SurfaceControl.FX_SURFACE_MASK;
                                            BR11 = 10000;
                                            break;
                                        case 1024:
                                            FR8 = 30.0d;
                                            FS11 = 2228224;
                                            BR11 = 12000;
                                            break;
                                        case 2048:
                                            FR8 = 30.0d;
                                            FS11 = 2228224;
                                            BR11 = 30000;
                                            break;
                                        case 4096:
                                            FR8 = 60.0d;
                                            FS11 = 2228224;
                                            BR11 = 20000;
                                            break;
                                        case 8192:
                                            FR8 = 60.0d;
                                            FS11 = 2228224;
                                            BR11 = 50000;
                                            break;
                                        case 16384:
                                            FR8 = 30.0d;
                                            FS11 = 8912896;
                                            BR11 = 25000;
                                            break;
                                        case 32768:
                                            FR8 = 30.0d;
                                            FS11 = 8912896;
                                            BR11 = UserHandle.PER_USER_RANGE;
                                            break;
                                        case 65536:
                                            FR8 = 60.0d;
                                            FS11 = 8912896;
                                            BR11 = HealthKeys.BASE_PACKAGE;
                                            break;
                                        case 131072:
                                            FR8 = 60.0d;
                                            FS11 = 8912896;
                                            BR11 = Protocol.BASE_WIFI_SCANNER_SERVICE;
                                            break;
                                        case 262144:
                                            FR8 = 120.0d;
                                            FS11 = 8912896;
                                            BR11 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                            break;
                                        case 524288:
                                            FR8 = 120.0d;
                                            FS11 = 8912896;
                                            BR11 = 240000;
                                            break;
                                        case 1048576:
                                            FR8 = 30.0d;
                                            FS11 = 35651584;
                                            BR11 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                            break;
                                        case 2097152:
                                            FR8 = 30.0d;
                                            FS11 = 35651584;
                                            BR11 = 240000;
                                            break;
                                        case 4194304:
                                            FR8 = 60.0d;
                                            FS11 = 35651584;
                                            BR11 = AutofillManager.MAX_TEMP_AUGMENTED_SERVICE_DURATION_MS;
                                            break;
                                        case 8388608:
                                            FR8 = 60.0d;
                                            FS11 = 35651584;
                                            BR11 = 480000;
                                            break;
                                        case 16777216:
                                            FR8 = 120.0d;
                                            FS11 = 35651584;
                                            BR11 = 240000;
                                            break;
                                        case 33554432:
                                            FR8 = 120.0d;
                                            FS11 = 35651584;
                                            BR11 = 800000;
                                            break;
                                        default:
                                            Log.m64w(TAG, "Unrecognized level " + profileLevel6.level + " for " + mime2);
                                            errors |= 1;
                                            break;
                                    }
                                    int i20 = profileLevel6.profile;
                                    if (i20 != 4 && i20 != 4096 && i20 != 8192) {
                                        switch (i20) {
                                            case 1:
                                            case 2:
                                                break;
                                            default:
                                                Log.m64w(TAG, "Unrecognized profile " + profileLevel6.profile + " for " + mime2);
                                                errors |= 1;
                                                continue;
                                        }
                                    }
                                    errors &= -5;
                                    maxBlocksPerSecond7 = Math.max((int) (FS2 * FR8), maxBlocksPerSecond7);
                                    maxBlocks5 = Math.max(FS11 >> 6, maxBlocks5);
                                    maxBps = Math.max(BR11 * 1000, maxBps);
                                }
                                int maxLengthInBlocks3 = (int) Math.sqrt(maxBlocks5 * 8);
                                applyMacroBlockLimits(maxLengthInBlocks3, maxLengthInBlocks3, maxBlocks5, maxBlocksPerSecond7, 8, 8, 1, 1);
                            } else {
                                CodecProfileLevel[] profileLevels9 = profileLevels8;
                                if (mime2.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AV1)) {
                                    int maxBlocks6 = 36864;
                                    int length4 = profileLevels9.length;
                                    maxBps = 200000;
                                    errors = 4;
                                    int maxDim3 = 512;
                                    long maxBlocksPerSecond8 = 829440;
                                    int i21 = 0;
                                    while (i21 < length4) {
                                        CodecProfileLevel profileLevel7 = profileLevels9[i21];
                                        long SR3 = 0;
                                        int BR12 = 0;
                                        int D2 = 0;
                                        switch (profileLevel7.level) {
                                            case 1:
                                                SR3 = 5529600;
                                                FS = Protocol.BASE_WIFI_MONITOR;
                                                BR12 = 1500;
                                                D2 = 2048;
                                                i2 = length4;
                                                break;
                                            case 2:
                                            case 4:
                                            case 8:
                                                SR3 = 10454400;
                                                FS = 278784;
                                                BR12 = PathInterpolatorCompat.MAX_NUM_POINTS;
                                                D2 = 2816;
                                                i2 = length4;
                                                break;
                                            case 16:
                                                SR3 = 24969600;
                                                FS = 665856;
                                                BR12 = 6000;
                                                D2 = 4352;
                                                i2 = length4;
                                                break;
                                            case 32:
                                            case 64:
                                            case 128:
                                                SR3 = 39938400;
                                                FS = 1065024;
                                                BR12 = 10000;
                                                D2 = 5504;
                                                i2 = length4;
                                                break;
                                            case 256:
                                                SR3 = 77856768;
                                                FS = 2359296;
                                                BR12 = 12000;
                                                D2 = GLES30.GL_COLOR;
                                                i2 = length4;
                                                break;
                                            case 512:
                                            case 1024:
                                            case 2048:
                                                SR3 = 155713536;
                                                FS = 2359296;
                                                BR12 = 20000;
                                                D2 = GLES30.GL_COLOR;
                                                i2 = length4;
                                                break;
                                            case 4096:
                                                SR3 = 273715200;
                                                FS = 8912896;
                                                BR12 = 30000;
                                                D2 = 8192;
                                                i2 = length4;
                                                break;
                                            case 8192:
                                                SR3 = 547430400;
                                                FS = 8912896;
                                                BR12 = HealthKeys.BASE_PACKAGE;
                                                D2 = 8192;
                                                i2 = length4;
                                                break;
                                            case 16384:
                                                SR3 = 1094860800;
                                                FS = 8912896;
                                                BR12 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                                D2 = 8192;
                                                i2 = length4;
                                                break;
                                            case 32768:
                                                SR3 = 1176502272;
                                                FS = 8912896;
                                                BR12 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                                D2 = 8192;
                                                i2 = length4;
                                                break;
                                            case 65536:
                                                SR3 = 1176502272;
                                                FS = 35651584;
                                                BR12 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                                D2 = 16384;
                                                i2 = length4;
                                                break;
                                            case 131072:
                                                SR3 = 2189721600L;
                                                FS = 35651584;
                                                BR12 = UserHandle.PER_USER_RANGE;
                                                D2 = 16384;
                                                i2 = length4;
                                                break;
                                            case 262144:
                                                SR3 = 4379443200L;
                                                FS = 35651584;
                                                BR12 = Protocol.BASE_WIFI_SCANNER_SERVICE;
                                                D2 = 16384;
                                                i2 = length4;
                                                break;
                                            case 524288:
                                                SR3 = 4706009088L;
                                                FS = 35651584;
                                                BR12 = Protocol.BASE_WIFI_SCANNER_SERVICE;
                                                D2 = 16384;
                                                i2 = length4;
                                                break;
                                            default:
                                                StringBuilder sb6 = new StringBuilder();
                                                i2 = length4;
                                                sb6.append("Unrecognized level ");
                                                sb6.append(profileLevel7.level);
                                                sb6.append(" for ");
                                                sb6.append(mime2);
                                                Log.m64w(TAG, sb6.toString());
                                                errors |= 1;
                                                FS = 0;
                                                break;
                                        }
                                        int i22 = profileLevel7.profile;
                                        if (i22 != 4096 && i22 != 8192) {
                                            switch (i22) {
                                                case 1:
                                                case 2:
                                                    break;
                                                default:
                                                    StringBuilder sb7 = new StringBuilder();
                                                    profileLevels = profileLevels9;
                                                    sb7.append("Unrecognized profile ");
                                                    sb7.append(profileLevel7.profile);
                                                    sb7.append(" for ");
                                                    sb7.append(mime2);
                                                    Log.m64w(TAG, sb7.toString());
                                                    errors |= 1;
                                                    continue;
                                                    errors &= -5;
                                                    maxBlocksPerSecond8 = Math.max(SR3, maxBlocksPerSecond8);
                                                    maxBlocks6 = Math.max(FS, maxBlocks6);
                                                    maxBps = Math.max(BR12 * 1000, maxBps);
                                                    maxDim3 = Math.max(D2, maxDim3);
                                                    i21++;
                                                    length4 = i2;
                                                    profileLevels9 = profileLevels;
                                            }
                                        }
                                        profileLevels = profileLevels9;
                                        errors &= -5;
                                        maxBlocksPerSecond8 = Math.max(SR3, maxBlocksPerSecond8);
                                        maxBlocks6 = Math.max(FS, maxBlocks6);
                                        maxBps = Math.max(BR12 * 1000, maxBps);
                                        maxDim3 = Math.max(D2, maxDim3);
                                        i21++;
                                        length4 = i2;
                                        profileLevels9 = profileLevels;
                                    }
                                    int maxLengthInBlocks4 = Utils.divUp(maxDim3, 8);
                                    applyMacroBlockLimits(maxLengthInBlocks4, maxLengthInBlocks4, Utils.divUp(maxBlocks6, 64), Utils.divUp(maxBlocksPerSecond8, 64L), 8, 8, 1, 1);
                                } else {
                                    Log.m64w(TAG, "Unsupported mime " + mime2);
                                    errors = 4 | 2;
                                    maxBps = 64000;
                                }
                            }
                        }
                    }
                }
                this.mBitrateRange = Range.create(Integer.valueOf(i), Integer.valueOf(maxBps));
                this.mParent.mError |= errors;
            } else {
                int maxBps7 = 64000;
                int H8 = profileLevels5.length;
                errors = 4;
                int maxWidth3 = 11;
                int maxHeight6 = 9;
                int maxRate5 = 15;
                int i23 = 0;
                long maxBlocksPerSecond9 = 1485;
                int maxBlocks7 = 99;
                while (i23 < H8) {
                    CodecProfileLevel profileLevel8 = profileLevels5[i23];
                    boolean supported3 = true;
                    switch (profileLevel8.profile) {
                        case 0:
                            MBPS3 = 0;
                            FS7 = 0;
                            int MBPS10 = profileLevel8.level;
                            if (MBPS10 != 1) {
                                Log.m64w(TAG, "Unrecognized profile/level " + profileLevel8.profile + "/" + profileLevel8.level + " for " + mime);
                                errors |= 1;
                                i4 = H8;
                                z = z2;
                                BR4 = 0;
                                FR5 = 0;
                                W4 = 0;
                                H4 = 0;
                                MBPS4 = MBPS3;
                                FS8 = FS7;
                                if (!supported3) {
                                    errors &= -5;
                                }
                                maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                maxBlocks7 = Math.max(FS8, maxBlocks7);
                                maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                maxWidth3 = Math.max(W4, maxWidth3);
                                maxHeight6 = Math.max(H4, maxHeight6);
                                maxRate5 = Math.max(FR5, maxRate5);
                                i23++;
                                H8 = i4;
                                z2 = z;
                                profileLevels5 = profileLevels5;
                                mime = mime;
                            } else {
                                FR4 = 30;
                                W3 = 45;
                                H3 = 36;
                                MBPS4 = 40500;
                                FS8 = 1620;
                                BR3 = 15000;
                                i4 = H8;
                                z = z2;
                                BR4 = BR3;
                                FR5 = FR4;
                                W4 = W3;
                                H4 = H3;
                                if (!supported3) {
                                }
                                maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                maxBlocks7 = Math.max(FS8, maxBlocks7);
                                maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                maxWidth3 = Math.max(W4, maxWidth3);
                                maxHeight6 = Math.max(H4, maxHeight6);
                                maxRate5 = Math.max(FR5, maxRate5);
                                i23++;
                                H8 = i4;
                                z2 = z;
                                profileLevels5 = profileLevels5;
                                mime = mime;
                            }
                        case 1:
                            MBPS3 = 0;
                            FS7 = 0;
                            int MBPS11 = profileLevel8.level;
                            switch (MBPS11) {
                                case 0:
                                    FR4 = 30;
                                    W3 = 22;
                                    H3 = 18;
                                    MBPS4 = 11880;
                                    FS8 = 396;
                                    BR3 = 4000;
                                    i4 = H8;
                                    z = z2;
                                    BR4 = BR3;
                                    FR5 = FR4;
                                    W4 = W3;
                                    H4 = H3;
                                    if (!supported3) {
                                    }
                                    maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                    maxBlocks7 = Math.max(FS8, maxBlocks7);
                                    maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                    maxWidth3 = Math.max(W4, maxWidth3);
                                    maxHeight6 = Math.max(H4, maxHeight6);
                                    maxRate5 = Math.max(FR5, maxRate5);
                                    i23++;
                                    H8 = i4;
                                    z2 = z;
                                    profileLevels5 = profileLevels5;
                                    mime = mime;
                                    break;
                                case 1:
                                    FR4 = 30;
                                    W3 = 45;
                                    H3 = 36;
                                    MBPS4 = 40500;
                                    FS8 = 1620;
                                    BR3 = 15000;
                                    i4 = H8;
                                    z = z2;
                                    BR4 = BR3;
                                    FR5 = FR4;
                                    W4 = W3;
                                    H4 = H3;
                                    if (!supported3) {
                                    }
                                    maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                    maxBlocks7 = Math.max(FS8, maxBlocks7);
                                    maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                    maxWidth3 = Math.max(W4, maxWidth3);
                                    maxHeight6 = Math.max(H4, maxHeight6);
                                    maxRate5 = Math.max(FR5, maxRate5);
                                    i23++;
                                    H8 = i4;
                                    z2 = z;
                                    profileLevels5 = profileLevels5;
                                    mime = mime;
                                    break;
                                case 2:
                                    FR4 = 60;
                                    W3 = 90;
                                    H3 = 68;
                                    MBPS4 = 183600;
                                    FS8 = 6120;
                                    BR3 = MediaPlayer.ProvisioningThread.TIMEOUT_MS;
                                    i4 = H8;
                                    z = z2;
                                    BR4 = BR3;
                                    FR5 = FR4;
                                    W4 = W3;
                                    H4 = H3;
                                    if (!supported3) {
                                    }
                                    maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                    maxBlocks7 = Math.max(FS8, maxBlocks7);
                                    maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                    maxWidth3 = Math.max(W4, maxWidth3);
                                    maxHeight6 = Math.max(H4, maxHeight6);
                                    maxRate5 = Math.max(FR5, maxRate5);
                                    i23++;
                                    H8 = i4;
                                    z2 = z;
                                    profileLevels5 = profileLevels5;
                                    mime = mime;
                                    break;
                                case 3:
                                    FR4 = 60;
                                    W3 = 120;
                                    H3 = 68;
                                    MBPS4 = 244800;
                                    FS8 = 8160;
                                    BR3 = 80000;
                                    i4 = H8;
                                    z = z2;
                                    BR4 = BR3;
                                    FR5 = FR4;
                                    W4 = W3;
                                    H4 = H3;
                                    if (!supported3) {
                                    }
                                    maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                    maxBlocks7 = Math.max(FS8, maxBlocks7);
                                    maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                    maxWidth3 = Math.max(W4, maxWidth3);
                                    maxHeight6 = Math.max(H4, maxHeight6);
                                    maxRate5 = Math.max(FR5, maxRate5);
                                    i23++;
                                    H8 = i4;
                                    z2 = z;
                                    profileLevels5 = profileLevels5;
                                    mime = mime;
                                    break;
                                case 4:
                                    FR4 = 60;
                                    W3 = 120;
                                    H3 = 68;
                                    MBPS4 = 489600;
                                    FS8 = 8160;
                                    BR3 = 80000;
                                    i4 = H8;
                                    z = z2;
                                    BR4 = BR3;
                                    FR5 = FR4;
                                    W4 = W3;
                                    H4 = H3;
                                    if (!supported3) {
                                    }
                                    maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                    maxBlocks7 = Math.max(FS8, maxBlocks7);
                                    maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                    maxWidth3 = Math.max(W4, maxWidth3);
                                    maxHeight6 = Math.max(H4, maxHeight6);
                                    maxRate5 = Math.max(FR5, maxRate5);
                                    i23++;
                                    H8 = i4;
                                    z2 = z;
                                    profileLevels5 = profileLevels5;
                                    mime = mime;
                                    break;
                                default:
                                    Log.m64w(TAG, "Unrecognized profile/level " + profileLevel8.profile + "/" + profileLevel8.level + " for " + mime);
                                    errors |= 1;
                                    i4 = H8;
                                    z = z2;
                                    BR4 = 0;
                                    FR5 = 0;
                                    W4 = 0;
                                    H4 = 0;
                                    MBPS4 = MBPS3;
                                    FS8 = FS7;
                                    if (!supported3) {
                                    }
                                    maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                                    maxBlocks7 = Math.max(FS8, maxBlocks7);
                                    maxBps7 = Math.max(BR4 * 1000, maxBps7);
                                    maxWidth3 = Math.max(W4, maxWidth3);
                                    maxHeight6 = Math.max(H4, maxHeight6);
                                    maxRate5 = Math.max(FR5, maxRate5);
                                    i23++;
                                    H8 = i4;
                                    z2 = z;
                                    profileLevels5 = profileLevels5;
                                    mime = mime;
                                    break;
                            }
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            MBPS3 = 0;
                            StringBuilder sb8 = new StringBuilder();
                            FS7 = 0;
                            sb8.append("Unsupported profile ");
                            sb8.append(profileLevel8.profile);
                            sb8.append(" for ");
                            sb8.append(mime);
                            Log.m68i(TAG, sb8.toString());
                            errors |= 2;
                            supported3 = false;
                            i4 = H8;
                            z = z2;
                            BR4 = 0;
                            FR5 = 0;
                            W4 = 0;
                            H4 = 0;
                            MBPS4 = MBPS3;
                            FS8 = FS7;
                            if (!supported3) {
                            }
                            maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                            maxBlocks7 = Math.max(FS8, maxBlocks7);
                            maxBps7 = Math.max(BR4 * 1000, maxBps7);
                            maxWidth3 = Math.max(W4, maxWidth3);
                            maxHeight6 = Math.max(H4, maxHeight6);
                            maxRate5 = Math.max(FR5, maxRate5);
                            i23++;
                            H8 = i4;
                            z2 = z;
                            profileLevels5 = profileLevels5;
                            mime = mime;
                            break;
                        default:
                            MBPS3 = 0;
                            FS7 = 0;
                            Log.m64w(TAG, "Unrecognized profile " + profileLevel8.profile + " for " + mime);
                            errors |= 1;
                            i4 = H8;
                            z = z2;
                            BR4 = 0;
                            FR5 = 0;
                            W4 = 0;
                            H4 = 0;
                            MBPS4 = MBPS3;
                            FS8 = FS7;
                            if (!supported3) {
                            }
                            maxBlocksPerSecond9 = Math.max(MBPS4, maxBlocksPerSecond9);
                            maxBlocks7 = Math.max(FS8, maxBlocks7);
                            maxBps7 = Math.max(BR4 * 1000, maxBps7);
                            maxWidth3 = Math.max(W4, maxWidth3);
                            maxHeight6 = Math.max(H4, maxHeight6);
                            maxRate5 = Math.max(FR5, maxRate5);
                            i23++;
                            H8 = i4;
                            z2 = z;
                            profileLevels5 = profileLevels5;
                            mime = mime;
                            break;
                    }
                }
                int maxBps8 = maxBps7;
                int maxBps9 = maxBlocks7;
                applyMacroBlockLimits(maxWidth3, maxHeight6, maxBps9, maxBlocksPerSecond9, 16, 16, 1, 1);
                this.mFrameRateRange = this.mFrameRateRange.intersect(12, Integer.valueOf(maxRate5));
                maxBps = maxBps8;
                i = 1;
                j = maxBlocksPerSecond9;
            }
            this.mBitrateRange = Range.create(Integer.valueOf(i), Integer.valueOf(maxBps));
            this.mParent.mError |= errors;
        }
    }

    /* loaded from: classes3.dex */
    public static final class EncoderCapabilities {
        public static final int BITRATE_MODE_CBR = 2;
        public static final int BITRATE_MODE_CQ = 0;
        public static final int BITRATE_MODE_VBR = 1;
        private static final Feature[] bitrates = {new Feature("VBR", 1, true), new Feature("CBR", 2, false), new Feature("CQ", 0, false)};
        private int mBitControl;
        private Range<Integer> mComplexityRange;
        private Integer mDefaultComplexity;
        private Integer mDefaultQuality;
        private CodecCapabilities mParent;
        private Range<Integer> mQualityRange;
        private String mQualityScale;

        public Range<Integer> getQualityRange() {
            return this.mQualityRange;
        }

        public Range<Integer> getComplexityRange() {
            return this.mComplexityRange;
        }

        private static int parseBitrateMode(String mode) {
            Feature[] featureArr;
            for (Feature feat : bitrates) {
                if (feat.mName.equalsIgnoreCase(mode)) {
                    return feat.mValue;
                }
            }
            return 0;
        }

        public boolean isBitrateModeSupported(int mode) {
            Feature[] featureArr;
            for (Feature feat : bitrates) {
                if (mode == feat.mValue) {
                    return (this.mBitControl & (1 << mode)) != 0;
                }
            }
            return false;
        }

        private EncoderCapabilities() {
        }

        public static EncoderCapabilities create(MediaFormat info, CodecCapabilities parent) {
            EncoderCapabilities caps = new EncoderCapabilities();
            caps.init(info, parent);
            return caps;
        }

        private void init(MediaFormat info, CodecCapabilities parent) {
            this.mParent = parent;
            this.mComplexityRange = Range.create(0, 0);
            this.mQualityRange = Range.create(0, 0);
            this.mBitControl = 2;
            applyLevelLimits();
            parseFromInfo(info);
        }

        private void applyLevelLimits() {
            String mime = this.mParent.getMimeType();
            if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_FLAC)) {
                this.mComplexityRange = Range.create(0, 8);
                this.mBitControl = 1;
            } else if (mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AMR_NB) || mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AMR_WB) || mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_G711_MLAW) || mime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                this.mBitControl = 4;
            }
        }

        private void parseFromInfo(MediaFormat info) {
            String[] split;
            Map<String, Object> map = info.getMap();
            if (info.containsKey("complexity-range")) {
                this.mComplexityRange = Utils.parseIntRange(info.getString("complexity-range"), this.mComplexityRange);
            }
            if (info.containsKey("quality-range")) {
                this.mQualityRange = Utils.parseIntRange(info.getString("quality-range"), this.mQualityRange);
            }
            if (info.containsKey("feature-bitrate-modes")) {
                for (String mode : info.getString("feature-bitrate-modes").split(SmsManager.REGEX_PREFIX_DELIMITER)) {
                    this.mBitControl |= 1 << parseBitrateMode(mode);
                }
            }
            try {
                this.mDefaultComplexity = Integer.valueOf(Integer.parseInt((String) map.get("complexity-default")));
            } catch (NumberFormatException e) {
            }
            try {
                this.mDefaultQuality = Integer.valueOf(Integer.parseInt((String) map.get("quality-default")));
            } catch (NumberFormatException e2) {
            }
            this.mQualityScale = (String) map.get("quality-scale");
        }

        private boolean supports(Integer complexity, Integer quality, Integer profile) {
            boolean ok = true;
            if (1 != 0 && complexity != null) {
                ok = this.mComplexityRange.contains((Range<Integer>) complexity);
            }
            if (ok && quality != null) {
                ok = this.mQualityRange.contains((Range<Integer>) quality);
            }
            if (ok && profile != null) {
                CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
                int length = codecProfileLevelArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    CodecProfileLevel pl = codecProfileLevelArr[i];
                    if (pl.profile != profile.intValue()) {
                        i++;
                    } else {
                        profile = null;
                        break;
                    }
                }
                return profile == null;
            }
            return ok;
        }

        public void getDefaultFormat(MediaFormat format) {
            Feature[] featureArr;
            if (!this.mQualityRange.getUpper().equals(this.mQualityRange.getLower()) && this.mDefaultQuality != null) {
                format.setInteger(MediaFormat.KEY_QUALITY, this.mDefaultQuality.intValue());
            }
            if (!this.mComplexityRange.getUpper().equals(this.mComplexityRange.getLower()) && this.mDefaultComplexity != null) {
                format.setInteger(MediaFormat.KEY_COMPLEXITY, this.mDefaultComplexity.intValue());
            }
            for (Feature feat : bitrates) {
                if ((this.mBitControl & (1 << feat.mValue)) != 0) {
                    format.setInteger(MediaFormat.KEY_BITRATE_MODE, feat.mValue);
                    return;
                }
            }
        }

        public boolean supportsFormat(MediaFormat format) {
            Map<String, Object> map = format.getMap();
            String mime = this.mParent.getMimeType();
            Integer mode = (Integer) map.get(MediaFormat.KEY_BITRATE_MODE);
            if (mode != null && !isBitrateModeSupported(mode.intValue())) {
                return false;
            }
            Integer complexity = (Integer) map.get(MediaFormat.KEY_COMPLEXITY);
            if (MediaFormat.MIMETYPE_AUDIO_FLAC.equalsIgnoreCase(mime)) {
                Integer flacComplexity = (Integer) map.get(MediaFormat.KEY_FLAC_COMPRESSION_LEVEL);
                if (complexity == null) {
                    complexity = flacComplexity;
                } else if (flacComplexity != null && !complexity.equals(flacComplexity)) {
                    throw new IllegalArgumentException("conflicting values for complexity and flac-compression-level");
                }
            }
            Integer profile = (Integer) map.get(MediaFormat.KEY_PROFILE);
            if (MediaFormat.MIMETYPE_AUDIO_AAC.equalsIgnoreCase(mime)) {
                Integer aacProfile = (Integer) map.get(MediaFormat.KEY_AAC_PROFILE);
                if (profile == null) {
                    profile = aacProfile;
                } else if (aacProfile != null && !aacProfile.equals(profile)) {
                    throw new IllegalArgumentException("conflicting values for profile and aac-profile");
                }
            }
            Integer quality = (Integer) map.get(MediaFormat.KEY_QUALITY);
            return supports(complexity, quality, profile);
        }
    }

    /* loaded from: classes3.dex */
    public static final class CodecProfileLevel {
        public static final int AACObjectELD = 39;
        public static final int AACObjectERLC = 17;
        public static final int AACObjectERScalable = 20;
        public static final int AACObjectHE = 5;
        public static final int AACObjectHE_PS = 29;
        public static final int AACObjectLC = 2;
        public static final int AACObjectLD = 23;
        public static final int AACObjectLTP = 4;
        public static final int AACObjectMain = 1;
        public static final int AACObjectSSR = 3;
        public static final int AACObjectScalable = 6;
        public static final int AACObjectXHE = 42;
        public static final int AV1Level2 = 1;
        public static final int AV1Level21 = 2;
        public static final int AV1Level22 = 4;
        public static final int AV1Level23 = 8;
        public static final int AV1Level3 = 16;
        public static final int AV1Level31 = 32;
        public static final int AV1Level32 = 64;
        public static final int AV1Level33 = 128;
        public static final int AV1Level4 = 256;
        public static final int AV1Level41 = 512;
        public static final int AV1Level42 = 1024;
        public static final int AV1Level43 = 2048;
        public static final int AV1Level5 = 4096;
        public static final int AV1Level51 = 8192;
        public static final int AV1Level52 = 16384;
        public static final int AV1Level53 = 32768;
        public static final int AV1Level6 = 65536;
        public static final int AV1Level61 = 131072;
        public static final int AV1Level62 = 262144;
        public static final int AV1Level63 = 524288;
        public static final int AV1Level7 = 1048576;
        public static final int AV1Level71 = 2097152;
        public static final int AV1Level72 = 4194304;
        public static final int AV1Level73 = 8388608;
        public static final int AV1ProfileMain10 = 2;
        public static final int AV1ProfileMain10HDR10 = 4096;
        public static final int AV1ProfileMain10HDR10Plus = 8192;
        public static final int AV1ProfileMain8 = 1;
        public static final int AVCLevel1 = 1;
        public static final int AVCLevel11 = 4;
        public static final int AVCLevel12 = 8;
        public static final int AVCLevel13 = 16;
        public static final int AVCLevel1b = 2;
        public static final int AVCLevel2 = 32;
        public static final int AVCLevel21 = 64;
        public static final int AVCLevel22 = 128;
        public static final int AVCLevel3 = 256;
        public static final int AVCLevel31 = 512;
        public static final int AVCLevel32 = 1024;
        public static final int AVCLevel4 = 2048;
        public static final int AVCLevel41 = 4096;
        public static final int AVCLevel42 = 8192;
        public static final int AVCLevel5 = 16384;
        public static final int AVCLevel51 = 32768;
        public static final int AVCLevel52 = 65536;
        public static final int AVCLevel6 = 131072;
        public static final int AVCLevel61 = 262144;
        public static final int AVCLevel62 = 524288;
        public static final int AVCProfileBaseline = 1;
        public static final int AVCProfileConstrainedBaseline = 65536;
        public static final int AVCProfileConstrainedHigh = 524288;
        public static final int AVCProfileExtended = 4;
        public static final int AVCProfileHigh = 8;
        public static final int AVCProfileHigh10 = 16;
        public static final int AVCProfileHigh422 = 32;
        public static final int AVCProfileHigh444 = 64;
        public static final int AVCProfileMain = 2;
        public static final int DolbyVisionLevelFhd24 = 4;
        public static final int DolbyVisionLevelFhd30 = 8;
        public static final int DolbyVisionLevelFhd60 = 16;
        public static final int DolbyVisionLevelHd24 = 1;
        public static final int DolbyVisionLevelHd30 = 2;
        public static final int DolbyVisionLevelUhd24 = 32;
        public static final int DolbyVisionLevelUhd30 = 64;
        public static final int DolbyVisionLevelUhd48 = 128;
        public static final int DolbyVisionLevelUhd60 = 256;
        public static final int DolbyVisionProfileDvavPen = 2;
        public static final int DolbyVisionProfileDvavPer = 1;
        public static final int DolbyVisionProfileDvavSe = 512;
        public static final int DolbyVisionProfileDvheDen = 8;
        public static final int DolbyVisionProfileDvheDer = 4;
        public static final int DolbyVisionProfileDvheDtb = 128;
        public static final int DolbyVisionProfileDvheDth = 64;
        public static final int DolbyVisionProfileDvheDtr = 16;
        public static final int DolbyVisionProfileDvheSt = 256;
        public static final int DolbyVisionProfileDvheStn = 32;
        public static final int H263Level10 = 1;
        public static final int H263Level20 = 2;
        public static final int H263Level30 = 4;
        public static final int H263Level40 = 8;
        public static final int H263Level45 = 16;
        public static final int H263Level50 = 32;
        public static final int H263Level60 = 64;
        public static final int H263Level70 = 128;
        public static final int H263ProfileBackwardCompatible = 4;
        public static final int H263ProfileBaseline = 1;
        public static final int H263ProfileH320Coding = 2;
        public static final int H263ProfileHighCompression = 32;
        public static final int H263ProfileHighLatency = 256;
        public static final int H263ProfileISWV2 = 8;
        public static final int H263ProfileISWV3 = 16;
        public static final int H263ProfileInterlace = 128;
        public static final int H263ProfileInternet = 64;
        public static final int HEVCHighTierLevel1 = 2;
        public static final int HEVCHighTierLevel2 = 8;
        public static final int HEVCHighTierLevel21 = 32;
        public static final int HEVCHighTierLevel3 = 128;
        public static final int HEVCHighTierLevel31 = 512;
        public static final int HEVCHighTierLevel4 = 2048;
        public static final int HEVCHighTierLevel41 = 8192;
        public static final int HEVCHighTierLevel5 = 32768;
        public static final int HEVCHighTierLevel51 = 131072;
        public static final int HEVCHighTierLevel52 = 524288;
        public static final int HEVCHighTierLevel6 = 2097152;
        public static final int HEVCHighTierLevel61 = 8388608;
        public static final int HEVCHighTierLevel62 = 33554432;
        private static final int HEVCHighTierLevels = 44739242;
        public static final int HEVCMainTierLevel1 = 1;
        public static final int HEVCMainTierLevel2 = 4;
        public static final int HEVCMainTierLevel21 = 16;
        public static final int HEVCMainTierLevel3 = 64;
        public static final int HEVCMainTierLevel31 = 256;
        public static final int HEVCMainTierLevel4 = 1024;
        public static final int HEVCMainTierLevel41 = 4096;
        public static final int HEVCMainTierLevel5 = 16384;
        public static final int HEVCMainTierLevel51 = 65536;
        public static final int HEVCMainTierLevel52 = 262144;
        public static final int HEVCMainTierLevel6 = 1048576;
        public static final int HEVCMainTierLevel61 = 4194304;
        public static final int HEVCMainTierLevel62 = 16777216;
        public static final int HEVCProfileMain = 1;
        public static final int HEVCProfileMain10 = 2;
        public static final int HEVCProfileMain10HDR10 = 4096;
        public static final int HEVCProfileMain10HDR10Plus = 8192;
        public static final int HEVCProfileMainStill = 4;
        public static final int MPEG2LevelH14 = 2;
        public static final int MPEG2LevelHL = 3;
        public static final int MPEG2LevelHP = 4;
        public static final int MPEG2LevelLL = 0;
        public static final int MPEG2LevelML = 1;
        public static final int MPEG2Profile422 = 2;
        public static final int MPEG2ProfileHigh = 5;
        public static final int MPEG2ProfileMain = 1;
        public static final int MPEG2ProfileSNR = 3;
        public static final int MPEG2ProfileSimple = 0;
        public static final int MPEG2ProfileSpatial = 4;
        public static final int MPEG4Level0 = 1;
        public static final int MPEG4Level0b = 2;
        public static final int MPEG4Level1 = 4;
        public static final int MPEG4Level2 = 8;
        public static final int MPEG4Level3 = 16;
        public static final int MPEG4Level3b = 24;
        public static final int MPEG4Level4 = 32;
        public static final int MPEG4Level4a = 64;
        public static final int MPEG4Level5 = 128;
        public static final int MPEG4Level6 = 256;
        public static final int MPEG4ProfileAdvancedCoding = 4096;
        public static final int MPEG4ProfileAdvancedCore = 8192;
        public static final int MPEG4ProfileAdvancedRealTime = 1024;
        public static final int MPEG4ProfileAdvancedScalable = 16384;
        public static final int MPEG4ProfileAdvancedSimple = 32768;
        public static final int MPEG4ProfileBasicAnimated = 256;
        public static final int MPEG4ProfileCore = 4;
        public static final int MPEG4ProfileCoreScalable = 2048;
        public static final int MPEG4ProfileHybrid = 512;
        public static final int MPEG4ProfileMain = 8;
        public static final int MPEG4ProfileNbit = 16;
        public static final int MPEG4ProfileScalableTexture = 32;
        public static final int MPEG4ProfileSimple = 1;
        public static final int MPEG4ProfileSimpleFBA = 128;
        public static final int MPEG4ProfileSimpleFace = 64;
        public static final int MPEG4ProfileSimpleScalable = 2;
        public static final int VP8Level_Version0 = 1;
        public static final int VP8Level_Version1 = 2;
        public static final int VP8Level_Version2 = 4;
        public static final int VP8Level_Version3 = 8;
        public static final int VP8ProfileMain = 1;
        public static final int VP9Level1 = 1;
        public static final int VP9Level11 = 2;
        public static final int VP9Level2 = 4;
        public static final int VP9Level21 = 8;
        public static final int VP9Level3 = 16;
        public static final int VP9Level31 = 32;
        public static final int VP9Level4 = 64;
        public static final int VP9Level41 = 128;
        public static final int VP9Level5 = 256;
        public static final int VP9Level51 = 512;
        public static final int VP9Level52 = 1024;
        public static final int VP9Level6 = 2048;
        public static final int VP9Level61 = 4096;
        public static final int VP9Level62 = 8192;
        public static final int VP9Profile0 = 1;
        public static final int VP9Profile1 = 2;
        public static final int VP9Profile2 = 4;
        public static final int VP9Profile2HDR = 4096;
        public static final int VP9Profile2HDR10Plus = 16384;
        public static final int VP9Profile3 = 8;
        public static final int VP9Profile3HDR = 8192;
        public static final int VP9Profile3HDR10Plus = 32768;
        public int level;
        public int profile;

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof CodecProfileLevel)) {
                return false;
            }
            CodecProfileLevel other = (CodecProfileLevel) obj;
            return other.profile == this.profile && other.level == this.level;
        }

        public int hashCode() {
            return Long.hashCode((this.profile << 32) | this.level);
        }
    }

    public final CodecCapabilities getCapabilitiesForType(String type) {
        CodecCapabilities caps = this.mCaps.get(type);
        if (caps == null) {
            throw new IllegalArgumentException("codec does not support type");
        }
        return caps.dup();
    }

    public MediaCodecInfo makeRegular() {
        ArrayList<CodecCapabilities> caps = new ArrayList<>();
        for (CodecCapabilities c : this.mCaps.values()) {
            if (c.isRegular()) {
                caps.add(c);
            }
        }
        if (caps.size() == 0) {
            return null;
        }
        if (caps.size() == this.mCaps.size()) {
            return this;
        }
        return new MediaCodecInfo(this.mName, this.mCanonicalName, this.mFlags, (CodecCapabilities[]) caps.toArray(new CodecCapabilities[caps.size()]));
    }
}
