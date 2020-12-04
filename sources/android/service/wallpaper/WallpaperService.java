package android.service.wallpaper;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.SettingsStringUtil;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.util.MergedConfiguration;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.HandlerCaller;
import com.android.internal.view.BaseIWindow;
import com.android.internal.view.BaseSurfaceHolder;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public abstract class WallpaperService extends Service {
    static final boolean DEBUG = false;
    private static final int DO_ATTACH = 10;
    private static final int DO_DETACH = 20;
    private static final int DO_IN_AMBIENT_MODE = 50;
    private static final int DO_SET_DESIRED_SIZE = 30;
    private static final int DO_SET_DISPLAY_PADDING = 40;
    private static final int MSG_REQUEST_WALLPAPER_COLORS = 10050;
    private static final int MSG_TOUCH_EVENT = 10040;
    private static final int MSG_UPDATE_SURFACE = 10000;
    private static final int MSG_VISIBILITY_CHANGED = 10010;
    private static final int MSG_WALLPAPER_COMMAND = 10025;
    private static final int MSG_WALLPAPER_OFFSETS = 10020;
    private static final int MSG_WINDOW_MOVED = 10035;
    @UnsupportedAppUsage
    private static final int MSG_WINDOW_RESIZED = 10030;
    private static final int NOTIFY_COLORS_RATE_LIMIT_MS = 1000;
    public static final String SERVICE_INTERFACE = "android.service.wallpaper.WallpaperService";
    public static final String SERVICE_META_DATA = "android.service.wallpaper";
    static final String TAG = "WallpaperService";
    /* access modifiers changed from: private */
    public final ArrayList<Engine> mActiveEngines = new ArrayList<>();

    public abstract Engine onCreateEngine();

    static final class WallpaperCommand {
        String action;
        Bundle extras;
        boolean sync;
        int x;
        int y;
        int z;

        WallpaperCommand() {
        }
    }

    public class Engine {
        final Rect mBackdropFrame;
        HandlerCaller mCaller;
        private final Supplier<Long> mClockFunction;
        IWallpaperConnection mConnection;
        final Rect mContentInsets;
        boolean mCreated;
        int mCurHeight;
        int mCurWidth;
        int mCurWindowFlags;
        int mCurWindowPrivateFlags;
        boolean mDestroyed;
        final Rect mDispatchedContentInsets;
        DisplayCutout mDispatchedDisplayCutout;
        final Rect mDispatchedOutsets;
        final Rect mDispatchedOverscanInsets;
        final Rect mDispatchedStableInsets;
        /* access modifiers changed from: private */
        public Display mDisplay;
        private Context mDisplayContext;
        final DisplayCutout.ParcelableWrapper mDisplayCutout;
        private final DisplayManager.DisplayListener mDisplayListener;
        /* access modifiers changed from: private */
        public int mDisplayState;
        boolean mDrawingAllowed;
        final Rect mFinalStableInsets;
        final Rect mFinalSystemInsets;
        boolean mFixedSizeAllowed;
        int mFormat;
        private final Handler mHandler;
        int mHeight;
        IWallpaperEngineWrapper mIWallpaperEngine;
        boolean mInitializing;
        InputChannel mInputChannel;
        WallpaperInputEventReceiver mInputEventReceiver;
        final InsetsState mInsetsState;
        boolean mIsCreating;
        boolean mIsInAmbientMode;
        private long mLastColorInvalidation;
        final WindowManager.LayoutParams mLayout;
        final Object mLock;
        final MergedConfiguration mMergedConfiguration;
        private final Runnable mNotifyColorsChanged;
        boolean mOffsetMessageEnqueued;
        boolean mOffsetsChanged;
        final Rect mOutsets;
        final Rect mOverscanInsets;
        MotionEvent mPendingMove;
        boolean mPendingSync;
        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        float mPendingXOffset;
        float mPendingXOffsetStep;
        float mPendingYOffset;
        float mPendingYOffsetStep;
        boolean mReportedVisible;
        IWindowSession mSession;
        final Rect mStableInsets;
        SurfaceControl mSurfaceControl;
        boolean mSurfaceCreated;
        final BaseSurfaceHolder mSurfaceHolder;
        int mType;
        boolean mVisible;
        final Rect mVisibleInsets;
        int mWidth;
        final Rect mWinFrame;
        final BaseIWindow mWindow;
        int mWindowFlags;
        int mWindowPrivateFlags;
        IBinder mWindowToken;

        final class WallpaperInputEventReceiver extends InputEventReceiver {
            public WallpaperInputEventReceiver(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            public void onInputEvent(InputEvent event) {
                boolean handled = false;
                try {
                    if ((event instanceof MotionEvent) && (event.getSource() & 2) != 0) {
                        Engine.this.dispatchPointer(MotionEvent.obtainNoHistory((MotionEvent) event));
                        handled = true;
                    }
                } finally {
                    finishInputEvent(event, handled);
                }
            }
        }

        public Engine(WallpaperService this$02) {
            this($$Lambda$87DoTfJA3qVM7QF6F_6BpQlQTA.INSTANCE, Handler.getMain());
        }

        @VisibleForTesting
        public Engine(Supplier<Long> clockFunction, Handler handler) {
            this.mInitializing = true;
            this.mWindowFlags = 16;
            this.mWindowPrivateFlags = 4;
            this.mCurWindowFlags = this.mWindowFlags;
            this.mCurWindowPrivateFlags = this.mWindowPrivateFlags;
            this.mVisibleInsets = new Rect();
            this.mWinFrame = new Rect();
            this.mOverscanInsets = new Rect();
            this.mContentInsets = new Rect();
            this.mStableInsets = new Rect();
            this.mOutsets = new Rect();
            this.mDispatchedOverscanInsets = new Rect();
            this.mDispatchedContentInsets = new Rect();
            this.mDispatchedStableInsets = new Rect();
            this.mDispatchedOutsets = new Rect();
            this.mFinalSystemInsets = new Rect();
            this.mFinalStableInsets = new Rect();
            this.mBackdropFrame = new Rect();
            this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
            this.mDispatchedDisplayCutout = DisplayCutout.NO_CUTOUT;
            this.mInsetsState = new InsetsState();
            this.mMergedConfiguration = new MergedConfiguration();
            this.mLayout = new WindowManager.LayoutParams();
            this.mLock = new Object();
            this.mNotifyColorsChanged = new Runnable() {
                public final void run() {
                    WallpaperService.Engine.this.notifyColorsChanged();
                }
            };
            this.mSurfaceControl = new SurfaceControl();
            this.mSurfaceHolder = new BaseSurfaceHolder() {
                {
                    this.mRequestedFormat = 2;
                }

                public boolean onAllowLockCanvas() {
                    return Engine.this.mDrawingAllowed;
                }

                public void onRelayoutContainer() {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessage(10000));
                }

                public void onUpdateSurface() {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessage(10000));
                }

                public boolean isCreating() {
                    return Engine.this.mIsCreating;
                }

                public void setFixedSize(int width, int height) {
                    if (Engine.this.mFixedSizeAllowed) {
                        super.setFixedSize(width, height);
                        return;
                    }
                    throw new UnsupportedOperationException("Wallpapers currently only support sizing from layout");
                }

                public void setKeepScreenOn(boolean screenOn) {
                    throw new UnsupportedOperationException("Wallpapers do not support keep screen on");
                }

                private void prepareToDraw() {
                    if (Engine.this.mDisplayState == 3 || Engine.this.mDisplayState == 4) {
                        try {
                            Engine.this.mSession.pokeDrawLock(Engine.this.mWindow);
                        } catch (RemoteException e) {
                        }
                    }
                }

                public Canvas lockCanvas() {
                    prepareToDraw();
                    return super.lockCanvas();
                }

                public Canvas lockCanvas(Rect dirty) {
                    prepareToDraw();
                    return super.lockCanvas(dirty);
                }

                public Canvas lockHardwareCanvas() {
                    prepareToDraw();
                    return super.lockHardwareCanvas();
                }
            };
            this.mWindow = new BaseIWindow() {
                public void resized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration mergedConfiguration, Rect backDropRect, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageIO(10030, reportDraw, outsets));
                }

                public void moved(int newX, int newY) {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageII(10035, newX, newY));
                }

                public void dispatchAppVisibility(boolean visible) {
                    if (!Engine.this.mIWallpaperEngine.mIsPreview) {
                        Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageI(10010, visible));
                    }
                }

                public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, boolean sync) {
                    synchronized (Engine.this.mLock) {
                        Engine.this.mPendingXOffset = x;
                        Engine.this.mPendingYOffset = y;
                        Engine.this.mPendingXOffsetStep = xStep;
                        Engine.this.mPendingYOffsetStep = yStep;
                        if (sync) {
                            Engine.this.mPendingSync = true;
                        }
                        if (!Engine.this.mOffsetMessageEnqueued) {
                            Engine.this.mOffsetMessageEnqueued = true;
                            Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessage(10020));
                        }
                    }
                }

                public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) {
                    synchronized (Engine.this.mLock) {
                        WallpaperCommand cmd = new WallpaperCommand();
                        cmd.action = action;
                        cmd.x = x;
                        cmd.y = y;
                        cmd.z = z;
                        cmd.extras = extras;
                        cmd.sync = sync;
                        Message msg = Engine.this.mCaller.obtainMessage(10025);
                        msg.obj = cmd;
                        Engine.this.mCaller.sendMessage(msg);
                    }
                }
            };
            this.mDisplayListener = new DisplayManager.DisplayListener() {
                public void onDisplayChanged(int displayId) {
                    if (Engine.this.mDisplay.getDisplayId() == displayId) {
                        Engine.this.reportVisibility();
                    }
                }

                public void onDisplayRemoved(int displayId) {
                }

                public void onDisplayAdded(int displayId) {
                }
            };
            this.mClockFunction = clockFunction;
            this.mHandler = handler;
        }

        public SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        public int getDesiredMinimumWidth() {
            return this.mIWallpaperEngine.mReqWidth;
        }

        public int getDesiredMinimumHeight() {
            return this.mIWallpaperEngine.mReqHeight;
        }

        public boolean isVisible() {
            return this.mReportedVisible;
        }

        public boolean isPreview() {
            return this.mIWallpaperEngine.mIsPreview;
        }

        @SystemApi
        public boolean isInAmbientMode() {
            return this.mIsInAmbientMode;
        }

        public void setTouchEventsEnabled(boolean enabled) {
            int i;
            if (enabled) {
                i = this.mWindowFlags & -17;
            } else {
                i = this.mWindowFlags | 16;
            }
            this.mWindowFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setOffsetNotificationsEnabled(boolean enabled) {
            int i;
            if (enabled) {
                i = this.mWindowPrivateFlags | 4;
            } else {
                i = this.mWindowPrivateFlags & -5;
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        @UnsupportedAppUsage
        public void setFixedSizeAllowed(boolean allowed) {
            this.mFixedSizeAllowed = allowed;
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
        }

        public void onDestroy() {
        }

        public void onVisibilityChanged(boolean visible) {
        }

        public void onApplyWindowInsets(WindowInsets insets) {
        }

        public void onTouchEvent(MotionEvent event) {
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
        }

        public Bundle onCommand(String action, int x, int y, int z, Bundle extras, boolean resultRequested) {
            return null;
        }

        @SystemApi
        public void onAmbientModeChanged(boolean inAmbientMode, long animationDuration) {
        }

        public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
        }

        public void notifyColorsChanged() {
            long now = this.mClockFunction.get().longValue();
            if (now - this.mLastColorInvalidation < 1000) {
                Log.w(WallpaperService.TAG, "This call has been deferred. You should only call notifyColorsChanged() once every 1.0 seconds.");
                if (!this.mHandler.hasCallbacks(this.mNotifyColorsChanged)) {
                    this.mHandler.postDelayed(this.mNotifyColorsChanged, 1000);
                    return;
                }
                return;
            }
            this.mLastColorInvalidation = now;
            this.mHandler.removeCallbacks(this.mNotifyColorsChanged);
            try {
                WallpaperColors newColors = onComputeColors();
                if (this.mConnection != null) {
                    this.mConnection.onWallpaperColorsChanged(newColors, this.mDisplay.getDisplayId());
                } else {
                    Log.w(WallpaperService.TAG, "Can't notify system because wallpaper connection was not established.");
                }
            } catch (RemoteException e) {
                Log.w(WallpaperService.TAG, "Can't notify system because wallpaper connection was lost.", e);
            }
        }

        public WallpaperColors onComputeColors() {
            return null;
        }

        @VisibleForTesting
        public void setCreated(boolean created) {
            this.mCreated = created;
        }

        /* access modifiers changed from: protected */
        public void dump(String prefix, FileDescriptor fd, PrintWriter out, String[] args) {
            out.print(prefix);
            out.print("mInitializing=");
            out.print(this.mInitializing);
            out.print(" mDestroyed=");
            out.println(this.mDestroyed);
            out.print(prefix);
            out.print("mVisible=");
            out.print(this.mVisible);
            out.print(" mReportedVisible=");
            out.println(this.mReportedVisible);
            out.print(prefix);
            out.print("mDisplay=");
            out.println(this.mDisplay);
            out.print(prefix);
            out.print("mCreated=");
            out.print(this.mCreated);
            out.print(" mSurfaceCreated=");
            out.print(this.mSurfaceCreated);
            out.print(" mIsCreating=");
            out.print(this.mIsCreating);
            out.print(" mDrawingAllowed=");
            out.println(this.mDrawingAllowed);
            out.print(prefix);
            out.print("mWidth=");
            out.print(this.mWidth);
            out.print(" mCurWidth=");
            out.print(this.mCurWidth);
            out.print(" mHeight=");
            out.print(this.mHeight);
            out.print(" mCurHeight=");
            out.println(this.mCurHeight);
            out.print(prefix);
            out.print("mType=");
            out.print(this.mType);
            out.print(" mWindowFlags=");
            out.print(this.mWindowFlags);
            out.print(" mCurWindowFlags=");
            out.println(this.mCurWindowFlags);
            out.print(prefix);
            out.print("mWindowPrivateFlags=");
            out.print(this.mWindowPrivateFlags);
            out.print(" mCurWindowPrivateFlags=");
            out.println(this.mCurWindowPrivateFlags);
            out.print(prefix);
            out.print("mVisibleInsets=");
            out.print(this.mVisibleInsets.toShortString());
            out.print(" mWinFrame=");
            out.print(this.mWinFrame.toShortString());
            out.print(" mContentInsets=");
            out.println(this.mContentInsets.toShortString());
            out.print(prefix);
            out.print("mConfiguration=");
            out.println(this.mMergedConfiguration.getMergedConfiguration());
            out.print(prefix);
            out.print("mLayout=");
            out.println(this.mLayout);
            synchronized (this.mLock) {
                out.print(prefix);
                out.print("mPendingXOffset=");
                out.print(this.mPendingXOffset);
                out.print(" mPendingXOffset=");
                out.println(this.mPendingXOffset);
                out.print(prefix);
                out.print("mPendingXOffsetStep=");
                out.print(this.mPendingXOffsetStep);
                out.print(" mPendingXOffsetStep=");
                out.println(this.mPendingXOffsetStep);
                out.print(prefix);
                out.print("mOffsetMessageEnqueued=");
                out.print(this.mOffsetMessageEnqueued);
                out.print(" mPendingSync=");
                out.println(this.mPendingSync);
                if (this.mPendingMove != null) {
                    out.print(prefix);
                    out.print("mPendingMove=");
                    out.println(this.mPendingMove);
                }
            }
        }

        /* access modifiers changed from: private */
        public void dispatchPointer(MotionEvent event) {
            if (event.isTouchEvent()) {
                synchronized (this.mLock) {
                    if (event.getAction() == 2) {
                        this.mPendingMove = event;
                    } else {
                        this.mPendingMove = null;
                    }
                }
                this.mCaller.sendMessage(this.mCaller.obtainMessageO(10040, event));
                return;
            }
            event.recycle();
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:208:0x05aa A[Catch:{ RemoteException -> 0x05b7 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void updateSurface(boolean r65, boolean r66, boolean r67) {
            /*
                r64 = this;
                r1 = r64
                boolean r0 = r1.mDestroyed
                if (r0 == 0) goto L_0x000d
                java.lang.String r0 = "WallpaperService"
                java.lang.String r3 = "Ignoring updateSurface: destroyed"
                android.util.Log.w((java.lang.String) r0, (java.lang.String) r3)
            L_0x000d:
                r0 = 0
                com.android.internal.view.BaseSurfaceHolder r3 = r1.mSurfaceHolder
                int r3 = r3.getRequestedWidth()
                if (r3 > 0) goto L_0x0018
                r3 = -1
                goto L_0x0019
            L_0x0018:
                r0 = 1
            L_0x0019:
                com.android.internal.view.BaseSurfaceHolder r4 = r1.mSurfaceHolder
                int r4 = r4.getRequestedHeight()
                if (r4 > 0) goto L_0x0024
                r4 = -1
            L_0x0022:
                r5 = r0
                goto L_0x0026
            L_0x0024:
                r0 = 1
                goto L_0x0022
            L_0x0026:
                boolean r0 = r1.mCreated
                r6 = 1
                r0 = r0 ^ r6
                r7 = r0
                boolean r0 = r1.mSurfaceCreated
                r0 = r0 ^ r6
                r8 = r0
                int r0 = r1.mFormat
                com.android.internal.view.BaseSurfaceHolder r9 = r1.mSurfaceHolder
                int r9 = r9.getRequestedFormat()
                r10 = 0
                if (r0 == r9) goto L_0x003c
                r0 = r6
                goto L_0x003d
            L_0x003c:
                r0 = r10
            L_0x003d:
                r9 = r0
                int r0 = r1.mWidth
                if (r0 != r3) goto L_0x0049
                int r0 = r1.mHeight
                if (r0 == r4) goto L_0x0047
                goto L_0x0049
            L_0x0047:
                r0 = r10
                goto L_0x004a
            L_0x0049:
                r0 = r6
            L_0x004a:
                r11 = r0
                boolean r0 = r1.mCreated
                r0 = r0 ^ r6
                r12 = r0
                int r0 = r1.mType
                com.android.internal.view.BaseSurfaceHolder r13 = r1.mSurfaceHolder
                int r13 = r13.getRequestedType()
                if (r0 == r13) goto L_0x005b
                r0 = r6
                goto L_0x005c
            L_0x005b:
                r0 = r10
            L_0x005c:
                r13 = r0
                int r0 = r1.mCurWindowFlags
                int r14 = r1.mWindowFlags
                if (r0 != r14) goto L_0x006c
                int r0 = r1.mCurWindowPrivateFlags
                int r14 = r1.mWindowPrivateFlags
                if (r0 == r14) goto L_0x006a
                goto L_0x006c
            L_0x006a:
                r0 = r10
                goto L_0x006d
            L_0x006c:
                r0 = r6
            L_0x006d:
                r14 = r0
                if (r65 != 0) goto L_0x0099
                if (r7 != 0) goto L_0x0099
                if (r8 != 0) goto L_0x0099
                if (r9 != 0) goto L_0x0099
                if (r11 != 0) goto L_0x0099
                if (r13 != 0) goto L_0x0099
                if (r14 != 0) goto L_0x0099
                if (r67 != 0) goto L_0x0099
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r0 = r1.mIWallpaperEngine
                boolean r0 = r0.mShownReported
                if (r0 != 0) goto L_0x0085
                goto L_0x0099
            L_0x0085:
                r2 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r34 = r7
                r35 = r8
                r33 = r9
                r31 = r13
                r32 = r14
                goto L_0x0616
            L_0x0099:
                r1.mWidth = r3     // Catch:{ RemoteException -> 0x05fe }
                r1.mHeight = r4     // Catch:{ RemoteException -> 0x05fe }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05fe }
                int r0 = r0.getRequestedFormat()     // Catch:{ RemoteException -> 0x05fe }
                r1.mFormat = r0     // Catch:{ RemoteException -> 0x05fe }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05fe }
                int r0 = r0.getRequestedType()     // Catch:{ RemoteException -> 0x05fe }
                r1.mType = r0     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                r0.x = r10     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                r0.y = r10     // Catch:{ RemoteException -> 0x05fe }
                if (r5 != 0) goto L_0x00d5
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x00c0 }
                r0.width = r3     // Catch:{ RemoteException -> 0x00c0 }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x00c0 }
                r0.height = r4     // Catch:{ RemoteException -> 0x00c0 }
                goto L_0x0103
            L_0x00c0:
                r0 = move-exception
                r59 = r3
                r58 = r4
                r60 = r5
                r34 = r7
                r35 = r8
                r33 = r9
                r31 = r13
                r32 = r14
            L_0x00d1:
                r7 = r67
                goto L_0x0615
            L_0x00d5:
                android.view.DisplayInfo r0 = new android.view.DisplayInfo     // Catch:{ RemoteException -> 0x05fe }
                r0.<init>()     // Catch:{ RemoteException -> 0x05fe }
                android.view.Display r10 = r1.mDisplay     // Catch:{ RemoteException -> 0x05fe }
                r10.getDisplayInfo(r0)     // Catch:{ RemoteException -> 0x05fe }
                int r10 = r0.logicalHeight     // Catch:{ RemoteException -> 0x05fe }
                float r10 = (float) r10     // Catch:{ RemoteException -> 0x05fe }
                float r6 = (float) r4     // Catch:{ RemoteException -> 0x05fe }
                float r10 = r10 / r6
                int r6 = r0.logicalWidth     // Catch:{ RemoteException -> 0x05fe }
                float r6 = (float) r6     // Catch:{ RemoteException -> 0x05fe }
                r16 = r0
                float r0 = (float) r3     // Catch:{ RemoteException -> 0x05fe }
                float r6 = r6 / r0
                float r0 = java.lang.Math.max(r10, r6)     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r6 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                float r10 = (float) r4     // Catch:{ RemoteException -> 0x05fe }
                float r10 = r10 * r0
                int r10 = (int) r10     // Catch:{ RemoteException -> 0x05fe }
                r6.height = r10     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r6 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                float r10 = (float) r3     // Catch:{ RemoteException -> 0x05fe }
                float r10 = r10 * r0
                int r10 = (int) r10     // Catch:{ RemoteException -> 0x05fe }
                r6.width = r10     // Catch:{ RemoteException -> 0x05fe }
                int r6 = r1.mWindowFlags     // Catch:{ RemoteException -> 0x05fe }
                r6 = r6 | 16384(0x4000, float:2.2959E-41)
                r1.mWindowFlags = r6     // Catch:{ RemoteException -> 0x05fe }
            L_0x0103:
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                int r6 = r1.mFormat     // Catch:{ RemoteException -> 0x05fe }
                r0.format = r6     // Catch:{ RemoteException -> 0x05fe }
                int r0 = r1.mWindowFlags     // Catch:{ RemoteException -> 0x05fe }
                r1.mCurWindowFlags = r0     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                int r6 = r1.mWindowFlags     // Catch:{ RemoteException -> 0x05fe }
                r6 = r6 | 512(0x200, float:7.175E-43)
                r10 = 65536(0x10000, float:9.18355E-41)
                r6 = r6 | r10
                r6 = r6 | 256(0x100, float:3.59E-43)
                r6 = r6 | 8
                r0.flags = r6     // Catch:{ RemoteException -> 0x05fe }
                int r0 = r1.mWindowPrivateFlags     // Catch:{ RemoteException -> 0x05fe }
                r1.mCurWindowPrivateFlags = r0     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                int r6 = r1.mWindowPrivateFlags     // Catch:{ RemoteException -> 0x05fe }
                r0.privateFlags = r6     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                int r6 = r1.mType     // Catch:{ RemoteException -> 0x05fe }
                r0.memoryType = r6     // Catch:{ RemoteException -> 0x05fe }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05fe }
                android.os.IBinder r6 = r1.mWindowToken     // Catch:{ RemoteException -> 0x05fe }
                r0.token = r6     // Catch:{ RemoteException -> 0x05fe }
                boolean r0 = r1.mCreated     // Catch:{ RemoteException -> 0x05fe }
                if (r0 != 0) goto L_0x0247
                android.service.wallpaper.WallpaperService r0 = android.service.wallpaper.WallpaperService.this     // Catch:{ RemoteException -> 0x0230 }
                int[] r6 = com.android.internal.R.styleable.Window     // Catch:{ RemoteException -> 0x0230 }
                android.content.res.TypedArray r0 = r0.obtainStyledAttributes(r6)     // Catch:{ RemoteException -> 0x0230 }
                r0.recycle()     // Catch:{ RemoteException -> 0x0230 }
                android.view.WindowManager$LayoutParams r6 = r1.mLayout     // Catch:{ RemoteException -> 0x0230 }
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r10 = r1.mIWallpaperEngine     // Catch:{ RemoteException -> 0x0230 }
                int r10 = r10.mWindowType     // Catch:{ RemoteException -> 0x0230 }
                r6.type = r10     // Catch:{ RemoteException -> 0x0230 }
                android.view.WindowManager$LayoutParams r6 = r1.mLayout     // Catch:{ RemoteException -> 0x0230 }
                r10 = 8388659(0x800033, float:1.1755015E-38)
                r6.gravity = r10     // Catch:{ RemoteException -> 0x0230 }
                android.view.WindowManager$LayoutParams r6 = r1.mLayout     // Catch:{ RemoteException -> 0x0230 }
                android.service.wallpaper.WallpaperService r10 = android.service.wallpaper.WallpaperService.this     // Catch:{ RemoteException -> 0x0230 }
                java.lang.Class r10 = r10.getClass()     // Catch:{ RemoteException -> 0x0230 }
                java.lang.String r10 = r10.getName()     // Catch:{ RemoteException -> 0x0230 }
                r6.setTitle(r10)     // Catch:{ RemoteException -> 0x0230 }
                android.view.WindowManager$LayoutParams r6 = r1.mLayout     // Catch:{ RemoteException -> 0x0230 }
                r10 = 16974606(0x103030e, float:2.4063092E-38)
                r6.windowAnimations = r10     // Catch:{ RemoteException -> 0x0230 }
                android.view.InputChannel r6 = new android.view.InputChannel     // Catch:{ RemoteException -> 0x0230 }
                r6.<init>()     // Catch:{ RemoteException -> 0x0230 }
                r1.mInputChannel = r6     // Catch:{ RemoteException -> 0x0230 }
                android.view.IWindowSession r6 = r1.mSession     // Catch:{ RemoteException -> 0x0230 }
                com.android.internal.view.BaseIWindow r10 = r1.mWindow     // Catch:{ RemoteException -> 0x0230 }
                r29 = r0
                com.android.internal.view.BaseIWindow r0 = r1.mWindow     // Catch:{ RemoteException -> 0x0230 }
                int r0 = r0.mSeq     // Catch:{ RemoteException -> 0x0230 }
                r30 = r11
                android.view.WindowManager$LayoutParams r11 = r1.mLayout     // Catch:{ RemoteException -> 0x0219 }
                r20 = 0
                r31 = r13
                android.view.Display r13 = r1.mDisplay     // Catch:{ RemoteException -> 0x0204 }
                int r21 = r13.getDisplayId()     // Catch:{ RemoteException -> 0x0204 }
                android.graphics.Rect r13 = r1.mWinFrame     // Catch:{ RemoteException -> 0x0204 }
                r32 = r14
                android.graphics.Rect r14 = r1.mContentInsets     // Catch:{ RemoteException -> 0x01f1 }
                android.graphics.Rect r15 = r1.mStableInsets     // Catch:{ RemoteException -> 0x01f1 }
                r33 = r9
                android.graphics.Rect r9 = r1.mOutsets     // Catch:{ RemoteException -> 0x01e0 }
                android.view.DisplayCutout$ParcelableWrapper r2 = r1.mDisplayCutout     // Catch:{ RemoteException -> 0x01e0 }
                r34 = r7
                android.view.InputChannel r7 = r1.mInputChannel     // Catch:{ RemoteException -> 0x01d1 }
                r35 = r8
                android.view.InsetsState r8 = r1.mInsetsState     // Catch:{ RemoteException -> 0x029f }
                r16 = r6
                r17 = r10
                r18 = r0
                r19 = r11
                r22 = r13
                r23 = r14
                r24 = r15
                r25 = r9
                r26 = r2
                r27 = r7
                r28 = r8
                int r0 = r16.addToDisplay(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)     // Catch:{ RemoteException -> 0x029f }
                if (r0 >= 0) goto L_0x01bf
                java.lang.String r0 = "WallpaperService"
                java.lang.String r2 = "Failed to add window while updating wallpaper surface."
                android.util.Log.w((java.lang.String) r0, (java.lang.String) r2)     // Catch:{ RemoteException -> 0x029f }
                return
            L_0x01bf:
                r2 = 1
                r1.mCreated = r2     // Catch:{ RemoteException -> 0x029f }
                android.service.wallpaper.WallpaperService$Engine$WallpaperInputEventReceiver r0 = new android.service.wallpaper.WallpaperService$Engine$WallpaperInputEventReceiver     // Catch:{ RemoteException -> 0x029f }
                android.view.InputChannel r2 = r1.mInputChannel     // Catch:{ RemoteException -> 0x029f }
                android.os.Looper r6 = android.os.Looper.myLooper()     // Catch:{ RemoteException -> 0x029f }
                r0.<init>(r2, r6)     // Catch:{ RemoteException -> 0x029f }
                r1.mInputEventReceiver = r0     // Catch:{ RemoteException -> 0x029f }
                goto L_0x0253
            L_0x01d1:
                r0 = move-exception
                r35 = r8
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r11 = r30
                goto L_0x0615
            L_0x01e0:
                r0 = move-exception
                r34 = r7
                r35 = r8
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r11 = r30
                goto L_0x0615
            L_0x01f1:
                r0 = move-exception
                r34 = r7
                r35 = r8
                r33 = r9
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r11 = r30
                goto L_0x0615
            L_0x0204:
                r0 = move-exception
                r34 = r7
                r35 = r8
                r33 = r9
                r32 = r14
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r11 = r30
                goto L_0x0615
            L_0x0219:
                r0 = move-exception
                r34 = r7
                r35 = r8
                r33 = r9
                r31 = r13
                r32 = r14
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r11 = r30
                goto L_0x0615
            L_0x0230:
                r0 = move-exception
                r34 = r7
                r35 = r8
                r33 = r9
                r30 = r11
                r31 = r13
                r32 = r14
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                goto L_0x0615
            L_0x0247:
                r34 = r7
                r35 = r8
                r33 = r9
                r30 = r11
                r31 = r13
                r32 = r14
            L_0x0253:
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05f0 }
                java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock     // Catch:{ RemoteException -> 0x05f0 }
                r0.lock()     // Catch:{ RemoteException -> 0x05f0 }
                r2 = 1
                r1.mDrawingAllowed = r2     // Catch:{ RemoteException -> 0x05f0 }
                if (r5 != 0) goto L_0x02ac
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r0 = r0.surfaceInsets     // Catch:{ RemoteException -> 0x029f }
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r2 = r1.mIWallpaperEngine     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r2 = r2.mDisplayPadding     // Catch:{ RemoteException -> 0x029f }
                r0.set(r2)     // Catch:{ RemoteException -> 0x029f }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r0 = r0.surfaceInsets     // Catch:{ RemoteException -> 0x029f }
                int r2 = r0.left     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x029f }
                int r6 = r6.left     // Catch:{ RemoteException -> 0x029f }
                int r2 = r2 + r6
                r0.left = r2     // Catch:{ RemoteException -> 0x029f }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r0 = r0.surfaceInsets     // Catch:{ RemoteException -> 0x029f }
                int r2 = r0.top     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x029f }
                int r6 = r6.top     // Catch:{ RemoteException -> 0x029f }
                int r2 = r2 + r6
                r0.top = r2     // Catch:{ RemoteException -> 0x029f }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r0 = r0.surfaceInsets     // Catch:{ RemoteException -> 0x029f }
                int r2 = r0.right     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x029f }
                int r6 = r6.right     // Catch:{ RemoteException -> 0x029f }
                int r2 = r2 + r6
                r0.right = r2     // Catch:{ RemoteException -> 0x029f }
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r0 = r0.surfaceInsets     // Catch:{ RemoteException -> 0x029f }
                int r2 = r0.bottom     // Catch:{ RemoteException -> 0x029f }
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x029f }
                int r6 = r6.bottom     // Catch:{ RemoteException -> 0x029f }
                int r2 = r2 + r6
                r0.bottom = r2     // Catch:{ RemoteException -> 0x029f }
                goto L_0x02b4
            L_0x029f:
                r0 = move-exception
                r7 = r67
                r59 = r3
                r58 = r4
                r60 = r5
                r11 = r30
                goto L_0x0615
            L_0x02ac:
                android.view.WindowManager$LayoutParams r0 = r1.mLayout     // Catch:{ RemoteException -> 0x05f0 }
                android.graphics.Rect r0 = r0.surfaceInsets     // Catch:{ RemoteException -> 0x05f0 }
                r2 = 0
                r0.set(r2, r2, r2, r2)     // Catch:{ RemoteException -> 0x05f0 }
            L_0x02b4:
                android.view.IWindowSession r0 = r1.mSession     // Catch:{ RemoteException -> 0x05f0 }
                com.android.internal.view.BaseIWindow r2 = r1.mWindow     // Catch:{ RemoteException -> 0x05f0 }
                com.android.internal.view.BaseIWindow r6 = r1.mWindow     // Catch:{ RemoteException -> 0x05f0 }
                int r6 = r6.mSeq     // Catch:{ RemoteException -> 0x05f0 }
                android.view.WindowManager$LayoutParams r7 = r1.mLayout     // Catch:{ RemoteException -> 0x05f0 }
                int r8 = r1.mWidth     // Catch:{ RemoteException -> 0x05f0 }
                int r9 = r1.mHeight     // Catch:{ RemoteException -> 0x05f0 }
                r42 = 0
                r43 = 0
                r44 = -1
                android.graphics.Rect r10 = r1.mWinFrame     // Catch:{ RemoteException -> 0x05f0 }
                android.graphics.Rect r11 = r1.mOverscanInsets     // Catch:{ RemoteException -> 0x05f0 }
                android.graphics.Rect r13 = r1.mContentInsets     // Catch:{ RemoteException -> 0x05f0 }
                android.graphics.Rect r14 = r1.mVisibleInsets     // Catch:{ RemoteException -> 0x05f0 }
                android.graphics.Rect r15 = r1.mStableInsets     // Catch:{ RemoteException -> 0x05f0 }
                r57 = r12
                android.graphics.Rect r12 = r1.mOutsets     // Catch:{ RemoteException -> 0x05e2 }
                r58 = r4
                android.graphics.Rect r4 = r1.mBackdropFrame     // Catch:{ RemoteException -> 0x05d6 }
                r59 = r3
                android.view.DisplayCutout$ParcelableWrapper r3 = r1.mDisplayCutout     // Catch:{ RemoteException -> 0x05cc }
                r60 = r5
                android.util.MergedConfiguration r5 = r1.mMergedConfiguration     // Catch:{ RemoteException -> 0x05c4 }
                r61 = r5
                android.view.SurfaceControl r5 = r1.mSurfaceControl     // Catch:{ RemoteException -> 0x05c4 }
                r62 = r5
                android.view.InsetsState r5 = r1.mInsetsState     // Catch:{ RemoteException -> 0x05c4 }
                r36 = r0
                r37 = r2
                r38 = r6
                r39 = r7
                r40 = r8
                r41 = r9
                r46 = r10
                r47 = r11
                r48 = r13
                r49 = r14
                r50 = r15
                r51 = r12
                r52 = r4
                r53 = r3
                r54 = r61
                r55 = r62
                r56 = r5
                int r0 = r36.relayout(r37, r38, r39, r40, r41, r42, r43, r44, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55, r56)     // Catch:{ RemoteException -> 0x05c4 }
                r2 = r0
                android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ RemoteException -> 0x05c4 }
                boolean r0 = r0.isValid()     // Catch:{ RemoteException -> 0x05c4 }
                if (r0 == 0) goto L_0x0327
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05c4 }
                android.view.Surface r0 = r0.mSurface     // Catch:{ RemoteException -> 0x05c4 }
                android.view.SurfaceControl r3 = r1.mSurfaceControl     // Catch:{ RemoteException -> 0x05c4 }
                r0.copyFrom(r3)     // Catch:{ RemoteException -> 0x05c4 }
                android.view.SurfaceControl r0 = r1.mSurfaceControl     // Catch:{ RemoteException -> 0x05c4 }
                r0.release()     // Catch:{ RemoteException -> 0x05c4 }
            L_0x0327:
                android.graphics.Rect r0 = r1.mWinFrame     // Catch:{ RemoteException -> 0x05c4 }
                int r0 = r0.width()     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r3 = r1.mWinFrame     // Catch:{ RemoteException -> 0x05c4 }
                int r3 = r3.height()     // Catch:{ RemoteException -> 0x05c4 }
                if (r60 != 0) goto L_0x03e1
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r4 = r1.mIWallpaperEngine     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r4 = r4.mDisplayPadding     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r4.left     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r4.right     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r5 + r6
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6.left     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r5 + r6
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6.right     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r5 + r6
                int r0 = r0 + r5
                int r5 = r4.top     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r4.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r5 + r6
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6.top     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r5 + r6
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r5 = r5 + r6
                int r3 = r3 + r5
                android.graphics.Rect r5 = r1.mOverscanInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.left     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.left     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.left = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mOverscanInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.top     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.top     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.top = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mOverscanInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.right     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.right     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.right = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mOverscanInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.bottom = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mContentInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.left     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.left     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.left = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mContentInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.top     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.top     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.top = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mContentInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.right     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.right     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.right = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mContentInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.bottom = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mStableInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.left     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.left     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.left = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mStableInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.top     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.top     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.top = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mStableInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.right     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.right     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.right = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.graphics.Rect r5 = r1.mStableInsets     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r5.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r6 = r6 + r7
                r5.bottom = r6     // Catch:{ RemoteException -> 0x05c4 }
                android.view.DisplayCutout$ParcelableWrapper r5 = r1.mDisplayCutout     // Catch:{ RemoteException -> 0x05c4 }
                android.view.DisplayCutout$ParcelableWrapper r6 = r1.mDisplayCutout     // Catch:{ RemoteException -> 0x05c4 }
                android.view.DisplayCutout r6 = r6.get()     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = r4.left     // Catch:{ RemoteException -> 0x05c4 }
                int r7 = -r7
                int r8 = r4.top     // Catch:{ RemoteException -> 0x05c4 }
                int r8 = -r8
                int r9 = r4.right     // Catch:{ RemoteException -> 0x05c4 }
                int r9 = -r9
                int r10 = r4.bottom     // Catch:{ RemoteException -> 0x05c4 }
                int r10 = -r10
                android.view.DisplayCutout r6 = r6.inset(r7, r8, r9, r10)     // Catch:{ RemoteException -> 0x05c4 }
                r5.set((android.view.DisplayCutout) r6)     // Catch:{ RemoteException -> 0x05c4 }
                goto L_0x03e5
            L_0x03e1:
                r0 = r59
                r3 = r58
            L_0x03e5:
                r4 = r3
                r3 = r0
                int r0 = r1.mCurWidth     // Catch:{ RemoteException -> 0x05c4 }
                if (r0 == r3) goto L_0x03f6
                r11 = 1
                r1.mCurWidth = r3     // Catch:{ RemoteException -> 0x03f1 }
                r30 = r11
                goto L_0x03f6
            L_0x03f1:
                r0 = move-exception
                r7 = r67
                goto L_0x05c9
            L_0x03f6:
                int r0 = r1.mCurHeight     // Catch:{ RemoteException -> 0x05c4 }
                if (r0 == r4) goto L_0x0405
                r5 = 1
                r1.mCurHeight = r4     // Catch:{ RemoteException -> 0x03ff }
                r11 = r5
                goto L_0x0407
            L_0x03ff:
                r0 = move-exception
                r7 = r67
                r11 = r5
                goto L_0x05c9
            L_0x0405:
                r11 = r30
            L_0x0407:
                android.graphics.Rect r0 = r1.mDispatchedOverscanInsets     // Catch:{ RemoteException -> 0x03f1 }
                android.graphics.Rect r5 = r1.mOverscanInsets     // Catch:{ RemoteException -> 0x03f1 }
                boolean r0 = r0.equals(r5)     // Catch:{ RemoteException -> 0x03f1 }
                r5 = 1
                r0 = r0 ^ r5
                r5 = r57 | r0
                android.graphics.Rect r0 = r1.mDispatchedContentInsets     // Catch:{ RemoteException -> 0x05be }
                android.graphics.Rect r6 = r1.mContentInsets     // Catch:{ RemoteException -> 0x05be }
                boolean r0 = r0.equals(r6)     // Catch:{ RemoteException -> 0x05be }
                r6 = 1
                r0 = r0 ^ r6
                r5 = r5 | r0
                android.graphics.Rect r0 = r1.mDispatchedStableInsets     // Catch:{ RemoteException -> 0x05be }
                android.graphics.Rect r6 = r1.mStableInsets     // Catch:{ RemoteException -> 0x05be }
                boolean r0 = r0.equals(r6)     // Catch:{ RemoteException -> 0x05be }
                r6 = 1
                r0 = r0 ^ r6
                r5 = r5 | r0
                android.graphics.Rect r0 = r1.mDispatchedOutsets     // Catch:{ RemoteException -> 0x05be }
                android.graphics.Rect r6 = r1.mOutsets     // Catch:{ RemoteException -> 0x05be }
                boolean r0 = r0.equals(r6)     // Catch:{ RemoteException -> 0x05be }
                r6 = 1
                r0 = r0 ^ r6
                r5 = r5 | r0
                android.view.DisplayCutout r0 = r1.mDispatchedDisplayCutout     // Catch:{ RemoteException -> 0x05be }
                android.view.DisplayCutout$ParcelableWrapper r6 = r1.mDisplayCutout     // Catch:{ RemoteException -> 0x05be }
                android.view.DisplayCutout r6 = r6.get()     // Catch:{ RemoteException -> 0x05be }
                boolean r0 = r0.equals(r6)     // Catch:{ RemoteException -> 0x05be }
                r6 = 1
                r0 = r0 ^ r6
                r12 = r5 | r0
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05bb }
                r0.setSurfaceFrameSize(r3, r4)     // Catch:{ RemoteException -> 0x05bb }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05bb }
                java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock     // Catch:{ RemoteException -> 0x05bb }
                r0.unlock()     // Catch:{ RemoteException -> 0x05bb }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ RemoteException -> 0x05bb }
                android.view.Surface r0 = r0.mSurface     // Catch:{ RemoteException -> 0x05bb }
                boolean r0 = r0.isValid()     // Catch:{ RemoteException -> 0x05bb }
                if (r0 != 0) goto L_0x045e
                r64.reportSurfaceDestroyed()     // Catch:{ RemoteException -> 0x05bb }
                return
            L_0x045e:
                r5 = 0
                r6 = r5
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059f }
                r0.ungetCallbacks()     // Catch:{ all -> 0x059f }
                if (r35 == 0) goto L_0x0486
                r5 = 1
                r1.mIsCreating = r5     // Catch:{ all -> 0x059f }
                r6 = 1
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059f }
                r1.onSurfaceCreated(r0)     // Catch:{ all -> 0x059f }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059f }
                android.view.SurfaceHolder$Callback[] r0 = r0.getCallbacks()     // Catch:{ all -> 0x059f }
                if (r0 == 0) goto L_0x0486
                int r5 = r0.length     // Catch:{ all -> 0x059f }
                r7 = 0
            L_0x047a:
                if (r7 >= r5) goto L_0x0486
                r8 = r0[r7]     // Catch:{ all -> 0x059f }
                com.android.internal.view.BaseSurfaceHolder r9 = r1.mSurfaceHolder     // Catch:{ all -> 0x059f }
                r8.surfaceCreated(r9)     // Catch:{ all -> 0x059f }
                int r7 = r7 + 1
                goto L_0x047a
            L_0x0486:
                if (r34 != 0) goto L_0x048f
                r0 = r2 & 2
                if (r0 == 0) goto L_0x048d
                goto L_0x048f
            L_0x048d:
                r0 = 0
                goto L_0x0490
            L_0x048f:
                r0 = 1
            L_0x0490:
                r5 = r67 | r0
                if (r66 != 0) goto L_0x049c
                if (r34 != 0) goto L_0x049c
                if (r35 != 0) goto L_0x049c
                if (r33 != 0) goto L_0x049c
                if (r11 == 0) goto L_0x04c8
            L_0x049c:
                r6 = 1
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059d }
                int r7 = r1.mFormat     // Catch:{ all -> 0x059d }
                int r9 = r1.mCurWidth     // Catch:{ all -> 0x059d }
                int r10 = r1.mCurHeight     // Catch:{ all -> 0x059d }
                r1.onSurfaceChanged(r0, r7, r9, r10)     // Catch:{ all -> 0x059d }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059d }
                android.view.SurfaceHolder$Callback[] r0 = r0.getCallbacks()     // Catch:{ all -> 0x059d }
                if (r0 == 0) goto L_0x04c8
                int r7 = r0.length     // Catch:{ all -> 0x059d }
                r9 = 0
            L_0x04b2:
                if (r9 >= r7) goto L_0x04c8
                r10 = r0[r9]     // Catch:{ all -> 0x059d }
                com.android.internal.view.BaseSurfaceHolder r13 = r1.mSurfaceHolder     // Catch:{ all -> 0x059d }
                int r14 = r1.mFormat     // Catch:{ all -> 0x059d }
                int r15 = r1.mCurWidth     // Catch:{ all -> 0x059d }
                r63 = r0
                int r0 = r1.mCurHeight     // Catch:{ all -> 0x059d }
                r10.surfaceChanged(r13, r14, r15, r0)     // Catch:{ all -> 0x059d }
                int r9 = r9 + 1
                r0 = r63
                goto L_0x04b2
            L_0x04c8:
                if (r12 == 0) goto L_0x054e
                android.graphics.Rect r0 = r1.mDispatchedOverscanInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mOverscanInsets     // Catch:{ all -> 0x059d }
                r0.set(r7)     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedOverscanInsets     // Catch:{ all -> 0x059d }
                int r7 = r0.left     // Catch:{ all -> 0x059d }
                android.graphics.Rect r9 = r1.mOutsets     // Catch:{ all -> 0x059d }
                int r9 = r9.left     // Catch:{ all -> 0x059d }
                int r7 = r7 + r9
                r0.left = r7     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedOverscanInsets     // Catch:{ all -> 0x059d }
                int r7 = r0.top     // Catch:{ all -> 0x059d }
                android.graphics.Rect r9 = r1.mOutsets     // Catch:{ all -> 0x059d }
                int r9 = r9.top     // Catch:{ all -> 0x059d }
                int r7 = r7 + r9
                r0.top = r7     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedOverscanInsets     // Catch:{ all -> 0x059d }
                int r7 = r0.right     // Catch:{ all -> 0x059d }
                android.graphics.Rect r9 = r1.mOutsets     // Catch:{ all -> 0x059d }
                int r9 = r9.right     // Catch:{ all -> 0x059d }
                int r7 = r7 + r9
                r0.right = r7     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedOverscanInsets     // Catch:{ all -> 0x059d }
                int r7 = r0.bottom     // Catch:{ all -> 0x059d }
                android.graphics.Rect r9 = r1.mOutsets     // Catch:{ all -> 0x059d }
                int r9 = r9.bottom     // Catch:{ all -> 0x059d }
                int r7 = r7 + r9
                r0.bottom = r7     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedContentInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mContentInsets     // Catch:{ all -> 0x059d }
                r0.set(r7)     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedStableInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mStableInsets     // Catch:{ all -> 0x059d }
                r0.set(r7)     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mDispatchedOutsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mOutsets     // Catch:{ all -> 0x059d }
                r0.set(r7)     // Catch:{ all -> 0x059d }
                android.view.DisplayCutout$ParcelableWrapper r0 = r1.mDisplayCutout     // Catch:{ all -> 0x059d }
                android.view.DisplayCutout r0 = r0.get()     // Catch:{ all -> 0x059d }
                r1.mDispatchedDisplayCutout = r0     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mFinalSystemInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mDispatchedOverscanInsets     // Catch:{ all -> 0x059d }
                r0.set(r7)     // Catch:{ all -> 0x059d }
                android.graphics.Rect r0 = r1.mFinalStableInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mDispatchedStableInsets     // Catch:{ all -> 0x059d }
                r0.set(r7)     // Catch:{ all -> 0x059d }
                android.view.WindowInsets r0 = new android.view.WindowInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r7 = r1.mFinalSystemInsets     // Catch:{ all -> 0x059d }
                android.graphics.Rect r9 = r1.mFinalStableInsets     // Catch:{ all -> 0x059d }
                android.service.wallpaper.WallpaperService r10 = android.service.wallpaper.WallpaperService.this     // Catch:{ all -> 0x059d }
                android.content.res.Resources r10 = r10.getResources()     // Catch:{ all -> 0x059d }
                android.content.res.Configuration r10 = r10.getConfiguration()     // Catch:{ all -> 0x059d }
                boolean r19 = r10.isScreenRound()     // Catch:{ all -> 0x059d }
                r20 = 0
                android.view.DisplayCutout r10 = r1.mDispatchedDisplayCutout     // Catch:{ all -> 0x059d }
                r16 = r0
                r17 = r7
                r18 = r9
                r21 = r10
                r16.<init>(r17, r18, r19, r20, r21)     // Catch:{ all -> 0x059d }
                r1.onApplyWindowInsets(r0)     // Catch:{ all -> 0x059d }
            L_0x054e:
                if (r5 == 0) goto L_0x0572
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059d }
                r1.onSurfaceRedrawNeeded(r0)     // Catch:{ all -> 0x059d }
                com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x059d }
                android.view.SurfaceHolder$Callback[] r0 = r0.getCallbacks()     // Catch:{ all -> 0x059d }
                if (r0 == 0) goto L_0x0572
                int r7 = r0.length     // Catch:{ all -> 0x059d }
                r9 = 0
            L_0x055f:
                if (r9 >= r7) goto L_0x0572
                r10 = r0[r9]     // Catch:{ all -> 0x059d }
                boolean r13 = r10 instanceof android.view.SurfaceHolder.Callback2     // Catch:{ all -> 0x059d }
                if (r13 == 0) goto L_0x056f
                r13 = r10
                android.view.SurfaceHolder$Callback2 r13 = (android.view.SurfaceHolder.Callback2) r13     // Catch:{ all -> 0x059d }
                com.android.internal.view.BaseSurfaceHolder r14 = r1.mSurfaceHolder     // Catch:{ all -> 0x059d }
                r13.surfaceRedrawNeeded(r14)     // Catch:{ all -> 0x059d }
            L_0x056f:
                int r9 = r9 + 1
                goto L_0x055f
            L_0x0572:
                if (r6 == 0) goto L_0x0584
                boolean r0 = r1.mReportedVisible     // Catch:{ all -> 0x059d }
                if (r0 != 0) goto L_0x0584
                boolean r0 = r1.mIsCreating     // Catch:{ all -> 0x059d }
                if (r0 == 0) goto L_0x0580
                r7 = 1
                r1.onVisibilityChanged(r7)     // Catch:{ all -> 0x059d }
            L_0x0580:
                r7 = 0
                r1.onVisibilityChanged(r7)     // Catch:{ all -> 0x059d }
            L_0x0584:
                r7 = 0
                r1.mIsCreating = r7     // Catch:{ RemoteException -> 0x05b7 }
                r7 = 1
                r1.mSurfaceCreated = r7     // Catch:{ RemoteException -> 0x05b7 }
                if (r5 == 0) goto L_0x0593
                android.view.IWindowSession r0 = r1.mSession     // Catch:{ RemoteException -> 0x05b7 }
                com.android.internal.view.BaseIWindow r7 = r1.mWindow     // Catch:{ RemoteException -> 0x05b7 }
                r0.finishDrawing(r7)     // Catch:{ RemoteException -> 0x05b7 }
            L_0x0593:
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r0 = r1.mIWallpaperEngine     // Catch:{ RemoteException -> 0x05b7 }
                r0.reportShown()     // Catch:{ RemoteException -> 0x05b7 }
                r2 = r5
                goto L_0x0616
            L_0x059d:
                r0 = move-exception
                goto L_0x05a2
            L_0x059f:
                r0 = move-exception
                r5 = r67
            L_0x05a2:
                r7 = 0
                r1.mIsCreating = r7     // Catch:{ RemoteException -> 0x05b7 }
                r7 = 1
                r1.mSurfaceCreated = r7     // Catch:{ RemoteException -> 0x05b7 }
                if (r5 == 0) goto L_0x05b1
                android.view.IWindowSession r7 = r1.mSession     // Catch:{ RemoteException -> 0x05b7 }
                com.android.internal.view.BaseIWindow r9 = r1.mWindow     // Catch:{ RemoteException -> 0x05b7 }
                r7.finishDrawing(r9)     // Catch:{ RemoteException -> 0x05b7 }
            L_0x05b1:
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r7 = r1.mIWallpaperEngine     // Catch:{ RemoteException -> 0x05b7 }
                r7.reportShown()     // Catch:{ RemoteException -> 0x05b7 }
                throw r0     // Catch:{ RemoteException -> 0x05b7 }
            L_0x05b7:
                r0 = move-exception
                r7 = r5
                goto L_0x0615
            L_0x05bb:
                r0 = move-exception
                goto L_0x00d1
            L_0x05be:
                r0 = move-exception
                r7 = r67
                r12 = r5
                goto L_0x0615
            L_0x05c4:
                r0 = move-exception
                r7 = r67
                r11 = r30
            L_0x05c9:
                r12 = r57
                goto L_0x0615
            L_0x05cc:
                r0 = move-exception
                r60 = r5
                r7 = r67
                r11 = r30
                r12 = r57
                goto L_0x0615
            L_0x05d6:
                r0 = move-exception
                r59 = r3
                r60 = r5
                r7 = r67
                r11 = r30
                r12 = r57
                goto L_0x0615
            L_0x05e2:
                r0 = move-exception
                r59 = r3
                r58 = r4
                r60 = r5
                r7 = r67
                r11 = r30
                r12 = r57
                goto L_0x0615
            L_0x05f0:
                r0 = move-exception
                r59 = r3
                r58 = r4
                r60 = r5
                r57 = r12
                r7 = r67
                r11 = r30
                goto L_0x0615
            L_0x05fe:
                r0 = move-exception
                r59 = r3
                r58 = r4
                r60 = r5
                r34 = r7
                r35 = r8
                r33 = r9
                r30 = r11
                r57 = r12
                r31 = r13
                r32 = r14
                r7 = r67
            L_0x0615:
                r2 = r7
            L_0x0616:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.wallpaper.WallpaperService.Engine.updateSurface(boolean, boolean, boolean):void");
        }

        /* access modifiers changed from: package-private */
        public void attach(IWallpaperEngineWrapper wrapper) {
            if (!this.mDestroyed) {
                this.mIWallpaperEngine = wrapper;
                this.mCaller = wrapper.mCaller;
                this.mConnection = wrapper.mConnection;
                this.mWindowToken = wrapper.mWindowToken;
                this.mSurfaceHolder.setSizeFromLayout();
                this.mInitializing = true;
                this.mSession = WindowManagerGlobal.getWindowSession();
                this.mWindow.setSession(this.mSession);
                this.mLayout.packageName = WallpaperService.this.getPackageName();
                this.mIWallpaperEngine.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mCaller.getHandler());
                this.mDisplay = this.mIWallpaperEngine.mDisplay;
                this.mDisplayContext = WallpaperService.this.createDisplayContext(this.mDisplay);
                this.mDisplayState = this.mDisplay.getState();
                onCreate(this.mSurfaceHolder);
                this.mInitializing = false;
                this.mReportedVisible = false;
                updateSurface(false, false, false);
            }
        }

        public Context getDisplayContext() {
            return this.mDisplayContext;
        }

        @VisibleForTesting
        public void doAmbientModeChanged(boolean inAmbientMode, long animationDuration) {
            if (!this.mDestroyed) {
                this.mIsInAmbientMode = inAmbientMode;
                if (this.mCreated) {
                    onAmbientModeChanged(inAmbientMode, animationDuration);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void doDesiredSizeChanged(int desiredWidth, int desiredHeight) {
            if (!this.mDestroyed) {
                this.mIWallpaperEngine.mReqWidth = desiredWidth;
                this.mIWallpaperEngine.mReqHeight = desiredHeight;
                onDesiredSizeChanged(desiredWidth, desiredHeight);
                doOffsetsChanged(true);
            }
        }

        /* access modifiers changed from: package-private */
        public void doDisplayPaddingChanged(Rect padding) {
            if (!this.mDestroyed && !this.mIWallpaperEngine.mDisplayPadding.equals(padding)) {
                this.mIWallpaperEngine.mDisplayPadding.set(padding);
                updateSurface(true, false, false);
            }
        }

        /* access modifiers changed from: package-private */
        public void doVisibilityChanged(boolean visible) {
            if (!this.mDestroyed) {
                this.mVisible = visible;
                reportVisibility();
            }
        }

        /* access modifiers changed from: package-private */
        public void reportVisibility() {
            if (!this.mDestroyed) {
                this.mDisplayState = this.mDisplay == null ? 0 : this.mDisplay.getState();
                boolean z = true;
                if (!this.mVisible || this.mDisplayState == 1) {
                    z = false;
                }
                boolean visible = z;
                if (this.mReportedVisible != visible) {
                    this.mReportedVisible = visible;
                    if (visible) {
                        doOffsetsChanged(false);
                        updateSurface(false, false, false);
                    }
                    onVisibilityChanged(visible);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void doOffsetsChanged(boolean always) {
            float xOffset;
            float yOffset;
            float xOffsetStep;
            float yOffsetStep;
            boolean sync;
            int yPixels;
            if (!this.mDestroyed) {
                if (always || this.mOffsetsChanged) {
                    synchronized (this.mLock) {
                        xOffset = this.mPendingXOffset;
                        yOffset = this.mPendingYOffset;
                        xOffsetStep = this.mPendingXOffsetStep;
                        yOffsetStep = this.mPendingYOffsetStep;
                        sync = this.mPendingSync;
                        yPixels = 0;
                        this.mPendingSync = false;
                        this.mOffsetMessageEnqueued = false;
                    }
                    if (this.mSurfaceCreated) {
                        if (this.mReportedVisible) {
                            int availw = this.mIWallpaperEngine.mReqWidth - this.mCurWidth;
                            int xPixels = availw > 0 ? -((int) ((((float) availw) * xOffset) + 0.5f)) : 0;
                            int availh = this.mIWallpaperEngine.mReqHeight - this.mCurHeight;
                            if (availh > 0) {
                                yPixels = -((int) ((((float) availh) * yOffset) + 0.5f));
                            }
                            onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixels, yPixels);
                        } else {
                            this.mOffsetsChanged = true;
                        }
                    }
                    if (sync) {
                        try {
                            this.mSession.wallpaperOffsetsComplete(this.mWindow.asBinder());
                        } catch (RemoteException e) {
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void doCommand(WallpaperCommand cmd) {
            Bundle result;
            if (!this.mDestroyed) {
                result = onCommand(cmd.action, cmd.x, cmd.y, cmd.z, cmd.extras, cmd.sync);
            } else {
                result = null;
            }
            if (cmd.sync) {
                try {
                    this.mSession.wallpaperCommandComplete(this.mWindow.asBinder(), result);
                } catch (RemoteException e) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void reportSurfaceDestroyed() {
            if (this.mSurfaceCreated) {
                this.mSurfaceCreated = false;
                this.mSurfaceHolder.ungetCallbacks();
                SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                if (callbacks != null) {
                    for (SurfaceHolder.Callback c : callbacks) {
                        c.surfaceDestroyed(this.mSurfaceHolder);
                    }
                }
                onSurfaceDestroyed(this.mSurfaceHolder);
            }
        }

        /* access modifiers changed from: package-private */
        public void detach() {
            if (!this.mDestroyed) {
                this.mDestroyed = true;
                if (this.mIWallpaperEngine.mDisplayManager != null) {
                    this.mIWallpaperEngine.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
                }
                if (this.mVisible) {
                    this.mVisible = false;
                    onVisibilityChanged(false);
                }
                reportSurfaceDestroyed();
                onDestroy();
                if (this.mCreated) {
                    try {
                        if (this.mInputEventReceiver != null) {
                            this.mInputEventReceiver.dispose();
                            this.mInputEventReceiver = null;
                        }
                        this.mSession.remove(this.mWindow);
                    } catch (RemoteException e) {
                    }
                    this.mSurfaceHolder.mSurface.release();
                    this.mCreated = false;
                    if (this.mInputChannel != null) {
                        this.mInputChannel.dispose();
                        this.mInputChannel = null;
                    }
                }
            }
        }
    }

    class IWallpaperEngineWrapper extends IWallpaperEngine.Stub implements HandlerCaller.Callback {
        /* access modifiers changed from: private */
        public final HandlerCaller mCaller;
        final IWallpaperConnection mConnection;
        private final AtomicBoolean mDetached = new AtomicBoolean();
        final Display mDisplay;
        final int mDisplayId;
        final DisplayManager mDisplayManager;
        final Rect mDisplayPadding = new Rect();
        Engine mEngine;
        final boolean mIsPreview;
        int mReqHeight;
        int mReqWidth;
        boolean mShownReported;
        final IBinder mWindowToken;
        final int mWindowType;

        IWallpaperEngineWrapper(WallpaperService context, IWallpaperConnection conn, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId) {
            this.mCaller = new HandlerCaller(context, context.getMainLooper(), this, true);
            this.mConnection = conn;
            this.mWindowToken = windowToken;
            this.mWindowType = windowType;
            this.mIsPreview = isPreview;
            this.mReqWidth = reqWidth;
            this.mReqHeight = reqHeight;
            this.mDisplayPadding.set(padding);
            this.mDisplayId = displayId;
            this.mDisplayManager = (DisplayManager) WallpaperService.this.getSystemService(DisplayManager.class);
            this.mDisplay = this.mDisplayManager.getDisplay(this.mDisplayId);
            if (this.mDisplay != null) {
                this.mCaller.sendMessage(this.mCaller.obtainMessage(10));
                return;
            }
            throw new IllegalArgumentException("Cannot find display with id" + this.mDisplayId);
        }

        public void setDesiredSize(int width, int height) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageII(30, width, height));
        }

        public void setDisplayPadding(Rect padding) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageO(40, padding));
        }

        public void setVisibility(boolean visible) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10010, visible));
        }

        public void setInAmbientMode(boolean inAmbientDisplay, long animationDuration) throws RemoteException {
            this.mCaller.sendMessage(this.mCaller.obtainMessageIO(50, inAmbientDisplay, Long.valueOf(animationDuration)));
        }

        public void dispatchPointer(MotionEvent event) {
            if (this.mEngine != null) {
                this.mEngine.dispatchPointer(event);
            } else {
                event.recycle();
            }
        }

        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras) {
            if (this.mEngine != null) {
                this.mEngine.mWindow.dispatchWallpaperCommand(action, x, y, z, extras, false);
            }
        }

        public void reportShown() {
            if (!this.mShownReported) {
                this.mShownReported = true;
                try {
                    this.mConnection.engineShown(this);
                } catch (RemoteException e) {
                    Log.w(WallpaperService.TAG, "Wallpaper host disappeared", e);
                }
            }
        }

        public void requestWallpaperColors() {
            this.mCaller.sendMessage(this.mCaller.obtainMessage(10050));
        }

        public void destroy() {
            this.mCaller.sendMessage(this.mCaller.obtainMessage(20));
        }

        public void detach() {
            this.mDetached.set(true);
        }

        private void doDetachEngine() {
            WallpaperService.this.mActiveEngines.remove(this.mEngine);
            this.mEngine.detach();
        }

        public void executeMessage(Message message) {
            if (!this.mDetached.get()) {
                boolean z = false;
                switch (message.what) {
                    case 10:
                        try {
                            this.mConnection.attachEngine(this, this.mDisplayId);
                            Engine engine = WallpaperService.this.onCreateEngine();
                            this.mEngine = engine;
                            WallpaperService.this.mActiveEngines.add(engine);
                            engine.attach(this);
                            return;
                        } catch (RemoteException e) {
                            Log.w(WallpaperService.TAG, "Wallpaper host disappeared", e);
                            return;
                        }
                    case 20:
                        doDetachEngine();
                        return;
                    case 30:
                        this.mEngine.doDesiredSizeChanged(message.arg1, message.arg2);
                        return;
                    case 40:
                        this.mEngine.doDisplayPaddingChanged((Rect) message.obj);
                        return;
                    case 50:
                        Engine engine2 = this.mEngine;
                        if (message.arg1 != 0) {
                            z = true;
                        }
                        engine2.doAmbientModeChanged(z, ((Long) message.obj).longValue());
                        return;
                    case 10000:
                        this.mEngine.updateSurface(true, false, true);
                        return;
                    case 10010:
                        Engine engine3 = this.mEngine;
                        if (message.arg1 != 0) {
                            z = true;
                        }
                        engine3.doVisibilityChanged(z);
                        return;
                    case 10020:
                        this.mEngine.doOffsetsChanged(true);
                        return;
                    case 10025:
                        this.mEngine.doCommand((WallpaperCommand) message.obj);
                        return;
                    case 10030:
                        boolean reportDraw = message.arg1 != 0;
                        this.mEngine.mOutsets.set((Rect) message.obj);
                        this.mEngine.updateSurface(true, false, reportDraw);
                        this.mEngine.doOffsetsChanged(true);
                        return;
                    case 10035:
                        return;
                    case 10040:
                        boolean skip = false;
                        MotionEvent ev = (MotionEvent) message.obj;
                        if (ev.getAction() == 2) {
                            synchronized (this.mEngine.mLock) {
                                if (this.mEngine.mPendingMove == ev) {
                                    this.mEngine.mPendingMove = null;
                                } else {
                                    skip = true;
                                }
                            }
                        }
                        if (!skip) {
                            this.mEngine.onTouchEvent(ev);
                        }
                        ev.recycle();
                        return;
                    case 10050:
                        if (this.mConnection != null) {
                            try {
                                this.mConnection.onWallpaperColorsChanged(this.mEngine.onComputeColors(), this.mDisplayId);
                                return;
                            } catch (RemoteException e2) {
                                return;
                            }
                        } else {
                            return;
                        }
                    default:
                        Log.w(WallpaperService.TAG, "Unknown message type " + message.what);
                        return;
                }
            } else if (WallpaperService.this.mActiveEngines.contains(this.mEngine)) {
                doDetachEngine();
            }
        }
    }

    class IWallpaperServiceWrapper extends IWallpaperService.Stub {
        private IWallpaperEngineWrapper mEngineWrapper;
        private final WallpaperService mTarget;

        public IWallpaperServiceWrapper(WallpaperService context) {
            this.mTarget = context;
        }

        public void attach(IWallpaperConnection conn, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId) {
            this.mEngineWrapper = new IWallpaperEngineWrapper(this.mTarget, conn, windowToken, windowType, isPreview, reqWidth, reqHeight, padding, displayId);
        }

        public void detach() {
            this.mEngineWrapper.detach();
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < this.mActiveEngines.size(); i++) {
            this.mActiveEngines.get(i).detach();
        }
        this.mActiveEngines.clear();
    }

    public final IBinder onBind(Intent intent) {
        return new IWallpaperServiceWrapper(this);
    }

    /* access modifiers changed from: protected */
    public void dump(FileDescriptor fd, PrintWriter out, String[] args) {
        out.print("State of wallpaper ");
        out.print(this);
        out.println(SettingsStringUtil.DELIMITER);
        for (int i = 0; i < this.mActiveEngines.size(); i++) {
            Engine engine = this.mActiveEngines.get(i);
            out.print("  Engine ");
            out.print(engine);
            out.println(SettingsStringUtil.DELIMITER);
            engine.dump("    ", fd, out, args);
        }
    }
}
