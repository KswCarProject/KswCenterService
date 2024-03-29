package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class LinkMovementMethod extends ScrollingMovementMethod {
    private static final int CLICK = 1;
    private static final int DOWN = 3;
    private static Object FROM_BELOW = new NoCopySpan.Concrete();
    private static final int HIDE_FLOATING_TOOLBAR_DELAY_MS = 200;

    /* renamed from: UP */
    private static final int f284UP = 2;
    @UnsupportedAppUsage
    private static LinkMovementMethod sInstance;

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean canSelectArbitrarily() {
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean handleMovementKey(TextView widget, Spannable buffer, int keyCode, int movementMetaState, KeyEvent event) {
        if ((keyCode == 23 || keyCode == 66) && KeyEvent.metaStateHasNoModifiers(movementMetaState) && event.getAction() == 0 && event.getRepeatCount() == 0 && action(1, widget, buffer)) {
            return true;
        }
        return super.handleMovementKey(widget, buffer, keyCode, movementMetaState, event);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    /* renamed from: up */
    protected boolean mo76up(TextView widget, Spannable buffer) {
        if (action(2, widget, buffer)) {
            return true;
        }
        return super.mo76up(widget, buffer);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean down(TextView widget, Spannable buffer) {
        if (action(3, widget, buffer)) {
            return true;
        }
        return super.down(widget, buffer);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean left(TextView widget, Spannable buffer) {
        if (action(2, widget, buffer)) {
            return true;
        }
        return super.left(widget, buffer);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean right(TextView widget, Spannable buffer) {
        if (action(3, widget, buffer)) {
            return true;
        }
        return super.right(widget, buffer);
    }

    private boolean action(int what, TextView widget, Spannable buffer) {
        int areaBot;
        Layout layout = widget.getLayout();
        int padding = widget.getTotalPaddingTop() + widget.getTotalPaddingBottom();
        int i = widget.getScrollY();
        int areaBot2 = (widget.getHeight() + i) - padding;
        int lineTop = layout.getLineForVertical(i);
        int lineBot = layout.getLineForVertical(areaBot2);
        int first = layout.getLineStart(lineTop);
        int last = layout.getLineEnd(lineBot);
        ClickableSpan[] candidates = (ClickableSpan[]) buffer.getSpans(first, last, ClickableSpan.class);
        int a = Selection.getSelectionStart(buffer);
        int b = Selection.getSelectionEnd(buffer);
        int selStart = Math.min(a, b);
        int selEnd = Math.max(a, b);
        if (selStart < 0 && buffer.getSpanStart(FROM_BELOW) >= 0) {
            int length = buffer.length();
            selEnd = length;
            selStart = length;
        }
        if (selStart > last) {
            selEnd = Integer.MAX_VALUE;
            selStart = Integer.MAX_VALUE;
        }
        if (selEnd < first) {
            selEnd = -1;
            selStart = -1;
        }
        switch (what) {
            case 1:
                if (selStart == selEnd) {
                    return false;
                }
                ClickableSpan[] links = (ClickableSpan[]) buffer.getSpans(selStart, selEnd, ClickableSpan.class);
                if (links.length != 1) {
                    return false;
                }
                ClickableSpan link = links[0];
                if (link instanceof TextLinks.TextLinkSpan) {
                    ((TextLinks.TextLinkSpan) link).onClick(widget, 1);
                    break;
                } else {
                    link.onClick(widget);
                    break;
                }
            case 2:
                int bestEnd = -1;
                int bestEnd2 = -1;
                int bestStart = 0;
                while (bestStart < candidates.length) {
                    int end = buffer.getSpanEnd(candidates[bestStart]);
                    if (end >= selEnd && selStart != selEnd) {
                        areaBot = areaBot2;
                    } else if (end <= bestEnd) {
                        areaBot = areaBot2;
                    } else {
                        areaBot = areaBot2;
                        bestEnd2 = buffer.getSpanStart(candidates[bestStart]);
                        bestEnd = end;
                    }
                    bestStart++;
                    areaBot2 = areaBot;
                }
                if (bestEnd2 >= 0) {
                    Selection.setSelection(buffer, bestEnd, bestEnd2);
                    return true;
                }
                break;
            case 3:
                int bestStart2 = Integer.MAX_VALUE;
                int bestEnd3 = Integer.MAX_VALUE;
                int bestStart3 = 0;
                while (true) {
                    int i2 = bestStart3;
                    int padding2 = padding;
                    int areaTop = i;
                    if (i2 < candidates.length) {
                        int start = buffer.getSpanStart(candidates[i2]);
                        if ((start > selStart || selStart == selEnd) && start < bestStart2) {
                            bestEnd3 = buffer.getSpanEnd(candidates[i2]);
                            bestStart2 = start;
                        }
                        bestStart3 = i2 + 1;
                        padding = padding2;
                        i = areaTop;
                    } else if (bestEnd3 >= Integer.MAX_VALUE) {
                        break;
                    } else {
                        Selection.setSelection(buffer, bestStart2, bestEnd3);
                        return true;
                    }
                }
        }
        return false;
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        if (action == 1 || action == 0) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int x2 = x - widget.getTotalPaddingLeft();
            int y2 = y - widget.getTotalPaddingTop();
            int x3 = x2 + widget.getScrollX();
            int y3 = y2 + widget.getScrollY();
            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y3);
            int off = layout.getOffsetForHorizontal(line, x3);
            ClickableSpan[] links = (ClickableSpan[]) buffer.getSpans(off, off, ClickableSpan.class);
            if (links.length != 0) {
                ClickableSpan link = links[0];
                if (action == 1) {
                    if (link instanceof TextLinks.TextLinkSpan) {
                        ((TextLinks.TextLinkSpan) link).onClick(widget, 0);
                    } else {
                        link.onClick(widget);
                    }
                } else if (action == 0) {
                    if (widget.getContext().getApplicationInfo().targetSdkVersion >= 28) {
                        widget.hideFloatingToolbar(200);
                    }
                    Selection.setSelection(buffer, buffer.getSpanStart(link), buffer.getSpanEnd(link));
                }
                return true;
            }
            Selection.removeSelection(buffer);
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void initialize(TextView widget, Spannable text) {
        Selection.removeSelection(text);
        text.removeSpan(FROM_BELOW);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void onTakeFocus(TextView view, Spannable text, int dir) {
        Selection.removeSelection(text);
        if ((dir & 1) != 0) {
            text.setSpan(FROM_BELOW, 0, 0, 34);
        } else {
            text.removeSpan(FROM_BELOW);
        }
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new LinkMovementMethod();
        }
        return sInstance;
    }
}
