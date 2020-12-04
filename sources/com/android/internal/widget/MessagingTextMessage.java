package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Pools;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class MessagingTextMessage extends ImageFloatingTextView implements MessagingMessage {
    private static Pools.SimplePool<MessagingTextMessage> sInstancePool = new Pools.SynchronizedPool(20);
    private final MessagingMessageState mState = new MessagingMessageState(this);

    public MessagingTextMessage(Context context) {
        super(context);
    }

    public MessagingTextMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessagingTextMessage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MessagingTextMessage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MessagingMessageState getState() {
        return this.mState;
    }

    public boolean setMessage(Notification.MessagingStyle.Message message) {
        super.setMessage(message);
        setText(message.getText());
        return true;
    }

    /* JADX WARNING: type inference failed for: r2v2, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.android.internal.widget.MessagingMessage createMessage(com.android.internal.widget.MessagingLayout r5, android.app.Notification.MessagingStyle.Message r6) {
        /*
            com.android.internal.widget.MessagingLinearLayout r0 = r5.getMessagingLinearLayout()
            android.util.Pools$SimplePool<com.android.internal.widget.MessagingTextMessage> r1 = sInstancePool
            java.lang.Object r1 = r1.acquire()
            com.android.internal.widget.MessagingTextMessage r1 = (com.android.internal.widget.MessagingTextMessage) r1
            if (r1 != 0) goto L_0x0027
            android.content.Context r2 = r5.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            r3 = 17367208(0x10900a8, float:2.5163397E-38)
            r4 = 0
            android.view.View r2 = r2.inflate((int) r3, (android.view.ViewGroup) r0, (boolean) r4)
            r1 = r2
            com.android.internal.widget.MessagingTextMessage r1 = (com.android.internal.widget.MessagingTextMessage) r1
            android.view.View$OnLayoutChangeListener r2 = com.android.internal.widget.MessagingLayout.MESSAGING_PROPERTY_ANIMATOR
            r1.addOnLayoutChangeListener(r2)
        L_0x0027:
            r1.setMessage(r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.MessagingTextMessage.createMessage(com.android.internal.widget.MessagingLayout, android.app.Notification$MessagingStyle$Message):com.android.internal.widget.MessagingMessage");
    }

    public void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    public static void dropCache() {
        sInstancePool = new Pools.SynchronizedPool(10);
    }

    public int getMeasuredType() {
        Layout layout;
        if ((!(getMeasuredHeight() < (getLayoutHeight() + getPaddingTop()) + getPaddingBottom()) || getLineCount() > 1) && (layout = getLayout()) != null) {
            return layout.getEllipsisCount(layout.getLineCount() - 1) > 0 ? 1 : 0;
        }
        return 2;
    }

    public void setMaxDisplayedLines(int lines) {
        setMaxLines(lines);
    }

    public int getConsumedLines() {
        return getLineCount();
    }

    public int getLayoutHeight() {
        Layout layout = getLayout();
        if (layout == null) {
            return 0;
        }
        return layout.getHeight();
    }

    public void setColor(int color) {
        setTextColor(color);
    }
}
