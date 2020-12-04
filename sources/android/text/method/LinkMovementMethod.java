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

public class LinkMovementMethod extends ScrollingMovementMethod {
    private static final int CLICK = 1;
    private static final int DOWN = 3;
    private static Object FROM_BELOW = new NoCopySpan.Concrete();
    private static final int HIDE_FLOATING_TOOLBAR_DELAY_MS = 200;
    private static final int UP = 2;
    @UnsupportedAppUsage
    private static LinkMovementMethod sInstance;

    public boolean canSelectArbitrarily() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean handleMovementKey(TextView widget, Spannable buffer, int keyCode, int movementMetaState, KeyEvent event) {
        if ((keyCode == 23 || keyCode == 66) && KeyEvent.metaStateHasNoModifiers(movementMetaState) && event.getAction() == 0 && event.getRepeatCount() == 0 && action(1, widget, buffer)) {
            return true;
        }
        return super.handleMovementKey(widget, buffer, keyCode, movementMetaState, event);
    }

    /* access modifiers changed from: protected */
    public boolean up(TextView widget, Spannable buffer) {
        if (action(2, widget, buffer)) {
            return true;
        }
        return super.up(widget, buffer);
    }

    /* access modifiers changed from: protected */
    public boolean down(TextView widget, Spannable buffer) {
        if (action(3, widget, buffer)) {
            return true;
        }
        return super.down(widget, buffer);
    }

    /* access modifiers changed from: protected */
    public boolean left(TextView widget, Spannable buffer) {
        if (action(2, widget, buffer)) {
            return true;
        }
        return super.left(widget, buffer);
    }

    /* access modifiers changed from: protected */
    public boolean right(TextView widget, Spannable buffer) {
        if (action(3, widget, buffer)) {
            return true;
        }
        return super.right(widget, buffer);
    }

    private boolean action(int what, TextView widget, Spannable buffer) {
        int areaBot;
        TextView textView = widget;
        Spannable spannable = buffer;
        Layout layout = widget.getLayout();
        int padding = widget.getTotalPaddingTop() + widget.getTotalPaddingBottom();
        int i = widget.getScrollY();
        int areaBot2 = (widget.getHeight() + i) - padding;
        int lineTop = layout.getLineForVertical(i);
        int lineBot = layout.getLineForVertical(areaBot2);
        int first = layout.getLineStart(lineTop);
        int last = layout.getLineEnd(lineBot);
        ClickableSpan[] candidates = (ClickableSpan[]) spannable.getSpans(first, last, ClickableSpan.class);
        int a = Selection.getSelectionStart(buffer);
        int b = Selection.getSelectionEnd(buffer);
        int selStart = Math.min(a, b);
        int selEnd = Math.max(a, b);
        if (selStart < 0 && spannable.getSpanStart(FROM_BELOW) >= 0) {
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
                int i2 = padding;
                int i3 = i;
                int i4 = areaBot2;
                if (selStart != selEnd) {
                    ClickableSpan[] links = (ClickableSpan[]) spannable.getSpans(selStart, selEnd, ClickableSpan.class);
                    if (links.length == 1) {
                        ClickableSpan link = links[0];
                        if (!(link instanceof TextLinks.TextLinkSpan)) {
                            link.onClick(textView);
                            break;
                        } else {
                            ((TextLinks.TextLinkSpan) link).onClick(textView, 1);
                            break;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case 2:
                int i5 = padding;
                int i6 = i;
                int bestEnd = -1;
                int bestStart = -1;
                int i7 = 0;
                while (i7 < candidates.length) {
                    int end = spannable.getSpanEnd(candidates[i7]);
                    if (end >= selEnd && selStart != selEnd) {
                        areaBot = areaBot2;
                    } else if (end > bestEnd) {
                        areaBot = areaBot2;
                        bestStart = spannable.getSpanStart(candidates[i7]);
                        bestEnd = end;
                    } else {
                        areaBot = areaBot2;
                    }
                    i7++;
                    areaBot2 = areaBot;
                }
                if (bestStart >= 0) {
                    Selection.setSelection(spannable, bestEnd, bestStart);
                    return true;
                }
                break;
            case 3:
                Layout layout2 = layout;
                int bestStart2 = Integer.MAX_VALUE;
                int bestEnd2 = Integer.MAX_VALUE;
                int bestStart3 = 0;
                while (true) {
                    int padding2 = padding;
                    int areaTop = i;
                    int areaTop2 = bestStart3;
                    if (areaTop2 >= candidates.length) {
                        if (bestEnd2 >= Integer.MAX_VALUE) {
                            int i8 = areaBot2;
                            break;
                        } else {
                            Selection.setSelection(spannable, bestStart2, bestEnd2);
                            return true;
                        }
                    } else {
                        int start = spannable.getSpanStart(candidates[areaTop2]);
                        if ((start > selStart || selStart == selEnd) && start < bestStart2) {
                            bestEnd2 = spannable.getSpanEnd(candidates[areaTop2]);
                            bestStart2 = start;
                        }
                        bestStart3 = areaTop2 + 1;
                        padding = padding2;
                        i = areaTop;
                    }
                }
            default:
                Layout layout3 = layout;
                int i9 = padding;
                int i10 = i;
                int i11 = areaBot2;
                break;
        }
        return false;
    }

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        if (action == 1 || action == 0) {
            int x = ((int) event.getX()) - widget.getTotalPaddingLeft();
            int y = ((int) event.getY()) - widget.getTotalPaddingTop();
            int x2 = x + widget.getScrollX();
            int y2 = y + widget.getScrollY();
            Layout layout = widget.getLayout();
            int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y2), (float) x2);
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

    public void initialize(TextView widget, Spannable text) {
        Selection.removeSelection(text);
        text.removeSpan(FROM_BELOW);
    }

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
