package com.ibm.icu.text;

import android.app.backup.FullBackup;
import android.net.wifi.WifiEnterpriseConfig;
import android.telecom.Logging.Session;
import android.telephony.SmsManager;
import android.util.TimeUtils;
import com.ibm.icu.impl.PluralRulesLoader;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class PluralRules implements Serializable {
    static final UnicodeSet ALLOWED_ID = new UnicodeSet("[a-z]").freeze();
    static final Pattern AND_SEPARATED = Pattern.compile("\\s*and\\s*");
    static final Pattern AT_SEPARATED = Pattern.compile("\\s*\\Q\\E@\\s*");
    @Deprecated
    public static final String CATEGORY_SEPARATOR = ";  ";
    static final Pattern COMMA_SEPARATED = Pattern.compile("\\s*,\\s*");
    public static final PluralRules DEFAULT = new PluralRules(new RuleList().addRule(DEFAULT_RULE));
    private static final Rule DEFAULT_RULE = new Rule("other", NO_CONSTRAINT, (FixedDecimalSamples) null, (FixedDecimalSamples) null);
    static final Pattern DOTDOT_SEPARATED = Pattern.compile("\\s*\\Q..\\E\\s*");
    public static final String KEYWORD_FEW = "few";
    public static final String KEYWORD_MANY = "many";
    public static final String KEYWORD_ONE = "one";
    public static final String KEYWORD_OTHER = "other";
    @Deprecated
    public static final String KEYWORD_RULE_SEPARATOR = ": ";
    public static final String KEYWORD_TWO = "two";
    public static final String KEYWORD_ZERO = "zero";
    private static final Constraint NO_CONSTRAINT = new Constraint() {
        private static final long serialVersionUID = 9163464945387899416L;

        public boolean isFulfilled(IFixedDecimal n) {
            return true;
        }

        public boolean isLimited(SampleType sampleType) {
            return false;
        }

        public String toString() {
            return "";
        }
    };
    public static final double NO_UNIQUE_VALUE = -0.00123456777d;
    static final Pattern OR_SEPARATED = Pattern.compile("\\s*or\\s*");
    static final Pattern SEMI_SEPARATED = Pattern.compile("\\s*;\\s*");
    static final Pattern TILDE_SEPARATED = Pattern.compile("\\s*~\\s*");
    private static final long serialVersionUID = 1;
    private final transient Set<String> keywords;
    private final RuleList rules;

    private interface Constraint extends Serializable {
        boolean isFulfilled(IFixedDecimal iFixedDecimal);

        boolean isLimited(SampleType sampleType);
    }

    @Deprecated
    public interface IFixedDecimal {
        @Deprecated
        double getPluralOperand(Operand operand);

        @Deprecated
        boolean isInfinite();

        @Deprecated
        boolean isNaN();
    }

    public enum KeywordStatus {
        INVALID,
        SUPPRESSED,
        UNIQUE,
        BOUNDED,
        UNBOUNDED
    }

    @Deprecated
    public enum Operand {
        n,
        i,
        f,
        t,
        v,
        w,
        j
    }

    public enum PluralType {
        CARDINAL,
        ORDINAL
    }

    @Deprecated
    public enum SampleType {
        INTEGER,
        DECIMAL
    }

    @Deprecated
    public static abstract class Factory {
        @Deprecated
        public abstract PluralRules forLocale(ULocale uLocale, PluralType pluralType);

        @Deprecated
        public abstract ULocale[] getAvailableULocales();

        @Deprecated
        public abstract ULocale getFunctionalEquivalent(ULocale uLocale, boolean[] zArr);

        @Deprecated
        public abstract boolean hasOverride(ULocale uLocale);

        @Deprecated
        protected Factory() {
        }

        @Deprecated
        public final PluralRules forLocale(ULocale locale) {
            return forLocale(locale, PluralType.CARDINAL);
        }

        @Deprecated
        public static PluralRulesLoader getDefaultFactory() {
            return PluralRulesLoader.loader;
        }
    }

    public static PluralRules parseDescription(String description) throws ParseException {
        String description2 = description.trim();
        return description2.length() == 0 ? DEFAULT : new PluralRules(parseRuleChain(description2));
    }

    public static PluralRules createRules(String description) {
        try {
            return parseDescription(description);
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static class FixedDecimal extends Number implements Comparable<FixedDecimal>, IFixedDecimal {
        static final long MAX = 1000000000000000000L;
        private static final long MAX_INTEGER_PART = 1000000000;
        private static final long serialVersionUID = -4756200506571685661L;
        /* access modifiers changed from: private */
        public final int baseFactor;
        final long decimalDigits;
        final long decimalDigitsWithoutTrailingZeros;
        final boolean hasIntegerValue;
        final long integerValue;
        final boolean isNegative;
        final double source;
        final int visibleDecimalDigitCount;
        final int visibleDecimalDigitCountWithoutTrailingZeros;

        @Deprecated
        public double getSource() {
            return this.source;
        }

        @Deprecated
        public int getVisibleDecimalDigitCount() {
            return this.visibleDecimalDigitCount;
        }

        @Deprecated
        public int getVisibleDecimalDigitCountWithoutTrailingZeros() {
            return this.visibleDecimalDigitCountWithoutTrailingZeros;
        }

        @Deprecated
        public long getDecimalDigits() {
            return this.decimalDigits;
        }

        @Deprecated
        public long getDecimalDigitsWithoutTrailingZeros() {
            return this.decimalDigitsWithoutTrailingZeros;
        }

        @Deprecated
        public long getIntegerValue() {
            return this.integerValue;
        }

        @Deprecated
        public boolean isHasIntegerValue() {
            return this.hasIntegerValue;
        }

        @Deprecated
        public boolean isNegative() {
            return this.isNegative;
        }

        @Deprecated
        public int getBaseFactor() {
            return this.baseFactor;
        }

        @Deprecated
        public FixedDecimal(double n, int v, long f) {
            boolean z = true;
            this.isNegative = n < 0.0d;
            this.source = this.isNegative ? -n : n;
            this.visibleDecimalDigitCount = v;
            this.decimalDigits = f;
            this.integerValue = n > 1.0E18d ? MAX : (long) n;
            this.hasIntegerValue = this.source != ((double) this.integerValue) ? false : z;
            if (f == 0) {
                this.decimalDigitsWithoutTrailingZeros = 0;
                this.visibleDecimalDigitCountWithoutTrailingZeros = 0;
            } else {
                long fdwtz = f;
                int trimmedCount = v;
                while (fdwtz % 10 == 0) {
                    fdwtz /= 10;
                    trimmedCount--;
                }
                this.decimalDigitsWithoutTrailingZeros = fdwtz;
                this.visibleDecimalDigitCountWithoutTrailingZeros = trimmedCount;
            }
            this.baseFactor = (int) Math.pow(10.0d, (double) v);
        }

        @Deprecated
        public FixedDecimal(double n, int v) {
            this(n, v, (long) getFractionalDigits(n, v));
        }

        private static int getFractionalDigits(double n, int v) {
            if (v == 0) {
                return 0;
            }
            if (n < 0.0d) {
                n = -n;
            }
            int baseFactor2 = (int) Math.pow(10.0d, (double) v);
            return (int) (Math.round(((double) baseFactor2) * n) % ((long) baseFactor2));
        }

        @Deprecated
        public FixedDecimal(double n) {
            this(n, decimals(n));
        }

        @Deprecated
        public FixedDecimal(long n) {
            this((double) n, 0);
        }

        @Deprecated
        public static int decimals(double n) {
            if (Double.isInfinite(n) || Double.isNaN(n)) {
                return 0;
            }
            if (n < 0.0d) {
                n = -n;
            }
            if (n == Math.floor(n)) {
                return 0;
            }
            if (n < 1.0E9d) {
                long temp = ((long) (1000000.0d * n)) % TimeUtils.NANOS_PER_MS;
                int mask = 10;
                for (int digits = 6; digits > 0; digits--) {
                    if (temp % ((long) mask) != 0) {
                        return digits;
                    }
                    mask *= 10;
                }
                return 0;
            }
            String buf = String.format(Locale.ENGLISH, "%1.15e", new Object[]{Double.valueOf(n)});
            int ePos = buf.lastIndexOf(101);
            int expNumPos = ePos + 1;
            if (buf.charAt(expNumPos) == '+') {
                expNumPos++;
            }
            int numFractionDigits = (ePos - 2) - Integer.parseInt(buf.substring(expNumPos));
            if (numFractionDigits < 0) {
                return 0;
            }
            int i = ePos - 1;
            while (numFractionDigits > 0 && buf.charAt(i) == '0') {
                numFractionDigits--;
                i--;
            }
            return numFractionDigits;
        }

        @Deprecated
        public FixedDecimal(String n) {
            this(Double.parseDouble(n), getVisibleFractionCount(n));
        }

        private static int getVisibleFractionCount(String value) {
            String value2 = value.trim();
            int decimalPos = value2.indexOf(46) + 1;
            if (decimalPos == 0) {
                return 0;
            }
            return value2.length() - decimalPos;
        }

        @Deprecated
        public double getPluralOperand(Operand operand) {
            switch (operand) {
                case n:
                    return this.source;
                case i:
                    return (double) this.integerValue;
                case f:
                    return (double) this.decimalDigits;
                case t:
                    return (double) this.decimalDigitsWithoutTrailingZeros;
                case v:
                    return (double) this.visibleDecimalDigitCount;
                case w:
                    return (double) this.visibleDecimalDigitCountWithoutTrailingZeros;
                default:
                    return this.source;
            }
        }

        @Deprecated
        public static Operand getOperand(String t) {
            return Operand.valueOf(t);
        }

        @Deprecated
        public int compareTo(FixedDecimal other) {
            if (this.integerValue != other.integerValue) {
                if (this.integerValue < other.integerValue) {
                    return -1;
                }
                return 1;
            } else if (this.source != other.source) {
                if (this.source < other.source) {
                    return -1;
                }
                return 1;
            } else if (this.visibleDecimalDigitCount == other.visibleDecimalDigitCount) {
                long diff = this.decimalDigits - other.decimalDigits;
                if (diff == 0) {
                    return 0;
                }
                if (diff < 0) {
                    return -1;
                }
                return 1;
            } else if (this.visibleDecimalDigitCount < other.visibleDecimalDigitCount) {
                return -1;
            } else {
                return 1;
            }
        }

        @Deprecated
        public boolean equals(Object arg0) {
            if (arg0 == null) {
                return false;
            }
            if (arg0 == this) {
                return true;
            }
            if (!(arg0 instanceof FixedDecimal)) {
                return false;
            }
            FixedDecimal other = (FixedDecimal) arg0;
            if (this.source == other.source && this.visibleDecimalDigitCount == other.visibleDecimalDigitCount && this.decimalDigits == other.decimalDigits) {
                return true;
            }
            return false;
        }

        @Deprecated
        public int hashCode() {
            return (int) (this.decimalDigits + ((long) ((this.visibleDecimalDigitCount + ((int) (this.source * 37.0d))) * 37)));
        }

        @Deprecated
        public String toString() {
            return String.format("%." + this.visibleDecimalDigitCount + FullBackup.FILES_TREE_TOKEN, new Object[]{Double.valueOf(this.source)});
        }

        @Deprecated
        public boolean hasIntegerValue() {
            return this.hasIntegerValue;
        }

        @Deprecated
        public int intValue() {
            return (int) this.integerValue;
        }

        @Deprecated
        public long longValue() {
            return this.integerValue;
        }

        @Deprecated
        public float floatValue() {
            return (float) this.source;
        }

        @Deprecated
        public double doubleValue() {
            return this.isNegative ? -this.source : this.source;
        }

        @Deprecated
        public long getShiftedValue() {
            return (this.integerValue * ((long) this.baseFactor)) + this.decimalDigits;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            throw new NotSerializableException();
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            throw new NotSerializableException();
        }

        @Deprecated
        public boolean isNaN() {
            return Double.isNaN(this.source);
        }

        @Deprecated
        public boolean isInfinite() {
            return Double.isInfinite(this.source);
        }
    }

    @Deprecated
    public static class FixedDecimalRange {
        @Deprecated
        public final FixedDecimal end;
        @Deprecated
        public final FixedDecimal start;

        @Deprecated
        public FixedDecimalRange(FixedDecimal start2, FixedDecimal end2) {
            if (start2.visibleDecimalDigitCount == end2.visibleDecimalDigitCount) {
                this.start = start2;
                this.end = end2;
                return;
            }
            throw new IllegalArgumentException("Ranges must have the same number of visible decimals: " + start2 + "~" + end2);
        }

        @Deprecated
        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(this.start);
            if (this.end == this.start) {
                str = "";
            } else {
                str = "~" + this.end;
            }
            sb.append(str);
            return sb.toString();
        }
    }

    @Deprecated
    public static class FixedDecimalSamples {
        @Deprecated
        public final boolean bounded;
        @Deprecated
        public final SampleType sampleType;
        @Deprecated
        public final Set<FixedDecimalRange> samples;

        private FixedDecimalSamples(SampleType sampleType2, Set<FixedDecimalRange> samples2, boolean bounded2) {
            this.sampleType = sampleType2;
            this.samples = samples2;
            this.bounded = bounded2;
        }

        static FixedDecimalSamples parse(String source) {
            SampleType sampleType2;
            Set<FixedDecimalRange> samples2 = new LinkedHashSet<>();
            if (source.startsWith("integer")) {
                sampleType2 = SampleType.INTEGER;
            } else if (source.startsWith("decimal")) {
                sampleType2 = SampleType.DECIMAL;
            } else {
                throw new IllegalArgumentException("Samples must start with 'integer' or 'decimal'");
            }
            boolean haveBound = false;
            boolean bounded2 = true;
            for (String range : PluralRules.COMMA_SEPARATED.split(source.substring(7).trim())) {
                if (range.equals("…") || range.equals(Session.TRUNCATE_STRING)) {
                    bounded2 = false;
                    haveBound = true;
                } else if (!haveBound) {
                    String[] rangeParts = PluralRules.TILDE_SEPARATED.split(range);
                    switch (rangeParts.length) {
                        case 1:
                            FixedDecimal sample = new FixedDecimal(rangeParts[0]);
                            checkDecimal(sampleType2, sample);
                            samples2.add(new FixedDecimalRange(sample, sample));
                            break;
                        case 2:
                            FixedDecimal start = new FixedDecimal(rangeParts[0]);
                            FixedDecimal end = new FixedDecimal(rangeParts[1]);
                            checkDecimal(sampleType2, start);
                            checkDecimal(sampleType2, end);
                            samples2.add(new FixedDecimalRange(start, end));
                            break;
                        default:
                            throw new IllegalArgumentException("Ill-formed number range: " + range);
                    }
                } else {
                    throw new IllegalArgumentException("Can only have … at the end of samples: " + range);
                }
            }
            return new FixedDecimalSamples(sampleType2, Collections.unmodifiableSet(samples2), bounded2);
        }

        private static void checkDecimal(SampleType sampleType2, FixedDecimal sample) {
            boolean z = false;
            boolean z2 = sampleType2 == SampleType.INTEGER;
            if (sample.getVisibleDecimalDigitCount() == 0) {
                z = true;
            }
            if (z2 != z) {
                throw new IllegalArgumentException("Ill-formed number range: " + sample);
            }
        }

        @Deprecated
        public Set<Double> addSamples(Set<Double> result) {
            for (FixedDecimalRange item : this.samples) {
                long startDouble = item.start.getShiftedValue();
                long endDouble = item.end.getShiftedValue();
                for (long d = startDouble; d <= endDouble; d++) {
                    result.add(Double.valueOf(((double) d) / ((double) item.start.baseFactor)));
                }
            }
            return result;
        }

        @Deprecated
        public String toString() {
            StringBuilder b = new StringBuilder("@").append(this.sampleType.toString().toLowerCase(Locale.ENGLISH));
            boolean first = true;
            for (FixedDecimalRange item : this.samples) {
                if (first) {
                    first = false;
                } else {
                    b.append(SmsManager.REGEX_PREFIX_DELIMITER);
                }
                b.append(' ');
                b.append(item);
            }
            if (!this.bounded) {
                b.append(", …");
            }
            return b.toString();
        }

        @Deprecated
        public Set<FixedDecimalRange> getSamples() {
            return this.samples;
        }

        @Deprecated
        public void getStartEndSamples(Set<FixedDecimal> target) {
            for (FixedDecimalRange item : this.samples) {
                target.add(item.start);
                target.add(item.end);
            }
        }
    }

    static class SimpleTokenizer {
        static final UnicodeSet BREAK_AND_IGNORE = new UnicodeSet(9, 10, 12, 13, 32, 32).freeze();
        static final UnicodeSet BREAK_AND_KEEP = new UnicodeSet(33, 33, 37, 37, 44, 44, 46, 46, 61, 61).freeze();

        SimpleTokenizer() {
        }

        static String[] split(String source) {
            int last = -1;
            List<String> result = new ArrayList<>();
            for (int i = 0; i < source.length(); i++) {
                char ch = source.charAt(i);
                if (BREAK_AND_IGNORE.contains((int) ch)) {
                    if (last >= 0) {
                        result.add(source.substring(last, i));
                        last = -1;
                    }
                } else if (BREAK_AND_KEEP.contains((int) ch)) {
                    if (last >= 0) {
                        result.add(source.substring(last, i));
                    }
                    result.add(source.substring(i, i + 1));
                    last = -1;
                } else if (last < 0) {
                    last = i;
                }
            }
            if (last >= 0) {
                result.add(source.substring(last));
            }
            return (String[]) result.toArray(new String[result.size()]);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r35v1, resolved type: com.ibm.icu.text.PluralRules$Constraint} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: com.ibm.icu.text.PluralRules$RangeConstraint} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v4, resolved type: com.ibm.icu.text.PluralRules$RangeConstraint} */
    /* JADX WARNING: type inference failed for: r1v8, types: [com.ibm.icu.text.PluralRules$Constraint] */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02a5, code lost:
        r21 = r1;
        r38 = r4;
        r32 = r5;
        r33 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02ad, code lost:
        if (r38 != null) goto L_0x02b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02af, code lost:
        r0 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x02b0, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02b2, code lost:
        r0 = new com.ibm.icu.text.PluralRules.OrConstraint(r38, r7);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.ibm.icu.text.PluralRules.Constraint parseConstraint(java.lang.String r42) throws java.text.ParseException {
        /*
            r0 = 0
            java.util.regex.Pattern r1 = OR_SEPARATED
            r2 = r42
            java.lang.String[] r1 = r1.split(r2)
            r4 = r0
            r0 = 0
        L_0x000b:
            r5 = r0
            int r0 = r1.length
            if (r5 >= r0) goto L_0x02c2
            r0 = 0
            java.util.regex.Pattern r6 = AND_SEPARATED
            r7 = r1[r5]
            java.lang.String[] r6 = r6.split(r7)
            r7 = r0
            r0 = 0
        L_0x001a:
            r8 = r0
            int r0 = r6.length
            if (r8 >= r0) goto L_0x02a5
            com.ibm.icu.text.PluralRules$Constraint r9 = NO_CONSTRAINT
            r0 = r6[r8]
            java.lang.String r10 = r0.trim()
            java.lang.String[] r11 = com.ibm.icu.text.PluralRules.SimpleTokenizer.split(r10)
            r12 = 0
            r13 = 1
            r14 = 1
            r15 = 4890909195324358656(0x43e0000000000000, double:9.223372036854776E18)
            r17 = -4332462841530417152(0xc3e0000000000000, double:-9.223372036854776E18)
            r19 = 0
            r0 = 0
            int r3 = r0 + 1
            r21 = r1
            r1 = r11[r0]
            r0 = 0
            r20 = r0
            com.ibm.icu.text.PluralRules$Operand r25 = com.ibm.icu.text.PluralRules.FixedDecimal.getOperand(r1)     // Catch:{ Exception -> 0x0293 }
            int r0 = r11.length
            if (r3 >= r0) goto L_0x0266
            int r0 = r3 + 1
            r1 = r11[r3]
            java.lang.String r3 = "mod"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L_0x005a
            java.lang.String r3 = "%"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0068
        L_0x005a:
            int r3 = r0 + 1
            r0 = r11[r0]
            int r12 = java.lang.Integer.parseInt(r0)
            int r0 = r3 + 1
            java.lang.String r1 = nextToken(r11, r3, r10)
        L_0x0068:
            java.lang.String r3 = "not"
            boolean r3 = r3.equals(r1)
            r22 = 1
            if (r3 == 0) goto L_0x008d
            if (r13 != 0) goto L_0x0077
            r3 = r22
            goto L_0x0078
        L_0x0077:
            r3 = 0
        L_0x0078:
            r13 = r3
            int r3 = r0 + 1
            java.lang.String r1 = nextToken(r11, r0, r10)
            java.lang.String r0 = "="
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0088
            goto L_0x00b1
        L_0x0088:
            java.text.ParseException r0 = unexpected(r1, r10)
            throw r0
        L_0x008d:
            java.lang.String r3 = "!"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x00b0
            if (r13 != 0) goto L_0x009a
            r3 = r22
            goto L_0x009b
        L_0x009a:
            r3 = 0
        L_0x009b:
            r13 = r3
            int r3 = r0 + 1
            java.lang.String r1 = nextToken(r11, r0, r10)
            java.lang.String r0 = "="
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00ab
            goto L_0x00b1
        L_0x00ab:
            java.text.ParseException r0 = unexpected(r1, r10)
            throw r0
        L_0x00b0:
            r3 = r0
        L_0x00b1:
            java.lang.String r0 = "is"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00df
            java.lang.String r0 = "in"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00df
            java.lang.String r0 = "="
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00ca
            goto L_0x00df
        L_0x00ca:
            java.lang.String r0 = "within"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00da
            r14 = 0
            int r0 = r3 + 1
            java.lang.String r1 = nextToken(r11, r3, r10)
            goto L_0x00f5
        L_0x00da:
            java.text.ParseException r0 = unexpected(r1, r10)
            throw r0
        L_0x00df:
            java.lang.String r0 = "is"
            boolean r20 = r0.equals(r1)
            if (r20 == 0) goto L_0x00ef
            if (r13 == 0) goto L_0x00ea
            goto L_0x00ef
        L_0x00ea:
            java.text.ParseException r0 = unexpected(r1, r10)
            throw r0
        L_0x00ef:
            int r0 = r3 + 1
            java.lang.String r1 = nextToken(r11, r3, r10)
        L_0x00f5:
            java.lang.String r3 = "not"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0116
            if (r20 != 0) goto L_0x0107
            if (r13 == 0) goto L_0x0102
            goto L_0x0107
        L_0x0102:
            java.text.ParseException r3 = unexpected(r1, r10)
            throw r3
        L_0x0107:
            if (r13 != 0) goto L_0x010a
            goto L_0x010c
        L_0x010a:
            r22 = 0
        L_0x010c:
            r3 = r22
            int r13 = r0 + 1
            java.lang.String r1 = nextToken(r11, r0, r10)
            r0 = r13
            goto L_0x0117
        L_0x0116:
            r3 = r13
        L_0x0117:
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r32 = r5
            r33 = r6
            r34 = r8
            r35 = r9
            r5 = r15
            r8 = r17
        L_0x0127:
            r36 = r14
            long r14 = java.lang.Long.parseLong(r1)
            r16 = r14
            r37 = r1
            int r1 = r11.length
            if (r0 >= r1) goto L_0x018c
            int r1 = r0 + 1
            java.lang.String r0 = nextToken(r11, r0, r10)
            java.lang.String r2 = "."
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x017a
            int r2 = r1 + 1
            java.lang.String r0 = nextToken(r11, r1, r10)
            java.lang.String r1 = "."
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0175
            int r1 = r2 + 1
            java.lang.String r0 = nextToken(r11, r2, r10)
            long r16 = java.lang.Long.parseLong(r0)
            int r2 = r11.length
            if (r1 >= r2) goto L_0x0182
            int r2 = r1 + 1
            java.lang.String r0 = nextToken(r11, r1, r10)
            java.lang.String r1 = ","
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0170
            r38 = r4
            r4 = r2
            r2 = r0
            goto L_0x0191
        L_0x0170:
            java.text.ParseException r1 = unexpected(r0, r10)
            throw r1
        L_0x0175:
            java.text.ParseException r1 = unexpected(r0, r10)
            throw r1
        L_0x017a:
            java.lang.String r2 = ","
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0187
        L_0x0182:
            r2 = r0
            r38 = r4
            r4 = r1
            goto L_0x0191
        L_0x0187:
            java.text.ParseException r2 = unexpected(r0, r10)
            throw r2
        L_0x018c:
            r38 = r4
            r2 = r37
            r4 = r0
        L_0x0191:
            r0 = r16
            int r16 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r16 > 0) goto L_0x0249
            if (r12 == 0) goto L_0x01bc
            r40 = r2
            r39 = r3
            long r2 = (long) r12
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x01a3
            goto L_0x01c0
        L_0x01a3:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r3 = ">mod="
            r2.append(r3)
            r2.append(r12)
            java.lang.String r2 = r2.toString()
            java.text.ParseException r2 = unexpected(r2, r10)
            throw r2
        L_0x01bc:
            r40 = r2
            r39 = r3
        L_0x01c0:
            java.lang.Long r2 = java.lang.Long.valueOf(r14)
            r13.add(r2)
            java.lang.Long r2 = java.lang.Long.valueOf(r0)
            r13.add(r2)
            double r2 = (double) r14
            double r5 = java.lang.Math.min(r5, r2)
            double r2 = (double) r0
            double r8 = java.lang.Math.max(r8, r2)
            int r2 = r11.length
            if (r4 < r2) goto L_0x0235
            java.lang.String r0 = ","
            r2 = r40
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L_0x0230
            int r0 = r13.size()
            r1 = 2
            if (r0 != r1) goto L_0x01ef
            r0 = 0
            goto L_0x0208
        L_0x01ef:
            int r0 = r13.size()
            long[] r0 = new long[r0]
            r1 = 0
        L_0x01f6:
            int r3 = r0.length
            if (r1 >= r3) goto L_0x0208
            java.lang.Object r3 = r13.get(r1)
            java.lang.Long r3 = (java.lang.Long) r3
            long r14 = r3.longValue()
            r0[r1] = r14
            int r1 = r1 + 1
            goto L_0x01f6
        L_0x0208:
            int r1 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r1 == 0) goto L_0x0218
            if (r20 == 0) goto L_0x0218
            if (r39 == 0) goto L_0x0211
            goto L_0x0218
        L_0x0211:
            java.lang.String r1 = "is not <range>"
            java.text.ParseException r1 = unexpected(r1, r10)
            throw r1
        L_0x0218:
            com.ibm.icu.text.PluralRules$RangeConstraint r1 = new com.ibm.icu.text.PluralRules$RangeConstraint
            r22 = r1
            r23 = r12
            r24 = r39
            r26 = r36
            r27 = r5
            r29 = r8
            r31 = r0
            r22.<init>(r23, r24, r25, r26, r27, r29, r31)
            r3 = r4
            r15 = r5
            r17 = r8
            goto L_0x0279
        L_0x0230:
            java.text.ParseException r0 = unexpected(r2, r10)
            throw r0
        L_0x0235:
            r2 = r40
            int r3 = r4 + 1
            java.lang.String r1 = nextToken(r11, r4, r10)
            r0 = r3
            r14 = r36
            r4 = r38
            r3 = r39
            r2 = r42
            goto L_0x0127
        L_0x0249:
            r39 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r14)
            r41 = r2
            java.lang.String r2 = "~"
            r3.append(r2)
            r3.append(r0)
            java.lang.String r2 = r3.toString()
            java.text.ParseException r2 = unexpected(r2, r10)
            throw r2
        L_0x0266:
            r38 = r4
            r32 = r5
            r33 = r6
            r34 = r8
            r35 = r9
            r2 = r1
            r39 = r13
            r36 = r14
            r0 = r19
            r1 = r35
        L_0x0279:
            if (r7 != 0) goto L_0x027e
            r4 = r1
            r7 = r4
            goto L_0x0285
        L_0x027e:
            com.ibm.icu.text.PluralRules$AndConstraint r4 = new com.ibm.icu.text.PluralRules$AndConstraint
            r4.<init>(r7, r1)
            r0 = r4
            r7 = r0
        L_0x0285:
            int r0 = r34 + 1
            r1 = r21
            r5 = r32
            r6 = r33
            r4 = r38
            r2 = r42
            goto L_0x001a
        L_0x0293:
            r0 = move-exception
            r2 = r0
            r38 = r4
            r32 = r5
            r33 = r6
            r34 = r8
            r35 = r9
            r0 = r2
            java.text.ParseException r2 = unexpected(r1, r10)
            throw r2
        L_0x02a5:
            r21 = r1
            r38 = r4
            r32 = r5
            r33 = r6
            if (r38 != 0) goto L_0x02b2
            r0 = r7
        L_0x02b0:
            r4 = r0
            goto L_0x02ba
        L_0x02b2:
            com.ibm.icu.text.PluralRules$OrConstraint r0 = new com.ibm.icu.text.PluralRules$OrConstraint
            r1 = r38
            r0.<init>(r1, r7)
            goto L_0x02b0
        L_0x02ba:
            int r0 = r32 + 1
            r1 = r21
            r2 = r42
            goto L_0x000b
        L_0x02c2:
            r21 = r1
            r1 = r4
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.PluralRules.parseConstraint(java.lang.String):com.ibm.icu.text.PluralRules$Constraint");
    }

    private static ParseException unexpected(String token, String context) {
        return new ParseException("unexpected token '" + token + "' in '" + context + "'", -1);
    }

    private static String nextToken(String[] tokens, int x, String context) throws ParseException {
        if (x < tokens.length) {
            return tokens[x];
        }
        throw new ParseException("missing token at end of '" + context + "'", -1);
    }

    /* access modifiers changed from: private */
    public static Rule parseRule(String description) throws ParseException {
        Constraint constraint;
        if (description.length() == 0) {
            return DEFAULT_RULE;
        }
        String description2 = description.toLowerCase(Locale.ENGLISH);
        int x = description2.indexOf(58);
        if (x != -1) {
            String keyword = description2.substring(0, x).trim();
            if (isValidKeyword(keyword)) {
                String description3 = description2.substring(x + 1).trim();
                String[] constraintOrSamples = AT_SEPARATED.split(description3);
                FixedDecimalSamples integerSamples = null;
                FixedDecimalSamples decimalSamples = null;
                boolean z = true;
                switch (constraintOrSamples.length) {
                    case 1:
                        break;
                    case 2:
                        integerSamples = FixedDecimalSamples.parse(constraintOrSamples[1]);
                        if (integerSamples.sampleType == SampleType.DECIMAL) {
                            decimalSamples = integerSamples;
                            integerSamples = null;
                            break;
                        }
                        break;
                    case 3:
                        integerSamples = FixedDecimalSamples.parse(constraintOrSamples[1]);
                        decimalSamples = FixedDecimalSamples.parse(constraintOrSamples[2]);
                        if (!(integerSamples.sampleType == SampleType.INTEGER && decimalSamples.sampleType == SampleType.DECIMAL)) {
                            throw new IllegalArgumentException("Must have @integer then @decimal in " + description3);
                        }
                    default:
                        throw new IllegalArgumentException("Too many samples in " + description3);
                }
                if (0 == 0) {
                    boolean isOther = keyword.equals("other");
                    if (constraintOrSamples[0].length() != 0) {
                        z = false;
                    }
                    if (isOther == z) {
                        if (isOther) {
                            constraint = NO_CONSTRAINT;
                        } else {
                            constraint = parseConstraint(constraintOrSamples[0]);
                        }
                        return new Rule(keyword, constraint, integerSamples, decimalSamples);
                    }
                    throw new IllegalArgumentException("The keyword 'other' must have no constraints, just samples.");
                }
                throw new IllegalArgumentException("Ill-formed samples—'@' characters.");
            }
            throw new ParseException("keyword '" + keyword + " is not valid", 0);
        }
        throw new ParseException("missing ':' in rule description '" + description2 + "'", 0);
    }

    private static RuleList parseRuleChain(String description) throws ParseException {
        RuleList result = new RuleList();
        if (description.endsWith(";")) {
            description = description.substring(0, description.length() - 1);
        }
        String[] rules2 = SEMI_SEPARATED.split(description);
        for (String trim : rules2) {
            Rule rule = parseRule(trim.trim());
            boolean unused = result.hasExplicitBoundingInfo = result.hasExplicitBoundingInfo | ((rule.integerSamples == null && rule.decimalSamples == null) ? false : true);
            result.addRule(rule);
        }
        return result.finish();
    }

    private static class RangeConstraint implements Constraint, Serializable {
        private static final long serialVersionUID = 1;
        private final boolean inRange;
        private final boolean integersOnly;
        private final double lowerBound;
        private final int mod;
        private final Operand operand;
        private final long[] range_list;
        private final double upperBound;

        RangeConstraint(int mod2, boolean inRange2, Operand operand2, boolean integersOnly2, double lowBound, double highBound, long[] vals) {
            this.mod = mod2;
            this.inRange = inRange2;
            this.integersOnly = integersOnly2;
            this.lowerBound = lowBound;
            this.upperBound = highBound;
            this.range_list = vals;
            this.operand = operand2;
        }

        public boolean isFulfilled(IFixedDecimal number) {
            double n = number.getPluralOperand(this.operand);
            if ((this.integersOnly && n - ((double) ((long) n)) != 0.0d) || (this.operand == Operand.j && number.getPluralOperand(Operand.v) != 0.0d)) {
                return !this.inRange;
            }
            if (this.mod != 0) {
                n %= (double) this.mod;
            }
            boolean test = n >= this.lowerBound && n <= this.upperBound;
            if (test && this.range_list != null) {
                boolean test2 = false;
                int i = 0;
                while (!test2 && i < this.range_list.length) {
                    test2 = n >= ((double) this.range_list[i]) && n <= ((double) this.range_list[i + 1]);
                    i += 2;
                }
                test = test2;
            }
            if (this.inRange == test) {
                return true;
            }
            return false;
        }

        public boolean isLimited(SampleType sampleType) {
            boolean hasDecimals = (this.operand == Operand.v || this.operand == Operand.w || this.operand == Operand.f || this.operand == Operand.t) && this.inRange != ((this.lowerBound > this.upperBound ? 1 : (this.lowerBound == this.upperBound ? 0 : -1)) == 0 && (this.lowerBound > 0.0d ? 1 : (this.lowerBound == 0.0d ? 0 : -1)) == 0);
            switch (sampleType) {
                case INTEGER:
                    if (hasDecimals) {
                        return true;
                    }
                    if ((this.operand == Operand.n || this.operand == Operand.i || this.operand == Operand.j) && this.mod == 0 && this.inRange) {
                        return true;
                    }
                    return false;
                case DECIMAL:
                    if ((!hasDecimals || this.operand == Operand.n || this.operand == Operand.j) && ((this.integersOnly || this.lowerBound == this.upperBound) && this.mod == 0 && this.inRange)) {
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
            if (r11.inRange != false) goto L_0x002c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
            r1 = " != ";
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
            if (r11.inRange != false) goto L_0x002c;
         */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x004b  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x006a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String toString() {
            /*
                r11 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                com.ibm.icu.text.PluralRules$Operand r1 = r11.operand
                r0.append(r1)
                int r1 = r11.mod
                if (r1 == 0) goto L_0x0018
                java.lang.String r1 = " % "
                r0.append(r1)
                int r1 = r11.mod
                r0.append(r1)
            L_0x0018:
                double r1 = r11.lowerBound
                double r3 = r11.upperBound
                int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                r7 = 0
                r8 = 1
                if (r1 == 0) goto L_0x0024
                r1 = r8
                goto L_0x0025
            L_0x0024:
                r1 = r7
            L_0x0025:
                r9 = r1
                if (r9 != 0) goto L_0x0032
                boolean r1 = r11.inRange
                if (r1 == 0) goto L_0x002f
            L_0x002c:
                java.lang.String r1 = " = "
                goto L_0x0044
            L_0x002f:
                java.lang.String r1 = " != "
                goto L_0x0044
            L_0x0032:
                boolean r1 = r11.integersOnly
                if (r1 == 0) goto L_0x003b
                boolean r1 = r11.inRange
                if (r1 == 0) goto L_0x002f
                goto L_0x002c
            L_0x003b:
                boolean r1 = r11.inRange
                if (r1 == 0) goto L_0x0042
                java.lang.String r1 = " within "
                goto L_0x0044
            L_0x0042:
                java.lang.String r1 = " not within "
            L_0x0044:
                r0.append(r1)
                long[] r1 = r11.range_list
                if (r1 == 0) goto L_0x006a
                r1 = r7
            L_0x004c:
                r10 = r1
                long[] r1 = r11.range_list
                int r1 = r1.length
                if (r10 >= r1) goto L_0x0073
                long[] r1 = r11.range_list
                r2 = r1[r10]
                double r2 = (double) r2
                long[] r1 = r11.range_list
                int r4 = r10 + 1
                r4 = r1[r4]
                double r4 = (double) r4
                if (r10 == 0) goto L_0x0062
                r6 = r8
                goto L_0x0063
            L_0x0062:
                r6 = r7
            L_0x0063:
                r1 = r0
                com.ibm.icu.text.PluralRules.addRange(r1, r2, r4, r6)
                int r1 = r10 + 2
                goto L_0x004c
            L_0x006a:
                double r2 = r11.lowerBound
                double r4 = r11.upperBound
                r6 = 0
                r1 = r0
                com.ibm.icu.text.PluralRules.addRange(r1, r2, r4, r6)
            L_0x0073:
                java.lang.String r1 = r0.toString()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.PluralRules.RangeConstraint.toString():java.lang.String");
        }
    }

    /* access modifiers changed from: private */
    public static void addRange(StringBuilder result, double lb, double ub, boolean addSeparator) {
        if (addSeparator) {
            result.append(SmsManager.REGEX_PREFIX_DELIMITER);
        }
        if (lb == ub) {
            result.append(format(lb));
            return;
        }
        result.append(format(lb) + ".." + format(ub));
    }

    private static String format(double lb) {
        long lbi = (long) lb;
        return lb == ((double) lbi) ? String.valueOf(lbi) : String.valueOf(lb);
    }

    private static abstract class BinaryConstraint implements Constraint, Serializable {
        private static final long serialVersionUID = 1;
        protected final Constraint a;
        protected final Constraint b;

        protected BinaryConstraint(Constraint a2, Constraint b2) {
            this.a = a2;
            this.b = b2;
        }
    }

    private static class AndConstraint extends BinaryConstraint {
        private static final long serialVersionUID = 7766999779862263523L;

        AndConstraint(Constraint a, Constraint b) {
            super(a, b);
        }

        public boolean isFulfilled(IFixedDecimal n) {
            return this.a.isFulfilled(n) && this.b.isFulfilled(n);
        }

        public boolean isLimited(SampleType sampleType) {
            return this.a.isLimited(sampleType) || this.b.isLimited(sampleType);
        }

        public String toString() {
            return this.a.toString() + " and " + this.b.toString();
        }
    }

    private static class OrConstraint extends BinaryConstraint {
        private static final long serialVersionUID = 1405488568664762222L;

        OrConstraint(Constraint a, Constraint b) {
            super(a, b);
        }

        public boolean isFulfilled(IFixedDecimal n) {
            return this.a.isFulfilled(n) || this.b.isFulfilled(n);
        }

        public boolean isLimited(SampleType sampleType) {
            return this.a.isLimited(sampleType) && this.b.isLimited(sampleType);
        }

        public String toString() {
            return this.a.toString() + " or " + this.b.toString();
        }
    }

    private static class Rule implements Serializable {
        private static final long serialVersionUID = 1;
        private final Constraint constraint;
        /* access modifiers changed from: private */
        public final FixedDecimalSamples decimalSamples;
        /* access modifiers changed from: private */
        public final FixedDecimalSamples integerSamples;
        private final String keyword;

        public Rule(String keyword2, Constraint constraint2, FixedDecimalSamples integerSamples2, FixedDecimalSamples decimalSamples2) {
            this.keyword = keyword2;
            this.constraint = constraint2;
            this.integerSamples = integerSamples2;
            this.decimalSamples = decimalSamples2;
        }

        public Rule and(Constraint c) {
            return new Rule(this.keyword, new AndConstraint(this.constraint, c), this.integerSamples, this.decimalSamples);
        }

        public Rule or(Constraint c) {
            return new Rule(this.keyword, new OrConstraint(this.constraint, c), this.integerSamples, this.decimalSamples);
        }

        public String getKeyword() {
            return this.keyword;
        }

        public boolean appliesTo(IFixedDecimal n) {
            return this.constraint.isFulfilled(n);
        }

        public boolean isLimited(SampleType sampleType) {
            return this.constraint.isLimited(sampleType);
        }

        public String toString() {
            String str;
            String str2;
            StringBuilder sb = new StringBuilder();
            sb.append(this.keyword);
            sb.append(PluralRules.KEYWORD_RULE_SEPARATOR);
            sb.append(this.constraint.toString());
            if (this.integerSamples == null) {
                str = "";
            } else {
                str = WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.integerSamples.toString();
            }
            sb.append(str);
            if (this.decimalSamples == null) {
                str2 = "";
            } else {
                str2 = WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.decimalSamples.toString();
            }
            sb.append(str2);
            return sb.toString();
        }

        public int hashCode() {
            return this.keyword.hashCode() ^ this.constraint.hashCode();
        }

        public String getConstraint() {
            return this.constraint.toString();
        }
    }

    private static class RuleList implements Serializable {
        private static final long serialVersionUID = 1;
        /* access modifiers changed from: private */
        public boolean hasExplicitBoundingInfo;
        private final List<Rule> rules;

        private RuleList() {
            this.hasExplicitBoundingInfo = false;
            this.rules = new ArrayList();
        }

        public RuleList addRule(Rule nextRule) {
            String keyword = nextRule.getKeyword();
            for (Rule rule : this.rules) {
                if (keyword.equals(rule.getKeyword())) {
                    throw new IllegalArgumentException("Duplicate keyword: " + keyword);
                }
            }
            this.rules.add(nextRule);
            return this;
        }

        public RuleList finish() throws ParseException {
            Rule otherRule = null;
            Iterator<Rule> it = this.rules.iterator();
            while (it.hasNext()) {
                Rule rule = it.next();
                if ("other".equals(rule.getKeyword())) {
                    otherRule = rule;
                    it.remove();
                }
            }
            if (otherRule == null) {
                otherRule = PluralRules.parseRule("other:");
            }
            this.rules.add(otherRule);
            return this;
        }

        private Rule selectRule(IFixedDecimal n) {
            for (Rule rule : this.rules) {
                if (rule.appliesTo(n)) {
                    return rule;
                }
            }
            return null;
        }

        public String select(IFixedDecimal n) {
            if (n.isInfinite() || n.isNaN()) {
                return "other";
            }
            return selectRule(n).getKeyword();
        }

        public Set<String> getKeywords() {
            Set<String> result = new LinkedHashSet<>();
            for (Rule rule : this.rules) {
                result.add(rule.getKeyword());
            }
            return result;
        }

        public boolean isLimited(String keyword, SampleType sampleType) {
            if (!this.hasExplicitBoundingInfo) {
                return computeLimited(keyword, sampleType);
            }
            FixedDecimalSamples mySamples = getDecimalSamples(keyword, sampleType);
            if (mySamples == null) {
                return true;
            }
            return mySamples.bounded;
        }

        public boolean computeLimited(String keyword, SampleType sampleType) {
            boolean result = false;
            for (Rule rule : this.rules) {
                if (keyword.equals(rule.getKeyword())) {
                    if (!rule.isLimited(sampleType)) {
                        return false;
                    }
                    result = true;
                }
            }
            return result;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (Rule rule : this.rules) {
                if (builder.length() != 0) {
                    builder.append(PluralRules.CATEGORY_SEPARATOR);
                }
                builder.append(rule);
            }
            return builder.toString();
        }

        public String getRules(String keyword) {
            for (Rule rule : this.rules) {
                if (rule.getKeyword().equals(keyword)) {
                    return rule.getConstraint();
                }
            }
            return null;
        }

        public boolean select(IFixedDecimal sample, String keyword) {
            for (Rule rule : this.rules) {
                if (rule.getKeyword().equals(keyword) && rule.appliesTo(sample)) {
                    return true;
                }
            }
            return false;
        }

        public FixedDecimalSamples getDecimalSamples(String keyword, SampleType sampleType) {
            for (Rule rule : this.rules) {
                if (rule.getKeyword().equals(keyword)) {
                    return sampleType == SampleType.INTEGER ? rule.integerSamples : rule.decimalSamples;
                }
            }
            return null;
        }
    }

    private boolean addConditional(Set<IFixedDecimal> toAddTo, Set<IFixedDecimal> others, double trial) {
        IFixedDecimal toAdd = new FixedDecimal(trial);
        if (toAddTo.contains(toAdd) || others.contains(toAdd)) {
            return false;
        }
        others.add(toAdd);
        return true;
    }

    public static PluralRules forLocale(ULocale locale) {
        return Factory.getDefaultFactory().forLocale(locale, PluralType.CARDINAL);
    }

    public static PluralRules forLocale(Locale locale) {
        return forLocale(ULocale.forLocale(locale));
    }

    public static PluralRules forLocale(ULocale locale, PluralType type) {
        return Factory.getDefaultFactory().forLocale(locale, type);
    }

    public static PluralRules forLocale(Locale locale, PluralType type) {
        return forLocale(ULocale.forLocale(locale), type);
    }

    private static boolean isValidKeyword(String token) {
        return ALLOWED_ID.containsAll(token);
    }

    private PluralRules(RuleList rules2) {
        this.rules = rules2;
        this.keywords = Collections.unmodifiableSet(rules2.getKeywords());
    }

    public int hashCode() {
        return this.rules.hashCode();
    }

    public String select(double number) {
        return this.rules.select(new FixedDecimal(number));
    }

    @Deprecated
    public String select(double number, int countVisibleFractionDigits, long fractionaldigits) {
        return this.rules.select(new FixedDecimal(number, countVisibleFractionDigits, fractionaldigits));
    }

    @Deprecated
    public String select(IFixedDecimal number) {
        return this.rules.select(number);
    }

    @Deprecated
    public boolean matches(FixedDecimal sample, String keyword) {
        return this.rules.select(sample, keyword);
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getUniqueKeywordValue(String keyword) {
        Collection<Double> values = getAllKeywordValues(keyword);
        if (values == null || values.size() != 1) {
            return -0.00123456777d;
        }
        return values.iterator().next().doubleValue();
    }

    public Collection<Double> getAllKeywordValues(String keyword) {
        return getAllKeywordValues(keyword, SampleType.INTEGER);
    }

    @Deprecated
    public Collection<Double> getAllKeywordValues(String keyword, SampleType type) {
        Collection<Double> samples;
        if (isLimited(keyword, type) && (samples = getSamples(keyword, type)) != null) {
            return Collections.unmodifiableCollection(samples);
        }
        return null;
    }

    public Collection<Double> getSamples(String keyword) {
        return getSamples(keyword, SampleType.INTEGER);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
        if (r3 >= 2000) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005a, code lost:
        if (addSample(r11, new com.ibm.icu.text.PluralRules.FixedDecimal(((double) r3) / 10.0d, 1), r2, r0) != false) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0060, code lost:
        addSample(r11, new com.ibm.icu.text.PluralRules.FixedDecimal(1000000.0d, 1), r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0072, code lost:
        if (r3 >= 200) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007c, code lost:
        if (addSample(r11, java.lang.Integer.valueOf(r3), r2, r0) != false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0082, code lost:
        addSample(r11, 1000000, r2, r0);
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<java.lang.Double> getSamples(java.lang.String r11, com.ibm.icu.text.PluralRules.SampleType r12) {
        /*
            r10 = this;
            java.util.Set<java.lang.String> r0 = r10.keywords
            boolean r0 = r0.contains(r11)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.util.TreeSet r0 = new java.util.TreeSet
            r0.<init>()
            com.ibm.icu.text.PluralRules$RuleList r2 = r10.rules
            boolean r2 = r2.hasExplicitBoundingInfo
            if (r2 == 0) goto L_0x002d
            com.ibm.icu.text.PluralRules$RuleList r1 = r10.rules
            com.ibm.icu.text.PluralRules$FixedDecimalSamples r1 = r1.getDecimalSamples(r11, r12)
            if (r1 != 0) goto L_0x0024
            java.util.Set r2 = java.util.Collections.unmodifiableSet(r0)
            goto L_0x002c
        L_0x0024:
            java.util.Set r2 = r1.addSamples(r0)
            java.util.Set r2 = java.util.Collections.unmodifiableSet(r2)
        L_0x002c:
            return r2
        L_0x002d:
            boolean r2 = r10.isLimited(r11, r12)
            if (r2 == 0) goto L_0x0037
            r2 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0039
        L_0x0037:
            r2 = 20
        L_0x0039:
            int[] r3 = com.ibm.icu.text.PluralRules.AnonymousClass2.$SwitchMap$com$ibm$icu$text$PluralRules$SampleType
            int r4 = r12.ordinal()
            r3 = r3[r4]
            r4 = 0
            switch(r3) {
                case 1: goto L_0x006e;
                case 2: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x008d
        L_0x0046:
        L_0x0047:
            r3 = r4
            r4 = 2000(0x7d0, float:2.803E-42)
            r5 = 1
            if (r3 >= r4) goto L_0x0060
            com.ibm.icu.text.PluralRules$FixedDecimal r4 = new com.ibm.icu.text.PluralRules$FixedDecimal
            double r6 = (double) r3
            r8 = 4621819117588971520(0x4024000000000000, double:10.0)
            double r6 = r6 / r8
            r4.<init>(r6, r5)
            boolean r4 = r10.addSample(r11, r4, r2, r0)
            if (r4 != 0) goto L_0x005d
            goto L_0x0060
        L_0x005d:
            int r4 = r3 + 1
            goto L_0x0047
        L_0x0060:
            com.ibm.icu.text.PluralRules$FixedDecimal r3 = new com.ibm.icu.text.PluralRules$FixedDecimal
            r6 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            r3.<init>(r6, r5)
            r10.addSample(r11, r3, r2, r0)
            goto L_0x008d
        L_0x006e:
        L_0x006f:
            r3 = r4
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 >= r4) goto L_0x0082
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r4 = r10.addSample(r11, r4, r2, r0)
            if (r4 != 0) goto L_0x007f
            goto L_0x0082
        L_0x007f:
            int r4 = r3 + 1
            goto L_0x006f
        L_0x0082:
            r3 = 1000000(0xf4240, float:1.401298E-39)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r10.addSample(r11, r3, r2, r0)
        L_0x008d:
            int r3 = r0.size()
            if (r3 != 0) goto L_0x0094
            goto L_0x0098
        L_0x0094:
            java.util.Set r1 = java.util.Collections.unmodifiableSet(r0)
        L_0x0098:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.PluralRules.getSamples(java.lang.String, com.ibm.icu.text.PluralRules$SampleType):java.util.Collection");
    }

    @Deprecated
    public boolean addSample(String keyword, Number sample, int maxCount, Set<Double> result) {
        if (!(sample instanceof FixedDecimal ? select((IFixedDecimal) (FixedDecimal) sample) : select(sample.doubleValue())).equals(keyword)) {
            return true;
        }
        result.add(Double.valueOf(sample.doubleValue()));
        if (maxCount - 1 < 0) {
            return false;
        }
        return true;
    }

    @Deprecated
    public FixedDecimalSamples getDecimalSamples(String keyword, SampleType sampleType) {
        return this.rules.getDecimalSamples(keyword, sampleType);
    }

    public static ULocale[] getAvailableULocales() {
        return Factory.getDefaultFactory().getAvailableULocales();
    }

    public static ULocale getFunctionalEquivalent(ULocale locale, boolean[] isAvailable) {
        return Factory.getDefaultFactory().getFunctionalEquivalent(locale, isAvailable);
    }

    public String toString() {
        return this.rules.toString();
    }

    public boolean equals(Object rhs) {
        return (rhs instanceof PluralRules) && equals((PluralRules) rhs);
    }

    public boolean equals(PluralRules rhs) {
        return rhs != null && toString().equals(rhs.toString());
    }

    public KeywordStatus getKeywordStatus(String keyword, int offset, Set<Double> explicits, Output<Double> uniqueValue) {
        return getKeywordStatus(keyword, offset, explicits, uniqueValue, SampleType.INTEGER);
    }

    @Deprecated
    public KeywordStatus getKeywordStatus(String keyword, int offset, Set<Double> explicits, Output<Double> uniqueValue, SampleType sampleType) {
        if (uniqueValue != null) {
            uniqueValue.value = null;
        }
        if (!this.keywords.contains(keyword)) {
            return KeywordStatus.INVALID;
        }
        if (!isLimited(keyword, sampleType)) {
            return KeywordStatus.UNBOUNDED;
        }
        Collection<Double> values = getSamples(keyword, sampleType);
        int originalSize = values.size();
        if (explicits == null) {
            explicits = Collections.emptySet();
        }
        if (originalSize <= explicits.size()) {
            HashSet<Double> subtractedSet = new HashSet<>(values);
            for (Double explicit : explicits) {
                subtractedSet.remove(Double.valueOf(explicit.doubleValue() - ((double) offset)));
            }
            if (subtractedSet.size() == 0) {
                return KeywordStatus.SUPPRESSED;
            }
            if (uniqueValue != null && subtractedSet.size() == 1) {
                uniqueValue.value = subtractedSet.iterator().next();
            }
            return originalSize == 1 ? KeywordStatus.UNIQUE : KeywordStatus.BOUNDED;
        } else if (originalSize != 1) {
            return KeywordStatus.BOUNDED;
        } else {
            if (uniqueValue != null) {
                uniqueValue.value = values.iterator().next();
            }
            return KeywordStatus.UNIQUE;
        }
    }

    @Deprecated
    public String getRules(String keyword) {
        return this.rules.getRules(keyword);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new NotSerializableException();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new NotSerializableException();
    }

    private Object writeReplace() throws ObjectStreamException {
        return new PluralRulesSerialProxy(toString());
    }

    @Deprecated
    public int compareTo(PluralRules other) {
        return toString().compareTo(other.toString());
    }

    @Deprecated
    public Boolean isLimited(String keyword) {
        return Boolean.valueOf(this.rules.isLimited(keyword, SampleType.INTEGER));
    }

    @Deprecated
    public boolean isLimited(String keyword, SampleType sampleType) {
        return this.rules.isLimited(keyword, sampleType);
    }

    @Deprecated
    public boolean computeLimited(String keyword, SampleType sampleType) {
        return this.rules.computeLimited(keyword, sampleType);
    }
}
