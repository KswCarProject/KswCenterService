package com.ibm.icu.text;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.RuleBasedTransliterator;
import java.text.ParsePosition;
import java.util.List;
import java.util.Map;

class TransliteratorParser {
    private static final char ALT_FORWARD_RULE_OP = '→';
    private static final char ALT_FUNCTION = '∆';
    private static final char ALT_FWDREV_RULE_OP = '↔';
    private static final char ALT_REVERSE_RULE_OP = '←';
    private static final char ANCHOR_START = '^';
    private static final char CONTEXT_ANTE = '{';
    private static final char CONTEXT_POST = '}';
    private static final char CURSOR_OFFSET = '@';
    private static final char CURSOR_POS = '|';
    private static final char DOT = '.';
    private static final String DOT_SET = "[^[:Zp:][:Zl:]\\r\\n$]";
    private static final char END_OF_RULE = ';';
    private static final char ESCAPE = '\\';
    private static final char FORWARD_RULE_OP = '>';
    private static final char FUNCTION = '&';
    private static final char FWDREV_RULE_OP = '~';
    private static final String HALF_ENDERS = "=><←→↔;";
    private static final String ID_TOKEN = "::";
    private static final int ID_TOKEN_LEN = 2;
    /* access modifiers changed from: private */
    public static UnicodeSet ILLEGAL_FUNC = new UnicodeSet("[\\^\\(\\.\\*\\+\\?\\{\\}\\|\\@]");
    /* access modifiers changed from: private */
    public static UnicodeSet ILLEGAL_SEG = new UnicodeSet("[\\{\\}\\|\\@]");
    /* access modifiers changed from: private */
    public static UnicodeSet ILLEGAL_TOP = new UnicodeSet("[\\)]");
    private static final char KLEENE_STAR = '*';
    private static final char ONE_OR_MORE = '+';
    private static final String OPERATORS = "=><←→↔";
    private static final char QUOTE = '\'';
    private static final char REVERSE_RULE_OP = '<';
    private static final char RULE_COMMENT_CHAR = '#';
    private static final char SEGMENT_CLOSE = ')';
    private static final char SEGMENT_OPEN = '(';
    private static final char VARIABLE_DEF_OP = '=';
    private static final char ZERO_OR_ONE = '?';
    public UnicodeSet compoundFilter;
    /* access modifiers changed from: private */
    public RuleBasedTransliterator.Data curData;
    public List<RuleBasedTransliterator.Data> dataVector;
    private int direction;
    private int dotStandIn = -1;
    public List<String> idBlockVector;
    /* access modifiers changed from: private */
    public ParseData parseData;
    private List<StringMatcher> segmentObjects;
    private StringBuffer segmentStandins;
    private String undefinedVariableName;
    private char variableLimit;
    /* access modifiers changed from: private */
    public Map<String, char[]> variableNames;
    private char variableNext;
    /* access modifiers changed from: private */
    public List<Object> variablesVector;

    private class ParseData implements SymbolTable {
        private ParseData() {
        }

        public char[] lookup(String name) {
            return (char[]) TransliteratorParser.this.variableNames.get(name);
        }

        public UnicodeMatcher lookupMatcher(int ch) {
            int i = ch - TransliteratorParser.this.curData.variablesBase;
            if (i < 0 || i >= TransliteratorParser.this.variablesVector.size()) {
                return null;
            }
            return (UnicodeMatcher) TransliteratorParser.this.variablesVector.get(i);
        }

        public String parseReference(String text, ParsePosition pos, int limit) {
            int start = pos.getIndex();
            int i = start;
            while (i < limit) {
                char c = text.charAt(i);
                if ((i == start && !UCharacter.isUnicodeIdentifierStart(c)) || !UCharacter.isUnicodeIdentifierPart(c)) {
                    break;
                }
                i++;
            }
            if (i == start) {
                return null;
            }
            pos.setIndex(i);
            return text.substring(start, i);
        }

        public boolean isMatcher(int ch) {
            int i = ch - TransliteratorParser.this.curData.variablesBase;
            if (i < 0 || i >= TransliteratorParser.this.variablesVector.size()) {
                return true;
            }
            return TransliteratorParser.this.variablesVector.get(i) instanceof UnicodeMatcher;
        }

        public boolean isReplacer(int ch) {
            int i = ch - TransliteratorParser.this.curData.variablesBase;
            if (i < 0 || i >= TransliteratorParser.this.variablesVector.size()) {
                return true;
            }
            return TransliteratorParser.this.variablesVector.get(i) instanceof UnicodeReplacer;
        }
    }

    private static abstract class RuleBody {
        /* access modifiers changed from: package-private */
        public abstract String handleNextLine();

        /* access modifiers changed from: package-private */
        public abstract void reset();

        private RuleBody() {
        }

        /* access modifiers changed from: package-private */
        public String nextLine() {
            String s;
            String s2 = handleNextLine();
            if (s2 == null || s2.length() <= 0 || s2.charAt(s2.length() - 1) != '\\') {
                return s2;
            }
            StringBuilder b = new StringBuilder(s2);
            do {
                b.deleteCharAt(b.length() - 1);
                s = handleNextLine();
                if (s == null) {
                    break;
                }
                b.append(s);
                if (s.length() <= 0) {
                    break;
                }
            } while (s.charAt(s.length() - 1) != '\\');
            return b.toString();
        }
    }

    private static class RuleArray extends RuleBody {
        String[] array;
        int i = 0;

        public RuleArray(String[] array2) {
            super();
            this.array = array2;
        }

        public String handleNextLine() {
            if (this.i >= this.array.length) {
                return null;
            }
            String[] strArr = this.array;
            int i2 = this.i;
            this.i = i2 + 1;
            return strArr[i2];
        }

        public void reset() {
            this.i = 0;
        }
    }

    private static class RuleHalf {
        public boolean anchorEnd;
        public boolean anchorStart;
        public int ante;
        public int cursor;
        public int cursorOffset;
        private int cursorOffsetPos;
        private int nextSegmentNumber;
        public int post;
        public String text;

        private RuleHalf() {
            this.cursor = -1;
            this.ante = -1;
            this.post = -1;
            this.cursorOffset = 0;
            this.cursorOffsetPos = 0;
            this.anchorStart = false;
            this.anchorEnd = false;
            this.nextSegmentNumber = 1;
        }

