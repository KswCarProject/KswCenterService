package com.ibm.icu.text;

import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes5.dex */
public final class MessagePattern implements Cloneable, Freezable<MessagePattern> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ARG_NAME_NOT_NUMBER = -1;
    public static final int ARG_NAME_NOT_VALID = -2;
    private static final int MAX_PREFIX_LENGTH = 24;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8d;
    private ApostropheMode aposMode;
    private volatile boolean frozen;
    private boolean hasArgNames;
    private boolean hasArgNumbers;
    private String msg;
    private boolean needsAutoQuoting;
    private ArrayList<Double> numericValues;
    private ArrayList<Part> parts;
    private static final ApostropheMode defaultAposMode = ApostropheMode.valueOf(ICUConfig.get("com.ibm.icu.text.MessagePattern.ApostropheMode", "DOUBLE_OPTIONAL"));
    private static final ArgType[] argTypes = ArgType.values();

    /* loaded from: classes5.dex */
    public enum ApostropheMode {
        DOUBLE_OPTIONAL,
        DOUBLE_REQUIRED
    }

    public MessagePattern() {
        this.parts = new ArrayList<>();
        this.aposMode = defaultAposMode;
    }

    public MessagePattern(ApostropheMode mode) {
        this.parts = new ArrayList<>();
        this.aposMode = mode;
    }

    public MessagePattern(String pattern) {
        this.parts = new ArrayList<>();
        this.aposMode = defaultAposMode;
        parse(pattern);
    }

    public MessagePattern parse(String pattern) {
        preParse(pattern);
        parseMessage(0, 0, 0, ArgType.NONE);
        postParse();
        return this;
    }

    public MessagePattern parseChoiceStyle(String pattern) {
        preParse(pattern);
        parseChoiceStyle(0, 0);
        postParse();
        return this;
    }

    public MessagePattern parsePluralStyle(String pattern) {
        preParse(pattern);
        parsePluralOrSelectStyle(ArgType.PLURAL, 0, 0);
        postParse();
        return this;
    }

    public MessagePattern parseSelectStyle(String pattern) {
        preParse(pattern);
        parsePluralOrSelectStyle(ArgType.SELECT, 0, 0);
        postParse();
        return this;
    }

    public void clear() {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to clear() a frozen MessagePattern instance.");
        }
        this.msg = null;
        this.hasArgNumbers = false;
        this.hasArgNames = false;
        this.needsAutoQuoting = false;
        this.parts.clear();
        if (this.numericValues != null) {
            this.numericValues.clear();
        }
    }

    public void clearPatternAndSetApostropheMode(ApostropheMode mode) {
        clear();
        this.aposMode = mode;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MessagePattern o = (MessagePattern) other;
        if (this.aposMode.equals(o.aposMode) && (this.msg != null ? this.msg.equals(o.msg) : o.msg == null) && this.parts.equals(o.parts)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.aposMode.hashCode() * 37) + (this.msg != null ? this.msg.hashCode() : 0)) * 37) + this.parts.hashCode();
    }

    public ApostropheMode getApostropheMode() {
        return this.aposMode;
    }

    boolean jdkAposMode() {
        return this.aposMode == ApostropheMode.DOUBLE_REQUIRED;
    }

    public String getPatternString() {
        return this.msg;
    }

    public boolean hasNamedArguments() {
        return this.hasArgNames;
    }

    public boolean hasNumberedArguments() {
        return this.hasArgNumbers;
    }

    public String toString() {
        return this.msg;
    }

    public static int validateArgumentName(String name) {
        if (!PatternProps.isIdentifier(name)) {
            return -2;
        }
        return parseArgNumber(name, 0, name.length());
    }

    public String autoQuoteApostropheDeep() {
        if (!this.needsAutoQuoting) {
            return this.msg;
        }
        int count = countParts();
        StringBuilder modified = null;
        int i = count;
        while (i > 0) {
            i--;
            Part part = getPart(i);
            if (part.getType() == Part.Type.INSERT_CHAR) {
                if (modified == null) {
                    modified = new StringBuilder(this.msg.length() + 10).append(this.msg);
                }
                modified.insert(part.index, (char) part.value);
            }
        }
        if (modified == null) {
            return this.msg;
        }
        return modified.toString();
    }

    public int countParts() {
        return this.parts.size();
    }

    public Part getPart(int i) {
        return this.parts.get(i);
    }

    public Part.Type getPartType(int i) {
        return this.parts.get(i).type;
    }

    public int getPatternIndex(int partIndex) {
        return this.parts.get(partIndex).index;
    }

    public String getSubstring(Part part) {
        int index = part.index;
        return this.msg.substring(index, part.length + index);
    }

    public boolean partSubstringMatches(Part part, String s) {
        return part.length == s.length() && this.msg.regionMatches(part.index, s, 0, part.length);
    }

    public double getNumericValue(Part part) {
        Part.Type type = part.type;
        if (type != Part.Type.ARG_INT) {
            if (type != Part.Type.ARG_DOUBLE) {
                return -1.23456789E8d;
            }
            return this.numericValues.get(part.value).doubleValue();
        }
        return part.value;
    }

    public double getPluralOffset(int pluralStart) {
        Part part = this.parts.get(pluralStart);
        if (part.type.hasNumericValue()) {
            return getNumericValue(part);
        }
        return 0.0d;
    }

    public int getLimitPartIndex(int start) {
        int limit = this.parts.get(start).limitPartIndex;
        if (limit < start) {
            return start;
        }
        return limit;
    }

    /* loaded from: classes5.dex */
    public static final class Part {
        private static final int MAX_LENGTH = 65535;
        private static final int MAX_VALUE = 32767;
        private final int index;
        private final char length;
        private int limitPartIndex;
        private final Type type;
        private short value;

        private Part(Type t, int i, int l, int v) {
            this.type = t;
            this.index = i;
            this.length = (char) l;
            this.value = (short) v;
        }

        public Type getType() {
            return this.type;
        }

        public int getIndex() {
            return this.index;
        }

        public int getLength() {
            return this.length;
        }

        public int getLimit() {
            return this.index + this.length;
        }

        public int getValue() {
            return this.value;
        }

        public ArgType getArgType() {
            Type type = getType();
            if (type == Type.ARG_START || type == Type.ARG_LIMIT) {
                return MessagePattern.argTypes[this.value];
            }
            return ArgType.NONE;
        }

        /* loaded from: classes5.dex */
        public enum Type {
            MSG_START,
            MSG_LIMIT,
            SKIP_SYNTAX,
            INSERT_CHAR,
            REPLACE_NUMBER,
            ARG_START,
            ARG_LIMIT,
            ARG_NUMBER,
            ARG_NAME,
            ARG_TYPE,
            ARG_STYLE,
            ARG_SELECTOR,
            ARG_INT,
            ARG_DOUBLE;

            public boolean hasNumericValue() {
                return this == ARG_INT || this == ARG_DOUBLE;
            }
        }

        public String toString() {
            String valueString = (this.type == Type.ARG_START || this.type == Type.ARG_LIMIT) ? getArgType().name() : Integer.toString(this.value);
            return this.type.name() + "(" + valueString + ")@" + this.index;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Part o = (Part) other;
            if (this.type.equals(o.type) && this.index == o.index && this.length == o.length && this.value == o.value && this.limitPartIndex == o.limitPartIndex) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((this.type.hashCode() * 37) + this.index) * 37) + this.length) * 37) + this.value;
        }
    }

    /* loaded from: classes5.dex */
    public enum ArgType {
        NONE,
        SIMPLE,
        CHOICE,
        PLURAL,
        SELECT,
        SELECTORDINAL;

        public boolean hasPluralStyle() {
            return this == PLURAL || this == SELECTORDINAL;
        }
    }

    public Object clone() {
        if (isFrozen()) {
            return this;
        }
        return m203cloneAsThawed();
    }

    /* renamed from: cloneAsThawed */
    public MessagePattern m203cloneAsThawed() {
        try {
            MessagePattern newMsg = (MessagePattern) super.clone();
            newMsg.parts = (ArrayList) this.parts.clone();
            if (this.numericValues != null) {
                newMsg.numericValues = (ArrayList) this.numericValues.clone();
            }
            newMsg.frozen = false;
            return newMsg;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    /* renamed from: freeze */
    public MessagePattern m204freeze() {
        this.frozen = true;
        return this;
    }

    public boolean isFrozen() {
        return this.frozen;
    }

    private void preParse(String pattern) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to parse(" + prefix(pattern) + ") on frozen MessagePattern instance.");
        }
        this.msg = pattern;
        this.hasArgNumbers = false;
        this.hasArgNames = false;
        this.needsAutoQuoting = false;
        this.parts.clear();
        if (this.numericValues != null) {
            this.numericValues.clear();
        }
    }

    private void postParse() {
    }

    private int parseMessage(int index, int msgStartLength, int nestingLevel, ArgType parentType) {
        int index2;
        int index3;
        if (nestingLevel <= 32767) {
            int msgStart = this.parts.size();
            addPart(Part.Type.MSG_START, index, msgStartLength, nestingLevel);
            int index4 = index + msgStartLength;
            while (index4 < this.msg.length()) {
                int index5 = index4 + 1;
                int index6 = this.msg.charAt(index4);
                if (index6 == 39) {
                    if (index5 == this.msg.length()) {
                        addPart(Part.Type.INSERT_CHAR, index5, 0, 39);
                        this.needsAutoQuoting = true;
                    } else {
                        char c = this.msg.charAt(index5);
                        if (c == '\'') {
                            addPart(Part.Type.SKIP_SYNTAX, index5, 1, 0);
                            index4 = index5 + 1;
                        } else if (this.aposMode == ApostropheMode.DOUBLE_REQUIRED || c == '{' || c == '}' || ((parentType == ArgType.CHOICE && c == '|') || (parentType.hasPluralStyle() && c == '#'))) {
                            addPart(Part.Type.SKIP_SYNTAX, index5 - 1, 1, 0);
                            while (true) {
                                index3 = this.msg.indexOf(39, index5 + 1);
                                if (index3 < 0) {
                                    index2 = this.msg.length();
                                    addPart(Part.Type.INSERT_CHAR, index2, 0, 39);
                                    this.needsAutoQuoting = true;
                                    break;
                                } else if (index3 + 1 >= this.msg.length() || this.msg.charAt(index3 + 1) != '\'') {
                                    break;
                                } else {
                                    index5 = index3 + 1;
                                    addPart(Part.Type.SKIP_SYNTAX, index5, 1, 0);
                                }
                            }
                            addPart(Part.Type.SKIP_SYNTAX, index3, 1, 0);
                            index4 = index3 + 1;
                        } else {
                            addPart(Part.Type.INSERT_CHAR, index5, 0, 39);
                            this.needsAutoQuoting = true;
                        }
                    }
                    index4 = index5;
                } else {
                    if (parentType.hasPluralStyle() && index6 == 35) {
                        addPart(Part.Type.REPLACE_NUMBER, index5 - 1, 1, 0);
                    } else if (index6 == 123) {
                        index2 = parseArg(index5 - 1, 1, nestingLevel);
                        index4 = index2;
                    } else if ((nestingLevel > 0 && index6 == 125) || (parentType == ArgType.CHOICE && index6 == 124)) {
                        int limitLength = (parentType == ArgType.CHOICE && index6 == 125) ? 0 : 1;
                        addLimitPart(msgStart, Part.Type.MSG_LIMIT, index5 - 1, limitLength, nestingLevel);
                        return parentType == ArgType.CHOICE ? index5 - 1 : index5;
                    }
                    index4 = index5;
                }
            }
            if (nestingLevel <= 0 || inTopLevelChoiceMessage(nestingLevel, parentType)) {
                addLimitPart(msgStart, Part.Type.MSG_LIMIT, index4, 0, nestingLevel);
                return index4;
            }
            throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
        }
        throw new IndexOutOfBoundsException();
    }

    private int parseArg(int index, int argStartLength, int nestingLevel) {
        int argStart = this.parts.size();
        ArgType argType = ArgType.NONE;
        addPart(Part.Type.ARG_START, index, argStartLength, argType.ordinal());
        int index2 = skipWhiteSpace(index + argStartLength);
        if (index2 == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
        }
        int index3 = skipIdentifier(index2);
        int number = parseArgNumber(index2, index3);
        if (number >= 0) {
            int length = index3 - index2;
            if (length > 65535 || number > 32767) {
                throw new IndexOutOfBoundsException("Argument number too large: " + prefix(index2));
            }
            this.hasArgNumbers = true;
            addPart(Part.Type.ARG_NUMBER, index2, length, number);
        } else if (number == -1) {
            int length2 = index3 - index2;
            if (length2 > 65535) {
                throw new IndexOutOfBoundsException("Argument name too long: " + prefix(index2));
            }
            this.hasArgNames = true;
            addPart(Part.Type.ARG_NAME, index2, length2, 0);
        } else {
            throw new IllegalArgumentException("Bad argument syntax: " + prefix(index2));
        }
        int index4 = skipWhiteSpace(index3);
        if (index4 == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
        }
        char c = this.msg.charAt(index4);
        if (c != '}') {
            if (c != ',') {
                throw new IllegalArgumentException("Bad argument syntax: " + prefix(index2));
            }
            int typeIndex = skipWhiteSpace(index4 + 1);
            int index5 = typeIndex;
            while (index5 < this.msg.length() && isArgTypeChar(this.msg.charAt(index5))) {
                index5++;
            }
            int length3 = index5 - typeIndex;
            index4 = skipWhiteSpace(index5);
            if (index4 == this.msg.length()) {
                throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
            }
            if (length3 != 0) {
                char charAt = this.msg.charAt(index4);
                c = charAt;
                if (charAt == ',' || c == '}') {
                    if (length3 > 65535) {
                        throw new IndexOutOfBoundsException("Argument type name too long: " + prefix(index2));
                    }
                    argType = ArgType.SIMPLE;
                    if (length3 == 6) {
                        if (isChoice(typeIndex)) {
                            argType = ArgType.CHOICE;
                        } else if (isPlural(typeIndex)) {
                            argType = ArgType.PLURAL;
                        } else if (isSelect(typeIndex)) {
                            argType = ArgType.SELECT;
                        }
                    } else if (length3 == 13 && isSelect(typeIndex) && isOrdinal(typeIndex + 6)) {
                        argType = ArgType.SELECTORDINAL;
                    }
                    this.parts.get(argStart).value = (short) argType.ordinal();
                    if (argType == ArgType.SIMPLE) {
                        addPart(Part.Type.ARG_TYPE, typeIndex, length3, 0);
                    }
                    if (c == '}') {
                        if (argType != ArgType.SIMPLE) {
                            throw new IllegalArgumentException("No style field for complex argument: " + prefix(index2));
                        }
                    } else {
                        int index6 = index4 + 1;
                        if (argType == ArgType.SIMPLE) {
                            index4 = parseSimpleStyle(index6);
                        } else if (argType == ArgType.CHOICE) {
                            index4 = parseChoiceStyle(index6, nestingLevel);
                        } else {
                            index4 = parsePluralOrSelectStyle(argType, index6, nestingLevel);
                        }
                    }
                }
            }
            throw new IllegalArgumentException("Bad argument syntax: " + prefix(index2));
        }
        addLimitPart(argStart, Part.Type.ARG_LIMIT, index4, 1, argType.ordinal());
        return index4 + 1;
    }

    private int parseSimpleStyle(int index) {
        int index2 = index;
        int nestedBraces = 0;
        while (index2 < this.msg.length()) {
            int index3 = index2 + 1;
            int index4 = this.msg.charAt(index2);
            if (index4 == 39) {
                int index5 = this.msg.indexOf(39, index3);
                if (index5 < 0) {
                    throw new IllegalArgumentException("Quoted literal argument style text reaches to the end of the message: " + prefix(index));
                }
                index2 = index5 + 1;
            } else {
                if (index4 == 123) {
                    nestedBraces++;
                } else if (index4 == 125) {
                    if (nestedBraces > 0) {
                        nestedBraces--;
                    } else {
                        int index6 = index3 - 1;
                        int length = index6 - index;
                        if (length <= 65535) {
                            addPart(Part.Type.ARG_STYLE, index, length, 0);
                            return index6;
                        }
                        throw new IndexOutOfBoundsException("Argument style text too long: " + prefix(index));
                    }
                }
                index2 = index3;
            }
        }
        throw new IllegalArgumentException("Unmatched '{' braces in message " + prefix());
    }

    private int parseChoiceStyle(int index, int nestingLevel) {
        int index2 = skipWhiteSpace(index);
        if (index2 == this.msg.length() || this.msg.charAt(index2) == '}') {
            throw new IllegalArgumentException("Missing choice argument pattern in " + prefix());
        }
        while (true) {
            int numberIndex = index2;
            int index3 = skipDouble(index2);
            int length = index3 - numberIndex;
            if (length == 0) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + prefix(index));
            } else if (length > 65535) {
                throw new IndexOutOfBoundsException("Choice number too long: " + prefix(numberIndex));
            } else {
                parseDouble(numberIndex, index3, true);
                int index4 = skipWhiteSpace(index3);
                if (index4 == this.msg.length()) {
                    throw new IllegalArgumentException("Bad choice pattern syntax: " + prefix(index));
                }
                char c = this.msg.charAt(index4);
                if (c == '#' || c == '<' || c == '\u2264') {
                    addPart(Part.Type.ARG_SELECTOR, index4, 1, 0);
                    int index5 = parseMessage(index4 + 1, 0, nestingLevel + 1, ArgType.CHOICE);
                    if (index5 == this.msg.length()) {
                        return index5;
                    }
                    if (this.msg.charAt(index5) == '}') {
                        if (!inMessageFormatPattern(nestingLevel)) {
                            throw new IllegalArgumentException("Bad choice pattern syntax: " + prefix(index));
                        }
                        return index5;
                    }
                    index2 = skipWhiteSpace(index5 + 1);
                } else {
                    throw new IllegalArgumentException("Expected choice separator (#<\u2264) instead of '" + c + "' in choice pattern " + prefix(index));
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0127, code lost:
        addPart(com.ibm.icu.text.MessagePattern.Part.Type.ARG_SELECTOR, r1, r7, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0134, code lost:
        if (r12.msg.regionMatches(r1, "other", 0, r7) == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0136, code lost:
        r14 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0182, code lost:
        throw new java.lang.IllegalArgumentException("No message fragment after " + r13.toString().toLowerCase(java.util.Locale.ENGLISH) + " selector: " + prefix(r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01cf, code lost:
        if (r4 == inMessageFormatPattern(r15)) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01d1, code lost:
        if (r14 == 0) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01d3, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0200, code lost:
        throw new java.lang.IllegalArgumentException("Missing 'other' keyword in " + r13.toString().toLowerCase(java.util.Locale.ENGLISH) + " pattern in " + prefix());
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x022d, code lost:
        throw new java.lang.IllegalArgumentException("Bad " + r13.toString().toLowerCase(java.util.Locale.ENGLISH) + " pattern syntax: " + prefix(r14));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int parsePluralOrSelectStyle(ArgType argType, int index, int nestingLevel) {
        int index2;
        boolean isEmpty = true;
        int index3 = index;
        int index4 = 0;
        while (true) {
            int index5 = skipWhiteSpace(index3);
            boolean eos = index5 == this.msg.length();
            if (eos || this.msg.charAt(index5) == '}') {
                break;
            }
            if (argType.hasPluralStyle() && this.msg.charAt(index5) == '=') {
                index2 = skipDouble(index5 + 1);
                int length = index2 - index5;
                if (length == 1) {
                    throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + prefix(index));
                } else if (length <= 65535) {
                    addPart(Part.Type.ARG_SELECTOR, index5, length, 0);
                    parseDouble(index5 + 1, index2, false);
                } else {
                    throw new IndexOutOfBoundsException("Argument selector too long: " + prefix(index5));
                }
            } else {
                index2 = skipIdentifier(index5);
                int length2 = index2 - index5;
                if (length2 == 0) {
                    throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + prefix(index));
                } else if (argType.hasPluralStyle() && length2 == 6 && index2 < this.msg.length() && this.msg.regionMatches(index5, "offset:", 0, 7)) {
                    if (!isEmpty) {
                        throw new IllegalArgumentException("Plural argument 'offset:' (if present) must precede key-message pairs: " + prefix(index));
                    }
                    int valueIndex = skipWhiteSpace(index2 + 1);
                    index3 = skipDouble(valueIndex);
                    if (index3 == valueIndex) {
                        throw new IllegalArgumentException("Missing value for plural 'offset:' " + prefix(index));
                    } else if (index3 - valueIndex > 65535) {
                        throw new IndexOutOfBoundsException("Plural offset value too long: " + prefix(valueIndex));
                    } else {
                        parseDouble(valueIndex, index3, false);
                        isEmpty = false;
                    }
                } else {
                    throw new IndexOutOfBoundsException("Argument selector too long: " + prefix(index5));
                }
            }
            int index6 = skipWhiteSpace(index2);
            if (index6 == this.msg.length() || this.msg.charAt(index6) != '{') {
                break;
            }
            index3 = parseMessage(index6, 1, nestingLevel + 1, argType);
            isEmpty = false;
        }
    }

    private static int parseArgNumber(CharSequence s, int start, int limit) {
        int number;
        boolean badNumber;
        if (start >= limit) {
            return -2;
        }
        int start2 = start + 1;
        int start3 = s.charAt(start);
        if (start3 == 48) {
            if (start2 == limit) {
                return 0;
            }
            number = 0;
            badNumber = true;
        } else if (49 > start3 || start3 > 57) {
            return -1;
        } else {
            number = start3 - 48;
            badNumber = false;
        }
        while (start2 < limit) {
            int start4 = start2 + 1;
            char c = s.charAt(start2);
            if ('0' > c || c > '9') {
                return -1;
            }
            if (number >= 214748364) {
                badNumber = true;
            }
            number = (number * 10) + (c - '0');
            start2 = start4;
        }
        if (badNumber) {
            return -2;
        }
        return number;
    }

    private int parseArgNumber(int start, int limit) {
        return parseArgNumber(this.msg, start, limit);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0060 A[LOOP:0: B:25:0x0060->B:39:0x0082, LOOP_START, PHI: r0 r2 r5 
      PHI: (r0v2 'value' int) = (r0v0 'value' int), (r0v3 'value' int) binds: [B:15:0x0030, B:39:0x0082] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r2v7 'c' char) = (r2v6 'c' char), (r2v8 'c' char) binds: [B:15:0x0030, B:39:0x0082] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r5v3 'index' int) = (r5v2 'index' int), (r5v4 'index' int) binds: [B:15:0x0030, B:39:0x0082] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseDouble(int start, int limit, boolean allowInfinity) {
        int index;
        int value = 0;
        int isNegative = 0;
        int index2 = start + 1;
        char c = this.msg.charAt(start);
        if (c == '-') {
            isNegative = 1;
            if (index2 != limit) {
                index = index2 + 1;
                c = this.msg.charAt(index2);
                if (c != '\u221e') {
                    if (allowInfinity && index == limit) {
                        addArgDoublePart(isNegative != 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, start, limit - start);
                        return;
                    }
                } else {
                    while ('0' <= c && c <= '9' && (value = (value * 10) + (c - '0')) <= isNegative + 32767) {
                        if (index == limit) {
                            addPart(Part.Type.ARG_INT, start, limit - start, isNegative != 0 ? -value : value);
                            return;
                        } else {
                            c = this.msg.charAt(index);
                            index++;
                        }
                    }
                    double numericValue = Double.parseDouble(this.msg.substring(start, limit));
                    addArgDoublePart(numericValue, start, limit - start);
                    return;
                }
            }
            throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(start, limit));
        }
        if (c == '+') {
            if (index2 != limit) {
                index = index2 + 1;
                c = this.msg.charAt(index2);
            }
            throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(start, limit));
        }
        index = index2;
        if (c != '\u221e') {
        }
    }

    static void appendReducedApostrophes(String s, int start, int limit, StringBuilder sb) {
        int doubleApos = -1;
        while (true) {
            int i = s.indexOf(39, start);
            if (i < 0 || i >= limit) {
                break;
            } else if (i == doubleApos) {
                sb.append(android.text.format.DateFormat.QUOTE);
                start++;
                doubleApos = -1;
            } else {
                sb.append((CharSequence) s, start, i);
                int i2 = i + 1;
                start = i2;
                doubleApos = i2;
            }
        }
        sb.append((CharSequence) s, start, limit);
    }

    private int skipWhiteSpace(int index) {
        return PatternProps.skipWhiteSpace(this.msg, index);
    }

    private int skipIdentifier(int index) {
        return PatternProps.skipIdentifier(this.msg, index);
    }

    private int skipDouble(int index) {
        char c;
        while (index < this.msg.length() && (((c = this.msg.charAt(index)) >= '0' || "+-.".indexOf(c) >= 0) && (c <= '9' || c == 'e' || c == 'E' || c == '\u221e'))) {
            index++;
        }
        return index;
    }

    private static boolean isArgTypeChar(int c) {
        return (97 <= c && c <= 122) || (65 <= c && c <= 90);
    }

    private boolean isChoice(int index) {
        char c;
        int index2 = index + 1;
        int index3 = this.msg.charAt(index);
        if (index3 == 99 || index3 == 67) {
            int index4 = index2 + 1;
            char c2 = this.msg.charAt(index2);
            if (c2 == 'h' || c2 == 'H') {
                int index5 = index4 + 1;
                char c3 = this.msg.charAt(index4);
                if (c3 == 'o' || c3 == 'O') {
                    index4 = index5 + 1;
                    char c4 = this.msg.charAt(index5);
                    if (c4 == 'i' || c4 == 'I') {
                        int index6 = index4 + 1;
                        char c5 = this.msg.charAt(index4);
                        if ((c5 == 'c' || c5 == 'C') && ((c = this.msg.charAt(index6)) == 'e' || c == 'E')) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isPlural(int index) {
        char c;
        int index2 = index + 1;
        int index3 = this.msg.charAt(index);
        if (index3 == 112 || index3 == 80) {
            int index4 = index2 + 1;
            char c2 = this.msg.charAt(index2);
            if (c2 == 'l' || c2 == 'L') {
                int index5 = index4 + 1;
                char c3 = this.msg.charAt(index4);
                if (c3 == 'u' || c3 == 'U') {
                    index4 = index5 + 1;
                    char c4 = this.msg.charAt(index5);
                    if (c4 == 'r' || c4 == 'R') {
                        int index6 = index4 + 1;
                        char c5 = this.msg.charAt(index4);
                        if ((c5 == 'a' || c5 == 'A') && ((c = this.msg.charAt(index6)) == 'l' || c == 'L')) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isSelect(int index) {
        char c;
        int index2 = index + 1;
        int index3 = this.msg.charAt(index);
        if (index3 == 115 || index3 == 83) {
            int index4 = index2 + 1;
            char c2 = this.msg.charAt(index2);
            if (c2 == 'e' || c2 == 'E') {
                int index5 = index4 + 1;
                char c3 = this.msg.charAt(index4);
                if (c3 == 'l' || c3 == 'L') {
                    index4 = index5 + 1;
                    char c4 = this.msg.charAt(index5);
                    if (c4 == 'e' || c4 == 'E') {
                        int index6 = index4 + 1;
                        char c5 = this.msg.charAt(index4);
                        if ((c5 == 'c' || c5 == 'C') && ((c = this.msg.charAt(index6)) == 't' || c == 'T')) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isOrdinal(int index) {
        char c;
        int index2 = index + 1;
        int index3 = this.msg.charAt(index);
        if (index3 == 111 || index3 == 79) {
            int index4 = index2 + 1;
            char c2 = this.msg.charAt(index2);
            if (c2 == 'r' || c2 == 'R') {
                index2 = index4 + 1;
                char c3 = this.msg.charAt(index4);
                if (c3 == 'd' || c3 == 'D') {
                    int index5 = index2 + 1;
                    char c4 = this.msg.charAt(index2);
                    if (c4 == 'i' || c4 == 'I') {
                        index2 = index5 + 1;
                        char c5 = this.msg.charAt(index5);
                        if (c5 == 'n' || c5 == 'N') {
                            int index6 = index2 + 1;
                            char c6 = this.msg.charAt(index2);
                            if ((c6 == 'a' || c6 == 'A') && ((c = this.msg.charAt(index6)) == 'l' || c == 'L')) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    private boolean inMessageFormatPattern(int nestingLevel) {
        return nestingLevel > 0 || this.parts.get(0).type == Part.Type.MSG_START;
    }

    private boolean inTopLevelChoiceMessage(int nestingLevel, ArgType parentType) {
        return nestingLevel == 1 && parentType == ArgType.CHOICE && this.parts.get(0).type != Part.Type.MSG_START;
    }

    private void addPart(Part.Type type, int index, int length, int value) {
        this.parts.add(new Part(type, index, length, value));
    }

    private void addLimitPart(int start, Part.Type type, int index, int length, int value) {
        this.parts.get(start).limitPartIndex = this.parts.size();
        addPart(type, index, length, value);
    }

    private void addArgDoublePart(double numericValue, int start, int length) {
        int numericIndex;
        if (this.numericValues == null) {
            this.numericValues = new ArrayList<>();
            numericIndex = 0;
        } else {
            numericIndex = this.numericValues.size();
            if (numericIndex > 32767) {
                throw new IndexOutOfBoundsException("Too many numeric values");
            }
        }
        this.numericValues.add(Double.valueOf(numericValue));
        addPart(Part.Type.ARG_DOUBLE, start, length, numericIndex);
    }

    private static String prefix(String s, int start) {
        StringBuilder prefix = new StringBuilder(44);
        if (start == 0) {
            prefix.append("\"");
        } else {
            prefix.append("[at pattern index ");
            prefix.append(start);
            prefix.append("] \"");
        }
        int substringLength = s.length() - start;
        if (substringLength <= 24) {
            prefix.append(start == 0 ? s : s.substring(start));
        } else {
            int limit = (start + 24) - 4;
            if (Character.isHighSurrogate(s.charAt(limit - 1))) {
                limit--;
            }
            prefix.append((CharSequence) s, start, limit);
            prefix.append(" ...");
        }
        prefix.append("\"");
        return prefix.toString();
    }

    private static String prefix(String s) {
        return prefix(s, 0);
    }

    private String prefix(int start) {
        return prefix(this.msg, start);
    }

    private String prefix() {
        return prefix(this.msg, 0);
    }
}
