package com.ibm.icu.text;

import android.telecom.Logging.Session;
import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.TransliteratorIDParser;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
class TransliteratorParser {
    private static final char ALT_FORWARD_RULE_OP = '\u2192';
    private static final char ALT_FUNCTION = '\u2206';
    private static final char ALT_FWDREV_RULE_OP = '\u2194';
    private static final char ALT_REVERSE_RULE_OP = '\u2190';
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
    private static final String HALF_ENDERS = "=><\u2190\u2192\u2194;";
    private static final String ID_TOKEN = "::";
    private static final int ID_TOKEN_LEN = 2;
    private static final char KLEENE_STAR = '*';
    private static final char ONE_OR_MORE = '+';
    private static final String OPERATORS = "=><\u2190\u2192\u2194";
    private static final char QUOTE = '\'';
    private static final char REVERSE_RULE_OP = '<';
    private static final char RULE_COMMENT_CHAR = '#';
    private static final char SEGMENT_CLOSE = ')';
    private static final char SEGMENT_OPEN = '(';
    private static final char VARIABLE_DEF_OP = '=';
    private static final char ZERO_OR_ONE = '?';
    public UnicodeSet compoundFilter;
    private RuleBasedTransliterator.Data curData;
    public List<RuleBasedTransliterator.Data> dataVector;
    private int direction;
    private int dotStandIn = -1;
    public List<String> idBlockVector;
    private ParseData parseData;
    private List<StringMatcher> segmentObjects;
    private StringBuffer segmentStandins;
    private String undefinedVariableName;
    private char variableLimit;
    private Map<String, char[]> variableNames;
    private char variableNext;
    private List<Object> variablesVector;
    private static UnicodeSet ILLEGAL_TOP = new UnicodeSet("[\\)]");
    private static UnicodeSet ILLEGAL_SEG = new UnicodeSet("[\\{\\}\\|\\@]");
    private static UnicodeSet ILLEGAL_FUNC = new UnicodeSet("[\\^\\(\\.\\*\\+\\?\\{\\}\\|\\@]");

    /* loaded from: classes5.dex */
    private class ParseData implements SymbolTable {
        private ParseData() {
        }

        @Override // com.ibm.icu.text.SymbolTable
        public char[] lookup(String name) {
            return (char[]) TransliteratorParser.this.variableNames.get(name);
        }

        @Override // com.ibm.icu.text.SymbolTable
        public UnicodeMatcher lookupMatcher(int ch) {
            int i = ch - TransliteratorParser.this.curData.variablesBase;
            if (i >= 0 && i < TransliteratorParser.this.variablesVector.size()) {
                return (UnicodeMatcher) TransliteratorParser.this.variablesVector.get(i);
            }
            return null;
        }

        @Override // com.ibm.icu.text.SymbolTable
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
            if (i >= 0 && i < TransliteratorParser.this.variablesVector.size()) {
                return TransliteratorParser.this.variablesVector.get(i) instanceof UnicodeMatcher;
            }
            return true;
        }

