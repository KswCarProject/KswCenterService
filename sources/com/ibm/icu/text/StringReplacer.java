package com.ibm.icu.text;

import android.net.wifi.WifiEnterpriseConfig;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.RuleBasedTransliterator;

/* loaded from: classes5.dex */
class StringReplacer implements UnicodeReplacer {
    private int cursorPos;
    private final RuleBasedTransliterator.Data data;
    private boolean hasCursor;
    private boolean isComplex;
    private String output;

    public StringReplacer(String theOutput, int theCursorPos, RuleBasedTransliterator.Data theData) {
        this.output = theOutput;
        this.cursorPos = theCursorPos;
        this.hasCursor = true;
        this.data = theData;
        this.isComplex = true;
    }

    public StringReplacer(String theOutput, RuleBasedTransliterator.Data theData) {
        this.output = theOutput;
        this.cursorPos = 0;
        this.hasCursor = false;
        this.data = theData;
        this.isComplex = true;
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public int replace(Replaceable text, int start, int limit, int[] cursor) {
        int destStart;
        int newStart;
        int outLen;
        int newStart2;
        int newStart3;
        if (!this.isComplex) {
            text.replace(start, limit, this.output);
            outLen = this.output.length();
            int newStart4 = this.cursorPos;
            newStart = newStart4;
        } else {
            StringBuffer buf = new StringBuffer();
            this.isComplex = false;
            int tempStart = text.length();
            boolean z = true;
            if (start > 0) {
                int len = UTF16.getCharCount(text.char32At(start - 1));
                text.copy(start - len, start, tempStart);
                destStart = tempStart + len;
            } else {
                text.replace(tempStart, tempStart, "\uffff");
                destStart = tempStart + 1;
            }
            int destLimit = destStart;
            int tempExtra = 0;
            newStart = 0;
            int oOutput = 0;
            while (oOutput < this.output.length()) {
                if (oOutput == this.cursorPos) {
                    newStart = (buf.length() + destLimit) - destStart;
                }
                int c = UTF16.charAt(this.output, oOutput);
                int nextIndex = UTF16.getCharCount(c) + oOutput;
                if (nextIndex == this.output.length()) {
                    int tempExtra2 = UTF16.getCharCount(text.char32At(limit));
                    text.copy(limit, limit + tempExtra2, destLimit);
                    tempExtra = tempExtra2;
                }
                UnicodeReplacer r = this.data.lookupReplacer(c);
                if (r == null) {
                    UTF16.append(buf, c);
                } else {
                    this.isComplex = z;
                    if (buf.length() > 0) {
                        text.replace(destLimit, destLimit, buf.toString());
                        destLimit += buf.length();
                        buf.setLength(0);
                    }
                    destLimit += r.replace(text, destLimit, destLimit, cursor);
                }
                oOutput = nextIndex;
                z = true;
            }
            if (buf.length() > 0) {
                text.replace(destLimit, destLimit, buf.toString());
                destLimit += buf.length();
            }
            if (oOutput == this.cursorPos) {
                newStart = destLimit - destStart;
            }
            int outLen2 = destLimit - destStart;
            text.copy(destStart, destLimit, start);
            text.replace(tempStart + outLen2, destLimit + tempExtra + outLen2, "");
            text.replace(start + outLen2, limit + outLen2, "");
            outLen = outLen2;
        }
        if (this.hasCursor) {
            if (this.cursorPos >= 0) {
                if (this.cursorPos > this.output.length()) {
                    int newStart5 = start + outLen;
                    int n = this.cursorPos - this.output.length();
                    while (n > 0 && newStart5 < text.length()) {
                        newStart5 += UTF16.getCharCount(text.char32At(newStart5));
                        n--;
                    }
                    newStart3 = newStart5 + n;
                } else {
                    newStart2 = newStart + start;
                    cursor[0] = newStart2;
                }
            } else {
                int newStart6 = start;
                int n2 = this.cursorPos;
                while (n2 < 0 && newStart6 > 0) {
                    newStart6 -= UTF16.getCharCount(text.char32At(newStart6 - 1));
                    n2++;
                }
                newStart3 = newStart6 + n2;
            }
            newStart2 = newStart3;
            cursor[0] = newStart2;
        }
        return outLen;
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public String toReplacerPattern(boolean escapeUnprintable) {
        int cursor;
        StringBuffer rule = new StringBuffer();
        StringBuffer quoteBuf = new StringBuffer();
        int cursor2 = this.cursorPos;
        if (this.hasCursor && cursor2 < 0) {
            while (true) {
                cursor = cursor2 + 1;
                if (cursor2 >= 0) {
                    break;
                }
                Utility.appendToRule(rule, 64, true, escapeUnprintable, quoteBuf);
                cursor2 = cursor;
            }
            cursor2 = cursor;
        }
        for (int i = 0; i < this.output.length(); i++) {
            if (this.hasCursor && i == cursor2) {
                Utility.appendToRule(rule, 124, true, escapeUnprintable, quoteBuf);
            }
            char c = this.output.charAt(i);
            UnicodeReplacer r = this.data.lookupReplacer(c);
            if (r == null) {
                Utility.appendToRule(rule, c, false, escapeUnprintable, quoteBuf);
            } else {
                StringBuffer buf = new StringBuffer(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                buf.append(r.toReplacerPattern(escapeUnprintable));
                buf.append(' ');
                Utility.appendToRule(rule, buf.toString(), true, escapeUnprintable, quoteBuf);
            }
        }
        if (this.hasCursor && cursor2 > this.output.length()) {
            int cursor3 = cursor2 - this.output.length();
            while (true) {
                int cursor4 = cursor3 - 1;
                if (cursor3 <= 0) {
                    break;
                }
                Utility.appendToRule(rule, 64, true, escapeUnprintable, quoteBuf);
                cursor3 = cursor4;
            }
            Utility.appendToRule(rule, 124, true, escapeUnprintable, quoteBuf);
        }
        Utility.appendToRule(rule, -1, true, escapeUnprintable, quoteBuf);
        return rule.toString();
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public void addReplacementSetTo(UnicodeSet toUnionTo) {
        int i = 0;
        while (i < this.output.length()) {
            int ch = UTF16.charAt(this.output, i);
            UnicodeReplacer r = this.data.lookupReplacer(ch);
            if (r == null) {
                toUnionTo.add(ch);
            } else {
                r.addReplacementSetTo(toUnionTo);
            }
            i += UTF16.getCharCount(ch);
        }
    }
}
