package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;

/* loaded from: classes5.dex */
class Quantifier implements UnicodeMatcher {
    public static final int MAX = Integer.MAX_VALUE;
    private UnicodeMatcher matcher;
    private int maxCount;
    private int minCount;

    public Quantifier(UnicodeMatcher theMatcher, int theMinCount, int theMaxCount) {
        if (theMatcher == null || theMinCount < 0 || theMaxCount < 0 || theMinCount > theMaxCount) {
            throw new IllegalArgumentException();
        }
        this.matcher = theMatcher;
        this.minCount = theMinCount;
        this.maxCount = theMaxCount;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public int matches(Replaceable text, int[] offset, int limit, boolean incremental) {
        int start = offset[0];
        int count = 0;
        while (true) {
            if (count >= this.maxCount) {
                break;
            }
            int pos = offset[0];
            int m = this.matcher.matches(text, offset, limit, incremental);
            if (m == 2) {
                count++;
                if (pos == offset[0]) {
                    break;
                }
            } else if (incremental && m == 1) {
                return 1;
            }
        }
        if (incremental && offset[0] == limit) {
            return 1;
        }
        if (count >= this.minCount) {
            return 2;
        }
        offset[0] = start;
        return 0;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public String toPattern(boolean escapeUnprintable) {
        StringBuilder result = new StringBuilder();
        result.append(this.matcher.toPattern(escapeUnprintable));
        if (this.minCount == 0) {
            if (this.maxCount == 1) {
                result.append('?');
                return result.toString();
            } else if (this.maxCount == Integer.MAX_VALUE) {
                result.append('*');
                return result.toString();
            }
        } else if (this.minCount == 1 && this.maxCount == Integer.MAX_VALUE) {
            result.append('+');
            return result.toString();
        }
        result.append('{');
        result.append(Utility.hex(this.minCount, 1));
        result.append(',');
        if (this.maxCount != Integer.MAX_VALUE) {
            result.append(Utility.hex(this.maxCount, 1));
        }
        result.append('}');
        return result.toString();
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public boolean matchesIndexValue(int v) {
        return this.minCount == 0 || this.matcher.matchesIndexValue(v);
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public void addMatchSetTo(UnicodeSet toUnionTo) {
        if (this.maxCount > 0) {
            this.matcher.addMatchSetTo(toUnionTo);
        }
    }
}