        public boolean isReplacer(int ch) {
            int i = ch - TransliteratorParser.this.curData.variablesBase;
            if (i >= 0 && i < TransliteratorParser.this.variablesVector.size()) {
                return TransliteratorParser.this.variablesVector.get(i) instanceof UnicodeReplacer;
            }
            return true;
        }
    }

    /* loaded from: classes5.dex */
    private static abstract class RuleBody {
        abstract String handleNextLine();

        abstract void reset();

        private RuleBody() {
        }

        String nextLine() {
            String s;
            String s2 = handleNextLine();
            if (s2 != null && s2.length() > 0 && s2.charAt(s2.length() - 1) == '\\') {
                StringBuilder b = new StringBuilder(s2);
                do {
                    b.deleteCharAt(b.length() - 1);
                    s = handleNextLine();
                    if (s != null) {
                        b.append(s);
                        if (s.length() <= 0) {
                            break;
                        }
                    } else {
                        break;
                    }
                } while (s.charAt(s.length() - 1) == '\\');
                return b.toString();
            }
            return s2;
        }
    }

    /* loaded from: classes5.dex */
    private static class RuleArray extends RuleBody {
        String[] array;

        /* renamed from: i */
        int f2565i;

        public RuleArray(String[] array) {
            super();
            this.array = array;
            this.f2565i = 0;
        }

        @Override // com.ibm.icu.text.TransliteratorParser.RuleBody
        public String handleNextLine() {
            if (this.f2565i < this.array.length) {
                String[] strArr = this.array;
                int i = this.f2565i;
                this.f2565i = i + 1;
                return strArr[i];
            }
            return null;
        }

        @Override // com.ibm.icu.text.TransliteratorParser.RuleBody
        public void reset() {
            this.f2565i = 0;
        }
    }

    /* loaded from: classes5.dex */
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
            StringBuffer buf = new StringBuffer();
            int pos2 = parseSection(rule, pos, limit, parser, buf, TransliteratorParser.ILLEGAL_TOP, false);
            this.text = buf.toString();
            if (this.cursorOffset > 0 && this.cursor != this.cursorOffsetPos) {
                TransliteratorParser.syntaxError("Misplaced |", rule, pos);
            }
            return pos2;
        }

        /* JADX WARN: Removed duplicated region for block: B:133:0x027a  */
        /* JADX WARN: Removed duplicated region for block: B:137:0x0288  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private int parseSection(String rule, int pos, int limit, TransliteratorParser parser, StringBuffer buf, UnicodeSet illegal, boolean isSegment) {
            int pos2;
            int i;
            int pos3;
            int varLimit;
            int bufStart;
            int quoteLimit;
            int start;
            int[] iref;
            int pos4;
            int start2;
            boolean z;
            int pos5;
            boolean z2;
            int pos6;
            int[] iref2;
            boolean z3;
            int qstart;
            int qlimit;
            int qstart2;
            int i2;
            int i3 = limit;
            int start3 = pos;
            int varLimit2 = -1;
            int[] iref3 = new int[1];
            int bufStart2 = buf.length();
            ParsePosition pp = null;
            int quoteStart = -1;
            int start4 = -1;
            int varStart = -1;
            int pos7 = pos;
            while (pos7 < i3) {
                int pos8 = pos7 + 1;
                char c = rule.charAt(pos7);
                if (!PatternProps.isWhiteSpace(c)) {
                    if (TransliteratorParser.HALF_ENDERS.indexOf(c) >= 0) {
                        if (isSegment) {
                            TransliteratorParser.syntaxError("Unclosed segment", rule, start3);
                        }
                        pos2 = pos8;
                        i = varLimit2;
                    } else {
                        if (this.anchorEnd) {
                            TransliteratorParser.syntaxError("Malformed variable reference", rule, start3);
                        }
                        if (UnicodeSet.resemblesPattern(rule, pos8 - 1)) {
                            ParsePosition pp2 = pp == null ? new ParsePosition(0) : pp;
                            pp2.setIndex(pos8 - 1);
                            buf.append(parser.parseSet(rule, pp2));
                            pp = pp2;
                            pos7 = pp2.getIndex();
                        } else if (c == '\\') {
                            if (pos8 == i3) {
                                TransliteratorParser.syntaxError("Trailing backslash", rule, start3);
                            }
                            iref3[0] = pos8;
                            int escaped = Utility.unescapeAt(rule, iref3);
                            int pos9 = iref3[0];
                            if (escaped == -1) {
                                TransliteratorParser.syntaxError("Malformed escape", rule, start3);
                            }
                            parser.checkVariableRange(escaped, rule, start3);
                            UTF16.append(buf, escaped);
                            pos7 = pos9;
                        } else if (c == '\'') {
                            int iq = rule.indexOf(39, pos8);
                            if (iq == pos8) {
                                buf.append(c);
                                pos7 = pos8 + 1;
                            } else {
                                quoteStart = buf.length();
                                while (true) {
                                    if (iq < 0) {
                                        TransliteratorParser.syntaxError("Unterminated quote", rule, start3);
                                    }
                                    buf.append(rule.substring(pos8, iq));
                                    pos8 = iq + 1;
                                    if (pos8 < i3 && rule.charAt(pos8) == '\'') {
                                        iq = rule.indexOf(39, pos8 + 1);
                                    }
                                }
                                start4 = buf.length();
                                for (int iq2 = quoteStart; iq2 < start4; iq2++) {
                                    parser.checkVariableRange(buf.charAt(iq2), rule, start3);
                                }
                                pos7 = pos8;
                            }
                        } else {
                            parser.checkVariableRange(c, rule, start3);
                            if (illegal.contains(c)) {
                                TransliteratorParser.syntaxError("Illegal character '" + c + '\'', rule, start3);
                            }
                            if (c != '$') {
                                if (c != '&') {
                                    if (c == '.') {
                                        pos6 = pos8;
                                        varLimit = varLimit2;
                                        bufStart = bufStart2;
                                        quoteLimit = start4;
                                        iref2 = iref3;
                                        z3 = true;
                                        buf.append(parser.getDotStandIn());
                                    } else if (c == '^') {
                                        pos6 = pos8;
                                        varLimit = varLimit2;
                                        bufStart = bufStart2;
                                        quoteLimit = start4;
                                        iref2 = iref3;
                                        z3 = true;
                                        if (buf.length() != 0 || this.anchorStart) {
                                            TransliteratorParser.syntaxError("Misplaced anchor start", rule, start3);
                                        } else {
                                            this.anchorStart = true;
                                        }
                                    } else if (c != '\u2206') {
                                        switch (c) {
                                            case '(':
                                                int bufSegStart = buf.length();
                                                int segmentNumber = this.nextSegmentNumber;
                                                this.nextSegmentNumber = segmentNumber + 1;
                                                bufStart = bufStart2;
                                                quoteLimit = start4;
                                                int pos10 = parseSection(rule, pos8, limit, parser, buf, TransliteratorParser.ILLEGAL_SEG, true);
                                                StringMatcher m = new StringMatcher(buf.substring(bufSegStart), segmentNumber, parser.curData);
                                                parser.setSegmentObject(segmentNumber, m);
                                                buf.setLength(bufSegStart);
                                                buf.append(parser.getSegmentStandin(segmentNumber));
                                                pos7 = pos10;
                                                z = true;
                                                start2 = start3;
                                                varLimit2 = varLimit2;
                                                iref = iref3;
                                                pos4 = limit;
                                                break;
                                            case ')':
                                                pos2 = pos8;
                                                i = varLimit2;
                                                break;
                                            default:
                                                switch (c) {
                                                    case '?':
                                                        break;
                                                    case '@':
                                                        if (this.cursorOffset < 0) {
                                                            if (buf.length() > 0) {
                                                                TransliteratorParser.syntaxError("Misplaced " + c, rule, start3);
                                                            }
                                                            this.cursorOffset--;
                                                        } else if (this.cursorOffset > 0) {
                                                            if (buf.length() != this.cursorOffsetPos || this.cursor >= 0) {
                                                                TransliteratorParser.syntaxError("Misplaced " + c, rule, start3);
                                                            }
                                                            this.cursorOffset++;
                                                        } else if (this.cursor == 0 && buf.length() == 0) {
                                                            this.cursorOffset = -1;
                                                        } else if (this.cursor < 0) {
                                                            this.cursorOffsetPos = buf.length();
                                                            z2 = true;
                                                            this.cursorOffset = 1;
                                                            varLimit = varLimit2;
                                                            bufStart = bufStart2;
                                                            quoteLimit = start4;
                                                            pos4 = i3;
                                                            start = start3;
                                                            break;
                                                        } else {
                                                            TransliteratorParser.syntaxError("Misplaced " + c, rule, start3);
                                                        }
                                                        varLimit = varLimit2;
                                                        bufStart = bufStart2;
                                                        quoteLimit = start4;
                                                        pos4 = i3;
                                                        start = start3;
                                                        z2 = true;
                                                        break;
                                                    default:
                                                        switch (c) {
                                                            case '{':
                                                                if (this.ante >= 0) {
                                                                    TransliteratorParser.syntaxError("Multiple ante contexts", rule, start3);
                                                                }
                                                                this.ante = buf.length();
                                                                break;
                                                            case '|':
                                                                if (this.cursor >= 0) {
                                                                    TransliteratorParser.syntaxError("Multiple cursors", rule, start3);
                                                                }
                                                                this.cursor = buf.length();
                                                                break;
                                                            case '}':
                                                                if (this.post >= 0) {
                                                                    TransliteratorParser.syntaxError("Multiple post contexts", rule, start3);
                                                                }
                                                                this.post = buf.length();
                                                                break;
                                                            default:
                                                                if (c >= '!' && c <= '~' && ((c < '0' || c > '9') && ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')))) {
                                                                    TransliteratorParser.syntaxError("Unquoted " + c, rule, start3);
                                                                }
                                                                buf.append(c);
                                                                break;
                                                        }
                                                        varLimit = varLimit2;
                                                        bufStart = bufStart2;
                                                        quoteLimit = start4;
                                                        pos4 = i3;
                                                        start = start3;
                                                        z2 = true;
                                                        break;
                                                }
                                                pos3 = pos8;
                                                iref = iref3;
                                                z = z2;
                                                pos7 = pos3;
                                                varLimit2 = varLimit;
                                                start2 = start;
                                                break;
                                            case '*':
                                            case '+':
                                                if (isSegment && buf.length() == bufStart2) {
                                                    TransliteratorParser.syntaxError("Misplaced quantifier", rule, start3);
                                                } else {
                                                    try {
                                                        if (buf.length() == start4) {
                                                            qstart2 = quoteStart;
                                                            i2 = start4;
                                                        } else if (buf.length() == varLimit2) {
                                                            qstart2 = varStart;
                                                            i2 = varLimit2;
                                                        } else {
                                                            int qstart3 = buf.length() - 1;
                                                            qstart = qstart3;
                                                            qlimit = qstart3 + 1;
                                                            UnicodeMatcher m2 = new StringMatcher(buf.toString(), qstart, qlimit, 0, parser.curData);
                                                            int min = 0;
                                                            int max = Integer.MAX_VALUE;
                                                            if (c != '+') {
                                                                min = 1;
                                                            } else if (c == '?') {
                                                                min = 0;
                                                                max = 1;
                                                            }
                                                            UnicodeMatcher m3 = new Quantifier(m2, min, max);
                                                            buf.setLength(qstart);
                                                            buf.append(parser.generateStandInFor(m3));
                                                        }
                                                        UnicodeMatcher m22 = new StringMatcher(buf.toString(), qstart, qlimit, 0, parser.curData);
                                                        int min2 = 0;
                                                        int max2 = Integer.MAX_VALUE;
                                                        if (c != '+') {
                                                        }
                                                        UnicodeMatcher m32 = new Quantifier(m22, min2, max2);
                                                        buf.setLength(qstart);
                                                        buf.append(parser.generateStandInFor(m32));
                                                    } catch (RuntimeException e) {
                                                        String precontext = pos8 < 50 ? rule.substring(0, pos8) : Session.TRUNCATE_STRING + rule.substring(pos8 - 50, pos8);
                                                        String postContext = i3 - pos8 <= 50 ? rule.substring(pos8, i3) : rule.substring(pos8, pos8 + 50) + Session.TRUNCATE_STRING;
                                                        throw new IllegalIcuArgumentException("Failure in rule: " + precontext + "$$$" + postContext).initCause(e);
                                                    }
                                                    qstart = qstart2;
                                                    qlimit = i2;
                                                }
                                                varLimit = varLimit2;
                                                bufStart = bufStart2;
                                                quoteLimit = start4;
                                                pos4 = i3;
                                                start = start3;
                                                z2 = true;
                                                pos3 = pos8;
                                                iref = iref3;
                                                z = z2;
                                                pos7 = pos3;
                                                varLimit2 = varLimit;
                                                start2 = start;
                                                break;
                                        }
                                        i3 = pos4;
                                        iref3 = iref;
                                        bufStart2 = bufStart;
                                        start3 = start2;
                                        start4 = quoteLimit;
                                    }
                                    z2 = z3;
                                    start = start3;
                                    pos3 = pos6;
                                    iref = iref2;
                                    pos4 = limit;
                                    z = z2;
                                    pos7 = pos3;
                                    varLimit2 = varLimit;
                                    start2 = start;
                                    i3 = pos4;
                                    iref3 = iref;
                                    bufStart2 = bufStart;
                                    start3 = start2;
                                    start4 = quoteLimit;
                                }
                                int varLimit3 = varLimit2;
                                bufStart = bufStart2;
                                quoteLimit = start4;
                                int[] iref4 = iref3;
                                iref4[0] = pos8;
                                TransliteratorIDParser.SingleID single = TransliteratorIDParser.parseFilterID(rule, iref4);
                                if (single == null || !Utility.parseChar(rule, iref4, (char) TransliteratorParser.SEGMENT_OPEN)) {
                                    TransliteratorParser.syntaxError("Invalid function", rule, start3);
                                }
                                Transliterator t = single.getInstance();
                                if (t == null) {
                                    TransliteratorParser.syntaxError("Invalid function ID", rule, start3);
                                }
                                int bufSegStart2 = buf.length();
                                int start5 = start3;
                                iref = iref4;
                                int pos11 = parseSection(rule, iref4[0], limit, parser, buf, TransliteratorParser.ILLEGAL_FUNC, true);
                                FunctionReplacer r = new FunctionReplacer(t, new StringReplacer(buf.substring(bufSegStart2), parser.curData));
                                buf.setLength(bufSegStart2);
                                buf.append(parser.generateStandInFor(r));
                                pos7 = pos11;
                                varLimit2 = varLimit3;
                                start2 = start5;
                                pos4 = limit;
                                z = true;
                                i3 = pos4;
                                iref3 = iref;
                                bufStart2 = bufStart;
                                start3 = start2;
                                start4 = quoteLimit;
                            } else {
                                pos3 = pos8;
                                varLimit = varLimit2;
                                bufStart = bufStart2;
                                quoteLimit = start4;
                                start = start3;
                                iref = iref3;
                                pos4 = limit;
                                if (pos3 == pos4) {
                                    z2 = true;
                                    this.anchorEnd = true;
                                    z = z2;
                                    pos7 = pos3;
                                    varLimit2 = varLimit;
                                    start2 = start;
                                    i3 = pos4;
                                    iref3 = iref;
                                    bufStart2 = bufStart;
                                    start3 = start2;
                                    start4 = quoteLimit;
                                } else {
                                    int r2 = UCharacter.digit(rule.charAt(pos3), 10);
                                    if (r2 < 1 || r2 > 9) {
                                        start2 = start;
                                        ParsePosition pp3 = pp == null ? new ParsePosition(0) : pp;
                                        pp3.setIndex(pos3);
                                        String name = parser.parseData.parseReference(rule, pp3, pos4);
                                        if (name == null) {
                                            z = true;
                                            this.anchorEnd = true;
                                            pp = pp3;
                                            pos7 = pos3;
                                            varLimit2 = varLimit;
                                            i3 = pos4;
                                            iref3 = iref;
                                            bufStart2 = bufStart;
                                            start3 = start2;
                                            start4 = quoteLimit;
                                        } else {
                                            z = true;
                                            int pos12 = pp3.getIndex();
                                            varStart = buf.length();
                                            parser.appendVariableDef(name, buf);
                                            varLimit2 = buf.length();
                                            pp = pp3;
                                            pos5 = pos12;
                                        }
                                    } else {
                                        iref[0] = pos3;
                                        int r3 = Utility.parseNumber(rule, iref, 10);
                                        if (r3 < 0) {
                                            start2 = start;
                                            TransliteratorParser.syntaxError("Undefined segment reference", rule, start2);
                                        } else {
                                            start2 = start;
                                        }
                                        pos5 = iref[0];
                                        buf.append(parser.getSegmentStandin(r3));
                                        varLimit2 = varLimit;
                                        z = true;
                                    }
                                    pos7 = pos5;
                                    i3 = pos4;
                                    iref3 = iref;
                                    bufStart2 = bufStart;
                                    start3 = start2;
                                    start4 = quoteLimit;
                                }
                            }
                        }
                    }
                    return pos2;
                }
                pos7 = pos8;
            }
            return pos7;
        }

        void removeContext() {
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

    /* JADX WARN: Can't wrap try/catch for region: R(15:39|(4:43|44|45|46)|118|(3:120|(3:122|(1:124)(1:126)|125)|127)|128|(9:147|148|149|150|151|152|153|154|(1:171)(5:158|(1:160)(1:170)|161|(1:163)(1:169)|(11:165|(1:167)|168|138|139|140|141|32|33|34|15)))(3:133|(1:135)(1:146)|136)|137|138|139|140|141|32|33|34|15) */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0168, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0169, code lost:
        r19 = r2;
     */
    /* JADX WARN: Incorrect condition in loop: B:25:0x00a2 */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x022c A[LOOP:3: B:122:0x0224->B:124:0x022c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x025d A[Catch: IllegalArgumentException -> 0x02ad, TryCatch #4 {IllegalArgumentException -> 0x02ad, blocks: (B:126:0x0259, B:128:0x025d, B:134:0x0266, B:138:0x026d, B:139:0x0274, B:141:0x0276, B:143:0x027e, B:144:0x028e, B:146:0x0297, B:148:0x02a6), top: B:176:0x0259 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x027e A[Catch: IllegalArgumentException -> 0x02ad, LOOP:4: B:141:0x0276->B:143:0x027e, LOOP_END, TryCatch #4 {IllegalArgumentException -> 0x02ad, blocks: (B:126:0x0259, B:128:0x025d, B:134:0x0266, B:138:0x026d, B:139:0x0274, B:141:0x0276, B:143:0x027e, B:144:0x028e, B:146:0x0297, B:148:0x02a6), top: B:176:0x0259 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02ec A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x01d8 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void parseRules(RuleBody ruleArray, int dir) {
        int compoundFilterOffset;
        int i;
        RuntimeException previous;
        int i2;
        int i3;
        int compoundFilterOffset2;
        Object obj;
        int i4;
        UnicodeSet f;
        int compoundFilterOffset3;
        int ppp;
        int compoundFilterOffset4;
        int i5;
        this.dataVector = new ArrayList();
        this.idBlockVector = new ArrayList();
        Object obj2 = null;
        this.curData = null;
        this.direction = dir;
        this.compoundFilter = null;
        this.variablesVector = new ArrayList();
        this.variableNames = new HashMap();
        this.parseData = new ParseData();
        List<RuntimeException> errors = new ArrayList<>();
        ruleArray.reset();
        StringBuilder idBlockResult = new StringBuilder();
        this.compoundFilter = null;
        int errorCount = 0;
        int ruleCount = 0;
        boolean compoundFilterOffset5 = true;
        int compoundFilterOffset6 = -1;
        loop0: while (true) {
            String rule = ruleArray.nextLine();
            int i6 = 1;
            int i7 = 0;
            if (rule != null) {
                int pos = 0;
                int limit = rule.length();
                int errorCount2 = errorCount;
                boolean parsingIDs = compoundFilterOffset5;
                int compoundFilterOffset7 = compoundFilterOffset6;
                while (pos < limit) {
                    int pos2 = pos + 1;
                    int pos3 = rule.charAt(pos);
                    if (!PatternProps.isWhiteSpace(pos3)) {
                        if (pos3 == 35) {
                            pos2 = rule.indexOf("\n", pos2) + 1;
                            if (pos2 == 0) {
                                break;
                            }
                        } else if (pos3 != 59) {
                            ruleCount++;
                            int pos4 = pos2 - 1;
                            if (pos4 + 2 + i6 <= limit) {
                                try {
                                } catch (IllegalArgumentException e) {
                                    e = e;
                                    compoundFilterOffset2 = compoundFilterOffset7;
                                }
                                if (rule.regionMatches(pos4, ID_TOKEN, i7, 2)) {
                                    pos4 += 2;
                                    char charAt = rule.charAt(pos4);
                                    while (PatternProps.isWhiteSpace(c) && pos4 < limit) {
                                        pos4++;
                                        try {
                                            charAt = rule.charAt(pos4);
                                        } catch (IllegalArgumentException e2) {
                                            e = e2;
                                            compoundFilterOffset2 = compoundFilterOffset7;
                                            compoundFilterOffset5 = parsingIDs;
                                            if (errorCount2 != 30) {
                                                IllegalIcuArgumentException icuEx = new IllegalIcuArgumentException("\nMore than 30 errors; further messages squelched");
                                                icuEx.initCause(e);
                                                errors.add(icuEx);
                                                compoundFilterOffset = compoundFilterOffset2;
                                                if (!compoundFilterOffset5) {
                                                }
                                                if (!compoundFilterOffset5) {
                                                    if (this.direction != 0) {
                                                    }
                                                }
                                                while (i < this.dataVector.size()) {
                                                }
                                                this.variablesVector = null;
                                                if (this.compoundFilter != null) {
                                                }
                                                while (i2 < this.dataVector.size()) {
                                                }
                                                if (this.idBlockVector.size() == 1) {
                                                    this.idBlockVector.remove(0);
                                                }
                                                if (errors.size() == 0) {
                                                }
                                            } else {
                                                obj = null;
                                                i7 = 0;
                                                e.fillInStackTrace();
                                                errors.add(e);
                                                errorCount2++;
                                                i4 = 1;
                                                parsingIDs = compoundFilterOffset5;
                                                pos = ruleEnd(rule, pos4, limit) + 1;
                                                compoundFilterOffset7 = compoundFilterOffset2;
                                                obj2 = obj;
                                                i6 = i4;
                                            }
                                        }
                                    }
                                    int[] p = new int[i6];
                                    p[i7] = pos4;
                                    if (!parsingIDs) {
                                        if (this.curData != null) {
                                            if (this.direction == 0) {
                                                this.dataVector.add(this.curData);
                                            } else {
                                                this.dataVector.add(i7, this.curData);
                                            }
                                            this.curData = null;
                                        }
                                        parsingIDs = true;
                                    }
                                    TransliteratorIDParser.SingleID id = TransliteratorIDParser.parseSingleID(rule, p, this.direction);
                                    if (p[i7] != pos4 && Utility.parseChar(rule, p, ';')) {
                                        if (this.direction == 0) {
                                            idBlockResult.append(id.canonID);
                                            idBlockResult.append(';');
                                        } else {
                                            idBlockResult.insert(0, id.canonID + ';');
                                        }
                                        compoundFilterOffset2 = compoundFilterOffset7;
                                    } else {
                                        int[] withParens = new int[1];
                                        try {
                                            withParens[0] = -1;
                                            compoundFilterOffset2 = compoundFilterOffset7;
                                        } catch (IllegalArgumentException e3) {
                                            e = e3;
                                            compoundFilterOffset2 = compoundFilterOffset7;
                                            compoundFilterOffset5 = parsingIDs;
                                            if (errorCount2 != 30) {
                                            }
                                        }
                                        try {
                                            f = TransliteratorIDParser.parseGlobalFilter(rule, p, this.direction, withParens, null);
                                        } catch (IllegalArgumentException e4) {
                                            e = e4;
                                            compoundFilterOffset5 = parsingIDs;
                                            if (errorCount2 != 30) {
                                            }
                                        }
                                        if (f == null || !Utility.parseChar(rule, p, ';')) {
                                            syntaxError("Invalid ::ID", rule, pos4);
                                        } else if ((this.direction == 0) == (withParens[0] == 0)) {
                                            if (this.compoundFilter != null) {
                                                syntaxError("Multiple global filters", rule, pos4);
                                            }
                                            this.compoundFilter = f;
                                            compoundFilterOffset3 = ruleCount;
                                            ppp = p[0];
                                            compoundFilterOffset4 = compoundFilterOffset3;
                                            pos = ppp;
                                            compoundFilterOffset7 = compoundFilterOffset4;
                                            obj = null;
                                            i7 = 0;
                                            i4 = 1;
                                            obj2 = obj;
                                            i6 = i4;
                                        }
                                    }
                                    compoundFilterOffset3 = compoundFilterOffset2;
                                    ppp = p[0];
                                    compoundFilterOffset4 = compoundFilterOffset3;
                                    pos = ppp;
                                    compoundFilterOffset7 = compoundFilterOffset4;
                                    obj = null;
                                    i7 = 0;
                                    i4 = 1;
                                    obj2 = obj;
                                    i6 = i4;
                                }
                            }
                            compoundFilterOffset4 = compoundFilterOffset7;
                            if (parsingIDs) {
                                if (this.direction == 0) {
                                    this.idBlockVector.add(idBlockResult.toString());
                                    i5 = 0;
                                } else {
                                    i5 = 0;
                                    this.idBlockVector.add(0, idBlockResult.toString());
                                }
                                idBlockResult.delete(i5, idBlockResult.length());
                                parsingIDs = false;
                                this.curData = new RuleBasedTransliterator.Data();
                                setVariableRange(61440, 63743);
                            }
                            if (resemblesPragma(rule, pos4, limit)) {
                                ppp = parsePragma(rule, pos4, limit);
                                if (ppp < 0) {
                                    syntaxError("Unrecognized pragma", rule, pos4);
                                }
                            } else {
                                ppp = parseRule(rule, pos4, limit);
                            }
                            pos = ppp;
                            compoundFilterOffset7 = compoundFilterOffset4;
                            obj = null;
                            i7 = 0;
                            i4 = 1;
                            obj2 = obj;
                            i6 = i4;
                        }
                    }
                    pos = pos2;
                }
                int compoundFilterOffset8 = compoundFilterOffset7;
                compoundFilterOffset5 = parsingIDs;
                obj2 = obj2;
                errorCount = errorCount2;
                compoundFilterOffset6 = compoundFilterOffset8;
            } else {
                compoundFilterOffset = compoundFilterOffset6;
                break;
            }
        }
        if (!compoundFilterOffset5 && idBlockResult.length() > 0) {
            if (this.direction == 0) {
                this.idBlockVector.add(idBlockResult.toString());
            } else {
                this.idBlockVector.add(0, idBlockResult.toString());
            }
        } else if (!compoundFilterOffset5 && this.curData != null) {
            if (this.direction != 0) {
                this.dataVector.add(this.curData);
            } else {
                this.dataVector.add(0, this.curData);
            }
        }
        for (i = 0; i < this.dataVector.size(); i++) {
            RuleBasedTransliterator.Data data = this.dataVector.get(i);
            data.variables = new Object[this.variablesVector.size()];
            this.variablesVector.toArray(data.variables);
            data.variableNames = new HashMap();
            data.variableNames.putAll(this.variableNames);
        }
        this.variablesVector = null;
        try {
            if (this.compoundFilter != null) {
                if (this.direction == 0) {
                    i3 = 1;
                    if (compoundFilterOffset == 1) {
                    }
                    throw new IllegalIcuArgumentException("Compound filters misplaced");
                }
                i3 = 1;
                if (this.direction == i3) {
                    if (compoundFilterOffset == ruleCount) {
                    }
                    throw new IllegalIcuArgumentException("Compound filters misplaced");
                }
            }
            for (i2 = 0; i2 < this.dataVector.size(); i2++) {
                this.dataVector.get(i2).ruleSet.freeze();
            }
            if (this.idBlockVector.size() == 1 && this.idBlockVector.get(0).length() == 0) {
                this.idBlockVector.remove(0);
            }
        } catch (IllegalArgumentException e5) {
            e5.fillInStackTrace();
            errors.add(e5);
        }
        if (errors.size() == 0) {
            for (int i8 = errors.size() - 1; i8 > 0; i8--) {
                RuntimeException previous2 = errors.get(i8 - 1);
                while (true) {
                    previous = (RuntimeException) previous2;
                    if (previous.getCause() != null) {
                        previous2 = previous.getCause();
                    }
                }
                previous.initCause(errors.get(i8));
            }
            throw errors.get(0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0039, code lost:
        if (com.ibm.icu.text.TransliteratorParser.OPERATORS.indexOf(r9) < 0) goto L122;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int parseRule(String rule, int pos, int limit) {
        char operator = 0;
        this.segmentStandins = new StringBuffer();
        this.segmentObjects = new ArrayList();
        RuleHalf left = new RuleHalf();
        RuleHalf right = new RuleHalf();
        this.undefinedVariableName = null;
        int pos2 = left.parse(rule, pos, limit, this);
        if (pos2 != limit) {
            pos2--;
            char charAt = rule.charAt(pos2);
            operator = charAt;
        }
        syntaxError("No operator pos=" + pos2, rule, pos);
        int pos3 = pos2 + 1;
        if (operator == '<' && pos3 < limit && rule.charAt(pos3) == '>') {
            pos3++;
            operator = FWDREV_RULE_OP;
        }
        if (operator == '\u2190') {
            operator = REVERSE_RULE_OP;
        } else if (operator == '\u2192') {
            operator = FORWARD_RULE_OP;
        } else if (operator == '\u2194') {
            operator = FWDREV_RULE_OP;
        }
        int pos4 = right.parse(rule, pos3, limit, this);
        if (pos4 < limit) {
            pos4--;
            if (rule.charAt(pos4) == ';') {
                pos4++;
            } else {
                syntaxError("Unquoted operator", rule, pos);
            }
        }
        if (operator == '=') {
            if (this.undefinedVariableName == null) {
                syntaxError("Missing '$' or duplicate definition", rule, pos);
            }
            if (left.text.length() != 1 || left.text.charAt(0) != this.variableLimit) {
                syntaxError("Malformed LHS", rule, pos);
            }
            if (left.anchorStart || left.anchorEnd || right.anchorStart || right.anchorEnd) {
                syntaxError("Malformed variable def", rule, pos);
            }
            int n = right.text.length();
            char[] value = new char[n];
            right.text.getChars(0, n, value, 0);
            this.variableNames.put(this.undefinedVariableName, value);
            this.variableLimit = (char) (this.variableLimit + 1);
            return pos4;
        }
        if (this.undefinedVariableName != null) {
            syntaxError("Undefined variable $" + this.undefinedVariableName, rule, pos);
        }
        if (this.segmentStandins.length() > this.segmentObjects.size()) {
            syntaxError("Undefined segment reference", rule, pos);
        }
        for (int i = 0; i < this.segmentStandins.length(); i++) {
            if (this.segmentStandins.charAt(i) == 0) {
                syntaxError("Internal error", rule, pos);
            }
        }
        for (int i2 = 0; i2 < this.segmentObjects.size(); i2++) {
            if (this.segmentObjects.get(i2) == null) {
                syntaxError("Internal error", rule, pos);
            }
        }
        if (operator != '~') {
            if ((this.direction == 0) != (operator == '>')) {
                return pos4;
            }
        }
        if (this.direction == 1) {
            left = right;
            right = left;
        }
        if (operator == '~') {
            right.removeContext();
            left.cursor = -1;
            left.cursorOffset = 0;
        }
        if (left.ante < 0) {
            left.ante = 0;
        }
        if (left.post < 0) {
            left.post = left.text.length();
        }
        if (right.ante >= 0 || right.post >= 0 || left.cursor >= 0 || ((right.cursorOffset != 0 && right.cursor < 0) || right.anchorStart || right.anchorEnd || !left.isValidInput(this) || !right.isValidOutput(this) || left.ante > left.post)) {
            syntaxError("Malformed rule", rule, pos);
        }
        UnicodeMatcher[] segmentsArray = null;
        if (this.segmentObjects.size() > 0) {
            segmentsArray = new UnicodeMatcher[this.segmentObjects.size()];
            this.segmentObjects.toArray(segmentsArray);
        }
        this.curData.ruleSet.addRule(new TransliterationRule(left.text, left.ante, left.post, right.text, right.cursor, right.cursorOffset, segmentsArray, left.anchorStart, left.anchorEnd, this.curData));
        return pos4;
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

    /* JADX INFO: Access modifiers changed from: private */
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
        if (p4 >= 0) {
            pragmaNormalizeRules(Normalizer.NFC);
            return p4;
        }
        return -1;
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

    /* JADX INFO: Access modifiers changed from: private */
    public final char parseSet(String rule, ParsePosition pos) {
        UnicodeSet set = new UnicodeSet(rule, pos, this.parseData);
        if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Private use variables exhausted");
        }
        set.compact();
        return generateStandInFor(set);
    }

    char generateStandInFor(Object obj) {
        for (int i = 0; i < this.variablesVector.size(); i++) {
            if (this.variablesVector.get(i) == obj) {
                return (char) (this.curData.variablesBase + i);
            }
        }
        int i2 = this.variableNext;
        if (i2 >= this.variableLimit) {
            throw new RuntimeException("Variable range exhausted");
        }
        this.variablesVector.add(obj);
        char c = this.variableNext;
        this.variableNext = (char) (c + 1);
        return c;
    }

    public char getSegmentStandin(int seg) {
        if (this.segmentStandins.length() < seg) {
            this.segmentStandins.setLength(seg);
        }
        char c = this.segmentStandins.charAt(seg - 1);
        if (c == 0) {
            if (this.variableNext >= this.variableLimit) {
                throw new RuntimeException("Variable range exhausted");
            }
            char c2 = this.variableNext;
            this.variableNext = (char) (c2 + 1);
            this.variablesVector.add(null);
            this.segmentStandins.setCharAt(seg - 1, c2);
            return c2;
        }
        return c;
    }

    public void setSegmentObject(int seg, StringMatcher obj) {
        while (this.segmentObjects.size() < seg) {
            this.segmentObjects.add(null);
        }
        int index = getSegmentStandin(seg) - this.curData.variablesBase;
        if (this.segmentObjects.get(seg - 1) != null || this.variablesVector.get(index) != null) {
            throw new RuntimeException();
        }
        this.segmentObjects.set(seg - 1, obj);
        this.variablesVector.set(index, obj);
    }

    char getDotStandIn() {
        if (this.dotStandIn == -1) {
            this.dotStandIn = generateStandInFor(new UnicodeSet(DOT_SET));
        }
        return (char) this.dotStandIn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendVariableDef(String name, StringBuffer buf) {
        char[] ch = this.variableNames.get(name);
        if (ch == null) {
            if (this.undefinedVariableName == null) {
                this.undefinedVariableName = name;
                if (this.variableNext >= this.variableLimit) {
                    throw new RuntimeException("Private use variables exhausted");
                }
                char c = (char) (this.variableLimit - 1);
                this.variableLimit = c;
                buf.append(c);
                return;
            }
            throw new IllegalIcuArgumentException("Undefined variable $" + name);
        }
        buf.append(ch);
    }
}
