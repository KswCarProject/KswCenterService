package android.text.method;

import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class Touch {
    private Touch() {
    }

    public static void scrollTo(TextView widget, Layout layout, int x, int y) {
        int left;
        int right;
        int x2;
        int horizontalPadding = widget.getTotalPaddingLeft() + widget.getTotalPaddingRight();
        int availableWidth = widget.getWidth() - horizontalPadding;
        int top = layout.getLineForVertical(y);
        Layout.Alignment a = layout.getParagraphAlignment(top);
        boolean ltr = layout.getParagraphDirection(top) > 0;
        if (widget.getHorizontallyScrolling()) {
            int verticalPadding = widget.getTotalPaddingTop() + widget.getTotalPaddingBottom();
            int bottom = layout.getLineForVertical((widget.getHeight() + y) - verticalPadding);
            right = 0;
            left = Integer.MAX_VALUE;
            for (int left2 = top; left2 <= bottom; left2++) {
                left = (int) Math.min(left, layout.getLineLeft(left2));
                right = (int) Math.max(right, layout.getLineRight(left2));
            }
        } else {
            left = 0;
            right = availableWidth;
        }
        int right2 = right;
        int actualWidth = right2 - left;
        if (actualWidth < availableWidth) {
            if (a == Layout.Alignment.ALIGN_CENTER) {
                x2 = left - ((availableWidth - actualWidth) / 2);
            } else if ((ltr && a == Layout.Alignment.ALIGN_OPPOSITE) || ((!ltr && a == Layout.Alignment.ALIGN_NORMAL) || a == Layout.Alignment.ALIGN_RIGHT)) {
                x2 = left - (availableWidth - actualWidth);
            } else {
                x2 = left;
            }
        } else {
            x2 = Math.max(Math.min(x, right2 - availableWidth), left);
        }
        widget.scrollTo(x2, y);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        float dx;
        float dy;
        switch (event.getActionMasked()) {
            case 0:
                for (DragState dragState : (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class)) {
                    buffer.removeSpan(dragState);
                }
                buffer.setSpan(new DragState(event.getX(), event.getY(), widget.getScrollX(), widget.getScrollY()), 0, 0, 17);
                return true;
            case 1:
                DragState[] ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
                for (DragState dragState2 : ds) {
                    buffer.removeSpan(dragState2);
                }
                int i = ds.length;
                return i > 0 && ds[0].mUsed;
            case 2:
                DragState[] ds2 = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
                if (ds2.length > 0) {
                    if (!ds2[0].mFarEnough) {
                        int slop = ViewConfiguration.get(widget.getContext()).getScaledTouchSlop();
                        if (Math.abs(event.getX() - ds2[0].f285mX) >= slop || Math.abs(event.getY() - ds2[0].f286mY) >= slop) {
                            ds2[0].mFarEnough = true;
                        }
                    }
                    if (ds2[0].mFarEnough) {
                        ds2[0].mUsed = true;
                        boolean cap = ((event.getMetaState() & 1) == 0 && MetaKeyKeyListener.getMetaState(buffer, 1) != 1 && MetaKeyKeyListener.getMetaState(buffer, 2048) == 0) ? false : true;
                        if (cap) {
                            dx = event.getX() - ds2[0].f285mX;
                            dy = event.getY() - ds2[0].f286mY;
                        } else {
                            dx = ds2[0].f285mX - event.getX();
                            dy = ds2[0].f286mY - event.getY();
                        }
                        ds2[0].f285mX = event.getX();
                        ds2[0].f286mY = event.getY();
                        int nx = widget.getScrollX() + ((int) dx);
                        int ny = widget.getScrollY() + ((int) dy);
                        int padding = widget.getTotalPaddingTop() + widget.getTotalPaddingBottom();
                        Layout layout = widget.getLayout();
                        int ny2 = Math.max(Math.min(ny, layout.getHeight() - (widget.getHeight() - padding)), 0);
                        int oldX = widget.getScrollX();
                        int oldY = widget.getScrollY();
                        scrollTo(widget, layout, nx, ny2);
                        if (oldX != widget.getScrollX() || oldY != widget.getScrollY()) {
                            widget.cancelLongPress();
                        }
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public static int getInitialScrollX(TextView widget, Spannable buffer) {
        DragState[] ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
        if (ds.length > 0) {
            return ds[0].mScrollX;
        }
        return -1;
    }

    public static int getInitialScrollY(TextView widget, Spannable buffer) {
        DragState[] ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
        if (ds.length > 0) {
            return ds[0].mScrollY;
        }
        return -1;
    }

    /* loaded from: classes4.dex */
    private static class DragState implements NoCopySpan {
        public boolean mFarEnough;
        public int mScrollX;
        public int mScrollY;
        public boolean mUsed;

        /* renamed from: mX */
        public float f285mX;

        /* renamed from: mY */
        public float f286mY;

        public DragState(float x, float y, int scrollX, int scrollY) {
            this.f285mX = x;
            this.f286mY = y;
            this.mScrollX = scrollX;
            this.mScrollY = scrollY;
        }
    }
}
