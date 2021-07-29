package com.ibm.icu.text;

import android.os.DropBoxManager;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.MessagePattern;
import com.ibm.icu.text.PluralFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.ChoiceFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MessageFormat extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char CURLY_BRACE_LEFT = '{';
    private static final char CURLY_BRACE_RIGHT = '}';
    private static final int DATE_MODIFIER_EMPTY = 0;
    private static final int DATE_MODIFIER_FULL = 4;
    private static final int DATE_MODIFIER_LONG = 3;
    private static final int DATE_MODIFIER_MEDIUM = 2;
    private static final int DATE_MODIFIER_SHORT = 1;
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_EMPTY = 0;
    private static final int MODIFIER_INTEGER = 3;
    private static final int MODIFIER_PERCENT = 2;
    private static final char SINGLE_QUOTE = '\'';
    private static final int STATE_INITIAL = 0;
    private static final int STATE_IN_QUOTE = 2;
    private static final int STATE_MSG_ELEMENT = 3;
    private static final int STATE_SINGLE_QUOTE = 1;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_DURATION = 5;
    private static final int TYPE_NUMBER = 0;
    private static final int TYPE_ORDINAL = 4;
    private static final int TYPE_SPELLOUT = 3;
    private static final int TYPE_TIME = 2;
    private static final String[] dateModifierList = {"", "short", "medium", "long", "full"};
    private static final String[] modifierList = {"", "currency", "percent", "integer"};
    private static final Locale rootLocale = new Locale("");
    static final long serialVersionUID = 7136212545847378652L;
    private static final String[] typeList = {"number", "date", DropBoxManager.EXTRA_TIME, "spellout", "ordinal", "duration"};
    /* access modifiers changed from: private */
    public transient Map<Integer, Format> cachedFormatters;
    private transient Set<Integer> customFormatArgStarts;
    private transient MessagePattern msgPattern;
    private transient PluralSelectorProvider ordinalProvider;
    private transient PluralSelectorProvider pluralProvider;
    private transient DateFormat stockDateFormatter;
    private transient NumberFormat stockNumberFormatter;
    /* access modifiers changed from: private */
    public transient ULocale ulocale;

    public MessageFormat(String pattern) {
        this.ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
        applyPattern(pattern);
    }

    public MessageFormat(String pattern, Locale locale) {
        this(pattern, ULocale.forLocale(locale));
    }

    public MessageFormat(String pattern, ULocale locale) {
        this.ulocale = locale;
        applyPattern(pattern);
    }

    public void setLocale(Locale locale) {
        setLocale(ULocale.forLocale(locale));
    }

    public void setLocale(ULocale locale) {
        String existingPattern = toPattern();
        this.ulocale = locale;
        this.stockDateFormatter = null;
        this.stockNumberFormatter = null;
        this.pluralProvider = null;
        this.ordinalProvider = null;
        applyPattern(existingPattern);
    }

    public Locale getLocale() {
        return this.ulocale.toLocale();
    }

    public ULocale getULocale() {
        return this.ulocale;
    }

    public void applyPattern(String pttrn) {
        try {
            if (this.msgPattern == null) {
                this.msgPattern = new MessagePattern(pttrn);
            } else {
                this.msgPattern.parse(pttrn);
            }
            cacheExplicitFormats();
        } catch (RuntimeException e) {
            resetPattern();
            throw e;
        }
    }

    public void applyPattern(String pattern, MessagePattern.ApostropheMode aposMode) {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern(aposMode);
        } else if (aposMode != this.msgPattern.getApostropheMode()) {
            this.msgPattern.clearPatternAndSetApostropheMode(aposMode);
        }
        applyPattern(pattern);
    }

    public MessagePattern.ApostropheMode getApostropheMode() {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        return this.msgPattern.getApostropheMode();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000b, code lost:
        r0 = r2.msgPattern.getPatternString();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toPattern() {
        /*
            r2 = this;
            java.util.Set<java.lang.Integer> r0 = r2.customFormatArgStarts
            if (r0 != 0) goto L_0x0018
            com.ibm.icu.text.MessagePattern r0 = r2.msgPattern
            if (r0 != 0) goto L_0x000b
            java.lang.String r0 = ""
            return r0
        L_0x000b:
            com.ibm.icu.text.MessagePattern r0 = r2.msgPattern
            java.lang.String r0 = r0.getPatternString()
            if (r0 != 0) goto L_0x0016
            java.lang.String r1 = ""
            goto L_0x0017
        L_0x0016:
            r1 = r0
        L_0x0017:
            return r1
        L_0x0018:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "toPattern() is not supported after custom Format objects have been set via setFormat() or similar APIs"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.MessageFormat.toPattern():java.lang.String");
    }

    private int nextTopLevelArgStart(int partIndex) {
        MessagePattern.Part.Type type;
        if (partIndex != 0) {
            partIndex = this.msgPattern.getLimitPartIndex(partIndex);
        }
        do {
            partIndex++;
            type = this.msgPattern.getPartType(partIndex);
            if (type == MessagePattern.Part.Type.ARG_START) {
                return partIndex;
            }
        } while (type != MessagePattern.Part.Type.MSG_LIMIT);
        return -1;
    }

    private boolean argNameMatches(int partIndex, String argName, int argNumber) {
        MessagePattern.Part part = this.msgPattern.getPart(partIndex);
        if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
            return this.msgPattern.partSubstringMatches(part, argName);
        }
        return part.getValue() == argNumber;
    }

    private String getArgName(int partIndex) {
        MessagePattern.Part part = this.msgPattern.getPart(partIndex);
        if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
            return this.msgPattern.getSubstring(part);
        }
        return Integer.toString(part.getValue());
    }

    public void setFormatsByArgumentIndex(Format[] newFormats) {
        if (!this.msgPattern.hasNamedArguments()) {
            int partIndex = 0;
            while (true) {
                int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
                partIndex = nextTopLevelArgStart;
                if (nextTopLevelArgStart >= 0) {
                    int argNumber = this.msgPattern.getPart(partIndex + 1).getValue();
                    if (argNumber < newFormats.length) {
                        setCustomArgStartFormat(partIndex, newFormats[argNumber]);
                    }
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
    }

    public void setFormatsByArgumentName(Map<String, Format> newFormats) {
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                String key = getArgName(partIndex + 1);
                if (newFormats.containsKey(key)) {
                    setCustomArgStartFormat(partIndex, newFormats.get(key));
                }
            } else {
                return;
            }
        }
    }

    public void setFormats(Format[] newFormats) {
        int formatNumber = 0;
        int partIndex = 0;
        while (formatNumber < newFormats.length) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                setCustomArgStartFormat(partIndex, newFormats[formatNumber]);
                formatNumber++;
            } else {
                return;
            }
        }
    }

    public void setFormatByArgumentIndex(int argumentIndex, Format newFormat) {
        if (!this.msgPattern.hasNamedArguments()) {
            int partIndex = 0;
            while (true) {
                int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
                partIndex = nextTopLevelArgStart;
                if (nextTopLevelArgStart < 0) {
                    return;
                }
                if (this.msgPattern.getPart(partIndex + 1).getValue() == argumentIndex) {
                    setCustomArgStartFormat(partIndex, newFormat);
                }
            }
        } else {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
    }

    public void setFormatByArgumentName(String argumentName, Format newFormat) {
        int argNumber = MessagePattern.validateArgumentName(argumentName);
        if (argNumber >= -1) {
            int partIndex = 0;
            while (true) {
                int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
                partIndex = nextTopLevelArgStart;
                if (nextTopLevelArgStart < 0) {
                    return;
                }
                if (argNameMatches(partIndex + 1, argumentName, argNumber)) {
                    setCustomArgStartFormat(partIndex, newFormat);
                }
            }
        }
    }

    public void setFormat(int formatElementIndex, Format newFormat) {
        int formatNumber = 0;
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart < 0) {
                throw new ArrayIndexOutOfBoundsException(formatElementIndex);
            } else if (formatNumber == formatElementIndex) {
                setCustomArgStartFormat(partIndex, newFormat);
                return;
            } else {
                formatNumber++;
            }
        }
    }

    public Format[] getFormatsByArgumentIndex() {
        Format format;
        if (!this.msgPattern.hasNamedArguments()) {
            ArrayList<Format> list = new ArrayList<>();
            int partIndex = 0;
            while (true) {
                int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
                partIndex = nextTopLevelArgStart;
                if (nextTopLevelArgStart < 0) {
                    return (Format[]) list.toArray(new Format[list.size()]);
                }
                int argNumber = this.msgPattern.getPart(partIndex + 1).getValue();
                while (true) {
                    format = null;
                    if (argNumber < list.size()) {
                        break;
                    }
                    list.add((Object) null);
                }
                if (this.cachedFormatters != null) {
                    format = this.cachedFormatters.get(Integer.valueOf(partIndex));
                }
                list.set(argNumber, format);
            }
        } else {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
    }

    public Format[] getFormats() {
        ArrayList<Format> list = new ArrayList<>();
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart < 0) {
                return (Format[]) list.toArray(new Format[list.size()]);
            }
            list.add(this.cachedFormatters == null ? null : this.cachedFormatters.get(Integer.valueOf(partIndex)));
        }
    }

    public Set<String> getArgumentNames() {
        Set<String> result = new HashSet<>();
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart < 0) {
                return result;
            }
            result.add(getArgName(partIndex + 1));
        }
    }

    public Format getFormatByArgumentName(String argumentName) {
        int argNumber;
        if (this.cachedFormatters == null || (argNumber = MessagePattern.validateArgumentName(argumentName)) < -1) {
            return null;
        }
        int partIndex = 0;
        do {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart < 0) {
                return null;
            }
        } while (!argNameMatches(partIndex + 1, argumentName, argNumber));
        return this.cachedFormatters.get(Integer.valueOf(partIndex));
    }

    public final StringBuffer format(Object[] arguments, StringBuffer result, FieldPosition pos) {
        format(arguments, (Map<String, Object>) null, new AppendableWrapper(result), pos);
        return result;
    }

    public final StringBuffer format(Map<String, Object> arguments, StringBuffer result, FieldPosition pos) {
        format((Object[]) null, arguments, new AppendableWrapper(result), pos);
        return result;
    }

    public static String format(String pattern, Object... arguments) {
        return new MessageFormat(pattern).format(arguments);
    }

    public static String format(String pattern, Map<String, Object> arguments) {
        return new MessageFormat(pattern).format(arguments);
    }

    public boolean usesNamedArguments() {
        return this.msgPattern.hasNamedArguments();
    }

    public final StringBuffer format(Object arguments, StringBuffer result, FieldPosition pos) {
        format(arguments, new AppendableWrapper(result), pos);
        return result;
    }

    public AttributedCharacterIterator formatToCharacterIterator(Object arguments) {
        if (arguments != null) {
            StringBuilder result = new StringBuilder();
            AppendableWrapper wrapper = new AppendableWrapper(result);
            wrapper.useAttributes();
            format(arguments, wrapper, (FieldPosition) null);
            AttributedString as = new AttributedString(result.toString());
            for (AttributeAndPosition a : wrapper.attributes) {
                as.addAttribute(a.key, a.value, a.start, a.limit);
            }
            return as.getIterator();
        }
        throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
    }

    public Object[] parse(String source, ParsePosition pos) {
        if (!this.msgPattern.hasNamedArguments()) {
            int maxArgId = -1;
            int partIndex = 0;
            while (true) {
                int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
                partIndex = nextTopLevelArgStart;
                if (nextTopLevelArgStart < 0) {
                    break;
                }
                int argNumber = this.msgPattern.getPart(partIndex + 1).getValue();
                if (argNumber > maxArgId) {
                    maxArgId = argNumber;
                }
            }
            Object[] resultArray = new Object[(maxArgId + 1)];
            int backupStartPos = pos.getIndex();
            parse(0, source, pos, resultArray, (Map<String, Object>) null);
            if (pos.getIndex() == backupStartPos) {
                return null;
            }
            return resultArray;
        }
        throw new IllegalArgumentException("This method is not available in MessageFormat objects that use named argument.");
    }

    public Map<String, Object> parseToMap(String source, ParsePosition pos) {
        HashMap hashMap = new HashMap();
        int backupStartPos = pos.getIndex();
        parse(0, source, pos, (Object[]) null, hashMap);
        if (pos.getIndex() == backupStartPos) {
            return null;
        }
        return hashMap;
    }

    public Object[] parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Object[] result = parse(source, pos);
        if (pos.getIndex() != 0) {
            return result;
        }
        throw new ParseException("MessageFormat parse error!", pos.getErrorIndex());
    }

    /* JADX WARNING: Removed duplicated region for block: B:77:0x01b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parse(int r31, java.lang.String r32, java.text.ParsePosition r33, java.lang.Object[] r34, java.util.Map<java.lang.String, java.lang.Object> r35) {
        /*
            r30 = this;
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r33
            r4 = r35
            if (r2 != 0) goto L_0x000d
            return
        L_0x000d:
            com.ibm.icu.text.MessagePattern r5 = r0.msgPattern
            java.lang.String r5 = r5.getPatternString()
            com.ibm.icu.text.MessagePattern r6 = r0.msgPattern
            com.ibm.icu.text.MessagePattern$Part r6 = r6.getPart(r1)
            int r6 = r6.getLimit()
            int r7 = r33.getIndex()
            java.text.ParsePosition r8 = new java.text.ParsePosition
            r9 = 0
            r8.<init>(r9)
            int r9 = r1 + 1
        L_0x0029:
            com.ibm.icu.text.MessagePattern r10 = r0.msgPattern
            com.ibm.icu.text.MessagePattern$Part r10 = r10.getPart(r9)
            com.ibm.icu.text.MessagePattern$Part$Type r11 = r10.getType()
            int r12 = r10.getIndex()
            int r13 = r12 - r6
            if (r13 == 0) goto L_0x0046
            boolean r14 = r5.regionMatches(r6, r2, r7, r13)
            if (r14 == 0) goto L_0x0042
            goto L_0x0046
        L_0x0042:
            r3.setErrorIndex(r7)
            return
        L_0x0046:
            int r7 = r7 + r13
            int r6 = r6 + r13
            com.ibm.icu.text.MessagePattern$Part$Type r14 = com.ibm.icu.text.MessagePattern.Part.Type.MSG_LIMIT
            if (r11 != r14) goto L_0x0050
            r3.setIndex(r7)
            return
        L_0x0050:
            com.ibm.icu.text.MessagePattern$Part$Type r14 = com.ibm.icu.text.MessagePattern.Part.Type.SKIP_SYNTAX
            if (r11 == r14) goto L_0x01cb
            com.ibm.icu.text.MessagePattern$Part$Type r14 = com.ibm.icu.text.MessagePattern.Part.Type.INSERT_CHAR
            if (r11 != r14) goto L_0x0061
            r15 = r5
            r18 = r6
            r28 = r8
            r23 = r11
            goto L_0x01d2
        L_0x0061:
            com.ibm.icu.text.MessagePattern r14 = r0.msgPattern
            int r14 = r14.getLimitPartIndex(r9)
            com.ibm.icu.text.MessagePattern$ArgType r1 = r10.getArgType()
            r15 = r5
            com.ibm.icu.text.MessagePattern r5 = r0.msgPattern
            int r9 = r9 + 1
            com.ibm.icu.text.MessagePattern$Part r5 = r5.getPart(r9)
            r10 = 0
            r16 = 0
            r17 = 0
            if (r34 == 0) goto L_0x0089
            int r16 = r5.getValue()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r16)
            r18 = r6
        L_0x0086:
            r6 = r17
            goto L_0x00aa
        L_0x0089:
            r18 = r6
            com.ibm.icu.text.MessagePattern$Part$Type r6 = r5.getType()
            r19 = r10
            com.ibm.icu.text.MessagePattern$Part$Type r10 = com.ibm.icu.text.MessagePattern.Part.Type.ARG_NAME
            if (r6 != r10) goto L_0x009e
            com.ibm.icu.text.MessagePattern r6 = r0.msgPattern
            java.lang.String r6 = r6.getSubstring(r5)
        L_0x009b:
            r17 = r6
            goto L_0x00a7
        L_0x009e:
            int r6 = r5.getValue()
            java.lang.String r6 = java.lang.Integer.toString(r6)
            goto L_0x009b
        L_0x00a7:
            r10 = r17
            goto L_0x0086
        L_0x00aa:
            int r9 = r9 + 1
            r17 = 0
            r19 = 0
            r20 = 0
            r21 = r5
            java.util.Map<java.lang.Integer, java.text.Format> r5 = r0.cachedFormatters
            if (r5 == 0) goto L_0x00ee
            java.util.Map<java.lang.Integer, java.text.Format> r5 = r0.cachedFormatters
            int r22 = r9 + -2
            r23 = r11
            java.lang.Integer r11 = java.lang.Integer.valueOf(r22)
            java.lang.Object r5 = r5.get(r11)
            java.text.Format r5 = (java.text.Format) r5
            r11 = r5
            if (r5 == 0) goto L_0x00f2
            r8.setIndex(r7)
            java.lang.Object r5 = r11.parseObject(r2, r8)
            r24 = r5
            int r5 = r8.getIndex()
            if (r5 != r7) goto L_0x00de
            r3.setErrorIndex(r7)
            return
        L_0x00de:
            r5 = 1
            int r7 = r8.getIndex()
            r29 = r1
            r28 = r8
            r25 = r11
            r11 = r5
            r5 = r24
            goto L_0x01b3
        L_0x00ee:
            r23 = r11
            r11 = r17
        L_0x00f2:
            com.ibm.icu.text.MessagePattern$ArgType r5 = com.ibm.icu.text.MessagePattern.ArgType.NONE
            if (r1 == r5) goto L_0x0166
            java.util.Map<java.lang.Integer, java.text.Format> r5 = r0.cachedFormatters
            if (r5 == 0) goto L_0x010d
            java.util.Map<java.lang.Integer, java.text.Format> r5 = r0.cachedFormatters
            int r17 = r9 + -2
            r25 = r11
            java.lang.Integer r11 = java.lang.Integer.valueOf(r17)
            boolean r5 = r5.containsKey(r11)
            if (r5 == 0) goto L_0x010f
            r28 = r8
            goto L_0x016a
        L_0x010d:
            r25 = r11
        L_0x010f:
            com.ibm.icu.text.MessagePattern$ArgType r5 = com.ibm.icu.text.MessagePattern.ArgType.CHOICE
            if (r1 != r5) goto L_0x0136
            r8.setIndex(r7)
            com.ibm.icu.text.MessagePattern r5 = r0.msgPattern
            double r26 = parseChoiceArgument(r5, r9, r2, r8)
            int r5 = r8.getIndex()
            if (r5 != r7) goto L_0x0126
            r3.setErrorIndex(r7)
            return
        L_0x0126:
            java.lang.Double r5 = java.lang.Double.valueOf(r26)
            r11 = 1
            int r7 = r8.getIndex()
            r29 = r1
            r28 = r8
            goto L_0x01b3
        L_0x0136:
            boolean r5 = r1.hasPluralStyle()
            if (r5 != 0) goto L_0x015c
            com.ibm.icu.text.MessagePattern$ArgType r5 = com.ibm.icu.text.MessagePattern.ArgType.SELECT
            if (r1 != r5) goto L_0x0143
            r28 = r8
            goto L_0x015e
        L_0x0143:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r28 = r8
            java.lang.String r8 = "unexpected argType "
            r11.append(r8)
            r11.append(r1)
            java.lang.String r8 = r11.toString()
            r5.<init>(r8)
            throw r5
        L_0x015c:
            r28 = r8
        L_0x015e:
            java.lang.UnsupportedOperationException r5 = new java.lang.UnsupportedOperationException
            java.lang.String r8 = "Parsing of plural/select/selectordinal argument is not supported."
            r5.<init>(r8)
            throw r5
        L_0x0166:
            r28 = r8
            r25 = r11
        L_0x016a:
            java.lang.String r5 = r0.getLiteralStringUntilNextArgument(r14)
            int r8 = r5.length()
            if (r8 == 0) goto L_0x0179
            int r8 = r2.indexOf(r5, r7)
            goto L_0x017d
        L_0x0179:
            int r8 = r32.length()
        L_0x017d:
            if (r8 >= 0) goto L_0x0183
            r3.setErrorIndex(r7)
            return
        L_0x0183:
            java.lang.String r11 = r2.substring(r7, r8)
            r29 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "{"
            r1.append(r2)
            java.lang.String r2 = r10.toString()
            r1.append(r2)
            java.lang.String r2 = "}"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            boolean r1 = r11.equals(r1)
            if (r1 != 0) goto L_0x01ad
            r19 = 1
            r20 = r11
        L_0x01ad:
            r7 = r8
            r11 = r19
            r5 = r20
        L_0x01b3:
            if (r11 == 0) goto L_0x01bf
            if (r34 == 0) goto L_0x01ba
            r34[r16] = r5
            goto L_0x01bf
        L_0x01ba:
            if (r4 == 0) goto L_0x01bf
            r4.put(r6, r5)
        L_0x01bf:
            com.ibm.icu.text.MessagePattern r1 = r0.msgPattern
            com.ibm.icu.text.MessagePattern$Part r1 = r1.getPart(r14)
            int r1 = r1.getLimit()
            r9 = r14
            goto L_0x01d7
        L_0x01cb:
            r15 = r5
            r18 = r6
            r28 = r8
            r23 = r11
        L_0x01d2:
            int r1 = r10.getLimit()
        L_0x01d7:
            r6 = r1
            int r9 = r9 + 1
            r5 = r15
            r8 = r28
            r1 = r31
            r2 = r32
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.MessageFormat.parse(int, java.lang.String, java.text.ParsePosition, java.lang.Object[], java.util.Map):void");
    }

    public Map<String, Object> parseToMap(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Map<String, Object> result = new HashMap<>();
        parse(0, source, pos, (Object[]) null, result);
        if (pos.getIndex() != 0) {
            return result;
        }
        throw new ParseException("MessageFormat parse error!", pos.getErrorIndex());
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (!this.msgPattern.hasNamedArguments()) {
            return parse(source, pos);
        }
        return parseToMap(source, pos);
    }

    public Object clone() {
        MessageFormat other = (MessageFormat) super.clone();
        if (this.customFormatArgStarts != null) {
            other.customFormatArgStarts = new HashSet();
            for (Integer key : this.customFormatArgStarts) {
                other.customFormatArgStarts.add(key);
            }
        } else {
            other.customFormatArgStarts = null;
        }
        if (this.cachedFormatters != null) {
            other.cachedFormatters = new HashMap();
            for (Map.Entry<Integer, Format> entry : this.cachedFormatters.entrySet()) {
                other.cachedFormatters.put(entry.getKey(), entry.getValue());
            }
        } else {
            other.cachedFormatters = null;
        }
        other.msgPattern = this.msgPattern == null ? null : (MessagePattern) this.msgPattern.clone();
        other.stockDateFormatter = this.stockDateFormatter == null ? null : (DateFormat) this.stockDateFormatter.clone();
        other.stockNumberFormatter = this.stockNumberFormatter == null ? null : (NumberFormat) this.stockNumberFormatter.clone();
        other.pluralProvider = null;
        other.ordinalProvider = null;
        return other;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessageFormat other = (MessageFormat) obj;
        if (!Objects.equals(this.ulocale, other.ulocale) || !Objects.equals(this.msgPattern, other.msgPattern) || !Objects.equals(this.cachedFormatters, other.cachedFormatters) || !Objects.equals(this.customFormatArgStarts, other.customFormatArgStarts)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.msgPattern.getPatternString().hashCode();
    }

    public static class Field extends Format.Field {
        public static final Field ARGUMENT = new Field("message argument field");
        private static final long serialVersionUID = 7510380454602616157L;

        protected Field(String name) {
            super(name);
        }

        /* access modifiers changed from: protected */
        public Object readResolve() throws InvalidObjectException {
            if (getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of MessageFormat.Field must implement readResolve.");
            } else if (getName().equals(ARGUMENT.getName())) {
                return ARGUMENT;
            } else {
                throw new InvalidObjectException("Unknown attribute name.");
            }
        }
    }

    private DateFormat getStockDateFormatter() {
        if (this.stockDateFormatter == null) {
            this.stockDateFormatter = DateFormat.getDateTimeInstance(3, 3, this.ulocale);
        }
        return this.stockDateFormatter;
    }

    /* access modifiers changed from: private */
    public NumberFormat getStockNumberFormatter() {
        if (this.stockNumberFormatter == null) {
            this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
        }
        return this.stockNumberFormatter;
    }

    private void format(int msgStart, PluralSelectorContext pluralNumber, Object[] args, Map<String, Object> argsMap, AppendableWrapper dest, FieldPosition fp) {
        String msgString;
        AppendableWrapper appendableWrapper;
        int prevDestLength;
        String msgString2;
        Object arg;
        int prevDestLength2;
        Object argId;
        int index;
        FieldPosition fp2;
        String argName;
        MessagePattern.ArgType argType;
        int prevDestLength3;
        Object arg2;
        int prevDestLength4;
        int index2;
        String msgString3;
        PluralSelectorProvider pluralSelectorProvider;
        Object argId2;
        FieldPosition fp3;
        MessagePattern.ArgType argType2;
        int prevDestLength5;
        int i = msgStart;
        PluralSelectorContext pluralSelectorContext = pluralNumber;
        Object[] objArr = args;
        Map<String, Object> map = argsMap;
        AppendableWrapper appendableWrapper2 = dest;
        String msgString4 = this.msgPattern.getPatternString();
        int i2 = this.msgPattern.getPart(i).getLimit();
        int i3 = i + 1;
        FieldPosition fp4 = fp;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i3);
            MessagePattern.Part.Type type = part.getType();
            int index3 = part.getIndex();
            appendableWrapper2.append(msgString4, i2, index3);
            if (type != MessagePattern.Part.Type.MSG_LIMIT) {
                int prevIndex = part.getLimit();
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    if (pluralSelectorContext.forReplaceNumber) {
                        appendableWrapper2.formatAndAppend(pluralSelectorContext.formatter, pluralSelectorContext.number, pluralSelectorContext.numberString);
                    } else {
                        appendableWrapper2.formatAndAppend(getStockNumberFormatter(), pluralSelectorContext.number);
                    }
                } else if (type == MessagePattern.Part.Type.ARG_START) {
                    int argLimit = this.msgPattern.getLimitPartIndex(i3);
                    MessagePattern.ArgType argType3 = part.getArgType();
                    int i4 = i3 + 1;
                    MessagePattern.Part part2 = this.msgPattern.getPart(i4);
                    boolean noArg = false;
                    Object argId3 = null;
                    int argLimit2 = argLimit;
                    String argName2 = this.msgPattern.getSubstring(part2);
                    if (objArr != null) {
                        msgString2 = msgString4;
                        int argNumber = part2.getValue();
                        if (dest.attributes != null) {
                            argId3 = Integer.valueOf(argNumber);
                        }
                        if (argNumber < 0 || argNumber >= objArr.length) {
                            arg = null;
                            noArg = true;
                        } else {
                            arg = objArr[argNumber];
                        }
                    } else {
                        msgString2 = msgString4;
                        argId3 = argName2;
                        if (map == null || !map.containsKey(argName2)) {
                            arg = null;
                            noArg = true;
                        } else {
                            arg = map.get(argName2);
                        }
                    }
                    Object arg3 = arg;
                    boolean noArg2 = noArg;
                    int i5 = i4 + 1;
                    int prevDestLength6 = dest.length;
                    if (noArg2) {
                        appendableWrapper2.append((CharSequence) "{" + argName2 + "}");
                    } else if (arg3 == null) {
                        appendableWrapper2.append((CharSequence) "null");
                    } else if (pluralSelectorContext == null || pluralSelectorContext.numberArgIndex != i5 - 2) {
                        String argName3 = argName2;
                        if (this.cachedFormatters != null) {
                            Format format = this.cachedFormatters.get(Integer.valueOf(i5 - 2));
                            Format formatter = format;
                            if (format != null) {
                                if ((formatter instanceof ChoiceFormat) || (formatter instanceof PluralFormat) || (formatter instanceof SelectFormat)) {
                                    String subMsgString = formatter.format(arg3);
                                    if (subMsgString.indexOf(123) >= 0 || (subMsgString.indexOf(39) >= 0 && !this.msgPattern.jdkAposMode())) {
                                        prevDestLength5 = prevDestLength6;
                                        argId = argId3;
                                        MessagePattern.Part part3 = part2;
                                        argType2 = argType3;
                                        int i6 = index3;
                                        MessagePattern.Part.Type type2 = type;
                                        boolean z = noArg2;
                                        fp2 = fp4;
                                        new MessageFormat(subMsgString, this.ulocale).format(0, (PluralSelectorContext) null, args, argsMap, dest, (FieldPosition) null);
                                    } else {
                                        if (dest.attributes == null) {
                                            appendableWrapper2.append((CharSequence) subMsgString);
                                        } else {
                                            appendableWrapper2.formatAndAppend(formatter, arg3);
                                        }
                                        prevDestLength5 = prevDestLength6;
                                        argId = argId3;
                                        argType2 = argType3;
                                        fp2 = fp4;
                                    }
                                } else {
                                    appendableWrapper2.formatAndAppend(formatter, arg3);
                                    boolean z2 = noArg2;
                                    prevDestLength5 = prevDestLength6;
                                    argId = argId3;
                                    MessagePattern.Part part4 = part2;
                                    argType2 = argType3;
                                    int i7 = index3;
                                    MessagePattern.Part.Type type3 = type;
                                    fp2 = fp4;
                                }
                                int i8 = i5;
                                Object obj = arg3;
                                prevDestLength2 = prevDestLength5;
                                Format format2 = formatter;
                                Object argId4 = argType2;
                                index = argLimit2;
                                msgString = msgString2;
                                String str = argName3;
                                appendableWrapper = dest;
                                prevDestLength = index;
                                fp4 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId);
                                prevIndex = this.msgPattern.getPart(index).getLimit();
                                objArr = args;
                                map = argsMap;
                                appendableWrapper2 = appendableWrapper;
                                msgString4 = msgString;
                                int i9 = msgStart;
                                pluralSelectorContext = pluralNumber;
                                i3 = prevDestLength + 1;
                                i2 = prevIndex;
                            } else {
                                prevDestLength3 = prevDestLength6;
                                argId2 = argId3;
                                MessagePattern.Part part5 = part2;
                                argType = argType3;
                                int i10 = index3;
                                MessagePattern.Part.Type type4 = type;
                                fp3 = fp4;
                                Format format3 = formatter;
                            }
                        } else {
                            boolean z3 = noArg2;
                            prevDestLength3 = prevDestLength6;
                            argId2 = argId3;
                            MessagePattern.Part part6 = part2;
                            argType = argType3;
                            int i11 = index3;
                            MessagePattern.Part.Type type5 = type;
                            fp3 = fp4;
                        }
                        if (argType == MessagePattern.ArgType.NONE) {
                            arg2 = arg3;
                            prevDestLength4 = prevDestLength3;
                            MessagePattern.ArgType argType4 = argType;
                            index2 = argLimit2;
                            msgString3 = msgString2;
                            String str2 = argName3;
                        } else if (this.cachedFormatters == null || !this.cachedFormatters.containsKey(Integer.valueOf(i5 - 2))) {
                            if (argType != MessagePattern.ArgType.CHOICE) {
                                int i12 = i5;
                                Object arg4 = arg3;
                                prevDestLength2 = prevDestLength3;
                                MessagePattern.ArgType argType5 = argType;
                                index = argLimit2;
                                msgString = msgString2;
                                String argName4 = argName3;
                                if (!argType5.hasPluralStyle()) {
                                    MessagePattern.ArgType argType6 = argType5;
                                    if (argType6 == MessagePattern.ArgType.SELECT) {
                                        formatComplexSubMessage(SelectFormat.findSubMessage(this.msgPattern, i12, arg4.toString()), (PluralSelectorContext) null, args, argsMap, dest);
                                    } else {
                                        throw new IllegalStateException("unexpected argType " + argType6);
                                    }
                                } else if (arg4 instanceof Number) {
                                    if (argType5 == MessagePattern.ArgType.PLURAL) {
                                        if (this.pluralProvider == null) {
                                            this.pluralProvider = new PluralSelectorProvider(this, PluralRules.PluralType.CARDINAL);
                                        }
                                        pluralSelectorProvider = this.pluralProvider;
                                    } else {
                                        if (this.ordinalProvider == null) {
                                            this.ordinalProvider = new PluralSelectorProvider(this, PluralRules.PluralType.ORDINAL);
                                        }
                                        pluralSelectorProvider = this.ordinalProvider;
                                    }
                                    PluralSelectorProvider selector = pluralSelectorProvider;
                                    Number number = (Number) arg4;
                                    PluralSelectorContext pluralSelectorContext2 = new PluralSelectorContext(i12, argName4, number, this.msgPattern.getPluralOffset(i12));
                                    formatComplexSubMessage(PluralFormat.findSubMessage(this.msgPattern, i12, selector, pluralSelectorContext2, number.doubleValue()), pluralSelectorContext2, args, argsMap, dest);
                                } else {
                                    throw new IllegalArgumentException("'" + arg4 + "' is not a Number");
                                }
                            } else if (arg3 instanceof Number) {
                                index = argLimit2;
                                String str3 = argName3;
                                int i13 = i5;
                                Object obj2 = arg3;
                                msgString = msgString2;
                                prevDestLength2 = prevDestLength3;
                                AppendableWrapper appendableWrapper3 = dest;
                                formatComplexSubMessage(findChoiceSubMessage(this.msgPattern, i5, ((Number) arg3).doubleValue()), (PluralSelectorContext) null, args, argsMap, dest);
                                MessagePattern.ArgType argType7 = argType;
                            } else {
                                int i14 = prevDestLength3;
                                MessagePattern.ArgType argType8 = argType;
                                int i15 = argLimit2;
                                String str4 = msgString2;
                                String str5 = argName3;
                                throw new IllegalArgumentException("'" + arg3 + "' is not a Number");
                            }
                            appendableWrapper = dest;
                            prevDestLength = index;
                            fp4 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId);
                            prevIndex = this.msgPattern.getPart(index).getLimit();
                            objArr = args;
                            map = argsMap;
                            appendableWrapper2 = appendableWrapper;
                            msgString4 = msgString;
                            int i92 = msgStart;
                            pluralSelectorContext = pluralNumber;
                            i3 = prevDestLength + 1;
                            i2 = prevIndex;
                        } else {
                            int i16 = i5;
                            arg2 = arg3;
                            prevDestLength4 = prevDestLength3;
                            MessagePattern.ArgType argType9 = argType;
                            index2 = argLimit2;
                            msgString3 = msgString2;
                            String str6 = argName3;
                        }
                        if (arg2 instanceof Number) {
                            appendableWrapper = dest;
                            appendableWrapper.formatAndAppend(getStockNumberFormatter(), arg2);
                        } else {
                            appendableWrapper = dest;
                            if (arg2 instanceof Date) {
                                appendableWrapper.formatAndAppend(getStockDateFormatter(), arg2);
                            } else {
                                appendableWrapper.append((CharSequence) arg2.toString());
                            }
                        }
                        prevDestLength = index;
                        fp4 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId);
                        prevIndex = this.msgPattern.getPart(index).getLimit();
                        objArr = args;
                        map = argsMap;
                        appendableWrapper2 = appendableWrapper;
                        msgString4 = msgString;
                        int i922 = msgStart;
                        pluralSelectorContext = pluralNumber;
                        i3 = prevDestLength + 1;
                        i2 = prevIndex;
                    } else {
                        if (pluralSelectorContext.offset == 0.0d) {
                            argName = argName2;
                            appendableWrapper2.formatAndAppend(pluralSelectorContext.formatter, pluralSelectorContext.number, pluralSelectorContext.numberString);
                        } else {
                            argName = argName2;
                            appendableWrapper2.formatAndAppend(pluralSelectorContext.formatter, arg3);
                        }
                        Object obj3 = arg3;
                        boolean z4 = noArg2;
                        prevDestLength2 = prevDestLength6;
                        argId = argId3;
                        MessagePattern.Part part7 = part2;
                        MessagePattern.ArgType argId5 = argType3;
                        int i17 = index3;
                        MessagePattern.Part.Type type6 = type;
                        fp2 = fp4;
                        index = argLimit2;
                        msgString = msgString2;
                        String str7 = argName;
                        int i18 = i5;
                        appendableWrapper = appendableWrapper2;
                        prevDestLength = index;
                        fp4 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId);
                        prevIndex = this.msgPattern.getPart(index).getLimit();
                        objArr = args;
                        map = argsMap;
                        appendableWrapper2 = appendableWrapper;
                        msgString4 = msgString;
                        int i9222 = msgStart;
                        pluralSelectorContext = pluralNumber;
                        i3 = prevDestLength + 1;
                        i2 = prevIndex;
                    }
                    argName = argName2;
                    Object obj32 = arg3;
                    boolean z42 = noArg2;
                    prevDestLength2 = prevDestLength6;
                    argId = argId3;
                    MessagePattern.Part part72 = part2;
                    MessagePattern.ArgType argId52 = argType3;
                    int i172 = index3;
                    MessagePattern.Part.Type type62 = type;
                    fp2 = fp4;
                    index = argLimit2;
                    msgString = msgString2;
                    String str72 = argName;
                    int i182 = i5;
                    appendableWrapper = appendableWrapper2;
                    prevDestLength = index;
                    fp4 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId);
                    prevIndex = this.msgPattern.getPart(index).getLimit();
                    objArr = args;
                    map = argsMap;
                    appendableWrapper2 = appendableWrapper;
                    msgString4 = msgString;
                    int i92222 = msgStart;
                    pluralSelectorContext = pluralNumber;
                    i3 = prevDestLength + 1;
                    i2 = prevIndex;
                }
                prevDestLength = i3;
                msgString = msgString4;
                appendableWrapper = appendableWrapper2;
                objArr = args;
                map = argsMap;
                appendableWrapper2 = appendableWrapper;
                msgString4 = msgString;
                int i922222 = msgStart;
                pluralSelectorContext = pluralNumber;
                i3 = prevDestLength + 1;
                i2 = prevIndex;
            } else {
                return;
            }
        }
    }

    private void formatComplexSubMessage(int msgStart, PluralSelectorContext pluralNumber, Object[] args, Map<String, Object> argsMap, AppendableWrapper dest) {
        int index;
        String subMsgString;
        PluralSelectorContext pluralSelectorContext = pluralNumber;
        if (!this.msgPattern.jdkAposMode()) {
            format(msgStart, pluralNumber, args, argsMap, dest, (FieldPosition) null);
            return;
        }
        String msgString = this.msgPattern.getPatternString();
        int i = msgStart;
        int prevIndex = this.msgPattern.getPart(i).getLimit();
        StringBuilder sb = null;
        int i2 = i;
        while (true) {
            i2++;
            MessagePattern.Part part = this.msgPattern.getPart(i2);
            MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            AppendableWrapper appendableWrapper = dest;
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(msgString, prevIndex, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    if (pluralSelectorContext.forReplaceNumber) {
                        sb.append(pluralSelectorContext.numberString);
                    } else {
                        sb.append(getStockNumberFormatter().format(pluralSelectorContext.number));
                    }
                }
                prevIndex = part.getLimit();
            } else if (type == MessagePattern.Part.Type.ARG_START) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(msgString, prevIndex, index);
                int prevIndex2 = index;
                i2 = this.msgPattern.getLimitPartIndex(i2);
                int index2 = this.msgPattern.getPart(i2).getLimit();
                MessagePattern.appendReducedApostrophes(msgString, prevIndex2, index2, sb);
                prevIndex = index2;
            }
        }
        if (sb == null) {
            subMsgString = msgString.substring(prevIndex, index);
        } else {
            sb.append(msgString, prevIndex, index);
            subMsgString = sb.toString();
        }
        String subMsgString2 = subMsgString;
        if (subMsgString2.indexOf(123) >= 0) {
            MessageFormat subMsgFormat = new MessageFormat("", this.ulocale);
            subMsgFormat.applyPattern(subMsgString2, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
            subMsgFormat.format(0, (PluralSelectorContext) null, args, argsMap, dest, (FieldPosition) null);
            AppendableWrapper appendableWrapper2 = dest;
            return;
        }
        dest.append((CharSequence) subMsgString2);
    }

    private String getLiteralStringUntilNextArgument(int from) {
        StringBuilder b = new StringBuilder();
        String msgString = this.msgPattern.getPatternString();
        int prevIndex = this.msgPattern.getPart(from).getLimit();
        int i = from + 1;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            b.append(msgString, prevIndex, part.getIndex());
            if (type != MessagePattern.Part.Type.ARG_START && type != MessagePattern.Part.Type.MSG_LIMIT) {
                prevIndex = part.getLimit();
                i++;
            }
        }
        return b.toString();
    }

    private FieldPosition updateMetaData(AppendableWrapper dest, int prevLength, FieldPosition fp, Object argId) {
        if (dest.attributes != null && prevLength < dest.length) {
            dest.attributes.add(new AttributeAndPosition(argId, prevLength, dest.length));
        }
        if (fp == null || !Field.ARGUMENT.equals(fp.getFieldAttribute())) {
            return fp;
        }
        fp.setBeginIndex(prevLength);
        fp.setEndIndex(dest.length);
        return null;
    }

    private static int findChoiceSubMessage(MessagePattern pattern, int partIndex, double number) {
        int msgStart;
        int partIndex2;
        int count = pattern.countParts();
        int partIndex3 = partIndex + 2;
        while (true) {
            msgStart = partIndex3;
            int partIndex4 = pattern.getLimitPartIndex(partIndex3) + 1;
            if (partIndex4 >= count) {
                break;
            }
            int partIndex5 = partIndex4 + 1;
            MessagePattern.Part part = pattern.getPart(partIndex4);
            if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) {
                int i = partIndex5;
                break;
            }
            double boundary = pattern.getNumericValue(part);
            partIndex2 = partIndex5 + 1;
            if (pattern.getPatternString().charAt(pattern.getPatternIndex(partIndex5)) == '<') {
                if (number <= boundary) {
                    break;
                }
                partIndex3 = partIndex2;
            } else if (number < boundary) {
                break;
            } else {
                partIndex3 = partIndex2;
            }
        }
        int i2 = partIndex2;
        return msgStart;
    }

    private static double parseChoiceArgument(MessagePattern pattern, int partIndex, String source, ParsePosition pos) {
        int newIndex;
        int start = pos.getIndex();
        int furthest = start;
        double bestNumber = Double.NaN;
        while (pattern.getPartType(partIndex) != MessagePattern.Part.Type.ARG_LIMIT) {
            double tempNumber = pattern.getNumericValue(pattern.getPart(partIndex));
            int partIndex2 = partIndex + 2;
            int msgLimit = pattern.getLimitPartIndex(partIndex2);
            int len = matchStringUntilLimitPart(pattern, partIndex2, msgLimit, source, start);
            if (len >= 0 && (newIndex = start + len) > furthest) {
                furthest = newIndex;
                bestNumber = tempNumber;
                if (furthest == source.length()) {
                    break;
                }
            }
            partIndex = msgLimit + 1;
        }
        if (furthest == start) {
            pos.setErrorIndex(start);
        } else {
            pos.setIndex(furthest);
        }
        return bestNumber;
    }

    private static int matchStringUntilLimitPart(MessagePattern pattern, int partIndex, int limitPartIndex, String source, int sourceOffset) {
        int matchingSourceLength = 0;
        String msgString = pattern.getPatternString();
        int prevIndex = pattern.getPart(partIndex).getLimit();
        while (true) {
            partIndex++;
            MessagePattern.Part part = pattern.getPart(partIndex);
            if (partIndex == limitPartIndex || part.getType() == MessagePattern.Part.Type.SKIP_SYNTAX) {
                int length = part.getIndex() - prevIndex;
                if (length != 0 && !source.regionMatches(sourceOffset, msgString, prevIndex, length)) {
                    return -1;
                }
                matchingSourceLength += length;
                if (partIndex == limitPartIndex) {
                    return matchingSourceLength;
                }
                prevIndex = part.getLimit();
            }
        }
    }

    /* access modifiers changed from: private */
    public int findOtherSubMessage(int partIndex) {
        int count = this.msgPattern.countParts();
        if (this.msgPattern.getPart(partIndex).getType().hasNumericValue()) {
            partIndex++;
        }
        do {
            int partIndex2 = partIndex + 1;
            MessagePattern.Part part = this.msgPattern.getPart(partIndex);
            if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) {
                int i = partIndex2;
                return 0;
            } else if (this.msgPattern.partSubstringMatches(part, "other")) {
                return partIndex2;
            } else {
                if (this.msgPattern.getPartType(partIndex2).hasNumericValue()) {
                    partIndex2++;
                }
                partIndex = this.msgPattern.getLimitPartIndex(partIndex2) + 1;
            }
        } while (partIndex < count);
        return 0;
    }

    /* access modifiers changed from: private */
    public int findFirstPluralNumberArg(int msgStart, String argName) {
        int i = msgStart + 1;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                return 0;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                return -1;
            }
            if (type == MessagePattern.Part.Type.ARG_START) {
                MessagePattern.ArgType argType = part.getArgType();
                if (argName.length() != 0 && (argType == MessagePattern.ArgType.NONE || argType == MessagePattern.ArgType.SIMPLE)) {
                    if (this.msgPattern.partSubstringMatches(this.msgPattern.getPart(i + 1), argName)) {
                        return i;
                    }
                }
                i = this.msgPattern.getLimitPartIndex(i);
            }
            i++;
        }
    }

    private static final class PluralSelectorContext {
        String argName;
        boolean forReplaceNumber;
        Format formatter;
        Number number;
        int numberArgIndex;
        String numberString;
        double offset;
        int startIndex;

        private PluralSelectorContext(int start, String name, Number num, double off) {
            this.startIndex = start;
            this.argName = name;
            if (off == 0.0d) {
                this.number = num;
            } else {
                this.number = Double.valueOf(num.doubleValue() - off);
            }
            this.offset = off;
        }

        public String toString() {
            throw new AssertionError("PluralSelectorContext being formatted, rather than its number");
        }
    }

    private static final class PluralSelectorProvider implements PluralFormat.PluralSelector {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private MessageFormat msgFormat;
        private PluralRules rules;
        private PluralRules.PluralType type;

        static {
            Class<MessageFormat> cls = MessageFormat.class;
        }

        public PluralSelectorProvider(MessageFormat mf, PluralRules.PluralType type2) {
            this.msgFormat = mf;
            this.type = type2;
        }

        public String select(Object ctx, double number) {
            if (this.rules == null) {
                this.rules = PluralRules.forLocale(this.msgFormat.ulocale, this.type);
            }
            PluralSelectorContext context = (PluralSelectorContext) ctx;
            context.numberArgIndex = this.msgFormat.findFirstPluralNumberArg(this.msgFormat.findOtherSubMessage(context.startIndex), context.argName);
            if (context.numberArgIndex > 0 && this.msgFormat.cachedFormatters != null) {
                context.formatter = (Format) this.msgFormat.cachedFormatters.get(Integer.valueOf(context.numberArgIndex));
            }
            if (context.formatter == null) {
                context.formatter = this.msgFormat.getStockNumberFormatter();
                context.forReplaceNumber = true;
            }
            context.numberString = context.formatter.format(context.number);
            if (!(context.formatter instanceof DecimalFormat)) {
                return this.rules.select(number);
            }
            return this.rules.select(((DecimalFormat) context.formatter).getFixedDecimal(number));
        }
    }

    private void format(Object arguments, AppendableWrapper result, FieldPosition fp) {
        if (arguments == null || (arguments instanceof Map)) {
            format((Object[]) null, (Map) arguments, result, fp);
        } else {
            format((Object[]) arguments, (Map<String, Object>) null, result, fp);
        }
    }

    private void format(Object[] arguments, Map<String, Object> argsMap, AppendableWrapper dest, FieldPosition fp) {
        if (arguments == null || !this.msgPattern.hasNamedArguments()) {
            format(0, (PluralSelectorContext) null, arguments, argsMap, dest, fp);
            return;
        }
        throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
    }

    private void resetPattern() {
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
        if (this.cachedFormatters != null) {
            this.cachedFormatters.clear();
        }
        this.customFormatArgStarts = null;
    }

    private Format createAppropriateFormat(String type, String style) {
        switch (findKeyword(type, typeList)) {
            case 0:
                switch (findKeyword(style, modifierList)) {
                    case 0:
                        return NumberFormat.getInstance(this.ulocale);
                    case 1:
                        return NumberFormat.getCurrencyInstance(this.ulocale);
                    case 2:
                        return NumberFormat.getPercentInstance(this.ulocale);
                    case 3:
                        return NumberFormat.getIntegerInstance(this.ulocale);
                    default:
                        int i = 0;
                        while (PatternProps.isWhiteSpace(style.charAt(i))) {
                            i++;
                        }
                        if (style.regionMatches(i, "::", 0, 2)) {
                            return NumberFormatter.forSkeleton(style.substring(i + 2)).locale(this.ulocale).toFormat();
                        }
                        return new DecimalFormat(style, new DecimalFormatSymbols(this.ulocale));
                }
            case 1:
                switch (findKeyword(style, dateModifierList)) {
                    case 0:
                        return DateFormat.getDateInstance(2, this.ulocale);
                    case 1:
                        return DateFormat.getDateInstance(3, this.ulocale);
                    case 2:
                        return DateFormat.getDateInstance(2, this.ulocale);
                    case 3:
                        return DateFormat.getDateInstance(1, this.ulocale);
                    case 4:
                        return DateFormat.getDateInstance(0, this.ulocale);
                    default:
                        return new SimpleDateFormat(style, this.ulocale);
                }
            case 2:
                switch (findKeyword(style, dateModifierList)) {
                    case 0:
                        return DateFormat.getTimeInstance(2, this.ulocale);
                    case 1:
                        return DateFormat.getTimeInstance(3, this.ulocale);
                    case 2:
                        return DateFormat.getTimeInstance(2, this.ulocale);
                    case 3:
                        return DateFormat.getTimeInstance(1, this.ulocale);
                    case 4:
                        return DateFormat.getTimeInstance(0, this.ulocale);
                    default:
                        return new SimpleDateFormat(style, this.ulocale);
                }
            case 3:
                RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(this.ulocale, 1);
                String ruleset = style.trim();
                if (ruleset.length() != 0) {
                    try {
                        rbnf.setDefaultRuleSet(ruleset);
                    } catch (Exception e) {
                    }
                }
                return rbnf;
            case 4:
                RuleBasedNumberFormat rbnf2 = new RuleBasedNumberFormat(this.ulocale, 2);
                String ruleset2 = style.trim();
                if (ruleset2.length() != 0) {
                    try {
                        rbnf2.setDefaultRuleSet(ruleset2);
                    } catch (Exception e2) {
                    }
                }
                return rbnf2;
            case 5:
                RuleBasedNumberFormat rbnf3 = new RuleBasedNumberFormat(this.ulocale, 3);
                String ruleset3 = style.trim();
                if (ruleset3.length() != 0) {
                    try {
                        rbnf3.setDefaultRuleSet(ruleset3);
                    } catch (Exception e3) {
                    }
                }
                return rbnf3;
            default:
                throw new IllegalArgumentException("Unknown format type \"" + type + "\"");
        }
    }

    private static final int findKeyword(String s, String[] list) {
        String s2 = PatternProps.trimWhiteSpace(s).toLowerCase(rootLocale);
        for (int i = 0; i < list.length; i++) {
            if (s2.equals(list[i])) {
                return i;
            }
        }
        return -1;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.ulocale.toLanguageTag());
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        out.writeObject(this.msgPattern.getApostropheMode());
        out.writeObject(this.msgPattern.getPatternString());
        if (this.customFormatArgStarts != null && !this.customFormatArgStarts.isEmpty()) {
            out.writeInt(this.customFormatArgStarts.size());
            int formatIndex = 0;
            int partIndex = 0;
            while (true) {
                int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
                partIndex = nextTopLevelArgStart;
                if (nextTopLevelArgStart < 0) {
                    break;
                }
                if (this.customFormatArgStarts.contains(Integer.valueOf(partIndex))) {
                    out.writeInt(formatIndex);
                    out.writeObject(this.cachedFormatters.get(Integer.valueOf(partIndex)));
                }
                formatIndex++;
            }
        } else {
            out.writeInt(0);
        }
        out.writeInt(0);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.ulocale = ULocale.forLanguageTag((String) in.readObject());
        MessagePattern.ApostropheMode aposMode = (MessagePattern.ApostropheMode) in.readObject();
        if (this.msgPattern == null || aposMode != this.msgPattern.getApostropheMode()) {
            this.msgPattern = new MessagePattern(aposMode);
        }
        String msg = (String) in.readObject();
        if (msg != null) {
            applyPattern(msg);
        }
        for (int numFormatters = in.readInt(); numFormatters > 0; numFormatters--) {
            setFormat(in.readInt(), (Format) in.readObject());
        }
        for (int numPairs = in.readInt(); numPairs > 0; numPairs--) {
            in.readInt();
            in.readObject();
        }
    }

    private void cacheExplicitFormats() {
        if (this.cachedFormatters != null) {
            this.cachedFormatters.clear();
        }
        this.customFormatArgStarts = null;
        int limit = this.msgPattern.countParts() - 2;
        int i = 1;
        while (i < limit) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            if (part.getType() == MessagePattern.Part.Type.ARG_START && part.getArgType() == MessagePattern.ArgType.SIMPLE) {
                int index = i;
                int i2 = i + 2;
                int i3 = i2 + 1;
                String explicitType = this.msgPattern.getSubstring(this.msgPattern.getPart(i2));
                String style = "";
                MessagePattern.Part part2 = this.msgPattern.getPart(i3);
                MessagePattern.Part part3 = part2;
                if (part2.getType() == MessagePattern.Part.Type.ARG_STYLE) {
                    style = this.msgPattern.getSubstring(part3);
                    i3++;
                }
                setArgStartFormat(index, createAppropriateFormat(explicitType, style));
                i = i3;
            }
            i++;
        }
    }

    private void setArgStartFormat(int argStart, Format formatter) {
        if (this.cachedFormatters == null) {
            this.cachedFormatters = new HashMap();
        }
        this.cachedFormatters.put(Integer.valueOf(argStart), formatter);
    }

    private void setCustomArgStartFormat(int argStart, Format formatter) {
        setArgStartFormat(argStart, formatter);
        if (this.customFormatArgStarts == null) {
            this.customFormatArgStarts = new HashSet();
        }
        this.customFormatArgStarts.add(Integer.valueOf(argStart));
    }

    public static String autoQuoteApostrophe(String pattern) {
        StringBuilder buf = new StringBuilder(pattern.length() * 2);
        int state = 0;
        int braceCount = 0;
        int j = pattern.length();
        for (int i = 0; i < j; i++) {
            char c = pattern.charAt(i);
            switch (state) {
                case 0:
                    if (c != '\'') {
                        if (c == '{') {
                            state = 3;
                            braceCount++;
                            break;
                        } else {
                            break;
                        }
                    } else {
                        state = 1;
                        break;
                    }
                case 1:
                    if (c != '\'') {
                        if (c != '{' && c != '}') {
                            buf.append('\'');
                            state = 0;
                            break;
                        } else {
                            state = 2;
                            break;
                        }
                    } else {
                        state = 0;
                        break;
                    }
                case 2:
                    if (c == '\'') {
                        state = 0;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (c != '{') {
                        if (c == '}' && braceCount - 1 == 0) {
                            state = 0;
                            break;
                        }
                    } else {
                        braceCount++;
                        break;
                    }
            }
            buf.append(c);
        }
        if (state == 1 || state == 2) {
            buf.append('\'');
        }
        return new String(buf);
    }

    private static final class AppendableWrapper {
        private Appendable app;
        /* access modifiers changed from: private */
        public List<AttributeAndPosition> attributes = null;
        /* access modifiers changed from: private */
        public int length;

        public AppendableWrapper(StringBuilder sb) {
            this.app = sb;
            this.length = sb.length();
        }

        public AppendableWrapper(StringBuffer sb) {
            this.app = sb;
            this.length = sb.length();
        }

        public void useAttributes() {
            this.attributes = new ArrayList();
        }

        public void append(CharSequence s) {
            try {
                this.app.append(s);
                this.length += s.length();
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void append(CharSequence s, int start, int limit) {
            try {
                this.app.append(s, start, limit);
                this.length += limit - start;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void append(CharacterIterator iterator) {
            this.length += append(this.app, iterator);
        }

        public static int append(Appendable result, CharacterIterator iterator) {
            try {
                int start = iterator.getBeginIndex();
                int limit = iterator.getEndIndex();
                int length2 = limit - start;
                if (start < limit) {
                    result.append(iterator.first());
                    while (true) {
                        start++;
                        if (start >= limit) {
                            break;
                        }
                        result.append(iterator.next());
                    }
                }
                return length2;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void formatAndAppend(Format formatter, Object arg) {
            if (this.attributes == null) {
                append((CharSequence) formatter.format(arg));
                return;
            }
            AttributedCharacterIterator formattedArg = formatter.formatToCharacterIterator(arg);
            int prevLength = this.length;
            append((CharacterIterator) formattedArg);
            formattedArg.first();
            int start = formattedArg.getIndex();
            int limit = formattedArg.getEndIndex();
            int offset = prevLength - start;
            while (start < limit) {
                Map<AttributedCharacterIterator.Attribute, Object> map = formattedArg.getAttributes();
                int runLimit = formattedArg.getRunLimit();
                if (map.size() != 0) {
                    for (Map.Entry<AttributedCharacterIterator.Attribute, Object> entry : map.entrySet()) {
                        this.attributes.add(new AttributeAndPosition(entry.getKey(), entry.getValue(), offset + start, offset + runLimit));
                    }
                }
                start = runLimit;
                formattedArg.setIndex(start);
            }
        }

        public void formatAndAppend(Format formatter, Object arg, String argString) {
            if (this.attributes != null || argString == null) {
                formatAndAppend(formatter, arg);
            } else {
                append((CharSequence) argString);
            }
        }
    }

    private static final class AttributeAndPosition {
        /* access modifiers changed from: private */
        public AttributedCharacterIterator.Attribute key;
        /* access modifiers changed from: private */
        public int limit;
        /* access modifiers changed from: private */
        public int start;
        /* access modifiers changed from: private */
        public Object value;

        public AttributeAndPosition(Object fieldValue, int startIndex, int limitIndex) {
            init(Field.ARGUMENT, fieldValue, startIndex, limitIndex);
        }

        public AttributeAndPosition(AttributedCharacterIterator.Attribute field, Object fieldValue, int startIndex, int limitIndex) {
            init(field, fieldValue, startIndex, limitIndex);
        }

        public void init(AttributedCharacterIterator.Attribute field, Object fieldValue, int startIndex, int limitIndex) {
            this.key = field;
            this.value = fieldValue;
            this.start = startIndex;
            this.limit = limitIndex;
        }
    }
}
