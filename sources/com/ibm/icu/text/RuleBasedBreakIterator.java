package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.RBBIDataWrapper;
import com.ibm.icu.text.DictionaryBreakEngine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class RuleBasedBreakIterator extends BreakIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String RBBI_DEBUG_ARG = "rbbi";
    private static final int RBBI_END = 2;
    private static final int RBBI_RUN = 1;
    private static final int RBBI_START = 0;
    private static final int START_STATE = 1;
    private static final int STOP_STATE = 0;
    private static final boolean TRACE = (ICUDebug.enabled(RBBI_DEBUG_ARG) && ICUDebug.value(RBBI_DEBUG_ARG).indexOf("trace") >= 0);
    @Deprecated
    public static final String fDebugEnv = (ICUDebug.enabled(RBBI_DEBUG_ARG) ? ICUDebug.value(RBBI_DEBUG_ARG) : null);
    private static final List<LanguageBreakEngine> gAllBreakEngines = new ArrayList();
    private static final UnhandledBreakEngine gUnhandledBreakEngine = new UnhandledBreakEngine();
    private static final int kMaxLookaheads = 8;
    private BreakCache fBreakCache;
    private List<LanguageBreakEngine> fBreakEngines;
    /* access modifiers changed from: private */
    public DictionaryCache fDictionaryCache;
    /* access modifiers changed from: private */
    public int fDictionaryCharCount;
    /* access modifiers changed from: private */
    public boolean fDone;
    private LookAheadResults fLookAheadMatches;
    /* access modifiers changed from: private */
    public int fPosition;
    @Deprecated
    public RBBIDataWrapper fRData;
    /* access modifiers changed from: private */
    public int fRuleStatusIndex;
    /* access modifiers changed from: private */
    public CharacterIterator fText;

    static {
        gAllBreakEngines.add(gUnhandledBreakEngine);
    }

    private RuleBasedBreakIterator() {
        this.fText = new StringCharacterIterator("");
        this.fBreakCache = new BreakCache();
        this.fDictionaryCache = new DictionaryCache();
        this.fLookAheadMatches = new LookAheadResults();
        this.fDictionaryCharCount = 0;
        synchronized (gAllBreakEngines) {
            this.fBreakEngines = new ArrayList(gAllBreakEngines);
        }
    }

    public static RuleBasedBreakIterator getInstanceFromCompiledRules(InputStream is) throws IOException {
        RuleBasedBreakIterator This = new RuleBasedBreakIterator();
        This.fRData = RBBIDataWrapper.get(ICUBinary.getByteBufferFromInputStreamAndCloseStream(is));
        return This;
    }

    @Deprecated
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(ByteBuffer bytes) throws IOException {
        RuleBasedBreakIterator This = new RuleBasedBreakIterator();
        This.fRData = RBBIDataWrapper.get(bytes);
        return This;
    }

    public RuleBasedBreakIterator(String rules) {
        this();
        try {
            ByteArrayOutputStream ruleOS = new ByteArrayOutputStream();
            compileRules(rules, ruleOS);
            this.fRData = RBBIDataWrapper.get(ByteBuffer.wrap(ruleOS.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException("RuleBasedBreakIterator rule compilation internal error: " + e.getMessage());
        }
    }

    public Object clone() {
        RuleBasedBreakIterator result = (RuleBasedBreakIterator) super.clone();
        if (this.fText != null) {
            result.fText = (CharacterIterator) this.fText.clone();
        }
        synchronized (gAllBreakEngines) {
            result.fBreakEngines = new ArrayList(gAllBreakEngines);
        }
        result.fLookAheadMatches = new LookAheadResults();
        result.getClass();
        result.fBreakCache = new BreakCache(this.fBreakCache);
        result.getClass();
        result.fDictionaryCache = new DictionaryCache(this.fDictionaryCache);
        return result;
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        try {
            RuleBasedBreakIterator other = (RuleBasedBreakIterator) that;
            if (this.fRData != other.fRData && (this.fRData == null || other.fRData == null)) {
                return false;
            }
            if (this.fRData != null && other.fRData != null && !this.fRData.fRuleSource.equals(other.fRData.fRuleSource)) {
                return false;
            }
            if (this.fText == null && other.fText == null) {
                return true;
            }
            if (!(this.fText == null || other.fText == null)) {
                if (this.fText.equals(other.fText)) {
                    if (this.fPosition == other.fPosition) {
                        return true;
                    }
                    return false;
                }
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public String toString() {
        if (this.fRData != null) {
            return this.fRData.fRuleSource;
        }
        return "";
    }

    public int hashCode() {
        return this.fRData.fRuleSource.hashCode();
    }

    @Deprecated
    public void dump(PrintStream out) {
        if (out == null) {
            out = System.out;
        }
        this.fRData.dump(out);
    }

    public static void compileRules(String rules, OutputStream ruleBinary) throws IOException {
        RBBIRuleBuilder.compileRules(rules, ruleBinary);
    }

    public int first() {
        if (this.fText == null) {
            return -1;
        }
        this.fText.first();
        int start = this.fText.getIndex();
        if (!this.fBreakCache.seek(start)) {
            this.fBreakCache.populateNear(start);
        }
        this.fBreakCache.current();
        return this.fPosition;
    }

    public int last() {
        if (this.fText == null) {
            return -1;
        }
        int endPos = this.fText.getEndIndex();
        boolean isBoundary = isBoundary(endPos);
        if (this.fPosition != endPos) {
        }
        return endPos;
    }

    public int next(int n) {
        int result = 0;
        if (n > 0) {
            while (n > 0 && result != -1) {
                result = next();
                n--;
            }
            return result;
        } else if (n >= 0) {
            return current();
        } else {
            while (n < 0 && result != -1) {
                result = previous();
                n++;
            }
            return result;
        }
    }

    public int next() {
        this.fBreakCache.next();
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    public int previous() {
        this.fBreakCache.previous();
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    public int following(int startPos) {
        if (startPos < this.fText.getBeginIndex()) {
            return first();
        }
        this.fBreakCache.following(CISetIndex32(this.fText, startPos));
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    public int preceding(int offset) {
        if (this.fText == null || offset > this.fText.getEndIndex()) {
            return last();
        }
        if (offset < this.fText.getBeginIndex()) {
            return first();
        }
        this.fBreakCache.preceding(offset);
        if (this.fDone) {
            return -1;
        }
        return this.fPosition;
    }

    protected static final void checkOffset(int offset, CharacterIterator text) {
        if (offset < text.getBeginIndex() || offset > text.getEndIndex()) {
            throw new IllegalArgumentException("offset out of bounds");
        }
    }

    public boolean isBoundary(int offset) {
        checkOffset(offset, this.fText);
        int adjustedOffset = CISetIndex32(this.fText, offset);
        boolean result = false;
        if (this.fBreakCache.seek(adjustedOffset) || this.fBreakCache.populateNear(adjustedOffset)) {
            result = this.fBreakCache.current() == offset;
        }
        if (!result) {
            next();
        }
        return result;
    }

    public int current() {
        if (this.fText != null) {
            return this.fPosition;
        }
        return -1;
    }

    public int getRuleStatus() {
        return this.fRData.fStatusTable[this.fRuleStatusIndex + this.fRData.fStatusTable[this.fRuleStatusIndex]];
    }

    public int getRuleStatusVec(int[] fillInArray) {
        int numStatusVals = this.fRData.fStatusTable[this.fRuleStatusIndex];
        if (fillInArray != null) {
            int numToCopy = Math.min(numStatusVals, fillInArray.length);
            for (int i = 0; i < numToCopy; i++) {
                fillInArray[i] = this.fRData.fStatusTable[this.fRuleStatusIndex + i + 1];
            }
        }
        return numStatusVals;
    }

    public CharacterIterator getText() {
        return this.fText;
    }

    public void setText(CharacterIterator newText) {
        if (newText != null) {
            this.fBreakCache.reset(newText.getBeginIndex(), 0);
        } else {
            this.fBreakCache.reset();
        }
        this.fDictionaryCache.reset();
        this.fText = newText;
        first();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0092, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ibm.icu.text.LanguageBreakEngine getLanguageBreakEngine(int r5) {
        /*
            r4 = this;
            java.util.List<com.ibm.icu.text.LanguageBreakEngine> r0 = r4.fBreakEngines
            java.util.Iterator r0 = r0.iterator()
        L_0x0006:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x001a
            java.lang.Object r1 = r0.next()
            com.ibm.icu.text.LanguageBreakEngine r1 = (com.ibm.icu.text.LanguageBreakEngine) r1
            boolean r2 = r1.handles(r5)
            if (r2 == 0) goto L_0x0019
            return r1
        L_0x0019:
            goto L_0x0006
        L_0x001a:
            java.util.List<com.ibm.icu.text.LanguageBreakEngine> r0 = gAllBreakEngines
            monitor-enter(r0)
            java.util.List<com.ibm.icu.text.LanguageBreakEngine> r1 = gAllBreakEngines     // Catch:{ all -> 0x0093 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0093 }
        L_0x0023:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0093 }
            if (r2 == 0) goto L_0x003d
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0093 }
            com.ibm.icu.text.LanguageBreakEngine r2 = (com.ibm.icu.text.LanguageBreakEngine) r2     // Catch:{ all -> 0x0093 }
            boolean r3 = r2.handles(r5)     // Catch:{ all -> 0x0093 }
            if (r3 == 0) goto L_0x003c
            java.util.List<com.ibm.icu.text.LanguageBreakEngine> r1 = r4.fBreakEngines     // Catch:{ all -> 0x0093 }
            r1.add(r2)     // Catch:{ all -> 0x0093 }
            monitor-exit(r0)     // Catch:{ all -> 0x0093 }
            return r2
        L_0x003c:
            goto L_0x0023
        L_0x003d:
            r1 = 4106(0x100a, float:5.754E-42)
            int r1 = com.ibm.icu.lang.UCharacter.getIntPropertyValue(r5, r1)     // Catch:{ all -> 0x0093 }
            r2 = 22
            if (r1 == r2) goto L_0x004b
            r2 = 20
            if (r1 != r2) goto L_0x004d
        L_0x004b:
            r1 = 17
        L_0x004d:
            switch(r1) {
                case 17: goto L_0x0072;
                case 18: goto L_0x006b;
                case 23: goto L_0x0065;
                case 24: goto L_0x005f;
                case 28: goto L_0x0059;
                case 38: goto L_0x0053;
                default: goto L_0x0050;
            }
        L_0x0050:
            com.ibm.icu.text.UnhandledBreakEngine r2 = gUnhandledBreakEngine     // Catch:{ IOException -> 0x007f }
            goto L_0x0079
        L_0x0053:
            com.ibm.icu.text.ThaiBreakEngine r2 = new com.ibm.icu.text.ThaiBreakEngine     // Catch:{ IOException -> 0x007f }
            r2.<init>()     // Catch:{ IOException -> 0x007f }
            goto L_0x007e
        L_0x0059:
            com.ibm.icu.text.BurmeseBreakEngine r2 = new com.ibm.icu.text.BurmeseBreakEngine     // Catch:{ IOException -> 0x007f }
            r2.<init>()     // Catch:{ IOException -> 0x007f }
            goto L_0x007e
        L_0x005f:
            com.ibm.icu.text.LaoBreakEngine r2 = new com.ibm.icu.text.LaoBreakEngine     // Catch:{ IOException -> 0x007f }
            r2.<init>()     // Catch:{ IOException -> 0x007f }
            goto L_0x007e
        L_0x0065:
            com.ibm.icu.text.KhmerBreakEngine r2 = new com.ibm.icu.text.KhmerBreakEngine     // Catch:{ IOException -> 0x007f }
            r2.<init>()     // Catch:{ IOException -> 0x007f }
            goto L_0x007e
        L_0x006b:
            com.ibm.icu.text.CjkBreakEngine r2 = new com.ibm.icu.text.CjkBreakEngine     // Catch:{ IOException -> 0x007f }
            r3 = 1
            r2.<init>(r3)     // Catch:{ IOException -> 0x007f }
            goto L_0x007e
        L_0x0072:
            com.ibm.icu.text.CjkBreakEngine r2 = new com.ibm.icu.text.CjkBreakEngine     // Catch:{ IOException -> 0x007f }
            r3 = 0
            r2.<init>(r3)     // Catch:{ IOException -> 0x007f }
            goto L_0x007e
        L_0x0079:
            r2.handleChar(r5)     // Catch:{ IOException -> 0x007f }
            com.ibm.icu.text.UnhandledBreakEngine r2 = gUnhandledBreakEngine     // Catch:{ IOException -> 0x007f }
        L_0x007e:
            goto L_0x0081
        L_0x007f:
            r2 = move-exception
            r2 = 0
        L_0x0081:
            if (r2 == 0) goto L_0x0091
            com.ibm.icu.text.UnhandledBreakEngine r3 = gUnhandledBreakEngine     // Catch:{ all -> 0x0093 }
            if (r2 == r3) goto L_0x0091
            java.util.List<com.ibm.icu.text.LanguageBreakEngine> r3 = gAllBreakEngines     // Catch:{ all -> 0x0093 }
            r3.add(r2)     // Catch:{ all -> 0x0093 }
            java.util.List<com.ibm.icu.text.LanguageBreakEngine> r3 = r4.fBreakEngines     // Catch:{ all -> 0x0093 }
            r3.add(r2)     // Catch:{ all -> 0x0093 }
        L_0x0091:
            monitor-exit(r0)     // Catch:{ all -> 0x0093 }
            return r2
        L_0x0093:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0093 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RuleBasedBreakIterator.getLanguageBreakEngine(int):com.ibm.icu.text.LanguageBreakEngine");
    }

    private static class LookAheadResults {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int[] fKeys = new int[8];
        int[] fPositions = new int[8];
        int fUsedSlotLimit = 0;

        static {
            Class<RuleBasedBreakIterator> cls = RuleBasedBreakIterator.class;
        }

        LookAheadResults() {
        }

        /* access modifiers changed from: package-private */
        public int getPosition(int key) {
            for (int i = 0; i < this.fUsedSlotLimit; i++) {
                if (this.fKeys[i] == key) {
                    return this.fPositions[i];
                }
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public void setPosition(int key, int position) {
            int i = 0;
            while (i < this.fUsedSlotLimit) {
                if (this.fKeys[i] == key) {
                    this.fPositions[i] = position;
                    return;
                }
                i++;
            }
            if (i >= 8) {
                i = 7;
            }
            this.fKeys[i] = key;
            this.fPositions[i] = position;
            this.fUsedSlotLimit = i + 1;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.fUsedSlotLimit = 0;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r12v2, types: [short, int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int handleNext() {
        /*
            r18 = this;
            r0 = r18
            boolean r1 = TRACE
            if (r1 == 0) goto L_0x000d
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r2 = "Handle Next   pos      char  state category"
            r1.println(r2)
        L_0x000d:
            r1 = 0
            r0.fRuleStatusIndex = r1
            r0.fDictionaryCharCount = r1
            java.text.CharacterIterator r2 = r0.fText
            com.ibm.icu.impl.RBBIDataWrapper r3 = r0.fRData
            com.ibm.icu.impl.Trie2 r3 = r3.fTrie
            com.ibm.icu.impl.RBBIDataWrapper r4 = r0.fRData
            com.ibm.icu.impl.RBBIDataWrapper$RBBIStateTable r4 = r4.fFTable
            short[] r4 = r4.fTable
            int r5 = r0.fPosition
            r2.setIndex(r5)
            r6 = r5
            char r7 = r2.current()
            r8 = 2147483647(0x7fffffff, float:NaN)
            r9 = 55296(0xd800, float:7.7486E-41)
            r10 = -1
            r11 = 1
            if (r7 < r9) goto L_0x003b
            int r7 = com.ibm.icu.impl.CharacterIteration.nextTrail32(r2, r7)
            if (r7 != r8) goto L_0x003b
            r0.fDone = r11
            return r10
        L_0x003b:
            r12 = 1
            com.ibm.icu.impl.RBBIDataWrapper r13 = r0.fRData
            int r13 = r13.getRowIndex(r12)
            r14 = 3
            com.ibm.icu.impl.RBBIDataWrapper r15 = r0.fRData
            com.ibm.icu.impl.RBBIDataWrapper$RBBIStateTable r15 = r15.fFTable
            int r15 = r15.fFlags
            r16 = 1
            r17 = r15 & 2
            r9 = 10
            r11 = 5
            if (r17 == 0) goto L_0x009e
            r14 = 2
            r16 = 0
            boolean r17 = TRACE
            if (r17 == 0) goto L_0x009e
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r10 = "            "
            r1.append(r10)
            int r10 = r2.getIndex()
            java.lang.String r10 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r10, r11)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r8.print(r1)
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r8 = com.ibm.icu.impl.RBBIDataWrapper.intToHexString(r7, r9)
            r1.print(r8)
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r10 = 7
            java.lang.String r9 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r12, r10)
            r8.append(r9)
            r9 = 6
            java.lang.String r10 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r14, r9)
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            r1.println(r8)
        L_0x009e:
            com.ibm.icu.text.RuleBasedBreakIterator$LookAheadResults r1 = r0.fLookAheadMatches
            r1.reset()
            r1 = r16
        L_0x00a5:
            if (r12 == 0) goto L_0x0188
            r8 = 2147483647(0x7fffffff, float:NaN)
            if (r7 != r8) goto L_0x00ba
            r9 = 2
            if (r1 != r9) goto L_0x00b1
            goto L_0x0188
        L_0x00b1:
            r1 = 2
            r9 = 1
            r14 = r9
            r8 = 55296(0xd800, float:7.7486E-41)
            r11 = 6
            goto L_0x012f
        L_0x00ba:
            r9 = 1
            if (r1 != r9) goto L_0x012a
            int r10 = r3.get(r7)
            short r10 = (short) r10
            r14 = r10 & 16384(0x4000, float:2.2959E-41)
            if (r14 == 0) goto L_0x00ce
            int r14 = r0.fDictionaryCharCount
            int r14 = r14 + r9
            r0.fDictionaryCharCount = r14
            r14 = r10 & -16385(0xffffffffffffbfff, float:NaN)
            short r10 = (short) r14
        L_0x00ce:
            boolean r14 = TRACE
            if (r14 == 0) goto L_0x011a
            java.io.PrintStream r14 = java.lang.System.out
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "            "
            r8.append(r9)
            int r9 = r2.getIndex()
            java.lang.String r9 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r9, r11)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r14.print(r8)
            java.io.PrintStream r8 = java.lang.System.out
            r9 = 10
            java.lang.String r14 = com.ibm.icu.impl.RBBIDataWrapper.intToHexString(r7, r9)
            r8.print(r14)
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r9 = 7
            java.lang.String r11 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r12, r9)
            r14.append(r11)
            r11 = 6
            java.lang.String r9 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r10, r11)
            r14.append(r9)
            java.lang.String r9 = r14.toString()
            r8.println(r9)
            goto L_0x011b
        L_0x011a:
            r11 = 6
        L_0x011b:
            char r7 = r2.next()
            r8 = 55296(0xd800, float:7.7486E-41)
            if (r7 < r8) goto L_0x0128
            int r7 = com.ibm.icu.impl.CharacterIteration.nextTrail32(r2, r7)
        L_0x0128:
            r14 = r10
            goto L_0x012f
        L_0x012a:
            r8 = 55296(0xd800, float:7.7486E-41)
            r11 = 6
            r1 = 1
        L_0x012f:
            int r9 = r13 + 4
            int r9 = r9 + r14
            short r12 = r4[r9]
            com.ibm.icu.impl.RBBIDataWrapper r9 = r0.fRData
            int r13 = r9.getRowIndex(r12)
            int r9 = r13 + 0
            short r9 = r4[r9]
            r10 = 1114111(0x10ffff, float:1.561202E-39)
            r8 = 65536(0x10000, float:9.18355E-41)
            r11 = -1
            if (r9 != r11) goto L_0x0156
            int r6 = r2.getIndex()
            if (r7 < r8) goto L_0x0150
            if (r7 > r10) goto L_0x0150
            int r6 = r6 + -1
        L_0x0150:
            int r9 = r13 + 2
            short r9 = r4[r9]
            r0.fRuleStatusIndex = r9
        L_0x0156:
            int r9 = r13 + 0
            short r9 = r4[r9]
            if (r9 <= 0) goto L_0x016d
            com.ibm.icu.text.RuleBasedBreakIterator$LookAheadResults r11 = r0.fLookAheadMatches
            int r11 = r11.getPosition(r9)
            if (r11 < 0) goto L_0x016d
            int r8 = r13 + 2
            short r8 = r4[r8]
            r0.fRuleStatusIndex = r8
            r0.fPosition = r11
            return r11
        L_0x016d:
            int r11 = r13 + 1
            short r11 = r4[r11]
            if (r11 == 0) goto L_0x0184
            int r16 = r2.getIndex()
            if (r7 < r8) goto L_0x017d
            if (r7 > r10) goto L_0x017d
            int r16 = r16 + -1
        L_0x017d:
            r8 = r16
            com.ibm.icu.text.RuleBasedBreakIterator$LookAheadResults r10 = r0.fLookAheadMatches
            r10.setPosition(r11, r8)
        L_0x0184:
            r11 = 5
            goto L_0x00a5
        L_0x0188:
            if (r6 != r5) goto L_0x01a2
            boolean r8 = TRACE
            if (r8 == 0) goto L_0x0195
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.String r9 = "Iterator did not move. Advancing by 1."
            r8.println(r9)
        L_0x0195:
            r2.setIndex(r5)
            com.ibm.icu.impl.CharacterIteration.next32(r2)
            int r6 = r2.getIndex()
            r8 = 0
            r0.fRuleStatusIndex = r8
        L_0x01a2:
            r0.fPosition = r6
            boolean r8 = TRACE
            if (r8 == 0) goto L_0x01be
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "result = "
            r9.append(r10)
            r9.append(r6)
            java.lang.String r9 = r9.toString()
            r8.println(r9)
        L_0x01be:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RuleBasedBreakIterator.handleNext():int");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r6v3, types: [short, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int handleSafePrevious(int r13) {
        /*
            r12 = this;
            r0 = 0
            r1 = 0
            java.text.CharacterIterator r2 = r12.fText
            com.ibm.icu.impl.RBBIDataWrapper r3 = r12.fRData
            com.ibm.icu.impl.Trie2 r3 = r3.fTrie
            com.ibm.icu.impl.RBBIDataWrapper r4 = r12.fRData
            com.ibm.icu.impl.RBBIDataWrapper$RBBIStateTable r4 = r4.fRTable
            short[] r4 = r4.fTable
            CISetIndex32(r2, r13)
            boolean r5 = TRACE
            if (r5 == 0) goto L_0x001c
            java.io.PrintStream r5 = java.lang.System.out
            java.lang.String r6 = "Handle Previous   pos   char  state category"
            r5.print(r6)
        L_0x001c:
            int r5 = r2.getIndex()
            int r6 = r2.getBeginIndex()
            if (r5 != r6) goto L_0x0028
            r5 = -1
            return r5
        L_0x0028:
            int r5 = com.ibm.icu.impl.CharacterIteration.previous32(r2)
            r6 = 1
            com.ibm.icu.impl.RBBIDataWrapper r7 = r12.fRData
            int r7 = r7.getRowIndex(r6)
        L_0x0033:
            r8 = 2147483647(0x7fffffff, float:NaN)
            if (r5 == r8) goto L_0x00a0
            int r8 = r3.get(r5)
            short r0 = (short) r8
            r8 = r0 & -16385(0xffffffffffffbfff, float:NaN)
            short r0 = (short) r8
            boolean r8 = TRACE
            if (r8 == 0) goto L_0x008c
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "            "
            r9.append(r10)
            int r10 = r2.getIndex()
            r11 = 5
            java.lang.String r10 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r10, r11)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.print(r9)
            java.io.PrintStream r8 = java.lang.System.out
            r9 = 10
            java.lang.String r9 = com.ibm.icu.impl.RBBIDataWrapper.intToHexString(r5, r9)
            r8.print(r9)
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r10 = 7
            java.lang.String r10 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r6, r10)
            r9.append(r10)
            r10 = 6
            java.lang.String r10 = com.ibm.icu.impl.RBBIDataWrapper.intToString(r0, r10)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.println(r9)
        L_0x008c:
            int r8 = r7 + 4
            int r8 = r8 + r0
            short r6 = r4[r8]
            com.ibm.icu.impl.RBBIDataWrapper r8 = r12.fRData
            int r7 = r8.getRowIndex(r6)
            if (r6 != 0) goto L_0x009b
            goto L_0x00a0
        L_0x009b:
            int r5 = com.ibm.icu.impl.CharacterIteration.previous32(r2)
            goto L_0x0033
        L_0x00a0:
            int r1 = r2.getIndex()
            boolean r8 = TRACE
            if (r8 == 0) goto L_0x00be
            java.io.PrintStream r8 = java.lang.System.out
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "result = "
            r9.append(r10)
            r9.append(r1)
            java.lang.String r9 = r9.toString()
            r8.println(r9)
        L_0x00be:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RuleBasedBreakIterator.handleSafePrevious(int):int");
    }

    private static int CISetIndex32(CharacterIterator ci, int index) {
        if (index <= ci.getBeginIndex()) {
            ci.first();
        } else if (index >= ci.getEndIndex()) {
            ci.setIndex(ci.getEndIndex());
        } else if (Character.isLowSurrogate(ci.setIndex(index)) && !Character.isHighSurrogate(ci.previous())) {
            ci.next();
        }
        return ci.getIndex();
    }

    class DictionaryCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int fBoundary;
        DictionaryBreakEngine.DequeI fBreaks;
        int fFirstRuleStatusIndex;
        int fLimit;
        int fOtherRuleStatusIndex;
        int fPositionInCache;
        int fStart;
        int fStatusIndex;

        static {
            Class<RuleBasedBreakIterator> cls = RuleBasedBreakIterator.class;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.fPositionInCache = -1;
            this.fStart = 0;
            this.fLimit = 0;
            this.fFirstRuleStatusIndex = 0;
            this.fOtherRuleStatusIndex = 0;
            this.fBreaks.removeAllElements();
        }

        /* access modifiers changed from: package-private */
        public boolean following(int fromPos) {
            if (fromPos >= this.fLimit || fromPos < this.fStart) {
                this.fPositionInCache = -1;
                return false;
            } else if (this.fPositionInCache < 0 || this.fPositionInCache >= this.fBreaks.size() || this.fBreaks.elementAt(this.fPositionInCache) != fromPos) {
                this.fPositionInCache = 0;
                while (this.fPositionInCache < this.fBreaks.size()) {
                    int r = this.fBreaks.elementAt(this.fPositionInCache);
                    if (r > fromPos) {
                        this.fBoundary = r;
                        this.fStatusIndex = this.fOtherRuleStatusIndex;
                        return true;
                    }
                    this.fPositionInCache++;
                }
                this.fPositionInCache = -1;
                return false;
            } else {
                this.fPositionInCache++;
                if (this.fPositionInCache >= this.fBreaks.size()) {
                    this.fPositionInCache = -1;
                    return false;
                }
                this.fBoundary = this.fBreaks.elementAt(this.fPositionInCache);
                this.fStatusIndex = this.fOtherRuleStatusIndex;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean preceding(int fromPos) {
            if (fromPos <= this.fStart || fromPos > this.fLimit) {
                this.fPositionInCache = -1;
                return false;
            }
            if (fromPos == this.fLimit) {
                this.fPositionInCache = this.fBreaks.size() - 1;
                if (this.fPositionInCache >= 0) {
                }
            }
            if (this.fPositionInCache > 0 && this.fPositionInCache < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == fromPos) {
                this.fPositionInCache--;
                int r = this.fBreaks.elementAt(this.fPositionInCache);
                this.fBoundary = r;
                this.fStatusIndex = r == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                return true;
            } else if (this.fPositionInCache == 0) {
                this.fPositionInCache = -1;
                return false;
            } else {
                this.fPositionInCache = this.fBreaks.size() - 1;
                while (this.fPositionInCache >= 0) {
                    int r2 = this.fBreaks.elementAt(this.fPositionInCache);
                    if (r2 < fromPos) {
                        this.fBoundary = r2;
                        this.fStatusIndex = r2 == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                        return true;
                    }
                    this.fPositionInCache--;
                }
                this.fPositionInCache = -1;
                return false;
            }
        }

        /* access modifiers changed from: package-private */
        public void populateDictionary(int startPos, int endPos, int firstRuleStatus, int otherRuleStatus) {
            if (endPos - startPos > 1) {
                reset();
                this.fFirstRuleStatusIndex = firstRuleStatus;
                this.fOtherRuleStatusIndex = otherRuleStatus;
                int rangeStart = startPos;
                int rangeEnd = endPos;
                int foundBreakCount = 0;
                RuleBasedBreakIterator.this.fText.setIndex(rangeStart);
                int c = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
                int category = (short) RuleBasedBreakIterator.this.fRData.fTrie.get(c);
                while (true) {
                    int index = RuleBasedBreakIterator.this.fText.getIndex();
                    int current = index;
                    if (index < rangeEnd && (category & 16384) == 0) {
                        c = CharacterIteration.next32(RuleBasedBreakIterator.this.fText);
                        category = (short) RuleBasedBreakIterator.this.fRData.fTrie.get(c);
                    } else if (current >= rangeEnd) {
                        break;
                    } else {
                        LanguageBreakEngine lbe = RuleBasedBreakIterator.this.getLanguageBreakEngine(c);
                        if (lbe != null) {
                            foundBreakCount += lbe.findBreaks(RuleBasedBreakIterator.this.fText, rangeStart, rangeEnd, this.fBreaks);
                        }
                        c = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
                        category = (short) RuleBasedBreakIterator.this.fRData.fTrie.get(c);
                    }
                }
                if (foundBreakCount > 0) {
                    if (startPos < this.fBreaks.elementAt(0)) {
                        this.fBreaks.offer(startPos);
                    }
                    if (endPos > this.fBreaks.peek()) {
                        this.fBreaks.push(endPos);
                    }
                    this.fPositionInCache = 0;
                    this.fStart = this.fBreaks.elementAt(0);
                    this.fLimit = this.fBreaks.peek();
                }
            }
        }

        DictionaryCache() {
            this.fPositionInCache = -1;
            this.fBreaks = new DictionaryBreakEngine.DequeI();
        }

        DictionaryCache(DictionaryCache src) {
            try {
                this.fBreaks = (DictionaryBreakEngine.DequeI) src.fBreaks.clone();
                this.fPositionInCache = src.fPositionInCache;
                this.fStart = src.fStart;
                this.fLimit = src.fLimit;
                this.fFirstRuleStatusIndex = src.fFirstRuleStatusIndex;
                this.fOtherRuleStatusIndex = src.fOtherRuleStatusIndex;
                this.fBoundary = src.fBoundary;
                this.fStatusIndex = src.fStatusIndex;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class BreakCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CACHE_SIZE = 128;
        static final boolean RetainCachePosition = false;
        static final boolean UpdateCachePosition = true;
        int[] fBoundaries = new int[128];
        int fBufIdx;
        int fEndBufIdx;
        DictionaryBreakEngine.DequeI fSideBuffer = new DictionaryBreakEngine.DequeI();
        int fStartBufIdx;
        short[] fStatuses = new short[128];
        int fTextIdx;

        static {
            Class<RuleBasedBreakIterator> cls = RuleBasedBreakIterator.class;
        }

        BreakCache() {
            reset();
        }

        /* access modifiers changed from: package-private */
        public void reset(int pos, int ruleStatus) {
            this.fStartBufIdx = 0;
            this.fEndBufIdx = 0;
            this.fTextIdx = pos;
            this.fBufIdx = 0;
            this.fBoundaries[0] = pos;
            this.fStatuses[0] = (short) ruleStatus;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            reset(0, 0);
        }

        /* access modifiers changed from: package-private */
        public void next() {
            if (this.fBufIdx == this.fEndBufIdx) {
                boolean unused = RuleBasedBreakIterator.this.fDone = !populateFollowing();
                int unused2 = RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
                int unused3 = RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
                return;
            }
            this.fBufIdx = modChunkSize(this.fBufIdx + 1);
            this.fTextIdx = RuleBasedBreakIterator.this.fPosition = this.fBoundaries[this.fBufIdx];
            int unused4 = RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
        }

        /* access modifiers changed from: package-private */
        public void previous() {
            int initialBufIdx = this.fBufIdx;
            boolean z = true;
            if (this.fBufIdx == this.fStartBufIdx) {
                populatePreceding();
            } else {
                this.fBufIdx = modChunkSize(this.fBufIdx - 1);
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
            }
            RuleBasedBreakIterator ruleBasedBreakIterator = RuleBasedBreakIterator.this;
            if (this.fBufIdx != initialBufIdx) {
                z = false;
            }
            boolean unused = ruleBasedBreakIterator.fDone = z;
            int unused2 = RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            int unused3 = RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
        }

        /* access modifiers changed from: package-private */
        public void following(int startPos) {
            if (startPos == this.fTextIdx || seek(startPos) || populateNear(startPos)) {
                boolean unused = RuleBasedBreakIterator.this.fDone = false;
                next();
            }
        }

        /* access modifiers changed from: package-private */
        public void preceding(int startPos) {
            if (startPos != this.fTextIdx && !seek(startPos) && !populateNear(startPos)) {
                return;
            }
            if (startPos == this.fTextIdx) {
                previous();
            } else {
                current();
            }
        }

        /* access modifiers changed from: package-private */
        public int current() {
            int unused = RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            int unused2 = RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            boolean unused3 = RuleBasedBreakIterator.this.fDone = false;
            return this.fTextIdx;
        }

        /* access modifiers changed from: package-private */
        public boolean populateNear(int position) {
            if (position < this.fBoundaries[this.fStartBufIdx] - 15 || position > this.fBoundaries[this.fEndBufIdx] + 15) {
                int aBoundary = RuleBasedBreakIterator.this.fText.getBeginIndex();
                int ruleStatusIndex = 0;
                if (position > aBoundary + 20) {
                    int backupPos = RuleBasedBreakIterator.this.handleSafePrevious(position);
                    if (backupPos > aBoundary) {
                        int unused = RuleBasedBreakIterator.this.fPosition = backupPos;
                        aBoundary = RuleBasedBreakIterator.this.handleNext();
                        if (aBoundary == backupPos + 1 || (aBoundary == backupPos + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(backupPos)) && Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next()))) {
                            aBoundary = RuleBasedBreakIterator.this.handleNext();
                        }
                    }
                    ruleStatusIndex = RuleBasedBreakIterator.this.fRuleStatusIndex;
                }
                reset(aBoundary, ruleStatusIndex);
            }
            if (this.fBoundaries[this.fEndBufIdx] < position) {
                while (this.fBoundaries[this.fEndBufIdx] < position) {
                    if (!populateFollowing()) {
                        return false;
                    }
                }
                this.fBufIdx = this.fEndBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                while (this.fTextIdx > position) {
                    previous();
                }
                return true;
            } else if (this.fBoundaries[this.fStartBufIdx] <= position) {
                return true;
            } else {
                while (this.fBoundaries[this.fStartBufIdx] > position) {
                    populatePreceding();
                }
                this.fBufIdx = this.fStartBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                while (this.fTextIdx < position) {
                    next();
                }
                if (this.fTextIdx > position) {
                    previous();
                }
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean populateFollowing() {
            int pos;
            int fromPosition = this.fBoundaries[this.fEndBufIdx];
            short fromRuleStatusIdx = this.fStatuses[this.fEndBufIdx];
            if (RuleBasedBreakIterator.this.fDictionaryCache.following(fromPosition)) {
                addFollowing(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                return true;
            }
            int unused = RuleBasedBreakIterator.this.fPosition = fromPosition;
            int pos2 = RuleBasedBreakIterator.this.handleNext();
            if (pos2 == -1) {
                return false;
            }
            int ruleStatusIdx = RuleBasedBreakIterator.this.fRuleStatusIndex;
            if (RuleBasedBreakIterator.this.fDictionaryCharCount > 0) {
                RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(fromPosition, pos2, fromRuleStatusIdx, ruleStatusIdx);
                if (RuleBasedBreakIterator.this.fDictionaryCache.following(fromPosition)) {
                    addFollowing(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                    return true;
                }
            }
            addFollowing(pos2, ruleStatusIdx, true);
            int i = pos2;
            for (int count = 0; count < 6 && (pos = RuleBasedBreakIterator.this.handleNext()) != -1 && RuleBasedBreakIterator.this.fDictionaryCharCount <= 0; count++) {
                addFollowing(pos, RuleBasedBreakIterator.this.fRuleStatusIndex, false);
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean populatePreceding() {
            int positionStatusIdx;
            int position;
            int textBegin = RuleBasedBreakIterator.this.fText.getBeginIndex();
            int fromPosition = this.fBoundaries[this.fStartBufIdx];
            if (fromPosition == textBegin) {
                return false;
            }
            int position2 = textBegin;
            if (RuleBasedBreakIterator.this.fDictionaryCache.preceding(fromPosition)) {
                addPreceding(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                return true;
            }
            int positionStatusIdx2 = position2;
            int backupPosition = fromPosition;
            do {
                int backupPosition2 = backupPosition - 30;
                if (backupPosition2 <= textBegin) {
                    backupPosition = textBegin;
                } else {
                    backupPosition = RuleBasedBreakIterator.this.handleSafePrevious(backupPosition2);
                }
                if (backupPosition == -1 || backupPosition == textBegin) {
                    position = textBegin;
                    positionStatusIdx = 0;
                    continue;
                } else {
                    int unused = RuleBasedBreakIterator.this.fPosition = backupPosition;
                    position = RuleBasedBreakIterator.this.handleNext();
                    if (position == backupPosition + 1 || (position == backupPosition + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(backupPosition)) && Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next()))) {
                        position = RuleBasedBreakIterator.this.handleNext();
                    }
                    positionStatusIdx = RuleBasedBreakIterator.this.fRuleStatusIndex;
                    continue;
                }
            } while (position >= fromPosition);
            this.fSideBuffer.removeAllElements();
            this.fSideBuffer.push(position);
            this.fSideBuffer.push(positionStatusIdx);
            do {
                int prevPosition = RuleBasedBreakIterator.this.fPosition = position;
                int prevStatusIdx = positionStatusIdx;
                int position3 = RuleBasedBreakIterator.this.handleNext();
                int positionStatusIdx3 = RuleBasedBreakIterator.this.fRuleStatusIndex;
                if (position3 == -1) {
                    break;
                }
                boolean segmentHandledByDictionary = false;
                if (RuleBasedBreakIterator.this.fDictionaryCharCount != 0) {
                    RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(prevPosition, position3, prevStatusIdx, positionStatusIdx3);
                    while (RuleBasedBreakIterator.this.fDictionaryCache.following(prevPosition)) {
                        position3 = RuleBasedBreakIterator.this.fDictionaryCache.fBoundary;
                        positionStatusIdx3 = RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex;
                        segmentHandledByDictionary = true;
                        if (position3 >= fromPosition) {
                            break;
                        }
                        this.fSideBuffer.push(position3);
                        this.fSideBuffer.push(positionStatusIdx3);
                        prevPosition = position3;
                    }
                }
                if (!segmentHandledByDictionary && position < fromPosition) {
                    this.fSideBuffer.push(position);
                    this.fSideBuffer.push(positionStatusIdx);
                    continue;
                }
            } while (position < fromPosition);
            boolean success = false;
            if (!this.fSideBuffer.isEmpty()) {
                addPreceding(this.fSideBuffer.pop(), this.fSideBuffer.pop(), true);
                success = true;
            }
            while (!this.fSideBuffer.isEmpty()) {
                if (!addPreceding(this.fSideBuffer.pop(), this.fSideBuffer.pop(), false)) {
                    break;
                }
            }
            return success;
        }

        /* access modifiers changed from: package-private */
        public void addFollowing(int position, int ruleStatusIdx, boolean update) {
            int nextIdx = modChunkSize(this.fEndBufIdx + 1);
            if (nextIdx == this.fStartBufIdx) {
                this.fStartBufIdx = modChunkSize(this.fStartBufIdx + 6);
            }
            this.fBoundaries[nextIdx] = position;
            this.fStatuses[nextIdx] = (short) ruleStatusIdx;
            this.fEndBufIdx = nextIdx;
            if (update) {
                this.fBufIdx = nextIdx;
                this.fTextIdx = position;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean addPreceding(int position, int ruleStatusIdx, boolean update) {
            int nextIdx = modChunkSize(this.fStartBufIdx - 1);
            if (nextIdx == this.fEndBufIdx) {
                if (this.fBufIdx == this.fEndBufIdx && !update) {
                    return false;
                }
                this.fEndBufIdx = modChunkSize(this.fEndBufIdx - 1);
            }
            this.fBoundaries[nextIdx] = position;
            this.fStatuses[nextIdx] = (short) ruleStatusIdx;
            this.fStartBufIdx = nextIdx;
            if (update) {
                this.fBufIdx = nextIdx;
                this.fTextIdx = position;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean seek(int pos) {
            if (pos < this.fBoundaries[this.fStartBufIdx] || pos > this.fBoundaries[this.fEndBufIdx]) {
                return false;
            }
            if (pos == this.fBoundaries[this.fStartBufIdx]) {
                this.fBufIdx = this.fStartBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return true;
            } else if (pos == this.fBoundaries[this.fEndBufIdx]) {
                this.fBufIdx = this.fEndBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return true;
            } else {
                int min = this.fStartBufIdx;
                int max = this.fEndBufIdx;
                while (min != max) {
                    int probe = modChunkSize(((min + max) + (min > max ? 128 : 0)) / 2);
                    if (this.fBoundaries[probe] > pos) {
                        max = probe;
                    } else {
                        min = modChunkSize(probe + 1);
                    }
                }
                this.fBufIdx = modChunkSize(max - 1);
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return true;
            }
        }

        BreakCache(BreakCache src) {
            this.fStartBufIdx = src.fStartBufIdx;
            this.fEndBufIdx = src.fEndBufIdx;
            this.fTextIdx = src.fTextIdx;
            this.fBufIdx = src.fBufIdx;
            this.fBoundaries = (int[]) src.fBoundaries.clone();
            this.fStatuses = (short[]) src.fStatuses.clone();
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
        }

        /* access modifiers changed from: package-private */
        public void dumpCache() {
            System.out.printf("fTextIdx:%d   fBufIdx:%d%n", new Object[]{Integer.valueOf(this.fTextIdx), Integer.valueOf(this.fBufIdx)});
            int i = this.fStartBufIdx;
            while (true) {
                System.out.printf("%d  %d%n", new Object[]{Integer.valueOf(i), Integer.valueOf(this.fBoundaries[i])});
                if (i != this.fEndBufIdx) {
                    i = modChunkSize(i + 1);
                } else {
                    return;
                }
            }
        }

        private final int modChunkSize(int index) {
            return index & 127;
        }
    }
}
