package android.media;

import android.net.wifi.WifiEnterpriseConfig;
import android.provider.BrowserContract;
import android.provider.SettingsStringUtil;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import com.android.internal.app.DumpHeapActivity;
import java.util.Vector;

/* compiled from: WebVttRenderer */
class WebVttParser {
    private static final String TAG = "WebVttParser";
    private String mBuffer = "";
    /* access modifiers changed from: private */
    public TextTrackCue mCue;
    /* access modifiers changed from: private */
    public Vector<String> mCueTexts;
    /* access modifiers changed from: private */
    public WebVttCueListener mListener;
    /* access modifiers changed from: private */
    public final Phase mParseCueId = new Phase() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<WebVttParser> cls = WebVttParser.class;
        }

        public void parse(String line) {
            if (line.length() != 0) {
                if (line.equals("NOTE") || line.startsWith("NOTE ")) {
                    Phase unused = WebVttParser.this.mPhase = WebVttParser.this.mParseCueText;
                }
                TextTrackCue unused2 = WebVttParser.this.mCue = new TextTrackCue();
                WebVttParser.this.mCueTexts.clear();
                Phase unused3 = WebVttParser.this.mPhase = WebVttParser.this.mParseCueTime;
                if (line.contains("-->")) {
                    WebVttParser.this.mPhase.parse(line);
                } else {
                    WebVttParser.this.mCue.mId = line;
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final Phase mParseCueText = new Phase() {
        public void parse(String line) {
            if (line.length() == 0) {
                WebVttParser.this.yieldCue();
                Phase unused = WebVttParser.this.mPhase = WebVttParser.this.mParseCueId;
            } else if (WebVttParser.this.mCue != null) {
                WebVttParser.this.mCueTexts.add(line);
            }
        }
    };
    /* access modifiers changed from: private */
    public final Phase mParseCueTime = new Phase() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<WebVttParser> cls = WebVttParser.class;
        }

        public void parse(String line) {
            String str = line;
            int arrowAt = str.indexOf("-->");
            if (arrowAt < 0) {
                TextTrackCue unused = WebVttParser.this.mCue = null;
                Phase unused2 = WebVttParser.this.mPhase = WebVttParser.this.mParseCueId;
                return;
            }
            int i = 0;
            String start = str.substring(0, arrowAt).trim();
            String rest = str.substring(arrowAt + 3).replaceFirst("^\\s+", "").replaceFirst("\\s+", WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            int spaceAt = rest.indexOf(32);
            String end = spaceAt > 0 ? rest.substring(0, spaceAt) : rest;
            String rest2 = spaceAt > 0 ? rest.substring(spaceAt + 1) : "";
            WebVttParser.this.mCue.mStartTimeMs = WebVttParser.parseTimestampMs(start);
            WebVttParser.this.mCue.mEndTimeMs = WebVttParser.parseTimestampMs(end);
            String[] split = rest2.split(" +");
            int length = split.length;
            int i2 = 0;
            while (i2 < length) {
                String setting = split[i2];
                int colonAt = setting.indexOf(58);
                if (colonAt > 0 && colonAt != setting.length() - 1) {
                    String name = setting.substring(i, colonAt);
                    String value = setting.substring(colonAt + 1);
                    if (name.equals(TtmlUtils.TAG_REGION)) {
                        WebVttParser.this.mCue.mRegionId = value;
                    } else if (name.equals("vertical")) {
                        if (value.equals("rl")) {
                            WebVttParser.this.mCue.mWritingDirection = 101;
                        } else if (value.equals("lr")) {
                            WebVttParser.this.mCue.mWritingDirection = 102;
                        } else {
                            WebVttParser.this.log_warning("cue setting", name, "has invalid value", value);
                        }
                    } else if (name.equals("line")) {
                        try {
                            if (value.endsWith("%")) {
                                WebVttParser.this.mCue.mSnapToLines = false;
                                WebVttParser.this.mCue.mLinePosition = Integer.valueOf(WebVttParser.parseIntPercentage(value));
                            } else if (value.matches(".*[^0-9].*")) {
                                WebVttParser.this.log_warning("cue setting", name, "contains an invalid character", value);
                            } else {
                                WebVttParser.this.mCue.mSnapToLines = true;
                                WebVttParser.this.mCue.mLinePosition = Integer.valueOf(Integer.parseInt(value));
                            }
                        } catch (NumberFormatException e) {
                            NumberFormatException numberFormatException = e;
                            WebVttParser.this.log_warning("cue setting", name, "is not numeric or percentage", value);
                        }
                    } else if (name.equals(BrowserContract.Bookmarks.POSITION)) {
                        try {
                            WebVttParser.this.mCue.mTextPosition = WebVttParser.parseIntPercentage(value);
                        } catch (NumberFormatException e2) {
                            NumberFormatException numberFormatException2 = e2;
                            WebVttParser.this.log_warning("cue setting", name, "is not numeric or percentage", value);
                        }
                    } else if (name.equals(DumpHeapActivity.KEY_SIZE)) {
                        try {
                            WebVttParser.this.mCue.mSize = WebVttParser.parseIntPercentage(value);
                        } catch (NumberFormatException e3) {
                            NumberFormatException numberFormatException3 = e3;
                            WebVttParser.this.log_warning("cue setting", name, "is not numeric or percentage", value);
                        }
                    } else if (name.equals("align")) {
                        if (value.equals(Telephony.BaseMmsColumns.START)) {
                            WebVttParser.this.mCue.mAlignment = 201;
                        } else if (value.equals("middle")) {
                            WebVttParser.this.mCue.mAlignment = 200;
                        } else if (value.equals("end")) {
                            WebVttParser.this.mCue.mAlignment = 202;
                        } else if (value.equals("left")) {
                            WebVttParser.this.mCue.mAlignment = 203;
                        } else if (value.equals("right")) {
                            WebVttParser.this.mCue.mAlignment = 204;
                        } else {
                            WebVttParser.this.log_warning("cue setting", name, "has invalid value", value);
                        }
                    }
                }
                i2++;
                String str2 = line;
                i = 0;
            }
            if (!(WebVttParser.this.mCue.mLinePosition == null && WebVttParser.this.mCue.mSize == 100 && WebVttParser.this.mCue.mWritingDirection == 100)) {
                WebVttParser.this.mCue.mRegionId = "";
            }
            Phase unused3 = WebVttParser.this.mPhase = WebVttParser.this.mParseCueText;
        }
    };
    /* access modifiers changed from: private */
    public final Phase mParseHeader = new Phase() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<WebVttParser> cls = WebVttParser.class;
        }

        /* access modifiers changed from: package-private */
        public TextTrackRegion parseRegion(String s) {
            int i;
            TextTrackRegion region = new TextTrackRegion();
            String[] split = s.split(" +");
            int length = split.length;
            int i2 = 0;
            int i3 = 0;
            while (i3 < length) {
                String setting = split[i3];
                int equalAt = setting.indexOf(61);
                if (equalAt > 0 && equalAt != setting.length() - 1) {
                    String name = setting.substring(i2, equalAt);
                    String value = setting.substring(equalAt + 1);
                    if (name.equals("id")) {
                        region.mId = value;
                    } else if (name.equals("width")) {
                        try {
                            region.mWidth = WebVttParser.parseFloatPercentage(value);
                        } catch (NumberFormatException e) {
                            String str = name;
                            WebVttParser.this.log_warning("region setting", name, "has invalid value", e.getMessage(), value);
                        }
                    } else {
                        String value2 = value;
                        String name2 = name;
                        if (name2.equals("lines")) {
                            String value3 = value2;
                            if (value3.matches(".*[^0-9].*")) {
                                WebVttParser.this.log_warning("lines", name2, "contains an invalid character", value3);
                            } else {
                                try {
                                    region.mLines = Integer.parseInt(value3);
                                } catch (NumberFormatException e2) {
                                    WebVttParser.this.log_warning("region setting", name2, "is not numeric", value3);
                                }
                            }
                        } else {
                            String value4 = value2;
                            if (name2.equals("regionanchor") || name2.equals("viewportanchor")) {
                                int commaAt = value4.indexOf(SmsManager.REGEX_PREFIX_DELIMITER);
                                if (commaAt < 0) {
                                    WebVttParser.this.log_warning("region setting", name2, "contains no comma", value4);
                                } else {
                                    String anchorX = value4.substring(0, commaAt);
                                    String anchorY = value4.substring(commaAt + 1);
                                    try {
                                        float x = WebVttParser.parseFloatPercentage(anchorX);
                                        try {
                                            float y = WebVttParser.parseFloatPercentage(anchorY);
                                            if (name2.charAt(0) == 'r') {
                                                region.mAnchorPointX = x;
                                                region.mAnchorPointY = y;
                                            } else {
                                                region.mViewportAnchorPointX = x;
                                                region.mViewportAnchorPointY = y;
                                            }
                                        } catch (NumberFormatException e3) {
                                            i = 0;
                                            float f = x;
                                            int i4 = commaAt;
                                            String str2 = value4;
                                            WebVttParser.this.log_warning("region setting", name2, "has invalid y component", e3.getMessage(), anchorY);
                                        }
                                    } catch (NumberFormatException e4) {
                                        int i5 = commaAt;
                                        String str3 = value4;
                                        i = 0;
                                        WebVttParser.this.log_warning("region setting", name2, "has invalid x component", e4.getMessage(), anchorX);
                                    }
                                }
                            } else if (name2.equals("scroll")) {
                                if (value4.equals("up")) {
                                    region.mScrollValue = 301;
                                } else {
                                    WebVttParser.this.log_warning("region setting", name2, "has invalid value", value4);
                                }
                            }
                        }
                        i = 0;
                        i3++;
                        i2 = i;
                    }
                }
                i = i2;
                i3++;
                i2 = i;
            }
            return region;
        }

        public void parse(String line) {
            if (line.length() == 0) {
                Phase unused = WebVttParser.this.mPhase = WebVttParser.this.mParseCueId;
            } else if (line.contains("-->")) {
                Phase unused2 = WebVttParser.this.mPhase = WebVttParser.this.mParseCueTime;
                WebVttParser.this.mPhase.parse(line);
            } else {
                int colonAt = line.indexOf(58);
                if (colonAt <= 0 || colonAt >= line.length() - 1) {
                    WebVttParser.this.log_warning("meta data header has invalid format", line);
                }
                String name = line.substring(0, colonAt);
                String value = line.substring(colonAt + 1);
                if (name.equals("Region")) {
                    WebVttParser.this.mListener.onRegionParsed(parseRegion(value));
                }
            }
        }
    };
    private final Phase mParseStart = new Phase() {
        public void parse(String line) {
            if (line.startsWith("﻿")) {
                line = line.substring(1);
            }
            if (line.equals("WEBVTT") || line.startsWith("WEBVTT ") || line.startsWith("WEBVTT\t")) {
                Phase unused = WebVttParser.this.mPhase = WebVttParser.this.mParseHeader;
                return;
            }
            WebVttParser.this.log_warning("Not a WEBVTT header", line);
            Phase unused2 = WebVttParser.this.mPhase = WebVttParser.this.mSkipRest;
        }
    };
    /* access modifiers changed from: private */
    public Phase mPhase = this.mParseStart;
    /* access modifiers changed from: private */
    public final Phase mSkipRest = new Phase() {
        public void parse(String line) {
        }
    };

    /* compiled from: WebVttRenderer */
    interface Phase {
        void parse(String str);
    }

    WebVttParser(WebVttCueListener listener) {
        this.mListener = listener;
        this.mCueTexts = new Vector<>();
    }

    public static float parseFloatPercentage(String s) throws NumberFormatException {
        if (s.endsWith("%")) {
            String s2 = s.substring(0, s.length() - 1);
            if (!s2.matches(".*[^0-9.].*")) {
                try {
                    float value = Float.parseFloat(s2);
                    if (value >= 0.0f && value <= 100.0f) {
                        return value;
                    }
                    throw new NumberFormatException("is out of range");
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("is not a number");
                }
            } else {
                throw new NumberFormatException("contains an invalid character");
            }
        } else {
            throw new NumberFormatException("does not end in %");
        }
    }

    public static int parseIntPercentage(String s) throws NumberFormatException {
        if (s.endsWith("%")) {
            String s2 = s.substring(0, s.length() - 1);
            if (!s2.matches(".*[^0-9].*")) {
                try {
                    int value = Integer.parseInt(s2);
                    if (value >= 0 && value <= 100) {
                        return value;
                    }
                    throw new NumberFormatException("is out of range");
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("is not a number");
                }
            } else {
                throw new NumberFormatException("contains an invalid character");
            }
        } else {
            throw new NumberFormatException("does not end in %");
        }
    }

    public static long parseTimestampMs(String s) throws NumberFormatException {
        if (s.matches("(\\d+:)?[0-5]\\d:[0-5]\\d\\.\\d{3}")) {
            String[] parts = s.split("\\.", 2);
            long value = 0;
            for (String group : parts[0].split(SettingsStringUtil.DELIMITER)) {
                value = (60 * value) + Long.parseLong(group);
            }
            return (1000 * value) + Long.parseLong(parts[1]);
        }
        throw new NumberFormatException("has invalid format");
    }

    public static String timeToString(long timeMs) {
        return String.format("%d:%02d:%02d.%03d", new Object[]{Long.valueOf(timeMs / 3600000), Long.valueOf((timeMs / 60000) % 60), Long.valueOf((timeMs / 1000) % 60), Long.valueOf(timeMs % 1000)});
    }

    public void parse(String s) {
        boolean trailingCR = false;
        this.mBuffer = (this.mBuffer + s.replace("\u0000", "�")).replace("\r\n", "\n");
        if (this.mBuffer.endsWith("\r")) {
            trailingCR = true;
            this.mBuffer = this.mBuffer.substring(0, this.mBuffer.length() - 1);
        }
        String[] lines = this.mBuffer.split("[\r\n]");
        for (int i = 0; i < lines.length - 1; i++) {
            this.mPhase.parse(lines[i]);
        }
        this.mBuffer = lines[lines.length - 1];
        if (trailingCR) {
            this.mBuffer += "\r";
        }
    }

    public void eos() {
        if (this.mBuffer.endsWith("\r")) {
            this.mBuffer = this.mBuffer.substring(0, this.mBuffer.length() - 1);
        }
        this.mPhase.parse(this.mBuffer);
        this.mBuffer = "";
        yieldCue();
        this.mPhase = this.mParseStart;
    }

    public void yieldCue() {
        if (this.mCue != null && this.mCueTexts.size() > 0) {
            this.mCue.mStrings = new String[this.mCueTexts.size()];
            this.mCueTexts.toArray(this.mCue.mStrings);
            this.mCueTexts.clear();
            this.mListener.onCueParsed(this.mCue);
        }
        this.mCue = null;
    }

    /* access modifiers changed from: private */
    public void log_warning(String nameType, String name, String message, String subMessage, String value) {
        String name2 = getClass().getName();
        Log.w(name2, nameType + " '" + name + "' " + message + " ('" + value + "' " + subMessage + ")");
    }

    /* access modifiers changed from: private */
    public void log_warning(String nameType, String name, String message, String value) {
        String name2 = getClass().getName();
        Log.w(name2, nameType + " '" + name + "' " + message + " ('" + value + "')");
    }

    /* access modifiers changed from: private */
    public void log_warning(String message, String value) {
        String name = getClass().getName();
        Log.w(name, message + " ('" + value + "')");
    }
}
