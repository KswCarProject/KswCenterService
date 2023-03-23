package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.MathUtils;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import java.text.NumberFormat;
import java.util.Locale;
import libcore.icu.LocaleData;

class SimpleMonthView extends View {
    private static final int DAYS_IN_WEEK = 7;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_WEEK_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String MONTH_YEAR_FORMAT = "MMMMy";
    private static final int SELECTED_HIGHLIGHT_ALPHA = 176;
    /* access modifiers changed from: private */
    public int mActivatedDay;
    private final Calendar mCalendar;
    private int mCellWidth;
    /* access modifiers changed from: private */
    public final NumberFormat mDayFormatter;
    private int mDayHeight;
    private final Paint mDayHighlightPaint;
    private final Paint mDayHighlightSelectorPaint;
    private int mDayOfWeekHeight;
    private final String[] mDayOfWeekLabels;
    private final TextPaint mDayOfWeekPaint;
    private int mDayOfWeekStart;
    private final TextPaint mDayPaint;
    private final Paint mDaySelectorPaint;
    private int mDaySelectorRadius;
    private ColorStateList mDayTextColor;
    /* access modifiers changed from: private */
    public int mDaysInMonth;
    private final int mDesiredCellWidth;
    private final int mDesiredDayHeight;
    private final int mDesiredDayOfWeekHeight;
    private final int mDesiredDaySelectorRadius;
    private final int mDesiredMonthHeight;
    private int mEnabledDayEnd;
    private int mEnabledDayStart;
    private int mHighlightedDay;
    private boolean mIsTouchHighlighted;
    private final Locale mLocale;
    /* access modifiers changed from: private */
    public int mMonth;
    private int mMonthHeight;
    private final TextPaint mMonthPaint;
    private String mMonthYearLabel;
    private OnDayClickListener mOnDayClickListener;
    private int mPaddedHeight;
    private int mPaddedWidth;
    private int mPreviouslyHighlightedDay;
    private int mToday;
    private final MonthViewTouchHelper mTouchHelper;
    private int mWeekStart;
    /* access modifiers changed from: private */
    public int mYear;

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, Calendar calendar);
    }

    public SimpleMonthView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SimpleMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 16843612);
    }

    public SimpleMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SimpleMonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mMonthPaint = new TextPaint();
        this.mDayOfWeekPaint = new TextPaint();
        this.mDayPaint = new TextPaint();
        this.mDaySelectorPaint = new Paint();
        this.mDayHighlightPaint = new Paint();
        this.mDayHighlightSelectorPaint = new Paint();
        this.mDayOfWeekLabels = new String[7];
        this.mActivatedDay = -1;
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mHighlightedDay = -1;
        this.mPreviouslyHighlightedDay = -1;
        this.mIsTouchHighlighted = false;
        Resources res = context.getResources();
        this.mDesiredMonthHeight = res.getDimensionPixelSize(R.dimen.date_picker_month_height);
        this.mDesiredDayOfWeekHeight = res.getDimensionPixelSize(R.dimen.date_picker_day_of_week_height);
        this.mDesiredDayHeight = res.getDimensionPixelSize(R.dimen.date_picker_day_height);
        this.mDesiredCellWidth = res.getDimensionPixelSize(R.dimen.date_picker_day_width);
        this.mDesiredDaySelectorRadius = res.getDimensionPixelSize(R.dimen.date_picker_day_selector_radius);
        this.mTouchHelper = new MonthViewTouchHelper(this);
        setAccessibilityDelegate(this.mTouchHelper);
        setImportantForAccessibility(1);
        this.mLocale = res.getConfiguration().locale;
        this.mCalendar = Calendar.getInstance(this.mLocale);
        this.mDayFormatter = NumberFormat.getIntegerInstance(this.mLocale);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        initPaints(res);
    }

    private void updateMonthYearLabel() {
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormat.getBestDateTimePattern(this.mLocale, MONTH_YEAR_FORMAT), this.mLocale);
        formatter.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        this.mMonthYearLabel = formatter.format(this.mCalendar.getTime());
    }

    private void updateDayOfWeekLabels() {
        String[] tinyWeekdayNames = LocaleData.get(this.mLocale).tinyWeekdayNames;
        for (int i = 0; i < 7; i++) {
            this.mDayOfWeekLabels[i] = tinyWeekdayNames[(((this.mWeekStart + i) - 1) % 7) + 1];
        }
    }

    private ColorStateList applyTextAppearance(Paint p, int resId) {
        TypedArray ta = this.mContext.obtainStyledAttributes((AttributeSet) null, R.styleable.TextAppearance, 0, resId);
        String fontFamily = ta.getString(12);
        if (fontFamily != null) {
            p.setTypeface(Typeface.create(fontFamily, 0));
        }
        p.setTextSize((float) ta.getDimensionPixelSize(0, (int) p.getTextSize()));
        ColorStateList textColor = ta.getColorStateList(3);
        if (textColor != null) {
            p.setColor(textColor.getColorForState(ENABLED_STATE_SET, 0));
        }
        ta.recycle();
        return textColor;
    }

    public int getMonthHeight() {
        return this.mMonthHeight;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    public void setMonthTextAppearance(int resId) {
        applyTextAppearance(this.mMonthPaint, resId);
        invalidate();
    }

    public void setDayOfWeekTextAppearance(int resId) {
        applyTextAppearance(this.mDayOfWeekPaint, resId);
        invalidate();
    }

    public void setDayTextAppearance(int resId) {
        ColorStateList textColor = applyTextAppearance(this.mDayPaint, resId);
        if (textColor != null) {
            this.mDayTextColor = textColor;
        }
        invalidate();
    }

    private void initPaints(Resources res) {
        String monthTypeface = res.getString(R.string.date_picker_month_typeface);
        String dayOfWeekTypeface = res.getString(R.string.date_picker_day_of_week_typeface);
        String dayTypeface = res.getString(R.string.date_picker_day_typeface);
        int monthTextSize = res.getDimensionPixelSize(R.dimen.date_picker_month_text_size);
        int dayOfWeekTextSize = res.getDimensionPixelSize(R.dimen.date_picker_day_of_week_text_size);
        int dayTextSize = res.getDimensionPixelSize(R.dimen.date_picker_day_text_size);
        this.mMonthPaint.setAntiAlias(true);
        this.mMonthPaint.setTextSize((float) monthTextSize);
        this.mMonthPaint.setTypeface(Typeface.create(monthTypeface, 0));
        this.mMonthPaint.setTextAlign(Paint.Align.CENTER);
        this.mMonthPaint.setStyle(Paint.Style.FILL);
        this.mDayOfWeekPaint.setAntiAlias(true);
        this.mDayOfWeekPaint.setTextSize((float) dayOfWeekTextSize);
        this.mDayOfWeekPaint.setTypeface(Typeface.create(dayOfWeekTypeface, 0));
        this.mDayOfWeekPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayOfWeekPaint.setStyle(Paint.Style.FILL);
        this.mDaySelectorPaint.setAntiAlias(true);
        this.mDaySelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightPaint.setAntiAlias(true);
        this.mDayHighlightPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightSelectorPaint.setAntiAlias(true);
        this.mDayHighlightSelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayPaint.setAntiAlias(true);
        this.mDayPaint.setTextSize((float) dayTextSize);
        this.mDayPaint.setTypeface(Typeface.create(dayTypeface, 0));
        this.mDayPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayPaint.setStyle(Paint.Style.FILL);
    }

    /* access modifiers changed from: package-private */
    public void setMonthTextColor(ColorStateList monthTextColor) {
        this.mMonthPaint.setColor(monthTextColor.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setDayOfWeekTextColor(ColorStateList dayOfWeekTextColor) {
        this.mDayOfWeekPaint.setColor(dayOfWeekTextColor.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setDayTextColor(ColorStateList dayTextColor) {
        this.mDayTextColor = dayTextColor;
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setDaySelectorColor(ColorStateList dayBackgroundColor) {
        int activatedColor = dayBackgroundColor.getColorForState(StateSet.get(40), 0);
        this.mDaySelectorPaint.setColor(activatedColor);
        this.mDayHighlightSelectorPaint.setColor(activatedColor);
        this.mDayHighlightSelectorPaint.setAlpha(176);
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setDayHighlightColor(ColorStateList dayHighlightColor) {
        this.mDayHighlightPaint.setColor(dayHighlightColor.getColorForState(StateSet.get(24), 0));
        invalidate();
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.mOnDayClickListener = listener;
    }

    public boolean dispatchHoverEvent(MotionEvent event) {
        return this.mTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX() + 0.5f);
        int y = (int) (event.getY() + 0.5f);
        int action = event.getAction();
        switch (action) {
            case 0:
            case 2:
                int touchedItem = getDayAtLocation(x, y);
                this.mIsTouchHighlighted = true;
                if (this.mHighlightedDay != touchedItem) {
                    this.mHighlightedDay = touchedItem;
                    this.mPreviouslyHighlightedDay = touchedItem;
                    invalidate();
                }
                if (action != 0 || touchedItem >= 0) {
                    return true;
                }
                return false;
            case 1:
                onDayClicked(getDayAtLocation(x, y));
                break;
            case 3:
                break;
        }
        this.mHighlightedDay = -1;
        this.mIsTouchHighlighted = false;
        invalidate();
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0091  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int r7, android.view.KeyEvent r8) {
        /*
            r6 = this;
            r0 = 0
            int r1 = r8.getKeyCode()
            r2 = 61
            r3 = 1
            if (r1 == r2) goto L_0x006a
            r2 = 66
            if (r1 == r2) goto L_0x005f
            r2 = 7
            switch(r1) {
                case 19: goto L_0x004b;
                case 20: goto L_0x0034;
                case 21: goto L_0x0025;
                case 22: goto L_0x0014;
                case 23: goto L_0x005f;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x0096
        L_0x0014:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L_0x0096
            boolean r1 = r6.isLayoutRtl()
            r1 = r1 ^ r3
            boolean r0 = r6.moveOneDay(r1)
            goto L_0x0096
        L_0x0025:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L_0x0096
            boolean r1 = r6.isLayoutRtl()
            boolean r0 = r6.moveOneDay(r1)
            goto L_0x0096
        L_0x0034:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L_0x0096
            r6.ensureFocusedDay()
            int r1 = r6.mHighlightedDay
            int r4 = r6.mDaysInMonth
            int r4 = r4 - r2
            if (r1 > r4) goto L_0x0096
            int r1 = r6.mHighlightedDay
            int r1 = r1 + r2
            r6.mHighlightedDay = r1
            r0 = 1
            goto L_0x0096
        L_0x004b:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L_0x0096
            r6.ensureFocusedDay()
            int r1 = r6.mHighlightedDay
            if (r1 <= r2) goto L_0x0096
            int r1 = r6.mHighlightedDay
            int r1 = r1 - r2
            r6.mHighlightedDay = r1
            r0 = 1
            goto L_0x0096
        L_0x005f:
            int r1 = r6.mHighlightedDay
            r2 = -1
            if (r1 == r2) goto L_0x0096
            int r1 = r6.mHighlightedDay
            r6.onDayClicked(r1)
            return r3
        L_0x006a:
            r1 = 0
            boolean r2 = r8.hasNoModifiers()
            if (r2 == 0) goto L_0x0073
            r1 = 2
            goto L_0x007a
        L_0x0073:
            boolean r2 = r8.hasModifiers(r3)
            if (r2 == 0) goto L_0x007a
            r1 = 1
        L_0x007a:
            if (r1 == 0) goto L_0x0096
            android.view.ViewParent r2 = r6.getParent()
            r4 = r6
        L_0x0081:
            android.view.View r4 = r4.focusSearch(r1)
            if (r4 == 0) goto L_0x008f
            if (r4 == r6) goto L_0x008f
            android.view.ViewParent r5 = r4.getParent()
            if (r5 == r2) goto L_0x0081
        L_0x008f:
            if (r4 == 0) goto L_0x0095
            r4.requestFocus()
            return r3
        L_0x0095:
        L_0x0096:
            if (r0 == 0) goto L_0x009c
            r6.invalidate()
            return r3
        L_0x009c:
            boolean r1 = super.onKeyDown(r7, r8)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SimpleMonthView.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    private boolean moveOneDay(boolean positive) {
        ensureFocusedDay();
        if (positive) {
            if (isLastDayOfWeek(this.mHighlightedDay) || this.mHighlightedDay >= this.mDaysInMonth) {
                return false;
            }
            this.mHighlightedDay++;
            return true;
        } else if (isFirstDayOfWeek(this.mHighlightedDay) || this.mHighlightedDay <= 1) {
            return false;
        } else {
            this.mHighlightedDay--;
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {
            int offset = findDayOffset();
            int i = 1;
            if (direction == 17) {
                this.mHighlightedDay = Math.min(this.mDaysInMonth, ((findClosestRow(previouslyFocusedRect) + 1) * 7) - offset);
            } else if (direction == 33) {
                int day = (findClosestColumn(previouslyFocusedRect) - offset) + (((this.mDaysInMonth + offset) / 7) * 7) + 1;
                this.mHighlightedDay = day > this.mDaysInMonth ? day - 7 : day;
            } else if (direction == 66) {
                int col = findClosestRow(previouslyFocusedRect);
                if (col != 0) {
                    i = 1 + ((col * 7) - offset);
                }
                this.mHighlightedDay = i;
            } else if (direction == 130) {
                int day2 = (findClosestColumn(previouslyFocusedRect) - offset) + 1;
                this.mHighlightedDay = day2 < 1 ? day2 + 7 : day2;
            }
            ensureFocusedDay();
            invalidate();
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    private int findClosestRow(Rect previouslyFocusedRect) {
        if (previouslyFocusedRect == null) {
            return 3;
        }
        if (this.mDayHeight == 0) {
            return 0;
        }
        int centerY = previouslyFocusedRect.centerY();
        TextPaint p = this.mDayPaint;
        int headerHeight = this.mMonthHeight + this.mDayOfWeekHeight;
        int rowHeight = this.mDayHeight;
        int row = Math.round(((float) ((int) (((float) centerY) - (((float) ((rowHeight / 2) + headerHeight)) - ((p.ascent() + p.descent()) / 2.0f))))) / ((float) rowHeight));
        int maxDay = findDayOffset() + this.mDaysInMonth;
        return MathUtils.constrain(row, 0, (maxDay / 7) - (maxDay % 7 == 0 ? 1 : 0));
    }

    private int findClosestColumn(Rect previouslyFocusedRect) {
        if (previouslyFocusedRect == null) {
            return 3;
        }
        if (this.mCellWidth == 0) {
            return 0;
        }
        int columnFromLeft = MathUtils.constrain((previouslyFocusedRect.centerX() - this.mPaddingLeft) / this.mCellWidth, 0, 6);
        return isLayoutRtl() ? (7 - columnFromLeft) - 1 : columnFromLeft;
    }

    public void getFocusedRect(Rect r) {
        if (this.mHighlightedDay > 0) {
            getBoundsForDay(this.mHighlightedDay, r);
        } else {
            super.getFocusedRect(r);
        }
    }

    /* access modifiers changed from: protected */
    public void onFocusLost() {
        if (!this.mIsTouchHighlighted) {
            this.mPreviouslyHighlightedDay = this.mHighlightedDay;
            this.mHighlightedDay = -1;
            invalidate();
        }
        super.onFocusLost();
    }

    private void ensureFocusedDay() {
        if (this.mHighlightedDay == -1) {
            if (this.mPreviouslyHighlightedDay != -1) {
                this.mHighlightedDay = this.mPreviouslyHighlightedDay;
            } else if (this.mActivatedDay != -1) {
                this.mHighlightedDay = this.mActivatedDay;
            } else {
                this.mHighlightedDay = 1;
            }
        }
    }

    private boolean isFirstDayOfWeek(int day) {
        return ((findDayOffset() + day) - 1) % 7 == 0;
    }

    private boolean isLastDayOfWeek(int day) {
        return (findDayOffset() + day) % 7 == 0;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        canvas.translate((float) paddingLeft, (float) paddingTop);
        drawMonth(canvas);
        drawDaysOfWeek(canvas);
        drawDays(canvas);
        canvas.translate((float) (-paddingLeft), (float) (-paddingTop));
    }

    private void drawMonth(Canvas canvas) {
        float lineHeight = this.mMonthPaint.ascent() + this.mMonthPaint.descent();
        canvas.drawText(this.mMonthYearLabel, ((float) this.mPaddedWidth) / 2.0f, (((float) this.mMonthHeight) - lineHeight) / 2.0f, this.mMonthPaint);
    }

    public String getMonthYearLabel() {
        return this.mMonthYearLabel;
    }

    private void drawDaysOfWeek(Canvas canvas) {
        int colCenterRtl;
        TextPaint p = this.mDayOfWeekPaint;
        int headerHeight = this.mMonthHeight;
        int rowHeight = this.mDayOfWeekHeight;
        int colWidth = this.mCellWidth;
        float halfLineHeight = (p.ascent() + p.descent()) / 2.0f;
        int rowCenter = (rowHeight / 2) + headerHeight;
        for (int col = 0; col < 7; col++) {
            int colCenter = (colWidth * col) + (colWidth / 2);
            if (isLayoutRtl()) {
                colCenterRtl = this.mPaddedWidth - colCenter;
            } else {
                colCenterRtl = colCenter;
            }
            canvas.drawText(this.mDayOfWeekLabels[col], (float) colCenterRtl, ((float) rowCenter) - halfLineHeight, p);
        }
    }

    private void drawDays(Canvas canvas) {
        int colCenterRtl;
        int stateMask;
        int colWidth;
        int headerHeight;
        int dayTextColor;
        int stateMask2;
        Paint paint;
        Canvas canvas2 = canvas;
        TextPaint p = this.mDayPaint;
        int headerHeight2 = this.mMonthHeight + this.mDayOfWeekHeight;
        int rowHeight = this.mDayHeight;
        int colWidth2 = this.mCellWidth;
        float halfLineHeight = (p.ascent() + p.descent()) / 2.0f;
        int rowCenter = (rowHeight / 2) + headerHeight2;
        int day = 1;
        int col = findDayOffset();
        while (day <= this.mDaysInMonth) {
            int colCenter = (colWidth2 * col) + (colWidth2 / 2);
            if (isLayoutRtl()) {
                colCenterRtl = this.mPaddedWidth - colCenter;
            } else {
                colCenterRtl = colCenter;
            }
            int stateMask3 = 0;
            boolean isDayEnabled = isDayEnabled(day);
            if (isDayEnabled) {
                stateMask3 = 0 | 8;
            }
            boolean isDayToday = true;
            boolean isDayActivated = this.mActivatedDay == day;
            boolean isDayHighlighted = this.mHighlightedDay == day;
            if (isDayActivated) {
                int stateMask4 = stateMask3 | 32;
                if (isDayHighlighted) {
                    headerHeight = headerHeight2;
                    paint = this.mDayHighlightSelectorPaint;
                } else {
                    headerHeight = headerHeight2;
                    paint = this.mDaySelectorPaint;
                }
                colWidth = colWidth2;
                int i = colCenter;
                stateMask = stateMask4;
                canvas2.drawCircle((float) colCenterRtl, (float) rowCenter, (float) this.mDaySelectorRadius, paint);
            } else {
                headerHeight = headerHeight2;
                colWidth = colWidth2;
                int i2 = colCenter;
                if (isDayHighlighted) {
                    int stateMask5 = stateMask3 | 16;
                    if (isDayEnabled) {
                        stateMask2 = stateMask5;
                        canvas2.drawCircle((float) colCenterRtl, (float) rowCenter, (float) this.mDaySelectorRadius, this.mDayHighlightPaint);
                    } else {
                        stateMask2 = stateMask5;
                    }
                    stateMask = stateMask2;
                } else {
                    stateMask = stateMask3;
                }
            }
            if (this.mToday != day) {
                isDayToday = false;
            }
            if (!isDayToday || isDayActivated) {
                dayTextColor = this.mDayTextColor.getColorForState(StateSet.get(stateMask), 0);
            } else {
                dayTextColor = this.mDaySelectorPaint.getColor();
            }
            p.setColor(dayTextColor);
            boolean z = isDayEnabled;
            canvas2.drawText(this.mDayFormatter.format((long) day), (float) colCenterRtl, ((float) rowCenter) - halfLineHeight, p);
            col++;
            if (col == 7) {
                col = 0;
                rowCenter += rowHeight;
            }
            day++;
            headerHeight2 = headerHeight;
            colWidth2 = colWidth;
        }
        int i3 = colWidth2;
    }

    /* access modifiers changed from: private */
    public boolean isDayEnabled(int day) {
        return day >= this.mEnabledDayStart && day <= this.mEnabledDayEnd;
    }

    /* access modifiers changed from: private */
    public boolean isValidDayOfMonth(int day) {
        return day >= 1 && day <= this.mDaysInMonth;
    }

    private static boolean isValidDayOfWeek(int day) {
        return day >= 1 && day <= 7;
    }

    private static boolean isValidMonth(int month) {
        return month >= 0 && month <= 11;
    }

    public void setSelectedDay(int dayOfMonth) {
        this.mActivatedDay = dayOfMonth;
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    public void setFirstDayOfWeek(int weekStart) {
        if (isValidDayOfWeek(weekStart)) {
            this.mWeekStart = weekStart;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setMonthParams(int selectedDay, int month, int year, int weekStart, int enabledDayStart, int enabledDayEnd) {
        this.mActivatedDay = selectedDay;
        if (isValidMonth(month)) {
            this.mMonth = month;
        }
        this.mYear = year;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        if (isValidDayOfWeek(weekStart)) {
            this.mWeekStart = weekStart;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        Calendar today = Calendar.getInstance();
        this.mToday = -1;
        this.mDaysInMonth = getDaysInMonth(this.mMonth, this.mYear);
        for (int i = 0; i < this.mDaysInMonth; i++) {
            int day = i + 1;
            if (sameDay(day, today)) {
                this.mToday = day;
            }
        }
        this.mEnabledDayStart = MathUtils.constrain(enabledDayStart, 1, this.mDaysInMonth);
        this.mEnabledDayEnd = MathUtils.constrain(enabledDayEnd, this.mEnabledDayStart, this.mDaysInMonth);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    private static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return year % 4 == 0 ? 29 : 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    private boolean sameDay(int day, Calendar today) {
        if (this.mYear == today.get(1) && this.mMonth == today.get(2) && day == today.get(5)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize((this.mDesiredCellWidth * 7) + getPaddingStart() + getPaddingEnd(), widthMeasureSpec), resolveSize((this.mDesiredDayHeight * 6) + this.mDesiredDayOfWeekHeight + this.mDesiredMonthHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            int w = right - left;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            int paddedWidth = (w - paddingRight) - paddingLeft;
            int paddedHeight = ((bottom - top) - paddingBottom) - paddingTop;
            if (paddedWidth == this.mPaddedWidth) {
            } else if (paddedHeight == this.mPaddedHeight) {
                int i = w;
            } else {
                this.mPaddedWidth = paddedWidth;
                this.mPaddedHeight = paddedHeight;
                int measuredPaddedHeight = (getMeasuredHeight() - paddingTop) - paddingBottom;
                float scaleH = ((float) paddedHeight) / ((float) measuredPaddedHeight);
                int monthHeight = (int) (((float) this.mDesiredMonthHeight) * scaleH);
                int i2 = measuredPaddedHeight;
                int cellWidth = this.mPaddedWidth / 7;
                this.mMonthHeight = monthHeight;
                int i3 = monthHeight;
                this.mDayOfWeekHeight = (int) (((float) this.mDesiredDayOfWeekHeight) * scaleH);
                this.mDayHeight = (int) (((float) this.mDesiredDayHeight) * scaleH);
                this.mCellWidth = cellWidth;
                int i4 = cellWidth;
                float f = scaleH;
                int i5 = w;
                this.mDaySelectorRadius = Math.min(this.mDesiredDaySelectorRadius, Math.min((cellWidth / 2) + Math.min(paddingLeft, paddingRight), (this.mDayHeight / 2) + paddingBottom));
                this.mTouchHelper.invalidateRoot();
            }
        }
    }

    private int findDayOffset() {
        int offset = this.mDayOfWeekStart - this.mWeekStart;
        if (this.mDayOfWeekStart < this.mWeekStart) {
            return offset + 7;
        }
        return offset;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000e, code lost:
        r2 = r10.mMonthHeight + r10.mDayOfWeekHeight;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getDayAtLocation(int r11, int r12) {
        /*
            r10 = this;
            int r0 = r10.getPaddingLeft()
            int r0 = r11 - r0
            r1 = -1
            if (r0 < 0) goto L_0x0048
            int r2 = r10.mPaddedWidth
            if (r0 < r2) goto L_0x000e
            goto L_0x0048
        L_0x000e:
            int r2 = r10.mMonthHeight
            int r3 = r10.mDayOfWeekHeight
            int r2 = r2 + r3
            int r3 = r10.getPaddingTop()
            int r3 = r12 - r3
            if (r3 < r2) goto L_0x0047
            int r4 = r10.mPaddedHeight
            if (r3 < r4) goto L_0x0020
            goto L_0x0047
        L_0x0020:
            boolean r4 = r10.isLayoutRtl()
            if (r4 == 0) goto L_0x002a
            int r4 = r10.mPaddedWidth
            int r4 = r4 - r0
            goto L_0x002b
        L_0x002a:
            r4 = r0
        L_0x002b:
            int r5 = r3 - r2
            int r6 = r10.mDayHeight
            int r5 = r5 / r6
            int r6 = r4 * 7
            int r7 = r10.mPaddedWidth
            int r6 = r6 / r7
            int r7 = r5 * 7
            int r7 = r7 + r6
            int r8 = r7 + 1
            int r9 = r10.findDayOffset()
            int r8 = r8 - r9
            boolean r9 = r10.isValidDayOfMonth(r8)
            if (r9 != 0) goto L_0x0046
            return r1
        L_0x0046:
            return r8
        L_0x0047:
            return r1
        L_0x0048:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SimpleMonthView.getDayAtLocation(int, int):int");
    }

    public boolean getBoundsForDay(int id, Rect outBounds) {
        int left;
        if (!isValidDayOfMonth(id)) {
            return false;
        }
        int index = (id - 1) + findDayOffset();
        int col = index % 7;
        int colWidth = this.mCellWidth;
        if (isLayoutRtl()) {
            left = (getWidth() - getPaddingRight()) - ((col + 1) * colWidth);
        } else {
            left = getPaddingLeft() + (col * colWidth);
        }
        int rowHeight = this.mDayHeight;
        int top = getPaddingTop() + this.mMonthHeight + this.mDayOfWeekHeight + ((index / 7) * rowHeight);
        outBounds.set(left, top, left + colWidth, top + rowHeight);
        return true;
    }

    /* access modifiers changed from: private */
    public boolean onDayClicked(int day) {
        if (!isValidDayOfMonth(day) || !isDayEnabled(day)) {
            return false;
        }
        if (this.mOnDayClickListener != null) {
            Calendar date = Calendar.getInstance();
            date.set(this.mYear, this.mMonth, day);
            this.mOnDayClickListener.onDayClick(this, date);
        }
        this.mTouchHelper.sendEventForVirtualView(day, 1);
        return true;
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (!isEnabled()) {
            return null;
        }
        if (getDayAtLocation((int) (event.getX() + 0.5f), (int) (event.getY() + 0.5f)) >= 0) {
            return PointerIcon.getSystemIcon(getContext(), 1002);
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    private class MonthViewTouchHelper extends ExploreByTouchHelper {
        private static final String DATE_FORMAT = "dd MMMM yyyy";
        private final Calendar mTempCalendar = Calendar.getInstance();
        private final Rect mTempRect = new Rect();

        public MonthViewTouchHelper(View host) {
            super(host);
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float x, float y) {
            int day = SimpleMonthView.this.getDayAtLocation((int) (x + 0.5f), (int) (0.5f + y));
            if (day != -1) {
                return day;
            }
            return Integer.MIN_VALUE;
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(IntArray virtualViewIds) {
            for (int day = 1; day <= SimpleMonthView.this.mDaysInMonth; day++) {
                virtualViewIds.add(day);
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
            event.setContentDescription(getDayDescription(virtualViewId));
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo node) {
            if (!SimpleMonthView.this.getBoundsForDay(virtualViewId, this.mTempRect)) {
                this.mTempRect.setEmpty();
                node.setContentDescription("");
                node.setBoundsInParent(this.mTempRect);
                node.setVisibleToUser(false);
                return;
            }
            node.setText(getDayText(virtualViewId));
            node.setContentDescription(getDayDescription(virtualViewId));
            node.setBoundsInParent(this.mTempRect);
            boolean isDayEnabled = SimpleMonthView.this.isDayEnabled(virtualViewId);
            if (isDayEnabled) {
                node.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            }
            node.setEnabled(isDayEnabled);
            if (virtualViewId == SimpleMonthView.this.mActivatedDay) {
                node.setChecked(true);
            }
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (action != 16) {
                return false;
            }
            return SimpleMonthView.this.onDayClicked(virtualViewId);
        }

        private CharSequence getDayDescription(int id) {
            if (!SimpleMonthView.this.isValidDayOfMonth(id)) {
                return "";
            }
            this.mTempCalendar.set(SimpleMonthView.this.mYear, SimpleMonthView.this.mMonth, id);
            return DateFormat.format((CharSequence) DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
        }

        private CharSequence getDayText(int id) {
            if (SimpleMonthView.this.isValidDayOfMonth(id)) {
                return SimpleMonthView.this.mDayFormatter.format((long) id);
            }
            return null;
        }
    }
}
