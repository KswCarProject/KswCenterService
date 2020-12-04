package com.android.internal.widget;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;

public class SubtitleView extends View {
    private static final int COLOR_BEVEL_DARK = Integer.MIN_VALUE;
    private static final int COLOR_BEVEL_LIGHT = -2130706433;
    private static final float INNER_PADDING_RATIO = 0.125f;
    private Layout.Alignment mAlignment;
    private int mBackgroundColor;
    private final float mCornerRadius;
    private int mEdgeColor;
    private int mEdgeType;
    private int mForegroundColor;
    private boolean mHasMeasurements;
    private int mInnerPaddingX;
    private int mLastMeasuredWidth;
    private StaticLayout mLayout;
    private final RectF mLineBounds;
    private final float mOutlineWidth;
    private Paint mPaint;
    private final float mShadowOffsetX;
    private final float mShadowOffsetY;
    private final float mShadowRadius;
    private float mSpacingAdd;
    private float mSpacingMult;
    private final SpannableStringBuilder mText;
    private TextPaint mTextPaint;

    public SubtitleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SubtitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubtitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SubtitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        this.mLineBounds = new RectF();
        this.mText = new SpannableStringBuilder();
        this.mAlignment = Layout.Alignment.ALIGN_CENTER;
        this.mSpacingMult = 1.0f;
        this.mSpacingAdd = 0.0f;
        this.mInnerPaddingX = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextView, defStyleAttr, defStyleRes);
        CharSequence text = "";
        int textSize = 15;
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == 0) {
                textSize = a.getDimensionPixelSize(attr, textSize);
            } else if (attr != 18) {
                switch (attr) {
                    case 53:
                        this.mSpacingAdd = (float) a.getDimensionPixelSize(attr, (int) this.mSpacingAdd);
                        break;
                    case 54:
                        this.mSpacingMult = a.getFloat(attr, this.mSpacingMult);
                        break;
                }
            } else {
                text = a.getText(attr);
            }
        }
        Resources res = getContext().getResources();
        this.mCornerRadius = (float) res.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_corner_radius);
        this.mOutlineWidth = (float) res.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_outline_width);
        this.mShadowRadius = (float) res.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_shadow_radius);
        this.mShadowOffsetX = (float) res.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_shadow_offset);
        this.mShadowOffsetY = this.mShadowOffsetX;
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setSubpixelText(true);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        setText(text);
        setTextSize((float) textSize);
    }

    public void setText(int resId) {
        setText(getContext().getText(resId));
    }

    public void setText(CharSequence text) {
        this.mText.clear();
        this.mText.append(text);
        this.mHasMeasurements = false;
        requestLayout();
        invalidate();
    }

    public void setForegroundColor(int color) {
        this.mForegroundColor = color;
        invalidate();
    }

    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
        invalidate();
    }

    public void setEdgeType(int edgeType) {
        this.mEdgeType = edgeType;
        invalidate();
    }

    public void setEdgeColor(int color) {
        this.mEdgeColor = color;
        invalidate();
    }

    public void setTextSize(float size) {
        if (this.mTextPaint.getTextSize() != size) {
            this.mTextPaint.setTextSize(size);
            this.mInnerPaddingX = (int) ((INNER_PADDING_RATIO * size) + 0.5f);
            this.mHasMeasurements = false;
            requestLayout();
            invalidate();
        }
    }

    public void setTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            this.mHasMeasurements = false;
            requestLayout();
            invalidate();
        }
    }

    public void setAlignment(Layout.Alignment textAlignment) {
        if (this.mAlignment != textAlignment) {
            this.mAlignment = textAlignment;
            this.mHasMeasurements = false;
            requestLayout();
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (computeMeasurements(View.MeasureSpec.getSize(widthMeasureSpec))) {
            StaticLayout layout = this.mLayout;
            setMeasuredDimension(layout.getWidth() + this.mPaddingLeft + this.mPaddingRight + (this.mInnerPaddingX * 2), layout.getHeight() + this.mPaddingTop + this.mPaddingBottom);
            return;
        }
        setMeasuredDimension(16777216, 16777216);
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        computeMeasurements(r - l);
    }

    private boolean computeMeasurements(int maxWidth) {
        if (this.mHasMeasurements && maxWidth == this.mLastMeasuredWidth) {
            return true;
        }
        int maxWidth2 = maxWidth - ((this.mPaddingLeft + this.mPaddingRight) + (this.mInnerPaddingX * 2));
        if (maxWidth2 <= 0) {
            return false;
        }
        this.mHasMeasurements = true;
        this.mLastMeasuredWidth = maxWidth2;
        this.mLayout = StaticLayout.Builder.obtain(this.mText, 0, this.mText.length(), this.mTextPaint, maxWidth2).setAlignment(this.mAlignment).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setUseLineSpacingFromFallbacks(true).build();
        return true;
    }

    public void setStyle(int styleId) {
        CaptioningManager.CaptionStyle style;
        ContentResolver cr = this.mContext.getContentResolver();
        if (styleId == -1) {
            style = CaptioningManager.CaptionStyle.getCustomStyle(cr);
        } else {
            style = CaptioningManager.CaptionStyle.PRESETS[styleId];
        }
        CaptioningManager.CaptionStyle defStyle = CaptioningManager.CaptionStyle.DEFAULT;
        this.mForegroundColor = style.hasForegroundColor() ? style.foregroundColor : defStyle.foregroundColor;
        this.mBackgroundColor = style.hasBackgroundColor() ? style.backgroundColor : defStyle.backgroundColor;
        this.mEdgeType = style.hasEdgeType() ? style.edgeType : defStyle.edgeType;
        this.mEdgeColor = style.hasEdgeColor() ? style.edgeColor : defStyle.edgeColor;
        this.mHasMeasurements = false;
        setTypeface(style.getTypeface());
        requestLayout();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00de A[LOOP:3: B:34:0x00dc->B:35:0x00de, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            android.text.StaticLayout r2 = r0.mLayout
            if (r2 != 0) goto L_0x0009
            return
        L_0x0009:
            int r3 = r18.save()
            int r4 = r0.mInnerPaddingX
            int r5 = r0.mPaddingLeft
            int r5 = r5 + r4
            float r5 = (float) r5
            int r6 = r0.mPaddingTop
            float r6 = (float) r6
            r1.translate(r5, r6)
            int r5 = r2.getLineCount()
            android.text.TextPaint r6 = r0.mTextPaint
            android.graphics.Paint r7 = r0.mPaint
            android.graphics.RectF r8 = r0.mLineBounds
            int r9 = r0.mBackgroundColor
            int r9 = android.graphics.Color.alpha((int) r9)
            r10 = 0
            if (r9 <= 0) goto L_0x0062
            float r9 = r0.mCornerRadius
            int r11 = r2.getLineTop(r10)
            float r11 = (float) r11
            int r12 = r0.mBackgroundColor
            r7.setColor((int) r12)
            android.graphics.Paint$Style r12 = android.graphics.Paint.Style.FILL
            r7.setStyle(r12)
            r12 = r11
            r11 = r10
        L_0x003f:
            if (r11 >= r5) goto L_0x0062
            float r13 = r2.getLineLeft(r11)
            float r14 = (float) r4
            float r13 = r13 - r14
            r8.left = r13
            float r13 = r2.getLineRight(r11)
            float r14 = (float) r4
            float r13 = r13 + r14
            r8.right = r13
            r8.top = r12
            int r13 = r2.getLineBottom(r11)
            float r13 = (float) r13
            r8.bottom = r13
            float r12 = r8.bottom
            r1.drawRoundRect(r8, r9, r9, r7)
            int r11 = r11 + 1
            goto L_0x003f
        L_0x0062:
            int r9 = r0.mEdgeType
            r11 = 1
            if (r9 != r11) goto L_0x0084
            android.graphics.Paint$Join r11 = android.graphics.Paint.Join.ROUND
            r6.setStrokeJoin(r11)
            float r11 = r0.mOutlineWidth
            r6.setStrokeWidth(r11)
            int r11 = r0.mEdgeColor
            r6.setColor((int) r11)
            android.graphics.Paint$Style r11 = android.graphics.Paint.Style.FILL_AND_STROKE
            r6.setStyle(r11)
            r11 = r10
        L_0x007c:
            if (r11 >= r5) goto L_0x0092
            r2.drawText(r1, r11, r11)
            int r11 = r11 + 1
            goto L_0x007c
        L_0x0084:
            r12 = 2
            if (r9 != r12) goto L_0x0095
            float r11 = r0.mShadowRadius
            float r12 = r0.mShadowOffsetX
            float r13 = r0.mShadowOffsetY
            int r14 = r0.mEdgeColor
            r6.setShadowLayer((float) r11, (float) r12, (float) r13, (int) r14)
        L_0x0092:
            r16 = r4
            goto L_0x00d1
        L_0x0095:
            r12 = 3
            if (r9 == r12) goto L_0x009b
            r13 = 4
            if (r9 != r13) goto L_0x0092
        L_0x009b:
            if (r9 != r12) goto L_0x009e
            goto L_0x009f
        L_0x009e:
            r11 = r10
        L_0x009f:
            r12 = -1
            if (r11 == 0) goto L_0x00a4
            r13 = r12
            goto L_0x00a6
        L_0x00a4:
            int r13 = r0.mEdgeColor
        L_0x00a6:
            if (r11 == 0) goto L_0x00ab
            int r12 = r0.mEdgeColor
        L_0x00ab:
            float r14 = r0.mShadowRadius
            r15 = 1073741824(0x40000000, float:2.0)
            float r14 = r14 / r15
            int r15 = r0.mForegroundColor
            r6.setColor((int) r15)
            android.graphics.Paint$Style r15 = android.graphics.Paint.Style.FILL
            r6.setStyle(r15)
            float r15 = r0.mShadowRadius
            float r10 = -r14
            r16 = r4
            float r4 = -r14
            r6.setShadowLayer((float) r15, (float) r10, (float) r4, (int) r13)
            r4 = 0
        L_0x00c4:
            if (r4 >= r5) goto L_0x00cc
            r2.drawText(r1, r4, r4)
            int r4 = r4 + 1
            goto L_0x00c4
        L_0x00cc:
            float r4 = r0.mShadowRadius
            r6.setShadowLayer((float) r4, (float) r14, (float) r14, (int) r12)
        L_0x00d1:
            int r4 = r0.mForegroundColor
            r6.setColor((int) r4)
            android.graphics.Paint$Style r4 = android.graphics.Paint.Style.FILL
            r6.setStyle(r4)
            r4 = 0
        L_0x00dc:
            if (r4 >= r5) goto L_0x00e4
            r2.drawText(r1, r4, r4)
            int r4 = r4 + 1
            goto L_0x00dc
        L_0x00e4:
            r4 = 0
            r10 = 0
            r6.setShadowLayer((float) r4, (float) r4, (float) r4, (int) r10)
            r1.restoreToCount(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.SubtitleView.onDraw(android.graphics.Canvas):void");
    }
}
