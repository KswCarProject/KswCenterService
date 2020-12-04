package android.media;

import android.annotation.UnsupportedAppUsage;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.os.SystemProperties;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.nano.MetricsProto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public final class MediaCodecInfo {
    /* access modifiers changed from: private */
    public static final Range<Integer> BITRATE_RANGE = Range.create(0, 500000000);
    private static final int DEFAULT_MAX_SUPPORTED_INSTANCES = 32;
    private static final int ERROR_NONE_SUPPORTED = 4;
    private static final int ERROR_UNRECOGNIZED = 1;
    private static final int ERROR_UNSUPPORTED = 2;
    private static final int FLAG_IS_ENCODER = 1;
    private static final int FLAG_IS_HARDWARE_ACCELERATED = 8;
    private static final int FLAG_IS_SOFTWARE_ONLY = 4;
    private static final int FLAG_IS_VENDOR = 2;
    /* access modifiers changed from: private */
    public static final Range<Integer> FRAME_RATE_RANGE = Range.create(0, 960);
    private static final int MAX_SUPPORTED_INSTANCES_LIMIT = 256;
    /* access modifiers changed from: private */
    public static final Range<Integer> POSITIVE_INTEGERS = Range.create(1, Integer.MAX_VALUE);
    /* access modifiers changed from: private */
    public static final Range<Long> POSITIVE_LONGS = Range.create(1L, Long.MAX_VALUE);
    /* access modifiers changed from: private */
    public static final Range<Rational> POSITIVE_RATIONALS = Range.create(new Rational(1, Integer.MAX_VALUE), new Rational(Integer.MAX_VALUE, 1));
    /* access modifiers changed from: private */
    public static final Range<Integer> SIZE_RANGE = Range.create(1, 32768);
    private static final String TAG = "MediaCodecInfo";
    private String mCanonicalName;
    private Map<String, CodecCapabilities> mCaps = new HashMap();
    private int mFlags;
    private String mName;

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

    /* access modifiers changed from: private */
    public static int checkPowerOfTwo(int value, String message) {
        if (((value - 1) & value) == 0) {
            return value;
        }
        throw new IllegalArgumentException(message);
    }

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
        public static final String FEATURE_AdaptivePlayback = "adaptive-playback";
        public static final String FEATURE_DynamicTimestamp = "dynamic-timestamp";
        public static final String FEATURE_FrameParsing = "frame-parsing";
        public static final String FEATURE_IntraRefresh = "intra-refresh";
        public static final String FEATURE_MultipleFrames = "multiple-frames";
        public static final String FEATURE_PartialFrame = "partial-frame";
        public static final String FEATURE_SecurePlayback = "secure-playback";
        public static final String FEATURE_TunneledPlayback = "tunneled-playback";
        private static final String TAG = "CodecCapabilities";
        private static final Feature[] decoderFeatures = {new Feature(FEATURE_AdaptivePlayback, 1, true), new Feature(FEATURE_SecurePlayback, 2, false), new Feature(FEATURE_TunneledPlayback, 4, false), new Feature(FEATURE_PartialFrame, 8, false), new Feature(FEATURE_FrameParsing, 16, false), new Feature(FEATURE_MultipleFrames, 32, false), new Feature(FEATURE_DynamicTimestamp, 64, false)};
        private static final Feature[] encoderFeatures = {new Feature(FEATURE_IntraRefresh, 1, false), new Feature(FEATURE_MultipleFrames, 2, false), new Feature(FEATURE_DynamicTimestamp, 4, false)};
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
            Feature[] validFeatures = getValidFeatures();
            int length = validFeatures.length;
            int i = 0;
            while (i < length) {
                Feature feat = validFeatures[i];
                if (!feat.mName.equals(name)) {
                    i++;
                } else if ((feat.mValue & flags) != 0) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        public boolean isRegular() {
            for (Feature feat : getValidFeatures()) {
                if (!feat.mDefault && isFeatureRequired(feat.mName)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isFormatSupported(MediaFormat format) {
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
            if (this.mVideoCaps != null && !this.mVideoCaps.supportsFormat(format)) {
                return false;
            }
            if (this.mEncoderCaps == null || this.mEncoderCaps.supportsFormat(format)) {
                return true;
            }
            return false;
        }

        /* access modifiers changed from: private */
        public static boolean supportsBitrate(Range<Integer> bitrateRange, MediaFormat format) {
            Map<String, Object> map = format.getMap();
            Integer maxBitrate = (Integer) map.get(MediaFormat.KEY_MAX_BIT_RATE);
            Integer bitrate = (Integer) map.get(MediaFormat.KEY_BIT_RATE);
            if (bitrate == null) {
                bitrate = maxBitrate;
            } else if (maxBitrate != null) {
                bitrate = Integer.valueOf(Math.max(bitrate.intValue(), maxBitrate.intValue()));
            }
            if (bitrate == null || bitrate.intValue() <= 0) {
                return true;
            }
            return bitrateRange.contains(bitrate);
        }

        private boolean supportsProfileLevel(int profile, Integer level) {
            for (CodecProfileLevel pl : this.profileLevels) {
                if (pl.profile == profile) {
                    if (level == null || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_AUDIO_AAC)) {
                        return true;
                    }
                    if ((!this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_H263) || pl.level == level.intValue() || pl.level != 16 || level.intValue() <= 1) && (!this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_MPEG4) || pl.level == level.intValue() || pl.level != 4 || level.intValue() <= 1)) {
                        if (this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_HEVC)) {
                            boolean supportsHighTier = (pl.level & 44739242) != 0;
                            if (((44739242 & level.intValue()) != 0) && !supportsHighTier) {
                            }
                        }
                        if (pl.level >= level.intValue()) {
                            if (createFromProfileLevel(this.mMime, profile, pl.level) == null) {
                                return true;
                            }
                            if (createFromProfileLevel(this.mMime, profile, level.intValue()) != null) {
                                return true;
                            }
                            return false;
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
            MediaFormat mediaFormat = info;
            Map<String, Object> map = info.getMap();
            this.colorFormats = colFmts;
            int i = 0;
            this.mFlagsVerified = 0;
            this.mDefaultFormat = defaultFormat;
            this.mCapabilitiesInfo = mediaFormat;
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
                this.mAudioCaps = AudioCapabilities.create(mediaFormat, this);
                this.mAudioCaps.getDefaultFormat(this.mDefaultFormat);
            } else if (this.mMime.toLowerCase().startsWith("video/") || this.mMime.equalsIgnoreCase(MediaFormat.MIMETYPE_IMAGE_ANDROID_HEIC)) {
                this.mVideoCaps = VideoCapabilities.create(mediaFormat, this);
            }
            if (encoder) {
                this.mEncoderCaps = EncoderCapabilities.create(mediaFormat, this);
                this.mEncoderCaps.getDefaultFormat(this.mDefaultFormat);
            }
            this.mMaxSupportedInstances = Utils.parseIntSafely(MediaCodecList.getGlobalSettings().get("max-concurrent-instances"), 32);
            this.mMaxSupportedInstances = ((Integer) Range.create(1, 256).clamp(Integer.valueOf(Utils.parseIntSafely(map.get("max-concurrent-instances"), this.mMaxSupportedInstances)))).intValue();
            Feature[] validFeatures = getValidFeatures();
            int length = validFeatures.length;
            while (i < length) {
                Feature feat = validFeatures[i];
                String key = MediaFormat.KEY_FEATURE_ + feat.mName;
                Integer yesNo = (Integer) map.get(key);
                if (yesNo != null) {
                    if (yesNo.intValue() > 0) {
                        this.mFlagsRequired = feat.mValue | this.mFlagsRequired;
                    }
                    this.mFlagsSupported |= feat.mValue;
                    this.mDefaultFormat.setInteger(key, 1);
                }
                i++;
                MediaFormat mediaFormat2 = info;
            }
        }
    }

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
            this.mSampleRateRanges = new Range[]{Range.create(Integer.valueOf(SystemProperties.getInt("ro.mediacodec.min_sample_rate", 7350)), Integer.valueOf(SystemProperties.getInt("ro.mediacodec.max_sample_rate", AudioFormat.SAMPLE_RATE_HZ_MAX)))};
            this.mSampleRates = null;
        }

        private boolean supports(Integer sampleRate, Integer inputChannels) {
            if (inputChannels == null || (inputChannels.intValue() >= 1 && inputChannels.intValue() <= this.mMaxInputChannelCount)) {
                return sampleRate == null || Utils.binarySearchDistinctRanges(this.mSampleRateRanges, sampleRate) >= 0;
            }
            return false;
        }

        public boolean isSampleRateSupported(int sampleRate) {
            return supports(Integer.valueOf(sampleRate), (Integer) null);
        }

        private void limitSampleRates(int[] rates) {
            Arrays.sort(rates);
            ArrayList<Range<Integer>> ranges = new ArrayList<>();
            for (int rate : rates) {
                if (supports(Integer.valueOf(rate), (Integer) null)) {
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
                sampleRateRange = Range.create(8000, Integer.valueOf(AudioFormat.SAMPLE_RATE_HZ_MAX));
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
                Log.w(TAG, "Unsupported mime " + mime);
                CodecCapabilities codecCapabilities = this.mParent;
                codecCapabilities.mError = codecCapabilities.mError | 2;
            }
            if (sampleRates != null) {
                limitSampleRates(sampleRates);
            } else if (sampleRateRange != null) {
                limitSampleRates((Range<Integer>[]) new Range[]{sampleRateRange});
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
                    rateRanges[i] = Utils.parseIntRange(rateStrings[i], (Range<Integer>) null);
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
            if (supports((Integer) map.get(MediaFormat.KEY_SAMPLE_RATE), (Integer) map.get(MediaFormat.KEY_CHANNEL_COUNT)) && CodecCapabilities.supportsBitrate(this.mBitrateRange, format)) {
                return true;
            }
            return false;
        }
    }

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
                if (!this.mHeightRange.contains(Integer.valueOf(height)) || height % this.mHeightAlignment != 0) {
                    throw new IllegalArgumentException("unsupported height");
                }
                int heightInBlocks = Utils.divUp(height, this.mBlockHeight);
                Range<Integer> range2 = range.intersect(Integer.valueOf(((Math.max(Utils.divUp(this.mBlockCountRange.getLower().intValue(), heightInBlocks), (int) Math.ceil(this.mBlockAspectRatioRange.getLower().doubleValue() * ((double) heightInBlocks))) - 1) * this.mBlockWidth) + this.mWidthAlignment), Integer.valueOf(this.mBlockWidth * Math.min(this.mBlockCountRange.getUpper().intValue() / heightInBlocks, (int) (this.mBlockAspectRatioRange.getUpper().doubleValue() * ((double) heightInBlocks)))));
                if (height > this.mSmallerDimensionUpperLimit) {
                    range2 = range2.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                return range2.intersect(Integer.valueOf((int) Math.ceil(this.mAspectRatioRange.getLower().doubleValue() * ((double) height))), Integer.valueOf((int) (this.mAspectRatioRange.getUpper().doubleValue() * ((double) height))));
            } catch (IllegalArgumentException e) {
                Log.v(TAG, "could not get supported widths for " + height);
                throw new IllegalArgumentException("unsupported height");
            }
        }

        public Range<Integer> getSupportedHeightsFor(int width) {
            try {
                Range<Integer> range = this.mHeightRange;
                if (!this.mWidthRange.contains(Integer.valueOf(width)) || width % this.mWidthAlignment != 0) {
                    throw new IllegalArgumentException("unsupported width");
                }
                int widthInBlocks = Utils.divUp(width, this.mBlockWidth);
                Range<Integer> range2 = range.intersect(Integer.valueOf(((Math.max(Utils.divUp(this.mBlockCountRange.getLower().intValue(), widthInBlocks), (int) Math.ceil(((double) widthInBlocks) / this.mBlockAspectRatioRange.getUpper().doubleValue())) - 1) * this.mBlockHeight) + this.mHeightAlignment), Integer.valueOf(this.mBlockHeight * Math.min(this.mBlockCountRange.getUpper().intValue() / widthInBlocks, (int) (((double) widthInBlocks) / this.mBlockAspectRatioRange.getLower().doubleValue()))));
                if (width > this.mSmallerDimensionUpperLimit) {
                    range2 = range2.intersect(1, Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                return range2.intersect(Integer.valueOf((int) Math.ceil(((double) width) / this.mAspectRatioRange.getUpper().doubleValue())), Integer.valueOf((int) (((double) width) / this.mAspectRatioRange.getLower().doubleValue())));
            } catch (IllegalArgumentException e) {
                Log.v(TAG, "could not get supported heights for " + width);
                throw new IllegalArgumentException("unsupported width");
            }
        }

        public Range<Double> getSupportedFrameRatesFor(int width, int height) {
            Range<Integer> range = this.mHeightRange;
            if (supports(Integer.valueOf(width), Integer.valueOf(height), (Number) null)) {
                int blockCount = Utils.divUp(width, this.mBlockWidth) * Utils.divUp(height, this.mBlockHeight);
                return Range.create(Double.valueOf(Math.max(((double) this.mBlocksPerSecondRange.getLower().longValue()) / ((double) blockCount), (double) this.mFrameRateRange.getLower().intValue())), Double.valueOf(Math.min(((double) this.mBlocksPerSecondRange.getUpper().longValue()) / ((double) blockCount), (double) this.mFrameRateRange.getUpper().intValue())));
            }
            throw new IllegalArgumentException("unsupported size");
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
            Double ratio = Double.valueOf(((double) getBlockCount(size.getWidth(), size.getHeight())) / ((double) Math.max(getBlockCount(width, height), 1)));
            return Range.create(Double.valueOf(((double) range.getLower().longValue()) * ratio.doubleValue()), Double.valueOf(((double) range.getUpper().longValue()) * ratio.doubleValue()));
        }

        public Range<Double> getAchievableFrameRatesFor(int width, int height) {
            if (!supports(Integer.valueOf(width), Integer.valueOf(height), (Number) null)) {
                throw new IllegalArgumentException("unsupported size");
            } else if (this.mMeasuredFrameRates != null && this.mMeasuredFrameRates.size() > 0) {
                return estimateFrameRatesFor(width, height);
            } else {
                Log.w(TAG, "Codec did not publish any measurement data.");
                return null;
            }
        }

        public static final class PerformancePoint {
            public static final PerformancePoint FHD_100 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 100);
            public static final PerformancePoint FHD_120 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 120);
            public static final PerformancePoint FHD_200 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 200);
            public static final PerformancePoint FHD_24 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 24);
            public static final PerformancePoint FHD_240 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 240);
            public static final PerformancePoint FHD_25 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 25);
            public static final PerformancePoint FHD_30 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 30);
            public static final PerformancePoint FHD_50 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 50);
            public static final PerformancePoint FHD_60 = new PerformancePoint(LegacyCameraDevice.MAX_DIMEN_FOR_ROUNDING, 1080, 60);
            public static final PerformancePoint HD_100 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 100);
            public static final PerformancePoint HD_120 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 120);
            public static final PerformancePoint HD_200 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 200);
            public static final PerformancePoint HD_24 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 24);
            public static final PerformancePoint HD_240 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 240);
            public static final PerformancePoint HD_25 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 25);
            public static final PerformancePoint HD_30 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 30);
            public static final PerformancePoint HD_50 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 50);
            public static final PerformancePoint HD_60 = new PerformancePoint(1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 60);
            public static final PerformancePoint SD_24 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 24);
            public static final PerformancePoint SD_25 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 576, 25);
            public static final PerformancePoint SD_30 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 30);
            public static final PerformancePoint SD_48 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 48);
            public static final PerformancePoint SD_50 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 576, 50);
            public static final PerformancePoint SD_60 = new PerformancePoint(MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 60);
            public static final PerformancePoint UHD_100 = new PerformancePoint(3840, 2160, 100);
            public static final PerformancePoint UHD_120 = new PerformancePoint(3840, 2160, 120);
            public static final PerformancePoint UHD_200 = new PerformancePoint(3840, 2160, 200);
            public static final PerformancePoint UHD_24 = new PerformancePoint(3840, 2160, 24);
            public static final PerformancePoint UHD_240 = new PerformancePoint(3840, 2160, 240);
            public static final PerformancePoint UHD_25 = new PerformancePoint(3840, 2160, 25);
            public static final PerformancePoint UHD_30 = new PerformancePoint(3840, 2160, 30);
            public static final PerformancePoint UHD_50 = new PerformancePoint(3840, 2160, 50);
            public static final PerformancePoint UHD_60 = new PerformancePoint(3840, 2160, 60);
            private Size mBlockSize;
            private int mHeight;
            private int mMaxFrameRate;
            private long mMaxMacroBlockRate;
            private int mWidth;

            public int getMaxMacroBlocks() {
                return saturateLongToInt(((long) this.mWidth) * ((long) this.mHeight));
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
                int origRate = (int) Utils.divUp(this.mMaxMacroBlockRate, (long) getMaxMacroBlocks());
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
                int unused = MediaCodecInfo.checkPowerOfTwo(blockSize.getWidth(), "block width");
                int unused2 = MediaCodecInfo.checkPowerOfTwo(blockSize.getHeight(), "block height");
                this.mBlockSize = new Size(Utils.divUp(blockSize.getWidth(), 16), Utils.divUp(blockSize.getHeight(), 16));
                this.mWidth = (int) (Utils.divUp(Math.max(1, (long) width), (long) Math.max(blockSize.getWidth(), 16)) * ((long) this.mBlockSize.getWidth()));
                this.mHeight = (int) (Utils.divUp(Math.max(1, (long) height), (long) Math.max(blockSize.getHeight(), 16)) * ((long) this.mBlockSize.getHeight()));
                this.mMaxFrameRate = Math.max(1, Math.max(frameRate, maxFrameRate));
                this.mMaxMacroBlockRate = (long) (Math.max(1, frameRate) * getMaxMacroBlocks());
            }

            public PerformancePoint(PerformancePoint pp, Size newBlockSize) {
                this(pp.mWidth * 16, pp.mHeight * 16, (int) Utils.divUp(pp.mMaxMacroBlockRate, (long) pp.getMaxMacroBlocks()), pp.mMaxFrameRate, new Size(Math.max(newBlockSize.getWidth(), pp.mBlockSize.getWidth() * 16), Math.max(newBlockSize.getHeight(), pp.mBlockSize.getHeight() * 16)));
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
                return covers(new PerformancePoint(format.getInteger("width", 0), format.getInteger("height", 0), Math.round((float) Math.ceil(format.getNumber(MediaFormat.KEY_FRAME_RATE, 0).doubleValue()))));
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
                if (!(o instanceof PerformancePoint)) {
                    return false;
                }
                PerformancePoint other = (PerformancePoint) o;
                Size commonSize = getCommonBlockSize(other);
                PerformancePoint aligned = new PerformancePoint(this, commonSize);
                PerformancePoint otherAligned = new PerformancePoint(other, commonSize);
                if (aligned.getMaxMacroBlocks() == otherAligned.getMaxMacroBlocks() && aligned.mMaxFrameRate == otherAligned.mMaxFrameRate && aligned.mMaxMacroBlockRate == otherAligned.mMaxMacroBlockRate) {
                    return true;
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
            return supports(Integer.valueOf(width), Integer.valueOf(height), (Number) null);
        }

        private boolean supports(Integer width, Integer height, Number rate) {
            boolean ok = true;
            boolean z = true;
            if (!(1 == 0 || width == null)) {
                ok = this.mWidthRange.contains(width) && width.intValue() % this.mWidthAlignment == 0;
            }
            if (ok && height != null) {
                ok = this.mHeightRange.contains(height) && height.intValue() % this.mHeightAlignment == 0;
            }
            if (ok && rate != null) {
                ok = this.mFrameRateRange.contains(Utils.intRangeFor(rate.doubleValue()));
            }
            if (!ok || height == null || width == null) {
                return ok;
            }
            boolean ok2 = Math.min(height.intValue(), width.intValue()) <= this.mSmallerDimensionUpperLimit;
            int widthInBlocks = Utils.divUp(width.intValue(), this.mBlockWidth);
            int heightInBlocks = Utils.divUp(height.intValue(), this.mBlockHeight);
            int blockCount = widthInBlocks * heightInBlocks;
            if (!ok2 || !this.mBlockCountRange.contains(Integer.valueOf(blockCount)) || !this.mBlockAspectRatioRange.contains(new Rational(widthInBlocks, heightInBlocks)) || !this.mAspectRatioRange.contains(new Rational(width.intValue(), height.intValue()))) {
                z = false;
            }
            boolean ok3 = z;
            if (!ok3 || rate == null) {
                return ok3;
            }
            return this.mBlocksPerSecondRange.contains(Utils.longRangeFor(((double) blockCount) * rate.doubleValue()));
        }

        public boolean supportsFormat(MediaFormat format) {
            Map<String, Object> map = format.getMap();
            if (supports((Integer) map.get("width"), (Integer) map.get("height"), (Number) map.get(MediaFormat.KEY_FRAME_RATE)) && CodecCapabilities.supportsBitrate(this.mBitrateRange, format)) {
                return true;
            }
            return false;
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
                    if (key.substring("performance-point-".length()).equals("none") && ret.size() == 0) {
                        return Collections.unmodifiableList(ret);
                    }
                    String[] temp = key.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (temp.length == 4) {
                        Size size = Utils.parseSize(temp[2], (Size) null);
                        if (size == null) {
                            Map<String, Object> map2 = map;
                        } else if (size.getWidth() * size.getHeight() > 0 && (range = Utils.parseLongRange(map.get(key), (Range<Long>) null)) != null && range.getLower().longValue() >= 0 && range.getUpper().longValue() >= 0) {
                            String prefix2 = prefix;
                            Set<String> keys2 = keys;
                            PerformancePoint given = new PerformancePoint(size.getWidth(), size.getHeight(), range.getLower().intValue(), range.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                            Iterator<String> it2 = it;
                            String str = key;
                            PerformancePoint performancePoint = new PerformancePoint(size.getHeight(), size.getWidth(), range.getLower().intValue(), range.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                            ret.add(given);
                            if (!given.covers(performancePoint)) {
                                ret.add(performancePoint);
                            }
                            prefix = prefix2;
                            keys = keys2;
                            it = it2;
                        }
                    }
                }
            }
            Map<String, Object> map3 = map;
            String str2 = prefix;
            Set<String> set = keys;
            if (ret.size() == 0) {
                return null;
            }
            ret.sort($$Lambda$MediaCodecInfo$VideoCapabilities$DpgwEngVFZT9EtP3qcxpiA2G0M.INSTANCE);
            return Collections.unmodifiableList(ret);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0045, code lost:
            if (r6.getMaxFrameRate() < r7.getMaxFrameRate()) goto L_0x0016;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0014, code lost:
            if (r6.getMaxMacroBlocks() < r7.getMaxMacroBlocks()) goto L_0x0016;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x002f, code lost:
            if (r6.getMaxMacroBlockRate() < r7.getMaxMacroBlockRate()) goto L_0x0016;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static /* synthetic */ int lambda$getPerformancePoints$0(android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint r6, android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint r7) {
            /*
                int r0 = r6.getMaxMacroBlocks()
                int r1 = r7.getMaxMacroBlocks()
                r2 = 1
                r3 = -1
                if (r0 == r1) goto L_0x0019
                int r0 = r6.getMaxMacroBlocks()
                int r1 = r7.getMaxMacroBlocks()
                if (r0 >= r1) goto L_0x0018
            L_0x0016:
                r2 = r3
                goto L_0x004a
            L_0x0018:
                goto L_0x004a
            L_0x0019:
                long r0 = r6.getMaxMacroBlockRate()
                long r4 = r7.getMaxMacroBlockRate()
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L_0x0033
                long r0 = r6.getMaxMacroBlockRate()
                long r4 = r7.getMaxMacroBlockRate()
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 >= 0) goto L_0x0032
                goto L_0x0016
            L_0x0032:
                goto L_0x004a
            L_0x0033:
                int r0 = r6.getMaxFrameRate()
                int r1 = r7.getMaxFrameRate()
                if (r0 == r1) goto L_0x0049
                int r0 = r6.getMaxFrameRate()
                int r1 = r7.getMaxFrameRate()
                if (r0 >= r1) goto L_0x0048
                goto L_0x0016
            L_0x0048:
                goto L_0x004a
            L_0x0049:
                r2 = 0
            L_0x004a:
                int r0 = -r2
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaCodecInfo.VideoCapabilities.lambda$getPerformancePoints$0(android.media.MediaCodecInfo$VideoCapabilities$PerformancePoint, android.media.MediaCodecInfo$VideoCapabilities$PerformancePoint):int");
        }

        private Map<Size, Range<Long>> getMeasuredFrameRates(Map<String, Object> map) {
            Size size;
            Range<Long> range;
            Map<Size, Range<Long>> ret = new HashMap<>();
            for (String key : map.keySet()) {
                if (key.startsWith("measured-frame-rate-")) {
                    String substring = key.substring("measured-frame-rate-".length());
                    String[] temp = key.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (temp.length == 5 && (size = Utils.parseSize(temp[3], (Size) null)) != null && size.getWidth() * size.getHeight() > 0 && (range = Utils.parseLongRange(map.get(key), (Range<Long>) null)) != null && range.getLower().longValue() >= 0 && range.getUpper().longValue() >= 0) {
                        ret.put(size, range);
                    }
                }
            }
            return ret;
        }

        private static Pair<Range<Integer>, Range<Integer>> parseWidthHeightRanges(Object o) {
            Pair<Size, Size> range = Utils.parseSizeRange(o);
            if (range == null) {
                return null;
            }
            try {
                return Pair.create(Range.create(Integer.valueOf(((Size) range.first).getWidth()), Integer.valueOf(((Size) range.second).getWidth())), Range.create(Integer.valueOf(((Size) range.first).getHeight()), Integer.valueOf(((Size) range.second).getHeight())));
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "could not parse size range '" + o + "'");
                return null;
            }
        }

        public static int equivalentVP9Level(MediaFormat info) {
            Map<String, Object> map = info.getMap();
            Size blockSize = Utils.parseSize(map.get("block-size"), new Size(8, 8));
            int BS = blockSize.getWidth() * blockSize.getHeight();
            Range<Integer> counts = Utils.parseIntRange(map.get("block-count-range"), (Range<Integer>) null);
            int BR = 0;
            int FS = counts == null ? 0 : counts.getUpper().intValue() * BS;
            Range<Long> blockRates = Utils.parseLongRange(map.get("blocks-per-second-range"), (Range<Long>) null);
            long SR = blockRates == null ? 0 : ((long) BS) * blockRates.getUpper().longValue();
            Pair<Range<Integer>, Range<Integer>> dimensionRanges = parseWidthHeightRanges(map.get("size-range"));
            int D = dimensionRanges == null ? 0 : Math.max(((Integer) ((Range) dimensionRanges.first).getUpper()).intValue(), ((Integer) ((Range) dimensionRanges.second).getUpper()).intValue());
            Range<Integer> bitRates = Utils.parseIntRange(map.get("bitrate-range"), (Range<Integer>) null);
            if (bitRates != null) {
                BR = Utils.divUp(bitRates.getUpper().intValue(), 1000);
            }
            if (SR <= 829440 && FS <= 36864 && BR <= 200 && D <= 512) {
                return 1;
            }
            if (SR <= 2764800 && FS <= 73728 && BR <= 800 && D <= 768) {
                return 2;
            }
            if (SR <= 4608000 && FS <= 122880 && BR <= 1800 && D <= 960) {
                return 4;
            }
            if (SR <= 9216000 && FS <= 245760 && BR <= 3600 && D <= 1344) {
                return 8;
            }
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
            if (SR <= 588251136 && FS <= 8912896 && BR <= 120000 && D <= 8384) {
                return 512;
            }
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

        /* JADX WARNING: Removed duplicated region for block: B:25:0x01cd  */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x0272  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x027e  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x028a  */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x0296  */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x02b5  */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x02d5  */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x02f3  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x02ff  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x030b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void parseFromInfo(android.media.MediaFormat r31) {
            /*
                r30 = this;
                r11 = r30
                java.util.Map r12 = r31.getMap()
                android.util.Size r0 = new android.util.Size
                int r1 = r11.mBlockWidth
                int r2 = r11.mBlockHeight
                r0.<init>(r1, r2)
                android.util.Size r1 = new android.util.Size
                int r2 = r11.mWidthAlignment
                int r3 = r11.mHeightAlignment
                r1.<init>(r2, r3)
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
                java.lang.String r10 = "block-size"
                java.lang.Object r10 = r12.get(r10)
                android.util.Size r13 = android.media.Utils.parseSize(r10, r0)
                java.lang.String r0 = "alignment"
                java.lang.Object r0 = r12.get(r0)
                android.util.Size r14 = android.media.Utils.parseSize(r0, r1)
                java.lang.String r0 = "block-count-range"
                java.lang.Object r0 = r12.get(r0)
                r1 = 0
                android.util.Range r15 = android.media.Utils.parseIntRange(r0, r1)
                java.lang.String r0 = "blocks-per-second-range"
                java.lang.Object r0 = r12.get(r0)
                android.util.Range r10 = android.media.Utils.parseLongRange(r0, r1)
                java.util.Map r0 = r11.getMeasuredFrameRates(r12)
                r11.mMeasuredFrameRates = r0
                java.util.List r0 = r11.getPerformancePoints(r12)
                r11.mPerformancePoints = r0
                java.lang.String r0 = "size-range"
                java.lang.Object r0 = r12.get(r0)
                android.util.Pair r7 = parseWidthHeightRanges(r0)
                if (r7 == 0) goto L_0x006c
                F r0 = r7.first
                r3 = r0
                android.util.Range r3 = (android.util.Range) r3
                S r0 = r7.second
                r4 = r0
                android.util.Range r4 = (android.util.Range) r4
            L_0x006c:
                java.lang.String r0 = "feature-can-swap-width-height"
                boolean r0 = r12.containsKey(r0)
                if (r0 == 0) goto L_0x00c9
                if (r3 == 0) goto L_0x0098
                java.lang.Comparable r0 = r3.getUpper()
                java.lang.Integer r0 = (java.lang.Integer) r0
                int r0 = r0.intValue()
                java.lang.Comparable r2 = r4.getUpper()
                java.lang.Integer r2 = (java.lang.Integer) r2
                int r2 = r2.intValue()
                int r0 = java.lang.Math.min(r0, r2)
                r11.mSmallerDimensionUpperLimit = r0
                android.util.Range r0 = r3.extend(r4)
                r4 = r0
                r3 = r0
                goto L_0x00c9
            L_0x0098:
                java.lang.String r0 = "VideoCapabilities"
                java.lang.String r2 = "feature can-swap-width-height is best used with size-range"
                android.util.Log.w((java.lang.String) r0, (java.lang.String) r2)
                android.util.Range<java.lang.Integer> r0 = r11.mWidthRange
                java.lang.Comparable r0 = r0.getUpper()
                java.lang.Integer r0 = (java.lang.Integer) r0
                int r0 = r0.intValue()
                android.util.Range<java.lang.Integer> r2 = r11.mHeightRange
                java.lang.Comparable r2 = r2.getUpper()
                java.lang.Integer r2 = (java.lang.Integer) r2
                int r2 = r2.intValue()
                int r0 = java.lang.Math.min(r0, r2)
                r11.mSmallerDimensionUpperLimit = r0
                android.util.Range<java.lang.Integer> r0 = r11.mWidthRange
                android.util.Range<java.lang.Integer> r2 = r11.mHeightRange
                android.util.Range r0 = r0.extend(r2)
                r11.mHeightRange = r0
                r11.mWidthRange = r0
            L_0x00c9:
                r29 = r4
                r4 = r3
                r3 = r29
                java.lang.String r0 = "block-aspect-ratio-range"
                java.lang.Object r0 = r12.get(r0)
                android.util.Range r8 = android.media.Utils.parseRationalRange(r0, r1)
                java.lang.String r0 = "pixel-aspect-ratio-range"
                java.lang.Object r0 = r12.get(r0)
                android.util.Range r9 = android.media.Utils.parseRationalRange(r0, r1)
                java.lang.String r0 = "frame-rate-range"
                java.lang.Object r0 = r12.get(r0)
                android.util.Range r2 = android.media.Utils.parseIntRange(r0, r1)
                if (r2 == 0) goto L_0x011f
                android.util.Range r0 = android.media.MediaCodecInfo.FRAME_RATE_RANGE     // Catch:{ IllegalArgumentException -> 0x00f9 }
                android.util.Range r0 = r2.intersect(r0)     // Catch:{ IllegalArgumentException -> 0x00f9 }
                r2 = r0
                goto L_0x011f
            L_0x00f9:
                r0 = move-exception
                java.lang.String r5 = "VideoCapabilities"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                r17 = r0
                java.lang.String r0 = "frame rate range ("
                r1.append(r0)
                r1.append(r2)
                java.lang.String r0 = ") is out of limits: "
                r1.append(r0)
                android.util.Range r0 = android.media.MediaCodecInfo.FRAME_RATE_RANGE
                r1.append(r0)
                java.lang.String r0 = r1.toString()
                android.util.Log.w((java.lang.String) r5, (java.lang.String) r0)
                r2 = 0
            L_0x011f:
                r5 = r2
                java.lang.String r0 = "bitrate-range"
                java.lang.Object r0 = r12.get(r0)
                r1 = 0
                android.util.Range r1 = android.media.Utils.parseIntRange(r0, r1)
                if (r1 == 0) goto L_0x015d
                android.util.Range r0 = android.media.MediaCodecInfo.BITRATE_RANGE     // Catch:{ IllegalArgumentException -> 0x0137 }
                android.util.Range r0 = r1.intersect(r0)     // Catch:{ IllegalArgumentException -> 0x0137 }
                r1 = r0
                goto L_0x015e
            L_0x0137:
                r0 = move-exception
                java.lang.String r2 = "VideoCapabilities"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r18 = r0
                java.lang.String r0 = "bitrate range ("
                r6.append(r0)
                r6.append(r1)
                java.lang.String r0 = ") is out of limits: "
                r6.append(r0)
                android.util.Range r0 = android.media.MediaCodecInfo.BITRATE_RANGE
                r6.append(r0)
                java.lang.String r0 = r6.toString()
                android.util.Log.w((java.lang.String) r2, (java.lang.String) r0)
                r1 = 0
            L_0x015d:
                r0 = r1
            L_0x015e:
                int r1 = r13.getWidth()
                java.lang.String r2 = "block-size width must be power of two"
                int unused = android.media.MediaCodecInfo.checkPowerOfTwo(r1, r2)
                int r1 = r13.getHeight()
                java.lang.String r2 = "block-size height must be power of two"
                int unused = android.media.MediaCodecInfo.checkPowerOfTwo(r1, r2)
                int r1 = r14.getWidth()
                java.lang.String r2 = "alignment width must be power of two"
                int unused = android.media.MediaCodecInfo.checkPowerOfTwo(r1, r2)
                int r1 = r14.getHeight()
                java.lang.String r2 = "alignment height must be power of two"
                int unused = android.media.MediaCodecInfo.checkPowerOfTwo(r1, r2)
                r2 = 2147483647(0x7fffffff, float:NaN)
                r6 = 2147483647(0x7fffffff, float:NaN)
                r16 = 2147483647(0x7fffffff, float:NaN)
                r17 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r19 = r13.getWidth()
                int r20 = r13.getHeight()
                int r21 = r14.getWidth()
                int r22 = r14.getHeight()
                r1 = r30
                r23 = r3
                r3 = r6
                r6 = r4
                r4 = r16
                r25 = r5
                r24 = r6
                r5 = r17
                r16 = r7
                r7 = r19
                r26 = r8
                r8 = r20
                r27 = r9
                r9 = r21
                r28 = r12
                r12 = r10
                r10 = r22
                r1.applyMacroBlockLimits(r2, r3, r4, r5, r7, r8, r9, r10)
                android.media.MediaCodecInfo$CodecCapabilities r1 = r11.mParent
                int r1 = r1.mError
                r1 = r1 & 2
                if (r1 != 0) goto L_0x0272
                boolean r1 = r11.mAllowMbOverride
                if (r1 == 0) goto L_0x01dd
                r4 = r23
                r3 = r24
                r5 = r25
                r2 = r26
                r1 = r27
                goto L_0x027c
            L_0x01dd:
                r3 = r24
                if (r3 == 0) goto L_0x01e9
                android.util.Range<java.lang.Integer> r1 = r11.mWidthRange
                android.util.Range r1 = r1.intersect(r3)
                r11.mWidthRange = r1
            L_0x01e9:
                r4 = r23
                if (r4 == 0) goto L_0x01f5
                android.util.Range<java.lang.Integer> r1 = r11.mHeightRange
                android.util.Range r1 = r1.intersect(r4)
                r11.mHeightRange = r1
            L_0x01f5:
                if (r15 == 0) goto L_0x0212
                android.util.Range<java.lang.Integer> r1 = r11.mBlockCountRange
                int r2 = r11.mBlockWidth
                int r5 = r11.mBlockHeight
                int r2 = r2 * r5
                int r5 = r13.getWidth()
                int r2 = r2 / r5
                int r5 = r13.getHeight()
                int r2 = r2 / r5
                android.util.Range r2 = android.media.Utils.factorRange((android.util.Range<java.lang.Integer>) r15, (int) r2)
                android.util.Range r1 = r1.intersect(r2)
                r11.mBlockCountRange = r1
            L_0x0212:
                if (r12 == 0) goto L_0x0230
                android.util.Range<java.lang.Long> r1 = r11.mBlocksPerSecondRange
                int r2 = r11.mBlockWidth
                int r5 = r11.mBlockHeight
                int r2 = r2 * r5
                int r5 = r13.getWidth()
                int r2 = r2 / r5
                int r5 = r13.getHeight()
                int r2 = r2 / r5
                long r5 = (long) r2
                android.util.Range r2 = android.media.Utils.factorRange((android.util.Range<java.lang.Long>) r12, (long) r5)
                android.util.Range r1 = r1.intersect(r2)
                r11.mBlocksPerSecondRange = r1
            L_0x0230:
                r1 = r27
                if (r1 == 0) goto L_0x024e
                android.util.Range<android.util.Rational> r2 = r11.mBlockAspectRatioRange
                int r5 = r11.mBlockHeight
                int r6 = r13.getHeight()
                int r5 = r5 / r6
                int r6 = r11.mBlockWidth
                int r7 = r13.getWidth()
                int r6 = r6 / r7
                android.util.Range r5 = android.media.Utils.scaleRange(r1, r5, r6)
                android.util.Range r2 = r2.intersect(r5)
                r11.mBlockAspectRatioRange = r2
            L_0x024e:
                r2 = r26
                if (r2 == 0) goto L_0x025a
                android.util.Range<android.util.Rational> r5 = r11.mAspectRatioRange
                android.util.Range r5 = r5.intersect(r2)
                r11.mAspectRatioRange = r5
            L_0x025a:
                r5 = r25
                if (r5 == 0) goto L_0x0266
                android.util.Range<java.lang.Integer> r6 = r11.mFrameRateRange
                android.util.Range r6 = r6.intersect(r5)
                r11.mFrameRateRange = r6
            L_0x0266:
                if (r0 == 0) goto L_0x0326
                android.util.Range<java.lang.Integer> r6 = r11.mBitrateRange
                android.util.Range r6 = r6.intersect(r0)
                r11.mBitrateRange = r6
                goto L_0x0326
            L_0x0272:
                r4 = r23
                r3 = r24
                r5 = r25
                r2 = r26
                r1 = r27
            L_0x027c:
                if (r3 == 0) goto L_0x0288
                android.util.Range r6 = android.media.MediaCodecInfo.SIZE_RANGE
                android.util.Range r6 = r6.intersect(r3)
                r11.mWidthRange = r6
            L_0x0288:
                if (r4 == 0) goto L_0x0294
                android.util.Range r6 = android.media.MediaCodecInfo.SIZE_RANGE
                android.util.Range r6 = r6.intersect(r4)
                r11.mHeightRange = r6
            L_0x0294:
                if (r15 == 0) goto L_0x02b3
                android.util.Range r6 = android.media.MediaCodecInfo.POSITIVE_INTEGERS
                int r7 = r11.mBlockWidth
                int r8 = r11.mBlockHeight
                int r7 = r7 * r8
                int r8 = r13.getWidth()
                int r7 = r7 / r8
                int r8 = r13.getHeight()
                int r7 = r7 / r8
                android.util.Range r7 = android.media.Utils.factorRange((android.util.Range<java.lang.Integer>) r15, (int) r7)
                android.util.Range r6 = r6.intersect(r7)
                r11.mBlockCountRange = r6
            L_0x02b3:
                if (r12 == 0) goto L_0x02d3
                android.util.Range r6 = android.media.MediaCodecInfo.POSITIVE_LONGS
                int r7 = r11.mBlockWidth
                int r8 = r11.mBlockHeight
                int r7 = r7 * r8
                int r8 = r13.getWidth()
                int r7 = r7 / r8
                int r8 = r13.getHeight()
                int r7 = r7 / r8
                long r7 = (long) r7
                android.util.Range r7 = android.media.Utils.factorRange((android.util.Range<java.lang.Long>) r12, (long) r7)
                android.util.Range r6 = r6.intersect(r7)
                r11.mBlocksPerSecondRange = r6
            L_0x02d3:
                if (r1 == 0) goto L_0x02f1
                android.util.Range r6 = android.media.MediaCodecInfo.POSITIVE_RATIONALS
                int r7 = r11.mBlockHeight
                int r8 = r13.getHeight()
                int r7 = r7 / r8
                int r8 = r11.mBlockWidth
                int r9 = r13.getWidth()
                int r8 = r8 / r9
                android.util.Range r7 = android.media.Utils.scaleRange(r1, r7, r8)
                android.util.Range r6 = r6.intersect(r7)
                r11.mBlockAspectRatioRange = r6
            L_0x02f1:
                if (r2 == 0) goto L_0x02fd
                android.util.Range r6 = android.media.MediaCodecInfo.POSITIVE_RATIONALS
                android.util.Range r6 = r6.intersect(r2)
                r11.mAspectRatioRange = r6
            L_0x02fd:
                if (r5 == 0) goto L_0x0309
                android.util.Range r6 = android.media.MediaCodecInfo.FRAME_RATE_RANGE
                android.util.Range r6 = r6.intersect(r5)
                r11.mFrameRateRange = r6
            L_0x0309:
                if (r0 == 0) goto L_0x0326
                android.media.MediaCodecInfo$CodecCapabilities r6 = r11.mParent
                int r6 = r6.mError
                r6 = r6 & 2
                if (r6 == 0) goto L_0x031e
                android.util.Range r6 = android.media.MediaCodecInfo.BITRATE_RANGE
                android.util.Range r6 = r6.intersect(r0)
                r11.mBitrateRange = r6
                goto L_0x0326
            L_0x031e:
                android.util.Range<java.lang.Integer> r6 = r11.mBitrateRange
                android.util.Range r6 = r6.intersect(r0)
                r11.mBitrateRange = r6
            L_0x0326:
                r30.updateLimits()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaCodecInfo.VideoCapabilities.parseFromInfo(android.media.MediaFormat):void");
        }

        private void applyBlockLimits(int blockWidth, int blockHeight, Range<Integer> counts, Range<Long> rates, Range<Rational> ratios) {
            int unused = MediaCodecInfo.checkPowerOfTwo(blockWidth, "blockWidth must be a power of two");
            int unused2 = MediaCodecInfo.checkPowerOfTwo(blockHeight, "blockHeight must be a power of two");
            int newBlockWidth = Math.max(blockWidth, this.mBlockWidth);
            int newBlockHeight = Math.max(blockHeight, this.mBlockHeight);
            int factor = ((newBlockWidth * newBlockHeight) / this.mBlockWidth) / this.mBlockHeight;
            if (factor != 1) {
                this.mBlockCountRange = Utils.factorRange(this.mBlockCountRange, factor);
                this.mBlocksPerSecondRange = Utils.factorRange(this.mBlocksPerSecondRange, (long) factor);
                this.mBlockAspectRatioRange = Utils.scaleRange(this.mBlockAspectRatioRange, newBlockHeight / this.mBlockHeight, newBlockWidth / this.mBlockWidth);
                this.mHorizontalBlockRange = Utils.factorRange(this.mHorizontalBlockRange, newBlockWidth / this.mBlockWidth);
                this.mVerticalBlockRange = Utils.factorRange(this.mVerticalBlockRange, newBlockHeight / this.mBlockHeight);
            }
            int factor2 = ((newBlockWidth * newBlockHeight) / blockWidth) / blockHeight;
            if (factor2 != 1) {
                counts = Utils.factorRange(counts, factor2);
                rates = Utils.factorRange(rates, (long) factor2);
                ratios = Utils.scaleRange(ratios, newBlockHeight / blockHeight, newBlockWidth / blockWidth);
            }
            this.mBlockCountRange = this.mBlockCountRange.intersect(counts);
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(rates);
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(ratios);
            this.mBlockWidth = newBlockWidth;
            this.mBlockHeight = newBlockHeight;
        }

        private void applyAlignment(int widthAlignment, int heightAlignment) {
            int unused = MediaCodecInfo.checkPowerOfTwo(widthAlignment, "widthAlignment must be a power of two");
            int unused2 = MediaCodecInfo.checkPowerOfTwo(heightAlignment, "heightAlignment must be a power of two");
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
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(Long.valueOf(((long) this.mBlockCountRange.getLower().intValue()) * ((long) this.mFrameRateRange.getLower().intValue())), Long.valueOf(((long) this.mBlockCountRange.getUpper().intValue()) * ((long) this.mFrameRateRange.getUpper().intValue())));
            this.mFrameRateRange = this.mFrameRateRange.intersect(Integer.valueOf((int) (this.mBlocksPerSecondRange.getLower().longValue() / ((long) this.mBlockCountRange.getUpper().intValue()))), Integer.valueOf((int) (((double) this.mBlocksPerSecondRange.getUpper().longValue()) / ((double) this.mBlockCountRange.getLower().intValue()))));
        }

        private void applyMacroBlockLimits(int maxHorizontalBlocks, int maxVerticalBlocks, int maxBlocks, long maxBlocksPerSecond, int blockWidth, int blockHeight, int widthAlignment, int heightAlignment) {
            applyMacroBlockLimits(1, 1, maxHorizontalBlocks, maxVerticalBlocks, maxBlocks, maxBlocksPerSecond, blockWidth, blockHeight, widthAlignment, heightAlignment);
        }

        private void applyMacroBlockLimits(int minHorizontalBlocks, int minVerticalBlocks, int maxHorizontalBlocks, int maxVerticalBlocks, int maxBlocks, long maxBlocksPerSecond, int blockWidth, int blockHeight, int widthAlignment, int heightAlignment) {
            applyAlignment(widthAlignment, heightAlignment);
            applyBlockLimits(blockWidth, blockHeight, Range.create(1, Integer.valueOf(maxBlocks)), Range.create(1L, Long.valueOf(maxBlocksPerSecond)), Range.create(new Rational(1, maxVerticalBlocks), new Rational(maxHorizontalBlocks, 1)));
            int i = minHorizontalBlocks;
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Integer.valueOf(Utils.divUp(minHorizontalBlocks, this.mBlockWidth / blockWidth)), Integer.valueOf(maxHorizontalBlocks / (this.mBlockWidth / blockWidth)));
            int i2 = minVerticalBlocks;
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Integer.valueOf(Utils.divUp(minVerticalBlocks, this.mBlockHeight / blockHeight)), Integer.valueOf(maxVerticalBlocks / (this.mBlockHeight / blockHeight)));
        }

        /* JADX WARNING: Code restructure failed: missing block: B:128:0x05b5, code lost:
            r37 = r0;
            r38 = r9;
            r39 = r14;
            r6 = r18;
            r14 = r19;
            r0 = r20;
            r9 = r21;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x0615, code lost:
            if (r23 == false) goto L_0x0619;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x0617, code lost:
            r17 = r17 & -5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x0619, code lost:
            r40 = r12;
            r4 = java.lang.Math.max((long) r1, r4);
            r8 = java.lang.Math.max(r2, r8);
            r3 = java.lang.Math.max(r6 * 1000, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x062a, code lost:
            if (r22 == false) goto L_0x063a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:0x062c, code lost:
            r11 = java.lang.Math.max(r0, r11);
            r12 = java.lang.Math.max(r9, r15);
            r7 = java.lang.Math.max(r14, r7);
            r15 = r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x063a, code lost:
            r12 = (int) java.lang.Math.sqrt((double) (r2 * 2));
            r11 = java.lang.Math.max(r12, r11);
            r13 = java.lang.Math.max(r12, r15);
            r7 = java.lang.Math.max(java.lang.Math.max(r14, 60), r7);
            r15 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x0656, code lost:
            r10 = r10 + 1;
            r9 = r38;
            r14 = r39;
            r12 = r40;
            r13 = r58;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x0276, code lost:
            r30 = r0;
            r31 = r8;
            r32 = r13;
            r11 = 0;
            r13 = 0;
            r0 = 0;
            r8 = 0;
            r1 = r28;
            r2 = r29;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x0376, code lost:
            r30 = r0;
            r31 = r8;
            r32 = r13;
            r11 = r16;
            r13 = r18;
            r0 = r19;
            r8 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0384, code lost:
            if (r21 == false) goto L_0x038a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x0386, code lost:
            r17 = r17 & -5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x038a, code lost:
            r4 = java.lang.Math.max((long) r1, r4);
            r7 = java.lang.Math.max(r2, r7);
            r3 = java.lang.Math.max(r11 * 1000, r3);
            r12 = java.lang.Math.max(r0, r12);
            r9 = java.lang.Math.max(r8, r9);
            r6 = java.lang.Math.max(r13, r6);
            r10 = r10 + 1;
            r8 = r31;
            r13 = r32;
            r14 = r14;
            r15 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x045e, code lost:
            r37 = r0;
            r38 = r9;
            r39 = r14;
            r6 = 0;
            r14 = 0;
            r0 = 0;
            r9 = 0;
            r1 = r35;
            r2 = r36;
         */
        /* JADX WARNING: Removed duplicated region for block: B:332:0x01d1 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x01cd  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void applyLevelLimits() {
            /*
                r58 = this;
                r12 = r58
                r0 = 0
                r2 = 0
                r3 = 0
                r13 = 0
                r4 = 4
                android.media.MediaCodecInfo$CodecCapabilities r5 = r12.mParent
                android.media.MediaCodecInfo$CodecProfileLevel[] r14 = r5.profileLevels
                android.media.MediaCodecInfo$CodecCapabilities r5 = r12.mParent
                java.lang.String r15 = r5.getMimeType()
                java.lang.String r5 = "video/avc"
                boolean r5 = r15.equalsIgnoreCase(r5)
                r9 = 4
                if (r5 == 0) goto L_0x021c
                r2 = 99
                r0 = 1485(0x5cd, double:7.337E-321)
                r3 = 64000(0xfa00, float:8.9683E-41)
                r5 = 396(0x18c, float:5.55E-43)
                int r13 = r14.length
                r17 = r4
                r10 = 0
                r4 = r2
                r56 = r5
                r5 = r3
                r2 = r0
                r1 = r56
            L_0x0030:
                if (r10 >= r13) goto L_0x01ed
                r0 = r14[r10]
                r18 = 0
                r19 = 0
                r20 = 0
                r21 = 0
                r22 = 1
                int r8 = r0.level
                switch(r8) {
                    case 1: goto L_0x0143;
                    case 2: goto L_0x0139;
                    case 4: goto L_0x012f;
                    case 8: goto L_0x0125;
                    case 16: goto L_0x011b;
                    case 32: goto L_0x0111;
                    case 64: goto L_0x0107;
                    case 128: goto L_0x00fd;
                    case 256: goto L_0x00f2;
                    case 512: goto L_0x00e7;
                    case 1024: goto L_0x00dc;
                    case 2048: goto L_0x00d1;
                    case 4096: goto L_0x00c5;
                    case 8192: goto L_0x00b9;
                    case 16384: goto L_0x00ae;
                    case 32768: goto L_0x00a2;
                    case 65536: goto L_0x0095;
                    case 131072: goto L_0x0088;
                    case 262144: goto L_0x007b;
                    case 524288: goto L_0x006f;
                    default: goto L_0x0043;
                }
            L_0x0043:
                java.lang.String r8 = "VideoCapabilities"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "Unrecognized level "
                r6.append(r7)
                int r7 = r0.level
                r6.append(r7)
                java.lang.String r7 = " for "
                r6.append(r7)
                r6.append(r15)
                java.lang.String r6 = r6.toString()
                android.util.Log.w((java.lang.String) r8, (java.lang.String) r6)
                r17 = r17 | 1
            L_0x0065:
                r7 = r18
                r8 = r19
                r6 = r20
                r25 = r21
                goto L_0x014d
            L_0x006f:
                r18 = 16711680(0xff0000, float:2.3418052E-38)
                r19 = 139264(0x22000, float:1.9515E-40)
                r20 = 800000(0xc3500, float:1.121039E-39)
                r21 = 696320(0xaa000, float:9.75752E-40)
                goto L_0x0065
            L_0x007b:
                r18 = 8355840(0x7f8000, float:1.1709026E-38)
                r19 = 139264(0x22000, float:1.9515E-40)
                r20 = 480000(0x75300, float:6.72623E-40)
                r21 = 696320(0xaa000, float:9.75752E-40)
                goto L_0x0065
            L_0x0088:
                r18 = 4177920(0x3fc000, float:5.854513E-39)
                r19 = 139264(0x22000, float:1.9515E-40)
                r20 = 240000(0x3a980, float:3.36312E-40)
                r21 = 696320(0xaa000, float:9.75752E-40)
                goto L_0x0065
            L_0x0095:
                r18 = 2073600(0x1fa400, float:2.905732E-39)
                r19 = 36864(0x9000, float:5.1657E-41)
                r20 = 240000(0x3a980, float:3.36312E-40)
                r21 = 184320(0x2d000, float:2.58287E-40)
                goto L_0x0065
            L_0x00a2:
                r18 = 983040(0xf0000, float:1.377532E-39)
                r19 = 36864(0x9000, float:5.1657E-41)
                r20 = 240000(0x3a980, float:3.36312E-40)
                r21 = 184320(0x2d000, float:2.58287E-40)
                goto L_0x0065
            L_0x00ae:
                r18 = 589824(0x90000, float:8.2652E-40)
                r19 = 22080(0x5640, float:3.094E-41)
                r20 = 135000(0x20f58, float:1.89175E-40)
                r21 = 110400(0x1af40, float:1.54703E-40)
                goto L_0x0065
            L_0x00b9:
                r18 = 522240(0x7f800, float:7.31814E-40)
                r19 = 8704(0x2200, float:1.2197E-41)
                r20 = 50000(0xc350, float:7.0065E-41)
                r21 = 34816(0x8800, float:4.8788E-41)
                goto L_0x0065
            L_0x00c5:
                r18 = 245760(0x3c000, float:3.44383E-40)
                r19 = 8192(0x2000, float:1.14794E-41)
                r20 = 50000(0xc350, float:7.0065E-41)
                r21 = 32768(0x8000, float:4.5918E-41)
                goto L_0x0065
            L_0x00d1:
                r18 = 245760(0x3c000, float:3.44383E-40)
                r19 = 8192(0x2000, float:1.14794E-41)
                r20 = 20000(0x4e20, float:2.8026E-41)
                r21 = 32768(0x8000, float:4.5918E-41)
                goto L_0x0065
            L_0x00dc:
                r18 = 216000(0x34bc0, float:3.0268E-40)
                r19 = 5120(0x1400, float:7.175E-42)
                r20 = 20000(0x4e20, float:2.8026E-41)
                r21 = 20480(0x5000, float:2.8699E-41)
                goto L_0x0065
            L_0x00e7:
                r18 = 108000(0x1a5e0, float:1.5134E-40)
                r19 = 3600(0xe10, float:5.045E-42)
                r20 = 14000(0x36b0, float:1.9618E-41)
                r21 = 18000(0x4650, float:2.5223E-41)
                goto L_0x0065
            L_0x00f2:
                r18 = 40500(0x9e34, float:5.6753E-41)
                r19 = 1620(0x654, float:2.27E-42)
                r20 = 10000(0x2710, float:1.4013E-41)
                r21 = 8100(0x1fa4, float:1.135E-41)
                goto L_0x0065
            L_0x00fd:
                r18 = 20250(0x4f1a, float:2.8376E-41)
                r19 = 1620(0x654, float:2.27E-42)
                r20 = 4000(0xfa0, float:5.605E-42)
                r21 = 8100(0x1fa4, float:1.135E-41)
                goto L_0x0065
            L_0x0107:
                r18 = 19800(0x4d58, float:2.7746E-41)
                r19 = 792(0x318, float:1.11E-42)
                r20 = 4000(0xfa0, float:5.605E-42)
                r21 = 4752(0x1290, float:6.659E-42)
                goto L_0x0065
            L_0x0111:
                r18 = 11880(0x2e68, float:1.6647E-41)
                r19 = 396(0x18c, float:5.55E-43)
                r20 = 2000(0x7d0, float:2.803E-42)
                r21 = 2376(0x948, float:3.33E-42)
                goto L_0x0065
            L_0x011b:
                r18 = 11880(0x2e68, float:1.6647E-41)
                r19 = 396(0x18c, float:5.55E-43)
                r20 = 768(0x300, float:1.076E-42)
                r21 = 2376(0x948, float:3.33E-42)
                goto L_0x0065
            L_0x0125:
                r18 = 6000(0x1770, float:8.408E-42)
                r19 = 396(0x18c, float:5.55E-43)
                r20 = 384(0x180, float:5.38E-43)
                r21 = 2376(0x948, float:3.33E-42)
                goto L_0x0065
            L_0x012f:
                r18 = 3000(0xbb8, float:4.204E-42)
                r19 = 396(0x18c, float:5.55E-43)
                r20 = 192(0xc0, float:2.69E-43)
                r21 = 900(0x384, float:1.261E-42)
                goto L_0x0065
            L_0x0139:
                r18 = 1485(0x5cd, float:2.081E-42)
                r19 = 99
                r20 = 128(0x80, float:1.794E-43)
                r21 = 396(0x18c, float:5.55E-43)
                goto L_0x0065
            L_0x0143:
                r18 = 1485(0x5cd, float:2.081E-42)
                r19 = 99
                r20 = 64
                r21 = 396(0x18c, float:5.55E-43)
                goto L_0x0065
            L_0x014d:
                int r11 = r0.profile
                if (r11 == r9) goto L_0x0199
                r9 = 8
                if (r11 == r9) goto L_0x0196
                r9 = 16
                if (r11 == r9) goto L_0x019c
                r9 = 32
                if (r11 == r9) goto L_0x0199
                r9 = 64
                if (r11 == r9) goto L_0x0199
                r9 = 65536(0x10000, float:9.18355E-41)
                if (r11 == r9) goto L_0x0193
                r9 = 524288(0x80000, float:7.34684E-40)
                if (r11 == r9) goto L_0x0196
                switch(r11) {
                    case 1: goto L_0x0193;
                    case 2: goto L_0x0193;
                    default: goto L_0x016c;
                }
            L_0x016c:
                java.lang.String r9 = "VideoCapabilities"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                r26 = r13
                java.lang.String r13 = "Unrecognized profile "
                r11.append(r13)
                int r13 = r0.profile
                r11.append(r13)
                java.lang.String r13 = " for "
                r11.append(r13)
                r11.append(r15)
                java.lang.String r11 = r11.toString()
                android.util.Log.w((java.lang.String) r9, (java.lang.String) r11)
                r17 = r17 | 1
                int r6 = r6 * 1000
                goto L_0x01cb
            L_0x0193:
                r26 = r13
                goto L_0x01c9
            L_0x0196:
                r26 = r13
                goto L_0x01a1
            L_0x0199:
                r26 = r13
                goto L_0x01a4
            L_0x019c:
                r26 = r13
                int r6 = r6 * 3000
                goto L_0x01cb
            L_0x01a1:
                int r6 = r6 * 1250
                goto L_0x01cb
            L_0x01a4:
                java.lang.String r9 = "VideoCapabilities"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                java.lang.String r13 = "Unsupported profile "
                r11.append(r13)
                int r13 = r0.profile
                r11.append(r13)
                java.lang.String r13 = " for "
                r11.append(r13)
                r11.append(r15)
                java.lang.String r11 = r11.toString()
                android.util.Log.w((java.lang.String) r9, (java.lang.String) r11)
                r17 = r17 | 2
                r9 = 0
                r22 = r9
            L_0x01c9:
                int r6 = r6 * 1000
            L_0x01cb:
                if (r22 == 0) goto L_0x01d1
                r9 = r17 & -5
                r17 = r9
            L_0x01d1:
                long r11 = (long) r7
                long r2 = java.lang.Math.max(r11, r2)
                int r4 = java.lang.Math.max(r8, r4)
                int r5 = java.lang.Math.max(r6, r5)
                r9 = r25
                int r1 = java.lang.Math.max(r1, r9)
                int r10 = r10 + 1
                r13 = r26
                r9 = 4
                r12 = r58
                goto L_0x0030
            L_0x01ed:
                int r0 = r4 * 8
                double r6 = (double) r0
                double r6 = java.lang.Math.sqrt(r6)
                int r10 = (int) r6
                r6 = 16
                r7 = 16
                r8 = 1
                r9 = 1
                r0 = r58
                r11 = r1
                r1 = r10
                r12 = r2
                r2 = r10
                r3 = r4
                r16 = r4
                r18 = r5
                r4 = r12
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                r32 = r11
                r55 = r14
                r20 = 1
                r56 = r12
                r13 = r15
                r15 = r16
                r12 = r18
            L_0x0218:
                r18 = r56
                goto L_0x0e12
            L_0x021c:
                java.lang.String r5 = "video/mpeg2"
                boolean r5 = r15.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x03f6
                r5 = 11
                r6 = 9
                r7 = 15
                r2 = 99
                r0 = 1485(0x5cd, double:7.337E-321)
                r3 = 64000(0xfa00, float:8.9683E-41)
                int r8 = r14.length
                r17 = r4
                r12 = r5
                r9 = r6
                r6 = r7
                r10 = 0
                r4 = r0
                r7 = r2
            L_0x023b:
                if (r10 >= r8) goto L_0x03b5
                r0 = r14[r10]
                r1 = 0
                r2 = 0
                r16 = 0
                r18 = 0
                r19 = 0
                r20 = 0
                r21 = 1
                int r11 = r0.profile
                switch(r11) {
                    case 0: goto L_0x0332;
                    case 1: goto L_0x02b3;
                    case 2: goto L_0x028a;
                    case 3: goto L_0x028a;
                    case 4: goto L_0x028a;
                    case 5: goto L_0x028a;
                    default: goto L_0x0250;
                }
            L_0x0250:
                r28 = r1
                r29 = r2
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r11 = "Unrecognized profile "
                r2.append(r11)
                int r11 = r0.profile
                r2.append(r11)
                java.lang.String r11 = " for "
                r2.append(r11)
                r2.append(r15)
                java.lang.String r2 = r2.toString()
                android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
                r17 = r17 | 1
            L_0x0276:
                r30 = r0
                r31 = r8
                r32 = r13
                r11 = r16
                r13 = r18
                r0 = r19
                r8 = r20
                r1 = r28
                r2 = r29
                goto L_0x0384
            L_0x028a:
                java.lang.String r11 = "VideoCapabilities"
                r28 = r1
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                r29 = r2
                java.lang.String r2 = "Unsupported profile "
                r1.append(r2)
                int r2 = r0.profile
                r1.append(r2)
                java.lang.String r2 = " for "
                r1.append(r2)
                r1.append(r15)
                java.lang.String r1 = r1.toString()
                android.util.Log.i(r11, r1)
                r17 = r17 | 2
                r21 = 0
                goto L_0x0276
            L_0x02b3:
                r28 = r1
                r29 = r2
                int r1 = r0.level
                switch(r1) {
                    case 0: goto L_0x0325;
                    case 1: goto L_0x0317;
                    case 2: goto L_0x0308;
                    case 3: goto L_0x02f9;
                    case 4: goto L_0x02e9;
                    default: goto L_0x02bc;
                }
            L_0x02bc:
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r11 = "Unrecognized profile/level "
                r2.append(r11)
                int r11 = r0.profile
                r2.append(r11)
                java.lang.String r11 = "/"
                r2.append(r11)
                int r11 = r0.level
                r2.append(r11)
                java.lang.String r11 = " for "
                r2.append(r11)
                r2.append(r15)
                java.lang.String r2 = r2.toString()
                android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
                r17 = r17 | 1
                goto L_0x0276
            L_0x02e9:
                r18 = 60
                r19 = 120(0x78, float:1.68E-43)
                r20 = 68
                r1 = 489600(0x77880, float:6.86076E-40)
                r2 = 8160(0x1fe0, float:1.1435E-41)
                r16 = 80000(0x13880, float:1.12104E-40)
                goto L_0x0376
            L_0x02f9:
                r18 = 60
                r19 = 120(0x78, float:1.68E-43)
                r20 = 68
                r1 = 244800(0x3bc40, float:3.43038E-40)
                r2 = 8160(0x1fe0, float:1.1435E-41)
                r16 = 80000(0x13880, float:1.12104E-40)
                goto L_0x0376
            L_0x0308:
                r18 = 60
                r19 = 90
                r20 = 68
                r1 = 183600(0x2cd30, float:2.57278E-40)
                r2 = 6120(0x17e8, float:8.576E-42)
                r16 = 60000(0xea60, float:8.4078E-41)
                goto L_0x0376
            L_0x0317:
                r18 = 30
                r19 = 45
                r20 = 36
                r1 = 40500(0x9e34, float:5.6753E-41)
                r2 = 1620(0x654, float:2.27E-42)
                r16 = 15000(0x3a98, float:2.102E-41)
                goto L_0x0376
            L_0x0325:
                r18 = 30
                r19 = 22
                r20 = 18
                r1 = 11880(0x2e68, float:1.6647E-41)
                r2 = 396(0x18c, float:5.55E-43)
                r16 = 4000(0xfa0, float:5.605E-42)
                goto L_0x0376
            L_0x0332:
                r28 = r1
                r29 = r2
                int r1 = r0.level
                r11 = 1
                if (r1 == r11) goto L_0x0369
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r11 = "Unrecognized profile/level "
                r2.append(r11)
                int r11 = r0.profile
                r2.append(r11)
                java.lang.String r11 = "/"
                r2.append(r11)
                int r11 = r0.level
                r2.append(r11)
                java.lang.String r11 = " for "
                r2.append(r11)
                r2.append(r15)
                java.lang.String r2 = r2.toString()
                android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
                r17 = r17 | 1
                goto L_0x0276
            L_0x0369:
                r18 = 30
                r19 = 45
                r20 = 36
                r1 = 40500(0x9e34, float:5.6753E-41)
                r2 = 1620(0x654, float:2.27E-42)
                r16 = 15000(0x3a98, float:2.102E-41)
            L_0x0376:
                r30 = r0
                r31 = r8
                r32 = r13
                r11 = r16
                r13 = r18
                r0 = r19
                r8 = r20
            L_0x0384:
                if (r21 == 0) goto L_0x038a
                r16 = r17 & -5
                r17 = r16
            L_0x038a:
                r33 = r14
                r34 = r15
                long r14 = (long) r1
                long r4 = java.lang.Math.max(r14, r4)
                int r7 = java.lang.Math.max(r2, r7)
                int r14 = r11 * 1000
                int r3 = java.lang.Math.max(r14, r3)
                int r12 = java.lang.Math.max(r0, r12)
                int r9 = java.lang.Math.max(r8, r9)
                int r6 = java.lang.Math.max(r13, r6)
                int r10 = r10 + 1
                r8 = r31
                r13 = r32
                r14 = r33
                r15 = r34
                goto L_0x023b
            L_0x03b5:
                r32 = r13
                r33 = r14
                r34 = r15
                r8 = 16
                r10 = 16
                r11 = 1
                r13 = 1
                r0 = r58
                r1 = r12
                r2 = r9
                r14 = r3
                r3 = r7
                r15 = r4
                r18 = r6
                r6 = r8
                r19 = r7
                r7 = r10
                r8 = r11
                r10 = r9
                r9 = r13
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                r13 = r58
                android.util.Range<java.lang.Integer> r0 = r13.mFrameRateRange
                r1 = 12
                java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                java.lang.Integer r2 = java.lang.Integer.valueOf(r18)
                android.util.Range r0 = r0.intersect(r1, r2)
                r13.mFrameRateRange = r0
                r12 = r14
                r55 = r33
                r13 = r34
                r20 = 1
                r56 = r15
                r15 = r19
                goto L_0x0218
            L_0x03f6:
                r32 = r13
                r33 = r14
                r34 = r15
                r13 = r58
                java.lang.String r5 = "video/mp4v-es"
                r12 = r34
                boolean r5 = r12.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x069d
                r5 = 11
                r7 = 9
                r8 = 15
                r2 = 99
                r0 = 1485(0x5cd, double:7.337E-321)
                r3 = 64000(0xfa00, float:8.9683E-41)
                r14 = r33
                int r9 = r14.length
                r17 = r4
                r11 = r5
                r15 = r7
                r7 = r8
                r10 = 0
                r4 = r0
                r8 = r2
            L_0x0421:
                if (r10 >= r9) goto L_0x0662
                r0 = r14[r10]
                r1 = 0
                r2 = 0
                r18 = 0
                r19 = 0
                r20 = 0
                r21 = 0
                r22 = 0
                r23 = 1
                int r6 = r0.profile
                switch(r6) {
                    case 1: goto L_0x054a;
                    case 2: goto L_0x0520;
                    case 4: goto L_0x0520;
                    case 8: goto L_0x0520;
                    case 16: goto L_0x0520;
                    case 32: goto L_0x0520;
                    case 64: goto L_0x0520;
                    case 128: goto L_0x0520;
                    case 256: goto L_0x0520;
                    case 512: goto L_0x0520;
                    case 1024: goto L_0x0520;
                    case 2048: goto L_0x0520;
                    case 4096: goto L_0x0520;
                    case 8192: goto L_0x0520;
                    case 16384: goto L_0x0520;
                    case 32768: goto L_0x0472;
                    default: goto L_0x0438;
                }
            L_0x0438:
                r35 = r1
                r36 = r2
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Unrecognized profile "
                r2.append(r6)
                int r6 = r0.profile
                r2.append(r6)
                java.lang.String r6 = " for "
                r2.append(r6)
                r2.append(r12)
                java.lang.String r2 = r2.toString()
                android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
                r17 = r17 | 1
            L_0x045e:
                r37 = r0
                r38 = r9
                r39 = r14
                r6 = r18
                r14 = r19
                r0 = r20
                r9 = r21
                r1 = r35
                r2 = r36
                goto L_0x0615
            L_0x0472:
                int r6 = r0.level
                r35 = r1
                r1 = 1
                if (r6 == r1) goto L_0x0510
                r1 = 4
                if (r6 == r1) goto L_0x0510
                r1 = 8
                if (r6 == r1) goto L_0x0500
                r1 = 16
                if (r6 == r1) goto L_0x04f0
                r1 = 24
                if (r6 == r1) goto L_0x04e0
                r1 = 32
                if (r6 == r1) goto L_0x04d0
                r1 = 128(0x80, float:1.794E-43)
                if (r6 == r1) goto L_0x04bf
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                r36 = r2
                java.lang.String r2 = "Unrecognized profile/level "
                r6.append(r2)
                int r2 = r0.profile
                r6.append(r2)
                java.lang.String r2 = "/"
                r6.append(r2)
                int r2 = r0.level
                r6.append(r2)
                java.lang.String r2 = " for "
                r6.append(r2)
                r6.append(r12)
                java.lang.String r2 = r6.toString()
                android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
                r17 = r17 | 1
                goto L_0x045e
            L_0x04bf:
                r36 = r2
                r19 = 30
                r20 = 45
                r21 = 36
                r1 = 48600(0xbdd8, float:6.8103E-41)
                r2 = 1620(0x654, float:2.27E-42)
                r18 = 8000(0x1f40, float:1.121E-41)
                goto L_0x05b5
            L_0x04d0:
                r36 = r2
                r19 = 30
                r20 = 44
                r21 = 36
                r1 = 23760(0x5cd0, float:3.3295E-41)
                r2 = 792(0x318, float:1.11E-42)
                r18 = 3000(0xbb8, float:4.204E-42)
                goto L_0x05b5
            L_0x04e0:
                r36 = r2
                r19 = 30
                r20 = 22
                r21 = 18
                r1 = 11880(0x2e68, float:1.6647E-41)
                r2 = 396(0x18c, float:5.55E-43)
                r18 = 1500(0x5dc, float:2.102E-42)
                goto L_0x05b5
            L_0x04f0:
                r36 = r2
                r19 = 30
                r20 = 22
                r21 = 18
                r1 = 11880(0x2e68, float:1.6647E-41)
                r2 = 396(0x18c, float:5.55E-43)
                r18 = 768(0x300, float:1.076E-42)
                goto L_0x05b5
            L_0x0500:
                r36 = r2
                r19 = 30
                r20 = 22
                r21 = 18
                r1 = 5940(0x1734, float:8.324E-42)
                r2 = 396(0x18c, float:5.55E-43)
                r18 = 384(0x180, float:5.38E-43)
                goto L_0x05b5
            L_0x0510:
                r36 = r2
                r19 = 30
                r20 = 11
                r21 = 9
                r1 = 2970(0xb9a, float:4.162E-42)
                r2 = 99
                r18 = 128(0x80, float:1.794E-43)
                goto L_0x05b5
            L_0x0520:
                r35 = r1
                r36 = r2
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Unsupported profile "
                r2.append(r6)
                int r6 = r0.profile
                r2.append(r6)
                java.lang.String r6 = " for "
                r2.append(r6)
                r2.append(r12)
                java.lang.String r2 = r2.toString()
                android.util.Log.i(r1, r2)
                r17 = r17 | 2
                r23 = 0
                goto L_0x045e
            L_0x054a:
                r35 = r1
                r36 = r2
                int r1 = r0.level
                r2 = 4
                if (r1 == r2) goto L_0x0608
                r2 = 8
                if (r1 == r2) goto L_0x05fb
                r2 = 16
                if (r1 == r2) goto L_0x05ee
                r2 = 64
                if (r1 == r2) goto L_0x05e0
                r2 = 128(0x80, float:1.794E-43)
                if (r1 == r2) goto L_0x05d2
                r2 = 256(0x100, float:3.59E-43)
                if (r1 == r2) goto L_0x05c4
                switch(r1) {
                    case 1: goto L_0x05a7;
                    case 2: goto L_0x0598;
                    default: goto L_0x056a;
                }
            L_0x056a:
                java.lang.String r1 = "VideoCapabilities"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r6 = "Unrecognized profile/level "
                r2.append(r6)
                int r6 = r0.profile
                r2.append(r6)
                java.lang.String r6 = "/"
                r2.append(r6)
                int r6 = r0.level
                r2.append(r6)
                java.lang.String r6 = " for "
                r2.append(r6)
                r2.append(r12)
                java.lang.String r2 = r2.toString()
                android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
                r17 = r17 | 1
                goto L_0x045e
            L_0x0598:
                r22 = 1
                r19 = 15
                r20 = 11
                r21 = 9
                r1 = 1485(0x5cd, float:2.081E-42)
                r2 = 99
                r18 = 128(0x80, float:1.794E-43)
                goto L_0x05b5
            L_0x05a7:
                r22 = 1
                r19 = 15
                r20 = 11
                r21 = 9
                r1 = 1485(0x5cd, float:2.081E-42)
                r2 = 99
                r18 = 64
            L_0x05b5:
                r37 = r0
                r38 = r9
                r39 = r14
                r6 = r18
                r14 = r19
                r0 = r20
                r9 = r21
                goto L_0x0615
            L_0x05c4:
                r19 = 30
                r20 = 80
                r21 = 45
                r1 = 108000(0x1a5e0, float:1.5134E-40)
                r2 = 3600(0xe10, float:5.045E-42)
                r18 = 12000(0x2ee0, float:1.6816E-41)
                goto L_0x05b5
            L_0x05d2:
                r19 = 30
                r20 = 45
                r21 = 36
                r1 = 40500(0x9e34, float:5.6753E-41)
                r2 = 1620(0x654, float:2.27E-42)
                r18 = 8000(0x1f40, float:1.121E-41)
                goto L_0x05b5
            L_0x05e0:
                r19 = 30
                r20 = 40
                r21 = 30
                r1 = 36000(0x8ca0, float:5.0447E-41)
                r2 = 1200(0x4b0, float:1.682E-42)
                r18 = 4000(0xfa0, float:5.605E-42)
                goto L_0x05b5
            L_0x05ee:
                r19 = 30
                r20 = 22
                r21 = 18
                r1 = 11880(0x2e68, float:1.6647E-41)
                r2 = 396(0x18c, float:5.55E-43)
                r18 = 384(0x180, float:5.38E-43)
                goto L_0x05b5
            L_0x05fb:
                r19 = 30
                r20 = 22
                r21 = 18
                r1 = 5940(0x1734, float:8.324E-42)
                r2 = 396(0x18c, float:5.55E-43)
                r18 = 128(0x80, float:1.794E-43)
                goto L_0x05b5
            L_0x0608:
                r19 = 30
                r20 = 11
                r21 = 9
                r1 = 1485(0x5cd, float:2.081E-42)
                r2 = 99
                r18 = 64
                goto L_0x05b5
            L_0x0615:
                if (r23 == 0) goto L_0x0619
                r17 = r17 & -5
            L_0x0619:
                r40 = r12
                long r12 = (long) r1
                long r4 = java.lang.Math.max(r12, r4)
                int r8 = java.lang.Math.max(r2, r8)
                int r12 = r6 * 1000
                int r3 = java.lang.Math.max(r12, r3)
                if (r22 == 0) goto L_0x063a
                int r11 = java.lang.Math.max(r0, r11)
                int r12 = java.lang.Math.max(r9, r15)
                int r7 = java.lang.Math.max(r14, r7)
                r15 = r12
                goto L_0x0656
            L_0x063a:
                int r12 = r2 * 2
                double r12 = (double) r12
                double r12 = java.lang.Math.sqrt(r12)
                int r12 = (int) r12
                int r11 = java.lang.Math.max(r12, r11)
                int r13 = java.lang.Math.max(r12, r15)
                r15 = 60
                int r15 = java.lang.Math.max(r14, r15)
                int r0 = java.lang.Math.max(r15, r7)
                r7 = r0
                r15 = r13
            L_0x0656:
                int r10 = r10 + 1
                r9 = r38
                r14 = r39
                r12 = r40
                r13 = r58
                goto L_0x0421
            L_0x0662:
                r40 = r12
                r39 = r14
                r6 = 16
                r9 = 16
                r10 = 1
                r12 = 1
                r0 = r58
                r1 = r11
                r2 = r15
                r13 = r3
                r3 = r8
                r18 = r4
                r14 = r7
                r7 = r9
                r16 = r8
                r8 = r10
                r9 = r12
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                r12 = r58
                android.util.Range<java.lang.Integer> r0 = r12.mFrameRateRange
                r1 = 12
                java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                java.lang.Integer r2 = java.lang.Integer.valueOf(r14)
                android.util.Range r0 = r0.intersect(r1, r2)
                r12.mFrameRateRange = r0
                r12 = r13
                r15 = r16
                r55 = r39
                r13 = r40
                r20 = 1
                goto L_0x0e12
            L_0x069d:
                r40 = r12
                r12 = r13
                r39 = r33
                java.lang.String r5 = "video/3gpp"
                r13 = r40
                boolean r5 = r13.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x08e6
                r5 = 11
                r6 = 9
                r7 = 15
                r8 = r5
                r9 = r6
                r11 = 16
                r2 = 99
                r0 = 1485(0x5cd, double:7.337E-321)
                r3 = 64000(0xfa00, float:8.9683E-41)
                r14 = r39
                int r15 = r14.length
                r17 = r4
                r18 = r11
                r11 = r8
                r4 = r0
                r0 = 0
                r56 = r7
                r7 = r2
                r2 = r3
                r3 = r56
            L_0x06ce:
                if (r0 >= r15) goto L_0x0885
                r1 = r14[r0]
                r19 = 0
                r20 = 0
                r21 = 0
                r22 = 0
                r23 = 0
                r25 = r11
                r26 = r9
                r27 = 0
                int r10 = r1.level
                r41 = r15
                r15 = 4
                if (r10 == r15) goto L_0x07d8
                r15 = 8
                if (r10 == r15) goto L_0x07c6
                r15 = 16
                if (r10 == r15) goto L_0x079b
                r15 = 32
                if (r10 == r15) goto L_0x0786
                r15 = 64
                if (r10 == r15) goto L_0x0771
                r15 = 128(0x80, float:1.794E-43)
                if (r10 == r15) goto L_0x075c
                switch(r10) {
                    case 1: goto L_0x074b;
                    case 2: goto L_0x073a;
                    default: goto L_0x0700;
                }
            L_0x0700:
                java.lang.String r10 = "VideoCapabilities"
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                r15.<init>()
                r42 = r14
                java.lang.String r14 = "Unrecognized profile/level "
                r15.append(r14)
                int r14 = r1.profile
                r15.append(r14)
                java.lang.String r14 = "/"
                r15.append(r14)
                int r14 = r1.level
                r15.append(r14)
                java.lang.String r14 = " for "
                r15.append(r14)
                r15.append(r13)
                java.lang.String r14 = r15.toString()
                android.util.Log.w((java.lang.String) r10, (java.lang.String) r14)
                r17 = r17 | 1
            L_0x072e:
                r43 = r0
                r10 = r19
                r0 = r21
                r14 = r22
                r15 = r23
                goto L_0x07ea
            L_0x073a:
                r42 = r14
                r27 = 1
                r21 = 30
                r22 = 22
                r23 = 18
                r20 = 2
                int r10 = r22 * r23
                int r19 = r10 * 15
                goto L_0x072e
            L_0x074b:
                r42 = r14
                r27 = 1
                r21 = 15
                r22 = 11
                r23 = 9
                r20 = 1
                int r10 = r22 * r23
                int r19 = r10 * r21
                goto L_0x072e
            L_0x075c:
                r42 = r14
                r25 = 1
                r26 = 1
                r18 = 4
                r21 = 60
                r22 = 45
                r23 = 36
                r20 = 256(0x100, float:3.59E-43)
                int r10 = r22 * r23
                int r19 = r10 * 50
                goto L_0x072e
            L_0x0771:
                r42 = r14
                r25 = 1
                r26 = 1
                r18 = 4
                r21 = 60
                r22 = 45
                r23 = 18
                r20 = 128(0x80, float:1.794E-43)
                int r10 = r22 * r23
                int r19 = r10 * 50
                goto L_0x072e
            L_0x0786:
                r42 = r14
                r25 = 1
                r26 = 1
                r18 = 4
                r21 = 60
                r22 = 22
                r23 = 18
                r20 = 64
                int r10 = r22 * r23
                int r19 = r10 * 50
                goto L_0x072e
            L_0x079b:
                r42 = r14
                int r10 = r1.profile
                r14 = 1
                if (r10 == r14) goto L_0x07aa
                int r10 = r1.profile
                r14 = 4
                if (r10 != r14) goto L_0x07a8
                goto L_0x07aa
            L_0x07a8:
                r10 = 0
                goto L_0x07ab
            L_0x07aa:
                r10 = 1
            L_0x07ab:
                r27 = r10
                if (r27 != 0) goto L_0x07b8
                r10 = 1
                r14 = 1
                r15 = 4
                r25 = r10
                r26 = r14
                r18 = r15
            L_0x07b8:
                r21 = 15
                r22 = 11
                r23 = 9
                r20 = 2
                int r10 = r22 * r23
                int r19 = r10 * r21
                goto L_0x072e
            L_0x07c6:
                r42 = r14
                r27 = 1
                r21 = 30
                r22 = 22
                r23 = 18
                r20 = 32
                int r10 = r22 * r23
                int r19 = r10 * r21
                goto L_0x072e
            L_0x07d8:
                r42 = r14
                r27 = 1
                r21 = 30
                r22 = 22
                r23 = 18
                r20 = 6
                int r10 = r22 * r23
                int r19 = r10 * r21
                goto L_0x072e
            L_0x07ea:
                r44 = r9
                int r9 = r1.profile
                r45 = r11
                r11 = 4
                if (r9 == r11) goto L_0x0833
                r11 = 8
                if (r9 == r11) goto L_0x0833
                r11 = 16
                if (r9 == r11) goto L_0x0833
                r11 = 32
                if (r9 == r11) goto L_0x0833
                r11 = 64
                if (r9 == r11) goto L_0x0833
                r11 = 128(0x80, float:1.794E-43)
                if (r9 == r11) goto L_0x0833
                r11 = 256(0x100, float:3.59E-43)
                if (r9 == r11) goto L_0x0833
                switch(r9) {
                    case 1: goto L_0x0833;
                    case 2: goto L_0x0833;
                    default: goto L_0x080e;
                }
            L_0x080e:
                java.lang.String r9 = "VideoCapabilities"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                r46 = r0
                java.lang.String r0 = "Unrecognized profile "
                r11.append(r0)
                int r0 = r1.profile
                r11.append(r0)
                java.lang.String r0 = " for "
                r11.append(r0)
                r11.append(r13)
                java.lang.String r0 = r11.toString()
                android.util.Log.w((java.lang.String) r9, (java.lang.String) r0)
                r17 = r17 | 1
                goto L_0x0835
            L_0x0833:
                r46 = r0
            L_0x0835:
                if (r27 == 0) goto L_0x0841
                r25 = 11
                r26 = 9
                r0 = r25
                r9 = r26
                r11 = 1
                goto L_0x0848
            L_0x0841:
                r11 = 1
                r12.mAllowMbOverride = r11
                r0 = r25
                r9 = r26
            L_0x0848:
                r17 = r17 & -5
                long r11 = (long) r10
                long r4 = java.lang.Math.max(r11, r4)
                int r11 = r14 * r15
                int r7 = java.lang.Math.max(r11, r7)
                r11 = 64000(0xfa00, float:8.9683E-41)
                int r11 = r11 * r20
                int r2 = java.lang.Math.max(r11, r2)
                int r8 = java.lang.Math.max(r14, r8)
                int r6 = java.lang.Math.max(r15, r6)
                r11 = r46
                int r3 = java.lang.Math.max(r11, r3)
                r12 = r45
                int r12 = java.lang.Math.min(r0, r12)
                r47 = r10
                r10 = r44
                int r9 = java.lang.Math.min(r9, r10)
                int r0 = r43 + 1
                r11 = r12
                r15 = r41
                r14 = r42
                r12 = r58
                goto L_0x06ce
            L_0x0885:
                r10 = r9
                r12 = r11
                r42 = r14
                r14 = r58
                boolean r0 = r14.mAllowMbOverride
                if (r0 != 0) goto L_0x08a7
                android.util.Rational r0 = new android.util.Rational
                r1 = 11
                r9 = 9
                r0.<init>(r1, r9)
                android.util.Rational r1 = new android.util.Rational
                r9 = 11
                r11 = 9
                r1.<init>(r9, r11)
                android.util.Range r0 = android.util.Range.create(r0, r1)
                r14.mBlockAspectRatioRange = r0
            L_0x08a7:
                r9 = 16
                r11 = 16
                r0 = r58
                r1 = r12
                r15 = r2
                r2 = r10
                r16 = r3
                r3 = r8
                r19 = r4
                r4 = r6
                r5 = r7
                r21 = r6
                r22 = r7
                r6 = r19
                r23 = r8
                r8 = r9
                r24 = r10
                r9 = r11
                r10 = r18
                r48 = 1
                r11 = r18
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r5, r6, r8, r9, r10, r11)
                java.lang.Integer r0 = java.lang.Integer.valueOf(r48)
                java.lang.Integer r1 = java.lang.Integer.valueOf(r16)
                android.util.Range r0 = android.util.Range.create(r0, r1)
                r14.mFrameRateRange = r0
                r12 = r15
                r18 = r19
                r15 = r22
                r55 = r42
                r20 = r48
                goto L_0x0e12
            L_0x08e6:
                r14 = r12
                r42 = r39
                r48 = 1
                java.lang.String r5 = "video/x-vnd.on2.vp8"
                boolean r5 = r13.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x0992
                r10 = 2147483647(0x7fffffff, float:NaN)
                r11 = 2147483647(0x7fffffff, double:1.060997895E-314)
                r15 = 100000000(0x5f5e100, float:2.3122341E-35)
                r9 = r42
                int r0 = r9.length
                r17 = r4
                r1 = 0
            L_0x0903:
                if (r1 >= r0) goto L_0x0968
                r2 = r9[r1]
                int r3 = r2.level
                r4 = 4
                if (r3 == r4) goto L_0x0936
                r4 = 8
                if (r3 == r4) goto L_0x0936
                switch(r3) {
                    case 1: goto L_0x0936;
                    case 2: goto L_0x0936;
                    default: goto L_0x0913;
                }
            L_0x0913:
                java.lang.String r3 = "VideoCapabilities"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Unrecognized level "
                r4.append(r5)
                int r5 = r2.level
                r4.append(r5)
                java.lang.String r5 = " for "
                r4.append(r5)
                r4.append(r13)
                java.lang.String r4 = r4.toString()
                android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)
                r17 = r17 | 1
                goto L_0x0937
            L_0x0936:
            L_0x0937:
                int r3 = r2.profile
                r8 = r48
                if (r3 == r8) goto L_0x0960
                java.lang.String r3 = "VideoCapabilities"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Unrecognized profile "
                r4.append(r5)
                int r5 = r2.profile
                r4.append(r5)
                java.lang.String r5 = " for "
                r4.append(r5)
                r4.append(r13)
                java.lang.String r4 = r4.toString()
                android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)
                r17 = r17 | 1
                goto L_0x0961
            L_0x0960:
            L_0x0961:
                r17 = r17 & -5
                int r1 = r1 + 1
                r48 = r8
                goto L_0x0903
            L_0x0968:
                r8 = r48
                r16 = 16
                r1 = 32767(0x7fff, float:4.5916E-41)
                r2 = 32767(0x7fff, float:4.5916E-41)
                r6 = 16
                r7 = 16
                r18 = 1
                r19 = 1
                r0 = r58
                r3 = r10
                r4 = r11
                r20 = r8
                r8 = r18
                r49 = r10
                r10 = r9
                r9 = r19
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                r55 = r10
                r18 = r11
                r12 = r15
                r15 = r49
                goto L_0x0e12
            L_0x0992:
                r10 = r42
                r20 = r48
                java.lang.String r5 = "video/x-vnd.on2.vp9"
                boolean r5 = r13.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x0b18
                r0 = 829440(0xca800, double:4.09798E-318)
                r2 = 36864(0x9000, float:5.1657E-41)
                r3 = 200000(0x30d40, float:2.8026E-40)
                r5 = 512(0x200, float:7.175E-43)
                int r8 = r10.length
                r12 = r3
                r17 = r4
                r11 = r5
                r3 = r0
                r0 = 0
            L_0x09b1:
                if (r0 >= r8) goto L_0x0aed
                r1 = r10[r0]
                r18 = 0
                r5 = 0
                r9 = 0
                r15 = 0
                int r6 = r1.level
                switch(r6) {
                    case 1: goto L_0x0a7c;
                    case 2: goto L_0x0a71;
                    case 4: goto L_0x0a66;
                    case 8: goto L_0x0a5b;
                    case 16: goto L_0x0a50;
                    case 32: goto L_0x0a46;
                    case 64: goto L_0x0a3c;
                    case 128: goto L_0x0a32;
                    case 256: goto L_0x0a27;
                    case 512: goto L_0x0a1c;
                    case 1024: goto L_0x0a11;
                    case 2048: goto L_0x0a05;
                    case 4096: goto L_0x09f7;
                    case 8192: goto L_0x09e9;
                    default: goto L_0x09bf;
                }
            L_0x09bf:
                java.lang.String r6 = "VideoCapabilities"
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r50 = r5
                java.lang.String r5 = "Unrecognized level "
                r7.append(r5)
                int r5 = r1.level
                r7.append(r5)
                java.lang.String r5 = " for "
                r7.append(r5)
                r7.append(r13)
                java.lang.String r5 = r7.toString()
                android.util.Log.w((java.lang.String) r6, (java.lang.String) r5)
                r17 = r17 | 1
                r5 = r18
                r7 = r50
                goto L_0x0a89
            L_0x09e9:
                r18 = 4706009088(0x118800000, double:2.3250774194E-314)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 480000(0x75300, float:6.72623E-40)
                r15 = 16832(0x41c0, float:2.3587E-41)
                goto L_0x0a86
            L_0x09f7:
                r18 = 2353004544(0x8c400000, double:1.1625387097E-314)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 240000(0x3a980, float:3.36312E-40)
                r15 = 16832(0x41c0, float:2.3587E-41)
                goto L_0x0a86
            L_0x0a05:
                r18 = 1176502272(0x46200000, double:5.81269355E-315)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 180000(0x2bf20, float:2.52234E-40)
                r15 = 16832(0x41c0, float:2.3587E-41)
                goto L_0x0a86
            L_0x0a11:
                r18 = 1176502272(0x46200000, double:5.81269355E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 180000(0x2bf20, float:2.52234E-40)
                r15 = 8384(0x20c0, float:1.1748E-41)
                goto L_0x0a86
            L_0x0a1c:
                r18 = 588251136(0x23100000, double:2.906346774E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 120000(0x1d4c0, float:1.68156E-40)
                r15 = 8384(0x20c0, float:1.1748E-41)
                goto L_0x0a86
            L_0x0a27:
                r18 = 311951360(0x12980000, double:1.5412445E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 60000(0xea60, float:8.4078E-41)
                r15 = 8384(0x20c0, float:1.1748E-41)
                goto L_0x0a86
            L_0x0a32:
                r18 = 160432128(0x9900000, double:7.9264003E-316)
                r5 = 2228224(0x220000, float:3.122407E-39)
                r9 = 30000(0x7530, float:4.2039E-41)
                r15 = 4160(0x1040, float:5.83E-42)
                goto L_0x0a86
            L_0x0a3c:
                r18 = 83558400(0x4fb0000, double:4.1283335E-316)
                r5 = 2228224(0x220000, float:3.122407E-39)
                r9 = 18000(0x4650, float:2.5223E-41)
                r15 = 4160(0x1040, float:5.83E-42)
                goto L_0x0a86
            L_0x0a46:
                r18 = 36864000(0x2328000, double:1.8213236E-316)
                r5 = 983040(0xf0000, float:1.377532E-39)
                r9 = 12000(0x2ee0, float:1.6816E-41)
                r15 = 2752(0xac0, float:3.856E-42)
                goto L_0x0a86
            L_0x0a50:
                r18 = 20736000(0x13c6800, double:1.0244945E-316)
                r5 = 552960(0x87000, float:7.74862E-40)
                r9 = 7200(0x1c20, float:1.009E-41)
                r15 = 2048(0x800, float:2.87E-42)
                goto L_0x0a86
            L_0x0a5b:
                r18 = 9216000(0x8ca000, double:4.553309E-317)
                r5 = 245760(0x3c000, float:3.44383E-40)
                r9 = 3600(0xe10, float:5.045E-42)
                r15 = 1344(0x540, float:1.883E-42)
                goto L_0x0a86
            L_0x0a66:
                r18 = 4608000(0x465000, double:2.2766545E-317)
                r5 = 122880(0x1e000, float:1.72192E-40)
                r9 = 1800(0x708, float:2.522E-42)
                r15 = 960(0x3c0, float:1.345E-42)
                goto L_0x0a86
            L_0x0a71:
                r18 = 2764800(0x2a3000, double:1.3659927E-317)
                r5 = 73728(0x12000, float:1.03315E-40)
                r9 = 800(0x320, float:1.121E-42)
                r15 = 768(0x300, float:1.076E-42)
                goto L_0x0a86
            L_0x0a7c:
                r18 = 829440(0xca800, double:4.09798E-318)
                r5 = 36864(0x9000, float:5.1657E-41)
                r9 = 200(0xc8, float:2.8E-43)
                r15 = 512(0x200, float:7.175E-43)
            L_0x0a86:
                r7 = r5
                r5 = r18
            L_0x0a89:
                r51 = r8
                int r8 = r1.profile
                r14 = 4
                if (r8 == r14) goto L_0x0acd
                r14 = 8
                if (r8 == r14) goto L_0x0acd
                r14 = 4096(0x1000, float:5.74E-42)
                if (r8 == r14) goto L_0x0acd
                r14 = 8192(0x2000, float:1.14794E-41)
                if (r8 == r14) goto L_0x0acd
                r14 = 16384(0x4000, float:2.2959E-41)
                if (r8 == r14) goto L_0x0acd
                r14 = 32768(0x8000, float:4.5918E-41)
                if (r8 == r14) goto L_0x0acd
                switch(r8) {
                    case 1: goto L_0x0acd;
                    case 2: goto L_0x0acd;
                    default: goto L_0x0aa8;
                }
            L_0x0aa8:
                java.lang.String r8 = "VideoCapabilities"
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                r52 = r10
                java.lang.String r10 = "Unrecognized profile "
                r14.append(r10)
                int r10 = r1.profile
                r14.append(r10)
                java.lang.String r10 = " for "
                r14.append(r10)
                r14.append(r13)
                java.lang.String r10 = r14.toString()
                android.util.Log.w((java.lang.String) r8, (java.lang.String) r10)
                r17 = r17 | 1
                goto L_0x0acf
            L_0x0acd:
                r52 = r10
            L_0x0acf:
                r17 = r17 & -5
                long r3 = java.lang.Math.max(r5, r3)
                int r2 = java.lang.Math.max(r7, r2)
                int r8 = r9 * 1000
                int r12 = java.lang.Math.max(r8, r12)
                int r11 = java.lang.Math.max(r15, r11)
                int r0 = r0 + 1
                r8 = r51
                r10 = r52
                r14 = r58
                goto L_0x09b1
            L_0x0aed:
                r52 = r10
                r10 = 8
                r0 = 8
                int r14 = android.media.Utils.divUp((int) r11, (int) r0)
                r0 = 64
                int r15 = android.media.Utils.divUp((int) r2, (int) r0)
                r0 = 64
                long r18 = android.media.Utils.divUp((long) r3, (long) r0)
                r6 = 8
                r7 = 8
                r8 = 1
                r9 = 1
                r0 = r58
                r1 = r14
                r2 = r14
                r3 = r15
                r4 = r18
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                r55 = r52
                goto L_0x0e12
            L_0x0b18:
                r52 = r10
                java.lang.String r5 = "video/hevc"
                boolean r5 = r13.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x0c84
                r2 = 576(0x240, float:8.07E-43)
                int r5 = r2 * 15
                long r0 = (long) r5
                r3 = 128000(0x1f400, float:1.79366E-40)
                r10 = r52
                int r5 = r10.length
                r14 = r0
                r11 = r2
                r12 = r3
                r17 = r4
                r0 = 0
            L_0x0b34:
                if (r0 >= r5) goto L_0x0c60
                r1 = r10[r0]
                r2 = 0
                r4 = 0
                r6 = 0
                int r7 = r1.level
                switch(r7) {
                    case 1: goto L_0x0c0b;
                    case 2: goto L_0x0c0b;
                    case 4: goto L_0x0c03;
                    case 8: goto L_0x0c03;
                    case 16: goto L_0x0bfb;
                    case 32: goto L_0x0bfb;
                    case 64: goto L_0x0bf3;
                    case 128: goto L_0x0bf3;
                    case 256: goto L_0x0be9;
                    case 512: goto L_0x0be9;
                    case 1024: goto L_0x0be2;
                    case 2048: goto L_0x0bdb;
                    case 4096: goto L_0x0bd4;
                    case 8192: goto L_0x0bcc;
                    case 16384: goto L_0x0bc5;
                    case 32768: goto L_0x0bbd;
                    case 65536: goto L_0x0bb5;
                    case 131072: goto L_0x0bad;
                    case 262144: goto L_0x0ba4;
                    case 524288: goto L_0x0b9b;
                    case 1048576: goto L_0x0b92;
                    case 2097152: goto L_0x0b89;
                    case 4194304: goto L_0x0b80;
                    case 8388608: goto L_0x0b77;
                    case 16777216: goto L_0x0b6e;
                    case 33554432: goto L_0x0b65;
                    default: goto L_0x0b41;
                }
            L_0x0b41:
                java.lang.String r7 = "VideoCapabilities"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "Unrecognized level "
                r8.append(r9)
                int r9 = r1.level
                r8.append(r9)
                java.lang.String r9 = " for "
                r8.append(r9)
                r8.append(r13)
                java.lang.String r8 = r8.toString()
                android.util.Log.w((java.lang.String) r7, (java.lang.String) r8)
                r17 = r17 | 1
                goto L_0x0c12
            L_0x0b65:
                r2 = 4638144666238189568(0x405e000000000000, double:120.0)
                r4 = 35651584(0x2200000, float:1.1754944E-37)
                r6 = 800000(0xc3500, float:1.121039E-39)
                goto L_0x0c12
            L_0x0b6e:
                r2 = 4638144666238189568(0x405e000000000000, double:120.0)
                r4 = 35651584(0x2200000, float:1.1754944E-37)
                r6 = 240000(0x3a980, float:3.36312E-40)
                goto L_0x0c12
            L_0x0b77:
                r2 = 4633641066610819072(0x404e000000000000, double:60.0)
                r4 = 35651584(0x2200000, float:1.1754944E-37)
                r6 = 480000(0x75300, float:6.72623E-40)
                goto L_0x0c12
            L_0x0b80:
                r2 = 4633641066610819072(0x404e000000000000, double:60.0)
                r4 = 35651584(0x2200000, float:1.1754944E-37)
                r6 = 120000(0x1d4c0, float:1.68156E-40)
                goto L_0x0c12
            L_0x0b89:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 35651584(0x2200000, float:1.1754944E-37)
                r6 = 240000(0x3a980, float:3.36312E-40)
                goto L_0x0c12
            L_0x0b92:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 35651584(0x2200000, float:1.1754944E-37)
                r6 = 60000(0xea60, float:8.4078E-41)
                goto L_0x0c12
            L_0x0b9b:
                r2 = 4638144666238189568(0x405e000000000000, double:120.0)
                r4 = 8912896(0x880000, float:1.2489627E-38)
                r6 = 240000(0x3a980, float:3.36312E-40)
                goto L_0x0c12
            L_0x0ba4:
                r2 = 4638144666238189568(0x405e000000000000, double:120.0)
                r4 = 8912896(0x880000, float:1.2489627E-38)
                r6 = 60000(0xea60, float:8.4078E-41)
                goto L_0x0c12
            L_0x0bad:
                r2 = 4633641066610819072(0x404e000000000000, double:60.0)
                r4 = 8912896(0x880000, float:1.2489627E-38)
                r6 = 160000(0x27100, float:2.24208E-40)
                goto L_0x0c12
            L_0x0bb5:
                r2 = 4633641066610819072(0x404e000000000000, double:60.0)
                r4 = 8912896(0x880000, float:1.2489627E-38)
                r6 = 40000(0x9c40, float:5.6052E-41)
                goto L_0x0c12
            L_0x0bbd:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 8912896(0x880000, float:1.2489627E-38)
                r6 = 100000(0x186a0, float:1.4013E-40)
                goto L_0x0c12
            L_0x0bc5:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 8912896(0x880000, float:1.2489627E-38)
                r6 = 25000(0x61a8, float:3.5032E-41)
                goto L_0x0c12
            L_0x0bcc:
                r2 = 4633641066610819072(0x404e000000000000, double:60.0)
                r4 = 2228224(0x220000, float:3.122407E-39)
                r6 = 50000(0xc350, float:7.0065E-41)
                goto L_0x0c12
            L_0x0bd4:
                r2 = 4633641066610819072(0x404e000000000000, double:60.0)
                r4 = 2228224(0x220000, float:3.122407E-39)
                r6 = 20000(0x4e20, float:2.8026E-41)
                goto L_0x0c12
            L_0x0bdb:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 2228224(0x220000, float:3.122407E-39)
                r6 = 30000(0x7530, float:4.2039E-41)
                goto L_0x0c12
            L_0x0be2:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 2228224(0x220000, float:3.122407E-39)
                r6 = 12000(0x2ee0, float:1.6816E-41)
                goto L_0x0c12
            L_0x0be9:
                r2 = 4629946707541491712(0x4040e00000000000, double:33.75)
                r4 = 983040(0xf0000, float:1.377532E-39)
                r6 = 10000(0x2710, float:1.4013E-41)
                goto L_0x0c12
            L_0x0bf3:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 552960(0x87000, float:7.74862E-40)
                r6 = 6000(0x1770, float:8.408E-42)
                goto L_0x0c12
            L_0x0bfb:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 245760(0x3c000, float:3.44383E-40)
                r6 = 3000(0xbb8, float:4.204E-42)
                goto L_0x0c12
            L_0x0c03:
                r2 = 4629137466983448576(0x403e000000000000, double:30.0)
                r4 = 122880(0x1e000, float:1.72192E-40)
                r6 = 1500(0x5dc, float:2.102E-42)
                goto L_0x0c12
            L_0x0c0b:
                r2 = 4624633867356078080(0x402e000000000000, double:15.0)
                r4 = 36864(0x9000, float:5.1657E-41)
                r6 = 128(0x80, float:1.794E-43)
            L_0x0c12:
                int r7 = r1.profile
                r8 = 4
                if (r7 == r8) goto L_0x0c45
                r9 = 4096(0x1000, float:5.74E-42)
                if (r7 == r9) goto L_0x0c45
                r9 = 8192(0x2000, float:1.14794E-41)
                if (r7 == r9) goto L_0x0c45
                switch(r7) {
                    case 1: goto L_0x0c45;
                    case 2: goto L_0x0c45;
                    default: goto L_0x0c22;
                }
            L_0x0c22:
                java.lang.String r7 = "VideoCapabilities"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r8 = "Unrecognized profile "
                r9.append(r8)
                int r8 = r1.profile
                r9.append(r8)
                java.lang.String r8 = " for "
                r9.append(r8)
                r9.append(r13)
                java.lang.String r8 = r9.toString()
                android.util.Log.w((java.lang.String) r7, (java.lang.String) r8)
                r17 = r17 | 1
                goto L_0x0c46
            L_0x0c45:
            L_0x0c46:
                int r4 = r4 >> 6
                r17 = r17 & -5
                double r7 = (double) r4
                double r7 = r7 * r2
                int r7 = (int) r7
                long r7 = (long) r7
                long r14 = java.lang.Math.max(r7, r14)
                int r11 = java.lang.Math.max(r4, r11)
                int r7 = r6 * 1000
                int r12 = java.lang.Math.max(r7, r12)
                int r0 = r0 + 1
                goto L_0x0b34
            L_0x0c60:
                int r0 = r11 * 8
                double r0 = (double) r0
                double r0 = java.lang.Math.sqrt(r0)
                int r9 = (int) r0
                r6 = 8
                r7 = 8
                r8 = 1
                r16 = 1
                r0 = r58
                r1 = r9
                r2 = r9
                r3 = r11
                r4 = r14
                r18 = r9
                r9 = r16
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                r55 = r10
                r18 = r14
                r15 = r11
                goto L_0x0e12
            L_0x0c84:
                r10 = r52
                java.lang.String r5 = "video/av01"
                boolean r5 = r13.equalsIgnoreCase(r5)
                if (r5 == 0) goto L_0x0df1
                r0 = 829440(0xca800, double:4.09798E-318)
                r2 = 36864(0x9000, float:5.1657E-41)
                r3 = 200000(0x30d40, float:2.8026E-40)
                r5 = 512(0x200, float:7.175E-43)
                int r6 = r10.length
                r12 = r3
                r17 = r4
                r11 = r5
                r3 = r0
                r0 = 0
            L_0x0ca1:
                if (r0 >= r6) goto L_0x0dca
                r1 = r10[r0]
                r7 = 0
                r5 = 0
                r9 = 0
                r14 = 0
                int r15 = r1.level
                switch(r15) {
                    case 1: goto L_0x0d6e;
                    case 2: goto L_0x0d63;
                    case 4: goto L_0x0d63;
                    case 8: goto L_0x0d63;
                    case 16: goto L_0x0d58;
                    case 32: goto L_0x0d4d;
                    case 64: goto L_0x0d4d;
                    case 128: goto L_0x0d4d;
                    case 256: goto L_0x0d43;
                    case 512: goto L_0x0d39;
                    case 1024: goto L_0x0d39;
                    case 2048: goto L_0x0d39;
                    case 4096: goto L_0x0d2f;
                    case 8192: goto L_0x0d24;
                    case 16384: goto L_0x0d19;
                    case 32768: goto L_0x0d0e;
                    case 65536: goto L_0x0d03;
                    case 131072: goto L_0x0cf5;
                    case 262144: goto L_0x0ce7;
                    case 524288: goto L_0x0cd9;
                    default: goto L_0x0caf;
                }
            L_0x0caf:
                java.lang.String r15 = "VideoCapabilities"
                r53 = r5
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                r54 = r6
                java.lang.String r6 = "Unrecognized level "
                r5.append(r6)
                int r6 = r1.level
                r5.append(r6)
                java.lang.String r6 = " for "
                r5.append(r6)
                r5.append(r13)
                java.lang.String r5 = r5.toString()
                android.util.Log.w((java.lang.String) r15, (java.lang.String) r5)
                r17 = r17 | 1
                r5 = r53
                goto L_0x0d7a
            L_0x0cd9:
                r7 = 4706009088(0x118800000, double:2.3250774194E-314)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 160000(0x27100, float:2.24208E-40)
                r14 = 16384(0x4000, float:2.2959E-41)
                goto L_0x0d78
            L_0x0ce7:
                r7 = 4379443200(0x105090000, double:2.163732433E-314)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 160000(0x27100, float:2.24208E-40)
                r14 = 16384(0x4000, float:2.2959E-41)
                goto L_0x0d78
            L_0x0cf5:
                r7 = 2189721600(0x82848000, double:1.0818662165E-314)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 100000(0x186a0, float:1.4013E-40)
                r14 = 16384(0x4000, float:2.2959E-41)
                goto L_0x0d78
            L_0x0d03:
                r7 = 1176502272(0x46200000, double:5.81269355E-315)
                r5 = 35651584(0x2200000, float:1.1754944E-37)
                r9 = 60000(0xea60, float:8.4078E-41)
                r14 = 16384(0x4000, float:2.2959E-41)
                goto L_0x0d78
            L_0x0d0e:
                r7 = 1176502272(0x46200000, double:5.81269355E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 60000(0xea60, float:8.4078E-41)
                r14 = 8192(0x2000, float:1.14794E-41)
                goto L_0x0d78
            L_0x0d19:
                r7 = 1094860800(0x41424000, double:5.409331083E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 60000(0xea60, float:8.4078E-41)
                r14 = 8192(0x2000, float:1.14794E-41)
                goto L_0x0d78
            L_0x0d24:
                r7 = 547430400(0x20a12000, double:2.70466554E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 40000(0x9c40, float:5.6052E-41)
                r14 = 8192(0x2000, float:1.14794E-41)
                goto L_0x0d78
            L_0x0d2f:
                r7 = 273715200(0x10509000, double:1.35233277E-315)
                r5 = 8912896(0x880000, float:1.2489627E-38)
                r9 = 30000(0x7530, float:4.2039E-41)
                r14 = 8192(0x2000, float:1.14794E-41)
                goto L_0x0d78
            L_0x0d39:
                r7 = 155713536(0x9480000, double:7.69327087E-316)
                r5 = 2359296(0x240000, float:3.306078E-39)
                r9 = 20000(0x4e20, float:2.8026E-41)
                r14 = 6144(0x1800, float:8.61E-42)
                goto L_0x0d78
            L_0x0d43:
                r7 = 77856768(0x4a40000, double:3.84663544E-316)
                r5 = 2359296(0x240000, float:3.306078E-39)
                r9 = 12000(0x2ee0, float:1.6816E-41)
                r14 = 6144(0x1800, float:8.61E-42)
                goto L_0x0d78
            L_0x0d4d:
                r7 = 39938400(0x2616960, double:1.97321914E-316)
                r5 = 1065024(0x104040, float:1.492416E-39)
                r9 = 10000(0x2710, float:1.4013E-41)
                r14 = 5504(0x1580, float:7.713E-42)
                goto L_0x0d78
            L_0x0d58:
                r7 = 24969600(0x17d0180, double:1.23366216E-316)
                r5 = 665856(0xa2900, float:9.33063E-40)
                r9 = 6000(0x1770, float:8.408E-42)
                r14 = 4352(0x1100, float:6.098E-42)
                goto L_0x0d78
            L_0x0d63:
                r7 = 10454400(0x9f8580, double:5.16516E-317)
                r5 = 278784(0x44100, float:3.9066E-40)
                r9 = 3000(0xbb8, float:4.204E-42)
                r14 = 2816(0xb00, float:3.946E-42)
                goto L_0x0d78
            L_0x0d6e:
                r7 = 5529600(0x546000, double:2.7319854E-317)
                r5 = 147456(0x24000, float:2.0663E-40)
                r9 = 1500(0x5dc, float:2.102E-42)
                r14 = 2048(0x800, float:2.87E-42)
            L_0x0d78:
                r54 = r6
            L_0x0d7a:
                int r6 = r1.profile
                r15 = 4096(0x1000, float:5.74E-42)
                if (r6 == r15) goto L_0x0dac
                r15 = 8192(0x2000, float:1.14794E-41)
                if (r6 == r15) goto L_0x0dac
                switch(r6) {
                    case 1: goto L_0x0dac;
                    case 2: goto L_0x0dac;
                    default: goto L_0x0d87;
                }
            L_0x0d87:
                java.lang.String r6 = "VideoCapabilities"
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                r15.<init>()
                r55 = r10
                java.lang.String r10 = "Unrecognized profile "
                r15.append(r10)
                int r10 = r1.profile
                r15.append(r10)
                java.lang.String r10 = " for "
                r15.append(r10)
                r15.append(r13)
                java.lang.String r10 = r15.toString()
                android.util.Log.w((java.lang.String) r6, (java.lang.String) r10)
                r17 = r17 | 1
                goto L_0x0dae
            L_0x0dac:
                r55 = r10
            L_0x0dae:
                r17 = r17 & -5
                long r3 = java.lang.Math.max(r7, r3)
                int r2 = java.lang.Math.max(r5, r2)
                int r6 = r9 * 1000
                int r12 = java.lang.Math.max(r6, r12)
                int r11 = java.lang.Math.max(r14, r11)
                int r0 = r0 + 1
                r6 = r54
                r10 = r55
                goto L_0x0ca1
            L_0x0dca:
                r55 = r10
                r10 = 8
                r0 = 8
                int r14 = android.media.Utils.divUp((int) r11, (int) r0)
                r0 = 64
                int r15 = android.media.Utils.divUp((int) r2, (int) r0)
                r0 = 64
                long r18 = android.media.Utils.divUp((long) r3, (long) r0)
                r6 = 8
                r7 = 8
                r8 = 1
                r9 = 1
                r0 = r58
                r1 = r14
                r2 = r14
                r3 = r15
                r4 = r18
                r0.applyMacroBlockLimits(r1, r2, r3, r4, r6, r7, r8, r9)
                goto L_0x0e12
            L_0x0df1:
                r55 = r10
                java.lang.String r5 = "VideoCapabilities"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "Unsupported mime "
                r6.append(r7)
                r6.append(r13)
                java.lang.String r6 = r6.toString()
                android.util.Log.w((java.lang.String) r5, (java.lang.String) r6)
                r5 = 64000(0xfa00, float:8.9683E-41)
                r17 = r4 | 2
                r18 = r0
                r15 = r2
                r12 = r5
            L_0x0e12:
                java.lang.Integer r0 = java.lang.Integer.valueOf(r20)
                java.lang.Integer r1 = java.lang.Integer.valueOf(r12)
                android.util.Range r0 = android.util.Range.create(r0, r1)
                r1 = r58
                r1.mBitrateRange = r0
                android.media.MediaCodecInfo$CodecCapabilities r0 = r1.mParent
                int r2 = r0.mError
                r2 = r2 | r17
                r0.mError = r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaCodecInfo.VideoCapabilities.applyLevelLimits():void");
        }
    }

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
            for (Feature feat : bitrates) {
                if (feat.mName.equalsIgnoreCase(mode)) {
                    return feat.mValue;
                }
            }
            return 0;
        }

        public boolean isBitrateModeSupported(int mode) {
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
            Map<String, Object> map = info.getMap();
            if (info.containsKey("complexity-range")) {
                this.mComplexityRange = Utils.parseIntRange(info.getString("complexity-range"), this.mComplexityRange);
            }
            if (info.containsKey("quality-range")) {
                this.mQualityRange = Utils.parseIntRange(info.getString("quality-range"), this.mQualityRange);
            }
            if (info.containsKey("feature-bitrate-modes")) {
                String[] split = info.getString("feature-bitrate-modes").split(SmsManager.REGEX_PREFIX_DELIMITER);
                int length = split.length;
                for (int i = 0; i < length; i++) {
                    this.mBitControl |= 1 << parseBitrateMode(split[i]);
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
            if (!(1 == 0 || complexity == null)) {
                ok = this.mComplexityRange.contains(complexity);
            }
            if (ok && quality != null) {
                ok = this.mQualityRange.contains(quality);
            }
            if (!ok || profile == null) {
                return ok;
            }
            CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
            int length = codecProfileLevelArr.length;
            boolean ok2 = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (codecProfileLevelArr[i].profile == profile.intValue()) {
                    profile = null;
                    break;
                } else {
                    i++;
                }
            }
            if (profile == null) {
                ok2 = true;
            }
            return ok2;
        }

        public void getDefaultFormat(MediaFormat format) {
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
            return supports(complexity, (Integer) map.get(MediaFormat.KEY_QUALITY), profile);
        }
    }

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
            if (other.profile == this.profile && other.level == this.level) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Long.hashCode((((long) this.profile) << 32) | ((long) this.level));
        }
    }

    public final CodecCapabilities getCapabilitiesForType(String type) {
        CodecCapabilities caps = this.mCaps.get(type);
        if (caps != null) {
            return caps.dup();
        }
        throw new IllegalArgumentException("codec does not support type");
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
