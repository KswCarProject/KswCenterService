package com.ibm.icu.text;

import com.ibm.icu.impl.BMPSet;
import com.ibm.icu.impl.CharacterPropertiesImpl;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.RuleCharacterIterator;
import com.ibm.icu.impl.SortedSetRelation;
import com.ibm.icu.impl.StringRange;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.impl.UPropertyAliases;
import com.ibm.icu.impl.UnicodeSetStringSpan;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.CharSequences;
import com.ibm.icu.lang.CharacterProperties;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.OutputInt;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

/* loaded from: classes5.dex */
public class UnicodeSet extends UnicodeFilter implements Iterable<String>, Comparable<UnicodeSet>, Freezable<UnicodeSet> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ADD_CASE_MAPPINGS = 4;
    private static final String ANY_ID = "ANY";
    private static final String ASCII_ID = "ASCII";
    private static final String ASSIGNED = "Assigned";
    public static final int CASE = 2;
    public static final int CASE_INSENSITIVE = 2;
    private static final int HIGH = 1114112;
    public static final int IGNORE_SPACE = 1;
    private static final int INITIAL_CAPACITY = 25;
    private static final int LAST0_START = 0;
    private static final int LAST1_RANGE = 1;
    private static final int LAST2_SET = 2;
    private static final int LOW = 0;
    private static final int MAX_DEPTH = 100;
    private static final int MAX_LENGTH = 1114113;
    public static final int MAX_VALUE = 1114111;
    public static final int MIN_VALUE = 0;
    private static final int MODE0_NONE = 0;
    private static final int MODE1_INBRACKET = 1;
    private static final int MODE2_OUTBRACKET = 2;
    private static final int SETMODE0_NONE = 0;
    private static final int SETMODE1_UNICODESET = 1;
    private static final int SETMODE2_PROPERTYPAT = 2;
    private static final int SETMODE3_PREPARSED = 3;
    private volatile BMPSet bmpSet;
    private int[] buffer;
    private int len;
    private int[] list;
    private String pat;
    private int[] rangeList;
    private volatile UnicodeSetStringSpan stringSpan;
    SortedSet<String> strings;
    private static final SortedSet<String> EMPTY_STRINGS = Collections.unmodifiableSortedSet(new TreeSet());
    public static final UnicodeSet EMPTY = new UnicodeSet().m211freeze();
    public static final UnicodeSet ALL_CODE_POINTS = new UnicodeSet(0, 1114111).m211freeze();
    private static XSymbolTable XSYMBOL_TABLE = null;
    private static final VersionInfo NO_VERSION = VersionInfo.getInstance(0, 0, 0, 0);

    /* loaded from: classes5.dex */
    public enum ComparisonStyle {
        SHORTER_FIRST,
        LEXICOGRAPHIC,
        LONGER_FIRST
    }

    /* loaded from: classes5.dex */
    private interface Filter {
        boolean contains(int i);
    }

    /* loaded from: classes5.dex */
    public enum SpanCondition {
        NOT_CONTAINED,
        CONTAINED,
        SIMPLE,
        CONDITION_COUNT
    }

    public UnicodeSet() {
        this.strings = EMPTY_STRINGS;
        this.pat = null;
        this.list = new int[25];
        this.list[0] = HIGH;
        this.len = 1;
    }

    public UnicodeSet(UnicodeSet other) {
        this.strings = EMPTY_STRINGS;
        this.pat = null;
        set(other);
    }

    public UnicodeSet(int start, int end) {
        this();
        add(start, end);
    }

    public UnicodeSet(int... pairs) {
        this.strings = EMPTY_STRINGS;
        this.pat = null;
        if ((pairs.length & 1) != 0) {
            throw new IllegalArgumentException("Must have even number of integers");
        }
        this.list = new int[pairs.length + 1];
        this.len = this.list.length;
        int last = -1;
        int i = 0;
        while (i < pairs.length) {
            int start = pairs[i];
            if (last >= start) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            int i2 = i + 1;
            this.list[i] = start;
            int limit = pairs[i2] + 1;
            if (start >= limit) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            last = limit;
            this.list[i2] = limit;
            i = i2 + 1;
        }
        this.list[i] = HIGH;
    }

    public UnicodeSet(String pattern) {
        this();
        applyPattern(pattern, null, null, 1);
    }

    public UnicodeSet(String pattern, boolean ignoreWhitespace) {
        this();
        applyPattern(pattern, null, null, ignoreWhitespace ? 1 : 0);
    }

    public UnicodeSet(String pattern, int options) {
        this();
        applyPattern(pattern, null, null, options);
    }

    public UnicodeSet(String pattern, ParsePosition pos, SymbolTable symbols) {
        this();
        applyPattern(pattern, pos, symbols, 1);
    }

    public UnicodeSet(String pattern, ParsePosition pos, SymbolTable symbols, int options) {
        this();
        applyPattern(pattern, pos, symbols, options);
    }

    public Object clone() {
        if (isFrozen()) {
            return this;
        }
        return new UnicodeSet(this);
    }

    public UnicodeSet set(int start, int end) {
        checkFrozen();
        clear();
        complement(start, end);
        return this;
    }

    public UnicodeSet set(UnicodeSet other) {
        checkFrozen();
        this.list = Arrays.copyOf(other.list, other.len);
        this.len = other.len;
        this.pat = other.pat;
        if (other.hasStrings()) {
            this.strings = new TreeSet((SortedSet) other.strings);
        } else {
            this.strings = EMPTY_STRINGS;
        }
        return this;
    }

    public final UnicodeSet applyPattern(String pattern) {
        checkFrozen();
        return applyPattern(pattern, null, null, 1);
    }

    public UnicodeSet applyPattern(String pattern, boolean ignoreWhitespace) {
        checkFrozen();
        return applyPattern(pattern, null, null, ignoreWhitespace ? 1 : 0);
    }

    public UnicodeSet applyPattern(String pattern, int options) {
        checkFrozen();
        return applyPattern(pattern, null, null, options);
    }

    public static boolean resemblesPattern(String pattern, int pos) {
        if ((pos + 1 < pattern.length() && pattern.charAt(pos) == '[') || resemblesPropertyPattern(pattern, pos)) {
            return true;
        }
        return false;
    }

    private static void appendCodePoint(Appendable app, int c) {
        try {
            if (c <= 65535) {
                app.append((char) c);
            } else {
                app.append(UTF16.getLeadSurrogate(c)).append(UTF16.getTrailSurrogate(c));
            }
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    private static void append(Appendable app, CharSequence s) {
        try {
            app.append(s);
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    private static <T extends Appendable> T _appendToPat(T buf, String s, boolean escapeUnprintable) {
        int i = 0;
        while (i < s.length()) {
            int cp = s.codePointAt(i);
            _appendToPat(buf, cp, escapeUnprintable);
            i += Character.charCount(cp);
        }
        return buf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends Appendable> T _appendToPat(T buf, int c, boolean escapeUnprintable) {
        if (escapeUnprintable) {
            try {
                if (Utility.isUnprintable(c) && Utility.escapeUnprintable(buf, c)) {
                    return buf;
                }
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }
        if (c != 36 && c != 38 && c != 45 && c != 58 && c != 123 && c != 125) {
            switch (c) {
                case 91:
                case 92:
                case 93:
                case 94:
                    break;
                default:
                    if (PatternProps.isWhiteSpace(c)) {
                        buf.append('\\');
                        break;
                    }
                    break;
            }
            appendCodePoint(buf, c);
            return buf;
        }
        buf.append('\\');
        appendCodePoint(buf, c);
        return buf;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public String toPattern(boolean escapeUnprintable) {
        if (this.pat != null && !escapeUnprintable) {
            return this.pat;
        }
        StringBuilder result = new StringBuilder();
        return ((StringBuilder) _toPattern(result, escapeUnprintable)).toString();
    }

    private <T extends Appendable> T _toPattern(T result, boolean escapeUnprintable) {
        if (this.pat == null) {
            return (T) appendNewPattern(result, escapeUnprintable, true);
        }
        try {
            if (!escapeUnprintable) {
                result.append(this.pat);
                return result;
            }
            boolean oddNumberOfBackslashes = false;
            int i = 0;
            while (i < this.pat.length()) {
                int c = this.pat.codePointAt(i);
                i += Character.charCount(c);
                if (Utility.isUnprintable(c)) {
                    Utility.escapeUnprintable(result, c);
                    oddNumberOfBackslashes = false;
                } else if (!oddNumberOfBackslashes && c == 92) {
                    oddNumberOfBackslashes = true;
                } else {
                    if (oddNumberOfBackslashes) {
                        result.append('\\');
                    }
                    appendCodePoint(result, c);
                    oddNumberOfBackslashes = false;
                }
            }
            if (oddNumberOfBackslashes) {
                result.append('\\');
            }
            return result;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    public StringBuffer _generatePattern(StringBuffer result, boolean escapeUnprintable) {
        return _generatePattern(result, escapeUnprintable, true);
    }

    public StringBuffer _generatePattern(StringBuffer result, boolean escapeUnprintable, boolean includeStrings) {
        return (StringBuffer) appendNewPattern(result, escapeUnprintable, includeStrings);
    }

    private <T extends Appendable> T appendNewPattern(T result, boolean escapeUnprintable, boolean includeStrings) {
        try {
            result.append('[');
            int count = getRangeCount();
            if (count > 1 && getRangeStart(0) == 0 && getRangeEnd(count - 1) == 1114111) {
                result.append('^');
                for (int i = 1; i < count; i++) {
                    int start = getRangeEnd(i - 1) + 1;
                    int end = getRangeStart(i) - 1;
                    _appendToPat(result, start, escapeUnprintable);
                    if (start != end) {
                        if (start + 1 != end) {
                            result.append('-');
                        }
                        _appendToPat(result, end, escapeUnprintable);
                    }
                }
            } else {
                for (int i2 = 0; i2 < count; i2++) {
                    int start2 = getRangeStart(i2);
                    int end2 = getRangeEnd(i2);
                    _appendToPat(result, start2, escapeUnprintable);
                    if (start2 != end2) {
                        if (start2 + 1 != end2) {
                            result.append('-');
                        }
                        _appendToPat(result, end2, escapeUnprintable);
                    }
                }
            }
            if (includeStrings && hasStrings()) {
                for (String s : this.strings) {
                    result.append('{');
                    _appendToPat(result, s, escapeUnprintable);
                    result.append('}');
                }
            }
            result.append(']');
            return result;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    boolean hasStrings() {
        return !this.strings.isEmpty();
    }

    public int size() {
        int n = 0;
        int count = getRangeCount();
        for (int i = 0; i < count; i++) {
            n += (getRangeEnd(i) - getRangeStart(i)) + 1;
        }
        return this.strings.size() + n;
    }

    public boolean isEmpty() {
        return this.len == 1 && !hasStrings();
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public boolean matchesIndexValue(int v) {
        for (int i = 0; i < getRangeCount(); i++) {
            int low = getRangeStart(i);
            int high = getRangeEnd(i);
            if ((low & (-256)) == (high & (-256))) {
                if ((low & 255) <= v && v <= (high & 255)) {
                    return true;
                }
            } else if ((low & 255) <= v || v <= (high & 255)) {
                return true;
            }
        }
        if (hasStrings()) {
            for (String s : this.strings) {
                int c = UTF16.charAt(s, 0);
                if ((c & 255) == v) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.ibm.icu.text.UnicodeFilter, com.ibm.icu.text.UnicodeMatcher
    public int matches(Replaceable text, int[] offset, int limit, boolean incremental) {
        if (offset[0] == limit) {
            if (contains(65535)) {
                return incremental ? 1 : 2;
            }
            return 0;
        }
        if (hasStrings()) {
            boolean forward = offset[0] < limit;
            char firstChar = text.charAt(offset[0]);
            int highWaterLength = 0;
            for (String trial : this.strings) {
                char c = trial.charAt(forward ? 0 : trial.length() - 1);
                if (forward && c > firstChar) {
                    break;
                } else if (c == firstChar) {
                    int length = matchRest(text, offset[0], limit, trial);
                    if (incremental) {
                        int maxLen = forward ? limit - offset[0] : offset[0] - limit;
                        if (length == maxLen) {
                            return 1;
                        }
                    }
                    int maxLen2 = trial.length();
                    if (length == maxLen2) {
                        if (length > highWaterLength) {
                            highWaterLength = length;
                        }
                        if (forward && length < highWaterLength) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            if (highWaterLength != 0) {
                offset[0] = offset[0] + (forward ? highWaterLength : -highWaterLength);
                return 2;
            }
        }
        return super.matches(text, offset, limit, incremental);
    }

    private static int matchRest(Replaceable text, int start, int limit, String s) {
        int maxLen;
        int slen = s.length();
        int i = 1;
        if (start < limit) {
            maxLen = limit - start;
            if (maxLen > slen) {
                maxLen = slen;
            }
            while (i < maxLen) {
                if (text.charAt(start + i) != s.charAt(i)) {
                    return 0;
                }
                i++;
            }
        } else {
            maxLen = start - limit;
            if (maxLen > slen) {
                maxLen = slen;
            }
            int slen2 = slen - 1;
            while (i < maxLen) {
                if (text.charAt(start - i) != s.charAt(slen2 - i)) {
                    return 0;
                }
                i++;
            }
        }
        return maxLen;
    }

    @Deprecated
    public int matchesAt(CharSequence text, int offset) {
        int lastLen = -1;
        if (hasStrings()) {
            char firstChar = text.charAt(offset);
            String trial = null;
            Iterator<String> it = this.strings.iterator();
            while (true) {
                if (it.hasNext()) {
                    String trial2 = it.next();
                    trial = trial2;
                    char firstStringChar = trial.charAt(0);
                    if (firstStringChar >= firstChar && firstStringChar > firstChar) {
                        break;
                    }
                } else {
                    while (true) {
                        int tempLen = matchesAt(text, offset, trial);
                        if (lastLen > tempLen) {
                            break;
                        }
                        lastLen = tempLen;
                        if (!it.hasNext()) {
                            break;
                        }
                        String trial3 = it.next();
                        trial = trial3;
                    }
                }
            }
        }
        if (lastLen < 2) {
            int cp = UTF16.charAt(text, offset);
            if (contains(cp)) {
                lastLen = UTF16.getCharCount(cp);
            }
        }
        return offset + lastLen;
    }

    private static int matchesAt(CharSequence text, int offsetInText, CharSequence substring) {
        int len = substring.length();
        int textLength = text.length();
        if (textLength + offsetInText > len) {
            return -1;
        }
        int i = 0;
        int i2 = offsetInText;
        while (i < len) {
            char pc = substring.charAt(i);
            char tc = text.charAt(i2);
            if (pc != tc) {
                return -1;
            }
            i++;
            i2++;
        }
        return i;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public void addMatchSetTo(UnicodeSet toUnionTo) {
        toUnionTo.addAll(this);
    }

    public int indexOf(int c) {
        if (c < 0 || c > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(c, 6));
        }
        int i = 0;
        int n = 0;
        while (true) {
            int i2 = i + 1;
            int start = this.list[i];
            if (c < start) {
                return -1;
            }
            int i3 = i2 + 1;
            int limit = this.list[i2];
            if (c < limit) {
                return (n + c) - start;
            }
            n += limit - start;
            i = i3;
        }
    }

    public int charAt(int index) {
        if (index >= 0) {
            int len2 = this.len & (-2);
            int i = 0;
            while (i < len2) {
                int i2 = i + 1;
                int start = this.list[i];
                int i3 = i2 + 1;
                int count = this.list[i2] - start;
                if (index < count) {
                    return start + index;
                }
                index -= count;
                i = i3;
            }
            return -1;
        }
        return -1;
    }

    public UnicodeSet add(int start, int end) {
        checkFrozen();
        return add_unchecked(start, end);
    }

    public UnicodeSet addAll(int start, int end) {
        checkFrozen();
        return add_unchecked(start, end);
    }

    private UnicodeSet add_unchecked(int start, int end) {
        if (start < 0 || start > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(start, 6));
        } else if (end < 0 || end > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(end, 6));
        } else {
            if (start < end) {
                int limit = end + 1;
                if ((this.len & 1) != 0) {
                    int lastLimit = this.len == 1 ? -2 : this.list[this.len - 2];
                    if (lastLimit <= start) {
                        checkFrozen();
                        if (lastLimit == start) {
                            this.list[this.len - 2] = limit;
                            if (limit == HIGH) {
                                this.len--;
                            }
                        } else {
                            this.list[this.len - 1] = start;
                            if (limit < HIGH) {
                                ensureCapacity(this.len + 2);
                                int[] iArr = this.list;
                                int i = this.len;
                                this.len = i + 1;
                                iArr[i] = limit;
                                int[] iArr2 = this.list;
                                int i2 = this.len;
                                this.len = i2 + 1;
                                iArr2[i2] = HIGH;
                            } else {
                                ensureCapacity(this.len + 1);
                                int[] iArr3 = this.list;
                                int i3 = this.len;
                                this.len = i3 + 1;
                                iArr3[i3] = HIGH;
                            }
                        }
                        this.pat = null;
                        return this;
                    }
                }
                add(range(start, end), 2, 0);
            } else if (start == end) {
                add(start);
            }
            return this;
        }
    }

    public final UnicodeSet add(int c) {
        checkFrozen();
        return add_unchecked(c);
    }

    private final UnicodeSet add_unchecked(int c) {
        if (c < 0 || c > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(c, 6));
        }
        int i = findCodePoint(c);
        if ((i & 1) != 0) {
            return this;
        }
        if (c == this.list[i] - 1) {
            this.list[i] = c;
            if (c == 1114111) {
                ensureCapacity(this.len + 1);
                int[] iArr = this.list;
                int i2 = this.len;
                this.len = i2 + 1;
                iArr[i2] = HIGH;
            }
            if (i > 0 && c == this.list[i - 1]) {
                System.arraycopy(this.list, i + 1, this.list, i - 1, (this.len - i) - 1);
                this.len -= 2;
            }
        } else if (i > 0 && c == this.list[i - 1]) {
            int[] iArr2 = this.list;
            int i3 = i - 1;
            iArr2[i3] = iArr2[i3] + 1;
        } else {
            if (this.len + 2 > this.list.length) {
                int[] temp = new int[nextCapacity(this.len + 2)];
                if (i != 0) {
                    System.arraycopy(this.list, 0, temp, 0, i);
                }
                System.arraycopy(this.list, i, temp, i + 2, this.len - i);
                this.list = temp;
            } else {
                System.arraycopy(this.list, i, this.list, i + 2, this.len - i);
            }
            this.list[i] = c;
            this.list[i + 1] = c + 1;
            this.len += 2;
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet add(CharSequence s) {
        checkFrozen();
        int cp = getSingleCP(s);
        if (cp < 0) {
            String str = s.toString();
            if (!this.strings.contains(str)) {
                addString(str);
                this.pat = null;
            }
        } else {
            add_unchecked(cp, cp);
        }
        return this;
    }

    private void addString(CharSequence s) {
        if (this.strings == EMPTY_STRINGS) {
            this.strings = new TreeSet();
        }
        this.strings.add(s.toString());
    }

    private static int getSingleCP(CharSequence s) {
        if (s.length() < 1) {
            throw new IllegalArgumentException("Can't use zero-length strings in UnicodeSet");
        }
        if (s.length() > 2) {
            return -1;
        }
        if (s.length() == 1) {
            return s.charAt(0);
        }
        int cp = UTF16.charAt(s, 0);
        if (cp > 65535) {
            return cp;
        }
        return -1;
    }

    public final UnicodeSet addAll(CharSequence s) {
        checkFrozen();
        int i = 0;
        while (i < s.length()) {
            int cp = UTF16.charAt(s, i);
            add_unchecked(cp, cp);
            i += UTF16.getCharCount(cp);
        }
        return this;
    }

    public final UnicodeSet retainAll(CharSequence s) {
        return retainAll(fromAll(s));
    }

    public final UnicodeSet complementAll(CharSequence s) {
        return complementAll(fromAll(s));
    }

    public final UnicodeSet removeAll(CharSequence s) {
        return removeAll(fromAll(s));
    }

    public final UnicodeSet removeAllStrings() {
        checkFrozen();
        if (hasStrings()) {
            this.strings.clear();
            this.pat = null;
        }
        return this;
    }

    public static UnicodeSet from(CharSequence s) {
        return new UnicodeSet().add(s);
    }

    public static UnicodeSet fromAll(CharSequence s) {
        return new UnicodeSet().addAll(s);
    }

    public UnicodeSet retain(int start, int end) {
        checkFrozen();
        if (start < 0 || start > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(start, 6));
        } else if (end < 0 || end > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(end, 6));
        } else {
            if (start <= end) {
                retain(range(start, end), 2, 0);
            } else {
                clear();
            }
            return this;
        }
    }

    public final UnicodeSet retain(int c) {
        return retain(c, c);
    }

    public final UnicodeSet retain(CharSequence cs) {
        int cp = getSingleCP(cs);
        if (cp < 0) {
            checkFrozen();
            String s = cs.toString();
            boolean isIn = this.strings.contains(s);
            if (isIn && size() == 1) {
                return this;
            }
            clear();
            addString(s);
            this.pat = null;
        } else {
            retain(cp, cp);
        }
        return this;
    }

    public UnicodeSet remove(int start, int end) {
        checkFrozen();
        if (start < 0 || start > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(start, 6));
        } else if (end < 0 || end > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(end, 6));
        } else {
            if (start <= end) {
                retain(range(start, end), 2, 2);
            }
            return this;
        }
    }

    public final UnicodeSet remove(int c) {
        return remove(c, c);
    }

    public final UnicodeSet remove(CharSequence s) {
        int cp = getSingleCP(s);
        if (cp < 0) {
            checkFrozen();
            String str = s.toString();
            if (this.strings.contains(str)) {
                this.strings.remove(str);
                this.pat = null;
            }
        } else {
            remove(cp, cp);
        }
        return this;
    }

    public UnicodeSet complement(int start, int end) {
        checkFrozen();
        if (start < 0 || start > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(start, 6));
        } else if (end < 0 || end > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(end, 6));
        } else {
            if (start <= end) {
                xor(range(start, end), 2, 0);
            }
            this.pat = null;
            return this;
        }
    }

    public final UnicodeSet complement(int c) {
        return complement(c, c);
    }

    public UnicodeSet complement() {
        checkFrozen();
        if (this.list[0] == 0) {
            System.arraycopy(this.list, 1, this.list, 0, this.len - 1);
            this.len--;
        } else {
            ensureCapacity(this.len + 1);
            System.arraycopy(this.list, 0, this.list, 1, this.len);
            this.list[0] = 0;
            this.len++;
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet complement(CharSequence s) {
        checkFrozen();
        int cp = getSingleCP(s);
        if (cp < 0) {
            String s2 = s.toString();
            if (this.strings.contains(s2)) {
                this.strings.remove(s2);
            } else {
                addString(s2);
            }
            this.pat = null;
        } else {
            complement(cp, cp);
        }
        return this;
    }

    @Override // com.ibm.icu.text.UnicodeFilter
    public boolean contains(int c) {
        if (c < 0 || c > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(c, 6));
        } else if (this.bmpSet != null) {
            return this.bmpSet.contains(c);
        } else {
            if (this.stringSpan != null) {
                return this.stringSpan.contains(c);
            }
            int i = findCodePoint(c);
            return (i & 1) != 0;
        }
    }

    private final int findCodePoint(int c) {
        if (c < this.list[0]) {
            return 0;
        }
        if (this.len >= 2 && c >= this.list[this.len - 2]) {
            return this.len - 1;
        }
        int lo = 0;
        int hi = this.len - 1;
        while (true) {
            int i = (lo + hi) >>> 1;
            if (i == lo) {
                return hi;
            }
            if (c < this.list[i]) {
                hi = i;
            } else {
                lo = i;
            }
        }
    }

    public boolean contains(int start, int end) {
        if (start < 0 || start > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(start, 6));
        } else if (end < 0 || end > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(end, 6));
        } else {
            int i = findCodePoint(start);
            return (i & 1) != 0 && end < this.list[i];
        }
    }

    public final boolean contains(CharSequence s) {
        int cp = getSingleCP(s);
        if (cp < 0) {
            return this.strings.contains(s.toString());
        }
        return contains(cp);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
        if (r18.strings.containsAll(r19.strings) != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005e, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean containsAll(UnicodeSet b) {
        int[] listB = b.list;
        int startA = 0;
        int aLen = this.len - 1;
        int bLen = b.len - 1;
        int startA2 = 0;
        int limitA = 0;
        int bPtr = 0;
        int startB = 0;
        boolean needB = true;
        boolean needA = true;
        int limitB = 0;
        while (true) {
            if (needA) {
                if (startA >= aLen) {
                    if (!needB || startB < bLen) {
                        return false;
                    }
                } else {
                    int aPtr = startA + 1;
                    int startA3 = this.list[startA];
                    limitA = this.list[aPtr];
                    startA2 = startA3;
                    startA = aPtr + 1;
                }
            }
            if (needB) {
                if (startB >= bLen) {
                    break;
                }
                int bPtr2 = startB + 1;
                int startB2 = listB[startB];
                int startB3 = bPtr2 + 1;
                limitB = listB[bPtr2];
                bPtr = startB2;
                startB = startB3;
            }
            if (bPtr >= limitA) {
                needA = true;
                needB = false;
            } else if (bPtr < startA2 || limitB > limitA) {
                break;
            } else {
                needA = false;
                needB = true;
            }
        }
    }

    public boolean containsAll(String s) {
        int i = 0;
        while (i < s.length()) {
            int cp = UTF16.charAt(s, i);
            if (contains(cp)) {
                i += UTF16.getCharCount(cp);
            } else if (!hasStrings()) {
                return false;
            } else {
                return containsAll(s, 0);
            }
        }
        return true;
    }

    private boolean containsAll(String s, int i) {
        if (i >= s.length()) {
            return true;
        }
        int cp = UTF16.charAt(s, i);
        if (contains(cp) && containsAll(s, UTF16.getCharCount(cp) + i)) {
            return true;
        }
        for (String setStr : this.strings) {
            if (s.startsWith(setStr, i) && containsAll(s, setStr.length() + i)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public String getRegexEquivalent() {
        if (!hasStrings()) {
            return toString();
        }
        StringBuilder result = new StringBuilder("(?:");
        appendNewPattern(result, true, false);
        for (String s : this.strings) {
            result.append('|');
            _appendToPat(result, s, true);
        }
        result.append(")");
        return result.toString();
    }

    public boolean containsNone(int start, int end) {
        if (start < 0 || start > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(start, 6));
        } else if (end < 0 || end > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(end, 6));
        } else {
            int i = -1;
            do {
                i++;
            } while (start >= this.list[i]);
            return (i & 1) == 0 && end < this.list[i];
        }
    }

    public boolean containsNone(UnicodeSet b) {
        int[] listB = b.list;
        int startA = 0;
        int aLen = this.len - 1;
        int bLen = b.len - 1;
        int startA2 = 0;
        int limitA = 0;
        int bPtr = 0;
        int startB = 0;
        boolean needB = true;
        boolean needA = true;
        int limitB = 0;
        while (true) {
            if (needA) {
                if (startA >= aLen) {
                    break;
                }
                int aPtr = startA + 1;
                int startA3 = this.list[startA];
                limitA = this.list[aPtr];
                startA2 = startA3;
                startA = aPtr + 1;
            }
            if (needB) {
                if (startB >= bLen) {
                    break;
                }
                int bPtr2 = startB + 1;
                int startB2 = listB[startB];
                int startB3 = bPtr2 + 1;
                limitB = listB[bPtr2];
                bPtr = startB2;
                startB = startB3;
            }
            if (bPtr >= limitA) {
                needA = true;
                needB = false;
            } else if (startA2 >= limitB) {
                needA = false;
                needB = true;
            } else {
                return false;
            }
        }
        return SortedSetRelation.hasRelation(this.strings, 5, b.strings);
    }

    public boolean containsNone(CharSequence s) {
        return span(s, SpanCondition.NOT_CONTAINED) == s.length();
    }

    public final boolean containsSome(int start, int end) {
        return !containsNone(start, end);
    }

    public final boolean containsSome(UnicodeSet s) {
        return !containsNone(s);
    }

    public final boolean containsSome(CharSequence s) {
        return !containsNone(s);
    }

    public UnicodeSet addAll(UnicodeSet c) {
        checkFrozen();
        add(c.list, c.len, 0);
        if (c.hasStrings()) {
            if (this.strings == EMPTY_STRINGS) {
                this.strings = new TreeSet((SortedSet) c.strings);
            } else {
                this.strings.addAll(c.strings);
            }
        }
        return this;
    }

    public UnicodeSet retainAll(UnicodeSet c) {
        checkFrozen();
        retain(c.list, c.len, 0);
        if (hasStrings()) {
            if (!c.hasStrings()) {
                this.strings.clear();
            } else {
                this.strings.retainAll(c.strings);
            }
        }
        return this;
    }

    public UnicodeSet removeAll(UnicodeSet c) {
        checkFrozen();
        retain(c.list, c.len, 2);
        if (hasStrings() && c.hasStrings()) {
            this.strings.removeAll(c.strings);
        }
        return this;
    }

    public UnicodeSet complementAll(UnicodeSet c) {
        checkFrozen();
        xor(c.list, c.len, 0);
        if (c.hasStrings()) {
            if (this.strings == EMPTY_STRINGS) {
                this.strings = new TreeSet((SortedSet) c.strings);
            } else {
                SortedSetRelation.doOperation(this.strings, 5, c.strings);
            }
        }
        return this;
    }

    public UnicodeSet clear() {
        checkFrozen();
        this.list[0] = HIGH;
        this.len = 1;
        this.pat = null;
        if (hasStrings()) {
            this.strings.clear();
        }
        return this;
    }

    public int getRangeCount() {
        return this.len / 2;
    }

    public int getRangeStart(int index) {
        return this.list[index * 2];
    }

    public int getRangeEnd(int index) {
        return this.list[(index * 2) + 1] - 1;
    }

    public UnicodeSet compact() {
        checkFrozen();
        if (this.len + 7 < this.list.length) {
            this.list = Arrays.copyOf(this.list, this.len);
        }
        this.rangeList = null;
        this.buffer = null;
        if (this.strings != EMPTY_STRINGS && this.strings.isEmpty()) {
            this.strings = EMPTY_STRINGS;
        }
        return this;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            UnicodeSet that = (UnicodeSet) o;
            if (this.len != that.len) {
                return false;
            }
            for (int i = 0; i < this.len; i++) {
                if (this.list[i] != that.list[i]) {
                    return false;
                }
            }
            if (!this.strings.equals(that.strings)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int hashCode() {
        int result = this.len;
        for (int i = 0; i < this.len; i++) {
            result = (result * 1000003) + this.list[i];
        }
        return result;
    }

    public String toString() {
        return toPattern(true);
    }

    @Deprecated
    public UnicodeSet applyPattern(String pattern, ParsePosition pos, SymbolTable symbols, int options) {
        boolean parsePositionWasNull = pos == null;
        if (parsePositionWasNull) {
            pos = new ParsePosition(0);
        }
        StringBuilder rebuiltPat = new StringBuilder();
        RuleCharacterIterator chars = new RuleCharacterIterator(pattern, symbols, pos);
        applyPattern(chars, symbols, rebuiltPat, options, 0);
        if (chars.inVariable()) {
            syntaxError(chars, "Extra chars in variable value");
        }
        this.pat = rebuiltPat.toString();
        if (parsePositionWasNull) {
            int i = pos.getIndex();
            if ((options & 1) != 0) {
                i = PatternProps.skipWhiteSpace(pattern, i);
            }
            if (i != pattern.length()) {
                throw new IllegalArgumentException("Parse of \"" + pattern + "\" failed at " + i);
            }
        }
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x0396, code lost:
        r4 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:185:0x033c A[PHI: r2 r5 r15 
      PHI: (r2v19 'op' char) = (r2v3 'op' char), (r2v3 'op' char), (r2v17 'op' char), (r2v3 'op' char) binds: [B:184:0x0339, B:196:0x036e, B:195:0x035d, B:188:0x0346] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r5v16 char) = (r5v1 char), (r5v1 char), (r5v13 char), (r5v14 char) binds: [B:184:0x0339, B:196:0x036e, B:195:0x035d, B:188:0x0346] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r15v8 'lastChar' int) = (r15v2 'lastChar' int), (r15v5 'lastChar' int), (r15v2 'lastChar' int), (r15v6 'lastChar' int) binds: [B:184:0x0339, B:196:0x036e, B:195:0x035d, B:188:0x0346] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x00cc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0166  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void applyPattern(RuleCharacterIterator chars, SymbolTable symbols, Appendable rebuiltPat, int options, int depth) {
        boolean z;
        int c;
        char c2;
        char op;
        UnicodeSet nested;
        UnicodeSet nested2;
        char op2;
        StringBuilder buf;
        Object backup;
        UnicodeSet scratch;
        StringBuilder buf2;
        String lastString;
        Object backup2;
        String lastString2;
        UnicodeMatcher m;
        SymbolTable symbolTable = symbols;
        int i = depth;
        if (i > 100) {
            syntaxError(chars, "Pattern nested too deeply");
        }
        int opts = (options & 1) != 0 ? 3 | 4 : 3;
        StringBuilder patBuf = new StringBuilder();
        UnicodeSet scratch2 = null;
        Object backup3 = null;
        char c3 = 0;
        int mode = 0;
        boolean invert = false;
        clear();
        String lastString3 = null;
        boolean usePat = false;
        char op3 = 0;
        int lastChar = 0;
        StringBuilder buf3 = null;
        while (true) {
            String lastString4 = lastString3;
            if (mode != 2 && !chars.atEnd()) {
                int c4 = 0;
                boolean literal = false;
                UnicodeSet nested3 = null;
                int setMode = 0;
                if (resemblesPropertyPattern(chars, opts)) {
                    setMode = 2;
                } else {
                    backup3 = chars.getPos(backup3);
                    c = chars.next(opts);
                    literal = chars.isEscaped();
                    if (c != 91 || literal) {
                        if (symbolTable != null && (m = symbolTable.lookupMatcher(c)) != null) {
                            try {
                                nested3 = (UnicodeSet) m;
                                setMode = 3;
                            } catch (ClassCastException e) {
                                syntaxError(chars, "Syntax error");
                            }
                        }
                    } else if (mode == 1) {
                        chars.setPos(backup3);
                        setMode = 1;
                    } else {
                        mode = 1;
                        patBuf.append('[');
                        Object backup4 = chars.getPos(backup3);
                        int c5 = chars.next(opts);
                        boolean literal2 = chars.isEscaped();
                        if (c5 == 94 && !literal2) {
                            invert = true;
                            patBuf.append('^');
                            backup4 = chars.getPos(backup4);
                            c5 = chars.next(opts);
                            chars.isEscaped();
                        }
                        int i2 = c5;
                        backup3 = backup4;
                        c4 = i2;
                        if (c4 == 45) {
                            literal = true;
                        } else {
                            chars.setPos(backup3);
                            lastString3 = lastString4;
                        }
                    }
                    int mode2 = mode;
                    Object backup5 = backup3;
                    if (setMode == 0) {
                        if (c3 == 1) {
                            if (op3 != 0) {
                                syntaxError(chars, "Char expected after operator");
                            }
                            add_unchecked(lastChar, lastChar);
                            _appendToPat(patBuf, lastChar, false);
                            op = 0;
                            c2 = 0;
                        } else {
                            c2 = c3;
                            op = op3;
                        }
                        if (op == '-' || op == '&') {
                            patBuf.append(op);
                        }
                        if (nested3 == null) {
                            if (scratch2 == null) {
                                scratch2 = new UnicodeSet();
                            }
                            nested = scratch2;
                        } else {
                            nested = nested3;
                        }
                        UnicodeSet scratch3 = scratch2;
                        switch (setMode) {
                            case 1:
                                int i3 = i + 1;
                                nested2 = nested;
                                op2 = op;
                                buf = buf3;
                                backup = backup5;
                                nested.applyPattern(chars, symbols, patBuf, options, i3);
                                break;
                            case 2:
                                chars.skipIgnored(opts);
                                nested.applyPropertyPattern(chars, patBuf, symbolTable);
                                nested2 = nested;
                                op2 = op;
                                buf = buf3;
                                backup = backup5;
                                break;
                            case 3:
                                nested._toPattern(patBuf, false);
                                nested2 = nested;
                                op2 = op;
                                buf = buf3;
                                backup = backup5;
                                break;
                            default:
                                nested2 = nested;
                                op2 = op;
                                buf = buf3;
                                backup = backup5;
                                break;
                        }
                        usePat = true;
                        if (mode2 == 0) {
                            set(nested2);
                            mode = 2;
                            z = true;
                        } else {
                            if (op2 == 0) {
                                addAll(nested2);
                            } else if (op2 == '&') {
                                retainAll(nested2);
                            } else if (op2 == '-') {
                                removeAll(nested2);
                            }
                            op3 = 0;
                            c3 = 2;
                            buf3 = buf;
                            mode = mode2;
                            lastString3 = lastString4;
                            scratch2 = scratch3;
                        }
                    } else {
                        StringBuilder buf4 = buf3;
                        backup = backup5;
                        if (mode2 == 0) {
                            syntaxError(chars, "Missing '['");
                        }
                        if (literal) {
                            scratch = scratch2;
                            buf2 = buf4;
                            lastString = lastString4;
                            backup2 = backup;
                            symbolTable = symbols;
                        } else {
                            if (c != 36) {
                                if (c == 38) {
                                    scratch = scratch2;
                                    lastString = lastString4;
                                    if (c3 == 2 && op3 == 0) {
                                        op3 = (char) c;
                                        lastString3 = lastString;
                                        buf3 = buf4;
                                        mode = mode2;
                                        backup3 = backup;
                                        scratch2 = scratch;
                                        symbolTable = symbols;
                                    } else {
                                        syntaxError(chars, "'&' not after set");
                                        buf2 = buf4;
                                        backup2 = backup;
                                        symbolTable = symbols;
                                    }
                                } else if (c == 45) {
                                    scratch = scratch2;
                                    lastString = lastString4;
                                    if (op3 == 0) {
                                        if (c3 != 0) {
                                            op3 = (char) c;
                                        } else if (lastString != null) {
                                            op3 = (char) c;
                                        } else {
                                            add_unchecked(c, c);
                                            int c6 = chars.next(opts);
                                            boolean literal3 = chars.isEscaped();
                                            if (c6 != 93 || literal3) {
                                                c = c6;
                                            } else {
                                                patBuf.append("-]");
                                                lastString3 = lastString;
                                                buf3 = buf4;
                                                scratch2 = scratch;
                                                symbolTable = symbols;
                                                i = depth;
                                                mode = 2;
                                                backup3 = backup;
                                            }
                                        }
                                        lastString3 = lastString;
                                        buf3 = buf4;
                                        mode = mode2;
                                        backup3 = backup;
                                        scratch2 = scratch;
                                        symbolTable = symbols;
                                    }
                                    syntaxError(chars, "'-' not after char, string, or set");
                                    buf2 = buf4;
                                    backup3 = backup;
                                    symbolTable = symbols;
                                } else if (c != 123) {
                                    switch (c) {
                                        case 93:
                                            if (c3 == 1) {
                                                add_unchecked(lastChar, lastChar);
                                                _appendToPat(patBuf, lastChar, false);
                                            }
                                            if (op3 == '-') {
                                                add_unchecked(op3, op3);
                                                patBuf.append(op3);
                                            } else if (op3 == '&') {
                                                syntaxError(chars, "Trailing '&'");
                                            }
                                            patBuf.append(']');
                                            mode = 2;
                                            buf3 = buf4;
                                            lastString3 = lastString4;
                                            break;
                                        case 94:
                                            syntaxError(chars, "'^' not after '['");
                                            scratch = scratch2;
                                            buf2 = buf4;
                                            lastString = lastString4;
                                            backup2 = backup;
                                            symbolTable = symbols;
                                            break;
                                        default:
                                            scratch = scratch2;
                                            buf2 = buf4;
                                            lastString = lastString4;
                                            backup2 = backup;
                                            symbolTable = symbols;
                                            break;
                                    }
                                } else {
                                    if (op3 != 0 && op3 != '-') {
                                        syntaxError(chars, "Missing operand after operator");
                                    }
                                    if (c3 == 1) {
                                        add_unchecked(lastChar, lastChar);
                                        _appendToPat(patBuf, lastChar, false);
                                    }
                                    c3 = 0;
                                    if (buf4 == null) {
                                        buf4 = new StringBuilder();
                                    } else {
                                        buf4.setLength(0);
                                    }
                                    boolean ok = false;
                                    while (true) {
                                        if (!chars.atEnd()) {
                                            int c7 = chars.next(opts);
                                            boolean literal4 = chars.isEscaped();
                                            if (c7 != 125 || literal4) {
                                                appendCodePoint(buf4, c7);
                                            } else {
                                                ok = true;
                                            }
                                        }
                                    }
                                    boolean ok2 = ok;
                                    if (buf4.length() < 1 || !ok2) {
                                        syntaxError(chars, "Invalid multicharacter string");
                                    }
                                    String curString = buf4.toString();
                                    if (op3 == '-') {
                                        int lastSingle = CharSequences.getSingleCodePoint(lastString4 == null ? "" : lastString4);
                                        scratch = scratch2;
                                        int curSingle = CharSequences.getSingleCodePoint(curString);
                                        if (lastSingle == Integer.MAX_VALUE || curSingle == Integer.MAX_VALUE) {
                                            if (this.strings == EMPTY_STRINGS) {
                                                this.strings = new TreeSet();
                                            }
                                            try {
                                                StringRange.expand(lastString4, curString, true, this.strings);
                                            } catch (Exception e2) {
                                                syntaxError(chars, e2.getMessage());
                                            }
                                        } else {
                                            add(lastSingle, curSingle);
                                        }
                                        lastString2 = null;
                                        op3 = 0;
                                    } else {
                                        scratch = scratch2;
                                        add(curString);
                                        lastString2 = curString;
                                    }
                                    patBuf.append('{');
                                    _appendToPat(patBuf, curString, false);
                                    patBuf.append('}');
                                    lastString3 = lastString2;
                                    buf3 = buf4;
                                    mode = mode2;
                                    backup3 = backup;
                                    scratch2 = scratch;
                                    symbolTable = symbols;
                                }
                                i = depth;
                            } else {
                                scratch = scratch2;
                                lastString = lastString4;
                                backup2 = chars.getPos(backup);
                                c = chars.next(opts);
                                boolean literal5 = chars.isEscaped();
                                boolean anchor = c == 93 && !literal5;
                                buf2 = buf4;
                                symbolTable = symbols;
                                if (symbolTable == null && !anchor) {
                                    c = 36;
                                    chars.setPos(backup2);
                                } else if (anchor && op3 == 0) {
                                    if (c3 == 1) {
                                        add_unchecked(lastChar, lastChar);
                                        _appendToPat(patBuf, lastChar, false);
                                    }
                                    add_unchecked(65535);
                                    usePat = true;
                                    patBuf.append(SymbolTable.SYMBOL_REF);
                                    patBuf.append(']');
                                    backup3 = backup2;
                                    lastString3 = lastString;
                                    buf3 = buf2;
                                    scratch2 = scratch;
                                    i = depth;
                                    mode = 2;
                                } else {
                                    syntaxError(chars, "Unquoted '$'");
                                }
                            }
                            switch (c3) {
                                case 0:
                                    if (op3 == '-' && lastString != null) {
                                        syntaxError(chars, "Invalid range");
                                    }
                                    int lastChar2 = c;
                                    lastChar = lastChar2;
                                    lastString3 = null;
                                    c3 = 1;
                                    break;
                                case 1:
                                    if (op3 == '-') {
                                        if (lastString != null) {
                                            syntaxError(chars, "Invalid range");
                                        }
                                        if (lastChar >= c) {
                                            syntaxError(chars, "Invalid range");
                                        }
                                        add_unchecked(lastChar, c);
                                        _appendToPat(patBuf, lastChar, false);
                                        patBuf.append(op3);
                                        _appendToPat(patBuf, c, false);
                                        op3 = 0;
                                        c3 = 0;
                                    } else {
                                        add_unchecked(lastChar, lastChar);
                                        _appendToPat(patBuf, lastChar, false);
                                        int lastChar3 = c;
                                        lastChar = lastChar3;
                                    }
                                    lastString3 = lastString;
                                    break;
                                case 2:
                                    if (op3 != 0) {
                                        syntaxError(chars, "Set expected after operator");
                                    }
                                    int lastChar4 = c;
                                    lastChar = lastChar4;
                                    c3 = 1;
                                    lastString3 = lastString;
                                    break;
                                default:
                                    lastString3 = lastString;
                                    break;
                            }
                            mode = mode2;
                            buf3 = buf2;
                            scratch2 = scratch;
                            i = depth;
                        }
                        backup3 = backup2;
                        switch (c3) {
                        }
                        mode = mode2;
                        buf3 = buf2;
                        scratch2 = scratch;
                        i = depth;
                    }
                    backup3 = backup;
                    symbolTable = symbols;
                    i = depth;
                }
                c = c4;
                int mode22 = mode;
                Object backup52 = backup3;
                if (setMode == 0) {
                }
                backup3 = backup;
                symbolTable = symbols;
                i = depth;
            }
        }
        if (mode != 2) {
            syntaxError(chars, "Missing ']'");
        }
        chars.skipIgnored(opts);
        boolean z2 = z;
        if ((options & 2) != 0) {
            closeOver(2);
        }
        if (invert) {
            complement();
        }
        if (usePat) {
            append(rebuiltPat, patBuf.toString());
        } else {
            appendNewPattern(rebuiltPat, false, z2);
        }
    }

    private static void syntaxError(RuleCharacterIterator chars, String msg) {
        throw new IllegalArgumentException("Error: " + msg + " at \"" + Utility.escape(chars.toString()) + '\"');
    }

    public <T extends Collection<String>> T addAllTo(T target) {
        return (T) addAllTo(this, target);
    }

    public String[] addAllTo(String[] target) {
        return (String[]) addAllTo(this, target);
    }

    public static String[] toArray(UnicodeSet set) {
        return (String[]) addAllTo(set, new String[set.size()]);
    }

    public UnicodeSet add(Iterable<?> source) {
        return addAll(source);
    }

    public UnicodeSet addAll(Iterable<?> source) {
        checkFrozen();
        for (Object o : source) {
            add(o.toString());
        }
        return this;
    }

    private int nextCapacity(int minCapacity) {
        if (minCapacity < 25) {
            return minCapacity + 25;
        }
        if (minCapacity <= 2500) {
            return minCapacity * 5;
        }
        int newCapacity = minCapacity * 2;
        if (newCapacity > MAX_LENGTH) {
            return MAX_LENGTH;
        }
        return newCapacity;
    }

    private void ensureCapacity(int newLen) {
        if (newLen > MAX_LENGTH) {
            newLen = MAX_LENGTH;
        }
        if (newLen <= this.list.length) {
            return;
        }
        int newCapacity = nextCapacity(newLen);
        int[] temp = new int[newCapacity];
        System.arraycopy(this.list, 0, temp, 0, this.len);
        this.list = temp;
    }

    private void ensureBufferCapacity(int newLen) {
        if (newLen > MAX_LENGTH) {
            newLen = MAX_LENGTH;
        }
        if (this.buffer == null || newLen > this.buffer.length) {
            int newCapacity = nextCapacity(newLen);
            this.buffer = new int[newCapacity];
        }
    }

    private int[] range(int start, int end) {
        if (this.rangeList == null) {
            this.rangeList = new int[]{start, end + 1, HIGH};
        } else {
            this.rangeList[0] = start;
            this.rangeList[1] = end + 1;
        }
        return this.rangeList;
    }

    private UnicodeSet xor(int[] other, int otherLen, int polarity) {
        int j;
        int k;
        ensureBufferCapacity(this.len + otherLen);
        int j2 = 0;
        int k2 = 0;
        int j3 = 0 + 1;
        int a = this.list[0];
        if (polarity == 1 || polarity == 2) {
            j = 0;
            if (other[0] == 0) {
                j2 = 0 + 1;
                j = other[j2];
            }
        } else {
            int j4 = 0 + 1;
            int j5 = other[0];
            j = j5;
            j2 = j4;
        }
        while (true) {
            if (a < j) {
                k = k2 + 1;
                this.buffer[k2] = a;
                int i = j3 + 1;
                a = this.list[j3];
                j3 = i;
            } else if (j < a) {
                k = k2 + 1;
                this.buffer[k2] = j;
                int k3 = j2 + 1;
                j = other[j2];
                j2 = k3;
            } else if (a == HIGH) {
                this.buffer[k2] = HIGH;
                this.len = k2 + 1;
                int[] temp = this.list;
                this.list = this.buffer;
                this.buffer = temp;
                this.pat = null;
                return this;
            } else {
                int i2 = j3 + 1;
                a = this.list[j3];
                int i3 = j2 + 1;
                j = other[j2];
                j2 = i3;
                j3 = i2;
            }
            k2 = k;
        }
    }

    private UnicodeSet add(int[] other, int otherLen, int polarity) {
        int j;
        int k;
        int i;
        int i2;
        int j2;
        ensureBufferCapacity(this.len + otherLen);
        int k2 = 0;
        int i3 = 0 + 1;
        int a = this.list[0];
        int j3 = 0 + 1;
        int b = other[0];
        while (true) {
            switch (polarity) {
                case 0:
                    if (a >= b) {
                        if (b >= a) {
                            if (a != HIGH) {
                                if (k2 <= 0 || a > this.buffer[k2 - 1]) {
                                    this.buffer[k2] = a;
                                    a = this.list[i3];
                                    k2++;
                                } else {
                                    k2--;
                                    a = max(this.list[i3], this.buffer[k2]);
                                }
                                i3++;
                                polarity ^= 1;
                                j = j3 + 1;
                                b = other[j3];
                                polarity ^= 2;
                                j3 = j;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            if (k2 <= 0 || b > this.buffer[k2 - 1]) {
                                this.buffer[k2] = b;
                                b = other[j3];
                                k2++;
                            } else {
                                k2--;
                                b = max(other[j3], this.buffer[k2]);
                            }
                            j3++;
                            polarity ^= 2;
                            break;
                        }
                    } else {
                        if (k2 <= 0 || a > this.buffer[k2 - 1]) {
                            this.buffer[k2] = a;
                            a = this.list[i3];
                            k2++;
                        } else {
                            k2--;
                            a = max(this.list[i3], this.buffer[k2]);
                        }
                        i3++;
                        polarity ^= 1;
                        break;
                    }
                    break;
                case 1:
                    if (a >= b) {
                        if (b >= a) {
                            if (a != HIGH) {
                                i2 = i3 + 1;
                                a = this.list[i3];
                                j2 = j3 + 1;
                                b = other[j3];
                                polarity = (polarity ^ 1) ^ 2;
                                j3 = j2;
                                i3 = i2;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            j = j3 + 1;
                            b = other[j3];
                            polarity ^= 2;
                            j3 = j;
                            break;
                        }
                    } else {
                        k = k2 + 1;
                        this.buffer[k2] = a;
                        i = i3 + 1;
                        a = this.list[i3];
                        polarity ^= 1;
                        i3 = i;
                        k2 = k;
                        break;
                    }
                case 2:
                    if (b < a) {
                        k = k2 + 1;
                        this.buffer[k2] = b;
                        int k3 = j3 + 1;
                        b = other[j3];
                        polarity ^= 2;
                        j3 = k3;
                        k2 = k;
                        break;
                    } else if (a < b) {
                        i2 = i3 + 1;
                        a = this.list[i3];
                        polarity ^= 1;
                        i3 = i2;
                        break;
                    } else if (a == HIGH) {
                        break;
                    } else {
                        i2 = i3 + 1;
                        a = this.list[i3];
                        j2 = j3 + 1;
                        b = other[j3];
                        polarity = (polarity ^ 1) ^ 2;
                        j3 = j2;
                        i3 = i2;
                    }
                case 3:
                    if (b <= a) {
                        if (a == HIGH) {
                            break;
                        } else {
                            k = k2 + 1;
                            this.buffer[k2] = a;
                            i = i3 + 1;
                            a = this.list[i3];
                            b = other[j3];
                            polarity = (polarity ^ 1) ^ 2;
                            j3++;
                        }
                    } else if (b == HIGH) {
                        break;
                    } else {
                        k = k2 + 1;
                        this.buffer[k2] = b;
                        i = i3 + 1;
                        a = this.list[i3];
                        b = other[j3];
                        polarity = (polarity ^ 1) ^ 2;
                        j3++;
                    }
                    i3 = i;
                    k2 = k;
                    break;
            }
        }
        this.buffer[k2] = HIGH;
        this.len = k2 + 1;
        int[] temp = this.list;
        this.list = this.buffer;
        this.buffer = temp;
        this.pat = null;
        return this;
    }

    private UnicodeSet retain(int[] other, int otherLen, int polarity) {
        int k;
        int i;
        int j;
        int j2;
        int i2;
        int k2;
        int j3;
        ensureBufferCapacity(this.len + otherLen);
        int k3 = 0;
        int i3 = 0 + 1;
        int a = this.list[0];
        int j4 = 0 + 1;
        int b = other[0];
        while (true) {
            switch (polarity) {
                case 0:
                    if (a >= b) {
                        if (b >= a) {
                            if (a != HIGH) {
                                k = k3 + 1;
                                this.buffer[k3] = a;
                                i = i3 + 1;
                                a = this.list[i3];
                                j = j4 + 1;
                                b = other[j4];
                                polarity = (polarity ^ 1) ^ 2;
                                j4 = j;
                                i3 = i;
                                k3 = k;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            j2 = j4 + 1;
                            b = other[j4];
                            polarity ^= 2;
                            j4 = j2;
                            break;
                        }
                    } else {
                        i2 = i3 + 1;
                        a = this.list[i3];
                        polarity ^= 1;
                        i3 = i2;
                        break;
                    }
                case 1:
                    if (a < b) {
                        i2 = i3 + 1;
                        a = this.list[i3];
                        polarity ^= 1;
                        i3 = i2;
                        break;
                    } else if (b < a) {
                        k = k3 + 1;
                        this.buffer[k3] = b;
                        k2 = j4 + 1;
                        b = other[j4];
                        polarity ^= 2;
                        j4 = k2;
                        k3 = k;
                        break;
                    } else if (a == HIGH) {
                        break;
                    } else {
                        i2 = i3 + 1;
                        a = this.list[i3];
                        j3 = j4 + 1;
                        b = other[j4];
                        polarity = (polarity ^ 1) ^ 2;
                        j4 = j3;
                        i3 = i2;
                    }
                case 2:
                    if (b >= a) {
                        if (a >= b) {
                            if (a != HIGH) {
                                i2 = i3 + 1;
                                a = this.list[i3];
                                j3 = j4 + 1;
                                b = other[j4];
                                polarity = (polarity ^ 1) ^ 2;
                                j4 = j3;
                                i3 = i2;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            k = k3 + 1;
                            this.buffer[k3] = a;
                            i = i3 + 1;
                            a = this.list[i3];
                            polarity ^= 1;
                            i3 = i;
                            k3 = k;
                            break;
                        }
                    } else {
                        j2 = j4 + 1;
                        b = other[j4];
                        polarity ^= 2;
                        j4 = j2;
                        break;
                    }
                case 3:
                    if (a < b) {
                        k = k3 + 1;
                        this.buffer[k3] = a;
                        i = i3 + 1;
                        a = this.list[i3];
                        polarity ^= 1;
                        i3 = i;
                        k3 = k;
                        break;
                    } else if (b < a) {
                        k = k3 + 1;
                        this.buffer[k3] = b;
                        k2 = j4 + 1;
                        b = other[j4];
                        polarity ^= 2;
                        j4 = k2;
                        k3 = k;
                    } else if (a == HIGH) {
                        break;
                    } else {
                        k = k3 + 1;
                        this.buffer[k3] = a;
                        i = i3 + 1;
                        a = this.list[i3];
                        j = j4 + 1;
                        b = other[j4];
                        polarity = (polarity ^ 1) ^ 2;
                        j4 = j;
                        i3 = i;
                        k3 = k;
                    }
            }
        }
        this.buffer[k3] = HIGH;
        this.len = k3 + 1;
        int[] temp = this.list;
        this.list = this.buffer;
        this.buffer = temp;
        this.pat = null;
        return this;
    }

    private static final int max(int a, int b) {
        return a > b ? a : b;
    }

    /* loaded from: classes5.dex */
    private static final class NumericValueFilter implements Filter {
        double value;

        NumericValueFilter(double value) {
            this.value = value;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int ch) {
            return UCharacter.getUnicodeNumericValue(ch) == this.value;
        }
    }

    /* loaded from: classes5.dex */
    private static final class GeneralCategoryMaskFilter implements Filter {
        int mask;

        GeneralCategoryMaskFilter(int mask) {
            this.mask = mask;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int ch) {
            return ((1 << UCharacter.getType(ch)) & this.mask) != 0;
        }
    }

    /* loaded from: classes5.dex */
    private static final class IntPropertyFilter implements Filter {
        int prop;
        int value;

        IntPropertyFilter(int prop, int value) {
            this.prop = prop;
            this.value = value;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int ch) {
            return UCharacter.getIntPropertyValue(ch, this.prop) == this.value;
        }
    }

    /* loaded from: classes5.dex */
    private static final class ScriptExtensionsFilter implements Filter {
        int script;

        ScriptExtensionsFilter(int script) {
            this.script = script;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int c) {
            return UScript.hasScript(c, this.script);
        }
    }

    /* loaded from: classes5.dex */
    private static final class VersionFilter implements Filter {
        VersionInfo version;

        VersionFilter(VersionInfo version) {
            this.version = version;
        }

        @Override // com.ibm.icu.text.UnicodeSet.Filter
        public boolean contains(int ch) {
            VersionInfo v = UCharacter.getAge(ch);
            return !Utility.sameObjects(v, UnicodeSet.NO_VERSION) && v.compareTo(this.version) <= 0;
        }
    }

    private void applyFilter(Filter filter, UnicodeSet inclusions) {
        clear();
        int startHasProperty = -1;
        int limitRange = inclusions.getRangeCount();
        int j = 0;
        while (j < limitRange) {
            int start = inclusions.getRangeStart(j);
            int end = inclusions.getRangeEnd(j);
            int startHasProperty2 = startHasProperty;
            for (int startHasProperty3 = start; startHasProperty3 <= end; startHasProperty3++) {
                if (filter.contains(startHasProperty3)) {
                    if (startHasProperty2 < 0) {
                        startHasProperty2 = startHasProperty3;
                    }
                } else if (startHasProperty2 >= 0) {
                    add_unchecked(startHasProperty2, startHasProperty3 - 1);
                    startHasProperty2 = -1;
                }
            }
            j++;
            startHasProperty = startHasProperty2;
        }
        if (startHasProperty >= 0) {
            add_unchecked(startHasProperty, 1114111);
        }
    }

    private static String mungeCharName(String source) {
        String source2 = PatternProps.trimWhiteSpace(source);
        StringBuilder buf = null;
        for (int i = 0; i < source2.length(); i++) {
            char ch = source2.charAt(i);
            if (PatternProps.isWhiteSpace(ch)) {
                if (buf == null) {
                    buf = new StringBuilder().append((CharSequence) source2, 0, i);
                } else if (buf.charAt(buf.length() - 1) == ' ') {
                }
                ch = ' ';
            }
            if (buf != null) {
                buf.append(ch);
            }
        }
        return buf == null ? source2 : buf.toString();
    }

    public UnicodeSet applyIntPropertyValue(int prop, int value) {
        if (prop == 8192) {
            UnicodeSet inclusions = CharacterPropertiesImpl.getInclusionsForProperty(prop);
            applyFilter(new GeneralCategoryMaskFilter(value), inclusions);
        } else if (prop == 28672) {
            UnicodeSet inclusions2 = CharacterPropertiesImpl.getInclusionsForProperty(prop);
            applyFilter(new ScriptExtensionsFilter(value), inclusions2);
        } else if (prop >= 0 && prop < 65) {
            if (value == 0 || value == 1) {
                set(CharacterProperties.getBinaryPropertySet(prop));
                if (value == 0) {
                    complement();
                }
            } else {
                clear();
            }
        } else if (4096 <= prop && prop < 4121) {
            UnicodeSet inclusions3 = CharacterPropertiesImpl.getInclusionsForProperty(prop);
            applyFilter(new IntPropertyFilter(prop, value), inclusions3);
        } else {
            throw new IllegalArgumentException("unsupported property " + prop);
        }
        return this;
    }

    public UnicodeSet applyPropertyAlias(String propertyAlias, String valueAlias) {
        return applyPropertyAlias(propertyAlias, valueAlias, null);
    }

    public UnicodeSet applyPropertyAlias(String propertyAlias, String valueAlias, SymbolTable symbols) {
        int p;
        int v;
        int v2;
        checkFrozen();
        boolean invert = false;
        if (symbols != null && (symbols instanceof XSymbolTable) && ((XSymbolTable) symbols).applyPropertyAlias(propertyAlias, valueAlias, this)) {
            return this;
        }
        if (XSYMBOL_TABLE == null || !XSYMBOL_TABLE.applyPropertyAlias(propertyAlias, valueAlias, this)) {
            if (valueAlias.length() > 0) {
                p = UCharacter.getPropertyEnum(propertyAlias);
                if (p == 4101) {
                    p = 8192;
                }
                if ((p >= 0 && p < 65) || ((p >= 4096 && p < 4121) || (p >= 8192 && p < 8193))) {
                    try {
                        v = UCharacter.getPropertyValueEnum(p, valueAlias);
                    } catch (IllegalArgumentException e) {
                        if (p != 4098 && p != 4112 && p != 4113) {
                            throw e;
                        }
                        v2 = Integer.parseInt(PatternProps.trimWhiteSpace(valueAlias));
                        if (v2 < 0 || v2 > 255) {
                            throw e;
                        }
                    }
                } else if (p == 12288) {
                    double value = Double.parseDouble(PatternProps.trimWhiteSpace(valueAlias));
                    applyFilter(new NumericValueFilter(value), CharacterPropertiesImpl.getInclusionsForProperty(p));
                    return this;
                } else if (p == 16384) {
                    String buf = mungeCharName(valueAlias);
                    VersionInfo version = VersionInfo.getInstance(buf);
                    applyFilter(new VersionFilter(version), CharacterPropertiesImpl.getInclusionsForProperty(p));
                    return this;
                } else if (p == 16389) {
                    String buf2 = mungeCharName(valueAlias);
                    int ch = UCharacter.getCharFromExtendedName(buf2);
                    if (ch != -1) {
                        clear();
                        add_unchecked(ch);
                        return this;
                    }
                    throw new IllegalArgumentException("Invalid character name");
                } else if (p == 16395) {
                    throw new IllegalArgumentException("Unicode_1_Name (na1) not supported");
                } else {
                    if (p != 28672) {
                        throw new IllegalArgumentException("Unsupported property");
                    }
                    v = UCharacter.getPropertyValueEnum(4106, valueAlias);
                }
            } else {
                UPropertyAliases pnames = UPropertyAliases.INSTANCE;
                int p2 = 8192;
                int v3 = pnames.getPropertyValueEnum(8192, propertyAlias);
                if (v3 == -1) {
                    p2 = 4106;
                    v3 = pnames.getPropertyValueEnum(4106, propertyAlias);
                    if (v3 == -1) {
                        int p3 = pnames.getPropertyEnum(propertyAlias);
                        if (p3 == -1) {
                            p3 = -1;
                        }
                        if (p3 >= 0 && p3 < 65) {
                            v = 1;
                            p = p3;
                        } else if (p3 != -1) {
                            throw new IllegalArgumentException("Missing property value");
                        } else {
                            if (UPropertyAliases.compare(ANY_ID, propertyAlias) == 0) {
                                set(0, 1114111);
                                return this;
                            } else if (UPropertyAliases.compare(ASCII_ID, propertyAlias) == 0) {
                                set(0, 127);
                                return this;
                            } else if (UPropertyAliases.compare(ASSIGNED, propertyAlias) != 0) {
                                throw new IllegalArgumentException("Invalid property alias: " + propertyAlias + "=" + valueAlias);
                            } else {
                                v2 = 1;
                                invert = true;
                                p = 8192;
                                v = v2;
                            }
                        }
                    }
                }
                p = p2;
                v = v3;
            }
            applyIntPropertyValue(p, v);
            if (invert) {
                complement();
            }
            return this;
        }
        return this;
    }

    private static boolean resemblesPropertyPattern(String pattern, int pos) {
        if (pos + 5 > pattern.length()) {
            return false;
        }
        return pattern.regionMatches(pos, "[:", 0, 2) || pattern.regionMatches(true, pos, "\\p", 0, 2) || pattern.regionMatches(pos, "\\N", 0, 2);
    }

    private static boolean resemblesPropertyPattern(RuleCharacterIterator chars, int iterOpts) {
        boolean result = false;
        int iterOpts2 = iterOpts & (-3);
        Object pos = chars.getPos((Object) null);
        int c = chars.next(iterOpts2);
        if (c == 91 || c == 92) {
            int d = chars.next(iterOpts2 & (-5));
            boolean z = false;
            if (c != 91 ? d == 78 || d == 112 || d == 80 : d == 58) {
                z = true;
            }
            result = z;
        }
        chars.setPos(pos);
        return result;
    }

    private UnicodeSet applyPropertyPattern(String pattern, ParsePosition ppos, SymbolTable symbols) {
        int pos;
        String propName;
        String valueName;
        int pos2 = ppos.getIndex();
        if (pos2 + 5 > pattern.length()) {
            return null;
        }
        boolean posix = false;
        boolean isName = false;
        boolean invert = false;
        if (pattern.regionMatches(pos2, "[:", 0, 2)) {
            posix = true;
            pos = PatternProps.skipWhiteSpace(pattern, pos2 + 2);
            if (pos < pattern.length() && pattern.charAt(pos) == '^') {
                pos++;
                invert = true;
            }
        } else if (pattern.regionMatches(true, pos2, "\\p", 0, 2) || pattern.regionMatches(pos2, "\\N", 0, 2)) {
            char c = pattern.charAt(pos2 + 1);
            invert = c == 'P';
            isName = c == 'N';
            int pos3 = PatternProps.skipWhiteSpace(pattern, pos2 + 2);
            if (pos3 != pattern.length()) {
                int pos4 = pos3 + 1;
                if (pattern.charAt(pos3) == 123) {
                    pos = pos4;
                }
            }
            return null;
        } else {
            return null;
        }
        int close = pattern.indexOf(posix ? ":]" : "}", pos);
        if (close < 0) {
            return null;
        }
        int equals = pattern.indexOf(61, pos);
        if (equals >= 0 && equals < close && !isName) {
            propName = pattern.substring(pos, equals);
            valueName = pattern.substring(equals + 1, close);
        } else {
            propName = pattern.substring(pos, close);
            valueName = "";
            if (isName) {
                valueName = propName;
                propName = "na";
            }
        }
        applyPropertyAlias(propName, valueName, symbols);
        if (invert) {
            complement();
        }
        ppos.setIndex((posix ? 2 : 1) + close);
        return this;
    }

    private void applyPropertyPattern(RuleCharacterIterator chars, Appendable rebuiltPat, SymbolTable symbols) {
        String patStr = chars.lookahead();
        ParsePosition pos = new ParsePosition(0);
        applyPropertyPattern(patStr, pos, symbols);
        if (pos.getIndex() == 0) {
            syntaxError(chars, "Invalid property pattern");
        }
        chars.jumpahead(pos.getIndex());
        append(rebuiltPat, patStr.substring(0, pos.getIndex()));
    }

    private static final void addCaseMapping(UnicodeSet set, int result, StringBuilder full) {
        if (result >= 0) {
            if (result > 31) {
                set.add(result);
                return;
            }
            set.add(full.toString());
            full.setLength(0);
        }
    }

    public UnicodeSet closeOver(int attribute) {
        checkFrozen();
        if ((attribute & 6) != 0) {
            UCaseProps csp = UCaseProps.INSTANCE;
            UnicodeSet foldSet = new UnicodeSet(this);
            ULocale root = ULocale.ROOT;
            if ((attribute & 2) != 0 && foldSet.hasStrings()) {
                foldSet.strings.clear();
            }
            int n = getRangeCount();
            StringBuilder full = new StringBuilder();
            for (int i = 0; i < n; i++) {
                int start = getRangeStart(i);
                int end = getRangeEnd(i);
                if ((attribute & 2) != 0) {
                    for (int cp = start; cp <= end; cp++) {
                        csp.addCaseClosure(cp, foldSet);
                    }
                } else {
                    for (int cp2 = start; cp2 <= end; cp2++) {
                        int result = csp.toFullLower(cp2, (UCaseProps.ContextIterator) null, full, 1);
                        addCaseMapping(foldSet, result, full);
                        int result2 = csp.toFullTitle(cp2, (UCaseProps.ContextIterator) null, full, 1);
                        addCaseMapping(foldSet, result2, full);
                        int result3 = csp.toFullUpper(cp2, (UCaseProps.ContextIterator) null, full, 1);
                        addCaseMapping(foldSet, result3, full);
                        int result4 = csp.toFullFolding(cp2, full, 0);
                        addCaseMapping(foldSet, result4, full);
                    }
                }
            }
            if (hasStrings()) {
                if ((attribute & 2) != 0) {
                    for (String s : this.strings) {
                        String str = UCharacter.foldCase(s, 0);
                        if (!csp.addStringCaseClosure(str, foldSet)) {
                            foldSet.add(str);
                        }
                    }
                } else {
                    BreakIterator bi = BreakIterator.getWordInstance(root);
                    for (String str2 : this.strings) {
                        foldSet.add(UCharacter.toLowerCase(root, str2));
                        foldSet.add(UCharacter.toTitleCase(root, str2, bi));
                        foldSet.add(UCharacter.toUpperCase(root, str2));
                        foldSet.add(UCharacter.foldCase(str2, 0));
                    }
                }
            }
            set(foldSet);
        }
        return this;
    }

    /* loaded from: classes5.dex */
    public static abstract class XSymbolTable implements SymbolTable {
        @Override // com.ibm.icu.text.SymbolTable
        public UnicodeMatcher lookupMatcher(int i) {
            return null;
        }

        public boolean applyPropertyAlias(String propertyName, String propertyValue, UnicodeSet result) {
            return false;
        }

        @Override // com.ibm.icu.text.SymbolTable
        public char[] lookup(String s) {
            return null;
        }

        @Override // com.ibm.icu.text.SymbolTable
        public String parseReference(String text, ParsePosition pos, int limit) {
            return null;
        }
    }

    public boolean isFrozen() {
        return (this.bmpSet == null && this.stringSpan == null) ? false : true;
    }

    /* renamed from: freeze */
    public UnicodeSet m211freeze() {
        if (!isFrozen()) {
            compact();
            if (hasStrings()) {
                this.stringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), 127);
            }
            if (this.stringSpan == null || !this.stringSpan.needsStringSpanUTF16()) {
                this.bmpSet = new BMPSet(this.list, this.len);
            }
        }
        return this;
    }

    public int span(CharSequence s, SpanCondition spanCondition) {
        return span(s, 0, spanCondition);
    }

    public int span(CharSequence s, int start, SpanCondition spanCondition) {
        int end = s.length();
        if (start < 0) {
            start = 0;
        } else if (start >= end) {
            return end;
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(s, start, spanCondition, (OutputInt) null);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.span(s, start, spanCondition);
        }
        if (hasStrings()) {
            int which = spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34;
            UnicodeSetStringSpan strSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), which);
            if (strSpan.needsStringSpanUTF16()) {
                return strSpan.span(s, start, spanCondition);
            }
        }
        int which2 = spanCodePointsAndCount(s, start, spanCondition, null);
        return which2;
    }

    @Deprecated
    public int spanAndCount(CharSequence s, int start, SpanCondition spanCondition, OutputInt outCount) {
        if (outCount == null) {
            throw new IllegalArgumentException("outCount must not be null");
        }
        int end = s.length();
        if (start < 0) {
            start = 0;
        } else if (start >= end) {
            return end;
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanAndCount(s, start, spanCondition, outCount);
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(s, start, spanCondition, outCount);
        }
        if (hasStrings()) {
            int which = spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34;
            UnicodeSetStringSpan strSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), which | 64);
            return strSpan.spanAndCount(s, start, spanCondition, outCount);
        }
        int which2 = spanCodePointsAndCount(s, start, spanCondition, outCount);
        return which2;
    }

    private int spanCodePointsAndCount(CharSequence s, int start, SpanCondition spanCondition, OutputInt outCount) {
        int count = 0;
        boolean spanContained = spanCondition != SpanCondition.NOT_CONTAINED;
        int next = start;
        int length = s.length();
        do {
            int c = Character.codePointAt(s, next);
            if (spanContained != contains(c)) {
                break;
            }
            count++;
            next += Character.charCount(c);
        } while (next < length);
        if (outCount != null) {
            outCount.value = count;
        }
        return next;
    }

    public int spanBack(CharSequence s, SpanCondition spanCondition) {
        return spanBack(s, s.length(), spanCondition);
    }

    public int spanBack(CharSequence s, int fromIndex, SpanCondition spanCondition) {
        if (fromIndex <= 0) {
            return 0;
        }
        if (fromIndex > s.length()) {
            fromIndex = s.length();
        }
        if (this.bmpSet != null) {
            return this.bmpSet.spanBack(s, fromIndex, spanCondition);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanBack(s, fromIndex, spanCondition);
        }
        if (hasStrings()) {
            int which = spanCondition == SpanCondition.NOT_CONTAINED ? 17 : 18;
            UnicodeSetStringSpan strSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), which);
            if (strSpan.needsStringSpanUTF16()) {
                return strSpan.spanBack(s, fromIndex, spanCondition);
            }
        }
        boolean spanContained = spanCondition != SpanCondition.NOT_CONTAINED;
        int prev = fromIndex;
        do {
            int c = Character.codePointBefore(s, prev);
            if (spanContained != contains(c)) {
                break;
            }
            prev -= Character.charCount(c);
        } while (prev > 0);
        return prev;
    }

    /* renamed from: cloneAsThawed */
    public UnicodeSet m210cloneAsThawed() {
        UnicodeSet result = new UnicodeSet(this);
        return result;
    }

    private void checkFrozen() {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }

    /* loaded from: classes5.dex */
    public static class EntryRange {
        public int codepoint;
        public int codepointEnd;

        EntryRange() {
        }

        public String toString() {
            StringBuilder sb;
            StringBuilder b = new StringBuilder();
            if (this.codepoint == this.codepointEnd) {
                sb = (StringBuilder) UnicodeSet._appendToPat(b, this.codepoint, false);
            } else {
                StringBuilder sb2 = (StringBuilder) UnicodeSet._appendToPat(b, this.codepoint, false);
                sb2.append('-');
                sb = (StringBuilder) UnicodeSet._appendToPat(sb2, this.codepointEnd, false);
            }
            return sb.toString();
        }
    }

    public Iterable<EntryRange> ranges() {
        return new EntryRangeIterable();
    }

    /* loaded from: classes5.dex */
    private class EntryRangeIterable implements Iterable<EntryRange> {
        private EntryRangeIterable() {
        }

        @Override // java.lang.Iterable
        public Iterator<EntryRange> iterator() {
            return new EntryRangeIterator();
        }
    }

    /* loaded from: classes5.dex */
    private class EntryRangeIterator implements Iterator<EntryRange> {
        int pos;
        EntryRange result;

        private EntryRangeIterator() {
            this.result = new EntryRange();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.pos < UnicodeSet.this.len - 1;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public EntryRange next() {
            if (this.pos < UnicodeSet.this.len - 1) {
                EntryRange entryRange = this.result;
                int[] iArr = UnicodeSet.this.list;
                int i = this.pos;
                this.pos = i + 1;
                entryRange.codepoint = iArr[i];
                EntryRange entryRange2 = this.result;
                int[] iArr2 = UnicodeSet.this.list;
                int i2 = this.pos;
                this.pos = i2 + 1;
                entryRange2.codepointEnd = iArr2[i2] - 1;
                return this.result;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.lang.Iterable
    public Iterator<String> iterator() {
        return new UnicodeSetIterator2(this);
    }

    /* loaded from: classes5.dex */
    private static class UnicodeSetIterator2 implements Iterator<String> {
        private char[] buffer;
        private int current;
        private int item;
        private int len;
        private int limit;
        private int[] sourceList;
        private SortedSet<String> sourceStrings;
        private Iterator<String> stringIterator;

        UnicodeSetIterator2(UnicodeSet source) {
            this.len = source.len - 1;
            if (this.len > 0) {
                this.sourceStrings = source.strings;
                this.sourceList = source.list;
                int[] iArr = this.sourceList;
                int i = this.item;
                this.item = i + 1;
                this.current = iArr[i];
                int[] iArr2 = this.sourceList;
                int i2 = this.item;
                this.item = i2 + 1;
                this.limit = iArr2[i2];
                return;
            }
            this.stringIterator = source.strings.iterator();
            this.sourceList = null;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.sourceList != null || this.stringIterator.hasNext();
        }

        @Override // java.util.Iterator
        public String next() {
            if (this.sourceList == null) {
                return this.stringIterator.next();
            }
            int codepoint = this.current;
            this.current = codepoint + 1;
            if (this.current >= this.limit) {
                if (this.item >= this.len) {
                    this.stringIterator = this.sourceStrings.iterator();
                    this.sourceList = null;
                } else {
                    int[] iArr = this.sourceList;
                    int i = this.item;
                    this.item = i + 1;
                    this.current = iArr[i];
                    int[] iArr2 = this.sourceList;
                    int i2 = this.item;
                    this.item = i2 + 1;
                    this.limit = iArr2[i2];
                }
            }
            if (codepoint <= 65535) {
                return String.valueOf((char) codepoint);
            }
            if (this.buffer == null) {
                this.buffer = new char[2];
            }
            int offset = codepoint - 65536;
            this.buffer[0] = (char) ((offset >>> 10) + 55296);
            this.buffer[1] = (char) ((offset & 1023) + UTF16.TRAIL_SURROGATE_MIN_VALUE);
            return String.valueOf(this.buffer);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public <T extends CharSequence> boolean containsAll(Iterable<T> collection) {
        for (T o : collection) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    public <T extends CharSequence> boolean containsNone(Iterable<T> collection) {
        for (T o : collection) {
            if (contains(o)) {
                return false;
            }
        }
        return true;
    }

    public final <T extends CharSequence> boolean containsSome(Iterable<T> collection) {
        return !containsNone(collection);
    }

    public <T extends CharSequence> UnicodeSet addAll(T... collection) {
        checkFrozen();
        for (T str : collection) {
            add(str);
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet removeAll(Iterable<T> collection) {
        checkFrozen();
        for (T o : collection) {
            remove(o);
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet retainAll(Iterable<T> collection) {
        checkFrozen();
        UnicodeSet toRetain = new UnicodeSet();
        toRetain.addAll((Iterable<?>) collection);
        retainAll(toRetain);
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(UnicodeSet o) {
        return compareTo(o, ComparisonStyle.SHORTER_FIRST);
    }

    public int compareTo(UnicodeSet o, ComparisonStyle style) {
        int diff;
        if (style != ComparisonStyle.LEXICOGRAPHIC && (diff = size() - o.size()) != 0) {
            return (diff < 0) == (style == ComparisonStyle.SHORTER_FIRST) ? -1 : 1;
        }
        int i = 0;
        while (true) {
            int result = this.list[i] - o.list[i];
            if (result != 0) {
                if (this.list[i] == HIGH) {
                    if (hasStrings()) {
                        String item = this.strings.first();
                        return compare(item, o.list[i]);
                    }
                    return 1;
                } else if (o.list[i] != HIGH) {
                    return (i & 1) == 0 ? result : -result;
                } else if (o.hasStrings()) {
                    String item2 = o.strings.first();
                    int compareResult = compare(item2, this.list[i]);
                    if (compareResult > 0) {
                        return -1;
                    }
                    return compareResult < 0 ? 1 : 0;
                } else {
                    return -1;
                }
            } else if (this.list[i] != HIGH) {
                i++;
            } else {
                return compare(this.strings, o.strings);
            }
        }
    }

    public int compareTo(Iterable<String> other) {
        return compare(this, other);
    }

    public static int compare(CharSequence string, int codePoint) {
        return CharSequences.compare(string, codePoint);
    }

    public static int compare(int codePoint, CharSequence string) {
        return -CharSequences.compare(string, codePoint);
    }

    public static <T extends Comparable<T>> int compare(Iterable<T> collection1, Iterable<T> collection2) {
        return compare(collection1.iterator(), collection2.iterator());
    }

    @Deprecated
    public static <T extends Comparable<T>> int compare(Iterator<T> first, Iterator<T> other) {
        while (first.hasNext()) {
            if (!other.hasNext()) {
                return 1;
            }
            T item1 = first.next();
            T item2 = other.next();
            int result = item1.compareTo(item2);
            if (result != 0) {
                return result;
            }
        }
        return other.hasNext() ? -1 : 0;
    }

    public static <T extends Comparable<T>> int compare(Collection<T> collection1, Collection<T> collection2, ComparisonStyle style) {
        int diff;
        if (style == ComparisonStyle.LEXICOGRAPHIC || (diff = collection1.size() - collection2.size()) == 0) {
            return compare(collection1, collection2);
        }
        return (diff < 0) == (style == ComparisonStyle.SHORTER_FIRST) ? -1 : 1;
    }

    public static <T, U extends Collection<T>> U addAllTo(Iterable<T> source, U target) {
        for (T item : source) {
            target.add(item);
        }
        return target;
    }

    public static <T> T[] addAllTo(Iterable<T> source, T[] target) {
        int i = 0;
        for (T item : source) {
            target[i] = item;
            i++;
        }
        return target;
    }

    public Collection<String> strings() {
        if (hasStrings()) {
            return Collections.unmodifiableSortedSet(this.strings);
        }
        return EMPTY_STRINGS;
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence s) {
        return CharSequences.getSingleCodePoint(s);
    }

    @Deprecated
    public UnicodeSet addBridges(UnicodeSet dontCare) {
        UnicodeSet notInInput = new UnicodeSet(this).complement();
        UnicodeSetIterator it = new UnicodeSetIterator(notInInput);
        while (it.nextRange()) {
            if (it.codepoint != 0 && it.codepoint != UnicodeSetIterator.IS_STRING && it.codepointEnd != 1114111 && dontCare.contains(it.codepoint, it.codepointEnd)) {
                add(it.codepoint, it.codepointEnd);
            }
        }
        return this;
    }

    @Deprecated
    public int findIn(CharSequence value, int fromIndex, boolean findNot) {
        while (fromIndex < value.length()) {
            int cp = UTF16.charAt(value, fromIndex);
            if (contains(cp) != findNot) {
                break;
            }
            fromIndex += UTF16.getCharCount(cp);
        }
        return fromIndex;
    }

    @Deprecated
    public int findLastIn(CharSequence value, int fromIndex, boolean findNot) {
        int fromIndex2 = fromIndex - 1;
        while (fromIndex2 >= 0) {
            int cp = UTF16.charAt(value, fromIndex2);
            if (contains(cp) != findNot) {
                break;
            }
            fromIndex2 -= UTF16.getCharCount(cp);
        }
        if (fromIndex2 < 0) {
            return -1;
        }
        return fromIndex2;
    }

    @Deprecated
    public String stripFrom(CharSequence source, boolean matches) {
        StringBuilder result = new StringBuilder();
        int pos = 0;
        while (pos < source.length()) {
            int inside = findIn(source, pos, !matches);
            result.append(source.subSequence(pos, inside));
            pos = findIn(source, inside, matches);
        }
        return result.toString();
    }

    @Deprecated
    public static XSymbolTable getDefaultXSymbolTable() {
        return XSYMBOL_TABLE;
    }

    @Deprecated
    public static void setDefaultXSymbolTable(XSymbolTable xSymbolTable) {
        CharacterPropertiesImpl.clear();
        XSYMBOL_TABLE = xSymbolTable;
    }
}
