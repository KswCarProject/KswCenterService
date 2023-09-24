package android.view.inputmethod;

import android.content.Context;
import android.content.res.TypedArray;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;

/* loaded from: classes4.dex */
public class BaseInputConnection implements InputConnection {
    private static final boolean DEBUG = false;
    private static final String TAG = "BaseInputConnection";
    private Object[] mDefaultComposingSpans;
    final boolean mDummyMode;
    Editable mEditable;
    protected final InputMethodManager mIMM;
    KeyCharacterMap mKeyCharacterMap;
    final View mTargetView;
    static final Object COMPOSING = new ComposingText();
    private static int INVALID_INDEX = -1;

    BaseInputConnection(InputMethodManager mgr, boolean fullEditor) {
        this.mIMM = mgr;
        this.mTargetView = null;
        this.mDummyMode = !fullEditor;
    }

    public BaseInputConnection(View targetView, boolean fullEditor) {
        this.mIMM = (InputMethodManager) targetView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mTargetView = targetView;
        this.mDummyMode = !fullEditor;
    }

    public static final void removeComposingSpans(Spannable text) {
        text.removeSpan(COMPOSING);
        Object[] sps = text.getSpans(0, text.length(), Object.class);
        if (sps != null) {
            for (int i = sps.length - 1; i >= 0; i--) {
                Object o = sps[i];
                if ((text.getSpanFlags(o) & 256) != 0) {
                    text.removeSpan(o);
                }
            }
        }
    }

    public static void setComposingSpans(Spannable text) {
        setComposingSpans(text, 0, text.length());
    }

    public static void setComposingSpans(Spannable text, int start, int end) {
        Object[] sps = text.getSpans(start, end, Object.class);
        if (sps != null) {
            for (int i = sps.length - 1; i >= 0; i--) {
                Object o = sps[i];
                if (o == COMPOSING) {
                    text.removeSpan(o);
                } else {
                    int fl = text.getSpanFlags(o);
                    if ((fl & 307) != 289) {
                        text.setSpan(o, text.getSpanStart(o), text.getSpanEnd(o), (fl & (-52)) | 256 | 33);
                    }
                }
            }
        }
        text.setSpan(COMPOSING, start, end, 289);
    }

    public static int getComposingSpanStart(Spannable text) {
        return text.getSpanStart(COMPOSING);
    }

    public static int getComposingSpanEnd(Spannable text) {
        return text.getSpanEnd(COMPOSING);
    }

    public Editable getEditable() {
        if (this.mEditable == null) {
            this.mEditable = Editable.Factory.getInstance().newEditable("");
            Selection.setSelection(this.mEditable, 0);
        }
        return this.mEditable;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
        finishComposingText();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int states) {
        Editable content = getEditable();
        if (content == null) {
            return false;
        }
        MetaKeyKeyListener.clearMetaKeyState(content, states);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(CompletionInfo text) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence text, int newCursorPosition) {
        replaceText(text, newCursorPosition, false);
        sendCurrentText();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        Editable content = getEditable();
        if (content == null) {
            return false;
        }
        beginBatchEdit();
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        if (a > b) {
            a = b;
            b = a;
        }
        int ca = getComposingSpanStart(content);
        int cb = getComposingSpanEnd(content);
        if (cb < ca) {
            ca = cb;
            cb = ca;
        }
        if (ca != -1 && cb != -1) {
            if (ca < a) {
                a = ca;
            }
            if (cb > b) {
                b = cb;
            }
        }
        int deleted = 0;
        if (beforeLength > 0) {
            int start = a - beforeLength;
            if (start < 0) {
                start = 0;
            }
            content.delete(start, a);
            deleted = a - start;
        }
        if (afterLength > 0) {
            int b2 = b - deleted;
            int end = b2 + afterLength;
            if (end > content.length()) {
                end = content.length();
            }
            content.delete(b2, end);
        }
        endBatchEdit();
        return true;
    }

    private static int findIndexBackward(CharSequence cs, int from, int numCodePoints) {
        int N = cs.length();
        if (from < 0 || N < from) {
            return INVALID_INDEX;
        }
        if (numCodePoints < 0) {
            return INVALID_INDEX;
        }
        boolean waitingHighSurrogate = false;
        int currentIndex = from;
        int currentIndex2 = numCodePoints;
        while (currentIndex2 != 0) {
            currentIndex--;
            if (currentIndex < 0) {
                if (waitingHighSurrogate) {
                    return INVALID_INDEX;
                }
                return 0;
            }
            char c = cs.charAt(currentIndex);
            if (waitingHighSurrogate) {
                if (!Character.isHighSurrogate(c)) {
                    return INVALID_INDEX;
                }
                waitingHighSurrogate = false;
                currentIndex2--;
            } else if (!Character.isSurrogate(c)) {
                currentIndex2--;
            } else if (Character.isHighSurrogate(c)) {
                return INVALID_INDEX;
            } else {
                waitingHighSurrogate = true;
            }
        }
        return currentIndex;
    }

