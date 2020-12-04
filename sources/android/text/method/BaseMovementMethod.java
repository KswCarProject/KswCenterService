package android.text.method;

import android.text.Layout;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

public class BaseMovementMethod implements MovementMethod {
    public boolean canSelectArbitrarily() {
        return false;
    }

    public void initialize(TextView widget, Spannable text) {
    }

    public boolean onKeyDown(TextView widget, Spannable text, int keyCode, KeyEvent event) {
        boolean handled = handleMovementKey(widget, text, keyCode, getMovementMetaState(text, event), event);
        if (handled) {
            MetaKeyKeyListener.adjustMetaAfterKeypress(text);
            MetaKeyKeyListener.resetLockedMeta(text);
        }
        return handled;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyOther(android.widget.TextView r12, android.text.Spannable r13, android.view.KeyEvent r14) {
        /*
            r11 = this;
            int r6 = r11.getMovementMetaState(r13, r14)
            int r7 = r14.getKeyCode()
            r0 = 0
            if (r7 == 0) goto L_0x0035
            int r1 = r14.getAction()
            r2 = 2
            if (r1 != r2) goto L_0x0035
            int r8 = r14.getRepeatCount()
            r1 = 0
            r9 = r1
        L_0x0018:
            r10 = r0
            if (r10 >= r8) goto L_0x002c
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r7
            r4 = r6
            r5 = r14
            boolean r0 = r0.handleMovementKey(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x0028
            goto L_0x002c
        L_0x0028:
            r9 = 1
            int r0 = r10 + 1
            goto L_0x0018
        L_0x002c:
            if (r9 == 0) goto L_0x0034
            android.text.method.MetaKeyKeyListener.adjustMetaAfterKeypress((android.text.Spannable) r13)
            android.text.method.MetaKeyKeyListener.resetLockedMeta((android.text.Spannable) r13)
        L_0x0034:
            return r9
        L_0x0035:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.BaseMovementMethod.onKeyOther(android.widget.TextView, android.text.Spannable, android.view.KeyEvent):boolean");
    }

    public boolean onKeyUp(TextView widget, Spannable text, int keyCode, KeyEvent event) {
        return false;
    }

    public void onTakeFocus(TextView widget, Spannable text, int direction) {
    }

    public boolean onTouchEvent(TextView widget, Spannable text, MotionEvent event) {
        return false;
    }

    public boolean onTrackballEvent(TextView widget, Spannable text, MotionEvent event) {
        return false;
    }

    public boolean onGenericMotionEvent(TextView widget, Spannable text, MotionEvent event) {
        float hscroll;
        float vscroll;
        if ((event.getSource() & 2) == 0 || event.getAction() != 8) {
            return false;
        }
        if ((event.getMetaState() & 1) != 0) {
            vscroll = 0.0f;
            hscroll = event.getAxisValue(9);
        } else {
            vscroll = -event.getAxisValue(9);
            hscroll = event.getAxisValue(10);
        }
        boolean handled = false;
        if (hscroll < 0.0f) {
            handled = false | scrollLeft(widget, text, (int) Math.ceil((double) (-hscroll)));
        } else if (hscroll > 0.0f) {
            handled = false | scrollRight(widget, text, (int) Math.ceil((double) hscroll));
        }
        if (vscroll < 0.0f) {
            return handled | scrollUp(widget, text, (int) Math.ceil((double) (-vscroll)));
        }
        if (vscroll > 0.0f) {
            return handled | scrollDown(widget, text, (int) Math.ceil((double) vscroll));
        }
        return handled;
    }

    /* access modifiers changed from: protected */
    public int getMovementMetaState(Spannable buffer, KeyEvent event) {
        return KeyEvent.normalizeMetaState(MetaKeyKeyListener.getMetaState((CharSequence) buffer, event) & -1537) & -194;
    }

    /* access modifiers changed from: protected */
    public boolean handleMovementKey(TextView widget, Spannable buffer, int keyCode, int movementMetaState, KeyEvent event) {
        switch (keyCode) {
            case 19:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return up(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 2)) {
                    return top(widget, buffer);
                }
                return false;
            case 20:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return down(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 2)) {
                    return bottom(widget, buffer);
                }
                return false;
            case 21:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return left(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 4096)) {
                    return leftWord(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 2)) {
                    return lineStart(widget, buffer);
                }
                return false;
            case 22:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return right(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 4096)) {
                    return rightWord(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 2)) {
                    return lineEnd(widget, buffer);
                }
                return false;
            case 92:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return pageUp(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 2)) {
                    return top(widget, buffer);
                }
                return false;
            case 93:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return pageDown(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 2)) {
                    return bottom(widget, buffer);
                }
                return false;
            case 122:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return home(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 4096)) {
                    return top(widget, buffer);
                }
                return false;
            case 123:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    return end(widget, buffer);
                }
                if (KeyEvent.metaStateHasModifiers(movementMetaState, 4096)) {
                    return bottom(widget, buffer);
                }
                return false;
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean left(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean right(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean up(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean down(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean pageUp(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean pageDown(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean top(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean bottom(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean lineStart(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean lineEnd(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean leftWord(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean rightWord(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean home(TextView widget, Spannable buffer) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean end(TextView widget, Spannable buffer) {
        return false;
    }

    private int getTopLine(TextView widget) {
        return widget.getLayout().getLineForVertical(widget.getScrollY());
    }

    private int getBottomLine(TextView widget) {
        return widget.getLayout().getLineForVertical(widget.getScrollY() + getInnerHeight(widget));
    }

    private int getInnerWidth(TextView widget) {
        return (widget.getWidth() - widget.getTotalPaddingLeft()) - widget.getTotalPaddingRight();
    }

    private int getInnerHeight(TextView widget) {
        return (widget.getHeight() - widget.getTotalPaddingTop()) - widget.getTotalPaddingBottom();
    }

    private int getCharacterWidth(TextView widget) {
        return (int) Math.ceil((double) widget.getPaint().getFontSpacing());
    }

    private int getScrollBoundsLeft(TextView widget) {
        Layout layout = widget.getLayout();
        int topLine = getTopLine(widget);
        int bottomLine = getBottomLine(widget);
        if (topLine > bottomLine) {
            return 0;
        }
        int left = Integer.MAX_VALUE;
        for (int line = topLine; line <= bottomLine; line++) {
            int lineLeft = (int) Math.floor((double) layout.getLineLeft(line));
            if (lineLeft < left) {
                left = lineLeft;
            }
        }
        return left;
    }

    private int getScrollBoundsRight(TextView widget) {
        Layout layout = widget.getLayout();
        int topLine = getTopLine(widget);
        int bottomLine = getBottomLine(widget);
        if (topLine > bottomLine) {
            return 0;
        }
        int right = Integer.MIN_VALUE;
        for (int line = topLine; line <= bottomLine; line++) {
            int lineRight = (int) Math.ceil((double) layout.getLineRight(line));
            if (lineRight > right) {
                right = lineRight;
            }
        }
        return right;
    }

    /* access modifiers changed from: protected */
    public boolean scrollLeft(TextView widget, Spannable buffer, int amount) {
        int minScrollX = getScrollBoundsLeft(widget);
        int scrollX = widget.getScrollX();
        if (scrollX <= minScrollX) {
            return false;
        }
        widget.scrollTo(Math.max(scrollX - (getCharacterWidth(widget) * amount), minScrollX), widget.getScrollY());
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollRight(TextView widget, Spannable buffer, int amount) {
        int maxScrollX = getScrollBoundsRight(widget) - getInnerWidth(widget);
        int scrollX = widget.getScrollX();
        if (scrollX >= maxScrollX) {
            return false;
        }
        widget.scrollTo(Math.min((getCharacterWidth(widget) * amount) + scrollX, maxScrollX), widget.getScrollY());
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollUp(TextView widget, Spannable buffer, int amount) {
        Layout layout = widget.getLayout();
        int top = widget.getScrollY();
        int topLine = layout.getLineForVertical(top);
        if (layout.getLineTop(topLine) == top) {
            topLine--;
        }
        if (topLine < 0) {
            return false;
        }
        Touch.scrollTo(widget, layout, widget.getScrollX(), layout.getLineTop(Math.max((topLine - amount) + 1, 0)));
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollDown(TextView widget, Spannable buffer, int amount) {
        Layout layout = widget.getLayout();
        int innerHeight = getInnerHeight(widget);
        int bottom = widget.getScrollY() + innerHeight;
        int bottomLine = layout.getLineForVertical(bottom);
        if (layout.getLineTop(bottomLine + 1) < bottom + 1) {
            bottomLine++;
        }
        int limit = layout.getLineCount() - 1;
        if (bottomLine > limit) {
            return false;
        }
        Touch.scrollTo(widget, layout, widget.getScrollX(), layout.getLineTop(Math.min((bottomLine + amount) - 1, limit) + 1) - innerHeight);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollPageUp(TextView widget, Spannable buffer) {
        Layout layout = widget.getLayout();
        int topLine = layout.getLineForVertical(widget.getScrollY() - getInnerHeight(widget));
        if (topLine < 0) {
            return false;
        }
        Touch.scrollTo(widget, layout, widget.getScrollX(), layout.getLineTop(topLine));
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollPageDown(TextView widget, Spannable buffer) {
        Layout layout = widget.getLayout();
        int innerHeight = getInnerHeight(widget);
        int bottomLine = layout.getLineForVertical(widget.getScrollY() + innerHeight + innerHeight);
        if (bottomLine > layout.getLineCount() - 1) {
            return false;
        }
        Touch.scrollTo(widget, layout, widget.getScrollX(), layout.getLineTop(bottomLine + 1) - innerHeight);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollTop(TextView widget, Spannable buffer) {
        Layout layout = widget.getLayout();
        if (getTopLine(widget) < 0) {
            return false;
        }
        Touch.scrollTo(widget, layout, widget.getScrollX(), layout.getLineTop(0));
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollBottom(TextView widget, Spannable buffer) {
        Layout layout = widget.getLayout();
        int lineCount = layout.getLineCount();
        if (getBottomLine(widget) > lineCount - 1) {
            return false;
        }
        Touch.scrollTo(widget, layout, widget.getScrollX(), layout.getLineTop(lineCount) - getInnerHeight(widget));
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollLineStart(TextView widget, Spannable buffer) {
        int minScrollX = getScrollBoundsLeft(widget);
        if (widget.getScrollX() <= minScrollX) {
            return false;
        }
        widget.scrollTo(minScrollX, widget.getScrollY());
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean scrollLineEnd(TextView widget, Spannable buffer) {
        int maxScrollX = getScrollBoundsRight(widget) - getInnerWidth(widget);
        if (widget.getScrollX() >= maxScrollX) {
            return false;
        }
        widget.scrollTo(maxScrollX, widget.getScrollY());
        return true;
    }
}
