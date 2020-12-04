package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
public class MediaNotificationView extends FrameLayout {
    private View mActions;
    private NotificationHeaderView mHeader;
    private int mImagePushIn;
    private View mMainColumn;
    private View mMediaContent;
    private final int mNotificationContentImageMarginEnd;
    private final int mNotificationContentMarginEnd;
    private ImageView mRightIcon;

    public MediaNotificationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MediaNotificationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaNotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r18, int r19) {
        /*
            r17 = this;
            r0 = r17
            android.widget.ImageView r1 = r0.mRightIcon
            int r1 = r1.getVisibility()
            r2 = 0
            r3 = 8
            if (r1 == r3) goto L_0x000f
            r1 = 1
            goto L_0x0010
        L_0x000f:
            r1 = r2
        L_0x0010:
            if (r1 != 0) goto L_0x0015
            r17.resetHeaderIndention()
        L_0x0015:
            super.onMeasure(r18, r19)
            int r3 = android.view.View.MeasureSpec.getMode(r18)
            r4 = 0
            r0.mImagePushIn = r2
            if (r1 == 0) goto L_0x00cd
            if (r3 == 0) goto L_0x00cd
            int r5 = android.view.View.MeasureSpec.getSize(r18)
            android.view.View r6 = r0.mActions
            int r6 = r6.getMeasuredWidth()
            int r5 = r5 - r6
            android.widget.ImageView r6 = r0.mRightIcon
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r7 = r6.getMarginEnd()
            int r5 = r5 - r7
            android.view.View r8 = r0.mMediaContent
            int r8 = r8.getMeasuredHeight()
            if (r5 <= r8) goto L_0x0045
            r5 = r8
            goto L_0x004f
        L_0x0045:
            if (r5 >= r8) goto L_0x004f
            int r5 = java.lang.Math.max(r2, r5)
            int r2 = r8 - r5
            r0.mImagePushIn = r2
        L_0x004f:
            int r2 = r6.width
            if (r2 != r8) goto L_0x0057
            int r2 = r6.height
            if (r2 == r8) goto L_0x0061
        L_0x0057:
            r6.width = r8
            r6.height = r8
            android.widget.ImageView r2 = r0.mRightIcon
            r2.setLayoutParams(r6)
            r4 = 1
        L_0x0061:
            android.view.View r2 = r0.mMainColumn
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
            int r9 = r5 + r7
            int r10 = r0.mNotificationContentMarginEnd
            int r9 = r9 + r10
            int r10 = r2.getMarginEnd()
            if (r9 == r10) goto L_0x007d
            r2.setMarginEnd(r9)
            android.view.View r10 = r0.mMainColumn
            r10.setLayoutParams(r2)
            r4 = 1
        L_0x007d:
            r10 = r7
            int r11 = r5 + r7
            android.view.NotificationHeaderView r12 = r0.mHeader
            int r12 = r12.getHeaderTextMarginEnd()
            if (r11 == r12) goto L_0x008e
            android.view.NotificationHeaderView r12 = r0.mHeader
            r12.setHeaderTextMarginEnd(r11)
            r4 = 1
        L_0x008e:
            android.view.NotificationHeaderView r12 = r0.mHeader
            android.view.ViewGroup$LayoutParams r12 = r12.getLayoutParams()
            r2 = r12
            android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
            int r12 = r2.getMarginEnd()
            if (r12 == r10) goto L_0x00a6
            r2.setMarginEnd(r10)
            android.view.NotificationHeaderView r12 = r0.mHeader
            r12.setLayoutParams(r2)
            r4 = 1
        L_0x00a6:
            android.view.NotificationHeaderView r12 = r0.mHeader
            int r12 = r12.getPaddingEnd()
            int r13 = r0.mNotificationContentImageMarginEnd
            if (r12 == r13) goto L_0x00cd
            android.view.NotificationHeaderView r12 = r0.mHeader
            android.view.NotificationHeaderView r13 = r0.mHeader
            int r13 = r13.getPaddingStart()
            android.view.NotificationHeaderView r14 = r0.mHeader
            int r14 = r14.getPaddingTop()
            int r15 = r0.mNotificationContentImageMarginEnd
            r16 = r1
            android.view.NotificationHeaderView r1 = r0.mHeader
            int r1 = r1.getPaddingBottom()
            r12.setPaddingRelative(r13, r14, r15, r1)
            r4 = 1
            goto L_0x00cf
        L_0x00cd:
            r16 = r1
        L_0x00cf:
            if (r4 == 0) goto L_0x00d4
            super.onMeasure(r18, r19)
        L_0x00d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.MediaNotificationView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mImagePushIn > 0) {
            if (getLayoutDirection() == 1) {
                this.mImagePushIn *= -1;
            }
            this.mRightIcon.layout(this.mRightIcon.getLeft() + this.mImagePushIn, this.mRightIcon.getTop(), this.mRightIcon.getRight() + this.mImagePushIn, this.mRightIcon.getBottom());
        }
    }

    private void resetHeaderIndention() {
        if (this.mHeader.getPaddingEnd() != this.mNotificationContentMarginEnd) {
            this.mHeader.setPaddingRelative(this.mHeader.getPaddingStart(), this.mHeader.getPaddingTop(), this.mNotificationContentMarginEnd, this.mHeader.getPaddingBottom());
        }
        ViewGroup.MarginLayoutParams headerParams = (ViewGroup.MarginLayoutParams) this.mHeader.getLayoutParams();
        headerParams.setMarginEnd(0);
        if (headerParams.getMarginEnd() != 0) {
            headerParams.setMarginEnd(0);
            this.mHeader.setLayoutParams(headerParams);
        }
    }

    public MediaNotificationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mNotificationContentMarginEnd = context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        this.mNotificationContentImageMarginEnd = context.getResources().getDimensionPixelSize(R.dimen.notification_content_image_margin_end);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mRightIcon = (ImageView) findViewById(R.id.right_icon);
        this.mActions = findViewById(R.id.media_actions);
        this.mHeader = (NotificationHeaderView) findViewById(R.id.notification_header);
        this.mMainColumn = findViewById(R.id.notification_main_column);
        this.mMediaContent = findViewById(R.id.notification_media_content);
    }
}
