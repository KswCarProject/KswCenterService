package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import java.text.ParsePosition;
import java.util.HashMap;

class RBBIRuleScanner {
    static final int chLS = 8232;
    static final int chNEL = 133;
    private static String gRuleSet_digit_char_pattern = "[0-9]";
    private static String gRuleSet_name_char_pattern = "[_\\p{L}\\p{N}]";
    private static String gRuleSet_name_start_char_pattern = "[_\\p{L}]";
    private static String gRuleSet_rule_char_pattern = "[^[\\p{Z}\\u0020-\\u007f]-[\\p{L}]-[\\p{N}]]";
    private static String gRuleSet_white_space_pattern = "[\\p{Pattern_White_Space}]";
    private static String kAny = "any";
    private static final int kStackSize = 100;
    RBBIRuleChar fC = new RBBIRuleChar();
    int fCharNum;
    int fLastChar;
    int fLineNum;
    boolean fLookAheadRule;
    int fNextIndex;
    boolean fNoChainInRule;
    RBBINode[] fNodeStack = new RBBINode[100];
    int fNodeStackPtr;
    int fOptionStart;
    boolean fQuoteMode;
    RBBIRuleBuilder fRB;
    boolean fReverseRule;
    int fRuleNum;
    UnicodeSet[] fRuleSets = new UnicodeSet[10];
    int fScanIndex;
    HashMap<String, RBBISetTableEl> fSetTable = new HashMap<>();
    short[] fStack = new short[100];
    int fStackPtr;
    RBBISymbolTable fSymbolTable;

    static class RBBIRuleChar {
        int fChar;
        boolean fEscaped;

        RBBIRuleChar() {
        }
    }

    RBBIRuleScanner(RBBIRuleBuilder rb) {
        this.fRB = rb;
        this.fLineNum = 1;
        this.fRuleSets[3] = new UnicodeSet(gRuleSet_rule_char_pattern);
        this.fRuleSets[4] = new UnicodeSet(gRuleSet_white_space_pattern);
        this.fRuleSets[1] = new UnicodeSet(gRuleSet_name_char_pattern);
        this.fRuleSets[2] = new UnicodeSet(gRuleSet_name_start_char_pattern);
        this.fRuleSets[0] = new UnicodeSet(gRuleSet_digit_char_pattern);
        this.fSymbolTable = new RBBISymbolTable(this);
    }

