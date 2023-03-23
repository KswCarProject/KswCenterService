package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.wits.pms.R;
import java.util.Calendar;

public class ClockView extends View {
    private static String TAG = ClockView.class.getSimpleName();
    private Bitmap mClockBitmap;
    private int mClockBitmapHeight;
    private int mClockBitmapWidth;
    private int mClockCenterX;
    private int mClockCenterY;
    private int mClockX;
    private int mClockY;
    private MyTime mCurTime;
    private Bitmap mHourBitmap;
    private int mHourBitmapOffsetY;
    private int mHourBitmapPosX;
    private int mHourBitmapPosY;
    private Bitmap mMinuteBitmap;
    private int mMinuteBitmapOffsetY;
    private int mMinuteBitmapPosX;
    private int mMinuteBitmapPosY;
    private Bitmap mSecondBitmap;
    private int mSecondBitmapOffsetY;
    private int mSecondBitmapPosX;
    private int mSecondBitmapPosY;
    private boolean mViewInitComplete;
    private Paint paint;
    WindowManager windowManager;

    class MyTime {
        private Calendar mCalendar;
        int mHour = 0;
        int mHourDegree = 0;
        int mMinute = 0;
        int mMinuteDegree = 0;
        int mSecond = 0;
        int mSecondDegree = 0;

        MyTime() {
        }

        public void updateTime() {
            long time = System.currentTimeMillis();
            this.mCalendar = Calendar.getInstance();
            this.mCalendar.setTimeInMillis(time);
            this.mHour = this.mCalendar.get(11);
            this.mMinute = this.mCalendar.get(12);
            this.mSecond = this.mCalendar.get(13);
            this.mSecondDegree = this.mSecond * 6;
            this.mMinuteDegree = this.mMinute * 6;
            this.mHourDegree = ((this.mHour % 12) * 30) + ((this.mMinute * 30) / 60);
        }
    }

    public ClockView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, (AttributeSet) null, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mViewInitComplete = false;
        this.windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        this.mHourBitmapOffsetY = 18;
        this.mMinuteBitmapOffsetY = 18;
        this.mSecondBitmapOffsetY = 33;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.mClockBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.clock_bk);
        this.mClockBitmapWidth = this.mClockBitmap.getWidth();
        this.mClockBitmapHeight = this.mClockBitmap.getHeight();
        DisplayMetrics metrics = new DisplayMetrics();
        this.windowManager.getDefaultDisplay().getMetrics(metrics);
        this.mClockX = (metrics.widthPixels / 2) - (this.mClockBitmapWidth / 2);
        this.mClockY = (metrics.heightPixels / 2) - (this.mClockBitmapHeight / 2);
        this.mClockCenterX = (this.mClockX + (this.mClockBitmapWidth / 2)) - 2;
        this.mClockCenterY = this.mClockY + (this.mClockBitmapHeight / 2);
        this.mHourBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hour);
        this.mMinuteBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.minute);
        this.mSecondBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.second);
        calcHandPosition();
        this.mCurTime = new MyTime();
        this.mCurTime.updateTime();
        this.mViewInitComplete = true;
    }

    public void calcHandPosition() {
        if (this.mHourBitmap != null) {
            int w = this.mHourBitmap.getWidth();
            int h = this.mHourBitmap.getHeight();
            this.mHourBitmapPosX = (-w) / 2;
            this.mHourBitmapPosY = (-h) + this.mHourBitmapOffsetY;
        }
        if (this.mMinuteBitmap != null) {
            int w2 = this.mMinuteBitmap.getWidth();
            int h2 = this.mMinuteBitmap.getHeight();
            this.mMinuteBitmapPosX = (-w2) / 2;
            this.mMinuteBitmapPosY = (-h2) + this.mMinuteBitmapOffsetY;
        }
        if (this.mSecondBitmap != null) {
            int w3 = this.mSecondBitmap.getWidth();
            int h3 = this.mSecondBitmap.getHeight();
            this.mSecondBitmapPosX = (-w3) / 2;
            this.mSecondBitmapPosY = (-h3) + this.mSecondBitmapOffsetY;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewInitComplete) {
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            drawClockBk(canvas);
            drawHourHand(canvas);
            drawMinuteHand(canvas);
            drawSecondHand(canvas);
            this.mCurTime.updateTime();
            postInvalidate();
        }
    }

    public void drawClockBk(Canvas canvas) {
        if (this.mClockBitmap != null) {
            canvas.drawBitmap(this.mClockBitmap, (float) this.mClockX, (float) this.mClockY, (Paint) null);
        }
    }

    private void drawHourHand(Canvas canvas) {
        if (this.mHourBitmap != null) {
            canvas.save();
            canvas.translate((float) this.mClockCenterX, (float) this.mClockCenterY);
            canvas.rotate((float) this.mCurTime.mHourDegree);
            canvas.drawBitmap(this.mHourBitmap, (float) this.mHourBitmapPosX, (float) this.mHourBitmapPosY, this.paint);
            canvas.restore();
        }
    }

    public void drawMinuteHand(Canvas canvas) {
        if (this.mMinuteBitmap != null) {
            canvas.save();
            canvas.translate((float) this.mClockCenterX, (float) this.mClockCenterY);
            canvas.rotate((float) this.mCurTime.mMinuteDegree);
            canvas.drawBitmap(this.mMinuteBitmap, (float) this.mMinuteBitmapPosX, (float) this.mMinuteBitmapPosY, this.paint);
            canvas.restore();
        }
    }

    public void drawSecondHand(Canvas canvas) {
        if (this.mSecondBitmap != null) {
            canvas.save();
            canvas.translate((float) this.mClockCenterX, (float) this.mClockCenterY);
            canvas.rotate((float) this.mCurTime.mSecondDegree);
            canvas.drawBitmap(this.mSecondBitmap, (float) this.mSecondBitmapPosX, (float) this.mSecondBitmapPosY, this.paint);
            canvas.restore();
        }
    }
}
