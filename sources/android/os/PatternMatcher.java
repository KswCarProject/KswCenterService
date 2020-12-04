package android.os;

import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;

public class PatternMatcher implements Parcelable {
    public static final Parcelable.Creator<PatternMatcher> CREATOR = new Parcelable.Creator<PatternMatcher>() {
        public PatternMatcher createFromParcel(Parcel source) {
            return new PatternMatcher(source);
        }

        public PatternMatcher[] newArray(int size) {
            return new PatternMatcher[size];
        }
    };
    private static final int MAX_PATTERN_STORAGE = 2048;
    private static final int NO_MATCH = -1;
    private static final int PARSED_MODIFIER_ONE_OR_MORE = -8;
    private static final int PARSED_MODIFIER_RANGE_START = -5;
    private static final int PARSED_MODIFIER_RANGE_STOP = -6;
    private static final int PARSED_MODIFIER_ZERO_OR_MORE = -7;
    private static final int PARSED_TOKEN_CHAR_ANY = -4;
    private static final int PARSED_TOKEN_CHAR_SET_INVERSE_START = -2;
    private static final int PARSED_TOKEN_CHAR_SET_START = -1;
    private static final int PARSED_TOKEN_CHAR_SET_STOP = -3;
    public static final int PATTERN_ADVANCED_GLOB = 3;
    public static final int PATTERN_LITERAL = 0;
    public static final int PATTERN_PREFIX = 1;
    public static final int PATTERN_SIMPLE_GLOB = 2;
    private static final String TAG = "PatternMatcher";
    private static final int TOKEN_TYPE_ANY = 1;
    private static final int TOKEN_TYPE_INVERSE_SET = 3;
    private static final int TOKEN_TYPE_LITERAL = 0;
    private static final int TOKEN_TYPE_SET = 2;
    private static final int[] sParsedPatternScratch = new int[2048];
    private final int[] mParsedPattern;
    private final String mPattern;
    private final int mType;

    public PatternMatcher(String pattern, int type) {
        this.mPattern = pattern;
        this.mType = type;
        if (this.mType == 3) {
            this.mParsedPattern = parseAndVerifyAdvancedPattern(pattern);
        } else {
            this.mParsedPattern = null;
        }
    }

    public final String getPath() {
        return this.mPattern;
    }

    public final int getType() {
        return this.mType;
    }

    public boolean match(String str) {
        return matchPattern(str, this.mPattern, this.mParsedPattern, this.mType);
    }

