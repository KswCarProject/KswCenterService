package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.C3132R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class Toast {
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    static final String TAG = "Toast";
    static final boolean localLOGV = false;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private static INotificationManager sService;
    final Context mContext;
    @UnsupportedAppUsage
    int mDuration;
    View mNextView;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    final ITransientNotification$StubC3059TN mTN;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface Duration {
    }

    static /* synthetic */ INotificationManager access$100() {
        return getService();
    }

    public Toast(Context context) {
        this(context, null);
    }

    public Toast(Context context, Looper looper) {
        this.mContext = context;
        this.mTN = new ITransientNotification$StubC3059TN(context.getPackageName(), looper);
        this.mTN.f2466mY = context.getResources().getDimensionPixelSize(C3132R.dimen.toast_y_offset);
        this.mTN.mGravity = context.getResources().getInteger(C3132R.integer.config_toastDefaultGravity);
    }

    public void show() {
        if (this.mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }
        INotificationManager service = getService();
        String pkg = this.mContext.getOpPackageName();
        ITransientNotification$StubC3059TN tn = this.mTN;
        tn.mNextView = this.mNextView;
        int displayId = this.mContext.getDisplayId();
        try {
            service.enqueueToast(pkg, tn, this.mDuration, displayId);
        } catch (RemoteException e) {
        }
    }

    public void cancel() {
        this.mTN.cancel();
    }

    public void setView(View view) {
        this.mNextView = view;
    }

    public View getView() {
        return this.mNextView;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
        this.mTN.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        this.mTN.mHorizontalMargin = horizontalMargin;
        this.mTN.mVerticalMargin = verticalMargin;
    }

    public float getHorizontalMargin() {
        return this.mTN.mHorizontalMargin;
    }

    public float getVerticalMargin() {
        return this.mTN.mVerticalMargin;
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.mTN.mGravity = gravity;
        this.mTN.f2465mX = xOffset;
        this.mTN.f2466mY = yOffset;
    }

    public int getGravity() {
        return this.mTN.mGravity;
    }

    public int getXOffset() {
        return this.mTN.f2465mX;
    }

    public int getYOffset() {
        return this.mTN.f2466mY;
    }

    @UnsupportedAppUsage
    public WindowManager.LayoutParams getWindowParams() {
        return this.mTN.mParams;
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        return makeText(context, null, text, duration);
    }

    public static Toast makeText(Context context, Looper looper, CharSequence text, int duration) {
        Toast result = new Toast(context, looper);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(C3132R.layout.transient_notification, (ViewGroup) null);
        TextView tv = (TextView) v.findViewById(16908299);
        tv.setText(text);
        result.mNextView = v;
        result.mDuration = duration;
        return result;
    }

    public static Toast makeText(Context context, int resId, int duration) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    public void setText(int resId) {
        setText(this.mContext.getText(resId));
    }

    public void setText(CharSequence s) {
        if (this.mNextView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) this.mNextView.findViewById(16908299);
        if (tv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        tv.setText(s);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private static INotificationManager getService() {
        if (sService != null) {
            return sService;
        }
        sService = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        return sService;
    }

    /* renamed from: android.widget.Toast$TN */
    /* loaded from: classes4.dex */
    private static class ITransientNotification$StubC3059TN extends ITransientNotification.Stub {
        private static final int CANCEL = 2;
        private static final int HIDE = 1;
        static final long LONG_DURATION_TIMEOUT = 7000;
        static final long SHORT_DURATION_TIMEOUT = 4000;
        private static final int SHOW = 0;
        int mDuration;
        @UnsupportedAppUsage(maxTargetSdk = 28)
        int mGravity;
        final Handler mHandler;
        float mHorizontalMargin;
        @UnsupportedAppUsage(maxTargetSdk = 28)
        View mNextView;
        String mPackageName;
        @UnsupportedAppUsage(maxTargetSdk = 28)
        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        float mVerticalMargin;
        @UnsupportedAppUsage(maxTargetSdk = 28)
        View mView;
        WindowManager mWM;

        /* renamed from: mX */
        int f2465mX;
        @UnsupportedAppUsage(maxTargetSdk = 28)

        /* renamed from: mY */
        int f2466mY;

        ITransientNotification$StubC3059TN(String packageName, Looper looper) {
            WindowManager.LayoutParams params = this.mParams;
            params.height = -2;
            params.width = -2;
            params.format = -3;
            params.windowAnimations = 16973828;
            params.type = 2005;
            params.setTitle(Toast.TAG);
            params.flags = 152;
            this.mPackageName = packageName;
            if (looper == null && (looper = Looper.myLooper()) == null) {
                throw new RuntimeException("Can't toast on a thread that has not called Looper.prepare()");
            }
            this.mHandler = new Handler(looper, null) { // from class: android.widget.Toast.TN.1
                @Override // android.p007os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            IBinder token = (IBinder) msg.obj;
                            ITransientNotification$StubC3059TN.this.handleShow(token);
                            return;
                        case 1:
                            ITransientNotification$StubC3059TN.this.handleHide();
                            ITransientNotification$StubC3059TN.this.mNextView = null;
                            return;
                        case 2:
                            ITransientNotification$StubC3059TN.this.handleHide();
                            ITransientNotification$StubC3059TN.this.mNextView = null;
                            try {
                                Toast.access$100().cancelToast(ITransientNotification$StubC3059TN.this.mPackageName, ITransientNotification$StubC3059TN.this);
                                return;
                            } catch (RemoteException e) {
                                return;
                            }
                        default:
                            return;
                    }
                }
            };
        }

        @Override // android.app.ITransientNotification
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public void show(IBinder windowToken) {
            this.mHandler.obtainMessage(0, windowToken).sendToTarget();
        }

        @Override // android.app.ITransientNotification
        public void hide() {
            this.mHandler.obtainMessage(1).sendToTarget();
        }

        public void cancel() {
            this.mHandler.obtainMessage(2).sendToTarget();
        }

        public void handleShow(IBinder windowToken) {
            if (!this.mHandler.hasMessages(2) && !this.mHandler.hasMessages(1) && this.mView != this.mNextView) {
                handleHide();
                this.mView = this.mNextView;
                Context context = this.mView.getContext().getApplicationContext();
                String packageName = this.mView.getContext().getOpPackageName();
                if (context == null) {
                    context = this.mView.getContext();
                }
                this.mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Configuration config = this.mView.getContext().getResources().getConfiguration();
                int gravity = Gravity.getAbsoluteGravity(this.mGravity, config.getLayoutDirection());
                this.mParams.gravity = gravity;
                if ((gravity & 7) == 7) {
                    this.mParams.horizontalWeight = 1.0f;
                }
                if ((gravity & 112) == 112) {
                    this.mParams.verticalWeight = 1.0f;
                }
                this.mParams.f2425x = this.f2465mX;
                this.mParams.f2426y = this.f2466mY;
                this.mParams.verticalMargin = this.mVerticalMargin;
                this.mParams.horizontalMargin = this.mHorizontalMargin;
                this.mParams.packageName = packageName;
                this.mParams.hideTimeoutMilliseconds = this.mDuration == 1 ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
                this.mParams.token = windowToken;
                if (this.mView.getParent() != null) {
                    this.mWM.removeView(this.mView);
                }
                try {
                    this.mWM.addView(this.mView, this.mParams);
                    trySendAccessibilityEvent();
                } catch (WindowManager.BadTokenException e) {
                }
            }
        }

        private void trySendAccessibilityEvent() {
            AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mView.getContext());
            if (!accessibilityManager.isEnabled()) {
                return;
            }
            AccessibilityEvent event = AccessibilityEvent.obtain(64);
            event.setClassName(getClass().getName());
            event.setPackageName(this.mView.getContext().getPackageName());
            this.mView.dispatchPopulateAccessibilityEvent(event);
            accessibilityManager.sendAccessibilityEvent(event);
        }

        @UnsupportedAppUsage
        public void handleHide() {
            if (this.mView != null) {
                if (this.mView.getParent() != null) {
                    this.mWM.removeViewImmediate(this.mView);
                }
                try {
                    Toast.access$100().finishToken(this.mPackageName, this);
                } catch (RemoteException e) {
                }
                this.mView = null;
            }
        }
    }
}
