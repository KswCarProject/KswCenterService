package android.text;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.icu.lang.UCharacter;
import android.icu.text.CaseMap;
import android.icu.text.Edits;
import android.icu.util.ULocale;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Parcel;
import android.os.Parcelable;
import android.sysprop.DisplayProperties;
import android.telecom.Logging.Session;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.LineHeightSpan;
import android.text.style.LocaleSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.SpellCheckSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TtsSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.util.Printer;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.ibm.icu.text.PluralRules;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class TextUtils {
    public static final int ABSOLUTE_SIZE_SPAN = 16;
    public static final int ACCESSIBILITY_CLICKABLE_SPAN = 25;
    public static final int ACCESSIBILITY_URL_SPAN = 26;
    public static final int ALIGNMENT_SPAN = 1;
    public static final int ANNOTATION = 18;
    public static final int BACKGROUND_COLOR_SPAN = 12;
    public static final int BULLET_SPAN = 8;
    public static final int CAP_MODE_CHARACTERS = 4096;
    public static final int CAP_MODE_SENTENCES = 16384;
    public static final int CAP_MODE_WORDS = 8192;
    public static final Parcelable.Creator<CharSequence> CHAR_SEQUENCE_CREATOR = new Parcelable.Creator<CharSequence>() {
        public CharSequence createFromParcel(Parcel p) {
            int kind = p.readInt();
            String string = p.readString();
            if (string == null) {
                return null;
            }
            if (kind == 1) {
                return string;
            }
            SpannableString sp = new SpannableString(string);
            while (true) {
                int kind2 = p.readInt();
                if (kind2 == 0) {
                    return sp;
                }
                switch (kind2) {
                    case 1:
                        TextUtils.readSpan(p, sp, new AlignmentSpan.Standard(p));
                        break;
                    case 2:
                        TextUtils.readSpan(p, sp, new ForegroundColorSpan(p));
                        break;
                    case 3:
                        TextUtils.readSpan(p, sp, new RelativeSizeSpan(p));
                        break;
                    case 4:
                        TextUtils.readSpan(p, sp, new ScaleXSpan(p));
                        break;
                    case 5:
                        TextUtils.readSpan(p, sp, new StrikethroughSpan(p));
                        break;
                    case 6:
                        TextUtils.readSpan(p, sp, new UnderlineSpan(p));
                        break;
                    case 7:
                        TextUtils.readSpan(p, sp, new StyleSpan(p));
                        break;
                    case 8:
                        TextUtils.readSpan(p, sp, new BulletSpan(p));
                        break;
                    case 9:
                        TextUtils.readSpan(p, sp, new QuoteSpan(p));
                        break;
                    case 10:
                        TextUtils.readSpan(p, sp, new LeadingMarginSpan.Standard(p));
                        break;
                    case 11:
                        TextUtils.readSpan(p, sp, new URLSpan(p));
                        break;
                    case 12:
                        TextUtils.readSpan(p, sp, new BackgroundColorSpan(p));
                        break;
                    case 13:
                        TextUtils.readSpan(p, sp, new TypefaceSpan(p));
                        break;
                    case 14:
                        TextUtils.readSpan(p, sp, new SuperscriptSpan(p));
                        break;
                    case 15:
                        TextUtils.readSpan(p, sp, new SubscriptSpan(p));
                        break;
                    case 16:
                        TextUtils.readSpan(p, sp, new AbsoluteSizeSpan(p));
                        break;
                    case 17:
                        TextUtils.readSpan(p, sp, new TextAppearanceSpan(p));
                        break;
                    case 18:
                        TextUtils.readSpan(p, sp, new Annotation(p));
                        break;
                    case 19:
                        TextUtils.readSpan(p, sp, new SuggestionSpan(p));
                        break;
                    case 20:
                        TextUtils.readSpan(p, sp, new SpellCheckSpan(p));
                        break;
                    case 21:
                        TextUtils.readSpan(p, sp, new SuggestionRangeSpan(p));
                        break;
                    case 22:
                        TextUtils.readSpan(p, sp, new EasyEditSpan(p));
                        break;
                    case 23:
                        TextUtils.readSpan(p, sp, new LocaleSpan(p));
                        break;
                    case 24:
                        TextUtils.readSpan(p, sp, new TtsSpan(p));
                        break;
                    case 25:
                        TextUtils.readSpan(p, sp, new AccessibilityClickableSpan(p));
                        break;
                    case 26:
                        TextUtils.readSpan(p, sp, new AccessibilityURLSpan(p));
                        break;
                    case 27:
                        TextUtils.readSpan(p, sp, new LineBackgroundSpan.Standard(p));
                        break;
                    case 28:
                        TextUtils.readSpan(p, sp, new LineHeightSpan.Standard(p));
                        break;
                    default:
                        throw new RuntimeException("bogus span encoding " + kind2);
                }
            }
        }

        public CharSequence[] newArray(int size) {
            return new CharSequence[size];
        }
    };
    public static final int EASY_EDIT_SPAN = 22;
    static final char ELLIPSIS_FILLER = '﻿';
    private static final String ELLIPSIS_NORMAL = "…";
    private static final String ELLIPSIS_TWO_DOTS = "‥";
    private static String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int FIRST_SPAN = 1;
    public static final int FOREGROUND_COLOR_SPAN = 2;
    public static final int LAST_SPAN = 28;
    public static final int LEADING_MARGIN_SPAN = 10;
    public static final int LINE_BACKGROUND_SPAN = 27;
    private static final int LINE_FEED_CODE_POINT = 10;
    public static final int LINE_HEIGHT_SPAN = 28;
    public static final int LOCALE_SPAN = 23;
    private static final int NBSP_CODE_POINT = 160;
    private static final int PARCEL_SAFE_TEXT_LENGTH = 100000;
    public static final int QUOTE_SPAN = 9;
    public static final int RELATIVE_SIZE_SPAN = 3;
    public static final int SAFE_STRING_FLAG_FIRST_LINE = 4;
    public static final int SAFE_STRING_FLAG_SINGLE_LINE = 2;
    public static final int SAFE_STRING_FLAG_TRIM = 1;
    public static final int SCALE_X_SPAN = 4;
    public static final int SPELL_CHECK_SPAN = 20;
    public static final int STRIKETHROUGH_SPAN = 5;
    public static final int STYLE_SPAN = 7;
    public static final int SUBSCRIPT_SPAN = 15;
    public static final int SUGGESTION_RANGE_SPAN = 21;
    public static final int SUGGESTION_SPAN = 19;
    public static final int SUPERSCRIPT_SPAN = 14;
    private static final String TAG = "TextUtils";
    public static final int TEXT_APPEARANCE_SPAN = 17;
    public static final int TTS_SPAN = 24;
    public static final int TYPEFACE_SPAN = 13;
    public static final int UNDERLINE_SPAN = 6;
    public static final int URL_SPAN = 11;
    private static Object sLock = new Object();
    private static char[] sTemp = null;

    public interface EllipsizeCallback {
        void ellipsized(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SafeStringFlags {
    }

    public interface StringSplitter extends Iterable<String> {
        void setString(String str);
    }

    public enum TruncateAt {
        START,
        MIDDLE,
        END,
        MARQUEE,
        END_SMALL
    }

    public static String getEllipsisString(TruncateAt method) {
        return method == TruncateAt.END_SMALL ? ELLIPSIS_TWO_DOTS : ELLIPSIS_NORMAL;
    }

    private TextUtils() {
    }

    public static void getChars(CharSequence s, int start, int end, char[] dest, int destoff) {
        Class<?> cls = s.getClass();
        if (cls == String.class) {
            ((String) s).getChars(start, end, dest, destoff);
        } else if (cls == StringBuffer.class) {
            ((StringBuffer) s).getChars(start, end, dest, destoff);
        } else if (cls == StringBuilder.class) {
            ((StringBuilder) s).getChars(start, end, dest, destoff);
        } else if (s instanceof GetChars) {
            ((GetChars) s).getChars(start, end, dest, destoff);
        } else {
            int destoff2 = destoff;
            int i = start;
            while (i < end) {
                dest[destoff2] = s.charAt(i);
                i++;
                destoff2++;
            }
            int i2 = destoff2;
        }
    }

    public static int indexOf(CharSequence s, char ch) {
        return indexOf(s, ch, 0);
    }

    public static int indexOf(CharSequence s, char ch, int start) {
        if (s.getClass() == String.class) {
            return ((String) s).indexOf(ch, start);
        }
        return indexOf(s, ch, start, s.length());
    }

    public static int indexOf(CharSequence s, char ch, int start, int end) {
        Class<?> cls = s.getClass();
        if ((s instanceof GetChars) || cls == StringBuffer.class || cls == StringBuilder.class || cls == String.class) {
            char[] temp = obtain(500);
            while (start < end) {
                int segend = start + 500;
                if (segend > end) {
                    segend = end;
                }
                getChars(s, start, segend, temp, 0);
                int count = segend - start;
                for (int i = 0; i < count; i++) {
                    if (temp[i] == ch) {
                        recycle(temp);
                        return i + start;
                    }
                }
                start = segend;
            }
            recycle(temp);
            return -1;
        }
        for (int i2 = start; i2 < end; i2++) {
            if (s.charAt(i2) == ch) {
                return i2;
            }
        }
        return -1;
    }

    public static int lastIndexOf(CharSequence s, char ch) {
        return lastIndexOf(s, ch, s.length() - 1);
    }

    public static int lastIndexOf(CharSequence s, char ch, int last) {
        if (s.getClass() == String.class) {
            return ((String) s).lastIndexOf(ch, last);
        }
        return lastIndexOf(s, ch, 0, last);
    }

    public static int lastIndexOf(CharSequence s, char ch, int start, int last) {
        if (last < 0) {
            return -1;
        }
        if (last >= s.length()) {
            last = s.length() - 1;
        }
        int end = last + 1;
        Class<?> cls = s.getClass();
        if ((s instanceof GetChars) || cls == StringBuffer.class || cls == StringBuilder.class || cls == String.class) {
            char[] temp = obtain(500);
            while (start < end) {
                int segstart = end - 500;
                if (segstart < start) {
                    segstart = start;
                }
                getChars(s, segstart, end, temp, 0);
                for (int i = (end - segstart) - 1; i >= 0; i--) {
                    if (temp[i] == ch) {
                        recycle(temp);
                        return i + segstart;
                    }
                }
                end = segstart;
            }
            recycle(temp);
            return -1;
        }
        for (int i2 = end - 1; i2 >= start; i2--) {
            if (s.charAt(i2) == ch) {
                return i2;
            }
        }
        return -1;
    }

    public static int indexOf(CharSequence s, CharSequence needle) {
        return indexOf(s, needle, 0, s.length());
    }

    public static int indexOf(CharSequence s, CharSequence needle, int start) {
        return indexOf(s, needle, start, s.length());
    }

    public static int indexOf(CharSequence s, CharSequence needle, int start, int end) {
        int nlen = needle.length();
        if (nlen == 0) {
            return start;
        }
        char c = needle.charAt(0);
        while (true) {
            int start2 = indexOf(s, c, start);
            if (start2 > end - nlen || start2 < 0) {
                return -1;
            }
            if (regionMatches(s, start2, needle, 0, nlen)) {
                return start2;
            }
            start = start2 + 1;
        }
    }

    public static boolean regionMatches(CharSequence one, int toffset, CharSequence two, int ooffset, int len) {
        int tempLen = len * 2;
        if (tempLen >= len) {
            char[] temp = obtain(tempLen);
            int i = 0;
            getChars(one, toffset, toffset + len, temp, 0);
            getChars(two, ooffset, ooffset + len, temp, len);
            boolean match = true;
            while (true) {
                if (i >= len) {
                    break;
                } else if (temp[i] != temp[i + len]) {
                    match = false;
                    break;
                } else {
                    i++;
                }
            }
            recycle(temp);
            return match;
        }
        throw new IndexOutOfBoundsException();
    }

    public static String substring(CharSequence source, int start, int end) {
        if (source instanceof String) {
            return ((String) source).substring(start, end);
        }
        if (source instanceof StringBuilder) {
            return ((StringBuilder) source).substring(start, end);
        }
        if (source instanceof StringBuffer) {
            return ((StringBuffer) source).substring(start, end);
        }
        char[] temp = obtain(end - start);
        getChars(source, start, end, temp, 0);
        String ret = new String(temp, 0, end - start);
        recycle(temp);
        return ret;
    }

    public static String join(CharSequence delimiter, Object[] tokens) {
        int length = tokens.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(tokens[0]);
        for (int i = 1; i < length; i++) {
            sb.append(delimiter);
            sb.append(tokens[i]);
        }
        return sb.toString();
    }

    public static String join(CharSequence delimiter, Iterable tokens) {
        Iterator<?> it = tokens.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static String[] split(String text, String expression) {
        if (text.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return text.split(expression, -1);
    }

    public static String[] split(String text, Pattern pattern) {
        if (text.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return pattern.split(text, -1);
    }

    public static class SimpleStringSplitter implements StringSplitter, Iterator<String> {
        private char mDelimiter;
        private int mLength;
        private int mPosition;
        private String mString;

        public SimpleStringSplitter(char delimiter) {
            this.mDelimiter = delimiter;
        }

        public void setString(String string) {
            this.mString = string;
            this.mPosition = 0;
            this.mLength = this.mString.length();
        }

        public Iterator<String> iterator() {
            return this;
        }

        public boolean hasNext() {
            return this.mPosition < this.mLength;
        }

        public String next() {
            int end = this.mString.indexOf(this.mDelimiter, this.mPosition);
            if (end == -1) {
                end = this.mLength;
            }
            String nextString = this.mString.substring(this.mPosition, end);
            this.mPosition = end + 1;
            return nextString;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static CharSequence stringOrSpannedString(CharSequence source) {
        if (source == null) {
            return null;
        }
        if (source instanceof SpannedString) {
            return source;
        }
        if (source instanceof Spanned) {
            return new SpannedString(source);
        }
        return source.toString();
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static String nullIfEmpty(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static String firstNotEmpty(String a, String b) {
        return !isEmpty(a) ? a : (String) Preconditions.checkStringNotEmpty(b);
    }

    public static int length(String s) {
        if (s != null) {
            return s.length();
        }
        return 0;
    }

    public static String safeIntern(String s) {
        if (s != null) {
            return s.intern();
        }
        return null;
    }

    public static int getTrimmedLength(CharSequence s) {
        int len = s.length();
        int start = 0;
        while (start < len && s.charAt(start) <= ' ') {
            start++;
        }
        int end = len;
        while (end > start && s.charAt(end - 1) <= ' ') {
            end--;
        }
        return end - start;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        if (!(a == null || b == null)) {
            int length = a.length();
            int length2 = length;
            if (length == b.length()) {
                if ((a instanceof String) && (b instanceof String)) {
                    return a.equals(b);
                }
                for (int i = 0; i < length2; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static CharSequence getReverse(CharSequence source, int start, int end) {
        return new Reverser(source, start, end);
    }

    private static class Reverser implements CharSequence, GetChars {
        private int mEnd;
        private CharSequence mSource;
        private int mStart;

        public Reverser(CharSequence source, int start, int end) {
            this.mSource = source;
            this.mStart = start;
            this.mEnd = end;
        }

        public int length() {
            return this.mEnd - this.mStart;
        }

        public CharSequence subSequence(int start, int end) {
            char[] buf = new char[(end - start)];
            getChars(start, end, buf, 0);
            return new String(buf);
        }

        public String toString() {
            return subSequence(0, length()).toString();
        }

        public char charAt(int off) {
            return (char) UCharacter.getMirror(this.mSource.charAt((this.mEnd - 1) - off));
        }

        public void getChars(int start, int end, char[] dest, int destoff) {
            TextUtils.getChars(this.mSource, this.mStart + start, this.mStart + end, dest, destoff);
            AndroidCharacter.mirror(dest, 0, end - start);
            int len = end - start;
            int n = (end - start) / 2;
            for (int i = 0; i < n; i++) {
                char tmp = dest[destoff + i];
                dest[destoff + i] = dest[((destoff + len) - i) - 1];
                dest[((destoff + len) - i) - 1] = tmp;
            }
        }
    }

    public static void writeToParcel(CharSequence cs, Parcel p, int parcelableFlags) {
        if (cs instanceof Spanned) {
            p.writeInt(0);
            p.writeString(cs.toString());
            Spanned sp = (Spanned) cs;
            Object[] os = sp.getSpans(0, cs.length(), Object.class);
            for (int i = 0; i < os.length; i++) {
                Object o = os[i];
                Object prop = os[i];
                if (prop instanceof CharacterStyle) {
                    prop = ((CharacterStyle) prop).getUnderlying();
                }
                if (prop instanceof ParcelableSpan) {
                    ParcelableSpan ps = (ParcelableSpan) prop;
                    int spanTypeId = ps.getSpanTypeIdInternal();
                    if (spanTypeId < 1 || spanTypeId > 28) {
                        Log.e(TAG, "External class \"" + ps.getClass().getSimpleName() + "\" is attempting to use the frameworks-only ParcelableSpan interface");
                    } else {
                        p.writeInt(spanTypeId);
                        ps.writeToParcelInternal(p, parcelableFlags);
                        writeWhere(p, sp, o);
                    }
                }
            }
            p.writeInt(0);
            return;
        }
        p.writeInt(1);
        if (cs != null) {
            p.writeString(cs.toString());
        } else {
            p.writeString((String) null);
        }
    }

    private static void writeWhere(Parcel p, Spanned sp, Object o) {
        p.writeInt(sp.getSpanStart(o));
        p.writeInt(sp.getSpanEnd(o));
        p.writeInt(sp.getSpanFlags(o));
    }

    public static void dumpSpans(CharSequence cs, Printer printer, String prefix) {
        if (cs instanceof Spanned) {
            Spanned sp = (Spanned) cs;
            int i = 0;
            Object[] os = sp.getSpans(0, cs.length(), Object.class);
            while (true) {
                int i2 = i;
                if (i2 < os.length) {
                    Object o = os[i2];
                    printer.println(prefix + cs.subSequence(sp.getSpanStart(o), sp.getSpanEnd(o)) + PluralRules.KEYWORD_RULE_SEPARATOR + Integer.toHexString(System.identityHashCode(o)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + o.getClass().getCanonicalName() + " (" + sp.getSpanStart(o) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + sp.getSpanEnd(o) + ") fl=#" + sp.getSpanFlags(o));
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else {
            printer.println(prefix + cs + ": (no spans)");
        }
    }

    public static CharSequence replace(CharSequence template, String[] sources, CharSequence[] destinations) {
        SpannableStringBuilder tb = new SpannableStringBuilder(template);
        for (int i = 0; i < sources.length; i++) {
            int where = indexOf((CharSequence) tb, (CharSequence) sources[i]);
            if (where >= 0) {
                tb.setSpan(sources[i], where, sources[i].length() + where, 33);
            }
        }
        for (int i2 = 0; i2 < sources.length; i2++) {
            int start = tb.getSpanStart(sources[i2]);
            int end = tb.getSpanEnd(sources[i2]);
            if (start >= 0) {
                tb.replace(start, end, destinations[i2]);
            }
        }
        return tb;
    }

    public static CharSequence expandTemplate(CharSequence template, CharSequence... values) {
        if (values.length <= 9) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(template);
            int i = 0;
            while (i < ssb.length()) {
                try {
                    if (ssb.charAt(i) == '^') {
                        char next = ssb.charAt(i + 1);
                        if (next == '^') {
                            ssb.delete(i + 1, i + 2);
                            i++;
                        } else if (Character.isDigit(next)) {
                            int which = Character.getNumericValue(next) - 1;
                            if (which < 0) {
                                throw new IllegalArgumentException("template requests value ^" + (which + 1));
                            } else if (which < values.length) {
                                ssb.replace(i, i + 2, values[which]);
                                i += values[which].length();
                            } else {
                                throw new IllegalArgumentException("template requests value ^" + (which + 1) + "; only " + values.length + " provided");
                            }
                        }
                    }
                    i++;
                } catch (IndexOutOfBoundsException e) {
                }
            }
            return ssb;
        }
        throw new IllegalArgumentException("max of 9 values are supported");
    }

    public static int getOffsetBefore(CharSequence text, int offset) {
        int offset2;
        if (offset == 0 || offset == 1) {
            return 0;
        }
        char c = text.charAt(offset - 1);
        if (c < 56320 || c > 57343) {
            offset2 = offset - 1;
        } else {
            char c1 = text.charAt(offset - 2);
            if (c1 < 55296 || c1 > 56319) {
                offset2 = offset - 1;
            } else {
                offset2 = offset - 2;
            }
        }
        if (text instanceof Spanned) {
            ReplacementSpan[] spans = (ReplacementSpan[]) ((Spanned) text).getSpans(offset2, offset2, ReplacementSpan.class);
            for (int i = 0; i < spans.length; i++) {
                int start = ((Spanned) text).getSpanStart(spans[i]);
                int end = ((Spanned) text).getSpanEnd(spans[i]);
                if (start < offset2 && end > offset2) {
                    offset2 = start;
                }
            }
        }
        return offset2;
    }

    public static int getOffsetAfter(CharSequence text, int offset) {
        int offset2;
        int len = text.length();
        if (offset == len || offset == len - 1) {
            return len;
        }
        char c = text.charAt(offset);
        if (c < 55296 || c > 56319) {
            offset2 = offset + 1;
        } else {
            char c1 = text.charAt(offset + 1);
            if (c1 < 56320 || c1 > 57343) {
                offset2 = offset + 1;
            } else {
                offset2 = offset + 2;
            }
        }
        if (text instanceof Spanned) {
            ReplacementSpan[] spans = (ReplacementSpan[]) ((Spanned) text).getSpans(offset2, offset2, ReplacementSpan.class);
            for (int i = 0; i < spans.length; i++) {
                int start = ((Spanned) text).getSpanStart(spans[i]);
                int end = ((Spanned) text).getSpanEnd(spans[i]);
                if (start < offset2 && end > offset2) {
                    offset2 = end;
                }
            }
        }
        return offset2;
    }

    /* access modifiers changed from: private */
    public static void readSpan(Parcel p, Spannable sp, Object o) {
        sp.setSpan(o, p.readInt(), p.readInt(), p.readInt());
    }

    public static void copySpansFrom(Spanned source, int start, int end, Class kind, Spannable dest, int destoff) {
        if (kind == null) {
            kind = Object.class;
        }
        Object[] spans = source.getSpans(start, end, kind);
        for (int i = 0; i < spans.length; i++) {
            int st = source.getSpanStart(spans[i]);
            int en = source.getSpanEnd(spans[i]);
            int fl = source.getSpanFlags(spans[i]);
            if (st < start) {
                st = start;
            }
            if (en > end) {
                en = end;
            }
            dest.setSpan(spans[i], (st - start) + destoff, (en - start) + destoff, fl);
        }
    }

    public static CharSequence toUpperCase(Locale locale, CharSequence source, boolean copySpans) {
        int destStart;
        int destEnd;
        Locale locale2 = locale;
        CharSequence charSequence = source;
        Edits edits = new Edits();
        if (!copySpans) {
            return edits.hasChanges() ? (StringBuilder) CaseMap.toUpper().apply(locale2, charSequence, new StringBuilder(), edits) : charSequence;
        }
        SpannableStringBuilder result = (SpannableStringBuilder) CaseMap.toUpper().apply(locale2, charSequence, new SpannableStringBuilder(), edits);
        if (!edits.hasChanges()) {
            return charSequence;
        }
        Edits.Iterator iterator = edits.getFineIterator();
        int sourceLength = source.length();
        Spanned spanned = (Spanned) charSequence;
        int i = 0;
        Object[] spans = spanned.getSpans(0, sourceLength, Object.class);
        int length = spans.length;
        while (i < length) {
            Object span = spans[i];
            int sourceStart = spanned.getSpanStart(span);
            int sourceEnd = spanned.getSpanEnd(span);
            int flags = spanned.getSpanFlags(span);
            if (sourceStart == sourceLength) {
                destStart = result.length();
            } else {
                destStart = toUpperMapToDest(iterator, sourceStart);
            }
            if (sourceEnd == sourceLength) {
                destEnd = result.length();
            } else {
                destEnd = toUpperMapToDest(iterator, sourceEnd);
            }
            result.setSpan(span, destStart, destEnd, flags);
            i++;
            Locale locale3 = locale;
        }
        return result;
    }

    private static int toUpperMapToDest(Edits.Iterator iterator, int sourceIndex) {
        iterator.findSourceIndex(sourceIndex);
        if (sourceIndex == iterator.sourceIndex()) {
            return iterator.destinationIndex();
        }
        if (iterator.hasChange()) {
            return iterator.destinationIndex() + iterator.newLength();
        }
        return iterator.destinationIndex() + (sourceIndex - iterator.sourceIndex());
    }

    public static CharSequence ellipsize(CharSequence text, TextPaint p, float avail, TruncateAt where) {
        return ellipsize(text, p, avail, where, false, (EllipsizeCallback) null);
    }

    public static CharSequence ellipsize(CharSequence text, TextPaint paint, float avail, TruncateAt where, boolean preserveLength, EllipsizeCallback callback) {
        return ellipsize(text, paint, avail, where, preserveLength, callback, TextDirectionHeuristics.FIRSTSTRONG_LTR, getEllipsisString(where));
    }

    /* JADX WARNING: Removed duplicated region for block: B:84:0x0141  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.CharSequence ellipsize(java.lang.CharSequence r24, android.text.TextPaint r25, float r26, android.text.TextUtils.TruncateAt r27, boolean r28, android.text.TextUtils.EllipsizeCallback r29, android.text.TextDirectionHeuristic r30, java.lang.String r31) {
        /*
            r7 = r24
            r9 = r27
            r10 = r29
            r11 = r31
            int r12 = r24.length()
            r0 = 0
            r6 = r0
            r3 = 0
            int r4 = r24.length()     // Catch:{ all -> 0x0139 }
            r1 = r25
            r2 = r24
            r5 = r30
            android.text.MeasuredParagraph r1 = android.text.MeasuredParagraph.buildForMeasurement(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0139 }
            r13 = r1
            float r1 = r13.getWholeWidth()     // Catch:{ all -> 0x0133 }
            r14 = r1
            int r1 = (r14 > r26 ? 1 : (r14 == r26 ? 0 : -1))
            r2 = 0
            if (r1 > 0) goto L_0x0034
            if (r10 == 0) goto L_0x002d
            r10.ellipsized(r2, r2)     // Catch:{ all -> 0x0133 }
        L_0x002d:
            if (r13 == 0) goto L_0x0033
            r13.recycle()
        L_0x0033:
            return r7
        L_0x0034:
            r15 = r25
            float r1 = r15.measureText(r11)     // Catch:{ all -> 0x0131 }
            r16 = r1
            float r1 = r26 - r16
            r3 = 0
            r4 = r12
            r5 = 0
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 >= 0) goto L_0x0049
        L_0x0045:
            r17 = r1
            r8 = r4
            goto L_0x0079
        L_0x0049:
            android.text.TextUtils$TruncateAt r5 = android.text.TextUtils.TruncateAt.START     // Catch:{ all -> 0x012d }
            if (r9 != r5) goto L_0x0054
            int r5 = r13.breakText(r12, r2, r1)     // Catch:{ all -> 0x012d }
            int r4 = r12 - r5
            goto L_0x0045
        L_0x0054:
            android.text.TextUtils$TruncateAt r5 = android.text.TextUtils.TruncateAt.END     // Catch:{ all -> 0x012d }
            r6 = 1
            if (r9 == r5) goto L_0x0073
            android.text.TextUtils$TruncateAt r5 = android.text.TextUtils.TruncateAt.END_SMALL     // Catch:{ all -> 0x012d }
            if (r9 != r5) goto L_0x005e
            goto L_0x0073
        L_0x005e:
            r5 = 1073741824(0x40000000, float:2.0)
            float r5 = r1 / r5
            int r5 = r13.breakText(r12, r2, r5)     // Catch:{ all -> 0x012d }
            int r4 = r12 - r5
            float r5 = r13.measure(r4, r12)     // Catch:{ all -> 0x012d }
            float r1 = r1 - r5
            int r5 = r13.breakText(r4, r6, r1)     // Catch:{ all -> 0x012d }
            r3 = r5
            goto L_0x0045
        L_0x0073:
            int r5 = r13.breakText(r12, r6, r1)     // Catch:{ all -> 0x012d }
            r3 = r5
            goto L_0x0045
        L_0x0079:
            if (r10 == 0) goto L_0x0082
            r10.ellipsized(r3, r8)     // Catch:{ all -> 0x007f }
            goto L_0x0082
        L_0x007f:
            r0 = move-exception
            goto L_0x013f
        L_0x0082:
            char[] r1 = r13.getChars()     // Catch:{ all -> 0x007f }
            r6 = r1
            boolean r1 = r7 instanceof android.text.Spanned     // Catch:{ all -> 0x007f }
            if (r1 == 0) goto L_0x008f
            r0 = r7
            android.text.Spanned r0 = (android.text.Spanned) r0     // Catch:{ all -> 0x007f }
        L_0x008f:
            int r5 = r8 - r3
            int r18 = r12 - r5
            if (r28 == 0) goto L_0x00e9
            if (r18 <= 0) goto L_0x00a9
            int r1 = r31.length()     // Catch:{ all -> 0x007f }
            if (r5 < r1) goto L_0x00a9
            int r1 = r31.length()     // Catch:{ all -> 0x007f }
            r11.getChars(r2, r1, r6, r3)     // Catch:{ all -> 0x007f }
            int r1 = r31.length()     // Catch:{ all -> 0x007f }
            int r3 = r3 + r1
        L_0x00a9:
            r19 = r3
            r1 = r19
        L_0x00ad:
            if (r1 >= r8) goto L_0x00b7
            r3 = 65279(0xfeff, float:9.1475E-41)
            r6[r1] = r3     // Catch:{ all -> 0x007f }
            int r1 = r1 + 1
            goto L_0x00ad
        L_0x00b7:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x007f }
            r1.<init>(r6, r2, r12)     // Catch:{ all -> 0x007f }
            r4 = r1
            if (r0 != 0) goto L_0x00c6
            if (r13 == 0) goto L_0x00c5
            r13.recycle()
        L_0x00c5:
            return r4
        L_0x00c6:
            android.text.SpannableString r1 = new android.text.SpannableString     // Catch:{ all -> 0x007f }
            r1.<init>(r4)     // Catch:{ all -> 0x007f }
            r20 = r1
            r2 = 0
            java.lang.Class<java.lang.Object> r21 = java.lang.Object.class
            r22 = 0
            r1 = r0
            r3 = r12
            r23 = r4
            r4 = r21
            r21 = r5
            r5 = r20
            r9 = r6
            r6 = r22
            copySpansFrom(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x007f }
            if (r13 == 0) goto L_0x00e8
            r13.recycle()
        L_0x00e8:
            return r20
        L_0x00e9:
            r21 = r5
            r9 = r6
            if (r18 != 0) goto L_0x00f6
            java.lang.String r1 = ""
            if (r13 == 0) goto L_0x00f5
            r13.recycle()
        L_0x00f5:
            return r1
        L_0x00f6:
            if (r0 != 0) goto L_0x0118
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x007f }
            int r4 = r31.length()     // Catch:{ all -> 0x007f }
            int r4 = r18 + r4
            r1.<init>(r4)     // Catch:{ all -> 0x007f }
            r1.append(r9, r2, r3)     // Catch:{ all -> 0x007f }
            r1.append(r11)     // Catch:{ all -> 0x007f }
            int r2 = r12 - r8
            r1.append(r9, r8, r2)     // Catch:{ all -> 0x007f }
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x007f }
            if (r13 == 0) goto L_0x0117
            r13.recycle()
        L_0x0117:
            return r2
        L_0x0118:
            android.text.SpannableStringBuilder r1 = new android.text.SpannableStringBuilder     // Catch:{ all -> 0x007f }
            r1.<init>()     // Catch:{ all -> 0x007f }
            r1.append((java.lang.CharSequence) r7, (int) r2, (int) r3)     // Catch:{ all -> 0x007f }
            r1.append((java.lang.CharSequence) r11)     // Catch:{ all -> 0x007f }
            r1.append((java.lang.CharSequence) r7, (int) r8, (int) r12)     // Catch:{ all -> 0x007f }
            if (r13 == 0) goto L_0x012c
            r13.recycle()
        L_0x012c:
            return r1
        L_0x012d:
            r0 = move-exception
            r17 = r1
            goto L_0x013f
        L_0x0131:
            r0 = move-exception
            goto L_0x0136
        L_0x0133:
            r0 = move-exception
            r15 = r25
        L_0x0136:
            r17 = r26
            goto L_0x013f
        L_0x0139:
            r0 = move-exception
            r15 = r25
            r17 = r26
            r13 = r6
        L_0x013f:
            if (r13 == 0) goto L_0x0144
            r13.recycle()
        L_0x0144:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextUtils.ellipsize(java.lang.CharSequence, android.text.TextPaint, float, android.text.TextUtils$TruncateAt, boolean, android.text.TextUtils$EllipsizeCallback, android.text.TextDirectionHeuristic, java.lang.String):java.lang.CharSequence");
    }

    public static CharSequence listEllipsize(Context context, List<CharSequence> elements, String separator, TextPaint paint, float avail, int moreId) {
        int totalLen;
        BidiFormatter bidiFormatter;
        Resources res;
        CharSequence morePiece;
        if (elements == null || (totalLen = elements.size()) == 0) {
            return "";
        }
        if (context == null) {
            res = null;
            bidiFormatter = BidiFormatter.getInstance();
        } else {
            res = context.getResources();
            bidiFormatter = BidiFormatter.getInstance(res.getConfiguration().getLocales().get(0));
        }
        SpannableStringBuilder output = new SpannableStringBuilder();
        int[] endIndexes = new int[totalLen];
        for (int i = 0; i < totalLen; i++) {
            output.append(bidiFormatter.unicodeWrap(elements.get(i)));
            if (i != totalLen - 1) {
                output.append((CharSequence) separator);
            }
            endIndexes[i] = output.length();
        }
        for (int i2 = totalLen - 1; i2 >= 0; i2--) {
            output.delete(endIndexes[i2], output.length());
            int remainingElements = (totalLen - i2) - 1;
            if (remainingElements > 0) {
                if (res == null) {
                    morePiece = ELLIPSIS_NORMAL;
                } else {
                    morePiece = res.getQuantityString(moreId, remainingElements, Integer.valueOf(remainingElements));
                }
                output.append(bidiFormatter.unicodeWrap(morePiece));
            }
            if (paint.measureText((CharSequence) output, 0, output.length()) <= avail) {
                return output;
            }
        }
        return "";
    }

    @Deprecated
    public static CharSequence commaEllipsize(CharSequence text, TextPaint p, float avail, String oneMore, String more) {
        return commaEllipsize(text, p, avail, oneMore, more, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x010f  */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.CharSequence commaEllipsize(java.lang.CharSequence r24, android.text.TextPaint r25, float r26, java.lang.String r27, java.lang.String r28, android.text.TextDirectionHeuristic r29) {
        /*
            r7 = r24
            r8 = 0
            r0 = 0
            r9 = r0
            int r0 = r24.length()     // Catch:{ all -> 0x0103 }
            r3 = 0
            r1 = r25
            r2 = r24
            r4 = r0
            r5 = r29
            r6 = r8
            android.text.MeasuredParagraph r1 = android.text.MeasuredParagraph.buildForMeasurement(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0103 }
            r8 = r1
            float r1 = r8.getWholeWidth()     // Catch:{ all -> 0x0103 }
            int r2 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r2 > 0) goto L_0x002b
            if (r8 == 0) goto L_0x0025
            r8.recycle()
        L_0x0025:
            if (r9 == 0) goto L_0x002a
            r9.recycle()
        L_0x002a:
            return r7
        L_0x002b:
            char[] r2 = r8.getChars()     // Catch:{ all -> 0x0103 }
            r3 = 0
            r5 = r3
            r3 = 0
        L_0x0032:
            r6 = 44
            if (r3 >= r0) goto L_0x003f
            char r10 = r2[r3]     // Catch:{ all -> 0x0103 }
            if (r10 != r6) goto L_0x003c
            int r5 = r5 + 1
        L_0x003c:
            int r3 = r3 + 1
            goto L_0x0032
        L_0x003f:
            int r3 = r5 + 1
            r10 = 0
            java.lang.String r11 = ""
            r12 = 0
            r13 = 0
            android.text.AutoGrowArray$FloatArray r14 = r8.getWidths()     // Catch:{ all -> 0x0103 }
            float[] r14 = r14.getRawArray()     // Catch:{ all -> 0x0103 }
            r21 = r9
            r9 = r3
            r3 = 0
        L_0x0052:
            if (r3 >= r0) goto L_0x00e6
            float r4 = (float) r12
            r15 = r14[r3]     // Catch:{ all -> 0x00e2 }
            float r4 = r4 + r15
            int r12 = (int) r4     // Catch:{ all -> 0x00e2 }
            char r4 = r2[r3]     // Catch:{ all -> 0x00e2 }
            if (r4 != r6) goto L_0x00d2
            int r13 = r13 + 1
            int r9 = r9 + -1
            r4 = 1
            if (r9 != r4) goto L_0x0080
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e2 }
            r4.<init>()     // Catch:{ all -> 0x00e2 }
            java.lang.String r6 = " "
            r4.append(r6)     // Catch:{ all -> 0x00e2 }
            r6 = r27
            r4.append(r6)     // Catch:{ all -> 0x0101 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0101 }
            r22 = r0
            r23 = r1
            r16 = r4
            r1 = r28
            goto L_0x00aa
        L_0x0080:
            r6 = r27
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0101 }
            r4.<init>()     // Catch:{ all -> 0x0101 }
            r22 = r0
            java.lang.String r0 = " "
            r4.append(r0)     // Catch:{ all -> 0x0101 }
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0101 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0101 }
            r16 = 0
            r0[r16] = r15     // Catch:{ all -> 0x0101 }
            r23 = r1
            r1 = r28
            java.lang.String r0 = java.lang.String.format(r1, r0)     // Catch:{ all -> 0x0101 }
            r4.append(r0)     // Catch:{ all -> 0x0101 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0101 }
            r16 = r0
        L_0x00aa:
            r17 = 0
            int r18 = r16.length()     // Catch:{ all -> 0x0101 }
            r15 = r25
            r19 = r29
            r20 = r21
            android.text.MeasuredParagraph r0 = android.text.MeasuredParagraph.buildForMeasurement(r15, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x0101 }
            r4 = r0
            float r0 = r4.getWholeWidth()     // Catch:{ all -> 0x00ce }
            float r1 = (float) r12
            float r1 = r1 + r0
            int r1 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r1 > 0) goto L_0x00cb
            int r1 = r3 + 1
            r0 = r16
            r11 = r0
            r10 = r1
        L_0x00cb:
            r21 = r4
            goto L_0x00d8
        L_0x00ce:
            r0 = move-exception
            r21 = r4
            goto L_0x0108
        L_0x00d2:
            r6 = r27
            r22 = r0
            r23 = r1
        L_0x00d8:
            int r3 = r3 + 1
            r0 = r22
            r1 = r23
            r6 = 44
            goto L_0x0052
        L_0x00e2:
            r0 = move-exception
            r6 = r27
            goto L_0x0108
        L_0x00e6:
            r6 = r27
            r22 = r0
            r23 = r1
            android.text.SpannableStringBuilder r0 = new android.text.SpannableStringBuilder     // Catch:{ all -> 0x0101 }
            r0.<init>(r11)     // Catch:{ all -> 0x0101 }
            r1 = 0
            r0.insert((int) r1, (java.lang.CharSequence) r7, (int) r1, (int) r10)     // Catch:{ all -> 0x0101 }
            if (r8 == 0) goto L_0x00fb
            r8.recycle()
        L_0x00fb:
            if (r21 == 0) goto L_0x0100
            r21.recycle()
        L_0x0100:
            return r0
        L_0x0101:
            r0 = move-exception
            goto L_0x0108
        L_0x0103:
            r0 = move-exception
            r6 = r27
            r21 = r9
        L_0x0108:
            if (r8 == 0) goto L_0x010d
            r8.recycle()
        L_0x010d:
            if (r21 == 0) goto L_0x0112
            r21.recycle()
        L_0x0112:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextUtils.commaEllipsize(java.lang.CharSequence, android.text.TextPaint, float, java.lang.String, java.lang.String, android.text.TextDirectionHeuristic):java.lang.CharSequence");
    }

    static boolean couldAffectRtl(char c) {
        return (1424 <= c && c <= 2303) || c == 8206 || c == 8207 || (8234 <= c && c <= 8238) || ((8294 <= c && c <= 8297) || ((55296 <= c && c <= 57343) || ((64285 <= c && c <= 65023) || (65136 <= c && c <= 65278))));
    }

    static boolean doesNotNeedBidi(char[] text, int start, int len) {
        int end = start + len;
        for (int i = start; i < end; i++) {
            if (couldAffectRtl(text[i])) {
                return false;
            }
        }
        return true;
    }

    static char[] obtain(int len) {
        char[] buf;
        synchronized (sLock) {
            buf = sTemp;
            sTemp = null;
        }
        if (buf == null || buf.length < len) {
            return ArrayUtils.newUnpaddedCharArray(len);
        }
        return buf;
    }

    static void recycle(char[] temp) {
        if (temp.length <= 1000) {
            synchronized (sLock) {
                sTemp = temp;
            }
        }
    }

    public static String htmlEncode(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\"') {
                sb.append("&quot;");
            } else if (c == '<') {
                sb.append("&lt;");
            } else if (c != '>') {
                switch (c) {
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '\'':
                        sb.append("&#39;");
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            } else {
                sb.append("&gt;");
            }
        }
        return sb.toString();
    }

    public static CharSequence concat(CharSequence... text) {
        if (text.length == 0) {
            return "";
        }
        int i = 0;
        if (text.length == 1) {
            return text[0];
        }
        boolean spanned = false;
        int length = text.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            } else if (text[i2] instanceof Spanned) {
                spanned = true;
                break;
            } else {
                i2++;
            }
        }
        if (spanned) {
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            int length2 = text.length;
            while (i < length2) {
                CharSequence piece = text[i];
                ssb.append(piece == null ? "null" : piece);
                i++;
            }
            return new SpannedString(ssb);
        }
        StringBuilder sb = new StringBuilder();
        int length3 = text.length;
        while (i < length3) {
            sb.append(text[i]);
            i++;
        }
        return sb.toString();
    }

    public static boolean isGraphic(CharSequence str) {
        int len = str.length();
        int i = 0;
        while (i < len) {
            int cp = Character.codePointAt(str, i);
            int gc = Character.getType(cp);
            if (gc != 15 && gc != 16 && gc != 19 && gc != 0 && gc != 13 && gc != 14 && gc != 12) {
                return true;
            }
            i += Character.charCount(cp);
        }
        return false;
    }

    @Deprecated
    public static boolean isGraphic(char c) {
        int gc = Character.getType(c);
        return (gc == 15 || gc == 16 || gc == 19 || gc == 0 || gc == 13 || gc == 14 || gc == 12) ? false : true;
    }

    public static boolean isDigitsOnly(CharSequence str) {
        int len = str.length();
        int i = 0;
        while (i < len) {
            int cp = Character.codePointAt(str, i);
            if (!Character.isDigit(cp)) {
                return false;
            }
            i += Character.charCount(cp);
        }
        return true;
    }

    public static boolean isPrintableAscii(char c) {
        return (' ' <= c && c <= '~') || c == 13 || c == 10;
    }

    @UnsupportedAppUsage
    public static boolean isPrintableAsciiOnly(CharSequence str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!isPrintableAscii(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int getCapsMode(CharSequence cs, int off, int reqModes) {
        char c;
        if (off < 0) {
            return 0;
        }
        int mode = 0;
        if ((reqModes & 4096) != 0) {
            mode = 0 | 4096;
        }
        if ((reqModes & 24576) == 0) {
            return mode;
        }
        int i = off;
        while (i > 0 && ((c = cs.charAt(i - 1)) == '\"' || c == '\'' || Character.getType(c) == 21)) {
            i--;
        }
        int j = i;
        while (j > 0) {
            char charAt = cs.charAt(j - 1);
            char c2 = charAt;
            if (charAt != ' ' && c2 != 9) {
                break;
            }
            j--;
        }
        if (j == 0 || cs.charAt(j - 1) == 10) {
            return mode | 8192;
        }
        if ((reqModes & 16384) == 0) {
            if (i != j) {
                return mode | 8192;
            }
            return mode;
        } else if (i == j) {
            return mode;
        } else {
            while (j > 0) {
                char c3 = cs.charAt(j - 1);
                if (c3 != '\"' && c3 != '\'' && Character.getType(c3) != 22) {
                    break;
                }
                j--;
            }
            if (j <= 0 || ((c = cs.charAt(j - 1)) != '.' && c != '?' && c != '!')) {
                return mode;
            }
            if (c == '.') {
                for (int k = j - 2; k >= 0; k--) {
                    char c4 = cs.charAt(k);
                    if (c4 == '.') {
                        return mode;
                    }
                    if (!Character.isLetter(c4)) {
                        break;
                    }
                }
            }
            return mode | 16384;
        }
    }

    public static boolean delimitedStringContains(String delimitedString, char delimiter, String item) {
        int expectedDelimiterPos;
        if (isEmpty(delimitedString) || isEmpty(item)) {
            return false;
        }
        int pos = -1;
        int length = delimitedString.length();
        while (true) {
            int indexOf = delimitedString.indexOf(item, pos + 1);
            pos = indexOf;
            if (indexOf == -1) {
                return false;
            }
            if ((pos <= 0 || delimitedString.charAt(pos - 1) == delimiter) && ((expectedDelimiterPos = item.length() + pos) == length || delimitedString.charAt(expectedDelimiterPos) == delimiter)) {
                return true;
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T[] removeEmptySpans(T[] r8, android.text.Spanned r9, java.lang.Class<T> r10) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = r1
            r1 = r0
            r0 = r2
        L_0x0006:
            int r4 = r8.length
            if (r0 >= r4) goto L_0x002f
            r4 = r8[r0]
            int r5 = r9.getSpanStart(r4)
            int r6 = r9.getSpanEnd(r4)
            if (r5 != r6) goto L_0x0026
            if (r1 != 0) goto L_0x002c
            int r7 = r8.length
            int r7 = r7 + -1
            java.lang.Object r7 = java.lang.reflect.Array.newInstance(r10, r7)
            r1 = r7
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            java.lang.System.arraycopy(r8, r2, r1, r2, r0)
            r3 = r0
            goto L_0x002c
        L_0x0026:
            if (r1 == 0) goto L_0x002c
            r1[r3] = r4
            int r3 = r3 + 1
        L_0x002c:
            int r0 = r0 + 1
            goto L_0x0006
        L_0x002f:
            if (r1 == 0) goto L_0x003b
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r10, r3)
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            java.lang.System.arraycopy(r1, r2, r0, r2, r3)
            return r0
        L_0x003b:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextUtils.removeEmptySpans(java.lang.Object[], android.text.Spanned, java.lang.Class):java.lang.Object[]");
    }

    @UnsupportedAppUsage
    public static long packRangeInLong(int start, int end) {
        return (((long) start) << 32) | ((long) end);
    }

    @UnsupportedAppUsage
    public static int unpackRangeStartFromLong(long range) {
        return (int) (range >>> 32);
    }

    @UnsupportedAppUsage
    public static int unpackRangeEndFromLong(long range) {
        return (int) (4294967295L & range);
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        if ((locale == null || locale.equals(Locale.ROOT) || !ULocale.forLocale(locale).isRightToLeft()) && !DisplayProperties.debug_force_rtl().orElse(false).booleanValue()) {
            return 0;
        }
        return 1;
    }

    public static CharSequence formatSelectedCount(int count) {
        return Resources.getSystem().getQuantityString(R.plurals.selected_count, count, Integer.valueOf(count));
    }

    public static boolean hasStyleSpan(Spanned spanned) {
        Preconditions.checkArgument(spanned != null);
        for (Class nextSpanTransition : new Class[]{CharacterStyle.class, ParagraphStyle.class, UpdateAppearance.class}) {
            if (spanned.nextSpanTransition(-1, spanned.length(), nextSpanTransition) < spanned.length()) {
                return true;
            }
        }
        return false;
    }

    public static CharSequence trimNoCopySpans(CharSequence charSequence) {
        if (charSequence == null || !(charSequence instanceof Spanned)) {
            return charSequence;
        }
        return new SpannableStringBuilder(charSequence);
    }

    public static void wrap(StringBuilder builder, String start, String end) {
        builder.insert(0, start);
        builder.append(end);
    }

    public static <T extends CharSequence> T trimToParcelableSize(T text) {
        return trimToSize(text, 100000);
    }

    public static <T extends CharSequence> T trimToSize(T text, int size) {
        Preconditions.checkArgument(size > 0);
        if (isEmpty(text) || text.length() <= size) {
            return text;
        }
        if (Character.isHighSurrogate(text.charAt(size - 1)) && Character.isLowSurrogate(text.charAt(size))) {
            size--;
        }
        return text.subSequence(0, size);
    }

    public static <T extends CharSequence> T trimToLengthWithEllipsis(T text, int size) {
        T trimmed = trimToSize(text, size);
        if (trimmed.length() >= text.length()) {
            return trimmed;
        }
        return trimmed.toString() + Session.TRUNCATE_STRING;
    }

    private static boolean isNewline(int codePoint) {
        int type = Character.getType(codePoint);
        return type == 14 || type == 13 || codePoint == 10;
    }

    private static boolean isWhiteSpace(int codePoint) {
        return Character.isWhitespace(codePoint) || codePoint == 160;
    }

    public static String withoutPrefix(String prefix, String str) {
        if (prefix == null || str == null) {
            return str;
        }
        return str.startsWith(prefix) ? str.substring(prefix.length()) : str;
    }

    public static CharSequence makeSafeForPresentation(String unclean, int maxCharactersToConsider, float ellipsizeDip, int flags) {
        String shortString;
        int i = maxCharactersToConsider;
        float f = ellipsizeDip;
        int i2 = flags;
        boolean z = true;
        boolean onlyKeepFirstLine = (i2 & 4) != 0;
        boolean forceSingleLine = (i2 & 2) != 0;
        boolean trim = (i2 & 1) != 0;
        Preconditions.checkNotNull(unclean);
        Preconditions.checkArgumentNonnegative(maxCharactersToConsider);
        Preconditions.checkArgumentNonNegative(f, "ellipsizeDip");
        Preconditions.checkFlagsArgument(i2, 7);
        if (onlyKeepFirstLine && forceSingleLine) {
            z = false;
        }
        Preconditions.checkArgument(z, "Cannot set SAFE_STRING_FLAG_SINGLE_LINE and SAFE_STRING_FLAG_FIRST_LINE at thesame time");
        if (i > 0) {
            shortString = unclean.substring(0, Math.min(unclean.length(), i));
        } else {
            shortString = unclean;
        }
        StringWithRemovedChars gettingCleaned = new StringWithRemovedChars(Html.fromHtml(shortString).toString());
        int uncleanLength = gettingCleaned.length();
        int firstTrailingWhiteSpace = -1;
        int firstNonWhiteSpace = -1;
        int offset = 0;
        while (true) {
            if (offset >= uncleanLength) {
                break;
            }
            int codePoint = gettingCleaned.codePointAt(offset);
            int type = Character.getType(codePoint);
            int codePointLen = Character.charCount(codePoint);
            boolean isNewline = isNewline(codePoint);
            if (onlyKeepFirstLine && isNewline) {
                gettingCleaned.removeAllCharAfter(offset);
                break;
            }
            if (forceSingleLine && isNewline) {
                gettingCleaned.removeRange(offset, offset + codePointLen);
            } else if (type == 15 && !isNewline) {
                gettingCleaned.removeRange(offset, offset + codePointLen);
            } else if (trim && !isWhiteSpace(codePoint)) {
                if (firstNonWhiteSpace == -1) {
                    firstNonWhiteSpace = offset;
                }
                firstTrailingWhiteSpace = offset + codePointLen;
            }
            offset += codePointLen;
        }
        if (trim) {
            if (firstNonWhiteSpace == -1) {
                gettingCleaned.removeAllCharAfter(0);
            } else {
                if (firstNonWhiteSpace > 0) {
                    gettingCleaned.removeAllCharBefore(firstNonWhiteSpace);
                }
                if (firstTrailingWhiteSpace < uncleanLength) {
                    gettingCleaned.removeAllCharAfter(firstTrailingWhiteSpace);
                }
            }
        }
        if (f == 0.0f) {
            return gettingCleaned.toString();
        }
        TextPaint paint = new TextPaint();
        paint.setTextSize(42.0f);
        return ellipsize(gettingCleaned.toString(), paint, f, TruncateAt.END);
    }

    private static class StringWithRemovedChars {
        private final String mOriginal;
        private BitSet mRemovedChars;

        StringWithRemovedChars(String original) {
            this.mOriginal = original;
        }

        /* access modifiers changed from: package-private */
        public void removeRange(int firstRemoved, int firstNonRemoved) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(firstRemoved, firstNonRemoved);
        }

        /* access modifiers changed from: package-private */
        public void removeAllCharBefore(int firstNonRemoved) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(0, firstNonRemoved);
        }

        /* access modifiers changed from: package-private */
        public void removeAllCharAfter(int firstRemoved) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(firstRemoved, this.mOriginal.length());
        }

        public String toString() {
            if (this.mRemovedChars == null) {
                return this.mOriginal;
            }
            StringBuilder sb = new StringBuilder(this.mOriginal.length());
            for (int i = 0; i < this.mOriginal.length(); i++) {
                if (!this.mRemovedChars.get(i)) {
                    sb.append(this.mOriginal.charAt(i));
                }
            }
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public int length() {
            return this.mOriginal.length();
        }

        /* access modifiers changed from: package-private */
        public int codePointAt(int offset) {
            return this.mOriginal.codePointAt(offset);
        }
    }
}
