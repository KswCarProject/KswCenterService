package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.MediaFormat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class MediaFormat {
    public static final int COLOR_RANGE_FULL = 1;
    public static final int COLOR_RANGE_LIMITED = 2;
    public static final int COLOR_STANDARD_BT2020 = 6;
    public static final int COLOR_STANDARD_BT601_NTSC = 4;
    public static final int COLOR_STANDARD_BT601_PAL = 2;
    public static final int COLOR_STANDARD_BT709 = 1;
    public static final int COLOR_TRANSFER_HLG = 7;
    public static final int COLOR_TRANSFER_LINEAR = 1;
    public static final int COLOR_TRANSFER_SDR_VIDEO = 3;
    public static final int COLOR_TRANSFER_ST2084 = 6;
    public static final String KEY_AAC_DRC_ATTENUATION_FACTOR = "aac-drc-cut-level";
    public static final String KEY_AAC_DRC_BOOST_FACTOR = "aac-drc-boost-level";
    public static final String KEY_AAC_DRC_EFFECT_TYPE = "aac-drc-effect-type";
    public static final String KEY_AAC_DRC_HEAVY_COMPRESSION = "aac-drc-heavy-compression";
    public static final String KEY_AAC_DRC_TARGET_REFERENCE_LEVEL = "aac-target-ref-level";
    public static final String KEY_AAC_ENCODED_TARGET_LEVEL = "aac-encoded-target-level";
    public static final String KEY_AAC_MAX_OUTPUT_CHANNEL_COUNT = "aac-max-output-channel_count";
    public static final String KEY_AAC_PROFILE = "aac-profile";
    public static final String KEY_AAC_SBR_MODE = "aac-sbr-mode";
    public static final String KEY_AUDIO_SESSION_ID = "audio-session-id";
    public static final String KEY_BITRATE_MODE = "bitrate-mode";
    public static final String KEY_BIT_RATE = "bitrate";
    public static final String KEY_CAPTURE_RATE = "capture-rate";
    public static final String KEY_CA_PRIVATE_DATA = "ca-private-data";
    public static final String KEY_CA_SESSION_ID = "ca-session-id";
    public static final String KEY_CA_SYSTEM_ID = "ca-system-id";
    public static final String KEY_CHANNEL_COUNT = "channel-count";
    public static final String KEY_CHANNEL_MASK = "channel-mask";
    public static final String KEY_COLOR_FORMAT = "color-format";
    public static final String KEY_COLOR_RANGE = "color-range";
    public static final String KEY_COLOR_STANDARD = "color-standard";
    public static final String KEY_COLOR_TRANSFER = "color-transfer";
    public static final String KEY_COMPLEXITY = "complexity";
    public static final String KEY_CREATE_INPUT_SURFACE_SUSPENDED = "create-input-buffers-suspended";
    public static final String KEY_DURATION = "durationUs";
    public static final String KEY_FEATURE_ = "feature-";
    public static final String KEY_FLAC_COMPRESSION_LEVEL = "flac-compression-level";
    public static final String KEY_FRAME_RATE = "frame-rate";
    public static final String KEY_GRID_COLUMNS = "grid-cols";
    public static final String KEY_GRID_ROWS = "grid-rows";
    public static final String KEY_HAPTIC_CHANNEL_COUNT = "haptic-channel-count";
    public static final String KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
    public static final String KEY_HDR_STATIC_INFO = "hdr-static-info";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_INTRA_REFRESH_PERIOD = "intra-refresh-period";
    public static final String KEY_IS_ADTS = "is-adts";
    public static final String KEY_IS_AUTOSELECT = "is-autoselect";
    public static final String KEY_IS_DEFAULT = "is-default";
    public static final String KEY_IS_FORCED_SUBTITLE = "is-forced-subtitle";
    public static final String KEY_IS_TIMED_TEXT = "is-timed-text";
    public static final String KEY_I_FRAME_INTERVAL = "i-frame-interval";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_LATENCY = "latency";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_MAX_BIT_RATE = "max-bitrate";
    public static final String KEY_MAX_B_FRAMES = "max-bframes";
    public static final String KEY_MAX_FPS_TO_ENCODER = "max-fps-to-encoder";
    public static final String KEY_MAX_HEIGHT = "max-height";
    public static final String KEY_MAX_INPUT_SIZE = "max-input-size";
    public static final String KEY_MAX_PTS_GAP_TO_ENCODER = "max-pts-gap-to-encoder";
    public static final String KEY_MAX_WIDTH = "max-width";
    public static final String KEY_MIME = "mime";
    public static final String KEY_OPERATING_RATE = "operating-rate";
    public static final String KEY_OUTPUT_REORDER_DEPTH = "output-reorder-depth";
    public static final String KEY_PCM_ENCODING = "pcm-encoding";
    public static final String KEY_PREPEND_HEADER_TO_SYNC_FRAMES = "prepend-sps-pps-to-idr-frames";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_PUSH_BLANK_BUFFERS_ON_STOP = "push-blank-buffers-on-shutdown";
    public static final String KEY_QUALITY = "quality";
    public static final String KEY_REPEAT_PREVIOUS_FRAME_AFTER = "repeat-previous-frame-after";
    public static final String KEY_ROTATION = "rotation-degrees";
    public static final String KEY_SAMPLE_RATE = "sample-rate";
    public static final String KEY_SLICE_HEIGHT = "slice-height";
    public static final String KEY_STRIDE = "stride";
    public static final String KEY_TEMPORAL_LAYERING = "ts-schema";
    public static final String KEY_TILE_HEIGHT = "tile-height";
    public static final String KEY_TILE_WIDTH = "tile-width";
    public static final String KEY_TRACK_ID = "track-id";
    public static final String KEY_WIDTH = "width";
    public static final String MIMETYPE_AUDIO_AAC = "audio/mp4a-latm";
    public static final String MIMETYPE_AUDIO_AC3 = "audio/ac3";
    public static final String MIMETYPE_AUDIO_AC4 = "audio/ac4";
    public static final String MIMETYPE_AUDIO_AMR_NB = "audio/3gpp";
    public static final String MIMETYPE_AUDIO_AMR_WB = "audio/amr-wb";
    public static final String MIMETYPE_AUDIO_EAC3 = "audio/eac3";
    public static final String MIMETYPE_AUDIO_EAC3_JOC = "audio/eac3-joc";
    public static final String MIMETYPE_AUDIO_FLAC = "audio/flac";
    public static final String MIMETYPE_AUDIO_G711_ALAW = "audio/g711-alaw";
    public static final String MIMETYPE_AUDIO_G711_MLAW = "audio/g711-mlaw";
    public static final String MIMETYPE_AUDIO_MPEG = "audio/mpeg";
    public static final String MIMETYPE_AUDIO_MSGSM = "audio/gsm";
    public static final String MIMETYPE_AUDIO_OPUS = "audio/opus";
    public static final String MIMETYPE_AUDIO_QCELP = "audio/qcelp";
    public static final String MIMETYPE_AUDIO_RAW = "audio/raw";
    public static final String MIMETYPE_AUDIO_SCRAMBLED = "audio/scrambled";
    public static final String MIMETYPE_AUDIO_VORBIS = "audio/vorbis";
    public static final String MIMETYPE_IMAGE_ANDROID_HEIC = "image/vnd.android.heic";
    public static final String MIMETYPE_TEXT_CEA_608 = "text/cea-608";
    public static final String MIMETYPE_TEXT_CEA_708 = "text/cea-708";
    public static final String MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
    public static final String MIMETYPE_TEXT_VTT = "text/vtt";
    public static final String MIMETYPE_VIDEO_AV1 = "video/av01";
    public static final String MIMETYPE_VIDEO_AVC = "video/avc";
    public static final String MIMETYPE_VIDEO_DOLBY_VISION = "video/dolby-vision";
    public static final String MIMETYPE_VIDEO_H263 = "video/3gpp";
    public static final String MIMETYPE_VIDEO_HEVC = "video/hevc";
    public static final String MIMETYPE_VIDEO_MPEG2 = "video/mpeg2";
    public static final String MIMETYPE_VIDEO_MPEG4 = "video/mp4v-es";
    public static final String MIMETYPE_VIDEO_RAW = "video/raw";
    public static final String MIMETYPE_VIDEO_SCRAMBLED = "video/scrambled";
    public static final String MIMETYPE_VIDEO_VP8 = "video/x-vnd.on2.vp8";
    public static final String MIMETYPE_VIDEO_VP9 = "video/x-vnd.on2.vp9";
    public static final int TYPE_BYTE_BUFFER = 5;
    public static final int TYPE_FLOAT = 3;
    public static final int TYPE_INTEGER = 1;
    public static final int TYPE_LONG = 2;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_STRING = 4;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public Map<String, Object> mMap;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorRange {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorStandard {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorTransfer {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    MediaFormat(Map<String, Object> map) {
        this.mMap = map;
    }

    public MediaFormat() {
        this.mMap = new HashMap();
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public Map<String, Object> getMap() {
        return this.mMap;
    }

    public final boolean containsKey(String name) {
        return this.mMap.containsKey(name);
    }

    public final boolean containsFeature(String name) {
        Map<String, Object> map = this.mMap;
        return map.containsKey(KEY_FEATURE_ + name);
    }

    public final int getValueTypeForKey(String name) {
        Object value = this.mMap.get(name);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return 1;
        }
        if (value instanceof Long) {
            return 2;
        }
        if (value instanceof Float) {
            return 3;
        }
        if (value instanceof String) {
            return 4;
        }
        if (value instanceof ByteBuffer) {
            return 5;
        }
        throw new RuntimeException("invalid value for key");
    }

    public final Number getNumber(String name) {
        return (Number) this.mMap.get(name);
    }

    public final Number getNumber(String name, Number defaultValue) {
        Number ret = getNumber(name);
        return ret == null ? defaultValue : ret;
    }

    public final int getInteger(String name) {
        return ((Integer) this.mMap.get(name)).intValue();
    }

    public final int getInteger(String name, int defaultValue) {
        try {
            return getInteger(name);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    public final long getLong(String name) {
        return ((Long) this.mMap.get(name)).longValue();
    }

    public final long getLong(String name, long defaultValue) {
        try {
            return getLong(name);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    public final float getFloat(String name) {
        return ((Float) this.mMap.get(name)).floatValue();
    }

    public final float getFloat(String name, float defaultValue) {
        try {
            return getFloat(name);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    public final String getString(String name) {
        return (String) this.mMap.get(name);
    }

    public final String getString(String name, String defaultValue) {
        String ret = getString(name);
        return ret == null ? defaultValue : ret;
    }

    public final ByteBuffer getByteBuffer(String name) {
        return (ByteBuffer) this.mMap.get(name);
    }

    public final ByteBuffer getByteBuffer(String name, ByteBuffer defaultValue) {
        ByteBuffer ret = getByteBuffer(name);
        return ret == null ? defaultValue : ret;
    }

    public boolean getFeatureEnabled(String feature) {
        Map<String, Object> map = this.mMap;
        Integer enabled = (Integer) map.get(KEY_FEATURE_ + feature);
        if (enabled != null) {
            return enabled.intValue() != 0;
        }
        throw new IllegalArgumentException("feature is not specified");
    }

    public final void setInteger(String name, int value) {
        this.mMap.put(name, Integer.valueOf(value));
    }

    public final void setLong(String name, long value) {
        this.mMap.put(name, Long.valueOf(value));
    }

    public final void setFloat(String name, float value) {
        this.mMap.put(name, new Float(value));
    }

    public final void setString(String name, String value) {
        this.mMap.put(name, value);
    }

    public final void setByteBuffer(String name, ByteBuffer bytes) {
        this.mMap.put(name, bytes);
    }

    public final void removeKey(String name) {
        if (!name.startsWith(KEY_FEATURE_)) {
            this.mMap.remove(name);
        }
    }

    public final void removeFeature(String name) {
        Map<String, Object> map = this.mMap;
        map.remove(KEY_FEATURE_ + name);
    }

    private abstract class FilteredMappedKeySet extends AbstractSet<String> {
        /* access modifiers changed from: private */
        public Set<String> mKeys;

        /* access modifiers changed from: protected */
        public abstract boolean keepKey(String str);

        /* access modifiers changed from: protected */
        public abstract String mapItemToKey(String str);

        /* access modifiers changed from: protected */
        public abstract String mapKeyToItem(String str);

        public FilteredMappedKeySet() {
            this.mKeys = MediaFormat.this.mMap.keySet();
        }

        public boolean contains(Object o) {
            if (!(o instanceof String)) {
                return false;
            }
            String key = mapItemToKey((String) o);
            if (!keepKey(key) || !this.mKeys.contains(key)) {
                return false;
            }
            return true;
        }

        public boolean remove(Object o) {
            if (!(o instanceof String)) {
                return false;
            }
            String key = mapItemToKey((String) o);
            if (!keepKey(key) || !this.mKeys.remove(key)) {
                return false;
            }
            MediaFormat.this.mMap.remove(key);
            return true;
        }

        private class KeyIterator implements Iterator<String> {
            Iterator<String> mIterator;
            String mLast;

            public KeyIterator() {
                this.mIterator = ((List) FilteredMappedKeySet.this.mKeys.stream().filter(
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0025: IPUT  
                      (wrap: java.util.Iterator<java.lang.String> : 0x0021: INVOKE  (r2v6 java.util.Iterator<java.lang.String>) = 
                      (wrap: java.util.List : 0x001f: CHECK_CAST  (r2v5 java.util.List) = (java.util.List) (wrap: java.lang.Object : 0x001b: INVOKE  (r2v4 java.lang.Object) = 
                      (wrap: java.util.stream.Stream : 0x0013: INVOKE  (r2v3 java.util.stream.Stream) = 
                      (wrap: java.util.stream.Stream : 0x000a: INVOKE  (r2v2 java.util.stream.Stream) = 
                      (wrap: java.util.Set : 0x0006: INVOKE  (r2v1 java.util.Set) = 
                      (wrap: ? : ?: IGET  
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.this$1 android.media.MediaFormat$FilteredMappedKeySet)
                     android.media.MediaFormat.FilteredMappedKeySet.access$100(android.media.MediaFormat$FilteredMappedKeySet):java.util.Set type: STATIC)
                     java.util.Set.stream():java.util.stream.Stream type: INTERFACE)
                      (wrap: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc : 0x0010: CONSTRUCTOR  (r0v0 android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc) = 
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     call: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc.<init>(android.media.MediaFormat$FilteredMappedKeySet$KeyIterator):void type: CONSTRUCTOR)
                     java.util.stream.Stream.filter(java.util.function.Predicate):java.util.stream.Stream type: INTERFACE)
                      (wrap: java.util.stream.Collector : 0x0017: INVOKE  (r0v1 java.util.stream.Collector) =  java.util.stream.Collectors.toList():java.util.stream.Collector type: STATIC)
                     java.util.stream.Stream.collect(java.util.stream.Collector):java.lang.Object type: INTERFACE))
                     java.util.List.iterator():java.util.Iterator type: INTERFACE)
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.mIterator java.util.Iterator in method: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void, dex: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.util.ArrayList.forEach(ArrayList.java:1257)
                    	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                    	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                    	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                    	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.util.ArrayList.forEach(ArrayList.java:1257)
                    	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                    	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                    	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                    	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.util.ArrayList.forEach(ArrayList.java:1257)
                    	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                    	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                    	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                    	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                    	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0021: INVOKE  (r2v6 java.util.Iterator<java.lang.String>) = 
                      (wrap: java.util.List : 0x001f: CHECK_CAST  (r2v5 java.util.List) = (java.util.List) (wrap: java.lang.Object : 0x001b: INVOKE  (r2v4 java.lang.Object) = 
                      (wrap: java.util.stream.Stream : 0x0013: INVOKE  (r2v3 java.util.stream.Stream) = 
                      (wrap: java.util.stream.Stream : 0x000a: INVOKE  (r2v2 java.util.stream.Stream) = 
                      (wrap: java.util.Set : 0x0006: INVOKE  (r2v1 java.util.Set) = 
                      (wrap: ? : ?: IGET  
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.this$1 android.media.MediaFormat$FilteredMappedKeySet)
                     android.media.MediaFormat.FilteredMappedKeySet.access$100(android.media.MediaFormat$FilteredMappedKeySet):java.util.Set type: STATIC)
                     java.util.Set.stream():java.util.stream.Stream type: INTERFACE)
                      (wrap: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc : 0x0010: CONSTRUCTOR  (r0v0 android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc) = 
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     call: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc.<init>(android.media.MediaFormat$FilteredMappedKeySet$KeyIterator):void type: CONSTRUCTOR)
                     java.util.stream.Stream.filter(java.util.function.Predicate):java.util.stream.Stream type: INTERFACE)
                      (wrap: java.util.stream.Collector : 0x0017: INVOKE  (r0v1 java.util.stream.Collector) =  java.util.stream.Collectors.toList():java.util.stream.Collector type: STATIC)
                     java.util.stream.Stream.collect(java.util.stream.Collector):java.lang.Object type: INTERFACE))
                     java.util.List.iterator():java.util.Iterator type: INTERFACE in method: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void, dex: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:429)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	... 59 more
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x001f: CHECK_CAST  (r2v5 java.util.List) = (java.util.List) (wrap: java.lang.Object : 0x001b: INVOKE  (r2v4 java.lang.Object) = 
                      (wrap: java.util.stream.Stream : 0x0013: INVOKE  (r2v3 java.util.stream.Stream) = 
                      (wrap: java.util.stream.Stream : 0x000a: INVOKE  (r2v2 java.util.stream.Stream) = 
                      (wrap: java.util.Set : 0x0006: INVOKE  (r2v1 java.util.Set) = 
                      (wrap: ? : ?: IGET  
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.this$1 android.media.MediaFormat$FilteredMappedKeySet)
                     android.media.MediaFormat.FilteredMappedKeySet.access$100(android.media.MediaFormat$FilteredMappedKeySet):java.util.Set type: STATIC)
                     java.util.Set.stream():java.util.stream.Stream type: INTERFACE)
                      (wrap: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc : 0x0010: CONSTRUCTOR  (r0v0 android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc) = 
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     call: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc.<init>(android.media.MediaFormat$FilteredMappedKeySet$KeyIterator):void type: CONSTRUCTOR)
                     java.util.stream.Stream.filter(java.util.function.Predicate):java.util.stream.Stream type: INTERFACE)
                      (wrap: java.util.stream.Collector : 0x0017: INVOKE  (r0v1 java.util.stream.Collector) =  java.util.stream.Collectors.toList():java.util.stream.Collector type: STATIC)
                     java.util.stream.Stream.collect(java.util.stream.Collector):java.lang.Object type: INTERFACE) in method: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void, dex: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:91)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:697)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 63 more
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x001b: INVOKE  (r2v4 java.lang.Object) = 
                      (wrap: java.util.stream.Stream : 0x0013: INVOKE  (r2v3 java.util.stream.Stream) = 
                      (wrap: java.util.stream.Stream : 0x000a: INVOKE  (r2v2 java.util.stream.Stream) = 
                      (wrap: java.util.Set : 0x0006: INVOKE  (r2v1 java.util.Set) = 
                      (wrap: ? : ?: IGET  
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.this$1 android.media.MediaFormat$FilteredMappedKeySet)
                     android.media.MediaFormat.FilteredMappedKeySet.access$100(android.media.MediaFormat$FilteredMappedKeySet):java.util.Set type: STATIC)
                     java.util.Set.stream():java.util.stream.Stream type: INTERFACE)
                      (wrap: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc : 0x0010: CONSTRUCTOR  (r0v0 android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc) = 
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     call: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc.<init>(android.media.MediaFormat$FilteredMappedKeySet$KeyIterator):void type: CONSTRUCTOR)
                     java.util.stream.Stream.filter(java.util.function.Predicate):java.util.stream.Stream type: INTERFACE)
                      (wrap: java.util.stream.Collector : 0x0017: INVOKE  (r0v1 java.util.stream.Collector) =  java.util.stream.Collectors.toList():java.util.stream.Collector type: STATIC)
                     java.util.stream.Stream.collect(java.util.stream.Collector):java.lang.Object type: INTERFACE in method: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void, dex: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:291)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 69 more
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0013: INVOKE  (r2v3 java.util.stream.Stream) = 
                      (wrap: java.util.stream.Stream : 0x000a: INVOKE  (r2v2 java.util.stream.Stream) = 
                      (wrap: java.util.Set : 0x0006: INVOKE  (r2v1 java.util.Set) = 
                      (wrap: ? : ?: IGET  
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.this$1 android.media.MediaFormat$FilteredMappedKeySet)
                     android.media.MediaFormat.FilteredMappedKeySet.access$100(android.media.MediaFormat$FilteredMappedKeySet):java.util.Set type: STATIC)
                     java.util.Set.stream():java.util.stream.Stream type: INTERFACE)
                      (wrap: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc : 0x0010: CONSTRUCTOR  (r0v0 android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc) = 
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     call: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc.<init>(android.media.MediaFormat$FilteredMappedKeySet$KeyIterator):void type: CONSTRUCTOR)
                     java.util.stream.Stream.filter(java.util.function.Predicate):java.util.stream.Stream type: INTERFACE in method: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void, dex: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:91)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:697)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 73 more
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0010: CONSTRUCTOR  (r0v0 android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc) = 
                      (r1v0 'this' android.media.MediaFormat$FilteredMappedKeySet$KeyIterator A[THIS])
                     call: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc.<init>(android.media.MediaFormat$FilteredMappedKeySet$KeyIterator):void type: CONSTRUCTOR in method: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void, dex: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 79 more
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 85 more
                    */
                /*
                    this = this;
                    android.media.MediaFormat.FilteredMappedKeySet.this = r2
                    r1.<init>()
                    java.util.Set r2 = r2.mKeys
                    java.util.stream.Stream r2 = r2.stream()
                    android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc r0 = new android.media.-$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv-csQxBIPlfc
                    r0.<init>(r1)
                    java.util.stream.Stream r2 = r2.filter(r0)
                    java.util.stream.Collector r0 = java.util.stream.Collectors.toList()
                    java.lang.Object r2 = r2.collect(r0)
                    java.util.List r2 = (java.util.List) r2
                    java.util.Iterator r2 = r2.iterator()
                    r1.mIterator = r2
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.media.MediaFormat.FilteredMappedKeySet.KeyIterator.<init>(android.media.MediaFormat$FilteredMappedKeySet):void");
            }

            public boolean hasNext() {
                return this.mIterator.hasNext();
            }

            public String next() {
                this.mLast = this.mIterator.next();
                return FilteredMappedKeySet.this.mapKeyToItem(this.mLast);
            }

            public void remove() {
                this.mIterator.remove();
                MediaFormat.this.mMap.remove(this.mLast);
            }
        }

        public Iterator<String> iterator() {
            return new KeyIterator();
        }

        public int size() {
            return (int) this.mKeys.stream().filter(new Predicate() {
                public final boolean test(Object obj) {
                    return MediaFormat.FilteredMappedKeySet.this.keepKey((String) obj);
                }
            }).count();
        }
    }

    private class UnprefixedKeySet extends FilteredMappedKeySet {
        private String mPrefix;

        public UnprefixedKeySet(String prefix) {
            super();
            this.mPrefix = prefix;
        }

        /* access modifiers changed from: protected */
        public boolean keepKey(String key) {
            return !key.startsWith(this.mPrefix);
        }

        /* access modifiers changed from: protected */
        public String mapKeyToItem(String key) {
            return key;
        }

        /* access modifiers changed from: protected */
        public String mapItemToKey(String item) {
            return item;
        }
    }

    private class PrefixedKeySetWithPrefixRemoved extends FilteredMappedKeySet {
        private String mPrefix;
        private int mPrefixLength;

        public PrefixedKeySetWithPrefixRemoved(String prefix) {
            super();
            this.mPrefix = prefix;
            this.mPrefixLength = prefix.length();
        }

        /* access modifiers changed from: protected */
        public boolean keepKey(String key) {
            return key.startsWith(this.mPrefix);
        }

        /* access modifiers changed from: protected */
        public String mapKeyToItem(String key) {
            return key.substring(this.mPrefixLength);
        }

        /* access modifiers changed from: protected */
        public String mapItemToKey(String item) {
            return this.mPrefix + item;
        }
    }

    public final Set<String> getKeys() {
        return new UnprefixedKeySet(KEY_FEATURE_);
    }

    public final Set<String> getFeatures() {
        return new PrefixedKeySetWithPrefixRemoved(KEY_FEATURE_);
    }

    public MediaFormat(MediaFormat other) {
        this();
        this.mMap.putAll(other.mMap);
    }

    public void setFeatureEnabled(String feature, boolean enabled) {
        setInteger(KEY_FEATURE_ + feature, enabled);
    }

    public static final MediaFormat createAudioFormat(String mime, int sampleRate, int channelCount) {
        MediaFormat format = new MediaFormat();
        format.setString(KEY_MIME, mime);
        format.setInteger(KEY_SAMPLE_RATE, sampleRate);
        format.setInteger(KEY_CHANNEL_COUNT, channelCount);
        return format;
    }

    public static final MediaFormat createSubtitleFormat(String mime, String language) {
        MediaFormat format = new MediaFormat();
        format.setString(KEY_MIME, mime);
        format.setString("language", language);
        return format;
    }

    public static final MediaFormat createVideoFormat(String mime, int width, int height) {
        MediaFormat format = new MediaFormat();
        format.setString(KEY_MIME, mime);
        format.setInteger("width", width);
        format.setInteger("height", height);
        return format;
    }

    public String toString() {
        return this.mMap.toString();
    }
}
