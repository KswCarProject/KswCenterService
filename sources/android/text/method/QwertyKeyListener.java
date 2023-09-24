package android.text.method;

import android.text.AutoText;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;

/* loaded from: classes4.dex */
public class QwertyKeyListener extends BaseKeyListener {
    private static QwertyKeyListener sFullKeyboardInstance;
    private TextKeyListener.Capitalize mAutoCap;
    private boolean mAutoText;
    private boolean mFullKeyboard;
    private static QwertyKeyListener[] sInstance = new QwertyKeyListener[TextKeyListener.Capitalize.values().length * 2];
    private static SparseArray<String> PICKER_SETS = new SparseArray<>();

    static {
        PICKER_SETS.put(65, "\u00c0\u00c1\u00c2\u00c4\u00c6\u00c3\u00c5\u0104\u0100");
        PICKER_SETS.put(67, "\u00c7\u0106\u010c");
        PICKER_SETS.put(68, "\u010e");
        PICKER_SETS.put(69, "\u00c8\u00c9\u00ca\u00cb\u0118\u011a\u0112");
        PICKER_SETS.put(71, "\u011e");
        PICKER_SETS.put(76, "\u0141");
        PICKER_SETS.put(73, "\u00cc\u00cd\u00ce\u00cf\u012a\u0130");
        PICKER_SETS.put(78, "\u00d1\u0143\u0147");
        PICKER_SETS.put(79, "\u00d8\u0152\u00d5\u00d2\u00d3\u00d4\u00d6\u014c");
        PICKER_SETS.put(82, "\u0158");
        PICKER_SETS.put(83, "\u015a\u0160\u015e");
        PICKER_SETS.put(84, "\u0164");
        PICKER_SETS.put(85, "\u00d9\u00da\u00db\u00dc\u016e\u016a");
        PICKER_SETS.put(89, "\u00dd\u0178");
        PICKER_SETS.put(90, "\u0179\u017b\u017d");
        PICKER_SETS.put(97, "\u00e0\u00e1\u00e2\u00e4\u00e6\u00e3\u00e5\u0105\u0101");
        PICKER_SETS.put(99, "\u00e7\u0107\u010d");
        PICKER_SETS.put(100, "\u010f");
        PICKER_SETS.put(101, "\u00e8\u00e9\u00ea\u00eb\u0119\u011b\u0113");
        PICKER_SETS.put(103, "\u011f");
        PICKER_SETS.put(105, "\u00ec\u00ed\u00ee\u00ef\u012b\u0131");
        PICKER_SETS.put(108, "\u0142");
        PICKER_SETS.put(110, "\u00f1\u0144\u0148");
        PICKER_SETS.put(111, "\u00f8\u0153\u00f5\u00f2\u00f3\u00f4\u00f6\u014d");
        PICKER_SETS.put(114, "\u0159");
        PICKER_SETS.put(115, "\u00a7\u00df\u015b\u0161\u015f");
        PICKER_SETS.put(116, "\u0165");
        PICKER_SETS.put(117, "\u00f9\u00fa\u00fb\u00fc\u016f\u016b");
        PICKER_SETS.put(121, "\u00fd\u00ff");
        PICKER_SETS.put(122, "\u017a\u017c\u017e");
        PICKER_SETS.put(61185, "\u2026\u00a5\u2022\u00ae\u00a9\u00b1[]{}\\|");
        PICKER_SETS.put(47, "\\");
        PICKER_SETS.put(49, "\u00b9\u00bd\u2153\u00bc\u215b");
        PICKER_SETS.put(50, "\u00b2\u2154");
        PICKER_SETS.put(51, "\u00b3\u00be\u215c");
        PICKER_SETS.put(52, "\u2074");
        PICKER_SETS.put(53, "\u215d");
        PICKER_SETS.put(55, "\u215e");
        PICKER_SETS.put(48, "\u207f\u2205");
        PICKER_SETS.put(36, "\u00a2\u00a3\u20ac\u00a5\u20a3\u20a4\u20b1");
        PICKER_SETS.put(37, "\u2030");
        PICKER_SETS.put(42, "\u2020\u2021");
        PICKER_SETS.put(45, "\u2013\u2014");
        PICKER_SETS.put(43, "\u00b1");
        PICKER_SETS.put(40, "[{<");
        PICKER_SETS.put(41, "]}>");
        PICKER_SETS.put(33, "\u00a1");
        PICKER_SETS.put(34, "\u201c\u201d\u00ab\u00bb\u02dd");
        PICKER_SETS.put(63, "\u00bf");
        PICKER_SETS.put(44, "\u201a\u201e");
        PICKER_SETS.put(61, "\u2260\u2248\u221e");
        PICKER_SETS.put(60, "\u2264\u00ab\u2039");
        PICKER_SETS.put(62, "\u2265\u00bb\u203a");
    }