    private static int findIndexForward(CharSequence cs, int from, int numCodePoints) {
        int N = cs.length();
        if (from < 0 || N < from) {
            return INVALID_INDEX;
        }
        if (numCodePoints < 0) {
            return INVALID_INDEX;
        }
        boolean waitingLowSurrogate = false;
        int currentIndex = from;
        int currentIndex2 = numCodePoints;
        while (currentIndex2 != 0) {
            if (currentIndex >= N) {
                if (waitingLowSurrogate) {
                    return INVALID_INDEX;
                }
                return N;
            }
            char c = cs.charAt(currentIndex);
            if (waitingLowSurrogate) {
                if (!Character.isLowSurrogate(c)) {
                    return INVALID_INDEX;
                }
                currentIndex2--;
                waitingLowSurrogate = false;
                currentIndex++;
            } else if (!Character.isSurrogate(c)) {
                currentIndex2--;
                currentIndex++;
            } else if (Character.isLowSurrogate(c)) {
                return INVALID_INDEX;
            } else {
                waitingLowSurrogate = true;
                currentIndex++;
            }
        }
        return currentIndex;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
        int start;
        int end;
        Editable content = getEditable();
        if (content == null) {
            return false;
        }
        beginBatchEdit();
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        if (a > b) {
            a = b;
            b = a;
        }
        int ca = getComposingSpanStart(content);
        int cb = getComposingSpanEnd(content);
        if (cb < ca) {
            ca = cb;
            cb = ca;
        }
        if (ca != -1 && cb != -1) {
            if (ca < a) {
                a = ca;
            }
            if (cb > b) {
                b = cb;
            }
        }
        if (a >= 0 && b >= 0 && (start = findIndexBackward(content, a, Math.max(beforeLength, 0))) != INVALID_INDEX && (end = findIndexForward(content, b, Math.max(afterLength, 0))) != INVALID_INDEX) {
            int numDeleteBefore = a - start;
            if (numDeleteBefore > 0) {
                content.delete(start, a);
            }
            int numDeleteAfter = end - b;
            if (numDeleteAfter > 0) {
                content.delete(b - numDeleteBefore, end - numDeleteBefore);
            }
        }
        endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        Editable content = getEditable();
        if (content != null) {
            beginBatchEdit();
            removeComposingSpans(content);
            sendCurrentText();
            endBatchEdit();
            return true;
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int reqModes) {
        Editable content;
        if (this.mDummyMode || (content = getEditable()) == null) {
            return 0;
        }
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        if (a > b) {
            a = b;
        }
        int tmp = TextUtils.getCapsMode(content, a, reqModes);
        return tmp;
    }

    @Override // android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextBeforeCursor(int length, int flags) {
        Editable content = getEditable();
        if (content == null) {
            return null;
        }
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        if (a > b) {
            a = b;
        }
        if (a <= 0) {
            return "";
        }
        if (length > a) {
            length = a;
        }
        if ((flags & 1) != 0) {
            return content.subSequence(a - length, a);
        }
        return TextUtils.substring(content, a - length, a);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int flags) {
        Editable content = getEditable();
        if (content == null) {
            return null;
        }
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        if (a > b) {
            a = b;
            b = a;
        }
        if (a == b || a < 0) {
            return null;
        }
        if ((flags & 1) != 0) {
            return content.subSequence(a, b);
        }
        return TextUtils.substring(content, a, b);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextAfterCursor(int length, int flags) {
        Editable content = getEditable();
        if (content == null) {
            return null;
        }
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        if (a > b) {
            b = a;
        }
        if (b < 0) {
            b = 0;
        }
        if (b + length > content.length()) {
            length = content.length() - b;
        }
        if ((flags & 1) != 0) {
            return content.subSequence(b, b + length);
        }
        return TextUtils.substring(content, b, b + length);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int actionCode) {
        long eventTime = SystemClock.uptimeMillis();
        sendKeyEvent(new KeyEvent(eventTime, eventTime, 0, 66, 0, 0, -1, 0, 22));
        sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime, 1, 66, 0, 0, -1, 0, 22));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int id) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String action, Bundle data) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int cursorUpdateMode) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence text, int newCursorPosition) {
        replaceText(text, newCursorPosition, true);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int start, int end) {
        Editable content = getEditable();
        if (content != null) {
            beginBatchEdit();
            removeComposingSpans(content);
            int a = start;
            int b = end;
            if (a > b) {
                a = b;
                b = a;
            }
            int length = content.length();
            if (a < 0) {
                a = 0;
            }
            if (b < 0) {
                b = 0;
            }
            if (a > length) {
                a = length;
            }
            if (b > length) {
                b = length;
            }
            ensureDefaultComposingSpans();
            if (this.mDefaultComposingSpans != null) {
                for (int i = 0; i < this.mDefaultComposingSpans.length; i++) {
                    content.setSpan(this.mDefaultComposingSpans[i], a, b, 289);
                }
            }
            content.setSpan(COMPOSING, a, b, 289);
            sendCurrentText();
            endBatchEdit();
            return true;
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int start, int end) {
        Editable content = getEditable();
        if (content == null) {
            return false;
        }
        int len = content.length();
        if (start > len || end > len || start < 0 || end < 0) {
            return true;
        }
        if (start == end && MetaKeyKeyListener.getMetaState(content, 2048) != 0) {
            Selection.extendSelection(content, start);
        } else {
            Selection.setSelection(content, start, end);
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent event) {
        this.mIMM.dispatchKeyEventFromInputMethod(this.mTargetView, event);
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean enabled) {
        return true;
    }

    private void sendCurrentText() {
        Editable content;
        int N;
        if (!this.mDummyMode || (content = getEditable()) == null || (N = content.length()) == 0) {
            return;
        }
        if (N == 1) {
            if (this.mKeyCharacterMap == null) {
                this.mKeyCharacterMap = KeyCharacterMap.load(-1);
            }
            char[] chars = new char[1];
            content.getChars(0, 1, chars, 0);
            KeyEvent[] events = this.mKeyCharacterMap.getEvents(chars);
            if (events != null) {
                for (KeyEvent keyEvent : events) {
                    sendKeyEvent(keyEvent);
                }
                content.clear();
                return;
            }
        }
        KeyEvent event = new KeyEvent(SystemClock.uptimeMillis(), content.toString(), -1, 0);
        sendKeyEvent(event);
        content.clear();
    }

    private void ensureDefaultComposingSpans() {
        Context context;
        if (this.mDefaultComposingSpans == null) {
            if (this.mTargetView != null) {
                context = this.mTargetView.getContext();
            } else if (this.mIMM.mServedView != null) {
                context = this.mIMM.mServedView.getContext();
            } else {
                context = null;
            }
            if (context != null) {
                TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{16843312});
                CharSequence style = ta.getText(0);
                ta.recycle();
                if (style != null && (style instanceof Spanned)) {
                    this.mDefaultComposingSpans = ((Spanned) style).getSpans(0, style.length(), Object.class);
                }
            }
        }
    }

    private void replaceText(CharSequence text, int newCursorPosition, boolean composing) {
        int newCursorPosition2;
        Spannable sp;
        Editable content = getEditable();
        if (content == null) {
            return;
        }
        beginBatchEdit();
        int a = getComposingSpanStart(content);
        int b = getComposingSpanEnd(content);
        if (b < a) {
            a = b;
            b = a;
        }
        if (a != -1 && b != -1) {
            removeComposingSpans(content);
        } else {
            a = Selection.getSelectionStart(content);
            b = Selection.getSelectionEnd(content);
            if (a < 0) {
                a = 0;
            }
            if (b < 0) {
                b = 0;
            }
            if (b < a) {
                int tmp = a;
                a = b;
                b = tmp;
            }
        }
        if (composing) {
            if (!(text instanceof Spannable)) {
                sp = new SpannableStringBuilder(text);
                text = sp;
                ensureDefaultComposingSpans();
                if (this.mDefaultComposingSpans != null) {
                    for (int i = 0; i < this.mDefaultComposingSpans.length; i++) {
                        sp.setSpan(this.mDefaultComposingSpans[i], 0, sp.length(), 289);
                    }
                }
            } else {
                sp = (Spannable) text;
            }
            setComposingSpans(sp);
        }
        if (newCursorPosition > 0) {
            newCursorPosition2 = newCursorPosition + (b - 1);
        } else {
            newCursorPosition2 = newCursorPosition + a;
        }
        if (newCursorPosition2 < 0) {
            newCursorPosition2 = 0;
        }
        if (newCursorPosition2 > content.length()) {
            newCursorPosition2 = content.length();
        }
        Selection.setSelection(content, newCursorPosition2);
        content.replace(a, b, text);
        endBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(InputContentInfo inputContentInfo, int flags, Bundle opts) {
        return false;
    }
}