    public String toString() {
        String type = "? ";
        switch (this.mType) {
            case 0:
                type = "LITERAL: ";
                break;
            case 1:
                type = "PREFIX: ";
                break;
            case 2:
                type = "GLOB: ";
                break;
            case 3:
                type = "ADVANCED: ";
                break;
        }
        return "PatternMatcher{" + type + this.mPattern + "}";
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.mPattern);
        proto.write(1159641169922L, this.mType);
        proto.end(token);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPattern);
        dest.writeInt(this.mType);
        dest.writeIntArray(this.mParsedPattern);
    }

    public PatternMatcher(Parcel src) {
        this.mPattern = src.readString();
        this.mType = src.readInt();
        this.mParsedPattern = src.createIntArray();
    }

    static boolean matchPattern(String match, String pattern, int[] parsedPattern, int type) {
        if (match == null) {
            return false;
        }
        if (type == 0) {
            return pattern.equals(match);
        }
        if (type == 1) {
            return match.startsWith(pattern);
        }
        if (type == 2) {
            return matchGlobPattern(pattern, match);
        }
        if (type == 3) {
            return matchAdvancedPattern(parsedPattern, match);
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0062 A[LOOP:1: B:36:0x0062->B:39:0x006b, LOOP_START, PHI: r5 
      PHI: (r5v7 'im' int) = (r5v1 'im' int), (r5v10 'im' int) binds: [B:83:0x0062, B:39:0x006b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x007e A[LOOP:2: B:47:0x007e->B:50:0x0087, LOOP_START, PHI: r5 
      PHI: (r5v4 'im' int) = (r5v1 'im' int), (r5v6 'im' int) binds: [B:86:0x007e, B:50:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean matchGlobPattern(java.lang.String r13, java.lang.String r14) {
        /*
            int r0 = r13.length()
            r1 = 0
            r2 = 1
            if (r0 > 0) goto L_0x0011
            int r3 = r14.length()
            if (r3 > 0) goto L_0x0010
            r1 = r2
        L_0x0010:
            return r1
        L_0x0011:
            int r3 = r14.length()
            r4 = 0
            r5 = 0
            char r6 = r13.charAt(r1)
        L_0x001b:
            r7 = 42
            r8 = 46
            if (r4 >= r0) goto L_0x00a2
            if (r5 >= r3) goto L_0x00a2
            r9 = r6
            int r4 = r4 + 1
            if (r4 >= r0) goto L_0x002d
            char r10 = r13.charAt(r4)
            goto L_0x002e
        L_0x002d:
            r10 = r1
        L_0x002e:
            r6 = r10
            r10 = 92
            if (r9 != r10) goto L_0x0035
            r11 = r2
            goto L_0x0036
        L_0x0035:
            r11 = r1
        L_0x0036:
            if (r11 == 0) goto L_0x0044
            r9 = r6
            int r4 = r4 + 1
            if (r4 >= r0) goto L_0x0042
            char r12 = r13.charAt(r4)
            goto L_0x0043
        L_0x0042:
            r12 = r1
        L_0x0043:
            r6 = r12
        L_0x0044:
            if (r6 != r7) goto L_0x0095
            if (r11 != 0) goto L_0x007e
            if (r9 != r8) goto L_0x007e
            int r7 = r0 + -1
            if (r4 < r7) goto L_0x004f
            return r2
        L_0x004f:
            int r4 = r4 + 1
            char r6 = r13.charAt(r4)
            if (r6 != r10) goto L_0x0062
            int r4 = r4 + 1
            if (r4 >= r0) goto L_0x0060
            char r7 = r13.charAt(r4)
            goto L_0x0061
        L_0x0060:
            r7 = r1
        L_0x0061:
            r6 = r7
        L_0x0062:
            char r7 = r14.charAt(r5)
            if (r7 != r6) goto L_0x0069
            goto L_0x006d
        L_0x0069:
            int r5 = r5 + 1
            if (r5 < r3) goto L_0x0062
        L_0x006d:
            if (r5 != r3) goto L_0x0070
            return r1
        L_0x0070:
            int r4 = r4 + 1
            if (r4 >= r0) goto L_0x0079
            char r7 = r13.charAt(r4)
            goto L_0x007a
        L_0x0079:
            r7 = r1
        L_0x007a:
            r6 = r7
            int r5 = r5 + 1
            goto L_0x00a0
        L_0x007e:
            char r7 = r14.charAt(r5)
            if (r7 == r9) goto L_0x0085
            goto L_0x0089
        L_0x0085:
            int r5 = r5 + 1
            if (r5 < r3) goto L_0x007e
        L_0x0089:
            int r4 = r4 + 1
            if (r4 >= r0) goto L_0x0092
            char r7 = r13.charAt(r4)
            goto L_0x0093
        L_0x0092:
            r7 = r1
        L_0x0093:
            r6 = r7
            goto L_0x00a0
        L_0x0095:
            if (r9 == r8) goto L_0x009e
            char r7 = r14.charAt(r5)
            if (r7 == r9) goto L_0x009e
            return r1
        L_0x009e:
            int r5 = r5 + 1
        L_0x00a0:
            goto L_0x001b
        L_0x00a2:
            if (r4 < r0) goto L_0x00a7
            if (r5 < r3) goto L_0x00a7
            return r2
        L_0x00a7:
            int r9 = r0 + -2
            if (r4 != r9) goto L_0x00ba
            char r9 = r13.charAt(r4)
            if (r9 != r8) goto L_0x00ba
            int r8 = r4 + 1
            char r8 = r13.charAt(r8)
            if (r8 != r7) goto L_0x00ba
            return r2
        L_0x00ba:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.PatternMatcher.matchGlobPattern(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x014e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized int[] parseAndVerifyAdvancedPattern(java.lang.String r20) {
        /*
            r1 = r20
            java.lang.Class<android.os.PatternMatcher> r2 = android.os.PatternMatcher.class
            monitor-enter(r2)
            r0 = 0
            int r3 = r20.length()     // Catch:{ all -> 0x01f4 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = r6
            r6 = r5
            r5 = r4
            r4 = r0
            r0 = r7
        L_0x0013:
            if (r4 >= r3) goto L_0x01e2
            r9 = 2045(0x7fd, float:2.866E-42)
            if (r5 > r9) goto L_0x01da
            char r9 = r1.charAt(r4)     // Catch:{ all -> 0x01f4 }
            r10 = 0
            r11 = 46
            r12 = 125(0x7d, float:1.75E-43)
            if (r9 == r11) goto L_0x0103
            r11 = 123(0x7b, float:1.72E-43)
            if (r9 == r11) goto L_0x00e0
            if (r9 == r12) goto L_0x00d3
            switch(r9) {
                case 42: goto L_0x00b2;
                case 43: goto L_0x0092;
                default: goto L_0x002d;
            }     // Catch:{ all -> 0x01f4 }
        L_0x002d:
            r11 = -2
            r13 = -1
            switch(r9) {
                case 91: goto L_0x006f;
                case 92: goto L_0x005a;
                case 93: goto L_0x0038;
                default: goto L_0x0032;
            }     // Catch:{ all -> 0x01f4 }
        L_0x0032:
            r10 = 1
        L_0x0033:
            r13 = r5
        L_0x0034:
            r5 = r4
            r4 = r0
            goto L_0x010e
        L_0x0038:
            if (r6 != 0) goto L_0x003c
            r10 = 1
            goto L_0x0033
        L_0x003c:
            int[] r14 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r15 = r5 + -1
            r14 = r14[r15]     // Catch:{ all -> 0x01f4 }
            if (r14 == r13) goto L_0x0052
            if (r14 == r11) goto L_0x0052
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r15 = -3
            r11[r5] = r15     // Catch:{ all -> 0x01f4 }
            r5 = 0
            r0 = 0
            r6 = r5
            goto L_0x0034
        L_0x0052:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r11 = "You must define characters in a set."
            r7.<init>(r11)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x005a:
            int r11 = r4 + 1
            if (r11 >= r3) goto L_0x0067
            int r4 = r4 + 1
            char r11 = r1.charAt(r4)     // Catch:{ all -> 0x01f4 }
            r9 = r11
            r10 = 1
            goto L_0x0033
        L_0x0067:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r11 = "Escape found at end of pattern!"
            r7.<init>(r11)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x006f:
            if (r6 == 0) goto L_0x0073
            r10 = 1
            goto L_0x0033
        L_0x0073:
            int r12 = r4 + 1
            char r12 = r1.charAt(r12)     // Catch:{ all -> 0x01f4 }
            r14 = 94
            if (r12 != r14) goto L_0x0087
            int[] r12 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r12[r5] = r11     // Catch:{ all -> 0x01f4 }
            int r4 = r4 + 1
            r5 = r13
            goto L_0x008e
        L_0x0087:
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r12 = r5 + 1
            r11[r5] = r13     // Catch:{ all -> 0x01f4 }
            r5 = r12
        L_0x008e:
            int r4 = r4 + 1
            r6 = 1
            goto L_0x0013
        L_0x0092:
            if (r6 != 0) goto L_0x0033
            if (r5 == 0) goto L_0x00aa
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + -1
            r11 = r11[r13]     // Catch:{ all -> 0x01f4 }
            boolean r11 = isParsedModifier(r11)     // Catch:{ all -> 0x01f4 }
            if (r11 != 0) goto L_0x00aa
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r14 = -8
            r11[r5] = r14     // Catch:{ all -> 0x01f4 }
            goto L_0x0034
        L_0x00aa:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r11 = "Modifier must follow a token."
            r7.<init>(r11)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x00b2:
            if (r6 != 0) goto L_0x0033
            if (r5 == 0) goto L_0x00cb
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + -1
            r11 = r11[r13]     // Catch:{ all -> 0x01f4 }
            boolean r11 = isParsedModifier(r11)     // Catch:{ all -> 0x01f4 }
            if (r11 != 0) goto L_0x00cb
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r14 = -7
            r11[r5] = r14     // Catch:{ all -> 0x01f4 }
            goto L_0x0034
        L_0x00cb:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r11 = "Modifier must follow a token."
            r7.<init>(r11)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x00d3:
            if (r8 == 0) goto L_0x0033
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r14 = -6
            r11[r5] = r14     // Catch:{ all -> 0x01f4 }
            r5 = 0
        L_0x00dd:
            r8 = r5
            goto L_0x0034
        L_0x00e0:
            if (r6 != 0) goto L_0x0033
            if (r5 == 0) goto L_0x00fb
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + -1
            r11 = r11[r13]     // Catch:{ all -> 0x01f4 }
            boolean r11 = isParsedModifier(r11)     // Catch:{ all -> 0x01f4 }
            if (r11 != 0) goto L_0x00fb
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r14 = -5
            r11[r5] = r14     // Catch:{ all -> 0x01f4 }
            int r4 = r4 + 1
            r5 = 1
            goto L_0x00dd
        L_0x00fb:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r11 = "Modifier must follow a token."
            r7.<init>(r11)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x0103:
            if (r6 != 0) goto L_0x0033
            int[] r11 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r13 = r5 + 1
            r14 = -4
            r11[r5] = r14     // Catch:{ all -> 0x01f4 }
            goto L_0x0034
        L_0x010e:
            if (r6 == 0) goto L_0x014e
            if (r4 == 0) goto L_0x011b
            int[] r0 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r11 = r13 + 1
            r0[r13] = r9     // Catch:{ all -> 0x01f4 }
            r0 = 0
            goto L_0x01d4
        L_0x011b:
            int r0 = r5 + 2
            if (r0 >= r3) goto L_0x013e
            int r0 = r5 + 1
            char r0 = r1.charAt(r0)     // Catch:{ all -> 0x01f4 }
            r11 = 45
            if (r0 != r11) goto L_0x013e
            int r0 = r5 + 2
            char r0 = r1.charAt(r0)     // Catch:{ all -> 0x01f4 }
            r11 = 93
            if (r0 == r11) goto L_0x013e
            r0 = 1
            int[] r4 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r11 = r13 + 1
            r4[r13] = r9     // Catch:{ all -> 0x01f4 }
            int r5 = r5 + 1
            goto L_0x01d4
        L_0x013e:
            int[] r0 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r11 = r13 + 1
            r0[r13] = r9     // Catch:{ all -> 0x01f4 }
            int[] r0 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r12 = r11 + 1
            r0[r11] = r9     // Catch:{ all -> 0x01f4 }
            r0 = r4
            r11 = r12
            goto L_0x01d4
        L_0x014e:
            if (r8 == 0) goto L_0x01c8
            int r0 = r1.indexOf(r12, r5)     // Catch:{ all -> 0x01f4 }
            r11 = r0
            if (r11 < 0) goto L_0x01c0
            java.lang.String r0 = r1.substring(r5, r11)     // Catch:{ all -> 0x01f4 }
            r12 = r0
            r0 = 44
            int r0 = r12.indexOf(r0)     // Catch:{ all -> 0x01f4 }
            r14 = r0
            if (r14 >= 0) goto L_0x016d
            int r0 = java.lang.Integer.parseInt(r12)     // Catch:{ NumberFormatException -> 0x016b }
            r15 = r0
            goto L_0x018b
        L_0x016b:
            r0 = move-exception
            goto L_0x01b7
        L_0x016d:
            java.lang.String r0 = r12.substring(r7, r14)     // Catch:{ NumberFormatException -> 0x016b }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x016b }
            int r15 = r12.length()     // Catch:{ NumberFormatException -> 0x016b }
            int r15 = r15 + -1
            if (r14 != r15) goto L_0x0181
            r15 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x018b
        L_0x0181:
            int r15 = r14 + 1
            java.lang.String r15 = r12.substring(r15)     // Catch:{ NumberFormatException -> 0x016b }
            int r15 = java.lang.Integer.parseInt(r15)     // Catch:{ NumberFormatException -> 0x016b }
        L_0x018b:
            if (r0 > r15) goto L_0x01ad
            int[] r16 = sParsedPatternScratch     // Catch:{ NumberFormatException -> 0x016b }
            int r17 = r13 + 1
            r16[r13] = r0     // Catch:{ NumberFormatException -> 0x01a9 }
            int[] r13 = sParsedPatternScratch     // Catch:{ NumberFormatException -> 0x01a9 }
            int r16 = r17 + 1
            r13[r17] = r15     // Catch:{ NumberFormatException -> 0x01a5 }
            r0 = r11
            r5 = r16
            r19 = r4
            r4 = r0
            r0 = r19
            goto L_0x0013
        L_0x01a5:
            r0 = move-exception
            r13 = r16
            goto L_0x01b7
        L_0x01a9:
            r0 = move-exception
            r13 = r17
            goto L_0x01b7
        L_0x01ad:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ NumberFormatException -> 0x016b }
            r18 = r0
            java.lang.String r0 = "Range quantifier minimum is greater than maximum"
            r7.<init>(r0)     // Catch:{ NumberFormatException -> 0x016b }
            throw r7     // Catch:{ NumberFormatException -> 0x016b }
        L_0x01b7:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r15 = "Range number format incorrect"
            r7.<init>(r15, r0)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x01c0:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r7 = "Range not ended with '}'"
            r0.<init>(r7)     // Catch:{ all -> 0x01f4 }
            throw r0     // Catch:{ all -> 0x01f4 }
        L_0x01c8:
            if (r10 == 0) goto L_0x01d2
            int[] r0 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int r11 = r13 + 1
            r0[r13] = r9     // Catch:{ all -> 0x01f4 }
            r0 = r4
            goto L_0x01d4
        L_0x01d2:
            r0 = r4
            r11 = r13
        L_0x01d4:
            int r4 = r5 + 1
            r5 = r11
            goto L_0x0013
        L_0x01da:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r9 = "Pattern is too large!"
            r7.<init>(r9)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x01e2:
            if (r6 != 0) goto L_0x01ec
            int[] r7 = sParsedPatternScratch     // Catch:{ all -> 0x01f4 }
            int[] r7 = java.util.Arrays.copyOf(r7, r5)     // Catch:{ all -> 0x01f4 }
            monitor-exit(r2)
            return r7
        L_0x01ec:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01f4 }
            java.lang.String r9 = "Set was not terminated!"
            r7.<init>(r9)     // Catch:{ all -> 0x01f4 }
            throw r7     // Catch:{ all -> 0x01f4 }
        L_0x01f4:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.PatternMatcher.parseAndVerifyAdvancedPattern(java.lang.String):int[]");
    }

    private static boolean isParsedModifier(int parsedChar) {
        return parsedChar == -8 || parsedChar == -7 || parsedChar == -6 || parsedChar == -5;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0071 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean matchAdvancedPattern(int[] r22, java.lang.String r23) {
        /*
            r9 = r22
            r0 = 0
            r1 = 0
            int r10 = r9.length
            int r11 = r23.length()
            r2 = 0
            r12 = 0
            r13 = r1
            r1 = r0
            r0 = r12
        L_0x000e:
            r3 = 1
            if (r1 >= r10) goto L_0x0093
            r4 = r9[r1]
            r5 = -4
            r6 = 2
            r14 = -1
            if (r4 == r5) goto L_0x003b
            switch(r4) {
                case -2: goto L_0x0025;
                case -1: goto L_0x0025;
                default: goto L_0x001b;
            }
        L_0x001b:
            r2 = r1
            r5 = 0
            int r1 = r1 + 1
        L_0x001f:
            r16 = r0
            r15 = r2
            r17 = r5
            goto L_0x003f
        L_0x0025:
            if (r4 != r14) goto L_0x002a
            r5 = r6
            goto L_0x002b
        L_0x002a:
            r5 = 3
        L_0x002b:
            int r2 = r1 + 1
        L_0x002d:
            int r1 = r1 + r3
            if (r1 >= r10) goto L_0x0036
            r7 = r9[r1]
            r8 = -3
            if (r7 == r8) goto L_0x0036
            goto L_0x002d
        L_0x0036:
            int r0 = r1 + -1
            int r1 = r1 + 1
            goto L_0x001f
        L_0x003b:
            r5 = 1
            int r1 = r1 + 1
            goto L_0x001f
        L_0x003f:
            if (r1 < r10) goto L_0x0048
            r0 = r3
        L_0x0042:
            r8 = r0
            r18 = r1
        L_0x0045:
            r19 = r4
            goto L_0x006e
        L_0x0048:
            r4 = r9[r1]
            r0 = -5
            if (r4 == r0) goto L_0x0060
            switch(r4) {
                case -8: goto L_0x0059;
                case -7: goto L_0x0052;
                default: goto L_0x0050;
            }
        L_0x0050:
            r0 = r3
            goto L_0x0042
        L_0x0052:
            r3 = 0
            r0 = 2147483647(0x7fffffff, float:NaN)
            int r1 = r1 + 1
            goto L_0x0042
        L_0x0059:
            r3 = 1
            r0 = 2147483647(0x7fffffff, float:NaN)
            int r1 = r1 + 1
            goto L_0x0042
        L_0x0060:
            int r1 = r1 + 1
            r0 = r9[r1]
            int r1 = r1 + r3
            r2 = r9[r1]
            int r1 = r1 + r6
            r3 = r0
            r18 = r1
            r8 = r2
            goto L_0x0045
        L_0x006e:
            r7 = r3
            if (r7 <= r8) goto L_0x0072
            return r12
        L_0x0072:
            r0 = r23
            r1 = r13
            r2 = r11
            r3 = r17
            r4 = r7
            r5 = r8
            r6 = r22
            r20 = r7
            r7 = r15
            r21 = r8
            r8 = r16
            int r0 = matchChars(r0, r1, r2, r3, r4, r5, r6, r7, r8)
            if (r0 != r14) goto L_0x008a
            return r12
        L_0x008a:
            int r13 = r13 + r0
            r2 = r15
            r0 = r16
            r1 = r18
            goto L_0x000e
        L_0x0093:
            if (r1 < r10) goto L_0x0098
            if (r13 < r11) goto L_0x0098
            goto L_0x0099
        L_0x0098:
            r3 = r12
        L_0x0099:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.PatternMatcher.matchAdvancedPattern(int[], java.lang.String):boolean");
    }

    private static int matchChars(String match, int im, int lm, int tokenType, int minRepetition, int maxRepetition, int[] parsedPattern, int tokenStart, int tokenEnd) {
        int matched = 0;
        while (matched < maxRepetition) {
            if (!matchChar(match, im + matched, lm, tokenType, parsedPattern, tokenStart, tokenEnd)) {
                break;
            }
            matched++;
        }
        if (matched < minRepetition) {
            return -1;
        }
        return matched;
    }

    private static boolean matchChar(String match, int im, int lm, int tokenType, int[] parsedPattern, int tokenStart, int tokenEnd) {
        if (im >= lm) {
            return false;
        }
        switch (tokenType) {
            case 0:
                if (match.charAt(im) == parsedPattern[tokenStart]) {
                    return true;
                }
                return false;
            case 1:
                return true;
            case 2:
                for (int i = tokenStart; i < tokenEnd; i += 2) {
                    char matchChar = match.charAt(im);
                    if (matchChar >= parsedPattern[i] && matchChar <= parsedPattern[i + 1]) {
                        return true;
                    }
                }
                return false;
            case 3:
                for (int i2 = tokenStart; i2 < tokenEnd; i2 += 2) {
                    char matchChar2 = match.charAt(im);
                    if (matchChar2 >= parsedPattern[i2] && matchChar2 <= parsedPattern[i2 + 1]) {
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }
}
