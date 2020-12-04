package android.text.method;

import android.graphics.Paint;
import android.icu.lang.UCharacter;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.TextKeyListener;
import android.text.style.ReplacementSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.android.internal.annotations.GuardedBy;

public abstract class BaseKeyListener extends MetaKeyKeyListener implements KeyListener {
    private static final int CARRIAGE_RETURN = 13;
    private static final int LINE_FEED = 10;
    static final Object OLD_SEL_START = new NoCopySpan.Concrete();
    @GuardedBy({"mLock"})
    static Paint sCachedPaint = null;
    private final Object mLock = new Object();

    public boolean backspace(View view, Editable content, int keyCode, KeyEvent event) {
        return backspaceOrForwardDelete(view, content, keyCode, event, false);
    }

    public boolean forwardDelete(View view, Editable content, int keyCode, KeyEvent event) {
        return backspaceOrForwardDelete(view, content, keyCode, event, true);
    }

    private static boolean isVariationSelector(int codepoint) {
        return UCharacter.hasBinaryProperty(codepoint, 36);
    }

    private static int adjustReplacementSpan(CharSequence text, int offset, boolean moveToStart) {
        if (!(text instanceof Spanned)) {
            return offset;
        }
        ReplacementSpan[] spans = (ReplacementSpan[]) ((Spanned) text).getSpans(offset, offset, ReplacementSpan.class);
        for (int i = 0; i < spans.length; i++) {
            int start = ((Spanned) text).getSpanStart(spans[i]);
            int end = ((Spanned) text).getSpanEnd(spans[i]);
            if (start < offset && end > offset) {
                offset = moveToStart ? start : end;
            }
        }
        return offset;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0164, code lost:
        r19 = r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getOffsetForBackspaceKey(java.lang.CharSequence r26, int r27) {
        /*
            r0 = r26
            r1 = r27
            r2 = 1
            if (r1 > r2) goto L_0x0009
            r2 = 0
            return r2
        L_0x0009:
            r3 = 0
            r4 = 1
            r5 = 2
            r6 = 3
            r7 = 4
            r8 = 5
            r9 = 6
            r10 = 7
            r11 = 8
            r12 = 9
            r13 = 10
            r14 = 11
            r15 = 12
            r16 = 13
            r17 = 0
            r18 = 0
            r19 = 0
            r2 = r19
            r19 = r18
            r18 = r17
            r17 = r1
        L_0x002b:
            r21 = r17
            r22 = r3
            r23 = r4
            r3 = r21
            int r4 = java.lang.Character.codePointBefore(r0, r3)
            int r17 = java.lang.Character.charCount(r4)
            int r17 = r3 - r17
            switch(r2) {
                case 0: goto L_0x017f;
                case 1: goto L_0x0176;
                case 2: goto L_0x0158;
                case 3: goto L_0x0146;
                case 4: goto L_0x012a;
                case 5: goto L_0x0118;
                case 6: goto L_0x00f3;
                case 7: goto L_0x00e7;
                case 8: goto L_0x00b6;
                case 9: goto L_0x009d;
                case 10: goto L_0x008d;
                case 11: goto L_0x007d;
                case 12: goto L_0x005d;
                default: goto L_0x0040;
            }
        L_0x0040:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "state "
            r0.append(r1)
            r0.append(r2)
            java.lang.String r1 = " is unknown"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r3.<init>(r0)
            throw r3
        L_0x005d:
            boolean r21 = android.text.Emoji.isTagSpecChar(r4)
            if (r21 == 0) goto L_0x0067
            int r18 = r18 + 2
            goto L_0x01be
        L_0x0067:
            boolean r21 = android.text.Emoji.isEmoji(r4)
            if (r21 == 0) goto L_0x0077
            int r21 = java.lang.Character.charCount(r4)
            int r18 = r18 + r21
            r2 = 13
            goto L_0x01be
        L_0x0077:
            r18 = 2
            r2 = 13
            goto L_0x01be
        L_0x007d:
            boolean r21 = android.text.Emoji.isRegionalIndicatorSymbol(r4)
            if (r21 == 0) goto L_0x0089
            int r18 = r18 + -2
            r2 = 10
            goto L_0x01be
        L_0x0089:
            r2 = 13
            goto L_0x01be
        L_0x008d:
            boolean r21 = android.text.Emoji.isRegionalIndicatorSymbol(r4)
            if (r21 == 0) goto L_0x0099
            int r18 = r18 + 2
            r2 = 11
            goto L_0x01be
        L_0x0099:
            r2 = 13
            goto L_0x01be
        L_0x009d:
            boolean r21 = android.text.Emoji.isEmoji(r4)
            if (r21 == 0) goto L_0x00b2
            int r21 = r19 + 1
            int r24 = java.lang.Character.charCount(r4)
            int r21 = r21 + r24
            int r18 = r18 + r21
            r19 = 0
            r2 = 7
            goto L_0x01be
        L_0x00b2:
            r2 = 13
            goto L_0x01be
        L_0x00b6:
            boolean r21 = android.text.Emoji.isEmoji(r4)
            if (r21 == 0) goto L_0x00d5
            int r21 = java.lang.Character.charCount(r4)
            r20 = 1
            int r21 = r21 + 1
            int r18 = r18 + r21
            boolean r21 = android.text.Emoji.isEmojiModifier(r4)
            if (r21 == 0) goto L_0x00cf
            r21 = 4
            goto L_0x00d1
        L_0x00cf:
            r21 = 7
        L_0x00d1:
            r2 = r21
            goto L_0x01be
        L_0x00d5:
            boolean r21 = isVariationSelector(r4)
            if (r21 == 0) goto L_0x00e3
            int r19 = java.lang.Character.charCount(r4)
            r2 = 9
            goto L_0x01be
        L_0x00e3:
            r2 = 13
            goto L_0x01be
        L_0x00e7:
            int r3 = android.text.Emoji.ZERO_WIDTH_JOINER
            if (r4 != r3) goto L_0x00ef
            r2 = 8
            goto L_0x01be
        L_0x00ef:
            r2 = 13
            goto L_0x01be
        L_0x00f3:
            boolean r3 = android.text.Emoji.isEmoji(r4)
            if (r3 == 0) goto L_0x0102
            int r3 = java.lang.Character.charCount(r4)
            int r18 = r18 + r3
            r2 = 7
            goto L_0x01be
        L_0x0102:
            boolean r3 = isVariationSelector(r4)
            if (r3 != 0) goto L_0x0114
            int r3 = android.icu.lang.UCharacter.getCombiningClass(r4)
            if (r3 != 0) goto L_0x0114
            int r3 = java.lang.Character.charCount(r4)
            int r18 = r18 + r3
        L_0x0114:
            r2 = 13
            goto L_0x01be
        L_0x0118:
            boolean r3 = android.text.Emoji.isEmojiModifierBase(r4)
            if (r3 == 0) goto L_0x0126
            int r3 = java.lang.Character.charCount(r4)
            int r3 = r19 + r3
            int r18 = r18 + r3
        L_0x0126:
            r2 = 13
            goto L_0x01be
        L_0x012a:
            boolean r3 = isVariationSelector(r4)
            if (r3 == 0) goto L_0x0136
            int r3 = java.lang.Character.charCount(r4)
            r2 = 5
            goto L_0x0164
        L_0x0136:
            boolean r3 = android.text.Emoji.isEmojiModifierBase(r4)
            if (r3 == 0) goto L_0x0142
            int r3 = java.lang.Character.charCount(r4)
            int r18 = r18 + r3
        L_0x0142:
            r2 = 13
            goto L_0x01be
        L_0x0146:
            boolean r3 = android.text.Emoji.isKeycapBase(r4)
            if (r3 == 0) goto L_0x0154
            int r3 = java.lang.Character.charCount(r4)
            int r3 = r19 + r3
            int r18 = r18 + r3
        L_0x0154:
            r2 = 13
            goto L_0x01be
        L_0x0158:
            boolean r3 = isVariationSelector(r4)
            if (r3 == 0) goto L_0x0167
            int r3 = java.lang.Character.charCount(r4)
            r2 = 3
        L_0x0164:
            r19 = r3
            goto L_0x01be
        L_0x0167:
            boolean r3 = android.text.Emoji.isKeycapBase(r4)
            if (r3 == 0) goto L_0x0173
            int r3 = java.lang.Character.charCount(r4)
            int r18 = r18 + r3
        L_0x0173:
            r2 = 13
            goto L_0x01be
        L_0x0176:
            r3 = 13
            if (r4 != r3) goto L_0x017c
            int r18 = r18 + 1
        L_0x017c:
            r2 = 13
            goto L_0x01be
        L_0x017f:
            int r3 = java.lang.Character.charCount(r4)
            r25 = r3
            r3 = 10
            if (r4 != r3) goto L_0x018d
            r2 = 1
        L_0x018a:
            r18 = r25
            goto L_0x01be
        L_0x018d:
            boolean r3 = isVariationSelector(r4)
            if (r3 == 0) goto L_0x0195
            r2 = 6
            goto L_0x018a
        L_0x0195:
            boolean r3 = android.text.Emoji.isRegionalIndicatorSymbol(r4)
            if (r3 == 0) goto L_0x019e
            r2 = 10
            goto L_0x018a
        L_0x019e:
            boolean r3 = android.text.Emoji.isEmojiModifier(r4)
            if (r3 == 0) goto L_0x01a6
            r2 = 4
            goto L_0x018a
        L_0x01a6:
            int r3 = android.text.Emoji.COMBINING_ENCLOSING_KEYCAP
            if (r4 != r3) goto L_0x01ac
            r2 = 2
            goto L_0x018a
        L_0x01ac:
            boolean r3 = android.text.Emoji.isEmoji(r4)
            if (r3 == 0) goto L_0x01b4
            r2 = 7
            goto L_0x018a
        L_0x01b4:
            int r3 = android.text.Emoji.CANCEL_TAG
            if (r4 != r3) goto L_0x01bb
            r2 = 12
            goto L_0x018a
        L_0x01bb:
            r2 = 13
            goto L_0x018a
        L_0x01be:
            if (r17 <= 0) goto L_0x01cb
            r3 = 13
            if (r2 != r3) goto L_0x01c5
            goto L_0x01cb
        L_0x01c5:
            r3 = r22
            r4 = r23
            goto L_0x002b
        L_0x01cb:
            int r3 = r1 - r18
            r4 = 1
            int r3 = adjustReplacementSpan(r0, r3, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.BaseKeyListener.getOffsetForBackspaceKey(java.lang.CharSequence, int):int");
    }

    private static int getOffsetForForwardDeleteKey(CharSequence text, int offset, Paint paint) {
        int len = text.length();
        if (offset >= len - 1) {
            return len;
        }
        return adjustReplacementSpan(text, paint.getTextRunCursor(text, offset, len, false, offset, 0), false);
    }

    private boolean backspaceOrForwardDelete(View view, Editable content, int keyCode, KeyEvent event, boolean isForwardDelete) {
        int end;
        Paint paint;
        Paint paint2;
        if (!KeyEvent.metaStateHasNoModifiers(event.getMetaState() & -28916)) {
            return false;
        }
        if (deleteSelection(view, content)) {
            return true;
        }
        boolean isCtrlActive = (event.getMetaState() & 4096) != 0;
        boolean isShiftActive = getMetaState(content, 1, event) == 1;
        boolean isAltActive = getMetaState(content, 2, event) == 1;
        if (isCtrlActive) {
            if (isAltActive || isShiftActive) {
                return false;
            }
            return deleteUntilWordBoundary(view, content, isForwardDelete);
        } else if (isAltActive && deleteLine(view, content)) {
            return true;
        } else {
            int start = Selection.getSelectionEnd(content);
            if (isForwardDelete) {
                if (view instanceof TextView) {
                    paint = ((TextView) view).getPaint();
                } else {
                    synchronized (this.mLock) {
                        if (sCachedPaint == null) {
                            sCachedPaint = new Paint();
                        }
                        paint2 = sCachedPaint;
                    }
                    paint = paint2;
                }
                end = getOffsetForForwardDeleteKey(content, start, paint);
            } else {
                end = getOffsetForBackspaceKey(content, start);
            }
            if (start == end) {
                return false;
            }
            content.delete(Math.min(start, end), Math.max(start, end));
            return true;
        }
    }

    private boolean deleteUntilWordBoundary(View view, Editable content, boolean isForwardDelete) {
        int deleteTo;
        int deleteFrom;
        int currentCursorOffset = Selection.getSelectionStart(content);
        if (currentCursorOffset != Selection.getSelectionEnd(content)) {
            return false;
        }
        if ((!isForwardDelete && currentCursorOffset == 0) || (isForwardDelete && currentCursorOffset == content.length())) {
            return false;
        }
        WordIterator wordIterator = null;
        if (view instanceof TextView) {
            wordIterator = ((TextView) view).getWordIterator();
        }
        if (wordIterator == null) {
            wordIterator = new WordIterator();
        }
        if (isForwardDelete) {
            deleteFrom = currentCursorOffset;
            wordIterator.setCharSequence(content, deleteFrom, content.length());
            deleteTo = wordIterator.following(currentCursorOffset);
            if (deleteTo == -1) {
                deleteTo = content.length();
            }
        } else {
            deleteTo = currentCursorOffset;
            wordIterator.setCharSequence(content, 0, deleteTo);
            deleteFrom = wordIterator.preceding(currentCursorOffset);
            if (deleteFrom == -1) {
                deleteFrom = 0;
            }
        }
        content.delete(deleteFrom, deleteTo);
        return true;
    }

    private boolean deleteSelection(View view, Editable content) {
        int selectionStart = Selection.getSelectionStart(content);
        int selectionEnd = Selection.getSelectionEnd(content);
        if (selectionEnd < selectionStart) {
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }
        if (selectionStart == selectionEnd) {
            return false;
        }
        content.delete(selectionStart, selectionEnd);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000d, code lost:
        r1 = r0.getLineForOffset(android.text.Selection.getSelectionStart(r7));
        r2 = r0.getLineStart(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean deleteLine(android.view.View r6, android.text.Editable r7) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof android.widget.TextView
            if (r0 == 0) goto L_0x0024
            r0 = r6
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.text.Layout r0 = r0.getLayout()
            if (r0 == 0) goto L_0x0024
            int r1 = android.text.Selection.getSelectionStart(r7)
            int r1 = r0.getLineForOffset(r1)
            int r2 = r0.getLineStart(r1)
            int r3 = r0.getLineEnd(r1)
            if (r3 == r2) goto L_0x0024
            r7.delete(r2, r3)
            r4 = 1
            return r4
        L_0x0024:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.BaseKeyListener.deleteLine(android.view.View, android.text.Editable):boolean");
    }

    static int makeTextContentType(TextKeyListener.Capitalize caps, boolean autoText) {
        int contentType = 1;
        switch (caps) {
            case CHARACTERS:
                contentType = 1 | 4096;
                break;
            case WORDS:
                contentType = 1 | 8192;
                break;
            case SENTENCES:
                contentType = 1 | 16384;
                break;
        }
        if (autoText) {
            return contentType | 32768;
        }
        return contentType;
    }

    public boolean onKeyDown(View view, Editable content, int keyCode, KeyEvent event) {
        boolean handled;
        if (keyCode == 67) {
            handled = backspace(view, content, keyCode, event);
        } else if (keyCode != 112) {
            handled = false;
        } else {
            handled = forwardDelete(view, content, keyCode, event);
        }
        if (!handled) {
            return super.onKeyDown(view, content, keyCode, event);
        }
        adjustMetaAfterKeypress((Spannable) content);
        return true;
    }

    public boolean onKeyOther(View view, Editable content, KeyEvent event) {
        if (event.getAction() != 2 || event.getKeyCode() != 0) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(content);
        int selectionEnd = Selection.getSelectionEnd(content);
        if (selectionEnd < selectionStart) {
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }
        CharSequence text = event.getCharacters();
        if (text == null) {
            return false;
        }
        content.replace(selectionStart, selectionEnd, text);
        return true;
    }
}