        public int parse(String rule, int pos, int limit, TransliteratorParser parser) {
            int start = pos;
            StringBuffer buf = new StringBuffer();
            int pos2 = parseSection(rule, pos, limit, parser, buf, TransliteratorParser.ILLEGAL_TOP, false);
            this.text = buf.toString();
            if (this.cursorOffset > 0 && this.cursor != this.cursorOffsetPos) {
                TransliteratorParser.syntaxError("Misplaced |", rule, start);
            }
            return pos2;
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x0228, code lost:
            if (r44 == false) goto L_0x0237;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x022e, code lost:
            if (r42.length() != r5) goto L_0x0237;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0230, code lost:
            com.ibm.icu.text.TransliteratorParser.syntaxError("Misplaced quantifier", r10, r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x023b, code lost:
            if (r42.length() != r6) goto L_0x0246;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x023d, code lost:
            r0 = r17;
            r8 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x0240, code lost:
            r27 = r0;
            r24 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:0x024a, code lost:
            if (r42.length() != r4) goto L_0x0250;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x024c, code lost:
            r0 = r18;
            r8 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x0250, code lost:
            r0 = r42.length() - 1;
            r27 = r0;
            r24 = r0 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
            r21 = new com.ibm.icu.text.StringMatcher(r42.toString(), r27, r24, 0, com.ibm.icu.text.TransliteratorParser.access$100(r41));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:0x026f, code lost:
            r19 = 0;
            r20 = Integer.MAX_VALUE;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x0278, code lost:
            if (r2 == '+') goto L_0x0288;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:133:0x027c, code lost:
            if (r2 == '?') goto L_0x0283;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x027e, code lost:
            r1 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x0283, code lost:
            r19 = 0;
            r20 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x0288, code lost:
            r19 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x028b, code lost:
            r28 = r2;
            r0 = new com.ibm.icu.text.Quantifier(r21, r19, r1);
            r13.setLength(r27);
            r29 = r1;
            r13.append(r12.generateStandInFor(r0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x02a3, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:0x02a4, code lost:
            r28 = r2;
            r2 = r27;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x02aa, code lost:
            if (r3 < 50) goto L_0x02ac;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x02ac, code lost:
            r8 = r10.substring(0, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:0x02b2, code lost:
            r8 = android.telecom.Logging.Session.TRUNCATE_STRING + r10.substring(r3 - 50, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x02c9, code lost:
            r1 = r8;
            r30 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:144:0x02d0, code lost:
            if ((r11 - r3) <= 50) goto L_0x02d2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:145:0x02d2, code lost:
            r2 = r10.substring(r3, r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:146:0x02d7, code lost:
            r2 = r10.substring(r3, r3 + 50) + android.telecom.Logging.Session.TRUNCATE_STRING;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:147:0x02ee, code lost:
            r31 = r3;
            r3 = new java.lang.StringBuilder();
            r32 = r4;
            r3.append("Failure in rule: ");
            r3.append(r1);
            r3.append("$$$");
            r3.append(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:148:0x0314, code lost:
            throw new com.ibm.icu.impl.IllegalIcuArgumentException(r3.toString()).initCause(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x015c, code lost:
            r22 = r4;
            r23 = r5;
            r24 = r6;
            r1 = r11;
            r36 = r15;
            r0 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x0166, code lost:
            r11 = r3;
            r15 = r7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int parseSection(java.lang.String r38, int r39, int r40, com.ibm.icu.text.TransliteratorParser r41, java.lang.StringBuffer r42, com.ibm.icu.text.UnicodeSet r43, boolean r44) {
            /*
                r37 = this;
                r9 = r37
                r10 = r38
                r11 = r40
                r12 = r41
                r13 = r42
                r15 = r39
                r0 = 0
                r1 = -1
                r2 = -1
                r3 = -1
                r4 = -1
                r8 = 1
                int[] r7 = new int[r8]
                int r5 = r42.length()
                r16 = r0
                r17 = r1
                r6 = r2
                r18 = r3
                r0 = r39
            L_0x0021:
                if (r0 >= r11) goto L_0x04da
                int r3 = r0 + 1
                char r2 = r10.charAt(r0)
                boolean r0 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r2)
                if (r0 == 0) goto L_0x0032
                r0 = r3
                goto L_0x0021
            L_0x0032:
                java.lang.String r0 = "=><←→↔;"
                int r0 = r0.indexOf(r2)
                if (r0 < 0) goto L_0x0047
                if (r44 == 0) goto L_0x0041
                java.lang.String r0 = "Unclosed segment"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0041:
                r31 = r3
                r32 = r4
                goto L_0x031b
            L_0x0047:
                boolean r0 = r9.anchorEnd
                if (r0 == 0) goto L_0x0050
                java.lang.String r0 = "Malformed variable reference"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0050:
                int r0 = r3 + -1
                boolean r0 = com.ibm.icu.text.UnicodeSet.resemblesPattern(r10, r0)
                r1 = 0
                if (r0 == 0) goto L_0x0078
                if (r16 != 0) goto L_0x0061
                java.text.ParsePosition r0 = new java.text.ParsePosition
                r0.<init>(r1)
                goto L_0x0063
            L_0x0061:
                r0 = r16
            L_0x0063:
                int r1 = r3 + -1
                r0.setIndex(r1)
                char r1 = r12.parseSet(r10, r0)
                r13.append(r1)
                int r1 = r0.getIndex()
                r16 = r0
                r0 = r1
                goto L_0x0021
            L_0x0078:
                r0 = 92
                r8 = -1
                if (r2 != r0) goto L_0x009d
                if (r3 != r11) goto L_0x0084
                java.lang.String r0 = "Trailing backslash"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0084:
                r7[r1] = r3
                int r0 = com.ibm.icu.impl.Utility.unescapeAt(r10, r7)
                r1 = r7[r1]
                if (r0 != r8) goto L_0x0093
                java.lang.String r3 = "Malformed escape"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r3, r10, r15)
            L_0x0093:
                r12.checkVariableRange(r0, r10, r15)
                com.ibm.icu.text.UTF16.append(r13, r0)
                r0 = r1
            L_0x009b:
                r8 = 1
                goto L_0x0021
            L_0x009d:
                r0 = 39
                if (r2 != r0) goto L_0x00e4
                int r1 = r10.indexOf(r0, r3)
                if (r1 != r3) goto L_0x00ad
                r13.append(r2)
                int r0 = r3 + 1
                goto L_0x009b
            L_0x00ad:
                int r17 = r42.length()
            L_0x00b1:
                if (r1 >= 0) goto L_0x00b8
                java.lang.String r8 = "Unterminated quote"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r8, r10, r15)
            L_0x00b8:
                java.lang.String r8 = r10.substring(r3, r1)
                r13.append(r8)
                int r3 = r1 + 1
                if (r3 >= r11) goto L_0x00d0
                char r8 = r10.charAt(r3)
                if (r8 != r0) goto L_0x00d0
                int r8 = r3 + 1
                int r1 = r10.indexOf(r0, r8)
                goto L_0x00b1
            L_0x00d0:
                int r6 = r42.length()
                r0 = r17
            L_0x00d6:
                if (r0 >= r6) goto L_0x00e2
                char r1 = r13.charAt(r0)
                r12.checkVariableRange(r1, r10, r15)
                int r0 = r0 + 1
                goto L_0x00d6
            L_0x00e2:
                r0 = r3
                goto L_0x009b
            L_0x00e4:
                r12.checkVariableRange(r2, r10, r15)
                r1 = r43
                boolean r21 = r1.contains((int) r2)
                if (r21 == 0) goto L_0x0108
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r0 = "Illegal character '"
                r8.append(r0)
                r8.append(r2)
                r0 = 39
                r8.append(r0)
                java.lang.String r0 = r8.toString()
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0108:
                r0 = 36
                if (r2 == r0) goto L_0x0449
                r0 = 38
                if (r2 == r0) goto L_0x0389
                r0 = 46
                if (r2 == r0) goto L_0x03b7
                r0 = 94
                if (r2 == r0) goto L_0x0397
                r0 = 8710(0x2206, float:1.2205E-41)
                if (r2 == r0) goto L_0x0389
                switch(r2) {
                    case 40: goto L_0x0328;
                    case 41: goto L_0x0315;
                    case 42: goto L_0x0228;
                    case 43: goto L_0x0228;
                    default: goto L_0x011f;
                }
            L_0x011f:
                switch(r2) {
                    case 63: goto L_0x0228;
                    case 64: goto L_0x019a;
                    default: goto L_0x0122;
                }
            L_0x0122:
                switch(r2) {
                    case 123: goto L_0x018a;
                    case 124: goto L_0x017a;
                    case 125: goto L_0x016a;
                    default: goto L_0x0125;
                }
            L_0x0125:
                r0 = 33
                if (r2 < r0) goto L_0x0159
                r0 = 126(0x7e, float:1.77E-43)
                if (r2 > r0) goto L_0x0159
                r0 = 48
                if (r2 < r0) goto L_0x0135
                r0 = 57
                if (r2 <= r0) goto L_0x0159
            L_0x0135:
                r0 = 65
                if (r2 < r0) goto L_0x013d
                r0 = 90
                if (r2 <= r0) goto L_0x0159
            L_0x013d:
                r0 = 97
                if (r2 < r0) goto L_0x0145
                r0 = 122(0x7a, float:1.71E-43)
                if (r2 <= r0) goto L_0x0159
            L_0x0145:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r8 = "Unquoted "
                r0.append(r8)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0159:
                r13.append(r2)
            L_0x015c:
                r22 = r4
                r23 = r5
                r24 = r6
                r1 = r11
                r36 = r15
                r0 = 1
            L_0x0166:
                r11 = r3
                r15 = r7
                goto L_0x045d
            L_0x016a:
                int r0 = r9.post
                if (r0 < 0) goto L_0x0173
                java.lang.String r0 = "Multiple post contexts"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0173:
                int r0 = r42.length()
                r9.post = r0
                goto L_0x015c
            L_0x017a:
                int r0 = r9.cursor
                if (r0 < 0) goto L_0x0183
                java.lang.String r0 = "Multiple cursors"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0183:
                int r0 = r42.length()
                r9.cursor = r0
                goto L_0x015c
            L_0x018a:
                int r0 = r9.ante
                if (r0 < 0) goto L_0x0193
                java.lang.String r0 = "Multiple ante contexts"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x0193:
                int r0 = r42.length()
                r9.ante = r0
                goto L_0x015c
            L_0x019a:
                int r0 = r9.cursorOffset
                if (r0 >= 0) goto L_0x01bf
                int r0 = r42.length()
                if (r0 <= 0) goto L_0x01b8
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r8 = "Misplaced "
                r0.append(r8)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x01b8:
                int r0 = r9.cursorOffset
                r8 = 1
                int r0 = r0 - r8
                r9.cursorOffset = r0
                goto L_0x015c
            L_0x01bf:
                int r0 = r9.cursorOffset
                if (r0 <= 0) goto L_0x01eb
                int r0 = r42.length()
                int r8 = r9.cursorOffsetPos
                if (r0 != r8) goto L_0x01cf
                int r0 = r9.cursor
                if (r0 < 0) goto L_0x01e3
            L_0x01cf:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r8 = "Misplaced "
                r0.append(r8)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
            L_0x01e3:
                int r0 = r9.cursorOffset
                r8 = 1
                int r0 = r0 + r8
                r9.cursorOffset = r0
                goto L_0x015c
            L_0x01eb:
                int r0 = r9.cursor
                if (r0 != 0) goto L_0x01fa
                int r0 = r42.length()
                if (r0 != 0) goto L_0x01fa
                r0 = -1
                r9.cursorOffset = r0
                goto L_0x015c
            L_0x01fa:
                int r0 = r9.cursor
                if (r0 >= 0) goto L_0x0212
                int r0 = r42.length()
                r9.cursorOffsetPos = r0
                r0 = 1
                r9.cursorOffset = r0
                r22 = r4
                r23 = r5
                r24 = r6
                r1 = r11
                r36 = r15
                goto L_0x0166
            L_0x0212:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r8 = "Misplaced "
                r0.append(r8)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
                goto L_0x015c
            L_0x0228:
                if (r44 == 0) goto L_0x0237
                int r0 = r42.length()
                if (r0 != r5) goto L_0x0237
                java.lang.String r0 = "Misplaced quantifier"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
                goto L_0x015c
            L_0x0237:
                int r0 = r42.length()
                if (r0 != r6) goto L_0x0246
                r0 = r17
                r8 = r6
            L_0x0240:
                r27 = r0
                r24 = r8
                r8 = 1
                goto L_0x025c
            L_0x0246:
                int r0 = r42.length()
                if (r0 != r4) goto L_0x0250
                r0 = r18
                r8 = r4
                goto L_0x0240
            L_0x0250:
                int r0 = r42.length()
                r8 = 1
                int r0 = r0 - r8
                int r19 = r0 + 1
                r27 = r0
                r24 = r19
            L_0x025c:
                com.ibm.icu.text.StringMatcher r0 = new com.ibm.icu.text.StringMatcher     // Catch:{ RuntimeException -> 0x02a3 }
                java.lang.String r22 = r42.toString()     // Catch:{ RuntimeException -> 0x02a3 }
                r25 = 0
                com.ibm.icu.text.RuleBasedTransliterator$Data r26 = r41.curData     // Catch:{ RuntimeException -> 0x02a3 }
                r21 = r0
                r23 = r27
                r21.<init>(r22, r23, r24, r25, r26)     // Catch:{ RuntimeException -> 0x02a3 }
                r19 = 0
                r20 = 2147483647(0x7fffffff, float:NaN)
                r8 = 43
                if (r2 == r8) goto L_0x0288
                r8 = 63
                if (r2 == r8) goto L_0x0283
            L_0x027e:
                r8 = r19
                r1 = r20
                goto L_0x028b
            L_0x0283:
                r19 = 0
                r20 = 1
                goto L_0x027e
            L_0x0288:
                r19 = 1
                goto L_0x027e
            L_0x028b:
                r28 = r2
                com.ibm.icu.text.Quantifier r2 = new com.ibm.icu.text.Quantifier
                r2.<init>(r0, r8, r1)
                r0 = r2
                r2 = r27
                r13.setLength(r2)
                r29 = r1
                char r1 = r12.generateStandInFor(r0)
                r13.append(r1)
                goto L_0x015c
            L_0x02a3:
                r0 = move-exception
                r28 = r2
                r2 = r27
                r1 = 50
                if (r3 >= r1) goto L_0x02b2
                r8 = 0
                java.lang.String r8 = r10.substring(r8, r3)
                goto L_0x02c9
            L_0x02b2:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r1 = "..."
                r8.append(r1)
                int r1 = r3 + -50
                java.lang.String r1 = r10.substring(r1, r3)
                r8.append(r1)
                java.lang.String r8 = r8.toString()
            L_0x02c9:
                r1 = r8
                int r8 = r11 - r3
                r30 = r2
                r2 = 50
                if (r8 > r2) goto L_0x02d7
                java.lang.String r2 = r10.substring(r3, r11)
                goto L_0x02ee
            L_0x02d7:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                int r8 = r3 + 50
                java.lang.String r8 = r10.substring(r3, r8)
                r2.append(r8)
                java.lang.String r8 = "..."
                r2.append(r8)
                java.lang.String r2 = r2.toString()
            L_0x02ee:
                com.ibm.icu.impl.IllegalIcuArgumentException r8 = new com.ibm.icu.impl.IllegalIcuArgumentException
                r31 = r3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r32 = r4
                java.lang.String r4 = "Failure in rule: "
                r3.append(r4)
                r3.append(r1)
                java.lang.String r4 = "$$$"
                r3.append(r4)
                r3.append(r2)
                java.lang.String r3 = r3.toString()
                r8.<init>(r3)
                com.ibm.icu.impl.IllegalIcuArgumentException r3 = r8.initCause(r0)
                throw r3
            L_0x0315:
                r28 = r2
                r31 = r3
                r32 = r4
            L_0x031b:
                r23 = r5
                r24 = r6
                r1 = r11
                r6 = r15
                r0 = r31
                r22 = r32
                r15 = r7
                goto L_0x04e3
            L_0x0328:
                r28 = r2
                r31 = r3
                r32 = r4
                int r0 = r42.length()
                int r1 = r9.nextSegmentNumber
                int r2 = r1 + 1
                r9.nextSegmentNumber = r2
                r8 = r1
                com.ibm.icu.text.UnicodeSet r19 = com.ibm.icu.text.TransliteratorParser.ILLEGAL_SEG
                r20 = 1
                r1 = r37
                r21 = r28
                r2 = r38
                r4 = r31
                r3 = r4
                r33 = r4
                r22 = r32
                r4 = r40
                r23 = r5
                r5 = r41
                r24 = r6
                r6 = r42
                r14 = r7
                r7 = r19
                r11 = r8
                r34 = r14
                r14 = 1
                r8 = r20
                int r1 = r1.parseSection(r2, r3, r4, r5, r6, r7, r8)
                com.ibm.icu.text.StringMatcher r2 = new com.ibm.icu.text.StringMatcher
                java.lang.String r3 = r13.substring(r0)
                com.ibm.icu.text.RuleBasedTransliterator$Data r4 = r41.curData
                r2.<init>(r3, r11, r4)
                r12.setSegmentObject(r11, r2)
                r13.setLength(r0)
                char r3 = r12.getSegmentStandin(r11)
                r13.append(r3)
                r0 = r1
                r5 = r14
                r6 = r15
                r4 = r22
                r15 = r34
                r1 = r40
                goto L_0x04d0
            L_0x0389:
                r21 = r2
                r33 = r3
                r22 = r4
                r23 = r5
                r24 = r6
                r34 = r7
                r14 = 1
                goto L_0x03d7
            L_0x0397:
                r21 = r2
                r33 = r3
                r22 = r4
                r23 = r5
                r24 = r6
                r34 = r7
                r14 = 1
                int r0 = r42.length()
                if (r0 != 0) goto L_0x03b1
                boolean r0 = r9.anchorStart
                if (r0 != 0) goto L_0x03b1
                r9.anchorStart = r14
                goto L_0x03cc
            L_0x03b1:
                java.lang.String r0 = "Misplaced anchor start"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r0, r10, r15)
                goto L_0x03cc
            L_0x03b7:
                r21 = r2
                r33 = r3
                r22 = r4
                r23 = r5
                r24 = r6
                r34 = r7
                r14 = 1
                char r0 = r41.getDotStandIn()
                r13.append(r0)
            L_0x03cc:
                r0 = r14
                r36 = r15
                r11 = r33
                r15 = r34
                r1 = r40
                goto L_0x045d
            L_0x03d7:
                r11 = r33
                r1 = 0
                r34[r1] = r11
                r8 = r34
                com.ibm.icu.text.TransliteratorIDParser$SingleID r0 = com.ibm.icu.text.TransliteratorIDParser.parseFilterID(r10, r8)
                if (r0 == 0) goto L_0x03ec
                r1 = 40
                boolean r1 = com.ibm.icu.impl.Utility.parseChar(r10, r8, r1)
                if (r1 != 0) goto L_0x03f1
            L_0x03ec:
                java.lang.String r1 = "Invalid function"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r1, r10, r15)
            L_0x03f1:
                com.ibm.icu.text.Transliterator r7 = r0.getInstance()
                if (r7 != 0) goto L_0x03fc
                java.lang.String r1 = "Invalid function ID"
                com.ibm.icu.text.TransliteratorParser.syntaxError(r1, r10, r15)
            L_0x03fc:
                int r6 = r42.length()
                r1 = 0
                r3 = r8[r1]
                com.ibm.icu.text.UnicodeSet r19 = com.ibm.icu.text.TransliteratorParser.ILLEGAL_FUNC
                r20 = 1
                r1 = r37
                r2 = r38
                r4 = r40
                r5 = r41
                r14 = r6
                r6 = r42
                r35 = r0
                r0 = r7
                r7 = r19
                r36 = r15
                r15 = r8
                r8 = r20
                int r1 = r1.parseSection(r2, r3, r4, r5, r6, r7, r8)
                com.ibm.icu.text.FunctionReplacer r2 = new com.ibm.icu.text.FunctionReplacer
                com.ibm.icu.text.StringReplacer r3 = new com.ibm.icu.text.StringReplacer
                java.lang.String r4 = r13.substring(r14)
                com.ibm.icu.text.RuleBasedTransliterator$Data r5 = r41.curData
                r3.<init>(r4, r5)
                r2.<init>(r0, r3)
                r13.setLength(r14)
                char r3 = r12.generateStandInFor(r2)
                r13.append(r3)
                r0 = r1
                r4 = r22
                r6 = r36
                r1 = r40
                r5 = 1
                goto L_0x04d0
            L_0x0449:
                r21 = r2
                r11 = r3
                r22 = r4
                r23 = r5
                r24 = r6
                r36 = r15
                r15 = r7
                r1 = r40
                if (r11 != r1) goto L_0x0465
                r0 = 1
                r9.anchorEnd = r0
            L_0x045d:
                r5 = r0
                r0 = r11
                r4 = r22
                r6 = r36
                goto L_0x04d0
            L_0x0465:
                r0 = 1
                char r2 = r10.charAt(r11)
                r3 = 10
                int r4 = com.ibm.icu.lang.UCharacter.digit(r2, r3)
                if (r4 < r0) goto L_0x0496
                r0 = 9
                if (r4 > r0) goto L_0x0496
                r5 = 0
                r15[r5] = r11
                int r0 = com.ibm.icu.impl.Utility.parseNumber(r10, r15, r3)
                if (r0 >= 0) goto L_0x0487
                java.lang.String r3 = "Undefined segment reference"
                r6 = r36
                com.ibm.icu.text.TransliteratorParser.syntaxError(r3, r10, r6)
                goto L_0x0489
            L_0x0487:
                r6 = r36
            L_0x0489:
                r3 = r15[r5]
                char r4 = r12.getSegmentStandin(r0)
                r13.append(r4)
                r4 = r22
                r5 = 1
                goto L_0x04cf
            L_0x0496:
                r6 = r36
                r5 = 0
                if (r16 != 0) goto L_0x04a3
                java.text.ParsePosition r0 = new java.text.ParsePosition
                r0.<init>(r5)
                r16 = r0
                goto L_0x04a5
            L_0x04a3:
                r0 = r16
            L_0x04a5:
                r0.setIndex(r11)
                com.ibm.icu.text.TransliteratorParser$ParseData r3 = r41.parseData
                java.lang.String r3 = r3.parseReference(r10, r0, r1)
                if (r3 != 0) goto L_0x04bc
                r5 = 1
                r9.anchorEnd = r5
                r16 = r0
                r0 = r11
                r4 = r22
                goto L_0x04d0
            L_0x04bc:
                r5 = 1
                int r7 = r0.getIndex()
                int r18 = r42.length()
                r12.appendVariableDef(r3, r13)
                int r4 = r42.length()
                r16 = r0
                r3 = r7
            L_0x04cf:
                r0 = r3
            L_0x04d0:
                r11 = r1
                r8 = r5
                r7 = r15
                r5 = r23
                r15 = r6
                r6 = r24
                goto L_0x0021
            L_0x04da:
                r22 = r4
                r23 = r5
                r24 = r6
                r1 = r11
                r6 = r15
                r15 = r7
            L_0x04e3:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorParser.RuleHalf.parseSection(java.lang.String, int, int, com.ibm.icu.text.TransliteratorParser, java.lang.StringBuffer, com.ibm.icu.text.UnicodeSet, boolean):int");
        }

        /* access modifiers changed from: package-private */
        public void removeContext() {
            this.text = this.text.substring(this.ante < 0 ? 0 : this.ante, this.post < 0 ? this.text.length() : this.post);
            this.post = -1;
            this.ante = -1;
            this.anchorEnd = false;
            this.anchorStart = false;
        }

        public boolean isValidOutput(TransliteratorParser parser) {
            int i = 0;
            while (i < this.text.length()) {
                int c = UTF16.charAt(this.text, i);
                i += UTF16.getCharCount(c);
                if (!parser.parseData.isReplacer(c)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isValidInput(TransliteratorParser parser) {
            int i = 0;
            while (i < this.text.length()) {
                int c = UTF16.charAt(this.text, i);
                i += UTF16.getCharCount(c);
                if (!parser.parseData.isMatcher(c)) {
                    return false;
                }
            }
            return true;
        }
    }

    public void parse(String rules, int dir) {
        parseRules(new RuleArray(new String[]{rules}), dir);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0209 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x022c A[LOOP:3: B:129:0x0224->B:131:0x022c, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x025d A[Catch:{ IllegalArgumentException -> 0x02ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x027e A[Catch:{ IllegalArgumentException -> 0x02ad }, LOOP:4: B:149:0x0276->B:151:0x027e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x02ba  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x02ec A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x02ed  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x01d8 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseRules(com.ibm.icu.text.TransliteratorParser.RuleBody r22, int r23) {
        /*
            r21 = this;
            r1 = r21
            r0 = 1
            r2 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r1.dataVector = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r1.idBlockVector = r3
            r3 = 0
            r1.curData = r3
            r4 = r23
            r1.direction = r4
            r1.compoundFilter = r3
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r1.variablesVector = r5
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r1.variableNames = r5
            com.ibm.icu.text.TransliteratorParser$ParseData r5 = new com.ibm.icu.text.TransliteratorParser$ParseData
            r5.<init>()
            r1.parseData = r5
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r6 = 0
            r22.reset()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r1.compoundFilter = r3
            r9 = r6
            r6 = r2
            r2 = r0
            r0 = -1
        L_0x0044:
            java.lang.String r10 = r22.nextLine()
            r11 = 1
            r12 = 0
            if (r10 != 0) goto L_0x0051
            r3 = r0
            r15 = r9
            goto L_0x01e8
        L_0x0051:
            r13 = 0
            int r14 = r10.length()
            r15 = r9
            r9 = r2
            r2 = r0
        L_0x0059:
            if (r13 >= r14) goto L_0x0308
            int r0 = r13 + 1
            char r13 = r10.charAt(r13)
            boolean r16 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r13)
            if (r16 == 0) goto L_0x0068
            goto L_0x0083
        L_0x0068:
            r8 = 35
            if (r13 != r8) goto L_0x007e
            java.lang.String r8 = "\n"
            int r8 = r10.indexOf(r8, r0)
            int r0 = r8 + 1
            if (r0 != 0) goto L_0x0083
            r19 = r2
            r11 = r3
            r16 = -1
            goto L_0x030d
        L_0x007e:
            r8 = 59
            if (r13 != r8) goto L_0x0085
        L_0x0083:
            r13 = r0
            goto L_0x0059
        L_0x0085:
            int r6 = r6 + 1
            int r8 = r0 + -1
            int r0 = r8 + 2
            int r0 = r0 + r11
            if (r0 > r14) goto L_0x0177
            java.lang.String r0 = "::"
            r3 = 2
            boolean r0 = r10.regionMatches(r8, r0, r12, r3)     // Catch:{ IllegalArgumentException -> 0x0170 }
            if (r0 == 0) goto L_0x0177
            int r8 = r8 + 2
            char r0 = r10.charAt(r8)     // Catch:{ IllegalArgumentException -> 0x0170 }
            r13 = r0
        L_0x009e:
            boolean r0 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r13)     // Catch:{ IllegalArgumentException -> 0x0170 }
            if (r0 == 0) goto L_0x00b6
            if (r8 >= r14) goto L_0x00b6
            int r8 = r8 + 1
            char r0 = r10.charAt(r8)     // Catch:{ IllegalArgumentException -> 0x00ae }
            r13 = r0
            goto L_0x009e
        L_0x00ae:
            r0 = move-exception
            r19 = r2
            r2 = r9
            r16 = -1
            goto L_0x01d3
        L_0x00b6:
            int[] r0 = new int[r11]     // Catch:{ IllegalArgumentException -> 0x0170 }
            r0[r12] = r8     // Catch:{ IllegalArgumentException -> 0x0170 }
            if (r9 != 0) goto L_0x00d7
            com.ibm.icu.text.RuleBasedTransliterator$Data r3 = r1.curData     // Catch:{ IllegalArgumentException -> 0x00ae }
            if (r3 == 0) goto L_0x00d6
            int r3 = r1.direction     // Catch:{ IllegalArgumentException -> 0x00ae }
            if (r3 != 0) goto L_0x00cc
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r3 = r1.dataVector     // Catch:{ IllegalArgumentException -> 0x00ae }
            com.ibm.icu.text.RuleBasedTransliterator$Data r11 = r1.curData     // Catch:{ IllegalArgumentException -> 0x00ae }
            r3.add(r11)     // Catch:{ IllegalArgumentException -> 0x00ae }
            goto L_0x00d3
        L_0x00cc:
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r3 = r1.dataVector     // Catch:{ IllegalArgumentException -> 0x00ae }
            com.ibm.icu.text.RuleBasedTransliterator$Data r11 = r1.curData     // Catch:{ IllegalArgumentException -> 0x00ae }
            r3.add(r12, r11)     // Catch:{ IllegalArgumentException -> 0x00ae }
        L_0x00d3:
            r3 = 0
            r1.curData = r3     // Catch:{ IllegalArgumentException -> 0x00ae }
        L_0x00d6:
            r9 = 1
        L_0x00d7:
            int r3 = r1.direction     // Catch:{ IllegalArgumentException -> 0x0170 }
            com.ibm.icu.text.TransliteratorIDParser$SingleID r3 = com.ibm.icu.text.TransliteratorIDParser.parseSingleID(r10, r0, r3)     // Catch:{ IllegalArgumentException -> 0x0170 }
            r11 = r0[r12]     // Catch:{ IllegalArgumentException -> 0x0170 }
            if (r11 == r8) goto L_0x0116
            r11 = 59
            boolean r16 = com.ibm.icu.impl.Utility.parseChar(r10, r0, r11)     // Catch:{ IllegalArgumentException -> 0x00ae }
            if (r16 == 0) goto L_0x0116
            int r11 = r1.direction     // Catch:{ IllegalArgumentException -> 0x00ae }
            if (r11 != 0) goto L_0x00fe
            java.lang.String r11 = r3.canonID     // Catch:{ IllegalArgumentException -> 0x00ae }
            r7.append(r11)     // Catch:{ IllegalArgumentException -> 0x00ae }
            r11 = 59
            r7.append(r11)     // Catch:{ IllegalArgumentException -> 0x00ae }
        L_0x00f7:
            r19 = r2
            r20 = r3
            r16 = -1
            goto L_0x015e
        L_0x00fe:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x00ae }
            r11.<init>()     // Catch:{ IllegalArgumentException -> 0x00ae }
            java.lang.String r12 = r3.canonID     // Catch:{ IllegalArgumentException -> 0x00ae }
            r11.append(r12)     // Catch:{ IllegalArgumentException -> 0x00ae }
            r12 = 59
            r11.append(r12)     // Catch:{ IllegalArgumentException -> 0x00ae }
            java.lang.String r11 = r11.toString()     // Catch:{ IllegalArgumentException -> 0x00ae }
            r12 = 0
            r7.insert(r12, r11)     // Catch:{ IllegalArgumentException -> 0x00ae }
            goto L_0x00f7
        L_0x0116:
            r11 = 1
            int[] r12 = new int[r11]     // Catch:{ IllegalArgumentException -> 0x0170 }
            r11 = 0
            r16 = -1
            r12[r11] = r16     // Catch:{ IllegalArgumentException -> 0x016c }
            r11 = r12
            int r12 = r1.direction     // Catch:{ IllegalArgumentException -> 0x016c }
            r19 = r2
            r2 = 0
            com.ibm.icu.text.UnicodeSet r12 = com.ibm.icu.text.TransliteratorIDParser.parseGlobalFilter(r10, r0, r12, r11, r2)     // Catch:{ IllegalArgumentException -> 0x01af }
            r2 = r12
            if (r2 == 0) goto L_0x0157
            r12 = 59
            boolean r12 = com.ibm.icu.impl.Utility.parseChar(r10, r0, r12)     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r12 == 0) goto L_0x0157
            int r12 = r1.direction     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r12 != 0) goto L_0x0139
            r12 = 1
            goto L_0x013a
        L_0x0139:
            r12 = 0
        L_0x013a:
            r17 = 0
            r18 = r11[r17]     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r18 != 0) goto L_0x0144
            r20 = r3
            r3 = 1
            goto L_0x0147
        L_0x0144:
            r20 = r3
            r3 = 0
        L_0x0147:
            if (r12 != r3) goto L_0x015e
            com.ibm.icu.text.UnicodeSet r3 = r1.compoundFilter     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r3 == 0) goto L_0x0152
            java.lang.String r3 = "Multiple global filters"
            syntaxError(r3, r10, r8)     // Catch:{ IllegalArgumentException -> 0x01af }
        L_0x0152:
            r1.compoundFilter = r2     // Catch:{ IllegalArgumentException -> 0x01af }
            r3 = r6
            r2 = r3
            goto L_0x0160
        L_0x0157:
            r20 = r3
            java.lang.String r3 = "Invalid ::ID"
            syntaxError(r3, r10, r8)     // Catch:{ IllegalArgumentException -> 0x01af }
        L_0x015e:
            r2 = r19
        L_0x0160:
            r3 = 0
            r11 = r0[r3]     // Catch:{ IllegalArgumentException -> 0x0168 }
            r0 = r11
            r19 = r2
            goto L_0x01c9
        L_0x0168:
            r0 = move-exception
            r19 = r2
            goto L_0x01b0
        L_0x016c:
            r0 = move-exception
            r19 = r2
            goto L_0x0175
        L_0x0170:
            r0 = move-exception
            r19 = r2
            r16 = -1
        L_0x0175:
            r2 = r9
            goto L_0x01d3
        L_0x0177:
            r19 = r2
            r16 = -1
            if (r9 == 0) goto L_0x01b2
            int r0 = r1.direction     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r0 != 0) goto L_0x018c
            java.util.List<java.lang.String> r0 = r1.idBlockVector     // Catch:{ IllegalArgumentException -> 0x01af }
            java.lang.String r2 = r7.toString()     // Catch:{ IllegalArgumentException -> 0x01af }
            r0.add(r2)     // Catch:{ IllegalArgumentException -> 0x01af }
            r3 = 0
            goto L_0x0196
        L_0x018c:
            java.util.List<java.lang.String> r0 = r1.idBlockVector     // Catch:{ IllegalArgumentException -> 0x01af }
            java.lang.String r2 = r7.toString()     // Catch:{ IllegalArgumentException -> 0x01af }
            r3 = 0
            r0.add(r3, r2)     // Catch:{ IllegalArgumentException -> 0x01af }
        L_0x0196:
            int r0 = r7.length()     // Catch:{ IllegalArgumentException -> 0x01af }
            r7.delete(r3, r0)     // Catch:{ IllegalArgumentException -> 0x01af }
            r9 = 0
            com.ibm.icu.text.RuleBasedTransliterator$Data r0 = new com.ibm.icu.text.RuleBasedTransliterator$Data     // Catch:{ IllegalArgumentException -> 0x01af }
            r0.<init>()     // Catch:{ IllegalArgumentException -> 0x01af }
            r1.curData = r0     // Catch:{ IllegalArgumentException -> 0x01af }
            r0 = 61440(0xf000, float:8.6096E-41)
            r2 = 63743(0xf8ff, float:8.9323E-41)
            r1.setVariableRange(r0, r2)     // Catch:{ IllegalArgumentException -> 0x01af }
            goto L_0x01b2
        L_0x01af:
            r0 = move-exception
        L_0x01b0:
            r2 = r9
            goto L_0x01d3
        L_0x01b2:
            boolean r0 = resemblesPragma(r10, r8, r14)     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r0 == 0) goto L_0x01c5
            int r0 = r1.parsePragma(r10, r8, r14)     // Catch:{ IllegalArgumentException -> 0x01af }
            if (r0 >= 0) goto L_0x01c3
            java.lang.String r2 = "Unrecognized pragma"
            syntaxError(r2, r10, r8)     // Catch:{ IllegalArgumentException -> 0x01af }
        L_0x01c3:
            goto L_0x01c9
        L_0x01c5:
            int r0 = r1.parseRule(r10, r8, r14)     // Catch:{ IllegalArgumentException -> 0x01af }
        L_0x01c9:
            r13 = r0
            r2 = r19
            r11 = 0
            r12 = 0
            r17 = 1
            goto L_0x0303
        L_0x01d3:
            r3 = 30
            if (r15 != r3) goto L_0x02ed
            com.ibm.icu.impl.IllegalIcuArgumentException r3 = new com.ibm.icu.impl.IllegalIcuArgumentException
            java.lang.String r9 = "\nMore than 30 errors; further messages squelched"
            r3.<init>(r9)
            r3.initCause(r0)
            r5.add(r3)
            r3 = r19
        L_0x01e8:
            if (r2 == 0) goto L_0x0209
            int r0 = r7.length()
            if (r0 <= 0) goto L_0x0209
            int r0 = r1.direction
            if (r0 != 0) goto L_0x01fe
            java.util.List<java.lang.String> r0 = r1.idBlockVector
            java.lang.String r8 = r7.toString()
            r0.add(r8)
            goto L_0x0223
        L_0x01fe:
            java.util.List<java.lang.String> r0 = r1.idBlockVector
            java.lang.String r8 = r7.toString()
            r9 = 0
            r0.add(r9, r8)
            goto L_0x0223
        L_0x0209:
            if (r2 != 0) goto L_0x0223
            com.ibm.icu.text.RuleBasedTransliterator$Data r0 = r1.curData
            if (r0 == 0) goto L_0x0223
            int r0 = r1.direction
            if (r0 != 0) goto L_0x021b
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r0 = r1.dataVector
            com.ibm.icu.text.RuleBasedTransliterator$Data r8 = r1.curData
            r0.add(r8)
            goto L_0x0223
        L_0x021b:
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r0 = r1.dataVector
            com.ibm.icu.text.RuleBasedTransliterator$Data r8 = r1.curData
            r9 = 0
            r0.add(r9, r8)
        L_0x0223:
            r0 = 0
        L_0x0224:
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r8 = r1.dataVector
            int r8 = r8.size()
            if (r0 >= r8) goto L_0x0256
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r8 = r1.dataVector
            java.lang.Object r8 = r8.get(r0)
            com.ibm.icu.text.RuleBasedTransliterator$Data r8 = (com.ibm.icu.text.RuleBasedTransliterator.Data) r8
            java.util.List<java.lang.Object> r9 = r1.variablesVector
            int r9 = r9.size()
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r8.variables = r9
            java.util.List<java.lang.Object> r9 = r1.variablesVector
            java.lang.Object[] r10 = r8.variables
            r9.toArray(r10)
            java.util.HashMap r9 = new java.util.HashMap
            r9.<init>()
            r8.variableNames = r9
            java.util.Map<java.lang.String, char[]> r9 = r8.variableNames
            java.util.Map<java.lang.String, char[]> r10 = r1.variableNames
            r9.putAll(r10)
            int r0 = r0 + 1
            goto L_0x0224
        L_0x0256:
            r11 = 0
            r1.variablesVector = r11
            com.ibm.icu.text.UnicodeSet r0 = r1.compoundFilter     // Catch:{ IllegalArgumentException -> 0x02ad }
            if (r0 == 0) goto L_0x0275
            int r0 = r1.direction     // Catch:{ IllegalArgumentException -> 0x02ad }
            if (r0 != 0) goto L_0x0265
            r8 = 1
            if (r3 != r8) goto L_0x026d
            goto L_0x0266
        L_0x0265:
            r8 = 1
        L_0x0266:
            int r0 = r1.direction     // Catch:{ IllegalArgumentException -> 0x02ad }
            if (r0 != r8) goto L_0x0275
            if (r3 != r6) goto L_0x026d
            goto L_0x0275
        L_0x026d:
            com.ibm.icu.impl.IllegalIcuArgumentException r0 = new com.ibm.icu.impl.IllegalIcuArgumentException     // Catch:{ IllegalArgumentException -> 0x02ad }
            java.lang.String r8 = "Compound filters misplaced"
            r0.<init>(r8)     // Catch:{ IllegalArgumentException -> 0x02ad }
            throw r0     // Catch:{ IllegalArgumentException -> 0x02ad }
        L_0x0275:
            r0 = 0
        L_0x0276:
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r8 = r1.dataVector     // Catch:{ IllegalArgumentException -> 0x02ad }
            int r8 = r8.size()     // Catch:{ IllegalArgumentException -> 0x02ad }
            if (r0 >= r8) goto L_0x028e
            java.util.List<com.ibm.icu.text.RuleBasedTransliterator$Data> r8 = r1.dataVector     // Catch:{ IllegalArgumentException -> 0x02ad }
            java.lang.Object r8 = r8.get(r0)     // Catch:{ IllegalArgumentException -> 0x02ad }
            com.ibm.icu.text.RuleBasedTransliterator$Data r8 = (com.ibm.icu.text.RuleBasedTransliterator.Data) r8     // Catch:{ IllegalArgumentException -> 0x02ad }
            com.ibm.icu.text.TransliterationRuleSet r9 = r8.ruleSet     // Catch:{ IllegalArgumentException -> 0x02ad }
            r9.freeze()     // Catch:{ IllegalArgumentException -> 0x02ad }
            int r0 = r0 + 1
            goto L_0x0276
        L_0x028e:
            java.util.List<java.lang.String> r0 = r1.idBlockVector     // Catch:{ IllegalArgumentException -> 0x02ad }
            int r0 = r0.size()     // Catch:{ IllegalArgumentException -> 0x02ad }
            r8 = 1
            if (r0 != r8) goto L_0x02ac
            java.util.List<java.lang.String> r0 = r1.idBlockVector     // Catch:{ IllegalArgumentException -> 0x02ad }
            r8 = 0
            java.lang.Object r0 = r0.get(r8)     // Catch:{ IllegalArgumentException -> 0x02ad }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ IllegalArgumentException -> 0x02ad }
            int r0 = r0.length()     // Catch:{ IllegalArgumentException -> 0x02ad }
            if (r0 != 0) goto L_0x02ac
            java.util.List<java.lang.String> r0 = r1.idBlockVector     // Catch:{ IllegalArgumentException -> 0x02ad }
            r8 = 0
            r0.remove(r8)     // Catch:{ IllegalArgumentException -> 0x02ad }
        L_0x02ac:
            goto L_0x02b4
        L_0x02ad:
            r0 = move-exception
            r0.fillInStackTrace()
            r5.add(r0)
        L_0x02b4:
            int r0 = r5.size()
            if (r0 == 0) goto L_0x02ec
            int r0 = r5.size()
            r8 = 1
            int r0 = r0 - r8
        L_0x02c0:
            if (r0 <= 0) goto L_0x02e4
            int r8 = r0 + -1
            java.lang.Object r8 = r5.get(r8)
            java.lang.RuntimeException r8 = (java.lang.RuntimeException) r8
        L_0x02ca:
            java.lang.Throwable r9 = r8.getCause()
            if (r9 == 0) goto L_0x02d8
            java.lang.Throwable r9 = r8.getCause()
            r8 = r9
            java.lang.RuntimeException r8 = (java.lang.RuntimeException) r8
            goto L_0x02ca
        L_0x02d8:
            java.lang.Object r9 = r5.get(r0)
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r8.initCause(r9)
            int r0 = r0 + -1
            goto L_0x02c0
        L_0x02e4:
            r12 = 0
            java.lang.Object r0 = r5.get(r12)
            java.lang.RuntimeException r0 = (java.lang.RuntimeException) r0
            throw r0
        L_0x02ec:
            return
        L_0x02ed:
            r11 = 0
            r12 = 0
            r0.fillInStackTrace()
            r5.add(r0)
            int r15 = r15 + 1
            int r3 = ruleEnd(r10, r8, r14)
            r17 = 1
            int r3 = r3 + 1
            r9 = r2
            r13 = r3
            r2 = r19
        L_0x0303:
            r3 = r11
            r11 = r17
            goto L_0x0059
        L_0x0308:
            r19 = r2
            r11 = r3
            r16 = -1
        L_0x030d:
            r2 = r9
            r3 = r11
            r9 = r15
            r0 = r19
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorParser.parseRules(com.ibm.icu.text.TransliteratorParser$RuleBody, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0039, code lost:
        if (OPERATORS.indexOf(r9) < 0) goto L_0x003b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int parseRule(java.lang.String r24, int r25, int r26) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r2 = r26
            r3 = r25
            r4 = 0
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            r0.segmentStandins = r5
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r0.segmentObjects = r5
            com.ibm.icu.text.TransliteratorParser$RuleHalf r5 = new com.ibm.icu.text.TransliteratorParser$RuleHalf
            r6 = 0
            r5.<init>()
            com.ibm.icu.text.TransliteratorParser$RuleHalf r7 = new com.ibm.icu.text.TransliteratorParser$RuleHalf
            r7.<init>()
            r0.undefinedVariableName = r6
            r6 = r25
            int r6 = r5.parse(r1, r6, r2, r0)
            if (r6 == r2) goto L_0x003b
            java.lang.String r8 = "=><←→↔"
            int r6 = r6 + -1
            char r9 = r1.charAt(r6)
            r4 = r9
            int r8 = r8.indexOf(r9)
            if (r8 >= 0) goto L_0x004f
        L_0x003b:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "No operator pos="
            r8.append(r9)
            r8.append(r6)
            java.lang.String r8 = r8.toString()
            syntaxError(r8, r1, r3)
        L_0x004f:
            r8 = 1
            int r6 = r6 + r8
            r9 = 60
            r10 = 62
            if (r4 != r9) goto L_0x0063
            if (r6 >= r2) goto L_0x0063
            char r9 = r1.charAt(r6)
            if (r9 != r10) goto L_0x0063
            int r6 = r6 + 1
            r4 = 126(0x7e, float:1.77E-43)
        L_0x0063:
            r9 = 8592(0x2190, float:1.204E-41)
            if (r4 == r9) goto L_0x0076
            r9 = 8594(0x2192, float:1.2043E-41)
            if (r4 == r9) goto L_0x0073
            r9 = 8596(0x2194, float:1.2046E-41)
            if (r4 == r9) goto L_0x0070
            goto L_0x0079
        L_0x0070:
            r4 = 126(0x7e, float:1.77E-43)
            goto L_0x0079
        L_0x0073:
            r4 = 62
            goto L_0x0079
        L_0x0076:
            r4 = 60
        L_0x0079:
            int r6 = r7.parse(r1, r6, r2, r0)
            if (r6 >= r2) goto L_0x0091
            int r6 = r6 + -1
            char r9 = r1.charAt(r6)
            r11 = 59
            if (r9 != r11) goto L_0x008c
            int r6 = r6 + 1
            goto L_0x0091
        L_0x008c:
            java.lang.String r9 = "Unquoted operator"
            syntaxError(r9, r1, r3)
        L_0x0091:
            r9 = 61
            r11 = 0
            if (r4 != r9) goto L_0x00e6
            java.lang.String r9 = r0.undefinedVariableName
            if (r9 != 0) goto L_0x009f
            java.lang.String r9 = "Missing '$' or duplicate definition"
            syntaxError(r9, r1, r3)
        L_0x009f:
            java.lang.String r9 = r5.text
            int r9 = r9.length()
            if (r9 != r8) goto L_0x00b1
            java.lang.String r9 = r5.text
            char r9 = r9.charAt(r11)
            char r10 = r0.variableLimit
            if (r9 == r10) goto L_0x00b6
        L_0x00b1:
            java.lang.String r9 = "Malformed LHS"
            syntaxError(r9, r1, r3)
        L_0x00b6:
            boolean r9 = r5.anchorStart
            if (r9 != 0) goto L_0x00c6
            boolean r9 = r5.anchorEnd
            if (r9 != 0) goto L_0x00c6
            boolean r9 = r7.anchorStart
            if (r9 != 0) goto L_0x00c6
            boolean r9 = r7.anchorEnd
            if (r9 == 0) goto L_0x00cb
        L_0x00c6:
            java.lang.String r9 = "Malformed variable def"
            syntaxError(r9, r1, r3)
        L_0x00cb:
            java.lang.String r9 = r7.text
            int r9 = r9.length()
            char[] r10 = new char[r9]
            java.lang.String r12 = r7.text
            r12.getChars(r11, r9, r10, r11)
            java.util.Map<java.lang.String, char[]> r11 = r0.variableNames
            java.lang.String r12 = r0.undefinedVariableName
            r11.put(r12, r10)
            char r11 = r0.variableLimit
            int r11 = r11 + r8
            char r8 = (char) r11
            r0.variableLimit = r8
            return r6
        L_0x00e6:
            java.lang.String r9 = r0.undefinedVariableName
            if (r9 == 0) goto L_0x0100
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r12 = "Undefined variable $"
            r9.append(r12)
            java.lang.String r12 = r0.undefinedVariableName
            r9.append(r12)
            java.lang.String r9 = r9.toString()
            syntaxError(r9, r1, r3)
        L_0x0100:
            java.lang.StringBuffer r9 = r0.segmentStandins
            int r9 = r9.length()
            java.util.List<com.ibm.icu.text.StringMatcher> r12 = r0.segmentObjects
            int r12 = r12.size()
            if (r9 <= r12) goto L_0x0113
            java.lang.String r9 = "Undefined segment reference"
            syntaxError(r9, r1, r3)
        L_0x0113:
            r9 = r11
        L_0x0114:
            java.lang.StringBuffer r12 = r0.segmentStandins
            int r12 = r12.length()
            if (r9 >= r12) goto L_0x012c
            java.lang.StringBuffer r12 = r0.segmentStandins
            char r12 = r12.charAt(r9)
            if (r12 != 0) goto L_0x0129
            java.lang.String r12 = "Internal error"
            syntaxError(r12, r1, r3)
        L_0x0129:
            int r9 = r9 + 1
            goto L_0x0114
        L_0x012c:
            r9 = r11
        L_0x012d:
            java.util.List<com.ibm.icu.text.StringMatcher> r12 = r0.segmentObjects
            int r12 = r12.size()
            if (r9 >= r12) goto L_0x0145
            java.util.List<com.ibm.icu.text.StringMatcher> r12 = r0.segmentObjects
            java.lang.Object r12 = r12.get(r9)
            if (r12 != 0) goto L_0x0142
            java.lang.String r12 = "Internal error"
            syntaxError(r12, r1, r3)
        L_0x0142:
            int r9 = r9 + 1
            goto L_0x012d
        L_0x0145:
            r9 = 126(0x7e, float:1.77E-43)
            if (r4 == r9) goto L_0x0158
            int r12 = r0.direction
            if (r12 != 0) goto L_0x014f
            r12 = r8
            goto L_0x0150
        L_0x014f:
            r12 = r11
        L_0x0150:
            if (r4 != r10) goto L_0x0154
            r10 = r8
            goto L_0x0155
        L_0x0154:
            r10 = r11
        L_0x0155:
            if (r12 == r10) goto L_0x0158
            return r6
        L_0x0158:
            int r10 = r0.direction
            if (r10 != r8) goto L_0x015f
            r8 = r5
            r5 = r7
            r7 = r8
        L_0x015f:
            if (r4 != r9) goto L_0x0169
            r7.removeContext()
            r8 = -1
            r5.cursor = r8
            r5.cursorOffset = r11
        L_0x0169:
            int r8 = r5.ante
            if (r8 >= 0) goto L_0x016f
            r5.ante = r11
        L_0x016f:
            int r8 = r5.post
            if (r8 >= 0) goto L_0x017b
            java.lang.String r8 = r5.text
            int r8 = r8.length()
            r5.post = r8
        L_0x017b:
            int r8 = r7.ante
            if (r8 >= 0) goto L_0x01a9
            int r8 = r7.post
            if (r8 >= 0) goto L_0x01a9
            int r8 = r5.cursor
            if (r8 >= 0) goto L_0x01a9
            int r8 = r7.cursorOffset
            if (r8 == 0) goto L_0x018f
            int r8 = r7.cursor
            if (r8 < 0) goto L_0x01a9
        L_0x018f:
            boolean r8 = r7.anchorStart
            if (r8 != 0) goto L_0x01a9
            boolean r8 = r7.anchorEnd
            if (r8 != 0) goto L_0x01a9
            boolean r8 = r5.isValidInput(r0)
            if (r8 == 0) goto L_0x01a9
            boolean r8 = r7.isValidOutput(r0)
            if (r8 == 0) goto L_0x01a9
            int r8 = r5.ante
            int r9 = r5.post
            if (r8 <= r9) goto L_0x01ae
        L_0x01a9:
            java.lang.String r8 = "Malformed rule"
            syntaxError(r8, r1, r3)
        L_0x01ae:
            r8 = 0
            java.util.List<com.ibm.icu.text.StringMatcher> r9 = r0.segmentObjects
            int r9 = r9.size()
            if (r9 <= 0) goto L_0x01c4
            java.util.List<com.ibm.icu.text.StringMatcher> r9 = r0.segmentObjects
            int r9 = r9.size()
            com.ibm.icu.text.UnicodeMatcher[] r8 = new com.ibm.icu.text.UnicodeMatcher[r9]
            java.util.List<com.ibm.icu.text.StringMatcher> r9 = r0.segmentObjects
            r9.toArray(r8)
        L_0x01c4:
            com.ibm.icu.text.RuleBasedTransliterator$Data r9 = r0.curData
            com.ibm.icu.text.TransliterationRuleSet r9 = r9.ruleSet
            com.ibm.icu.text.TransliterationRule r15 = new com.ibm.icu.text.TransliterationRule
            java.lang.String r11 = r5.text
            int r12 = r5.ante
            int r13 = r5.post
            java.lang.String r14 = r7.text
            int r10 = r7.cursor
            int r1 = r7.cursorOffset
            boolean r2 = r5.anchorStart
            r21 = r3
            boolean r3 = r5.anchorEnd
            r22 = r4
            com.ibm.icu.text.RuleBasedTransliterator$Data r4 = r0.curData
            r16 = r10
            r10 = r15
            r0 = r15
            r15 = r16
            r16 = r1
            r17 = r8
            r18 = r2
            r19 = r3
            r20 = r4
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r9.addRule(r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TransliteratorParser.parseRule(java.lang.String, int, int):int");
    }

    private void setVariableRange(int start, int end) {
        if (start > end || start < 0 || end > 65535) {
            throw new IllegalIcuArgumentException("Invalid variable range " + start + ", " + end);
        }
        this.curData.variablesBase = (char) start;
        if (this.dataVector.size() == 0) {
            this.variableNext = (char) start;
            this.variableLimit = (char) (end + 1);
        }
    }

    /* access modifiers changed from: private */
    public void checkVariableRange(int ch, String rule, int start) {
        if (ch >= this.curData.variablesBase && ch < this.variableLimit) {
            syntaxError("Variable range character in rule", rule, start);
        }
    }

    private void pragmaMaximumBackup(int backup) {
        throw new IllegalIcuArgumentException("use maximum backup pragma not implemented yet");
    }

    private void pragmaNormalizeRules(Normalizer.Mode mode) {
        throw new IllegalIcuArgumentException("use normalize rules pragma not implemented yet");
    }

    static boolean resemblesPragma(String rule, int pos, int limit) {
        return Utility.parsePattern(rule, pos, limit, "use ", (int[]) null) >= 0;
    }

    private int parsePragma(String rule, int pos, int limit) {
        int[] array = new int[2];
        int pos2 = pos + 4;
        int p = Utility.parsePattern(rule, pos2, limit, "~variable range # #~;", array);
        if (p >= 0) {
            setVariableRange(array[0], array[1]);
            return p;
        }
        int p2 = Utility.parsePattern(rule, pos2, limit, "~maximum backup #~;", array);
        if (p2 >= 0) {
            pragmaMaximumBackup(array[0]);
            return p2;
        }
        int p3 = Utility.parsePattern(rule, pos2, limit, "~nfd rules~;", (int[]) null);
        if (p3 >= 0) {
            pragmaNormalizeRules(Normalizer.NFD);
            return p3;
        }
        int p4 = Utility.parsePattern(rule, pos2, limit, "~nfc rules~;", (int[]) null);
        if (p4 < 0) {
            return -1;
        }
        pragmaNormalizeRules(Normalizer.NFC);
        return p4;
    }

    static final void syntaxError(String msg, String rule, int start) {
        int end = ruleEnd(rule, start, rule.length());
        throw new IllegalIcuArgumentException(msg + " in \"" + Utility.escape(rule.substring(start, end)) + '\"');
    }

    static final int ruleEnd(String rule, int start, int limit) {
        int end = Utility.quotedIndexOf(rule, start, limit, ";");
        if (end < 0) {
            return limit;
        }
        return end;
    }

    /* access modifiers changed from: private */
    public final char parseSet(String rule, ParsePosition pos) {
        UnicodeSet set = new UnicodeSet(rule, pos, this.parseData);
        if (this.variableNext < this.variableLimit) {
            set.compact();
            return generateStandInFor(set);
        }
        throw new RuntimeException("Private use variables exhausted");
    }

    /* access modifiers changed from: package-private */
    public char generateStandInFor(Object obj) {
        for (int i = 0; i < this.variablesVector.size(); i++) {
            if (this.variablesVector.get(i) == obj) {
                return (char) (this.curData.variablesBase + i);
            }
        }
        if (this.variableNext < this.variableLimit) {
            this.variablesVector.add(obj);
            char c = this.variableNext;
            this.variableNext = (char) (c + 1);
            return c;
        }
        throw new RuntimeException("Variable range exhausted");
    }

    public char getSegmentStandin(int seg) {
        if (this.segmentStandins.length() < seg) {
            this.segmentStandins.setLength(seg);
        }
        char c = this.segmentStandins.charAt(seg - 1);
        if (c != 0) {
            return c;
        }
        if (this.variableNext < this.variableLimit) {
            char c2 = this.variableNext;
            this.variableNext = (char) (c2 + 1);
            char c3 = c2;
            this.variablesVector.add((Object) null);
            this.segmentStandins.setCharAt(seg - 1, c3);
            return c3;
        }
        throw new RuntimeException("Variable range exhausted");
    }

    public void setSegmentObject(int seg, StringMatcher obj) {
        while (this.segmentObjects.size() < seg) {
            this.segmentObjects.add((Object) null);
        }
        int index = getSegmentStandin(seg) - this.curData.variablesBase;
        if (this.segmentObjects.get(seg - 1) == null && this.variablesVector.get(index) == null) {
            this.segmentObjects.set(seg - 1, obj);
            this.variablesVector.set(index, obj);
            return;
        }
        throw new RuntimeException();
    }

    /* access modifiers changed from: package-private */
    public char getDotStandIn() {
        if (this.dotStandIn == -1) {
            this.dotStandIn = generateStandInFor(new UnicodeSet(DOT_SET));
        }
        return (char) this.dotStandIn;
    }

    /* access modifiers changed from: private */
    public void appendVariableDef(String name, StringBuffer buf) {
        char[] ch = this.variableNames.get(name);
        if (ch != null) {
            buf.append(ch);
        } else if (this.undefinedVariableName == null) {
            this.undefinedVariableName = name;
            if (this.variableNext < this.variableLimit) {
                char c = (char) (this.variableLimit - 1);
                this.variableLimit = c;
                buf.append(c);
                return;
            }
            throw new RuntimeException("Private use variables exhausted");
        } else {
            throw new IllegalIcuArgumentException("Undefined variable $" + name);
        }
    }
}