    private QwertyKeyListener(TextKeyListener.Capitalize cap, boolean autoText, boolean fullKeyboard) {
        this.mAutoCap = cap;
        this.mAutoText = autoText;
        this.mFullKeyboard = fullKeyboard;
    }

    public QwertyKeyListener(TextKeyListener.Capitalize cap, boolean autoText) {
        this(cap, autoText, false);
    }

    public static QwertyKeyListener getInstance(boolean autoText, TextKeyListener.Capitalize cap) {
        int off = (cap.ordinal() * 2) + (autoText ? 1 : 0);
        if (sInstance[off] == null) {
            sInstance[off] = new QwertyKeyListener(cap, autoText);
        }
        return sInstance[off];
    }

    public static QwertyKeyListener getInstanceForFullKeyboard() {
        if (sFullKeyboardInstance == null) {
            sFullKeyboardInstance = new QwertyKeyListener(TextKeyListener.Capitalize.NONE, false, true);
        }
        return sFullKeyboardInstance;
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    /* JADX WARN: Code restructure failed: missing block: B:162:0x0285, code lost:
        if (r27.hasModifiers(2) != false) goto L160;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a3  */
    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onKeyDown(View view, Editable content, int keyCode, KeyEvent event) {
        int i;
        int activeEnd;
        int i2;
        int selEnd;
        int start;
        int count;
        int pref = view != null ? TextKeyListener.getInstance().getPrefs(view.getContext()) : 0;
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        int selStart = Math.min(a, b);
        int selEnd2 = Math.max(a, b);
        if (selStart < 0 || selEnd2 < 0) {
            selEnd2 = 0;
            selStart = 0;
            Selection.setSelection(content, 0, 0);
        }
        int selStart2 = selStart;
        int selEnd3 = selEnd2;
        int activeStart = content.getSpanStart(TextKeyListener.ACTIVE);
        int activeEnd2 = content.getSpanEnd(TextKeyListener.ACTIVE);
        int i3 = event.getUnicodeChar(getMetaState(content, event));
        if (!this.mFullKeyboard && (count = event.getRepeatCount()) > 0 && selStart2 == selEnd3 && selStart2 > 0) {
            char c = content.charAt(selStart2 - 1);
            if (c == i3 || c == Character.toUpperCase(i3)) {
                if (view != null) {
                    i = i3;
                    activeEnd = activeEnd2;
                    if (showCharacterPicker(view, content, c, false, count)) {
                        resetMetaState(content);
                        return true;
                    }
                }
            } else {
                i = i3;
                activeEnd = activeEnd2;
            }
            if (i != 61185) {
                if (view != null) {
                    showCharacterPicker(view, content, KeyCharacterMap.PICKER_DIALOG_INPUT, true, 1);
                }
                resetMetaState(content);
                return true;
            }
            if (i == 61184) {
                if (selStart2 == selEnd3) {
                    start = selEnd3;
                    while (start > 0 && selEnd3 - start < 4 && Character.digit(content.charAt(start - 1), 16) >= 0) {
                        start--;
                    }
                } else {
                    start = selStart2;
                }
                int ch = -1;
                try {
                    String hex = TextUtils.substring(content, start, selEnd3);
                    ch = Integer.parseInt(hex, 16);
                } catch (NumberFormatException e) {
                }
                if (ch >= 0) {
                    selStart2 = start;
                    Selection.setSelection(content, selStart2, selEnd3);
                    i2 = ch;
                } else {
                    i2 = 0;
                }
            } else {
                i2 = i;
            }
            if (i2 != 0) {
                boolean dead = false;
                if ((Integer.MIN_VALUE & i2) != 0) {
                    dead = true;
                    i2 &= Integer.MAX_VALUE;
                }
                if (activeStart == selStart2 && activeEnd == selEnd3) {
                    boolean replace = false;
                    if ((selEnd3 - selStart2) - 1 == 0) {
                        char accent = content.charAt(selStart2);
                        int composed = KeyEvent.getDeadChar(accent, i2);
                        if (composed != 0) {
                            i2 = composed;
                            replace = true;
                            dead = false;
                        }
                    }
                    if (!replace) {
                        Selection.setSelection(content, selEnd3);
                        content.removeSpan(TextKeyListener.ACTIVE);
                        selStart2 = selEnd3;
                    }
                }
                if ((pref & 1) != 0 && Character.isLowerCase(i2) && TextKeyListener.shouldCap(this.mAutoCap, content, selStart2)) {
                    int where = content.getSpanEnd(TextKeyListener.CAPPED);
                    int flags = content.getSpanFlags(TextKeyListener.CAPPED);
                    if (where == selStart2 && ((flags >> 16) & 65535) == i2) {
                        content.removeSpan(TextKeyListener.CAPPED);
                    } else {
                        int flags2 = i2 << 16;
                        i2 = Character.toUpperCase(i2);
                        if (selStart2 == 0) {
                            content.setSpan(TextKeyListener.CAPPED, 0, 0, flags2 | 17);
                        } else {
                            content.setSpan(TextKeyListener.CAPPED, selStart2 - 1, selStart2, flags2 | 33);
                        }
                    }
                }
                if (selStart2 != selEnd3) {
                    Selection.setSelection(content, selEnd3);
                }
                content.setSpan(OLD_SEL_START, selStart2, selStart2, 17);
                content.replace(selStart2, selEnd3, String.valueOf((char) i2));
                int oldStart = content.getSpanStart(OLD_SEL_START);
                int selEnd4 = Selection.getSelectionEnd(content);
                if (oldStart < selEnd4) {
                    content.setSpan(TextKeyListener.LAST_TYPED, oldStart, selEnd4, 33);
                    if (dead) {
                        Selection.setSelection(content, oldStart, selEnd4);
                        content.setSpan(TextKeyListener.ACTIVE, oldStart, selEnd4, 33);
                    }
                }
                adjustMetaAfterKeypress(content);
                if ((pref & 2) != 0 && this.mAutoText) {
                    if (i2 == 32 || i2 == 9 || i2 == 10 || i2 == 44 || i2 == 46 || i2 == 33 || i2 == 63 || i2 == 34 || Character.getType(i2) == 22) {
                        if (content.getSpanEnd(TextKeyListener.INHIBIT_REPLACEMENT) != oldStart) {
                            int x = oldStart;
                            while (x > 0) {
                                char c2 = content.charAt(x - 1);
                                if (c2 != '\'' && !Character.isLetter(c2)) {
                                    break;
                                }
                                x--;
                            }
                            String rep = getReplacement(content, x, oldStart, view);
                            if (rep != null) {
                                for (Replaced replaced : (Replaced[]) content.getSpans(0, content.length(), Replaced.class)) {
                                    content.removeSpan(replaced);
                                }
                                char[] orig = new char[oldStart - x];
                                TextUtils.getChars(content, x, oldStart, orig, 0);
                                selEnd = selEnd4;
                                content.setSpan(new Replaced(orig), x, oldStart, 33);
                                content.replace(x, oldStart, rep);
                            }
                        }
                    } else {
                        selEnd = selEnd4;
                    }
                    if ((pref & 4) == 0 && this.mAutoText) {
                        int selEnd5 = Selection.getSelectionEnd(content);
                        if (selEnd5 - 3 >= 0 && content.charAt(selEnd5 - 1) == ' ' && content.charAt(selEnd5 - 2) == ' ') {
                            char c3 = content.charAt(selEnd5 - 3);
                            for (int j = selEnd5 - 3; j > 0 && (c3 == '\"' || Character.getType(c3) == 22); j--) {
                                c3 = content.charAt(j - 1);
                            }
                            if (Character.isLetter(c3) || Character.isDigit(c3)) {
                                content.replace(selEnd5 - 2, selEnd5 - 1, ".");
                            }
                        }
                    }
                    return true;
                }
                selEnd = selEnd4;
                if ((pref & 4) == 0) {
                }
                return true;
            }
            if (keyCode == 67) {
                if (!event.hasNoModifiers()) {
                }
                if (selStart2 == selEnd3) {
                    int consider = 1;
                    if (content.getSpanEnd(TextKeyListener.LAST_TYPED) == selStart2 && content.charAt(selStart2 - 1) != '\n') {
                        consider = 2;
                    }
                    Replaced[] repl = (Replaced[]) content.getSpans(selStart2 - consider, selStart2, Replaced.class);
                    if (repl.length > 0) {
                        int st = content.getSpanStart(repl[0]);
                        int en = content.getSpanEnd(repl[0]);
                        String old = new String(repl[0].mText);
                        content.removeSpan(repl[0]);
                        if (selStart2 >= en) {
                            content.setSpan(TextKeyListener.INHIBIT_REPLACEMENT, en, en, 34);
                            content.replace(st, en, old);
                            int en2 = content.getSpanStart(TextKeyListener.INHIBIT_REPLACEMENT);
                            if (en2 - 1 >= 0) {
                                content.setSpan(TextKeyListener.INHIBIT_REPLACEMENT, en2 - 1, en2, 33);
                            } else {
                                content.removeSpan(TextKeyListener.INHIBIT_REPLACEMENT);
                            }
                            adjustMetaAfterKeypress(content);
                            return true;
                        }
                        adjustMetaAfterKeypress(content);
                        return super.onKeyDown(view, content, keyCode, event);
                    }
                }
            }
            return super.onKeyDown(view, content, keyCode, event);
        }
        i = i3;
        activeEnd = activeEnd2;
        if (i != 61185) {
        }
    }

    private String getReplacement(CharSequence src, int start, int end, View view) {
        String out;
        int len = end - start;
        boolean changecase = false;
        String replacement = AutoText.get(src, start, end, view);
        if (replacement == null) {
            String key = TextUtils.substring(src, start, end).toLowerCase();
            replacement = AutoText.get(key, 0, end - start, view);
            changecase = true;
            if (replacement == null) {
                return null;
            }
        }
        int j = 0;
        if (changecase) {
            int caps = 0;
            for (int caps2 = start; caps2 < end; caps2++) {
                if (Character.isUpperCase(src.charAt(caps2))) {
                    caps++;
                }
            }
            j = caps;
        }
        if (j == 0) {
            out = replacement;
        } else if (j == 1) {
            out = toTitleCase(replacement);
        } else if (j == len) {
            out = replacement.toUpperCase();
        } else {
            out = toTitleCase(replacement);
        }
        if (out.length() == len && TextUtils.regionMatches(src, start, out, 0, len)) {
            return null;
        }
        return out;
    }

    public static void markAsReplaced(Spannable content, int start, int end, String original) {
        Replaced[] repl = (Replaced[]) content.getSpans(0, content.length(), Replaced.class);
        for (Replaced replaced : repl) {
            content.removeSpan(replaced);
        }
        int len = original.length();
        char[] orig = new char[len];
        original.getChars(0, len, orig, 0);
        content.setSpan(new Replaced(orig), start, end, 33);
    }

    private boolean showCharacterPicker(View view, Editable content, char c, boolean insert, int count) {
        String set = PICKER_SETS.get(c);
        if (set == null) {
            return false;
        }
        if (count == 1) {
            new CharacterPickerDialog(view.getContext(), view, content, set, insert).show();
        }
        return true;
    }

    private static String toTitleCase(String src) {
        return Character.toUpperCase(src.charAt(0)) + src.substring(1);
    }

    /* loaded from: classes4.dex */
    static class Replaced implements NoCopySpan {
        private char[] mText;

        public Replaced(char[] text) {
            this.mText = text;
        }
    }
}
