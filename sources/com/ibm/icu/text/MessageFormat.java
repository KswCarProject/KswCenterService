package com.ibm.icu.text;

import android.p007os.DropBoxManager;
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

/* loaded from: classes5.dex */
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
    static final long serialVersionUID = 7136212545847378652L;
    private transient Map<Integer, Format> cachedFormatters;
    private transient Set<Integer> customFormatArgStarts;
    private transient MessagePattern msgPattern;
    private transient PluralSelectorProvider ordinalProvider;
    private transient PluralSelectorProvider pluralProvider;
    private transient DateFormat stockDateFormatter;
    private transient NumberFormat stockNumberFormatter;
    private transient ULocale ulocale;
    private static final String[] typeList = {"number", "date", DropBoxManager.EXTRA_TIME, "spellout", "ordinal", "duration"};
    private static final String[] modifierList = {"", "currency", "percent", "integer"};
    private static final String[] dateModifierList = {"", "short", "medium", "long", "full"};
    private static final Locale rootLocale = new Locale("");

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

    public String toPattern() {
        String originalPattern;
        if (this.customFormatArgStarts == null) {
            return (this.msgPattern == null || (originalPattern = this.msgPattern.getPatternString()) == null) ? "" : originalPattern;
        }
        throw new IllegalStateException("toPattern() is not supported after custom Format objects have been set via setFormat() or similar APIs");
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
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
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
        int partIndex = 0;
        for (Format format : newFormats) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                setCustomArgStartFormat(partIndex, format);
            } else {
                return;
            }
        }
    }

    public void setFormatByArgumentIndex(int argumentIndex, Format newFormat) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                if (this.msgPattern.getPart(partIndex + 1).getValue() == argumentIndex) {
                    setCustomArgStartFormat(partIndex, newFormat);
                }
            } else {
                return;
            }
        }
    }

    public void setFormatByArgumentName(String argumentName, Format newFormat) {
        int argNumber = MessagePattern.validateArgumentName(argumentName);
        if (argNumber < -1) {
            return;
        }
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                if (argNameMatches(partIndex + 1, argumentName, argNumber)) {
                    setCustomArgStartFormat(partIndex, newFormat);
                }
            } else {
                return;
            }
        }
    }

    public void setFormat(int formatElementIndex, Format newFormat) {
        int formatNumber = 0;
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                if (formatNumber == formatElementIndex) {
                    setCustomArgStartFormat(partIndex, newFormat);
                    return;
                }
                formatNumber++;
            } else {
                throw new ArrayIndexOutOfBoundsException(formatElementIndex);
            }
        }
    }

    public Format[] getFormatsByArgumentIndex() {
        Format format;
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        ArrayList<Format> list = new ArrayList<>();
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                int argNumber = this.msgPattern.getPart(partIndex + 1).getValue();
                while (true) {
                    format = null;
                    if (argNumber < list.size()) {
                        break;
                    }
                    list.add(null);
                }
                if (this.cachedFormatters != null) {
                    format = this.cachedFormatters.get(Integer.valueOf(partIndex));
                }
                list.set(argNumber, format);
            } else {
                int partIndex2 = list.size();
                return (Format[]) list.toArray(new Format[partIndex2]);
            }
        }
    }

    public Format[] getFormats() {
        ArrayList<Format> list = new ArrayList<>();
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                list.add(this.cachedFormatters == null ? null : this.cachedFormatters.get(Integer.valueOf(partIndex)));
            } else {
                int partIndex2 = list.size();
                return (Format[]) list.toArray(new Format[partIndex2]);
            }
        }
    }

    public Set<String> getArgumentNames() {
        Set<String> result = new HashSet<>();
        int partIndex = 0;
        while (true) {
            int nextTopLevelArgStart = nextTopLevelArgStart(partIndex);
            partIndex = nextTopLevelArgStart;
            if (nextTopLevelArgStart >= 0) {
                result.add(getArgName(partIndex + 1));
            } else {
                return result;
            }
        }
    }

    public Format getFormatByArgumentName(String argumentName) {
        int argNumber;
        if (this.cachedFormatters != null && (argNumber = MessagePattern.validateArgumentName(argumentName)) >= -1) {
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
        return null;
    }

    public final StringBuffer format(Object[] arguments, StringBuffer result, FieldPosition pos) {
        format(arguments, null, new AppendableWrapper(result), pos);
        return result;
    }

    public final StringBuffer format(Map<String, Object> arguments, StringBuffer result, FieldPosition pos) {
        format(null, arguments, new AppendableWrapper(result), pos);
        return result;
    }

    public static String format(String pattern, Object... arguments) {
        MessageFormat temp = new MessageFormat(pattern);
        return temp.format(arguments);
    }

    public static String format(String pattern, Map<String, Object> arguments) {
        MessageFormat temp = new MessageFormat(pattern);
        return temp.format(arguments);
    }

    public boolean usesNamedArguments() {
        return this.msgPattern.hasNamedArguments();
    }

    @Override // java.text.Format
    public final StringBuffer format(Object arguments, StringBuffer result, FieldPosition pos) {
        format(arguments, new AppendableWrapper(result), pos);
        return result;
    }

    @Override // java.text.Format
    public AttributedCharacterIterator formatToCharacterIterator(Object arguments) {
        if (arguments == null) {
            throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
        }
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

    public Object[] parse(String source, ParsePosition pos) {
        if (this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use named argument.");
        }
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
        int partIndex2 = maxArgId + 1;
        Object[] resultArray = new Object[partIndex2];
        int backupStartPos = pos.getIndex();
        parse(0, source, pos, resultArray, null);
        if (pos.getIndex() == backupStartPos) {
            return null;
        }
        return resultArray;
    }

    public Map<String, Object> parseToMap(String source, ParsePosition pos) {
        Map<String, Object> result = new HashMap<>();
        int backupStartPos = pos.getIndex();
        parse(0, source, pos, null, result);
        if (pos.getIndex() == backupStartPos) {
            return null;
        }
        return result;
    }

    public Object[] parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Object[] result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw new ParseException("MessageFormat parse error!", pos.getErrorIndex());
        }
        return result;
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x01b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parse(int msgStart, String source, ParsePosition pos, Object[] args, Map<String, Object> argsMap) {
        String msgString;
        ParsePosition tempStatus;
        int prevIndex;
        String num;
        Object argId;
        Format formatter;
        int next;
        boolean haveArgResult;
        Object argResult;
        String str = source;
        if (str == null) {
            return;
        }
        String msgString2 = this.msgPattern.getPatternString();
        int prevIndex2 = this.msgPattern.getPart(msgStart).getLimit();
        int sourceOffset = pos.getIndex();
        ParsePosition tempStatus2 = new ParsePosition(0);
        int i = msgStart + 1;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            int index = part.getIndex();
            int len = index - prevIndex2;
            if (len == 0 || msgString2.regionMatches(prevIndex2, str, sourceOffset, len)) {
                sourceOffset += len;
                int i2 = prevIndex2 + len;
                if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                    pos.setIndex(sourceOffset);
                    return;
                }
                if (type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                    msgString = msgString2;
                    tempStatus = tempStatus2;
                } else if (type == MessagePattern.Part.Type.INSERT_CHAR) {
                    msgString = msgString2;
                    tempStatus = tempStatus2;
                } else {
                    int argLimit = this.msgPattern.getLimitPartIndex(i);
                    MessagePattern.ArgType argType = part.getArgType();
                    msgString = msgString2;
                    int i3 = i + 1;
                    MessagePattern.Part part2 = this.msgPattern.getPart(i3);
                    int argNumber = 0;
                    String key = null;
                    if (args != null) {
                        argNumber = part2.getValue();
                        argId = Integer.valueOf(argNumber);
                    } else {
                        Object type2 = part2.getType();
                        Object argId2 = MessagePattern.Part.Type.ARG_NAME;
                        if (type2 == argId2) {
                            num = this.msgPattern.getSubstring(part2);
                        } else {
                            num = Integer.toString(part2.getValue());
                        }
                        key = num;
                        argId = key;
                    }
                    String key2 = key;
                    int i4 = i3 + 1;
                    boolean haveArgResult2 = false;
                    Object argResult2 = null;
                    if (this.cachedFormatters != null) {
                        Format format = this.cachedFormatters.get(Integer.valueOf(i4 - 2));
                        formatter = format;
                        if (format != null) {
                            tempStatus2.setIndex(sourceOffset);
                            Object argResult3 = formatter.parseObject(str, tempStatus2);
                            if (tempStatus2.getIndex() == sourceOffset) {
                                pos.setErrorIndex(sourceOffset);
                                return;
                            }
                            sourceOffset = tempStatus2.getIndex();
                            tempStatus = tempStatus2;
                            haveArgResult = true;
                            argResult = argResult3;
                            if (haveArgResult) {
                                if (args != null) {
                                    args[argNumber] = argResult;
                                } else if (argsMap != null) {
                                    argsMap.put(key2, argResult);
                                }
                            }
                            prevIndex = this.msgPattern.getPart(argLimit).getLimit();
                            i = argLimit;
                            prevIndex2 = prevIndex;
                            i++;
                            msgString2 = msgString;
                            tempStatus2 = tempStatus;
                            str = source;
                        }
                    } else {
                        formatter = null;
                    }
                    if (argType != MessagePattern.ArgType.NONE) {
                        if (this.cachedFormatters != null && this.cachedFormatters.containsKey(Integer.valueOf(i4 - 2))) {
                            tempStatus = tempStatus2;
                        }
                        if (argType == MessagePattern.ArgType.CHOICE) {
                            tempStatus2.setIndex(sourceOffset);
                            double choiceResult = parseChoiceArgument(this.msgPattern, i4, str, tempStatus2);
                            if (tempStatus2.getIndex() == sourceOffset) {
                                pos.setErrorIndex(sourceOffset);
                                return;
                            }
                            argResult = Double.valueOf(choiceResult);
                            haveArgResult = true;
                            sourceOffset = tempStatus2.getIndex();
                            tempStatus = tempStatus2;
                            if (haveArgResult) {
                            }
                            prevIndex = this.msgPattern.getPart(argLimit).getLimit();
                            i = argLimit;
                            prevIndex2 = prevIndex;
                            i++;
                            msgString2 = msgString;
                            tempStatus2 = tempStatus;
                            str = source;
                        } else {
                            if (!argType.hasPluralStyle() && argType != MessagePattern.ArgType.SELECT) {
                                throw new IllegalStateException("unexpected argType " + argType);
                            }
                            throw new UnsupportedOperationException("Parsing of plural/select/selectordinal argument is not supported.");
                        }
                    } else {
                        tempStatus = tempStatus2;
                    }
                    String stringAfterArgument = getLiteralStringUntilNextArgument(argLimit);
                    if (stringAfterArgument.length() != 0) {
                        next = str.indexOf(stringAfterArgument, sourceOffset);
                    } else {
                        next = source.length();
                    }
                    if (next < 0) {
                        pos.setErrorIndex(sourceOffset);
                        return;
                    }
                    String strValue = str.substring(sourceOffset, next);
                    if (!strValue.equals("{" + argId.toString() + "}")) {
                        haveArgResult2 = true;
                        argResult2 = strValue;
                    }
                    sourceOffset = next;
                    haveArgResult = haveArgResult2;
                    argResult = argResult2;
                    if (haveArgResult) {
                    }
                    prevIndex = this.msgPattern.getPart(argLimit).getLimit();
                    i = argLimit;
                    prevIndex2 = prevIndex;
                    i++;
                    msgString2 = msgString;
                    tempStatus2 = tempStatus;
                    str = source;
                }
                prevIndex = part.getLimit();
                prevIndex2 = prevIndex;
                i++;
                msgString2 = msgString;
                tempStatus2 = tempStatus;
                str = source;
            } else {
                pos.setErrorIndex(sourceOffset);
                return;
            }
        }
    }

    public Map<String, Object> parseToMap(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Map<String, Object> result = new HashMap<>();
        parse(0, source, pos, null, result);
        if (pos.getIndex() != 0) {
            return result;
        }
        throw new ParseException("MessageFormat parse error!", pos.getErrorIndex());
    }

    @Override // java.text.Format
    public Object parseObject(String source, ParsePosition pos) {
        if (!this.msgPattern.hasNamedArguments()) {
            return parse(source, pos);
        }
        return parseToMap(source, pos);
    }

    @Override // java.text.Format
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
        if (Objects.equals(this.ulocale, other.ulocale) && Objects.equals(this.msgPattern, other.msgPattern) && Objects.equals(this.cachedFormatters, other.cachedFormatters) && Objects.equals(this.customFormatArgStarts, other.customFormatArgStarts)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.msgPattern.getPatternString().hashCode();
    }

    /* loaded from: classes5.dex */
    public static class Field extends Format.Field {
        public static final Field ARGUMENT = new Field("message argument field");
        private static final long serialVersionUID = 7510380454602616157L;

        protected Field(String name) {
            super(name);
        }

        @Override // java.text.AttributedCharacterIterator.Attribute
        protected Object readResolve() throws InvalidObjectException {
            if (getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of MessageFormat.Field must implement readResolve.");
            }
            if (getName().equals(ARGUMENT.getName())) {
                return ARGUMENT;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
    }

    private DateFormat getStockDateFormatter() {
        if (this.stockDateFormatter == null) {
            this.stockDateFormatter = DateFormat.getDateTimeInstance(3, 3, this.ulocale);
        }
        return this.stockDateFormatter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public NumberFormat getStockNumberFormatter() {
        if (this.stockNumberFormatter == null) {
            this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
        }
        return this.stockNumberFormatter;
    }

    private void format(int msgStart, PluralSelectorContext pluralNumber, Object[] args, Map<String, Object> argsMap, AppendableWrapper dest, FieldPosition fp) {
        String msgString;
        Object argId;
        Object arg;
        int prevDestLength;
        Object argId2;
        Object argType;
        FieldPosition fp2;
        Object arg2;
        int prevDestLength2;
        int index;
        String msgString2;
        AppendableWrapper appendableWrapper;
        PluralSelectorProvider pluralSelectorProvider;
        int prevDestLength3;
        Object argType2;
        String argName;
        int prevDestLength4;
        PluralSelectorContext pluralSelectorContext = pluralNumber;
        Object[] objArr = args;
        Map<String, Object> map = argsMap;
        AppendableWrapper appendableWrapper2 = dest;
        String msgString3 = this.msgPattern.getPatternString();
        int i = this.msgPattern.getPart(msgStart).getLimit();
        int i2 = msgStart + 1;
        FieldPosition fp3 = fp;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i2);
            MessagePattern.Part.Type type = part.getType();
            int index2 = part.getIndex();
            appendableWrapper2.append(msgString3, i, index2);
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                return;
            }
            int prevIndex = part.getLimit();
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                if (pluralSelectorContext.forReplaceNumber) {
                    appendableWrapper2.formatAndAppend(pluralSelectorContext.formatter, pluralSelectorContext.number, pluralSelectorContext.numberString);
                } else {
                    appendableWrapper2.formatAndAppend(getStockNumberFormatter(), pluralSelectorContext.number);
                }
            } else if (type == MessagePattern.Part.Type.ARG_START) {
                int argLimit = this.msgPattern.getLimitPartIndex(i2);
                Object argType3 = part.getArgType();
                int i3 = i2 + 1;
                MessagePattern.Part part2 = this.msgPattern.getPart(i3);
                boolean noArg = false;
                String argName2 = this.msgPattern.getSubstring(part2);
                if (objArr != null) {
                    msgString = msgString3;
                    int argNumber = part2.getValue();
                    argId = dest.attributes != null ? Integer.valueOf(argNumber) : null;
                    if (argNumber < 0 || argNumber >= objArr.length) {
                        arg = null;
                        noArg = true;
                    } else {
                        arg = objArr[argNumber];
                    }
                } else {
                    msgString = msgString3;
                    argId = argName2;
                    if (map == null || !map.containsKey(argName2)) {
                        arg = null;
                        noArg = true;
                    } else {
                        arg = map.get(argName2);
                    }
                }
                Object arg3 = arg;
                boolean noArg2 = noArg;
                int i4 = i3 + 1;
                int prevDestLength5 = dest.length;
                if (noArg2) {
                    appendableWrapper2.append("{" + argName2 + "}");
                } else if (arg3 == null) {
                    appendableWrapper2.append("null");
                } else if (pluralSelectorContext == null || pluralSelectorContext.numberArgIndex != i4 - 2) {
                    if (this.cachedFormatters != null) {
                        Format formatter = this.cachedFormatters.get(Integer.valueOf(i4 - 2));
                        if (formatter != null) {
                            if ((formatter instanceof ChoiceFormat) || (formatter instanceof PluralFormat) || (formatter instanceof SelectFormat)) {
                                String subMsgString = formatter.format(arg3);
                                if (subMsgString.indexOf(123) >= 0 || (subMsgString.indexOf(39) >= 0 && !this.msgPattern.jdkAposMode())) {
                                    MessageFormat subMsgFormat = new MessageFormat(subMsgString, this.ulocale);
                                    prevDestLength3 = prevDestLength5;
                                    argId2 = argId;
                                    argType2 = argType3;
                                    fp2 = fp3;
                                    subMsgFormat.format(0, null, args, argsMap, dest, null);
                                } else {
                                    if (dest.attributes == null) {
                                        appendableWrapper2.append(subMsgString);
                                    } else {
                                        appendableWrapper2.formatAndAppend(formatter, arg3);
                                    }
                                    prevDestLength3 = prevDestLength5;
                                    argId2 = argId;
                                    argType2 = argType3;
                                    fp2 = fp3;
                                }
                            } else {
                                appendableWrapper2.formatAndAppend(formatter, arg3);
                                prevDestLength3 = prevDestLength5;
                                argId2 = argId;
                                argType2 = argType3;
                                fp2 = fp3;
                            }
                            prevDestLength2 = prevDestLength3;
                            index = argLimit;
                            msgString2 = msgString;
                            appendableWrapper = dest;
                            FieldPosition fp4 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId2);
                            int prevIndex2 = this.msgPattern.getPart(index).getLimit();
                            prevDestLength4 = index;
                            fp3 = fp4;
                            prevIndex = prevIndex2;
                            int i5 = prevDestLength4 + 1;
                            objArr = args;
                            map = argsMap;
                            appendableWrapper2 = appendableWrapper;
                            msgString3 = msgString2;
                            pluralSelectorContext = pluralNumber;
                            i2 = i5;
                            i = prevIndex;
                        } else {
                            prevDestLength = prevDestLength5;
                            argId2 = argId;
                            argType = argType3;
                            fp2 = fp3;
                        }
                    } else {
                        prevDestLength = prevDestLength5;
                        argId2 = argId;
                        argType = argType3;
                        fp2 = fp3;
                    }
                    if (argType == MessagePattern.ArgType.NONE) {
                        arg2 = arg3;
                        prevDestLength2 = prevDestLength;
                        index = argLimit;
                        msgString2 = msgString;
                    } else if (this.cachedFormatters == null || !this.cachedFormatters.containsKey(Integer.valueOf(i4 - 2))) {
                        if (argType != MessagePattern.ArgType.CHOICE) {
                            prevDestLength2 = prevDestLength;
                            MessagePattern.ArgType argType4 = argType;
                            index = argLimit;
                            msgString2 = msgString;
                            if (argType4.hasPluralStyle()) {
                                if (!(arg3 instanceof Number)) {
                                    throw new IllegalArgumentException("'" + arg3 + "' is not a Number");
                                }
                                if (argType4 == MessagePattern.ArgType.PLURAL) {
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
                                Number number = (Number) arg3;
                                double offset = this.msgPattern.getPluralOffset(i4);
                                PluralSelectorContext context = new PluralSelectorContext(i4, argName2, number, offset);
                                int subMsgStart = PluralFormat.findSubMessage(this.msgPattern, i4, selector, context, number.doubleValue());
                                formatComplexSubMessage(subMsgStart, context, args, argsMap, dest);
                            } else if (argType4 != MessagePattern.ArgType.SELECT) {
                                throw new IllegalStateException("unexpected argType " + argType4);
                            } else {
                                int subMsgStart2 = SelectFormat.findSubMessage(this.msgPattern, i4, arg3.toString());
                                formatComplexSubMessage(subMsgStart2, null, args, argsMap, dest);
                            }
                        } else if (!(arg3 instanceof Number)) {
                            throw new IllegalArgumentException("'" + arg3 + "' is not a Number");
                        } else {
                            int subMsgStart3 = findChoiceSubMessage(this.msgPattern, i4, ((Number) arg3).doubleValue());
                            index = argLimit;
                            msgString2 = msgString;
                            prevDestLength2 = prevDestLength;
                            formatComplexSubMessage(subMsgStart3, null, args, argsMap, dest);
                        }
                        appendableWrapper = dest;
                        FieldPosition fp42 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId2);
                        int prevIndex22 = this.msgPattern.getPart(index).getLimit();
                        prevDestLength4 = index;
                        fp3 = fp42;
                        prevIndex = prevIndex22;
                        int i52 = prevDestLength4 + 1;
                        objArr = args;
                        map = argsMap;
                        appendableWrapper2 = appendableWrapper;
                        msgString3 = msgString2;
                        pluralSelectorContext = pluralNumber;
                        i2 = i52;
                        i = prevIndex;
                    } else {
                        arg2 = arg3;
                        prevDestLength2 = prevDestLength;
                        index = argLimit;
                        msgString2 = msgString;
                    }
                    if (arg2 instanceof Number) {
                        appendableWrapper = dest;
                        appendableWrapper.formatAndAppend(getStockNumberFormatter(), arg2);
                    } else {
                        appendableWrapper = dest;
                        if (arg2 instanceof Date) {
                            appendableWrapper.formatAndAppend(getStockDateFormatter(), arg2);
                        } else {
                            appendableWrapper.append(arg2.toString());
                        }
                    }
                    FieldPosition fp422 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId2);
                    int prevIndex222 = this.msgPattern.getPart(index).getLimit();
                    prevDestLength4 = index;
                    fp3 = fp422;
                    prevIndex = prevIndex222;
                    int i522 = prevDestLength4 + 1;
                    objArr = args;
                    map = argsMap;
                    appendableWrapper2 = appendableWrapper;
                    msgString3 = msgString2;
                    pluralSelectorContext = pluralNumber;
                    i2 = i522;
                    i = prevIndex;
                } else {
                    if (pluralSelectorContext.offset == 0.0d) {
                        argName = argName2;
                        appendableWrapper2.formatAndAppend(pluralSelectorContext.formatter, pluralSelectorContext.number, pluralSelectorContext.numberString);
                    } else {
                        argName = argName2;
                        appendableWrapper2.formatAndAppend(pluralSelectorContext.formatter, arg3);
                    }
                    prevDestLength2 = prevDestLength5;
                    argId2 = argId;
                    fp2 = fp3;
                    index = argLimit;
                    msgString2 = msgString;
                    appendableWrapper = appendableWrapper2;
                    FieldPosition fp4222 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId2);
                    int prevIndex2222 = this.msgPattern.getPart(index).getLimit();
                    prevDestLength4 = index;
                    fp3 = fp4222;
                    prevIndex = prevIndex2222;
                    int i5222 = prevDestLength4 + 1;
                    objArr = args;
                    map = argsMap;
                    appendableWrapper2 = appendableWrapper;
                    msgString3 = msgString2;
                    pluralSelectorContext = pluralNumber;
                    i2 = i5222;
                    i = prevIndex;
                }
                argName = argName2;
                prevDestLength2 = prevDestLength5;
                argId2 = argId;
                fp2 = fp3;
                index = argLimit;
                msgString2 = msgString;
                appendableWrapper = appendableWrapper2;
                FieldPosition fp42222 = updateMetaData(appendableWrapper, prevDestLength2, fp2, argId2);
                int prevIndex22222 = this.msgPattern.getPart(index).getLimit();
                prevDestLength4 = index;
                fp3 = fp42222;
                prevIndex = prevIndex22222;
                int i52222 = prevDestLength4 + 1;
                objArr = args;
                map = argsMap;
                appendableWrapper2 = appendableWrapper;
                msgString3 = msgString2;
                pluralSelectorContext = pluralNumber;
                i2 = i52222;
                i = prevIndex;
            }
            prevDestLength4 = i2;
            msgString2 = msgString3;
            appendableWrapper = appendableWrapper2;
            int i522222 = prevDestLength4 + 1;
            objArr = args;
            map = argsMap;
            appendableWrapper2 = appendableWrapper;
            msgString3 = msgString2;
            pluralSelectorContext = pluralNumber;
            i2 = i522222;
            i = prevIndex;
        }
    }

    private void formatComplexSubMessage(int msgStart, PluralSelectorContext pluralNumber, Object[] args, Map<String, Object> argsMap, AppendableWrapper dest) {
        int index;
        String subMsgString;
        if (!this.msgPattern.jdkAposMode()) {
            format(msgStart, pluralNumber, args, argsMap, dest, null);
            return;
        }
        String msgString = this.msgPattern.getPatternString();
        int prevIndex = this.msgPattern.getPart(msgStart).getLimit();
        int prevIndex2 = prevIndex;
        StringBuilder sb = null;
        int i = msgStart;
        while (true) {
            i++;
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            } else if (type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) msgString, prevIndex2, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    if (pluralNumber.forReplaceNumber) {
                        sb.append(pluralNumber.numberString);
                    } else {
                        sb.append(getStockNumberFormatter().format(pluralNumber.number));
                    }
                }
                prevIndex2 = part.getLimit();
            } else if (type == MessagePattern.Part.Type.ARG_START) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) msgString, prevIndex2, index);
                i = this.msgPattern.getLimitPartIndex(i);
                int index2 = this.msgPattern.getPart(i).getLimit();
                MessagePattern.appendReducedApostrophes(msgString, index, index2, sb);
                prevIndex2 = index2;
            }
        }
        if (sb == null) {
            subMsgString = msgString.substring(prevIndex2, index);
        } else {
            sb.append((CharSequence) msgString, prevIndex2, index);
            subMsgString = sb.toString();
        }
        String subMsgString2 = subMsgString;
        if (subMsgString2.indexOf(123) >= 0) {
            MessageFormat subMsgFormat = new MessageFormat("", this.ulocale);
            subMsgFormat.applyPattern(subMsgString2, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
            subMsgFormat.format(0, null, args, argsMap, dest, null);
            return;
        }
        dest.append(subMsgString2);
    }

    private String getLiteralStringUntilNextArgument(int from) {
        StringBuilder b = new StringBuilder();
        String msgString = this.msgPattern.getPatternString();
        int prevIndex = this.msgPattern.getPart(from).getLimit();
        int i = from + 1;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            int index = part.getIndex();
            b.append((CharSequence) msgString, prevIndex, index);
            if (type == MessagePattern.Part.Type.ARG_START || type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            prevIndex = part.getLimit();
            i++;
        }
        return b.toString();
    }

    private FieldPosition updateMetaData(AppendableWrapper dest, int prevLength, FieldPosition fp, Object argId) {
        if (dest.attributes != null && prevLength < dest.length) {
            dest.attributes.add(new AttributeAndPosition(argId, prevLength, dest.length));
        }
        if (fp != null && Field.ARGUMENT.equals(fp.getFieldAttribute())) {
            fp.setBeginIndex(prevLength);
            fp.setEndIndex(dest.length);
            return null;
        }
        return fp;
    }

    private static int findChoiceSubMessage(MessagePattern pattern, int partIndex, double number) {
        int msgStart;
        int count = pattern.countParts();
        int partIndex2 = partIndex + 2;
        while (true) {
            msgStart = partIndex2;
            int partIndex3 = pattern.getLimitPartIndex(partIndex2) + 1;
            if (partIndex3 >= count) {
                break;
            }
            int partIndex4 = partIndex3 + 1;
            MessagePattern.Part part = pattern.getPart(partIndex3);
            MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            double boundary = pattern.getNumericValue(part);
            int partIndex5 = partIndex4 + 1;
            char boundaryChar = pattern.getPatternString().charAt(pattern.getPatternIndex(partIndex4));
            if (boundaryChar == '<') {
                if (number <= boundary) {
                    break;
                }
                partIndex2 = partIndex5;
            } else if (number < boundary) {
                break;
            } else {
                partIndex2 = partIndex5;
            }
        }
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
                int index = part.getIndex();
                int length = index - prevIndex;
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

    /* JADX INFO: Access modifiers changed from: private */
    public int findOtherSubMessage(int partIndex) {
        int count = this.msgPattern.countParts();
        if (this.msgPattern.getPart(partIndex).getType().hasNumericValue()) {
            partIndex++;
        }
        do {
            int partIndex2 = partIndex + 1;
            MessagePattern.Part part = this.msgPattern.getPart(partIndex);
            MessagePattern.Part.Type type = part.getType();
            if (type != MessagePattern.Part.Type.ARG_LIMIT) {
                if (this.msgPattern.partSubstringMatches(part, "other")) {
                    return partIndex2;
                }
                if (this.msgPattern.getPartType(partIndex2).hasNumericValue()) {
                    partIndex2++;
                }
                partIndex = this.msgPattern.getLimitPartIndex(partIndex2) + 1;
            } else {
                return 0;
            }
        } while (partIndex < count);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    /* loaded from: classes5.dex */
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

    /* loaded from: classes5.dex */
    private static final class PluralSelectorProvider implements PluralFormat.PluralSelector {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private MessageFormat msgFormat;
        private PluralRules rules;
        private PluralRules.PluralType type;

        public PluralSelectorProvider(MessageFormat mf, PluralRules.PluralType type) {
            this.msgFormat = mf;
            this.type = type;
        }

        @Override // com.ibm.icu.text.PluralFormat.PluralSelector
        public String select(Object ctx, double number) {
            if (this.rules == null) {
                this.rules = PluralRules.forLocale(this.msgFormat.ulocale, this.type);
            }
            PluralSelectorContext context = (PluralSelectorContext) ctx;
            int otherIndex = this.msgFormat.findOtherSubMessage(context.startIndex);
            context.numberArgIndex = this.msgFormat.findFirstPluralNumberArg(otherIndex, context.argName);
            if (context.numberArgIndex > 0 && this.msgFormat.cachedFormatters != null) {
                context.formatter = (Format) this.msgFormat.cachedFormatters.get(Integer.valueOf(context.numberArgIndex));
            }
            if (context.formatter == null) {
                context.formatter = this.msgFormat.getStockNumberFormatter();
                context.forReplaceNumber = true;
            }
            context.numberString = context.formatter.format(context.number);
            if (context.formatter instanceof DecimalFormat) {
                PluralRules.IFixedDecimal dec = ((DecimalFormat) context.formatter).getFixedDecimal(number);
                return this.rules.select(dec);
            }
            return this.rules.select(number);
        }
    }

    private void format(Object arguments, AppendableWrapper result, FieldPosition fp) {
        if (arguments == null || (arguments instanceof Map)) {
            format(null, (Map) arguments, result, fp);
        } else {
            format((Object[]) arguments, null, result, fp);
        }
    }

    private void format(Object[] arguments, Map<String, Object> argsMap, AppendableWrapper dest, FieldPosition fp) {
        if (arguments != null && this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        format(0, null, arguments, argsMap, dest, fp);
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
        int subformatType = findKeyword(type, typeList);
        switch (subformatType) {
            case 0:
                switch (findKeyword(style, modifierList)) {
                    case 0:
                        Format newFormat = NumberFormat.getInstance(this.ulocale);
                        return newFormat;
                    case 1:
                        Format newFormat2 = NumberFormat.getCurrencyInstance(this.ulocale);
                        return newFormat2;
                    case 2:
                        Format newFormat3 = NumberFormat.getPercentInstance(this.ulocale);
                        return newFormat3;
                    case 3:
                        Format newFormat4 = NumberFormat.getIntegerInstance(this.ulocale);
                        return newFormat4;
                    default:
                        int i = 0;
                        while (PatternProps.isWhiteSpace(style.charAt(i))) {
                            i++;
                        }
                        if (style.regionMatches(i, "::", 0, 2)) {
                            Format newFormat5 = NumberFormatter.forSkeleton(style.substring(i + 2)).locale(this.ulocale).toFormat();
                            return newFormat5;
                        }
                        Format newFormat6 = new DecimalFormat(style, new DecimalFormatSymbols(this.ulocale));
                        return newFormat6;
                }
            case 1:
                switch (findKeyword(style, dateModifierList)) {
                    case 0:
                        Format newFormat7 = DateFormat.getDateInstance(2, this.ulocale);
                        return newFormat7;
                    case 1:
                        Format newFormat8 = DateFormat.getDateInstance(3, this.ulocale);
                        return newFormat8;
                    case 2:
                        Format newFormat9 = DateFormat.getDateInstance(2, this.ulocale);
                        return newFormat9;
                    case 3:
                        Format newFormat10 = DateFormat.getDateInstance(1, this.ulocale);
                        return newFormat10;
                    case 4:
                        Format newFormat11 = DateFormat.getDateInstance(0, this.ulocale);
                        return newFormat11;
                    default:
                        Format newFormat12 = new SimpleDateFormat(style, this.ulocale);
                        return newFormat12;
                }
            case 2:
                switch (findKeyword(style, dateModifierList)) {
                    case 0:
                        Format newFormat13 = DateFormat.getTimeInstance(2, this.ulocale);
                        return newFormat13;
                    case 1:
                        Format newFormat14 = DateFormat.getTimeInstance(3, this.ulocale);
                        return newFormat14;
                    case 2:
                        Format newFormat15 = DateFormat.getTimeInstance(2, this.ulocale);
                        return newFormat15;
                    case 3:
                        Format newFormat16 = DateFormat.getTimeInstance(1, this.ulocale);
                        return newFormat16;
                    case 4:
                        Format newFormat17 = DateFormat.getTimeInstance(0, this.ulocale);
                        return newFormat17;
                    default:
                        Format newFormat18 = new SimpleDateFormat(style, this.ulocale);
                        return newFormat18;
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
        if (this.customFormatArgStarts == null || this.customFormatArgStarts.isEmpty()) {
            out.writeInt(0);
        } else {
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
        }
        out.writeInt(0);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String languageTag = (String) in.readObject();
        this.ulocale = ULocale.forLanguageTag(languageTag);
        MessagePattern.ApostropheMode aposMode = (MessagePattern.ApostropheMode) in.readObject();
        if (this.msgPattern == null || aposMode != this.msgPattern.getApostropheMode()) {
            this.msgPattern = new MessagePattern(aposMode);
        }
        String msg = (String) in.readObject();
        if (msg != null) {
            applyPattern(msg);
        }
        for (int numFormatters = in.readInt(); numFormatters > 0; numFormatters--) {
            int formatIndex = in.readInt();
            Format formatter = (Format) in.readObject();
            setFormat(formatIndex, formatter);
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
            if (part.getType() == MessagePattern.Part.Type.ARG_START) {
                MessagePattern.ArgType argType = part.getArgType();
                if (argType == MessagePattern.ArgType.SIMPLE) {
                    int index = i;
                    int i2 = i + 2;
                    int i3 = i2 + 1;
                    String explicitType = this.msgPattern.getSubstring(this.msgPattern.getPart(i2));
                    String style = "";
                    MessagePattern.Part part2 = this.msgPattern.getPart(i3);
                    if (part2.getType() == MessagePattern.Part.Type.ARG_STYLE) {
                        style = this.msgPattern.getSubstring(part2);
                        i3++;
                    }
                    Format formatter = createAppropriateFormat(explicitType, style);
                    setArgStartFormat(index, formatter);
                    i = i3;
                }
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
                        if (c != '{') {
                            break;
                        } else {
                            state = 3;
                            braceCount++;
                            break;
                        }
                    } else {
                        state = 1;
                        break;
                    }
                case 1:
                    if (c != '\'') {
                        if (c == '{' || c == '}') {
                            state = 2;
                            break;
                        } else {
                            buf.append('\'');
                            state = 0;
                            break;
                        }
                    } else {
                        state = 0;
                        break;
                    }
                case 2:
                    if (c != '\'') {
                        break;
                    } else {
                        state = 0;
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
                    break;
            }
            buf.append(c);
        }
        if (state == 1 || state == 2) {
            buf.append('\'');
        }
        return new String(buf);
    }

    /* loaded from: classes5.dex */
    private static final class AppendableWrapper {
        private Appendable app;
        private List<AttributeAndPosition> attributes = null;
        private int length;

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
                int length = limit - start;
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
                return length;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public void formatAndAppend(Format formatter, Object arg) {
            if (this.attributes == null) {
                append(formatter.format(arg));
                return;
            }
            AttributedCharacterIterator formattedArg = formatter.formatToCharacterIterator(arg);
            int prevLength = this.length;
            append(formattedArg);
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
            if (this.attributes == null && argString != null) {
                append(argString);
            } else {
                formatAndAppend(formatter, arg);
            }
        }
    }

    /* loaded from: classes5.dex */
    private static final class AttributeAndPosition {
        private AttributedCharacterIterator.Attribute key;
        private int limit;
        private int start;
        private Object value;

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