    /* access modifiers changed from: package-private */
    public boolean doParseActions(int action) {
        int i = 3;
        switch (action) {
            case 1:
                if (this.fNodeStack[this.fNodeStackPtr].fLeftChild != null) {
                    return true;
                }
                error(66058);
                return false;
            case 2:
                RBBINode n = pushNewNode(0);
                findSetFor(kAny, n, (UnicodeSet) null);
                n.fFirstPos = this.fScanIndex;
                n.fLastPos = this.fNextIndex;
                n.fText = this.fRB.fRules.substring(n.fFirstPos, n.fLastPos);
                return true;
            case 3:
                fixOpStack(1);
                RBBINode startExprNode = this.fNodeStack[this.fNodeStackPtr - 2];
                RBBINode varRefNode = this.fNodeStack[this.fNodeStackPtr - 1];
                RBBINode RHSExprNode = this.fNodeStack[this.fNodeStackPtr];
                RHSExprNode.fFirstPos = startExprNode.fFirstPos;
                RHSExprNode.fLastPos = this.fScanIndex;
                RHSExprNode.fText = this.fRB.fRules.substring(RHSExprNode.fFirstPos, RHSExprNode.fLastPos);
                varRefNode.fLeftChild = RHSExprNode;
                RHSExprNode.fParent = varRefNode;
                this.fSymbolTable.addEntry(varRefNode.fText, varRefNode);
                this.fNodeStackPtr -= 3;
                return true;
            case 4:
                fixOpStack(1);
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rtree") >= 0) {
                    printNodeStack("end of rule");
                }
                Assert.assrt(this.fNodeStackPtr == 1);
                RBBINode thisRule = this.fNodeStack[this.fNodeStackPtr];
                if (this.fLookAheadRule) {
                    RBBINode endNode = pushNewNode(6);
                    RBBINode catNode = pushNewNode(8);
                    this.fNodeStackPtr -= 2;
                    catNode.fLeftChild = thisRule;
                    catNode.fRightChild = endNode;
                    this.fNodeStack[this.fNodeStackPtr] = catNode;
                    endNode.fVal = this.fRuleNum;
                    endNode.fLookAheadEnd = true;
                    thisRule = catNode;
                }
                thisRule.fRuleRoot = true;
                if (this.fRB.fChainRules && !this.fNoChainInRule) {
                    thisRule.fChainIn = true;
                }
                if (!this.fReverseRule) {
                    i = this.fRB.fDefaultTree;
                }
                int destRules = i;
                if (this.fRB.fTreeRoots[destRules] != null) {
                    RBBINode thisRule2 = this.fNodeStack[this.fNodeStackPtr];
                    RBBINode prevRules = this.fRB.fTreeRoots[destRules];
                    RBBINode orNode = pushNewNode(9);
                    orNode.fLeftChild = prevRules;
                    prevRules.fParent = orNode;
                    orNode.fRightChild = thisRule2;
                    thisRule2.fParent = orNode;
                    this.fRB.fTreeRoots[destRules] = orNode;
                } else {
                    this.fRB.fTreeRoots[destRules] = this.fNodeStack[this.fNodeStackPtr];
                }
                this.fReverseRule = false;
                this.fLookAheadRule = false;
                this.fNoChainInRule = false;
                this.fNodeStackPtr = 0;
                return true;
            case 5:
                RBBINode n2 = this.fNodeStack[this.fNodeStackPtr];
                if (n2 == null || n2.fType != 2) {
                    error(66049);
                    return true;
                }
                n2.fLastPos = this.fScanIndex;
                n2.fText = this.fRB.fRules.substring(n2.fFirstPos + 1, n2.fLastPos);
                n2.fLeftChild = this.fSymbolTable.lookupNode(n2.fText);
                return true;
            case 6:
                return false;
            case 7:
                fixOpStack(4);
                RBBINode[] rBBINodeArr = this.fNodeStack;
                int i2 = this.fNodeStackPtr;
                this.fNodeStackPtr = i2 - 1;
                RBBINode operandNode = rBBINodeArr[i2];
                RBBINode catNode2 = pushNewNode(8);
                catNode2.fLeftChild = operandNode;
                operandNode.fParent = catNode2;
                return true;
            case 8:
            case 13:
                return true;
            case 9:
                fixOpStack(4);
                RBBINode[] rBBINodeArr2 = this.fNodeStack;
                int i3 = this.fNodeStackPtr;
                this.fNodeStackPtr = i3 - 1;
                RBBINode operandNode2 = rBBINodeArr2[i3];
                RBBINode orNode2 = pushNewNode(9);
                orNode2.fLeftChild = operandNode2;
                operandNode2.fParent = orNode2;
                return true;
            case 10:
                fixOpStack(2);
                return true;
            case 11:
                pushNewNode(7);
                this.fRuleNum++;
                return true;
            case 12:
                pushNewNode(15);
                return true;
            case 14:
                this.fNoChainInRule = true;
                return true;
            case 15:
                String opt = this.fRB.fRules.substring(this.fOptionStart, this.fScanIndex);
                if (opt.equals("chain")) {
                    this.fRB.fChainRules = true;
                    return true;
                } else if (opt.equals("LBCMNoChain")) {
                    this.fRB.fLBCMNoChain = true;
                    return true;
                } else if (opt.equals("forward")) {
                    this.fRB.fDefaultTree = 0;
                    return true;
                } else if (opt.equals("reverse")) {
                    this.fRB.fDefaultTree = 1;
                    return true;
                } else if (opt.equals("safe_forward")) {
                    this.fRB.fDefaultTree = 2;
                    return true;
                } else if (opt.equals("safe_reverse")) {
                    this.fRB.fDefaultTree = 3;
                    return true;
                } else if (opt.equals("lookAheadHardBreak")) {
                    this.fRB.fLookAheadHardBreak = true;
                    return true;
                } else if (opt.equals("quoted_literals_only")) {
                    this.fRuleSets[3].clear();
                    return true;
                } else if (opt.equals("unquoted_literals")) {
                    this.fRuleSets[3].applyPattern(gRuleSet_rule_char_pattern);
                    return true;
                } else {
                    error(66061);
                    return true;
                }
            case 16:
                this.fOptionStart = this.fScanIndex;
                return true;
            case 17:
                this.fReverseRule = true;
                return true;
            case 18:
                RBBINode n3 = pushNewNode(0);
                findSetFor(String.valueOf((char) this.fC.fChar), n3, (UnicodeSet) null);
                n3.fFirstPos = this.fScanIndex;
                n3.fLastPos = this.fNextIndex;
                n3.fText = this.fRB.fRules.substring(n3.fFirstPos, n3.fLastPos);
                return true;
            case 19:
                error(66052);
                return false;
            case 20:
                error(66054);
                return false;
            case 21:
                scanSet();
                return true;
            case 22:
                RBBINode n4 = pushNewNode(4);
                n4.fVal = this.fRuleNum;
                n4.fFirstPos = this.fScanIndex;
                n4.fLastPos = this.fNextIndex;
                n4.fText = this.fRB.fRules.substring(n4.fFirstPos, n4.fLastPos);
                this.fLookAheadRule = true;
                return true;
            case 23:
                this.fNodeStack[this.fNodeStackPtr - 1].fFirstPos = this.fNextIndex;
                pushNewNode(7);
                return true;
            case 24:
                RBBINode n5 = pushNewNode(5);
                n5.fVal = 0;
                n5.fFirstPos = this.fScanIndex;
                n5.fLastPos = this.fNextIndex;
                return true;
            case 25:
                pushNewNode(2).fFirstPos = this.fScanIndex;
                return true;
            case 26:
                RBBINode n6 = this.fNodeStack[this.fNodeStackPtr];
                n6.fVal = (n6.fVal * 10) + UCharacter.digit((char) this.fC.fChar, 10);
                return true;
            case 27:
                error(66062);
                return false;
            case 28:
                RBBINode n7 = this.fNodeStack[this.fNodeStackPtr];
                n7.fLastPos = this.fNextIndex;
                n7.fText = this.fRB.fRules.substring(n7.fFirstPos, n7.fLastPos);
                return true;
            case 29:
                RBBINode[] rBBINodeArr3 = this.fNodeStack;
                int i4 = this.fNodeStackPtr;
                this.fNodeStackPtr = i4 - 1;
                RBBINode operandNode3 = rBBINodeArr3[i4];
                RBBINode plusNode = pushNewNode(11);
                plusNode.fLeftChild = operandNode3;
                operandNode3.fParent = plusNode;
                return true;
            case 30:
                RBBINode[] rBBINodeArr4 = this.fNodeStack;
                int i5 = this.fNodeStackPtr;
                this.fNodeStackPtr = i5 - 1;
                RBBINode operandNode4 = rBBINodeArr4[i5];
                RBBINode qNode = pushNewNode(12);
                qNode.fLeftChild = operandNode4;
                operandNode4.fParent = qNode;
                return true;
            case 31:
                RBBINode[] rBBINodeArr5 = this.fNodeStack;
                int i6 = this.fNodeStackPtr;
                this.fNodeStackPtr = i6 - 1;
                RBBINode operandNode5 = rBBINodeArr5[i6];
                RBBINode starNode = pushNewNode(10);
                starNode.fLeftChild = operandNode5;
                operandNode5.fParent = starNode;
                return true;
            case 32:
                error(66052);
                return true;
            default:
                error(66049);
                return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void error(int e) {
        throw new IllegalArgumentException("Error " + e + " at line " + this.fLineNum + " column " + this.fCharNum);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void fixOpStack(int r6) {
        /*
            r5 = this;
        L_0x0000:
            com.ibm.icu.text.RBBINode[] r0 = r5.fNodeStack
            int r1 = r5.fNodeStackPtr
            int r1 = r1 + -1
            r0 = r0[r1]
            int r1 = r0.fPrecedence
            if (r1 != 0) goto L_0x001a
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r2 = "RBBIRuleScanner.fixOpStack, bad operator node"
            r1.print(r2)
            r1 = 66049(0x10201, float:9.2554E-41)
            r5.error(r1)
            return
        L_0x001a:
            int r1 = r0.fPrecedence
            r2 = 2
            if (r1 < r6) goto L_0x003b
            int r1 = r0.fPrecedence
            if (r1 > r2) goto L_0x0024
            goto L_0x003b
        L_0x0024:
            com.ibm.icu.text.RBBINode[] r1 = r5.fNodeStack
            int r2 = r5.fNodeStackPtr
            r1 = r1[r2]
            r0.fRightChild = r1
            com.ibm.icu.text.RBBINode[] r1 = r5.fNodeStack
            int r2 = r5.fNodeStackPtr
            r1 = r1[r2]
            r1.fParent = r0
            int r1 = r5.fNodeStackPtr
            int r1 = r1 + -1
            r5.fNodeStackPtr = r1
            goto L_0x0000
        L_0x003b:
            if (r6 > r2) goto L_0x005b
            int r1 = r0.fPrecedence
            if (r1 == r6) goto L_0x0047
            r1 = 66056(0x10208, float:9.2564E-41)
            r5.error(r1)
        L_0x0047:
            com.ibm.icu.text.RBBINode[] r1 = r5.fNodeStack
            int r2 = r5.fNodeStackPtr
            int r2 = r2 + -1
            com.ibm.icu.text.RBBINode[] r3 = r5.fNodeStack
            int r4 = r5.fNodeStackPtr
            r3 = r3[r4]
            r1[r2] = r3
            int r1 = r5.fNodeStackPtr
            int r1 = r1 + -1
            r5.fNodeStackPtr = r1
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RBBIRuleScanner.fixOpStack(int):void");
    }

    static class RBBISetTableEl {
        String key;
        RBBINode val;

        RBBISetTableEl() {
        }
    }

    /* access modifiers changed from: package-private */
    public void findSetFor(String s, RBBINode node, UnicodeSet setToAdopt) {
        RBBISetTableEl el = this.fSetTable.get(s);
        boolean z = false;
        if (el != null) {
            node.fLeftChild = el.val;
            if (node.fLeftChild.fType == 1) {
                z = true;
            }
            Assert.assrt(z);
            return;
        }
        if (setToAdopt == null) {
            if (s.equals(kAny)) {
                setToAdopt = new UnicodeSet(0, 1114111);
            } else {
                int c = UTF16.charAt(s, 0);
                setToAdopt = new UnicodeSet(c, c);
            }
        }
        RBBINode usetNode = new RBBINode(1);
        usetNode.fInputSet = setToAdopt;
        usetNode.fParent = node;
        node.fLeftChild = usetNode;
        usetNode.fText = s;
        this.fRB.fUSetNodes.add(usetNode);
        RBBISetTableEl el2 = new RBBISetTableEl();
        el2.key = s;
        el2.val = usetNode;
        this.fSetTable.put(el2.key, el2);
    }

    static String stripRules(String rules) {
        StringBuilder strippedRules = new StringBuilder();
        int rulesLength = rules.length();
        boolean skippingSpaces = false;
        int idx = 0;
        while (idx < rulesLength) {
            int cp = rules.codePointAt(idx);
            boolean whiteSpace = UCharacter.hasBinaryProperty(cp, 43);
            if (!skippingSpaces || !whiteSpace) {
                strippedRules.appendCodePoint(cp);
                skippingSpaces = whiteSpace;
            }
            idx = rules.offsetByCodePoints(idx, 1);
        }
        return strippedRules.toString();
    }

    /* access modifiers changed from: package-private */
    public int nextCharLL() {
        if (this.fNextIndex >= this.fRB.fRules.length()) {
            return -1;
        }
        int ch = UTF16.charAt(this.fRB.fRules, this.fNextIndex);
        this.fNextIndex = UTF16.moveCodePointOffset(this.fRB.fRules, this.fNextIndex, 1);
        if (ch == 13 || ch == 133 || ch == 8232 || (ch == 10 && this.fLastChar != 13)) {
            this.fLineNum++;
            this.fCharNum = 0;
            if (this.fQuoteMode) {
                error(66057);
                this.fQuoteMode = false;
            }
        } else if (ch != 10) {
            this.fCharNum++;
        }
        this.fLastChar = ch;
        return ch;
    }

    /* access modifiers changed from: package-private */
    public void nextChar(RBBIRuleChar c) {
        this.fScanIndex = this.fNextIndex;
        c.fChar = nextCharLL();
        c.fEscaped = false;
        if (c.fChar == 39) {
            if (UTF16.charAt(this.fRB.fRules, this.fNextIndex) == 39) {
                c.fChar = nextCharLL();
                c.fEscaped = true;
            } else {
                this.fQuoteMode = !this.fQuoteMode;
                if (this.fQuoteMode) {
                    c.fChar = 40;
                } else {
                    c.fChar = 41;
                }
                c.fEscaped = false;
                return;
            }
        }
        if (this.fQuoteMode) {
            c.fEscaped = true;
            return;
        }
        if (c.fChar == 35) {
            int commentStart = this.fScanIndex;
            do {
                c.fChar = nextCharLL();
                if (c.fChar == -1 || c.fChar == 13 || c.fChar == 10 || c.fChar == 133 || c.fChar == 8232) {
                }
                c.fChar = nextCharLL();
                break;
            } while (c.fChar == 8232);
            for (int i = commentStart; i < this.fNextIndex - 1; i++) {
                this.fRB.fStrippedRules.setCharAt(i, ' ');
            }
        }
        if (c.fChar != -1 && c.fChar == 92) {
            c.fEscaped = true;
            int[] unescapeIndex = {this.fNextIndex};
            c.fChar = Utility.unescapeAt(this.fRB.fRules, unescapeIndex);
            if (unescapeIndex[0] == this.fNextIndex) {
                error(66050);
            }
            this.fCharNum += unescapeIndex[0] - this.fNextIndex;
            this.fNextIndex = unescapeIndex[0];
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r0v3, types: [short] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parse() {
        /*
            r9 = this;
            r0 = 1
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r1 = r9.fC
            r9.nextChar(r1)
        L_0x0006:
            r1 = 1
            if (r0 != 0) goto L_0x000b
            goto L_0x011a
        L_0x000b:
            com.ibm.icu.text.RBBIRuleParseTable$RBBIRuleTableElement[] r2 = com.ibm.icu.text.RBBIRuleParseTable.gRuleParseStateTable
            r2 = r2[r0]
            com.ibm.icu.text.RBBIRuleBuilder r3 = r9.fRB
            java.lang.String r3 = r3.fDebugEnv
            if (r3 == 0) goto L_0x005a
            com.ibm.icu.text.RBBIRuleBuilder r3 = r9.fRB
            java.lang.String r3 = r3.fDebugEnv
            java.lang.String r4 = "scan"
            int r3 = r3.indexOf(r4)
            if (r3 < 0) goto L_0x005a
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "char, line, col = ('"
            r4.append(r5)
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r5 = r9.fC
            int r5 = r5.fChar
            char r5 = (char) r5
            r4.append(r5)
            java.lang.String r5 = "', "
            r4.append(r5)
            int r5 = r9.fLineNum
            r4.append(r5)
            java.lang.String r5 = ", "
            r4.append(r5)
            int r5 = r9.fCharNum
            r4.append(r5)
            java.lang.String r5 = "    state = "
            r4.append(r5)
            java.lang.String r5 = r2.fStateName
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.println(r4)
        L_0x005a:
            r3 = r2
            r2 = r0
        L_0x005c:
            com.ibm.icu.text.RBBIRuleParseTable$RBBIRuleTableElement[] r4 = com.ibm.icu.text.RBBIRuleParseTable.gRuleParseStateTable
            r3 = r4[r2]
            com.ibm.icu.text.RBBIRuleBuilder r4 = r9.fRB
            java.lang.String r4 = r4.fDebugEnv
            if (r4 == 0) goto L_0x0079
            com.ibm.icu.text.RBBIRuleBuilder r4 = r9.fRB
            java.lang.String r4 = r4.fDebugEnv
            java.lang.String r5 = "scan"
            int r4 = r4.indexOf(r5)
            if (r4 < 0) goto L_0x0079
            java.io.PrintStream r4 = java.lang.System.out
            java.lang.String r5 = "."
            r4.print(r5)
        L_0x0079:
            short r4 = r3.fCharClass
            r5 = 127(0x7f, float:1.78E-43)
            r6 = 255(0xff, float:3.57E-43)
            if (r4 >= r5) goto L_0x0091
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            boolean r4 = r4.fEscaped
            if (r4 != 0) goto L_0x0091
            short r4 = r3.fCharClass
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r5 = r9.fC
            int r5 = r5.fChar
            if (r4 != r5) goto L_0x0091
            goto L_0x00f8
        L_0x0091:
            short r4 = r3.fCharClass
            if (r4 != r6) goto L_0x0096
            goto L_0x00f8
        L_0x0096:
            short r4 = r3.fCharClass
            r5 = 254(0xfe, float:3.56E-43)
            if (r4 != r5) goto L_0x00a3
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            boolean r4 = r4.fEscaped
            if (r4 == 0) goto L_0x00a3
            goto L_0x00f8
        L_0x00a3:
            short r4 = r3.fCharClass
            r5 = 253(0xfd, float:3.55E-43)
            if (r4 != r5) goto L_0x00c0
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            boolean r4 = r4.fEscaped
            if (r4 == 0) goto L_0x00c0
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            int r4 = r4.fChar
            r5 = 80
            if (r4 == r5) goto L_0x00f8
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            int r4 = r4.fChar
            r5 = 112(0x70, float:1.57E-43)
            if (r4 != r5) goto L_0x00c0
            goto L_0x00f8
        L_0x00c0:
            short r4 = r3.fCharClass
            r5 = 252(0xfc, float:3.53E-43)
            r7 = -1
            if (r4 != r5) goto L_0x00ce
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            int r4 = r4.fChar
            if (r4 != r7) goto L_0x00ce
            goto L_0x00f8
        L_0x00ce:
            short r4 = r3.fCharClass
            r5 = 128(0x80, float:1.794E-43)
            if (r4 < r5) goto L_0x0205
            short r4 = r3.fCharClass
            r8 = 240(0xf0, float:3.36E-43)
            if (r4 >= r8) goto L_0x0205
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            boolean r4 = r4.fEscaped
            if (r4 != 0) goto L_0x0205
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r4 = r9.fC
            int r4 = r4.fChar
            if (r4 == r7) goto L_0x0205
            com.ibm.icu.text.UnicodeSet[] r4 = r9.fRuleSets
            short r7 = r3.fCharClass
            int r7 = r7 - r5
            r4 = r4[r7]
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r5 = r9.fC
            int r5 = r5.fChar
            boolean r5 = r4.contains((int) r5)
            if (r5 == 0) goto L_0x0205
        L_0x00f8:
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            java.lang.String r2 = r2.fDebugEnv
            if (r2 == 0) goto L_0x0111
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            java.lang.String r2 = r2.fDebugEnv
            java.lang.String r4 = "scan"
            int r2 = r2.indexOf(r4)
            if (r2 < 0) goto L_0x0111
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r4 = ""
            r2.println(r4)
        L_0x0111:
            short r2 = r3.fAction
            boolean r2 = r9.doParseActions(r2)
            if (r2 != 0) goto L_0x01b5
        L_0x011a:
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r3 = 0
            r2 = r2[r3]
            if (r2 != 0) goto L_0x0129
            r2 = 66052(0x10204, float:9.2559E-41)
            r9.error(r2)
        L_0x0129:
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            java.lang.String r2 = r2.fDebugEnv
            if (r2 == 0) goto L_0x0140
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            java.lang.String r2 = r2.fDebugEnv
            java.lang.String r4 = "symbols"
            int r2 = r2.indexOf(r4)
            if (r2 < 0) goto L_0x0140
            com.ibm.icu.text.RBBISymbolTable r2 = r9.fSymbolTable
            r2.rbbiSymtablePrint()
        L_0x0140:
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            java.lang.String r2 = r2.fDebugEnv
            if (r2 == 0) goto L_0x01b4
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            java.lang.String r2 = r2.fDebugEnv
            java.lang.String r4 = "ptree"
            int r2 = r2.indexOf(r4)
            if (r2 < 0) goto L_0x01b4
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r4 = "Completed Forward Rules Parse Tree..."
            r2.println(r4)
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r2 = r2[r3]
            r2.printTree(r1)
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r3 = "\nCompleted Reverse Rules Parse Tree..."
            r2.println(r3)
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r2 = r2[r1]
            r2.printTree(r1)
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r3 = "\nCompleted Safe Point Forward Rules Parse Tree..."
            r2.println(r3)
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r3 = 2
            r2 = r2[r3]
            if (r2 != 0) goto L_0x018a
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r3 = "  -- null -- "
            r2.println(r3)
            goto L_0x0193
        L_0x018a:
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r2 = r2[r3]
            r2.printTree(r1)
        L_0x0193:
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r3 = "\nCompleted Safe Point Reverse Rules Parse Tree..."
            r2.println(r3)
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r3 = 3
            r2 = r2[r3]
            if (r2 != 0) goto L_0x01ab
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r2 = "  -- null -- "
            r1.println(r2)
            goto L_0x01b4
        L_0x01ab:
            com.ibm.icu.text.RBBIRuleBuilder r2 = r9.fRB
            com.ibm.icu.text.RBBINode[] r2 = r2.fTreeRoots
            r2 = r2[r3]
            r2.printTree(r1)
        L_0x01b4:
            return
        L_0x01b5:
            short r2 = r3.fPushState
            r4 = 66049(0x10201, float:9.2554E-41)
            if (r2 == 0) goto L_0x01d9
            int r2 = r9.fStackPtr
            int r2 = r2 + r1
            r9.fStackPtr = r2
            int r2 = r9.fStackPtr
            r5 = 100
            if (r2 < r5) goto L_0x01d1
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.String r5 = "RBBIRuleScanner.parse() - state stack overflow."
            r2.println(r5)
            r9.error(r4)
        L_0x01d1:
            short[] r2 = r9.fStack
            int r5 = r9.fStackPtr
            short r7 = r3.fPushState
            r2[r5] = r7
        L_0x01d9:
            boolean r2 = r3.fNextChar
            if (r2 == 0) goto L_0x01e2
            com.ibm.icu.text.RBBIRuleScanner$RBBIRuleChar r2 = r9.fC
            r9.nextChar(r2)
        L_0x01e2:
            short r2 = r3.fNextState
            if (r2 == r6) goto L_0x01ea
            short r0 = r3.fNextState
            goto L_0x0006
        L_0x01ea:
            short[] r2 = r9.fStack
            int r5 = r9.fStackPtr
            short r0 = r2[r5]
            int r2 = r9.fStackPtr
            int r2 = r2 - r1
            r9.fStackPtr = r2
            int r1 = r9.fStackPtr
            if (r1 >= 0) goto L_0x0006
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r2 = "RBBIRuleScanner.parse() - state stack underflow."
            r1.println(r2)
            r9.error(r4)
            goto L_0x0006
        L_0x0205:
            int r2 = r2 + 1
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RBBIRuleScanner.parse():void");
    }

    /* access modifiers changed from: package-private */
    public void printNodeStack(String title) {
        System.out.println(title + ".  Dumping node stack...\n");
        for (int i = this.fNodeStackPtr; i > 0; i--) {
            this.fNodeStack[i].printTree(true);
        }
    }

    /* access modifiers changed from: package-private */
    public RBBINode pushNewNode(int nodeType) {
        this.fNodeStackPtr++;
        if (this.fNodeStackPtr >= 100) {
            System.out.println("RBBIRuleScanner.pushNewNode - stack overflow.");
            error(66049);
        }
        this.fNodeStack[this.fNodeStackPtr] = new RBBINode(nodeType);
        return this.fNodeStack[this.fNodeStackPtr];
    }

    /* access modifiers changed from: package-private */
    public void scanSet() {
        UnicodeSet uset = null;
        ParsePosition pos = new ParsePosition(this.fScanIndex);
        int startPos = this.fScanIndex;
        try {
            uset = new UnicodeSet(this.fRB.fRules, pos, this.fSymbolTable, 1);
        } catch (Exception e) {
            error(66063);
        }
        if (uset.isEmpty()) {
            error(66060);
        }
        int i = pos.getIndex();
        while (this.fNextIndex < i) {
            nextCharLL();
        }
        RBBINode n = pushNewNode(0);
        n.fFirstPos = startPos;
        n.fLastPos = this.fNextIndex;
        n.fText = this.fRB.fRules.substring(n.fFirstPos, n.fLastPos);
        findSetFor(n.fText, n, uset);
    }
}
